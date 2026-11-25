package com.google.android.gms.cast.framework.media.internal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import com.google.android.gms.cast.CastDevice;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.cast.framework.media.CastMediaOptions;
import com.google.android.gms.cast.framework.media.ImageHints;
import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.cast.framework.media.MediaIntentReceiver;
import com.google.android.gms.cast.framework.media.NotificationAction;
import com.google.android.gms.cast.framework.media.NotificationOptions;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
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
final class zzo {
    private static final Logger zza = new Logger("MediaNotificationProxy");
    private final Context zzb;
    private final NotificationManager zzc;
    private final CastContext zzd;
    private final NotificationOptions zze;
    private final ImagePicker zzf;
    private final ComponentName zzg;
    private final ComponentName zzh;
    private List zzi = new ArrayList();
    private int[] zzj;
    private final long zzk;
    private final zzb zzl;
    private final ImageHints zzm;
    private final Resources zzn;
    private zzm zzo;
    private zzn zzp;
    private Notification zzq;
    private NotificationCompat.Action zzr;
    private NotificationCompat.Action zzs;
    private NotificationCompat.Action zzt;
    private NotificationCompat.Action zzu;
    private NotificationCompat.Action zzv;
    private NotificationCompat.Action zzw;
    private NotificationCompat.Action zzx;
    private NotificationCompat.Action zzy;

