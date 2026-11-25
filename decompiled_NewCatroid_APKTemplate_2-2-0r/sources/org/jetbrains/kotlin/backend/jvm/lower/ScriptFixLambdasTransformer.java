package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
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
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0013\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u001a2\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u001c2\u0006\u0010\u000f\u001a\u00020\u0002H\u0016J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0013\u001a\u00020\u001e2\u0006\u0010\u000f\u001a\u00020\u0002H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptFixLambdasTransformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptFixLambdasTransformerContext;", "irScriptClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "getIrScriptClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "unexpectedElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "expression", "data", "visitElement", "visitExternalPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "declaration", "visitFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "visitGetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "visitModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "visitScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ScriptFixLambdasTransformer implements IrElementTransformer<ScriptFixLambdasTransformerContext> {
    private final IrClass irScriptClass;

    public ScriptFixLambdasTransformer(IrClass irScriptClass) {
        Intrinsics.checkNotNullParameter(irScriptClass, "irScriptClass");
        this.irScriptClass = irScriptClass;
    }

    public final IrClass getIrScriptClass() {
        return this.irScriptClass;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public IrStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBlock2(IrBlock irBlock, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBlock(this, irBlock, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBlockBody2(IrBlockBody irBlockBody, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitBody2(IrBody irBody, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBody(this, irBody, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public IrBranch visitBranch2(IrBranch irBranch, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBranch(this, irBranch, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreak2(IrBreak irBreak, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBreak(this, irBreak, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitBreakContinue2(IrBreakContinue irBreakContinue, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitCall2(IrCall irCall, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitCall(this, irCall, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (ScriptFixLambdasTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCallableReference, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitCallableReference2(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (ScriptFixLambdasTransformerContext) obj);
    }

    public IrElement visitCallableReference(IrCallableReference<?> irCallableReference, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public IrCatch visitCatch2(IrCatch irCatch, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitCatch(this, irCatch, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public IrStatement visitClass2(IrClass irClass, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitClass(this, irClass, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitClassReference2(IrClassReference irClassReference, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitClassReference(this, irClassReference, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (ScriptFixLambdasTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConst, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitConst2(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (ScriptFixLambdasTransformerContext) obj);
    }

    public IrExpression visitConst(IrConst<?> irConst, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConst(this, irConst, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantArray2(IrConstantArray irConstantArray, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantObject2(IrConstantObject irConstantObject, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public IrConstantValue visitConstantValue2(IrConstantValue irConstantValue, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public IrStatement visitConstructor2(IrConstructor irConstructor, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstructor(this, irConstructor, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitConstructorCall2(IrConstructorCall irConstructorCall, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContainerExpression2(IrContainerExpression irContainerExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitContinue2(IrContinue irContinue, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitContinue(this, irContinue, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDeclarationReference2(IrDeclarationReference irDeclarationReference, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicExpression2(IrDynamicExpression irDynamicExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public IrElseBranch visitElseBranch2(IrElseBranch irElseBranch, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public IrElement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public IrStatement visitEnumEntry2(IrEnumEntry irEnumEntry, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public IrStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitErrorExpression2(IrErrorExpression irErrorExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitExpression2(IrExpression irExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitExpression(this, irExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitExpressionBody2(IrExpressionBody irExpressionBody, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public IrStatement visitField2(IrField irField, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitField(this, irField, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public IrStatement visitFunction2(IrFunction irFunction, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunction(this, irFunction, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitFunctionReference2(IrFunctionReference irFunctionReference, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetClass2(IrGetClass irGetClass, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetClass(this, irGetClass, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetEnumValue2(IrGetEnumValue irGetEnumValue, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetField2(IrGetField irGetField, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetField(this, irGetField, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetObjectValue2(IrGetObjectValue irGetObjectValue, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public IrExpression visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitLoop2(IrLoop irLoop, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitLoop(this, irLoop, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (ScriptFixLambdasTransformerContext) obj);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitMemberAccess, reason: avoid collision after fix types in other method */
    public /* bridge */ /* synthetic */ IrElement visitMemberAccess2(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (ScriptFixLambdasTransformerContext) obj);
    }

    public IrElement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrElement visitPackageFragment2(IrPackageFragment irPackageFragment, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public IrStatement visitProperty2(IrProperty irProperty, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitProperty(this, irProperty, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public IrElement visitPropertyReference2(IrPropertyReference irPropertyReference, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public IrExpression visitReturn2(IrReturn irReturn, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitReturn(this, irReturn, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetField2(IrSetField irSetField, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSetField(this, irSetField, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSetValue2(IrSetValue irSetValue, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSetValue(this, irSetValue, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public IrSpreadElement visitSpreadElement2(IrSpreadElement irSpreadElement, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public IrExpression visitStringConcatenation2(IrStringConcatenation irStringConcatenation, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public IrExpression visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public IrBody visitSyntheticBody2(IrSyntheticBody irSyntheticBody, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public IrExpression visitThrow2(IrThrow irThrow, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitThrow(this, irThrow, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTry2(IrTry irTry, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitTry(this, irTry, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeAlias2(IrTypeAlias irTypeAlias, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public IrExpression visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitTypeParameter2(IrTypeParameter irTypeParameter, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public IrExpression visitValueAccess2(IrValueAccessExpression irValueAccessExpression, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public IrStatement visitValueParameter2(IrValueParameter irValueParameter, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public IrExpression visitVararg2(IrVararg irVararg, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitVararg(this, irVararg, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public IrStatement visitVariable2(IrVariable irVariable, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitVariable(this, irVariable, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhen2(IrWhen irWhen, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitWhen(this, irWhen, scriptFixLambdasTransformerContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public IrExpression visitWhileLoop2(IrWhileLoop irWhileLoop, ScriptFixLambdasTransformerContext scriptFixLambdasTransformerContext) {
        return IrElementTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, scriptFixLambdasTransformerContext);
    }

    private final Void unexpectedElement(IrElement element) {
        throw new IllegalArgumentException("Unsupported element type: " + element);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
    public IrElement visitElement2(IrElement element, ScriptFixLambdasTransformerContext data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(element);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public IrModuleFragment visitModuleFragment2(IrModuleFragment declaration, ScriptFixLambdasTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public IrExternalPackageFragment visitExternalPackageFragment2(IrExternalPackageFragment declaration, ScriptFixLambdasTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public IrFile visitFile2(IrFile declaration, ScriptFixLambdasTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public IrScript visitScript2(IrScript declaration, ScriptFixLambdasTransformerContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        unexpectedElement(declaration);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public IrExpression visitGetValue2(IrGetValue expression, ScriptFixLambdasTransformerContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (Intrinsics.areEqual(data.getValueParameterToReplaceWithScript(), expression.getSymbol().getOwner())) {
            int startOffset = expression.getStartOffset();
            int endOffset = expression.getEndOffset();
            IrType type = expression.getType();
            IrValueParameter thisReceiver = this.irScriptClass.getThisReceiver();
            Intrinsics.checkNotNull(thisReceiver);
            return IrElementTransformer.DefaultImpls.visitGetValue(this, new IrGetValueImpl(startOffset, endOffset, type, thisReceiver.getSymbol(), expression.getOrigin()), data);
        }
        return IrElementTransformer.DefaultImpls.visitGetValue(this, expression, data);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public IrSimpleFunction visitSimpleFunction2(IrSimpleFunction declaration, ScriptFixLambdasTransformerContext data) {
        IrStatement irStatementVisitSimpleFunction;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        if (data.getInsideTopLevelDestructuringDeclaration() && Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE)) {
            DescriptorVisibility LOCAL = DescriptorVisibilities.LOCAL;
            Intrinsics.checkNotNullExpressionValue(LOCAL, "LOCAL");
            declaration.setVisibility(LOCAL);
            IrValueParameter dispatchReceiverParameter = declaration.getDispatchReceiverParameter();
            if (Intrinsics.areEqual(dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null, IrUtilsKt.getDefaultType(this.irScriptClass))) {
                IrValueParameter dispatchReceiverParameter2 = declaration.getDispatchReceiverParameter();
                declaration.setDispatchReceiverParameter(null);
                data = ScriptFixLambdasTransformerContext.copy$default(data, false, dispatchReceiverParameter2, 1, null);
            }
            irStatementVisitSimpleFunction = IrElementTransformer.DefaultImpls.visitSimpleFunction(this, declaration, data);
        } else {
            irStatementVisitSimpleFunction = IrElementTransformer.DefaultImpls.visitSimpleFunction(this, declaration, data);
        }
        Intrinsics.checkNotNull(irStatementVisitSimpleFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
        return (IrSimpleFunction) irStatementVisitSimpleFunction;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformer, org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public IrComposite visitComposite2(IrComposite expression, ScriptFixLambdasTransformerContext data) {
        Object next;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (Intrinsics.areEqual(expression.getOrigin(), IrStatementOrigin.DESTRUCTURING_DECLARATION.INSTANCE)) {
            Iterator<T> it = expression.getStatements().iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!(next instanceof IrDeclaration));
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if (Intrinsics.areEqual(irDeclaration != null ? irDeclaration.getParent() : null, this.irScriptClass)) {
                data = ScriptFixLambdasTransformerContext.copy$default(data, true, null, 2, null);
            }
        }
        IrExpression irExpressionVisitComposite = IrElementTransformer.DefaultImpls.visitComposite(this, expression, data);
        Intrinsics.checkNotNull(irExpressionVisitComposite, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrComposite");
        return (IrComposite) irExpressionVisitComposite;
    }
}
