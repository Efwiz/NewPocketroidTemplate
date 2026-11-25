package org.jetbrains.kotlin.org.fusesource.jansi.internal;

import java.io.IOException;
import org.catrobat.catroid.ui.SpriteActivity;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.ArgFlag;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.ClassFlag;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.FieldFlag;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniArg;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniClass;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniField;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniMethod;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.Library;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.MethodFlag;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.PointerMath;

/* JADX INFO: loaded from: classes4.dex */
@JniClass(conditional = "defined(_WIN32) || defined(_WIN64)")
public class Kernel32 {

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short BACKGROUND_BLUE;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short BACKGROUND_GREEN;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short BACKGROUND_INTENSITY;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short BACKGROUND_RED;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short COMMON_LVB_GRID_HORIZONTAL;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short COMMON_LVB_GRID_LVERTICAL;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short COMMON_LVB_GRID_RVERTICAL;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short COMMON_LVB_LEADING_BYTE;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short COMMON_LVB_REVERSE_VIDEO;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short COMMON_LVB_TRAILING_BYTE;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short COMMON_LVB_UNDERSCORE;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short FOREGROUND_BLUE;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short FOREGROUND_GREEN;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short FOREGROUND_INTENSITY;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static short FOREGROUND_RED;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static int FORMAT_MESSAGE_FROM_SYSTEM;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static int INVALID_HANDLE_VALUE;
    private static final Library LIBRARY;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static int STD_ERROR_HANDLE;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static int STD_INPUT_HANDLE;

    @JniField(flags = {FieldFlag.CONSTANT})
    public static int STD_OUTPUT_HANDLE;

    public static final native int CloseHandle(@JniArg(cast = "HANDLE") long j);

    public static final native int FillConsoleOutputAttribute(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, short s, int i, @JniArg(flags = {ArgFlag.BY_VALUE, ArgFlag.NO_OUT}) COORD coord, @JniArg(flags = {ArgFlag.NO_IN}) int[] iArr);

    public static final native int FillConsoleOutputCharacterW(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, char c, int i, @JniArg(flags = {ArgFlag.BY_VALUE, ArgFlag.NO_OUT}) COORD coord, @JniArg(flags = {ArgFlag.NO_IN}) int[] iArr);

    public static final native int FlushConsoleInputBuffer(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j);

    public static final native int FormatMessageW(int i, @JniArg(cast = "void *") long j, int i2, int i3, @JniArg(cast = "void *", flags = {ArgFlag.NO_IN, ArgFlag.CRITICAL}) byte[] bArr, int i4, @JniArg(cast = "void *", flags = {ArgFlag.NO_IN, ArgFlag.CRITICAL, ArgFlag.SENTINEL}) long[] jArr);

    public static final native int GetConsoleMode(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(flags = {ArgFlag.NO_IN}) int[] iArr);

    public static final native int GetConsoleOutputCP();

    public static final native int GetConsoleScreenBufferInfo(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(flags = {ArgFlag.NO_IN}) CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info);

    public static final native int GetLastError();

    public static final native int GetNumberOfConsoleInputEvents(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(flags = {ArgFlag.NO_IN}) int[] iArr);

    @JniMethod(cast = "HANDLE", flags = {MethodFlag.POINTER_RETURN})
    public static final native long GetStdHandle(int i);

    private static final native int PeekConsoleInputW(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(cast = "PINPUT_RECORD", flags = {ArgFlag.POINTER_ARG}) long j2, int i, @JniArg(flags = {ArgFlag.NO_IN}) int[] iArr);

    private static final native int ReadConsoleInputW(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(cast = "PINPUT_RECORD", flags = {ArgFlag.POINTER_ARG}) long j2, int i, @JniArg(flags = {ArgFlag.NO_IN}) int[] iArr);

    public static final native int ScrollConsoleScreenBuffer(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(flags = {ArgFlag.NO_OUT}) SMALL_RECT small_rect, @JniArg(flags = {ArgFlag.NO_OUT}) SMALL_RECT small_rect2, @JniArg(flags = {ArgFlag.BY_VALUE, ArgFlag.NO_OUT}) COORD coord, @JniArg(flags = {ArgFlag.NO_OUT}) CHAR_INFO char_info);

    public static final native int SetConsoleCursorPosition(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(flags = {ArgFlag.BY_VALUE, ArgFlag.NO_OUT}) COORD coord);

    public static final native int SetConsoleMode(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, int i);

    public static final native int SetConsoleOutputCP(int i);

    public static final native int SetConsoleTextAttribute(@JniArg(cast = "HANDLE") long j, short s);

    public static final native int SetConsoleTitle(@JniArg(flags = {ArgFlag.UNICODE}) String str);

    public static final native int WriteConsoleW(@JniArg(cast = "HANDLE", flags = {ArgFlag.POINTER_ARG}) long j, @JniArg(flags = {ArgFlag.NO_OUT}) char[] cArr, int i, @JniArg(flags = {ArgFlag.NO_IN}) int[] iArr, @JniArg(cast = "LPVOID", flags = {ArgFlag.POINTER_ARG}) long j2);

