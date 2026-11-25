package org.jetbrains.kotlin.js.translate.callTranslator;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtils;

/* JADX INFO: compiled from: CallInfo.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/AbstractCallInfo;", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "()V", "toString", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractCallInfo implements CallInfo {
    public String toString() {
        AbstractCallInfo abstractCallInfo = this;
        String strAtLocation = DiagnosticUtils.atLocation(CallInfoExtensionsKt.getCallableDescriptor(abstractCallInfo));
        String strAsString = CallInfoExtensionsKt.getCallableDescriptor(abstractCallInfo).getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return "callableDescriptor: " + strAsString + " at " + strAtLocation + "; dispatchReceiver: " + getDispatchReceiver() + "; extensionReceiver: " + getExtensionReceiver();
    }
}
