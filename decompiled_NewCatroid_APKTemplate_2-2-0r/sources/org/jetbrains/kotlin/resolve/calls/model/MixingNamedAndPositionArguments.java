package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/MixingNamedAndPositionArguments;", "Lorg/jetbrains/kotlin/resolve/calls/model/InapplicableArgumentDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "(Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCallArgument;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MixingNamedAndPositionArguments extends InapplicableArgumentDiagnostic {
    private final KotlinCallArgument argument;

    public MixingNamedAndPositionArguments(KotlinCallArgument argument) {
        Intrinsics.checkNotNullParameter(argument, "argument");
        this.argument = argument;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.model.InapplicableArgumentDiagnostic
    public KotlinCallArgument getArgument() {
        return this.argument;
    }
}
