package com.baidu.v_lining05.myapplication;

import com.baidu.v_lining05.mylibrary.network.request.AbsHttpPostRequest;
import com.baidu.v_lining05.mylibrary.util.LogUtil;

import java.util.Map;

/**
 * 创建人：v_lining05
 * 创建时间：2018/4/11
 */
public class RobokitRequest extends AbsHttpPostRequest {


    @Override
    public void onSuccess(String var1, int var2, String var3) {
        LogUtil.d(RobokitRequest.TAG, "url: " + var1);
        LogUtil.d(RobokitRequest.TAG, "statusCode: " + var2);
        LogUtil.d(RobokitRequest.TAG, "response" + var3);
    }

    @Override
    public void onError(String var1, String var2) {
        LogUtil.d(RobokitRequest.TAG, "url:" + var1);
        LogUtil.d(RobokitRequest.TAG, "errMsg:" + var2);
    }

    @Override
    public String getUrl() {
        return "http://privatefm.baidu.com/service/news/detail?id=85034760075&vehicle_channel=radio&cuid=00000000-4ca3-a170-0000-000012c8d39b&uuid=00000000-4ca3-a170-0000-000012c8d39b&channel_id=4&device_from=1&channel=baidu&product=lion&sv=1.5.0&timestamp=1522651782000&sign=0941ED6A73D4D5390EA7D86D50A300EF";
    }

    @Override
    public Map<String, String> getParams() {
        return null;
    }
}
