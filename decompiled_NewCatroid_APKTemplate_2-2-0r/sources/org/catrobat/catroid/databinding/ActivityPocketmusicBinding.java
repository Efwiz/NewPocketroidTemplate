package org.catrobat.catroid.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.fastscroller.FastScroller;
import org.catrobat.catroid.pocketmusic.ui.PianoView;
import org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/databinding/ActivityPocketmusicBinding.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class ActivityPocketmusicBinding implements ViewBinding {
    public final FastScroller fastscroll;
    public final PianoView musicdroidPiano;
    public final ImageButton pocketmusicPlayButton;
    public final View pocketmusicPlayLine;
    private final LinearLayout rootView;
    public final TactScrollRecyclerView tactScroller;

    private ActivityPocketmusicBinding(LinearLayout rootView, FastScroller fastscroll, PianoView musicdroidPiano, ImageButton pocketmusicPlayButton, View pocketmusicPlayLine, TactScrollRecyclerView tactScroller) {
        this.rootView = rootView;
        this.fastscroll = fastscroll;
        this.musicdroidPiano = musicdroidPiano;
        this.pocketmusicPlayButton = pocketmusicPlayButton;
        this.pocketmusicPlayLine = pocketmusicPlayLine;
        this.tactScroller = tactScroller;
    }

    @Override // androidx.viewbinding.ViewBinding
    public LinearLayout getRoot() {
        return this.rootView;
    }

    public static ActivityPocketmusicBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, null, false);
    }

    public static ActivityPocketmusicBinding inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent) {
        View viewInflate = inflater.inflate(R.layout.activity_pocketmusic, parent, false);
        if (attachToParent) {
            parent.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    public static ActivityPocketmusicBinding bind(View rootView) {
        int i = R.id.fastscroll;
        FastScroller fastScroller = (FastScroller) ViewBindings.findChildViewById(rootView, R.id.fastscroll);
        if (fastScroller != null) {
            i = R.id.musicdroid_piano;
            PianoView pianoView = (PianoView) ViewBindings.findChildViewById(rootView, R.id.musicdroid_piano);
            if (pianoView != null) {
                i = R.id.pocketmusic_play_button;
                ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(rootView, R.id.pocketmusic_play_button);
                if (imageButton != null) {
                    i = R.id.pocketmusic_play_line;
                    View viewFindChildViewById = ViewBindings.findChildViewById(rootView, R.id.pocketmusic_play_line);
                    if (viewFindChildViewById != null) {
                        i = R.id.tact_scroller;
                        TactScrollRecyclerView tactScrollRecyclerView = (TactScrollRecyclerView) ViewBindings.findChildViewById(rootView, R.id.tact_scroller);
                        if (tactScrollRecyclerView != null) {
                            return new ActivityPocketmusicBinding((LinearLayout) rootView, fastScroller, pianoView, imageButton, viewFindChildViewById, tactScrollRecyclerView);
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(rootView.getResources().getResourceName(i)));
    }
}
