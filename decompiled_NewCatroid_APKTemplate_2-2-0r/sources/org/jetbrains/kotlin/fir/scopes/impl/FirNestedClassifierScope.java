package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirNestedClassifierScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u0016\u0010\u000e\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\rH$J\b\u0010\u0011\u001a\u00020\u0012H&J.\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\r2\u001c\u0010\u0015\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0017\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00140\u0016H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;Lorg/jetbrains/kotlin/fir/FirSession;)V", "getKlass", "()Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getUseSiteSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getNestedClassSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "name", "isEmpty", "", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirNestedClassifierScope extends FirContainingNamesAwareScope {
    private final FirClass klass;
    private final FirSession useSiteSession;

    protected abstract FirClassLikeSymbol<?> getNestedClassSymbol(Name name2);

    public abstract boolean isEmpty();

    public FirNestedClassifierScope(FirClass klass, FirSession useSiteSession) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        this.klass = klass;
        this.useSiteSession = useSiteSession;
    }

    public final FirClass getKlass() {
        return this.klass;
    }

    public final FirSession getUseSiteSession() {
        return this.useSiteSession;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        ConeSubstitutor coneSubstitutorByMap;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirClassLikeSymbol<?> nestedClassSymbol = getNestedClassSymbol(name2);
        if (nestedClassSymbol == null) {
            return;
        }
        if (this.klass.getTypeParameters().isEmpty()) {
            coneSubstitutorByMap = ConeSubstitutor.Empty.INSTANCE;
        } else {
            List<FirTypeParameterRef> typeParameters = this.klass.getTypeParameters();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameters, 10)), 16));
            for (FirTypeParameterRef firTypeParameterRef : typeParameters) {
                Pair pair = TuplesKt.to(firTypeParameterRef.getSymbol(), FirNestedClassifierScopeKt.toConeType(firTypeParameterRef));
                linkedHashMap.put(pair.getFirst(), pair.getSecond());
            }
            coneSubstitutorByMap = new ConeSubstitutorByMap(linkedHashMap, this.useSiteSession);
        }
        processor.invoke(nestedClassSymbol, coneSubstitutorByMap);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return SetsKt.emptySet();
    }
}
