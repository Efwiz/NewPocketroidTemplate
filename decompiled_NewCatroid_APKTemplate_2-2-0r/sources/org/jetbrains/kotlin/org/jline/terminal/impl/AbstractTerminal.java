package org.jetbrains.kotlin.org.jline.terminal.impl;

import java.io.IOError;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Cursor;
import org.jetbrains.kotlin.org.jline.terminal.MouseEvent;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.Curses;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;
import org.jetbrains.kotlin.org.jline.utils.Log;

/* JADX INFO: loaded from: classes4.dex */
public abstract class AbstractTerminal implements Terminal {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected final String f937name;
    protected final String type;
    protected final Map<Terminal.Signal, Terminal.SignalHandler> handlers = new HashMap();
    protected final Set<InfoCmp.Capability> bools = new HashSet();
    protected final Map<InfoCmp.Capability, Integer> ints = new HashMap();
    protected final Map<InfoCmp.Capability, String> strings = new HashMap();
    private MouseEvent lastMouseEvent = new MouseEvent(MouseEvent.Type.Moved, MouseEvent.Button.NoButton, EnumSet.noneOf(MouseEvent.Modifier.class), 0, 0);

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Cursor getCursorPosition(IntConsumer intConsumer) {
        return null;
    }

    public AbstractTerminal(String str, String str2, Terminal.SignalHandler signalHandler) throws IOException {
        this.f937name = str;
        this.type = str2;
        for (Terminal.Signal signal : Terminal.Signal.values()) {
            this.handlers.put(signal, signalHandler);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Terminal.SignalHandler handle(Terminal.Signal signal, Terminal.SignalHandler signalHandler) {
        Objects.requireNonNull(signal);
        Objects.requireNonNull(signalHandler);
        return this.handlers.put(signal, signalHandler);
    }

    public void raise(Terminal.Signal signal) {
        Objects.requireNonNull(signal);
        Terminal.SignalHandler signalHandler = this.handlers.get(signal);
        if (signalHandler == Terminal.SignalHandler.SIG_DFL || signalHandler == Terminal.SignalHandler.SIG_IGN) {
            return;
        }
        signalHandler.handle(signal);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.org.jline.terminal.impl.AbstractTerminal$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jline$terminal$Terminal$Signal;

        static {
            int[] iArr = new int[Terminal.Signal.values().length];
            $SwitchMap$org$jline$terminal$Terminal$Signal = iArr;
            try {
                iArr[Terminal.Signal.INT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jline$terminal$Terminal$Signal[Terminal.Signal.QUIT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jline$terminal$Terminal$Signal[Terminal.Signal.TSTP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected void echoSignal(Terminal.Signal signal) {
        Attributes.ControlChar controlChar;
        int controlChar2;
        int i = AnonymousClass1.$SwitchMap$org$jline$terminal$Terminal$Signal[signal.ordinal()];
        if (i == 1) {
            controlChar = Attributes.ControlChar.VINTR;
        } else if (i == 2) {
            controlChar = Attributes.ControlChar.VQUIT;
        } else {
            controlChar = i != 3 ? null : Attributes.ControlChar.VSUSP;
        }
        if (controlChar == null || (controlChar2 = getAttributes().getControlChar(controlChar)) <= 0 || controlChar2 >= 32) {
            return;
        }
        writer().write(new char[]{Util.C_EXCEPTION_START, (char) (controlChar2 + 64)}, 0, 2);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Attributes enterRawMode() {
        Attributes attributes = getAttributes();
        Attributes attributes2 = new Attributes(attributes);
        attributes2.setLocalFlags(EnumSet.of(Attributes.LocalFlag.ICANON, Attributes.LocalFlag.ECHO, Attributes.LocalFlag.IEXTEN), false);
        attributes2.setInputFlags(EnumSet.of(Attributes.InputFlag.IXON, Attributes.InputFlag.ICRNL, Attributes.InputFlag.INLCR), false);
        attributes2.setControlChar(Attributes.ControlChar.VMIN, 1);
        attributes2.setControlChar(Attributes.ControlChar.VTIME, 0);
        setAttributes(attributes2);
        return attributes;
    }

    public String getName() {
        return this.f937name;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public String getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal, java.io.Flushable
    public void flush() {
        writer().flush();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public boolean puts(InfoCmp.Capability capability, Object... objArr) {
        String stringCapability = getStringCapability(capability);
        if (stringCapability == null) {
            return false;
        }
        try {
            Curses.tputs(writer(), stringCapability, objArr);
            return true;
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public boolean getBooleanCapability(InfoCmp.Capability capability) {
        return this.bools.contains(capability);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public Integer getNumericCapability(InfoCmp.Capability capability) {
        return this.ints.get(capability);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public String getStringCapability(InfoCmp.Capability capability) {
        return this.strings.get(capability);
    }

    protected void parseInfoCmp() throws Throwable {
        String infoCmp;
        String str = this.type;
        if (str != null) {
            try {
                infoCmp = InfoCmp.getInfoCmp(str);
            } catch (Exception e) {
                Log.warn("Unable to retrieve infocmp for type " + this.type, e);
                infoCmp = null;
            }
        } else {
            infoCmp = null;
        }
        if (infoCmp == null) {
            infoCmp = InfoCmp.getLoadedInfoCmp("ansi");
        }
        InfoCmp.parseInfoCmp(infoCmp, this.bools, this.ints, this.strings);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public boolean trackMouse(Terminal.MouseTracking mouseTracking) {
        return MouseSupport.trackMouse(this, mouseTracking);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal
    public MouseEvent readMouseEvent(IntSupplier intSupplier) {
        MouseEvent mouse = MouseSupport.readMouse(intSupplier, this.lastMouseEvent);
        this.lastMouseEvent = mouse;
        return mouse;
    }
}
