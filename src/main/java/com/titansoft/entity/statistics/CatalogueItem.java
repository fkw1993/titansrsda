package com.titansoft.entity.statistics;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;



@TableName(value = "t_tj_catalogueitem")
public class CatalogueItem {
	@TableId
	private String id;
	private String idnumber;
	private String unitname;
	private String department;
	private String name;
	
	private String sortPiece1="0";//各个分类份数
	private String sortPiece2="0";
	private String sortPiece3="0";
	private String sortPiece4_1="0";
	private String sortPiece4_2="0";
	private String sortPiece4_3="0";
	private String sortPiece4_4="0";
	private String sortPiece5="0";
	private String sortPiece6="0";
	private String sortPiece7="0";
	private String sortPiece8="0";
	private String sortPiece9_1="0";
	private String sortPiece9_2="0";
	private String sortPiece9_3="0";
	private String sortPiece9_4="0";
	private String sortPiece10="0";

	private String sortPnum1="0";//各个分类页数
	private String sortPnum2="0";
	private String sortPnum3="0";
	private String sortPnum4_1="0";
	private String sortPnum4_2="0";
	private String sortPnum4_3="0";
	private String sortPnum4_4="0";
	private String sortPnum5="0";
	private String sortPnum6="0";
	private String sortPnum7="0";
	private String sortPnum8="0";
	private String sortPnum9_1="0";
	private String sortPnum9_2="0";
	private String sortPnum9_3="0";
	private String sortPnum9_4="0";
	private String sortPnum10="0";
	private static final String tablenameofpo = "t_tj_catalogueitem";//对应数据库表名

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getSortPiece1() {
		return sortPiece1;
	}
	public void setSortPiece1(String sortPiece1) {
		this.sortPiece1 = sortPiece1;
	}
	public String getSortPiece2() {
		return sortPiece2;
	}
	public void setSortPiece2(String sortPiece2) {
		this.sortPiece2 = sortPiece2;
	}
	public String getSortPiece3() {
		return sortPiece3;
	}
	public void setSortPiece3(String sortPiece3) {
		this.sortPiece3 = sortPiece3;
	}
	public String getSortPiece4_1() {
		return sortPiece4_1;
	}
	public void setSortPiece4_1(String sortPiece4_1) {
		this.sortPiece4_1 = sortPiece4_1;
	}
	public String getSortPiece4_2() {
		return sortPiece4_2;
	}
	public void setSortPiece4_2(String sortPiece4_2) {
		this.sortPiece4_2 = sortPiece4_2;
	}
	public String getSortPiece4_3() {
		return sortPiece4_3;
	}
	public void setSortPiece4_3(String sortPiece4_3) {
		this.sortPiece4_3 = sortPiece4_3;
	}
	public String getSortPiece4_4() {
		return sortPiece4_4;
	}
	public void setSortPiece4_4(String sortPiece4_4) {
		this.sortPiece4_4 = sortPiece4_4;
	}
	public String getSortPiece5() {
		return sortPiece5;
	}
	public void setSortPiece5(String sortPiece5) {
		this.sortPiece5 = sortPiece5;
	}
	public String getSortPiece6() {
		return sortPiece6;
	}
	public void setSortPiece6(String sortPiece6) {
		this.sortPiece6 = sortPiece6;
	}
	public String getSortPiece7() {
		return sortPiece7;
	}
	public void setSortPiece7(String sortPiece7) {
		this.sortPiece7 = sortPiece7;
	}
	public String getSortPiece8() {
		return sortPiece8;
	}
	public void setSortPiece8(String sortPiece8) {
		this.sortPiece8 = sortPiece8;
	}
	public String getSortPiece9_1() {
		return sortPiece9_1;
	}
	public void setSortPiece9_1(String sortPiece9_1) {
		this.sortPiece9_1 = sortPiece9_1;
	}
	public String getSortPiece9_2() {
		return sortPiece9_2;
	}
	public void setSortPiece9_2(String sortPiece9_2) {
		this.sortPiece9_2 = sortPiece9_2;
	}
	public String getSortPiece9_3() {
		return sortPiece9_3;
	}
	public void setSortPiece9_3(String sortPiece9_3) {
		this.sortPiece9_3 = sortPiece9_3;
	}
	public String getSortPiece9_4() {
		return sortPiece9_4;
	}
	public void setSortPiece9_4(String sortPiece9_4) {
		this.sortPiece9_4 = sortPiece9_4;
	}
	public String getSortPiece10() {
		return sortPiece10;
	}
	public void setSortPiece10(String sortPiece10) {
		this.sortPiece10 = sortPiece10;
	}
	public String getSortPnum1() {
		return sortPnum1;
	}
	public void setSortPnum1(String sortPnum1) {
		this.sortPnum1 = sortPnum1;
	}
	public String getSortPnum2() {
		return sortPnum2;
	}
	public void setSortPnum2(String sortPnum2) {
		this.sortPnum2 = sortPnum2;
	}
	public String getSortPnum3() {
		return sortPnum3;
	}
	public void setSortPnum3(String sortPnum3) {
		this.sortPnum3 = sortPnum3;
	}
	public String getSortPnum4_1() {
		return sortPnum4_1;
	}
	public void setSortPnum4_1(String sortPnum4_1) {
		this.sortPnum4_1 = sortPnum4_1;
	}
	public String getSortPnum4_2() {
		return sortPnum4_2;
	}
	public void setSortPnum4_2(String sortPnum4_2) {
		this.sortPnum4_2 = sortPnum4_2;
	}
	public String getSortPnum4_3() {
		return sortPnum4_3;
	}
	public void setSortPnum4_3(String sortPnum4_3) {
		this.sortPnum4_3 = sortPnum4_3;
	}
	public String getSortPnum4_4() {
		return sortPnum4_4;
	}
	public void setSortPnum4_4(String sortPnum4_4) {
		this.sortPnum4_4 = sortPnum4_4;
	}
	public String getSortPnum5() {
		return sortPnum5;
	}
	public void setSortPnum5(String sortPnum5) {
		this.sortPnum5 = sortPnum5;
	}
	public String getSortPnum6() {
		return sortPnum6;
	}
	public void setSortPnum6(String sortPnum6) {
		this.sortPnum6 = sortPnum6;
	}
	public String getSortPnum7() {
		return sortPnum7;
	}
	public void setSortPnum7(String sortPnum7) {
		this.sortPnum7 = sortPnum7;
	}
	public String getSortPnum8() {
		return sortPnum8;
	}
	public void setSortPnum8(String sortPnum8) {
		this.sortPnum8 = sortPnum8;
	}
	public String getSortPnum9_1() {
		return sortPnum9_1;
	}
	public void setSortPnum9_1(String sortPnum9_1) {
		this.sortPnum9_1 = sortPnum9_1;
	}
	public String getSortPnum9_2() {
		return sortPnum9_2;
	}
	public void setSortPnum9_2(String sortPnum9_2) {
		this.sortPnum9_2 = sortPnum9_2;
	}
	public String getSortPnum9_3() {
		return sortPnum9_3;
	}
	public void setSortPnum9_3(String sortPnum9_3) {
		this.sortPnum9_3 = sortPnum9_3;
	}
	public String getSortPnum9_4() {
		return sortPnum9_4;
	}
	public void setSortPnum9_4(String sortPnum9_4) {
		this.sortPnum9_4 = sortPnum9_4;
	}
	public String getSortPnum10() {
		return sortPnum10;
	}
	public void setSortPnum10(String sortPnum10) {
		this.sortPnum10 = sortPnum10;
	}
	public static String getTablenameofpo() {
		return tablenameofpo;
	}
	public String getUnitname() {
		return unitname;
	}
	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
