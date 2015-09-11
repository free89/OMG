package me.carpela.generator.mybatis;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generator.TestFreeMarker;

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
import me.carpela.generator.data.TableData;
import me.carpela.generator.type.JavaTypeResolver;

public class MybatisGenerator {

	public void generate(String template, List<TableData> tds) {
		for (TableData td : tds) {
			File outFile = new File(td.getTablename() + ".xml");
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Configuration config = new Configuration();
			config.setDefaultEncoding("utf-8");
			config.setClassForTemplateLoading(MybatisGenerator.class, "/");
			Template t = null;
			try {
				t = config.getTemplate(template);
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
