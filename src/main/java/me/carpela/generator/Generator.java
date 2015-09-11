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
 * @mail <a href="mailto:carpela@163.com?subject=Generator����>����</a>
 * @description ����
 */
public class Generator {

	private static Logger logger = Logger.getLogger(Generator.class);
	/**
	 * @Parameter �����ļ���
	 * @function �������ã������ļ�
	 */
	public static void doGenerate(String filename)
	{
		
		Configuration config = null;
		ConfigParser parser = new XmlConfigParser();
		
		// ��ȡ����
		logger.info("���������ļ�:"+filename);
		config = parser.parse(filename);
		List<TableData> tds = null;
		
		// ֧�ֵ�����
		String dbType = config.getDb().getType();
		logger.info("������ݿ��б���Ϣ:"+dbType);
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
			logger.error("��֧�ֵ����ݿ�����");
			return;
		}
		
		MyBatisGenerator mg = new MyBatisGeneratorDefaultImpl(tds);
		// ����mybatis
		mg.generate(config.getSqlMap());
		logger.info("���ɵ�MyBatis�����ļ�Ŀ¼: "+config.getSqlMap().getTargetDir());
		
		JavaGenerator jg = new JavaGeneratorDefaultImpl(tds);
		// ����bean
		jg.generateBean(config.getJavaModel());
		logger.info("���ɵ�Java BeanĿ¼: "+config.getJavaModel().getTargetDir());
		// ����dao
		jg.generateDAO(config.getJavaDAO());
		logger.info("���ɵ�Java DAOĿ¼: "+config.getJavaDAO().getTargetDir());
		// ����mapper
		jg.generateMapper(config.getJavaMapper());
		logger.info("���ɵ�MyBatis MapperĿ¼: "+config.getJavaMapper().getTargetDir());
	}
	
	public static void main(String[] args)
	{
		doGenerate("generatorConfig.xml");
	}
}
