package org.jf.baksmali.Adaptors.Debug;

import java.io.IOException;
import org.jf.baksmali.Adaptors.RegisterFormatter;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.iface.debug.EndLocal;

/* JADX INFO: loaded from: classes6.dex */
public class EndLocalMethodItem extends DebugMethodItem {
    private final EndLocal endLocal;
    private final RegisterFormatter registerFormatter;

    public EndLocalMethodItem(int i, int i2, RegisterFormatter registerFormatter, EndLocal endLocal) {
        super(i, i2);
        this.endLocal = endLocal;
        this.registerFormatter = registerFormatter;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".end local ");
        this.registerFormatter.writeTo(baksmaliWriter, this.endLocal.getRegister());
        String name2 = this.endLocal.getName();
        String type = this.endLocal.getType();
        String signature = this.endLocal.getSignature();
        if (name2 == null && type == null && signature == null) {
            return true;
        }
        baksmaliWriter.write("    # ");
        LocalFormatter.writeLocal(baksmaliWriter, name2, type, signature);
        return true;
    }
}
