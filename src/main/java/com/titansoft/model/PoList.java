package com.titansoft.model;

import com.titansoft.entity.Po;

import java.util.List;

/**用于接收表单数据
 * @Author: Kevin
 * @Date: 2019/10/2 17:03
 */
public class PoList {
    private Po basicpo;
    private List<Po> educationPoList;
    private List<Po> workPoList;
    private List<Po> familyPoList;
    private List<Po> abroadPoList;
    private List<Po> archivesKeepPoList;

    public List<Po> getEducationPoList() {
        return educationPoList;
    }

    public void setEducationPoList(List<Po> educationPoList) {
        this.educationPoList = educationPoList;
    }

    public List<Po> getWorkPoList() {
        return workPoList;
    }

    public void setWorkPoList(List<Po> workPoList) {
        this.workPoList = workPoList;
    }

    public List<Po> getFamilyPoList() {
        return familyPoList;
    }

    public void setFamilyPoList(List<Po> familyPoList) {
        this.familyPoList = familyPoList;
    }

    public List<Po> getAbroadPoList() {
        return abroadPoList;
    }

    public void setAbroadPoList(List<Po> abroadPoList) {
        this.abroadPoList = abroadPoList;
    }

    public List<Po> getArchivesKeepPoList() {
        return archivesKeepPoList;
    }

    public void setArchivesKeepPoList(List<Po> archivesKeepPoList) {
        this.archivesKeepPoList = archivesKeepPoList;
    }

    public Po getBasicpo() {
        return basicpo;
    }

    public void setBasicpo(Po basicpo) {
        this.basicpo = basicpo;
    }
}
