package org.jetbrains.kotlin.org.jline.terminal;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ServiceLoader;
import java.util.function.Supplier;
import org.jetbrains.kotlin.org.jline.terminal.impl.AbstractPosixTerminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.DumbTerminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.ExecPty;
import org.jetbrains.kotlin.org.jline.terminal.impl.ExternalTerminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.PosixPtyTerminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.PosixSysTerminal;
import org.jetbrains.kotlin.org.jline.terminal.spi.JansiSupport;
import org.jetbrains.kotlin.org.jline.terminal.spi.JnaSupport;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;
import org.jetbrains.kotlin.org.jline.utils.Log;
import org.jetbrains.kotlin.org.jline.utils.OSUtils;

/* JADX INFO: loaded from: classes4.dex */
public final class TerminalBuilder {
    private Attributes attributes;
    private Boolean dumb;
    private String encoding;
    private Boolean exec;
    private InputStream in;
    private Boolean jansi;
    private Boolean jna;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f936name;
    private OutputStream out;
    private Size size;
    private Boolean system;
    private String type;
    private boolean nativeSignals = false;
    private Terminal.SignalHandler signalHandler = Terminal.SignalHandler.SIG_DFL;

    public static Terminal terminal() throws IOException {
        return builder().build();
    }

    public static TerminalBuilder builder() {
        return new TerminalBuilder();
    }

    private TerminalBuilder() {
    }

