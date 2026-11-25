package org.jetbrains.kotlin.descriptors;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: DescriptorWithDeprecation.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u0016*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003:\u0001\u0016B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\tJ\t\u0010\r\u001a\u00020\u0006HÆ\u0003J(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "", "descriptor", "isDeprecated", "", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Z)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Z)Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DescriptorWithDeprecation<T extends DeclarationDescriptor> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final T descriptor;
    private final boolean isDeprecated;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DescriptorWithDeprecation copy$default(DescriptorWithDeprecation descriptorWithDeprecation, DeclarationDescriptor declarationDescriptor, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            declarationDescriptor = descriptorWithDeprecation.descriptor;
        }
        if ((i & 2) != 0) {
            z = descriptorWithDeprecation.isDeprecated;
        }
        return descriptorWithDeprecation.copy(declarationDescriptor, z);
    }

    public final T component1() {
        return this.descriptor;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsDeprecated() {
        return this.isDeprecated;
    }

    public final DescriptorWithDeprecation<T> copy(T descriptor, boolean isDeprecated) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new DescriptorWithDeprecation<>(descriptor, isDeprecated);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DescriptorWithDeprecation)) {
            return false;
        }
        DescriptorWithDeprecation descriptorWithDeprecation = (DescriptorWithDeprecation) other;
        return Intrinsics.areEqual(this.descriptor, descriptorWithDeprecation.descriptor) && this.isDeprecated == descriptorWithDeprecation.isDeprecated;
    }

    public int hashCode() {
        return (this.descriptor.hashCode() * 31) + Boolean.hashCode(this.isDeprecated);
    }

    public String toString() {
        return "DescriptorWithDeprecation(descriptor=" + this.descriptor + ", isDeprecated=" + this.isDeprecated + ')';
    }

    /* JADX INFO: compiled from: DescriptorWithDeprecation.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J#\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0001\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u0002H\u0005¢\u0006\u0002\u0010\bJ#\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\b\b\u0001\u0010\u0005*\u00020\u00062\u0006\u0010\u0007\u001a\u0002H\u0005¢\u0006\u0002\u0010\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation$Companion;", "", "()V", "createDeprecated", "Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Lorg/jetbrains/kotlin/descriptors/DescriptorWithDeprecation;", "createNonDeprecated", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final <T extends DeclarationDescriptor> DescriptorWithDeprecation<T> createNonDeprecated(T descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return new DescriptorWithDeprecation<>(descriptor, false);
        }

        public final <T extends DeclarationDescriptor> DescriptorWithDeprecation<T> createDeprecated(T descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return new DescriptorWithDeprecation<>(descriptor, true);
        }
    }

    public DescriptorWithDeprecation(T descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
        this.isDeprecated = z;
    }

    public final T getDescriptor() {
        return this.descriptor;
    }

    public final boolean isDeprecated() {
        return this.isDeprecated;
    }
}
