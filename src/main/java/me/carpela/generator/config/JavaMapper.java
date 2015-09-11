package me.carpela.generator.config;

/**
 * 
 * @author Hover Winter
 * @description 配置文件对象
 *
 */
public class JavaMapper {

	private String targetPackage;
	private String targetDir;
	private String template;
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getTargetPackage() {
		return targetPackage;
	}
	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}
	public String getTargetDir() {
		return targetDir;
	}
	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}
}
