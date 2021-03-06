package com.zhishun.zaotoutiao.biz.service;

import com.zhishun.zaotoutiao.core.model.entity.PlatformChannel;
import com.zhishun.zaotoutiao.core.model.entity.User;
import com.zhishun.zaotoutiao.core.model.entity.UserBehavior;
import com.zhishun.zaotoutiao.core.model.entity.UserPlatform;
import com.zhishun.zaotoutiao.core.model.vo.UserBehaviorVO;
import com.zhishun.zaotoutiao.core.model.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IPlatformWebService, v0.1 2018年03月03日 9:58闫迎军(YanYingJun) Exp $
 */
public interface IPlatformWebService {

    /**
     * 获取用户平台列表
     * @return
     */
    List<UserPlatform> listUserPlatform();

    /**
     * 获取平台渠道记录
     * @param platformId
     * @return
     */
    List<PlatformChannel> listPlatformChannelByPlatformId(int platformId);

    /**
     * 获取用户行为统计数据（按时间统计）
     * @param platformId
     * @param channelId
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    List<UserBehaviorVO> listBehaviorByType(int platformId, int channelId, String type, String startDate, String endDate);

    /**
     * 获取活跃用户（按时间统计）
     * @param platformId
     * @param channelId
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    List<UserBehaviorVO> listOpenAppCount(int platformId, int channelId, String type, String startDate, String endDate);

    /**
     * 获取未登录用户（按时间统计）
     * @param platformId
     * @param channelId
     * @param type
     * @param startDate
     * @param endDate
     * @return
     */
    List<UserVO> listUserCount(int platformId, int channelId, String type, String startDate, String endDate);
}
