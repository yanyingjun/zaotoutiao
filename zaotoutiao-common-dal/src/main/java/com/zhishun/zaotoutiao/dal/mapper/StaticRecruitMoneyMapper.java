/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.dal.mapper;

import com.zhishun.zaotoutiao.core.model.entity.StaticRecruitMoney;

public interface StaticRecruitMoneyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_recruit_money
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_recruit_money
     *
     * @mbggenerated
     */
    int insert(StaticRecruitMoney record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_recruit_money
     *
     * @mbggenerated
     */
    int insertSelective(StaticRecruitMoney record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_recruit_money
     *
     * @mbggenerated
     */
    StaticRecruitMoney selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_recruit_money
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StaticRecruitMoney record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_recruit_money
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StaticRecruitMoney record);
}