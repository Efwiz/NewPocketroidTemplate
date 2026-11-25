package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConeTypeProjection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeConflictingProjection;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeProjection;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "getKind", "()Lorg/jetbrains/kotlin/fir/types/ProjectionKind;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConeKotlinTypeConflictingProjection extends ConeKotlinTypeProjection {
    private final ConeKotlinType type;

    public static /* synthetic */ ConeKotlinTypeConflictingProjection copy$default(ConeKotlinTypeConflictingProjection coneKotlinTypeConflictingProjection, ConeKotlinType coneKotlinType, int i, Object obj) {
        if ((i & 1) != 0) {
            coneKotlinType = coneKotlinTypeConflictingProjection.type;
        }
        return coneKotlinTypeConflictingProjection.copy(coneKotlinType);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConeKotlinType getType() {
        return this.type;
    }

    public final ConeKotlinTypeConflictingProjection copy(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return new ConeKotlinTypeConflictingProjection(type);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof ConeKotlinTypeConflictingProjection) && Intrinsics.areEqual(this.type, ((ConeKotlinTypeConflictingProjection) other).type);
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public int hashCode() {
        return this.type.hashCode();
    }

    public String toString() {
        return "ConeKotlinTypeConflictingProjection(type=" + this.type + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConeKotlinTypeConflictingProjection(ConeKotlinType type) {
        super(null);
        Intrinsics.checkNotNullParameter(type, "type");
        this.type = type;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjection
    public ConeKotlinType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeTypeProjection
    public ProjectionKind getKind() {
        return ProjectionKind.INVARIANT;
    }
}
