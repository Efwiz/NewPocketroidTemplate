package org.catrobat.catroid.ui;

import android.content.ActivityNotFoundException;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwnerKt;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.concurrent.CancellationException;
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
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.random.Random;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.ai.KoveManager;
import org.catrobat.catroid.cast.CastManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.common.FlavoredConstants;
import org.catrobat.catroid.common.SharedPreferenceKeys;
import org.catrobat.catroid.common.Survey;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.databinding.ActivityLoadingBinding;
import org.catrobat.catroid.databinding.ActivityMainMenuBinding;
import org.catrobat.catroid.databinding.ActivityMainMenuSplashscreenBinding;
import org.catrobat.catroid.databinding.DeclinedTermsOfUseAndServiceAlertViewBinding;
import org.catrobat.catroid.databinding.PrivacyPolicyViewBinding;
import org.catrobat.catroid.databinding.ProgressBarBinding;
import org.catrobat.catroid.io.ZipArchiver;
import org.catrobat.catroid.io.asynctask.ProjectLoader;
import org.catrobat.catroid.io.asynctask.ProjectSaver;
import org.catrobat.catroid.plugins.PluginEventBus;
import org.catrobat.catroid.python.PythonEngine;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.ui.dialogs.NewProjectDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.AboutDialogFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.MainMenuFragment;
import org.catrobat.catroid.ui.recyclerview.fragment.MainMenuPcFragment;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.catrobat.catroid.utils.ExtensionsKt;
import org.catrobat.catroid.utils.FileMetaDataExtractor;
import org.catrobat.catroid.utils.NativeLibraryManager;
import org.catrobat.catroid.utils.ScreenValueHandler;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.Utils;
import org.catrobat.catroid.utils.community.ModerationCheckWorker;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.koin.android.ext.android.ComponentCallbackExtKt;
import org.koin.core.qualifier.Qualifier;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MainMenuActivity.class
  classes14.dex
 */
