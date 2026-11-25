package org.catrobat.catroid.cast;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.CoroutineLiveDataKt;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import com.badlogic.gdx.backends.android.surfaceview.GLSurfaceView20;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.CastMediaControlIntent;
import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import com.google.android.gms.common.api.Status;
import java.util.ArrayList;
import java.util.EnumMap;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.bricks.CameraBrick;
import org.catrobat.catroid.content.bricks.ChooseCameraBrick;
import org.catrobat.catroid.content.bricks.FlashBrick;
import org.catrobat.catroid.formulaeditor.Sensors;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.ui.adapter.CastDevicesAdapter;
import org.catrobat.catroid.ui.dialogs.SelectCastDialog;
import org.catrobat.catroid.utils.ToastUtil;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/cast/CastManager.class
  classes12.dex
 */
/* JADX INFO: loaded from: classes12.dex */
public final class CastManager {
    private static final CastManager INSTANCE = new CastManager();
    public static ArrayList<Class<?>> unsupportedBricks = new ArrayList<Class<?>>() { // from class: org.catrobat.catroid.cast.CastManager.1
        {
            add(CameraBrick.class);
            add(ChooseCameraBrick.class);
            add(FlashBrick.class);
        }
    };
    private MyMediaRouterCallback callback;
    private MenuItem castButton;
    private ArrayAdapter<MediaRouter.RouteInfo> deviceAdapter;
    private StageActivity gamepadActivity;
    private AppCompatActivity initializingActivity;
    private boolean isCastDeviceAvailable;
    private boolean isConnected;
    private EnumMap<Sensors, Boolean> isGamepadButtonPressed;
    private MediaRouteSelector mediaRouteSelector;
    private MediaRouter mediaRouter;
    private boolean pausedScreenShowing;
    private RelativeLayout pausedView;
    private RelativeLayout remoteLayout;
    private final ArrayList<MediaRouter.RouteInfo> routeInfos = new ArrayList<>();
    private CastDevice selectedDevice;
    private GLSurfaceView20 stageViewDisplayedOnCast;

