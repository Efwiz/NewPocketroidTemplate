package org.catrobat.catroid.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import io.netty.handler.traffic.AbstractTrafficShapingHandler;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.RangesKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/SansMinigameManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: SansMinigameManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002JF\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0002Jj\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u00192\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u0014H\u0002J\u0018\u0010&\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010'\u001a\u00020\u0019H\u0007JR\u0010(\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020!2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u00192\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010)\u001a\u00020*2\u0006\u0010%\u001a\u00020\u0014H\u0002J2\u0010+\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\"\u001a\u00020\u00192\b\u0010#\u001a\u0004\u0018\u00010$H\u0002J*\u0010,\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\"\u001a\u00020\u00192\b\u0010#\u001a\u0004\u0018\u00010$H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/catrobat/catroid/ui/SansMinigameManager;", "", "()V", "hp", "", "isGameOver", "", "isInvulnerable", "isPlaying", "()Z", "setPlaying", "(Z)V", "maxHp", "spawnBone", "", "activity", "Landroid/app/Activity;", "overlay", "Landroid/widget/FrameLayout;", "ax", "", "ay", "size", "projs", "", "Landroid/view/View;", "spawnGaster", "sans", "Landroid/widget/ImageView;", "startGameLoop", "heart", "bar", "txt", "Landroid/widget/TextView;", "orig", "mp", "Landroid/media/MediaPlayer;", "hpMaxW", "startSans", "originalView", "takeDamage", "loop", "Landroid/animation/ValueAnimator;", "triggerDeath", "triggerWin", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SansMinigameManager {
    private static boolean isGameOver;
    private static boolean isInvulnerable;
    private static boolean isPlaying;
    public static final SansMinigameManager INSTANCE = new SansMinigameManager();
    private static int hp = 150;
    private static final int maxHp = 150;

    private SansMinigameManager() {
    }

    public final boolean isPlaying() {
        return isPlaying;
    }

    public final void setPlaying(boolean z) {
        isPlaying = z;
    }

    public final void startSans(final Activity activity, final View originalView) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(originalView, "originalView");
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        hp = maxHp;
        isInvulnerable = false;
        isGameOver = false;
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        final FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        frameLayout.setClickable(true);
        frameLayout.setFocusable(true);
        ((ViewGroup) decorView).addView(frameLayout, -1, -1);
        originalView.setVisibility(4);
        final MediaPlayer mediaPlayer = null;
        try {
            MediaPlayer mediaPlayerCreate = MediaPlayer.create(activity, R.raw.megalovania);
            if (mediaPlayerCreate != null) {
                mediaPlayerCreate.setLooping(true);
                mediaPlayerCreate.start();
                mediaPlayer = mediaPlayerCreate;
            }
        } catch (Exception unused) {
        }
        frameLayout.post(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.startSans$lambda$15(frameLayout, activity, originalView, mediaPlayer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startSans$lambda$15(FrameLayout overlay, Activity activity, View originalView, MediaPlayer mediaPlayer) {
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(originalView, "$originalView");
        float width = overlay.getWidth();
        float height = overlay.getHeight();
        final float fMin = Math.min(width, height) * 0.42f;
        int i = (int) (0.85f * fMin);
        Activity activity2 = activity;
        final ImageView imageView = new ImageView(activity2);
        imageView.setImageResource(R.drawable.ut_sans);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i);
        layoutParams.gravity = 1;
        float f = fMin / 2.0f;
        final float f2 = (height / 2.0f) - f;
        layoutParams.topMargin = RangesKt.coerceAtLeast((int) ((f2 - i) - 40.0f), 10);
        overlay.addView(imageView, layoutParams);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(800L);
        valueAnimatorOfFloat.setRepeatMode(2);
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda11
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SansMinigameManager.startSans$lambda$15$lambda$5$lambda$4(imageView, valueAnimator);
            }
        });
        valueAnimatorOfFloat.start();
        final float f3 = (width - fMin) / 2.0f;
        FrameLayout frameLayout = new FrameLayout(activity2);
        frameLayout.setBackgroundColor(-1);
        frameLayout.setX(f3 - 8.0f);
        frameLayout.setY(f2 - 8.0f);
        FrameLayout frameLayout2 = new FrameLayout(activity2);
        frameLayout2.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        frameLayout2.setX(f3);
        frameLayout2.setY(f2);
        int i2 = (int) (16.0f + fMin);
        overlay.addView(frameLayout, i2, i2);
        int i3 = (int) fMin;
        overlay.addView(frameLayout2, i3, i3);
        float f4 = f2 + fMin + 25.0f;
        TextView textView = new TextView(activity2);
        textView.setText("CHARA  LV 19");
        textView.setTextColor(-1);
        textView.setTypeface(Typeface.MONOSPACE);
        textView.setTextSize(14.0f);
        textView.setX(f3);
        textView.setY(f4);
        overlay.addView(textView, -2, -2);
        View view = new View(activity2);
        view.setBackgroundColor(-65536);
        view.setX(textView.getX() + 130.0f);
        view.setY(f4);
        View view2 = new View(activity2);
        view2.setBackgroundColor(InputDeviceCompat.SOURCE_ANY);
        view2.setX(view.getX());
        view2.setY(f4);
        int i4 = (int) 180.0f;
        overlay.addView(view, i4, 25);
        overlay.addView(view2, i4, 25);
        TextView textView2 = new TextView(activity2);
        textView2.setText(hp + " / " + maxHp);
        textView2.setTextColor(-1);
        textView2.setTypeface(Typeface.MONOSPACE);
        textView2.setTextSize(14.0f);
        textView2.setX(view.getX() + 180.0f + 15.0f);
        textView2.setY(f4);
        overlay.addView(textView2, -2, -2);
        ImageView imageView2 = new ImageView(activity2);
        imageView2.setImageResource(R.drawable.ut_ui_buttons);
        imageView2.setX(f3 - 10.0f);
        imageView2.setY(f4 + 50.0f);
        overlay.addView(imageView2, (int) (20.0f + fMin), 80);
        ImageView imageView3 = new ImageView(activity2);
        imageView3.setImageResource(R.drawable.ut_heart);
        imageView3.setX((width / 2.0f) - 17.5f);
        imageView3.setY((f + f2) - 17.5f);
        final float f5 = 35.0f;
        int i5 = (int) 35.0f;
        overlay.addView(imageView3, i5, i5);
        imageView3.setOnTouchListener(new View.OnTouchListener() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda12
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                return SansMinigameManager.startSans$lambda$15$lambda$14(f5, f3, fMin, f2, view3, motionEvent);
            }
        });
        INSTANCE.startGameLoop(activity, overlay, imageView3, view2, textView2, imageView, originalView, mediaPlayer, f3, f2, fMin, 180.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startSans$lambda$15$lambda$5$lambda$4(ImageView sans, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(sans, "$sans");
        Intrinsics.checkNotNullParameter(it, "it");
        sans.setTranslationX(((float) Math.sin(it.getAnimatedFraction() * 3.1415927f)) * 20.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean startSans$lambda$15$lambda$14(float f, float f2, float f3, float f4, View view, MotionEvent motionEvent) {
        if (isGameOver) {
            return true;
        }
        if (motionEvent.getAction() != 2 && motionEvent.getAction() != 0) {
            return true;
        }
        float f5 = f / 2;
        view.setX(RangesKt.coerceIn(motionEvent.getRawX() - f5, f2, (f2 + f3) - f));
        view.setY(RangesKt.coerceIn(motionEvent.getRawY() - f5, f4, (f3 + f4) - f));
        return true;
    }

    private final void startGameLoop(final Activity activity, final FrameLayout overlay, final ImageView heart, final View bar, final TextView txt, final ImageView sans, final View orig, final MediaPlayer mp, final float ax, final float ay, final float size, final float hpMaxW) {
        final ArrayList arrayList = new ArrayList();
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final Ref.LongRef longRef = new Ref.LongRef();
        final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(100000L);
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SansMinigameManager.startGameLoop$lambda$17(jCurrentTimeMillis, activity, overlay, orig, mp, longRef, ax, ay, size, arrayList, sans, heart, bar, txt, valueAnimatorOfFloat, hpMaxW, valueAnimator);
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startGameLoop$lambda$17(long j, Activity activity, FrameLayout overlay, View orig, MediaPlayer mediaPlayer, Ref.LongRef lastSpawn, float f, float f2, float f3, List projs, ImageView sans, ImageView heart, View bar, TextView txt, ValueAnimator valueAnimator, float f4, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(orig, "$orig");
        Intrinsics.checkNotNullParameter(lastSpawn, "$lastSpawn");
        Intrinsics.checkNotNullParameter(projs, "$projs");
        Intrinsics.checkNotNullParameter(sans, "$sans");
        Intrinsics.checkNotNullParameter(heart, "$heart");
        Intrinsics.checkNotNullParameter(bar, "$bar");
        Intrinsics.checkNotNullParameter(txt, "$txt");
        Intrinsics.checkNotNullParameter(it, "it");
        if (isGameOver) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - j;
        if (jCurrentTimeMillis > 30000) {
            SansMinigameManager sansMinigameManager = INSTANCE;
            isGameOver = true;
            sansMinigameManager.triggerWin(activity, overlay, orig, mediaPlayer);
            return;
        }
        if (System.currentTimeMillis() - lastSpawn.element > (jCurrentTimeMillis > AbstractTrafficShapingHandler.DEFAULT_MAX_TIME ? 140L : 300L)) {
            lastSpawn.element = System.currentTimeMillis();
            if (Math.random() < 0.75d) {
                INSTANCE.spawnBone(activity, overlay, f, f2, f3, projs);
            } else {
                INSTANCE.spawnGaster(activity, overlay, sans, f, f2, f3, projs);
            }
        }
        RectF rectF = new RectF(heart.getX() + 8.0f, heart.getY() + 8.0f, (heart.getX() + heart.getWidth()) - 8.0f, (heart.getY() + heart.getHeight()) - 8.0f);
        Iterator it2 = projs.iterator();
        while (it2.hasNext()) {
            View view = (View) it2.next();
            if (Intrinsics.areEqual(view.getTag(), "bone")) {
                view.setY(view.getY() - 18.0f);
            }
            if (view.getY() < f2 - 100 || Intrinsics.areEqual(view.getTag(), "expired")) {
                overlay.removeView(view);
                it2.remove();
                rectF = rectF;
            } else if (RectF.intersects(rectF, new RectF(view.getX(), view.getY(), view.getX() + view.getWidth(), view.getY() + view.getHeight()))) {
                SansMinigameManager sansMinigameManager2 = INSTANCE;
                Intrinsics.checkNotNull(valueAnimator);
                sansMinigameManager2.takeDamage(activity, heart, bar, txt, overlay, orig, mediaPlayer, valueAnimator, f4);
                rectF = rectF;
            }
        }
    }

    private final void spawnBone(Activity activity, FrameLayout overlay, float ax, float ay, float size, List<View> projs) {
        ImageView imageView = new ImageView(activity);
        imageView.setImageResource(R.drawable.ut_bone);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setX(ax + ((float) (Math.random() * ((double) (size - 25.0f)))));
        imageView.setY(ay + size);
        imageView.setTag("bone");
        overlay.addView(imageView, 25, (int) (((double) 70) + (Math.random() * ((double) 130))));
        projs.add(imageView);
    }

    private final void spawnGaster(final Activity activity, final FrameLayout overlay, final ImageView sans, final float ax, float ay, final float size, final List<View> projs) {
        final float fRandom = ay + ((float) (Math.random() * ((double) (size - 60.0f))));
        sans.setColorFilter(Color.parseColor("#42f5f1"), PorterDuff.Mode.MULTIPLY);
        final ImageView imageView = new ImageView(activity);
        imageView.setImageResource(R.drawable.ut_blaster);
        imageView.setX(ax - 160.0f);
        imageView.setY(fRandom - 40.0f);
        imageView.setScaleX(0.0f);
        imageView.setScaleY(0.0f);
        overlay.addView(imageView, 100, 100);
        imageView.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200L).start();
        overlay.postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.spawnGaster$lambda$23(sans, activity, overlay, size, projs, ax, fRandom, imageView);
            }
        }, 400L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void spawnGaster$lambda$23(ImageView sans, Activity activity, final FrameLayout overlay, float f, List projs, float f2, float f3, final ImageView blaster) {
        Intrinsics.checkNotNullParameter(sans, "$sans");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(projs, "$projs");
        Intrinsics.checkNotNullParameter(blaster, "$blaster");
        sans.clearColorFilter();
        final View view = new View(activity);
        view.setBackgroundColor(-1);
        view.setX(f2);
        view.setY(f3);
        view.setTag("beam");
        overlay.addView(view, (int) f, 55);
        projs.add(view);
        overlay.postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.spawnGaster$lambda$23$lambda$22(overlay, view, blaster);
            }
        }, 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void spawnGaster$lambda$23$lambda$22(final FrameLayout overlay, View beam, final ImageView blaster) {
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(beam, "$beam");
        Intrinsics.checkNotNullParameter(blaster, "$blaster");
        overlay.removeView(beam);
        beam.setTag("expired");
        blaster.animate().alpha(0.0f).scaleX(0.0f).setDuration(150L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.spawnGaster$lambda$23$lambda$22$lambda$21(overlay, blaster);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void spawnGaster$lambda$23$lambda$22$lambda$21(FrameLayout overlay, ImageView blaster) {
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(blaster, "$blaster");
        overlay.removeView(blaster);
    }

    private final void takeDamage(Activity activity, final ImageView heart, View bar, TextView txt, FrameLayout overlay, View orig, MediaPlayer mp, ValueAnimator loop, float hpMaxW) {
        if (isInvulnerable || isGameOver) {
            return;
        }
        int iMax = Math.max(0, hp - 25);
        hp = iMax;
        int i = maxHp;
        txt.setText(iMax + " / " + i);
        bar.getLayoutParams().width = RangesKt.coerceAtLeast((int) (hpMaxW * (hp / i)), 1);
        bar.requestLayout();
        try {
            final MediaPlayer mediaPlayerCreate = MediaPlayer.create(activity, R.raw.snd_hurt);
            if (mediaPlayerCreate != null) {
                mediaPlayerCreate.start();
                mediaPlayerCreate.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda0
                    @Override // android.media.MediaPlayer.OnCompletionListener
                    public final void onCompletion(MediaPlayer mediaPlayer) {
                        SansMinigameManager.takeDamage$lambda$25$lambda$24(mediaPlayerCreate, mediaPlayer);
                    }
                });
            }
        } catch (Exception unused) {
        }
        if (hp <= 0) {
            isGameOver = true;
            loop.cancel();
            triggerDeath(activity, overlay, heart, orig, mp);
            return;
        }
        isInvulnerable = true;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setDuration(100L);
        valueAnimatorOfFloat.setRepeatCount(5);
        valueAnimatorOfFloat.setRepeatMode(2);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SansMinigameManager.takeDamage$lambda$27$lambda$26(heart, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.catrobat.catroid.ui.SansMinigameManager$takeDamage$anim$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator a) {
                Intrinsics.checkNotNullParameter(a, "a");
                heart.setAlpha(1.0f);
                SansMinigameManager sansMinigameManager = SansMinigameManager.INSTANCE;
                SansMinigameManager.isInvulnerable = false;
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void takeDamage$lambda$25$lambda$24(MediaPlayer this_apply, MediaPlayer mediaPlayer) {
        Intrinsics.checkNotNullParameter(this_apply, "$this_apply");
        this_apply.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void takeDamage$lambda$27$lambda$26(ImageView heart, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(heart, "$heart");
        Intrinsics.checkNotNullParameter(it, "it");
        Object animatedValue = it.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        heart.setAlpha(((Float) animatedValue).floatValue());
    }

    private final void triggerDeath(final Activity activity, final FrameLayout overlay, final ImageView heart, final View orig, MediaPlayer mp) {
        if (mp != null) {
            mp.stop();
        }
        if (mp != null) {
            mp.release();
        }
        heart.setImageResource(R.drawable.ut_heart_break);
        try {
            MediaPlayer mediaPlayerCreate = MediaPlayer.create(activity, R.raw.snd_break);
            if (mediaPlayerCreate != null) {
                mediaPlayerCreate.start();
            }
        } catch (Exception unused) {
        }
        overlay.postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.triggerDeath$lambda$33(heart, activity, overlay, orig);
            }
        }, 1200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerDeath$lambda$33(ImageView heart, final Activity activity, final FrameLayout overlay, final View orig) {
        Intrinsics.checkNotNullParameter(heart, "$heart");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(orig, "$orig");
        heart.setVisibility(4);
        for (int i = 0; i < 6; i++) {
            final ImageView imageView = new ImageView(activity);
            imageView.setImageResource(R.drawable.ut_heart_shard);
            imageView.setX(heart.getX() + 10.0f);
            imageView.setY(heart.getY() + 10.0f);
            overlay.addView(imageView, 15, 15);
            double d = 10;
            final float fRandom = (float) ((Math.random() * ((double) 20)) - d);
            final Ref.FloatRef floatRef = new Ref.FloatRef();
            floatRef.element = (float) ((Math.random() * ((double) (-30))) - d);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(2000L);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda13
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SansMinigameManager.triggerDeath$lambda$33$lambda$30$lambda$29(imageView, fRandom, floatRef, valueAnimator);
                }
            });
            valueAnimatorOfFloat.start();
        }
        overlay.postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.triggerDeath$lambda$33$lambda$32(overlay, activity, orig);
            }
        }, 1800L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerDeath$lambda$33$lambda$30$lambda$29(ImageView shard, float f, Ref.FloatRef vy, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(shard, "$shard");
        Intrinsics.checkNotNullParameter(vy, "$vy");
        Intrinsics.checkNotNullParameter(it, "it");
        shard.setX(shard.getX() + f);
        shard.setY(shard.getY() + vy.element);
        vy.element += 1.5f;
        shard.setRotation(shard.getRotation() + 15.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerDeath$lambda$33$lambda$32(final FrameLayout overlay, final Activity activity, final View orig) {
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(orig, "$orig");
        overlay.animate().alpha(0.0f).setDuration(1200L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.triggerDeath$lambda$33$lambda$32$lambda$31(activity, overlay, orig);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerDeath$lambda$33$lambda$32$lambda$31(Activity activity, FrameLayout overlay, View orig) {
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(orig, "$orig");
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        ((ViewGroup) decorView).removeView(overlay);
        orig.setVisibility(0);
        isPlaying = false;
    }

    private final void triggerWin(final Activity activity, final FrameLayout overlay, final View orig, MediaPlayer mp) {
        if (mp != null) {
            mp.stop();
        }
        if (mp != null) {
            mp.release();
        }
        View view = new View(activity);
        view.setBackgroundColor(-1);
        view.setAlpha(0.0f);
        overlay.addView(view, -1, -1);
        view.animate().alpha(1.0f).setDuration(600L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.SansMinigameManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SansMinigameManager.triggerWin$lambda$35(activity, overlay, orig);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerWin$lambda$35(Activity activity, FrameLayout overlay, View orig) {
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(orig, "$orig");
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        ((ViewGroup) decorView).removeView(overlay);
        orig.setVisibility(0);
        isPlaying = false;
        orig.performClick();
    }
}
