package com.yuzeduan.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzeduan.activity.R;
import com.yuzeduan.adapter.InsetAdapter;
import com.yuzeduan.bean.Inset;
import com.yuzeduan.model.InsetCallback;
import com.yuzeduan.model.InsetModel;

import java.util.List;

import static com.yuzeduan.bean.Constant.INSETID_URL;
import static com.yuzeduan.bean.Constant.NEW_INSETID_URL;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

public class InsetFragment extends BaseFragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefresh;
    private List<Inset> mInsetList;
    private InsetModel mInsetModel = new InsetModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment, container, false);
            mSwipeRefresh = mView.findViewById(R.id.swipe_refresh);
            mSwipeRefresh.setRefreshing(true);
            mRecyclerView = mView.findViewById(R.id.main_rv_list);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(manager);
        }
        isPrepared = true;
        refreshView();
        return mView;
    }

    public void setView() {
        if(isPrepared && isVisible && isFirst) {
            mSwipeRefresh.setRefreshing(false);
            mInsetModel.getInsetListData(INSETID_URL, new InsetCallback() {
                @Override
                public void onRefresh() {
                }

                @Override
                public void onFinish(List<Inset> list) {
                    mInsetList = list;
                    InsetAdapter adapter = new InsetAdapter(getActivity(), mInsetList, R.layout.inset_item);
                    mRecyclerView.setAdapter(adapter);
                }
            });
        }
    }

    public void refreshView() {
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mInsetModel.queryInsetListData(NEW_INSETID_URL, REFRESH_DATA, new InsetCallback() {
                    @Override
                    public void onRefresh() {
                        setView();
                    }

                    @Override
                    public void onFinish(List<Inset> list) {
                    }
                });
                mSwipeRefresh.setRefreshing(false);
            }
        });
    }
}
