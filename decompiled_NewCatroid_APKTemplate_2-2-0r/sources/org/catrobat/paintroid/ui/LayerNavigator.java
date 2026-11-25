package org.catrobat.paintroid.ui;

import android.content.Context;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: LayerNavigator.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/catrobat/paintroid/ui/LayerNavigator;", "Lorg/catrobat/paintroid/contract/LayerContracts$Navigator;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "showToast", "", "id", "", "length", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LayerNavigator implements LayerContracts.Navigator {
    private final Context context;

    public LayerNavigator(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.catrobat.paintroid.contract.LayerContracts.Navigator
    public void showToast(int id2, int length) {
        ToastFactory.makeText(this.context, id2, length).show();
    }
}
