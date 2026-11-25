package org.jetbrains.kotlin.resolve;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: MissingSupertypesResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\n2\u0006\u0010\f\u001a\u00020\tH\u0002J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\n2\u0006\u0010\f\u001a\u00020\tR \u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/MissingSupertypesResolver;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;)V", "missingClassifiers", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "", "doGetMissingClassifiers", "descriptor", "getMissingSuperClassifiers", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MissingSupertypesResolver {
    private final MemoizedFunctionToNotNull<ClassifierDescriptor, Set<ClassifierDescriptor>> missingClassifiers;
    private final ModuleDescriptor moduleDescriptor;

    public MissingSupertypesResolver(StorageManager storageManager, ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.moduleDescriptor = moduleDescriptor;
        this.missingClassifiers = storageManager.createMemoizedFunction(new Function1<ClassifierDescriptor, Set<? extends ClassifierDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.MissingSupertypesResolver$missingClassifiers$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Set<ClassifierDescriptor> invoke(ClassifierDescriptor classifier) {
                Intrinsics.checkNotNullParameter(classifier, "classifier");
                return this.this$0.doGetMissingClassifiers(classifier);
            }
        });
    }

    public final Set<ClassifierDescriptor> getMissingSuperClassifiers(ClassifierDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return this.missingClassifiers.invoke(descriptor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<ClassifierDescriptor> doGetMissingClassifiers(ClassifierDescriptor descriptor) {
        ClassId classId;
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        SimpleType defaultType = descriptor.getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        Iterator<KotlinType> it = TypeUtilsKt.supertypes(defaultType).iterator();
        while (it.hasNext()) {
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = it.next().getConstructor().getThis$0();
            if (classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor) {
                ClassDescriptor classDescriptor = (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor;
                if (!classDescriptor.get$isExpect() && !Intrinsics.areEqual(classDescriptor.getVisibilityImpl(), DescriptorVisibilities.LOCAL) && (classId = DescriptorUtilsKt.getClassId(classifierDescriptorMo7434getDeclarationDescriptor)) != null && ((classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(this.moduleDescriptor, classId)) == null || (classDescriptorFindClassAcrossModuleDependencies instanceof NotFoundClasses.MockClassDescriptor))) {
                    linkedHashSet.add(classifierDescriptorMo7434getDeclarationDescriptor);
                }
            }
        }
        return CollectionsKt.toSet(linkedHashSet);
    }
}
