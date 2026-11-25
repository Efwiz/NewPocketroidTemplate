package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.utils.MiscKt;
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
import org.jetbrains.kotlin.ir.visitors.IrElementVisitor;
import org.jetbrains.kotlin.js.backend.ast.JsNode;

/* JADX INFO: compiled from: BaseIrElementToJsNodeTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u0002*\u0006\b\u0001\u0010\u0003 \u00002\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u0004J\u001d\u0010\u0005\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/BaseIrElementToJsNodeTransformer;", "R", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "D", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "data", "(Lorg/jetbrains/kotlin/ir/IrElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface BaseIrElementToJsNodeTransformer<R extends JsNode, D> extends IrElementVisitor<R, D> {

    /* JADX INFO: compiled from: BaseIrElementToJsNodeTransformer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static <R extends JsNode, D> R visitAnonymousInitializer(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrAnonymousInitializer declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitAnonymousInitializer(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitBlock(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrBlock expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitBlock(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitBlockBody(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrBlockBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return (R) IrElementVisitor.DefaultImpls.visitBlockBody(baseIrElementToJsNodeTransformer, body, d);
        }

        public static <R extends JsNode, D> R visitBody(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return (R) IrElementVisitor.DefaultImpls.visitBody(baseIrElementToJsNodeTransformer, body, d);
        }

        public static <R extends JsNode, D> R visitBranch(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            return (R) IrElementVisitor.DefaultImpls.visitBranch(baseIrElementToJsNodeTransformer, branch, d);
        }

        public static <R extends JsNode, D> R visitBreak(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrBreak jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return (R) IrElementVisitor.DefaultImpls.visitBreak(baseIrElementToJsNodeTransformer, jump, d);
        }

        public static <R extends JsNode, D> R visitBreakContinue(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrBreakContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return (R) IrElementVisitor.DefaultImpls.visitBreakContinue(baseIrElementToJsNodeTransformer, jump, d);
        }

        public static <R extends JsNode, D> R visitCall(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitCall(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitCallableReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrCallableReference<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitCallableReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitCatch(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrCatch aCatch, D d) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            return (R) IrElementVisitor.DefaultImpls.visitCatch(baseIrElementToJsNodeTransformer, aCatch, d);
        }

        public static <R extends JsNode, D> R visitClass(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrClass declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitClass(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitClassReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrClassReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitClassReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitComposite(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrComposite expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitComposite(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitConst(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrConst<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitConst(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitConstantArray(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrConstantArray expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitConstantArray(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitConstantObject(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrConstantObject expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitConstantObject(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitConstantPrimitive(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrConstantPrimitive expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitConstantPrimitive(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitConstantValue(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrConstantValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitConstantValue(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitConstructor(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrConstructor declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitConstructor(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitConstructorCall(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitConstructorCall(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitContainerExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrContainerExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitContainerExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitContinue(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrContinue jump, D d) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            return (R) IrElementVisitor.DefaultImpls.visitContinue(baseIrElementToJsNodeTransformer, jump, d);
        }

        public static <R extends JsNode, D> R visitDeclaration(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrDeclarationBase declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitDeclaration(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitDeclarationReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrDeclarationReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitDeclarationReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitDelegatingConstructorCall(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrDelegatingConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitDoWhileLoop(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrDoWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return (R) IrElementVisitor.DefaultImpls.visitDoWhileLoop(baseIrElementToJsNodeTransformer, loop, d);
        }

        public static <R extends JsNode, D> R visitDynamicExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrDynamicExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitDynamicExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitDynamicMemberExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrDynamicMemberExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitDynamicOperatorExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrDynamicOperatorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitElseBranch(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrElseBranch branch, D d) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            return (R) IrElementVisitor.DefaultImpls.visitElseBranch(baseIrElementToJsNodeTransformer, branch, d);
        }

        public static <R extends JsNode, D> R visitEnumConstructorCall(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrEnumConstructorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitEnumEntry(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrEnumEntry declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitEnumEntry(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitErrorCallExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrErrorCallExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitErrorCallExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitErrorDeclaration(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrErrorDeclaration declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitErrorDeclaration(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitErrorExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrErrorExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitErrorExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitExpressionBody(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrExpressionBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return (R) IrElementVisitor.DefaultImpls.visitExpressionBody(baseIrElementToJsNodeTransformer, body, d);
        }

        public static <R extends JsNode, D> R visitExternalPackageFragment(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrExternalPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitField(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrField declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitField(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitFieldAccess(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrFieldAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitFieldAccess(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitFile(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrFile declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitFile(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitFunction(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitFunction(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitFunctionAccess(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrFunctionAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitFunctionAccess(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitFunctionExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrFunctionExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitFunctionExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitFunctionReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitFunctionReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitGetClass(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrGetClass expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitGetClass(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitGetEnumValue(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrGetEnumValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitGetEnumValue(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitGetField(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrGetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitGetField(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitGetObjectValue(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrGetObjectValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitGetObjectValue(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitGetValue(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrGetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitGetValue(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitInstanceInitializerCall(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrInstanceInitializerCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitLocalDelegatedProperty(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrLocalDelegatedProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitLocalDelegatedPropertyReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrLocalDelegatedPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitLoop(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return (R) IrElementVisitor.DefaultImpls.visitLoop(baseIrElementToJsNodeTransformer, loop, d);
        }

        public static <R extends JsNode, D> R visitMemberAccess(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrMemberAccessExpression<?> expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitMemberAccess(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitModuleFragment(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrModuleFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitModuleFragment(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitPackageFragment(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrPackageFragment declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitPackageFragment(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitProperty(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrProperty declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitProperty(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitPropertyReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrPropertyReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitPropertyReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitRawFunctionReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrRawFunctionReference expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitRawFunctionReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitReturn(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrReturn expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitReturn(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitScript(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrScript declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitScript(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitSetField(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrSetField expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitSetField(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitSetValue(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrSetValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitSetValue(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitSimpleFunction(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrSimpleFunction declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitSimpleFunction(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitSingletonReference(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrGetSingletonValue expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitSingletonReference(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitSpreadElement(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrSpreadElement spread, D d) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            return (R) IrElementVisitor.DefaultImpls.visitSpreadElement(baseIrElementToJsNodeTransformer, spread, d);
        }

        public static <R extends JsNode, D> R visitStringConcatenation(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrStringConcatenation expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitStringConcatenation(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitSuspendableExpression(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrSuspendableExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitSuspendableExpression(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitSuspensionPoint(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrSuspensionPoint expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitSuspensionPoint(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitSyntheticBody(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrSyntheticBody body, D d) {
            Intrinsics.checkNotNullParameter(body, "body");
            return (R) IrElementVisitor.DefaultImpls.visitSyntheticBody(baseIrElementToJsNodeTransformer, body, d);
        }

        public static <R extends JsNode, D> R visitThrow(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrThrow expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitThrow(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitTry(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrTry aTry, D d) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            return (R) IrElementVisitor.DefaultImpls.visitTry(baseIrElementToJsNodeTransformer, aTry, d);
        }

        public static <R extends JsNode, D> R visitTypeAlias(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrTypeAlias declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitTypeAlias(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitTypeOperator(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrTypeOperatorCall expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitTypeOperator(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitTypeParameter(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrTypeParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitTypeParameter(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitValueAccess(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrValueAccessExpression expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitValueAccess(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitValueParameter(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrValueParameter declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitValueParameter(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitVararg(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrVararg expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitVararg(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitVariable(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrVariable declaration, D d) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            return (R) IrElementVisitor.DefaultImpls.visitVariable(baseIrElementToJsNodeTransformer, declaration, d);
        }

        public static <R extends JsNode, D> R visitWhen(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrWhen expression, D d) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return (R) IrElementVisitor.DefaultImpls.visitWhen(baseIrElementToJsNodeTransformer, expression, d);
        }

        public static <R extends JsNode, D> R visitWhileLoop(BaseIrElementToJsNodeTransformer<? extends R, ? super D> baseIrElementToJsNodeTransformer, IrWhileLoop loop, D d) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            return (R) IrElementVisitor.DefaultImpls.visitWhileLoop(baseIrElementToJsNodeTransformer, loop, d);
        }
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
    /* JADX INFO: renamed from: visitElement */
    default R visitElement2(IrElement element, D data) {
        Intrinsics.checkNotNullParameter(element, "element");
        MiscKt.TODO(element);
        throw null;
    }
}
