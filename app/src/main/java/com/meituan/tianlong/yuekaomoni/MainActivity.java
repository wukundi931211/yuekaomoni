package com.meituan.tianlong.yuekaomoni;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fynn.fluidlayout.FluidLayout;
import com.meituan.tianlong.yuekaomoni.mvp.model.dao.MySql;
import com.meituan.tianlong.yuekaomoni.mvp.model.dao.SqliteDao;
import com.meituan.tianlong.yuekaomoni.mvp.view.Adapter.MainAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button,lishi1;
    private FluidLayout fluidLayout;

    private TagFlowLayout last_tag;
    private KeyAdapter lastAdapter;
    //    private MainAdapter adapter;
    //    private ListView listView;
    //    private SqliteDao dao;
    //    private List<String> list;
    private List<String> list1;

    private String mNAME[] = {
            "考拉三周年人气热销榜",
            "电动牙刷",
            "尤妮佳",
            "豆豆鞋",
            "沐浴露",
            "日东红茶",
            "榴莲",
            "电动牙刷",
            "雅诗莱黛",
            "豆豆鞋"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initview();
    }

    private void initview() {
        editText = findViewById(R.id.souzhou);
        button = findViewById(R.id.sousuo);
        lishi1 = findViewById(R.id.btn_clear);
        fluidLayout = findViewById(R.id.liushi);
//        listView = findViewById(R.id.list_ite);
        //流式布局
        list1 = new ArrayList<>();
        list1.add("冰激淋");
        list1.add("麻辣小龙虾");
        list1.add("汉堡");
        list1.add("煎饼");
        last_tag = findViewById(R.id.hot_tag);
        lastAdapter = new KeyAdapter(list1);

        last_tag.setAdapter(lastAdapter);


        //数据库
//        dao = new SqliteDao(this);
//        list = new ArrayList<>();
//        list  = dao.select();
//        list.clear();
//        adapter = new MainAdapter(this,list);
//        listView.setAdapter(adapter);


        //搜索跳转
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keywords = editText.getText().toString();
                //dao方法
                //dao.add(keywords);

                if (!TextUtils.isEmpty(keywords)) {
                    list1.add(keywords);
                    lastAdapter.notifyDataChanged();
                }

//                list = dao.select();
//                adapter = new MainAdapter(MainActivity.this,list);
//                listView.setAdapter(adapter);
                //跳转传值
                Intent intent = new Intent(MainActivity.this, LieBiaoActivity.class);
                intent.putExtra("keywords", keywords);
                startActivity(intent);
            }
        });
        //点击事件，流式布局
        last_tag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String key = lastAdapter.getItem(position);//获取点击项目的值
                //TODO 跳转到产品列表页面
                Intent intent = new Intent(MainActivity.this, LieBiaoActivity.class);
                intent.putExtra("key", key);
                startActivity(intent);
                return true;
            }
        });

        lishi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list1.clear();
                lastAdapter.notifyDataChanged();
            }
        });



        //流式布局
        for (int i = 0; i < mNAME.length; i++) {
            FluidLayout.LayoutParams params =
                    new FluidLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );
            params.setMargins(12, 12, 12, 12);
            TextView textView = new TextView(this);
            textView.setText(mNAME[i]);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundResource(R.drawable.flow_yangshi);

            fluidLayout.addView(textView, params);
        }

    }

    class KeyAdapter extends TagAdapter<String> {

        public KeyAdapter(List<String> datas) {
            super(datas);
        }

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText(s);
            textView.setBackgroundResource(R.drawable.flow_yangshi);

            return textView;
        }
    }
}
