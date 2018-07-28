package com.meituan.tianlong.yuekaomoni.mvp.view.Adapter.gouwu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meituan.tianlong.yuekaomoni.R;


public class AddSumView extends LinearLayout {

    private Context context;

    private ImageView add;
    private ImageView reduce;
    private TextView sum;

    private int totalSum = 1 ;//记录商品数量
    private int limitSum = 5 ;//最多添加几件商品
    //定义点击事件
    private SumClickListener  listener;

    public void setSumClickListener(SumClickListener listener) {
        this.listener = listener;
    }


    public void setSum(int num) {
        totalSum = num;//设置初试数量
        this.sum.setText("" + num);
    }

    /**
     * 设置限购的数量
     *
     * @param limitSum
     */
    public void setLimitSum(int limitSum) {
        if (limitSum > 0) {
            this.limitSum = limitSum;
        } else {
            this.limitSum = 5;
        }
    }
    public AddSumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initview(context);
    }

    private void initview(Context context) {
        View view = View.inflate(context, R.layout.layout_add_sum_view,this);
        add  = view.findViewById(R.id.add);
        reduce = view.findViewById(R.id.reduce);
        sum = view.findViewById(R.id.txt_sum);
        //增加
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addClick();
            }
        });
        //减少
        reduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                reduceClick();
            }
        });

    }

    /**
     * 减少商品
     */
    public void reduceClick() {
        totalSum--;
        if (totalSum > 0) {
            sum.setText(totalSum + "");
        } else {
            totalSum = 1;//TODO
            Toast.makeText(context, "不能再减少了", Toast.LENGTH_SHORT).show();
            sum.setText(totalSum + "");
        }

        //回调事件
        if (listener != null) {
            listener.sumClick(totalSum);
        }
    }
    /**
     * private int totalSum = 1 ;//记录商品数量
     *private int limitSum = 5 ;//最多添加几件商品
     */
    /**
     * 增加数量
     */
    public void addClick() {
        totalSum ++ ;
        if (totalSum >= limitSum){
            totalSum = limitSum;
            Toast.makeText(context, "每人限购" + limitSum + "件", Toast.LENGTH_SHORT).show();
        }

        if (listener != null){
            listener.sumClick(totalSum);
        }

        sum.setText(totalSum+"");
    }

    /**
     *回调商品数量点击事件
     */
     interface SumClickListener {
        void sumClick(int psum);
    }
}
