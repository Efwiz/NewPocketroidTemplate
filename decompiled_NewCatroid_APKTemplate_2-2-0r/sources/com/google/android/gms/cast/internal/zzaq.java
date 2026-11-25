package com.google.android.gms.cast.internal;

import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.cast.AdBreakStatus;
import com.google.android.gms.cast.MediaError;
import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaLiveSeekableRange;
import com.google.android.gms.cast.MediaLoadRequestData;
import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.MediaSeekOptions;
import com.google.android.gms.cast.MediaStatus;
import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.cast.internal.media.MediaCommon;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.time.DateUtils;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaq extends zzd {
    public static final String zzb;
    final zzau zzc;
    final zzau zzd;
    final zzau zze;
    final zzau zzf;
    final zzau zzg;
    final zzau zzh;
    final zzau zzi;
    final zzau zzj;
    final zzau zzk;
    final zzau zzl;
    final zzau zzm;
    final zzau zzn;
    final zzau zzo;
    final zzau zzp;
    final zzau zzq;
    final zzau zzr;
    final zzau zzs;
    final zzau zzt;
    final zzau zzu;
    private long zzv;
    private MediaStatus zzw;
    private Long zzx;
    private zzan zzy;
    private int zzz;

    static {
        int i = CastUtils.zza;
        zzb = "urn:x-cast:com.google.cast.media";
    }

    public zzaq(String str) {
        super(zzb, "MediaControlChannel", null);
        this.zzz = -1;
        zzau zzauVar = new zzau(DateUtils.MILLIS_PER_DAY, "load");
        this.zzc = zzauVar;
        zzau zzauVar2 = new zzau(DateUtils.MILLIS_PER_DAY, "pause");
        this.zzd = zzauVar2;
        zzau zzauVar3 = new zzau(DateUtils.MILLIS_PER_DAY, "play");
        this.zze = zzauVar3;
        zzau zzauVar4 = new zzau(DateUtils.MILLIS_PER_DAY, "stop");
        this.zzf = zzauVar4;
        zzau zzauVar5 = new zzau(10000L, "seek");
        this.zzg = zzauVar5;
        zzau zzauVar6 = new zzau(DateUtils.MILLIS_PER_DAY, "volume");
        this.zzh = zzauVar6;
        zzau zzauVar7 = new zzau(DateUtils.MILLIS_PER_DAY, "mute");
        this.zzi = zzauVar7;
        zzau zzauVar8 = new zzau(DateUtils.MILLIS_PER_DAY, NotificationCompat.CATEGORY_STATUS);
        this.zzj = zzauVar8;
        zzau zzauVar9 = new zzau(DateUtils.MILLIS_PER_DAY, "activeTracks");
        this.zzk = zzauVar9;
        zzau zzauVar10 = new zzau(DateUtils.MILLIS_PER_DAY, "trackStyle");
        this.zzl = zzauVar10;
        zzau zzauVar11 = new zzau(DateUtils.MILLIS_PER_DAY, "queueInsert");
        this.zzm = zzauVar11;
        zzau zzauVar12 = new zzau(DateUtils.MILLIS_PER_DAY, "queueUpdate");
        this.zzn = zzauVar12;
        zzau zzauVar13 = new zzau(DateUtils.MILLIS_PER_DAY, "queueRemove");
        this.zzo = zzauVar13;
        zzau zzauVar14 = new zzau(DateUtils.MILLIS_PER_DAY, "queueReorder");
        this.zzp = zzauVar14;
        zzau zzauVar15 = new zzau(DateUtils.MILLIS_PER_DAY, "queueFetchItemIds");
        this.zzq = zzauVar15;
        zzau zzauVar16 = new zzau(DateUtils.MILLIS_PER_DAY, "queueFetchItemRange");
        this.zzs = zzauVar16;
        this.zzr = new zzau(DateUtils.MILLIS_PER_DAY, "queueFetchItems");
        zzau zzauVar17 = new zzau(DateUtils.MILLIS_PER_DAY, "setPlaybackRate");
        this.zzt = zzauVar17;
        zzau zzauVar18 = new zzau(DateUtils.MILLIS_PER_DAY, "skipAd");
        this.zzu = zzauVar18;
        zzc(zzauVar);
        zzc(zzauVar2);
        zzc(zzauVar3);
        zzc(zzauVar4);
        zzc(zzauVar5);
        zzc(zzauVar6);
        zzc(zzauVar7);
        zzc(zzauVar8);
        zzc(zzauVar9);
        zzc(zzauVar10);
        zzc(zzauVar11);
        zzc(zzauVar12);
        zzc(zzauVar13);
        zzc(zzauVar14);
        zzc(zzauVar15);
        zzc(zzauVar16);
        zzc(zzauVar16);
        zzc(zzauVar17);
        zzc(zzauVar18);
        zzT();
    }

    private final long zzR(double d, long j, long j2) {
        long jElapsedRealtime = SystemClock.elapsedRealtime() - this.zzv;
        if (jElapsedRealtime < 0) {
            jElapsedRealtime = 0;
        }
        if (jElapsedRealtime == 0) {
            return j;
        }
        long j3 = j + ((long) (jElapsedRealtime * d));
        if (j2 > 0 && j3 > j2) {
            return j2;
        }
        if (j3 >= 0) {
            return j3;
        }
        return 0L;
    }

    private static zzap zzS(JSONObject jSONObject) {
        MediaError mediaErrorZza = MediaError.zza(jSONObject);
        zzap zzapVar = new zzap();
        int i = CastUtils.zza;
        zzapVar.zza = jSONObject.has("customData") ? jSONObject.optJSONObject("customData") : null;
        zzapVar.zzb = mediaErrorZza;
        return zzapVar;
    }

    private final void zzT() {
        this.zzv = 0L;
        this.zzw = null;
        Iterator it = zza().iterator();
        while (it.hasNext()) {
            ((zzau) it.next()).zzc(2002);
        }
    }

    private final void zzU(JSONObject jSONObject, String str) {
        if (jSONObject.has("sequenceNumber")) {
            this.zzz = jSONObject.optInt("sequenceNumber", -1);
        } else {
            this.zza.w(str.concat(" message is missing a sequence number."), new Object[0]);
        }
    }

    private final void zzV() {
        zzan zzanVar = this.zzy;
        if (zzanVar != null) {
            zzanVar.zzc();
        }
    }

    private final void zzW() {
        zzan zzanVar = this.zzy;
        if (zzanVar != null) {
            zzanVar.zzd();
        }
    }

    private final void zzX() {
        zzan zzanVar = this.zzy;
        if (zzanVar != null) {
            zzanVar.zzk();
        }
    }

    private final void zzY() {
        zzan zzanVar = this.zzy;
        if (zzanVar != null) {
            zzanVar.zzm();
        }
    }

    private final boolean zzZ() {
        return this.zzz != -1;
    }

    private static int[] zzaa(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        int[] iArr = new int[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            iArr[i] = jSONArray.getInt(i);
        }
        return iArr;
    }

    public final long zzB(zzas zzasVar) throws IllegalStateException {
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "GET_STATUS");
            MediaStatus mediaStatus = this.zzw;
            if (mediaStatus != null) {
                jSONObject.put("mediaSessionId", mediaStatus.zzb());
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzj.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzC(zzas zzasVar, MediaSeekOptions mediaSeekOptions) throws IllegalStateException, zzao {
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        long position = mediaSeekOptions.isSeekToInfinite() ? 4294967296000L : mediaSeekOptions.getPosition();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "SEEK");
            jSONObject.put("mediaSessionId", zzn());
            jSONObject.put("currentTime", CastUtils.millisecToSec(position));
            if (mediaSeekOptions.getResumeState() == 1) {
                jSONObject.put("resumeState", "PLAYBACK_START");
            } else if (mediaSeekOptions.getResumeState() == 2) {
                jSONObject.put("resumeState", "PLAYBACK_PAUSE");
            }
            if (mediaSeekOptions.getCustomData() != null) {
                jSONObject.put("customData", mediaSeekOptions.getCustomData());
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzx = Long.valueOf(position);
        this.zzg.zzb(jZzd, new zzal(this, zzasVar));
        return jZzd;
    }

    public final long zzE(zzas zzasVar, double d, JSONObject jSONObject) throws IllegalStateException, zzao {
        if (this.zzw == null) {
            throw new zzao();
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "SET_PLAYBACK_RATE");
            jSONObject2.put("playbackRate", d);
            Preconditions.checkNotNull(this.zzw, "mediaStatus should not be null");
            jSONObject2.put("mediaSessionId", this.zzw.zzb());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzt.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzF(zzas zzasVar, boolean z, JSONObject jSONObject) throws IllegalStateException, zzao {
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zzn());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("muted", z);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzi.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzG(zzas zzasVar, double d, JSONObject jSONObject) throws IllegalStateException, IllegalArgumentException, zzao {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            throw new IllegalArgumentException("Volume cannot be " + d);
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "SET_VOLUME");
            jSONObject2.put("mediaSessionId", zzn());
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(FirebaseAnalytics.Param.LEVEL, d);
            jSONObject2.put("volume", jSONObject3);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzh.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzI(zzas zzasVar) throws IllegalStateException, zzao {
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "SKIP_AD");
            jSONObject.put("mediaSessionId", zzn());
        } catch (JSONException e) {
            this.zza.w(String.format(Locale.ROOT, "Error creating SkipAd message: %s", e.getMessage()), new Object[0]);
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzu.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzJ(zzas zzasVar, JSONObject jSONObject) throws IllegalStateException, zzao {
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "STOP");
            jSONObject2.put("mediaSessionId", zzn());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzf.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final MediaInfo zzK() {
        MediaStatus mediaStatus = this.zzw;
        if (mediaStatus == null) {
            return null;
        }
        return mediaStatus.getMediaInfo();
    }

    public final MediaStatus zzL() {
        return this.zzw;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:34:0x008b  */
    /* JADX WARN: Code duplicated, block: B:65:0x011a  */
    public final void zzO(String str) {
        int iZza;
        MediaStatus mediaStatus;
        int[] iArrZzaa;
        byte b;
        this.zza.d("message received: %s", str);
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(ModuleXmlParser.TYPE);
            long jOptLong = jSONObject.optLong("requestId", -1L);
            int i = 1;
            switch (string) {
                case "MEDIA_STATUS":
                    JSONArray jSONArray = jSONObject.getJSONArray(NotificationCompat.CATEGORY_STATUS);
                    if (jSONArray.length() > 0) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(0);
                        boolean zZze = this.zzc.zze(jOptLong);
                        if ((!this.zzh.zzf() || this.zzh.zze(jOptLong)) && (!this.zzi.zzf() || this.zzi.zze(jOptLong))) {
                            i = 0;
                        }
                        if (zZze || (mediaStatus = this.zzw) == null) {
                            this.zzw = new MediaStatus(jSONObject2);
                            this.zzv = SystemClock.elapsedRealtime();
                            iZza = 127;
                        } else {
                            iZza = mediaStatus.zza(jSONObject2, i);
                        }
                        if ((iZza & 1) != 0) {
                            this.zzv = SystemClock.elapsedRealtime();
                            this.zzz = -1;
                            zzY();
                        }
                        if ((iZza & 2) != 0) {
                            this.zzv = SystemClock.elapsedRealtime();
                            zzY();
                        }
                        if ((iZza & 128) != 0) {
                            this.zzv = SystemClock.elapsedRealtime();
                        }
                        if ((iZza & 4) != 0) {
                            zzV();
                        }
                        if ((iZza & 8) != 0) {
                            zzX();
                        }
                        if ((iZza & 16) != 0) {
                            zzW();
                        }
                        if ((iZza & 32) != 0) {
                            this.zzv = SystemClock.elapsedRealtime();
                            zzan zzanVar = this.zzy;
                            if (zzanVar != null) {
                                zzanVar.zza();
                            }
                        }
                        if ((iZza & 64) != 0) {
                            this.zzv = SystemClock.elapsedRealtime();
                            zzY();
                        }
                    } else {
                        this.zzw = null;
                        zzY();
                        zzV();
                        zzX();
                        zzW();
                    }
                    Iterator it = zza().iterator();
                    while (it.hasNext()) {
                        ((zzau) it.next()).zzd(jOptLong, 0, null);
                    }
                    break;
                case "INVALID_PLAYER_STATE":
                    this.zza.w("received unexpected error: Invalid Player State.", new Object[0]);
                    Iterator it2 = zza().iterator();
                    while (it2.hasNext()) {
                        ((zzau) it2.next()).zzd(jOptLong, 2100, zzS(jSONObject));
                    }
                    break;
                case "LOAD_FAILED":
                    this.zzc.zzd(jOptLong, 2100, zzS(jSONObject));
                    break;
                case "LOAD_CANCELLED":
                    this.zzc.zzd(jOptLong, RemoteMediaPlayer.STATUS_CANCELED, zzS(jSONObject));
                    break;
                case "INVALID_REQUEST":
                    this.zza.w("received unexpected error: Invalid Request.", new Object[0]);
                    Iterator it3 = zza().iterator();
                    while (it3.hasNext()) {
                        ((zzau) it3.next()).zzd(jOptLong, 2001, zzS(jSONObject));
                    }
                    break;
                case "ERROR":
                    Iterator it4 = zza().iterator();
                    while (it4.hasNext()) {
                        ((zzau) it4.next()).zzd(jOptLong, 2100, zzS(jSONObject));
                    }
                    if (this.zzy != null) {
                        this.zzy.zzb(MediaError.zza(jSONObject));
                        break;
                    } else {
                        break;
                    }
                    break;
                case "QUEUE_ITEM_IDS":
                    this.zzq.zzd(jOptLong, 0, null);
                    zzU(jSONObject, "QUEUE_ITEM_IDS");
                    if (this.zzy != null && (iArrZzaa = zzaa(jSONObject.getJSONArray("itemIds"))) != null) {
                        this.zzy.zze(iArrZzaa);
                        break;
                    }
                    break;
                case "QUEUE_CHANGE":
                    this.zzs.zzd(jOptLong, 0, null);
                    zzU(jSONObject, "QUEUE_CHANGE");
                    if (this.zzy != null) {
                        String string2 = jSONObject.getString("changeType");
                        int[] iArrZzaa2 = zzaa(jSONObject.getJSONArray("itemIds"));
                        int iOptInt = jSONObject.optInt("insertBefore", 0);
                        if (iArrZzaa2 != null) {
                            switch (string2) {
                                case "INSERT":
                                    b = 0;
                                    break;
                                case "REMOVE":
                                    b = 2;
                                    break;
                                case "UPDATE":
                                    b = 3;
                                    break;
                                case "ITEMS_CHANGE":
                                    b = 1;
                                    break;
                                default:
                                    b = -1;
                                    break;
                            }
                            if (b == 0) {
                                this.zzy.zzf(iArrZzaa2, iOptInt);
                                break;
                            } else if (b == 1) {
                                this.zzy.zzj(iArrZzaa2);
                                break;
                            } else if (b == 2) {
                                this.zzy.zzh(iArrZzaa2);
                                break;
                            } else if (b == 3) {
                                int[] iArrZzaa3 = zzaa(jSONObject.getJSONArray("itemIds"));
                                Preconditions.checkNotNull(iArrZzaa3, "A list of item IDs is expected in a QUEUE UPDATE message.");
                                JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("reorderItemIds");
                                if (jSONArrayOptJSONArray != null) {
                                    this.zzy.zzi(CastUtils.zzd(iArrZzaa3), CastUtils.zzd((int[]) Preconditions.checkNotNull(zzaa(jSONArrayOptJSONArray))), jSONObject.optInt("insertBefore", 0));
                                } else {
                                    this.zzy.zze(iArrZzaa3);
                                }
                                break;
                            }
                        }
                    }
                    break;
                case "QUEUE_ITEMS":
                    this.zzr.zzd(jOptLong, 0, null);
                    zzU(jSONObject, "QUEUE_ITEMS");
                    if (this.zzy != null) {
                        JSONArray jSONArray2 = jSONObject.getJSONArray(FirebaseAnalytics.Param.ITEMS);
                        MediaQueueItem[] mediaQueueItemArr = new MediaQueueItem[jSONArray2.length()];
                        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                            mediaQueueItemArr[i2] = new MediaQueueItem.Builder(jSONArray2.getJSONObject(i2)).build();
                        }
                        this.zzy.zzg(mediaQueueItemArr);
                        break;
                    }
                    break;
            }
        } catch (JSONException e) {
            this.zza.w("Message is malformed (%s); ignoring: %s", e.getMessage(), str);
        }
    }

    public final void zzP(long j, int i) {
        Iterator it = zza().iterator();
        while (it.hasNext()) {
            ((zzau) it.next()).zzd(j, i, null);
        }
    }

    public final void zzQ(zzan zzanVar) {
        this.zzy = zzanVar;
    }

    @Override // com.google.android.gms.cast.internal.zzp
    public final void zzf() {
        zzb();
        zzT();
    }

    public final long zzj() {
        MediaStatus mediaStatus;
        AdBreakStatus adBreakStatus;
        if (this.zzv == 0 || (mediaStatus = this.zzw) == null || (adBreakStatus = mediaStatus.getAdBreakStatus()) == null) {
            return 0L;
        }
        double playbackRate = mediaStatus.getPlaybackRate();
        if (playbackRate == 0.0d) {
            playbackRate = 1.0d;
        }
        return zzR(mediaStatus.getPlayerState() != 2 ? 0.0d : playbackRate, adBreakStatus.getCurrentBreakClipTimeInMs(), 0L);
    }

    public final long zzk() {
        MediaLiveSeekableRange liveSeekableRange;
        MediaStatus mediaStatus = this.zzw;
        if (mediaStatus == null || (liveSeekableRange = mediaStatus.getLiveSeekableRange()) == null) {
            return 0L;
        }
        long endTime = liveSeekableRange.getEndTime();
        return !liveSeekableRange.isLiveDone() ? zzR(1.0d, endTime, -1L) : endTime;
    }

    public final long zzl() {
        MediaLiveSeekableRange liveSeekableRange;
        MediaStatus mediaStatus = this.zzw;
        if (mediaStatus == null || (liveSeekableRange = mediaStatus.getLiveSeekableRange()) == null) {
            return 0L;
        }
        long startTime = liveSeekableRange.getStartTime();
        if (liveSeekableRange.isMovingWindow()) {
            startTime = zzR(1.0d, startTime, -1L);
        }
        return liveSeekableRange.isLiveDone() ? Math.min(startTime, liveSeekableRange.getEndTime()) : startTime;
    }

    public final long zzm() {
        MediaStatus mediaStatus;
        MediaInfo mediaInfoZzK = zzK();
        if (mediaInfoZzK == null || (mediaStatus = this.zzw) == null) {
            return 0L;
        }
        Long l = this.zzx;
        if (l == null) {
            if (this.zzv == 0) {
                return 0L;
            }
            double playbackRate = mediaStatus.getPlaybackRate();
            long streamPosition = mediaStatus.getStreamPosition();
            return (playbackRate == 0.0d || mediaStatus.getPlayerState() != 2) ? streamPosition : zzR(playbackRate, streamPosition, mediaInfoZzK.getStreamDuration());
        }
        if (l.equals(4294967296000L)) {
            if (this.zzw.getLiveSeekableRange() != null) {
                return Math.min(l.longValue(), zzk());
            }
            if (zzo() >= 0) {
                return Math.min(l.longValue(), zzo());
            }
        }
        return l.longValue();
    }

    public final long zzn() throws zzao {
        MediaStatus mediaStatus = this.zzw;
        if (mediaStatus != null) {
            return mediaStatus.zzb();
        }
        throw new zzao();
    }

    public final long zzo() {
        MediaInfo mediaInfoZzK = zzK();
        if (mediaInfoZzK != null) {
            return mediaInfoZzK.getStreamDuration();
        }
        return 0L;
    }

    public final long zzp(zzas zzasVar, MediaLoadRequestData mediaLoadRequestData) throws IllegalStateException, IllegalArgumentException {
        if (mediaLoadRequestData.getMediaInfo() == null && mediaLoadRequestData.getQueueData() == null) {
            throw new IllegalArgumentException("MediaInfo and MediaQueueData should not be both null");
        }
        JSONObject json = mediaLoadRequestData.toJson();
        if (json == null) {
            throw new IllegalArgumentException("Failed to jsonify the load request due to malformed request");
        }
        long jZzd = zzd();
        try {
            json.put("requestId", jZzd);
            json.put(ModuleXmlParser.TYPE, "LOAD");
        } catch (JSONException unused) {
        }
        zzg(json.toString(), jZzd, null);
        this.zzc.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzq(zzas zzasVar, JSONObject jSONObject) throws IllegalStateException, zzao {
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "PAUSE");
            jSONObject2.put("mediaSessionId", zzn());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzd.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzr(zzas zzasVar, JSONObject jSONObject) throws IllegalStateException, zzao {
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "PLAY");
            jSONObject2.put("mediaSessionId", zzn());
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zze.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzs(String str, List list) throws IllegalStateException {
        long jZzd = zzd();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "PRECACHE");
            jSONObject.put("precacheData", str);
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        return jZzd;
    }

    public final long zzt(zzas zzasVar, int i, int i2, int i3) throws IllegalArgumentException, zzao {
        if (i2 > 0 && i3 == 0) {
            i3 = 0;
        } else if (i2 != 0 || i3 <= 0) {
            throw new IllegalArgumentException("Exactly one of nextCount and prevCount must be positive and the other must be zero");
        }
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "QUEUE_GET_ITEM_RANGE");
            jSONObject.put("mediaSessionId", zzn());
            jSONObject.put("itemId", i);
            if (i2 > 0) {
                jSONObject.put("nextCount", i2);
            }
            if (i3 > 0) {
                jSONObject.put("prevCount", i3);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzs.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzu(zzas zzasVar) throws IllegalStateException, zzao {
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "QUEUE_GET_ITEM_IDS");
            jSONObject.put("mediaSessionId", zzn());
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzq.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzv(zzas zzasVar, int[] iArr) throws IllegalArgumentException, zzao {
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "QUEUE_GET_ITEMS");
            jSONObject.put("mediaSessionId", zzn());
            JSONArray jSONArray = new JSONArray();
            for (int i : iArr) {
                jSONArray.put(i);
            }
            jSONObject.put("itemIds", jSONArray);
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzr.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzD(zzas zzasVar, long[] jArr) throws IllegalStateException, zzao {
        if (jArr == null) {
            throw new IllegalArgumentException("trackIds cannot be null");
        }
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "EDIT_TRACKS_INFO");
            jSONObject.put("mediaSessionId", zzn());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < jArr.length; i++) {
                jSONArray.put(i, jArr[i]);
            }
            jSONObject.put("activeTrackIds", jSONArray);
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzk.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzH(zzas zzasVar, TextTrackStyle textTrackStyle) throws IllegalStateException, zzao {
        if (textTrackStyle == null) {
            throw new IllegalArgumentException("trackStyle cannot be null");
        }
        JSONObject jSONObject = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject.put("requestId", jZzd);
            jSONObject.put(ModuleXmlParser.TYPE, "EDIT_TRACKS_INFO");
            jSONObject.put("textTrackStyle", textTrackStyle.zza());
            jSONObject.put("mediaSessionId", zzn());
        } catch (JSONException unused) {
        }
        zzg(jSONObject.toString(), jZzd, null);
        this.zzl.zzb(jZzd, zzasVar);
        return jZzd;
    }

    public final long zzy(zzas zzasVar, int[] iArr, JSONObject jSONObject) throws IllegalStateException, IllegalArgumentException, zzao {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIdsToRemove must not be null or empty.");
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "QUEUE_REMOVE");
            jSONObject2.put("mediaSessionId", zzn());
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < iArr.length; i++) {
                jSONArray.put(i, iArr[i]);
            }
            jSONObject2.put("itemIds", jSONArray);
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
            if (zzZ()) {
                jSONObject2.put("sequenceNumber", this.zzz);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzo.zzb(jZzd, new zzam(this, zzasVar));
        return jZzd;
    }

    public final long zzz(zzas zzasVar, int[] iArr, int i, JSONObject jSONObject) throws IllegalStateException, IllegalArgumentException, zzao {
        if (iArr == null || iArr.length == 0) {
            throw new IllegalArgumentException("itemIdsToReorder must not be null or empty.");
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "QUEUE_REORDER");
            jSONObject2.put("mediaSessionId", zzn());
            JSONArray jSONArray = new JSONArray();
            for (int i2 = 0; i2 < iArr.length; i2++) {
                jSONArray.put(i2, iArr[i2]);
            }
            jSONObject2.put("itemIds", jSONArray);
            if (i != 0) {
                jSONObject2.put("insertBefore", i);
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
            if (zzZ()) {
                jSONObject2.put("sequenceNumber", this.zzz);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzp.zzb(jZzd, new zzam(this, zzasVar));
        return jZzd;
    }

    public final long zzw(zzas zzasVar, MediaQueueItem[] mediaQueueItemArr, int i, int i2, int i3, long j, JSONObject jSONObject) throws IllegalStateException, IllegalArgumentException, zzao {
        if (mediaQueueItemArr == null || mediaQueueItemArr.length == 0) {
            throw new IllegalArgumentException("itemsToInsert must not be null or empty.");
        }
        if (j != -1 && j < 0) {
            throw new IllegalArgumentException("playPosition can not be negative: " + j);
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "QUEUE_INSERT");
            jSONObject2.put("mediaSessionId", zzn());
            JSONArray jSONArray = new JSONArray();
            for (int i4 = 0; i4 < mediaQueueItemArr.length; i4++) {
                jSONArray.put(i4, mediaQueueItemArr[i4].toJson());
            }
            jSONObject2.put(FirebaseAnalytics.Param.ITEMS, jSONArray);
            if (i != 0) {
                jSONObject2.put("insertBefore", i);
            }
            if (i3 != -1) {
                jSONObject2.put("currentItemIndex", 0);
            }
            if (j != -1) {
                jSONObject2.put("currentTime", CastUtils.millisecToSec(j));
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
            if (zzZ()) {
                jSONObject2.put("sequenceNumber", this.zzz);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzm.zzb(jZzd, new zzam(this, zzasVar));
        return jZzd;
    }

    public final long zzA(zzas zzasVar, int i, long j, MediaQueueItem[] mediaQueueItemArr, int i2, Boolean bool, Integer num, JSONObject jSONObject) throws IllegalStateException, IllegalArgumentException, zzao {
        if (j != -1 && j < 0) {
            throw new IllegalArgumentException("playPosition cannot be negative: " + j);
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "QUEUE_UPDATE");
            jSONObject2.put("mediaSessionId", zzn());
            if (i != 0) {
                jSONObject2.put("currentItemId", i);
            }
            if (i2 != 0) {
                jSONObject2.put("jump", i2);
            }
            if (mediaQueueItemArr != null && mediaQueueItemArr.length > 0) {
                JSONArray jSONArray = new JSONArray();
                for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                    jSONArray.put(i3, mediaQueueItemArr[i3].toJson());
                }
                jSONObject2.put(FirebaseAnalytics.Param.ITEMS, jSONArray);
            }
            if (bool != null) {
                jSONObject2.put("shuffle", bool);
            }
            String strZza = MediaCommon.zza(num);
            if (strZza != null) {
                jSONObject2.put("repeatMode", strZza);
            }
            if (j != -1) {
                jSONObject2.put("currentTime", CastUtils.millisecToSec(j));
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
            if (zzZ()) {
                jSONObject2.put("sequenceNumber", this.zzz);
            }
        } catch (JSONException unused) {
        }
        zzg(jSONObject2.toString(), jZzd, null);
        this.zzn.zzb(jZzd, new zzam(this, zzasVar));
        return jZzd;
    }

    public final long zzx(zzas zzasVar, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) throws IllegalStateException, IllegalArgumentException {
        int length;
        if (mediaQueueItemArr == null || (length = mediaQueueItemArr.length) == 0) {
            throw new IllegalArgumentException("items must not be null or empty.");
        }
        if (i < 0 || i >= length) {
            throw new IllegalArgumentException("Invalid startIndex: " + i);
        }
        if (j != -1 && j < 0) {
            throw new IllegalArgumentException("playPosition can not be negative: " + j);
        }
        JSONObject jSONObject2 = new JSONObject();
        long jZzd = zzd();
        this.zzc.zzb(jZzd, zzasVar);
        try {
            jSONObject2.put("requestId", jZzd);
            jSONObject2.put(ModuleXmlParser.TYPE, "QUEUE_LOAD");
            JSONArray jSONArray = new JSONArray();
            for (int i3 = 0; i3 < mediaQueueItemArr.length; i3++) {
                jSONArray.put(i3, mediaQueueItemArr[i3].toJson());
            }
            jSONObject2.put(FirebaseAnalytics.Param.ITEMS, jSONArray);
            String strZza = MediaCommon.zza(Integer.valueOf(i2));
            if (strZza == null) {
                throw new IllegalArgumentException("Invalid repeat mode: " + i2);
            }
            jSONObject2.put("repeatMode", strZza);
            jSONObject2.put("startIndex", i);
            if (j != -1) {
                jSONObject2.put("currentTime", CastUtils.millisecToSec(j));
            }
            if (jSONObject != null) {
                jSONObject2.put("customData", jSONObject);
            }
            if (zzZ()) {
                jSONObject2.put("sequenceNumber", this.zzz);
            }
            zzg(jSONObject2.toString(), jZzd, null);
            return jZzd;
        } catch (JSONException unused) {
        }
    }
}
