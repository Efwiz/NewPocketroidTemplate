package org.jetbrains.kotlin.fir.extensions.utils;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.caches.FirCache;
import org.jetbrains.kotlin.fir.caches.FirCachesFactoryKt;
import org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent;
import org.jetbrains.kotlin.fir.extensions.FirPredicateBasedProviderKt;
import org.jetbrains.kotlin.fir.extensions.predicate.DeclarationPredicate;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: AbstractSimpleClassPredicateMatchingService.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0007J\f\u0010\u0010\u001a\u00020\b*\u00020\u0007H\u0002J\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013R\"\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\n\u001a\u00020\u000bX¤\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/utils/AbstractSimpleClassPredicateMatchingService;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/fir/caches/FirCache;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "", "", "predicate", "Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "getPredicate", "()Lorg/jetbrains/kotlin/fir/extensions/predicate/DeclarationPredicate;", "isAnnotated", NativeSymbol.TYPE_NAME, "annotated", "registerPredicates", "", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationPredicateRegistrar;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractSimpleClassPredicateMatchingService extends FirExtensionSessionComponent {
    private final FirCache cache;

    protected abstract DeclarationPredicate getPredicate();

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractSimpleClassPredicateMatchingService(FirSession session) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        this.cache = FirCachesFactoryKt.getFirCachesFactory(session).createCache(new Function2() { // from class: org.jetbrains.kotlin.fir.extensions.utils.AbstractSimpleClassPredicateMatchingService$cache$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Boolean invoke(FirRegularClassSymbol symbol, Void r2) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                return Boolean.valueOf(this.this$0.annotated(symbol));
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtension
    public final void registerPredicates(FirDeclarationPredicateRegistrar firDeclarationPredicateRegistrar) {
        Intrinsics.checkNotNullParameter(firDeclarationPredicateRegistrar, "<this>");
        firDeclarationPredicateRegistrar.register(getPredicate());
    }

    public final boolean isAnnotated(FirRegularClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return ((Boolean) this.cache.getValue(symbol, null)).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean annotated(FirRegularClassSymbol firRegularClassSymbol) {
        if (FirPredicateBasedProviderKt.getPredicateBasedProvider(getSession()).matches(getPredicate(), firRegularClassSymbol)) {
            return true;
        }
        List<ConeKotlinType> resolvedSuperTypes = firRegularClassSymbol.getResolvedSuperTypes();
        if (!(resolvedSuperTypes instanceof Collection) || !resolvedSuperTypes.isEmpty()) {
            Iterator<T> it = resolvedSuperTypes.iterator();
            while (it.hasNext()) {
                FirRegularClassSymbol regularClassSymbol = TypeUtilsKt.toRegularClassSymbol((ConeKotlinType) it.next(), getSession());
                if (regularClassSymbol == null ? false : ((Boolean) this.cache.getValue(regularClassSymbol, null)).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }
}
