package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: FirPackageMemberScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\u0002\u0010\u000bJ.\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\n2\u001c\u0010\u001f\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u001d0 H\u0016J$\u0010\"\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\n2\u0012\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u001d0#H\u0016J(\u0010$\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\n2\u0016\u0010\u001f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030%\u0012\u0004\u0012\u00020\u001d0#H\u0016R \u0010\f\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R \u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\rX\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00120\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirPackageMemberScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "excludedNames", "", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;Ljava/util/Set;)V", "classifierCache", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "functionCache", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "propertyCache", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "scopeOwnerLookupNames", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "processClassifiersByNameWithSubstitution", "", "name", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processFunctionsByName", "Lkotlin/Function1;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPackageMemberScope extends FirScope {
    private final Map<Name, FirClassifierSymbol<?>> classifierCache;
    private final Set<Name> excludedNames;
    private final FqName fqName;
    private final Map<Name, List<FirNamedFunctionSymbol>> functionCache;
    private final Map<Name, List<FirPropertySymbol>> propertyCache;
    private final List<String> scopeOwnerLookupNames;
    private final FirSession session;
    private final FirSymbolProvider symbolProvider;

    public final FqName getFqName() {
        return this.fqName;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public /* synthetic */ FirPackageMemberScope(FqName fqName, FirSession firSession, FirSymbolProvider firSymbolProvider, Set set, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fqName, firSession, (i & 4) != 0 ? FirSymbolProviderKt.getSymbolProvider(firSession) : firSymbolProvider, (i & 8) != 0 ? SetsKt.emptySet() : set);
    }

    public FirPackageMemberScope(FqName fqName, FirSession session, FirSymbolProvider symbolProvider, Set<Name> excludedNames) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
        Intrinsics.checkNotNullParameter(excludedNames, "excludedNames");
        this.fqName = fqName;
        this.session = session;
        this.symbolProvider = symbolProvider;
        this.excludedNames = excludedNames;
        this.classifierCache = new LinkedHashMap();
        this.functionCache = new LinkedHashMap();
        this.propertyCache = new LinkedHashMap();
        this.scopeOwnerLookupNames = new SmartList(fqName.asString());
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        String strAsString = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (strAsString.length() == 0 || this.excludedNames.contains(name2)) {
            return;
        }
        Map<Name, FirClassifierSymbol<?>> map = this.classifierCache;
        FirClassLikeSymbol<?> classLikeSymbolByClassId = map.get(name2);
        if (classLikeSymbolByClassId == null) {
            classLikeSymbolByClassId = this.symbolProvider.getClassLikeSymbolByClassId(new ClassId(this.fqName, name2));
            map.put(name2, classLikeSymbolByClassId);
        }
        FirClassifierSymbol<?> firClassifierSymbol = classLikeSymbolByClassId;
        if (firClassifierSymbol != null) {
            processor.invoke(firClassifierSymbol, ConeSubstitutor.Empty.INSTANCE);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (this.excludedNames.contains(name2)) {
            return;
        }
        Map<Name, List<FirNamedFunctionSymbol>> map = this.functionCache;
        List<FirNamedFunctionSymbol> topLevelFunctionSymbols = map.get(name2);
        if (topLevelFunctionSymbols == null) {
            topLevelFunctionSymbols = this.symbolProvider.getTopLevelFunctionSymbols(this.fqName, name2);
            map.put(name2, topLevelFunctionSymbols);
        }
        Iterator<FirNamedFunctionSymbol> it = topLevelFunctionSymbols.iterator();
        while (it.hasNext()) {
            processor.invoke(it.next());
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (this.excludedNames.contains(name2)) {
            return;
        }
        Map<Name, List<FirPropertySymbol>> map = this.propertyCache;
        List<FirPropertySymbol> topLevelPropertySymbols = map.get(name2);
        if (topLevelPropertySymbols == null) {
            topLevelPropertySymbols = this.symbolProvider.getTopLevelPropertySymbols(this.fqName, name2);
            map.put(name2, topLevelPropertySymbols);
        }
        Iterator<FirPropertySymbol> it = topLevelPropertySymbols.iterator();
        while (it.hasNext()) {
            processor.invoke(it.next());
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return this.scopeOwnerLookupNames;
    }
}
