package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.ClipData;
import android.content.ComponentCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.huawei.hms.support.api.entity.core.CommonCode;
import io.noties.markwon.Markwon;
import io.noties.markwon.image.ImagesPlugin;
import io.noties.markwon.image.file.FileSchemeHandler;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.MatchResult;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.ProjectData;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.backwardcompatibility.ProjectMetaDataParser;
import org.catrobat.catroid.exceptions.LoadingProjectException;
import org.catrobat.catroid.io.StorageOperations;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.io.asynctask.ImportResult;
import org.catrobat.catroid.io.asynctask.ProjectCopier;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.io.asynctask.ProjectRenamer;
import org.catrobat.catroid.io.asynctask.ProjectUnZipperAndImporter;
import org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.ProjectActivity;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.catrobat.catroid.ui.UiUtils;
import org.catrobat.catroid.ui.filepicker.FilePickerActivity;
import org.catrobat.catroid.ui.fragment.ProjectFilesFragment;
import org.catrobat.catroid.ui.fragment.ProjectLibsFragment;
import org.catrobat.catroid.ui.fragment.ProjectOptionsFragment;
import org.catrobat.catroid.ui.recyclerview.adapter.ProjectAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.catrobat.catroid.utils.ProjectTrashManager;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectListFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u0000 q2\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00012\u00020\u0003:\u0002qrB\u0005¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0019\u001a\u00020\u001aJ\u0018\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002J\u001a\u0010 \u001a\u00020\u001a2\u0010\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\"H\u0014J\u001a\u0010#\u001a\u00020\u001a2\u0010\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\"H\u0014J\b\u0010$\u001a\u00020\u001aH\u0002J(\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020'2\u0016\u0010(\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u0006j\b\u0012\u0004\u0012\u00020\u001d`\bH\u0002J\b\u0010)\u001a\u00020*H\u0015J\b\u0010+\u001a\u00020*H\u0014J\b\u0010,\u001a\u00020*H\u0014J\u0012\u0010-\u001a\u00020\u001a2\b\u0010&\u001a\u0004\u0018\u00010'H\u0002J \u0010.\u001a\u00020\u001a2\u0016\u0010(\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u0006j\b\u0012\u0004\u0012\u00020\u001d`\bH\u0002J\b\u0010/\u001a\u00020\u001aH\u0002J\b\u00100\u001a\u00020\u001aH\u0002J\b\u00101\u001a\u00020\u001aH\u0014J\b\u00102\u001a\u00020\nH\u0014J\u0010\u00103\u001a\u00020\n2\u0006\u00104\u001a\u00020\u0007H\u0002J\u0018\u00105\u001a\u00020\u001a2\u0006\u00106\u001a\u0002072\u0006\u00104\u001a\u00020\u0007H\u0002J\b\u00108\u001a\u00020\u001aH\u0002J\u0012\u00109\u001a\u00020\u001a2\b\u0010:\u001a\u0004\u0018\u00010;H\u0016J\"\u0010<\u001a\u00020\u001a2\u0006\u0010=\u001a\u00020*2\u0006\u0010>\u001a\u00020*2\b\u0010&\u001a\u0004\u0018\u00010'H\u0016J\u0010\u0010?\u001a\u00020\u001a2\u0006\u0010@\u001a\u00020\nH\u0002J\u0018\u0010A\u001a\u00020\u001a2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0016J\b\u0010F\u001a\u00020\u001aH\u0016J\b\u0010G\u001a\u00020\u001aH\u0002J\u0010\u0010H\u001a\u00020\u001a2\u0006\u0010I\u001a\u00020JH\u0002J\u001c\u0010K\u001a\u00020\u001a2\b\u0010L\u001a\u0004\u0018\u00010\u00022\b\u0010M\u001a\u0004\u0018\u00010NH\u0016J\u001c\u0010O\u001a\u00020\u001a2\b\u0010L\u001a\u0004\u0018\u00010\u00022\b\u0010P\u001a\u0004\u0018\u00010QH\u0016J\u0010\u0010R\u001a\u00020\u001a2\u0006\u0010@\u001a\u00020\nH\u0016J\u0010\u0010S\u001a\u00020\n2\u0006\u0010L\u001a\u00020TH\u0016J\u0010\u0010U\u001a\u00020\u001a2\u0006\u0010B\u001a\u00020CH\u0016J\u0010\u0010V\u001a\u00020\u001a2\u0006\u0010@\u001a\u00020\nH\u0002J\b\u0010W\u001a\u00020\u001aH\u0016J\u001c\u0010X\u001a\u00020\u001a2\b\u0010L\u001a\u0004\u0018\u00010\u00022\b\u0010Y\u001a\u0004\u0018\u00010ZH\u0016J\u001a\u0010[\u001a\u00020\u001a2\u0010\u0010!\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\"H\u0014J\u0010\u0010\\\u001a\u00020\u001a2\u0006\u0010]\u001a\u00020*H\u0014J \u0010^\u001a\u00020\u001a2\u0016\u0010_\u001a\u0012\u0012\u0004\u0012\u00020\u001d0\u0006j\b\u0012\u0004\u0012\u00020\u001d`\bH\u0002J\u001c\u0010`\u001a\u00020\u001a2\b\u0010L\u001a\u0004\u0018\u00010\u00022\b\u0010a\u001a\u0004\u0018\u00010\u001fH\u0014J\u0010\u0010b\u001a\u00020\u001a2\u0006\u0010c\u001a\u00020\nH\u0002J\b\u0010d\u001a\u00020\u001aH\u0002J\b\u0010e\u001a\u00020\u001aH\u0002J\u0018\u0010f\u001a\u00020\u001a2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020\u001fH\u0002J\u0012\u0010j\u001a\u00020\u001a2\b\u0010L\u001a\u0004\u0018\u00010\u0002H\u0002J\u0012\u0010k\u001a\u00020\u001a2\b\u0010L\u001a\u0004\u0018\u00010\u0002H\u0002J\u0012\u0010l\u001a\u00020\u001a2\b\u0010L\u001a\u0004\u0018\u00010\u0002H\u0002J\u0010\u0010m\u001a\u00020\u001a2\u0006\u0010n\u001a\u00020\u001fH\u0002J\b\u0010c\u001a\u00020\u001aH\u0002J\b\u0010o\u001a\u00020\u001aH\u0014J\b\u0010p\u001a\u00020\u001aH\u0002R\"\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00020\u0007\u0018\u0001`\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0010¨\u0006s"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment;", "Lorg/catrobat/catroid/ui/recyclerview/fragment/RecyclerViewFragment;", "Lorg/catrobat/catroid/common/ProjectData;", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "()V", "filesForUnzipAndImportTask", "Ljava/util/ArrayList;", "Ljava/io/File;", "Lkotlin/collections/ArrayList;", "hasUnzipAndImportTaskFinished", "", "importDialog", "Landroidx/appcompat/app/AlertDialog;", "itemList", "", "getItemList", "()Ljava/util/List;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "sortedItemList", "getSortedItemList", "checkForEmptyList", "", "copyFileContentToCacheFile", "uri", "Landroid/net/Uri;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "copyItems", "selectedItems", "", "deleteItems", "dismissImportProgressDialog", "extractAllUris", "data", "Landroid/content/Intent;", "uris", "getDeleteAlertTitleId", "", "getRenameDialogHint", "getRenameDialogTitle", "importProject", "importProjectUris", "importUsingFilePickerActivity", "importUsingSystemFilePicker", "initializeAdapter", "isBackpackEmpty", "isValidNewStructure", ProjectUploadActivityKt.PROJECT_DIR, "launchBakedProject", "context", "Landroid/content/Context;", "libsMenu", "onActivityCreated", "savedInstance", "Landroid/os/Bundle;", "onActivityResult", "requestCode", "resultCode", "onCopyProjectComplete", FirebaseAnalytics.Param.SUCCESS, "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onDestroy", "onImportError", "onImportProjectFinished", "result", "Lorg/catrobat/catroid/io/asynctask/ImportResult;", "onItemClick", "item", "selectionManager", "Lorg/catrobat/catroid/ui/recyclerview/adapter/multiselection/MultiSelectionManager;", "onItemLongClick", "holder", "Lorg/catrobat/catroid/ui/recyclerview/viewholder/CheckableViewHolder;", "onLoadFinished", "onOptionsItemSelected", "Landroid/view/MenuItem;", "onPrepareOptionsMenu", "onRenameFinished", "onResume", "onSettingsClick", Promotion.ACTION_VIEW, "Landroid/view/View;", "packItems", "prepareActionMode", ModuleXmlParser.TYPE, "prepareFilesForImport", "urisToImport", "renameItem", "name", "setAdapterItems", "sortProjects", "showImportChooser", "showImportProgressDialog", "showMarkdownDialog", "project", "Lorg/catrobat/catroid/content/Project;", "markdownText", "showProjectFilesFragment", "showProjectLibsFragment", "showProjectOptionsFragment", "showReadmeForProject", "projectName", "switchToBackpack", "updateAdapterAsync", "Companion", "ProjectImportFinishedListener", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectListFragment extends RecyclerViewFragment<ProjectData> implements ProjectLoader.ProjectLoadListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int PERMISSIONS_REQUEST_IMPORT_FROM_EXTERNAL_STORAGE = 801;
    private static final int REQUEST_IMPORT_PROJECT = 7;
    private static final String TAG;
    private static final ConcurrentHashMap<String, ProjectData> projectDataCache;
    private ArrayList<File> filesForUnzipAndImportTask;
    private boolean hasUnzipAndImportTaskFinished;
    private AlertDialog importDialog;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$ProjectImportFinishedListener.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectListFragment.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$ProjectImportFinishedListener;", "", "notifyActivityFinished", "", FirebaseAnalytics.Param.SUCCESS, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ProjectImportFinishedListener {
        void notifyActivityFinished(boolean success);
    }

    @JvmStatic
    public static final void clearCache() {
        INSTANCE.clearCache();
    }

    @JvmStatic
    public static final void getLocalProjectList(List<ProjectData> list) {
        INSTANCE.getLocalProjectList(list);
    }

    public static final String getTAG() {
        return INSTANCE.getTAG();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getDeleteAlertTitleId() {
        return R.plurals.delete_projects;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogHint() {
        return R.string.project_name_label;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected int getRenameDialogTitle() {
        return R.string.rename_project;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected boolean isBackpackEmpty() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectListFragment() {
        final ProjectListFragment projectListFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = projectListFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        this.filesForUnzipAndImportTask = new ArrayList<>();
        this.hasUnzipAndImportTaskFinished = true;
        if (getArguments() != null) {
            importProject((Intent) requireArguments().getParcelable(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK));
        }
        Intent intent = requireActivity().getIntent();
        if (intent == null || !intent.hasExtra("merge")) {
            return;
        }
        this.adapter.showSettings = false;
        this.actionModeType = 6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onImportProjectFinished(ImportResult result) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        dismissImportProgressDialog();
        ArrayList<File> arrayList = this.filesForUnzipAndImportTask;
        if (arrayList != null) {
            arrayList.clear();
        }
        setShowProgressBar(false);
        if (result instanceof ImportResult.Success) {
            INSTANCE.clearCache();
            setAdapterItems(this.adapter.projectsSorted);
        } else if (result instanceof ImportResult.Failure) {
            ToastUtil.showError(context, R.string.error_import_project);
        } else if (result instanceof ImportResult.BakedProject) {
            ToastUtil.showSuccess(context, "Запуск запеченного проекта...");
            launchBakedProject(context, ((ImportResult.BakedProject) result).getProjectDir());
        }
    }

    private final void launchBakedProject(Context context, File projectDir) {
        Intent intent = new Intent(context, (Class<?>) StageActivity.class);
        intent.putExtra(StageActivity.EXTRA_PROJECT_PATH, projectDir.getAbsolutePath());
        intent.putExtra("IS_BAKED_LAUNCH", true);
        startActivity(intent);
    }

    private final void showImportProgressDialog() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        int i = (int) (24 * linearLayout.getResources().getDisplayMetrics().density);
        linearLayout.setPadding(i, i, i, i);
        linearLayout.setGravity(16);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setIndeterminate(true);
        TextView textView = new TextView(context);
        textView.setText(getString(R.string.importing_project_dialog_msg));
        textView.setPadding((int) (16 * textView.getResources().getDisplayMetrics().density), 0, 0, 0);
        textView.setTextSize(16.0f);
        textView.setTextColor(-1);
        linearLayout.addView(progressBar);
        linearLayout.addView(textView);
        AlertDialog alertDialogCreate = new AlertDialog.Builder(context).setView(linearLayout).setCancelable(false).create();
        this.importDialog = alertDialogCreate;
        if (alertDialogCreate != null) {
            alertDialogCreate.show();
        }
    }

    private final void dismissImportProgressDialog() {
        AlertDialog alertDialog;
        try {
            AlertDialog alertDialog2 = this.importDialog;
            if (alertDialog2 != null) {
                Intrinsics.checkNotNull(alertDialog2);
                if (alertDialog2.isShowing() && (alertDialog = this.importDialog) != null) {
                    alertDialog.dismiss();
                }
            }
        } catch (Exception unused) {
        }
        this.importDialog = null;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        dismissImportProgressDialog();
        super.onDestroy();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$showReadmeForProject$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$showReadmeForProject$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectListFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$showReadmeForProject$1", f = "ProjectListFragment.kt", i = {}, l = {223}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42001 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $projectName;
        int label;
        final /* synthetic */ ProjectListFragment this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C42001(String str, ProjectListFragment projectListFragment, Continuation<? super C42001> continuation) {
            super(2, continuation);
            this.$projectName = str;
            this.this$0 = projectListFragment;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C42001(this.$projectName, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42001) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    File file = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, this.$projectName);
                    if (!file.exists()) {
                        return Unit.INSTANCE;
                    }
                    Project projectLoadProject = XstreamSerializer.getInstance().loadProject(file, this.this$0.requireContext());
                    String description = projectLoadProject != null ? projectLoadProject.getDescription() : null;
                    String str = description;
                    if (str != null && !StringsKt.isBlank(str)) {
                        this.label = 1;
                        if (BuildersKt.withContext(Dispatchers.getMain(), new C02671(this.this$0, projectLoadProject, description, null), this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
            } catch (Exception e) {
                Log.e(ProjectListFragment.INSTANCE.getTAG(), "Failed to load project for Readme display", e);
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$showReadmeForProject$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$showReadmeForProject$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: ProjectListFragment.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$showReadmeForProject$1$1", f = "ProjectListFragment.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02671 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $description;
            final /* synthetic */ Project $project;
            int label;
            final /* synthetic */ ProjectListFragment this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02671(ProjectListFragment projectListFragment, Project project, String str, Continuation<? super C02671> continuation) {
                super(2, continuation);
                this.this$0 = projectListFragment;
                this.$project = project;
                this.$description = str;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02671(this.this$0, this.$project, this.$description, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02671) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                ProjectListFragment projectListFragment = this.this$0;
                Project project = this.$project;
                Intrinsics.checkNotNullExpressionValue(project, "$project");
                projectListFragment.showMarkdownDialog(project, this.$description);
                return Unit.INSTANCE;
            }
        }
    }

    private final void showReadmeForProject(String projectName) {
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C42001(projectName, this, null), 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showMarkdownDialog(Project project, String markdownText) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        FragmentActivity fragmentActivity = activity;
        TextView textView = new TextView(fragmentActivity);
        int i = (int) (16 * textView.getResources().getDisplayMetrics().density);
        textView.setPadding(i, i, i, i);
        Markwon markwonBuild = Markwon.builder(fragmentActivity).usePlugin(ImagesPlugin.create(new ImagesPlugin.ImagesConfigure() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$$ExternalSyntheticLambda1
            @Override // io.noties.markwon.image.ImagesPlugin.ImagesConfigure
            public final void configureImages(ImagesPlugin imagesPlugin) {
                ProjectListFragment.showMarkdownDialog$lambda$4(imagesPlugin);
            }
        })).build();
        Intrinsics.checkNotNullExpressionValue(markwonBuild, "build(...)");
        markwonBuild.setMarkdown(textView, showMarkdownDialog$resolveImagePaths(project, markdownText));
        new AlertDialog.Builder(new ContextThemeWrapper(fragmentActivity, 2131952355)).setTitle("Сведения о проекте: " + project.getName()).setView(textView).setPositiveButton(R.string.ok, (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showMarkdownDialog$lambda$4(ImagesPlugin plugin) {
        Intrinsics.checkNotNullParameter(plugin, "plugin");
        plugin.addSchemeHandler(FileSchemeHandler.create());
    }

    private static final String showMarkdownDialog$resolveImagePaths(final Project project, String str) {
        project.getDirectory();
        return new Regex("!\\[(.*?)]\\((?!file://)(.*?)\\)").replace(str, new Function1<MatchResult, CharSequence>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$showMarkdownDialog$resolveImagePaths$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(MatchResult matchResult) {
                Intrinsics.checkNotNullParameter(matchResult, "matchResult");
                String str2 = matchResult.getGroupValues().get(1);
                File file = project.getFile(matchResult.getGroupValues().get(2));
                if (file != null && file.exists()) {
                    return "![" + str2 + "](" + file.toURI() + Constant.AFTER_QUTO;
                }
                return matchResult.getValue();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onRenameFinished(boolean success) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        if (success) {
            if (this.hasUnzipAndImportTaskFinished) {
                ToastUtil.showSuccess(context, context.getString(R.string.renamed_project));
                ArrayList<File> arrayList = this.filesForUnzipAndImportTask;
                if (arrayList != null) {
                    arrayList.clear();
                }
            }
            setAdapterItems(this.adapter.projectsSorted);
        } else {
            ToastUtil.showError(context, R.string.error_rename_incompatible_project);
        }
        setShowProgressBar(false);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onResume() {
        if (this.actionModeType != 6) {
            getProjectManager().setCurrentProject(null);
        }
        updateAdapterAsync();
        BottomBar.showBottomBar(requireActivity());
        super.onResume();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void initializeAdapter() {
        this.sharedPreferenceDetailsKey = SharedPreferenceKeys.SHOW_DETAILS_PROJECTS_PREFERENCE_KEY;
        this.adapter = new ProjectAdapter(new ArrayList());
        onAdapterReady();
    }

    private final List<ProjectData> getItemList() {
        ArrayList arrayList = new ArrayList();
        INSTANCE.getLocalProjectList(arrayList);
        CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ProjectListFragment._get_itemList_$lambda$5((ProjectData) obj, (ProjectData) obj2);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int _get_itemList_$lambda$5(ProjectData project1, ProjectData project2) {
        Intrinsics.checkNotNullParameter(project1, "project1");
        Intrinsics.checkNotNullParameter(project2, "project2");
        return Intrinsics.compare(project2.getLastUsed(), project1.getLastUsed());
    }

    private final List<ProjectData> getSortedItemList() {
        ArrayList arrayList = new ArrayList();
        INSTANCE.getLocalProjectList(arrayList);
        CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$$ExternalSyntheticLambda6
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ProjectListFragment._get_sortedItemList_$lambda$6((ProjectData) obj, (ProjectData) obj2);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int _get_sortedItemList_$lambda$6(ProjectData project1, ProjectData project2) {
        Intrinsics.checkNotNullParameter(project1, "project1");
        Intrinsics.checkNotNullParameter(project2, "project2");
        String name2 = project1.getName();
        String name3 = project2.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        return name2.compareTo(name3);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        switch (item.getItemId()) {
            case R.id.import_project /* 2131364556 */:
                showImportChooser();
                return true;
            case R.id.libs_menu /* 2131364619 */:
                libsMenu();
                return true;
            case R.id.menu_trash_bin /* 2131364661 */:
                Context contextRequireContext = requireContext();
                Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
                ProjectTrashManager.showTrashBinDialog(contextRequireContext, new Runnable() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        ProjectListFragment.onOptionsItemSelected$lambda$7(this.f$0);
                    }
                });
                return true;
            case R.id.sort_projects /* 2131365247 */:
                sortProjects();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onOptionsItemSelected$lambda$7(ProjectListFragment this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.updateAdapterAsync();
    }

    private final void libsMenu() {
        startActivity(new Intent(getContext(), (Class<?>) LibraryEditorActivity.class));
    }

    private final void sortProjects() {
        this.adapter.projectsSorted = !this.adapter.projectsSorted;
        PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().putBoolean(SharedPreferenceKeys.SORT_PROJECTS_PREFERENCE_KEY, this.adapter.projectsSorted).apply();
        updateAdapterAsync();
    }

    private final void showImportChooser() {
        setShowProgressBar(true);
        if (Build.VERSION.SDK_INT >= 29) {
            importUsingSystemFilePicker();
        } else {
            importUsingFilePickerActivity();
        }
    }

    private final void importUsingSystemFilePicker() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
        intent.putExtra("android.provider.extra.INITIAL_URI", Environment.DIRECTORY_DOWNLOADS);
        String string = requireContext().getResources().getString(R.string.import_project);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        startActivityForResult(Intent.createChooser(intent, string), 7);
    }

    private final void importUsingFilePickerActivity() {
        new RequiresPermissionTask(CollectionsKt.listOf("android.permission.READ_EXTERNAL_STORAGE")) { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment.importUsingFilePickerActivity.1
            @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
            public void task() {
                ProjectListFragment.this.startActivityForResult(new Intent(ProjectListFragment.this.requireContext(), (Class<?>) FilePickerActivity.class), 7);
            }
        }.execute(requireActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7 && resultCode == -1) {
            importProject(data);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void importProject(Intent data) {
        if (data == null) {
            onImportError();
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (data.getData() == null && !data.hasExtra("android.intent.extra.STREAM") && data.getClipData() == null) {
            onImportError();
            return;
        }
        if (data.hasExtra("android.intent.extra.STREAM")) {
            Bundle extras = data.getExtras();
            Object obj = extras != null ? extras.get("android.intent.extra.STREAM") : null;
            if (obj instanceof ArrayList) {
                arrayList = (ArrayList) obj;
            } else if (obj instanceof Uri) {
                arrayList.add(obj);
            }
        } else {
            extractAllUris(data, arrayList);
        }
        try {
            importProjectUris(arrayList);
        } catch (IOException e) {
            Log.e(TAG, "Cannot resolve project to import.", e);
        }
    }

    private final void onImportError() {
        Context context = getContext();
        if (context == null) {
            return;
        }
        dismissImportProgressDialog();
        setShowProgressBar(false);
        ToastUtil.showError(context, R.string.error_import_project);
    }

    private final void extractAllUris(Intent data, ArrayList<Uri> uris) {
        Uri data2 = data.getData();
        if (data2 != null) {
            uris.add(data2);
            return;
        }
        ClipData clipData = data.getClipData();
        if (clipData != null) {
            int itemCount = clipData.getItemCount();
            for (int i = 0; i < itemCount; i++) {
                uris.add(clipData.getItemAt(i).getUri());
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void importProjectUris(ArrayList<Uri> uris) throws IOException {
        showImportProgressDialog();
        prepareFilesForImport(uris);
        ArrayList<File> arrayList = this.filesForUnzipAndImportTask;
        if (arrayList != null) {
            ArrayList<File> arrayList2 = arrayList;
            if (!arrayList2.isEmpty()) {
                new ProjectUnZipperAndImporter(new Function1<ImportResult, Unit>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$importProjectUris$1$1
                    {
                        super(1);
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(ImportResult result) {
                        Intrinsics.checkNotNullParameter(result, "result");
                        this.this$0.onImportProjectFinished(result);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(ImportResult importResult) {
                        invoke2(importResult);
                        return Unit.INSTANCE;
                    }
                }, null, 2, 0 == true ? 1 : 0).unZipAndImportAsync((File[]) arrayList2.toArray(new File[0]));
                return;
            }
            dismissImportProgressDialog();
        }
    }

    private final void prepareFilesForImport(ArrayList<Uri> urisToImport) throws IOException {
        for (Uri uri : urisToImport) {
            ContentResolver contentResolver = requireActivity().getContentResolver();
            String strResolveFileName = StorageOperations.resolveFileName(contentResolver, uri);
            File fileCopyUriToDir = StorageOperations.copyUriToDir(contentResolver, uri, Constants.CACHE_DIRECTORY, strResolveFileName);
            Intrinsics.checkNotNull(strResolveFileName);
            if (StringsKt.endsWith$default(strResolveFileName, Constants.CATROBAT_EXTENSION, false, 2, (Object) null)) {
                ArrayList<File> arrayList = this.filesForUnzipAndImportTask;
                if (arrayList != null) {
                    arrayList.add(fileCopyUriToDir);
                }
            } else if (StringsKt.endsWith$default(strResolveFileName, Constants.NEW_CATROBAT_EXTENSION, false, 2, (Object) null)) {
                ArrayList<File> arrayList2 = this.filesForUnzipAndImportTask;
                if (arrayList2 != null) {
                    arrayList2.add(fileCopyUriToDir);
                }
            } else {
                try {
                    ArrayList<File> arrayList3 = this.filesForUnzipAndImportTask;
                    if (arrayList3 != null) {
                        arrayList3.add(fileCopyUriToDir);
                    }
                } catch (Exception unused) {
                    ToastUtil.showError(requireContext(), R.string.only_select_catrobat_files);
                }
            }
            this.hasUnzipAndImportTaskFinished = false;
        }
    }

    private final boolean isValidNewStructure(File projectDir) {
        return new File(projectDir, Constants.CODE_XML_FILE_NAME).exists() && new File(projectDir, "scenes").isDirectory() && new File(projectDir, "files").isDirectory();
    }

    private final void copyFileContentToCacheFile(Uri uri, String fileName) throws IOException {
        File fileCopyUriToDir = StorageOperations.copyUriToDir(requireActivity().getContentResolver(), uri, Constants.CACHE_DIRECTORY, fileName);
        ArrayList<File> arrayList = this.filesForUnzipAndImportTask;
        if (arrayList != null) {
            arrayList.add(fileCopyUriToDir);
        }
        this.hasUnzipAndImportTaskFinished = false;
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void prepareActionMode(int type) {
        if (type == 2) {
            this.adapter.selectionMode = 2;
        } else if (type == 5) {
            this.adapter.selectionMode = 1;
        }
        super.prepareActionMode(type);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void packItems(List<ProjectData> selectedItems) {
        throw new IllegalStateException(TAG + ": Projects cannot be backpacked");
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void switchToBackpack() {
        throw new IllegalStateException(TAG + ": Projects cannot be backpacked");
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void copyItems(List<ProjectData> selectedItems) {
        finishActionMode();
        setShowProgressBar(true);
        if (selectedItems == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.adapter.getItems());
        for (ProjectData projectData : selectedItems) {
            if (projectData != null) {
                String uniqueNameInNameables = this.uniqueNameProvider.getUniqueNameInNameables(projectData.getName(), arrayList);
                arrayList.add(new ProjectData(uniqueNameInNameables, null, 0.0d, false));
                File directory = projectData.getDirectory();
                Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
                Intrinsics.checkNotNull(uniqueNameInNameables);
                ProjectCopier.copyProjectAsync$default(new ProjectCopier(directory, uniqueNameInNameables), new Function1<Boolean, Unit>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment.copyItems.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                        invoke(bool.booleanValue());
                        return Unit.INSTANCE;
                    }

                    public final void invoke(boolean z) {
                        ProjectListFragment.this.onCopyProjectComplete(z);
                    }
                }, null, null, 6, null);
            }
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    protected void deleteItems(List<ProjectData> selectedItems) {
        if (selectedItems == null) {
            return;
        }
        final List listFilterNotNull = CollectionsKt.filterNotNull(selectedItems);
        if (listFilterNotNull.isEmpty()) {
            return;
        }
        List list = listFilterNotNull;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ProjectData) it.next()).getDirectory());
        }
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        ProjectTrashManager.showDeleteMultipleProjectsDialog(contextRequireContext, arrayList, new Runnable() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ProjectListFragment.deleteItems$lambda$12(this.f$0, listFilterNotNull);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteItems$lambda$12(final ProjectListFragment this$0, final List validItems) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(validItems, "$validItems");
        this$0.requireActivity().runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ProjectListFragment.deleteItems$lambda$12$lambda$11(this.f$0, validItems);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public static final void deleteItems$lambda$12$lambda$11(ProjectListFragment this$0, List validItems) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(validItems, "$validItems");
        this$0.setShowProgressBar(true);
        INSTANCE.clearCache();
        Iterator it = validItems.iterator();
        while (it.hasNext()) {
            ProjectData projectData = (ProjectData) it.next();
            this$0.getProjectManager().deleteDownloadedProjectInformation(projectData.getName());
            this$0.adapter.remove(projectData);
        }
        int size = validItems.size();
        ToastUtil.showSuccess(this$0.requireContext(), this$0.getResources().getQuantityString(R.plurals.deleted_projects, size, Integer.valueOf(size)));
        this$0.finishActionMode();
        this$0.setAdapterItems(this$0.adapter.projectsSorted);
        this$0.checkForEmptyList();
        this$0.setShowProgressBar(false);
    }

    public final void checkForEmptyList() {
        if (this.adapter.getItems().isEmpty()) {
            setShowProgressBar(true);
            if (getProjectManager().initializeDefaultProject()) {
                setAdapterItems(this.adapter.projectsSorted);
                setShowProgressBar(false);
            } else {
                ToastUtil.showError(requireContext(), R.string.wtf_error);
                requireActivity().finish();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void renameItem(ProjectData item, String name2) {
        finishActionMode();
        if (item == null || name2 == null || Intrinsics.areEqual(name2, item.getName())) {
            return;
        }
        setShowProgressBar(true);
        INSTANCE.clearCache();
        File directory = item.getDirectory();
        Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
        ProjectRenamer.renameProjectAsync$default(new ProjectRenamer(directory, name2), new Function1<Boolean, Unit>() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment.renameItem.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Boolean bool) {
                invoke(bool.booleanValue());
                return Unit.INSTANCE;
            }

            public final void invoke(boolean z) {
                ProjectListFragment.this.onRenameFinished(z);
            }
        }, null, null, 6, null);
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        try {
            if (success) {
                Intent intent = new Intent(context, (Class<?>) ProjectActivity.class);
                intent.putExtra("fragmentPosition", 0);
                startActivity(intent);
            } else {
                setShowProgressBar(false);
                ToastUtil.showError(context, R.string.error_load_project);
            }
        } catch (Exception e) {
            ToastUtil.showError(CatroidApplication.getAppContext(), "Something went wrong");
            e.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        inflater.inflate(R.menu.menu_projects_activity, menu);
        MenuItem menuItemFindItem = menu.findItem(R.id.merge);
        if (menuItemFindItem != null) {
            menuItemFindItem.setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onCopyProjectComplete(boolean success) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        if (success) {
            setAdapterItems(this.adapter.projectsSorted);
        } else {
            ToastUtil.showError(context, R.string.error_copy_project);
        }
        setShowProgressBar(false);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onItemClick(ProjectData item, MultiSelectionManager selectionManager) {
        File directory;
        File absoluteFile;
        int i = this.actionModeType;
        String absolutePath = null;
        if (i == 0) {
            setShowProgressBar(true);
            File directory2 = item != null ? item.getDirectory() : null;
            if (directory2 == null) {
                return;
            }
            Context contextRequireContext = requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            ProjectLoader.loadProjectAsync$default(new ProjectLoader(directory2, contextRequireContext).setListener(this), null, 1, null);
            return;
        }
        if (i == 4) {
            super.onItemClick(item, (MultiSelectionManager) null);
            return;
        }
        if (i == 6) {
            Intent intent = new Intent();
            if (item != null && (directory = item.getDirectory()) != null && (absoluteFile = directory.getAbsoluteFile()) != null) {
                absolutePath = absoluteFile.getAbsolutePath();
            }
            intent.putExtra("merge", absolutePath);
            requireActivity().setResult(-1, intent);
            requireActivity().finish();
            return;
        }
        super.onItemClick(item, selectionManager);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment
    public void onItemLongClick(ProjectData item, CheckableViewHolder holder) {
        onItemClick(item, (MultiSelectionManager) null);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
    public void onSettingsClick(final ProjectData item, View view) {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(item);
        try {
            PopupMenu popupMenuCreateSettingsPopUpMenu = UiUtils.createSettingsPopUpMenu(view, requireContext(), R.menu.menu_project_activity, new int[]{R.id.new_group, R.id.new_scene, R.id.show_details, R.id.from_library, R.id.from_local, R.id.edit});
            Intrinsics.checkNotNullExpressionValue(popupMenuCreateSettingsPopUpMenu, "createSettingsPopUpMenu(...)");
            popupMenuCreateSettingsPopUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$$ExternalSyntheticLambda5
                @Override // android.widget.PopupMenu.OnMenuItemClickListener
                public final boolean onMenuItemClick(MenuItem menuItem) {
                    return ProjectListFragment.onSettingsClick$lambda$13(this.f$0, arrayList, item, menuItem);
                }
            });
            popupMenuCreateSettingsPopUpMenu.show();
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showError(requireContext(), R.string.error_unknown_error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean onSettingsClick$lambda$13(ProjectListFragment this$0, List itemList, ProjectData projectData, MenuItem menuItem) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(itemList, "$itemList");
        Intrinsics.checkNotNullParameter(menuItem, "menuItem");
        switch (menuItem.getItemId()) {
            case R.id.copy /* 2131364141 */:
                this$0.copyItems(itemList);
                break;
            case R.id.delete /* 2131364226 */:
                this$0.deleteItems(itemList);
                break;
            case R.id.project_files /* 2131365061 */:
                this$0.showProjectFilesFragment(projectData);
                break;
            case R.id.project_libs /* 2131365066 */:
                this$0.showProjectLibsFragment(projectData);
                break;
            case R.id.project_options /* 2131365069 */:
                this$0.showProjectOptionsFragment(projectData);
                break;
            case R.id.rename /* 2131365129 */:
                this$0.showRenameDialog(projectData);
                break;
        }
        return true;
    }

    private final void showProjectOptionsFragment(ProjectData item) {
        if (item == null) {
            return;
        }
        try {
            getProjectManager().setCurrentProject(XstreamSerializer.getInstance().loadProject(item.getDirectory(), requireContext()));
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectOptionsFragment(), ProjectOptionsFragment.INSTANCE.getTAG()).addToBackStack(ProjectOptionsFragment.INSTANCE.getTAG()).commit();
        } catch (IOException e) {
            ToastUtil.showError(requireContext(), R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (LoadingProjectException e2) {
            ToastUtil.showError(requireContext(), R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e2));
        }
    }

    private final void showProjectLibsFragment(ProjectData item) {
        if (item == null) {
            return;
        }
        try {
            getProjectManager().setCurrentProject(XstreamSerializer.getInstance().loadProject(item.getDirectory(), requireContext()));
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectLibsFragment(), ProjectOptionsFragment.INSTANCE.getTAG()).addToBackStack(ProjectOptionsFragment.INSTANCE.getTAG()).commit();
        } catch (IOException e) {
            ToastUtil.showError(requireContext(), R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (LoadingProjectException e2) {
            ToastUtil.showError(requireContext(), R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e2));
        }
    }

    private final void showProjectFilesFragment(ProjectData item) {
        if (item == null) {
            return;
        }
        try {
            getProjectManager().setCurrentProject(XstreamSerializer.getInstance().loadProject(item.getDirectory(), requireContext()));
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProjectFilesFragment(), ProjectOptionsFragment.INSTANCE.getTAG()).addToBackStack(ProjectOptionsFragment.INSTANCE.getTAG()).commit();
        } catch (IOException e) {
            ToastUtil.showError(requireContext(), R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (LoadingProjectException e2) {
            ToastUtil.showError(requireContext(), R.string.error_load_project);
            Log.e(TAG, Log.getStackTraceString(e2));
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.RecyclerViewFragment, androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        this.adapter.projectsSorted = PreferenceManager.getDefaultSharedPreferences(requireContext()).getBoolean(SharedPreferenceKeys.SORT_PROJECTS_PREFERENCE_KEY, false);
        MenuItem menuItemFindItem = menu.findItem(R.id.sort_projects);
        if (menuItemFindItem != null) {
            menuItemFindItem.setTitle(this.adapter.projectsSorted ? R.string.unsort_projects : R.string.sort_projects);
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    private final void setAdapterItems(boolean sortProjects) {
        if (sortProjects) {
            this.adapter.setItems(getSortedItemList());
        } else {
            this.adapter.setItems(getItemList());
        }
        this.adapter.notifyDataSetChanged();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectListFragment.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0007J\u0016\u0010\u0010\u001a\u00020\u000f2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\b\u0010\u0002\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$Companion;", "", "()V", "PERMISSIONS_REQUEST_IMPORT_FROM_EXTERNAL_STORAGE", "", "REQUEST_IMPORT_PROJECT", "TAG", "", "getTAG$annotations", "getTAG", "()Ljava/lang/String;", "projectDataCache", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/catrobat/catroid/common/ProjectData;", "clearCache", "", "getLocalProjectList", FirebaseAnalytics.Param.ITEMS, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getTAG$annotations() {
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectListFragment.TAG;
        }

        @JvmStatic
        public final void clearCache() {
            ProjectListFragment.projectDataCache.clear();
        }

        @JvmStatic
        public final void getLocalProjectList(List<ProjectData> items) {
            Intrinsics.checkNotNullParameter(items, "items");
            File[] fileArrListFiles = FlavoredConstants.DEFAULT_ROOT_DIRECTORY.listFiles();
            if (fileArrListFiles != null) {
                for (File file : fileArrListFiles) {
                    File file2 = new File(file, Constants.CODE_XML_FILE_NAME);
                    if (file2.exists()) {
                        String absolutePath = file2.getAbsolutePath();
                        ProjectData projectData = (ProjectData) ProjectListFragment.projectDataCache.get(absolutePath);
                        if (projectData != null) {
                            items.add(projectData);
                        } else {
                            try {
                                ProjectData projectMetaData = new ProjectMetaDataParser(file2).getProjectMetaData();
                                ConcurrentHashMap concurrentHashMap = ProjectListFragment.projectDataCache;
                                Intrinsics.checkNotNull(absolutePath);
                                Intrinsics.checkNotNull(projectMetaData);
                                concurrentHashMap.put(absolutePath, projectMetaData);
                                items.add(projectMetaData);
                            } catch (IOException e) {
                                Log.e(ProjectListFragment.INSTANCE.getTAG(), "Could not parse local project.", e);
                            }
                        }
                    }
                }
            }
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ProjectListFragment", "getSimpleName(...)");
        TAG = "ProjectListFragment";
        projectDataCache = new ConcurrentHashMap<>();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateAdapterAsync() {
        setShowProgressBar(true);
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(viewLifecycleOwner), Dispatchers.getMain(), null, new C42011(null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$updateAdapterAsync$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ProjectListFragment.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment$updateAdapterAsync$1", f = "ProjectListFragment.kt", i = {}, l = {854, 873}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class C42011 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        C42011(Continuation<? super C42011> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return ProjectListFragment.this.new C42011(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C42011) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:26:0x009c  */
        /* JADX WARN: Code duplicated, block: B:28:0x00a8  */
        /* JADX WARN: Code duplicated, block: B:29:0x00ae  */
        /* JADX WARN: Code duplicated, block: B:30:0x00b8  */
        /* JADX WARN: Type inference incomplete: some casts might be missing */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            List list;
            Context context;
            FragmentActivity activity;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i != 0) {
                if (i == 1) {
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
                list = (List) obj;
                context = ProjectListFragment.this.getContext();
                if (context == null && (activity = ProjectListFragment.this.getActivity()) != null) {
                    ProjectListFragment.this.adapter.setItems((List<T>) list);
                    ProjectListFragment.this.adapter.notifyDataSetChanged();
                    if (ProjectListFragment.this.adapter.getItems().isEmpty()) {
                        if (ProjectListFragment.this.getProjectManager().initializeDefaultProject()) {
                            ProjectListFragment.this.updateAdapterAsync();
                        } else {
                            ToastUtil.showError(context, R.string.wtf_error);
                            activity.finish();
                        }
                    } else {
                        ProjectListFragment.this.setShowProgressBar(false);
                    }
                    return Unit.INSTANCE;
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            obj = BuildersKt.withContext(Dispatchers.getIO(), new ProjectListFragment$updateAdapterAsync$1$quickItems$1(ProjectListFragment.this, null), this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
            ProjectListFragment.this.adapter.setItems((List<T>) ((List) obj));
            ProjectListFragment.this.adapter.notifyDataSetChanged();
            this.label = 2;
            obj = BuildersKt.withContext(Dispatchers.getIO(), new ProjectListFragment$updateAdapterAsync$1$finalItems$1(ProjectListFragment.this, null), this);
            if (obj == coroutine_suspended) {
                return coroutine_suspended;
            }
            list = (List) obj;
            context = ProjectListFragment.this.getContext();
            if (context == null) {
                return Unit.INSTANCE;
            }
            ProjectListFragment.this.adapter.setItems((List<T>) list);
            ProjectListFragment.this.adapter.notifyDataSetChanged();
            if (ProjectListFragment.this.adapter.getItems().isEmpty()) {
                if (ProjectListFragment.this.getProjectManager().initializeDefaultProject()) {
                    ProjectListFragment.this.updateAdapterAsync();
                } else {
                    ToastUtil.showError(context, R.string.wtf_error);
                    activity.finish();
                }
            } else {
                ProjectListFragment.this.setShowProgressBar(false);
            }
            return Unit.INSTANCE;
        }
    }
}
