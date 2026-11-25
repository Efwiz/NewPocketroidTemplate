package org.jf.baksmali.Adaptors.Debug;

import java.io.IOException;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.iface.debug.SetSourceFile;

/* JADX INFO: loaded from: classes6.dex */
public class SetSourceFileMethodItem extends DebugMethodItem {
    private final String sourceFile;

    public SetSourceFileMethodItem(int i, int i2, SetSourceFile setSourceFile) {
        super(i, i2);
        this.sourceFile = setSourceFile.getSourceFile();
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".source");
        if (this.sourceFile == null) {
            return true;
        }
        baksmaliWriter.write(" ");
        baksmaliWriter.writeQuotedString(this.sourceFile);
        return true;
    }
}
