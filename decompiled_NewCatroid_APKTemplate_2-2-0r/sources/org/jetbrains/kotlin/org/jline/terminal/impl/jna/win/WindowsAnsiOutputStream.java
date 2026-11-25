package org.jetbrains.kotlin.org.jline.terminal.impl.jna.win;

import com.sun.jna.LastErrorException;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes4.dex */
public final class WindowsAnsiOutputStream extends AnsiOutputStream {
    private final Pointer console;
    private final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info;
    private boolean negative;
    private final short originalColors;
    private short savedX;
    private short savedY;
    private static final short[] ANSI_FOREGROUND_COLOR_MAP = {0, 4, 2, 6, 1, 5, 3, 7};
    private static final short[] ANSI_BACKGROUND_COLOR_MAP = {0, 64, 32, 96, 16, 80, 48, 112};

    private short invertAttributeColors(short s) {
        return (short) ((s & 65280) | ((s & 15) << 4) | ((s & 240) >> 4));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    public WindowsAnsiOutputStream(OutputStream outputStream, Pointer pointer) throws IOException, LastErrorException {
        super(outputStream);
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        this.info = console_screen_buffer_info;
        this.savedX = (short) -1;
        this.savedY = (short) -1;
        this.console = pointer;
        getConsoleInfo();
        this.originalColors = console_screen_buffer_info.wAttributes;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    private void getConsoleInfo() throws IOException, LastErrorException {
        this.out.flush();
        Kernel32.INSTANCE.GetConsoleScreenBufferInfo(this.console, this.info);
        if (this.negative) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
            console_screen_buffer_info.wAttributes = invertAttributeColors(console_screen_buffer_info.wAttributes);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    private void applyAttribute() throws IOException, LastErrorException {
        this.out.flush();
        short sInvertAttributeColors = this.info.wAttributes;
        if (this.negative) {
            sInvertAttributeColors = invertAttributeColors(sInvertAttributeColors);
        }
        Kernel32.INSTANCE.SetConsoleTextAttribute(this.console, sInvertAttributeColors);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    private void applyCursorPosition() throws LastErrorException, IOException {
        Kernel32.INSTANCE.SetConsoleCursorPosition(this.console, this.info.dwCursorPosition);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processDefaultTextColor() throws IOException, LastErrorException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.wAttributes = (short) ((console_screen_buffer_info.wAttributes & (-16)) | (this.originalColors & 15));
        applyAttribute();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processDefaultBackgroundColor() throws IOException, LastErrorException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.wAttributes = (short) ((console_screen_buffer_info.wAttributes & (-241)) | (this.originalColors & 240));
        applyAttribute();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processEraseScreen(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        IntByReference intByReference = new IntByReference();
        if (i == 0) {
            int i2 = ((this.info.srWindow.Bottom - this.info.dwCursorPosition.Y) * this.info.dwSize.X) + (this.info.dwSize.X - this.info.dwCursorPosition.X);
            Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, StringUtil.SPACE, i2, this.info.dwCursorPosition, intByReference);
            Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, i2, this.info.dwCursorPosition, intByReference);
            return;
        }
        if (i == 1) {
            Kernel32.COORD coord = new Kernel32.COORD();
            coord.X = (short) 0;
            coord.Y = this.info.srWindow.Top;
            int i3 = ((this.info.dwCursorPosition.Y - this.info.srWindow.Top) * this.info.dwSize.X) + this.info.dwCursorPosition.X;
            Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, StringUtil.SPACE, i3, coord, intByReference);
            Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, i3, coord, intByReference);
            return;
        }
        if (i != 2) {
            return;
        }
        Kernel32.COORD coord2 = new Kernel32.COORD();
        coord2.X = (short) 0;
        coord2.Y = this.info.srWindow.Top;
        int iHeight = this.info.srWindow.height() * this.info.dwSize.X;
        Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, StringUtil.SPACE, iHeight, coord2, intByReference);
        Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, iHeight, coord2, intByReference);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processEraseLine(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        IntByReference intByReference = new IntByReference();
        if (i == 0) {
            int i2 = this.info.dwSize.X - this.info.dwCursorPosition.X;
            Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, StringUtil.SPACE, i2, this.info.dwCursorPosition, intByReference);
            Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, i2, this.info.dwCursorPosition, intByReference);
        } else if (i == 1) {
            Kernel32.COORD coord = new Kernel32.COORD((short) 0, this.info.dwCursorPosition.Y);
            Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, StringUtil.SPACE, this.info.dwCursorPosition.X, coord, intByReference);
            Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, this.info.dwCursorPosition.X, coord, intByReference);
        } else {
            if (i != 2) {
                return;
            }
            Kernel32.COORD coord2 = new Kernel32.COORD((short) 0, this.info.dwCursorPosition.Y);
            Kernel32.INSTANCE.FillConsoleOutputCharacter(this.console, StringUtil.SPACE, this.info.dwSize.X, coord2, intByReference);
            Kernel32.INSTANCE.FillConsoleOutputAttribute(this.console, this.info.wAttributes, this.info.dwSize.X, coord2, intByReference);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorUpLine(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.X = (short) 0;
        this.info.dwCursorPosition.Y = (short) Math.max((int) this.info.srWindow.Top, this.info.dwCursorPosition.Y - i);
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorDownLine(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.X = (short) 0;
        this.info.dwCursorPosition.Y = (short) Math.min((int) this.info.dwSize.Y, this.info.dwCursorPosition.Y + i);
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorLeft(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.X = (short) Math.max(0, this.info.dwCursorPosition.X - i);
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorRight(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.X = (short) Math.min((int) this.info.srWindow.width(), this.info.dwCursorPosition.X + i);
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorDown(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.Y = (short) Math.min((int) this.info.dwSize.Y, this.info.dwCursorPosition.Y + i);
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorUp(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.Y = (short) Math.max((int) this.info.srWindow.Top, this.info.dwCursorPosition.Y - i);
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorTo(int i, int i2) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.Y = (short) Math.max((int) this.info.srWindow.Top, Math.min((int) this.info.dwSize.Y, (this.info.srWindow.Top + i) - 1));
        this.info.dwCursorPosition.X = (short) Math.max(0, Math.min((int) this.info.srWindow.width(), i2 - 1));
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processCursorToColumn(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        this.info.dwCursorPosition.X = (short) Math.max(0, Math.min((int) this.info.srWindow.width(), i - 1));
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processSetForegroundColor(int i, boolean z) throws IOException, LastErrorException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.wAttributes = (short) (ANSI_FOREGROUND_COLOR_MAP[i] | (console_screen_buffer_info.wAttributes & (-8)));
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
        console_screen_buffer_info2.wAttributes = (short) ((z ? 8 : 0) | (console_screen_buffer_info2.wAttributes & (-9)));
        applyAttribute();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processSetBackgroundColor(int i, boolean z) throws IOException, LastErrorException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.wAttributes = (short) (ANSI_BACKGROUND_COLOR_MAP[i] | (console_screen_buffer_info.wAttributes & (-113)));
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
        console_screen_buffer_info2.wAttributes = (short) ((z ? 128 : 0) | (console_screen_buffer_info2.wAttributes & (-129)));
        applyAttribute();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processAttributeRest() throws IOException, LastErrorException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
        console_screen_buffer_info.wAttributes = (short) ((console_screen_buffer_info.wAttributes & (-256)) | this.originalColors);
        this.negative = false;
        applyAttribute();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processSetAttribute(int i) throws IOException, LastErrorException {
        if (i == 1) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = this.info;
            console_screen_buffer_info.wAttributes = (short) (console_screen_buffer_info.wAttributes | 8);
            applyAttribute();
            return;
        }
        if (i == 4) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info2 = this.info;
            console_screen_buffer_info2.wAttributes = (short) (console_screen_buffer_info2.wAttributes | 128);
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
            console_screen_buffer_info3.wAttributes = (short) (console_screen_buffer_info3.wAttributes & (-9));
            applyAttribute();
        } else if (i == 24) {
            Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info4 = this.info;
            console_screen_buffer_info4.wAttributes = (short) (console_screen_buffer_info4.wAttributes & (-129));
            applyAttribute();
        } else {
            if (i != 27) {
                return;
            }
            this.negative = false;
            applyAttribute();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processSaveCursorPosition() throws IOException, LastErrorException {
        getConsoleInfo();
        this.savedX = this.info.dwCursorPosition.X;
        this.savedY = this.info.dwCursorPosition.Y;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processRestoreCursorPosition() throws IOException, LastErrorException {
        if (this.savedX == -1 || this.savedY == -1) {
            return;
        }
        this.out.flush();
        this.info.dwCursorPosition.X = this.savedX;
        this.info.dwCursorPosition.Y = this.savedY;
        applyCursorPosition();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processInsertLine(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        Kernel32.SMALL_RECT small_rect = new Kernel32.SMALL_RECT(this.info.srWindow);
        small_rect.Top = this.info.dwCursorPosition.Y;
        Kernel32.COORD coord = new Kernel32.COORD();
        coord.X = (short) 0;
        coord.Y = (short) (this.info.dwCursorPosition.Y + i);
        Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, small_rect, small_rect, coord, new Kernel32.CHAR_INFO(StringUtil.SPACE, this.originalColors));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processDeleteLine(int i) throws IOException, LastErrorException {
        getConsoleInfo();
        Kernel32.SMALL_RECT small_rect = new Kernel32.SMALL_RECT(this.info.srWindow);
        small_rect.Top = this.info.dwCursorPosition.Y;
        Kernel32.COORD coord = new Kernel32.COORD();
        coord.X = (short) 0;
        coord.Y = (short) (this.info.dwCursorPosition.Y - i);
        Kernel32.INSTANCE.ScrollConsoleScreenBuffer(this.console, small_rect, small_rect, coord, new Kernel32.CHAR_INFO(StringUtil.SPACE, this.originalColors));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jna.win.AnsiOutputStream
    protected void processChangeWindowTitle(String str) throws LastErrorException {
        Kernel32.INSTANCE.SetConsoleTitle(str);
    }
}
