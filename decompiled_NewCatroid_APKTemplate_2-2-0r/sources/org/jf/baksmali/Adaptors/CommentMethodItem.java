package org.jf.baksmali.Adaptors;

import java.io.IOException;
import org.jf.baksmali.formatter.BaksmaliWriter;

/* JADX INFO: loaded from: classes6.dex */
public class CommentMethodItem extends MethodItem {
    private final String comment;
    private final double sortOrder;

    public CommentMethodItem(String str, int i, double d) {
        super(i);
        this.comment = str;
        this.sortOrder = d;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public double getSortOrder() {
        return this.sortOrder;
    }

    @Override // org.jf.baksmali.Adaptors.MethodItem
    public boolean writeTo(BaksmaliWriter baksmaliWriter) throws IOException {
        baksmaliWriter.write(35);
        baksmaliWriter.write(this.comment);
        return true;
    }
}
