package org.catrobat.catroid.common;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.core.app.NotificationCompat;
import com.gaurav.avnc.vnc.XKeySym;
import com.huawei.hms.support.api.entity.core.CommonCode;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;
import org.catrobat.catroid.ui.SpriteActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/common/NewCatroidBackgroundService.class
  classes12.dex
 */
/* JADX INFO: compiled from: NewCatroidBackgroundService.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\fH\u0016J\"\u0010\u000e\u001a\u00020\u000f2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/catrobat/catroid/common/NewCatroidBackgroundService;", "Landroid/app/Service;", "()V", "isTicking", "", "tickThread", "Ljava/lang/Thread;", "onBind", "Landroid/os/IBinder;", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "onCreate", "", "onDestroy", "onStartCommand", "", OverlayablePolicy.NAME_flags, "startId", "startLogicTicker", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCatroidBackgroundService extends Service {
    private boolean isTicking;
    private Thread tickThread;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        String stringExtra;
        String stringExtra2;
        if (intent == null || (stringExtra = intent.getStringExtra("TITLE")) == null) {
            stringExtra = "Работа в фоне";
        }
        if (intent == null || (stringExtra2 = intent.getStringExtra(SpriteActivity.EXTRA_TEXT)) == null) {
            stringExtra2 = "Выполняются скрипты...";
        }
        Object systemService = getSystemService("notification");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.app.NotificationManager");
        ((NotificationManager) systemService).createNotificationChannel(new NotificationChannel("catroid_bg_service_channel", "Фоновый режим игры", 2));
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
        Notification notificationBuild = new NotificationCompat.Builder(this, "catroid_bg_service_channel").setContentTitle(stringExtra).setContentText(stringExtra2).setSmallIcon(getApplicationContext().getApplicationInfo().icon).setContentIntent(launchIntentForPackage != null ? PendingIntent.getActivity(this, 0, launchIntentForPackage, 67108864 | 134217728) : null).setOngoing(true).build();
        Intrinsics.checkNotNullExpressionValue(notificationBuild, "build(...)");
        startForeground(XKeySym.XK_Greek_XI, notificationBuild);
        startLogicTicker();
        return 1;
    }

    private final void startLogicTicker() {
        if (this.isTicking) {
            return;
        }
        this.isTicking = true;
        this.tickThread = ThreadsKt.thread$default(true, false, null, null, 0, new Function0<Unit>() { // from class: org.catrobat.catroid.common.NewCatroidBackgroundService.startLogicTicker.1
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
                StageListener activeStageListener;
                long jNanoTime = System.nanoTime();
                while (NewCatroidBackgroundService.this.isTicking) {
                    long jNanoTime2 = System.nanoTime();
                    float f = (jNanoTime2 - jNanoTime) / 1.0E9f;
                    try {
                        if (StageActivity.isAppPaused && (activeStageListener = StageActivity.getActiveStageListener()) != null && activeStageListener.isBackgroundModeEnabled && !activeStageListener.isFinished()) {
                            if (f > 0.1f) {
                                f = 0.016f;
                            }
                            activeStageListener.backgroundTick(f);
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    try {
                        Thread.sleep(16L);
                        jNanoTime = jNanoTime2;
                    } catch (InterruptedException unused) {
                        return;
                    }
                }
            }
        }, 30, null);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.isTicking = false;
        Thread thread = this.tickThread;
        if (thread != null) {
            thread.interrupt();
        }
        this.tickThread = null;
        super.onDestroy();
    }
}
