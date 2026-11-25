package org.catrobat.catroid.ide;

import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/ProjectMetadata.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/ide/ProjectMetadata;", "", "name", "", "path", "lastModified", "", "(Ljava/lang/String;Ljava/lang/String;J)V", "getLastModified", "()J", "getName", "()Ljava/lang/String;", "getPath", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProjectMetadata {
    private final long lastModified;
    private final String name;
    private final String path;

    public static /* synthetic */ ProjectMetadata copy$default(ProjectMetadata projectMetadata, String str, String str2, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = projectMetadata.name;
        }
        if ((i & 2) != 0) {
            str2 = projectMetadata.path;
        }
        if ((i & 4) != 0) {
            j = projectMetadata.lastModified;
        }
        return projectMetadata.copy(str, str2, j);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final long getLastModified() {
        return this.lastModified;
    }

    public final ProjectMetadata copy(String name2, String path, long lastModified) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(path, "path");
        return new ProjectMetadata(name2, path, lastModified);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProjectMetadata)) {
            return false;
        }
        ProjectMetadata projectMetadata = (ProjectMetadata) other;
        return Intrinsics.areEqual(this.name, projectMetadata.name) && Intrinsics.areEqual(this.path, projectMetadata.path) && this.lastModified == projectMetadata.lastModified;
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + this.path.hashCode()) * 31) + Long.hashCode(this.lastModified);
    }

    public String toString() {
        return "ProjectMetadata(name=" + this.name + ", path=" + this.path + ", lastModified=" + this.lastModified + Constant.AFTER_QUTO;
    }

    public ProjectMetadata(String name2, String path, long j) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(path, "path");
        this.name = name2;
        this.path = path;
        this.lastModified = j;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPath() {
        return this.path;
    }

    public final long getLastModified() {
        return this.lastModified;
    }
}
