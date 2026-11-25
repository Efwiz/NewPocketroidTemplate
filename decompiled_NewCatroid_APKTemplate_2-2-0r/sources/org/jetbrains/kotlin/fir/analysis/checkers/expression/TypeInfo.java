package org.jetbrains.kotlin.fir.analysis.checkers.expression;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirEqualityCompatibilityChecker.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0006¢\u0006\u0002\u0010\fJ\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\rR\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\rR\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\rR\u0011\u0010\n\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/expression/TypeInfo;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "notNullType", "isFinalClass", "", "isEnumClass", "isPrimitive", "isBuiltin", "isValueClass", "isLiterallyTypeParameter", "(Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;ZZZZZZ)V", "()Z", "getNotNullType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getType", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypeInfo {
    private final boolean isBuiltin;
    private final boolean isEnumClass;
    private final boolean isFinalClass;
    private final boolean isLiterallyTypeParameter;
    private final boolean isPrimitive;
    private final boolean isValueClass;
    private final ConeKotlinType notNullType;
    private final ConeKotlinType type;

    public TypeInfo(ConeKotlinType type, ConeKotlinType notNullType, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(notNullType, "notNullType");
        this.type = type;
        this.notNullType = notNullType;
        this.isFinalClass = z;
        this.isEnumClass = z2;
        this.isPrimitive = z3;
        this.isBuiltin = z4;
        this.isValueClass = z5;
        this.isLiterallyTypeParameter = z6;
    }

    public final ConeKotlinType getType() {
        return this.type;
    }

    public final ConeKotlinType getNotNullType() {
        return this.notNullType;
    }

    /* JADX INFO: renamed from: isFinalClass, reason: from getter */
    public final boolean getIsFinalClass() {
        return this.isFinalClass;
    }

    /* JADX INFO: renamed from: isEnumClass, reason: from getter */
    public final boolean getIsEnumClass() {
        return this.isEnumClass;
    }

    /* JADX INFO: renamed from: isPrimitive, reason: from getter */
    public final boolean getIsPrimitive() {
        return this.isPrimitive;
    }

    /* JADX INFO: renamed from: isBuiltin, reason: from getter */
    public final boolean getIsBuiltin() {
        return this.isBuiltin;
    }

    /* JADX INFO: renamed from: isValueClass, reason: from getter */
    public final boolean getIsValueClass() {
        return this.isValueClass;
    }

    /* JADX INFO: renamed from: isLiterallyTypeParameter, reason: from getter */
    public final boolean getIsLiterallyTypeParameter() {
        return this.isLiterallyTypeParameter;
    }

    public String toString() {
        return String.valueOf(this.type);
    }
}
