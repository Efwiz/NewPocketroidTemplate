package org.catrobat.catroid.ide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hivemq.client.internal.mqtt.handler.auth.MqttAuthHandler;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ui.ProjectUploadActivityKt;
import org.catrobat.catroid.utils.git.GitHubActionsApi;
import org.catrobat.catroid.utils.git.TokenManager;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;
import org.json.JSONObject;
import retrofit2.Response;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: IdeActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000~\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001:\u0002=>B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0002J\u0010\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\bH\u0002J6\u0010\"\u001a\u00020\u001f2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020*H\u0082@¢\u0006\u0002\u0010+J\u0010\u0010,\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020\bH\u0002J\b\u0010.\u001a\u00020\u001fH\u0002J\u0012\u0010/\u001a\u00020\u001f2\b\u00100\u001a\u0004\u0018\u000101H\u0014J\b\u00102\u001a\u00020\u001fH\u0014J\u0010\u00103\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\bH\u0002J\b\u00104\u001a\u00020\u001fH\u0002J\b\u00105\u001a\u00020\u001fH\u0002J\b\u00106\u001a\u00020\u001fH\u0002J\b\u00107\u001a\u00020\u001fH\u0002J\b\u00108\u001a\u00020\u001fH\u0002J\b\u00109\u001a\u00020\u001fH\u0002J \u0010:\u001a\u00020\u001f2\u0006\u0010;\u001a\u00020&2\u0006\u0010'\u001a\u00020&2\u0006\u0010(\u001a\u00020&H\u0002J\b\u0010<\u001a\u00020\u001fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u0012\u0010\u0010\u001a\u00060\u0011R\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\b0\u0017j\b\u0012\u0004\u0012\u00020\b`\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0012\u0010\u001b\u001a\u00060\u001cR\u00020\u0000X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000¨\u0006?"}, d2 = {"Lorg/catrobat/catroid/ide/IdeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "aiInputField", "Landroid/widget/EditText;", "bottomPanel", "Landroid/widget/LinearLayout;", "currentDir", "Ljava/io/File;", "currentFileTab", "Landroid/widget/TextView;", "currentOpenedFile", "drawerLayout", "Landroidx/drawerlayout/widget/DrawerLayout;", "editor", "Lio/github/rosemoe/sora/widget/CodeEditor;", "fileAdapter", "Lorg/catrobat/catroid/ide/IdeActivity$FileBrowserAdapter;", "fileTreeRecycler", "Landroidx/recyclerview/widget/RecyclerView;", "filesList", "", "modifiedFiles", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "openFiles", ProjectUploadActivityKt.PROJECT_DIR, "tabAdapter", "Lorg/catrobat/catroid/ide/IdeActivity$EditorTabAdapter;", "tabsRecycler", "checkNotificationPermission", "", "closeFileTab", "file", "ensureBranchExists", "api", "Lorg/catrobat/catroid/utils/git/GitHubActionsApi;", MqttAuthHandler.NAME, "", FirebaseAnalytics.Event.LOGIN, ConfigConstants.CONFIG_BRANCH_SECTION, "jsonType", "Lokhttp3/MediaType;", "(Lorg/catrobat/catroid/utils/git/GitHubActionsApi;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lokhttp3/MediaType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "loadDirectory", "dir", "loadWorkspace", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "openFileInEditor", "saveCurrentFile", "saveWorkspace", "setupButtons", "setupEditor", "setupFileBrowser", "setupTabs", "startBuildService", "token", "startCloudBuild", "EditorTabAdapter", "FileBrowserAdapter", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeActivity extends AppCompatActivity {
    private EditText aiInputField;
    private LinearLayout bottomPanel;
    private File currentDir;
    private TextView currentFileTab;
    private File currentOpenedFile;
    private DrawerLayout drawerLayout;
    private CodeEditor editor;
    private FileBrowserAdapter fileAdapter;
    private RecyclerView fileTreeRecycler;
    private File projectDir;
    private EditorTabAdapter tabAdapter;
    private RecyclerView tabsRecycler;
    private final HashSet<File> modifiedFiles = new HashSet<>();
    private final List<File> openFiles = new ArrayList();
    private final List<File> filesList = new ArrayList();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$ensureBranchExists$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeActivity$ensureBranchExists$1, reason: invalid class name */
    /* JADX INFO: compiled from: IdeActivity.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ide.IdeActivity", f = "IdeActivity.kt", i = {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3}, l = {338, 349, 355, 369}, m = "ensureBranchExists", n = {"api", MqttAuthHandler.NAME, FirebaseAnalytics.Event.LOGIN, ConfigConstants.CONFIG_BRANCH_SECTION, "jsonType", "api", MqttAuthHandler.NAME, FirebaseAnalytics.Event.LOGIN, ConfigConstants.CONFIG_BRANCH_SECTION, "jsonType", "baseSha", "api", MqttAuthHandler.NAME, FirebaseAnalytics.Event.LOGIN, ConfigConstants.CONFIG_BRANCH_SECTION, "jsonType", "baseSha", ConfigConstants.CONFIG_BRANCH_SECTION}, s = {"L$0", "L$1", "L$2", "L$3", "L$4", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5", "L$0"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return IdeActivity.this.ensureBranchExists(null, null, null, null, null, this);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ide);
        View viewFindViewById = findViewById(R.id.drawer_layout);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.drawerLayout = (DrawerLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.editor);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.editor = (CodeEditor) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.bottom_panel);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.bottomPanel = (LinearLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.ai_input_field);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(...)");
        this.aiInputField = (EditText) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.file_tree_recycler);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(...)");
        this.fileTreeRecycler = (RecyclerView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.tabs_recycler);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(...)");
        this.tabsRecycler = (RecyclerView) viewFindViewById6;
        String stringExtra = getIntent().getStringExtra("PROJECT_NAME");
        if (stringExtra == null) {
            stringExtra = "UnknownProject";
        }
        File file = new File(getFilesDir(), "IdeProjects/" + stringExtra);
        this.projectDir = file;
        this.currentDir = file;
        setupEditor();
        setupButtons();
        setupFileBrowser();
        setupTabs();
        loadWorkspace();
        File file2 = this.currentDir;
        if (file2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("currentDir");
            file2 = null;
        }
        loadDirectory(file2);
    }

    private final void setupTabs() {
        this.tabAdapter = new EditorTabAdapter();
        RecyclerView recyclerView = this.tabsRecycler;
        EditorTabAdapter editorTabAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabsRecycler");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this, 0, false));
        RecyclerView recyclerView2 = this.tabsRecycler;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabsRecycler");
            recyclerView2 = null;
        }
        EditorTabAdapter editorTabAdapter2 = this.tabAdapter;
        if (editorTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabAdapter");
        } else {
            editorTabAdapter = editorTabAdapter2;
        }
        recyclerView2.setAdapter(editorTabAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openFileInEditor(File file) {
        saveCurrentFile();
        if (!this.openFiles.contains(file)) {
            this.openFiles.add(file);
        }
        this.currentOpenedFile = file;
        EditorTabAdapter editorTabAdapter = this.tabAdapter;
        DrawerLayout drawerLayout = null;
        if (editorTabAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabAdapter");
            editorTabAdapter = null;
        }
        editorTabAdapter.notifyDataSetChanged();
        int iIndexOf = this.openFiles.indexOf(file);
        if (iIndexOf != -1) {
            RecyclerView recyclerView = this.tabsRecycler;
            if (recyclerView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tabsRecycler");
                recyclerView = null;
            }
            recyclerView.smoothScrollToPosition(iIndexOf);
        }
        CodeEditor codeEditor = this.editor;
        if (codeEditor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
            codeEditor = null;
        }
        codeEditor.setText(FilesKt.readText$default(file, null, 1, null));
        String lowerCase = FilesKt.getExtension(file).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        JavaLanguage javaLanguage = Intrinsics.areEqual(lowerCase, SuffixConstants.EXTENSION_java) ? new JavaLanguage() : new JavaLanguage();
        CodeEditor codeEditor2 = this.editor;
        if (codeEditor2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
            codeEditor2 = null;
        }
        codeEditor2.setEditorLanguage(javaLanguage);
        DrawerLayout drawerLayout2 = this.drawerLayout;
        if (drawerLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
        } else {
            drawerLayout = drawerLayout2;
        }
        drawerLayout.closeDrawer(8388611);
        saveWorkspace();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void closeFileTab(File file) {
        if (Intrinsics.areEqual(this.currentOpenedFile, file)) {
            saveCurrentFile();
        }
        this.openFiles.remove(file);
        EditorTabAdapter editorTabAdapter = null;
        if (this.openFiles.isEmpty()) {
            this.currentOpenedFile = null;
            CodeEditor codeEditor = this.editor;
            if (codeEditor == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editor");
                codeEditor = null;
            }
            codeEditor.setText("// Выберите файл слева в меню для редактирования");
        } else if (Intrinsics.areEqual(this.currentOpenedFile, file)) {
            openFileInEditor((File) CollectionsKt.last((List) this.openFiles));
        }
        EditorTabAdapter editorTabAdapter2 = this.tabAdapter;
        if (editorTabAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tabAdapter");
        } else {
            editorTabAdapter = editorTabAdapter2;
        }
        editorTabAdapter.notifyDataSetChanged();
        saveWorkspace();
    }

    private final void saveCurrentFile() {
        File file = this.currentOpenedFile;
        if (file != null) {
            CodeEditor codeEditor = this.editor;
            if (codeEditor == null) {
                Intrinsics.throwUninitializedPropertyAccessException("editor");
                codeEditor = null;
            }
            String string = codeEditor.getText().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            if (!file.exists() || Intrinsics.areEqual(FilesKt.readText$default(file, null, 1, null), string)) {
                return;
            }
            FilesKt.writeText$default(file, string, null, 2, null);
            this.modifiedFiles.add(file);
            Log.d("IDE_DEBUG", "Файл изменен: " + file.getName());
            saveWorkspace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void saveWorkspace() {
        File file = this.projectDir;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException(ProjectUploadActivityKt.PROJECT_DIR);
            file = null;
        }
        SharedPreferences.Editor editorEdit = getSharedPreferences("IDE_STATE_" + file.getName(), 0).edit();
        HashSet<File> hashSet = this.modifiedFiles;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(hashSet, 10));
        Iterator<T> it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add(((File) it.next()).getAbsolutePath());
        }
        editorEdit.putStringSet("modified_files", CollectionsKt.toSet(arrayList));
        editorEdit.putString("open_files", CollectionsKt.joinToString$default(this.openFiles, "|", null, null, 0, null, new Function1<File, CharSequence>() { // from class: org.catrobat.catroid.ide.IdeActivity$saveWorkspace$1$2
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(File it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                String absolutePath = it2.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                return absolutePath;
            }
        }, 30, null));
        File file2 = this.currentOpenedFile;
        editorEdit.putString("current_file", file2 != null ? file2.getAbsolutePath() : null);
        editorEdit.apply();
    }

    private final void loadWorkspace() {
        File file = this.projectDir;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException(ProjectUploadActivityKt.PROJECT_DIR);
            file = null;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("IDE_STATE_" + file.getName(), 0);
        Set<String> stringSet = sharedPreferences.getStringSet("modified_files", SetsKt.emptySet());
        if (stringSet == null) {
            stringSet = SetsKt.emptySet();
        }
        this.modifiedFiles.clear();
        Iterator<T> it = stringSet.iterator();
        while (it.hasNext()) {
            File file2 = new File((String) it.next());
            if (file2.exists()) {
                this.modifiedFiles.add(file2);
            }
        }
        this.openFiles.clear();
        String string = sharedPreferences.getString("open_files", "");
        String str = string != null ? string : "";
        if (str.length() > 0) {
            Iterator it2 = StringsKt.split$default((CharSequence) str, new String[]{"|"}, false, 0, 6, (Object) null).iterator();
            while (it2.hasNext()) {
                File file3 = new File((String) it2.next());
                if (file3.exists()) {
                    this.openFiles.add(file3);
                }
            }
        }
        String string2 = sharedPreferences.getString("current_file", null);
        if (string2 != null) {
            File file4 = new File(string2);
            if (file4.exists()) {
                openFileInEditor(file4);
            }
        }
    }

    private final void setupEditor() {
        CodeEditor codeEditor = this.editor;
        CodeEditor codeEditor2 = null;
        if (codeEditor == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
            codeEditor = null;
        }
        codeEditor.setColorScheme(new SchemeDarcula());
        CodeEditor codeEditor3 = this.editor;
        if (codeEditor3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
            codeEditor3 = null;
        }
        codeEditor3.setEditorLanguage(new JavaLanguage());
        CodeEditor codeEditor4 = this.editor;
        if (codeEditor4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("editor");
        } else {
            codeEditor2 = codeEditor4;
        }
        codeEditor2.setText("// Выберите файл слева в меню для редактирования");
    }

    private final void setupFileBrowser() {
        this.fileAdapter = new FileBrowserAdapter(this, this.filesList, new Function1<File, Unit>() { // from class: org.catrobat.catroid.ide.IdeActivity.setupFileBrowser.1
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
                if (Intrinsics.areEqual(file.getName(), "..")) {
                    IdeActivity ideActivity = IdeActivity.this;
                    File file2 = ideActivity.currentDir;
                    if (file2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("currentDir");
                        file2 = null;
                    }
                    File parentFile = file2.getParentFile();
                    Intrinsics.checkNotNull(parentFile);
                    ideActivity.loadDirectory(parentFile);
                    return;
                }
                if (file.isDirectory()) {
                    IdeActivity.this.loadDirectory(file);
                } else {
                    IdeActivity.this.openFileInEditor(file);
                }
            }
        });
        RecyclerView recyclerView = this.fileTreeRecycler;
        FileBrowserAdapter fileBrowserAdapter = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTreeRecycler");
            recyclerView = null;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView recyclerView2 = this.fileTreeRecycler;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileTreeRecycler");
            recyclerView2 = null;
        }
        FileBrowserAdapter fileBrowserAdapter2 = this.fileAdapter;
        if (fileBrowserAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileAdapter");
        } else {
            fileBrowserAdapter = fileBrowserAdapter2;
        }
        recyclerView2.setAdapter(fileBrowserAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadDirectory(File dir) {
        List listEmptyList;
        this.currentDir = dir;
        this.filesList.clear();
        String absolutePath = dir.getAbsolutePath();
        File file = this.projectDir;
        FileBrowserAdapter fileBrowserAdapter = null;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException(ProjectUploadActivityKt.PROJECT_DIR);
            file = null;
        }
        if (!Intrinsics.areEqual(absolutePath, file.getAbsolutePath())) {
            this.filesList.add(new File(".."));
        }
        File[] fileArrListFiles = dir.listFiles();
        if (fileArrListFiles == null || (listEmptyList = ArraysKt.toList(fileArrListFiles)) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        this.filesList.addAll(CollectionsKt.sortedWith(listEmptyList, ComparisonsKt.compareBy(new Function1<File, Comparable<?>>() { // from class: org.catrobat.catroid.ide.IdeActivity$loadDirectory$sortedItems$1
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(File file2) {
                return Boolean.valueOf(!file2.isDirectory());
            }
        }, new Function1<File, Comparable<?>>() { // from class: org.catrobat.catroid.ide.IdeActivity$loadDirectory$sortedItems$2
            @Override // kotlin.jvm.functions.Function1
            public final Comparable<?> invoke(File file2) {
                String name2 = file2.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                String lowerCase = name2.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                return lowerCase;
            }
        })));
        FileBrowserAdapter fileBrowserAdapter2 = this.fileAdapter;
        if (fileBrowserAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("fileAdapter");
        } else {
            fileBrowserAdapter = fileBrowserAdapter2;
        }
        fileBrowserAdapter.notifyDataSetChanged();
    }

    private final void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT < 33 || checkSelfPermission("android.permission.POST_NOTIFICATIONS") == 0) {
            return;
        }
        requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 101);
    }

    private final void startCloudBuild() {
        String token = TokenManager.INSTANCE.getToken(this);
        if (token == null) {
            return;
        }
        if (this.currentOpenedFile != null) {
            saveCurrentFile();
        }
        File file = this.projectDir;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException(ProjectUploadActivityKt.PROJECT_DIR);
            file = null;
        }
        String name2 = file.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41731(token, this, "feature-" + StringsKt.substringAfter$default(name2, "_", (String) null, 2, (Object) null), null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$startCloudBuild$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: IdeActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1", f = "IdeActivity.kt", i = {0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5}, l = {260, 263, 271, 286, 292, 307, 311, MediaError.DetailedErrorCode.DASH_NO_INIT}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"api", "authHeader", "jsonType", "api", "authHeader", "jsonType", FirebaseAnalytics.Event.LOGIN, "api", "authHeader", "jsonType", FirebaseAnalytics.Event.LOGIN, "file", "relativePath", "fileSha", "api", "authHeader", "jsonType", FirebaseAnalytics.Event.LOGIN, "api", "authHeader", "jsonType", FirebaseAnalytics.Event.LOGIN, FirebaseAnalytics.Event.LOGIN}, s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$5", "L$6", "L$7", "L$0", "L$1", "L$2", "L$3", "L$0", "L$1", "L$2", "L$3", "L$0"})
    static final class C41731 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $branchName;
        final /* synthetic */ String $token;
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        Object L$6;
        Object L$7;
        int label;
        final /* synthetic */ IdeActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41731(String str, IdeActivity ideActivity, String str2, Continuation<? super C41731> continuation) {
            super(2, continuation);
            this.$token = str;
            this.this$0 = ideActivity;
            this.$branchName = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41731(this.$token, this.this$0, this.$branchName, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41731) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:48:0x0183 A[Catch: Exception -> 0x0029, TryCatch #2 {Exception -> 0x0029, blocks: (B:7:0x0024, B:12:0x0031, B:15:0x004a, B:18:0x0066, B:46:0x017d, B:48:0x0183, B:50:0x0199, B:51:0x019d, B:55:0x01f5, B:58:0x0202, B:60:0x020c, B:63:0x0213, B:64:0x021c, B:21:0x009a, B:24:0x00b7, B:43:0x0165, B:45:0x0173, B:27:0x00c8, B:34:0x011e, B:36:0x012a, B:39:0x0131, B:30:0x00d7), top: B:115:0x0014 }] */
        /* JADX WARN: Code duplicated, block: B:50:0x0199 A[Catch: Exception -> 0x0029, TryCatch #2 {Exception -> 0x0029, blocks: (B:7:0x0024, B:12:0x0031, B:15:0x004a, B:18:0x0066, B:46:0x017d, B:48:0x0183, B:50:0x0199, B:51:0x019d, B:55:0x01f5, B:58:0x0202, B:60:0x020c, B:63:0x0213, B:64:0x021c, B:21:0x009a, B:24:0x00b7, B:43:0x0165, B:45:0x0173, B:27:0x00c8, B:34:0x011e, B:36:0x012a, B:39:0x0131, B:30:0x00d7), top: B:115:0x0014 }] */
        /* JADX WARN: Code duplicated, block: B:53:0x01e7 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:54:0x01e8  */
        /* JADX WARN: Code duplicated, block: B:58:0x0202 A[Catch: Exception -> 0x0029, TRY_ENTER, TryCatch #2 {Exception -> 0x0029, blocks: (B:7:0x0024, B:12:0x0031, B:15:0x004a, B:18:0x0066, B:46:0x017d, B:48:0x0183, B:50:0x0199, B:51:0x019d, B:55:0x01f5, B:58:0x0202, B:60:0x020c, B:63:0x0213, B:64:0x021c, B:21:0x009a, B:24:0x00b7, B:43:0x0165, B:45:0x0173, B:27:0x00c8, B:34:0x011e, B:36:0x012a, B:39:0x0131, B:30:0x00d7), top: B:115:0x0014 }] */
        /* JADX WARN: Code duplicated, block: B:60:0x020c A[Catch: Exception -> 0x0029, TryCatch #2 {Exception -> 0x0029, blocks: (B:7:0x0024, B:12:0x0031, B:15:0x004a, B:18:0x0066, B:46:0x017d, B:48:0x0183, B:50:0x0199, B:51:0x019d, B:55:0x01f5, B:58:0x0202, B:60:0x020c, B:63:0x0213, B:64:0x021c, B:21:0x009a, B:24:0x00b7, B:43:0x0165, B:45:0x0173, B:27:0x00c8, B:34:0x011e, B:36:0x012a, B:39:0x0131, B:30:0x00d7), top: B:115:0x0014 }] */
        /* JADX WARN: Code duplicated, block: B:62:0x0212  */
        /* JADX WARN: Code duplicated, block: B:68:0x0261 A[Catch: Exception -> 0x02b0, TryCatch #0 {Exception -> 0x02b0, blocks: (B:66:0x023c, B:68:0x0261, B:69:0x0266), top: B:112:0x023c }] */
        /* JADX WARN: Code duplicated, block: B:72:0x02a1 A[RETURN] */
        /* JADX WARN: Code duplicated, block: B:73:0x02a2  */
        /* JADX WARN: Type inference failed for: r9v10, types: [T, java.lang.String] */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:73:0x02a2 -> B:74:0x02a6). Please report as a decompilation issue!!! */
        /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
            	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
            	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
            	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
            */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final java.lang.Object invokeSuspend(java.lang.Object r25) {
            /*
                Method dump skipped, instruction units count: 1066
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.catrobat.catroid.ide.IdeActivity.C41731.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$startCloudBuild$1$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: IdeActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1$1", f = "IdeActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class C02541 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ IdeActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C02541(IdeActivity ideActivity, Continuation<? super C02541> continuation) {
                super(2, continuation);
                this.this$0 = ideActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new C02541(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((C02541) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    EditorTabAdapter editorTabAdapter = this.this$0.tabAdapter;
                    if (editorTabAdapter == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tabAdapter");
                        editorTabAdapter = null;
                    }
                    editorTabAdapter.notifyDataSetChanged();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$startCloudBuild$1$2.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: IdeActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1$2", f = "IdeActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $branchName;
            final /* synthetic */ String $login;
            final /* synthetic */ String $token;
            int label;
            final /* synthetic */ IdeActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(IdeActivity ideActivity, String str, String str2, String str3, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.this$0 = ideActivity;
                this.$token = str;
                this.$login = str2;
                this.$branchName = str3;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.this$0, this.$token, this.$login, this.$branchName, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                IdeActivity ideActivity = this.this$0;
                String str = this.$token;
                String login = this.$login;
                Intrinsics.checkNotNullExpressionValue(login, "$login");
                ideActivity.startBuildService(str, login, this.$branchName);
                Toast.makeText(this.this$0, "Сборка запущена! 🚀", 0).show();
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$startCloudBuild$1$3.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: IdeActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ide.IdeActivity$startCloudBuild$1$3", f = "IdeActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ IdeActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(IdeActivity ideActivity, Exception exc, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = ideActivity;
                this.$e = exc;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass3(this.this$0, this.$e, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
                Toast.makeText(this.this$0, "Ошибка: " + this.$e.getMessage(), 1).show();
                return Unit.INSTANCE;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:34:0x0146  */
    /* JADX WARN: Code duplicated, block: B:41:0x0167  */
    /* JADX WARN: Code duplicated, block: B:43:0x018b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:44:0x018c  */
    /* JADX WARN: Code duplicated, block: B:47:0x0195  */
    /* JADX WARN: Code duplicated, block: B:49:0x019f  */
    /* JADX WARN: Code duplicated, block: B:56:0x01b8  */
    /* JADX WARN: Code duplicated, block: B:58:0x020a A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:59:0x020b  */
    /* JADX WARN: Code duplicated, block: B:62:0x0214  */
    /* JADX WARN: Code duplicated, block: B:64:0x0230  */
    /* JADX WARN: Code duplicated, block: B:66:0x024a  */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    /* JADX WARN: Type inference failed for: r0v24, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v37, types: [T, java.lang.String] */
    public final Object ensureBranchExists(GitHubActionsApi gitHubActionsApi, String str, String str2, String str3, MediaType mediaType, Continuation<? super Unit> continuation) throws Exception {
        AnonymousClass1 anonymousClass1;
        String str4;
        String str5;
        MediaType mediaType2;
        String str6;
        GitHubActionsApi gitHubActionsApi2;
        GitHubActionsApi gitHubActionsApi3;
        String str7;
        String str8;
        String str9;
        MediaType mediaType3;
        Ref.ObjectRef objectRef;
        String str10;
        Response response;
        GitHubActionsApi gitHubActionsApi4;
        ResponseBody responseBody;
        String strString;
        String str11;
        Response response2;
        ResponseBody responseBody2;
        String strString2;
        Response response3;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        AnonymousClass1 anonymousClass2 = anonymousClass1;
        Object ref = anonymousClass2.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass2.label;
        String str12 = "";
        if (i != 0) {
            if (i == 1) {
                mediaType2 = (MediaType) anonymousClass2.L$4;
                str6 = (String) anonymousClass2.L$3;
                str5 = (String) anonymousClass2.L$2;
                str4 = (String) anonymousClass2.L$1;
                gitHubActionsApi2 = (GitHubActionsApi) anonymousClass2.L$0;
                ResultKt.throwOnFailure(ref);
            } else if (i == 2) {
                objectRef = (Ref.ObjectRef) anonymousClass2.L$5;
                mediaType3 = (MediaType) anonymousClass2.L$4;
                str9 = (String) anonymousClass2.L$3;
                str8 = (String) anonymousClass2.L$2;
                str7 = (String) anonymousClass2.L$1;
                gitHubActionsApi3 = (GitHubActionsApi) anonymousClass2.L$0;
                ResultKt.throwOnFailure(ref);
                str10 = "IDE_DEBUG";
                response = (Response) ref;
                if (response.isSuccessful()) {
                    responseBody = (ResponseBody) response.body();
                    if (responseBody != null && (strString = responseBody.string()) != null) {
                        str12 = strString;
                    }
                    objectRef.element = new JSONObject(str12).getJSONObject("object").getString("sha");
                    gitHubActionsApi4 = gitHubActionsApi3;
                } else {
                    anonymousClass2.L$0 = gitHubActionsApi3;
                    anonymousClass2.L$1 = str7;
                    anonymousClass2.L$2 = str8;
                    anonymousClass2.L$3 = str9;
                    anonymousClass2.L$4 = mediaType3;
                    anonymousClass2.L$5 = objectRef;
                    anonymousClass2.label = 3;
                    ref = gitHubActionsApi3.getRef(str7, str8, "NewCatroid", Constants.MASTER, anonymousClass2);
                    if (ref == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    gitHubActionsApi4 = gitHubActionsApi3;
                    response2 = (Response) ref;
                    if (response2.isSuccessful()) {
                        responseBody2 = (ResponseBody) response2.body();
                        if (responseBody2 != null) {
                            str12 = strString2;
                        }
                        objectRef.element = new JSONObject(str12).getJSONObject("object").getString("sha");
                    }
                }
                if (objectRef.element == 0) {
                    throw new Exception("Не удалось найти базовую ветку (main/master) для создания форка.");
                }
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("ref", Constants.R_HEADS + str9);
                jSONObject.put("sha", objectRef.element);
                RequestBody.Companion companion = RequestBody.INSTANCE;
                String string = jSONObject.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                RequestBody requestBodyCreate = companion.create(string, mediaType3);
                anonymousClass2.L$0 = str9;
                anonymousClass2.L$1 = null;
                anonymousClass2.L$2 = null;
                anonymousClass2.L$3 = null;
                anonymousClass2.L$4 = null;
                anonymousClass2.L$5 = null;
                anonymousClass2.label = 4;
                ref = gitHubActionsApi4.createRef(str7, str8, "NewCatroid", requestBodyCreate, anonymousClass2);
                if (ref == coroutine_suspended) {
                    return coroutine_suspended;
                }
                str11 = str9;
            } else if (i == 3) {
                objectRef = (Ref.ObjectRef) anonymousClass2.L$5;
                mediaType3 = (MediaType) anonymousClass2.L$4;
                str9 = (String) anonymousClass2.L$3;
                str8 = (String) anonymousClass2.L$2;
                str7 = (String) anonymousClass2.L$1;
                gitHubActionsApi4 = (GitHubActionsApi) anonymousClass2.L$0;
                ResultKt.throwOnFailure(ref);
                str10 = "IDE_DEBUG";
                response2 = (Response) ref;
                if (response2.isSuccessful()) {
                    responseBody2 = (ResponseBody) response2.body();
                    if (responseBody2 != null && (strString2 = responseBody2.string()) != null) {
                        str12 = strString2;
                    }
                    objectRef.element = new JSONObject(str12).getJSONObject("object").getString("sha");
                }
                if (objectRef.element == 0) {
                    throw new Exception("Не удалось найти базовую ветку (main/master) для создания форка.");
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("ref", Constants.R_HEADS + str9);
                jSONObject2.put("sha", objectRef.element);
                RequestBody.Companion companion2 = RequestBody.INSTANCE;
                String string2 = jSONObject2.toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                RequestBody requestBodyCreate2 = companion2.create(string2, mediaType3);
                anonymousClass2.L$0 = str9;
                anonymousClass2.L$1 = null;
                anonymousClass2.L$2 = null;
                anonymousClass2.L$3 = null;
                anonymousClass2.L$4 = null;
                anonymousClass2.L$5 = null;
                anonymousClass2.label = 4;
                ref = gitHubActionsApi4.createRef(str7, str8, "NewCatroid", requestBodyCreate2, anonymousClass2);
                if (ref == coroutine_suspended) {
                    return coroutine_suspended;
                }
                str11 = str9;
            } else {
                if (i != 4) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                str11 = (String) anonymousClass2.L$0;
                ResultKt.throwOnFailure(ref);
                str10 = "IDE_DEBUG";
            }
            response3 = (Response) ref;
            if (response3.isSuccessful()) {
                throw new Exception("Не удалось создать ветку на GitHub: " + response3.code());
            }
            Log.d(str10, "Ветка " + str11 + " успешно создана на GitHub!");
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(ref);
        Log.d("IDE_DEBUG", "Проверка ветки " + str3 + " на сервере...");
        anonymousClass2.L$0 = gitHubActionsApi;
        anonymousClass2.L$1 = str;
        anonymousClass2.L$2 = str2;
        anonymousClass2.L$3 = str3;
        anonymousClass2.L$4 = mediaType;
        anonymousClass2.label = 1;
        ref = gitHubActionsApi.getRef(str, str2, "NewCatroid", str3, anonymousClass2);
        if (ref == coroutine_suspended) {
            return coroutine_suspended;
        }
        str4 = str;
        str5 = str2;
        mediaType2 = mediaType;
        str6 = str3;
        gitHubActionsApi2 = gitHubActionsApi;
        if (((Response) ref).isSuccessful()) {
            Log.d("IDE_DEBUG", "Ветка уже существует.");
            return Unit.INSTANCE;
        }
        Log.d(str10, "Ветки нет. Создаем от main...");
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        anonymousClass2.L$0 = gitHubActionsApi2;
        anonymousClass2.L$1 = str4;
        anonymousClass2.L$2 = str5;
        anonymousClass2.L$3 = str6;
        anonymousClass2.L$4 = mediaType2;
        anonymousClass2.L$5 = objectRef2;
        anonymousClass2.label = 2;
        Object ref2 = gitHubActionsApi2.getRef(str4, str5, "NewCatroid", "main", anonymousClass2);
        if (ref2 == coroutine_suspended) {
            return coroutine_suspended;
        }
        gitHubActionsApi3 = gitHubActionsApi2;
        str7 = str4;
        str8 = str5;
        str9 = str6;
        mediaType3 = mediaType2;
        objectRef = objectRef2;
        ref = ref2;
        response = (Response) ref;
        if (response.isSuccessful()) {
            responseBody = (ResponseBody) response.body();
            if (responseBody != null) {
                str12 = strString;
            }
            objectRef.element = new JSONObject(str12).getJSONObject("object").getString("sha");
            gitHubActionsApi4 = gitHubActionsApi3;
        } else {
            anonymousClass2.L$0 = gitHubActionsApi3;
            anonymousClass2.L$1 = str7;
            anonymousClass2.L$2 = str8;
            anonymousClass2.L$3 = str9;
            anonymousClass2.L$4 = mediaType3;
            anonymousClass2.L$5 = objectRef;
            anonymousClass2.label = 3;
            ref = gitHubActionsApi3.getRef(str7, str8, "NewCatroid", Constants.MASTER, anonymousClass2);
            if (ref == coroutine_suspended) {
                return coroutine_suspended;
            }
            gitHubActionsApi4 = gitHubActionsApi3;
            response2 = (Response) ref;
            if (response2.isSuccessful()) {
                responseBody2 = (ResponseBody) response2.body();
                if (responseBody2 != null) {
                    str12 = strString2;
                }
                objectRef.element = new JSONObject(str12).getJSONObject("object").getString("sha");
            }
        }
        if (objectRef.element == 0) {
            throw new Exception("Не удалось найти базовую ветку (main/master) для создания форка.");
        }
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("ref", Constants.R_HEADS + str9);
        jSONObject3.put("sha", objectRef.element);
        RequestBody.Companion companion3 = RequestBody.INSTANCE;
        String string3 = jSONObject3.toString();
        Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
        RequestBody requestBodyCreate3 = companion3.create(string3, mediaType3);
        anonymousClass2.L$0 = str9;
        anonymousClass2.L$1 = null;
        anonymousClass2.L$2 = null;
        anonymousClass2.L$3 = null;
        anonymousClass2.L$4 = null;
        anonymousClass2.L$5 = null;
        anonymousClass2.label = 4;
        ref = gitHubActionsApi4.createRef(str7, str8, "NewCatroid", requestBodyCreate3, anonymousClass2);
        if (ref == coroutine_suspended) {
            return coroutine_suspended;
        }
        str11 = str9;
        response3 = (Response) ref;
        if (response3.isSuccessful()) {
            throw new Exception("Не удалось создать ветку на GitHub: " + response3.code());
        }
        Log.d(str10, "Ветка " + str11 + " успешно создана на GitHub!");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startBuildService(String token, String login, String branch) {
        Intent intent = new Intent(this, (Class<?>) BuildMonitorService.class);
        intent.putExtra("TOKEN", token);
        intent.putExtra("LOGIN", login);
        intent.putExtra("BRANCH", branch);
        File file = this.projectDir;
        if (file == null) {
            Intrinsics.throwUninitializedPropertyAccessException(ProjectUploadActivityKt.PROJECT_DIR);
            file = null;
        }
        intent.putExtra("FEATURE_NAME", file.getName());
        startForegroundService(intent);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
        saveCurrentFile();
    }

    private final void setupButtons() {
        ((ImageButton) findViewById(R.id.btn_open_files)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IdeActivity.setupButtons$lambda$7(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_ai_assistant)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IdeActivity.setupButtons$lambda$8(this.f$0, view);
            }
        });
        ((ImageButton) findViewById(R.id.btn_close_bottom_panel)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IdeActivity.setupButtons$lambda$9(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_check_syntax)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IdeActivity.setupButtons$lambda$10(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_build_cloud)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IdeActivity.setupButtons$lambda$11(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupButtons$lambda$7(IdeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        DrawerLayout drawerLayout = this$0.drawerLayout;
        if (drawerLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("drawerLayout");
            drawerLayout = null;
        }
        drawerLayout.openDrawer(8388611);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupButtons$lambda$8(IdeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LinearLayout linearLayout = this$0.bottomPanel;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomPanel");
            linearLayout = null;
        }
        LinearLayout linearLayout3 = this$0.bottomPanel;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomPanel");
        } else {
            linearLayout2 = linearLayout3;
        }
        linearLayout.setVisibility(linearLayout2.getVisibility() == 8 ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupButtons$lambda$9(IdeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        LinearLayout linearLayout = this$0.bottomPanel;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bottomPanel");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupButtons$lambda$10(IdeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveCurrentFile();
        Toast.makeText(this$0, "Код сохранен. Вызов ECJ...", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupButtons$lambda$11(IdeActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.saveCurrentFile();
        this$0.startCloudBuild();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$FileBrowserAdapter.class
      classes14.dex
     */
    /* JADX INFO: compiled from: IdeActivity.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0014B'\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J \u0010\r\u001a\u00020\t2\u000e\u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\fH\u0016J \u0010\u0010\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/ide/IdeActivity$FileBrowserAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lorg/catrobat/catroid/ide/IdeActivity$FileBrowserAdapter$ViewHolder;", "Lorg/catrobat/catroid/ide/IdeActivity;", FirebaseAnalytics.Param.ITEMS, "", "Ljava/io/File;", "onClick", "Lkotlin/Function1;", "", "(Lorg/catrobat/catroid/ide/IdeActivity;Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FileBrowserAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final List<File> items;
        private final Function1<File, Unit> onClick;
        final /* synthetic */ IdeActivity this$0;

        /* JADX WARN: Multi-variable type inference failed */
        public FileBrowserAdapter(IdeActivity ideActivity, List<? extends File> items, Function1<? super File, Unit> onClick) {
            Intrinsics.checkNotNullParameter(items, "items");
            Intrinsics.checkNotNullParameter(onClick, "onClick");
            this.this$0 = ideActivity;
            this.items = items;
            this.onClick = onClick;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$FileBrowserAdapter$ViewHolder.class
          classes14.dex
         */
        /* JADX INFO: compiled from: IdeActivity.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/catrobat/catroid/ide/IdeActivity$FileBrowserAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", Promotion.ACTION_VIEW, "Landroid/view/View;", "(Lorg/catrobat/catroid/ide/IdeActivity$FileBrowserAdapter;Landroid/view/View;)V", "icon", "Landroid/widget/ImageView;", "getIcon", "()Landroid/widget/ImageView;", "name", "Landroid/widget/TextView;", "getName", "()Landroid/widget/TextView;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public final class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView icon;
            private final TextView name;
            final /* synthetic */ FileBrowserAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public ViewHolder(final FileBrowserAdapter fileBrowserAdapter, View view) {
                super(view);
                Intrinsics.checkNotNullParameter(view, "view");
                this.this$0 = fileBrowserAdapter;
                View viewFindViewById = view.findViewById(R.id.file_icon);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
                this.icon = (ImageView) viewFindViewById;
                View viewFindViewById2 = view.findViewById(R.id.file_name);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
                this.name = (TextView) viewFindViewById2;
                view.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$FileBrowserAdapter$ViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        IdeActivity.FileBrowserAdapter.ViewHolder._init_$lambda$0(fileBrowserAdapter, this, view2);
                    }
                });
            }

            public final ImageView getIcon() {
                return this.icon;
            }

            public final TextView getName() {
                return this.name;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public static final void _init_$lambda$0(FileBrowserAdapter this$0, ViewHolder this$1, View view) {
                Intrinsics.checkNotNullParameter(this$0, "this$0");
                Intrinsics.checkNotNullParameter(this$1, "this$1");
                this$0.onClick.invoke(this$0.items.get(this$1.getAdapterPosition()));
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file_browser, parent, false);
            Intrinsics.checkNotNull(viewInflate);
            return new ViewHolder(this, viewInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(ViewHolder holder, int position) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            File file = this.items.get(position);
            holder.getName().setText(file.getName());
            if (Intrinsics.areEqual(file.getName(), "..")) {
                holder.getIcon().setImageResource(android.R.drawable.ic_menu_revert);
                return;
            }
            if (file.isDirectory()) {
                holder.getIcon().setImageResource(android.R.drawable.ic_menu_sort_by_size);
                return;
            }
            if (Intrinsics.areEqual(FilesKt.getExtension(file), SuffixConstants.EXTENSION_java)) {
                holder.getIcon().setImageResource(android.R.drawable.ic_menu_edit);
            } else if (Intrinsics.areEqual(FilesKt.getExtension(file), "xml")) {
                holder.getIcon().setImageResource(android.R.drawable.ic_menu_view);
            } else {
                holder.getIcon().setImageResource(android.R.drawable.ic_menu_gallery);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.items.size();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$EditorTabAdapter.class
      classes14.dex
     */
    /* JADX INFO: compiled from: IdeActivity.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u000fB\u0005¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\b2\u000e\u0010\t\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\n\u001a\u00020\u0006H\u0016J \u0010\u000b\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0006H\u0016¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/ide/IdeActivity$EditorTabAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lorg/catrobat/catroid/ide/IdeActivity$EditorTabAdapter$TabViewHolder;", "Lorg/catrobat/catroid/ide/IdeActivity;", "(Lorg/catrobat/catroid/ide/IdeActivity;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "TabViewHolder", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class EditorTabAdapter extends RecyclerView.Adapter<TabViewHolder> {

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeActivity$EditorTabAdapter$TabViewHolder.class
          classes14.dex
         */
        /* JADX INFO: compiled from: IdeActivity.kt */
        @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/catrobat/catroid/ide/IdeActivity$EditorTabAdapter$TabViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", Promotion.ACTION_VIEW, "Landroid/view/View;", "(Lorg/catrobat/catroid/ide/IdeActivity$EditorTabAdapter;Landroid/view/View;)V", "closeBtn", "Landroid/widget/ImageButton;", "getCloseBtn", "()Landroid/widget/ImageButton;", "container", "Landroid/widget/LinearLayout;", "getContainer", "()Landroid/widget/LinearLayout;", "name", "Landroid/widget/TextView;", "getName", "()Landroid/widget/TextView;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public final class TabViewHolder extends RecyclerView.ViewHolder {
            private final ImageButton closeBtn;
            private final LinearLayout container;
            private final TextView name;
            final /* synthetic */ EditorTabAdapter this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public TabViewHolder(EditorTabAdapter editorTabAdapter, View view) {
                super(view);
                Intrinsics.checkNotNullParameter(view, "view");
                this.this$0 = editorTabAdapter;
                View viewFindViewById = view.findViewById(R.id.tab_container);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
                this.container = (LinearLayout) viewFindViewById;
                View viewFindViewById2 = view.findViewById(R.id.tab_file_name);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
                this.name = (TextView) viewFindViewById2;
                View viewFindViewById3 = view.findViewById(R.id.tab_close_btn);
                Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
                this.closeBtn = (ImageButton) viewFindViewById3;
            }

            public final LinearLayout getContainer() {
                return this.container;
            }

            public final TextView getName() {
                return this.name;
            }

            public final ImageButton getCloseBtn() {
                return this.closeBtn;
            }
        }

        public EditorTabAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public TabViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Intrinsics.checkNotNullParameter(parent, "parent");
            View viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_editor_tab, parent, false);
            Intrinsics.checkNotNull(viewInflate);
            return new TabViewHolder(this, viewInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(TabViewHolder holder, int position) {
            String name2;
            Intrinsics.checkNotNullParameter(holder, "holder");
            final File file = (File) IdeActivity.this.openFiles.get(position);
            boolean zContains = IdeActivity.this.modifiedFiles.contains(file);
            TextView name3 = holder.getName();
            if (zContains) {
                name2 = file.getName() + " *";
            } else {
                name2 = file.getName();
            }
            name3.setText(name2);
            if (Intrinsics.areEqual(file, IdeActivity.this.currentOpenedFile)) {
                holder.getContainer().setBackgroundColor(Color.parseColor("#1E1E1E"));
                holder.getName().setTextColor(-1);
            } else {
                holder.getContainer().setBackgroundColor(Color.parseColor("#2D2D30"));
                holder.getName().setTextColor(Color.parseColor("#AAAAAA"));
            }
            LinearLayout container = holder.getContainer();
            final IdeActivity ideActivity = IdeActivity.this;
            container.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$EditorTabAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IdeActivity.EditorTabAdapter.onBindViewHolder$lambda$0(ideActivity, file, view);
                }
            });
            ImageButton closeBtn = holder.getCloseBtn();
            final IdeActivity ideActivity2 = IdeActivity.this;
            closeBtn.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeActivity$EditorTabAdapter$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    IdeActivity.EditorTabAdapter.onBindViewHolder$lambda$1(ideActivity2, file, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onBindViewHolder$lambda$0(IdeActivity this$0, File file, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(file, "$file");
            this$0.openFileInEditor(file);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onBindViewHolder$lambda$1(IdeActivity this$0, File file, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(file, "$file");
            this$0.closeFileTab(file);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return IdeActivity.this.openFiles.size();
        }
    }
}
