package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassValueReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;

/* JADX INFO: compiled from: EnumEntryVsCompanionPriorityCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000¨\u0006\u0005"}, d2 = {"isQualifierFor", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EnumEntryVsCompanionPriorityCallCheckerKt {
    public static final boolean isQualifierFor(ReceiverValue receiverValue, ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (!(receiverValue instanceof ClassValueReceiver)) {
            return false;
        }
        ClassifierDescriptorWithTypeParameters descriptor = ((ClassValueReceiver) receiverValue).getClassQualifier().getDescriptor();
        ClassDescriptor classDescriptor2 = descriptor instanceof ClassDescriptor ? (ClassDescriptor) descriptor : null;
        if (classDescriptor2 == null) {
            return false;
        }
        return Intrinsics.areEqual(classDescriptor2.getTypeConstructor(), classDescriptor.getTypeConstructor());
    }
}
