package androidx.constraintlayout.core.parser;

import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: loaded from: classes.dex */
public class CLString extends CLElement {
    public CLString(char[] cArr) {
        super(cArr);
    }

    public static CLElement allocate(char[] cArr) {
        return new CLString(cArr);
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toJSON() {
        return FormatHelper.QUOTE + content() + FormatHelper.QUOTE;
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    protected String toFormattedJSON(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        addIndent(sb, i);
        sb.append(FormatHelper.QUOTE);
        sb.append(content());
        sb.append(FormatHelper.QUOTE);
        return sb.toString();
    }
}
