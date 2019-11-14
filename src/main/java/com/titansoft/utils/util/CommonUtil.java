package com.titansoft.utils.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.titansoft.model.Tree;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.Query;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * 常用工具类
 *
 * @Author: Kevin
 * @Date: 2019/7/27 9:41
 */
public class CommonUtil {
    /**
     * @param *    @param list:
     * @param key: Map的key
     * @param s:   分隔符
     * @return
     * @description list存放的是Map
     * @author Fkw
     * @date 2019/7/27
     */
    public static String getListMapString(List list, String key, String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            Map map = (Map) list.get(i);
            sb.append(map.get(key)).append(s);
        }
        return sb.toString();
    }

    /**
     * @param * @param ids:
     * @return
     * @description ID号字符串数字转换
     * @author Fkw
     * @date 2019/7/27
     */
    public static String getIDstr(String ids) {
        if (ids.indexOf("'") == -1) {
            ids = ids.replace(",", "','");
            ids = "'" + ids + "'";
        }
        return ids;
    }

    public static String getGuid() {
        GuidUtil myguid = new GuidUtil(false);
        return myguid.toString();
    }

    public static String maptojson(Map map) throws Exception {
        return JSONObject.toJSONString(map);
    }

    /**
     * @param * @param orgMap:
     * @return
     * @description 把map的key转成小写
     * @author Fkw
     * @date 2019/7/30
     */
    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap) {
        Map<String, Object> resultMap = new HashMap<>();

        if (orgMap == null || orgMap.isEmpty()) {
            return resultMap;
        }

        Set<String> keySet = orgMap.keySet();
        for (String key : keySet) {
            String newKey = key.toLowerCase();
            newKey = newKey.replace("_", "");

            resultMap.put(newKey, orgMap.get(key));
        }

        return resultMap;
    }

    /**
     * @param * @param orgList:
     * @return
     * @description 把list 的里面的Map的key全部转成小写
     * @author Fkw
     * @date 2019/8/1
     */
    public static List transformUpperCaseList(List orgList) {
        List returnList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < orgList.size(); i++) {
            Map resultMap = (Map) orgList.get(i);
            Map returnMap = transformUpperCase(resultMap);
            returnList.add(returnMap);
        }
        return returnList;
    }


    public static String createTreeJson(List<Tree> trees, boolean isStaticTree) {
        String result = "";
        // 静态树
        if (isStaticTree) {

        } else {
            //将为null的属性不进行序列化
            PropertyFilter filter = new PropertyFilter() {

                @Override
                public boolean apply(Object source, String name, Object value) {

                    return value == null;
                }
            };


            // 将结合转换成JSON字符串
            result = JSONArray.toJSONString(trees);
        }
        return result;
    }


    /**
     * @param * @param filedlist:
     * @return
     * @description 把 a,b,c,d转成 "a","b","c","d"
     * @author Fkw
     * @date 2019/8/2
     */
    public static String getField(String fieldlist) {
        String returnStr = "\"" + fieldlist.replace(",", "\",\"") + "\"";
        return returnStr;
    }


    /**
     * @param * @param obj:
     * @return
     * @description 把对象转成Map  包含字段的值为空的
     * @author Fkw
     * @date 2019/8/2
     */
    public static HashMap<String, Object> convertToMap(Object obj) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();
            boolean accessFlag = fields[i].isAccessible();
            fields[i].setAccessible(true);

            Object o = null;
            try {
                o = fields[i].get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (o != null) {
                map.put(varName, o.toString());
            } else {
                map.put(varName, "");
            }
            fields[i].setAccessible(accessFlag);
        }

        return map;
    }


    /**
     * @param * @param map:
     * @return
     * @description 处理map，value为null的值处理成空字符串
     * @author Fkw
     * @date 2019/10/19
     */
    public static Map<String, Object> MapNullToEmpty(Map<String, Object> map) {
        Set<String> set = map.keySet();
        if (set != null && !set.isEmpty()) {
            for (String key : set) {
                if (map.get(key) == null) {
                    map.put(key, "");
                }
            }
        }
        return map;
    }


    /**
     * @param * object.class
     * @return
     * @description 获取注解获取表名
     * @author Fkw
     * @date 2019/8/13
     */
    public static String getTableName(Object o) {
        TableName tableName = (TableName) ((Class) o).getAnnotation(TableName.class);
        return tableName.value();
    }

    public static void setTableName(Object o, String table) {
        try {
            TableName tableName = (TableName) ((Class) o).getAnnotation(TableName.class);
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(tableName);
            // 获取 AnnotationInvocationHandler 的 memberValues 字段
            Field declaredField = null;
            declaredField = invocationHandler.getClass().getDeclaredField("memberValues");

            //允许访问私有变量
            declaredField.setAccessible(true);
            // 获取 memberValues
            Map memberValues = (Map) declaredField.get(invocationHandler);
            memberValues.put("value", table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取ip
    public static String getLocalIpAddr() {
        Enumeration<NetworkInterface> networks = null;
        try {
            // 获取网卡设备
            networks = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {

        }
        InetAddress ip = null;
        Enumeration<InetAddress> addrs;
        // 遍历网卡设备
        while (networks.hasMoreElements()) {
            addrs = networks.nextElement().getInetAddresses();
            while (addrs.hasMoreElements()) {
                ip = addrs.nextElement();
                if (ip != null && ip instanceof InetAddress && ip.isSiteLocalAddress()) {
                    if (ip.getHostAddress() == null || "".equals(ip.getHostAddress())) {
                        return null;
                    }
                    return ip.getHostAddress();// 客户端ip
                }
            }
        }
        return null;
    }

    //获取tomcat端口号
    public static String getLocalPort() throws MalformedObjectNameException {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
                Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
        String port = objectNames.iterator().next().getKeyProperty("port");
        return port;
    }

    //转换中文
    public static String ToCH(int intInput) {
        String si = String.valueOf(intInput);
        String sd = "";
        if (si.length() == 1) // 個
        {
            sd += GetCH(intInput);
            return sd;
        } else if (si.length() == 2)// 十
        {
            if (si.substring(0, 1).equals("1"))
                sd += "十";
            else
                sd += (GetCH(intInput / 10) + "十");
            sd += ToCH(intInput % 10);
        } else if (si.length() == 3)// 百
        {
            sd += (GetCH(intInput / 100) + "百");
            if (String.valueOf(intInput % 100).length() < 2)
                sd += "零";
            sd += ToCH(intInput % 100);
        } else if (si.length() == 4)// 千
        {
            sd += (GetCH(intInput / 1000) + "千");
            if (String.valueOf(intInput % 1000).length() < 3)
                sd += "零";
            sd += ToCH(intInput % 1000);
        } else if (si.length() == 5)// 萬
        {
            sd += (GetCH(intInput / 10000) + "萬");
            if (String.valueOf(intInput % 10000).length() < 4)
                sd += "零";
            sd += ToCH(intInput % 10000);
        }

        return sd;
    }

    private static String GetCH(int input) {
        String sd = "";
        switch (input) {
            case 1:
                sd = "一";
                break;
            case 2:
                sd = "二";
                break;
            case 3:
                sd = "三";
                break;
            case 4:
                sd = "四";
                break;
            case 5:
                sd = "五";
                break;
            case 6:
                sd = "六";
                break;
            case 7:
                sd = "七";
                break;
            case 8:
                sd = "八";
                break;
            case 9:
                sd = "九";
                break;
            default:
                break;
        }
        return sd;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //CommonUtil.getTableName(ClassLoader.getClassLoader(Cadre.class));
        setFineReportDataSource();
    }

    public static void setFineReportDataSource() {
        try {
            SAXReader reader = new SAXReader();

            Document document = reader.read(new File("C:/Java/project/titansrsda/src/main/webapp/WEB-INF/resources/datasource.xml"));

            Element root = document.getRootElement();

            Iterator<?> it = root.elementIterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();//获取子元素

                Element connectionElement = e.element("Connection");
                Element jdbcElement = connectionElement.element("JDBCDatabaseAttr");
                Attribute aa = jdbcElement.attribute("user");
                aa.setValue("bb");
                writeXml(document, "C:/Java/project/titansrsda/src/main/webapp/WEB-INF/resources/datasource.xml");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeXml(Document document, String filePath) throws IOException {
        File xmlFile = new File(filePath);
        XMLWriter writer = null;
        try {
            if (xmlFile.exists())
                xmlFile.delete();
            writer = new XMLWriter(new FileOutputStream(xmlFile), OutputFormat.createPrettyPrint());
            writer.write(document);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
