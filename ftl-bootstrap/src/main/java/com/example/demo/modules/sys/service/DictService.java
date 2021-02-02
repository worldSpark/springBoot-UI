
package com.example.demo.modules.sys.service;

import com.example.demo.modules.sys.mapper.BscDicCodeItemMapper;
import com.example.demo.modules.sys.mapper.BscDicCodeTypeMapper;
import com.example.demo.modules.sys.model.BscDicCodeItem;
import com.example.demo.modules.sys.model.BscDicCodeType;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author qjp
 * @since 2016-01-31 21:42
 */
@Service
@Transactional
public class DictService{
    @Autowired
    private BscDicCodeItemMapper bscDicCodeItemMapper;

    @Resource
    private BscDicCodeTypeMapper bscDicCodeTypeMapper;

    public void saveOrUpdateItem(BscDicCodeItem bscDicCodeItem) {

        if (bscDicCodeItem.getId() == null) {
            if(StringUtils.isBlank(bscDicCodeItem.getIsActive())){
                bscDicCodeItem.setIsActive("0");
            }
            bscDicCodeItemMapper.insert(bscDicCodeItem);
        }else{
            bscDicCodeItemMapper.updateByPrimaryKey(bscDicCodeItem);
        }
    }

    public void deleteItem(Integer id) {
        if (id != null ) {
            bscDicCodeItemMapper.deleteByPrimaryKey(id);
        }
    }

    public BscDicCodeItem view(Integer id) {
        return bscDicCodeItemMapper.selectByPrimaryKey(id);
    }




    public void saveOrUpdateType(BscDicCodeType bscDicCodeType) {

        if (bscDicCodeType.getId() == null) {
            if(StringUtils.isBlank(bscDicCodeType.getIsActive())){
                bscDicCodeType.setIsActive("0");
            }
           bscDicCodeTypeMapper.myInsertUseGeneratedKeys(bscDicCodeType);
        }else{
            bscDicCodeTypeMapper.updateByPrimaryKeySelective(bscDicCodeType);
        }
    }

    public void deleteType(Integer id) {
        if (id != null ) {
            bscDicCodeTypeMapper.deleteByPrimaryKey(id);
            bscDicCodeItemMapper.deleteByTypeId(id);
        }
    }

    public BscDicCodeType viewType(Integer id) {
        return bscDicCodeTypeMapper.selectByPrimaryKey(id);
    }

    public List<BscDicCodeItem> getBscDicCodeItemListByTypeCode(String typeCode) {
        return bscDicCodeItemMapper.getBscDicCodeItemListByTypeCode(typeCode);
    }


    public BscDicCodeItem getBscDicCodeItemListByTypeCodeAndItemCode(String typeCode, String itemCode) {
        return bscDicCodeItemMapper.getBscDicCodeItemListByTypeCodeAndItemCode(typeCode,itemCode);
    }

    public List<BscDicCodeItem> listItem(BscDicCodeItem bscDicCodeItem,String keyword) {
        PageHelper.startPage(bscDicCodeItem.getPage(),bscDicCodeItem.getRows());
        Example example=new Example(BscDicCodeItem.class);
        Example.Criteria criteria = example.createCriteria();
        if(bscDicCodeItem.getTypeId()!=null){
            criteria.andEqualTo("typeId",bscDicCodeItem.getTypeId());
        }
        if(StringUtils.isNotBlank(keyword)){
            criteria.andLike("itemCode","%"+keyword+"%");
            criteria.orLike("itemName","%"+keyword+"%");
        }
        example.orderBy("sortNo");
        return bscDicCodeItemMapper.selectByExample(example);
    }

    public List<BscDicCodeType> listType(BscDicCodeType bscDicCodeType,String keyword) {
        PageHelper.startPage(bscDicCodeType.getPage(),bscDicCodeType.getRows());
        Example example=new Example(BscDicCodeType.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("createTime").desc();
        if(StringUtils.isNotBlank(keyword)){
            criteria.andLike("typeCode","%"+keyword+"%");
            criteria.orLike("typeName","%"+keyword+"%");
        }
        return bscDicCodeTypeMapper.selectByExample(example);
    }
}
