package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.backend.js.utils.JsStaticContext;
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
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;

/* JADX INFO: compiled from: IrFileToJsTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0003H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrFileToJsTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "useBareParameterNames", "", "(Z)V", "visitFile", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "data", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFileToJsTransformer implements BaseIrElementToJsNodeTransformer<JsBlock, JsStaticContext> {
    private final boolean useBareParameterNames;

    public IrFileToJsTransformer() {
        this(false, 1, null);
    }

    public IrFileToJsTransformer(boolean z) {
        this.useBareParameterNames = z;
    }

    public /* synthetic */ IrFileToJsTransformer(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public JsBlock visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public JsBlock visitBlock2(IrBlock irBlock, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlock(this, irBlock, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public JsBlock visitBlockBody2(IrBlockBody irBlockBody, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBlockBody(this, irBlockBody, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBody, reason: merged with bridge method [inline-methods] */
    public JsBlock visitBody2(IrBody irBody, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBody(this, irBody, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBranch, reason: merged with bridge method [inline-methods] */
    public JsBlock visitBranch2(IrBranch irBranch, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBranch(this, irBranch, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public JsBlock visitBreak2(IrBreak irBreak, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreak(this, irBreak, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public JsBlock visitBreakContinue2(IrBreakContinue irBreakContinue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public JsBlock visitCall2(IrCall irCall, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCall(this, irCall, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
        return visitCallableReference((IrCallableReference<?>) irCallableReference, (JsStaticContext) obj);
    }

    public JsBlock visitCallableReference(IrCallableReference<?> irCallableReference, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCallableReference(this, irCallableReference, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCatch, reason: merged with bridge method [inline-methods] */
    public JsBlock visitCatch2(IrCatch irCatch, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitCatch(this, irCatch, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public JsBlock visitClass2(IrClass irClass, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClass(this, irClass, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public JsBlock visitClassReference2(IrClassReference irClassReference, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClassReference(this, irClassReference, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public JsBlock visitComposite2(IrComposite irComposite, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitComposite(this, irComposite, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitConst(IrConst irConst, Object obj) {
        return visitConst((IrConst<?>) irConst, (JsStaticContext) obj);
    }

    public JsBlock visitConst(IrConst<?> irConst, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConst(this, irConst, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantArray, reason: merged with bridge method [inline-methods] */
    public JsBlock visitConstantArray2(IrConstantArray irConstantArray, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantArray(this, irConstantArray, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantObject, reason: merged with bridge method [inline-methods] */
    public JsBlock visitConstantObject2(IrConstantObject irConstantObject, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantObject(this, irConstantObject, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantPrimitive, reason: merged with bridge method [inline-methods] */
    public JsBlock visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstantValue, reason: merged with bridge method [inline-methods] */
    public JsBlock visitConstantValue2(IrConstantValue irConstantValue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstantValue(this, irConstantValue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public JsBlock visitConstructor2(IrConstructor irConstructor, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstructor(this, irConstructor, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsBlock visitConstructorCall2(IrConstructorCall irConstructorCall, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstructorCall(this, irConstructorCall, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContainerExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitContainerExpression2(IrContainerExpression irContainerExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContainerExpression(this, irContainerExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public JsBlock visitContinue2(IrContinue irContinue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitContinue(this, irContinue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclaration, reason: merged with bridge method [inline-methods] */
    public JsBlock visitDeclaration2(IrDeclarationBase irDeclarationBase, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclaration(this, irDeclarationBase, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDeclarationReference, reason: merged with bridge method [inline-methods] */
    public JsBlock visitDeclarationReference2(IrDeclarationReference irDeclarationReference, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsBlock visitDelegatingConstructorCall2(IrDelegatingConstructorCall irDelegatingConstructorCall, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsBlock visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitDynamicExpression2(IrDynamicExpression irDynamicExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicMemberExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitDynamicMemberExpression2(IrDynamicMemberExpression irDynamicMemberExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDynamicOperatorExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitDynamicOperatorExpression2(IrDynamicOperatorExpression irDynamicOperatorExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElseBranch, reason: merged with bridge method [inline-methods] */
    public JsBlock visitElseBranch2(IrElseBranch irElseBranch, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitElseBranch(this, irElseBranch, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsBlock visitEnumConstructorCall2(IrEnumConstructorCall irEnumConstructorCall, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitEnumEntry, reason: merged with bridge method [inline-methods] */
    public JsBlock visitEnumEntry2(IrEnumEntry irEnumEntry, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitEnumEntry(this, irEnumEntry, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorCallExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitErrorCallExpression2(IrErrorCallExpression irErrorCallExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public JsBlock visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitErrorExpression2(IrErrorExpression irErrorExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitExpression2(IrExpression irExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExpression(this, irExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpressionBody, reason: merged with bridge method [inline-methods] */
    public JsBlock visitExpressionBody2(IrExpressionBody irExpressionBody, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExpressionBody(this, irExpressionBody, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExternalPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsBlock visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public JsBlock visitField2(IrField irField, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitField(this, irField, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFieldAccess, reason: merged with bridge method [inline-methods] */
    public JsBlock visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public JsBlock visitFunction2(IrFunction irFunction, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunction(this, irFunction, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public JsBlock visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitFunctionExpression2(IrFunctionExpression irFunctionExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsBlock visitFunctionReference2(IrFunctionReference irFunctionReference, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionReference(this, irFunctionReference, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetClass, reason: merged with bridge method [inline-methods] */
    public JsBlock visitGetClass2(IrGetClass irGetClass, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetClass(this, irGetClass, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetEnumValue, reason: merged with bridge method [inline-methods] */
    public JsBlock visitGetEnumValue2(IrGetEnumValue irGetEnumValue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetField, reason: merged with bridge method [inline-methods] */
    public JsBlock visitGetField2(IrGetField irGetField, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetField(this, irGetField, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetObjectValue, reason: merged with bridge method [inline-methods] */
    public JsBlock visitGetObjectValue2(IrGetObjectValue irGetObjectValue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitGetValue, reason: merged with bridge method [inline-methods] */
    public JsBlock visitGetValue2(IrGetValue irGetValue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitGetValue(this, irGetValue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public JsBlock visitInstanceInitializerCall2(IrInstanceInitializerCall irInstanceInitializerCall, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedProperty, reason: merged with bridge method [inline-methods] */
    public JsBlock visitLocalDelegatedProperty2(IrLocalDelegatedProperty irLocalDelegatedProperty, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsBlock visitLocalDelegatedPropertyReference2(IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitLoop, reason: merged with bridge method [inline-methods] */
    public JsBlock visitLoop2(IrLoop irLoop, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitLoop(this, irLoop, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
        return visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (JsStaticContext) obj);
    }

    public JsBlock visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitModuleFragment, reason: merged with bridge method [inline-methods] */
    public JsBlock visitModuleFragment2(IrModuleFragment irModuleFragment, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitModuleFragment(this, irModuleFragment, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPackageFragment, reason: merged with bridge method [inline-methods] */
    public JsBlock visitPackageFragment2(IrPackageFragment irPackageFragment, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPackageFragment(this, irPackageFragment, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitProperty, reason: merged with bridge method [inline-methods] */
    public JsBlock visitProperty2(IrProperty irProperty, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitProperty(this, irProperty, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitPropertyReference, reason: merged with bridge method [inline-methods] */
    public JsBlock visitPropertyReference2(IrPropertyReference irPropertyReference, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitPropertyReference(this, irPropertyReference, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitRawFunctionReference, reason: merged with bridge method [inline-methods] */
    public JsBlock visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public JsBlock visitReturn2(IrReturn irReturn, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitReturn(this, irReturn, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public JsBlock visitScript2(IrScript irScript, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitScript(this, irScript, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSetField2(IrSetField irSetField, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSetField(this, irSetField, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSetValue2(IrSetValue irSetValue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSetValue(this, irSetValue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSimpleFunction2(IrSimpleFunction irSimpleFunction, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSingletonReference, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSpreadElement, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSpreadElement2(IrSpreadElement irSpreadElement, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSpreadElement(this, irSpreadElement, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitStringConcatenation, reason: merged with bridge method [inline-methods] */
    public JsBlock visitStringConcatenation2(IrStringConcatenation irStringConcatenation, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspendableExpression, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSuspensionPoint, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSyntheticBody, reason: merged with bridge method [inline-methods] */
    public JsBlock visitSyntheticBody2(IrSyntheticBody irSyntheticBody, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public JsBlock visitThrow2(IrThrow irThrow, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitThrow(this, irThrow, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public JsBlock visitTry2(IrTry irTry, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTry(this, irTry, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeAlias, reason: merged with bridge method [inline-methods] */
    public JsBlock visitTypeAlias2(IrTypeAlias irTypeAlias, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeAlias(this, irTypeAlias, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeOperator, reason: merged with bridge method [inline-methods] */
    public JsBlock visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTypeParameter, reason: merged with bridge method [inline-methods] */
    public JsBlock visitTypeParameter2(IrTypeParameter irTypeParameter, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitTypeParameter(this, irTypeParameter, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueAccess, reason: merged with bridge method [inline-methods] */
    public JsBlock visitValueAccess2(IrValueAccessExpression irValueAccessExpression, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueAccess(this, irValueAccessExpression, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitValueParameter, reason: merged with bridge method [inline-methods] */
    public JsBlock visitValueParameter2(IrValueParameter irValueParameter, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitValueParameter(this, irValueParameter, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVararg, reason: merged with bridge method [inline-methods] */
    public JsBlock visitVararg2(IrVararg irVararg, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitVararg(this, irVararg, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public JsBlock visitVariable2(IrVariable irVariable, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitVariable(this, irVariable, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public JsBlock visitWhen2(IrWhen irWhen, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitWhen(this, irWhen, jsStaticContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsBlock visitWhileLoop2(IrWhileLoop irWhileLoop, JsStaticContext jsStaticContext) {
        return (JsBlock) BaseIrElementToJsNodeTransformer.DefaultImpls.visitWhileLoop(this, irWhileLoop, jsStaticContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFile, reason: merged with bridge method [inline-methods] */
    public JsBlock visitFile2(IrFile declaration, JsStaticContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        JsGenerationContext jsGenerationContext = new JsGenerationContext(declaration, null, data, null, null, this.useBareParameterNames, 24, null);
        JsCompositeBlock jsCompositeBlock = new JsCompositeBlock();
        Iterator<T> it = declaration.getDeclarations().iterator();
        while (it.hasNext()) {
            jsCompositeBlock.getStatements().add(((IrDeclaration) it.next()).accept(new IrDeclarationToJsTransformer(), jsGenerationContext));
        }
        return jsCompositeBlock;
    }
}
