package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.lower.JsCodeOutliningLowering;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
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
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;

/* JADX INFO: compiled from: IrDeclarationToJsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\t\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0016J\u0018\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00162\u0006\u0010\b\u001a\u00020\u0003H\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrDeclarationToJsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "()V", "visitClass", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "context", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitErrorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "data", "visitField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "visitScript", "irScript", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDeclarationToJsTransformer implements BaseIrElementToJsNodeTransformer<JsStatement, JsGenerationContext> {
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public JsStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBlock2(IrBlock irBlock, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlock(this, irBlock, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBlockBody2(IrBlockBody irBlockBody, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBody2(IrBody irBody, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBody(this, irBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBranch2(IrBranch irBranch, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBranch(this, irBranch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBreak2(IrBreak irBreak, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreak(this, irBreak, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBreakContinue2(IrBreakContinue irBreakContinue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitCall2(IrCall irCall, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCall(this, irCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (JsGenerationContext) obj);
    }

    public JsStatement visitCallableReference(IrCallableReference<?> irCallableReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public JsStatement visitCatch2(IrCatch irCatch, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCatch(this, irCatch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitClassReference2(IrClassReference irClassReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClassReference(this, irClassReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public JsStatement visitComposite2(IrComposite irComposite, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitComposite(this, irComposite, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (JsGenerationContext) obj);
    }

    public JsStatement visitConst(IrConst<?> irConst, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConst(this, irConst, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public JsStatement visitConstantArray2(IrConstantArray irConstantArray, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public JsStatement visitConstantObject2(IrConstantObject irConstantObject, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public JsStatement visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitConstantValue2(IrConstantValue irConstantValue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitConstructorCall2(IrConstructorCall irConstructorCall, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitContainerExpression2(IrContainerExpression irContainerExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitContinue2(IrContinue irContinue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContinue(this, irContinue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDeclaration2(IrDeclarationBase irDeclarationBase, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDeclarationReference2(IrDeclarationReference irDeclarationReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDynamicExpression2(IrDynamicExpression irDynamicExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public JsStatement visitElseBranch2(IrElseBranch irElseBranch, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public JsStatement visitEnumEntry2(IrEnumEntry irEnumEntry, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitErrorExpression2(IrErrorExpression irErrorExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitExpression2(IrExpression irExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExpression(this, irExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public JsStatement visitExpressionBody2(IrExpressionBody irExpressionBody, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsStatement visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFile2(IrFile irFile, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFile(this, irFile, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFunction2(IrFunction irFunction, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunction(this, irFunction, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFunctionExpression2(IrFunctionExpression irFunctionExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFunctionReference2(IrFunctionReference irFunctionReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public JsStatement visitGetClass2(IrGetClass irGetClass, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetClass(this, irGetClass, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitGetEnumValue2(IrGetEnumValue irGetEnumValue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public JsStatement visitGetField2(IrGetField irGetField, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetField(this, irGetField, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitGetObjectValue2(IrGetObjectValue irGetObjectValue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitGetValue2(IrGetValue irGetValue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetValue(this, irGetValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public JsStatement visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public JsStatement visitLoop2(IrLoop irLoop, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLoop(this, irLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (JsGenerationContext) obj);
    }

    public JsStatement visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public JsStatement visitModuleFragment2(IrModuleFragment irModuleFragment, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsStatement visitPackageFragment2(IrPackageFragment irPackageFragment, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public JsStatement visitProperty2(IrProperty irProperty, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitProperty(this, irProperty, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitPropertyReference2(IrPropertyReference irPropertyReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public JsStatement visitReturn2(IrReturn irReturn, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitReturn(this, irReturn, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSetField2(IrSetField irSetField, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSetField(this, irSetField, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSetValue2(IrSetValue irSetValue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSetValue(this, irSetValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSpreadElement2(IrSpreadElement irSpreadElement, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public JsStatement visitStringConcatenation2(IrStringConcatenation irStringConcatenation, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSyntheticBody2(IrSyntheticBody irSyntheticBody, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public JsStatement visitThrow2(IrThrow irThrow, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitThrow(this, irThrow, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public JsStatement visitTry2(IrTry irTry, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTry(this, irTry, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public JsStatement visitTypeAlias2(IrTypeAlias irTypeAlias, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public JsStatement visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public JsStatement visitTypeParameter2(IrTypeParameter irTypeParameter, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public JsStatement visitValueAccess2(IrValueAccessExpression irValueAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public JsStatement visitValueParameter2(IrValueParameter irValueParameter, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public JsStatement visitVararg2(IrVararg irVararg, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitVararg(this, irVararg, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public JsStatement visitWhen2(IrWhen irWhen, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitWhen(this, irWhen, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsStatement visitWhileLoop2(IrWhileLoop irWhileLoop, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSimpleFunction2(IrSimpleFunction declaration, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        if (declaration.getIsExpect()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        if (Intrinsics.areEqual(declaration.getOrigin(), JsCodeOutliningLowering.INSTANCE.getOUTLINED_JS_CODE_ORIGIN())) {
            return JsEmpty.INSTANCE;
        }
        JsStatement jsStatementMakeStmt = ((JsFunction) declaration.accept(new IrFunctionToJsTransformer(), context)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public JsStatement visitConstructor2(IrConstructor declaration, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        JsStatement jsStatementMakeStmt = ((JsFunction) declaration.accept(new IrFunctionToJsTransformer(), context)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public JsStatement visitClass2(IrClass declaration, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        return new JsClassGenerator(declaration, JsGenerationContext.newDeclaration$default(context, null, null, 3, null)).generate();
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public JsStatement visitErrorDeclaration2(IrErrorDeclaration declaration, JsGenerationContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        return new JsSingleLineComment("$error code: declaration");
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public JsStatement visitField2(IrField declaration, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        JsName nameForField = context.getNameForField(declaration);
        if (declaration.getIsExternal()) {
            return JsEmpty.INSTANCE;
        }
        if (declaration.getInitializer() != null) {
            IrExpressionBody initializer = declaration.getInitializer();
            Intrinsics.checkNotNull(initializer);
            JsExpression jsExpression = (JsExpression) initializer.accept(new IrElementToJsExpressionTransformer(), context);
            List<JsStatement> statements = context.getStaticContext().getInitializerBlock().getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            JsNameRef jsNameRefMakeRef = nameForField.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            statements.add(JsAstUtilsKt.jsAssignment(jsNameRefMakeRef, jsExpression).makeStmt());
        }
        return new JsVars(new JsVars.JsVar(nameForField));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public JsStatement visitVariable2(IrVariable declaration, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        return (JsStatement) declaration.accept(new IrElementToJsStatementTransformer(), context);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public JsStatement visitScript2(IrScript irScript, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(irScript, "irScript");
        Intrinsics.checkNotNullParameter(context, "context");
        JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
        for (IrStatement irStatement : irScript.getStatements()) {
            List<JsStatement> statements = jsCompositeBlock.getStatements();
            Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
            statements.add((JsStatement) (irStatement instanceof IrDeclaration ? irStatement.accept(this, context) : irStatement.accept(new IrElementToJsStatementTransformer(), context)));
        }
        return jsCompositeBlock;
    }
}
