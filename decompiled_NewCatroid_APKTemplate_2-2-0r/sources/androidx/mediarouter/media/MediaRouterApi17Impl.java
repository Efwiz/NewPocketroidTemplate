package androidx.mediarouter.media;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
final class MediaRouterApi17Impl {
    private static final String TAG = "MediaRouterJellybeanMr1";

    public interface Callback extends MediaRouterApi16Impl.Callback {
        void onRoutePresentationDisplayChanged(android.media.MediaRouter.RouteInfo routeInfo);
    }

    private MediaRouterApi17Impl() {
    }

    public static android.media.MediaRouter.Callback createCallback(Callback callback) {
        return new CallbackProxy(callback);
    }

    public static final class RouteInfo {
        private RouteInfo() {
        }

        public static boolean isEnabled(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.isEnabled();
        }

        public static Display getPresentationDisplay(android.media.MediaRouter.RouteInfo routeInfo) {
            try {
                return routeInfo.getPresentationDisplay();
            } catch (NoSuchMethodError e) {
                Log.w(MediaRouterApi17Impl.TAG, "Cannot get presentation display for the route.", e);
                return null;
            }
        }
    }

    public static final class ActiveScanWorkaround implements Runnable {
        private static final int WIFI_DISPLAY_SCAN_INTERVAL = 15000;
        private boolean mActivelyScanningWifiDisplays;
        private final DisplayManager mDisplayManager;
        private final Handler mHandler;
        private Method mScanWifiDisplaysMethod;

        ActiveScanWorkaround(Context context, Handler handler) {
            throw new UnsupportedOperationException();
        }

        public void setActiveScanRouteTypes(int i) {
            if ((i & 2) != 0) {
                if (this.mActivelyScanningWifiDisplays) {
                    return;
                }
                if (this.mScanWifiDisplaysMethod != null) {
                    this.mActivelyScanningWifiDisplays = true;
                    this.mHandler.post(this);
                    return;
                } else {
                    Log.w(MediaRouterApi17Impl.TAG, "Cannot scan for wifi displays because the DisplayManager.scanWifiDisplays() method is not available on this device.");
                    return;
                }
            }
            if (this.mActivelyScanningWifiDisplays) {
                this.mActivelyScanningWifiDisplays = false;
                this.mHandler.removeCallbacks(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mActivelyScanningWifiDisplays) {
                try {
                    this.mScanWifiDisplaysMethod.invoke(this.mDisplayManager, new Object[0]);
                } catch (IllegalAccessException e) {
                    Log.w(MediaRouterApi17Impl.TAG, "Cannot scan for wifi displays.", e);
                } catch (InvocationTargetException e2) {
                    Log.w(MediaRouterApi17Impl.TAG, "Cannot scan for wifi displays.", e2);
                }
                this.mHandler.postDelayed(this, AbstractTrafficShapingHandler.DEFAULT_MAX_TIME);
            }
        }
    }

    public static final class IsConnectingWorkaround {
        private Method mGetStatusCodeMethod;
        private int mStatusConnecting;

        IsConnectingWorkaround() {
            throw new UnsupportedOperationException();
        }

        public boolean isConnecting(android.media.MediaRouter.RouteInfo routeInfo) {
            Method method = this.mGetStatusCodeMethod;
            if (method == null) {
                return false;
            }
            try {
                return ((Integer) method.invoke(routeInfo, new Object[0])).intValue() == this.mStatusConnecting;
            } catch (IllegalAccessException | InvocationTargetException unused) {
                return false;
            }
        }
    }

    static class CallbackProxy<T extends Callback> extends MediaRouterApi16Impl.CallbackProxy<T> {
        CallbackProxy(T t) {
            super(t);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRoutePresentationDisplayChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            ((Callback) this.mCallback).onRoutePresentationDisplayChanged(routeInfo);
        }
    }
}
