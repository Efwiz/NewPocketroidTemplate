package org.jetbrains.kotlin.resolve.scopes;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ResolutionScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u001a\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J \u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u000b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016J.\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0014\b\u0002\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00030\u0012H&J#\u0010\u0013\u001a\r\u0012\t\u0012\u00070\u0014¢\u0006\u0002\b\u00150\r2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J#\u0010\u0016\u001a\r\u0012\t\u0012\u00070\u0017¢\u0006\u0002\b\u00150\r2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/ResolutionScope;", "", "definitelyDoesNotContainName", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getContributedClassifierIncludeDeprecated", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", "getContributedDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "nameFilter", "Lkotlin/Function1;", "getContributedFunctions", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lkotlin/jvm/JvmWildcard;", "getContributedVariables", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "recordLookup", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ResolutionScope {
    boolean definitelyDoesNotContainName(Name name2);

    /* JADX INFO: renamed from: getContributedClassifier */
    ClassifierDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location);

    DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(Name name2, LookupLocation location);

    Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, Function1<? super Name, Boolean> nameFilter);

    Collection<? extends FunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location);

    Collection<? extends VariableDescriptor> getContributedVariables(Name name2, LookupLocation location);

    /* JADX INFO: renamed from: recordLookup */
    void mo7479recordLookup(Name name2, LookupLocation location);

    /* JADX INFO: compiled from: ResolutionScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean definitelyDoesNotContainName(ResolutionScope resolutionScope, Name name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return false;
        }

        public static DescriptorWithDeprecation<ClassifierDescriptor> getContributedClassifierIncludeDeprecated(ResolutionScope resolutionScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            ClassifierDescriptor classifierDescriptorMo7465getContributedClassifier = resolutionScope.mo7465getContributedClassifier(name2, location);
            if (classifierDescriptorMo7465getContributedClassifier != null) {
                return DescriptorWithDeprecation.INSTANCE.createNonDeprecated(classifierDescriptorMo7465getContributedClassifier);
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Collection getContributedDescriptors$default(ResolutionScope resolutionScope, DescriptorKindFilter descriptorKindFilter, Function1 function1, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getContributedDescriptors");
            }
            if ((i & 1) != 0) {
                descriptorKindFilter = DescriptorKindFilter.ALL;
            }
            if ((i & 2) != 0) {
                function1 = MemberScope.INSTANCE.getALL_NAME_FILTER();
            }
            return resolutionScope.getContributedDescriptors(descriptorKindFilter, function1);
        }

        public static void recordLookup(ResolutionScope resolutionScope, Name name2, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            resolutionScope.getContributedFunctions(name2, location);
        }
    }
}
