package generator;

import java.io.InputStream;

import me.carpela.generator.config.Configuration;
import me.carpela.generator.config.xml.XmlConfigParser;

public class Main {

	public static void main(String[] args) throws Exception
	{
		XmlConfigParser parser = new XmlConfigParser();
		InputStream is = Main.class.getClassLoader().getResourceAsStream("generatorConfig.xml");
		Configuration config = parser.parse(is);
		System.out.println(config.getJavaModel().getTargetDir());
		System.out.println(config.getSqlMap().getTargetPackage());
		System.out.println(config.getDb().getConfigURL());
	}
}
