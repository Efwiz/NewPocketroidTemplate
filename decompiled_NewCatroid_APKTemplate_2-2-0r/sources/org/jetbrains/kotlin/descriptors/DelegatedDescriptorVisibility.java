package org.jetbrains.kotlin.descriptors;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;

/* JADX INFO: compiled from: DescriptorVisibility.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0001H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/DelegatedDescriptorVisibility;", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/descriptors/Visibility;", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)V", "getDelegate", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "externalDisplayName", "", "getExternalDisplayName", "()Ljava/lang/String;", "internalDisplayName", "getInternalDisplayName", "mustCheckInImports", "", "normalize", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DelegatedDescriptorVisibility extends DescriptorVisibility {
    private final Visibility delegate;

    public DelegatedDescriptorVisibility(Visibility delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
    public Visibility getDelegate() {
        return this.delegate;
    }

    @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
    /* JADX INFO: renamed from: mustCheckInImports */
    public boolean mo7471mustCheckInImports() {
        return getDelegate().mustCheckInImports();
    }

    @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
    public String getInternalDisplayName() {
        return getDelegate().getName();
    }

    @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
    public String getExternalDisplayName() {
        return getDelegate().getExternalDisplayName();
    }

    @Override // org.jetbrains.kotlin.descriptors.DescriptorVisibility
    public DescriptorVisibility normalize() {
        DescriptorVisibility descriptorVisibility = DescriptorVisibilities.toDescriptorVisibility(getDelegate().normalize());
        Intrinsics.checkNotNullExpressionValue(descriptorVisibility, "toDescriptorVisibility(...)");
        return descriptorVisibility;
    }
}
