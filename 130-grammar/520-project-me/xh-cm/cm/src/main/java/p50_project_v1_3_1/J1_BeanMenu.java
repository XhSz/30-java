package p50_project_v1_3_1;

import java.util.ArrayList;
import java.util.List;

public class J1_BeanMenu {
	
	boolean isLeaf;
	List<J1_BeanMenu> children;
	int level;//相对于根节点的层级,根节点为0
	String menuCode;//主键标识全局唯一,不是柜面菜单码
	String menuId;//实现继承关系的，柜面显示的菜单码
	String menuNameZh;
	String menuNameEn;
	String menuJsonPath;
	String qryTrans;
	String addJsonPath;
	String addTrans;
	String editJsonPath;
	String editTrans;
	String delTrans;
	String qryTransIcore;
	String editTransIcore;
	String delTransIcore;
	String addTransIcore;
	
	public J1_BeanMenu(){
		super();
		children = new ArrayList<J1_BeanMenu>();
	}
	
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuNameZh() {
		return menuNameZh;
	}
	public void setMenuNameZh(String menuNameZh) {
		this.menuNameZh = menuNameZh;
	}
	public String getMenuNameEn() {
		return menuNameEn;
	}
	public void setMenuNameEn(String menuNameEn) {
		this.menuNameEn = menuNameEn;
	}
	public String getMenuJsonPath() {
		return menuJsonPath;
	}
	public void setMenuJsonPath(String menuJsonPath) {
		this.menuJsonPath = menuJsonPath;
	}
	public String getQryTrans() {
		return qryTrans;
	}
	public void setQryTrans(String qryTrans) {
		this.qryTrans = qryTrans;
	}
	public String getAddJsonPath() {
		return addJsonPath;
	}
	public void setAddJsonPath(String addJsonPath) {
		this.addJsonPath = addJsonPath;
	}
	public String getAddTrans() {
		return addTrans;
	}
	public void setAddTrans(String addTrans) {
		this.addTrans = addTrans;
	}
	public String getEditJsonPath() {
		return editJsonPath;
	}
	public void setEditJsonPath(String editJsonPath) {
		this.editJsonPath = editJsonPath;
	}
	public String getEditTrans() {
		return editTrans;
	}
	public void setEditTrans(String editTrans) {
		this.editTrans = editTrans;
	}
	public String getDelTrans() {
		return delTrans;
	}
	public void setDelTrans(String delTrans) {
		this.delTrans = delTrans;
	}
	public List<J1_BeanMenu> getChildren() {
		return children;
	}
	public void setChildren(List<J1_BeanMenu> children) {
		this.children = children;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getQryTransIcore() {
		return qryTransIcore;
	}

	public void setQryTransIcore(String qryTransIcore) {
		this.qryTransIcore = qryTransIcore;
	}

	public String getEditTransIcore() {
		return editTransIcore;
	}

	public void setEditTransIcore(String editTransIcore) {
		this.editTransIcore = editTransIcore;
	}

	public String getDelTransIcore() {
		return delTransIcore;
	}

	public void setDelTransIcore(String delTransIcore) {
		this.delTransIcore = delTransIcore;
	}

	public String getAddTransIcore() {
		return addTransIcore;
	}

	public void setAddTransIcore(String addTransIcore) {
		this.addTransIcore = addTransIcore;
	}
}
