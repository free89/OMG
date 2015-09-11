package me.carpela.generator.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import me.carpela.generator.data.BeanData;
import me.carpela.generator.data.TableData;
import me.carpela.generator.dom.java.Field;
import me.carpela.generator.dom.java.FullyQualifiedJavaType;
import me.carpela.generator.dom.java.JavaVisibility;
import me.carpela.generator.dom.java.Method;
import me.carpela.generator.dom.java.Parameter;
import me.carpela.generator.dom.java.TopLevelClass;
import me.carpela.generator.mybatis.MybatisGenerator;
import me.carpela.generator.type.JavaTypeResolver;
import me.carpela.generator.util.StringUtility;

public class JavaGenerator {

	public void generateBeanByDom(String namespace, List<TableData> tds) {
		for (TableData td : tds) {
			FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
					namespace + "." + td.getObjectName());
			TopLevelClass tlc = new TopLevelClass(fqjt);
			JavaVisibility jv = JavaVisibility.PUBLIC;
			
			for(String[] tmp : td.getFields().values())
			{
				java.lang.reflect.Field field;
				FullyQualifiedJavaType type = null;
				try {
					field = Types.class.getField(tmp[1]);
					int jdbcType = (int)field.getInt(Types.class);
					type = new JavaTypeResolver().calculateJavaType(jdbcType);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
				}
				
				Parameter parameter = new Parameter(type, StringUtility.camelName(tmp[0]));
				
				Field attr = new Field(StringUtility.camelName(tmp[0]), type);
				attr.setVisibility(jv);
				Method getMethod = new Method(StringUtility.camelName("get_"+tmp[0]));
				getMethod.addBodyLine("return this."+StringUtility.camelName(tmp[0])+";");
				getMethod.setVisibility(jv);
				Method setMethod = new Method(StringUtility.camelName("set_"+tmp[0]));
				setMethod.addParameter(parameter);
				setMethod.addBodyLine("this."+StringUtility.camelName(tmp[0])+"="+StringUtility.camelName(tmp[0])+";");
				setMethod.setVisibility(jv);
				tlc.addField(attr);
				tlc.addMethod(setMethod);
				tlc.addMethod(getMethod);
				tlc.setVisibility(jv);
				if(type.getImportList().size() != 0)
					tlc.addImportedType(type);
			}
			
			BufferedWriter bw;
			try {
				bw = new BufferedWriter(new FileWriter(td.getObjectName()+".java", false));
				bw.write(tlc.getFormattedContent());
		        bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		        
		}
	}
	
	public void generateBean(String template, String packagename, List<TableData> tds) {
		List<BeanData> bds = TableToBeanDataWrapper.toBeanData(packagename, tds);
		for(BeanData bd: bds)
		{
			File outFile = new File(bd.getObjectName() + ".java");
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
			config.setClassForTemplateLoading(JavaGenerator.class, "/");
			Template t = null;
			try {
				t = config.getTemplate(template);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				// 输出数据到模板中，生成文件。
				t.process(bd, out);
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void generateDAO(String template, String daoPackageName, List<TableData> tds)
	{
		for (TableData td : tds) {
			File outFile = new File(td.getDaoname() + ".java");
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
			
			Map<String,String> data = new HashMap<String,String>();
			data.put("objectName", td.getObjectName());
			data.put("daoName", td.getDaoname());
			data.put("namespace", td.getNamespace());
			data.put("daoPackageName", daoPackageName);
			
			Configuration config = new Configuration();
			config.setDefaultEncoding("utf-8");
			config.setClassForTemplateLoading(JavaGenerator.class, "/");
			Template t = null;
			try {
				t = config.getTemplate(template);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				// 输出数据到模板中，生成文件。
				t.process(data, out);
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
