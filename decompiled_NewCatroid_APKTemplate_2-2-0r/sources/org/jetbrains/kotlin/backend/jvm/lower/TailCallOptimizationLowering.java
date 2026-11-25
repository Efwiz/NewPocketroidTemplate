package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
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
import org.jetbrains.kotlin.ir.expressions.IrInlinedFunctionBlock;
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
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrThrow;
import org.jetbrains.kotlin.ir.expressions.IrTry;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: TailCallOptimizationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\f\u0010\t\u001a\u00020\n*\u00020\nH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/TailCallOptimizationLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "coerceToUnit", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TailCallOptimizationLowering implements FileLoweringPass {
    private final JvmBackendContext context;

    public TailCallOptimizationLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        irFile.transformChildren(new IrElementTransformer<TailCallOptimizationData>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.TailCallOptimizationLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
            public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBlock2(IrBlock irBlock, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBlockBody2(IrBlockBody irBlockBody, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitBody2(IrBody irBody, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitBody(this, irBody, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
            public IrBranch visitBranch2(IrBranch irBranch, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreak2(IrBreak irBreak, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (TailCallOptimizationData) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
                return visitCallableReference((IrCallableReference<?>) irCallableReference, (TailCallOptimizationData) obj);
            }

            public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
            public IrCatch visitCatch2(IrCatch irCatch, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
            public IrStatement visitClass2(IrClass irClass, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitClass(this, irClass, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitClassReference2(IrClassReference irClassReference, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
            public IrExpression visitComposite2(IrComposite irComposite, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (TailCallOptimizationData) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
                return visitConst((IrConst<?>) irConst, (TailCallOptimizationData) obj);
            }

            public IrExpression visitConst(IrConst<?> irConst, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitConst(this, irConst, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
            public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
            public IrStatement visitConstructor2(IrConstructor irConstructor, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContinue2(IrContinue irContinue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
            public IrElement visitElement2(IrElement irElement, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitElement(this, irElement, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
            public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
            public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
            public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
            public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitExpression2(IrExpression irExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
            public IrStatement visitField2(IrField irField, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitField(this, irField, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
            public IrFile visitFile2(IrFile irFile2, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitFile(this, irFile2, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitFunction2(IrFunction irFunction, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetClass2(IrGetClass irGetClass, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetField2(IrGetField irGetField, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitGetValue2(IrGetValue irGetValue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
            public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitLoop2(IrLoop irLoop, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (TailCallOptimizationData) obj);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
            public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (TailCallOptimizationData) obj);
            }

            public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
            public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
            public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
            public IrStatement visitProperty2(IrProperty irProperty, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
            public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
            public IrExpression visitReturn2(IrReturn irReturn, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
            public IrStatement visitScript2(IrScript irScript, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitScript(this, irScript, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetField2(IrSetField irSetField, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSetValue2(IrSetValue irSetValue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
            public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
            public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
            public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
            public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
            public IrExpression visitThrow2(IrThrow irThrow, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTry2(IrTry irTry, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitTry(this, irTry, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
            public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
            public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
            public IrStatement visitValueParameter2(IrValueParameter irValueParameter, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
            public IrExpression visitVararg2(IrVararg irVararg, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
            public IrStatement visitVariable2(IrVariable irVariable, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhen2(IrWhen irWhen, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
            public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, TailCallOptimizationData tailCallOptimizationData) {
                return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, tailCallOptimizationData);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
            public IrStatement visitSimpleFunction2(IrSimpleFunction declaration, TailCallOptimizationData data) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, declaration, declaration.getIsSuspend() ? new TailCallOptimizationData(declaration) : null);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
            public IrExpression visitContainerExpression2(IrContainerExpression expression, TailCallOptimizationData data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if ((expression instanceof IrInlinedFunctionBlock) && IrInlineUtilsKt.isFunctionInlining((IrInlinedFunctionBlock) expression)) {
                    return expression;
                }
                return IrElementTransformer.DefaultImpls.visitContainerExpression(this, expression, data);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
            public IrExpression visitCall2(IrCall expression, TailCallOptimizationData data) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                IrElement irElementVisitCall = IrElementTransformer.DefaultImpls.visitCall(this, expression, data);
                Intrinsics.checkNotNull(irElementVisitCall, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                IrExpression irExpressionCoerceToUnit = (IrExpression) irElementVisitCall;
                if (data == null || !data.getTailCalls().contains(expression)) {
                    return irExpressionCoerceToUnit;
                }
                int endOffset = data.getFunction().getEndOffset();
                int endOffset2 = data.getFunction().getEndOffset();
                IrType nothingType = TailCallOptimizationLowering.this.context.getIrBuiltIns().getNothingType();
                IrSimpleFunctionSymbol symbol = data.getFunction().getSymbol();
                if (data.getReturnsUnit()) {
                    irExpressionCoerceToUnit = TailCallOptimizationLowering.this.coerceToUnit(irExpressionCoerceToUnit);
                }
                return new IrReturnImpl(endOffset, endOffset2, nothingType, symbol, irExpressionCoerceToUnit);
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression coerceToUnit(IrExpression irExpression) {
        return Intrinsics.areEqual(irExpression.getType(), this.context.getIrBuiltIns().getUnitType()) ? irExpression : new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), this.context.getIrBuiltIns().getUnitType(), IrTypeOperator.IMPLICIT_COERCION_TO_UNIT, this.context.getIrBuiltIns().getUnitType(), irExpression);
    }
}
