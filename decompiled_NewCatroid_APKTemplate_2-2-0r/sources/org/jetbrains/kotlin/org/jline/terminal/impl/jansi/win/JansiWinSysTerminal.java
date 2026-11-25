package org.jetbrains.kotlin.org.jline.terminal.impl.jansi.win;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.function.IntConsumer;
import org.jetbrains.kotlin.org.fusesource.jansi.WindowsAnsiOutputStream;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.Kernel32;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.WindowsSupport;
import org.jetbrains.kotlin.org.jline.terminal.Cursor;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;

/* JADX INFO: loaded from: classes4.dex */
public class JansiWinSysTerminal extends AbstractWindowsTerminal {
    public JansiWinSysTerminal(String str, boolean z, Terminal.SignalHandler signalHandler) throws IOException {
        super(new WindowsAnsiOutputStream(new FileOutputStream(FileDescriptor.out)), str, z, signalHandler);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected int getConsoleOutputCP() {
        return Kernel32.GetConsoleOutputCP();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected int getConsoleMode() {
        return WindowsSupport.getConsoleMode();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected void setConsoleMode(int i) {
        WindowsSupport.setConsoleMode(i);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Size getSize() {
        Size size = new Size();
        size.setColumns(WindowsSupport.getWindowsTerminalWidth());
        size.setRows(WindowsSupport.getWindowsTerminalHeight());
        return size;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal
    protected byte[] readConsoleInput() throws IOException {
        Kernel32.INPUT_RECORD[] consoleInput = WindowsSupport.readConsoleInput(1);
        if (consoleInput == null) {
            return new byte[0];
        }
        StringBuilder sb = new StringBuilder();
        for (Kernel32.INPUT_RECORD input_record : consoleInput) {
            Kernel32.KEY_EVENT_RECORD key_event_record = input_record.keyEvent;
            boolean z = ((Kernel32.KEY_EVENT_RECORD.LEFT_ALT_PRESSED | Kernel32.KEY_EVENT_RECORD.RIGHT_ALT_PRESSED) & key_event_record.controlKeyState) != 0 && (key_event_record.controlKeyState & (Kernel32.KEY_EVENT_RECORD.LEFT_CTRL_PRESSED | Kernel32.KEY_EVENT_RECORD.RIGHT_CTRL_PRESSED)) == 0;
            if (key_event_record.keyDown) {
                if (key_event_record.uchar > 0) {
                    boolean z2 = (key_event_record.controlKeyState & Kernel32.KEY_EVENT_RECORD.SHIFT_PRESSED) != 0;
                    if (key_event_record.uchar == '\t' && z2) {
                        sb.append(getSequence(InfoCmp.Capability.key_btab));
                    } else {
                        if (z) {
                            sb.append((char) 27);
                        }
                        sb.append(key_event_record.uchar);
                    }
                } else {
                    String escapeSequence = getEscapeSequence(key_event_record.keyCode);
                    if (escapeSequence != null) {
                        for (int i = 0; i < key_event_record.repeatCount; i++) {
                            if (z) {
                                sb.append((char) 27);
                            }
                            sb.append(escapeSequence);
                        }
                    }
                }
            } else if (key_event_record.keyCode == 18 && key_event_record.uchar > 0) {
                sb.append(key_event_record.uchar);
            }
        }
        return sb.toString().getBytes();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal, org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Cursor getCursorPosition(IntConsumer intConsumer) {
        Kernel32.CONSOLE_SCREEN_BUFFER_INFO console_screen_buffer_info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        if (Kernel32.GetConsoleScreenBufferInfo(Kernel32.GetStdHandle(Kernel32.STD_OUTPUT_HANDLE), console_screen_buffer_info) == 0) {
            throw new IOError(new IOException("Could not get the cursor position: " + WindowsSupport.getLastErrorMessage()));
        }
        return new Cursor(console_screen_buffer_info.cursorPosition.x, console_screen_buffer_info.cursorPosition.y);
    }

    public void disableScrolling() {
        this.strings.remove(InfoCmp.Capability.insert_line);
        this.strings.remove(InfoCmp.Capability.parm_insert_line);
        this.strings.remove(InfoCmp.Capability.delete_line);
        this.strings.remove(InfoCmp.Capability.parm_delete_line);
    }
}
