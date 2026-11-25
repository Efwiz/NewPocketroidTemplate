package androidx.mediarouter.media;

import android.content.Context;
import android.media.RemoteControlClient;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
abstract class RemoteControlClientCompat {
    protected final Context mContext;
    protected final RemoteControlClient mRcc;
    protected VolumeCallback mVolumeCallback;

    public static final class PlaybackInfo {
        public int volume;
        public String volumeControlId;
        public int volumeMax;
        public int volumeHandling = 0;
        public int playbackStream = 3;
        public int playbackType = 1;
    }

    public interface VolumeCallback {
        void onVolumeSetRequest(int i);

        void onVolumeUpdateRequest(int i);
    }

    public void setPlaybackInfo(PlaybackInfo playbackInfo) {
    }

    protected RemoteControlClientCompat(Context context, RemoteControlClient remoteControlClient) {
        this.mContext = context;
        this.mRcc = remoteControlClient;
    }

    public static RemoteControlClientCompat obtain(Context context, RemoteControlClient remoteControlClient) {
        return new JellybeanImpl(context, remoteControlClient);
    }

    public RemoteControlClient getRemoteControlClient() {
        return this.mRcc;
    }

    public void setVolumeCallback(VolumeCallback volumeCallback) {
        this.mVolumeCallback = volumeCallback;
    }

    static class LegacyImpl extends RemoteControlClientCompat {
        LegacyImpl(Context context, RemoteControlClient remoteControlClient) {
            super(context, remoteControlClient);
        }
    }

    static class JellybeanImpl extends RemoteControlClientCompat {
        private boolean mRegistered;
        private final android.media.MediaRouter mRouter;
        private final android.media.MediaRouter.UserRouteInfo mUserRoute;
        private final android.media.MediaRouter.RouteCategory mUserRouteCategory;

        JellybeanImpl(Context context, RemoteControlClient remoteControlClient) {
            super(context, remoteControlClient);
            android.media.MediaRouter mediaRouter = MediaRouterApi16Impl.getMediaRouter(context);
            this.mRouter = mediaRouter;
            android.media.MediaRouter.RouteCategory routeCategoryCreateRouteCategory = MediaRouterApi16Impl.createRouteCategory(mediaRouter, "", false);
            this.mUserRouteCategory = routeCategoryCreateRouteCategory;
            this.mUserRoute = MediaRouterApi16Impl.createUserRoute(mediaRouter, routeCategoryCreateRouteCategory);
        }

        @Override // androidx.mediarouter.media.RemoteControlClientCompat
        public void setPlaybackInfo(PlaybackInfo playbackInfo) {
            MediaRouterApi16Impl.UserRouteInfo.setVolume(this.mUserRoute, playbackInfo.volume);
            MediaRouterApi16Impl.UserRouteInfo.setVolumeMax(this.mUserRoute, playbackInfo.volumeMax);
            MediaRouterApi16Impl.UserRouteInfo.setVolumeHandling(this.mUserRoute, playbackInfo.volumeHandling);
            MediaRouterApi16Impl.UserRouteInfo.setPlaybackStream(this.mUserRoute, playbackInfo.playbackStream);
            MediaRouterApi16Impl.UserRouteInfo.setPlaybackType(this.mUserRoute, playbackInfo.playbackType);
            if (this.mRegistered) {
                return;
            }
            this.mRegistered = true;
            MediaRouterApi16Impl.UserRouteInfo.setVolumeCallback(this.mUserRoute, MediaRouterApi16Impl.createVolumeCallback(new VolumeCallbackWrapper(this)));
            MediaRouterApi16Impl.UserRouteInfo.setRemoteControlClient(this.mUserRoute, this.mRcc);
        }

        private static final class VolumeCallbackWrapper implements MediaRouterApi16Impl.VolumeCallback {
            private final WeakReference<JellybeanImpl> mImplWeak;

            public VolumeCallbackWrapper(JellybeanImpl jellybeanImpl) {
                this.mImplWeak = new WeakReference<>(jellybeanImpl);
            }

            @Override // androidx.mediarouter.media.MediaRouterApi16Impl.VolumeCallback
            public void onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
                JellybeanImpl jellybeanImpl = this.mImplWeak.get();
                if (jellybeanImpl == null || jellybeanImpl.mVolumeCallback == null) {
                    return;
                }
                jellybeanImpl.mVolumeCallback.onVolumeUpdateRequest(i);
            }

            @Override // androidx.mediarouter.media.MediaRouterApi16Impl.VolumeCallback
            public void onVolumeSetRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
                JellybeanImpl jellybeanImpl = this.mImplWeak.get();
                if (jellybeanImpl == null || jellybeanImpl.mVolumeCallback == null) {
                    return;
                }
                jellybeanImpl.mVolumeCallback.onVolumeSetRequest(i);
            }
        }
    }
}
