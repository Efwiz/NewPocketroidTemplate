package org.jetbrains.kotlin.resolve.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: MemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001:\u0004\u000e\u000f\u0010\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u0010\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\fH\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude;", "", "()V", "fullyExcludedDescriptorKinds", "", "getFullyExcludedDescriptorKinds", "()I", "excludes", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "toString", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "EnumEntry", "Extensions", "NonExtensions", "TopLevelPackages", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DescriptorKindExclude {
    public abstract boolean excludes(DeclarationDescriptor descriptor);

    public abstract int getFullyExcludedDescriptorKinds();

    public String toString() {
        return getClass().getSimpleName();
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude$Extensions;", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude;", "()V", "fullyExcludedDescriptorKinds", "", "getFullyExcludedDescriptorKinds", "()I", "excludes", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Extensions extends DescriptorKindExclude {
        public static final Extensions INSTANCE = new Extensions();

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public int getFullyExcludedDescriptorKinds() {
            return 0;
        }

        private Extensions() {
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public boolean excludes(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return (descriptor instanceof CallableDescriptor) && ((CallableDescriptor) descriptor).getExtensionReceiverParameter() != null;
        }
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude$NonExtensions;", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude;", "()V", "fullyExcludedDescriptorKinds", "", "getFullyExcludedDescriptorKinds", "()I", "excludes", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NonExtensions extends DescriptorKindExclude {
        public static final NonExtensions INSTANCE = new NonExtensions();
        private static final int fullyExcludedDescriptorKinds = DescriptorKindFilter.INSTANCE.getALL_KINDS_MASK() & (~(DescriptorKindFilter.INSTANCE.getFUNCTIONS_MASK() | DescriptorKindFilter.INSTANCE.getVARIABLES_MASK()));

        private NonExtensions() {
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public boolean excludes(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return !(descriptor instanceof CallableDescriptor) || ((CallableDescriptor) descriptor).getExtensionReceiverParameter() == null;
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public int getFullyExcludedDescriptorKinds() {
            return fullyExcludedDescriptorKinds;
        }
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude$EnumEntry;", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude;", "()V", "fullyExcludedDescriptorKinds", "", "getFullyExcludedDescriptorKinds", "()I", "excludes", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnumEntry extends DescriptorKindExclude {
        public static final EnumEntry INSTANCE = new EnumEntry();

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public int getFullyExcludedDescriptorKinds() {
            return 0;
        }

        private EnumEntry() {
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public boolean excludes(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return (descriptor instanceof ClassDescriptor) && ((ClassDescriptor) descriptor).getKind() == ClassKind.ENUM_ENTRY;
        }
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude$TopLevelPackages;", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude;", "()V", "fullyExcludedDescriptorKinds", "", "getFullyExcludedDescriptorKinds", "()I", "excludes", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TopLevelPackages extends DescriptorKindExclude {
        public static final TopLevelPackages INSTANCE = new TopLevelPackages();

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public int getFullyExcludedDescriptorKinds() {
            return 0;
        }

        private TopLevelPackages() {
        }

        @Override // org.jetbrains.kotlin.resolve.scopes.DescriptorKindExclude
        public boolean excludes(DeclarationDescriptor descriptor) {
            FqName fqName;
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            if (descriptor instanceof PackageFragmentDescriptor) {
                fqName = ((PackageFragmentDescriptor) descriptor).getFqName();
            } else {
                if (!(descriptor instanceof PackageViewDescriptor)) {
                    return false;
                }
                fqName = ((PackageViewDescriptor) descriptor).getFqName();
            }
            return fqName.parent().isRoot();
        }
    }
}
