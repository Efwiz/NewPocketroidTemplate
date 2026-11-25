package org.catrobat.catroid.ui;

import android.content.ActivityNotFoundException;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.support.api.entity.core.CommonCode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.Survey;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.databinding.ActivityUploadBinding;
import org.catrobat.catroid.databinding.DialogReplaceApiKeyBinding;
import org.catrobat.catroid.databinding.DialogUploadUnchangedProjectBinding;
import org.catrobat.catroid.exceptions.ProjectException;
import org.catrobat.catroid.io.ProjectAndSceneScreenshotLoader;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.io.asynctask.ProjectLoaderKt;
import org.catrobat.catroid.io.asynctask.ProjectRenamerKt;
import org.catrobat.catroid.transfers.CheckTokenTask;
import org.catrobat.catroid.transfers.GetTagsTask;
import org.catrobat.catroid.transfers.project.ResultReceiverWrapper;
import org.catrobat.catroid.transfers.project.ResultReceiverWrapperInterface;
import org.catrobat.catroid.ui.controller.ProjectUploadController;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ProjectUploadActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: ProjectUploadActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001c\b\u0016\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u0006:\u0001kB\u0005¢\u0006\u0002\u0010\u0007J\b\u00102\u001a\u000203H\u0002J\b\u00104\u001a\u000203H\u0002J\b\u00105\u001a\u000203H\u0002J\n\u00106\u001a\u0004\u0018\u00010(H\u0014J\b\u00107\u001a\u00020\u0000H\u0016J\b\u00108\u001a\u00020.H\u0016J\b\u00109\u001a\u000203H\u0002J\b\u0010:\u001a\u000203H\u0002J\b\u0010;\u001a\u000203H\u0002J\"\u0010<\u001a\u0002032\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020>2\b\u0010@\u001a\u0004\u0018\u00010AH\u0014J\b\u0010B\u001a\u000203H\u0016J\u0012\u0010C\u001a\u0002032\b\u0010D\u001a\u0004\u0018\u00010EH\u0014J\u0010\u0010F\u001a\u00020\u00132\u0006\u0010G\u001a\u00020HH\u0016J\b\u0010I\u001a\u000203H\u0002J\b\u0010J\u001a\u000203H\u0014J\u0010\u0010K\u001a\u0002032\u0006\u0010L\u001a\u00020\u0013H\u0016J\b\u0010M\u001a\u000203H\u0002J\u0010\u0010N\u001a\u00020\u00132\u0006\u0010O\u001a\u00020PH\u0016J\u0010\u0010Q\u001a\u00020\u00132\u0006\u0010G\u001a\u00020HH\u0016J\u001a\u0010R\u001a\u0002032\u0006\u0010?\u001a\u00020>2\b\u0010S\u001a\u0004\u0018\u00010EH\u0016J\u0016\u0010T\u001a\u0002032\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0*H\u0016J\u0018\u0010U\u001a\u0002032\u0006\u0010V\u001a\u00020\u00132\u0006\u0010W\u001a\u00020\u0013H\u0016J\b\u0010X\u001a\u000203H\u0002J\u0010\u0010Y\u001a\u0002032\u0006\u0010Z\u001a\u00020\u000bH\u0002J\u0010\u0010[\u001a\u0002032\u0006\u0010\\\u001a\u00020\u0013H\u0002J\u0010\u0010]\u001a\u0002032\u0006\u0010^\u001a\u00020\u0013H\u0002J\u000e\u0010_\u001a\u0002032\u0006\u0010`\u001a\u00020\u0013J\u0010\u0010a\u001a\u0002032\u0006\u0010b\u001a\u00020>H\u0002J\u0010\u0010c\u001a\u0002032\u0006\u0010Z\u001a\u00020\u000bH\u0002J\b\u0010d\u001a\u000203H\u0002J\u0006\u0010e\u001a\u000203J\b\u0010f\u001a\u000203H\u0002J\u0006\u0010g\u001a\u000203J\u0012\u0010h\u001a\u0002032\b\u0010i\u001a\u0004\u0018\u00010AH\u0016J\b\u0010j\u001a\u000203H\u0014R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0014\u001a\u00060\u0015R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0019\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.¢\u0006\u0002\n\u0000R\u0014\u0010\u001d\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u0018R\u001b\u0010\u001f\u001a\u00020 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b!\u0010\"R\u0014\u0010%\u001a\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\u0018R\u0014\u0010'\u001a\u0004\u0018\u00010(8\u0004@\u0004X\u0085\u000e¢\u0006\u0002\n\u0000R\u0014\u0010)\u001a\b\u0012\u0004\u0012\u00020\u000b0*X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u0004\u0018\u00010,X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020.X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082.¢\u0006\u0002\n\u0000¨\u0006l"}, d2 = {"Lorg/catrobat/catroid/ui/ProjectUploadActivity;", "Lorg/catrobat/catroid/ui/BaseActivity;", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "Lorg/catrobat/catroid/transfers/CheckTokenTask$TokenCheckListener;", "Lorg/catrobat/catroid/transfers/GetTagsTask$TagResponseListener;", "Lorg/catrobat/catroid/transfers/project/ResultReceiverWrapperInterface;", "Lorg/catrobat/catroid/ui/controller/ProjectUploadController$ProjectUploadInterface;", "()V", "apiMatcher", "Ljava/util/regex/Matcher;", "backUpXml", "", "binding", "Lorg/catrobat/catroid/databinding/ActivityUploadBinding;", "dialogReplaceApiKeyBinding", "Lorg/catrobat/catroid/databinding/DialogReplaceApiKeyBinding;", "dialogUploadUnchangedProjectBinding", "Lorg/catrobat/catroid/databinding/DialogUploadUnchangedProjectBinding;", "enableNextButton", "", "nameInputTextWatcher", "Lorg/catrobat/catroid/ui/ProjectUploadActivity$NameInputTextWatcher;", "notesAndCredits", "getNotesAndCredits", "()Ljava/lang/String;", "notesAndCreditsScreen", "originalProjectName", "project", "Lorg/catrobat/catroid/content/Project;", Constants.EXTRA_PROJECT_DESCRIPTION, "getProjectDescription", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "projectName", "getProjectName", "projectUploadController", "Lorg/catrobat/catroid/ui/controller/ProjectUploadController;", "tags", "", "uploadProgressDialog", "Landroidx/appcompat/app/AlertDialog;", "uploadResultReceiver", "Lorg/catrobat/catroid/transfers/project/ResultReceiverWrapper;", "xml", "xmlFile", "Ljava/io/File;", "apiKeyFound", "", "checkApiPattern", "checkCodeForApiKey", "createProjectUploadController", "getContext", "getResultReceiverWrapper", "getTags", "loadBackup", "loadProjectActivity", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onCreateView", "onDestroy", "onLoadFinished", FirebaseAnalytics.Param.SUCCESS, "onNextButtonClick", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPrepareOptionsMenu", "onReceiveResult", "resultData", "onTagsReceived", "onTokenCheckComplete", "tokenValid", "connectionFailed", "reloadProject", "replaceSecret", "secret", "setNextButtonEnabled", CompilerOptions.ENABLED, "setScreen", "screen", "setShowProgressBar", "show", "setVisibility", "visibility", "showApiReplacementDialog", "showSelectTagsDialog", "showUploadDialog", "showUploadIsUnchangedDialog", "startSignInWorkflow", "startUploadService", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "verifyUserIdentity", "NameInputTextWatcher", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ProjectUploadActivity extends BaseActivity implements ProjectLoader.ProjectLoadListener, CheckTokenTask.TokenCheckListener, GetTagsTask.TagResponseListener, ResultReceiverWrapperInterface, ProjectUploadController.ProjectUploadInterface {
    private Matcher apiMatcher;
    private String backUpXml;
    private ActivityUploadBinding binding;
    private DialogReplaceApiKeyBinding dialogReplaceApiKeyBinding;
    private DialogUploadUnchangedProjectBinding dialogUploadUnchangedProjectBinding;
    private boolean notesAndCreditsScreen;
    private String originalProjectName;
    private Project project;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    protected ProjectUploadController projectUploadController;
    private List<String> tags;
    private AlertDialog uploadProgressDialog;
    private String xml;
    private File xmlFile;
    private final ResultReceiverWrapper uploadResultReceiver = new ResultReceiverWrapper(this, new Handler());
    private final NameInputTextWatcher nameInputTextWatcher = new NameInputTextWatcher();
    private boolean enableNextButton = true;

    @Override // org.catrobat.catroid.ui.controller.ProjectUploadController.ProjectUploadInterface
    public ProjectUploadActivity getContext() {
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ProjectUploadActivity() {
        final ProjectUploadActivity projectUploadActivity = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = projectUploadActivity;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
        this.tags = new ArrayList();
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUploadBinding activityUploadBindingInflate = ActivityUploadBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityUploadBindingInflate, "inflate(...)");
        this.binding = activityUploadBindingInflate;
        if (activityUploadBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBindingInflate = null;
        }
        LinearLayout root = activityUploadBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        setContentView(root);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(R.string.upload_project_dialog_title);
        }
        ActionBar supportActionBar2 = getSupportActionBar();
        if (supportActionBar2 != null) {
            supportActionBar2.setDisplayHomeAsUpEnabled(true);
        }
        this.notesAndCreditsScreen = false;
        setShowProgressBar(true);
        loadProjectActivity();
    }

    protected ProjectUploadController createProjectUploadController() {
        return new ProjectUploadController(this);
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        if (success) {
            loadProjectActivity();
            return;
        }
        ToastUtil.showError(this, R.string.error_load_project);
        setShowProgressBar(false);
        finish();
    }

    private final void loadProjectActivity() {
        getTags();
        Project currentProject = getProjectManager().getCurrentProject();
        Intrinsics.checkNotNullExpressionValue(currentProject, "getCurrentProject(...)");
        this.project = currentProject;
        this.projectUploadController = createProjectUploadController();
        verifyUserIdentity();
    }

    private final void onCreateView() throws Throwable {
        ProjectAndSceneScreenshotLoader projectAndSceneScreenshotLoader = new ProjectAndSceneScreenshotLoader(100, 100);
        Project project = this.project;
        Project project2 = null;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        String name2 = project.getDirectory().getName();
        Project project3 = this.project;
        if (project3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project3 = null;
        }
        projectAndSceneScreenshotLoader.loadAndShowScreenshot(name2, projectAndSceneScreenshotLoader.getScreenshotSceneName(project3.getDirectory()), false, (ImageView) findViewById(R.id.project_image_view));
        ActivityUploadBinding activityUploadBinding = this.binding;
        if (activityUploadBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding = null;
        }
        TextView textView = activityUploadBinding.projectSizeView;
        Project project4 = this.project;
        if (project4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project4 = null;
        }
        textView.setText(FileMetaDataExtractor.getSizeAsString(project4.getDirectory(), this));
        ProjectManager projectManager = getProjectManager();
        Project project5 = this.project;
        if (project5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project5 = null;
        }
        if (!projectManager.isChangedProject(project5)) {
            showUploadIsUnchangedDialog();
        }
        ActivityUploadBinding activityUploadBinding2 = this.binding;
        if (activityUploadBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding2 = null;
        }
        EditText editText = activityUploadBinding2.inputProjectName.getEditText();
        if (editText != null) {
            Project project6 = this.project;
            if (project6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
                project6 = null;
            }
            editText.setText(project6.getName());
        }
        ActivityUploadBinding activityUploadBinding3 = this.binding;
        if (activityUploadBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding3 = null;
        }
        EditText editText2 = activityUploadBinding3.inputProjectDescription.getEditText();
        if (editText2 != null) {
            Project project7 = this.project;
            if (project7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
                project7 = null;
            }
            editText2.setText(project7.getDescription());
        }
        ActivityUploadBinding activityUploadBinding4 = this.binding;
        if (activityUploadBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding4 = null;
        }
        EditText editText3 = activityUploadBinding4.inputProjectNotesAndCredits.getEditText();
        if (editText3 != null) {
            Project project8 = this.project;
            if (project8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
                project8 = null;
            }
            editText3.setText(project8.getNotesAndCredits());
        }
        ActivityUploadBinding activityUploadBinding5 = this.binding;
        if (activityUploadBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding5 = null;
        }
        EditText editText4 = activityUploadBinding5.inputProjectName.getEditText();
        if (editText4 != null) {
            editText4.addTextChangedListener(this.nameInputTextWatcher);
        }
        Project project9 = this.project;
        if (project9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
        } else {
            project2 = project9;
        }
        String name3 = project2.getName();
        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
        this.originalProjectName = name3;
        checkCodeForApiKey();
        setShowProgressBar(false);
        setNextButtonEnabled(true);
    }

    private final void showUploadIsUnchangedDialog() {
        DialogUploadUnchangedProjectBinding dialogUploadUnchangedProjectBindingInflate = DialogUploadUnchangedProjectBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(dialogUploadUnchangedProjectBindingInflate, "inflate(...)");
        this.dialogUploadUnchangedProjectBinding = dialogUploadUnchangedProjectBindingInflate;
        DialogUploadUnchangedProjectBinding dialogUploadUnchangedProjectBinding = null;
        if (dialogUploadUnchangedProjectBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialogUploadUnchangedProjectBinding");
            dialogUploadUnchangedProjectBindingInflate = null;
        }
        dialogUploadUnchangedProjectBindingInflate.unchangedUploadUrl.setMovementMethod(LinkMovementMethod.getInstance());
        String string = getString(R.string.unchanged_upload_url, new Object[]{"<a href='https://catrob.at/ltp'>https://catrob.at/ltp</a>"});
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        DialogUploadUnchangedProjectBinding dialogUploadUnchangedProjectBinding2 = this.dialogUploadUnchangedProjectBinding;
        if (dialogUploadUnchangedProjectBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialogUploadUnchangedProjectBinding");
            dialogUploadUnchangedProjectBinding2 = null;
        }
        dialogUploadUnchangedProjectBinding2.unchangedUploadUrl.setText(Html.fromHtml(string));
        AlertDialog.Builder title = new AlertDialog.Builder(this).setTitle(R.string.warning);
        DialogUploadUnchangedProjectBinding dialogUploadUnchangedProjectBinding3 = this.dialogUploadUnchangedProjectBinding;
        if (dialogUploadUnchangedProjectBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialogUploadUnchangedProjectBinding");
        } else {
            dialogUploadUnchangedProjectBinding = dialogUploadUnchangedProjectBinding3;
        }
        AlertDialog alertDialogCreate = title.setView(dialogUploadUnchangedProjectBinding.getRoot()).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showUploadIsUnchangedDialog$lambda$0(this.f$0, dialogInterface, i);
            }
        }).setCancelable(false).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        alertDialogCreate.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showUploadIsUnchangedDialog$lambda$0(ProjectUploadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        AlertDialog alertDialog;
        AlertDialog alertDialog2 = this.uploadProgressDialog;
        if (alertDialog2 != null && alertDialog2.isShowing() && (alertDialog = this.uploadProgressDialog) != null) {
            alertDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        menu.findItem(R.id.next).setEnabled(this.enableNextButton);
        return true;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        boolean z = this.notesAndCreditsScreen;
        if (z) {
            setScreen(z);
            this.notesAndCreditsScreen = false;
        } else {
            loadBackup();
            super.onBackPressed();
        }
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        int itemId = item.getItemId();
        if (itemId != 16908332) {
            if (itemId == R.id.next) {
                onNextButtonClick();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
        boolean z = this.notesAndCreditsScreen;
        if (z) {
            setScreen(z);
            this.notesAndCreditsScreen = false;
        }
        return super.onOptionsItemSelected(item);
    }

    public final void setShowProgressBar(boolean show) {
        findViewById(R.id.progress_bar).setVisibility(show ? 0 : 8);
        ActivityUploadBinding activityUploadBinding = this.binding;
        if (activityUploadBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding = null;
        }
        activityUploadBinding.uploadLayout.setVisibility(show ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setNextButtonEnabled(boolean enabled) {
        this.enableNextButton = enabled;
        invalidateOptionsMenu();
    }

    private final void onNextButtonClick() {
        Utils.hideStandardSystemKeyboard(this);
        if (!this.notesAndCreditsScreen) {
            ActivityUploadBinding activityUploadBinding = this.binding;
            ActivityUploadBinding activityUploadBinding2 = null;
            if (activityUploadBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityUploadBinding = null;
            }
            EditText editText = activityUploadBinding.inputProjectName.getEditText();
            String strValidateName = this.nameInputTextWatcher.validateName(StringsKt.trim((CharSequence) String.valueOf(editText != null ? editText.getText() : null)).toString());
            if (strValidateName != null) {
                ActivityUploadBinding activityUploadBinding3 = this.binding;
                if (activityUploadBinding3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("binding");
                } else {
                    activityUploadBinding2 = activityUploadBinding3;
                }
                activityUploadBinding2.inputProjectName.setError(strValidateName);
                return;
            }
            setScreen(this.notesAndCreditsScreen);
            this.notesAndCreditsScreen = true;
            return;
        }
        setNextButtonEnabled(false);
        setShowProgressBar(true);
        showSelectTagsDialog();
    }

    private final void checkCodeForApiKey() throws Throwable {
        Project project = this.project;
        String str = null;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        File file = new File(project.getDirectory(), Constants.CODE_XML_FILE_NAME);
        this.xmlFile = file;
        try {
            String str2 = Files.asCharSource(file, Charsets.UTF_8).read();
            Intrinsics.checkNotNullExpressionValue(str2, "read(...)");
            this.xml = str2;
            if (str2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("xml");
                str2 = null;
            }
            this.backUpXml = str2;
        } catch (IOException e) {
            Log.e(ProjectUploadActivityKt.TAG, Log.getStackTraceString(e));
        }
        String str3 = this.xml;
        if (str3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("xml");
        } else {
            str = str3;
        }
        if (StringsKt.findAnyOf$default(str, CollectionsKt.listOf((Object[]) new String[]{"WebRequestBrick", "BackgroundRequestBrick", "LookRequestBrick", "OpenUrlBrick"}), 0, false, 6, null) != null) {
            checkApiPattern();
        }
    }

    private final void checkApiPattern() {
        Pattern patternCompile = Pattern.compile("<value>.*?((?=[A-Za-z]+[0-9]|[0-9]+[A-Za-z])[A-Za-z0-9]{24,45})", 2);
        String str = this.xml;
        Matcher matcher = null;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("xml");
            str = null;
        }
        Matcher matcher2 = patternCompile.matcher(str);
        Intrinsics.checkNotNullExpressionValue(matcher2, "matcher(...)");
        this.apiMatcher = matcher2;
        if (matcher2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiMatcher");
            matcher2 = null;
        }
        if (matcher2.find()) {
            Matcher matcher3 = this.apiMatcher;
            if (matcher3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("apiMatcher");
            } else {
                matcher = matcher3;
            }
            Object objRequireNonNull = Objects.requireNonNull(matcher.group(1));
            Intrinsics.checkNotNullExpressionValue(objRequireNonNull, "requireNonNull(...)");
            showApiReplacementDialog((String) objRequireNonNull);
        }
    }

    private final void apiKeyFound() {
        Matcher matcher = this.apiMatcher;
        Matcher matcher2 = null;
        if (matcher == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiMatcher");
            matcher = null;
        }
        Matcher matcher3 = this.apiMatcher;
        if (matcher3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("apiMatcher");
            matcher3 = null;
        }
        if (matcher.find(matcher3.end())) {
            Matcher matcher4 = this.apiMatcher;
            if (matcher4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("apiMatcher");
            } else {
                matcher2 = matcher4;
            }
            Object objRequireNonNull = Objects.requireNonNull(matcher2.group(1));
            Intrinsics.checkNotNullExpressionValue(objRequireNonNull, "requireNonNull(...)");
            showApiReplacementDialog((String) objRequireNonNull);
        }
    }

    private final void replaceSecret(String secret) {
        String str = this.xml;
        String str2 = null;
        if (str == null) {
            Intrinsics.throwUninitializedPropertyAccessException("xml");
            str = null;
        }
        Regex regex = new Regex(secret);
        String string = getString(R.string.api_replacement);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.xml = regex.replace(str, string);
        try {
            File file = this.xmlFile;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("xmlFile");
                file = null;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String str3 = this.xml;
            if (str3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("xml");
            } else {
                str2 = str3;
            }
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            byte[] bytes = str2.getBytes(UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(ProjectUploadActivityKt.TAG, Log.getStackTraceString(e));
        }
        reloadProject();
        apiKeyFound();
    }

    private final void reloadProject() {
        try {
            ProjectManager projectManager = getProjectManager();
            Project project = this.project;
            if (project == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
                project = null;
            }
            projectManager.loadProject(project.getDirectory());
            Project currentProject = getProjectManager().getCurrentProject();
            Intrinsics.checkNotNullExpressionValue(currentProject, "getCurrentProject(...)");
            this.project = currentProject;
        } catch (ProjectException e) {
            Log.e(ProjectUploadActivityKt.TAG, Log.getStackTraceString(e));
        }
    }

    private final void loadBackup() {
        String str;
        Project project = this.project;
        String str2 = null;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        String name2 = project.getName();
        String str3 = this.originalProjectName;
        if (str3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("originalProjectName");
            str3 = null;
        }
        if (!Intrinsics.areEqual(name2, str3)) {
            String str4 = this.originalProjectName;
            if (str4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("originalProjectName");
                str4 = null;
            }
            String str5 = "<programName>" + str4 + "</programName>";
            String str6 = "<programName>" + name2 + "</programName>";
            Project project2 = this.project;
            if (project2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
                project2 = null;
            }
            this.xmlFile = new File(project2.getDirectory(), Constants.CODE_XML_FILE_NAME);
            String str7 = this.backUpXml;
            if (str7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backUpXml");
                str = null;
            } else {
                str = str7;
            }
            this.backUpXml = StringsKt.replace$default(str, str5, str6, false, 4, (Object) null);
        }
        try {
            File file = this.xmlFile;
            if (file == null) {
                Intrinsics.throwUninitializedPropertyAccessException("xmlFile");
                file = null;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            String str8 = this.backUpXml;
            if (str8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("backUpXml");
            } else {
                str2 = str8;
            }
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            byte[] bytes = str2.getBytes(UTF_8);
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e(ProjectUploadActivityKt.TAG, Log.getStackTraceString(e));
        }
        reloadProject();
    }

    private final void showApiReplacementDialog(final String secret) {
        DialogReplaceApiKeyBinding dialogReplaceApiKeyBindingInflate = DialogReplaceApiKeyBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(dialogReplaceApiKeyBindingInflate, "inflate(...)");
        this.dialogReplaceApiKeyBinding = dialogReplaceApiKeyBindingInflate;
        DialogReplaceApiKeyBinding dialogReplaceApiKeyBinding = null;
        if (dialogReplaceApiKeyBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialogReplaceApiKeyBinding");
            dialogReplaceApiKeyBindingInflate = null;
        }
        dialogReplaceApiKeyBindingInflate.replaceApiKeyWarning.setMovementMethod(LinkMovementMethod.getInstance());
        String string = getString(R.string.api_replacement_dialog_warning, new Object[]{"<a href='https://catrob.at/webbricks'>https://catrob.at/webbricks</a>"});
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        DialogReplaceApiKeyBinding dialogReplaceApiKeyBinding2 = this.dialogReplaceApiKeyBinding;
        if (dialogReplaceApiKeyBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialogReplaceApiKeyBinding");
            dialogReplaceApiKeyBinding2 = null;
        }
        dialogReplaceApiKeyBinding2.replaceApiKeyWarning.setText(Html.fromHtml(string));
        AlertDialog.Builder title = new AlertDialog.Builder(this).setTitle(R.string.warning);
        DialogReplaceApiKeyBinding dialogReplaceApiKeyBinding3 = this.dialogReplaceApiKeyBinding;
        if (dialogReplaceApiKeyBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialogReplaceApiKeyBinding");
            dialogReplaceApiKeyBinding3 = null;
        }
        AlertDialog alertDialogCreate = title.setView(dialogReplaceApiKeyBinding3.getRoot()).setPositiveButton(getString(R.string.api_replacement_dialog_accept), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showApiReplacementDialog$lambda$3(this.f$0, secret, dialogInterface, i);
            }
        }).setNegativeButton(getText(R.string.api_replacement_dialog_neutral), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showApiReplacementDialog$lambda$4(this.f$0, dialogInterface, i);
            }
        }).setNeutralButton(getText(R.string.cancel), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showApiReplacementDialog$lambda$5(this.f$0, dialogInterface, i);
            }
        }).setCancelable(false).create();
        Intrinsics.checkNotNullExpressionValue(alertDialogCreate, "create(...)");
        alertDialogCreate.show();
        DialogReplaceApiKeyBinding dialogReplaceApiKeyBinding4 = this.dialogReplaceApiKeyBinding;
        if (dialogReplaceApiKeyBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("dialogReplaceApiKeyBinding");
        } else {
            dialogReplaceApiKeyBinding = dialogReplaceApiKeyBinding4;
        }
        dialogReplaceApiKeyBinding.replaceApiKey.setText(secret);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showApiReplacementDialog$lambda$3(ProjectUploadActivity this$0, String secret, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(secret, "$secret");
        this$0.replaceSecret(secret);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showApiReplacementDialog$lambda$4(ProjectUploadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.apiKeyFound();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showApiReplacementDialog$lambda$5(ProjectUploadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.loadBackup();
        this$0.finish();
    }

    private final void showSelectTagsDialog() {
        final ArrayList arrayList = new ArrayList();
        final String[] strArr = (String[]) this.tags.toArray(new String[0]);
        new AlertDialog.Builder(this).setTitle(R.string.upload_tag_dialog_title).setMultiChoiceItems(strArr, (boolean[]) null, new DialogInterface.OnMultiChoiceClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnMultiChoiceClickListener
            public final void onClick(DialogInterface dialogInterface, int i, boolean z) {
                ProjectUploadActivity.showSelectTagsDialog$lambda$6(arrayList, this, strArr, dialogInterface, i, z);
            }
        }).setPositiveButton(getText(R.string.next), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showSelectTagsDialog$lambda$7(this.f$0, arrayList, dialogInterface, i);
            }
        }).setNegativeButton(getText(R.string.cancel), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showSelectTagsDialog$lambda$8(this.f$0, dialogInterface, i);
            }
        }).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSelectTagsDialog$lambda$6(List checkedTags, ProjectUploadActivity this$0, String[] availableTags, DialogInterface dialog, int i, boolean z) {
        Intrinsics.checkNotNullParameter(checkedTags, "$checkedTags");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(availableTags, "$availableTags");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        if (z) {
            if (checkedTags.size() >= 3) {
                ToastUtil.showError(this$0.getContext(), R.string.upload_tags_maximum_error);
                ((AlertDialog) dialog).getListView().setItemChecked(i, false);
                return;
            } else {
                checkedTags.add(availableTags[i]);
                return;
            }
        }
        checkedTags.remove(availableTags[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSelectTagsDialog$lambda$7(ProjectUploadActivity this$0, List checkedTags, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(checkedTags, "$checkedTags");
        Project project = this$0.project;
        Project project2 = null;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        project.setTags(checkedTags);
        ProjectUploadController projectUploadController = this$0.projectUploadController;
        if (projectUploadController != null) {
            String projectName = this$0.getProjectName();
            String projectDescription = this$0.getProjectDescription();
            String notesAndCredits = this$0.getNotesAndCredits();
            Project project3 = this$0.project;
            if (project3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
            } else {
                project2 = project3;
            }
            projectUploadController.startUpload(projectName, projectDescription, notesAndCredits, project2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showSelectTagsDialog$lambda$8(ProjectUploadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Utils.invalidateLoginTokenIfUserRestricted(this$0);
        this$0.setShowProgressBar(false);
        this$0.setNextButtonEnabled(true);
    }

    private final void setScreen(boolean screen) {
        if (screen) {
            setVisibility(0);
        } else {
            setVisibility(8);
        }
        ActivityUploadBinding activityUploadBinding = this.binding;
        ActivityUploadBinding activityUploadBinding2 = null;
        if (activityUploadBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding = null;
        }
        activityUploadBinding.projectNotesAndCreditsExplanation.setVisibility(screen ? 8 : 0);
        ActivityUploadBinding activityUploadBinding3 = this.binding;
        if (activityUploadBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityUploadBinding2 = activityUploadBinding3;
        }
        activityUploadBinding2.inputProjectNotesAndCredits.setVisibility(screen ? 8 : 0);
    }

    private final void setVisibility(int visibility) {
        ActivityUploadBinding activityUploadBinding = this.binding;
        ActivityUploadBinding activityUploadBinding2 = null;
        if (activityUploadBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding = null;
        }
        activityUploadBinding.projectImageView.setVisibility(visibility);
        ActivityUploadBinding activityUploadBinding3 = this.binding;
        if (activityUploadBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding3 = null;
        }
        activityUploadBinding3.projectSize.setVisibility(visibility);
        ActivityUploadBinding activityUploadBinding4 = this.binding;
        if (activityUploadBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding4 = null;
        }
        activityUploadBinding4.projectSizeView.setVisibility(visibility);
        ActivityUploadBinding activityUploadBinding5 = this.binding;
        if (activityUploadBinding5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding5 = null;
        }
        activityUploadBinding5.inputProjectName.setVisibility(visibility);
        ActivityUploadBinding activityUploadBinding6 = this.binding;
        if (activityUploadBinding6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
        } else {
            activityUploadBinding2 = activityUploadBinding6;
        }
        activityUploadBinding2.inputProjectDescription.setVisibility(visibility);
    }

    private final String getProjectName() {
        ActivityUploadBinding activityUploadBinding = this.binding;
        Project project = null;
        if (activityUploadBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding = null;
        }
        EditText editText = activityUploadBinding.inputProjectName.getEditText();
        String strValueOf = String.valueOf(editText != null ? editText.getText() : null);
        int length = strValueOf.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare((int) strValueOf.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                }
                length--;
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        String string = strValueOf.subSequence(i, length + 1).toString();
        Project project2 = this.project;
        if (project2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project2 = null;
        }
        if (!Intrinsics.areEqual(project2.getName(), string)) {
            Project project3 = this.project;
            if (project3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("project");
            } else {
                project = project3;
            }
            File directory = project.getDirectory();
            Intrinsics.checkNotNullExpressionValue(directory, "getDirectory(...)");
            File fileRenameProject = ProjectRenamerKt.renameProject(directory, string);
            if (fileRenameProject == null) {
                Log.e(ProjectUploadActivityKt.TAG, "Creating renamed directory failed!");
                return string;
            }
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            ProjectLoaderKt.loadProject(fileRenameProject, applicationContext);
            Project currentProject = getProjectManager().getCurrentProject();
            Intrinsics.checkNotNullExpressionValue(currentProject, "getCurrentProject(...)");
            this.project = currentProject;
        }
        return string;
    }

    private final String getProjectDescription() {
        ActivityUploadBinding activityUploadBinding = this.binding;
        if (activityUploadBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding = null;
        }
        EditText editText = activityUploadBinding.inputProjectDescription.getEditText();
        String strValueOf = String.valueOf(editText != null ? editText.getText() : null);
        int length = strValueOf.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare((int) strValueOf.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                }
                length--;
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        return strValueOf.subSequence(i, length + 1).toString();
    }

    private final String getNotesAndCredits() {
        ActivityUploadBinding activityUploadBinding = this.binding;
        if (activityUploadBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("binding");
            activityUploadBinding = null;
        }
        EditText editText = activityUploadBinding.inputProjectNotesAndCredits.getEditText();
        String strValueOf = String.valueOf(editText != null ? editText.getText() : null);
        int length = strValueOf.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = Intrinsics.compare((int) strValueOf.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                }
                length--;
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        return strValueOf.subSequence(i, length + 1).toString();
    }

    public final void showUploadDialog() {
        Survey survey;
        if (MainMenuActivity.surveyCampaign != null && (survey = MainMenuActivity.surveyCampaign) != null) {
            survey.setUploadFlag(true);
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(this).setTitle(getString(R.string.upload_project_dialog_title)).setView(R.layout.dialog_upload_project_progress).setPositiveButton(R.string.progress_upload_dialog_show_program, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda5
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showUploadDialog$lambda$12(this.f$0, dialogInterface, i);
            }
        }).setNegativeButton(R.string.done, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda6
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                ProjectUploadActivity.showUploadDialog$lambda$13(this.f$0, dialogInterface, i);
            }
        }).setCancelable(false).create();
        this.uploadProgressDialog = alertDialogCreate;
        if (alertDialogCreate != null) {
            alertDialogCreate.show();
        }
        AlertDialog alertDialog = this.uploadProgressDialog;
        Button button = alertDialog != null ? alertDialog.getButton(-1) : null;
        if (button == null) {
            return;
        }
        button.setEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showUploadDialog$lambda$12(ProjectUploadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.loadBackup();
        ProjectManager projectManager = this$0.getProjectManager();
        Project project = this$0.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        projectManager.resetChangedFlag(project);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showUploadDialog$lambda$13(ProjectUploadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.loadBackup();
        ProjectManager projectManager = this$0.getProjectManager();
        Project project = this$0.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        projectManager.resetChangedFlag(project);
        Survey survey = MainMenuActivity.surveyCampaign;
        if (survey != null) {
            survey.showSurvey(this$0);
        }
        this$0.finish();
    }

    @Override // org.catrobat.catroid.ui.controller.ProjectUploadController.ProjectUploadInterface
    /* JADX INFO: renamed from: getResultReceiverWrapper, reason: from getter */
    public ResultReceiverWrapper getUploadResultReceiver() {
        return this.uploadResultReceiver;
    }

    @Override // org.catrobat.catroid.ui.controller.ProjectUploadController.ProjectUploadInterface
    public void startUploadService(Intent intent) {
        showUploadDialog();
        startService(intent);
    }

    @Override // org.catrobat.catroid.transfers.project.ResultReceiverWrapperInterface
    public void onReceiveResult(int resultCode, Bundle resultData) {
        AlertDialog alertDialog;
        if (resultCode != 1 || resultData == null || ((alertDialog = this.uploadProgressDialog) != null && !alertDialog.isShowing())) {
            AlertDialog alertDialog2 = this.uploadProgressDialog;
            View viewFindViewById = alertDialog2 != null ? alertDialog2.findViewById(R.id.dialog_upload_progress_progressbar) : null;
            if (viewFindViewById != null) {
                viewFindViewById.setVisibility(8);
            }
            AlertDialog alertDialog3 = this.uploadProgressDialog;
            View viewFindViewById2 = alertDialog3 != null ? alertDialog3.findViewById(R.id.dialog_upload_message_failed) : null;
            if (viewFindViewById2 != null) {
                viewFindViewById2.setVisibility(0);
            }
            AlertDialog alertDialog4 = this.uploadProgressDialog;
            ImageView imageView = alertDialog4 != null ? (ImageView) alertDialog4.findViewById(R.id.dialog_upload_progress_image) : null;
            if (imageView != null) {
                imageView.setImageResource(R.drawable.ic_upload_failed);
            }
            if (imageView == null) {
                return;
            }
            imageView.setVisibility(0);
            return;
        }
        final String string = resultData.getString(Constants.EXTRA_PROJECT_ID);
        AlertDialog alertDialog5 = this.uploadProgressDialog;
        Button button = alertDialog5 != null ? alertDialog5.getButton(-1) : null;
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ProjectUploadActivity.onReceiveResult$lambda$14(string, this, view);
                }
            });
        }
        if (button != null) {
            button.setEnabled(true);
        }
        AlertDialog alertDialog6 = this.uploadProgressDialog;
        View viewFindViewById3 = alertDialog6 != null ? alertDialog6.findViewById(R.id.dialog_upload_progress_progressbar) : null;
        if (viewFindViewById3 != null) {
            viewFindViewById3.setVisibility(8);
        }
        AlertDialog alertDialog7 = this.uploadProgressDialog;
        ImageView imageView2 = alertDialog7 != null ? (ImageView) alertDialog7.findViewById(R.id.dialog_upload_progress_image) : null;
        if (imageView2 != null) {
            imageView2.setImageResource(R.drawable.ic_upload_success);
        }
        if (imageView2 != null) {
            imageView2.setVisibility(0);
        }
        ProjectUploadActivity projectUploadActivity = this;
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(projectUploadActivity);
        int i = defaultSharedPreferences.getInt(ProjectUploadActivityKt.NUMBER_OF_UPLOADED_PROJECTS, 0) + 1;
        defaultSharedPreferences.edit().putInt(ProjectUploadActivityKt.NUMBER_OF_UPLOADED_PROJECTS, i).apply();
        if (i != 2) {
            return;
        }
        new AlertDialog.Builder(projectUploadActivity).setTitle(getString(R.string.rating_dialog_title)).setView(R.layout.dialog_rate_pocketcode).setPositiveButton(R.string.rating_dialog_rate_now, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda3
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                ProjectUploadActivity.onReceiveResult$lambda$15(this.f$0, dialogInterface, i2);
            }
        }).setNeutralButton(getString(R.string.rating_dialog_rate_later), new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.ProjectUploadActivity$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                ProjectUploadActivity.onReceiveResult$lambda$16(defaultSharedPreferences, dialogInterface, i2);
            }
        }).setNegativeButton(getString(R.string.rating_dialog_rate_never), (DialogInterface.OnClickListener) null).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onReceiveResult$lambda$14(String str, ProjectUploadActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String str2 = Constants.SHARE_PROJECT_URL + str;
        Intent intent = new Intent(this$0, (Class<?>) WebViewActivity.class);
        intent.putExtra("url", str2);
        this$0.startActivity(intent);
        this$0.loadBackup();
        ProjectManager projectManager = this$0.getProjectManager();
        Project project = this$0.project;
        if (project == null) {
            Intrinsics.throwUninitializedPropertyAccessException("project");
            project = null;
        }
        projectManager.resetChangedFlag(project);
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onReceiveResult$lambda$15(ProjectUploadActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this$0.getPackageName())).addFlags(268435456));
        } catch (ActivityNotFoundException e) {
            Log.e(ProjectUploadActivityKt.TAG, "onReceiveResult: ", e);
            this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Constants.PLAY_STORE_PAGE_LINK + this$0.getPackageName())));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onReceiveResult$lambda$16(SharedPreferences sharedPreferences, DialogInterface dialogInterface, int i) {
        sharedPreferences.edit().putInt(ProjectUploadActivityKt.NUMBER_OF_UPLOADED_PROJECTS, 0).apply();
    }

    protected void verifyUserIdentity() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String string = defaultSharedPreferences.getString("token", Constants.NO_TOKEN);
        String string2 = defaultSharedPreferences.getString("username", Constants.NO_USERNAME);
        if (!Intrinsics.areEqual(string, Constants.NO_TOKEN) && string != null && string.length() == 32 && !Intrinsics.areEqual(string, ServerAuthenticationConstants.TOKEN_CODE_INVALID)) {
            new CheckTokenTask(this).execute(string, string2);
        } else {
            startSignInWorkflow();
        }
    }

    @Override // org.catrobat.catroid.transfers.CheckTokenTask.TokenCheckListener
    public void onTokenCheckComplete(boolean tokenValid, boolean connectionFailed) throws Throwable {
        if (connectionFailed) {
            if (!tokenValid) {
                ProjectUploadActivity projectUploadActivity = this;
                ToastUtil.showError(projectUploadActivity, R.string.error_session_expired);
                Utils.logoutUser(projectUploadActivity);
                startSignInWorkflow();
            } else {
                ToastUtil.showError(this, R.string.error_internet_connection);
                return;
            }
        } else if (!tokenValid) {
            startSignInWorkflow();
            return;
        }
        onCreateView();
    }

    public final void startSignInWorkflow() {
        startActivityForResult(new Intent(this, (Class<?>) SignInActivity.class), 42);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) throws Throwable {
        if (requestCode != 42) {
            super.onActivityResult(requestCode, resultCode, data);
        } else if (resultCode == -1) {
            onCreateView();
        } else {
            finish();
        }
    }

    private final void getTags() {
        GetTagsTask getTagsTask = new GetTagsTask();
        getTagsTask.setOnTagsResponseListener(this);
        getTagsTask.execute(new String[0]);
    }

    @Override // org.catrobat.catroid.transfers.GetTagsTask.TagResponseListener
    public void onTagsReceived(List<String> tags) {
        Intrinsics.checkNotNullParameter(tags, "tags");
        this.tags = tags;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ProjectUploadActivity$NameInputTextWatcher.class
      classes14.dex
     */
    /* JADX INFO: compiled from: ProjectUploadActivity.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J(\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0016J(\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0010¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/ui/ProjectUploadActivity$NameInputTextWatcher;", "Landroid/text/TextWatcher;", "(Lorg/catrobat/catroid/ui/ProjectUploadActivity;)V", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "", "count", "after", "onTextChanged", "before", "validateName", "", "name", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class NameInputTextWatcher implements TextWatcher {
        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Intrinsics.checkNotNullParameter(s, "s");
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Intrinsics.checkNotNullParameter(s, "s");
        }

        public NameInputTextWatcher() {
        }

        public final String validateName(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            String str = name2;
            if (str.length() == 0) {
                return ProjectUploadActivity.this.getString(R.string.name_empty);
            }
            int length = str.length() - 1;
            int i = 0;
            boolean z = false;
            while (i <= length) {
                boolean z2 = Intrinsics.compare((int) str.charAt(!z ? i : length), 32) <= 0;
                if (z) {
                    if (!z2) {
                        break;
                    }
                    length--;
                } else if (z2) {
                    i++;
                } else {
                    z = true;
                }
            }
            String string = str.subSequence(i, length + 1).toString();
            if (string.length() == 0) {
                return ProjectUploadActivity.this.getString(R.string.name_consists_of_spaces_only);
            }
            if (!Intrinsics.areEqual(string, ProjectUploadActivity.this.getString(R.string.default_project_name))) {
                Project project = ProjectUploadActivity.this.project;
                if (project == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("project");
                    project = null;
                }
                if (Intrinsics.areEqual(string, project.getName()) || !FileMetaDataExtractor.getProjectNames(FlavoredConstants.DEFAULT_ROOT_DIRECTORY).contains(string)) {
                    return null;
                }
                return ProjectUploadActivity.this.getString(R.string.name_already_exists);
            }
            return ProjectUploadActivity.this.getString(R.string.error_upload_project_with_default_name, new Object[]{string});
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable s) {
            Intrinsics.checkNotNullParameter(s, "s");
            String strValidateName = validateName(s.toString());
            ActivityUploadBinding activityUploadBinding = ProjectUploadActivity.this.binding;
            if (activityUploadBinding == null) {
                Intrinsics.throwUninitializedPropertyAccessException("binding");
                activityUploadBinding = null;
            }
            activityUploadBinding.inputProjectName.setError(strValidateName);
            ProjectUploadActivity.this.setNextButtonEnabled(strValidateName == null);
        }
    }
}
