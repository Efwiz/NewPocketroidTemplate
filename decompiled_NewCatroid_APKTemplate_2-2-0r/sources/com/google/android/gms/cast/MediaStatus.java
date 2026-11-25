package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import com.google.android.gms.cast.internal.CastUtils;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.cast.internal.media.MediaCommon;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.util.JsonUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public class MediaStatus extends AbstractSafeParcelable {
    public static final long COMMAND_DISLIKE = 32768;
    public static final long COMMAND_EDIT_TRACKS = 4096;
    public static final long COMMAND_FOLLOW = 65536;
    public static final long COMMAND_LIKE = 16384;
    public static final long COMMAND_PAUSE = 1;
    public static final long COMMAND_PLAYBACK_RATE = 8192;
    public static final long COMMAND_QUEUE_NEXT = 64;
    public static final long COMMAND_QUEUE_PREVIOUS = 128;
    public static final long COMMAND_QUEUE_REPEAT = 3072;
    public static final long COMMAND_QUEUE_REPEAT_ALL = 1024;
    public static final long COMMAND_QUEUE_REPEAT_ONE = 2048;
    public static final long COMMAND_QUEUE_SHUFFLE = 256;
    public static final long COMMAND_SEEK = 2;
    public static final long COMMAND_SET_VOLUME = 4;
    public static final long COMMAND_SKIP_AD = 512;

    @Deprecated
    public static final long COMMAND_SKIP_BACKWARD = 32;

    @Deprecated
    public static final long COMMAND_SKIP_FORWARD = 16;
    public static final long COMMAND_STREAM_TRANSFER = 262144;
    public static final long COMMAND_TOGGLE_MUTE = 8;
    public static final long COMMAND_UNFOLLOW = 131072;
    public static final int IDLE_REASON_CANCELED = 2;
    public static final int IDLE_REASON_ERROR = 4;
    public static final int IDLE_REASON_FINISHED = 1;
    public static final int IDLE_REASON_INTERRUPTED = 3;
    public static final int IDLE_REASON_NONE = 0;
    public static final int PLAYER_STATE_BUFFERING = 4;
    public static final int PLAYER_STATE_IDLE = 1;
    public static final int PLAYER_STATE_LOADING = 5;
    public static final int PLAYER_STATE_PAUSED = 3;
    public static final int PLAYER_STATE_PLAYING = 2;
    public static final int PLAYER_STATE_UNKNOWN = 0;
    public static final int REPEAT_MODE_REPEAT_ALL = 1;
    public static final int REPEAT_MODE_REPEAT_ALL_AND_SHUFFLE = 3;
    public static final int REPEAT_MODE_REPEAT_OFF = 0;
    public static final int REPEAT_MODE_REPEAT_SINGLE = 2;
    MediaInfo zza;
    long zzb;
    int zzc;
    double zzd;
    int zze;
    int zzf;
    long zzg;
    long zzh;
    double zzi;
    boolean zzj;
    long[] zzk;
    int zzl;
    int zzm;
    String zzn;
    JSONObject zzo;
    int zzp;
    final List zzq;
    boolean zzr;
    AdBreakStatus zzs;
    VideoInfo zzt;
    MediaLiveSeekableRange zzu;
    MediaQueueData zzv;
    boolean zzw;
    private final SparseArray zzy;
    private final Writer zzz;
    private static final Logger zzx = new Logger("MediaStatus");
    public static final Parcelable.Creator<MediaStatus> CREATOR = new zzcm();

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    public static class Builder {
        private MediaInfo zza;
        private long zzb;
        private double zzd;
        private long zzg;
        private long zzh;
        private double zzi;
        private boolean zzj;
        private long[] zzk;
        private JSONObject zzn;
        private boolean zzq;
        private AdBreakStatus zzr;
        private VideoInfo zzs;
        private MediaLiveSeekableRange zzt;
        private MediaQueueData zzu;
        private int zzc = 0;
        private int zze = 0;
        private int zzf = 0;
        private int zzl = 0;
        private int zzm = 0;
        private int zzo = 0;
        private final List zzp = new ArrayList();

        public MediaStatus build() {
            MediaStatus mediaStatus = new MediaStatus(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi, this.zzj, this.zzk, this.zzl, this.zzm, null, this.zzo, this.zzp, this.zzq, this.zzr, this.zzs, this.zzt, this.zzu);
            mediaStatus.zzo = this.zzn;
            return mediaStatus;
        }

        public Builder setActiveTrackIds(long[] jArr) {
            this.zzk = jArr;
            return this;
        }

        public Builder setAdBreakStatus(AdBreakStatus adBreakStatus) {
            this.zzr = adBreakStatus;
            return this;
        }

        public Builder setCurrentItemId(int i) {
            this.zzc = i;
            return this;
        }

        public Builder setCustomData(JSONObject jSONObject) {
            this.zzn = jSONObject;
            return this;
        }

        public Builder setIdleReason(int i) {
            this.zzf = i;
            return this;
        }

        public Builder setIsMute(boolean z) {
            this.zzj = z;
            return this;
        }

        public Builder setIsPlayingAd(boolean z) {
            this.zzq = z;
            return this;
        }

        public Builder setLiveSeekableRange(MediaLiveSeekableRange mediaLiveSeekableRange) {
            this.zzt = mediaLiveSeekableRange;
            return this;
        }

        public Builder setLoadingItemId(int i) {
            this.zzl = i;
            return this;
        }

        public Builder setMediaInfo(MediaInfo mediaInfo) {
            this.zza = mediaInfo;
            return this;
        }

        public Builder setMediaSessionId(long j) {
            this.zzb = j;
            return this;
        }

        public Builder setPlaybackRate(double d) {
            this.zzd = d;
            return this;
        }

        public Builder setPlayerState(int i) {
            this.zze = i;
            return this;
        }

        public Builder setPreloadedItemId(int i) {
            this.zzm = i;
            return this;
        }

        public Builder setQueueData(MediaQueueData mediaQueueData) {
            this.zzu = mediaQueueData;
            return this;
        }

        public Builder setQueueItems(List<MediaQueueItem> list) {
            this.zzp.clear();
            this.zzp.addAll(list);
            return this;
        }

        public Builder setQueueRepeatMode(int i) {
            this.zzo = i;
            return this;
        }

        public Builder setStreamPosition(long j) {
            this.zzg = j;
            return this;
        }

        public Builder setStreamVolume(double d) {
            this.zzi = d;
            return this;
        }

        public Builder setSupportedMediaCommands(long j) {
            this.zzh = j;
            return this;
        }

        public Builder setVideoInfo(VideoInfo videoInfo) {
            this.zzs = videoInfo;
            return this;
        }
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
    public class Writer {
        public Writer() {
        }

        public void setActiveTrackIds(long[] jArr) {
            MediaStatus.this.zzk = jArr;
        }

        public void setAdBreakStatus(AdBreakStatus adBreakStatus) {
            MediaStatus.this.zzs = adBreakStatus;
        }

        public void setCurrentItemId(int i) {
            MediaStatus.this.zzc = i;
        }

        public void setCustomData(JSONObject jSONObject) {
            MediaStatus mediaStatus = MediaStatus.this;
            mediaStatus.zzo = jSONObject;
            mediaStatus.zzn = null;
        }

        public void setIdleReason(int i) {
            MediaStatus.this.zzf = i;
        }

        public void setIsPlayingAd(boolean z) {
            MediaStatus.this.zzr = z;
        }

        public void setLiveSeekableRange(MediaLiveSeekableRange mediaLiveSeekableRange) {
            MediaStatus.this.zzu = mediaLiveSeekableRange;
        }

        public void setLoadingItemId(int i) {
            MediaStatus.this.zzl = i;
        }

        public void setMediaInfo(MediaInfo mediaInfo) {
            MediaStatus.this.zza = mediaInfo;
        }

        public void setMute(boolean z) {
            MediaStatus.this.zzj = z;
        }

        public void setPlaybackRate(double d) {
            MediaStatus.this.zzd = d;
        }

        public void setPlayerState(int i) {
            MediaStatus.this.zze = i;
        }

        public void setPreloadedItemId(int i) {
            MediaStatus.this.zzm = i;
        }

        public void setQueueData(MediaQueueData mediaQueueData) {
            MediaStatus.this.zzv = mediaQueueData;
        }

        public void setQueueItems(List<MediaQueueItem> list) {
            MediaStatus.this.zze(list);
        }

        public void setQueueRepeatMode(int i) {
            MediaStatus.this.zzp = i;
        }

        public void setShuffle(boolean z) {
            MediaStatus.this.zzw = z;
        }

        public void setStreamPosition(long j) {
            MediaStatus.this.zzg = j;
        }

        public void setStreamVolume(double d) {
            MediaStatus.this.zzi = d;
        }

        public void setSupportedMediaCommands(long j) {
            MediaStatus.this.zzh = j;
        }

        public void setVideoInfo(VideoInfo videoInfo) {
            MediaStatus.this.zzt = videoInfo;
        }
    }

    public MediaStatus(MediaInfo mediaInfo, long j, int i, double d, int i2, int i3, long j2, long j3, double d2, boolean z, long[] jArr, int i4, int i5, String str, int i6, List list, boolean z2, AdBreakStatus adBreakStatus, VideoInfo videoInfo, MediaLiveSeekableRange mediaLiveSeekableRange, MediaQueueData mediaQueueData) {
        this.zzq = new ArrayList();
        this.zzy = new SparseArray();
        this.zzz = new Writer();
        this.zza = mediaInfo;
        this.zzb = j;
        this.zzc = i;
        this.zzd = d;
        this.zze = i2;
        this.zzf = i3;
        this.zzg = j2;
        this.zzh = j3;
        this.zzi = d2;
        this.zzj = z;
        this.zzk = jArr;
        this.zzl = i4;
        this.zzm = i5;
        this.zzn = str;
        if (str != null) {
            try {
                this.zzo = new JSONObject(this.zzn);
            } catch (JSONException unused) {
                this.zzo = null;
                this.zzn = null;
            }
        } else {
            this.zzo = null;
        }
        this.zzp = i6;
        if (list != null && !list.isEmpty()) {
            zze(list);
        }
        this.zzr = z2;
        this.zzs = adBreakStatus;
        this.zzt = videoInfo;
        this.zzu = mediaLiveSeekableRange;
        this.zzv = mediaQueueData;
        boolean z3 = false;
        if (mediaQueueData != null && mediaQueueData.zzk()) {
            z3 = true;
        }
        this.zzw = z3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zze(List list) {
        this.zzq.clear();
        this.zzy.clear();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                MediaQueueItem mediaQueueItem = (MediaQueueItem) list.get(i);
                this.zzq.add(mediaQueueItem);
                this.zzy.put(mediaQueueItem.getItemId(), Integer.valueOf(i));
            }
        }
    }

    private static final boolean zzf(int i, int i2, int i3, int i4) {
        if (i != 1) {
            return false;
        }
        if (i2 != 1) {
            if (i2 == 2) {
                return i4 != 2;
            }
            if (i2 != 3) {
                return true;
            }
        }
        return i3 == 0;
    }

    public boolean equals(Object obj) {
        JSONObject jSONObject;
        JSONObject jSONObject2;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaStatus)) {
            return false;
        }
        MediaStatus mediaStatus = (MediaStatus) obj;
        return (this.zzo == null) == (mediaStatus.zzo == null) && this.zzb == mediaStatus.zzb && this.zzc == mediaStatus.zzc && this.zzd == mediaStatus.zzd && this.zze == mediaStatus.zze && this.zzf == mediaStatus.zzf && this.zzg == mediaStatus.zzg && this.zzi == mediaStatus.zzi && this.zzj == mediaStatus.zzj && this.zzl == mediaStatus.zzl && this.zzm == mediaStatus.zzm && this.zzp == mediaStatus.zzp && Arrays.equals(this.zzk, mediaStatus.zzk) && CastUtils.zze(Long.valueOf(this.zzh), Long.valueOf(mediaStatus.zzh)) && CastUtils.zze(this.zzq, mediaStatus.zzq) && CastUtils.zze(this.zza, mediaStatus.zza) && ((jSONObject = this.zzo) == null || (jSONObject2 = mediaStatus.zzo) == null || JsonUtils.areJsonValuesEquivalent(jSONObject, jSONObject2)) && this.zzr == mediaStatus.isPlayingAd() && CastUtils.zze(this.zzs, mediaStatus.zzs) && CastUtils.zze(this.zzt, mediaStatus.zzt) && CastUtils.zze(this.zzu, mediaStatus.zzu) && Objects.equal(this.zzv, mediaStatus.zzv) && this.zzw == mediaStatus.zzw;
    }

    public long[] getActiveTrackIds() {
        return this.zzk;
    }

    public AdBreakStatus getAdBreakStatus() {
        return this.zzs;
    }

    public AdBreakInfo getCurrentAdBreak() {
        MediaInfo mediaInfo;
        List<AdBreakInfo> adBreaks;
        AdBreakStatus adBreakStatus = this.zzs;
        if (adBreakStatus == null) {
            return null;
        }
        String breakId = adBreakStatus.getBreakId();
        if (!TextUtils.isEmpty(breakId) && (mediaInfo = this.zza) != null && (adBreaks = mediaInfo.getAdBreaks()) != null && !adBreaks.isEmpty()) {
            for (AdBreakInfo adBreakInfo : adBreaks) {
                if (breakId.equals(adBreakInfo.getId())) {
                    return adBreakInfo;
                }
            }
        }
        return null;
    }

    public AdBreakClipInfo getCurrentAdBreakClip() {
        MediaInfo mediaInfo;
        List<AdBreakClipInfo> adBreakClips;
        AdBreakStatus adBreakStatus = this.zzs;
        if (adBreakStatus == null) {
            return null;
        }
        String breakClipId = adBreakStatus.getBreakClipId();
        if (!TextUtils.isEmpty(breakClipId) && (mediaInfo = this.zza) != null && (adBreakClips = mediaInfo.getAdBreakClips()) != null && !adBreakClips.isEmpty()) {
            for (AdBreakClipInfo adBreakClipInfo : adBreakClips) {
                if (breakClipId.equals(adBreakClipInfo.getId())) {
                    return adBreakClipInfo;
                }
            }
        }
        return null;
    }

    public int getCurrentItemId() {
        return this.zzc;
    }

    public JSONObject getCustomData() {
        return this.zzo;
    }

    public int getIdleReason() {
        return this.zzf;
    }

    public Integer getIndexById(int i) {
        return (Integer) this.zzy.get(i);
    }

    public MediaQueueItem getItemById(int i) {
        Integer num = (Integer) this.zzy.get(i);
        if (num == null) {
            return null;
        }
        return (MediaQueueItem) this.zzq.get(num.intValue());
    }

    public MediaQueueItem getItemByIndex(int i) {
        if (i < 0 || i >= this.zzq.size()) {
            return null;
        }
        return (MediaQueueItem) this.zzq.get(i);
    }

    public MediaLiveSeekableRange getLiveSeekableRange() {
        return this.zzu;
    }

    public int getLoadingItemId() {
        return this.zzl;
    }

    public MediaInfo getMediaInfo() {
        return this.zza;
    }

    public double getPlaybackRate() {
        return this.zzd;
    }

    public int getPlayerState() {
        return this.zze;
    }

    public int getPreloadedItemId() {
        return this.zzm;
    }

    public MediaQueueData getQueueData() {
        return this.zzv;
    }

    public MediaQueueItem getQueueItem(int i) {
        return getItemByIndex(i);
    }

    public MediaQueueItem getQueueItemById(int i) {
        return getItemById(i);
    }

    public int getQueueItemCount() {
        return this.zzq.size();
    }

    public List<MediaQueueItem> getQueueItems() {
        return this.zzq;
    }

    public int getQueueRepeatMode() {
        return this.zzp;
    }

    public long getStreamPosition() {
        return this.zzg;
    }

    public double getStreamVolume() {
        return this.zzi;
    }

    public long getSupportedMediaCommands() {
        return this.zzh;
    }

    public VideoInfo getVideoInfo() {
        return this.zzt;
    }

    public Writer getWriter() {
        return this.zzz;
    }

    public int hashCode() {
        return Objects.hashCode(this.zza, Long.valueOf(this.zzb), Integer.valueOf(this.zzc), Double.valueOf(this.zzd), Integer.valueOf(this.zze), Integer.valueOf(this.zzf), Long.valueOf(this.zzg), Long.valueOf(this.zzh), Double.valueOf(this.zzi), Boolean.valueOf(this.zzj), Integer.valueOf(Arrays.hashCode(this.zzk)), Integer.valueOf(this.zzl), Integer.valueOf(this.zzm), String.valueOf(this.zzo), Integer.valueOf(this.zzp), this.zzq, Boolean.valueOf(this.zzr), this.zzs, this.zzt, this.zzu, this.zzv);
    }

    public boolean isMediaCommandSupported(long j) {
        return (j & this.zzh) != 0;
    }

    public boolean isMute() {
        return this.zzj;
    }

    public boolean isPlayingAd() {
        return this.zzr;
    }

    public JSONObject toJson() {
        String str;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("mediaSessionId", this.zzb);
            int i = this.zze;
            String str2 = "IDLE";
            if (i != 1) {
                if (i == 2) {
                    str2 = "PLAYING";
                } else if (i == 3) {
                    str2 = "PAUSED";
                } else if (i == 4) {
                    str2 = "BUFFERING";
                } else if (i == 5) {
                    str2 = "LOADING";
                }
            }
            jSONObject.put("playerState", str2);
            JSONArray jSONArray = null;
            if (this.zze == 1) {
                int i2 = this.zzf;
                if (i2 == 1) {
                    str = "FINISHED";
                } else if (i2 == 2) {
                    str = "CANCELLED";
                } else if (i2 != 3) {
                    str = i2 != 4 ? null : MediaError.ERROR_TYPE_ERROR;
                } else {
                    str = "INTERRUPTED";
                }
                jSONObject.putOpt("idleReason", str);
            }
            jSONObject.put("playbackRate", this.zzd);
            jSONObject.put("currentTime", CastUtils.millisecToSec(this.zzg));
            jSONObject.put("supportedMediaCommands", this.zzh);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(FirebaseAnalytics.Param.LEVEL, this.zzi);
            jSONObject2.put("muted", this.zzj);
            jSONObject.put("volume", jSONObject2);
            if (this.zzk != null) {
                jSONArray = new JSONArray();
                for (long j : this.zzk) {
                    jSONArray.put(j);
                }
            }
            jSONObject.putOpt("activeTrackIds", jSONArray);
            jSONObject.putOpt("customData", this.zzo);
            jSONObject.putOpt("shuffle", Boolean.valueOf(this.zzw));
            MediaInfo mediaInfo = this.zza;
            if (mediaInfo != null) {
                jSONObject.putOpt("media", mediaInfo.zza());
            }
            int i3 = this.zzc;
            if (i3 != 0) {
                jSONObject.put("currentItemId", i3);
            }
            int i4 = this.zzm;
            if (i4 != 0) {
                jSONObject.put("preloadedItemId", i4);
            }
            int i5 = this.zzl;
            if (i5 != 0) {
                jSONObject.put("loadingItemId", i5);
            }
            AdBreakStatus adBreakStatus = this.zzs;
            if (adBreakStatus != null) {
                jSONObject.putOpt("breakStatus", adBreakStatus.zzb());
            }
            VideoInfo videoInfo = this.zzt;
            if (videoInfo != null) {
                jSONObject.putOpt("videoInfo", videoInfo.zzb());
            }
            MediaQueueData mediaQueueData = this.zzv;
            if (mediaQueueData != null) {
                jSONObject.putOpt("queueData", mediaQueueData.zza());
            }
            MediaLiveSeekableRange mediaLiveSeekableRange = this.zzu;
            if (mediaLiveSeekableRange != null) {
                jSONObject.putOpt("liveSeekableRange", mediaLiveSeekableRange.zzb());
            }
            jSONObject.putOpt("repeatMode", MediaCommon.zza(Integer.valueOf(this.zzp)));
            List list = this.zzq;
            if (list != null && !list.isEmpty()) {
                JSONArray jSONArray2 = new JSONArray();
                Iterator it = this.zzq.iterator();
                while (it.hasNext()) {
                    jSONArray2.put(((MediaQueueItem) it.next()).toJson());
                }
                jSONObject.put(FirebaseAnalytics.Param.ITEMS, jSONArray2);
            }
            return jSONObject;
        } catch (JSONException e) {
            zzx.e(e, "Error transforming MediaStatus into JSONObject", new Object[0]);
            return new JSONObject();
        }
    }

    /* JADX WARN: Code duplicated, block: B:102:0x018c A[EDGE_INSN: B:102:0x018c->B:103:0x0190 BREAK  A[LOOP:0: B:95:0x0178->B:99:0x0185]] */
    /* JADX WARN: Code duplicated, block: B:142:0x023e  */
    public final int zza(JSONObject jSONObject, int i) throws JSONException {
        int i2;
        MediaInfo mediaInfo;
        boolean zFromJson;
        MediaInfo mediaInfo2;
        int i3;
        int i4;
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("extendedStatus");
        boolean z = false;
        if (jSONObjectOptJSONObject != null) {
            try {
                ArrayList arrayList = new ArrayList();
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    arrayList.add(itKeys.next());
                }
                JSONObject jSONObject2 = new JSONObject(jSONObject, (String[]) arrayList.toArray(new String[0]));
                Iterator<String> itKeys2 = jSONObjectOptJSONObject.keys();
                while (itKeys2.hasNext()) {
                    String next = itKeys2.next();
                    jSONObject2.put(next, jSONObjectOptJSONObject.get(next));
                }
                jSONObject2.remove("extendedStatus");
                jSONObject = jSONObject2;
            } catch (JSONException unused) {
            }
        }
        long j = jSONObject.getLong("mediaSessionId");
        if (j != this.zzb) {
            this.zzb = j;
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (jSONObject.has("playerState")) {
            String string = jSONObject.getString("playerState");
            int i5 = 3;
            if (string.equals("IDLE")) {
                i4 = 1;
            } else if (string.equals("PLAYING")) {
                i4 = 2;
            } else if (string.equals("PAUSED")) {
                i4 = 3;
            } else if (string.equals("BUFFERING")) {
                i4 = 4;
            } else {
                i4 = string.equals("LOADING") ? 5 : 0;
            }
            if (i4 != this.zze) {
                this.zze = i4;
                i2 |= 2;
            }
            if (i4 == 1 && jSONObject.has("idleReason")) {
                String string2 = jSONObject.getString("idleReason");
                if (string2.equals("CANCELLED")) {
                    i5 = 2;
                } else if (!string2.equals("INTERRUPTED")) {
                    i5 = string2.equals("FINISHED") ? 1 : string2.equals(MediaError.ERROR_TYPE_ERROR) ? 4 : 0;
                }
                if (i5 != this.zzf) {
                    this.zzf = i5;
                    i2 |= 2;
                }
            }
        }
        if (jSONObject.has("playbackRate")) {
            double d = jSONObject.getDouble("playbackRate");
            if (this.zzd != d) {
                this.zzd = d;
                i2 |= 2;
            }
        }
        if (jSONObject.has("currentTime")) {
            long jSecToMillisec = CastUtils.secToMillisec(jSONObject.getDouble("currentTime"));
            if (jSecToMillisec != this.zzg) {
                this.zzg = jSecToMillisec;
                i2 |= 2;
            }
            i2 |= 128;
        }
        if (jSONObject.has("supportedMediaCommands")) {
            long j2 = jSONObject.getLong("supportedMediaCommands");
            if (j2 != this.zzh) {
                this.zzh = j2;
                i2 |= 2;
            }
        }
        if (jSONObject.has("volume") && i == 0) {
            JSONObject jSONObject3 = jSONObject.getJSONObject("volume");
            double d2 = jSONObject3.getDouble(FirebaseAnalytics.Param.LEVEL);
            if (d2 != this.zzi) {
                this.zzi = d2;
                i2 |= 2;
            }
            boolean z2 = jSONObject3.getBoolean("muted");
            if (z2 != this.zzj) {
                this.zzj = z2;
                i2 |= 2;
            }
        }
        long[] jArrZzg = CastUtils.zzg(jSONObject.has("activeTrackIds") ? jSONObject.getJSONArray("activeTrackIds") : null);
        if (jArrZzg != null) {
            long[] jArr = this.zzk;
            if (jArr == null) {
                this.zzk = jArrZzg;
                i2 |= 2;
                break;
            }
            if (jArr.length != jArrZzg.length) {
                this.zzk = jArrZzg;
                i2 |= 2;
                break;
            }
            for (int i6 = 0; i6 < jArrZzg.length; i6++) {
                if (this.zzk[i6] != jArrZzg[i6]) {
                    this.zzk = jArrZzg;
                    i2 |= 2;
                    break;
                }
            }
        } else if (this.zzk != null) {
            this.zzk = jArrZzg;
            i2 |= 2;
            break;
        }
        if (jSONObject.has("customData")) {
            this.zzo = jSONObject.getJSONObject("customData");
            this.zzn = null;
            i2 |= 2;
        }
        if (jSONObject.has("media")) {
            JSONObject jSONObject4 = jSONObject.getJSONObject("media");
            MediaInfo mediaInfo3 = new MediaInfo(jSONObject4);
            MediaInfo mediaInfo4 = this.zza;
            if (mediaInfo4 == null || !mediaInfo4.equals(mediaInfo3)) {
                this.zza = mediaInfo3;
                i2 |= 2;
            }
            if (jSONObject4.has("metadata")) {
                i2 |= 4;
            }
        }
        if (jSONObject.has("currentItemId") && this.zzc != (i3 = jSONObject.getInt("currentItemId"))) {
            this.zzc = i3;
            i2 |= 2;
        }
        int iOptInt = jSONObject.optInt("preloadedItemId", 0);
        if (this.zzm != iOptInt) {
            this.zzm = iOptInt;
            i2 |= 16;
        }
        int iOptInt2 = jSONObject.optInt("loadingItemId", 0);
        if (this.zzl != iOptInt2) {
            this.zzl = iOptInt2;
            i2 |= 2;
        }
        MediaInfo mediaInfo5 = this.zza;
        if (zzf(this.zze, this.zzf, this.zzl, mediaInfo5 == null ? -1 : mediaInfo5.getStreamType())) {
            this.zzc = 0;
            this.zzl = 0;
            this.zzm = 0;
            if (!this.zzq.isEmpty()) {
                i2 |= 8;
                this.zzp = 0;
                this.zzq.clear();
                this.zzy.clear();
            }
        } else {
            if (jSONObject.has("repeatMode")) {
                Integer numMediaRepeatModeFromString = MediaCommon.mediaRepeatModeFromString(jSONObject.getString("repeatMode"));
                Integer numValueOf = Integer.valueOf(numMediaRepeatModeFromString == null ? this.zzp : numMediaRepeatModeFromString.intValue());
                if (this.zzp != numValueOf.intValue()) {
                    this.zzp = numValueOf.intValue();
                    zFromJson = true;
                } else {
                    zFromJson = false;
                }
            } else {
                zFromJson = false;
            }
            if (jSONObject.has(FirebaseAnalytics.Param.ITEMS)) {
                JSONArray jSONArray = jSONObject.getJSONArray(FirebaseAnalytics.Param.ITEMS);
                int length = jSONArray.length();
                SparseArray sparseArray = new SparseArray();
                for (int i7 = 0; i7 < length; i7++) {
                    sparseArray.put(i7, Integer.valueOf(jSONArray.getJSONObject(i7).getInt("itemId")));
                }
                ArrayList arrayList2 = new ArrayList();
                for (int i8 = 0; i8 < length; i8++) {
                    Integer num = (Integer) sparseArray.get(i8);
                    JSONObject jSONObject5 = jSONArray.getJSONObject(i8);
                    MediaQueueItem itemById = getItemById(num.intValue());
                    if (itemById != null) {
                        zFromJson |= itemById.fromJson(jSONObject5);
                        arrayList2.add(itemById);
                        if (i8 != getIndexById(num.intValue()).intValue()) {
                        }
                    } else if (num.intValue() != this.zzc || (mediaInfo2 = this.zza) == null) {
                        arrayList2.add(new MediaQueueItem(jSONObject5));
                    } else {
                        MediaQueueItem mediaQueueItemBuild = new MediaQueueItem.Builder(mediaInfo2).build();
                        mediaQueueItemBuild.fromJson(jSONObject5);
                        arrayList2.add(mediaQueueItemBuild);
                    }
                    zFromJson = true;
                }
                zFromJson |= !(this.zzq.size() == length);
                zze(arrayList2);
            }
            if (zFromJson) {
                i2 |= 8;
            }
        }
        AdBreakStatus adBreakStatusZza = AdBreakStatus.zza(jSONObject.optJSONObject("breakStatus"));
        AdBreakStatus adBreakStatus = this.zzs;
        if ((adBreakStatus == null && adBreakStatusZza != null) || (adBreakStatus != null && !adBreakStatus.equals(adBreakStatusZza))) {
            if (adBreakStatusZza != null && (adBreakStatusZza.getBreakId() != null || adBreakStatusZza.getBreakClipId() != null)) {
                z = true;
            }
            this.zzr = z;
            this.zzs = adBreakStatusZza;
            i2 |= 32;
        }
        VideoInfo videoInfoZza = VideoInfo.zza(jSONObject.optJSONObject("videoInfo"));
        VideoInfo videoInfo = this.zzt;
        if ((videoInfo == null && videoInfoZza != null) || (videoInfo != null && !videoInfo.equals(videoInfoZza))) {
            this.zzt = videoInfoZza;
            i2 |= 64;
        }
        if (jSONObject.has("breakInfo") && (mediaInfo = this.zza) != null) {
            mediaInfo.zzr(jSONObject.getJSONObject("breakInfo"));
            i2 |= 2;
        }
        if (jSONObject.has("queueData")) {
            MediaQueueData.Builder builder = new MediaQueueData.Builder();
            builder.zza(jSONObject.getJSONObject("queueData"));
            MediaQueueData mediaQueueDataBuild = builder.build();
            this.zzv = mediaQueueDataBuild;
            boolean zZzk = mediaQueueDataBuild.zzk();
            if (this.zzw != zZzk) {
                this.zzw = zZzk;
                i2 |= 8;
            }
        }
        if (jSONObject.has("liveSeekableRange")) {
            int i9 = i2 | 2;
            this.zzu = MediaLiveSeekableRange.zza(jSONObject.optJSONObject("liveSeekableRange"));
            return i9;
        }
        if (this.zzu != null) {
            i2 |= 2;
        }
        this.zzu = null;
        return i2;
    }

    public final long zzb() {
        return this.zzb;
    }

    public final boolean zzd() {
        MediaInfo mediaInfo = this.zza;
        return zzf(this.zze, this.zzf, this.zzl, mediaInfo == null ? -1 : mediaInfo.getStreamType());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        JSONObject jSONObject = this.zzo;
        this.zzn = jSONObject == null ? null : jSONObject.toString();
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, getMediaInfo(), i, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzb);
        SafeParcelWriter.writeInt(parcel, 4, getCurrentItemId());
        SafeParcelWriter.writeDouble(parcel, 5, getPlaybackRate());
        SafeParcelWriter.writeInt(parcel, 6, getPlayerState());
        SafeParcelWriter.writeInt(parcel, 7, getIdleReason());
        SafeParcelWriter.writeLong(parcel, 8, getStreamPosition());
        SafeParcelWriter.writeLong(parcel, 9, this.zzh);
        SafeParcelWriter.writeDouble(parcel, 10, getStreamVolume());
        SafeParcelWriter.writeBoolean(parcel, 11, isMute());
        SafeParcelWriter.writeLongArray(parcel, 12, getActiveTrackIds(), false);
        SafeParcelWriter.writeInt(parcel, 13, getLoadingItemId());
        SafeParcelWriter.writeInt(parcel, 14, getPreloadedItemId());
        SafeParcelWriter.writeString(parcel, 15, this.zzn, false);
        SafeParcelWriter.writeInt(parcel, 16, this.zzp);
        SafeParcelWriter.writeTypedList(parcel, 17, this.zzq, false);
        SafeParcelWriter.writeBoolean(parcel, 18, isPlayingAd());
        SafeParcelWriter.writeParcelable(parcel, 19, getAdBreakStatus(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 20, getVideoInfo(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 21, getLiveSeekableRange(), i, false);
        SafeParcelWriter.writeParcelable(parcel, 22, getQueueData(), i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public MediaStatus(JSONObject jSONObject) throws JSONException {
        this(null, 0L, 0, 0.0d, 0, 0, 0L, 0L, 0.0d, false, null, 0, 0, null, 0, null, false, null, null, null, null);
        zza(jSONObject, 0);
    }
}
