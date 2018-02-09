/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.common.dal.mapper;

import com.zhishun.zaotoutiao.core.model.entity.Advertisement;

public interface AdvertisementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisement
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long adId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisement
     *
     * @mbggenerated
     */
    int insert(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisement
     *
     * @mbggenerated
     */
    int insertSelective(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisement
     *
     * @mbggenerated
     */
    Advertisement selectByPrimaryKey(Long adId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisement
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table advertisement
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Advertisement record);
}