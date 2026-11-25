package org.jetbrains.kotlin.backend.jvm.intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.codegen.BlockInfo;
import org.jetbrains.kotlin.backend.jvm.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.backend.jvm.codegen.IrCallGenerator;
import org.jetbrains.kotlin.backend.jvm.codegen.MaterialValue;
import org.jetbrains.kotlin.backend.jvm.codegen.PromisedValue;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: JvmInvokeDynamic.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\r\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0018\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0002J \u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/intrinsics/JvmInvokeDynamic;", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", "()V", "evalBootstrapArgumentIntrinsicCall", "", "irCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "codegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ExpressionCodegen;", "evalMethodHandle", "Lorg/jetbrains/org/objectweb/asm/Handle;", "evalMethodType", "Lorg/jetbrains/org/objectweb/asm/Type;", "evalOriginalMethodType", "generateBootstrapMethodArg", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/expressions/IrVarargElement;", "generateMethodHandle", "irRawFunctionReference", "Lorg/jetbrains/kotlin/ir/expressions/IrRawFunctionReference;", "invoke", "Lorg/jetbrains/kotlin/backend/jvm/codegen/PromisedValue;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "data", "Lorg/jetbrains/kotlin/backend/jvm/codegen/BlockInfo;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmInvokeDynamic extends IntrinsicMethod {
    public static final JvmInvokeDynamic INSTANCE = new JvmInvokeDynamic();

    private JvmInvokeDynamic() {
    }

    private static final Void invoke$fail(IrFunctionAccessExpression irFunctionAccessExpression, String str) {
        throw new AssertionError(str + "; expression:\n" + DumpIrTreeKt.dump$default(irFunctionAccessExpression, null, 1, null));
    }

    @Override // org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod
    public PromisedValue invoke(IrFunctionAccessExpression expression, ExpressionCodegen codegen, BlockInfo data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(data, "data");
        IrExpression valueArgument = expression.getValueArgument(0);
        IrCall irCall = valueArgument instanceof IrCall ? (IrCall) valueArgument : null;
        if (irCall == null) {
            invoke$fail(expression, "'dynamicCall' is expected to be a call");
            throw null;
        }
        IrSimpleFunction owner = irCall.getSymbol().getOwner();
        if (!Intrinsics.areEqual(owner.getParent(), codegen.getContext().getIr().getSymbols().getKotlinJvmInternalInvokeDynamicPackage()) || !Intrinsics.areEqual(owner.getOrigin(), JvmLoweredDeclarationOrigin.INVOKEDYNAMIC_CALL_TARGET.INSTANCE)) {
            invoke$fail(expression, "Unexpected dynamicCallee: '" + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null) + '\'');
            throw null;
        }
        IrExpression valueArgument2 = expression.getValueArgument(1);
        IrCall irCall2 = valueArgument2 instanceof IrCall ? (IrCall) valueArgument2 : null;
        if (irCall2 == null) {
            invoke$fail(expression, "'bootstrapMethodHandle' should be a call");
            throw null;
        }
        Handle handleEvalMethodHandle = evalMethodHandle(irCall2);
        IrExpression valueArgument3 = expression.getValueArgument(2);
        IrVararg irVararg = valueArgument3 instanceof IrVararg ? (IrVararg) valueArgument3 : null;
        if (irVararg == null) {
            invoke$fail(expression, "'bootstrapMethodArgs' is expected to be a vararg");
            throw null;
        }
        List<IrVarargElement> elements = irVararg.getElements();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(elements, 10));
        Iterator<T> it = elements.iterator();
        while (it.hasNext()) {
            arrayList.add(INSTANCE.generateBootstrapMethodArg((IrVarargElement) it.next(), codegen));
        }
        Object[] array = arrayList.toArray(new Object[0]);
        Method methodMapAsmMethod = codegen.getMethodSignatureMapper().mapAsmMethod(owner);
        IrCallGenerator.DefaultCallGenerator defaultCallGenerator = IrCallGenerator.DefaultCallGenerator.INSTANCE;
        Type[] argumentTypes = methodMapAsmMethod.getArgumentTypes();
        int i = 0;
        for (int size = owner.getValueParameters().size(); i < size; size = size) {
            IrValueParameter irValueParameter = owner.getValueParameters().get(i);
            IrExpression valueArgument4 = irCall.getValueArgument(i);
            if (valueArgument4 == null) {
                invoke$fail(expression, "No argument #" + i + " in 'dynamicCall'");
                throw null;
            }
            Intrinsics.checkNotNull(argumentTypes);
            if (i < 0 || i > ArraysKt.getLastIndex(argumentTypes)) {
                invoke$fail(expression, "No argument type #" + i + " in dynamic callee: " + methodMapAsmMethod);
                throw null;
            }
            Type type = argumentTypes[i];
            Intrinsics.checkNotNull(type);
            defaultCallGenerator.genValueAndPut(irValueParameter, valueArgument4, type, codegen, data);
            i++;
        }
        codegen.getMv().invokedynamic(methodMapAsmMethod.getName(), methodMapAsmMethod.getDescriptor(), handleEvalMethodHandle, array);
        Type returnType = methodMapAsmMethod.getReturnType();
        Intrinsics.checkNotNullExpressionValue(returnType, "getReturnType(...)");
        return new MaterialValue(codegen, returnType, expression.getType());
    }

    private final Object generateBootstrapMethodArg(IrVarargElement element, ExpressionCodegen codegen) {
        if (element instanceof IrRawFunctionReference) {
            return generateMethodHandle((IrRawFunctionReference) element, codegen);
        }
        if (element instanceof IrCall) {
            Object objEvalBootstrapArgumentIntrinsicCall = evalBootstrapArgumentIntrinsicCall((IrCall) element, codegen);
            if (objEvalBootstrapArgumentIntrinsicCall != null) {
                return objEvalBootstrapArgumentIntrinsicCall;
            }
            throw new AssertionError("Unexpected callee in bootstrap method argument:\n" + DumpIrTreeKt.dump$default(element, null, 1, null));
        }
        if (element instanceof IrConst) {
            IrConst irConst = (IrConst) element;
            IrConstKind kind = irConst.getKind();
            if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
                Object value = irConst.getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Byte");
                return Integer.valueOf(((Byte) value).byteValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
                Object value2 = irConst.getValue();
                Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Short");
                return Integer.valueOf(((Short) value2).shortValue());
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
                Object value3 = irConst.getValue();
                Intrinsics.checkNotNull(value3, "null cannot be cast to non-null type kotlin.Int");
                return (Integer) value3;
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
                Object value4 = irConst.getValue();
                Intrinsics.checkNotNull(value4, "null cannot be cast to non-null type kotlin.Long");
                return (Long) value4;
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Float.INSTANCE)) {
                Object value5 = irConst.getValue();
                Intrinsics.checkNotNull(value5, "null cannot be cast to non-null type kotlin.Float");
                return (Float) value5;
            }
            if (Intrinsics.areEqual(kind, IrConstKind.Double.INSTANCE)) {
                Object value6 = irConst.getValue();
                Intrinsics.checkNotNull(value6, "null cannot be cast to non-null type kotlin.Double");
                return (Double) value6;
            }
            if (!Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE)) {
                throw new AssertionError("Unexpected constant expression in bootstrap method argument:\n" + DumpIrTreeKt.dump$default(element, null, 1, null));
            }
            Object value7 = irConst.getValue();
            Intrinsics.checkNotNull(value7, "null cannot be cast to non-null type kotlin.String");
            return (String) value7;
        }
        throw new AssertionError("Unexpected bootstrap method argument:\n" + DumpIrTreeKt.dump$default(element, null, 1, null));
    }

    private final Object evalBootstrapArgumentIntrinsicCall(IrCall irCall, ExpressionCodegen codegen) {
        IrSimpleFunctionSymbol symbol = irCall.getSymbol();
        if (Intrinsics.areEqual(symbol, codegen.getContext().getIr().getSymbols().getJvmOriginalMethodTypeIntrinsic())) {
            return evalOriginalMethodType(irCall, codegen);
        }
        if (Intrinsics.areEqual(symbol, codegen.getContext().getIr().getSymbols().getJvmMethodType())) {
            return evalMethodType(irCall);
        }
        if (Intrinsics.areEqual(symbol, codegen.getContext().getIr().getSymbols().getJvmMethodHandle())) {
            return evalMethodHandle(irCall);
        }
        return null;
    }

    private final Type evalMethodType(IrCall irCall) {
        Type methodType = Type.getMethodType(JvmIrUtilsKt.getStringConstArgument(irCall, 0));
        Intrinsics.checkNotNullExpressionValue(methodType, "getMethodType(...)");
        return methodType;
    }

    private final Handle evalMethodHandle(IrCall irCall) {
        IrCall irCall2 = irCall;
        return new Handle(JvmIrUtilsKt.getIntConstArgument(irCall2, 0), JvmIrUtilsKt.getStringConstArgument(irCall2, 1), JvmIrUtilsKt.getStringConstArgument(irCall2, 2), JvmIrUtilsKt.getStringConstArgument(irCall2, 3), JvmIrUtilsKt.getBooleanConstArgument(irCall2, 4));
    }

    private final Handle generateMethodHandle(IrRawFunctionReference irRawFunctionReference, ExpressionCodegen codegen) {
        return codegen.getMethodSignatureMapper().mapToMethodHandle(irRawFunctionReference.getSymbol().getOwner());
    }

    private final Type evalOriginalMethodType(IrCall irCall, ExpressionCodegen codegen) {
        IrExpression valueArgument = irCall.getValueArgument(0);
        IrRawFunctionReference irRawFunctionReference = valueArgument instanceof IrRawFunctionReference ? (IrRawFunctionReference) valueArgument : null;
        if (irRawFunctionReference == null) {
            throw new AssertionError("Argument in " + irCall.getSymbol().getOwner().getName() + " call is expected to be a raw function reference:\n" + DumpIrTreeKt.dump$default(irCall, null, 1, null));
        }
        Type methodType = Type.getMethodType(codegen.getMethodSignatureMapper().mapAsmMethod(irRawFunctionReference.getSymbol().getOwner()).getDescriptor());
        Intrinsics.checkNotNullExpressionValue(methodType, "getMethodType(...)");
        return methodType;
    }
}
