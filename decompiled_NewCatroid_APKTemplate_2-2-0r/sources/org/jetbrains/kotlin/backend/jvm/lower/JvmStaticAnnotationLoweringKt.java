package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;

/* JADX INFO: compiled from: JvmStaticAnnotationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0014\u0010\n\u001a\u00020\u000b*\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H\u0002\u001a\f\u0010\u0012\u001a\u00020\u0010*\u00020\u0011H\u0002\u001a\f\u0010\u0013\u001a\u00020\u0010*\u00020\u0011H\u0000\u001a\"\u0010\u0014\u001a\u00020\f*\u0006\u0012\u0002\b\u00030\u00152\u0006\u0010\r\u001a\u00020\u000e2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0002\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"0\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b0\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\b`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0006¨\u0006\u0018"}, d2 = {"jvmStaticInCompanionPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getJvmStaticInCompanionPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "jvmStaticInObjectPhase", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getJvmStaticInObjectPhase", "coerceToUnit", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrTypeOperatorCallImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "isJvmStaticDeclaration", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "isJvmStaticInCompanion", "isJvmStaticInObject", "makeStatic", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "replaceCallee", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmStaticAnnotationLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> jvmStaticInObjectPhase = PhaseBuildersKt.makeIrModulePhase(JvmStaticAnnotationLoweringKt$jvmStaticInObjectPhase$1.INSTANCE, "JvmStaticInObject", "Make JvmStatic functions in non-companion objects static and replace all call sites in the module", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> jvmStaticInCompanionPhase = PhaseBuildersKt.makeIrFilePhase(JvmStaticAnnotationLoweringKt$jvmStaticInCompanionPhase$1.INSTANCE, "JvmStaticInCompanion", "Synthesize static proxy functions for JvmStatic functions in companion objects", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrModuleFragment> getJvmStaticInObjectPhase() {
        return jvmStaticInObjectPhase;
    }

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getJvmStaticInCompanionPhase() {
        return jvmStaticInCompanionPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isJvmStaticDeclaration(IrDeclaration irDeclaration) {
        IrSimpleFunction getter;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        if (IrUtilsKt.hasAnnotation(irDeclaration, AnnotationUtilKt.getJVM_STATIC_ANNOTATION_FQ_NAME())) {
            return true;
        }
        IrSimpleFunction irSimpleFunction = irDeclaration instanceof IrSimpleFunction ? (IrSimpleFunction) irDeclaration : null;
        if (irSimpleFunction != null && (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null && IrUtilsKt.hasAnnotation(owner, AnnotationUtilKt.getJVM_STATIC_ANNOTATION_FQ_NAME())) {
            return true;
        }
        IrProperty irProperty = irDeclaration instanceof IrProperty ? (IrProperty) irDeclaration : null;
        return (irProperty == null || (getter = irProperty.getGetter()) == null || !IrUtilsKt.hasAnnotation(getter, AnnotationUtilKt.getJVM_STATIC_ANNOTATION_FQ_NAME())) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isJvmStaticInCompanion(IrDeclaration irDeclaration) {
        if (isJvmStaticDeclaration(irDeclaration)) {
            IrDeclarationParent parent = irDeclaration.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass != null && irClass.getIsCompanion()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isJvmStaticInObject(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (isJvmStaticDeclaration(irDeclaration)) {
            IrDeclarationParent parent = irDeclaration.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass != null && IrUtilsKt.isNonCompanionObject(irClass)) {
                return true;
            }
        }
        return false;
    }

    private static final IrTypeOperatorCallImpl coerceToUnit(IrExpression irExpression, IrBuiltIns irBuiltIns) {
        return new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irBuiltIns.getUnitType(), IrTypeOperator.IMPLICIT_COERCION_TO_UNIT, irBuiltIns.getUnitType(), irExpression);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression makeStatic(IrMemberAccessExpression<?> irMemberAccessExpression, IrBuiltIns irBuiltIns, IrSimpleFunction irSimpleFunction) {
        IrExpression dispatchReceiver = irMemberAccessExpression.getDispatchReceiver();
        if (dispatchReceiver == null) {
            return irMemberAccessExpression;
        }
        irMemberAccessExpression.setDispatchReceiver(null);
        if (irSimpleFunction != null) {
            Intrinsics.checkNotNull(irMemberAccessExpression, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrCall");
            ((IrCall) irMemberAccessExpression).setSymbol(irSimpleFunction.getSymbol());
        }
        if (IrUtilsKt.isTrivial(dispatchReceiver)) {
            return irMemberAccessExpression;
        }
        IrBlockImpl irBlockImpl = new IrBlockImpl(irMemberAccessExpression.getStartOffset(), irMemberAccessExpression.getEndOffset(), irMemberAccessExpression.getType(), null, 8, null);
        irBlockImpl.getStatements().add(coerceToUnit(dispatchReceiver, irBuiltIns));
        irBlockImpl.getStatements().add(irMemberAccessExpression);
        return irBlockImpl;
    }
}
