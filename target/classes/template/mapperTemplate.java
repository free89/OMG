package ${mapperPackageName};

import java.util.List;

public interface ${mapperName} {

	public List<${objectName}> select(${objectName} object);
	
	public int update(${objectName} object);
	
	public int delete(${objectName} object);
	
	public int insert(${objectName} object);
	
	public int updateById(${objectName} object);
	
	public int deleteById(String id);
}
