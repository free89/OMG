package com.carpela.test;

import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class VerifyTransferEnvrionmentDAO {

	private SqlSession session;
	
	public List<VerifyTransferEnvrionment> select(VerifyTransferEnvrionment object)
	{
		return session.selectList("com.carpela.test.VerifyTransferEnvrionmentDAO.select", object);
	}
	
	public int update(VerifyTransferEnvrionment object)
	{
		return session.update("com.carpela.test.VerifyTransferEnvrionmentDAO.update", object);
	}
	
	public int delete(VerifyTransferEnvrionment object)
	{
		return session.delete("com.carpela.test.VerifyTransferEnvrionmentDAO.delete", object);
	}
	
	public int insert(VerifyTransferEnvrionment object)
	{
		return session.insert("com.carpela.test.VerifyTransferEnvrionmentDAO.insert", object);
	}
	
	public int updateById(VerifyTransferEnvrionment object)
	{
		return session.update("com.carpela.test.VerifyTransferEnvrionmentDAO.updateById", object);
	}
	
	public int deleteById(String id)
	{
		return session.delete("com.carpela.test.VerifyTransferEnvrionmentDAO.deleteById", id);
	}
}
