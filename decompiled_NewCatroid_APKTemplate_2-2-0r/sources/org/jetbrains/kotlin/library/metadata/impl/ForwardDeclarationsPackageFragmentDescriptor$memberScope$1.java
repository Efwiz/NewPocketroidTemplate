package org.jetbrains.kotlin.library.metadata.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorImpl;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NativeStandardInteropNames;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J\u0010\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0011\u001a\u00020\u0004H\u0002J\u0018\u0010\u0014\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0006\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001b\u0010\f\u001a\u00020\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\r\u0010\t¨\u0006\u001b"}, d2 = {"org/jetbrains/kotlin/library/metadata/impl/ForwardDeclarationsPackageFragmentDescriptor$memberScope$1", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScopeImpl;", "declarations", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "experimentalAnnotationType", "Lorg/jetbrains/kotlin/types/SimpleType;", "getExperimentalAnnotationType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "experimentalAnnotationType$delegate", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "supertype", "getSupertype", "supertype$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "createDeclaration", "name", "findCinteropClass", "findCinteropClassOrNull", "getContributedClassifier", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "printScopeStructure", "", "p", "Lorg/jetbrains/kotlin/utils/Printer;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForwardDeclarationsPackageFragmentDescriptor$memberScope$1 extends MemberScopeImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ForwardDeclarationsPackageFragmentDescriptor$memberScope$1.class), "supertype", "getSupertype()Lorg/jetbrains/kotlin/types/SimpleType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(ForwardDeclarationsPackageFragmentDescriptor$memberScope$1.class), "experimentalAnnotationType", "getExperimentalAnnotationType()Lorg/jetbrains/kotlin/types/SimpleType;"))};
    final /* synthetic */ ClassKind $classKind;
    final /* synthetic */ boolean $isExpect;
    private final MemoizedFunctionToNotNull<Name, ClassDescriptor> declarations;

    /* JADX INFO: renamed from: experimentalAnnotationType$delegate, reason: from kotlin metadata */
    private final NullableLazyValue experimentalAnnotationType;

    /* JADX INFO: renamed from: supertype$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue supertype;
    final /* synthetic */ ForwardDeclarationsPackageFragmentDescriptor this$0;

    ForwardDeclarationsPackageFragmentDescriptor$memberScope$1(StorageManager storageManager, final Name name2, ForwardDeclarationsPackageFragmentDescriptor forwardDeclarationsPackageFragmentDescriptor, ClassKind classKind, boolean z) {
        this.this$0 = forwardDeclarationsPackageFragmentDescriptor;
        this.$classKind = classKind;
        this.$isExpect = z;
        this.declarations = storageManager.createMemoizedFunction(new ForwardDeclarationsPackageFragmentDescriptor$memberScope$1$declarations$1(this));
        this.supertype = storageManager.createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.library.metadata.impl.ForwardDeclarationsPackageFragmentDescriptor$memberScope$1$supertype$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                return this.this$0.findCinteropClass(name2).getDefaultType();
            }
        });
        this.experimentalAnnotationType = storageManager.createNullableLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.library.metadata.impl.ForwardDeclarationsPackageFragmentDescriptor$memberScope$1$experimentalAnnotationType$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                ClassDescriptor classDescriptorFindCinteropClassOrNull = this.this$0.findCinteropClassOrNull(NativeStandardInteropNames.INSTANCE.getExperimentalForeignApi());
                if (classDescriptorFindCinteropClassOrNull != null) {
                    return classDescriptorFindCinteropClassOrNull.getDefaultType();
                }
                return null;
            }
        });
    }

    private final SimpleType getSupertype() {
        return (SimpleType) StorageKt.getValue(this.supertype, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final SimpleType getExperimentalAnnotationType() {
        return (SimpleType) StorageKt.getValue(this.experimentalAnnotationType, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor findCinteropClass(Name name2) {
        ClassDescriptor classDescriptorFindCinteropClassOrNull = findCinteropClassOrNull(name2);
        if (classDescriptorFindCinteropClassOrNull != null) {
            return classDescriptorFindCinteropClassOrNull;
        }
        throw new IllegalStateException(("Class " + name2 + " is not found").toString());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor findCinteropClassOrNull(Name name2) {
        return (ClassDescriptor) DescriptorUtilsKt.getBuiltIns(this.this$0).getBuiltInsModule().getPackage(NativeStandardInteropNames.INSTANCE.getCInteropPackage()).getMemberScope().mo7465getContributedClassifier(name2, NoLookupLocation.FROM_BACKEND);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor createDeclaration(final Name name2) {
        SimpleType experimentalAnnotationType = getExperimentalAnnotationType();
        final AnnotationDescriptorImpl annotationDescriptorImpl = experimentalAnnotationType != null ? new AnnotationDescriptorImpl(experimentalAnnotationType, MapsKt.emptyMap(), SourceElement.NO_SOURCE) : null;
        final Modality modality = Modality.FINAL;
        final List listListOf = CollectionsKt.listOf(getSupertype());
        final SourceElement sourceElement = SourceElement.NO_SOURCE;
        final StorageManager storageManager = LockBasedStorageManager.NO_LOCKS;
        final ForwardDeclarationsPackageFragmentDescriptor forwardDeclarationsPackageFragmentDescriptor = this.this$0;
        final ClassKind classKind = this.$classKind;
        final boolean z = this.$isExpect;
        ClassDescriptorImpl classDescriptorImpl = new ClassDescriptorImpl(annotationDescriptorImpl, forwardDeclarationsPackageFragmentDescriptor, name2, classKind, z, modality, listListOf, sourceElement, storageManager) { // from class: org.jetbrains.kotlin.library.metadata.impl.ForwardDeclarationsPackageFragmentDescriptor$memberScope$1$createDeclaration$1
            final /* synthetic */ boolean $isExpect;
            private final Annotations annotations;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(forwardDeclarationsPackageFragmentDescriptor, name2, modality, classKind, listListOf, sourceElement, false, storageManager);
                this.$isExpect = z;
                this.annotations = Annotations.INSTANCE.create(CollectionsKt.listOfNotNull(annotationDescriptorImpl));
            }

            @Override // org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl, org.jetbrains.kotlin.descriptors.MemberDescriptor
            /* JADX INFO: renamed from: isExpect, reason: from getter */
            public boolean get$isExpect() {
                return this.$isExpect;
            }

            @Override // org.jetbrains.kotlin.descriptors.impl.ClassDescriptorImpl, org.jetbrains.kotlin.descriptors.annotations.Annotated
            public Annotations getAnnotations() {
                return this.annotations;
            }
        };
        classDescriptorImpl.initialize(MemberScope.Empty.INSTANCE, SetsKt.emptySet(), null);
        return classDescriptorImpl;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: getContributedClassifier */
    public ClassDescriptor mo7465getContributedClassifier(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        return this.declarations.invoke(name2);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    /* JADX INFO: renamed from: printScopeStructure */
    public void mo6105printScopeStructure(Printer p) {
        Intrinsics.checkNotNullParameter(p, "p");
        p.println(getClass().getSimpleName(), "{}");
    }
}
