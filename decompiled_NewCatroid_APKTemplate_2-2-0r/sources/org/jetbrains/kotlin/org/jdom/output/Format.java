package org.jetbrains.kotlin.org.jdom.output;

import io.netty.util.internal.StringUtil;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Locale;
import org.apache.commons.lang3.CharEncoding;
import org.jetbrains.kotlin.org.jdom.Verifier;

/* JADX INFO: loaded from: classes4.dex */
public class Format implements Cloneable {
    private static final EscapeStrategy Bits7EscapeStrategy;
    private static final EscapeStrategy Bits8EscapeStrategy;
    private static final EscapeStrategy DefaultEscapeStrategy = new EscapeStrategy() { // from class: org.jetbrains.kotlin.org.jdom.output.Format.1
        @Override // org.jetbrains.kotlin.org.jdom.output.EscapeStrategy
        public boolean shouldEscape(char c) {
            return Verifier.isHighSurrogate(c);
        }
    };
    private static final String STANDARD_LINE_SEPARATOR = LineSeparator.DEFAULT.value();
    private static final EscapeStrategy UTFEscapeStrategy;
    String indent = null;
    String lineSeparator = STANDARD_LINE_SEPARATOR;
    String encoding = "UTF-8";
    boolean omitDeclaration = false;
    boolean omitEncoding = false;
    boolean specifiedAttributesOnly = false;
    boolean expandEmptyElements = false;
    boolean ignoreTrAXEscapingPIs = false;
    TextMode mode = TextMode.PRESERVE;
    EscapeStrategy escapeStrategy = DefaultEscapeStrategy;

    public enum TextMode {
        PRESERVE,
        TRIM,
        NORMALIZE,
        TRIM_FULL_WHITE
    }

    private static final class EscapeStrategyUTF implements EscapeStrategy {
        private EscapeStrategyUTF() {
        }

        @Override // org.jetbrains.kotlin.org.jdom.output.EscapeStrategy
        public final boolean shouldEscape(char c) {
            return Verifier.isHighSurrogate(c);
        }
    }

    static {
        UTFEscapeStrategy = new EscapeStrategyUTF();
        Bits8EscapeStrategy = new EscapeStrategy8Bits();
        Bits7EscapeStrategy = new EscapeStrategy7Bits();
    }

    private static final class EscapeStrategy8Bits implements EscapeStrategy {
        @Override // org.jetbrains.kotlin.org.jdom.output.EscapeStrategy
        public boolean shouldEscape(char c) {
            return (c >>> '\b') != 0;
        }

        private EscapeStrategy8Bits() {
        }
    }

    private static final class EscapeStrategy7Bits implements EscapeStrategy {
        @Override // org.jetbrains.kotlin.org.jdom.output.EscapeStrategy
        public boolean shouldEscape(char c) {
            return (c >>> 7) != 0;
        }

        private EscapeStrategy7Bits() {
        }
    }

    private static final class DefaultCharsetEscapeStrategy implements EscapeStrategy {
        private final CharsetEncoder encoder;

        public DefaultCharsetEscapeStrategy(CharsetEncoder charsetEncoder) {
            this.encoder = charsetEncoder;
        }

        @Override // org.jetbrains.kotlin.org.jdom.output.EscapeStrategy
        public boolean shouldEscape(char c) {
            if (Verifier.isHighSurrogate(c)) {
                return true;
            }
            return !this.encoder.canEncode(c);
        }
    }

    public static Format getRawFormat() {
        return new Format();
    }

    public static final String compact(String str) {
        int length = str.length() - 1;
        int i = 0;
        while (i <= length && Verifier.isXMLWhitespace(str.charAt(i))) {
            i++;
        }
        while (length > i && Verifier.isXMLWhitespace(str.charAt(length))) {
            length--;
        }
        if (i > length) {
            return "";
        }
        StringBuilder sb = new StringBuilder((length - i) + 1);
        boolean z = true;
        while (i <= length) {
            char cCharAt = str.charAt(i);
            if (!Verifier.isXMLWhitespace(cCharAt)) {
                sb.append(cCharAt);
                z = true;
            } else if (z) {
                sb.append(StringUtil.SPACE);
                z = false;
            }
            i++;
        }
        return sb.toString();
    }

    private static final EscapeStrategy chooseStrategy(String str) {
        if ("UTF-8".equalsIgnoreCase(str) || CharEncoding.UTF_16.equalsIgnoreCase(str)) {
            return UTFEscapeStrategy;
        }
        if (str.toUpperCase(Locale.ENGLISH).startsWith("ISO-8859-") || "Latin1".equalsIgnoreCase(str)) {
            return Bits8EscapeStrategy;
        }
        if (CharEncoding.US_ASCII.equalsIgnoreCase(str) || "ASCII".equalsIgnoreCase(str)) {
            return Bits7EscapeStrategy;
        }
        try {
            return new DefaultCharsetEscapeStrategy(Charset.forName(str).newEncoder());
        } catch (Exception unused) {
            return DefaultEscapeStrategy;
        }
    }

    private Format() {
        setEncoding("UTF-8");
    }

    public EscapeStrategy getEscapeStrategy() {
        return this.escapeStrategy;
    }

    public String getLineSeparator() {
        return this.lineSeparator;
    }

    public boolean getOmitEncoding() {
        return this.omitEncoding;
    }

    public boolean getOmitDeclaration() {
        return this.omitDeclaration;
    }

    public boolean getExpandEmptyElements() {
        return this.expandEmptyElements;
    }

    public void setIgnoreTrAXEscapingPIs(boolean z) {
        this.ignoreTrAXEscapingPIs = z;
    }

    public boolean getIgnoreTrAXEscapingPIs() {
        return this.ignoreTrAXEscapingPIs;
    }

    public TextMode getTextMode() {
        return this.mode;
    }

    public String getIndent() {
        return this.indent;
    }

    public Format setEncoding(String str) {
        this.encoding = str;
        this.escapeStrategy = chooseStrategy(str);
        return this;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public boolean isSpecifiedAttributesOnly() {
        return this.specifiedAttributesOnly;
    }

    public Format clone() {
        try {
            return (Format) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
