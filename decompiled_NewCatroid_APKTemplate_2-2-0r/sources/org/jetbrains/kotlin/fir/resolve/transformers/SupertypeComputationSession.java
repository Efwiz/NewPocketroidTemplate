package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: FirSupertypesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JP\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u001f2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\f0\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u001f2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0004J\u000e\u0010#\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001dJ0\u0010$\u001a\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b2\u0006\u0010%\u001a\u00020\u00052\u0016\u0010&\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0'J0\u0010(\u001a\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b2\u0006\u0010)\u001a\u00020\u000e2\u0016\u0010&\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0'J0\u0010*\u001a\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b2\u0006\u0010)\u001a\u00020\u000e2\u0016\u0010&\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0'J0\u0010+\u001a\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b2\u0006\u0010)\u001a\u00020\u000e2\u0016\u0010&\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0'J\u0018\u0010,\u001a\n\u0012\u0004\u0012\u00020.\u0018\u00010-2\u0006\u0010/\u001a\u00020\fH\u0014J\u000e\u00100\u001a\u00020\u00132\u0006\u0010/\u001a\u00020\fJ\u0010\u00101\u001a\u0002022\u0006\u0010/\u001a\u00020\fH\u0014J\u001e\u00103\u001a\u00020\u001a2\u0006\u0010/\u001a\u00020\f2\f\u00104\u001a\b\u0012\u0004\u0012\u00020.0-H\u0014J\u000e\u00105\u001a\u00020\u001a2\u0006\u0010/\u001a\u00020\fJ\u001c\u00106\u001a\u00020\u001a2\u0006\u0010/\u001a\u00020\f2\f\u00107\u001a\b\u0012\u0004\u0012\u00020.0-J\u001b\u00104\u001a\b\u0012\u0004\u0012\u0002080-2\u0006\u0010\u001b\u001a\u00020\fH\u0010¢\u0006\u0002\b9R>\u0010\u0003\u001a2\u0012\u0004\u0012\u00020\u0005\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0\u0004j\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\r\u001a2\u0012\u0004\u0012\u00020\u000e\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0\u0004j\u0018\u0012\u0004\u0012\u00020\u000e\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\u000f\u001a2\u0012\u0004\u0012\u00020\u000e\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0\u0004j\u0018\u0012\u0004\u0012\u00020\u000e\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R>\u0010\u0010\u001a2\u0012\u0004\u0012\u00020\u000e\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b0\u0004j\u0018\u0012\u0004\u0012\u00020\u000e\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00070\u0006j\u0002`\b`\tX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0011\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00130\u0012j\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u0013`\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006:"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;", "", "()V", "fileScopesMap", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopePersistentList;", "Lkotlin/collections/HashMap;", "newClassifiersForBreakingLoops", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "scopesForCompanionMap", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "scopesForNestedClassesMap", "scopesForStaticNestedClassesMap", "supertypeStatusMap", "Ljava/util/LinkedHashMap;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationStatus;", "Lkotlin/collections/LinkedHashMap;", "supertypesSupplier", "Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "getSupertypesSupplier", "()Lorg/jetbrains/kotlin/fir/resolve/SupertypeSupplier;", "breakLoopFor", "", "declaration", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "visited", "", "looped", "pathSet", "path", "breakLoops", "getOrPutFileScope", "file", "scope", "Lkotlin/Function0;", "getOrPutScopeForCompanion", "klass", "getOrPutScopeForNestedClasses", "getOrPutScopeForStaticNestedClasses", "getResolvedSuperTypeRefsForOutOfSessionDeclaration", "", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "classLikeDeclaration", "getSupertypesComputationStatus", "isAlreadyResolved", "", "reportLoopErrorRefs", "supertypeRefs", "startComputingSupertypes", "storeSupertypes", "resolvedTypesRefs", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "supertypeRefs$resolve", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SupertypeComputationSession {
    private final HashMap<FirFile, PersistentList<FirScope>> fileScopesMap = new HashMap<>();
    private final HashMap<FirClass, PersistentList<FirScope>> scopesForNestedClassesMap = new HashMap<>();
    private final HashMap<FirClass, PersistentList<FirScope>> scopesForStaticNestedClassesMap = new HashMap<>();
    private final HashMap<FirClass, PersistentList<FirScope>> scopesForCompanionMap = new HashMap<>();
    private final LinkedHashMap<FirClassLikeDeclaration, SupertypeComputationStatus> supertypeStatusMap = new LinkedHashMap<>();
    private final SupertypeSupplier supertypesSupplier = new SupertypeSupplier() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.SupertypeComputationSession$supertypesSupplier$1
        @Override // org.jetbrains.kotlin.fir.resolve.SupertypeSupplier
        public List<ConeClassLikeType> forClass(FirClass firClass, FirSession useSiteSession) {
            Intrinsics.checkNotNullParameter(firClass, "firClass");
            Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
            FirClass firClass2 = firClass;
            SupertypeComputationStatus supertypesComputationStatus = this.this$0.getSupertypesComputationStatus(firClass2);
            ArrayList arrayList = null;
            SupertypeComputationStatus.Computed computed = supertypesComputationStatus instanceof SupertypeComputationStatus.Computed ? (SupertypeComputationStatus.Computed) supertypesComputationStatus : null;
            List<FirResolvedTypeRef> supertypeRefs = computed != null ? computed.getSupertypeRefs() : null;
            if (supertypeRefs == null) {
                supertypeRefs = this.this$0.getResolvedSuperTypeRefsForOutOfSessionDeclaration(firClass2);
            }
            if (supertypeRefs != null) {
                ArrayList arrayList2 = new ArrayList();
                for (FirResolvedTypeRef firResolvedTypeRef : supertypeRefs) {
                    FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRef instanceof FirResolvedTypeRef ? firResolvedTypeRef : null;
                    ConeKotlinType type = firResolvedTypeRef2 != null ? firResolvedTypeRef2.getType() : null;
                    if (!(type instanceof ConeClassLikeType)) {
                        type = null;
                    }
                    ConeClassLikeType coneClassLikeType = (ConeClassLikeType) type;
                    if (coneClassLikeType != null) {
                        arrayList2.add(coneClassLikeType);
                    }
                }
                arrayList = arrayList2;
            }
            return arrayList == null ? CollectionsKt.emptyList() : arrayList;
        }

        @Override // org.jetbrains.kotlin.fir.resolve.SupertypeSupplier
        public ConeClassLikeType expansionForTypeAlias(FirTypeAlias typeAlias, FirSession useSiteSession) {
            List<FirResolvedTypeRef> supertypeRefs;
            FirResolvedTypeRef firResolvedTypeRef;
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
            if (typeAlias.getExpandedTypeRef() instanceof FirResolvedTypeRef) {
                return FirDeclarationUtilKt.getExpandedConeType(typeAlias);
            }
            SupertypeComputationStatus supertypesComputationStatus = this.this$0.getSupertypesComputationStatus(typeAlias);
            SupertypeComputationStatus.Computed computed = supertypesComputationStatus instanceof SupertypeComputationStatus.Computed ? (SupertypeComputationStatus.Computed) supertypesComputationStatus : null;
            if (computed == null || (supertypeRefs = computed.getSupertypeRefs()) == null || (firResolvedTypeRef = (FirResolvedTypeRef) CollectionsKt.getOrNull(supertypeRefs, 0)) == null) {
                return null;
            }
            FirResolvedTypeRef firResolvedTypeRef2 = firResolvedTypeRef;
            FirResolvedTypeRef firResolvedTypeRef3 = firResolvedTypeRef2 instanceof FirResolvedTypeRef ? firResolvedTypeRef2 : null;
            ConeKotlinType type = firResolvedTypeRef3 != null ? firResolvedTypeRef3.getType() : null;
            return (ConeClassLikeType) (type instanceof ConeClassLikeType ? type : null);
        }
    };
    private final List<FirClassLikeDeclaration> newClassifiersForBreakingLoops = new ArrayList();

    protected boolean isAlreadyResolved(FirClassLikeDeclaration classLikeDeclaration) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        return false;
    }

    public final SupertypeSupplier getSupertypesSupplier() {
        return this.supertypesSupplier;
    }

    public final SupertypeComputationStatus getSupertypesComputationStatus(FirClassLikeDeclaration classLikeDeclaration) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        SupertypeComputationStatus supertypeComputationStatus = this.supertypeStatusMap.get(classLikeDeclaration);
        return supertypeComputationStatus == null ? SupertypeComputationStatus.NotComputed.INSTANCE : supertypeComputationStatus;
    }

    public final PersistentList<FirScope> getOrPutFileScope(FirFile file, Function0<? extends PersistentList<? extends FirScope>> scope) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(scope, "scope");
        HashMap<FirFile, PersistentList<FirScope>> map = this.fileScopesMap;
        PersistentList<FirScope> persistentList = map.get(file);
        if (persistentList == null) {
            persistentList = (PersistentList) scope.invoke();
            map.put(file, persistentList);
        }
        return persistentList;
    }

    public final PersistentList<FirScope> getOrPutScopeForNestedClasses(FirClass klass, Function0<? extends PersistentList<? extends FirScope>> scope) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(scope, "scope");
        HashMap<FirClass, PersistentList<FirScope>> map = this.scopesForNestedClassesMap;
        PersistentList<FirScope> persistentList = map.get(klass);
        if (persistentList == null) {
            persistentList = (PersistentList) scope.invoke();
            map.put(klass, persistentList);
        }
        return persistentList;
    }

    public final PersistentList<FirScope> getOrPutScopeForStaticNestedClasses(FirClass klass, Function0<? extends PersistentList<? extends FirScope>> scope) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(scope, "scope");
        HashMap<FirClass, PersistentList<FirScope>> map = this.scopesForStaticNestedClassesMap;
        PersistentList<FirScope> persistentList = map.get(klass);
        if (persistentList == null) {
            persistentList = (PersistentList) scope.invoke();
            map.put(klass, persistentList);
        }
        return persistentList;
    }

    public final PersistentList<FirScope> getOrPutScopeForCompanion(FirClass klass, Function0<? extends PersistentList<? extends FirScope>> scope) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(scope, "scope");
        HashMap<FirClass, PersistentList<FirScope>> map = this.scopesForCompanionMap;
        PersistentList<FirScope> persistentList = map.get(klass);
        if (persistentList == null) {
            persistentList = (PersistentList) scope.invoke();
            map.put(klass, persistentList);
        }
        return persistentList;
    }

    public final void startComputingSupertypes(FirClassLikeDeclaration classLikeDeclaration) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        if (this.supertypeStatusMap.get(classLikeDeclaration) != null) {
            throw new IllegalArgumentException(("Unexpected in startComputingSupertypes supertype status for " + classLikeDeclaration + ": " + this.supertypeStatusMap.get(classLikeDeclaration)).toString());
        }
        this.supertypeStatusMap.put(classLikeDeclaration, SupertypeComputationStatus.Computing.INSTANCE);
    }

    public final void storeSupertypes(FirClassLikeDeclaration classLikeDeclaration, List<? extends FirResolvedTypeRef> resolvedTypesRefs) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        Intrinsics.checkNotNullParameter(resolvedTypesRefs, "resolvedTypesRefs");
        if (!(this.supertypeStatusMap.get(classLikeDeclaration) instanceof SupertypeComputationStatus.Computing)) {
            throw new IllegalArgumentException(("Unexpected in storeSupertypes supertype status for " + classLikeDeclaration + ": " + this.supertypeStatusMap.get(classLikeDeclaration)).toString());
        }
        this.supertypeStatusMap.put(classLikeDeclaration, new SupertypeComputationStatus.Computed(resolvedTypesRefs));
        this.newClassifiersForBreakingLoops.add(classLikeDeclaration);
    }

    protected void reportLoopErrorRefs(FirClassLikeDeclaration classLikeDeclaration, List<? extends FirResolvedTypeRef> supertypeRefs) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        Intrinsics.checkNotNullParameter(supertypeRefs, "supertypeRefs");
        this.supertypeStatusMap.put(classLikeDeclaration, new SupertypeComputationStatus.Computed(supertypeRefs));
    }

    protected List<FirResolvedTypeRef> getResolvedSuperTypeRefsForOutOfSessionDeclaration(FirClassLikeDeclaration classLikeDeclaration) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        if (!(classLikeDeclaration instanceof FirClass)) {
            if (!(classLikeDeclaration instanceof FirTypeAlias)) {
                return null;
            }
            FirTypeRef expandedTypeRef = ((FirTypeAlias) classLikeDeclaration).getExpandedTypeRef();
            return CollectionsKt.listOfNotNull(expandedTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) expandedTypeRef : null);
        }
        List<FirTypeRef> superTypeRefs = ((FirClass) classLikeDeclaration).getSuperTypeRefs();
        ArrayList arrayList = new ArrayList();
        for (Object obj : superTypeRefs) {
            if (obj instanceof FirResolvedTypeRef) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public List<FirTypeRef> supertypeRefs$resolve(FirClassLikeDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof FirRegularClass) {
            return ((FirRegularClass) declaration).getSuperTypeRefs();
        }
        return declaration instanceof FirTypeAlias ? CollectionsKt.listOf(((FirTypeAlias) declaration).getExpandedTypeRef()) : CollectionsKt.emptyList();
    }

    protected final void breakLoopFor(FirClassLikeDeclaration declaration, FirSession session, Set<FirClassLikeDeclaration> visited, Set<FirClassLikeDeclaration> looped, Set<FirClassLikeDeclaration> pathSet, List<FirClassLikeDeclaration> path) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(visited, "visited");
        Intrinsics.checkNotNullParameter(looped, "looped");
        Intrinsics.checkNotNullParameter(pathSet, "pathSet");
        Intrinsics.checkNotNullParameter(path, "path");
        if (!path.isEmpty()) {
            throw new IllegalArgumentException("Path should be empty".toString());
        }
        if (!pathSet.isEmpty()) {
            throw new IllegalArgumentException("Path set should be empty".toString());
        }
        breakLoopFor$checkIsInLoop(this, visited, pathSet, looped, path, session, declaration, false, false);
        if (!path.isEmpty()) {
            throw new IllegalArgumentException("Path should be empty".toString());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void breakLoopFor$checkIsInLoop(SupertypeComputationSession supertypeComputationSession, Set<FirClassLikeDeclaration> set, Set<FirClassLikeDeclaration> set2, Set<FirClassLikeDeclaration> set3, List<FirClassLikeDeclaration> list, FirSession firSession, FirClassLikeDeclaration firClassLikeDeclaration, boolean z, boolean z2) {
        List<FirResolvedTypeRef> resolvedSuperTypeRefsForOutOfSessionDeclaration;
        FirClassLikeSymbol<? extends FirClassLikeDeclaration> symbol;
        ArrayList list2;
        if (firClassLikeDeclaration == null || supertypeComputationSession.isAlreadyResolved(firClassLikeDeclaration)) {
            return;
        }
        if (z && z2) {
            throw new IllegalArgumentException("This must hold by induction, because otherwise such a loop is allowed".toString());
        }
        SupertypeComputationStatus supertypeComputationStatus = supertypeComputationSession.supertypeStatusMap.get(firClassLikeDeclaration);
        if (supertypeComputationStatus != null) {
            if (!(supertypeComputationStatus instanceof SupertypeComputationStatus.Computed)) {
                throw new IllegalArgumentException(("Expected computed supertypes in breakLoops for " + firClassLikeDeclaration.getSymbol().getClassId()).toString());
            }
            resolvedSuperTypeRefsForOutOfSessionDeclaration = ((SupertypeComputationStatus.Computed) supertypeComputationStatus).getSupertypeRefs();
        } else {
            resolvedSuperTypeRefsForOutOfSessionDeclaration = supertypeComputationSession.getResolvedSuperTypeRefsForOutOfSessionDeclaration(firClassLikeDeclaration);
            if (resolvedSuperTypeRefsForOutOfSessionDeclaration == null) {
                return;
            }
        }
        List<FirResolvedTypeRef> list3 = resolvedSuperTypeRefsForOutOfSessionDeclaration;
        if (set.contains(firClassLikeDeclaration)) {
            if (set2.contains(firClassLikeDeclaration)) {
                set3.add(firClassLikeDeclaration);
                if (list.isEmpty()) {
                    list2 = CollectionsKt.emptyList();
                } else {
                    ListIterator<FirClassLikeDeclaration> listIterator = list.listIterator(list.size());
                    while (listIterator.hasPrevious()) {
                        if (Intrinsics.areEqual(listIterator.previous(), firClassLikeDeclaration)) {
                            listIterator.next();
                            int size = list.size() - listIterator.nextIndex();
                            if (size == 0) {
                                list2 = CollectionsKt.emptyList();
                            } else {
                                ArrayList arrayList = new ArrayList(size);
                                while (listIterator.hasNext()) {
                                    arrayList.add(listIterator.next());
                                }
                                list2 = arrayList;
                            }
                        }
                    }
                    list2 = CollectionsKt.toList(list);
                }
                set3.addAll(list2);
                return;
            }
            return;
        }
        list.add(firClassLikeDeclaration);
        set2.add(firClassLikeDeclaration);
        set.add(firClassLikeDeclaration);
        FqName fqNameParent = firClassLikeDeclaration.getSymbol().getClassId().getRelativeClassName().parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        if (!fqNameParent.isRoot()) {
            FirSymbolProvider symbolProvider = FirSymbolProviderKt.getSymbolProvider(firSession);
            ClassId classIdFromString = ClassId.fromString(fqNameParent.asString());
            Intrinsics.checkNotNullExpressionValue(classIdFromString, "fromString(...)");
            FirClassLikeSymbol<?> classLikeSymbolByClassId = symbolProvider.getClassLikeSymbolByClassId(classIdFromString);
            if (classLikeSymbolByClassId instanceof FirRegularClassSymbol) {
                breakLoopFor$checkIsInLoop(supertypeComputationSession, set, set2, set3, list, firSession, (FirClassLikeDeclaration) ((FirRegularClassSymbol) classLikeSymbolByClassId).getFir(), z, z2);
            }
        }
        boolean z3 = firClassLikeDeclaration instanceof FirTypeAlias;
        if (z2 && !z3) {
            list.remove(list.size() - 1);
            set2.remove(firClassLikeDeclaration);
            return;
        }
        boolean z4 = z || !z3;
        ArrayList arrayList2 = new ArrayList();
        boolean z5 = false;
        for (FirErrorTypeRef firErrorTypeRefCreateErrorTypeRef : list3) {
            FirClassLikeDeclaration firClassLikeDeclarationFirClassLike = DeclarationUtilsKt.firClassLike(firErrorTypeRefCreateErrorTypeRef, firSession);
            ArrayList arrayList3 = arrayList2;
            boolean z6 = z3;
            breakLoopFor$checkIsInLoop(supertypeComputationSession, set, set2, set3, list, firSession, firClassLikeDeclarationFirClassLike, z4, z2);
            if (!z4) {
                breakLoopFor$checkIsInLoop$checkTypeArgumentsRecursively(firSession, z, true, supertypeComputationSession, set, set2, set3, list, firErrorTypeRefCreateErrorTypeRef.getType(), new LinkedHashSet());
            }
            if (set3.contains(firClassLikeDeclaration)) {
                firErrorTypeRefCreateErrorTypeRef = FirSupertypesResolutionKt.createErrorTypeRef(firErrorTypeRefCreateErrorTypeRef, "Loop in supertype: " + firClassLikeDeclaration.getSymbol().getClassId() + " -> " + ((firClassLikeDeclarationFirClassLike == null || (symbol = firClassLikeDeclarationFirClassLike.getSymbol()) == null) ? null : symbol.getClassId()), z6 ? DiagnosticKind.RecursiveTypealiasExpansion : DiagnosticKind.LoopInSupertype);
                z5 = true;
            }
            arrayList3.add(firErrorTypeRefCreateErrorTypeRef);
            arrayList2 = arrayList3;
            z3 = z6;
        }
        ArrayList arrayList4 = arrayList2;
        if (z5) {
            supertypeComputationSession.reportLoopErrorRefs(firClassLikeDeclaration, arrayList4);
        }
        list.remove(list.size() - 1);
        set2.remove(firClassLikeDeclaration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final void breakLoopFor$checkIsInLoop$checkTypeArgumentsRecursively(FirSession firSession, boolean z, boolean z2, SupertypeComputationSession supertypeComputationSession, Set<FirClassLikeDeclaration> set, Set<FirClassLikeDeclaration> set2, Set<FirClassLikeDeclaration> set3, List<FirClassLikeDeclaration> list, ConeKotlinType coneKotlinType, Set<ConeKotlinType> set4) {
        if (set4.contains(coneKotlinType)) {
            return;
        }
        set4.add(coneKotlinType);
        for (ConeTypeProjection coneTypeProjection : coneKotlinType.getTypeArguments()) {
            if (coneTypeProjection instanceof ConeClassLikeType) {
                FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) coneTypeProjection).getLookupTag(), firSession);
                breakLoopFor$checkIsInLoop(supertypeComputationSession, set, set2, set3, list, firSession, symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null, z, z2);
                breakLoopFor$checkIsInLoop$checkTypeArgumentsRecursively(firSession, z, z2, supertypeComputationSession, set, set2, set3, list, (ConeKotlinType) coneTypeProjection, set4);
            }
        }
    }

    public final void breakLoops(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        ArrayList arrayList = new ArrayList();
        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        Iterator<FirClassLikeDeclaration> it = this.newClassifiersForBreakingLoops.iterator();
        while (it.hasNext()) {
            breakLoopFor(it.next(), session, linkedHashSet, linkedHashSet2, linkedHashSet3, arrayList);
        }
        this.newClassifiersForBreakingLoops.clear();
    }
}
