package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
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
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;

/* JADX INFO: compiled from: IrFunctionToJsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0003H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrFunctionToJsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "()V", "visitConstructor", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "context", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFunctionToJsTransformer implements BaseIrElementToJsNodeTransformer<JsFunction, JsGenerationContext> {
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public JsFunction visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public JsFunction visitBlock2(IrBlock irBlock, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlock(this, irBlock, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public JsFunction visitBlockBody2(IrBlockBody irBlockBody, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public JsFunction visitBody2(IrBody irBody, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBody(this, irBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public JsFunction visitBranch2(IrBranch irBranch, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBranch(this, irBranch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public JsFunction visitBreak2(IrBreak irBreak, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreak(this, irBreak, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public JsFunction visitBreakContinue2(IrBreakContinue irBreakContinue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public JsFunction visitCall2(IrCall irCall, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCall(this, irCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (JsGenerationContext) obj);
    }

    public JsFunction visitCallableReference(IrCallableReference<?> irCallableReference, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public JsFunction visitCatch2(IrCatch irCatch, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCatch(this, irCatch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public JsFunction visitClass2(IrClass irClass, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClass(this, irClass, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public JsFunction visitClassReference2(IrClassReference irClassReference, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClassReference(this, irClassReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public JsFunction visitComposite2(IrComposite irComposite, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitComposite(this, irComposite, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (JsGenerationContext) obj);
    }

    public JsFunction visitConst(IrConst<?> irConst, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConst(this, irConst, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public JsFunction visitConstantArray2(IrConstantArray irConstantArray, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public JsFunction visitConstantObject2(IrConstantObject irConstantObject, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public JsFunction visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public JsFunction visitConstantValue2(IrConstantValue irConstantValue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsFunction visitConstructorCall2(IrConstructorCall irConstructorCall, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitContainerExpression2(IrContainerExpression irContainerExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public JsFunction visitContinue2(IrContinue irContinue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContinue(this, irContinue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public JsFunction visitDeclaration2(IrDeclarationBase irDeclarationBase, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public JsFunction visitDeclarationReference2(IrDeclarationReference irDeclarationReference, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsFunction visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsFunction visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitDynamicExpression2(IrDynamicExpression irDynamicExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public JsFunction visitElseBranch2(IrElseBranch irElseBranch, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsFunction visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public JsFunction visitEnumEntry2(IrEnumEntry irEnumEntry, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public JsFunction visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitErrorExpression2(IrErrorExpression irErrorExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitExpression2(IrExpression irExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExpression(this, irExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public JsFunction visitExpressionBody2(IrExpressionBody irExpressionBody, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsFunction visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public JsFunction visitField2(IrField irField, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitField(this, irField, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public JsFunction visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public JsFunction visitFile2(IrFile irFile, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFile(this, irFile, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public JsFunction visitFunction2(IrFunction irFunction, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunction(this, irFunction, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public JsFunction visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitFunctionExpression2(IrFunctionExpression irFunctionExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsFunction visitFunctionReference2(IrFunctionReference irFunctionReference, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public JsFunction visitGetClass2(IrGetClass irGetClass, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetClass(this, irGetClass, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public JsFunction visitGetEnumValue2(IrGetEnumValue irGetEnumValue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public JsFunction visitGetField2(IrGetField irGetField, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetField(this, irGetField, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public JsFunction visitGetObjectValue2(IrGetObjectValue irGetObjectValue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public JsFunction visitGetValue2(IrGetValue irGetValue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetValue(this, irGetValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public JsFunction visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public JsFunction visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsFunction visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public JsFunction visitLoop2(IrLoop irLoop, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLoop(this, irLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (JsGenerationContext) obj);
    }

    public JsFunction visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public JsFunction visitModuleFragment2(IrModuleFragment irModuleFragment, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsFunction visitPackageFragment2(IrPackageFragment irPackageFragment, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public JsFunction visitProperty2(IrProperty irProperty, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitProperty(this, irProperty, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsFunction visitPropertyReference2(IrPropertyReference irPropertyReference, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsFunction visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public JsFunction visitReturn2(IrReturn irReturn, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitReturn(this, irReturn, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public JsFunction visitScript2(IrScript irScript, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitScript(this, irScript, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSetField2(IrSetField irSetField, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSetField(this, irSetField, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSetValue2(IrSetValue irSetValue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSetValue(this, irSetValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSpreadElement2(IrSpreadElement irSpreadElement, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public JsFunction visitStringConcatenation2(IrStringConcatenation irStringConcatenation, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSyntheticBody2(IrSyntheticBody irSyntheticBody, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public JsFunction visitThrow2(IrThrow irThrow, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitThrow(this, irThrow, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public JsFunction visitTry2(IrTry irTry, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTry(this, irTry, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public JsFunction visitTypeAlias2(IrTypeAlias irTypeAlias, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public JsFunction visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public JsFunction visitTypeParameter2(IrTypeParameter irTypeParameter, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public JsFunction visitValueAccess2(IrValueAccessExpression irValueAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public JsFunction visitValueParameter2(IrValueParameter irValueParameter, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public JsFunction visitVararg2(IrVararg irVararg, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitVararg(this, irVararg, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public JsFunction visitVariable2(IrVariable irVariable, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitVariable(this, irVariable, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public JsFunction visitWhen2(IrWhen irWhen, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitWhen(this, irWhen, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsFunction visitWhileLoop2(IrWhileLoop irWhileLoop, JsGenerationContext jsGenerationContext) {
        return (JsFunction) BaseIrElementToJsNodeTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public JsFunction visitSimpleFunction2(IrSimpleFunction declaration, JsGenerationContext context) {
        JsName nameForStaticFunction;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        IrDeclarationParent parent = declaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        boolean zIsInterface = irClass != null ? IrUtilsKt.isInterface(irClass) : false;
        if (declaration.getDispatchReceiverParameter() == null || zIsInterface) {
            if (declaration.getParent() instanceof IrFunction) {
                nameForStaticFunction = context.getNameForValueDeclaration(declaration);
            } else {
                nameForStaticFunction = context.getNameForStaticFunction(declaration);
            }
        } else {
            nameForStaticFunction = context.getNameForMemberFunction(declaration);
        }
        return JsAstUtilsKt.translateFunction(declaration, nameForStaticFunction, context);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public JsFunction visitConstructor2(IrConstructor declaration, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        declaration.getIsPrimary();
        return JsAstUtilsKt.translateFunction(declaration, context.getNameForConstructor(declaration), context);
    }
}
