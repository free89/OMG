package me.carpela.generator;

import me.carpela.generator.config.JavaDAO;
import me.carpela.generator.config.JavaMapper;
import me.carpela.generator.config.JavaModel;

/**
 * 
 * @author Hover Winter
 * @description 生成Java文件的接口
 * 
 */
public interface JavaGenerator {

	public void generateBean(JavaModel javaModel);
	public void generateDAO(JavaDAO javaDAO);
	public void generateMapper(JavaMapper javaMapper);
	
}
