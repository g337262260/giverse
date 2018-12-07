package com.guowei.diverse.ui.learn.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.guowei.diverse.R;
import com.guowei.diverse.app.Constants;
import com.guowei.diverse.model.ProjectModel;
import com.guowei.diverse.util.RandomUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;



public class ProjectTagAdapter extends TagAdapter<ProjectModel> {

    private Context mContext;
    private LayoutInflater mInflater;

    public ProjectTagAdapter(Context context, List<ProjectModel> datas) {
        super(datas);
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, ProjectModel data) {
        TextView tv = (TextView) mInflater.inflate(R.layout.tree_tag_item,parent,false);
        tv.setText(data.getName());
        tv.setTextColor(RandomUtils.INSTANCE.randomColor(Constants.COLOR_RGB_MIN,Constants.COLOR_RGB_MAX));
        return tv;
    }


}
