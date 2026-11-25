package org.catrobat.paintroid.colorpicker;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: ColorHistory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0005R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u00068F¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006\r"}, d2 = {"Lorg/catrobat/paintroid/colorpicker/ColorHistory;", "Ljava/io/Serializable;", "()V", "colorHistory", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "colors", "getColors", "()Ljava/util/ArrayList;", "addColor", "", TypedValues.Custom.S_COLOR, "colorpicker_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ColorHistory implements Serializable {
    private final ArrayList<Integer> colorHistory = new ArrayList<>();

    public final ArrayList<Integer> getColors() {
        return this.colorHistory;
    }

    public final void addColor(int color) {
        Integer num = (Integer) CollectionsKt.lastOrNull((List) this.colorHistory);
        if (num == null || num.intValue() != color) {
            this.colorHistory.add(Integer.valueOf(color));
        }
        if (this.colorHistory.size() > 4) {
            CollectionsKt.removeFirst(this.colorHistory);
        }
    }
}
