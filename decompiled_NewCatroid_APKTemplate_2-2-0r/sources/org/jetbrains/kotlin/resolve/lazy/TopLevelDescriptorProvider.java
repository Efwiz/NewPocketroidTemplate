package org.jetbrains.kotlin.resolve.lazy;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyPackageDescriptor;

/* JADX INFO: compiled from: TopLevelDescriptorProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u001a\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\nH&J\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;", "", "assertValid", "", "getPackageFragment", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyPackageDescriptor;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFragmentOrDiagnoseFailure", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/psi/KtFile;", "getTopLevelClassifierDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TopLevelDescriptorProvider {
    void assertValid();

    LazyPackageDescriptor getPackageFragment(FqName fqName);

    LazyPackageDescriptor getPackageFragmentOrDiagnoseFailure(FqName fqName, KtFile from);

    Collection<ClassifierDescriptor> getTopLevelClassifierDescriptors(FqName fqName, LookupLocation location);
}
