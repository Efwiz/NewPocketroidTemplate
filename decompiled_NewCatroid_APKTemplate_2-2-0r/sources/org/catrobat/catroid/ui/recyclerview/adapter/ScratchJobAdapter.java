package org.catrobat.catroid.ui.recyclerview.adapter;

import android.content.Context;
import com.beust.jcommander.Parameters;
import com.google.android.gms.common.images.WebImage;
import com.squareup.picasso.Picasso;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.ui.recyclerview.viewholder.ExtendedViewHolder;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/ScratchJobAdapter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScratchJobAdapter extends ExtendedRVAdapter<Job> {
    public ScratchJobAdapter(List<Job> items) {
        super(items);
    }

    @Override // org.catrobat.catroid.ui.recyclerview.adapter.ExtendedRVAdapter
    public void onBindViewHolder(ExtendedViewHolder holder, int position) {
        int i;
        Job job = (Job) this.items.get(position);
        Context context = holder.itemView.getContext();
        holder.title.setText(job.getTitle());
        if (job.getImage() != null && job.getImage().getUrl() != null) {
            Picasso.get().load(Utils.changeSizeOfScratchImageURL(job.getImage().getUrl().toString(), context.getResources().getDimensionPixelSize(R.dimen.scratch_project_thumbnail_height))).into(holder.image);
        } else {
            holder.image.setImageBitmap(null);
        }
        switch (AnonymousClass1.$SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[job.getState().ordinal()]) {
            case 1:
                holder.details.setText(Parameters.DEFAULT_OPTION_PREFIXES);
                break;
            case 2:
                holder.details.setText(context.getString(R.string.status_scheduled));
                break;
            case 3:
                holder.details.setText(context.getString(R.string.status_waiting_for_worker));
                break;
            case 4:
                holder.details.setText(context.getString(R.string.status_started));
                break;
            case 5:
                int i2 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$DownloadState[job.getDownloadState().ordinal()];
                if (i2 == 1) {
                    i = R.string.status_downloading;
                } else if (i2 != 2) {
                    i = i2 != 3 ? R.string.status_conversion_finished : R.string.status_download_canceled;
                } else {
                    i = R.string.status_download_finished;
                }
                holder.details.setText(context.getString(i));
                break;
            case 6:
                holder.details.setText(R.string.status_conversion_failed);
                holder.details.setTextColor(-65536);
                break;
        }
        WebImage image = job.getImage();
        if (image != null && image.getUrl() != null) {
            Picasso.get().load(Utils.changeSizeOfScratchImageURL(image.getUrl().toString(), context.getResources().getDimensionPixelSize(R.dimen.scratch_project_thumbnail_height))).into(holder.image);
        } else {
            holder.image.setImageBitmap(null);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/adapter/ScratchJobAdapter$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.recyclerview.adapter.ScratchJobAdapter$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$DownloadState;
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State;

        static {
            int[] iArr = new int[Job.State.values().length];
            $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State = iArr;
            try {
                iArr[Job.State.UNSCHEDULED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[Job.State.SCHEDULED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[Job.State.READY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[Job.State.RUNNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[Job.State.FINISHED.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$State[Job.State.FAILED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[Job.DownloadState.values().length];
            $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$DownloadState = iArr2;
            try {
                iArr2[Job.DownloadState.DOWNLOADING.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$DownloadState[Job.DownloadState.DOWNLOADED.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$scratchconverter$protocol$Job$DownloadState[Job.DownloadState.CANCELED.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }
}
