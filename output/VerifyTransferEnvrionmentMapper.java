package com.carpela.test;

import java.util.List;

public interface VerifyTransferEnvrionmentMapper {

	public List<VerifyTransferEnvrionment> select(VerifyTransferEnvrionment object);
	
	public int update(VerifyTransferEnvrionment object);
	
	public int delete(VerifyTransferEnvrionment object);
	
	public int insert(VerifyTransferEnvrionment object);
	
	public int updateById(VerifyTransferEnvrionment object);
	
	public int deleteById(String id);
}
