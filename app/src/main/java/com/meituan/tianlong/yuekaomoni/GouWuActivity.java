package com.meituan.tianlong.yuekaomoni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.beans.DataBean;
import com.meituan.tianlong.yuekaomoni.mvp.presenter.gouwu.GouIPresent;
import com.meituan.tianlong.yuekaomoni.mvp.view.Adapter.gouwu.GouWuAdapter;
import com.meituan.tianlong.yuekaomoni.mvp.view.gouwu.ShopIView;

import java.util.List;

public class GouWuActivity extends AppCompatActivity implements ShopIView {
    private ExpandableListView ListView;
    //全选
    private ImageView img_check_all;
    //总价
    private TextView txt_total_price;
    private LinearLayout btn_check_all;

    //注入P层
    GouIPresent gouIPresent;
    //适配器
    GouWuAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gou_wu);
        //初始化
        initView();
        gouIPresent = new GouIPresent(this);
        gouIPresent.getCarts();
    }

    private void initView() {
        //获得组件
        ListView = findViewById(R.id.cartList);
        //TODO 设置二级列表 不能关闭
        ListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return  false;//返回true,表示不可点击
            }
        });
        //TODO 去掉默认箭头
        ListView.setGroupIndicator(null);

        //适配器
        adapter = new GouWuAdapter(this);
        ListView.setAdapter(adapter);

        //全选
        img_check_all = findViewById(R.id.img_check_all);
        //总价
        txt_total_price = findViewById(R.id.txt_total_price);
        btn_check_all = findViewById(R.id.btn_check_all);
        //选中
        btn_check_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //适配器选中 // TODO 全选反选
                if (adapter.isCheckAll()){
                    adapter.invertAll();//反选
                }else {
                    adapter.checkAll();//全选
                }
              //  adapter.sumPrice();
            }
        });


        adapter.setTxt_total_price(txt_total_price);
        adapter.setImg_check_all(img_check_all);

    }


    //适配器中添加数据
    @Override
    public void ShowData(List<DataBean> data) {
        adapter.addData(data);
        adapter.expandGroup(ListView);
        adapter.checkAll();

       // adapter.sumPrice();
    }

    @Override
    public void ShowError(String error) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gouIPresent.Destory();
    }


}
