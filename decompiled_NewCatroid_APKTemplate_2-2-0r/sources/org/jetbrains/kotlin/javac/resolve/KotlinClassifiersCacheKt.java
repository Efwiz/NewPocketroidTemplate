package org.jetbrains.kotlin.javac.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: KotlinClassifiersCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0002\u001a\u000e\u0010\u0002\u001a\u0004\u0018\u00010\u0003*\u00020\u0004H\u0002¨\u0006\u0005"}, d2 = {"shouldNotBeCalled", "", "computeClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "javac-wrapper"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinClassifiersCacheKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId computeClassId(KtClassOrObject ktClassOrObject) {
        ClassId classIdComputeClassId;
        ClassId classIdCreateNestedClassId;
        KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(ktClassOrObject);
        if (containingClassOrObject != null && (classIdComputeClassId = computeClassId(containingClassOrObject)) != null && (classIdCreateNestedClassId = classIdComputeClassId.createNestedClassId(ktClassOrObject.getNameAsSafeName())) != null) {
            return classIdCreateNestedClassId;
        }
        FqName fqName = ktClassOrObject.mo7391getFqName();
        if (fqName != null) {
            return ClassId.topLevel(fqName);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Void shouldNotBeCalled() {
        throw new UnsupportedOperationException("Should not be called");
    }
}
