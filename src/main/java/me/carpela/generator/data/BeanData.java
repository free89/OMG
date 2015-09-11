package me.carpela.generator.data;

import java.util.Map;

/**
 * 
 * @author Hover Winter
 * @description 生成java bean的数据
 */
public class BeanData {

	private String packageName;
	/*
	 * FieldName -> FiledType
	 */
	private Map<String, String> fields;
	private String objectName;
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Map<String, String> getFields() {
		return fields;
	}
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}
