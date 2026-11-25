package org.jetbrains.kotlin.org.fusesource.jansi;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.SystemProperties;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes4.dex */
public class Ansi {
    private static final char FIRST_ESC_CHAR = 27;
    private static final char SECOND_ESC_CHAR = '[';
    private final ArrayList<Integer> attributeOptions;
    private final StringBuilder builder;
    public static final String DISABLE = Ansi.class.getName() + ".disable";
    private static Callable<Boolean> detector = new Callable<Boolean>() { // from class: org.jetbrains.kotlin.org.fusesource.jansi.Ansi.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.concurrent.Callable
        public Boolean call() throws Exception {
            return Boolean.valueOf(!Boolean.getBoolean(Ansi.DISABLE));
        }
    };
    private static final InheritableThreadLocal<Boolean> holder = new InheritableThreadLocal<Boolean>() { // from class: org.jetbrains.kotlin.org.fusesource.jansi.Ansi.2
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public Boolean initialValue() {
            return Boolean.valueOf(Ansi.isDetected());
        }
    };

    public enum Color {
        BLACK(0, "BLACK"),
        RED(1, "RED"),
        GREEN(2, "GREEN"),
        YELLOW(3, "YELLOW"),
        BLUE(4, "BLUE"),
        MAGENTA(5, "MAGENTA"),
        CYAN(6, "CYAN"),
        WHITE(7, "WHITE"),
        DEFAULT(9, "DEFAULT");


        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f930name;
        private final int value;

        Color(int i, String str) {
            this.value = i;
            this.f930name = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.f930name;
        }

        public int value() {
            return this.value;
        }

        public int fg() {
            return this.value + 30;
        }

        public int bg() {
            return this.value + 40;
        }

        public int fgBright() {
            return this.value + 90;
        }

        public int bgBright() {
            return this.value + 100;
        }
    }

    public enum Attribute {
        RESET(0, "RESET"),
        INTENSITY_BOLD(1, "INTENSITY_BOLD"),
        INTENSITY_FAINT(2, "INTENSITY_FAINT"),
        ITALIC(3, "ITALIC_ON"),
        UNDERLINE(4, "UNDERLINE_ON"),
        BLINK_SLOW(5, "BLINK_SLOW"),
        BLINK_FAST(6, "BLINK_FAST"),
        NEGATIVE_ON(7, "NEGATIVE_ON"),
        CONCEAL_ON(8, "CONCEAL_ON"),
        STRIKETHROUGH_ON(9, "STRIKETHROUGH_ON"),
        UNDERLINE_DOUBLE(21, "UNDERLINE_DOUBLE"),
        INTENSITY_BOLD_OFF(22, "INTENSITY_BOLD_OFF"),
        ITALIC_OFF(23, "ITALIC_OFF"),
        UNDERLINE_OFF(24, "UNDERLINE_OFF"),
        BLINK_OFF(25, "BLINK_OFF"),
        NEGATIVE_OFF(27, "NEGATIVE_OFF"),
        CONCEAL_OFF(28, "CONCEAL_OFF"),
        STRIKETHROUGH_OFF(29, "STRIKETHROUGH_OFF");


        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f929name;
        private final int value;

        Attribute(int i, String str) {
            this.value = i;
            this.f929name = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.f929name;
        }

        public int value() {
            return this.value;
        }
    }

    public enum Erase {
        FORWARD(0, "FORWARD"),
        BACKWARD(1, "BACKWARD"),
        ALL(2, "ALL");


        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f931name;
        private final int value;

        Erase(int i, String str) {
            this.value = i;
            this.f931name = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.f931name;
        }

        public int value() {
            return this.value;
        }
    }

    public static void setDetector(Callable<Boolean> callable) {
        if (callable == null) {
            throw new IllegalArgumentException();
        }
        detector = callable;
    }

    public static boolean isDetected() {
        try {
            return detector.call().booleanValue();
        } catch (Exception unused) {
            return true;
        }
    }

    public static void setEnabled(boolean z) {
        holder.set(Boolean.valueOf(z));
    }

    public static boolean isEnabled() {
        return holder.get().booleanValue();
    }

    public static Ansi ansi() {
        if (isEnabled()) {
            return new Ansi();
        }
        return new NoAnsi();
    }

    public static Ansi ansi(StringBuilder sb) {
        if (isEnabled()) {
            return new Ansi(sb);
        }
        return new NoAnsi(sb);
    }

    public static Ansi ansi(int i) {
        if (isEnabled()) {
            return new Ansi(i);
        }
        return new NoAnsi(i);
    }

