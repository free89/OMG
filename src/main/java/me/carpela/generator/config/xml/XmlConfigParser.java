package me.carpela.generator.config.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import me.carpela.generator.config.ConfigParser;
import me.carpela.generator.config.Configuration;
import me.carpela.generator.config.DataBase;
import me.carpela.generator.config.JavaDAO;
import me.carpela.generator.config.JavaMapper;
import me.carpela.generator.config.JavaModel;
import me.carpela.generator.config.SqlMap;
import me.carpela.generator.config.Table;

/**
 * 
 * @author Hover Winter
 * @description xml形式的配置文件解析
 *
 */
public class XmlConfigParser implements ConfigParser{

	private static Logger logger = Logger.getLogger(XmlConfigParser.class);
	
	@Override
	public Configuration parse(String config)
	{
		InputStream is = XmlConfigParser.class.getClassLoader().getResourceAsStream(config);
		return parse(is);
	}
	
	@Override
	public Configuration parse(InputStream is)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Document doc = null;
		try {
			doc = builder.parse(is);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		
		Configuration config = new Configuration();
		
		Element root = doc.getDocumentElement();
		if(!root.getNodeName().equals("configuration"))
		{
			return null;
		}
		
		NodeList nodes = root.getChildNodes();
		List<Table> tables = new ArrayList<Table>();
		for(int i = 0; i < nodes.getLength(); i++)
		{
			Node node = nodes.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				if(node.getNodeName().equals("javaModel"))
				{
					config.setJavaModel(parseJavaModel(node));
				}else if(node.getNodeName().equals("javaDAO"))
				{
					config.setJavaDAO(parseJavaDAO(node));
				}else if(node.getNodeName().equals("sqlMap"))
				{
					config.setSqlMap(parseSqlMap(node));
				}else if(node.getNodeName().equals("db"))
				{
					config.setDb(parseDB(node));
				}else if(node.getNodeName().equals("table"))
				{
					tables.add(parseTable(node));
				}else if(node.getNodeName().equals("javaMapper"))
				{
					config.setJavaMapper(parseJavaMapper(node));
				}
			}
		}
		config.setTables(tables);
		if(! config.getJavaMapper().getTargetPackage().equals(config.getSqlMap().getTargetPackage()))
		{
			logger.warn("xml文件和mapper接口的命名空间不一致: mapper "+ config.getJavaMapper().getTargetPackage() + " xml "+config.getSqlMap().getTargetPackage());
		}
		
		return config;
		
	}
	
	// 解析 javamodel 标签
	private JavaModel parseJavaModel(Node node)
	{
		JavaModel javaModel = new JavaModel();
		Element ele = (Element) node;
		javaModel.setTargetDir(ele.getAttribute("targetDir"));
		javaModel.setTargetPackage(ele.getAttribute("targetPackage"));
		javaModel.setTemplate(ele.getAttribute("template"));
		return javaModel;
	}
	
	// 解析javaDAO标签
	private JavaDAO parseJavaDAO(Node node)
	{
		JavaDAO javaDAO = new JavaDAO();
		Element ele = (Element) node;
		javaDAO.setTargetDir(ele.getAttribute("targetDir"));
		javaDAO.setTargetPackage(ele.getAttribute("targetPackage"));
		javaDAO.setTemplate(ele.getAttribute("template"));
		return javaDAO;
	}
	
	// 解析sqlMap标签
	private SqlMap parseSqlMap(Node node)
	{
		SqlMap sqlMap = new SqlMap();
		Element ele = (Element) node;
		sqlMap.setTargetDir(ele.getAttribute("targetDir"));
		sqlMap.setTargetPackage(ele.getAttribute("targetPackage"));
		sqlMap.setTemplate(ele.getAttribute("template"));
		return sqlMap;
	}
	
	// 解析javaMapper标签
	private JavaMapper parseJavaMapper(Node node)
	{
		JavaMapper javaMapper = new JavaMapper();
		Element ele = (Element) node;
		javaMapper.setTargetDir(ele.getAttribute("targetDir"));
		javaMapper.setTargetPackage(ele.getAttribute("targetPackage"));
		javaMapper.setTemplate(ele.getAttribute("template"));
		return javaMapper;
	}
	
	// 解析table标签
	private Table parseTable(Node node)
	{
		Table table = new Table();
		Element ele = (Element) node;
		table.setName(ele.getAttribute("name"));
		table.setObjectName(ele.getAttribute("objectName"));
		return table;
	}
	
	// 解析 db 标签
	private DataBase parseDB(Node node)
	{
		DataBase database = new DataBase();
		Element ele = (Element) node;
		database.setType(ele.getAttribute("type"));
		database.setConfigURL(ele.getAttribute("configURL"));
		return database;
	}
}
