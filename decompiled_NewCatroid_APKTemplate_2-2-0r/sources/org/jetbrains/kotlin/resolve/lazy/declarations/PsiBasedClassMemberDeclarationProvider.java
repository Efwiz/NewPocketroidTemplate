package org.jetbrains.kotlin.resolve.lazy.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: PsiBasedClassMemberDeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/PsiBasedClassMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/AbstractPsiBasedDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "ownerInfo", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;)V", "getOwnerInfo", "()Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "doCreateIndex", "", "index", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/AbstractPsiBasedDeclarationProvider$Index;", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiBasedClassMemberDeclarationProvider extends AbstractPsiBasedDeclarationProvider implements ClassMemberDeclarationProvider {
    private final KtClassLikeInfo ownerInfo;

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider
    public KtClassLikeInfo getOwnerInfo() {
        return this.ownerInfo;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiBasedClassMemberDeclarationProvider(StorageManager storageManager, KtClassLikeInfo ownerInfo) {
        super(storageManager);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(ownerInfo, "ownerInfo");
        this.ownerInfo = ownerInfo;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.AbstractPsiBasedDeclarationProvider
    protected void doCreateIndex(AbstractPsiBasedDeclarationProvider.Index index) {
        Intrinsics.checkNotNullParameter(index, "index");
        for (KtDeclaration ktDeclaration : getOwnerInfo().getDeclarations()) {
            Intrinsics.checkNotNull(ktDeclaration);
            index.putToIndex(ktDeclaration);
        }
        for (KtParameter ktParameter : getOwnerInfo().getPrimaryConstructorParameters()) {
            if (ktParameter.hasValOrVar()) {
                Intrinsics.checkNotNull(ktParameter);
                index.putToIndex(ktParameter);
            }
        }
    }

    public String toString() {
        return "Declarations for " + getOwnerInfo();
    }
}
