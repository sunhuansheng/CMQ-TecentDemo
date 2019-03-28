package com.hpm.blog.api;

import com.alibaba.fastjson.JSONObject;
import com.hpm.blog.model.Kd;
import com.hpm.blog.service.KdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//控制层:快递api
@RestController
public class KdApi {

    private KdService kdService;

    @Autowired
    public KdApi(KdService kdService) {
        this.kdService = kdService;
    }

//    快递鸟即时查询
    @RequestMapping("/api/selectKd1002")
    @PostMapping("")
    public Object selectKd1002( @RequestBody Kd kd) {

        JSONObject jsonObject = new JSONObject();
        try {
            String result = kdService.getOrderTracesByJson(kd.getExpCode(), kd.getExpNo());
//            System.out.print(result);
            jsonObject = JSONObject.parseObject(result.toString());
//            System.out.print(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    //    快递鸟单号识别查询
    @RequestMapping("/api/selectKd2002")
    @PostMapping("")
    public Object selectKd2002( @RequestBody Kd kd) {

        JSONObject jsonObject = new JSONObject();
        try {

            String result = kdService.getOrderTracesByJson(kd.getExpNo());
            jsonObject = JSONObject.parseObject(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
