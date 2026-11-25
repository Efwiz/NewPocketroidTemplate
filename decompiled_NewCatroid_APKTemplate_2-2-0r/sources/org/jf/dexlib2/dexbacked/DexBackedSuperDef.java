package org.jf.dexlib2.dexbacked;

import org.jf.dexlib2.dexbacked.model.DexTypeString;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedSuperDef {
    private final DexBackedClassDef classDef;

    public DexBackedSuperDef(DexBackedClassDef dexBackedClassDef) {
        this.classDef = dexBackedClassDef;
    }

    public String getType() {
        DexTypeString dexTypeString = getDexTypeString();
        if (dexTypeString != null) {
            return dexTypeString.getValue();
        }
        return null;
    }

    public DexTypeString getDexTypeString() {
        return this.classDef.dexFile.getTypeStringSection().get(getIndex());
    }

    private int getIndex() {
        return this.classDef.dexFile.getBuffer().readOptionalUint(this.classDef.classDefOffset + 8);
    }
}
