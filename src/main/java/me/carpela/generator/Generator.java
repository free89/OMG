package me.carpela.generator;

import java.util.List;

import org.apache.log4j.Logger;

import me.carpela.generator.config.ConfigParser;
import me.carpela.generator.config.Configuration;
import me.carpela.generator.config.xml.XmlConfigParser;
import me.carpela.generator.data.TableData;
import me.carpela.generator.java.JavaGeneratorDefaultImpl;
import me.carpela.generator.mybatis.MyBatisGeneratorDefaultImpl;
import me.carpela.generator.mysql.MySQLTableDataFetch;
import me.carpela.generator.oceanbase.OceanBaseTableDataFetch;

/**
 * @Author Hover Winter
 * @mail <a href="mailto:carpela@163.com?subject=Generator工具>反馈</a>
 * @description 主类
 */
public class Generator {

	private static Logger logger = Logger.getLogger(Generator.class);
	/**
	 * @Parameter 配置文件名
	 * @function 解析配置，生成文件
	 */
	public static void doGenerate(String filename)
	{
		
		Configuration config = null;
		ConfigParser parser = new XmlConfigParser();
		
		// 获取配置
		logger.info("解析配置文件:"+filename);
		config = parser.parse(filename);
		List<TableData> tds = null;
		
		// 支持的类型
		String dbType = config.getDb().getType();
		logger.info("获得数据库中表信息:"+dbType);
		if(dbType.equals("oceanbase"))
		{
			tds = OceanBaseTableDataFetch.fetch(config);
		}
		else if(dbType.equals("mysql"))
		{
			tds = MySQLTableDataFetch.fetch(config);
		}
		else
		{
			logger.error("不支持的数据库类型");
			return;
		}
		
		MyBatisGenerator mg = new MyBatisGeneratorDefaultImpl(tds);
		// 生成mybatis
		mg.generate(config.getSqlMap());
		logger.info("生成的MyBatis配置文件目录: "+config.getSqlMap().getTargetDir());
		
		JavaGenerator jg = new JavaGeneratorDefaultImpl(tds);
		// 生成bean
		jg.generateBean(config.getJavaModel());
		logger.info("生成的Java Bean目录: "+config.getJavaModel().getTargetDir());
		// 生成dao
		jg.generateDAO(config.getJavaDAO());
		logger.info("生成的Java DAO目录: "+config.getJavaDAO().getTargetDir());
		// 生成mapper
		jg.generateMapper(config.getJavaMapper());
		logger.info("生成的MyBatis Mapper目录: "+config.getJavaMapper().getTargetDir());
	}
	
	public static void main(String[] args)
	{
		doGenerate("generatorConfig.xml");
	}
}
