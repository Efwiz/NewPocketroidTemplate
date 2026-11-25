package org.jetbrains.kotlin.resolve.calls.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.UnwrappedType;

/* JADX INFO: compiled from: KotlinCallDiagnostics.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/model/NotCallableExpectedType;", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceInapplicableDiagnostic;", "argument", "Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "expectedType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "notCallableTypeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "(Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/TypeConstructor;)V", "getArgument", "()Lorg/jetbrains/kotlin/resolve/calls/model/CallableReferenceKotlinCallArgument;", "getExpectedType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "getNotCallableTypeConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NotCallableExpectedType extends CallableReferenceInapplicableDiagnostic {
    private final CallableReferenceKotlinCallArgument argument;
    private final UnwrappedType expectedType;
    private final TypeConstructor notCallableTypeConstructor;

    public final CallableReferenceKotlinCallArgument getArgument() {
        return this.argument;
    }

    public final UnwrappedType getExpectedType() {
        return this.expectedType;
    }

    public final TypeConstructor getNotCallableTypeConstructor() {
        return this.notCallableTypeConstructor;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotCallableExpectedType(CallableReferenceKotlinCallArgument argument, UnwrappedType expectedType, TypeConstructor notCallableTypeConstructor) {
        super(argument, null, 2, null);
        Intrinsics.checkNotNullParameter(argument, "argument");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        Intrinsics.checkNotNullParameter(notCallableTypeConstructor, "notCallableTypeConstructor");
        this.argument = argument;
        this.expectedType = expectedType;
        this.notCallableTypeConstructor = notCallableTypeConstructor;
    }
}
