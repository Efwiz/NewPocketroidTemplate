package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirExplicitStarImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B1\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\u0002\u0010\fR!\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00038VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001f\u0010\u0013\u001a\r\u0012\t\u0012\u00070\u0014¢\u0006\u0002\b\u00150\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirExplicitStarImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirAbstractStarImportingScope;", "imports", "", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Ljava/util/Set;)V", "scopeOwnerLookupNames", "", "getScopeOwnerLookupNames", "()Ljava/util/List;", "scopeOwnerLookupNames$delegate", "Lkotlin/Lazy;", "starImports", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedImport;", "Lkotlin/internal/NoInfer;", "getStarImports", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirExplicitStarImportingScope extends FirAbstractStarImportingScope {

    /* JADX INFO: renamed from: scopeOwnerLookupNames$delegate, reason: from kotlin metadata */
    private final Lazy scopeOwnerLookupNames;
    private final List<FirResolvedImport> starImports;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirExplicitStarImportingScope(List<? extends FirImport> imports, FirSession session, ScopeSession scopeSession, Set<FqName> excludedImportNames) {
        super(session, scopeSession, true, excludedImportNames);
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(excludedImportNames, "excludedImportNames");
        ArrayList arrayList = new ArrayList();
        for (Object obj : imports) {
            if (obj instanceof FirResolvedImport) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (((FirResolvedImport) obj2).getIsAllUnder()) {
                arrayList2.add(obj2);
            }
        }
        this.starImports = arrayList2;
        this.scopeOwnerLookupNames = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends String>>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirExplicitStarImportingScope$scopeOwnerLookupNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends String> invoke() {
                List<FirResolvedImport> starImports = this.this$0.getStarImports();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                Iterator<T> it = starImports.iterator();
                while (it.hasNext()) {
                    linkedHashSet.add(((FirResolvedImport) it.next()).getPackageFqName().asString());
                }
                return CollectionsKt.toList(linkedHashSet);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.impl.FirAbstractStarImportingScope
    public List<FirResolvedImport> getStarImports() {
        return this.starImports;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public List<String> getScopeOwnerLookupNames() {
        return (List) this.scopeOwnerLookupNames.getValue();
    }
}
