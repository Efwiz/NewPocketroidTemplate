package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirClassAnySynthesizedMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR%\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirSynthesizedStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "synthesizedCacheByScope", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirSynthesizedStorage$SynthesizedCache;", "", "getSynthesizedCacheByScope", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "SynthesizedCache", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSynthesizedStorage implements FirSessionComponent {
    private final FirCachesFactory cachesFactory;
    private final FirSession session;
    private final FirCache synthesizedCacheByScope;

    public FirSynthesizedStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.synthesizedCacheByScope = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirSynthesizedStorage$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ConeClassLikeLookupTag) obj, (Void) obj2);
            }

            public final FirSynthesizedStorage.SynthesizedCache invoke(ConeClassLikeLookupTag key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return new FirSynthesizedStorage.SynthesizedCache(FirCachesFactoryKt.getFirCachesFactory(this.this$0.getSession()));
            }
        });
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final FirCache getSynthesizedCacheByScope() {
        return this.synthesizedCacheByScope;
    }

    /* JADX INFO: compiled from: FirClassAnySynthesizedMemberScope.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirSynthesizedStorage$SynthesizedCache;", "", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "(Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;)V", "synthesizedFunction", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassAnySynthesizedMemberScope;", "getSynthesizedFunction", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SynthesizedCache {
        private final FirCache<Name, FirNamedFunctionSymbol, FirClassAnySynthesizedMemberScope> synthesizedFunction;

        public SynthesizedCache(FirCachesFactory cachesFactory) {
            Intrinsics.checkNotNullParameter(cachesFactory, "cachesFactory");
            this.synthesizedFunction = cachesFactory.createCache(new Function2<Name, FirClassAnySynthesizedMemberScope, FirNamedFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirSynthesizedStorage$SynthesizedCache$synthesizedFunction$1
                @Override // kotlin.jvm.functions.Function2
                public final FirNamedFunctionSymbol invoke(Name name2, FirClassAnySynthesizedMemberScope scope) {
                    Intrinsics.checkNotNullParameter(name2, "name");
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    return scope.generateSyntheticFunctionByName$providers(name2);
                }
            });
        }

        public final FirCache<Name, FirNamedFunctionSymbol, FirClassAnySynthesizedMemberScope> getSynthesizedFunction() {
            return this.synthesizedFunction;
        }
    }
}
