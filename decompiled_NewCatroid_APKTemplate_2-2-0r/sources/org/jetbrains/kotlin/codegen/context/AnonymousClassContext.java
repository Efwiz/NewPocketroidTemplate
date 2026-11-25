package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;

/* JADX INFO: loaded from: classes3.dex */
public class AnonymousClassContext extends ClassContext {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "contextDescriptor";
        } else if (i != 2) {
            objArr[0] = "typeMapper";
        } else {
            objArr[0] = "contextKind";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/context/AnonymousClassContext";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnonymousClassContext(KotlinTypeMapper kotlinTypeMapper, ClassDescriptor classDescriptor, OwnerKind ownerKind, CodegenContext codegenContext, LocalLookup localLookup) {
        super(kotlinTypeMapper, classDescriptor, ownerKind, codegenContext, localLookup);
        if (kotlinTypeMapper == null) {
            $$$reportNull$$$0(0);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(1);
        }
        if (ownerKind == null) {
            $$$reportNull$$$0(2);
        }
    }

    @Override // org.jetbrains.kotlin.codegen.context.ClassContext
    public String toString() {
        return "Anonymous: " + getThisDescriptor();
    }
}
