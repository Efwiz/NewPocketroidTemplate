package org.catrobat.catroid.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntRange;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/LimboMinigameManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: LimboMinigameManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002*+B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jj\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00042\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001c2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0014H\u0002J \u0010 \u001a\u0004\u0018\u00010\u00142\u0006\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u00182\u0006\u0010#\u001a\u00020\u0018J\u0016\u0010$\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010%\u001a\u00020\u0014J(\u0010&\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010'\u001a\u00020(2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002JB\u0010)\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00042\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\b\u0010\u001f\u001a\u0004\u0018\u00010\u0014H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\b\"\u0004\b\t\u0010\n¨\u0006,"}, d2 = {"Lorg/catrobat/catroid/ui/LimboMinigameManager;", "", "()V", "MOVES", "", "Lorg/catrobat/catroid/ui/LimboMinigameManager$Move;", "isPlaying", "", "()Z", "setPlaying", "(Z)V", "executeChoreography", "", "activity", "Landroid/app/Activity;", "overlay", "Landroid/widget/FrameLayout;", UserMetadata.KEYDATA_FILENAME, "Landroid/widget/ImageView;", "originalView", "Landroid/view/View;", "mediaPlayer", "Landroid/media/MediaPlayer;", "moveIndex", "", "currentSlots", "", "centerX", "", "centerY", "radius", "preFlash", "findClickableView", Promotion.ACTION_VIEW, "x", "y", "startLimbo", "targetView", "startLimboMadness", "rawBitmap", "Landroid/graphics/Bitmap;", "triggerFocusEndgame", "Move", "MoveType", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LimboMinigameManager {
    public static final LimboMinigameManager INSTANCE = new LimboMinigameManager();
    private static final List<Move> MOVES;
    private static boolean isPlaying;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/LimboMinigameManager$MoveType.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LimboMinigameManager.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b"}, d2 = {"Lorg/catrobat/catroid/ui/LimboMinigameManager$MoveType;", "", "(Ljava/lang/String;I)V", "ROTATE_CW", "ROTATE_CCW", "SWAP_OPPOSITE", "SWAP_ADJACENT", "CHAOS", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum MoveType {
        ROTATE_CW,
        ROTATE_CCW,
        SWAP_OPPOSITE,
        SWAP_ADJACENT,
        CHAOS;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<MoveType> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/LimboMinigameManager$WhenMappings.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LimboMinigameManager.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MoveType.values().length];
            try {
                iArr[MoveType.ROTATE_CW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[MoveType.ROTATE_CCW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private LimboMinigameManager() {
    }

    public final boolean isPlaying() {
        return isPlaying;
    }

    public final void setPlaying(boolean z) {
        isPlaying = z;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/LimboMinigameManager$Move.class
      classes14.dex
     */
    /* JADX INFO: compiled from: LimboMinigameManager.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/ui/LimboMinigameManager$Move;", "", ModuleXmlParser.TYPE, "Lorg/catrobat/catroid/ui/LimboMinigameManager$MoveType;", "durationMs", "", "steps", "", "shake", "", "isFinal", "(Lorg/catrobat/catroid/ui/LimboMinigameManager$MoveType;JIZZ)V", "getDurationMs", "()J", "()Z", "getShake", "getSteps", "()I", "getType", "()Lorg/catrobat/catroid/ui/LimboMinigameManager$MoveType;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Move {
        private final long durationMs;
        private final boolean isFinal;
        private final boolean shake;
        private final int steps;
        private final MoveType type;

        public Move(MoveType type, long j, int i, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(type, "type");
            this.type = type;
            this.durationMs = j;
            this.steps = i;
            this.shake = z;
            this.isFinal = z2;
        }

        public /* synthetic */ Move(MoveType moveType, long j, int i, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(moveType, j, (i2 & 4) != 0 ? 0 : i, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? false : z2);
        }

        public final long getDurationMs() {
            return this.durationMs;
        }

        public final boolean getShake() {
            return this.shake;
        }

        public final int getSteps() {
            return this.steps;
        }

        public final MoveType getType() {
            return this.type;
        }

        /* JADX INFO: renamed from: isFinal, reason: from getter */
        public final boolean getIsFinal() {
            return this.isFinal;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        int i = 28;
        DefaultConstructorMarker defaultConstructorMarker = null;
        long j = 300;
        boolean z = false;
        boolean z2 = false;
        int i2 = 28;
        int i3 = 0;
        long j2 = 250;
        int i4 = 28;
        int i5 = 0;
        int i6 = 28;
        int i7 = 0;
        int i8 = 28;
        int i9 = 0;
        int i10 = 28;
        long j3 = 200;
        int i11 = 0;
        int i12 = 28;
        int i13 = 0;
        int i14 = 28;
        int i15 = 0;
        MOVES = CollectionsKt.listOf((Object[]) new Move[]{new Move(MoveType.ROTATE_CW, 300L, 2, false, false, 24, null), new Move(MoveType.ROTATE_CCW, 300L, 3, false, false, 24, null), new Move(MoveType.SWAP_OPPOSITE, j, 0 == true ? 1 : 0, z, z2, i, defaultConstructorMarker), new Move(MoveType.SWAP_ADJACENT, j, 0 == true ? 1 : 0, z, z2, i, defaultConstructorMarker), new Move(MoveType.CHAOS, j, 0 == true ? 1 : 0, z, z2, i, defaultConstructorMarker), new Move(MoveType.ROTATE_CW, j, 1, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_OPPOSITE, j, 0, z, z2, 28, defaultConstructorMarker), new Move(MoveType.ROTATE_CCW, j, 2, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_ADJACENT, j, i3, z, z2, i2, defaultConstructorMarker), new Move(MoveType.CHAOS, j, i3, z, z2, i2, defaultConstructorMarker), new Move(MoveType.ROTATE_CW, j2, 3, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_OPPOSITE, j2, i5, z, z2, i4, defaultConstructorMarker), new Move(MoveType.CHAOS, j2, i5, z, z2, i4, defaultConstructorMarker), new Move(MoveType.ROTATE_CCW, j2, 2, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_ADJACENT, j2, i7, z, z2, i6, defaultConstructorMarker), new Move(MoveType.CHAOS, j2, i7, z, z2, i6, defaultConstructorMarker), new Move(MoveType.ROTATE_CW, j2, 2, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_OPPOSITE, j2, i9, z, z2, i8, defaultConstructorMarker), new Move(MoveType.SWAP_ADJACENT, j2, i9, z, z2, i8, defaultConstructorMarker), new Move(MoveType.ROTATE_CCW, j2, 1, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_OPPOSITE, j3, i11, z, z2, i10, defaultConstructorMarker), new Move(MoveType.CHAOS, j3, i11, z, z2, i10, defaultConstructorMarker), new Move(MoveType.ROTATE_CW, j3, 2, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_ADJACENT, j3, i13, z, z2, i12, defaultConstructorMarker), new Move(MoveType.CHAOS, j3, i13, z, z2, i12, defaultConstructorMarker), new Move(MoveType.ROTATE_CCW, j3, 3, z, z2, 24, defaultConstructorMarker), new Move(MoveType.SWAP_OPPOSITE, j3, i15, z, z2, i14, defaultConstructorMarker), new Move(MoveType.SWAP_ADJACENT, j3, i15, z, z2, i14, defaultConstructorMarker), new Move(MoveType.CHAOS, j3, i15, z, z2, i14, defaultConstructorMarker), new Move(MoveType.ROTATE_CW, j3, 4, z, z2, 24, defaultConstructorMarker), new Move(MoveType.ROTATE_CW, 2000L, 24, true, true)});
    }

    public final View findClickableView(View view, int x, int y) {
        int i;
        Intrinsics.checkNotNullParameter(view, "view");
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        if (x < i2 || x > i2 + view.getWidth() || y < (i = iArr[1]) || y > i + view.getHeight()) {
            return null;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; -1 < childCount; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt.getVisibility() == 0) {
                    Intrinsics.checkNotNull(childAt);
                    View viewFindClickableView = findClickableView(childAt, x, y);
                    if (viewFindClickableView != null) {
                        return viewFindClickableView;
                    }
                }
            }
        }
        if (view.isClickable()) {
            return view;
        }
        return null;
    }

    public final void startLimbo(Activity activity, View targetView) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(targetView, "targetView");
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(targetView.getWidth(), targetView.getHeight(), Bitmap.Config.ARGB_8888);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateBitmap, "createBitmap(...)");
        targetView.draw(new Canvas(bitmapCreateBitmap));
        int[] iArr = new int[2];
        targetView.getLocationOnScreen(iArr);
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        Activity activity2 = activity;
        FrameLayout frameLayout = new FrameLayout(activity2);
        frameLayout.setBackgroundColor(Color.parseColor("#95000000"));
        frameLayout.setClickable(true);
        frameLayout.setFocusable(true);
        ((ViewGroup) decorView).addView(frameLayout, -1, -1);
        targetView.setVisibility(4);
        ImageView imageView = new ImageView(activity2);
        imageView.setImageBitmap(bitmapCreateBitmap);
        imageView.setX(iArr[0]);
        imageView.setY(iArr[1]);
        frameLayout.addView(imageView, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 0.05f).setDuration(200L), ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 0.0f).setDuration(200L));
        animatorSet.addListener(new AnonymousClass1(frameLayout, imageView, activity, bitmapCreateBitmap, targetView));
        animatorSet.start();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/LimboMinigameManager$startLimbo$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.ui.LimboMinigameManager$startLimbo$1, reason: invalid class name */
    /* JADX INFO: compiled from: LimboMinigameManager.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"org/catrobat/catroid/ui/LimboMinigameManager$startLimbo$1", "Landroid/animation/AnimatorListenerAdapter;", "onAnimationEnd", "", "animation", "Landroid/animation/Animator;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 extends AnimatorListenerAdapter {
        final /* synthetic */ Activity $activity;
        final /* synthetic */ ImageView $fakeButton;
        final /* synthetic */ FrameLayout $overlay;
        final /* synthetic */ Bitmap $rawBitmap;
        final /* synthetic */ View $targetView;

        AnonymousClass1(FrameLayout frameLayout, ImageView imageView, Activity activity, Bitmap bitmap, View view) {
            this.$overlay = frameLayout;
            this.$fakeButton = imageView;
            this.$activity = activity;
            this.$rawBitmap = bitmap;
            this.$targetView = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animation) {
            Intrinsics.checkNotNullParameter(animation, "animation");
            this.$overlay.removeView(this.$fakeButton);
            final FrameLayout frameLayout = this.$overlay;
            final Activity activity = this.$activity;
            final Bitmap bitmap = this.$rawBitmap;
            final View view = this.$targetView;
            frameLayout.post(new Runnable() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$startLimbo$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LimboMinigameManager.AnonymousClass1.onAnimationEnd$lambda$0(activity, frameLayout, bitmap, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onAnimationEnd$lambda$0(Activity activity, FrameLayout overlay, Bitmap rawBitmap, View targetView) {
            Intrinsics.checkNotNullParameter(activity, "$activity");
            Intrinsics.checkNotNullParameter(overlay, "$overlay");
            Intrinsics.checkNotNullParameter(rawBitmap, "$rawBitmap");
            Intrinsics.checkNotNullParameter(targetView, "$targetView");
            LimboMinigameManager.INSTANCE.startLimboMadness(activity, overlay, rawBitmap, targetView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void startLimboMadness(final Activity activity, final FrameLayout overlay, Bitmap rawBitmap, final View originalView) {
        float width = overlay.getWidth();
        float height = overlay.getHeight();
        float fMin = Math.min(1.0f, (Math.min(width, height) * 0.18f) / Math.max(rawBitmap.getWidth(), rawBitmap.getHeight()));
        Bitmap bitmapCreateScaledBitmap = fMin < 1.0f ? Bitmap.createScaledBitmap(rawBitmap, Math.max(1, (int) (rawBitmap.getWidth() * fMin)), Math.max(1, (int) (rawBitmap.getHeight() * fMin)), true) : rawBitmap;
        Intrinsics.checkNotNull(bitmapCreateScaledBitmap);
        final ArrayList arrayList = new ArrayList();
        final float width2 = (width / 2.0f) - (bitmapCreateScaledBitmap.getWidth() / 2.0f);
        final float height2 = (height / 2.0f) - (bitmapCreateScaledBitmap.getHeight() / 2.0f);
        final float fMin2 = Math.min(width, height) * 0.32f;
        MediaPlayer mediaPlayer = null;
        try {
            MediaPlayer mediaPlayerCreate = MediaPlayer.create(activity, R.raw.limbo_music);
            if (mediaPlayerCreate != null) {
                mediaPlayerCreate.start();
                mediaPlayer = mediaPlayerCreate;
            }
        } catch (Exception unused) {
        }
        final MediaPlayer mediaPlayer2 = mediaPlayer;
        for (int i = 0; i < 8; i++) {
            double radians = Math.toRadians(((double) i) * 45.0d);
            ImageView imageView = new ImageView(activity);
            imageView.setImageBitmap(bitmapCreateScaledBitmap);
            imageView.setX((((float) Math.cos(radians)) * fMin2) + width2);
            imageView.setY((((float) Math.sin(radians)) * fMin2) + height2);
            imageView.setAlpha(0.0f);
            overlay.addView(imageView, bitmapCreateScaledBitmap.getWidth(), bitmapCreateScaledBitmap.getHeight());
            arrayList.add(imageView);
            imageView.animate().alpha(1.0f).setDuration(400L).start();
        }
        ((ImageView) arrayList.get(0)).setColorFilter(-16711936, PorterDuff.Mode.MULTIPLY);
        overlay.postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LimboMinigameManager.startLimboMadness$lambda$4(arrayList, activity, overlay, originalView, mediaPlayer2, width2, height2, fMin2);
            }
        }, 1500L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void startLimboMadness$lambda$4(List keys, Activity activity, FrameLayout overlay, View originalView, MediaPlayer mediaPlayer, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(keys, "$keys");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(originalView, "$originalView");
        ((ImageView) keys.get(0)).clearColorFilter();
        LimboMinigameManager limboMinigameManager = INSTANCE;
        int[] iArr = new int[8];
        for (int i = 0; i < 8; i++) {
            iArr[i] = i;
        }
        limboMinigameManager.executeChoreography(activity, overlay, keys, originalView, mediaPlayer, 0, iArr, f, f2, f3, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:42:0x014f A[LOOP:1: B:41:0x014d->B:42:0x014f, LOOP_END] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v30, types: [T, android.view.View] */
    public final void executeChoreography(final Activity activity, final FrameLayout overlay, final List<? extends ImageView> keys, final View originalView, final MediaPlayer mediaPlayer, final int moveIndex, final int[] currentSlots, final float centerX, final float centerY, final float radius, View preFlash) {
        int i;
        Ref.ObjectRef objectRef;
        int[] iArr;
        int i2;
        int i3;
        int i4;
        int i5;
        List<Move> list = MOVES;
        if (moveIndex >= list.size()) {
            overlay.setTranslationX(0.0f);
            overlay.setTranslationY(0.0f);
            triggerFocusEndgame(activity, overlay, keys, originalView, mediaPlayer, preFlash);
            return;
        }
        final Move move = list.get(moveIndex);
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        int[] iArr2 = new int[8];
        Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        objectRef2.element = preFlash;
        if (move.getShake()) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LimboMinigameManager.executeChoreography$lambda$6$lambda$5(overlay, valueAnimator);
                }
            });
            Intrinsics.checkNotNull(valueAnimatorOfFloat);
            arrayList.add(valueAnimatorOfFloat);
        }
        if (move.getIsFinal()) {
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            i = -1;
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LimboMinigameManager.executeChoreography$lambda$8$lambda$7(radius, move, currentSlots, keys, centerX, centerY, valueAnimator);
                }
            });
            Intrinsics.checkNotNull(valueAnimatorOfFloat2);
            arrayList.add(valueAnimatorOfFloat2);
            ?? view = new View(activity);
            view.setBackgroundColor(-1);
            view.setAlpha(0.0f);
            objectRef = objectRef2;
            objectRef.element = view;
            overlay.addView((View) objectRef.element, -1, -1);
            ((View) objectRef.element).animate().alpha(0.8f).setDuration(move.getDurationMs()).start();
        } else {
            i = -1;
            objectRef = objectRef2;
        }
        int i6 = 0;
        if (!move.getIsFinal()) {
            int i7 = WhenMappings.$EnumSwitchMapping$0[move.getType().ordinal()];
            if (i7 != 1) {
                i2 = 2;
                if (i7 != 2) {
                    if (move.getType() == MoveType.SWAP_OPPOSITE) {
                        i3 = 8;
                        for (int i8 = 0; i8 < 8; i8++) {
                            iArr2[i8] = (currentSlots[i8] + 4) % 8;
                        }
                    } else {
                        i3 = 8;
                        if (move.getType() == MoveType.SWAP_ADJACENT) {
                            int i9 = 0;
                            while (i9 < i3) {
                                int i10 = currentSlots[i9];
                                iArr2[i9] = i10 % 2 == 0 ? i10 + 1 : i10 - 1;
                                i9++;
                                i3 = 8;
                            }
                        } else if (move.getType() == MoveType.CHAOS) {
                            List listShuffled = CollectionsKt.shuffled(CollectionsKt.toMutableList(new IntRange(0, 7)));
                            i4 = 8;
                            for (int i11 = 0; i11 < 8; i11++) {
                                iArr2[i11] = ((Number) listShuffled.get(i11)).intValue();
                            }
                        } else {
                            i4 = 8;
                        }
                        i5 = 0;
                        while (i5 < i4) {
                            double radians = Math.toRadians(((double) iArr2[i5]) * 45.0d);
                            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(keys.get(i5), "x", keys.get(i5).getX(), centerX + (((float) Math.cos(radians)) * radius));
                            Intrinsics.checkNotNullExpressionValue(objectAnimatorOfFloat, "ofFloat(...)");
                            arrayList.add(objectAnimatorOfFloat);
                            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(keys.get(i5), "y", keys.get(i5).getY(), centerY + (((float) Math.sin(radians)) * radius));
                            Intrinsics.checkNotNullExpressionValue(objectAnimatorOfFloat2, "ofFloat(...)");
                            arrayList.add(objectAnimatorOfFloat2);
                            i5++;
                            i4 = 8;
                        }
                        iArr = iArr2;
                    }
                    i4 = i3;
                    i5 = 0;
                    while (i5 < i4) {
                        double radians2 = Math.toRadians(((double) iArr2[i5]) * 45.0d);
                        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(keys.get(i5), "x", keys.get(i5).getX(), centerX + (((float) Math.cos(radians2)) * radius));
                        Intrinsics.checkNotNullExpressionValue(objectAnimatorOfFloat3, "ofFloat(...)");
                        arrayList.add(objectAnimatorOfFloat3);
                        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(keys.get(i5), "y", keys.get(i5).getY(), centerY + (((float) Math.sin(radians2)) * radius));
                        Intrinsics.checkNotNullExpressionValue(objectAnimatorOfFloat4, "ofFloat(...)");
                        arrayList.add(objectAnimatorOfFloat4);
                        i5++;
                        i4 = 8;
                    }
                    iArr = iArr2;
                }
            } else {
                i2 = 2;
            }
            final int i12 = move.getType() == MoveType.ROTATE_CW ? 1 : i;
            while (i6 < 8) {
                int steps = (currentSlots[i6] + (move.getSteps() * i12)) % 8;
                iArr2[i6] = steps;
                if (steps < 0) {
                    iArr2[i6] = steps + 8;
                }
                i6++;
            }
            float[] fArr = new float[i2];
            // fill-array-data instruction
            fArr[0] = 0.0f;
            fArr[1] = 1.0f;
            ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(fArr);
            iArr = iArr2;
            valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LimboMinigameManager.executeChoreography$lambda$11$lambda$10(currentSlots, move, i12, keys, centerX, radius, centerY, valueAnimator);
                }
            });
            Intrinsics.checkNotNull(valueAnimatorOfFloat3);
            arrayList.add(valueAnimatorOfFloat3);
        } else {
            iArr = iArr2;
            int i13 = move.getType() == MoveType.ROTATE_CW ? 1 : i;
            while (i6 < 8) {
                int steps2 = (currentSlots[i6] + (move.getSteps() * i13)) % 8;
                iArr[i6] = steps2;
                if (steps2 < 0) {
                    iArr[i6] = steps2 + 8;
                }
                i6++;
            }
        }
        animatorSet.playTogether(arrayList);
        animatorSet.setDuration(move.getDurationMs());
        animatorSet.setInterpolator(move.getIsFinal() ? new AccelerateInterpolator() : new AccelerateDecelerateInterpolator());
        final int[] iArr3 = iArr;
        final Ref.ObjectRef objectRef3 = objectRef;
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.catrobat.catroid.ui.LimboMinigameManager.executeChoreography.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                for (int i14 = 0; i14 < 8; i14++) {
                    currentSlots[i14] = iArr3[i14];
                }
                LimboMinigameManager.INSTANCE.executeChoreography(activity, overlay, keys, originalView, mediaPlayer, moveIndex + 1, currentSlots, centerX, centerY, radius, objectRef3.element);
            }
        });
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeChoreography$lambda$6$lambda$5(FrameLayout overlay, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(it, "it");
        double d = 50;
        double d2 = 25;
        overlay.setTranslationX((float) ((Math.random() * d) - d2));
        overlay.setTranslationY((float) ((Math.random() * d) - d2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeChoreography$lambda$8$lambda$7(float f, Move move, int[] currentSlots, List keys, float f2, float f3, ValueAnimator it) {
        Intrinsics.checkNotNullParameter(move, "$move");
        Intrinsics.checkNotNullParameter(currentSlots, "$currentSlots");
        Intrinsics.checkNotNullParameter(keys, "$keys");
        Intrinsics.checkNotNullParameter(it, "it");
        float animatedFraction = it.getAnimatedFraction();
        float f4 = f * ((0.3f * animatedFraction) + 1.0f);
        float f5 = (0.5f * animatedFraction) + 1.0f;
        int i = move.getType() == MoveType.ROTATE_CW ? 1 : -1;
        for (int i2 = 0; i2 < 8; i2++) {
            float f6 = currentSlots[i2] * 45.0f;
            double radians = Math.toRadians(f6 + (((((move.getSteps() * 45.0f) * i) + f6) - f6) * animatedFraction));
            ((ImageView) keys.get(i2)).setX((((float) Math.cos(radians)) * f4) + f2);
            ((ImageView) keys.get(i2)).setY((((float) Math.sin(radians)) * f4) + f3);
            ((ImageView) keys.get(i2)).setScaleX(f5);
            ((ImageView) keys.get(i2)).setScaleY(f5);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void executeChoreography$lambda$11$lambda$10(int[] currentSlots, Move move, int i, List keys, float f, float f2, float f3, ValueAnimator anim) {
        Intrinsics.checkNotNullParameter(currentSlots, "$currentSlots");
        Intrinsics.checkNotNullParameter(move, "$move");
        Intrinsics.checkNotNullParameter(keys, "$keys");
        Intrinsics.checkNotNullParameter(anim, "anim");
        float animatedFraction = anim.getAnimatedFraction();
        for (int i2 = 0; i2 < 8; i2++) {
            double radians = Math.toRadians((currentSlots[i2] * 45.0f) + (move.getSteps() * 45.0f * i * animatedFraction));
            ((ImageView) keys.get(i2)).setX((((float) Math.cos(radians)) * f2) + f);
            ((ImageView) keys.get(i2)).setY((((float) Math.sin(radians)) * f2) + f3);
        }
    }

    /* JADX WARN: Type inference failed for: r1v19, types: [T, android.animation.ValueAnimator] */
    private final void triggerFocusEndgame(final Activity activity, final FrameLayout overlay, final List<? extends ImageView> keys, final View originalView, final MediaPlayer mediaPlayer, final View preFlash) {
        if (preFlash != null) {
            preFlash.setAlpha(1.0f);
            preFlash.animate().alpha(0.0f).setDuration(800L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    LimboMinigameManager.triggerFocusEndgame$lambda$12(overlay, preFlash);
                }
            }).start();
        }
        boolean z = true;
        List listShuffled = CollectionsKt.shuffled(CollectionsKt.listOf((Object[]) new Integer[]{-65536, -16776961, -16711936, Integer.valueOf(InputDeviceCompat.SOURCE_ANY), -16711681, -65281, Integer.valueOf(Color.parseColor("#FFA500")), -1}));
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        int size = keys.size();
        int i = 0;
        while (i < size) {
            keys.get(i).setColorFilter(((Number) listShuffled.get(i)).intValue(), PorterDuff.Mode.MULTIPLY);
            keys.get(i).setClickable(z);
            final int i2 = i;
            keys.get(i).setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda7
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LimboMinigameManager.triggerFocusEndgame$lambda$17(objectRef, keys, mediaPlayer, i2, activity, overlay, originalView, view);
                }
            });
            i++;
            z = true;
        }
        final float[] fArr = new float[keys.size()];
        int size2 = keys.size();
        for (int i3 = 0; i3 < size2; i3++) {
            fArr[i3] = keys.get(i3).getY();
        }
        ?? OfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        OfFloat.setDuration(8000L);
        OfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LimboMinigameManager.triggerFocusEndgame$lambda$19$lambda$18(keys, fArr, valueAnimator);
            }
        });
        OfFloat.start();
        objectRef.element = OfFloat;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerFocusEndgame$lambda$12(FrameLayout overlay, View view) {
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        overlay.removeView(view);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void triggerFocusEndgame$lambda$17(Ref.ObjectRef floatAnim, final List keys, MediaPlayer mediaPlayer, int i, final Activity activity, final FrameLayout overlay, final View originalView, View view) {
        Intrinsics.checkNotNullParameter(floatAnim, "$floatAnim");
        Intrinsics.checkNotNullParameter(keys, "$keys");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(originalView, "$originalView");
        if (isPlaying) {
            ValueAnimator valueAnimator = (ValueAnimator) floatAnim.element;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            Iterator it = keys.iterator();
            while (it.hasNext()) {
                ((ImageView) it.next()).setClickable(false);
            }
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
            if (Intrinsics.areEqual(keys.get(i), keys.get(0))) {
                Toast.makeText(activity, "FOCUS: УСПЕХ", 0).show();
            }
            final View view2 = new View(activity);
            view2.setBackgroundColor(-1);
            view2.setAlpha(0.0f);
            overlay.addView(view2, -1, -1);
            final boolean zAreEqual = Intrinsics.areEqual(keys.get(i), keys.get(0));
            view2.animate().alpha(1.0f).setDuration(150L).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    LimboMinigameManager.triggerFocusEndgame$lambda$17$lambda$16(view2, keys, activity, overlay, originalView, zAreEqual);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerFocusEndgame$lambda$17$lambda$16(View postClickFlash, List keys, final Activity activity, final FrameLayout overlay, final View originalView, final boolean z) {
        Intrinsics.checkNotNullParameter(postClickFlash, "$postClickFlash");
        Intrinsics.checkNotNullParameter(keys, "$keys");
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(originalView, "$originalView");
        postClickFlash.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        Iterator it = keys.iterator();
        while (it.hasNext()) {
            ((ImageView) it.next()).setVisibility(8);
        }
        postClickFlash.animate().alpha(0.0f).setDuration(1500L).setInterpolator(new DecelerateInterpolator()).withEndAction(new Runnable() { // from class: org.catrobat.catroid.ui.LimboMinigameManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                LimboMinigameManager.triggerFocusEndgame$lambda$17$lambda$16$lambda$15(activity, overlay, originalView, z);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerFocusEndgame$lambda$17$lambda$16$lambda$15(Activity activity, FrameLayout overlay, View originalView, boolean z) {
        Intrinsics.checkNotNullParameter(activity, "$activity");
        Intrinsics.checkNotNullParameter(overlay, "$overlay");
        Intrinsics.checkNotNullParameter(originalView, "$originalView");
        View decorView = activity.getWindow().getDecorView();
        Intrinsics.checkNotNull(decorView, "null cannot be cast to non-null type android.view.ViewGroup");
        ((ViewGroup) decorView).removeView(overlay);
        originalView.setVisibility(0);
        isPlaying = false;
        if (z) {
            originalView.performClick();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void triggerFocusEndgame$lambda$19$lambda$18(List keys, float[] baseYs, ValueAnimator anim) {
        Intrinsics.checkNotNullParameter(keys, "$keys");
        Intrinsics.checkNotNullParameter(baseYs, "$baseYs");
        Intrinsics.checkNotNullParameter(anim, "anim");
        float animatedFraction = (1.0f - anim.getAnimatedFraction()) * 40.0f;
        int size = keys.size();
        for (int i = 0; i < size; i++) {
            ((ImageView) keys.get(i)).setY(baseYs[i] + ((float) (Math.sin((anim.getCurrentPlayTime() / 200.0d) + ((double) i)) * ((double) animatedFraction))));
        }
    }
}
