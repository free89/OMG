package me.carpela.generator.config;

import java.io.InputStream;

/**
 * @author Hover Winter
 * @description ���ý����ӿ�
 */
public interface ConfigParser {

	public Configuration parse(InputStream is);

	public Configuration parse(String configFile);
	
}
