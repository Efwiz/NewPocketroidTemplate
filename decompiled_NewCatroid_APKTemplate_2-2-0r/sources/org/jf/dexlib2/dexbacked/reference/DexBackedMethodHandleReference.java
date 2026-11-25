package org.jf.dexlib2.dexbacked.reference;

import org.jf.dexlib2.base.reference.BaseMethodHandleReference;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedMethodHandleReference extends BaseMethodHandleReference {
    public final DexBackedDexFile dexFile;
    public final int methodHandleIndex;
    public final int methodHandleOffset;

    public DexBackedMethodHandleReference(DexBackedDexFile dexBackedDexFile, int i) {
        this.dexFile = dexBackedDexFile;
        this.methodHandleIndex = i;
        this.methodHandleOffset = dexBackedDexFile.getMethodHandleSection().getOffset(i);
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    public int getMethodHandleType() {
        return this.dexFile.getBuffer().readUshort(this.methodHandleOffset);
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    public Reference getMemberReference() {
        int ushort = this.dexFile.getBuffer().readUshort(this.methodHandleOffset + 4);
        switch (getMethodHandleType()) {
            case 0:
            case 1:
            case 2:
            case 3:
                return new DexBackedFieldReference(this.dexFile, ushort);
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return new DexBackedMethodReference(this.dexFile, ushort);
            default:
                throw new ExceptionWithContext("Invalid method handle type: %d", Integer.valueOf(getMethodHandleType()));
        }
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    public void validateReference() throws Reference.InvalidReferenceException {
        int i = this.methodHandleIndex;
        if (i < 0 || i >= this.dexFile.getMethodHandleSection().size()) {
            throw new Reference.InvalidReferenceException("methodhandle@" + this.methodHandleIndex);
        }
        try {
            getMemberReference();
        } catch (ExceptionWithContext e) {
            throw new Reference.InvalidReferenceException("methodhandle@" + this.methodHandleIndex, e);
        }
    }
}
