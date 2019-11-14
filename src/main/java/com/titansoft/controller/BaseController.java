package com.titansoft.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.mapper.*;
import com.titansoft.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 父controller
 *
 * @Author: Kevin
 * @Date: 2019/7/25 11:16
 */
public abstract class BaseController extends ApplicationObjectSupport {
    public static final Logger log = LoggerFactory.getLogger(BaseController.class);
    /***********************加载Mapper***********************/
    @Autowired
    AuthMapper authMapper;
    @Autowired
    CadreMapper cadreMapper;
    @Autowired
    CatalogueMapper catalogueMapper;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    DaProcessMapper daProcessMapper;
    @Autowired
    DictionaryMapper dictionaryMapper;
    @Autowired
    ErrorLogMapper errorLogMapper;
    @Autowired
    LoginLogMapper loginLogMapper;
    @Autowired
    MediaSourceMapper mediaSourceMapper;
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    OrganMapper organMapper;
    @Autowired
    PrivilegeMapper privilegeMapper;
    @Autowired
    PrivilegeViewMapper privilegeViewMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    SecurityLogMapper securityLogMapper;
    @Autowired
    SortMapper sortMapper;
    @Autowired
    SqcdMapper sqcdMapper;
    @Autowired
    StoreitemMapper storeitemMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    UnitMapper unitMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserStatusMapper userStatusMapper;

    /***********************加载Service***********************/
    @Autowired
    CadreCJService cadreCJService;
    @Autowired
    CadreService cadreService;
    @Autowired
    CadreSHService cadreSHService;
    @Autowired
    CatalogueService catalogueService;
    @Autowired
    DesktopService desktopService;
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    LoginService loginService;
    @Autowired
    LogService logService;
    @Autowired
    MediaService mediaService;
    @Autowired
    ConfigService configService;
    @Autowired
    PrivilegeService privilegeService;
    @Autowired
    UnitService unitService;
    @Autowired
    CadreStatisticsService cadreStatisticsService;
    @Autowired
    NoticeService noticeService;

