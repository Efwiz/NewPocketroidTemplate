package org.catrobat.catroid.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.core.view.ViewCompat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/BorderTextView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BorderTextView extends TextView {
    private static final int SHADOW_RADIUS = 3;
    private static final int SHADOW_REPEAT = 3;

    public BorderTextView(Context context) {
        super(context);
    }

    public BorderTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BorderTextView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        getPaint().setShadowLayer(3.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        getPaint().setShader(null);
        for (int i = 0; i < 3; i++) {
            super.onDraw(canvas);
        }
    }
}
