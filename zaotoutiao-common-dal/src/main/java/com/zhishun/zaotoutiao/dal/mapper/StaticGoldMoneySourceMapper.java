/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.dal.mapper;

import com.zhishun.zaotoutiao.core.model.entity.StaticGoldMoneySource;

public interface StaticGoldMoneySourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_gold_money_source
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_gold_money_source
     *
     * @mbggenerated
     */
    int insert(StaticGoldMoneySource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_gold_money_source
     *
     * @mbggenerated
     */
    int insertSelective(StaticGoldMoneySource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_gold_money_source
     *
     * @mbggenerated
     */
    StaticGoldMoneySource selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_gold_money_source
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StaticGoldMoneySource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_gold_money_source
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StaticGoldMoneySource record);
}