package org.jetbrains.kotlin.descriptors.deserialization;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClassDescriptorFactory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00072\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\rH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/deserialization/ClassDescriptorFactory;", "", "createClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getAllContributedClassesIfPossible", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "shouldCreateClass", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ClassDescriptorFactory {
    ClassDescriptor createClass(ClassId classId);

    Collection<ClassDescriptor> getAllContributedClassesIfPossible(FqName packageFqName);

    boolean shouldCreateClass(FqName packageFqName, Name name2);
}
