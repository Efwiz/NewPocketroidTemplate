package org.jetbrains.kotlin.org.jline.terminal;

import java.io.Closeable;
import java.io.Flushable;
import java.io.PrintWriter;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import org.jetbrains.kotlin.org.jline.terminal.impl.NativeSignalHandler;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;
import org.jetbrains.kotlin.org.jline.utils.NonBlockingReader;

/* JADX INFO: loaded from: classes4.dex */
public interface Terminal extends Closeable, Flushable {

    public enum MouseTracking {
        Off,
        Normal,
        Button,
        Any
    }

    public enum Signal {
        INT,
        QUIT,
        TSTP,
        CONT,
        INFO,
        WINCH
    }

    public interface SignalHandler {
        public static final SignalHandler SIG_DFL = NativeSignalHandler.SIG_DFL;
        public static final SignalHandler SIG_IGN = NativeSignalHandler.SIG_IGN;

        void handle(Signal signal);
    }

    Attributes enterRawMode();

    @Override // java.io.Flushable
    void flush();

    Attributes getAttributes();

    boolean getBooleanCapability(InfoCmp.Capability capability);

    Cursor getCursorPosition(IntConsumer intConsumer);

    Integer getNumericCapability(InfoCmp.Capability capability);

    Size getSize();

    String getStringCapability(InfoCmp.Capability capability);

    String getType();

    SignalHandler handle(Signal signal, SignalHandler signalHandler);

    boolean puts(InfoCmp.Capability capability, Object... objArr);

    MouseEvent readMouseEvent(IntSupplier intSupplier);

    NonBlockingReader reader();

    void setAttributes(Attributes attributes);

    void setSize(Size size);

    boolean trackMouse(MouseTracking mouseTracking);

    PrintWriter writer();
}
