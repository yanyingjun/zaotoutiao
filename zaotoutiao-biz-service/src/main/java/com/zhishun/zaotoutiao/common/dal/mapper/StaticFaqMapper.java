/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.common.dal.mapper;

import com.zhishun.zaotoutiao.core.model.entity.StaticFaq;

public interface StaticFaqMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_faq
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_faq
     *
     * @mbggenerated
     */
    int insert(StaticFaq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_faq
     *
     * @mbggenerated
     */
    int insertSelective(StaticFaq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_faq
     *
     * @mbggenerated
     */
    StaticFaq selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_faq
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(StaticFaq record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table static_faq
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(StaticFaq record);
}