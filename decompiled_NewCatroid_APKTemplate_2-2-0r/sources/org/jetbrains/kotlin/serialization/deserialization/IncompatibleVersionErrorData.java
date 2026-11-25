package org.jetbrains.kotlin.serialization.deserialization;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: IncompatibleVersionErrorData.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002B5\u0012\u0006\u0010\u0003\u001a\u00028\u0000\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u0012\u0006\u0010\u0006\u001a\u00028\u0000\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u000e\u0010\u0016\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\rJ\u000e\u0010\u0017\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\rJ\u000e\u0010\u0018\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\rJ\u000e\u0010\u0019\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\rJ\t\u0010\u001a\u001a\u00020\bHÆ\u0003J\t\u0010\u001b\u001a\u00020\nHÆ\u0003JP\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00028\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00002\b\b\u0002\u0010\u0005\u001a\u00028\u00002\b\b\u0002\u0010\u0006\u001a\u00028\u00002\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001¢\u0006\u0002\u0010\u001dJ\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0002HÖ\u0003J\t\u0010!\u001a\u00020\"HÖ\u0001J\t\u0010#\u001a\u00020\bHÖ\u0001R\u0013\u0010\u0003\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\u0011\u0010\rR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\u0012\u0010\rR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\u0015\u0010\r¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", ExifInterface.GPS_DIRECTION_TRUE, "", "actualVersion", "compilerVersion", "languageVersion", "expectedVersion", "filePath", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;Lorg/jetbrains/kotlin/name/ClassId;)V", "getActualVersion", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getCompilerVersion", "getExpectedVersion", "getFilePath", "()Ljava/lang/String;", "getLanguageVersion", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;Lorg/jetbrains/kotlin/name/ClassId;)Lorg/jetbrains/kotlin/serialization/deserialization/IncompatibleVersionErrorData;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IncompatibleVersionErrorData<T> {
    private final T actualVersion;
    private final ClassId classId;
    private final T compilerVersion;
    private final T expectedVersion;
    private final String filePath;
    private final T languageVersion;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IncompatibleVersionErrorData copy$default(IncompatibleVersionErrorData incompatibleVersionErrorData, Object obj, Object obj2, Object obj3, Object obj4, String str, ClassId classId, int i, Object obj5) {
        if ((i & 1) != 0) {
            obj = incompatibleVersionErrorData.actualVersion;
        }
        if ((i & 2) != 0) {
            obj2 = incompatibleVersionErrorData.compilerVersion;
        }
        Object obj6 = obj2;
        if ((i & 4) != 0) {
            obj3 = incompatibleVersionErrorData.languageVersion;
        }
        Object obj7 = obj3;
        if ((i & 8) != 0) {
            obj4 = incompatibleVersionErrorData.expectedVersion;
        }
        Object obj8 = obj4;
        if ((i & 16) != 0) {
            str = incompatibleVersionErrorData.filePath;
        }
        String str2 = str;
        if ((i & 32) != 0) {
            classId = incompatibleVersionErrorData.classId;
        }
        return incompatibleVersionErrorData.copy(obj, obj6, obj7, obj8, str2, classId);
    }

    public final T component1() {
        return this.actualVersion;
    }

    public final T component2() {
        return this.compilerVersion;
    }

    public final T component3() {
        return this.languageVersion;
    }

    public final T component4() {
        return this.expectedVersion;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getFilePath() {
        return this.filePath;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final ClassId getClassId() {
        return this.classId;
    }

    public final IncompatibleVersionErrorData<T> copy(T actualVersion, T compilerVersion, T languageVersion, T expectedVersion, String filePath, ClassId classId) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(classId, "classId");
        return new IncompatibleVersionErrorData<>(actualVersion, compilerVersion, languageVersion, expectedVersion, filePath, classId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IncompatibleVersionErrorData)) {
            return false;
        }
        IncompatibleVersionErrorData incompatibleVersionErrorData = (IncompatibleVersionErrorData) other;
        return Intrinsics.areEqual(this.actualVersion, incompatibleVersionErrorData.actualVersion) && Intrinsics.areEqual(this.compilerVersion, incompatibleVersionErrorData.compilerVersion) && Intrinsics.areEqual(this.languageVersion, incompatibleVersionErrorData.languageVersion) && Intrinsics.areEqual(this.expectedVersion, incompatibleVersionErrorData.expectedVersion) && Intrinsics.areEqual(this.filePath, incompatibleVersionErrorData.filePath) && Intrinsics.areEqual(this.classId, incompatibleVersionErrorData.classId);
    }

    public int hashCode() {
        T t = this.actualVersion;
        int iHashCode = (t == null ? 0 : t.hashCode()) * 31;
        T t2 = this.compilerVersion;
        int iHashCode2 = (iHashCode + (t2 == null ? 0 : t2.hashCode())) * 31;
        T t3 = this.languageVersion;
        int iHashCode3 = (iHashCode2 + (t3 == null ? 0 : t3.hashCode())) * 31;
        T t4 = this.expectedVersion;
        return ((((iHashCode3 + (t4 != null ? t4.hashCode() : 0)) * 31) + this.filePath.hashCode()) * 31) + this.classId.hashCode();
    }

    public String toString() {
        return "IncompatibleVersionErrorData(actualVersion=" + this.actualVersion + ", compilerVersion=" + this.compilerVersion + ", languageVersion=" + this.languageVersion + ", expectedVersion=" + this.expectedVersion + ", filePath=" + this.filePath + ", classId=" + this.classId + ')';
    }

    public IncompatibleVersionErrorData(T t, T t2, T t3, T t4, String filePath, ClassId classId) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(classId, "classId");
        this.actualVersion = t;
        this.compilerVersion = t2;
        this.languageVersion = t3;
        this.expectedVersion = t4;
        this.filePath = filePath;
        this.classId = classId;
    }

    public final T getActualVersion() {
        return this.actualVersion;
    }

    public final T getCompilerVersion() {
        return this.compilerVersion;
    }

    public final T getLanguageVersion() {
        return this.languageVersion;
    }

    public final T getExpectedVersion() {
        return this.expectedVersion;
    }

    public final String getFilePath() {
        return this.filePath;
    }

    public final ClassId getClassId() {
        return this.classId;
    }
}
