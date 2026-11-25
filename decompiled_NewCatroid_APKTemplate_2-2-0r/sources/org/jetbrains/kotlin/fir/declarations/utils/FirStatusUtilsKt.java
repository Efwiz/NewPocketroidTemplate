package org.jetbrains.kotlin.fir.declarations.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;

/* JADX INFO: compiled from: FirStatusUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\u00078Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\b\"\u0016\u0010\u0000\u001a\u00020\u0001*\u00020\t8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\n\"\u0016\u0010\u000b\u001a\u00020\u0001*\u00020\f8Æ\u0002¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e\"\u0016\u0010\u000f\u001a\u00020\u0010*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\"\u0016\u0010\u0013\u001a\u00020\u0001*\u00020\u00148Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\"\u0016\u0010\u0013\u001a\u00020\u0001*\u00020\t8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0015\u0010\n\"\u0016\u0010\u0017\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0006\"\u0016\u0010\u0019\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0006\"\u0016\u0010\u001a\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0006\"\u0016\u0010\u001b\u001a\u00020\u0001*\u00020\f8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000e\"\u0016\u0010\u001c\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u0006\"\u0016\u0010\u001d\u001a\u00020\u0001*\u00020\f8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u000e\"\u0016\u0010\u001e\u001a\u00020\u0001*\u00020\u001f8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001e\u0010 \"\u0016\u0010!\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b!\u0010\u0006\"\u0016\u0010\"\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\"\u0010\u0006\"\u0016\u0010#\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b#\u0010\u0006\"\u0016\u0010$\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b$\u0010\u0006\"\u0016\u0010%\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b%\u0010\u0006\"\u0016\u0010&\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b&\u0010\u0006\"\u0016\u0010'\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b'\u0010\u0006\"\u0016\u0010(\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b(\u0010\u0006\"\u0016\u0010)\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b)\u0010\u0006\"\u0016\u0010*\u001a\u00020\u0001*\u00020\u001f8Æ\u0002¢\u0006\u0006\u001a\u0004\b*\u0010 \"\u0016\u0010+\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b+\u0010\u0006\"\u0016\u0010,\u001a\u00020\u0001*\u00020-8Æ\u0002¢\u0006\u0006\u001a\u0004\b,\u0010.\"\u0016\u0010,\u001a\u00020\u0001*\u00020/8Æ\u0002¢\u0006\u0006\u001a\u0004\b,\u00100\"\u0016\u00101\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b1\u0010\u0006\"\u0016\u00102\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b2\u0010\u0006\"\u0016\u00103\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b3\u0010\u0006\"\u0016\u00104\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b4\u0010\u0006\"\u0016\u00105\u001a\u00020\u0001*\u00020\f8Æ\u0002¢\u0006\u0006\u001a\u0004\b5\u0010\u000e\"\u0016\u00106\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b6\u0010\u0006\"\u0016\u00107\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b7\u0010\u0006\"\u0016\u00108\u001a\u00020\u0001*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b8\u0010\u0006\"\u0018\u00109\u001a\u0004\u0018\u00010:*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b;\u0010<\"\u0016\u0010=\u001a\u00020\u0002*\u00020\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b>\u0010?¨\u0006@"}, d2 = {"allowsToHaveFakeOverride", "", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getAllowsToHaveFakeOverride", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)Z", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)Z", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirProperty;)Z", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;)Z", "canHaveAbstractDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "getCanHaveAbstractDeclaration", "(Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;)Z", "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "getEffectiveVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "hasBody", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "getHasBody", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;)Z", "hasStableParameterNames", "getHasStableParameterNames", "isAbstract", "isActual", "isCompanion", "isConst", "isData", "isEnumClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Z", "isExpect", "isExternal", "isFinal", "isFromEnumClass", "isFromSealedClass", "isFun", "isInfix", "isInline", "isInner", "isInterface", "isLateInit", "isLocal", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;)Z", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "(Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;)Z", "isOpen", "isOperator", "isOverridable", "isOverride", "isSealed", "isStatic", "isSuspend", "isTailRec", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "getVisibility", "(Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;)Lorg/jetbrains/kotlin/descriptors/Visibility;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirStatusUtilsKt {
    public static final Modality getModality(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().getModality();
    }

    public static final boolean isAbstract(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().getModality() == Modality.ABSTRACT;
    }

    public static final boolean isOpen(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().getModality() == Modality.OPEN;
    }

    public static final boolean isFinal(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        Modality modality = firMemberDeclaration.getStatus().getModality();
        return modality == null || modality == Modality.FINAL;
    }

    public static final Visibility getVisibility(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().getVisibility();
    }

    public static final EffectiveVisibility getEffectiveVisibility(FirMemberDeclaration firMemberDeclaration) {
        EffectiveVisibility effectiveVisibility;
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        FirDeclarationStatus status = firMemberDeclaration.getStatus();
        FirResolvedDeclarationStatus firResolvedDeclarationStatus = status instanceof FirResolvedDeclarationStatus ? (FirResolvedDeclarationStatus) status : null;
        return (firResolvedDeclarationStatus == null || (effectiveVisibility = firResolvedDeclarationStatus.getEffectiveVisibility()) == null) ? EffectiveVisibility.Local.INSTANCE : effectiveVisibility;
    }

    public static final boolean getAllowsToHaveFakeOverride(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return (Visibilities.INSTANCE.isPrivate(firMemberDeclaration.getStatus().getVisibility()) || Intrinsics.areEqual(firMemberDeclaration.getStatus().getVisibility(), Visibilities.InvisibleFake.INSTANCE)) ? false : true;
    }

    public static final boolean isOverridable(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return (firMemberDeclaration.getStatus().getModality() == Modality.FINAL || Intrinsics.areEqual(firMemberDeclaration.getStatus().getVisibility(), Visibilities.Private.INSTANCE)) ? false : true;
    }

    public static final boolean isActual(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isActual();
    }

    public static final boolean isExpect(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isExpect();
    }

    public static final boolean isInner(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isInner();
    }

    public static final boolean isStatic(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isStatic();
    }

    public static final boolean isOverride(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isOverride();
    }

    public static final boolean isOperator(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isOperator();
    }

    public static final boolean isInfix(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isInfix();
    }

    public static final boolean isInline(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isInline();
    }

    public static final boolean isTailRec(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isTailRec();
    }

    public static final boolean isExternal(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isExternal();
    }

    public static final boolean isSuspend(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isSuspend();
    }

    public static final boolean isConst(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isConst();
    }

    public static final boolean isLateInit(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isLateInit();
    }

    public static final boolean isFromSealedClass(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isFromSealedClass();
    }

    public static final boolean isFromEnumClass(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isFromEnumClass();
    }

    public static final boolean isFun(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().isFun();
    }

    public static final boolean getHasStableParameterNames(FirMemberDeclaration firMemberDeclaration) {
        Intrinsics.checkNotNullParameter(firMemberDeclaration, "<this>");
        return firMemberDeclaration.getStatus().getHasStableParameterNames();
    }

    public static final boolean isLocal(FirClassLikeDeclaration firClassLikeDeclaration) {
        Intrinsics.checkNotNullParameter(firClassLikeDeclaration, "<this>");
        return firClassLikeDeclaration.getSymbol().getClassId().isLocal();
    }

    public static final boolean isInterface(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return firClass.getClassKind() == ClassKind.INTERFACE;
    }

    public static final boolean isEnumClass(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "<this>");
        return firClass.getClassKind() == ClassKind.ENUM_CLASS;
    }

    public static final boolean isSealed(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return firRegularClass.getStatus().getModality() == Modality.SEALED;
    }

    public static final boolean getCanHaveAbstractDeclaration(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return firRegularClass.getStatus().getModality() == Modality.ABSTRACT || firRegularClass.getStatus().getModality() == Modality.SEALED || firRegularClass.getClassKind() == ClassKind.ENUM_CLASS;
    }

    public static final boolean isCompanion(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return firRegularClass.getStatus().isCompanion();
    }

    public static final boolean isData(FirRegularClass firRegularClass) {
        Intrinsics.checkNotNullParameter(firRegularClass, "<this>");
        return firRegularClass.getStatus().isData();
    }

    public static final boolean getHasBody(FirFunction firFunction) {
        Intrinsics.checkNotNullParameter(firFunction, "<this>");
        return firFunction.getBody() != null;
    }

    public static final boolean getHasBody(FirPropertyAccessor firPropertyAccessor) {
        Intrinsics.checkNotNullParameter(firPropertyAccessor, "<this>");
        return firPropertyAccessor.getBody() != null;
    }

    public static final boolean getAllowsToHaveFakeOverride(FirPropertyAccessor firPropertyAccessor) {
        Intrinsics.checkNotNullParameter(firPropertyAccessor, "<this>");
        Visibility visibility = firPropertyAccessor.getStatus().getVisibility();
        return (Visibilities.INSTANCE.isPrivate(visibility) || Intrinsics.areEqual(visibility, Visibilities.InvisibleFake.INSTANCE)) ? false : true;
    }

    public static final boolean getAllowsToHaveFakeOverride(FirProperty firProperty) {
        Intrinsics.checkNotNullParameter(firProperty, "<this>");
        Visibility visibility = firProperty.getStatus().getVisibility();
        return (Visibilities.INSTANCE.isPrivate(visibility) || Intrinsics.areEqual(visibility, Visibilities.InvisibleFake.INSTANCE)) ? false : true;
    }

    public static final boolean getAllowsToHaveFakeOverride(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "<this>");
        return (Visibilities.INSTANCE.isPrivate(visibility) || Intrinsics.areEqual(visibility, Visibilities.InvisibleFake.INSTANCE)) ? false : true;
    }

    public static final boolean isLocal(FirSimpleFunction firSimpleFunction) {
        Intrinsics.checkNotNullParameter(firSimpleFunction, "<this>");
        return Intrinsics.areEqual(firSimpleFunction.getStatus().getVisibility(), Visibilities.Local.INSTANCE);
    }
}
