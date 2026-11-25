package org.catrobat.catroid.pocketmusic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.pocketmusic.note.NoteLength;
import org.catrobat.catroid.pocketmusic.ui.AnimatorUpdateCallback;
import org.catrobat.catroid.pocketmusic.ui.PianoView;
import org.catrobat.catroid.pocketmusic.ui.TactScrollRecyclerView;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/pocketmusic/ScrollController.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ScrollController {
    private final int beatsPerMinute;
    private int oldScrollPosition = 0;
    private final PianoView pianoView;
    private final ImageButton playButton;
    private final View playLine;
    private ObjectAnimator scrollingAnimator;
    private final TactScrollRecyclerView scrollingView;

    public ScrollController(ViewGroup pocketmusicMainLayout, TactScrollRecyclerView tactScrollRecyclerView, int beatsPerMinute) {
        this.beatsPerMinute = beatsPerMinute;
        this.scrollingView = tactScrollRecyclerView;
        this.playLine = pocketmusicMainLayout.findViewById(R.id.pocketmusic_play_line);
        this.playButton = (ImageButton) pocketmusicMainLayout.findViewById(R.id.pocketmusic_play_button);
        this.pianoView = (PianoView) pocketmusicMainLayout.findViewById(R.id.musicdroid_piano);
        init();
    }

    private void init() {
        initializePlayLine();
        initializeAnimator();
        this.scrollingView.setUpdateAnimatorCallback(new AnimatorUpdateCallback() { // from class: org.catrobat.catroid.pocketmusic.ScrollController.1
            @Override // org.catrobat.catroid.pocketmusic.ui.AnimatorUpdateCallback
            public void updateCallback(int tactCount, int measuredWidth) {
                long milliseconds = NoteLength.QUARTER.toMilliseconds(ScrollController.this.beatsPerMinute);
                int i = tactCount * 4;
                ScrollController.this.scrollingAnimator.setIntValues((measuredWidth / ScrollController.this.scrollingView.getNotesPerScreen()) * i);
                ScrollController.this.scrollingAnimator.setDuration(milliseconds * ((long) i));
            }
        });
    }

    private void setGlobalPlayPosition(int xPosition) {
        this.scrollingView.scrollBy(xPosition - this.oldScrollPosition, 0);
        this.oldScrollPosition = xPosition;
    }

    public void initializeAnimator() {
        ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, "globalPlayPosition", 0);
        this.scrollingAnimator = objectAnimatorOfInt;
        objectAnimatorOfInt.setInterpolator(new LinearInterpolator());
        this.scrollingAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.catrobat.catroid.pocketmusic.ScrollController.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                ScrollController.this.playButton.setImageResource(R.drawable.ic_stop);
                ScrollController.this.playLine.setVisibility(0);
                ScrollController.this.scrollingView.setPlaying(true);
                ScrollController.this.scrollingView.smoothScrollToPosition(0);
                ScrollController.this.scrollingView.getTrackGrid().startPlayback(ScrollController.this.pianoView);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                ScrollController.this.playButton.setImageResource(R.drawable.ic_play);
                ScrollController.this.playLine.setVisibility(8);
                ScrollController.this.scrollingView.setPlaying(false);
                ScrollController.this.scrollingView.getTrackGrid().stopPlayback(ScrollController.this.pianoView);
                ScrollController.this.scrollingView.smoothScrollToPosition(0);
            }
        });
    }

    private void initializePlayLine() {
        this.scrollingView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: org.catrobat.catroid.pocketmusic.ScrollController.3
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ScrollController.this.initializeAnimator();
                ScrollController.this.scrollingView.removeOnLayoutChangeListener(this);
            }
        });
        this.playButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.pocketmusic.ScrollController.4
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (ScrollController.this.scrollingAnimator.isRunning()) {
                    ScrollController.this.scrollingAnimator.end();
                    ScrollController.this.scrollingAnimator.setupStartValues();
                } else {
                    ScrollController.this.scrollingAnimator.start();
                }
            }
        });
    }
}
