package org.jetbrains.kotlin.org.jline.utils;

import io.netty.util.internal.StringUtil;
import java.io.IOError;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;

/* JADX INFO: loaded from: classes4.dex */
public class Display {
    protected final boolean canScroll;
    private int columns;
    private int columns1;
    protected final boolean cursorDownIsNewLine;
    protected int cursorPos;
    protected boolean delayLineWrap;
    protected final boolean delayedWrapAtEol;
    protected final boolean fullScreen;
    protected boolean reset;
    protected int rows;
    protected final Terminal terminal;
    protected final boolean wrapAtEol;
    protected List<AttributedString> oldLines = Collections.emptyList();
    protected final Map<InfoCmp.Capability, Integer> cost = new HashMap();

    public Display(Terminal terminal, boolean z) {
        this.terminal = terminal;
        this.fullScreen = z;
        this.canScroll = can(InfoCmp.Capability.insert_line, InfoCmp.Capability.parm_insert_line) && can(InfoCmp.Capability.delete_line, InfoCmp.Capability.parm_delete_line);
        boolean booleanCapability = terminal.getBooleanCapability(InfoCmp.Capability.auto_right_margin);
        this.wrapAtEol = booleanCapability;
        this.delayedWrapAtEol = booleanCapability && terminal.getBooleanCapability(InfoCmp.Capability.eat_newline_glitch);
        this.cursorDownIsNewLine = "\n".equals(tput(InfoCmp.Capability.cursor_down, new Object[0]));
    }

    public boolean delayLineWrap() {
        return this.delayLineWrap;
    }

    public void setDelayLineWrap(boolean z) {
        this.delayLineWrap = z;
    }

    public void resize(int i, int i2) {
        if (this.rows == i && this.columns == i2) {
            return;
        }
        this.rows = i;
        this.columns = i2;
        this.columns1 = i2 + 1;
        this.oldLines = AttributedString.join(AttributedString.EMPTY, this.oldLines).columnSplitLength(i2, true, delayLineWrap());
    }

    public void reset() {
        this.oldLines = Collections.emptyList();
    }

    public void update(List<AttributedString> list, int i) {
        update(list, i, true);
    }

