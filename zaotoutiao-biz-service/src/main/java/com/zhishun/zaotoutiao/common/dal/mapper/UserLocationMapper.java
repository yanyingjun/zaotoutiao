/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.common.dal.mapper;

import com.zhishun.zaotoutiao.core.model.entity.UserLocation;

public interface UserLocationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long locationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int insert(UserLocation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int insertSelective(UserLocation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    UserLocation selectByPrimaryKey(Long locationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserLocation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_location
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserLocation record);
}