package androidx.mediarouter.media;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.AudioManager;
import android.view.Display;
import androidx.mediarouter.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
abstract class SystemMediaRouteProvider extends MediaRouteProvider {
    public static final String DEFAULT_ROUTE_ID = "DEFAULT_ROUTE";
    public static final String PACKAGE_NAME = "android";

    public interface SyncCallback {
        void onSystemRouteSelectedByDescriptorId(String str);
    }

    public void onSyncRouteAdded(MediaRouter.RouteInfo routeInfo) {
    }

    public void onSyncRouteChanged(MediaRouter.RouteInfo routeInfo) {
    }

    public void onSyncRouteRemoved(MediaRouter.RouteInfo routeInfo) {
    }

    public void onSyncRouteSelected(MediaRouter.RouteInfo routeInfo) {
    }

    protected SystemMediaRouteProvider(Context context) {
        super(context, new MediaRouteProvider.ProviderMetadata(new ComponentName("android", SystemMediaRouteProvider.class.getName())));
    }

    public static SystemMediaRouteProvider obtain(Context context, SyncCallback syncCallback) {
        return new Api24Impl(context, syncCallback);
    }

    static class LegacyImpl extends SystemMediaRouteProvider {
        private static final ArrayList<IntentFilter> CONTROL_FILTERS;
        static final int PLAYBACK_STREAM = 3;
        final AudioManager mAudioManager;
        int mLastReportedVolume;
        private final VolumeChangeReceiver mVolumeChangeReceiver;

        static {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO);
            intentFilter.addCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
            ArrayList<IntentFilter> arrayList = new ArrayList<>();
            CONTROL_FILTERS = arrayList;
            arrayList.add(intentFilter);
        }

        public LegacyImpl(Context context) {
            super(context);
            this.mLastReportedVolume = -1;
            this.mAudioManager = (AudioManager) context.getSystemService("audio");
            VolumeChangeReceiver volumeChangeReceiver = new VolumeChangeReceiver();
            this.mVolumeChangeReceiver = volumeChangeReceiver;
            context.registerReceiver(volumeChangeReceiver, new IntentFilter(VolumeChangeReceiver.VOLUME_CHANGED_ACTION));
            publishRoutes();
        }

