package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.impl.LocalVariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.metadata.MetadataProperties;
import org.jetbrains.kotlin.js.backend.ast.metadata.SideEffectKind;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.declaration.PropertyTranslatorKt;
import org.jetbrains.kotlin.js.translate.reference.CallArgumentTranslatorKt;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.JsDescriptorUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedValueArgument;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: VariableCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0014J\f\u0010\u0006\u001a\u00020\u0004*\u00020\u0005H\u0014J\f\u0010\u0007\u001a\u00020\u0004*\u00020\u0005H\u0014J\f\u0010\b\u001a\u00020\u0004*\u00020\u0005H\u0014¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/DefaultVariableAccessCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessCase;", "()V", "bothReceivers", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;", "dispatchReceiver", "extensionReceiver", "noReceivers", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultVariableAccessCase extends VariableAccessCase {
    public static final DefaultVariableAccessCase INSTANCE = new DefaultVariableAccessCase();

    private DefaultVariableAccessCase() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:36:0x010d  */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression noReceivers(VariableAccessInfo variableAccessInfo) {
        LocalVariableAccessorDescriptor setter;
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        VariableDescriptor variableDescriptor = CallInfoExtensionsKt.getVariableDescriptor(variableAccessInfo);
        if ((variableDescriptor instanceof PropertyDescriptor) && !JsDescriptorUtils.isSimpleFinalProperty((PropertyDescriptor) variableDescriptor) && variableAccessInfo.getContext().isFromCurrentModule(variableDescriptor)) {
            JsExpression innerReference = variableAccessInfo.getContext().getInnerReference(CallInfoExtensionsKt.getAccessDescriptor(variableAccessInfo));
            Intrinsics.checkNotNullExpressionValue(innerReference, "getInnerReference(...)");
            JsExpression[] jsExpressionArr = (JsExpression[]) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo).toArray(new JsExpression[0]);
            return new JsInvocation(innerReference, (JsExpression[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
        }
        CallableDescriptor resultingDescriptor = variableAccessInfo.getResolvedCall().getResultingDescriptor();
        if ((resultingDescriptor instanceof PropertyDescriptor) && TranslationUtils.shouldAccessViaFunctions(resultingDescriptor)) {
            JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getAccessDescriptorIfNeeded(variableAccessInfo), variableAccessInfo.getContext());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
            JsExpression[] jsExpressionArr2 = (JsExpression[]) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo).toArray(new JsExpression[0]);
            return new JsInvocation(jsExpressionTranslateAsValueReference, (JsExpression[]) Arrays.copyOf(jsExpressionArr2, jsExpressionArr2.length));
        }
        if (resultingDescriptor instanceof FakeCallableDescriptorForObject) {
            JsExpression jsExpressionTranslateAsValueReference2 = ReferenceTranslator.translateAsValueReference(((FakeCallableDescriptorForObject) resultingDescriptor).getReferencedObject(), variableAccessInfo.getContext());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference2, "translateAsValueReference(...)");
            return jsExpressionTranslateAsValueReference2;
        }
        VariableAccessInfo variableAccessInfo2 = variableAccessInfo;
        JsNameRef jsNameRefTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getCallableDescriptor(variableAccessInfo2), variableAccessInfo.getContext());
        Intrinsics.checkNotNullExpressionValue(jsNameRefTranslateAsValueReference, "translateAsValueReference(...)");
        if (variableAccessInfo.getContext().isBoxedLocalCapturedInClosure(CallInfoExtensionsKt.getCallableDescriptor(variableAccessInfo2))) {
            JsNameRef capturedVarAccessor = Namer.getCapturedVarAccessor(jsNameRefTranslateAsValueReference);
            Intrinsics.checkNotNull(capturedVarAccessor);
            jsNameRefTranslateAsValueReference = capturedVarAccessor;
        } else if (CallInfoExtensionsKt.isGetAccess(variableAccessInfo)) {
            MetadataProperties.setSideEffects(jsNameRefTranslateAsValueReference, SideEffectKind.DEPENDS_ON_STATE);
        }
        CallableDescriptor resultingDescriptor2 = variableAccessInfo.getResolvedCall().getResultingDescriptor();
        LocalVariableDescriptor localVariableDescriptor = resultingDescriptor2 instanceof LocalVariableDescriptor ? (LocalVariableDescriptor) resultingDescriptor2 : null;
        if (CallInfoExtensionsKt.isGetAccess(variableAccessInfo)) {
            if (localVariableDescriptor != null) {
                setter = localVariableDescriptor.getGetter();
            } else {
                setter = null;
            }
        } else if (localVariableDescriptor != null) {
            setter = localVariableDescriptor.getSetter();
        } else {
            setter = null;
        }
        LocalVariableAccessorDescriptor localVariableAccessorDescriptor = setter;
        ResolvedCall resolvedCall = localVariableAccessorDescriptor != null ? (ResolvedCall) variableAccessInfo.getContext().bindingContext().get(BindingContext.DELEGATED_PROPERTY_RESOLVED_CALL, localVariableAccessorDescriptor) : null;
        if (resolvedCall != null) {
            TranslationContext context = variableAccessInfo.getContext();
            Intrinsics.checkNotNull(localVariableDescriptor);
            TranslationContext translationContextContextWithPropertyMetadataCreationIntrinsified = PropertyTranslatorKt.contextWithPropertyMetadataCreationIntrinsified(context, resolvedCall, localVariableDescriptor, new JsNullLiteral());
            if (!CallInfoExtensionsKt.isGetAccess(variableAccessInfo)) {
                List<ResolvedValueArgument> valueArgumentsByIndex = resolvedCall.getValueArgumentsByIndex();
                Intrinsics.checkNotNull(valueArgumentsByIndex);
                KtExpression argumentExpression = valueArgumentsByIndex.get(2).getArguments().get(0).getArgumentExpression();
                JsExpression value = variableAccessInfo.getValue();
                Intrinsics.checkNotNull(value);
                translationContextContextWithPropertyMetadataCreationIntrinsified = translationContextContextWithPropertyMetadataCreationIntrinsified.innerContextWithAliasesForExpressions(MapsKt.mapOf(TuplesKt.to(argumentExpression, value)));
            }
            Intrinsics.checkNotNull(translationContextContextWithPropertyMetadataCreationIntrinsified);
            LocalVariableDescriptor localVariableDescriptor2 = localVariableDescriptor;
            JsNameRef aliasForDescriptor = variableAccessInfo.getContext().getAliasForDescriptor(localVariableDescriptor2);
            if (aliasForDescriptor == null) {
                aliasForDescriptor = JsAstUtils.pureFqn(variableAccessInfo.getContext().getNameForDescriptor(localVariableDescriptor2), (JsExpression) null);
            }
            Intrinsics.checkNotNull(aliasForDescriptor);
            return CallTranslator.translate(translationContextContextWithPropertyMetadataCreationIntrinsified, resolvedCall, aliasForDescriptor);
        }
        return CallInfoExtensionsKt.constructAccessExpression(variableAccessInfo, jsNameRefTranslateAsValueReference);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        CallableDescriptor resultingDescriptor = variableAccessInfo.getResolvedCall().getResultingDescriptor();
        boolean z = resultingDescriptor instanceof PropertyDescriptor;
        if (z && TranslationUtils.shouldAccessViaFunctions(resultingDescriptor)) {
            JsName nameForDescriptor = variableAccessInfo.getContext().getNameForDescriptor(CallInfoExtensionsKt.getAccessDescriptorIfNeeded(variableAccessInfo));
            JsExpression dispatchReceiver = variableAccessInfo.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(nameForDescriptor, dispatchReceiver);
            Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
            JsExpression[] jsExpressionArr = (JsExpression[]) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo).toArray(new JsExpression[0]);
            return new JsInvocation(jsNameRefPureFqn, (JsExpression[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
        }
        JsName variableName = CallInfoExtensionsKt.getVariableName(variableAccessInfo);
        JsExpression dispatchReceiver2 = variableAccessInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver2);
        JsNameRef jsNameRef = new JsNameRef(variableName, dispatchReceiver2);
        if (z && !JsDescriptorUtils.sideEffectsPossibleOnRead((PropertyDescriptor) resultingDescriptor)) {
            MetadataProperties.setSideEffects(jsNameRef, SideEffectKind.DEPENDS_ON_STATE);
        }
        return CallInfoExtensionsKt.constructAccessExpression(variableAccessInfo, jsNameRef);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression extensionReceiver(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getAccessDescriptorIfNeeded(variableAccessInfo), variableAccessInfo.getContext());
        Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
        Map<TypeParameterDescriptor, KotlinType> typeArguments = variableAccessInfo.getResolvedCall().getTypeArguments();
        Intrinsics.checkNotNullExpressionValue(typeArguments, "getTypeArguments(...)");
        List<JsExpression> listBuildReifiedTypeArgs = CallArgumentTranslatorKt.buildReifiedTypeArgs(typeArguments, variableAccessInfo.getContext());
        JsExpression extensionReceiver = variableAccessInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        return new JsInvocation(jsExpressionTranslateAsValueReference, (List<? extends JsExpression>) CollectionsKt.plus((Collection) CollectionsKt.plus((Collection) listBuildReifiedTypeArgs, (Iterable) CollectionsKt.listOf(extensionReceiver)), (Iterable) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression bothReceivers(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        JsName nameForDescriptor = variableAccessInfo.getContext().getNameForDescriptor(CallInfoExtensionsKt.getAccessDescriptorIfNeeded(variableAccessInfo));
        JsExpression dispatchReceiver = variableAccessInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver);
        JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(nameForDescriptor, dispatchReceiver);
        Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
        JsExpression extensionReceiver = variableAccessInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        spreadBuilder.add(extensionReceiver);
        spreadBuilder.addSpread(VariableCallCasesKt.getAdditionalArguments(variableAccessInfo).toArray(new JsExpression[0]));
        return new JsInvocation(jsNameRefPureFqn, (JsExpression[]) spreadBuilder.toArray(new JsExpression[spreadBuilder.size()]));
    }
}
