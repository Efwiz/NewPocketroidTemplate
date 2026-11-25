package org.jetbrains.kotlin.psi.stubs.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: KotlinConstantValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/stubs/impl/KClassData;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "arrayNestedness", "", "(Lorg/jetbrains/kotlin/name/ClassId;I)V", "getArrayNestedness", "()I", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class KClassData {
    private final int arrayNestedness;
    private final ClassId classId;

    public static /* synthetic */ KClassData copy$default(KClassData kClassData, ClassId classId, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            classId = kClassData.classId;
        }
        if ((i2 & 2) != 0) {
            i = kClassData.arrayNestedness;
        }
        return kClassData.copy(classId, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getArrayNestedness() {
        return this.arrayNestedness;
    }

    public final KClassData copy(ClassId classId, int arrayNestedness) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return new KClassData(classId, arrayNestedness);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KClassData)) {
            return false;
        }
        KClassData kClassData = (KClassData) other;
        return Intrinsics.areEqual(this.classId, kClassData.classId) && this.arrayNestedness == kClassData.arrayNestedness;
    }

    public int hashCode() {
        return (this.classId.hashCode() * 31) + Integer.hashCode(this.arrayNestedness);
    }

    public String toString() {
        return "KClassData(classId=" + this.classId + ", arrayNestedness=" + this.arrayNestedness + ')';
    }

    public KClassData(ClassId classId, int i) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        this.classId = classId;
        this.arrayNestedness = i;
    }

    public final int getArrayNestedness() {
        return this.arrayNestedness;
    }

    public final ClassId getClassId() {
        return this.classId;
    }
}
