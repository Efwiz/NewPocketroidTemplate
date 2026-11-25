package org.jetbrains.kotlin.js.translate.callTranslator;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.translate.callTranslator.CallInfo;

/* JADX INFO: compiled from: CallTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000*\n\b\u0000\u0010\u0001 \u0000*\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000¢\u0006\u0002\u0010\bJ\u0011\u0010\t\u001a\u00020\u0006*\u00028\u0000H\u0014¢\u0006\u0002\u0010\bJ\u0011\u0010\n\u001a\u00020\u0006*\u00028\u0000H\u0014¢\u0006\u0002\u0010\bJ\u0011\u0010\u000b\u001a\u00020\u0006*\u00028\u0000H\u0014¢\u0006\u0002\u0010\bJ\u0011\u0010\f\u001a\u00020\u0006*\u00028\u0000H\u0014¢\u0006\u0002\u0010\bJ\u001b\u0010\r\u001a\u00020\u000e*\u00028\u00002\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0014¢\u0006\u0002\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/callTranslator/CallCase;", "I", "Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;", "", "()V", "translate", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callInfo", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "bothReceivers", "dispatchReceiver", "extensionReceiver", "noReceivers", "unsupported", "", CrashHianalyticsData.MESSAGE, "", "(Lorg/jetbrains/kotlin/js/translate/callTranslator/CallInfo;Ljava/lang/String;)Ljava/lang/Void;", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CallCase<I extends CallInfo> {
    public static /* synthetic */ Void unsupported$default(CallCase callCase, CallInfo callInfo, String str, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: unsupported");
        }
        if ((i & 1) != 0) {
            str = "";
        }
        return callCase.unsupported(callInfo, str);
    }

    protected Void unsupported(I i, String message) {
        Intrinsics.checkNotNullParameter(i, "<this>");
        Intrinsics.checkNotNullParameter(message, "message");
        throw new IllegalStateException("this case unsupported. " + i);
    }

    protected JsExpression noReceivers(I i) {
        Intrinsics.checkNotNullParameter(i, "<this>");
        unsupported$default(this, i, null, 1, null);
        throw null;
    }

    protected JsExpression dispatchReceiver(I i) {
        Intrinsics.checkNotNullParameter(i, "<this>");
        unsupported$default(this, i, null, 1, null);
        throw null;
    }

    protected JsExpression extensionReceiver(I i) {
        Intrinsics.checkNotNullParameter(i, "<this>");
        unsupported$default(this, i, null, 1, null);
        throw null;
    }

    protected JsExpression bothReceivers(I i) {
        Intrinsics.checkNotNullParameter(i, "<this>");
        unsupported$default(this, i, null, 1, null);
        throw null;
    }

    public final JsExpression translate(I callInfo) {
        Intrinsics.checkNotNullParameter(callInfo, "callInfo");
        if (callInfo.getDispatchReceiver() == null) {
            if (callInfo.getExtensionReceiver() == null) {
                return noReceivers(callInfo);
            }
            return extensionReceiver(callInfo);
        }
        if (callInfo.getExtensionReceiver() == null) {
            return dispatchReceiver(callInfo);
        }
        return bothReceivers(callInfo);
    }
}
