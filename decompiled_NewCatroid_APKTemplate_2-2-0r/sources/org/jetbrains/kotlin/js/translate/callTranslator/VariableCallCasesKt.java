package org.jetbrains.kotlin.js.translate.callTranslator;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;

/* JADX INFO: compiled from: VariableCallCases.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\n\u0010\u0006\u001a\u00020\u0002*\u00020\u0003\"\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0007"}, d2 = {"additionalArguments", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;", "getAdditionalArguments", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/VariableAccessInfo;)Ljava/util/List;", "translateVariableAccess", "js.translator"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VariableCallCasesKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final List<JsExpression> getAdditionalArguments(VariableAccessInfo variableAccessInfo) {
        JsExpression value = variableAccessInfo.getValue();
        List<JsExpression> listListOf = value != null ? CollectionsKt.listOf(value) : null;
        return listListOf == null ? CollectionsKt.emptyList() : listListOf;
    }

    public static final JsExpression translateVariableAccess(VariableAccessInfo variableAccessInfo) {
        Intrinsics.checkNotNullParameter(variableAccessInfo, "<this>");
        VariableAccessInfo variableAccessInfo2 = variableAccessInfo;
        JsExpression jsExpressionIntrinsic = DelegatePropertyAccessIntrinsic.INSTANCE.intrinsic(variableAccessInfo2, variableAccessInfo.getContext());
        if (jsExpressionIntrinsic != null) {
            return jsExpressionIntrinsic;
        }
        if (CallInfoExtensionsKt.isSuperInvocation(variableAccessInfo2)) {
            return SuperPropertyAccessCase.INSTANCE.translate(variableAccessInfo2);
        }
        if (CallInfoExtensionsKt.isNative(variableAccessInfo2)) {
            return NativeVariableAccessCase.INSTANCE.translate(variableAccessInfo2);
        }
        return DefaultVariableAccessCase.INSTANCE.translate(variableAccessInfo2);
    }
}
