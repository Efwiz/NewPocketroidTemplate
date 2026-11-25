package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.scopes.MemberWithBaseScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirTypeIntersectionScopeContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004RM\u0010\u0005\u001a>\u0012\u0004\u0012\u00020\u0007\u0012,\u0012*\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\t\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\u0006j\u0002`\u000b\u0012\u0006\u0012\u0004\u0018\u00010\f0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirIntersectionOverrideStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "cacheByScope", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/MemberWithBaseScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection$NonTrivial;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirIntersectionOverrideCache;", "", "getCacheByScope", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirIntersectionOverrideStorage implements FirSessionComponent {
    private final FirCache cacheByScope;
    private final FirCachesFactory cachesFactory;
    private final FirSession session;

    public FirIntersectionOverrideStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.cacheByScope = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirIntersectionOverrideStorage$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ConeKotlinType) obj, (Void) obj2);
            }

            public final FirCache<? super FirCallableSymbol<?>, ? extends MemberWithBaseScope<? extends FirCallableSymbol<?>>, ? super FirTypeIntersectionScopeContext.ResultOfIntersection.NonTrivial<?>> invoke(ConeKotlinType key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.this$0.cachesFactory.createCache(new Function2<FirCallableSymbol<?>, FirTypeIntersectionScopeContext.ResultOfIntersection.NonTrivial<?>, MemberWithBaseScope<? extends FirCallableSymbol<?>>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirIntersectionOverrideStorage$cacheByScope$1$1
                    @Override // kotlin.jvm.functions.Function2
                    public final MemberWithBaseScope<FirCallableSymbol<?>> invoke(FirCallableSymbol<?> firCallableSymbol, FirTypeIntersectionScopeContext.ResultOfIntersection.NonTrivial<?> result) {
                        Intrinsics.checkNotNullParameter(firCallableSymbol, "<anonymous parameter 0>");
                        Intrinsics.checkNotNullParameter(result, "result");
                        return result.getContext().createIntersectionOverride(result.getMostSpecific(), result.getOverriddenMembers());
                    }
                });
            }
        });
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final FirCache getCacheByScope() {
        return this.cacheByScope;
    }
}
