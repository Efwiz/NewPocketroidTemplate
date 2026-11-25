package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.Collections;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;

/* JADX INFO: loaded from: classes6.dex */
public interface DeclarationProviderFactory {
    public static final DeclarationProviderFactory EMPTY = new FileBasedDeclarationProviderFactory(LockBasedStorageManager.NO_LOCKS, Collections.emptyList());

    void diagnoseMissingPackageFragment(FqName fqName, KtFile ktFile);

    ClassMemberDeclarationProvider getClassMemberDeclarationProvider(KtClassLikeInfo ktClassLikeInfo);

    PackageMemberDeclarationProvider getPackageMemberDeclarationProvider(FqName fqName);
}
