package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: IrConstructorCallTypeArguments.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u001a\u0014\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u0001\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0002\u001a\u0014\u0010\u0011\u001a\u0004\u0018\u00010\r*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u0001\u001a\u0012\u0010\u0012\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0001\u001a\n\u0010\u0014\u001a\u00020\u0015*\u00020\u0002\u001a\u001c\u0010\u0016\u001a\u00020\u0017*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\r\u001a\u001c\u0010\u0019\u001a\u00020\u0017*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\r\u001a\u001f\u0010\u001a\u001a\u00020\u0017*\u00020\u00102\u0006\u0010\u000e\u001a\u00020\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\rH\u0086\u0002\u001a\u001f\u0010\u001a\u001a\u00020\u0017*\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u00012\b\u0010\u0018\u001a\u0004\u0018\u00010\rH\u0086\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\",\u0010\u0007\u001a\u0004\u0018\u00010\u0006*\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u001b"}, d2 = {"classTypeArgumentsCount", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getClassTypeArgumentsCount", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)I", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "outerClassReceiver", "getOuterClassReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setOuterClassReceiver", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getClassTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrType;", "index", "getClassTypeArguments", "Lorg/jetbrains/kotlin/ir/expressions/ClassTypeArguments;", "getConstructorTypeArgument", "getConstructorTypeArgumentIndex", "constructorTypeArgumentIndex", "getConstructorTypeArguments", "Lorg/jetbrains/kotlin/ir/expressions/ConstructorTypeArguments;", "putClassTypeArgument", "", ModuleXmlParser.TYPE, "putConstructorTypeArgument", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrConstructorCallTypeArgumentsKt {
    public static final int getConstructorTypeArgumentIndex(IrConstructorCall irConstructorCall, int i) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        return (irConstructorCall.getTypeArgumentsCount() - irConstructorCall.getConstructorTypeArgumentsCount()) + i;
    }

    public static final IrType getConstructorTypeArgument(IrConstructorCall irConstructorCall, int i) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        return irConstructorCall.getTypeArgument(getConstructorTypeArgumentIndex(irConstructorCall, i));
    }

    public static final void putConstructorTypeArgument(IrConstructorCall irConstructorCall, int i, IrType irType) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        irConstructorCall.putTypeArgument(getConstructorTypeArgumentIndex(irConstructorCall, i), irType);
    }

    public static final void set(ConstructorTypeArguments constructorTypeArguments, int i, IrType irType) {
        Intrinsics.checkNotNullParameter(constructorTypeArguments, "<this>");
        if (i >= constructorTypeArguments.size()) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + constructorTypeArguments.size());
        }
        putConstructorTypeArgument(constructorTypeArguments.getIrConstructorCall(), i, irType);
    }

    public static final int getClassTypeArgumentsCount(IrConstructorCall irConstructorCall) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        return irConstructorCall.getTypeArgumentsCount() - irConstructorCall.getConstructorTypeArgumentsCount();
    }

    public static final IrType getClassTypeArgument(IrConstructorCall irConstructorCall, int i) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        return irConstructorCall.getTypeArgument(i);
    }

    public static final void putClassTypeArgument(IrConstructorCall irConstructorCall, int i, IrType irType) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        irConstructorCall.putTypeArgument(i, irType);
    }

    public static final void set(ClassTypeArguments classTypeArguments, int i, IrType irType) {
        Intrinsics.checkNotNullParameter(classTypeArguments, "<this>");
        if (i >= classTypeArguments.size()) {
            throw new IndexOutOfBoundsException("index: " + i + ", size: " + classTypeArguments.size());
        }
        putClassTypeArgument(classTypeArguments.getIrConstructorCall(), i, irType);
    }

    public static final ConstructorTypeArguments getConstructorTypeArguments(IrConstructorCall irConstructorCall) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        return new ConstructorTypeArguments(irConstructorCall);
    }

    public static final ClassTypeArguments getClassTypeArguments(IrConstructorCall irConstructorCall) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        return new ClassTypeArguments(irConstructorCall);
    }

    public static final IrExpression getOuterClassReceiver(IrConstructorCall irConstructorCall) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        return irConstructorCall.getDispatchReceiver();
    }

    public static final void setOuterClassReceiver(IrConstructorCall irConstructorCall, IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        irConstructorCall.setDispatchReceiver(irExpression);
    }
}
