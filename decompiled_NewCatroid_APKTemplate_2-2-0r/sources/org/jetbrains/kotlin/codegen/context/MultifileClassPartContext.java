package org.jetbrains.kotlin.codegen.context;

import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class MultifileClassPartContext extends MultifileClassContextBase implements DelegatingToPartContext, FacadePartWithSourceFile {
    private final KtFile sourceFile;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "sourceFile";
        } else {
            objArr[0] = "org/jetbrains/kotlin/codegen/context/MultifileClassPartContext";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/codegen/context/MultifileClassPartContext";
        } else {
            objArr[1] = "getSourceFile";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultifileClassPartContext(PackageFragmentDescriptor packageFragmentDescriptor, CodegenContext codegenContext, Type type, Type type2, KtFile ktFile) {
        super(packageFragmentDescriptor, codegenContext, type, type2);
        if (ktFile == null) {
            $$$reportNull$$$0(0);
        }
        this.sourceFile = ktFile;
    }

    @Override // org.jetbrains.kotlin.codegen.context.DelegatingToPartContext
    public Type getImplementationOwnerClassType() {
        return getFilePartType();
    }

    @Override // org.jetbrains.kotlin.codegen.context.FacadePartWithSourceFile
    public KtFile getSourceFile() {
        KtFile ktFile = this.sourceFile;
        if (ktFile == null) {
            $$$reportNull$$$0(1);
        }
        return ktFile;
    }
}
