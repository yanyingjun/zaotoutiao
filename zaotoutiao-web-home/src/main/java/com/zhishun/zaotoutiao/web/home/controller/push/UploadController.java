/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package com.zhishun.zaotoutiao.web.home.controller.push;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.zhishun.zaotoutiao.biz.service.IInfosImageService;
import com.zhishun.zaotoutiao.common.util.AssertsUtil;
import com.zhishun.zaotoutiao.common.util.DateUtil;
import com.zhishun.zaotoutiao.common.util.LoggerUtils;
import com.zhishun.zaotoutiao.common.util.OSSClientUtil;
import com.zhishun.zaotoutiao.core.model.entity.InfosImage;
import com.zhishun.zaotoutiao.core.model.enums.ErrorCodeEnum;
import com.zhishun.zaotoutiao.core.model.exception.ZhiShunException;
import com.zhishun.zaotoutiao.web.home.callback.ControllerCallback;
import com.zhishun.zaotoutiao.web.home.constant.request.ZttWebMsgReq;
import com.zhishun.zaotoutiao.web.home.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: UploadController, v0.1 2018年03月22日 10:10闫迎军(YanYingJun) Exp $
 */
@RestController
public class UploadController extends BaseController{

    public static Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * 新闻图片路径
     */
    private static String NEWS_IMAGE_DIR = "news/image/";


    private static String VIDEO_DRI = "video/";

    @Autowired
    private IInfosImageService infosImageService;

    /**
     * 上传图片
     * @param pic
     * @return
     */
    @RequestMapping(value = ZttWebMsgReq.ZTT_UPLOAD_LIST_PIC_REQ)
    public Map<Object,Object> uploadListPic(final List<MultipartFile> pic, String infoType){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotEmpty(pic, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {

                try {
                    for(MultipartFile file : pic){
                        String name = OSSClientUtil.uploadImgOss(file, NEWS_IMAGE_DIR, infoType);
                        String imgUrl = OSSClientUtil.getImgUrl(name, NEWS_IMAGE_DIR);
                        InfosImage infosImage = new InfosImage();
                        infosImage.setPicName(name);
                        infosImage.setPicUrl(imgUrl);
                        infosImage.setCreateDate(DateUtil.toString(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));
                        dataMap.put("infosImage", infosImage);
                    }
                    dataMap.put("result", "success");
                    dataMap.put("msg", "上传图片成功");
                    System.out.println(JSON.toJSON(dataMap));
                }catch(IOException e){
                    dataMap.put("result", "fail");
                    dataMap.put("msg", "上传图片失败");
                    LoggerUtils.error(LOGGER, "上传图片异常，异常信息：" + e.getMessage());
                }
            }
        });

        return dataMap;

    }

    /**
     * 上传视频
     * @param pic
     * @return
     */
    @RequestMapping(value = ZttWebMsgReq.ZTT_UPLOAD_VIDEO_REQ)
    public Map<Object,Object> uploadListVideo(final MultipartFile video, String infoType){

        final Map<Object,Object> dataMap = Maps.newHashMap();
        this.excute(dataMap, null, new ControllerCallback() {
            @Override
            public void check() throws ZhiShunException {
                AssertsUtil.isNotNull(video, ErrorCodeEnum.SYSTEM_ANOMALY);
            }

            @Override
            public void handle() throws Exception {

                try {
                    String name = OSSClientUtil.uploadImgOss(video, VIDEO_DRI, infoType);
                    String videoUrl = OSSClientUtil.getImgUrl(name, VIDEO_DRI);
                    InfosImage infosImage = new InfosImage();
                    infosImage.setPicName(name);
                    infosImage.setPicUrl(videoUrl);
                    infosImage.setCreateDate(DateUtil.toString(new Date(), DateUtil.DEFAULT_DATETIME_FORMAT));
                    dataMap.put("infosImage", infosImage);
                    dataMap.put("result", "success");
                    dataMap.put("msg", "上传视频成功");
                    System.out.println(JSON.toJSON(dataMap));
                }catch(IOException e){
                    dataMap.put("result", "fail");
                    dataMap.put("msg", "上传视频失败");
                    LoggerUtils.error(LOGGER, "上传视频异常，异常信息：" + e.getMessage());
                }
            }
        });

        return dataMap;

    }


}
