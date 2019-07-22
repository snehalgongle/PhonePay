package com.snake.phonepay.adapter;

import android.view.View;

public interface OnDataBindCallback<V> {

    void onItemClick(View view, int position, V v);
    void onItemLongClick(View view, int position, V v);
    void onDataBind(V v, View.OnClickListener onClickListener, View.OnLongClickListener onLongClickListener);
}
