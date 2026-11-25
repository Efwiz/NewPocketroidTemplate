package com.huawei.hms.mlplugin.asr.view;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;

/* JADX INFO: loaded from: classes10.dex */
public class EllipsizeTextView extends TextView {
    public EllipsizeTextView(Context context) {
        super(context);
    }

    public void setEllipsizeText(String str) {
        int maxLines = getMaxLines();
        int width = getWidth() * maxLines;
        if (TextUtils.isEmpty(str) || maxLines <= 1 || width <= 0) {
            setText(str);
            return;
        }
        Paint paint = new Paint(getPaint());
        paint.setSubpixelText(true);
        String strReplace = str.replace(LanguageCodeUtil.FI, "_!");
        int length = strReplace.length();
        float f = width;
        if (paint.breakText(strReplace, 0, length, false, f, null) >= length) {
            setText(str);
        } else {
            String strConcat = strReplace.concat("...");
            setText("...".concat(str.substring(length - (paint.breakText(strConcat, 0, strConcat.length(), false, f, null) - 3))));
        }
    }

    public EllipsizeTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EllipsizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