    public Terminal build() throws IOException {
        final Terminal terminalDoBuild = doBuild();
        Log.debug((Supplier<String>) new Supplier() { // from class: org.jetbrains.kotlin.org.jline.terminal.TerminalBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return TerminalBuilder.lambda$build$0(terminalDoBuild);
            }
        });
        if (terminalDoBuild instanceof AbstractPosixTerminal) {
            Log.debug((Supplier<String>) new Supplier() { // from class: org.jetbrains.kotlin.org.jline.terminal.TerminalBuilder$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return TerminalBuilder.lambda$build$1(terminalDoBuild);
                }
            });
        }
        return terminalDoBuild;
    }

    static /* synthetic */ String lambda$build$0(Terminal terminal) {
        return "Using terminal " + terminal.getClass().getSimpleName();
    }

    static /* synthetic */ String lambda$build$1(Terminal terminal) {
        return "Using pty " + ((AbstractPosixTerminal) terminal).getPty().getClass().getSimpleName();
    }

    /* JADX WARN: Code duplicated, block: B:105:0x01f6  */
    /* JADX WARN: Code duplicated, block: B:107:0x01fc  */
    /* JADX WARN: Code duplicated, block: B:108:0x0206  */
    /* JADX WARN: Code duplicated, block: B:111:0x0213  */
    /* JADX WARN: Code duplicated, block: B:112:0x0215  */
    /* JADX WARN: Code duplicated, block: B:135:0x02b5  */
    /* JADX WARN: Code duplicated, block: B:138:0x02bc  */
    /* JADX WARN: Code duplicated, block: B:144:0x0274 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:146:0x0184 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:158:0x0155 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    private Terminal doBuild() throws IOException {
        ExternalTerminal externalTerminal;
        Attributes attributes;
        Size size;
        String str;
        Object obj;
        String str2 = this.f936name;
        if (str2 == null) {
            str2 = "JLine terminal";
        }
        String str3 = str2;
        String strName = this.encoding;
        if (strName == null) {
            strName = System.getProperty("org.jetbrains.kotlin.org.jline.terminal.encoding");
        }
        if (strName == null) {
            strName = Charset.defaultCharset().name();
        }
        String str4 = strName;
        String property = this.type;
        if (property == null) {
            property = System.getProperty("org.jetbrains.kotlin.org.jline.terminal.type");
        }
        if (property == null) {
            property = System.getenv("TERM");
        }
        String str5 = property;
        Boolean bool = this.jna;
        if (bool == null) {
            bool = getBoolean("org.jetbrains.kotlin.org.jline.terminal.jna", true);
        }
        Boolean bool2 = this.jansi;
        if (bool2 == null) {
            bool2 = getBoolean("org.jetbrains.kotlin.org.jline.terminal.jansi", true);
        }
        Boolean bool3 = bool2;
        Boolean bool4 = this.exec;
        if (bool4 == null) {
            bool4 = getBoolean("org.jetbrains.kotlin.org.jline.terminal.exec", true);
        }
        Boolean bool5 = bool4;
        Boolean bool6 = this.dumb;
        if (bool6 == null) {
            bool6 = getBoolean("org.jetbrains.kotlin.org.jline.terminal.dumb", null);
        }
        Boolean bool7 = bool6;
        Boolean bool8 = this.system;
        String str6 = "Error creating JANSI based terminal: ";
        if ((bool8 != null && bool8.booleanValue()) || (this.system == null && this.in == null && this.out == null)) {
            if (this.attributes != null || this.size != null) {
                Log.warn("Attributes and size fields are ignored when creating a system terminal");
            }
            IllegalStateException illegalStateException = new IllegalStateException("Unable to create a system terminal");
            if (OSUtils.IS_CYGWIN || OSUtils.IS_MINGW) {
                bool7 = bool7;
                illegalStateException = illegalStateException;
                if (bool5.booleanValue()) {
                    try {
                        Pty ptyCurrent = ExecPty.current();
                        if ("xterm".equals(str5) && this.type == null && System.getProperty("org.jetbrains.kotlin.org.jline.terminal.type") == null) {
                            str5 = "xterm-256color";
                        }
                        return new PosixSysTerminal(str3, str5, ptyCurrent, str4, this.nativeSignals, this.signalHandler);
                    } catch (IOException e) {
                        Log.debug("Error creating EXEC based terminal: ", e.getMessage(), e);
                        illegalStateException.addSuppressed(e);
                    }
                }
            } else if (OSUtils.IS_WINDOWS) {
                if (bool.booleanValue()) {
                    try {
                        return ((JnaSupport) load(JnaSupport.class)).winSysTerminal(str3, this.nativeSignals, this.signalHandler);
                    } catch (Throwable th) {
                        Log.debug("Error creating JNA based terminal: ", th.getMessage(), th);
                        illegalStateException.addSuppressed(th);
                    }
                }
                if (bool3.booleanValue()) {
                    try {
                        return ((JansiSupport) load(JansiSupport.class)).winSysTerminal(str3, this.nativeSignals, this.signalHandler);
                    } catch (Throwable th2) {
                        Log.debug("Error creating JANSI based terminal: ", th2.getMessage(), th2);
                        illegalStateException.addSuppressed(th2);
                    }
                }
                bool7 = bool7;
                illegalStateException = illegalStateException;
            } else {
                if (bool.booleanValue()) {
                    try {
                        obj = "Error creating EXEC based terminal: ";
                        try {
                            return new PosixSysTerminal(str3, str5, ((JnaSupport) load(JnaSupport.class)).current(), str4, this.nativeSignals, this.signalHandler);
                        } catch (Throwable th3) {
                            th = th3;
                            Log.debug("Error creating JNA based terminal: ", th.getMessage(), th);
                            illegalStateException.addSuppressed(th);
                            if (bool3.booleanValue()) {
                                try {
                                    return new PosixSysTerminal(str3, str5, ((JansiSupport) load(JansiSupport.class)).current(), str4, this.nativeSignals, this.signalHandler);
                                } catch (Throwable th4) {
                                    Log.debug("Error creating JANSI based terminal: ", th4.getMessage(), th4);
                                    illegalStateException.addSuppressed(th4);
                                }
                            }
                            if (bool5.booleanValue()) {
                                try {
                                    return new PosixSysTerminal(str3, str5, ExecPty.current(), str4, this.nativeSignals, this.signalHandler);
                                } catch (Throwable th5) {
                                    Log.debug(obj, th5.getMessage(), th5);
                                    illegalStateException.addSuppressed(th5);
                                }
                            }
                            if (bool7 != null) {
                            }
                            if (bool7 == null) {
                                if (Log.isDebugEnabled()) {
                                    Log.warn("Creating a dumb terminal", illegalStateException);
                                } else {
                                    Log.warn("Unable to create a system terminal, creating a dumb terminal (enable debug logging for more information)");
                                }
                            }
                            if (str5 != null) {
                                str = str5;
                            } else {
                                str = "dumb";
                            }
                            return new DumbTerminal(str3, str, new FileInputStream(FileDescriptor.in), new FileOutputStream(FileDescriptor.out), str4, this.signalHandler);
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        obj = "Error creating EXEC based terminal: ";
                    }
                } else {
                    obj = "Error creating EXEC based terminal: ";
                    bool5 = bool5;
                    bool7 = bool7;
                    illegalStateException = illegalStateException;
                }
                if (bool3.booleanValue()) {
                    return new PosixSysTerminal(str3, str5, ((JansiSupport) load(JansiSupport.class)).current(), str4, this.nativeSignals, this.signalHandler);
                }
                if (bool5.booleanValue()) {
                    return new PosixSysTerminal(str3, str5, ExecPty.current(), str4, this.nativeSignals, this.signalHandler);
                }
            }
            if (bool7 != null || bool7.booleanValue()) {
                if (bool7 == null) {
                    if (Log.isDebugEnabled()) {
                        Log.warn("Creating a dumb terminal", illegalStateException);
                    } else {
                        Log.warn("Unable to create a system terminal, creating a dumb terminal (enable debug logging for more information)");
                    }
                }
                if (str5 != null) {
                    str = str5;
                } else {
                    str = "dumb";
                }
                return new DumbTerminal(str3, str, new FileInputStream(FileDescriptor.in), new FileOutputStream(FileDescriptor.out), str4, this.signalHandler);
            }
            throw illegalStateException;
        }
        if (bool.booleanValue()) {
            try {
                try {
                    return new PosixPtyTerminal(str3, str5, ((JnaSupport) load(JnaSupport.class)).open(this.attributes, this.size), this.in, this.out, str4, this.signalHandler);
                } catch (Throwable th7) {
                    th = th7;
                    Log.debug("Error creating JNA based terminal: ", th.getMessage(), th);
                    if (bool3.booleanValue()) {
                        try {
                            return new PosixPtyTerminal(str3, str5, ((JansiSupport) load(JansiSupport.class)).open(this.attributes, this.size), this.in, this.out, str4, this.signalHandler);
                        } catch (Throwable th8) {
                            Log.debug(str6, th8.getMessage(), th8);
                        }
                    }
                    externalTerminal = new ExternalTerminal(str3, str5, this.in, this.out, str4, this.signalHandler);
                    attributes = this.attributes;
                    if (attributes != null) {
                        externalTerminal.setAttributes(attributes);
                    }
                    size = this.size;
                    if (size != null) {
                        externalTerminal.setSize(size);
                    }
                    return externalTerminal;
                }
            } catch (Throwable th9) {
                th = th9;
            }
        } else {
            str6 = "Error creating JANSI based terminal: ";
        }
        if (bool3.booleanValue()) {
            return new PosixPtyTerminal(str3, str5, ((JansiSupport) load(JansiSupport.class)).open(this.attributes, this.size), this.in, this.out, str4, this.signalHandler);
        }
        externalTerminal = new ExternalTerminal(str3, str5, this.in, this.out, str4, this.signalHandler);
        attributes = this.attributes;
        if (attributes != null) {
            externalTerminal.setAttributes(attributes);
        }
        size = this.size;
        if (size != null) {
            externalTerminal.setSize(size);
        }
        return externalTerminal;
    }

    private static Boolean getBoolean(String str, Boolean bool) {
        try {
            String property = System.getProperty(str);
            if (property != null) {
                return Boolean.valueOf(Boolean.parseBoolean(property));
            }
        } catch (IllegalArgumentException | NullPointerException unused) {
        }
        return bool;
    }

    private <S> S load(Class<S> cls) {
        return ServiceLoader.load(cls, cls.getClassLoader()).iterator().next();
    }
}
