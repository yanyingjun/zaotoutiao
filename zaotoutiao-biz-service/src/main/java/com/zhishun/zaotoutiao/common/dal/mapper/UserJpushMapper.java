/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.common.dal.mapper;

import com.zhishun.zaotoutiao.core.model.entity.UserJpush;

public interface UserJpushMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_jpush
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_jpush
     *
     * @mbggenerated
     */
    int insert(UserJpush record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_jpush
     *
     * @mbggenerated
     */
    int insertSelective(UserJpush record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_jpush
     *
     * @mbggenerated
     */
    UserJpush selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_jpush
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserJpush record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_jpush
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserJpush record);
}