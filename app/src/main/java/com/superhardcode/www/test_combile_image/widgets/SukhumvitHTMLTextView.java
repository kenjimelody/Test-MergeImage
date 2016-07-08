package com.superhardcode.www.test_combile_image.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by Thanisak Piyasaksiri on 7/8/16 AD.
 */
public class SukhumvitHTMLTextView extends HtmlTextView {

    @SuppressWarnings("static-access")
    public SukhumvitHTMLTextView(Context context) {

        super(context);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SukhumvitSet.ttc");
        this.setTypeface(face);
    }

    public SukhumvitHTMLTextView(Context context, AttributeSet attrs) {

        super(context, attrs);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SukhumvitSet.ttc");
        this.setTypeface(face);
    }

    public SukhumvitHTMLTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/SukhumvitSet.ttc");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {

        super.onDraw(canvas);
    }
}