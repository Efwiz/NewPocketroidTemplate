package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsStringLiteral;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;

/* JADX INFO: compiled from: VariableCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0014¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/SuperPropertyAccessCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessCase;", "()V", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SuperPropertyAccessCase extends VariableAccessCase {
    public static final SuperPropertyAccessCase INSTANCE = new SuperPropertyAccessCase();

    private SuperPropertyAccessCase() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        JsStringLiteral jsStringLiteral = new JsStringLiteral(CallInfoExtensionsKt.getVariableName(variableAccessInfo).getIdent());
        CallableDescriptor resultingDescriptor = variableAccessInfo.getResolvedCall().getResultingDescriptor();
        if ((resultingDescriptor instanceof PropertyDescriptor) && TranslationUtils.shouldAccessViaFunctions(resultingDescriptor)) {
            CallableDescriptor accessDescriptorIfNeeded = CallInfoExtensionsKt.getAccessDescriptorIfNeeded(variableAccessInfo);
            JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(((PropertyDescriptor) resultingDescriptor).getContainingModule(), variableAccessInfo.getContext());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
            JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(Namer.getPrototypeName(), jsExpressionTranslateAsValueReference);
            Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
            JsNameRef functionCallRef = Namer.getFunctionCallRef(JsAstUtils.pureFqn(variableAccessInfo.getContext().getNameForDescriptor(accessDescriptorIfNeeded), jsNameRefPureFqn));
            Intrinsics.checkNotNullExpressionValue(functionCallRef, "getFunctionCallRef(...)");
            JsExpression dispatchReceiver = variableAccessInfo.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            JsExpression[] jsExpressionArr = (JsExpression[]) CollectionsKt.plus((Collection) CollectionsKt.listOf(dispatchReceiver), (Iterable) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo)).toArray(new JsExpression[0]);
            return new JsInvocation(functionCallRef, (JsExpression[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
        }
        JsExpression callGetProperty = CallInfoExtensionsKt.isGetAccess(variableAccessInfo) ? variableAccessInfo.getContext().namer().getCallGetProperty() : variableAccessInfo.getContext().namer().getCallSetProperty();
        Intrinsics.checkNotNull(callGetProperty);
        JsExpression dispatchReceiver2 = variableAccessInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver2);
        JsExpression jsExpressionPrototypeOf = JsAstUtils.prototypeOf(CallInfoExtensionsKt.getCalleeOwner(variableAccessInfo));
        Intrinsics.checkNotNullExpressionValue(jsExpressionPrototypeOf, "prototypeOf(...)");
        JsExpression[] jsExpressionArr2 = (JsExpression[]) CollectionsKt.plus((Collection) CollectionsKt.listOf((Object[]) new JsExpression[]{dispatchReceiver2, jsExpressionPrototypeOf, jsStringLiteral}), (Iterable) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo)).toArray(new JsExpression[0]);
        return new JsInvocation(callGetProperty, (JsExpression[]) Arrays.copyOf(jsExpressionArr2, jsExpressionArr2.length));
    }
}