    zzo(Context context) {
        this.zzb = context;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        this.zzc = notificationManager;
        CastContext castContext = (CastContext) Preconditions.checkNotNull(CastContext.getSharedInstance());
        this.zzd = castContext;
        CastMediaOptions castMediaOptions = (CastMediaOptions) Preconditions.checkNotNull(((CastOptions) Preconditions.checkNotNull(castContext.getCastOptions())).getCastMediaOptions());
        NotificationOptions notificationOptions = (NotificationOptions) Preconditions.checkNotNull(castMediaOptions.getNotificationOptions());
        this.zze = notificationOptions;
        this.zzf = castMediaOptions.getImagePicker();
        Resources resources = context.getResources();
        this.zzn = resources;
        this.zzg = new ComponentName(context.getApplicationContext(), castMediaOptions.getMediaIntentReceiverClassName());
        if (TextUtils.isEmpty(notificationOptions.getTargetActivityClassName())) {
            this.zzh = null;
        } else {
            this.zzh = new ComponentName(context.getApplicationContext(), notificationOptions.getTargetActivityClassName());
        }
        this.zzk = notificationOptions.getSkipStepMs();
        int dimensionPixelSize = resources.getDimensionPixelSize(notificationOptions.zze());
        ImageHints imageHints = new ImageHints(1, dimensionPixelSize, dimensionPixelSize);
        this.zzm = imageHints;
        this.zzl = new zzb(context.getApplicationContext(), imageHints);
        if (PlatformVersion.isAtLeastO() && notificationManager != null) {
            NotificationChannel notificationChannel = new NotificationChannel("cast_media_notification", ((Context) Preconditions.checkNotNull(context)).getResources().getString(R.string.media_notification_channel_name), 2);
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        com.google.android.gms.internal.cast.zzo.zzd(zzml.CAF_MEDIA_NOTIFICATION_PROXY);
    }

    static boolean zze(CastOptions castOptions) {
        NotificationOptions notificationOptions;
        CastMediaOptions castMediaOptions = castOptions.getCastMediaOptions();
        if (castMediaOptions == null || (notificationOptions = castMediaOptions.getNotificationOptions()) == null) {
            return false;
        }
        com.google.android.gms.cast.framework.media.zzg zzgVarZzm = notificationOptions.zzm();
        if (zzgVarZzm == null) {
            return true;
        }
        List listZzf = zzw.zzf(zzgVarZzm);
        int[] iArrZzg = zzw.zzg(zzgVarZzm);
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
    private final NotificationCompat.Action zzf(String str) {
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
                zzm zzmVar = this.zzo;
                int i = zzmVar.zzc;
                if (!zzmVar.zzb) {
                    if (this.zzr == null) {
                        Intent intent = new Intent(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK);
                        intent.setComponent(this.zzg);
                        PendingIntent broadcast2 = PendingIntent.getBroadcast(this.zzb, 0, intent, zzdy.zza);
                        NotificationOptions notificationOptions = this.zze;
                        this.zzr = new NotificationCompat.Action.Builder(notificationOptions.getPlayDrawableResId(), this.zzn.getString(notificationOptions.zzg()), broadcast2).build();
                    }
                    return this.zzr;
                }
                if (this.zzs == null) {
                    if (i == 2) {
                        NotificationOptions notificationOptions2 = this.zze;
                        pauseDrawableResId = notificationOptions2.getStopLiveStreamDrawableResId();
                        iZzf = notificationOptions2.getStopLiveStreamTitleResId();
                    } else {
                        NotificationOptions notificationOptions3 = this.zze;
                        pauseDrawableResId = notificationOptions3.getPauseDrawableResId();
                        iZzf = notificationOptions3.zzf();
                    }
                    Intent intent2 = new Intent(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK);
                    intent2.setComponent(this.zzg);
                    this.zzs = new NotificationCompat.Action.Builder(pauseDrawableResId, this.zzn.getString(iZzf), PendingIntent.getBroadcast(this.zzb, 0, intent2, zzdy.zza)).build();
                }
                return this.zzs;
            case 1:
                boolean z = this.zzo.zzf;
                if (this.zzt == null) {
                    if (z) {
                        Intent intent3 = new Intent(MediaIntentReceiver.ACTION_SKIP_NEXT);
                        intent3.setComponent(this.zzg);
                        broadcast = PendingIntent.getBroadcast(this.zzb, 0, intent3, zzdy.zza);
                    }
                    NotificationOptions notificationOptions4 = this.zze;
                    this.zzt = new NotificationCompat.Action.Builder(notificationOptions4.getSkipNextDrawableResId(), this.zzn.getString(notificationOptions4.zzk()), broadcast).build();
                }
                return this.zzt;
            case 2:
                boolean z2 = this.zzo.zzg;
                if (this.zzu == null) {
                    if (z2) {
                        Intent intent4 = new Intent(MediaIntentReceiver.ACTION_SKIP_PREV);
                        intent4.setComponent(this.zzg);
                        broadcast = PendingIntent.getBroadcast(this.zzb, 0, intent4, zzdy.zza);
                    }
                    NotificationOptions notificationOptions5 = this.zze;
                    this.zzu = new NotificationCompat.Action.Builder(notificationOptions5.getSkipPrevDrawableResId(), this.zzn.getString(notificationOptions5.zzl()), broadcast).build();
                }
                return this.zzu;
            case 3:
                long j = this.zzk;
                if (this.zzv == null) {
                    Intent intent5 = new Intent(MediaIntentReceiver.ACTION_FORWARD);
                    intent5.setComponent(this.zzg);
                    intent5.putExtra(MediaIntentReceiver.EXTRA_SKIP_STEP_MS, j);
                    this.zzv = new NotificationCompat.Action.Builder(zzw.zza(this.zze, j), this.zzn.getString(zzw.zzb(this.zze, j)), PendingIntent.getBroadcast(this.zzb, 0, intent5, zzdy.zza | 134217728)).build();
                }
                return this.zzv;
            case 4:
                long j2 = this.zzk;
                if (this.zzw == null) {
                    Intent intent6 = new Intent(MediaIntentReceiver.ACTION_REWIND);
                    intent6.setComponent(this.zzg);
                    intent6.putExtra(MediaIntentReceiver.EXTRA_SKIP_STEP_MS, j2);
                    this.zzw = new NotificationCompat.Action.Builder(zzw.zzc(this.zze, j2), this.zzn.getString(zzw.zzd(this.zze, j2)), PendingIntent.getBroadcast(this.zzb, 0, intent6, zzdy.zza | 134217728)).build();
                }
                return this.zzw;
            case 5:
                if (this.zzy == null) {
                    Intent intent7 = new Intent(MediaIntentReceiver.ACTION_STOP_CASTING);
                    intent7.setComponent(this.zzg);
                    PendingIntent broadcast3 = PendingIntent.getBroadcast(this.zzb, 0, intent7, zzdy.zza);
                    NotificationOptions notificationOptions6 = this.zze;
                    this.zzy = new NotificationCompat.Action.Builder(notificationOptions6.getDisconnectDrawableResId(), this.zzn.getString(notificationOptions6.zza()), broadcast3).build();
                }
                return this.zzy;
            case 6:
                if (this.zzx == null) {
                    Intent intent8 = new Intent(MediaIntentReceiver.ACTION_DISCONNECT);
                    intent8.setComponent(this.zzg);
                    PendingIntent broadcast4 = PendingIntent.getBroadcast(this.zzb, 0, intent8, zzdy.zza);
                    NotificationOptions notificationOptions7 = this.zze;
                    this.zzx = new NotificationCompat.Action.Builder(notificationOptions7.getDisconnectDrawableResId(), this.zzn.getString(notificationOptions7.zza(), ""), broadcast4).build();
                }
                return this.zzx;
            default:
                zza.e("Action: %s is not a pre-defined action.", str);
                return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzg() {
        PendingIntent pendingIntent;
        NotificationCompat.Action actionZzf;
        if (this.zzc == null || this.zzo == null) {
            return;
        }
        zzn zznVar = this.zzp;
        NotificationCompat.Builder visibility = new NotificationCompat.Builder(this.zzb, "cast_media_notification").setLargeIcon(zznVar == null ? null : zznVar.zzb).setSmallIcon(this.zze.getSmallIconDrawableResId()).setContentTitle(this.zzo.zzd).setContentText(this.zzn.getString(this.zze.getCastingToDeviceStringResId(), this.zzo.zze)).setOngoing(true).setShowWhen(false).setVisibility(1);
        ComponentName componentName = this.zzh;
        if (componentName == null) {
            pendingIntent = null;
        } else {
            Intent intent = new Intent();
            intent.putExtra("targetActivity", componentName);
            intent.setAction(componentName.flattenToString());
            intent.setComponent(componentName);
            TaskStackBuilder taskStackBuilderCreate = TaskStackBuilder.create(this.zzb);
            taskStackBuilderCreate.addNextIntentWithParentStack(intent);
            pendingIntent = taskStackBuilderCreate.getPendingIntent(1, zzdy.zza | 134217728);
        }
        if (pendingIntent != null) {
            visibility.setContentIntent(pendingIntent);
        }
        com.google.android.gms.cast.framework.media.zzg zzgVarZzm = this.zze.zzm();
        if (zzgVarZzm != null) {
            zza.d("actionsProvider != null", new Object[0]);
            int[] iArrZzg = zzw.zzg(zzgVarZzm);
            this.zzj = iArrZzg != null ? (int[]) iArrZzg.clone() : null;
            List<NotificationAction> listZzf = zzw.zzf(zzgVarZzm);
            this.zzi = new ArrayList();
            if (listZzf != null) {
                for (NotificationAction notificationAction : listZzf) {
                    String action = notificationAction.getAction();
                    if (action.equals(MediaIntentReceiver.ACTION_TOGGLE_PLAYBACK) || action.equals(MediaIntentReceiver.ACTION_SKIP_NEXT) || action.equals(MediaIntentReceiver.ACTION_SKIP_PREV) || action.equals(MediaIntentReceiver.ACTION_FORWARD) || action.equals(MediaIntentReceiver.ACTION_REWIND) || action.equals(MediaIntentReceiver.ACTION_STOP_CASTING) || action.equals(MediaIntentReceiver.ACTION_DISCONNECT)) {
                        actionZzf = zzf(notificationAction.getAction());
                    } else {
                        Intent intent2 = new Intent(notificationAction.getAction());
                        intent2.setComponent(this.zzg);
                        actionZzf = new NotificationCompat.Action.Builder(notificationAction.getIconResId(), notificationAction.getContentDescription(), PendingIntent.getBroadcast(this.zzb, 0, intent2, zzdy.zza)).build();
                    }
                    if (actionZzf != null) {
                        this.zzi.add(actionZzf);
                    }
                }
            }
        } else {
            zza.d("actionsProvider == null", new Object[0]);
            this.zzi = new ArrayList();
            Iterator<String> it = this.zze.getActions().iterator();
            while (it.hasNext()) {
                NotificationCompat.Action actionZzf2 = zzf(it.next());
                if (actionZzf2 != null) {
                    this.zzi.add(actionZzf2);
                }
            }
            this.zzj = (int[]) this.zze.getCompatActionIndices().clone();
        }
        Iterator it2 = this.zzi.iterator();
        while (it2.hasNext()) {
            visibility.addAction((NotificationCompat.Action) it2.next());
        }
        androidx.media.app.NotificationCompat.MediaStyle mediaStyle = new androidx.media.app.NotificationCompat.MediaStyle();
        int[] iArr = this.zzj;
        if (iArr != null) {
            mediaStyle.setShowActionsInCompactView(iArr);
        }
        MediaSessionCompat.Token token = this.zzo.zza;
        if (token != null) {
            mediaStyle.setMediaSession(token);
        }
        visibility.setStyle(mediaStyle);
        Notification notificationBuild = visibility.build();
        this.zzq = notificationBuild;
        this.zzc.notify("castMediaNotification", 1, notificationBuild);
    }

    final void zzc() {
        this.zzl.zza();
        NotificationManager notificationManager = this.zzc;
        if (notificationManager != null) {
            notificationManager.cancel("castMediaNotification", 1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:30:0x0051  */
    final void zzd(CastDevice castDevice, RemoteMediaClient remoteMediaClient, MediaSessionCompat mediaSessionCompat, boolean z) {
        MediaInfo mediaInfo;
        MediaMetadata metadata;
        boolean z2;
        boolean z3;
        WebImage webImageOnPickImage;
        zzm zzmVar;
        if (castDevice == null || remoteMediaClient == null || mediaSessionCompat == null || (mediaInfo = remoteMediaClient.getMediaInfo()) == null || (metadata = mediaInfo.getMetadata()) == null) {
            return;
        }
        MediaStatus mediaStatus = remoteMediaClient.getMediaStatus();
        if (mediaStatus == null) {
            z2 = false;
            z3 = z2;
        } else {
            int queueRepeatMode = mediaStatus.getQueueRepeatMode();
            if (queueRepeatMode == 1 || queueRepeatMode == 2 || queueRepeatMode == 3) {
                z2 = true;
                z3 = z2;
            } else {
                Integer indexById = mediaStatus.getIndexById(mediaStatus.getCurrentItemId());
                if (indexById != null) {
                    boolean z4 = indexById.intValue() > 0;
                    z2 = indexById.intValue() < mediaStatus.getQueueItemCount() + (-1);
                    z3 = z4;
                } else {
                    z2 = false;
                    z3 = z2;
                }
            }
        }
        zzm zzmVar2 = new zzm(remoteMediaClient.getPlayerState() == 2, mediaInfo.getStreamType(), metadata.getString(MediaMetadata.KEY_TITLE), castDevice.getFriendlyName(), mediaSessionCompat.getSessionToken(), z2, z3);
        if (z || (zzmVar = this.zzo) == null || zzmVar2.zzb != zzmVar.zzb || zzmVar2.zzc != zzmVar.zzc || !CastUtils.zze(zzmVar2.zzd, zzmVar.zzd) || !CastUtils.zze(zzmVar2.zze, zzmVar.zze) || zzmVar2.zzf != zzmVar.zzf || zzmVar2.zzg != zzmVar.zzg) {
            this.zzo = zzmVar2;
            zzg();
        }
        ImagePicker imagePicker = this.zzf;
        if (imagePicker != null) {
            webImageOnPickImage = imagePicker.onPickImage(metadata, this.zzm);
        } else {
            webImageOnPickImage = metadata.hasImages() ? metadata.getImages().get(0) : null;
        }
        zzn zznVar = new zzn(webImageOnPickImage);
        zzn zznVar2 = this.zzp;
        if (zznVar2 == null || !CastUtils.zze(zznVar.zza, zznVar2.zza)) {
            this.zzl.zzc(new zzl(this, zznVar));
            this.zzl.zzd(zznVar.zza);
        }
    }
}
