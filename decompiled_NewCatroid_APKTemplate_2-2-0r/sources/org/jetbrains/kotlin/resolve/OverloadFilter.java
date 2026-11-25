package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorNonRoot;

/* JADX INFO: compiled from: OverloadFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001:\u0001\u0006J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverloadFilter;", "", "filterPackageMemberOverloads", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "overloads", "Default", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface OverloadFilter {
    Collection<DeclarationDescriptorNonRoot> filterPackageMemberOverloads(Collection<? extends DeclarationDescriptorNonRoot> overloads);

    /* JADX INFO: compiled from: OverloadFilter.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/OverloadFilter$Default;", "Lorg/jetbrains/kotlin/resolve/OverloadFilter;", "()V", "filterPackageMemberOverloads", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorNonRoot;", "overloads", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements OverloadFilter {
        public static final Default INSTANCE = new Default();

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.resolve.OverloadFilter
        public Collection<DeclarationDescriptorNonRoot> filterPackageMemberOverloads(Collection<? extends DeclarationDescriptorNonRoot> overloads) {
            Intrinsics.checkNotNullParameter(overloads, "overloads");
            return overloads;
        }

        private Default() {
        }
    }
}
