package org.jetbrains.kotlin.fir.analysis.checkers.declaration;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.checkers.FirHelpersKt;
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.impl.FirImplicitUnitTypeRef;

/* JADX INFO: compiled from: declarationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a-\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00010\u0016H\u0082\b\u001a\u001e\u0010\u0017\u001a\u00020\u0001*\u00020\u00182\b\u0010\u0010\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a\u001e\u0010\u0019\u001a\u00020\u0001*\u00020\u00182\b\u0010\u0010\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a\u0014\u0010\u001a\u001a\u00020\u0001*\u00020\u00182\u0006\u0010\u0011\u001a\u00020\u0012H\u0000\u001a\u0012\u0010\u001b\u001a\u00020\u0001*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e\u001a\u0010\u0010\u001f\u001a\u0004\u0018\u00010 *\u0006\u0012\u0002\b\u00030!\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0019\u0010\u0005\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u001c\u0010\t\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00068@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\b\"\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\f\"\u001c\u0010\n\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\r8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000e¨\u0006\""}, d2 = {"canHaveOpenMembers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getCanHaveOpenMembers", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Z", "hasExplicitReturnType", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getHasExplicitReturnType", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Z", "isExtensionMember", "isLocalMember", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Z", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)Z", "isInsideExpectClass", "containingClass", "context", "Lorg/jetbrains/kotlin/fir/analysis/checkers/context/CheckerContext;", "isInsideExternalClass", "isInsideSpecificClass", "predicate", "Lkotlin/Function1;", "isEffectivelyExpect", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "isEffectivelyExternal", "isEffectivelyFinal", "needsMultiFieldValueClassFlattening", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "primaryConstructorSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeclarationUtilsKt {
    public static final boolean isInsideExpectClass(FirClass containingClass, CheckerContext context) {
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(containingClass instanceof FirRegularClass) || !containingClass.getStatus().isExpect()) {
            List<FirDeclaration> listAsReversed = CollectionsKt.asReversed(context.getContainingDeclarations());
            if (!(listAsReversed instanceof Collection) || !listAsReversed.isEmpty()) {
                for (FirDeclaration firDeclaration : listAsReversed) {
                    if (firDeclaration instanceof FirRegularClass) {
                        FirClass firClass = (FirClass) firDeclaration;
                        if (!(firClass instanceof FirRegularClass) || !firClass.getStatus().isExpect()) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean isInsideExternalClass(FirClass containingClass, CheckerContext context) {
        Intrinsics.checkNotNullParameter(containingClass, "containingClass");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!(containingClass instanceof FirRegularClass) || !containingClass.getStatus().isExternal()) {
            List<FirDeclaration> listAsReversed = CollectionsKt.asReversed(context.getContainingDeclarations());
            if (!(listAsReversed instanceof Collection) || !listAsReversed.isEmpty()) {
                for (FirDeclaration firDeclaration : listAsReversed) {
                    if (firDeclaration instanceof FirRegularClass) {
                        FirClass firClass = (FirClass) firDeclaration;
                        if (!(firClass instanceof FirRegularClass) || !firClass.getStatus().isExternal()) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean getCanHaveOpenMembers(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return FirHelpersKt.modality(firClass) != Modality.FINAL || firClass.getClassKind() == ClassKind.ENUM_CLASS;
    }

    public static final boolean isLocalMember(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        return isLocalMember(firDeclaration.getSymbol());
    }

    public static final boolean isLocalMember(FirBasedSymbol<?> firBasedSymbol) {
        Intrinsics.checkNotNullParameter(firBasedSymbol, "<this>");
        if (firBasedSymbol instanceof FirPropertySymbol) {
            return ((FirPropertySymbol) firBasedSymbol).isLocal();
        }
        if (!(firBasedSymbol instanceof FirRegularClassSymbol)) {
            if (firBasedSymbol instanceof FirNamedFunctionSymbol) {
                return Intrinsics.areEqual(((FirNamedFunctionSymbol) firBasedSymbol).getRawStatus().getVisibility(), Visibilities.Local.INSTANCE);
            }
            return false;
        }
        return ((FirClassLikeSymbol) firBasedSymbol).getClassId().isLocal();
    }

    public static final boolean isExtensionMember(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return (firCallableSymbol.getResolvedReceiverTypeRef() == null || firCallableSymbol.getDispatchReceiverType() == null) ? false : true;
    }

    public static final FirConstructorSymbol primaryConstructorSymbol(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        for (FirBasedSymbol<?> firBasedSymbol : firClassSymbol.getDeclarationSymbols()) {
            if (firBasedSymbol instanceof FirConstructorSymbol) {
                FirConstructorSymbol firConstructorSymbol = (FirConstructorSymbol) firBasedSymbol;
                if (firConstructorSymbol.isPrimary()) {
                    return firConstructorSymbol;
                }
            }
        }
        return null;
    }

    public static final boolean needsMultiFieldValueClassFlattening(FirTypeRef firTypeRef, FirSession session) {
        Intrinsics.checkNotNullParameter(firTypeRef, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(session);
        return typeContext.isMultiFieldValueClass(typeContext.typeConstructor(FirTypeUtilsKt.getConeType(firTypeRef))) && !ConeTypeUtilsKt.isNullable(FirTypeUtilsKt.getConeType(firTypeRef));
    }

    public static final boolean getHasExplicitReturnType(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        FirResolvedTypeRef resolvedReturnTypeRef = firCallableSymbol.getResolvedReturnTypeRef();
        return resolvedReturnTypeRef.getDelegatedTypeRef() != null || (resolvedReturnTypeRef instanceof FirImplicitUnitTypeRef);
    }

    public static final boolean isEffectivelyFinal(FirMemberDeclaration firMemberDeclaration, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        Modality modality = firMemberDeclaration.getStatus().getModality();
        if (modality == null || modality == Modality.FINAL) {
            return true;
        }
        Object objLastOrNull = CollectionsKt.lastOrNull((List<? extends Object>) context.getContainingDeclarations());
        FirRegularClass firRegularClass = objLastOrNull instanceof FirRegularClass ? (FirRegularClass) objLastOrNull : null;
        if (firRegularClass == null) {
            return true;
        }
        if (firRegularClass.getClassKind() == ClassKind.ENUM_CLASS) {
            return false;
        }
        Modality modality2 = firRegularClass.getStatus().getModality();
        return modality2 == null || modality2 == Modality.FINAL;
    }

    public static final boolean isEffectivelyExpect(FirMemberDeclaration firMemberDeclaration, FirClass firClass, CheckerContext context) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (firMemberDeclaration.getStatus().isExpect()) {
            return true;
        }
        return firClass != null && isInsideExpectClass(firClass, context);
    }

    public static final boolean isEffectivelyExternal(FirMemberDeclaration firMemberDeclaration, FirClass firClass, CheckerContext context) {
        FirProperty firProperty;
        FirPropertyAccessor getter;
        FirPropertyAccessor setter;
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (firMemberDeclaration.getStatus().isExternal()) {
            return true;
        }
        if (firMemberDeclaration instanceof FirPropertyAccessor) {
            Object objLast = CollectionsKt.last((List<? extends Object>) context.getContainingDeclarations());
            Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirProperty");
            return isEffectivelyExternal((FirProperty) objLast, firClass, context);
        }
        if (!(firMemberDeclaration instanceof FirProperty) || (getter = (firProperty = (FirProperty) firMemberDeclaration).getGetter()) == null || !getter.getStatus().isExternal() || (firProperty.getIsVar() && ((setter = firProperty.getSetter()) == null || !setter.getStatus().isExternal()))) {
            return firClass != null && isInsideExternalClass(firClass, context);
        }
        return true;
    }
}
