
package com.titansoft.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * 非法字符过滤器
 * 1.所有非法字符配置在web.xml中，如需添加新字符，请自行配置
 * 2.请注意请求与相应时的编码格式设置，否则遇到中文时，会出现乱码(GBK与其子集应该没问题)
 *
 * @author lee
 */
@Order(0)
@WebFilter(filterName = "xssfilter", urlPatterns = "/*", initParams = {@WebInitParam(name = "encoding", value = "utf-8"),
        @WebInitParam(name = "legalNames", value = "content1,ver"),
        @WebInitParam(name = "actionNames", value = ""),
        @WebInitParam(name = "nofilter", value = ".js,.css"),
        @WebInitParam(name = "illegalChars", value = "$,@,',&quot;,\\',\\&quot;,&lt;,>,+,CR,LF,\\&quot;,&quot;,\\,;,[,],{,},|,http://")
})
public class XSSFilter implements Filter {
    //private Logger log = Logger.getLogger(AttackCharFilter.class);
    private String encoding;
    private String[] legalNames;
    private String[] illegalChars;
    private String[] actionNames;
    private String[] nofilter;

    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        legalNames = filterConfig.getInitParameter("legalNames").split(",");
        illegalChars = filterConfig.getInitParameter("illegalChars").split(",");
        actionNames = filterConfig.getInitParameter("actionNames").split(",");
        nofilter = filterConfig.getInitParameter("nofilter").split(",");
    }

    public void destroy() {
        encoding = null;
        legalNames = null;
        illegalChars = null;
    }

    private boolean doPass(String path, String[] page) {
        for (String p : page) {
            if (Pattern.matches(p, path))
                return true;
        }
        return false;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //必须手动指定编码格式
        req.setCharacterEncoding(encoding);
        String tempURL = req.getRequestURI() + "?" + req.getQueryString();
        //log.info(tempURL);
        Enumeration params = req.getParameterNames();

        //是否执行过滤  true：执行过滤  false：不执行过滤
        boolean executable = true;

        //非法状态  true：非法  false；不非法
        boolean illegalStatus = false;
        String illegalChar = "";

        //唐展鸿-增加-20160428
        String action = "";//用作存放当前访问的action名
        String tempURLTemp = "";//存放tempURL值
        boolean actionGo = false;//存放是否不走特殊符号过滤

        //用作判断当前访问的action是否为在web.xml中filter的init-param所配置项
        if (tempURL != null && !"".equals(tempURL)&&!doPass(tempURL,nofilter)) {
            tempURLTemp = tempURL.trim();
            tempURLTemp = tempURLTemp.replace("\\", "/");
            action = tempURLTemp;
            if (actionNames.length > 0) {
                for (String actionName : actionNames) {
                    if (!"".equals(actionName.trim()) && action.toLowerCase().toLowerCase().indexOf(actionName.toLowerCase()) >= 0) {
                        actionGo = true;
                        break;
                    }
                }
            }
            if (request.getParameterMap().containsKey("bsencodeForm64")) {
                actionGo = true;
            }
        }
        //唐展鸿-增加-20160428
        //如果不为以上所配置的action,都将走以下的逻辑判断
        if (!actionGo) {
            //对参数名与参数进行判断
            w:
            while (params.hasMoreElements()) {

                String paramName = (String) params.nextElement();

                executable = true;

                //密码不过滤
                //20180202 LPK 前端查询参数改为jason数组，包含[]{}等字符,不过滤
                if (paramName.toLowerCase().contains("password")
                        || paramName.toLowerCase().contains("filterstr")
                        || paramName.toLowerCase().contains("catalogstr")
                        || paramName.toLowerCase().contains("list")
                        || paramName.toLowerCase().contains("filter")
                ) {
                    executable = false;
                } else {
                    //检查提交参数的名字，是否合法，即不过滤其提交的值
                    f:
                    for (int i = 0; i < legalNames.length; i++) {
                        if (legalNames[i].equals(paramName)) {
                            executable = false;
                            break f;
                        }
                    }
                }

                if (executable) {
                    String[] paramValues = req.getParameterValues(paramName);

                    f1:
                    for (int i = 0; i < paramValues.length; i++) {

                        String paramValue = paramValues[i];

                        f2:
                        for (int j = 0; j < illegalChars.length; j++) {

                            illegalChar = illegalChars[j];

                            if (paramValue.indexOf(illegalChar) != -1 || paramValue.indexOf(URLEncoder.encode(illegalChar, "utf-8")) != -1) {
                                illegalStatus = true;//非法状态

                                break f2;
                            }
                        }

                        if (illegalStatus) {
                            break f1;
                        }

                    }
                }

                if (illegalStatus) {
                    break w;
                }
            }
            //对URL进行判断
            for (int j = 0; j < illegalChars.length; j++) {

                illegalChar = illegalChars[j];

                if (tempURL.indexOf(illegalChar) != -1 || tempURL.indexOf(URLEncoder.encode(illegalChar, "utf-8")) != -1) {
                    illegalStatus = true;//非法状态
                    break;
                }
            }
        }
        if (illegalStatus && 2 == 1) {
            System.out.println("***************被拦截的URL为:" + tempURL);
            //必须手动指定编码格式
            res.setContentType("text/html;charset=" + encoding);
            res.setCharacterEncoding(encoding);
            res.setContentType("text/html;charset=UTF-8");
            res.setHeader("Cache-Control", "no-cache");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write("{success: false, error:\"提交内容中含有非法字符,提交内容中不能含有＇|”,＇$”,＇@＇,＇'＇,＇&quot;＇,＇\'＇,＇\\&quot;＇,＇&lt;＇,＇>＇,＇(＇,＇)＇,＇+＇,＇CR＇,＇LF＇,＇\\&quot;＇,＇&quot;＇,＇\\\\＇,＇;＇,请检查！\"}");
            //res.getWriter().print("<script>window.alert('当前链接中存在非法字符');window.history.go(-1);</script>");
        } else {
            filterChain.doFilter(request, response);
        }
    }

}

