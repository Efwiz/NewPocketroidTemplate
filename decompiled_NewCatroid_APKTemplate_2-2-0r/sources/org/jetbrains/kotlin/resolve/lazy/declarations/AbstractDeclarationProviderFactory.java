package org.jetbrains.kotlin.resolve.lazy.declarations;

import kotlin.jvm.functions.Function1;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNullable;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: loaded from: classes6.dex */
public abstract class AbstractDeclarationProviderFactory implements DeclarationProviderFactory {
    private final MemoizedFunctionToNullable<FqName, PackageMemberDeclarationProvider> packageDeclarationProviders;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "packageFqName";
        } else if (i != 2) {
            objArr[0] = "storageManager";
        } else {
            objArr[0] = "fqName";
        }
        objArr[1] = "org/jetbrains/kotlin/resolve/lazy/declarations/AbstractDeclarationProviderFactory";
        if (i == 1) {
            objArr[2] = "getPackageMemberDeclarationProvider";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "diagnoseMissingPackageFragment";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract PackageMemberDeclarationProvider createPackageMemberDeclarationProvider(FqName fqName);

    public abstract boolean packageExists(FqName fqName);

    public AbstractDeclarationProviderFactory(StorageManager storageManager) {
        if (storageManager == null) {
            $$$reportNull$$$0(0);
        }
        this.packageDeclarationProviders = storageManager.createMemoizedFunctionWithNullableValues(new Function1() { // from class: org.jetbrains.kotlin.resolve.lazy.declarations.AbstractDeclarationProviderFactory$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.createPackageMemberDeclarationProvider((FqName) obj);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
    public PackageMemberDeclarationProvider getPackageMemberDeclarationProvider(FqName fqName) {
        if (fqName == null) {
            $$$reportNull$$$0(1);
        }
        if (packageExists(fqName)) {
            return this.packageDeclarationProviders.invoke(fqName);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactory
    public void diagnoseMissingPackageFragment(FqName fqName, KtFile ktFile) {
        if (fqName == null) {
            $$$reportNull$$$0(2);
        }
        String str = "Cannot find package fragment " + fqName;
        if (ktFile != null) {
            str = str + "\nvFile = " + ktFile.getVirtualFilePath() + ", file package = '" + ktFile.getPackageFqName() + FormatHelper.QUOTE;
        }
        throw new IllegalStateException(str);
    }
}
