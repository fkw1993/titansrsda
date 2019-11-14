package com.titansoft.utils.util.extjs;

import com.titansoft.model.VO;
import com.titansoft.model.XsdObject;
import com.titansoft.model.extjs.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 表单工具类
 *
 * @author Administrator
 */
public class FormUtil {


    /**
     * 根据模板配置构造json对象
     *
     * @param list          模板对象
     * @param
     * @param values        值对象
     * @param themeStoreUrl 指同倒排URL对象(主题词标引)
     * @return json对象
     * @throws Throwable
     * @throws Exception
     */
    public static String ConvertFormFieldsJSON(List<VO> list, Object object, AbstractMap values, String themeStoreUrl) throws Throwable {
        List<AbstractField> listFields = new ArrayList<AbstractField>();
        AbstractField field;
        String xType;
        String cType;
        String vType;
        String fieldLabel;
        String name;
        String defaultValue;
        String value = null;
        Boolean readOnly;
        Boolean isLine;
        Boolean isCommon;
        Boolean isCommonitem;
        Integer maxLength;
        Integer width;
        Boolean allowBlank;
        String store;
        Integer row;
        Boolean isHide;
        String fileValue;
        String title;
        Boolean editable;
        String regex;
        String regexText;

        for (VO vo : list) {
            if (vo.get("code") == null) {
                continue;
            }

            if (object != null) {
                if (PoManager.getPoFieldValue(vo.get("code").toLowerCase(), object) != null && !"".equals(PoManager.getPoFieldValue(vo.get("code").toLowerCase(), object))) {
                    vo.put("defaultvalue", PoManager.getPoFieldValue(vo.get("code").toLowerCase(), object));
                }
            }
            xType = vo.get("fieldtype") != null ? vo.get("fieldtype").toString().trim() : "";
            cType = vo.get("controltype") != null ? vo.get("controltype").toString().trim() : "";
            vType = vo.get("fieldverify") != null ? vo.get("fieldverify").toString().trim() : "";
            fieldLabel = vo.get("name") != null ? vo.get("name").toString().trim() : "";
            name = vo.get("code") != null ? vo.get("code").toString().trim() : "";
            defaultValue = vo.get("defaultvalue") != null ? vo.get("defaultvalue").toString().trim() : "";
            value = values != null && values.get(name) != null ? formatstr(values.get(name).toString()) : "";
            if ((value == null || "".equals(value)) && !"".equals(defaultValue))
                value = defaultValue;
            value = "".equals(value) ? null : value;
            readOnly = vo.get("isreadonly") != null ? vo.get("isreadonly").toString().equalsIgnoreCase("1") : false;
            isLine = vo.get("iswhieline") != null ? vo.get("iswhieline").equalsIgnoreCase("1") : false;
            isCommon = vo.get("display") != null ? vo.get("display").equalsIgnoreCase("true") : false;
            isCommonitem = vo.get("itemtype") != null ? vo.get("itemtype").equalsIgnoreCase("true") : false;
            maxLength = vo.get("fieldlength") != null && !"".equals(vo.get("fieldlength"))
                    ? Integer.parseInt(vo.get("fieldlength").toString()) : 0;
            width = vo.get("controlwidth") != null && !"".equals(vo.get("controlwidth"))
                    ? Integer.parseInt(vo.get("controlwidth").toString()) : 0;
            allowBlank = vo.get("mustinput") != null ? !vo.get("mustinput").equalsIgnoreCase("1") : false;
            store = vo.get("prompt") != null ? vo.get("prompt").toString().trim() : "";
            row = vo.get("textrow") != null && !"".equals(vo.get("textrow"))
                    ? Integer.parseInt(vo.get("textrow").toString()) : 1;
            isHide = vo.get("isinputhidden") != null ? vo.get("isinputhidden").equalsIgnoreCase("1") : false;
            title = vo.get("title") != null ? vo.get("title").toString().trim() : "";

            regex = vo.get("regex") != null ? vo.get("regex").toString().trim() : "";
            regexText = vo.get("regextext") != null ? vo.get("regextext").toString().trim() : "";

            editable = vo.get("editable") != null ? vo.get("editable").toString().equalsIgnoreCase("1") : false;
            String valueText = vo.get("valueText") != null ? vo.get(
                    "valueText").toString().trim() : "";
            String dataurl = vo.get("dataurl") != null ? vo.get("dataurl").toString()
                    .trim() : "";

            if (isHide) {
                field = new Hidden(name, value);
            } /*else if (fieldLabel.equals("电子文件")) {
				fileValue = (values != null && values.get("filename") != null) ? 
						formatstr(values.get("filename").toString().trim()) : "";
				field = new FileButtonText(fieldLabel, "filename", fileValue, readOnly,
						isLine, isCommon,isCommonitem, 0, allowBlank, ">>>", null,
						"emailClick", name, value, "/",regex,regexText);
			} */ else if ("date".equals(xType)
                //|| fieldLabel.indexOf("日期") >= 0
                //|| fieldLabel.indexOf("时间") >= 0
            ) {
                field = new UDateField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, isCommonitem, maxLength, allowBlank);
            } else if ("buttontext".equals(cType)) {
                String buttonlabel = vo.get("buttonlabel") != null ? vo.get("buttonlabel").toString().trim() : "获取";
                field = new ButtonText(fieldLabel, name, value, readOnly,
                        isLine, isCommon, isCommonitem, maxLength, allowBlank, buttonlabel, null,
                        "getClick", regex, regexText);
            } else if ("combobuttontext".equals(cType)) {
                field = new ComboButtonText(fieldLabel, name, value, readOnly,
                        isLine, isCommon, isCommonitem, maxLength, allowBlank,
                        themeStoreUrl, "主题词标引", null, "themeClick", true, regex, regexText);
            } else if ("combox".equals(cType)) {
                if (store.split("[$]").length > 1) {
					/*Service sv = new Service();
					sv.setDataSourceName(ConstantUtil.DB_NAME);
					String[] comboxsqlsplit=store.split("[$]");
					try
					{
						sv.put("tablename", comboxsqlsplit[0].trim());
						sv.put("fieldlist", comboxsqlsplit[1].trim());
						sv.put("filter", comboxsqlsplit.length>2?comboxsqlsplit[2].trim():"1=1");
						sv.doService("com.wd.frame.base.bs.BaseBS", "getcombox");
						store = (String) sv.get("result");
					}catch(Exception e)
					{
						System.out.println(e.getMessage());
					}*/
                }
                field = new ComboBox(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, editable, getStore(store));
            } else if (cType.trim().equalsIgnoreCase("checkbox")) {
                boolean checked = false;
                if ("true".trim().equalsIgnoreCase(value))
                    checked = true;
                field = new Checkbox(fieldLabel, name, "true", readOnly,
                        isLine, isCommon, checked);
            } else if (cType.trim().equalsIgnoreCase("combotree")) {
                field = new ComboTree(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, editable, dataurl);
                ((ComboTree) field).setValueText(valueText);
            } else if (row > 1 || "buttontextarea".equals(cType)) {
                if ("buttontextarea".equals(cType)) {
                    String buttonlabel = vo.get("buttonlabel") != null ? vo.get("buttonlabel").toString().trim() : "提取保管期限鉴定值";
                    field = new ButtonTextArea(fieldLabel, name, value, readOnly, isLine,
                            isCommon, isCommonitem, maxLength, allowBlank, buttonlabel, row, null, "verifyClick", regex, regexText);
                } else {
                    field = new TextArea(fieldLabel, name, value, readOnly, isLine,
                            isCommon, isCommonitem, maxLength, allowBlank, row);
                }
            } else if ("double".equals(xType)) {
                field = new NumberField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, isCommonitem, maxLength, allowBlank);
            } else if ("int".equals(xType)) {
                field = new NumberField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, isCommonitem, maxLength, allowBlank,
                        new Integer(0), new Double(0), null);
            } else if ("fileno".equals(vType)) {
                field = new FNTextField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, isCommonitem, maxLength, allowBlank, regex, regexText);
            } else if ("radiogroup".equals(cType)) {
                field = new Radiogroup(fieldLabel, getRadio(store, vo), "2", isLine, name);
            } else if (cType.trim().equalsIgnoreCase("password")) {
                field = new PasswordField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else {
                field = new TextField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, isCommonitem, maxLength, allowBlank, regex, regexText);
            }
            field.setTitle(title);
            field.setWidth(width);
            if (!"".equals(vType))
                field.setVtype(vType);

            if (!"".equals(regex))
                field.setRegex(regex);
            if (!"".equals(regexText))
                field.setRegexText(regexText);

            if (!"".equals(vo.get("groupname"))) {
                field.setGroupname(vo.get("groupname"));
                field.setGroupcollapsed(vo.get("groupcollapsed"));

            }
            field.setGroupcolumn(vo.get("groupcolumn"));
            listFields.add(field);
        }
        StringBuffer json = new StringBuffer("{");
        json.append(AbstractField.toJson(listFields));
        json.append("}");

        return json.toString();
    }


    /**
     * 根据模板配置构造jason对象----针对普通模块表单构造(数据管理模块除外)
     *
     * @param list    模板对象
     * @param
     * @param hiddens 隐藏对象
     * @param type    操作对象 增加,修改，删除
     * @return json对象
     * @throws Exception
     * @throws Throwable
     */
    public static String NotButtonConvertJSON(List list, Object po, List hiddens, String type) {
        List<AbstractField> listFields = new ArrayList<AbstractField>();
        AbstractField field;
        String xType;
        String fieldLabel;
        String name;
        String defaultValue;
        String value = null;
        Boolean readOnly;
        Boolean editable;
        Boolean isLine;
        Boolean isCommon;
        Integer maxLength;
        Boolean allowBlank;
        Boolean isCombo;
        String store;
        Boolean isTheme;
        Integer row;
        Boolean isBtn;
        Boolean isHide;
        String fileValue = "";
        String controltype = "";// 控件类型
        String comboxdata = ""; // 下拉框值
        String comboxsql = ""; // 下拉框数据
        String mulvalue = ""; // 复选择框与单选择框值
        String dataurl = "";//针对下啦树

        String eventname;//事件名
        String buttondesc;//按钮描述
        Integer fieldwidth; //控件宽度
        String fieldverify;
        String valueText;//下拉树文本值
        Boolean checked = false;// 单选框与多选框选择状态
        for (Object obj : list) {
            VO vo = (VO) obj;
            xType = vo.get("fieldtype") != null ? vo.get("fieldtype")
                    .toString().trim() : "";
            fieldLabel = vo.get("fieldname") != null ? vo.get("fieldname")
                    .toString().trim() : "";
            name = vo.get("fieldcode") != null ? vo.get("fieldcode").toString()
                    .trim().toLowerCase() : "";
            defaultValue = vo.get("defaultvalue") != null ? vo.get(
                    "defaultvalue").toString().trim() : "";
            valueText = vo.get("valueText") != null ? vo.get(
                    "valueText").toString().trim() : "";
            try {
                value = po != null && getPoFieldValue(name, po) != null ? formatstr(getPoFieldValue(name, po).toString()) : "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ((value == null || "".equals(value)) && !"".equals(defaultValue))
                value = defaultValue;
            value = "".equals(value) ? null : value;
            readOnly = vo.get("isreadonly") != null ? vo.get("isreadonly")
                    .toString().equalsIgnoreCase("true") : false;

            editable = vo.get("editable") != null ? vo.get("editable")
                    .toString().equalsIgnoreCase("true") : false;
            isLine = vo.get("iswhieline") != null ? vo.get("iswhieline")
                    .equalsIgnoreCase("true") : false;
            isCommon = vo.get("display") != null ? vo.get("display")
                    .equalsIgnoreCase("true") : false;
            maxLength = vo.get("fieldlength") != null
                    && !"".equals(vo.get("fieldlength")) ? Integer.parseInt(vo
                    .get("fieldlength").toString()) : 0;
            allowBlank = vo.get("mustinput") != null ? !vo.get("mustinput")
                    .equalsIgnoreCase("true") : false;
            isCombo = vo.get("promptcheck") != null ? vo.get("promptcheck")
                    .equalsIgnoreCase("true") : false;
            store = vo.get("prompt") != null ? vo.get("prompt").toString()
                    .trim() : "";
            comboxdata = vo.get("comboxdata") != null ? vo.get("comboxdata")
                    .toString().trim() : "";
            controltype = vo.get("controltype") != null ? vo.get("controltype")
                    .toString().trim() : "";
            comboxsql = vo.get("comboxsql") != null ? vo.get("comboxsql")
                    .toString().trim() : "";
            checked = vo.get("checked") != null ? vo.get("checked").toString()
                    .equalsIgnoreCase("true") : false;
            mulvalue = vo.get("mulvalue") != null ? vo.get("mulvalue")
                    .toString().trim() : "";

            fieldwidth = vo.get("fieldwidth") != null
                    && !"".equals(vo.get("fieldwidth")) ? Integer.parseInt(vo
                    .get("fieldwidth").toString()) : 5;

            eventname = vo.get("eventname") != null ? vo.get("eventname")
                    .toString().trim() : "";
            buttondesc = vo.get("buttondesc") != null ? vo.get("buttondesc")
                    .toString().trim() : "";
            fieldverify = vo.get("fieldverify") != null ? vo.get("fieldverify").toString()
                    .trim() : "";
            dataurl = vo.get("dataurl") != null ? vo.get("dataurl").toString()
                    .trim() : "";
            if (!comboxdata.trim().equalsIgnoreCase(""))
                store = comboxdata;
            else if (!comboxdata.trim().equalsIgnoreCase(""))
                store = comboxdata;
            else if (!comboxsql.trim().equalsIgnoreCase("")) // 通过sql语句构造json对象
            {
				/*try {
					String[] comboxsqlsplit=comboxsql.split("[$]");
					sv.put("tablename", comboxsqlsplit[0].trim());
					sv.put("fieldlist", comboxsqlsplit[1].trim());
					sv.put("filter", comboxsqlsplit[2].trim());
					sv.doService("com.wd.frame.base.bs.BaseBS", "getcombox");
					store = (String) sv.get("result");
				} catch (Exception e) {
					e.printStackTrace();
				}*/
            }
            isTheme = vo.get("dpts") != null ? vo.get("dpts").equalsIgnoreCase(
                    "true") : false;
            row = vo.get("textrow") != null && !"".equals(vo.get("textrow")) ? Integer
                    .parseInt(vo.get("textrow").toString())
                    : 1;
            isBtn = vo.get("inputtotal") != null ? vo.get("inputtotal")
                    .equalsIgnoreCase("true") : false;
            isHide = vo.get("isinputhidden") != null ? vo.get("isinputhidden")
                    .equalsIgnoreCase("true") : false;

            if (isHide) {
                field = new Hidden(name, value);
            } else if (fieldLabel.indexOf("电子文件") >= 0) {
                try {
                    fileValue = (po != null && getPoFieldValue("filename", po) != null) ? formatstr(getPoFieldValue("filename", po).toString().trim()) : "";
                } catch (Exception e) {
                    e.printStackTrace();
                }
                field = new FileButtonText(fieldLabel, "filename", fileValue,
                        readOnly, isLine, isCommon, 0, allowBlank,
                        ">>>", null, "emailClick", name, value, "/");
            } else if ("date".equals(xType) || fieldLabel.indexOf("日期") >= 0
                    || fieldLabel.indexOf("时间") >= 0) {
                field = new UDateField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else if (isBtn) {
                field = new ButtonText(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank, "获取", null,
                        "getClick");
            } else if (isCombo) {
                field = new ComboBox(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, getStore(store));
            } else if (controltype.trim().equalsIgnoreCase("textarea")) {
                field = new TextArea(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, row);
            } else if (controltype.trim().equalsIgnoreCase("buttontextarea")) {
                field = new ButtonTextArea(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, buttondesc, row, null, eventname);
            } else if (controltype.trim().equalsIgnoreCase("colorfield")) {
                field = new TextField("colorfield", fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else if ("double".equals(xType)) {
                field = new NumberField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else if ("int".equals(xType)) {
                field = new NumberField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank,
                        new Integer(0), new Double(0), null);
            } else if ("fileno".equals(fieldverify)) {
                field = new FNTextField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else if (controltype.trim().equalsIgnoreCase("buttonedit")) {
                field = new ButtonText(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank, buttondesc, null,
                        eventname);
            } else if (controltype.trim().equalsIgnoreCase("htmledit")) {
                field = new HtmlEditor(fieldLabel, name, value, readOnly, isLine,
                        isCommon, 20 * row);
            } else if (controltype.trim().equalsIgnoreCase("combox")) {
                field = new ComboBox(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, editable, getStore(store));
            } else if (controltype.trim().equalsIgnoreCase("combotree")) {
                field = new ComboTree(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, dataurl);
                ((ComboTree) field).setValueText(valueText);
            } else if (controltype.trim().equalsIgnoreCase("checkbox")) {
                /**
                 * @param fieldLabel
                 *            描述
                 * @param name
                 *            字段名
                 * @param value
                 *            字段值
                 * @param readOnly
                 *            只读
                 * @param isLine
                 *            换行
                 * @param isCommon
                 *            常用项
                 * @param checked
                 *            是否选中
                 */
                if (mulvalue.trim().equalsIgnoreCase(value))
                    checked = true;
                if (mulvalue.trim().equalsIgnoreCase(""))
                    field = new Checkbox(fieldLabel, name, value, readOnly,
                            isLine, isCommon, checked);
                else
                    field = new Checkbox(fieldLabel, name, mulvalue, readOnly,
                            isLine, isCommon, checked);
            } else if (controltype.trim().equalsIgnoreCase("radio")) {
                /**
                 * @param fieldLabel
                 *            描述
                 * @param name
                 *            字段名
                 * @param value
                 *            字段值
                 * @param readOnly
                 *            只读
                 * @param isLine
                 *            换行
                 * @param isCommon
                 *            常用项
                 * @param checked
                 *            是否选中
                 */
                if (mulvalue.trim().equalsIgnoreCase(value))
                    checked = true;
                if (mulvalue.trim().equalsIgnoreCase(""))
                    field = new Radio(fieldLabel, name, value, readOnly,
                            isLine, isCommon, checked);
                else
                    field = new Radio(fieldLabel, name, mulvalue, readOnly,
                            isLine, isCommon, checked);
            } else if (controltype.trim().equalsIgnoreCase("password")) {
                field = new PasswordField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else {
                field = new TextField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            }

            listFields.add(field);
        }

        if (hiddens != null) {
            for (java.util.Iterator it = hiddens.iterator(); it.hasNext(); ) {
                XsdObject xsd = (XsdObject) it.next();
                String fieldname = xsd.getFieldName() == null ? "" : xsd
                        .getFieldName().trim();
                String fieldvalue = xsd.getDefaultvalue() == null ? "" : xsd
                        .getDefaultvalue().trim();
                field = new Hidden(fieldname, fieldvalue);
                listFields.add(field);
            }
        }

        StringBuffer json = new StringBuffer("{");
        json.append(AbstractField.toJson(listFields));
        json.append("}");

        return json.toString();
    }

    /**
     * Method 通过一个类名及属性名则返回一个值
     *
     * @param fieldName //字段名
     * @param //类名
     * @return result 返回字段值
     */
    public static Object getPoFieldValue(String fieldName, Object po) {

        try {
            Class poClass = Class.forName(po.getClass().getName());
            fieldName = fieldName;
            String methodName = fieldName.trim().substring(0, 1).toUpperCase()
                    + fieldName.substring(1);
            String setMethod = "set" + methodName;
            String getMethod = "get" + methodName;
            Method meth = null;
            String fieldType = "string";
            Field field = null;
            Class partypes[] = new Class[1];
            Object fieldNameValue = null;
            try {
                field = poClass.getField(fieldName);
            } catch (Exception er) {
                field = null;
            }
            if (field != null) {
                fieldType = field.getType().getName().trim();
                if (fieldType.trim().toLowerCase().lastIndexOf("string") >= 0) {
                    partypes[0] = field.getType().getName().getClass();
                    meth = poClass.getMethod(getMethod, null);
                    fieldNameValue = meth.invoke(po, null);
                } else if (fieldType.trim().toLowerCase().equalsIgnoreCase("int")) {
                    partypes[0] = Integer.TYPE;
                    meth = poClass.getMethod(getMethod, null);
                    fieldNameValue = meth.invoke(po, null);
                    fieldNameValue = String.valueOf(fieldNameValue);
                } else if (fieldType.trim().toLowerCase().lastIndexOf("integer") >= 0) {
                    partypes[0] = Integer.class;
                    meth = poClass.getMethod(getMethod, null);
                    fieldNameValue = meth.invoke(po, null);
                    fieldNameValue = String.valueOf(fieldNameValue);
                } else if (fieldType.trim().toLowerCase().lastIndexOf("double") >= 0) {
                    partypes[0] = Double.TYPE;
                    meth = poClass.getMethod(getMethod, null);
                    fieldNameValue = meth.invoke(po, null);
                    fieldNameValue = String.valueOf(fieldNameValue);
                } else if (fieldType.trim().toLowerCase().lastIndexOf("date") >= 0) {
                    partypes[0] = java.util.Date.class;
                    meth = poClass.getMethod(getMethod, null);
                    fieldNameValue = meth.invoke(po, null);
                    fieldNameValue = String.valueOf(fieldNameValue)
                            .substring(0, 10);
                } else if (fieldType.trim().toLowerCase().lastIndexOf("long") >= 0) {
                    partypes[0] = Long.TYPE;
                    meth = poClass.getMethod(getMethod, null);
                    fieldNameValue = meth.invoke(po, null);
                    fieldNameValue = String.valueOf(fieldNameValue);
                }
            } else {
                meth = poClass.getMethod(getMethod, null);
                fieldNameValue = meth.invoke(po, null);
                fieldNameValue = String.valueOf(fieldNameValue);
            }
            if (fieldNameValue == null || fieldNameValue.equals("null"))
                fieldNameValue = "";
            return fieldNameValue;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * method jason格式化数据转换函数 包括回车符，换行符 ，‘’ “ “等
     *
     * @param str
     * @return
     */
    private static String formatstr(String str) {
        str = str.replace("\r\n", "\\\\r\\\\n");
        return str;
    }

    private static String getRadio(String oriData, VO vo) {
        String[] kv;
        StringBuffer store = new StringBuffer("[");
        boolean checked = false;
        for (String item : oriData.split("\\|")) {
            if ("".equals(item))
                continue;
            checked = false;
            if (store.length() > 1)
                store.append(",");
            kv = item.split("-");
            if (kv[1].equals(vo.get("defaultvalue"))) {
                checked = true;
            }
            store.append("{xtype:\"radio\",boxLabel:\"" + kv[0] + "\",name:\"" + vo.get("fieldcode") + "\",inputValue:\"" + kv[1] + "\",checked:" + checked + "}");
        }
        store.append("]");
        return store.toString();
    }

    private static String getStore(String oriData) {
        String[] kv;
        StringBuffer store = new StringBuffer("[");
        for (String item : oriData.split("\\|")) {
            if ("".equals(item))
                continue;

            if (store.length() > 1)
                store.append(",");

            kv = item.split("-");

            store.append("[\"");
            if (kv.length > 1)
                store.append(kv[1]);
            else
                store.append(kv[0]);
            store.append("\",\"");
            store.append(kv[0]);
            store.append("\"]");
        }
        store.append("]");
        return store.toString();
    }


    public static String ConvertJSON(List list, Object po, List hiddens, String type) {
        List<AbstractField> listFields = new ArrayList<AbstractField>();
        List<AbstractButton> listButtons = new ArrayList<AbstractButton>();
        AbstractField field;
        String xType;
        String fieldLabel;
        String name;
        String defaultValue;
        String value = null;
        Boolean readOnly;
        Boolean isLine;
        Boolean isCommon;
        Integer maxLength;
        Boolean allowBlank;
        Boolean isCombo;
        String store;
        Boolean isTheme;
        Integer row;
        Boolean isBtn;
        Boolean isHide;
        String fileValue;
        String controltype = "";// 控件类型
        String comboxdata = ""; // 下拉框值
        List comboxsql = null; // 下拉框数据
        String mulvalue = ""; // 复选择框与单选择框值
        String dataurl = "";//针对下啦树

        String eventname;//事件名
        String buttondesc;//按钮描述
        Integer fieldwidth; //控件宽度
        String fieldverify;
        String valueText;//下拉树文本值
        Boolean checked = false;// 单选框与多选框选择状态
        for (Object obj : list) {
            VO vo = (VO) obj;
            xType = vo.get("fieldtype") != null ? vo.get("fieldtype")
                    .toString().trim() : "";
            fieldLabel = vo.get("fieldname") != null ? vo.get("fieldname")
                    .toString().trim() : "";
            name = vo.get("fieldcode") != null ? vo.get("fieldcode").toString()
                    .trim().toLowerCase() : "";
            defaultValue = vo.get("defaultvalue") != null ? vo.get(
                    "defaultvalue").toString().trim() : "";
            valueText = vo.get("valueText") != null ? vo.get(
                    "valueText").toString().trim() : "";
            value = po != null && getPoFieldValue(name, po) != null ? formatstr(getPoFieldValue(
                    name, po).toString())
                    : "";
            if ((value == null || "".equals(value)) && !"".equals(defaultValue))
                value = defaultValue;
            value = "".equals(value) ? null : value;
            readOnly = vo.get("isreadonly") != null ? vo.get("isreadonly")
                    .toString().equalsIgnoreCase("true") : false;
            isLine = vo.get("iswhieline") != null ? vo.get("iswhieline")
                    .equalsIgnoreCase("true") : false;
            isCommon = vo.get("display") != null ? vo.get("display")
                    .equalsIgnoreCase("true") : false;
            maxLength = vo.get("fieldlength") != null
                    && !"".equals(vo.get("fieldlength")) ? Integer.parseInt(vo
                    .get("fieldlength").toString()) : 0;
            allowBlank = vo.get("mustinput") != null ? !vo.get("mustinput")
                    .equalsIgnoreCase("true") : false;
            isCombo = vo.get("promptcheck") != null ? vo.get("promptcheck")
                    .equalsIgnoreCase("true") : false;
            store = vo.get("prompt") != null ? vo.get("prompt").toString()
                    .trim() : "";
            comboxdata = vo.get("comboxdata") != null ? vo.get("comboxdata")
                    .toString().trim() : "";
            controltype = vo.get("controltype") != null ? vo.get("controltype")
                    .toString().trim() : "";
            comboxsql = vo.get("comboxsql") != null ? (List) vo.getOBJ("comboxsql")
                    : null;
            checked = vo.get("checked") != null ? vo.get("checked").toString()
                    .equalsIgnoreCase("true") : false;
            mulvalue = vo.get("mulvalue") != null ? vo.get("mulvalue")
                    .toString().trim() : "";

            fieldwidth = vo.get("fieldwidth") != null
                    && !"".equals(vo.get("fieldwidth")) ? Integer.parseInt(vo
                    .get("fieldwidth").toString()) : 5;

            eventname = vo.get("eventname") != null ? vo.get("eventname")
                    .toString().trim() : "";
            buttondesc = vo.get("buttondesc") != null ? vo.get("buttondesc")
                    .toString().trim() : "";
            fieldverify = vo.get("fieldverify") != null ? vo.get("fieldverify").toString()
                    .trim() : "";
            dataurl = vo.get("dataurl") != null ? vo.get("dataurl").toString()
                    .trim() : "";
            if (!comboxdata.trim().equalsIgnoreCase(""))
                store = comboxdata;
            else if (!comboxdata.trim().equalsIgnoreCase(""))
                store = comboxdata;
            else if (comboxsql != null) // 通过sql语句构造json对象
            {
                if (comboxsql != null && comboxsql.size() > 0) {
                    for (java.util.Iterator it = comboxsql.iterator(); it.hasNext(); ) {
                        Map hashMap = (Map) it.next();
                        if (hashMap != null) {
                            String resultinner = "";
                            //String tmp="长期-长期|永久-永久|短期-短期|30年-30年|10年-10年";
                            //////////////////////start
                            Set set = hashMap.entrySet();
                            for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                                Map.Entry me = (Map.Entry) iter.next();
                                if (resultinner.trim().equalsIgnoreCase(""))
                                    resultinner = me.getValue().toString();
                                else
                                    resultinner = resultinner + "-" + me.getValue().toString();
                            }
                            //////////////////////end
                            if (store.trim().equalsIgnoreCase(""))
                                store = resultinner;
                            else
                                store = store + "|" + resultinner;
                        }

                    }
                }

            }
            isTheme = vo.get("dpts") != null ? vo.get("dpts").equalsIgnoreCase(
                    "true") : false;
            row = vo.get("textrow") != null && !"".equals(vo.get("textrow")) ? Integer
                    .parseInt(vo.get("textrow").toString())
                    : 1;
            isBtn = vo.get("inputtotal") != null ? vo.get("inputtotal")
                    .equalsIgnoreCase("true") : false;
            isHide = vo.get("isinputhidden") != null ? vo.get("isinputhidden")
                    .equalsIgnoreCase("true") : false;

            if (isHide) {
                field = new Hidden(name, value);
            } else if (fieldLabel.indexOf("电子文件") >= 0) {
                fileValue = (po != null && getPoFieldValue("filename", po) != null) ? formatstr(
                        getPoFieldValue("filename", po).toString().trim())
                        : "";
                field = new FileButtonText(fieldLabel, "filename", fileValue,
                        readOnly, isLine, isCommon, 0, allowBlank,
                        ">>>", null, "emailClick", name, value, "/");
            } else if ("date".equals(xType) || fieldLabel.indexOf("日期") >= 0
                    || fieldLabel.indexOf("时间") >= 0) {
                field = new UDateField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else if (isBtn) {
                field = new ButtonText(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank, "获取", null,
                        "getClick");
            } else if (isCombo) {
                field = new ComboBox(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, getStore(store));
            } else if (controltype.trim().equalsIgnoreCase("textarea")) {
                field = new TextArea(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, row);
            } else if (controltype.trim().equalsIgnoreCase("buttontextarea")) {
                field = new ButtonTextArea(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, buttondesc, row, null, eventname);
            } else if ("double".equals(xType)) {
                field = new NumberField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else if ("int".equals(xType)) {
                field = new NumberField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank,
                        new Integer(0), new Double(0), null);
            } else if ("fileno".equals(fieldverify)) {
                field = new FNTextField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else if (controltype.trim().equalsIgnoreCase("buttonedit")) {
                field = new ButtonText(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank, buttondesc, null,
                        eventname);
            } else if (controltype.trim().equalsIgnoreCase("htmledit")) {
                field = new HtmlEditor(fieldLabel, name, value, readOnly, isLine,
                        isCommon, 20 * row);
            } else if (controltype.trim().equalsIgnoreCase("combox")) {
                field = new ComboBox(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, getStore(store));
            } else if (controltype.trim().equalsIgnoreCase("combotree")) {
                field = new ComboTree(fieldLabel, name, value, readOnly, isLine,
                        isCommon, maxLength, allowBlank, dataurl);
                ((ComboTree) field).setValueText(valueText);
            } else if (controltype.trim().equalsIgnoreCase("checkbox")) {
                /**
                 * @param fieldLabel
                 *            描述
                 * @param name
                 *            字段名
                 * @param value
                 *            字段值
                 * @param readOnly
                 *            只读
                 * @param isLine
                 *            换行
                 * @param isCommon
                 *            常用项
                 * @param checked
                 *            是否选中
                 */
                if (mulvalue.trim().equalsIgnoreCase(value))
                    checked = true;
                if (mulvalue.trim().equalsIgnoreCase(""))
                    field = new Checkbox(fieldLabel, name, value, readOnly,
                            isLine, isCommon, checked);
                else
                    field = new Checkbox(fieldLabel, name, mulvalue, readOnly,
                            isLine, isCommon, checked);
            } else if (controltype.trim().equalsIgnoreCase("radio")) {
                /**
                 * @param fieldLabel
                 *            描述
                 * @param name
                 *            字段名
                 * @param value
                 *            字段值
                 * @param readOnly
                 *            只读
                 * @param isLine
                 *            换行
                 * @param isCommon
                 *            常用项
                 * @param checked
                 *            是否选中
                 */
                if (mulvalue.trim().equalsIgnoreCase(value))
                    checked = true;
                if (mulvalue.trim().equalsIgnoreCase(""))
                    field = new Radio(fieldLabel, name, value, readOnly,
                            isLine, isCommon, checked);
                else
                    field = new Radio(fieldLabel, name, mulvalue, readOnly,
                            isLine, isCommon, checked);
            } else if (controltype.trim().equalsIgnoreCase("password")) {
                field = new PasswordField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            } else {
                field = new TextField(fieldLabel, name, value, readOnly,
                        isLine, isCommon, maxLength, allowBlank);
            }

            listFields.add(field);
        }

        if (hiddens != null) {
            for (java.util.Iterator it = hiddens.iterator(); it.hasNext(); ) {
                XsdObject xsd = (XsdObject) it.next();
                String fieldname = xsd.getFieldName() == null ? "" : xsd
                        .getFieldName().trim();
                String fieldvalue = xsd.getDefaultvalue() == null ? "" : xsd
                        .getDefaultvalue().trim();
                field = new Hidden(fieldname, fieldvalue);
                listFields.add(field);
            }
        }

        /*
         * if (!"add".equalsIgnoreCase(type)) listButtons.add(new Button("上一条",
         * "Back", null, "backClick", false));
         *
         * if (!"add".equalsIgnoreCase(type)) listButtons.add(new Button("下一条",
         * "Forward", null, "forwardClick", false));
         */
		/*if ("add".equalsIgnoreCase(type))
			try {
				listButtons.add(new MultiButton("保存", "Save", null,
						"saveClick", false, false, true, "连续录入", true, true,
						"序号递增"));
			} catch (Exception er) {
				System.out.println(er.getMessage());
			}*/

        if ("edit".equalsIgnoreCase(type) || "add".equalsIgnoreCase(type))
            listButtons.add(new Button("保存", "Save", null, "saveClick",
                    false));

        listButtons.add(new Button("返回", "Return", null, "returnClick", false));

        return AbstractForm.toJson(listFields, listButtons);
    }
}
