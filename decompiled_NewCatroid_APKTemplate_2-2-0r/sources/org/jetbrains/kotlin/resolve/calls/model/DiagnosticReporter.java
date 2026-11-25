package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;

/* JADX INFO: compiled from: Diagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\r\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\u000e\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0018\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/DiagnosticReporter;", "", "constraintError", "", "error", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "onCall", "diagnostic", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallDiagnostic;", "onCallArgument", "callArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "onCallArgumentName", "onCallArgumentSpread", "onCallName", "onCallReceiver", "callReceiver", "Lorg/jetbrains/kotlin/resolve/calls/model/SimpleKotlinCallArgument;", "onExplicitReceiver", "onTypeArgument", "typeArgument", "Lorg/jetbrains/kotlin/resolve/calls/model/TypeArgument;", "onTypeArguments", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DiagnosticReporter {
    void constraintError(ConstraintSystemError error);

    void onCall(KotlinCallDiagnostic diagnostic);

    void onCallArgument(KotlinCallArgument callArgument, KotlinCallDiagnostic diagnostic);

    void onCallArgumentName(KotlinCallArgument callArgument, KotlinCallDiagnostic diagnostic);

    void onCallArgumentSpread(KotlinCallArgument callArgument, KotlinCallDiagnostic diagnostic);

    void onCallName(KotlinCallDiagnostic diagnostic);

    void onCallReceiver(SimpleKotlinCallArgument callReceiver, KotlinCallDiagnostic diagnostic);

    void onExplicitReceiver(KotlinCallDiagnostic diagnostic);

    void onTypeArgument(TypeArgument typeArgument, KotlinCallDiagnostic diagnostic);

    void onTypeArguments(KotlinCallDiagnostic diagnostic);
}
