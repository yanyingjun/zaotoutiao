/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.common.dal.mapper;

import com.zhishun.zaotoutiao.core.model.entity.UserCheckGoldHistory;

public interface UserCheckGoldHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_check_gold_history
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_check_gold_history
     *
     * @mbggenerated
     */
    int insert(UserCheckGoldHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_check_gold_history
     *
     * @mbggenerated
     */
    int insertSelective(UserCheckGoldHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_check_gold_history
     *
     * @mbggenerated
     */
    UserCheckGoldHistory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_check_gold_history
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserCheckGoldHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_check_gold_history
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserCheckGoldHistory record);
}