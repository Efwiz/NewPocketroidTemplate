package org.jetbrains.kotlin.load.java;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus;

/* JADX INFO: compiled from: NullabilityQualifierWithApplicability.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/load/java/NullabilityQualifierWithApplicability;", "", "nullabilityQualifier", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;", "qualifierApplicabilityTypes", "", "Lorg/jetbrains/kotlin/load/java/AnnotationQualifierApplicabilityType;", "(Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;Ljava/util/Collection;)V", "getNullabilityQualifier", "()Lorg/jetbrains/kotlin/load/java/typeEnhancement/NullabilityQualifierWithMigrationStatus;", "getQualifierApplicabilityTypes", "()Ljava/util/Collection;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "compiler.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class NullabilityQualifierWithApplicability {
    private final NullabilityQualifierWithMigrationStatus nullabilityQualifier;
    private final Collection<AnnotationQualifierApplicabilityType> qualifierApplicabilityTypes;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ NullabilityQualifierWithApplicability copy$default(NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability, NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus, Collection collection, int i, Object obj) {
        if ((i & 1) != 0) {
            nullabilityQualifierWithMigrationStatus = nullabilityQualifierWithApplicability.nullabilityQualifier;
        }
        if ((i & 2) != 0) {
            collection = nullabilityQualifierWithApplicability.qualifierApplicabilityTypes;
        }
        return nullabilityQualifierWithApplicability.copy(nullabilityQualifierWithMigrationStatus, collection);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final NullabilityQualifierWithMigrationStatus getNullabilityQualifier() {
        return this.nullabilityQualifier;
    }

    public final Collection<AnnotationQualifierApplicabilityType> component2() {
        return this.qualifierApplicabilityTypes;
    }

    public final NullabilityQualifierWithApplicability copy(NullabilityQualifierWithMigrationStatus nullabilityQualifier, Collection<? extends AnnotationQualifierApplicabilityType> qualifierApplicabilityTypes) {
        Intrinsics.checkNotNullParameter(nullabilityQualifier, "nullabilityQualifier");
        Intrinsics.checkNotNullParameter(qualifierApplicabilityTypes, "qualifierApplicabilityTypes");
        return new NullabilityQualifierWithApplicability(nullabilityQualifier, qualifierApplicabilityTypes);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NullabilityQualifierWithApplicability)) {
            return false;
        }
        NullabilityQualifierWithApplicability nullabilityQualifierWithApplicability = (NullabilityQualifierWithApplicability) other;
        return Intrinsics.areEqual(this.nullabilityQualifier, nullabilityQualifierWithApplicability.nullabilityQualifier) && Intrinsics.areEqual(this.qualifierApplicabilityTypes, nullabilityQualifierWithApplicability.qualifierApplicabilityTypes);
    }

    public int hashCode() {
        return (this.nullabilityQualifier.hashCode() * 31) + this.qualifierApplicabilityTypes.hashCode();
    }

    public String toString() {
        return "NullabilityQualifierWithApplicability(nullabilityQualifier=" + this.nullabilityQualifier + ", qualifierApplicabilityTypes=" + this.qualifierApplicabilityTypes + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public NullabilityQualifierWithApplicability(NullabilityQualifierWithMigrationStatus nullabilityQualifier, Collection<? extends AnnotationQualifierApplicabilityType> qualifierApplicabilityTypes) {
        Intrinsics.checkNotNullParameter(nullabilityQualifier, "nullabilityQualifier");
        Intrinsics.checkNotNullParameter(qualifierApplicabilityTypes, "qualifierApplicabilityTypes");
        this.nullabilityQualifier = nullabilityQualifier;
        this.qualifierApplicabilityTypes = qualifierApplicabilityTypes;
    }

    public final NullabilityQualifierWithMigrationStatus getNullabilityQualifier() {
        return this.nullabilityQualifier;
    }

    public final Collection<AnnotationQualifierApplicabilityType> getQualifierApplicabilityTypes() {
        return this.qualifierApplicabilityTypes;
    }
}
