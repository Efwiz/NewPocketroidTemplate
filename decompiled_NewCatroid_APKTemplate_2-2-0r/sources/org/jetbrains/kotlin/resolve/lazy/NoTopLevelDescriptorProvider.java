package org.jetbrains.kotlin.resolve.lazy;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyPackageDescriptor;

/* JADX INFO: compiled from: TopLevelDescriptorProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001a\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0002¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/NoTopLevelDescriptorProvider;", "Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;", "()V", "assertValid", "", "getPackageFragment", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyPackageDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFragmentOrDiagnoseFailure", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/psi/KtFile;", "getTopLevelClassifierDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "shouldNotBeCalled", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NoTopLevelDescriptorProvider implements TopLevelDescriptorProvider {
    public static final NoTopLevelDescriptorProvider INSTANCE = new NoTopLevelDescriptorProvider();

    private NoTopLevelDescriptorProvider() {
    }

    private final Void shouldNotBeCalled() {
        throw new UnsupportedOperationException("Should not be called");
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public LazyPackageDescriptor getPackageFragment(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public LazyPackageDescriptor getPackageFragmentOrDiagnoseFailure(FqName fqName, KtFile from) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public Collection<ClassifierDescriptor> getTopLevelClassifierDescriptors(FqName fqName, LookupLocation location) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(location, "location");
        shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider
    public void assertValid() {
        shouldNotBeCalled();
        throw null;
    }
}
