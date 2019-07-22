package com.snake.phonepay.ui.base;

import android.view.View;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.snake.phonepay.adapter.OnDataBindCallback;

public class BaseViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public final V viewDataBinding;
    public final OnDataBindCallback onDataBindCallback;

    public BaseViewHolder(V viewDataBinding, OnDataBindCallback onDataBindCallback) {
        super(viewDataBinding.getRoot());
        this.viewDataBinding = viewDataBinding;
        this.onDataBindCallback = onDataBindCallback;
        viewDataBinding.getRoot().setOnClickListener(this);
        viewDataBinding.getRoot().setOnLongClickListener(this);
        onDataBindCallback.onDataBind(viewDataBinding,this,this::onLongClick);
    }

    @Override
    public void onClick(View v) {
        onDataBindCallback.onItemClick(v,getAdapterPosition(),viewDataBinding);
    }

    @Override
    public boolean onLongClick(View v) {
        onDataBindCallback.onItemLongClick(v,getAdapterPosition(),viewDataBinding);
        return true;
    }
}
