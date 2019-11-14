package com.titansoft.model;

import java.util.HashMap;
import java.util.Set;

/**
 * 
 *
 * @author 珠海·李正球
 * @version 2.0  2008-10-27
 * 
 */
public class VO {
	
	HashMap vomap=new HashMap();
	/**
	 * 装载对象
	 * @param key 键名
	 * @param o 键值对象
	 */
	public void put(String key ,Object o){
		vomap.put(key ,o);
		
	}
	/**
	 * 得到对象的字符串值
	 * @param key 键名
	 * @return 返回字符串
	 */
	public String get(String key){
		String str=null;
		Object obj=vomap.get(key);
		if(null!=obj){
			str=obj.toString();
		}
		return  str;
	}
	public void put$(String key ,Object o){
		vomap.put(key ,o);
		
	}
	/**
	 * 得到对象的字符串值
	 * @param key 键名
	 * @return 返回字符串
	 */
	public String get$(String key){
		String str=null;
		Object obj=vomap.get(key);
		if(null!=obj){
			str=obj.toString();
		}
		return  str;
	}
	/**
	 * 得到对象
	 * @param key 键名
	 * @return 返回得到的对象
	 */
	public Object getOBJ(String key){
		return vomap.get(key);
	}
	/**
	 * 判断是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return vomap.isEmpty();
	}
	/**
	 * 将VO转成Map
	 * @return
	 */
	public HashMap toMap(){
		return vomap;
	}
	/**
	 * 设置VO的map对象，即用map初始化该VO
	 * @param map
	 */
	public void setMap(HashMap map){
		this.vomap=map;
		
	}
	
	/**
	 * 设置VO的map对象，即用map初始化该VO
	 * @param map
	 */
	public void addVO(VO vo){
		this.vomap.putAll(vo.toMap());
		
	}
	
	
	public String getString(String key) {
		if (this.vomap.containsKey(key)) {
			Object obj = this.vomap.get(key);
			return obj != null ? obj.toString().trim() : "";
		}
		return null;
	}


	public boolean containsKey(String key) {
		return this.vomap.containsKey(key);
	}
	
	public Set<String> keySet() {
		return this.vomap.keySet();
	}
//	
	public Object getMap(){
		return vomap;
	}

}
