package com.meituan.tianlong.yuekaomoni.mvp.view.gouwu;

import com.meituan.tianlong.yuekaomoni.BasePresent;

import com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.beans.DataBean;
import com.meituan.tianlong.yuekaomoni.mvp.presenter.gouwu.GouIPresent;
import com.meituan.tianlong.yuekaomoni.mvp.presenter.gouwu.GouWuIPresenter;

import java.util.List;

public interface ShopIView {
    void ShowData(List<DataBean> data);

    void ShowError(String error);
}
