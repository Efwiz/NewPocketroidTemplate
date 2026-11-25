package org.jf.dexlib2.dexbacked.reference;

import org.jf.dexlib2.base.reference.BaseTypeReference;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedTypeReference extends BaseTypeReference {
    public final DexBackedDexFile dexFile;
    public final int typeIndex;

    public int getSize() {
        return 4;
    }

    public DexBackedTypeReference(DexBackedDexFile dexBackedDexFile, int i) {
        this.dexFile = dexBackedDexFile;
        this.typeIndex = i;
    }

    @Override // org.jf.dexlib2.iface.reference.TypeReference, org.jf.dexlib2.iface.ClassDef
    public String getType() {
        return this.dexFile.getTypeSection().get(this.typeIndex);
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    public void validateReference() throws Reference.InvalidReferenceException {
        int i = this.typeIndex;
        if (i < 0 || i >= this.dexFile.getTypeSection().size()) {
            throw new Reference.InvalidReferenceException("type@" + this.typeIndex);
        }
    }
}
