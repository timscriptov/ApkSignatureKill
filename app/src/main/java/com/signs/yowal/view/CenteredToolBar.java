package com.signs.yowal.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.mcal.apkkiller.R;

public class CenteredToolBar extends Toolbar {

    private AppCompatTextView centeredTitleTextView;

    public CenteredToolBar(Context context) {
        super(context);
    }

    public CenteredToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CenteredToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTitleTextColor(int color) {
        getCenteredTitleTextView().setTextColor(color);
    }

    @Override
    public CharSequence getTitle() {
        return getCenteredTitleTextView().getText().toString();
    }

    @Override
    public void setTitle(@StringRes int resId) {
        String s = getResources().getString(resId);
        setTitle(s);
    }

    @Override
    public void setTitle(CharSequence title) {
        getCenteredTitleTextView().setText(title);
    }

    public void setTypeface(Typeface font) {
        getCenteredTitleTextView().setTypeface(font);
    }

    private AppCompatTextView getCenteredTitleTextView() {
        if (centeredTitleTextView == null) {
            centeredTitleTextView = new AppCompatTextView(getContext());
            //  centeredTitleTextView.setTypeface(...);
            centeredTitleTextView.setSingleLine();
            centeredTitleTextView.setEllipsize(TextUtils.TruncateAt.END);
            centeredTitleTextView.setGravity(Gravity.CENTER);
            centeredTitleTextView.setTextAppearance(getContext(), R.style.TextAppearance_AppCompat_Widget_ActionBar_Title);

            Toolbar.LayoutParams lp = new Toolbar.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER;
            centeredTitleTextView.setLayoutParams(lp);

            addView(centeredTitleTextView);
        }
        return centeredTitleTextView;
    }
}