package com.titansoft.utils.config;


import com.titansoft.entity.system.Auth;
import com.titansoft.entity.system.Logininfo;
import com.titansoft.entity.system.Privilege;
import com.titansoft.entity.system.Product;
import com.titansoft.mapper.PrivilegeMapper;
import com.titansoft.mapper.ProductMapper;
import com.titansoft.utils.util.DES_simple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 初始化功能信息对象
 * @author Administrator
 *
 */
@Component
public class PrivilegeConfig {
	@Autowired
	ProductMapper productMapper;
	@Autowired
	PrivilegeMapper privilegeMapper;
	/**
	 * 功能对象的map对象,key为privilegeid
	 */
	public static Map<String, Privilege> privilegeMap = new HashMap<String,Privilege>();
	
	/**
	 * 功能对象的map对象,key为parentid,为了快速查询相关的权限按钮等
	 */
	public static Map<String,List<Privilege>> privilegeListParentidMap = new HashMap<String,List<Privilege>>();
	
	/**
	 * 功能对象的map对象,以权限的URI资源作为KEY，用于监控用户是否有权限操作该资源URL
	 */
	public static Map<String,Privilege> privilegeListUriMap = new HashMap<String,Privilege>();
	
	/**
	 * 功能对象的map对象,以权限的URI资源作为KEY，用于监控用户是否有权限操作该资源URL
	 */
	public static Map<String,Privilege> privilegeListCheckUrlMap = new HashMap<String,Privilege>();
	
	/**
	 * 登录者凭证map，包含凭证 ,key为登录凭证
	 */
	public static Map<String, Auth> authMap = new HashMap<String,Auth>();
	
	/**
	 * 登录者信息对象map，包含用户信息和用户权限信息 ,key为登录凭证,与登录凭证一一对应
	 */
	public static Map<String, Logininfo> logininfoMap = new HashMap<String,Logininfo>();
	
	/**
	 * 初始化产品的所有按钮信息到内存,能通过parentid找到其下所有的按钮对象
	 * @pram String productCode 产品id
	 * @return
	 */
	public static Map<String,List<Map<String,String>>> buttonsStrMap = new HashMap<String,List<Map<String,String>>>();
	
	/**
	 * 功能对象的map对象,key为code,value为privilegeid
	 */
	public static Map<String,Privilege> privilegeCodeMap = new HashMap<String,Privilege>();
	
	/**
	 * 功能对象的map对象,key为功能全路径的fullname,value为privilegeid
	 */
	public static Map<String,Privilege> privilegeFullNameMap = new HashMap<String,Privilege>();
	