    private static class NoAnsi extends Ansi {
        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi a(Attribute attribute) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi bg(Color color) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi bgBright(Color color) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursor(int i, int i2) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorDown(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorDownLine() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorDownLine(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorLeft(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorRight(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorToColumn(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorUp(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorUpLine() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi cursorUpLine(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi eraseLine() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi eraseLine(Erase erase) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi eraseScreen() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi eraseScreen(Erase erase) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi fg(Color color) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi fgBright(Color color) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi reset() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        @Deprecated
        public Ansi restorCursorPosition() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi restoreCursorPosition() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi saveCursorPosition() {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi scrollDown(int i) {
            return this;
        }

        @Override // org.jetbrains.kotlin.org.fusesource.jansi.Ansi
        public Ansi scrollUp(int i) {
            return this;
        }

        public NoAnsi() {
        }

        public NoAnsi(int i) {
            super(i);
        }

        public NoAnsi(StringBuilder sb) {
            super(sb);
        }
    }

    public Ansi() {
        this(new StringBuilder());
    }

    public Ansi(Ansi ansi) {
        this(new StringBuilder(ansi.builder));
        this.attributeOptions.addAll(ansi.attributeOptions);
    }

    public Ansi(int i) {
        this(new StringBuilder(i));
    }

    public Ansi(StringBuilder sb) {
        this.attributeOptions = new ArrayList<>(5);
        this.builder = sb;
    }

    public Ansi fg(Color color) {
        this.attributeOptions.add(Integer.valueOf(color.fg()));
        return this;
    }

    public Ansi fgBlack() {
        return fg(Color.BLACK);
    }

    public Ansi fgBlue() {
        return fg(Color.BLUE);
    }

    public Ansi fgCyan() {
        return fg(Color.CYAN);
    }

    public Ansi fgDefault() {
        return fg(Color.DEFAULT);
    }

    public Ansi fgGreen() {
        return fg(Color.GREEN);
    }

    public Ansi fgMagenta() {
        return fg(Color.MAGENTA);
    }

    public Ansi fgRed() {
        return fg(Color.RED);
    }

    public Ansi fgYellow() {
        return fg(Color.YELLOW);
    }

    public Ansi bg(Color color) {
        this.attributeOptions.add(Integer.valueOf(color.bg()));
        return this;
    }

    public Ansi bgCyan() {
        return fg(Color.CYAN);
    }

    public Ansi bgDefault() {
        return bg(Color.DEFAULT);
    }

    public Ansi bgGreen() {
        return bg(Color.GREEN);
    }

    public Ansi bgMagenta() {
        return bg(Color.MAGENTA);
    }

    public Ansi bgRed() {
        return bg(Color.RED);
    }

    public Ansi bgYellow() {
        return bg(Color.YELLOW);
    }

    public Ansi fgBright(Color color) {
        this.attributeOptions.add(Integer.valueOf(color.fgBright()));
        return this;
    }

    public Ansi fgBrightBlack() {
        return fgBright(Color.BLACK);
    }

    public Ansi fgBrightBlue() {
        return fgBright(Color.BLUE);
    }

    public Ansi fgBrightCyan() {
        return fgBright(Color.CYAN);
    }

    public Ansi fgBrightDefault() {
        return fgBright(Color.DEFAULT);
    }

    public Ansi fgBrightGreen() {
        return fgBright(Color.GREEN);
    }

    public Ansi fgBrightMagenta() {
        return fgBright(Color.MAGENTA);
    }

    public Ansi fgBrightRed() {
        return fgBright(Color.RED);
    }

    public Ansi fgBrightYellow() {
        return fgBright(Color.YELLOW);
    }

    public Ansi bgBright(Color color) {
        this.attributeOptions.add(Integer.valueOf(color.bgBright()));
        return this;
    }

    public Ansi bgBrightCyan() {
        return fgBright(Color.CYAN);
    }

    public Ansi bgBrightDefault() {
        return bgBright(Color.DEFAULT);
    }

    public Ansi bgBrightGreen() {
        return bgBright(Color.GREEN);
    }

    public Ansi bgBrightMagenta() {
        return bg(Color.MAGENTA);
    }

    public Ansi bgBrightRed() {
        return bgBright(Color.RED);
    }

    public Ansi bgBrightYellow() {
        return bgBright(Color.YELLOW);
    }

    public Ansi a(Attribute attribute) {
        this.attributeOptions.add(Integer.valueOf(attribute.value()));
        return this;
    }

    public Ansi cursor(int i, int i2) {
        return appendEscapeSequence('H', Integer.valueOf(i), Integer.valueOf(i2));
    }

    public Ansi cursorToColumn(int i) {
        return appendEscapeSequence('G', i);
    }

    public Ansi cursorUp(int i) {
        return appendEscapeSequence('A', i);
    }

    public Ansi cursorDown(int i) {
        return appendEscapeSequence(Util.C_BYTE, i);
    }

    public Ansi cursorRight(int i) {
        return appendEscapeSequence(Util.C_CHAR, i);
    }

    public Ansi cursorLeft(int i) {
        return appendEscapeSequence(Util.C_DOUBLE, i);
    }

    public Ansi cursorDownLine() {
        return appendEscapeSequence('E');
    }

    public Ansi cursorDownLine(int i) {
        return appendEscapeSequence('E', i);
    }

    public Ansi cursorUpLine() {
        return appendEscapeSequence(Util.C_FLOAT);
    }

    public Ansi cursorUpLine(int i) {
        return appendEscapeSequence(Util.C_FLOAT, i);
    }

    public Ansi eraseScreen() {
        return appendEscapeSequence(Util.C_LONG, Erase.ALL.value());
    }

    public Ansi eraseScreen(Erase erase) {
        return appendEscapeSequence(Util.C_LONG, erase.value());
    }

    public Ansi eraseLine() {
        return appendEscapeSequence('K');
    }

    public Ansi eraseLine(Erase erase) {
        return appendEscapeSequence('K', erase.value());
    }

    public Ansi scrollUp(int i) {
        return appendEscapeSequence(Util.C_SHORT, i);
    }

    public Ansi scrollDown(int i) {
        return appendEscapeSequence(Util.C_TYPE_VARIABLE, i);
    }

    public Ansi saveCursorPosition() {
        return appendEscapeSequence(Advice.OffsetMapping.ForOrigin.Renderer.ForJavaSignature.SYMBOL);
    }

    @Deprecated
    public Ansi restorCursorPosition() {
        return appendEscapeSequence('u');
    }

    public Ansi restoreCursorPosition() {
        return appendEscapeSequence('u');
    }

    public Ansi reset() {
        return a(Attribute.RESET);
    }

    public Ansi bold() {
        return a(Attribute.INTENSITY_BOLD);
    }

    public Ansi boldOff() {
        return a(Attribute.INTENSITY_BOLD_OFF);
    }

    public Ansi a(String str) {
        flushAttributes();
        this.builder.append(str);
        return this;
    }

    public Ansi a(boolean z) {
        flushAttributes();
        this.builder.append(z);
        return this;
    }

    public Ansi a(char c) {
        flushAttributes();
        this.builder.append(c);
        return this;
    }

    public Ansi a(char[] cArr, int i, int i2) {
        flushAttributes();
        this.builder.append(cArr, i, i2);
        return this;
    }

    public Ansi a(char[] cArr) {
        flushAttributes();
        this.builder.append(cArr);
        return this;
    }

    public Ansi a(CharSequence charSequence, int i, int i2) {
        flushAttributes();
        this.builder.append(charSequence, i, i2);
        return this;
    }

    public Ansi a(CharSequence charSequence) {
        flushAttributes();
        this.builder.append(charSequence);
        return this;
    }

    public Ansi a(double d) {
        flushAttributes();
        this.builder.append(d);
        return this;
    }

    public Ansi a(float f) {
        flushAttributes();
        this.builder.append(f);
        return this;
    }

    public Ansi a(int i) {
        flushAttributes();
        this.builder.append(i);
        return this;
    }

    public Ansi a(long j) {
        flushAttributes();
        this.builder.append(j);
        return this;
    }

    public Ansi a(Object obj) {
        flushAttributes();
        this.builder.append(obj);
        return this;
    }

    public Ansi a(StringBuffer stringBuffer) {
        flushAttributes();
        this.builder.append(stringBuffer);
        return this;
    }

    public Ansi newline() {
        flushAttributes();
        this.builder.append(System.getProperty(SystemProperties.LINE_SEPARATOR));
        return this;
    }

    public Ansi format(String str, Object... objArr) {
        flushAttributes();
        this.builder.append(String.format(str, objArr));
        return this;
    }

    public Ansi render(String str) {
        a(AnsiRenderer.render(str));
        return this;
    }

    public Ansi render(String str, Object... objArr) {
        a(String.format(AnsiRenderer.render(str), objArr));
        return this;
    }

    public String toString() {
        flushAttributes();
        return this.builder.toString();
    }

    private Ansi appendEscapeSequence(char c) {
        flushAttributes();
        this.builder.append(FIRST_ESC_CHAR);
        this.builder.append('[');
        this.builder.append(c);
        return this;
    }

    private Ansi appendEscapeSequence(char c, int i) {
        flushAttributes();
        this.builder.append(FIRST_ESC_CHAR);
        this.builder.append('[');
        this.builder.append(i);
        this.builder.append(c);
        return this;
    }

    private Ansi appendEscapeSequence(char c, Object... objArr) {
        flushAttributes();
        return _appendEscapeSequence(c, objArr);
    }

    private void flushAttributes() {
        if (this.attributeOptions.isEmpty()) {
            return;
        }
        if (this.attributeOptions.size() == 1 && this.attributeOptions.get(0).intValue() == 0) {
            this.builder.append(FIRST_ESC_CHAR);
            this.builder.append('[');
            this.builder.append(Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL);
        } else {
            _appendEscapeSequence(Advice.OffsetMapping.ForOrigin.Renderer.ForMethodName.SYMBOL, this.attributeOptions.toArray());
        }
        this.attributeOptions.clear();
    }

    private Ansi _appendEscapeSequence(char c, Object... objArr) {
        this.builder.append(FIRST_ESC_CHAR);
        this.builder.append('[');
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                this.builder.append(';');
            }
            Object obj = objArr[i];
            if (obj != null) {
                this.builder.append(obj);
            }
        }
        this.builder.append(c);
        return this;
    }
}
