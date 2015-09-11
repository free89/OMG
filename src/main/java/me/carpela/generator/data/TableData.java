package me.carpela.generator.data;

import java.util.Map;

/**
 * 
 * @author HoverWinter
 * @description 生成mybatis xml的数据
 */
public class TableData {

	private String namespace;
	private String daoname;
	private String mappername;
	private String objectName;
	private String tablename;
	private String parameterType;
	/*
	 *	FieldName -> [ColumnName, Type] 
	 */
	private Map<String, String[]> fields;
	public String getNamespace() {
		return namespace;
	}
	public String getDaoname() {
		return daoname;
	}
	public void setDaoname(String daoname) {
		this.daoname = daoname;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public Map<String, String[]> getFields() {
		return fields;
	}
	public void setFields(Map<String, String[]> fields) {
		this.fields = fields;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getMappername() {
		return mappername;
	}
	public void setMappername(String mappername) {
		this.mappername = mappername;
	}
}
