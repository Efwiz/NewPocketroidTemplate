package org.jetbrains.kotlin.resolve.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: Scopes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "parent", "getParent", "()Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", "printStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface HierarchicalScope extends ResolutionScope {
    HierarchicalScope getParent();

    void printStructure(Printer p);

    /* JADX INFO: compiled from: Scopes.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean definitelyDoesNotContainName(HierarchicalScope hierarchicalScope, Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return ResolutionScope.DefaultImpls.definitelyDoesNotContainName(hierarchicalScope, name2);
        }

        public static DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            return ResolutionScope.DefaultImpls.getContributedClassifierIncludeDeprecated(hierarchicalScope, name2, location);
        }

        public static void recordLookup(HierarchicalScope hierarchicalScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            ResolutionScope.DefaultImpls.recordLookup(hierarchicalScope, name2, location);
        }
    }
}
