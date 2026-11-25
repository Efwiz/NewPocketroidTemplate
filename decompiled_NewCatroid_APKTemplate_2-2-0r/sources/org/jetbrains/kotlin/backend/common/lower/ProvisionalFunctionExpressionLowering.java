package org.jetbrains.kotlin.backend.common.lower;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
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
import org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall;
import org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrFunctionReferenceImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: ProvisionalFunctionExpressionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0002H\u0016¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/ProvisionalFunctionExpressionLowering;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/backend/common/lower/ProvisionalFunctionExpressionLoweringContext;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "()V", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "visitCall", "Lorg/jetbrains/kotlin/ir/IrElement;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "data", "visitContainerExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitVariable", "Lorg/jetbrains/kotlin/ir/IrStatement;", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProvisionalFunctionExpressionLowering implements BodyLoweringPass, IrElementTransformer<ProvisionalFunctionExpressionLoweringContext> {
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBlock2(IrBlock irBlock, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBlockBody2(IrBlockBody irBlockBody, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBody2(IrBody irBody, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitBody(this, irBody, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public IrBranch visitBranch2(IrBranch irBranch, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreak2(IrBreak irBreak, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (ProvisionalFunctionExpressionLoweringContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (ProvisionalFunctionExpressionLoweringContext) obj);
    }

    public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public IrCatch visitCatch2(IrCatch irCatch, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public IrStatement visitClass2(IrClass irClass, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitClass(this, irClass, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitClassReference2(IrClassReference irClassReference, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public IrExpression visitComposite2(IrComposite irComposite, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitComposite(this, irComposite, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (ProvisionalFunctionExpressionLoweringContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (ProvisionalFunctionExpressionLoweringContext) obj);
    }

    public IrExpression visitConst(IrConst<?> irConst, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitConst(this, irConst, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public IrStatement visitConstructor2(IrConstructor irConstructor, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContinue2(IrContinue irContinue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
    public IrElement visitElement2(IrElement irElement, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitElement(this, irElement, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitExpression2(IrExpression irExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public IrStatement visitField2(IrField irField, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitField(this, irField, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public IrFile visitFile2(IrFile irFile, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitFile(this, irFile, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitFunction2(IrFunction irFunction, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetClass2(IrGetClass irGetClass, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetField2(IrGetField irGetField, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetValue2(IrGetValue irGetValue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitGetValue(this, irGetValue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitLoop2(IrLoop irLoop, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (ProvisionalFunctionExpressionLoweringContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (ProvisionalFunctionExpressionLoweringContext) obj);
    }

    public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public IrModuleFragment visitModuleFragment2(IrModuleFragment irModuleFragment, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitProperty2(IrProperty irProperty, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public IrExpression visitReturn2(IrReturn irReturn, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public IrStatement visitScript2(IrScript irScript, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitScript(this, irScript, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetField2(IrSetField irSetField, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetValue2(IrSetValue irSetValue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public IrExpression visitThrow2(IrThrow irThrow, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTry2(IrTry irTry, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitTry(this, irTry, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitValueParameter2(IrValueParameter irValueParameter, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public IrExpression visitVararg2(IrVararg irVararg, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhen2(IrWhen irWhen, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, ProvisionalFunctionExpressionLoweringContext provisionalFunctionExpressionLoweringContext) {
        return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, provisionalFunctionExpressionLoweringContext);
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        irBody.transformChildren(this, new ProvisionalFunctionExpressionLoweringContext(null, null, 3, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitCall2(IrCall expression, ProvisionalFunctionExpressionLoweringContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return IrElementTransformer.DefaultImpls.visitCall(this, expression, new ProvisionalFunctionExpressionLoweringContext(Integer.valueOf(expression.getStartOffset()), Integer.valueOf(expression.getEndOffset())));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public IrStatement visitVariable2(IrVariable declaration, ProvisionalFunctionExpressionLoweringContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        return IrElementTransformer.DefaultImpls.visitVariable(this, declaration, new ProvisionalFunctionExpressionLoweringContext(Integer.valueOf(declaration.getStartOffset()), Integer.valueOf(declaration.getEndOffset())));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContainerExpression2(IrContainerExpression expression, ProvisionalFunctionExpressionLoweringContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        return !(expression instanceof IrReturnableBlock) ? IrElementTransformer.DefaultImpls.visitContainerExpression(this, expression, data) : IrElementTransformer.DefaultImpls.visitContainerExpression(this, expression, new ProvisionalFunctionExpressionLoweringContext(null, null, 3, null));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionExpression2(IrFunctionExpression expression, ProvisionalFunctionExpressionLoweringContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        expression.transformChildren(this, new ProvisionalFunctionExpressionLoweringContext(null, null, 3, null));
        Integer startOffset = data.getStartOffset();
        int iIntValue = startOffset != null ? startOffset.intValue() : expression.getStartOffset();
        Integer endOffset = data.getEndOffset();
        int iIntValue2 = endOffset != null ? endOffset.intValue() : expression.getEndOffset();
        IrType type = expression.getType();
        IrStatementOrigin origin = expression.getOrigin();
        IrSimpleFunction function = expression.getFunction();
        int i = iIntValue;
        return new IrBlockImpl(i, iIntValue2, type, origin, CollectionsKt.listOf((Object[]) new IrElement[]{function, IrDeclarationsKt.copyAttributes(new IrFunctionReferenceImpl(i, iIntValue2, type, function.getSymbol(), 0, function.getValueParameters().size(), null, origin), expression)}));
    }
}
