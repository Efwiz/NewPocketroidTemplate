package org.catrobat.paintroid.tools;

import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import com.reandroid.arsc.value.ResConfigBase;
import kotlin.Metadata;

/* JADX INFO: compiled from: ContextCallback.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001:\u0002\u001f J\u0012\u0010\u0012\u001a\u00020\u000f2\b\b\u0001\u0010\u0013\u001a\u00020\u000fH'J\u0014\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\b\u0001\u0010\u0016\u001a\u00020\u000fH&J\u0014\u0010\u0017\u001a\u0004\u0018\u00010\u00182\b\b\u0001\u0010\u0013\u001a\u00020\u000fH&J\u0012\u0010\u0019\u001a\u00020\u001a2\b\b\u0001\u0010\u001b\u001a\u00020\u000fH&J\u001a\u0010\u001c\u001a\u00020\u001a2\b\b\u0001\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001eH&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006!"}, d2 = {"Lorg/catrobat/paintroid/tools/ContextCallback;", "", "checkeredBitmapShader", "Landroid/graphics/Shader;", "getCheckeredBitmapShader", "()Landroid/graphics/Shader;", "displayMetrics", "Landroid/util/DisplayMetrics;", "getDisplayMetrics", "()Landroid/util/DisplayMetrics;", ResConfigBase.NAME_orientation, "Lorg/catrobat/paintroid/tools/ContextCallback$ScreenOrientation;", "getOrientation", "()Lorg/catrobat/paintroid/tools/ContextCallback$ScreenOrientation;", "scrollTolerance", "", "getScrollTolerance", "()I", "getColor", "id", "getDrawable", "Landroid/graphics/drawable/Drawable;", "resource", "getFont", "Landroid/graphics/Typeface;", "showNotification", "", "resId", "showNotificationWithDuration", "duration", "Lorg/catrobat/paintroid/tools/ContextCallback$NotificationDuration;", "NotificationDuration", "ScreenOrientation", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ContextCallback {

    /* JADX INFO: compiled from: ContextCallback.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/tools/ContextCallback$NotificationDuration;", "", "(Ljava/lang/String;I)V", "SHORT", "LONG", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum NotificationDuration {
        SHORT,
        LONG
    }

    /* JADX INFO: compiled from: ContextCallback.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/tools/ContextCallback$ScreenOrientation;", "", "(Ljava/lang/String;I)V", "PORTRAIT", "LANDSCAPE", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public enum ScreenOrientation {
        PORTRAIT,
        LANDSCAPE
    }

    Shader getCheckeredBitmapShader();

    int getColor(int id2);

    DisplayMetrics getDisplayMetrics();

    Drawable getDrawable(int resource);

    Typeface getFont(int id2);

    ScreenOrientation getOrientation();

    int getScrollTolerance();

    void showNotification(int resId);

    void showNotificationWithDuration(int resId, NotificationDuration duration);
}
