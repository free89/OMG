package me.carpela.generator.config;

/**
 * 
 * @author Hover Winter
 * @description 配置文件对象
 *
 */
public class DataBase {

	private String type;

	private String configURL;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConfigURL() {
		return configURL;
	}

	public void setConfigURL(String configURL) {
		this.configURL = configURL;
	}
}
