package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirSyntheticPropertiesStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004*V\u0010\u0007\"(\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u000b0\b2(\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u000b0\b¨\u0006\u000f"}, d2 = {"syntheticPropertiesStorage", "Lorg/jetbrains/kotlin/fir/java/FirSyntheticPropertiesStorage;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSyntheticPropertiesStorage", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/java/FirSyntheticPropertiesStorage;", "syntheticPropertiesStorage$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "SyntheticPropertiesCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassUseSiteMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticPropertiesStorageKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirSyntheticPropertiesStorageKt.class, SuffixConstants.EXTENSION_java), "syntheticPropertiesStorage", "getSyntheticPropertiesStorage(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/java/FirSyntheticPropertiesStorage;"))};
    private static final ArrayMapAccessor syntheticPropertiesStorage$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirSyntheticPropertiesStorage.class), (Object) null, 2, (Object) null);

    public static final FirSyntheticPropertiesStorage getSyntheticPropertiesStorage(FirSession firSession) {
        Intrinsics.checkNotNullParameter(firSession, "<this>");
        return (FirSyntheticPropertiesStorage) syntheticPropertiesStorage$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }
}
