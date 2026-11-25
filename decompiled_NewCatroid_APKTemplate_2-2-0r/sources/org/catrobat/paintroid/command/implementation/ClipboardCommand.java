package org.catrobat.paintroid.command.implementation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.constraintlayout.motion.widget.Key;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.FileIO;
import org.catrobat.paintroid.PaintroidApplication;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.contract.LayerContracts;

/* JADX INFO: compiled from: ClipboardCommand.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 +2\u00020\u0001:\u0001+B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0014\u0010 \u001a\u0004\u0018\u00010\u00032\b\u0010!\u001a\u0004\u0018\u00010\u0003H\u0002J \u0010\"\u001a\u00020\u00032\u0006\u0010#\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J\u0018\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J \u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0007R\"\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0011\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u001e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u001e\u0010\u0015\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001d¨\u0006,"}, d2 = {"Lorg/catrobat/paintroid/command/implementation/ClipboardCommand;", "Lorg/catrobat/paintroid/command/Command;", "bitmap", "Landroid/graphics/Bitmap;", "position", "Landroid/graphics/Point;", "width", "", "height", Key.ROTATION, "(Landroid/graphics/Bitmap;Landroid/graphics/Point;FFF)V", "<set-?>", "getBitmap", "()Landroid/graphics/Bitmap;", "boxHeight", "getBoxHeight", "()F", "boxRotation", "getBoxRotation", "boxWidth", "getBoxWidth", "coordinates", "getCoordinates", "()Landroid/graphics/Point;", "fileToStoredBitmap", "Ljava/io/File;", "getFileToStoredBitmap", "()Ljava/io/File;", "setFileToStoredBitmap", "(Ljava/io/File;)V", "freeResources", "", "recycleBitmap", "bitmapToRecycle", "resizeBitmap", "bitmapToResize", "run", "canvas", "Landroid/graphics/Canvas;", "layerModel", "Lorg/catrobat/paintroid/contract/LayerContracts$Model;", "storeBitmap", "bitmapToStore", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class ClipboardCommand implements Command {
    private static final int COMPRESSION_QUALITY = 100;
    private Bitmap bitmap;
    private float boxHeight;
    private float boxRotation;
    private float boxWidth;
    private Point coordinates;
    private File fileToStoredBitmap;

    public ClipboardCommand(Bitmap bitmap, Point position, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        Intrinsics.checkNotNullParameter(position, "position");
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        this.coordinates = position;
        this.boxRotation = f3;
        this.boxWidth = f;
        this.boxHeight = f2;
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final Point getCoordinates() {
        return this.coordinates;
    }

    public final float getBoxRotation() {
        return this.boxRotation;
    }

    public final float getBoxWidth() {
        return this.boxWidth;
    }

    public final float getBoxHeight() {
        return this.boxHeight;
    }

    public final File getFileToStoredBitmap() {
        return this.fileToStoredBitmap;
    }

    public final void setFileToStoredBitmap(File file) {
        this.fileToStoredBitmap = file;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void run(Canvas canvas, LayerContracts.Model layerModel) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(layerModel, "layerModel");
        Bitmap bitmapFromFile = this.bitmap;
        if (this.fileToStoredBitmap != null) {
            bitmapFromFile = FileIO.INSTANCE.getBitmapFromFile(this.fileToStoredBitmap);
        }
        if (bitmapFromFile == null) {
            return;
        }
        float f = this.boxWidth;
        float f2 = this.boxHeight;
        RectF rectF = new RectF((-f) / 2.0f, (-f2) / 2.0f, f / 2.0f, f2 / 2.0f);
        canvas.save();
        canvas.translate(getCoordinates().x, getCoordinates().y);
        canvas.rotate(getBoxRotation());
        canvas.drawBitmap(bitmapFromFile, (Rect) null, rectF, (Paint) null);
        canvas.restore();
        if (this.fileToStoredBitmap == null) {
            storeBitmap(bitmapFromFile, this.boxWidth, this.boxHeight);
        }
        this.bitmap = recycleBitmap(bitmapFromFile);
    }

    public final void storeBitmap(Bitmap bitmapToStore, float boxWidth, float boxHeight) {
        Intrinsics.checkNotNullParameter(bitmapToStore, "bitmapToStore");
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        File cacheDir = PaintroidApplication.INSTANCE.getCacheDir();
        this.fileToStoredBitmap = new File(cacheDir == null ? null : cacheDir.getAbsolutePath(), String.valueOf(random.nextLong()));
        Bitmap bitmapResizeBitmap = resizeBitmap(bitmapToStore, boxWidth, boxHeight);
        FileOutputStream fileOutputStream = new FileOutputStream(this.fileToStoredBitmap);
        try {
            bitmapResizeBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            CloseableKt.closeFinally(fileOutputStream, null);
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(fileOutputStream, th);
                throw th2;
            }
        }
    }

    private final Bitmap resizeBitmap(Bitmap bitmapToResize, float boxWidth, float boxHeight) {
        int width = boxWidth < ((float) bitmapToResize.getWidth()) ? (int) boxWidth : bitmapToResize.getWidth();
        int height = boxHeight < ((float) bitmapToResize.getHeight()) ? (int) boxHeight : bitmapToResize.getHeight();
        if (width == bitmapToResize.getWidth() && height == bitmapToResize.getHeight()) {
            return bitmapToResize;
        }
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapToResize, width, height, false);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(bitma…wWidth, newHeight, false)");
        return bitmapCreateScaledBitmap;
    }

    private final Bitmap recycleBitmap(Bitmap bitmapToRecycle) {
        if (bitmapToRecycle != null) {
            if (!bitmapToRecycle.isRecycled()) {
                bitmapToRecycle.recycle();
            }
        }
        return null;
    }

    @Override // org.catrobat.paintroid.command.Command
    public void freeResources() {
        this.bitmap = recycleBitmap(this.bitmap);
        File file = this.fileToStoredBitmap;
        if (file != null && file.exists()) {
            file.delete();
        }
    }
}
