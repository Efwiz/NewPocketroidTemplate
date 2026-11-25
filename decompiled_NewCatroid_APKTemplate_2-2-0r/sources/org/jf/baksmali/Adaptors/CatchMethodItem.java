package org.jf.baksmali.Adaptors;

import java.io.IOException;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.baksmali.formatter.BaksmaliWriter;

/* JADX INFO: loaded from: classes6.dex */
public class CatchMethodItem extends MethodItem {
    private final String exceptionType;
    private final LabelMethodItem handlerLabel;
    private final LabelMethodItem tryEndLabel;
    private final LabelMethodItem tryStartLabel;

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return 102.0d;
    }

    public CatchMethodItem(BaksmaliOptions baksmaliOptions, MethodDefinition.LabelCache labelCache, int i, String str, int i2, int i3, int i4) {
        super(i);
        this.exceptionType = str;
        this.tryStartLabel = labelCache.internLabel(new LabelMethodItem(baksmaliOptions, i2, "try_start_"));
        this.tryEndLabel = labelCache.internLabel(new EndTryLabelMethodItem(baksmaliOptions, i, i3));
        if (str == null) {
            this.handlerLabel = labelCache.internLabel(new LabelMethodItem(baksmaliOptions, i4, "catchall_"));
        } else {
            this.handlerLabel = labelCache.internLabel(new LabelMethodItem(baksmaliOptions, i4, "catch_"));
        }
    }

    public LabelMethodItem getTryStartLabel() {
        return this.tryStartLabel;
    }

    public LabelMethodItem getTryEndLabel() {
        return this.tryEndLabel;
    }

    public LabelMethodItem getHandlerLabel() {
        return this.handlerLabel;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        if (this.exceptionType == null) {
            baksmaliWriter.write(".catchall");
        } else {
            baksmaliWriter.write(".catch ");
            baksmaliWriter.write(this.exceptionType);
        }
        baksmaliWriter.write(" {");
        this.tryStartLabel.writeTo(baksmaliWriter);
        baksmaliWriter.write(" .. ");
        this.tryEndLabel.writeTo(baksmaliWriter);
        baksmaliWriter.write("} ");
        this.handlerLabel.writeTo(baksmaliWriter);
        return true;
    }
}
