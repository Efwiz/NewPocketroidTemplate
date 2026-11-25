package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.lower.ES6_DELEGATING_CONSTRUCTOR_CALL_REPLACEMENT;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContext;
import org.jetbrains.kotlin.ir.backend.js.utils.JsGenerationContextKt;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.ir.declarations.IrMemberWithContainerSource;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
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
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.util.IrInlineUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperation;
import org.jetbrains.kotlin.js.backend.ast.JsBinaryOperator;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsBreak;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.js.backend.ast.JsContinue;
import org.jetbrains.kotlin.js.backend.ast.JsDoWhile;
import org.jetbrains.kotlin.js.backend.ast.JsEmpty;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsLabel;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsSingleLineComment;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.backend.ast.JsTry;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.js.backend.ast.JsWhile;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: IrElementToJsStatementTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000²\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0005¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0016J\u0018\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u001c2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0011\u001a\u00020\u0003H\u0016J\u0018\u0010 \u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020!2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010\"\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020#2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010$\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020%2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010&\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020'2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010(\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020)2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010*\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020+2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010,\u001a\u00020\u00022\u0006\u0010-\u001a\u00020.2\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u0010/\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u0002002\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u00101\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u0002022\u0006\u0010\b\u001a\u00020\u0003H\u0016J\u0018\u00103\u001a\u00020\u00022\u0006\u0010\u0019\u001a\u0002042\u0006\u0010\b\u001a\u00020\u0003H\u0016J.\u00105\u001a\u00020\u0002*\u00020\u001c2\u0006\u0010\b\u001a\u00020\u00032\u0018\u00106\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020908\u0012\u0004\u0012\u00020\u000207H\u0002J \u0010:\u001a\b\u0012\u0004\u0012\u00020\u00020;*\b\u0012\u0004\u0012\u00020\u00020;2\u0006\u0010\u0006\u001a\u00020<H\u0002¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/IrElementToJsStatementTransformer;", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "()V", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "context", "visitBlockBody", "body", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "visitBreak", "jump", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "data", "visitComposite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "visitContinue", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "visitDelegatingConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "visitDoWhileLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "visitExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "visitFunction", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "visitFunctionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "visitInstanceInitializerCall", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "visitReturn", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "visitSetField", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "visitSetValue", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "visitThrow", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "visitTry", "aTry", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "visitVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "visitWhen", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "visitWhileLoop", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "maybeOptimizeIntoSwitch", "transformer", "Lkotlin/Function1;", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "wrapInCommentsInlineFunctionCall", "", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrElementToJsStatementTransformer implements BaseIrElementToJsNodeTransformer<JsStatement, JsGenerationContext> {
    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitAnonymousInitializer, reason: merged with bridge method [inline-methods] */
    public JsStatement visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitBreakContinue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBreakContinue2(IrBreakContinue irBreakContinue, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitBreakContinue(this, irBreakContinue, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitClass, reason: merged with bridge method [inline-methods] */
    public JsStatement visitClass2(IrClass irClass, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClass(this, irClass, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitClassReference, reason: merged with bridge method [inline-methods] */
    public JsStatement visitClassReference2(IrClassReference irClassReference, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitClassReference(this, irClassReference, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitConstructor, reason: merged with bridge method [inline-methods] */
    public JsStatement visitConstructor2(IrConstructor irConstructor, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitConstructor(this, irConstructor, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitErrorDeclaration, reason: merged with bridge method [inline-methods] */
    public JsStatement visitErrorDeclaration2(IrErrorDeclaration irErrorDeclaration, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitErrorExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitErrorExpression2(IrErrorExpression irErrorExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitErrorExpression(this, irErrorExpression, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitField, reason: merged with bridge method [inline-methods] */
    public JsStatement visitField2(IrField irField, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitField(this, irField, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitFunctionAccess, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitScript, reason: merged with bridge method [inline-methods] */
    public JsStatement visitScript2(IrScript irScript, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitScript(this, irScript, jsGenerationContext);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSimpleFunction, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSimpleFunction2(IrSimpleFunction irSimpleFunction, JsGenerationContext jsGenerationContext) {
        return (JsStatement) BaseIrElementToJsNodeTransformer.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, jsGenerationContext);
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
    /* JADX INFO: renamed from: visitFunction, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFunction2(IrFunction declaration, JsGenerationContext data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(data, "data");
        throw new IllegalStateException("All functions must be already lowered".toString());
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlockBody, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBlockBody2(IrBlockBody body, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.checkNotNullParameter(context, "context");
        List<IrStatement> statements = body.getStatements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(statements, 10));
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            arrayList.add((JsStatement) ((IrStatement) it.next()).accept(this, context));
        }
        return (JsStatement) JsAstUtilsKt.withSource$default(new JsBlock((List<JsStatement>) MemoryOptimizedCollectionUtilKt.toSmartList(arrayList)), body, context, null, context.getCurrentFunction(), 4, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBlock, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBlock2(IrBlock expression, JsGenerationContext context) {
        JsGenerationContext jsGenerationContextNewFile;
        JsBlock jsBlock;
        IrFunction inlineFunction;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z = expression instanceof IrReturnableBlock;
        IrReturnableBlock irReturnableBlock = z ? (IrReturnableBlock) expression : null;
        if (irReturnableBlock == null || (inlineFunction = IrInlineUtilsKt.getInlineFunction(irReturnableBlock)) == null || (jsGenerationContextNewFile = context.newFile(IrUtilsKt.getFile(inlineFunction), context.getCurrentFunction(), context.getLocalNames())) == null) {
            jsGenerationContextNewFile = context;
        }
        List<IrStatement> statements = IrInlineUtilsKt.getInnerInlinedBlockOrThis(expression).getStatements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(statements, 10));
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            arrayList.add((JsStatement) ((IrStatement) it.next()).accept(this, jsGenerationContextNewFile));
        }
        List<? extends JsStatement> smartList = MemoryOptimizedCollectionUtilKt.toSmartList(arrayList);
        if (z) {
            IrReturnableBlock irReturnableBlock2 = (IrReturnableBlock) expression;
            JsName nameForReturnableBlock = context.getNameForReturnableBlock(irReturnableBlock2);
            List<JsStatement> listWrapInCommentsInlineFunctionCall = wrapInCommentsInlineFunctionCall(smartList, irReturnableBlock2);
            if (nameForReturnableBlock != null) {
                jsBlock = new JsLabel(nameForReturnableBlock, new JsBlock(listWrapInCommentsInlineFunctionCall));
            } else {
                jsBlock = new JsCompositeBlock(listWrapInCommentsInlineFunctionCall);
            }
        } else {
            jsBlock = new JsBlock((List<JsStatement>) smartList);
        }
        return (JsStatement) JsAstUtilsKt.withSource$default(jsBlock, expression, context, null, null, 12, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final List<JsStatement> wrapInCommentsInlineFunctionCall(List<? extends JsStatement> list, IrReturnableBlock irReturnableBlock) {
        IrMemberWithContainerSource irMemberWithContainerSource;
        IrMemberWithContainerSource inlineFunction = IrInlineUtilsKt.getInlineFunction(irReturnableBlock);
        if (inlineFunction == null) {
            return list;
        }
        IrSimpleFunction irSimpleFunction = inlineFunction instanceof IrSimpleFunction ? (IrSimpleFunction) inlineFunction : null;
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunction != null ? irSimpleFunction.getCorrespondingPropertySymbol() : null;
        IrMemberWithContainerSource irMemberWithContainerSource2 = (correspondingPropertySymbol == null || (irMemberWithContainerSource = (IrProperty) correspondingPropertySymbol.getOwner()) == null) ? inlineFunction : irMemberWithContainerSource;
        Object fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irMemberWithContainerSource2);
        if (fqNameWhenAvailable == null) {
            fqNameWhenAvailable = ((IrPossiblyExternalDeclaration) irMemberWithContainerSource2).getName();
        }
        return CollectionsKt.plus((Collection) CollectionsKt.listOf(new JsSingleLineComment(" Inline function '" + fqNameWhenAvailable + "' call")), (Iterable) list);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitComposite, reason: merged with bridge method [inline-methods] */
    public JsStatement visitComposite2(IrComposite expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        if (expression.getStatements().isEmpty()) {
            return JsEmpty.INSTANCE;
        }
        List<IrStatement> statements = expression.getStatements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(statements, 10));
        Iterator<T> it = statements.iterator();
        while (it.hasNext()) {
            arrayList.add((JsStatement) ((IrStatement) it.next()).accept(this, context));
        }
        return (JsStatement) JsAstUtilsKt.withSource$default(new JsBlock((List<JsStatement>) MemoryOptimizedCollectionUtilKt.toSmartList(arrayList)), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitExpression2(IrExpression expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        JsStatement jsStatementMakeStmt = ((JsExpression) expression.accept(new IrElementToJsExpressionTransformer(), context)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitFunctionExpression, reason: merged with bridge method [inline-methods] */
    public JsStatement visitFunctionExpression2(IrFunctionExpression expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        JsStatement jsStatementMakeStmt = ((JsFunction) expression.getFunction().accept(new IrFunctionToJsTransformer(), context)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitBreak, reason: merged with bridge method [inline-methods] */
    public JsStatement visitBreak2(IrBreak jump, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.checkNotNullParameter(context, "context");
        JsName nameForLoop = context.getNameForLoop(jump.getLoop());
        return (JsStatement) JsAstUtilsKt.withSource$default(new JsBreak(nameForLoop != null ? new JsNameRef(nameForLoop) : null), jump, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitContinue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitContinue2(IrContinue jump, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(jump, "jump");
        Intrinsics.checkNotNullParameter(context, "context");
        JsName nameForLoop = context.getNameForLoop(jump.getLoop());
        return (JsStatement) JsAstUtilsKt.withSource$default(new JsContinue(nameForLoop != null ? new JsNameRef(nameForLoop) : null), jump, context, null, null, 12, null);
    }

    private final JsStatement maybeOptimizeIntoSwitch(final IrExpression irExpression, final JsGenerationContext jsGenerationContext, final Function1<? super Function0<? extends JsExpression>, ? extends JsStatement> function1) {
        JsStatement jsStatementTryOptimize;
        return (!(irExpression instanceof IrWhen) || (jsStatementTryOptimize = new SwitchOptimizer(jsGenerationContext, true, new Function1<Function0<? extends JsStatement>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer$maybeOptimizeIntoSwitch$stmtTransformer$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JsStatement invoke(final Function0<? extends JsStatement> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Function1<Function0<? extends JsExpression>, JsStatement> function2 = function1;
                final IrExpression irExpression2 = irExpression;
                return function2.invoke(new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer$maybeOptimizeIntoSwitch$stmtTransformer$1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final JsExpression invoke() {
                        JsStatement jsStatementInvoke = it.invoke();
                        boolean z = jsStatementInvoke instanceof JsExpressionStatement;
                        Intrinsics.checkNotNull(jsStatementInvoke, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement");
                        JsExpression expression = ((JsExpressionStatement) jsStatementInvoke).getExpression();
                        Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                        return expression;
                    }
                });
            }
        }).tryOptimize((IrWhen) irExpression)) == null) ? function1.invoke(new Function0<JsExpression>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer.maybeOptimizeIntoSwitch.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JsExpression invoke() {
                return (JsExpression) irExpression.accept(new IrElementToJsExpressionTransformer(), jsGenerationContext);
            }
        }) : jsStatementTryOptimize;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetField, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSetField2(final IrSetField expression, final JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        JsName nameForField = context.getNameForField(expression.getSymbol().getOwner());
        IrElementToJsExpressionTransformer irElementToJsExpressionTransformer = new IrElementToJsExpressionTransformer();
        IrExpression receiver = expression.getReceiver();
        final JsExpression jsExpressionJsElementAccess = JsAstUtilsKt.jsElementAccess(nameForField, receiver != null ? (JsExpression) receiver.accept(irElementToJsExpressionTransformer, context) : null);
        return maybeOptimizeIntoSwitch(expression.getValue(), context, new Function1<Function0<? extends JsExpression>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer.visitSetField.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JsStatement invoke(Function0<? extends JsExpression> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                JsStatement jsStatementMakeStmt = ((JsBinaryOperation) JsAstUtilsKt.withSource$default(JsAstUtilsKt.jsAssignment(jsExpressionJsElementAccess, it.invoke()), expression, context, null, null, 12, null)).makeStmt();
                Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
                return jsStatementMakeStmt;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitSetValue, reason: merged with bridge method [inline-methods] */
    public JsStatement visitSetValue2(final IrSetValue expression, final JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        final JsNameRef jsNameRef = new JsNameRef(context.getNameForValueDeclaration(expression.getSymbol().getOwner()));
        return maybeOptimizeIntoSwitch(expression.getValue(), context, new Function1<Function0<? extends JsExpression>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer.visitSetValue.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JsStatement invoke(Function0<? extends JsExpression> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                JsStatement jsStatementMakeStmt = ((JsBinaryOperation) JsAstUtilsKt.withSource$default(JsAstUtilsKt.jsAssignment(jsNameRef, it.invoke()), expression, context, null, null, 12, null)).makeStmt();
                Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
                return jsStatementMakeStmt;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitReturn, reason: merged with bridge method [inline-methods] */
    public JsStatement visitReturn2(final IrReturn expression, final JsGenerationContext context) {
        IrElementToJsStatementTransformer$visitReturn$lastStatementTransformer$2 irElementToJsStatementTransformer$visitReturn$lastStatementTransformer$2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        final IrReturnTargetSymbol returnTargetSymbol = expression.getReturnTargetSymbol();
        if (returnTargetSymbol instanceof IrReturnableBlockSymbol) {
            irElementToJsStatementTransformer$visitReturn$lastStatementTransformer$2 = new Function1<Function0<? extends JsExpression>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer$visitReturn$lastStatementTransformer$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(Function0<? extends JsExpression> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    JsName nameForReturnableBlock = context.getNameForReturnableBlock(((IrReturnableBlockSymbol) returnTargetSymbol).getOwner());
                    if (IrJsUtilsKt.isTheLastReturnStatementIn(expression, (IrReturnableBlockSymbol) returnTargetSymbol)) {
                        nameForReturnableBlock = null;
                    }
                    return nameForReturnableBlock != null ? new JsBreak(nameForReturnableBlock.makeRef()) : JsEmpty.INSTANCE;
                }
            };
        } else if (returnTargetSymbol instanceof IrFunctionSymbol) {
            irElementToJsStatementTransformer$visitReturn$lastStatementTransformer$2 = new Function1<Function0<? extends JsExpression>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer$visitReturn$lastStatementTransformer$2
                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(Function0<? extends JsExpression> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new JsReturn(it.invoke());
                }
            };
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return (JsStatement) JsAstUtilsKt.withSource$default(maybeOptimizeIntoSwitch(expression.getValue(), context, irElementToJsStatementTransformer$visitReturn$lastStatementTransformer$2), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitThrow, reason: merged with bridge method [inline-methods] */
    public JsStatement visitThrow2(IrThrow expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        return (JsStatement) JsAstUtilsKt.withSource$default(maybeOptimizeIntoSwitch(expression.getValue(), context, new Function1<Function0<? extends JsExpression>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer.visitThrow.1
            @Override // kotlin.jvm.functions.Function1
            public final JsStatement invoke(Function0<? extends JsExpression> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new JsThrow(it.invoke());
            }
        }), expression, context, null, null, 12, null);
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitVariable, reason: merged with bridge method [inline-methods] */
    public JsStatement visitVariable2(IrVariable declaration, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(context, "context");
        IrVariable irVariable = declaration;
        JsName nameForValueDeclaration = context.getNameForValueDeclaration(irVariable);
        IrExpression initializer = declaration.getInitializer();
        boolean z = true;
        if (initializer instanceof IrWhen) {
            final JsNameRef jsNameRefMakeRef = nameForValueDeclaration.makeRef();
            Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
            JsStatement jsStatementTryOptimize = new SwitchOptimizer(context, true, new Function1<Function0<? extends JsStatement>, JsStatement>() { // from class: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer$visitVariable$transformer$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final JsStatement invoke(Function0<? extends JsStatement> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    JsStatement jsStatementInvoke = it.invoke();
                    Intrinsics.checkNotNull(jsStatementInvoke, "null cannot be cast to non-null type org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement");
                    JsExpression expression = ((JsExpressionStatement) jsStatementInvoke).getExpression();
                    Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
                    JsStatement jsStatementMakeStmt = new JsBinaryOperation(JsBinaryOperator.ASG, jsNameRefMakeRef, expression).makeStmt();
                    Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
                    return jsStatementMakeStmt;
                }
            }).tryOptimize((IrWhen) initializer);
            if (jsStatementTryOptimize != null) {
                return (JsStatement) JsAstUtilsKt.withSource$default(new JsBlock(new JsVars(new JsVars.JsVar(nameForValueDeclaration)), jsStatementTryOptimize), declaration, context, null, null, 12, null);
            }
        }
        JsExpression jsExpression = initializer != null ? (JsExpression) initializer.accept(new IrElementToJsExpressionTransformer(), context) : null;
        IrDeclarationOrigin origin = declaration.getOrigin();
        if (!(origin instanceof IrDeclarationOrigin.IR_TEMPORARY_VARIABLE) && !(origin instanceof IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER) && !(origin instanceof IrDeclarationOrigin.IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER) && !(origin instanceof ES6_DELEGATING_CONSTRUCTOR_CALL_REPLACEMENT)) {
            z = false;
        }
        JsVars.JsVar jsVar = new JsVars.JsVar(nameForValueDeclaration, jsExpression);
        JsAstUtilsKt.withSource$default(jsVar, declaration, context, irVariable, null, 8, null);
        MetadataProperties.setSynthetic(jsVar, z);
        JsVars jsVars = new JsVars(jsVar);
        MetadataProperties.setSynthetic(jsVars, z);
        return jsVars;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDelegatingConstructorCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDelegatingConstructorCall2(IrDelegatingConstructorCall expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        if (IrTypePredicatesKt.isAny(IrUtilsKt.getConstructedClassType(expression.getSymbol().getOwner()))) {
            return JsEmpty.INSTANCE;
        }
        JsStatement jsStatementMakeStmt = ((JsExpression) expression.accept(new IrElementToJsExpressionTransformer(), context)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitCall2(IrCall expression, JsGenerationContext data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (IrJsUtilsKt.isUnitInstanceFunction(expression.getSymbol(), data.getStaticContext().getBackendContext())) {
            return JsEmpty.INSTANCE;
        }
        if (data.checkIfJsCode(expression.getSymbol()) || data.checkIfHasAssociatedJsCode(expression.getSymbol())) {
            return new JsCallTransformer(expression, data).generateStatement();
        }
        JsStatement jsStatementMakeStmt = ((JsExpression) JsAstUtilsKt.withSource$default(JsAstUtilsKt.translateCall(expression, data, new IrElementToJsExpressionTransformer()), expression, data, null, null, 12, null)).makeStmt();
        Intrinsics.checkNotNullExpressionValue(jsStatementMakeStmt, "makeStmt(...)");
        return jsStatementMakeStmt;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitInstanceInitializerCall, reason: merged with bridge method [inline-methods] */
    public JsStatement visitInstanceInitializerCall2(IrInstanceInitializerCall expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        return JsEmpty.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitTry, reason: merged with bridge method [inline-methods] */
    public JsStatement visitTry2(IrTry aTry, JsGenerationContext context) {
        JsCatch jsCatch;
        JsStatement jsStatement;
        Intrinsics.checkNotNullParameter(aTry, "aTry");
        Intrinsics.checkNotNullParameter(context, "context");
        IrElementToJsStatementTransformer irElementToJsStatementTransformer = this;
        JsBlock jsBlockAsBlock = JsAstUtilsKt.asBlock((JsStatement) aTry.getTryResult().accept(irElementToJsStatementTransformer, context));
        IrCatch irCatch = (IrCatch) CollectionsKt.singleOrNull((List) aTry.getCatches());
        JsBlock jsBlockAsBlock2 = null;
        if (irCatch != null) {
            jsCatch = (JsCatch) JsAstUtilsKt.withSource$default(new JsCatch(JsGenerationContextKt.getEmptyScope(), context.getNameForValueDeclaration(irCatch.getCatchParameter()).getIdent(), (JsStatement) irCatch.getResult().accept(irElementToJsStatementTransformer, context)), irCatch, context, null, null, 12, null);
        } else {
            jsCatch = null;
        }
        IrExpression finallyExpression = aTry.getFinallyExpression();
        if (finallyExpression != null && (jsStatement = (JsStatement) finallyExpression.accept(irElementToJsStatementTransformer, context)) != null) {
            jsBlockAsBlock2 = JsAstUtilsKt.asBlock(jsStatement);
        }
        return (JsStatement) JsAstUtilsKt.withSource$default(new JsTry(jsBlockAsBlock, jsCatch, jsBlockAsBlock2), aTry, context, null, null, 12, null);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrElementToJsStatementTransformer$visitWhen$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: IrElementToJsStatementTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C47001 extends FunctionReference implements Function3<JsExpression, JsStatement, JsStatement, JsIf> {
        public static final C47001 INSTANCE = new C47001();

        C47001() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(JsIf.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final JsIf invoke(JsExpression p0, JsStatement p1, JsStatement jsStatement) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return new JsIf(p0, p1, jsStatement);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhen, reason: merged with bridge method [inline-methods] */
    public JsStatement visitWhen2(IrWhen expression, JsGenerationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        JsStatement jsStatementTryOptimize = new SwitchOptimizer(context).tryOptimize(expression);
        if (jsStatementTryOptimize != null) {
            return jsStatementTryOptimize;
        }
        JsStatement jsStatement = (JsStatement) JsAstUtilsKt.toJsNode$default(expression, this, context, C47001.INSTANCE, null, 8, null);
        return jsStatement == null ? JsEmpty.INSTANCE : jsStatement;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsStatement visitWhileLoop2(IrWhileLoop loop, JsGenerationContext context) {
        JsEmpty jsEmpty;
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(context, "context");
        JsName nameForLoop = context.getNameForLoop(loop);
        JsExpression jsExpression = (JsExpression) loop.getCondition().accept(new IrElementToJsExpressionTransformer(), context);
        IrExpression body = loop.getBody();
        if (body == null || (jsEmpty = (JsStatement) body.accept(this, context)) == null) {
            jsEmpty = JsEmpty.INSTANCE;
        }
        JsWhile jsWhile = new JsWhile(jsExpression, jsEmpty);
        return nameForLoop != null ? new JsLabel(nameForLoop, jsWhile) : jsWhile;
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitDoWhileLoop, reason: merged with bridge method [inline-methods] */
    public JsStatement visitDoWhileLoop2(IrDoWhileLoop loop, JsGenerationContext context) {
        JsEmpty jsEmpty;
        Intrinsics.checkNotNullParameter(loop, "loop");
        Intrinsics.checkNotNullParameter(context, "context");
        JsName nameForLoop = context.getNameForLoop(loop);
        JsExpression jsExpression = (JsExpression) loop.getCondition().accept(new IrElementToJsExpressionTransformer(), context);
        IrExpression body = loop.getBody();
        if (body == null || (jsEmpty = (JsStatement) body.accept(this, context)) == null) {
            jsEmpty = JsEmpty.INSTANCE;
        }
        JsDoWhile jsDoWhile = new JsDoWhile(jsExpression, jsEmpty);
        return nameForLoop != null ? new JsLabel(nameForLoop, jsDoWhile) : jsDoWhile;
    }
}
