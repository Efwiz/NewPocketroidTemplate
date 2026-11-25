package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSessionKey;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirClassSubstitutionScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR-\u0010\t\u001a\u001e\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u000b\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\n¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirSubstitutionOverrideStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "substitutionOverrideCacheByScope", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSessionKey;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirSubstitutionOverrideStorage$SubstitutionOverrideCache;", "", "getSubstitutionOverrideCacheByScope", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "SubstitutionOverrideCache", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSubstitutionOverrideStorage implements FirSessionComponent {
    private final FirCachesFactory cachesFactory;
    private final FirSession session;
    private final FirCache substitutionOverrideCacheByScope;

    public FirSubstitutionOverrideStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.substitutionOverrideCacheByScope = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirSubstitutionOverrideStorage$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((ScopeSessionKey<?, ?>) obj, (Void) obj2);
            }

            public final FirSubstitutionOverrideStorage.SubstitutionOverrideCache invoke(ScopeSessionKey<?, ?> key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return new FirSubstitutionOverrideStorage.SubstitutionOverrideCache(FirCachesFactoryKt.getFirCachesFactory(this.this$0.getSession()));
            }
        });
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final FirCache getSubstitutionOverrideCacheByScope() {
        return this.substitutionOverrideCacheByScope;
    }

    /* JADX INFO: compiled from: FirClassSubstitutionScope.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR#\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR+\u0010\u000e\u001a\u001c\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\n¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirSubstitutionOverrideStorage$SubstitutionOverrideCache;", "", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "(Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;)V", "overridesForConstructors", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassSubstitutionScope;", "getOverridesForConstructors", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "overridesForFunctions", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "getOverridesForFunctions", "overridesForVariables", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getOverridesForVariables", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SubstitutionOverrideCache {
        private final FirCache<FirConstructorSymbol, FirConstructorSymbol, FirClassSubstitutionScope> overridesForConstructors;
        private final FirCache<FirNamedFunctionSymbol, FirNamedFunctionSymbol, FirClassSubstitutionScope> overridesForFunctions;
        private final FirCache<FirVariableSymbol<?>, FirVariableSymbol<?>, FirClassSubstitutionScope> overridesForVariables;

        public SubstitutionOverrideCache(FirCachesFactory cachesFactory) {
            Intrinsics.checkNotNullParameter(cachesFactory, "cachesFactory");
            this.overridesForFunctions = cachesFactory.createCache(new Function2<FirNamedFunctionSymbol, FirClassSubstitutionScope, FirNamedFunctionSymbol>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirSubstitutionOverrideStorage$SubstitutionOverrideCache$overridesForFunctions$1
                @Override // kotlin.jvm.functions.Function2
                public final FirNamedFunctionSymbol invoke(FirNamedFunctionSymbol original, FirClassSubstitutionScope scope) {
                    Intrinsics.checkNotNullParameter(original, "original");
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    return scope.createSubstitutionOverrideFunction(original);
                }
            });
            this.overridesForConstructors = cachesFactory.createCache(new Function2<FirConstructorSymbol, FirClassSubstitutionScope, FirConstructorSymbol>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirSubstitutionOverrideStorage$SubstitutionOverrideCache$overridesForConstructors$1
                @Override // kotlin.jvm.functions.Function2
                public final FirConstructorSymbol invoke(FirConstructorSymbol original, FirClassSubstitutionScope scope) {
                    Intrinsics.checkNotNullParameter(original, "original");
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    return scope.createSubstitutionOverrideConstructor(original);
                }
            });
            this.overridesForVariables = cachesFactory.createCache(new Function2<FirVariableSymbol<?>, FirClassSubstitutionScope, FirVariableSymbol<?>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirSubstitutionOverrideStorage$SubstitutionOverrideCache$overridesForVariables$1
                @Override // kotlin.jvm.functions.Function2
                public final FirVariableSymbol<?> invoke(FirVariableSymbol<?> original, FirClassSubstitutionScope scope) {
                    Intrinsics.checkNotNullParameter(original, "original");
                    Intrinsics.checkNotNullParameter(scope, "scope");
                    if (original instanceof FirPropertySymbol) {
                        return scope.createSubstitutionOverrideProperty((FirPropertySymbol) original);
                    }
                    if (original instanceof FirFieldSymbol) {
                        return scope.createSubstitutionOverrideField((FirFieldSymbol) original);
                    }
                    KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("symbol " + original.getClass() + " is not overridable", null);
                    ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                    FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "original", original.getFir());
                    kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                    throw kotlinIllegalArgumentExceptionWithAttachments;
                }
            });
        }

        public final FirCache<FirNamedFunctionSymbol, FirNamedFunctionSymbol, FirClassSubstitutionScope> getOverridesForFunctions() {
            return this.overridesForFunctions;
        }

        public final FirCache<FirConstructorSymbol, FirConstructorSymbol, FirClassSubstitutionScope> getOverridesForConstructors() {
            return this.overridesForConstructors;
        }

        public final FirCache<FirVariableSymbol<?>, FirVariableSymbol<?>, FirClassSubstitutionScope> getOverridesForVariables() {
            return this.overridesForVariables;
        }
    }
}
