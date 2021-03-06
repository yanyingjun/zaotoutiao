/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.api.home.controller.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhishun.zaotoutiao.api.home.callback.ControllerCallback;
import com.zhishun.zaotoutiao.api.home.controller.base.BaseController;
import com.zhishun.zaotoutiao.api.home.request.UserMsgReq;
import com.zhishun.zaotoutiao.biz.service.*;
import com.zhishun.zaotoutiao.common.base.pagination.Page;
import com.zhishun.zaotoutiao.common.base.pagination.PageRequest;
import com.zhishun.zaotoutiao.common.util.*;
import com.zhishun.zaotoutiao.core.model.entity.*;
import com.zhishun.zaotoutiao.core.model.enums.DanRulesEnum;
import com.zhishun.zaotoutiao.core.model.enums.ErrorCodeEnum;
import com.zhishun.zaotoutiao.core.model.exception.ZhiShunException;
import com.zhishun.zaotoutiao.core.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: UserRegisterController, v0.1 2018年02月23日 11:44闫迎军(YanYingJun) Exp $
 */
@RestController
public class UserController extends BaseController{


    @Autowired
    private IUserService userService;

    @Autowired
    private IExchangeRateService exchangeRateService;

    @Autowired
    private INewsService newsService;

    @Autowired
    private IGoldRecordService goldRecordService;

    @Autowired
    private IStaticFakeDataService staticFakeDataService;

    @Autowired
    private IMoneyRecordService moneyRecordService;

    @Autowired
    private IInformationService iInformationService;

    @Autowired
    private IUserReadService userReadService;

