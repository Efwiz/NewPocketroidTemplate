package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.Collections;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;

/* JADX INFO: compiled from: VariableCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\f\u0010\u0004\u001a\u00020\u0005*\u00020\u0002H\u0016J\u0012\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007*\u00020\u0002H\u0016J\f\u0010\t\u001a\u00020\n*\u00020\u0002H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/DelegatePropertyAccessIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/DelegateIntrinsic;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;", "()V", "canBeApply", "", "getArgs", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getDescriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DelegatePropertyAccessIntrinsic implements DelegateIntrinsic<VariableAccessInfo> {
    public static final DelegatePropertyAccessIntrinsic INSTANCE = new DelegatePropertyAccessIntrinsic();

    private DelegatePropertyAccessIntrinsic() {
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.DelegateIntrinsic
    public boolean canBeApply(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        if (!(CallInfoExtensionsKt.getVariableDescriptor(variableAccessInfo) instanceof PropertyDescriptor)) {
            return false;
        }
        if (!CallInfoExtensionsKt.isGetAccess(variableAccessInfo)) {
            VariableDescriptor variableDescriptor = CallInfoExtensionsKt.getVariableDescriptor(variableAccessInfo);
            Intrinsics.checkNotNull(variableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
            if (!((PropertyDescriptor) variableDescriptor).isVar()) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.DelegateIntrinsic
    public List<JsExpression> getArgs(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        if (CallInfoExtensionsKt.isGetAccess(variableAccessInfo)) {
            List<JsExpression> listEmptyList = Collections.emptyList();
            Intrinsics.checkNotNullExpressionValue(listEmptyList, "emptyList(...)");
            return listEmptyList;
        }
        JsExpression value = variableAccessInfo.getValue();
        Intrinsics.checkNotNull(value);
        List<JsExpression> listSingletonList = Collections.singletonList(value);
        Intrinsics.checkNotNullExpressionValue(listSingletonList, "singletonList(...)");
        return listSingletonList;
    }

    @Override // org.jetbrains.kotlin.js.translate.callTranslator.DelegateIntrinsic
    public CallableDescriptor getDescriptor(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        VariableDescriptor variableDescriptor = CallInfoExtensionsKt.getVariableDescriptor(variableAccessInfo);
        Intrinsics.checkNotNull(variableDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) variableDescriptor;
        if (CallInfoExtensionsKt.isGetAccess(variableAccessInfo)) {
            PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
            Intrinsics.checkNotNull(getter);
            Intrinsics.checkNotNull(getter);
            return getter;
        }
        PropertySetterDescriptor setter = propertyDescriptor.getSetter();
        Intrinsics.checkNotNull(setter);
        Intrinsics.checkNotNull(setter);
        return setter;
    }
}
