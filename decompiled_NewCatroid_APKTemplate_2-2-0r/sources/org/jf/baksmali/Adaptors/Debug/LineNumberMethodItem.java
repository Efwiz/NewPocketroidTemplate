package org.jf.baksmali.Adaptors.Debug;

import java.io.IOException;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.iface.debug.LineNumber;

/* JADX INFO: loaded from: classes6.dex */
public class LineNumberMethodItem extends DebugMethodItem {
    private int lineNumber;

    public LineNumberMethodItem(int i, int i2, LineNumber lineNumber) {
        super(i, i2);
        this.lineNumber = lineNumber.getLineNumber();
    }

    public LineNumberMethodItem(int i) {
        super(0, 0);
        this.lineNumber = i;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int i) {
        this.lineNumber = i;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".line ");
        baksmaliWriter.writeUnsignedIntAsDec(this.lineNumber);
        return true;
    }
}
