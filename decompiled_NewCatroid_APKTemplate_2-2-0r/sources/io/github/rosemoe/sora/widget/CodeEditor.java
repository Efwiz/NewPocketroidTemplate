package io.github.rosemoe.sora.widget;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.TransactionTooLargeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import io.github.rosemoe.sora.I18nConfig;
import io.github.rosemoe.sora.R;
import io.github.rosemoe.sora.event.BuildEditorInfoEvent;
import io.github.rosemoe.sora.event.ColorSchemeUpdateEvent;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.event.CreateContextMenuEvent;
import io.github.rosemoe.sora.event.EditorAttachStateChangeEvent;
import io.github.rosemoe.sora.event.EditorFocusChangeEvent;
import io.github.rosemoe.sora.event.EditorFormatEvent;
import io.github.rosemoe.sora.event.EditorReleaseEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventManager;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.HoverEvent;
import io.github.rosemoe.sora.event.ScrollEvent;
import io.github.rosemoe.sora.event.SelectionChangeEvent;
import io.github.rosemoe.sora.event.SubscriptionReceipt;
import io.github.rosemoe.sora.lang.EmptyLanguage;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.lang.QuickQuoteHandler;
import io.github.rosemoe.sora.lang.analysis.AnalyzeManager;
import io.github.rosemoe.sora.lang.analysis.StyleUpdateRange;
import io.github.rosemoe.sora.lang.diagnostic.DiagnosticsContainer;
import io.github.rosemoe.sora.lang.format.Formatter;
import io.github.rosemoe.sora.lang.styling.CodeBlock;
import io.github.rosemoe.sora.lang.styling.Span;
import io.github.rosemoe.sora.lang.styling.SpanFactory;
import io.github.rosemoe.sora.lang.styling.Spans;
import io.github.rosemoe.sora.lang.styling.Styles;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.ContentListener;
import io.github.rosemoe.sora.text.ContentReference;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.text.LineRemoveListener;
import io.github.rosemoe.sora.text.LineSeparator;
import io.github.rosemoe.sora.text.TextLayoutHelper;
import io.github.rosemoe.sora.text.TextRange;
import io.github.rosemoe.sora.text.TextUtils;
import io.github.rosemoe.sora.text.method.KeyMetaStates;
import io.github.rosemoe.sora.util.Chars;
import io.github.rosemoe.sora.util.ClipDataUtils;
import io.github.rosemoe.sora.util.EditorHandler;
import io.github.rosemoe.sora.util.Floats;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.KeyboardUtils;
import io.github.rosemoe.sora.util.Logger;
import io.github.rosemoe.sora.util.LongArrayList;
import io.github.rosemoe.sora.util.Numbers;
import io.github.rosemoe.sora.util.TemporaryFloatBuffer;
import io.github.rosemoe.sora.util.ThemeUtils;
import io.github.rosemoe.sora.util.ViewUtils;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import io.github.rosemoe.sora.widget.component.EditorBuiltinComponent;
import io.github.rosemoe.sora.widget.component.EditorContextMenuCreator;
import io.github.rosemoe.sora.widget.component.EditorDiagnosticTooltipWindow;
import io.github.rosemoe.sora.widget.component.EditorTextActionWindow;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.layout.Layout;
import io.github.rosemoe.sora.widget.layout.LineBreakLayout;
import io.github.rosemoe.sora.widget.layout.Row;
import io.github.rosemoe.sora.widget.layout.ViewMeasureHelper;
import io.github.rosemoe.sora.widget.layout.WordwrapLayout;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.snippet.SnippetController;
import io.github.rosemoe.sora.widget.style.CursorAnimator;
import io.github.rosemoe.sora.widget.style.DiagnosticIndicatorStyle;
import io.github.rosemoe.sora.widget.style.LineNumberTipTextProvider;
import io.github.rosemoe.sora.widget.style.SelectionHandleStyle;
import io.github.rosemoe.sora.widget.style.builtin.DefaultLineNumberTip;
import io.github.rosemoe.sora.widget.style.builtin.HandleStyleSideDrop;
import io.github.rosemoe.sora.widget.style.builtin.MoveCursorAnimator;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import kotlin.jvm.functions.Function5;
import kotlin.text.StringsKt;

/* JADX INFO: loaded from: classes11.dex */
public class CodeEditor extends View implements ContentListener, Formatter.FormatResultReceiver, LineRemoveListener {
    static final int ACTION_MODE_NONE = 0;
    static final int ACTION_MODE_SEARCH_TEXT = 1;
    static final int ACTION_MODE_SELECT_TEXT = 2;
    private static final String COPYRIGHT = "sora-editor\nCopyright (C) Rosemoe roses2020@qq.com\nThis project is distributed under the LGPL v2.1 license";
    public static final int DEFAULT_CURSOR_BLINK_PERIOD = 500;
    public static final int DEFAULT_LINE_INFO_TEXT_SIZE = 21;
    public static final int DEFAULT_TEXT_SIZE = 18;
    public static final int FLAG_DRAW_LINE_SEPARATOR = 16;
    public static final int FLAG_DRAW_TAB_SAME_AS_SPACE = 32;
    public static final int FLAG_DRAW_WHITESPACE_FOR_EMPTY_LINE = 8;
    public static final int FLAG_DRAW_WHITESPACE_INNER = 2;
    public static final int FLAG_DRAW_WHITESPACE_IN_SELECTION = 64;
    public static final int FLAG_DRAW_WHITESPACE_LEADING = 1;
    public static final int FLAG_DRAW_WHITESPACE_TRAILING = 4;
    private static final String NUMBER_DIGITS = "0 1 2 3 4 5 6 7 8 9";
    private CursorAnchorInfo.Builder anchorInfoBuilder;
    private boolean antiWordBreaking;
    private boolean anyWrapContentSet;
    private long availableFloatArrayRegion;
    private GestureDetector basicDetector;
    private boolean blockLineEnabled;
    private float blockLineWidth;
    private ClipboardManager clipboardManager;
    private EditorColorScheme colorScheme;
    private EditorAutoCompletion completionWindow;
    private int completionWndPosMode;
    protected EditorContextMenuCreator contextMenuCreator;
    private Cursor cursor;
    private boolean cursorAnimation;
    private CursorAnimator cursorAnimator;
    private CursorBlink cursorBlink;
    private int cursorPosition;
    protected List<Span> defaultSpans;
    private DiagnosticIndicatorStyle diagnosticStyle;
    protected EditorDiagnosticTooltipWindow diagnosticTooltip;
    private DiagnosticsContainer diagnostics;
    private boolean displayLnPanel;
    private float dividerMarginLeft;
    private float dividerMarginRight;
    private float dividerWidth;
    private int downX;
    private float dpUnit;
    private EdgeEffect edgeEffectHorizontal;
    private EdgeEffect edgeEffectVertical;
    private boolean editable;
    private Language editorLanguage;
    private EditorSearcher editorSearcher;
    EventManager eventManager;
    private Bundle extraArguments;
    private ExtractedTextRequest extractingTextRequest;
    private boolean firstLineNumberAlwaysVisible;
    private boolean forceHorizontalScrollable;
    private String formatTip;
    private SelectionHandleStyle.HandleDescriptor handleDescInsert;
    private SelectionHandleStyle.HandleDescriptor handleDescLeft;
    private SelectionHandleStyle.HandleDescriptor handleDescRight;
    private SelectionHandleStyle handleStyle;
    private boolean hardwareAccAllowed;
    private boolean highlightBracketPair;
    private boolean highlightCurrentBlock;
    private boolean highlightCurrentLine;
    private boolean horizontalAbsorb;
    private boolean horizontalScrollBarEnabled;
    EditorInputConnection inputConnection;
    private InputMethodManager inputMethodManager;
    private int inputType;
    private float insertSelectionWidth;
    private boolean isDisableSoftKbdOnHardKbd;
    private boolean isInLongSelect;
    private boolean isSoftKbdEnabled;
    protected final EditorKeyEventHandler keyEventHandler;
    protected SymbolPairMatch languageSymbolPairs;
    private boolean lastAnchorIsSelLeft;
    private boolean lastCursorState;
    private TextRange lastInsertion;
    private long lastMakeVisible;
    Layout layout;
    private volatile boolean layoutBusy;
    private boolean ligatureEnabled;
    private float lineInfoTextSize;
    private Paint.Align lineNumberAlign;
    private boolean lineNumberEnabled;
    private float lineNumberMarginLeft;
    private LineNumberTipTextProvider lineNumberTipTextProvider;
    private LineSeparator lineSeparator;
    private float lineSpacingAdd;
    private float lineSpacingMultiplier;
    private int lnPanelPosition;
    private int lnPanelPositionMode;
    private Matrix matrix;
    private boolean mouseButtonPressed;
    private boolean mouseHover;
    private int nonPrintableOptions;
    private boolean pinLineNumber;
    private DirectAccessProps props;
    private int rejectComposingCount;
    private boolean released;
    private boolean renderFunctionCharacters;
    private EditorRenderer renderer;
    private boolean scalable;
    private ScaleGestureDetector scaleDetector;
    private float scrollerFinalX;
    private float scrollerFinalY;
    protected CharPosition selectionAnchor;
    private SnippetController snippetController;
    int startedActionMode;
    private boolean stickyTextSelection;
    protected EditorStyleDelegate styleDelegate;
    private int tabWidth;
    private Content text;
    protected EditorTextActionWindow textActionWindow;
    private Styles textStyles;
    private EditorTouchEventHandler touchHandler;
    private boolean undoEnabled;
    private boolean verticalAbsorb;
    private float verticalExtraSpaceFactor;
    private boolean verticalScrollBarEnabled;
    private float verticalScrollFactor;
    private boolean waitForNextChange;
    private boolean wordwrap;
    private static final String LOG_TAG = "CodeEditor";
    private static final Logger logger = Logger.instance(LOG_TAG);

    static boolean hasVisibleRegion(int i, int i2, int i3, int i4) {
        return i2 > i3 && i < i4;
    }

    private boolean isWhitespace(char c) {
        return c == '\t' || c == ' ';
    }

    protected boolean canHandleKeyBinding(int i, boolean z, boolean z2, boolean z3) {
        boolean z4 = i == 19 || i == 20 || i == 21 || i == 22;
        boolean z5 = i == 122 || i == 123;
        if (!z) {
            if (z2) {
                return z4 || z5 || i == 66;
            }
            return false;
        }
        if (z2) {
            return z4 || z5 || i == 38;
        }
        if (z3) {
            return i == 66;
        }
        return z4 || z5 || i == 29 || i == 31 || i == 52 || i == 50 || i == 49 || i == 46 || i == 32 || i == 51 || i == 66;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollExtent() {
        return 0;
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return 0;
    }

    public CodeEditor(Context context) {
        this(context, null);
    }

    public CodeEditor(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.codeEditorStyle);
    }

