package com.meituan.tianlong.yuekaomoni.mvp.presenter.gouwu;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.meituan.tianlong.yuekaomoni.GouWuActivity;
import com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.beans.Data;
import com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.fangfa.GouWuTask;
import com.meituan.tianlong.yuekaomoni.mvp.view.gouwu.ShopIView;
import com.meituan.tianlong.yuekaomoni.network.OkCallBack;
import com.meituan.tianlong.yuekaomoni.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class GouIPresent implements GouWuIPresenter{
    //注入Model
    GouWuTask task;
    //注入view层
    ShopIView view;


    public GouIPresent(GouWuActivity activity) {
        this.task = new GouWuTask();
        this.view = activity;
    }

    @Override
    public void getCarts() {
        Map<String,String> map = new HashMap<>();
        map.put("uid","4243");
        map.put("token","94A2C256471982A75C170CAB844FE4FE");
        task.getCarts(Constants.GET_CARTS, map, new OkCallBack() {
            @Override
            public void success(String json) {
                Gson gson = new Gson();
                Data data = gson.fromJson(json, Data.class);
                if (TextUtils.equals(data.code,"0")){
                    view.ShowData(data.data);
                }else {
                    view.ShowError(data.msg);
                }
            }

            @Override
            public void failed(String json) {
                //TODO 当错时 展示信息
                view.ShowError(json);
            }
        });
    }

    @Override
    public void Destory() {
        view = null;
    }
}
