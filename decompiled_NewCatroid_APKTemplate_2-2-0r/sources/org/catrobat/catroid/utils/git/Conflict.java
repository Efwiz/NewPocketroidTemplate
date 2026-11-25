package org.catrobat.catroid.utils.git;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/Conflict.class
  classes14.dex
 */
/* JADX INFO: compiled from: GitResult.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0001\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0001\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\bJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÆ\u0003JA\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0001HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\n¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/utils/git/Conflict;", "", "path", "", "fieldName", "baseValue", "localValue", "remoteValue", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "getBaseValue", "()Ljava/lang/Object;", "getFieldName", "()Ljava/lang/String;", "getLocalValue", "getPath", "getRemoteValue", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Conflict {
    private final Object baseValue;
    private final String fieldName;
    private final Object localValue;
    private final String path;
    private final Object remoteValue;

    public static /* synthetic */ Conflict copy$default(Conflict conflict, String str, String str2, Object obj, Object obj2, Object obj3, int i, Object obj4) {
        if ((i & 1) != 0) {
            str = conflict.path;
        }
        if ((i & 2) != 0) {
            str2 = conflict.fieldName;
        }
        String str3 = str2;
        if ((i & 4) != 0) {
            obj = conflict.baseValue;
        }
        Object obj5 = obj;
        if ((i & 8) != 0) {
            obj2 = conflict.localValue;
        }
        Object obj6 = obj2;
        if ((i & 16) != 0) {
            obj3 = conflict.remoteValue;
        }
        return conflict.copy(str, str3, obj5, obj6, obj3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getFieldName() {
        return this.fieldName;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Object getBaseValue() {
        return this.baseValue;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final Object getLocalValue() {
        return this.localValue;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final Object getRemoteValue() {
        return this.remoteValue;
    }

    public final Conflict copy(String path, String fieldName, Object baseValue, Object localValue, Object remoteValue) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        return new Conflict(path, fieldName, baseValue, localValue, remoteValue);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Conflict)) {
            return false;
        }
        Conflict conflict = (Conflict) other;
        return Intrinsics.areEqual(this.path, conflict.path) && Intrinsics.areEqual(this.fieldName, conflict.fieldName) && Intrinsics.areEqual(this.baseValue, conflict.baseValue) && Intrinsics.areEqual(this.localValue, conflict.localValue) && Intrinsics.areEqual(this.remoteValue, conflict.remoteValue);
    }

    public int hashCode() {
        int iHashCode = ((this.path.hashCode() * 31) + this.fieldName.hashCode()) * 31;
        Object obj = this.baseValue;
        int iHashCode2 = (iHashCode + (obj == null ? 0 : obj.hashCode())) * 31;
        Object obj2 = this.localValue;
        int iHashCode3 = (iHashCode2 + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Object obj3 = this.remoteValue;
        return iHashCode3 + (obj3 != null ? obj3.hashCode() : 0);
    }

    public String toString() {
        return "Conflict(path=" + this.path + ", fieldName=" + this.fieldName + ", baseValue=" + this.baseValue + ", localValue=" + this.localValue + ", remoteValue=" + this.remoteValue + Constant.AFTER_QUTO;
    }

    public Conflict(String path, String fieldName, Object obj, Object obj2, Object obj3) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        this.path = path;
        this.fieldName = fieldName;
        this.baseValue = obj;
        this.localValue = obj2;
        this.remoteValue = obj3;
    }

    public final String getPath() {
        return this.path;
    }

    public final String getFieldName() {
        return this.fieldName;
    }

    public final Object getBaseValue() {
        return this.baseValue;
    }

    public final Object getLocalValue() {
        return this.localValue;
    }

    public final Object getRemoteValue() {
        return this.remoteValue;
    }
}
