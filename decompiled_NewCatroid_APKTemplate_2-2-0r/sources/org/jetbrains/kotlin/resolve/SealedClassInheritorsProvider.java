package org.jetbrains.kotlin.resolve;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;

/* JADX INFO: compiled from: SealedClassInheritorsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/SealedClassInheritorsProvider;", "", "()V", "computeSealedSubclasses", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "sealedClass", "allowSealedInheritorsInDifferentFilesOfSamePackage", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class SealedClassInheritorsProvider {
    public abstract Collection<ClassDescriptor> computeSealedSubclasses(ClassDescriptor sealedClass, boolean allowSealedInheritorsInDifferentFilesOfSamePackage);
}
