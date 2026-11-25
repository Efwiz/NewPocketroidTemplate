package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class MultifileClassFacadeContext extends MultifileClassContextBase {
    public MultifileClassFacadeContext(PackageFragmentDescriptor packageFragmentDescriptor, CodegenContext codegenContext, Type type, Type type2) {
        super(packageFragmentDescriptor, codegenContext, type, type2);
    }
}
