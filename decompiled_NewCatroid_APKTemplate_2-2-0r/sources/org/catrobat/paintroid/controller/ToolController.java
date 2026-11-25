package org.catrobat.paintroid.controller;

import android.graphics.Bitmap;
import java.util.HashSet;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.colorpicker.OnColorPickedListener;
import org.catrobat.paintroid.tools.Tool;
import org.catrobat.paintroid.tools.ToolType;

/* JADX INFO: compiled from: ToolController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0007H&J\b\u0010\u0018\u001a\u00020\u0016H&J\b\u0010\u0019\u001a\u00020\u0016H&J\b\u0010\u001a\u001a\u00020\u0016H&J\b\u0010\u001b\u001a\u00020\u0016H&J\b\u0010\u001c\u001a\u00020\u0016H&J\b\u0010\u001d\u001a\u00020\u0007H&J\b\u0010\u001e\u001a\u00020\u0016H&J\b\u0010\u001f\u001a\u00020\u0016H&J\b\u0010 \u001a\u00020\u0016H&J\u0012\u0010!\u001a\u00020\u00162\b\u0010\"\u001a\u0004\u0018\u00010#H&J\u0010\u0010$\u001a\u00020\u00162\u0006\u0010%\u001a\u00020&H&J\b\u0010'\u001a\u00020\u0016H&J\u0010\u0010(\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u000fH&J\b\u0010)\u001a\u00020\u0016H&J\b\u0010*\u001a\u00020\u0007H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\nX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u0004\u0018\u00010\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006+"}, d2 = {"Lorg/catrobat/paintroid/controller/ToolController;", "", "currentTool", "Lorg/catrobat/paintroid/tools/Tool;", "getCurrentTool", "()Lorg/catrobat/paintroid/tools/Tool;", "isDefaultTool", "", "()Z", "toolColor", "", "getToolColor", "()Ljava/lang/Integer;", "toolList", "Ljava/util/HashSet;", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolList", "()Ljava/util/HashSet;", "toolType", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "adjustClippingToolOnBackPressed", "", "backPressed", "createTool", "disableHideOption", "disableToolOptionsView", "enableHideOption", "enableToolOptionsView", "hasToolOptionsView", "hideToolOptionsView", "resetToolInternalState", "resetToolInternalStateOnImageLoaded", "setBitmapFromSource", "bitmap", "Landroid/graphics/Bitmap;", "setOnColorPickedListener", "onColorPickedListener", "Lorg/catrobat/paintroid/colorpicker/OnColorPickedListener;", "showToolOptionsView", "switchTool", "toggleToolOptionsView", "toolOptionsViewVisible", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ToolController {
    void adjustClippingToolOnBackPressed(boolean backPressed);

    void createTool();

    void disableHideOption();

    void disableToolOptionsView();

    void enableHideOption();

    void enableToolOptionsView();

    Tool getCurrentTool();

    Integer getToolColor();

    HashSet<ToolType> getToolList();

    ToolType getToolType();

    boolean hasToolOptionsView();

    void hideToolOptionsView();

    boolean isDefaultTool();

    void resetToolInternalState();

    void resetToolInternalStateOnImageLoaded();

    void setBitmapFromSource(Bitmap bitmap);

    void setOnColorPickedListener(OnColorPickedListener onColorPickedListener);

    void showToolOptionsView();

    void switchTool(ToolType toolType);

    void toggleToolOptionsView();

    boolean toolOptionsViewVisible();

    /* JADX INFO: compiled from: ToolController.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public static final class DefaultImpls {
        public static HashSet<ToolType> getToolList(ToolController toolController) {
            Intrinsics.checkNotNullParameter(toolController, "this");
            return SetsKt.hashSetOf(ToolType.TEXT, ToolType.TRANSFORM, ToolType.IMPORTPNG, ToolType.SHAPE, ToolType.LINE);
        }
    }
}
