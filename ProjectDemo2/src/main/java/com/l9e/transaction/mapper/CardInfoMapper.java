package com.l9e.transaction.mapper;

import com.l9e.transaction.bean.CardInfo;

public interface CardInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CardInfo record);

    int insertSelective(CardInfo record);

    CardInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CardInfo record);

    int updateByPrimaryKey(CardInfo record);
}