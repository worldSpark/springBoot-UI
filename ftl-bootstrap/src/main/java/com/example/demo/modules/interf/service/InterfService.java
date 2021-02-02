package com.example.demo.modules.interf.service;


import com.example.demo.modules.interf.mapper.SysInterfaceMapper;
import com.example.demo.modules.interf.model.SysInterface;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("/interfService")
public class InterfService{

	@Autowired
	private SysInterfaceMapper sysInterfaceMapper;


	public SysInterface get(Integer id) {
		return sysInterfaceMapper.selectByPrimaryKey(id);
	}

	public List<SysInterface> queryPage(SysInterface SysInterface,String keyword) {
		PageHelper.startPage(SysInterface.getPage(),SysInterface.getRows());
		//return sysInterfaceMapper.select(SysInterface);
		Example example=new Example(SysInterface.class);
		Example.Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(keyword)){
			keyword="%"+keyword+"%";
			criteria.andLike("interfaceName",keyword);
			criteria.orLike("interfaceUrl",keyword);
		}
		return sysInterfaceMapper.selectByExample(example);
	}



	public int save(SysInterface sysInterface) {
		return sysInterfaceMapper.insert(sysInterface);
	}


	public int update(SysInterface sysInterface) {
		return sysInterfaceMapper.updateByPrimaryKeySelective(sysInterface);
	}

	public int remove(Integer id) {
		 return sysInterfaceMapper.deleteByPrimaryKey(id);
	}

	public void batchRemove(Integer[] ids) {
		for (Integer id : ids) {
			sysInterfaceMapper.deleteByPrimaryKey(id);
		}

	}
}
