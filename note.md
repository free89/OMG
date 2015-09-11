- 本地连接Oceanbase的JDK补丁[地址](http://oceanbase.alibaba-inc.com/wiki/index.php?title=%E5%AE%A2%E6%88%B7%E7%AB%AFjava.security.InvalidKeyException:_Illegal_key_size_or_default_parameters)
- freemarker
- Oceanbase不稳定
- CURRENT_TIME() / CURRENT_TIMESTAMP()
- 修改：update和insert对应有gmtModified和gmtCreate使用系统函数，如果需要更改，添加或修改判断即可
	
		<#if mKey == "gmtModified">
		${tmp}=CURRENT_TIMESTAMP()
		<#else>
		
- 配置文件和模板文件路径以classpath根目录寻址