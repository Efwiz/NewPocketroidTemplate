package org.jetbrains.kotlin.org.fusesource.jansi;

import java.io.IOException;
import java.util.Locale;

/* JADX INFO: loaded from: classes4.dex */
public class AnsiRenderer {
    public static final String BEGIN_TOKEN = "@|";
    private static final int BEGIN_TOKEN_LEN = 2;
    public static final String CODE_LIST_SEPARATOR = ",";
    public static final String CODE_TEXT_SEPARATOR = " ";
    public static final String END_TOKEN = "|@";
    private static final int END_TOKEN_LEN = 2;

    public static String render(String str) throws IllegalArgumentException {
        try {
            return render(str, new StringBuilder()).toString();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Appendable render(String str, Appendable appendable) throws IOException {
        int i = 0;
        while (true) {
            int iIndexOf = str.indexOf(BEGIN_TOKEN, i);
            if (iIndexOf == -1) {
                if (i == 0) {
                    appendable.append(str);
                    return appendable;
                }
                appendable.append(str.substring(i, str.length()));
                return appendable;
            }
            appendable.append(str.substring(i, iIndexOf));
            int iIndexOf2 = str.indexOf(END_TOKEN, iIndexOf);
            if (iIndexOf2 == -1) {
                appendable.append(str);
                return appendable;
            }
            String[] strArrSplit = str.substring(iIndexOf + 2, iIndexOf2).split(" ", 2);
            if (strArrSplit.length == 1) {
                appendable.append(str);
                return appendable;
            }
            appendable.append(render(strArrSplit[1], strArrSplit[0].split(",")));
            i = iIndexOf2 + 2;
        }
    }

    public static String render(String str, String... strArr) {
        return render(Ansi.ansi(), strArr).a(str).reset().toString();
    }

    public static String renderCodes(String... strArr) {
        return render(Ansi.ansi(), strArr).toString();
    }

    public static String renderCodes(String str) {
        return renderCodes(str.split("\\s"));
    }

    private static Ansi render(Ansi ansi, String... strArr) {
        for (String str : strArr) {
            render(ansi, str);
        }
        return ansi;
    }

    private static Ansi render(Ansi ansi, String str) {
        Code codeValueOf = Code.valueOf(str.toUpperCase(Locale.ENGLISH));
        if (codeValueOf.isColor()) {
            if (codeValueOf.isBackground()) {
                ansi.bg(codeValueOf.getColor());
            } else {
                ansi.fg(codeValueOf.getColor());
            }
        } else if (codeValueOf.isAttribute()) {
            ansi.a(codeValueOf.getAttribute());
        }
        return ansi;
    }

    public static boolean test(String str) {
        return str != null && str.contains(BEGIN_TOKEN);
    }

    public enum Code {
        BLACK(Ansi.Color.BLACK),
        RED(Ansi.Color.RED),
        GREEN(Ansi.Color.GREEN),
        YELLOW(Ansi.Color.YELLOW),
        BLUE(Ansi.Color.BLUE),
        MAGENTA(Ansi.Color.MAGENTA),
        CYAN(Ansi.Color.CYAN),
        WHITE(Ansi.Color.WHITE),
        FG_BLACK(Ansi.Color.BLACK, false),
        FG_RED(Ansi.Color.RED, false),
        FG_GREEN(Ansi.Color.GREEN, false),
        FG_YELLOW(Ansi.Color.YELLOW, false),
        FG_BLUE(Ansi.Color.BLUE, false),
        FG_MAGENTA(Ansi.Color.MAGENTA, false),
        FG_CYAN(Ansi.Color.CYAN, false),
        FG_WHITE(Ansi.Color.WHITE, false),
        BG_BLACK(Ansi.Color.BLACK, true),
        BG_RED(Ansi.Color.RED, true),
        BG_GREEN(Ansi.Color.GREEN, true),
        BG_YELLOW(Ansi.Color.YELLOW, true),
        BG_BLUE(Ansi.Color.BLUE, true),
        BG_MAGENTA(Ansi.Color.MAGENTA, true),
        BG_CYAN(Ansi.Color.CYAN, true),
        BG_WHITE(Ansi.Color.WHITE, true),
        RESET(Ansi.Attribute.RESET),
        INTENSITY_BOLD(Ansi.Attribute.INTENSITY_BOLD),
        INTENSITY_FAINT(Ansi.Attribute.INTENSITY_FAINT),
        ITALIC(Ansi.Attribute.ITALIC),
        UNDERLINE(Ansi.Attribute.UNDERLINE),
        BLINK_SLOW(Ansi.Attribute.BLINK_SLOW),
        BLINK_FAST(Ansi.Attribute.BLINK_FAST),
        BLINK_OFF(Ansi.Attribute.BLINK_OFF),
        NEGATIVE_ON(Ansi.Attribute.NEGATIVE_ON),
        NEGATIVE_OFF(Ansi.Attribute.NEGATIVE_OFF),
        CONCEAL_ON(Ansi.Attribute.CONCEAL_ON),
        CONCEAL_OFF(Ansi.Attribute.CONCEAL_OFF),
        UNDERLINE_DOUBLE(Ansi.Attribute.UNDERLINE_DOUBLE),
        UNDERLINE_OFF(Ansi.Attribute.UNDERLINE_OFF),
        BOLD(Ansi.Attribute.INTENSITY_BOLD),
        FAINT(Ansi.Attribute.INTENSITY_FAINT);

        private final boolean background;
        private final Enum n;

        Code(Enum r3, boolean z) {
            this.n = r3;
            this.background = z;
        }

        Code(Enum r4) {
            this(r4, false);
        }

        public boolean isColor() {
            return this.n instanceof Ansi.Color;
        }

        public Ansi.Color getColor() {
            return (Ansi.Color) this.n;
        }

        public boolean isAttribute() {
            return this.n instanceof Ansi.Attribute;
        }

        public Ansi.Attribute getAttribute() {
            return (Ansi.Attribute) this.n;
        }

        public boolean isBackground() {
            return this.background;
        }
    }

    private AnsiRenderer() {
    }
}
