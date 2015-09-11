package me.carpela.generator.mybatis;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.internal.TestFreeMarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.carpela.generator.Generator;
import me.carpela.generator.MyBatisGenerator;
import me.carpela.generator.config.SqlMap;
import me.carpela.generator.data.TableData;
import me.carpela.generator.type.JavaTypeResolver;

/**
 * @description 用于生成mybatis配置文件
 * @author Hover Winter
 *
 */
public class MyBatisGeneratorDefaultImpl implements MyBatisGenerator{

	private List<TableData> tds;
	
	public MyBatisGeneratorDefaultImpl(List<TableData> tds)
	{
		if(tds==null)
		{
			System.exit(-1);
		}
		this.tds = tds;
	}
	
	public void generate(SqlMap sqlMap) {
		for (TableData td : tds) {
			File outFile = new File(sqlMap.getTargetDir()+"/"+td.getTablename() + ".xml");
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			Configuration config = new Configuration();
			config.setDefaultEncoding("utf-8");
			config.setClassForTemplateLoading(MyBatisGeneratorDefaultImpl.class, "/");
			Template t = null;
			try {
				t = config.getTemplate(sqlMap.getTemplate());
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				// 输出数据到模板中，生成文件。
				t.process(td, out);
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
