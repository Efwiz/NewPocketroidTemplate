package org.catrobat.paintroid.ui.tools;

import android.text.InputFilter;
import android.text.Spanned;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;

/* JADX INFO: compiled from: NumberRangeFilter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\r\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J:\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0003H&R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/NumberRangeFilter;", "Landroid/text/InputFilter;", "max", "", "getMax", "()I", "setMax", "(I)V", "filter", "", FirebaseAnalytics.Param.SOURCE, "start", "end", "dest", "Landroid/text/Spanned;", "dstart", "dend", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface NumberRangeFilter extends InputFilter {
    @Override // android.text.InputFilter
    CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend);

    int getMax();

    void setMax(int i);
}
