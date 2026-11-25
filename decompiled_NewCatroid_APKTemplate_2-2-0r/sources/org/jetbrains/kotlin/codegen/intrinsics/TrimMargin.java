package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.DefaultValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ExpressionValueArgument;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;

/* JADX INFO: compiled from: Trim.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001e\u0010\r\u001a\u0004\u0018\u00010\u00042\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/TrimMargin;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "fd", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isSuper", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "tryApply", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrimMargin extends IntrinsicMethod {
    @Override // org.jetbrains.kotlin.codegen.intrinsics.IntrinsicMethod
    public Callable toCallable(FunctionDescriptor fd, boolean isSuper, ResolvedCall<?> resolvedCall, ExpressionCodegen codegen) {
        Intrinsics.checkNotNullParameter(fd, "fd");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Callable callableTryApply = tryApply(resolvedCall, codegen);
        return callableTryApply == null ? KotlinTypeMapper.mapToCallableMethod$default(codegen.getState().getTypeMapper(), fd, false, null, null, 12, null) : callableTryApply;
    }

    private final Callable tryApply(ResolvedCall<?> resolvedCall, ExpressionCodegen codegen) {
        String value;
        KtExpression argumentExpression;
        String value2;
        String strTrimMargin;
        KtExpression receiverExpression = CallUtilKt.getReceiverExpression(resolvedCall);
        if (receiverExpression != null) {
            ConstantValue<?> compileTimeConstant = codegen.getCompileTimeConstant(receiverExpression);
            StringValue stringValue = compileTimeConstant instanceof StringValue ? (StringValue) compileTimeConstant : null;
            if (stringValue != null && (value = stringValue.getValue()) != null) {
                ResolvedValueArgument resolvedValueArgument = (ResolvedValueArgument) CollectionsKt.single(resolvedCall.getValueArguments().values());
                if (!(resolvedValueArgument instanceof DefaultValueArgument)) {
                    if (resolvedValueArgument instanceof ExpressionValueArgument) {
                        ValueArgument valueArgument = ((ExpressionValueArgument) resolvedValueArgument).getValueArgument();
                        if (valueArgument != null && (argumentExpression = valueArgument.getArgumentExpression()) != null) {
                            ConstantValue<?> compileTimeConstant2 = codegen.getCompileTimeConstant(argumentExpression);
                            StringValue stringValue2 = compileTimeConstant2 instanceof StringValue ? (StringValue) compileTimeConstant2 : null;
                            if (stringValue2 != null && (value2 = stringValue2.getValue()) != null) {
                                try {
                                    strTrimMargin = StringsKt.trimMargin(value, value2);
                                } catch (IllegalArgumentException unused) {
                                }
                            }
                        }
                        return null;
                    }
                    throw new IllegalStateException(("Unknown value argument type " + Reflection.getOrCreateKotlinClass(resolvedValueArgument.getClass()) + ": " + resolvedValueArgument).toString());
                }
                strTrimMargin = StringsKt.trimMargin$default(value, null, 1, null);
                return new StringConstant(strTrimMargin);
            }
        }
        return null;
    }
}
