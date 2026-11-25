package org.jetbrains.kotlin.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;

/* JADX INFO: compiled from: ImplicitScopeTower.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/NestedClassViaInstanceReference;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ResolutionDiagnostic;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "getClassDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NestedClassViaInstanceReference extends ResolutionDiagnostic {
    private final ClassDescriptor classDescriptor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NestedClassViaInstanceReference(ClassDescriptor classDescriptor) {
        super(CandidateApplicability.K1_IMPOSSIBLE_TO_GENERATE);
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        this.classDescriptor = classDescriptor;
    }

    public final ClassDescriptor getClassDescriptor() {
        return this.classDescriptor;
    }
}
