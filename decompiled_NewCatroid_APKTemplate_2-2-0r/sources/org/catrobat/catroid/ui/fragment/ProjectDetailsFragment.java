package org.catrobat.catroid.ui.fragment;

import android.content.ComponentCallbacks;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.cast.MediaTrack;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.noties.markwon.Markwon;
import io.noties.markwon.image.ImagesPlugin;
import io.noties.markwon.image.file.FileSchemeHandler;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.XmlHeader;
import org.catrobat.catroid.databinding.FragmentProjectShowDetailsBinding;
import org.catrobat.catroid.exceptions.LoadingProjectException;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectDetailsFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectDetailsFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 72\u00020\u0001:\u00017B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u001dH\u0002J$\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\b\u0010'\u001a\u00020\u001dH\u0016J\u0014\u0010(\u001a\u00020\u001d2\n\u0010)\u001a\u00060*j\u0002`+H\u0002J\u0010\u0010,\u001a\u00020\u001d2\u0006\u0010-\u001a\u00020.H\u0016J\u001a\u0010/\u001a\u00020\u001d2\u0006\u00100\u001a\u00020 2\b\u0010%\u001a\u0004\u0018\u00010&H\u0016J\u0010\u00101\u001a\u00020\t2\u0006\u00102\u001a\u00020\tH\u0002J\u0010\u00103\u001a\u00020\u001d2\b\u00104\u001a\u0004\u0018\u00010\tJ\u0012\u00105\u001a\u00020\u001d2\b\u00106\u001a\u0004\u0018\u00010\tH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u000bR\u0014\u0010\u001a\u001a\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u000b¨\u00068"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectDetailsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/FragmentProjectShowDetailsBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/FragmentProjectShowDetailsBinding;", "lastAccess", "", "getLastAccess", "()Ljava/lang/String;", "markwon", "Lio/noties/markwon/Markwon;", "project", "Lorg/catrobat/catroid/content/Project;", "projectData", "Lorg/catrobat/catroid/common/ProjectData;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "remixOf", "getRemixOf", "userHandle", "getUserHandle", "handleDescriptionPressed", "", "handleNotesAndCreditsPressed", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onExceptionThrown", "e", "Ljava/lang/Exception;", "Lkotlin/Exception;", "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onViewCreated", Promotion.ACTION_VIEW, "resolveImagePaths", "markdown", "setDescription", MediaTrack.ROLE_DESCRIPTION, "setNotesAndCredits", "notesAndCredits", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectDetailsFragment extends Fragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    public static final String SELECTED_PROJECT_KEY = "selectedProject";
    private static final String TAG;
    private FragmentProjectShowDetailsBinding _binding;
    private Markwon markwon;
    private Project project;
    private ProjectData projectData;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectDetailsFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectDetailsFragment.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectDetailsFragment$Companion;", "", "()V", "SELECTED_PROJECT_KEY", "", "TAG", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectDetailsFragment.TAG;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectDetailsFragment() {
        final ProjectDetailsFragment projectDetailsFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.fragment.ProjectDetailsFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = projectDetailsFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ProjectDetailsFragment", "getSimpleName(...)");
        TAG = "ProjectDetailsFragment";
    }

    private final FragmentProjectShowDetailsBinding getBinding() {
        FragmentProjectShowDetailsBinding fragmentProjectShowDetailsBinding = this._binding;
        Intrinsics.checkNotNull(fragmentProjectShowDetailsBinding);
        return fragmentProjectShowDetailsBinding;
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = FragmentProjectShowDetailsBinding.inflate(inflater, container, false);
        LinearLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        Markwon markwon = null;
        try {
            ProjectData projectData = (ProjectData) requireArguments().getSerializable(SELECTED_PROJECT_KEY);
            if (projectData == null) {
                return;
            }
            this.projectData = projectData;
            XstreamSerializer xstreamSerializer = XstreamSerializer.getInstance();
            ProjectData projectData2 = this.projectData;
            if (projectData2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("projectData");
                projectData2 = null;
            }
            Project projectLoadProject = xstreamSerializer.loadProject(projectData2.getDirectory(), requireContext());
            Intrinsics.checkNotNullExpressionValue(projectLoadProject, "loadProject(...)");
            this.project = projectLoadProject;
            ProjectManager projectManager = getProjectManager();
            Project project = this.project;
            if (project == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
                project = null;
            }
            projectManager.setCurrentProject(project);
        } catch (IOException e) {
            onExceptionThrown(e);
        } catch (LoadingProjectException e2) {
            onExceptionThrown(e2);
        }
        Markwon markwonBuild = Markwon.builder(requireContext()).usePlugin(ImagesPlugin.create(new ImagesPlugin.ImagesConfigure() { // from class: org.catrobat.catroid.ui.fragment.ProjectDetailsFragment$$ExternalSyntheticLambda0
            @Override // io.noties.markwon.image.ImagesPlugin.ImagesConfigure
            public final void configureImages(ImagesPlugin imagesPlugin) {
                ProjectDetailsFragment.onViewCreated$lambda$0(imagesPlugin);
            }
        })).build();
        Intrinsics.checkNotNullExpressionValue(markwonBuild, "build(...)");
        this.markwon = markwonBuild;
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(requireContext().getResources().getDimensionPixelSize(R.dimen.project_thumbnail_width), requireContext().getResources().getDimensionPixelSize(R.dimen.project_thumbnail_height));
        Project project2 = this.project;
        if (project2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project2 = null;
        }
        XmlHeader xmlHeader = project2.getXmlHeader();
        ProjectData projectData3 = this.projectData;
        if (projectData3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectData");
            projectData3 = null;
        }
        String name2 = projectData3.getName();
        Project project3 = this.project;
        if (project3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project3 = null;
        }
        projectAndSceneScreenshotLoader.loadAndShowScreenshot(name2, projectAndSceneScreenshotLoader.getScreenshotSceneName(project3.getDirectory()), false, getBinding().image);
        File file = FlavoredConstants.DEFAULT_ROOT_DIRECTORY;
        ProjectData projectData4 = this.projectData;
        if (projectData4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectData");
            projectData4 = null;
        }
        String sizeAsString = FileMetaDataExtractor.getSizeAsString(new File(file, projectData4.getName()), requireContext());
        int i = xmlHeader.islandscapeMode() ? R.string.landscape : R.string.portrait;
        String str = xmlHeader.getVirtualScreenWidth() + "x" + xmlHeader.getVirtualScreenHeight();
        FragmentProjectShowDetailsBinding binding = getBinding();
        TextView textView = binding.f572name;
        ProjectData projectData5 = this.projectData;
        if (projectData5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectData");
            projectData5 = null;
        }
        textView.setText(projectData5.getName());
        binding.authorValue.setText(getUserHandle());
        binding.sizeValue.setText(sizeAsString);
        binding.lastAccessValue.setText(getLastAccess());
        binding.screenSizeValue.setText(str);
        binding.modeValue.setText(getString(i));
        binding.remixOfValue.setText(getRemixOf());
        TextView textView2 = binding.descriptionValue;
        String description = xmlHeader.getDescription();
        String str2 = "";
        if (description == null) {
            description = "";
        } else {
            Intrinsics.checkNotNull(description);
        }
        Markwon markwon2 = this.markwon;
        if (markwon2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("markwon");
            markwon2 = null;
        }
        markwon2.setMarkdown(textView2, resolveImagePaths(description));
        TextView textView3 = binding.notesAndCreditsValue;
        String notesAndCredits = xmlHeader.getNotesAndCredits();
        if (notesAndCredits != null) {
            Intrinsics.checkNotNull(notesAndCredits);
            str2 = notesAndCredits;
        }
        Markwon markwon3 = this.markwon;
        if (markwon3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("markwon");
        } else {
            markwon = markwon3;
        }
        markwon.setMarkdown(textView3, resolveImagePaths(str2));
        BottomBar.hideBottomBar(requireActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onViewCreated$lambda$0(ImagesPlugin plugin) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        plugin.addSchemeHandler(FileSchemeHandler.create());
    }

    private final String resolveImagePaths(String markdown) {
        Project project = this.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        final File filesDir = project.getFilesDir();
        return new Regex("!\\[(.*?)]\\((?!file://)(.*?)\\)").replace(markdown, new Function1<MatchResult, CharSequence>() { // from class: org.catrobat.catroid.ui.fragment.ProjectDetailsFragment.resolveImagePaths.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(MatchResult matchResult) {
                Intrinsics.checkNotNullParameter(matchResult, "matchResult");
                String str = matchResult.getGroupValues().get(1);
                File file = new File(filesDir, matchResult.getGroupValues().get(2));
                if (file.exists()) {
                    return "![" + str + "](" + file.toURI() + Constant.AFTER_QUTO;
                }
                return matchResult.getValue();
            }
        });
    }

    private final void onExceptionThrown(Exception e) {
        ToastUtil.showError(requireContext(), R.string.error_load_project);
        Log.e(TAG, Log.getStackTraceString(e));
        requireActivity().onBackPressed();
    }

    private final void handleDescriptionPressed() {
        TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
        TextInputDialog.Builder hint = builder.setHint(getString(R.string.description));
        Project project = this.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        hint.setText(project.getDescription()).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectDetailsFragment$$ExternalSyntheticLambda1
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                ProjectDetailsFragment.handleDescriptionPressed$lambda$4(this.f$0, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.set_description).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDescriptionPressed$lambda$4(ProjectDetailsFragment this$0, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setDescription(str);
    }

    private final void handleNotesAndCreditsPressed() {
        TextInputDialog.Builder builder = new TextInputDialog.Builder(requireContext());
        TextInputDialog.Builder hint = builder.setHint(getString(R.string.notes_and_credits_title));
        Project project = this.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        hint.setText(project.getNotesAndCredits()).setPositiveButton(getString(R.string.ok), new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectDetailsFragment$$ExternalSyntheticLambda2
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                ProjectDetailsFragment.handleNotesAndCreditsPressed$lambda$5(this.f$0, dialogInterface, str);
            }
        });
        builder.setTitle(R.string.set_notes_and_credits).setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleNotesAndCreditsPressed$lambda$5(ProjectDetailsFragment this$0, DialogInterface dialogInterface, String str) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.setNotesAndCredits(str);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        int size = menu.size();
        for (int i = 0; i < size; i++) {
            menu.getItem(i).setVisible(false);
        }
    }

    private final String getLastAccess() {
        ProjectData projectData = this.projectData;
        if (projectData == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectData");
            projectData = null;
        }
        Date date = new Date(projectData.getLastUsed());
        if (DateUtils.isToday(date.getTime())) {
            return (getString(R.string.details_date_today) + ": ") + DateFormat.getTimeFormat(requireContext()).format(date);
        }
        String str = DateFormat.getDateFormat(requireContext()).format(date);
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    private final String getUserHandle() {
        Project project = this.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        String userHandle = project.getXmlHeader().getUserHandle();
        if (userHandle != null && !Intrinsics.areEqual(userHandle, "")) {
            return userHandle;
        }
        String string = getString(R.string.unknown);
        Intrinsics.checkNotNull(string);
        return string;
    }

    private final String getRemixOf() {
        Project project = this.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        String remixParentsUrlString = project.getXmlHeader().getRemixParentsUrlString();
        if (remixParentsUrlString != null && !Intrinsics.areEqual(remixParentsUrlString, "")) {
            return remixParentsUrlString;
        }
        String string = getString(R.string.nxt_no_sensor);
        Intrinsics.checkNotNull(string);
        return string;
    }

    public final void setDescription(String description) {
        Project project = this.project;
        Markwon markwon = null;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        project.setDescription(description);
        XstreamSerializer xstreamSerializer = XstreamSerializer.getInstance();
        Project project2 = this.project;
        if (project2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project2 = null;
        }
        if (xstreamSerializer.saveProject(project2)) {
            Markwon markwon2 = this.markwon;
            if (markwon2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("markwon");
            } else {
                markwon = markwon2;
            }
            TextView textView = getBinding().descriptionValue;
            if (description == null) {
                description = "";
            }
            markwon.setMarkdown(textView, resolveImagePaths(description));
            return;
        }
        ToastUtil.showError(requireContext(), R.string.error_set_description);
    }

    private final void setNotesAndCredits(String notesAndCredits) {
        Project project = this.project;
        Markwon markwon = null;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        project.setNotesAndCredits(notesAndCredits);
        XstreamSerializer xstreamSerializer = XstreamSerializer.getInstance();
        Project project2 = this.project;
        if (project2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project2 = null;
        }
        if (xstreamSerializer.saveProject(project2)) {
            Markwon markwon2 = this.markwon;
            if (markwon2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("markwon");
            } else {
                markwon = markwon2;
            }
            TextView textView = getBinding().notesAndCreditsValue;
            if (notesAndCredits == null) {
                notesAndCredits = "";
            }
            markwon.setMarkdown(textView, resolveImagePaths(notesAndCredits));
            return;
        }
        ToastUtil.showError(requireContext(), R.string.error_set_notes_and_credits);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        this._binding = null;
    }
}
