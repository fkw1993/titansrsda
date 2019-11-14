package com.titansoft.entity;


import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 人事档案过程信息
 */
@TableName(value = "t_da_process")
public class DaProcess {

	private String id;
	
	private String username;//操作人
	
	private String processmsg;//过程信息
	
	private String  processdate;//操作日期
	
	private String idnumber;//操作的干部身份证号

	private static final String tablenameofpo = "t_da_process";//对应数据库表名

	public DaProcess(String id, String username, String processmsg, String processdate, String idnumber) {
		super();
		this.id = id;
		this.username = username;
		this.processmsg = processmsg;
		this.processdate = processdate;
		this.idnumber = idnumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProcessmsg() {
		return processmsg;
	}

	public void setProcessmsg(String processmsg) {
		this.processmsg = processmsg;
	}

	public String getProcessdate() {
		return processdate;
	}

	public void setProcessdate(String processdate) {
		this.processdate = processdate;
	}

	public String getIdnumber() {
		return idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public static String getTablenameofpo() {
		return tablenameofpo;
	}

	
	

	
	
}
