package org.catrobat.catroid.formulaeditor;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes14.dex */
public final /* synthetic */ class FormulaElement$$ExternalSyntheticBackport0 {
    public static /* synthetic */ String m(String str, int i) {
        if (i < 0) {
            throw new IllegalArgumentException("count is negative: " + i);
        }
        int length = str.length();
        if (i == 0 || length == 0) {
            return "";
        }
        if (i == 1) {
            return str;
        }
        if (str.length() > Integer.MAX_VALUE / i) {
            throw new OutOfMemoryError("Repeating " + str.length() + " bytes String " + i + " times will produce a String exceeding maximum size.");
        }
        StringBuilder sb = new StringBuilder(length * i);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
