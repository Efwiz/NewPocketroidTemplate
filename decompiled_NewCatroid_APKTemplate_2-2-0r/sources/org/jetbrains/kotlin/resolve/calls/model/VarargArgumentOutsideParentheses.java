package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/VarargArgumentOutsideParentheses;", "Lorg/jetbrains/kotlin/resolve/calls/model/InapplicableArgumentDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "parameterDescriptor", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "getParameterDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VarargArgumentOutsideParentheses extends InapplicableArgumentDiagnostic {
    private final KotlinCallArgument argument;
    private final ValueParameterDescriptor parameterDescriptor;

    @Override // org.jetbrains.kotlin.resolve.calls.model.InapplicableArgumentDiagnostic
    public KotlinCallArgument getArgument() {
        return this.argument;
    }

    public final ValueParameterDescriptor getParameterDescriptor() {
        return this.parameterDescriptor;
    }

    public VarargArgumentOutsideParentheses(KotlinCallArgument argument, ValueParameterDescriptor parameterDescriptor) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(parameterDescriptor, "parameterDescriptor");
        this.argument = argument;
        this.parameterDescriptor = parameterDescriptor;
    }
}
