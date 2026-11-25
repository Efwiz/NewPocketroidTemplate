package org.jf.dexlib2.dexbacked.reference;

import java.util.List;
import org.jf.dexlib2.base.reference.BaseMethodReference;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.util.FixedSizeList;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.util.collection.EmptyList;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedMethodReference extends BaseMethodReference {
    public final DexBackedDexFile dexFile;
    private final int methodIndex;
    private int protoIdItemOffset;

    public int getSize() {
        return 8;
    }

    public DexBackedMethodReference(DexBackedDexFile dexBackedDexFile, int i) {
        this.dexFile = dexBackedDexFile;
        this.methodIndex = i;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public String getDefiningClass() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readUshort(this.dexFile.getMethodSection().getOffset(this.methodIndex)));
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method, org.jf.dexlib2.iface.Member
    public String getName() {
        return this.dexFile.getStringSection().get(this.dexFile.getBuffer().readSmallUint(this.dexFile.getMethodSection().getOffset(this.methodIndex) + 4));
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference
    public List<String> getParameterTypes() {
        int smallUint = this.dexFile.getBuffer().readSmallUint(getProtoIdItemOffset() + 8);
        if (smallUint > 0) {
            final int smallUint2 = this.dexFile.getDataBuffer().readSmallUint(smallUint);
            final int i = smallUint + 4;
            return new FixedSizeList<String>() { // from class: org.jf.dexlib2.dexbacked.reference.DexBackedMethodReference.1
                @Override // org.jf.dexlib2.dexbacked.util.FixedSizeList
                public String readItem(int i2) {
                    return DexBackedMethodReference.this.dexFile.getTypeSection().get(DexBackedMethodReference.this.dexFile.getDataBuffer().readUshort(i + (i2 * 2)));
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return smallUint2;
                }
            };
        }
        return EmptyList.of();
    }

    @Override // org.jf.dexlib2.iface.reference.MethodReference, org.jf.dexlib2.iface.Method
    public String getReturnType() {
        return this.dexFile.getTypeSection().get(this.dexFile.getBuffer().readSmallUint(getProtoIdItemOffset() + 4));
    }

    private int getProtoIdItemOffset() {
        if (this.protoIdItemOffset == 0) {
            this.protoIdItemOffset = this.dexFile.getProtoSection().getOffset(this.dexFile.getBuffer().readUshort(this.dexFile.getMethodSection().getOffset(this.methodIndex) + 2));
        }
        return this.protoIdItemOffset;
    }

    @Override // org.jf.dexlib2.base.reference.BaseReference, org.jf.dexlib2.iface.reference.Reference
    public void validateReference() throws Reference.InvalidReferenceException {
        int i = this.methodIndex;
        if (i < 0 || i >= this.dexFile.getMethodSection().size()) {
            throw new Reference.InvalidReferenceException("method@" + this.methodIndex);
        }
    }
}
