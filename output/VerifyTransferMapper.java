package com.carpela.test;

import java.util.List;

public interface VerifyTransferMapper {

	public List<VerifyTransfer> select(VerifyTransfer object);
	
	public int update(VerifyTransfer object);
	
	public int delete(VerifyTransfer object);
	
	public int insert(VerifyTransfer object);
	
	public int updateById(VerifyTransfer object);
	
	public int deleteById(String id);
}
