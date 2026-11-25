package org.jetbrains.kotlin.descriptors;

import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: EffectiveVisibilityUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0005HÂ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0006\u0010\f\u001a\u00020\rJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/DescriptorWithRelation;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "relation", "Lorg/jetbrains/kotlin/descriptors/RelationToType;", "(Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;Lorg/jetbrains/kotlin/descriptors/RelationToType;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "effectiveVisibility", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DescriptorWithRelation {
    private final ClassifierDescriptor descriptor;
    private final RelationToType relation;

    public static /* synthetic */ DescriptorWithRelation copy$default(DescriptorWithRelation descriptorWithRelation, ClassifierDescriptor classifierDescriptor, RelationToType relationToType, int i, Object obj) {
        if ((i & 1) != 0) {
            classifierDescriptor = descriptorWithRelation.descriptor;
        }
        if ((i & 2) != 0) {
            relationToType = descriptorWithRelation.relation;
        }
        return descriptorWithRelation.copy(classifierDescriptor, relationToType);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassifierDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final DescriptorWithRelation copy(ClassifierDescriptor descriptor, RelationToType relation) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(relation, "relation");
        return new DescriptorWithRelation(descriptor, relation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DescriptorWithRelation)) {
            return false;
        }
        DescriptorWithRelation descriptorWithRelation = (DescriptorWithRelation) other;
        return Intrinsics.areEqual(this.descriptor, descriptorWithRelation.descriptor) && this.relation == descriptorWithRelation.relation;
    }

    public int hashCode() {
        return (this.descriptor.hashCode() * 31) + this.relation.hashCode();
    }

    public DescriptorWithRelation(ClassifierDescriptor descriptor, RelationToType relation) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(relation, "relation");
        this.descriptor = descriptor;
        this.relation = relation;
    }

    public final ClassifierDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final EffectiveVisibility effectiveVisibility() {
        DescriptorVisibility visibility;
        EffectiveVisibility effectiveVisibility;
        ClassifierDescriptor classifierDescriptor = this.descriptor;
        ClassDescriptor classDescriptor = classifierDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptor : null;
        return (classDescriptor == null || (visibility = classDescriptor.getVisibility()) == null || (effectiveVisibility = EffectiveVisibilityUtilsKt.effectiveVisibility(visibility, (DeclarationDescriptor) this.descriptor, false)) == null) ? EffectiveVisibility.Public.INSTANCE : effectiveVisibility;
    }

    public String toString() {
        return new StringBuilder().append(this.relation).append(StringUtil.SPACE).append(this.descriptor.getShortName()).toString();
    }
}
