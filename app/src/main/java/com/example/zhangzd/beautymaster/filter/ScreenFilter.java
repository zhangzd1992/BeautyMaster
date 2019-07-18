package com.example.zhangzd.beautymaster.filter;

import android.content.Context;

import com.example.zhangzd.beautymaster.R;


/**
 * @Description:
 * @Author: zhangzd
 * @CreateDate: 2019-07-12 13:58
 */
public class ScreenFilter extends AbstrictFilter {

    public ScreenFilter(Context context) {
        super(context, R.raw.base_vertex,R.raw.base_frag);
    }

    @Override
    protected void initCoordinate() {

    }
}
