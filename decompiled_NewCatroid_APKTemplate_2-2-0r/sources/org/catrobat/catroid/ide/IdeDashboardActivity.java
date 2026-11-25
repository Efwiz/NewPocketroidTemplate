package org.catrobat.catroid.ide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import okhttp3.ResponseBody;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.utils.git.GitController;
import org.catrobat.catroid.utils.git.GitHubActionsApi;
import org.catrobat.catroid.utils.git.GitResult;
import org.catrobat.catroid.utils.git.TokenManager;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;
import org.jetbrains.kotlin.name.SpecialNames;
import org.json.JSONObject;
import retrofit2.Response;
import retrofit2.Retrofit;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeDashboardActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: IdeDashboardActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0006H\u0002J\b\u0010\u0018\u001a\u00020\u0015H\u0002J\u0012\u0010\u0019\u001a\u00020\u00152\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\u0010\u0010\u001c\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u0006H\u0002J\b\u0010\u001e\u001a\u00020\u0015H\u0002J\b\u0010\u001f\u001a\u00020\u0015H\u0002J\u0010\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/ide/IdeDashboardActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "TARGET_SDK", "", "UPSTREAM_OWNER", "", "UPSTREAM_REPO", "loadingOverlay", "Landroid/widget/LinearLayout;", "projectsList", "Landroid/widget/ListView;", "tvLoadingText", "Landroid/widget/TextView;", "workspaceDir", "Ljava/io/File;", "getWorkspaceDir", "()Ljava/io/File;", "workspaceDir$delegate", "Lkotlin/Lazy;", "checkAndInstallSdk", "", "createForkAndClone", "token", "hideLoading", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "openIde", "projectName", "refreshProjectsList", "setupButtons", "showLoading", "text", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeDashboardActivity extends AppCompatActivity {
    private LinearLayout loadingOverlay;
    private ListView projectsList;
    private TextView tvLoadingText;
    private final int TARGET_SDK = 33;
    private final String UPSTREAM_OWNER = "Danveyd";
    private final String UPSTREAM_REPO = "NewCatroid";

    /* JADX INFO: renamed from: workspaceDir$delegate, reason: from kotlin metadata */
    private final Lazy workspaceDir = LazyKt.lazy(new Function0<File>() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$workspaceDir$2
        {
            super(0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final File invoke() {
            File file = new File(this.this$0.getFilesDir(), "IdeProjects");
            file.mkdirs();
            return file;
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public final File getWorkspaceDir() {
        return (File) this.workspaceDir.getValue();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ide_dashboard);
        View viewFindViewById = findViewById(R.id.loading_overlay);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(...)");
        this.loadingOverlay = (LinearLayout) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tv_loading_text);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(...)");
        this.tvLoadingText = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.projects_list);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(...)");
        this.projectsList = (ListView) viewFindViewById3;
        setupButtons();
        checkAndInstallSdk();
    }

    private final void checkAndInstallSdk() {
        if (!IdeSettings.INSTANCE.getAndroidJar(this, this.TARGET_SDK).exists()) {
            showLoading("Подготовка окружения...\nСкачивание Android SDK " + this.TARGET_SDK);
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new AnonymousClass1(null), 2, null);
            return;
        }
        refreshProjectsList();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeDashboardActivity$checkAndInstallSdk$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeDashboardActivity$checkAndInstallSdk$1, reason: invalid class name */
    /* JADX INFO: compiled from: IdeDashboardActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ide.IdeDashboardActivity$checkAndInstallSdk$1", f = "IdeDashboardActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return IdeDashboardActivity.this.new AnonymousClass1(continuation);
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
            SdkManager sdkManager = SdkManager.INSTANCE;
            IdeDashboardActivity ideDashboardActivity = IdeDashboardActivity.this;
            final boolean zDownloadPlatform = sdkManager.downloadPlatform(ideDashboardActivity, ideDashboardActivity.TARGET_SDK, new IdeDashboardActivity$checkAndInstallSdk$1$success$1(IdeDashboardActivity.this));
            final IdeDashboardActivity ideDashboardActivity2 = IdeDashboardActivity.this;
            ideDashboardActivity2.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$checkAndInstallSdk$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    IdeDashboardActivity.AnonymousClass1.invokeSuspend$lambda$0(ideDashboardActivity2, zDownloadPlatform);
                }
            });
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(IdeDashboardActivity ideDashboardActivity, boolean z) {
            ideDashboardActivity.hideLoading();
            if (z) {
                Toast.makeText(ideDashboardActivity, "SDK успешно установлен!", 0).show();
                ideDashboardActivity.refreshProjectsList();
            }
        }
    }

    private final void setupButtons() {
        ((Button) findViewById(R.id.btn_create_fork)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IdeDashboardActivity.setupButtons$lambda$0(this.f$0, view);
            }
        });
        ((Button) findViewById(R.id.btn_clone_existing)).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                IdeDashboardActivity.setupButtons$lambda$1(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupButtons$lambda$0(IdeDashboardActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        IdeDashboardActivity ideDashboardActivity = this$0;
        String token = TokenManager.INSTANCE.getToken(ideDashboardActivity);
        if (token == null) {
            Toast.makeText(ideDashboardActivity, "Сначала авторизуйтесь в GitHub!", 0).show();
        } else {
            this$0.createForkAndClone(token);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setupButtons$lambda$1(IdeDashboardActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Toast.makeText(this$0, "Функция подключения появится чуть позже", 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void refreshProjectsList() {
        final ArrayList arrayListEmptyList;
        File[] fileArrListFiles = getWorkspaceDir().listFiles();
        if (fileArrListFiles != null) {
            ArrayList arrayList = new ArrayList();
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    arrayList.add(file);
                }
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                arrayList3.add(((File) it.next()).getName());
            }
            arrayListEmptyList = arrayList3;
        } else {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListEmptyList);
        ListView listView = this.projectsList;
        ListView listView2 = null;
        if (listView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectsList");
            listView = null;
        }
        listView.setAdapter((ListAdapter) arrayAdapter);
        ListView listView3 = this.projectsList;
        if (listView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectsList");
            listView3 = null;
        }
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$$ExternalSyntheticLambda0
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                IdeDashboardActivity.refreshProjectsList$lambda$4(arrayListEmptyList, this, adapterView, view, i, j);
            }
        });
        ListView listView4 = this.projectsList;
        if (listView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("projectsList");
        } else {
            listView2 = listView4;
        }
        listView2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                return IdeDashboardActivity.refreshProjectsList$lambda$6(arrayListEmptyList, this, adapterView, view, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshProjectsList$lambda$4(List projects, IdeDashboardActivity this$0, AdapterView adapterView, View view, int i, long j) {
        Intrinsics.checkNotNullParameter(projects, "$projects");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String str = (String) projects.get(i);
        Intrinsics.checkNotNull(str);
        this$0.openIde(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean refreshProjectsList$lambda$6(List projects, final IdeDashboardActivity this$0, AdapterView adapterView, View view, int i, long j) {
        Intrinsics.checkNotNullParameter(projects, "$projects");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        final String str = (String) projects.get(i);
        new AlertDialog.Builder(this$0).setTitle("Удалить фичу?").setMessage("Вы точно хотите удалить " + str + "? Локальные изменения будут потеряны.").setPositiveButton("Удалить", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                IdeDashboardActivity.refreshProjectsList$lambda$6$lambda$5(this.f$0, str, dialogInterface, i2);
            }
        }).setNegativeButton("Отмена", (DialogInterface.OnClickListener) null).show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void refreshProjectsList$lambda$6$lambda$5(IdeDashboardActivity this$0, String str, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FilesKt.deleteRecursively(new File(this$0.getWorkspaceDir(), str));
        Toast.makeText(this$0, "Проект удален", 0).show();
        this$0.refreshProjectsList();
    }

    private final void createForkAndClone(String token) {
        showLoading("Создание форка на GitHub...\nЭто может занять пару минут.");
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), Dispatchers.getIO(), null, new C41741(token, this, null), 2, null);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeDashboardActivity$createForkAndClone$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeDashboardActivity$createForkAndClone$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: IdeDashboardActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ide.IdeDashboardActivity$createForkAndClone$1", f = "IdeDashboardActivity.kt", i = {1}, l = {133, 147, 158, 175}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"cloneUrl"}, s = {"L$0"})
    static final class C41741 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ String $token;
        Object L$0;
        int label;
        final /* synthetic */ IdeDashboardActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C41741(String str, IdeDashboardActivity ideDashboardActivity, Continuation<? super C41741> continuation) {
            super(2, continuation);
            this.$token = str;
            this.this$0 = ideDashboardActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new C41741(this.$token, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((C41741) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:45:0x0160 A[RETURN] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object objCreateFork$default;
            String strString;
            String strOptString;
            String str;
            File file;
            GitResult<Unit> gitResultDownloadAndUnpack;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            try {
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    GitHubActionsApi gitHubActionsApi = (GitHubActionsApi) new Retrofit.Builder().baseUrl("https://api.github.com/").build().create(GitHubActionsApi.class);
                    Intrinsics.checkNotNull(gitHubActionsApi);
                    String str2 = this.$token;
                    this.label = 1;
                    objCreateFork$default = GitHubActionsApi.DefaultImpls.createFork$default(gitHubActionsApi, "Bearer " + str2, null, this.this$0.UPSTREAM_OWNER, this.this$0.UPSTREAM_REPO, this, 2, null);
                    if (objCreateFork$default == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else {
                    if (i == 1) {
                        ResultKt.throwOnFailure(obj);
                        objCreateFork$default = obj;
                    } else if (i == 2) {
                        strOptString = (String) this.L$0;
                        ResultKt.throwOnFailure(obj);
                        String strTakeLast = StringsKt.takeLast(String.valueOf(System.currentTimeMillis()), 5);
                        str = "Feature_" + strTakeLast;
                        String str3 = "feature-" + strTakeLast;
                        file = new File(this.this$0.getWorkspaceDir(), str);
                        GitController gitController = new GitController(file);
                        Intrinsics.checkNotNull(strOptString);
                        gitResultDownloadAndUnpack = gitController.downloadAndUnpack(strOptString, this.$token, file);
                        this.L$0 = null;
                        this.label = 3;
                        if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass2(gitResultDownloadAndUnpack, this.this$0, str, file, null), this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    } else {
                        if (i != 3 && i != 4) {
                            throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                        }
                        ResultKt.throwOnFailure(obj);
                    }
                    return Unit.INSTANCE;
                }
                Response response = (Response) objCreateFork$default;
                if (!response.isSuccessful() && response.code() != 202) {
                    throw new Exception("Ошибка GitHub API: " + response.code());
                }
                ResponseBody responseBody = (ResponseBody) response.body();
                if (responseBody == null || (strString = responseBody.string()) == null) {
                    ResponseBody responseBodyErrorBody = response.errorBody();
                    strString = responseBodyErrorBody != null ? responseBodyErrorBody.string() : "";
                }
                if (strString.length() > 0) {
                    strOptString = new JSONObject(strString).optString("clone_url");
                    final IdeDashboardActivity ideDashboardActivity = this.this$0;
                    ideDashboardActivity.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.ide.IdeDashboardActivity$createForkAndClone$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            IdeDashboardActivity.C41741.invokeSuspend$lambda$0(ideDashboardActivity);
                        }
                    });
                    this.L$0 = strOptString;
                    this.label = 2;
                    if (DelayKt.delay(DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    String strTakeLast2 = StringsKt.takeLast(String.valueOf(System.currentTimeMillis()), 5);
                    str = "Feature_" + strTakeLast2;
                    String str4 = "feature-" + strTakeLast2;
                    file = new File(this.this$0.getWorkspaceDir(), str);
                    GitController gitController2 = new GitController(file);
                    Intrinsics.checkNotNull(strOptString);
                    gitResultDownloadAndUnpack = gitController2.downloadAndUnpack(strOptString, this.$token, file);
                    this.L$0 = null;
                    this.label = 3;
                    if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass2(gitResultDownloadAndUnpack, this.this$0, str, file, null), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    return Unit.INSTANCE;
                }
                throw new Exception("Не удалось получить ссылку на форк");
            } catch (Exception e) {
                e.printStackTrace();
                this.L$0 = null;
                this.label = 4;
                if (BuildersKt.withContext(Dispatchers.getMain(), new AnonymousClass3(this.this$0, e, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void invokeSuspend$lambda$0(IdeDashboardActivity ideDashboardActivity) {
            TextView textView = ideDashboardActivity.tvLoadingText;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvLoadingText");
                textView = null;
            }
            textView.setText("Клонирование исходного кода...");
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeDashboardActivity$createForkAndClone$1$2.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeDashboardActivity$createForkAndClone$1$2, reason: invalid class name */
        /* JADX INFO: compiled from: IdeDashboardActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ide.IdeDashboardActivity$createForkAndClone$1$2", f = "IdeDashboardActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ String $projectName;
            final /* synthetic */ GitResult<Unit> $result;
            final /* synthetic */ File $targetDir;
            int label;
            final /* synthetic */ IdeDashboardActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass2(GitResult<Unit> gitResult, IdeDashboardActivity ideDashboardActivity, String str, File file, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$result = gitResult;
                this.this$0 = ideDashboardActivity;
                this.$projectName = str;
                this.$targetDir = file;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass2(this.$result, this.this$0, this.$projectName, this.$targetDir, continuation);
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
                if (this.$result instanceof GitResult.Success) {
                    this.this$0.hideLoading();
                    Toast.makeText(this.this$0, "Проект готов к работе!", 0).show();
                    this.this$0.refreshProjectsList();
                    this.this$0.openIde(this.$projectName);
                } else {
                    FilesKt.deleteRecursively(this.$targetDir);
                    this.this$0.hideLoading();
                    TextView textView = this.this$0.tvLoadingText;
                    if (textView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvLoadingText");
                        textView = null;
                    }
                    textView.setText("Ошибка!");
                    Toast.makeText(this.this$0, "Error", 1).show();
                    this.this$0.refreshProjectsList();
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/IdeDashboardActivity$createForkAndClone$1$3.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ide.IdeDashboardActivity$createForkAndClone$1$3, reason: invalid class name */
        /* JADX INFO: compiled from: IdeDashboardActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ide.IdeDashboardActivity$createForkAndClone$1$3", f = "IdeDashboardActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            final /* synthetic */ Exception $e;
            int label;
            final /* synthetic */ IdeDashboardActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass3(IdeDashboardActivity ideDashboardActivity, Exception exc, Continuation<? super AnonymousClass3> continuation) {
                super(2, continuation);
                this.this$0 = ideDashboardActivity;
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
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.hideLoading();
                    Toast.makeText(this.this$0, "Ошибка: " + this.$e.getMessage(), 1).show();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void openIde(String projectName) {
        Intent intent = new Intent(this, (Class<?>) IdeActivity.class);
        intent.putExtra("PROJECT_NAME", projectName);
        startActivity(intent);
    }

    private final void showLoading(String text) {
        TextView textView = this.tvLoadingText;
        LinearLayout linearLayout = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvLoadingText");
            textView = null;
        }
        textView.setText(text);
        LinearLayout linearLayout2 = this.loadingOverlay;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingOverlay");
        } else {
            linearLayout = linearLayout2;
        }
        linearLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideLoading() {
        LinearLayout linearLayout = this.loadingOverlay;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingOverlay");
            linearLayout = null;
        }
        linearLayout.setVisibility(8);
    }
}
