package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.declarations.builder.FirImportBuilder;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.FirImportResolveTransformer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.ImportPath;

/* JADX INFO: compiled from: FirDefaultSimpleImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0016\u0010\u0010\u001a\u0004\u0018\u00010\r*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R&\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirDefaultSimpleImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractSimpleImportingScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "priority", "Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultImportPriority;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultImportPriority;)V", "simpleImports", "", "Lorg/jetbrains/kotlin/name/Name;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "getSimpleImports", "()Ljava/util/Map;", "resolve", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "importResolveTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirImportResolveTransformer;"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultSimpleImportingScope extends FirAbstractSimpleImportingScope {
    private final Map<Name, List<FirResolvedImport>> simpleImports;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Code duplicated, block: B:23:0x00ce  */
    public FirDefaultSimpleImportingScope(FirSession session, ScopeSession scopeSession, DefaultImportPriority priority) {
        Map mapEmptyMap;
        super(session, scopeSession);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(priority, "priority");
        FirImportResolveTransformer firImportResolveTransformer = new FirImportResolveTransformer(session);
        List<ImportPath> allDefaultImports = priority.getAllDefaultImports(FirModuleDataKt.getModuleData(session).getAnalyzerServices(), LanguageVersionSettingsImpl.DEFAULT);
        if (allDefaultImports == null) {
            mapEmptyMap = MapsKt.emptyMap();
        } else {
            ArrayList arrayList = new ArrayList();
            for (Object obj : allDefaultImports) {
                if (!((ImportPath) obj).isAllUnder()) {
                    arrayList.add(obj);
                }
            }
            ArrayList<ImportPath> arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            for (ImportPath importPath : arrayList2) {
                FirImportBuilder firImportBuilder = new FirImportBuilder();
                firImportBuilder.setImportedFqName(importPath.getFqName());
                firImportBuilder.setAllUnder(false);
                arrayList3.add(resolve(firImportBuilder.build(), firImportResolveTransformer));
            }
            List listFilterNotNull = CollectionsKt.filterNotNull(arrayList3);
            if (listFilterNotNull == null) {
                mapEmptyMap = MapsKt.emptyMap();
            } else {
                mapEmptyMap = new LinkedHashMap();
                for (Object obj2 : listFilterNotNull) {
                    Name importedName = ((FirResolvedImport) obj2).getImportedName();
                    Intrinsics.checkNotNull(importedName);
                    Object obj3 = mapEmptyMap.get(importedName);
                    if (obj3 == null) {
                        obj3 = (List) new ArrayList();
                        mapEmptyMap.put(importedName, obj3);
                    }
                    ((List) obj3).add(obj2);
                }
            }
        }
        this.simpleImports = mapEmptyMap;
    }

    private final FirResolvedImport resolve(FirImport firImport, FirImportResolveTransformer firImportResolveTransformer) {
        FirImport firImportTransformImport = firImportResolveTransformer.transformImport(firImport, null);
        if (firImportTransformImport instanceof FirResolvedImport) {
            return (FirResolvedImport) firImportTransformImport;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractSimpleImportingScope
    public Map<Name, List<FirResolvedImport>> getSimpleImports() {
        return this.simpleImports;
    }
}
