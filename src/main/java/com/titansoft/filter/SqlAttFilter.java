package com.titansoft.filter;


import com.titansoft.utils.util.DES_simple;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * sql 注入攻击过滤器
 *
 * @author lhh
 * @version 1.0 2017-10-09
 */
@Order(2)
@WebFilter(filterName = "sqlattfilter", urlPatterns = "/*",initParams = {@WebInitParam(name = "encoding", value = "utf-8"),
        @WebInitParam(name = "nofilter", value = ".js,.css")
})
public class SqlAttFilter extends HttpServlet implements Filter {
    private String[] nofilter;
    public void init(FilterConfig filterConfig) throws ServletException {
        nofilter = filterConfig.getInitParameter("nofilter").split(",");
    }

    public static HashMap<String, String> sessionOnlineMap = null;

    public static long sessiontimeout = 0;

    private boolean doPass(String path, String[] page) {
        for (String p : page) {
            if (Pattern.matches(p, path))
                return true;
        }
        return false;
    }

    //Process the request/response pair
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        javax.servlet.http.HttpServletRequest hrequest = (javax.servlet.http.HttpServletRequest) request;
        javax.servlet.http.HttpServletResponse hresponse = (javax.servlet.http.HttpServletResponse) response;
        javax.servlet.http.HttpSession session = hrequest.getSession(true);
        try {
            Iterator its = hrequest.getParameterMap().values().iterator();
            SpecialCharDeal specialCharDeal = new SpecialCharDeal();
            String tempURL = hrequest.getRequestURI() + "?" + hrequest.getQueryString();
            if (tempURL != null && !"".equals(tempURL)&&!doPass(tempURL,nofilter)) {
                while (its.hasNext()) {
                    String[] params = (String[]) its.next();
                    for (int i = 0; i < params.length; i++) {
                        params[i] = specialCharDeal.specialCharEncode(params[i]);
                        //params[i] = specialCharDeal.sqlattCharsEncode(params[i]);
                        params[i] = specialCharDeal.xssattCharsDecode(params[i]);
                    }
                }

                Map<String, String[]> m = new HashMap<String, String[]>(request.getParameterMap());
                ParameterRequestWrapper requestw = new ParameterRequestWrapper((HttpServletRequest) request, m);
                boolean uncommon = false; //是否不正常请求
                if (requestw.getParameterMap().containsKey("bsencodeForm64")) {
                    String params = decode(requestw.getParameter("bsencodeForm64"));

                    if ("".equals(params) || params.indexOf("|") < 0 || params.indexOf("datalength=") != 0) {
                        uncommon = true;
                    } else {
                        String lenstr = params.substring(0, params.indexOf("|"));
                        params = params.substring(params.indexOf("|") + 1);
                        int length = Integer.parseInt(lenstr.split("=")[1]);
                        if (length != params.length()) {
                            uncommon = true;
                        } else {
                            for (String param : params.split("&")) {
                                if (param.split("=").length == 2) {
                                    try {
                                        requestw.addParameter(param.split("=")[0],
                                                java.net.URLDecoder.decode(
                                                        String.valueOf(param
                                                                .split("=")[1]),
                                                        "UTF-8"));
                                    } catch (Exception e) {
                                        uncommon = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (uncommon) //不正常请求
                {
                    HttpServletResponse res = (HttpServletResponse) response;
                    //必须手动指定编码格式
                    res.setContentType("text/html;charset=UTF-8");
                    res.setCharacterEncoding("UTF-8");
                    res.setContentType("text/html;charset=UTF-8");
                    res.setHeader("Cache-Control", "no-cache");
                    res.setCharacterEncoding("UTF-8");
                    res.getWriter().write("{success: false, error:\"请求参数不合法,请联系管理员！\"}");
                } else {
                    ServletResponse newresponse = response;
                    if (requestw instanceof HttpServletRequest) {
                        newresponse = new ResponseWrapper(
                                (HttpServletResponse) response);
                    }
                    filterChain.doFilter(requestw, newresponse);
                    if (newresponse instanceof ResponseWrapper) {
                        String text = newresponse.toString();
                        if (text != null) {
                            text = specialCharDeal.specialCharDecode(text);
                        }
                        if (response != null && response.getWriter() != null && !response.isCommitted()) {
                            response.setCharacterEncoding("utf-8");
                            response.getWriter().write(text);
                        }
                    }
                }
            }else{
                filterChain.doFilter(request, response);
            }
        } catch (Exception er) {
            //er.printStackTrace();
        }
    }

    //base64解码
    private String decode(String str) {
        return DES_simple.decryptBase64Str(str);
    }

    //Clean up resources
    public void destroy() {
    }
}
