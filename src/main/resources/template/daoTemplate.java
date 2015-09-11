package ${daoPackageName};

import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class ${daoName} {

	private SqlSession session;
	
	public List<${objectName}> select(${objectName} object)
	{
		return session.selectList("${namespace}.${daoName}.select", object);
	}
	
	public int update(${objectName} object)
	{
		return session.update("${namespace}.${daoName}.update", object);
	}
	
	public int delete(${objectName} object)
	{
		return session.delete("${namespace}.${daoName}.delete", object);
	}
	
	public int insert(${objectName} object)
	{
		return session.insert("${namespace}.${daoName}.insert", object);
	}
	
	public int updateById(${objectName} object)
	{
		return session.update("${namespace}.${daoName}.updateById", object);
	}
	
	public int deleteById(String id)
	{
		return session.delete("${namespace}.${daoName}.deleteById", id);
	}
}
