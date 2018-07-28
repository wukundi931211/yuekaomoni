package com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.fangfa;

import com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.jiekou.GouWuCarts;
import com.meituan.tianlong.yuekaomoni.network.Ok;
import com.meituan.tianlong.yuekaomoni.network.OkCallBack;

import java.util.Map;

public class GouWuTask implements GouWuCarts{

    @Override
    public void getCarts(String url, Map<String, String> map, OkCallBack okCallBack) {
        Ok.getOk().post(url,map,okCallBack);
    }
}
