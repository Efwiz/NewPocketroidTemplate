package androidx.mediarouter.media;

import android.content.Context;
import android.media.RemoteControlClient;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class MediaRouterApi16Impl {
    public static final int ALL_ROUTE_TYPES = 8388611;
    public static final int ROUTE_TYPE_LIVE_AUDIO = 1;
    public static final int ROUTE_TYPE_LIVE_VIDEO = 2;
    public static final int ROUTE_TYPE_USER = 8388608;
    private static final String TAG = "MediaRouterJellybean";

    public interface Callback {
        void onRouteAdded(android.media.MediaRouter.RouteInfo routeInfo);

        void onRouteChanged(android.media.MediaRouter.RouteInfo routeInfo);

        void onRouteGrouped(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i);

        void onRouteRemoved(android.media.MediaRouter.RouteInfo routeInfo);

        void onRouteSelected(int i, android.media.MediaRouter.RouteInfo routeInfo);

        void onRouteUngrouped(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup);

        void onRouteUnselected(int i, android.media.MediaRouter.RouteInfo routeInfo);

        void onRouteVolumeChanged(android.media.MediaRouter.RouteInfo routeInfo);
    }

    public interface VolumeCallback {
        void onVolumeSetRequest(android.media.MediaRouter.RouteInfo routeInfo, int i);

        void onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo routeInfo, int i);
    }

    private MediaRouterApi16Impl() {
    }

    public static android.media.MediaRouter getMediaRouter(Context context) {
        return (android.media.MediaRouter) context.getSystemService("media_router");
    }

    public static List<android.media.MediaRouter.RouteInfo> getRoutes(android.media.MediaRouter mediaRouter) {
        int routeCount = mediaRouter.getRouteCount();
        ArrayList arrayList = new ArrayList(routeCount);
        for (int i = 0; i < routeCount; i++) {
            arrayList.add(mediaRouter.getRouteAt(i));
        }
        return arrayList;
    }

    public static android.media.MediaRouter.RouteInfo getSelectedRoute(android.media.MediaRouter mediaRouter, int i) {
        return mediaRouter.getSelectedRoute(i);
    }

    public static void selectRoute(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
        mediaRouter.selectRoute(i, routeInfo);
    }

    public static void addCallback(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.Callback callback) {
        mediaRouter.addCallback(i, callback);
    }

    public static void removeCallback(android.media.MediaRouter mediaRouter, android.media.MediaRouter.Callback callback) {
        mediaRouter.removeCallback(callback);
    }

    public static android.media.MediaRouter.RouteCategory createRouteCategory(android.media.MediaRouter mediaRouter, String str, boolean z) {
        return mediaRouter.createRouteCategory(str, z);
    }

    public static android.media.MediaRouter.UserRouteInfo createUserRoute(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteCategory routeCategory) {
        return mediaRouter.createUserRoute(routeCategory);
    }

    public static void addUserRoute(android.media.MediaRouter mediaRouter, android.media.MediaRouter.UserRouteInfo userRouteInfo) {
        mediaRouter.addUserRoute(userRouteInfo);
    }

    public static void removeUserRoute(android.media.MediaRouter mediaRouter, android.media.MediaRouter.UserRouteInfo userRouteInfo) {
        try {
            mediaRouter.removeUserRoute(userRouteInfo);
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "Failed to remove user route", e);
        }
    }

    public static android.media.MediaRouter.Callback createCallback(Callback callback) {
        return new CallbackProxy(callback);
    }

    public static android.media.MediaRouter.VolumeCallback createVolumeCallback(VolumeCallback volumeCallback) {
        return new VolumeCallbackProxy(volumeCallback);
    }

    public static final class RouteInfo {
        private RouteInfo() {
        }

        public static CharSequence getName(android.media.MediaRouter.RouteInfo routeInfo, Context context) {
            return routeInfo.getName(context);
        }

        public static int getSupportedTypes(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.getSupportedTypes();
        }

        public static int getPlaybackType(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.getPlaybackType();
        }

        public static int getPlaybackStream(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.getPlaybackStream();
        }

        public static int getVolume(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.getVolume();
        }

        public static int getVolumeMax(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.getVolumeMax();
        }

        public static int getVolumeHandling(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.getVolumeHandling();
        }

        public static Object getTag(android.media.MediaRouter.RouteInfo routeInfo) {
            return routeInfo.getTag();
        }

        public static void setTag(android.media.MediaRouter.RouteInfo routeInfo, Object obj) {
            routeInfo.setTag(obj);
        }

        public static void requestSetVolume(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            routeInfo.requestSetVolume(i);
        }

        public static void requestUpdateVolume(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            routeInfo.requestUpdateVolume(i);
        }
    }

    public static final class UserRouteInfo {
        private UserRouteInfo() {
        }

        public static void setName(android.media.MediaRouter.UserRouteInfo userRouteInfo, CharSequence charSequence) {
            userRouteInfo.setName(charSequence);
        }

        public static void setPlaybackType(android.media.MediaRouter.UserRouteInfo userRouteInfo, int i) {
            userRouteInfo.setPlaybackType(i);
        }

        public static void setPlaybackStream(android.media.MediaRouter.UserRouteInfo userRouteInfo, int i) {
            userRouteInfo.setPlaybackStream(i);
        }

        public static void setVolume(android.media.MediaRouter.UserRouteInfo userRouteInfo, int i) {
            userRouteInfo.setVolume(i);
        }

        public static void setVolumeMax(android.media.MediaRouter.UserRouteInfo userRouteInfo, int i) {
            userRouteInfo.setVolumeMax(i);
        }

        public static void setVolumeHandling(android.media.MediaRouter.UserRouteInfo userRouteInfo, int i) {
            userRouteInfo.setVolumeHandling(i);
        }

        public static void setVolumeCallback(android.media.MediaRouter.UserRouteInfo userRouteInfo, android.media.MediaRouter.VolumeCallback volumeCallback) {
            userRouteInfo.setVolumeCallback(volumeCallback);
        }

        public static void setRemoteControlClient(android.media.MediaRouter.UserRouteInfo userRouteInfo, RemoteControlClient remoteControlClient) {
            userRouteInfo.setRemoteControlClient(remoteControlClient);
        }
    }

    public static final class SelectRouteWorkaround {
        private Method mSelectRouteIntMethod;

        SelectRouteWorkaround() {
            throw new UnsupportedOperationException();
        }

        public void selectRoute(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            if ((routeInfo.getSupportedTypes() & 8388608) == 0) {
                Method method = this.mSelectRouteIntMethod;
                if (method != null) {
                    try {
                        method.invoke(mediaRouter, Integer.valueOf(i), routeInfo);
                        return;
                    } catch (IllegalAccessException e) {
                        Log.w(MediaRouterApi16Impl.TAG, "Cannot programmatically select non-user route.  Media routing may not work.", e);
                    } catch (InvocationTargetException e2) {
                        Log.w(MediaRouterApi16Impl.TAG, "Cannot programmatically select non-user route.  Media routing may not work.", e2);
                    }
                } else {
                    Log.w(MediaRouterApi16Impl.TAG, "Cannot programmatically select non-user route because the platform is missing the selectRouteInt() method.  Media routing may not work.");
                }
            }
            mediaRouter.selectRoute(i, routeInfo);
        }
    }

    public static final class GetDefaultRouteWorkaround {
        private Method mGetSystemAudioRouteMethod;

        GetDefaultRouteWorkaround() {
            throw new UnsupportedOperationException();
        }

        public Object getDefaultRoute(android.media.MediaRouter mediaRouter) {
            Method method = this.mGetSystemAudioRouteMethod;
            if (method != null) {
                try {
                    return method.invoke(mediaRouter, new Object[0]);
                } catch (IllegalAccessException | InvocationTargetException unused) {
                }
            }
            return mediaRouter.getRouteAt(0);
        }
    }

    static class CallbackProxy<T extends Callback> extends android.media.MediaRouter.Callback {
        protected final T mCallback;

        CallbackProxy(T t) {
            this.mCallback = t;
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteSelected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            this.mCallback.onRouteSelected(i, routeInfo);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUnselected(android.media.MediaRouter mediaRouter, int i, android.media.MediaRouter.RouteInfo routeInfo) {
            this.mCallback.onRouteUnselected(i, routeInfo);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteAdded(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.mCallback.onRouteAdded(routeInfo);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteRemoved(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.mCallback.onRouteRemoved(routeInfo);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.mCallback.onRouteChanged(routeInfo);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteGrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i) {
            this.mCallback.onRouteGrouped(routeInfo, routeGroup, i);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteUngrouped(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup) {
            this.mCallback.onRouteUngrouped(routeInfo, routeGroup);
        }

        @Override // android.media.MediaRouter.Callback
        public void onRouteVolumeChanged(android.media.MediaRouter mediaRouter, android.media.MediaRouter.RouteInfo routeInfo) {
            this.mCallback.onRouteVolumeChanged(routeInfo);
        }
    }

    static class VolumeCallbackProxy<T extends VolumeCallback> extends android.media.MediaRouter.VolumeCallback {
        protected final T mCallback;

        VolumeCallbackProxy(T t) {
            this.mCallback = t;
        }

        @Override // android.media.MediaRouter.VolumeCallback
        public void onVolumeSetRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            this.mCallback.onVolumeSetRequest(routeInfo, i);
        }

        @Override // android.media.MediaRouter.VolumeCallback
        public void onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            this.mCallback.onVolumeUpdateRequest(routeInfo, i);
        }
    }
}
