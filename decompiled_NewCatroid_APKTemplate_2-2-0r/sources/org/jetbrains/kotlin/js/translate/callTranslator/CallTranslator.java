package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.utils.AnnotationsUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CallTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0004J,\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0007J*\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004J2\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u000e2\u0006\u0010\u0013\u001a\u00020\u00042\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0004¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/CallTranslator;", "", "()V", "buildCall", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "args", "", "dispatchReceiver", "translate", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "extensionOrDispatchReceiver", "translateGet", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "translateSet", "value", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CallTranslator {
    public static final CallTranslator INSTANCE = new CallTranslator();

    @JvmStatic
    public static final JsExpression translate(TranslationContext context, ResolvedCall<? extends FunctionDescriptor> resolvedCall) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        return translate$default(context, resolvedCall, null, 4, null);
    }

    private CallTranslator() {
    }

    public static /* synthetic */ JsExpression translate$default(TranslationContext translationContext, ResolvedCall resolvedCall, JsExpression jsExpression, int i, Object obj) {
        if ((i & 4) != 0) {
            jsExpression = null;
        }
        return translate(translationContext, resolvedCall, jsExpression);
    }

    @JvmStatic
    public static final JsExpression translate(TranslationContext context, ResolvedCall<? extends FunctionDescriptor> resolvedCall, JsExpression extensionOrDispatchReceiver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        JsExpression jsExpressionSource = CallTranslatorKt.translateCall(context, resolvedCall, new ExplicitReceivers(extensionOrDispatchReceiver, null, 2, null)).source((Object) resolvedCall.getCall().getCallElement());
        Intrinsics.checkNotNullExpressionValue(jsExpressionSource, "source(...)");
        return jsExpressionSource;
    }

    public static /* synthetic */ JsExpression translateGet$default(CallTranslator callTranslator, TranslationContext translationContext, ResolvedCall resolvedCall, JsExpression jsExpression, int i, Object obj) {
        if ((i & 4) != 0) {
            jsExpression = null;
        }
        return callTranslator.translateGet(translationContext, resolvedCall, jsExpression);
    }

    public final JsExpression translateGet(TranslationContext context, ResolvedCall<? extends VariableDescriptor> resolvedCall, JsExpression extensionOrDispatchReceiver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        VariableAccessInfo variableAccessInfo = new VariableAccessInfo(CallInfoKt.getCallInfo(context, resolvedCall, extensionOrDispatchReceiver), null);
        JsExpression jsExpressionSource = variableAccessInfo.constructSafeCallIfNeeded(VariableCallCasesKt.translateVariableAccess(variableAccessInfo)).source((Object) resolvedCall.getCall().getCallElement());
        Intrinsics.checkNotNull(jsExpressionSource);
        MetadataProperties.setType(jsExpressionSource, TranslationUtils.getReturnTypeForCoercion(((VariableDescriptor) resolvedCall.getResultingDescriptor()).getOriginal()));
        return jsExpressionSource;
    }

    public static /* synthetic */ JsExpression translateSet$default(CallTranslator callTranslator, TranslationContext translationContext, ResolvedCall resolvedCall, JsExpression jsExpression, JsExpression jsExpression2, int i, Object obj) {
        if ((i & 8) != 0) {
            jsExpression2 = null;
        }
        return callTranslator.translateSet(translationContext, resolvedCall, jsExpression, jsExpression2);
    }

    public final JsExpression translateSet(TranslationContext context, ResolvedCall<? extends VariableDescriptor> resolvedCall, JsExpression value, JsExpression extensionOrDispatchReceiver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(value, "value");
        KotlinType returnTypeForCoercion = TranslationUtils.getReturnTypeForCoercion(resolvedCall.getResultingDescriptor());
        Intrinsics.checkNotNullExpressionValue(returnTypeForCoercion, "getReturnTypeForCoercion(...)");
        JsExpression jsExpressionCoerce = TranslationUtils.coerce(context, value, returnTypeForCoercion);
        Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce, "coerce(...)");
        VariableAccessInfo variableAccessInfo = new VariableAccessInfo(CallInfoKt.getCallInfo(context, resolvedCall, extensionOrDispatchReceiver), jsExpressionCoerce);
        JsExpression jsExpressionSource = variableAccessInfo.constructSafeCallIfNeeded(VariableCallCasesKt.translateVariableAccess(variableAccessInfo)).source((Object) resolvedCall.getCall().getCallElement());
        Intrinsics.checkNotNull(jsExpressionSource);
        MetadataProperties.setType(jsExpressionSource, context.getCurrentModule().getBuiltIns().getUnitType());
        return jsExpressionSource;
    }

    public final JsExpression buildCall(TranslationContext context, FunctionDescriptor functionDescriptor, List<? extends JsExpression> args, JsExpression dispatchReceiver) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(args, "args");
        CallArgumentTranslator.ArgumentsInfo argumentsInfo = new CallArgumentTranslator.ArgumentsInfo(args, false, null, null, 8, null);
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        JsName nameForDescriptor = context.getNameForDescriptor(functionDescriptor2);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        boolean zIsNativeObject = AnnotationsUtils.isNativeObject(functionDescriptor2);
        if (dispatchReceiver != null) {
            return DefaultFunctionCallCase.INSTANCE.buildDefaultCallWithDispatchReceiver(argumentsInfo, dispatchReceiver, nameForDescriptor, zIsNativeObject, false);
        }
        return DefaultFunctionCallCase.INSTANCE.buildDefaultCallWithoutReceiver(context, argumentsInfo, functionDescriptor, zIsNativeObject, false);
    }
}
