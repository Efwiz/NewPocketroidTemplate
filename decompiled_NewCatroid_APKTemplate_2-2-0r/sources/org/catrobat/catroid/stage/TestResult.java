package org.catrobat.catroid.stage;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/TestResult.class
  classes14.dex
 */
/* JADX INFO: compiled from: TestResult.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/stage/TestResult;", "", CrashHianalyticsData.MESSAGE, "", "resultCode", "", "(Ljava/lang/String;I)V", "getMessage", "()Ljava/lang/String;", "setMessage", "(Ljava/lang/String;)V", "getResultCode", "()I", "setResultCode", "(I)V", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class TestResult {
    public static final int STAGE_ACTIVITY_TEST_FAIL = 8888;
    public static final int STAGE_ACTIVITY_TEST_SUCCESS = 7777;
    public static final String TEST_RESULT_MESSAGE = "ASSERTION_MESSAGE";
    private String message;
    private int resultCode;

    public static /* synthetic */ TestResult copy$default(TestResult testResult, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = testResult.message;
        }
        if ((i2 & 2) != 0) {
            i = testResult.resultCode;
        }
        return testResult.copy(str, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getResultCode() {
        return this.resultCode;
    }

    public final TestResult copy(String message, int resultCode) {
        Intrinsics.checkNotNullParameter(message, "message");
        return new TestResult(message, resultCode);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TestResult)) {
            return false;
        }
        TestResult testResult = (TestResult) other;
        return Intrinsics.areEqual(this.message, testResult.message) && this.resultCode == testResult.resultCode;
    }

    public int hashCode() {
        return (this.message.hashCode() * 31) + Integer.hashCode(this.resultCode);
    }

    public String toString() {
        return "TestResult(message=" + this.message + ", resultCode=" + this.resultCode + Constant.AFTER_QUTO;
    }

    public TestResult(String message, int i) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.message = message;
        this.resultCode = i;
    }

    public final String getMessage() {
        return this.message;
    }

    public final int getResultCode() {
        return this.resultCode;
    }

    public final void setMessage(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.message = str;
    }

    public final void setResultCode(int i) {
        this.resultCode = i;
    }
}
