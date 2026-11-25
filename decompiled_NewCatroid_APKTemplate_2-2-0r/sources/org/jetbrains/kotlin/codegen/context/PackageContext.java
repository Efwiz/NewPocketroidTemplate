package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class PackageContext extends FieldOwnerContext<PackageFragmentDescriptor> implements DelegatingToPartContext, FacadePartWithSourceFile {
    private final Type packagePartType;
    private final KtFile sourceFile;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i != 1) {
            objArr[0] = "contextDescriptor";
        } else {
            objArr[0] = "parent";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/context/PackageContext";
        objArr[2] = "<init>";
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageContext(PackageFragmentDescriptor packageFragmentDescriptor, CodegenContext codegenContext, Type type, KtFile ktFile) {
        super(packageFragmentDescriptor, OwnerKind.PACKAGE, codegenContext, null, null, null);
        if (packageFragmentDescriptor == null) {
            $$$reportNull$$$0(0);
        }
        if (codegenContext == null) {
            $$$reportNull$$$0(1);
        }
        this.packagePartType = type;
        this.sourceFile = ktFile;
    }

    public String toString() {
        return "Package: " + ((PackageFragmentDescriptor) getContextDescriptor()).getShortName();
    }

    @Override // org.jetbrains.kotlin.codegen.context.DelegatingToPartContext
    public Type getImplementationOwnerClassType() {
        return this.packagePartType;
    }

    @Override // org.jetbrains.kotlin.codegen.context.FacadePartWithSourceFile
    public KtFile getSourceFile() {
        return this.sourceFile;
    }
}
