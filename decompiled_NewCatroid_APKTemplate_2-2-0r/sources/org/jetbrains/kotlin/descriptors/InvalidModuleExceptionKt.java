package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: InvalidModuleException.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0005\u001a\u00020\u0006*\u00020\u0007\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\b"}, d2 = {"INVALID_MODULE_NOTIFIER_CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "Lorg/jetbrains/kotlin/descriptors/InvalidModuleNotifier;", "getINVALID_MODULE_NOTIFIER_CAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "moduleInvalidated", "", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InvalidModuleExceptionKt {
    private static final ModuleCapability<InvalidModuleNotifier> INVALID_MODULE_NOTIFIER_CAPABILITY = new ModuleCapability<>("InvalidModuleNotifier");

    public static final void moduleInvalidated(ModuleDescriptor moduleDescriptor) {
        Unit unit;
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        InvalidModuleNotifier invalidModuleNotifier = (InvalidModuleNotifier) moduleDescriptor.getCapability(INVALID_MODULE_NOTIFIER_CAPABILITY);
        if (invalidModuleNotifier != null) {
            invalidModuleNotifier.notifyModuleInvalidated(moduleDescriptor);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            throw new InvalidModuleException("Accessing invalid module descriptor " + moduleDescriptor);
        }
    }

    public static final ModuleCapability<InvalidModuleNotifier> getINVALID_MODULE_NOTIFIER_CAPABILITY() {
        return INVALID_MODULE_NOTIFIER_CAPABILITY;
    }
}
