package org.catrobat.catroid.content;

import android.view.View;
import android.widget.AdapterView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/AdapterViewOnItemSelectedListenerImpl.class
  classes14.dex
 */
/* JADX INFO: compiled from: AdapterViewOnItemSelectedListenerImpl.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J0\u0010\u0007\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010\u000f\u001a\u00020\u00052\f\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\tH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/content/AdapterViewOnItemSelectedListenerImpl;", "Landroid/widget/AdapterView$OnItemSelectedListener;", "callback", "Lkotlin/Function1;", "", "", "(Lkotlin/jvm/functions/Function1;)V", "onItemSelected", "parent", "Landroid/widget/AdapterView;", Promotion.ACTION_VIEW, "Landroid/view/View;", "position", "id", "", "onNothingSelected", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AdapterViewOnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {
    private final Function1<Integer, Unit> callback;

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public AdapterViewOnItemSelectedListenerImpl(Function1<? super Integer, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id2) {
        this.callback.invoke(Integer.valueOf(position));
    }
}
