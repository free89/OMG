package me.carpela.generator.java;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.carpela.generator.data.BeanData;
import me.carpela.generator.data.TableData;
import me.carpela.generator.dom.java.FullyQualifiedJavaType;
import me.carpela.generator.type.JavaTypeResolver;

/**
 * 
 * @author Hover Winter
 * @description 将表信息转换成bean信息
 *
 */
public class TableToBeanDataWrapper {

	public static BeanData toBeanData(String packagename, TableData td)
	{
		BeanData bd = new BeanData();
		bd.setPackageName(packagename);
		bd.setObjectName(td.getObjectName());
		Map<String,String> data = new HashMap<String, String>();
		for(Map.Entry<String, String[]> entry: td.getFields().entrySet())
		{
			String name = entry.getKey();
			String[] value = entry.getValue();
			java.lang.reflect.Field field;
			String type = null;
			try {
				field = Types.class.getField(value[1]);
				int jdbcType = (int)field.getInt(Types.class);
				type = new JavaTypeResolver().calculateJavaType(jdbcType).getShortName();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (Exception e) {
			}
			data.put(name, type);
		}
		bd.setFields(data);
		return bd;
	}
	
	public static List<BeanData> toBeanData(String packagename, List<TableData> tds)
	{
		List<BeanData> bds = new ArrayList<BeanData>();
		for(TableData td: tds)
		{
			bds.add(toBeanData(packagename, td));
		}
		return bds;
	}
}
