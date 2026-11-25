package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: RttiExpressionChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\r¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/RttiExpressionInformation;", "", "subject", "Lorg/jetbrains/kotlin/psi/KtElement;", "sourceType", "Lorg/jetbrains/kotlin/types/KotlinType;", "targetType", "operation", "Lorg/jetbrains/kotlin/resolve/calls/checkers/RttiOperation;", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/resolve/calls/checkers/RttiOperation;)V", "getOperation", "()Lorg/jetbrains/kotlin/resolve/calls/checkers/RttiOperation;", "getSourceType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getSubject", "()Lorg/jetbrains/kotlin/psi/KtElement;", "getTargetType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RttiExpressionInformation {
    private final RttiOperation operation;
    private final KotlinType sourceType;
    private final KtElement subject;
    private final KotlinType targetType;

    public RttiExpressionInformation(KtElement subject, KotlinType kotlinType, KotlinType kotlinType2, RttiOperation operation) {
        Intrinsics.checkNotNullParameter(subject, "subject");
        Intrinsics.checkNotNullParameter(operation, "operation");
        this.subject = subject;
        this.sourceType = kotlinType;
        this.targetType = kotlinType2;
        this.operation = operation;
    }

    public final KtElement getSubject() {
        return this.subject;
    }

    public final KotlinType getSourceType() {
        return this.sourceType;
    }

    public final KotlinType getTargetType() {
        return this.targetType;
    }

    public final RttiOperation getOperation() {
        return this.operation;
    }
}
