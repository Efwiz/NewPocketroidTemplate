package org.catrobat.catroid.formulaeditor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.Layout;
import android.text.style.BackgroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.ui.fragment.FormulaEditorFragment;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/FormulaEditorEditText.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class FormulaEditorEditText extends EditText implements View.OnTouchListener {
    private static final BackgroundColorSpan COLOR_ERROR = new BackgroundColorSpan(-1048576);
    private static final BackgroundColorSpan COLOR_HIGHLIGHT = new BackgroundColorSpan(-13388315);
    private static final String TAG = "FormulaEditorEditText";
    private int absoluteCursorPosition;
    private Context context;
    private final Runnable cursorAnimation;
    private Button foldButton;
    private PopupWindow foldButtonPopup;
    private final Set<Integer> foldedBracketOccurrences;
    FormulaEditorFragment formulaEditorFragment;
    final GestureDetector gestureDetector;
    private FormulaEditorHistory history;
    private InternFormula internFormula;
    private final Paint paint;

    public boolean isDoNotMoveCursorOnTab() {
        return false;
    }

    @Override // android.widget.TextView, android.view.View
    public boolean onCheckIsTextEditor() {
        return false;
    }

    public FormulaEditorEditText(Context context) {
        super(context);
        this.history = null;
        this.formulaEditorFragment = null;
        this.absoluteCursorPosition = 0;
        this.paint = new Paint();
        this.foldedBracketOccurrences = new HashSet();
        this.foldButtonPopup = null;
        this.foldButton = null;
        this.gestureDetector = new GestureDetector(this.context, new GestureDetector.SimpleOnGestureListener() { // from class: org.catrobat.catroid.formulaeditor.FormulaEditorEditText.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent event) {
                FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, true);
                FormulaEditorEditText.this.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
                FormulaEditorEditText.this.highlightSelection();
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:26:0x00a1 A[LOOP:0: B:24:0x0099->B:26:0x00a1, LOOP_END] */
            /* JADX WARN: Code duplicated, block: B:29:0x00b1  */
            /* JADX WARN: Code duplicated, block: B:32:0x00bf  */
            /* JADX WARN: Code duplicated, block: B:35:0x00a4 A[EDGE_INSN: B:35:0x00a4->B:27:0x00a4 BREAK  A[LOOP:0: B:24:0x0099->B:26:0x00a1], SYNTHETIC] */
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motion) {
                int i;
                int i2;
                int offsetForHorizontal;
                Layout layout = FormulaEditorEditText.this.getLayout();
                if (layout != null) {
                    float lineHeight = FormulaEditorEditText.this.getLineHeight();
                    int y = (int) motion.getY();
                    int x = ((int) motion.getX()) - FormulaEditorEditText.this.getPaddingLeft();
                    float scrollY = FormulaEditorEditText.this.getScrollY();
                    int i3 = (int) (scrollY % lineHeight);
                    int height = (int) (FormulaEditorEditText.this.getHeight() / lineHeight);
                    float f = y;
                    float f2 = i3;
                    float f3 = lineHeight - f2;
                    if (f <= f3) {
                        FormulaEditorEditText.this.scrollBy(0, (int) (scrollY > lineHeight ? (f2 + (lineHeight / 2.0f)) * (-1.0f) : i3 * (-1)));
                    } else {
                        float f4 = lineHeight / 2.0f;
                        if (f < (height * lineHeight) - f4) {
                            int i4 = 1;
                            while (true) {
                                if (i4 <= height) {
                                    if (f <= FormulaEditorEditText.this.getPaddingTop() + f3 + (i4 * lineHeight)) {
                                        height = i4;
                                        break;
                                    }
                                    i4++;
                                }
                            }
                        } else if (f <= ((layout.getLineCount() * lineHeight) - FormulaEditorEditText.this.getScrollY()) - FormulaEditorEditText.this.getPaddingTop()) {
                            FormulaEditorEditText.this.scrollBy(0, (int) (f3 + f4));
                        }
                        i = (int) (scrollY / lineHeight);
                        while (true) {
                            i2 = height + i;
                            if (i2 >= layout.getLineCount()) {
                                break;
                            }
                            i--;
                        }
                        offsetForHorizontal = layout.getOffsetForHorizontal(i2, x);
                        if (offsetForHorizontal > FormulaEditorEditText.this.length()) {
                            offsetForHorizontal = FormulaEditorEditText.this.length();
                        }
                        if (!FormulaEditorEditText.this.isDoNotMoveCursorOnTab()) {
                            FormulaEditorEditText.this.absoluteCursorPosition = offsetForHorizontal;
                        }
                        FormulaEditorEditText formulaEditorEditText = FormulaEditorEditText.this;
                        formulaEditorEditText.absoluteCursorPosition = Math.min(formulaEditorEditText.absoluteCursorPosition, FormulaEditorEditText.this.length());
                        FormulaEditorEditText formulaEditorEditText2 = FormulaEditorEditText.this;
                        formulaEditorEditText2.setSelection(formulaEditorEditText2.absoluteCursorPosition);
                        FormulaEditorEditText.this.postInvalidate();
                        FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, false);
                        FormulaEditorEditText.this.highlightSelection();
                        FormulaEditorEditText.this.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
                        FormulaEditorEditText.this.history.updateCurrentCursor(FormulaEditorEditText.this.absoluteCursorPosition);
                        FormulaEditorEditText.this.formulaEditorFragment.refreshFormulaPreviewString(FormulaEditorEditText.this.internFormula.getExternFormulaString());
                        FormulaEditorEditText.this.formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                    }
                    height = 0;
                    i = (int) (scrollY / lineHeight);
                    while (true) {
                        i2 = height + i;
                        if (i2 >= layout.getLineCount()) {
                            break;
                            break;
                        }
                        i--;
                    }
                    offsetForHorizontal = layout.getOffsetForHorizontal(i2, x);
                    if (offsetForHorizontal > FormulaEditorEditText.this.length()) {
                        offsetForHorizontal = FormulaEditorEditText.this.length();
                    }
                    if (!FormulaEditorEditText.this.isDoNotMoveCursorOnTab()) {
                        FormulaEditorEditText.this.absoluteCursorPosition = offsetForHorizontal;
                    }
                    FormulaEditorEditText formulaEditorEditText3 = FormulaEditorEditText.this;
                    formulaEditorEditText3.absoluteCursorPosition = Math.min(formulaEditorEditText3.absoluteCursorPosition, FormulaEditorEditText.this.length());
                    FormulaEditorEditText formulaEditorEditText4 = FormulaEditorEditText.this;
                    formulaEditorEditText4.setSelection(formulaEditorEditText4.absoluteCursorPosition);
                    FormulaEditorEditText.this.postInvalidate();
                    FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, false);
                    FormulaEditorEditText.this.highlightSelection();
                    FormulaEditorEditText.this.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
                    FormulaEditorEditText.this.history.updateCurrentCursor(FormulaEditorEditText.this.absoluteCursorPosition);
                    FormulaEditorEditText.this.formulaEditorFragment.refreshFormulaPreviewString(FormulaEditorEditText.this.internFormula.getExternFormulaString());
                    FormulaEditorEditText.this.formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                }
                return true;
            }
        });
        this.cursorAnimation = new Runnable() { // from class: org.catrobat.catroid.formulaeditor.FormulaEditorEditText.2
            @Override // java.lang.Runnable
            public void run() {
                int currentTextColor = FormulaEditorEditText.this.getCurrentTextColor();
                Paint paint = FormulaEditorEditText.this.paint;
                if (FormulaEditorEditText.this.paint.getColor() != 0) {
                    currentTextColor = 0;
                }
                paint.setColor(currentTextColor);
                FormulaEditorEditText.this.invalidate();
                FormulaEditorEditText formulaEditorEditText = FormulaEditorEditText.this;
                formulaEditorEditText.postDelayed(formulaEditorEditText.cursorAnimation, 500L);
            }
        };
        this.context = context;
    }

    public FormulaEditorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.history = null;
        this.formulaEditorFragment = null;
        this.absoluteCursorPosition = 0;
        this.paint = new Paint();
        this.foldedBracketOccurrences = new HashSet();
        this.foldButtonPopup = null;
        this.foldButton = null;
        this.gestureDetector = new GestureDetector(this.context, new GestureDetector.SimpleOnGestureListener() { // from class: org.catrobat.catroid.formulaeditor.FormulaEditorEditText.1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent event) {
                FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, true);
                FormulaEditorEditText.this.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
                FormulaEditorEditText.this.highlightSelection();
                return true;
            }

            /* JADX WARN: Code duplicated, block: B:26:0x00a1 A[LOOP:0: B:24:0x0099->B:26:0x00a1, LOOP_END] */
            /* JADX WARN: Code duplicated, block: B:29:0x00b1  */
            /* JADX WARN: Code duplicated, block: B:32:0x00bf  */
            /* JADX WARN: Code duplicated, block: B:35:0x00a4 A[EDGE_INSN: B:35:0x00a4->B:27:0x00a4 BREAK  A[LOOP:0: B:24:0x0099->B:26:0x00a1], SYNTHETIC] */
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onSingleTapUp(MotionEvent motion) {
                int i;
                int i2;
                int offsetForHorizontal;
                Layout layout = FormulaEditorEditText.this.getLayout();
                if (layout != null) {
                    float lineHeight = FormulaEditorEditText.this.getLineHeight();
                    int y = (int) motion.getY();
                    int x = ((int) motion.getX()) - FormulaEditorEditText.this.getPaddingLeft();
                    float scrollY = FormulaEditorEditText.this.getScrollY();
                    int i3 = (int) (scrollY % lineHeight);
                    int height = (int) (FormulaEditorEditText.this.getHeight() / lineHeight);
                    float f = y;
                    float f2 = i3;
                    float f3 = lineHeight - f2;
                    if (f <= f3) {
                        FormulaEditorEditText.this.scrollBy(0, (int) (scrollY > lineHeight ? (f2 + (lineHeight / 2.0f)) * (-1.0f) : i3 * (-1)));
                    } else {
                        float f4 = lineHeight / 2.0f;
                        if (f < (height * lineHeight) - f4) {
                            int i4 = 1;
                            while (true) {
                                if (i4 <= height) {
                                    if (f <= FormulaEditorEditText.this.getPaddingTop() + f3 + (i4 * lineHeight)) {
                                        height = i4;
                                        break;
                                    }
                                    i4++;
                                }
                            }
                        } else if (f <= ((layout.getLineCount() * lineHeight) - FormulaEditorEditText.this.getScrollY()) - FormulaEditorEditText.this.getPaddingTop()) {
                            FormulaEditorEditText.this.scrollBy(0, (int) (f3 + f4));
                        }
                        i = (int) (scrollY / lineHeight);
                        while (true) {
                            i2 = height + i;
                            if (i2 >= layout.getLineCount()) {
                                break;
                            }
                            i--;
                        }
                        offsetForHorizontal = layout.getOffsetForHorizontal(i2, x);
                        if (offsetForHorizontal > FormulaEditorEditText.this.length()) {
                            offsetForHorizontal = FormulaEditorEditText.this.length();
                        }
                        if (!FormulaEditorEditText.this.isDoNotMoveCursorOnTab()) {
                            FormulaEditorEditText.this.absoluteCursorPosition = offsetForHorizontal;
                        }
                        FormulaEditorEditText formulaEditorEditText3 = FormulaEditorEditText.this;
                        formulaEditorEditText3.absoluteCursorPosition = Math.min(formulaEditorEditText3.absoluteCursorPosition, FormulaEditorEditText.this.length());
                        FormulaEditorEditText formulaEditorEditText4 = FormulaEditorEditText.this;
                        formulaEditorEditText4.setSelection(formulaEditorEditText4.absoluteCursorPosition);
                        FormulaEditorEditText.this.postInvalidate();
                        FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, false);
                        FormulaEditorEditText.this.highlightSelection();
                        FormulaEditorEditText.this.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
                        FormulaEditorEditText.this.history.updateCurrentCursor(FormulaEditorEditText.this.absoluteCursorPosition);
                        FormulaEditorEditText.this.formulaEditorFragment.refreshFormulaPreviewString(FormulaEditorEditText.this.internFormula.getExternFormulaString());
                        FormulaEditorEditText.this.formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                    }
                    height = 0;
                    i = (int) (scrollY / lineHeight);
                    while (true) {
                        i2 = height + i;
                        if (i2 >= layout.getLineCount()) {
                            break;
                            break;
                        }
                        i--;
                    }
                    offsetForHorizontal = layout.getOffsetForHorizontal(i2, x);
                    if (offsetForHorizontal > FormulaEditorEditText.this.length()) {
                        offsetForHorizontal = FormulaEditorEditText.this.length();
                    }
                    if (!FormulaEditorEditText.this.isDoNotMoveCursorOnTab()) {
                        FormulaEditorEditText.this.absoluteCursorPosition = offsetForHorizontal;
                    }
                    FormulaEditorEditText formulaEditorEditText5 = FormulaEditorEditText.this;
                    formulaEditorEditText5.absoluteCursorPosition = Math.min(formulaEditorEditText5.absoluteCursorPosition, FormulaEditorEditText.this.length());
                    FormulaEditorEditText formulaEditorEditText6 = FormulaEditorEditText.this;
                    formulaEditorEditText6.setSelection(formulaEditorEditText6.absoluteCursorPosition);
                    FormulaEditorEditText.this.postInvalidate();
                    FormulaEditorEditText.this.internFormula.setCursorAndSelection(FormulaEditorEditText.this.absoluteCursorPosition, false);
                    FormulaEditorEditText.this.highlightSelection();
                    FormulaEditorEditText.this.history.updateCurrentSelection(FormulaEditorEditText.this.internFormula.getSelection());
                    FormulaEditorEditText.this.history.updateCurrentCursor(FormulaEditorEditText.this.absoluteCursorPosition);
                    FormulaEditorEditText.this.formulaEditorFragment.refreshFormulaPreviewString(FormulaEditorEditText.this.internFormula.getExternFormulaString());
                    FormulaEditorEditText.this.formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
                }
                return true;
            }
        });
        this.cursorAnimation = new Runnable() { // from class: org.catrobat.catroid.formulaeditor.FormulaEditorEditText.2
            @Override // java.lang.Runnable
            public void run() {
                int currentTextColor = FormulaEditorEditText.this.getCurrentTextColor();
                Paint paint = FormulaEditorEditText.this.paint;
                if (FormulaEditorEditText.this.paint.getColor() != 0) {
                    currentTextColor = 0;
                }
                paint.setColor(currentTextColor);
                FormulaEditorEditText.this.invalidate();
                FormulaEditorEditText formulaEditorEditText = FormulaEditorEditText.this;
                formulaEditorEditText.postDelayed(formulaEditorEditText.cursorAnimation, 500L);
            }
        };
        this.context = context;
    }

    public void addTokensToActiveFormula(List<InternToken> tokensToAdd) {
        if (this.internFormula == null || tokensToAdd == null || tokensToAdd.isEmpty()) {
            Log.w(TAG, "addTokensToActiveFormula: internFormula is null or tokensToAdd is null/empty");
            return;
        }
        this.internFormula.insertTokens(this.context, tokensToAdd);
        pushToHistoryAndRefreshPreviewString();
        FormulaEditorFragment formulaEditorFragment = this.formulaEditorFragment;
        if (formulaEditorFragment != null) {
            formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
        }
    }

    public void init(FormulaEditorFragment formulaEditorFragment) {
        this.formulaEditorFragment = formulaEditorFragment;
        setOnTouchListener(this);
        setLongClickable(false);
        setSelectAllOnFocus(false);
        setCursorVisible(false);
        setTextColor(-1);
        setHintTextColor(-5194043);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(-16766131);
        gradientDrawable.setCornerRadius(24.0f);
        gradientDrawable.setStroke(3, -11442802);
        setBackground(gradientDrawable);
        setPadding(36, 28, 36, 28);
        this.cursorAnimation.run();
    }

    public List<InternToken> getSelectedTokens() {
        return this.internFormula.getSelectedTokenForCopy();
    }

    private void pushToHistoryAndRefreshPreviewString() {
        this.history.push(new UndoState(this.internFormula.getInternFormulaState(), this.formulaEditorFragment.getCurrentBrickField()));
        String strUpdateTextAndCursorFromInternFormula = updateTextAndCursorFromInternFormula();
        setSelection(this.absoluteCursorPosition);
        this.formulaEditorFragment.refreshFormulaPreviewString(strUpdateTextAndCursorFromInternFormula);
    }

    public void addTokens(List<InternToken> tokens) {
        this.internFormula.addTokens(this.context, tokens);
        pushToHistoryAndRefreshPreviewString();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void enterNewFormula(UndoState state) {
        this.foldedBracketOccurrences.clear();
        hideFoldPopup();
        InternFormula internFormulaCreateInternFormulaFromState = state.internFormulaState.createInternFormulaFromState();
        this.internFormula = internFormulaCreateInternFormulaFromState;
        internFormulaCreateInternFormulaFromState.generateExternFormulaStringAndInternExternMapping(this.context);
        updateTextAndCursorFromInternFormula();
        this.internFormula.selectWholeFormula();
        highlightSelection();
        FormulaEditorHistory formulaEditorHistory = this.history;
        if (formulaEditorHistory == null) {
            this.history = new FormulaEditorHistory(state);
        } else {
            formulaEditorHistory.updateCurrentState(state);
        }
    }

    public void updateVariableReferences(String oldName, String newName) {
        InternFormula internFormula = this.internFormula;
        if (internFormula == null) {
            return;
        }
        internFormula.updateVariableReferences(oldName, newName, this.context);
        this.history.push(new UndoState(this.internFormula.getInternFormulaState(), this.formulaEditorFragment.getCurrentBrickField()));
        Iterator<Map.Entry<Brick.FormulaField, InternFormulaState>> it = this.history.initialStates.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().updateUserDataTokens(InternTokenType.USER_VARIABLE, oldName, newName);
        }
        String strUpdateTextAndCursorFromInternFormula = updateTextAndCursorFromInternFormula();
        setSelection(this.absoluteCursorPosition);
        this.formulaEditorFragment.refreshFormulaPreviewString(strUpdateTextAndCursorFromInternFormula);
    }

    public void updateListReferences(String oldName, String newName) {
        InternFormula internFormula = this.internFormula;
        if (internFormula == null) {
            return;
        }
        internFormula.updateListReferences(oldName, newName, this.context);
        this.history.push(new UndoState(this.internFormula.getInternFormulaState(), this.formulaEditorFragment.getCurrentBrickField()));
        Iterator<Map.Entry<Brick.FormulaField, InternFormulaState>> it = this.history.initialStates.entrySet().iterator();
        while (it.hasNext()) {
            it.next().getValue().updateUserDataTokens(InternTokenType.USER_LIST, oldName, newName);
        }
        String strUpdateTextAndCursorFromInternFormula = updateTextAndCursorFromInternFormula();
        setSelection(this.absoluteCursorPosition);
        this.formulaEditorFragment.refreshFormulaPreviewString(strUpdateTextAndCursorFromInternFormula);
    }

    public void syncCursorPosition() {
        InternFormula internFormula;
        InternFormula internFormula2;
        int selectionStart = getSelectionStart();
        this.absoluteCursorPosition = selectionStart;
        int iMin = Math.min(selectionStart, length());
        this.absoluteCursorPosition = iMin;
        InternFormula internFormula3 = this.internFormula;
        if (internFormula3 != null) {
            internFormula3.setCursorAndSelection(iMin, false);
        }
        highlightSelection();
        FormulaEditorHistory formulaEditorHistory = this.history;
        if (formulaEditorHistory != null && (internFormula2 = this.internFormula) != null) {
            formulaEditorHistory.updateCurrentSelection(internFormula2.getSelection());
            this.history.updateCurrentCursor(this.absoluteCursorPosition);
        }
        FormulaEditorFragment formulaEditorFragment = this.formulaEditorFragment;
        if (formulaEditorFragment != null && (internFormula = this.internFormula) != null) {
            formulaEditorFragment.refreshFormulaPreviewString(internFormula.getExternFormulaString());
            this.formulaEditorFragment.updateButtonsOnKeyboardAndInvalidateOptionsMenu();
        }
        postInvalidate();
        checkForBracketNearCursor();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.absoluteCursorPosition = Math.min(this.absoluteCursorPosition, length());
        this.paint.setStrokeWidth(3.0f);
        Layout layout = getLayout();
        if (layout != null) {
            int lineForOffset = layout.getLineForOffset(this.absoluteCursorPosition);
            float primaryHorizontal = layout.getPrimaryHorizontal(this.absoluteCursorPosition) + getPaddingLeft();
            canvas.drawLine(primaryHorizontal, layout.getLineBaseline(lineForOffset) + layout.getLineAscent(lineForOffset) + getPaddingTop(), primaryHorizontal, layout.getLineBaseline(lineForOffset) + layout.getLineAscent(lineForOffset) + getTextSize() + getPaddingTop() + (lineForOffset == 0 ? 5.0f : 0.0f), this.paint);
        }
    }

    public void highlightSelection() {
        Editable text = getText();
        BackgroundColorSpan backgroundColorSpan = COLOR_HIGHLIGHT;
        text.removeSpan(backgroundColorSpan);
        BackgroundColorSpan backgroundColorSpan2 = COLOR_ERROR;
        text.removeSpan(backgroundColorSpan2);
        int externSelectionStartIndex = this.internFormula.getExternSelectionStartIndex();
        int externSelectionEndIndex = this.internFormula.getExternSelectionEndIndex();
        InternFormula.TokenSelectionType externSelectionType = this.internFormula.getExternSelectionType();
        if (externSelectionStartIndex == -1 || externSelectionEndIndex == -1 || externSelectionEndIndex == externSelectionStartIndex || externSelectionEndIndex > text.length()) {
            return;
        }
        if (externSelectionType == InternFormula.TokenSelectionType.USER_SELECTION) {
            text.setSpan(backgroundColorSpan, externSelectionStartIndex, externSelectionEndIndex, 33);
        } else {
            text.setSpan(backgroundColorSpan2, externSelectionStartIndex, externSelectionEndIndex, 33);
        }
    }

    public void setParseErrorCursorAndSelection() {
        this.internFormula.selectParseErrorTokenAndSetCursor();
        highlightSelection();
        setSelection(this.absoluteCursorPosition);
    }

    public void handleKeyEvent(int resource, String name2) {
        this.internFormula.handleKeyInput(resource, this.context, name2);
        pushToHistoryAndRefreshPreviewString();
    }

    public String getStringFromInternFormula() {
        return this.internFormula.getExternFormulaString();
    }

    public String getSelectedTextFromInternFormula() {
        return this.internFormula.getSelectedText();
    }

    public boolean isSelectedTokenFirstParamOfRegularExpression() {
        return this.internFormula.isSelectedTokenFirstParamOfRegularExpression();
    }

    public void overrideSelectedText(String string) {
        this.internFormula.overrideSelectedText(string, this.context);
        pushToHistoryAndRefreshPreviewString();
    }

    public boolean hasChanges() {
        FormulaEditorHistory formulaEditorHistory = this.history;
        return formulaEditorHistory != null && formulaEditorHistory.hasUnsavedChanges();
    }

    public void formulaSaved() {
        this.history.changesSaved();
    }

    public void endEdit() {
        this.history.clear();
    }

    public void quickSelect() {
        this.internFormula.selectWholeFormula();
        highlightSelection();
    }

    public boolean undo() {
        if (!this.history.undoIsPossible()) {
            return false;
        }
        UndoState undoStateBackward = this.history.backward();
        if (undoStateBackward != null) {
            InternFormula internFormulaCreateInternFormulaFromState = undoStateBackward.internFormulaState.createInternFormulaFromState();
            this.internFormula = internFormulaCreateInternFormulaFromState;
            internFormulaCreateInternFormulaFromState.generateExternFormulaStringAndInternExternMapping(this.context);
            this.internFormula.updateInternCursorPosition();
            updateTextAndCursorFromInternFormula();
        }
        this.formulaEditorFragment.refreshFormulaPreviewString(this.internFormula.getExternFormulaString());
        return true;
    }

    public boolean redo() {
        if (!this.history.redoIsPossible()) {
            return false;
        }
        UndoState undoStateForward = this.history.forward();
        if (undoStateForward != null) {
            InternFormula internFormulaCreateInternFormulaFromState = undoStateForward.internFormulaState.createInternFormulaFromState();
            this.internFormula = internFormulaCreateInternFormulaFromState;
            internFormulaCreateInternFormulaFromState.generateExternFormulaStringAndInternExternMapping(this.context);
            this.internFormula.updateInternCursorPosition();
            updateTextAndCursorFromInternFormula();
        }
        this.formulaEditorFragment.refreshFormulaPreviewString(this.internFormula.getExternFormulaString());
        return true;
    }

    private String updateTextAndCursorFromInternFormula() {
        String externFormulaString = this.internFormula.getExternFormulaString();
        setText(FormulaSpannableStringBuilder.buildSpannableFormulaString(this.context, externFormulaString, getTextSize()));
        applyFoldedSpans();
        int externCursorPosition = this.internFormula.getExternCursorPosition();
        this.absoluteCursorPosition = externCursorPosition;
        if (externCursorPosition > length()) {
            this.absoluteCursorPosition = length();
        }
        highlightSelection();
        return externFormulaString;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motion) {
        return this.gestureDetector.onTouchEvent(motion);
    }

    public InternFormulaParser getFormulaParser() {
        return this.internFormula.getInternFormulaParser();
    }

    public FormulaEditorHistory getHistory() {
        return this.history;
    }

    public boolean isThereSomethingToDelete() {
        InternFormula internFormula = this.internFormula;
        if (internFormula == null) {
            return false;
        }
        return internFormula.isThereSomethingToDelete();
    }

    public int getIndexOfCorrespondingRegularExpression() {
        return this.internFormula.getIndexOfCorrespondingRegularExpression();
    }

    public void setSelectionToFirstParamOfRegularExpressionAtInternalIndex(int indexOfRegularExpression) {
        this.internFormula.setSelectionToFirstParamOfRegularExpressionAtInternalIndex(indexOfRegularExpression);
        highlightSelection();
    }

    public InternFormula getInternFormula() {
        return this.internFormula;
    }

    private void showFoldPopup(final int bracketIndex, boolean isFolded, float x, float y) {
        String string;
        if (this.foldButtonPopup == null) {
            Button button = new Button(getContext());
            this.foldButton = button;
            button.setTextSize(12.0f);
            this.foldButton.setPadding(24, 12, 24, 12);
            this.foldButton.setAllCaps(false);
            int color = getContext().getResources().getColor(R.color.button_background);
            getContext().getResources().getColor(R.color.accent);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(color);
            gradientDrawable.setCornerRadius(16.0f);
            this.foldButton.setBackground(gradientDrawable);
            this.foldButton.setTextColor(-1);
            PopupWindow popupWindow = new PopupWindow(this.foldButton, -2, -2);
            this.foldButtonPopup = popupWindow;
            popupWindow.setFocusable(false);
            this.foldButtonPopup.setOutsideTouchable(true);
        }
        if (isFolded) {
            string = getContext().getString(R.string.formula_unfold);
        } else {
            string = getContext().getString(R.string.formula_fold);
        }
        this.foldButton.setText(string);
        this.foldButton.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.catroid.formulaeditor.FormulaEditorEditText$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.m5381x8dcef6(bracketIndex, view);
            }
        });
        if (getWindowToken() != null) {
            int[] iArr = new int[2];
            getLocationOnScreen(iArr);
            this.foldButtonPopup.showAtLocation(this, 0, (iArr[0] + ((int) x)) - 100, (iArr[1] + ((int) y)) - 140);
        }
    }

    /* JADX INFO: renamed from: lambda$showFoldPopup$0$org-catrobat-catroid-formulaeditor-FormulaEditorEditText, reason: not valid java name */
    /* synthetic */ void m5381x8dcef6(int i, View view) {
        toggleFold(i);
        this.foldButtonPopup.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:12:0x0028  */
    /* JADX WARN: Code duplicated, block: B:14:0x002c  */
    /* JADX WARN: Code duplicated, block: B:16:0x0034  */
    /* JADX WARN: Code duplicated, block: B:17:0x0036  */
    /* JADX WARN: Code duplicated, block: B:19:0x003c  */
    /* JADX WARN: Code duplicated, block: B:22:0x0043  */
    /* JADX WARN: Code duplicated, block: B:7:0x001b  */
    public void checkForBracketNearCursor() {
        int i;
        int iFindMatchingBracket;
        String string = getText().toString();
        int selectionStart = getSelectionStart();
        if (selectionStart > 0) {
            int i2 = selectionStart - 1;
            if (string.charAt(i2) == '(') {
                selectionStart = i2;
            } else if (selectionStart < string.length() || string.charAt(selectionStart) != '(') {
                if (selectionStart > 0) {
                    i = selectionStart - 1;
                    if (string.charAt(i) == ')') {
                        selectionStart = i;
                    } else if (selectionStart < string.length() || string.charAt(selectionStart) != ')') {
                        selectionStart = -1;
                    }
                } else if (selectionStart < string.length()) {
                    selectionStart = -1;
                } else {
                    selectionStart = -1;
                }
            }
        } else if (selectionStart < string.length()) {
            if (selectionStart > 0) {
                i = selectionStart - 1;
                if (string.charAt(i) == ')') {
                    selectionStart = i;
                } else if (selectionStart < string.length()) {
                    selectionStart = -1;
                } else {
                    selectionStart = -1;
                }
            } else if (selectionStart < string.length()) {
                selectionStart = -1;
            } else {
                selectionStart = -1;
            }
        } else if (selectionStart > 0) {
            i = selectionStart - 1;
            if (string.charAt(i) == ')') {
                selectionStart = i;
            } else if (selectionStart < string.length()) {
                selectionStart = -1;
            } else {
                selectionStart = -1;
            }
        } else if (selectionStart < string.length()) {
            selectionStart = -1;
        } else {
            selectionStart = -1;
        }
        if (selectionStart != -1 && (iFindMatchingBracket = findMatchingBracket(string, selectionStart)) != -1) {
            if (string.charAt(selectionStart) == '(') {
                iFindMatchingBracket = selectionStart;
            }
            boolean zContains = this.foldedBracketOccurrences.contains(Integer.valueOf(getOpeningBracketOccurrenceIndex(string, iFindMatchingBracket)));
            Layout layout = getLayout();
            if (layout != null) {
                int lineForOffset = layout.getLineForOffset(selectionStart);
                showFoldPopup(selectionStart, zContains, layout.getPrimaryHorizontal(selectionStart) + getPaddingLeft(), layout.getLineBaseline(lineForOffset) + layout.getLineAscent(lineForOffset));
                return;
            }
            return;
        }
        hideFoldPopup();
    }

    public static int findMatchingBracket(String text, int index) {
        if (index >= 0 && index < text.length()) {
            char cCharAt = text.charAt(index);
            int i = 0;
            if (cCharAt == '(') {
                while (index < text.length()) {
                    if (text.charAt(index) == '(') {
                        i++;
                    } else if (text.charAt(index) == ')' && (i = i - 1) == 0) {
                        return index;
                    }
                    index++;
                }
            } else if (cCharAt == ')') {
                while (index >= 0) {
                    if (text.charAt(index) == ')') {
                        i++;
                    } else if (text.charAt(index) == '(' && (i = i - 1) == 0) {
                        return index;
                    }
                    index--;
                }
            }
        }
        return -1;
    }

    private int getOpeningBracketOccurrenceIndex(String text, int openingBracketIndex) {
        int i = 0;
        for (int i2 = 0; i2 < openingBracketIndex; i2++) {
            if (text.charAt(i2) == '(') {
                i++;
            }
        }
        return i;
    }

    public void applyFoldedSpans() {
        int iFindMatchingBracket;
        int i;
        Editable text = getText();
        String string = text.toString();
        for (FoldedFormulaSpan foldedFormulaSpan : (FoldedFormulaSpan[]) text.getSpans(0, text.length(), FoldedFormulaSpan.class)) {
            text.removeSpan(foldedFormulaSpan);
        }
        int i2 = 0;
        for (int i3 = 0; i3 < string.length(); i3++) {
            if (string.charAt(i3) == '(') {
                if (this.foldedBracketOccurrences.contains(Integer.valueOf(i2)) && (iFindMatchingBracket = findMatchingBracket(string, i3)) != -1 && iFindMatchingBracket > (i = i3 + 1)) {
                    text.setSpan(new FoldedFormulaSpan(getTextSize()), i, iFindMatchingBracket, 33);
                }
                i2++;
            }
        }
    }

    private void toggleFold(int bracketIndex) {
        String string = getText().toString();
        if (bracketIndex < 0 || bracketIndex >= string.length()) {
            return;
        }
        if (string.charAt(bracketIndex) != '(') {
            bracketIndex = findMatchingBracket(string, bracketIndex);
        }
        if (bracketIndex == -1) {
            return;
        }
        int openingBracketOccurrenceIndex = getOpeningBracketOccurrenceIndex(string, bracketIndex);
        if (this.foldedBracketOccurrences.contains(Integer.valueOf(openingBracketOccurrenceIndex))) {
            this.foldedBracketOccurrences.remove(Integer.valueOf(openingBracketOccurrenceIndex));
        } else {
            this.foldedBracketOccurrences.add(Integer.valueOf(openingBracketOccurrenceIndex));
        }
        updateTextAndCursorFromInternFormula();
    }

    private void hideFoldPopup() {
        PopupWindow popupWindow = this.foldButtonPopup;
        if (popupWindow == null || !popupWindow.isShowing()) {
            return;
        }
        this.foldButtonPopup.dismiss();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        hideFoldPopup();
        super.onDetachedFromWindow();
    }

    @Override // android.widget.TextView
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        this.absoluteCursorPosition = selStart;
        post(new Runnable() { // from class: org.catrobat.catroid.formulaeditor.FormulaEditorEditText.3
            @Override // java.lang.Runnable
            public void run() {
                FormulaEditorEditText.this.checkForBracketNearCursor();
            }
        });
    }
}
