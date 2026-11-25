package org.jf.dexlib2.dexbacked;

import org.jf.dexlib2.dexbacked.model.DexTypeString;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedInterfaceDef {
    private final DexBackedClassDef classDef;
    private final int index;

    public DexBackedInterfaceDef(DexBackedClassDef dexBackedClassDef, int i) {
        this.classDef = dexBackedClassDef;
        this.index = i;
    }

    public String getType() {
        return getDexTypeString().getValue();
    }

    public DexTypeString getDexTypeString() {
        DexBuffer buffer = this.classDef.dexFile.getBuffer();
        return this.classDef.dexFile.getTypeStringSection().get(buffer.readUshort(buffer.readSmallUint(this.classDef.classDefOffset + 12) + 4 + (this.index * 2)));
    }
}
