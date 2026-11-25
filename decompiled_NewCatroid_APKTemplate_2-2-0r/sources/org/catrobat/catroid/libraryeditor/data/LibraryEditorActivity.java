package org.catrobat.catroid.libraryeditor.data;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryEditorActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: LibraryEditorActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0011H\u0014J\b\u0010\u001d\u001a\u00020\u0011H\u0002J\b\u0010\u001e\u001a\u00020\u0011H\u0002J\b\u0010\u001f\u001a\u00020\u0011H\u0002R\u001c\u0010\u0003\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\r¨\u0006 "}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "importFileLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "saveFileLauncher", "toolbar", "Landroidx/appcompat/widget/Toolbar;", "viewModel", "Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "getViewModel", "()Lorg/catrobat/catroid/libraryeditor/data/LibraryEditorViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "observeViewModel", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "", LineReader.MENU, "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPause", "showClearConfirmationDialog", "triggerExport", "triggerImport", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibraryEditorActivity extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> importFileLauncher;
    private final ActivityResultLauncher<Intent> saveFileLauncher;
    private Toolbar toolbar;

    /* JADX INFO: renamed from: viewModel$delegate, reason: from kotlin metadata */
    private final Lazy viewModel;

    public LibraryEditorActivity() {
        final LibraryEditorActivity libraryEditorActivity = this;
        final Function0 function0 = null;
        this.viewModel = new ViewModelLazy(Reflection.getOrCreateKotlinClass(LibraryEditorViewModel.class), new Function0<ViewModelStore>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity$special$$inlined$viewModels$default$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelStore invoke() {
                return libraryEditorActivity.getViewModelStore();
            }
        }, new Function0<ViewModelProvider.Factory>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity$special$$inlined$viewModels$default$1
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ViewModelProvider.Factory invoke() {
                return libraryEditorActivity.getDefaultViewModelProviderFactory();
            }
        }, new Function0<CreationExtras>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity$special$$inlined$viewModels$default$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final CreationExtras invoke() {
                CreationExtras creationExtras;
                Function0 function1 = function0;
                return (function1 == null || (creationExtras = (CreationExtras) function1.invoke()) == null) ? libraryEditorActivity.getDefaultViewModelCreationExtras() : creationExtras;
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity$$ExternalSyntheticLambda0
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                LibraryEditorActivity.saveFileLauncher$lambda$1(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult, "registerForActivityResult(...)");
        this.saveFileLauncher = activityResultLauncherRegisterForActivityResult;
        ActivityResultLauncher<Intent> activityResultLauncherRegisterForActivityResult2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity$$ExternalSyntheticLambda1
            @Override // androidx.activity.result.ActivityResultCallback
            public final void onActivityResult(Object obj) {
                LibraryEditorActivity.importFileLauncher$lambda$3(this.f$0, (ActivityResult) obj);
            }
        });
        Intrinsics.checkNotNullExpressionValue(activityResultLauncherRegisterForActivityResult2, "registerForActivityResult(...)");
        this.importFileLauncher = activityResultLauncherRegisterForActivityResult2;
    }

    private final LibraryEditorViewModel getViewModel() {
        return (LibraryEditorViewModel) this.viewModel.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void saveFileLauncher$lambda$1(LibraryEditorActivity this$0, ActivityResult activityResult) {
        Intent data;
        Uri data2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null || (data2 = data.getData()) == null) {
            return;
        }
        this$0.getViewModel().exportToUri(this$0, data2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void importFileLauncher$lambda$3(LibraryEditorActivity this$0, ActivityResult activityResult) {
        Intent data;
        Uri data2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (activityResult.getResultCode() != -1 || (data = activityResult.getData()) == null || (data2 = data.getData()) == null) {
            return;
        }
        this$0.getViewModel().importFromUri(this$0, data2);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_editor);
        View viewFindViewById = findViewById(R.id.toolbar);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        Toolbar toolbar = (Toolbar) viewFindViewById;
        this.toolbar = toolbar;
        if (toolbar == null) {
            Intrinsics.throwUninitializedPropertyAccessException("toolbar");
            toolbar = null;
        }
        setSupportActionBar(toolbar);
        View viewFindViewById2 = findViewById(R.id.view_pager);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        ViewPager2 viewPager2 = (ViewPager2) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tab_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        viewPager2.setAdapter(new LibraryEditorPagerAdapter(this));
        new TabLayoutMediator((TabLayout) viewFindViewById3, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity$$ExternalSyntheticLambda3
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                LibraryEditorActivity.onCreate$lambda$4(this.f$0, tab, i);
            }
        }).attach();
        getViewModel().loadLastSession(this);
        observeViewModel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$4(LibraryEditorActivity this$0, TabLayout.Tab tab, int i) {
        int i2;
        String string;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(tab, "tab");
        if (i == 0) {
            i2 = R.string.libs_code;
        } else {
            if (i != 1) {
                if (i != 2) {
                    string = null;
                } else {
                    i2 = R.string.libs_bricks;
                }
                tab.setText(string);
            }
            i2 = R.string.libs_formulas;
        }
        string = this$0.getString(i2);
        tab.setText(string);
    }

    private final void observeViewModel() {
        LibraryEditorActivity libraryEditorActivity = this;
        getViewModel().getLibraryDraft().observe(libraryEditorActivity, new LibraryEditorActivity$sam$androidx_lifecycle_Observer$0(new Function1<LibraryDraft, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity.observeViewModel.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(LibraryDraft libraryDraft) {
                invoke2(libraryDraft);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(LibraryDraft libraryDraft) {
                Toolbar toolbar = LibraryEditorActivity.this.toolbar;
                if (toolbar == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("toolbar");
                    toolbar = null;
                }
                toolbar.setTitle(libraryDraft.getName());
            }
        }));
        getViewModel().getToastMessage().observe(libraryEditorActivity, new LibraryEditorActivity$sam$androidx_lifecycle_Observer$0(new Function1<Event<? extends String>, Unit>() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity.observeViewModel.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Event<? extends String> event) {
                invoke2((Event<String>) event);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Event<String> event) {
                String contentIfNotHandled = event.getContentIfNotHandled();
                if (contentIfNotHandled != null) {
                    Toast.makeText(LibraryEditorActivity.this, contentIfNotHandled, 0).show();
                }
            }
        }));
    }

    @Override // android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.library_editor_menu, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId == R.id.action_clear_library) {
            showClearConfirmationDialog();
            return true;
        }
        if (itemId == R.id.action_export_library) {
            triggerExport();
            return true;
        }
        if (itemId == R.id.action_import_library) {
            triggerImport();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final void triggerExport() {
        String name2;
        LibraryDraft value = getViewModel().getLibraryDraft().getValue();
        if (value == null || (name2 = value.getName()) == null) {
            name2 = "library";
        }
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("application/octet-stream");
        intent.putExtra("android.intent.extra.TITLE", name2 + ".newlib");
        this.saveFileLauncher.launch(intent);
    }

    private final void triggerImport() {
        Intent intent = new Intent("android.intent.action.OPEN_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        this.importFileLauncher.launch(intent);
    }

    private final void showClearConfirmationDialog() {
        new AlertDialog.Builder(this).setTitle(getString(R.string.libs_clearp)).setMessage(getString(R.string.libs_clearpd)).setPositiveButton(getString(R.string.libs_clear), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.libraryeditor.data.LibraryEditorActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                LibraryEditorActivity.showClearConfirmationDialog$lambda$7(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(getString(R.string.libs_cancel), (DialogInterface.OnClickListener) null).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showClearConfirmationDialog$lambda$7(LibraryEditorActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getViewModel().clearCurrentLibrary(this$0);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        getViewModel().saveCurrentSession(this);
    }
}
