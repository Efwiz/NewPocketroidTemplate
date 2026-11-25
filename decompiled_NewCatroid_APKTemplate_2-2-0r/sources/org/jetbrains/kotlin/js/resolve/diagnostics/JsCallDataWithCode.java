package org.jetbrains.kotlin.js.resolve.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: JsCallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallDataWithCode;", "Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallData;", "reportRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", CrashHianalyticsData.MESSAGE, "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "codeRange", "(Lcom/intellij/openapi/util/TextRange;Ljava/lang/String;Ljava/lang/String;Lcom/intellij/openapi/util/TextRange;)V", "getCode", "()Ljava/lang/String;", "getCodeRange", "()Lcom/intellij/openapi/util/TextRange;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCallDataWithCode extends JsCallData {
    private final String code;
    private final TextRange codeRange;

    public final String getCode() {
        return this.code;
    }

    public final TextRange getCodeRange() {
        return this.codeRange;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsCallDataWithCode(TextRange reportRange, String message, String code, TextRange codeRange) {
        super(reportRange, message);
        Intrinsics.checkNotNullParameter(reportRange, "reportRange");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(code, "code");
        Intrinsics.checkNotNullParameter(codeRange, "codeRange");
        this.code = code;
        this.codeRange = codeRange;
    }
}
