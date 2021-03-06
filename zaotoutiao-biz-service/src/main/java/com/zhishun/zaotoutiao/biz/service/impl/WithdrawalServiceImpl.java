/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.biz.service.impl;

import com.google.common.collect.Maps;
import com.zhishun.zaotoutiao.biz.service.IWithdrawalService;
import com.zhishun.zaotoutiao.common.util.DateUtil;
import com.zhishun.zaotoutiao.core.model.entity.StaticRedEnvelope;
import com.zhishun.zaotoutiao.core.model.entity.UserWithdrawalState;
import com.zhishun.zaotoutiao.core.model.vo.UserWithdrawalStateVO;
import com.zhishun.zaotoutiao.dal.mapper.StaticRedEnvelopeMapper;
import com.zhishun.zaotoutiao.dal.mapper.UserWithdrawalStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: WithdrawalServiceImpl, v0.1 2018年03月01日 14:30闫迎军(YanYingJun) Exp $
 */
@Service
public class WithdrawalServiceImpl implements IWithdrawalService{

    @Autowired
    private UserWithdrawalStateMapper userWithdrawalStateMapper;

    @Autowired
    private StaticRedEnvelopeMapper staticRedEnvelopeMapper;

    @Override
    public List<UserWithdrawalState> getHasApplication(Long userId) {
        return userWithdrawalStateMapper.getHasApplication(userId);
    }

    @Override
    public int updateWithdrawalState(UserWithdrawalState userWithdrawalState) {
        return userWithdrawalStateMapper.updateByPrimaryKeySelective(userWithdrawalState);
    }

    @Override
    public Map<String,Object> addWithdrawalState(Long userId, BigDecimal money, int status) {
        UserWithdrawalState userWithdrawalState = new UserWithdrawalState();
        userWithdrawalState.setStatus(status);
        userWithdrawalState.setUserId(userId);
        userWithdrawalState.setMoney(money);
        userWithdrawalState.setCreateDate(DateUtil.toString(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));

        userWithdrawalStateMapper.insertSelective(userWithdrawalState);
        String billNo = userWithdrawalState.getId() + "" + String.valueOf(System.currentTimeMillis());
        userWithdrawalState.setBillNo(billNo);
        userWithdrawalStateMapper.updateByPrimaryKeySelective(userWithdrawalState);
        Map<String,Object> map = Maps.newHashMap();
        map.put("insertId", userWithdrawalState.getId());
        map.put("billNo", billNo);
        return map;
    }

    @Override
    public List<StaticRedEnvelope> listRedEnvelope() {
        return staticRedEnvelopeMapper.listRedEnvelope();
    }

    @Override
    public List<UserWithdrawalStateVO> listWithdrawals(String keyWord, String channelId, String createDate, Integer status) {
        Map map = Maps.newHashMap();
        if(!StringUtils.isEmpty(keyWord)){
            map.put("keyWord", keyWord);
        }
        if(!StringUtils.isEmpty(channelId) && Integer.valueOf(channelId) != 0){
            map.put("channelId", channelId);
        }
        if(!StringUtils.isEmpty(createDate)){
            map.put("createDate", createDate);
        }
        if(!StringUtils.isEmpty(status)){
            map.put("status", status);
        }
        return userWithdrawalStateMapper.listWithdrawals(map);
    }
}
