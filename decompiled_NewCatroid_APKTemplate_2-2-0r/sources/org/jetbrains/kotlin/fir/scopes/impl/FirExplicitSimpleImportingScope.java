package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirResolvedImport;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirExplicitSimpleImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR!\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00038VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR+\u0010\u0010\u001a\u0019\u0012\u0004\u0012\u00020\u0012\u0012\u000f\u0012\r\u0012\t\u0012\u00070\u0013¢\u0006\u0002\b\u00140\u00030\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirExplicitSimpleImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractSimpleImportingScope;", "imports", "", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "scopeOwnerLookupNames", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "scopeOwnerLookupNames$delegate", "Lkotlin/Lazy;", "simpleImports", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "Lkotlin/internal/NoInfer;", "getSimpleImports", "()Ljava/util/Map;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirExplicitSimpleImportingScope extends FirAbstractSimpleImportingScope {

    /* JADX INFO: renamed from: scopeOwnerLookupNames$delegate, reason: from kotlin metadata */
    private final Lazy scopeOwnerLookupNames;
    private final Map<Name, List<FirResolvedImport>> simpleImports;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirExplicitSimpleImportingScope(List<? extends FirImport> imports, FirSession session, ScopeSession scopeSession) {
        super(session, scopeSession);
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        ArrayList arrayList = new ArrayList();
        for (Object obj : imports) {
            if (obj instanceof FirResolvedImport) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            FirResolvedImport firResolvedImport = (FirResolvedImport) obj2;
            if (!firResolvedImport.getIsAllUnder() && firResolvedImport.getImportedName() != null) {
                arrayList2.add(obj2);
            }
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj3 : arrayList2) {
            FirResolvedImport firResolvedImport2 = (FirResolvedImport) obj3;
            Name aliasName = firResolvedImport2.getAliasName();
            if (aliasName == null) {
                aliasName = firResolvedImport2.getImportedName();
                Intrinsics.checkNotNull(aliasName);
            }
            Object obj4 = linkedHashMap.get(aliasName);
            if (obj4 == null) {
                obj4 = (List) new ArrayList();
                linkedHashMap.put(aliasName, obj4);
            }
            ((List) obj4).add(obj3);
        }
        this.simpleImports = linkedHashMap;
        this.scopeOwnerLookupNames = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirExplicitSimpleImportingScope$scopeOwnerLookupNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends String> invoke() {
                Collection<List<FirResolvedImport>> collectionValues = this.this$0.getSimpleImports().values();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<T> it = collectionValues.iterator();
                while (it.hasNext()) {
                    List list = (List) it.next();
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        arrayList3.add(((FirResolvedImport) it2.next()).getPackageFqName().asString());
                    }
                    CollectionsKt.addAll(linkedHashSet, arrayList3);
                }
                return CollectionsKt.toList(linkedHashSet);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractSimpleImportingScope
    public Map<Name, List<FirResolvedImport>> getSimpleImports() {
        return this.simpleImports;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return (List) this.scopeOwnerLookupNames.getValue();
    }
}
