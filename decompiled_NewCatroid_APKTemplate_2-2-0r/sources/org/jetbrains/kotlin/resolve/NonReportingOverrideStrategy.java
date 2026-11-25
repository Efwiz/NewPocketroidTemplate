package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;

/* JADX INFO: compiled from: OverridingStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H$J\u0018\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006H\u0016J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/NonReportingOverrideStrategy;", "Lorg/jetbrains/kotlin/resolve/OverridingStrategy;", "()V", "conflict", "", "fromSuper", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "fromCurrent", "inheritanceConflict", "first", "second", "overrideConflict", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NonReportingOverrideStrategy extends OverridingStrategy {
    protected abstract void conflict(CallableMemberDescriptor fromSuper, CallableMemberDescriptor fromCurrent);

    @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
    public void overrideConflict(CallableMemberDescriptor fromSuper, CallableMemberDescriptor fromCurrent) {
        Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
        Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
        conflict(fromSuper, fromCurrent);
    }

    @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
    public void inheritanceConflict(CallableMemberDescriptor first, CallableMemberDescriptor second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        conflict(first, second);
    }
}
