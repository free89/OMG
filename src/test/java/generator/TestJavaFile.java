package generator;

import me.carpela.generator.dom.java.FullyQualifiedJavaType;
import me.carpela.generator.dom.java.JavaVisibility;
import me.carpela.generator.dom.java.Method;
import me.carpela.generator.dom.java.Parameter;
import me.carpela.generator.dom.java.TopLevelClass;

public class TestJavaFile {

	public static void main(String[] args)
	{
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType("com.carpela.test.HelloWorld");
		System.out.println(fqjt.getPackageName());
		
		
		TopLevelClass tlc = new TopLevelClass(fqjt);
		
		Method method = new Method("test");
		Parameter parameter = new Parameter(new FullyQualifiedJavaType(String.class.getName()), "arg0");
		method.addParameter(parameter);
		JavaVisibility jv = JavaVisibility.PRIVATE;
		method.setVisibility(jv);
		method.addBodyLine("System.out.println('heh')");
		tlc.addMethod(method);
		
		System.out.println(tlc.getFormattedContent());
	}
}
