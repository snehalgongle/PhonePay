package com.snake.phonepay.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snake.phonepay.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;

public final class BaseRecyclerAdapter<T,V extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<V>> {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private final int layoutResourceId;
    private final int bindVariableId;
    private final ArrayList<T> items;
    private Map dataVariables;
    private final OnDataBindCallback onDataBindCallback;
    private int visibleThreshold = 1;
    public static int lastVisibleItem, totalItemCount;
    public  boolean loading=false;
    private OnLoadMoreListener onLoadMoreListener;

    public BaseRecyclerAdapter(int layoutResourceId, int bindVariableId, ArrayList<T> items, Map dataVariables, OnDataBindCallback onDataBindCallback) {
        this.layoutResourceId = layoutResourceId;
        this.bindVariableId = bindVariableId;
        this.items = items;
        this.dataVariables = dataVariables;
        this.onDataBindCallback = onDataBindCallback;
    }

    @NonNull
    @Override
    public BaseViewHolder<V> onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), layoutResourceId, parent, false), onDataBindCallback);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<V> viewHolder, int position) {
        if (dataVariables != null) {
            /*for (data in dataVariables!!.entries) {
                holder.viewDataBinding.setVariable(data.key, data.value)
            }*/
        }
        viewHolder.viewDataBinding.setVariable(bindVariableId, getItem(position));
        viewHolder.viewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(int position){
        return items.get(position);
    }

    public ArrayList<T> getItems(){
        return items;
    }

    public void addDataSet(List<T> data) {
        items.addAll(data);
        notifyDataSetChanged();
    }

    public void clearDataSet() {
        items.clear();
        notifyDataSetChanged();
    }

    public  void removeDataSet(int position){
        items.remove(position);
        notifyDataSetChanged();
    }


    public void add(T response) {
        items.add(response);
        notifyItemInserted(items.size() - 1);
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setScrolllistener(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                        Log.d("totalItemCount",""+totalItemCount);
                        Log.d("LastandVisible",""+(lastVisibleItem + visibleThreshold));
                        Log.d("loading",""+loading);
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                        Log.d("-----","end");
                        if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            loading = true;
                            if (onLoadMoreListener != null) {
                                Log.d("loadmore","loadmore called()");
                                onLoadMoreListener.onLoadMore();
                            }
                        }
                    }
                }
            });
        }
    }


}

