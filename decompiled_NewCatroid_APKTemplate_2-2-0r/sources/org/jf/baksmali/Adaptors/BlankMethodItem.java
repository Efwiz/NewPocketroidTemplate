package org.jf.baksmali.Adaptors;

import org.jf.baksmali.formatter.BaksmaliWriter;

/* JADX INFO: loaded from: classes6.dex */
public class BlankMethodItem extends MethodItem {
    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return 2.147483647E9d;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) {
        return true;
    }

    public BlankMethodItem(int i) {
        super(i);
    }
}
