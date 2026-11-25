package org.jetbrains.kotlin.ir.interpreter.preprocessor;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: IrInterpreterPreprocessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0002H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/preprocessor/IrInterpreterPreprocessor;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "Lorg/jetbrains/kotlin/ir/interpreter/preprocessor/IrInterpreterPreprocessorData;", "preprocess", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "file", "data", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrInterpreterPreprocessor extends IrElementTransformer<IrInterpreterPreprocessorData> {

    /* JADX INFO: compiled from: IrInterpreterPreprocessor.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static IrStatement visitAnonymousInitializer(IrInterpreterPreprocessor irInterpreterPreprocessor, IrAnonymousInitializer declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitAnonymousInitializer(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitBlock(IrInterpreterPreprocessor irInterpreterPreprocessor, IrBlock expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitBlock(irInterpreterPreprocessor, expression, data);
        }

        public static IrBody visitBlockBody(IrInterpreterPreprocessor irInterpreterPreprocessor, IrBlockBody body, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitBlockBody(irInterpreterPreprocessor, body, data);
        }

        public static IrBody visitBody(IrInterpreterPreprocessor irInterpreterPreprocessor, IrBody body, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitBody(irInterpreterPreprocessor, body, data);
        }

        public static IrBranch visitBranch(IrInterpreterPreprocessor irInterpreterPreprocessor, IrBranch branch, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitBranch(irInterpreterPreprocessor, branch, data);
        }

        public static IrExpression visitBreak(IrInterpreterPreprocessor irInterpreterPreprocessor, IrBreak jump, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitBreak(irInterpreterPreprocessor, jump, data);
        }

        public static IrExpression visitBreakContinue(IrInterpreterPreprocessor irInterpreterPreprocessor, IrBreakContinue jump, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitBreakContinue(irInterpreterPreprocessor, jump, data);
        }

        public static IrElement visitCall(IrInterpreterPreprocessor irInterpreterPreprocessor, IrCall expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitCall(irInterpreterPreprocessor, expression, data);
        }

        public static IrElement visitCallableReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrCallableReference<?> expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitCallableReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrCatch visitCatch(IrInterpreterPreprocessor irInterpreterPreprocessor, IrCatch aCatch, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(aCatch, "aCatch");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitCatch(irInterpreterPreprocessor, aCatch, data);
        }

        public static IrStatement visitClass(IrInterpreterPreprocessor irInterpreterPreprocessor, IrClass declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitClass(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitClassReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrClassReference expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitClassReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitComposite(IrInterpreterPreprocessor irInterpreterPreprocessor, IrComposite expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitComposite(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitConst(IrInterpreterPreprocessor irInterpreterPreprocessor, IrConst<?> expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitConst(irInterpreterPreprocessor, expression, data);
        }

        public static IrConstantValue visitConstantArray(IrInterpreterPreprocessor irInterpreterPreprocessor, IrConstantArray expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitConstantArray(irInterpreterPreprocessor, expression, data);
        }

        public static IrConstantValue visitConstantObject(IrInterpreterPreprocessor irInterpreterPreprocessor, IrConstantObject expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitConstantObject(irInterpreterPreprocessor, expression, data);
        }

        public static IrConstantValue visitConstantPrimitive(IrInterpreterPreprocessor irInterpreterPreprocessor, IrConstantPrimitive expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitConstantPrimitive(irInterpreterPreprocessor, expression, data);
        }

        public static IrConstantValue visitConstantValue(IrInterpreterPreprocessor irInterpreterPreprocessor, IrConstantValue expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitConstantValue(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitConstructor(IrInterpreterPreprocessor irInterpreterPreprocessor, IrConstructor declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitConstructor(irInterpreterPreprocessor, declaration, data);
        }

        public static IrElement visitConstructorCall(IrInterpreterPreprocessor irInterpreterPreprocessor, IrConstructorCall expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitConstructorCall(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitContainerExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrContainerExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitContainerExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitContinue(IrInterpreterPreprocessor irInterpreterPreprocessor, IrContinue jump, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(jump, "jump");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitContinue(irInterpreterPreprocessor, jump, data);
        }

        public static IrStatement visitDeclaration(IrInterpreterPreprocessor irInterpreterPreprocessor, IrDeclarationBase declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitDeclaration(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitDeclarationReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrDeclarationReference expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitDeclarationReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrElement visitDelegatingConstructorCall(IrInterpreterPreprocessor irInterpreterPreprocessor, IrDelegatingConstructorCall expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitDelegatingConstructorCall(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitDoWhileLoop(IrInterpreterPreprocessor irInterpreterPreprocessor, IrDoWhileLoop loop, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitDoWhileLoop(irInterpreterPreprocessor, loop, data);
        }

        public static IrExpression visitDynamicExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrDynamicExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitDynamicExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitDynamicMemberExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrDynamicMemberExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitDynamicMemberExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitDynamicOperatorExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrDynamicOperatorExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitDynamicOperatorExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrElement visitElement(IrInterpreterPreprocessor irInterpreterPreprocessor, IrElement element, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitElement(irInterpreterPreprocessor, element, data);
        }

        public static IrElseBranch visitElseBranch(IrInterpreterPreprocessor irInterpreterPreprocessor, IrElseBranch branch, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(branch, "branch");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitElseBranch(irInterpreterPreprocessor, branch, data);
        }

        public static IrElement visitEnumConstructorCall(IrInterpreterPreprocessor irInterpreterPreprocessor, IrEnumConstructorCall expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitEnumConstructorCall(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitEnumEntry(IrInterpreterPreprocessor irInterpreterPreprocessor, IrEnumEntry declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitEnumEntry(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitErrorCallExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrErrorCallExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitErrorCallExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitErrorDeclaration(IrInterpreterPreprocessor irInterpreterPreprocessor, IrErrorDeclaration declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitErrorDeclaration(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitErrorExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrErrorExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitErrorExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrBody visitExpressionBody(IrInterpreterPreprocessor irInterpreterPreprocessor, IrExpressionBody body, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitExpressionBody(irInterpreterPreprocessor, body, data);
        }

        public static IrExternalPackageFragment visitExternalPackageFragment(IrInterpreterPreprocessor irInterpreterPreprocessor, IrExternalPackageFragment declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitExternalPackageFragment(irInterpreterPreprocessor, declaration, data);
        }

        public static IrStatement visitField(IrInterpreterPreprocessor irInterpreterPreprocessor, IrField declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitField(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitFieldAccess(IrInterpreterPreprocessor irInterpreterPreprocessor, IrFieldAccessExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitFieldAccess(irInterpreterPreprocessor, expression, data);
        }

        public static IrFile visitFile(IrInterpreterPreprocessor irInterpreterPreprocessor, IrFile declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitFile(irInterpreterPreprocessor, declaration, data);
        }

        public static IrStatement visitFunction(IrInterpreterPreprocessor irInterpreterPreprocessor, IrFunction declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitFunction(irInterpreterPreprocessor, declaration, data);
        }

        public static IrElement visitFunctionAccess(IrInterpreterPreprocessor irInterpreterPreprocessor, IrFunctionAccessExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitFunctionAccess(irInterpreterPreprocessor, expression, data);
        }

        public static IrElement visitFunctionExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrFunctionExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitFunctionExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrElement visitFunctionReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrFunctionReference expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitFunctionReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitGetClass(IrInterpreterPreprocessor irInterpreterPreprocessor, IrGetClass expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitGetClass(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitGetEnumValue(IrInterpreterPreprocessor irInterpreterPreprocessor, IrGetEnumValue expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitGetEnumValue(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitGetField(IrInterpreterPreprocessor irInterpreterPreprocessor, IrGetField expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitGetField(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitGetObjectValue(IrInterpreterPreprocessor irInterpreterPreprocessor, IrGetObjectValue expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitGetObjectValue(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitGetValue(IrInterpreterPreprocessor irInterpreterPreprocessor, IrGetValue expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitGetValue(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitInstanceInitializerCall(IrInterpreterPreprocessor irInterpreterPreprocessor, IrInstanceInitializerCall expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitInstanceInitializerCall(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitLocalDelegatedProperty(IrInterpreterPreprocessor irInterpreterPreprocessor, IrLocalDelegatedProperty declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedProperty(irInterpreterPreprocessor, declaration, data);
        }

        public static IrElement visitLocalDelegatedPropertyReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrLocalDelegatedPropertyReference expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitLocalDelegatedPropertyReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitLoop(IrInterpreterPreprocessor irInterpreterPreprocessor, IrLoop loop, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitLoop(irInterpreterPreprocessor, loop, data);
        }

        public static IrElement visitMemberAccess(IrInterpreterPreprocessor irInterpreterPreprocessor, IrMemberAccessExpression<?> expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitMemberAccess(irInterpreterPreprocessor, expression, data);
        }

        public static IrModuleFragment visitModuleFragment(IrInterpreterPreprocessor irInterpreterPreprocessor, IrModuleFragment declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitModuleFragment(irInterpreterPreprocessor, declaration, data);
        }

        public static IrElement visitPackageFragment(IrInterpreterPreprocessor irInterpreterPreprocessor, IrPackageFragment declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitPackageFragment(irInterpreterPreprocessor, declaration, data);
        }

        public static IrStatement visitProperty(IrInterpreterPreprocessor irInterpreterPreprocessor, IrProperty declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitProperty(irInterpreterPreprocessor, declaration, data);
        }

        public static IrElement visitPropertyReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrPropertyReference expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitPropertyReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitRawFunctionReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrRawFunctionReference expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitRawFunctionReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitReturn(IrInterpreterPreprocessor irInterpreterPreprocessor, IrReturn expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitReturn(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitScript(IrInterpreterPreprocessor irInterpreterPreprocessor, IrScript declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitScript(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitSetField(IrInterpreterPreprocessor irInterpreterPreprocessor, IrSetField expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSetField(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitSetValue(IrInterpreterPreprocessor irInterpreterPreprocessor, IrSetValue expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSetValue(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitSimpleFunction(IrInterpreterPreprocessor irInterpreterPreprocessor, IrSimpleFunction declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSimpleFunction(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitSingletonReference(IrInterpreterPreprocessor irInterpreterPreprocessor, IrGetSingletonValue expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSingletonReference(irInterpreterPreprocessor, expression, data);
        }

        public static IrSpreadElement visitSpreadElement(IrInterpreterPreprocessor irInterpreterPreprocessor, IrSpreadElement spread, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(spread, "spread");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSpreadElement(irInterpreterPreprocessor, spread, data);
        }

        public static IrExpression visitStringConcatenation(IrInterpreterPreprocessor irInterpreterPreprocessor, IrStringConcatenation expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitStringConcatenation(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitSuspendableExpression(IrInterpreterPreprocessor irInterpreterPreprocessor, IrSuspendableExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSuspendableExpression(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitSuspensionPoint(IrInterpreterPreprocessor irInterpreterPreprocessor, IrSuspensionPoint expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSuspensionPoint(irInterpreterPreprocessor, expression, data);
        }

        public static IrBody visitSyntheticBody(IrInterpreterPreprocessor irInterpreterPreprocessor, IrSyntheticBody body, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(body, "body");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitSyntheticBody(irInterpreterPreprocessor, body, data);
        }

        public static IrExpression visitThrow(IrInterpreterPreprocessor irInterpreterPreprocessor, IrThrow expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitThrow(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitTry(IrInterpreterPreprocessor irInterpreterPreprocessor, IrTry aTry, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(aTry, "aTry");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitTry(irInterpreterPreprocessor, aTry, data);
        }

        public static IrStatement visitTypeAlias(IrInterpreterPreprocessor irInterpreterPreprocessor, IrTypeAlias declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitTypeAlias(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitTypeOperator(IrInterpreterPreprocessor irInterpreterPreprocessor, IrTypeOperatorCall expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitTypeOperator(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitTypeParameter(IrInterpreterPreprocessor irInterpreterPreprocessor, IrTypeParameter declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitTypeParameter(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitValueAccess(IrInterpreterPreprocessor irInterpreterPreprocessor, IrValueAccessExpression expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitValueAccess(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitValueParameter(IrInterpreterPreprocessor irInterpreterPreprocessor, IrValueParameter declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitValueParameter(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitVararg(IrInterpreterPreprocessor irInterpreterPreprocessor, IrVararg expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitVararg(irInterpreterPreprocessor, expression, data);
        }

        public static IrStatement visitVariable(IrInterpreterPreprocessor irInterpreterPreprocessor, IrVariable declaration, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitVariable(irInterpreterPreprocessor, declaration, data);
        }

        public static IrExpression visitWhen(IrInterpreterPreprocessor irInterpreterPreprocessor, IrWhen expression, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitWhen(irInterpreterPreprocessor, expression, data);
        }

        public static IrExpression visitWhileLoop(IrInterpreterPreprocessor irInterpreterPreprocessor, IrWhileLoop loop, IrInterpreterPreprocessorData data) {
            Intrinsics.checkNotNullParameter(loop, "loop");
            Intrinsics.checkNotNullParameter(data, "data");
            return IrElementTransformer.DefaultImpls.visitWhileLoop(irInterpreterPreprocessor, loop, data);
        }
    }

    default IrFile preprocess(IrFile file, IrInterpreterPreprocessorData data) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(data, "data");
        return file.transform((IrElementTransformer<? super IrInterpreterPreprocessorData>) this, data);
    }
}