    public static final native int _getch();

    public static final native void free(@JniArg(cast = "void *") long j);

    @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
    private static final native void init();

    @JniMethod(cast = "void *")
    public static final native long malloc(@JniArg(cast = "size_t") long j);

    static {
        Library library = new Library("jansi", (Class<?>) Kernel32.class);
        LIBRARY = library;
        library.load();
        init();
    }

    @JniClass(conditional = "defined(_WIN32) || defined(_WIN64)", flags = {ClassFlag.STRUCT, ClassFlag.TYPEDEF})
    public static class SMALL_RECT {

        @JniField(accessor = "sizeof(SMALL_RECT)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = "Bottom")
        public short bottom;

        @JniField(accessor = "Left")
        public short left;

        @JniField(accessor = "Right")
        public short right;

        @JniField(accessor = "Top")
        public short top;

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static final native void init();

        static {
            Kernel32.LIBRARY.load();
            init();
        }

        public short width() {
            return (short) (this.right - this.left);
        }

        public short height() {
            return (short) (this.bottom - this.top);
        }

        public SMALL_RECT copy() {
            SMALL_RECT small_rect = new SMALL_RECT();
            small_rect.left = this.left;
            small_rect.top = this.top;
            small_rect.right = this.right;
            small_rect.bottom = this.bottom;
            return small_rect;
        }
    }

    @JniClass(conditional = "defined(_WIN32) || defined(_WIN64)", flags = {ClassFlag.STRUCT, ClassFlag.TYPEDEF})
    public static class COORD {

        @JniField(accessor = "sizeof(COORD)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = SpriteActivity.EXTRA_X_TRANSFORM)
        public short x;

        @JniField(accessor = SpriteActivity.EXTRA_Y_TRANSFORM)
        public short y;

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static final native void init();

        static {
            Kernel32.LIBRARY.load();
            init();
        }

        public COORD copy() {
            COORD coord = new COORD();
            coord.x = this.x;
            coord.y = this.y;
            return coord;
        }
    }

    @JniClass(conditional = "defined(_WIN32) || defined(_WIN64)", flags = {ClassFlag.STRUCT, ClassFlag.TYPEDEF})
    public static class CONSOLE_SCREEN_BUFFER_INFO {

        @JniField(accessor = "sizeof(CONSOLE_SCREEN_BUFFER_INFO)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = "wAttributes")
        public short attributes;

        @JniField(accessor = "dwSize")
        public COORD size = new COORD();

        @JniField(accessor = "dwCursorPosition")
        public COORD cursorPosition = new COORD();

        @JniField(accessor = "srWindow")
        public SMALL_RECT window = new SMALL_RECT();

        @JniField(accessor = "dwMaximumWindowSize")
        public COORD maximumWindowSize = new COORD();

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static final native void init();

        static {
            Kernel32.LIBRARY.load();
            init();
        }

        public int windowWidth() {
            return this.window.width() + 1;
        }

        public int windowHeight() {
            return this.window.height() + 1;
        }
    }

    @JniClass(conditional = "defined(_WIN32) || defined(_WIN64)", flags = {ClassFlag.STRUCT, ClassFlag.TYPEDEF})
    public static class CHAR_INFO {

        @JniField(accessor = "sizeof(CHAR_INFO)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = "Attributes")
        public short attributes;

        @JniField(accessor = "Char.UnicodeChar")
        public char unicodeChar;

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static final native void init();

        static {
            Kernel32.LIBRARY.load();
            init();
        }
    }

    @JniClass(conditional = "defined(_WIN32) || defined(_WIN64)", flags = {ClassFlag.STRUCT, ClassFlag.TYPEDEF})
    public static class KEY_EVENT_RECORD {

        @JniField(accessor = "CAPSLOCK_ON", flags = {FieldFlag.CONSTANT})
        public static int CAPSLOCK_ON;

        @JniField(accessor = "ENHANCED_KEY", flags = {FieldFlag.CONSTANT})
        public static int ENHANCED_KEY;

        @JniField(accessor = "LEFT_ALT_PRESSED", flags = {FieldFlag.CONSTANT})
        public static int LEFT_ALT_PRESSED;

        @JniField(accessor = "LEFT_CTRL_PRESSED", flags = {FieldFlag.CONSTANT})
        public static int LEFT_CTRL_PRESSED;

        @JniField(accessor = "NUMLOCK_ON", flags = {FieldFlag.CONSTANT})
        public static int NUMLOCK_ON;

        @JniField(accessor = "RIGHT_ALT_PRESSED", flags = {FieldFlag.CONSTANT})
        public static int RIGHT_ALT_PRESSED;

        @JniField(accessor = "RIGHT_CTRL_PRESSED", flags = {FieldFlag.CONSTANT})
        public static int RIGHT_CTRL_PRESSED;

