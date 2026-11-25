package org.jetbrains.kotlin.resolve.scopes.synthetic;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;
import org.jetbrains.kotlin.resolve.sam.SamConstructorUtilsKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionOracle;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolver;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverImplKt;
import org.jetbrains.kotlin.resolve.sam.SamConversionResolverKt;
import org.jetbrains.kotlin.resolve.scopes.SyntheticScope;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: FunInterfaceConstructorsSyntheticScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u000f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0014H\u0016J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0010\u001a\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/synthetic/FunInterfaceConstructorsSyntheticScope;", "Lorg/jetbrains/kotlin/resolve/scopes/SyntheticScope$Default;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "lookupTracker", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", "samResolver", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;", "samConversionOracle", "Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/incremental/components/LookupTracker;Lorg/jetbrains/kotlin/resolve/sam/SamConversionResolver;Lorg/jetbrains/kotlin/resolve/sam/SamConversionOracle;)V", "samConstructorForClassifier", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/resolve/sam/SamConstructorDescriptor;", "checkIfClassifierApplicable", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getSamConstructor", "getSyntheticConstructors", "", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "classifierDescriptors", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "contributedClassifier", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getTypeAliasSamConstructor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "recordSamLookupsToClassifier", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunInterfaceConstructorsSyntheticScope extends SyntheticScope.Default {
    private final LookupTracker lookupTracker;
    private final MemoizedFunctionToNotNull<ClassDescriptor, SamConstructorDescriptor> samConstructorForClassifier;
    private final SamConversionOracle samConversionOracle;
    private final SamConversionResolver samResolver;

    public FunInterfaceConstructorsSyntheticScope(StorageManager storageManager, LookupTracker lookupTracker, SamConversionResolver samResolver, SamConversionOracle samConversionOracle) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(lookupTracker, "lookupTracker");
        Intrinsics.checkNotNullParameter(samResolver, "samResolver");
        Intrinsics.checkNotNullParameter(samConversionOracle, "samConversionOracle");
        this.lookupTracker = lookupTracker;
        this.samResolver = samResolver;
        this.samConversionOracle = samConversionOracle;
        this.samConstructorForClassifier = storageManager.createMemoizedFunction(new Function1<ClassDescriptor, SamConstructorDescriptor>() { // from class: org.jetbrains.kotlin.resolve.scopes.synthetic.FunInterfaceConstructorsSyntheticScope$samConstructorForClassifier$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SamConstructorDescriptor invoke(ClassDescriptor classifier) {
                Intrinsics.checkNotNullParameter(classifier, "classifier");
                DeclarationDescriptor containingDeclaration = classifier.getContainingDeclaration();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                return SamConstructorUtilsKt.createSamConstructorFunction(containingDeclaration, classifier, this.this$0.samResolver, this.this$0.samConversionOracle);
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticConstructors(ClassifierDescriptor contributedClassifier, LookupLocation location) {
        Intrinsics.checkNotNullParameter(contributedClassifier, "contributedClassifier");
        Intrinsics.checkNotNullParameter(location, "location");
        recordSamLookupsToClassifier(contributedClassifier, location);
        return CollectionsKt.listOfNotNull(getSamConstructor(contributedClassifier));
    }

    private final SamConstructorDescriptor getSamConstructor(ClassifierDescriptor classifier) {
        if (classifier instanceof TypeAliasDescriptor) {
            return getTypeAliasSamConstructor((TypeAliasDescriptor) classifier);
        }
        ClassDescriptor classDescriptorCheckIfClassifierApplicable = checkIfClassifierApplicable(classifier);
        if (classDescriptorCheckIfClassifierApplicable == null) {
            return null;
        }
        return this.samConstructorForClassifier.invoke(classDescriptorCheckIfClassifierApplicable);
    }

    private final SamConstructorDescriptor getTypeAliasSamConstructor(TypeAliasDescriptor classifier) {
        ClassDescriptor classDescriptorCheckIfClassifierApplicable;
        ClassDescriptor classDescriptor = classifier.getClassDescriptor();
        if (classDescriptor == null || (classDescriptorCheckIfClassifierApplicable = checkIfClassifierApplicable(classDescriptor)) == null) {
            return null;
        }
        return SamConstructorUtilsKt.createTypeAliasSamConstructorFunction(classifier, this.samConstructorForClassifier.invoke(classDescriptorCheckIfClassifierApplicable), this.samResolver, this.samConversionOracle);
    }

    private final ClassDescriptor checkIfClassifierApplicable(ClassifierDescriptor classifier) {
        if (!(classifier instanceof ClassDescriptor)) {
            return null;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) classifier;
        if (classDescriptor.isFun() && SamConversionResolverImplKt.getSingleAbstractMethodOrNull(classDescriptor) != null) {
            return classDescriptor;
        }
        return null;
    }

    private final void recordSamLookupsToClassifier(ClassifierDescriptor classifier, LookupLocation location) {
        if (classifier instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) classifier;
            if (classDescriptor.isFun()) {
                UtilsKt.record(this.lookupTracker, location, classDescriptor, SamConversionResolverKt.getSAM_LOOKUP_NAME());
            }
        }
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.SyntheticScope.Default, org.jetbrains.kotlin.resolve.scopes.SyntheticScope
    public Collection<FunctionDescriptor> getSyntheticConstructors(Collection<? extends DeclarationDescriptor> classifierDescriptors) {
        SamConstructorDescriptor samConstructor;
        Intrinsics.checkNotNullParameter(classifierDescriptors, "classifierDescriptors");
        if (classifierDescriptors.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        SmartList smartList = new SmartList();
        for (Object obj : classifierDescriptors) {
            if ((obj instanceof ClassifierDescriptor) && (samConstructor = getSamConstructor((ClassifierDescriptor) obj)) != null) {
                smartList.add(samConstructor);
            }
        }
        return smartList;
    }
}
