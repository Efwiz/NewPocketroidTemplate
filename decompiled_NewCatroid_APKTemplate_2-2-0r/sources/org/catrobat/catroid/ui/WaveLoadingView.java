package org.catrobat.catroid.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.content.ContextCompat;
import com.gaurav.avnc.vnc.XKeySym;
import com.reandroid.arsc.value.ResConfigBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/WaveLoadingView.class
  classes14.dex
 */
/* JADX INFO: compiled from: WaveLoadingView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001,B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001e\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\r2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\u0007J\u001e\u0010\"\u001a\u00020\u001e2\b\b\u0002\u0010#\u001a\u00020$2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u001e0&J\b\u0010'\u001a\u00020\u001eH\u0014J\b\u0010(\u001a\u00020\u001eH\u0014J\u0010\u0010)\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020+H\u0014R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u0014j\b\u0012\u0004\u0012\u00020\u0015`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006-"}, d2 = {"Lorg/catrobat/catroid/ui/WaveLoadingView;", "Landroid/view/View;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "accentColor", "amplitudeAnimator", "Landroid/animation/ValueAnimator;", "currentAmplitude", "", ResConfigBase.NAME_density, "maxAmplitude", "paintMain", "Landroid/graphics/Paint;", "particlePaint", "particles", "Ljava/util/ArrayList;", "Lorg/catrobat/catroid/ui/WaveLoadingView$Particle;", "Lkotlin/collections/ArrayList;", "path1", "Landroid/graphics/Path;", TypedValues.CycleType.S_WAVE_PHASE, "phaseAnimator", "random", "Ljava/util/Random;", "explode", "", "x", "y", "count", "flatline", "duration", "", "onComplete", "Lkotlin/Function0;", "onAttachedToWindow", "onDetachedFromWindow", "onDraw", "canvas", "Landroid/graphics/Canvas;", "Particle", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WaveLoadingView extends View {
    private final int accentColor;
    private ValueAnimator amplitudeAnimator;
    private float currentAmplitude;
    private final float density;
    private float maxAmplitude;
    private final Paint paintMain;
    private final Paint particlePaint;
    private final ArrayList<Particle> particles;
    private final Path path1;
    private float phase;
    private ValueAnimator phaseAnimator;
    private final Random random;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WaveLoadingView(Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public WaveLoadingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ WaveLoadingView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WaveLoadingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        float f = getResources().getDisplayMetrics().density;
        this.density = f;
        int color = ContextCompat.getColor(context, R.color.accent);
        this.accentColor = color;
        this.random = new Random();
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(2.5f * f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        this.paintMain = paint;
        Paint paint2 = new Paint(1);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(color);
        this.particlePaint = paint2;
        this.path1 = new Path();
        this.particles = new ArrayList<>();
        this.maxAmplitude = f * 14.0f;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 6.2831855f);
        valueAnimatorOfFloat.setDuration(1800L);
        valueAnimatorOfFloat.setRepeatCount(-1);
        valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.WaveLoadingView$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                WaveLoadingView.lambda$3$lambda$2(this.f$0, valueAnimator);
            }
        });
        this.phaseAnimator = valueAnimatorOfFloat;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, this.maxAmplitude);
        valueAnimatorOfFloat2.setDuration(1800L);
        valueAnimatorOfFloat2.setInterpolator(new DecelerateInterpolator());
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.WaveLoadingView$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                WaveLoadingView.lambda$5$lambda$4(this.f$0, valueAnimator);
            }
        });
        this.amplitudeAnimator = valueAnimatorOfFloat2;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/WaveLoadingView$Particle.class
      classes14.dex
     */
    /* JADX INFO: compiled from: WaveLoadingView.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0006\u0010 \u001a\u00020!R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000fR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\rR\u001a\u0010\u0007\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0015\"\u0004\b\u0019\u0010\u0017R\u001a\u0010\u0006\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0015\"\u0004\b\u001b\u0010\u0017R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0015\"\u0004\b\u001d\u0010\u0017R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0015\"\u0004\b\u001f\u0010\u0017¨\u0006\""}, d2 = {"Lorg/catrobat/catroid/ui/WaveLoadingView$Particle;", "", "x", "", "y", "vx", "vy", "size", "alpha", "", "maxLife", "(FFFFFII)V", "getAlpha", "()I", "setAlpha", "(I)V", "life", "getLife", "setLife", "getMaxLife", "getSize", "()F", "setSize", "(F)V", "getVx", "setVx", "getVy", "setVy", "getX", "setX", "getY", "setY", ConfigConstants.CONFIG_KEY_UPDATE, "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Particle {
        private int alpha;
        private int life;
        private final int maxLife;
        private float size;
        private float vx;
        private float vy;
        private float x;
        private float y;

        public Particle(float f, float f2, float f3, float f4, float f5, int i, int i2) {
            this.x = f;
            this.y = f2;
            this.vx = f3;
            this.vy = f4;
            this.size = f5;
            this.alpha = i;
            this.maxLife = i2;
            this.life = i2;
        }

        public final float getX() {
            return this.x;
        }

        public final void setX(float f) {
            this.x = f;
        }

        public final float getY() {
            return this.y;
        }

        public final void setY(float f) {
            this.y = f;
        }

        public final float getVx() {
            return this.vx;
        }

        public final void setVx(float f) {
            this.vx = f;
        }

        public final float getVy() {
            return this.vy;
        }

        public final void setVy(float f) {
            this.vy = f;
        }

        public final float getSize() {
            return this.size;
        }

        public final void setSize(float f) {
            this.size = f;
        }

        public final int getAlpha() {
            return this.alpha;
        }

        public final void setAlpha(int i) {
            this.alpha = i;
        }

        public final int getMaxLife() {
            return this.maxLife;
        }

        public final int getLife() {
            return this.life;
        }

        public final void setLife(int i) {
            this.life = i;
        }

        public final boolean update() {
            float f = this.x;
            float f2 = this.vx;
            this.x = f + f2;
            float f3 = this.y;
            float f4 = this.vy;
            this.y = f3 + f4;
            this.vx = f2 * 0.91f;
            this.vy = f4 * 0.91f;
            int i = this.life - 1;
            this.life = i;
            this.alpha = RangesKt.coerceIn((int) ((i / this.maxLife) * XKeySym.XK_ae), 0, 255);
            return this.life > 0;
        }
    }

    static final void lambda$3$lambda$2(WaveLoadingView this$0, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        this$0.phase = ((Float) animatedValue).floatValue();
        this$0.invalidate();
    }

    static final void lambda$5$lambda$4(WaveLoadingView this$0, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        this$0.currentAmplitude = ((Float) animatedValue).floatValue();
    }

    public final void explode(float x, float y, int count) {
        for (int i = 0; i < count; i++) {
            double dNextFloat = ((double) (this.random.nextFloat() * 2)) * 3.141592653589793d;
            float fNextFloat = ((this.random.nextFloat() * 5.5f) + 2.0f) * this.density;
            this.particles.add(new Particle(x, y, ((float) Math.cos(dNextFloat)) * fNextFloat, ((float) Math.sin(dNextFloat)) * fNextFloat, ((this.random.nextFloat() * 2.2f) + 1.2f) * this.density, 255, this.random.nextInt(15) + 20));
        }
        invalidate();
    }

    public static /* synthetic */ void flatline$default(WaveLoadingView waveLoadingView, long j, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 350;
        }
        waveLoadingView.flatline(j, function0);
    }

    public final void flatline(long duration, final Function0<Unit> onComplete) {
        Intrinsics.checkNotNullParameter(onComplete, "onComplete");
        ValueAnimator valueAnimator = this.amplitudeAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.currentAmplitude, 0.0f);
        valueAnimatorOfFloat.setDuration(duration);
        valueAnimatorOfFloat.setInterpolator(new DecelerateInterpolator());
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.catrobat.catroid.ui.WaveLoadingView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                WaveLoadingView.flatline$lambda$7$lambda$6(this.f$0, valueAnimator2);
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.catrobat.catroid.ui.WaveLoadingView$flatline$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                Intrinsics.checkNotNullParameter(animation, "animation");
                onComplete.invoke();
            }
        });
        valueAnimatorOfFloat.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void flatline$lambda$7$lambda$6(WaveLoadingView this$0, ValueAnimator animation) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(animation, "animation");
        Object animatedValue = animation.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        this$0.currentAmplitude = ((Float) animatedValue).floatValue();
        this$0.invalidate();
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ValueAnimator valueAnimator = this.phaseAnimator;
        if (valueAnimator != null) {
            valueAnimator.start();
        }
        ValueAnimator valueAnimator2 = this.amplitudeAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.start();
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        ValueAnimator valueAnimator = this.phaseAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimator2 = this.amplitudeAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.cancel();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        float width = getWidth();
        float height = getHeight();
        float f = 0.7f * height;
        if (width <= 0.0f || height <= 0.0f) {
            return;
        }
        this.path1.reset();
        float f2 = this.density * 240.0f;
        float f3 = (width - f2) / 2.0f;
        float f4 = (width + f2) / 2.0f;
        this.path1.moveTo(f3, f);
        for (float f5 = f3; f5 <= f4; f5 += 2.0f) {
            float f6 = (f5 - f3) / f2;
            this.path1.lineTo(f5, (((float) Math.sin((((double) (f6 * 2)) * 3.141592653589793d * 1.5d) + ((double) this.phase))) * this.currentAmplitude * ((float) Math.sin(((double) f6) * 3.141592653589793d))) + f);
        }
        Iterator<Particle> it = this.particles.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            Particle next = it.next();
            Intrinsics.checkNotNullExpressionValue(next, "next(...)");
            Particle particle = next;
            if (particle.update()) {
                this.particlePaint.setAlpha(particle.getAlpha());
                canvas.drawCircle(particle.getX(), particle.getY(), particle.getSize(), this.particlePaint);
            } else {
                it.remove();
            }
        }
        canvas.drawPath(this.path1, this.paintMain);
    }
}
