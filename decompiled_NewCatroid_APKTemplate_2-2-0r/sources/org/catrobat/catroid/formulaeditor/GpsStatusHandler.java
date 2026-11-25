package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import java.util.concurrent.Executor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/GpsStatusHandler.class
  classes14.dex
 */
/* JADX INFO: compiled from: GpsStatusHandler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0013B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u0011J\u0018\u0010\u0012\u001a\u00020\f2\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0005\u001a\b\u0018\u00010\u0006R\u00020\u0000X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/GpsStatusHandler;", "", "sensorHandler", "Lorg/catrobat/catroid/formulaeditor/SensorHandler;", "(Lorg/catrobat/catroid/formulaeditor/SensorHandler;)V", "gnssCallback", "Lorg/catrobat/catroid/formulaeditor/GpsStatusHandler$GNSSCallback;", "lastLocationGps", "Landroid/location/Location;", "lastLocationGpsMillis", "", "deregisterGNSSCallback", "", "locationManager", "Landroid/location/LocationManager;", "registerGNSSCallback", "context", "Landroid/content/Context;", "setLastLocationAndGpsMillis", "GNSSCallback", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class GpsStatusHandler {
    private GNSSCallback gnssCallback;
    private Location lastLocationGps;
    private long lastLocationGpsMillis;
    private final SensorHandler sensorHandler;

    public GpsStatusHandler(SensorHandler sensorHandler) {
        Intrinsics.checkNotNullParameter(sensorHandler, "sensorHandler");
        this.sensorHandler = sensorHandler;
    }

    public final void registerGNSSCallback(LocationManager locationManager, Context context) {
        Intrinsics.checkNotNullParameter(locationManager, "locationManager");
        Intrinsics.checkNotNullParameter(context, "context");
        if (Build.VERSION.SDK_INT >= 30) {
            this.gnssCallback = new GNSSCallback();
            if (ActivityCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                return;
            }
            Executor mainExecutor = context.getMainExecutor();
            GNSSCallback gNSSCallback = this.gnssCallback;
            Intrinsics.checkNotNull(gNSSCallback);
            locationManager.registerGnssStatusCallback(mainExecutor, gNSSCallback);
        } else {
            this.gnssCallback = new GNSSCallback();
        }
        GNSSCallback gNSSCallback2 = this.gnssCallback;
        Intrinsics.checkNotNull(gNSSCallback2);
        locationManager.registerGnssStatusCallback(gNSSCallback2);
    }

    public final void deregisterGNSSCallback(LocationManager locationManager) {
        Intrinsics.checkNotNullParameter(locationManager, "locationManager");
        GNSSCallback gNSSCallback = this.gnssCallback;
        if (gNSSCallback != null) {
            Intrinsics.checkNotNull(gNSSCallback);
            locationManager.unregisterGnssStatusCallback(gNSSCallback);
        }
    }

    public final void setLastLocationAndGpsMillis(Location lastLocationGps, long lastLocationGpsMillis) {
        this.lastLocationGps = lastLocationGps;
        this.lastLocationGpsMillis = lastLocationGpsMillis;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/GpsStatusHandler$GNSSCallback.class
      classes14.dex
     */
    /* JADX INFO: compiled from: GpsStatusHandler.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016¨\u0006\u000b"}, d2 = {"Lorg/catrobat/catroid/formulaeditor/GpsStatusHandler$GNSSCallback;", "Landroid/location/GnssStatus$Callback;", "(Lorg/catrobat/catroid/formulaeditor/GpsStatusHandler;)V", "onFirstFix", "", "ttffMillis", "", "onSatelliteStatusChanged", NotificationCompat.CATEGORY_STATUS, "Landroid/location/GnssStatus;", "onStopped", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class GNSSCallback extends GnssStatus.Callback {
        public GNSSCallback() {
        }

        @Override // android.location.GnssStatus.Callback
        public void onStopped() {
            GpsStatusHandler.this.sensorHandler.setIsGpsConnected(false);
        }

        @Override // android.location.GnssStatus.Callback
        public void onFirstFix(int ttffMillis) {
            GpsStatusHandler.this.sensorHandler.setIsGpsConnected(true);
        }

        @Override // android.location.GnssStatus.Callback
        public void onSatelliteStatusChanged(GnssStatus status) {
            Intrinsics.checkNotNullParameter(status, "status");
            if (GpsStatusHandler.this.lastLocationGps != null) {
                GpsStatusHandler.this.sensorHandler.setIsGpsConnected(SystemClock.elapsedRealtime() - GpsStatusHandler.this.lastLocationGpsMillis < DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS);
            }
        }
    }
}
