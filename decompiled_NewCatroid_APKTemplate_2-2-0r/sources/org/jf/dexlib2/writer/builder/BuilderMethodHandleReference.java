package org.jf.dexlib2.writer.builder;

import org.jf.dexlib2.base.reference.BaseMethodHandleReference;

/* JADX INFO: loaded from: classes6.dex */
public class BuilderMethodHandleReference extends BaseMethodHandleReference implements BuilderReference {
    int index = -1;
    final BuilderReference memberReference;
    final int methodHandleType;

    public BuilderMethodHandleReference(int i, BuilderReference builderReference) {
        this.methodHandleType = i;
        this.memberReference = builderReference;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    public int getMethodHandleType() {
        return this.methodHandleType;
    }

    @Override // org.jf.dexlib2.iface.reference.MethodHandleReference
    public BuilderReference getMemberReference() {
        return this.memberReference;
    }

    @Override // org.jf.dexlib2.writer.builder.BuilderReference
    public int getIndex() {
        return this.index;
    }

    @Override // org.jf.dexlib2.writer.builder.BuilderReference
    public void setIndex(int i) {
        this.index = i;
    }
}