    public static HttpServletRequest request;
    public static HttpServletResponse response;
    public static HttpSession session;
    public static Logininfo logininfo;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        this.request = request;
        this.response = response;
        this.session = session;
        this.logininfo = (Logininfo) session.getAttribute("logininfo");
        setParams(request);
    }


    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    protected void toAjax(String data) {
        try {
            HttpServletResponse response = getResponse();
            if (data != null && data.indexOf("error:") >= 0) {
                data = "{success: false, error: \"服务器错误,请联系管理员！\"}";
            }
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("cache-control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(data);
        } catch (IOException e) {

        }
    }

    protected void toAjaxError(String data) {
        try {
            HttpServletResponse response = getResponse();
            if (data != null) {
                data = "{success: false, error: \"" + data + "\"}";
            }
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("cache-control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(data);
        } catch (IOException e) {

        }
    }


    protected void toAjaxSuccess() {
        try {
            HttpServletResponse response = getResponse();
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("cache-control", "no-cache");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{success: true}");
        } catch (IOException e) {

        }
    }

    protected String result = "error"; // 返回目标页面状态

    protected String cpage = null; // 当前页数

    protected String medialook = null; // 查看多媒体

    protected String mediaedit = null; // 编辑多煤体

    protected String filter = null; // 列表查询条件

    protected String basefilter = "";//外部条件

    protected String pagepath = "";// 动态调用页面path

    protected String pagepathaction = "";// 动态调用action path

    protected String uid = ""; // 列表标识对象 防止多个列表控件同时打开变量不同步.

    public final String PAGEPATH = "pagepath";

    public final String PAGEPATHACTION = "pagepathaction";

    // ///////////////////ext列表控件相关信息 start
    protected String fieldlist = ""; // 查询字段

    protected String tablename = ""; // 查询表名

    protected String key = "";//主键名

    protected String treeid = "0"; //树id

    protected String treetext = ""; //树文本

    protected String node = "0"; //树id

    protected String nodetext = ""; //树文本

    protected String findvalue = "";// 查询值

    protected String findname = ""; // 查询字段名

    protected String relation = ""; // 查询符

    protected String searchresult = ""; // 结果中检索

    protected String sortfield = "";// 排序字段

    protected String dir = ""; // 排序类型

    protected int start = 0;

    protected int limit = 0;

    protected int page = 0;

    protected String dataType = "";// 数据库类型

    protected boolean islook = false;// 是否查看表单 true表示查看

    protected String allid = "";//选择所有记录ID


    public void setParams(HttpServletRequest request) {
        this.start = Integer.valueOf(request.getParameter("start") == null ? "0" : request.getParameter("start"));
        this.fieldlist = request.getParameter("fieldlist") == null ? "" : request.getParameter("fieldlist");
        this.treeid = request.getParameter("treeid") == null ? "" : request.getParameter("treeid");
        this.filter = request.getParameter("filter") == null ? "" : request.getParameter("filter");
        this.page = Integer.valueOf(request.getParameter("page") == null ? "0" : request.getParameter("page"));
        this.limit = Integer.valueOf(request.getParameter("limit") == null ? "0" : request.getParameter("limit"));
        this.sortfield = request.getParameter("sortfield") == null ? "" : request.getParameter("sortfield");
        this.dir = request.getParameter("dir") == null ? "" : request.getParameter("dir");
    }

    protected Map grid() {
        ///////////////////////////////////接收条件 start
        try {
            basefilter = java.net.URLDecoder.decode(basefilter, "UTF-8"); //列表初始条件
            filter = java.net.URLDecoder.decode(filter, "UTF-8");//列表查询条件
        } catch (Exception er) {
            log.info("无需编码");
        }
        if (filter.trim().equalsIgnoreCase(""))
            filter = " 1=1 ";
        if (this.basefilter.trim().equalsIgnoreCase(""))
            filter = filter;
        else
            filter = "( " + basefilter + " )  and ( " + filter + " )";
        ///////////////////////////////////接收条件  end
        ///////////////////////////////////接收条件转换 start
        basefilter = basefilter.replace('∪', '\'');
        basefilter = basefilter.replaceAll("∩", "%");
        filter = filter.replace('∪', '\'');
        filter = filter.replaceAll("∩", "%");

        tablename = tablename.replace('∪', '\'');
        tablename = tablename.replaceAll("∩", "%");
        fieldlist = fieldlist.replace('∪', '\'');
        fieldlist = fieldlist.replaceAll("∩", "%");
        ///////////////////////////////////接收条件转换  end

        //////////////////////////////////接收查询字段列表示start
        try {
            tablename = java.net.URLDecoder.decode(tablename, "UTF-8"); // 查询表名
            fieldlist = java.net.URLDecoder.decode(fieldlist, "UTF-8"); // 查询字段列表
        } catch (Exception er) {
            log.info("无需编码");
        }
        //////////////////////////////////接收查询字段列表示 end
        // 获取分页信息 start
        int start = 0;
        try {
            start = Integer.parseInt(request.getParameter("start"));
        } catch (Exception ex) {
            start = 0;
        }
        int limit = 15;
        try {
            limit = Integer.parseInt(request.getParameter("limit"));
        } catch (Exception ex) {
            limit = 20;
        }
        try {
            basefilter = basefilter.replace('\'', '∪');
            basefilter = basefilter.replaceAll("%", "∩");
        } catch (Exception er) {
            log.info(er.getMessage());
        }
        Map m = new HashMap();
        m.put("fieldlist", fieldlist);
        m.put("filter", filter);
        m.put("start", start);
        m.put("limit", limit);
        m.put("sortfield", sortfield);
        m.put("dir", dir);
        m.put("page", page);
        return m;

    }


    protected String pageToJson(Page page) {
        String json = "{totalCount:" + page.getTotal() + ",list:[";
        if (page.getTotal() == 0) {
            json = json + "]}";
        } else {
            String jsonsingle = "";//定义单条对象
            ArrayList listData = (ArrayList) page.getRecords();
            String mediaPath = "";
            if (listData != null && !listData.isEmpty()) {
                int i = 0;
                for (Iterator iter = listData.iterator(); iter.hasNext(); ) {
                    jsonsingle = JSONObject.toJSONString(iter.next());
                    if (json.trim().equalsIgnoreCase("{totalCount:" + page.getTotal() + ",list:["))
                        json = json + "" + jsonsingle + "";
                    else
                        json = json + "," + jsonsingle + "";
                }
            }
            //设置json结束符
            json = json + "]}";
        }
        return json;
    }


    protected Map<String, Object> dataGrid(BaseMapper baseMapper) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map> list = null;
        try {

            long total = baseMapper.selectCount((Wrapper) new QueryWrapper().apply(getAllFilter()));
            map.put("total", total);
            //  list = this.modulBS.grid(getTable(), getKey(), this.fieldlist, getAllFilter(), sortfield, dir, start, limit);
            map.put("list", list);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return map;
    }

    protected String getAllFilter() {
        String value = basefilter;
        if (value == null)
            value = "";
        if (filter != null && !"".equals(filter)) {
            if (!"".equals(value))
                value = "(" + value + ") and (" + filter + ")";
            else
                value = filter;
        }
        value = value.replaceAll("∮", "&");
        value = value.replaceAll("@", "'" + node + "'");
        return value;
    }

    //解码
    public Map decodeMap(Map map) {
        Set keSet = map.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext(); ) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String value = "";
            if (ov instanceof String) {
                value = (String) ov;
            }
            try {
                value = URLDecoder.decode(value, "UTF-8");
                me.setValue(value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return map;

    }

    /**
     * Method 根据传过来字符串----ajax转码
     *
     * @param
     * @return result 返回已编码o对象
     */
    public String convercharajax(String filenamelist) throws Exception {
        return java.net.URLDecoder
                .decode(String.valueOf(filenamelist), "UTF-8");
    }

}
