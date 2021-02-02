
package com.example.demo.modules.sys.service.impl;

import com.example.demo.modules.sys.mapper.SysDeptMapper;
import com.example.demo.modules.sys.mapper.SysDeptUserMapper;
import com.example.demo.modules.sys.mapper.SysUserMapper;
import com.example.demo.modules.sys.model.SysDept;
import com.example.demo.modules.sys.model.SysDeptUser;
import com.example.demo.modules.sys.model.SysUser;
import com.example.demo.modules.sys.service.DeptService;
import com.example.demo.modules.sys.vo.SysDeptVo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author qjp
 * @since 2016-01-31 21:42
 */
@Service("deptService")
@Transactional
public class DeptServiceImpl implements DeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptUserMapper sysDeptUserMapper;
    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public List<SysDept> getAll() {
        return sysDeptMapper.selectAll();
    }

    @Override
    public Map<String, Object> treeList() {
        Map<String, Object> map=new HashMap<>();
        List<SysDept> toplist=sysDeptMapper.ListTopDept();
        Map<String, Object> dataMap=convertTree(toplist);
        map.put("core",dataMap);
        return map;
    }

    @Override
    public SysDept getDeptById(Integer id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SysDept sysDept) throws Exception {
        if(sysDept.getId()!=null){
            sysDeptMapper.updateByPrimaryKey(sysDept);
        }else{
            SysDept query=new SysDept();
            query.setDeptName(sysDept.getDeptName());
            query.setParentId(sysDept.getParentId());
            List<SysDept> sysDepts = sysDeptMapper.select(query);
            if(sysDepts.size()>0){
                throw new Exception("部门【"+sysDept.getDeptName()+"】已存在");
            }
            sysDeptMapper.insert(sysDept);
        }
    }

    @Override
    public void deleteById(Integer id) {
        sysDeptMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SysUser getMasterUserByDept(Integer deptId) {
        return sysUserMapper.getMasterUserByDept(deptId);
    }

    @Override
    public void allotUserByDept(Integer userId,Integer deptId, Integer type) {
        SysDeptUser sysDeptUser=new SysDeptUser();
        sysDeptUser.setDeptId(deptId);
        sysDeptUser.setUserId(userId);
        if(type==0){//添加
            sysDeptUser.setIsmaster(1);
            sysDeptUserMapper.insert(sysDeptUser);
        }else{//删除
            sysDeptUserMapper.delete(sysDeptUser);
        }
    }

    @Override
    public List<SysUser> isUnAllotUserList(SysUser sysUser,Integer deptId) {
        PageHelper.startPage(sysUser.getPage(),sysUser.getRows());
        return sysUserMapper.isUnAllotUserList(deptId);
    }

    @Override
    public List<SysUser> isAllotUserList(SysUser sysUser,Integer deptId) {
        PageHelper.startPage(sysUser.getPage(),sysUser.getRows());
        return sysUserMapper.isAllotUserList(deptId);
    }

    @Override
    public void setMasterUserByDept(Integer userId, Integer deptId,Integer type) {
        if(type==0){//直接取消管理员
            sysDeptUserMapper.clearMasterByDept(userId,deptId);
        }else{
            //先把该部门的所有用户都清除管理员
            sysDeptUserMapper.clearMasterByDept(null,deptId);
            //再设定管理员
            sysDeptUserMapper.setMasterUserByDept(userId,deptId);
        }
    }

    @Override
    public SysDeptUser getSysDeptUser(Integer userId, Integer deptId) {
        SysDeptUser query=new SysDeptUser();
        query.setUserId(userId);
        query.setDeptId(deptId);
        return sysDeptUserMapper.selectOne(query);
    }

    private Map<String,Object> convertTree(List<SysDept> toplist) {
        Map<String,Object> map=new HashMap<>();
        map.put("data",convertVo(toplist));
        return map;
    }

    private List<SysDeptVo> convertVo(List<SysDept> toplist){
        List<SysDeptVo> result=new ArrayList<>();
        if(toplist.size()>0){
            for (SysDept sysDept:toplist){
                SysDeptVo vo=new SysDeptVo();
                vo.setId(sysDept.getId());
                vo.setIcon(sysDept.getIcon()==null?"none":sysDept.getIcon());
                vo.setText(sysDept.getDeptName());
                Map state=new HashMap();
                state.put("opened",true);
                vo.setState(state);
                List<SysDept> list=sysDeptMapper.getChildDeptList(sysDept.getId());
                if(list.size()>0){
                    vo.setChildren(convertVo(list));
                }
                result.add(vo);
            }
        }
        return result;
    }
}
