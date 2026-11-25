package org.jetbrains.kotlin.org.jline.reader.impl;

import java.util.function.Function;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.org.jline.reader.Highlighter;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.org.jline.utils.AttributedString;
import org.jetbrains.kotlin.org.jline.utils.AttributedStringBuilder;
import org.jetbrains.kotlin.org.jline.utils.AttributedStyle;
import org.jetbrains.kotlin.org.jline.utils.WCWidth;

/* JADX INFO: loaded from: classes4.dex */
public class DefaultHighlighter implements Highlighter {
    @Override // org.jetbrains.kotlin.org.jline.reader.Highlighter
    public AttributedString highlight(LineReader lineReader, String str) {
        int length;
        int iIndexOf;
        int iCursor;
        String searchTerm = lineReader.getSearchTerm();
        int regionMark = -1;
        if (searchTerm == null || searchTerm.length() <= 0) {
            length = -1;
            iIndexOf = -1;
        } else {
            iIndexOf = str.indexOf(searchTerm);
            length = iIndexOf >= 0 ? (searchTerm.length() + iIndexOf) - 1 : -1;
        }
        if (lineReader.getRegionActive() != LineReader.RegionType.NONE) {
            regionMark = lineReader.getRegionMark();
            iCursor = lineReader.getBuffer().cursor();
            if (regionMark > iCursor) {
                iCursor = regionMark;
                regionMark = iCursor;
            }
            if (lineReader.getRegionActive() == LineReader.RegionType.LINE) {
                while (regionMark > 0 && lineReader.getBuffer().atChar(regionMark - 1) != 10) {
                    regionMark--;
                }
                while (iCursor < lineReader.getBuffer().length() - 1) {
                    int i = iCursor + 1;
                    if (lineReader.getBuffer().atChar(i) == 10) {
                        break;
                    }
                    iCursor = i;
                }
            }
        } else {
            iCursor = -1;
        }
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (i2 == iIndexOf) {
                attributedStringBuilder.style(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.DefaultHighlighter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((AttributedStyle) obj).underline();
                    }
                });
            }
            if (i2 == regionMark) {
                attributedStringBuilder.style(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.DefaultHighlighter$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((AttributedStyle) obj).inverse();
                    }
                });
            }
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\t' || cCharAt == '\n') {
                attributedStringBuilder.append(cCharAt);
            } else if (cCharAt < ' ') {
                attributedStringBuilder.style(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.DefaultHighlighter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((AttributedStyle) obj).inverseNeg();
                    }
                }).append(Util.C_EXCEPTION_START).append((char) (cCharAt + '@')).style(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.DefaultHighlighter$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((AttributedStyle) obj).inverseNeg();
                    }
                });
            } else if (WCWidth.wcwidth(cCharAt) > 0) {
                attributedStringBuilder.append(cCharAt);
            }
            if (i2 == length) {
                attributedStringBuilder.style(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.DefaultHighlighter$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((AttributedStyle) obj).underlineOff();
                    }
                });
            }
            if (i2 == iCursor) {
                attributedStringBuilder.style(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.DefaultHighlighter$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((AttributedStyle) obj).inverseOff();
                    }
                });
            }
        }
        return attributedStringBuilder.toAttributedString();
    }
}
