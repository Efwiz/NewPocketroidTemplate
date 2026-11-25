package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliReader;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliCode extends Smali {
    @Override // com.reandroid.dex.smali.SmaliParser
    public void parse(SmaliReader smaliReader) throws IOException {
    }

    public SmaliCodeSet getCodeSet() {
        return (SmaliCodeSet) getParentInstance(SmaliCodeSet.class);
    }
}
