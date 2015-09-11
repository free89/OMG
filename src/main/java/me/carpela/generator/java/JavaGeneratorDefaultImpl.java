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

import org.apache.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import me.carpela.generator.JavaGenerator;
import me.carpela.generator.config.JavaDAO;
import me.carpela.generator.config.JavaMapper;
import me.carpela.generator.config.JavaModel;
import me.carpela.generator.data.BeanData;
import me.carpela.generator.data.TableData;
import me.carpela.generator.dom.java.Field;
import me.carpela.generator.dom.java.FullyQualifiedJavaType;
import me.carpela.generator.dom.java.JavaVisibility;
import me.carpela.generator.dom.java.Method;
import me.carpela.generator.dom.java.Parameter;
import me.carpela.generator.dom.java.TopLevelClass;
import me.carpela.generator.mybatis.MyBatisGeneratorDefaultImpl;
import me.carpela.generator.type.JavaTypeResolver;
import me.carpela.generator.util.StringUtility;

/**
 * 
 * @author Hover Winter
 * @description 用于生成java文件
 *
 */
public class JavaGeneratorDefaultImpl implements JavaGenerator{

	private Logger logger = Logger.getLogger(JavaGeneratorDefaultImpl.class);
	private List<TableData> tds;
	
	public JavaGeneratorDefaultImpl(List<TableData> tds)
	{
		if(tds==null)
		{
			System.exit(-1);
		}
		this.tds = tds;
	}
	/**
	 * @description 通过DOM生成java bean
	 * @param namespace 包名
	 * @param dir 生成文件目录
	 */
	public void generateBeanByDom(JavaDAO javaDAO) {
		for (TableData td : tds) {
			FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
					javaDAO.getTargetPackage() + "." + td.getObjectName());
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
					e.printStackTrace();
				} catch (SecurityException e) {
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
				bw = new BufferedWriter(new FileWriter(javaDAO.getTargetDir()+"/"+td.getObjectName()+".java", false));
				bw.write(tlc.getFormattedContent());
		        bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		        
		}
	}
	
	/**
	 * @description 根据模板生成java bean
	 * @param template 模板文件名
	 * @param packagename 包名
	 * @param dir 生成文件目录
	 */
	public void generateBean(JavaModel javaModel) {
		List<BeanData> bds = TableToBeanDataWrapper.toBeanData(javaModel.getTargetPackage(), tds);
		for(BeanData bd: bds)
		{
			File outFile = new File(javaModel.getTargetDir()+"/"+bd.getObjectName() + ".java");
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
			config.setClassForTemplateLoading(JavaGeneratorDefaultImpl.class, "/");
			Template t = null;
			try {
				t = config.getTemplate(javaModel.getTemplate());
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
	
	/**
	 * @description 生成DAO
	 * @param template 模板文件名
	 * @param daoPackageName 包名
	 * @param dir 目录名
	 */
	public void generateDAO(JavaDAO javaDAO)
	{
		for (TableData td : tds) {
			File outFile = new File(javaDAO.getTargetDir()+"/"+td.getDaoname() + ".java");
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			Map<String,String> data = new HashMap<String,String>();
			data.put("objectName", td.getObjectName());
			data.put("daoName", td.getDaoname());
			data.put("namespace", td.getNamespace());
			data.put("daoPackageName", javaDAO.getTargetPackage());
			
			Configuration config = new Configuration();
			config.setDefaultEncoding("utf-8");
			config.setClassForTemplateLoading(JavaGeneratorDefaultImpl.class, "/");
			Template t = null;
			try {
				t = config.getTemplate(javaDAO.getTemplate());
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
	
	/**
	 * @description 生成DAO
	 * @param template 模板文件
	 * @param daoPackageName 包名
	 * @param dir 目录名
	 */
	public void generateMapper(JavaMapper javaMapper)
	{
		for (TableData td : tds) {
			File outFile = new File(javaMapper.getTargetDir()+"/"+td.getMappername() + ".java");
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			Map<String,String> data = new HashMap<String,String>();
			data.put("objectName", td.getObjectName());
			data.put("mapperName", td.getMappername());
			data.put("namespace", td.getNamespace());
			data.put("mapperPackageName", javaMapper.getTargetPackage());
			
			Configuration config = new Configuration();
			config.setDefaultEncoding("utf-8");
			config.setClassForTemplateLoading(JavaGeneratorDefaultImpl.class, "/");
			Template t = null;
			try {
				t = config.getTemplate(javaMapper.getTemplate());
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
