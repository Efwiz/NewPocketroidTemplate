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
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
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
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.databinding.FragmentMainMenuPcBinding;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.io.asynctask.ProjectLoaderKt;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.CommunityWebViewActivity;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.WebViewActivity;
import org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/MainMenuPcFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: MainMenuPcFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 $2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001$B\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J$\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010\u001e\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020\u00132\u0006\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u0013H\u0016J\u001a\u0010#\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00162\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f¨\u0006%"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/MainMenuPcFragment;", "Landroidx/fragment/app/Fragment;", "Landroid/view/View$OnClickListener;", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/FragmentMainMenuPcBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/FragmentMainMenuPcBinding;", "currentProject", "", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "loadCurrentProjectData", "", "onClick", Promotion.ACTION_VIEW, "Landroid/view/View;", "onCreateView", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onLoadFinished", FirebaseAnalytics.Param.SUCCESS, "", "onResume", "onViewCreated", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MainMenuPcFragment extends Fragment implements View.OnClickListener, ProjectLoader.ProjectLoadListener {
    private static final int CURRENT_THUMBNAIL_SIZE = 500;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private FragmentMainMenuPcBinding _binding;
    private String currentProject;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;

    /* JADX WARN: Multi-variable type inference failed */
    public MainMenuPcFragment() {
        final MainMenuPcFragment mainMenuPcFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuPcFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = mainMenuPcFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    private final FragmentMainMenuPcBinding getBinding() {
        FragmentMainMenuPcBinding fragmentMainMenuPcBinding = this._binding;
        Intrinsics.checkNotNull(fragmentMainMenuPcBinding);
        return fragmentMainMenuPcBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = FragmentMainMenuPcBinding.inflate(inflater, container, false);
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        MainMenuPcFragment mainMenuPcFragment = this;
        getBinding().editProject.setOnClickListener(mainMenuPcFragment);
        getBinding().projectImageView.setOnClickListener(mainMenuPcFragment);
        getBinding().playProject.setOnClickListener(mainMenuPcFragment);
        getBinding().btnWeb.setOnClickListener(mainMenuPcFragment);
        getBinding().btnCommunity.setOnClickListener(mainMenuPcFragment);
        getBinding().btnNewProjectFab.setOnClickListener(mainMenuPcFragment);
        if (savedInstanceState == null) {
            getChildFragmentManager().beginTransaction().replace(R.id.pc_project_list_container, new ProjectListFragment()).commit();
        }
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
        loadCurrentProjectData();
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }

    private final void loadCurrentProjectData() {
        this.currentProject = Utils.getCurrentProjectName(requireContext());
        File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.currentProject));
        BuildersKt__Builders_commonKt.launch$default(CoroutineScopeKt.CoroutineScope(Dispatchers.getIO()), null, null, new AnonymousClass1(file, this, null), 3, null);
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(500, 500);
        projectAndSceneScreenshotLoader.loadAndShowScreenshot(file.getName(), projectAndSceneScreenshotLoader.getScreenshotSceneName(file), false, getBinding().projectImageView);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/MainMenuPcFragment$loadCurrentProjectData$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.recyclerview.fragment.MainMenuPcFragment$loadCurrentProjectData$1, reason: invalid class name */
    /* JADX INFO: compiled from: MainMenuPcFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.MainMenuPcFragment$loadCurrentProjectData$1", f = "MainMenuPcFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ File $projectDir;
        int label;
        final /* synthetic */ MainMenuPcFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(File file, MainMenuPcFragment mainMenuPcFragment, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.$projectDir = file;
            this.this$0 = mainMenuPcFragment;
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

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        switch (view.getId()) {
            case R.id.btnCommunity /* 2131363923 */:
                startActivity(new Intent(getActivity(), (Class<?>) CommunityWebViewActivity.class));
                break;
            case R.id.btnNewProjectFab /* 2131363930 */:
                new NewProjectDialogFragment().show(getParentFragmentManager(), NewProjectDialogFragment.INSTANCE.getTAG());
                break;
            case R.id.btnWeb /* 2131363937 */:
                startActivity(new Intent(getActivity(), (Class<?>) WebViewActivity.class));
                break;
            case R.id.editProject /* 2131364318 */:
            case R.id.projectImageView /* 2131365058 */:
                File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem(this.currentProject));
                Context contextRequireContext = requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, contextRequireContext).setListener(this), null, 1, null);
                break;
            case R.id.playProject /* 2131364860 */:
                StageActivity.handlePlayButton(getProjectManager(), getActivity());
                break;
        }
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        if (success) {
            Intent intent = new Intent(getActivity(), (Class<?>) ProjectActivity.class);
            intent.putExtra("fragmentPosition", 0);
            startActivity(intent);
            return;
        }
        ToastUtil.showError(getActivity(), R.string.error_load_project);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/MainMenuPcFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: MainMenuPcFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/MainMenuPcFragment$Companion;", "", "()V", "CURRENT_THUMBNAIL_SIZE", "", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return MainMenuPcFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("MainMenuPcFragment", "getSimpleName(...)");
        TAG = "MainMenuPcFragment";
    }
}
