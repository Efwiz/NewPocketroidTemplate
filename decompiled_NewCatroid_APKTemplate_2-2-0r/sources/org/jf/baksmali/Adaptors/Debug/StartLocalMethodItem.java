package org.jf.baksmali.Adaptors.Debug;

import java.io.IOException;
import org.jf.baksmali.Adaptors.ClassDefinition;
import org.jf.baksmali.Adaptors.RegisterFormatter;
import org.jf.baksmali.formatter.BaksmaliWriter;
import org.jf.dexlib2.iface.debug.StartLocal;

/* JADX INFO: loaded from: classes6.dex */
public class StartLocalMethodItem extends DebugMethodItem {
    private final ClassDefinition classDef;
    private final RegisterFormatter registerFormatter;
    private final StartLocal startLocal;

    public StartLocalMethodItem(ClassDefinition classDefinition, int i, int i2, RegisterFormatter registerFormatter, StartLocal startLocal) {
        super(i, i2);
        this.classDef = classDefinition;
        this.startLocal = startLocal;
        this.registerFormatter = registerFormatter;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(".local ");
        this.registerFormatter.writeTo(baksmaliWriter, this.startLocal.getRegister());
        String name2 = this.startLocal.getName();
        String type = this.startLocal.getType();
        String signature = this.startLocal.getSignature();
        if (name2 == null && type == null && signature == null) {
            return true;
        }
        baksmaliWriter.write(", ");
        LocalFormatter.writeLocal(baksmaliWriter, name2, type, signature);
        return true;
    }
}
