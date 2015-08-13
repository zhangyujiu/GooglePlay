package com.sctbc.googleplay.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.sctbc.googleplay.R;
import com.sctbc.googleplay.domain.CategoryInfo;
import com.sctbc.googleplay.tools.UiUtils;

public class CategoryTitleHolder extends BaseHolder<CategoryInfo> {

	private TextView tv;

	@Override
	public View initView() {
		tv = new TextView(UiUtils.getContext());
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundDrawable(UiUtils.getDrawable(R.drawable.grid_item_bg));
		return tv;
	}

	@Override
	public void refreshView(CategoryInfo data) {
		tv.setText(data.getTitle());
	}

}
