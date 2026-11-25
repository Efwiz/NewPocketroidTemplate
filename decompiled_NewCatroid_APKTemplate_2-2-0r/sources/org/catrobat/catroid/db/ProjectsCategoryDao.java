package org.catrobat.catroid.db;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;
import org.catrobat.catroid.retrofit.models.ProjectResponse;
import org.catrobat.catroid.retrofit.models.ProjectsCategory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/db/ProjectsCategoryDao.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectsCategoryDao.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H%J\b\u0010\u0005\u001a\u00020\u0004H%J\u0014\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0007H'J\u0016\u0010\n\u001a\u00020\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0017J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH%J\u0016\u0010\u000f\u001a\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\bH%J\b\u0010\u0012\u001a\u00020\u0004H\u0017¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/db/ProjectsCategoryDao;", "", "()V", "deleteAllProjectResponse", "", "deleteAllProjectsCategory", "getProjectsCategories", "Lkotlinx/coroutines/flow/Flow;", "", "Lorg/catrobat/catroid/retrofit/models/ProjectCategoryWithResponses;", "insertProjectCategoriesWithResponses", "projectCategoryWithResponses", "insertProjectCategory", "projectsCategory", "Lorg/catrobat/catroid/retrofit/models/ProjectsCategory;", "insertProjectResponses", "projectResponseList", "Lorg/catrobat/catroid/retrofit/models/ProjectResponse;", "nukeAll", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ProjectsCategoryDao {
    protected abstract void deleteAllProjectResponse();

    protected abstract void deleteAllProjectsCategory();

    public abstract Flow<List<ProjectCategoryWithResponses>> getProjectsCategories();

    protected abstract void insertProjectCategory(ProjectsCategory projectsCategory);

    protected abstract void insertProjectResponses(List<ProjectResponse> projectResponseList);

    public void insertProjectCategoriesWithResponses(List<ProjectCategoryWithResponses> projectCategoryWithResponses) {
        Intrinsics.checkNotNullParameter(projectCategoryWithResponses, "projectCategoryWithResponses");
        nukeAll();
        for (ProjectCategoryWithResponses projectCategoryWithResponses2 : projectCategoryWithResponses) {
            List<ProjectResponse> projectsList = projectCategoryWithResponses2.getProjectsList();
            if (projectsList != null && !projectsList.isEmpty()) {
                insertProjectCategory(projectCategoryWithResponses2.getCategory());
                insertProjectResponses(projectCategoryWithResponses2.getProjectsList());
            }
        }
    }

    public void nukeAll() {
        deleteAllProjectsCategory();
        deleteAllProjectResponse();
    }
}
