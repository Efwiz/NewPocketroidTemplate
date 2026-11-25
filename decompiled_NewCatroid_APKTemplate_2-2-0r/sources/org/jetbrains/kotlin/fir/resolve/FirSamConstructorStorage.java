package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;

/* JADX INFO: compiled from: SamResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R)\u0010\u0005\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0007\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirSamConstructorStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "samConstructors", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/FirSamResolver;", "getSamConstructors", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSamConstructorStorage implements FirSessionComponent {
    private final FirCache<FirClassLikeSymbol<?>, FirNamedFunctionSymbol, FirSamResolver> samConstructors;

    public FirSamConstructorStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.samConstructors = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2<FirClassLikeSymbol<?>, FirSamResolver, FirNamedFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.resolve.FirSamConstructorStorage$samConstructors$1
            @Override // kotlin.jvm.functions.Function2
            public final FirNamedFunctionSymbol invoke(FirClassLikeSymbol<?> classSymbol, FirSamResolver samResolver) {
                Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
                Intrinsics.checkNotNullParameter(samResolver, "samResolver");
                if (classSymbol instanceof FirRegularClassSymbol) {
                    return samResolver.buildSamConstructorForRegularClass((FirRegularClassSymbol) classSymbol);
                }
                if (classSymbol instanceof FirTypeAliasSymbol) {
                    return samResolver.buildSamConstructorForTypeAlias((FirTypeAliasSymbol) classSymbol);
                }
                if (classSymbol instanceof FirAnonymousObjectSymbol) {
                    return null;
                }
                throw new NoWhenBranchMatchedException();
            }
        });
    }

    public final FirCache<FirClassLikeSymbol<?>, FirNamedFunctionSymbol, FirSamResolver> getSamConstructors() {
        return this.samConstructors;
    }
}
