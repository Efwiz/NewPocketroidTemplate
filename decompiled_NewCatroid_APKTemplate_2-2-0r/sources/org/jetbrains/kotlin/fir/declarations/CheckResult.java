package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: OperatorFunctionChecks.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0006\u0007\bB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u0082\u0001\u0003\t\n\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/CheckResult;", "", "isSuccess", "", "(Z)V", "()Z", "IllegalFunctionName", "IllegalSignature", "SuccessCheck", "Lorg/jetbrains/kotlin/fir/declarations/CheckResult$IllegalFunctionName;", "Lorg/jetbrains/kotlin/fir/declarations/CheckResult$IllegalSignature;", "Lorg/jetbrains/kotlin/fir/declarations/CheckResult$SuccessCheck;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CheckResult {
    private final boolean isSuccess;

    public /* synthetic */ CheckResult(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    /* JADX INFO: compiled from: OperatorFunctionChecks.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/CheckResult$IllegalSignature;", "Lorg/jetbrains/kotlin/fir/declarations/CheckResult;", "error", "", "(Ljava/lang/String;)V", "getError", "()Ljava/lang/String;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalSignature extends CheckResult {
        private final String error;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IllegalSignature(String error) {
            super(false, null);
            Intrinsics.checkNotNullParameter(error, "error");
            this.error = error;
        }

        public final String getError() {
            return this.error;
        }
    }

    private CheckResult(boolean z) {
        this.isSuccess = z;
    }

    /* JADX INFO: renamed from: isSuccess, reason: from getter */
    public final boolean getIsSuccess() {
        return this.isSuccess;
    }

    /* JADX INFO: compiled from: OperatorFunctionChecks.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/CheckResult$IllegalFunctionName;", "Lorg/jetbrains/kotlin/fir/declarations/CheckResult;", "()V", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalFunctionName extends CheckResult {
        public static final IllegalFunctionName INSTANCE = new IllegalFunctionName();

        private IllegalFunctionName() {
            super(false, null);
        }
    }

    /* JADX INFO: compiled from: OperatorFunctionChecks.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/CheckResult$SuccessCheck;", "Lorg/jetbrains/kotlin/fir/declarations/CheckResult;", "()V", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SuccessCheck extends CheckResult {
        public static final SuccessCheck INSTANCE = new SuccessCheck();

        private SuccessCheck() {
            super(true, null);
        }
    }
}
