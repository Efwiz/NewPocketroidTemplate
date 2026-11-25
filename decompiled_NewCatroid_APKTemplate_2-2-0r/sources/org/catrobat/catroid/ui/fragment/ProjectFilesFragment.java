package org.catrobat.catroid.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.apkeditor.Options;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.random.Random;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.databinding.FragmentProjectFilesBinding;
import org.catrobat.catroid.io.asynctask.ProjectSaverKt;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.ProjectUploadActivity;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.catrobat.catroid.ui.adapter.FilesAdapter;
import org.catrobat.catroid.utils.SimpleTextEditorActivity;
import org.catrobat.catroid.utils.Utils;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectFilesFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectFilesFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u0000 F2\u00020\u0001:\u0001FB\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tH\u0002J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001aH\u0002J\u0010\u0010 \u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tH\u0002J\u0010\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020#H\u0002J\u0006\u0010$\u001a\u00020\u001aJ\u0006\u0010%\u001a\u00020\u001aJ\b\u0010&\u001a\u00020\u001cH\u0002J\b\u0010'\u001a\u00020\u001cH\u0002J\b\u0010(\u001a\u00020\u001cH\u0002J\"\u0010)\u001a\u00020\u001c2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J$\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u0001042\b\u00105\u001a\u0004\u0018\u000106H\u0016J\b\u00107\u001a\u00020\u001cH\u0016J\b\u00108\u001a\u00020\u001cH\u0016J\b\u00109\u001a\u00020\u001cH\u0002J\u001a\u0010:\u001a\u00020\u001c2\u0006\u0010;\u001a\u0002002\b\u00105\u001a\u0004\u0018\u000106H\u0016J\u0010\u0010<\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tH\u0002J\u0010\u0010=\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020#H\u0002J\b\u0010>\u001a\u00020\u001cH\u0002J\b\u0010?\u001a\u00020\u001cH\u0002J\b\u0010@\u001a\u00020\u001cH\u0002J\b\u0010A\u001a\u00020\u001cH\u0002J\u000e\u0010B\u001a\u00020\u001c2\u0006\u0010C\u001a\u00020\u001aJ\u0010\u0010D\u001a\u00020\u001c2\u0006\u0010E\u001a\u00020\tH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006G"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectFilesFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/FragmentProjectFilesBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/FragmentProjectFilesBinding;", "currentDirectory", "Ljava/io/File;", "filesAdapter", "Lorg/catrobat/catroid/ui/adapter/FilesAdapter;", "filesList", "", "project", "Lorg/catrobat/catroid/content/Project;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "rootDirectory", "sceneName", "", "copyFile", "", "file", "copyToClipboard", "text", "deleteFile", "getFileName", "uri", "Landroid/net/Uri;", "getRandomError", "getRandomMessage", "handleAdd", "handleCmd", "handleText", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "onSaveProjectComplete", "onViewCreated", Promotion.ACTION_VIEW, "openFile", "saveFileToProject", "saveProject", "setupAdd", "setupBackPressed", "setupRecyclerView", "showToast", "toast", "updateFilesList", "directory", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectFilesFragment extends Fragment {
    private static final int ADD_FILE_REQUEST = 15;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private FragmentProjectFilesBinding _binding;
    private File currentDirectory;
    private FilesAdapter filesAdapter;
    private List<File> filesList;
    private Project project;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private RecyclerView recyclerView;
    private File rootDirectory;
    private String sceneName;

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectFilesFragment() {
        final ProjectFilesFragment projectFilesFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = projectFilesFragment;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
        this.filesList = new ArrayList();
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    private final FragmentProjectFilesBinding getBinding() {
        FragmentProjectFilesBinding fragmentProjectFilesBinding = this._binding;
        Intrinsics.checkNotNull(fragmentProjectFilesBinding);
        return fragmentProjectFilesBinding;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        this._binding = FragmentProjectFilesBinding.inflate(inflater, container, false);
        RecyclerView recyclerViewFiles = getBinding().recyclerViewFiles;
        Intrinsics.checkNotNullExpressionValue(recyclerViewFiles, "recyclerViewFiles");
        this.recyclerView = recyclerViewFiles;
        RelativeLayout root = getBinding().getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        return root;
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNull(fragmentActivityRequireActivity, "null cannot be cast to non-null type androidx.appcompat.app.AppCompatActivity");
        ActionBar supportActionBar = ((AppCompatActivity) fragmentActivityRequireActivity).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.project_files);
        }
        this.project = getProjectManager().getCurrentProject();
        this.sceneName = getProjectManager().getCurrentlyEditedScene().getName();
        Project project = this.project;
        File file = null;
        if (project != null) {
            File absoluteFile = new File(project.getDirectory(), "files").getAbsoluteFile();
            Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
            this.rootDirectory = absoluteFile;
            if (absoluteFile == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootDirectory");
                absoluteFile = null;
            }
            this.currentDirectory = absoluteFile;
        }
        setupAdd();
        setupRecyclerView();
        setupBackPressed();
        File file2 = this.currentDirectory;
        if (file2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
        } else {
            file = file2;
        }
        updateFilesList(file);
        BottomBar.hideBottomBar(requireActivity());
    }

    private final void setupBackPressed() {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment$setupBackPressed$callback$1
            {
                super(true);
            }

            @Override // androidx.activity.OnBackPressedCallback
            public void handleOnBackPressed() {
                File file = this.this$0.currentDirectory;
                File file2 = null;
                if (file == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
                    file = null;
                }
                File file3 = this.this$0.rootDirectory;
                if (file3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("rootDirectory");
                    file3 = null;
                }
                if (!Intrinsics.areEqual(file, file3)) {
                    ProjectFilesFragment projectFilesFragment = this.this$0;
                    File file4 = projectFilesFragment.currentDirectory;
                    if (file4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
                        file4 = null;
                    }
                    File parentFile = file4.getParentFile();
                    if (parentFile == null && (parentFile = this.this$0.rootDirectory) == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("rootDirectory");
                        parentFile = null;
                    }
                    projectFilesFragment.currentDirectory = parentFile;
                    ProjectFilesFragment projectFilesFragment2 = this.this$0;
                    File file5 = projectFilesFragment2.currentDirectory;
                    if (file5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
                    } else {
                        file2 = file5;
                    }
                    projectFilesFragment2.updateFilesList(file2);
                    return;
                }
                setEnabled(false);
                this.this$0.requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        };
        OnBackPressedDispatcher onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
        Intrinsics.checkNotNullExpressionValue(viewLifecycleOwner, "getViewLifecycleOwner(...)");
        onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback);
    }

    private final void setupAdd() {
        getBinding().projectFilesAdd.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectFilesFragment.setupAdd$lambda$1(this.f$0, view);
            }
        });
        getBinding().projectFilesCmd.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectFilesFragment.setupAdd$lambda$2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAdd$lambda$1(ProjectFilesFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleAdd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAdd$lambda$2(ProjectFilesFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleCmd();
    }

    private final void setupRecyclerView() {
        this.filesAdapter = new FilesAdapter(this.project, this.filesList, new Function1<File, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment.setupRecyclerView.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(File file) {
                invoke2(file);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(File file) {
                Intrinsics.checkNotNullParameter(file, "file");
                ProjectFilesFragment.this.deleteFile(file);
            }
        }, new Function1<File, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment.setupRecyclerView.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(File file) {
                invoke2(file);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(File file) {
                Intrinsics.checkNotNullParameter(file, "file");
                ProjectFilesFragment.this.copyFile(file);
            }
        }, new Function1<File, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment.setupRecyclerView.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(File file) {
                invoke2(file);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(File file) {
                Intrinsics.checkNotNullParameter(file, "file");
                ProjectFilesFragment.this.openFile(file);
            }
        });
        RecyclerView recyclerView = this.recyclerView;
        FilesAdapter filesAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView2 = null;
        }
        FilesAdapter filesAdapter2 = this.filesAdapter;
        if (filesAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filesAdapter");
        } else {
            filesAdapter = filesAdapter2;
        }
        recyclerView2.setAdapter(filesAdapter);
    }

    private final void handleCmd() {
        Unit unit;
        File filesDir;
        String absolutePath;
        Project project = this.project;
        if (project == null || (filesDir = project.getFilesDir()) == null || (absolutePath = filesDir.getAbsolutePath()) == null) {
            unit = null;
        } else {
            CommandPromptDialogFragment.INSTANCE.newInstance(absolutePath).show(getParentFragmentManager(), CommandPromptDialogFragment.INSTANCE.getTAG());
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            Toast.makeText(requireContext(), "Ошибка: директория проекта не найдена", 0).show();
        }
    }

    private final void copyToClipboard(String text) {
        Object systemService = CatroidApplication.getAppContext().getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("Response", text));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openFile(File file) {
        String type;
        ContentResolver contentResolver;
        File file2 = null;
        if (Intrinsics.areEqual(file.getName(), "..")) {
            File file3 = this.currentDirectory;
            if (file3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
                file3 = null;
            }
            File parentFile = file3.getParentFile();
            if (parentFile == null && (parentFile = this.rootDirectory) == null) {
                Intrinsics.throwUninitializedPropertyAccessException("rootDirectory");
                parentFile = null;
            }
            this.currentDirectory = parentFile;
            if (parentFile == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
            } else {
                file2 = parentFile;
            }
            updateFilesList(file2);
            return;
        }
        if (file.isDirectory()) {
            this.currentDirectory = file;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
                file = null;
            }
            updateFilesList(file);
            return;
        }
        if (!file.exists()) {
            Toast.makeText(requireContext(), "Файл не найден", 0).show();
            return;
        }
        String lowerCase = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        if (CollectionsKt.listOf((Object[]) new String[]{"txt", "py", Options.TYPE_JSON, "xml", "lua", "md", "csv", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "rscene", SuffixConstants.EXTENSION_java, KotlinFileType.EXTENSION}).contains(lowerCase)) {
            Intent intent = new Intent(requireContext(), (Class<?>) SimpleTextEditorActivity.class);
            intent.putExtra("FILE_PATH", file.getAbsolutePath());
            startActivity(intent);
            return;
        }
        try {
            Uri uriForFile = FileProvider.getUriForFile(requireContext(), "org.DanVexTeam.NewCatroidApkTemplate.fileProvider", file);
            Intent intent2 = new Intent("android.intent.action.VIEW");
            Context context = getContext();
            if (context == null || (contentResolver = context.getContentResolver()) == null || (type = contentResolver.getType(uriForFile)) == null) {
                type = "*/*";
            }
            intent2.setDataAndType(uriForFile, type);
            intent2.addFlags(1);
            startActivity(intent2);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(requireContext(), "Нечем открыть этот файл", 0).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Ошибка открытия: " + e.getLocalizedMessage(), 0).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void copyFile(File file) {
        String name2 = file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        copyToClipboard(name2);
        Toast.makeText(requireContext(), "Имя файла скопировано в буфер", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteFile(File file) {
        boolean zDelete;
        if (file.isDirectory()) {
            zDelete = FilesKt.deleteRecursively(file);
        } else {
            zDelete = file.delete();
        }
        if (zDelete) {
            File file2 = this.currentDirectory;
            if (file2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
                file2 = null;
            }
            updateFilesList(file2);
            Toast.makeText(requireContext(), "Удалено", 0).show();
            return;
        }
        Toast.makeText(requireContext(), "Ошибка при удалении", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateFilesList(File directory) {
        List listEmptyList;
        this.filesList.clear();
        File file = this.rootDirectory;
        FilesAdapter filesAdapter = null;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rootDirectory");
            file = null;
        }
        if (!Intrinsics.areEqual(directory, file)) {
            this.filesList.add(new File(directory.getParentFile(), ".."));
        }
        File[] fileArrListFiles = directory.listFiles();
        if (fileArrListFiles == null || (listEmptyList = ArraysKt.sortedWith(fileArrListFiles, ComparisonsKt.compareBy(new Function1<File, Comparable<?>>() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment$updateFilesList$sortedFiles$1
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(File file2) {
                return Boolean.valueOf(!file2.isDirectory());
            }
        }, new Function1<File, Comparable<?>>() { // from class: org.catrobat.catroid.ui.fragment.ProjectFilesFragment$updateFilesList$sortedFiles$2
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(File file2) {
                String name2 = file2.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                String lowerCase = name2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                return lowerCase;
            }
        }))) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        this.filesList.addAll(listEmptyList);
        FilesAdapter filesAdapter2 = this.filesAdapter;
        if (filesAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("filesAdapter");
        } else {
            filesAdapter = filesAdapter2;
        }
        filesAdapter.notifyDataSetChanged();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        saveProject();
        super.onPause();
    }

    private final void saveProject() {
        Project project = this.project;
        if (project == null) {
            return;
        }
        Context contextRequireContext = requireContext();
        Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
        ProjectSaverKt.saveProjectSerial(project, contextRequireContext);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 15 && resultCode == -1) {
            Uri data2 = data != null ? data.getData() : null;
            if (data2 != null) {
                saveFileToProject(data2);
            }
        }
    }

    private final void saveFileToProject(Uri uri) {
        String fileName = getFileName(uri);
        File file = this.currentDirectory;
        File file2 = null;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
            file = null;
        }
        File file3 = new File(file, fileName);
        File parentFile = file3.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        try {
            InputStream inputStreamOpenInputStream = requireContext().getContentResolver().openInputStream(uri);
            if (inputStreamOpenInputStream == null) {
                Toast.makeText(requireContext(), "Не удалось открыть файл", 0).show();
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            try {
                InputStream inputStream = inputStreamOpenInputStream;
                try {
                    ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                    CloseableKt.closeFinally(inputStream, null);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    File file4 = this.currentDirectory;
                    if (file4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentDirectory");
                    } else {
                        file2 = file4;
                    }
                    updateFilesList(file2);
                    Toast.makeText(requireContext(), getRandomMessage(), 0).show();
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(inputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(fileOutputStream, th3);
                    throw th4;
                }
            }
        } catch (Exception e) {
            Log.e("ProjectFile", "Error saving file", e);
            Toast.makeText(requireContext(), "Ошибка при сохранении: " + e.getLocalizedMessage(), 0).show();
        }
    }

    private final void handleAdd() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory("android.intent.category.OPENABLE");
        startActivityForResult(Intent.createChooser(intent, "Выберите файл"), 15);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getProjectManager().setCurrentProject(this.project);
        BottomBar.hideBottomBar(requireActivity());
    }

    private final void handleText() {
        showToast(getRandomError());
    }

    private final void onSaveProjectComplete() {
        Project currentProject = getProjectManager().getCurrentProject();
        if (Utils.isDefaultProject(currentProject, getActivity())) {
            getBinding().getRoot();
            Snackbar.make(getBinding().getRoot(), R.string.error_upload_default_project, 0).show();
        } else {
            Intent intent = new Intent(requireContext(), (Class<?>) ProjectUploadActivity.class);
            intent.putExtra(ProjectUploadActivityKt.PROJECT_DIR, currentProject.getDirectory());
            startActivity(intent);
        }
    }

    public final void showToast(String toast) {
        Intrinsics.checkNotNullParameter(toast, "toast");
        if (StageActivity.messageHandler != null) {
            StageActivity.messageHandler.obtainMessage(3, new ArrayList(CollectionsKt.listOf(toast))).sendToTarget();
        } else {
            Log.e("ShowToast", "messageHandler is null!");
        }
    }

    public final String getRandomMessage() {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"Готово!", "Сделано!", "Успех!", "Завершено!", "Отличная работа!", "Все готово!"});
        return (String) listListOf.get(Random.INSTANCE.nextInt(listListOf.size()));
    }

    public final String getRandomError() {
        List listListOf = CollectionsKt.listOf((Object[]) new String[]{"Произошла ошибка!", "Упс! Что-то пошло не так."});
        return (String) listListOf.get(Random.INSTANCE.nextInt(listListOf.size()));
    }

    private final String getFileName(Uri uri) {
        String name2 = "";
        if (Intrinsics.areEqual(uri.getScheme(), FirebaseAnalytics.Param.CONTENT)) {
            Cursor cursorQuery = requireActivity().getContentResolver().query(uri, null, null, null, null);
            if (cursorQuery != null) {
                Cursor cursor = cursorQuery;
                try {
                    Cursor cursor2 = cursor;
                    int columnIndex = cursor2.getColumnIndex("_display_name");
                    if (cursor2.moveToFirst() && columnIndex != -1) {
                        String string = cursor2.getString(columnIndex);
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        name2 = string;
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(cursor, null);
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(cursor, th);
                        throw th2;
                    }
                }
            }
        } else if (Intrinsics.areEqual(uri.getScheme(), "file")) {
            name2 = new File(uri.getPath()).getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        }
        String str = name2;
        if (str.length() == 0) {
            str = "неизвестный_файл";
        }
        return str;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectFilesFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectFilesFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectFilesFragment$Companion;", "", "()V", "ADD_FILE_REQUEST", "", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectFilesFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ProjectFilesFragment", "getSimpleName(...)");
        TAG = "ProjectFilesFragment";
    }
}
