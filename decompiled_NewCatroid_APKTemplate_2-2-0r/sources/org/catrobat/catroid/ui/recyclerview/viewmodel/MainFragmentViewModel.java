package org.catrobat.catroid.ui.recyclerview.viewmodel;

import android.util.Log;
import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.content.backwardcompatibility.ProjectMetaDataParser;
import org.catrobat.catroid.retrofit.models.FeaturedProject;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;
import org.catrobat.catroid.sync.FeaturedProjectSyncWorker;
import org.catrobat.catroid.sync.ProjectsCategoriesSyncWorker;
import org.catrobat.catroid.ui.recyclerview.repository.FeaturedProjectsRepository;
import org.catrobat.catroid.ui.recyclerview.repository.ProjectCategoriesRepository;
import org.catrobat.catroid.utils.LiveDataExtensionsKt;
import org.catrobat.catroid.utils.NetworkConnectionMonitor;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/viewmodel/MainFragmentViewModel.class
  classes14.dex
 */
/* JADX INFO: compiled from: MainFragmentViewModel.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\u0018\u0000 %2\u00020\u0001:\u0001%B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ*\u0010\u0011\u001a&\u0012\"\u0012 \u0012\u0004\u0012\u00020\r\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u000f0\u00130\u0012J\u001a\u0010\u0016\u001a\u00020\u00172\u0010\u0010\u0018\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u001a0\u0019H\u0002J\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002J\u0012\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u0012J\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\r0\u0012J\u0006\u0010 \u001a\u00020\u001cJ\u0010\u0010!\u001a\u00020\u001c2\b\b\u0002\u0010\"\u001a\u00020\rJ\u0006\u0010#\u001a\u00020\u001cJ\u0006\u0010$\u001a\u00020\u001cR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/viewmodel/MainFragmentViewModel;", "Landroidx/lifecycle/ViewModel;", "workManager", "Landroidx/work/WorkManager;", "featuredProjectsRepository", "Lorg/catrobat/catroid/ui/recyclerview/repository/FeaturedProjectsRepository;", "projectCategoriesRepository", "Lorg/catrobat/catroid/ui/recyclerview/repository/ProjectCategoriesRepository;", "connectionMonitor", "Lorg/catrobat/catroid/utils/NetworkConnectionMonitor;", "(Landroidx/work/WorkManager;Lorg/catrobat/catroid/ui/recyclerview/repository/FeaturedProjectsRepository;Lorg/catrobat/catroid/ui/recyclerview/repository/ProjectCategoriesRepository;Lorg/catrobat/catroid/utils/NetworkConnectionMonitor;)V", "isLoadingData", "Landroidx/lifecycle/MutableLiveData;", "", "projectList", "", "Lorg/catrobat/catroid/common/ProjectData;", "connectionStatusAndFeaturedProjectsAndProjectCategoriesLiveData", "Landroidx/lifecycle/LiveData;", "Lkotlin/Triple;", "Lorg/catrobat/catroid/retrofit/models/FeaturedProject;", "Lorg/catrobat/catroid/retrofit/models/ProjectCategoryWithResponses;", "createPeriodicWorkerRequestOf", "Landroidx/work/PeriodicWorkRequest;", "workerClass", "Ljava/lang/Class;", "Landroidx/work/ListenableWorker;", "forceUpdate", "", "getProjectData", "getProjects", "isLoading", "registerNetworkCallback", "setIsLoading", "loading", "unregisterNetworkCallback", ConfigConstants.CONFIG_KEY_UPDATE, "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MainFragmentViewModel extends ViewModel {
    private static final long BACKOFF_DELAY = 20;
    private static final String FEATURED_PROJECTS_WORK_NAME = "featured_projects_work";
    private static final String PROJECTS_CATEGORIES_WORK_NAME = "projects_categories_work";
    private static final long REPEATED_INTERVAL = 1;
    private final NetworkConnectionMonitor connectionMonitor;
    private final FeaturedProjectsRepository featuredProjectsRepository;
    private final MutableLiveData<Boolean> isLoadingData;
    private final ProjectCategoriesRepository projectCategoriesRepository;
    private final MutableLiveData<List<ProjectData>> projectList;
    private final WorkManager workManager;

    public MainFragmentViewModel(WorkManager workManager, FeaturedProjectsRepository featuredProjectsRepository, ProjectCategoriesRepository projectCategoriesRepository, NetworkConnectionMonitor connectionMonitor) {
        Intrinsics.checkNotNullParameter(workManager, "workManager");
        Intrinsics.checkNotNullParameter(featuredProjectsRepository, "featuredProjectsRepository");
        Intrinsics.checkNotNullParameter(projectCategoriesRepository, "projectCategoriesRepository");
        Intrinsics.checkNotNullParameter(connectionMonitor, "connectionMonitor");
        this.workManager = workManager;
        this.featuredProjectsRepository = featuredProjectsRepository;
        this.projectCategoriesRepository = projectCategoriesRepository;
        this.connectionMonitor = connectionMonitor;
        this.projectList = new MutableLiveData<>();
        update();
        this.isLoadingData = new MutableLiveData<>(true);
    }

    public final LiveData<List<ProjectData>> getProjects() {
        return this.projectList;
    }

    private final List<ProjectData> getProjectData() {
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = FlavoredConstants.DEFAULT_ROOT_DIRECTORY.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
                if (file2.exists()) {
                    try {
                        ProjectData projectMetaData = new ProjectMetaDataParser(file2).getProjectMetaData();
                        Intrinsics.checkNotNullExpressionValue(projectMetaData, "getProjectMetaData(...)");
                        arrayList.add(projectMetaData);
                    } catch (IOException e) {
                        Log.e(getClass().getSimpleName(), "Project not parsable", e);
                    }
                }
            }
        }
        return CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.viewmodel.MainFragmentViewModel$getProjectData$$inlined$sortedByDescending$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Long.valueOf(((ProjectData) t2).getLastUsed()), Long.valueOf(((ProjectData) t).getLastUsed()));
            }
        });
    }

    public final void forceUpdate() {
        this.projectList.postValue(getProjectData());
    }

    public final void update() {
        this.workManager.enqueueUniquePeriodicWork(FEATURED_PROJECTS_WORK_NAME, ExistingPeriodicWorkPolicy.REPLACE, createPeriodicWorkerRequestOf(FeaturedProjectSyncWorker.class));
        this.workManager.enqueueUniquePeriodicWork(PROJECTS_CATEGORIES_WORK_NAME, ExistingPeriodicWorkPolicy.REPLACE, createPeriodicWorkerRequestOf(ProjectsCategoriesSyncWorker.class));
    }

    public final LiveData<Boolean> isLoading() {
        return this.isLoadingData;
    }

    public static /* synthetic */ void setIsLoading$default(MainFragmentViewModel mainFragmentViewModel, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        mainFragmentViewModel.setIsLoading(z);
    }

    public final void setIsLoading(boolean loading) {
        this.isLoadingData.postValue(Boolean.valueOf(loading));
    }

    public final LiveData<Triple<Boolean, List<FeaturedProject>, List<ProjectCategoryWithResponses>>> connectionStatusAndFeaturedProjectsAndProjectCategoriesLiveData() {
        return LiveDataExtensionsKt.combineWith(this.connectionMonitor, FlowLiveDataConversions.asLiveData$default(this.featuredProjectsRepository.getFeaturedProjects(), (CoroutineContext) null, 0L, 3, (Object) null), FlowLiveDataConversions.asLiveData$default(this.projectCategoriesRepository.getProjectsCategories(), (CoroutineContext) null, 0L, 3, (Object) null));
    }

    public final void registerNetworkCallback() {
        this.connectionMonitor.registerDefaultNetworkCallback();
    }

    public final void unregisterNetworkCallback() {
        this.connectionMonitor.unregisterDefaultNetworkCallback();
    }

    private final PeriodicWorkRequest createPeriodicWorkerRequestOf(Class<? extends ListenableWorker> workerClass) {
        Constraints constraintsBuild = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).setRequiresStorageNotLow(true).setRequiresBatteryNotLow(true).build();
        Intrinsics.checkNotNullExpressionValue(constraintsBuild, "build(...)");
        PeriodicWorkRequest periodicWorkRequestBuild = new PeriodicWorkRequest.Builder(workerClass, 1L, TimeUnit.DAYS).setConstraints(constraintsBuild).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, BACKOFF_DELAY, TimeUnit.SECONDS).build();
        Intrinsics.checkNotNullExpressionValue(periodicWorkRequestBuild, "build(...)");
        return periodicWorkRequestBuild;
    }
}
