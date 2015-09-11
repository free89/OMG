package com.carpela.test;

import org.apache.ibatis.session.SqlSession;
import java.util.List;

public class VerifyTransferDAO {

	private SqlSession session;
	
	public List<VerifyTransfer> select(VerifyTransfer object)
	{
		return session.selectList("com.carpela.test.VerifyTransferDAO.select", object);
	}
	
	public int update(VerifyTransfer object)
	{
		return session.update("com.carpela.test.VerifyTransferDAO.update", object);
	}
	
	public int delete(VerifyTransfer object)
	{
		return session.delete("com.carpela.test.VerifyTransferDAO.delete", object);
	}
	
	public int insert(VerifyTransfer object)
	{
		return session.insert("com.carpela.test.VerifyTransferDAO.insert", object);
	}
	
	public int updateById(VerifyTransfer object)
	{
		return session.update("com.carpela.test.VerifyTransferDAO.updateById", object);
	}
	
	public int deleteById(String id)
	{
		return session.delete("com.carpela.test.VerifyTransferDAO.deleteById", id);
	}
}
