package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.IrArrayBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrElementBuilderKt;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrBreak;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrCatch;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrComposite;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrContinue;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrSetField;
import org.jetbrains.kotlin.ir.expressions.IrSetValue;
import org.jetbrains.kotlin.ir.expressions.IrSpreadElement;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: InlineCallableReferenceToLambda.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001a\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f2\b\u0010\f\u001a\u0004\u0018\u00010\u0003H\u0016J \u0010\u0010\u001a\u00020\u0011*\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u0003H\u0002J\u0016\u0010\u0016\u001a\u00020\u0017*\u00020\u00172\b\u0010\u0015\u001a\u0004\u0018\u00010\u0003H\u0002J\u0014\u0010\u0018\u001a\u00020\u0012*\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002J\u0018\u0010\u001c\u001a\u00020\u0012*\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/InlineCallableReferenceToLambdaVisitor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "data", "visitFunctionAccess", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "toLambda", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "original", "Lorg/jetbrains/kotlin/ir/expressions/IrCallableReference;", "scope", "transform", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "wrapField", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "wrapFunction", "referencedFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class InlineCallableReferenceToLambdaVisitor implements IrElementVisitor<Unit, IrDeclaration> {
    private final JvmBackendContext context;

    public InlineCallableReferenceToLambdaVisitor(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer */
    public /* bridge */ /* synthetic */ Unit visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
        visitAnonymousInitializer2(irAnonymousInitializer, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: avoid collision after fix types in other method */
    public void visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock */
    public /* bridge */ /* synthetic */ Unit visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
        visitBlock2(irBlock, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlock, reason: avoid collision after fix types in other method */
    public void visitBlock2(IrBlock irBlock, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBlock(this, irBlock, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody */
    public /* bridge */ /* synthetic */ Unit visitBlockBody2(IrBlockBody irBlockBody, IrDeclaration irDeclaration) {
        visitBlockBody2(irBlockBody, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBlockBody, reason: avoid collision after fix types in other method */
    public void visitBlockBody2(IrBlockBody irBlockBody, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBlockBody(this, irBlockBody, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody */
    public /* bridge */ /* synthetic */ Unit visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
        visitBody2(irBody, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBody, reason: avoid collision after fix types in other method */
    public void visitBody2(IrBody irBody, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBody(this, irBody, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch */
    public /* bridge */ /* synthetic */ Unit visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
        visitBranch2(irBranch, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBranch, reason: avoid collision after fix types in other method */
    public void visitBranch2(IrBranch irBranch, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBranch(this, irBranch, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak */
    public /* bridge */ /* synthetic */ Unit visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
        visitBreak2(irBreak, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreak, reason: avoid collision after fix types in other method */
    public void visitBreak2(IrBreak irBreak, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBreak(this, irBreak, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue */
    public /* bridge */ /* synthetic */ Unit visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
        visitBreakContinue2(irBreakContinue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitBreakContinue, reason: avoid collision after fix types in other method */
    public void visitBreakContinue2(IrBreakContinue irBreakContinue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitBreakContinue(this, irBreakContinue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall */
    public /* bridge */ /* synthetic */ Unit visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
        visitCall2(irCall, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCall, reason: avoid collision after fix types in other method */
    public void visitCall2(IrCall irCall, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitCall(this, irCall, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitCallableReference(IrCallableReference irCallableReference, IrDeclaration irDeclaration) {
        visitCallableReference2((IrCallableReference<?>) irCallableReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public void visitCallableReference2(IrCallableReference<?> irCallableReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitCallableReference(this, irCallableReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch */
    public /* bridge */ /* synthetic */ Unit visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
        visitCatch2(irCatch, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitCatch, reason: avoid collision after fix types in other method */
    public void visitCatch2(IrCatch irCatch, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitCatch(this, irCatch, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass */
    public /* bridge */ /* synthetic */ Unit visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
        visitClass2(irClass, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClass, reason: avoid collision after fix types in other method */
    public void visitClass2(IrClass irClass, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitClass(this, irClass, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference */
    public /* bridge */ /* synthetic */ Unit visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
        visitClassReference2(irClassReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitClassReference, reason: avoid collision after fix types in other method */
    public void visitClassReference2(IrClassReference irClassReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitClassReference(this, irClassReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite */
    public /* bridge */ /* synthetic */ Unit visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
        visitComposite2(irComposite, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitComposite, reason: avoid collision after fix types in other method */
    public void visitComposite2(IrComposite irComposite, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitComposite(this, irComposite, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitConst(IrConst irConst, IrDeclaration irDeclaration) {
        visitConst2((IrConst<?>) irConst, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public void visitConst2(IrConst<?> irConst, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConst(this, irConst, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray */
    public /* bridge */ /* synthetic */ Unit visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
        visitConstantArray2(irConstantArray, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantArray, reason: avoid collision after fix types in other method */
    public void visitConstantArray2(IrConstantArray irConstantArray, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantArray(this, irConstantArray, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject */
    public /* bridge */ /* synthetic */ Unit visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
        visitConstantObject2(irConstantObject, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantObject, reason: avoid collision after fix types in other method */
    public void visitConstantObject2(IrConstantObject irConstantObject, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantObject(this, irConstantObject, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive */
    public /* bridge */ /* synthetic */ Unit visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
        visitConstantPrimitive2(irConstantPrimitive, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantPrimitive, reason: avoid collision after fix types in other method */
    public void visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue */
    public /* bridge */ /* synthetic */ Unit visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
        visitConstantValue2(irConstantValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstantValue, reason: avoid collision after fix types in other method */
    public void visitConstantValue2(IrConstantValue irConstantValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstantValue(this, irConstantValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor */
    public /* bridge */ /* synthetic */ Unit visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
        visitConstructor2(irConstructor, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructor, reason: avoid collision after fix types in other method */
    public void visitConstructor2(IrConstructor irConstructor, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstructor(this, irConstructor, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
        visitConstructorCall2(irConstructorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitConstructorCall, reason: avoid collision after fix types in other method */
    public void visitConstructorCall2(IrConstructorCall irConstructorCall, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitConstructorCall(this, irConstructorCall, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression */
    public /* bridge */ /* synthetic */ Unit visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
        visitContainerExpression2(irContainerExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContainerExpression, reason: avoid collision after fix types in other method */
    public void visitContainerExpression2(IrContainerExpression irContainerExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitContainerExpression(this, irContainerExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue */
    public /* bridge */ /* synthetic */ Unit visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
        visitContinue2(irContinue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitContinue, reason: avoid collision after fix types in other method */
    public void visitContinue2(IrContinue irContinue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitContinue(this, irContinue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration */
    public /* bridge */ /* synthetic */ Unit visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclaration irDeclaration) {
        visitDeclaration2(irDeclarationBase, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclaration, reason: avoid collision after fix types in other method */
    public void visitDeclaration2(IrDeclarationBase irDeclarationBase, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDeclaration(this, irDeclarationBase, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference */
    public /* bridge */ /* synthetic */ Unit visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
        visitDeclarationReference2(irDeclarationReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDeclarationReference, reason: avoid collision after fix types in other method */
    public void visitDeclarationReference2(IrDeclarationReference irDeclarationReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
        visitDelegatingConstructorCall2(irDelegatingConstructorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: avoid collision after fix types in other method */
    public void visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
        visitDoWhileLoop2(irDoWhileLoop, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDoWhileLoop, reason: avoid collision after fix types in other method */
    public void visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
        visitDynamicExpression2(irDynamicExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicExpression2(IrDynamicExpression irDynamicExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
        visitDynamicMemberExpression2(irDynamicMemberExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression */
    public /* bridge */ /* synthetic */ Unit visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
        visitDynamicOperatorExpression2(irDynamicOperatorExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: avoid collision after fix types in other method */
    public void visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    public /* bridge */ /* synthetic */ Unit visitElement2(IrElement irElement, IrDeclaration irDeclaration) {
        visitElement2(irElement, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch */
    public /* bridge */ /* synthetic */ Unit visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
        visitElseBranch2(irElseBranch, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitElseBranch, reason: avoid collision after fix types in other method */
    public void visitElseBranch2(IrElseBranch irElseBranch, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitElseBranch(this, irElseBranch, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall */
    public /* bridge */ /* synthetic */ Unit visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
        visitEnumConstructorCall2(irEnumConstructorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: avoid collision after fix types in other method */
    public void visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry */
    public /* bridge */ /* synthetic */ Unit visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
        visitEnumEntry2(irEnumEntry, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitEnumEntry, reason: avoid collision after fix types in other method */
    public void visitEnumEntry2(IrEnumEntry irEnumEntry, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitEnumEntry(this, irEnumEntry, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
        visitErrorCallExpression2(irErrorCallExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorCallExpression, reason: avoid collision after fix types in other method */
    public void visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration */
    public /* bridge */ /* synthetic */ Unit visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
        visitErrorDeclaration2(irErrorDeclaration, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorDeclaration, reason: avoid collision after fix types in other method */
    public void visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression */
    public /* bridge */ /* synthetic */ Unit visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
        visitErrorExpression2(irErrorExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitErrorExpression, reason: avoid collision after fix types in other method */
    public void visitErrorExpression2(IrErrorExpression irErrorExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitErrorExpression(this, irErrorExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression */
    public /* bridge */ /* synthetic */ Unit visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
        visitExpression2(irExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpression, reason: avoid collision after fix types in other method */
    public void visitExpression2(IrExpression irExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitExpression(this, irExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody */
    public /* bridge */ /* synthetic */ Unit visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
        visitExpressionBody2(irExpressionBody, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExpressionBody, reason: avoid collision after fix types in other method */
    public void visitExpressionBody2(IrExpressionBody irExpressionBody, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitExpressionBody(this, irExpressionBody, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
        visitExternalPackageFragment2(irExternalPackageFragment, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: avoid collision after fix types in other method */
    public void visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField */
    public /* bridge */ /* synthetic */ Unit visitField2(IrField irField, IrDeclaration irDeclaration) {
        visitField2(irField, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitField, reason: avoid collision after fix types in other method */
    public void visitField2(IrField irField, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitField(this, irField, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess */
    public /* bridge */ /* synthetic */ Unit visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
        visitFieldAccess2(irFieldAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFieldAccess, reason: avoid collision after fix types in other method */
    public void visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile */
    public /* bridge */ /* synthetic */ Unit visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
        visitFile2(irFile, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFile, reason: avoid collision after fix types in other method */
    public void visitFile2(IrFile irFile, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFile(this, irFile, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction */
    public /* bridge */ /* synthetic */ Unit visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
        visitFunction2(irFunction, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunction, reason: avoid collision after fix types in other method */
    public void visitFunction2(IrFunction irFunction, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFunction(this, irFunction, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess */
    public /* bridge */ /* synthetic */ Unit visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, IrDeclaration irDeclaration) {
        visitFunctionAccess2(irFunctionAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression */
    public /* bridge */ /* synthetic */ Unit visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
        visitFunctionExpression2(irFunctionExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionExpression, reason: avoid collision after fix types in other method */
    public void visitFunctionExpression2(IrFunctionExpression irFunctionExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
        visitFunctionReference2(irFunctionReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitFunctionReference, reason: avoid collision after fix types in other method */
    public void visitFunctionReference2(IrFunctionReference irFunctionReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitFunctionReference(this, irFunctionReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass */
    public /* bridge */ /* synthetic */ Unit visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
        visitGetClass2(irGetClass, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetClass, reason: avoid collision after fix types in other method */
    public void visitGetClass2(IrGetClass irGetClass, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitGetClass(this, irGetClass, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue */
    public /* bridge */ /* synthetic */ Unit visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
        visitGetEnumValue2(irGetEnumValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetEnumValue, reason: avoid collision after fix types in other method */
    public void visitGetEnumValue2(IrGetEnumValue irGetEnumValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField */
    public /* bridge */ /* synthetic */ Unit visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
        visitGetField2(irGetField, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetField, reason: avoid collision after fix types in other method */
    public void visitGetField2(IrGetField irGetField, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitGetField(this, irGetField, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue */
    public /* bridge */ /* synthetic */ Unit visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
        visitGetObjectValue2(irGetObjectValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetObjectValue, reason: avoid collision after fix types in other method */
    public void visitGetObjectValue2(IrGetObjectValue irGetObjectValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue */
    public /* bridge */ /* synthetic */ Unit visitGetValue2(IrGetValue irGetValue, IrDeclaration irDeclaration) {
        visitGetValue2(irGetValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitGetValue, reason: avoid collision after fix types in other method */
    public void visitGetValue2(IrGetValue irGetValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitGetValue(this, irGetValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall */
    public /* bridge */ /* synthetic */ Unit visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
        visitInstanceInitializerCall2(irInstanceInitializerCall, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: avoid collision after fix types in other method */
    public void visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclaration irDeclaration) {
        visitLocalDelegatedProperty2(irLocalDelegatedProperty, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
        visitLocalDelegatedPropertyReference2(irLocalDelegatedPropertyReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: avoid collision after fix types in other method */
    public void visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop */
    public /* bridge */ /* synthetic */ Unit visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
        visitLoop2(irLoop, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitLoop, reason: avoid collision after fix types in other method */
    public void visitLoop2(IrLoop irLoop, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitLoop(this, irLoop, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Unit visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, IrDeclaration irDeclaration) {
        visitMemberAccess2((IrMemberAccessExpression<?>) irMemberAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public void visitMemberAccess2(IrMemberAccessExpression<?> irMemberAccessExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment */
    public /* bridge */ /* synthetic */ Unit visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
        visitModuleFragment2(irModuleFragment, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitModuleFragment, reason: avoid collision after fix types in other method */
    public void visitModuleFragment2(IrModuleFragment irModuleFragment, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitModuleFragment(this, irModuleFragment, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment */
    public /* bridge */ /* synthetic */ Unit visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
        visitPackageFragment2(irPackageFragment, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPackageFragment, reason: avoid collision after fix types in other method */
    public void visitPackageFragment2(IrPackageFragment irPackageFragment, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitPackageFragment(this, irPackageFragment, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty */
    public /* bridge */ /* synthetic */ Unit visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) {
        visitProperty2(irProperty, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitProperty, reason: avoid collision after fix types in other method */
    public void visitProperty2(IrProperty irProperty, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitProperty(this, irProperty, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference */
    public /* bridge */ /* synthetic */ Unit visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
        visitPropertyReference2(irPropertyReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitPropertyReference, reason: avoid collision after fix types in other method */
    public void visitPropertyReference2(IrPropertyReference irPropertyReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitPropertyReference(this, irPropertyReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference */
    public /* bridge */ /* synthetic */ Unit visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclaration irDeclaration) {
        visitRawFunctionReference2(irRawFunctionReference, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitRawFunctionReference, reason: avoid collision after fix types in other method */
    public void visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn */
    public /* bridge */ /* synthetic */ Unit visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
        visitReturn2(irReturn, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitReturn, reason: avoid collision after fix types in other method */
    public void visitReturn2(IrReturn irReturn, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitReturn(this, irReturn, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript */
    public /* bridge */ /* synthetic */ Unit visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
        visitScript2(irScript, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitScript, reason: avoid collision after fix types in other method */
    public void visitScript2(IrScript irScript, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitScript(this, irScript, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField */
    public /* bridge */ /* synthetic */ Unit visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
        visitSetField2(irSetField, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetField, reason: avoid collision after fix types in other method */
    public void visitSetField2(IrSetField irSetField, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSetField(this, irSetField, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue */
    public /* bridge */ /* synthetic */ Unit visitSetValue2(IrSetValue irSetValue, IrDeclaration irDeclaration) {
        visitSetValue2(irSetValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSetValue, reason: avoid collision after fix types in other method */
    public void visitSetValue2(IrSetValue irSetValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSetValue(this, irSetValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction */
    public /* bridge */ /* synthetic */ Unit visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
        visitSimpleFunction2(irSimpleFunction, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSimpleFunction, reason: avoid collision after fix types in other method */
    public void visitSimpleFunction2(IrSimpleFunction irSimpleFunction, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference */
    public /* bridge */ /* synthetic */ Unit visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
        visitSingletonReference2(irGetSingletonValue, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSingletonReference, reason: avoid collision after fix types in other method */
    public void visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement */
    public /* bridge */ /* synthetic */ Unit visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
        visitSpreadElement2(irSpreadElement, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSpreadElement, reason: avoid collision after fix types in other method */
    public void visitSpreadElement2(IrSpreadElement irSpreadElement, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSpreadElement(this, irSpreadElement, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation */
    public /* bridge */ /* synthetic */ Unit visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclaration irDeclaration) {
        visitStringConcatenation2(irStringConcatenation, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitStringConcatenation, reason: avoid collision after fix types in other method */
    public void visitStringConcatenation2(IrStringConcatenation irStringConcatenation, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression */
    public /* bridge */ /* synthetic */ Unit visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
        visitSuspendableExpression2(irSuspendableExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspendableExpression, reason: avoid collision after fix types in other method */
    public void visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint */
    public /* bridge */ /* synthetic */ Unit visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
        visitSuspensionPoint2(irSuspensionPoint, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSuspensionPoint, reason: avoid collision after fix types in other method */
    public void visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody */
    public /* bridge */ /* synthetic */ Unit visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
        visitSyntheticBody2(irSyntheticBody, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitSyntheticBody, reason: avoid collision after fix types in other method */
    public void visitSyntheticBody2(IrSyntheticBody irSyntheticBody, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow */
    public /* bridge */ /* synthetic */ Unit visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
        visitThrow2(irThrow, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitThrow, reason: avoid collision after fix types in other method */
    public void visitThrow2(IrThrow irThrow, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitThrow(this, irThrow, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry */
    public /* bridge */ /* synthetic */ Unit visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
        visitTry2(irTry, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTry, reason: avoid collision after fix types in other method */
    public void visitTry2(IrTry irTry, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitTry(this, irTry, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias */
    public /* bridge */ /* synthetic */ Unit visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
        visitTypeAlias2(irTypeAlias, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: avoid collision after fix types in other method */
    public void visitTypeAlias2(IrTypeAlias irTypeAlias, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitTypeAlias(this, irTypeAlias, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator */
    public /* bridge */ /* synthetic */ Unit visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
        visitTypeOperator2(irTypeOperatorCall, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeOperator, reason: avoid collision after fix types in other method */
    public void visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter */
    public /* bridge */ /* synthetic */ Unit visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
        visitTypeParameter2(irTypeParameter, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitTypeParameter, reason: avoid collision after fix types in other method */
    public void visitTypeParameter2(IrTypeParameter irTypeParameter, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitTypeParameter(this, irTypeParameter, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess */
    public /* bridge */ /* synthetic */ Unit visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
        visitValueAccess2(irValueAccessExpression, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueAccess, reason: avoid collision after fix types in other method */
    public void visitValueAccess2(IrValueAccessExpression irValueAccessExpression, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitValueAccess(this, irValueAccessExpression, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter */
    public /* bridge */ /* synthetic */ Unit visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
        visitValueParameter2(irValueParameter, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitValueParameter, reason: avoid collision after fix types in other method */
    public void visitValueParameter2(IrValueParameter irValueParameter, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitValueParameter(this, irValueParameter, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg */
    public /* bridge */ /* synthetic */ Unit visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
        visitVararg2(irVararg, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVararg, reason: avoid collision after fix types in other method */
    public void visitVararg2(IrVararg irVararg, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitVararg(this, irVararg, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable */
    public /* bridge */ /* synthetic */ Unit visitVariable2(IrVariable irVariable, IrDeclaration irDeclaration) {
        visitVariable2(irVariable, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitVariable, reason: avoid collision after fix types in other method */
    public void visitVariable2(IrVariable irVariable, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitVariable(this, irVariable, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen */
    public /* bridge */ /* synthetic */ Unit visitWhen2(IrWhen irWhen, IrDeclaration irDeclaration) {
        visitWhen2(irWhen, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhen, reason: avoid collision after fix types in other method */
    public void visitWhen2(IrWhen irWhen, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitWhen(this, irWhen, irDeclaration);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop */
    public /* bridge */ /* synthetic */ Unit visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
        visitWhileLoop2(irWhileLoop, irDeclaration);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: visitWhileLoop, reason: avoid collision after fix types in other method */
    public void visitWhileLoop2(IrWhileLoop irWhileLoop, IrDeclaration irDeclaration) {
        IrElementVisitor.DefaultImpls.visitWhileLoop(this, irWhileLoop, irDeclaration);
    }

    /* JADX INFO: renamed from: visitElement, reason: avoid collision after fix types in other method */
    public void visitElement2(IrElement element, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(element, "element");
        InlineCallableReferenceToLambdaVisitor inlineCallableReferenceToLambdaVisitor = this;
        IrDeclaration irDeclaration = element instanceof IrDeclaration ? (IrDeclaration) element : null;
        if (irDeclaration != null) {
            data = irDeclaration;
        }
        element.acceptChildren(inlineCallableReferenceToLambdaVisitor, data);
    }

    /* JADX INFO: renamed from: visitFunctionAccess, reason: avoid collision after fix types in other method */
    public void visitFunctionAccess2(IrFunctionAccessExpression expression, IrDeclaration data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        expression.acceptChildren(this, data);
        IrFunction owner = expression.getSymbol().getOwner();
        if (JvmIrInlineUtilsKt.isInlineFunctionCall(owner, this.context)) {
            for (IrValueParameter irValueParameter : owner.getValueParameters()) {
                if (JvmIrInlineUtilsKt.isInlineParameter(irValueParameter)) {
                    int index = irValueParameter.getIndex();
                    IrExpression valueArgument = expression.getValueArgument(irValueParameter.getIndex());
                    expression.putValueArgument(index, valueArgument != null ? transform(valueArgument, data) : null);
                }
            }
        }
    }

    private final IrExpression transform(IrExpression irExpression, IrDeclaration irDeclaration) {
        IrSimpleFunction irSimpleFunctionWrapFunction;
        if (irExpression instanceof IrBlock) {
            IrBlock irBlock = (IrBlock) irExpression;
            if (InlineCallableReferenceToLambdaKt.isInlinable(irBlock.getOrigin())) {
                Object objLast = CollectionsKt.last((List<? extends Object>) irBlock.getStatements());
                Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrFunctionReference");
                IrFunctionReference irFunctionReference = (IrFunctionReference) objLast;
                irFunctionReference.getSymbol().getOwner().setOrigin(JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE);
                irBlock.getStatements().set(CollectionsKt.getLastIndex(irBlock.getStatements()), InlineCallableReferenceToLambdaKt.replaceOrigin(irFunctionReference, JvmLoweredStatementOrigin.INLINE_LAMBDA.INSTANCE));
                return irExpression;
            }
        }
        if (irExpression instanceof IrFunctionReference) {
            IrCallableReference<?> irCallableReference = (IrCallableReference) irExpression;
            IrSimpleFunction irSimpleFunctionWrapFunction2 = wrapFunction(irCallableReference, ((IrFunctionReference) irExpression).getSymbol().getOwner());
            Intrinsics.checkNotNull(irDeclaration);
            return toLambda(irSimpleFunctionWrapFunction2, irCallableReference, irDeclaration);
        }
        if (!(irExpression instanceof IrPropertyReference)) {
            return irExpression;
        }
        IrPropertyReference irPropertyReference = (IrPropertyReference) irExpression;
        if (irPropertyReference.getTypeArgumentsCount() > 0) {
            IrDeclarationOrigin origin = irPropertyReference.getSymbol().getOwner().getOrigin();
            if (Intrinsics.areEqual(origin, IrDeclarationOrigin.SYNTHETIC_JAVA_PROPERTY_DELEGATE.INSTANCE) || Intrinsics.areEqual(origin, IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) {
                return irExpression;
            }
        }
        if (irPropertyReference.getField() != null) {
            IrFieldSymbol field = irPropertyReference.getField();
            Intrinsics.checkNotNull(field);
            irSimpleFunctionWrapFunction = wrapField(irPropertyReference, field.getOwner());
        } else {
            IrSimpleFunctionSymbol getter = irPropertyReference.getGetter();
            Intrinsics.checkNotNull(getter);
            irSimpleFunctionWrapFunction = wrapFunction((IrCallableReference) irExpression, getter.getOwner());
        }
        Intrinsics.checkNotNull(irDeclaration);
        return toLambda(irSimpleFunctionWrapFunction, (IrCallableReference) irExpression, irDeclaration);
    }

    private final IrSimpleFunction wrapField(IrPropertyReference irPropertyReference, IrField irField) {
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrElementBuilderKt.setSourceRange(irFunctionBuilder, irPropertyReference);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE);
        Name nameIdentifier = Name.identifier(InlineCallableReferenceToLambdaKt.STUB_FOR_INLINING);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        irFunctionBuilder.setVisibility(LOCAL);
        irFunctionBuilder.setReturnType(irField.getType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, (Object) null);
        IrExpression dispatchReceiver = irPropertyReference.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = irPropertyReference.getExtensionReceiver();
        }
        IrGetValueImpl irGetValueImplIrGet = null;
        if (!irField.getIsStatic()) {
            if (dispatchReceiver != null) {
                irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, IrUtilsKt.addExtensionReceiver$default(irSimpleFunctionBuildFunction, dispatchReceiver.getType(), null, 2, null));
            } else {
                irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder$default, DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "receiver", org.jetbrains.kotlin.ir.util.IrUtilsKt.getDefaultType(org.jetbrains.kotlin.ir.util.IrUtilsKt.getParentAsClass(irField)), (IrDeclarationOrigin) null, 4, (Object) null));
            }
        }
        DeclarationIrBuilder declarationIrBuilder = declarationIrBuilderCreateIrBuilder$default;
        irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(declarationIrBuilder, ExpressionHelpersKt.irGetField$default(declarationIrBuilder, irGetValueImplIrGet, irField, null, 4, null)));
        return irSimpleFunctionBuildFunction;
    }

    private final IrSimpleFunction wrapFunction(IrCallableReference<?> irCallableReference, IrFunction irFunction) {
        IrValueParameter extensionReceiverParameter;
        IrGetValueImpl irGetValueImplIrGet;
        IrFactory irFactory = this.context.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        IrElementBuilderKt.setSourceRange(irFunctionBuilder, irCallableReference);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE);
        Name nameIdentifier = Name.identifier(InlineCallableReferenceToLambdaKt.STUB_FOR_INLINING);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
        Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
        irFunctionBuilder.setVisibility(LOCAL);
        IrType type = irCallableReference.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        Object objLast = CollectionsKt.last((List<? extends Object>) ((IrSimpleType) type).getArguments());
        Intrinsics.checkNotNull(objLast, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
        irFunctionBuilder.setReturnType(((IrTypeProjection) objLast).getType());
        irFunctionBuilder.setSuspend(AdditionalIrUtilsKt.isSuspend(irFunction));
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder = JvmIrBuilderKt.createJvmIrBuilder(this.context, irSimpleFunctionBuildFunction.getSymbol(), irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset());
        IrType type2 = irCallableReference.getType();
        Intrinsics.checkNotNull(type2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        List<IrTypeArgument> listDropLast = CollectionsKt.dropLast(((IrSimpleType) type2).getArguments(), 1);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listDropLast, 10));
        for (IrTypeArgument irTypeArgument : listDropLast) {
            Intrinsics.checkNotNull(irTypeArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeProjection");
            arrayList.add(((IrTypeProjection) irTypeArgument).getType());
        }
        ArrayList arrayList2 = arrayList;
        IrExpression dispatchReceiver = irCallableReference.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = irCallableReference.getExtensionReceiver();
        }
        IrDeclarationOrigin irDeclarationOrigin = null;
        if (irCallableReference.getDispatchReceiver() != null) {
            extensionReceiverParameter = irFunction.getDispatchReceiverParameter();
        } else {
            extensionReceiverParameter = irCallableReference.getExtensionReceiver() != null ? irFunction.getExtensionReceiverParameter() : null;
        }
        JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder;
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder, irFunction.getSymbol(), irSimpleFunctionBuildFunction.getReturnType());
        IrFunctionAccessExpression irFunctionAccessExpression = irFunctionAccessExpressionIrCall;
        int i = 2;
        IrExpressionsKt.copyTypeArgumentsFrom$default(irFunctionAccessExpression, irCallableReference, 0, 2, null);
        for (IrValueParameter irValueParameter : org.jetbrains.kotlin.ir.util.IrUtilsKt.getExplicitParameters(irFunction)) {
            int size = irSimpleFunctionBuildFunction.getValueParameters().size();
            if (Intrinsics.areEqual(extensionReceiverParameter, irValueParameter)) {
                Intrinsics.checkNotNull(dispatchReceiver);
                irGetValueImplIrGet = ExpressionHelpersKt.irGet(jvmIrBuilder, IrUtilsKt.addExtensionReceiver$default(irSimpleFunctionBuildFunction, dispatchReceiver.getType(), irDeclarationOrigin, i, irDeclarationOrigin));
            } else if (AdditionalIrUtilsKt.isVararg(irValueParameter) && size < arrayList2.size() && Intrinsics.areEqual(irValueParameter.getType(), arrayList2.get(size))) {
                irGetValueImplIrGet = ExpressionHelpersKt.irGet(jvmIrBuilder, DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "p" + size, (IrType) arrayList2.get(size), (IrDeclarationOrigin) null, 4, (Object) null));
            } else if (!AdditionalIrUtilsKt.isVararg(irValueParameter) || (size >= arrayList2.size() && org.jetbrains.kotlin.ir.util.IrUtilsKt.hasDefaultValue(irValueParameter))) {
                irGetValueImplIrGet = size >= arrayList2.size() ? null : ExpressionHelpersKt.irGet(jvmIrBuilder, DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "p" + size, (IrType) arrayList2.get(size), (IrDeclarationOrigin) null, 4, (Object) null));
            } else {
                IrArrayBuilder irArrayBuilder = new IrArrayBuilder(jvmIrBuilderCreateJvmIrBuilder, irValueParameter.getType());
                int size2 = arrayList2.size();
                while (size < size2) {
                    irArrayBuilder.unaryPlus(ExpressionHelpersKt.irGet(jvmIrBuilder, DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, "p" + size, (IrType) arrayList2.get(size), (IrDeclarationOrigin) null, 4, (Object) null)));
                    size++;
                }
                irGetValueImplIrGet = irArrayBuilder.build();
            }
            if (irGetValueImplIrGet != null) {
                IrExpressionsKt.putArgument(irFunctionAccessExpression, irFunction, irValueParameter, irGetValueImplIrGet);
            }
            irDeclarationOrigin = null;
            i = 2;
        }
        irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilder, irFunctionAccessExpressionIrCall));
        return irSimpleFunctionBuildFunction;
    }

    private final IrContainerExpression toLambda(IrSimpleFunction irSimpleFunction, IrCallableReference<?> irCallableReference, IrDeclaration irDeclaration) {
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(this.context, irDeclaration.getSymbol(), 0, 0, 6, (Object) null);
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irSimpleFunction.getStartOffset(), irSimpleFunction.getEndOffset(), IrStatementOrigin.LAMBDA.INSTANCE, null, false, 64, null);
        irSimpleFunction.setParent(ExpressionHelpersKt.getParent(irBlockBuilder));
        irBlockBuilder.unaryPlus(irSimpleFunction);
        IrFunctionReferenceImpl irFunctionReferenceImplFromSymbolOwner = IrFunctionReferenceImpl.INSTANCE.fromSymbolOwner(irBlockBuilder.getStartOffset(), irBlockBuilder.getEndOffset(), irCallableReference.getType(), irSimpleFunction.getSymbol(), 0, null, JvmLoweredStatementOrigin.INLINE_LAMBDA.INSTANCE);
        IrDeclarationsKt.copyAttributes(irFunctionReferenceImplFromSymbolOwner, irCallableReference);
        IrExpression dispatchReceiver = irCallableReference.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = irCallableReference.getExtensionReceiver();
        }
        irFunctionReferenceImplFromSymbolOwner.setExtensionReceiver(dispatchReceiver);
        irBlockBuilder.unaryPlus(irFunctionReferenceImplFromSymbolOwner);
        return irBlockBuilder.getIrBlockBody();
    }
}
