package org.jf.baksmali.Adaptors;

import java.io.IOException;
import org.jf.baksmali.BaksmaliOptions;
import org.jf.baksmali.formatter.BaksmaliWriter;

/* JADX INFO: loaded from: classes6.dex */
public class LabelMethodItem extends MethodItem {
    private final String labelPrefix;
    private int labelSequence;
    private final BaksmaliOptions options;

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return 0.0d;
    }

    public LabelMethodItem(BaksmaliOptions baksmaliOptions, int i, String str) {
        super(i);
        this.options = baksmaliOptions;
        this.labelPrefix = str;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem, java.lang.Comparable
    public int compareTo(MethodItem methodItem) {
        int iCompareTo = super.compareTo(methodItem);
        return (iCompareTo == 0 && (methodItem instanceof LabelMethodItem)) ? this.labelPrefix.compareTo(((LabelMethodItem) methodItem).labelPrefix) : iCompareTo;
    }

    public int hashCode() {
        return getCodeAddress();
    }

    public boolean equals(Object obj) {
        return (obj instanceof LabelMethodItem) && compareTo((MethodItem) obj) == 0;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(58);
        baksmaliWriter.write(this.labelPrefix);
        if (this.options.sequentialLabels) {
            baksmaliWriter.writeUnsignedLongAsHex(this.labelSequence);
            return true;
        }
        baksmaliWriter.writeUnsignedLongAsHex(getLabelAddress());
        return true;
    }

    public String getLabelPrefix() {
        return this.labelPrefix;
    }

    public int getLabelAddress() {
        return getCodeAddress();
    }

    public int getLabelSequence() {
        return this.labelSequence;
    }

    public void setLabelSequence(int i) {
        this.labelSequence = i;
    }
}