    public CodeEditor(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CodeEditor(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.keyEventHandler = new EditorKeyEventHandler(this);
        this.defaultSpans = new ArrayList(2);
        this.downX = 0;
        this.lineSpacingMultiplier = 1.0f;
        this.lineSpacingAdd = 0.0f;
        this.verticalExtraSpaceFactor = 0.5f;
        this.diagnosticStyle = DiagnosticIndicatorStyle.WAVY_LINE;
        this.lastMakeVisible = 0L;
        initialize(attributeSet, i, i2);
        applyAttributeSets(attributeSet, i, i2);
    }

    public <T extends EditorBuiltinComponent> T getComponent(Class<T> cls) {
        if (cls == EditorAutoCompletion.class) {
            return this.completionWindow;
        }
        if (cls == Magnifier.class) {
            return this.touchHandler.magnifier;
        }
        if (cls == EditorTextActionWindow.class) {
            return this.textActionWindow;
        }
        if (cls == EditorDiagnosticTooltipWindow.class) {
            return this.diagnosticTooltip;
        }
        if (cls == EditorContextMenuCreator.class) {
            return this.contextMenuCreator;
        }
        throw new IllegalArgumentException("Unknown component type");
    }

    public <T extends EditorBuiltinComponent> void replaceComponent(Class<T> cls, T t) {
        EditorBuiltinComponent component = getComponent(cls);
        boolean zIsEnabled = component.isEnabled();
        component.setEnabled(false);
        if (cls == EditorAutoCompletion.class) {
            this.completionWindow = (EditorAutoCompletion) t;
        } else if (cls == Magnifier.class) {
            this.touchHandler.magnifier = (Magnifier) t;
        } else if (cls == EditorTextActionWindow.class) {
            this.textActionWindow = (EditorTextActionWindow) t;
        } else if (cls == EditorDiagnosticTooltipWindow.class) {
            this.diagnosticTooltip = (EditorDiagnosticTooltipWindow) t;
        } else if (cls == EditorContextMenuCreator.class) {
            this.contextMenuCreator = (EditorContextMenuCreator) t;
        } else {
            throw new IllegalArgumentException("Unknown component type");
        }
        t.setEnabled(zIsEnabled);
    }

    public KeyMetaStates getKeyMetaStates() {
        return this.keyEventHandler.getKeyMetaStates();
    }

    protected void cancelAnimation() {
        this.lastMakeVisible = System.currentTimeMillis();
    }

    public float measureTextRegionOffset() {
        if (isLineNumberEnabled()) {
            return measureLineNumber() + this.dividerMarginLeft + this.dividerMarginRight + this.dividerWidth + (this.renderer.hasSideHintIcons() ? getRowHeight() : 0);
        }
        return this.dpUnit * 5.0f;
    }

    public SelectionHandleStyle.HandleDescriptor getLeftHandleDescriptor() {
        return this.handleDescLeft;
    }

    public SelectionHandleStyle.HandleDescriptor getRightHandleDescriptor() {
        return this.handleDescRight;
    }

    public float getOffset(int i, int i2) {
        return (this.layout.getCharLayoutOffset(i, i2)[1] + measureTextRegionOffset()) - getOffsetX();
    }

    public float getCharOffsetX(int i, int i2) {
        return (this.layout.getCharLayoutOffset(i, i2)[1] + measureTextRegionOffset()) - getOffsetX();
    }

    public float getCharOffsetY(int i, int i2) {
        return this.layout.getCharLayoutOffset(i, i2)[0] - getOffsetY();
    }

    protected void initialize(AttributeSet attributeSet, int i, int i2) {
        Log.v(LOG_TAG, COPYRIGHT);
        this.eventManager = new EventManager();
        this.renderFunctionCharacters = true;
        this.renderer = onCreateRenderer();
        this.styleDelegate = new EditorStyleDelegate(this);
        this.verticalScrollFactor = ViewUtils.getVerticalScrollFactor(getContext());
        this.lineSeparator = LineSeparator.LF;
        this.lineNumberTipTextProvider = DefaultLineNumberTip.INSTANCE;
        this.formatTip = I18nConfig.getString(getContext(), R.string.sora_editor_editor_formatting);
        this.props = new DirectAccessProps();
        float fApplyDimension = TypedValue.applyDimension(1, 10.0f, Resources.getSystem().getDisplayMetrics()) / 10.0f;
        this.dpUnit = fApplyDimension;
        this.dividerWidth = fApplyDimension;
        this.insertSelectionWidth = fApplyDimension;
        float f = fApplyDimension * 2.0f;
        this.dividerMarginRight = f;
        this.dividerMarginLeft = f;
        this.matrix = new Matrix();
        this.handleStyle = new HandleStyleSideDrop(getContext());
        this.editorSearcher = new EditorSearcher(this);
        this.cursorAnimator = new MoveCursorAnimator(this);
        setCursorBlinkPeriod(500);
        this.anchorInfoBuilder = new CursorAnchorInfo.Builder();
        this.startedActionMode = 0;
        setTextSize(18.0f);
        setLineInfoTextSize(TypedValue.applyDimension(2, 21.0f, Resources.getSystem().getDisplayMetrics()));
        EditorColorScheme editorColorScheme = EditorColorScheme.getDefault();
        this.colorScheme = editorColorScheme;
        editorColorScheme.attachEditor(this);
        this.touchHandler = new EditorTouchEventHandler(this);
        GestureDetector gestureDetector = new GestureDetector(getContext(), this.touchHandler);
        this.basicDetector = gestureDetector;
        gestureDetector.setOnDoubleTapListener(this.touchHandler);
        this.scaleDetector = new ScaleGestureDetector(getContext(), this.touchHandler);
        this.handleDescInsert = new SelectionHandleStyle.HandleDescriptor();
        this.handleDescLeft = new SelectionHandleStyle.HandleDescriptor();
        this.handleDescRight = new SelectionHandleStyle.HandleDescriptor();
        this.lineNumberAlign = Paint.Align.RIGHT;
        this.waitForNextChange = false;
        this.blockLineEnabled = true;
        this.blockLineWidth = 1.0f;
        this.inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        this.clipboardManager = (ClipboardManager) getContext().getSystemService("clipboard");
        setUndoEnabled(true);
        this.cursorPosition = -1;
        setScalable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.highlightBracketPair = true;
        this.inputConnection = new EditorInputConnection(this);
        this.completionWindow = new EditorAutoCompletion(this);
        this.edgeEffectVertical = new EdgeEffect(getContext());
        this.edgeEffectHorizontal = new EdgeEffect(getContext());
        this.textActionWindow = new EditorTextActionWindow(this);
        this.diagnosticTooltip = new EditorDiagnosticTooltipWindow(this);
        this.contextMenuCreator = new EditorContextMenuCreator(this);
        setEditorLanguage(null);
        setText(null);
        setTabWidth(4);
        setHighlightCurrentLine(true);
        setVerticalScrollBarEnabled(true);
        setHighlightCurrentBlock(true);
        setDisplayLnPanel(true);
        setHorizontalScrollBarEnabled(true);
        setFirstLineNumberAlwaysVisible(true);
        setCursorAnimationEnabled(true);
        setEditable(true);
        setLineNumberEnabled(true);
        setHardwareAcceleratedDrawAllowed(true);
        setInterceptParentHorizontalScrollIfNeeded(false);
        setTypefaceText(Typeface.DEFAULT);
        setSoftKeyboardEnabled(true);
        setDisableSoftKbdIfHardKbdAvailable(true);
        setDefaultFocusHighlightEnabled(false);
        if (getContext() instanceof ContextThemeWrapper) {
            setEdgeEffectColor(ThemeUtils.getColorPrimary((ContextThemeWrapper) getContext()));
        }
        this.scaleDetector.setQuickScaleEnabled(false);
        this.snippetController = new SnippetController(this);
    }

    protected void applyAttributeSets(AttributeSet attributeSet, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CodeEditor, i, i2);
        setHorizontalScrollbarThumbDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.CodeEditor_android_scrollbarThumbHorizontal));
        setHorizontalScrollbarTrackDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.CodeEditor_android_scrollbarTrackHorizontal));
        setVerticalScrollbarThumbDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.CodeEditor_android_scrollbarThumbVertical));
        setVerticalScrollbarTrackDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.CodeEditor_android_scrollbarTrackVertical));
        setLnPanelPositionMode(typedArrayObtainStyledAttributes.getInt(R.styleable.CodeEditor_lnPanelPositionMode, 1));
        setLnPanelPosition(typedArrayObtainStyledAttributes.getInt(R.styleable.CodeEditor_lnPanelPosition, 15));
        setDividerWidth(typedArrayObtainStyledAttributes.getDimension(R.styleable.CodeEditor_dividerWidth, getDividerWidth()));
        setDividerMargin(typedArrayObtainStyledAttributes.getDimension(R.styleable.CodeEditor_dividerMargin, this.dividerMarginLeft), typedArrayObtainStyledAttributes.getDimension(R.styleable.CodeEditor_dividerMargin, this.dividerMarginRight));
        setPinLineNumber(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_pinLineNumber, false));
        setHighlightCurrentBlock(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_highlightCurrentBlock, true));
        setHighlightCurrentLine(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_highlightCurrentLine, true));
        setHighlightBracketPair(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_highlightBracketPair, true));
        setLigatureEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_ligatures, true));
        setLineNumberEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_lineNumberVisible, isLineNumberEnabled()));
        ((EditorAutoCompletion) getComponent(EditorAutoCompletion.class)).setEnabled(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_autoCompleteEnabled, true));
        this.props.symbolPairAutoCompletion = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_symbolCompletionEnabled, true);
        setRenderFunctionCharacters(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_renderFunctionChars, isRenderFunctionCharacters()));
        setScalable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.CodeEditor_scalable, isScalable()));
        setTextSizePx(typedArrayObtainStyledAttributes.getDimension(R.styleable.CodeEditor_textSize, getTextSizePx()));
        setCursorBlinkPeriod(typedArrayObtainStyledAttributes.getInt(R.styleable.CodeEditor_cursorBlinkPeriod, getCursorBlink().period));
        setTabWidth(typedArrayObtainStyledAttributes.getInt(R.styleable.CodeEditor_tabWidth, getTabWidth()));
        int i3 = typedArrayObtainStyledAttributes.getInt(R.styleable.CodeEditor_wordwrapMode, 0);
        if (i3 != 0) {
            setWordwrap(true, i3 > 1);
        }
        setText(typedArrayObtainStyledAttributes.getString(R.styleable.CodeEditor_text));
        typedArrayObtainStyledAttributes.recycle();
    }

    public SnippetController getSnippetController() {
        return this.snippetController;
    }

    public DirectAccessProps getProps() {
        return this.props;
    }

    public String getFormatTip() {
        return this.formatTip;
    }

    public void setFormatTip(String str) {
        this.formatTip = (String) Objects.requireNonNull(str);
    }

    public void setPinLineNumber(boolean z) {
        this.pinLineNumber = z;
        if (isLineNumberEnabled()) {
            invalidate();
        }
    }

    public boolean isLineNumberPinned() {
        return this.pinLineNumber;
    }

    public boolean isFirstLineNumberAlwaysVisible() {
        return this.firstLineNumberAlwaysVisible;
    }

    public void setFirstLineNumberAlwaysVisible(boolean z) {
        this.firstLineNumberAlwaysVisible = z;
        if (isWordwrap()) {
            invalidate();
        }
    }

    public void insertText(String str, int i) {
        if (i < 0 || i > str.length()) {
            throw new IllegalArgumentException("selectionOffset is invalid");
        }
        Cursor cursor = getText().getCursor();
        if (cursor.isSelected()) {
            deleteText();
            notifyIMEExternalCursorChange();
        }
        this.text.insert(cursor.getRightLine(), cursor.getRightColumn(), str);
        notifyIMEExternalCursorChange();
        if (i != str.length()) {
            CharPosition charPosition = this.text.getIndexer().getCharPosition(cursor.getRight() - (str.length() - i));
            setSelection(charPosition.line, charPosition.column);
        }
    }

    public void setCursorBlinkPeriod(int i) {
        CursorBlink cursorBlink = this.cursorBlink;
        if (cursorBlink == null) {
            this.cursorBlink = new CursorBlink(this, i);
            return;
        }
        int i2 = cursorBlink.period;
        this.cursorBlink.setPeriod(i);
        if (i2 <= 0 && this.cursorBlink.valid && isAttachedToWindow()) {
            postInLifecycle(this.cursorBlink);
        }
    }

    protected CursorBlink getCursorBlink() {
        return this.cursorBlink;
    }

    public boolean isLigatureEnabled() {
        return this.ligatureEnabled;
    }

    public void setLigatureEnabled(boolean z) {
        this.ligatureEnabled = z;
        setFontFeatureSettings(z ? null : "'liga' 0,'calt' 0,'hlig' 0,'dlig' 0,'clig' 0");
    }

    public void setFontFeatureSettings(String str) {
        this.renderer.getPaint().setFontFeatureSettingsWrapped(str);
        this.renderer.getPaintOther().setFontFeatureSettings(str);
        this.renderer.getPaintGraph().setFontFeatureSettings(str);
        this.renderer.updateTimestamp();
        invalidate();
    }

    public void setSelectionHandleStyle(SelectionHandleStyle selectionHandleStyle) {
        this.handleStyle = (SelectionHandleStyle) Objects.requireNonNull(selectionHandleStyle);
        invalidate();
    }

    public SelectionHandleStyle getHandleStyle() {
        return this.handleStyle;
    }

    public boolean isHighlightCurrentBlock() {
        return this.highlightCurrentBlock;
    }

    public void setHighlightCurrentBlock(boolean z) {
        this.highlightCurrentBlock = z;
        if (!z) {
            this.cursorPosition = -1;
        } else {
            this.cursorPosition = findCursorBlock();
        }
        invalidate();
    }

    public boolean isStickyTextSelection() {
        return this.stickyTextSelection;
    }

    public void setStickyTextSelection(boolean z) {
        this.stickyTextSelection = z;
    }

    public boolean isHighlightCurrentLine() {
        return this.highlightCurrentLine;
    }

    public void setHighlightCurrentLine(boolean z) {
        this.highlightCurrentLine = z;
        invalidate();
    }

    public Language getEditorLanguage() {
        return this.editorLanguage;
    }

    public void setEditorLanguage(Language language) {
        if (language == null) {
            language = new EmptyLanguage();
        }
        Language language2 = this.editorLanguage;
        if (language2 != null) {
            Formatter formatter = language2.getFormatter();
            formatter.setReceiver(null);
            formatter.destroy();
            language2.getAnalyzeManager().setReceiver(null);
            language2.getAnalyzeManager().destroy();
            language2.destroy();
        }
        this.styleDelegate.reset();
        this.editorLanguage = language;
        this.textStyles = null;
        this.diagnostics = null;
        AnalyzeManager analyzeManager = language.getAnalyzeManager();
        analyzeManager.setReceiver(this.styleDelegate);
        if (this.text != null) {
            analyzeManager.reset(new ContentReference(this.text), this.extraArguments);
        }
        SymbolPairMatch symbolPairMatch = this.languageSymbolPairs;
        if (symbolPairMatch != null) {
            symbolPairMatch.setParent(null);
        }
        SymbolPairMatch symbolPairs = this.editorLanguage.getSymbolPairs();
        this.languageSymbolPairs = symbolPairs;
        if (symbolPairs == null) {
            Log.w(LOG_TAG, "Language(" + this.editorLanguage.toString() + ") returned null for symbol pairs. It is a mistake.");
            this.languageSymbolPairs = new SymbolPairMatch();
        }
        this.languageSymbolPairs.setParent(this.props.overrideSymbolPairs);
        SnippetController snippetController = this.snippetController;
        if (snippetController != null) {
            snippetController.stopSnippet();
        }
        this.renderer.invalidateRenderNodes();
        invalidate();
    }

    public float getBlockLineWidth() {
        return this.blockLineWidth;
    }

    public void setBlockLineWidth(float f) {
        this.blockLineWidth = f;
        invalidate();
    }

    public boolean isWordwrap() {
        return this.wordwrap;
    }

    public boolean isAntiWordBreaking() {
        return this.antiWordBreaking;
    }

    public void setWordwrap(boolean z) {
        setWordwrap(z, true);
    }

    public void setWordwrap(boolean z, boolean z2) {
        if (this.wordwrap == z && this.antiWordBreaking == z2) {
            return;
        }
        this.wordwrap = z;
        this.antiWordBreaking = z2;
        requestLayoutIfNeeded();
        createLayout();
        if (!z) {
            this.renderer.invalidateRenderNodes();
        }
        invalidate();
    }

    public boolean isCursorAnimationEnabled() {
        return this.cursorAnimation;
    }

    public void setCursorAnimationEnabled(boolean z) {
        if (!z) {
            this.cursorAnimator.cancel();
        }
        this.cursorAnimation = z;
    }

    public CursorAnimator getCursorAnimator() {
        return this.cursorAnimator;
    }

    public void setCursorAnimator(CursorAnimator cursorAnimator) {
        this.cursorAnimator = cursorAnimator;
    }

    public void setScrollBarEnabled(boolean z) {
        this.horizontalScrollBarEnabled = z;
        this.verticalScrollBarEnabled = z;
        invalidate();
    }

    @Override // android.view.View
    public void setHorizontalScrollbarThumbDrawable(Drawable drawable) {
        this.renderer.setHorizontalScrollbarThumbDrawable(drawable);
    }

    @Override // android.view.View
    public Drawable getHorizontalScrollbarThumbDrawable() {
        return this.renderer.getHorizontalScrollbarThumbDrawable();
    }

    @Override // android.view.View
    public void setHorizontalScrollbarTrackDrawable(Drawable drawable) {
        this.renderer.setHorizontalScrollbarTrackDrawable(drawable);
    }

    @Override // android.view.View
    public Drawable getHorizontalScrollbarTrackDrawable() {
        return this.renderer.getHorizontalScrollbarTrackDrawable();
    }

    @Override // android.view.View
    public void setVerticalScrollbarThumbDrawable(Drawable drawable) {
        this.renderer.setVerticalScrollbarThumbDrawable(drawable);
    }

    @Override // android.view.View
    public Drawable getVerticalScrollbarThumbDrawable() {
        return this.renderer.getVerticalScrollbarThumbDrawable();
    }

    @Override // android.view.View
    public void setVerticalScrollbarTrackDrawable(Drawable drawable) {
        this.renderer.setVerticalScrollbarTrackDrawable(drawable);
    }

    @Override // android.view.View
    public Drawable getVerticalScrollbarTrackDrawable() {
        return this.renderer.getVerticalScrollbarTrackDrawable();
    }

    public boolean isDisplayLnPanel() {
        return this.displayLnPanel;
    }

    public void setDisplayLnPanel(boolean z) {
        this.displayLnPanel = z;
        invalidate();
    }

    public int getLnPanelPositionMode() {
        return this.lnPanelPositionMode;
    }

    public void setLnPanelPositionMode(int i) {
        this.lnPanelPositionMode = i;
        invalidate();
    }

    public int getLnPanelPosition() {
        return this.lnPanelPosition;
    }

    public void setLnPanelPosition(int i) {
        this.lnPanelPosition = i;
        invalidate();
    }

    public LineNumberTipTextProvider getLineNumberTipTextProvider() {
        return this.lineNumberTipTextProvider;
    }

    public void setLineNumberTipTextProvider(LineNumberTipTextProvider lineNumberTipTextProvider) {
        Objects.requireNonNull(lineNumberTipTextProvider, "Provider can not be null");
        this.lineNumberTipTextProvider = lineNumberTipTextProvider;
        invalidate();
    }

    @Override // android.view.View
    public boolean isHorizontalScrollBarEnabled() {
        return this.horizontalScrollBarEnabled;
    }

    @Override // android.view.View
    public void setHorizontalScrollBarEnabled(boolean z) {
        this.horizontalScrollBarEnabled = z;
    }

    @Override // android.view.View
    public boolean isVerticalScrollBarEnabled() {
        return this.verticalScrollBarEnabled;
    }

    @Override // android.view.View
    public void setVerticalScrollBarEnabled(boolean z) {
        this.verticalScrollBarEnabled = z;
    }

    public SelectionHandleStyle.HandleDescriptor getInsertHandleDescriptor() {
        return this.handleDescInsert;
    }

    public float getTextSizePx() {
        return this.renderer.getPaint().getTextSize();
    }

    public void setTextSizePx(float f) {
        setTextSizePxDirect(f);
        requestLayoutIfNeeded();
        createLayout();
        invalidate();
    }

    protected void setTextSizePxDirect(float f) {
        this.renderer.setTextSizePxDirect(f);
    }

    protected void requestLayoutIfNeeded() {
        if (this.anyWrapContentSet) {
            requestLayout();
        }
    }

    protected void checkForRelayout() {
        ViewGroup.LayoutParams layoutParams;
        if (!this.anyWrapContentSet || (layoutParams = getLayoutParams()) == null) {
            return;
        }
        if (layoutParams.width == -2) {
            requestLayout();
        } else {
            if (layoutParams.height != -2 || getHeight() == this.layout.getLayoutHeight()) {
                return;
            }
            requestLayout();
        }
    }

    public EditorRenderer getRenderer() {
        return this.renderer;
    }

    public Paint.FontMetricsInt getLineNumberMetrics() {
        return this.renderer.getLineNumberMetrics();
    }

    protected void rememberDisplayedLines() {
        this.availableFloatArrayRegion = IntPair.pack(getFirstVisibleLine(), getLastVisibleLine());
    }

    protected float[] obtainFloatArray(int i, boolean z) {
        int first = IntPair.getFirst(this.availableFloatArrayRegion);
        int second = IntPair.getSecond(this.availableFloatArrayRegion);
        int firstVisibleLine = getFirstVisibleLine();
        int lastVisibleLine = getLastVisibleLine();
        int iMax = Math.max(0, first - 5);
        int iMin = Math.min(second + 5, getLineCount());
        while (iMax < iMin) {
            if (iMax < firstVisibleLine || iMax > lastVisibleLine) {
                ContentLine line = z ? this.renderer.getLine(iMax) : this.text.getLine(iMax);
                if (line.widthCache != null && line.widthCache.length >= i) {
                    line.timestamp = 0L;
                    float[] fArr = line.widthCache;
                    line.widthCache = null;
                    return fArr;
                }
            }
            if (iMax >= firstVisibleLine && iMax <= lastVisibleLine) {
                iMax = lastVisibleLine;
            }
            iMax++;
        }
        return new float[i];
    }

    protected boolean shouldInitializeNonPrintable() {
        return Numbers.clearBit(Numbers.clearBit(this.nonPrintableOptions, 8), 32) != 0;
    }

    public boolean isHardwareAcceleratedDrawAllowed() {
        return this.hardwareAccAllowed;
    }

    public void setHardwareAcceleratedDrawAllowed(boolean z) {
        this.hardwareAccAllowed = z;
        if (!z || isWordwrap()) {
            return;
        }
        this.renderer.invalidateRenderNodes();
    }

    protected long findLeadingAndTrailingWhitespacePos(ContentLine contentLine) {
        char[] cArr = contentLine.value;
        int length = contentLine.length();
        int i = 0;
        while (i < length && isWhitespace(cArr[i])) {
            i++;
        }
        if (i != length && (this.nonPrintableOptions & 6) != 0) {
            while (length > 0 && isWhitespace(cArr[length - 1])) {
                length--;
            }
        }
        return IntPair.pack(i, length);
    }

    protected void computeMatchedPositions(int i, LongArrayList longArrayList) {
        LongArrayList longArrayList2;
        longArrayList.clear();
        if (this.editorSearcher.currentPattern == null || this.editorSearcher.searchOptions == null || !this.editorSearcher.isResultValid() || (longArrayList2 = this.editorSearcher.lastResults) == null) {
            return;
        }
        int charIndex = this.text.getCharIndex(i, 0);
        int columnCount = this.text.getColumnCount(i) + charIndex;
        for (int iMax = Math.max(0, longArrayList.lowerBoundByFirst(charIndex) - 1); iMax < longArrayList2.size(); iMax++) {
            long j = longArrayList2.get(iMax);
            int first = IntPair.getFirst(j);
            int second = IntPair.getSecond(j);
            int iMax2 = Math.max(first, charIndex);
            int iMin = Math.min(second, columnCount);
            if (iMax2 < iMin) {
                longArrayList.add(IntPair.pack(iMax2 - charIndex, iMin - charIndex));
            }
            if (first > columnCount) {
                return;
            }
        }
    }

    public int getEdgeEffectColor() {
        return this.edgeEffectVertical.getColor();
    }

    public void setEdgeEffectColor(int i) {
        this.edgeEffectVertical.setColor(i);
        this.edgeEffectHorizontal.setColor(i);
    }

    public Layout getLayout() {
        return this.layout;
    }

    protected EdgeEffect getVerticalEdgeEffect() {
        return this.edgeEffectVertical;
    }

    protected EdgeEffect getHorizontalEdgeEffect() {
        return this.edgeEffectHorizontal;
    }

    private int findCursorBlock() {
        Styles styles = this.textStyles;
        List<CodeBlock> list = styles == null ? null : styles.blocks;
        if (list == null || list.isEmpty()) {
            return -1;
        }
        return findCursorBlock(list);
    }

    private int findCursorBlock(List<CodeBlock> list) {
        int leftLine = this.cursor.getLeftLine();
        int iBinarySearchEndBlock = binarySearchEndBlock(leftLine, list);
        int i = 0;
        int i2 = -1;
        if (iBinarySearchEndBlock == -1) {
            iBinarySearchEndBlock = 0;
        }
        int size = list.size() - 1;
        Styles styles = this.textStyles;
        int suppressSwitch = styles != null ? styles.getSuppressSwitch() : Integer.MAX_VALUE;
        int i3 = Integer.MAX_VALUE;
        while (iBinarySearchEndBlock <= size) {
            CodeBlock codeBlock = list.get(iBinarySearchEndBlock);
            if (codeBlock != null) {
                if (codeBlock.endLine >= leftLine && codeBlock.startLine <= leftLine) {
                    int i4 = codeBlock.endLine - codeBlock.startLine;
                    if (i4 < i3) {
                        i2 = iBinarySearchEndBlock;
                        i3 = i4;
                    }
                } else if (i3 != Integer.MAX_VALUE && (i = i + 1) >= suppressSwitch) {
                    break;
                }
            }
            iBinarySearchEndBlock++;
        }
        return i2;
    }

    public int getCurrentCursorBlock() {
        return this.cursorPosition;
    }

    int binarySearchEndBlock(int i, List<CodeBlock> list) {
        return CodeBlock.binarySearchEndBlock(i, list);
    }

    public List<Span> getSpansForLine(int i) {
        Styles styles = this.textStyles;
        Spans spans = styles == null ? null : styles.spans;
        if (this.defaultSpans.isEmpty()) {
            this.defaultSpans.add(SpanFactory.obtain(0, 5L));
        }
        try {
            if (spans != null) {
                return spans.read().getSpansOnLine(i);
            }
            return this.defaultSpans;
        } catch (Exception unused) {
            return this.defaultSpans;
        }
    }

    public float measureLineNumber() {
        float fMax = 0.0f;
        if (!isLineNumberEnabled()) {
            return 0.0f;
        }
        int i = 0;
        for (int lineCount = getLineCount(); lineCount > 0; lineCount /= 10) {
            i++;
        }
        int length = NUMBER_DIGITS.length();
        float[] fArrObtain = TemporaryFloatBuffer.obtain(length);
        this.renderer.getPaintOther().getTextWidths(NUMBER_DIGITS, fArrObtain);
        TemporaryFloatBuffer.recycle(fArrObtain);
        for (int i2 = 0; i2 < length; i2 += 2) {
            fMax = Math.max(fMax, fArrObtain[i2]);
        }
        return (fMax * i) + this.lineNumberMarginLeft;
    }

    protected void createLayout() {
        createLayout(true);
    }

    protected void createLayout(boolean z) {
        Layout layout = this.layout;
        if (layout != null) {
            if ((layout instanceof LineBreakLayout) && !this.wordwrap) {
                ((LineBreakLayout) layout).reuse(this.text);
                return;
            } else {
                if ((layout instanceof WordwrapLayout) && this.wordwrap) {
                    WordwrapLayout wordwrapLayout = new WordwrapLayout(this, this.text, this.antiWordBreaking, ((WordwrapLayout) this.layout).getRowTable(), z);
                    this.layout.destroyLayout();
                    this.layout = wordwrapLayout;
                    return;
                }
                layout.destroyLayout();
            }
        }
        if (this.wordwrap) {
            this.renderer.setCachedLineNumberWidth((int) measureLineNumber());
            this.layout = new WordwrapLayout(this, this.text, this.antiWordBreaking, null, false);
        } else {
            this.layout = new LineBreakLayout(this, this.text);
        }
        EditorTouchEventHandler editorTouchEventHandler = this.touchHandler;
        if (editorTouchEventHandler != null) {
            editorTouchEventHandler.scrollBy(0.0f, 0.0f);
        }
    }

    public void indentSelection() {
        indentLines(true);
    }

    public void indentLines(boolean z) {
        Cursor cursor = getCursor();
        if (!z || cursor.isSelected()) {
            String strCreateTabString = createTabString();
            Content text = getText();
            int tabWidth = getTabWidth();
            text.beginBatchEdit();
            for (int leftLine = cursor.getLeftLine(); leftLine <= cursor.getRightLine(); leftLine++) {
                long jCountLeadingSpacesAndTabs = TextUtils.countLeadingSpacesAndTabs(text.getLine(leftLine));
                int first = IntPair.getFirst(jCountLeadingSpacesAndTabs);
                int second = IntPair.getSecond(jCountLeadingSpacesAndTabs);
                int i = (second * tabWidth) + first;
                int i2 = first + second;
                int i3 = tabWidth - (i % tabWidth);
                if (first > 0 && second > 0) {
                    if (i3 == 0) {
                        i3 = tabWidth;
                    }
                    text.replace(leftLine, 0, leftLine, i2, StringsKt.repeat(strCreateTabString, (i3 + i) / tabWidth));
                } else if (i3 == 0) {
                    text.insert(leftLine, i2, strCreateTabString);
                } else {
                    text.insert(leftLine, i2, StringsKt.repeat(" ", i3));
                }
            }
            text.endBatchEdit();
        }
    }

    public void unindentSelection() {
        Cursor cursor = getCursor();
        Content text = getText();
        int tabWidth = getTabWidth();
        String strCreateTabString = createTabString();
        text.beginBatchEdit();
        for (int leftLine = cursor.getLeftLine(); leftLine <= cursor.getRightLine(); leftLine++) {
            long jCountLeadingSpacesAndTabs = TextUtils.countLeadingSpacesAndTabs(text.getLineString(leftLine));
            int first = IntPair.getFirst(jCountLeadingSpacesAndTabs);
            int second = IntPair.getSecond(jCountLeadingSpacesAndTabs);
            int i = (second * tabWidth) + first;
            if (i != 0) {
                int i2 = first + second;
                int i3 = i % tabWidth;
                if (first > 0 && second > 0) {
                    if (i3 == 0) {
                        i3 = tabWidth;
                    }
                    text.replace(leftLine, 0, leftLine, i2, StringsKt.repeat(strCreateTabString, Math.abs(i - i3) / tabWidth));
                } else if (i3 == 0) {
                    text.delete(leftLine, i2 - (second > 0 ? 1 : tabWidth), leftLine, i2);
                } else {
                    text.delete(leftLine, i2 - i3, leftLine, i2);
                }
            }
        }
        text.endBatchEdit();
    }

    protected void commitTab() {
        if (this.inputConnection == null || !isEditable()) {
            return;
        }
        this.inputConnection.commitTextInternal(createTabString(), true);
    }

    public void indentOrCommitTab() {
        Cursor cursor = getCursor();
        if (cursor.isSelected()) {
            indentSelection();
            return;
        }
        CharPosition charPositionLeft = cursor.left();
        long jCountLeadingSpacesAndTabs = TextUtils.countLeadingSpacesAndTabs(getText().getLine(charPositionLeft.line));
        if (charPositionLeft.column > IntPair.getFirst(jCountLeadingSpacesAndTabs) + IntPair.getSecond(jCountLeadingSpacesAndTabs)) {
            commitTab();
        } else {
            indentLines(false);
        }
    }

    protected String createTabString() {
        return TextUtils.createIndent(getTabWidth(), getTabWidth(), getEditorLanguage().useTab());
    }

    public float updateCursorAnchor() {
        boolean z;
        int rightLine = this.cursor.getRightLine();
        float fMeasureTextRegionOffset = (measureTextRegionOffset() + this.layout.getCharLayoutOffset(rightLine, this.cursor.getRightColumn())[1]) - getOffsetX();
        if (fMeasureTextRegionOffset < 0.0f) {
            fMeasureTextRegionOffset = 0.0f;
            z = false;
        } else {
            z = true;
        }
        if (this.props.reportCursorAnchor) {
            CursorAnchorInfo.Builder builder = this.anchorInfoBuilder;
            builder.reset();
            this.matrix.set(getMatrix());
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            this.matrix.postTranslate(iArr[0], iArr[1]);
            builder.setMatrix(this.matrix);
            builder.setSelectionRange(this.cursor.getLeft(), this.cursor.getRight());
            builder.setInsertionMarkerLocation(fMeasureTextRegionOffset, getRowTop(rightLine) - getOffsetY(), getRowBaseline(rightLine) - getOffsetY(), getRowBottom(rightLine) - getOffsetY(), z ? 1 : 2);
            this.inputMethodManager.updateCursorAnchorInfo(this, builder.build());
        }
        return fMeasureTextRegionOffset;
    }

    public void deleteText() {
        boolean z;
        Cursor cursor = this.cursor;
        if (cursor.isSelected()) {
            this.text.delete(cursor.getLeftLine(), cursor.getLeftColumn(), cursor.getRightLine(), cursor.getRightColumn());
            return;
        }
        int leftColumn = cursor.getLeftColumn();
        int leftLine = cursor.getLeftLine();
        if (this.props.deleteEmptyLineFast || (this.props.deleteMultiSpaces != 1 && leftColumn > 0 && this.text.charAt(leftLine, leftColumn - 1) == ' ')) {
            char[] cArr = this.text.getLine(cursor.getLeftLine()).value;
            int i = leftColumn - 1;
            int i2 = i;
            while (true) {
                if (i2 >= 0) {
                    char c = cArr[i2];
                    if (c != ' ' && c != '\t') {
                        break;
                    } else {
                        i2--;
                    }
                } else {
                    int columnCount = this.text.getColumnCount(leftLine);
                    int i3 = leftColumn;
                    while (true) {
                        if (i3 < columnCount) {
                            char c2 = cArr[i3];
                            if (c2 != ' ' && c2 != '\t') {
                                z = false;
                                break;
                            }
                            i3++;
                        } else {
                            z = true;
                            break;
                        }
                    }
                    if (!this.props.deleteEmptyLineFast || !z) {
                        if (this.props.deleteMultiSpaces == 1 || leftColumn <= 0 || this.text.charAt(leftLine, i) != ' ') {
                            break;
                            break;
                            break;
                        } else {
                            this.text.delete(leftLine, Math.max(0, leftColumn - (this.props.deleteMultiSpaces == -1 ? getTabWidth() : this.props.deleteMultiSpaces)), leftLine, leftColumn);
                            return;
                        }
                    }
                    if (leftLine == 0) {
                        this.text.delete(leftLine, 0, leftLine, leftColumn);
                        return;
                    }
                    Content content = this.text;
                    int i4 = leftLine - 1;
                    content.delete(i4, content.getColumnCount(i4), leftLine, columnCount);
                    return;
                }
            }
        }
        int curPosLeft = TextLayoutHelper.get().getCurPosLeft(leftColumn, this.text.getLine(cursor.getLeftLine()));
        int leftColumn2 = cursor.getLeftColumn();
        if (curPosLeft > leftColumn2) {
            leftColumn2 = curPosLeft;
            curPosLeft = leftColumn2;
        }
        if (curPosLeft == leftColumn2) {
            if (cursor.getLeftLine() > 0) {
                this.text.delete(cursor.getLeftLine() - 1, this.text.getColumnCount(cursor.getLeftLine() - 1), cursor.getLeftLine(), 0);
                return;
            }
            return;
        }
        this.text.delete(cursor.getLeftLine(), curPosLeft, cursor.getLeftLine(), leftColumn2);
    }

    public void commitText(CharSequence charSequence) {
        commitText(charSequence, true);
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void commitText(CharSequence charSequence, boolean z) {
        if (charSequence.length() == 0) {
            return;
        }
        Cursor cursor = this.cursor;
        int i = 1;
        if (cursor.isSelected()) {
            if (charSequence.length() > 0 && charSequence.length() == 1) {
                QuickQuoteHandler quickQuoteHandler = this.editorLanguage.getQuickQuoteHandler();
                System.out.println(quickQuoteHandler);
                QuickQuoteHandler.HandleResult handleResultOnHandleTyping = quickQuoteHandler == null ? null : quickQuoteHandler.onHandleTyping(charSequence.toString(), this.text, getCursorRange(), getStyles());
                if (handleResultOnHandleTyping != null && handleResultOnHandleTyping.isConsumed()) {
                    TextRange newCursorRange = handleResultOnHandleTyping.getNewCursorRange();
                    if (newCursorRange != null) {
                        setSelectionRegion(newCursorRange.getStart().line, newCursorRange.getStart().column, newCursorRange.getEnd().line, newCursorRange.getEnd().column);
                        return;
                    }
                    return;
                }
            }
            this.text.replace(cursor.getLeftLine(), cursor.getLeftColumn(), cursor.getRightLine(), cursor.getRightColumn(), charSequence);
            return;
        }
        if (this.props.autoIndent && charSequence.length() != 0 && z) {
            char cCharAt = charSequence.charAt(0);
            if (cCharAt == '\n' || cCharAt == '\r') {
                String lineString = this.text.getLineString(cursor.getLeftLine());
                int indentAdvance = 0;
                for (int i2 = 0; i2 < cursor.getLeftColumn() && isWhitespace(lineString.charAt(i2)); i2++) {
                    indentAdvance = lineString.charAt(i2) == '\t' ? indentAdvance + this.tabWidth : indentAdvance + 1;
                }
                try {
                    indentAdvance += this.editorLanguage.getIndentAdvance(new ContentReference(this.text), cursor.getLeftLine(), cursor.getLeftColumn());
                } catch (Exception e) {
                    Log.w(LOG_TAG, "Language object error", e);
                }
                if (cCharAt == '\r' && charSequence.length() >= 2 && charSequence.charAt(1) == '\n') {
                    i = 2;
                }
                StringBuilder sb = new StringBuilder(charSequence);
                sb.insert(i, TextUtils.createIndent(indentAdvance, this.tabWidth, this.editorLanguage.useTab()));
                charSequence = sb;
            }
        }
        this.text.insert(cursor.getLeftLine(), cursor.getLeftColumn(), charSequence);
    }

    public float getLineInfoTextSize() {
        return this.lineInfoTextSize;
    }

    public void setLineInfoTextSize(float f) {
        if (f <= 0.0f) {
            throw new IllegalArgumentException();
        }
        this.lineInfoTextSize = f;
    }

    public int getNonPrintablePaintingFlags() {
        return this.nonPrintableOptions;
    }

    public void setNonPrintablePaintingFlags(int i) {
        this.nonPrintableOptions = i;
        invalidate();
    }

    public boolean hasComposingText() {
        return this.inputConnection.composingText.isComposing();
    }

    public void ensureSelectionVisible() {
        ensurePositionVisible(getCursor().getRightLine(), getCursor().getRightColumn());
    }

    public void ensurePositionVisible(int i, int i2) {
        ensurePositionVisible(i, i2, false);
    }

    public void ensurePositionVisible(int i, int i2, boolean z) {
        float width;
        EditorScroller scroller = getScroller();
        float[] charLayoutOffset = this.layout.getCharLayoutOffset(i, i2);
        float fMeasureTextRegionOffset = charLayoutOffset[1] + measureTextRegionOffset();
        float f = charLayoutOffset[0];
        float offsetY = scroller.isFinished() ? getOffsetY() : scroller.getFinalY();
        float offsetX = scroller.isFinished() ? getOffsetX() : scroller.getFinalX();
        int i3 = this.props.stickyScroll ? this.props.stickyScrollMaxLines : 2;
        float rowHeight = f - ((float) (getRowHeight() * i3)) < offsetY ? f - (getRowHeight() * i3) : offsetY;
        if (f > getHeight() + offsetY) {
            rowHeight = (f - getHeight()) + (getRowHeight() * 1.0f);
        }
        float fMeasureText = i2 == 0 ? 0.0f : this.renderer.measureText(this.text.getLine(i), i, i2 - 1, 1);
        if (fMeasureTextRegionOffset < (this.pinLineNumber ? measureTextRegionOffset() : 0.0f) + offsetX) {
            int width2 = getWidth() / 2;
            width = ((this.pinLineNumber ? -measureTextRegionOffset() : 0.0f) + fMeasureTextRegionOffset) - fMeasureText;
            float f2 = width2;
            if (Math.abs(width - offsetX) < f2) {
                width = Math.max(1.0f, offsetX - f2);
            }
        } else {
            width = offsetX;
        }
        if (fMeasureTextRegionOffset + fMeasureText > offsetX + getWidth()) {
            width = (fMeasureTextRegionOffset + (fMeasureText * 0.8f)) - getWidth();
        }
        float fMax = Math.max(0.0f, Math.min(getScrollMaxX(), width));
        float fMax2 = Math.max(0.0f, Math.min(getScrollMaxY(), rowHeight));
        if (Floats.withinDelta(fMax, getOffsetX(), 1.0f) && Floats.withinDelta(fMax2, getOffsetY(), 1.0f)) {
            invalidate();
            return;
        }
        boolean z2 = System.currentTimeMillis() - this.lastMakeVisible >= 100;
        this.lastMakeVisible = System.currentTimeMillis();
        if (z2 && !z) {
            scroller.forceFinished(true);
            scroller.startScroll(getOffsetX(), getOffsetY(), (int) (fMax - getOffsetX()), (int) (fMax2 - getOffsetY()));
            if (this.props.awareScrollbarWhenAdjust && Math.abs(getOffsetY() - fMax2) > this.dpUnit * 100.0f) {
                this.touchHandler.notifyScrolled();
            }
        } else {
            scroller.startScroll(getOffsetX(), getOffsetY(), (int) (fMax - getOffsetX()), (int) (fMax2 - getOffsetY()), 0);
            scroller.abortAnimation();
        }
        dispatchEvent(new ScrollEvent(this, getOffsetX(), getOffsetY(), (int) fMax, (int) fMax2, 3));
        invalidate();
    }

    public boolean hasClip() {
        return this.clipboardManager.hasPrimaryClip();
    }

    public float getDpUnit() {
        return this.dpUnit;
    }

    public EditorScroller getScroller() {
        return this.touchHandler.getScroller();
    }

    public boolean isOverMaxY(float f) {
        return f + ((float) getOffsetY()) > ((float) this.layout.getLayoutHeight());
    }

    public boolean isScreenPointOnText(float f, float f2) {
        long pointPositionOnScreen = getPointPositionOnScreen(f, f2);
        Row rowAt = this.layout.getRowAt(this.layout.getRowIndexForPosition(this.text.getCharIndex(IntPair.getFirst(pointPositionOnScreen), IntPair.getSecond(pointPositionOnScreen))));
        float fMeasureText = this.renderer.measureText(this.text.getLine(rowAt.lineIndex), rowAt.lineIndex, rowAt.startColumn, rowAt.endColumn - rowAt.startColumn);
        float fMeasureTextRegionOffset = measureTextRegionOffset();
        float f3 = fMeasureText + fMeasureTextRegionOffset;
        float offsetX = getOffsetX() + f;
        return offsetX >= fMeasureTextRegionOffset && offsetX <= f3;
    }

    public long getPointPosition(float f, float f2) {
        return this.layout.getCharPositionForLayoutOffset(f - measureTextRegionOffset(), f2);
    }

    public long getPointPositionOnScreen(float f, float f2) {
        float fMax = Math.max(0.0f, f2);
        List<CodeBlock> list = this.renderer.lastStuckLines;
        if (list != null && fMax < list.size() * getRowHeight()) {
            return getPointPosition(f, this.layout.getCharLayoutOffset(list.get((int) (fMax / getRowHeight())).startLine, 0)[0] - (getRowHeight() / 2.0f));
        }
        return getPointPosition(f + getOffsetX(), fMax + getOffsetY());
    }

    public int getScrollMaxY() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        return Math.max(0, this.layout.getLayoutHeight() - ((int) ((layoutParams == null || layoutParams.height == -2) ? getHeight() : getHeight() * (1.0f - this.verticalExtraSpaceFactor))));
    }

    public int getScrollMaxX() {
        return (int) Math.max(0.0f, (this.layout.getLayoutWidth() + measureTextRegionOffset()) - (getWidth() / 2.0f));
    }

    public void setVerticalExtraSpaceFactor(float f) {
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("the factor should be in range [0.0, 1.0]");
        }
        this.verticalExtraSpaceFactor = f;
        this.touchHandler.scrollBy(0.0f, 0.0f);
    }

    public float getVerticalExtraSpaceFactor() {
        return this.verticalExtraSpaceFactor;
    }

    public EditorSearcher getSearcher() {
        return this.editorSearcher;
    }

    public void setBasicDisplayMode(boolean z) {
        this.text.setBidiEnabled(!z);
        this.renderer.invalidateRenderNodes();
        this.renderer.basicDisplayMode = z;
        this.renderer.updateTimestamp();
        invalidate();
    }

    public boolean isBasicDisplayMode() {
        return this.renderer.isBasicDisplayMode();
    }

    protected void setSelectionAround(int i, int i2) {
        if (i < getLineCount()) {
            int columnCount = this.text.getColumnCount(i);
            if (i2 > columnCount) {
                i2 = columnCount;
            }
            setSelection(i, i2);
            return;
        }
        setSelection(getLineCount() - 1, this.text.getColumnCount(getLineCount() - 1));
    }

    public synchronized boolean formatCodeAsync() {
        if (isFormatting()) {
            return false;
        }
        Formatter formatter = this.editorLanguage.getFormatter();
        formatter.setReceiver(this);
        Content contentCopyText = this.text.copyText(false);
        contentCopyText.setUndoEnabled(false);
        formatter.format(contentCopyText, getCursorRange());
        postInvalidate();
        return true;
    }

    public synchronized boolean formatCodeAsync(CharPosition charPosition, CharPosition charPosition2) {
        if (charPosition.index > charPosition2.index) {
            throw new IllegalArgumentException("start > end");
        }
        if (isFormatting()) {
            return false;
        }
        Formatter formatter = this.editorLanguage.getFormatter();
        formatter.setReceiver(this);
        Content contentCopyText = this.text.copyText(false);
        contentCopyText.setUndoEnabled(false);
        formatter.formatRegion(contentCopyText, new TextRange(charPosition, charPosition2), getCursorRange());
        postInvalidate();
        return true;
    }

    public TextRange getCursorRange() {
        return this.cursor.getRange();
    }

    public boolean isTextSelected() {
        return this.cursor.isSelected();
    }

    public int getTabWidth() {
        return this.tabWidth;
    }

    public void setTabWidth(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("width can not be under 1");
        }
        this.tabWidth = i;
        this.renderer.invalidateRenderNodes();
        this.renderer.updateTimestamp();
        requestLayoutIfNeeded();
        invalidate();
    }

    public void setScaleTextSizes(float f, float f2) {
        if (f > f2) {
            throw new IllegalArgumentException("min size can not be bigger than max size");
        }
        if (f < 2.0f) {
            throw new IllegalArgumentException("min size must be at least 2px");
        }
        this.touchHandler.scaleMinSize = f;
        this.touchHandler.scaleMaxSize = f2;
    }

    public boolean isInterceptParentHorizontalScrollEnabled() {
        return this.forceHorizontalScrollable;
    }

    public void setInterceptParentHorizontalScrollIfNeeded(boolean z) {
        ViewParent parent;
        this.forceHorizontalScrollable = z;
        if (z || (parent = getParent()) == null) {
            return;
        }
        parent.requestDisallowInterceptTouchEvent(false);
    }

    public boolean isHighlightBracketPair() {
        return this.highlightBracketPair;
    }

    public void setHighlightBracketPair(boolean z) {
        this.highlightBracketPair = z;
        if (!z) {
            this.styleDelegate.clearFoundBracketPair();
        } else {
            this.styleDelegate.postUpdateBracketPair();
        }
        invalidate();
    }

    public void setLineSeparator(LineSeparator lineSeparator) {
        if (Objects.requireNonNull(lineSeparator) == LineSeparator.NONE) {
            throw new IllegalArgumentException();
        }
        this.lineSeparator = lineSeparator;
    }

    public LineSeparator getLineSeparator() {
        return this.lineSeparator;
    }

    public int getInputType() {
        return this.inputType;
    }

    public void setInputType(int i) {
        this.inputType = i;
        restartInput();
    }

    public void undo() {
        TextRange textRangeUndo = this.text.undo();
        if (textRangeUndo != null) {
            try {
                setSelectionRegion(textRangeUndo.getStart().line, textRangeUndo.getStart().column, textRangeUndo.getEnd().line, textRangeUndo.getEnd().column, true, 1);
            } catch (IndexOutOfBoundsException unused) {
            }
        }
        notifyIMEExternalCursorChange();
    }

    public void redo() {
        this.text.redo();
        notifyIMEExternalCursorChange();
    }

    public boolean canUndo() {
        return this.text.canUndo();
    }

    public boolean canRedo() {
        return this.text.canRedo();
    }

    public boolean isUndoEnabled() {
        return this.undoEnabled;
    }

    public void setUndoEnabled(boolean z) {
        this.undoEnabled = z;
        Content content = this.text;
        if (content != null) {
            content.setUndoEnabled(z);
        }
    }

    public DiagnosticIndicatorStyle getDiagnosticIndicatorStyle() {
        return this.diagnosticStyle;
    }

    public void setDiagnosticIndicatorStyle(DiagnosticIndicatorStyle diagnosticIndicatorStyle) {
        this.diagnosticStyle = diagnosticIndicatorStyle;
        invalidate();
    }

    /* JADX INFO: renamed from: io.github.rosemoe.sora.widget.CodeEditor$1SearchActionMode, reason: invalid class name */
    class C1SearchActionMode implements ActionMode.Callback {
        @Override // android.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return true;
        }

        C1SearchActionMode() {
        }

        @Override // android.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            CodeEditor.this.startedActionMode = 1;
            menu.add(0, 0, 0, I18nConfig.getResourceId(R.string.sora_editor_next)).setShowAsActionFlags(1);
            menu.add(0, 1, 0, I18nConfig.getResourceId(R.string.sora_editor_last)).setShowAsActionFlags(0);
            menu.add(0, 2, 0, I18nConfig.getResourceId(R.string.sora_editor_replace)).setShowAsActionFlags(0);
            menu.add(0, 3, 0, I18nConfig.getResourceId(R.string.sora_editor_replaceAll)).setShowAsActionFlags(0);
            SearchView searchView = new SearchView(CodeEditor.this.getContext());
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() { // from class: io.github.rosemoe.sora.widget.CodeEditor.1SearchActionMode.1
                @Override // android.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextSubmit(String str) {
                    CodeEditor.this.getSearcher().gotoNext();
                    return false;
                }

                @Override // android.widget.SearchView.OnQueryTextListener
                public boolean onQueryTextChange(String str) {
                    if (str == null || str.isEmpty()) {
                        CodeEditor.this.getSearcher().stopSearch();
                        return false;
                    }
                    CodeEditor.this.getSearcher().search(str, new EditorSearcher.SearchOptions(false, false));
                    return false;
                }
            });
            actionMode.setCustomView(searchView);
            searchView.performClick();
            searchView.setQueryHint(I18nConfig.getString(CodeEditor.this.getContext(), R.string.sora_editor_text_to_search));
            searchView.setIconifiedByDefault(false);
            searchView.setIconified(false);
            return true;
        }

        @Override // android.view.ActionMode.Callback
        public boolean onActionItemClicked(final ActionMode actionMode, MenuItem menuItem) {
            if (!CodeEditor.this.getSearcher().hasQuery()) {
                return false;
            }
            int itemId = menuItem.getItemId();
            if (itemId != 0) {
                if (itemId == 1) {
                    CodeEditor.this.getSearcher().gotoPrevious();
                } else if (itemId == 2 || itemId == 3) {
                    final boolean z = menuItem.getItemId() == 3;
                    final EditText editText = new EditText(CodeEditor.this.getContext());
                    editText.setHint(I18nConfig.getResourceId(R.string.sora_editor_replacement));
                    new AlertDialog.Builder(CodeEditor.this.getContext()).setTitle(I18nConfig.getResourceId(z ? R.string.sora_editor_replaceAll : R.string.sora_editor_replace)).setView(editText).setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null).setPositiveButton(I18nConfig.getResourceId(R.string.sora_editor_replace), new DialogInterface.OnClickListener() { // from class: io.github.rosemoe.sora.widget.CodeEditor$1SearchActionMode$$ExternalSyntheticLambda1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            this.f$0.lambda$onActionItemClicked$0(z, editText, actionMode, dialogInterface, i);
                        }
                    }).show();
                }
            } else {
                CodeEditor.this.getSearcher().gotoNext();
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onActionItemClicked$0(boolean z, EditText editText, final ActionMode actionMode, DialogInterface dialogInterface, int i) {
            if (z) {
                EditorSearcher searcher = CodeEditor.this.getSearcher();
                String string = editText.getText().toString();
                Objects.requireNonNull(actionMode);
                searcher.replaceAll(string, new Runnable() { // from class: io.github.rosemoe.sora.widget.CodeEditor$1SearchActionMode$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        actionMode.finish();
                    }
                });
            } else {
                CodeEditor.this.getSearcher().replaceThis(editText.getText().toString());
                actionMode.finish();
            }
            dialogInterface.dismiss();
        }

        @Override // android.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            CodeEditor.this.startedActionMode = 0;
            CodeEditor.this.getSearcher().stopSearch();
        }
    }

    public void beginSearchMode() {
        startActionMode(new C1SearchActionMode());
    }

    public EditorTouchEventHandler getEventHandler() {
        return this.touchHandler;
    }

    public float getDividerMarginLeft() {
        return this.dividerMarginLeft;
    }

    public float getDividerMarginRight() {
        return this.dividerMarginRight;
    }

    public void setDividerMargin(float f, float f2) {
        if (f < 0.0f || f2 < 0.0f) {
            throw new IllegalArgumentException("margin can not be under zero");
        }
        this.dividerMarginLeft = f;
        this.dividerMarginRight = f2;
        requestLayoutIfNeeded();
        invalidate();
    }

    public void setDividerMargin(float f) {
        setDividerMargin(f, f);
    }

    public void setLineNumberMarginLeft(float f) {
        this.lineNumberMarginLeft = f;
        requestLayoutIfNeeded();
        invalidate();
    }

    public float getLineNumberMarginLeft() {
        return this.lineNumberMarginLeft;
    }

    public float getDividerWidth() {
        return this.dividerWidth;
    }

    public void setDividerWidth(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("width can not be under zero");
        }
        this.dividerWidth = f;
        requestLayoutIfNeeded();
        invalidate();
    }

    public Typeface getTypefaceLineNumber() {
        return this.renderer.getPaintOther().getTypeface();
    }

    public void setTypefaceLineNumber(Typeface typeface) {
        this.renderer.setTypefaceLineNumber(typeface);
        requestLayoutIfNeeded();
    }

    public Typeface getTypefaceText() {
        return this.renderer.getPaint().getTypeface();
    }

    public void setTypefaceText(Typeface typeface) {
        this.renderer.setTypefaceText(typeface);
        requestLayoutIfNeeded();
    }

    public float getTextScaleX() {
        return this.renderer.getPaint().getTextScaleX();
    }

    public void setTextScaleX(float f) {
        this.renderer.setTextScaleX(f);
    }

    public float getTextLetterSpacing() {
        return this.renderer.getPaint().getLetterSpacing();
    }

    public void setTextLetterSpacing(float f) {
        this.renderer.setLetterSpacing(f);
        requestLayoutIfNeeded();
    }

    public Paint.Align getLineNumberAlign() {
        return this.lineNumberAlign;
    }

    public void setLineNumberAlign(Paint.Align align) {
        if (align == null) {
            align = Paint.Align.LEFT;
        }
        this.lineNumberAlign = align;
        invalidate();
    }

    public void setCursorWidth(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("width can not be under zero");
        }
        this.insertSelectionWidth = f;
        invalidate();
    }

    public float getInsertSelectionWidth() {
        return this.insertSelectionWidth;
    }

    public Cursor getCursor() {
        return this.cursor;
    }

    public int getLineCount() {
        return this.text.getLineCount();
    }

    public int getFirstVisibleLine() {
        try {
            return this.layout.getLineNumberForRow(getFirstVisibleRow());
        } catch (IndexOutOfBoundsException unused) {
            return 0;
        }
    }

    public int getFirstVisibleRow() {
        return Math.max(0, getOffsetY() / getRowHeight());
    }

    public int getLastVisibleRow() {
        return Math.max(0, Math.min(this.layout.getRowCount() - 1, (getOffsetY() + getHeight()) / getRowHeight()));
    }

    public int getLastVisibleLine() {
        try {
            return this.layout.getLineNumberForRow(getLastVisibleRow());
        } catch (IndexOutOfBoundsException unused) {
            return getLineCount() - 1;
        }
    }

    public boolean isRowVisible(int i) {
        return getFirstVisibleRow() <= i && i <= getLastVisibleRow();
    }

    public void setLineSpacing(float f, float f2) {
        this.lineSpacingAdd = f;
        this.lineSpacingMultiplier = f2;
    }

    public float getLineSpacingExtra() {
        return this.lineSpacingAdd;
    }

    public void setLineSpacingExtra(float f) {
        this.lineSpacingAdd = f;
        invalidate();
    }

    public float getLineSpacingMultiplier() {
        return this.lineSpacingMultiplier;
    }

    public void setLineSpacingMultiplier(float f) {
        this.lineSpacingMultiplier = f;
        invalidate();
    }

    public int getLineSpacingPixels() {
        Paint.FontMetricsInt fontMetricsInt = this.renderer.metricsText;
        return (((int) (((fontMetricsInt.descent - fontMetricsInt.ascent) * (this.lineSpacingMultiplier - 1.0f)) + this.lineSpacingAdd)) / 2) * 2;
    }

    public int getRowBaseline(int i) {
        int lineSpacingPixels = getLineSpacingPixels();
        Paint.FontMetricsInt fontMetricsInt = this.renderer.metricsText;
        return ((Math.max(1, (fontMetricsInt.descent - fontMetricsInt.ascent) + lineSpacingPixels) * (i + 1)) - fontMetricsInt.descent) - (lineSpacingPixels / 2);
    }

    public int getRowHeight() {
        Paint.FontMetricsInt fontMetricsInt = this.renderer.metricsText;
        return Math.max(1, (fontMetricsInt.descent - fontMetricsInt.ascent) + getLineSpacingPixels());
    }

    public int getRowTop(int i) {
        return getRowHeight() * i;
    }

    public int getRowBottom(int i) {
        return getRowHeight() * (i + 1);
    }

    public int getRowTopOfText(int i) {
        return getRowTop(i) + (getLineSpacingPixels() / 2);
    }

    public int getRowBottomOfText(int i) {
        return getRowBottom(i) - (getLineSpacingPixels() / 2);
    }

    public int getRowHeightOfText() {
        Paint.FontMetricsInt fontMetricsInt = this.renderer.metricsText;
        return fontMetricsInt.descent - fontMetricsInt.ascent;
    }

    public int getOffsetX() {
        return this.touchHandler.getScroller().getCurrX();
    }

    public int getOffsetY() {
        return this.touchHandler.getScroller().getCurrY();
    }

    public void setLayoutBusy(boolean z) {
        if (this.layoutBusy && !z && this.wordwrap && this.touchHandler.positionNotApplied) {
            this.touchHandler.positionNotApplied = false;
            float fFindRow = ((((WordwrapLayout) this.layout).findRow(IntPair.getFirst(this.touchHandler.memoryPosition), IntPair.getSecond(this.touchHandler.memoryPosition)) * getRowHeight()) - getHeight()) + this.touchHandler.focusY;
            EditorScroller scroller = this.touchHandler.getScroller();
            int i = (int) fFindRow;
            dispatchEvent(new ScrollEvent(this, scroller.getCurrX(), scroller.getCurrY(), 0, i, 5));
            scroller.startScroll(0, i, 0, 0, 0);
            scroller.abortAnimation();
            this.layoutBusy = false;
            restartInput();
            postInvalidate();
            return;
        }
        this.layoutBusy = z;
    }

    public boolean isEditable() {
        return (!this.editable || this.layoutBusy || isFormatting()) ? false : true;
    }

    public boolean getEditable() {
        return this.editable;
    }

    public void setEditable(boolean z) {
        this.editable = z;
        if (z) {
            return;
        }
        hideSoftInput();
        this.snippetController.stopSnippet();
    }

    public boolean isScalable() {
        return this.scalable;
    }

    public void setScalable(boolean z) {
        this.scalable = z;
    }

    public boolean isBlockLineEnabled() {
        return this.blockLineEnabled;
    }

    public void setBlockLineEnabled(boolean z) {
        this.blockLineEnabled = z;
        invalidate();
    }

    public void beginComposingTextRejection() {
        this.rejectComposingCount++;
    }

    public boolean acceptsComposingText() {
        return this.rejectComposingCount == 0;
    }

    public void endComposingTextRejection() {
        int i = this.rejectComposingCount - 1;
        this.rejectComposingCount = i;
        if (i < 0) {
            this.rejectComposingCount = 0;
        }
    }

    public boolean hasMouseHovering() {
        return this.mouseHover;
    }

    public boolean hasMousePressed() {
        return this.mouseButtonPressed;
    }

    public boolean isInMouseMode() {
        int i = this.props.mouseMode;
        if (i == 1) {
            return true;
        }
        if (i != 2) {
            return hasMouseHovering() || hasMousePressed();
        }
        return false;
    }

    protected CharPosition getSelectingTarget() {
        if (this.cursor.left().equals(this.selectionAnchor)) {
            return this.cursor.right();
        }
        return this.cursor.left();
    }

    protected void ensureSelectingTargetVisible() {
        if (this.cursor.left().equals(this.selectionAnchor)) {
            ensureSelectionVisible();
        } else {
            ensurePositionVisible(this.cursor.getLeftLine(), this.cursor.getLeftColumn());
        }
    }

    protected void ensureSelectionAnchorAvailable() {
        CharPosition charPosition = this.selectionAnchor;
        if (charPosition == null || !this.text.isValidPosition(charPosition)) {
            this.selectionAnchor = this.cursor.right();
        }
    }

    public void moveOrExtendSelection(SelectionMovement selectionMovement, boolean z) {
        if (z) {
            extendSelection(selectionMovement);
        } else {
            moveSelection(selectionMovement);
        }
    }

    public void extendSelection(SelectionMovement selectionMovement) {
        ensureSelectionAnchorAvailable();
        CharPosition positionAfterMovement = selectionMovement.getPositionAfterMovement(this, getSelectingTarget());
        setSelectionRegion(this.selectionAnchor.line, this.selectionAnchor.column, positionAfterMovement.line, positionAfterMovement.column, false, 7);
        if (selectionMovement == SelectionMovement.PAGE_UP) {
            this.touchHandler.scrollBy(0.0f, -getHeight(), true);
        } else if (selectionMovement == SelectionMovement.PAGE_DOWN) {
            this.touchHandler.scrollBy(0.0f, getHeight(), true);
        }
        ensureSelectingTargetVisible();
    }

    public void moveSelection(SelectionMovement selectionMovement) {
        CharPosition charPositionLeft;
        if (this.cursor.isSelected()) {
            if (selectionMovement == SelectionMovement.LEFT) {
                setSelection(this.cursor.getLeftLine(), this.cursor.getLeftColumn(), 7);
                return;
            } else if (selectionMovement == SelectionMovement.RIGHT) {
                setSelection(this.cursor.getRightLine(), this.cursor.getRightColumn(), 7);
                return;
            }
        }
        int i = AnonymousClass1.$SwitchMap$io$github$rosemoe$sora$widget$SelectionMovement$MovingBasePosition[selectionMovement.getBasePosition().ordinal()];
        if (i == 1) {
            charPositionLeft = this.cursor.left();
        } else if (i == 2) {
            charPositionLeft = this.cursor.right();
        } else {
            ensureSelectionAnchorAvailable();
            charPositionLeft = this.selectionAnchor;
        }
        CharPosition positionAfterMovement = selectionMovement.getPositionAfterMovement(this, charPositionLeft);
        if (selectionMovement == SelectionMovement.PAGE_UP) {
            this.touchHandler.scrollBy(0.0f, -getHeight(), true);
        } else if (selectionMovement == SelectionMovement.PAGE_DOWN) {
            this.touchHandler.scrollBy(0.0f, getHeight(), true);
        }
        setSelection(positionAfterMovement.line, positionAfterMovement.column, 7);
    }

    /* JADX INFO: renamed from: io.github.rosemoe.sora.widget.CodeEditor$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$github$rosemoe$sora$widget$SelectionMovement$MovingBasePosition;

        static {
            int[] iArr = new int[SelectionMovement.MovingBasePosition.values().length];
            $SwitchMap$io$github$rosemoe$sora$widget$SelectionMovement$MovingBasePosition = iArr;
            try {
                iArr[SelectionMovement.MovingBasePosition.LEFT_SELECTION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$github$rosemoe$sora$widget$SelectionMovement$MovingBasePosition[SelectionMovement.MovingBasePosition.RIGHT_SELECTION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public void setSelection(int i, int i2) {
        setSelection(i, i2, 0);
    }

    public void setSelection(int i, int i2, int i3) {
        setSelection(i, i2, true, i3);
    }

    public void setSelection(int i, int i2, boolean z) {
        setSelection(i, i2, z, 0);
    }

    public void setSelection(int i, int i2, boolean z, int i3) {
        int i4;
        this.cursorAnimator.markStartPos();
        if (i2 > 0 && Character.isHighSurrogate(this.text.charAt(i, i2 - 1)) && (i4 = i2 + 1) <= this.text.getColumnCount(i)) {
            i2 = i4;
        }
        this.cursor.set(i, i2);
        if (this.highlightCurrentBlock) {
            this.cursorPosition = findCursorBlock();
        }
        updateCursor();
        updateSelection();
        if (this.editable && !this.touchHandler.hasAnyHeldHandle() && acceptsComposingText()) {
            this.cursorAnimator.markEndPos();
            this.cursorAnimator.start();
            Log.d(LOG_TAG, "Start cursor animation");
        }
        Log.d(LOG_TAG, "Held = " + this.touchHandler.hasAnyHeldHandle() + " rejCnt = " + this.rejectComposingCount);
        this.selectionAnchor = this.cursor.right();
        this.renderer.invalidateRenderNodes();
        if (z) {
            ensurePositionVisible(i, i2);
        } else {
            invalidate();
        }
        onSelectionChanged(i3);
    }

    public void selectAll() {
        setSelectionRegion(0, 0, getLineCount() - 1, getText().getColumnCount(getLineCount() - 1));
    }

    public void setSelectionRegion(int i, int i2, int i3, int i4, int i5) {
        setSelectionRegion(i, i2, i3, i4, true, i5);
    }

    public void setSelectionRegion(int i, int i2, int i3, int i4) {
        setSelectionRegion(i, i2, i3, i4, true, 0);
    }

    public void setSelectionRegion(int i, int i2, int i3, int i4, boolean z) {
        setSelectionRegion(i, i2, i3, i4, z, 0);
    }

    public void setSelectionRegion(int i, int i2, int i3, int i4, boolean z, int i5) {
        int i6;
        int i7;
        requestFocus();
        int charIndex = getText().getCharIndex(i, i2);
        int charIndex2 = getText().getCharIndex(i3, i4);
        if (charIndex == charIndex2) {
            setSelection(i, i2, z, i5);
            return;
        }
        if (charIndex > charIndex2) {
            setSelectionRegion(i3, i4, i, i2, z, i5);
            Log.w(LOG_TAG, "setSelectionRegion() error: start > end:start = " + charIndex + " end = " + charIndex2 + " lineLeft = " + i + " columnLeft = " + i2 + " lineRight = " + i3 + " columnRight = " + i4);
            return;
        }
        this.cursorAnimator.cancel();
        this.cursor.isSelected();
        if (i2 > 0) {
            if (Character.isHighSurrogate(this.text.charAt(i, i2 - 1)) && (i7 = i2 + 1) <= this.text.getColumnCount(i)) {
                i2 = i7;
            }
        }
        if (i4 > 0) {
            if (Character.isHighSurrogate(this.text.charAt(i3, i4 - 1)) && (i6 = i4 + 1) <= this.text.getColumnCount(i3)) {
                i4 = i6;
            }
        }
        this.cursor.setLeft(i, i2);
        this.cursor.setRight(i3, i4);
        updateCursor();
        updateSelection();
        this.renderer.invalidateRenderNodes();
        if (!this.cursor.left().equals(this.selectionAnchor) && !this.cursor.right().equals(this.selectionAnchor)) {
            this.selectionAnchor = this.cursor.right();
        }
        if (!z) {
            invalidate();
        } else if (i5 == 6) {
            ensurePositionVisible(i, i2);
            this.lastMakeVisible = 0L;
            ensurePositionVisible(i3, i4);
        } else {
            ensurePositionVisible(i3, i4);
        }
        onSelectionChanged(i5);
    }

    public ClipboardManager getClipboardManager() {
        return this.clipboardManager;
    }

    public void pasteText() {
        ClipData primaryClip;
        try {
            if (this.clipboardManager.hasPrimaryClip() && (primaryClip = this.clipboardManager.getPrimaryClip()) != null) {
                pasteText(ClipDataUtils.clipDataToString(primaryClip));
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Error pasting text to editor", e);
            Toast.makeText(getContext(), e.toString(), 0).show();
        }
    }

    public void pasteText(CharSequence charSequence) {
        EditorInputConnection editorInputConnection;
        if (charSequence == null || (editorInputConnection = this.inputConnection) == null) {
            return;
        }
        editorInputConnection.commitText(charSequence, 1);
        if (this.props.formatPastedText) {
            formatCodeAsync(this.lastInsertion.getStart(), this.lastInsertion.getEnd());
        }
        notifyIMEExternalCursorChange();
    }

    public void copyText() {
        copyText(true);
    }

    public void copyText(boolean z) {
        if (this.cursor.isSelected()) {
            copyTextToClipboard(getText(), this.cursor.getLeft(), this.cursor.getRight());
        } else if (z) {
            copyLine();
        } else {
            String content = getLineSeparator().getContent();
            copyTextToClipboard(content, 0, content.length());
        }
    }

    protected void copyTextToClipboard(CharSequence charSequence, int i, int i2) {
        if (i2 < i) {
            return;
        }
        if (i2 - i > this.props.clipboardTextLengthLimit) {
            Toast.makeText(getContext(), I18nConfig.getResourceId(R.string.sora_editor_clip_text_length_too_large), 0).show();
            return;
        }
        try {
            String strSubstring = charSequence instanceof Content ? ((Content) charSequence).substring(i, i2) : charSequence.subSequence(i, i2).toString();
            this.clipboardManager.setPrimaryClip(ClipData.newPlainText(strSubstring, strSubstring));
        } catch (RuntimeException e) {
            if (e.getCause() instanceof TransactionTooLargeException) {
                Toast.makeText(getContext(), I18nConfig.getResourceId(R.string.sora_editor_clip_text_length_too_large), 0).show();
            } else {
                Log.w(LOG_TAG, e);
                Toast.makeText(getContext(), e.getClass().toString(), 0).show();
            }
        }
    }

    private void copyLine() {
        Cursor cursor = getCursor();
        if (cursor.isSelected()) {
            copyText();
            return;
        }
        int i = cursor.left().line;
        setSelectionRegion(i, 0, i, getText().getColumnCount(i));
        copyText(false);
    }

    public void cutText() {
        if (this.cursor.isSelected()) {
            copyText();
            deleteText();
            notifyIMEExternalCursorChange();
            return;
        }
        cutLine();
    }

    public void cutLine() {
        Cursor cursor = getCursor();
        if (cursor.isSelected()) {
            cutText();
            return;
        }
        CharPosition charPositionLeft = cursor.left();
        int i = charPositionLeft.line;
        getText().getColumnCount(charPositionLeft.line);
        int i2 = i + 1;
        if (i2 == getLineCount()) {
            if (getText().getColumnCount(i) == 0) {
                copyText(false);
                return;
            }
            setSelectionRegion(i, 0, i, getText().getColumnCount(i));
        } else {
            setSelectionRegion(i, 0, i2, 0);
        }
        cutText();
        if (this.props.placeSelOnPreviousLineAfterCut) {
            moveSelection(SelectionMovement.LEFT);
        }
    }

    public void duplicateLine() {
        Cursor cursor = getCursor();
        if (cursor.isSelected()) {
            duplicateSelection();
            return;
        }
        CharPosition charPositionLeft = cursor.left();
        setSelectionRegion(charPositionLeft.line, 0, charPositionLeft.line, getText().getColumnCount(charPositionLeft.line), true);
        duplicateSelection("\n", false);
    }

    public void duplicateSelection() {
        duplicateSelection(true);
    }

    public void duplicateSelection(boolean z) {
        duplicateSelection("", z);
    }

    public void duplicateSelection(String str, boolean z) {
        Cursor cursor = getCursor();
        if (cursor.isSelected()) {
            CharPosition charPositionLeft = cursor.left();
            CharPosition charPositionFromThis = cursor.right().fromThis();
            Content contentSubContent = getText().subContent(charPositionLeft.line, charPositionLeft.column, charPositionFromThis.line, charPositionFromThis.column);
            setSelection(charPositionFromThis.line, charPositionFromThis.column);
            commitText(str + ((Object) contentSubContent), false);
            if (z) {
                CharPosition charPositionRight = cursor.right();
                setSelectionRegion(charPositionFromThis.line, charPositionFromThis.column, charPositionRight.line, charPositionRight.column);
            }
        }
    }

    public void selectCurrentWord() {
        CharPosition charPositionLeft = getCursor().left();
        selectWord(charPositionLeft.line, charPositionLeft.column);
    }

    public void selectWord(int i, int i2) {
        TextRange wordRange = getWordRange(i, i2);
        CharPosition start = wordRange.getStart();
        CharPosition end = wordRange.getEnd();
        setSelectionRegion(start.line, start.column, end.line, end.column, 5);
    }

    public TextRange getWordRange(int i, int i2) {
        return getWordRange(i, i2, this.props.useICULibToSelectWords);
    }

    public TextRange getWordRange(int i, int i2, boolean z) {
        return Chars.getWordRange(getText(), i, i2, z);
    }

    public Content getText() {
        return this.text;
    }

    public void setText(CharSequence charSequence) {
        setText(charSequence, true, null);
    }

    public Bundle getExtraArguments() {
        return this.extraArguments;
    }

    public void setText(CharSequence charSequence, Bundle bundle) {
        setText(charSequence, true, bundle);
    }

    public void setText(CharSequence charSequence, boolean z, Bundle bundle) {
        if (charSequence == null) {
            charSequence = "";
        }
        Content content = this.text;
        if (content != null) {
            content.removeContentListener(this);
            this.text.setLineListener(null);
            this.text.resetBatchEdit();
        }
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.extraArguments = bundle;
        this.lastInsertion = null;
        if (z && (charSequence instanceof Content)) {
            Content content2 = (Content) charSequence;
            this.text = content2;
            content2.resetBatchEdit();
            this.renderer.updateTimestamp();
        } else {
            this.text = new Content(charSequence);
        }
        this.text.setBidiEnabled(!this.renderer.basicDisplayMode);
        this.styleDelegate.reset();
        this.textStyles = null;
        Cursor cursor = this.text.getCursor();
        this.cursor = cursor;
        this.selectionAnchor = cursor.right();
        this.touchHandler.reset();
        this.text.addContentListener(this);
        this.text.setUndoEnabled(this.undoEnabled);
        this.text.setLineListener(this);
        this.renderer.onEditorFullTextUpdate();
        Language language = this.editorLanguage;
        if (language != null) {
            language.getAnalyzeManager().reset(new ContentReference(this.text), this.extraArguments);
            this.editorLanguage.getFormatter().cancel();
        }
        dispatchEvent(new ContentChangeEvent(this, 1, new CharPosition(), this.text.getIndexer().getCharPosition(getLineCount() - 1, this.text.getColumnCount(getLineCount() - 1)), this.text, false));
        InputMethodManager inputMethodManager = this.inputMethodManager;
        if (inputMethodManager != null) {
            inputMethodManager.restartInput(this);
        }
        createLayout();
        requestLayout();
        this.renderer.invalidateRenderNodes();
        invalidate();
    }

    public void setTextSize(float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        setTextSizePx(TypedValue.applyDimension(2, f, resources.getDisplayMetrics()));
    }

    public void setRenderFunctionCharacters(boolean z) {
        if (this.renderFunctionCharacters != z) {
            this.renderFunctionCharacters = z;
            this.renderer.onTextStyleUpdate();
            requestLayoutIfNeeded();
            createLayout();
            invalidate();
        }
    }

    public boolean isRenderFunctionCharacters() {
        return this.renderFunctionCharacters;
    }

    public <T extends Event> SubscriptionReceipt<T> subscribeEvent(Class<T> cls, EventReceiver<T> eventReceiver) {
        return this.eventManager.subscribeEvent(cls, eventReceiver);
    }

    public <T extends Event> int dispatchEvent(T t) {
        return this.eventManager.dispatchEvent(t);
    }

    public EventManager createSubEventManager() {
        return new EventManager(this.eventManager);
    }

    public boolean isFormatting() {
        return this.editorLanguage.getFormatter().isRunning();
    }

    public boolean isLineNumberEnabled() {
        return this.lineNumberEnabled;
    }

    public void setLineNumberEnabled(boolean z) {
        if (z != this.lineNumberEnabled && isWordwrap()) {
            createLayout();
        }
        this.lineNumberEnabled = z;
        invalidate();
    }

    public io.github.rosemoe.sora.graphics.Paint getTextPaint() {
        return this.renderer.getPaint();
    }

    public io.github.rosemoe.sora.graphics.Paint getOtherPaint() {
        return this.renderer.getPaintOther();
    }

    public io.github.rosemoe.sora.graphics.Paint getGraphPaint() {
        return this.renderer.getPaintGraph();
    }

    public EditorColorScheme getColorScheme() {
        return this.colorScheme;
    }

    public void setColorScheme(EditorColorScheme editorColorScheme) {
        EditorColorScheme editorColorScheme2 = this.colorScheme;
        if (editorColorScheme2 != null) {
            editorColorScheme2.detachEditor(this);
        }
        this.colorScheme = editorColorScheme;
        editorColorScheme.attachEditor(this);
        invalidate();
    }

    public void jumpToLine(int i) {
        setSelection(i, 0);
    }

    public void beginLongSelect() {
        if (isEditable()) {
            if (this.cursor.isSelected()) {
                setSelection(this.cursor.getLeftLine(), this.cursor.getLeftColumn());
            }
            this.isInLongSelect = true;
            invalidate();
        }
    }

    public boolean isInLongSelect() {
        return this.isInLongSelect;
    }

    public void endLongSelect() {
        this.isInLongSelect = false;
    }

    public void rerunAnalysis() {
        Language language = this.editorLanguage;
        if (language != null) {
            language.getAnalyzeManager().rerun();
        }
    }

    public Styles getStyles() {
        return this.textStyles;
    }

    public void setStyles(Styles styles) {
        this.textStyles = styles;
        if (this.highlightCurrentBlock) {
            this.cursorPosition = findCursorBlock();
        }
        this.renderer.invalidateRenderNodes();
        this.renderer.updateTimestamp();
        invalidate();
    }

    public void updateStyles(Styles styles, StyleUpdateRange styleUpdateRange) {
        if (this.textStyles != styles || styleUpdateRange == null) {
            setStyles(styles);
            return;
        }
        if (this.highlightCurrentBlock) {
            this.cursorPosition = findCursorBlock();
        }
        this.renderer.invalidateInRegion(styleUpdateRange);
        this.renderer.updateTimestamp();
        invalidate();
    }

    public DiagnosticsContainer getDiagnostics() {
        return this.diagnostics;
    }

    public void setDiagnostics(DiagnosticsContainer diagnosticsContainer) {
        this.diagnostics = diagnosticsContainer;
        invalidate();
    }

    public void hideAutoCompleteWindow() {
        EditorAutoCompletion editorAutoCompletion = this.completionWindow;
        if (editorAutoCompletion != null) {
            editorAutoCompletion.hide();
        }
    }

    public int getBlockIndex() {
        return this.cursorPosition;
    }

    public void showSoftInput() {
        if (isEditable() && isEnabled()) {
            if (isInTouchMode() && !isFocused()) {
                requestFocusFromTouch();
            }
            if (!isFocused()) {
                requestFocus();
            }
            if (checkSoftInputEnabled()) {
                this.inputMethodManager.showSoftInput(this, 0);
            }
        }
        invalidate();
    }

    public void hideSoftInput() {
        this.inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
    }

    protected boolean checkSoftInputEnabled() {
        if (isDisableSoftKbdIfHardKbdAvailable() && KeyboardUtils.INSTANCE.isHardKeyboardConnected(getContext())) {
            return false;
        }
        return isSoftKeyboardEnabled();
    }

    public void setSoftKeyboardEnabled(boolean z) {
        if (isSoftKeyboardEnabled() == z) {
            return;
        }
        this.isSoftKbdEnabled = z;
        hideSoftInput();
        restartInput();
    }

    public boolean isSoftKeyboardEnabled() {
        return this.isSoftKbdEnabled;
    }

    public void setDisableSoftKbdIfHardKbdAvailable(boolean z) {
        if (isDisableSoftKbdIfHardKbdAvailable() == z) {
            return;
        }
        this.isDisableSoftKbdOnHardKbd = z;
        hideSoftInput();
        restartInput();
    }

    public boolean isDisableSoftKbdIfHardKbdAvailable() {
        return this.isDisableSoftKbdOnHardKbd;
    }

    protected void updateSelection() {
        int i;
        int i2;
        int i3;
        if (this.props.disallowSuggestions) {
            int iNextInt = new Random().nextInt();
            this.inputMethodManager.updateSelection(this, iNextInt, iNextInt, -1, -1);
            return;
        }
        int i4 = -1;
        if (this.inputConnection.composingText.isComposing()) {
            try {
                i = this.inputConnection.composingText.startIndex;
                try {
                    i4 = this.inputConnection.composingText.endIndex;
                } catch (IndexOutOfBoundsException unused) {
                }
            } catch (IndexOutOfBoundsException unused2) {
                i = -1;
            }
            i2 = i;
            i3 = i4;
        } else {
            i2 = -1;
            i3 = -1;
        }
        this.inputMethodManager.updateSelection(this, this.cursor.getLeft(), this.cursor.getRight(), i2, i3);
    }

    protected void updateExtractedText() {
        ExtractedTextRequest extractedTextRequest = this.extractingTextRequest;
        if (extractedTextRequest != null) {
            this.inputMethodManager.updateExtractedText(this, this.extractingTextRequest.token, extractText(extractedTextRequest));
        }
    }

    protected void setExtracting(ExtractedTextRequest extractedTextRequest) {
        if (getProps().disallowSuggestions) {
            this.extractingTextRequest = null;
        } else {
            this.extractingTextRequest = extractedTextRequest;
        }
    }

    protected ExtractedText extractText(ExtractedTextRequest extractedTextRequest) {
        if (getProps().disallowSuggestions) {
            return null;
        }
        Cursor cursor = getCursor();
        ExtractedText extractedText = new ExtractedText();
        int left = cursor.getLeft();
        int right = cursor.getRight();
        if (extractedTextRequest.hintMaxChars == 0) {
            extractedTextRequest.hintMaxChars = this.props.maxIPCTextLength;
        }
        int iMin = extractedTextRequest.hintMaxChars < left ? Math.min(left - (extractedTextRequest.hintMaxChars / 2), left) : 0;
        extractedText.text = this.inputConnection.getTextRegion(iMin, extractedTextRequest.hintMaxChars + iMin, extractedTextRequest.flags);
        extractedText.startOffset = iMin;
        extractedText.selectionStart = left - iMin;
        extractedText.selectionEnd = right - iMin;
        if (left != right) {
            extractedText.flags |= 2;
        }
        return extractedText;
    }

    public void notifyIMEExternalCursorChange() {
        updateExtractedText();
        updateSelection();
        updateCursorAnchor();
        if (this.inputConnection.composingText.isComposing()) {
            restartInput();
        }
    }

    public void restartInput() {
        EditorInputConnection editorInputConnection = this.inputConnection;
        if (editorInputConnection != null) {
            editorInputConnection.reset();
        }
        InputMethodManager inputMethodManager = this.inputMethodManager;
        if (inputMethodManager != null) {
            inputMethodManager.restartInput(this);
        }
    }

    public void updateCursor() {
        updateCursorAnchor();
        updateExtractedText();
        if (this.text.getNestedBatchEdit() <= 1 || this.inputConnection.composingText.isComposing()) {
            return;
        }
        updateSelection();
    }

    public void release() {
        hideEditorWindows();
        if (this.released) {
            return;
        }
        dispatchEvent(new EditorReleaseEvent(this));
        this.released = true;
        Language language = this.editorLanguage;
        if (language != null) {
            language.getAnalyzeManager().destroy();
            Formatter formatter = this.editorLanguage.getFormatter();
            formatter.setReceiver(null);
            formatter.destroy();
            this.editorLanguage.destroy();
            this.editorLanguage = new EmptyLanguage();
        }
        this.textStyles = null;
        this.diagnostics = null;
        this.styleDelegate.reset();
        Content content = this.text;
        if (content != null) {
            content.removeContentListener(this);
            content.setLineListener(null);
        }
        this.colorScheme.detachEditor(this);
    }

    public boolean isReleased() {
        return this.released;
    }

    public void hideEditorWindows() {
        this.completionWindow.cancelCompletion();
        this.completionWindow.hide();
        this.textActionWindow.dismiss();
        this.touchHandler.magnifier.dismiss();
    }

    public void onColorUpdated(int i) {
        dispatchEvent(new ColorSchemeUpdateEvent(this));
        this.renderer.invalidateRenderNodes();
        invalidate();
    }

    public void onColorFullUpdate() {
        dispatchEvent(new ColorSchemeUpdateEvent(this));
        this.renderer.invalidateRenderNodes();
        invalidate();
    }

    protected InputMethodManager getInputMethodManager() {
        return this.inputMethodManager;
    }

    protected void onCloseConnection() {
        setExtracting(null);
        invalidate();
    }

    protected EditorRenderer onCreateRenderer() {
        return new EditorRenderer(this);
    }

    protected void onSelectionChanged(int i) {
        dispatchEvent(new SelectionChangeEvent(this, i));
    }

    protected void releaseEdgeEffects() {
        this.edgeEffectHorizontal.onRelease();
        this.edgeEffectVertical.onRelease();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.renderer.draw(canvas);
        if (!(this.lastCursorState == this.cursorBlink.visibility && this.touchHandler.getScroller().isFinished()) && this.touchHandler.magnifier.isShowing()) {
            this.lastCursorState = this.cursorBlink.visibility;
            final Magnifier magnifier = this.touchHandler.magnifier;
            Objects.requireNonNull(magnifier);
            postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.CodeEditor$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    magnifier.updateDisplay();
                }
            });
        }
    }

    @Override // android.view.View
    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo accessibilityNodeInfoCreateAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        if (isEnabled()) {
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setEditable(isEditable());
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setTextSelection(this.cursor.getLeft(), this.cursor.getRight());
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setInputType(1);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setMultiLine(true);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setText(getText().toStringBuilder());
            accessibilityNodeInfoCreateAccessibilityNodeInfo.setLongClickable(true);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COPY);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CUT);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PASTE);
            accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
            int scrollMaxY = getScrollMaxY();
            if (scrollMaxY > 0) {
                accessibilityNodeInfoCreateAccessibilityNodeInfo.setScrollable(true);
                int offsetY = getOffsetY();
                if (offsetY > 0) {
                    accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                    accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                }
                if (offsetY < scrollMaxY) {
                    accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                    accessibilityNodeInfoCreateAccessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                }
            }
        }
        return accessibilityNodeInfoCreateAccessibilityNodeInfo;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        int scrollMaxY = getScrollMaxY();
        accessibilityEvent.setScrollable(scrollMaxY > 0);
        accessibilityEvent.setMaxScrollX(getScrollMaxX());
        accessibilityEvent.setMaxScrollY(scrollMaxY);
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i == 4096) {
            moveSelection(SelectionMovement.PAGE_UP);
            return true;
        }
        if (i == 8192) {
            moveSelection(SelectionMovement.PAGE_DOWN);
            return true;
        }
        if (i == 16384) {
            copyText();
            return true;
        }
        if (i == 32768) {
            pasteText();
            return true;
        }
        if (i == 65536) {
            cutText();
            return true;
        }
        if (i == 2097152) {
            setText(bundle.getCharSequence(AccessibilityNodeInfoCompat.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE));
            return true;
        }
        if (i == 16908344) {
            moveSelection(SelectionMovement.PAGE_DOWN);
            return true;
        }
        if (i == 16908346) {
            moveSelection(SelectionMovement.PAGE_UP);
            return true;
        }
        return super.performAccessibilityAction(i, bundle);
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return CodeEditor.class.getName();
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downX = x;
            if (this.forceHorizontalScrollable) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (action == 2) {
            int i = x - this.downX;
            if (this.forceHorizontalScrollable && !this.touchHandler.hasAnyHeldHandle() && ((i > 0 && getScroller().getCurrX() == 0) || (i < 0 && getScroller().getCurrX() == getScrollMaxX()))) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onCheckIsTextEditor() {
        return isEnabled() && isEditable();
    }

    @Override // android.view.View
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        if (!isEditable() || !isEnabled()) {
            return null;
        }
        if (checkSoftInputEnabled()) {
            int i = this.inputType;
            if (i == 0) {
                i = 131073;
            }
            editorInfo.inputType = i;
        } else {
            editorInfo.inputType = 0;
        }
        editorInfo.initialSelStart = getCursor() != null ? getCursor().getLeft() : 0;
        editorInfo.initialSelEnd = getCursor() != null ? getCursor().getRight() : 0;
        editorInfo.initialCapsMode = this.inputConnection.getCursorCapsMode(0);
        if (!this.props.allowFullscreen) {
            editorInfo.imeOptions = 301989888;
        }
        dispatchEvent(new BuildEditorInfoEvent(this, editorInfo));
        this.inputConnection.reset();
        this.text.resetBatchEdit();
        setExtracting(null);
        return this.inputConnection;
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        int i2;
        if (motionEvent.isFromSource(8194)) {
            if (isFormatting()) {
                return PointerIcon.getSystemIcon(getContext(), 1004);
            }
            if (this.touchHandler.hasAnyHeldHandle()) {
                return PointerIcon.getSystemIcon(getContext(), 1021);
            }
            if (getLeftHandleDescriptor().position.contains(motionEvent.getX(), motionEvent.getY()) || getRightHandleDescriptor().position.contains(motionEvent.getX(), motionEvent.getY()) || getInsertHandleDescriptor().position.contains(motionEvent.getX(), motionEvent.getY())) {
                return PointerIcon.getSystemIcon(getContext(), PointerIconCompat.TYPE_GRAB);
            }
            long jResolveTouchRegion = RegionResolverKt.resolveTouchRegion(this, motionEvent, i);
            int first = IntPair.getFirst(jResolveTouchRegion);
            boolean z = IntPair.getSecond(jResolveTouchRegion) == 0;
            if (first == 5 && z) {
                if (this.touchHandler.mouseCanMoveText && !this.touchHandler.mouseClick) {
                    return PointerIcon.getSystemIcon(getContext(), 1021);
                }
                return PointerIcon.getSystemIcon(getContext(), PointerIconCompat.TYPE_TEXT);
            }
            if (first == 1 && ((i2 = this.props.actionWhenLineNumberClicked) == 1 || i2 == 2)) {
                return PointerIcon.getSystemIcon(getContext(), 1002);
            }
            return super.onResolvePointerIcon(motionEvent, i);
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        if (motionEvent.isFromSource(8194) && this.props.mouseMode != 2) {
            return this.touchHandler.onMouseEvent(motionEvent);
        }
        if (isFormatting()) {
            this.touchHandler.reset2();
            this.scaleDetector.onTouchEvent(motionEvent);
            return this.basicDetector.onTouchEvent(motionEvent);
        }
        boolean zHandlingMotions = this.touchHandler.handlingMotions();
        boolean zOnTouchEvent = this.touchHandler.onTouchEvent(motionEvent);
        boolean zHandlingMotions2 = this.touchHandler.handlingMotions();
        boolean zOnTouchEvent2 = this.scaleDetector.onTouchEvent(motionEvent);
        boolean zOnTouchEvent3 = (zHandlingMotions2 || zHandlingMotions) ? false : this.basicDetector.onTouchEvent(motionEvent);
        if (motionEvent.getAction() == 1) {
            this.edgeEffectVertical.onRelease();
            this.edgeEffectHorizontal.onRelease();
        }
        return zOnTouchEvent2 || zOnTouchEvent3 || zOnTouchEvent;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return this.keyEventHandler.onKeyDown(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        return this.keyEventHandler.onKeyUp(i, keyEvent);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return this.keyEventHandler.onKeyMultiple(i, i2, keyEvent);
    }

    boolean onSuperKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    boolean onSuperKeyUp(int i, KeyEvent keyEvent) {
        return super.onKeyUp(i, keyEvent);
    }

    boolean onSuperKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        return super.onKeyMultiple(i, i2, keyEvent);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) != 1073741824 || View.MeasureSpec.getMode(i2) != 1073741824) {
            Log.w(LOG_TAG, "use wrap_content in editor may cause layout lags");
            long desiredSize = ViewMeasureHelper.getDesiredSize(i, i2, measureTextRegionOffset(), getRowHeight(), this.wordwrap, this.tabWidth, this.text, this.renderer.paintGeneral);
            int first = IntPair.getFirst(desiredSize);
            i2 = IntPair.getSecond(desiredSize);
            this.anyWrapContentSet = true;
            i = first;
        } else {
            this.anyWrapContentSet = false;
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public boolean onDragEvent(DragEvent dragEvent) {
        int action = dragEvent.getAction();
        if (action != 1) {
            if (action != 2) {
                if (action != 3) {
                    if (action == 6) {
                        this.touchHandler.draggingSelection = null;
                        postInvalidate();
                        return true;
                    }
                    return super.onDragEvent(dragEvent);
                }
                CharPosition charPosition = this.touchHandler.draggingSelection;
                if (charPosition == null) {
                    return false;
                }
                this.touchHandler.draggingSelection = null;
                setSelection(charPosition.line, charPosition.column);
                pasteText(ClipDataUtils.clipDataToString(dragEvent.getClipData()));
                requestFocus();
                postInvalidate();
                super.onDragEvent(dragEvent);
                return true;
            }
            long pointPositionOnScreen = getPointPositionOnScreen(dragEvent.getX(), dragEvent.getY());
            int first = IntPair.getFirst(pointPositionOnScreen);
            int second = IntPair.getSecond(pointPositionOnScreen);
            this.touchHandler.draggingSelection = getText().getIndexer().getCharPosition(first, second);
            postInvalidate();
            this.touchHandler.scrollIfReachesEdge(null, dragEvent.getX(), dragEvent.getY());
        }
        return true;
    }

    @Override // android.view.View
    protected void onCreateContextMenu(ContextMenu contextMenu) {
        super.onCreateContextMenu(contextMenu);
        PointF lastContextClickPosition = this.touchHandler.getLastContextClickPosition();
        if (lastContextClickPosition == null) {
            return;
        }
        long pointPositionOnScreen = getPointPositionOnScreen(lastContextClickPosition.x, lastContextClickPosition.y);
        dispatchEvent(new CreateContextMenuEvent(this, contextMenu, this.text.getIndexer().getCharPosition(IntPair.getFirst(pointPositionOnScreen), IntPair.getSecond(pointPositionOnScreen))));
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.touchHandler.resetMouse();
        this.mouseButtonPressed = false;
        this.mouseHover = false;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194)) {
            if (motionEvent.getAction() == 9) {
                this.mouseHover = true;
            } else if (motionEvent.getAction() == 10) {
                this.mouseHover = false;
            }
            if (motionEvent.getActionMasked() == 11 || motionEvent.getActionMasked() == 12) {
                this.mouseButtonPressed = motionEvent.getButtonState() != 0;
            }
            int action = motionEvent.getAction();
            if (action == 7 || action == 9 || action == 10) {
                this.touchHandler.dispatchEditorMotionEvent(new Function5() { // from class: io.github.rosemoe.sora.widget.CodeEditor$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function5
                    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                        return new HoverEvent((CodeEditor) obj, (CharPosition) obj2, (MotionEvent) obj3, (Span) obj4, (TextRange) obj5);
                    }
                }, null, motionEvent);
                return true;
            }
        }
        if (motionEvent.getAction() == 8 && motionEvent.isFromSource(2) && !this.keyEventHandler.getKeyMetaStates().isCtrlPressed()) {
            float f = -motionEvent.getAxisValue(9);
            float f2 = -motionEvent.getAxisValue(10);
            float f3 = this.verticalScrollFactor;
            float f4 = f2 * f3;
            float f5 = f * f3;
            if (this.keyEventHandler.getKeyMetaStates().isAltPressed()) {
                float f6 = this.props.fastScrollSensitivity;
                f4 *= f6;
                f5 *= f6;
            }
            if (!this.keyEventHandler.getKeyMetaStates().isShiftPressed()) {
                float f7 = f4;
                f4 = f5;
                f5 = f7;
            }
            this.touchHandler.onScroll(motionEvent, motionEvent, f5, f4);
            return true;
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.renderer.onSizeChanged(i, i2);
        getVerticalEdgeEffect().setSize(i, i2);
        getHorizontalEdgeEffect().setSize(i2, i);
        getVerticalEdgeEffect().finish();
        getHorizontalEdgeEffect().finish();
        if (this.layout == null || (isWordwrap() && i != i3)) {
            createLayout();
        } else {
            this.touchHandler.scrollBy(getOffsetX() > getScrollMaxX() ? getScrollMaxX() - getOffsetX() : 0.0f, getOffsetY() > getScrollMaxY() ? getScrollMaxY() - getOffsetY() : 0.0f);
        }
        this.verticalAbsorb = false;
        this.horizontalAbsorb = false;
        if (i4 <= i2 || !this.props.adjustToSelectionOnResize) {
            return;
        }
        ensureSelectionVisible();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dispatchEvent(new EditorAttachStateChangeEvent(this, false));
        this.cursorBlink.valid = false;
        removeCallbacks(this.cursorBlink);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        dispatchEvent(new EditorAttachStateChangeEvent(this, true));
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        if (z) {
            CursorBlink cursorBlink = this.cursorBlink;
            cursorBlink.valid = cursorBlink.period > 0;
            if (this.cursorBlink.valid) {
                postInLifecycle(this.cursorBlink);
            }
        } else {
            this.cursorBlink.valid = false;
            this.cursorBlink.visibility = false;
            this.touchHandler.hideInsertHandle();
            removeCallbacks(this.cursorBlink);
        }
        dispatchEvent(new EditorFocusChangeEvent(this, z));
        invalidate();
    }

    @Override // android.view.View
    public void computeScroll() {
        EditorScroller scroller = this.touchHandler.getScroller();
        if (scroller.computeScrollOffset()) {
            if (!scroller.isFinished() && (scroller.getStartX() != scroller.getFinalX() || scroller.getStartY() != scroller.getFinalY())) {
                this.scrollerFinalX = scroller.getFinalX();
                this.scrollerFinalY = scroller.getFinalY();
                this.horizontalAbsorb = ((float) Math.abs(scroller.getStartX() - scroller.getFinalX())) > getDpUnit() * 5.0f;
                this.verticalAbsorb = ((float) Math.abs(scroller.getStartY() - scroller.getFinalY())) > getDpUnit() * 5.0f;
            }
            if (scroller.getCurrX() <= 0 && this.scrollerFinalX <= 0.0f && this.edgeEffectHorizontal.isFinished() && this.horizontalAbsorb) {
                this.edgeEffectHorizontal.onAbsorb((int) scroller.getCurrVelocity());
                this.touchHandler.glowLeftOrRight = false;
            } else {
                int scrollMaxX = getScrollMaxX();
                if (scroller.getCurrX() >= scrollMaxX && this.scrollerFinalX >= scrollMaxX && this.edgeEffectHorizontal.isFinished() && this.horizontalAbsorb) {
                    this.edgeEffectHorizontal.onAbsorb((int) scroller.getCurrVelocity());
                    this.touchHandler.glowLeftOrRight = true;
                }
            }
            if (scroller.getCurrY() <= 0 && this.scrollerFinalY <= 0.0f && this.edgeEffectVertical.isFinished() && this.verticalAbsorb) {
                this.edgeEffectVertical.onAbsorb((int) scroller.getCurrVelocity());
                this.touchHandler.glowTopOrBottom = false;
            } else {
                int scrollMaxY = getScrollMaxY();
                if (scroller.getCurrY() >= scrollMaxY && this.scrollerFinalY >= scrollMaxY && this.edgeEffectVertical.isFinished() && this.verticalAbsorb) {
                    this.edgeEffectVertical.onAbsorb((int) scroller.getCurrVelocity());
                    this.touchHandler.glowTopOrBottom = true;
                }
            }
            postInvalidateOnAnimation();
        }
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        return getScrollMaxY();
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        return Math.max(0, Math.min(getScrollMaxY(), getOffsetY()));
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        return getScrollMaxX();
    }

    @Override // android.view.View
    protected int computeHorizontalScrollOffset() {
        return Math.max(0, Math.min(getScrollMaxX(), getOffsetX()));
    }

    @Override // android.view.View
    public boolean removeCallbacks(Runnable runnable) {
        EditorHandler.INSTANCE.removeCallbacks(runnable);
        return super.removeCallbacks(runnable);
    }

    public boolean postInLifecycle(final Runnable runnable) {
        return EditorHandler.INSTANCE.post(new Runnable() { // from class: io.github.rosemoe.sora.widget.CodeEditor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$postInLifecycle$0(runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postInLifecycle$0(Runnable runnable) {
        if (this.released) {
            return;
        }
        runnable.run();
    }

    public boolean postDelayedInLifecycle(final Runnable runnable, long j) {
        return EditorHandler.INSTANCE.postDelayed(new Runnable() { // from class: io.github.rosemoe.sora.widget.CodeEditor$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$postDelayedInLifecycle$1(runnable);
            }
        }, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postDelayedInLifecycle$1(Runnable runnable) {
        if (this.released) {
            return;
        }
        runnable.run();
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeReplace(Content content) {
        this.waitForNextChange = true;
        this.layout.beforeReplace(content);
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void afterInsert(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.renderer.updateTimestamp();
        this.styleDelegate.onTextChange();
        CharPosition charPosition = this.text.getIndexer().getCharPosition(i, i2);
        CharPosition charPosition2 = this.text.getIndexer().getCharPosition(i3, i4);
        this.renderer.buildMeasureCacheForLines(i, i3);
        try {
            Styles styles = this.textStyles;
            if (styles != null) {
                styles.adjustOnInsert(charPosition, charPosition2);
            }
            DiagnosticsContainer diagnosticsContainer = this.diagnostics;
            if (diagnosticsContainer != null) {
                diagnosticsContainer.shiftOnInsert(charPosition.index, charPosition2.index);
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Update failure", e);
        }
        this.layout.afterInsert(content, i, i2, i3, i4, charSequence);
        checkForRelayout();
        updateCursor();
        this.waitForNextChange = false;
        updateCursorAnchor();
        this.renderer.invalidateOnInsert(i, i3);
        ensureSelectionVisible();
        this.editorLanguage.getAnalyzeManager().insert(charPosition, charPosition2, charSequence);
        this.touchHandler.hideInsertHandle();
        if (this.editable && !this.cursor.isSelected() && !this.inputConnection.composingText.isComposing() && acceptsComposingText()) {
            this.cursorAnimator.markEndPos();
            this.cursorAnimator.start();
        }
        this.selectionAnchor = this.lastAnchorIsSelLeft ? this.cursor.left() : this.cursor.right();
        dispatchEvent(new ContentChangeEvent(this, 2, charPosition, charPosition2, charSequence, this.text.isUndoManagerWorking()));
        onSelectionChanged(1);
        this.lastInsertion = new TextRange(charPosition.fromThis(), charPosition2.fromThis());
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void afterDelete(Content content, int i, int i2, int i3, int i4, CharSequence charSequence) {
        this.renderer.updateTimestamp();
        this.styleDelegate.onTextChange();
        CharPosition charPosition = this.text.getIndexer().getCharPosition(i, i2);
        CharPosition charPositionFromThis = charPosition.fromThis();
        charPositionFromThis.column = i4;
        charPositionFromThis.line = i3;
        charPositionFromThis.index = charPosition.index + charSequence.length();
        this.renderer.buildMeasureCacheForLines(i, i + 1);
        try {
            Styles styles = this.textStyles;
            if (styles != null) {
                styles.adjustOnDelete(charPosition, charPositionFromThis);
            }
            DiagnosticsContainer diagnosticsContainer = this.diagnostics;
            if (diagnosticsContainer != null) {
                diagnosticsContainer.shiftOnDelete(charPosition.index, charPositionFromThis.index);
            }
        } catch (Exception e) {
            Log.w(LOG_TAG, "Update failure", e);
        }
        this.layout.afterDelete(content, i, i2, i3, i4, charSequence);
        checkForRelayout();
        updateCursor();
        this.renderer.invalidateOnDelete(i, i3);
        if (!this.waitForNextChange) {
            updateCursorAnchor();
            ensureSelectionVisible();
            this.touchHandler.hideInsertHandle();
        }
        if (this.editable && !this.cursor.isSelected() && !this.waitForNextChange && !this.inputConnection.composingText.isComposing() && acceptsComposingText()) {
            this.cursorAnimator.markEndPos();
            this.cursorAnimator.start();
        }
        this.editorLanguage.getAnalyzeManager().delete(charPosition, charPositionFromThis, charSequence);
        this.selectionAnchor = this.lastAnchorIsSelLeft ? this.cursor.left() : this.cursor.right();
        dispatchEvent(new ContentChangeEvent(this, 3, charPosition, charPositionFromThis, charSequence, this.text.isUndoManagerWorking()));
        onSelectionChanged(1);
    }

    @Override // io.github.rosemoe.sora.text.ContentListener
    public void beforeModification(Content content) {
        this.cursorAnimator.markStartPos();
        this.lastAnchorIsSelLeft = this.cursor.left().equals(this.selectionAnchor);
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter.FormatResultReceiver
    public void onFormatSucceed(final CharSequence charSequence, final TextRange textRange) {
        postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.CodeEditor$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFormatSucceed$2(charSequence, textRange);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFormatSucceed$2(CharSequence charSequence, TextRange textRange) {
        int leftLine = this.cursor.getLeftLine();
        int leftColumn = this.cursor.getLeftColumn();
        int offsetX = getOffsetX();
        int offsetY = getOffsetY();
        if (charSequence instanceof Content) {
            charSequence = ((Content) charSequence).toStringBuilder();
        }
        this.text.beginBatchEdit();
        Content content = this.text;
        int lineCount = content.getLineCount() - 1;
        Content content2 = this.text;
        content.delete(0, 0, lineCount, content2.getColumnCount(content2.getLineCount() - 1));
        this.text.insert(0, 0, charSequence);
        this.text.endBatchEdit();
        this.inputConnection.invalid();
        if (textRange == null) {
            setSelectionAround(leftLine, leftColumn);
        } else {
            try {
                CharPosition start = textRange.getStart();
                CharPosition end = textRange.getEnd();
                setSelectionRegion(start.line, start.column, end.line, end.column);
            } catch (IndexOutOfBoundsException e) {
                Log.w(LOG_TAG, e);
            }
        }
        getScroller().forceFinished(true);
        getScroller().startScroll(offsetX, offsetY, 0, 0, 0);
        getScroller().abortAnimation();
        this.touchHandler.scrollBy(0.0f, 0.0f);
        this.inputConnection.reset();
        restartInput();
        dispatchEvent(new EditorFormatEvent(this, true));
    }

    @Override // io.github.rosemoe.sora.lang.format.Formatter.FormatResultReceiver
    public void onFormatFail(final Throwable th) {
        postInLifecycle(new Runnable() { // from class: io.github.rosemoe.sora.widget.CodeEditor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onFormatFail$3(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFormatFail$3(Throwable th) {
        Toast.makeText(getContext(), "Format:" + th, 0).show();
        dispatchEvent(new EditorFormatEvent(this, false));
    }

    @Override // io.github.rosemoe.sora.text.LineRemoveListener
    public void onRemove(Content content, ContentLine contentLine) {
        this.layout.onRemove(content, contentLine);
    }
}
