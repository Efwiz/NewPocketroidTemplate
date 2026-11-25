package org.jetbrains.kotlin.org.fusesource.jansi;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.Kernel32;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.WindowsSupport;

/* JADX INFO: loaded from: classes4.dex */
public final class WindowsAnsiOutputStream extends AnsiOutputStream {
    private static final short[] ANSI_BACKGROUND_COLOR_MAP;
    private static final short[] ANSI_FOREGROUND_COLOR_MAP;
    private static final short BACKGROUND_BLACK = 0;
    private static final short BACKGROUND_CYAN;
    private static final short BACKGROUND_MAGENTA;
    private static final short BACKGROUND_WHITE;
    private static final short BACKGROUND_YELLOW;
    private static final short FOREGROUND_BLACK = 0;
    private static final short FOREGROUND_CYAN;
    private static final short FOREGROUND_MAGENTA;
    private static final short FOREGROUND_WHITE;
    private static final short FOREGROUND_YELLOW;
    private static final long console = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
    private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info;
    private boolean negative;
    private final short originalColors;
    private short savedX;
    private short savedY;

    private short invertAttributeColors(short s) {
        return (short) ((s & 65280) | ((s & 15) << 4) | ((s & 240) >> 4));
    }

    static {
        short s = (short) (Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN);
        FOREGROUND_YELLOW = s;
        short s2 = (short) (Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_RED);
        FOREGROUND_MAGENTA = s2;
        short s3 = (short) (Kernel32.FOREGROUND_BLUE | Kernel32.FOREGROUND_GREEN);
        FOREGROUND_CYAN = s3;
        short s4 = (short) (Kernel32.FOREGROUND_RED | Kernel32.FOREGROUND_GREEN | Kernel32.FOREGROUND_BLUE);
        FOREGROUND_WHITE = s4;
        short s5 = (short) (Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN);
        BACKGROUND_YELLOW = s5;
        short s6 = (short) (Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_RED);
        BACKGROUND_MAGENTA = s6;
        short s7 = (short) (Kernel32.BACKGROUND_BLUE | Kernel32.BACKGROUND_GREEN);
        BACKGROUND_CYAN = s7;
        short s8 = (short) (Kernel32.BACKGROUND_RED | Kernel32.BACKGROUND_GREEN | Kernel32.BACKGROUND_BLUE);
        BACKGROUND_WHITE = s8;
        ANSI_FOREGROUND_COLOR_MAP = new short[]{0, Kernel32.FOREGROUND_RED, Kernel32.FOREGROUND_GREEN, s, Kernel32.FOREGROUND_BLUE, s2, s3, s4};
        ANSI_BACKGROUND_COLOR_MAP = new short[]{0, Kernel32.BACKGROUND_RED, Kernel32.BACKGROUND_GREEN, s5, Kernel32.BACKGROUND_BLUE, s6, s7, s8};
    }

    public WindowsAnsiOutputStream(OutputStream outputStream) throws IOException {
        super(outputStream);
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        this.info = console_screen_buffer_info;
        this.savedX = (short) -1;
        this.savedY = (short) -1;
        getConsoleInfo();
        this.originalColors = console_screen_buffer_info.attributes;
    }

