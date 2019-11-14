package com.titansoft.filter;

import com.titansoft.entity.system.Logininfo;
import com.titansoft.utils.util.DES_simple;
import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @Author: Kevin
 * @Date: 2019/7/25 16:33
 */
@WebFilter(filterName = "securitys", urlPatterns = "/*", initParams = {@WebInitParam(name = "noLoginPage", value = "/index,/CheckCodeImg.jsp,/login/*,/druid/*,/resources/*,/ReportServer/*,/ReportServer,/decision,/decision/*,/static/*,/api/*,/lib/*,/css/*,/images/*,/js/*,/layui/*"),
})
@Order(1)
public class SingleLogin extends HttpServlet implements Filter {
    protected Logger log = Logger.getLogger(this.getClass()); // log4j
    private String[] noLogin = null;
    public static String serverPort = null;
    public static String appName = null;
    private String[] nofilter;
    @Override
    public void destroy() {
        this.noLogin = null;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        String path = request.getRequestURI().substring(
                request.getContextPath().length());
        serverPort = request.getServerPort() + "";
        appName = request.getContextPath() + "";
        String paramString = request.getQueryString();
        if (paramString != null && paramString.indexOf("bsencodeForm64") >= 0) {
            paramString = DES_simple.decryptBase64Str(request.getParameter("bsencodeForm64"));
            paramString = paramString.split("\\|")[1];
        }
        if (this.doPass(path, this.noLogin)) {
            chain.doFilter(request, response);
        } else {
            Logininfo logininfo = (Logininfo) session.getAttribute("logininfo");
            if (logininfo != null) {
                String user = logininfo.getUsername();
                if (user != null && !"".equals(user)) {
                    chain.doFilter(request, response);
                } else {
                    chain.doFilter(request, response);
                    //this.sendRetoLogin(request, response);
                }
            } else {
                chain.doFilter(request, response);
               //pthis.sendRetoLogin(request, response);
            }
        }
    }

    /**
     * 过滤失败返回登录页面
     *
     * @param request
     * @param response
     */
    private void sendRetoLogin(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + this.noLogin[0]);
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("cache-control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{success: false,error:\"没有得到凭据信息!请先登录集成平台！\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 脱离监控的URL，原则上只允许登录页
     *
     * @param path
     * @return
     */
    private boolean doPass(String path, String[] page) {
        for (String p : page) {
            if (Pattern.matches(p, path))
                return true;
        }
        return false;
    }


    @Override
    public void init(FilterConfig config) throws ServletException {
        String tempNologin = config.getInitParameter("noLoginPage");
        this.noLogin = tempNologin != null ? ("/," + tempNologin).split(",")
                : new String[]{"/"};
        for (int i = 0, len = this.noLogin.length; i < len; ++i)
            this.noLogin[i] = this.noLogin[i].trim().replaceAll("\\.", "\\\\.")
                    .replaceAll("\\*", ".*");
    }
}