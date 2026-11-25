package org.jetbrains.kotlin.fir.java.enhancement;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactory;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SignatureEnhancement.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R%\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirEnhancedSymbolsStorage;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "cacheByOwner", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirEnhancedSymbolsStorage$EnhancementSymbolsCache;", "", "getCacheByOwner", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "EnhancementSymbolsCache", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEnhancedSymbolsStorage implements FirSessionComponent {
    private final FirCache cacheByOwner;
    private final FirCachesFactory cachesFactory;
    private final FirSession session;

    public FirEnhancedSymbolsStorage(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        FirCachesFactory firCachesFactory = FirCachesFactoryKt.getFirCachesFactory(session);
        this.cachesFactory = firCachesFactory;
        this.cacheByOwner = firCachesFactory.createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirEnhancedSymbolsStorage$special$$inlined$createCache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke((FirRegularClassSymbol) obj, (Void) obj2);
            }

            public final FirEnhancedSymbolsStorage.EnhancementSymbolsCache invoke(FirRegularClassSymbol key, Void r2) {
                Intrinsics.checkNotNullParameter(key, "key");
                return new FirEnhancedSymbolsStorage.EnhancementSymbolsCache(this.this$0.cachesFactory);
            }
        });
    }

    public final FirSession getSession() {
        return this.session;
    }

    public final FirCache getCacheByOwner() {
        return this.cacheByOwner;
    }

    /* JADX INFO: compiled from: SignatureEnhancement.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R?\u0010\u0005\u001a*\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0007\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0007\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00010\n0\b0\u0006¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR=\u0010\u000f\u001a(\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0010\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b0\u0006¢\u0006\u000e\n\u0000\u0012\u0004\b\u0011\u0010\f\u001a\u0004\b\u0012\u0010\u000e¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/enhancement/FirEnhancedSymbolsStorage$EnhancementSymbolsCache;", "", "cachesFactory", "Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;", "(Lorg/jetbrains/kotlin/fir/caches/FirCachesFactory;)V", "enhancedFunctions", "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement;", "Lorg/jetbrains/kotlin/name/Name;", "getEnhancedFunctions$annotations", "()V", "getEnhancedFunctions", "()Lorg/jetbrains/kotlin/fir/caches/FirCache;", "enhancedVariables", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getEnhancedVariables$annotations", "getEnhancedVariables", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnhancementSymbolsCache {
        private final FirCache<FirFunctionSymbol<?>, FirFunctionSymbol<?>, Pair<FirSignatureEnhancement, Name>> enhancedFunctions;
        private final FirCache<FirVariableSymbol<?>, FirVariableSymbol<?>, Pair<FirSignatureEnhancement, Name>> enhancedVariables;

        public static /* synthetic */ void getEnhancedFunctions$annotations() {
        }

        public static /* synthetic */ void getEnhancedVariables$annotations() {
        }

        public EnhancementSymbolsCache(FirCachesFactory cachesFactory) {
            Intrinsics.checkNotNullParameter(cachesFactory, "cachesFactory");
            this.enhancedFunctions = cachesFactory.createCacheWithPostCompute(new Function2<FirFunctionSymbol<?>, Pair<? extends FirSignatureEnhancement, ? extends Name>, Pair<? extends FirFunctionSymbol<?>, ? extends FirSignatureEnhancement>>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirEnhancedSymbolsStorage$EnhancementSymbolsCache$enhancedFunctions$1
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Pair<? extends FirFunctionSymbol<?>, ? extends FirSignatureEnhancement> invoke(FirFunctionSymbol<?> firFunctionSymbol, Pair<? extends FirSignatureEnhancement, ? extends Name> pair) {
                    return invoke2(firFunctionSymbol, (Pair<FirSignatureEnhancement, Name>) pair);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final Pair<FirFunctionSymbol<?>, FirSignatureEnhancement> invoke2(FirFunctionSymbol<?> original, Pair<FirSignatureEnhancement, Name> pair) {
                    Intrinsics.checkNotNullParameter(original, "original");
                    Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 1>");
                    FirSignatureEnhancement firSignatureEnhancementComponent1 = pair.component1();
                    return TuplesKt.to(firSignatureEnhancementComponent1.enhance$java(original, pair.component2()), firSignatureEnhancementComponent1);
                }
            }, new Function3<FirFunctionSymbol<?>, FirFunctionSymbol<?>, FirSignatureEnhancement, Unit>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirEnhancedSymbolsStorage$EnhancementSymbolsCache$enhancedFunctions$2
                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Unit invoke(FirFunctionSymbol<?> firFunctionSymbol, FirFunctionSymbol<?> firFunctionSymbol2, FirSignatureEnhancement firSignatureEnhancement) {
                    invoke2(firFunctionSymbol, firFunctionSymbol2, firSignatureEnhancement);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirFunctionSymbol<?> firFunctionSymbol, FirFunctionSymbol<?> enhancedVersion, FirSignatureEnhancement enhancement) {
                    Intrinsics.checkNotNullParameter(firFunctionSymbol, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(enhancedVersion, "enhancedVersion");
                    Intrinsics.checkNotNullParameter(enhancement, "enhancement");
                    FirFunction firFunction = (FirFunction) enhancedVersion.getFir();
                    FirCallableDeclaration initialSignatureAttr = ClassMembersKt.getInitialSignatureAttr(firFunction);
                    FirSimpleFunction firSimpleFunction = initialSignatureAttr instanceof FirSimpleFunction ? (FirSimpleFunction) initialSignatureAttr : null;
                    if (firSimpleFunction != null) {
                        ClassMembersKt.setInitialSignatureAttr(firFunction, (FirCallableDeclaration) enhancement.enhancedFunction(firSimpleFunction.getSymbol(), firSimpleFunction.getName()).getFir());
                    }
                }
            });
            this.enhancedVariables = cachesFactory.createCache(new Function2<FirVariableSymbol<?>, Pair<? extends FirSignatureEnhancement, ? extends Name>, FirVariableSymbol<?>>() { // from class: org.jetbrains.kotlin.fir.java.enhancement.FirEnhancedSymbolsStorage$EnhancementSymbolsCache$enhancedVariables$1
                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ FirVariableSymbol<?> invoke(FirVariableSymbol<?> firVariableSymbol, Pair<? extends FirSignatureEnhancement, ? extends Name> pair) {
                    return invoke2(firVariableSymbol, (Pair<FirSignatureEnhancement, Name>) pair);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final FirVariableSymbol<?> invoke2(FirVariableSymbol<?> original, Pair<FirSignatureEnhancement, Name> pair) {
                    Intrinsics.checkNotNullParameter(original, "original");
                    Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 1>");
                    return pair.component1().enhance$java(original, pair.component2());
                }
            });
        }

        public final FirCache<FirFunctionSymbol<?>, FirFunctionSymbol<?>, Pair<FirSignatureEnhancement, Name>> getEnhancedFunctions() {
            return this.enhancedFunctions;
        }

        public final FirCache<FirVariableSymbol<?>, FirVariableSymbol<?>, Pair<FirSignatureEnhancement, Name>> getEnhancedVariables() {
            return this.enhancedVariables;
        }
    }
}
