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
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirScriptDeclarationsScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u0013H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u0013H\u0016J5\u0010\u0015\u001a\u00020\u0016\"\u000e\b\u0000\u0010\u0017\u0018\u0001*\u0006\u0012\u0002\b\u00030\u000b2\u0006\u0010\u0018\u001a\u00020\t2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0017\u0012\u0004\u0012\u00020\u00160\u001aH\u0082\bJ.\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\t2\u001c\u0010\u0019\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001d\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00160\u001cH\u0016J$\u0010\u001f\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\t2\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u00160\u001aH\u0016J(\u0010!\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\t2\u0016\u0010\u0019\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\"\u0012\u0004\u0012\u00020\u00160\u001aH\u0016R$\u0010\u0007\u001a\u0018\u0012\u0004\u0012\u00020\t\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\r0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirScriptDeclarationsScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "script", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirScript;)V", "callablesIndex", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "classIndex", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getScript", "()Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "getUseSiteSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getCallableNames", "", "getClassifierNames", "processCallables", "", "D", "name", "processor", "Lkotlin/Function1;", "processClassifiersByNameWithSubstitution", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScriptDeclarationsScope extends FirContainingNamesAwareScope {
    private final Map<Name, List<FirCallableSymbol<?>>> callablesIndex;
    private final Map<Name, FirRegularClassSymbol> classIndex;
    private final FirScript script;
    private final FirSession useSiteSession;

    public final FirSession getUseSiteSession() {
        return this.useSiteSession;
    }

    public final FirScript getScript() {
        return this.script;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x005e  */
    public FirScriptDeclarationsScope(FirSession useSiteSession, FirScript script) {
        Name name2;
        Object obj;
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(script, "script");
        this.useSiteSession = useSiteSession;
        this.script = script;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (FirAnnotationContainer firAnnotationContainer : this.script.getStatements()) {
            if (firAnnotationContainer instanceof FirCallableDeclaration) {
                FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firAnnotationContainer;
                if (!(firCallableDeclaration instanceof FirVariable)) {
                    if (firCallableDeclaration instanceof FirSimpleFunction) {
                        name2 = ((FirSimpleFunction) firAnnotationContainer).getName();
                        obj = linkedHashMap.get(name2);
                        if (obj == null) {
                            obj = (List) new ArrayList();
                            linkedHashMap.put(name2, obj);
                        }
                        ((Collection) obj).add(firCallableDeclaration.getSymbol());
                    }
                } else if (!(((FirDeclaration) firAnnotationContainer).getOrigin() instanceof FirDeclarationOrigin.Synthetic)) {
                    name2 = ((FirVariable) firAnnotationContainer).getName();
                    obj = linkedHashMap.get(name2);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        linkedHashMap.put(name2, obj);
                    }
                    ((Collection) obj).add(firCallableDeclaration.getSymbol());
                }
            }
        }
        this.callablesIndex = linkedHashMap;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (FirStatement firStatement : this.script.getStatements()) {
            if (firStatement instanceof FirRegularClass) {
                FirRegularClass firRegularClass = (FirRegularClass) firStatement;
                linkedHashMap2.put(firRegularClass.getName(), firRegularClass.getSymbol());
            }
        }
        this.classIndex = linkedHashMap2;
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

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.callablesIndex.keySet();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirRegularClassSymbol firRegularClassSymbol = this.classIndex.get(name2);
        if (firRegularClassSymbol == null) {
            return;
        }
        List<FirTypeParameterSymbol> typeParameterSymbols = firRegularClassSymbol.getTypeParameterSymbols();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameterSymbols, 10)), 16));
        for (Object obj : typeParameterSymbols) {
            linkedHashMap.put(obj, FirNestedClassifierScopeKt.toConeType((FirTypeParameterSymbol) obj));
        }
        processor.invoke(firRegularClassSymbol, new ConeSubstitutorByMap(linkedHashMap, this.useSiteSession));
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.classIndex.keySet();
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
