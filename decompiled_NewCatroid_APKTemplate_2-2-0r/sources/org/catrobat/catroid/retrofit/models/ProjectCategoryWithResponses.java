package org.catrobat.catroid.retrofit.models;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/retrofit/models/ProjectCategoryWithResponses.class
  classes14.dex
 */
/* JADX INFO: compiled from: Models.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/retrofit/models/ProjectCategoryWithResponses;", "", "category", "Lorg/catrobat/catroid/retrofit/models/ProjectsCategory;", "projectsList", "", "Lorg/catrobat/catroid/retrofit/models/ProjectResponse;", "(Lorg/catrobat/catroid/retrofit/models/ProjectsCategory;Ljava/util/List;)V", "getCategory", "()Lorg/catrobat/catroid/retrofit/models/ProjectsCategory;", "getProjectsList", "()Ljava/util/List;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ProjectCategoryWithResponses {
    private final ProjectsCategory category;
    private final List<ProjectResponse> projectsList;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ProjectCategoryWithResponses copy$default(ProjectCategoryWithResponses projectCategoryWithResponses, ProjectsCategory projectsCategory, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            projectsCategory = projectCategoryWithResponses.category;
        }
        if ((i & 2) != 0) {
            list = projectCategoryWithResponses.projectsList;
        }
        return projectCategoryWithResponses.copy(projectsCategory, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ProjectsCategory getCategory() {
        return this.category;
    }

    public final List<ProjectResponse> component2() {
        return this.projectsList;
    }

    public final ProjectCategoryWithResponses copy(ProjectsCategory category, List<ProjectResponse> projectsList) {
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(projectsList, "projectsList");
        return new ProjectCategoryWithResponses(category, projectsList);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProjectCategoryWithResponses)) {
            return false;
        }
        ProjectCategoryWithResponses projectCategoryWithResponses = (ProjectCategoryWithResponses) other;
        return Intrinsics.areEqual(this.category, projectCategoryWithResponses.category) && Intrinsics.areEqual(this.projectsList, projectCategoryWithResponses.projectsList);
    }

    public int hashCode() {
        return (this.category.hashCode() * 31) + this.projectsList.hashCode();
    }

    public String toString() {
        return "ProjectCategoryWithResponses(category=" + this.category + ", projectsList=" + this.projectsList + Constant.AFTER_QUTO;
    }

    public ProjectCategoryWithResponses(ProjectsCategory category, List<ProjectResponse> projectsList) {
        Intrinsics.checkNotNullParameter(category, "category");
        Intrinsics.checkNotNullParameter(projectsList, "projectsList");
        this.category = category;
        this.projectsList = projectsList;
    }

    public final ProjectsCategory getCategory() {
        return this.category;
    }

    public final List<ProjectResponse> getProjectsList() {
        return this.projectsList;
    }
}
