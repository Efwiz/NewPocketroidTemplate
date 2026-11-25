package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: ConeTypeProjection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\t2\u0006\u0010\n\u001a\u00020\u0005\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\u0010\n\u001a\u0004\u0018\u00010\u0005\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0017\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000b"}, d2 = {"isStarProjection", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)Z", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getType", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;)Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "replaceType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeProjection;", "newType", "cones"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ConeTypeProjectionKt {
    public static final ConeKotlinType getType(ConeTypeProjection coneTypeProjection) {
        Intrinsics.checkNotNullParameter(coneTypeProjection, "<this>");
        if (Intrinsics.areEqual(coneTypeProjection, ConeStarProjection.INSTANCE)) {
            return null;
        }
        if (coneTypeProjection instanceof ConeKotlinTypeProjection) {
            return ((ConeKotlinTypeProjection) coneTypeProjection).getType();
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final boolean isStarProjection(ConeTypeProjection coneTypeProjection) {
        Intrinsics.checkNotNullParameter(coneTypeProjection, "<this>");
        return Intrinsics.areEqual(coneTypeProjection, ConeStarProjection.INSTANCE);
    }

    public static final ConeTypeProjection replaceType(ConeTypeProjection coneTypeProjection, ConeKotlinType coneKotlinType) {
        Intrinsics.checkNotNullParameter(coneTypeProjection, "<this>");
        if (coneTypeProjection instanceof ConeStarProjection) {
            return coneTypeProjection;
        }
        if (!(coneTypeProjection instanceof ConeKotlinTypeProjection)) {
            throw new NoWhenBranchMatchedException();
        }
        if (coneKotlinType == null) {
            throw new IllegalArgumentException("Type for non star projection should be not null".toString());
        }
        return replaceType((ConeKotlinTypeProjection) coneTypeProjection, coneKotlinType);
    }

    public static final ConeKotlinTypeProjection replaceType(ConeKotlinTypeProjection coneKotlinTypeProjection, ConeKotlinType newType) {
        Intrinsics.checkNotNullParameter(coneKotlinTypeProjection, "<this>");
        Intrinsics.checkNotNullParameter(newType, "newType");
        if (coneKotlinTypeProjection instanceof ConeKotlinType) {
            return newType;
        }
        if (coneKotlinTypeProjection instanceof ConeKotlinTypeProjectionIn) {
            return new ConeKotlinTypeProjectionIn(newType);
        }
        if (coneKotlinTypeProjection instanceof ConeKotlinTypeProjectionOut) {
            return new ConeKotlinTypeProjectionOut(newType);
        }
        if (coneKotlinTypeProjection instanceof ConeKotlinTypeConflictingProjection) {
            return new ConeKotlinTypeConflictingProjection(newType);
        }
        throw new NoWhenBranchMatchedException();
    }
}
