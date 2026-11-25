package io.github.rosemoe.sora.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.github.rosemoe.sora.text.CharPosition;
import io.github.rosemoe.sora.text.Content;
import io.github.rosemoe.sora.text.ContentLine;
import io.github.rosemoe.sora.text.ICUUtils;
import io.github.rosemoe.sora.text.TextRange;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

/* JADX INFO: compiled from: Chars.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\r\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007J(\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\nH\u0007J\u0018\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\"\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\r2\b\b\u0002\u0010\t\u001a\u00020\nH\u0007¨\u0006\u0015"}, d2 = {"Lio/github/rosemoe/sora/util/Chars;", "", "()V", "findWord", "Lio/github/rosemoe/sora/text/TextRange;", "position", "Lio/github/rosemoe/sora/text/CharPosition;", "text", "Lio/github/rosemoe/sora/text/Content;", "reverse", "", "getWordRange", Constant.LINE, "", "column", "useIcu", "nextWordEnd", "prevWordStart", "skipWs", "", TypedValues.CycleType.S_WAVE_OFFSET, "editor_release"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Chars {
    public static final Chars INSTANCE = new Chars();

    @JvmStatic
    public static final TextRange findWord(CharPosition position, Content text) {
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(text, "text");
        return findWord$default(position, text, false, 4, null);
    }

    @JvmStatic
    public static final int skipWs(CharSequence text, int i) {
        Intrinsics.checkNotNullParameter(text, "text");
        return skipWs$default(text, i, false, 4, null);
    }

    private Chars() {
    }

    @JvmStatic
    public static final CharPosition prevWordStart(CharPosition position, Content text) {
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(text, "text");
        CharPosition start = findWord(position, text, true).getStart();
        Intrinsics.checkNotNullExpressionValue(start, "getStart(...)");
        return start;
    }

    @JvmStatic
    public static final CharPosition nextWordEnd(CharPosition position, Content text) {
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(text, "text");
        CharPosition end = findWord$default(position, text, false, 4, null).getEnd();
        Intrinsics.checkNotNullExpressionValue(end, "getEnd(...)");
        return end;
    }

    public static /* synthetic */ TextRange findWord$default(CharPosition charPosition, Content content, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return findWord(charPosition, content, z);
    }

    @JvmStatic
    public static final TextRange findWord(CharPosition position, Content text, boolean reverse) {
        Intrinsics.checkNotNullParameter(position, "position");
        Intrinsics.checkNotNullParameter(text, "text");
        if (reverse) {
            position.column--;
        }
        if (position.column <= 0 && position.line > 0 && reverse) {
            int i = position.line - 1;
            CharPosition charPosition = new CharPosition(i, text.getLine(i).length());
            return new TextRange(charPosition, charPosition);
        }
        if (text.getColumnCount(position.line) == position.column && position.line < text.getLineCount() - 1 && !reverse) {
            CharPosition charPosition2 = new CharPosition(position.line + 1, 0);
            return new TextRange(charPosition2, charPosition2);
        }
        ContentLine line = text.getLine(position.line);
        Intrinsics.checkNotNullExpressionValue(line, "getLine(...)");
        return getWordRange(text, position.line, skipWs(line, position.column, reverse), false);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0044  */
    @JvmStatic
    public static final TextRange getWordRange(Content text, int line, int column, boolean useIcu) {
        int i;
        int columnCount;
        Intrinsics.checkNotNullParameter(text, "text");
        ContentLine line2 = text.getLine(line);
        long wordRange = ICUUtils.getWordRange(line2, column, useIcu);
        int first = IntPair.getFirst(wordRange);
        int second = IntPair.getSecond(wordRange);
        if (first != second) {
            line = line;
            i = second;
            columnCount = first;
        } else {
            if (second < line2.length()) {
                i = second + 1;
                line = line;
            } else {
                if (first > 0) {
                    columnCount = first - 1;
                } else if (line > 0) {
                    int i2 = line - 1;
                    columnCount = text.getColumnCount(i2);
                    line = i2;
                } else if (line < text.getLineCount() - 1) {
                    i = 0;
                    line++;
                } else {
                    line = line;
                    i = second;
                }
                i = second;
            }
            columnCount = first;
        }
        return new TextRange(new CharPosition(line, columnCount, first), new CharPosition(line, i, second));
    }

    public static /* synthetic */ int skipWs$default(CharSequence charSequence, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return skipWs(charSequence, i, z);
    }

    @JvmStatic
    public static final int skipWs(CharSequence text, int offset, boolean reverse) {
        Intrinsics.checkNotNullParameter(text, "text");
        while (true) {
            if ((reverse && offset < 0) || ((!reverse && offset == text.length()) || !CharsKt.isWhitespace(text.charAt(offset)) || (offset == 0 && reverse))) {
                break;
            }
            offset += reverse ? -1 : 1;
        }
        return offset;
    }
}
