package me.carpela.generator;

import me.carpela.generator.config.JavaDAO;
import me.carpela.generator.config.JavaMapper;
import me.carpela.generator.config.JavaModel;

/**
 * 
 * @author Hover Winter
 * @description ����Java�ļ��Ľӿ�
 * 
 */
public interface JavaGenerator {

	public void generateBean(JavaModel javaModel);
	public void generateDAO(JavaDAO javaDAO);
	public void generateMapper(JavaMapper javaMapper);
	
}
