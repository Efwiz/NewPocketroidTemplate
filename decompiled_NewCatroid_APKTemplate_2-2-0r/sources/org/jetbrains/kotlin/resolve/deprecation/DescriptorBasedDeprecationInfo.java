package org.jetbrains.kotlin.resolve.deprecation;

import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;

/* JADX INFO: compiled from: DescriptorBasedDeprecationInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0012\u0010\t\u001a\u00020\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "()V", "forcePropagationToOverrides", "", "getForcePropagationToOverrides", "()Z", "propagatesToOverrides", "getPropagatesToOverrides", "target", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DescriptorBasedDeprecationInfo extends DeprecationInfo {
    public boolean getForcePropagationToOverrides() {
        return false;
    }

    public abstract DeclarationDescriptor getTarget();

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public boolean getPropagatesToOverrides() {
        return getForcePropagationToOverrides();
    }
}
