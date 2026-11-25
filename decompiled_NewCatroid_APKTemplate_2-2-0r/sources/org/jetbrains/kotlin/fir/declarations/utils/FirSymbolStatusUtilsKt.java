package org.jetbrains.kotlin.fir.declarations.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.EffectiveVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;

/* JADX INFO: compiled from: FirSymbolStatusUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u001a\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u001a\u0010\u0007\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0007\u0010\t\"\u001a\u0010\u0007\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n\"\u001a\u0010\u000b\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t\"\u001a\u0010\u000b\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000b\u0010\n\"\u001a\u0010\f\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\r8Æ\u0002¢\u0006\u0006\u001a\u0004\b\f\u0010\u000e\"\u001a\u0010\u000f\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u000f\u0010\n\"\u001a\u0010\u0010\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0010\u0010\t\"\u001a\u0010\u0011\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0011\u0010\n\"\u001a\u0010\u0012\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\r8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000e\"\u001a\u0010\u0013\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\r8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u000e\"\u001a\u0010\u0014\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\t\"\u001a\u0010\u0014\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\n\"\u001a\u0010\u0015\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0015\u0010\t\"\u001a\u0010\u0015\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0015\u0010\n\"\u001a\u0010\u0016\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0016\u0010\t\"\u001a\u0010\u0016\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0016\u0010\n\"\u001a\u0010\u0017\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\t\"\u001a\u0010\u0017\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\n\"\u001a\u0010\u0018\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\t\"\u001a\u0010\u0018\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0018\u0010\n\"\u001a\u0010\u0019\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0019\u0010\t\"\u001a\u0010\u0019\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0019\u0010\n\"\u001a\u0010\u001a\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001a\u0010\t\"\u001a\u0010\u001b\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001b\u0010\t\"\u001a\u0010\u001b\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001b\u0010\n\"\u001a\u0010\u001c\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001c\u0010\t\"\u001a\u0010\u001c\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001c\u0010\n\"\u001a\u0010\u001d\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\r8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u000e\"\u001a\u0010\u001e\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001e\u0010\t\"\u001a\u0010\u001f\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001f\u0010\n\"\u0016\u0010\u001f\u001a\u00020\b*\u00020 8Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001f\u0010!\"\u001a\u0010\"\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\r8Æ\u0002¢\u0006\u0006\u001a\u0004\b\"\u0010\u000e\"\u001a\u0010#\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b#\u0010\t\"\u001a\u0010$\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b$\u0010\t\"\u001a\u0010%\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b%\u0010\t\"\u001a\u0010&\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b&\u0010\n\"\u001a\u0010'\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b'\u0010\t\"\u001a\u0010'\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b'\u0010\n\"\u001a\u0010(\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b(\u0010\t\"\u001a\u0010)\u001a\u00020\b*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b)\u0010\t\"\u001c\u0010*\u001a\u0004\u0018\u00010+*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b,\u0010-\"\u001c\u0010*\u001a\u0004\u0018\u00010+*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b,\u0010.\"\u001a\u0010/\u001a\u00020+*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b0\u0010-\"\u001a\u00101\u001a\u000202*\u0006\u0012\u0002\b\u00030\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b3\u00104\"\u001a\u00101\u001a\u000202*\u0006\u0012\u0002\b\u00030\u00058Æ\u0002¢\u0006\u0006\u001a\u0004\b3\u00105¨\u00066"}, d2 = {"effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getEffectiveVisibility", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "isAbstract", "", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Z", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Z", "isActual", "isClass", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;)Z", "isCompanion", "isConst", "isData", "isEnumClass", "isEnumEntry", "isExpect", "isExternal", "isFinal", "isFromEnumClass", "isFromSealedClass", "isFun", "isInfix", "isInline", "isInner", "isInterface", "isLateInit", "isLocal", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;)Z", "isLocalClassOrAnonymousObject", "isOpen", "isOperator", "isOverride", "isSealed", "isStatic", "isSuspend", "isTailRec", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Lorg/jetbrains/kotlin/descriptors/Modality;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Lorg/jetbrains/kotlin/descriptors/Modality;", "modalityOrFinal", "getModalityOrFinal", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Lorg/jetbrains/kotlin/descriptors/Visibility;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;)Lorg/jetbrains/kotlin/descriptors/Visibility;", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirSymbolStatusUtilsKt {
    public static final Modality getModality(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getResolvedStatus().getModality();
    }

    public static final boolean isAbstract(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getResolvedStatus().getModality() == Modality.ABSTRACT;
    }

    public static final boolean isOpen(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getResolvedStatus().getModality() == Modality.OPEN;
    }

    public static final boolean isFinal(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Modality modality = firCallableSymbol.getResolvedStatus().getModality();
        return modality == null || modality == Modality.FINAL;
    }

    public static final Visibility getVisibility(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getResolvedStatus().getVisibility();
    }

    public static final EffectiveVisibility getEffectiveVisibility(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getResolvedStatus().getEffectiveVisibility();
    }

    public static final boolean isActual(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isActual();
    }

    public static final boolean isExpect(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isExpect();
    }

    public static final boolean isInner(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isInner();
    }

    public static final boolean isStatic(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isStatic();
    }

    public static final boolean isOverride(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isOverride();
    }

    public static final boolean isOperator(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getResolvedStatus().isOperator();
    }

    public static final boolean isInfix(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getResolvedStatus().isInfix();
    }

    public static final boolean isInline(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isInline();
    }

    public static final boolean isTailRec(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isTailRec();
    }

    public static final boolean isExternal(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isExternal();
    }

    public static final boolean isSuspend(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isSuspend();
    }

    public static final boolean isConst(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isConst();
    }

    public static final boolean isLateInit(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isLateInit();
    }

    public static final boolean isFromSealedClass(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isFromSealedClass();
    }

    public static final boolean isFromEnumClass(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isFromEnumClass();
    }

    public static final boolean isFun(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        return firCallableSymbol.getRawStatus().isFun();
    }

    public static final Modality getModality(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getResolvedStatus().getModality();
    }

    public static final boolean isAbstract(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getResolvedStatus().getModality() == Modality.ABSTRACT;
    }

    public static final boolean isFinal(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        Modality modality = firClassLikeSymbol.getResolvedStatus().getModality();
        return modality == null || modality == Modality.FINAL;
    }

    public static final Visibility getVisibility(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getResolvedStatus().getVisibility();
    }

    public static final EffectiveVisibility getEffectiveVisibility(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getResolvedStatus().getEffectiveVisibility();
    }

    public static final boolean isActual(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isActual();
    }

    public static final boolean isExpect(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isExpect();
    }

    public static final boolean isInner(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isInner();
    }

    public static final boolean isStatic(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isStatic();
    }

    public static final boolean isInline(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isInline();
    }

    public static final boolean isExternal(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isExternal();
    }

    public static final boolean isFromSealedClass(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isFromSealedClass();
    }

    public static final boolean isFromEnumClass(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isFromEnumClass();
    }

    public static final boolean isFun(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isFun();
    }

    public static final boolean isCompanion(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isCompanion();
    }

    public static final boolean isData(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getRawStatus().isData();
    }

    public static final boolean isSealed(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getResolvedStatus().getModality() == Modality.SEALED;
    }

    public static final boolean isLocal(FirClassLikeSymbol<?> firClassLikeSymbol) {
        Intrinsics.checkNotNullParameter(firClassLikeSymbol, "<this>");
        return firClassLikeSymbol.getClassId().isLocal();
    }

    public static final boolean isLocalClassOrAnonymousObject(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return firClassSymbol.getClassId().isLocal() || (firClassSymbol instanceof FirAnonymousObjectSymbol);
    }

    public static final boolean isClass(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return firClassSymbol.getClassKind() == ClassKind.CLASS;
    }

    public static final boolean isInterface(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return firClassSymbol.getClassKind() == ClassKind.INTERFACE;
    }

    public static final boolean isEnumClass(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return firClassSymbol.getClassKind() == ClassKind.ENUM_CLASS;
    }

    public static final boolean isEnumEntry(FirClassSymbol<?> firClassSymbol) {
        Intrinsics.checkNotNullParameter(firClassSymbol, "<this>");
        return firClassSymbol.getClassKind() == ClassKind.ENUM_ENTRY;
    }

    public static final boolean isLocal(FirNamedFunctionSymbol firNamedFunctionSymbol) {
        Intrinsics.checkNotNullParameter(firNamedFunctionSymbol, "<this>");
        return Intrinsics.areEqual(firNamedFunctionSymbol.getRawStatus().getVisibility(), Visibilities.Local.INSTANCE);
    }

    public static final Modality getModalityOrFinal(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Modality modality = firCallableSymbol.getResolvedStatus().getModality();
        return modality == null ? Modality.FINAL : modality;
    }
}
