package org.catrobat.paintroid.tools.implementation;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.ui.ToastFactory;

/* JADX INFO: compiled from: DefaultContextCallback.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0015\u001a\u00020\u00122\b\b\u0001\u0010\u0016\u001a\u00020\u0012H\u0017J\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\b\u0001\u0010\u0019\u001a\u00020\u0012H\u0016J\u0014\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\b\u0001\u0010\u0016\u001a\u00020\u0012H\u0016J\u0012\u0010\u001c\u001a\u00020\u001d2\b\b\u0001\u0010\u001e\u001a\u00020\u0012H\u0016J\u001a\u0010\u001f\u001a\u00020\u001d2\b\b\u0001\u0010\u001e\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!H\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\""}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/DefaultContextCallback;", "Lorg/catrobat/paintroid/tools/ContextCallback;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "checkeredBitmapShader", "Landroid/graphics/Shader;", "getCheckeredBitmapShader", "()Landroid/graphics/Shader;", "displayMetrics", "Landroid/util/DisplayMetrics;", "getDisplayMetrics", "()Landroid/util/DisplayMetrics;", ResConfigBase.NAME_orientation, "Lorg/catrobat/paintroid/tools/ContextCallback$ScreenOrientation;", "getOrientation", "()Lorg/catrobat/paintroid/tools/ContextCallback$ScreenOrientation;", "scrollTolerance", "", "getScrollTolerance", "()I", "getColor", "id", "getDrawable", "Landroid/graphics/drawable/Drawable;", "resource", "getFont", "Landroid/graphics/Typeface;", "showNotification", "", "resId", "showNotificationWithDuration", "duration", "Lorg/catrobat/paintroid/tools/ContextCallback$NotificationDuration;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultContextCallback implements ContextCallback {
    private final Shader checkeredBitmapShader;
    private final Context context;
    private final DisplayMetrics displayMetrics;
    private final int scrollTolerance;

    public DefaultContextCallback(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.checkeredBitmapShader = new BitmapShader(BitmapFactory.decodeResource(context.getResources(), R.drawable.pocketpaint_checkeredbg), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        this.scrollTolerance = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.1f);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Intrinsics.checkNotNullExpressionValue(displayMetrics, "context.resources.displayMetrics");
        this.displayMetrics = displayMetrics;
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public Shader getCheckeredBitmapShader() {
        return this.checkeredBitmapShader;
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public void showNotification(int resId) {
        showNotificationWithDuration(resId, ContextCallback.NotificationDuration.SHORT);
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public void showNotificationWithDuration(int resId, ContextCallback.NotificationDuration duration) {
        Intrinsics.checkNotNullParameter(duration, "duration");
        ToastFactory.makeText(this.context, resId, duration == ContextCallback.NotificationDuration.SHORT ? 0 : 1).show();
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public int getScrollTolerance() {
        return this.scrollTolerance;
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public ContextCallback.ScreenOrientation getOrientation() {
        return this.context.getResources().getConfiguration().orientation == 2 ? ContextCallback.ScreenOrientation.LANDSCAPE : ContextCallback.ScreenOrientation.PORTRAIT;
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public Typeface getFont(int id2) {
        return ResourcesCompat.getFont(this.context, id2);
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public DisplayMetrics getDisplayMetrics() {
        return this.displayMetrics;
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public int getColor(int id2) {
        return ContextCompat.getColor(this.context, id2);
    }

    @Override // org.catrobat.paintroid.tools.ContextCallback
    public Drawable getDrawable(int resource) {
        return AppCompatResources.getDrawable(this.context, resource);
    }
}
