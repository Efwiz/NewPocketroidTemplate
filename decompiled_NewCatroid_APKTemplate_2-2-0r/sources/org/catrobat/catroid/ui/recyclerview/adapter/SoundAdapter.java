package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.common.SoundInfo;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;
import org.catrobat.catroid.utils.FileMetaDataExtractor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/SoundAdapter.class
  classes14.dex
 */
/* JADX INFO: compiled from: SoundAdapter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0017\u0012\u0010\u0010\u0003\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0002H\u0002J\u001a\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\bH\u0016J\u0012\u0010\u0013\u001a\u00020\u000f2\b\u0010\r\u001a\u0004\u0018\u00010\u0002H\u0002J$\u0010\u0014\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0002H\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/adapter/SoundAdapter;", "Lorg/catrobat/catroid/ui/recyclerview/adapter/ExtendedRVAdapter;", "Lorg/catrobat/catroid/common/SoundInfo;", FirebaseAnalytics.Param.ITEMS, "", "(Ljava/util/List;)V", "currentPlaying", "currentPlayingPosition", "", "mediaPlayer", "Landroid/media/MediaPlayer;", "getSoundDuration", "", "sound", "onBindViewHolder", "", "holder", "Lorg/catrobat/catroid/ui/recyclerview/viewholder/ExtendedViewHolder;", "position", "playSound", "setAndPlaySound", "item", "stopSound", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SoundAdapter extends ExtendedRVAdapter<SoundInfo> {
    private SoundInfo currentPlaying;
    private int currentPlayingPosition;
    private MediaPlayer mediaPlayer;

    public SoundAdapter(List<? extends SoundInfo> list) {
        super(list);
        this.mediaPlayer = new MediaPlayer();
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(final ExtendedViewHolder holder, final int position) {
        View view;
        ImageView imageView;
        ImageView imageView2;
        final SoundInfo soundInfo = (SoundInfo) this.items.get(position);
        Context context = null;
        TextView textView = holder != null ? holder.title : null;
        if (textView != null) {
            textView.setText(soundInfo != null ? soundInfo.f517name : null);
        }
        if (holder != null && (imageView2 = holder.image) != null) {
            imageView2.setImageResource(R.drawable.ic_media_play_dark);
        }
        if (holder != null && (imageView = holder.image) != null) {
            imageView.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.SoundAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    SoundAdapter.onBindViewHolder$lambda$0(this.f$0, holder, soundInfo, position, view2);
                }
            });
        }
        if (holder != null && (view = holder.itemView) != null) {
            context = view.getContext();
        }
        if (context == null || soundInfo == null) {
            return;
        }
        if (this.showDetails) {
            TextView textView2 = holder.details;
            if (textView2 == null) {
                return;
            }
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Locale locale = Locale.getDefault();
            String string = context.getString(R.string.sound_details);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            String str = String.format(locale, string, Arrays.copyOf(new Object[]{getSoundDuration(soundInfo), FileMetaDataExtractor.getSizeAsString(soundInfo.getFile(), context)}, 2));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            textView2.setText(str);
            return;
        }
        TextView textView3 = holder.details;
        if (textView3 == null) {
            return;
        }
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        Locale locale2 = Locale.getDefault();
        String string2 = context.getString(R.string.sound_duration);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        String str2 = String.format(locale2, string2, Arrays.copyOf(new Object[]{getSoundDuration(soundInfo)}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        textView3.setText(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onBindViewHolder$lambda$0(SoundAdapter this$0, ExtendedViewHolder extendedViewHolder, SoundInfo soundInfo, int i, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.mediaPlayer.isPlaying()) {
            extendedViewHolder.image.setImageResource(R.drawable.ic_media_play_dark);
            this$0.stopSound();
            if (Intrinsics.areEqual(soundInfo, this$0.currentPlaying)) {
                return;
            }
            this$0.notifyItemChanged(this$0.currentPlayingPosition);
            this$0.setAndPlaySound(extendedViewHolder, i, soundInfo);
            return;
        }
        this$0.setAndPlaySound(extendedViewHolder, i, soundInfo);
    }

    private final void setAndPlaySound(final ExtendedViewHolder holder, int position, SoundInfo item) {
        ImageView imageView;
        if (holder != null && (imageView = holder.image) != null) {
            imageView.setImageResource(R.drawable.ic_media_pause_dark);
        }
        playSound(item);
        this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: org.catrobat.catroid.ui.recyclerview.adapter.SoundAdapter$$ExternalSyntheticLambda1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer) {
                SoundAdapter.setAndPlaySound$lambda$1(holder, mediaPlayer);
            }
        });
        this.currentPlaying = item;
        this.currentPlayingPosition = position;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setAndPlaySound$lambda$1(ExtendedViewHolder extendedViewHolder, MediaPlayer mediaPlayer) {
        ImageView imageView;
        if (extendedViewHolder == null || (imageView = extendedViewHolder.image) == null) {
            return;
        }
        imageView.setImageResource(R.drawable.ic_media_play_dark);
    }

    private final String getSoundDuration(SoundInfo sound) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        File file = sound.getFile();
        mediaMetadataRetriever.setDataSource(file != null ? file.getAbsolutePath() : null);
        String strExtractMetadata = mediaMetadataRetriever.extractMetadata(9);
        long j = (strExtractMetadata != null ? Long.parseLong(strExtractMetadata) : 0L) / ((long) 1000);
        if (j == 0) {
            j = 1;
        }
        String elapsedTime = DateUtils.formatElapsedTime(j);
        Intrinsics.checkNotNullExpressionValue(elapsedTime, "formatElapsedTime(...)");
        return elapsedTime;
    }

    private final void playSound(SoundInfo sound) {
        File file;
        try {
            this.mediaPlayer.release();
            MediaPlayer mediaPlayer = new MediaPlayer();
            this.mediaPlayer = mediaPlayer;
            mediaPlayer.setDataSource((sound == null || (file = sound.getFile()) == null) ? null : file.getAbsolutePath());
            this.mediaPlayer.prepare();
            this.mediaPlayer.start();
        } catch (IOException e) {
            Log.e("[ERROR]", Log.getStackTraceString(e));
        }
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void stopSound() {
        this.mediaPlayer.stop();
    }
}
