package org.eclipse.jdt.internal.compiler.parser;

import com.huawei.hms.ml.language.common.utils.Constant;

/* JADX INFO: loaded from: classes14.dex */
public class NLSTag {
    public int end;
    public int index;
    public int lineNumber;
    public int start;

    public NLSTag(int i, int i2, int i3, int i4) {
        this.start = i;
        this.end = i2;
        this.lineNumber = i3;
        this.index = i4;
    }

    public String toString() {
        return "NLSTag(" + this.start + "," + this.end + "," + this.lineNumber + Constant.AFTER_QUTO;
    }
}
