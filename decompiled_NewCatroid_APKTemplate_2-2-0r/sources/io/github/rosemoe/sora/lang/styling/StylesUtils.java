package io.github.rosemoe.sora.lang.styling;

import android.util.Log;
import io.github.rosemoe.sora.text.CharPosition;

/* JADX INFO: loaded from: classes11.dex */
public class StylesUtils {
    private static final String LOG_TAG = "StylesUtils";

    public static boolean checkNoCompletion(Styles styles, CharPosition charPosition) {
        Span spanForPosition = getSpanForPosition(styles, charPosition);
        return spanForPosition == null || TextStyle.isNoCompletion(spanForPosition.getStyle());
    }

    public static Span getSpanForPosition(Styles styles, CharPosition charPosition) {
        return getSpanForPositionImpl(styles, charPosition, 0);
    }

    public static Span getFollowingSpanForPosition(Styles styles, CharPosition charPosition) {
        return getSpanForPositionImpl(styles, charPosition, 1);
    }

    private static Span getSpanForPositionImpl(Styles styles, CharPosition charPosition, int i) {
        Spans spans;
        int i2 = charPosition.line;
        int i3 = charPosition.column;
        if (styles != null && (spans = styles.spans) != null) {
            Spans.Reader reader = spans.read();
            try {
                reader.moveToLine(i2);
                int spanCount = reader.getSpanCount() - 1;
                if (spanCount == -1) {
                    try {
                        reader.moveToLine(-1);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "failed to close " + reader, e);
                    }
                    return null;
                }
                for (int i4 = 0; i4 < reader.getSpanCount(); i4++) {
                    if (reader.getSpanAt(i4).getColumn() > i3) {
                        spanCount = i4 - 1;
                        break;
                    }
                }
                int i5 = spanCount + i;
                if (i5 >= 0 && i5 < reader.getSpanCount()) {
                    Span spanAt = reader.getSpanAt(i5);
                    try {
                        reader.moveToLine(-1);
                    } catch (Exception e2) {
                        Log.e(LOG_TAG, "failed to close " + reader, e2);
                    }
                    return spanAt;
                }
                try {
                    reader.moveToLine(-1);
                } catch (Exception e3) {
                    Log.e(LOG_TAG, "failed to close " + reader, e3);
                }
                return null;
            } catch (Exception e4) {
                try {
                    reader.moveToLine(-1);
                } catch (Exception e5) {
                    e4.addSuppressed(e5);
                }
                Log.e(LOG_TAG, "failed to get spans from " + reader + " at " + charPosition, e4);
            } catch (Throwable th) {
                try {
                    reader.moveToLine(-1);
                } catch (Exception e6) {
                    Log.e(LOG_TAG, "failed to close " + reader, e6);
                }
                throw th;
            }
        }
        return null;
    }
}
