package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a/\u0010\u0007\u001a\u00020\b\"\f\b\u0000\u0010\t*\u0006\u0012\u0002\b\u00030\n*\b\u0012\u0004\u0012\u0002H\t0\u000b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0000(\u0000\u001a-\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\t0\u000e\"\f\b\u0001\u0010\t*\u0006\u0012\u0002\b\u00030\n*\u0002H\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u0011\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0003\u0010\u0004*R\u0010\u0012\"&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u00132&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u0013*@\u0010\u0014\u001a\u0004\b\u0001\u0010\t\"\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u00150\u00160\u00152\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\t0\u00150\u00160\u0015ò\u0001\n\n\b\u0012\u0004\u0012\u00028\u00000\f¨\u0006\u0017"}, d2 = {"intersectionOverrideStorage", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirIntersectionOverrideStorage;", "Lorg/jetbrains/kotlin/fir/FirSession;", "getIntersectionOverrideStorage", "(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/impl/FirIntersectionOverrideStorage;", "intersectionOverrideStorage$delegate", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "isIntersectionOverride", "", "D", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection$NonTrivial;", "withScope", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "baseScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "FirIntersectionOverrideCache", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "MembersByScope", "", "Lkotlin/Pair;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirTypeIntersectionScopeContextKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(FirTypeIntersectionScopeContextKt.class, "providers"), "intersectionOverrideStorage", "getIntersectionOverrideStorage(Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/scopes/impl/FirIntersectionOverrideStorage;"))};
    private static final ArrayMapAccessor intersectionOverrideStorage$delegate = TypeRegistry.generateAccessor$default(FirSession.INSTANCE, Reflection.getOrCreateKotlinClass(FirIntersectionOverrideStorage.class), (Object) null, 2, (Object) null);

    /* JADX INFO: Access modifiers changed from: private */
    public static final <D extends FirCallableSymbol<?>> MemberWithBaseScope<D> withScope(D d, FirTypeScope firTypeScope) {
        return new MemberWithBaseScope<>(d, firTypeScope);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirIntersectionOverrideStorage getIntersectionOverrideStorage(FirSession firSession) {
        return (FirIntersectionOverrideStorage) intersectionOverrideStorage$delegate.getValue((AbstractArrayMapOwner) firSession, $$delegatedProperties[0]);
    }

    public static final <D extends FirCallableSymbol<?>> boolean isIntersectionOverride(FirTypeIntersectionScopeContext.ResultOfIntersection<D> resultOfIntersection) {
        Intrinsics.checkNotNullParameter(resultOfIntersection, "<this>");
        return resultOfIntersection instanceof FirTypeIntersectionScopeContext.ResultOfIntersection.NonTrivial;
    }
}
