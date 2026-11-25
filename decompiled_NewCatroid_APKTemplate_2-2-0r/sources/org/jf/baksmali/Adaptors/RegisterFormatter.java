package org.jf.baksmali.Adaptors;

import java.io.IOException;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.baksmali.formatter.BaksmaliWriter;

/* JADX INFO: loaded from: classes6.dex */
public class RegisterFormatter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public final BaksmaliOptions options;
    public final int parameterRegisterCount;
    public final int registerCount;

    public RegisterFormatter(BaksmaliOptions baksmaliOptions, int i, int i2) {
        this.options = baksmaliOptions;
        this.registerCount = i;
        this.parameterRegisterCount = i2;
    }

    public void writeRegisterRange(BaksmaliWriter baksmaliWriter, int i, int i2) throws IOException {
        if (this.options.parameterRegisters && i >= this.registerCount - this.parameterRegisterCount) {
            baksmaliWriter.write("{p");
            baksmaliWriter.writeSignedIntAsDec(i - (this.registerCount - this.parameterRegisterCount));
            baksmaliWriter.write(" .. p");
            baksmaliWriter.writeSignedIntAsDec(i2 - (this.registerCount - this.parameterRegisterCount));
            baksmaliWriter.write(125);
            return;
        }
        baksmaliWriter.write("{v");
        baksmaliWriter.writeSignedIntAsDec(i);
        baksmaliWriter.write(" .. v");
        baksmaliWriter.writeSignedIntAsDec(i2);
        baksmaliWriter.write(125);
    }

    public void writeTo(BaksmaliWriter baksmaliWriter, int i) throws IOException {
        if (this.options.parameterRegisters && i >= this.registerCount - this.parameterRegisterCount) {
            baksmaliWriter.write(112);
            baksmaliWriter.writeSignedIntAsDec(i - (this.registerCount - this.parameterRegisterCount));
        } else {
            baksmaliWriter.write(118);
            baksmaliWriter.writeSignedIntAsDec(i);
        }
    }
}
