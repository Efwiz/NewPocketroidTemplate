package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class MultifileClassContextBase extends FieldOwnerContext<PackageFragmentDescriptor> {
    private final Type filePartType;
    private final Type multifileClassType;

    public MultifileClassContextBase(PackageFragmentDescriptor packageFragmentDescriptor, CodegenContext codegenContext, Type type, Type type2) {
        super(packageFragmentDescriptor, OwnerKind.PACKAGE, codegenContext, null, null, null);
        this.multifileClassType = type;
        this.filePartType = type2;
    }

    public Type getMultifileClassType() {
        return this.multifileClassType;
    }

    public Type getFilePartType() {
        return this.filePartType;
    }
}
