package com.guowei.diverse.ui.learn.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.guowei.diverse.R;
import com.guowei.diverse.app.Constants;
import com.guowei.diverse.model.learn.TreeModel;
import com.guowei.diverse.util.RandomUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;


/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class TreeTagAdapter extends TagAdapter<TreeModel.ChildrenBean> {

    private Context mContext;
    private LayoutInflater mInflater;

    public TreeTagAdapter(Context context, List<TreeModel.ChildrenBean> datas) {
        super(datas);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, TreeModel.ChildrenBean data) {
        TextView tv = (TextView) mInflater.inflate(R.layout.tree_tag_item,parent,false);
        tv.setText(data.getName());
        tv.setTextColor(RandomUtils.INSTANCE.randomColor(Constants.COLOR_RGB_MIN,Constants.COLOR_RGB_MAX));
        return tv;
    }


}
