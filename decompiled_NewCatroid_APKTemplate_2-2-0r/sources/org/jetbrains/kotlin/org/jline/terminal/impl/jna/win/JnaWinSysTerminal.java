package org.jetbrains.kotlin.org.jline.terminal.impl.jna.win;

import com.sun.jna.LastErrorException;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import io.netty.util.internal.StringUtil;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.org.jline.terminal.Cursor;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;

/* JADX INFO: loaded from: classes4.dex */
public class JnaWinSysTerminal extends AbstractWindowsTerminal {
    private static final Pointer consoleIn = Kernel32.INSTANCE.GetStdHandle(-10);
    private static final Pointer consoleOut = Kernel32.INSTANCE.GetStdHandle(-11);
    private char[] mouse;
    private int prevButtonState;

    public JnaWinSysTerminal(String str, boolean z, Terminal.SignalHandler signalHandler) throws IOException {
        super(new WindowsAnsiOutputStream(new FileOutputStream(FileDescriptor.out), consoleOut), str, z, signalHandler);
        this.mouse = new char[]{27, '[', 'M', StringUtil.SPACE, StringUtil.SPACE, StringUtil.SPACE};
        this.strings.put(InfoCmp.Capability.key_mouse, "\\E[M");
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected int getConsoleOutputCP() {
        return Kernel32.INSTANCE.GetConsoleOutputCP();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected int getConsoleMode() throws LastErrorException {
        IntByReference intByReference = new IntByReference();
        Kernel32.INSTANCE.GetConsoleMode(consoleIn, intByReference);
        return intByReference.getValue();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected void setConsoleMode(int i) throws LastErrorException {
        Kernel32.INSTANCE.SetConsoleMode(consoleIn, i);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Size getSize() throws LastErrorException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.INSTANCE.GetConsoleScreenBufferInfo(consoleOut, console_screen_buffer_info);
        return new Size(console_screen_buffer_info.windowWidth(), console_screen_buffer_info.windowHeight());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected byte[] readConsoleInput() throws LastErrorException, IOException {
        Kernel32.INPUT_RECORD[] input_recordArrDoReadConsoleInput = doReadConsoleInput();
        if (input_recordArrDoReadConsoleInput == null) {
            return new byte[0];
        }
        StringBuilder sb = new StringBuilder();
        for (Kernel32.INPUT_RECORD input_record : input_recordArrDoReadConsoleInput) {
            int i = 1;
            if (input_record.EventType == 1) {
                Kernel32.KEY_EVENT_RECORD key_event_record = input_record.Event.KeyEvent;
                boolean z = (key_event_record.dwControlKeyState & 3) != 0 && (key_event_record.dwControlKeyState & 12) == 0;
                if (key_event_record.bKeyDown) {
                    if (key_event_record.uChar.UnicodeChar > 0) {
                        i = (key_event_record.dwControlKeyState & 16) == 0 ? 0 : 1;
                        if (key_event_record.uChar.UnicodeChar == '\t' && i != 0) {
                            sb.append(getSequence(InfoCmp.Capability.key_btab));
                        } else {
                            if (z) {
                                sb.append((char) 27);
                            }
                            sb.append(key_event_record.uChar.UnicodeChar);
                        }
                    } else {
                        String escapeSequence = getEscapeSequence(key_event_record.wVirtualKeyCode);
                        if (escapeSequence != null) {
                            for (int i2 = 0; i2 < key_event_record.wRepeatCount; i2++) {
                                if (z) {
                                    sb.append((char) 27);
                                }
                                sb.append(escapeSequence);
                            }
                        }
                    }
                } else if (key_event_record.wVirtualKeyCode == 18 && key_event_record.uChar.UnicodeChar > 0) {
                    sb.append(key_event_record.uChar.UnicodeChar);
                }
            } else if (input_record.EventType == 4) {
                raise(Terminal.Signal.WINCH);
            } else if (input_record.EventType == 2) {
                Kernel32.MOUSE_EVENT_RECORD mouse_event_record = input_record.Event.MouseEvent;
                int i3 = mouse_event_record.dwEventFlags;
                int i4 = mouse_event_record.dwButtonState;
                if (this.tracking != Terminal.MouseTracking.Off && ((this.tracking != Terminal.MouseTracking.Normal || i3 != 1) && (this.tracking != Terminal.MouseTracking.Button || i3 != 1 || i4 != 0))) {
                    int i5 = i3 & (-3);
                    if (i5 == 4) {
                        i = (i4 >> 16) < 0 ? 65 : 64;
                    } else if (i5 != 8) {
                        if ((i4 & 1) != 0) {
                            i = 0;
                        } else if ((i4 & 2) == 0) {
                            i = (i4 & 4) != 0 ? 2 : 3;
                        }
                    }
                    short s = mouse_event_record.dwMousePosition.X;
                    short s2 = mouse_event_record.dwMousePosition.Y;
                    char[] cArr = this.mouse;
                    cArr[3] = (char) (i + 32);
                    cArr[4] = (char) (s + 33);
                    cArr[5] = (char) (s2 + 33);
                    sb.append(cArr);
                    this.prevButtonState = i4;
                }
            }
        }
        return sb.toString().getBytes();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    private Kernel32.INPUT_RECORD[] doReadConsoleInput() throws LastErrorException, IOException {
        Kernel32.INPUT_RECORD[] input_recordArr = new Kernel32.INPUT_RECORD[1];
        IntByReference intByReference = new IntByReference();
        Kernel32.INSTANCE.ReadConsoleInput(consoleIn, input_recordArr, 1, intByReference);
        for (int i = 0; i < intByReference.getValue(); i++) {
            short s = input_recordArr[i].EventType;
            if (s == 1 || s == 2 || s == 4) {
                return input_recordArr;
            }
        }
        return null;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal, org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Cursor getCursorPosition(IntConsumer intConsumer) throws LastErrorException {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        Kernel32.INSTANCE.GetConsoleScreenBufferInfo(consoleOut, console_screen_buffer_info);
        return new Cursor(console_screen_buffer_info.dwCursorPosition.X, console_screen_buffer_info.dwCursorPosition.Y);
    }
}
