package io.github.rosemoe.sora.widget.component;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.google.android.material.badge.BadgeDrawable;
import io.github.rosemoe.sora.R;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class Magnifier implements EditorBuiltinComponent {
    private long expectedRequestTime;
    private final ImageView image;
    private final float maxTextSize;
    private final Paint paint;
    private View parentView;
    private final PopupWindow popup;
    private float scaleFactor;
    private final CodeEditor view;
    private int x;
    private int y;
    private boolean enabled = true;
    private boolean withinEditorForcibly = false;

    public Magnifier(CodeEditor codeEditor) {
        this.view = codeEditor;
        this.parentView = codeEditor;
        PopupWindow popupWindow = new PopupWindow();
        this.popup = popupWindow;
        popupWindow.setElevation(codeEditor.getDpUnit() * 4.0f);
        View viewInflate = LayoutInflater.from(codeEditor.getContext()).inflate(R.layout.magnifier_popup, (ViewGroup) null);
        this.image = (ImageView) viewInflate.findViewById(R.id.magnifier_image_view);
        popupWindow.setHeight((int) (codeEditor.getDpUnit() * 70.0f));
        popupWindow.setWidth((int) (codeEditor.getDpUnit() * 100.0f));
        popupWindow.setContentView(viewInflate);
        this.maxTextSize = TypedValue.applyDimension(2, 28.0f, viewInflate.getResources().getDisplayMetrics());
        this.scaleFactor = 1.25f;
        this.paint = new Paint();
    }

    public View getParentView() {
        return this.parentView;
    }

    public void setParentView(View view) {
        this.parentView = (View) Objects.requireNonNull(view);
    }

    public float getScaleFactor() {
        return this.scaleFactor;
    }

    public void setScaleFactor(float f) {
        if (f <= 1.0f) {
            throw new IllegalArgumentException("factor can not be under 1.0");
        }
        this.scaleFactor = f;
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override // io.github.rosemoe.sora.widget.component.EditorBuiltinComponent
    public void setEnabled(boolean z) {
        this.enabled = z;
        if (z) {
            return;
        }
        dismiss();
    }

    public boolean isWithinEditorForcibly() {
        return this.withinEditorForcibly;
    }

    public void setWithinEditorForcibly(boolean z) {
        this.withinEditorForcibly = z;
    }

    public void show(int i, int i2) {
        if (this.enabled) {
            if (Math.abs(i - this.x) >= 2 || Math.abs(i2 - this.y) >= 2) {
                if (this.view.getTextSizePx() > this.maxTextSize) {
                    if (isShowing()) {
                        dismiss();
                        return;
                    }
                    return;
                }
                this.popup.setWidth(Math.min((this.view.getWidth() * 3) / 5, (int) this.view.getDpUnit()) * 250);
                this.x = i;
                this.y = i2;
                int[] iArr = new int[2];
                this.view.getLocationInWindow(iArr);
                int iMax = Math.max((iArr[0] + i) - (this.popup.getWidth() / 2), 0);
                if (this.popup.getWidth() + iMax > this.view.getWidth() + iArr[0]) {
                    iMax = Math.max(0, (this.view.getWidth() + iArr[0]) - this.popup.getWidth());
                }
                int iMax2 = Math.max(((iArr[1] + i2) - this.popup.getHeight()) - this.view.getRowHeight(), 0);
                if (this.popup.isShowing()) {
                    PopupWindow popupWindow = this.popup;
                    popupWindow.update(iMax, iMax2, popupWindow.getWidth(), this.popup.getHeight());
                } else {
                    this.popup.showAtLocation(this.parentView, BadgeDrawable.TOP_START, iMax, iMax2);
                }
                updateDisplay();
            }
        }
    }

    public boolean isShowing() {
        return this.popup.isShowing();
    }

    public void dismiss() {
        this.popup.dismiss();
    }

    public void updateDisplay() {
        if (isShowing()) {
            if (!this.withinEditorForcibly && (this.view.getContext() instanceof Activity)) {
                updateDisplayOreo((Activity) this.view.getContext());
            } else {
                updateDisplayWithinEditor();
            }
        }
    }

    private void updateDisplayOreo(Activity activity) {
        int i;
        int width = (int) (this.popup.getWidth() / this.scaleFactor);
        int height = (int) (this.popup.getHeight() / this.scaleFactor);
        int iMax = Math.max(this.x - (width / 2), 0);
        int iMax2 = Math.max(this.y - (height / 2), 0);
        int iMin = Math.min(iMax + width, this.view.getWidth());
        int iMin2 = Math.min(iMax2 + height, this.view.getHeight());
        if (iMin - iMax < width) {
            iMax = Math.max(0, iMin - width);
        }
        if (iMin2 - iMax2 < height) {
            iMax2 = Math.max(0, iMin2 - height);
        }
        int i2 = iMin - iMax;
        if (i2 <= 0 || (i = iMin2 - iMax2) <= 0) {
            dismiss();
            return;
        }
        int[] iArr = new int[2];
        this.view.getLocationInWindow(iArr);
        final long jCurrentTimeMillis = System.currentTimeMillis();
        this.expectedRequestTime = jCurrentTimeMillis;
        final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
        try {
            Window window = activity.getWindow();
            int i3 = iArr[0];
            int i4 = iArr[1];
            PixelCopy.request(window, new Rect(iMax + i3, iMax2 + i4, i3 + iMin, i4 + iMin2), bitmapCreateBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: io.github.rosemoe.sora.widget.component.Magnifier$$ExternalSyntheticLambda0
                @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                public final void onPixelCopyFinished(int i5) {
                    this.f$0.lambda$updateDisplayOreo$0(jCurrentTimeMillis, bitmapCreateBitmap, i5);
                }
            }, this.view.getHandler());
        } catch (IllegalArgumentException unused) {
            dismiss();
            if (bitmapCreateBitmap.isRecycled()) {
                return;
            }
            bitmapCreateBitmap.recycle();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDisplayOreo$0(long j, Bitmap bitmap, int i) {
        if (j != this.expectedRequestTime) {
            return;
        }
        if (i == 0) {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.popup.getWidth(), this.popup.getHeight(), Bitmap.Config.ARGB_8888);
            Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, this.popup.getWidth(), this.popup.getHeight(), true);
            bitmap.recycle();
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            this.paint.reset();
            this.paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(0.0f, 0.0f, this.popup.getWidth(), this.popup.getHeight(), this.view.getDpUnit() * 6.0f, this.view.getDpUnit() * 6.0f, this.paint);
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmapCreateScaledBitmap, 0.0f, 0.0f, this.paint);
            bitmapCreateScaledBitmap.recycle();
            this.image.setImageBitmap(bitmapCreateBitmap);
            return;
        }
        Log.w("Magnifier", "Failed to copy pixels, error = " + i);
    }

    private void updateDisplayWithinEditor() {
        if (this.popup.getWidth() <= 0 || this.popup.getHeight() <= 0) {
            dismiss();
            return;
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.popup.getWidth(), this.popup.getHeight(), Bitmap.Config.ARGB_8888);
        int width = (int) (this.popup.getWidth() / this.scaleFactor);
        int height = (int) (this.popup.getHeight() / this.scaleFactor);
        int iMax = Math.max(this.x - (width / 2), 0);
        int iMax2 = Math.max(this.y - (height / 2), 0);
        int iMin = Math.min(iMax + width, this.view.getWidth());
        int iMin2 = Math.min(iMax2 + height, this.view.getHeight());
        if (iMin - iMax < width) {
            iMax = Math.max(0, iMin - width);
        }
        if (iMin2 - iMax2 < height) {
            iMax2 = Math.max(0, iMin2 - height);
        }
        if (iMin - iMax <= 0 || iMin2 - iMax2 <= 0) {
            dismiss();
            bitmapCreateBitmap.recycle();
            return;
        }
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap2);
        canvas.translate(-iMax, -iMax2);
        this.view.draw(canvas);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap2, this.popup.getWidth(), this.popup.getHeight(), true);
        bitmapCreateBitmap2.recycle();
        Canvas canvas2 = new Canvas(bitmapCreateBitmap);
        this.paint.reset();
        this.paint.setAntiAlias(true);
        canvas2.drawARGB(0, 0, 0, 0);
        canvas2.drawRoundRect(0.0f, 0.0f, this.popup.getWidth(), this.popup.getHeight(), this.view.getDpUnit() * 6.0f, this.view.getDpUnit() * 6.0f, this.paint);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas2.drawBitmap(bitmapCreateScaledBitmap, 0.0f, 0.0f, this.paint);
        bitmapCreateScaledBitmap.recycle();
        this.image.setImageBitmap(bitmapCreateBitmap);
    }
}