    /**
     * 用户注册
     * @param telephone
     * @param password
     * @return
     */
    @RequestMapping(value = UserMsgReq.USER_REGISTER_REQ, method = RequestMethod.POST)
    public Map<Object, Object> search(final HttpServletRequest request, final String telephone, final String password, final Integer platformId, final String channelId, final String address, final String verificationCode, final String idImei) {

        // 定义Map集合对象
        final Map<Object, Object> dataMap = Maps.newHashMap();

        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotNull(telephone, ErrorCodeEnum.PARAMETER_ANOMALY);
                AssertsUtil.isNotNull(password, ErrorCodeEnum.PARAMETER_ANOMALY);
            }
            @Override
            public void handle() throws Exception {
                //业务逻辑
                User user = userService.getUserByMap(telephone);
                if(StringUtils.isEmpty(user)){
                    Long userId = userService.addUserInfo(telephone, password, platformId, channelId, address, idImei);
                    //为用户添加消息和公告
                    List<UserInformationTemplate> listInformation = iInformationService.listInformationNew();
                    for(UserInformationTemplate userInfo : listInformation){
                        UserInformation userInformation = new UserInformation();
                        BeanMapUtil.copy(userInfo, userInformation);
                        userInformation.setUserId(userId);
                        userInformation.setCreateDate(DateUtil.toString(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));
                        iInformationService.addUserInformation(userInformation);
                    }
                    Map userData = userService.getUserByTelephone(telephone);
                    dataMap.put("result", "success");
                    dataMap.put("data", userData);
                    dataMap.put("msg", "新增用户成功");
                }else{
                    dataMap.put("msg", "用户已存在，请直接登录");
                    dataMap.put("data", null);
                }
            }

        });

        return dataMap;
    }

    /**
     * 查询电话是否已存在
     * @param telephone
     * @return
     */
    @RequestMapping(value = UserMsgReq.USER_EXIST_ACCOUNT_REQ, method = RequestMethod.GET)
    public Map<Object,Object> existAccount(final String telephone){

        final Map<Object,Object> dataMap = Maps.newHashMap();

        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotBlank(telephone, ErrorCodeEnum.PARAMETER_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                User user = userService.getUserByMap(telephone);
                if(StringUtils.isEmpty(user)){
                    dataMap.put("result","success");
                    dataMap.put("msg","用户不存在，可以创建");
                }else{
                    dataMap.put("result","failure");
                    dataMap.put("msg","用户已存在，请直接登录");
                    dataMap.put("data",user);
                }
            }
        });
        return dataMap;
    }


    /**
     * 用户登录
     * @param telephone
     * @param password
     * @param invitation  用户邀请码
     * @param idImei   手机号唯一设备编码
     * @return
     */
    @RequestMapping(value = UserMsgReq.USER_LOGIN_REQ, method = RequestMethod.POST)
    public Map<Object,Object> login(final String telephone, final String password, final String invitation, final String idImei){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotBlank(telephone, ErrorCodeEnum.PARAMETER_ANOMALY);
                AssertsUtil.isNotBlank(password, ErrorCodeEnum.PARAMETER_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                //查询用户信息
                User userData = userService.getUserByMap(telephone);

                if(StringUtils.isEmpty(userData)){
                    dataMap.put("result", "failure");
                    dataMap.put("msg", "用户不存在，请先注册");
                }else{
                    Boolean userLogin = userService.isUserLogin(telephone, Md5Util.md5Encode(password));
                    if(userLogin == false){
                        dataMap.put("result", "failure");
                        dataMap.put("msg", "密码错误");
                    }else{
                        if(!StringUtils.isEmpty(invitation)){
                            Map map = Maps.newHashMap();
                            map.put("myInvitation", invitation);
                            User userParent = userService.getUserByParam(map);
                            //判断是否已经绑定过师徒关系
                            //判断同一台设备多账号登录，不能绑定师徒关系
                            User userA = userService.getParentApprentice(userData.getUserId(), userParent.getUserId());
                            if(userData.getIdImei().equals(idImei) && StringUtils.isEmpty(userA)){
                                userData.setParentId(userParent.getUserId());
                            }
                        }
                        //更改用户登录状态为在线
                        dataMap.put("result", "success");
                        dataMap.put("msg", "登录成功");
                        if(userData.getIsOnline() == 0){
                            dataMap.put("isFirstLogin", "true");
                        }else{
                            dataMap.put("isFirstLogin", "false");
                        }
                        userData.setIsOnline(1);
                        userData.setLastVisitDate(DateUtil.toString(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));
                        userService.updateUser(userData);
                        dataMap.put("data", userData);
                    }
                }
            }
        });
        return dataMap;
    }

    /**
     * 忘记密码
     * @param userVO
     * @return
     */
    @RequestMapping(value = UserMsgReq.USER_FORGET_PASSWORD_REQ, method = RequestMethod.POST)
    public Map<Object,Object> forgetPassword(final String telephone, final String password, final String userId){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotBlank(telephone, ErrorCodeEnum.PARAMETER_ANOMALY);
                AssertsUtil.isNotBlank(password, ErrorCodeEnum.PARAMETER_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                User user = userService.getUserByMap(telephone);
                if(StringUtils.isEmpty(user)){
                    dataMap.put("result", "failure");
                    dataMap.put("msg", "用户不存在，请先注册");
                }else{
                    if(StringUtils.isEmpty(userId)){
                        //更新用户密码
                        user.setPassword(Md5Util.md5Encode(password));
                        userService.updateUser(user);
                        dataMap.put("result", "success");
                        dataMap.put("msg", "修改密码成功");
                        dataMap.put("data", user);
                    }else{
                        //绑定手机号
                        user.setTelephone(telephone);
                        user.setPassword(Md5Util.md5Encode(password));
                        userService.updateUser(user);
                        dataMap.put("result", "success");
                        dataMap.put("msg", "绑定手机号成功");
                        dataMap.put("data", user);
                    }
                }
            }
        });

        return dataMap;
    }

    /**
     * 退出登录
     * @param telephone
     * @return
     */
    @RequestMapping(value = UserMsgReq.USER_LOGOUT_REQ, method = RequestMethod.POST)
    public Map<Object,Object> logout(final String telephone){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotBlank(telephone,ErrorCodeEnum.PARAMETER_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                User user = userService.getUserByMap(telephone);
                //更改用户状态为离线
                user.setIsOnline(0);
                userService.updateUser(user);
                dataMap.put("result", "success");
                dataMap.put("msg", "成功退出");
                dataMap.put("data", user);
            }
        });

        return dataMap;
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @RequestMapping(value = UserMsgReq.USER_GET_REQ, method = RequestMethod.POST)
    public Map<Object,Object> getUser(final Long userId){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId,ErrorCodeEnum.PARAMETER_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                User user = userService.getUserByUserId(userId);
                dataMap.put("result", "success");
                dataMap.put("msg", "信息返回成功");
                dataMap.put("data", user);
            }
        });

        return dataMap;
    }


    /**
     * 用户信息修改
     * @param userVO
     * @return
     */
    @RequestMapping(value = UserMsgReq.USER_SET_REQ, method = RequestMethod.POST)
    public Map<Object,Object> setUser(final UserVO userVO){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userVO.getUserId(), ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                //过滤微信emoji表情
                Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                        Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
                String nickName = userVO.getNickName();
                if(!StringUtils.isEmpty(nickName)){
                    Matcher emojiMatcher = emoji.matcher(nickName);
                    if (emojiMatcher.find()) {
                        nickName = emojiMatcher.replaceAll("*");
                    }
                }

                Map<String,Object> map = Maps.newHashMap();
                map.put("nickName", nickName);
                User user = userService.getUserByParam(map);

                User user1 = userService.getUserByUserId(userVO.getUserId());
                if(StringUtils.isEmpty(user1)){
                    dataMap.put("result", "failure");
                    dataMap.put("msg", "用户不存在");
                }

                if(user == null || user.getUserId().equals(user.getUserId())){
                    User user2 = new User();
                    BeanMapUtil.copy(userVO, user2);
                    userService.updateUser(user2);
                    User user3 = userService.getUserByUserId(userVO.getUserId());
                    dataMap.put("result", "success");
                    dataMap.put("msg", "个人信息修改成功");
                    dataMap.put("data", user3);
                }else{
                    dataMap.put("result", "failure");
                    dataMap.put("msg", "昵称已存在，换个呗");
                }
            }
        });

        return dataMap;
    }


    /**
     * 我的收入
     * @param userId
     * @return
     */
    @RequestMapping(value = UserMsgReq.MY_INCOME_REQ, method = RequestMethod.GET)
    public Map<Object,Object> getUserIncome(final Long userId){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId,ErrorCodeEnum.PARAMETER_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                User user = userService.getUserByUserId(userId);
                //金币来源去向
                List<UserGoldRecordVO> listUGR = userService.getUserGoldRecord(userId);
                dataMap.put("gold_record", listUGR);
                List<UserMoneyRecordVO> listUMR = userService.getUserMoneyRecord(userId);
                dataMap.put("money_record", listUMR);
                //我的金币，我的零钱
                dataMap.put("user_info", user);
                //金币零钱汇率
                ExchangeRate exchangeRate = exchangeRateService.getGoldToMoney();
                dataMap.put("gold_to_money", exchangeRate.getGoldToMoney());
                //昨天金币收入
                BigDecimal goldCount = userService.getGoldYesterday(userId);
                dataMap.put("get_gold_yesterday", goldCount);
                //昨天零钱收入
                BigDecimal moneyCount = userService.getMoneyYesterday(userId);
                if(!StringUtils.isEmpty(moneyCount)){
                    moneyCount = moneyCount.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                dataMap.put("get_money_yesterday", moneyCount);
                //累计金币收入
                BigDecimal goldCountAll = userService.getGoldAll(userId);
                dataMap.put("get_gold_all", goldCountAll);
                //累计零钱收入
                BigDecimal moneyCountAll = userService.getMoneyAll(userId);
                if(!StringUtils.isEmpty(moneyCountAll)){
                    moneyCountAll =  moneyCountAll.setScale(2,BigDecimal.ROUND_HALF_UP);
                }
                dataMap.put("get_money_all", moneyCountAll);
                dataMap.put("result", "success");
                dataMap.put("msg", "信息返回成功");
            }
        });

        return dataMap;
    }

    /**
     * 修改密码
     * @param userVO
     * @return
     */
    @RequestMapping(value = UserMsgReq.PASSWORD_UPDATE_REQ, method = RequestMethod.POST)
    public Map<Object,Object> updatePassword(final UserVO userVO){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotBlank(userVO.getOldPassword(),ErrorCodeEnum.PARAMETER_ANOMALY);
                AssertsUtil.isNotBlank(userVO.getNewPassword(),ErrorCodeEnum.PARAMETER_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                String oldPassword = Md5Util.md5Encode(userVO.getOldPassword());
                String newPassword = Md5Util.md5Encode(userVO.getNewPassword());
                User user = userService.getUserByUserId(userVO.getUserId());
                if(StringUtils.isEmpty(user)){
                    dataMap.put("result", "failure");
                    dataMap.put("msg", "用户不存在，请先注册");
                }else{
                    if(user.getPassword().equals(oldPassword) == false){
                        dataMap.put("result", "failure");
                        dataMap.put("msg", "密码错误，请重新输入");
                    }else{
                        user.setPassword(newPassword);
                        userService.updateUser(user);
                        dataMap.put("result", "success");
                        dataMap.put("msg", "修改密码成功");
                        dataMap.put("data", user);
                    }
                }
            }
        });

        return dataMap;
    }

    /**
     * 收徒，填写邀请码，绑定师徒关系
     * @param userId 当前用户id
     * @param invitation 师傅的邀请码
     * @return
     */
    @RequestMapping(value = UserMsgReq.RECRUIT_SET_REQ, method = RequestMethod.POST)
    public Map<Object,Object> recruitSet(final Long userId, final String invitation){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
                AssertsUtil.isNotBlank(invitation, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                //获取当前用户信息
                User user = userService.getUserByUserId(userId);
                //根据邀请码获取师傅id
                User parent = userService.getUserParent(invitation);
                //判断是否已经绑定过师徒关系
                User userPar = userService.getParentApprentice(userId, parent.getUserId());
                dataMap.put("result", "success");
                if(StringUtils.isEmpty(userPar)){
                    //添加师傅id到自己的表里
                    user.setParentId(parent.getUserId());
                    userService.updateUser(user);
                    //查询收徒奖励金币数
                    ExchangeRate exchangeRate = exchangeRateService.getGoldToMoney();
                    int gold = exchangeRate.getRecruitGold();
                    //给师傅添加收徒奖励金
                    BigDecimal goldAll = new BigDecimal(parent.getGold()).add(new BigDecimal(gold));
                    parent.setGold(goldAll.intValue());
                    userService.updateUser(parent);
                    //添加金币新增记录
                    userService.addUserGoldRecord(6, parent.getUserId(), gold, userId);

                    dataMap.put("msg", "师徒关系绑定成功");
                    dataMap.put("parent_info", parent);
                    dataMap.put("data", user);
                }else{
                    dataMap.put("msg", "师徒关系已存在");
                }
            }
        });

        return dataMap;
    }

    /**
     * 获取徒弟列表信息
     * @param userId
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = UserMsgReq.APPRENTICE_GET_REQ, method = RequestMethod.GET)
    public Map<Object,Object> apprenticeGet(final Long userId, final PageRequest pageRequest){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                Page<User> page = userService.getApprenticePage(userId, pageRequest);
                dataMap.put("result", "success");
                dataMap.put("msg", "查询徒弟列表信息成功");
                dataMap.put("data", page.getRows());
                dataMap.put("count", page.getTotal());
            }
        });

        return dataMap;
    }

    /**
     * 待唤醒徒弟列表(三天未活跃用户列表,每页50)
     * @param userId
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = UserMsgReq.WAKE_UP_APPRENTICE_LIST_GET)
    public Map<Object,Object> wakeUpApprenticeListGet(final Long userId, final PageRequest pageRequest){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                Page<UserVO> page = userService.getWakeUpApprenticePage(userId, pageRequest);
                if(page.getTotal() > 0){
                    for(UserVO userVO : page.getRows()){
                        User user = userService.getUserByUserId(userVO.getParentId());
                        userVO.setParent(user);
                    }
                }
                dataMap.put("result", "success");
                dataMap.put("msg", "相关信息返回成功");
                dataMap.put("data", page.getRows());
            }
        });
        return dataMap;
    }

    /**
     * 获取用户历史记录
     * @param userId
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = UserMsgReq.LOOK_RECORD_GET)
    public Map<Object,Object> lookRecordGet(final Long userId, final PageRequest pageRequest){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                Page<UserReadRecord> page = newsService.listLookRecordPage(userId, pageRequest);
                dataMap.put("result", "success");
                dataMap.put("msg", "相关信息返回成功");
                dataMap.put("data", page.getRows());
            }
        });

        return dataMap;
    }

    /**
     * 根据type类型删除用户相关通知信息
     * @param userId
     * @param type
     * @return
     */
    @RequestMapping(value = UserMsgReq.DELETE_INFORMATION_BY_TYPE, method = RequestMethod.POST)
    public Map<Object,Object> noticeDel(final int userId ,final String type){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
                AssertsUtil.isNotNull(type, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                //返回执行的结果
                String result = userService.delUserInformation(userId,type);
                dataMap.put("result", "success");
                dataMap.put("msg", "相关信息返回成功");
                dataMap.put("data", result);
            }
        });

        return dataMap;
    }

    /**
     * 阅读获取金币
     * @param userId
     * @param probability 概率
     * @return
     */
    /*@RequestMapping(value = UserMsgReq.READ_GOLD_GET)
    public Map<Object,Object> readGoldGet(final Long userId, final Integer probability){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {

                //判断用户是否阅读过该新闻，并请求过加金币
                int num = userService.isRead(userId, null);
                int readContinuousDay = 0;
                if(num == 2){
                    //新手任务
                    //判断是否超过活动日期
                    //判断是否超过活动天数 newbie_read_time < 0为该活动永不过期
                    ExchangeRate exchangeRate = exchangeRateService.getGoldToMoney();
                    Integer newBieReadTime = exchangeRate.getNewbieReadTime();
                    User user = userService.isSurpassingActivtiy(newBieReadTime, userId);
                    *//*if(!StringUtils.isEmpty(user) && newBieReadTime < 0){
                        //判断今天是否已经获得过新手奖励
                        UserGoldRecord userGoldRecord = userService.isGetNewbieGoldToday(userId);
                        if(StringUtils.isEmpty(userGoldRecord)) {
                            *//**//**
                             * 新手阅读任务：三十天时限
                             首次登陆当天每天阅读3篇以上 200
                             连续3天  300
                             连续6天 400
                             连续9天 500
                             连续12天 600
                             连续15天 700
                             连续18天 800
                             连续21天 1000
                             *//**//*
                            int addGoldNum = 0;
                            if (readContinuousDay < 3) {
                                addGoldNum = 200;
                            } else if (readContinuousDay < 6) {
                                addGoldNum = 300;
                            } else if (readContinuousDay < 9) {
                                addGoldNum = 400;
                            } else if (readContinuousDay < 12) {
                                addGoldNum = 500;
                            } else if (readContinuousDay < 15) {
                                addGoldNum = 600;
                            } else if (readContinuousDay < 18) {
                                addGoldNum = 700;
                            } else if (readContinuousDay < 21) {
                                addGoldNum = 800;
                            } else if (readContinuousDay < 30) {
                                addGoldNum = 1000;
                            } else {
                                addGoldNum = 0;
                            }
                            //获取用户当天阅读篇数（如果大于三篇）
                            List<UserReadRecord> urrList = userService.isReadThreeToday(userId);
                            if (urrList.size() > 3) {
                                //添加新手阅读金币和金币记录
                                //新闻阅读奖励类型
                                int source = 8;
                                userService.addUserGoldRecord(source, userId, Long.valueOf(addGoldNum), null);
                                //更新用户金币
                                userService.updateUserInfo(userId, addGoldNum);
                                dataMap.put("newBieReadGold", addGoldNum);
                            } else {
                                dataMap.put("newBieReadGold", "0");
                            }
                        }
                    }else{
                        dataMap.put("newBieReadGold", "0");
                    }*//*
                    //日常任务
                    //判断是否给师傅进贡，判断是否算有效徒弟

                    //判断是否超过今天阅读可以获得的最大金币数
                    //获取用户当天阅读获得的金币总和
                    int goldToday = userService.getReadGoldToday(userId, 1);
                    //获取用户当天最大可以获得的金币数
                    StaticGoldConfig staticGoldConfig = userService.getReadGoldConfig("READ_ARTICLE");
                    Integer readGoldMax = staticGoldConfig.getHighest();
                    if(goldToday < readGoldMax){
                        //获取活动天数
                        //获取用户注册时间
                        //判断是否超过活动天数 readActivityDays < 0为该活动永不过期
                        Integer readActivityDays = exchangeRate.getReadActivityDays();
                        User user1 = userService.isSurpassingActivtiy(readActivityDays, userId);
                        if(!StringUtils.isEmpty(user1) || readActivityDays < 0){
                            //随机为用户添加金币
                            //获取随机概率
                            Integer prob = staticGoldConfig.getUnit();
                            String addGoldNum = staticGoldConfig.getValue();
                            if(rand(prob) == true){
                                //添加自己阅读金币和金币记录
                                //新闻阅读奖励类型
                                int source = 1;
                                userService.addUserGoldRecord(source, userId, Long.valueOf(addGoldNum), null);
                                //更新用户金币
                                userService.updateUserInfo(userId, Integer.parseInt(addGoldNum));
                                dataMap.put("result", "success");
                                dataMap.put("msg", "本次获得金币奖励");
                                dataMap.put("getGold", addGoldNum);
                                dataMap.put("data", "true");
                                dataMap.put("newBieReadGold", 0);

                                //判断是否有师傅
                                Long parentId = user.getParentId();
                                if(!StringUtils.isEmpty(parentId) && parentId != 0){
                                    //判断是否已经阅读超过五篇文章，确定是否有师徒关系
                                    List<UserReadRecord> listURR = userService.isReadFive(userId);
                                    if(listURR.size() > 5){
                                        //为父类添加金币
                                        StaticGoldConfig sgc = userService.getReadGoldConfig("APPRENTICE_READ_ARTICLE");
                                        Integer prob1 = sgc.getUnit();
                                        BigDecimal addParentGoldNum = new BigDecimal(addGoldNum).multiply(new BigDecimal(prob1/100));
                                        //添加金币和金币记录
                                        //这里把父类id当作用户id,用户id当作徒弟id,添加的金币作为阅读进贡
                                        //添加徒弟阅读进贡金币记录
                                        //徒弟新闻阅读进贡奖励类型
                                        userService.addUserGoldRecord(2,parentId, addParentGoldNum.longValue(), userId);
                                        //更新用户金币
                                        userService.updateUserInfo(parentId, addParentGoldNum.intValue());

                                        //--------收徒奖励 --------
                                        //获取配置,判断师傅是否是首次收徒
                                        User user4 = userService.isParentFirstRecruit(parentId);
                                        if(StringUtils.isEmpty(user4)){
                                            //给师傅添加收徒奖励和奖励记录（首次）
                                            Integer addParentGoldNum1 = exchangeRate.getNewbieRecruitGold();
                                            BigDecimal addParentMoneyNum = exchangeRate.getNewbieRecruitMoney();
                                            //添加金币和金币记录
                                            //徒弟为
                                            userService.addUserGoldRecord(9,parentId, addParentGoldNum1.longValue(), userId);
                                            //更新用户金币
                                            userService.updateUserInfo(parentId, addParentGoldNum1.intValue());

                                            //添加零钱和零钱记录

                                            userService.addUserMoneyRecord(3, parentId, addParentMoneyNum, userId);
                                            userService.updateUserMoneyRecord(parentId, addParentMoneyNum);
                                        }else{
                                            //判断师傅已经收到自己的收徒奖励
                                            UserGoldRecord userGoldRecord1 = userService.isGiveParentRecruitGold(userId, parentId);
                                            if(StringUtils.isEmpty(userGoldRecord1)){
                                                //给师傅添加收徒奖励和奖励记录（普通）
                                                userService.addUserGoldRecord(6,parentId, exchangeRate.getRecruitGold().longValue(), userId);
                                                userService.updateUserInfo(parentId, exchangeRate.getRecruitGold().intValue());

                                            }
                                        }
                                        //判断三天内是否添加过唤醒金币
                                        UserGoldRecord userGoldRecord1 = userService.getWeekupThreeDayGetGold(userId, 13);
                                        if(StringUtils.isEmpty(userGoldRecord1)){
                                            //判断三天内是否有师傅唤醒自己
                                            // 被唤醒类型
                                            String type = "AWAKEN";
                                            UserShare userShare = userService.getWeekupThreeDay(userId, type);
                                            if(!StringUtils.isEmpty(userShare)){
                                                //添加金币和金币记录
                                                //给师傅添加唤醒徒弟奖励金币
                                                userService.addUserGoldRecord(12,parentId, exchangeRate.getAwakenParentGold().longValue(), userId);
                                                userService.updateUserInfo(parentId, exchangeRate.getAwakenParentGold().intValue());

                                                //给师徒弟加被唤醒奖励金币
                                                userService.addUserGoldRecord(13,userId, exchangeRate.getAwakenUserGold().longValue(), userId);
                                                userService.updateUserInfo(userId, exchangeRate.getAwakenUserGold().intValue());
                                            }

                                        }
                                    }
                                }

                            }else{
                                dataMap.put("result", "success");
                                dataMap.put("msg", "本次浏览没获得金币");
                                dataMap.put("getGold", 0);
                                dataMap.put("data", false);
                                dataMap.put("newBieReadGold", 0);
                            }
                        }else {
                            dataMap.put("result", "success");
                            dataMap.put("msg", "已经过了活动日期");
                            dataMap.put("getGold", 0);
                            dataMap.put("data", false);
                            dataMap.put("newBieReadGold", 0);
                        }
                    }else {
                        dataMap.put("result", "success");
                        dataMap.put("msg", "已经达到今日最多看新闻获得金币");
                        dataMap.put("getGold", 0);
                        dataMap.put("data", false);
                        dataMap.put("newBieReadGold", 0);
                    }
                }else{
                    dataMap.put("result", "success");
                    dataMap.put("msg", "您已经浏览过该新闻,本次浏览无金币");
                    dataMap.put("getGold", 0);
                    dataMap.put("data", false);
                    dataMap.put("newBieReadGold", 0);
                }

            }
        });

        return dataMap;
    }*/

    /**
     * 阅读获取金币概率
     * @param userRead
     * @return
     */
    @RequestMapping(value = UserMsgReq.READ_GOLD_GET, method = RequestMethod.POST)
    public Map<Object,Object> readGoldGet(final UserReadRecord userRead){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotNull(userRead, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {

                Map<String,Object> map = Maps.newHashMap();
                map.put("userId", userRead.getUserId());
                //获取用户当天总得阅读记录数
                int readNum = userReadService.CountReadRecord(userRead.getUserId());
                map.put("infoId", userRead.getInfoId());
                UserReadRecord userReadRecord = userReadService.getUserReadRecord(map);

                ExchangeRate exchangeRate = exchangeRateService.getGoldToMoney();
                User user = userService.getUserByUserId(userRead.getUserId());

                //判断是否添加到阅读列表
                if(StringUtils.isEmpty(userReadRecord)){
                    //随机为用户添加金币
                    int gold = 0;
                    //获取随机概率
                    if(userRead.getBrowsing() == 1){
                        gold = 0;
                    }else{
                        //判断今天的阅读篇数是否小于等于2
                        if(readNum <= 2){
                            gold = exchangeRate.getReadDownGold();
                        }else{
                            //查看前一篇阅读记录用户的行为
                            UserReadRecord userReadRecord1 = userReadService.maxReadRecord(userRead.getUserId());
                            if(userReadRecord1.getBrowsing() == 3){
                                //金币掉落概率为0.5-0.7
                                int random = RandomUtil.getRandomIndex();
                                if(random >= 3){
                                    gold = exchangeRate.getReadDownGold();
                                }
                            }else{
                                //金币掉落概率为0.5
                                int random = RandomUtil.getRandomIndex();
                                if(random >= 5){
                                    gold = exchangeRate.getReadDownGold();
                                }
                            }
                        }
                    }
                    if(gold != 0){
                        userReadService.readAddGold(userRead.getUserId(), gold, user, exchangeRate, userRead);
                        dataMap.put("result", "success");
                        dataMap.put("msg", "本次获得金币奖励");
                        dataMap.put("getGold", gold);
                        dataMap.put("data", "true");
                    }else{
                        //添加阅读记录
                        userRead.setCreateDate(DateUtil.toString(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));
                        userReadService.readRecordAdd(userRead);
                        dataMap.put("result", "success");
                        dataMap.put("msg", "本次浏览没获得金币");
                        dataMap.put("getGold", 0);
                        dataMap.put("data", false);
                        //dataMap.put("newBieReadGold", 0);
                    }
                }else{
                    dataMap.put("result", "success");
                    dataMap.put("msg", "您已经浏览过该新闻,本次浏览无金币");
                    dataMap.put("getGold", 0);
                    dataMap.put("data", false);
                    //dataMap.put("newBieReadGold", 0);
                }
            }
        });
        return dataMap;
    }


    /**
     * 获取职业列表信息
     * @return
     */
    @RequestMapping(value = UserMsgReq.JOBS_GET)
    public Map<Object,Object> jobsGet(){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {

            }

            @Override
            public void handle() throws Exception {
                List<StaticIndustrysVO> listVO = userService.listStaticIndustrys();
                dataMap.put("result", "success");
                dataMap.put("msg", "信息返回成功");
                dataMap.put("data", listVO);

            }
        });

        return dataMap;
    }

    /**
     * 清空用户历史记录
     * @return
     */
    @RequestMapping(value = UserMsgReq.LOOK_RECORD_DEL)
    public Map<Object,Object> lookRecordDel(final Long userId){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                userService.delUserReadRecord(userId);
                dataMap.put("result", "success");
                dataMap.put("msg", "返回信息成功");

            }
        });

        return dataMap;
    }

    /**
     * 开宝箱
     * @param userId
     * @param isSubmit
     * @return
     */
    @RequestMapping(value = UserMsgReq.OPEN_TREASURE)
    public Map<Object,Object> openTreasure(final Long userId, final Long isSubmit, HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin", "*");
        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                //判断是否超过今天开宝箱可以获得的最大金币数
                //获取用户当天开宝箱获得的金币总和
                String goldToday = goldRecordService.getOpenGoldToday(userId, 4);
                if(StringUtils.isEmpty(goldToday)){
                    goldToday = "0";
                }
                //获取用户当天最大可以获得的金币数
                StaticGoldConfig staticGoldConfig = userService.getReadGoldConfig("OPEN_TREASURE");
                int openGoldMax = staticGoldConfig.getHighest();
                if(Integer.parseInt(goldToday) < openGoldMax){
                    //判断时间差
                    Map leadTime = goldRecordService.leadTime(userId);
                    //计算下次开宝箱时间
                    Map leadTime2 = goldRecordService.leadTimeTwo(userId);
                    if(StringUtils.isEmpty(leadTime.get("createDate"))){
                        //该用户还没开过宝箱，直接开启宝箱
                        //添加金币和金币记录
                        if(isSubmit != 0){
                            userService.addUserGoldRecord(4,userId, Integer.valueOf(staticGoldConfig.getValue()), null);
                            userService.updateUserInfo(userId, Integer.parseInt(staticGoldConfig.getValue()));
                            dataMap.put("result", "success");
                            dataMap.put("msg", "该用户还没开过宝箱,直接开启宝箱");
                            dataMap.put("data", false);
                            dataMap.put("getGold", staticGoldConfig.getValue());
                            dataMap.put("leadTime", leadTime2);
                        }else{
                            dataMap.put("result", "success");
                            dataMap.put("msg", "该用户还没开过宝箱,可以开启宝箱");
                            dataMap.put("data", true);
                            dataMap.put("getGold", 0);
                            dataMap.put("leadTime", leadTime);
                        }
                    }else{
                        String leadSecond = leadTime.get("leadSecond").toString();
                        if(Integer.parseInt(leadSecond) >= 0){
                            //可以开宝箱
                            if(isSubmit != 0){
                                userService.addUserGoldRecord(4,userId, Integer.valueOf(staticGoldConfig.getValue()), null);
                                userService.updateUserInfo(userId, Integer.parseInt(staticGoldConfig.getValue()));
                                dataMap.put("result", "success");
                                dataMap.put("msg", "可以开宝箱，已开");
                                dataMap.put("data", false);
                                dataMap.put("getGold", staticGoldConfig.getValue());
                                dataMap.put("leadTime", leadTime2);
                            }else{
                                dataMap.put("result", "success");
                                dataMap.put("msg", "可以开宝箱");
                                dataMap.put("data", true);
                                dataMap.put("getGold", staticGoldConfig.getValue());
                                dataMap.put("leadTime", leadTime);
                            }
                        }else {
                            //还未到下次开宝箱时间，返回下次开箱时间
                            dataMap.put("result", "success");
                            dataMap.put("msg", "还未到下次开宝箱时间，返回下次开箱时间");
                            dataMap.put("data", false);
                            dataMap.put("getGold", 0);
                            dataMap.put("leadTime", leadTime);
                        }
                    }
                }else{
                    dataMap.put("result", "Failure");
                    dataMap.put("msg", "已经达到今日最多开宝箱获得金币数");
                    dataMap.put("data", false);
                    dataMap.put("getGold", false);
                }
            }
        });

        return dataMap;
    }

    /**
     * 周排行和总排行列表
     * @return
     */
    @RequestMapping(value = UserMsgReq.GOLD_RANKINGS, method = RequestMethod.GET)
    public Map<Object,Object> rankings(){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {

            }

            @Override
            public void handle() throws Exception {
                //周排行
                List<AllRankingVO> list = moneyRecordService.weekRankings();
                //总排行
                List<AllRankingVO> listAll = moneyRecordService.allRankings();
                dataMap.put("result", "success");
                dataMap.put("msg", "相关信息返回成功");
                dataMap.put("weekRankings", list);
                dataMap.put("allRankings", listAll);

            }
        });

        return dataMap;

    }

    /**
     * 徒弟进贡排行榜
     * @return
     */
    @RequestMapping(value = UserMsgReq.APPRENTICE_PAY_RANKING, method = RequestMethod.GET)
    public Map<Object,Object> apprenticePayRanking(final Long userId){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                List<UserGoldRecord> list = goldRecordService.listUserGoldRecord(userId);
                dataMap.put("result", "success");
                dataMap.put("msg", "徒弟进贡排行榜");
                dataMap.put("data", list);

            }
        });

        return dataMap;

    }

    /**
     * 用户消息通知
     * @param userId
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = UserMsgReq.MSG_GET)
    public Map<Object,Object> msgGet(final Long userId, final PageRequest pageRequest){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                String type = "MSG";
                List<UserInformation> list = iInformationService.listInformationPage(userId, type, pageRequest);
                dataMap.put("result", "success");
                dataMap.put("msg", "相关信息返回成功");
                dataMap.put("data", list);
            }
        });

        return dataMap;
    }


    /**
     * 用户经纬度设置
     * @return
     */
    @RequestMapping(value = UserMsgReq.LOCATION_SET, method = RequestMethod.POST)
    public Map<Object,Object> locationSet(final Long userId, final float lat, final float lng){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
            }

            @Override
            public void handle() throws Exception {
                userService.addUserLocation(userId, lat, lng);
                dataMap.put("result", "success");
                dataMap.put("msg", "返回信息成功");
            }
        });

        return dataMap;
    }

    /**
     * 用户公告获取（WALLET  钱包 \n  FAQ    常见问题  \n  RECRUIT 收徒界面）
     * @param userId
     * @param pageRequest
     * @return
     */
    @RequestMapping(value = UserMsgReq.NOTICE_GET)
    public Map<Object,Object> noticeGet(final Long userId, final PageRequest pageRequest){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                String type = "NOTICE";
                List<UserInformation> list = iInformationService.listInformationPage(userId, type, pageRequest);
                dataMap.put("result", "success");
                dataMap.put("msg", "相关信息返回成功");
                dataMap.put("data", list);
            }
        });

        return dataMap;
    }



    //随机数
    public Boolean rand(int prob){
        int random = (int)(Math.random() * 100);
        if(random < prob){
            return true;
        }else{
            return false;
        }
    }



    /**
     * 登录随机红包(新人登录红包)
     * @param userId
     * @return
     */
    @RequestMapping(value = UserMsgReq.REGISTER_MONEY_ADD, method = RequestMethod.GET)
    public Map<Object,Object> registerMoneyAdd(final Long userId){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                Boolean result = userService.getNewUserMoney(userId);
                if (result){
                    dataMap.put("result", "success");
                    dataMap.put("msg", "获得新人登录红包");
                    dataMap.put("newbieRegisterMoney", 5);

                }else{
                    dataMap.put("result", "failure");
                    dataMap.put("msg", "该活动仅限新人，您已经是老用户了");
                    dataMap.put("newbieRegisterMoney", 0);
                }

            }
        });

        return dataMap;
    }


    /**
     * 根据类型返回信息
     * @param type
     * @return
     */
    @RequestMapping(value = UserMsgReq.FAKE_DATE_GET, method = RequestMethod.GET)
    public Map<Object,Object> fakeDateGet(final String type){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotNull(type, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                List<StaticFakeData> staticFakeDataList = userService.getFakeData(type);
                if(!staticFakeDataList.isEmpty()) {
                    dataMap.put("result", "success");
                    dataMap.put("msg", "返回数据成功");
                    dataMap.put("data", staticFakeDataList);
                }else{
                    dataMap.put("result", "failed");
                    dataMap.put("msg", "未找到相关数据");
                    dataMap.put("data", staticFakeDataList);
                }
            }
        });

        return dataMap;
    }

    /**
     * 活动奖励
     * @return
     */
    @RequestMapping(value = UserMsgReq.ACTIVITY_REWARD, method = RequestMethod.GET)
    public Map<Object,Object> activityReward(final Long userId, final String startDate, final String endDate, HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin", "*");
        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                //获取活动时间内新增徒弟数量
                int newNum = userService.getActivityApprenticeSum(userId, startDate, endDate);
                //获取活动时间内的有效徒弟数量
                int effectiveNum = goldRecordService.getEffectiveApprenticeNum(userId, startDate, endDate);
                //累计奖励
                int goldNum = goldRecordService.getActivityGoldSum(userId, startDate, endDate);
                //段位
                String dan = "";
                //需要多少个有效徒弟解锁
                int unlockDan = 0;
                if(effectiveNum > 1 && effectiveNum < 3){
                    dan = DanRulesEnum.BRONZE.getName();
                    unlockDan = 3 - effectiveNum;
                }else if(effectiveNum >=3 && effectiveNum < 10){
                    dan = DanRulesEnum.SILVER.getName();
                    unlockDan = 10 - effectiveNum;
                }else if(effectiveNum >= 10 && effectiveNum < 66){
                    dan = DanRulesEnum.GOLD.getName();
                    unlockDan = 66 - effectiveNum;
                }else if(effectiveNum >= 66 && effectiveNum < 200){
                    dan = DanRulesEnum.PLATINUM.getName();
                    unlockDan = 200 - effectiveNum;
                }else if(effectiveNum >= 200 && effectiveNum < 400){
                    dan = DanRulesEnum.DIAMONDS.getName();
                    unlockDan = 400 - effectiveNum;
                }else if(effectiveNum >= 400 && effectiveNum < 1000){
                    dan = DanRulesEnum.MASTER.getName();
                    unlockDan = 1000 - effectiveNum;
                }else if(effectiveNum >= 1000){
                    dan = DanRulesEnum.KING.getName();
                }else{
                    dan = "未知";
                }

                dataMap.put("dan", dan);
                dataMap.put("unlockDan", unlockDan);
                dataMap.put("newNum", newNum);
                dataMap.put("effectiveNum", effectiveNum);
                dataMap.put("goldNum", goldNum);
            }
        });

        return dataMap;
    }

    /**
     * 我的徒弟列表
     * @return
     */
    @RequestMapping(value = UserMsgReq.LIST_APPRENTICE, method = RequestMethod.GET)
    public Map<Object,Object> getListApprentice(final Long userId, final String type, final PageRequest pageRequest, HttpServletResponse response){

        response.setHeader("Access-Control-Allow-Origin", "*");
        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotZero(userId, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {
                List<ApprenticeRepVO> list = Lists.newArrayList();
                if(type.equals("avaken")){
                    list = goldRecordService.listAwakenApprentice(userId, pageRequest);
                }
                if(type.equals("effective")){
                    list = goldRecordService.listEffectiveApprentice(userId, pageRequest);
                }
                if(type.equals("my")){
                    list = goldRecordService.listMyApprentice(userId, pageRequest);

                }

                dataMap.put("data", list);
                dataMap.put("state", "success");
                dataMap.put("msg", "查询成功");
            }
        });

        return dataMap;
    }

}
