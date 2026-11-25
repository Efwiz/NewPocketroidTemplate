package org.catrobat.paintroid.tools.helper;

import android.text.Spanned;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.ui.tools.NumberRangeFilter;

/* JADX INFO: compiled from: DefaultNumberRangeFilter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\r\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J:\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0016R\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/DefaultNumberRangeFilter;", "Lorg/catrobat/paintroid/ui/tools/NumberRangeFilter;", "min", "", "max", "(II)V", "getMax", "()I", "setMax", "(I)V", "filter", "", FirebaseAnalytics.Param.SOURCE, "start", "end", "dest", "Landroid/text/Spanned;", "dstart", "dend", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultNumberRangeFilter implements NumberRangeFilter {
    private static final String TAG = "DefaultNumberRangeFilter";
    private int max;
    private final int min;

    public DefaultNumberRangeFilter(int i, int i2) {
        this.min = i;
        this.max = i2;
    }

    @Override // org.catrobat.paintroid.ui.tools.NumberRangeFilter
    public int getMax() {
        return this.max;
    }

    @Override // org.catrobat.paintroid.ui.tools.NumberRangeFilter
    public void setMax(int i) {
        this.max = i;
    }

    @Override // org.catrobat.paintroid.ui.tools.NumberRangeFilter, android.text.InputFilter
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(dest, "dest");
        try {
            int i = Integer.parseInt(new StringBuilder().append((Object) dest).append((Object) source).toString());
            int i2 = this.min;
            if (i <= getMax() && i2 <= i) {
                return null;
            }
        } catch (NumberFormatException e) {
            String localizedMessage = e.getLocalizedMessage();
            if (localizedMessage != null) {
                Log.d(TAG, localizedMessage);
            }
        }
        return "";
    }
}
