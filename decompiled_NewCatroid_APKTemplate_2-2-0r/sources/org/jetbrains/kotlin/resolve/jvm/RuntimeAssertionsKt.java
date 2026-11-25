package org.jetbrains.kotlin.resolve.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.psi.KtExpression;

/* JADX INFO: compiled from: RuntimeAssertions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u001d\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"textForRuntimeAssertionInfo", "", "Lorg/jetbrains/annotations/NotNull;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "getTextForRuntimeAssertionInfo", "(Lorg/jetbrains/kotlin/psi/KtExpression;)Ljava/lang/String;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class RuntimeAssertionsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final String getTextForRuntimeAssertionInfo(KtExpression ktExpression) {
        String strTrimMiddle = StringUtil.trimMiddle(ktExpression.getText(), 50);
        Intrinsics.checkNotNullExpressionValue(strTrimMiddle, "trimMiddle(...)");
        return strTrimMiddle;
    }
}
