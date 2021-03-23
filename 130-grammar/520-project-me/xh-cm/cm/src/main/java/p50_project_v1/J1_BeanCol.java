package p50_project_v1;

import java.util.Map;

public class J1_BeanCol {
	public String col_name;
	public String col_des;
	public String col_enum;
	public int col_type;
	public Map<String,Integer> enumMap;
	
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	public int getCol_type() {
		return col_type;
	}
	public void setCol_type(int col_type) {
		this.col_type = col_type;
	}
	public String getCol_des() {
		return col_des;
	}
	public void setCol_des(String col_des) {
		this.col_des = col_des;
	}
	public String getCol_enum() {
		return col_enum;
	}
	public void setCol_enum(String col_enum) {
		this.col_enum = col_enum;
	}
	public Map<String, Integer> getEnumMap() {
		return enumMap;
	}
	public void setEnumMap(Map<String, Integer> enumMap) {
		this.enumMap = enumMap;
	}
}
