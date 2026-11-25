package io.github.rosemoe.sora.widget;

import android.R;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.widget.EdgeEffect;
import androidx.collection.MutableIntList;
import androidx.core.view.ViewCompat;
import io.github.rosemoe.sora.graphics.BubbleHelper;
import io.github.rosemoe.sora.graphics.BufferedDrawPoints;
import io.github.rosemoe.sora.graphics.CharPosDesc;
import io.github.rosemoe.sora.graphics.GraphicCharacter;
import io.github.rosemoe.sora.graphics.GraphicTextRow;
import io.github.rosemoe.sora.lang.analysis.StyleUpdateRange;
import io.github.rosemoe.sora.lang.brackets.PairedBracket;
import io.github.rosemoe.sora.lang.completion.snippet.PlaceholderItem;
import io.github.rosemoe.sora.lang.completion.snippet.SnippetItem;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticRegion;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer;
import io.github.rosemoe.sora.lang.styling.CodeBlock;
import io.github.rosemoe.sora.lang.styling.EmptyReader;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.SpanFactory;
import io.github.rosemoe.sora.lang.styling.Spans;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.lang.styling.TextStyle;
import io.github.rosemoe.sora.lang.styling.color.ResolvableColor;
import io.github.rosemoe.sora.lang.styling.line.LineAnchorStyle;
import io.github.rosemoe.sora.lang.styling.line.LineBackground;
import io.github.rosemoe.sora.lang.styling.line.LineGutterBackground;
import io.github.rosemoe.sora.lang.styling.line.LineSideIcon;
import io.github.rosemoe.sora.lang.styling.line.LineStyles;
import io.github.rosemoe.sora.lang.styling.span.SpanExternalRenderer;
import io.github.rosemoe.sora.text.CachedIndexer;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.text.FunctionCharacters;
import io.github.rosemoe.sora.text.UnicodeIterator;
import io.github.rosemoe.sora.text.bidi.Directions;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.LongArrayList;
import io.github.rosemoe.sora.util.MutableInt;
import io.github.rosemoe.sora.util.Numbers;
import io.github.rosemoe.sora.util.RendererUtils;
import io.github.rosemoe.sora.util.TemporaryCharBuffer;
import io.github.rosemoe.sora.widget.layout.Layout;
import io.github.rosemoe.sora.widget.layout.Row;
import io.github.rosemoe.sora.widget.layout.RowIterator;
import io.github.rosemoe.sora.widget.layout.WordwrapLayout;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.snippet.SnippetController;
import io.github.rosemoe.sora.widget.style.DiagnosticIndicatorStyle;
import io.github.rosemoe.sora.widget.style.SelectionHandleStyle;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class EditorRenderer {
    private static final int[] DEFAULT_DRAWABLE_STATE;
    private static final String LOG_TAG = "EditorRenderer";
    private static final int[] PRESSED_DRAWABLE_STATE;
    private static final int[] sDiagnosticsColorMapping;
    private static final List<Span> sSpansForWordwrap;
    protected boolean basicDisplayMode;
    protected final BufferedDrawPoints bufferedDrawPoints;
    private int cachedGutterWidth;
    protected Content content;
    private Cursor cursor;
    private volatile long displayTimestamp;
    private final CodeEditor editor;
    protected boolean forcedRecreateLayout;
    private Drawable horizontalScrollbarThumbDrawable;
    private Drawable horizontalScrollbarTrackDrawable;
    protected List<CodeBlock> lastStuckLines;
    protected ContentLine lineBuf;
    private Paint.FontMetricsInt metricsGraph;
    private Paint.FontMetricsInt metricsLineNumber;
    Paint.FontMetricsInt metricsText;
    protected final io.github.rosemoe.sora.graphics.Paint paintGeneral;
    protected final io.github.rosemoe.sora.graphics.Paint paintGraph;
    protected final io.github.rosemoe.sora.graphics.Paint paintOther;
    protected RenderNodeHolder renderNodeHolder;
    private volatile boolean renderingFlag;
    private final Path tmpPath;
    private final RectF tmpRect;
    private Drawable verticalScrollbarThumbDrawable;
    private Drawable verticalScrollbarTrackDrawable;
    protected final Rect viewRect;
    private final LongArrayList postDrawLineNumbers = new LongArrayList();
    private final MutableIntList postDrawCurrentLines = new MutableIntList();
    private final LongArrayList matchedPositions = new LongArrayList();
    private final SparseArray<ContentLine> preloadedLines = new SparseArray<>();
    private final SparseArray<Directions> preloadedDirections = new SparseArray<>();
    private final List<DiagnosticRegion> collectedDiagnostics = new ArrayList();
    private final LineStyles coordinateLine = new LineStyles(0);
    private final RectF verticalScrollBarRect = new RectF();
    private final RectF horizontalScrollBarRect = new RectF();

    protected interface PatchDraw {
        void draw(Canvas canvas, float f, int i, int i2, int i3, int i4, long j);
    }

    static {
        ArrayList arrayList = new ArrayList();
        sSpansForWordwrap = arrayList;
        arrayList.add(SpanFactory.obtain(0, TextStyle.makeStyle(0, 0, true, true, false)));
        PRESSED_DRAWABLE_STATE = new int[]{R.attr.state_pressed, R.attr.state_enabled};
        DEFAULT_DRAWABLE_STATE = new int[]{R.attr.state_enabled};
        sDiagnosticsColorMapping = new int[]{0, 37, 36, 35};
    }

    public EditorRenderer(CodeEditor codeEditor) {
        this.editor = codeEditor;
        if (Build.VERSION.SDK_INT >= 29) {
            this.renderNodeHolder = new RenderNodeHolder(codeEditor);
        }
        this.bufferedDrawPoints = new BufferedDrawPoints();
        io.github.rosemoe.sora.graphics.Paint paint = new io.github.rosemoe.sora.graphics.Paint(codeEditor.isRenderFunctionCharacters());
        this.paintGeneral = paint;
        paint.setAntiAlias(true);
        io.github.rosemoe.sora.graphics.Paint paint2 = new io.github.rosemoe.sora.graphics.Paint(false);
        this.paintOther = paint2;
        paint2.setStrokeWidth(codeEditor.getDpUnit() * 1.8f);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setTypeface(Typeface.MONOSPACE);
        paint2.setAntiAlias(true);
        io.github.rosemoe.sora.graphics.Paint paint3 = new io.github.rosemoe.sora.graphics.Paint(false);
        this.paintGraph = paint3;
        paint3.setAntiAlias(true);
        this.metricsText = paint.getFontMetricsInt();
        this.metricsLineNumber = paint2.getFontMetricsInt();
        this.viewRect = new Rect();
        this.tmpRect = new RectF();
        this.tmpPath = new Path();
        onEditorFullTextUpdate();
    }

    public boolean isBasicDisplayMode() {
        return this.basicDisplayMode;
    }

    public void onEditorFullTextUpdate() {
        this.cursor = this.editor.getCursor();
        this.content = this.editor.getText();
    }

    public void draw(Canvas canvas) {
        int iSave = canvas.save();
        canvas.translate(this.editor.getOffsetX(), this.editor.getOffsetY());
        this.renderingFlag = true;
        try {
            drawView(canvas);
            this.renderingFlag = false;
            canvas.restoreToCount(iSave);
        } catch (Throwable th) {
            this.renderingFlag = false;
            throw th;
        }
    }

    public void onSizeChanged(int i, int i2) {
        this.viewRect.right = i;
        this.viewRect.bottom = i2;
    }

    io.github.rosemoe.sora.graphics.Paint getPaint() {
        return this.paintGeneral;
    }

    io.github.rosemoe.sora.graphics.Paint getPaintOther() {
        return this.paintOther;
    }

    io.github.rosemoe.sora.graphics.Paint getPaintGraph() {
        return this.paintGraph;
    }

    void setCachedLineNumberWidth(int i) {
        this.cachedGutterWidth = i;
    }

    public RectF getVerticalScrollBarRect() {
        return this.verticalScrollBarRect;
    }

    public RectF getHorizontalScrollBarRect() {
        return this.horizontalScrollBarRect;
    }

    public void setHorizontalScrollbarThumbDrawable(Drawable drawable) {
        this.horizontalScrollbarThumbDrawable = drawable;
    }

    public Drawable getHorizontalScrollbarThumbDrawable() {
        return this.horizontalScrollbarThumbDrawable;
    }

    public void setHorizontalScrollbarTrackDrawable(Drawable drawable) {
        this.horizontalScrollbarTrackDrawable = drawable;
    }

    public Drawable getHorizontalScrollbarTrackDrawable() {
        return this.horizontalScrollbarTrackDrawable;
    }

    public void setVerticalScrollbarThumbDrawable(Drawable drawable) {
        this.verticalScrollbarThumbDrawable = drawable;
    }

    public Drawable getVerticalScrollbarThumbDrawable() {
        return this.verticalScrollbarThumbDrawable;
    }

    public void setVerticalScrollbarTrackDrawable(Drawable drawable) {
        this.verticalScrollbarTrackDrawable = drawable;
    }

    public Drawable getVerticalScrollbarTrackDrawable() {
        return this.verticalScrollbarTrackDrawable;
    }

    public void setTextSizePxDirect(float f) {
        this.paintGeneral.setTextSizeWrapped(f);
        this.paintOther.setTextSize(f);
        io.github.rosemoe.sora.graphics.Paint paint = this.paintGraph;
        Objects.requireNonNull(this.editor.getProps());
        paint.setTextSize(f * 0.85f);
        this.metricsText = this.paintGeneral.getFontMetricsInt();
        this.metricsLineNumber = this.paintOther.getFontMetricsInt();
        this.metricsGraph = this.paintGraph.getFontMetricsInt();
        invalidateRenderNodes();
        updateTimestamp();
    }

    public void setTypefaceText(Typeface typeface) {
        if (typeface == null) {
            typeface = Typeface.DEFAULT;
        }
        this.paintGeneral.setTypefaceWrapped(typeface);
        this.metricsText = this.paintGeneral.getFontMetricsInt();
        invalidateRenderNodes();
        updateTimestamp();
        this.editor.createLayout();
        this.editor.invalidate();
    }

    public void setTypefaceLineNumber(Typeface typeface) {
        if (typeface == null) {
            typeface = Typeface.MONOSPACE;
        }
        this.paintOther.setTypeface(typeface);
        this.metricsLineNumber = this.paintOther.getFontMetricsInt();
        this.editor.invalidate();
    }

    public void setTextScaleX(float f) {
        this.paintGeneral.setTextScaleX(f);
        this.paintOther.setTextScaleX(f);
        onTextStyleUpdate();
    }

    public void setLetterSpacing(float f) {
        this.paintGeneral.setLetterSpacing(f);
        this.paintOther.setLetterSpacing(f);
        onTextStyleUpdate();
    }

    protected void onTextStyleUpdate() {
        this.paintGeneral.setRenderFunctionCharacters(this.editor.isRenderFunctionCharacters());
        this.metricsGraph = this.paintGraph.getFontMetricsInt();
        this.metricsLineNumber = this.paintOther.getFontMetricsInt();
        this.metricsText = this.paintGeneral.getFontMetricsInt();
        invalidateRenderNodes();
        updateTimestamp();
        this.editor.createLayout();
        this.editor.invalidate();
    }

    protected void updateTimestamp() {
        this.displayTimestamp = System.nanoTime();
    }

    protected void prepareLine(int i) {
        this.lineBuf = getLine(i);
    }

    protected ContentLine getLine(int i) {
        if (!this.renderingFlag) {
            return getLineDirect(i);
        }
        ContentLine contentLine = this.preloadedLines.get(i);
        if (contentLine != null) {
            return contentLine;
        }
        ContentLine line = this.content.getLine(i);
        this.preloadedLines.put(i, line);
        return line;
    }

    protected Directions getLineDirections(int i) {
        if (!this.renderingFlag) {
            return this.content.getLineDirections(i);
        }
        Directions directions = this.preloadedDirections.get(i);
        if (directions != null) {
            return directions;
        }
        Directions lineDirections = this.content.getLineDirections(i);
        this.preloadedDirections.put(i, lineDirections);
        return lineDirections;
    }

    ContentLine getLineDirect(int i) {
        return this.content.getLine(i);
    }

    int getColumnCount(int i) {
        return getLine(i).length();
    }

    public void invalidateRenderNodes() {
        if (this.renderNodeHolder == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        this.renderNodeHolder.invalidate();
    }

    public void invalidateInRegion(int i, int i2) {
        if (this.renderNodeHolder == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        this.renderNodeHolder.invalidateInRegion(i, i2);
    }

    public void invalidateInRegion(StyleUpdateRange styleUpdateRange) {
        if (this.renderNodeHolder == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        this.renderNodeHolder.invalidateInRegion(styleUpdateRange);
    }

    public void invalidateChanged(int i) {
        if (this.renderNodeHolder == null || Build.VERSION.SDK_INT < 29 || this.cursor == null || !this.renderNodeHolder.invalidateInRegion(i, Integer.MAX_VALUE)) {
            return;
        }
        this.editor.invalidate();
    }

    public void invalidateOnInsert(int i, int i2) {
        if (this.renderNodeHolder == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        this.renderNodeHolder.afterInsert(i, i2);
    }

    public void invalidateOnDelete(int i, int i2) {
        if (this.renderNodeHolder == null || Build.VERSION.SDK_INT < 29) {
            return;
        }
        this.renderNodeHolder.afterDelete(i, i2);
    }

    protected void updateLineDisplayList(RenderNode renderNode, int i, Spans.Reader reader) {
        renderNode.setPosition(0, 0, (int) (measureText(this.lineBuf, i, 0, getColumnCount(i)) + (this.editor.getDpUnit() * 20.0f) + (this.paintGraph.measureText("↵") * 1.5f)), this.editor.getRowHeight());
        try {
            drawSingleTextLine(renderNode.beginRecording(), i, 0.0f, 0.0f, reader, false);
        } finally {
            renderNode.endRecording();
        }
    }

    protected void drawSingleTextLine(Canvas canvas, int i, float f, float f2, Spans.Reader reader, boolean z) {
        boolean z2;
        float f3;
        long j;
        int i2;
        Span span;
        Spans.Reader reader2;
        int i3;
        float f4;
        int iResolve;
        int i4;
        Spans.Reader reader3;
        int i5 = i;
        prepareLine(i5);
        canvas.save();
        float f5 = 0.0f;
        canvas.translate(0.0f, f2);
        int columnCount = getColumnCount(i5);
        Spans.Reader emptyReader = (reader == null || reader.getSpanCount() <= 0) ? EmptyReader.getInstance() : reader;
        boolean z3 = false;
        long j2 = 0;
        float f6 = f;
        Span spanAt = emptyReader.getSpanAt(0);
        int i6 = 0;
        while (true) {
            if (columnCount <= spanAt.getColumn()) {
                z2 = z3;
                break;
            }
            int i7 = i6 + 1;
            int iMin = Math.min(columnCount, i7 >= emptyReader.getSpanCount() ? columnCount : emptyReader.getSpanAt(i7).getColumn());
            int column = spanAt.getColumn();
            int iMin2 = Math.min(columnCount, iMin);
            float fMeasureText = measureText(this.lineBuf, i5, column, iMin2 - column);
            float f7 = f6 + fMeasureText;
            if (f7 > f5 || !z) {
                SpanExternalRenderer spanExternalRenderer = (SpanExternalRenderer) spanAt.getSpanExt(1);
                Spans.Reader reader4 = emptyReader;
                int i8 = columnCount;
                if (spanExternalRenderer == null || !spanExternalRenderer.requirePreDraw()) {
                    f3 = fMeasureText;
                } else {
                    int iSave = canvas.save();
                    canvas.translate(f6, this.editor.getRowTop(0));
                    canvas.clipRect(0.0f, 0.0f, fMeasureText, this.editor.getRowHeight());
                    try {
                        f3 = fMeasureText;
                        try {
                            spanExternalRenderer.draw(canvas, this.paintGeneral, this.editor.getColorScheme(), true);
                        } catch (Exception e) {
                            e = e;
                            Log.e(LOG_TAG, "Error while invoking external renderer", e);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        f3 = fMeasureText;
                    }
                    canvas.restoreToCount(iSave);
                }
                long styleBits = spanAt.getStyleBits();
                if (spanAt.getStyleBits() != j2) {
                    this.paintGeneral.setFakeBoldText(TextStyle.isBold(styleBits));
                    if (TextStyle.isItalics(styleBits)) {
                        this.paintGeneral.setTextSkewX(-0.2f);
                    } else {
                        this.paintGeneral.setTextSkewX(0.0f);
                    }
                    j = styleBits;
                } else {
                    j = j2;
                }
                int backgroundColor = RendererUtils.getBackgroundColor(spanAt, this.editor.getColorScheme());
                if (backgroundColor != 0 && column != iMin2) {
                    this.tmpRect.set(f6, this.editor.getRowTop(0), f7, this.editor.getRowBottom(0));
                    this.paintGeneral.setColor(backgroundColor);
                    canvas.drawRoundRect(this.tmpRect, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.paintGeneral);
                }
                i2 = iMin2;
                span = spanAt;
                float f8 = f6;
                float f9 = f3;
                z2 = false;
                reader2 = reader4;
                i3 = i8;
                drawRegionTextDirectional(canvas, f6, this.editor.getRowBaseline(0), i, column, i2, spanAt.getColumn(), iMin, i8, RendererUtils.getForegroundColor(spanAt, this.editor.getColorScheme()));
                if (TextStyle.isStrikeThrough(span.getStyle())) {
                    int color = this.editor.getColorScheme().getColor(57);
                    io.github.rosemoe.sora.graphics.Paint paint = this.paintOther;
                    if (color == 0) {
                        color = this.paintGeneral.getColor();
                    }
                    paint.setColor(color);
                    canvas.drawLine(f8, this.editor.getRowTop(0) + (this.editor.getRowHeight() / 2.0f), f7, this.editor.getRowTop(0) + (this.editor.getRowHeight() / 2.0f), this.paintOther);
                }
                ResolvableColor underlineColor = span.getUnderlineColor();
                if (underlineColor == null || (iResolve = underlineColor.resolve(this.editor.getColorScheme())) == 0) {
                    f4 = f8;
                } else {
                    this.tmpRect.bottom = this.editor.getRowBottom(0) - (this.editor.getDpUnit() * 1.0f);
                    RectF rectF = this.tmpRect;
                    rectF.top = rectF.bottom - (this.editor.getRowHeight() * 0.06f);
                    f4 = f8;
                    this.tmpRect.left = f4;
                    this.tmpRect.right = f7;
                    drawColor(canvas, iResolve, this.tmpRect);
                }
                if (spanExternalRenderer != null && spanExternalRenderer.requirePostDraw()) {
                    int iSave2 = canvas.save();
                    canvas.translate(f4, this.editor.getRowTop(0));
                    canvas.clipRect(0.0f, 0.0f, f9, this.editor.getRowHeight());
                    try {
                        spanExternalRenderer.draw(canvas, this.paintGeneral, this.editor.getColorScheme(), false);
                    } catch (Exception e3) {
                        Log.e(LOG_TAG, "Error while invoking external renderer", e3);
                    }
                    canvas.restoreToCount(iSave2);
                }
                j2 = j;
            } else {
                i2 = iMin2;
                span = spanAt;
                reader2 = emptyReader;
                i3 = columnCount;
                z2 = false;
            }
            if ((z && f7 > this.editor.getWidth()) || i2 == (i4 = i3)) {
                f6 = f7;
                break;
            }
            if (i7 < reader2.getSpanCount()) {
                reader3 = reader2;
                spanAt = reader3.getSpanAt(i7);
                i6 = i7;
            } else {
                reader3 = reader2;
                spanAt = span;
            }
            columnCount = i4;
            emptyReader = reader3;
            f6 = f7;
            z3 = z2;
            f5 = 0.0f;
            i5 = i;
        }
        if ((this.editor.getNonPrintablePaintingFlags() & 16) != 0) {
            drawMiniGraph(canvas, f6, -1, "↵");
        }
        this.paintGeneral.setTextSkewX(0.0f);
        this.paintGeneral.setFakeBoldText(z2);
        canvas.restore();
    }

    public boolean hasSideHintIcons() {
        MutableInt mutableInt;
        Styles styles = this.editor.getStyles();
        return (styles == null || styles.styleTypeCount == null || (mutableInt = styles.styleTypeCount.get(LineSideIcon.class)) == null || mutableInt.value <= 0) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:72:0x032c  */
    /* JADX WARN: Code duplicated, block: B:73:0x0333  */
    /* JADX WARN: Code duplicated, block: B:76:0x0350  */
    /* JADX WARN: Code duplicated, block: B:78:0x0353  */
    /* JADX WARN: Code duplicated, block: B:81:0x0357  */
    /* JADX WARN: Code duplicated, block: B:82:0x0368  */
    /* JADX WARN: Code duplicated, block: B:83:0x036e  */
    public void drawView(Canvas canvas) {
        int i;
        int i2;
        LongArrayList longArrayList;
        float f;
        List<CodeBlock> list;
        float rowBottom;
        int offsetY;
        float f2;
        int color;
        String string;
        int i3;
        this.cursor.updateCache(this.editor.getFirstVisibleLine());
        EditorColorScheme colorScheme = this.editor.getColorScheme();
        drawColor(canvas, colorScheme.getColor(4), this.viewRect);
        float fMeasureLineNumber = this.editor.measureLineNumber();
        float rowHeight = hasSideHintIcons() ? this.editor.getRowHeight() : 0.0f;
        float fMeasureTextRegionOffset = (-this.editor.getOffsetX()) + this.editor.measureTextRegionOffset();
        float f3 = fMeasureLineNumber + rowHeight;
        int dividerWidth = (int) (this.editor.getDividerWidth() + f3 + this.editor.getDividerMarginLeft() + this.editor.getDividerMarginRight());
        if (this.editor.isWordwrap()) {
            int i4 = this.cachedGutterWidth;
            if (i4 == 0) {
                this.cachedGutterWidth = dividerWidth;
            } else if (i4 != dividerWidth && !this.editor.getEventHandler().isScaling) {
                this.cachedGutterWidth = dividerWidth;
                final CodeEditor codeEditor = this.editor;
                Objects.requireNonNull(codeEditor);
                codeEditor.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorRenderer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        codeEditor.requestLayoutIfNeeded();
                    }
                });
                this.editor.createLayout(false);
            } else if (this.forcedRecreateLayout) {
                this.editor.createLayout();
                final CodeEditor codeEditor2 = this.editor;
                Objects.requireNonNull(codeEditor2);
                codeEditor2.postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.EditorRenderer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        codeEditor2.requestLayoutIfNeeded();
                    }
                });
            }
        } else {
            this.cachedGutterWidth = 0;
            if (this.forcedRecreateLayout) {
                this.editor.createLayout();
            }
        }
        this.forcedRecreateLayout = false;
        prepareLines(this.editor.getFirstVisibleLine(), this.editor.getLastVisibleLine());
        buildMeasureCacheForLines(this.editor.getFirstVisibleLine(), this.editor.getLastVisibleLine(), this.displayTimestamp, true);
        List<CodeBlock> stuckCodeBlocks = getStuckCodeBlocks();
        int size = stuckCodeBlocks == null ? 0 : stuckCodeBlocks.size();
        if (this.cursor.isSelected()) {
            this.editor.getInsertHandleDescriptor().setEmpty();
        } else {
            this.editor.getLeftHandleDescriptor().setEmpty();
            this.editor.getRightHandleDescriptor().setEmpty();
        }
        boolean z = this.editor.isLineNumberEnabled() && (this.editor.isWordwrap() || !this.editor.isLineNumberPinned());
        LongArrayList longArrayList2 = this.postDrawLineNumbers;
        longArrayList2.clear();
        MutableIntList mutableIntList = this.postDrawCurrentLines;
        mutableIntList.clear();
        ArrayList arrayList = new ArrayList(3);
        MutableInt mutableInt = (this.editor.isFirstLineNumberAlwaysVisible() && this.editor.isWordwrap()) ? new MutableInt(-1) : null;
        canvas.save();
        canvas.clipRect(0, this.editor.getRowHeight() * size, this.editor.getWidth(), this.editor.getHeight());
        MutableInt mutableInt2 = mutableInt;
        drawRows(canvas, fMeasureTextRegionOffset, longArrayList2, arrayList, mutableIntList, mutableInt2);
        patchHighlightedDelimiters(canvas, fMeasureTextRegionOffset);
        drawDiagnosticIndicators(canvas, fMeasureTextRegionOffset);
        canvas.restore();
        float f4 = -this.editor.getOffsetX();
        int leftLine = this.cursor.isSelected() ? -1 : this.cursor.getLeftLine();
        if (z) {
            drawLineNumberBackground(canvas, f4, this.editor.getDividerMarginLeft() + f3, colorScheme.getColor(3));
            int color2 = this.editor.getColorScheme().getColor(2);
            int color3 = this.editor.getColorScheme().getColor(9);
            if (this.editor.getCursorAnimator().isRunning() && this.editor.isEditable()) {
                this.tmpRect.bottom = this.editor.getCursorAnimator().animatedLineBottom() - this.editor.getOffsetY();
                RectF rectF = this.tmpRect;
                rectF.top = rectF.bottom - this.editor.getCursorAnimator().animatedLineHeight();
                this.tmpRect.left = 0.0f;
                this.tmpRect.right = (int) (fMeasureTextRegionOffset - this.editor.getDividerMarginRight());
                drawColor(canvas, color3, this.tmpRect);
            }
            canvas.save();
            canvas.clipRect(0, this.editor.getRowHeight() * size, this.editor.getWidth(), this.editor.getHeight());
            for (int i5 = 0; i5 < mutableIntList.get_size(); i5++) {
                drawRowBackground(canvas, color3, mutableIntList.get(i5), (int) (fMeasureTextRegionOffset - this.editor.getDividerMarginRight()));
            }
            drawUserGutterBackground(canvas, (int) (fMeasureTextRegionOffset - this.editor.getDividerMarginRight()));
            float f5 = f4 + fMeasureLineNumber;
            drawSideIcons(canvas, f5);
            canvas.restore();
            drawDivider(canvas, rowHeight + f5 + this.editor.getDividerMarginLeft(), colorScheme.getColor(1));
            canvas.save();
            canvas.clipRect(0, this.editor.getRowHeight() * size, this.editor.getWidth(), this.editor.getHeight());
            if (mutableInt2 == null || mutableInt2.value == -1) {
                longArrayList = longArrayList2;
                i = 2;
            } else {
                int rowBottom2 = this.editor.getRowBottom(0);
                if (longArrayList2.size() != 0) {
                    longArrayList = longArrayList2;
                    if (this.editor.getRowTop(IntPair.getSecond(longArrayList.get(0))) - this.editor.getOffsetY() <= rowBottom2) {
                        int second = IntPair.getSecond(longArrayList.get(0)) - 1;
                        rowBottom = (((this.editor.getRowBottom(second) + this.editor.getRowTop(second)) / 2.0f) - ((this.metricsLineNumber.descent - this.metricsLineNumber.ascent) / 2.0f)) - this.metricsLineNumber.ascent;
                        offsetY = this.editor.getOffsetY();
                    }
                    f2 = rowBottom - offsetY;
                    this.paintOther.setTextAlign(this.editor.getLineNumberAlign());
                    io.github.rosemoe.sora.graphics.Paint paint = this.paintOther;
                    if (mutableInt2.value == leftLine) {
                        color = colorScheme.getColor(45);
                    } else {
                        color = color2;
                    }
                    paint.setColor(color);
                    string = Integer.toString(mutableInt2.value + 1);
                    i3 = AnonymousClass1.$SwitchMap$android$graphics$Paint$Align[this.editor.getLineNumberAlign().ordinal()];
                    if (i3 != 1) {
                        i = 2;
                        if (i3 != 2) {
                            canvas.drawText(string, f5, f2, this.paintOther);
                        } else if (i3 == 3) {
                            canvas.drawText(string, ((this.editor.getDividerMarginLeft() + fMeasureLineNumber) / 2.0f) + f4, f2, this.paintOther);
                        }
                    } else {
                        i = 2;
                        canvas.drawText(string, f4, f2, this.paintOther);
                    }
                } else {
                    longArrayList = longArrayList2;
                }
                rowBottom = ((this.editor.getRowBottom(0) + this.editor.getRowTop(0)) / 2.0f) - ((this.metricsLineNumber.descent - this.metricsLineNumber.ascent) / 2.0f);
                offsetY = this.metricsLineNumber.ascent;
                f2 = rowBottom - offsetY;
                this.paintOther.setTextAlign(this.editor.getLineNumberAlign());
                io.github.rosemoe.sora.graphics.Paint paint2 = this.paintOther;
                if (mutableInt2.value == leftLine) {
                    color = colorScheme.getColor(45);
                } else {
                    color = color2;
                }
                paint2.setColor(color);
                string = Integer.toString(mutableInt2.value + 1);
                i3 = AnonymousClass1.$SwitchMap$android$graphics$Paint$Align[this.editor.getLineNumberAlign().ordinal()];
                if (i3 != 1) {
                    i = 2;
                    if (i3 != 2) {
                        canvas.drawText(string, f5, f2, this.paintOther);
                    } else if (i3 == 3) {
                        canvas.drawText(string, ((this.editor.getDividerMarginLeft() + fMeasureLineNumber) / 2.0f) + f4, f2, this.paintOther);
                    }
                } else {
                    i = 2;
                    canvas.drawText(string, f4, f2, this.paintOther);
                }
            }
            int i6 = 0;
            while (i6 < longArrayList.size()) {
                long j = longArrayList.get(i6);
                drawLineNumber(canvas, IntPair.getFirst(j), IntPair.getSecond(j), f4, fMeasureLineNumber, IntPair.getFirst(j) == leftLine ? colorScheme.getColor(45) : color2);
                i6++;
                f4 = f4;
                leftLine = leftLine;
            }
            i2 = leftLine;
            f = f4;
            canvas.restore();
        } else {
            i = 2;
            i2 = leftLine;
            longArrayList = longArrayList2;
            f = f4;
        }
        if (this.editor.isBlockLineEnabled()) {
            canvas.save();
            canvas.clipRect(0, this.editor.getRowHeight() * size, this.editor.getWidth(), this.editor.getHeight());
            if (this.editor.isWordwrap()) {
                drawSideBlockLine(canvas);
            } else {
                drawBlockLines(canvas, fMeasureTextRegionOffset);
            }
            canvas.restore();
        }
        if (!this.editor.getCursorAnimator().isRunning()) {
            Iterator<DrawCursorTask> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().execute(canvas);
            }
        } else {
            drawSelectionOnAnimation(canvas);
        }
        List<CodeBlock> list2 = stuckCodeBlocks;
        drawStuckLines(canvas, list2, fMeasureTextRegionOffset);
        if (!this.editor.isLineNumberEnabled() || z) {
            list = list2;
        } else {
            drawLineNumberBackground(canvas, 0.0f, this.editor.getDividerMarginLeft() + f3, colorScheme.getColor(3));
            canvas.save();
            canvas.clipRect(0, this.editor.getRowHeight() * size, this.editor.getWidth(), this.editor.getHeight());
            int color4 = this.editor.getColorScheme().getColor(i);
            int color5 = this.editor.getColorScheme().getColor(9);
            if (this.editor.getCursorAnimator().isRunning() && this.editor.isEditable()) {
                this.tmpRect.bottom = this.editor.getCursorAnimator().animatedLineBottom() - this.editor.getOffsetY();
                RectF rectF2 = this.tmpRect;
                rectF2.top = rectF2.bottom - this.editor.getCursorAnimator().animatedLineHeight();
                this.tmpRect.left = 0.0f;
                this.tmpRect.right = (int) (fMeasureTextRegionOffset - this.editor.getDividerMarginRight());
                drawColor(canvas, color5, this.tmpRect);
            }
            for (int i7 = 0; i7 < r21.get_size(); i7++) {
                drawRowBackground(canvas, color5, mutableIntList.get(i7), (int) ((fMeasureTextRegionOffset - this.editor.getDividerMarginRight()) + this.editor.getOffsetX()));
            }
            drawUserGutterBackground(canvas, (int) ((fMeasureTextRegionOffset - this.editor.getDividerMarginRight()) + this.editor.getOffsetX()));
            drawSideIcons(canvas, fMeasureLineNumber);
            canvas.restore();
            drawDivider(canvas, f3 + this.editor.getDividerMarginLeft(), colorScheme.getColor(1));
            canvas.save();
            canvas.clipRect(0, this.editor.getRowHeight() * size, this.editor.getWidth(), this.editor.getHeight());
            int i8 = 0;
            while (i8 < longArrayList.size()) {
                long j2 = longArrayList.get(i8);
                int i9 = i2;
                drawLineNumber(canvas, IntPair.getFirst(j2), IntPair.getSecond(j2), 0.0f, fMeasureLineNumber, IntPair.getFirst(j2) == i9 ? colorScheme.getColor(45) : color4);
                i8++;
                i2 = i9;
                list2 = list2;
            }
            list = list2;
            canvas.restore();
        }
        drawStuckLineNumbers(canvas, list, f, fMeasureLineNumber, this.editor.getColorScheme().getColor(i));
        drawScrollBars(canvas);
        drawEdgeEffect(canvas);
        this.editor.rememberDisplayedLines();
        releasePreloadedData();
        this.lastStuckLines = list;
        drawFormatTip(canvas);
    }

    protected void drawUserGutterBackground(Canvas canvas, int i) {
        int lastVisibleLine = this.editor.getLastVisibleLine();
        for (int firstVisibleLine = this.editor.getFirstVisibleLine(); firstVisibleLine <= lastVisibleLine; firstVisibleLine++) {
            ResolvableColor userGutterBackgroundForLine = getUserGutterBackgroundForLine(firstVisibleLine);
            if (userGutterBackgroundForLine != null) {
                int iResolve = userGutterBackgroundForLine.resolve(this.editor.getColorScheme());
                int rowHeight = ((int) (this.editor.getLayout().getCharLayoutOffset(firstVisibleLine, 0)[0] / this.editor.getRowHeight())) - 1;
                int rowCountForLine = this.editor.getLayout().getRowCountForLine(firstVisibleLine);
                for (int i2 = 0; i2 < rowCountForLine; i2++) {
                    drawRowBackground(canvas, iResolve, rowHeight + i2, i);
                }
            }
        }
    }

    protected void drawStuckLineNumbers(Canvas canvas, List<CodeBlock> list, float f, float f2, int i) {
        if (list == null || list.isEmpty() || !this.editor.isLineNumberEnabled()) {
            return;
        }
        Cursor cursor = this.editor.getCursor();
        int leftLine = cursor.isSelected() ? -1 : cursor.getLeftLine();
        canvas.save();
        int offsetY = this.editor.getOffsetY();
        canvas.translate(0.0f, offsetY);
        for (int i2 = 0; i2 < list.size(); i2++) {
            int i3 = list.get(i2).startLine;
            ResolvableColor userGutterBackgroundForLine = getUserGutterBackgroundForLine(i3);
            int iResolve = userGutterBackgroundForLine != null ? userGutterBackgroundForLine.resolve(this.editor.getColorScheme()) : 0;
            if (leftLine == i3 || iResolve != 0) {
                this.tmpRect.top = this.editor.getRowTop(i2) - offsetY;
                this.tmpRect.bottom = (this.editor.getRowBottom(i2) - offsetY) - this.editor.getDpUnit();
                this.tmpRect.left = this.editor.isLineNumberPinned() ? 0.0f : f;
                RectF rectF = this.tmpRect;
                rectF.right = rectF.left + this.editor.measureTextRegionOffset();
                if (leftLine == i3) {
                    drawColor(canvas, this.editor.getColorScheme().getColor(9), this.tmpRect);
                }
                if (iResolve != 0) {
                    drawColor(canvas, iResolve, this.tmpRect);
                }
            }
            drawLineNumber(canvas, i3, i2, this.editor.isLineNumberPinned() ? 0.0f : f, f2, leftLine == i3 ? this.editor.getColorScheme().getColor(45) : i);
        }
        canvas.restore();
    }

    protected void drawStuckLines(Canvas canvas, List<CodeBlock> list, float f) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Styles styles = this.editor.getStyles();
        Spans spans = styles != null ? styles.spans : null;
        Spans.Reader reader = spans != null ? spans.read() : null;
        Cursor cursor = this.editor.getCursor();
        int leftLine = cursor.isSelected() ? -1 : cursor.getLeftLine();
        int i = 0;
        int i2 = -1;
        for (int i3 = 0; i3 < list.size(); i3++) {
            CodeBlock codeBlock = list.get(i3);
            if (codeBlock.startLine > i2) {
                this.tmpRect.top = this.editor.getRowTop(i);
                this.tmpRect.bottom = this.editor.getRowBottom(i);
                this.tmpRect.left = f;
                this.tmpRect.right = this.editor.getWidth();
                drawColor(canvas, this.editor.getColorScheme().getColor(codeBlock.startLine == leftLine ? 9 : 4), this.tmpRect);
                if (reader != null) {
                    try {
                        reader.moveToLine(codeBlock.startLine);
                    } catch (Throwable th) {
                        if (reader != null) {
                            reader.moveToLine(-1);
                        }
                        throw th;
                    }
                }
                drawSingleTextLine(canvas, codeBlock.startLine, f, this.editor.getRowHeight() * i, reader, true);
                if (reader != null) {
                    reader.moveToLine(-1);
                }
                i2 = codeBlock.startLine;
                i++;
            }
        }
        if (i > 0) {
            this.tmpRect.top = this.editor.getRowTop(i) - this.editor.getDpUnit();
            this.tmpRect.bottom = this.editor.getRowTop(i);
            this.tmpRect.left = 0.0f;
            this.tmpRect.right = this.editor.getWidth();
            drawColor(canvas, this.editor.getColorScheme().getColor(62), this.tmpRect);
        }
    }

    protected void drawHardwrapMarker(Canvas canvas, float f) {
        int i = this.editor.getProps().hardwrapColumn;
        if (this.editor.isWordwrap() || i <= 0) {
            return;
        }
        this.tmpRect.left = f + (this.paintGeneral.measureText("a") * i);
        RectF rectF = this.tmpRect;
        rectF.right = rectF.left + (this.editor.getDpUnit() * 2.0f);
        this.tmpRect.top = 0.0f;
        this.tmpRect.bottom = this.viewRect.bottom;
        drawColor(canvas, this.editor.getColorScheme().getColor(51), this.tmpRect);
    }

    protected void drawSideIcons(Canvas canvas, float f) {
        LineSideIcon lineSideIcon;
        if (hasSideHintIcons()) {
            int firstVisibleRow = this.editor.getFirstVisibleRow();
            RowIterator rowIteratorObtainRowIterator = this.editor.getLayout().obtainRowIterator(firstVisibleRow);
            float f2 = this.editor.getProps().sideIconSizeFactor;
            int rowHeight = (int) (this.editor.getRowHeight() * f2);
            int rowHeight2 = (int) ((this.editor.getRowHeight() * (1.0f - f2)) / 2.0f);
            while (firstVisibleRow <= this.editor.getLastVisibleRow() && rowIteratorObtainRowIterator.hasNext()) {
                Row next = rowIteratorObtainRowIterator.next();
                if (next.isLeadingRow && (lineSideIcon = (LineSideIcon) getLineStyle(next.lineIndex, LineSideIcon.class)) != null) {
                    Drawable drawable = lineSideIcon.getDrawable();
                    Rect rect = new Rect(0, 0, rowHeight, rowHeight);
                    rect.offsetTo(((int) f) + rowHeight2, (this.editor.getRowTop(firstVisibleRow) - this.editor.getOffsetY()) + rowHeight2);
                    drawable.setBounds(rect);
                    drawable.draw(canvas);
                }
                firstVisibleRow++;
            }
        }
    }

    protected void drawFormatTip(Canvas canvas) {
        if (this.editor.isFormatting()) {
            String formatTip = this.editor.getFormatTip();
            float rowBaseline = this.editor.getRowBaseline(0);
            float width = this.editor.getWidth();
            this.paintGeneral.setColor(this.editor.getColorScheme().getColor(5));
            this.paintGeneral.setFakeBoldText(true);
            this.paintGeneral.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(formatTip, width, rowBaseline, this.paintGeneral);
            this.paintGeneral.setTextAlign(Paint.Align.LEFT);
            this.paintGeneral.setFakeBoldText(false);
        }
    }

    protected void drawColor(Canvas canvas, int i, RectF rectF) {
        if (i != 0) {
            this.paintGeneral.setColor(i);
            canvas.drawRect(rectF, this.paintGeneral);
        }
    }

    protected void drawColorRound(Canvas canvas, int i, RectF rectF) {
        if (i != 0) {
            this.paintGeneral.setColor(i);
            canvas.drawRoundRect(rectF, rectF.height() * 0.13f, rectF.height() * 0.13f, this.paintGeneral);
        }
    }

    protected void drawColor(Canvas canvas, int i, Rect rect) {
        if (i != 0) {
            this.paintGeneral.setColor(i);
            canvas.drawRect(rect, this.paintGeneral);
        }
    }

    protected void drawRowBackground(Canvas canvas, int i, int i2) {
        drawRowBackground(canvas, i, i2, this.viewRect.right);
    }

    protected void drawRowBackground(Canvas canvas, int i, int i2, int i3) {
        this.tmpRect.top = this.editor.getRowTop(i2) - this.editor.getOffsetY();
        this.tmpRect.bottom = this.editor.getRowBottom(i2) - this.editor.getOffsetY();
        this.tmpRect.left = 0.0f;
        this.tmpRect.right = i3;
        drawColor(canvas, i, this.tmpRect);
    }

    protected void drawLineNumber(Canvas canvas, int i, int i2, float f, float f2, int i3) {
        float f3 = f2 + f;
        if (f3 <= 0.0f) {
            return;
        }
        if (this.paintOther.getTextAlign() != this.editor.getLineNumberAlign()) {
            this.paintOther.setTextAlign(this.editor.getLineNumberAlign());
        }
        this.paintOther.setColor(i3);
        float rowBottom = ((((this.editor.getRowBottom(i2) + this.editor.getRowTop(i2)) / 2.0f) - ((this.metricsLineNumber.descent - this.metricsLineNumber.ascent) / 2.0f)) - this.metricsLineNumber.ascent) - this.editor.getOffsetY();
        char[] cArrObtain = TemporaryCharBuffer.obtain(20);
        int i4 = i + 1;
        int iStringSize = Numbers.stringSize(i4);
        Numbers.getChars(i4, iStringSize, cArrObtain);
        int i5 = AnonymousClass1.$SwitchMap$android$graphics$Paint$Align[this.editor.getLineNumberAlign().ordinal()];
        if (i5 == 1) {
            canvas.drawText(cArrObtain, 0, iStringSize, f, rowBottom, this.paintOther);
        } else if (i5 == 2) {
            canvas.drawText(cArrObtain, 0, iStringSize, f3, rowBottom, this.paintOther);
        } else if (i5 == 3) {
            canvas.drawText(cArrObtain, 0, iStringSize, f + ((f2 + this.editor.getDividerMarginLeft()) / 2.0f), rowBottom, this.paintOther);
        }
        TemporaryCharBuffer.recycle(cArrObtain);
    }

    protected void drawLineNumberBackground(Canvas canvas, float f, float f2, int i) {
        float f3 = f2 + f;
        if (f3 < 0.0f) {
            return;
        }
        float fMax = Math.max(0.0f, f);
        this.tmpRect.bottom = this.editor.getHeight();
        this.tmpRect.top = 0.0f;
        int offsetY = this.editor.getOffsetY();
        if (offsetY < 0) {
            float f4 = offsetY;
            this.tmpRect.bottom -= f4;
            this.tmpRect.top -= f4;
        }
        this.tmpRect.left = fMax;
        this.tmpRect.right = f3;
        drawColor(canvas, i, this.tmpRect);
    }

    protected void drawDivider(Canvas canvas, float f, int i) {
        boolean z = this.editor.isLineNumberPinned() && !this.editor.isWordwrap() && this.editor.getOffsetX() > 0;
        float dividerWidth = this.editor.getDividerWidth() + f;
        if (dividerWidth < 0.0f) {
            return;
        }
        float fMax = Math.max(0.0f, f);
        this.tmpRect.bottom = this.editor.getHeight();
        this.tmpRect.top = 0.0f;
        int offsetY = this.editor.getOffsetY();
        if (offsetY < 0) {
            float f2 = offsetY;
            this.tmpRect.bottom -= f2;
            this.tmpRect.top -= f2;
        }
        this.tmpRect.left = fMax;
        this.tmpRect.right = dividerWidth;
        if (z) {
            canvas.save();
            canvas.clipRect(this.tmpRect.left, this.tmpRect.top, this.editor.getWidth(), this.tmpRect.bottom);
            this.paintGeneral.setShadowLayer(Math.min(this.editor.getDpUnit() * 8.0f, this.editor.getOffsetX()), 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        }
        drawColor(canvas, i, this.tmpRect);
        if (z) {
            canvas.restore();
            this.paintGeneral.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        }
    }

    private void prepareLines(int i, int i2) {
        releasePreloadedData();
        this.content.runReadActionsOnLines(Math.max(0, i - 5), Math.min(this.content.getLineCount() - 1, i2 + 5), new Content.ContentLineConsumer() { // from class: io.github.rosemoe.sora.widget.EditorRenderer$$ExternalSyntheticLambda2
            @Override // io.github.rosemoe.sora.text.Content.ContentLineConsumer
            public final void accept(int i3, ContentLine contentLine, Directions directions) {
                this.f$0.lambda$prepareLines$0(i3, contentLine, directions);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareLines$0(int i, ContentLine contentLine, Directions directions) {
        this.preloadedLines.put(i, contentLine);
        this.preloadedDirections.put(i, directions);
    }

    private void releasePreloadedData() {
        this.preloadedLines.clear();
        this.preloadedDirections.clear();
    }

    protected List<CodeBlock> getStuckCodeBlocks() {
        List<CodeBlock> list;
        if (!this.editor.isWordwrap() && this.editor.getProps().stickyScroll) {
            int firstVisibleLine = this.editor.getFirstVisibleLine();
            int offsetY = this.editor.getOffsetY();
            int rowHeight = this.editor.getRowHeight();
            Styles styles = this.editor.getStyles();
            if (styles != null && (list = styles.blocksByStart) != null) {
                int size = list.size();
                List<CodeBlock> arrayList = new ArrayList<>();
                int i = this.editor.getProps().stickyScrollIterationLimit;
                for (int i2 = 0; i2 < size && i2 < i; i2++) {
                    CodeBlock codeBlock = list.get(i2);
                    if (codeBlock != null) {
                        if (codeBlock.startLine > firstVisibleLine) {
                            break;
                        }
                        if (codeBlock.endLine > firstVisibleLine && this.editor.getRowTop(codeBlock.startLine) - offsetY < 0) {
                            arrayList.add(codeBlock);
                            firstVisibleLine++;
                            offsetY += rowHeight;
                        }
                    }
                }
                int i3 = this.editor.getProps().stickyScrollMaxLines;
                if (arrayList.size() > i3) {
                    if (i3 <= 0) {
                        return null;
                    }
                    if (this.editor.getProps().stickyScrollPreferInnerScope) {
                        arrayList = arrayList.subList(arrayList.size() - i3, arrayList.size());
                    } else {
                        arrayList = arrayList.subList(0, i3);
                    }
                }
                if (this.editor.getCursor().isSelected() && this.editor.getProps().stickyScrollAutoCollapse) {
                    int leftLine = this.editor.getCursor().getLeftLine();
                    int firstVisibleLine2 = this.editor.getFirstVisibleLine();
                    if (this.editor.getCursor().getRightLine() >= firstVisibleLine2) {
                        while (!arrayList.isEmpty() && arrayList.size() + firstVisibleLine2 >= leftLine) {
                            arrayList.remove(arrayList.size() - 1);
                        }
                    }
                }
                return arrayList;
            }
        }
        return null;
    }

    protected LineStyles getLineStyles(int i) {
        List<LineStyles> list;
        Styles styles = this.editor.getStyles();
        if (styles != null && (list = styles.lineStyles) != null) {
            this.coordinateLine.setLine(i);
            int iBinarySearch = Collections.binarySearch(list, this.coordinateLine);
            if (iBinarySearch >= 0 && iBinarySearch < list.size()) {
                return list.get(iBinarySearch);
            }
        }
        return null;
    }

    protected <T extends LineAnchorStyle> T getLineStyle(int i, Class<T> cls) {
        LineStyles lineStyles = getLineStyles(i);
        if (lineStyles != null) {
            return (T) lineStyles.findOne(cls);
        }
        return null;
    }

    protected ResolvableColor getUserBackgroundForLine(int i) {
        LineBackground lineBackground = (LineBackground) getLineStyle(i, LineBackground.class);
        if (lineBackground != null) {
            return lineBackground.getColor();
        }
        return null;
    }

    protected ResolvableColor getUserGutterBackgroundForLine(int i) {
        LineGutterBackground lineGutterBackground = (LineGutterBackground) getLineStyle(i, LineGutterBackground.class);
        if (lineGutterBackground != null) {
            return lineGutterBackground.getColor();
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:107:0x039d  */
    /* JADX WARN: Code duplicated, block: B:109:0x03a2  */
    /* JADX WARN: Code duplicated, block: B:114:0x03ad  */
    /* JADX WARN: Code duplicated, block: B:116:0x03b0  */
    /* JADX WARN: Code duplicated, block: B:117:0x03b5  */
    /* JADX WARN: Code duplicated, block: B:124:0x03ce  */
    /* JADX WARN: Code duplicated, block: B:127:0x03da  */
    /* JADX WARN: Code duplicated, block: B:128:0x03f1  */
    /* JADX WARN: Code duplicated, block: B:129:0x03fa  */
    /* JADX WARN: Code duplicated, block: B:147:0x04be  */
    /* JADX WARN: Code duplicated, block: B:151:0x04ce  */
    /* JADX WARN: Code duplicated, block: B:157:0x04eb  */
    /* JADX WARN: Code duplicated, block: B:159:0x04f3  */
    /* JADX WARN: Code duplicated, block: B:160:0x04f9  */
    /* JADX WARN: Code duplicated, block: B:164:0x052c  */
    /* JADX WARN: Code duplicated, block: B:167:0x0548  */
    /* JADX WARN: Code duplicated, block: B:169:0x0562  */
    /* JADX WARN: Code duplicated, block: B:182:0x05a5  */
    /* JADX WARN: Code duplicated, block: B:185:0x05b5  */
    /* JADX WARN: Code duplicated, block: B:187:0x05c4  */
    /* JADX WARN: Code duplicated, block: B:188:0x05ce  */
    /* JADX WARN: Code duplicated, block: B:190:0x05d7  */
    /* JADX WARN: Code duplicated, block: B:193:0x05e4 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:197:0x0694  */
    /* JADX WARN: Code duplicated, block: B:199:0x06a4  */
    /* JADX WARN: Code duplicated, block: B:201:0x06ee  */
    /* JADX WARN: Code duplicated, block: B:204:0x06f6  */
    /* JADX WARN: Code duplicated, block: B:209:0x0741  */
    /* JADX WARN: Code duplicated, block: B:222:0x0785  */
    /* JADX WARN: Code duplicated, block: B:225:0x0793 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:236:0x07dc  */
    /* JADX WARN: Code duplicated, block: B:242:0x07ea  */
    /* JADX WARN: Code duplicated, block: B:244:0x07ee  */
    /* JADX WARN: Code duplicated, block: B:250:0x07fb  */
    /* JADX WARN: Code duplicated, block: B:251:0x0815  */
    /* JADX WARN: Code duplicated, block: B:254:0x081d  */
    /* JADX WARN: Code duplicated, block: B:257:0x0834  */
    /* JADX WARN: Code duplicated, block: B:260:0x084a  */
    /* JADX WARN: Code duplicated, block: B:286:0x0902 A[PHI: r54
      0x0902: PHI (r54v7 int) = (r54v6 int), (r54v16 int) binds: [B:241:0x07e8, B:247:0x07f3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:287:0x0904 A[PHI: r33 r35 r54
      0x0904: PHI (r33v10 int) = (r33v9 int), (r33v9 int), (r33v9 int), (r33v12 int), (r33v14 int) binds: [B:286:0x0902, B:259:0x0848, B:261:0x0850, B:285:0x08ff, B:275:0x0888] A[DONT_GENERATE, DONT_INLINE]
      0x0904: PHI (r35v5 io.github.rosemoe.sora.lang.styling.Spans$Reader) = 
      (r35v4 io.github.rosemoe.sora.lang.styling.Spans$Reader)
      (r9v17 io.github.rosemoe.sora.lang.styling.Spans$Reader)
      (r9v17 io.github.rosemoe.sora.lang.styling.Spans$Reader)
      (r9v17 io.github.rosemoe.sora.lang.styling.Spans$Reader)
      (r9v17 io.github.rosemoe.sora.lang.styling.Spans$Reader)
     binds: [B:286:0x0902, B:259:0x0848, B:261:0x0850, B:285:0x08ff, B:275:0x0888] A[DONT_GENERATE, DONT_INLINE]
      0x0904: PHI (r54v8 int) = (r54v7 int), (r54v11 int), (r54v11 int), (r54v11 int), (r54v11 int) binds: [B:286:0x0902, B:259:0x0848, B:261:0x0850, B:285:0x08ff, B:275:0x0888] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:290:0x090a  */
    /* JADX WARN: Code duplicated, block: B:292:0x0910  */
    /* JADX WARN: Code duplicated, block: B:294:0x0928  */
    /* JADX WARN: Code duplicated, block: B:295:0x0978  */
    /* JADX WARN: Code duplicated, block: B:298:0x0988  */
    /* JADX WARN: Code duplicated, block: B:300:0x0990  */
    /* JADX WARN: Code duplicated, block: B:302:0x09a2  */
    /* JADX WARN: Code duplicated, block: B:304:0x09d9  */
    /* JADX WARN: Code duplicated, block: B:305:0x09db  */
    /* JADX WARN: Code duplicated, block: B:307:0x0a01  */
    /* JADX WARN: Code duplicated, block: B:308:0x0a04  */
    /* JADX WARN: Code duplicated, block: B:312:0x0a12  */
    /* JADX WARN: Code duplicated, block: B:316:0x0a58  */
    /* JADX WARN: Code duplicated, block: B:317:0x0a5b  */
    /* JADX WARN: Code duplicated, block: B:319:0x0a7a  */
    /* JADX WARN: Code duplicated, block: B:321:0x0a88  */
    /* JADX WARN: Code duplicated, block: B:326:0x0adc  */
    /* JADX WARN: Code duplicated, block: B:328:0x0ae0  */
    /* JADX WARN: Code duplicated, block: B:331:0x0b20  */
    /* JADX WARN: Code duplicated, block: B:333:0x0b28  */
    /* JADX WARN: Code duplicated, block: B:378:0x0b7b A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:381:0x0b7b A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:386:0x04dc A[ADDED_TO_REGION, EDGE_INSN: B:386:0x04dc->B:154:0x04dc BREAK  A[LOOP:3: B:149:0x04c4->B:153:0x04d8], REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:388:0x07c6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:389:0x051a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:390:0x0536 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:391:0x07c2 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    protected void drawRows(Canvas canvas, float f, LongArrayList longArrayList, List<DrawCursorTask> list, MutableIntList mutableIntList, MutableInt mutableInt) {
        int i;
        int second;
        int i2;
        int i3;
        Spans.Reader reader;
        int first;
        int textOffset;
        float pixelWidthOrOffset;
        Spans.Reader reader2;
        int i4;
        int i5;
        int i6;
        int i7;
        long j;
        Row row;
        int textOffset2;
        int nonPrintablePaintingFlags;
        char c;
        int i8;
        int i9;
        Spans.Reader reader3;
        int i10;
        Span spanAt;
        float f2;
        int i11;
        int rightColumn;
        int i12;
        float f3;
        float f4;
        int i13;
        int i14;
        int column;
        int i15;
        int iMin;
        int iMax;
        int iMin2;
        float fMeasureText;
        SpanExternalRenderer spanExternalRenderer;
        int i16;
        long styleBits;
        int backgroundColor;
        String str;
        float f5;
        Spans.Reader reader4;
        float f6;
        ResolvableColor underlineColor;
        String str2;
        float f7;
        Spans.Reader reader5;
        int iResolve;
        int color;
        int i17;
        CharPosition charPosition;
        int i18;
        Layout layout;
        Row row2;
        int i19;
        CharPosition charPosition2;
        CharPosition selectingTarget;
        int i20;
        int i21;
        int i22;
        int iMin3;
        int iMin4;
        int i23;
        int i24;
        int i25;
        int i26;
        int i27;
        Spans.Reader emptyReader;
        int i28;
        ResolvableColor userBackgroundForLine;
        LongArrayList longArrayList2;
        float f8;
        float f9;
        CharPosition charPosition3;
        CharPosition charPosition4;
        int i29;
        int i30;
        Row row3;
        int firstVisibleRow = this.editor.getFirstVisibleRow();
        RowIterator rowIteratorObtainRowIterator = this.editor.getLayout().obtainRowIterator(firstVisibleRow, this.preloadedLines);
        Spans spans = this.editor.getStyles() == null ? null : this.editor.getStyles().spans;
        LongArrayList longArrayList3 = this.matchedPositions;
        longArrayList3.clear();
        int leftLine = this.cursor.isSelected() ? -1 : this.cursor.getLeftLine();
        int color2 = this.editor.getColorScheme().getColor(9);
        CharPosition charPosition5 = (!this.editor.inputConnection.composingText.isComposing() || this.editor.inputConnection.composingText.startIndex < 0 || this.editor.inputConnection.composingText.startIndex >= this.content.length()) ? null : this.content.getIndexer().getCharPosition(this.editor.inputConnection.composingText.startIndex);
        int i31 = this.editor.inputConnection.composingText.endIndex - this.editor.inputConnection.composingText.startIndex;
        CharPosition charPosition6 = this.editor.getEventHandler().draggingSelection;
        float fMin = this.editor.shouldInitializeNonPrintable() ? Math.min(this.editor.getRowHeight(), this.paintGeneral.getSpaceWidth()) * 0.125f : 0.0f;
        if (Build.VERSION.SDK_INT >= 29 && !this.editor.isWordwrap() && canvas.isHardwareAccelerated() && this.editor.isHardwareAcceleratedDrawAllowed()) {
            this.renderNodeHolder.keepCurrentInDisplay(firstVisibleRow, this.editor.getLastVisibleRow());
        }
        float offsetX = this.editor.getOffsetX() - this.editor.measureTextRegionOffset();
        float dpUnit = offsetX - (this.editor.getDpUnit() * 15.0f);
        if (this.editor.getCursorAnimator().isRunning()) {
            this.tmpRect.bottom = this.editor.getCursorAnimator().animatedLineBottom() - this.editor.getOffsetY();
            RectF rectF = this.tmpRect;
            rectF.top = rectF.bottom - this.editor.getCursorAnimator().animatedLineHeight();
            this.tmpRect.left = 0.0f;
            this.tmpRect.right = this.viewRect.right;
            drawColor(canvas, color2, this.tmpRect);
        }
        int i32 = firstVisibleRow;
        int i33 = -1;
        while (i32 <= this.editor.getLastVisibleRow() && rowIteratorObtainRowIterator.hasNext()) {
            Row next = rowIteratorObtainRowIterator.next();
            int i34 = next.lineIndex;
            int columnCount = getColumnCount(i34);
            if (i33 != i34) {
                this.editor.computeMatchedPositions(i34, longArrayList3);
                prepareLine(i34);
                i28 = i34;
            } else {
                i28 = i33;
            }
            float pixelWidthOrOffset2 = CharPosDesc.getPixelWidthOrOffset(findDesiredVisibleChar(dpUnit, i34, next.startColumn, next.endColumn)) - offsetX;
            boolean z = i34 == leftLine && !this.editor.getCursorAnimator().isRunning() && this.editor.isEditable();
            if ((!z || this.editor.getProps().drawCustomLineBgOnCurrentLine) && (userBackgroundForLine = getUserBackgroundForLine(i34)) != null) {
                drawRowBackground(canvas, userBackgroundForLine.resolve(this.editor.getColorScheme()), i32);
            }
            if (z) {
                drawRowBackground(canvas, color2, i32);
                mutableIntList.add(i32);
            }
            if (longArrayList3.size() > 0) {
                float f10 = dpUnit;
                int i35 = 0;
                while (i35 < longArrayList3.size()) {
                    long j2 = longArrayList3.get(i35);
                    drawRowRegionBackground(canvas, i32, i34, IntPair.getFirst(j2), IntPair.getSecond(j2), next.startColumn, next.endColumn, this.editor.getColorScheme().getColor(29));
                    i35++;
                    next = next;
                    pixelWidthOrOffset2 = pixelWidthOrOffset2;
                    color2 = color2;
                    leftLine = leftLine;
                    longArrayList3 = longArrayList3;
                    f10 = f10;
                    charPosition6 = charPosition6;
                    charPosition5 = charPosition5;
                }
                longArrayList2 = longArrayList3;
                f9 = pixelWidthOrOffset2;
                charPosition3 = charPosition6;
                charPosition4 = charPosition5;
                i29 = color2;
                i30 = leftLine;
                row3 = next;
                f8 = f10;
            } else {
                longArrayList2 = longArrayList3;
                f8 = dpUnit;
                f9 = pixelWidthOrOffset2;
                charPosition3 = charPosition6;
                charPosition4 = charPosition5;
                i29 = color2;
                i30 = leftLine;
                row3 = next;
            }
            if (this.cursor.isSelected() && i34 >= this.cursor.getLeftLine() && i34 <= this.cursor.getRightLine()) {
                int leftColumn = i34 == this.cursor.getLeftLine() ? this.cursor.getLeftColumn() : 0;
                int rightColumn2 = i34 == this.cursor.getRightLine() ? this.cursor.getRightColumn() : columnCount;
                if (getColumnCount(i34) == 0 && i34 != this.cursor.getRightLine()) {
                    this.tmpRect.top = this.editor.getRowTop(i32) - this.editor.getOffsetY();
                    this.tmpRect.bottom = this.editor.getRowBottom(i32) - this.editor.getOffsetY();
                    this.tmpRect.left = f9;
                    RectF rectF2 = this.tmpRect;
                    rectF2.right = rectF2.left + (this.paintGeneral.getSpaceWidth() * 2.0f);
                    this.paintGeneral.setColor(this.editor.getColorScheme().getColor(6));
                    canvas.drawRoundRect(this.tmpRect, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.paintGeneral);
                } else if (leftColumn < rightColumn2) {
                    drawRowRegionBackground(canvas, i32, i34, leftColumn, rightColumn2, row3.startColumn, row3.endColumn, this.editor.getColorScheme().getColor(6));
                }
            }
            i32++;
            i33 = i28;
            color2 = i29;
            leftLine = i30;
            longArrayList3 = longArrayList2;
            dpUnit = f8;
            charPosition6 = charPosition3;
            charPosition5 = charPosition4;
        }
        float f11 = dpUnit;
        CharPosition charPosition7 = charPosition6;
        CharPosition charPosition8 = charPosition5;
        rowIteratorObtainRowIterator.reset();
        patchSnippetRegions(canvas, f);
        drawHardwrapMarker(canvas, f);
        long j3 = 0;
        int i36 = firstVisibleRow;
        int i37 = -1;
        int i38 = 0;
        int i39 = 0;
        int i40 = 0;
        Spans.Reader reader6 = null;
        while (true) {
            int lastVisibleRow = this.editor.getLastVisibleRow();
            String str3 = LOG_TAG;
            if (i36 > lastVisibleRow || !rowIteratorObtainRowIterator.hasNext()) {
                break;
            }
            Row next2 = rowIteratorObtainRowIterator.next();
            int i41 = next2.lineIndex;
            int length = getLine(i41).length();
            int i42 = firstVisibleRow;
            if (i36 == firstVisibleRow && mutableInt != null) {
                mutableInt.value = i41;
                i = i38;
            } else {
                i = i38;
                if (next2.isLeadingRow) {
                    longArrayList.add(IntPair.pack(i41, i36));
                }
                if (i37 != i41) {
                    prepareLine(i41);
                    if (reader6 != null) {
                        try {
                            reader6.moveToLine(-1);
                        } catch (Exception e) {
                            Log.w(LOG_TAG, "Failed to release SpanReader", e);
                        }
                    }
                    if (spans == null) {
                        emptyReader = EmptyReader.getInstance();
                    } else {
                        emptyReader = spans.read();
                    }
                    try {
                        emptyReader.moveToLine(i41);
                    } catch (Exception e2) {
                        Log.w(LOG_TAG, "Failed to read span", e2);
                        emptyReader = EmptyReader.getInstance();
                    }
                    if (emptyReader.getSpanCount() == 0) {
                        emptyReader = EmptyReader.getInstance();
                    }
                    if (this.editor.shouldInitializeNonPrintable()) {
                        long jFindLeadingAndTrailingWhitespacePos = this.editor.findLeadingAndTrailingWhitespacePos(this.lineBuf);
                        first = IntPair.getFirst(jFindLeadingAndTrailingWhitespacePos);
                        second = IntPair.getSecond(jFindLeadingAndTrailingWhitespacePos);
                        i2 = i41;
                        reader = emptyReader;
                        i3 = 0;
                    } else {
                        first = i;
                        reader = emptyReader;
                        second = i39;
                        i3 = 0;
                        i2 = i41;
                    }
                } else {
                    second = i39;
                    i2 = i37;
                    i3 = i40;
                    reader = reader6;
                    first = i;
                }
                long jFindDesiredVisibleChar = findDesiredVisibleChar(f11, i41, next2.startColumn, next2.endColumn);
                textOffset = CharPosDesc.getTextOffset(jFindDesiredVisibleChar);
                pixelWidthOrOffset = CharPosDesc.getPixelWidthOrOffset(jFindDesiredVisibleChar) - offsetX;
                int i43 = i3;
                reader2 = reader;
                i4 = length;
                i5 = second;
                i6 = first;
                i7 = i41;
                j = j3;
                row = next2;
                textOffset2 = CharPosDesc.getTextOffset(findDesiredVisibleChar(this.editor.getWidth() - pixelWidthOrOffset, i41, textOffset, next2.endColumn, next2.startColumn, true));
                nonPrintablePaintingFlags = this.editor.getNonPrintablePaintingFlags();
                if (this.editor.isHardwareAcceleratedDrawAllowed() || this.editor.getEventHandler().isScaling || !canvas.isHardwareAccelerated() || this.editor.isWordwrap()) {
                    c = 29;
                } else {
                    c = 29;
                    if (Build.VERSION.SDK_INT >= 29 && (row.endColumn - row.startColumn <= 128 || this.editor.getProps().cacheRenderNodeForLongLines)) {
                        this.renderNodeHolder.drawLineHardwareAccelerated(canvas, i7, f, this.editor.getRowTop(i7) - this.editor.getOffsetY());
                        this.editor.getDpUnit();
                        row = row;
                        i7 = i7;
                        i6 = i6;
                        reader3 = reader2;
                        f11 = f11;
                        i13 = i4;
                        rightColumn = i13;
                        i12 = 0;
                        i10 = i43;
                        i11 = textOffset;
                        f3 = 0.0f;
                    }
                    if (fMin == f3) {
                        reader3 = reader3;
                        i17 = i5;
                    } else {
                        i23 = i6;
                        if (i23 == rightColumn || (nonPrintablePaintingFlags & 8) != 0) {
                            i24 = nonPrintablePaintingFlags & 1;
                            if (i24 != 0) {
                                i6 = i23;
                                drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, 0, i6);
                            } else {
                                i6 = i23;
                            }
                            i25 = nonPrintablePaintingFlags & 2;
                            if (i25 != 0) {
                                drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i6, i5);
                            }
                            i26 = nonPrintablePaintingFlags & 4;
                            if (i26 != 0) {
                                drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i5, rightColumn);
                            }
                            if ((nonPrintablePaintingFlags & 64) == 0 && this.cursor.isSelected()) {
                                int i44 = i7;
                                if (i44 < this.cursor.getLeftLine() || i44 > this.cursor.getRightLine()) {
                                    i7 = i44;
                                } else {
                                    int leftColumn2 = i44 == this.cursor.getLeftLine() ? this.cursor.getLeftColumn() : i12;
                                    if (i44 == this.cursor.getRightLine()) {
                                        rightColumn = this.cursor.getRightColumn();
                                    }
                                    if ((nonPrintablePaintingFlags & 14) == 0) {
                                        i7 = i44;
                                        drawWhitespaces(canvas, pixelWidthOrOffset, i44, i36, i11, i13, leftColumn2, rightColumn);
                                    } else {
                                        i7 = i44;
                                        if (i24 == 0) {
                                            int i45 = i6;
                                            i6 = i45;
                                            drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, leftColumn2, Math.min(i45, rightColumn));
                                        } else {
                                            i6 = i6;
                                        }
                                        if (i25 == 0) {
                                            int i46 = leftColumn2;
                                            i17 = i5;
                                            i27 = i46;
                                            drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, Math.max(i6, i46), Math.min(i5, rightColumn));
                                        } else {
                                            i17 = i5;
                                            i27 = leftColumn2;
                                        }
                                        if (i26 == 0) {
                                            drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, Math.max(i17, i27), rightColumn);
                                        }
                                    }
                                }
                                i17 = i5;
                            } else {
                                i17 = i5;
                            }
                        } else {
                            i6 = i23;
                            reader3 = reader3;
                            i17 = i5;
                        }
                    }
                    charPosition = charPosition8;
                    if (charPosition != null) {
                        i18 = i7;
                        if (i18 == charPosition.line) {
                            int i47 = charPosition.column;
                            int i48 = i47 + i31;
                            i22 = i11;
                            iMin3 = Math.min(Math.max(i47, i22), i13);
                            iMin4 = Math.min(Math.max(i48, i22), i13);
                            if (iMin3 < iMin4) {
                                this.tmpRect.top = this.editor.getRowBottom(i36) - this.editor.getOffsetY();
                                RectF rectF3 = this.tmpRect;
                                rectF3.bottom = rectF3.top + (this.editor.getRowHeight() * 0.06f);
                                this.tmpRect.left = pixelWidthOrOffset + measureText(this.lineBuf, i18, i22, iMin3 - i22);
                                RectF rectF4 = this.tmpRect;
                                rectF4.right = rectF4.left + measureText(this.lineBuf, i18, iMin3, iMin4 - iMin3);
                                drawColor(canvas, this.editor.getColorScheme().getColor(10), this.tmpRect);
                            }
                        }
                    } else {
                        i18 = i7;
                    }
                    layout = this.editor.getLayout();
                    if (this.cursor.isSelected()) {
                        if (this.cursor.getLeftLine() == i18) {
                            row2 = row;
                            if (isInside(this.cursor.getLeftColumn(), row2.startColumn, row2.endColumn, i18)) {
                                float fMeasureTextRegionOffset = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX();
                                if (this.content.isRtlAt(this.cursor.getLeftLine(), this.cursor.getLeftColumn())) {
                                    i21 = 2;
                                } else {
                                    i21 = 1;
                                }
                                i19 = 1;
                                list = list;
                                list.add(new DrawCursorTask(fMeasureTextRegionOffset, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i21, this.editor.getLeftHandleDescriptor()));
                            }
                            if (this.cursor.getRightLine() == i18 && isInside(this.cursor.getRightColumn(), row2.startColumn, row2.endColumn, i18)) {
                                float fMeasureTextRegionOffset2 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                                if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                                    i20 = i19;
                                } else {
                                    i20 = 2;
                                }
                                list.add(new DrawCursorTask(fMeasureTextRegionOffset2, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                            }
                        } else {
                            row2 = row;
                        }
                        i19 = 1;
                        if (this.cursor.getRightLine() == i18) {
                            float fMeasureTextRegionOffset3 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                            if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                                i20 = i19;
                            } else {
                                i20 = 2;
                            }
                            list.add(new DrawCursorTask(fMeasureTextRegionOffset3, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                        }
                    } else {
                        list = list;
                        row2 = row;
                        i19 = 1;
                        if (this.cursor.getLeftLine() == i18 && isInside(this.cursor.getLeftColumn(), row2.startColumn, row2.endColumn, i18)) {
                            list.add(new DrawCursorTask((this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX(), getRowBottomForBackground(i36) - this.editor.getOffsetY(), 0, this.editor.getInsertHandleDescriptor()));
                        }
                    }
                    charPosition2 = charPosition7;
                    if (charPosition2 != null) {
                        if (charPosition2.line != i18 && isInside(charPosition2.column, row2.startColumn, row2.endColumn, i18)) {
                            list.add(new DrawCursorTask((this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(charPosition2.line, charPosition2.column)[i19]) - this.editor.getOffsetX(), getRowBottomForBackground(i36) - this.editor.getOffsetY(), -1, null));
                        }
                    } else if (!this.editor.isInMouseMode() && this.editor.isTextSelected() && (selectingTarget = this.editor.getSelectingTarget()) != null && selectingTarget.line == i18 && isInside(selectingTarget.column, row2.startColumn, row2.endColumn, i18)) {
                        list.add(new DrawCursorTask((this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(selectingTarget.line, selectingTarget.column)[i19]) - this.editor.getOffsetX(), getRowBottomForBackground(i36) - this.editor.getOffsetY(), -1, null));
                    }
                    i36++;
                    charPosition8 = charPosition;
                    charPosition7 = charPosition2;
                    i39 = i17;
                    firstVisibleRow = i42;
                    i37 = i2;
                    i40 = i10;
                    j3 = j;
                    reader6 = reader3;
                    i38 = i6;
                    f11 = f11;
                }
                i8 = i43;
                while (true) {
                    i9 = i8 + 1;
                    reader3 = reader2;
                    if (i9 < reader2.getSpanCount() || reader3.getSpanAt(i9).getColumn() > textOffset) {
                        break;
                    }
                    i8 = i9;
                    reader2 = reader3;
                }
                i10 = i8;
                spanAt = reader3.getSpanAt(i8);
                f2 = pixelWidthOrOffset;
                while (true) {
                    if (textOffset2 > spanAt.getColumn()) {
                        i14 = i10 + 1;
                        if (i14 >= reader3.getSpanCount()) {
                            column = i4;
                            i15 = column;
                        } else {
                            column = reader3.getSpanAt(i14).getColumn();
                            i15 = i4;
                        }
                        iMin = Math.min(i15, column);
                        i11 = textOffset;
                        iMax = Math.max(0, Math.max(textOffset, spanAt.getColumn()));
                        if (iMax >= i15) {
                            rightColumn = i15;
                            i12 = 0;
                            f3 = 0.0f;
                        } else {
                            iMin2 = Math.min(i15, Math.min(textOffset2, iMin));
                            if (iMax > iMin2) {
                                rightColumn = i15;
                                f3 = 0.0f;
                                i12 = 0;
                            } else {
                                int i49 = i15;
                                fMeasureText = measureText(this.lineBuf, i7, iMax, iMin2 - iMax);
                                spanExternalRenderer = (SpanExternalRenderer) spanAt.getSpanExt(1);
                                if (spanExternalRenderer == null && spanExternalRenderer.requirePreDraw()) {
                                    int iSave = canvas.save();
                                    canvas.translate(f2, this.editor.getRowTop(i36) - this.editor.getOffsetY());
                                    canvas.clipRect(0.0f, 0.0f, fMeasureText, this.editor.getRowHeight());
                                    try {
                                        i16 = textOffset2;
                                        try {
                                            spanExternalRenderer.draw(canvas, this.paintGeneral, this.editor.getColorScheme(), true);
                                        } catch (Exception e3) {
                                            e = e3;
                                            Log.e(str3, "Error while invoking external renderer", e);
                                        }
                                    } catch (Exception e4) {
                                        e = e4;
                                        i16 = textOffset2;
                                    }
                                    canvas.restoreToCount(iSave);
                                } else {
                                    i16 = textOffset2;
                                }
                                styleBits = spanAt.getStyleBits();
                                if (spanAt.getStyleBits() != j) {
                                    this.paintGeneral.setFakeBoldText(TextStyle.isBold(styleBits));
                                    if (TextStyle.isItalics(styleBits)) {
                                        this.paintGeneral.setTextSkewX(-0.2f);
                                    } else {
                                        this.paintGeneral.setTextSkewX(0.0f);
                                    }
                                    j = styleBits;
                                }
                                backgroundColor = RendererUtils.getBackgroundColor(spanAt, this.editor.getColorScheme());
                                if (backgroundColor != 0 && iMax != iMin2) {
                                    this.tmpRect.top = this.editor.getRowTop(i36) - this.editor.getOffsetY();
                                    this.tmpRect.bottom = this.editor.getRowBottom(i36) - this.editor.getOffsetY();
                                    this.tmpRect.left = f2;
                                    RectF rectF5 = this.tmpRect;
                                    rectF5.right = rectF5.left + fMeasureText;
                                    this.paintGeneral.setColor(backgroundColor);
                                    canvas.drawRoundRect(this.tmpRect, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.paintGeneral);
                                }
                                str = str3;
                                f5 = f2;
                                Span span = spanAt;
                                reader4 = reader3;
                                int i50 = i16;
                                row = row;
                                i7 = i7;
                                i6 = i6;
                                f11 = f11;
                                drawRegionTextDirectional(canvas, f2, this.editor.getRowBaseline(i36) - this.editor.getOffsetY(), i7, iMax, iMin2, spanAt.getColumn(), iMin, i49, RendererUtils.getForegroundColor(spanAt, this.editor.getColorScheme()));
                                if (TextStyle.isStrikeThrough(styleBits)) {
                                    color = this.editor.getColorScheme().getColor(57);
                                    io.github.rosemoe.sora.graphics.Paint paint = this.paintOther;
                                    if (color == 0) {
                                        color = this.paintGeneral.getColor();
                                    }
                                    paint.setColor(color);
                                    f6 = f5;
                                    canvas.drawLine(f6, (this.editor.getRowTop(i36) + (this.editor.getRowHeight() / 2.0f)) - this.editor.getOffsetY(), f6 + fMeasureText, (this.editor.getRowTop(i36) + (this.editor.getRowHeight() / 2.0f)) - this.editor.getOffsetY(), this.paintOther);
                                } else {
                                    f6 = f5;
                                }
                                underlineColor = span.getUnderlineColor();
                                if (underlineColor != null && (iResolve = underlineColor.resolve(this.editor.getColorScheme())) != 0) {
                                    this.tmpRect.bottom = (this.editor.getRowBottom(i36) - this.editor.getOffsetY()) - (this.editor.getDpUnit() * 1.0f);
                                    RectF rectF6 = this.tmpRect;
                                    rectF6.top = rectF6.bottom - (this.editor.getRowHeight() * 0.06f);
                                    this.tmpRect.left = f6;
                                    this.tmpRect.right = f6 + fMeasureText;
                                    drawColor(canvas, iResolve, this.tmpRect);
                                }
                                if (spanExternalRenderer == null && spanExternalRenderer.requirePostDraw()) {
                                    int iSave2 = canvas.save();
                                    canvas.translate(f6, this.editor.getRowTop(i36) - this.editor.getOffsetY());
                                    f3 = 0.0f;
                                    canvas.clipRect(0.0f, 0.0f, fMeasureText, this.editor.getRowHeight());
                                    try {
                                        i12 = 0;
                                        try {
                                            spanExternalRenderer.draw(canvas, this.paintGeneral, this.editor.getColorScheme(), false);
                                            str2 = str;
                                        } catch (Exception e5) {
                                            e = e5;
                                            str2 = str;
                                            Log.e(str2, "Error while invoking external renderer", e);
                                        }
                                    } catch (Exception e6) {
                                        e = e6;
                                        i12 = 0;
                                    }
                                    canvas.restoreToCount(iSave2);
                                } else {
                                    str2 = str;
                                    i12 = 0;
                                    f3 = 0.0f;
                                }
                                f7 = f6 + fMeasureText;
                                textOffset2 = i50;
                                rightColumn = i49;
                                if (iMin2 < textOffset2 || iMin2 >= rightColumn) {
                                    reader3 = reader4;
                                    f4 = f7;
                                } else {
                                    if (i14 < reader4.getSpanCount()) {
                                        reader5 = reader4;
                                        i10 = i14;
                                        spanAt = reader5.getSpanAt(i14);
                                    } else {
                                        reader5 = reader4;
                                        spanAt = span;
                                    }
                                    textOffset = i11;
                                    textOffset2 = textOffset2;
                                    reader3 = reader5;
                                    i4 = rightColumn;
                                    f11 = f11;
                                    i7 = i7;
                                    c = 29;
                                    row = row;
                                    i6 = i6;
                                    str3 = str2;
                                    f2 = f7;
                                }
                            }
                            if (textOffset2 == rightColumn && (nonPrintablePaintingFlags & 16) != 0) {
                                drawMiniGraph(canvas, f4, i36, "↵");
                            }
                            i13 = textOffset2;
                            if (fMin == f3) {
                                reader3 = reader3;
                                i17 = i5;
                            } else {
                                i23 = i6;
                                if (i23 == rightColumn) {
                                }
                                i24 = nonPrintablePaintingFlags & 1;
                                if (i24 != 0) {
                                    i6 = i23;
                                    drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, 0, i6);
                                } else {
                                    i6 = i23;
                                }
                                i25 = nonPrintablePaintingFlags & 2;
                                if (i25 != 0) {
                                    drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i6, i5);
                                }
                                i26 = nonPrintablePaintingFlags & 4;
                                if (i26 != 0) {
                                    drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i5, rightColumn);
                                }
                                if ((nonPrintablePaintingFlags & 64) == 0) {
                                    i17 = i5;
                                } else {
                                    i17 = i5;
                                }
                            }
                            charPosition = charPosition8;
                            if (charPosition != null) {
                                i18 = i7;
                                if (i18 == charPosition.line) {
                                    int i410 = charPosition.column;
                                    int i411 = i410 + i31;
                                    i22 = i11;
                                    iMin3 = Math.min(Math.max(i410, i22), i13);
                                    iMin4 = Math.min(Math.max(i411, i22), i13);
                                    if (iMin3 < iMin4) {
                                        this.tmpRect.top = this.editor.getRowBottom(i36) - this.editor.getOffsetY();
                                        RectF rectF7 = this.tmpRect;
                                        rectF7.bottom = rectF7.top + (this.editor.getRowHeight() * 0.06f);
                                        this.tmpRect.left = pixelWidthOrOffset + measureText(this.lineBuf, i18, i22, iMin3 - i22);
                                        RectF rectF8 = this.tmpRect;
                                        rectF8.right = rectF8.left + measureText(this.lineBuf, i18, iMin3, iMin4 - iMin3);
                                        drawColor(canvas, this.editor.getColorScheme().getColor(10), this.tmpRect);
                                    }
                                }
                            } else {
                                i18 = i7;
                            }
                            layout = this.editor.getLayout();
                            if (this.cursor.isSelected()) {
                                if (this.cursor.getLeftLine() == i18) {
                                    row2 = row;
                                    if (isInside(this.cursor.getLeftColumn(), row2.startColumn, row2.endColumn, i18)) {
                                        float fMeasureTextRegionOffset4 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX();
                                        if (this.content.isRtlAt(this.cursor.getLeftLine(), this.cursor.getLeftColumn())) {
                                            i21 = 2;
                                        } else {
                                            i21 = 1;
                                        }
                                        i19 = 1;
                                        list = list;
                                        list.add(new DrawCursorTask(fMeasureTextRegionOffset4, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i21, this.editor.getLeftHandleDescriptor()));
                                    }
                                    if (this.cursor.getRightLine() == i18) {
                                        float fMeasureTextRegionOffset5 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                                        if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                                            i20 = i19;
                                        } else {
                                            i20 = 2;
                                        }
                                        list.add(new DrawCursorTask(fMeasureTextRegionOffset5, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                                    }
                                } else {
                                    row2 = row;
                                }
                                i19 = 1;
                                if (this.cursor.getRightLine() == i18) {
                                    float fMeasureTextRegionOffset6 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                                    if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                                        i20 = i19;
                                    } else {
                                        i20 = 2;
                                    }
                                    list.add(new DrawCursorTask(fMeasureTextRegionOffset6, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                                }
                            } else {
                                list = list;
                                row2 = row;
                                i19 = 1;
                                if (this.cursor.getLeftLine() == i18) {
                                    list.add(new DrawCursorTask((this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX(), getRowBottomForBackground(i36) - this.editor.getOffsetY(), 0, this.editor.getInsertHandleDescriptor()));
                                }
                            }
                            charPosition2 = charPosition7;
                            if (charPosition2 != null) {
                                if (charPosition2.line != i18) {
                                }
                            } else if (!this.editor.isInMouseMode()) {
                            }
                            i36++;
                            charPosition8 = charPosition;
                            charPosition7 = charPosition2;
                            i39 = i17;
                            firstVisibleRow = i42;
                            i37 = i2;
                            i40 = i10;
                            j3 = j;
                            reader6 = reader3;
                            i38 = i6;
                            f11 = f11;
                        }
                    } else {
                        i11 = textOffset;
                        rightColumn = i4;
                        i12 = 0;
                        f3 = 0.0f;
                    }
                    f4 = f2;
                    if (textOffset2 == rightColumn) {
                        drawMiniGraph(canvas, f4, i36, "↵");
                    }
                    i13 = textOffset2;
                    if (fMin == f3) {
                        reader3 = reader3;
                        i17 = i5;
                    } else {
                        i23 = i6;
                        if (i23 == rightColumn) {
                        }
                        i24 = nonPrintablePaintingFlags & 1;
                        if (i24 != 0) {
                            i6 = i23;
                            drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, 0, i6);
                        } else {
                            i6 = i23;
                        }
                        i25 = nonPrintablePaintingFlags & 2;
                        if (i25 != 0) {
                            drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i6, i5);
                        }
                        i26 = nonPrintablePaintingFlags & 4;
                        if (i26 != 0) {
                            drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i5, rightColumn);
                        }
                        if ((nonPrintablePaintingFlags & 64) == 0) {
                            i17 = i5;
                        } else {
                            i17 = i5;
                        }
                    }
                    charPosition = charPosition8;
                    if (charPosition != null) {
                        i18 = i7;
                        if (i18 == charPosition.line) {
                            int i412 = charPosition.column;
                            int i413 = i412 + i31;
                            i22 = i11;
                            iMin3 = Math.min(Math.max(i412, i22), i13);
                            iMin4 = Math.min(Math.max(i413, i22), i13);
                            if (iMin3 < iMin4) {
                                this.tmpRect.top = this.editor.getRowBottom(i36) - this.editor.getOffsetY();
                                RectF rectF9 = this.tmpRect;
                                rectF9.bottom = rectF9.top + (this.editor.getRowHeight() * 0.06f);
                                this.tmpRect.left = pixelWidthOrOffset + measureText(this.lineBuf, i18, i22, iMin3 - i22);
                                RectF rectF10 = this.tmpRect;
                                rectF10.right = rectF10.left + measureText(this.lineBuf, i18, iMin3, iMin4 - iMin3);
                                drawColor(canvas, this.editor.getColorScheme().getColor(10), this.tmpRect);
                            }
                        }
                    } else {
                        i18 = i7;
                    }
                    layout = this.editor.getLayout();
                    if (this.cursor.isSelected()) {
                        if (this.cursor.getLeftLine() == i18) {
                            row2 = row;
                            if (isInside(this.cursor.getLeftColumn(), row2.startColumn, row2.endColumn, i18)) {
                                float fMeasureTextRegionOffset7 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX();
                                if (this.content.isRtlAt(this.cursor.getLeftLine(), this.cursor.getLeftColumn())) {
                                    i21 = 2;
                                } else {
                                    i21 = 1;
                                }
                                i19 = 1;
                                list = list;
                                list.add(new DrawCursorTask(fMeasureTextRegionOffset7, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i21, this.editor.getLeftHandleDescriptor()));
                            }
                            if (this.cursor.getRightLine() == i18) {
                                float fMeasureTextRegionOffset8 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                                if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                                    i20 = i19;
                                } else {
                                    i20 = 2;
                                }
                                list.add(new DrawCursorTask(fMeasureTextRegionOffset8, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                            }
                        } else {
                            row2 = row;
                        }
                        i19 = 1;
                        if (this.cursor.getRightLine() == i18) {
                            float fMeasureTextRegionOffset9 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                            if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                                i20 = i19;
                            } else {
                                i20 = 2;
                            }
                            list.add(new DrawCursorTask(fMeasureTextRegionOffset9, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                        }
                    } else {
                        list = list;
                        row2 = row;
                        i19 = 1;
                        if (this.cursor.getLeftLine() == i18) {
                            list.add(new DrawCursorTask((this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX(), getRowBottomForBackground(i36) - this.editor.getOffsetY(), 0, this.editor.getInsertHandleDescriptor()));
                        }
                    }
                    charPosition2 = charPosition7;
                    if (charPosition2 != null) {
                        if (charPosition2.line != i18) {
                        }
                    } else if (!this.editor.isInMouseMode()) {
                    }
                    i36++;
                    charPosition8 = charPosition;
                    charPosition7 = charPosition2;
                    i39 = i17;
                    firstVisibleRow = i42;
                    i37 = i2;
                    i40 = i10;
                    j3 = j;
                    reader6 = reader3;
                    i38 = i6;
                    f11 = f11;
                }
            }
            if (i37 != i41) {
                prepareLine(i41);
                if (reader6 != null) {
                    reader6.moveToLine(-1);
                }
                if (spans == null) {
                    emptyReader = EmptyReader.getInstance();
                } else {
                    emptyReader = spans.read();
                }
                emptyReader.moveToLine(i41);
                if (emptyReader.getSpanCount() == 0) {
                    emptyReader = EmptyReader.getInstance();
                }
                if (this.editor.shouldInitializeNonPrintable()) {
                    long jFindLeadingAndTrailingWhitespacePos2 = this.editor.findLeadingAndTrailingWhitespacePos(this.lineBuf);
                    first = IntPair.getFirst(jFindLeadingAndTrailingWhitespacePos2);
                    second = IntPair.getSecond(jFindLeadingAndTrailingWhitespacePos2);
                    i2 = i41;
                    reader = emptyReader;
                    i3 = 0;
                } else {
                    first = i;
                    reader = emptyReader;
                    second = i39;
                    i3 = 0;
                    i2 = i41;
                }
            } else {
                second = i39;
                i2 = i37;
                i3 = i40;
                reader = reader6;
                first = i;
            }
            long jFindDesiredVisibleChar2 = findDesiredVisibleChar(f11, i41, next2.startColumn, next2.endColumn);
            textOffset = CharPosDesc.getTextOffset(jFindDesiredVisibleChar2);
            pixelWidthOrOffset = CharPosDesc.getPixelWidthOrOffset(jFindDesiredVisibleChar2) - offsetX;
            int i414 = i3;
            reader2 = reader;
            i4 = length;
            i5 = second;
            i6 = first;
            i7 = i41;
            j = j3;
            row = next2;
            textOffset2 = CharPosDesc.getTextOffset(findDesiredVisibleChar(this.editor.getWidth() - pixelWidthOrOffset, i41, textOffset, next2.endColumn, next2.startColumn, true));
            nonPrintablePaintingFlags = this.editor.getNonPrintablePaintingFlags();
            if (this.editor.isHardwareAcceleratedDrawAllowed()) {
                c = 29;
                i8 = i414;
                while (true) {
                    i9 = i8 + 1;
                    reader3 = reader2;
                    if (i9 < reader2.getSpanCount()) {
                        break;
                    }
                    break;
                    break;
                    i8 = i9;
                    reader2 = reader3;
                }
                i10 = i8;
                spanAt = reader3.getSpanAt(i8);
                f2 = pixelWidthOrOffset;
                while (true) {
                    if (textOffset2 > spanAt.getColumn()) {
                        i14 = i10 + 1;
                        if (i14 >= reader3.getSpanCount()) {
                            column = i4;
                            i15 = column;
                        } else {
                            column = reader3.getSpanAt(i14).getColumn();
                            i15 = i4;
                        }
                        iMin = Math.min(i15, column);
                        i11 = textOffset;
                        iMax = Math.max(0, Math.max(textOffset, spanAt.getColumn()));
                        if (iMax >= i15) {
                            rightColumn = i15;
                            i12 = 0;
                            f3 = 0.0f;
                        } else {
                            iMin2 = Math.min(i15, Math.min(textOffset2, iMin));
                            if (iMax > iMin2) {
                                rightColumn = i15;
                                f3 = 0.0f;
                                i12 = 0;
                            } else {
                                int i415 = i15;
                                fMeasureText = measureText(this.lineBuf, i7, iMax, iMin2 - iMax);
                                spanExternalRenderer = (SpanExternalRenderer) spanAt.getSpanExt(1);
                                if (spanExternalRenderer == null) {
                                    i16 = textOffset2;
                                } else {
                                    i16 = textOffset2;
                                }
                                styleBits = spanAt.getStyleBits();
                                if (spanAt.getStyleBits() != j) {
                                    this.paintGeneral.setFakeBoldText(TextStyle.isBold(styleBits));
                                    if (TextStyle.isItalics(styleBits)) {
                                        this.paintGeneral.setTextSkewX(-0.2f);
                                    } else {
                                        this.paintGeneral.setTextSkewX(0.0f);
                                    }
                                    j = styleBits;
                                }
                                backgroundColor = RendererUtils.getBackgroundColor(spanAt, this.editor.getColorScheme());
                                if (backgroundColor != 0) {
                                    this.tmpRect.top = this.editor.getRowTop(i36) - this.editor.getOffsetY();
                                    this.tmpRect.bottom = this.editor.getRowBottom(i36) - this.editor.getOffsetY();
                                    this.tmpRect.left = f2;
                                    RectF rectF11 = this.tmpRect;
                                    rectF11.right = rectF11.left + fMeasureText;
                                    this.paintGeneral.setColor(backgroundColor);
                                    canvas.drawRoundRect(this.tmpRect, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.paintGeneral);
                                }
                                str = str3;
                                f5 = f2;
                                Span span2 = spanAt;
                                reader4 = reader3;
                                int i51 = i16;
                                row = row;
                                i7 = i7;
                                i6 = i6;
                                f11 = f11;
                                drawRegionTextDirectional(canvas, f2, this.editor.getRowBaseline(i36) - this.editor.getOffsetY(), i7, iMax, iMin2, spanAt.getColumn(), iMin, i415, RendererUtils.getForegroundColor(spanAt, this.editor.getColorScheme()));
                                if (TextStyle.isStrikeThrough(styleBits)) {
                                    color = this.editor.getColorScheme().getColor(57);
                                    io.github.rosemoe.sora.graphics.Paint paint2 = this.paintOther;
                                    if (color == 0) {
                                        color = this.paintGeneral.getColor();
                                    }
                                    paint2.setColor(color);
                                    f6 = f5;
                                    canvas.drawLine(f6, (this.editor.getRowTop(i36) + (this.editor.getRowHeight() / 2.0f)) - this.editor.getOffsetY(), f6 + fMeasureText, (this.editor.getRowTop(i36) + (this.editor.getRowHeight() / 2.0f)) - this.editor.getOffsetY(), this.paintOther);
                                } else {
                                    f6 = f5;
                                }
                                underlineColor = span2.getUnderlineColor();
                                if (underlineColor != null) {
                                    this.tmpRect.bottom = (this.editor.getRowBottom(i36) - this.editor.getOffsetY()) - (this.editor.getDpUnit() * 1.0f);
                                    RectF rectF12 = this.tmpRect;
                                    rectF12.top = rectF12.bottom - (this.editor.getRowHeight() * 0.06f);
                                    this.tmpRect.left = f6;
                                    this.tmpRect.right = f6 + fMeasureText;
                                    drawColor(canvas, iResolve, this.tmpRect);
                                }
                                if (spanExternalRenderer == null) {
                                    str2 = str;
                                    i12 = 0;
                                    f3 = 0.0f;
                                } else {
                                    str2 = str;
                                    i12 = 0;
                                    f3 = 0.0f;
                                }
                                f7 = f6 + fMeasureText;
                                textOffset2 = i51;
                                rightColumn = i415;
                                if (iMin2 < textOffset2) {
                                }
                                reader3 = reader4;
                                f4 = f7;
                            }
                            if (textOffset2 == rightColumn) {
                                drawMiniGraph(canvas, f4, i36, "↵");
                            }
                            i13 = textOffset2;
                        }
                    } else {
                        i11 = textOffset;
                        rightColumn = i4;
                        i12 = 0;
                        f3 = 0.0f;
                    }
                    f4 = f2;
                    if (textOffset2 == rightColumn) {
                        drawMiniGraph(canvas, f4, i36, "↵");
                    }
                    i13 = textOffset2;
                    textOffset = i11;
                    textOffset2 = textOffset2;
                    reader3 = reader5;
                    i4 = rightColumn;
                    f11 = f11;
                    i7 = i7;
                    c = 29;
                    row = row;
                    i6 = i6;
                    str3 = str2;
                    f2 = f7;
                }
            } else {
                c = 29;
                i8 = i414;
                while (true) {
                    i9 = i8 + 1;
                    reader3 = reader2;
                    if (i9 < reader2.getSpanCount()) {
                        break;
                        break;
                    } else {
                        break;
                        break;
                    }
                    i8 = i9;
                    reader2 = reader3;
                }
                i10 = i8;
                spanAt = reader3.getSpanAt(i8);
                f2 = pixelWidthOrOffset;
                while (true) {
                    if (textOffset2 > spanAt.getColumn()) {
                        i14 = i10 + 1;
                        if (i14 >= reader3.getSpanCount()) {
                            column = i4;
                            i15 = column;
                        } else {
                            column = reader3.getSpanAt(i14).getColumn();
                            i15 = i4;
                        }
                        iMin = Math.min(i15, column);
                        i11 = textOffset;
                        iMax = Math.max(0, Math.max(textOffset, spanAt.getColumn()));
                        if (iMax >= i15) {
                            rightColumn = i15;
                            i12 = 0;
                            f3 = 0.0f;
                        } else {
                            iMin2 = Math.min(i15, Math.min(textOffset2, iMin));
                            if (iMax > iMin2) {
                                rightColumn = i15;
                                f3 = 0.0f;
                                i12 = 0;
                            } else {
                                int i416 = i15;
                                fMeasureText = measureText(this.lineBuf, i7, iMax, iMin2 - iMax);
                                spanExternalRenderer = (SpanExternalRenderer) spanAt.getSpanExt(1);
                                if (spanExternalRenderer == null) {
                                    i16 = textOffset2;
                                } else {
                                    i16 = textOffset2;
                                }
                                styleBits = spanAt.getStyleBits();
                                if (spanAt.getStyleBits() != j) {
                                    this.paintGeneral.setFakeBoldText(TextStyle.isBold(styleBits));
                                    if (TextStyle.isItalics(styleBits)) {
                                        this.paintGeneral.setTextSkewX(-0.2f);
                                    } else {
                                        this.paintGeneral.setTextSkewX(0.0f);
                                    }
                                    j = styleBits;
                                }
                                backgroundColor = RendererUtils.getBackgroundColor(spanAt, this.editor.getColorScheme());
                                if (backgroundColor != 0) {
                                    this.tmpRect.top = this.editor.getRowTop(i36) - this.editor.getOffsetY();
                                    this.tmpRect.bottom = this.editor.getRowBottom(i36) - this.editor.getOffsetY();
                                    this.tmpRect.left = f2;
                                    RectF rectF13 = this.tmpRect;
                                    rectF13.right = rectF13.left + fMeasureText;
                                    this.paintGeneral.setColor(backgroundColor);
                                    canvas.drawRoundRect(this.tmpRect, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.paintGeneral);
                                }
                                str = str3;
                                f5 = f2;
                                Span span3 = spanAt;
                                reader4 = reader3;
                                int i52 = i16;
                                row = row;
                                i7 = i7;
                                i6 = i6;
                                f11 = f11;
                                drawRegionTextDirectional(canvas, f2, this.editor.getRowBaseline(i36) - this.editor.getOffsetY(), i7, iMax, iMin2, spanAt.getColumn(), iMin, i416, RendererUtils.getForegroundColor(spanAt, this.editor.getColorScheme()));
                                if (TextStyle.isStrikeThrough(styleBits)) {
                                    color = this.editor.getColorScheme().getColor(57);
                                    io.github.rosemoe.sora.graphics.Paint paint3 = this.paintOther;
                                    if (color == 0) {
                                        color = this.paintGeneral.getColor();
                                    }
                                    paint3.setColor(color);
                                    f6 = f5;
                                    canvas.drawLine(f6, (this.editor.getRowTop(i36) + (this.editor.getRowHeight() / 2.0f)) - this.editor.getOffsetY(), f6 + fMeasureText, (this.editor.getRowTop(i36) + (this.editor.getRowHeight() / 2.0f)) - this.editor.getOffsetY(), this.paintOther);
                                } else {
                                    f6 = f5;
                                }
                                underlineColor = span3.getUnderlineColor();
                                if (underlineColor != null) {
                                    this.tmpRect.bottom = (this.editor.getRowBottom(i36) - this.editor.getOffsetY()) - (this.editor.getDpUnit() * 1.0f);
                                    RectF rectF14 = this.tmpRect;
                                    rectF14.top = rectF14.bottom - (this.editor.getRowHeight() * 0.06f);
                                    this.tmpRect.left = f6;
                                    this.tmpRect.right = f6 + fMeasureText;
                                    drawColor(canvas, iResolve, this.tmpRect);
                                }
                                if (spanExternalRenderer == null) {
                                    str2 = str;
                                    i12 = 0;
                                    f3 = 0.0f;
                                } else {
                                    str2 = str;
                                    i12 = 0;
                                    f3 = 0.0f;
                                }
                                f7 = f6 + fMeasureText;
                                textOffset2 = i52;
                                rightColumn = i416;
                                if (iMin2 < textOffset2) {
                                }
                                reader3 = reader4;
                                f4 = f7;
                            }
                            if (textOffset2 == rightColumn) {
                                drawMiniGraph(canvas, f4, i36, "↵");
                            }
                            i13 = textOffset2;
                        }
                    } else {
                        i11 = textOffset;
                        rightColumn = i4;
                        i12 = 0;
                        f3 = 0.0f;
                    }
                    f4 = f2;
                    if (textOffset2 == rightColumn) {
                        drawMiniGraph(canvas, f4, i36, "↵");
                    }
                    i13 = textOffset2;
                    textOffset = i11;
                    textOffset2 = textOffset2;
                    reader3 = reader5;
                    i4 = rightColumn;
                    f11 = f11;
                    i7 = i7;
                    c = 29;
                    row = row;
                    i6 = i6;
                    str3 = str2;
                    f2 = f7;
                }
            }
            if (fMin == f3) {
                reader3 = reader3;
                i17 = i5;
            } else {
                i23 = i6;
                if (i23 == rightColumn) {
                }
                i24 = nonPrintablePaintingFlags & 1;
                if (i24 != 0) {
                    i6 = i23;
                    drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, 0, i6);
                } else {
                    i6 = i23;
                }
                i25 = nonPrintablePaintingFlags & 2;
                if (i25 != 0) {
                    drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i6, i5);
                }
                i26 = nonPrintablePaintingFlags & 4;
                if (i26 != 0) {
                    drawWhitespaces(canvas, pixelWidthOrOffset, i7, i36, i11, i13, i5, rightColumn);
                }
                if ((nonPrintablePaintingFlags & 64) == 0) {
                    i17 = i5;
                } else {
                    i17 = i5;
                }
            }
            charPosition = charPosition8;
            if (charPosition != null) {
                i18 = i7;
                if (i18 == charPosition.line) {
                    int i417 = charPosition.column;
                    int i418 = i417 + i31;
                    i22 = i11;
                    iMin3 = Math.min(Math.max(i417, i22), i13);
                    iMin4 = Math.min(Math.max(i418, i22), i13);
                    if (iMin3 < iMin4) {
                        this.tmpRect.top = this.editor.getRowBottom(i36) - this.editor.getOffsetY();
                        RectF rectF15 = this.tmpRect;
                        rectF15.bottom = rectF15.top + (this.editor.getRowHeight() * 0.06f);
                        this.tmpRect.left = pixelWidthOrOffset + measureText(this.lineBuf, i18, i22, iMin3 - i22);
                        RectF rectF16 = this.tmpRect;
                        rectF16.right = rectF16.left + measureText(this.lineBuf, i18, iMin3, iMin4 - iMin3);
                        drawColor(canvas, this.editor.getColorScheme().getColor(10), this.tmpRect);
                    }
                }
            } else {
                i18 = i7;
            }
            layout = this.editor.getLayout();
            if (this.cursor.isSelected()) {
                if (this.cursor.getLeftLine() == i18) {
                    row2 = row;
                    if (isInside(this.cursor.getLeftColumn(), row2.startColumn, row2.endColumn, i18)) {
                        float fMeasureTextRegionOffset10 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX();
                        if (this.content.isRtlAt(this.cursor.getLeftLine(), this.cursor.getLeftColumn())) {
                            i21 = 2;
                        } else {
                            i21 = 1;
                        }
                        i19 = 1;
                        list = list;
                        list.add(new DrawCursorTask(fMeasureTextRegionOffset10, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i21, this.editor.getLeftHandleDescriptor()));
                    }
                    if (this.cursor.getRightLine() == i18) {
                        float fMeasureTextRegionOffset11 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                        if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                            i20 = i19;
                        } else {
                            i20 = 2;
                        }
                        list.add(new DrawCursorTask(fMeasureTextRegionOffset11, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                    }
                } else {
                    row2 = row;
                }
                i19 = 1;
                if (this.cursor.getRightLine() == i18) {
                    float fMeasureTextRegionOffset12 = (this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getRightLine(), this.cursor.getRightColumn())[i19]) - this.editor.getOffsetX();
                    if (this.content.isRtlAt(this.cursor.getRightLine(), this.cursor.getRightColumn())) {
                        i20 = i19;
                    } else {
                        i20 = 2;
                    }
                    list.add(new DrawCursorTask(fMeasureTextRegionOffset12, getRowBottomForBackground(i36) - this.editor.getOffsetY(), i20, this.editor.getRightHandleDescriptor()));
                }
            } else {
                list = list;
                row2 = row;
                i19 = 1;
                if (this.cursor.getLeftLine() == i18) {
                    list.add(new DrawCursorTask((this.editor.measureTextRegionOffset() + layout.getCharLayoutOffset(this.cursor.getLeftLine(), this.cursor.getLeftColumn())[1]) - this.editor.getOffsetX(), getRowBottomForBackground(i36) - this.editor.getOffsetY(), 0, this.editor.getInsertHandleDescriptor()));
                }
            }
            charPosition2 = charPosition7;
            if (charPosition2 != null) {
                if (charPosition2.line != i18) {
                }
            } else if (!this.editor.isInMouseMode()) {
            }
            i36++;
            charPosition8 = charPosition;
            charPosition7 = charPosition2;
            i39 = i17;
            firstVisibleRow = i42;
            i37 = i2;
            i40 = i10;
            j3 = j;
            reader6 = reader3;
            i38 = i6;
            f11 = f11;
        }
        if (reader6 != null) {
            try {
                reader6.moveToLine(-1);
            } catch (Exception e7) {
                Log.w(LOG_TAG, "Failed to release SpanReader", e7);
            }
        }
        this.paintGeneral.setFakeBoldText(false);
        this.paintGeneral.setTextSkewX(0.0f);
        this.paintOther.setStrokeWidth(fMin * 2.0f);
        this.bufferedDrawPoints.commitPoints(canvas, this.paintOther);
    }

    protected void drawDiagnosticIndicators(Canvas canvas, float f) {
        float fMeasureText;
        float f2;
        DiagnosticsContainer diagnostics = this.editor.getDiagnostics();
        DiagnosticIndicatorStyle diagnosticIndicatorStyle = this.editor.getDiagnosticIndicatorStyle();
        if (diagnostics != null && diagnosticIndicatorStyle != DiagnosticIndicatorStyle.NONE && diagnosticIndicatorStyle != null) {
            Content content = this.content;
            int firstVisibleRow = this.editor.getFirstVisibleRow();
            int lastVisibleRow = this.editor.getLastVisibleRow();
            int charIndex = content.getCharIndex(this.editor.getFirstVisibleLine(), 0);
            int iMin = Math.min(content.getLineCount() - 1, this.editor.getLastVisibleLine() + 1);
            int charIndex2 = content.getCharIndex(iMin, 0) + content.getColumnCount(iMin);
            diagnostics.queryInRegion(this.collectedDiagnostics, charIndex, charIndex2);
            if (this.collectedDiagnostics.isEmpty()) {
                return;
            }
            float dpUnit = this.editor.getDpUnit() * this.editor.getProps().indicatorWaveLength;
            float dpUnit2 = this.editor.getDpUnit() * this.editor.getProps().indicatorWaveAmplitude;
            float dpUnit3 = this.editor.getDpUnit() * this.editor.getProps().indicatorWaveWidth;
            CharPosition charPosition = new CharPosition();
            CharPosition charPosition2 = new CharPosition();
            CachedIndexer indexer = this.cursor.getIndexer();
            for (DiagnosticRegion diagnosticRegion : this.collectedDiagnostics) {
                int iMax = Math.max(charIndex, diagnosticRegion.startIndex);
                int iMin2 = Math.min(charIndex2, diagnosticRegion.endIndex);
                indexer.getCharPosition(iMax, charPosition);
                indexer.getCharPosition(iMin2, charPosition2);
                CachedIndexer cachedIndexer = indexer;
                int rowIndexForPosition = this.editor.getLayout().getRowIndexForPosition(iMax);
                int rowIndexForPosition2 = this.editor.getLayout().getRowIndexForPosition(iMin2);
                int i = charIndex;
                int i2 = (diagnosticRegion.severity < 0 || diagnosticRegion.severity > 3) ? 0 : sDiagnosticsColorMapping[diagnosticRegion.severity];
                if (i2 == 0) {
                    break;
                }
                this.paintOther.setColor(this.editor.getColorScheme().getColor(i2));
                int iMax2 = Math.max(firstVisibleRow, rowIndexForPosition);
                int iMin3 = Math.min(lastVisibleRow, rowIndexForPosition2);
                int i3 = iMax2;
                while (i3 <= iMin3) {
                    Row rowAt = this.editor.getLayout().getRowAt(i3);
                    float fMeasureText2 = i3 == iMax2 ? measureText(getLine(rowAt.lineIndex), rowAt.lineIndex, rowAt.startColumn, Math.max(charPosition.column - rowAt.startColumn, 0)) : 0.0f;
                    if (i3 != iMin3) {
                        fMeasureText = measureText(getLine(rowAt.lineIndex), rowAt.lineIndex, rowAt.startColumn, rowAt.endColumn - rowAt.startColumn);
                    } else {
                        fMeasureText = measureText(getLine(rowAt.lineIndex), rowAt.lineIndex, rowAt.startColumn, Math.max(0, charPosition2.column - rowAt.startColumn));
                    }
                    float f3 = fMeasureText2 + f;
                    float f4 = fMeasureText + f;
                    float fMeasureText3 = ((double) Math.abs(f3 - f4)) < 0.01d ? this.paintGeneral.measureText("a") + f3 : f4;
                    if (fMeasureText3 > 0.0f && f3 < this.editor.getWidth()) {
                        float rowBottom = this.editor.getRowBottom(i3) - this.editor.getOffsetY();
                        int i4 = AnonymousClass1.$SwitchMap$io$github$rosemoe$sora$widget$style$DiagnosticIndicatorStyle[diagnosticIndicatorStyle.ordinal()];
                        if (i4 == 1) {
                            charPosition2 = charPosition2;
                            diagnosticIndicatorStyle = diagnosticIndicatorStyle;
                            iMin3 = iMin3;
                            dpUnit2 = dpUnit2;
                            charPosition = charPosition;
                            f2 = dpUnit3;
                            float f5 = 0.0f - f3;
                            float fCeil = f5 < 0.0f ? 0.0f : (((int) Math.ceil(f5 / dpUnit)) * dpUnit) - f5;
                            canvas.save();
                            canvas.clipRect(f3, 0.0f, fMeasureText3, canvas.getHeight());
                            canvas.translate(f3, rowBottom);
                            this.tmpPath.reset();
                            this.tmpPath.moveTo(0.0f, 0.0f);
                            int iCeil = (int) Math.ceil((fCeil + (fMeasureText3 - f3)) / dpUnit);
                            for (int i5 = 0; i5 < iCeil; i5++) {
                                float f6 = i5 * dpUnit;
                                this.tmpPath.quadTo((dpUnit / 4.0f) + f6, dpUnit2, (dpUnit / 2.0f) + f6, 0.0f);
                                this.tmpPath.quadTo(((dpUnit * 3.0f) / 4.0f) + f6, -dpUnit2, f6 + dpUnit, 0.0f);
                            }
                            this.paintOther.setStrokeWidth(f2);
                            this.paintOther.setStyle(Paint.Style.STROKE);
                            canvas.drawPath(this.tmpPath, this.paintOther);
                            canvas.restore();
                            this.paintOther.setStyle(Paint.Style.FILL);
                        } else if (i4 == 2) {
                            charPosition2 = charPosition2;
                            diagnosticIndicatorStyle = diagnosticIndicatorStyle;
                            iMin3 = iMin3;
                            dpUnit2 = dpUnit2;
                            charPosition = charPosition;
                            f2 = dpUnit3;
                            this.paintOther.setStrokeWidth(f2);
                            canvas.drawLine(f3, rowBottom, fMeasureText3, rowBottom, this.paintOther);
                        } else if (i4 == 3) {
                            this.paintOther.setStrokeWidth(dpUnit3 / 3.0f);
                            charPosition2 = charPosition2;
                            charPosition = charPosition;
                            float f7 = fMeasureText3;
                            diagnosticIndicatorStyle = diagnosticIndicatorStyle;
                            f2 = dpUnit3;
                            iMin3 = iMin3;
                            dpUnit2 = dpUnit2;
                            canvas.drawLine(f3, rowBottom, f7, rowBottom, this.paintOther);
                            float f8 = rowBottom - f2;
                            canvas.drawLine(f3, f8, f7, f8, this.paintOther);
                        }
                        i3++;
                        dpUnit3 = f2;
                        dpUnit2 = dpUnit2;
                        charPosition = charPosition;
                        iMax2 = iMax2;
                        firstVisibleRow = firstVisibleRow;
                        lastVisibleRow = lastVisibleRow;
                        charPosition2 = charPosition2;
                        diagnosticIndicatorStyle = diagnosticIndicatorStyle;
                        iMin3 = iMin3;
                    }
                    f2 = dpUnit3;
                    i3++;
                    dpUnit3 = f2;
                    dpUnit2 = dpUnit2;
                    charPosition = charPosition;
                    iMax2 = iMax2;
                    firstVisibleRow = firstVisibleRow;
                    lastVisibleRow = lastVisibleRow;
                    charPosition2 = charPosition2;
                    diagnosticIndicatorStyle = diagnosticIndicatorStyle;
                    iMin3 = iMin3;
                }
                indexer = cachedIndexer;
                charIndex = i;
            }
        }
        this.collectedDiagnostics.clear();
    }

    /* JADX INFO: renamed from: io.github.rosemoe.sora.widget.EditorRenderer$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$graphics$Paint$Align;
        static final /* synthetic */ int[] $SwitchMap$io$github$rosemoe$sora$widget$style$DiagnosticIndicatorStyle;

        static {
            int[] iArr = new int[DiagnosticIndicatorStyle.values().length];
            $SwitchMap$io$github$rosemoe$sora$widget$style$DiagnosticIndicatorStyle = iArr;
            try {
                iArr[DiagnosticIndicatorStyle.WAVY_LINE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$widget$style$DiagnosticIndicatorStyle[DiagnosticIndicatorStyle.LINE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$widget$style$DiagnosticIndicatorStyle[DiagnosticIndicatorStyle.DOUBLE_LINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            int[] iArr2 = new int[Paint.Align.values().length];
            $SwitchMap$android$graphics$Paint$Align = iArr2;
            try {
                iArr2[Paint.Align.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$android$graphics$Paint$Align[Paint.Align.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$graphics$Paint$Align[Paint.Align.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    protected void drawWhitespaces(Canvas canvas, float f, int i, int i2, int i3, int i4, int i5, int i6) {
        boolean z;
        char c;
        int i7 = i;
        int iMax = Math.max(i3, Math.min(i4, i5));
        int iMax2 = Math.max(i3, Math.min(i4, i6));
        this.paintOther.setColor(this.editor.getColorScheme().getColor(31));
        if (iMax < iMax2) {
            float spaceWidth = this.paintGeneral.getSpaceWidth();
            float f2 = 2.0f;
            float rowTop = ((this.editor.getRowTop(i2) + this.editor.getRowBottom(i2)) / 2.0f) - this.editor.getOffsetY();
            float fMeasureText = f + measureText(this.lineBuf, i7, i3, iMax - i3);
            char[] cArr = this.lineBuf.value;
            int i8 = iMax;
            while (iMax < iMax2) {
                char c2 = cArr[iMax];
                if (c2 == ' ' || c2 == '\t') {
                    fMeasureText += measureText(this.lineBuf, i7, i8, iMax - i8);
                }
                int i9 = 0;
                int tabWidth = 1;
                if (c2 == ' ') {
                    z = false;
                } else if (c2 != '\t') {
                    tabWidth = 0;
                    z = false;
                } else if ((this.editor.getNonPrintablePaintingFlags() & 32) != 0) {
                    tabWidth = this.editor.getTabWidth();
                    z = false;
                } else {
                    z = true;
                    tabWidth = 0;
                }
                while (i9 < tabWidth) {
                    float f3 = (i9 * spaceWidth) + fMeasureText;
                    this.bufferedDrawPoints.drawPoint((f3 + (f3 + spaceWidth)) / f2, rowTop);
                    i9++;
                    f2 = 2.0f;
                }
                if (z) {
                    float tabWidth2 = this.editor.getTabWidth() * spaceWidth;
                    float f4 = 0.05f * tabWidth2;
                    float f5 = (tabWidth2 + fMeasureText) - f4;
                    io.github.rosemoe.sora.graphics.Paint paint = this.paintOther;
                    c = StringUtil.SPACE;
                    canvas.drawLine(fMeasureText + f4, rowTop, f5, rowTop, paint);
                } else {
                    c = StringUtil.SPACE;
                }
                if (c2 == c || c2 == '\t') {
                    fMeasureText += c2 == c ? spaceWidth : this.editor.getTabWidth() * spaceWidth;
                    i8 = iMax + 1;
                }
                iMax++;
                i7 = i;
                rowTop = rowTop;
                f2 = 2.0f;
            }
        }
    }

    protected void drawMiniGraph(Canvas canvas, float f, int i, String str) {
        this.paintGraph.setColor(this.editor.getColorScheme().getColor(31));
        canvas.drawText(str, 0, str.length(), f, (i == -1 ? this.editor.getRowBottom(0) : this.editor.getRowBottom(i) - this.editor.getOffsetY()) - this.metricsGraph.descent, (Paint) this.paintGraph);
    }

    protected int getRowTopForBackground(int i) {
        if (!this.editor.getProps().textBackgroundWrapTextOnly) {
            return this.editor.getRowTop(i);
        }
        return this.editor.getRowTopOfText(i);
    }

    protected int getRowBottomForBackground(int i) {
        if (!this.editor.getProps().textBackgroundWrapTextOnly) {
            return this.editor.getRowBottom(i);
        }
        return this.editor.getRowBottomOfText(i);
    }

    protected void drawRowRegionBackground(Canvas canvas, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        float fMeasureText;
        float fMeasureText2;
        float f;
        Directions directions;
        int i8 = i2;
        int iMax = Math.max(i3, i5);
        int iMin = Math.min(i4, i6);
        if (iMax < iMin) {
            this.tmpRect.top = getRowTopForBackground(i) - this.editor.getOffsetY();
            this.tmpRect.bottom = getRowBottomForBackground(i) - this.editor.getOffsetY();
            Directions lineDirections = getLineDirections(i8);
            ContentLine line = getLine(i8);
            this.paintGeneral.setColor(i7);
            float fMeasureTextRegionOffset = this.editor.measureTextRegionOffset() - this.editor.getOffsetX();
            boolean z = true;
            int i9 = 0;
            while (i9 < lineDirections.getRunCount()) {
                int iMax2 = Math.max(iMax, lineDirections.getRunStart(i9));
                int iMin2 = Math.min(iMin, lineDirections.getRunEnd(i9));
                if (lineDirections.getRunStart(i9) >= iMin) {
                    break;
                }
                int iMax3 = Math.max(i5, lineDirections.getRunStart(i9));
                int iMin3 = Math.min(i6, lineDirections.getRunEnd(i9));
                float fMeasureText3 = iMin3 <= iMax3 ? 0.0f : measureText(line, i8, iMax3, iMin3 - iMax3);
                if (iMax2 >= iMin2) {
                    fMeasureTextRegionOffset += fMeasureText3;
                    directions = lineDirections;
                } else {
                    if (lineDirections.isRunRtl(i9)) {
                        float f2 = fMeasureTextRegionOffset + fMeasureText3;
                        fMeasureText = f2 - measureText(line, i8, iMax3, iMax2 - iMax3);
                        fMeasureText2 = f2 - measureText(line, i8, iMax3, iMin2 - iMax3);
                    } else {
                        fMeasureText = measureText(line, i8, iMax3, iMax2 - iMax3) + fMeasureTextRegionOffset;
                        fMeasureText2 = fMeasureTextRegionOffset + measureText(line, i8, iMax3, iMin2 - iMax3);
                    }
                    if (fMeasureText > fMeasureText2) {
                        f = fMeasureText;
                        fMeasureText = fMeasureText2;
                    } else {
                        f = fMeasureText2;
                    }
                    if (z) {
                        this.tmpRect.left = fMeasureText;
                        this.tmpRect.right = f;
                        directions = lineDirections;
                        z = false;
                    } else {
                        directions = lineDirections;
                        if (Math.abs(fMeasureText - this.tmpRect.right) < 0.01d) {
                            this.tmpRect.right = f;
                        } else if (Math.abs(f - this.tmpRect.left) < 0.01d) {
                            this.tmpRect.left = fMeasureText;
                        } else {
                            drawRowBackgroundRect(canvas, this.tmpRect);
                            this.tmpRect.left = fMeasureText;
                            this.tmpRect.right = f;
                        }
                    }
                    fMeasureTextRegionOffset += fMeasureText3;
                }
                i9++;
                i8 = i2;
                lineDirections = directions;
            }
            if (z) {
                return;
            }
            drawRowBackgroundRect(canvas, this.tmpRect);
        }
    }

    protected void drawRowBackgroundRect(Canvas canvas, RectF rectF) {
        if (this.editor.getProps().enableRoundTextBackground) {
            canvas.drawRoundRect(rectF, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.paintGeneral);
        } else {
            canvas.drawRect(rectF, this.paintGeneral);
        }
    }

    protected void drawRegionText(Canvas canvas, float f, float f2, int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7) {
        boolean z2 = this.cursor.isSelected() && i >= this.cursor.getLeftLine() && i <= this.cursor.getRightLine();
        int i8 = i5 - i4;
        int leftColumn = i == this.cursor.getLeftLine() ? this.cursor.getLeftColumn() : 0;
        int rightColumn = i == this.cursor.getRightLine() ? this.cursor.getRightColumn() : i6;
        this.paintGeneral.setColor(i7);
        if (!z2 || this.editor.getColorScheme().getColor(30) == 0) {
            drawText(canvas, this.lineBuf, i2, i3 - i2, i4, i8, z, f, f2, i);
            return;
        }
        if (i3 <= leftColumn || i2 >= rightColumn) {
            drawText(canvas, this.lineBuf, i2, i3 - i2, i4, i8, z, f, f2, i);
            return;
        }
        if (i2 > leftColumn) {
            int i9 = rightColumn;
            if (i3 > i9) {
                ContentLine contentLine = this.lineBuf;
                int i10 = i9 - i2;
                drawText(canvas, contentLine, i9, i3 - i9, i4, i8, z, f + measureText(contentLine, i, i2, i10), f2, i);
                this.paintGeneral.setColor(this.editor.getColorScheme().getColor(30));
                drawText(canvas, this.lineBuf, i2, i10, i4, i8, z, f, f2, i);
                return;
            }
            this.paintGeneral.setColor(this.editor.getColorScheme().getColor(30));
            drawText(canvas, this.lineBuf, i2, i3 - i2, i4, i8, z, f, f2, i);
            return;
        }
        if (i3 >= rightColumn) {
            int i11 = leftColumn - i2;
            int i12 = rightColumn;
            drawText(canvas, this.lineBuf, i2, i11, i4, i8, z, f, f2, i);
            float fMeasureText = measureText(this.lineBuf, i, i2, i11);
            this.paintGeneral.setColor(this.editor.getColorScheme().getColor(30));
            int i13 = i12 - leftColumn;
            drawText(canvas, this.lineBuf, leftColumn, i13, i4, i8, z, f + fMeasureText, f2, i);
            float fMeasureText2 = fMeasureText + measureText(this.lineBuf, i, leftColumn, i13);
            this.paintGeneral.setColor(i7);
            drawText(canvas, this.lineBuf, i12, i3 - i12, i4, i8, z, f + fMeasureText2, f2, i);
            return;
        }
        int i14 = leftColumn - i2;
        drawText(canvas, this.lineBuf, i2, i14, i4, i8, z, f, f2, i);
        this.paintGeneral.setColor(this.editor.getColorScheme().getColor(30));
        ContentLine contentLine2 = this.lineBuf;
        drawText(canvas, contentLine2, leftColumn, i3 - leftColumn, i4, i8, z, f + measureText(contentLine2, i, i2, i14), f2, i);
    }

    protected void drawRegionTextDirectional(Canvas canvas, float f, float f2, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        EditorRenderer editorRenderer;
        EditorRenderer editorRenderer2 = this;
        Directions lineDirections = editorRenderer2.getLineDirections(i);
        float fMeasureText = 0.0f;
        int i8 = 0;
        while (i8 < lineDirections.getRunCount()) {
            int iMax = Math.max(lineDirections.getRunStart(i8), i2);
            int iMin = Math.min(lineDirections.getRunEnd(i8), i3);
            if (iMin > iMax) {
                drawRegionText(canvas, f + fMeasureText, f2, i, iMax, iMin, i4, i5, lineDirections.isRunRtl(i8), i6, i7);
            }
            i8++;
            if (i8 >= lineDirections.getRunCount() || iMin <= iMax) {
                editorRenderer = this;
                fMeasureText = fMeasureText;
            } else {
                editorRenderer = this;
                fMeasureText += editorRenderer.measureText(editorRenderer.getLine(i), i, iMax, iMin - iMax);
            }
            editorRenderer2 = editorRenderer;
        }
    }

    protected void drawFunctionCharacter(Canvas canvas, float f, float f2, float f3, char c) {
        this.paintGraph.setTextAlign(Paint.Align.CENTER);
        float rowBaseline = (f2 - this.editor.getRowBaseline(0)) + (this.editor.getRowHeight() / 2.0f);
        float f4 = (this.metricsGraph.descent - this.metricsGraph.ascent) / 2.0f;
        float f5 = rowBaseline - f4;
        float f6 = f5 - this.metricsGraph.ascent;
        this.paintGraph.setColor(this.paintGeneral.getColor());
        float f7 = f + (f3 / 2.0f);
        canvas.drawText(FunctionCharacters.getNameForFunctionCharacter(c), f7, f6, this.paintGraph);
        this.paintGraph.setTextAlign(Paint.Align.LEFT);
        float fMeasureText = this.paintGraph.measureText(FunctionCharacters.getNameForFunctionCharacter(c));
        this.tmpRect.top = f5;
        this.tmpRect.bottom = rowBaseline + f4;
        float f8 = fMeasureText / 2.0f;
        this.tmpRect.left = f7 - f8;
        this.tmpRect.right = f7 + f8;
        int color = this.paintGeneral.getColor();
        this.paintGeneral.setColor(this.editor.getColorScheme().getColor(52));
        this.paintGeneral.setStyle(Paint.Style.STROKE);
        this.paintGeneral.setStrokeWidth(this.editor.getRowHeightOfText() * 0.05f);
        drawRowBackgroundRect(canvas, this.tmpRect);
        this.paintGeneral.setStyle(Paint.Style.FILL);
        this.paintGeneral.setColor(color);
    }

    protected void drawText(Canvas canvas, ContentLine contentLine, int i, int i2, int i3, int i4, boolean z, float f, float f2, int i5) {
        int i6;
        char[] cArr;
        int i7;
        int i8;
        int iMin = Math.min(contentLine.value.length, i + i2);
        char[] cArr2 = contentLine.value;
        boolean zIsRenderFunctionCharacters = this.editor.isRenderFunctionCharacters();
        int i9 = i;
        int i10 = i9;
        float fMeasureText = f;
        while (i10 < iMin) {
            char c = cArr2[i10];
            if (c == '\t') {
                int i11 = i10 - i9;
                i6 = iMin;
                int i12 = i10;
                drawTextRunDirect(canvas, cArr2, i9, i11, i3, i4, fMeasureText, f2, z);
                fMeasureText += measureText(contentLine, i5, i9, i11 + 1);
                i8 = i12 + 1;
                cArr = cArr2;
                i7 = i12;
            } else {
                i6 = iMin;
                int i13 = i10;
                int i14 = i9;
                if (zIsRenderFunctionCharacters && FunctionCharacters.isEditorFunctionChar(c)) {
                    int i15 = i13 - i14;
                    i7 = i13;
                    cArr = cArr2;
                    drawTextRunDirect(canvas, cArr2, i14, i15, i3, i4, fMeasureText, f2, z);
                    float fMeasureText2 = fMeasureText + measureText(contentLine, i5, i14, i15);
                    float fMeasureText3 = measureText(contentLine, i5, i7, 1);
                    drawFunctionCharacter(canvas, fMeasureText2, f2, fMeasureText3, cArr[i7]);
                    fMeasureText = fMeasureText2 + fMeasureText3;
                    i8 = i7 + 1;
                } else {
                    cArr = cArr2;
                    zIsRenderFunctionCharacters = zIsRenderFunctionCharacters;
                    i7 = i13;
                    i9 = i14;
                }
                i10 = i7 + 1;
                iMin = i6;
                zIsRenderFunctionCharacters = zIsRenderFunctionCharacters;
                cArr2 = cArr;
            }
            i9 = i8;
            i10 = i7 + 1;
            iMin = i6;
            zIsRenderFunctionCharacters = zIsRenderFunctionCharacters;
            cArr2 = cArr;
        }
        int i16 = iMin;
        char[] cArr3 = cArr2;
        int i17 = i9;
        if (i17 < i16) {
            drawTextRunDirect(canvas, cArr3, i17, i16 - i17, i3, i4, fMeasureText, f2, z);
        }
    }

    protected void drawTextRunDirect(Canvas canvas, char[] cArr, int i, int i2, int i3, int i4, float f, float f2, boolean z) {
        if (!this.basicDisplayMode) {
            canvas.drawTextRun(cArr, i, i2, i3, i4, f, f2, z, this.paintGeneral);
            return;
        }
        float fMyGetTextRunAdvances = f;
        int i5 = 0;
        while (i5 < i2) {
            int i6 = i + i5;
            int i7 = (Character.isHighSurrogate(cArr[i6]) && i5 + 1 < i2 && Character.isLowSurrogate(cArr[i6 + 1])) ? 2 : 1;
            canvas.drawText(cArr, i6, i7, fMyGetTextRunAdvances, f2, this.paintGeneral);
            fMyGetTextRunAdvances += this.paintGeneral.myGetTextRunAdvances(cArr, i6, i7, i6, i7, false, null, 0, true);
            i5 += i7;
        }
    }

    private boolean isInside(int i, int i2, int i3, int i4) {
        return (i != i3 || this.content.getLine(i4).length() == i3) && i >= i2 && i <= i3;
    }

    public long getTimestamp() {
        return this.displayTimestamp;
    }

    public Paint.FontMetricsInt getLineNumberMetrics() {
        return this.metricsLineNumber;
    }

    /* JADX WARN: Code duplicated, block: B:50:0x0112 A[PHI: r5
      0x0112: PHI (r5v3 boolean) = (r5v2 boolean), (r5v4 boolean), (r5v4 boolean) binds: [B:28:0x00a2, B:41:0x00e0, B:45:0x00f2] A[DONT_GENERATE, DONT_INLINE]] */
    protected void drawEdgeEffect(Canvas canvas) {
        boolean zDraw;
        EdgeEffect verticalEdgeEffect = this.editor.getVerticalEdgeEffect();
        EdgeEffect horizontalEdgeEffect = this.editor.getHorizontalEdgeEffect();
        if (verticalEdgeEffect.isFinished()) {
            zDraw = false;
        } else {
            boolean z = this.editor.getEventHandler().glowTopOrBottom;
            if (z) {
                canvas.save();
                canvas.translate(-this.editor.getMeasuredWidth(), this.editor.getMeasuredHeight());
                canvas.rotate(180.0f, this.editor.getMeasuredWidth(), 0.0f);
            }
            zDraw = verticalEdgeEffect.draw(canvas);
            if (z) {
                canvas.restore();
            }
        }
        if (this.editor.isWordwrap()) {
            horizontalEdgeEffect.finish();
        }
        boolean z2 = true;
        if (!horizontalEdgeEffect.isFinished()) {
            canvas.save();
            if (this.editor.getEventHandler().glowLeftOrRight) {
                canvas.rotate(90.0f);
                canvas.translate(0.0f, -this.editor.getMeasuredWidth());
            } else {
                canvas.translate(0.0f, this.editor.getMeasuredHeight());
                canvas.rotate(-90.0f);
            }
            zDraw = horizontalEdgeEffect.draw(canvas) || zDraw;
            canvas.restore();
        }
        EditorScroller scroller = this.editor.getScroller();
        if (scroller.isOverScrolled()) {
            if (verticalEdgeEffect.isFinished() && (scroller.getCurrY() < 0 || scroller.getCurrY() > this.editor.getScrollMaxY())) {
                this.editor.getEventHandler().glowTopOrBottom = scroller.getCurrY() >= this.editor.getScrollMaxY();
                verticalEdgeEffect.onAbsorb((int) scroller.getCurrVelocity());
                zDraw = true;
            }
            if (!horizontalEdgeEffect.isFinished() || (scroller.getCurrX() >= 0 && scroller.getCurrX() <= this.editor.getScrollMaxX())) {
                z2 = zDraw;
            } else {
                this.editor.getEventHandler().glowLeftOrRight = scroller.getCurrX() >= this.editor.getScrollMaxX();
                horizontalEdgeEffect.onAbsorb((int) scroller.getCurrVelocity());
            }
        } else {
            z2 = zDraw;
        }
        if (z2) {
            this.editor.postInvalidate();
        }
    }

    protected void drawBlockLines(Canvas canvas, float f) {
        Styles styles = this.editor.getStyles();
        List<CodeBlock> list = styles == null ? null : styles.blocks;
        boolean z = styles != null && styles.isIndentCountMode();
        if (list == null || list.isEmpty()) {
            return;
        }
        int firstVisibleRow = this.editor.getFirstVisibleRow();
        int lastVisibleRow = this.editor.getLastVisibleRow();
        int suppressSwitch = styles.getSuppressSwitch();
        int iBinarySearchEndBlock = this.editor.binarySearchEndBlock(firstVisibleRow, list);
        if (iBinarySearchEndBlock == -1) {
            iBinarySearchEndBlock = 0;
        }
        int currentCursorBlock = this.editor.getCurrentCursorBlock();
        boolean z2 = false;
        int i = 0;
        while (iBinarySearchEndBlock < list.size()) {
            CodeBlock codeBlock = list.get(iBinarySearchEndBlock);
            if (codeBlock != null) {
                if (CodeEditor.hasVisibleRegion(codeBlock.startLine, codeBlock.endLine, firstVisibleRow, lastVisibleRow)) {
                    try {
                        ContentLine line = getLine(codeBlock.endLine);
                        float spaceWidth = z ? this.paintGeneral.getSpaceWidth() * codeBlock.endColumn : measureText(line, codeBlock.endLine, 0, Math.min(codeBlock.endColumn, line.length()));
                        ContentLine line2 = getLine(codeBlock.startLine);
                        float fMin = Math.min(spaceWidth, z ? this.paintGeneral.getSpaceWidth() * codeBlock.startColumn : measureText(line2, codeBlock.startLine, 0, Math.min(codeBlock.startColumn, line2.length()))) + f;
                        this.tmpRect.top = Math.max(0, this.editor.getRowBottom(codeBlock.startLine) - this.editor.getOffsetY());
                        this.tmpRect.bottom = Math.min(this.editor.getHeight(), (codeBlock.toBottomOfEndLine ? this.editor.getRowBottom(codeBlock.endLine) : this.editor.getRowTop(codeBlock.endLine)) - this.editor.getOffsetY());
                        this.tmpRect.left = fMin - ((this.editor.getDpUnit() * this.editor.getBlockLineWidth()) / 2.0f);
                        this.tmpRect.right = fMin + ((this.editor.getDpUnit() * this.editor.getBlockLineWidth()) / 2.0f);
                        try {
                            drawColor(canvas, this.editor.getColorScheme().getColor(iBinarySearchEndBlock == currentCursorBlock ? 15 : 14), this.tmpRect);
                        } catch (IndexOutOfBoundsException unused) {
                        }
                    } catch (IndexOutOfBoundsException unused2) {
                    }
                    z2 = true;
                } else if (!z2) {
                    continue;
                } else if (i >= suppressSwitch) {
                    return;
                } else {
                    i++;
                }
            }
            iBinarySearchEndBlock++;
        }
    }

    protected void drawSideBlockLine(Canvas canvas) {
        int currentCursorBlock;
        if (this.editor.getProps().drawSideBlockLine) {
            List<CodeBlock> list = this.editor.getStyles() == null ? null : this.editor.getStyles().blocks;
            if (list == null || list.isEmpty() || (currentCursorBlock = this.editor.getCurrentCursorBlock()) < 0 || currentCursorBlock >= list.size()) {
                return;
            }
            CodeBlock codeBlock = list.get(currentCursorBlock);
            Layout layout = this.editor.getLayout();
            try {
                float rowHeight = (layout.getCharLayoutOffset(codeBlock.startLine, codeBlock.startColumn)[0] - this.editor.getRowHeight()) - this.editor.getOffsetY();
                float offsetY = layout.getCharLayoutOffset(codeBlock.endLine, codeBlock.endColumn)[0] - this.editor.getOffsetY();
                float fMeasureLineNumber = this.editor.measureLineNumber();
                float dividerMarginLeft = ((fMeasureLineNumber + (this.editor.getDividerMarginLeft() + fMeasureLineNumber)) / 2.0f) - this.editor.getOffsetX();
                this.paintGeneral.setColor(this.editor.getColorScheme().getColor(38));
                this.paintGeneral.setStrokeWidth(this.editor.getDpUnit() * this.editor.getBlockLineWidth());
                canvas.drawLine(dividerMarginLeft, rowHeight, dividerMarginLeft, offsetY, this.paintGeneral);
            } catch (IndexOutOfBoundsException unused) {
            }
        }
    }

    protected void drawScrollBars(Canvas canvas) {
        this.verticalScrollBarRect.setEmpty();
        this.horizontalScrollBarRect.setEmpty();
        if (this.editor.getEventHandler().shouldDrawScrollBar()) {
            float dpUnit = this.editor.getDpUnit() * 10.0f;
            if (this.editor.isHorizontalScrollBarEnabled() && !this.editor.isWordwrap() && this.editor.getScrollMaxX() > (this.editor.getWidth() * 3) / 4) {
                canvas.save();
                canvas.translate(0.0f, this.editor.getEventHandler().getScrollBarMovementPercentage() * dpUnit);
                drawScrollBarTrackHorizontal(canvas);
                drawScrollBarHorizontal(canvas);
                canvas.restore();
            }
            if (!this.editor.isVerticalScrollBarEnabled() || this.editor.getScrollMaxY() <= this.editor.getHeight() / 2) {
                return;
            }
            canvas.save();
            canvas.translate(dpUnit * this.editor.getEventHandler().getScrollBarMovementPercentage(), 0.0f);
            drawScrollBarTrackVertical(canvas);
            drawScrollBarVertical(canvas);
            canvas.restore();
        }
    }

    protected void drawScrollBarTrackVertical(Canvas canvas) {
        if (this.editor.getEventHandler().holdVerticalScrollBar()) {
            this.tmpRect.right = this.editor.getWidth();
            this.tmpRect.left = this.editor.getWidth() - (this.editor.getDpUnit() * 10.0f);
            this.tmpRect.top = 0.0f;
            this.tmpRect.bottom = this.editor.getHeight();
            Drawable drawable = this.verticalScrollbarTrackDrawable;
            if (drawable != null) {
                drawable.setBounds((int) this.tmpRect.left, (int) this.tmpRect.top, (int) this.tmpRect.right, (int) this.tmpRect.bottom);
                this.verticalScrollbarTrackDrawable.draw(canvas);
            } else {
                drawColor(canvas, this.editor.getColorScheme().getColor(13), this.tmpRect);
            }
        }
    }

    protected void drawScrollBarVertical(Canvas canvas) {
        int height = this.editor.getHeight();
        float scrollMaxY = this.editor.getScrollMaxY() + height;
        float f = height;
        float fMax = Math.max((f / scrollMaxY) * f, this.editor.getDpUnit() * 60.0f);
        float offsetY = ((this.editor.getOffsetY() * 1.0f) / this.editor.getScrollMaxY()) * (f - fMax);
        if (this.editor.getEventHandler().holdVerticalScrollBar()) {
            drawLineInfoPanel(canvas, offsetY, fMax);
        }
        this.tmpRect.right = this.editor.getWidth();
        this.tmpRect.left = this.editor.getWidth() - (this.editor.getDpUnit() * 10.0f);
        this.tmpRect.top = offsetY;
        this.tmpRect.bottom = offsetY + fMax;
        this.verticalScrollBarRect.set(this.tmpRect);
        Drawable drawable = this.verticalScrollbarThumbDrawable;
        if (drawable == null) {
            drawColor(canvas, this.editor.getColorScheme().getColor(this.editor.getEventHandler().holdVerticalScrollBar() ? 12 : 11), this.tmpRect);
            return;
        }
        drawable.setState(this.editor.getEventHandler().holdVerticalScrollBar() ? PRESSED_DRAWABLE_STATE : DEFAULT_DRAWABLE_STATE);
        this.verticalScrollbarThumbDrawable.setBounds((int) this.tmpRect.left, (int) this.tmpRect.top, (int) this.tmpRect.right, (int) this.tmpRect.bottom);
        this.verticalScrollbarThumbDrawable.draw(canvas);
    }

    protected void drawLineInfoPanel(Canvas canvas, float f, float f2) {
        float rowHeight;
        float[] fArr;
        float f3;
        if (this.editor.isDisplayLnPanel()) {
            int lnPanelPositionMode = this.editor.getLnPanelPositionMode();
            int lnPanelPosition = this.editor.getLnPanelPosition();
            String currentText = this.editor.getLineNumberTipTextProvider().getCurrentText(this.editor);
            float textSize = this.paintGeneral.getTextSize();
            this.paintGeneral.setTextSize(this.editor.getLineInfoTextSize());
            Paint.FontMetricsInt fontMetricsInt = this.metricsText;
            this.metricsText = this.paintGeneral.getFontMetricsInt();
            float dpUnit = this.editor.getDpUnit() * 8.0f;
            float fMeasureText = this.paintGeneral.measureText(currentText);
            int i = 0;
            if (lnPanelPositionMode == 0) {
                this.tmpRect.top = ((this.editor.getHeight() / 2.0f) - (this.editor.getRowHeight() / 2.0f)) - dpUnit;
                this.tmpRect.bottom = (this.editor.getHeight() / 2.0f) + (this.editor.getRowHeight() / 2.0f) + dpUnit;
                float f4 = fMeasureText / 2.0f;
                this.tmpRect.left = ((this.editor.getWidth() / 2.0f) - f4) - dpUnit;
                this.tmpRect.right = (this.editor.getWidth() / 2.0f) + f4 + dpUnit;
                float f5 = dpUnit * 2.0f;
                rowHeight = (this.editor.getHeight() / 2.0f) + f5;
                float dpUnit2 = this.editor.getDpUnit() * 10.0f;
                if (lnPanelPosition != 15) {
                    if ((lnPanelPosition | 2) == lnPanelPosition) {
                        this.tmpRect.top = dpUnit2;
                        this.tmpRect.bottom = this.editor.getRowHeight() + dpUnit2 + f5;
                        rowHeight = this.editor.getRowBaseline(0) + dpUnit2 + dpUnit;
                    }
                    if ((lnPanelPosition | 8) == lnPanelPosition) {
                        this.tmpRect.top = ((this.editor.getHeight() - dpUnit2) - f5) - this.editor.getRowHeight();
                        this.tmpRect.bottom = this.editor.getHeight() - dpUnit2;
                        rowHeight = (((this.editor.getHeight() - this.editor.getRowHeight()) + this.editor.getRowBaseline(0)) - dpUnit2) - dpUnit;
                    }
                    if ((lnPanelPosition | 1) == lnPanelPosition) {
                        this.tmpRect.left = dpUnit2;
                        this.tmpRect.right = dpUnit2 + f5 + fMeasureText;
                    }
                    if ((lnPanelPosition | 4) == lnPanelPosition) {
                        this.tmpRect.right = this.editor.getWidth() - dpUnit2;
                        this.tmpRect.left = ((this.editor.getWidth() - dpUnit2) - f5) - fMeasureText;
                    }
                }
                drawColorRound(canvas, this.editor.getColorScheme().getColor(16), this.tmpRect);
                f3 = 0.0f;
            } else {
                this.tmpRect.right = this.editor.getWidth() - (this.editor.getDpUnit() * 30.0f);
                float f6 = dpUnit * 2.0f;
                this.tmpRect.left = ((this.editor.getWidth() - (this.editor.getDpUnit() * 30.0f)) - f6) - fMeasureText;
                if (lnPanelPosition == 2) {
                    this.tmpRect.top = f;
                    this.tmpRect.bottom = this.editor.getRowHeight() + f + f6;
                    rowHeight = f + this.editor.getRowBaseline(0) + dpUnit;
                    fArr = new float[8];
                    while (i < 8) {
                        if (i != 5) {
                            fArr[i] = this.tmpRect.height() * 0.5f;
                        }
                        i++;
                    }
                } else if (lnPanelPosition == 8) {
                    float f7 = f + f2;
                    this.tmpRect.top = (f7 - this.editor.getRowHeight()) - f6;
                    this.tmpRect.bottom = f7;
                    rowHeight = f7 - (this.editor.getRowBaseline(0) / 2.0f);
                    fArr = new float[8];
                    while (i < 8) {
                        if (i != 3) {
                            fArr[i] = this.tmpRect.height() * 0.5f;
                        }
                        i++;
                    }
                } else {
                    float f8 = f + (f2 / 2.0f);
                    this.tmpRect.top = (f8 - (this.editor.getRowHeight() / 2.0f)) - dpUnit;
                    this.tmpRect.bottom = (this.editor.getRowHeight() / 2.0f) + f8 + dpUnit;
                    rowHeight = (f8 - (this.editor.getRowHeight() / 2.0f)) + this.editor.getRowBaseline(0);
                    fArr = null;
                }
                if (fArr != null) {
                    this.tmpPath.reset();
                    this.tmpPath.addRoundRect(this.tmpRect, fArr, Path.Direction.CW);
                    f3 = 0.0f;
                } else {
                    float f9 = -dpUnit;
                    this.tmpRect.offset(f9, 0.0f);
                    this.tmpRect.right += dpUnit;
                    f3 = f9 / 2.0f;
                    BubbleHelper.buildBubblePath(this.tmpPath, this.tmpRect);
                }
                this.paintGeneral.setColor(this.editor.getColorScheme().getColor(16));
                canvas.drawPath(this.tmpPath, this.paintGeneral);
            }
            float f10 = ((this.tmpRect.left + this.tmpRect.right) / 2.0f) + f3;
            this.paintGeneral.setColor(this.editor.getColorScheme().getColor(17));
            this.paintGeneral.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(currentText, f10, rowHeight, this.paintGeneral);
            this.paintGeneral.setTextAlign(Paint.Align.LEFT);
            this.paintGeneral.setTextSize(textSize);
            this.metricsText = fontMetricsInt;
        }
    }

    protected void drawScrollBarTrackHorizontal(Canvas canvas) {
        if (this.editor.getEventHandler().holdHorizontalScrollBar()) {
            this.tmpRect.set(0.0f, this.editor.getHeight() - (this.editor.getDpUnit() * 10.0f), this.editor.getWidth(), this.editor.getHeight());
            Drawable drawable = this.horizontalScrollbarTrackDrawable;
            if (drawable != null) {
                drawable.setBounds((int) this.tmpRect.left, (int) this.tmpRect.top, (int) this.tmpRect.right, (int) this.tmpRect.bottom);
                this.horizontalScrollbarTrackDrawable.draw(canvas);
            } else {
                drawColor(canvas, this.editor.getColorScheme().getColor(13), this.tmpRect);
            }
        }
    }

    protected void patchSnippetRegions(Canvas canvas, float f) {
        SnippetController snippetController = this.editor.getSnippetController();
        if (snippetController.isInSnippet()) {
            PlaceholderItem editingTabStop = snippetController.getEditingTabStop();
            if (editingTabStop != null) {
                patchTextRegionWithColor(canvas, f, editingTabStop.getStartIndex(), editingTabStop.getEndIndex(), 0, this.editor.getColorScheme().getColor(48), 0);
            }
            for (SnippetItem snippetItem : snippetController.getEditingRelatedTabStops()) {
                patchTextRegionWithColor(canvas, f, snippetItem.getStartIndex(), snippetItem.getEndIndex(), 0, this.editor.getColorScheme().getColor(47), 0);
            }
            for (SnippetItem snippetItem2 : snippetController.getInactiveTabStops()) {
                patchTextRegionWithColor(canvas, f, snippetItem2.getStartIndex(), snippetItem2.getEndIndex(), 0, this.editor.getColorScheme().getColor(46), 0);
            }
        }
    }

    protected void patchHighlightedDelimiters(Canvas canvas, float f) {
        PairedBracket foundBracketPair;
        if (this.editor.inputConnection.composingText.isComposing() || !this.editor.getProps().highlightMatchingDelimiters || this.editor.getCursor().isSelected() || (foundBracketPair = this.editor.styleDelegate.getFoundBracketPair()) == null) {
            return;
        }
        int color = this.editor.getColorScheme().getColor(39);
        int color2 = this.editor.getColorScheme().getColor(41);
        int color3 = this.editor.getColorScheme().getColor(40);
        if (isInvalidTextBounds(foundBracketPair.leftIndex, foundBracketPair.leftLength) || isInvalidTextBounds(foundBracketPair.rightIndex, foundBracketPair.rightLength)) {
            return;
        }
        patchTextRegionWithColor(canvas, f, foundBracketPair.leftIndex, foundBracketPair.leftIndex + foundBracketPair.leftLength, color, color2, color3);
        patchTextRegionWithColor(canvas, f, foundBracketPair.rightIndex, foundBracketPair.rightIndex + foundBracketPair.rightLength, color, color2, color3);
    }

    protected boolean isInvalidTextBounds(int i, int i2) {
        return i < 0 || i2 < 0 || i + i2 > this.content.length();
    }

    protected void patchTextRegionWithColor(final Canvas canvas, float f, int i, int i2, final int i3, final int i4, final int i5) {
        this.paintGeneral.setColor(i3);
        this.paintOther.setStrokeWidth(this.editor.getRowHeightOfText() * 0.1f);
        this.paintGeneral.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paintGeneral.setFakeBoldText(this.editor.getProps().boldMatchingDelimiters);
        patchTextRegions(canvas, f, getTextRegionPositions(i, i2), new PatchDraw() { // from class: io.github.rosemoe.sora.widget.EditorRenderer$$ExternalSyntheticLambda1
            @Override // io.github.rosemoe.sora.widget.EditorRenderer.PatchDraw
            public final void draw(Canvas canvas2, float f2, int i6, int i7, int i8, int i9, long j) {
                this.f$0.lambda$patchTextRegionWithColor$1(i4, canvas, i3, i5, canvas2, f2, i6, i7, i8, i9, j);
            }
        });
        this.paintGeneral.setStyle(Paint.Style.FILL);
        this.paintGeneral.setFakeBoldText(false);
        this.paintGeneral.setTextSkewX(0.0f);
        this.paintGeneral.setStrikeThruText(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$patchTextRegionWithColor$1(int i, Canvas canvas, int i2, int i3, Canvas canvas2, float f, int i4, int i5, int i6, int i7, long j) {
        if (i != 0) {
            this.tmpRect.top = getRowTopForBackground(i4) - this.editor.getOffsetY();
            this.tmpRect.bottom = getRowBottomForBackground(i4) - this.editor.getOffsetY();
            this.tmpRect.left = 0.0f;
            this.tmpRect.right = this.editor.getWidth();
            this.paintOther.setColor(i);
            if (this.editor.getProps().enableRoundTextBackground) {
                canvas.drawRoundRect(this.tmpRect, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.editor.getRowHeight() * this.editor.getProps().roundTextBackgroundFactor, this.paintOther);
            } else {
                canvas.drawRect(this.tmpRect, this.paintOther);
            }
        }
        if (i2 != 0) {
            this.paintGeneral.setTextSkewX(TextStyle.isItalics(j) ? -0.2f : 0.0f);
            this.paintGeneral.setStrikeThruText(TextStyle.isStrikeThrough(j));
            int i8 = i7 - i6;
            drawText(canvas, getLine(i5), i6, i8, i6, i8, false, f, this.editor.getRowBaseline(i4) - this.editor.getOffsetY(), i5);
        }
        if (i3 != 0) {
            this.paintOther.setColor(i3);
            float rowBottomOfText = (this.editor.getRowBottomOfText(i4) - this.editor.getOffsetY()) - (this.editor.getRowHeightOfText() * 0.05f);
            canvas.drawLine(0.0f, rowBottomOfText, this.editor.getWidth(), rowBottomOfText, this.paintOther);
        }
    }

    protected List<TextDisplayPosition> getTextRegionPositions(int i, int i2) {
        Layout layout = this.editor.getLayout();
        int rowIndexForPosition = layout.getRowIndexForPosition(i);
        int rowIndexForPosition2 = layout.getRowIndexForPosition(i2);
        CharPosition charPosition = this.cursor.getIndexer().getCharPosition(i);
        CharPosition charPosition2 = this.cursor.getIndexer().getCharPosition(i2);
        RowIterator rowIteratorObtainRowIterator = layout.obtainRowIterator(rowIndexForPosition, this.preloadedLines);
        ArrayList arrayList = new ArrayList();
        int i3 = rowIndexForPosition;
        while (i3 <= rowIndexForPosition2 && rowIteratorObtainRowIterator.hasNext()) {
            Row next = rowIteratorObtainRowIterator.next();
            int i4 = i3 == rowIndexForPosition ? charPosition.column : next.startColumn;
            int i5 = i3 == rowIndexForPosition2 ? charPosition2.column : next.endColumn;
            TextDisplayPosition textDisplayPosition = new TextDisplayPosition();
            arrayList.add(textDisplayPosition);
            textDisplayPosition.row = i3;
            ContentLine line = this.content.getLine(next.lineIndex);
            textDisplayPosition.left = measureText(line, next.lineIndex, next.startColumn, i4 - next.startColumn);
            textDisplayPosition.right = textDisplayPosition.left + measureText(line, next.lineIndex, i4, i5 - i4);
            textDisplayPosition.startColumn = i4;
            textDisplayPosition.endColumn = i5;
            textDisplayPosition.line = next.lineIndex;
            textDisplayPosition.rowStart = next.startColumn;
            i3++;
        }
        return arrayList;
    }

    protected void patchTextRegions(Canvas canvas, float f, List<TextDisplayPosition> list, PatchDraw patchDraw) {
        float fMeasureText;
        boolean z;
        Canvas canvas2 = canvas;
        Styles styles = this.editor.getStyles();
        Spans spans = styles != null ? styles.getSpans() : null;
        Spans.Reader emptyReader = spans != null ? spans.read() : EmptyReader.getInstance();
        int firstVisibleRow = this.editor.getFirstVisibleRow();
        int lastVisibleRow = this.editor.getLastVisibleRow();
        for (TextDisplayPosition textDisplayPosition : list) {
            if (firstVisibleRow > textDisplayPosition.row) {
                canvas2 = canvas;
            } else if (textDisplayPosition.row > lastVisibleRow) {
                continue;
            } else {
                int i = textDisplayPosition.line;
                try {
                    emptyReader.moveToLine(i);
                    int i2 = textDisplayPosition.startColumn;
                    int i3 = textDisplayPosition.endColumn;
                    ContentLine line = getLine(i);
                    int length = line.length();
                    canvas.save();
                    int spanCount = emptyReader.getSpanCount();
                    boolean z2 = true;
                    float fMeasureText2 = f;
                    int i4 = 0;
                    Span spanAt = null;
                    while (true) {
                        if (i4 >= spanCount) {
                            firstVisibleRow = firstVisibleRow;
                            lastVisibleRow = lastVisibleRow;
                            break;
                        }
                        if (spanAt == null) {
                            spanAt = emptyReader.getSpanAt(i4);
                        }
                        int i5 = i4 + 1;
                        Span spanAt2 = i5 == spanCount ? null : emptyReader.getSpanAt(i5);
                        int i6 = i5;
                        int iMax = Math.max(spanAt.getColumn(), textDisplayPosition.rowStart);
                        int iMax2 = Math.max(i2, iMax);
                        int iMin = Math.min(length, spanAt2 == null ? length : spanAt2.getColumn());
                        int i7 = length;
                        if (iMin <= textDisplayPosition.startColumn) {
                            i6 = i6;
                            line = line;
                            i2 = i2;
                            textDisplayPosition = textDisplayPosition;
                            firstVisibleRow = firstVisibleRow;
                            lastVisibleRow = lastVisibleRow;
                            i3 = i3;
                            i = i;
                        } else {
                            if (Math.min(i3, iMin) - iMax2 > 0) {
                                if (z2) {
                                    fMeasureText = fMeasureText2 + measureText(line, i, textDisplayPosition.rowStart, iMax - textDisplayPosition.rowStart);
                                    z = false;
                                } else {
                                    fMeasureText = fMeasureText2;
                                    z = z2;
                                }
                                if (TextStyle.isItalics(spanAt.getStyleBits())) {
                                    Path path = new Path();
                                    float rowBottomOfText = this.editor.getRowBottomOfText(textDisplayPosition.row) - this.editor.getOffsetY();
                                    path.moveTo(f + textDisplayPosition.left, rowBottomOfText);
                                    path.lineTo((f + textDisplayPosition.left) - (rowBottomOfText * (-0.2f)), 0.0f);
                                    path.lineTo(this.editor.getWidth(), 0.0f);
                                    path.lineTo(this.editor.getWidth(), this.editor.getHeight());
                                    path.close();
                                    canvas2.clipPath(path);
                                } else {
                                    canvas2.clipRect(f + textDisplayPosition.left, 0.0f, this.editor.getWidth(), this.editor.getHeight());
                                }
                                if (TextStyle.isItalics(spanAt.getStyleBits())) {
                                    Path path2 = new Path();
                                    float rowBottomOfText2 = this.editor.getRowBottomOfText(textDisplayPosition.row) - this.editor.getOffsetY();
                                    path2.moveTo(f + textDisplayPosition.right, rowBottomOfText2);
                                    path2.lineTo((f + textDisplayPosition.right) - (rowBottomOfText2 * (-0.2f)), 0.0f);
                                    path2.lineTo(0.0f, 0.0f);
                                    path2.lineTo(0.0f, this.editor.getHeight());
                                    path2.close();
                                    canvas2.clipPath(path2);
                                } else {
                                    canvas2.clipRect(0.0f, 0.0f, f + textDisplayPosition.right, this.editor.getHeight());
                                }
                                patchDraw.draw(canvas, fMeasureText, textDisplayPosition.row, i, iMax, iMin, spanAt.getStyle());
                                z2 = z;
                                fMeasureText2 = fMeasureText;
                            }
                            if (iMin >= i3) {
                                break;
                            } else {
                                fMeasureText2 += measureText(line, i, iMax, iMin - iMax);
                            }
                        }
                        line = line;
                        i3 = i3;
                        i = i;
                        i4 = i6;
                        spanAt = spanAt2;
                        length = i7;
                        spanCount = spanCount;
                        firstVisibleRow = firstVisibleRow;
                        i2 = i2;
                        textDisplayPosition = textDisplayPosition;
                        lastVisibleRow = lastVisibleRow;
                        canvas2 = canvas;
                    }
                    canvas.restore();
                    canvas2 = canvas;
                    firstVisibleRow = firstVisibleRow;
                    lastVisibleRow = lastVisibleRow;
                } catch (Exception e) {
                    Log.e(LOG_TAG, "patchTextRegions: Unable to get spans", e);
                }
            }
        }
        try {
            emptyReader.moveToLine(-1);
        } catch (Exception e2) {
            Log.w(LOG_TAG, "Failed to release SpanReader", e2);
        }
    }

    protected void drawSelectionOnAnimation(Canvas canvas) {
        if (this.editor.isEditable()) {
            this.tmpRect.bottom = this.editor.getCursorAnimator().animatedY() - this.editor.getOffsetY();
            RectF rectF = this.tmpRect;
            rectF.top = rectF.bottom - (this.editor.getProps().textBackgroundWrapTextOnly ? this.editor.getRowHeightOfText() : this.editor.getRowHeight());
            float fAnimatedX = this.editor.getCursorAnimator().animatedX() - this.editor.getOffsetX();
            this.tmpRect.left = fAnimatedX - (this.editor.getInsertSelectionWidth() / 2.0f);
            this.tmpRect.right = (this.editor.getInsertSelectionWidth() / 2.0f) + fAnimatedX;
            drawColor(canvas, this.editor.getColorScheme().getColor(7), this.tmpRect);
            if (this.editor.getEventHandler().shouldDrawInsertHandle()) {
                this.editor.getHandleStyle().draw(canvas, 0, fAnimatedX, this.tmpRect.bottom, this.editor.getRowHeight(), this.editor.getColorScheme().getColor(8), this.editor.getInsertHandleDescriptor());
            }
        }
    }

    protected void drawScrollBarHorizontal(Canvas canvas) {
        int width = this.editor.getWidth();
        float scrollMaxX = this.editor.getScrollMaxX();
        float width2 = (width / (this.editor.getWidth() + scrollMaxX)) * this.editor.getWidth();
        float dpUnit = this.editor.getDpUnit() * 60.0f;
        if (width2 <= dpUnit) {
            width2 = dpUnit;
        }
        float offsetX = (this.editor.getOffsetX() / scrollMaxX) * (this.editor.getWidth() - width2);
        this.tmpRect.top = this.editor.getHeight() - (this.editor.getDpUnit() * 10.0f);
        this.tmpRect.bottom = this.editor.getHeight();
        this.tmpRect.right = width2 + offsetX;
        this.tmpRect.left = offsetX;
        this.horizontalScrollBarRect.set(this.tmpRect);
        Drawable drawable = this.horizontalScrollbarThumbDrawable;
        if (drawable == null) {
            drawColor(canvas, this.editor.getColorScheme().getColor(this.editor.getEventHandler().holdHorizontalScrollBar() ? 12 : 11), this.tmpRect);
            return;
        }
        drawable.setState(this.editor.getEventHandler().holdHorizontalScrollBar() ? PRESSED_DRAWABLE_STATE : DEFAULT_DRAWABLE_STATE);
        this.horizontalScrollbarThumbDrawable.setBounds((int) this.tmpRect.left, (int) this.tmpRect.top, (int) this.tmpRect.right, (int) this.tmpRect.bottom);
        this.horizontalScrollbarThumbDrawable.draw(canvas);
    }

    public long findDesiredVisibleChar(float f, int i, int i2, int i3) {
        return findDesiredVisibleChar(f, i, i2, i3, i2, false);
    }

    public long findDesiredVisibleChar(float f, int i, int i2, int i3, int i4, boolean z) {
        boolean zIsRunRtl;
        if (i2 >= i3) {
            return CharPosDesc.make(i3, 0.0f);
        }
        ContentLine line = getLine(i);
        if (line.widthCache != null && line.timestamp < this.displayTimestamp) {
            buildMeasureCacheForLines(i, i, this.displayTimestamp, false);
        }
        GraphicTextRow graphicTextRowObtain = GraphicTextRow.obtain(this.basicDisplayMode);
        Directions lineDirections = null;
        graphicTextRowObtain.set(line, getLineDirections(i), i4, i3, this.editor.getTabWidth(), line.widthCache == null ? this.editor.getSpansForLine(i) : null, this.paintGeneral);
        if ((this.editor.getLayout() instanceof WordwrapLayout) && line.widthCache == null) {
            graphicTextRowObtain.setSoftBreaks(((WordwrapLayout) this.editor.getLayout()).getSoftBreaksForLine(i));
        }
        int textOffset = CharPosDesc.getTextOffset(graphicTextRowObtain.findOffsetByAdvance(i2, f));
        int runCount = -1;
        boolean z2 = true;
        if (!line.mayNeedBidi()) {
            zIsRunRtl = false;
            break;
        }
        lineDirections = this.content.getLineDirections(i);
        if (textOffset == line.length()) {
            runCount = lineDirections.getRunCount() - 1;
        } else {
            int i5 = 0;
            while (true) {
                if (i5 < lineDirections.getRunCount()) {
                    if (textOffset >= lineDirections.getRunStart(i5) && textOffset < lineDirections.getRunEnd(i5)) {
                        zIsRunRtl = lineDirections.isRunRtl(i5);
                        runCount = i5;
                        break;
                    }
                    i5++;
                }
            }
        }
        zIsRunRtl = false;
        break;
        if (zIsRunRtl) {
            textOffset = z ? lineDirections.getRunEnd(runCount) : lineDirections.getRunStart(runCount);
        } else if (z) {
            int i6 = textOffset + 1;
            if (i6 < i3) {
                UnicodeIterator unicodeIteratorObtain = UnicodeIterator.obtain(line, i6, i3);
                while (true) {
                    int iNextCodePoint = unicodeIteratorObtain.nextCodePoint();
                    if (iNextCodePoint == 0 || !(GraphicCharacter.isCombiningCharacter(iNextCodePoint) || z2)) {
                        break;
                    }
                    textOffset = unicodeIteratorObtain.getEndIndex();
                    z2 = false;
                }
                unicodeIteratorObtain.recycle();
            }
        } else if (textOffset < i3) {
            char[] rawData = line.getRawData();
            while (textOffset > i2) {
                char c = rawData[textOffset];
                if (Character.isLowSurrogate(c)) {
                    int i7 = textOffset - 1;
                    if (i7 < i2) {
                        continue;
                    } else if (GraphicCharacter.isCombiningCharacter(Character.toCodePoint(rawData[i7], c))) {
                        textOffset -= 2;
                    } else {
                        if (!GraphicCharacter.isCombiningCharacter(c)) {
                            break;
                        }
                        textOffset--;
                    }
                } else {
                    if (!Character.isHighSurrogate(c) && !GraphicCharacter.isCombiningCharacter(c)) {
                        break;
                    }
                    textOffset--;
                }
            }
        }
        if (!zIsRunRtl && !z && textOffset > i2) {
            char[] rawData2 = line.getRawData();
            int i8 = textOffset - 1;
            if (Character.isLowSurrogate(rawData2[i8])) {
                if (i8 > i2 && !GraphicCharacter.couldBeEmojiPart(Character.toCodePoint(rawData2[textOffset - 2], rawData2[i8]))) {
                    textOffset -= 2;
                }
            } else if (!Character.isHighSurrogate(rawData2[i8])) {
                textOffset--;
            }
        }
        int iMin = Math.min(i3, Math.max(i2, textOffset));
        long jMake = CharPosDesc.make(iMin, graphicTextRowObtain.measureText(i2, iMin));
        graphicTextRowObtain.recycle();
        return jMake;
    }

    public long findFirstVisibleCharForWordwrap(float f, int i, int i2, int i3, int i4, io.github.rosemoe.sora.graphics.Paint paint) {
        if (i2 >= i3) {
            return CharPosDesc.make(i3, 0.0f);
        }
        GraphicTextRow graphicTextRowObtain = GraphicTextRow.obtain(this.basicDisplayMode);
        graphicTextRowObtain.set(this.content, i, i4, i3, this.editor.getTabWidth(), sSpansForWordwrap, paint);
        graphicTextRowObtain.disableCache();
        long jFindOffsetByAdvance = graphicTextRowObtain.findOffsetByAdvance(i2, f);
        graphicTextRowObtain.recycle();
        return jFindOffsetByAdvance;
    }

    protected void buildMeasureCacheForLines(int i, int i2, long j, boolean z) {
        boolean z2;
        Content content = this.content;
        for (int i3 = i; i3 <= i2 && i3 < content.getLineCount(); i3++) {
            ContentLine line = z != 0 ? getLine(i3) : getLineDirect(i3);
            if (line.timestamp < j) {
                GraphicTextRow graphicTextRowObtain = GraphicTextRow.obtain(this.basicDisplayMode);
                if (line.widthCache == null || line.widthCache.length < line.length()) {
                    line.widthCache = this.editor.obtainFloatArray(Math.max(line.length() + 8, 90), z);
                    z2 = true;
                } else {
                    z2 = false;
                }
                boolean z3 = z2;
                List<Span> spansForLine = this.editor.getSpansForLine(i3);
                graphicTextRowObtain.set(content, i3, 0, line.length(), this.editor.getTabWidth(), spansForLine, this.paintGeneral);
                List<Integer> softBreaksForLine = this.editor.layout instanceof WordwrapLayout ? ((WordwrapLayout) this.editor.layout).getSoftBreaksForLine(i3) : null;
                graphicTextRowObtain.setSoftBreaks(softBreaksForLine);
                int iHash = Objects.hash(spansForLine, Integer.valueOf(line.length()), Integer.valueOf(this.editor.getTabWidth()), Boolean.valueOf(this.basicDisplayMode), softBreaksForLine, Integer.valueOf(this.paintGeneral.getFlags()), Float.valueOf(this.paintGeneral.getTextSize()), Float.valueOf(this.paintGeneral.getTextScaleX()), Float.valueOf(this.paintGeneral.getLetterSpacing()), this.paintGeneral.getFontFeatureSettings());
                if (line.styleHash != iHash || z3) {
                    graphicTextRowObtain.buildMeasureCache();
                    line.styleHash = iHash;
                }
                graphicTextRowObtain.recycle();
                line.timestamp = j;
            }
        }
    }

    protected void buildMeasureCacheForLines(int i, int i2) {
        buildMeasureCacheForLines(i, i2, this.displayTimestamp, false);
    }

    public float measureText(ContentLine contentLine, int i, int i2, int i3) {
        float[] fArr = contentLine.widthCache;
        if ((contentLine.timestamp < this.displayTimestamp && fArr != null) || (fArr != null && fArr.length >= i2 + i3)) {
            buildMeasureCacheForLines(i, i);
        }
        GraphicTextRow graphicTextRowObtain = GraphicTextRow.obtain(this.basicDisplayMode);
        List<Span> spansForLine = this.editor.defaultSpans;
        if (contentLine.widthCache == null) {
            spansForLine = this.editor.getSpansForLine(i);
        }
        graphicTextRowObtain.set(contentLine, contentLine.mayNeedBidi() ? getLineDirections(i) : null, 0, contentLine.length(), this.editor.getTabWidth(), spansForLine, this.paintGeneral);
        if ((this.editor.layout instanceof WordwrapLayout) && contentLine.widthCache == null) {
            graphicTextRowObtain.setSoftBreaks(((WordwrapLayout) this.editor.layout).getSoftBreaksForLine(i));
        }
        float fMeasureText = graphicTextRowObtain.measureText(i2, i3 + i2);
        graphicTextRowObtain.recycle();
        return fMeasureText;
    }

    protected static class TextDisplayPosition {
        protected int endColumn;
        protected float left;
        protected int line;
        protected float right;
        protected int row;
        protected int rowStart;
        protected int startColumn;

        protected TextDisplayPosition() {
        }

        public String toString() {
            return "TextDisplayPosition{row=" + this.row + ", startColumn=" + this.startColumn + ", endColumn=" + this.endColumn + ", line=" + this.line + ", rowStart=" + this.rowStart + ", left=" + this.left + ", right=" + this.right + '}';
        }
    }

    protected class DrawCursorTask {
        private static final SelectionHandleStyle.HandleDescriptor TMP_DESC = new SelectionHandleStyle.HandleDescriptor();
        protected SelectionHandleStyle.HandleDescriptor descriptor;
        protected int handleType;
        protected float x;
        protected float y;

        public DrawCursorTask(float f, float f2, int i, SelectionHandleStyle.HandleDescriptor handleDescriptor) {
            this.x = f;
            this.y = f2;
            this.handleType = i;
            this.descriptor = handleDescriptor;
        }

        private boolean drawSelForLeftRight() {
            int i = this.handleType;
            return (i == 1 || i == 2) && EditorRenderer.this.editor.getProps().showSelectionWhenSelected && !EditorRenderer.this.editor.isInMouseMode();
        }

        private boolean drawSelForInsert() {
            int i = this.handleType;
            return (i == 1 || i == 2 || (!EditorRenderer.this.editor.getCursorBlink().visibility && !EditorRenderer.this.editor.getEventHandler().holdInsertHandle() && !EditorRenderer.this.editor.isInLongSelect())) ? false : true;
        }

        private boolean isSelForLongSelect() {
            int i;
            return (!EditorRenderer.this.editor.isInLongSelect() || (i = this.handleType) == 1 || i == 2) ? false : true;
        }

        protected void execute(Canvas canvas) {
            if (this.handleType == -1 || (!EditorRenderer.this.editor.inputConnection.imeConsumingInput && EditorRenderer.this.editor.isFocused())) {
                if (this.handleType != 0 || EditorRenderer.this.editor.isEditable()) {
                    SelectionHandleStyle.HandleDescriptor handleDescriptor = this.descriptor;
                    if (handleDescriptor == null) {
                        handleDescriptor = TMP_DESC;
                    }
                    if (!handleDescriptor.position.isEmpty()) {
                        boolean z = EditorRenderer.this.editor.getEventHandler().holdInsertHandle() && this.handleType == 0;
                        boolean z2 = EditorRenderer.this.editor.getEventHandler().selHandleType == 1 && this.handleType == 1;
                        boolean z3 = EditorRenderer.this.editor.getEventHandler().selHandleType == 2 && this.handleType == 2;
                        if (!EditorRenderer.this.editor.isStickyTextSelection() && (z || z2 || z3)) {
                            this.x = EditorRenderer.this.editor.getEventHandler().motionX + ((handleDescriptor.alignment != 0 ? handleDescriptor.position.width() : 0.0f) * (handleDescriptor.alignment == 1 ? 1 : -1));
                            this.y = EditorRenderer.this.editor.getEventHandler().motionY - ((handleDescriptor.position.height() * 2.0f) / 3.0f);
                        }
                    }
                    if (drawSelForLeftRight() || drawSelForInsert() || this.handleType == -1) {
                        float rowHeightOfText = this.y - (EditorRenderer.this.editor.getProps().textBackgroundWrapTextOnly ? EditorRenderer.this.editor.getRowHeightOfText() : EditorRenderer.this.editor.getRowHeight());
                        float f = this.y;
                        EditorRenderer.this.paintGeneral.setColor(EditorRenderer.this.editor.getColorScheme().getColor(7));
                        EditorRenderer.this.paintGeneral.setStrokeWidth(EditorRenderer.this.editor.getInsertSelectionWidth());
                        EditorRenderer.this.paintGeneral.setStyle(Paint.Style.STROKE);
                        if (isSelForLongSelect()) {
                            float f2 = f - rowHeightOfText;
                            float f3 = f2 / 8.0f;
                            EditorRenderer.this.paintGeneral.setPathEffect(new DashPathEffect(new float[]{f3, f3}, f2 / 16.0f));
                            EditorRenderer.this.paintGeneral.setStrokeWidth(EditorRenderer.this.editor.getInsertSelectionWidth() * 1.5f);
                        }
                        float f4 = this.x;
                        canvas.drawLine(f4, rowHeightOfText, f4, f, EditorRenderer.this.paintGeneral);
                        EditorRenderer.this.paintGeneral.setStyle(Paint.Style.FILL);
                        EditorRenderer.this.paintGeneral.setPathEffect(null);
                    }
                    int i = this.handleType;
                    int i2 = (i != 0 || (!EditorRenderer.this.editor.isInLongSelect() && EditorRenderer.this.editor.getEventHandler().shouldDrawInsertHandle())) ? i : -1;
                    if (i2 != -1 && !EditorRenderer.this.editor.isInMouseMode()) {
                        EditorRenderer.this.editor.getHandleStyle().draw(canvas, i2, this.x, this.y, EditorRenderer.this.editor.getRowHeight(), EditorRenderer.this.editor.getColorScheme().getColor(8), handleDescriptor);
                        if (handleDescriptor == TMP_DESC) {
                            handleDescriptor.setEmpty();
                            return;
                        }
                        return;
                    }
                    handleDescriptor.setEmpty();
                }
            }
        }
    }
}
