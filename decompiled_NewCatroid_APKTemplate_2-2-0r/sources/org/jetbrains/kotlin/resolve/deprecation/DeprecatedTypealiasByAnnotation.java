package org.jetbrains.kotlin.resolve.deprecation;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Deprecation.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bHÖ\u0003J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001J\t\u0010\u001e\u001a\u00020\fHÖ\u0001R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedTypealiasByAnnotation;", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "typeAliasTarget", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "nested", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation;", "(Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation;)V", "deprecationLevel", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", "getDeprecationLevel", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationLevelValue;", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "getNested", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByAnnotation;", "target", "getTarget", "()Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "getTypeAliasTarget", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class DeprecatedTypealiasByAnnotation extends DescriptorBasedDeprecationInfo {
    private final DeprecatedByAnnotation nested;
    private final TypeAliasDescriptor typeAliasTarget;

    public static /* synthetic */ DeprecatedTypealiasByAnnotation copy$default(DeprecatedTypealiasByAnnotation deprecatedTypealiasByAnnotation, TypeAliasDescriptor typeAliasDescriptor, DeprecatedByAnnotation deprecatedByAnnotation, int i, Object obj) {
        if ((i & 1) != 0) {
            typeAliasDescriptor = deprecatedTypealiasByAnnotation.typeAliasTarget;
        }
        if ((i & 2) != 0) {
            deprecatedByAnnotation = deprecatedTypealiasByAnnotation.nested;
        }
        return deprecatedTypealiasByAnnotation.copy(typeAliasDescriptor, deprecatedByAnnotation);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final TypeAliasDescriptor getTypeAliasTarget() {
        return this.typeAliasTarget;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final DeprecatedByAnnotation getNested() {
        return this.nested;
    }

    public final DeprecatedTypealiasByAnnotation copy(TypeAliasDescriptor typeAliasTarget, DeprecatedByAnnotation nested) {
        Intrinsics.checkNotNullParameter(typeAliasTarget, "typeAliasTarget");
        Intrinsics.checkNotNullParameter(nested, "nested");
        return new DeprecatedTypealiasByAnnotation(typeAliasTarget, nested);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeprecatedTypealiasByAnnotation)) {
            return false;
        }
        DeprecatedTypealiasByAnnotation deprecatedTypealiasByAnnotation = (DeprecatedTypealiasByAnnotation) other;
        return Intrinsics.areEqual(this.typeAliasTarget, deprecatedTypealiasByAnnotation.typeAliasTarget) && Intrinsics.areEqual(this.nested, deprecatedTypealiasByAnnotation.nested);
    }

    public int hashCode() {
        return (this.typeAliasTarget.hashCode() * 31) + this.nested.hashCode();
    }

    public String toString() {
        return "DeprecatedTypealiasByAnnotation(typeAliasTarget=" + this.typeAliasTarget + ", nested=" + this.nested + ')';
    }

    public final TypeAliasDescriptor getTypeAliasTarget() {
        return this.typeAliasTarget;
    }

    public final DeprecatedByAnnotation getNested() {
        return this.nested;
    }

    public DeprecatedTypealiasByAnnotation(TypeAliasDescriptor typeAliasTarget, DeprecatedByAnnotation nested) {
        Intrinsics.checkNotNullParameter(typeAliasTarget, "typeAliasTarget");
        Intrinsics.checkNotNullParameter(nested, "nested");
        this.typeAliasTarget = typeAliasTarget;
        this.nested = nested;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo
    public TypeAliasDescriptor getTarget() {
        return this.typeAliasTarget;
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public DeprecationLevelValue getDeprecationLevel() {
        return this.nested.getDeprecationLevel();
    }

    @Override // org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo
    public String getMessage() {
        return this.nested.getMessage();
    }
}
