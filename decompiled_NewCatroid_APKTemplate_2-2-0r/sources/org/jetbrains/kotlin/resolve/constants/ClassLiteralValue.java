package org.jetbrains.kotlin.resolve.constants;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ClassLiteralValue.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÖ\u0001J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "arrayNestedness", "", "(Lorg/jetbrains/kotlin/name/ClassId;I)V", "getArrayNestedness", "()I", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassLiteralValue {
    private final int arrayNestedness;
    private final ClassId classId;

    public static /* synthetic */ ClassLiteralValue copy$default(ClassLiteralValue classLiteralValue, ClassId classId, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            classId = classLiteralValue.classId;
        }
        if ((i2 & 2) != 0) {
            i = classLiteralValue.arrayNestedness;
        }
        return classLiteralValue.copy(classId, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getArrayNestedness() {
        return this.arrayNestedness;
    }

    public final ClassLiteralValue copy(ClassId classId, int arrayNestedness) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return new ClassLiteralValue(classId, arrayNestedness);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassLiteralValue)) {
            return false;
        }
        ClassLiteralValue classLiteralValue = (ClassLiteralValue) other;
        return Intrinsics.areEqual(this.classId, classLiteralValue.classId) && this.arrayNestedness == classLiteralValue.arrayNestedness;
    }

    public int hashCode() {
        return (this.classId.hashCode() * 31) + Integer.hashCode(this.arrayNestedness);
    }

    public ClassLiteralValue(ClassId classId, int i) {
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.arrayNestedness;
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("kotlin/Array<");
        }
        sb.append(this.classId);
        int i3 = this.arrayNestedness;
        for (int i4 = 0; i4 < i3; i4++) {
            sb.append(">");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
