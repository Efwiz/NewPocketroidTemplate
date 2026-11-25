package io.github.rosemoe.sora.widget;

import android.util.Log;
import android.view.KeyEvent;
import io.github.rosemoe.sora.event.EditorKeyEvent;
import io.github.rosemoe.sora.event.EventManager;
import io.github.rosemoe.sora.event.KeyBindingEvent;
import io.github.rosemoe.sora.lang.Language;
import io.github.rosemoe.sora.lang.smartEnter.NewlineHandleResult;
import io.github.rosemoe.sora.lang.smartEnter.NewlineHandler;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.Cursor;
import io.github.rosemoe.sora.text.method.KeyMetaStates;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class EditorKeyEventHandler {
    private static final String TAG = "EditorKeyEventHandler";
    private final CodeEditor editor;
    private final KeyMetaStates keyMetaStates;

    EditorKeyEventHandler(CodeEditor codeEditor) {
        Objects.requireNonNull(codeEditor, "Cannot setup KeyEvent with null editor instance.");
        this.editor = codeEditor;
        this.keyMetaStates = new KeyMetaStates(codeEditor);
    }

    private boolean isKeyBindingEvent(int i, KeyEvent keyEvent) {
        if (!this.keyMetaStates.isShiftPressed() && !this.keyMetaStates.isAltPressed() && !keyEvent.isCtrlPressed()) {
            return false;
        }
        if (i < 29 || i > 54) {
            return i == 66 || i == 19 || i == 20 || i == 21 || i == 22 || i == 122 || i == 123;
        }
        return true;
    }

    public KeyMetaStates getKeyMetaStates() {
        return this.keyMetaStates;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0071  */
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        this.keyMetaStates.onKeyDown(keyEvent);
        CodeEditor codeEditor = this.editor;
        EventManager eventManager = codeEditor.eventManager;
        EditorKeyEvent editorKeyEvent = new EditorKeyEvent(codeEditor, keyEvent, EditorKeyEvent.Type.DOWN);
        KeyBindingEvent keyBindingEvent = new KeyBindingEvent(codeEditor, keyEvent, EditorKeyEvent.Type.DOWN, codeEditor.canHandleKeyBinding(i, keyEvent.isCtrlPressed(), this.keyMetaStates.isShiftPressed(), this.keyMetaStates.isAltPressed()));
        if ((eventManager.dispatchEvent(editorKeyEvent) & 2) != 0) {
            return editorKeyEvent.result(false);
        }
        boolean zIsShiftPressed = this.keyMetaStates.isShiftPressed();
        boolean zIsAltPressed = this.keyMetaStates.isAltPressed();
        boolean zIsCtrlPressed = keyEvent.isCtrlPressed();
        if (isKeyBindingEvent(i, keyEvent) && (eventManager.dispatchEvent(keyBindingEvent) & 2) != 0) {
            return keyBindingEvent.result(false) || editorKeyEvent.result(false);
        }
        if (i == 122 || i == 123) {
            this.keyMetaStates.adjust();
        } else {
            switch (i) {
                case 19:
                case 20:
                case 21:
                case 22:
                    this.keyMetaStates.adjust();
                    break;
            }
        }
        Boolean boolHandleKeyEvent = handleKeyEvent(keyEvent, editorKeyEvent, keyBindingEvent, i, zIsShiftPressed, zIsAltPressed, zIsCtrlPressed);
        if (boolHandleKeyEvent != null) {
            return editorKeyEvent.result(boolHandleKeyEvent.booleanValue());
        }
        return editorKeyEvent.result(codeEditor.onSuperKeyDown(i, keyEvent));
    }

    private Boolean handleKeyEvent(KeyEvent keyEvent, EditorKeyEvent editorKeyEvent, KeyBindingEvent keyBindingEvent, int i, boolean z, boolean z2, boolean z3) {
        EditorInputConnection editorInputConnection = this.editor.inputConnection;
        Cursor cursor = this.editor.getCursor();
        Content text = this.editor.getText();
        if (i == 4) {
            if (cursor.isSelected()) {
                this.editor.setSelection(cursor.getLeftLine(), cursor.getLeftColumn());
                return Boolean.valueOf(editorKeyEvent.result(true));
            }
            if (this.editor.isInLongSelect()) {
                this.editor.endLongSelect();
                return Boolean.valueOf(editorKeyEvent.result(true));
            }
            return Boolean.valueOf(editorKeyEvent.result(false));
        }
        if (i == 61) {
            if (this.editor.isEditable() && !z2 && !z3) {
                if (this.editor.getSnippetController().isInSnippet()) {
                    if (z) {
                        this.editor.getSnippetController().shiftToPreviousTabStop();
                    } else {
                        this.editor.getSnippetController().shiftToNextTabStop();
                    }
                } else if (z) {
                    this.editor.unindentSelection();
                } else {
                    this.editor.indentOrCommitTab();
                }
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 62) {
            if (this.editor.isEditable()) {
                this.editor.commitText(" ");
                this.editor.notifyIMEExternalCursorChange();
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 66) {
            return handleEnterKeyEvent(editorKeyEvent, keyBindingEvent, z, z2, z3);
        }
        if (i == 67) {
            if (this.editor.isEditable()) {
                this.editor.deleteText();
                this.editor.notifyIMEExternalCursorChange();
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 92) {
            if (z3) {
                this.editor.moveOrExtendSelection(SelectionMovement.PAGE_TOP, z);
            } else {
                this.editor.moveOrExtendSelection(SelectionMovement.PAGE_UP, z);
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 93) {
            if (z3) {
                this.editor.moveOrExtendSelection(SelectionMovement.PAGE_BOTTOM, z);
            } else {
                this.editor.moveOrExtendSelection(SelectionMovement.PAGE_DOWN, z);
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 111) {
            if (cursor.isSelected()) {
                CharPosition charPositionRight = this.editor.getProps().positionOfCursorWhenExitSelecting ? cursor.right() : cursor.left();
                this.editor.setSelection(charPositionRight.line, charPositionRight.column, true);
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 112) {
            if (this.editor.isEditable()) {
                editorInputConnection.deleteSurroundingText(0, 1);
                this.editor.notifyIMEExternalCursorChange();
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 122) {
            if (z3) {
                this.editor.moveOrExtendSelection(SelectionMovement.TEXT_START, z);
            } else {
                this.editor.moveOrExtendSelection(SelectionMovement.LINE_START, z);
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 123) {
            if (z3) {
                this.editor.moveOrExtendSelection(SelectionMovement.TEXT_END, z);
            } else {
                this.editor.moveOrExtendSelection(SelectionMovement.LINE_END, z);
            }
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i == 278) {
            this.editor.copyText();
            return Boolean.valueOf(editorKeyEvent.result(true));
        }
        if (i != 279) {
            switch (i) {
                case 19:
                    if (!z3) {
                        this.editor.moveOrExtendSelection(SelectionMovement.UP, z);
                        return Boolean.valueOf(editorKeyEvent.result(true));
                    }
                    if (z) {
                        CharPosition charPositionLeft = cursor.left();
                        CharPosition charPositionRight2 = cursor.right();
                        if (charPositionLeft.line == 0) {
                            return Boolean.valueOf(editorKeyEvent.result(true));
                        }
                        String string = text.getLine(charPositionLeft.line - 1).toString();
                        text.beginBatchEdit();
                        text.delete(charPositionLeft.line - 1, 0, charPositionLeft.line, 0);
                        text.insert(charPositionRight2.line - 1, text.getColumnCount(charPositionRight2.line - 1), this.editor.getLineSeparator().getContent().concat(string));
                        text.endBatchEdit();
                        CharPosition charPosition = text.getIndexer().getCharPosition(charPositionLeft.line - 1, charPositionLeft.column);
                        CharPosition charPosition2 = text.getIndexer().getCharPosition(charPositionRight2.line - 1, charPositionRight2.column);
                        if (charPositionLeft.index != charPositionRight2.index) {
                            CharPosition charPosition3 = this.editor.selectionAnchor;
                            this.editor.setSelectionRegion(charPosition.line, charPosition.column, charPosition2.line, charPosition2.column);
                            if (charPosition3 != null) {
                                if (charPosition3.equals(charPositionLeft)) {
                                    this.editor.selectionAnchor = charPosition;
                                } else {
                                    this.editor.selectionAnchor = charPosition2;
                                }
                            }
                        } else {
                            this.editor.setSelection(charPosition.line, charPosition.column);
                        }
                        return Boolean.valueOf(editorKeyEvent.result(true));
                    }
                    this.editor.getEventHandler().scrollBy(0.0f, -this.editor.getRowHeight());
                    return Boolean.valueOf(editorKeyEvent.result(true));
                case 20:
                    if (!z3) {
                        this.editor.moveOrExtendSelection(SelectionMovement.DOWN, z);
                        return Boolean.valueOf(editorKeyEvent.result(true));
                    }
                    if (z) {
                        CharPosition charPositionLeft2 = cursor.left();
                        CharPosition charPositionRight3 = cursor.right();
                        if (charPositionRight3.line == text.getLineCount() - 1) {
                            return Boolean.valueOf(editorKeyEvent.result(true));
                        }
                        String string2 = text.getLine(charPositionRight3.line + 1).toString();
                        text.beginBatchEdit();
                        text.delete(charPositionRight3.line, text.getColumnCount(charPositionRight3.line), charPositionRight3.line + 1, string2.length());
                        text.insert(charPositionLeft2.line, 0, string2.concat(this.editor.getLineSeparator().getContent()));
                        text.endBatchEdit();
                        CharPosition charPosition4 = text.getIndexer().getCharPosition(charPositionLeft2.line + 1, charPositionLeft2.column);
                        CharPosition charPosition5 = text.getIndexer().getCharPosition(charPositionRight3.line + 1, charPositionRight3.column);
                        if (charPositionLeft2.index != charPositionRight3.index) {
                            CharPosition charPosition6 = this.editor.selectionAnchor;
                            this.editor.setSelectionRegion(charPosition4.line, charPosition4.column, charPosition5.line, charPosition5.column);
                            if (charPosition6 != null) {
                                if (charPosition6.equals(charPositionLeft2)) {
                                    this.editor.selectionAnchor = charPosition4;
                                } else {
                                    this.editor.selectionAnchor = charPosition5;
                                }
                            }
                        } else {
                            this.editor.setSelection(charPosition4.line, charPosition4.column);
                        }
                        return Boolean.valueOf(editorKeyEvent.result(true));
                    }
                    this.editor.getEventHandler().scrollBy(0.0f, this.editor.getRowHeight());
                    return Boolean.valueOf(editorKeyEvent.result(true));
                case 21:
                    if (z3) {
                        this.editor.moveOrExtendSelection(SelectionMovement.PREVIOUS_WORD_BOUNDARY, z);
                    } else {
                        this.editor.moveOrExtendSelection(SelectionMovement.LEFT, z);
                    }
                    return Boolean.valueOf(editorKeyEvent.result(true));
                case 22:
                    if (z3) {
                        this.editor.moveOrExtendSelection(SelectionMovement.NEXT_WORD_BOUNDARY, z);
                    } else {
                        this.editor.moveOrExtendSelection(SelectionMovement.RIGHT, z);
                    }
                    return Boolean.valueOf(editorKeyEvent.result(true));
                default:
                    if (keyEvent.isCtrlPressed() && !keyEvent.isAltPressed()) {
                        return handleCtrlKeyBinding(editorKeyEvent, keyBindingEvent, i, z);
                    }
                    if (keyEvent.isCtrlPressed() || keyEvent.isAltPressed()) {
                        return null;
                    }
                    return handlePrintingKey(keyEvent, editorKeyEvent, i);
            }
        }
        if (this.editor.isEditable()) {
            this.editor.pasteText();
        }
        return Boolean.valueOf(editorKeyEvent.result(true));
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
    private Boolean handlePrintingKey(KeyEvent keyEvent, EditorKeyEvent editorKeyEvent, int i) {
        Content text = this.editor.getText();
        Cursor cursor = this.editor.getCursor();
        int unicodeChar = keyEvent.getUnicodeChar(this.keyMetaStates.getMetaState(keyEvent));
        if (unicodeChar == 0 || !this.editor.isEditable()) {
            return Boolean.valueOf(this.editor.onSuperKeyDown(i, keyEvent));
        }
        if (unicodeChar == 61184 || unicodeChar == 61185) {
            return Boolean.valueOf(this.editor.onSuperKeyDown(i, keyEvent));
        }
        String str = new String(Character.toChars(unicodeChar));
        SymbolPairMatch.SymbolPair symbolPairMatchBestPair = null;
        if (this.editor.getProps().symbolPairAutoCompletion) {
            symbolPairMatchBestPair = this.editor.languageSymbolPairs.matchBestPair(this.editor.getText(), this.editor.getCursor().left(), str.length() > 1 ? str.toCharArray() : null, str.charAt(0));
        }
        SymbolPairMatch.SymbolPair symbolPair = symbolPairMatchBestPair;
        if (symbolPair == null || symbolPair == SymbolPairMatch.SymbolPair.EMPTY_SYMBOL_PAIR || symbolPair.shouldNotReplace(this.editor)) {
            this.editor.commitText(str);
            this.editor.notifyIMEExternalCursorChange();
        } else {
            if (symbolPair.shouldDoAutoSurround(text) && this.editor.getEditorLanguage().getQuickQuoteHandler() == null) {
                text.beginBatchEdit();
                text.insert(cursor.getLeftLine(), cursor.getLeftColumn(), symbolPair.open);
                text.insert(cursor.getRightLine(), cursor.getRightColumn(), symbolPair.close);
                text.endBatchEdit();
                this.editor.setSelectionRegion(cursor.getLeftLine(), cursor.getLeftColumn(), cursor.getRightLine(), cursor.getRightColumn() - symbolPair.close.length());
            } else if (cursor.isSelected() && this.editor.getEditorLanguage().getQuickQuoteHandler() != null) {
                this.editor.commitText(str);
            } else {
                text.beginBatchEdit();
                CharPosition charPosition = text.getIndexer().getCharPosition(symbolPair.getInsertOffset());
                text.replace(charPosition.line, charPosition.column, cursor.getRightLine(), cursor.getRightColumn(), symbolPair.open);
                text.insert(charPosition.line, charPosition.column + symbolPair.open.length(), symbolPair.close);
                text.endBatchEdit();
                CharPosition charPosition2 = text.getIndexer().getCharPosition(symbolPair.getCursorOffset());
                this.editor.setSelection(charPosition2.line, charPosition2.column);
            }
            this.editor.notifyIMEExternalCursorChange();
        }
        return Boolean.valueOf(editorKeyEvent.result(true));
    }

    /* JADX WARN: Code duplicated, block: B:43:0x008e  */
    private Boolean handleCtrlKeyBinding(EditorKeyEvent editorKeyEvent, KeyBindingEvent keyBindingEvent, int i, boolean z) {
        boolean z2;
        CodeEditor codeEditor = this.editor;
        EditorInputConnection editorInputConnection = codeEditor.inputConnection;
        Content text = codeEditor.getText();
        Cursor cursor = codeEditor.getCursor();
        if (i == 29) {
            codeEditor.selectAll();
        } else {
            if (i == 38) {
                if (!z || cursor.isSelected()) {
                    z2 = false;
                } else {
                    int leftLine = cursor.getLeftLine();
                    codeEditor.setSelection(leftLine, text.getColumnCount(leftLine));
                    editorInputConnection.deleteSurroundingText(0, 1);
                    codeEditor.ensureSelectionVisible();
                }
                return Boolean.valueOf(!keyBindingEvent.result(z2) || editorKeyEvent.result(z2));
            }
            if (i == 31) {
                codeEditor.copyText();
            } else if (i != 32) {
                switch (i) {
                    case 50:
                        if (codeEditor.isEditable()) {
                            codeEditor.pasteText();
                        }
                        break;
                    case 51:
                        codeEditor.selectCurrentWord();
                        break;
                    case 52:
                        if (codeEditor.isEditable()) {
                            codeEditor.cutText();
                        } else {
                            codeEditor.copyText();
                        }
                        break;
                    case 53:
                        if (codeEditor.isEditable()) {
                            codeEditor.redo();
                        }
                        break;
                    case 54:
                        if (codeEditor.isEditable()) {
                            codeEditor.undo();
                        }
                        break;
                    default:
                        return null;
                }
            } else if (codeEditor.isEditable()) {
                codeEditor.duplicateLine();
            }
        }
        z2 = true;
        return Boolean.valueOf(!keyBindingEvent.result(z2) || editorKeyEvent.result(z2));
    }

    private Boolean handleEnterKeyEvent(EditorKeyEvent editorKeyEvent, KeyBindingEvent keyBindingEvent, boolean z, boolean z2, boolean z3) {
        CodeEditor codeEditor = this.editor;
        Cursor cursor = codeEditor.getCursor();
        Content text = codeEditor.getText();
        boolean z4 = true;
        if (codeEditor.isEditable()) {
            String content = codeEditor.getLineSeparator().getContent();
            Language editorLanguage = codeEditor.getEditorLanguage();
            if (z && !z2 && !z3) {
                return Boolean.valueOf(startNewLine(codeEditor, cursor, text, editorKeyEvent, keyBindingEvent));
            }
            if (z3 && !z) {
                if (z2) {
                    int i = cursor.left().line;
                    if (i == 0) {
                        text.insert(0, 0, content);
                        codeEditor.setSelection(0, 0);
                        codeEditor.ensureSelectionVisible();
                        if (!keyBindingEvent.result(true) && !editorKeyEvent.result(true)) {
                            z4 = false;
                        }
                        return Boolean.valueOf(z4);
                    }
                    int i2 = i - 1;
                    codeEditor.setSelection(i2, text.getColumnCount(i2));
                    return Boolean.valueOf(startNewLine(codeEditor, cursor, text, editorKeyEvent, keyBindingEvent));
                }
                CharPosition charPositionFromThis = cursor.left().fromThis();
                codeEditor.commitText(content);
                codeEditor.setSelection(charPositionFromThis.line, charPositionFromThis.column);
                codeEditor.ensureSelectionVisible();
                if (!keyBindingEvent.result(true) && !editorKeyEvent.result(true)) {
                    z4 = false;
                }
                return Boolean.valueOf(z4);
            }
            NewlineHandler[] newlineHandlers = editorLanguage.getNewlineHandlers();
            if (newlineHandlers == null || cursor.isSelected()) {
                codeEditor.commitText(content, true);
            } else {
                int length = newlineHandlers.length;
                int i3 = 0;
                while (true) {
                    if (i3 < length) {
                        NewlineHandler newlineHandler = newlineHandlers[i3];
                        if (newlineHandler != null && newlineHandler.matchesRequirement(text, cursor.left(), codeEditor.getStyles())) {
                            try {
                                NewlineHandleResult newlineHandleResultHandleNewline = newlineHandler.handleNewline(text, cursor.left(), codeEditor.getStyles(), codeEditor.getTabWidth());
                                codeEditor.commitText(newlineHandleResultHandleNewline.text, false);
                                int i4 = newlineHandleResultHandleNewline.shiftLeft;
                                if (i4 == 0) {
                                    break;
                                }
                                CharPosition charPosition = cursor.getIndexer().getCharPosition(Math.max(cursor.getLeft() - i4, 0));
                                codeEditor.setSelection(charPosition.line, charPosition.column);
                                break;
                            } catch (Exception e) {
                                Log.w(TAG, "Error occurred while calling Language's NewlineHandler", e);
                                codeEditor.commitText(content, true);
                                break;
                            }
                        }
                        i3++;
                    }
                    codeEditor.commitText(content, true);
                    break;
                }
            }
            codeEditor.notifyIMEExternalCursorChange();
        }
        return Boolean.valueOf(editorKeyEvent.result(true));
    }

    private boolean startNewLine(CodeEditor codeEditor, Cursor cursor, Content content, EditorKeyEvent editorKeyEvent, KeyBindingEvent keyBindingEvent) {
        int i = cursor.right().line;
        codeEditor.setSelection(i, content.getColumnCount(i));
        codeEditor.commitText(codeEditor.getLineSeparator().getContent());
        codeEditor.ensureSelectionVisible();
        return keyBindingEvent.result(true) || editorKeyEvent.result(true);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        this.keyMetaStates.onKeyUp(keyEvent);
        EventManager eventManager = this.editor.eventManager;
        Cursor cursor = this.editor.getCursor();
        EditorKeyEvent editorKeyEvent = new EditorKeyEvent(this.editor, keyEvent, EditorKeyEvent.Type.UP);
        if ((eventManager.dispatchEvent(editorKeyEvent) & 2) != 0) {
            return editorKeyEvent.result(false);
        }
        if (isKeyBindingEvent(i, keyEvent)) {
            KeyBindingEvent keyBindingEvent = new KeyBindingEvent(this.editor, keyEvent, EditorKeyEvent.Type.UP, this.editor.canHandleKeyBinding(i, keyEvent.isCtrlPressed(), this.keyMetaStates.isShiftPressed(), this.keyMetaStates.isAltPressed()));
            if ((eventManager.dispatchEvent(keyBindingEvent) & 2) != 0) {
                return keyBindingEvent.result(false) || editorKeyEvent.result(false);
            }
        }
        if (!this.keyMetaStates.isShiftPressed() && this.editor.selectionAnchor != null && !cursor.isSelected()) {
            this.editor.selectionAnchor = null;
            return editorKeyEvent.result(true);
        }
        return editorKeyEvent.result(this.editor.onSuperKeyUp(i, keyEvent));
    }

    public boolean onKeyMultiple(int i, int i2, KeyEvent keyEvent) {
        EditorKeyEvent editorKeyEvent = new EditorKeyEvent(this.editor, keyEvent, EditorKeyEvent.Type.MULTIPLE);
        if ((this.editor.eventManager.dispatchEvent(editorKeyEvent) & 2) != 0) {
            return editorKeyEvent.result(false);
        }
        return editorKeyEvent.result(this.editor.onSuperKeyMultiple(i, i2, keyEvent));
    }
}
