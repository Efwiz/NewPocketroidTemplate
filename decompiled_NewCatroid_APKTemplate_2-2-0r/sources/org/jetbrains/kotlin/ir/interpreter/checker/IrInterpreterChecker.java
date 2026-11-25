package org.jetbrains.kotlin.ir.interpreter.checker;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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

/* JADX INFO: compiled from: IrInterpreterChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterChecker;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementVisitor;", "", "Lorg/jetbrains/kotlin/ir/interpreter/checker/IrInterpreterCheckerData;", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrInterpreterChecker extends IrElementVisitor<Boolean, IrInterpreterCheckerData> {

    /* JADX INFO: compiled from: IrInterpreterChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static Boolean visitAnonymousInitializer(IrInterpreterChecker irInterpreterChecker, IrAnonymousInitializer declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitAnonymousInitializer(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitBlock(IrInterpreterChecker irInterpreterChecker, IrBlock expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitBlock(irInterpreterChecker, expression, data);
        }

        public static Boolean visitBlockBody(IrInterpreterChecker irInterpreterChecker, IrBlockBody body, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitBlockBody(irInterpreterChecker, body, data);
        }

        public static Boolean visitBody(IrInterpreterChecker irInterpreterChecker, IrBody body, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitBody(irInterpreterChecker, body, data);
        }

        public static Boolean visitBranch(IrInterpreterChecker irInterpreterChecker, IrBranch branch, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitBranch(irInterpreterChecker, branch, data);
        }

        public static Boolean visitBreak(IrInterpreterChecker irInterpreterChecker, IrBreak jump, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitBreak(irInterpreterChecker, jump, data);
        }

        public static Boolean visitBreakContinue(IrInterpreterChecker irInterpreterChecker, IrBreakContinue jump, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitBreakContinue(irInterpreterChecker, jump, data);
        }

        public static Boolean visitCall(IrInterpreterChecker irInterpreterChecker, IrCall expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitCall(irInterpreterChecker, expression, data);
        }

        public static Boolean visitCallableReference(IrInterpreterChecker irInterpreterChecker, IrCallableReference<?> expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitCallableReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitCatch(IrInterpreterChecker irInterpreterChecker, IrCatch aCatch, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitCatch(irInterpreterChecker, aCatch, data);
        }

        public static Boolean visitClass(IrInterpreterChecker irInterpreterChecker, IrClass declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitClass(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitClassReference(IrInterpreterChecker irInterpreterChecker, IrClassReference expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitClassReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitComposite(IrInterpreterChecker irInterpreterChecker, IrComposite expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitComposite(irInterpreterChecker, expression, data);
        }

        public static Boolean visitConst(IrInterpreterChecker irInterpreterChecker, IrConst<?> expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitConst(irInterpreterChecker, expression, data);
        }

        public static Boolean visitConstantArray(IrInterpreterChecker irInterpreterChecker, IrConstantArray expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantArray(irInterpreterChecker, expression, data);
        }

        public static Boolean visitConstantObject(IrInterpreterChecker irInterpreterChecker, IrConstantObject expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantObject(irInterpreterChecker, expression, data);
        }

        public static Boolean visitConstantPrimitive(IrInterpreterChecker irInterpreterChecker, IrConstantPrimitive expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantPrimitive(irInterpreterChecker, expression, data);
        }

        public static Boolean visitConstantValue(IrInterpreterChecker irInterpreterChecker, IrConstantValue expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstantValue(irInterpreterChecker, expression, data);
        }

        public static Boolean visitConstructor(IrInterpreterChecker irInterpreterChecker, IrConstructor declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstructor(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitConstructorCall(IrInterpreterChecker irInterpreterChecker, IrConstructorCall expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitConstructorCall(irInterpreterChecker, expression, data);
        }

        public static Boolean visitContainerExpression(IrInterpreterChecker irInterpreterChecker, IrContainerExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitContainerExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitContinue(IrInterpreterChecker irInterpreterChecker, IrContinue jump, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitContinue(irInterpreterChecker, jump, data);
        }

        public static Boolean visitDeclaration(IrInterpreterChecker irInterpreterChecker, IrDeclarationBase declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitDeclaration(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitDeclarationReference(IrInterpreterChecker irInterpreterChecker, IrDeclarationReference expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitDeclarationReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitDelegatingConstructorCall(IrInterpreterChecker irInterpreterChecker, IrDelegatingConstructorCall expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitDelegatingConstructorCall(irInterpreterChecker, expression, data);
        }

        public static Boolean visitDoWhileLoop(IrInterpreterChecker irInterpreterChecker, IrDoWhileLoop loop, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitDoWhileLoop(irInterpreterChecker, loop, data);
        }

        public static Boolean visitDynamicExpression(IrInterpreterChecker irInterpreterChecker, IrDynamicExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitDynamicMemberExpression(IrInterpreterChecker irInterpreterChecker, IrDynamicMemberExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicMemberExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitDynamicOperatorExpression(IrInterpreterChecker irInterpreterChecker, IrDynamicOperatorExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitDynamicOperatorExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitElseBranch(IrInterpreterChecker irInterpreterChecker, IrElseBranch branch, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitElseBranch(irInterpreterChecker, branch, data);
        }

        public static Boolean visitEnumConstructorCall(IrInterpreterChecker irInterpreterChecker, IrEnumConstructorCall expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitEnumConstructorCall(irInterpreterChecker, expression, data);
        }

        public static Boolean visitEnumEntry(IrInterpreterChecker irInterpreterChecker, IrEnumEntry declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitEnumEntry(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitErrorCallExpression(IrInterpreterChecker irInterpreterChecker, IrErrorCallExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitErrorCallExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitErrorDeclaration(IrInterpreterChecker irInterpreterChecker, IrErrorDeclaration declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitErrorDeclaration(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitErrorExpression(IrInterpreterChecker irInterpreterChecker, IrErrorExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitErrorExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitExpression(IrInterpreterChecker irInterpreterChecker, IrExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitExpressionBody(IrInterpreterChecker irInterpreterChecker, IrExpressionBody body, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitExpressionBody(irInterpreterChecker, body, data);
        }

        public static Boolean visitExternalPackageFragment(IrInterpreterChecker irInterpreterChecker, IrExternalPackageFragment declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitExternalPackageFragment(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitField(IrInterpreterChecker irInterpreterChecker, IrField declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitField(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitFieldAccess(IrInterpreterChecker irInterpreterChecker, IrFieldAccessExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitFieldAccess(irInterpreterChecker, expression, data);
        }

        public static Boolean visitFile(IrInterpreterChecker irInterpreterChecker, IrFile declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitFile(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitFunction(IrInterpreterChecker irInterpreterChecker, IrFunction declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunction(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitFunctionAccess(IrInterpreterChecker irInterpreterChecker, IrFunctionAccessExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionAccess(irInterpreterChecker, expression, data);
        }

        public static Boolean visitFunctionExpression(IrInterpreterChecker irInterpreterChecker, IrFunctionExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitFunctionReference(IrInterpreterChecker irInterpreterChecker, IrFunctionReference expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitFunctionReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitGetClass(IrInterpreterChecker irInterpreterChecker, IrGetClass expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetClass(irInterpreterChecker, expression, data);
        }

        public static Boolean visitGetEnumValue(IrInterpreterChecker irInterpreterChecker, IrGetEnumValue expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetEnumValue(irInterpreterChecker, expression, data);
        }

        public static Boolean visitGetField(IrInterpreterChecker irInterpreterChecker, IrGetField expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetField(irInterpreterChecker, expression, data);
        }

        public static Boolean visitGetObjectValue(IrInterpreterChecker irInterpreterChecker, IrGetObjectValue expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetObjectValue(irInterpreterChecker, expression, data);
        }

        public static Boolean visitGetValue(IrInterpreterChecker irInterpreterChecker, IrGetValue expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitGetValue(irInterpreterChecker, expression, data);
        }

        public static Boolean visitInstanceInitializerCall(IrInterpreterChecker irInterpreterChecker, IrInstanceInitializerCall expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitInstanceInitializerCall(irInterpreterChecker, expression, data);
        }

        public static Boolean visitLocalDelegatedProperty(IrInterpreterChecker irInterpreterChecker, IrLocalDelegatedProperty declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitLocalDelegatedProperty(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitLocalDelegatedPropertyReference(IrInterpreterChecker irInterpreterChecker, IrLocalDelegatedPropertyReference expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitLocalDelegatedPropertyReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitLoop(IrInterpreterChecker irInterpreterChecker, IrLoop loop, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitLoop(irInterpreterChecker, loop, data);
        }

        public static Boolean visitMemberAccess(IrInterpreterChecker irInterpreterChecker, IrMemberAccessExpression<?> expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitMemberAccess(irInterpreterChecker, expression, data);
        }

        public static Boolean visitModuleFragment(IrInterpreterChecker irInterpreterChecker, IrModuleFragment declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitModuleFragment(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitPackageFragment(IrInterpreterChecker irInterpreterChecker, IrPackageFragment declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitPackageFragment(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitProperty(IrInterpreterChecker irInterpreterChecker, IrProperty declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitProperty(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitPropertyReference(IrInterpreterChecker irInterpreterChecker, IrPropertyReference expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitPropertyReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitRawFunctionReference(IrInterpreterChecker irInterpreterChecker, IrRawFunctionReference expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitRawFunctionReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitReturn(IrInterpreterChecker irInterpreterChecker, IrReturn expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitReturn(irInterpreterChecker, expression, data);
        }

        public static Boolean visitScript(IrInterpreterChecker irInterpreterChecker, IrScript declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitScript(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitSetField(IrInterpreterChecker irInterpreterChecker, IrSetField expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSetField(irInterpreterChecker, expression, data);
        }

        public static Boolean visitSetValue(IrInterpreterChecker irInterpreterChecker, IrSetValue expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSetValue(irInterpreterChecker, expression, data);
        }

        public static Boolean visitSimpleFunction(IrInterpreterChecker irInterpreterChecker, IrSimpleFunction declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSimpleFunction(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitSingletonReference(IrInterpreterChecker irInterpreterChecker, IrGetSingletonValue expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSingletonReference(irInterpreterChecker, expression, data);
        }

        public static Boolean visitSpreadElement(IrInterpreterChecker irInterpreterChecker, IrSpreadElement spread, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSpreadElement(irInterpreterChecker, spread, data);
        }

        public static Boolean visitStringConcatenation(IrInterpreterChecker irInterpreterChecker, IrStringConcatenation expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitStringConcatenation(irInterpreterChecker, expression, data);
        }

        public static Boolean visitSuspendableExpression(IrInterpreterChecker irInterpreterChecker, IrSuspendableExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSuspendableExpression(irInterpreterChecker, expression, data);
        }

        public static Boolean visitSuspensionPoint(IrInterpreterChecker irInterpreterChecker, IrSuspensionPoint expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSuspensionPoint(irInterpreterChecker, expression, data);
        }

        public static Boolean visitSyntheticBody(IrInterpreterChecker irInterpreterChecker, IrSyntheticBody body, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitSyntheticBody(irInterpreterChecker, body, data);
        }

        public static Boolean visitThrow(IrInterpreterChecker irInterpreterChecker, IrThrow expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitThrow(irInterpreterChecker, expression, data);
        }

        public static Boolean visitTry(IrInterpreterChecker irInterpreterChecker, IrTry aTry, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitTry(irInterpreterChecker, aTry, data);
        }

        public static Boolean visitTypeAlias(IrInterpreterChecker irInterpreterChecker, IrTypeAlias declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitTypeAlias(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitTypeOperator(IrInterpreterChecker irInterpreterChecker, IrTypeOperatorCall expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitTypeOperator(irInterpreterChecker, expression, data);
        }

        public static Boolean visitTypeParameter(IrInterpreterChecker irInterpreterChecker, IrTypeParameter declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitTypeParameter(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitValueAccess(IrInterpreterChecker irInterpreterChecker, IrValueAccessExpression expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitValueAccess(irInterpreterChecker, expression, data);
        }

        public static Boolean visitValueParameter(IrInterpreterChecker irInterpreterChecker, IrValueParameter declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitValueParameter(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitVararg(IrInterpreterChecker irInterpreterChecker, IrVararg expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitVararg(irInterpreterChecker, expression, data);
        }

        public static Boolean visitVariable(IrInterpreterChecker irInterpreterChecker, IrVariable declaration, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitVariable(irInterpreterChecker, declaration, data);
        }

        public static Boolean visitWhen(IrInterpreterChecker irInterpreterChecker, IrWhen expression, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitWhen(irInterpreterChecker, expression, data);
        }

        public static Boolean visitWhileLoop(IrInterpreterChecker irInterpreterChecker, IrWhileLoop loop, IrInterpreterCheckerData data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            return (Boolean) IrElementVisitor.DefaultImpls.visitWhileLoop(irInterpreterChecker, loop, data);
        }
    }
}
