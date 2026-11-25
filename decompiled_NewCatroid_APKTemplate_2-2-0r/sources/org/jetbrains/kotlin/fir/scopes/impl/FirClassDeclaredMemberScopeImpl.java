package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirClassDeclaredMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0019H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u0019H\u0016J5\u0010\u001b\u001a\u00020\u001c\"\u000e\b\u0000\u0010\u001d\u0018\u0001*\u0006\u0012\u0002\b\u00030\u00102\u0006\u0010\u001e\u001a\u00020\n2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u0002H\u001d\u0012\u0004\u0012\u00020\u001c0 H\u0082\bJ.\u0010!\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\n2\u001c\u0010\u001f\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030#\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u001c0\"H\u0016J\u001c\u0010%\u001a\u00020\u001c2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u001c0 H\u0016J$\u0010'\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\n2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u001c0 H\u0016J(\u0010)\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\n2\u0016\u0010\u001f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030*\u0012\u0004\u0012\u00020\u001c0 H\u0016R$\u0010\u000e\u001a\u0018\u0012\u0004\u0012\u00020\n\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00100\t0\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0018\u0010\u0015\u001a\u00020\u0007*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassDeclaredMemberScopeImpl;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassDeclaredMemberScope;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "useLazyNestedClassifierScope", "", "existingNames", "", "Lorg/jetbrains/kotlin/name/Name;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirClass;ZLjava/util/List;Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;)V", "callablesIndex", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "nestedClassifierScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "getUseSiteSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "supportsEnumEntries", "getSupportsEnumEntries", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Z", "getCallableNames", "", "getClassifierNames", "processCallables", "", "D", "name", "processor", "Lkotlin/Function1;", "processClassifiersByNameWithSubstitution", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirClassDeclaredMemberScopeImpl extends FirClassDeclaredMemberScope {
    private final Map<Name, List<FirCallableSymbol<?>>> callablesIndex;
    private final FirContainingNamesAwareScope nestedClassifierScope;
    private final FirSession useSiteSession;

    public /* synthetic */ FirClassDeclaredMemberScopeImpl(FirSession firSession, FirClass firClass, boolean z, List list, FirSymbolProvider firSymbolProvider, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firClass, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : list, (i & 16) != 0 ? null : firSymbolProvider);
    }

    public final FirSession getUseSiteSession() {
        return this.useSiteSession;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirClassDeclaredMemberScopeImpl(FirSession useSiteSession, FirClass klass, boolean z, List<Name> list, FirSymbolProvider firSymbolProvider) {
        FirNestedClassifierScope firNestedClassifierScopeNestedClassifierScope;
        Name name2;
        super(FirDeclarationUtilKt.getClassId(klass));
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.useSiteSession = useSiteSession;
        if (z) {
            ClassId classId = klass.getSymbol().getClassId();
            Intrinsics.checkNotNull(list);
            Intrinsics.checkNotNull(firSymbolProvider);
            firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.lazyNestedClassifierScope(classId, list, firSymbolProvider);
        } else {
            firNestedClassifierScopeNestedClassifierScope = FirDeclaredMemberScopeProviderKt.nestedClassifierScope(useSiteSession, klass);
        }
        this.nestedClassifierScope = firNestedClassifierScopeNestedClassifierScope;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (FirDeclaration firDeclaration : klass.getDeclarations()) {
            if (firDeclaration instanceof FirCallableDeclaration) {
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firDeclaration;
                if (firCallableDeclaration instanceof FirConstructor) {
                    name2 = SpecialNames.INIT;
                } else if (!(firCallableDeclaration instanceof FirVariable)) {
                    if (firCallableDeclaration instanceof FirSimpleFunction) {
                        name2 = ((FirSimpleFunction) firDeclaration).getName();
                    }
                } else if (!(firDeclaration.getOrigin() instanceof FirDeclarationOrigin.Synthetic)) {
                    FirVariable firVariable = (FirVariable) firDeclaration;
                    if (!UtilsKt.isEnumEntries(firVariable, klass) || getSupportsEnumEntries(klass)) {
                        name2 = firVariable.getName();
                    }
                }
                Object obj = linkedHashMap.get(name2);
                if (obj == null) {
                    obj = (List) new ArrayList();
                    linkedHashMap.put(name2, obj);
                }
                ((Collection) obj).add(firCallableDeclaration.getSymbol());
            }
        }
        this.callablesIndex = linkedHashMap;
    }

    private final boolean getSupportsEnumEntries(FirClass firClass) {
        return FirEnumEntriesSupportKt.getEnumEntriesSupport(this.useSiteSession).canSynthesizeEnumEntriesFor(firClass);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (Intrinsics.areEqual(name2, SpecialNames.INIT)) {
            return;
        }
        List<FirCallableSymbol<?>> listEmptyList = this.callablesIndex.get(name2);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        for (FirCallableSymbol<?> firCallableSymbol : listEmptyList) {
            if (firCallableSymbol instanceof FirNamedFunctionSymbol) {
                processor.invoke(firCallableSymbol);
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        List<FirCallableSymbol<?>> listEmptyList = this.callablesIndex.get(SpecialNames.INIT);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        for (FirCallableSymbol<?> firCallableSymbol : listEmptyList) {
            if (firCallableSymbol instanceof FirConstructorSymbol) {
                processor.invoke(firCallableSymbol);
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirContainingNamesAwareScope firContainingNamesAwareScope = this.nestedClassifierScope;
        if (firContainingNamesAwareScope != null) {
            firContainingNamesAwareScope.processClassifiersByNameWithSubstitution(name2, processor);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.callablesIndex.keySet();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        FirContainingNamesAwareScope firContainingNamesAwareScope = this.nestedClassifierScope;
        Set<Name> classifierNames = firContainingNamesAwareScope != null ? firContainingNamesAwareScope.getClassifierNames() : null;
        return classifierNames == null ? SetsKt.emptySet() : classifierNames;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        List<FirCallableSymbol<?>> listEmptyList = this.callablesIndex.get(name2);
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        for (FirCallableSymbol<?> firCallableSymbol : listEmptyList) {
            if (firCallableSymbol instanceof FirVariableSymbol) {
                processor.invoke(firCallableSymbol);
            }
        }
    }
}