    private void getConsoleInfo() throws IOException {
        this.out.flush();
        if (Kernel32.GetConsoleScreenBufferInfo(console, this.info) == 0) {
            throw new IOException("Could not get the screen info: " + WindowsSupport.getLastErrorMessage());
        }
        if (this.negative) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
            console_screen_buffer_info.attributes = invertAttributeColors(console_screen_buffer_info.attributes);
        }
    }

    private void applyAttribute() throws IOException {
        this.out.flush();
        short sInvertAttributeColors = this.info.attributes;
        if (this.negative) {
            sInvertAttributeColors = invertAttributeColors(sInvertAttributeColors);
        }
        if (Kernel32.SetConsoleTextAttribute(console, sInvertAttributeColors) == 0) {
            throw new IOException(WindowsSupport.getLastErrorMessage());
        }
    }

    private void applyCursorPosition() throws IOException {
        if (Kernel32.SetConsoleCursorPosition(console, this.info.cursorPosition.copy()) == 0) {
            throw new IOException(WindowsSupport.getLastErrorMessage());
        }
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processEraseScreen(int i) throws IOException {
        getConsoleInfo();
        int[] iArr = new int[1];
        if (i == 0) {
            int i2 = ((this.info.window.bottom - this.info.cursorPosition.y) * this.info.size.x) + (this.info.size.x - this.info.cursorPosition.x);
            long j = console;
            Kernel32.FillConsoleOutputAttribute(j, this.originalColors, i2, this.info.cursorPosition.copy(), iArr);
            Kernel32.FillConsoleOutputCharacterW(j, StringUtil.SPACE, i2, this.info.cursorPosition.copy(), iArr);
            return;
        }
        if (i == 1) {
            Kernel32.COORD coord = new Kernel32.COORD();
            coord.x = (short) 0;
            coord.y = this.info.window.top;
            int i3 = ((this.info.cursorPosition.y - this.info.window.top) * this.info.size.x) + this.info.cursorPosition.x;
            long j2 = console;
            Kernel32.FillConsoleOutputAttribute(j2, this.originalColors, i3, coord, iArr);
            Kernel32.FillConsoleOutputCharacterW(j2, StringUtil.SPACE, i3, coord, iArr);
            return;
        }
        if (i != 2) {
            return;
        }
        Kernel32.COORD coord2 = new Kernel32.COORD();
        coord2.x = (short) 0;
        coord2.y = this.info.window.top;
        int iHeight = this.info.window.height() * this.info.size.x;
        long j3 = console;
        Kernel32.FillConsoleOutputAttribute(j3, this.originalColors, iHeight, coord2, iArr);
        Kernel32.FillConsoleOutputCharacterW(j3, StringUtil.SPACE, iHeight, coord2, iArr);
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processEraseLine(int i) throws IOException {
        getConsoleInfo();
        int[] iArr = new int[1];
        if (i == 0) {
            int i2 = this.info.size.x - this.info.cursorPosition.x;
            long j = console;
            Kernel32.FillConsoleOutputAttribute(j, this.originalColors, i2, this.info.cursorPosition.copy(), iArr);
            Kernel32.FillConsoleOutputCharacterW(j, StringUtil.SPACE, i2, this.info.cursorPosition.copy(), iArr);
            return;
        }
        if (i == 1) {
            Kernel32.COORD coordCopy = this.info.cursorPosition.copy();
            coordCopy.x = (short) 0;
            long j2 = console;
            Kernel32.FillConsoleOutputAttribute(j2, this.originalColors, this.info.cursorPosition.x, coordCopy, iArr);
            Kernel32.FillConsoleOutputCharacterW(j2, StringUtil.SPACE, this.info.cursorPosition.x, coordCopy, iArr);
            return;
        }
        if (i != 2) {
            return;
        }
        Kernel32.COORD coordCopy2 = this.info.cursorPosition.copy();
        coordCopy2.x = (short) 0;
        long j3 = console;
        Kernel32.FillConsoleOutputAttribute(j3, this.originalColors, this.info.size.x, coordCopy2, iArr);
        Kernel32.FillConsoleOutputCharacterW(j3, StringUtil.SPACE, this.info.size.x, coordCopy2, iArr);
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processCursorLeft(int i) throws IOException {
        getConsoleInfo();
        this.info.cursorPosition.x = (short) Math.max(0, this.info.cursorPosition.x - i);
        applyCursorPosition();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processCursorRight(int i) throws IOException {
        getConsoleInfo();
        this.info.cursorPosition.x = (short) Math.min((int) this.info.window.width(), this.info.cursorPosition.x + i);
        applyCursorPosition();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processCursorDown(int i) throws IOException {
        getConsoleInfo();
        this.info.cursorPosition.y = (short) Math.min(Math.max(0, this.info.size.y - 1), this.info.cursorPosition.y + i);
        applyCursorPosition();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processCursorUp(int i) throws IOException {
        getConsoleInfo();
        this.info.cursorPosition.y = (short) Math.max((int) this.info.window.top, this.info.cursorPosition.y - i);
        applyCursorPosition();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processCursorTo(int i, int i2) throws IOException {
        getConsoleInfo();
        this.info.cursorPosition.y = (short) Math.max((int) this.info.window.top, Math.min((int) this.info.size.y, (this.info.window.top + i) - 1));
        this.info.cursorPosition.x = (short) Math.max(0, Math.min((int) this.info.window.width(), i2 - 1));
        applyCursorPosition();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processCursorToColumn(int i) throws IOException {
        getConsoleInfo();
        this.info.cursorPosition.x = (short) Math.max(0, Math.min((int) this.info.window.width(), i - 1));
        applyCursorPosition();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processSetForegroundColor(int i, boolean z) throws IOException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.attributes = (short) (ANSI_FOREGROUND_COLOR_MAP[i] | (console_screen_buffer_info.attributes & (-8)));
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
        console_screen_buffer_info2.attributes = (short) ((z ? Kernel32.FOREGROUND_INTENSITY : (short) 0) | (console_screen_buffer_info2.attributes & (~Kernel32.FOREGROUND_INTENSITY)));
        applyAttribute();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processSetBackgroundColor(int i, boolean z) throws IOException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.attributes = (short) (ANSI_BACKGROUND_COLOR_MAP[i] | (console_screen_buffer_info.attributes & (-113)));
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
        console_screen_buffer_info2.attributes = (short) ((z ? Kernel32.BACKGROUND_INTENSITY : (short) 0) | (console_screen_buffer_info2.attributes & (~Kernel32.BACKGROUND_INTENSITY)));
        applyAttribute();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processDefaultTextColor() throws IOException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.attributes = (short) ((console_screen_buffer_info.attributes & (-16)) | (this.originalColors & 15));
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
        console_screen_buffer_info2.attributes = (short) (console_screen_buffer_info2.attributes & (~Kernel32.FOREGROUND_INTENSITY));
        applyAttribute();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processDefaultBackgroundColor() throws IOException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.attributes = (short) ((console_screen_buffer_info.attributes & (-241)) | (this.originalColors & 240));
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
        console_screen_buffer_info2.attributes = (short) (console_screen_buffer_info2.attributes & (~Kernel32.BACKGROUND_INTENSITY));
        applyAttribute();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processAttributeRest() throws IOException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.attributes = (short) ((console_screen_buffer_info.attributes & (-256)) | this.originalColors);
        this.negative = false;
        applyAttribute();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processSetAttribute(int i) throws IOException {
        if (i == 1) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
            console_screen_buffer_info.attributes = (short) (console_screen_buffer_info.attributes | Kernel32.FOREGROUND_INTENSITY);
            applyAttribute();
            return;
        }
        if (i == 4) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
            console_screen_buffer_info2.attributes = (short) (console_screen_buffer_info2.attributes | Kernel32.BACKGROUND_INTENSITY);
            applyAttribute();
            return;
        }
        if (i == 7) {
            this.negative = true;
            applyAttribute();
            return;
        }
        if (i == 22) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info3 = this.info;
            console_screen_buffer_info3.attributes = (short) (console_screen_buffer_info3.attributes & (~Kernel32.FOREGROUND_INTENSITY));
            applyAttribute();
        } else if (i == 24) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info4 = this.info;
            console_screen_buffer_info4.attributes = (short) (console_screen_buffer_info4.attributes & (~Kernel32.BACKGROUND_INTENSITY));
            applyAttribute();
        } else {
            if (i != 27) {
                return;
            }
            this.negative = false;
            applyAttribute();
        }
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processSaveCursorPosition() throws IOException {
        getConsoleInfo();
        this.savedX = this.info.cursorPosition.x;
        this.savedY = this.info.cursorPosition.y;
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processRestoreCursorPosition() throws IOException {
        if (this.savedX == -1 || this.savedY == -1) {
            return;
        }
        this.out.flush();
        this.info.cursorPosition.x = this.savedX;
        this.info.cursorPosition.y = this.savedY;
        applyCursorPosition();
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processInsertLine(int i) throws IOException {
        getConsoleInfo();
        Kernel32.SMALL_RECT small_rectCopy = this.info.window.copy();
        small_rectCopy.top = this.info.cursorPosition.y;
        Kernel32.COORD coord = new Kernel32.COORD();
        coord.x = (short) 0;
        coord.y = (short) (this.info.cursorPosition.y + i);
        Kernel32.CHAR_INFO char_info = new Kernel32.CHAR_INFO();
        char_info.attributes = this.originalColors;
        char_info.unicodeChar = StringUtil.SPACE;
        if (Kernel32.ScrollConsoleScreenBuffer(console, small_rectCopy, small_rectCopy, coord, char_info) == 0) {
            throw new IOException(WindowsSupport.getLastErrorMessage());
        }
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processDeleteLine(int i) throws IOException {
        getConsoleInfo();
        Kernel32.SMALL_RECT small_rectCopy = this.info.window.copy();
        small_rectCopy.top = this.info.cursorPosition.y;
        Kernel32.COORD coord = new Kernel32.COORD();
        coord.x = (short) 0;
        coord.y = (short) (this.info.cursorPosition.y - i);
        Kernel32.CHAR_INFO char_info = new Kernel32.CHAR_INFO();
        char_info.attributes = this.originalColors;
        char_info.unicodeChar = StringUtil.SPACE;
        if (Kernel32.ScrollConsoleScreenBuffer(console, small_rectCopy, small_rectCopy, coord, char_info) == 0) {
            throw new IOException(WindowsSupport.getLastErrorMessage());
        }
    }

    @Override // org.jetbrains.kotlin.org.fusesource.jansi.AnsiOutputStream
    protected void processChangeWindowTitle(String str) {
        Kernel32.SetConsoleTitle(str);
    }
}
