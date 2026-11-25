package com.google.android.gms.cast.framework.media;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.internal.CastUtils;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.internal.cast.zzdy;
import com.google.android.gms.internal.cast.zzml;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public class MediaNotificationService extends Service {
    public static final String ACTION_UPDATE_NOTIFICATION = "com.google.android.gms.cast.framework.action.UPDATE_NOTIFICATION";
    private static final Logger zza = new Logger("MediaNotificationService");
    private static Runnable zzb;
    private NotificationOptions zzc;
    private ImagePicker zzd;
    private ComponentName zze;
    private ComponentName zzf;
    private List zzg = new ArrayList();
    private int[] zzh;
    private long zzi;
    private com.google.android.gms.cast.framework.media.internal.zzb zzj;
    private ImageHints zzk;
    private Resources zzl;
    private zzm zzm;
    private zzn zzn;
    private NotificationManager zzo;
    private Notification zzp;
    private CastContext zzq;

    public static boolean isNotificationOptionsValid(CastOptions castOptions) {
        NotificationOptions notificationOptions;
        CastMediaOptions castMediaOptions = castOptions.getCastMediaOptions();
        if (castMediaOptions == null || (notificationOptions = castMediaOptions.getNotificationOptions()) == null) {
            return false;
        }
        zzg zzgVarZzm = notificationOptions.zzm();
        if (zzgVarZzm == null) {
            return true;
        }
        List listZzf = com.google.android.gms.cast.framework.media.internal.zzw.zzf(zzgVarZzm);
        int[] iArrZzg = com.google.android.gms.cast.framework.media.internal.zzw.zzg(zzgVarZzm);
        int size = listZzf == null ? 0 : listZzf.size();
        if (listZzf == null || listZzf.isEmpty()) {
            zza.e(String.valueOf("NotificationActionsProvider").concat(" doesn't provide any action."), new Object[0]);
        } else if (listZzf.size() > 5) {
            zza.e(String.valueOf("NotificationActionsProvider").concat(" provides more than 5 actions."), new Object[0]);
        } else {
            if (iArrZzg != null && (iArrZzg.length) != 0) {
                for (int i : iArrZzg) {
                    if (i < 0 || i >= size) {
                        zza.e(String.valueOf("NotificationActionsProvider").concat("provides a compact view action whose index is out of bounds."), new Object[0]);
                    }
                }
                return true;
            }
            zza.e(String.valueOf("NotificationActionsProvider").concat(" doesn't provide any actions for compact view."), new Object[0]);
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:26:0x0050  */
    private final NotificationCompat.Action zzc(String str) {
        byte b;
        int pauseDrawableResId;
        int iZzf;
        switch (str) {
            case "com.google.android.gms.cast.framework.action.REWIND":
                b = 4;
                break;
            case "com.google.android.gms.cast.framework.action.SKIP_NEXT":
                b = 1;
                break;
            case "com.google.android.gms.cast.framework.action.SKIP_PREV":
                b = 2;
                break;
            case "com.google.android.gms.cast.framework.action.STOP_CASTING":
                b = 5;
                break;
            case "com.google.android.gms.cast.framework.action.DISCONNECT":
                b = 6;
                break;
            case "com.google.android.gms.cast.framework.action.TOGGLE_PLAYBACK":
                b = 0;
                break;
            case "com.google.android.gms.cast.framework.action.FORWARD":
                b = 3;
                break;
            default:
                b = -1;
                break;
        }
        PendingIntent broadcast = null;
        switch (b) {
            case 0:
                zzm zzmVar = this.zzm;
                int i = zzmVar.zzc;
                boolean z = zzmVar.zzb;
                if (i == 2) {
                    pauseDrawableResId = this.zzc.getStopLiveStreamDrawableResId();
                    iZzf = this.zzc.getStopLiveStreamTitleResId();
                } else {
                    pauseDrawableResId = this.zzc.getPauseDrawableResId();
                    iZzf = this.zzc.zzf();
                }
                if (!z) {
                    pauseDrawableResId = this.zzc.getPlayDrawableResId();
                }
                if (!z) {
                    iZzf = this.zzc.zzg();
                }
                Intent intent = new Intent(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK);
                intent.setComponent(this.zze);
                return new NotificationCompat.Action.Builder(pauseDrawableResId, this.zzl.getString(iZzf), PendingIntent.getBroadcast(this, 0, intent, zzdy.zza)).build();
            case 1:
                if (this.zzm.zzf) {
                    Intent intent2 = new Intent(MediaIntentReceiver.ACTION_SKIP_NEXT);
                    intent2.setComponent(this.zze);
                    broadcast = PendingIntent.getBroadcast(this, 0, intent2, zzdy.zza);
                }
                return new NotificationCompat.Action.Builder(this.zzc.getSkipNextDrawableResId(), this.zzl.getString(this.zzc.zzk()), broadcast).build();
            case 2:
                if (this.zzm.zzg) {
                    Intent intent3 = new Intent(MediaIntentReceiver.ACTION_SKIP_PREV);
                    intent3.setComponent(this.zze);
                    broadcast = PendingIntent.getBroadcast(this, 0, intent3, zzdy.zza);
                }
                return new NotificationCompat.Action.Builder(this.zzc.getSkipPrevDrawableResId(), this.zzl.getString(this.zzc.zzl()), broadcast).build();
            case 3:
                long j = this.zzi;
                Intent intent4 = new Intent(MediaIntentReceiver.ACTION_FORWARD);
                intent4.setComponent(this.zze);
                intent4.putExtra(MediaIntentReceiver.EXTRA_SKIP_STEP_MS, j);
                return new NotificationCompat.Action.Builder(com.google.android.gms.cast.framework.media.internal.zzw.zza(this.zzc, j), this.zzl.getString(com.google.android.gms.cast.framework.media.internal.zzw.zzb(this.zzc, j)), PendingIntent.getBroadcast(this, 0, intent4, zzdy.zza | 134217728)).build();
            case 4:
                long j2 = this.zzi;
                Intent intent5 = new Intent(MediaIntentReceiver.ACTION_REWIND);
                intent5.setComponent(this.zze);
                intent5.putExtra(MediaIntentReceiver.EXTRA_SKIP_STEP_MS, j2);
                return new NotificationCompat.Action.Builder(com.google.android.gms.cast.framework.media.internal.zzw.zzc(this.zzc, j2), this.zzl.getString(com.google.android.gms.cast.framework.media.internal.zzw.zzd(this.zzc, j2)), PendingIntent.getBroadcast(this, 0, intent5, zzdy.zza | 134217728)).build();
            case 5:
                Intent intent6 = new Intent(MediaIntentReceiver.ACTION_STOP_CASTING);
                intent6.setComponent(this.zze);
                return new NotificationCompat.Action.Builder(this.zzc.getDisconnectDrawableResId(), this.zzl.getString(this.zzc.zza()), PendingIntent.getBroadcast(this, 0, intent6, zzdy.zza)).build();
            case 6:
                Intent intent7 = new Intent(MediaIntentReceiver.ACTION_DISCONNECT);
                intent7.setComponent(this.zze);
                return new NotificationCompat.Action.Builder(this.zzc.getDisconnectDrawableResId(), this.zzl.getString(this.zzc.zza(), ""), PendingIntent.getBroadcast(this, 0, intent7, zzdy.zza)).build();
            default:
                zza.e("Action: %s is not a pre-defined action.", str);
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzd() {
        PendingIntent pendingIntent;
        NotificationCompat.Action actionZzc;
        if (this.zzm == null) {
            return;
        }
        zzn zznVar = this.zzn;
        NotificationCompat.Builder visibility = new NotificationCompat.Builder(this, "cast_media_notification").setLargeIcon(zznVar == null ? null : zznVar.zzb).setSmallIcon(this.zzc.getSmallIconDrawableResId()).setContentTitle(this.zzm.zzd).setContentText(this.zzl.getString(this.zzc.getCastingToDeviceStringResId(), this.zzm.zze)).setOngoing(true).setShowWhen(false).setVisibility(1);
        ComponentName componentName = this.zzf;
        if (componentName == null) {
            pendingIntent = null;
        } else {
            Intent intent = new Intent();
            intent.putExtra("targetActivity", componentName);
            intent.setAction(componentName.flattenToString());
            intent.setComponent(componentName);
            TaskStackBuilder taskStackBuilderCreate = TaskStackBuilder.create(this);
            taskStackBuilderCreate.addNextIntentWithParentStack(intent);
            pendingIntent = taskStackBuilderCreate.getPendingIntent(1, zzdy.zza | 134217728);
        }
        if (pendingIntent != null) {
            visibility.setContentIntent(pendingIntent);
        }
        zzg zzgVarZzm = this.zzc.zzm();
        if (zzgVarZzm != null) {
            zza.i("actionsProvider != null", new Object[0]);
            int[] iArrZzg = com.google.android.gms.cast.framework.media.internal.zzw.zzg(zzgVarZzm);
            this.zzh = iArrZzg != null ? (int[]) iArrZzg.clone() : null;
            List<NotificationAction> listZzf = com.google.android.gms.cast.framework.media.internal.zzw.zzf(zzgVarZzm);
            this.zzg = new ArrayList();
            if (listZzf != null) {
                for (NotificationAction notificationAction : listZzf) {
                    String action = notificationAction.getAction();
                    if (action.equals(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK) || action.equals(MediaIntentReceiver.ACTION_SKIP_NEXT) || action.equals(MediaIntentReceiver.ACTION_SKIP_PREV) || action.equals(MediaIntentReceiver.ACTION_FORWARD) || action.equals(MediaIntentReceiver.ACTION_REWIND) || action.equals(MediaIntentReceiver.ACTION_STOP_CASTING) || action.equals(MediaIntentReceiver.ACTION_DISCONNECT)) {
                        actionZzc = zzc(notificationAction.getAction());
                    } else {
                        Intent intent2 = new Intent(notificationAction.getAction());
                        intent2.setComponent(this.zze);
                        actionZzc = new NotificationCompat.Action.Builder(notificationAction.getIconResId(), notificationAction.getContentDescription(), PendingIntent.getBroadcast(this, 0, intent2, zzdy.zza)).build();
                    }
                    if (actionZzc != null) {
                        this.zzg.add(actionZzc);
                    }
                }
            }
        } else {
            zza.i("actionsProvider == null", new Object[0]);
            this.zzg = new ArrayList();
            Iterator<String> it = this.zzc.getActions().iterator();
            while (it.hasNext()) {
                NotificationCompat.Action actionZzc2 = zzc(it.next());
                if (actionZzc2 != null) {
                    this.zzg.add(actionZzc2);
                }
            }
            this.zzh = (int[]) this.zzc.getCompatActionIndices().clone();
        }
        Iterator it2 = this.zzg.iterator();
        while (it2.hasNext()) {
            visibility.addAction((NotificationCompat.Action) it2.next());
        }
        androidx.media.app.NotificationCompat.MediaStyle mediaStyle = new androidx.media.app.NotificationCompat.MediaStyle();
        int[] iArr = this.zzh;
        if (iArr != null) {
            mediaStyle.setShowActionsInCompactView(iArr);
        }
        MediaSessionCompat.Token token = this.zzm.zza;
        if (token != null) {
            mediaStyle.setMediaSession(token);
        }
        visibility.setStyle(mediaStyle);
        Notification notificationBuild = visibility.build();
        this.zzp = notificationBuild;
        startForeground(1, notificationBuild);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        this.zzo = (NotificationManager) getSystemService("notification");
        CastContext sharedInstance = CastContext.getSharedInstance(this);
        this.zzq = sharedInstance;
        CastMediaOptions castMediaOptions = (CastMediaOptions) Preconditions.checkNotNull(sharedInstance.getCastOptions().getCastMediaOptions());
        this.zzc = (NotificationOptions) Preconditions.checkNotNull(castMediaOptions.getNotificationOptions());
        this.zzd = castMediaOptions.getImagePicker();
        this.zzl = getResources();
        this.zze = new ComponentName(getApplicationContext(), castMediaOptions.getMediaIntentReceiverClassName());
        if (TextUtils.isEmpty(this.zzc.getTargetActivityClassName())) {
            this.zzf = null;
        } else {
            this.zzf = new ComponentName(getApplicationContext(), this.zzc.getTargetActivityClassName());
        }
        this.zzi = this.zzc.getSkipStepMs();
        int dimensionPixelSize = this.zzl.getDimensionPixelSize(this.zzc.zze());
        this.zzk = new ImageHints(1, dimensionPixelSize, dimensionPixelSize);
        this.zzj = new com.google.android.gms.cast.framework.media.internal.zzb(getApplicationContext(), this.zzk);
        if (PlatformVersion.isAtLeastO()) {
            NotificationChannel notificationChannel = new NotificationChannel("cast_media_notification", getResources().getString(R.string.media_notification_channel_name), 2);
            notificationChannel.setShowBadge(false);
            this.zzo.createNotificationChannel(notificationChannel);
        }
        com.google.android.gms.internal.cast.zzo.zzd(zzml.CAF_NOTIFICATION_SERVICE);
    }

    @Override // android.app.Service
    public void onDestroy() {
        com.google.android.gms.cast.framework.media.internal.zzb zzbVar = this.zzj;
        if (zzbVar != null) {
            zzbVar.zza();
        }
        zzb = null;
        this.zzo.cancel(1);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, final int i2) {
        WebImage webImageOnPickImage;
        zzm zzmVar;
        MediaInfo mediaInfo = (MediaInfo) Preconditions.checkNotNull((MediaInfo) intent.getParcelableExtra("extra_media_info"));
        MediaMetadata mediaMetadata = (MediaMetadata) Preconditions.checkNotNull(mediaInfo.getMetadata());
        int intExtra = intent.getIntExtra("extra_remote_media_client_player_state", 0);
        CastDevice castDevice = (CastDevice) Preconditions.checkNotNull((CastDevice) intent.getParcelableExtra("extra_cast_device"));
        zzm zzmVar2 = new zzm(intExtra == 2, mediaInfo.getStreamType(), mediaMetadata.getString(MediaMetadata.KEY_TITLE), castDevice.getFriendlyName(), (MediaSessionCompat.Token) intent.getParcelableExtra("extra_media_session_token"), intent.getBooleanExtra("extra_can_skip_next", false), intent.getBooleanExtra("extra_can_skip_prev", false));
        if (intent.getBooleanExtra("extra_media_notification_force_update", false) || (zzmVar = this.zzm) == null || zzmVar2.zzb != zzmVar.zzb || zzmVar2.zzc != zzmVar.zzc || !CastUtils.zze(zzmVar2.zzd, zzmVar.zzd) || !CastUtils.zze(zzmVar2.zze, zzmVar.zze) || zzmVar2.zzf != zzmVar.zzf || zzmVar2.zzg != zzmVar.zzg) {
            this.zzm = zzmVar2;
            zzd();
        }
        ImagePicker imagePicker = this.zzd;
        if (imagePicker != null) {
            webImageOnPickImage = imagePicker.onPickImage(mediaMetadata, this.zzk);
        } else {
            webImageOnPickImage = mediaMetadata.hasImages() ? mediaMetadata.getImages().get(0) : null;
        }
        zzn zznVar = new zzn(webImageOnPickImage);
        zzn zznVar2 = this.zzn;
        if (zznVar2 == null || !CastUtils.zze(zznVar.zza, zznVar2.zza)) {
            this.zzj.zzc(new zzl(this, zznVar));
            this.zzj.zzd(zznVar.zza);
        }
        startForeground(1, this.zzp);
        zzb = new Runnable() { // from class: com.google.android.gms.cast.framework.media.zzk
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.stopSelf(i2);
            }
        };
        return 2;
    }
}
