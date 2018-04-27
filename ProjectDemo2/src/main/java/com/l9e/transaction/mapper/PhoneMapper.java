package com.l9e.transaction.mapper;

import com.l9e.transaction.bean.Phone;

public interface PhoneMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Phone record);

    int insertSelective(Phone record);

    Phone selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Phone record);

    int updateByPrimaryKey(Phone record);
}