package generator.internal;

import java.sql.Types;

import me.carpela.generator.type.JavaTypeResolver;

public class ConstantField {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		JavaTypeResolver jtr = new JavaTypeResolver();

		int jdbcType = (int) Types.class.getField("VARCHAR").get(Types.class);
		System.out.println(jtr.calculateJavaType(jdbcType));
		
	}
}
