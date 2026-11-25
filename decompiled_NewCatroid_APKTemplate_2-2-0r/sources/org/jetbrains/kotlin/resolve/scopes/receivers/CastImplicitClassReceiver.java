package org.jetbrains.kotlin.resolve.scopes.receivers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CastImplicitClassReceiver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/receivers/CastImplicitClassReceiver;", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ImplicitClassReceiver;", "originalDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "targetType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getTargetType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CastImplicitClassReceiver extends ImplicitClassReceiver {
    private final KotlinType targetType;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CastImplicitClassReceiver(ClassDescriptor originalDescriptor, KotlinType targetType) {
        super(originalDescriptor, null, 2, null);
        Intrinsics.checkNotNullParameter(originalDescriptor, "originalDescriptor");
        Intrinsics.checkNotNullParameter(targetType, "targetType");
        this.targetType = targetType;
    }

    public final KotlinType getTargetType() {
        return this.targetType;
    }
}
