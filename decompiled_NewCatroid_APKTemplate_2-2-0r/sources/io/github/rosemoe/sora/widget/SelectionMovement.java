package io.github.rosemoe.sora.widget;

import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.TextUtils;
import io.github.rosemoe.sora.util.Chars;
import io.github.rosemoe.sora.util.IntPair;
import io.github.rosemoe.sora.util.Numbers;
import io.github.rosemoe.sora.widget.layout.Layout;
import io.github.rosemoe.sora.widget.layout.Row;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'PAGE_UP' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:399)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:364)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:349)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:315)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:288)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:160)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: compiled from: SelectionMovement.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0016\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001dB/\b\u0002\u0012\u001c\u0010\u0002\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0018\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0005H\u0007R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR$\u0010\u0002\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006X\u0082\u0004¢\u0006\u0002\n\u0000j\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019j\u0002\b\u001aj\u0002\b\u001bj\u0002\b\u001c¨\u0006\u001e"}, d2 = {"Lio/github/rosemoe/sora/widget/SelectionMovement;", "", "computeFunc", "Lkotlin/Function2;", "Lio/github/rosemoe/sora/widget/CodeEditor;", "Lio/github/rosemoe/sora/text/CharPosition;", "Lio/github/rosemoe/sora/widget/SelectionMovementComputeFunc;", "basePosition", "Lio/github/rosemoe/sora/widget/SelectionMovement$MovingBasePosition;", "(Ljava/lang/String;ILkotlin/jvm/functions/Function2;Lio/github/rosemoe/sora/widget/SelectionMovement$MovingBasePosition;)V", "getBasePosition", "()Lio/github/rosemoe/sora/widget/SelectionMovement$MovingBasePosition;", "getPositionAfterMovement", "editor", "pos", "UP", "DOWN", "LEFT", "RIGHT", "PREVIOUS_WORD_BOUNDARY", "NEXT_WORD_BOUNDARY", "PAGE_UP", "PAGE_DOWN", "PAGE_TOP", "PAGE_BOTTOM", "LINE_START", "LINE_END", "TEXT_START", "TEXT_END", "MovingBasePosition", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SelectionMovement {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ SelectionMovement[] $VALUES;
    public static final SelectionMovement LINE_END;
    public static final SelectionMovement LINE_START;
    public static final SelectionMovement PAGE_BOTTOM;
    public static final SelectionMovement PAGE_DOWN;
    public static final SelectionMovement PAGE_TOP;
    public static final SelectionMovement PAGE_UP;
    public static final SelectionMovement TEXT_END;
    public static final SelectionMovement TEXT_START;
    private final MovingBasePosition basePosition;
    private final Function2<CodeEditor, CharPosition, CharPosition> computeFunc;
    public static final SelectionMovement UP = new SelectionMovement("UP", 0, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.1
        @Override // kotlin.jvm.functions.Function2
        public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
            Intrinsics.checkNotNullParameter(editor, "editor");
            Intrinsics.checkNotNullParameter(pos, "pos");
            long upPosition = editor.layout.getUpPosition(pos.line, pos.column);
            CharPosition charPosition = editor.getText().getIndexer().getCharPosition(IntPair.getFirst(upPosition), IntPair.getSecond(upPosition));
            Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
            return charPosition;
        }
    }, MovingBasePosition.LEFT_SELECTION);
    public static final SelectionMovement DOWN = new SelectionMovement("DOWN", 1, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.2
        @Override // kotlin.jvm.functions.Function2
        public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
            Intrinsics.checkNotNullParameter(editor, "editor");
            Intrinsics.checkNotNullParameter(pos, "pos");
            long downPosition = editor.layout.getDownPosition(pos.line, pos.column);
            CharPosition charPosition = editor.getText().getIndexer().getCharPosition(IntPair.getFirst(downPosition), IntPair.getSecond(downPosition));
            Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
            return charPosition;
        }
    }, MovingBasePosition.RIGHT_SELECTION);
    public static final SelectionMovement LEFT = new SelectionMovement("LEFT", 2, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.3
        @Override // kotlin.jvm.functions.Function2
        public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
            Intrinsics.checkNotNullParameter(editor, "editor");
            Intrinsics.checkNotNullParameter(pos, "pos");
            long leftOf = editor.getCursor().getLeftOf(pos.toIntPair());
            CharPosition charPosition = editor.getText().getIndexer().getCharPosition(IntPair.getFirst(leftOf), IntPair.getSecond(leftOf));
            Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
            return charPosition;
        }
    }, MovingBasePosition.LEFT_SELECTION);
    public static final SelectionMovement RIGHT = new SelectionMovement("RIGHT", 3, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.4
        @Override // kotlin.jvm.functions.Function2
        public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
            Intrinsics.checkNotNullParameter(editor, "editor");
            Intrinsics.checkNotNullParameter(pos, "pos");
            long rightOf = editor.getCursor().getRightOf(pos.toIntPair());
            CharPosition charPosition = editor.getText().getIndexer().getCharPosition(IntPair.getFirst(rightOf), IntPair.getSecond(rightOf));
            Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
            return charPosition;
        }
    }, MovingBasePosition.RIGHT_SELECTION);
    public static final SelectionMovement PREVIOUS_WORD_BOUNDARY = new SelectionMovement("PREVIOUS_WORD_BOUNDARY", 4, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.5
        @Override // kotlin.jvm.functions.Function2
        public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
            Intrinsics.checkNotNullParameter(editor, "editor");
            Intrinsics.checkNotNullParameter(pos, "pos");
            Content text = editor.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            CharPosition charPositionPrevWordStart = Chars.prevWordStart(pos, text);
            CharPosition charPosition = editor.getText().getIndexer().getCharPosition(charPositionPrevWordStart.line, charPositionPrevWordStart.column);
            Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
            return charPosition;
        }
    }, null, 2, null);
    public static final SelectionMovement NEXT_WORD_BOUNDARY = new SelectionMovement("NEXT_WORD_BOUNDARY", 5, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.6
        @Override // kotlin.jvm.functions.Function2
        public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
            Intrinsics.checkNotNullParameter(editor, "editor");
            Intrinsics.checkNotNullParameter(pos, "pos");
            Content text = editor.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            CharPosition charPositionNextWordEnd = Chars.nextWordEnd(pos, text);
            CharPosition charPosition = editor.getText().getIndexer().getCharPosition(charPositionNextWordEnd.line, charPositionNextWordEnd.column);
            Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
            return charPosition;
        }
    }, null, 2, null);

    /* JADX INFO: compiled from: SelectionMovement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lio/github/rosemoe/sora/widget/SelectionMovement$MovingBasePosition;", "", "(Ljava/lang/String;I)V", "LEFT_SELECTION", "RIGHT_SELECTION", "SELECTION_ANCHOR", "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum MovingBasePosition {
        LEFT_SELECTION,
        RIGHT_SELECTION,
        SELECTION_ANCHOR;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<MovingBasePosition> getEntries() {
            return $ENTRIES;
        }
    }

    private static final /* synthetic */ SelectionMovement[] $values() {
        return new SelectionMovement[]{UP, DOWN, LEFT, RIGHT, PREVIOUS_WORD_BOUNDARY, NEXT_WORD_BOUNDARY, PAGE_UP, PAGE_DOWN, PAGE_TOP, PAGE_BOTTOM, LINE_START, LINE_END, TEXT_START, TEXT_END};
    }

    public static EnumEntries<SelectionMovement> getEntries() {
        return $ENTRIES;
    }

    public static SelectionMovement valueOf(String str) {
        return (SelectionMovement) Enum.valueOf(SelectionMovement.class, str);
    }

    public static SelectionMovement[] values() {
        return (SelectionMovement[]) $VALUES.clone();
    }

    private SelectionMovement(String str, int i, Function2 function2, MovingBasePosition movingBasePosition) {
        super(str, i);
        this.computeFunc = function2;
        this.basePosition = movingBasePosition;
    }

    /* synthetic */ SelectionMovement(String str, int i, Function2 function2, MovingBasePosition movingBasePosition, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, i, function2, (i2 & 2) != 0 ? MovingBasePosition.SELECTION_ANCHOR : movingBasePosition);
    }

    public final MovingBasePosition getBasePosition() {
        return this.basePosition;
    }

    static {
        int i = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        MovingBasePosition movingBasePosition = null;
        PAGE_UP = new SelectionMovement("PAGE_UP", 6, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.7
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(pos, "pos");
                Layout layout = editor.layout;
                int iCeil = (int) Math.ceil(editor.getHeight() / editor.getRowHeight());
                int rowIndexForPosition = layout.getRowIndexForPosition(pos.index);
                int iCoerceIn = Numbers.coerceIn(rowIndexForPosition - iCeil, 0, layout.getRowCount() - 1);
                int i2 = pos.column - layout.getRowAt(rowIndexForPosition).startColumn;
                Row rowAt = layout.getRowAt(iCoerceIn);
                Intrinsics.checkNotNullExpressionValue(rowAt, "getRowAt(...)");
                CharPosition charPosition = editor.getText().getIndexer().getCharPosition(rowAt.lineIndex, rowAt.startColumn + Numbers.coerceIn(i2, 0, rowAt.endColumn - rowAt.startColumn));
                Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
                return charPosition;
            }
        }, movingBasePosition, i, defaultConstructorMarker);
        int i2 = 2;
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        MovingBasePosition movingBasePosition2 = null;
        PAGE_DOWN = new SelectionMovement("PAGE_DOWN", 7, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.8
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(pos, "pos");
                Layout layout = editor.layout;
                int iCeil = (int) Math.ceil(editor.getHeight() / editor.getRowHeight());
                int rowIndexForPosition = layout.getRowIndexForPosition(pos.index);
                int iCoerceIn = Numbers.coerceIn(iCeil + rowIndexForPosition, 0, layout.getRowCount() - 1);
                int i3 = pos.column - layout.getRowAt(rowIndexForPosition).startColumn;
                Row rowAt = layout.getRowAt(iCoerceIn);
                Intrinsics.checkNotNullExpressionValue(rowAt, "getRowAt(...)");
                CharPosition charPosition = editor.getText().getIndexer().getCharPosition(rowAt.lineIndex, rowAt.startColumn + Numbers.coerceIn(i3, 0, rowAt.endColumn - rowAt.startColumn));
                Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
                return charPosition;
            }
        }, movingBasePosition2, i2, defaultConstructorMarker2);
        PAGE_TOP = new SelectionMovement("PAGE_TOP", 8, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.9
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(pos, "pos");
                Layout layout = editor.layout;
                int i3 = pos.column - layout.getRowAt(layout.getRowIndexForPosition(pos.index)).startColumn;
                Row rowAt = layout.getRowAt(editor.getFirstVisibleRow());
                Intrinsics.checkNotNullExpressionValue(rowAt, "getRowAt(...)");
                CharPosition charPosition = editor.getText().getIndexer().getCharPosition(rowAt.lineIndex, rowAt.startColumn + Numbers.coerceIn(i3, 0, rowAt.endColumn - rowAt.startColumn));
                Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
                return charPosition;
            }
        }, movingBasePosition, i, defaultConstructorMarker);
        PAGE_BOTTOM = new SelectionMovement("PAGE_BOTTOM", 9, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.10
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(pos, "pos");
                Layout layout = editor.layout;
                int i3 = pos.column - layout.getRowAt(layout.getRowIndexForPosition(pos.index)).startColumn;
                Row rowAt = layout.getRowAt(editor.getLastVisibleRow());
                Intrinsics.checkNotNullExpressionValue(rowAt, "getRowAt(...)");
                CharPosition charPosition = editor.getText().getIndexer().getCharPosition(rowAt.lineIndex, rowAt.startColumn + Numbers.coerceIn(i3, 0, rowAt.endColumn - rowAt.startColumn));
                Intrinsics.checkNotNullExpressionValue(charPosition, "getCharPosition(...)");
                return charPosition;
            }
        }, movingBasePosition2, i2, defaultConstructorMarker2);
        LINE_START = new SelectionMovement("LINE_START", 10, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.11
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
                CharPosition charPosition;
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(pos, "pos");
                if (editor.getProps().enhancedHomeAndEnd) {
                    int first = IntPair.getFirst(TextUtils.findLeadingAndTrailingWhitespacePos(editor.getText().getLine(pos.line)));
                    if (pos.column != first) {
                        charPosition = editor.getText().getIndexer().getCharPosition(pos.line, first);
                    } else {
                        charPosition = editor.getText().getIndexer().getCharPosition(pos.line, 0);
                    }
                    Intrinsics.checkNotNull(charPosition);
                    return charPosition;
                }
                CharPosition charPosition2 = editor.getText().getIndexer().getCharPosition(pos.line, 0);
                Intrinsics.checkNotNull(charPosition2);
                return charPosition2;
            }
        }, movingBasePosition, i, defaultConstructorMarker);
        LINE_END = new SelectionMovement("LINE_END", 11, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.12
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor editor, CharPosition pos) {
                CharPosition charPosition;
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(pos, "pos");
                int columnCount = editor.getText().getColumnCount(pos.line);
                if (editor.getProps().enhancedHomeAndEnd) {
                    int second = IntPair.getSecond(TextUtils.findLeadingAndTrailingWhitespacePos(editor.getText().getLine(pos.line)));
                    if (pos.column != second) {
                        charPosition = editor.getText().getIndexer().getCharPosition(pos.line, second);
                    } else {
                        charPosition = editor.getText().getIndexer().getCharPosition(pos.line, columnCount);
                    }
                    Intrinsics.checkNotNull(charPosition);
                    return charPosition;
                }
                CharPosition charPosition2 = editor.getText().getIndexer().getCharPosition(pos.line, columnCount);
                Intrinsics.checkNotNull(charPosition2);
                return charPosition2;
            }
        }, movingBasePosition2, i2, defaultConstructorMarker2);
        TEXT_START = new SelectionMovement("TEXT_START", 12, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.13
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor codeEditor, CharPosition charPosition) {
                Intrinsics.checkNotNullParameter(codeEditor, "<anonymous parameter 0>");
                Intrinsics.checkNotNullParameter(charPosition, "<anonymous parameter 1>");
                CharPosition bof = new CharPosition().toBOF();
                Intrinsics.checkNotNullExpressionValue(bof, "toBOF(...)");
                return bof;
            }
        }, movingBasePosition, i, defaultConstructorMarker);
        TEXT_END = new SelectionMovement("TEXT_END", 13, new Function2<CodeEditor, CharPosition, CharPosition>() { // from class: io.github.rosemoe.sora.widget.SelectionMovement.14
            @Override // kotlin.jvm.functions.Function2
            public final CharPosition invoke(CodeEditor editor, CharPosition charPosition) {
                Intrinsics.checkNotNullParameter(editor, "editor");
                Intrinsics.checkNotNullParameter(charPosition, "<anonymous parameter 1>");
                CharPosition charPosition2 = editor.getText().getIndexer().getCharPosition(editor.getText().length());
                Intrinsics.checkNotNullExpressionValue(charPosition2, "getCharPosition(...)");
                return charPosition2;
            }
        }, movingBasePosition2, i2, defaultConstructorMarker2);
        SelectionMovement[] selectionMovementArr$values = $values();
        $VALUES = selectionMovementArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(selectionMovementArr$values);
    }

    public final CharPosition getPositionAfterMovement(CodeEditor editor, CharPosition pos) {
        Intrinsics.checkNotNullParameter(editor, "editor");
        Intrinsics.checkNotNullParameter(pos, "pos");
        return this.computeFunc.invoke(editor, pos);
    }
}