        @JniField(accessor = "SCROLLLOCK_ON", flags = {FieldFlag.CONSTANT})
        public static int SCROLLLOCK_ON;

        @JniField(accessor = "SHIFT_PRESSED", flags = {FieldFlag.CONSTANT})
        public static int SHIFT_PRESSED;

        @JniField(accessor = "sizeof(KEY_EVENT_RECORD)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = "dwControlKeyState")
        public int controlKeyState;

        @JniField(accessor = "wVirtualKeyCode")
        public short keyCode;

        @JniField(accessor = "bKeyDown")
        public boolean keyDown;

        @JniField(accessor = "wRepeatCount")
        public short repeatCount;

        @JniField(accessor = "wVirtualScanCode")
        public short scanCode;

        @JniField(accessor = "uChar.UnicodeChar")
        public char uchar;

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static final native void init();

        static {
            Kernel32.LIBRARY.load();
            init();
        }

        public String toString() {
            return "KEY_EVENT_RECORD{keyDown=" + this.keyDown + ", repeatCount=" + ((int) this.repeatCount) + ", keyCode=" + ((int) this.keyCode) + ", scanCode=" + ((int) this.scanCode) + ", uchar=" + this.uchar + ", controlKeyState=" + this.controlKeyState + '}';
        }
    }

    @JniClass(conditional = "defined(_WIN32) || defined(_WIN64)", flags = {ClassFlag.STRUCT, ClassFlag.TYPEDEF})
    public static class INPUT_RECORD {

        @JniField(accessor = "KEY_EVENT", flags = {FieldFlag.CONSTANT})
        public static short KEY_EVENT;

        @JniField(accessor = "sizeof(INPUT_RECORD)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = "EventType")
        public short eventType;

        @JniField(accessor = "Event.KeyEvent")
        public KEY_EVENT_RECORD keyEvent = new KEY_EVENT_RECORD();

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static final native void init();

        public static final native void memmove(@JniArg(cast = "void *", flags = {ArgFlag.NO_IN, ArgFlag.CRITICAL}) INPUT_RECORD input_record, @JniArg(cast = "const void *", flags = {ArgFlag.NO_OUT, ArgFlag.CRITICAL}) long j, @JniArg(cast = "size_t") long j2);

        static {
            Kernel32.LIBRARY.load();
            init();
        }
    }

    public static INPUT_RECORD[] readConsoleInputHelper(long j, int i, boolean z) throws Throwable {
        long jMalloc;
        int iReadConsoleInputW;
        int[] iArr = new int[1];
        try {
            jMalloc = malloc(INPUT_RECORD.SIZEOF * i);
            try {
                if (jMalloc == 0) {
                    throw new IOException("cannot allocate memory with JNI");
                }
                if (z) {
                    iReadConsoleInputW = PeekConsoleInputW(j, jMalloc, i, iArr);
                } else {
                    iReadConsoleInputW = ReadConsoleInputW(j, jMalloc, i, iArr);
                }
                if (iReadConsoleInputW == 0) {
                    throw new IOException("ReadConsoleInputW failed");
                }
                int i2 = iArr[0];
                if (i2 <= 0) {
                    INPUT_RECORD[] input_recordArr = new INPUT_RECORD[0];
                    if (jMalloc != 0) {
                        free(jMalloc);
                    }
                    return input_recordArr;
                }
                INPUT_RECORD[] input_recordArr2 = new INPUT_RECORD[i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    INPUT_RECORD input_record = new INPUT_RECORD();
                    input_recordArr2[i3] = input_record;
                    INPUT_RECORD.memmove(input_record, PointerMath.add(jMalloc, INPUT_RECORD.SIZEOF * i3), INPUT_RECORD.SIZEOF);
                }
                if (jMalloc != 0) {
                    free(jMalloc);
                }
                return input_recordArr2;
            } catch (Throwable th) {
                th = th;
                if (jMalloc != 0) {
                    free(jMalloc);
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            jMalloc = 0;
        }
    }

    public static INPUT_RECORD[] readConsoleKeyInput(long j, int i, boolean z) throws Throwable {
        INPUT_RECORD[] consoleInputHelper;
        int i2;
        int i3;
        do {
            consoleInputHelper = readConsoleInputHelper(j, i, z);
            i3 = 0;
            for (INPUT_RECORD input_record : consoleInputHelper) {
                if (input_record.eventType == INPUT_RECORD.KEY_EVENT) {
                    i3++;
                }
            }
        } while (i3 <= 0);
        INPUT_RECORD[] input_recordArr = new INPUT_RECORD[i3];
        int i4 = 0;
        for (INPUT_RECORD input_record2 : consoleInputHelper) {
            if (input_record2.eventType == INPUT_RECORD.KEY_EVENT) {
                input_recordArr[i4] = input_record2;
                i4++;
            }
        }
        return input_recordArr;
    }
}