/* JADX INFO: compiled from: MainMenuActivity.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 D2\u00020\u00012\u00020\u0002:\u0001DB\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0018\u001a\u00020\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0006\u0010#\u001a\u00020\u0019J\u0006\u0010$\u001a\u00020\u0019J\b\u0010%\u001a\u00020\u0019H\u0002J\b\u0010&\u001a\u00020\u0019H\u0002J\b\u0010'\u001a\u00020\u0019H\u0002J\b\u0010(\u001a\u00020\u0019H\u0002J\u0018\u0010)\u001a\u00020\u00192\u0006\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u001eH\u0002J\"\u0010,\u001a\u00020\u00192\u0006\u0010-\u001a\u00020\u00052\u0006\u0010.\u001a\u00020\u00052\b\u0010/\u001a\u0004\u0018\u000100H\u0014J\u0012\u00101\u001a\u00020\u00192\b\u00102\u001a\u0004\u0018\u000103H\u0014J\u0010\u00104\u001a\u00020 2\u0006\u00105\u001a\u000206H\u0016J\u0010\u00107\u001a\u00020\u00192\u0006\u00108\u001a\u00020 H\u0016J\u0010\u00109\u001a\u00020 2\u0006\u0010:\u001a\u00020;H\u0016J\b\u0010<\u001a\u00020\u0019H\u0016J\u0010\u0010=\u001a\u00020 2\u0006\u00105\u001a\u000206H\u0016J\b\u0010>\u001a\u00020\u0019H\u0014J\b\u0010?\u001a\u00020\u0019H\u0002J\u0010\u0010@\u001a\u00020\u00192\u0006\u0010A\u001a\u00020 H\u0002J\b\u0010B\u001a\u00020\u0019H\u0002J\b\u0010C\u001a\u00020\u0019H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.¢\u0006\u0002\n\u0000R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0017\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006E"}, d2 = {"Lorg/catrobat/catroid/ui/MainMenuActivity;", "Lorg/catrobat/catroid/ui/BaseCastActivity;", "Lorg/catrobat/catroid/io/asynctask/ProjectLoader$ProjectLoadListener;", "()V", "OVERLAY_PERMISSION_REQ_CODE", "", "declinedTermsOfUseViewBinding", "Lorg/catrobat/catroid/databinding/DeclinedTermsOfUseAndServiceAlertViewBinding;", "lastTapTime", "", "loadingBinding", "Lorg/catrobat/catroid/databinding/ActivityLoadingBinding;", "mainMenuBinding", "Lorg/catrobat/catroid/databinding/ActivityMainMenuBinding;", "oldPrivacyPolicy", "privacyPolicyBinding", "Lorg/catrobat/catroid/databinding/PrivacyPolicyViewBinding;", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "getProjectManager", "()Lorg/catrobat/catroid/ProjectManager;", "projectManager$delegate", "Lkotlin/Lazy;", "safeModeTapCounter", "checkOverlayPermission", "", "copyAssets", "assetPath", "", "destDir", "Ljava/io/File;", "dispatchKeyEvent", "", "event", "Landroid/view/KeyEvent;", "handleAgreedToPrivacyPolicyButton", "handleDeclinedPrivacyPolicyButton", "heavyInitialization", "loadContent", "loadFinalContent", "loadFragment", "mergeDirectoriesWithoutOverwrite", FirebaseAnalytics.Param.SOURCE, FirebaseAnalytics.Param.DESTINATION, "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "onLoadFinished", FirebaseAnalytics.Param.SUCCESS, "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "onPause", "onPrepareOptionsMenu", "onResume", "prepareStandaloneProject", "setShowProgressBar", "show", "showRandomFacts", "showTermsOfUseDialog", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MainMenuActivity extends BaseCastActivity implements ProjectLoader.ProjectLoadListener {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "MainMenuActivity";
    public static PythonEngine pythonEngine;
    public static Survey surveyCampaign;
    private final int OVERLAY_PERMISSION_REQ_CODE = XKeySym.XK_kana_ME;
    private DeclinedTermsOfUseAndServiceAlertViewBinding declinedTermsOfUseViewBinding;
    private long lastTapTime;
    private ActivityLoadingBinding loadingBinding;
    private ActivityMainMenuBinding mainMenuBinding;
    private int oldPrivacyPolicy;
    private PrivacyPolicyViewBinding privacyPolicyBinding;

    /* JADX INFO: renamed from: projectManager$delegate, reason: from kotlin metadata */
    private final Lazy projectManager;
    private int safeModeTapCounter;

    private final void showTermsOfUseDialog() {
    }

    @Override // android.app.Activity
    public boolean onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MainMenuActivity() {
        final MainMenuActivity mainMenuActivity = this;
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.NONE;
        final Qualifier qualifier = null;
        final Object[] objArr = 0 == true ? 1 : 0;
        this.projectManager = LazyKt.lazy(lazyThreadSafetyMode, (Function0) new Function0<ProjectManager>() { // from class: org.catrobat.catroid.ui.MainMenuActivity$special$$inlined$inject$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Object, org.catrobat.catroid.ProjectManager] */
            @Override // kotlin.jvm.functions.Function0
            public final ProjectManager invoke() {
                ComponentCallbacks componentCallbacks = mainMenuActivity;
                return ComponentCallbackExtKt.getKoin(componentCallbacks).get_scopeRegistry().getRootScope().get(Reflection.getOrCreateKotlinClass(ProjectManager.class), qualifier, objArr);
            }
        });
    }

    private final ProjectManager getProjectManager() {
        return (ProjectManager) this.projectManager.getValue();
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        String text$default;
        super.onCreate(savedInstanceState);
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: org.catrobat.catroid.ui.MainMenuActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                MainMenuActivity.onCreate$lambda$0(this.f$0, defaultUncaughtExceptionHandler, thread, th);
            }
        });
        SettingsFragment.setToChosenLanguage(this);
        MainMenuActivity mainMenuActivity = this;
        ModerationCheckWorker.INSTANCE.schedulePeriodicCheck(mainMenuActivity);
        File file = new File(getApplicationContext().getCacheDir(), "last_crash_log.txt");
        if (file.exists()) {
            try {
                text$default = FilesKt.readText$default(file, null, 1, null);
            } catch (Exception unused) {
                text$default = "";
            }
            file.delete();
            if (text$default.length() > 0) {
                new AlertDialog.Builder(mainMenuActivity).setTitle("Аварийное завершение / Crash Log").setMessage("В прошлый раз приложение закрылось с ошибкой. Пожалуйста, отправьте этот текст разработчику:\n\n" + text$default).setPositiveButton("OK", (DialogInterface.OnClickListener) null).show();
            }
        }
        ActivityMainMenuSplashscreenBinding activityMainMenuSplashscreenBindingInflate = ActivityMainMenuSplashscreenBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityMainMenuSplashscreenBindingInflate, "inflate(...)");
        setContentView(activityMainMenuSplashscreenBindingInflate.getRoot());
        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new AnonymousClass2(null), 3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(MainMenuActivity this$0, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Thread thread, Throwable th) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        try {
            StringWriter stringWriter = new StringWriter();
            th.printStackTrace(new PrintWriter(stringWriter));
            String string = stringWriter.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            FilesKt.writeText$default(new File(this$0.getApplicationContext().getCacheDir(), "last_crash_log.txt"), string, null, 2, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }

    /* JADX INFO: renamed from: org.catrobat.catroid.ui.MainMenuActivity$onCreate$2, reason: invalid class name */
    /* JADX INFO: compiled from: MainMenuActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.MainMenuActivity$onCreate$2", f = "MainMenuActivity.kt", i = {}, l = {147}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
    static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        int label;

        AnonymousClass2(Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return MainMenuActivity.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX INFO: renamed from: org.catrobat.catroid.ui.MainMenuActivity$onCreate$2$1, reason: invalid class name */
        /* JADX INFO: compiled from: MainMenuActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.MainMenuActivity$onCreate$2$1", f = "MainMenuActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainMenuActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(MainMenuActivity mainMenuActivity, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = mainMenuActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.heavyInitialization();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(MainMenuActivity.this, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                ResultKt.throwOnFailure(obj);
            }
            MainMenuActivity.this.loadFinalContent();
            return Unit.INSTANCE;
        }
    }

    private static final void onCreate$lambda$1(WaveLoadingView waveLoadingView, MainMenuActivity this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int[] iArr = new int[2];
        if (waveLoadingView != null) {
            waveLoadingView.getLocationOnScreen(iArr);
        }
        int[] iArr2 = new int[2];
        ActivityLoadingBinding activityLoadingBinding = this$0.loadingBinding;
        ActivityLoadingBinding activityLoadingBinding2 = null;
        if (activityLoadingBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding = null;
        }
        activityLoadingBinding.loadingLogo.getLocationOnScreen(iArr2);
        float f = iArr2[0];
        ActivityLoadingBinding activityLoadingBinding3 = this$0.loadingBinding;
        if (activityLoadingBinding3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding3 = null;
        }
        float width = (f + (activityLoadingBinding3.loadingLogo.getWidth() / 2.0f)) - iArr[0];
        float f2 = iArr2[1];
        ActivityLoadingBinding activityLoadingBinding4 = this$0.loadingBinding;
        if (activityLoadingBinding4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
        } else {
            activityLoadingBinding2 = activityLoadingBinding4;
        }
        float height = (f2 + (activityLoadingBinding2.loadingLogo.getHeight() / 2.0f)) - iArr[1];
        if (waveLoadingView != null) {
            waveLoadingView.explode(width, height, 45);
        }
    }

    private static final void onCreate$lambda$2(MainMenuActivity this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this$0.lastTapTime > 500) {
            this$0.safeModeTapCounter = 0;
        }
        this$0.lastTapTime = jCurrentTimeMillis;
        int i = this$0.safeModeTapCounter + 1;
        this$0.safeModeTapCounter = i;
        if (i >= 5) {
            MainMenuActivity mainMenuActivity = this$0;
            PreferenceManager.getDefaultSharedPreferences(mainMenuActivity).edit().putBoolean("force_safe_mode", true).apply();
            Toast.makeText(mainMenuActivity, "Safe Mode for next launch", 1).show();
            this$0.safeModeTapCounter = 0;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MainMenuActivity$onCreate$5.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.MainMenuActivity$onCreate$5, reason: invalid class name */
    /* JADX INFO: compiled from: MainMenuActivity.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.catrobat.catroid.ui.MainMenuActivity$onCreate$5", f = "MainMenuActivity.kt", i = {0}, l = {XKeySym.XK_ediaeresis, 244}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"factJob"}, s = {"L$0"})
    static final class AnonymousClass5 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        final /* synthetic */ long $startTime;
        final /* synthetic */ WaveLoadingView $waveView;
        final /* synthetic */ ViewGroup $windowContent;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ MainMenuActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass5(long j, WaveLoadingView waveLoadingView, MainMenuActivity mainMenuActivity, ViewGroup viewGroup, Continuation<? super AnonymousClass5> continuation) {
            super(2, continuation);
            this.$startTime = j;
            this.$waveView = waveLoadingView;
            this.this$0 = mainMenuActivity;
            this.$windowContent = viewGroup;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass5 anonymousClass5 = new AnonymousClass5(this.$startTime, this.$waveView, this.this$0, this.$windowContent, continuation);
            anonymousClass5.L$0 = obj;
            return anonymousClass5;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return ((AnonymousClass5) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code duplicated, block: B:20:0x008b  */
        /* JADX WARN: Code duplicated, block: B:21:0x009a  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            Job jobLaunch$default;
            final MainMenuActivity$onCreate$5$performExitTransition$1 mainMenuActivity$onCreate$5$performExitTransition$1;
            WaveLoadingView waveLoadingView;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                jobLaunch$default = BuildersKt__Builders_commonKt.launch$default((CoroutineScope) this.L$0, null, null, new MainMenuActivity$onCreate$5$factJob$1(this.this$0, null), 3, null);
                this.L$0 = jobLaunch$default;
                this.label = 1;
                if (BuildersKt.withContext(Dispatchers.getIO(), new AnonymousClass1(this.this$0, null), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i == 1) {
                    jobLaunch$default = (Job) this.L$0;
                    ResultKt.throwOnFailure(obj);
                } else {
                    if (i != 2) {
                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                    }
                    ResultKt.throwOnFailure(obj);
                }
                mainMenuActivity$onCreate$5$performExitTransition$1 = new MainMenuActivity$onCreate$5$performExitTransition$1(this.this$0, this.$windowContent);
                waveLoadingView = this.$waveView;
                if (waveLoadingView != null) {
                    this.this$0.loadFinalContent();
                    mainMenuActivity$onCreate$5$performExitTransition$1.invoke();
                } else {
                    final MainMenuActivity mainMenuActivity = this.this$0;
                    waveLoadingView.flatline(350L, new Function0<Unit>() { // from class: org.catrobat.catroid.ui.MainMenuActivity.onCreate.5.2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                            mainMenuActivity.loadFinalContent();
                            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(mainMenuActivity), null, null, new AnonymousClass1(mainMenuActivity$onCreate$5$performExitTransition$1, null), 3, null);
                        }

                        /* JADX WARN: Classes with same name are omitted, all sources:
                          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MainMenuActivity$onCreate$5$2$1.class
                          classes14.dex
                         */
                        /* JADX INFO: renamed from: org.catrobat.catroid.ui.MainMenuActivity$onCreate$5$2$1, reason: invalid class name */
                        /* JADX INFO: compiled from: MainMenuActivity.kt */
                        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                        @DebugMetadata(c = "org.catrobat.catroid.ui.MainMenuActivity$onCreate$5$2$1", f = "MainMenuActivity.kt", i = {}, l = {268}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
                        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                            final /* synthetic */ Function0<Unit> $performExitTransition;
                            int label;

                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            AnonymousClass1(Function0<Unit> function0, Continuation<? super AnonymousClass1> continuation) {
                                super(2, continuation);
                                this.$performExitTransition = function0;
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                                return new AnonymousClass1(this.$performExitTransition, continuation);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) throws Throwable {
                                Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                int i = this.label;
                                if (i == 0) {
                                    ResultKt.throwOnFailure(obj);
                                    this.label = 1;
                                    if (DelayKt.delay(150L, this) == coroutine_suspended) {
                                        return coroutine_suspended;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                                    }
                                    ResultKt.throwOnFailure(obj);
                                }
                                this.$performExitTransition.invoke();
                                return Unit.INSTANCE;
                            }
                        }
                    });
                }
                return Unit.INSTANCE;
            }
            Job.DefaultImpls.cancel$default(jobLaunch$default, (CancellationException) null, 1, (Object) null);
            long jCurrentTimeMillis = ((long) 1400) - (System.currentTimeMillis() - this.$startTime);
            if (jCurrentTimeMillis > 0) {
                this.L$0 = null;
                this.label = 2;
                if (DelayKt.delay(jCurrentTimeMillis, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            mainMenuActivity$onCreate$5$performExitTransition$1 = new MainMenuActivity$onCreate$5$performExitTransition$1(this.this$0, this.$windowContent);
            waveLoadingView = this.$waveView;
            if (waveLoadingView != null) {
                this.this$0.loadFinalContent();
                mainMenuActivity$onCreate$5$performExitTransition$1.invoke();
            } else {
                final MainMenuActivity mainMenuActivity2 = this.this$0;
                waveLoadingView.flatline(350L, new Function0<Unit>() { // from class: org.catrobat.catroid.ui.MainMenuActivity.onCreate.5.2
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        mainMenuActivity2.loadFinalContent();
                        BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(mainMenuActivity2), null, null, new AnonymousClass1(mainMenuActivity$onCreate$5$performExitTransition$1, null), 3, null);
                    }

                    /* JADX WARN: Classes with same name are omitted, all sources:
                      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MainMenuActivity$onCreate$5$2$1.class
                      classes14.dex
                     */
                    /* JADX INFO: renamed from: org.catrobat.catroid.ui.MainMenuActivity$onCreate$5$2$1, reason: invalid class name */
                    /* JADX INFO: compiled from: MainMenuActivity.kt */
                    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
                    @DebugMetadata(c = "org.catrobat.catroid.ui.MainMenuActivity$onCreate$5$2$1", f = "MainMenuActivity.kt", i = {}, l = {268}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
                    static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
                        final /* synthetic */ Function0<Unit> $performExitTransition;
                        int label;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        AnonymousClass1(Function0<Unit> function0, Continuation<? super AnonymousClass1> continuation) {
                            super(2, continuation);
                            this.$performExitTransition = function0;
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                            return new AnonymousClass1(this.$performExitTransition, continuation);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
                        }

                        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                        public final Object invokeSuspend(Object obj) throws Throwable {
                            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
                            int i = this.label;
                            if (i == 0) {
                                ResultKt.throwOnFailure(obj);
                                this.label = 1;
                                if (DelayKt.delay(150L, this) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            this.$performExitTransition.invoke();
                            return Unit.INSTANCE;
                        }
                    }
                });
            }
            return Unit.INSTANCE;
        }

        /* JADX WARN: Classes with same name are omitted, all sources:
          NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MainMenuActivity$onCreate$5$1.class
          classes14.dex
         */
        /* JADX INFO: renamed from: org.catrobat.catroid.ui.MainMenuActivity$onCreate$5$1, reason: invalid class name */
        /* JADX INFO: compiled from: MainMenuActivity.kt */
        @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlinx/coroutines/CoroutineScope;"}, k = 3, mv = {1, 9, 0}, xi = 48)
        @DebugMetadata(c = "org.catrobat.catroid.ui.MainMenuActivity$onCreate$5$1", f = "MainMenuActivity.kt", i = {}, l = {}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
        static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            int label;
            final /* synthetic */ MainMenuActivity this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(MainMenuActivity mainMenuActivity, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.this$0 = mainMenuActivity;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                return new AnonymousClass1(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label == 0) {
                    ResultKt.throwOnFailure(obj);
                    this.this$0.heavyInitialization();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void showRandomFacts() {
        String[] stringArray = getResources().getStringArray(R.array.loading_facts);
        Intrinsics.checkNotNullExpressionValue(stringArray, "getStringArray(...)");
        String str = stringArray[Random.INSTANCE.nextInt(stringArray.length)];
        ActivityLoadingBinding activityLoadingBinding = this.loadingBinding;
        if (activityLoadingBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("loadingBinding");
            activityLoadingBinding = null;
        }
        activityLoadingBinding.factTextView.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void heavyInitialization() {
        NativeLibraryManager.INSTANCE.initialize();
        MainMenuActivity mainMenuActivity = this;
        PreferenceManager.setDefaultValues(mainMenuActivity, R.xml.preferences, true);
        PreferenceManager.setDefaultValues(mainMenuActivity, R.xml.nxt_preferences, true);
        PreferenceManager.setDefaultValues(mainMenuActivity, R.xml.ev3_preferences, true);
        ScreenValueHandler.updateScreenWidthAndHeight(mainMenuActivity);
        Context applicationContext = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        pythonEngine = new PythonEngine(applicationContext);
        Context applicationContext2 = getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext2, "getApplicationContext(...)");
        KoveManager.initIfEnabled(applicationContext2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void loadFinalContent() {
        prepareStandaloneProject();
        MainMenuActivity mainMenuActivity = this;
        int i = PreferenceManager.getDefaultSharedPreferences(mainMenuActivity).getInt(SharedPreferenceKeys.AGREED_TO_PRIVACY_POLICY_VERSION, 0);
        this.oldPrivacyPolicy = i;
        if (i != 1) {
            showTermsOfUseDialog();
        }
        Survey survey = new Survey(mainMenuActivity);
        surveyCampaign = survey;
        survey.showSurvey(mainMenuActivity);
        PluginEventBus.getInstance().dispatch("Activity.onShow", "MainMenuActivity");
    }

    public final void handleAgreedToPrivacyPolicyButton() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(SharedPreferenceKeys.AGREED_TO_PRIVACY_POLICY_VERSION, 1).apply();
        prepareStandaloneProject();
    }

    public final void handleDeclinedPrivacyPolicyButton() {
        DeclinedTermsOfUseAndServiceAlertViewBinding declinedTermsOfUseAndServiceAlertViewBindingInflate = DeclinedTermsOfUseAndServiceAlertViewBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(declinedTermsOfUseAndServiceAlertViewBindingInflate, "inflate(...)");
        this.declinedTermsOfUseViewBinding = declinedTermsOfUseAndServiceAlertViewBindingInflate;
        DeclinedTermsOfUseAndServiceAlertViewBinding declinedTermsOfUseAndServiceAlertViewBinding = null;
        if (declinedTermsOfUseAndServiceAlertViewBindingInflate == null) {
            Intrinsics.throwUninitializedPropertyAccessException("declinedTermsOfUseViewBinding");
            declinedTermsOfUseAndServiceAlertViewBindingInflate = null;
        }
        LinearLayout root = declinedTermsOfUseAndServiceAlertViewBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        String string = getString(R.string.about_link_template, new Object[]{Constants.BASE_APP_URL_HTTPS, getString(R.string.share_website_text)});
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        DeclinedTermsOfUseAndServiceAlertViewBinding declinedTermsOfUseAndServiceAlertViewBinding2 = this.declinedTermsOfUseViewBinding;
        if (declinedTermsOfUseAndServiceAlertViewBinding2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("declinedTermsOfUseViewBinding");
        } else {
            declinedTermsOfUseAndServiceAlertViewBinding = declinedTermsOfUseAndServiceAlertViewBinding2;
        }
        TextView shareWebsiteView = declinedTermsOfUseAndServiceAlertViewBinding.shareWebsiteView;
        Intrinsics.checkNotNullExpressionValue(shareWebsiteView, "shareWebsiteView");
        shareWebsiteView.setMovementMethod(LinkMovementMethod.getInstance());
        shareWebsiteView.setText(Html.fromHtml(string, 0));
        new AlertDialog.Builder(this).setView(root).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.ui.MainMenuActivity$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MainMenuActivity.handleDeclinedPrivacyPolicyButton$lambda$3(this.f$0, dialogInterface, i);
            }
        }).setCancelable(false).setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: org.catrobat.catroid.ui.MainMenuActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return MainMenuActivity.handleDeclinedPrivacyPolicyButton$lambda$4(this.f$0, dialogInterface, i, keyEvent);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void handleDeclinedPrivacyPolicyButton$lambda$3(MainMenuActivity this$0, DialogInterface dialogInterface, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.showTermsOfUseDialog();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean handleDeclinedPrivacyPolicyButton$lambda$4(MainMenuActivity this$0, DialogInterface dialog, int i, KeyEvent keyEvent) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(dialog, "dialog");
        if (i != 4) {
            return false;
        }
        dialog.cancel();
        this$0.showTermsOfUseDialog();
        return true;
    }

    private final void loadContent() {
        ActivityMainMenuSplashscreenBinding activityMainMenuSplashscreenBindingInflate = ActivityMainMenuSplashscreenBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(activityMainMenuSplashscreenBindingInflate, "inflate(...)");
        setContentView(activityMainMenuSplashscreenBindingInflate.getRoot());
        if (this.oldPrivacyPolicy == 1) {
            prepareStandaloneProject();
        }
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
            CastManager.getInstance().addCallback();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (event.getAction() == 1) {
            if (event.isCtrlPressed() && event.getKeyCode() == 42) {
                new NewProjectDialogFragment().show(getSupportFragmentManager(), NewProjectDialogFragment.INSTANCE.getTAG());
                return true;
            }
            if (event.getKeyCode() == 135) {
                if (getProjectManager().getCurrentProject() != null) {
                    StageActivity.handlePlayButton(getProjectManager(), this);
                } else {
                    Toast.makeText(this, "Нет активного проекта для запуска", 0).show();
                }
                return true;
            }
            if (event.isCtrlPressed() && event.getKeyCode() == 43) {
                startActivity(new Intent(this, (Class<?>) ProjectListActivity.class));
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private final void loadFragment() {
        Fragment mainMenuFragment;
        MainMenuActivity mainMenuActivity = this;
        if (PreferenceManager.getDefaultSharedPreferences(mainMenuActivity).getBoolean("pref_pc_mode_enabled", false)) {
            mainMenuFragment = new MainMenuPcFragment();
        } else {
            mainMenuFragment = new MainMenuFragment();
        }
        FragmentTransaction fragmentTransactionBeginTransaction = getSupportFragmentManager().beginTransaction();
        ActivityMainMenuBinding activityMainMenuBinding = this.mainMenuBinding;
        if (activityMainMenuBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainMenuBinding");
            activityMainMenuBinding = null;
        }
        fragmentTransactionBeginTransaction.replace(activityMainMenuBinding.fragmentContainer.getId(), mainMenuFragment, "MainMenuFragment").commitAllowingStateLoss();
        setShowProgressBar(false);
        Intent intent = getIntent();
        if (intent.getAction() == null || !Intrinsics.areEqual(intent.getAction(), "android.intent.action.VIEW") || intent.getData() == null) {
            return;
        }
        Uri data = intent.getData();
        Intent intent2 = new Intent(mainMenuActivity, (Class<?>) WebViewActivity.class);
        intent2.putExtra("url", String.valueOf(data));
        startActivity(intent2);
    }

    private final void setShowProgressBar(boolean show) {
        ProgressBarBinding progressBarBindingInflate = ProgressBarBinding.inflate(getLayoutInflater());
        Intrinsics.checkNotNullExpressionValue(progressBarBindingInflate, "inflate(...)");
        LinearLayout root = progressBarBindingInflate.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "getRoot(...)");
        ExtensionsKt.setVisibleOrGone(root, show);
        ActivityMainMenuBinding activityMainMenuBinding = this.mainMenuBinding;
        if (activityMainMenuBinding == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mainMenuBinding");
            activityMainMenuBinding = null;
        }
        FrameLayout fragmentContainer = activityMainMenuBinding.fragmentContainer;
        Intrinsics.checkNotNullExpressionValue(fragmentContainer, "fragmentContainer");
        ExtensionsKt.setVisibleOrGone(fragmentContainer, !show);
    }

    private final void checkOverlayPermission() {
        if (Settings.canDrawOverlays(this)) {
            return;
        }
        startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName())), this.OVERLAY_PERMISSION_REQ_CODE);
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (SettingsFragment.isCastSharedPreferenceEnabled(this)) {
            CastManager.getInstance().removeCallback();
        }
        Project currentProject = getProjectManager().getCurrentProject();
        if (currentProject != null) {
            Context applicationContext = getApplicationContext();
            Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
            ProjectSaver.saveProjectAsync$default(new ProjectSaver(currentProject, applicationContext), null, null, 3, null);
            Utils.setLastUsedProjectName(getApplicationContext(), currentProject.getName());
        }
    }

    @Override // org.catrobat.catroid.ui.BaseCastActivity, org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private final void copyAssets(String assetPath, File destDir) {
        InputStream inputStreamOpen;
        InputStream inputStream;
        FileOutputStream fileOutputStream;
        try {
            AssetManager assets = getAssets();
            String[] list = assets.list(assetPath);
            if (list != null && list.length != 0) {
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                Iterator it = ArrayIteratorKt.iterator(list);
                while (it.hasNext()) {
                    String str = (String) it.next();
                    String str2 = assetPath.length() == 0 ? str : assetPath + "/" + str;
                    File file = new File(destDir, str);
                    String[] list2 = assets.list(str2);
                    try {
                        try {
                            if (list2 != null) {
                                if (!(list2.length == 0)) {
                                    file.mkdirs();
                                    Intrinsics.checkNotNull(str2);
                                    copyAssets(str2, file);
                                }
                            }
                            Intrinsics.checkNotNull(inputStream);
                            ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                            CloseableKt.closeFinally(fileOutputStream, null);
                            CloseableKt.closeFinally(inputStreamOpen, null);
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(fileOutputStream, th);
                                throw th2;
                            }
                        }
                        inputStream = inputStreamOpen;
                        fileOutputStream = new FileOutputStream(file);
                    } catch (Throwable th3) {
                        try {
                            throw th3;
                        } catch (Throwable th4) {
                            CloseableKt.closeFinally(inputStreamOpen, th3);
                            throw th4;
                        }
                    }
                    inputStreamOpen = assets.open(str2);
                }
                return;
            }
            if (destDir.exists()) {
                return;
            }
            destDir.mkdirs();
        } catch (Exception e) {
            Log.e("PythonEngine", "FATAL ERROR in copyAssets for path: " + assetPath, e);
        }
    }

    @Override // org.catrobat.catroid.ui.BaseCastActivity, org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        switch (item.getItemId()) {
            case R.id.menu_about /* 2131364655 */:
                new AboutDialogFragment().show(getSupportFragmentManager(), AboutDialogFragment.INSTANCE.getTAG());
                return true;
            case R.id.menu_privacy_policy /* 2131364657 */:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(FlavoredConstants.PRIVACY_POLICY_URL)));
                return true;
            case R.id.menu_rate_app /* 2131364658 */:
                MainMenuActivity mainMenuActivity = this;
                if (!Utils.checkIsNetworkAvailableAndShowErrorMessage(mainMenuActivity)) {
                    return true;
                }
                try {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.rustore.ru/catalog/app/org.DanVexTeam.NewCatroid")));
                    return true;
                } catch (ActivityNotFoundException e) {
                    Log.e(TAG, "onOptionsItemSelected: ", e);
                    ToastUtil.showError(mainMenuActivity, R.string.main_menu_play_store_not_installed);
                    return true;
                }
            case R.id.settings /* 2131365206 */:
                startActivity(new Intent(this, (Class<?>) SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* JADX WARN: Code duplicated, block: B:71:0x01c7  */
    /* JADX WARN: Code duplicated, block: B:90:? A[RETURN, SYNTHETIC] */
    private final void prepareStandaloneProject() {
        long jLastModified;
        String str;
        int i;
        File file;
        File[] fileArr;
        MainMenuActivity mainMenuActivity = this;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mainMenuActivity);
        try {
            jLastModified = new File(getPackageCodePath()).lastModified();
        } catch (Exception unused) {
            jLastModified = 0;
        }
        long j = defaultSharedPreferences.getLong("last_unpacked_apk_time", -1L);
        File file2 = new File(FlavoredConstants.DEFAULT_ROOT_DIRECTORY, FileMetaDataExtractor.encodeSpecialCharsForFileSystem("project"));
        if (jLastModified > j || !file2.exists()) {
            Log.d("STANDALONE", "APK update detected or first launch (APK time: " + jLastModified + ", Last: " + j + "). Running selective update...");
            try {
                File file3 = new File(getCacheDir(), "standalone_temp_" + System.currentTimeMillis());
                InputStream inputStreamOpen = getAssets().open("project.zip");
                Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "open(...)");
                new ZipArchiver().unzip(inputStreamOpen, file3);
                Log.d("STANDALONE", "Unpacked new template assets to temporary directory: " + file3.getPath());
                try {
                    try {
                        if (file2.exists()) {
                            Log.d("STANDALONE", "Old project found. Updating assets and preserving user data...");
                            File[] fileArrListFiles = file2.listFiles();
                            if (fileArrListFiles != null) {
                                int length = fileArrListFiles.length;
                                int i2 = 0;
                                while (i2 < length) {
                                    File file4 = fileArrListFiles[i2];
                                    String name2 = file4.getName();
                                    if (name2 != null) {
                                        int iHashCode = name2.hashCode();
                                        fileArr = fileArrListFiles;
                                        if (iHashCode != -1795437803) {
                                            if (iHashCode != -981040457) {
                                                if (iHashCode == 97434231 && name2.equals("files")) {
                                                }
                                            } else if (!name2.equals(Constants.DEVICE_LIST_JSON_FILE_NAME)) {
                                            }
                                        } else if (name2.equals(Constants.DEVICE_VARIABLE_JSON_FILE_NAME)) {
                                        }
                                        i2++;
                                        fileArrListFiles = fileArr;
                                    } else {
                                        fileArr = fileArrListFiles;
                                    }
                                    Intrinsics.checkNotNull(file4);
                                    FilesKt.deleteRecursively(file4);
                                    i2++;
                                    fileArrListFiles = fileArr;
                                }
                            }
                            File[] fileArrListFiles2 = file3.listFiles();
                            if (fileArrListFiles2 != null) {
                                for (File file5 : fileArrListFiles2) {
                                    File file6 = new File(file2, file5.getName());
                                    if (Intrinsics.areEqual(file5.getName(), "files")) {
                                        Intrinsics.checkNotNull(file5);
                                        mergeDirectoriesWithoutOverwrite(file5, file6);
                                    } else {
                                        Intrinsics.checkNotNull(file5);
                                        FilesKt.copyRecursively$default(file5, file6, true, null, 4, null);
                                    }
                                }
                            }
                            FilesKt.deleteRecursively(file3);
                        } else {
                            Log.d("STANDALONE", "First install. Creating project directory...");
                            if (!file3.renameTo(file2)) {
                                str = "STANDALONE";
                                file = file2;
                                try {
                                    FilesKt.copyRecursively$default(file3, file2, true, null, 4, null);
                                    FilesKt.deleteRecursively(file3);
                                } catch (IOException e) {
                                    e = e;
                                    i = 1;
                                    Log.e(str, "Cannot unpack or update standalone project: ", e);
                                    if (file.exists()) {
                                        ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, mainMenuActivity).setListener(this), null, i, null);
                                        return;
                                    }
                                    return;
                                }
                            }
                            i = 1;
                            ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, this).setListener(this), null, 1, null);
                            defaultSharedPreferences.edit().putLong("last_unpacked_apk_time", jLastModified).apply();
                            Log.d(str, "Update complete. Saved APK timestamp: " + jLastModified);
                            return;
                        }
                        ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, this).setListener(this), null, 1, null);
                        defaultSharedPreferences.edit().putLong("last_unpacked_apk_time", jLastModified).apply();
                        Log.d(str, "Update complete. Saved APK timestamp: " + jLastModified);
                        return;
                    } catch (IOException e2) {
                        e = e2;
                        Log.e(str, "Cannot unpack or update standalone project: ", e);
                        if (file.exists()) {
                            ProjectLoader.loadProjectAsync$default(new ProjectLoader(file, mainMenuActivity).setListener(this), null, i, null);
                            return;
                        }
                        return;
                    }
                    str = "STANDALONE";
                    file = file2;
                    i = 1;
                } catch (IOException e3) {
                    e = e3;
                    str = "STANDALONE";
                    file = file2;
                }
            } catch (IOException e4) {
                e = e4;
                str = "STANDALONE";
                i = 1;
                file = file2;
            }
        } else {
            Log.d("STANDALONE", "Project is up to date (APK unchanged). Loading from storage.");
            ProjectLoader.loadProjectAsync$default(new ProjectLoader(file2, mainMenuActivity).setListener(this), null, 1, null);
        }
    }

    private final void mergeDirectoriesWithoutOverwrite(File source, File destination) {
        if (source.exists()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }
            File[] fileArrListFiles = source.listFiles();
            if (fileArrListFiles != null) {
                for (File file : fileArrListFiles) {
                    File file2 = new File(destination, file.getName());
                    if (file.isDirectory()) {
                        Intrinsics.checkNotNull(file);
                        mergeDirectoriesWithoutOverwrite(file, file2);
                    } else if (file2.exists()) {
                        Log.d("STANDALONE_MIGRATE", "Preserved user-modified file: " + file.getName());
                    } else {
                        Intrinsics.checkNotNull(file);
                        FilesKt.copyTo$default(file, file2, false, 0, 4, null);
                        Log.d("STANDALONE_MIGRATE", "Merged new default asset: " + file.getName());
                    }
                }
            }
        }
    }

    @Override // org.catrobat.catroid.io.asynctask.ProjectLoader.ProjectLoadListener
    public void onLoadFinished(boolean success) {
        if (success) {
            startActivityForResult(new Intent(this, (Class<?>) StageActivity.class), 101);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            finish();
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/MainMenuActivity$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: MainMenuActivity.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\f\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0011R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u0004\u0018\u00010\t8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/ui/MainMenuActivity$Companion;", "", "()V", "TAG", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getTAG", "()Ljava/lang/String;", "pythonEngine", "Lorg/catrobat/catroid/python/PythonEngine;", "surveyCampaign", "Lorg/catrobat/catroid/common/Survey;", "getCpuArchitecture", "toast", "", "text", "duration", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final String getTAG() {
            return MainMenuActivity.TAG;
        }

        public final void toast(String text, int duration) {
            Intrinsics.checkNotNullParameter(text, "text");
            Toast.makeText(CatroidApplication.getAppContext(), text, duration).show();
        }

        public final String getCpuArchitecture() {
            String[] strArr = Build.SUPPORTED_ABIS;
            if (strArr != null) {
                if (!(strArr.length == 0)) {
                    String str = strArr[0];
                    Intrinsics.checkNotNull(str);
                    return str;
                }
            }
            String str2 = Build.CPU_ABI;
            Intrinsics.checkNotNull(str2);
            return str2;
        }
    }
}
