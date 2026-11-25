package org.catrobat.catroid.stage;

import android.R;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.PixelCopy;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;
import androidx.test.espresso.idling.CountingIdlingResource;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidGraphics;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.gaurav.avnc.vnc.UserCredential;
import com.gaurav.avnc.vnc.VncClient;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.material.badge.BadgeDrawable;
import com.google.common.collect.Multimap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.bluetooth.base.BluetoothDeviceService;
import org.catrobat.catroid.camera.CameraManager;
import org.catrobat.catroid.common.CatroidService;
import org.catrobat.catroid.common.NativeViewBindingManager;
import org.catrobat.catroid.common.ScreenValues;
import org.catrobat.catroid.common.ServiceProvider;
import org.catrobat.catroid.content.BackPressedScript;
import org.catrobat.catroid.content.GlobalManager;
import org.catrobat.catroid.content.MyActivityManager;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.SafeKeyboardHeightProvider;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.RunJSAction;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.exceptions.ProjectException;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.io.StageAudioFocus;
import org.catrobat.catroid.nfc.NfcHandler;
import org.catrobat.catroid.stage.event.EventManager;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.ui.dialogs.StageDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.PlaySceneDialog;
import org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog;
import org.catrobat.catroid.ui.runtimepermissions.BrickResourcesToRuntimePermissions;
import org.catrobat.catroid.ui.runtimepermissions.PermissionAdaptingActivity;
import org.catrobat.catroid.ui.runtimepermissions.PermissionHandlingActivity;
import org.catrobat.catroid.ui.runtimepermissions.PermissionRequestActivityExtension;
import org.catrobat.catroid.ui.runtimepermissions.RequiresPermissionTask;
import org.catrobat.catroid.ui.workspace.WindowContainer;
import org.catrobat.catroid.utils.NativeBridge;
import org.catrobat.catroid.utils.NewCatroidNotificationManager;
import org.catrobat.catroid.utils.OverlayViewManager;
import org.catrobat.catroid.utils.ProjectSecurityChecker;
import org.catrobat.catroid.utils.Resolution;
import org.catrobat.catroid.utils.ScreenValueHandler;
import org.catrobat.catroid.utils.ToastUtil;
import org.catrobat.catroid.utils.VibrationManager;
import org.catrobat.catroid.utils.lunoscript.LunoScriptEngine;
import org.catrobat.catroid.utils.lunoscript.LunoValue;
import org.catrobat.catroid.virtualmachine.VirtualMachineManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.daemon.common.LoopbackNetworkInterface;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StageActivity extends AndroidApplication implements ContextProvider, PermissionHandlingActivity, PermissionAdaptingActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int BACK_PRESS_EXIT_TIMEOUT = 2000;
    public static final String DEFAULT_VM_NAME = "default_vm";
    private static final int EXPORT_FILE_REQUEST_CODE = 42;
    public static final String EXTRA_PROJECT_PATH = "EXTRA_PROJECT_PATH";
    private static final int FILE_CHOOSER_REQUEST_CODE = 1001;
    private static final int PERFORM_INTENT = 1;
    private static final String PREFS_KEY_SUPPRESS_WARNING = "suppress_security_warning_new";
    private static final String PREFS_NAME = "SecurityPreferences";
    public static final int REGISTER_INTENT = 0;
    public static final int REQUEST_START_STAGE = 101;
    public static final int SHOW_DIALOG = 2;
    public static final int SHOW_LONG_TOAST = 4;
    public static final int SHOW_TOAST = 3;
    public static final String STYLE_BACKGROUND_COLOR = "backgroundColor";
    public static final String STYLE_CORNER_RADIUS = "cornerRadius";
    public static final String STYLE_FONT_PATH = "fontPath";
    public static final String STYLE_HINT_TEXT = "hintText";
    public static final String STYLE_HINT_TEXT_COLOR = "hintTextColor";
    public static final String STYLE_INPUT_TYPE = "inputType";
    public static final String STYLE_IS_MULTI_LINE = "isMultiLine";
    public static final String STYLE_IS_PASSWORD = "isPassword";
    public static final String STYLE_MAX_LENGTH = "maxLength";
    public static final String STYLE_TEXT_ALIGNMENT = "textAlignment";
    public static final String STYLE_TEXT_COLOR = "textColor";
    public static final String STYLE_TEXT_SIZE = "textSize";
    public static final String TAG = "StageActivity";
    public static WeakReference<StageActivity> activeStageActivity;
    private static Handler mainThreadHandler;
    public static Handler messageHandler;
    private static NdefMessage nfcTagMessage;
    static int numberOfSpritesCloned;
    private FrameLayout activeNativeLayer;
    private FrameLayout backgroundLayout;
    BrickDialogManager brickDialogManager;
    private FrameLayout cameraContainer;
    CameraManager cameraManager;
    private FrameLayout foregroundLayout;
    private View gameView;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    private boolean resizePossible;
    private FrameLayout rootLayout;
    private String sourceFileToExportPath;
    StageAudioFocus stageAudioFocus;
    StageDialog stageDialog;
    public StageListener stageListener;
    public StageResourceHolder stageResourceHolder;
    public VibrationManager vibrationManager;
    private Pixmap vmPixmap;
    private Texture vmTexture;
    private ValueCallback<Uri[]> webViewFilePathCallback;
    private WindowContainer window;
    private static final List<IntentListener> intentListeners2 = new ArrayList();
    public static SparseArray<IntentListener> intentListeners = new SparseArray<>();
    public static Random randomGenerator = new Random();
    public static String pendingNotificationId = null;
    public static boolean isAppPaused = false;
    public static volatile boolean isKeyboardVisible = false;
    public static volatile int realKeyboardHeight = 0;
    private long backPressedTime = 0;
    AndroidApplicationConfiguration configuration = null;
    public CountingIdlingResource idlingResource = new CountingIdlingResource("StageActivity");
    private PermissionRequestActivityExtension permissionRequestActivityExtension = new PermissionRequestActivityExtension();
    private Map<String, View> dynamicViews = new HashMap();
    private Map<String, WebViewCallback> webViewCallbacks = new HashMap();
    public Map<String, VncClient> vncClients = new HashMap();
    public volatile boolean frameReadyToRender = false;
    private boolean newFrameAvailable = false;
    private float currentVmMouseX = 0.0f;
    private float currentVmMouseY = 0.0f;
    private boolean captureScheduled = false;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageActivity$IntentListener.class
      classes14.dex
     */
    public interface IntentListener {
        Intent getTargetIntent();

        boolean onIntentResult(int requestCode, int resultCode, Intent data);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageActivity$WebViewCallback.class
      classes14.dex
     */
    public interface WebViewCallback {
        void onJavaScriptMessage(String message);
    }

    private void handleBack() {
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, com.badlogic.gdx.backends.android.AndroidApplicationBase
    public Context getContext() {
        return this;
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, com.badlogic.gdx.Application
    public int getLogLevel() {
        return 0;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageActivity$WebAppInterface.class
      classes14.dex
     */
    public class WebAppInterface {
        private final String viewId;

        WebAppInterface(String viewId) {
            this.viewId = viewId;
        }

        @JavascriptInterface
        public void postMessage(final String message) {
            final WebViewCallback webViewCallback = (WebViewCallback) StageActivity.this.webViewCallbacks.get(this.viewId);
            if (webViewCallback != null) {
                StageActivity.runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$WebAppInterface$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        webViewCallback.onJavaScriptMessage(message);
                    }
                });
            }
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        int i;
        float f;
        String str;
        if (getIntent().hasExtra(EXTRA_PROJECT_PATH)) {
            File file = new File(getIntent().getStringExtra(EXTRA_PROJECT_PATH));
            File file2 = new File(file, "init.luno.txt");
            File file3 = new File(file, "init.bin");
            if (file2.exists() || file3.exists()) {
                try {
                    LunoScriptEngine lunoScriptEngine = new LunoScriptEngine(this, null);
                    lunoScriptEngine.getInterpreter().getGlobals().define("ROOT_PATH", new LunoValue.String(file.getAbsolutePath()));
                    if (file2.exists()) {
                        str = new String(Files.readAllBytes(file2.toPath()));
                    } else {
                        str = new String(Files.readAllBytes(file3.toPath()));
                    }
                    if (Build.VERSION.SDK_INT >= 29) {
                        lunoScriptEngine.execute(str);
                    }
                    if (ProjectManager.getInstance().getCurrentProject() != null) {
                        ProjectManager.getInstance().getCurrentProject().setDirectory(file);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Failed to load baked project via LunoScript", e);
                    Toast.makeText(this, "Error executing LunoScript: " + e.getMessage(), 1).show();
                    finish();
                    return;
                }
            } else if (file.exists() && file.isDirectory()) {
                try {
                    ProjectManager.getInstance().loadProject(file);
                } catch (ProjectException unused) {
                }
            }
        }
        super.onCreate(savedInstanceState);
        this.rootLayout = new FrameLayout(this);
        this.cameraContainer = new FrameLayout(this);
        this.backgroundLayout = new FrameLayout(this);
        this.foregroundLayout = new FrameLayout(this);
        StageLifeCycleController.stageCreate(this);
        activeStageActivity = new WeakReference<>(this);
        MyActivityManager.INSTANCE.setStage_activity(this);
        boolean z = this instanceof StageWorkspaceActivity;
        AndroidApplicationConfiguration androidApplicationConfiguration = new AndroidApplicationConfiguration();
        this.configuration = androidApplicationConfiguration;
        androidApplicationConfiguration.r = 8;
        this.configuration.g = 8;
        this.configuration.b = 8;
        this.configuration.a = 8;
        this.gameView = initializeForView(getApplicationListener(), this.configuration);
        if (ProjectManager.getInstance().getCurrentProject() != null) {
            calculateScreenSizes();
        }
        injectSafeKeyboardProvider();
        View view = this.gameView;
        if (view instanceof SurfaceView) {
            SurfaceView surfaceView = (SurfaceView) view;
            if (z) {
                surfaceView.getHolder().setFormat(-1);
            } else {
                surfaceView.getHolder().setFormat(-3);
            }
            surfaceView.setZOrderMediaOverlay(true);
        }
        this.rootLayout.addView(this.cameraContainer);
        this.rootLayout.addView(this.backgroundLayout);
        this.rootLayout.addView(this.gameView);
        this.rootLayout.addView(this.foregroundLayout);
        this.activeNativeLayer = this.foregroundLayout;
        if (z) {
            getWindow().setFormat(-3);
            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setBackgroundColor(0);
            WindowContainer windowContainer = new WindowContainer(this);
            this.window = windowContainer;
            windowContainer.initWindow("StageGame", "Игровой экран");
            this.window.contentFrame.addView(this.rootLayout, new FrameLayout.LayoutParams(-1, -1));
            float f2 = getResources().getDisplayMetrics().density;
            if (ProjectManager.getInstance().getCurrentProject().getXmlHeader().islandscapeMode()) {
                i = (int) (580.0f * f2);
                f = f2 * 360.0f;
            } else {
                i = (int) (360.0f * f2);
                f = f2 * 580.0f;
            }
            int i2 = (int) f;
            int i3 = getResources().getDisplayMetrics().widthPixels;
            int i4 = (getResources().getDisplayMetrics().heightPixels - i2) / 2;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i2);
            layoutParams.gravity = BadgeDrawable.TOP_START;
            layoutParams.leftMargin = (i3 - i) / 2;
            layoutParams.topMargin = i4;
            this.window.setLayoutParams(layoutParams);
            this.window.setOnCloseClickListener(new Function0() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda22
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return this.f$0.m5535lambda$onCreate$0$orgcatrobatcatroidstageStageActivity();
                }
            });
            frameLayout.addView(this.window);
            setContentView(frameLayout);
            updateStageSize(i, i2);
        } else {
            setContentView(this.rootLayout);
        }
        this.rootLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda27
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view2, WindowInsets windowInsets) {
                return StageActivity.lambda$onCreate$1(view2, windowInsets);
            }
        });
        GlobalManager.INSTANCE.setSaveScenes(true);
        GlobalManager.INSTANCE.setStopSounds(true);
        mainThreadHandler = new Handler(Looper.getMainLooper());
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (externalStoragePublicDirectory.canWrite() && NativeBridge.INSTANCE.isWorking()) {
            NativeBridge.INSTANCE.setCrashLogPath(new File(externalStoragePublicDirectory, "NewCatroid_CPP_CrashLog.txt").getAbsolutePath());
        }
        checkAndRequestPermissions();
        handleNotificationIntent(getIntent());
    }

    /* JADX INFO: renamed from: lambda$onCreate$0$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ Unit m5535lambda$onCreate$0$orgcatrobatcatroidstageStageActivity() {
        finish();
        return null;
    }

    static /* synthetic */ WindowInsets lambda$onCreate$1(View view, WindowInsets windowInsets) {
        int i;
        if (Build.VERSION.SDK_INT >= 30) {
            boolean zIsVisible = windowInsets.isVisible(WindowInsets.Type.ime());
            isKeyboardVisible = zIsVisible;
            if (zIsVisible && (i = windowInsets.getInsets(WindowInsets.Type.ime()).bottom) > 200) {
                realKeyboardHeight = i;
            }
        } else {
            int systemWindowInsetBottom = windowInsets.getSystemWindowInsetBottom();
            if (systemWindowInsetBottom > 200) {
                isKeyboardVisible = true;
                realKeyboardHeight = systemWindowInsetBottom;
            } else if (systemWindowInsetBottom < 150) {
                isKeyboardVisible = false;
            }
        }
        return view.onApplyWindowInsets(windowInsets);
    }

    private void handleNotificationIntent(Intent intent) {
        CharSequence charSequence;
        if (intent == null) {
            return;
        }
        if (intent.hasExtra("NOTIFICATION_CLICKED_ID")) {
            final String stringExtra = intent.getStringExtra("NOTIFICATION_CLICKED_ID");
            intent.removeExtra("NOTIFICATION_CLICKED_ID");
            if (getActiveStageListener() != null) {
                Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        StageActivity.getActiveStageListener().onNotificationClicked(stringExtra);
                    }
                });
            } else {
                pendingNotificationId = stringExtra;
            }
        }
        if (intent.hasExtra("NOTIFICATION_ACTION_ID")) {
            final String stringExtra2 = intent.getStringExtra("NOTIFICATION_ACTION_ID");
            String stringExtra3 = intent.getStringExtra("NOTIF_DRAFT_ID");
            int intExtra = intent.getIntExtra("NOTIF_ID", -1);
            boolean booleanExtra = intent.getBooleanExtra("AUTO_CLOSE", false);
            intent.removeExtra("NOTIFICATION_ACTION_ID");
            Bundle resultsFromIntent = RemoteInput.getResultsFromIntent(intent);
            if (resultsFromIntent != null && (charSequence = resultsFromIntent.getCharSequence("KEY_REPLY")) != null) {
                NewCatroidNotificationManager.INSTANCE.getSavedReplies().put(stringExtra2, charSequence.toString());
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
            if (!booleanExtra) {
                NewCatroidNotificationManager.NotificationState notificationState = NewCatroidNotificationManager.INSTANCE.getActiveDrafts().get(stringExtra3);
                if (notificationState != null) {
                    NewCatroidNotificationManager.INSTANCE.buildAndShow(this, notificationState);
                }
            } else if (intExtra != -1) {
                notificationManager.cancel(intExtra);
            }
            if (getActiveStageListener() != null) {
                Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        StageActivity.lambda$handleNotificationIntent$3(stringExtra2);
                    }
                });
            } else {
                pendingNotificationId = stringExtra2;
            }
        }
    }

    static /* synthetic */ void lambda$handleNotificationIntent$3(String str) {
        getActiveStageListener().onNotificationActionTriggered(str);
        getActiveStageListener().onNotificationClicked(str);
    }

    public void createAndRunVM(String memory, String cpuCores, String hdaPath, String cdromPath) {
        File file;
        File file2;
        StringBuilder sb = new StringBuilder("-m ");
        sb.append(memory);
        sb.append(" -smp ").append(cpuCores);
        sb.append(" -display vnc=0.0.0.0:0 -vga std");
        if (hdaPath != null && !hdaPath.isEmpty() && (file2 = ProjectManager.getInstance().getCurrentProject().getFile(hdaPath)) != null && file2.exists()) {
            sb.append(" -hda \"").append(file2.getAbsolutePath()).append("\"");
        }
        if (cdromPath != null && !cdromPath.isEmpty() && !cdromPath.equals("0") && (file = ProjectManager.getInstance().getCurrentProject().getFile(cdromPath)) != null && file.exists()) {
            sb.append(" -cdrom \"").append(file.getAbsolutePath()).append("\"");
        }
        VirtualMachineManager.INSTANCE.createVM(getApplicationContext(), DEFAULT_VM_NAME, sb.toString(), "", "");
    }

    public void moveVmMouseRelative(float dx, float dy, int buttonState) {
        this.currentVmMouseX += dx;
        this.currentVmMouseY += dy;
        StageListener stageListener = this.stageListener;
        if (stageListener != null) {
            float virtualWidth = stageListener.getVirtualWidth() / 2.0f;
            float virtualHeight = this.stageListener.getVirtualHeight() / 2.0f;
            if (this.currentVmMouseX > virtualWidth) {
                this.currentVmMouseX = virtualWidth;
            }
            float f = -virtualWidth;
            if (this.currentVmMouseX < f) {
                this.currentVmMouseX = f;
            }
            if (this.currentVmMouseY > virtualHeight) {
                this.currentVmMouseY = virtualHeight;
            }
            float f2 = -virtualHeight;
            if (this.currentVmMouseY < f2) {
                this.currentVmMouseY = f2;
            }
            sendVmMouseEvent(this.currentVmMouseX, this.currentVmMouseY, buttonState);
        }
    }

    public void resizeVmMonitor(int width, int height) {
        StageListener stageListener = this.stageListener;
        if (stageListener != null) {
            stageListener.setVmScreenSize(width, height);
        }
    }

    public int getKeysymByName(String keyName) {
        if (keyName == null) {
            return 0;
        }
        String strTrim = keyName.toLowerCase().trim();
        strTrim.hashCode();
        switch (strTrim) {
            case "num lock":
                return XKeySym.XK_Num_Lock;
            case "l shift":
            case "shift":
                return XKeySym.XK_Shift_L;
            case "arrow down":
            case "down":
                return XKeySym.XK_Down;
            case "arrow left":
            case "left":
                return XKeySym.XK_Left;
            case "caps lock":
                return XKeySym.XK_Caps_Lock;
            case "delete":
            case "del":
                return 65535;
            case "arrow up":
            case "up":
                return XKeySym.XK_Up;
            case "insert":
            case "ins":
                return XKeySym.XK_Insert;
            case "l ctrl":
            case "ctrl":
                return XKeySym.XK_Control_L;
            case "r ctrl":
                return XKeySym.XK_Control_R;
            case "r shift":
                return XKeySym.XK_Shift_R;
            case "page up":
            case "pgup":
                return 65365;
            case "prt scr":
            case "print screen":
                return XKeySym.XK_Print;
            case "f1":
                return XKeySym.XK_F1;
            case "f2":
                return XKeySym.XK_F2;
            case "f3":
                return XKeySym.XK_F3;
            case "f4":
                return XKeySym.XK_F4;
            case "f5":
                return XKeySym.XK_F5;
            case "f6":
                return XKeySym.XK_F6;
            case "f7":
                return XKeySym.XK_F7;
            case "f8":
                return XKeySym.XK_F8;
            case "f9":
                return XKeySym.XK_F9;
            case "alt":
            case "l alt":
                return XKeySym.XK_Alt_L;
            case "f10":
                return XKeySym.XK_F10;
            case "f11":
                return 65480;
            case "f12":
                return 65481;
            case "end":
                return XKeySym.XK_End;
            case "esc":
                return XKeySym.XK_Escape;
            case "tab":
                return XKeySym.XK_Tab;
            case "win":
            case "super":
            case "command":
                return XKeySym.XK_Super_L;
            case "home":
                return XKeySym.XK_Home;
            case "pgdn":
            case "page down":
                return 65366;
            case "break":
            case "pause":
                return 65299;
            case "enter":
                return XKeySym.XK_Return;
            case "r alt":
                return XKeySym.XK_Alt_R;
            case "right":
            case "arrow right":
                return XKeySym.XK_Right;
            case "space":
                return 32;
            case "backspace":
                return XKeySym.XK_BackSpace;
            case "scroll lock":
                return 65300;
            default:
                if (strTrim.length() == 1) {
                    return strTrim.charAt(0);
                }
                return 0;
        }
    }

    public void reloadWithNewProject(final String newProjectPath) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5538xe0fae9bc(newProjectPath);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$reloadWithNewProject$4$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5538xe0fae9bc(String str) {
        try {
            ProjectManager.getInstance().loadProject(new File(str));
            StageListener stageListener = this.stageListener;
            if (stageListener != null) {
                stageListener.reloadProject(this.stageDialog);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to load project for reload: " + str, e);
            Toast.makeText(this, "Error loading project: " + e.getMessage(), 1).show();
        }
    }

    public void stopVM() {
        VirtualMachineManager.INSTANCE.stopVM(DEFAULT_VM_NAME);
    }

    public void createHardDisk(String diskName, String diskSize) {
        if (diskName == null || diskName.isEmpty() || diskSize == null || diskSize.isEmpty()) {
            return;
        }
        String absolutePath = new File(getFilesDir(), "qemu_x86_64").getAbsolutePath();
        File filesDir = ProjectManager.getInstance().getCurrentProject().getFilesDir();
        if (!filesDir.exists()) {
            filesDir.mkdirs();
        }
        VirtualMachineManager.INSTANCE.createDiskIfNotExists(absolutePath, new File(filesDir, diskName).getAbsolutePath(), diskSize);
    }

    public void sendVmMouseEvent(float catroidX, float catroidY, int buttonState) {
        VncClient vncClient;
        StageListener stageListener;
        this.currentVmMouseX = catroidX;
        this.currentVmMouseY = catroidY;
        if (!VirtualMachineManager.INSTANCE.isWorking() || (vncClient = this.vncClients.get(DEFAULT_VM_NAME)) == null || (stageListener = this.stageListener) == null) {
            return;
        }
        float virtualWidth = stageListener.getVirtualWidth();
        float virtualHeight = this.stageListener.getVirtualHeight();
        int vmWidth = this.stageListener.getVmWidth();
        int vmHeight = this.stageListener.getVmHeight();
        vncClient.sendPointerEvent(Math.max(0, Math.min(vmWidth - 1, (int) (((catroidX + (virtualWidth / 2.0f)) / virtualWidth) * vmWidth))), Math.max(0, Math.min(vmHeight - 1, (int) ((((-catroidY) + (virtualHeight / 2.0f)) / virtualHeight) * vmHeight))), buttonState);
    }

    public void sendVmKeyEvent(int keysym, boolean isDown) {
        VncClient vncClient;
        if (VirtualMachineManager.INSTANCE.isWorking() && (vncClient = this.vncClients.get(DEFAULT_VM_NAME)) != null) {
            vncClient.sendKeyEvent(keysym, 0, isDown);
        }
    }

    public void setVmDisplayVisible(boolean visible) {
        if (this.stageListener != null) {
            attachVMScreen(DEFAULT_VM_NAME);
            this.stageListener.setVmDisplayVisible(visible);
        }
    }

    public void launchExportFilePicker(String sourcePath, String defaultName) {
        this.sourceFileToExportPath = sourcePath;
        Intent intent = new Intent("android.intent.action.CREATE_DOCUMENT");
        intent.addCategory("android.intent.category.OPENABLE");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.TITLE", defaultName);
        startActivityForResult(intent, 42);
    }

    public void attachVMScreen(final String viewId) {
        if (VirtualMachineManager.INSTANCE.isWorking()) {
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5525lambda$attachVMScreen$6$orgcatrobatcatroidstageStageActivity(viewId);
                }
            });
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageActivity$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.stage.StageActivity$1, reason: invalid class name */
    class AnonymousClass1 implements VncClient.Observer {
        @Override // com.gaurav.avnc.vnc.VncClient.Observer
        public void onGotXCutText(String text) {
        }

        @Override // com.gaurav.avnc.vnc.VncClient.Observer
        public void onPointerMoved(int x, int y) {
        }

        @Override // com.gaurav.avnc.vnc.VncClient.Observer
        public boolean onVerifyCertificate(X509Certificate certificate) {
            return true;
        }

        AnonymousClass1() {
        }

        @Override // com.gaurav.avnc.vnc.VncClient.Observer
        public String onPasswordRequired() {
            return "";
        }

        @Override // com.gaurav.avnc.vnc.VncClient.Observer
        public UserCredential onCredentialRequired() {
            return new UserCredential("", "");
        }

        @Override // com.gaurav.avnc.vnc.VncClient.Observer
        public void onFramebufferUpdated() {
            StageActivity.this.frameReadyToRender = true;
            if (StageActivity.this.captureScheduled || StageActivity.this.stageListener == null) {
                return;
            }
            StageActivity.this.captureScheduled = true;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5545xdcaefc58();
                }
            }, DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS);
        }

        /* JADX INFO: renamed from: lambda$onFramebufferUpdated$0$org-catrobat-catroid-stage-StageActivity$1, reason: not valid java name */
        /* synthetic */ void m5545xdcaefc58() {
            Log.i("VNC_CAPTURE", "Requesting VM Texture capture now...");
            StageActivity.this.stageListener.captureAndSaveVmTexture();
        }

        @Override // com.gaurav.avnc.vnc.VncClient.Observer
        public void onFramebufferSizeChanged(int width, int height) {
            Log.i(StageActivity.TAG, "VM screen size changed: " + width + "x" + height);
            if (StageActivity.this.stageListener != null) {
                StageActivity.this.stageListener.setVmScreenSize(width, height);
            }
        }
    }

    /* JADX INFO: renamed from: lambda$attachVMScreen$6$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5525lambda$attachVMScreen$6$orgcatrobatcatroidstageStageActivity(final String str) {
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        new Thread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda29
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5524lambda$attachVMScreen$5$orgcatrobatcatroidstageStageActivity(anonymousClass1, str);
            }
        }).start();
    }

    /* JADX INFO: renamed from: lambda$attachVMScreen$5$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5524lambda$attachVMScreen$5$orgcatrobatcatroidstageStageActivity(VncClient.Observer observer, String str) {
        try {
            Log.i(TAG, "Waiting for QEMU VNC server to start...");
            Thread.sleep(1500L);
            VncClient vncClient = new VncClient(observer);
            try {
                try {
                    this.vncClients.put(str, vncClient);
                    vncClient.configure(0, true, 5, false);
                    vncClient.connect(LoopbackNetworkInterface.IPV4_LOOPBACK_INET_ADDRESS, 5900);
                    while (vncClient.getConnected()) {
                        vncClient.processServerMessage();
                    }
                    VncClient vncClientRemove = this.vncClients.remove(str);
                    if (vncClientRemove != null) {
                        vncClientRemove.cleanup();
                    }
                    Log.i(TAG, "VNC Client thread finished for viewId: " + str);
                } catch (Exception e) {
                    String str2 = TAG;
                    Log.e(str2, "VNC Client thread failed", e);
                    VncClient vncClientRemove2 = this.vncClients.remove(str);
                    if (vncClientRemove2 != null) {
                        vncClientRemove2.cleanup();
                    }
                    Log.i(str2, "VNC Client thread finished for viewId: " + str);
                }
            } catch (Throwable th) {
                VncClient vncClientRemove3 = this.vncClients.remove(str);
                if (vncClientRemove3 != null) {
                    vncClientRemove3.cleanup();
                }
                Log.i(TAG, "VNC Client thread finished for viewId: " + str);
                throw th;
            }
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    public void sendVMMouseEvent(String viewId, int x, int y, int buttonMask) {
        VncClient vncClient = this.vncClients.get(viewId);
        if (vncClient != null) {
            vncClient.sendPointerEvent(x, y, buttonMask);
        }
    }

    public void sendVMKeyEvent(String viewId, int keysym, boolean isDown) {
        VncClient vncClient = this.vncClients.get(viewId);
        if (vncClient != null) {
            vncClient.sendKeyEvent(keysym, 0, isDown);
        }
    }

    /* JADX INFO: renamed from: lambda$setNativesBackground$7$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5542x197ab289() {
        this.activeNativeLayer = this.backgroundLayout;
    }

    public void setNativesBackground() {
        runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda32
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5542x197ab289();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setNativesForeground$8$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5543x22cff873() {
        this.activeNativeLayer = this.foregroundLayout;
    }

    public void setNativesForeground() {
        runOnMainThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5543x22cff873();
            }
        });
    }

    private void injectSafeKeyboardProvider() {
        try {
            Field declaredField = AndroidApplication.class.getDeclaredField("keyboardHeightProvider");
            declaredField.setAccessible(true);
            declaredField.set(this, new SafeKeyboardHeightProvider(this));
            Log.i(TAG, "Successfully injected SafeKeyboardHeightProvider via reflection.");
        } catch (Exception e) {
            Log.e(TAG, "Failed to inject SafeKeyboardHeightProvider via reflection. Keyboard-related crashes might occur.", e);
        }
    }

    public void createWebViewWithUrl(String viewId, String url, int x, int y, int width, int height) {
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(viewId), "Android");
        webView.setBackgroundColor(0);
        webView.setLayerType(2, null);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new AnonymousClass2());
        webView.loadUrl(url);
        webView.setVisibility(NativeViewBindingManager.getDefaultVisibility());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        addViewToStage(viewId, webView, layoutParams);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageActivity$2.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.stage.StageActivity$2, reason: invalid class name */
    class AnonymousClass2 extends WebChromeClient {
        AnonymousClass2() {
        }

        @Override // android.webkit.WebChromeClient
        public void onPermissionRequest(final PermissionRequest request) {
            StageActivity.this.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionRequest permissionRequest = request;
                    permissionRequest.grant(permissionRequest.getResources());
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (StageActivity.this.webViewFilePathCallback != null) {
                StageActivity.this.webViewFilePathCallback.onReceiveValue(null);
            }
            StageActivity.this.webViewFilePathCallback = filePathCallback;
            try {
                StageActivity.this.startActivityForResult(fileChooserParams.createIntent(), 1001);
                return true;
            } catch (ActivityNotFoundException unused) {
                StageActivity.this.webViewFilePathCallback = null;
                return false;
            }
        }
    }

    public void playVideo(final String viewId) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5537lambda$playVideo$9$orgcatrobatcatroidstageStageActivity(viewId);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$playVideo$9$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5537lambda$playVideo$9$orgcatrobatcatroidstageStageActivity(String str) {
        View view = this.dynamicViews.get(str);
        if (view instanceof VideoView) {
            ((VideoView) view).start();
        }
    }

    public void pauseVideo(final String viewId) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5536lambda$pauseVideo$10$orgcatrobatcatroidstageStageActivity(viewId);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$pauseVideo$10$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5536lambda$pauseVideo$10$orgcatrobatcatroidstageStageActivity(String str) {
        View view = this.dynamicViews.get(str);
        if (view instanceof VideoView) {
            VideoView videoView = (VideoView) view;
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        }
    }

    public void seekVideoTo(final String viewId, final int seconds) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda31
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5541lambda$seekVideoTo$11$orgcatrobatcatroidstageStageActivity(viewId, seconds);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$seekVideoTo$11$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5541lambda$seekVideoTo$11$orgcatrobatcatroidstageStageActivity(String str, int i) {
        View view = this.dynamicViews.get(str);
        if (view instanceof VideoView) {
            ((VideoView) view).seekTo(i * 1000);
        }
    }

    public float getVideoCurrentTime(final String viewId) {
        FutureTask futureTask = new FutureTask(new Callable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda28
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m5531x8ca5b6c0(viewId);
            }
        });
        runOnUiThread(futureTask);
        try {
            return ((Integer) futureTask.get(1L, TimeUnit.SECONDS)).intValue() / 1000.0f;
        } catch (Exception e) {
            Log.e("StageActivity", "Failed to get video time for " + viewId, e);
            return -1.0f;
        }
    }

    /* JADX INFO: renamed from: lambda$getVideoCurrentTime$12$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ Integer m5531x8ca5b6c0(String str) throws Exception {
        View view = this.dynamicViews.get(str);
        if (view instanceof VideoView) {
            return Integer.valueOf(((VideoView) view).getCurrentPosition());
        }
        return -1;
    }

    public boolean isVideoPlaying(final String viewId) {
        FutureTask futureTask = new FutureTask(new Callable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda18
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m5534xbc74bf55(viewId);
            }
        });
        runOnUiThread(futureTask);
        try {
            return ((Boolean) futureTask.get(1L, TimeUnit.SECONDS)).booleanValue();
        } catch (Exception e) {
            Log.e("StageActivity", "Failed to get video playing state for " + viewId, e);
            return false;
        }
    }

    /* JADX INFO: renamed from: lambda$isVideoPlaying$13$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ Boolean m5534xbc74bf55(String str) throws Exception {
        View view = this.dynamicViews.get(str);
        if (view instanceof VideoView) {
            return Boolean.valueOf(((VideoView) view).isPlaying());
        }
        return false;
    }

    public void createContainer(String viewId, int x, int y, int width, int height) {
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundColor(0);
        frameLayout.setVisibility(NativeViewBindingManager.getDefaultVisibility());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        addViewToStage(viewId, frameLayout, layoutParams);
    }

    public float getViewX(final String viewId) {
        View view = this.dynamicViews.get(viewId);
        if (view == null) {
            return -1.0f;
        }
        if (view.getLayoutParams() instanceof WindowManager.LayoutParams) {
            return ((WindowManager.LayoutParams) view.getLayoutParams()).x;
        }
        if (view.getParent() != null) {
            View view2 = (View) view.getParent();
            if (view2.getLayoutParams() instanceof WindowManager.LayoutParams) {
                return ((WindowManager.LayoutParams) view2.getLayoutParams()).x + view.getX();
            }
        }
        return view.getX();
    }

    public float getViewY(final String viewId) {
        Callable callable = new Callable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda19
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return this.f$0.m5532lambda$getViewY$14$orgcatrobatcatroidstageStageActivity(viewId);
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            try {
                return ((Float) callable.call()).floatValue();
            } catch (Exception unused) {
                return -1.0f;
            }
        }
        FutureTask futureTask = new FutureTask(callable);
        runOnUiThread(futureTask);
        try {
            return ((Float) futureTask.get(500L, TimeUnit.MILLISECONDS)).floatValue();
        } catch (Exception unused2) {
            return -1.0f;
        }
    }

    /* JADX INFO: renamed from: lambda$getViewY$14$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ Float m5532lambda$getViewY$14$orgcatrobatcatroidstageStageActivity(String str) throws Exception {
        View view = this.dynamicViews.get(str);
        if (view != null) {
            if (view.getLayoutParams() instanceof WindowManager.LayoutParams) {
                return Float.valueOf(((WindowManager.LayoutParams) view.getLayoutParams()).y);
            }
            return Float.valueOf(view.getY());
        }
        return Float.valueOf(-1.0f);
    }

    public int getViewWidth(final String viewId) {
        View view = this.dynamicViews.get(viewId);
        if (view != null) {
            return view.getWidth();
        }
        return -1;
    }

    public int getViewHeight(final String viewId) {
        View view = this.dynamicViews.get(viewId);
        if (view != null) {
            return view.getHeight();
        }
        return -1;
    }

    public void createWebViewWithHtml(String viewId, String htmlContent, int x, int y, int width, int height) {
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(viewId), "Android");
        webView.getSettings().setDomStorageEnabled(true);
        webView.setBackgroundColor(0);
        webView.setLayerType(2, null);
        webView.setWebChromeClient(new AnonymousClass3());
        webView.loadDataWithBaseURL("https://", htmlContent, "text/html", "UTF-8", null);
        webView.setVisibility(NativeViewBindingManager.getDefaultVisibility());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        addViewToStage(viewId, webView, layoutParams);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/stage/StageActivity$3.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.stage.StageActivity$3, reason: invalid class name */
    class AnonymousClass3 extends WebChromeClient {
        AnonymousClass3() {
        }

        @Override // android.webkit.WebChromeClient
        public void onPermissionRequest(final PermissionRequest request) {
            StageActivity.this.runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PermissionRequest permissionRequest = request;
                    permissionRequest.grant(permissionRequest.getResources());
                }
            });
        }

        @Override // android.webkit.WebChromeClient
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            if (StageActivity.this.webViewFilePathCallback != null) {
                StageActivity.this.webViewFilePathCallback.onReceiveValue(null);
            }
            StageActivity.this.webViewFilePathCallback = filePathCallback;
            try {
                StageActivity.this.startActivityForResult(fileChooserParams.createIntent(), 1001);
                return true;
            } catch (ActivityNotFoundException unused) {
                StageActivity.this.webViewFilePathCallback = null;
                return false;
            }
        }
    }

    public void createInputField(String viewId, final UserVariable variable, String initialText, int x, int y, int width, int height, HashMap<String, String> styleOptions) {
        String str;
        EditText editText = new EditText(this);
        editText.setText(initialText);
        if (styleOptions != null) {
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            if (styleOptions.containsKey(STYLE_CORNER_RADIUS)) {
                try {
                    gradientDrawable.setCornerRadius(Float.parseFloat(styleOptions.get(STYLE_CORNER_RADIUS)));
                } catch (NumberFormatException unused) {
                }
            }
            if (styleOptions.containsKey(STYLE_BACKGROUND_COLOR)) {
                try {
                    gradientDrawable.setColor(Color.parseColor(styleOptions.get(STYLE_BACKGROUND_COLOR)));
                } catch (IllegalArgumentException unused2) {
                }
            } else {
                gradientDrawable.setColor(0);
            }
            editText.setBackground(gradientDrawable);
            if (styleOptions.containsKey(STYLE_TEXT_SIZE)) {
                try {
                    editText.setTextSize(2, Float.parseFloat(styleOptions.get(STYLE_TEXT_SIZE)));
                } catch (NumberFormatException unused3) {
                }
            }
            if (styleOptions.containsKey(STYLE_TEXT_COLOR)) {
                try {
                    editText.setTextColor(Color.parseColor(styleOptions.get(STYLE_TEXT_COLOR)));
                } catch (IllegalArgumentException unused4) {
                }
            }
            if (styleOptions.containsKey(STYLE_HINT_TEXT)) {
                editText.setHint(styleOptions.get(STYLE_HINT_TEXT));
            }
            if (styleOptions.containsKey(STYLE_HINT_TEXT_COLOR)) {
                try {
                    editText.setHintTextColor(Color.parseColor(styleOptions.get(STYLE_HINT_TEXT_COLOR)));
                } catch (IllegalArgumentException unused5) {
                }
            }
            if (styleOptions.containsKey(STYLE_TEXT_ALIGNMENT) && (str = styleOptions.get(STYLE_TEXT_ALIGNMENT)) != null) {
                String lowerCase = str.toLowerCase();
                lowerCase.hashCode();
                if (lowerCase.equals("center")) {
                    editText.setGravity(17);
                } else if (lowerCase.equals("right")) {
                    editText.setGravity(8388629);
                } else {
                    editText.setGravity(8388627);
                }
            }
            if (styleOptions.containsKey(STYLE_MAX_LENGTH)) {
                try {
                    int i = Integer.parseInt(styleOptions.get(STYLE_MAX_LENGTH));
                    if (i > 0) {
                        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(i)});
                    }
                } catch (NumberFormatException unused6) {
                }
            }
            if (Boolean.parseBoolean(styleOptions.get(STYLE_IS_PASSWORD))) {
                editText.setInputType(129);
            } else if (styleOptions.containsKey(STYLE_INPUT_TYPE)) {
                if ("number".equalsIgnoreCase(styleOptions.get(STYLE_INPUT_TYPE))) {
                    editText.setInputType(12290);
                } else {
                    editText.setInputType(131073);
                    editText.setMaxLines(Integer.MAX_VALUE);
                    editText.setGravity((editText.getGravity() & (-113)) | 48);
                }
            }
            if (styleOptions.containsKey(STYLE_FONT_PATH)) {
                try {
                    editText.setTypeface(Typeface.createFromFile(styleOptions.get(STYLE_FONT_PATH)));
                } catch (Exception e) {
                    Log.e("StageActivity", "Failed to load font from path: " + styleOptions.get(STYLE_FONT_PATH), e);
                }
            }
        }
        editText.addTextChangedListener(new TextWatcher() { // from class: org.catrobat.catroid.stage.StageActivity.4
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s) {
                UserVariable userVariable;
                if (ProjectManager.getInstance().getCurrentProject() == null || (userVariable = variable) == null) {
                    return;
                }
                userVariable.setValue(s.toString());
            }
        });
        editText.setVisibility(NativeViewBindingManager.getDefaultVisibility());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        addViewToStage(viewId, editText, layoutParams);
    }

    public void setWebViewCallback(String viewId, WebViewCallback callback) {
        if (callback == null) {
            this.webViewCallbacks.remove(viewId);
        } else {
            this.webViewCallbacks.put(viewId, callback);
        }
    }

    public void createGLSurfaceView(final String viewId, int x, int y, int width, int height) {
        if (NativeBridge.INSTANCE.isWorking()) {
            if (this.dynamicViews.containsKey(viewId)) {
                Log.w(TAG, "View with id '" + viewId + "' already exists. Removing old one.");
                removeView(viewId);
            }
            SurfaceView surfaceView = new SurfaceView(this);
            surfaceView.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda10
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return StageActivity.lambda$createGLSurfaceView$15(viewId, view, motionEvent);
                }
            });
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: org.catrobat.catroid.stage.StageActivity.5
                @Override // android.view.SurfaceHolder.Callback
                public void surfaceCreated(SurfaceHolder holder) {
                    NativeBridge.INSTANCE.onSurfaceCreated(viewId, holder.getSurface());
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceChanged(SurfaceHolder holder, int format, int width2, int height2) {
                    NativeBridge.INSTANCE.onSurfaceChanged(viewId, width2, height2);
                }

                @Override // android.view.SurfaceHolder.Callback
                public void surfaceDestroyed(SurfaceHolder holder) {
                    NativeBridge.INSTANCE.onSurfaceDestroyed(viewId);
                }
            });
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
            layoutParams.gravity = BadgeDrawable.TOP_START;
            layoutParams.leftMargin = x;
            layoutParams.topMargin = y;
            addViewToStage(viewId, surfaceView, layoutParams);
        }
    }

    static /* synthetic */ boolean lambda$createGLSurfaceView$15(String str, View view, MotionEvent motionEvent) {
        NativeBridge.INSTANCE.onTouchEvent(str, motionEvent.getActionMasked(), motionEvent.getX(), motionEvent.getY(), motionEvent.getPointerId(0));
        return true;
    }

    public void attachSoToView(String viewId, String soPath) {
        if (NativeBridge.INSTANCE.isWorking()) {
            if (!this.dynamicViews.containsKey(viewId)) {
                Log.e(TAG, "Cannot attach .so: View with id '" + viewId + "' not found.");
            } else {
                NativeBridge.INSTANCE.attachSoToView(viewId, soPath);
            }
        }
    }

    public void destroyGLView(String viewId) {
        if (NativeBridge.INSTANCE.isWorking()) {
            removeViewFromStage(viewId);
            NativeBridge.INSTANCE.cleanupInstance(viewId);
        }
    }

    public void removeAllNativeViews() {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5539x4dd12dcf();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$removeAllNativeViews$16$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5539x4dd12dcf() {
        if (NativeBridge.INSTANCE.isWorking()) {
            NativeBridge.INSTANCE.cleanupAllInstances();
        }
        for (View view : this.dynamicViews.values()) {
            this.rootLayout.removeView(view);
            if (view != null && (view.getParent() instanceof ViewGroup)) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
        }
        this.dynamicViews.clear();
        this.webViewCallbacks.clear();
    }

    public void executeJavaScript(final String viewId, final String javascriptCode) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5530x71ebea9c(viewId, javascriptCode);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$executeJavaScript$17$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5530x71ebea9c(String str, String str2) {
        View view = this.dynamicViews.get(str);
        if (view instanceof WebView) {
            ((WebView) view).evaluateJavascript(str2, null);
        } else {
            Log.w(TAG, "View with id '" + str + "' is not a WebView. Cannot execute JavaScript.");
        }
    }

    public void createDebugView(String viewId, int color, int x, int y, int width, int height) {
        View view = new View(this);
        view.setBackgroundColor(color);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        addViewToStage(viewId, view, layoutParams);
    }

    public void debugLayoutHierarchy() {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5529x9a365c8b();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$debugLayoutHierarchy$18$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5529x9a365c8b() {
        Log.d("LayoutDebugger", "=============================================");
        Log.d("LayoutDebugger", "====== ЗАПУСК ДИАГНОСТИКИ ИЕРАРХИИ СЛОЕВ ======");
        Log.d("LayoutDebugger", "=============================================");
        if (this.rootLayout == null) {
            Log.e("LayoutDebugger", "КРИТИЧЕСКАЯ ОШИБКА: rootLayout == null!");
            return;
        }
        Log.d("LayoutDebugger", "Иерархия в rootLayout:");
        for (int i = 0; i < this.rootLayout.getChildCount(); i++) {
            View childAt = this.rootLayout.getChildAt(i);
            String simpleName = childAt.getClass().getSimpleName();
            if (childAt == this.cameraContainer) {
                simpleName = "cameraContainer";
            }
            if (childAt == this.backgroundLayout) {
                simpleName = "backgroundLayout";
            }
            if (childAt == this.gameView) {
                simpleName = "gameView (LibGDX)";
            }
            if (childAt == this.foregroundLayout) {
                simpleName = "foregroundLayout";
            }
            Log.d("LayoutDebugger", "  -> Слой " + i + ": " + simpleName);
        }
        Log.d("LayoutDebugger", "---------------------------------------------");
        Log.d("LayoutDebugger", "АНАЛИЗ СЛОЯ backgroundLayout:");
        if (this.backgroundLayout != null) {
            Log.d("LayoutDebugger", "  - Видимость: ".concat(this.backgroundLayout.getVisibility() == 0 ? "VISIBLE" : VirtualFile.PROP_HIDDEN));
            Log.d("LayoutDebugger", "  - Размеры (ШхВ): " + this.backgroundLayout.getWidth() + "x" + this.backgroundLayout.getHeight());
            Log.d("LayoutDebugger", "  - Альфа: " + this.backgroundLayout.getAlpha());
            Log.d("LayoutDebugger", "  - Количество дочерних View: " + this.backgroundLayout.getChildCount());
            if (this.backgroundLayout.getChildCount() > 0) {
                View childAt2 = this.backgroundLayout.getChildAt(0);
                Log.d("LayoutDebugger", "    -> Дочерний View[0]: " + childAt2.getClass().getSimpleName());
                Log.d("LayoutDebugger", "       - Размеры (ШхВ): " + childAt2.getWidth() + "x" + childAt2.getHeight());
            }
        } else {
            Log.e("LayoutDebugger", "  - ОШИБКА: backgroundLayout == null!");
        }
        Log.d("LayoutDebugger", "---------------------------------------------");
        Log.d("LayoutDebugger", "АНАЛИЗ СЛОЯ foregroundLayout:");
        if (this.foregroundLayout != null) {
            Log.d("LayoutDebugger", "  - Количество дочерних View: " + this.foregroundLayout.getChildCount());
            if (this.foregroundLayout.getChildCount() > 0) {
                Log.d("LayoutDebugger", "    -> Дочерний View[0]: " + this.foregroundLayout.getChildAt(0).getClass().getSimpleName());
            }
        }
        Log.d("LayoutDebugger", "=============================================");
        Log.d("LayoutDebugger", "=============== ДИАГНОСТИКА ЗАВЕРШЕНА ===============");
        Log.d("LayoutDebugger", "=============================================");
    }

    public void setViewPosition(final String viewId, final int x, final int y) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda30
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5544x8ae91a28(viewId, x, y);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$setViewPosition$19$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5544x8ae91a28(String str, int i, int i2) {
        View view = this.dynamicViews.get(str);
        if (view != null) {
            if (view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = i;
                layoutParams.topMargin = i2;
                view.setLayoutParams(layoutParams);
                return;
            }
            if (view.getLayoutParams() instanceof WindowManager.LayoutParams) {
                OverlayViewManager.updateOverlayPosition(str, i, i2);
                return;
            }
            return;
        }
        Log.w(TAG, "Cannot set position for view '" + str + "'. View not found.");
    }

    public void createVideoPlayer(String viewId, String videoPath, int x, int y, int width, int height, boolean showControls, final boolean loopVideo, boolean isTransparent) {
        VideoView videoView = new VideoView(this);
        if (isTransparent) {
            videoView.getHolder().setFormat(-3);
        }
        videoView.setZOrderOnTop(this.activeNativeLayer == this.foregroundLayout);
        if (showControls) {
            MediaController mediaController = new MediaController(this);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
        }
        videoView.setVideoPath(videoPath);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda25
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer) {
                StageActivity.lambda$createVideoPlayer$20(loopVideo, mediaPlayer);
            }
        });
        videoView.setVisibility(NativeViewBindingManager.getDefaultVisibility());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.gravity = BadgeDrawable.TOP_START;
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        addViewToStage(viewId, videoView, layoutParams);
        videoView.requestFocus();
    }

    static /* synthetic */ void lambda$createVideoPlayer$20(boolean z, MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        if (z) {
            mediaPlayer.setLooping(true);
        }
    }

    public void removeView(String viewId) {
        removeViewFromStage(viewId);
    }

    public void addViewToStage(final String viewId, final View view, final FrameLayout.LayoutParams params) {
        removeViewFromStage(viewId);
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5523xbe3141d8(view, params, viewId);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$addViewToStage$21$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5523xbe3141d8(View view, FrameLayout.LayoutParams layoutParams, String str) {
        view.setLayoutParams(layoutParams);
        this.dynamicViews.put(str, view);
        this.activeNativeLayer.addView(view);
    }

    public void removeViewFromStage(final String viewId) {
        if (this.dynamicViews.containsKey(viewId)) {
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5540xf28180cb(viewId);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$removeViewFromStage$22$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5540xf28180cb(String str) {
        View view = this.dynamicViews.get(str);
        if (view != null && (view.getParent() instanceof ViewGroup)) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        this.dynamicViews.remove(str);
    }

    public FrameLayout getCameraContainer() {
        return this.cameraContainer;
    }

    public View getViewFromStage(String viewId) {
        return this.dynamicViews.get(viewId);
    }

    public static void runOnMainThread(Runnable runnable) {
        Handler handler = mainThreadHandler;
        if (handler != null) {
            handler.post(runnable);
        }
    }

    private void checkAndRequestPermissions() {
        ArrayList arrayList = new ArrayList();
        if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            arrayList.add("android.permission.CAMERA");
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != 0) {
            arrayList.add("android.permission.RECORD_AUDIO");
        }
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            arrayList.add("android.permission.ACCESS_FINE_LOCATION");
        }
        if (Build.VERSION.SDK_INT >= 31 && ContextCompat.checkSelfPermission(this, "android.permission.NEARBY_WIFI_DEVICES") != 0) {
            arrayList.add("android.permission.NEARBY_WIFI_DEVICES");
        }
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATIONS") != 0) {
            arrayList.add("android.permission.POST_NOTIFICATIONS");
        }
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.READ_MEDIA_IMAGES") != 0) {
                arrayList.add("android.permission.READ_MEDIA_IMAGES");
            }
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (arrayList.isEmpty()) {
            return;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[0]), 100);
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, android.app.Activity
    public void onPause() {
        isAppPaused = true;
        StageLifeCycleController.stagePause(this);
        super.onPause();
        if (MainMenuActivity.surveyCampaign != null) {
            MainMenuActivity.surveyCampaign.endStageTime();
            PowerManager powerManager = (PowerManager) getSystemService("power");
            try {
                if (isApplicationSentToBackground(this) || !powerManager.isInteractive()) {
                    MainMenuActivity.surveyCampaign.endAppTime(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isApplicationSentToBackground(final Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.importance == 100) {
                for (String str : runningAppProcessInfo.pkgList) {
                    if (str.equals(context.getPackageName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, android.app.Activity
    public void onResume() {
        isAppPaused = false;
        StageLifeCycleController.stageResume(this);
        super.onResume();
        activeStageActivity = new WeakReference<>(this);
        if (MainMenuActivity.surveyCampaign != null) {
            MainMenuActivity.surveyCampaign.startAppTime(this);
            MainMenuActivity.surveyCampaign.startStageTime();
        }
    }

    @Override // android.app.Activity
    public void finish() {
        ProjectManager.popProjectHistory();
        super.finish();
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        if (NativeBridge.INSTANCE.isWorking()) {
            NativeBridge.INSTANCE.cleanupAllInstances();
        }
        if (ProjectManager.getInstance().getCurrentProject() != null) {
            StageLifeCycleController.stageDestroy(this);
        }
        OverlayViewManager.removeAllOverlays();
        RunJSAction.INSTANCE.destroyWebView();
    }

    AndroidGraphics getGdxGraphics() {
        return this.graphics;
    }

    void setupAskHandler() {
        messageHandler = new Handler(Looper.getMainLooper()) { // from class: org.catrobat.catroid.stage.StageActivity.6
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                ArrayList arrayList = (ArrayList) message.obj;
                int i = message.what;
                if (i == 0) {
                    this.queueIntent((IntentListener) arrayList.get(0));
                    return;
                }
                if (i == 1) {
                    this.startQueuedIntent(((Integer) arrayList.get(0)).intValue());
                    return;
                }
                if (i == 2) {
                    StageActivity.this.brickDialogManager.showDialog((BrickDialogManager.DialogType) arrayList.get(0), (Action) arrayList.get(1), (String) arrayList.get(2), (String) arrayList.get(3), (String) arrayList.get(4), (String) arrayList.get(5), (String) arrayList.get(6));
                    return;
                }
                if (i == 3) {
                    StageActivity.this.showToastMessage((String) arrayList.get(0));
                } else if (i == 4) {
                    StageActivity.this.showLongToastMessage((String) arrayList.get(0));
                } else {
                    Log.e(StageActivity.TAG, "Unhandled message in messagehandler, case " + message.what);
                }
            }
        };
    }

    public boolean dialogIsShowing() {
        return this.stageDialog.isShowing() || this.brickDialogManager.dialogIsShowing();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showToastMessage(String message) {
        ToastUtil.showError(this, message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLongToastMessage(String message) {
        ToastUtil.showInfoLong(this, message);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        NfcHandler.processIntent(intent);
        setIntent(intent);
        if (nfcTagMessage != null) {
            Tag tag = (Tag) intent.getParcelableExtra("android.nfc.extra.TAG");
            synchronized (StageActivity.class) {
                NfcHandler.writeTag(tag, nfcTagMessage);
                setNfcTagMessage(null);
            }
        }
        handleNotificationIntent(intent);
    }

    private boolean projectHasScriptOfType(Class<? extends Script> scriptClass) {
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        if (currentProject != null && scriptClass != null) {
            Iterator<Scene> it = currentProject.getSceneList().iterator();
            while (it.hasNext()) {
                Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
                while (it2.hasNext()) {
                    Iterator<Script> it3 = it2.next().getScriptList().iterator();
                    while (it3.hasNext()) {
                        if (scriptClass.isInstance(it3.next())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private void broadcastEventToAllSprites(EventId eventId) {
        Scene currentlyPlayingScene = ProjectManager.getInstance().getCurrentlyPlayingScene();
        if (currentlyPlayingScene == null) {
            return;
        }
        for (Sprite sprite : currentlyPlayingScene.getSpriteList()) {
            Multimap<EventId, ScriptSequenceAction> idToEventThreadMap = sprite.getIdToEventThreadMap();
            if (idToEventThreadMap != null && idToEventThreadMap.containsKey(eventId)) {
                for (ScriptSequenceAction scriptSequenceAction : idToEventThreadMap.get(eventId)) {
                    scriptSequenceAction.restart();
                    sprite.look.addAction(scriptSequenceAction);
                }
            }
        }
    }

    public void captureScreenWithNativeViews(final String screenshotPath, final String filename, final ScreenshotSaverCallback callback) {
        View view = this.gameView;
        if (!(view instanceof SurfaceView)) {
            if (callback != null) {
                callback.screenshotSaved(false);
                return;
            }
            return;
        }
        SurfaceView surfaceView = (SurfaceView) view;
        int width = surfaceView.getWidth();
        int height = surfaceView.getHeight();
        if (width <= 0 || height <= 0) {
            if (callback != null) {
                callback.screenshotSaved(false);
            }
        } else {
            final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            final HandlerThread handlerThread = new HandlerThread("PixelCopyThread");
            handlerThread.start();
            PixelCopy.request(surfaceView, bitmapCreateBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda20
                @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                public final void onPixelCopyFinished(int i) {
                    this.f$0.m5528xcd5f4594(handlerThread, bitmapCreateBitmap, screenshotPath, filename, callback, i);
                }
            }, new Handler(handlerThread.getLooper()));
        }
    }

    /* JADX INFO: renamed from: lambda$captureScreenWithNativeViews$27$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5528xcd5f4594(HandlerThread handlerThread, final Bitmap bitmap, final String str, final String str2, final ScreenshotSaverCallback screenshotSaverCallback, int i) {
        handlerThread.quitSafely();
        if (i == 0) {
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m5527xbe94db56(bitmap, str, str2, screenshotSaverCallback);
                }
            });
            return;
        }
        bitmap.recycle();
        Log.e("StageActivity", "PixelCopy failed with error code: " + i);
        if (screenshotSaverCallback != null) {
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    screenshotSaverCallback.screenshotSaved(false);
                }
            });
        }
    }

    /* JADX INFO: renamed from: lambda$captureScreenWithNativeViews$25$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5527xbe94db56(final Bitmap bitmap, final String str, final String str2, final ScreenshotSaverCallback screenshotSaverCallback) {
        Canvas canvas = new Canvas(bitmap);
        FrameLayout frameLayout = this.backgroundLayout;
        if (frameLayout != null) {
            frameLayout.draw(canvas);
        }
        FrameLayout frameLayout2 = this.foregroundLayout;
        if (frameLayout2 != null) {
            frameLayout2.draw(canvas);
        }
        new Thread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5526xb72fa637(bitmap, str, str2, screenshotSaverCallback);
            }
        }).start();
    }

    /* JADX INFO: renamed from: lambda$captureScreenWithNativeViews$24$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5526xb72fa637(Bitmap bitmap, String str, String str2, final ScreenshotSaverCallback screenshotSaverCallback) {
        final boolean zSaveBitmapToPng = saveBitmapToPng(bitmap, new File(str, str2));
        bitmap.recycle();
        if (screenshotSaverCallback != null) {
            runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    screenshotSaverCallback.screenshotSaved(zSaveBitmapToPng);
                }
            });
        }
    }

    private boolean saveBitmapToPng(Bitmap bitmap, File destFile) {
        try {
            if (destFile.exists()) {
                destFile.delete();
            }
            File parentFile = destFile.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                return true;
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e("StageActivity", "Failed to save bitmap to PNG", e);
            return false;
        }
    }

    public static StageListener getActiveStageListener() {
        StageActivity stageActivity;
        StageListener stageListener;
        WeakReference<StageActivity> weakReference = activeStageActivity;
        if (weakReference == null || (stageActivity = weakReference.get()) == null || (stageListener = stageActivity.stageListener) == null) {
            return null;
        }
        return stageListener;
    }

    @Override // android.app.Activity
    public void onBackPressed() {
        WindowContainer windowContainer;
        if ((this instanceof StageWorkspaceActivity) && (windowContainer = this.window) != null && windowContainer.getIsMaximized()) {
            this.window.toggleMaximize();
            return;
        }
        if (!EventManager.projectHasScriptOfType(ProjectManager.getInstance().getCurrentProject(), BackPressedScript.class)) {
            handleBack();
        } else {
            if (this.backPressedTime + 2000 > System.currentTimeMillis()) {
                handleBack();
                return;
            }
            broadcastEventToAllSprites(new EventId(7));
            Toast.makeText(this, "Нажмите еще раз для вызова меню", 0).show();
            this.backPressedTime = System.currentTimeMillis();
        }
    }

    private /* synthetic */ void lambda$handleBack$29(boolean z) {
        runOnUiThread(new Runnable() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.m5533lambda$handleBack$28$orgcatrobatcatroidstageStageActivity();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$handleBack$28$org-catrobat-catroid-stage-StageActivity, reason: not valid java name */
    /* synthetic */ void m5533lambda$handleBack$28$orgcatrobatcatroidstageStageActivity() {
        this.idlingResource.decrement();
    }

    public void manageLoadAndFinish() {
        this.stageListener.pause();
        this.stageListener.finish();
        TextToSpeechHolder.getInstance().shutDownTextToSpeech();
        ((SpeechRecognitionHolderFactory) KoinJavaComponent.get(SpeechRecognitionHolderFactory.class)).getInstance().destroy();
        BluetoothDeviceService bluetoothDeviceService = (BluetoothDeviceService) ServiceProvider.getService(CatroidService.BLUETOOTH_DEVICE_SERVICE);
        if (bluetoothDeviceService != null) {
            bluetoothDeviceService.pause();
        }
        RaspberryPiService.getInstance().disconnect();
    }

    public static CameraManager getActiveCameraManager() {
        WeakReference<StageActivity> weakReference = activeStageActivity;
        if (weakReference != null) {
            return weakReference.get().cameraManager;
        }
        return null;
    }

    public static VibrationManager getActiveVibrationManager() {
        WeakReference<StageActivity> weakReference = activeStageActivity;
        if (weakReference != null) {
            return weakReference.get().vibrationManager;
        }
        return null;
    }

    public boolean isResizePossible() {
        return this.resizePossible;
    }

    void calculateScreenSizes() {
        ScreenValueHandler.updateScreenWidthAndHeight(getContext());
        Resolution resolution = new Resolution(ProjectManager.getInstance().getCurrentProject().getXmlHeader().getVirtualScreenWidth(), ProjectManager.getInstance().getCurrentProject().getXmlHeader().getVirtualScreenHeight());
        ScreenValues.currentScreenResolution = ScreenValues.currentScreenResolution.flipToFit(resolution);
        boolean z = (ScreenValues.currentScreenResolution.sameRatioOrMeasurements(resolution) || ProjectManager.getInstance().getCurrentProject().isCastProject()) ? false : true;
        this.resizePossible = z;
        if (z) {
            this.stageListener.setMaxViewPort(resolution.resizeToFit(ScreenValues.currentScreenResolution));
        } else {
            this.stageListener.setMaxViewPort(ScreenValues.currentScreenResolution);
        }
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, com.badlogic.gdx.Application
    public ApplicationListener getApplicationListener() {
        if (this.stageListener == null) {
            this.stageListener = new StageListener();
        }
        return this.stageListener;
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, com.badlogic.gdx.Application
    public void log(String tag, String message, Throwable exception) {
        Log.d(tag, message, exception);
    }

    public void post(Runnable r) {
        this.handler.post(r);
    }

    public void jsDestroy() {
        this.stageListener.finish();
        manageLoadAndFinish();
        exit();
    }

    public static int getAndIncrementNumberOfClonedSprites() {
        int i = numberOfSpritesCloned + 1;
        numberOfSpritesCloned = i;
        return i;
    }

    public static void resetNumberOfClonedSprites() {
        numberOfSpritesCloned = 0;
    }

    public static void setNfcTagMessage(NdefMessage message) {
        nfcTagMessage = message;
    }

    public static NdefMessage getNfcTagMessage() {
        return nfcTagMessage;
    }

    public synchronized void queueIntent(IntentListener asker) {
        int iNextInt;
        if (messageHandler == null) {
            return;
        }
        do {
            iNextInt = randomGenerator.nextInt(Integer.MAX_VALUE);
        } while (intentListeners.indexOfKey(iNextInt) >= 0);
        intentListeners.put(iNextInt, asker);
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(iNextInt));
        messageHandler.obtainMessage(1, arrayList).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startQueuedIntent(int intentKey) {
        Intent targetIntent;
        if (intentListeners.indexOfKey(intentKey) >= 0 && (targetIntent = intentListeners.get(intentKey).getTargetIntent()) != null) {
            Package r1 = getClass().getPackage();
            if (r1 != null) {
                targetIntent.putExtra("calling_package", r1.getName());
            }
            startActivityForResult(targetIntent, intentKey);
        }
    }

    public static void addIntentListener(IntentListener listener) {
        intentListeners2.add(listener);
    }

    @Override // com.badlogic.gdx.backends.android.AndroidApplication, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 42) {
            if (resultCode == -1 && data != null && data.getData() != null) {
                Uri data2 = data.getData();
                try {
                    FileInputStream fileInputStream = new FileInputStream(new File(this.sourceFileToExportPath));
                    try {
                        OutputStream outputStreamOpenOutputStream = getContentResolver().openOutputStream(data2);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int i = fileInputStream.read(bArr);
                                if (i <= 0) {
                                    break;
                                } else {
                                    outputStreamOpenOutputStream.write(bArr, 0, i);
                                }
                                try {
                                    fileInputStream.close();
                                } catch (Throwable th) {
                                    th.addSuppressed(th);
                                }
                                throw th;
                            }
                            if (outputStreamOpenOutputStream != null) {
                                outputStreamOpenOutputStream.close();
                            }
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            if (outputStreamOpenOutputStream != null) {
                                try {
                                    outputStreamOpenOutputStream.close();
                                } catch (Throwable th3) {
                                    th2.addSuppressed(th3);
                                }
                            }
                            throw th2;
                        }
                    } catch (Throwable th4) {
                        fileInputStream.close();
                        throw th4;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Ошибка экспорта файла", e);
                    Toast.makeText(this, "Ошибка при экспорте: " + e.getMessage(), 0).show();
                }
            }
            this.sourceFileToExportPath = null;
        }
        Iterator<IntentListener> it = intentListeners2.iterator();
        while (it.hasNext()) {
            if (it.next().onIntentResult(requestCode, resultCode, data)) {
                return;
            }
        }
        if (resultCode == 7777 || resultCode == 8888) {
            String stringExtra = data.getStringExtra(TestResult.TEST_RESULT_MESSAGE);
            ToastUtil.showError(this, stringExtra);
            ((ClipboardManager) getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("TestResult", ProjectManager.getInstance().getCurrentProject().getName() + "\n" + stringExtra));
        }
        if (intentListeners.indexOfKey(requestCode) >= 0) {
            IntentListener intentListener = intentListeners.get(requestCode);
            if (data != null) {
                intentListener.onIntentResult(requestCode, resultCode, data);
            }
            intentListeners.remove(requestCode);
            return;
        }
        this.stageResourceHolder.onActivityResult(requestCode, resultCode, data);
    }

    @Override // org.catrobat.catroid.ui.runtimepermissions.PermissionAdaptingActivity
    public void adaptToDeniedPermissions(List<String> deniedPermissions) {
        Brick.ResourcesSet resourcesSet = new Brick.ResourcesSet();
        Iterator<Scene> it = ProjectManager.getInstance().getCurrentProject().getSceneList().iterator();
        while (it.hasNext()) {
            Iterator<Sprite> it2 = it.next().getSpriteList().iterator();
            while (it2.hasNext()) {
                for (Brick brick : it2.next().getAllBricks()) {
                    brick.addRequiredResources(resourcesSet);
                    List<String> listTranslate = BrickResourcesToRuntimePermissions.translate(resourcesSet);
                    listTranslate.retainAll(deniedPermissions);
                    if (!listTranslate.isEmpty()) {
                        brick.setCommentedOut(true);
                    }
                    resourcesSet.clear();
                }
            }
        }
    }

    @Override // org.catrobat.catroid.ui.runtimepermissions.PermissionHandlingActivity
    public void addToRequiresPermissionTaskList(RequiresPermissionTask task) {
        this.permissionRequestActivityExtension.addToRequiresPermissionTaskList(task);
    }

    @Override // android.app.Activity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            int length = grantResults.length;
            for (int i = 0; i < length && grantResults[i] == 0; i++) {
            }
            return;
        }
        this.permissionRequestActivityExtension.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    public static void handlePlayButton(ProjectManager projectManager, final Activity activity) {
        boolean zProjectContainsDangerousBricks = ProjectSecurityChecker.projectContainsDangerousBricks(projectManager.getCurrentProject());
        boolean z = activity.getSharedPreferences(PREFS_NAME, 0).getBoolean(PREFS_KEY_SUPPRESS_WARNING, false);
        if (zProjectContainsDangerousBricks && !z) {
            showSecurityWarningDialog(projectManager, activity);
        } else {
            launchProject(projectManager, activity);
        }
    }

    private static void showSecurityWarningDialog(final ProjectManager projectManager, final Activity activity) {
        new AlertDialog.Builder(activity).setTitle("Проект может содержать вредоносный код").setMessage("В проекте используется Java компиляция, LunoScript, Python или Библиотеки, это может быть опасно. Запускайте его только если проверили код или доверяете источнику.").setCancelable(false).setIcon(R.drawable.ic_dialog_alert).setPositiveButton("Запуск", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda7
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                StageActivity.lambda$showSecurityWarningDialog$30(projectManager, activity, dialogInterface, i);
            }
        }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setNeutralButton("Больше не напоминать", new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda9
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                StageActivity.lambda$showSecurityWarningDialog$32(activity, projectManager, dialogInterface, i);
            }
        }).show();
    }

    static /* synthetic */ void lambda$showSecurityWarningDialog$30(ProjectManager projectManager, Activity activity, DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        launchProject(projectManager, activity);
    }

    static /* synthetic */ void lambda$showSecurityWarningDialog$32(Activity activity, ProjectManager projectManager, DialogInterface dialogInterface, int i) {
        SharedPreferences.Editor editorEdit = activity.getSharedPreferences(PREFS_NAME, 0).edit();
        editorEdit.putBoolean(PREFS_KEY_SUPPRESS_WARNING, true);
        editorEdit.apply();
        dialogInterface.dismiss();
        launchProject(projectManager, activity);
    }

    private static void launchProject(ProjectManager projectManager, final Activity activity) {
        Project currentProject = projectManager.getCurrentProject();
        if (currentProject == null) {
            return;
        }
        Scene currentlyEditedScene = projectManager.getCurrentlyEditedScene();
        Scene defaultScene = currentProject.getDefaultScene();
        if (currentlyEditedScene == null || defaultScene == null) {
            return;
        }
        if (currentlyEditedScene.getName().equals(defaultScene.getName())) {
            projectManager.setCurrentlyPlayingScene(defaultScene);
            projectManager.setStartScene(defaultScene);
            startStageActivity(activity);
            return;
        }
        new PlaySceneDialog.Builder(activity).setPositiveButton(org.DanVexTeam.NewCatroidApkTemplate.R.string.play, new DialogInterface.OnClickListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                StageActivity.startStageActivity(activity);
            }
        }).create().show();
    }

    public static void handleAiButton() {
        StageActivity stageActivity = activeStageActivity.get();
        if (stageActivity == null || stageActivity.isFinishing()) {
            return;
        }
        View viewInflate = View.inflate(stageActivity, org.DanVexTeam.NewCatroidApkTemplate.R.layout.dialog_ai_assist, null);
        TextInputDialog.Builder builder = new TextInputDialog.Builder(stageActivity);
        builder.setPositiveButton("Ok", new TextInputDialog.OnClickListener() { // from class: org.catrobat.catroid.stage.StageActivity$$ExternalSyntheticLambda17
            @Override // org.catrobat.catroid.ui.recyclerview.dialog.TextInputDialog.OnClickListener
            public final void onPositiveButtonClick(DialogInterface dialogInterface, String str) {
                Log.d("ab", str);
            }
        });
        builder.setTitle(org.DanVexTeam.NewCatroidApkTemplate.R.string.ai_assist).setView(viewInflate).setNegativeButton("Cancel", (DialogInterface.OnClickListener) null).create().show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void startStageActivity(Activity activity) {
        Intent intent;
        if (PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("pref_workspace_stage", false)) {
            intent = new Intent(activity, (Class<?>) StageWorkspaceActivity.class);
        } else {
            intent = new Intent(activity, (Class<?>) StageActivity.class);
        }
        activity.startActivityForResult(intent, 101);
    }

    public static void finishStage() {
        StageActivity stageActivity = activeStageActivity.get();
        if (stageActivity == null || stageActivity.isFinishing()) {
            return;
        }
        stageActivity.finish();
    }

    public static void finishTestWithResult(TestResult testResult) {
        StageActivity stageActivity = activeStageActivity.get();
        if (stageActivity == null || stageActivity.isFinishing()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(TestResult.TEST_RESULT_MESSAGE, testResult.getMessage());
        stageActivity.setResult(testResult.getResultCode(), intent);
        stageActivity.finish();
    }

    public void updateStageSize(int width, int height) {
        View view = this.gameView;
        if (view instanceof SurfaceView) {
        }
    }
}
