package org.jetbrains.kotlin.fir.declarations;

import java.util.Comparator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: SealedClassInheritors.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0018\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\t*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0002\u001a\u001e\u0010\u0017\u001a\u00020\u0018*\u00020\f2\u0012\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u001a\u001a\u0018\u0010\u0017\u001a\u00020\u0018*\u00020\f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\t\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004\"Q\u0010\u000b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0018\u00010\b*\u00020\f2\u0014\u0010\u0007\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0018\u00010\b8F@BX\u0087\u008e\u0002¢\u0006\u0018\n\u0004\b\u0013\u0010\u0014\u0012\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001c"}, d2 = {"sealedClassInheritorsProvider", "Lorg/jetbrains/kotlin/fir/declarations/SealedClassInheritorsProvider;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSealedClassInheritorsProvider", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/declarations/SealedClassInheritorsProvider;", "sealedClassInheritorsProvider$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "<set-?>", "Lkotlin/Lazy;", "", "Lorg/jetbrains/kotlin/name/ClassId;", "sealedInheritorsAttr", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "getSealedInheritorsAttr$annotations", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)V", "getSealedInheritorsAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lkotlin/Lazy;", "setSealedInheritorsAttr", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;Lkotlin/Lazy;)V", "sealedInheritorsAttr$delegate", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "getSealedClassInheritors", "session", "setSealedClassInheritors", "", "inheritorComputer", "Lkotlin/Function0;", "inheritors", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SealedClassInheritorsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(SealedClassInheritorsKt.class, Constants.TYPE_TREE), "sealedClassInheritorsProvider", "getSealedClassInheritorsProvider(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/declarations/SealedClassInheritorsProvider;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(SealedClassInheritorsKt.class, Constants.TYPE_TREE), "sealedInheritorsAttr", "getSealedInheritorsAttr(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Lkotlin/Lazy;"))};
    private static final ArrayMapAccessor sealedClassInheritorsProvider$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(SealedClassInheritorsProvider.class), (Object) null, 2, (Object) null);
    private static final FirDeclarationDataRegistry.DeclarationDataAccessor sealedInheritorsAttr$delegate = FirDeclarationDataRegistry.INSTANCE.data(SealedClassInheritorsKey.INSTANCE);

    @SealedClassInheritorsProviderInternals
    public static /* synthetic */ void getSealedInheritorsAttr$annotations(FirRegularClass firRegularClass) {
    }

    private static final SealedClassInheritorsProvider getSealedClassInheritorsProvider(FirSession firSession) {
        return (SealedClassInheritorsProvider) sealedClassInheritorsProvider$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final Lazy<List<ClassId>> getSealedInheritorsAttr(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return (Lazy) sealedInheritorsAttr$delegate.getValue(firRegularClass, $$delegatedProperties[1]);
    }

    private static final void setSealedInheritorsAttr(FirRegularClass firRegularClass, Lazy<? extends List<ClassId>> lazy) {
        sealedInheritorsAttr$delegate.setValue(firRegularClass, $$delegatedProperties[1], lazy);
    }

    public static final List<ClassId> getSealedClassInheritors(FirRegularClass firRegularClass, FirSession session) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        if (firRegularClass.getStatus().getModality() != Modality.SEALED) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return getSealedClassInheritorsProvider(session).getSealedClassInheritors(firRegularClass);
    }

    public static final void setSealedClassInheritors(FirRegularClass firRegularClass, List<ClassId> inheritors) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(inheritors, "inheritors");
        if (firRegularClass.getStatus().getModality() != Modality.SEALED) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        setSealedInheritorsAttr(firRegularClass, LazyKt.lazyOf(CollectionsKt.sortedWith(inheritors, new Comparator() { // from class: org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt$setSealedClassInheritors$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((ClassId) t).asFqNameString(), ((ClassId) t2).asFqNameString());
            }
        })));
    }

    public static final void setSealedClassInheritors(FirRegularClass firRegularClass, final Function0<? extends List<ClassId>> inheritorComputer) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        Intrinsics.checkNotNullParameter(inheritorComputer, "inheritorComputer");
        if (firRegularClass.getStatus().getModality() != Modality.SEALED) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        setSealedInheritorsAttr(firRegularClass, LazyKt.lazy(new Function0<List<? extends ClassId>>() { // from class: org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt.setSealedClassInheritors.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ClassId> invoke() {
                return CollectionsKt.sortedWith(inheritorComputer.invoke(), new Comparator() { // from class: org.jetbrains.kotlin.fir.declarations.SealedClassInheritorsKt$setSealedClassInheritors$2$invoke$$inlined$sortedBy$1
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(((ClassId) t).asFqNameString(), ((ClassId) t2).asFqNameString());
                    }
                });
            }
        }));
    }
}
