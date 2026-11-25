package org.jf.dexlib2.dexbacked;

import org.jf.dexlib2.dexbacked.model.DexString;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedSourceFileDef {
    private final DexBackedClassDef classDef;

    public DexBackedSourceFileDef(DexBackedClassDef dexBackedClassDef) {
        this.classDef = dexBackedClassDef;
    }

    public String getType() {
        DexString dexString = getDexString();
        if (dexString != null) {
            return dexString.getValue();
        }
        return null;
    }

    public DexString getDexString() {
        return this.classDef.dexFile.getDexStringSection().get(getIndex());
    }

    private int getIndex() {
        return this.classDef.dexFile.getBuffer().readOptionalUint(this.classDef.classDefOffset + 16);
    }
}
