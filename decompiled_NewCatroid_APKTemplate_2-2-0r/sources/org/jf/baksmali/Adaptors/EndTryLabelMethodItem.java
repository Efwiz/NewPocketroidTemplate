package org.jf.baksmali.Adaptors;

import org.jf.baksmali.BaksmaliOptions;

/* JADX INFO: loaded from: classes6.dex */
public class EndTryLabelMethodItem extends LabelMethodItem {
    private int endTryAddress;

    @Override // org.jf.baksmali.Adaptors.LabelMethodItem, org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return 101.0d;
    }

    public EndTryLabelMethodItem(BaksmaliOptions baksmaliOptions, int i, int i2) {
        super(baksmaliOptions, i, "try_end_");
        this.endTryAddress = i2;
    }

    @Override // org.jf.baksmali.Adaptors.LabelMethodItem
    public int getLabelAddress() {
        return this.endTryAddress;
    }
}
