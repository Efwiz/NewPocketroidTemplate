package org.jf.baksmali.Adaptors.Debug;

import java.io.IOException;
import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.Adaptors.RegisterFormatter;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.iface.debug.RestartLocal;

/* JADX INFO: loaded from: classes6.dex */
public class RestartLocalMethodItem extends DebugMethodItem {
    private final ClassDefinition classDef;
    private final RegisterFormatter registerFormatter;
    private final RestartLocal restartLocal;

    public RestartLocalMethodItem(ClassDefinition classDefinition, int i, int i2, RegisterFormatter registerFormatter, RestartLocal restartLocal) {
        super(i, i2);
        this.classDef = classDefinition;
        this.restartLocal = restartLocal;
        this.registerFormatter = registerFormatter;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".restart local ");
        this.registerFormatter.writeTo(baksmaliWriter, this.restartLocal.getRegister());
        String name2 = this.restartLocal.getName();
        String type = this.restartLocal.getType();
        String signature = this.restartLocal.getSignature();
        if (name2 == null && type == null && signature == null) {
            return true;
        }
        baksmaliWriter.write("    # ");
        LocalFormatter.writeLocal(baksmaliWriter, name2, type, signature);
        return true;
    }
}
