package org.catrobat.catroid.pocketmusic.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.TactViewHolder;
import org.catrobat.catroid.pocketmusic.fastscroller.SectionTitleProvider;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.Track;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackGrid;
import org.catrobat.catroid.pocketmusic.note.trackgrid.TrackToTrackGridConverter;
import org.slf4j.Marker;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/TactScrollRecyclerView.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TactScrollRecyclerView extends RecyclerView {
    private static final int EMPTY_END_VIEW_TYPE = 102;
    private static final int PLUS_VIEW_TYPE = 101;
    private static final int TACTS_PER_SCREEN = 2;
    private static final int TACT_VIEW_TYPE = 100;
    private AnimatorUpdateCallback animatorUpdateCallback;
    private boolean isPlaying;
    private int tactCount;
    private TactSnapper tactSnapper;
    private ViewGroup.LayoutParams tactViewParams;
    private TrackGrid trackGrid;

    public TactScrollRecyclerView(Context context) {
        this(context, null);
    }

    public TactScrollRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TactScrollRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.tactViewParams = new ViewGroup.LayoutParams(0, 0);
        setLayoutManager(new LinearLayoutManager(context, 0, false));
        setAdapter(new TactAdapter());
        setHorizontalScrollBarEnabled(true);
        setScrollBarStyle(33554432);
        this.trackGrid = new TrackGrid(MusicalKey.VIOLIN, MusicalInstrument.ACCORDION, MusicalBeat.BEAT_4_4, new ArrayList());
        TactSnapper tactSnapper = new TactSnapper();
        this.tactSnapper = tactSnapper;
        addOnScrollListener(tactSnapper);
    }

    public void setUpdateAnimatorCallback(AnimatorUpdateCallback callback) {
        this.animatorUpdateCallback = callback;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return (e.getActionMasked() != 8 && this.isPlaying) || super.onInterceptTouchEvent(e);
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent e) {
        if (isPlaying()) {
            return false;
        }
        this.tactSnapper.setScrollStartedByUser(true);
        return super.onTouchEvent(e);
    }

    public void setTrack(Track track, int beatsPerMinute) {
        TrackGrid trackGridConvertTrackToTrackGrid = TrackToTrackGridConverter.convertTrackToTrackGrid(track, MusicalBeat.BEAT_4_4, beatsPerMinute);
        this.trackGrid = trackGridConvertTrackToTrackGrid;
        this.tactCount = Math.max(trackGridConvertTrackToTrackGrid.getTactCount(), 2);
    }

    public TrackGrid getTrackGrid() {
        return this.trackGrid;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
        getAdapter().notifyDataSetChanged();
    }

    public int getTactCount() {
        return this.tactCount;
    }

    public int getNotesPerScreen() {
        return this.trackGrid.getBeat().getTopNumber() * 2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (View.MeasureSpec.getMode(widthSpec) != 0 && View.MeasureSpec.getMode(heightSpec) != 0) {
            this.tactViewParams.width = View.MeasureSpec.getSize(widthSpec) / 2;
            this.tactViewParams.height = View.MeasureSpec.getSize(heightSpec);
            this.animatorUpdateCallback.updateCallback(this.tactCount, getMeasuredWidth());
        }
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).getLayoutParams().width = this.tactViewParams.width;
            getChildAt(i).getLayoutParams().height = this.tactViewParams.height;
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    public int getTactViewWidth() {
        return this.tactViewParams.width;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/TactScrollRecyclerView$TactAdapter.class
      classes14.dex
     */
    private class TactAdapter extends RecyclerView.Adapter<TactViewHolder> implements SectionTitleProvider {
        private static final int PLUS_BUTTON_ON_END = 1;
        private final View.OnClickListener addTactClickListener;

        private TactAdapter() {
            this.addTactClickListener = new View.OnClickListener() { // from class: org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView.TactAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v) {
                    TactScrollRecyclerView.this.tactCount++;
                    TactAdapter.this.notifyDataSetChanged();
                }
            };
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public TactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View viewInflate;
            if (viewType == 101) {
                viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.pocketmusic_add_tact_button, parent, false);
                viewInflate.setLayoutParams(TactScrollRecyclerView.this.tactViewParams);
                viewInflate.setOnClickListener(this.addTactClickListener);
            } else if (viewType == 102) {
                viewInflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.pocketmusic_empty_view, parent, false);
                viewInflate.setLayoutParams(TactScrollRecyclerView.this.tactViewParams);
            } else {
                viewInflate = new TrackView(TactScrollRecyclerView.this.getContext(), TactScrollRecyclerView.this.trackGrid);
                viewInflate.setLayoutParams(TactScrollRecyclerView.this.tactViewParams);
                TactScrollRecyclerView.this.animatorUpdateCallback.updateCallback(TactScrollRecyclerView.this.tactCount, TactScrollRecyclerView.this.getMeasuredWidth());
            }
            TactViewHolder tactViewHolder = new TactViewHolder(viewInflate);
            tactViewHolder.setIsRecyclable(false);
            return tactViewHolder;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(TactViewHolder holder, int position) {
            if (getItemViewType(position) == 100) {
                ((TrackView) holder.itemView).updateDataForTactPosition(position);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int position) {
            if (TactScrollRecyclerView.this.isPlaying) {
                return position >= getItemCount() + (-2) ? 102 : 100;
            }
            return position == getItemCount() + (-1) ? 101 : 100;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (TactScrollRecyclerView.this.isPlaying) {
                return Math.max(TactScrollRecyclerView.this.tactCount, 2) + 2;
            }
            return Math.max(TactScrollRecyclerView.this.tactCount, 2) + 1;
        }

        @Override // org.catrobat.catroid.pocketmusic.fastscroller.SectionTitleProvider
        public String getSectionTitle(int position) {
            if (getItemCount() - 1 == position) {
                return Marker.ANY_NON_NULL_MARKER;
            }
            return Integer.toString(position + 1);
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ui/TactScrollRecyclerView$TactSnapper.class
      classes14.dex
     */
    class TactSnapper extends RecyclerView.OnScrollListener {
        private boolean scrollStartedByUser = false;

        TactSnapper() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            TactViewHolder viewHolderAtScreenCenter;
            if (newState == 0 && this.scrollStartedByUser) {
                if (recyclerView.getChildCount() != 0 && (viewHolderAtScreenCenter = getViewHolderAtScreenCenter()) != null) {
                    recyclerView.smoothScrollBy((int) viewHolderAtScreenCenter.itemView.getX(), 0);
                }
                this.scrollStartedByUser = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        }

        public void setScrollStartedByUser(boolean scrollStartedByUser) {
            this.scrollStartedByUser = scrollStartedByUser;
        }

        TactViewHolder getViewHolderAtScreenCenter() {
            try {
                return (TactViewHolder) TactScrollRecyclerView.this.getChildViewHolder(TactScrollRecyclerView.this.findChildViewUnder(TactScrollRecyclerView.this.getWidth() / 4, TactScrollRecyclerView.this.getHeight() / 2));
            } catch (NullPointerException unused) {
                Log.w(getClass().getSimpleName(), "Warning: Tact not found for centering");
                return null;
            }
        }
    }
}
