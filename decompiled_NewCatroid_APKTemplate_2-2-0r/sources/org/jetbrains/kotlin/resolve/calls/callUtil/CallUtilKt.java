package org.jetbrains.kotlin.resolve.calls.callUtil;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.PsiUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;

/* JADX INFO: compiled from: callUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0007\u001a\u000e\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0003H\u0007\u001a\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0002*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0010\u0010\b\u001a\u0004\u0018\u00010\u0003*\u0004\u0018\u00010\u0005H\u0007\u001a(\u0010\t\u001a\u0004\u0018\u00010\n\"\b\b\u0000\u0010\u000b*\u00020\f*\b\u0012\u0004\u0012\u0002H\u000b0\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0007\u001a \u0010\u0010\u001a\f\u0012\u0006\b\u0001\u0012\u00020\f\u0018\u00010\r*\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u001a \u0010\u0010\u001a\f\u0012\u0006\b\u0001\u0012\u00020\f\u0018\u00010\r*\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u000f*\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u0003H\u0007¨\u0006\u0015"}, d2 = {"createLookupLocation", "Lorg/jetbrains/kotlin/incremental/KotlinLookupLocation;", "Lorg/jetbrains/kotlin/psi/Call;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getCall", "Lorg/jetbrains/kotlin/psi/KtElement;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getCalleeExpressionIfAny", "getParameterForArgument", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "D", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "valueArgument", "Lorg/jetbrains/kotlin/psi/ValueArgument;", "getResolvedCall", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getValueArgumentForExpression", "expression", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallUtilKt {
    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.getResolvedCall instead.", plugins = "Android in IDEA", usedIn = {IDEAPlatforms._213})
    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCall(Call call, BindingContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.getResolvedCall(call, context);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.getResolvedCall instead.", plugins = "Android in IDEA", usedIn = {IDEAPlatforms._213})
    public static final ResolvedCall<? extends CallableDescriptor> getResolvedCall(KtElement ktElement, BindingContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.getResolvedCall(ktElement, context);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.getType instead.", usedIn = {})
    public static final KotlinType getType(KtExpression ktExpression, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.getType(ktExpression, context);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.getCall instead.", plugins = "Android in IDEA", usedIn = {IDEAPlatforms._213})
    public static final Call getCall(KtElement ktElement, BindingContext context) {
        Intrinsics.checkNotNullParameter(ktElement, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.getCall(ktElement, context);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.getCalleeExpressionIfAny instead.", usedIn = {})
    public static final KtExpression getCalleeExpressionIfAny(KtElement ktElement) {
        return PsiUtilsKt.getCalleeExpressionIfAny(ktElement);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.createLookupLocation instead.", usedIn = {})
    public static final KotlinLookupLocation createLookupLocation(Call call) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.createLookupLocation(call);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.createLookupLocation instead.", usedIn = {})
    public static final KotlinLookupLocation createLookupLocation(KtExpression ktExpression) {
        Intrinsics.checkNotNullParameter(ktExpression, "<this>");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.createLookupLocation(ktExpression);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.getValueArgumentForExpression instead.", plugins = "Android in IDEA", usedIn = {IDEAPlatforms._213})
    public static final ValueArgument getValueArgumentForExpression(Call call, KtExpression expression) {
        Intrinsics.checkNotNullParameter(call, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.getValueArgumentForExpression(call, expression);
    }

    @IDEAPluginsCompatibilityAPI(message = "Use org.jetbrains.kotlin.resolve.calls.util.getValueArgumentForExpression instead.", plugins = "Android in IDEA", usedIn = {IDEAPlatforms._213})
    public static final <D extends CallableDescriptor> ValueParameterDescriptor getParameterForArgument(ResolvedCall<D> resolvedCall, ValueArgument valueArgument) {
        Intrinsics.checkNotNullParameter(resolvedCall, "<this>");
        return org.jetbrains.kotlin.resolve.calls.util.CallUtilKt.getParameterForArgument(resolvedCall, valueArgument);
    }
}
