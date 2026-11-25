package org.catrobat.paintroid.listener;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.colorpicker.OnColorPickedListener;
import org.catrobat.paintroid.contract.MainActivityContracts;

/* JADX INFO: compiled from: PresenterColorPickedListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/listener/PresenterColorPickedListener;", "Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;", "presenter", "Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;", "(Lorg/catrobat/paintroid/contract/MainActivityContracts$Presenter;)V", "colorChanged", "", TypedValues.Custom.S_COLOR, "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class PresenterColorPickedListener implements OnColorPickedListener {
    private final MainActivityContracts.Presenter presenter;

    public PresenterColorPickedListener(MainActivityContracts.Presenter presenter) {
        Intrinsics.checkNotNullParameter(presenter, "presenter");
        this.presenter = presenter;
    }

    @Override // org.catrobat.paintroid.colorpicker.OnColorPickedListener
    public void colorChanged(int color) {
        this.presenter.setBottomNavigationColor(color);
    }
}
