package org.catrobat.paintroid.tools.implementation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.ImageButton;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.test.espresso.idling.CountingIdlingResource;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.command.Command;
import org.catrobat.paintroid.command.CommandManager;
import org.catrobat.paintroid.command.serialization.SerializablePath;
import org.catrobat.paintroid.tools.ContextCallback;
import org.catrobat.paintroid.tools.ToolPaint;
import org.catrobat.paintroid.tools.ToolType;
import org.catrobat.paintroid.tools.Workspace;
import org.catrobat.paintroid.tools.common.CommonBrushChangedListener;
import org.catrobat.paintroid.tools.common.CommonBrushPreviewListener;
import org.catrobat.paintroid.tools.options.BrushToolOptionsView;
import org.catrobat.paintroid.tools.options.ToolOptionsViewController;
import org.catrobat.paintroid.ui.viewholder.TopBarViewHolder;

/* JADX INFO: compiled from: LineTool.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0014\u0018\u0000 t2\u00020\u0001:\u0001tBE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011¢\u0006\u0002\u0010\u0012J\u0010\u0010M\u001a\u00020\u00142\u0006\u0010N\u001a\u00020\u001dH\u0002J\u0018\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u0002002\u0006\u0010R\u001a\u00020\u0014H\u0016J\u0010\u0010S\u001a\u00020P2\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020P2\u0006\u0010W\u001a\u000200H\u0016J\u0010\u0010X\u001a\u00020P2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0010\u0010[\u001a\u00020P2\u0006\u0010Y\u001a\u00020ZH\u0016J\u0010\u0010\\\u001a\u00020\u00142\b\u0010N\u001a\u0004\u0018\u00010\u001dJ\u0012\u0010]\u001a\u00020\u00142\b\u0010N\u001a\u0004\u0018\u00010\u001dH\u0016J\u0012\u0010^\u001a\u00020P2\b\u0010N\u001a\u0004\u0018\u00010\u001dH\u0016J \u0010_\u001a\u00020\u00142\u0006\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020a2\b\b\u0002\u0010c\u001a\u00020\u0014J\u001a\u0010d\u001a\u00020\u00142\b\u0010N\u001a\u0004\u0018\u00010\u001d2\u0006\u0010e\u001a\u00020\u0014H\u0016J\u001e\u0010f\u001a\u00020\u00142\u0006\u0010N\u001a\u00020\u001d2\u0006\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020aJ\u0016\u0010g\u001a\u00020\u00142\u0006\u0010`\u001a\u00020a2\u0006\u0010b\u001a\u00020aJ\u0006\u0010h\u001a\u00020PJ\u0012\u0010i\u001a\u00020\u00142\b\u0010N\u001a\u0004\u0018\u00010\u001dH\u0016J\u0012\u0010j\u001a\u00020P2\b\u0010N\u001a\u0004\u0018\u00010\u001dH\u0016J\b\u0010k\u001a\u00020PH\u0002J\b\u0010l\u001a\u00020PH\u0016J\u0006\u0010m\u001a\u00020PJ\u0006\u0010n\u001a\u00020PJ\b\u0010o\u001a\u00020PH\u0014J\b\u0010p\u001a\u00020PH\u0002J\u0010\u0010q\u001a\u00020\u001d2\u0006\u0010N\u001a\u00020\u001dH\u0016J\u0016\u0010r\u001a\u00020P2\u0006\u0010Q\u001a\u0002002\u0006\u0010R\u001a\u00020\u0014J\u0018\u0010s\u001a\u00020P2\u0006\u0010Q\u001a\u0002002\b\b\u0002\u0010R\u001a\u00020\u0014R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0016\"\u0004\b\u001b\u0010\u0018R\u001c\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\u0010\u001a\u00020\u0011X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u001f\"\u0004\b(\u0010!R\u001a\u0010)\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0016\"\u0004\b+\u0010\u0018R\u001c\u0010,\u001a\u0004\u0018\u00010\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001f\"\u0004\b.\u0010!R\u001a\u0010/\u001a\u000200X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u001a\u00105\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0016\"\u0004\b7\u0010\u0018R\u001c\u00108\u001a\u0004\u0018\u00010\u001dX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u001f\"\u0004\b:\u0010!R\u001a\u0010;\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u0016\"\u0004\b=\u0010\u0018R\u001a\u0010>\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u0016\"\u0004\b@\u0010\u0018R\u001a\u0010A\u001a\u00020BX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u001a\u0010G\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u0016\"\u0004\bI\u0010\u0018R\u001a\u0010J\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\u0016\"\u0004\bL\u0010\u0018¨\u0006u"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/LineTool;", "Lorg/catrobat/paintroid/tools/implementation/BaseToolWithShape;", "brushToolOptionsView", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "contextCallback", "Lorg/catrobat/paintroid/tools/ContextCallback;", "toolOptionsViewController", "Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;", "toolPaint", "Lorg/catrobat/paintroid/tools/ToolPaint;", "workspace", "Lorg/catrobat/paintroid/tools/Workspace;", "idlingResource", "Landroidx/test/espresso/idling/CountingIdlingResource;", "commandManager", "Lorg/catrobat/paintroid/command/CommandManager;", "drawTime", "", "(Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;Lorg/catrobat/paintroid/tools/ContextCallback;Lorg/catrobat/paintroid/tools/options/ToolOptionsViewController;Lorg/catrobat/paintroid/tools/ToolPaint;Lorg/catrobat/paintroid/tools/Workspace;Landroidx/test/espresso/idling/CountingIdlingResource;Lorg/catrobat/paintroid/command/CommandManager;J)V", "changeInitialCoordinateForHandleNormalLine", "", "getChangeInitialCoordinateForHandleNormalLine", "()Z", "setChangeInitialCoordinateForHandleNormalLine", "(Z)V", "connectedLines", "getConnectedLines", "setConnectedLines", "currentCoordinate", "Landroid/graphics/PointF;", "getCurrentCoordinate", "()Landroid/graphics/PointF;", "setCurrentCoordinate", "(Landroid/graphics/PointF;)V", "getDrawTime", "()J", "setDrawTime", "(J)V", "endPointToDraw", "getEndPointToDraw", "setEndPointToDraw", "endpointSet", "getEndpointSet", "setEndpointSet", "initialEventCoordinate", "getInitialEventCoordinate", "setInitialEventCoordinate", "lastSetStrokeWidth", "", "getLastSetStrokeWidth", "()I", "setLastSetStrokeWidth", "(I)V", "lineFinalized", "getLineFinalized", "setLineFinalized", "startPointToDraw", "getStartPointToDraw", "setStartPointToDraw", "startpointSet", "getStartpointSet", "setStartpointSet", "toolSwitched", "getToolSwitched", "setToolSwitched", "toolType", "Lorg/catrobat/paintroid/tools/ToolType;", "getToolType", "()Lorg/catrobat/paintroid/tools/ToolType;", "setToolType", "(Lorg/catrobat/paintroid/tools/ToolType;)V", "undoPreviousLineForConnectedLines", "getUndoPreviousLineForConnectedLines", "setUndoPreviousLineForConnectedLines", "undoRecentlyClicked", "getUndoRecentlyClicked", "setUndoRecentlyClicked", "addPointCommand", "coordinate", "changePaintColor", "", TypedValues.Custom.S_COLOR, "invalidate", "changePaintStrokeCap", "cap", "Landroid/graphics/Paint$Cap;", "changePaintStrokeWidth", "strokeWidth", "draw", "canvas", "Landroid/graphics/Canvas;", "drawShape", "handleAddedLine", "handleDown", "handleDownAnimations", "handleEndPoint", "xDistance", "", "yDistance", "fromHandleLine", "handleMove", "shouldAnimate", "handleNormalLine", "handleStartPoint", "handleStateBeforeUndo", "handleUp", "handleUpAnimations", "hideToolOptions", "onClickOnButton", "onClickOnPlus", "redoLineTool", "resetInternalState", "showToolOptions", "toolPositionCoordinates", "undoChangePaintColor", "undoColorChangedCommand", "Companion", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class LineTool extends BaseToolWithShape {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static TopBarViewHolder topBarViewHolder;
    private final BrushToolOptionsView brushToolOptionsView;
    private boolean changeInitialCoordinateForHandleNormalLine;
    private boolean connectedLines;
    private PointF currentCoordinate;
    private long drawTime;
    private PointF endPointToDraw;
    private boolean endpointSet;
    private PointF initialEventCoordinate;
    private int lastSetStrokeWidth;
    private boolean lineFinalized;
    private PointF startPointToDraw;
    private boolean startpointSet;
    private boolean toolSwitched;
    private ToolType toolType;
    private boolean undoPreviousLineForConnectedLines;
    private boolean undoRecentlyClicked;

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void drawShape(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
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
    public LineTool(BrushToolOptionsView brushToolOptionsView, ContextCallback contextCallback, ToolOptionsViewController toolOptionsViewController, ToolPaint toolPaint, Workspace workspace, CountingIdlingResource idlingResource, CommandManager commandManager, long j) {
        ImageButton plusButton;
        TopBarViewHolder topBarViewHolder2;
        super(contextCallback, toolOptionsViewController, toolPaint, workspace, idlingResource, commandManager);
        Intrinsics.checkNotNullParameter(brushToolOptionsView, "brushToolOptionsView");
        Intrinsics.checkNotNullParameter(contextCallback, "contextCallback");
        Intrinsics.checkNotNullParameter(toolOptionsViewController, "toolOptionsViewController");
        Intrinsics.checkNotNullParameter(toolPaint, "toolPaint");
        Intrinsics.checkNotNullParameter(workspace, "workspace");
        Intrinsics.checkNotNullParameter(idlingResource, "idlingResource");
        Intrinsics.checkNotNullParameter(commandManager, "commandManager");
        this.brushToolOptionsView = brushToolOptionsView;
        this.drawTime = j;
        this.toolType = ToolType.LINE;
        this.undoPreviousLineForConnectedLines = true;
        brushToolOptionsView.setBrushChangedListener(new CommonBrushChangedListener(this));
        brushToolOptionsView.setBrushPreviewListener(new CommonBrushPreviewListener(toolPaint, getToolType()));
        brushToolOptionsView.setCurrentPaint(toolPaint.getBitmapPaint());
        brushToolOptionsView.setStrokeCapButtonChecked(toolPaint.getStrokeCap());
        TopBarViewHolder topBarViewHolder3 = topBarViewHolder;
        if (topBarViewHolder3 == null || topBarViewHolder3 == null || (plusButton = topBarViewHolder3.getPlusButton()) == null || plusButton.getVisibility() != 0 || (topBarViewHolder2 = topBarViewHolder) == null) {
            return;
        }
        topBarViewHolder2.hidePlusButton();
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public ToolType getToolType() {
        return this.toolType;
    }

    public void setToolType(ToolType toolType) {
        Intrinsics.checkNotNullParameter(toolType, "<set-?>");
        this.toolType = toolType;
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleUpAnimations(PointF coordinate) {
        super.handleUp(coordinate);
    }

    @Override // org.catrobat.paintroid.tools.Tool
    public void handleDownAnimations(PointF coordinate) {
        super.handleDown(coordinate);
    }

    public final boolean getLineFinalized() {
        return this.lineFinalized;
    }

    public final void setLineFinalized(boolean z) {
        this.lineFinalized = z;
    }

    public final boolean getEndpointSet() {
        return this.endpointSet;
    }

    public final void setEndpointSet(boolean z) {
        this.endpointSet = z;
    }

    public final boolean getStartpointSet() {
        return this.startpointSet;
    }

    public final void setStartpointSet(boolean z) {
        this.startpointSet = z;
    }

    public final PointF getInitialEventCoordinate() {
        return this.initialEventCoordinate;
    }

    public final void setInitialEventCoordinate(PointF pointF) {
        this.initialEventCoordinate = pointF;
    }

    public final PointF getStartPointToDraw() {
        return this.startPointToDraw;
    }

    public final void setStartPointToDraw(PointF pointF) {
        this.startPointToDraw = pointF;
    }

    public final PointF getEndPointToDraw() {
        return this.endPointToDraw;
    }

    public final void setEndPointToDraw(PointF pointF) {
        this.endPointToDraw = pointF;
    }

    public final PointF getCurrentCoordinate() {
        return this.currentCoordinate;
    }

    public final void setCurrentCoordinate(PointF pointF) {
        this.currentCoordinate = pointF;
    }

    public final boolean getToolSwitched() {
        return this.toolSwitched;
    }

    public final void setToolSwitched(boolean z) {
        this.toolSwitched = z;
    }

    public final int getLastSetStrokeWidth() {
        return this.lastSetStrokeWidth;
    }

    public final void setLastSetStrokeWidth(int i) {
        this.lastSetStrokeWidth = i;
    }

    public final boolean getConnectedLines() {
        return this.connectedLines;
    }

    public final void setConnectedLines(boolean z) {
        this.connectedLines = z;
    }

    public final boolean getUndoRecentlyClicked() {
        return this.undoRecentlyClicked;
    }

    public final void setUndoRecentlyClicked(boolean z) {
        this.undoRecentlyClicked = z;
    }

    public final boolean getUndoPreviousLineForConnectedLines() {
        return this.undoPreviousLineForConnectedLines;
    }

    public final void setUndoPreviousLineForConnectedLines(boolean z) {
        this.undoPreviousLineForConnectedLines = z;
    }

    public final boolean getChangeInitialCoordinateForHandleNormalLine() {
        return this.changeInitialCoordinateForHandleNormalLine;
    }

    public final void setChangeInitialCoordinateForHandleNormalLine(boolean z) {
        this.changeInitialCoordinateForHandleNormalLine = z;
    }

    /* JADX INFO: compiled from: LineTool.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/paintroid/tools/implementation/LineTool$Companion;", "", "()V", "topBarViewHolder", "Lorg/catrobat/paintroid/ui/viewholder/TopBarViewHolder;", "getTopBarViewHolder", "()Lorg/catrobat/paintroid/ui/viewholder/TopBarViewHolder;", "setTopBarViewHolder", "(Lorg/catrobat/paintroid/ui/viewholder/TopBarViewHolder;)V", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TopBarViewHolder getTopBarViewHolder() {
            return LineTool.topBarViewHolder;
        }

        public final void setTopBarViewHolder(TopBarViewHolder topBarViewHolder) {
            LineTool.topBarViewHolder = topBarViewHolder;
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void draw(Canvas canvas) {
        PointF currentCoordinate;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        PointF pointF = this.initialEventCoordinate;
        if (pointF == null || (currentCoordinate = getCurrentCoordinate()) == null) {
            return;
        }
        canvas.save();
        canvas.clipRect(0, 0, this.workspace.getWidth(), this.workspace.getHeight());
        canvas.drawLine(pointF.x, pointF.y, currentCoordinate.x, currentCoordinate.y, this.toolPaint.getPreviewPaint());
        canvas.restore();
    }

    public final void handleStateBeforeUndo() {
        if (!this.lineFinalized && this.startpointSet && !this.connectedLines) {
            this.startpointSet = false;
            this.startPointToDraw = null;
        } else {
            if (!this.undoRecentlyClicked) {
                this.endpointSet = false;
                this.endPointToDraw = null;
            } else {
                this.undoPreviousLineForConnectedLines = true;
                this.changeInitialCoordinateForHandleNormalLine = false;
                this.lineFinalized = true;
                resetInternalState();
            }
            this.undoRecentlyClicked = true;
        }
        TopBarViewHolder topBarViewHolder2 = topBarViewHolder;
        Intrinsics.checkNotNull(topBarViewHolder2);
        if (topBarViewHolder2.getPlusButton().getVisibility() != 0 || this.connectedLines) {
            return;
        }
        TopBarViewHolder topBarViewHolder3 = topBarViewHolder;
        Intrinsics.checkNotNull(topBarViewHolder3);
        topBarViewHolder3.getPlusButton().setVisibility(8);
    }

    public final void onClickOnPlus() {
        if (this.startpointSet && this.endpointSet) {
            PointF pointF = this.endPointToDraw;
            PointF pointF2 = pointF == null ? null : new PointF(pointF.x, pointF.y);
            PointF pointF3 = this.endPointToDraw;
            this.initialEventCoordinate = pointF3 == null ? null : new PointF(pointF3.x, pointF3.y);
            PointF pointF4 = this.endPointToDraw;
            this.previousEventCoordinate = pointF4 == null ? null : new PointF(pointF4.x, pointF4.y);
            this.startPointToDraw = null;
            this.endPointToDraw = null;
            this.startpointSet = false;
            this.endpointSet = false;
            this.lineFinalized = false;
            this.connectedLines = true;
            this.undoRecentlyClicked = false;
            handleAddedLine(pointF2);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseToolWithShape
    public void onClickOnButton() {
        ImageButton plusButton;
        TopBarViewHolder topBarViewHolder2;
        TopBarViewHolder topBarViewHolder3 = topBarViewHolder;
        if (topBarViewHolder3 != null && topBarViewHolder3 != null && (plusButton = topBarViewHolder3.getPlusButton()) != null && plusButton.getVisibility() == 0 && (topBarViewHolder2 = topBarViewHolder) != null) {
            topBarViewHolder2.hidePlusButton();
        }
        this.undoRecentlyClicked = false;
        boolean z = this.startpointSet;
        if (z && this.endpointSet) {
            if (this.toolSwitched) {
                PointF pointF = this.startPointToDraw;
                Float fValueOf = pointF == null ? null : Float.valueOf(pointF.x);
                PointF pointF2 = this.startPointToDraw;
                Float fValueOf2 = pointF2 == null ? null : Float.valueOf(pointF2.y);
                PointF pointF3 = this.endPointToDraw;
                Float fValueOf3 = pointF3 == null ? null : Float.valueOf(pointF3.x);
                PointF pointF4 = this.endPointToDraw;
                Float fValueOf4 = pointF4 != null ? Float.valueOf(pointF4.y) : null;
                SerializablePath serializablePath = new SerializablePath();
                if (fValueOf != null && fValueOf2 != null && fValueOf3 != null && fValueOf4 != null) {
                    serializablePath.moveTo(fValueOf.floatValue(), fValueOf2.floatValue());
                    serializablePath.lineTo(fValueOf3.floatValue(), fValueOf4.floatValue());
                }
                this.lineFinalized = true;
                this.toolSwitched = false;
                this.commandManager.addCommand(this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), serializablePath));
            }
            this.lineFinalized = true;
            resetInternalState();
            return;
        }
        if (z && !this.endpointSet) {
            if (this.commandManager.isUndoAvailable()) {
                this.commandManager.undoIgnoringColorChanges();
            }
            this.lineFinalized = true;
            resetInternalState();
            return;
        }
        resetInternalState();
    }

    private final void hideToolOptions() {
        if (this.toolOptionsViewController.isVisible()) {
            if (this.brushToolOptionsView.getTopLayout().getVisibility() == 0) {
                this.toolOptionsViewController.slideUp(this.brushToolOptionsView.getTopLayout(), true, false);
            }
            if (this.brushToolOptionsView.getBottomLayout().getVisibility() == 0) {
                this.toolOptionsViewController.slideDown(this.brushToolOptionsView.getBottomLayout(), true, false);
            }
        }
    }

    private final void showToolOptions() {
        if (this.toolOptionsViewController.isVisible()) {
            return;
        }
        if (this.brushToolOptionsView.getBottomLayout().getVisibility() == 4) {
            this.toolOptionsViewController.slideDown(this.brushToolOptionsView.getTopLayout(), false, true);
        }
        if (this.brushToolOptionsView.getBottomLayout().getVisibility() == 4) {
            this.toolOptionsViewController.slideUp(this.brushToolOptionsView.getBottomLayout(), false, true);
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleDown(PointF coordinate) {
        if (coordinate == null) {
            return false;
        }
        super.handleDown(coordinate);
        this.initialEventCoordinate = new PointF(coordinate.x, coordinate.y);
        this.previousEventCoordinate = new PointF(coordinate.x, coordinate.y);
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleMove(PointF coordinate, boolean shouldAnimate) {
        if (coordinate == null) {
            return false;
        }
        super.handleMove(coordinate, shouldAnimate);
        if (shouldAnimate) {
            hideToolOptions();
        }
        this.changeInitialCoordinateForHandleNormalLine = true;
        if (this.startpointSet) {
            PointF pointF = this.startPointToDraw;
            this.initialEventCoordinate = pointF == null ? null : new PointF(pointF.x, pointF.y);
            PointF pointF2 = this.startPointToDraw;
            this.previousEventCoordinate = pointF2 != null ? new PointF(pointF2.x, pointF2.y) : null;
            if (this.undoPreviousLineForConnectedLines && this.commandManager.isUndoAvailable() && !this.undoRecentlyClicked) {
                this.undoRecentlyClicked = false;
                this.commandManager.undoIgnoringColorChanges();
            }
            this.undoPreviousLineForConnectedLines = false;
            this.undoRecentlyClicked = false;
        }
        this.currentCoordinate = new PointF(coordinate.x, coordinate.y);
        return true;
    }

    public final boolean handleStartPoint(float xDistance, float yDistance) {
        PointF pointF = this.previousEventCoordinate;
        PointF pointF2 = pointF == null ? null : new PointF(pointF.x, pointF.y);
        this.startPointToDraw = pointF2;
        if (pointF2 != null) {
            pointF2.x = (pointF2 == null ? null : Float.valueOf(pointF2.x - xDistance)).floatValue();
        }
        PointF pointF3 = this.startPointToDraw;
        if (pointF3 != null) {
            pointF3.y = (pointF3 != null ? Float.valueOf(pointF3.y - yDistance) : null).floatValue();
        }
        PointF pointF4 = this.startPointToDraw;
        if (pointF4 != null && this.workspace.contains(pointF4)) {
            this.startpointSet = true;
            this.undoRecentlyClicked = false;
            resetInternalState();
            PointF pointF5 = this.startPointToDraw;
            if (pointF5 != null) {
                return addPointCommand(pointF5);
            }
        } else {
            this.lineFinalized = true;
            resetInternalState();
        }
        return true;
    }

    public static /* synthetic */ boolean handleEndPoint$default(LineTool lineTool, float f, float f2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return lineTool.handleEndPoint(f, f2, z);
    }

    public final boolean handleEndPoint(float xDistance, float yDistance, boolean fromHandleLine) {
        TopBarViewHolder topBarViewHolder2;
        ImageButton plusButton;
        PointF pointF = this.previousEventCoordinate;
        if (pointF != null && !this.workspace.contains(pointF)) {
            return false;
        }
        PointF pointF2 = this.previousEventCoordinate;
        PointF pointF3 = pointF2 == null ? null : new PointF(pointF2.x, pointF2.y);
        this.endPointToDraw = pointF3;
        if (pointF3 != null) {
            pointF3.x = (pointF3 == null ? null : Float.valueOf(pointF3.x - xDistance)).floatValue();
        }
        PointF pointF4 = this.endPointToDraw;
        if (pointF4 != null) {
            pointF4.y = (pointF4 == null ? null : Float.valueOf(pointF4.y - yDistance)).floatValue();
        }
        this.endpointSet = true;
        PointF pointF5 = this.startPointToDraw;
        Float fValueOf = pointF5 == null ? null : Float.valueOf(pointF5.x);
        PointF pointF6 = this.startPointToDraw;
        Float fValueOf2 = pointF6 == null ? null : Float.valueOf(pointF6.y);
        PointF pointF7 = this.endPointToDraw;
        Float fValueOf3 = pointF7 == null ? null : Float.valueOf(pointF7.x);
        PointF pointF8 = this.endPointToDraw;
        Float fValueOf4 = pointF8 != null ? Float.valueOf(pointF8.y) : null;
        SerializablePath serializablePath = new SerializablePath();
        if (fValueOf != null && fValueOf2 != null && fValueOf3 != null && fValueOf4 != null) {
            serializablePath.moveTo(fValueOf.floatValue(), fValueOf2.floatValue());
            serializablePath.lineTo(fValueOf3.floatValue(), fValueOf4.floatValue());
        }
        Command commandCreatePathCommand = this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), serializablePath);
        if (!fromHandleLine && !this.undoRecentlyClicked) {
            if (this.commandManager.isUndoAvailable() && !this.undoRecentlyClicked) {
                this.commandManager.undoIgnoringColorChangesAndAddCommand(commandCreatePathCommand);
            }
        } else {
            this.commandManager.addCommand(commandCreatePathCommand);
        }
        this.undoRecentlyClicked = false;
        resetInternalState();
        TopBarViewHolder topBarViewHolder3 = topBarViewHolder;
        if (topBarViewHolder3 != null && ((topBarViewHolder3 == null || (plusButton = topBarViewHolder3.getPlusButton()) == null || plusButton.getVisibility() != 0) && (topBarViewHolder2 = topBarViewHolder) != null)) {
            topBarViewHolder2.showPlusButton();
        }
        return true;
    }

    public final boolean handleNormalLine(PointF coordinate, float xDistance, float yDistance) {
        TopBarViewHolder topBarViewHolder2;
        ImageButton plusButton;
        Intrinsics.checkNotNullParameter(coordinate, "coordinate");
        RectF rectF = new RectF();
        if (this.startpointSet) {
            return handleEndPoint(xDistance, yDistance, true);
        }
        SerializablePath serializablePath = new SerializablePath();
        PointF initialEventCoordinate = getInitialEventCoordinate();
        Float fValueOf = initialEventCoordinate == null ? null : Float.valueOf(initialEventCoordinate.x);
        if (fValueOf == null) {
            return false;
        }
        float fFloatValue = fValueOf.floatValue();
        PointF initialEventCoordinate2 = getInitialEventCoordinate();
        Float fValueOf2 = initialEventCoordinate2 == null ? null : Float.valueOf(initialEventCoordinate2.y);
        if (fValueOf2 == null) {
            return false;
        }
        serializablePath.moveTo(fFloatValue, fValueOf2.floatValue());
        serializablePath.lineTo(coordinate.x, coordinate.y);
        serializablePath.computeBounds(rectF, true);
        rectF.inset(-this.toolPaint.getStrokeWidth(), -this.toolPaint.getStrokeWidth());
        PointF pointF = this.previousEventCoordinate;
        if (pointF != null) {
            PointF pointF2 = this.previousEventCoordinate;
            pointF.x = (pointF2 == null ? null : Float.valueOf(pointF2.x - xDistance)).floatValue();
        }
        PointF pointF3 = this.previousEventCoordinate;
        if (pointF3 != null) {
            PointF pointF4 = this.previousEventCoordinate;
            pointF3.y = (pointF4 == null ? null : Float.valueOf(pointF4.y - yDistance)).floatValue();
        }
        PointF pointF5 = this.initialEventCoordinate;
        this.startPointToDraw = pointF5 == null ? null : new PointF(pointF5.x, pointF5.y);
        PointF pointF6 = this.previousEventCoordinate;
        this.endPointToDraw = pointF6 != null ? new PointF(pointF6.x, pointF6.y) : null;
        this.endpointSet = true;
        this.startpointSet = true;
        this.undoRecentlyClicked = false;
        TopBarViewHolder topBarViewHolder3 = topBarViewHolder;
        if (topBarViewHolder3 != null && ((topBarViewHolder3 == null || (plusButton = topBarViewHolder3.getPlusButton()) == null || plusButton.getVisibility() != 0) && (topBarViewHolder2 = topBarViewHolder) != null)) {
            topBarViewHolder2.showPlusButton();
        }
        if (this.workspace.intersectsWith(rectF)) {
            this.commandManager.addCommand(this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), serializablePath));
        }
        resetInternalState();
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public boolean handleUp(PointF coordinate) {
        showToolOptions();
        super.handleUp(coordinate);
        return handleAddedLine(coordinate);
    }

    public final boolean handleAddedLine(PointF coordinate) {
        this.undoPreviousLineForConnectedLines = true;
        if (this.changeInitialCoordinateForHandleNormalLine && this.initialEventCoordinate == null) {
            PointF pointF = this.startPointToDraw;
            this.initialEventCoordinate = pointF == null ? null : new PointF(pointF.x, pointF.y);
        }
        if (this.initialEventCoordinate == null || this.previousEventCoordinate == null || coordinate == null) {
            this.changeInitialCoordinateForHandleNormalLine = false;
            return false;
        }
        PointF pointF2 = this.initialEventCoordinate;
        Float fValueOf = pointF2 == null ? null : Float.valueOf(pointF2.x - coordinate.x);
        PointF pointF3 = this.initialEventCoordinate;
        Float fValueOf2 = pointF3 != null ? Float.valueOf(pointF3.y - coordinate.y) : null;
        if (fValueOf != null && fValueOf2 != null) {
            if (this.changeInitialCoordinateForHandleNormalLine) {
                this.changeInitialCoordinateForHandleNormalLine = false;
                return handleNormalLine(coordinate, fValueOf.floatValue(), fValueOf2.floatValue());
            }
            if (!this.startpointSet) {
                return handleStartPoint(fValueOf.floatValue(), fValueOf2.floatValue());
            }
            return handleEndPoint$default(this, fValueOf.floatValue(), fValueOf2.floatValue(), false, 4, null);
        }
        this.changeInitialCoordinateForHandleNormalLine = false;
        return true;
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool
    protected void resetInternalState() {
        this.initialEventCoordinate = null;
        this.currentCoordinate = null;
        if (this.lineFinalized) {
            this.connectedLines = false;
            this.startPointToDraw = null;
            this.endPointToDraw = null;
            this.startpointSet = false;
            this.endpointSet = false;
            this.lineFinalized = false;
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintColor(int color, boolean invalidate) {
        PointF pointF;
        super.changePaintColor(color, invalidate);
        boolean z = this.startpointSet;
        if (z && this.endpointSet) {
            PointF pointF2 = this.startPointToDraw;
            Float fValueOf = pointF2 == null ? null : Float.valueOf(pointF2.x);
            PointF pointF3 = this.startPointToDraw;
            Float fValueOf2 = pointF3 == null ? null : Float.valueOf(pointF3.y);
            PointF pointF4 = this.endPointToDraw;
            Float fValueOf3 = pointF4 == null ? null : Float.valueOf(pointF4.x);
            PointF pointF5 = this.endPointToDraw;
            Float fValueOf4 = pointF5 != null ? Float.valueOf(pointF5.y) : null;
            if (this.commandManager.isUndoAvailable()) {
                SerializablePath serializablePath = new SerializablePath();
                if (fValueOf != null && fValueOf2 != null && fValueOf3 != null && fValueOf4 != null) {
                    serializablePath.moveTo(fValueOf.floatValue(), fValueOf2.floatValue());
                    serializablePath.lineTo(fValueOf3.floatValue(), fValueOf4.floatValue());
                }
                this.commandManager.undoIgnoringColorChangesAndAddCommand(this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), serializablePath));
            }
        } else if (z && !this.endpointSet && !this.lineFinalized && this.commandManager.isUndoAvailable() && !this.undoRecentlyClicked && (pointF = this.startPointToDraw) != null) {
            this.commandManager.undoIgnoringColorChangesAndAddCommand(this.commandFactory.createPointCommand(getDrawPaint(), pointF));
        }
        if (invalidate) {
            this.brushToolOptionsView.invalidate();
        }
    }

    public final void undoChangePaintColor(int color, boolean invalidate) {
        handleStateBeforeUndo();
        super.changePaintColor(color, invalidate);
        if (invalidate) {
            this.brushToolOptionsView.invalidate();
        }
        if (this.connectedLines) {
            this.commandManager.undoInConnectedLinesMode();
        } else {
            this.commandManager.undo();
        }
    }

    public final void redoLineTool() {
        this.undoRecentlyClicked = false;
        if (this.connectedLines) {
            this.commandManager.redoInConnectedLinesMode();
        } else {
            this.commandManager.redo();
        }
    }

    public static /* synthetic */ void undoColorChangedCommand$default(LineTool lineTool, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        lineTool.undoColorChangedCommand(i, z);
    }

    public final void undoColorChangedCommand(int color, boolean invalidate) {
        super.changePaintColor(color, invalidate);
        if (invalidate) {
            this.brushToolOptionsView.invalidate();
        }
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintStrokeWidth(int strokeWidth) {
        PointF pointF;
        super.changePaintStrokeWidth(strokeWidth);
        boolean z = this.lastSetStrokeWidth == strokeWidth;
        boolean z2 = this.startpointSet;
        if (z2 && this.endpointSet && !z) {
            PointF pointF2 = this.startPointToDraw;
            Float fValueOf = pointF2 == null ? null : Float.valueOf(pointF2.x);
            PointF pointF3 = this.startPointToDraw;
            Float fValueOf2 = pointF3 == null ? null : Float.valueOf(pointF3.y);
            PointF pointF4 = this.endPointToDraw;
            Float fValueOf3 = pointF4 == null ? null : Float.valueOf(pointF4.x);
            PointF pointF5 = this.endPointToDraw;
            Float fValueOf4 = pointF5 != null ? Float.valueOf(pointF5.y) : null;
            if (this.commandManager.isUndoAvailable()) {
                SerializablePath serializablePath = new SerializablePath();
                if (fValueOf != null && fValueOf2 != null && fValueOf3 != null && fValueOf4 != null) {
                    serializablePath.moveTo(fValueOf.floatValue(), fValueOf2.floatValue());
                    serializablePath.lineTo(fValueOf3.floatValue(), fValueOf4.floatValue());
                }
                this.commandManager.undoIgnoringColorChangesAndAddCommand(this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), serializablePath));
            }
        } else if (z2 && !this.endpointSet && !this.lineFinalized && !z && this.commandManager.isUndoAvailable() && !this.undoRecentlyClicked && (pointF = this.startPointToDraw) != null) {
            this.commandManager.undoIgnoringColorChangesAndAddCommand(this.commandFactory.createPointCommand(getDrawPaint(), pointF));
        }
        this.lastSetStrokeWidth = strokeWidth;
        this.brushToolOptionsView.invalidate();
    }

    @Override // org.catrobat.paintroid.tools.implementation.BaseTool, org.catrobat.paintroid.tools.Tool
    public void changePaintStrokeCap(Paint.Cap cap) {
        PointF pointF;
        Intrinsics.checkNotNullParameter(cap, "cap");
        super.changePaintStrokeCap(cap);
        boolean z = this.startpointSet;
        if (z && this.endpointSet) {
            PointF pointF2 = this.startPointToDraw;
            Float fValueOf = pointF2 == null ? null : Float.valueOf(pointF2.x);
            PointF pointF3 = this.startPointToDraw;
            Float fValueOf2 = pointF3 == null ? null : Float.valueOf(pointF3.y);
            PointF pointF4 = this.endPointToDraw;
            Float fValueOf3 = pointF4 == null ? null : Float.valueOf(pointF4.x);
            PointF pointF5 = this.endPointToDraw;
            Float fValueOf4 = pointF5 != null ? Float.valueOf(pointF5.y) : null;
            if (this.commandManager.isUndoAvailable()) {
                SerializablePath serializablePath = new SerializablePath();
                if (fValueOf != null && fValueOf2 != null && fValueOf3 != null && fValueOf4 != null) {
                    serializablePath.moveTo(fValueOf.floatValue(), fValueOf2.floatValue());
                    serializablePath.lineTo(fValueOf3.floatValue(), fValueOf4.floatValue());
                }
                this.commandManager.undoIgnoringColorChangesAndAddCommand(this.commandFactory.createPathCommand(this.toolPaint.getBitmapPaint(), serializablePath));
            }
        } else if (z && !this.endpointSet && !this.lineFinalized && this.commandManager.isUndoAvailable() && !this.undoRecentlyClicked && (pointF = this.startPointToDraw) != null) {
            this.commandManager.undoIgnoringColorChangesAndAddCommand(this.commandFactory.createPointCommand(getDrawPaint(), pointF));
        }
        this.brushToolOptionsView.invalidate();
    }

    private final boolean addPointCommand(PointF coordinate) {
        this.commandManager.addCommand(this.commandFactory.createPointCommand(getDrawPaint(), coordinate));
        return true;
    }
}
