package org.jetbrains.kotlin.fir.declarations;

import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: SealedClassInheritors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/SealedClassInheritorsProviderImpl;", "Lorg/jetbrains/kotlin/fir/declarations/SealedClassInheritorsProvider;", "()V", "getSealedClassInheritors", "", "Lorg/jetbrains/kotlin/name/ClassId;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SealedClassInheritorsProviderImpl extends SealedClassInheritorsProvider {
    public static final SealedClassInheritorsProviderImpl INSTANCE = new SealedClassInheritorsProviderImpl();

    private SealedClassInheritorsProviderImpl() {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsProvider
    public List<ClassId> getSealedClassInheritors(FirRegularClass firClass) {
        List<ClassId> value;
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        Lazy<List<ClassId>> sealedInheritorsAttr = SealedClassInheritorsKt.getSealedInheritorsAttr(firClass);
        return (sealedInheritorsAttr == null || (value = sealedInheritorsAttr.getValue()) == null) ? CollectionsKt.emptyList() : value;
    }
}
