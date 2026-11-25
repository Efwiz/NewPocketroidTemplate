package io.github.rosemoe.sora.util;

import android.content.ClipData;

/* JADX INFO: loaded from: classes11.dex */
public class ClipDataUtils {
    public static String clipDataToString(ClipData clipData) {
        if (clipData == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < clipData.getItemCount(); i++) {
            if (i > 0) {
                sb.append('\n');
            }
            ClipData.Item itemAt = clipData.getItemAt(i);
            CharSequence text = itemAt.getText();
            if (text == null) {
                text = itemAt.toString();
            }
            sb.append(text);
        }
        return sb.toString();
    }
}
