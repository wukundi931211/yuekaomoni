package com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.jiekou;

import com.meituan.tianlong.yuekaomoni.network.OkCallBack;

import java.util.Map;

public interface GouWuCarts {
        void getCarts(String url, Map<String,String> map , OkCallBack okCallBack);
}
