package org.catrobat.catroid.sync;

import android.util.Log;
import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.db.AppDatabase;
import org.catrobat.catroid.retrofit.WebService;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;
import org.catrobat.catroid.retrofit.models.ProjectsCategoryApi;
import org.catrobat.catroid.ui.recyclerview.repository.LocalHashVersionRepository;
import org.catrobat.catroid.utils.ExtensionsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import retrofit2.Response;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/sync/DefaultProjectsCategoriesSync.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectsCategoriesSync.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\nH\u0017J\u0018\u0010\u0011\u001a\u00020\u000f2\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\u0014\u0018\u00010\u0013H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/sync/DefaultProjectsCategoriesSync;", "Lorg/catrobat/catroid/sync/ProjectsCategoriesSync;", "webService", "Lorg/catrobat/catroid/retrofit/WebService;", "appDatabase", "Lorg/catrobat/catroid/db/AppDatabase;", "localHashVersionRepository", "Lorg/catrobat/catroid/ui/recyclerview/repository/LocalHashVersionRepository;", "(Lorg/catrobat/catroid/retrofit/WebService;Lorg/catrobat/catroid/db/AppDatabase;Lorg/catrobat/catroid/ui/recyclerview/repository/LocalHashVersionRepository;)V", "requireUpdate", "", "localHashVersion", "", "serverHashVersion", "sync", "", "force", ConfigConstants.CONFIG_KEY_UPDATE, "body", "", "Lorg/catrobat/catroid/retrofit/models/ProjectsCategoryApi;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultProjectsCategoriesSync implements ProjectsCategoriesSync {
    private final AppDatabase appDatabase;
    private final LocalHashVersionRepository localHashVersionRepository;
    private final WebService webService;

    public DefaultProjectsCategoriesSync(WebService webService, AppDatabase appDatabase, LocalHashVersionRepository localHashVersionRepository) {
        Intrinsics.checkNotNullParameter(webService, "webService");
        Intrinsics.checkNotNullParameter(appDatabase, "appDatabase");
        Intrinsics.checkNotNullParameter(localHashVersionRepository, "localHashVersionRepository");
        this.webService = webService;
        this.appDatabase = appDatabase;
        this.localHashVersionRepository = localHashVersionRepository;
    }

    @Override // org.catrobat.catroid.sync.ProjectsCategoriesSync
    public void sync(boolean force) throws IOException {
        String projectsCategoriesHashVersion = this.localHashVersionRepository.getProjectsCategoriesHashVersion();
        Response responseExecute = WebService.DefaultImpls.getProjectCategories$default(this.webService, null, null, 3, null).execute();
        String str = responseExecute.headers().get("x-response-hash");
        Log.d(getClass().getSimpleName(), "local stored hash version: " + projectsCategoriesHashVersion);
        Log.d(getClass().getSimpleName(), "server hash version: " + str);
        if (requireUpdate(projectsCategoriesHashVersion, str) || force) {
            update((List) responseExecute.body());
            this.localHashVersionRepository.setProjectsCategoriesHashVersion(str);
        } else {
            Log.d(getClass().getSimpleName(), "no update needed! you've latest version :)");
        }
    }

    private final void update(List<ProjectsCategoryApi> body) {
        List<ProjectCategoryWithResponses> projectCategoryWithResponsesList;
        Log.d(getClass().getSimpleName(), "updating projectsCategories");
        Log.d(getClass().getSimpleName(), String.valueOf(body));
        if (body == null || (projectCategoryWithResponsesList = ExtensionsKt.toProjectCategoryWithResponsesList(body)) == null) {
            return;
        }
        this.appDatabase.projectCategoryDao().nukeAll();
        this.appDatabase.projectCategoryDao().insertProjectCategoriesWithResponses(projectCategoryWithResponsesList);
    }

    private final boolean requireUpdate(String localHashVersion, String serverHashVersion) {
        return localHashVersion == null || !Intrinsics.areEqual(localHashVersion, serverHashVersion);
    }
}
