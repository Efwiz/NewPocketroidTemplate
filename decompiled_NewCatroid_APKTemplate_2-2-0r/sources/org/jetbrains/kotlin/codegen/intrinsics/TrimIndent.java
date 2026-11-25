package org.jetbrains.kotlin.codegen.intrinsics;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.Callable;
import org.jetbrains.kotlin.codegen.ExpressionCodegen;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;

/* JADX INFO: compiled from: Trim.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001e\u0010\r\u001a\u0004\u0018\u00010\u00042\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/intrinsics/TrimIndent;", "Lorg/jetbrains/kotlin/codegen/intrinsics/IntrinsicMethod;", "()V", "toCallable", "Lorg/jetbrains/kotlin/codegen/Callable;", "fd", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "isSuper", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "tryApply", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TrimIndent extends IntrinsicMethod {
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
        KtExpression receiverExpression = CallUtilKt.getReceiverExpression(resolvedCall);
        if (receiverExpression != null) {
            ConstantValue<?> compileTimeConstant = codegen.getCompileTimeConstant(receiverExpression);
            StringValue stringValue = compileTimeConstant instanceof StringValue ? (StringValue) compileTimeConstant : null;
            if (stringValue != null && (value = stringValue.getValue()) != null) {
                return new StringConstant(StringsKt.trimIndent(value));
            }
        }
        return null;
    }
}