	/**
	 * 产品对象
	 */
	public static Map<String,Product> productMap = new HashMap<String, Product>();
	
	
	/**
	 * 提供安全平台功能权限检查
	 * @param request
	 * @param path
	 * @return
	 */
	public static boolean checkPrower(HttpServletRequest request, String path)
	{
		boolean hashpower = true;
		String dodecs = "请求路径为:" + path + ",资源路径不明确";
		String errortype = "越权行为";
		String errorlocation = "请求路径为:" + path + ",资源路径不明确";
		Logininfo logininfo = logininfoMap.get(request.getRemoteAddr());
		path = path.replace("\\", "/").replace("//", "/");
		if(PrivilegeConfig.privilegeListUriMap.containsKey(path) || PrivilegeConfig.privilegeListCheckUrlMap.containsKey(path))
		{
			String privilegeid = null;
			String funondbclick = "false";
			String paramString = request.getQueryString();
			if(paramString!= null && paramString.indexOf("bsencodeForm64")>=0)
			{
				paramString = DES_simple.decryptBase64Str(request.getParameter("bsencodeForm64"));
				paramString = paramString.split("\\|")[1];
			}
			if(paramString!= null && paramString.indexOf("privilegeid")>=0)
			{
				String p1 = paramString.substring(0, paramString.indexOf("privilegeid"));
				String p2 = paramString.substring(paramString.indexOf("privilegeid")).split("&")[0];
				paramString = p1 + p2;
			}
			if(paramString!= null && paramString.indexOf("modulecode")>=0)
			{
				String p1 = paramString.substring(0, paramString.indexOf("modulecode"));
				String p2 = paramString.substring(paramString.indexOf("modulecode")).split("&")[0];
				paramString = p1 + p2;
				if(paramString.indexOf("_funondbclick") >0)
				{
					paramString = paramString.substring(0, paramString.indexOf("_funondbclick"));
					funondbclick = "true";
				}
			}
			if(paramString!= null && !"".equals(paramString))
			{
				String fulluri = path + "?" + paramString;
				if(PrivilegeConfig.privilegeListUriMap.get(fulluri)!=null) //加上参数作为监控对象让加上动态参数url也可以被监控
				{
					path = fulluri;
				}
				if(PrivilegeConfig.privilegeListCheckUrlMap.get(fulluri)!=null) //加上参数作为监控对象让加上动态参数url也可以被监控
				{
					path = fulluri;
				}
			}
			//证明是按钮操作，初始privilegeListUriMap时指定如果是按钮对象监控，直接放入权限对象
			if(PrivilegeConfig.privilegeListUriMap.get(path)!=null && PrivilegeConfig.privilegeListUriMap.get(path) != null)
			{
				privilegeid = PrivilegeConfig.privilegeListUriMap.get(path).getPrivilegeid();
				errorlocation = PrivilegeConfig.privilegeMap.get(privilegeid).getFullname();
				if(!logininfo.getFunmap().containsKey(privilegeid))
				{
					hashpower = false;
					dodecs="越权行为，没有操作【"+PrivilegeConfig.privilegeMap.get(privilegeid).getFullname()+"】功能的权限";
					errortype="越权行为";
				}
			}
			else if(PrivilegeConfig.privilegeListCheckUrlMap.containsKey(path) && PrivilegeConfig.privilegeListCheckUrlMap.get(path) != null)
			{
				privilegeid = PrivilegeConfig.privilegeListCheckUrlMap.get(path).getPrivilegeid();
				errorlocation = PrivilegeConfig.privilegeMap.get(privilegeid).getFullname();
				if(!logininfo.getFunmap().containsKey(privilegeid))
				{
					hashpower = false;
					dodecs="越权行为，没有操作【"+PrivilegeConfig.privilegeMap.get(privilegeid).getFullname()+"】功能的权限";
					errortype="越权行为";
				}
			}
			else
			{
				hashpower = false;
			}
			if(!hashpower)
			{
				/*try {
					Service sv = new Service();
					sv.setDataSourceName(Constant.getDatasource("security"));
					sv.put("logininfo", logininfo);
					sv.put("errortype", errortype);
					sv.put("dodesc", dodecs);
					sv.put("errorlocation", errorlocation);
					sv.doService("titans.log.bs.LogServiceBS",
							"writeerrorlog");
				} catch (Exception e) {
				}*/
			}
			else
			{
				//用逗号分隔的URL，如保存表单及保存方法，功能日志可以捕获表单展示URL或者保存URL，调整顺序即可，这样可以不重复记录日志
				if((privilegeid != null && PrivilegeConfig.privilegeMap.get(privilegeid).getUri().indexOf(path)==0) || "true".equals(funondbclick))
				{
					try {
						try {
							/*Service sv = new Service();
							sv.setDataSourceName(Constant.getDatasource("security"));
							sv.put("logininfo", logininfo);
							sv.put("isnormal", "正常");
							if("1".equals(PrivilegeServiceImpl.privilegeMap.get(privilegeid).getFlag()))
							{
								sv.put("isButtonPri", true);
							}
							sv.put("funondbclick", funondbclick);
							sv.put("modulecode", privilegeid);
							sv.doService("titans.log.bs.LogServiceBS", "writemodulelog");*/
						} catch (Exception e) {
						}
					} catch (Exception e) {
					}
				}
			}
		}
		return hashpower;
	}
	
