package com.reandroid.dex.ins;

import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public interface Label extends ExtraLine {
    int getAddress();

    String getLabelName();

    @Override // com.reandroid.dex.ins.ExtraLine
    default void appendExtra(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendLabelName(getLabelName());
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    default boolean isEqualExtraLine(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Label) {
            return getLabelName().equals(((Label) obj).getLabelName());
        }
        return false;
    }

    @Override // com.reandroid.dex.ins.ExtraLine
    default int compareExtraLine(ExtraLine extraLine) {
        int iCompareExtraLine = super.compareExtraLine(extraLine);
        if (iCompareExtraLine != 0) {
            return iCompareExtraLine;
        }
        if (extraLine instanceof Label) {
            return getLabelName().compareTo(((Label) extraLine).getLabelName());
        }
        return 0;
    }
}
