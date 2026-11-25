package io.github.rosemoe.sora.widget;

import android.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.SurroundingText;
import io.github.rosemoe.sora.event.ContentChangeEvent;
import io.github.rosemoe.sora.event.Event;
import io.github.rosemoe.sora.event.EventReceiver;
import io.github.rosemoe.sora.event.ImePrivateCommandEvent;
import io.github.rosemoe.sora.event.Unsubscribe;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.ComposingText;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.util.Logger;

/* JADX INFO: loaded from: classes11.dex */
class EditorInputConnection extends BaseInputConnection {
    protected ComposingText composingText;
    private boolean connectionInvalid;
    private final CodeEditor editor;
    protected boolean imeConsumingInput;
    private static final Logger logger = Logger.instance("EditorInputConnection");
    static boolean DEBUG = false;

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean deleteSurroundingTextInCodePoints(int i, int i2) {
        return false;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean reportFullscreenMode(boolean z) {
        return false;
    }

    public EditorInputConnection(CodeEditor codeEditor) {
        super(codeEditor, true);
        this.composingText = new ComposingText();
        this.imeConsumingInput = false;
        this.editor = codeEditor;
        this.connectionInvalid = false;
        codeEditor.subscribeEvent(ContentChangeEvent.class, new EventReceiver() { // from class: io.github.rosemoe.sora.widget.EditorInputConnection$$ExternalSyntheticLambda0
            @Override // io.github.rosemoe.sora.event.EventReceiver
            public final void onReceive(Event event, Unsubscribe unsubscribe) {
                this.f$0.lambda$new$0((ContentChangeEvent) event, unsubscribe);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ContentChangeEvent contentChangeEvent, Unsubscribe unsubscribe) {
        if (contentChangeEvent.getAction() == 2) {
            this.composingText.shiftOnInsert(contentChangeEvent.getChangeStart().index, contentChangeEvent.getChangeEnd().index);
        } else if (contentChangeEvent.getAction() == 3) {
            this.composingText.shiftOnDelete(contentChangeEvent.getChangeStart().index, contentChangeEvent.getChangeEnd().index);
        }
    }

    protected void invalid() {
        this.connectionInvalid = true;
        this.composingText.reset();
        resetBatchEdit();
        this.editor.invalidate();
    }

    protected void reset() {
        resetBatchEdit();
        this.composingText.reset();
        this.connectionInvalid = false;
        this.imeConsumingInput = false;
    }

    private void resetBatchEdit() {
        Content text = this.editor.getText();
        while (text.isInBatchEdit()) {
            text.endBatchEdit();
        }
    }

    private Cursor getCursor() {
        return this.editor.getCursor();
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean commitText(CharSequence charSequence, int i) {
        if (DEBUG) {
            logger.d("commitText text = " + ((Object) charSequence) + ", pos = " + i);
        }
        if (!this.editor.isEditable() || this.connectionInvalid || charSequence == null) {
            return false;
        }
        if ("\n".equals(charSequence.toString())) {
            sendKeyClick(66);
        } else {
            commitTextInternal(charSequence, true);
        }
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public synchronized void closeConnection() {
        super.closeConnection();
        resetBatchEdit();
        this.composingText.reset();
        this.editor.onCloseConnection();
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public int getCursorCapsMode(int i) {
        return TextUtils.getCapsMode(this.editor.getText(), getCursor().getLeft(), i);
    }

    private CharSequence getTextRegionInternal(int i, int i2, int i3, boolean z) {
        Content text = this.editor.getText();
        if (i > i2) {
            i2 = i;
            i = i2;
        }
        int i4 = 0;
        if (i < 0) {
            i = 0;
        }
        if (i2 > text.length()) {
            i2 = text.length();
        }
        if (i2 < i) {
            i = 0;
            i2 = 0;
        }
        if (!z && i2 - i > this.editor.getProps().maxIPCTextLength) {
            i2 = Math.max(0, this.editor.getProps().maxIPCTextLength) + i;
        }
        String string = text.subSequence(i, i2).toString();
        if (i3 != 1) {
            return string;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        if (this.composingText.isComposing()) {
            try {
                int i5 = this.composingText.startIndex;
                int i6 = this.composingText.endIndex;
                int i7 = i5 - i;
                if (i7 >= spannableStringBuilder.length()) {
                    return spannableStringBuilder;
                }
                if (i7 >= 0) {
                    i4 = i7;
                }
                int length = i6 - i;
                if (length <= 0) {
                    return spannableStringBuilder;
                }
                if (length >= spannableStringBuilder.length()) {
                    length = spannableStringBuilder.length();
                }
                spannableStringBuilder.setSpan(256, i4, length, 33);
            } catch (IndexOutOfBoundsException unused) {
            }
        }
        return spannableStringBuilder;
    }

    protected CharSequence getTextRegion(int i, int i2, int i3) {
        try {
            CharSequence textRegionInternal = getTextRegionInternal(i, i2, i3, false);
            if (DEBUG) {
                logger.d("getTextRegion result:" + ((Object) textRegionInternal));
            }
            return textRegionInternal;
        } catch (IndexOutOfBoundsException e) {
            logger.w("Failed to get text region for IME", e);
            return "";
        }
    }

    protected CharSequence getTextRegionUnlimited(int i, int i2, int i3) {
        try {
            CharSequence textRegionInternal = getTextRegionInternal(i, i2, i3, true);
            if (DEBUG) {
                logger.d("getTextRegion result:" + ((Object) textRegionInternal));
            }
            return textRegionInternal;
        } catch (IndexOutOfBoundsException e) {
            logger.w("Failed to get text region for IME", e);
            return "";
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public CharSequence getSelectedText(int i) {
        int left;
        int right;
        if (this.editor.getProps().disallowSuggestions || (left = getCursor().getLeft()) == (right = getCursor().getRight())) {
            return null;
        }
        return getTextRegion(left, right, i);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public CharSequence getTextBeforeCursor(int i, int i2) {
        if (this.editor.getProps().disallowSuggestions) {
            return "";
        }
        int left = getCursor().getLeft();
        return getTextRegion(Math.max(left - i, left - this.editor.getProps().maxIPCTextLength), left, i2);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public CharSequence getTextAfterCursor(int i, int i2) {
        if (this.editor.getProps().disallowSuggestions) {
            return "";
        }
        int right = getCursor().getRight();
        return getTextRegion(right, i + right, i2);
    }

    private void sendKeyClick(int i) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        sendKeyEvent(new KeyEvent(jUptimeMillis, jUptimeMillis, 0, i, 0, 0, -1, 0, 6));
        sendKeyEvent(new KeyEvent(SystemClock.uptimeMillis(), jUptimeMillis, 1, i, 0, 0, -1, 0, 6));
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    protected void commitTextInternal(CharSequence charSequence, boolean z) {
        boolean zIsComposing = this.composingText.isComposing();
        if (this.editor.getProps().trackComposingTextOnCommit) {
            if (this.composingText.isComposing()) {
                String string = this.editor.getText().subSequence(this.composingText.startIndex, this.composingText.endIndex).toString();
                String string2 = charSequence.toString();
                if (this.composingText.endIndex == getCursor().getLeft() && !getCursor().isSelected() && string2.startsWith(string) && string2.length() > string.length()) {
                    charSequence = string2.substring(string.length());
                    this.composingText.reset();
                } else {
                    deleteComposingText();
                }
            }
        } else if (this.composingText.isComposing()) {
            deleteComposingText();
        }
        SymbolPairMatch.SymbolPair symbolPairMatchBestPair = null;
        symbolPairMatchBestPair = null;
        if (this.editor.getProps().symbolPairAutoCompletion && charSequence.length() > 0) {
            symbolPairMatchBestPair = this.editor.languageSymbolPairs.matchBestPair(this.editor.getText(), this.editor.getCursor().left(), charSequence.length() > 1 ? charSequence.toString().toCharArray() : null, charSequence.charAt(0));
        }
        Content text = this.editor.getText();
        Cursor cursor = this.editor.getCursor();
        if (symbolPairMatchBestPair == null || symbolPairMatchBestPair == SymbolPairMatch.SymbolPair.EMPTY_SYMBOL_PAIR || symbolPairMatchBestPair.shouldNotReplace(this.editor)) {
            this.editor.commitText(charSequence, z);
        } else if (symbolPairMatchBestPair.shouldDoAutoSurround(text) && this.editor.getEditorLanguage().getQuickQuoteHandler() == null) {
            text.beginBatchEdit();
            text.insert(cursor.getLeftLine(), cursor.getLeftColumn(), symbolPairMatchBestPair.open);
            text.insert(cursor.getRightLine(), cursor.getRightColumn(), symbolPairMatchBestPair.close);
            text.endBatchEdit();
            this.editor.setSelectionRegion(cursor.getLeftLine(), cursor.getLeftColumn(), cursor.getRightLine(), cursor.getRightColumn() - symbolPairMatchBestPair.close.length());
        } else if (cursor.isSelected() && this.editor.getEditorLanguage().getQuickQuoteHandler() != null) {
            this.editor.commitText(charSequence, z);
        } else {
            text.beginBatchEdit();
            CharPosition charPosition = text.getIndexer().getCharPosition(symbolPairMatchBestPair.getInsertOffset());
            text.replace(charPosition.line, charPosition.column, cursor.getRightLine(), cursor.getRightColumn(), symbolPairMatchBestPair.open);
            text.insert(charPosition.line, charPosition.column + symbolPairMatchBestPair.open.length(), symbolPairMatchBestPair.close);
            text.endBatchEdit();
            CharPosition charPosition2 = text.getIndexer().getCharPosition(symbolPairMatchBestPair.getCursorOffset());
            this.editor.setSelection(charPosition2.line, charPosition2.column);
        }
        if (zIsComposing) {
            endBatchEdit();
        }
    }

    private void deleteComposingText() {
        if (this.composingText.isComposing()) {
            try {
                this.editor.getText().delete(this.composingText.startIndex, this.composingText.endIndex);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            this.composingText.reset();
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean deleteSurroundingText(int i, int i2) {
        if (DEBUG) {
            logger.d("deleteSurroundingText, before = " + i + ", after = " + i2);
        }
        if (!this.editor.isEditable() || this.connectionInvalid || i < 0 || i2 < 0) {
            return false;
        }
        if (i == 1 && i2 == 0 && !this.composingText.isComposing()) {
            this.editor.deleteText();
            return true;
        }
        if (i > 0 && i2 > 0) {
            beginBatchEdit();
        }
        boolean zIsComposing = this.composingText.isComposing();
        int i3 = zIsComposing ? this.composingText.startIndex : 0;
        int i4 = zIsComposing ? this.composingText.endIndex : 0;
        int left = getCursor().getLeft();
        int i5 = left - i;
        if (i5 < 0) {
            i5 = 0;
        }
        this.editor.getText().delete(i5, left);
        if (zIsComposing) {
            int iMax = Math.max(i5, i3);
            int iMax2 = i4 - Math.max(0, Math.min(left, i4) - iMax);
            int iMax3 = Math.max(0, iMax - i5);
            i4 = iMax2 - iMax3;
            i3 -= iMax3;
        }
        int right = getCursor().getRight();
        int length = right + i2;
        if (length > this.editor.getText().length()) {
            length = this.editor.getText().length();
        }
        this.editor.getText().delete(right, length);
        if (zIsComposing) {
            int iMax4 = Math.max(right, i3);
            Math.max(0, Math.min(length, i4) - iMax4);
            Math.max(0, iMax4 - right);
        }
        if (i > 0 && i2 > 0) {
            endBatchEdit();
        }
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public synchronized boolean beginBatchEdit() {
        if (DEBUG) {
            logger.d("beginBatchEdit");
        }
        if (this.editor.getProps().disallowSuggestions) {
            return this.editor.getText().isInBatchEdit();
        }
        return this.editor.getText().beginBatchEdit();
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public synchronized boolean endBatchEdit() {
        boolean zEndBatchEdit;
        if (DEBUG) {
            logger.d("endBatchEdit");
        }
        zEndBatchEdit = this.editor.getText().endBatchEdit();
        if (!zEndBatchEdit) {
            this.editor.updateSelection();
        }
        return zEndBatchEdit;
    }

    private void deleteSelected() {
        if (getCursor().isSelected()) {
            this.editor.deleteText();
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean setComposingText(CharSequence charSequence, int i) {
        if (DEBUG) {
            logger.d("setComposingText, text = " + ((Object) charSequence) + ", pos = " + i);
        }
        boolean z = false;
        if (this.editor.isEditable() && !this.connectionInvalid && this.editor.acceptsComposingText()) {
            if (this.editor.getProps().disallowSuggestions) {
                this.composingText.reset();
                commitText(charSequence, 0);
                return false;
            }
            if (TextUtils.indexOf(charSequence, '\n') != -1) {
                return false;
            }
            z = true;
            if (!this.composingText.isComposing()) {
                deleteSelected();
                beginBatchEdit();
                this.composingText.preSetComposing = true;
                this.editor.commitText(charSequence);
                this.composingText.set(getCursor().getLeft() - charSequence.length(), getCursor().getLeft());
            } else if (this.composingText.isComposing()) {
                if (this.editor.getProps().minimizeComposingTextUpdate) {
                    setComposingTextCompat(charSequence.toString());
                } else {
                    this.editor.getText().replace(this.composingText.startIndex, this.composingText.endIndex, charSequence);
                }
                this.composingText.adjustLength(charSequence.length());
            }
            if (charSequence.length() == 0) {
                finishComposingText();
            }
        }
        return z;
    }

    private void setComposingTextCompat(String str) {
        Content text = this.editor.getText();
        String strSubstring = text.substring(this.composingText.startIndex, this.composingText.endIndex);
        if (strSubstring.equals(str)) {
            return;
        }
        if (strSubstring.length() < str.length() && str.startsWith(strSubstring)) {
            CharPosition charPosition = text.getIndexer().getCharPosition(this.composingText.endIndex);
            text.insert(charPosition.line, charPosition.column, str.substring(strSubstring.length()));
        } else if (strSubstring.length() > str.length() && strSubstring.startsWith(str)) {
            text.delete(this.composingText.endIndex - (strSubstring.length() - str.length()), this.composingText.endIndex);
        } else {
            text.replace(this.composingText.startIndex, this.composingText.endIndex, str);
        }
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean finishComposingText() {
        if (DEBUG) {
            logger.d("finishComposingText");
        }
        if (!this.editor.isEditable() || this.connectionInvalid || this.editor.getProps().disallowSuggestions) {
            return false;
        }
        this.composingText.reset();
        endBatchEdit();
        this.editor.updateCursor();
        this.editor.invalidate();
        return true;
    }

    private int getWrappedIndex(int i) {
        if (i < 0) {
            return 0;
        }
        return i > this.editor.getText().length() ? this.editor.getText().length() : i;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean setSelection(int i, int i2) {
        if (DEBUG) {
            logger.d("setSelection, s = " + i + ", e = " + i2);
        }
        if (!this.editor.isEditable() || this.connectionInvalid || this.editor.getProps().disallowSuggestions) {
            return false;
        }
        int wrappedIndex = getWrappedIndex(i);
        int wrappedIndex2 = getWrappedIndex(i2);
        if (wrappedIndex > wrappedIndex2) {
            wrappedIndex2 = wrappedIndex;
            wrappedIndex = wrappedIndex2;
        }
        if (wrappedIndex == getCursor().getLeft() && wrappedIndex2 == getCursor().getRight()) {
            return true;
        }
        Content text = this.editor.getText();
        CharPosition charPosition = text.getIndexer().getCharPosition(wrappedIndex);
        CharPosition charPosition2 = text.getIndexer().getCharPosition(wrappedIndex2);
        this.editor.setSelectionRegion(charPosition.line, charPosition.column, charPosition2.line, charPosition2.column, false, 4);
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean setComposingRegion(int i, int i2) {
        if (DEBUG) {
            logger.d("setComposingRegion, s = " + i + ", e = " + i2);
        }
        if (this.editor.isEditable() && !this.connectionInvalid && this.editor.acceptsComposingText() && !this.editor.getProps().disallowSuggestions) {
            if (i == i2) {
                finishComposingText();
                return true;
            }
            if (i > i2) {
                i2 = i;
                i = i2;
            }
            if (i < 0) {
                i = 0;
            }
            try {
                Content text = this.editor.getText();
                if (i2 > text.length()) {
                    i2 = text.length();
                }
                if (i >= i2) {
                    return false;
                }
                this.composingText.set(i, i2);
                this.editor.invalidate();
                beginBatchEdit();
                return true;
            } catch (IndexOutOfBoundsException e) {
                logger.w("set composing region for IME failed", e);
            }
        }
        return false;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performContextMenuAction(int i) {
        switch (i) {
            case R.id.selectAll:
                this.editor.selectAll();
                return true;
            case R.id.cut:
                this.editor.copyText();
                if (getCursor().isSelected()) {
                    this.editor.deleteText();
                }
                return true;
            case R.id.copy:
                this.editor.copyText();
                return true;
            case R.id.paste:
                break;
            default:
                switch (i) {
                    case R.id.pasteAsPlainText:
                        break;
                    case R.id.undo:
                        this.editor.undo();
                        return true;
                    case R.id.redo:
                        this.editor.redo();
                        return true;
                    default:
                        return false;
                }
                break;
        }
        this.editor.pasteText();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean requestCursorUpdates(int i) {
        this.editor.updateCursorAnchor();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public ExtractedText getExtractedText(ExtractedTextRequest extractedTextRequest, int i) {
        if (DEBUG) {
            logger.d("getExtractedText, flags = " + i);
        }
        if (this.editor.getProps().disallowSuggestions) {
            return null;
        }
        if ((i & 1) != 0) {
            this.editor.setExtracting(extractedTextRequest);
        } else {
            this.editor.setExtracting(null);
        }
        return this.editor.extractText(extractedTextRequest);
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean clearMetaKeyStates(int i) {
        this.editor.getKeyMetaStates().clearMetaStates(i);
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public Handler getHandler() {
        return this.editor.getHandler();
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public SurroundingText getSurroundingText(int i, int i2, int i3) {
        if (DEBUG) {
            logger.d("getSurroundingText, beforeLen = " + i + ", afterLen = " + i2);
        }
        if (this.editor.getProps().disallowSuggestions) {
            return new SurroundingText("", 0, 0, -1);
        }
        if ((i | i2) < 0) {
            throw new IllegalArgumentException("length < 0");
        }
        int iMin = Math.min(Math.max(0, getCursor().getLeft() - i), getCursor().getLeft());
        return new SurroundingText(getTextRegionUnlimited(iMin, Math.min(this.editor.getText().length(), getCursor().getRight() + i2), i3), getCursor().getLeft() - iMin, getCursor().getRight() - iMin, iMin);
    }

    @Override // android.view.inputmethod.InputConnection
    public boolean setImeConsumesInput(boolean z) {
        if (this.connectionInvalid) {
            return false;
        }
        this.imeConsumingInput = z;
        this.editor.invalidate();
        return true;
    }

    @Override // android.view.inputmethod.BaseInputConnection, android.view.inputmethod.InputConnection
    public boolean performPrivateCommand(String str, Bundle bundle) {
        if (this.connectionInvalid) {
            return false;
        }
        this.editor.dispatchEvent(new ImePrivateCommandEvent(this.editor, str, bundle));
        return true;
    }
}
