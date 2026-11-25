package org.jetbrains.kotlin.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.metadata.serialization.StringTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: DescriptorAwareStringTable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\nH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0002R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "Lorg/jetbrains/kotlin/metadata/serialization/StringTable;", "isLocalClassIdReplacementKeptGeneric", "", "()Z", "getFqNameIndex", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "getLocalClassIdReplacement", "Lorg/jetbrains/kotlin/name/ClassId;", "getQualifiedClassNameIndex", "classId", "renderDescriptor", "", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DescriptorAwareStringTable extends StringTable {
    default ClassId getLocalClassIdReplacement(ClassifierDescriptorWithTypeParameters descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    default boolean isLocalClassIdReplacementKeptGeneric() {
        return false;
    }

    default int getQualifiedClassNameIndex(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        String strAsString = classId.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return getQualifiedClassNameIndex(strAsString, classId.isLocal());
    }

    default int getFqNameIndex(ClassifierDescriptorWithTypeParameters descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (ErrorUtils.isError(descriptor)) {
            throw new IllegalStateException("Cannot get FQ name of error class: " + renderDescriptor(descriptor));
        }
        ClassId classId = DescriptorUtilsKt.getClassId((ClassifierDescriptor) descriptor);
        if (classId == null && (classId = getLocalClassIdReplacement(descriptor)) == null) {
            throw new IllegalStateException("Cannot get FQ name of local class: " + renderDescriptor(descriptor));
        }
        return getQualifiedClassNameIndex(classId);
    }

    private default String renderDescriptor(ClassifierDescriptorWithTypeParameters descriptor) {
        StringBuilder sbAppend = new StringBuilder().append(DescriptorRenderer.COMPACT.render(descriptor)).append(" defined in ");
        DescriptorRenderer descriptorRenderer = DescriptorRenderer.FQ_NAMES_IN_TYPES;
        DeclarationDescriptor containingDeclaration = descriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        return sbAppend.append(descriptorRenderer.render(containingDeclaration)).toString();
    }
}
