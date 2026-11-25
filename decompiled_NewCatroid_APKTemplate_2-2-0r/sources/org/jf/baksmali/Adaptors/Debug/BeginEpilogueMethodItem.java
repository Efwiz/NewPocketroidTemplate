package org.jf.baksmali.Adaptors.Debug;

import java.io.IOException;
import org.jf.baksmali.formatter.BaksmaliWriter;

/* JADX INFO: loaded from: classes6.dex */
public class BeginEpilogueMethodItem extends DebugMethodItem {
    public BeginEpilogueMethodItem(int i, int i2) {
        super(i, i2);
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".prologue");
        return true;
    }
}
