package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.translate.reference.ReferenceTranslator;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.js.translate.utils.TranslationUtils;

/* JADX INFO: compiled from: VariableCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0014J\f\u0010\u0006\u001a\u00020\u0004*\u00020\u0005H\u0014J\f\u0010\u0007\u001a\u00020\u0004*\u00020\u0005H\u0014¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/NativeVariableAccessCase;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessCase;", "()V", "dispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;", "extensionReceiver", "noReceivers", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeVariableAccessCase extends VariableAccessCase {
    public static final NativeVariableAccessCase INSTANCE = new NativeVariableAccessCase();

    private NativeVariableAccessCase() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression extensionReceiver(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        JsName variableName = CallInfoExtensionsKt.getVariableName(variableAccessInfo);
        JsExpression extensionReceiver = variableAccessInfo.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        return CallInfoExtensionsKt.constructAccessExpression(variableAccessInfo, new JsNameRef(variableName, extensionReceiver));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression dispatchReceiver(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        CallableDescriptor resultingDescriptor = variableAccessInfo.getResolvedCall().getResultingDescriptor();
        if ((resultingDescriptor instanceof PropertyDescriptor) && TranslationUtils.shouldAccessViaFunctions(resultingDescriptor)) {
            JsName nameForDescriptor = variableAccessInfo.getContext().getNameForDescriptor(CallInfoExtensionsKt.getAccessDescriptorIfNeeded(variableAccessInfo));
            Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
            JsExpression dispatchReceiver = variableAccessInfo.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            JsNameRef jsNameRefPureFqn = JsAstUtils.pureFqn(nameForDescriptor, dispatchReceiver);
            JsExpression[] jsExpressionArr = (JsExpression[]) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo).toArray(new JsExpression[0]);
            return new JsInvocation(jsNameRefPureFqn, (JsExpression[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
        }
        JsName variableName = CallInfoExtensionsKt.getVariableName(variableAccessInfo);
        JsExpression dispatchReceiver2 = variableAccessInfo.getDispatchReceiver();
        Intrinsics.checkNotNull(dispatchReceiver2);
        return CallInfoExtensionsKt.constructAccessExpression(variableAccessInfo, new JsNameRef(variableName, dispatchReceiver2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.js.translate.callTranslator.CallCase
    public JsExpression noReceivers(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        CallableDescriptor resultingDescriptor = variableAccessInfo.getResolvedCall().getResultingDescriptor();
        if ((resultingDescriptor instanceof PropertyDescriptor) && TranslationUtils.shouldAccessViaFunctions(resultingDescriptor)) {
            JsExpression jsExpressionTranslateAsValueReference = ReferenceTranslator.translateAsValueReference(CallInfoExtensionsKt.getAccessDescriptorIfNeeded(variableAccessInfo), variableAccessInfo.getContext());
            Intrinsics.checkNotNullExpressionValue(jsExpressionTranslateAsValueReference, "translateAsValueReference(...)");
            JsExpression[] jsExpressionArr = (JsExpression[]) VariableCallCasesKt.getAdditionalArguments(variableAccessInfo).toArray(new JsExpression[0]);
            return new JsInvocation(jsExpressionTranslateAsValueReference, (JsExpression[]) Arrays.copyOf(jsExpressionArr, jsExpressionArr.length));
        }
        JsExpression qualifiedReference = variableAccessInfo.getContext().getQualifiedReference(CallInfoExtensionsKt.getCallableDescriptor(variableAccessInfo));
        Intrinsics.checkNotNullExpressionValue(qualifiedReference, "getQualifiedReference(...)");
        return CallInfoExtensionsKt.constructAccessExpression(variableAccessInfo, qualifiedReference);
    }
}
