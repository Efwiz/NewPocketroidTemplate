package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;

/* JADX INFO: compiled from: OverridingStrategy.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u001e\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H&J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006H&J\u001e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverridingStrategy;", "", "()V", "addFakeOverride", "", "fakeOverride", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "inheritanceConflict", "first", "second", "overrideConflict", "fromSuper", "fromCurrent", "setOverriddenDescriptors", "member", "overridden", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class OverridingStrategy {
    public abstract void addFakeOverride(CallableMemberDescriptor fakeOverride);

    public abstract void inheritanceConflict(CallableMemberDescriptor first, CallableMemberDescriptor second);

    public abstract void overrideConflict(CallableMemberDescriptor fromSuper, CallableMemberDescriptor fromCurrent);

    public void setOverriddenDescriptors(CallableMemberDescriptor member, Collection<? extends CallableMemberDescriptor> overridden) {
        Intrinsics.checkNotNullParameter(member, "member");
        Intrinsics.checkNotNullParameter(overridden, "overridden");
        member.mo7205setOverriddenDescriptors(overridden);
    }
}
