package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ConeTypeProjection.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0004\u0004\r\u000e\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeProjection;", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "()V", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeConflictingProjection;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeProjectionIn;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinTypeProjectionOut;", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeKotlinTypeProjection extends ConeTypeProjection {
    public /* synthetic */ ConeKotlinTypeProjection(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ConeKotlinType getType();

    private ConeKotlinTypeProjection() {
        super(null);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConeKotlinTypeProjection)) {
            return false;
        }
        ConeKotlinTypeProjection coneKotlinTypeProjection = (ConeKotlinTypeProjection) other;
        return getKind() == coneKotlinTypeProjection.getKind() && Intrinsics.areEqual(getType(), coneKotlinTypeProjection.getType());
    }

    public int hashCode() {
        return (getType().hashCode() * 31) + getKind().hashCode();
    }
}
