package me.carpela.generator;

import me.carpela.generator.config.SqlMap;

/**
 * 
 * @author Hover Winter
 * @description 生成MyBatis配置文件的接口
 * 
 */
public interface MyBatisGenerator {

	public void generate(SqlMap sqlMap);
	
}
