package org.catrobat.catroid.retrofit.models;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/models/ProjectsCategoryApi.class
  classes14.dex
 */
/* JADX INFO: compiled from: Models.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J-\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/retrofit/models/ProjectsCategoryApi;", "", ModuleXmlParser.TYPE, "", "name", "projectsList", "", "Lorg/catrobat/catroid/retrofit/models/ProjectResponseApi;", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getName", "()Ljava/lang/String;", "getProjectsList", "()Ljava/util/List;", "getType", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProjectsCategoryApi {
    private final String name;
    private final List<ProjectResponseApi> projectsList;
    private final String type;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ProjectsCategoryApi copy$default(ProjectsCategoryApi projectsCategoryApi, String str, String str2, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = projectsCategoryApi.type;
        }
        if ((i & 2) != 0) {
            str2 = projectsCategoryApi.name;
        }
        if ((i & 4) != 0) {
            list = projectsCategoryApi.projectsList;
        }
        return projectsCategoryApi.copy(str, str2, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getType() {
        return this.type;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    public final List<ProjectResponseApi> component3() {
        return this.projectsList;
    }

    public final ProjectsCategoryApi copy(String type, String name2, List<ProjectResponseApi> projectsList) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(projectsList, "projectsList");
        return new ProjectsCategoryApi(type, name2, projectsList);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProjectsCategoryApi)) {
            return false;
        }
        ProjectsCategoryApi projectsCategoryApi = (ProjectsCategoryApi) other;
        return Intrinsics.areEqual(this.type, projectsCategoryApi.type) && Intrinsics.areEqual(this.name, projectsCategoryApi.name) && Intrinsics.areEqual(this.projectsList, projectsCategoryApi.projectsList);
    }

    public int hashCode() {
        return (((this.type.hashCode() * 31) + this.name.hashCode()) * 31) + this.projectsList.hashCode();
    }

    public String toString() {
        return "ProjectsCategoryApi(type=" + this.type + ", name=" + this.name + ", projectsList=" + this.projectsList + Constant.AFTER_QUTO;
    }

    public ProjectsCategoryApi(String type, String name2, List<ProjectResponseApi> projectsList) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(projectsList, "projectsList");
        this.type = type;
        this.name = name2;
        this.projectsList = projectsList;
    }

    public final String getType() {
        return this.type;
    }

    public final String getName() {
        return this.name;
    }

    public final List<ProjectResponseApi> getProjectsList() {
        return this.projectsList;
    }
}
