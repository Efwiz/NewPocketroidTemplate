package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;

/* JADX INFO: compiled from: CallInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/ExplicitReceivers;", "", "extensionOrDispatchReceiver", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "extensionReceiver", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "getExtensionOrDispatchReceiver", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "getExtensionReceiver", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExplicitReceivers {
    private final JsExpression extensionOrDispatchReceiver;
    private final JsExpression extensionReceiver;

    public ExplicitReceivers(JsExpression jsExpression, JsExpression jsExpression2) {
        this.extensionOrDispatchReceiver = jsExpression;
        this.extensionReceiver = jsExpression2;
    }

    public /* synthetic */ ExplicitReceivers(JsExpression jsExpression, JsExpression jsExpression2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsExpression, (i & 2) != 0 ? null : jsExpression2);
    }

    public final JsExpression getExtensionOrDispatchReceiver() {
        return this.extensionOrDispatchReceiver;
    }

    public final JsExpression getExtensionReceiver() {
        return this.extensionReceiver;
    }
}
