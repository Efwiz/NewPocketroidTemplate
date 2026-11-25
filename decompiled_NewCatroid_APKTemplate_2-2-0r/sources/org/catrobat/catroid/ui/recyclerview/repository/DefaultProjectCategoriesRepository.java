package org.catrobat.catroid.ui.recyclerview.repository;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;
import org.catrobat.catroid.db.AppDatabase;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/repository/DefaultProjectCategoriesRepository.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectCategoriesRepository.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/repository/DefaultProjectCategoriesRepository;", "Lorg/catrobat/catroid/ui/recyclerview/repository/ProjectCategoriesRepository;", "appDatabase", "Lorg/catrobat/catroid/db/AppDatabase;", "(Lorg/catrobat/catroid/db/AppDatabase;)V", "getProjectsCategories", "Lkotlinx/coroutines/flow/Flow;", "", "Lorg/catrobat/catroid/retrofit/models/ProjectCategoryWithResponses;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultProjectCategoriesRepository implements ProjectCategoriesRepository {
    private final AppDatabase appDatabase;

    public DefaultProjectCategoriesRepository(AppDatabase appDatabase) {
        Intrinsics.checkNotNullParameter(appDatabase, "appDatabase");
        this.appDatabase = appDatabase;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.repository.ProjectCategoriesRepository
    public Flow<List<ProjectCategoryWithResponses>> getProjectsCategories() {
        return this.appDatabase.projectCategoryDao().getProjectsCategories();
    }
}
