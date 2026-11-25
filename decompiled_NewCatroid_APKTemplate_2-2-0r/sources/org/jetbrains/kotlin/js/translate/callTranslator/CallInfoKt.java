package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.contracts.parsing.PsiContractsUtilsKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsConditional;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslator;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.js.translate.utils.UtilsKt;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CallInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0002\u001a$\u0010\b\u001a\u00020\u0001*\u00020\u00022\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00042\b\u0010\t\u001a\u0004\u0018\u00010\n\u001a\"\u0010\b\u001a\u00020\u000b*\u00020\u00022\u000e\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u00042\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u0014\u0010\r\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000fH\u0002¨\u0006\u0010"}, d2 = {"createCallInfo", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "explicitReceivers", "Lorg/jetbrains/kotlin/js/translate/callTranslator/ExplicitReceivers;", "getCallInfo", "extensionOrDispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/FunctionCallInfo;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getDispatchReceiver", "receiverValue", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CallInfoKt {

    /* JADX INFO: compiled from: CallInfo.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ExplicitReceiverKind.values().length];
            try {
                iArr[ExplicitReceiverKind.DISPATCH_RECEIVER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ExplicitReceiverKind.BOTH_RECEIVERS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ExplicitReceiverKind.EXTENSION_RECEIVER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final CallInfo getCallInfo(TranslationContext translationContext, ResolvedCall<? extends CallableDescriptor> resolvedCall, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        return createCallInfo(translationContext, resolvedCall, new ExplicitReceivers(jsExpression, null, 2, null));
    }

    public static final FunctionCallInfo getCallInfo(TranslationContext translationContext, ResolvedCall<? extends FunctionDescriptor> resolvedCall, ExplicitReceivers explicitReceivers) {
        Intrinsics.checkNotNullParameter(translationContext, "<this>");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(explicitReceivers, "explicitReceivers");
        JsBlock jsBlock = new JsBlock();
        CallArgumentTranslator.ArgumentsInfo argumentsInfoTranslate = CallArgumentTranslator.INSTANCE.translate(resolvedCall, explicitReceivers.getExtensionOrDispatchReceiver(), translationContext, jsBlock);
        if (!jsBlock.isEmpty() && explicitReceivers.getExtensionOrDispatchReceiver() != null) {
            JsExpression jsExpressionCacheExpressionIfNeeded = translationContext.cacheExpressionIfNeeded(explicitReceivers.getExtensionOrDispatchReceiver());
            Intrinsics.checkNotNullExpressionValue(jsExpressionCacheExpressionIfNeeded, "cacheExpressionIfNeeded(...)");
            JsExpression extensionReceiver = explicitReceivers.getExtensionReceiver();
            if (extensionReceiver != null) {
                JsExpression extensionReceiver2 = explicitReceivers.getExtensionReceiver();
                Intrinsics.checkNotNull(extensionReceiver2);
                extensionReceiver = translationContext.defineTemporary(extensionReceiver2);
            }
            explicitReceivers = new ExplicitReceivers(jsExpressionCacheExpressionIfNeeded, extensionReceiver);
        }
        translationContext.addStatementsToCurrentBlockFrom(jsBlock);
        return new FunctionCallInfo(createCallInfo(translationContext, resolvedCall, explicitReceivers), argumentsInfoTranslate);
    }

    private static final JsExpression getDispatchReceiver(TranslationContext translationContext, ReceiverValue receiverValue) {
        JsExpression dispatchReceiver = translationContext.getDispatchReceiver(JsDescriptorUtils.getReceiverParameterForReceiver(receiverValue));
        Intrinsics.checkNotNullExpressionValue(dispatchReceiver, "getDispatchReceiver(...)");
        return dispatchReceiver;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [T, org.jetbrains.kotlin.js.backend.ast.JsExpression] */
    /* JADX WARN: Type inference failed for: r10v1, types: [T, org.jetbrains.kotlin.js.backend.ast.JsExpression] */
    /* JADX WARN: Type inference failed for: r10v22, types: [T, org.jetbrains.kotlin.js.backend.ast.JsConditional] */
    /* JADX WARN: Type inference failed for: r10v25, types: [T, org.jetbrains.kotlin.js.backend.ast.JsExpression] */
    /* JADX WARN: Type inference failed for: r10v28, types: [T, org.jetbrains.kotlin.js.backend.ast.JsConditional] */
    /* JADX WARN: Type inference failed for: r10v31, types: [T, org.jetbrains.kotlin.js.backend.ast.JsExpression] */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, org.jetbrains.kotlin.js.backend.ast.JsExpression] */
    private static final CallInfo createCallInfo(TranslationContext translationContext, ResolvedCall<? extends CallableDescriptor> resolvedCall, ExplicitReceivers explicitReceivers) {
        CallableMemberDescriptor.Kind kind;
        ExplicitReceiverKind explicitReceiverKind = resolvedCall.getExplicitReceiverKind();
        Intrinsics.checkNotNullExpressionValue(explicitReceiverKind, "getExplicitReceiverKind(...)");
        Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = createCallInfo$getDispatchReceiver(resolvedCall, explicitReceiverKind, explicitReceivers, translationContext);
        KotlinType smartCastDispatchReceiverType = resolvedCall.getSmartCastDispatchReceiverType();
        T tCoerce = 0;
        if (smartCastDispatchReceiverType == null) {
            ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
            smartCastDispatchReceiverType = dispatchReceiver != null ? dispatchReceiver.getType() : null;
        }
        if (smartCastDispatchReceiverType != null) {
            CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
            FunctionDescriptor functionDescriptor = resultingDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) resultingDescriptor : null;
            if (functionDescriptor != null && (kind = functionDescriptor.getKind()) != null && !kind.isReal()) {
                smartCastDispatchReceiverType = TranslationUtils.getDispatchReceiverTypeForCoercion(resolvedCall.getResultingDescriptor());
            } else if (KotlinBuiltIns.isChar(smartCastDispatchReceiverType) && PsiContractsUtilsKt.isEqualsDescriptor(resolvedCall.getResultingDescriptor())) {
                Collection<? extends CallableDescriptor> overriddenDescriptors = resolvedCall.getResultingDescriptor().getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = ((CallableDescriptor) CollectionsKt.single(overriddenDescriptors)).mo6753getDispatchReceiverParameter();
                Intrinsics.checkNotNull(receiverParameterDescriptorMo6753getDispatchReceiverParameter);
                smartCastDispatchReceiverType = receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType();
            }
        }
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        objectRef2.element = createCallInfo$getExtensionReceiver(resolvedCall, explicitReceiverKind, explicitReceivers, translationContext);
        Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        Call call = resolvedCall.getCall();
        Intrinsics.checkNotNullExpressionValue(call, "getCall(...)");
        if (CallUtilKt.isSafeCall(call)) {
            int i = WhenMappings.$EnumSwitchMapping$0[resolvedCall.getExplicitReceiverKind().ordinal()];
            if (i == 2 || i == 3) {
                T t = objectRef2.element;
                Intrinsics.checkNotNull(t);
                objectRef3.element = TranslationUtils.notNullConditional((JsExpression) t, new JsNullLiteral(), translationContext);
                objectRef2.element = ((JsConditional) objectRef3.element).getThenExpression();
            } else {
                T t2 = objectRef.element;
                Intrinsics.checkNotNull(t2);
                objectRef3.element = TranslationUtils.notNullConditional((JsExpression) t2, new JsNullLiteral(), translationContext);
                objectRef.element = ((JsConditional) objectRef3.element).getThenExpression();
            }
        }
        if (objectRef.element == 0) {
            DeclarationDescriptor containingDeclaration = resolvedCall.getResultingDescriptor().getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (DescriptorUtils.isObject(containingDeclaration)) {
                objectRef.element = ReferenceTranslator.translateAsValueReference(containingDeclaration, translationContext);
                smartCastDispatchReceiverType = ((ClassDescriptor) containingDeclaration).getDefaultType();
            }
        }
        if (smartCastDispatchReceiverType != null) {
            JsExpression jsExpression = (JsExpression) objectRef.element;
            objectRef.element = jsExpression != null ? TranslationUtils.coerce(translationContext, jsExpression, smartCastDispatchReceiverType) : 0;
        }
        JsExpression jsExpression2 = (JsExpression) objectRef2.element;
        if (jsExpression2 != null) {
            ReceiverParameterDescriptor extensionReceiverParameter = resolvedCall.get$suspendPropertyDescriptor().getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            tCoerce = TranslationUtils.coerce(translationContext, jsExpression2, extensionReceiverParameter.getType());
        }
        objectRef2.element = tCoerce;
        return new AbstractCallInfo(translationContext, resolvedCall, objectRef, objectRef2, objectRef3) { // from class: org.jetbrains.kotlin.js.translate.callTranslator.CallInfoKt.createCallInfo.3
            final /* synthetic */ ResolvedCall<? extends CallableDescriptor> $resolvedCall;
            private final TranslationContext context;
            private final JsExpression dispatchReceiver;
            private final JsExpression extensionReceiver;
            private final JsConditional notNullConditionalForSafeCall;
            private final ResolvedCall<? extends CallableDescriptor> resolvedCall;

            {
                this.$resolvedCall = resolvedCall;
                this.context = translationContext;
                this.resolvedCall = resolvedCall;
                this.dispatchReceiver = objectRef.element;
                this.extensionReceiver = objectRef2.element;
                this.notNullConditionalForSafeCall = objectRef3.element;
            }

            @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
            public TranslationContext getContext() {
                return this.context;
            }

            @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
            public ResolvedCall<? extends CallableDescriptor> getResolvedCall() {
                return this.resolvedCall;
            }

            @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
            public JsExpression getDispatchReceiver() {
                return this.dispatchReceiver;
            }

            @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
            public JsExpression getExtensionReceiver() {
                return this.extensionReceiver;
            }

            public final JsConditional getNotNullConditionalForSafeCall() {
                return this.notNullConditionalForSafeCall;
            }

            @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
            public JsExpression constructSafeCallIfNeeded(JsExpression result) {
                Intrinsics.checkNotNullParameter(result, "result");
                if (this.notNullConditionalForSafeCall == null) {
                    return result;
                }
                KotlinType returnType = CallTranslatorKt.getReturnType(this.$resolvedCall);
                MetadataProperties.setType(result, returnType);
                this.notNullConditionalForSafeCall.setThenExpression(TranslationUtils.coerce(getContext(), result, TypeUtilsKt.makeNullable(returnType)));
                return this.notNullConditionalForSafeCall;
            }

            @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallInfo
            public JsStatement constructSuspendSafeCallIfNeeded(JsStatement result) {
                JsBlock jsBlock;
                Intrinsics.checkNotNullParameter(result, "result");
                if (this.notNullConditionalForSafeCall == null) {
                    return result;
                }
                KtElement callElement = this.$resolvedCall.getCall().getCallElement();
                Intrinsics.checkNotNullExpressionValue(callElement, "getCallElement(...)");
                JsExpression jsExpressionCreateCoroutineResult = UtilsKt.createCoroutineResult(getContext(), this.$resolvedCall);
                JsExpression jsExpressionSource = JsAstUtils.assignment(jsExpressionCreateCoroutineResult, new JsNullLiteral()).source((Object) callElement);
                JsBlock jsBlock2 = new JsBlock();
                List<JsStatement> statements = jsBlock2.getStatements();
                Intrinsics.checkNotNullExpressionValue(statements, "getStatements(...)");
                statements.add(result);
                TranslationContext translationContextInnerBlock = getContext().innerBlock(jsBlock2);
                Intrinsics.checkNotNullExpressionValue(translationContextInnerBlock, "innerBlock(...)");
                JsExpression jsExpressionDeepCopy = jsExpressionCreateCoroutineResult.deepCopy();
                Intrinsics.checkNotNullExpressionValue(jsExpressionDeepCopy, "deepCopy(...)");
                JsExpression jsExpressionDeepCopy2 = jsExpressionCreateCoroutineResult.deepCopy();
                ResolvedCall<? extends CallableDescriptor> resolvedCall2 = this.$resolvedCall;
                Intrinsics.checkNotNull(jsExpressionDeepCopy2);
                MetadataProperties.setType(jsExpressionDeepCopy2, CallTranslatorKt.getReturnType(resolvedCall2));
                Intrinsics.checkNotNullExpressionValue(jsExpressionDeepCopy2, "apply(...)");
                JsExpression jsExpressionCoerce = TranslationUtils.coerce(translationContextInnerBlock, jsExpressionDeepCopy2, TypeUtilsKt.makeNullable(CallTranslatorKt.getReturnType(this.$resolvedCall)));
                Intrinsics.checkNotNullExpressionValue(jsExpressionCoerce, "coerce(...)");
                if (!Intrinsics.areEqual(jsExpressionCoerce, jsExpressionDeepCopy2)) {
                    List<JsStatement> statements2 = jsBlock2.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements2, "getStatements(...)");
                    statements2.add(JsAstUtils.asSyntheticStatement(JsAstUtils.assignment(jsExpressionDeepCopy, jsExpressionCoerce).source((Object) callElement)));
                }
                if (jsBlock2.getStatements().size() == 1) {
                    List<JsStatement> statements3 = jsBlock2.getStatements();
                    Intrinsics.checkNotNullExpressionValue(statements3, "getStatements(...)");
                    jsBlock = (JsStatement) CollectionsKt.first((List) statements3);
                } else {
                    jsBlock = jsBlock2;
                }
                return new JsIf(this.notNullConditionalForSafeCall.getTestExpression(), jsBlock, jsExpressionSource.makeStmt());
            }
        };
    }

    private static final JsExpression createCallInfo$getDispatchReceiver(ResolvedCall<? extends CallableDescriptor> resolvedCall, ExplicitReceiverKind explicitReceiverKind, ExplicitReceivers explicitReceivers, TranslationContext translationContext) {
        ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
        if (dispatchReceiver == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[explicitReceiverKind.ordinal()];
        if (i == 1 || i == 2) {
            return explicitReceivers.getExtensionOrDispatchReceiver();
        }
        return getDispatchReceiver(translationContext, dispatchReceiver);
    }

    private static final JsExpression createCallInfo$getExtensionReceiver(ResolvedCall<? extends CallableDescriptor> resolvedCall, ExplicitReceiverKind explicitReceiverKind, ExplicitReceivers explicitReceivers, TranslationContext translationContext) {
        ReceiverValue extensionReceiver = resolvedCall.getExtensionReceiver();
        if (extensionReceiver == null) {
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[explicitReceiverKind.ordinal()];
        if (i == 2) {
            return explicitReceivers.getExtensionReceiver();
        }
        if (i == 3) {
            return explicitReceivers.getExtensionOrDispatchReceiver();
        }
        return getDispatchReceiver(translationContext, extensionReceiver);
    }
}
