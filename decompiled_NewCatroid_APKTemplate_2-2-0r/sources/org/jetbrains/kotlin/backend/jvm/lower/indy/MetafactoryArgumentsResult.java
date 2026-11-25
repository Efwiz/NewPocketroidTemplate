package org.jetbrains.kotlin.backend.jvm.lower.indy;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b0\u0018\u00002\u00020\u0001:\u0002\u0006\u0007B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005\u0082\u0001\u0002\b\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult;", "", "()V", "isSuccess", "", "()Z", "Failure", "Success", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Success;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class MetafactoryArgumentsResult {
    public /* synthetic */ MetafactoryArgumentsResult(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract boolean isSuccess();

    private MetafactoryArgumentsResult() {
    }

    /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Success;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult;", "()V", "isSuccess", "", "()Z", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Success extends MetafactoryArgumentsResult {
        @Override // org.jetbrains.kotlin.backend.jvm.lower.indy.MetafactoryArgumentsResult
        public boolean isSuccess() {
            return true;
        }

        public Success() {
            super(null);
        }
    }

    /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001:\u0004\u0006\u0007\b\tB\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0005¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult;", "()V", "isSuccess", "", "()Z", "FunctionHazard", "InliningHazard", "LambdaMetafactoryAbiHazard", "LambdaMetafactorySemanticsHazard", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Failure extends MetafactoryArgumentsResult {
        @Override // org.jetbrains.kotlin.backend.jvm.lower.indy.MetafactoryArgumentsResult
        public boolean isSuccess() {
            return false;
        }

        public Failure() {
            super(null);
        }

        /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure$LambdaMetafactorySemanticsHazard;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure;", "()V", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class LambdaMetafactorySemanticsHazard extends Failure {
            public static final LambdaMetafactorySemanticsHazard INSTANCE = new LambdaMetafactorySemanticsHazard();

            private LambdaMetafactorySemanticsHazard() {
            }
        }

        /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure$LambdaMetafactoryAbiHazard;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure;", "()V", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class LambdaMetafactoryAbiHazard extends Failure {
            public static final LambdaMetafactoryAbiHazard INSTANCE = new LambdaMetafactoryAbiHazard();

            private LambdaMetafactoryAbiHazard() {
            }
        }

        /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure$InliningHazard;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure;", "()V", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class InliningHazard extends Failure {
            public static final InliningHazard INSTANCE = new InliningHazard();

            private InliningHazard() {
            }
        }

        /* JADX INFO: compiled from: LambdaMetafactoryArguments.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure$FunctionHazard;", "Lorg/jetbrains/kotlin/backend/jvm/lower/indy/MetafactoryArgumentsResult$Failure;", "()V", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FunctionHazard extends Failure {
            public static final FunctionHazard INSTANCE = new FunctionHazard();

            private FunctionHazard() {
            }
        }
    }
}
