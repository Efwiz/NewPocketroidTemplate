package org.jf.baksmali.formatter;

import java.io.Writer;
import org.jf.dexlib2.formatter.DexFormatter;

/* JADX INFO: loaded from: classes6.dex */
public class BaksmaliFormatter extends DexFormatter {
    private final String classContext;

    public BaksmaliFormatter() {
        this(null);
    }

    public BaksmaliFormatter(String str) {
        this.classContext = str;
    }

    @Override // org.jf.dexlib2.formatter.DexFormatter
    public BaksmaliWriter getWriter(Writer writer) {
        return new BaksmaliWriter(writer, this.classContext);
    }
}