	/**
	 * 初始化所有功能信息信息到内存
	 * @throws Exception
	 */
	public  void initPrivilegeMap() throws Exception {
		List<Product> productlist = productMapper.selectList(null);
		for(Product product : productlist)
		{
			productMap.put(product.getProductid(), product);
		}
		List<Privilege> list=null;
		try {
			list = privilegeMapper.getAllPrivilege();

		}catch (Exception e){
			System.out.println(e.getMessage());
		}
		privilegeMap = new HashMap<String, Privilege>();
		privilegeListUriMap = new HashMap<String,Privilege>();
		privilegeListCheckUrlMap = new HashMap<String,Privilege>();
		for(Privilege privilege : list)
		{
			if(privilege.getParentid() == null || "".equals(privilege.getParentid()))
			{
				privilege.setParentid("0");
			}
			
			if(!privilegeListParentidMap.containsKey(privilege.getParentid()))
			{
				privilegeListParentidMap.put(privilege.getParentid(), new ArrayList<Privilege>());
			}
			privilegeListParentidMap.get(privilege.getParentid()).add(privilege);
			
			privilegeMap.put(privilege.getPrivilegeid(), privilege);
			privilegeCodeMap.put(privilege.getCode(), privilege);
			//以权限的URI资源作为KEY，用于过滤用户的功能权限
			if(privilege.getUri() != null && !"".equals(privilege.getUri()))
			{
				for(String url : privilege.getUri().split(","))
				{
					privilegeListUriMap.put(url, privilege);
					if(url.indexOf("?")>0)
					{
						privilegeListUriMap.put(url.split("\\?")[0], null);
					}
				}
			}
			if(privilege.getCheckurl() != null && !"".equals(privilege.getCheckurl()))
			{
				for(String url : privilege.getCheckurl().split(","))
				{
					privilegeListCheckUrlMap.put(url, privilege);
					if(url.indexOf("?")>0)
					{
						privilegeListCheckUrlMap.put(url.split("\\?")[0], null);
					}
				}
			}
		}
		for(Privilege privilege : list)
		{
			String fullname = productMap.get(privilege.getProductid()).getName() + "_" + getFullname(privilege);
			privilegeFullNameMap.put(fullname, privilege);
			privilegeMap.get(privilege.getPrivilegeid()).setFullname(fullname);
		}
		
		Iterator iter = privilegeListParentidMap.keySet().iterator();
	    while (iter.hasNext()) {     
            String key = (String) iter.next();     
            List<Privilege> plist = privilegeListParentidMap.get(key); 
            if(plist.size()>0 && "1".equals(plist.get(0).getFlag()))
            {
            	for(Privilege privilege : plist)
            	{
            		String eventname = "";
    				Map<String,String> buttonitem = new HashMap<String,String>();
            		String iconCls = (privilege.getIconcls() == null || privilege.getIconcls().toString().trim().equalsIgnoreCase("")) ? "null"
    						: privilege.getIconcls().toString().trim();
    				String ename = privilege.getEventname() == null ? "" : privilege.getEventname().toString().trim();
    				String groupname = privilege.getRecommend() == null ? ""
    						: privilege.getRecommend().toString().trim();
    				if (ename.trim().equalsIgnoreCase(""))
    					continue;
    				if (eventname.trim().equalsIgnoreCase(""))
    					eventname = privilege.getName() + "," + privilege.getCode() + "," + iconCls + "," + ename;
    				else
    					eventname = eventname + ";" + privilege.getName() + "," + privilege.getCode() + ","
    							+ iconCls + "," + ename;
    				if (!groupname.trim().equalsIgnoreCase(""))
    					eventname = eventname + "," + groupname;
    				buttonitem.put("privilegeid", privilege.getPrivilegeid());
    				buttonitem.put("eventname", eventname);
    				
    				if(buttonsStrMap.get(privilege.getParentid()) == null)
    				{
    					buttonsStrMap.put(privilege.getParentid(), new ArrayList<Map<String,String>>());
    				}
    				buttonsStrMap.get(privilege.getParentid()).add(buttonitem);
            	}
            }
        }  
	    
	/*    while(Singlelogin.serverPort == null)
	    {
	    	for(Product product : productlist)
			{
				productMap.put(product.getProductid(), product);
				try {
					HttpClientUtil httpclient = new HttpClientUtil(product.getUri());
					httpclient.executeHttpMethod("/", null);
				} catch (Exception e) {
				}
			}
	    }*/
	}
	
	private static String getFullname(Privilege privilege)
	{
		String fullname="";
		if(privilege.getParentid() == null || "".equals(privilege.getParentid()) || "0".equals(privilege.getParentid()))
		{
			return privilege.getName();
		}
		else
		{
			Privilege parentprivilege = privilegeMap.get(privilege.getParentid());
			if(privilegeMap.get(privilege.getParentid())!=null){
				fullname=getFullname(parentprivilege) + "_" + privilege.getName();
			}
			return fullname;
		}
	}
	/**
	 * 获得用户list
	 * @param organid 机构id
	 * @return
	 * @throws Exception 
	 *//*
	public static List getUserList(String filter) throws Exception 
	{
		List list=SecurityFactory.getInstance().getUserList(filter);
		return list;
	}
	*//**
	 * 获得用户list
	 * @param organid 机构id
	 * @return
	 * @throws Exception 
	 *//*
	public static boolean isLdUnit(String filter) throws Exception 
	{
		return SecurityFactory.getInstance().isLdUnit(filter);
	}
	*//**
	 * 增加临时用户
	 * @param organid 机构id
	 * @return
	 * @throws Exception 
	 *//*
	public static boolean addtempuser(String username, String idnum) throws Exception 
	{
		return SecurityFactory.getInstance().adduser(username, idnum);
	}
	*//**
	 * 删除期临时账号
	 * @throws Exception
	 *//*
	public static void deltempuser() throws Exception {
		SecurityFactory.getInstance().deltempuser();
	}
	*//**lic
	 * 获得某类型的消息下，拥有对应模块权限的用户id，拼接成消息用的key_range
	 * @param key_type 消息管理的类型
	 * @return
	 * @throws Exception 
	 *//*
	public static String getPrivilegeidUser(String key_type)  throws Exception {
		String key_range=SecurityFactory.getInstance().getPrivilegeidUser(key_type);
		return key_range;
	}
	*//**
	 * 删除消息消息
	 * @param message
	 * @throws Exception
	 *//*
	public static void delMassageToUser(String  key_type,String userid) throws Exception{
		SecurityFactory.getInstance().delMassageToUser(key_type, userid);
	}*/
}
