package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.LocalClassesNavigationInfo;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.ScopesKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirDeclaredMemberScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.impl.FirMemberTypeParameterScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeWithSubstitutionKt;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.FirErrorTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: FirSupertypesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000r\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a6\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u001a+\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0016\"\u0004\b\u0000\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00170\u00162\u0006\u0010\u0018\u001a\u0002H\u0017H\u0002¢\u0006\u0002\u0010\u0019\u001a,\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00170\u0016\"\u0004\b\u0000\u0010\u0017*\b\u0012\u0004\u0012\u0002H\u00170\u00162\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H\u00170\tH\u0002\u001a*\u0010\u001c\u001a\f\u0012\u0004\u0012\u00020\n0\u0016j\u0002`\u001d*\f\u0012\u0004\u0012\u00020\n0\u0016j\u0002`\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\nH\u0002\u001aU\u0010\u001f\u001a\u0002H \"\b\b\u0000\u0010 *\u00020!*\u0002H 2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\f\u0010(\u001a\b\u0012\u0004\u0012\u00020)0#¢\u0006\u0002\u0010*\u001a\u000e\u0010+\u001a\u0004\u0018\u00010\n*\u00020!H\u0002*\u0018\b\u0002\u0010,\"\b\u0012\u0004\u0012\u00020\n0\u00162\b\u0012\u0004\u0012\u00020\n0\u0016¨\u0006-"}, d2 = {"createErrorTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirErrorTypeRef;", "fir", "Lorg/jetbrains/kotlin/fir/FirElement;", CrashHianalyticsData.MESSAGE, "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/diagnostics/DiagnosticKind;", "createOtherScopesForNestedClassesOrCompanion", "", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "supertypeComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;", "withCompanionScopes", "", "push", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", ExifInterface.LONGITUDE_EAST, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lkotlinx/collections/immutable/PersistentList;Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentList;", "pushAll", "collection", "pushIfNotNull", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopePersistentList;", "scope", "runSupertypeResolvePhaseForLocalClass", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "currentScopeList", "", "localClassesNavigationInfo", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingDeclarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Ljava/util/List;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/util/List;)Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "typeParametersScope", "ScopePersistentList", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSupertypesResolutionKt {
    public static final <F extends FirClassLikeDeclaration> F runSupertypeResolvePhaseForLocalClass(F f, FirSession session, ScopeSession scopeSession, List<? extends FirScope> currentScopeList, LocalClassesNavigationInfo localClassesNavigationInfo, FirFile useSiteFile, List<? extends FirDeclaration> containingDeclarations) {
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(currentScopeList, "currentScopeList");
        Intrinsics.checkNotNullParameter(localClassesNavigationInfo, "localClassesNavigationInfo");
        Intrinsics.checkNotNullParameter(useSiteFile, "useSiteFile");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        SupertypeComputationSessionForLocalClasses supertypeComputationSessionForLocalClasses = new SupertypeComputationSessionForLocalClasses();
        SupertypeComputationSessionForLocalClasses supertypeComputationSessionForLocalClasses2 = supertypeComputationSessionForLocalClasses;
        f.accept(new FirSupertypeResolverVisitor(session, supertypeComputationSessionForLocalClasses2, scopeSession, ExtensionsKt.toPersistentList(currentScopeList), localClassesNavigationInfo, useSiteFile, containingDeclarations), null);
        supertypeComputationSessionForLocalClasses.breakLoops(session);
        return (F) f.transform(new FirApplySupertypesTransformer(supertypeComputationSessionForLocalClasses2, session, scopeSession), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirScope typeParametersScope(FirClassLikeDeclaration firClassLikeDeclaration) {
        if (firClassLikeDeclaration.getTypeParameters().isEmpty()) {
            return null;
        }
        return new FirMemberTypeParameterScope(firClassLikeDeclaration);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Collection<FirScope> createOtherScopesForNestedClassesOrCompanion(FirClass firClass, FirSession firSession, ScopeSession scopeSession, SupertypeComputationSession supertypeComputationSession, boolean z) {
        ArrayList arrayList = new ArrayList();
        CollectionsKt.addIfNotNull(arrayList, FirDeclaredMemberScopeProviderKt.nestedClassifierScope(firSession, firClass));
        if (z) {
            List<FirDeclaration> declarations = firClass.getDeclarations();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof FirRegularClass) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            for (Object obj2 : arrayList2) {
                if (((FirRegularClass) obj2).getStatus().isCompanion()) {
                    arrayList3.add(obj2);
                }
            }
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                CollectionsKt.addIfNotNull(arrayList, FirDeclaredMemberScopeProviderKt.nestedClassifierScope(firSession, (FirRegularClass) it.next()));
            }
        }
        for (ConeClassLikeType coneClassLikeType : SupertypeUtilsKt.lookupSuperTypes(firClass, false, true, firSession, true, supertypeComputationSession.getSupertypesSupplier())) {
            FirContainingNamesAwareScope nestedClassifierScope = ScopesKt.getNestedClassifierScope(coneClassLikeType.getLookupTag(), firSession, scopeSession);
            FirContainingNamesAwareScope firContainingNamesAwareScopeWrapNestedClassifierScopeWithSubstitutionForSuperType = nestedClassifierScope != null ? FirNestedClassifierScopeWithSubstitutionKt.wrapNestedClassifierScopeWithSubstitutionForSuperType(nestedClassifierScope, coneClassLikeType, firSession) : null;
            if (firContainingNamesAwareScopeWrapNestedClassifierScopeWithSubstitutionForSuperType != null) {
                arrayList.add(firContainingNamesAwareScopeWrapNestedClassifierScopeWithSubstitutionForSuperType);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <E> PersistentList<E> push(PersistentList<? extends E> persistentList, E e) {
        return persistentList.add(0, (Object) e);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <E> PersistentList<E> pushAll(PersistentList<? extends E> persistentList, Collection<? extends E> collection) {
        return persistentList.addAll(0, (Collection<? extends Object>) collection);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final PersistentList<FirScope> pushIfNotNull(PersistentList<? extends FirScope> persistentList, FirScope firScope) {
        return firScope == null ? persistentList : push(persistentList, firScope);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FirErrorTypeRef createErrorTypeRef(FirElement firElement, String str, DiagnosticKind diagnosticKind) {
        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
        firErrorTypeRefBuilder.setSource(firElement.getSource());
        firErrorTypeRefBuilder.setDiagnostic(new ConeSimpleDiagnostic(str, diagnosticKind));
        return firErrorTypeRefBuilder.mo6809build();
    }
}
