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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.databinding.FragmentProjectFilesBinding;
import org.catrobat.catroid.io.asynctask.ProjectSaverKt;
import org.catrobat.catroid.libraries.LibraryManager;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.adapter.FilesAdapter;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectLibsFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectLibsFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 =2\u00020\u0001:\u0001=B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\fH\u0002J\u0018\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\fH\u0002J\u0010\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u0018H\u0002J\u0010\u0010\"\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\fH\u0002J\u0010\u0010#\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010$\u001a\u00020\u001aH\u0002J\"\u0010%\u001a\u00020\u001a2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'2\b\u0010)\u001a\u0004\u0018\u00010*H\u0016J$\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u0001002\b\u00101\u001a\u0004\u0018\u000102H\u0016J\b\u00103\u001a\u00020\u001aH\u0016J\b\u00104\u001a\u00020\u001aH\u0016J\u001a\u00105\u001a\u00020\u001a2\u0006\u00106\u001a\u00020,2\b\u00101\u001a\u0004\u0018\u000102H\u0016J\u0010\u00107\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\fH\u0002J\b\u00108\u001a\u00020\u001aH\u0002J\b\u00109\u001a\u00020\u001aH\u0002J\b\u0010:\u001a\u00020\u001aH\u0002J\u0010\u0010;\u001a\u00020\u001a2\u0006\u0010<\u001a\u00020\fH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001b\u0010\u000f\u001a\u00020\u00108BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006>"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectLibsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lorg/catrobat/catroid/databinding/FragmentProjectFilesBinding;", "binding", "getBinding", "()Lorg/catrobat/catroid/databinding/FragmentProjectFilesBinding;", "filesAdapter", "Lorg/catrobat/catroid/ui/adapter/FilesAdapter;", "filesList", "", "Ljava/io/File;", "project", "Lorg/catrobat/catroid/content/Project;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "sceneName", "", "copyFile", "", "file", "copyFileToDir", "uri", "Landroid/net/Uri;", "dir", "copyToClipboard", "text", "deleteFile", "getFileName", "handleAdd", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onResume", "onViewCreated", Promotion.ACTION_VIEW, "openFile", "saveProject", "setupAdd", "setupRecyclerView", "updateFilesList", "directory", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ProjectLibsFragment extends Fragment {
    private static final int ADD_FILE_REQUEST = 15;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private FragmentProjectFilesBinding _binding;
    private FilesAdapter filesAdapter;
    private List<File> filesList;
    private Project project;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private RecyclerView recyclerView;
    private String sceneName;

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectLibsFragment() {
        final ProjectLibsFragment projectLibsFragment = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.fragment.ProjectLibsFragment$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = projectLibsFragment;
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
        getBinding().projectFilesCmd.hide();
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
            supportActionBar.setTitle(R.string.project_libs);
        }
        this.project = getProjectManager().getCurrentProject();
        this.sceneName = getProjectManager().getCurrentlyEditedScene().getName();
        setupAdd();
        setupRecyclerView();
        Project project = this.project;
        if (project != null) {
            File absoluteFile = new File(project.getDirectory(), "libs").getAbsoluteFile();
            Intrinsics.checkNotNullExpressionValue(absoluteFile, "getAbsoluteFile(...)");
            updateFilesList(absoluteFile);
        }
        BottomBar.hideBottomBar(requireActivity());
    }

    private final void setupAdd() {
        getBinding().projectFilesAdd.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.fragment.ProjectLibsFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ProjectLibsFragment.setupAdd$lambda$1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupAdd$lambda$1(ProjectLibsFragment this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.handleAdd();
    }

    private final void setupRecyclerView() {
        this.filesAdapter = new FilesAdapter(this.project, this.filesList, new Function1<File, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectLibsFragment.setupRecyclerView.1
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
                ProjectLibsFragment.this.deleteFile(file);
            }
        }, new Function1<File, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectLibsFragment.setupRecyclerView.2
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
                ProjectLibsFragment.this.copyFile(file);
            }
        }, new Function1<File, Unit>() { // from class: org.catrobat.catroid.ui.fragment.ProjectLibsFragment.setupRecyclerView.3
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
                ProjectLibsFragment.this.openFile(file);
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

    /* JADX INFO: Access modifiers changed from: private */
    public final void openFile(File file) {
        String type;
        ContentResolver contentResolver;
        if (!file.exists()) {
            Toast.makeText(requireContext(), "Файл не найден", 0).show();
            return;
        }
        Uri uriForFile = FileProvider.getUriForFile(requireContext(), "org.DanVexTeam.NewCatroidApkTemplate.provider", file);
        Intent intent = new Intent("android.intent.action.VIEW");
        Context context = getContext();
        if (context == null || (contentResolver = context.getContentResolver()) == null || (type = contentResolver.getType(uriForFile)) == null) {
            type = "*/*";
        }
        intent.setDataAndType(uriForFile, type);
        intent.addFlags(1);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(requireContext(), "Не найдено приложений для открытия", 1).show();
        }
    }

    private final void copyToClipboard(String text) {
        Object systemService = CatroidApplication.getAppContext().getSystemService("clipboard");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.content.ClipboardManager");
        ((ClipboardManager) systemService).setPrimaryClip(ClipData.newPlainText("Response", text));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void copyFile(File file) {
        String name2 = file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        copyToClipboard(name2);
        Toast.makeText(requireContext(), "Имя скопировано", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void deleteFile(File file) {
        if (file.exists() && file.delete()) {
            Project project = this.project;
            Intrinsics.checkNotNull(project);
            updateFilesList(new File(project.getDirectory(), "libs"));
            Toast.makeText(requireContext(), "Библиотека удалена", 0).show();
            return;
        }
        Toast.makeText(requireContext(), "Ошибка при удалении библиотеки", 0).show();
    }

    private final void updateFilesList(File directory) {
        int iIndexOf;
        File[] fileArrListFiles = directory.listFiles();
        int i = 0;
        if (fileArrListFiles == null) {
            fileArrListFiles = new File[0];
        }
        List<File> list = CollectionsKt.toList(this.filesList);
        int length = fileArrListFiles.length;
        while (true) {
            FilesAdapter filesAdapter = null;
            if (i >= length) {
                break;
            }
            File file = fileArrListFiles[i];
            if (!list.contains(file)) {
                List<File> list2 = this.filesList;
                Intrinsics.checkNotNull(file);
                list2.add(file);
                FilesAdapter filesAdapter2 = this.filesAdapter;
                if (filesAdapter2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("filesAdapter");
                } else {
                    filesAdapter = filesAdapter2;
                }
                filesAdapter.notifyItemInserted(this.filesList.size() - 1);
            }
            i++;
        }
        for (File file2 : list) {
            if (!ArraysKt.contains(fileArrListFiles, file2) && (iIndexOf = this.filesList.indexOf(file2)) != -1) {
                this.filesList.remove(iIndexOf);
                FilesAdapter filesAdapter3 = this.filesAdapter;
                if (filesAdapter3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("filesAdapter");
                    filesAdapter3 = null;
                }
                filesAdapter3.notifyItemRemoved(iIndexOf);
            }
        }
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

    private final void handleAdd() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory("android.intent.category.OPENABLE");
        startActivityForResult(Intent.createChooser(intent, "Выберите файл .newlib"), 15);
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        getProjectManager().setCurrentProject(this.project);
        Project project = this.project;
        if (project != null) {
            LibraryManager.INSTANCE.syncAndLoadLibraries(project);
        }
        BottomBar.hideBottomBar(requireActivity());
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int requestCode, int resultCode, Intent data) throws FileNotFoundException {
        Uri data2;
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 15 && resultCode == -1 && (data2 = data.getData()) != null) {
            Project project = this.project;
            File file = new File(project != null ? project.getDirectory() : null, "libs");
            if (!file.exists()) {
                file.mkdirs();
            }
            copyFileToDir(data2, file);
        }
    }

    private final void copyFileToDir(Uri uri, File dir) throws FileNotFoundException {
        InputStream inputStreamOpenInputStream = requireActivity().getContentResolver().openInputStream(uri);
        File file = new File(dir, getFileName(uri));
        InputStream inputStream = inputStreamOpenInputStream;
        try {
            InputStream inputStream2 = inputStream;
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                FileOutputStream fileOutputStream2 = fileOutputStream;
                if (inputStream2 != null) {
                    Long.valueOf(ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream2, 0, 2, null));
                }
                CloseableKt.closeFinally(fileOutputStream, null);
                CloseableKt.closeFinally(inputStream, null);
                updateFilesList(dir);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(fileOutputStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(inputStream, th3);
                throw th4;
            }
        }
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
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/fragment/ProjectLibsFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectLibsFragment.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/fragment/ProjectLibsFragment$Companion;", "", "()V", "ADD_FILE_REQUEST", "", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return ProjectLibsFragment.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("ProjectLibsFragment", "getSimpleName(...)");
        TAG = "ProjectLibsFragment";
    }
}
