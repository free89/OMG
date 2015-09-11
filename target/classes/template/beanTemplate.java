package ${packageName};

import java.util.Date;

public class ${objectName} {
	<#list fields?keys as field>
	
	private ${fields[field]} ${field};
	
	public ${fields[field]} get${field?cap_first}()
	{
		return ${field};
	}
	
	public void set${field?cap_first}(${fields[field]} ${field})
	{
		this.${field} = ${field};
	}
	</#list>
	
}
