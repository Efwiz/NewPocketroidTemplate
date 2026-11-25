package org.jetbrains.kotlin.org.jline.terminal.impl;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.io.FilterInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.Curses;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;
import org.jetbrains.kotlin.org.jline.utils.InputStreamReader;
import org.jetbrains.kotlin.org.jline.utils.Log;
import org.jetbrains.kotlin.org.jline.utils.NonBlockingReader;
import org.jetbrains.kotlin.org.jline.utils.ShutdownHooks;
import org.jetbrains.kotlin.org.jline.utils.Signals;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractWindowsTerminal extends AbstractTerminal {
    protected final Attributes attributes;
    protected final ShutdownHooks.Task closer;
    private volatile boolean closing;
    protected final InputStream input;
    protected final Map<Terminal.Signal, Object> nativeHandlers;
    protected final OutputStream output;
    protected final Thread pump;
    protected final NonBlockingReader reader;
    protected final OutputStream slaveInputPipe;
    protected Terminal.MouseTracking tracking;
    protected final PrintWriter writer;

    protected abstract int getConsoleMode();

    protected abstract int getConsoleOutputCP();

    protected abstract byte[] readConsoleInput() throws IOException;

    protected abstract void setConsoleMode(int i);

    public AbstractWindowsTerminal(OutputStream outputStream, String str, boolean z, Terminal.SignalHandler signalHandler) throws IOException {
        super(str, "windows", signalHandler);
        this.nativeHandlers = new HashMap();
        Attributes attributes = new Attributes();
        this.attributes = attributes;
        this.tracking = Terminal.MouseTracking.Off;
        PipedInputStream pipedInputStream = new PipedInputStream(1024);
        this.slaveInputPipe = new PipedOutputStream(pipedInputStream);
        this.input = new FilterInputStream(pipedInputStream) { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal.1
        };
        this.output = outputStream;
        String consoleEncoding = getConsoleEncoding();
        consoleEncoding = consoleEncoding == null ? Charset.defaultCharset().name() : consoleEncoding;
        this.reader = new NonBlockingReader(getName(), new InputStreamReader(pipedInputStream, consoleEncoding));
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream, consoleEncoding));
        parseInfoCmp();
        attributes.setLocalFlag(Attributes.LocalFlag.ISIG, true);
        attributes.setControlChar(Attributes.ControlChar.VINTR, ctrl(Util.C_CHAR));
        attributes.setControlChar(Attributes.ControlChar.VEOF, ctrl(Util.C_DOUBLE));
        attributes.setControlChar(Attributes.ControlChar.VSUSP, ctrl('Z'));
        if (z) {
            for (final Terminal.Signal signal : Terminal.Signal.values()) {
                if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
                    this.nativeHandlers.put(signal, Signals.registerDefault(signal.name()));
                } else {
                    this.nativeHandlers.put(signal, Signals.register(signal.name(), new Runnable() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.m7385xbbea6af2(signal);
                        }
                    }));
                }
            }
        }
        Thread thread = new Thread(new Runnable() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.pump();
            }
        }, "WindowsStreamPump");
        this.pump = thread;
        thread.setDaemon(true);
        thread.start();
        ShutdownHooks.Task task = new ShutdownHooks.Task() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal$$ExternalSyntheticLambda3
            @Override // org.jetbrains.kotlin.org.jline.utils.ShutdownHooks.Task
            public final void run() throws IOException {
                this.f$0.close();
            }
        };
        this.closer = task;
        ShutdownHooks.add(task);
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-org-jline-terminal-impl-AbstractWindowsTerminal, reason: not valid java name */
    /* synthetic */ void m7385xbbea6af2(Terminal.Signal signal) {
        raise(signal);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal, org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Terminal.SignalHandler handle(final Terminal.Signal signal, Terminal.SignalHandler signalHandler) {
        Terminal.SignalHandler signalHandlerHandle = super.handle(signal, signalHandler);
        if (signalHandlerHandle != signalHandler) {
            if (signalHandler == Terminal.SignalHandler.SIG_DFL) {
                Signals.registerDefault(signal.name());
            } else {
                Signals.register(signal.name(), new Runnable() { // from class: org.jetbrains.kotlin.org.jline.terminal.impl.AbstractWindowsTerminal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m7384x5019f6ef(signal);
                    }
                });
            }
        }
        return signalHandlerHandle;
    }

    /* JADX INFO: renamed from: lambda$handle$1$org-jetbrains-kotlin-org-jline-terminal-impl-AbstractWindowsTerminal, reason: not valid java name */
    /* synthetic */ void m7384x5019f6ef(Terminal.Signal signal) {
        raise(signal);
    }

    protected String getConsoleEncoding() {
        int consoleOutputCP = getConsoleOutputCP();
        String str = LanguageCodeUtil.MS + consoleOutputCP;
        if (Charset.isSupported(str)) {
            return str;
        }
        String str2 = "cp" + consoleOutputCP;
        if (Charset.isSupported(str2)) {
            return str2;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public NonBlockingReader reader() {
        return this.reader;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public PrintWriter writer() {
        return this.writer;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Attributes getAttributes() {
        int consoleMode = getConsoleMode();
        if ((consoleMode & 4) != 0) {
            this.attributes.setLocalFlag(Attributes.LocalFlag.ECHO, true);
        }
        if ((consoleMode & 2) != 0) {
            this.attributes.setLocalFlag(Attributes.LocalFlag.ICANON, true);
        }
        return new Attributes(this.attributes);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public void setAttributes(Attributes attributes) {
        this.attributes.copy(attributes);
        updateConsoleMode();
    }

    protected void updateConsoleMode() {
        int i = this.attributes.getLocalFlag(Attributes.LocalFlag.ECHO) ? 12 : 8;
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ICANON)) {
            i |= 2;
        }
        if (this.tracking != Terminal.MouseTracking.Off) {
            i |= 16;
        }
        setConsoleMode(i);
    }

    protected int ctrl(char c) {
        return Character.toUpperCase(c) & 31;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public void setSize(Size size) {
        throw new UnsupportedOperationException("Can not resize windows terminal");
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.closing = true;
        this.pump.interrupt();
        ShutdownHooks.remove(this.closer);
        for (Map.Entry<Terminal.Signal, Object> entry : this.nativeHandlers.entrySet()) {
            Signals.unregister(entry.getKey().name(), entry.getValue());
        }
        this.reader.close();
        this.writer.close();
    }

    protected String getEscapeSequence(short s) {
        if (s == 8) {
            return getSequence(InfoCmp.Capability.key_backspace);
        }
        if (s == 45) {
            return getSequence(InfoCmp.Capability.key_ic);
        }
        if (s != 46) {
            switch (s) {
                case 33:
                    return getSequence(InfoCmp.Capability.key_ppage);
                case 34:
                    return getSequence(InfoCmp.Capability.key_npage);
                case 35:
                    return getSequence(InfoCmp.Capability.key_end);
                case 36:
                    return getSequence(InfoCmp.Capability.key_home);
                case 37:
                    return getSequence(InfoCmp.Capability.key_left);
                case 38:
                    return getSequence(InfoCmp.Capability.key_up);
                case 39:
                    return getSequence(InfoCmp.Capability.key_right);
                case 40:
                    return getSequence(InfoCmp.Capability.key_down);
                default:
                    switch (s) {
                        case 112:
                            return getSequence(InfoCmp.Capability.key_f1);
                        case 113:
                            return getSequence(InfoCmp.Capability.key_f2);
                        case 114:
                            return getSequence(InfoCmp.Capability.key_f3);
                        case 115:
                            return getSequence(InfoCmp.Capability.key_f4);
                        case 116:
                            return getSequence(InfoCmp.Capability.key_f5);
                        case 117:
                            return getSequence(InfoCmp.Capability.key_f6);
                        case 118:
                            return getSequence(InfoCmp.Capability.key_f7);
                        case 119:
                            return getSequence(InfoCmp.Capability.key_f8);
                        case 120:
                            return getSequence(InfoCmp.Capability.key_f9);
                        case 121:
                            return getSequence(InfoCmp.Capability.key_f10);
                        case 122:
                            return getSequence(InfoCmp.Capability.key_f11);
                        case 123:
                            return getSequence(InfoCmp.Capability.key_f12);
                        default:
                            return null;
                    }
            }
        }
        return getSequence(InfoCmp.Capability.key_dc);
    }

    protected String getSequence(InfoCmp.Capability capability) {
        String str = this.strings.get(capability);
        if (str == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        try {
            Curses.tputs(stringWriter, str, new Object[0]);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    protected void pump() {
        while (!this.closing) {
            try {
                for (byte b : readConsoleInput()) {
                    processInputByte(b);
                }
            } catch (IOException e) {
                if (this.closing) {
                    return;
                }
                Log.warn("Error in WindowsStreamPump", e);
                return;
            }
        }
    }

    public void processInputByte(int i) throws IOException {
        if (this.attributes.getLocalFlag(Attributes.LocalFlag.ISIG)) {
            if (i == this.attributes.getControlChar(Attributes.ControlChar.VINTR)) {
                raise(Terminal.Signal.INT);
                return;
            }
            if (i == this.attributes.getControlChar(Attributes.ControlChar.VQUIT)) {
                raise(Terminal.Signal.QUIT);
                return;
            } else if (i == this.attributes.getControlChar(Attributes.ControlChar.VSUSP)) {
                raise(Terminal.Signal.TSTP);
                return;
            } else if (i == this.attributes.getControlChar(Attributes.ControlChar.VSTATUS)) {
                raise(Terminal.Signal.INFO);
            }
        }
        if (i == 13) {
            if (this.attributes.getInputFlag(Attributes.InputFlag.IGNCR)) {
                return;
            }
            if (this.attributes.getInputFlag(Attributes.InputFlag.ICRNL)) {
                i = 10;
            }
        } else if (i == 10 && this.attributes.getInputFlag(Attributes.InputFlag.INLCR)) {
            i = 13;
        }
        this.slaveInputPipe.write(i);
        this.slaveInputPipe.flush();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal, org.jetbrains.kotlin.org.jline.terminal.Terminal
    public boolean trackMouse(Terminal.MouseTracking mouseTracking) {
        this.tracking = mouseTracking;
        updateConsoleMode();
        return true;
    }
}
