package com.reandroid.dex.resource;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.model.DexField;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class RStyleableIndex extends RStyleableItem implements IntegerReference {
    public RStyleableIndex(DexField dexField) {
        super(dexField);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        IntegerReference staticIntegerValue = getDexField().getStaticIntegerValue();
        if (staticIntegerValue != null) {
            return staticIntegerValue.get();
        }
        return 0;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        IntegerReference staticIntegerValue = getDexField().getStaticIntegerValue();
        if (staticIntegerValue != null) {
            staticIntegerValue.set(i);
        }
    }

    @Override // com.reandroid.dex.resource.RStyleableItem
    public void appendJavaValue(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendHex(get());
    }

    @Override // com.reandroid.dex.resource.RStyleableItem
    public boolean isValid() {
        return super.isValid() && (get() & (-65536)) == 0;
    }
}
