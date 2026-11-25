package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutorByMap;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirBackingFieldSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.util.PersistentMultimap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirLocalScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004BO\b\u0002\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u0006\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\u0006\u0012\u0006\u0010\u000e\u001a\u00020\u0003¢\u0006\u0002\u0010\u000fJ\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00070\u0018H\u0016J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u0018H\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u0007H\u0016J.\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001c\u001a\u00020\u00072\u001c\u0010\u001f\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030!\u0012\u0004\u0012\u00020\"\u0012\u0004\u0012\u00020\u001e0 H\u0016J$\u0010#\u001a\u00020\u001e2\u0006\u0010\u001c\u001a\u00020\u00072\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u001e0$H\u0016J(\u0010%\u001a\u00020\u001e2\u0006\u0010\u001c\u001a\u00020\u00072\u0016\u0010\u001f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0004\u0012\u00020\u001e0$H\u0016J\u0016\u0010&\u001a\u00020\u00002\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0002\u001a\u00020\u0003J\u0016\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020+2\u0006\u0010\u0002\u001a\u00020\u0003J\u0016\u0010,\u001a\u00020\u00002\u0006\u0010-\u001a\u00020.2\u0006\u0010\u0002\u001a\u00020\u0003J\u0016\u0010/\u001a\u00020\u00002\u0006\u00100\u001a\u0002012\u0006\u0010\u0002\u001a\u00020\u0003R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R!\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u0007\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u000e\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "properties", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "functions", "Lorg/jetbrains/kotlin/fir/util/PersistentMultimap;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "classes", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "useSiteSession", "(Lkotlinx/collections/immutable/PersistentMap;Lorg/jetbrains/kotlin/fir/util/PersistentMultimap;Lkotlinx/collections/immutable/PersistentMap;Lorg/jetbrains/kotlin/fir/FirSession;)V", "getClasses", "()Lkotlinx/collections/immutable/PersistentMap;", "getFunctions", "()Lorg/jetbrains/kotlin/fir/util/PersistentMultimap;", "getProperties", "getUseSiteSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getCallableNames", "", "getClassifierNames", "mayContainName", "", "name", "processClassifiersByNameWithSubstitution", "", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processFunctionsByName", "Lkotlin/Function1;", "processPropertiesByName", "storeBackingField", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "storeClass", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "storeFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "storeVariable", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLocalScope extends FirContainingNamesAwareScope {
    private final PersistentMap<Name, FirRegularClassSymbol> classes;
    private final PersistentMultimap<Name, FirNamedFunctionSymbol> functions;
    private final PersistentMap<Name, FirVariableSymbol<?>> properties;
    private final FirSession useSiteSession;

    public final PersistentMap<Name, FirVariableSymbol<?>> getProperties() {
        return this.properties;
    }

    public final PersistentMultimap<Name, FirNamedFunctionSymbol> getFunctions() {
        return this.functions;
    }

    public final PersistentMap<Name, FirRegularClassSymbol> getClasses() {
        return this.classes;
    }

    public final FirSession getUseSiteSession() {
        return this.useSiteSession;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private FirLocalScope(PersistentMap<Name, ? extends FirVariableSymbol<?>> persistentMap, PersistentMultimap<Name, FirNamedFunctionSymbol> persistentMultimap, PersistentMap<Name, FirRegularClassSymbol> persistentMap2, FirSession firSession) {
        this.properties = persistentMap;
        this.functions = persistentMultimap;
        this.classes = persistentMap2;
        this.useSiteSession = firSession;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirLocalScope(FirSession session) {
        this(ExtensionsKt.persistentMapOf(new Pair[0]), new PersistentMultimap(), ExtensionsKt.persistentMapOf(new Pair[0]), session);
        Intrinsics.checkNotNullParameter(session, "session");
    }

    public final FirLocalScope storeClass(FirRegularClass klass, FirSession session) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(session, "session");
        return new FirLocalScope(this.properties, this.functions, this.classes.put(klass.getName(), klass.getSymbol()), session);
    }

    public final FirLocalScope storeFunction(FirSimpleFunction function, FirSession session) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(session, "session");
        return new FirLocalScope(this.properties, this.functions.put(function.getName(), function.getSymbol()), this.classes, session);
    }

    public final FirLocalScope storeVariable(FirVariable variable, FirSession session) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(session, "session");
        return new FirLocalScope(this.properties.put(variable.getName(), variable.getSymbol()), this.functions, this.classes, session);
    }

    public final FirLocalScope storeBackingField(FirProperty property, FirSession session) {
        FirBackingFieldSymbol symbol;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(session, "session");
        FirBackingField backingField = property.getBackingField();
        PersistentMap<Name, FirVariableSymbol<?>> persistentMapPut = (backingField == null || (symbol = backingField.getSymbol()) == null) ? null : this.properties.put(StandardNames.BACKING_FIELD, symbol);
        if (persistentMapPut == null) {
            persistentMapPut = this.properties;
        }
        return new FirLocalScope(persistentMapPut, this.functions, this.classes, session);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        Iterator<FirNamedFunctionSymbol> it = this.functions.get(name2).iterator();
        while (it.hasNext()) {
            processor.invoke(it.next());
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirVariableSymbol firVariableSymbol = (FirVariableSymbol) this.properties.get(name2);
        if (firVariableSymbol != null) {
            processor.invoke(firVariableSymbol);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        FirRegularClassSymbol firRegularClassSymbol = (FirRegularClassSymbol) this.classes.get(name2);
        if (firRegularClassSymbol != null) {
            List<FirTypeParameterSymbol> typeParameterSymbols = firRegularClassSymbol.getTypeParameterSymbols();
            LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(typeParameterSymbols, 10)), 16));
            for (Object obj : typeParameterSymbols) {
                linkedHashMap.put(obj, FirNestedClassifierScopeKt.toConeType((FirTypeParameterSymbol) obj));
            }
            processor.invoke(firRegularClassSymbol, new ConeSubstitutorByMap(linkedHashMap, this.useSiteSession));
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public boolean mayContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.properties.containsKey(name2) || !this.functions.get(name2).isEmpty() || this.classes.containsKey(name2);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return SetsKt.plus(this.properties.keySet(), (Iterable) this.functions.getKeys());
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.classes.keySet();
    }
}
