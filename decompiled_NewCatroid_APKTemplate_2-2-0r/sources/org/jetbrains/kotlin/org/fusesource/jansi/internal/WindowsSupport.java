package org.jetbrains.kotlin.org.fusesource.jansi.internal;

import java.io.IOException;

/* JADX INFO: loaded from: classes4.dex */
public class WindowsSupport {
    public static String getLastErrorMessage() {
        byte[] bArr = new byte[160];
        Kernel32.FormatMessageW(Kernel32.FORMAT_MESSAGE_FROM_SYSTEM, 0L, Kernel32.GetLastError(), 0, bArr, 160, null);
        return new String(bArr);
    }

    public static int readByte() {
        return Kernel32._getch();
    }

    public static int getConsoleMode() {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (jGetStdHandle == Kernel32.INVALID_HANDLE_VALUE) {
            return -1;
        }
        int[] iArr = new int[1];
        if (Kernel32.GetConsoleMode(jGetStdHandle, iArr) == 0) {
            return -1;
        }
        return iArr[0];
    }

    public static void setConsoleMode(int i) {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (jGetStdHandle == Kernel32.INVALID_HANDLE_VALUE) {
            return;
        }
        Kernel32.SetConsoleMode(jGetStdHandle, i);
    }

    public static int getWindowsTerminalWidth() {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.GetConsoleScreenBufferInfo(jGetStdHandle, console_screen_buffer_info);
        return console_screen_buffer_info.windowWidth();
    }

    public static int getWindowsTerminalHeight() {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.GetConsoleScreenBufferInfo(jGetStdHandle, console_screen_buffer_info);
        return console_screen_buffer_info.windowHeight();
    }

    public static int writeConsole(String str) {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE);
        if (jGetStdHandle == Kernel32.INVALID_HANDLE_VALUE) {
            return 0;
        }
        char[] charArray = str.toCharArray();
        int[] iArr = new int[1];
        if (Kernel32.WriteConsoleW(jGetStdHandle, charArray, charArray.length, iArr, 0L) != 0) {
            return iArr[0];
        }
        return 0;
    }

    public static Kernel32.INPUT_RECORD[] readConsoleInput(int i) throws IOException {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (jGetStdHandle == Kernel32.INVALID_HANDLE_VALUE) {
            return null;
        }
        return Kernel32.readConsoleKeyInput(jGetStdHandle, i, false);
    }

    public static Kernel32.INPUT_RECORD[] peekConsoleInput(int i) throws IOException {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (jGetStdHandle == Kernel32.INVALID_HANDLE_VALUE) {
            return null;
        }
        return Kernel32.readConsoleKeyInput(jGetStdHandle, i, true);
    }

    public static void flushConsoleInputBuffer() {
        long jGetStdHandle = Kernel32.GetStdHandle(Kernel32.STD_INPUT_HANDLE);
        if (jGetStdHandle == Kernel32.INVALID_HANDLE_VALUE) {
            return;
        }
        Kernel32.FlushConsoleInputBuffer(jGetStdHandle);
    }
}