    public void update(List<AttributedString> list, int i, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = 0;
        if (this.reset) {
            this.terminal.puts(InfoCmp.Capability.clear_screen, new Object[0]);
            this.oldLines.clear();
            this.cursorPos = 0;
            this.reset = false;
        }
        Integer numericCapability = this.terminal.getNumericCapability(InfoCmp.Capability.max_colors);
        List<AttributedString> list2 = (numericCapability == null || numericCapability.intValue() < 8) ? (List) list.stream().map(new Function() { // from class: org.jetbrains.kotlin.org.jline.utils.Display$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Display.lambda$update$0((AttributedString) obj);
            }
        }).collect(Collectors.toList()) : list;
        int i10 = 1;
        if ((this.fullScreen || list2.size() >= this.rows) && list2.size() == this.oldLines.size() && this.canScroll) {
            int size = list2.size();
            int i11 = 0;
            while (i11 < size && Objects.equals(list2.get(i11), this.oldLines.get(i11))) {
                i11++;
            }
            int i12 = 0;
            while (i12 < (size - i11) - 1) {
                AttributedString attributedString = list2.get((list2.size() - i12) - 1);
                List<AttributedString> list3 = this.oldLines;
                if (!Objects.equals(attributedString, list3.get((list3.size() - i12) - 1))) {
                    break;
                } else {
                    i12++;
                }
            }
            List<AttributedString> listSubList = list2.subList(i11, list2.size() - i12);
            List<AttributedString> list4 = this.oldLines;
            int[] iArrLongestCommon = longestCommon(listSubList, list4.subList(i11, list4.size() - i12));
            if (iArrLongestCommon != null) {
                int i13 = iArrLongestCommon[0];
                int i14 = iArrLongestCommon[1];
                int i15 = iArrLongestCommon[2];
                if (i15 > 1 && i13 < i14) {
                    int i16 = i11 + i13;
                    moveVisualCursorTo(this.columns1 * i16);
                    int i17 = i14 - i13;
                    deleteLines(i17);
                    for (int i18 = 0; i18 < i17; i18++) {
                        this.oldLines.remove(i16);
                    }
                    if (i12 > 0) {
                        int i19 = i16 + i15;
                        moveVisualCursorTo(this.columns1 * i19);
                        insertLines(i17);
                        for (int i20 = 0; i20 < i17; i20++) {
                            this.oldLines.add(i19, new AttributedString(""));
                        }
                    }
                } else if (i15 > 1 && i13 > i14) {
                    int i21 = i13 - i14;
                    if (i12 > 0) {
                        int i22 = i11 + i14 + i15;
                        moveVisualCursorTo(this.columns1 * i22);
                        deleteLines(i21);
                        for (int i23 = 0; i23 < i21; i23++) {
                            this.oldLines.remove(i22);
                        }
                    }
                    int i24 = i11 + i14;
                    moveVisualCursorTo(this.columns1 * i24);
                    insertLines(i21);
                    for (int i25 = 0; i25 < i21; i25++) {
                        this.oldLines.add(i24, new AttributedString(""));
                    }
                }
            }
        }
        int iMax = Math.max(this.oldLines.size(), list2.size());
        int i26 = i;
        int i27 = 0;
        int i28 = 0;
        int i29 = 0;
        while (i27 < iMax) {
            AttributedString attributedStringSubstring = i27 < this.oldLines.size() ? this.oldLines.get(i27) : AttributedString.NEWLINE;
            AttributedString attributedStringSubstring2 = i27 < list2.size() ? list2.get(i27) : AttributedString.NEWLINE;
            int i30 = this.columns1 * i27;
            int length = attributedStringSubstring.length();
            int length2 = attributedStringSubstring2.length();
            int i31 = (length <= 0 || attributedStringSubstring.charAt(length + (-1)) != '\n') ? i9 : i10;
            int i32 = (length2 <= 0 || attributedStringSubstring2.charAt(length2 + (-1)) != '\n') ? i9 : i10;
            if (i31 != 0) {
                length--;
                attributedStringSubstring = attributedStringSubstring.substring(i9, length);
            }
            if (i32 != 0) {
                length2--;
                attributedStringSubstring2 = attributedStringSubstring2.substring(i9, length2);
            }
            String str = " \b";
            if (i29 == 0 || i27 != (this.cursorPos + i10) / this.columns1 || i27 >= list2.size()) {
                i2 = i30;
            } else {
                this.cursorPos += i10;
                if (length2 == 0 || attributedStringSubstring2.isHidden(0)) {
                    rawPrint(new AttributedString(" \b"));
                    i2 = i30;
                } else {
                    AttributedString attributedStringColumnSubSequence = attributedStringSubstring2.columnSubSequence(0, i10);
                    rawPrint(attributedStringColumnSubSequence);
                    this.cursorPos += i10;
                    int length3 = attributedStringColumnSubSequence.length();
                    attributedStringSubstring2 = attributedStringSubstring2.substring(length3, length2);
                    if (length >= length3) {
                        attributedStringSubstring = attributedStringSubstring.substring(length3, length);
                    }
                    i2 = this.cursorPos;
                }
            }
            List<DiffHelper.Diff> listDiff = DiffHelper.diff(attributedStringSubstring, attributedStringSubstring2);
            int i33 = i10;
            int i34 = 0;
            boolean z2 = false;
            while (i34 < listDiff.size()) {
                DiffHelper.Diff diff = listDiff.get(i34);
                int i35 = iMax;
                int iColumnLength = diff.text.columnLength();
                String str2 = str;
                int i36 = AnonymousClass1.$SwitchMap$org$jline$utils$DiffHelper$Operation[diff.operation.ordinal()];
                int i37 = i31;
                if (i36 != 1) {
                    if (i36 == 2) {
                        i6 = i30;
                        if (i34 <= listDiff.size() - 2 && listDiff.get(i34 + 1).operation == DiffHelper.Operation.EQUAL) {
                            this.cursorPos = moveVisualCursorTo(i2);
                            if (insertChars(iColumnLength)) {
                                rawPrint(diff.text);
                                i2 = this.cursorPos + iColumnLength;
                                this.cursorPos = i2;
                            } else {
                                moveVisualCursorTo(i2);
                                rawPrint(diff.text);
                                i2 = this.cursorPos + iColumnLength;
                                this.cursorPos = i2;
                                i7 = 1;
                                i33 = 0;
                            }
                        } else {
                            if (i34 <= listDiff.size() - 2) {
                                int i38 = i34 + 1;
                                if (listDiff.get(i38).operation == DiffHelper.Operation.DELETE && iColumnLength == listDiff.get(i38).text.columnLength()) {
                                    moveVisualCursorTo(i2);
                                    rawPrint(diff.text);
                                    i2 = this.cursorPos + iColumnLength;
                                    this.cursorPos = i2;
                                    i34 = i38;
                                }
                            }
                            moveVisualCursorTo(i2);
                            rawPrint(diff.text);
                            i2 = this.cursorPos + iColumnLength;
                            this.cursorPos = i2;
                            i7 = 1;
                            i33 = 0;
                        }
                    } else if (i36 == 3 && !z2 && (i8 = i2 - i30) < this.columns) {
                        if (i34 <= listDiff.size() - 2) {
                            int i39 = i34 + 1;
                            i6 = i30;
                            if (listDiff.get(i39).operation == DiffHelper.Operation.EQUAL && listDiff.get(i39).text.columnLength() + i2 < this.columns) {
                                moveVisualCursorTo(i2);
                                if (deleteChars(iColumnLength)) {
                                }
                            }
                        } else {
                            i6 = i30;
                        }
                        int iMax2 = Math.max(attributedStringSubstring.columnLength(), attributedStringSubstring2.columnLength()) - i8;
                        moveVisualCursorTo(i2);
                        if (!this.terminal.puts(InfoCmp.Capability.clr_eol, new Object[0])) {
                            rawPrint(StringUtil.SPACE, iMax2);
                            this.cursorPos += iMax2;
                        }
                        i7 = 1;
                        i33 = 0;
                        z2 = true;
                    } else {
                        i6 = i30;
                    }
                    i34 += i7;
                    str = str2;
                    iMax = i35;
                    i31 = i37;
                    i30 = i6;
                } else {
                    i6 = i30;
                    if (i33 == 0) {
                        this.cursorPos = moveVisualCursorTo(i2);
                        rawPrint(diff.text);
                        i2 = this.cursorPos + iColumnLength;
                        this.cursorPos = i2;
                    } else {
                        i2 += iColumnLength;
                    }
                }
                i7 = 1;
                i34 += i7;
                str = str2;
                iMax = i35;
                i31 = i37;
                i30 = i6;
            }
            int i40 = iMax;
            int i41 = i30;
            String str3 = str;
            int i42 = i31;
            i27++;
            boolean z3 = i32 == 0 && i27 < list2.size();
            int i43 = i26 + 1;
            int i44 = this.columns1;
            if (i43 == i27 * i44 && (z3 || !this.delayLineWrap)) {
                i26 = i43;
            }
            boolean z4 = (this.cursorPos - i41) % i44 == this.columns;
            if (this.delayedWrapAtEol) {
                boolean z5 = i42 == 0 && i27 < this.oldLines.size();
                if (z3 == z5 || (z5 && z2)) {
                    i5 = 0;
                } else {
                    moveVisualCursorTo((this.columns1 * i27) - 1, list2);
                    if (z3) {
                        i5 = 1;
                    } else {
                        this.terminal.puts(InfoCmp.Capability.clr_eol, new Object[0]);
                        i5 = 0;
                    }
                }
                i28 = i2;
                i29 = i5;
                i3 = 1;
                i4 = 0;
            } else {
                if (z4) {
                    if (this.wrapAtEol) {
                        this.terminal.writer().write(str3);
                        i3 = 1;
                        this.cursorPos++;
                        i4 = 0;
                    } else {
                        i3 = 1;
                        i4 = 0;
                        this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
                        this.cursorPos = i41;
                    }
                    i2 = this.cursorPos;
                } else {
                    i3 = 1;
                    i4 = 0;
                }
                i28 = i2;
                i29 = i4;
            }
            i9 = i4;
            i10 = i3;
            iMax = i40;
        }
        if (this.cursorPos != i26) {
            if (i26 < 0) {
                i26 = i28;
            }
            moveVisualCursorTo(i26, list2);
        }
        this.oldLines = list2;
        if (z) {
            this.terminal.flush();
        }
    }

    static /* synthetic */ AttributedString lambda$update$0(AttributedString attributedString) {
        return new AttributedString(attributedString.toString());
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.org.jline.utils.Display$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jline$utils$DiffHelper$Operation;

        static {
            int[] iArr = new int[DiffHelper.Operation.values().length];
            $SwitchMap$org$jline$utils$DiffHelper$Operation = iArr;
            try {
                iArr[DiffHelper.Operation.EQUAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jline$utils$DiffHelper$Operation[DiffHelper.Operation.INSERT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jline$utils$DiffHelper$Operation[DiffHelper.Operation.DELETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected boolean deleteLines(int i) {
        return perform(InfoCmp.Capability.delete_line, InfoCmp.Capability.parm_delete_line, i);
    }

    protected boolean insertLines(int i) {
        return perform(InfoCmp.Capability.insert_line, InfoCmp.Capability.parm_insert_line, i);
    }

    protected boolean insertChars(int i) {
        return perform(InfoCmp.Capability.insert_character, InfoCmp.Capability.parm_ich, i);
    }

    protected boolean deleteChars(int i) {
        return perform(InfoCmp.Capability.delete_character, InfoCmp.Capability.parm_dch, i);
    }

    protected boolean can(InfoCmp.Capability capability, InfoCmp.Capability capability2) {
        return (this.terminal.getStringCapability(capability) == null && this.terminal.getStringCapability(capability2) == null) ? false : true;
    }

    protected boolean perform(InfoCmp.Capability capability, InfoCmp.Capability capability2, int i) {
        boolean z = this.terminal.getStringCapability(capability2) != null;
        boolean z2 = this.terminal.getStringCapability(capability) != null;
        if (z && (!z2 || cost(capability) * i > cost(capability2))) {
            this.terminal.puts(capability2, Integer.valueOf(i));
            return true;
        }
        if (!z2) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            this.terminal.puts(capability, new Object[0]);
        }
        return true;
    }

    private int cost(InfoCmp.Capability capability) {
        return this.cost.computeIfAbsent(capability, new Function() { // from class: org.jetbrains.kotlin.org.jline.utils.Display$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(this.f$0.computeCost((InfoCmp.Capability) obj));
            }
        }).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int computeCost(InfoCmp.Capability capability) {
        String strTput = tput(capability, 0);
        if (strTput != null) {
            return strTput.length();
        }
        return Integer.MAX_VALUE;
    }

    private String tput(InfoCmp.Capability capability, Object... objArr) {
        try {
            StringWriter stringWriter = new StringWriter();
            String stringCapability = this.terminal.getStringCapability(capability);
            if (stringCapability == null) {
                return null;
            }
            Curses.tputs(stringWriter, stringCapability, objArr);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    private static int[] longestCommon(List<AttributedString> list, List<AttributedString> list2) {
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            for (int i5 = 0; i5 < list2.size(); i5++) {
                int i6 = 0;
                while (Objects.equals(list.get(i4 + i6), list2.get(i5 + i6))) {
                    i6++;
                    if (i4 + i6 >= list.size() || i5 + i6 >= list2.size()) {
                        break;
                    }
                }
                if (i6 > i) {
                    i2 = i4;
                    i3 = i5;
                    i = i6;
                }
            }
        }
        if (i != 0) {
            return new int[]{i2, i3, i};
        }
        return null;
    }

    protected void moveVisualCursorTo(int i, List<AttributedString> list) {
        AttributedString attributedStringColumnSubSequence;
        if (this.cursorPos != i) {
            int i2 = i % this.columns1 == this.columns ? 1 : 0;
            moveVisualCursorTo(i - i2);
            if (i2 != 0) {
                int i3 = i / this.columns1;
                if (i3 >= list.size()) {
                    attributedStringColumnSubSequence = AttributedString.EMPTY;
                } else {
                    AttributedString attributedString = list.get(i3);
                    int i4 = this.columns;
                    attributedStringColumnSubSequence = attributedString.columnSubSequence(i4 - 1, i4);
                }
                if (attributedStringColumnSubSequence.length() == 0) {
                    rawPrint(32);
                } else {
                    rawPrint(attributedStringColumnSubSequence);
                }
                this.cursorPos++;
            }
        }
    }

    protected int moveVisualCursorTo(int i) {
        int i2 = this.cursorPos;
        if (i2 == i) {
            return i;
        }
        int i3 = this.columns1;
        int i4 = i2 / i3;
        int i5 = i2 % i3;
        int i6 = i / i3;
        int i7 = i % i3;
        if (i5 == this.columns) {
            this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
            i5 = 0;
        }
        if (i4 > i6) {
            perform(InfoCmp.Capability.cursor_up, InfoCmp.Capability.parm_up_cursor, i4 - i6);
        } else if (i4 < i6) {
            if (this.fullScreen) {
                if (!this.terminal.puts(InfoCmp.Capability.parm_down_cursor, Integer.valueOf(i6 - i4))) {
                    while (i4 < i6) {
                        this.terminal.puts(InfoCmp.Capability.cursor_down, new Object[0]);
                        i4++;
                    }
                    if (this.cursorDownIsNewLine) {
                    }
                }
            } else {
                this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
                rawPrint('\n', i6 - i4);
            }
            i5 = 0;
        }
        if (i5 != 0 && i7 == 0) {
            this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
        } else if (i5 < i7) {
            perform(InfoCmp.Capability.cursor_right, InfoCmp.Capability.parm_right_cursor, i7 - i5);
        } else if (i5 > i7) {
            perform(InfoCmp.Capability.cursor_left, InfoCmp.Capability.parm_left_cursor, i5 - i7);
        }
        this.cursorPos = i;
        return i;
    }

    void rawPrint(char c, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            rawPrint(c);
        }
    }

    void rawPrint(int i) {
        this.terminal.writer().write(i);
    }

    void rawPrint(AttributedString attributedString) {
        this.terminal.writer().write(attributedString.toAnsi(this.terminal));
    }

    public int wcwidth(String str) {
        return AttributedString.fromAnsi(str).columnLength();
    }
}
