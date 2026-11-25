package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.test.espresso.idling.CountingIdlingResource;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.serialization.SerializableTypeface;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.FontType;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.options.TextToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;

/* JADX INFO: compiled from: TextTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0012\u0010:\u001a\u00020;2\b\b\u0002\u0010 \u001a\u00020\u0014H\u0002J\u0018\u0010<\u001a\u00020;2\u0006\u0010=\u001a\u0002032\u0006\u0010>\u001a\u00020\u0014H\u0016J\b\u0010?\u001a\u00020;H\u0002J \u0010@\u001a\u00020;2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020(2\u0006\u0010D\u001a\u00020(H\u0014J\u0012\u0010E\u001a\u00020;2\b\u0010F\u001a\u0004\u0018\u00010+H\u0016J\u001a\u0010G\u001a\u00020\u00142\b\u0010F\u001a\u0004\u0018\u00010+2\u0006\u0010H\u001a\u00020\u0014H\u0016J\u0012\u0010I\u001a\u00020\u00142\b\u0010F\u001a\u0004\u0018\u00010+H\u0016J\u0012\u0010J\u001a\u00020;2\b\u0010F\u001a\u0004\u0018\u00010+H\u0016J\u0006\u0010K\u001a\u00020;J\b\u0010L\u001a\u00020;H\u0002J\b\u0010M\u001a\u00020;H\u0016J\u0012\u0010N\u001a\u00020;2\b\u0010O\u001a\u0004\u0018\u00010PH\u0016J\u0012\u0010Q\u001a\u00020;2\b\u0010O\u001a\u0004\u0018\u00010PH\u0016J\b\u0010R\u001a\u00020;H\u0007J\b\u0010S\u001a\u00020;H\u0014J\b\u0010T\u001a\u00020;H\u0002J\u0006\u0010U\u001a\u00020;J\u0012\u0010V\u001a\u00020;2\b\b\u0002\u0010 \u001a\u00020\u0014H\u0002J\u0010\u0010W\u001a\u00020+2\u0006\u0010F\u001a\u00020+H\u0016J\b\u0010X\u001a\u00020;H\u0002R\u0018\u0010\u0013\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u001d\u001a\u00020\u001e8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b\u001f\u0010\u0016R\u0018\u0010 \u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b!\u0010\u0016R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#8G¢\u0006\u0006\u001a\u0004\b%\u0010&R\u000e\u0010'\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020(X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u0004\u0018\u00010+X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u0004\u0018\u00010\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010-\u001a\u00020$8\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b.\u0010\u0016R\u0016\u0010/\u001a\u0002008\u0006X\u0087\u0004¢\u0006\b\n\u0000\u0012\u0004\b1\u0010\u0016R\u000e\u00102\u001a\u000203X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00104\u001a\u0002058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u00107R\u0018\u00108\u001a\u00020\u00148\u0006@\u0006X\u0087\u000e¢\u0006\b\n\u0000\u0012\u0004\b9\u0010\u0016¨\u0006Y"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/TextTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithRectangleShape;", "textToolOptionsView", "Lorg/catrobat/paintroid/tools/options/TextToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/TextToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "bold", "", "getBold$annotations", "()V", "getDrawTime", "()J", "setDrawTime", "(J)V", "dubai", "Landroid/graphics/Typeface;", "font", "Lorg/catrobat/paintroid/tools/FontType;", "getFont$annotations", "italic", "getItalic$annotations", "multilineText", "", "", "getMultilineText", "()[Ljava/lang/String;", "oldBoxHeight", "", "oldBoxWidth", "oldToolPosition", "Landroid/graphics/PointF;", "stc", "text", "getText$annotations", "textPaint", "Landroid/graphics/Paint;", "getTextPaint$annotations", StageActivity.STYLE_TEXT_SIZE, "", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "underlined", "getUnderlined$annotations", "applyAttributes", "", "changePaintColor", TypedValues.Custom.S_COLOR, "invalidate", "changeTextColor", "drawBitmap", "canvas", "Landroid/graphics/Canvas;", "boxWidth", "boxHeight", "handleDownAnimations", "coordinate", "handleMove", "shouldAnimate", "handleUp", "handleUpAnimations", "hideTextToolLayout", "initializePaint", "onClickOnButton", "onRestoreInstanceState", "bundle", "Landroid/os/Bundle;", "onSaveInstanceState", "resetBoxPosition", "resetInternalState", "resetPreview", "showTextToolLayout", "storeAttributes", "toolPositionCoordinates", "updateTypeface", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class TextTool extends BaseToolWithRectangleShape {
    public boolean bold;
    private long drawTime;
    private final Typeface dubai;
    public FontType font;
    public boolean italic;
    private float oldBoxHeight;
    private float oldBoxWidth;
    private PointF oldToolPosition;
    private final Typeface stc;
    public String text;
    public final Paint textPaint;
    private int textSize;
    private final TextToolOptionsView textToolOptionsView;
    public boolean underlined;

    /* JADX INFO: compiled from: TextTool.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[FontType.values().length];
            iArr[FontType.SANS_SERIF.ordinal()] = 1;
            iArr[FontType.SERIF.ordinal()] = 2;
            iArr[FontType.MONOSPACE.ordinal()] = 3;
            iArr[FontType.STC.ordinal()] = 4;
            iArr[FontType.DUBAI.ordinal()] = 5;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static /* synthetic */ void getBold$annotations() {
    }

    public static /* synthetic */ void getFont$annotations() {
    }

    public static /* synthetic */ void getItalic$annotations() {
    }

    public static /* synthetic */ void getText$annotations() {
    }

    public static /* synthetic */ void getTextPaint$annotations() {
    }

    public static /* synthetic */ void getUnderlined$annotations() {
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    protected void resetInternalState() {
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public PointF toolPositionCoordinates(PointF coordinate) {
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        return coordinate;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public long getDrawTime() {
        return this.drawTime;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void setDrawTime(long j) {
        this.drawTime = j;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextTool(TextToolOptionsView textToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, final Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(textToolOptionsView, "textToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.textToolOptionsView = textToolOptionsView;
        this.drawTime = j;
        this.text = "";
        this.font = FontType.SANS_SERIF;
        this.textSize = 20;
        this.rotationEnabled = true;
        this.resizePointsVisible = true;
        this.stc = contextCallback.getFont(R.font.stc_regular);
        this.dubai = contextCallback.getFont(R.font.dubai);
        this.textPaint = new Paint();
        initializePaint();
        resetPreview();
        resetBoxPosition();
        textToolOptionsView.setCallback(new TextToolOptionsView.Callback() { // from class: org.catrobat.paintroid.tools.implementation.TextTool$callback$1
            @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView.Callback
            public void setText(String text) {
                Intrinsics.checkNotNullParameter(text, "text");
                this.this$0.text = text;
                this.this$0.resetPreview();
                workspace.invalidate();
            }

            @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView.Callback
            public void setFont(FontType fontType) {
                Intrinsics.checkNotNullParameter(fontType, "fontType");
                if (fontType == this.this$0.font) {
                    return;
                }
                this.this$0.font = fontType;
                this.this$0.updateTypeface();
                TextTool.storeAttributes$default(this.this$0, false, 1, null);
                this.this$0.resetPreview();
                workspace.invalidate();
                TextTool.applyAttributes$default(this.this$0, false, 1, null);
            }

            @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView.Callback
            public void setUnderlined(boolean underlined) {
                this.this$0.underlined = underlined;
                this.this$0.textPaint.setUnderlineText(this.this$0.underlined);
                TextTool.storeAttributes$default(this.this$0, false, 1, null);
                this.this$0.resetPreview();
                workspace.invalidate();
                TextTool.applyAttributes$default(this.this$0, false, 1, null);
            }

            @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView.Callback
            public void setItalic(boolean italic) {
                this.this$0.italic = italic;
                if (italic) {
                    this.this$0.storeAttributes(italic);
                } else {
                    TextTool.storeAttributes$default(this.this$0, false, 1, null);
                }
                this.this$0.updateTypeface();
                this.this$0.resetPreview();
                workspace.invalidate();
                if (italic) {
                    this.this$0.applyAttributes(italic);
                } else {
                    TextTool.applyAttributes$default(this.this$0, false, 1, null);
                }
            }

            @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView.Callback
            public void setBold(boolean bold) {
                this.this$0.bold = bold;
                TextTool.storeAttributes$default(this.this$0, false, 1, null);
                this.this$0.textPaint.setFakeBoldText(this.this$0.bold);
                this.this$0.resetPreview();
                workspace.invalidate();
                TextTool.applyAttributes$default(this.this$0, false, 1, null);
            }

            @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView.Callback
            public void setTextSize(int size) {
                this.this$0.textSize = size;
                this.this$0.textPaint.setTextSize(this.this$0.textSize * 3.0f);
                this.this$0.resetPreview();
                workspace.invalidate();
            }

            @Override // org.catrobat.paintroid.tools.options.TextToolOptionsView.Callback
            public void hideToolOptions() {
                this.this$0.toolOptionsViewController.hide();
            }
        });
        toolOptionsViewController.showDelayed();
    }

    public final String[] getMultilineText() {
        Object[] array = StringsKt.split$default((CharSequence) this.text, new String[]{"\n"}, false, 0, 6, (Object) null).toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return ToolType.TEXT;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        showTextToolLayout();
        this.toolOptionsViewController.animateBottomAndTopNavigation(false);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        hideTextToolLayout();
    }

    private final void initializePaint() {
        this.textPaint.setAntiAlias(true);
        this.textPaint.setColor(this.toolPaint.getPreviewColor());
        this.textPaint.setTextSize(60.0f);
        this.textPaint.setUnderlineText(this.underlined);
        this.textPaint.setFakeBoldText(this.bold);
        updateTypeface();
    }

    public final void hideTextToolLayout() {
        if (this.textToolOptionsView.getTopLayout().getVisibility() == 0) {
            this.toolOptionsViewController.slideUp(this.textToolOptionsView.getTopLayout(), true, false);
        }
        if (this.textToolOptionsView.getBottomLayout().getVisibility() == 0) {
            this.toolOptionsViewController.slideDown(this.textToolOptionsView.getBottomLayout(), true, false);
        }
    }

    public final void showTextToolLayout() {
        if (this.textToolOptionsView.getTopLayout().getVisibility() == 4) {
            if (!this.toolOptionsViewController.getToolOptionsShown()) {
                this.toolOptionsViewController.show();
            }
            this.toolOptionsViewController.slideDown(this.textToolOptionsView.getTopLayout(), false, true);
        }
        if (this.textToolOptionsView.getBottomLayout().getVisibility() == 4) {
            if (!this.toolOptionsViewController.getToolOptionsShown()) {
                this.toolOptionsViewController.show();
            }
            this.toolOptionsViewController.slideUp(this.textToolOptionsView.getBottomLayout(), false, true);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        this.textToolOptionsView.hideKeyboard();
        hideTextToolLayout();
        return super.handleMove(coordinate, false);
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        if (coordinate == null) {
            return true;
        }
        float f = 2;
        if (Math.abs(this.toolPosition.x - coordinate.x) <= this.boxWidth / f && Math.abs(this.toolPosition.y - coordinate.y) <= this.boxHeight / f) {
            showTextToolLayout();
            super.handleUp(coordinate);
            this.textToolOptionsView.showKeyboard();
            return true;
        }
        this.textToolOptionsView.hideKeyboard();
        hideTextToolLayout();
        super.handleUp(coordinate);
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape
    protected void drawBitmap(Canvas canvas, float boxWidth, float boxHeight) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        float fAscent = this.textPaint.ascent();
        float fDescent = (this.textPaint.descent() - fAscent) * getMultilineText().length;
        float length = fDescent / getMultilineText().length;
        String[] multilineText = getMultilineText();
        if (multilineText.length == 0) {
            throw new NoSuchElementException();
        }
        int i = 0;
        float fMeasureText = this.textPaint.measureText(multilineText[0]);
        int lastIndex = ArraysKt.getLastIndex(multilineText);
        int i2 = 1;
        if (1 <= lastIndex) {
            while (true) {
                fMeasureText = Math.max(fMeasureText, this.textPaint.measureText(multilineText[i2]));
                if (i2 == lastIndex) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        if (this.italic) {
            fMeasureText *= 1.2f;
        }
        canvas.save();
        float f = 40;
        float f2 = (boxWidth - f) / fMeasureText;
        float f3 = (boxHeight - f) / fDescent;
        canvas.scale(f2, f3);
        float f4 = 20;
        float f5 = f4 / f3;
        float f6 = f4 / f2;
        float f7 = boxWidth / f2;
        float f8 = boxHeight / f3;
        String[] multilineText2 = getMultilineText();
        int length2 = multilineText2.length;
        int i3 = 0;
        while (i < length2) {
            int i4 = i3 + 1;
            float f9 = 2;
            canvas.drawText(multilineText2[i], f6 - ((f7 / f9) / (this.italic ? 1.2f : 1.0f)), (((-(f8 / f9)) + f5) - fAscent) + (i3 * length), this.textPaint);
            i++;
            i3 = i4;
        }
        canvas.restore();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resetPreview() {
        float fDescent = this.textPaint.descent() - this.textPaint.ascent();
        String[] multilineText = getMultilineText();
        if (multilineText.length == 0) {
            throw new NoSuchElementException();
        }
        float fMeasureText = this.textPaint.measureText(multilineText[0]);
        int lastIndex = ArraysKt.getLastIndex(multilineText);
        int i = 1;
        if (1 <= lastIndex) {
            while (true) {
                fMeasureText = Math.max(fMeasureText, this.textPaint.measureText(multilineText[i]));
                if (i == lastIndex) {
                    break;
                } else {
                    i++;
                }
            }
        }
        float f = 40;
        this.boxHeight = (fDescent * getMultilineText().length) + f;
        this.boxWidth = fMeasureText + f;
    }

    static /* synthetic */ void storeAttributes$default(TextTool textTool, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        textTool.storeAttributes(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void storeAttributes(boolean italic) {
        if (italic) {
            this.boxWidth *= 1.2f;
        }
        this.oldBoxWidth = this.boxWidth;
        this.oldBoxHeight = this.boxHeight;
        this.oldToolPosition = new PointF(this.toolPosition.x, this.toolPosition.y);
    }

    static /* synthetic */ void applyAttributes$default(TextTool textTool, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        textTool.applyAttributes(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyAttributes(boolean italic) {
        this.boxWidth = this.oldBoxWidth / (italic ? 1.2f : 1.0f);
        this.boxHeight = this.oldBoxHeight;
        PointF pointF = this.oldToolPosition;
        if (pointF == null) {
            resetBoxPosition();
        } else {
            if (pointF == null) {
                throw new NullPointerException("null cannot be cast to non-null type android.graphics.PointF");
            }
            this.toolPosition = pointF;
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle == null) {
            return;
        }
        bundle.putBoolean("BUNDLE_TOOL_UNDERLINED", this.underlined);
        bundle.putBoolean("BUNDLE_TOOL_ITALIC", this.italic);
        bundle.putBoolean("BUNDLE_TOOL_BOLD", this.bold);
        bundle.putString("BUNDLE_TOOL_TEXT", this.text);
        bundle.putInt("BUNDLE_TOOL_TEXT_SIZE", this.textSize);
        bundle.putString("BUNDLE_TOOL_FONT", this.font.name());
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithRectangleShape, org.catrobat.paintroid.tools.implementation.BaseToolWithShape, org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (bundle != null) {
            this.underlined = bundle.getBoolean("BUNDLE_TOOL_UNDERLINED", this.underlined);
            this.italic = bundle.getBoolean("BUNDLE_TOOL_ITALIC", this.italic);
            this.bold = bundle.getBoolean("BUNDLE_TOOL_BOLD", this.bold);
            String string = bundle.getString("BUNDLE_TOOL_TEXT", this.text);
            Intrinsics.checkNotNullExpressionValue(string, "getString(BUNDLE_TOOL_TEXT, text)");
            this.text = string;
            this.textSize = bundle.getInt("BUNDLE_TOOL_TEXT_SIZE", this.textSize);
            String string2 = bundle.getString("BUNDLE_TOOL_FONT", this.font.name());
            Intrinsics.checkNotNullExpressionValue(string2, "getString(BUNDLE_TOOL_FONT, font.name)");
            this.font = FontType.valueOf(string2);
        }
        this.textToolOptionsView.setState(this.bold, this.italic, this.underlined, this.text, this.textSize, this.font);
        this.textPaint.setUnderlineText(this.underlined);
        this.textPaint.setFakeBoldText(this.bold);
        updateTypeface();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void updateTypeface() {
        boolean z = this.italic;
        int i = z ? 2 : 0;
        this.textPaint.setTextSkewX(z ? -0.25f : 0.0f);
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.font.ordinal()];
        if (i2 == 1) {
            this.textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, i));
            return;
        }
        if (i2 == 2) {
            this.textPaint.setTypeface(Typeface.create(Typeface.SERIF, i));
            return;
        }
        if (i2 == 3) {
            this.textPaint.setTypeface(Typeface.create(Typeface.MONOSPACE, i));
            return;
        }
        if (i2 == 4) {
            try {
                this.textPaint.setTypeface(this.stc);
            } catch (Exception unused) {
                Log.e("Can't set custom font", "stc_regular");
            }
        } else {
            if (i2 != 5) {
                return;
            }
            try {
                this.textPaint.setTypeface(this.dubai);
            } catch (Exception unused2) {
                Log.e("Can't set custom font", "dubai");
            }
        }
    }

    private final void changeTextColor() {
        float f = this.boxWidth;
        float f2 = this.boxHeight;
        PointF pointF = new PointF(this.toolPosition.x, this.toolPosition.y);
        this.textPaint.setColor(this.toolPaint.getPreviewColor());
        this.toolPosition.set(pointF);
        this.boxWidth = f;
        this.boxHeight = f2;
        this.workspace.invalidate();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void onClickOnButton() {
        highlightBox();
        this.commandManager.addCommand(this.commandFactory.createTextToolCommand(getMultilineText(), this.textPaint, 20, this.boxWidth, this.boxHeight, new PointF(this.toolPosition.x, this.toolPosition.y), this.boxRotation, new SerializableTypeface(this.font, this.bold, this.underlined, this.italic, this.textPaint.getTextSize(), this.textPaint.getTextSkewX())));
    }

    public final void resetBoxPosition() {
        if (this.workspace.getScale() <= 1.0f) {
            this.toolPosition.x = this.workspace.getWidth() / 2.0f;
            this.toolPosition.y = (this.boxHeight / 2.0f) + 200.0f;
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintColor(int color, boolean invalidate) {
        super.changePaintColor(color, invalidate);
        changeTextColor();
    }
}