    private CastManager() {
        EnumMap<Sensors, Boolean> enumMap = new EnumMap<>(Sensors.class);
        this.isGamepadButtonPressed = enumMap;
        this.isConnected = false;
        this.pausedView = null;
        this.pausedScreenShowing = false;
        enumMap.put(Sensors.GAMEPAD_A_PRESSED, false);
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_B_PRESSED, false);
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_LEFT_PRESSED, false);
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_RIGHT_PRESSED, false);
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_UP_PRESSED, false);
        this.isGamepadButtonPressed.put(Sensors.GAMEPAD_DOWN_PRESSED, false);
    }

    public static CastManager getInstance() {
        return INSTANCE;
    }

    public synchronized void initializeGamepadActivity(StageActivity gamepadActivity) {
        this.gamepadActivity = gamepadActivity;
        initGamepadListeners();
    }

    public synchronized void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
        MenuItem menuItem = this.castButton;
        if (menuItem != null) {
            menuItem.setIcon(isConnected ? R.drawable.ic_cast_connected_white : R.drawable.ic_cast_white);
        }
        AppCompatActivity appCompatActivity = this.initializingActivity;
        if (appCompatActivity != null) {
            appCompatActivity.invalidateOptionsMenu();
        }
    }

    public void startCastButtonAnimation() {
        MenuItem menuItem = this.castButton;
        if (menuItem != null) {
            menuItem.setIcon(R.drawable.animation_cast_button_connecting);
            if (this.castButton.getIcon() instanceof AnimationDrawable) {
                ((AnimationDrawable) this.castButton.getIcon()).start();
            }
        }
    }

    public synchronized boolean isConnected() {
        return this.isConnected;
    }

    public MediaRouter getMediaRouter() {
        return this.mediaRouter;
    }

    public ArrayAdapter<MediaRouter.RouteInfo> getDeviceAdapter() {
        return this.deviceAdapter;
    }

    public ArrayList<MediaRouter.RouteInfo> getRouteInfos() {
        return this.routeInfos;
    }

    public boolean isButtonPressed(Sensors btnSensor) {
        return this.isGamepadButtonPressed.get(btnSensor).booleanValue();
    }

    public void setButtonPress(Sensors btn, boolean b) {
        this.isGamepadButtonPressed.put(btn, Boolean.valueOf(b));
    }

    public CastDevice getSelectedDevice() {
        return this.selectedDevice;
    }

    public synchronized void initializeCast(AppCompatActivity activity) {
        this.initializingActivity = activity;
        if (this.mediaRouter != null) {
            return;
        }
        this.deviceAdapter = new CastDevicesAdapter(activity, R.layout.fragment_cast_device_list_item, this.routeInfos);
        this.mediaRouter = MediaRouter.getInstance(activity.getApplicationContext());
        this.mediaRouteSelector = new MediaRouteSelector.Builder().addControlCategory(CastMediaControlIntent.categoryForCast(Constants.REMOTE_DISPLAY_APP_ID)).build();
        this.callback = new MyMediaRouterCallback();
    }

    public void addCallback() {
        MyMediaRouterCallback myMediaRouterCallback;
        MediaRouter mediaRouter = this.mediaRouter;
        if (mediaRouter == null || (myMediaRouterCallback = this.callback) == null) {
            return;
        }
        mediaRouter.addCallback(this.mediaRouteSelector, myMediaRouterCallback, 1);
    }

    public void removeCallback() {
        MyMediaRouterCallback myMediaRouterCallback;
        MediaRouter mediaRouter = this.mediaRouter;
        if (mediaRouter == null || (myMediaRouterCallback = this.callback) == null) {
            return;
        }
        mediaRouter.removeCallback(myMediaRouterCallback);
    }

    public void openDeviceSelectorOrDisconnectDialog() {
        openDeviceSelectorOrDisconnectDialog(this.initializingActivity);
    }

    private void initGamepadListeners() {
        this.gamepadActivity.findViewById(R.id.gamepadPauseButton).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.cast.CastManager.2
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                v.performHapticFeedback(1);
                CastManager.this.gamepadActivity.onBackPressed();
            }
        });
        View.OnTouchListener onTouchListener = new View.OnTouchListener() { // from class: org.catrobat.catroid.cast.CastManager.3
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View v, MotionEvent event) {
                CastManager.this.handleGamepadTouch((ImageButton) v, event);
                return true;
            }
        };
        ImageButton[] imageButtonArr = {(ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonA), (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonB), (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonUp), (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonDown), (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonLeft), (ImageButton) this.gamepadActivity.findViewById(R.id.gamepadButtonRight)};
        for (int i = 0; i < 6; i++) {
            imageButtonArr[i].setOnTouchListener(onTouchListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void handleGamepadTouch(ImageButton button, MotionEvent event) {
        String string;
        Sensors sensors;
        if (event.getAction() == 0 || event.getAction() == 1) {
            if (this.gamepadActivity == null) {
                return;
            }
            boolean z = event.getAction() == 0;
            switch (button.getId()) {
                case R.id.gamepadButtonA /* 2131364494 */:
                    button.setImageResource(z ? R.drawable.gamepad_button_a_pressed : R.drawable.gamepad_button_a);
                    string = this.gamepadActivity.getString(R.string.cast_gamepad_A);
                    sensors = Sensors.GAMEPAD_A_PRESSED;
                    break;
                case R.id.gamepadButtonB /* 2131364495 */:
                    button.setImageResource(z ? R.drawable.gamepad_button_b_pressed : R.drawable.gamepad_button_b);
                    string = this.gamepadActivity.getString(R.string.cast_gamepad_B);
                    sensors = Sensors.GAMEPAD_B_PRESSED;
                    break;
                case R.id.gamepadButtonDown /* 2131364496 */:
                    string = this.gamepadActivity.getString(R.string.cast_gamepad_down);
                    sensors = Sensors.GAMEPAD_DOWN_PRESSED;
                    break;
                case R.id.gamepadButtonLeft /* 2131364497 */:
                    string = this.gamepadActivity.getString(R.string.cast_gamepad_left);
                    sensors = Sensors.GAMEPAD_LEFT_PRESSED;
                    break;
                case R.id.gamepadButtonRight /* 2131364498 */:
                    string = this.gamepadActivity.getString(R.string.cast_gamepad_right);
                    sensors = Sensors.GAMEPAD_RIGHT_PRESSED;
                    break;
                case R.id.gamepadButtonUp /* 2131364499 */:
                    string = this.gamepadActivity.getString(R.string.cast_gamepad_up);
                    sensors = Sensors.GAMEPAD_UP_PRESSED;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown button pressed");
            }
            setButtonPress(sensors, z);
            if (z) {
                ((StageListener) this.gamepadActivity.getApplicationListener()).gamepadPressed(string);
                button.performHapticFeedback(1);
            }
        }
    }

    public synchronized void addStageViewToLayout(GLSurfaceView20 stageView) {
        RelativeLayout relativeLayout = this.remoteLayout;
        if (relativeLayout == null) {
            return;
        }
        this.stageViewDisplayedOnCast = stageView;
        relativeLayout.setBackgroundColor(ContextCompat.getColor(this.initializingActivity, android.R.color.white));
        this.remoteLayout.removeAllViews();
        this.remoteLayout.addView(this.stageViewDisplayedOnCast);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        stageView.surfaceChanged(stageView.getHolder(), 1, currentProject.getXmlHeader().getVirtualScreenWidth(), currentProject.getXmlHeader().getVirtualScreenHeight());
    }

    public synchronized boolean currentlyConnecting() {
        return (this.isConnected || this.selectedDevice == null) ? false : true;
    }

    public synchronized void openDeviceSelectorOrDisconnectDialog(AppCompatActivity activity) {
        if (activity != null) {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                try {
                    new SelectCastDialog().show(activity.getSupportFragmentManager(), SelectCastDialog.TAG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void setCastButton(MenuItem castButton) {
        this.castButton = castButton;
        setIsConnected(this.isConnected);
    }

    public void selectRoute(MediaRouter.RouteInfo routeInfo) {
        this.mediaRouter.selectRoute(routeInfo);
    }

    public synchronized void setRemoteLayout(RelativeLayout remoteLayout) {
        this.remoteLayout = remoteLayout;
    }

    public synchronized void setRemoteLayoutToIdleScreen(Context context) {
        this.remoteLayout.removeAllViews();
        this.remoteLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.idle_screen_1));
    }

    public synchronized void setRemoteLayoutToPauseScreen(Context context) {
        if (this.remoteLayout != null) {
            if (this.pausedView == null && !this.pausedScreenShowing) {
                RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.cast_pause_screen, (ViewGroup) null);
                this.pausedView = relativeLayout;
                this.remoteLayout.addView(relativeLayout);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.pausedView.getLayoutParams();
                Project currentProject = ProjectManager.getInstance().getCurrentProject();
                layoutParams.height = currentProject.getXmlHeader().getVirtualScreenHeight();
                layoutParams.width = currentProject.getXmlHeader().getVirtualScreenWidth();
                this.pausedView.setLayoutParams(layoutParams);
                this.pausedView.setBackgroundColor(Constants.CAST_IDLE_BACKGROUND_COLOR);
                this.pausedScreenShowing = true;
            }
            this.pausedView.setVisibility(0);
            this.pausedScreenShowing = true;
        }
    }

    public synchronized void resumeRemoteLayoutFromPauseScreen() {
        RelativeLayout relativeLayout;
        if (this.remoteLayout != null && (relativeLayout = this.pausedView) != null) {
            relativeLayout.setVisibility(8);
            this.pausedScreenShowing = false;
        }
    }

    public synchronized void onStageDestroyed() {
        if (this.isConnected) {
            setRemoteLayoutToIdleScreen(this.initializingActivity);
        }
        this.stageViewDisplayedOnCast = null;
        this.pausedView = null;
        this.pausedScreenShowing = false;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/cast/CastManager$MyMediaRouterCallback.class
      classes12.dex
     */
    private class MyMediaRouterCallback extends MediaRouter.Callback {
        private long lastConnectionTry;

        private MyMediaRouterCallback() {
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo info) {
            synchronized (this) {
                for (int i = 0; i < CastManager.this.routeInfos.size(); i++) {
                    if (((MediaRouter.RouteInfo) CastManager.this.routeInfos.get(i)).equals(info)) {
                        CastManager.this.routeInfos.remove(i);
                    }
                }
                CastManager.this.routeInfos.add(info);
                if (CastManager.this.castButton != null) {
                    CastManager.this.castButton.setVisible(CastManager.this.mediaRouter.isRouteAvailable(CastManager.this.mediaRouteSelector, 2));
                }
                CastManager.this.deviceAdapter.notifyDataSetChanged();
            }
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo info) {
            synchronized (this) {
                for (int i = 0; i < CastManager.this.routeInfos.size(); i++) {
                    if (((MediaRouter.RouteInfo) CastManager.this.routeInfos.get(i)).equals(info)) {
                        CastManager.this.routeInfos.remove(i);
                        if (CastManager.this.routeInfos.size() == 0 && CastManager.this.castButton != null) {
                            CastManager.this.castButton.setVisible(CastManager.this.mediaRouter.isRouteAvailable(CastManager.this.mediaRouteSelector, 2));
                        }
                        CastManager.this.deviceAdapter.notifyDataSetChanged();
                    }
                }
            }
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo info) {
            synchronized (this) {
                CastManager.this.selectedDevice = CastDevice.getFromBundle(info.getExtras());
                startCastService(CastManager.this.initializingActivity);
                this.lastConnectionTry = System.currentTimeMillis();
                CastManager.this.isCastDeviceAvailable = CastRemoteDisplayLocalService.getInstance() != null && System.currentTimeMillis() - this.lastConnectionTry >= CoroutineLiveDataKt.DEFAULT_TIMEOUT;
                new Handler().postDelayed(new Runnable() { // from class: org.catrobat.catroid.cast.CastManager.MyMediaRouterCallback.1
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (this) {
                            if (CastManager.this.currentlyConnecting() && CastManager.this.isCastDeviceAvailable) {
                                CastRemoteDisplayLocalService.stopService();
                                ToastUtil.showError(CastManager.this.initializingActivity, CastManager.this.initializingActivity.getString(R.string.cast_connection_timout_msg));
                            }
                        }
                    }
                }, CoroutineLiveDataKt.DEFAULT_TIMEOUT);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouter.Callback
        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo info) {
            onCastStop();
        }

        public synchronized void onCastStop() {
            if (CastManager.this.stageViewDisplayedOnCast != null) {
                CastManager.this.gamepadActivity.onBackPressed();
            }
            CastManager.this.stageViewDisplayedOnCast = null;
            CastManager.this.setIsConnected(false);
            CastManager.this.selectedDevice = null;
            CastManager.this.gamepadActivity = null;
            CastManager.this.remoteLayout = null;
            CastManager.this.pausedView = null;
            CastManager.this.pausedScreenShowing = false;
            CastRemoteDisplayLocalService.stopService();
        }

        public void startCastService(final AppCompatActivity activity) {
            Intent intent = new Intent(activity, activity.getClass());
            intent.setFlags(CompilerOptions.RedundantSuperinterface);
            CastRemoteDisplayLocalService.startService(activity, CastService.class, Constants.REMOTE_DISPLAY_APP_ID, CastManager.this.selectedDevice, new CastRemoteDisplayLocalService.NotificationSettings.Builder().setNotificationPendingIntent(PendingIntent.getActivity(activity, 0, intent, 201326592)).build(), new CastRemoteDisplayLocalService.Callbacks() { // from class: org.catrobat.catroid.cast.CastManager.MyMediaRouterCallback.2
                @Override // com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks
                public void onRemoteDisplaySessionEnded(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
                }

                @Override // com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks
                public void onRemoteDisplaySessionStarted(CastRemoteDisplayLocalService service) {
                }

                @Override // com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks
                public void onServiceCreated(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
                }

                @Override // com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks
                public void onRemoteDisplaySessionError(Status errorReason) {
                    MyMediaRouterCallback.this.onCastStop();
                    activity.finish();
                }

                @Override // com.google.android.gms.cast.CastRemoteDisplayLocalService.Callbacks
                public void onRemoteDisplayMuteStateChanged(boolean muted) {
                    Log.d("CastManager", "Remote display mute state changed to: " + muted);
                }
            });
        }
    }
}
