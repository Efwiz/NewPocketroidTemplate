package org.jetbrains.kotlin.org.jline.terminal.impl.jna.win;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.util.Arrays;
import java.util.List;
import org.catrobat.catroid.ui.SpriteActivity;

/* JADX INFO: loaded from: classes4.dex */
interface Kernel32 extends StdCallLibrary {
    public static final Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
    public static final Pointer INVALID_HANDLE_VALUE = Pointer.createConstant(-1);

    void FillConsoleOutputAttribute(Pointer pointer, short s, int i, COORD coord, IntByReference intByReference) throws LastErrorException;

    void FillConsoleOutputCharacter(Pointer pointer, char c, int i, COORD coord, IntByReference intByReference) throws LastErrorException;

    void GetConsoleMode(Pointer pointer, IntByReference intByReference) throws LastErrorException;

    int GetConsoleOutputCP();

    void GetConsoleScreenBufferInfo(Pointer pointer, CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info) throws LastErrorException;

    Pointer GetStdHandle(int i);

    void ReadConsoleInput(Pointer pointer, INPUT_RECORD[] input_recordArr, int i, IntByReference intByReference) throws LastErrorException;

    void ScrollConsoleScreenBuffer(Pointer pointer, SMALL_RECT small_rect, SMALL_RECT small_rect2, COORD coord, CHAR_INFO char_info) throws LastErrorException;

    void SetConsoleCursorPosition(Pointer pointer, COORD coord) throws LastErrorException;

    void SetConsoleMode(Pointer pointer, int i) throws LastErrorException;

    void SetConsoleTextAttribute(Pointer pointer, short s) throws LastErrorException;

    void SetConsoleTitle(String str) throws LastErrorException;

    public static class CHAR_INFO extends Structure {
        private static String[] fieldOrder = {"uChar", "Attributes"};
        public short Attributes;
        public UnionChar uChar;

        public CHAR_INFO() {
        }

        public CHAR_INFO(char c, short s) {
            this.uChar = new UnionChar(c);
            this.Attributes = s;
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }
    }

    public static class CONSOLE_SCREEN_BUFFER_INFO extends Structure {
        private static String[] fieldOrder = {"dwSize", "dwCursorPosition", "wAttributes", "srWindow", "dwMaximumWindowSize"};
        public COORD dwCursorPosition;
        public COORD dwSize;
        public SMALL_RECT srWindow;
        public short wAttributes;

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }

        public int windowWidth() {
            return this.srWindow.width() + 1;
        }

        public int windowHeight() {
            return this.srWindow.height() + 1;
        }
    }

    public static class COORD extends Structure implements Structure.ByValue {
        private static String[] fieldOrder = {SpriteActivity.EXTRA_X_TRANSFORM, SpriteActivity.EXTRA_Y_TRANSFORM};
        public short X;
        public short Y;

        public COORD() {
        }

        public COORD(short s, short s2) {
            this.X = s;
            this.Y = s2;
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }
    }

    public static class INPUT_RECORD extends Structure {
        private static String[] fieldOrder = {"EventType", "Event"};
        public EventUnion Event;
        public short EventType;

        public static class EventUnion extends Union {
            public KEY_EVENT_RECORD KeyEvent;
            public MOUSE_EVENT_RECORD MouseEvent;
        }

        public void read() {
            readField("EventType");
            short s = this.EventType;
            if (s == 1) {
                this.Event.setType(KEY_EVENT_RECORD.class);
            } else if (s == 2) {
                this.Event.setType(MOUSE_EVENT_RECORD.class);
            } else if (s == 4) {
                this.Event.setType(WINDOW_BUFFER_SIZE_RECORD.class);
            }
            super.read();
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }
    }

    public static class KEY_EVENT_RECORD extends Structure {
        private static String[] fieldOrder = {"bKeyDown", "wRepeatCount", "wVirtualKeyCode", "wVirtualScanCode", "uChar", "dwControlKeyState"};
        public boolean bKeyDown;
        public int dwControlKeyState;
        public UnionChar uChar;
        public short wRepeatCount;
        public short wVirtualKeyCode;

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }
    }

    public static class MOUSE_EVENT_RECORD extends Structure {
        private static String[] fieldOrder = {"dwMousePosition", "dwButtonState", "dwControlKeyState", "dwEventFlags"};
        public int dwButtonState;
        public int dwEventFlags;
        public COORD dwMousePosition;

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }
    }

    public static class WINDOW_BUFFER_SIZE_RECORD extends Structure {
        private static String[] fieldOrder = {"dwSize"};

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }
    }

    public static class SMALL_RECT extends Structure {
        private static String[] fieldOrder = {"Left", "Top", "Right", "Bottom"};
        public short Bottom;
        public short Left;
        public short Right;
        public short Top;

        public SMALL_RECT() {
        }

        public SMALL_RECT(SMALL_RECT small_rect) {
            this(small_rect.Top, small_rect.Left, small_rect.Bottom, small_rect.Right);
        }

        public SMALL_RECT(short s, short s2, short s3, short s4) {
            this.Top = s;
            this.Left = s2;
            this.Bottom = s3;
            this.Right = s4;
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList(fieldOrder);
        }

        public short width() {
            return (short) (this.Right - this.Left);
        }

        public short height() {
            return (short) (this.Bottom - this.Top);
        }
    }

    public static class UnionChar extends Union {
        public char UnicodeChar;

        public UnionChar() {
        }

        public UnionChar(char c) {
            setType(Character.TYPE);
            this.UnicodeChar = c;
        }
    }
}
