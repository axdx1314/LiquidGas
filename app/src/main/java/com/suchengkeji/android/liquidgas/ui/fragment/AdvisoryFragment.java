package com.suchengkeji.android.liquidgas.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.suchengkeji.android.liquidgas.R;
import com.suchengkeji.android.liquidgas.commontadapter.CommonRecyclerAdapter;
import com.suchengkeji.android.liquidgas.commontadapter.MyViewHolder;
import com.suchengkeji.android.liquidgas.commontadapter.bean.Data;
import com.suchengkeji.android.liquidgas.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 咨询
 * create an instance of this fragment.
 */
public class AdvisoryFragment extends Fragment {
    private final String TAG = "===->>>" + this.getClass();
    @BindView(R.id.advisory_rec)
    RecyclerView advisoryRec;
    Unbinder unbinder;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_advisory, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbarTitle.setText(getResources().getString(R.string.string_consultation));
        initDatas();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initDatas() {
        advisoryRec.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<Data> dataList = new ArrayList<>();
//        for (int i = 0; i < 2; i++) {
//            Data data = new Data("咨询标题", "这是一条新消息！这是一条新消息！这是一条新消息！重要的事说三遍。");
//            dataList.add(data);
//        }
        MyAdapter myAdapter = new MyAdapter(getContext(), dataList, R.layout.adcisory_rec_item);
        advisoryRec.setAdapter(myAdapter);
        myAdapter.setmOnItemListener(new CommonRecyclerAdapter.OnItemListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                LogUtils.d(TAG, "==========onItemClickListener===========" + position + "");
            }

            @Override
            public void onLongClickListener(View view, int position) {
                LogUtils.d(TAG, "==========onLongClickListener===========" + position + "");
            }
        });
    }


    public static AdvisoryFragment newInstance() {
        Bundle args = new Bundle();
        AdvisoryFragment fragment = new AdvisoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * rec---适配器
     */
    public class MyAdapter extends CommonRecyclerAdapter<Data> {
        public MyAdapter(Context context, List<Data> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        public void convert(MyViewHolder holder, Data item) {
            holder.setText(R.id.tv_title, item.getTitle());
            holder.setText(R.id.tv_content, item.getContent());
        }
    }
}
