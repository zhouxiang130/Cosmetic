package com.yj.cosmetics.listener;


import com.yj.cosmetics.model.JudgeGoodsData;
import com.yj.cosmetics.util.luban.OnCompressListener;

/**
 * Created by Suo on 2017/9/21.
 */

public interface JudgeCompressListener extends OnCompressListener {

    void onJudgeSuccess(JudgeGoodsData data);
}
