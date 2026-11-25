package org.catrobat.catroid.ui.filepicker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.BaseCastActivity;
import org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter;
import org.catrobat.catroid.ui.recyclerview.adapter.multiselection.MultiSelectionManager;
import org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment;
import org.catrobat.catroid.ui.recyclerview.viewholder.CheckableViewHolder;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.ToastUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/FilePickerActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: FilePickerActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 (2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004:\u0001(B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u0015\u001a\u00020\u000bH\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\u0016\u0010\u0018\u001a\u00020\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0010\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u0017H\u0016J\b\u0010\u001b\u001a\u00020\u000bH\u0016J\b\u0010\u001c\u001a\u00020\u000bH\u0016J\u0012\u0010\u001d\u001a\u00020\u000b2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0018\u0010 \u001a\u00020\u000b2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u0011H\u0016J\b\u0010!\u001a\u00020\u000bH\u0002J\b\u0010\"\u001a\u00020\u000bH\u0016J\u0010\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u0017H\u0002J\b\u0010%\u001a\u00020\u000bH\u0003J\u0010\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u0012H\u0002R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006)"}, d2 = {"Lorg/catrobat/catroid/ui/filepicker/FilePickerActivity;", "Lorg/catrobat/catroid/ui/BaseCastActivity;", "Lorg/catrobat/catroid/ui/filepicker/ListProjectFilesTask$OnListProjectFilesListener;", "Lorg/catrobat/catroid/ui/filepicker/SelectActionModeCallback$ActionModeClickListener;", "Lorg/catrobat/catroid/ui/recyclerview/fragment/ProjectListFragment$ProjectImportFinishedListener;", "()V", "actionMode", "Landroid/view/ActionMode;", "filePickerAdapter", "Lorg/catrobat/catroid/ui/filepicker/FilePickerAdapter;", "files", "", "getFiles", "()Lkotlin/Unit;", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "storageRoots", "", "Ljava/io/File;", "getStorageRoots", "()Ljava/util/List;", "endMultiSelectionMode", "hasUnselectedItems", "", "initializeAdapter", "notifyActivityFinished", FirebaseAnalytics.Param.SUCCESS, "onBackPressed", "onConfirm", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onListProjectFilesComplete", "onSelectionChangedAction", "onToggleSelection", "setShowProgressBar", "show", "startMultiSelectionMode", "toggleItemSelection", "item", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FilePickerActivity extends BaseCastActivity implements ListProjectFilesTask.OnListProjectFilesListener, SelectActionModeCallback.ActionModeClickListener, ProjectListFragment.ProjectImportFinishedListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final int PERMISSIONS_REQUEST_IMPORT_FROM_EXTERNAL_STORAGE = 801;
    private static final String TAG;
    private ActionMode actionMode;
    private FilePickerAdapter filePickerAdapter;
    private RecyclerView recyclerView;

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsFragment.setToChosenLanguage(this);
        setContentView(R.layout.activity_file_picker);
        View viewFindViewById = findViewById(R.id.toolbar);
        Intrinsics.checkNotNull(viewFindViewById, "null cannot be cast to non-null type androidx.appcompat.widget.Toolbar");
        setSupportActionBar((Toolbar) viewFindViewById);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.import_project);
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayHomeAsUpEnabled(true);
        }
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setShowProgressBar(true);
        getFiles();
    }

    private final void setShowProgressBar(boolean show) {
        findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        findViewById(R.id.recycler_view).setVisibility(show ? 8 : 0);
    }

    private final Unit getFiles() {
        final List listListOf = CollectionsKt.listOf("android.permission.READ_EXTERNAL_STORAGE");
        new RequiresPermissionTask(listListOf) { // from class: org.catrobat.catroid.ui.filepicker.FilePickerActivity$files$1
            @Override // org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask
            public void task() {
                ListProjectFilesTask listProjectFilesTask = new ListProjectFilesTask(this.this$0);
                File[] fileArr = (File[]) this.this$0.getStorageRoots().toArray(new File[0]);
                listProjectFilesTask.execute(Arrays.copyOf(fileArr, fileArr.length));
            }
        }.execute(this);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<File> getStorageRoots() {
        ArrayList arrayList = new ArrayList();
        File[] externalFilesDirs = getExternalFilesDirs(null);
        Intrinsics.checkNotNullExpressionValue(externalFilesDirs, "getExternalFilesDirs(...)");
        for (File file : externalFilesDirs) {
            String absolutePath = file.getAbsolutePath();
            Log.e(TAG, file.canRead() + " Path: " + absolutePath);
            String packageName = getApplicationContext().getPackageName();
            Intrinsics.checkNotNull(absolutePath);
            arrayList.add(new File(StringsKt.replace$default(absolutePath, "/Android/data/" + packageName + new Regex("/files"), "", false, 4, (Object) null)));
        }
        return arrayList;
    }

    @Override // org.catrobat.catroid.ui.filepicker.ListProjectFilesTask.OnListProjectFilesListener
    public void onListProjectFilesComplete(List<? extends File> files) {
        setShowProgressBar(false);
        if (files == null || !files.isEmpty()) {
            if (files != null) {
                initializeAdapter(files);
            }
        } else {
            findViewById(R.id.empty_view).setVisibility(0);
            RecyclerView recyclerView = this.recyclerView;
            if (recyclerView == null) {
                return;
            }
            recyclerView.setVisibility(8);
        }
    }

    private final void initializeAdapter(List<? extends File> files) {
        FilePickerAdapter filePickerAdapter = new FilePickerAdapter(files);
        this.filePickerAdapter = filePickerAdapter;
        filePickerAdapter.setOnItemClickListener(new RVAdapter.OnItemClickListener<File>() { // from class: org.catrobat.catroid.ui.filepicker.FilePickerActivity.initializeAdapter.1
            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
            public void onSettingsClick(File item, View view) {
            }

            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
            public void onItemClick(File item, MultiSelectionManager selectionManager) {
                Intrinsics.checkNotNullParameter(selectionManager, "selectionManager");
                if (item != null) {
                    FilePickerActivity.this.toggleItemSelection(item);
                }
            }

            @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.OnItemClickListener
            public void onItemLongClick(File item, CheckableViewHolder holder) {
                if (item != null) {
                    FilePickerActivity.this.toggleItemSelection(item);
                }
            }
        });
        FilePickerAdapter filePickerAdapter2 = this.filePickerAdapter;
        if (filePickerAdapter2 != null) {
            filePickerAdapter2.setSelectionListener(new RVAdapter.SelectionListener() { // from class: org.catrobat.catroid.ui.filepicker.FilePickerActivity$$ExternalSyntheticLambda0
                @Override // org.catrobat.catroid.ui.recyclerview.adapter.RVAdapter.SelectionListener
                public final void onSelectionChanged(int i) {
                    FilePickerActivity.initializeAdapter$lambda$1(this.f$0, i);
                }
            });
        }
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView != null) {
            recyclerView.setAdapter(this.filePickerAdapter);
        }
        startMultiSelectionMode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initializeAdapter$lambda$1(FilePickerActivity this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.onSelectionChangedAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void toggleItemSelection(File item) {
        FilePickerAdapter filePickerAdapter = this.filePickerAdapter;
        if (filePickerAdapter != null) {
            filePickerAdapter.toggleSelection(item);
        }
        ActionMode actionMode = this.actionMode;
        if (actionMode != null) {
            actionMode.invalidate();
        }
    }

    @Override // org.catrobat.catroid.ui.filepicker.SelectActionModeCallback.ActionModeClickListener
    public void onToggleSelection() {
        FilePickerAdapter filePickerAdapter = this.filePickerAdapter;
        if (filePickerAdapter != null) {
            filePickerAdapter.toggleSelection();
        }
    }

    private final void startMultiSelectionMode() {
        FilePickerAdapter filePickerAdapter = this.filePickerAdapter;
        if (filePickerAdapter != null) {
            filePickerAdapter.showCheckBoxes = true;
        }
        this.actionMode = startActionMode(new SelectActionModeCallback(this));
        FilePickerAdapter filePickerAdapter2 = this.filePickerAdapter;
        if (filePickerAdapter2 != null) {
            filePickerAdapter2.notifyDataSetChanged();
        }
    }

    private final void onSelectionChangedAction() {
        ActionMode actionMode = this.actionMode;
        if (actionMode != null) {
            actionMode.invalidate();
        }
    }

    @Override // org.catrobat.catroid.ui.filepicker.SelectActionModeCallback.ActionModeClickListener
    public void endMultiSelectionMode() {
        finish();
    }

    @Override // org.catrobat.catroid.ui.filepicker.SelectActionModeCallback.ActionModeClickListener
    public void onConfirm() {
        FilePickerAdapter filePickerAdapter = this.filePickerAdapter;
        List<File> selectedItems = filePickerAdapter != null ? filePickerAdapter.getSelectedItems() : null;
        if (selectedItems != null && selectedItems.isEmpty()) {
            ToastUtil.showError(this, R.string.no_projects_selected);
            return;
        }
        setShowProgressBar(true);
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        if (selectedItems != null) {
            Iterator<File> it = selectedItems.iterator();
            while (it.hasNext()) {
                arrayList.add(Uri.fromFile(it.next()));
            }
        }
        Intent intent = new Intent("android.intent.action.SEND_MULTIPLE");
        intent.setType("*/*");
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        setResult(-1, intent);
        finish();
    }

    @Override // org.catrobat.catroid.ui.filepicker.SelectActionModeCallback.ActionModeClickListener
    public boolean hasUnselectedItems() {
        List<File> selectedItems;
        FilePickerAdapter filePickerAdapter = this.filePickerAdapter;
        Integer numValueOf = (filePickerAdapter == null || (selectedItems = filePickerAdapter.getSelectedItems()) == null) ? null : Integer.valueOf(selectedItems.size());
        FilePickerAdapter filePickerAdapter2 = this.filePickerAdapter;
        return !Intrinsics.areEqual(numValueOf, filePickerAdapter2 != null ? Integer.valueOf(filePickerAdapter2.getSelectableItemCount()) : null);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.fragment.ProjectListFragment.ProjectImportFinishedListener
    public void notifyActivityFinished(boolean success) {
        setShowProgressBar(false);
        if (success) {
            setResult(-1);
        } else {
            setResult(0);
        }
        finish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/filepicker/FilePickerActivity$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: FilePickerActivity.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/ui/filepicker/FilePickerActivity$Companion;", "", "()V", "PERMISSIONS_REQUEST_IMPORT_FROM_EXTERNAL_STORAGE", "", "TAG", "", "getTAG", "()Ljava/lang/String;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return FilePickerActivity.TAG;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("FilePickerActivity", "getSimpleName(...)");
        TAG = "FilePickerActivity";
    }
}
