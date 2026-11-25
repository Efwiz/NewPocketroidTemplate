package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ModuleCapability;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;

/* JADX INFO: compiled from: ResolutionAnchorProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0006\"\u0017\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0007"}, d2 = {"RESOLUTION_ANCHOR_PROVIDER_CAPABILITY", "Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "Lorg/jetbrains/kotlin/resolve/ResolutionAnchorProvider;", "getRESOLUTION_ANCHOR_PROVIDER_CAPABILITY", "()Lorg/jetbrains/kotlin/descriptors/ModuleCapability;", "getResolutionAnchorIfAny", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ResolutionAnchorProviderKt {
    private static final ModuleCapability<ResolutionAnchorProvider> RESOLUTION_ANCHOR_PROVIDER_CAPABILITY = new ModuleCapability<>("ResolutionAnchorProvider");

    public static final ModuleCapability<ResolutionAnchorProvider> getRESOLUTION_ANCHOR_PROVIDER_CAPABILITY() {
        return RESOLUTION_ANCHOR_PROVIDER_CAPABILITY;
    }

    public static final ModuleDescriptor getResolutionAnchorIfAny(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        ResolutionAnchorProvider resolutionAnchorProvider = (ResolutionAnchorProvider) moduleDescriptor.getCapability(RESOLUTION_ANCHOR_PROVIDER_CAPABILITY);
        if (resolutionAnchorProvider != null) {
            return resolutionAnchorProvider.getResolutionAnchor(moduleDescriptor);
        }
        return null;
    }
}
