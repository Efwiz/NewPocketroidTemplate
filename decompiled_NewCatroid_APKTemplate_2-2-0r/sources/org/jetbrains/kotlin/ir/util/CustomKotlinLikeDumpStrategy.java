package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.Printer;
import org.luaj.vm2.Lua;

/* JADX INFO: compiled from: dumpKotlinLike.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u0014\u0015J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0006\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0010H\u0016J\"\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy;", "", "didPrintElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "printer", "Lorg/jetbrains/kotlin/utils/Printer;", "shouldPrintAnnotation", "", "annotation", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "transformModifiersForDeclaration", "Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy$Modifiers;", "declaration", "modifiers", "willPrintElement", "Default", "Modifiers", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CustomKotlinLikeDumpStrategy {

    /* JADX INFO: compiled from: dumpKotlinLike.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static void didPrintElement(CustomKotlinLikeDumpStrategy customKotlinLikeDumpStrategy, IrElement element, IrDeclaration irDeclaration, Printer printer) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(printer, "printer");
        }

        public static boolean shouldPrintAnnotation(CustomKotlinLikeDumpStrategy customKotlinLikeDumpStrategy, IrConstructorCall annotation, IrAnnotationContainer container) {
            Intrinsics.checkNotNullParameter(annotation, "annotation");
            Intrinsics.checkNotNullParameter(container, "container");
            return true;
        }

        public static Modifiers transformModifiersForDeclaration(CustomKotlinLikeDumpStrategy customKotlinLikeDumpStrategy, IrDeclaration declaration, Modifiers modifiers) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(modifiers, "modifiers");
            return modifiers;
        }

        public static boolean willPrintElement(CustomKotlinLikeDumpStrategy customKotlinLikeDumpStrategy, IrElement element, IrDeclaration irDeclaration, Printer printer) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(printer, "printer");
            return true;
        }
    }

    void didPrintElement(IrElement element, IrDeclaration container, Printer printer);

    boolean shouldPrintAnnotation(IrConstructorCall annotation, IrAnnotationContainer container);

    Modifiers transformModifiersForDeclaration(IrDeclaration declaration, Modifiers modifiers);

    boolean willPrintElement(IrElement element, IrDeclaration container, Printer printer);

    /* JADX INFO: compiled from: dumpKotlinLike.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b*\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Bï\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0005¢\u0006\u0002\u0010\u001dJ\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0005HÆ\u0003J\t\u0010'\u001a\u00020\u0005HÆ\u0003J\t\u0010(\u001a\u00020\u0005HÆ\u0003J\t\u0010)\u001a\u00020\u0005HÆ\u0003J\t\u0010*\u001a\u00020\u0005HÆ\u0003J\t\u0010+\u001a\u00020\u0005HÆ\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0015HÆ\u0003J\t\u0010-\u001a\u00020\u0005HÆ\u0003J\t\u0010.\u001a\u00020\u0005HÆ\u0003J\t\u0010/\u001a\u00020\u0005HÆ\u0003J\t\u00100\u001a\u00020\u0005HÆ\u0003J\t\u00101\u001a\u00020\u0005HÆ\u0003J\t\u00102\u001a\u00020\u0005HÆ\u0003J\t\u00103\u001a\u00020\u0005HÆ\u0003J\t\u00104\u001a\u00020\u0005HÆ\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\t\u00106\u001a\u00020\u0005HÆ\u0003J\t\u00107\u001a\u00020\u0005HÆ\u0003J\t\u00108\u001a\u00020\u0005HÆ\u0003J\t\u00109\u001a\u00020\u0005HÆ\u0003J\t\u0010:\u001a\u00020\u0005HÆ\u0003J\t\u0010;\u001a\u00020\u0005HÆ\u0003Jó\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00052\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\b\u0002\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u0017\u001a\u00020\u00052\b\b\u0002\u0010\u0018\u001a\u00020\u00052\b\b\u0002\u0010\u0019\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u0005HÆ\u0001J\u0013\u0010=\u001a\u00020\u00052\b\u0010>\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010?\u001a\u00020@HÖ\u0001J\t\u0010A\u001a\u00020BHÖ\u0001R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u001c\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010 R\u0011\u0010\u0012\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010 R\u0011\u0010\u0019\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010 R\u0011\u0010\u0011\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010 R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010 R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010 R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010 R\u0011\u0010\u0013\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010 R\u0011\u0010\u001b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010 R\u0011\u0010\u0016\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010 R\u0011\u0010\u000f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010 R\u0011\u0010\u000e\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010 R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010 R\u0011\u0010\u001a\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010 R\u0011\u0010\u0017\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010 R\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010 R\u0011\u0010\r\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010 R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010 R\u0011\u0010\u0010\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010 R\u0011\u0010\u0018\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010 R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy$Modifiers;", "", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isExpect", "", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "isExternal", "isOverride", "isFakeOverride", "isLateinit", "isTailrec", "isSuspend", "isInner", "isInline", "isValue", "isData", "isCompanion", "isFunInterface", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "isInfix", "isOperator", "isVararg", "isCrossinline", "isNoinline", "isHidden", "isAssignable", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;ZLorg/jetbrains/kotlin/descriptors/Modality;ZZZZZZZZZZZZLorg/jetbrains/kotlin/descriptors/ClassKind;ZZZZZZZ)V", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "()Z", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component3", "component4", "component5", "component6", "component7", "component8", "component9", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Modifiers {
        private final ClassKind classKind;
        private final boolean isAssignable;
        private final boolean isCompanion;
        private final boolean isCrossinline;
        private final boolean isData;
        private final boolean isExpect;
        private final boolean isExternal;
        private final boolean isFakeOverride;
        private final boolean isFunInterface;
        private final boolean isHidden;
        private final boolean isInfix;
        private final boolean isInline;
        private final boolean isInner;
        private final boolean isLateinit;
        private final boolean isNoinline;
        private final boolean isOperator;
        private final boolean isOverride;
        private final boolean isSuspend;
        private final boolean isTailrec;
        private final boolean isValue;
        private final boolean isVararg;
        private final Modality modality;
        private final DescriptorVisibility visibility;

        public Modifiers() {
            this(null, false, null, false, false, false, false, false, false, false, false, false, false, false, false, null, false, false, false, false, false, false, false, Lua.MASK_NOT_B, null);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final DescriptorVisibility getVisibility() {
            return this.visibility;
        }

        /* JADX INFO: renamed from: component10, reason: from getter */
        public final boolean getIsInner() {
            return this.isInner;
        }

        /* JADX INFO: renamed from: component11, reason: from getter */
        public final boolean getIsInline() {
            return this.isInline;
        }

        /* JADX INFO: renamed from: component12, reason: from getter */
        public final boolean getIsValue() {
            return this.isValue;
        }

        /* JADX INFO: renamed from: component13, reason: from getter */
        public final boolean getIsData() {
            return this.isData;
        }

        /* JADX INFO: renamed from: component14, reason: from getter */
        public final boolean getIsCompanion() {
            return this.isCompanion;
        }

        /* JADX INFO: renamed from: component15, reason: from getter */
        public final boolean getIsFunInterface() {
            return this.isFunInterface;
        }

        /* JADX INFO: renamed from: component16, reason: from getter */
        public final ClassKind getClassKind() {
            return this.classKind;
        }

        /* JADX INFO: renamed from: component17, reason: from getter */
        public final boolean getIsInfix() {
            return this.isInfix;
        }

        /* JADX INFO: renamed from: component18, reason: from getter */
        public final boolean getIsOperator() {
            return this.isOperator;
        }

        /* JADX INFO: renamed from: component19, reason: from getter */
        public final boolean getIsVararg() {
            return this.isVararg;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getIsExpect() {
            return this.isExpect;
        }

        /* JADX INFO: renamed from: component20, reason: from getter */
        public final boolean getIsCrossinline() {
            return this.isCrossinline;
        }

        /* JADX INFO: renamed from: component21, reason: from getter */
        public final boolean getIsNoinline() {
            return this.isNoinline;
        }

        /* JADX INFO: renamed from: component22, reason: from getter */
        public final boolean getIsHidden() {
            return this.isHidden;
        }

        /* JADX INFO: renamed from: component23, reason: from getter */
        public final boolean getIsAssignable() {
            return this.isAssignable;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final Modality getModality() {
            return this.modality;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getIsExternal() {
            return this.isExternal;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getIsOverride() {
            return this.isOverride;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final boolean getIsFakeOverride() {
            return this.isFakeOverride;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final boolean getIsLateinit() {
            return this.isLateinit;
        }

        /* JADX INFO: renamed from: component8, reason: from getter */
        public final boolean getIsTailrec() {
            return this.isTailrec;
        }

        /* JADX INFO: renamed from: component9, reason: from getter */
        public final boolean getIsSuspend() {
            return this.isSuspend;
        }

        public final Modifiers copy(DescriptorVisibility visibility, boolean isExpect, Modality modality, boolean isExternal, boolean isOverride, boolean isFakeOverride, boolean isLateinit, boolean isTailrec, boolean isSuspend, boolean isInner, boolean isInline, boolean isValue, boolean isData, boolean isCompanion, boolean isFunInterface, ClassKind classKind, boolean isInfix, boolean isOperator, boolean isVararg, boolean isCrossinline, boolean isNoinline, boolean isHidden, boolean isAssignable) {
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            return new Modifiers(visibility, isExpect, modality, isExternal, isOverride, isFakeOverride, isLateinit, isTailrec, isSuspend, isInner, isInline, isValue, isData, isCompanion, isFunInterface, classKind, isInfix, isOperator, isVararg, isCrossinline, isNoinline, isHidden, isAssignable);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Modifiers)) {
                return false;
            }
            Modifiers modifiers = (Modifiers) other;
            return Intrinsics.areEqual(this.visibility, modifiers.visibility) && this.isExpect == modifiers.isExpect && this.modality == modifiers.modality && this.isExternal == modifiers.isExternal && this.isOverride == modifiers.isOverride && this.isFakeOverride == modifiers.isFakeOverride && this.isLateinit == modifiers.isLateinit && this.isTailrec == modifiers.isTailrec && this.isSuspend == modifiers.isSuspend && this.isInner == modifiers.isInner && this.isInline == modifiers.isInline && this.isValue == modifiers.isValue && this.isData == modifiers.isData && this.isCompanion == modifiers.isCompanion && this.isFunInterface == modifiers.isFunInterface && this.classKind == modifiers.classKind && this.isInfix == modifiers.isInfix && this.isOperator == modifiers.isOperator && this.isVararg == modifiers.isVararg && this.isCrossinline == modifiers.isCrossinline && this.isNoinline == modifiers.isNoinline && this.isHidden == modifiers.isHidden && this.isAssignable == modifiers.isAssignable;
        }

        public int hashCode() {
            int iHashCode = ((this.visibility.hashCode() * 31) + Boolean.hashCode(this.isExpect)) * 31;
            Modality modality = this.modality;
            int iHashCode2 = (((((((((((((((((((((((((iHashCode + (modality == null ? 0 : modality.hashCode())) * 31) + Boolean.hashCode(this.isExternal)) * 31) + Boolean.hashCode(this.isOverride)) * 31) + Boolean.hashCode(this.isFakeOverride)) * 31) + Boolean.hashCode(this.isLateinit)) * 31) + Boolean.hashCode(this.isTailrec)) * 31) + Boolean.hashCode(this.isSuspend)) * 31) + Boolean.hashCode(this.isInner)) * 31) + Boolean.hashCode(this.isInline)) * 31) + Boolean.hashCode(this.isValue)) * 31) + Boolean.hashCode(this.isData)) * 31) + Boolean.hashCode(this.isCompanion)) * 31) + Boolean.hashCode(this.isFunInterface)) * 31;
            ClassKind classKind = this.classKind;
            return ((((((((((((((iHashCode2 + (classKind != null ? classKind.hashCode() : 0)) * 31) + Boolean.hashCode(this.isInfix)) * 31) + Boolean.hashCode(this.isOperator)) * 31) + Boolean.hashCode(this.isVararg)) * 31) + Boolean.hashCode(this.isCrossinline)) * 31) + Boolean.hashCode(this.isNoinline)) * 31) + Boolean.hashCode(this.isHidden)) * 31) + Boolean.hashCode(this.isAssignable);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("Modifiers(visibility=");
            sb.append(this.visibility).append(", isExpect=").append(this.isExpect).append(", modality=").append(this.modality).append(", isExternal=").append(this.isExternal).append(", isOverride=").append(this.isOverride).append(", isFakeOverride=").append(this.isFakeOverride).append(", isLateinit=").append(this.isLateinit).append(", isTailrec=").append(this.isTailrec).append(", isSuspend=").append(this.isSuspend).append(", isInner=").append(this.isInner).append(", isInline=").append(this.isInline).append(", isValue=");
            sb.append(this.isValue).append(", isData=").append(this.isData).append(", isCompanion=").append(this.isCompanion).append(", isFunInterface=").append(this.isFunInterface).append(", classKind=").append(this.classKind).append(", isInfix=").append(this.isInfix).append(", isOperator=").append(this.isOperator).append(", isVararg=").append(this.isVararg).append(", isCrossinline=").append(this.isCrossinline).append(", isNoinline=").append(this.isNoinline).append(", isHidden=").append(this.isHidden).append(", isAssignable=").append(this.isAssignable);
            sb.append(')');
            return sb.toString();
        }

        public Modifiers(DescriptorVisibility visibility, boolean z, Modality modality, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, ClassKind classKind, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20) {
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            this.visibility = visibility;
            this.isExpect = z;
            this.modality = modality;
            this.isExternal = z2;
            this.isOverride = z3;
            this.isFakeOverride = z4;
            this.isLateinit = z5;
            this.isTailrec = z6;
            this.isSuspend = z7;
            this.isInner = z8;
            this.isInline = z9;
            this.isValue = z10;
            this.isData = z11;
            this.isCompanion = z12;
            this.isFunInterface = z13;
            this.classKind = classKind;
            this.isInfix = z14;
            this.isOperator = z15;
            this.isVararg = z16;
            this.isCrossinline = z17;
            this.isNoinline = z18;
            this.isHidden = z19;
            this.isAssignable = z20;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public /* synthetic */ Modifiers(DescriptorVisibility descriptorVisibility, boolean z, Modality modality, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, ClassKind classKind, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, int i, DefaultConstructorMarker defaultConstructorMarker) {
            DescriptorVisibility DEFAULT_VISIBILITY;
            if ((i & 1) != 0) {
                DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
                Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
            } else {
                DEFAULT_VISIBILITY = descriptorVisibility;
            }
            this(DEFAULT_VISIBILITY, (i & 2) != 0 ? false : z, (i & 4) != 0 ? null : modality, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, (i & 32) != 0 ? false : z4, (i & 64) != 0 ? false : z5, (i & 128) != 0 ? false : z6, (i & 256) != 0 ? false : z7, (i & 512) != 0 ? false : z8, (i & 1024) != 0 ? false : z9, (i & 2048) != 0 ? false : z10, (i & 4096) != 0 ? false : z11, (i & 8192) != 0 ? false : z12, (i & 16384) != 0 ? false : z13, (i & 32768) != 0 ? null : classKind, (i & 65536) != 0 ? false : z14, (i & 131072) != 0 ? false : z15, (i & 262144) != 0 ? false : z16, (i & 524288) != 0 ? false : z17, (i & 1048576) != 0 ? false : z18, (i & 2097152) != 0 ? false : z19, (i & 4194304) != 0 ? false : z20);
        }

        public final DescriptorVisibility getVisibility() {
            return this.visibility;
        }

        public final boolean isExpect() {
            return this.isExpect;
        }

        public final Modality getModality() {
            return this.modality;
        }

        public final boolean isExternal() {
            return this.isExternal;
        }

        public final boolean isOverride() {
            return this.isOverride;
        }

        public final boolean isFakeOverride() {
            return this.isFakeOverride;
        }

        public final boolean isLateinit() {
            return this.isLateinit;
        }

        public final boolean isTailrec() {
            return this.isTailrec;
        }

        public final boolean isSuspend() {
            return this.isSuspend;
        }

        public final boolean isInner() {
            return this.isInner;
        }

        public final boolean isInline() {
            return this.isInline;
        }

        public final boolean isValue() {
            return this.isValue;
        }

        public final boolean isData() {
            return this.isData;
        }

        public final boolean isCompanion() {
            return this.isCompanion;
        }

        public final boolean isFunInterface() {
            return this.isFunInterface;
        }

        public final ClassKind getClassKind() {
            return this.classKind;
        }

        public final boolean isInfix() {
            return this.isInfix;
        }

        public final boolean isOperator() {
            return this.isOperator;
        }

        public final boolean isVararg() {
            return this.isVararg;
        }

        public final boolean isCrossinline() {
            return this.isCrossinline;
        }

        public final boolean isNoinline() {
            return this.isNoinline;
        }

        public final boolean isHidden() {
            return this.isHidden;
        }

        public final boolean isAssignable() {
            return this.isAssignable;
        }
    }

    /* JADX INFO: compiled from: dumpKotlinLike.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy$Default;", "Lorg/jetbrains/kotlin/ir/util/CustomKotlinLikeDumpStrategy;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements CustomKotlinLikeDumpStrategy {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.ir.util.CustomKotlinLikeDumpStrategy
        public void didPrintElement(IrElement irElement, IrDeclaration irDeclaration, Printer printer) {
            DefaultImpls.didPrintElement(this, irElement, irDeclaration, printer);
        }

        @Override // org.jetbrains.kotlin.ir.util.CustomKotlinLikeDumpStrategy
        public boolean shouldPrintAnnotation(IrConstructorCall irConstructorCall, IrAnnotationContainer irAnnotationContainer) {
            return DefaultImpls.shouldPrintAnnotation(this, irConstructorCall, irAnnotationContainer);
        }

        @Override // org.jetbrains.kotlin.ir.util.CustomKotlinLikeDumpStrategy
        public Modifiers transformModifiersForDeclaration(IrDeclaration irDeclaration, Modifiers modifiers) {
            return DefaultImpls.transformModifiersForDeclaration(this, irDeclaration, modifiers);
        }

        @Override // org.jetbrains.kotlin.ir.util.CustomKotlinLikeDumpStrategy
        public boolean willPrintElement(IrElement irElement, IrDeclaration irDeclaration, Printer printer) {
            return DefaultImpls.willPrintElement(this, irElement, irDeclaration, printer);
        }
    }
}
