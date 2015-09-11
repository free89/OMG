package me.carpela.generator.config;

import java.util.List;

/**
 * 
 * @author Hover Winter
 * @description 配置文件对象
 *
 */
public class Configuration {

	private DataBase db;
	private JavaModel javaModel;
	private JavaDAO javaDAO;
	private SqlMap sqlMap;
	private JavaMapper javaMapper;
	private List<Table> tables;
	public DataBase getDb() {
		return db;
	}
	public void setDb(DataBase db) {
		this.db = db;
	}
	public JavaModel getJavaModel() {
		return javaModel;
	}
	public void setJavaModel(JavaModel javaModel) {
		this.javaModel = javaModel;
	}
	public JavaDAO getJavaDAO() {
		return javaDAO;
	}
	public void setJavaDAO(JavaDAO javaDAO) {
		this.javaDAO = javaDAO;
	}
	public SqlMap getSqlMap() {
		return sqlMap;
	}
	public void setSqlMap(SqlMap sqlMap) {
		this.sqlMap = sqlMap;
	}
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	public JavaMapper getJavaMapper() {
		return javaMapper;
	}
	public void setJavaMapper(JavaMapper javaMapper) {
		this.javaMapper = javaMapper;
	}
	
}
