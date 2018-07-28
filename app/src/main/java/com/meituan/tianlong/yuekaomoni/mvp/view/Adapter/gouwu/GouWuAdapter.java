package com.meituan.tianlong.yuekaomoni.mvp.view.Adapter.gouwu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.meituan.tianlong.yuekaomoni.R;
import com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.beans.DataBean;
import com.meituan.tianlong.yuekaomoni.mvp.model.gouwu.beans.ListBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GouWuAdapter extends BaseExpandableListAdapter {

    private List<DataBean> seller;//要请求数据
    private LayoutInflater inflater;//初始化视图
    private Context context;//上下文环境
    private TextView txt_total_price; //合计总价
    private ImageView img_check_all; //全选状态UI更新
    private boolean isCheckAll = false;//全选状态标记

    //图片
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    //构造器
    public GouWuAdapter(Context context) {
        //TODO 在这里初始化，避免空指针错误，下面在使用就不需要判断空指针
        this.seller = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        imageLoader = ImageLoader.getInstance();

    }

    /**
     * 绑定顶部的总计价格控件
     * @param txt_total_price
     */
    public void setTxt_total_price(TextView txt_total_price) {
        this.txt_total_price = txt_total_price;
    }
    /**
     * 绑定全选按钮的控件
     * @param img_check_all
     */
    public void setImg_check_all(ImageView img_check_all) {
        this.img_check_all = img_check_all;
    }

    /**
     * 添加数据，这样写入数据，可以有效避免空指针操作
     * @param list
     */
    public void addData(List<DataBean> list){
        seller.addAll(list);//添加数据
        notifyDataSetChanged();//刷新数据
    }

    /**
     * 默认展开  默认全部展开
     * @param listView
     */
    public void expandGroup(ExpandableListView listView){
        for (int i = 0; i <getGroupCount() ; i++) {
            listView.expandGroup(i);
        }
    }


    public boolean isCheckAll() {
        return isCheckAll;
    }

    /**
     * 全选方案
     */
    public void checkAll(){
        isCheckAll  = true;//全选
        //遍历店铺
        for (DataBean seller: seller) {
            seller.isCheck = true;
            //遍历商品
            List<ListBean> list = seller.list;
            for (ListBean listBean : list) {
                if (isCheckAll){
                    //商品中的字段  0 为未选中 1 为选中
                    listBean.selected= 1;
                }
            }
            //更新UI
            updateSelectUI();
        }
    }
    /**
     * 反选方案
     */
    public void invertAll(){
        isCheckAll = false;//反选
        for (DataBean seller:seller) {//遍历商家
            seller.isCheck = false;//设置未选中
            for (ListBean listBean:seller.list ) {
                listBean.selected = 0;
            }
        }
        updateSelectUI();
    }
    /**
     * 刷新页面
     */
    public void updateSelectUI() {
        //全选按钮的处理
        if (isCheckAll){
            //图片的选中
            img_check_all.setImageResource(R.drawable.ic_checked);
        }else {
            //图片的未选中
            img_check_all.setImageResource(R.drawable.ic_uncheck);
        }
        //
        sumPrice();
        //刷新
        notifyDataSetChanged();
    }
    /**
     * 计算所选产品价格
     */
    public void sumPrice(){
        //计算总价
        int total = 0;

        for (DataBean dataBean: seller) {
            //选中的店铺
            if (dataBean.isCheck){
                for (ListBean listBean:dataBean.list) {
                    if (listBean.selected == 1){
                        //选中的产品的价格计算
                        //解析价格
                      //  int price =(int) Double.parseDouble(listBean.price);

                        total = total + listBean.price * listBean.num;
                    }
                }
            }
        }
        txt_total_price.setText("合计 ￥ " + total);
    }

    /**
     * 检查是否
     * @return
     */
    public boolean isQueryCheckAll() {
        int total = 0 ;//全部商品
        int checkNum= 0;//选中产品
        for (DataBean dataBean: seller) {//遍历商家
            total += dataBean.list.size();//统计数量
            if (dataBean.isCheck){//只需要遍历选中的商家
                for (ListBean listBean:dataBean.list) {//遍历产品
                    if (listBean.selected == 1){//如果选中产品
                        checkNum += 1;//选中产品数量+1
                    }
                }
            }
        }
        return checkNum == total;//比较。相等，即全选
    }

    /**
     * 获得商家总数据的长度
     * @return
     */
    @Override
    public int getGroupCount() {
        return seller.size();
    }

    /**
     * 获得商品总数据的长度
     * @param i
     * @return
     */
    @Override
    public int getChildrenCount(int i) {
        return seller.get(i).list.size();
    }

    /**
     * 获得商家类
     * @param i
     * @return
     */
    @Override
    public DataBean getGroup(int i) {
        return seller.get(i);
    }

    /**
     * 获得商品类
     * @param i
     * @param i1
     * @return
     */
    @Override
    public ListBean getChild(int i, int i1) {
        return seller.get(i).list.get(i1);
    }

    /**
     * 固定ID
     * @param i
     * @return
     */
    @Override
    public long getGroupId(int i) {
        DataBean group = getGroup(i);
        return group.sellerid;
    }

    @Override
    public long getChildId(int i, int i1) {
        ListBean child = getChild(i, i1);
        return child.sellerid;
    }

    /**
     * 允许固定ID
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }


    /**
     * 获得一级菜单的
     * @param i
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        DataBeanViewHolder holder;
        if (view == null){
            view = inflater.inflate(R.layout.item_cart_seller,viewGroup,false);
            holder = new DataBeanViewHolder(view);
            view.setTag(holder);

        }else {
            holder = (DataBeanViewHolder) view.getTag();
        }
        final DataBean dataBean = getGroup(i);
        holder.sellerName.setText(dataBean.sellerName);
        //选择
        if (dataBean.isCheck){
            holder.sellerCheck.setImageResource(R.drawable.ic_checked);
        }else {
            holder.sellerCheck.setImageResource(R.drawable.ic_uncheck);
        }
        //一级菜单复选框的单击事件
        holder.sellerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 商家的单击事件
                clickSellerCheck(dataBean);
            }
        });
        return view;
    }
    //选择商家选中
    private void clickSellerCheck(DataBean dataBean) {

        List<ListBean> list = dataBean.list;
        if (dataBean.isCheck == true){
            dataBean.isCheck = false;
        }else {
            dataBean.isCheck = true;
        }

        //遍历商品
        for (ListBean listBean: list) {
            if (dataBean.isCheck){
                listBean.selected=1;
            }else {
                listBean.selected=0;
            }
        }
        isCheckAll = isQueryCheckAll();//查询是否为全部选中状态
        //计算价格
        sumPrice();
        //刷新UI
        updateSelectUI();
    }

    /**
     * 获得二级菜单  商品
     * @param i
     * @param i1
     * @param b
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ListBeanViewHolder listholder;
        if (view == null){
            view = inflater.inflate(R.layout.item_cart_product,viewGroup,false);
            listholder = new ListBeanViewHolder(view);
            view.setTag(listholder);
        }else {
            listholder = (ListBeanViewHolder) view.getTag();
        }
        final ListBean child = getChild(i, i1);

        listholder.listBeanName.setText(child.title);
        listholder.listBeanPrice.setText("￥"+child.price);

        listholder.addSumView.setSum(child.num);


        String url = child.images.split("\\|")[0];
        imageLoader.displayImage(url,listholder.listBeanImages,options);

        if (child.selected ==1){
            listholder.listCheck.setImageResource(R.drawable.ic_checked);
        }else {
            listholder.listCheck.setImageResource(R.drawable.ic_uncheck);
        }

        //设置选中监听
        listholder.listCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //获取商家
                DataBean dataBean = getGroup(i);
                clickDataBeanCheck(dataBean,child);
            }
        });

        listholder.addSumView.setSumClickListener(new AddSumView.SumClickListener() {
            @Override
            public void sumClick(int psum) {
                child.num = psum;
                sumPrice();
            }
        });

        return view;
    }

    private void clickDataBeanCheck(DataBean dataBean, ListBean child) {
        //全未选中，选择中产品的时候商家也选中，全选按钮是未选中状态
        //全选中，只要有一个未选，影响自己，和全选按钮
        if (child.selected == 1) {
            child.selected = 0;//产品未选中
            //TODO 当多个产品需要处理
            int selectedNum = 0;
            for (ListBean ptemp : dataBean.list) {//遍历产品
                if (ptemp.selected == 1) {
                    selectedNum += 1;//选中增加1
                }
            }
            if (selectedNum > 0) {//产品只要一个选中，商家就是选中状态
                dataBean.isCheck = true;
            } else {
                dataBean.isCheck = false;
            }

        } else {
            child.selected = 1;//产品选中
            dataBean.isCheck = true;//有一个产品选中，商家即为选中状态
        }

        isCheckAll = isQueryCheckAll();//查询是否为全部选中状态

        updateSelectUI();//更新UI
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    /**
     *
     * @param listBean  产品
     * @param spos       位置
     */
    private void clickListBeanCheck(ListBean listBean , int spos){
        if (listBean.selected == 1){
            listBean.selected =0;
        }else {
            listBean.selected =1;
        }

        //获取当前商品的店铺信息
        DataBean dataBean = getGroup(spos);
        //统计产品选中数量
        int sum = 0 ;

        for (ListBean list: dataBean.list) {
            if (list.selected == 1){//判断当前店铺有无选中产品
                sum   = +1;//统计数量
            }
        }
        if (sum == 0){
            dataBean.isCheck =false ;
        }else {
            dataBean.isCheck = true;
        }
        //计算价格
        sumPrice();

        notifyDataSetChanged();


    }
    /**
     * 商家
     */
    static class DataBeanViewHolder {
        TextView sellerName;//商家名称
        ImageView sellerCheck;//选中按钮

        public DataBeanViewHolder(View view){
            sellerName = view.findViewById(R.id.txt_seller_name);
            sellerCheck = view.findViewById(R.id.img_select);
        }
    }

    /**
     * 商品
     */
    static class ListBeanViewHolder {
        TextView listBeanName;
        TextView listBeanPrice;
        ImageView listCheck;
        ImageView listBeanImages;
        AddSumView addSumView;

        public ListBeanViewHolder(View view) {
            listBeanName = view.findViewById(R.id.txt_product_name);
            listBeanPrice=view.findViewById(R.id.txt_product_price);
            listCheck = view.findViewById(R.id.img_select);
            listBeanImages = view.findViewById(R.id.img_product);
            addSumView = view.findViewById(R.id.add_sum);
        }
    }
}
