package io.github.rosemoe.sora.lang.completion;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: SimpleCompletionIconDrawer.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0017J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u0011H\u0016J\u0012\u0010\u0014\u001a\u00020\r2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lio/github/rosemoe/sora/lang/completion/CircleDrawable;", "Landroid/graphics/drawable/Drawable;", Namer.METADATA_CLASS_KIND, "Lio/github/rosemoe/sora/lang/completion/CompletionItemKind;", "circle", "", "(Lio/github/rosemoe/sora/lang/completion/CompletionItemKind;Z)V", "mCircle", "mKind", "mPaint", "Landroid/graphics/Paint;", "mTextPaint", "draw", "", "canvas", "Landroid/graphics/Canvas;", "getOpacity", "", "setAlpha", "p1", "setColorFilter", "colorFilter", "Landroid/graphics/ColorFilter;", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CircleDrawable extends Drawable {
    private final boolean mCircle;
    private final CompletionItemKind mKind;
    private final Paint mPaint;
    private final Paint mTextPaint;

    @Override // android.graphics.drawable.Drawable
    @Deprecated(message = "Deprecated in Java", replaceWith = @ReplaceWith(expression = "PixelFormat.OPAQUE", imports = {"android.graphics.PixelFormat"}))
    public int getOpacity() {
        return -1;
    }

    public CircleDrawable(CompletionItemKind kind, boolean z) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.mKind = kind;
        this.mCircle = z;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor((int) kind.getDefaultDisplayBackgroundColor());
        this.mPaint = paint;
        Paint paint2 = new Paint();
        paint2.setColor(-1);
        paint2.setAntiAlias(true);
        paint2.setTextSize(Resources.getSystem().getDisplayMetrics().density * 14);
        paint2.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint = paint2;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        float f = getBounds().right;
        float f2 = getBounds().bottom;
        if (this.mCircle) {
            float f3 = 2;
            float f4 = f / f3;
            canvas.drawCircle(f4, f2 / f3, f4, this.mPaint);
        } else {
            canvas.drawRect(0.0f, 0.0f, f, f2, this.mPaint);
        }
        canvas.save();
        canvas.translate(f / 2.0f, f2 / 2.0f);
        canvas.drawText(this.mKind.getDisplayString(), 0.0f, (-(this.mTextPaint.descent() + this.mTextPaint.ascent())) / 2.0f, this.mTextPaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int p1) {
        this.mPaint.setAlpha(p1);
        this.mTextPaint.setAlpha(p1);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mTextPaint.setColorFilter(colorFilter);
    }
}
