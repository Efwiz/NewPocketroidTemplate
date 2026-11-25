package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.databinding.FragmentMainMenuBinding;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.io.asynctask.ProjectLoaderKt;
import org.catrobat.catroid.retrofit.models.FeaturedProject;
import org.catrobat.catroid.retrofit.models.ProjectCategoryWithResponses;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.CommunityWebViewActivity;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.ProjectListActivity;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment;
import org.catrobat.catroid.ui.recyclerview.CategoryTitleCallback;
import org.catrobat.catroid.ui.recyclerview.FeaturedProjectCallback;
import org.catrobat.catroid.ui.recyclerview.ProjectListener;
import org.catrobat.catroid.ui.recyclerview.adapter.CategoriesAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.FeaturedProjectsAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.HorizontalProjectsAdapter;
import org.catrobat.catroid.ui.recyclerview.viewmodel.MainFragmentViewModel;
import org.catrobat.catroid.utils.ExtensionsKt;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ProjectDownloadUtil;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.androidx.viewmodel.ext.android.ViewModelStoreOwnerExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/MainMenuFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: MainMenuFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0007\u0018\u0000 S2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u0006:\u0001SB\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0014H\u0002J\b\u0010/\u001a\u00020-H\u0002J\u0012\u00100\u001a\u00020-2\b\u00101\u001a\u0004\u0018\u000102H\u0016J\u0010\u00103\u001a\u00020-2\u0006\u00104\u001a\u00020\u0014H\u0016J\u0010\u00105\u001a\u00020-2\u0006\u00106\u001a\u000207H\u0016J$\u00108\u001a\u0002072\u0006\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00010<2\b\u00101\u001a\u0004\u0018\u000102H\u0016J\b\u0010=\u001a\u00020-H\u0016J\u0010\u0010>\u001a\u00020-2\u0006\u0010?\u001a\u00020\u0014H\u0016J\u0010\u0010@\u001a\u00020-2\u0006\u0010A\u001a\u00020BH\u0016J\b\u0010C\u001a\u00020-H\u0016J\u0012\u0010D\u001a\u00020-2\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J\b\u0010G\u001a\u00020-H\u0016J\u0012\u0010H\u001a\u00020-2\b\u0010I\u001a\u0004\u0018\u00010\u0014H\u0002J\u0006\u0010J\u001a\u00020-J\u0016\u0010K\u001a\u00020-2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020F0MH\u0002J\b\u0010N\u001a\u00020-H\u0002J\b\u0010O\u001a\u00020-H\u0002J\b\u0010P\u001a\u00020-H\u0002J\b\u0010Q\u001a\u00020-H\u0002J\u0016\u0010R\u001a\u00020-2\f\u0010L\u001a\b\u0012\u0004\u0012\u00020F0MH\u0002R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u0012\u001a\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010 \u001a\u00020!8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0012\u001a\u0004\b\"\u0010#R\u000e\u0010%\u001a\u00020&X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010'\u001a\u00020(8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b+\u0010\u0012\u001a\u0004\b)\u0010*¨\u0006T"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/MainMenuFragment;", "Landroidx/fragment/app/Fragment;", "Lorg/catrobat/catroid/ui/recyclerview/ProjectListener;", "Landroid/view/View$OnClickListener;", "Lorg/catrobat/catroid/ui/recyclerview/FeaturedProjectCallback;", "Lorg/catrobat/catroid/ui/recyclerview/CategoryTitleCallback;", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/FragmentMainMenuBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/FragmentMainMenuBinding;", "categoriesAdapter", "Lorg/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter;", "getCategoriesAdapter", "()Lorg/catrobat/catroid/ui/recyclerview/adapter/CategoriesAdapter;", "categoriesAdapter$delegate", "Lkotlin/Lazy;", "currentProject", "", "getCurrentProject", "()Ljava/lang/String;", "setCurrentProject", "(Ljava/lang/String;)V", "featuredProjectsAdapter", "Lorg/catrobat/catroid/ui/recyclerview/adapter/FeaturedProjectsAdapter;", "getFeaturedProjectsAdapter", "()Lorg/catrobat/catroid/ui/recyclerview/adapter/FeaturedProjectsAdapter;", "featuredProjectsAdapter$delegate", "progressBar", "Landroid/widget/LinearLayout;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "projectsAdapter", "Lorg/catrobat/catroid/ui/recyclerview/adapter/HorizontalProjectsAdapter;", "viewModel", "Lorg/catrobat/catroid/ui/recyclerview/viewmodel/MainFragmentViewModel;", "getViewModel", "()Lorg/catrobat/catroid/ui/recyclerview/viewmodel/MainFragmentViewModel;", "viewModel$delegate", "loadDownloadedProject", "", "name", "loadProjectImage", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCategoryTitleClicked", "categoryId", "onClick", Promotion.ACTION_VIEW, "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroyView", "onFeatureProjectClicked", "projectUrl", "onLoadFinished", FirebaseAnalytics.Param.SUCCESS, "", "onPause", "onProjectClick", "projectData", "Lorg/catrobat/catroid/common/ProjectData;", "onResume", "openWebView", "url", "refreshData", "setAndLoadCurrentProject", "myProjects", "", "setupCategoriesRV", "setupFeaturedProjectsRV", "setupProjectsRV", "setupViewVisibility", "updateRecyclerview", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MainMenuFragment extends Fragment implements ProjectListener, View.OnClickListener, FeaturedProjectCallback, CategoryTitleCallback, ProjectLoader.ProjectLoadListener {
    private static final int CURRENT_THUMBNAIL_SIZE = 500;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int MAX_PROJECTS_NUMBER = 10;
    private static final String TAG;
    private FragmentMainMenuBinding _binding;

    /* JADX INFO: renamed from: categoriesAdapter$delegate, reason: from kotlin metadata */
    private final Lazy categoriesAdapter;
    private String currentProject;

    /* JADX INFO: renamed from: featuredProjectsAdapter$delegate, reason: from kotlin metadata */
    private final Lazy featuredProjectsAdapter;
    private LinearLayout progressBar;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private HorizontalProjectsAdapter projectsAdapter;

    /* JADX INFO: renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    /* JADX WARN: Multi-variable type inference failed */
    public MainMenuFragment() {
        final MainMenuFragment mainMenuFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.viewModel = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<MainFragmentViewModel>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$special$$inlined$viewModel$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Type inference failed for: r0v1, types: [androidx.lifecycle.ViewModel, org.catrobat.catroid.ui.recyclerview.viewmodel.MainFragmentViewModel] */
            @Override // kotlin.jvm.functions.Function0
            public final MainFragmentViewModel invoke() {
                return ViewModelStoreOwnerExtKt.getViewModel(mainMenuFragment, Reflection.getOrCreateKotlinClass(MainFragmentViewModel.class), qualifier, objArr);
            }
        });
        final MainMenuFragment mainMenuFragment2 = this;
        LazyThreadSafetyMode lazyThreadSafetyMode2 = LazyThreadSafetyMode.NONE;
        final Object[] objArr2 = 0 == true ? 1 : 0;
        final Object[] objArr3 = 0 == true ? 1 : 0;
        this.featuredProjectsAdapter = LazyKt.lazy(lazyThreadSafetyMode2, (Function0) new Function0<FeaturedProjectsAdapter>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ui.recyclerview.adapter.FeaturedProjectsAdapter] */
            @Override // kotlin.jvm.functions.Function0
            public final FeaturedProjectsAdapter invoke() {
                ComponentCallbacks componentCallbacks = mainMenuFragment2;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(FeaturedProjectsAdapter.class), objArr2, objArr3);
            }
        });
        LazyThreadSafetyMode lazyThreadSafetyMode3 = LazyThreadSafetyMode.NONE;
        final Object[] objArr4 = 0 == true ? 1 : 0;
        final Object[] objArr5 = 0 == true ? 1 : 0;
        this.categoriesAdapter = LazyKt.lazy(lazyThreadSafetyMode3, (Function0) new Function0<CategoriesAdapter>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$special$$inlined$inject$default$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ui.recyclerview.adapter.CategoriesAdapter] */
            @Override // kotlin.jvm.functions.Function0
            public final CategoriesAdapter invoke() {
                ComponentCallbacks componentCallbacks = mainMenuFragment2;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(CategoriesAdapter.class), objArr4, objArr5);
            }
        });
        LazyThreadSafetyMode lazyThreadSafetyMode4 = LazyThreadSafetyMode.NONE;
        final Object[] objArr6 = 0 == true ? 1 : 0;
        final Object[] objArr7 = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode4, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$special$$inlined$inject$default$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = mainMenuFragment2;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), objArr6, objArr7);
            }
        });
    }

    public final String getCurrentProject() {
        return this.currentProject;
    }

    public final void setCurrentProject(String str) {
        this.currentProject = str;
    }

    private final MainFragmentViewModel getViewModel() {
        return (MainFragmentViewModel) this.viewModel.getValue();
    }

    private final FeaturedProjectsAdapter getFeaturedProjectsAdapter() {
        return (FeaturedProjectsAdapter) this.featuredProjectsAdapter.getValue();
    }

    private final CategoriesAdapter getCategoriesAdapter() {
        return (CategoriesAdapter) this.categoriesAdapter.getValue();
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    private final FragmentMainMenuBinding getBinding() {
        FragmentMainMenuBinding fragmentMainMenuBinding = this._binding;
        Intrinsics.checkNotNull(fragmentMainMenuBinding);
        return fragmentMainMenuBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        getViewModel().setIsLoading(true);
        this._binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            this.progressBar = (LinearLayout) requireActivity().findViewById(R.id.progress_bar);
            getViewModel().isLoading().observe(getViewLifecycleOwner(), new Observer() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    MainMenuFragment.onActivityCreated$lambda$0(this.f$0, (Boolean) obj);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        setupFeaturedProjectsRV();
        setupCategoriesRV();
        setupViewVisibility();
        MainMenuFragment mainMenuFragment = this;
        getBinding().editProject.setOnClickListener(mainMenuFragment);
        getBinding().newProjectFloatingActionButton.setOnClickListener(mainMenuFragment);
        getBinding().myProjectsTextView.setOnClickListener(mainMenuFragment);
        getBinding().projectImageView.setOnClickListener(mainMenuFragment);
        getBinding().playProject.setOnClickListener(mainMenuFragment);
        getBinding().featuredProjectsTextView.setOnClickListener(mainMenuFragment);
        getBinding().featuredProjectsTextView2.setOnClickListener(mainMenuFragment);
        ProjectDownloadUtil.INSTANCE.setFragment(this);
        setupProjectsRV();
        getViewModel().setIsLoading(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$0(MainMenuFragment this$0, Boolean bool) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LinearLayout linearLayout = this$0.progressBar;
        if (linearLayout != null) {
            Intrinsics.checkNotNull(bool);
            ExtensionsKt.setVisibleOrGone(linearLayout, bool.booleanValue());
        }
    }

    private final void setupCategoriesRV() {
        CategoriesAdapter categoriesAdapter = getCategoriesAdapter();
        categoriesAdapter.setOnProjectClickCallback(this);
        categoriesAdapter.setOnCategoryTitleClickCallback(this);
    }

    private final void setupViewVisibility() {
        getViewModel().connectionStatusAndFeaturedProjectsAndProjectCategoriesLiveData().observe(getViewLifecycleOwner(), new Observer() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$$ExternalSyntheticLambda2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainMenuFragment.setupViewVisibility$lambda$3(this.f$0, (Triple) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupViewVisibility$lambda$3(MainMenuFragment this$0, Triple triple) {
        List<FeaturedProject> list;
        List<ProjectCategoryWithResponses> list2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        boolean zBooleanValue = ((Boolean) triple.getFirst()).booleanValue();
        List<FeaturedProject> list3 = (List) triple.getSecond();
        List<ProjectCategoryWithResponses> list4 = (List) triple.getThird();
        if (zBooleanValue || !((list = list3) == null || list.isEmpty() || (list2 = list4) == null || list2.isEmpty())) {
            this$0.getFeaturedProjectsAdapter().setItems(list3);
            this$0.getCategoriesAdapter().setItems(list4);
        }
    }

    private final void setupProjectsRV() {
        this.projectsAdapter = new HorizontalProjectsAdapter(this);
        RecyclerView recyclerView = getBinding().myProjectsRecyclerView;
        recyclerView.setHasFixedSize(true);
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
        HorizontalProjectsAdapter horizontalProjectsAdapter = this.projectsAdapter;
        if (horizontalProjectsAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectsAdapter");
            horizontalProjectsAdapter = null;
        }
        recyclerView.setAdapter(horizontalProjectsAdapter);
        getViewModel().getProjects().observe(getViewLifecycleOwner(), new Observer() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainMenuFragment.setupProjectsRV$lambda$5(this.f$0, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupProjectsRV$lambda$5(MainMenuFragment this$0, List list) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNull(list);
        this$0.setAndLoadCurrentProject(list);
        this$0.updateRecyclerview(list);
    }

    private final void setupFeaturedProjectsRV() {
        getFeaturedProjectsAdapter().setCallback(this);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        ActionBar supportActionBar;
        super.onResume();
        FragmentActivity activity = getActivity();
        AppCompatActivity appCompatActivity = activity instanceof AppCompatActivity ? (AppCompatActivity) activity : null;
        if (appCompatActivity != null && (supportActionBar = appCompatActivity.getSupportActionBar()) != null) {
            supportActionBar.setTitle(R.string.app_name);
        }
        getViewModel().registerNetworkCallback();
        getViewModel().setIsLoading(false);
        getViewModel().update();
        String stringExtra = requireActivity().getIntent().getStringExtra("projectName");
        if (stringExtra != null) {
            requireActivity().getIntent().removeExtra("projectName");
            loadDownloadedProject(stringExtra);
        }
        refreshData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        getViewModel().unregisterNetworkCallback();
    }

    private final void setAndLoadCurrentProject(List<? extends ProjectData> myProjects) {
        String currentProjectName;
        if (!myProjects.isEmpty()) {
            currentProjectName = myProjects.get(0).getName();
        } else {
            currentProjectName = Utils.getCurrentProjectName(requireContext());
        }
        this.currentProject = currentProjectName;
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.currentProject)), this, null), 3, null);
        loadProjectImage();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/MainMenuFragment$setAndLoadCurrentProject$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$setAndLoadCurrentProject$1, reason: invalid class name */
    /* JADX INFO: compiled from: MainMenuFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment$setAndLoadCurrentProject$1", f = "MainMenuFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ File $projectDir;
        int label;
        final /* synthetic */ MainMenuFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(File file, MainMenuFragment mainMenuFragment, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$projectDir = file;
            this.this$0 = mainMenuFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass1(this.$projectDir, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            File file = this.$projectDir;
            Context contextRequireContext = this.this$0.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            ProjectLoaderKt.loadProject(file, contextRequireContext);
            return Unit.INSTANCE;
        }
    }

    private final void loadDownloadedProject(String name2) {
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(name2));
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, contextRequireContext).setListener(this), null, 1, null);
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        if (success) {
            Intent intent = new Intent(getActivity(), (Class<?>) ProjectActivity.class);
            intent.putExtra("fragmentPosition", 0);
            startActivity(intent);
        } else {
            getViewModel().setIsLoading(false);
            ToastUtil.showError(getActivity(), R.string.error_load_project);
        }
    }

    private final void loadProjectImage() {
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.currentProject));
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(500, 500);
        projectAndSceneScreenshotLoader.loadAndShowScreenshot(file.getName(), projectAndSceneScreenshotLoader.getScreenshotSceneName(file), false, getBinding().projectImageView);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void updateRecyclerview(List<? extends ProjectData> myProjects) {
        HorizontalProjectsAdapter horizontalProjectsAdapter = 0;
        if (myProjects.size() < 2) {
            HorizontalProjectsAdapter horizontalProjectsAdapter2 = this.projectsAdapter;
            if (horizontalProjectsAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("projectsAdapter");
                horizontalProjectsAdapter2 = null;
            }
            horizontalProjectsAdapter2.setItems(null);
            return;
        }
        int iCoerceAtMost = RangesKt.coerceAtMost(myProjects.size(), 10);
        HorizontalProjectsAdapter horizontalProjectsAdapter3 = this.projectsAdapter;
        if (horizontalProjectsAdapter3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectsAdapter");
        } else {
            horizontalProjectsAdapter = horizontalProjectsAdapter3;
        }
        horizontalProjectsAdapter.setItems(myProjects.subList(1, iCoerceAtMost));
    }

    @Override // org.catrobat.catroid.ui.recyclerview.ProjectListener
    public void onProjectClick(ProjectData projectData) {
        getViewModel().setIsLoading(true);
        File file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
        Intrinsics.checkNotNull(projectData);
        File file2 = new File(file, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(projectData.getName()));
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        ProjectLoader.loadProjectAsync$default(new ProjectLoader(file2, contextRequireContext).setListener(this), null, 1, null);
    }

    public final void refreshData() {
        getViewModel().forceUpdate();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        switch (view.getId()) {
            case R.id.editProject /* 2131364318 */:
            case R.id.image_view /* 2131364551 */:
            case R.id.projectImageView /* 2131365058 */:
                getViewModel().setIsLoading(true);
                File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.currentProject));
                Context contextRequireContext = requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, contextRequireContext).setListener(this), null, 1, null);
                break;
            case R.id.featuredProjectsTextView /* 2131364411 */:
                getViewModel().setIsLoading(true);
                startActivity(new Intent(getActivity(), (Class<?>) WebViewActivity.class));
                break;
            case R.id.featuredProjectsTextView2 /* 2131364412 */:
                getViewModel().setIsLoading(true);
                startActivity(new Intent(getActivity(), (Class<?>) CommunityWebViewActivity.class));
                break;
            case R.id.myProjectsTextView /* 2131364768 */:
                getViewModel().setIsLoading(true);
                startActivity(new Intent(getActivity(), (Class<?>) ProjectListActivity.class));
                break;
            case R.id.newProjectFloatingActionButton /* 2131364781 */:
                new NewProjectDialogFragment().show(getParentFragmentManager(), NewProjectDialogFragment.INSTANCE.getTAG());
                break;
            case R.id.playProject /* 2131364860 */:
                getViewModel().setIsLoading(true);
                StageActivity.handlePlayButton(getProjectManager(), getActivity());
                break;
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.FeaturedProjectCallback
    public void onFeatureProjectClicked(String projectUrl) {
        Intrinsics.checkNotNullParameter(projectUrl, "projectUrl");
        openWebView(projectUrl);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.CategoryTitleCallback
    public void onCategoryTitleClicked(String categoryId) {
        Intrinsics.checkNotNullParameter(categoryId, "categoryId");
        openWebView(FlavoredConstants.CATEGORY_URL + categoryId);
    }

    private final void openWebView(String url) {
        Intent intent = new Intent(getActivity(), (Class<?>) WebViewActivity.class);
        intent.putExtra("url", url);
        getViewModel().setIsLoading(true);
        startActivity(intent);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/MainMenuFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: MainMenuFragment.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/MainMenuFragment$Companion;", "", "()V", "CURRENT_THUMBNAIL_SIZE", "", "MAX_PROJECTS_NUMBER", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return MainMenuFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("MainMenuFragment", "getSimpleName(...)");
        TAG = "MainMenuFragment";
    }
}
