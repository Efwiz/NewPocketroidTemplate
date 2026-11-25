package org.jetbrains.kotlin.fir.declarations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.SessionHolder;
import org.jetbrains.kotlin.fir.resolve.SupertypeSupplier;
import org.jetbrains.kotlin.fir.resolve.SupertypeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValueForCallable;
import org.jetbrains.kotlin.fir.resolve.calls.ContextReceiverValueForClass;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitDispatchReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitExtensionReceiverValue;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValue;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScopeWithSubstitutionKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: ImplicitReceiverUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u0001*\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002j\u0002`\u0004\u001a\u000e\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0005\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u001a\u0014\u0010\t\u001a\u00020\u0001*\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u001a\u001c\u0010\f\u001a\u00020\r*\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0010\u001a\u0018\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u0002*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u000e\u001a\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 \u001a\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u000e*\u001e\u0010!\"\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00022\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002*\u0016\u0010\"\"\b\u0012\u0004\u0012\u00020$0#2\b\u0012\u0004\u0012\u00020$0#¨\u0006%"}, d2 = {"asTowerDataElement", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataElement;", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValue;", "Lorg/jetbrains/kotlin/fir/declarations/ContextReceiverGroup;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValue;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "isLocal", "", "asTowerDataElementForStaticScope", "staticScopeOwnerSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "collectImplicitReceivers", "Lorg/jetbrains/kotlin/fir/declarations/ImplicitReceivers;", "Lorg/jetbrains/kotlin/fir/resolve/SessionHolder;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "collectTowerDataElementsForClass", "Lorg/jetbrains/kotlin/fir/declarations/TowerElementsForClass;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "defaultType", "createContextReceiverValues", "Lorg/jetbrains/kotlin/fir/resolve/calls/ContextReceiverValueForCallable;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "sessionHolder", "staticScope", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "ContextReceiverGroup", "FirLocalScopes", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirLocalScope;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImplicitReceiverUtilsKt {
    public static final ImplicitReceivers collectImplicitReceivers(SessionHolder sessionHolder, ConeKotlinType coneKotlinType, FirDeclaration owner) {
        ImplicitExtensionReceiverValue thisReceiver;
        Intrinsics.checkNotNullParameter(sessionHolder, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (owner instanceof FirClass) {
            Intrinsics.checkNotNull(coneKotlinType);
            TowerElementsForClass towerElementsForClassCollectTowerDataElementsForClass = collectTowerDataElementsForClass(sessionHolder, (FirClass) owner, coneKotlinType);
            arrayList.addAll(towerElementsForClassCollectTowerDataElementsForClass.getImplicitCompanionValues());
            arrayList2.addAll(towerElementsForClassCollectTowerDataElementsForClass.getContextReceivers());
            thisReceiver = towerElementsForClassCollectTowerDataElementsForClass.getThisReceiver();
        } else {
            ImplicitExtensionReceiverValue implicitExtensionReceiverValue = null;
            if (owner instanceof FirFunction) {
                arrayList2.addAll(createContextReceiverValues((FirCallableDeclaration) owner, sessionHolder));
                if (coneKotlinType != null) {
                    implicitExtensionReceiverValue = new ImplicitExtensionReceiverValue(((FirFunction) owner).getSymbol(), coneKotlinType, sessionHolder.getSession(), sessionHolder.getScopeSession(), false, 16, null);
                }
                thisReceiver = implicitExtensionReceiverValue;
            } else if (owner instanceof FirVariable) {
                arrayList2.addAll(createContextReceiverValues((FirCallableDeclaration) owner, sessionHolder));
                if (coneKotlinType != null) {
                    implicitExtensionReceiverValue = new ImplicitExtensionReceiverValue(((FirVariable) owner).getSymbol(), coneKotlinType, sessionHolder.getSession(), sessionHolder.getScopeSession(), false, 16, null);
                }
                thisReceiver = implicitExtensionReceiverValue;
            } else {
                if (coneKotlinType != null) {
                    throw new IllegalArgumentException("Incorrect label & receiver owner: " + owner.getClass());
                }
                thisReceiver = null;
            }
        }
        return new ImplicitReceivers(thisReceiver, arrayList, arrayList2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final TowerElementsForClass collectTowerDataElementsForClass(SessionHolder sessionHolder, FirClass owner, ConeKotlinType defaultType) {
        ArrayList arrayListEmptyList;
        List<FirContextReceiver> contextReceivers;
        FirRegularClassSymbol companionObjectSymbol;
        FirContainingNamesAwareScope firContainingNamesAwareScopeWrapNestedClassifierScopeWithSubstitutionForSuperType;
        FirTowerDataElement firTowerDataElementAsTowerDataElementForStaticScope;
        FirRegularClassSymbol companionObjectSymbol2;
        Intrinsics.checkNotNullParameter(sessionHolder, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(defaultType, "defaultType");
        ArrayList arrayList = new ArrayList();
        boolean z = owner instanceof FirRegularClass;
        FirRegularClass firRegularClass = z ? (FirRegularClass) owner : null;
        FirRegularClass firRegularClass2 = (firRegularClass == null || (companionObjectSymbol2 = firRegularClass.getCompanionObjectSymbol()) == null) ? null : (FirRegularClass) companionObjectSymbol2.getFir();
        ImplicitDispatchReceiverValue implicitDispatchReceiverValue = firRegularClass2 != null ? new ImplicitDispatchReceiverValue(firRegularClass2.getSymbol(), sessionHolder.getSession(), sessionHolder.getScopeSession()) : null;
        ArrayList arrayList2 = arrayList;
        CollectionsKt.addIfNotNull(arrayList2, implicitDispatchReceiverValue);
        ArrayList arrayList3 = new ArrayList();
        Iterator it = SupertypeUtilsKt.lookupSuperTypes$default(owner, false, true, sessionHolder.getSession(), true, (SupertypeSupplier) null, 32, (Object) null).iterator();
        while (it.hasNext()) {
            ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default((ConeClassLikeType) it.next(), sessionHolder.getSession(), null, 2, null);
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeTypeFullyExpandedType$default.getLookupTag(), sessionHolder.getSession());
            FirClassLikeDeclaration firClassLikeDeclaration = symbol != null ? (FirClassLikeDeclaration) symbol.getFir() : null;
            FirRegularClass firRegularClass3 = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
            if (firRegularClass3 != null) {
                FirContainingNamesAwareScope firContainingNamesAwareScopeStaticScope = staticScope(firRegularClass3, sessionHolder);
                if (firContainingNamesAwareScopeStaticScope != null && (firContainingNamesAwareScopeWrapNestedClassifierScopeWithSubstitutionForSuperType = FirNestedClassifierScopeWithSubstitutionKt.wrapNestedClassifierScopeWithSubstitutionForSuperType(firContainingNamesAwareScopeStaticScope, coneClassLikeTypeFullyExpandedType$default, sessionHolder.getSession())) != null && (firTowerDataElementAsTowerDataElementForStaticScope = asTowerDataElementForStaticScope(firContainingNamesAwareScopeWrapNestedClassifierScopeWithSubstitutionForSuperType, firRegularClass3.getSymbol())) != null) {
                    arrayList3.add(firTowerDataElementAsTowerDataElementForStaticScope);
                }
                if (!(firRegularClass3 instanceof FirRegularClass)) {
                    firRegularClass3 = null;
                }
                if (firRegularClass3 != null && (companionObjectSymbol = firRegularClass3.getCompanionObjectSymbol()) != null) {
                    ImplicitDispatchReceiverValue implicitDispatchReceiverValue2 = new ImplicitDispatchReceiverValue(companionObjectSymbol, sessionHolder.getSession(), sessionHolder.getScopeSession());
                    arrayList3.add(asTowerDataElement(implicitDispatchReceiverValue2));
                    arrayList2.add(implicitDispatchReceiverValue2);
                }
            }
        }
        ImplicitDispatchReceiverValue implicitDispatchReceiverValue3 = implicitDispatchReceiverValue;
        ImplicitDispatchReceiverValue implicitDispatchReceiverValue4 = new ImplicitDispatchReceiverValue(owner.getSymbol(), defaultType, sessionHolder.getSession(), sessionHolder.getScopeSession(), false, 16, null);
        FirRegularClass firRegularClass4 = z ? (FirRegularClass) owner : null;
        if (firRegularClass4 == null || (contextReceivers = firRegularClass4.getContextReceivers()) == null) {
            arrayListEmptyList = null;
        } else {
            List<FirContextReceiver> list = contextReceivers;
            ArrayList arrayList4 = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(list, 10));
            int i = 0;
            for (Object obj : list) {
                int i2 = i + 1;
                if (i < 0) {
                    kotlin.collections.CollectionsKt.throwIndexOverflow();
                }
                FirContextReceiver firContextReceiver = (FirContextReceiver) obj;
                arrayList4.add(new ContextReceiverValueForClass(((FirRegularClass) owner).getSymbol(), FirTypeUtilsKt.getConeType(firContextReceiver.getTypeRef()), UtilsKt.getLabelName(firContextReceiver), sessionHolder.getSession(), sessionHolder.getScopeSession(), false, i, 32, null));
                i = i2;
            }
            arrayListEmptyList = arrayList4;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = kotlin.collections.CollectionsKt.emptyList();
        }
        return new TowerElementsForClass(implicitDispatchReceiverValue4, arrayListEmptyList, staticScope(owner, sessionHolder), implicitDispatchReceiverValue3, firRegularClass2 != null ? staticScope(firRegularClass2, sessionHolder) : null, kotlin.collections.CollectionsKt.asReversedMutable(arrayList3), kotlin.collections.CollectionsKt.asReversedMutable(arrayList));
    }

    public static final FirTowerDataElement asTowerDataElement(ImplicitReceiverValue<?> implicitReceiverValue) {
        Intrinsics.checkNotNullParameter(implicitReceiverValue, "<this>");
        return new FirTowerDataElement(null, implicitReceiverValue, null, false, null, 20, null);
    }

    public static final FirTowerDataElement asTowerDataElement(List<? extends ContextReceiverValue<?>> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return new FirTowerDataElement(null, null, list, false, null, 16, null);
    }

    public static final FirTowerDataElement asTowerDataElement(FirScope firScope, boolean z) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        return new FirTowerDataElement(firScope, null, null, z, null, 20, null);
    }

    public static final FirTowerDataElement asTowerDataElementForStaticScope(FirScope firScope, FirRegularClassSymbol firRegularClassSymbol) {
        Intrinsics.checkNotNullParameter(firScope, "<this>");
        return new FirTowerDataElement(firScope, null, null, false, firRegularClassSymbol, 4, null);
    }

    public static final FirContainingNamesAwareScope staticScope(FirClass firClass, SessionHolder sessionHolder) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(sessionHolder, "sessionHolder");
        return staticScope(firClass, sessionHolder.getSession(), sessionHolder.getScopeSession());
    }

    public static final FirContainingNamesAwareScope staticScope(FirClass firClass, FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        return firClass.getScopeProvider().getStaticScope(firClass, session, scopeSession);
    }

    public static final List<ContextReceiverValueForCallable> createContextReceiverValues(FirCallableDeclaration firCallableDeclaration, SessionHolder sessionHolder) {
        Intrinsics.checkNotNullParameter(firCallableDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(sessionHolder, "sessionHolder");
        List<FirContextReceiver> contextReceivers = firCallableDeclaration.getContextReceivers();
        ArrayList arrayList = new ArrayList(kotlin.collections.CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        int i = 0;
        for (Iterator it = contextReceivers.iterator(); it.hasNext(); it = it) {
            Object next = it.next();
            int i2 = i + 1;
            if (i < 0) {
                kotlin.collections.CollectionsKt.throwIndexOverflow();
            }
            FirContextReceiver firContextReceiver = (FirContextReceiver) next;
            arrayList.add(new ContextReceiverValueForCallable(firCallableDeclaration.getSymbol(), FirTypeUtilsKt.getConeType(firContextReceiver.getTypeRef()), UtilsKt.getLabelName(firContextReceiver), sessionHolder.getSession(), sessionHolder.getScopeSession(), false, i, 32, null));
            i = i2;
        }
        return arrayList;
    }
}