        void publishRoutes() {
            Resources resources = getContext().getResources();
            int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
            this.mLastReportedVolume = this.mAudioManager.getStreamVolume(3);
            setDescriptor(new MediaRouteProviderDescriptor.Builder().addRoute(new MediaRouteDescriptor.Builder(SystemMediaRouteProvider.DEFAULT_ROUTE_ID, resources.getString(R.string.mr_system_route_name)).addControlFilters(CONTROL_FILTERS).setPlaybackStream(3).setPlaybackType(0).setVolumeHandling(1).setVolumeMax(streamMaxVolume).setVolume(this.mLastReportedVolume).build()).build());
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public MediaRouteProvider.RouteController onCreateRouteController(String str) {
            if (str.equals(SystemMediaRouteProvider.DEFAULT_ROUTE_ID)) {
                return new DefaultRouteController();
            }
            return null;
        }

        final class DefaultRouteController extends MediaRouteProvider.RouteController {
            DefaultRouteController() {
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onSetVolume(int i) {
                LegacyImpl.this.mAudioManager.setStreamVolume(3, i, 0);
                LegacyImpl.this.publishRoutes();
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onUpdateVolume(int i) {
                int streamVolume = LegacyImpl.this.mAudioManager.getStreamVolume(3);
                if (Math.min(LegacyImpl.this.mAudioManager.getStreamMaxVolume(3), Math.max(0, i + streamVolume)) != streamVolume) {
                    LegacyImpl.this.mAudioManager.setStreamVolume(3, streamVolume, 0);
                }
                LegacyImpl.this.publishRoutes();
            }
        }

        final class VolumeChangeReceiver extends BroadcastReceiver {
            public static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
            public static final String EXTRA_VOLUME_STREAM_VALUE = "android.media.EXTRA_VOLUME_STREAM_VALUE";
            public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";

            VolumeChangeReceiver() {
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int intExtra;
                if (!intent.getAction().equals(VOLUME_CHANGED_ACTION) || intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, -1) != 3 || (intExtra = intent.getIntExtra(EXTRA_VOLUME_STREAM_VALUE, -1)) < 0 || intExtra == LegacyImpl.this.mLastReportedVolume) {
                    return;
                }
                LegacyImpl.this.publishRoutes();
            }
        }
    }

    static class JellybeanImpl extends SystemMediaRouteProvider implements MediaRouterApi16Impl.Callback, MediaRouterApi16Impl.VolumeCallback {
        private static final ArrayList<IntentFilter> LIVE_AUDIO_CONTROL_FILTERS;
        private static final ArrayList<IntentFilter> LIVE_VIDEO_CONTROL_FILTERS;
        protected boolean mActiveScan;
        protected final android.media.MediaRouter.Callback mCallback;
        protected boolean mCallbackRegistered;
        private MediaRouterApi16Impl.GetDefaultRouteWorkaround mGetDefaultRouteWorkaround;
        protected int mRouteTypes;
        protected final android.media.MediaRouter mRouter;
        private MediaRouterApi16Impl.SelectRouteWorkaround mSelectRouteWorkaround;
        private final SyncCallback mSyncCallback;
        protected final ArrayList<SystemRouteRecord> mSystemRouteRecords;
        protected final android.media.MediaRouter.RouteCategory mUserRouteCategory;
        protected final ArrayList<UserRouteRecord> mUserRouteRecords;
        protected final android.media.MediaRouter.VolumeCallback mVolumeCallback;

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteGrouped(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup, int i) {
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteUngrouped(android.media.MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.RouteGroup routeGroup) {
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteUnselected(int i, android.media.MediaRouter.RouteInfo routeInfo) {
        }

        static {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO);
            ArrayList<IntentFilter> arrayList = new ArrayList<>();
            LIVE_AUDIO_CONTROL_FILTERS = arrayList;
            arrayList.add(intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
            ArrayList<IntentFilter> arrayList2 = new ArrayList<>();
            LIVE_VIDEO_CONTROL_FILTERS = arrayList2;
            arrayList2.add(intentFilter2);
        }

        public JellybeanImpl(Context context, SyncCallback syncCallback) {
            super(context);
            this.mSystemRouteRecords = new ArrayList<>();
            this.mUserRouteRecords = new ArrayList<>();
            this.mSyncCallback = syncCallback;
            android.media.MediaRouter mediaRouter = MediaRouterApi16Impl.getMediaRouter(context);
            this.mRouter = mediaRouter;
            this.mCallback = createCallback();
            this.mVolumeCallback = createVolumeCallback();
            this.mUserRouteCategory = MediaRouterApi16Impl.createRouteCategory(mediaRouter, context.getResources().getString(R.string.mr_user_route_category_name), false);
            updateSystemRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public MediaRouteProvider.RouteController onCreateRouteController(String str) {
            int iFindSystemRouteRecordByDescriptorId = findSystemRouteRecordByDescriptorId(str);
            if (iFindSystemRouteRecordByDescriptorId >= 0) {
                return new SystemRouteController(this.mSystemRouteRecords.get(iFindSystemRouteRecordByDescriptorId).mRoute);
            }
            return null;
        }

        @Override // androidx.mediarouter.media.MediaRouteProvider
        public void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
            boolean zIsActiveScan;
            int i = 0;
            if (mediaRouteDiscoveryRequest != null) {
                List<String> controlCategories = mediaRouteDiscoveryRequest.getSelector().getControlCategories();
                int size = controlCategories.size();
                int i2 = 0;
                while (i < size) {
                    String str = controlCategories.get(i);
                    if (str.equals(MediaControlIntent.CATEGORY_LIVE_AUDIO)) {
                        i2 |= 1;
                    } else {
                        i2 = str.equals(MediaControlIntent.CATEGORY_LIVE_VIDEO) ? i2 | 2 : i2 | 8388608;
                    }
                    i++;
                }
                zIsActiveScan = mediaRouteDiscoveryRequest.isActiveScan();
                i = i2;
            } else {
                zIsActiveScan = false;
            }
            if (this.mRouteTypes == i && this.mActiveScan == zIsActiveScan) {
                return;
            }
            this.mRouteTypes = i;
            this.mActiveScan = zIsActiveScan;
            updateSystemRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteAdded(android.media.MediaRouter.RouteInfo routeInfo) {
            if (addSystemRouteNoPublish(routeInfo)) {
                publishRoutes();
            }
        }

        private void updateSystemRoutes() {
            updateCallback();
            Iterator<android.media.MediaRouter.RouteInfo> it = MediaRouterApi16Impl.getRoutes(this.mRouter).iterator();
            boolean zAddSystemRouteNoPublish = false;
            while (it.hasNext()) {
                zAddSystemRouteNoPublish |= addSystemRouteNoPublish(it.next());
            }
            if (zAddSystemRouteNoPublish) {
                publishRoutes();
            }
        }

        private boolean addSystemRouteNoPublish(android.media.MediaRouter.RouteInfo routeInfo) {
            if (getUserRouteRecord(routeInfo) != null || findSystemRouteRecord(routeInfo) >= 0) {
                return false;
            }
            SystemRouteRecord systemRouteRecord = new SystemRouteRecord(routeInfo, assignRouteId(routeInfo));
            updateSystemRouteDescriptor(systemRouteRecord);
            this.mSystemRouteRecords.add(systemRouteRecord);
            return true;
        }

        private String assignRouteId(android.media.MediaRouter.RouteInfo routeInfo) {
            String str;
            if (getDefaultRoute() == routeInfo) {
                str = SystemMediaRouteProvider.DEFAULT_ROUTE_ID;
            } else {
                str = String.format(Locale.US, "ROUTE_%08x", Integer.valueOf(getRouteName(routeInfo).hashCode()));
            }
            if (findSystemRouteRecordByDescriptorId(str) < 0) {
                return str;
            }
            int i = 2;
            while (true) {
                String str2 = String.format(Locale.US, "%s_%d", str, Integer.valueOf(i));
                if (findSystemRouteRecordByDescriptorId(str2) < 0) {
                    return str2;
                }
                i++;
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteRemoved(android.media.MediaRouter.RouteInfo routeInfo) {
            int iFindSystemRouteRecord;
            if (getUserRouteRecord(routeInfo) != null || (iFindSystemRouteRecord = findSystemRouteRecord(routeInfo)) < 0) {
                return;
            }
            this.mSystemRouteRecords.remove(iFindSystemRouteRecord);
            publishRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteChanged(android.media.MediaRouter.RouteInfo routeInfo) {
            int iFindSystemRouteRecord;
            if (getUserRouteRecord(routeInfo) != null || (iFindSystemRouteRecord = findSystemRouteRecord(routeInfo)) < 0) {
                return;
            }
            updateSystemRouteDescriptor(this.mSystemRouteRecords.get(iFindSystemRouteRecord));
            publishRoutes();
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteVolumeChanged(android.media.MediaRouter.RouteInfo routeInfo) {
            int iFindSystemRouteRecord;
            if (getUserRouteRecord(routeInfo) != null || (iFindSystemRouteRecord = findSystemRouteRecord(routeInfo)) < 0) {
                return;
            }
            SystemRouteRecord systemRouteRecord = this.mSystemRouteRecords.get(iFindSystemRouteRecord);
            int volume = MediaRouterApi16Impl.RouteInfo.getVolume(routeInfo);
            if (volume != systemRouteRecord.mRouteDescriptor.getVolume()) {
                systemRouteRecord.mRouteDescriptor = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptor).setVolume(volume).build();
                publishRoutes();
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.Callback
        public void onRouteSelected(int i, android.media.MediaRouter.RouteInfo routeInfo) {
            if (routeInfo != MediaRouterApi16Impl.getSelectedRoute(this.mRouter, 8388611)) {
                return;
            }
            UserRouteRecord userRouteRecord = getUserRouteRecord(routeInfo);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.select();
                return;
            }
            int iFindSystemRouteRecord = findSystemRouteRecord(routeInfo);
            if (iFindSystemRouteRecord >= 0) {
                this.mSyncCallback.onSystemRouteSelectedByDescriptorId(this.mSystemRouteRecords.get(iFindSystemRouteRecord).mRouteDescriptorId);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.VolumeCallback
        public void onVolumeSetRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            UserRouteRecord userRouteRecord = getUserRouteRecord(routeInfo);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.requestSetVolume(i);
            }
        }

        @Override // androidx.mediarouter.media.MediaRouterApi16Impl.VolumeCallback
        public void onVolumeUpdateRequest(android.media.MediaRouter.RouteInfo routeInfo, int i) {
            UserRouteRecord userRouteRecord = getUserRouteRecord(routeInfo);
            if (userRouteRecord != null) {
                userRouteRecord.mRoute.requestUpdateVolume(i);
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteAdded(MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.getProviderInstance() != this) {
                android.media.MediaRouter.UserRouteInfo userRouteInfoCreateUserRoute = MediaRouterApi16Impl.createUserRoute(this.mRouter, this.mUserRouteCategory);
                UserRouteRecord userRouteRecord = new UserRouteRecord(routeInfo, userRouteInfoCreateUserRoute);
                MediaRouterApi16Impl.RouteInfo.setTag(userRouteInfoCreateUserRoute, userRouteRecord);
                MediaRouterApi16Impl.UserRouteInfo.setVolumeCallback(userRouteInfoCreateUserRoute, this.mVolumeCallback);
                updateUserRouteProperties(userRouteRecord);
                this.mUserRouteRecords.add(userRouteRecord);
                MediaRouterApi16Impl.addUserRoute(this.mRouter, userRouteInfoCreateUserRoute);
                return;
            }
            int iFindSystemRouteRecord = findSystemRouteRecord(MediaRouterApi16Impl.getSelectedRoute(this.mRouter, 8388611));
            if (iFindSystemRouteRecord < 0 || !this.mSystemRouteRecords.get(iFindSystemRouteRecord).mRouteDescriptorId.equals(routeInfo.getDescriptorId())) {
                return;
            }
            routeInfo.select();
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteRemoved(MediaRouter.RouteInfo routeInfo) {
            int iFindUserRouteRecord;
            if (routeInfo.getProviderInstance() == this || (iFindUserRouteRecord = findUserRouteRecord(routeInfo)) < 0) {
                return;
            }
            UserRouteRecord userRouteRecordRemove = this.mUserRouteRecords.remove(iFindUserRouteRecord);
            MediaRouterApi16Impl.RouteInfo.setTag(userRouteRecordRemove.mUserRoute, null);
            MediaRouterApi16Impl.UserRouteInfo.setVolumeCallback(userRouteRecordRemove.mUserRoute, null);
            MediaRouterApi16Impl.removeUserRoute(this.mRouter, userRouteRecordRemove.mUserRoute);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteChanged(MediaRouter.RouteInfo routeInfo) {
            int iFindUserRouteRecord;
            if (routeInfo.getProviderInstance() == this || (iFindUserRouteRecord = findUserRouteRecord(routeInfo)) < 0) {
                return;
            }
            updateUserRouteProperties(this.mUserRouteRecords.get(iFindUserRouteRecord));
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider
        public void onSyncRouteSelected(MediaRouter.RouteInfo routeInfo) {
            if (routeInfo.isSelected()) {
                if (routeInfo.getProviderInstance() != this) {
                    int iFindUserRouteRecord = findUserRouteRecord(routeInfo);
                    if (iFindUserRouteRecord >= 0) {
                        selectRoute(this.mUserRouteRecords.get(iFindUserRouteRecord).mUserRoute);
                        return;
                    }
                    return;
                }
                int iFindSystemRouteRecordByDescriptorId = findSystemRouteRecordByDescriptorId(routeInfo.getDescriptorId());
                if (iFindSystemRouteRecordByDescriptorId >= 0) {
                    selectRoute(this.mSystemRouteRecords.get(iFindSystemRouteRecordByDescriptorId).mRoute);
                }
            }
        }

        protected void publishRoutes() {
            MediaRouteProviderDescriptor.Builder builder = new MediaRouteProviderDescriptor.Builder();
            int size = this.mSystemRouteRecords.size();
            for (int i = 0; i < size; i++) {
                builder.addRoute(this.mSystemRouteRecords.get(i).mRouteDescriptor);
            }
            setDescriptor(builder.build());
        }

        protected int findSystemRouteRecord(android.media.MediaRouter.RouteInfo routeInfo) {
            int size = this.mSystemRouteRecords.size();
            for (int i = 0; i < size; i++) {
                if (this.mSystemRouteRecords.get(i).mRoute == routeInfo) {
                    return i;
                }
            }
            return -1;
        }

        protected int findSystemRouteRecordByDescriptorId(String str) {
            int size = this.mSystemRouteRecords.size();
            for (int i = 0; i < size; i++) {
                if (this.mSystemRouteRecords.get(i).mRouteDescriptorId.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        protected int findUserRouteRecord(MediaRouter.RouteInfo routeInfo) {
            int size = this.mUserRouteRecords.size();
            for (int i = 0; i < size; i++) {
                if (this.mUserRouteRecords.get(i).mRoute == routeInfo) {
                    return i;
                }
            }
            return -1;
        }

        protected UserRouteRecord getUserRouteRecord(android.media.MediaRouter.RouteInfo routeInfo) {
            Object tag = MediaRouterApi16Impl.RouteInfo.getTag(routeInfo);
            if (tag instanceof UserRouteRecord) {
                return (UserRouteRecord) tag;
            }
            return null;
        }

        protected void updateSystemRouteDescriptor(SystemRouteRecord systemRouteRecord) {
            MediaRouteDescriptor.Builder builder = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptorId, getRouteName(systemRouteRecord.mRoute));
            onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            systemRouteRecord.mRouteDescriptor = builder.build();
        }

        protected String getRouteName(android.media.MediaRouter.RouteInfo routeInfo) {
            CharSequence name2 = MediaRouterApi16Impl.RouteInfo.getName(routeInfo, getContext());
            return name2 != null ? name2.toString() : "";
        }

        protected void onBuildSystemRouteDescriptor(SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            int supportedTypes = MediaRouterApi16Impl.RouteInfo.getSupportedTypes(systemRouteRecord.mRoute);
            if ((supportedTypes & 1) != 0) {
                builder.addControlFilters(LIVE_AUDIO_CONTROL_FILTERS);
            }
            if ((supportedTypes & 2) != 0) {
                builder.addControlFilters(LIVE_VIDEO_CONTROL_FILTERS);
            }
            builder.setPlaybackType(MediaRouterApi16Impl.RouteInfo.getPlaybackType(systemRouteRecord.mRoute));
            builder.setPlaybackStream(MediaRouterApi16Impl.RouteInfo.getPlaybackStream(systemRouteRecord.mRoute));
            builder.setVolume(MediaRouterApi16Impl.RouteInfo.getVolume(systemRouteRecord.mRoute));
            builder.setVolumeMax(MediaRouterApi16Impl.RouteInfo.getVolumeMax(systemRouteRecord.mRoute));
            builder.setVolumeHandling(MediaRouterApi16Impl.RouteInfo.getVolumeHandling(systemRouteRecord.mRoute));
        }

        protected void updateUserRouteProperties(UserRouteRecord userRouteRecord) {
            MediaRouterApi16Impl.UserRouteInfo.setName(userRouteRecord.mUserRoute, userRouteRecord.mRoute.getName());
            MediaRouterApi16Impl.UserRouteInfo.setPlaybackType(userRouteRecord.mUserRoute, userRouteRecord.mRoute.getPlaybackType());
            MediaRouterApi16Impl.UserRouteInfo.setPlaybackStream(userRouteRecord.mUserRoute, userRouteRecord.mRoute.getPlaybackStream());
            MediaRouterApi16Impl.UserRouteInfo.setVolume(userRouteRecord.mUserRoute, userRouteRecord.mRoute.getVolume());
            MediaRouterApi16Impl.UserRouteInfo.setVolumeMax(userRouteRecord.mUserRoute, userRouteRecord.mRoute.getVolumeMax());
            MediaRouterApi16Impl.UserRouteInfo.setVolumeHandling(userRouteRecord.mUserRoute, userRouteRecord.mRoute.getVolumeHandling());
        }

        protected void updateCallback() {
            if (this.mCallbackRegistered) {
                this.mCallbackRegistered = false;
                MediaRouterApi16Impl.removeCallback(this.mRouter, this.mCallback);
            }
            int i = this.mRouteTypes;
            if (i != 0) {
                this.mCallbackRegistered = true;
                MediaRouterApi16Impl.addCallback(this.mRouter, i, this.mCallback);
            }
        }

        protected android.media.MediaRouter.Callback createCallback() {
            return MediaRouterApi16Impl.createCallback(this);
        }

        protected android.media.MediaRouter.VolumeCallback createVolumeCallback() {
            return MediaRouterApi16Impl.createVolumeCallback(this);
        }

        protected void selectRoute(android.media.MediaRouter.RouteInfo routeInfo) {
            if (this.mSelectRouteWorkaround == null) {
                this.mSelectRouteWorkaround = new MediaRouterApi16Impl.SelectRouteWorkaround();
            }
            this.mSelectRouteWorkaround.selectRoute(this.mRouter, 8388611, routeInfo);
        }

        protected Object getDefaultRoute() {
            if (this.mGetDefaultRouteWorkaround == null) {
                this.mGetDefaultRouteWorkaround = new MediaRouterApi16Impl.GetDefaultRouteWorkaround();
            }
            return this.mGetDefaultRouteWorkaround.getDefaultRoute(this.mRouter);
        }

        protected static final class SystemRouteRecord {
            public final android.media.MediaRouter.RouteInfo mRoute;
            public MediaRouteDescriptor mRouteDescriptor;
            public final String mRouteDescriptorId;

            public SystemRouteRecord(android.media.MediaRouter.RouteInfo routeInfo, String str) {
                this.mRoute = routeInfo;
                this.mRouteDescriptorId = str;
            }
        }

        protected static final class UserRouteRecord {
            public final MediaRouter.RouteInfo mRoute;
            public final android.media.MediaRouter.UserRouteInfo mUserRoute;

            public UserRouteRecord(MediaRouter.RouteInfo routeInfo, android.media.MediaRouter.UserRouteInfo userRouteInfo) {
                this.mRoute = routeInfo;
                this.mUserRoute = userRouteInfo;
            }
        }

        protected static final class SystemRouteController extends MediaRouteProvider.RouteController {
            private final android.media.MediaRouter.RouteInfo mRoute;

            public SystemRouteController(android.media.MediaRouter.RouteInfo routeInfo) {
                this.mRoute = routeInfo;
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onSetVolume(int i) {
                MediaRouterApi16Impl.RouteInfo.requestSetVolume(this.mRoute, i);
            }

            @Override // androidx.mediarouter.media.MediaRouteProvider.RouteController
            public void onUpdateVolume(int i) {
                MediaRouterApi16Impl.RouteInfo.requestUpdateVolume(this.mRoute, i);
            }
        }
    }

    private static class JellybeanMr1Impl extends JellybeanImpl implements MediaRouterApi17Impl.Callback {
        private MediaRouterApi17Impl.ActiveScanWorkaround mActiveScanWorkaround;
        private MediaRouterApi17Impl.IsConnectingWorkaround mIsConnectingWorkaround;

        public JellybeanMr1Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.MediaRouterApi17Impl.Callback
        public void onRoutePresentationDisplayChanged(android.media.MediaRouter.RouteInfo routeInfo) {
            int iFindSystemRouteRecord = findSystemRouteRecord(routeInfo);
            if (iFindSystemRouteRecord >= 0) {
                JellybeanImpl.SystemRouteRecord systemRouteRecord = this.mSystemRouteRecords.get(iFindSystemRouteRecord);
                Display presentationDisplay = MediaRouterApi17Impl.RouteInfo.getPresentationDisplay(routeInfo);
                int displayId = presentationDisplay != null ? presentationDisplay.getDisplayId() : -1;
                if (displayId != systemRouteRecord.mRouteDescriptor.getPresentationDisplayId()) {
                    systemRouteRecord.mRouteDescriptor = new MediaRouteDescriptor.Builder(systemRouteRecord.mRouteDescriptor).setPresentationDisplayId(displayId).build();
                    publishRoutes();
                }
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            if (!MediaRouterApi17Impl.RouteInfo.isEnabled(systemRouteRecord.mRoute)) {
                builder.setEnabled(false);
            }
            if (isConnecting(systemRouteRecord)) {
                builder.setConnectionState(1);
            }
            Display presentationDisplay = MediaRouterApi17Impl.RouteInfo.getPresentationDisplay(systemRouteRecord.mRoute);
            if (presentationDisplay != null) {
                builder.setPresentationDisplayId(presentationDisplay.getDisplayId());
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void updateCallback() {
            super.updateCallback();
            if (this.mActiveScanWorkaround == null) {
                this.mActiveScanWorkaround = new MediaRouterApi17Impl.ActiveScanWorkaround(getContext(), getHandler());
            }
            this.mActiveScanWorkaround.setActiveScanRouteTypes(this.mActiveScan ? this.mRouteTypes : 0);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected android.media.MediaRouter.Callback createCallback() {
            return MediaRouterApi17Impl.createCallback(this);
        }

        protected boolean isConnecting(JellybeanImpl.SystemRouteRecord systemRouteRecord) {
            if (this.mIsConnectingWorkaround == null) {
                this.mIsConnectingWorkaround = new MediaRouterApi17Impl.IsConnectingWorkaround();
            }
            return this.mIsConnectingWorkaround.isConnecting(systemRouteRecord.mRoute);
        }
    }

    private static class JellybeanMr2Impl extends JellybeanMr1Impl {
        public JellybeanMr2Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            CharSequence description = systemRouteRecord.mRoute.getDescription();
            if (description != null) {
                builder.setDescription(description.toString());
            }
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void selectRoute(android.media.MediaRouter.RouteInfo routeInfo) {
            MediaRouterApi16Impl.selectRoute(this.mRouter, 8388611, routeInfo);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        public android.media.MediaRouter.RouteInfo getDefaultRoute() {
            return this.mRouter.getDefaultRoute();
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void updateUserRouteProperties(JellybeanImpl.UserRouteRecord userRouteRecord) {
            super.updateUserRouteProperties(userRouteRecord);
            userRouteRecord.mUserRoute.setDescription(userRouteRecord.mRoute.getDescription());
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void updateCallback() {
            if (this.mCallbackRegistered) {
                MediaRouterApi16Impl.removeCallback(this.mRouter, this.mCallback);
            }
            this.mCallbackRegistered = true;
            this.mRouter.addCallback(this.mRouteTypes, this.mCallback, (this.mActiveScan ? 1 : 0) | 2);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl
        protected boolean isConnecting(JellybeanImpl.SystemRouteRecord systemRouteRecord) {
            return systemRouteRecord.mRoute.isConnecting();
        }
    }

    private static class Api24Impl extends JellybeanMr2Impl {
        public Api24Impl(Context context, SyncCallback syncCallback) {
            super(context, syncCallback);
        }

        @Override // androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr2Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanMr1Impl, androidx.mediarouter.media.SystemMediaRouteProvider.JellybeanImpl
        protected void onBuildSystemRouteDescriptor(JellybeanImpl.SystemRouteRecord systemRouteRecord, MediaRouteDescriptor.Builder builder) {
            super.onBuildSystemRouteDescriptor(systemRouteRecord, builder);
            builder.setDeviceType(systemRouteRecord.mRoute.getDeviceType());
        }
    }
}
