package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.resolve.lazy.data.KtClassLikeInfo;

/* JADX INFO: compiled from: ClassMemberDeclarationProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001R\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0012À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProvider;", "companionObjects", "", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "getCompanionObjects", "()Ljava/util/List;", "correspondingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "getCorrespondingClassOrObject", "()Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "ownerInfo", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "getOwnerInfo", "()Lorg/jetbrains/kotlin/resolve/lazy/data/KtClassLikeInfo;", "primaryConstructorParameters", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getPrimaryConstructorParameters", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassMemberDeclarationProvider extends DeclarationProvider {
    KtClassLikeInfo getOwnerInfo();

    default KtPureClassOrObject getCorrespondingClassOrObject() {
        KtClassLikeInfo ownerInfo = getOwnerInfo();
        return ownerInfo != null ? ownerInfo.mo7451getCorrespondingClassOrObject() : null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    default List<KtParameter> getPrimaryConstructorParameters() {
        List list;
        List primaryConstructorParameters;
        KtClassLikeInfo ownerInfo = getOwnerInfo();
        if (ownerInfo != null) {
            primaryConstructorParameters = ownerInfo.getPrimaryConstructorParameters();
        } else {
            list = null;
        }
        if (list == null) {
            list = primaryConstructorParameters;
            return CollectionsKt.emptyList();
        }
        list = primaryConstructorParameters;
        return list;
    }

    default List<KtObjectDeclaration> getCompanionObjects() {
        KtClassLikeInfo ownerInfo = getOwnerInfo();
        List<KtObjectDeclaration> companionObjects = ownerInfo != null ? ownerInfo.getCompanionObjects() : null;
        return companionObjects == null ? CollectionsKt.emptyList() : companionObjects;
    }
}
