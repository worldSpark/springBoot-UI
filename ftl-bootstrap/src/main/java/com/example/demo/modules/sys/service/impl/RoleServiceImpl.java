
package com.example.demo.modules.sys.service.impl;


import com.example.demo.modules.sys.mapper.SysRoleMapper;
import com.example.demo.modules.sys.model.SysRole;
import com.example.demo.modules.sys.service.RoleService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author qjp
 * @since 2016-01-31 21:42
 */
@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> findByUserId(Integer userId) {
        return sysRoleMapper.findByUserId(userId);
    }

    @Override
    public List<SysRole> getAll(SysRole sysRole, String keyword) {
        PageHelper.startPage(sysRole.getPage(),sysRole.getRows());
        Example example=new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("roleName",keyword);
            criteria.orLike("remark",keyword);
        }
        example.orderBy("roleType");
        return sysRoleMapper.selectByExample(example);
    }

    @Override
    public void batchDelete(Integer[] ids) {
        if(ids.length>0){
            for (Integer id:ids){
                SysRole sysRole=new SysRole();
                sysRole.setId(id);
                sysRoleMapper.delete(sysRole);
            }
        }
    }

    @Override
    public void saveOrUpdate(SysRole sysRole) {
        if(sysRole.getId()!=null){//update
            sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        }else{//insert
            sysRole.setCreateTime(new Date());
            sysRole.setRoleType(Byte.valueOf("1"));
            sysRoleMapper.insert(sysRole);
        }
    }

    @Override
    public SysRole getById(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        sysRoleMapper.deleteByPrimaryKey(id);
    }
}
