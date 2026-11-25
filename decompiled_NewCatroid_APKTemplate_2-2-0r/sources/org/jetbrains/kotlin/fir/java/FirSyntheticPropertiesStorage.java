package org.jetbrains.kotlin.fir.java;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.java.scopes.JavaClassUseSiteMemberScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirTypeIntersectionScopeContext;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirSyntheticPropertiesStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004RO\u0010\u0005\u001a@\u0012\u0004\u0012\u00020\u0007\u0012.\u0012,\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\n0\u0006j\u0002`\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirSyntheticPropertiesStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "cacheByOwner", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirSyntheticPropertySymbol;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassUseSiteMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirTypeIntersectionScopeContext$ResultOfIntersection;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "Lorg/jetbrains/kotlin/fir/java/SyntheticPropertiesCache;", "", "getCacheByOwner", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSyntheticPropertiesStorage implements FirSessionComponent {
    private final FirCache cacheByOwner;
    private final FirCachesFactory cachesFactory;

    public FirSyntheticPropertiesStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.cacheByOwner = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.java.FirSyntheticPropertiesStorage$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FirRegularClass) obj, (Void) obj2);
            }

            public final FirCache<? super Name, ? extends FirSyntheticPropertySymbol, ? super Pair<? extends JavaClassUseSiteMemberScope, ? extends FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>> invoke(FirRegularClass key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.this$0.cachesFactory.createCache(new Function2<Name, Pair<? extends JavaClassUseSiteMemberScope, ? extends FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>, FirSyntheticPropertySymbol>() { // from class: org.jetbrains.kotlin.fir.java.FirSyntheticPropertiesStorage$cacheByOwner$1$1
                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ FirSyntheticPropertySymbol invoke(Name name2, Pair<? extends JavaClassUseSiteMemberScope, ? extends FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>> pair) {
                        return invoke2(name2, (Pair<JavaClassUseSiteMemberScope, ? extends FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>>) pair);
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final FirSyntheticPropertySymbol invoke2(Name name2, Pair<JavaClassUseSiteMemberScope, ? extends FirTypeIntersectionScopeContext.ResultOfIntersection<FirPropertySymbol>> pair) {
                        Intrinsics.checkNotNullParameter(name2, "<anonymous parameter 0>");
                        Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 1>");
                        return pair.component1().syntheticPropertyFromOverride$java(pair.component2());
                    }
                });
            }
        });
    }

    public final FirCache getCacheByOwner() {
        return this.cacheByOwner;
    }
}
