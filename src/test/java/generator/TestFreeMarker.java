package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.carpela.generator.data.TableData;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TestFreeMarker {

	public static void main(String[] args)
	{
		Configuration config = new Configuration();
		config.setDefaultEncoding("utf-8");
		config.setClassForTemplateLoading(TestFreeMarker.class, "/");
		Template t = null;
		try{
			t = config.getTemplate("mybatisTemplate.xml");
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
		File outFile = new File("D:/result.xml");
		Writer out = null;

		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Map map = new HashMap<String, Object>(); 
//		
//		
//        map.put("name", "蒙奇·D·路飞");  
//        map.put("country", "日本");  
//        map.put("city", "东京");  
//        map.put("time",new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()));
//        
//        
//        List<String> lists = new ArrayList<String>();
//        lists.add("hit");
//        lists.add("bit");
//        lists.add("mit");
//        map.put("lists", lists);
//        
//        map.put("namespace", "com.carpela.test");
        
        TableData td = new TableData();
        td.setNamespace("com.carpela.test.result");
        td.setParameterType("com.carpela.User");
        td.setTablename("user");
        Map<String, String[]> fields = new HashMap<String, String[]>();
        fields.put("name", new String[] { "name", "VARCHAR"});
        fields.put("password", new String[] {"password", "VARCHAR"});
        fields.put("contactEmail", new String[]{"contact_email", "VARCHAR"});
        
        td.setFields(fields);
        try {  
            //输出数据到模板中，生成文件。  
            t.process(td, out);  
            out.close();  
        } catch (TemplateException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		
	}
}
