package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.fir.FirLanguageSettingsComponentKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.declarations.builder.FirImportBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirResolvedImportBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.ImportPath;

/* JADX INFO: compiled from: FirSingleLevelDefaultStarImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B+\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fJ$\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00130\u0017H\u0016J(\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0016\u0010\u0016\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u001a\u0012\u0004\u0012\u00020\u00130\u0017H\u0016R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirSingleLevelDefaultStarImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractStarImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultStarImportingScopeMarker;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "priority", "Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultImportPriority;", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultImportPriority;Ljava/util/Set;)V", "starImports", "", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "getStarImports", "()Ljava/util/List;", "processFunctionsByName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "processor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSingleLevelDefaultStarImportingScope extends FirAbstractStarImportingScope implements DefaultStarImportingScopeMarker {
    private final List<FirResolvedImport> starImports;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSingleLevelDefaultStarImportingScope(FirSession session, ScopeSession scopeSession, DefaultImportPriority priority, Set<FqName> excludedImportNames) {
        ArrayList arrayListEmptyList;
        super(session, scopeSession, ((Boolean) FirLanguageSettingsComponentKt.getLanguageVersionSettings(session).getFlag(AnalysisFlags.getAllowKotlinPackage())).booleanValue(), excludedImportNames);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(priority, "priority");
        Intrinsics.checkNotNullParameter(excludedImportNames, "excludedImportNames");
        List<ImportPath> allDefaultImports = priority.getAllDefaultImports(FirModuleDataKt.getModuleData(session).getAnalyzerServices(), LanguageVersionSettingsImpl.DEFAULT);
        if (allDefaultImports == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else {
            ArrayList arrayList = new ArrayList();
            for (Object obj : allDefaultImports) {
                if (((ImportPath) obj).isAllUnder()) {
                    arrayList.add(obj);
                }
            }
            ArrayList<ImportPath> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (ImportPath importPath : arrayList2) {
                FirResolvedImportBuilder firResolvedImportBuilder = new FirResolvedImportBuilder();
                FirImportBuilder firImportBuilder = new FirImportBuilder();
                firImportBuilder.setImportedFqName(importPath.getFqName());
                firImportBuilder.setAllUnder(true);
                firResolvedImportBuilder.setDelegate(firImportBuilder.build());
                firResolvedImportBuilder.setPackageFqName(importPath.getFqName());
                arrayList3.add(firResolvedImportBuilder.build());
            }
            arrayListEmptyList = arrayList3;
        }
        this.starImports = arrayListEmptyList;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractStarImportingScope
    public List<FirResolvedImport> getStarImports() {
        return this.starImports;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractStarImportingScope, org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (!name2.isSpecial()) {
            String identifier = name2.getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            if (identifier.length() <= 0) {
                return;
            }
        }
        Iterator<FirResolvedImport> it = getStarImports().iterator();
        while (it.hasNext()) {
            Iterator<FirNamedFunctionSymbol> it2 = getProvider().getTopLevelFunctionSymbols(it.next().getPackageFqName(), name2).iterator();
            while (it2.hasNext()) {
                processor.invoke(it2.next());
            }
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractStarImportingScope, org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (!name2.isSpecial()) {
            String identifier = name2.getIdentifier();
            Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
            if (identifier.length() <= 0) {
                return;
            }
        }
        Iterator<FirResolvedImport> it = getStarImports().iterator();
        while (it.hasNext()) {
            Iterator<FirPropertySymbol> it2 = getProvider().getTopLevelPropertySymbols(it.next().getPackageFqName(), name2).iterator();
            while (it2.hasNext()) {
                processor.invoke(it2.next());
            }
        }
    }
}
