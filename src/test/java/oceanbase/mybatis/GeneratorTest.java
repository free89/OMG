package oceanbase.mybatis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import me.carpela.generator.config.Configuration;
import me.carpela.generator.config.xml.XmlConfigParser;
import me.carpela.generator.data.TableData;
import me.carpela.generator.java.JavaGenerator;
import me.carpela.generator.mybatis.MybatisGenerator;
import me.carpela.generator.oceanbase.OceanBaseTableDataFetch;

public class GeneratorTest {

	public static void main(String[] args)
	{
		Configuration config = null;
		XmlConfigParser parser = new XmlConfigParser();
		
		config = parser.parse("generatorConfig.xml");
		
		List<TableData> tds = OceanBaseTableDataFetch.fetch(config);
		MybatisGenerator mg = new MybatisGenerator();
		JavaGenerator jg = new JavaGenerator();
		mg.generate(config.getSqlMap().getTemplate(), tds);
		jg.generateBean(config.getJavaModel().getTemplate(),config.getJavaModel().getTargetPackage(),tds);
		jg.generateDAO(config.getJavaDAO().getTemplate(), config.getJavaDAO().getTargetPackage(), tds);
	}
}
