package org.jetbrains.kotlin.org.jline.reader.impl;

import androidx.exifinterface.media.ExifInterface;
import com.android.dx.rop.code.RegisterSpec;
import com.android.tools.r8.dex.U$$ExternalSyntheticLambda0;
import com.beust.jcommander.Parameters;
import com.huawei.hms.framework.common.ContainerUtils;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.Flushable;
import java.io.IOError;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.StringWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import org.antlr.stringtemplate.language.ASTExpr;
import org.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.catrobat.catroid.ui.SpriteActivity;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiLiteralUtil;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.org.jline.keymap.BindingReader;
import org.jetbrains.kotlin.org.jline.keymap.KeyMap;
import org.jetbrains.kotlin.org.jline.reader.Binding;
import org.jetbrains.kotlin.org.jline.reader.Buffer;
import org.jetbrains.kotlin.org.jline.reader.Candidate;
import org.jetbrains.kotlin.org.jline.reader.Completer;
import org.jetbrains.kotlin.org.jline.reader.EOFError;
import org.jetbrains.kotlin.org.jline.reader.EndOfFileException;
import org.jetbrains.kotlin.org.jline.reader.Expander;
import org.jetbrains.kotlin.org.jline.reader.Highlighter;
import org.jetbrains.kotlin.org.jline.reader.History;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.org.jline.reader.Macro;
import org.jetbrains.kotlin.org.jline.reader.ParsedLine;
import org.jetbrains.kotlin.org.jline.reader.Parser;
import org.jetbrains.kotlin.org.jline.reader.Reference;
import org.jetbrains.kotlin.org.jline.reader.SyntaxError;
import org.jetbrains.kotlin.org.jline.reader.UserInterruptException;
import org.jetbrains.kotlin.org.jline.reader.Widget;
import org.jetbrains.kotlin.org.jline.reader.impl.history.DefaultHistory;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Cursor;
import org.jetbrains.kotlin.org.jline.terminal.MouseEvent;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.utils.AttributedString;
import org.jetbrains.kotlin.org.jline.utils.AttributedStringBuilder;
import org.jetbrains.kotlin.org.jline.utils.AttributedStyle;
import org.jetbrains.kotlin.org.jline.utils.Curses;
import org.jetbrains.kotlin.org.jline.utils.Display;
import org.jetbrains.kotlin.org.jline.utils.InfoCmp;
import org.jetbrains.kotlin.org.jline.utils.Levenshtein;
import org.jetbrains.kotlin.org.jline.utils.Log;
import org.jetbrains.kotlin.org.jline.utils.WCWidth;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes4.dex */
public class LineReaderImpl implements Flushable, LineReader {
    protected final String appName;
    protected final BindingReader bindingReader;
    protected Map<String, Widget> builtinWidgets;
    protected int count;
    protected Display display;
    protected int findChar;
    protected int findDir;
    protected int findTailAdd;
    private boolean forceChar;
    private boolean forceLine;
    protected boolean isArgDigit;
    protected boolean isUndo;
    protected String keyMap;
    protected final Map<String, KeyMap<Binding>> keyMaps;
    protected Character mask;
    protected int mult;
    protected ParsedLine parsedLine;
    protected Supplier<AttributedString> post;
    protected AttributedString prompt;
    protected boolean reading;
    protected LineReader.RegionType regionActive;
    protected int regionMark;
    protected int repeatCount;
    protected AttributedString rightPrompt;
    protected CharSequence searchBuffer;
    private int searchDir;
    private String searchString;
    protected boolean skipRedisplay;
    protected final Terminal terminal;
    protected final Map<String, Object> variables;
    protected Map<String, Widget> widgets;
    protected History history = new DefaultHistory();
    protected Completer completer = null;
    protected Highlighter highlighter = new DefaultHighlighter();
    protected Parser parser = new DefaultParser();
    protected Expander expander = new DefaultExpander();
    protected final Map<LineReader.Option, Boolean> options = new HashMap();
    protected final Buffer buf = new BufferImpl();
    protected final Size size = new Size();
    protected Map<Integer, String> modifiedHistory = new HashMap();
    protected Buffer historyBuffer = null;
    protected StringBuffer searchTerm = null;
    protected int searchIndex = -1;
    protected String yankBuffer = "";
    protected ViMoveMode viMoveMode = ViMoveMode.NORMAL;
    protected KillRing killRing = new KillRing();
    protected UndoTree<Buffer> undo = new UndoTree<>(new Consumer() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda79
        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            this.f$0.setBuffer((Buffer) obj);
        }
    });
    protected State state = State.DONE;
    protected int universal = 4;
    protected boolean overTyping = false;
    protected int smallTerminalOffset = 0;

    protected enum BellType {
        NONE,
        AUDIBLE,
        VISIBLE
    }

    protected enum CompletionType {
        Expand,
        ExpandComplete,
        Complete,
        List
    }

    protected enum State {
        NORMAL,
        DONE,
        EOF,
        INTERRUPT
    }

    protected enum ViMoveMode {
        NORMAL,
        YANK,
        DELETE,
        CHANGE
    }

    static /* synthetic */ int lambda$toColumns$34(int i, int i2, int i3) {
        return (i2 * i) + i3;
    }

    static /* synthetic */ int lambda$toColumns$35(int i, int i2, int i3) {
        return (i3 * i) + i2;
    }

    protected int getBracketType(int i) {
        if (i == 40) {
            return 3;
        }
        if (i == 41) {
            return -3;
        }
        if (i == 91) {
            return 1;
        }
        if (i == 93) {
            return -1;
        }
        if (i != 123) {
            return i != 125 ? 0 : -2;
        }
        return 2;
    }

    protected boolean undefinedKey() {
        return false;
    }

    public LineReaderImpl(Terminal terminal, String str, Map<String, Object> map) {
        Objects.requireNonNull(terminal, "terminal can not be null");
        this.terminal = terminal;
        this.appName = str == null ? "JLine" : str;
        if (map != null) {
            this.variables = map;
        } else {
            this.variables = new HashMap();
        }
        this.keyMaps = defaultKeyMaps();
        this.builtinWidgets = builtinWidgets();
        this.widgets = new HashMap(this.builtinWidgets);
        this.bindingReader = new BindingReader(terminal.reader());
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Terminal getTerminal() {
        return this.terminal;
    }

    public String getAppName() {
        return this.appName;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Map<String, KeyMap<Binding>> getKeyMaps() {
        return this.keyMaps;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public KeyMap<Binding> getKeys() {
        return this.keyMaps.get(this.keyMap);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Map<String, Widget> getWidgets() {
        return this.widgets;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Map<String, Widget> getBuiltinWidgets() {
        return Collections.unmodifiableMap(this.builtinWidgets);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Buffer getBuffer() {
        return this.buf;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public void runMacro(String str) {
        this.bindingReader.runMacro(str);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public MouseEvent readMouseEvent() {
        Terminal terminal = this.terminal;
        final BindingReader bindingReader = this.bindingReader;
        bindingReader.getClass();
        return terminal.readMouseEvent(new IntSupplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda74
            @Override // java.util.function.IntSupplier
            public final int getAsInt() {
                return bindingReader.readCharacter();
            }
        });
    }

    public void setCompleter(Completer completer) {
        this.completer = completer;
    }

    public void setHistory(History history) {
        Objects.requireNonNull(history);
        this.history = history;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public History getHistory() {
        return this.history;
    }

    public void setHighlighter(Highlighter highlighter) {
        this.highlighter = highlighter;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Highlighter getHighlighter() {
        return this.highlighter;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Parser getParser() {
        return this.parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Expander getExpander() {
        return this.expander;
    }

    public void setExpander(Expander expander) {
        this.expander = expander;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String readLine() throws UserInterruptException, EndOfFileException {
        return readLine(null, null, null, null);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String readLine(Character ch) throws UserInterruptException, EndOfFileException {
        return readLine(null, null, ch, null);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String readLine(String str) throws UserInterruptException, EndOfFileException {
        return readLine(str, null, null, null);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String readLine(String str, Character ch) throws UserInterruptException, EndOfFileException {
        return readLine(str, null, ch, null);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String readLine(String str, Character ch, String str2) throws UserInterruptException, EndOfFileException {
        return readLine(str, null, ch, str2);
    }

    /* JADX WARN: Code duplicated, block: B:128:0x0291  */
    /* JADX WARN: Code duplicated, block: B:130:0x0298  */
    /* JADX WARN: Code duplicated, block: B:132:0x02a1  */
    /* JADX WARN: Code duplicated, block: B:134:0x02aa  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String readLine(String str, String str2, Character ch, String str3) throws Throwable {
        Terminal.SignalHandler signalHandlerHandle;
        Terminal.SignalHandler signalHandler;
        Terminal.SignalHandler signalHandler2;
        Terminal.SignalHandler signalHandlerHandle2;
        Terminal.SignalHandler signalHandlerHandle3;
        Attributes attributesEnterRawMode;
        final Thread threadCurrentThread = Thread.currentThread();
        boolean zEquals = "dumb".equals(this.terminal.getType());
        Attributes attributes = null;
        attributes = null;
        attributes = null;
        Terminal.SignalHandler signalHandler3 = null;
        try {
            if (this.reading) {
                throw new IllegalStateException();
            }
            this.reading = true;
            this.mask = ch;
            this.repeatCount = 0;
            this.mult = 1;
            this.regionActive = LineReader.RegionType.NONE;
            this.regionMark = -1;
            this.smallTerminalOffset = 0;
            this.state = State.NORMAL;
            this.modifiedHistory.clear();
            setPrompt(str);
            setRightPrompt(str2);
            this.buf.clear();
            if (str3 != null) {
                this.buf.write(str3);
            }
            this.undo.clear();
            this.parsedLine = null;
            this.keyMap = "main";
            History history = this.history;
            if (history != null) {
                history.attach(this);
            }
            signalHandlerHandle = this.terminal.handle(Terminal.Signal.INT, new Terminal.SignalHandler() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda91
                @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal.SignalHandler
                public final void handle(Terminal.Signal signal) {
                    threadCurrentThread.interrupt();
                }
            });
            try {
                signalHandlerHandle2 = this.terminal.handle(Terminal.Signal.WINCH, new Terminal.SignalHandler() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda92
                    @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal.SignalHandler
                    public final void handle(Terminal.Signal signal) {
                        this.f$0.handleSignal(signal);
                    }
                });
                try {
                    signalHandlerHandle3 = this.terminal.handle(Terminal.Signal.CONT, new Terminal.SignalHandler() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda92
                        @Override // org.jetbrains.kotlin.org.jline.terminal.Terminal.SignalHandler
                        public final void handle(Terminal.Signal signal) {
                            this.f$0.handleSignal(signal);
                        }
                    });
                    try {
                        attributesEnterRawMode = this.terminal.enterRawMode();
                        try {
                            this.size.copy(this.terminal.getSize());
                            this.display = new Display(this.terminal, false);
                            if (this.size.getRows() == 0 || this.size.getColumns() == 0) {
                                this.display.resize(1, Integer.MAX_VALUE);
                            } else {
                                this.display.resize(this.size.getRows(), this.size.getColumns());
                            }
                            if (isSet(LineReader.Option.DELAY_LINE_WRAP)) {
                                this.display.setDelayLineWrap(true);
                            }
                            if (!zEquals) {
                                this.terminal.puts(InfoCmp.Capability.keypad_xmit, new Object[0]);
                                if (isSet(LineReader.Option.AUTO_FRESH_LINE)) {
                                    callWidget(LineReader.FRESH_LINE);
                                }
                                if (isSet(LineReader.Option.MOUSE)) {
                                    this.terminal.trackMouse(Terminal.MouseTracking.Normal);
                                }
                            } else {
                                Attributes attributes2 = new Attributes(attributesEnterRawMode);
                                attributes2.setInputFlag(Attributes.InputFlag.IGNCR, true);
                                this.terminal.setAttributes(attributes2);
                            }
                            callWidget(LineReader.CALLBACK_INIT);
                            this.undo.newState(this.buf.copy());
                            redrawLine();
                            redisplay();
                            while (true) {
                                Binding binding = readBinding(getKeys(), (!isInViCmdMode() || this.regionActive == LineReader.RegionType.NONE) ? null : this.keyMaps.get(LineReader.VISUAL));
                                if (binding != null) {
                                    Log.trace("Binding: ", binding);
                                    if (this.buf.length() == 0 && getLastBinding().charAt(0) == attributesEnterRawMode.getControlChar(Attributes.ControlChar.VEOF)) {
                                        throw new EndOfFileException();
                                    }
                                    this.isArgDigit = false;
                                    int i = this.repeatCount;
                                    if (i == 0) {
                                        i = 1;
                                    }
                                    this.count = i * this.mult;
                                    this.isUndo = false;
                                    Buffer bufferCopy = this.buf.copy();
                                    if (!getWidget(binding).apply()) {
                                        beep();
                                    }
                                    if (!this.isUndo && !bufferCopy.toString().equals(this.buf.toString())) {
                                        this.undo.newState(this.buf.copy());
                                    }
                                    int i2 = AnonymousClass1.$SwitchMap$org$jline$reader$impl$LineReaderImpl$State[this.state.ordinal()];
                                    if (i2 == 1) {
                                        String strFinishBuffer = finishBuffer();
                                        cleanup();
                                        this.reading = false;
                                        if (attributesEnterRawMode != null) {
                                            this.terminal.setAttributes(attributesEnterRawMode);
                                        }
                                        if (signalHandlerHandle != null) {
                                            this.terminal.handle(Terminal.Signal.INT, signalHandlerHandle);
                                        }
                                        if (signalHandlerHandle2 != null) {
                                            this.terminal.handle(Terminal.Signal.WINCH, signalHandlerHandle2);
                                        }
                                        if (signalHandlerHandle3 != null) {
                                            this.terminal.handle(Terminal.Signal.CONT, signalHandlerHandle3);
                                        }
                                        return strFinishBuffer;
                                    }
                                    if (i2 == 2) {
                                        throw new EndOfFileException();
                                    }
                                    if (i2 == 3) {
                                        throw new UserInterruptException(this.buf.toString());
                                    }
                                    if (!this.isArgDigit) {
                                        this.repeatCount = 0;
                                        this.mult = 1;
                                    }
                                    if (!zEquals) {
                                        redisplay();
                                    }
                                } else {
                                    cleanup();
                                    this.reading = false;
                                    if (attributesEnterRawMode != null) {
                                        this.terminal.setAttributes(attributesEnterRawMode);
                                    }
                                    if (signalHandlerHandle != null) {
                                        this.terminal.handle(Terminal.Signal.INT, signalHandlerHandle);
                                    }
                                    if (signalHandlerHandle2 != null) {
                                        this.terminal.handle(Terminal.Signal.WINCH, signalHandlerHandle2);
                                    }
                                    if (signalHandlerHandle3 != null) {
                                        this.terminal.handle(Terminal.Signal.CONT, signalHandlerHandle3);
                                    }
                                    return null;
                                }
                            }
                        } catch (IOError e) {
                            signalHandler3 = signalHandlerHandle;
                            e = e;
                            try {
                                if (e.getCause() instanceof InterruptedIOException) {
                                    throw new UserInterruptException(this.buf.toString());
                                }
                                throw e;
                            } catch (Throwable th) {
                                th = th;
                                Terminal.SignalHandler signalHandler4 = signalHandlerHandle3;
                                signalHandler = signalHandlerHandle2;
                                signalHandlerHandle = signalHandler3;
                                attributes = attributesEnterRawMode;
                                signalHandler2 = signalHandler4;
                                cleanup();
                                this.reading = false;
                                if (attributes != null) {
                                    this.terminal.setAttributes(attributes);
                                }
                                if (signalHandlerHandle != null) {
                                    this.terminal.handle(Terminal.Signal.INT, signalHandlerHandle);
                                }
                                if (signalHandler != null) {
                                    this.terminal.handle(Terminal.Signal.WINCH, signalHandler);
                                }
                                if (signalHandler2 != null) {
                                    this.terminal.handle(Terminal.Signal.CONT, signalHandler2);
                                }
                                throw th;
                            }
                        } catch (Throwable th2) {
                            attributes = attributesEnterRawMode;
                            signalHandler2 = signalHandlerHandle3;
                            signalHandler = signalHandlerHandle2;
                            signalHandlerHandle = signalHandlerHandle;
                            th = th2;
                            cleanup();
                            this.reading = false;
                            if (attributes != null) {
                                this.terminal.setAttributes(attributes);
                            }
                            if (signalHandlerHandle != null) {
                                this.terminal.handle(Terminal.Signal.INT, signalHandlerHandle);
                            }
                            if (signalHandler != null) {
                                this.terminal.handle(Terminal.Signal.WINCH, signalHandler);
                            }
                            if (signalHandler2 != null) {
                                this.terminal.handle(Terminal.Signal.CONT, signalHandler2);
                            }
                            throw th;
                        }
                    } catch (IOError e2) {
                        signalHandler3 = signalHandlerHandle;
                        e = e2;
                        attributesEnterRawMode = null;
                    } catch (Throwable th3) {
                        th = th3;
                        signalHandler2 = signalHandlerHandle3;
                        signalHandler = signalHandlerHandle2;
                        cleanup();
                        this.reading = false;
                        if (attributes != null) {
                            this.terminal.setAttributes(attributes);
                        }
                        if (signalHandlerHandle != null) {
                            this.terminal.handle(Terminal.Signal.INT, signalHandlerHandle);
                        }
                        if (signalHandler != null) {
                            this.terminal.handle(Terminal.Signal.WINCH, signalHandler);
                        }
                        if (signalHandler2 != null) {
                            this.terminal.handle(Terminal.Signal.CONT, signalHandler2);
                        }
                        throw th;
                    }
                } catch (IOError e3) {
                    attributesEnterRawMode = null;
                    signalHandler3 = signalHandlerHandle;
                    e = e3;
                    signalHandlerHandle3 = null;
                } catch (Throwable th4) {
                    signalHandler2 = null;
                    th = th4;
                }
            } catch (IOError e4) {
                signalHandlerHandle3 = null;
                attributesEnterRawMode = null;
                signalHandler3 = signalHandlerHandle;
                e = e4;
                signalHandlerHandle2 = null;
            } catch (Throwable th5) {
                signalHandler = null;
                signalHandler2 = null;
                signalHandlerHandle = signalHandlerHandle;
                th = th5;
            }
        } catch (IOError e5) {
            e = e5;
            signalHandlerHandle2 = null;
            signalHandlerHandle3 = null;
            attributesEnterRawMode = null;
        } catch (Throwable th6) {
            th = th6;
            signalHandlerHandle = null;
            signalHandler = null;
            signalHandler2 = null;
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$jline$reader$impl$LineReaderImpl$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$org$jline$reader$impl$LineReaderImpl$State = iArr;
            try {
                iArr[State.DONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$jline$reader$impl$LineReaderImpl$State[State.EOF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$jline$reader$impl$LineReaderImpl$State[State.INTERRUPT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    protected boolean freshLine() {
        boolean booleanCapability = this.terminal.getBooleanCapability(InfoCmp.Capability.auto_right_margin);
        int i = 0;
        boolean z = booleanCapability && this.terminal.getBooleanCapability(InfoCmp.Capability.eat_newline_glitch);
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        attributedStringBuilder.style(AttributedStyle.DEFAULT.foreground(8));
        attributedStringBuilder.append((CharSequence) "~");
        attributedStringBuilder.style(AttributedStyle.DEFAULT);
        if (!booleanCapability || z) {
            while (i < this.size.getColumns() - 1) {
                attributedStringBuilder.append((CharSequence) " ");
                i++;
            }
            attributedStringBuilder.append((CharSequence) KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
            attributedStringBuilder.append((CharSequence) " ");
            attributedStringBuilder.append((CharSequence) KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
        } else {
            String stringCapability = this.terminal.getStringCapability(InfoCmp.Capability.clr_eol);
            if (stringCapability != null) {
                StringWriter stringWriter = new StringWriter();
                try {
                    Curses.tputs(stringWriter, stringCapability, new Object[0]);
                } catch (IOException unused) {
                }
                attributedStringBuilder.append((CharSequence) stringWriter.toString());
            }
            while (i < this.size.getColumns() - 2) {
                attributedStringBuilder.append((CharSequence) " ");
                i++;
            }
            attributedStringBuilder.append((CharSequence) KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
            attributedStringBuilder.append((CharSequence) " ");
            attributedStringBuilder.append((CharSequence) KeyMap.key(this.terminal, InfoCmp.Capability.carriage_return));
        }
        print(attributedStringBuilder.toAnsi(this.terminal));
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public void callWidget(String str) {
        Widget widget;
        if (!this.reading) {
            throw new IllegalStateException();
        }
        try {
            if (str.startsWith(".")) {
                widget = this.builtinWidgets.get(str.substring(1));
            } else {
                widget = this.widgets.get(str);
            }
            if (widget != null) {
                widget.apply();
            }
        } catch (Throwable th) {
            Log.debug("Error executing widget '", str, FormatHelper.QUOTE, th);
        }
    }

    public boolean redrawLine() {
        this.display.reset();
        return true;
    }

    public void putString(CharSequence charSequence) {
        this.buf.write(charSequence, this.overTyping);
    }

    @Override // java.io.Flushable
    public void flush() {
        this.terminal.flush();
    }

    public int readCharacter() {
        return this.bindingReader.readCharacter();
    }

    public int peekCharacter(long j) {
        return this.bindingReader.peekCharacter(j);
    }

    public Binding readBinding(KeyMap<Binding> keyMap) {
        return readBinding(keyMap, null);
    }

    public Binding readBinding(KeyMap<Binding> keyMap, KeyMap<Binding> keyMap2) {
        Binding binding = (Binding) this.bindingReader.readBinding(keyMap, keyMap2);
        if (binding instanceof Reference) {
            String strName = ((Reference) binding).name();
            if (!LineReader.YANK_POP.equals(strName) && !LineReader.YANK.equals(strName)) {
                this.killRing.resetLastYank();
            }
            if (!LineReader.KILL_LINE.equals(strName) && !LineReader.KILL_WHOLE_LINE.equals(strName) && !LineReader.BACKWARD_KILL_WORD.equals(strName) && !LineReader.KILL_WORD.equals(strName)) {
                this.killRing.resetLastKill();
            }
        }
        return binding;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public ParsedLine getParsedLine() {
        return this.parsedLine;
    }

    public String getLastBinding() {
        return this.bindingReader.getLastBinding();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String getSearchTerm() {
        StringBuffer stringBuffer = this.searchTerm;
        if (stringBuffer != null) {
            return stringBuffer.toString();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public LineReader.RegionType getRegionActive() {
        return this.regionActive;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public int getRegionMark() {
        return this.regionMark;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public boolean setKeyMap(String str) {
        if (this.keyMaps.get(str) == null) {
            return false;
        }
        this.keyMap = str;
        if (!this.reading) {
            return true;
        }
        callWidget(LineReader.CALLBACK_KEYMAP);
        return true;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public String getKeyMap() {
        return this.keyMap;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Map<String, Object> getVariables() {
        return this.variables;
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Object getVariable(String str) {
        return this.variables.get(str);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public void setVariable(String str, Object obj) {
        this.variables.put(str, obj);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public boolean isSet(LineReader.Option option) {
        Boolean bool = this.options.get(option);
        return bool != null ? bool.booleanValue() : option.isDef();
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public void setOpt(LineReader.Option option) {
        this.options.put(option, Boolean.TRUE);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public void unsetOpt(LineReader.Option option) {
        this.options.put(option, Boolean.FALSE);
    }

    protected String finishBuffer() {
        String string;
        String string2 = this.buf.toString();
        if (isSet(LineReader.Option.DISABLE_EVENT_EXPANSION)) {
            string = string2;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean z = false;
            for (int i = 0; i < string2.length(); i++) {
                char cCharAt = string2.charAt(i);
                if (z) {
                    if (cCharAt != '\n') {
                        sb.append(cCharAt);
                    }
                    z = false;
                } else if (cCharAt == '\\') {
                    z = true;
                } else {
                    sb.append(cCharAt);
                }
            }
            string = sb.toString();
        }
        if (string.length() > 0 && this.mask == null) {
            this.history.add(Instant.now(), string2);
        }
        return string;
    }

    protected void handleSignal(Terminal.Signal signal) {
        if (signal == Terminal.Signal.WINCH) {
            this.size.copy(this.terminal.getSize());
            this.display.resize(this.size.getRows(), this.size.getColumns());
            redisplay();
        } else if (signal == Terminal.Signal.CONT) {
            this.terminal.enterRawMode();
            this.size.copy(this.terminal.getSize());
            this.display.resize(this.size.getRows(), this.size.getColumns());
            this.terminal.puts(InfoCmp.Capability.keypad_xmit, new Object[0]);
            redrawLine();
            redisplay();
        }
    }

    protected Widget getWidget(Object obj) {
        Widget widget;
        if (obj instanceof Widget) {
            return (Widget) obj;
        }
        if (obj instanceof Macro) {
            final String sequence = ((Macro) obj).getSequence();
            widget = new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda86
                @Override // org.jetbrains.kotlin.org.jline.reader.Widget
                public final boolean apply() {
                    return this.f$0.m7376x7a75eb44(sequence);
                }
            };
        } else if (obj instanceof Reference) {
            final String strName = ((Reference) obj).name();
            widget = this.widgets.get(strName);
            if (widget == null) {
                widget = new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda87
                    @Override // org.jetbrains.kotlin.org.jline.reader.Widget
                    public final boolean apply() {
                        return this.f$0.m7377x9be184c6(strName);
                    }
                };
            }
        } else {
            return new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda89
                @Override // org.jetbrains.kotlin.org.jline.reader.Widget
                public final boolean apply() {
                    return this.f$0.m7378xbd4d1e48();
                }
            };
        }
        return widget;
    }

    /* JADX INFO: renamed from: lambda$getWidget$1$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ boolean m7376x7a75eb44(String str) {
        this.bindingReader.runMacro(str);
        return true;
    }

    static /* synthetic */ AttributedString lambda$null$2(String str) {
        return new AttributedString("No such widget `" + str + FormatHelper.QUOTE);
    }

    /* JADX INFO: renamed from: lambda$getWidget$3$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ boolean m7377x9be184c6(final String str) {
        this.post = new Supplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda93
            @Override // java.util.function.Supplier
            public final Object get() {
                return LineReaderImpl.lambda$null$2(str);
            }
        };
        return false;
    }

    static /* synthetic */ AttributedString lambda$null$4() {
        return new AttributedString("Unsupported widget");
    }

    /* JADX INFO: renamed from: lambda$getWidget$5$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ boolean m7378xbd4d1e48() {
        this.post = new Supplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda77
            @Override // java.util.function.Supplier
            public final Object get() {
                return LineReaderImpl.lambda$null$4();
            }
        };
        return false;
    }

    protected void setPrompt(String str) {
        AttributedString attributedStringExpandPromptPattern;
        if (str == null) {
            attributedStringExpandPromptPattern = AttributedString.EMPTY;
        } else {
            attributedStringExpandPromptPattern = expandPromptPattern(str, 0, "", 0);
        }
        this.prompt = attributedStringExpandPromptPattern;
    }

    protected void setRightPrompt(String str) {
        AttributedString attributedStringExpandPromptPattern;
        if (str == null) {
            attributedStringExpandPromptPattern = AttributedString.EMPTY;
        } else {
            attributedStringExpandPromptPattern = expandPromptPattern(str, 0, "", 0);
        }
        this.rightPrompt = attributedStringExpandPromptPattern;
    }

    protected void setBuffer(Buffer buffer) {
        setBuffer(buffer.toString());
        this.buf.cursor(buffer.cursor());
    }

    protected void setBuffer(String str) {
        this.buf.clear();
        this.buf.write(str);
    }

    protected String viDeleteChangeYankToRemap(String str) {
        str.hashCode();
        switch (str) {
            case "end-of-line":
            case "vi-find-prev-char":
            case "vi-find-next-char-skip":
            case "vi-forward-word-end":
            case "digit-argument":
            case "vi-repeat-find":
            case "vi-first-non-blank":
            case "vi-change-to":
            case "vi-forward-char":
            case "vi-forward-word":
            case "forward-char":
            case "vi-delete":
            case "vi-backward-char":
            case "vi-backward-word":
            case "vi-find-next-char":
            case "vi-find-prev-char-skip":
            case "abort":
            case "neg-argument":
            case "vi-yank":
            case "vi-rev-repeat-find":
            case "vi-goto-column":
            case "backward-char":
            case "vi-digit-or-beginning-of-line":
            case "vi-match-bracket":
                return str;
            default:
                return LineReader.VI_CMD_MODE;
        }
    }

    protected int switchCase(int i) {
        if (Character.isUpperCase(i)) {
            return Character.toLowerCase(i);
        }
        return Character.isLowerCase(i) ? Character.toUpperCase(i) : i;
    }

    protected boolean isInViMoveOperation() {
        return this.viMoveMode != ViMoveMode.NORMAL;
    }

    protected boolean isInViChangeOperation() {
        return this.viMoveMode == ViMoveMode.CHANGE;
    }

    protected boolean isInViCmdMode() {
        return LineReader.VICMD.equals(this.keyMap);
    }

    protected boolean viForwardChar() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda99(this));
        }
        int iFindeol = findeol();
        if (isInViCmdMode() && !isInViMoveOperation()) {
            iFindeol--;
        }
        if (this.buf.cursor() >= iFindeol) {
            return false;
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0 || this.buf.cursor() >= iFindeol) {
                break;
            }
            this.buf.move(1);
        }
        return true;
    }

    protected boolean viBackwardChar() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda113(this));
        }
        if (this.buf.cursor() == findbol()) {
            return false;
        }
        do {
            int i = this.count;
            this.count = i - 1;
            if (i > 0 && this.buf.cursor() > 0) {
                this.buf.move(-1);
            }
            return true;
        } while (this.buf.currChar() != 10);
        this.buf.move(1);
        return true;
    }

    protected boolean forwardWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda94(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                break;
            }
            while (this.buf.cursor() < this.buf.length() && isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            if (isInViChangeOperation() && this.count == 0) {
                break;
            }
            while (this.buf.cursor() < this.buf.length() && !isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
        }
        return true;
    }

    protected boolean viForwardWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda94(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            if (isViAlphaNum(this.buf.currChar())) {
                while (this.buf.cursor() < this.buf.length() && isViAlphaNum(this.buf.currChar())) {
                    this.buf.move(1);
                }
            } else {
                while (this.buf.cursor() < this.buf.length() && !isViAlphaNum(this.buf.currChar()) && !isWhitespace(this.buf.currChar())) {
                    this.buf.move(1);
                }
            }
            if (isInViChangeOperation() && this.count == 0) {
                return true;
            }
            int i2 = this.buf.currChar() == 10 ? 1 : 0;
            while (this.buf.cursor() < this.buf.length() && i2 < 2 && isWhitespace(this.buf.currChar())) {
                this.buf.move(1);
                i2 += this.buf.currChar() == 10 ? 1 : 0;
            }
        }
    }

    protected boolean viForwardBlankWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda49(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.cursor() < this.buf.length() && !isWhitespace(this.buf.currChar())) {
                this.buf.move(1);
            }
            if (isInViChangeOperation() && this.count == 0) {
                return true;
            }
            int i2 = this.buf.currChar() == 10 ? 1 : 0;
            while (this.buf.cursor() < this.buf.length() && i2 < 2 && isWhitespace(this.buf.currChar())) {
                this.buf.move(1);
                i2 += this.buf.currChar() == 10 ? 1 : 0;
            }
        }
    }

    protected boolean emacsForwardWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda170(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.cursor() < this.buf.length() && !isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            if (isInViChangeOperation() && this.count == 0) {
                return true;
            }
            while (this.buf.cursor() < this.buf.length() && isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
        }
    }

    protected boolean viForwardBlankWordEnd() {
        if (this.count < 0) {
            return false;
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.cursor() < this.buf.length()) {
                this.buf.move(1);
                if (!isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
            while (this.buf.cursor() < this.buf.length()) {
                this.buf.move(1);
                if (isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
        }
    }

    protected boolean viForwardWordEnd() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda94(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                break;
            }
            while (this.buf.cursor() < this.buf.length() && isWhitespace(this.buf.nextChar())) {
                this.buf.move(1);
            }
            if (this.buf.cursor() < this.buf.length()) {
                if (isViAlphaNum(this.buf.nextChar())) {
                    this.buf.move(1);
                    while (this.buf.cursor() < this.buf.length() && isViAlphaNum(this.buf.nextChar())) {
                        this.buf.move(1);
                    }
                } else {
                    this.buf.move(1);
                    while (this.buf.cursor() < this.buf.length() && !isViAlphaNum(this.buf.nextChar()) && !isWhitespace(this.buf.nextChar())) {
                        this.buf.move(1);
                    }
                }
            }
        }
        if (this.buf.cursor() < this.buf.length() && isInViMoveOperation()) {
            this.buf.move(1);
        }
        return true;
    }

    protected boolean backwardWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda3(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.cursor() > 0) {
                Buffer buffer = this.buf;
                if (isWord(buffer.atChar(buffer.cursor() - 1))) {
                    break;
                }
                this.buf.move(-1);
            }
            while (this.buf.cursor() > 0) {
                Buffer buffer2 = this.buf;
                if (!isWord(buffer2.atChar(buffer2.cursor() - 1))) {
                    break;
                }
                this.buf.move(-1);
            }
        }
    }

    protected boolean viBackwardWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda94(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            int i2 = 0;
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (!isWhitespace(this.buf.currChar())) {
                    break;
                }
                i2 += this.buf.currChar() == 10 ? 1 : 0;
                if (i2 == 2) {
                    this.buf.move(1);
                    break;
                }
            }
            if (this.buf.cursor() > 0) {
                if (isViAlphaNum(this.buf.currChar())) {
                    while (this.buf.cursor() > 0 && isViAlphaNum(this.buf.prevChar())) {
                        this.buf.move(-1);
                    }
                } else {
                    while (this.buf.cursor() > 0 && !isViAlphaNum(this.buf.prevChar()) && !isWhitespace(this.buf.prevChar())) {
                        this.buf.move(-1);
                    }
                }
            }
        }
    }

    protected boolean viBackwardBlankWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda111(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (!isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (isWhitespace(this.buf.currChar())) {
                    break;
                }
            }
        }
    }

    protected boolean viBackwardWordEnd() {
        char c;
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda116(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0 || this.buf.cursor() <= 1) {
                break;
            }
            if (isViAlphaNum(this.buf.currChar())) {
                c = 1;
            } else {
                c = !isWhitespace(this.buf.currChar()) ? (char) 2 : (char) 0;
            }
            while (this.buf.cursor() > 0) {
                boolean zIsViAlphaNum = c != 1 && isWhitespace(this.buf.currChar());
                if (c != 0) {
                    zIsViAlphaNum |= isViAlphaNum(this.buf.currChar());
                }
                if (zIsViAlphaNum == (c == 2)) {
                    break;
                }
                this.buf.move(-1);
            }
            while (this.buf.cursor() > 0 && isWhitespace(this.buf.currChar())) {
                this.buf.move(-1);
            }
        }
        return true;
    }

    protected boolean viBackwardBlankWordEnd() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda112(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.cursor() > 0 && !isWhitespace(this.buf.currChar())) {
                this.buf.move(-1);
            }
            while (this.buf.cursor() > 0 && isWhitespace(this.buf.currChar())) {
                this.buf.move(-1);
            }
        }
    }

    protected boolean emacsBackwardWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda171(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (isWord(this.buf.currChar())) {
                    break;
                }
            }
            while (this.buf.cursor() > 0) {
                this.buf.move(-1);
                if (!isWord(this.buf.currChar())) {
                    break;
                }
            }
        }
    }

    protected boolean backwardDeleteWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda159(this));
        }
        int iCursor = this.buf.cursor();
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i > 0) {
                while (iCursor > 0 && !isWord(this.buf.atChar(iCursor - 1))) {
                    iCursor--;
                }
                while (iCursor > 0 && isWord(this.buf.atChar(iCursor - 1))) {
                    iCursor--;
                }
            } else {
                Buffer buffer = this.buf;
                buffer.backspace(buffer.cursor() - iCursor);
                return true;
            }
        }
    }

    protected boolean viBackwardKillWord() {
        if (this.count < 0) {
            return false;
        }
        int iFindbol = findbol();
        int iCursor = this.buf.cursor();
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i > 0) {
                while (iCursor > iFindbol && isWhitespace(this.buf.atChar(iCursor - 1))) {
                    iCursor--;
                }
                if (iCursor > iFindbol) {
                    if (isViAlphaNum(this.buf.atChar(iCursor - 1))) {
                        while (iCursor > iFindbol && isViAlphaNum(this.buf.atChar(iCursor - 1))) {
                            iCursor--;
                        }
                    } else {
                        while (iCursor > iFindbol) {
                            int i2 = iCursor - 1;
                            if (isViAlphaNum(this.buf.atChar(i2)) || isWhitespace(this.buf.atChar(i2))) {
                                break;
                            }
                            iCursor--;
                        }
                    }
                }
            } else {
                KillRing killRing = this.killRing;
                Buffer buffer = this.buf;
                killRing.addBackwards(buffer.substring(iCursor, buffer.cursor()));
                Buffer buffer2 = this.buf;
                buffer2.backspace(buffer2.cursor() - iCursor);
                return true;
            }
        }
    }

    protected boolean backwardKillWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda17(this));
        }
        int iCursor = this.buf.cursor();
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i > 0) {
                while (iCursor > 0 && !isWord(this.buf.atChar(iCursor - 1))) {
                    iCursor--;
                }
                while (iCursor > 0 && isWord(this.buf.atChar(iCursor - 1))) {
                    iCursor--;
                }
            } else {
                KillRing killRing = this.killRing;
                Buffer buffer = this.buf;
                killRing.addBackwards(buffer.substring(iCursor, buffer.cursor()));
                Buffer buffer2 = this.buf;
                buffer2.backspace(buffer2.cursor() - iCursor);
                return true;
            }
        }
    }

    protected boolean copyPrevWord() {
        if (this.count <= 0) {
            return false;
        }
        int iCursor = this.buf.cursor();
        while (true) {
            int i = iCursor;
            while (i > 0 && !isWord(this.buf.atChar(i - 1))) {
                i--;
            }
            while (i > 0 && isWord(this.buf.atChar(i - 1))) {
                i--;
            }
            int i2 = this.count - 1;
            this.count = i2;
            if (i2 == 0) {
                Buffer buffer = this.buf;
                buffer.write(buffer.substring(i, iCursor));
                return true;
            }
            if (i == 0) {
                return false;
            }
            iCursor = i;
        }
    }

    protected boolean upCaseWord() {
        int iAbs = Math.abs(this.count);
        int iCursor = this.buf.cursor();
        while (true) {
            int i = iAbs - 1;
            if (iAbs <= 0) {
                break;
            }
            while (this.buf.cursor() < this.buf.length() && !isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            while (this.buf.cursor() < this.buf.length() && isWord(this.buf.currChar())) {
                Buffer buffer = this.buf;
                buffer.currChar(Character.toUpperCase(buffer.currChar()));
                this.buf.move(1);
            }
            iAbs = i;
        }
        if (this.count < 0) {
            this.buf.cursor(iCursor);
        }
        return true;
    }

    protected boolean downCaseWord() {
        int iAbs = Math.abs(this.count);
        int iCursor = this.buf.cursor();
        while (true) {
            int i = iAbs - 1;
            if (iAbs <= 0) {
                break;
            }
            while (this.buf.cursor() < this.buf.length() && !isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            while (this.buf.cursor() < this.buf.length() && isWord(this.buf.currChar())) {
                Buffer buffer = this.buf;
                buffer.currChar(Character.toLowerCase(buffer.currChar()));
                this.buf.move(1);
            }
            iAbs = i;
        }
        if (this.count < 0) {
            this.buf.cursor(iCursor);
        }
        return true;
    }

    protected boolean capitalizeWord() {
        int lowerCase;
        int iAbs = Math.abs(this.count);
        int iCursor = this.buf.cursor();
        while (true) {
            int i = iAbs - 1;
            if (iAbs <= 0) {
                break;
            }
            while (this.buf.cursor() < this.buf.length() && !isWord(this.buf.currChar())) {
                this.buf.move(1);
            }
            while (this.buf.cursor() < this.buf.length() && isWord(this.buf.currChar()) && !isAlpha(this.buf.currChar())) {
                this.buf.move(1);
            }
            boolean z = true;
            while (this.buf.cursor() < this.buf.length() && isWord(this.buf.currChar())) {
                Buffer buffer = this.buf;
                if (z) {
                    lowerCase = Character.toUpperCase(buffer.currChar());
                } else {
                    lowerCase = Character.toLowerCase(buffer.currChar());
                }
                buffer.currChar(lowerCase);
                this.buf.move(1);
                z = false;
            }
            iAbs = i;
        }
        if (this.count < 0) {
            this.buf.cursor(iCursor);
        }
        return true;
    }

    protected boolean deleteWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda9(this));
        }
        int iCursor = this.buf.cursor();
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i > 0) {
                while (iCursor < this.buf.length() && !isWord(this.buf.atChar(iCursor))) {
                    iCursor++;
                }
                while (iCursor < this.buf.length() && isWord(this.buf.atChar(iCursor))) {
                    iCursor++;
                }
            } else {
                Buffer buffer = this.buf;
                buffer.delete(iCursor - buffer.cursor());
                return true;
            }
        }
    }

    protected boolean killWord() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda32(this));
        }
        int iCursor = this.buf.cursor();
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i > 0) {
                while (iCursor < this.buf.length() && !isWord(this.buf.atChar(iCursor))) {
                    iCursor++;
                }
                while (iCursor < this.buf.length() && isWord(this.buf.atChar(iCursor))) {
                    iCursor++;
                }
            } else {
                KillRing killRing = this.killRing;
                Buffer buffer = this.buf;
                killRing.add(buffer.substring(buffer.cursor(), iCursor));
                Buffer buffer2 = this.buf;
                buffer2.delete(iCursor - buffer2.cursor());
                return true;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    protected boolean transposeWords() {
        ?? r6;
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        char c;
        int iCursor = this.buf.cursor() - 1;
        int iCursor2 = this.buf.cursor();
        while (this.buf.atChar(iCursor) != 0 && this.buf.atChar(iCursor) != 10) {
            iCursor--;
        }
        int i5 = iCursor + 1;
        while (this.buf.atChar(iCursor2) != 0 && this.buf.atChar(iCursor2) != 10) {
            iCursor2++;
        }
        if (iCursor2 - i5 < 2) {
            return false;
        }
        boolean zIsDelimiter = isDelimiter(this.buf.atChar(i5));
        boolean z2 = !zIsDelimiter ? 1 : 0;
        int i6 = !zIsDelimiter ? 1 : 0;
        int i7 = i5;
        while (i7 < iCursor2) {
            if (isDelimiter(this.buf.atChar(i7))) {
                r6 = z2;
                c = 0;
            } else {
                if (r6 == 0) {
                    i6++;
                }
                c = 1;
            }
            i7++;
            r6 = c;
        }
        if (i6 < 2) {
            r6 = z2;
            return false;
        }
        int i8 = this.count;
        if (i8 < 0) {
            r6 = z2;
            z = true;
        } else {
            r6 = z2;
            z = false;
        }
        for (int iMax = Math.max(i8, -i8); iMax > 0; iMax--) {
            int iCursor3 = this.buf.cursor();
            while (iCursor3 > i5 && !isDelimiter(this.buf.atChar(iCursor3 - 1))) {
                iCursor3--;
            }
            int i9 = iCursor3;
            while (i9 < iCursor2) {
                i9++;
                if (isDelimiter(this.buf.atChar(i9))) {
                    break;
                }
            }
            if (!z) {
                i = i9;
                while (i < iCursor2) {
                    i++;
                    if (!isDelimiter(this.buf.atChar(i))) {
                        break;
                    }
                }
                if (i != iCursor2) {
                    i2 = i;
                    while (i2 < iCursor2) {
                        i2++;
                        if (isDelimiter(this.buf.atChar(i2))) {
                            break;
                        }
                    }
                } else {
                    i3 = iCursor3;
                    while (isDelimiter(this.buf.atChar(i3 - 1))) {
                        i3--;
                    }
                    i4 = i3;
                    while (i4 > i5 && !isDelimiter(this.buf.atChar(i4 - 1))) {
                        i4--;
                    }
                    int i10 = i4;
                    i2 = i3;
                    i = i10;
                }
            } else {
                i3 = iCursor3 - 1;
                while (i3 > i5 && isDelimiter(this.buf.atChar(i3 - 1))) {
                    i3--;
                }
                if (i3 < i5) {
                    i = i9;
                    do {
                        i++;
                    } while (isDelimiter(this.buf.atChar(i)));
                    i2 = i;
                    while (i2 < iCursor2) {
                        i2++;
                        if (isDelimiter(this.buf.atChar(i2))) {
                            break;
                        }
                    }
                } else {
                    i4 = i3;
                    while (i4 > i5 && !isDelimiter(this.buf.atChar(i4 - 1))) {
                        i4--;
                    }
                    int i11 = i4;
                    i2 = i3;
                    i = i11;
                }
            }
            if (iCursor3 < i) {
                String str = this.buf.substring(0, iCursor3) + this.buf.substring(i, i2) + this.buf.substring(i9, i) + this.buf.substring(iCursor3, i9) + this.buf.substring(i2);
                this.buf.clear();
                this.buf.write(str);
                Buffer buffer = this.buf;
                if (!z) {
                    i9 = i2;
                }
                buffer.cursor(i9);
            } else {
                String str2 = this.buf.substring(0, i) + this.buf.substring(iCursor3, i9) + this.buf.substring(i2, iCursor3) + this.buf.substring(i, i2) + this.buf.substring(i9);
                this.buf.clear();
                this.buf.write(str2);
                Buffer buffer2 = this.buf;
                if (z) {
                    i9 = i2;
                }
                buffer2.cursor(i9);
            }
        }
        return true;
    }

    private int findbol() {
        int iCursor = this.buf.cursor();
        while (iCursor > 0 && this.buf.atChar(iCursor - 1) != 10) {
            iCursor--;
        }
        return iCursor;
    }

    private int findeol() {
        int iCursor = this.buf.cursor();
        while (iCursor < this.buf.length() && this.buf.atChar(iCursor) != 10) {
            iCursor++;
        }
        return iCursor;
    }

    protected boolean insertComment() {
        return doInsertComment(false);
    }

    protected boolean viInsertComment() {
        return doInsertComment(true);
    }

    protected boolean doInsertComment(boolean z) {
        String string = getString(LineReader.COMMENT_BEGIN, "#");
        beginningOfLine();
        putString(string);
        if (z) {
            setKeyMap(LineReader.VIINS);
        }
        return acceptLine();
    }

    protected boolean viFindNextChar() {
        int iVigetkey = vigetkey();
        this.findChar = iVigetkey;
        if (iVigetkey <= 0) {
            return false;
        }
        this.findDir = 1;
        this.findTailAdd = 0;
        return vifindchar(false);
    }

    protected boolean viFindPrevChar() {
        int iVigetkey = vigetkey();
        this.findChar = iVigetkey;
        if (iVigetkey <= 0) {
            return false;
        }
        this.findDir = -1;
        this.findTailAdd = 0;
        return vifindchar(false);
    }

    protected boolean viFindNextCharSkip() {
        int iVigetkey = vigetkey();
        this.findChar = iVigetkey;
        if (iVigetkey <= 0) {
            return false;
        }
        this.findDir = 1;
        this.findTailAdd = -1;
        return vifindchar(false);
    }

    protected boolean viFindPrevCharSkip() {
        int iVigetkey = vigetkey();
        this.findChar = iVigetkey;
        if (iVigetkey <= 0) {
            return false;
        }
        this.findDir = -1;
        this.findTailAdd = 1;
        return vifindchar(false);
    }

    protected boolean viRepeatFind() {
        return vifindchar(true);
    }

    protected boolean viRevRepeatFind() {
        if (this.count < 0) {
            return callNeg(new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.org.jline.reader.Widget
                public final boolean apply() {
                    return this.f$0.m7381x22c2af93();
                }
            });
        }
        this.findTailAdd = -this.findTailAdd;
        this.findDir = -this.findDir;
        boolean zVifindchar = vifindchar(true);
        this.findTailAdd = -this.findTailAdd;
        this.findDir = -this.findDir;
        return zVifindchar;
    }

    /* JADX INFO: renamed from: lambda$viRevRepeatFind$6$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ boolean m7381x22c2af93() {
        return vifindchar(true);
    }

    private int vigetkey() {
        int character = readCharacter();
        KeyMap<Binding> keyMap = this.keyMaps.get("main");
        if (keyMap == null) {
            return character;
        }
        Binding bound = keyMap.getBound(new String(Character.toChars(character)));
        if ((bound instanceof Reference) && LineReader.SEND_BREAK.equals(((Reference) bound).name())) {
            return -1;
        }
        return character;
    }

    private boolean vifindchar(boolean z) {
        int i = this.findDir;
        if (i == 0) {
            return false;
        }
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda95(this));
        }
        if (z && this.findTailAdd != 0) {
            if (i > 0) {
                if (this.buf.cursor() < this.buf.length() && this.buf.nextChar() == this.findChar) {
                    this.buf.move(1);
                }
            } else if (this.buf.cursor() > 0 && this.buf.prevChar() == this.findChar) {
                this.buf.move(-1);
            }
        }
        int iCursor = this.buf.cursor();
        do {
            int i2 = this.count;
            this.count = i2 - 1;
            if (i2 > 0) {
                do {
                    this.buf.move(this.findDir);
                    if (this.buf.cursor() <= 0 || this.buf.cursor() >= this.buf.length() || this.buf.currChar() == this.findChar) {
                        break;
                    }
                } while (this.buf.currChar() != 10);
                if (this.buf.cursor() <= 0 || this.buf.cursor() >= this.buf.length()) {
                    break;
                }
            } else {
                int i3 = this.findTailAdd;
                if (i3 != 0) {
                    this.buf.move(i3);
                }
                if (this.findDir == 1 && isInViMoveOperation()) {
                    this.buf.move(1);
                }
                return true;
            }
        } while (this.buf.currChar() != 10);
        this.buf.cursor(iCursor);
        return false;
    }

    private boolean callNeg(Widget widget) {
        this.count = -this.count;
        boolean zApply = widget.apply();
        this.count = -this.count;
        return zApply;
    }

    protected boolean viHistorySearchForward() {
        this.searchDir = 1;
        this.searchIndex = 0;
        return getViSearchString() && viRepeatSearch();
    }

    protected boolean viHistorySearchBackward() {
        this.searchDir = -1;
        this.searchIndex = this.history.size() - 1;
        return getViSearchString() && viRepeatSearch();
    }

    protected boolean viRepeatSearch() {
        int iSearchForwards;
        int i = this.searchDir;
        if (i == 0) {
            return false;
        }
        if (i < 0) {
            iSearchForwards = searchBackwards(this.searchString, this.searchIndex, false);
        } else {
            iSearchForwards = searchForwards(this.searchString, this.searchIndex, false);
        }
        if (iSearchForwards == -1 || iSearchForwards == this.history.index()) {
            return false;
        }
        this.searchIndex = iSearchForwards;
        this.buf.clear();
        this.history.moveTo(this.searchIndex);
        this.buf.write(this.history.get(this.searchIndex));
        if (!LineReader.VICMD.equals(this.keyMap)) {
            return true;
        }
        this.buf.move(-1);
        return true;
    }

    protected boolean viRevRepeatSearch() {
        this.searchDir = -this.searchDir;
        boolean zViRepeatSearch = viRepeatSearch();
        this.searchDir = -this.searchDir;
        return zViRepeatSearch;
    }

    private boolean getViSearchString() {
        int i = this.searchDir;
        if (i == 0) {
            return false;
        }
        final String str = i < 0 ? TypeDescription.Generic.OfWildcardType.SYMBOL : "/";
        final BufferImpl bufferImpl = new BufferImpl();
        KeyMap<Binding> keyMap = this.keyMaps.get("main");
        if (keyMap == null) {
            keyMap = this.keyMaps.get(LineReader.SAFE);
        }
        while (true) {
            this.post = new Supplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda88
                @Override // java.util.function.Supplier
                public final Object get() {
                    return LineReaderImpl.lambda$getViSearchString$7(str, bufferImpl);
                }
            };
            redisplay();
            Binding binding = (Binding) this.bindingReader.readBinding(keyMap);
            if (binding instanceof Reference) {
                String strName = ((Reference) binding).name();
                strName.hashCode();
                switch (strName) {
                    case "vi-backward-delete-char":
                    case "backward-delete-char":
                        if (bufferImpl.length() <= 0) {
                            break;
                        } else {
                            bufferImpl.backspace();
                            break;
                        }
                        break;
                    case "vi-quoted-insert":
                    case "quoted-insert":
                        int character = readCharacter();
                        if (character >= 0) {
                            bufferImpl.write(character);
                            break;
                        } else {
                            beep();
                            break;
                        }
                        break;
                    case "clear-screen":
                        clearScreen();
                        break;
                    case "magic-space":
                        bufferImpl.write(32);
                        break;
                    case "abort":
                        this.post = null;
                        return false;
                    case "vi-backward-kill-word":
                    case "backward-kill-word":
                        if (bufferImpl.length() > 0 && !isWhitespace(bufferImpl.prevChar())) {
                            bufferImpl.backspace();
                        }
                        if (bufferImpl.length() > 0 && isWhitespace(bufferImpl.prevChar())) {
                            bufferImpl.backspace();
                            break;
                        } else {
                            break;
                        }
                        break;
                    case "accept-line":
                    case "vi-cmd-mode":
                        this.searchString = bufferImpl.toString();
                        this.post = null;
                        return true;
                    case "self-insert":
                        bufferImpl.write(getLastBinding());
                        break;
                    case "self-insert-unmeta":
                        if (getLastBinding().charAt(0) != 27) {
                            break;
                        } else {
                            String strSubstring = getLastBinding().substring(1);
                            if ("\r".equals(strSubstring)) {
                                strSubstring = "\n";
                            }
                            bufferImpl.write(strSubstring);
                            break;
                        }
                        break;
                    case "redisplay":
                        redisplay();
                        break;
                    default:
                        beep();
                        break;
                }
            }
        }
    }

    static /* synthetic */ AttributedString lambda$getViSearchString$7(String str, Buffer buffer) {
        return new AttributedString(str + buffer.toString() + "_");
    }

    protected boolean insertCloseCurly() {
        return insertClose("}");
    }

    protected boolean insertCloseParen() {
        return insertClose(Constant.AFTER_QUTO);
    }

    protected boolean insertCloseSquare() {
        return insertClose("]");
    }

    protected boolean insertClose(String str) {
        putString(str);
        int iCursor = this.buf.cursor();
        this.buf.move(-1);
        doViMatchBracket();
        redisplay();
        peekCharacter(getLong(LineReader.BLINK_MATCHING_PAREN, 500L));
        this.buf.cursor(iCursor);
        return true;
    }

    protected boolean viMatchBracket() {
        return doViMatchBracket();
    }

    protected boolean doViMatchBracket() {
        int iCursor = this.buf.cursor();
        if (iCursor == this.buf.length()) {
            return false;
        }
        int bracketType = getBracketType(this.buf.atChar(iCursor));
        int i = bracketType < 0 ? -1 : 1;
        if (bracketType == 0) {
            return false;
        }
        int i2 = 1;
        while (i2 > 0) {
            iCursor += i;
            if (iCursor < 0 || iCursor >= this.buf.length()) {
                return false;
            }
            int bracketType2 = getBracketType(this.buf.atChar(iCursor));
            if (bracketType2 == bracketType) {
                i2++;
            } else if (bracketType2 == (-bracketType)) {
                i2--;
            }
        }
        if (i > 0 && isInViMoveOperation()) {
            iCursor++;
        }
        this.buf.cursor(iCursor);
        return true;
    }

    protected boolean transposeChars() {
        int iCursor = this.buf.cursor() - 1;
        int iCursor2 = this.buf.cursor();
        while (this.buf.atChar(iCursor) != 0 && this.buf.atChar(iCursor) != 10) {
            iCursor--;
        }
        int i = iCursor + 1;
        while (this.buf.atChar(iCursor2) != 0 && this.buf.atChar(iCursor2) != 10) {
            iCursor2++;
        }
        if (iCursor2 - i < 2) {
            return false;
        }
        int i2 = this.count;
        boolean z = i2 < 0;
        for (int iMax = Math.max(i2, -i2); iMax > 0; iMax--) {
            while (this.buf.cursor() <= i) {
                this.buf.move(1);
            }
            while (this.buf.cursor() >= iCursor2) {
                this.buf.move(-1);
            }
            int iCurrChar = this.buf.currChar();
            Buffer buffer = this.buf;
            buffer.currChar(buffer.prevChar());
            this.buf.move(-1);
            this.buf.currChar(iCurrChar);
            this.buf.move(z ? 0 : 2);
        }
        return true;
    }

    protected boolean undo() {
        this.isUndo = true;
        if (!this.undo.canUndo()) {
            return false;
        }
        this.undo.undo();
        return true;
    }

    protected boolean redo() {
        this.isUndo = true;
        if (!this.undo.canRedo()) {
            return false;
        }
        this.undo.redo();
        return true;
    }

    protected boolean sendBreak() {
        if (this.searchTerm != null) {
            return true;
        }
        this.buf.clear();
        println();
        redrawLine();
        return false;
    }

    protected boolean backwardChar() {
        return this.buf.move(-this.count) != 0;
    }

    protected boolean forwardChar() {
        return this.buf.move(this.count) != 0;
    }

    protected boolean viDigitOrBeginningOfLine() {
        if (this.repeatCount > 0) {
            return digitArgument();
        }
        return beginningOfLine();
    }

    protected boolean universalArgument() {
        this.mult *= this.universal;
        this.isArgDigit = true;
        return true;
    }

    protected boolean argumentBase() {
        int i = this.repeatCount;
        if (i <= 0 || i >= 32) {
            return false;
        }
        this.universal = i;
        this.isArgDigit = true;
        return true;
    }

    protected boolean negArgument() {
        this.mult *= -1;
        this.isArgDigit = true;
        return true;
    }

    protected boolean digitArgument() {
        String lastBinding = getLastBinding();
        this.repeatCount = ((this.repeatCount * 10) + lastBinding.charAt(lastBinding.length() - 1)) - 48;
        this.isArgDigit = true;
        return true;
    }

    protected boolean viDelete() {
        int iCursor = this.buf.cursor();
        Binding binding = readBinding(getKeys());
        if (binding instanceof Reference) {
            String strViDeleteChangeYankToRemap = viDeleteChangeYankToRemap(((Reference) binding).name());
            if (LineReader.VI_DELETE.equals(strViDeleteChangeYankToRemap)) {
                killWholeLine();
            } else {
                this.viMoveMode = ViMoveMode.DELETE;
                Widget widget = this.widgets.get(strViDeleteChangeYankToRemap);
                if (widget != null && !widget.apply()) {
                    this.viMoveMode = ViMoveMode.NORMAL;
                    return false;
                }
                this.viMoveMode = ViMoveMode.NORMAL;
            }
            return viDeleteTo(iCursor, this.buf.cursor());
        }
        pushBackBinding();
        return false;
    }

    protected boolean viYankTo() {
        int iCursor = this.buf.cursor();
        Binding binding = readBinding(getKeys());
        if (binding instanceof Reference) {
            String strViDeleteChangeYankToRemap = viDeleteChangeYankToRemap(((Reference) binding).name());
            if (LineReader.VI_YANK.equals(strViDeleteChangeYankToRemap)) {
                this.yankBuffer = this.buf.toString();
                return true;
            }
            this.viMoveMode = ViMoveMode.YANK;
            Widget widget = this.widgets.get(strViDeleteChangeYankToRemap);
            if (widget != null && !widget.apply()) {
                return false;
            }
            this.viMoveMode = ViMoveMode.NORMAL;
            return viYankTo(iCursor, this.buf.cursor());
        }
        pushBackBinding();
        return false;
    }

    protected boolean viYankWholeLine() {
        int iCursor = this.buf.cursor();
        while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {
        }
        int iCursor2 = this.buf.cursor();
        for (int i = 0; i < this.repeatCount; i++) {
            while (this.buf.move(1) == 1 && this.buf.prevChar() != 10) {
            }
        }
        String strSubstring = this.buf.substring(iCursor2, this.buf.cursor());
        this.yankBuffer = strSubstring;
        if (!strSubstring.endsWith("\n")) {
            this.yankBuffer += "\n";
        }
        this.buf.cursor(iCursor);
        return true;
    }

    protected boolean viChange() {
        int iCursor = this.buf.cursor();
        Binding binding = readBinding(getKeys());
        if (binding instanceof Reference) {
            String strViDeleteChangeYankToRemap = viDeleteChangeYankToRemap(((Reference) binding).name());
            if (LineReader.VI_CHANGE.equals(strViDeleteChangeYankToRemap)) {
                killWholeLine();
            } else {
                this.viMoveMode = ViMoveMode.CHANGE;
                Widget widget = this.widgets.get(strViDeleteChangeYankToRemap);
                if (widget != null && !widget.apply()) {
                    this.viMoveMode = ViMoveMode.NORMAL;
                    return false;
                }
                this.viMoveMode = ViMoveMode.NORMAL;
            }
            boolean zViChange = viChange(iCursor, this.buf.cursor());
            setKeyMap(LineReader.VIINS);
            return zViChange;
        }
        pushBackBinding();
        return false;
    }

    protected void cleanup() {
        Buffer buffer = this.buf;
        buffer.cursor(buffer.length());
        this.post = null;
        if (this.size.getColumns() > 0 || this.size.getRows() > 0) {
            redisplay(false);
            println();
            this.terminal.puts(InfoCmp.Capability.keypad_local, new Object[0]);
            this.terminal.trackMouse(Terminal.MouseTracking.Off);
            flush();
        }
        this.history.moveToEnd();
    }

    protected boolean historyIncrementalSearchForward() {
        return doSearchHistory(false);
    }

    protected boolean historyIncrementalSearchBackward() {
        return doSearchHistory(true);
    }

    protected boolean doSearchHistory(boolean z) {
        int iSearchForwards;
        if (this.history.isEmpty()) {
            return false;
        }
        Buffer bufferCopy = this.buf.copy();
        StringBuffer stringBuffer = this.searchTerm;
        String string = stringBuffer != null ? stringBuffer.toString() : "";
        StringBuffer stringBuffer2 = new StringBuffer(this.buf.toString());
        this.searchTerm = stringBuffer2;
        if (stringBuffer2.length() > 0) {
            if (z) {
                iSearchForwards = searchBackwards(this.searchTerm.toString(), this.history.index(), false);
            } else {
                iSearchForwards = searchForwards(this.searchTerm.toString(), this.history.index(), false);
            }
            this.searchIndex = iSearchForwards;
            if (iSearchForwards == -1) {
                beep();
            }
            String string2 = this.searchTerm.toString();
            int i = this.searchIndex;
            printSearchStatus(string2, i > -1 ? this.history.get(i) : "", z);
        } else {
            this.searchIndex = -1;
            printSearchStatus("", "", z);
        }
        redisplay();
        final KeyMap<Binding> keyMap = new KeyMap<>();
        getString(LineReader.SEARCH_TERMINATORS, "\u001b\n").codePoints().forEach(new IntConsumer() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda80
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                this.f$0.m7375xe141db92(keyMap, i2);
            }
        });
        while (true) {
            try {
                Binding binding = readBinding(getKeys(), keyMap);
                if (new Reference(LineReader.SEND_BREAK).equals(binding)) {
                    this.buf.copyFrom(bufferCopy);
                    break;
                }
                if (new Reference(LineReader.HISTORY_INCREMENTAL_SEARCH_BACKWARD).equals(binding)) {
                    if (this.searchTerm.length() == 0) {
                        this.searchTerm.append(string);
                    }
                    if (this.searchIndex > 0) {
                        this.searchIndex = searchBackwards(this.searchTerm.toString(), this.searchIndex, false);
                    }
                    z = true;
                } else if (new Reference(LineReader.HISTORY_INCREMENTAL_SEARCH_FORWARD).equals(binding)) {
                    if (this.searchTerm.length() == 0) {
                        this.searchTerm.append(string);
                    }
                    int i2 = this.searchIndex;
                    if (i2 > -1 && i2 < this.history.size() - 1) {
                        this.searchIndex = searchForwards(this.searchTerm.toString(), this.searchIndex, false);
                    }
                    z = false;
                } else if (new Reference(LineReader.BACKWARD_DELETE_CHAR).equals(binding)) {
                    if (this.searchTerm.length() > 0) {
                        StringBuffer stringBuffer3 = this.searchTerm;
                        stringBuffer3.deleteCharAt(stringBuffer3.length() - 1);
                        if (z) {
                            this.searchIndex = searchBackwards(this.searchTerm.toString(), this.history.index(), false);
                        } else {
                            this.searchIndex = searchForwards(this.searchTerm.toString(), this.history.index(), false);
                        }
                    }
                } else if (new Reference(LineReader.SELF_INSERT).equals(binding)) {
                    this.searchTerm.append(getLastBinding());
                    if (z) {
                        this.searchIndex = searchBackwards(this.searchTerm.toString(), this.history.index(), false);
                    } else {
                        this.searchIndex = searchForwards(this.searchTerm.toString(), this.history.index(), false);
                    }
                } else {
                    int i3 = this.searchIndex;
                    if (i3 != -1) {
                        this.history.moveTo(i3);
                    }
                    pushBackBinding();
                    break;
                }
                if (this.searchTerm.length() == 0) {
                    printSearchStatus("", "", z);
                    this.searchIndex = -1;
                } else if (this.searchIndex == -1) {
                    beep();
                    printSearchStatus(this.searchTerm.toString(), "", z);
                } else {
                    printSearchStatus(this.searchTerm.toString(), this.history.get(this.searchIndex), z);
                }
                redisplay();
            } catch (Throwable th) {
                this.searchTerm = null;
                this.searchIndex = -1;
                this.post = null;
                throw th;
            }
        }
        this.searchTerm = null;
        this.searchIndex = -1;
        this.post = null;
        return true;
    }

    /* JADX INFO: renamed from: lambda$doSearchHistory$8$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ void m7375xe141db92(KeyMap keyMap, int i) {
        bind((KeyMap<Binding>) keyMap, LineReader.ACCEPT_LINE, new String(Character.toChars(i)));
    }

    private void pushBackBinding() {
        pushBackBinding(false);
    }

    private void pushBackBinding(boolean z) {
        String lastBinding = getLastBinding();
        if (lastBinding != null) {
            this.bindingReader.runMacro(lastBinding);
            this.skipRedisplay = z;
        }
    }

    protected boolean historySearchForward() {
        if (this.historyBuffer == null || this.buf.length() == 0 || !this.buf.toString().equals(this.history.current())) {
            this.historyBuffer = this.buf.copy();
            this.searchBuffer = getFirstWord();
        }
        int iIndex = this.history.index() + 1;
        if (iIndex < this.history.last() + 1) {
            int iSearchForwards = searchForwards(this.searchBuffer.toString(), iIndex, true);
            if (iSearchForwards == -1) {
                this.history.moveToEnd();
                if (this.buf.toString().equals(this.historyBuffer.toString())) {
                    return false;
                }
                setBuffer(this.historyBuffer.toString());
                this.historyBuffer = null;
            } else if (this.history.moveTo(iSearchForwards)) {
                setBuffer(this.history.current());
            } else {
                this.history.moveToEnd();
                setBuffer(this.historyBuffer.toString());
                return false;
            }
        } else {
            this.history.moveToEnd();
            if (this.buf.toString().equals(this.historyBuffer.toString())) {
                return false;
            }
            setBuffer(this.historyBuffer.toString());
            this.historyBuffer = null;
        }
        return true;
    }

    private CharSequence getFirstWord() {
        String string = this.buf.toString();
        int i = 0;
        while (i < string.length() && !Character.isWhitespace(string.charAt(i))) {
            i++;
        }
        return string.substring(0, i);
    }

    protected boolean historySearchBackward() {
        if (this.historyBuffer == null || this.buf.length() == 0 || !this.buf.toString().equals(this.history.current())) {
            this.historyBuffer = this.buf.copy();
            this.searchBuffer = getFirstWord();
        }
        int iSearchBackwards = searchBackwards(this.searchBuffer.toString(), this.history.index(), true);
        if (iSearchBackwards == -1 || !this.history.moveTo(iSearchBackwards)) {
            return false;
        }
        setBuffer(this.history.current());
        return true;
    }

    public int searchBackwards(String str, int i, boolean z) {
        ListIterator<History.Entry> it = this.history.iterator(i);
        while (it.hasPrevious()) {
            History.Entry entryPrevious = it.previous();
            if (z) {
                if (entryPrevious.line().startsWith(str)) {
                    return entryPrevious.index();
                }
            } else if (entryPrevious.line().contains(str)) {
                return entryPrevious.index();
            }
        }
        return -1;
    }

    public int searchForwards(String str, int i, boolean z) {
        if (i > this.history.last()) {
            i = this.history.last();
        }
        ListIterator<History.Entry> it = this.history.iterator(i);
        if (this.searchIndex != -1 && it.hasNext()) {
            it.next();
        }
        while (it.hasNext()) {
            History.Entry next = it.next();
            if (z) {
                if (next.line().startsWith(str)) {
                    return next.index();
                }
            } else if (next.line().contains(str)) {
                return next.index();
            }
        }
        return -1;
    }

    public void printSearchStatus(final String str, String str2, boolean z) {
        final String str3 = z ? "bck-i-search" : "i-search";
        this.post = new Supplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda78
            @Override // java.util.function.Supplier
            public final Object get() {
                return LineReaderImpl.lambda$printSearchStatus$9(str3, str);
            }
        };
        setBuffer(str2);
        this.buf.move(str2.indexOf(str) - this.buf.cursor());
    }

    static /* synthetic */ AttributedString lambda$printSearchStatus$9(String str, String str2) {
        return new AttributedString(str + ": " + str2 + "_");
    }

    protected boolean acceptLine() {
        this.parsedLine = null;
        if (!isSet(LineReader.Option.DISABLE_EVENT_EXPANSION)) {
            try {
                String string = this.buf.toString();
                String strExpandHistory = this.expander.expandHistory(this.history, string);
                if (!strExpandHistory.equals(string)) {
                    this.buf.clear();
                    this.buf.write(strExpandHistory);
                    if (isSet(LineReader.Option.HISTORY_VERIFY)) {
                        return true;
                    }
                }
            } catch (IllegalArgumentException unused) {
            }
        }
        try {
            this.parsedLine = this.parser.parse(this.buf.toString(), this.buf.cursor(), Parser.ParseContext.ACCEPT_LINE);
        } catch (EOFError unused2) {
            this.buf.write("\n");
            return true;
        } catch (SyntaxError unused3) {
        }
        callWidget(LineReader.CALLBACK_FINISH);
        this.state = State.DONE;
        return true;
    }

    protected boolean selfInsert() {
        for (int i = this.count; i > 0; i--) {
            putString(getLastBinding());
        }
        return true;
    }

    protected boolean selfInsertUnmeta() {
        boolean z = false;
        if (getLastBinding().charAt(0) == 27) {
            z = true;
            String strSubstring = getLastBinding().substring(1);
            if ("\r".equals(strSubstring)) {
                strSubstring = "\n";
            }
            for (int i = this.count; i > 0; i--) {
                putString(strSubstring);
            }
        }
        return z;
    }

    protected boolean overwriteMode() {
        this.overTyping = !this.overTyping;
        return true;
    }

    protected boolean beginningOfBufferOrHistory() {
        if (findbol() != 0) {
            this.buf.cursor(0);
            return true;
        }
        return beginningOfHistory();
    }

    protected boolean beginningOfHistory() {
        if (!this.history.moveToFirst()) {
            return false;
        }
        setBuffer(this.history.current());
        return true;
    }

    protected boolean endOfBufferOrHistory() {
        if (findeol() != this.buf.length()) {
            Buffer buffer = this.buf;
            buffer.cursor(buffer.length());
            return true;
        }
        return endOfHistory();
    }

    protected boolean endOfHistory() {
        if (!this.history.moveToLast()) {
            return false;
        }
        setBuffer(this.history.current());
        return true;
    }

    protected boolean beginningOfLineHist() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda176(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            int iFindbol = findbol();
            if (iFindbol != this.buf.cursor()) {
                this.buf.cursor(iFindbol);
            } else {
                moveHistory(false);
                this.buf.cursor(0);
            }
        }
    }

    protected boolean endOfLineHist() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda137(this));
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            int iFindeol = findeol();
            if (iFindeol != this.buf.cursor()) {
                this.buf.cursor(iFindeol);
            } else {
                moveHistory(true);
            }
        }
    }

    protected boolean upHistory() {
        do {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
        } while (moveHistory(false));
        return !isSet(LineReader.Option.HISTORY_BEEP);
    }

    protected boolean downHistory() {
        do {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
        } while (moveHistory(true));
        return !isSet(LineReader.Option.HISTORY_BEEP);
    }

    protected boolean viUpLineOrHistory() {
        return upLine() || (upHistory() && viFirstNonBlank());
    }

    protected boolean viDownLineOrHistory() {
        return downLine() || (downHistory() && viFirstNonBlank());
    }

    protected boolean upLine() {
        return this.buf.up();
    }

    protected boolean downLine() {
        return this.buf.down();
    }

    protected boolean upLineOrHistory() {
        return upLine() || upHistory();
    }

    protected boolean upLineOrSearch() {
        return upLine() || historySearchBackward();
    }

    protected boolean downLineOrHistory() {
        return downLine() || downHistory();
    }

    protected boolean downLineOrSearch() {
        return downLine() || historySearchForward();
    }

    protected boolean viCmdMode() {
        if (this.state == State.NORMAL) {
            this.buf.move(-1);
        }
        return setKeyMap(LineReader.VICMD);
    }

    protected boolean viInsert() {
        return setKeyMap(LineReader.VIINS);
    }

    protected boolean viAddNext() {
        this.buf.move(1);
        return setKeyMap(LineReader.VIINS);
    }

    protected boolean viAddEol() {
        return endOfLine() && setKeyMap(LineReader.VIINS);
    }

    protected boolean emacsEditingMode() {
        return setKeyMap(LineReader.EMACS);
    }

    protected boolean viChangeWholeLine() {
        return viFirstNonBlank() && viChangeEol();
    }

    protected boolean viChangeEol() {
        return viChange(this.buf.cursor(), this.buf.length()) && setKeyMap(LineReader.VIINS);
    }

    protected boolean viKillEol() {
        int iFindeol = findeol();
        if (this.buf.cursor() == iFindeol) {
            return false;
        }
        KillRing killRing = this.killRing;
        Buffer buffer = this.buf;
        killRing.add(buffer.substring(buffer.cursor(), iFindeol));
        Buffer buffer2 = this.buf;
        buffer2.delete(iFindeol - buffer2.cursor());
        return true;
    }

    protected boolean quotedInsert() {
        int character = readCharacter();
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            putString(new String(Character.toChars(character)));
        }
    }

    protected boolean viJoin() {
        if (!this.buf.down()) {
            return false;
        }
        while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {
        }
        this.buf.backspace();
        this.buf.write(32);
        this.buf.move(-1);
        return true;
    }

    protected boolean viKillWholeLine() {
        return killWholeLine() && setKeyMap(LineReader.VIINS);
    }

    protected boolean viInsertBol() {
        return beginningOfLine() && setKeyMap(LineReader.VIINS);
    }

    protected boolean backwardDeleteChar() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda157(this));
        }
        if (this.buf.cursor() == 0) {
            return false;
        }
        this.buf.backspace(this.count);
        return true;
    }

    protected boolean viFirstNonBlank() {
        beginningOfLine();
        while (this.buf.cursor() < this.buf.length() && isWhitespace(this.buf.currChar())) {
            this.buf.move(1);
        }
        return true;
    }

    protected boolean viBeginningOfLine() {
        this.buf.cursor(findbol());
        return true;
    }

    protected boolean viEndOfLine() {
        if (this.count < 0) {
            return false;
        }
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i > 0) {
                this.buf.cursor(findeol() + 1);
            } else {
                this.buf.move(-1);
                return true;
            }
        }
    }

    protected boolean beginningOfLine() {
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {
            }
        }
    }

    protected boolean endOfLine() {
        while (true) {
            int i = this.count;
            this.count = i - 1;
            if (i <= 0) {
                return true;
            }
            while (this.buf.move(1) == 1 && this.buf.currChar() != 10) {
            }
        }
    }

    protected boolean deleteChar() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda180(this));
        }
        if (this.buf.cursor() == this.buf.length()) {
            return false;
        }
        this.buf.delete(this.count);
        return true;
    }

    protected boolean viBackwardDeleteChar() {
        for (int i = 0; i < this.count; i++) {
            if (!this.buf.backspace()) {
                return false;
            }
        }
        return true;
    }

    protected boolean viDeleteChar() {
        for (int i = 0; i < this.count; i++) {
            if (!this.buf.delete()) {
                return false;
            }
        }
        return true;
    }

    protected boolean viSwapCase() {
        for (int i = 0; i < this.count; i++) {
            if (this.buf.cursor() >= this.buf.length()) {
                return false;
            }
            Buffer buffer = this.buf;
            this.buf.currChar(switchCase(buffer.atChar(buffer.cursor())));
            this.buf.move(1);
        }
        return true;
    }

    protected boolean viReplaceChars() {
        int character = readCharacter();
        if (character >= 0 && character != 27 && character != 3) {
            for (int i = 0; i < this.count; i++) {
                if (!this.buf.currChar((char) character)) {
                    return false;
                }
                if (i < this.count - 1) {
                    this.buf.move(1);
                }
            }
        }
        return true;
    }

    protected boolean viChange(int i, int i2) {
        return doViDeleteOrChange(i, i2, true);
    }

    protected boolean viDeleteTo(int i, int i2) {
        return doViDeleteOrChange(i, i2, false);
    }

    protected boolean doViDeleteOrChange(int i, int i2, boolean z) {
        if (i == i2) {
            return true;
        }
        if (i2 < i) {
            i2 = i;
            i = i2;
        }
        this.buf.cursor(i);
        this.buf.delete(i2 - i);
        if (!z && i > 0 && i == this.buf.length()) {
            this.buf.move(-1);
        }
        return true;
    }

    protected boolean viYankTo(int i, int i2) {
        int i3;
        if (i2 < i) {
            i3 = i;
        } else {
            i3 = i2;
            i2 = i;
        }
        if (i2 == i3) {
            this.yankBuffer = "";
            return true;
        }
        this.yankBuffer = this.buf.substring(i2, i3);
        this.buf.cursor(i);
        return true;
    }

    protected boolean viOpenLineAbove() {
        while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {
        }
        this.buf.write(10);
        this.buf.move(-1);
        return setKeyMap(LineReader.VIINS);
    }

    protected boolean viOpenLineBelow() {
        while (this.buf.move(1) == 1 && this.buf.currChar() != 10) {
        }
        this.buf.write(10);
        return setKeyMap(LineReader.VIINS);
    }

    protected boolean viPutAfter() {
        if (this.yankBuffer.indexOf(10) >= 0) {
            while (this.buf.move(1) == 1 && this.buf.currChar() != 10) {
            }
            this.buf.move(1);
            putString(this.yankBuffer);
            this.buf.move(-this.yankBuffer.length());
        } else if (this.yankBuffer.length() != 0) {
            if (this.buf.cursor() < this.buf.length()) {
                this.buf.move(1);
            }
            for (int i = 0; i < this.count; i++) {
                putString(this.yankBuffer);
            }
            this.buf.move(-1);
        }
        return true;
    }

    protected boolean viPutBefore() {
        if (this.yankBuffer.indexOf(10) >= 0) {
            while (this.buf.move(-1) == -1 && this.buf.prevChar() != 10) {
            }
            putString(this.yankBuffer);
            this.buf.move(-this.yankBuffer.length());
            return true;
        }
        if (this.yankBuffer.length() == 0) {
            return true;
        }
        if (this.buf.cursor() > 0) {
            this.buf.move(-1);
        }
        for (int i = 0; i < this.count; i++) {
            putString(this.yankBuffer);
        }
        this.buf.move(-1);
        return true;
    }

    protected boolean doLowercaseVersion() {
        this.bindingReader.runMacro(getLastBinding().toLowerCase());
        return true;
    }

    protected boolean setMarkCommand() {
        if (this.count < 0) {
            this.regionActive = LineReader.RegionType.NONE;
            return true;
        }
        this.regionMark = this.buf.cursor();
        this.regionActive = LineReader.RegionType.CHAR;
        return true;
    }

    protected boolean exchangePointAndMark() {
        if (this.count == 0) {
            this.regionActive = LineReader.RegionType.CHAR;
            return true;
        }
        int i = this.regionMark;
        this.regionMark = this.buf.cursor();
        this.buf.cursor(i);
        if (this.buf.cursor() > this.buf.length()) {
            Buffer buffer = this.buf;
            buffer.cursor(buffer.length());
        }
        if (this.count > 0) {
            this.regionActive = LineReader.RegionType.CHAR;
        }
        return true;
    }

    protected boolean visualMode() {
        if (isInViMoveOperation()) {
            this.isArgDigit = true;
            this.forceLine = false;
            this.forceChar = true;
            return true;
        }
        if (this.regionActive == LineReader.RegionType.NONE) {
            this.regionMark = this.buf.cursor();
            this.regionActive = LineReader.RegionType.CHAR;
        } else if (this.regionActive == LineReader.RegionType.CHAR) {
            this.regionActive = LineReader.RegionType.NONE;
        } else if (this.regionActive == LineReader.RegionType.LINE) {
            this.regionActive = LineReader.RegionType.CHAR;
        }
        return true;
    }

    protected boolean visualLineMode() {
        if (isInViMoveOperation()) {
            this.isArgDigit = true;
            this.forceLine = true;
            this.forceChar = false;
            return true;
        }
        if (this.regionActive == LineReader.RegionType.NONE) {
            this.regionMark = this.buf.cursor();
            this.regionActive = LineReader.RegionType.LINE;
        } else if (this.regionActive == LineReader.RegionType.CHAR) {
            this.regionActive = LineReader.RegionType.LINE;
        } else if (this.regionActive == LineReader.RegionType.LINE) {
            this.regionActive = LineReader.RegionType.NONE;
        }
        return true;
    }

    protected boolean deactivateRegion() {
        this.regionActive = LineReader.RegionType.NONE;
        return true;
    }

    protected boolean whatCursorPosition() {
        this.post = new Supplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda85
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m7382xea4ededd();
            }
        };
        return true;
    }

    /* JADX INFO: renamed from: lambda$whatCursorPosition$10$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ AttributedString m7382xea4ededd() {
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        if (this.buf.cursor() < this.buf.length()) {
            int iCurrChar = this.buf.currChar();
            attributedStringBuilder.append((CharSequence) "Char: ");
            if (iCurrChar == 32) {
                attributedStringBuilder.append((CharSequence) "SPC");
            } else if (iCurrChar == 10) {
                attributedStringBuilder.append((CharSequence) "LFD");
            } else if (iCurrChar < 32) {
                attributedStringBuilder.append(Util.C_EXCEPTION_START);
                attributedStringBuilder.append((char) (iCurrChar + 64));
            } else if (iCurrChar == 127) {
                attributedStringBuilder.append((CharSequence) "^?");
            } else {
                attributedStringBuilder.append((char) iCurrChar);
            }
            attributedStringBuilder.append((CharSequence) " (");
            attributedStringBuilder.append((CharSequence) "0").append((CharSequence) Integer.toOctalString(iCurrChar)).append((CharSequence) " ");
            attributedStringBuilder.append((CharSequence) Integer.toString(iCurrChar)).append((CharSequence) " ");
            attributedStringBuilder.append((CharSequence) PsiLiteralUtil.HEX_PREFIX).append((CharSequence) Integer.toHexString(iCurrChar)).append((CharSequence) " ");
            attributedStringBuilder.append((CharSequence) Constant.AFTER_QUTO);
        } else {
            attributedStringBuilder.append((CharSequence) "EOF");
        }
        attributedStringBuilder.append((CharSequence) "   ");
        attributedStringBuilder.append((CharSequence) "point ");
        attributedStringBuilder.append((CharSequence) Integer.toString(this.buf.cursor() + 1));
        attributedStringBuilder.append((CharSequence) " of ");
        attributedStringBuilder.append((CharSequence) Integer.toString(this.buf.length() + 1));
        attributedStringBuilder.append((CharSequence) " (");
        attributedStringBuilder.append((CharSequence) Integer.toString(this.buf.length() != 0 ? (this.buf.cursor() * 100) / this.buf.length() : 100));
        attributedStringBuilder.append((CharSequence) "%)");
        attributedStringBuilder.append((CharSequence) "   ");
        attributedStringBuilder.append((CharSequence) "column ");
        attributedStringBuilder.append((CharSequence) Integer.toString(this.buf.cursor() - findbol()));
        return attributedStringBuilder.toAttributedString();
    }

    protected Map<String, Widget> builtinWidgets() {
        HashMap map = new HashMap();
        map.put(LineReader.ACCEPT_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda103
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.acceptLine();
            }
        });
        map.put(LineReader.ARGUMENT_BASE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda156
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.argumentBase();
            }
        });
        map.put(LineReader.BACKWARD_CHAR, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda168
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.backwardChar();
            }
        });
        map.put(LineReader.BACKWARD_DELETE_CHAR, new LineReaderImpl$$ExternalSyntheticLambda180(this));
        map.put(LineReader.BACKWARD_DELETE_WORD, new LineReaderImpl$$ExternalSyntheticLambda9(this));
        map.put(LineReader.BACKWARD_KILL_LINE, new LineReaderImpl$$ExternalSyntheticLambda20(this));
        map.put(LineReader.BACKWARD_KILL_WORD, new LineReaderImpl$$ExternalSyntheticLambda32(this));
        map.put(LineReader.BACKWARD_WORD, new LineReaderImpl$$ExternalSyntheticLambda94(this));
        map.put(LineReader.BEEP, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda54
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.beep();
            }
        });
        map.put(LineReader.BEGINNING_OF_BUFFER_OR_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda67
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.beginningOfBufferOrHistory();
            }
        });
        map.put(LineReader.BEGINNING_OF_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda115
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.beginningOfHistory();
            }
        });
        map.put(LineReader.BEGINNING_OF_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda126
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.beginningOfLine();
            }
        });
        map.put(LineReader.BEGINNING_OF_LINE_HIST, new LineReaderImpl$$ExternalSyntheticLambda137(this));
        map.put(LineReader.CAPITALIZE_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda148
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.capitalizeWord();
            }
        });
        map.put(LineReader.CLEAR, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda149
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.clear();
            }
        });
        map.put(LineReader.CLEAR_SCREEN, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda151
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.clearScreen();
            }
        });
        map.put(LineReader.COMPLETE_PREFIX, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda152
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.completePrefix();
            }
        });
        map.put(LineReader.COMPLETE_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda153
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.completeWord();
            }
        });
        map.put(LineReader.COPY_PREV_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda154
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.copyPrevWord();
            }
        });
        map.put(LineReader.COPY_REGION_AS_KILL, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda155
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.copyRegionAsKill();
            }
        });
        map.put(LineReader.DELETE_CHAR, new LineReaderImpl$$ExternalSyntheticLambda157(this));
        map.put(LineReader.DELETE_CHAR_OR_LIST, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda158
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.deleteCharOrList();
            }
        });
        map.put(LineReader.DELETE_WORD, new LineReaderImpl$$ExternalSyntheticLambda159(this));
        map.put(LineReader.DIGIT_ARGUMENT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda160
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.digitArgument();
            }
        });
        map.put(LineReader.DO_LOWERCASE_VERSION, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda162
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.doLowercaseVersion();
            }
        });
        map.put(LineReader.DOWN_CASE_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda163
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.downCaseWord();
            }
        });
        map.put(LineReader.DOWN_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda164
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.downLine();
            }
        });
        map.put(LineReader.DOWN_LINE_OR_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda165
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.downLineOrHistory();
            }
        });
        map.put(LineReader.DOWN_LINE_OR_SEARCH, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda166
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.downLineOrSearch();
            }
        });
        map.put(LineReader.DOWN_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda167
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.downHistory();
            }
        });
        map.put(LineReader.EMACS_EDITING_MODE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda169
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.emacsEditingMode();
            }
        });
        map.put(LineReader.EMACS_BACKWARD_WORD, new LineReaderImpl$$ExternalSyntheticLambda170(this));
        map.put(LineReader.EMACS_FORWARD_WORD, new LineReaderImpl$$ExternalSyntheticLambda171(this));
        map.put(LineReader.END_OF_BUFFER_OR_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda173
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.endOfBufferOrHistory();
            }
        });
        map.put(LineReader.END_OF_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda174
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.endOfHistory();
            }
        });
        map.put(LineReader.END_OF_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda175
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.endOfLine();
            }
        });
        map.put(LineReader.END_OF_LINE_HIST, new LineReaderImpl$$ExternalSyntheticLambda176(this));
        map.put(LineReader.EXCHANGE_POINT_AND_MARK, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda177
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.exchangePointAndMark();
            }
        });
        map.put(LineReader.EXPAND_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda178
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.expandHistory();
            }
        });
        map.put(LineReader.EXPAND_OR_COMPLETE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda179
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.expandOrComplete();
            }
        });
        map.put(LineReader.EXPAND_OR_COMPLETE_PREFIX, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda181
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.expandOrCompletePrefix();
            }
        });
        map.put(LineReader.EXPAND_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda182
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.expandWord();
            }
        });
        map.put(LineReader.FRESH_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.freshLine();
            }
        });
        map.put(LineReader.FORWARD_CHAR, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.forwardChar();
            }
        });
        map.put(LineReader.FORWARD_WORD, new LineReaderImpl$$ExternalSyntheticLambda3(this));
        map.put(LineReader.HISTORY_INCREMENTAL_SEARCH_BACKWARD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda4
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.historyIncrementalSearchBackward();
            }
        });
        map.put(LineReader.HISTORY_INCREMENTAL_SEARCH_FORWARD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda5
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.historyIncrementalSearchForward();
            }
        });
        map.put(LineReader.HISTORY_SEARCH_BACKWARD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda6
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.historySearchBackward();
            }
        });
        map.put(LineReader.HISTORY_SEARCH_FORWARD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda7
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.historySearchForward();
            }
        });
        map.put(LineReader.INSERT_CLOSE_CURLY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda8
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.insertCloseCurly();
            }
        });
        map.put(LineReader.INSERT_CLOSE_PAREN, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda10
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.insertCloseParen();
            }
        });
        map.put(LineReader.INSERT_CLOSE_SQUARE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda12
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.insertCloseSquare();
            }
        });
        map.put(LineReader.INSERT_COMMENT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda13
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.insertComment();
            }
        });
        map.put(LineReader.KILL_BUFFER, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda14
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.killBuffer();
            }
        });
        map.put(LineReader.KILL_LINE, new LineReaderImpl$$ExternalSyntheticLambda98(this));
        map.put(LineReader.KILL_REGION, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda15
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.killRegion();
            }
        });
        map.put(LineReader.KILL_WHOLE_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda16
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.killWholeLine();
            }
        });
        map.put(LineReader.KILL_WORD, new LineReaderImpl$$ExternalSyntheticLambda17(this));
        map.put(LineReader.LIST_CHOICES, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda18
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.listChoices();
            }
        });
        map.put(LineReader.MENU_COMPLETE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda19
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.menuComplete();
            }
        });
        map.put(LineReader.MENU_EXPAND_OR_COMPLETE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda21
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.menuExpandOrComplete();
            }
        });
        map.put(LineReader.NEG_ARGUMENT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda23
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.negArgument();
            }
        });
        map.put(LineReader.OVERWRITE_MODE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda24
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.overwriteMode();
            }
        });
        map.put(LineReader.QUOTED_INSERT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda25
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.quotedInsert();
            }
        });
        map.put(LineReader.REDISPLAY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda26
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.redisplay();
            }
        });
        map.put(LineReader.REDRAW_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda27
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.redrawLine();
            }
        });
        map.put(LineReader.REDO, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda28
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.redo();
            }
        });
        map.put(LineReader.SELF_INSERT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda29
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.selfInsert();
            }
        });
        map.put(LineReader.SELF_INSERT_UNMETA, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda30
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.selfInsertUnmeta();
            }
        });
        map.put(LineReader.SEND_BREAK, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda31
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.sendBreak();
            }
        });
        map.put(LineReader.SET_MARK_COMMAND, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda34
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.setMarkCommand();
            }
        });
        map.put(LineReader.TRANSPOSE_CHARS, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda35
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.transposeChars();
            }
        });
        map.put(LineReader.TRANSPOSE_WORDS, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda36
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.transposeWords();
            }
        });
        map.put(LineReader.UNDEFINED_KEY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda37
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.undefinedKey();
            }
        });
        map.put(LineReader.UNIVERSAL_ARGUMENT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda38
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.universalArgument();
            }
        });
        map.put(LineReader.UNDO, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda39
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.undo();
            }
        });
        map.put(LineReader.UP_CASE_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda40
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.upCaseWord();
            }
        });
        map.put(LineReader.UP_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda41
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.upHistory();
            }
        });
        map.put(LineReader.UP_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda42
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.upLine();
            }
        });
        map.put(LineReader.UP_LINE_OR_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda43
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.upLineOrHistory();
            }
        });
        map.put(LineReader.UP_LINE_OR_SEARCH, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda45
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.upLineOrSearch();
            }
        });
        map.put(LineReader.VI_ADD_EOL, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda46
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viAddEol();
            }
        });
        map.put(LineReader.VI_ADD_NEXT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda47
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viAddNext();
            }
        });
        map.put(LineReader.VI_BACKWARD_CHAR, new LineReaderImpl$$ExternalSyntheticLambda99(this));
        map.put(LineReader.VI_BACKWARD_DELETE_CHAR, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda48
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viBackwardDeleteChar();
            }
        });
        map.put(LineReader.VI_BACKWARD_BLANK_WORD, new LineReaderImpl$$ExternalSyntheticLambda49(this));
        map.put(LineReader.VI_BACKWARD_BLANK_WORD_END, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda50
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viBackwardBlankWordEnd();
            }
        });
        map.put(LineReader.VI_BACKWARD_KILL_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda51
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viBackwardKillWord();
            }
        });
        map.put(LineReader.VI_BACKWARD_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda52
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viBackwardWord();
            }
        });
        map.put(LineReader.VI_BACKWARD_WORD_END, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda53
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viBackwardWordEnd();
            }
        });
        map.put(LineReader.VI_BEGINNING_OF_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda56
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viBeginningOfLine();
            }
        });
        map.put(LineReader.VI_CMD_MODE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda57
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viCmdMode();
            }
        });
        map.put(LineReader.VI_DIGIT_OR_BEGINNING_OF_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda58
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viDigitOrBeginningOfLine();
            }
        });
        map.put(LineReader.VI_DOWN_LINE_OR_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda59
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viDownLineOrHistory();
            }
        });
        map.put(LineReader.VI_CHANGE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda60
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viChange();
            }
        });
        map.put(LineReader.VI_CHANGE_EOL, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda61
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viChangeEol();
            }
        });
        map.put(LineReader.VI_CHANGE_WHOLE_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda62
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viChangeWholeLine();
            }
        });
        map.put(LineReader.VI_DELETE_CHAR, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda63
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viDeleteChar();
            }
        });
        map.put(LineReader.VI_DELETE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda64
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viDelete();
            }
        });
        map.put(LineReader.VI_END_OF_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda65
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viEndOfLine();
            }
        });
        map.put(LineReader.VI_KILL_EOL, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda104
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viKillEol();
            }
        });
        map.put(LineReader.VI_FIRST_NON_BLANK, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda105
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viFirstNonBlank();
            }
        });
        map.put(LineReader.VI_FIND_NEXT_CHAR, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda107
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viFindNextChar();
            }
        });
        map.put(LineReader.VI_FIND_NEXT_CHAR_SKIP, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda108
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viFindNextCharSkip();
            }
        });
        map.put(LineReader.VI_FIND_PREV_CHAR, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda109
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viFindPrevChar();
            }
        });
        map.put(LineReader.VI_FIND_PREV_CHAR_SKIP, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda110
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viFindPrevCharSkip();
            }
        });
        map.put(LineReader.VI_FORWARD_BLANK_WORD, new LineReaderImpl$$ExternalSyntheticLambda111(this));
        map.put(LineReader.VI_FORWARD_BLANK_WORD_END, new LineReaderImpl$$ExternalSyntheticLambda112(this));
        map.put(LineReader.VI_FORWARD_CHAR, new LineReaderImpl$$ExternalSyntheticLambda113(this));
        map.put(LineReader.VI_FORWARD_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda114
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viForwardWord();
            }
        });
        map.put(LineReader.VI_FORWARD_WORD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda114
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viForwardWord();
            }
        });
        map.put(LineReader.VI_FORWARD_WORD_END, new LineReaderImpl$$ExternalSyntheticLambda116(this));
        map.put(LineReader.VI_HISTORY_SEARCH_BACKWARD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda118
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viHistorySearchBackward();
            }
        });
        map.put(LineReader.VI_HISTORY_SEARCH_FORWARD, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda119
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viHistorySearchForward();
            }
        });
        map.put(LineReader.VI_INSERT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda120
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viInsert();
            }
        });
        map.put(LineReader.VI_INSERT_BOL, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda121
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viInsertBol();
            }
        });
        map.put(LineReader.VI_INSERT_COMMENT, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda122
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viInsertComment();
            }
        });
        map.put(LineReader.VI_JOIN, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda123
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viJoin();
            }
        });
        map.put(LineReader.VI_KILL_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda124
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viKillWholeLine();
            }
        });
        map.put(LineReader.VI_MATCH_BRACKET, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda125
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viMatchBracket();
            }
        });
        map.put(LineReader.VI_OPEN_LINE_ABOVE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda127
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viOpenLineAbove();
            }
        });
        map.put(LineReader.VI_OPEN_LINE_BELOW, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda129
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viOpenLineBelow();
            }
        });
        map.put(LineReader.VI_PUT_AFTER, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda130
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viPutAfter();
            }
        });
        map.put(LineReader.VI_PUT_BEFORE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda131
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viPutBefore();
            }
        });
        map.put(LineReader.VI_REPEAT_FIND, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda132
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viRepeatFind();
            }
        });
        map.put(LineReader.VI_REPEAT_SEARCH, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda133
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viRepeatSearch();
            }
        });
        map.put(LineReader.VI_REPLACE_CHARS, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda134
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viReplaceChars();
            }
        });
        map.put(LineReader.VI_REV_REPEAT_FIND, new LineReaderImpl$$ExternalSyntheticLambda95(this));
        map.put(LineReader.VI_REV_REPEAT_SEARCH, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda135
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viRevRepeatSearch();
            }
        });
        map.put(LineReader.VI_SWAP_CASE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda136
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viSwapCase();
            }
        });
        map.put(LineReader.VI_UP_LINE_OR_HISTORY, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda138
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viUpLineOrHistory();
            }
        });
        map.put(LineReader.VI_YANK, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda140
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viYankTo();
            }
        });
        map.put(LineReader.VI_YANK_WHOLE_LINE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda141
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.viYankWholeLine();
            }
        });
        map.put(LineReader.VISUAL_LINE_MODE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda142
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.visualLineMode();
            }
        });
        map.put(LineReader.VISUAL_MODE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda143
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.visualMode();
            }
        });
        map.put(LineReader.WHAT_CURSOR_POSITION, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda144
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.whatCursorPosition();
            }
        });
        map.put(LineReader.YANK, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda145
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.yank();
            }
        });
        map.put(LineReader.YANK_POP, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda146
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.yankPop();
            }
        });
        map.put(LineReader.MOUSE, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda147
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.mouse();
            }
        });
        return map;
    }

    public boolean redisplay() {
        redisplay(true);
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:59:0x01c7  */
    protected void redisplay(boolean z) {
        List<AttributedString> listColumnSplitLength;
        List<AttributedString> arrayList;
        String string;
        int iCursorPos;
        int i;
        if (this.skipRedisplay) {
            this.skipRedisplay = false;
            return;
        }
        if (this.size.getRows() > 0 && this.size.getRows() < 3) {
            AttributedStringBuilder attributedStringBuilderTabs = new AttributedStringBuilder().tabs(4);
            attributedStringBuilderTabs.append(this.prompt);
            concat(getHighlightedBuffer(this.buf.toString()).columnSplitLength(Integer.MAX_VALUE), attributedStringBuilderTabs);
            AttributedString attributedString = attributedStringBuilderTabs.toAttributedString();
            attributedStringBuilderTabs.setLength(0);
            attributedStringBuilderTabs.append(this.prompt);
            concat(getMaskedBuffer(this.buf.upToCursor()).columnSplitLength(Integer.MAX_VALUE), attributedStringBuilderTabs);
            AttributedString attributedString2 = attributedStringBuilderTabs.toAttributedString();
            int iWcwidth = WCWidth.wcwidth(8230);
            int columns = this.size.getColumns();
            int iColumnLength = attributedString2.columnLength();
            int i2 = (columns / 2) + 1;
            while (true) {
                int i3 = this.smallTerminalOffset;
                if (iColumnLength > i3 + iWcwidth) {
                    break;
                } else {
                    this.smallTerminalOffset = i3 - i2;
                }
            }
            while (true) {
                i = this.smallTerminalOffset;
                if (iColumnLength < (i + columns) - iWcwidth) {
                    break;
                } else {
                    this.smallTerminalOffset = i + i2;
                }
            }
            if (i > 0) {
                attributedStringBuilderTabs.setLength(0);
                attributedStringBuilderTabs.append(StringUtil.ELLIPSIS);
                attributedStringBuilderTabs.append(attributedString.columnSubSequence(this.smallTerminalOffset + iWcwidth, Integer.MAX_VALUE));
                attributedString = attributedStringBuilderTabs.toAttributedString();
            }
            if (attributedString.columnLength() >= this.smallTerminalOffset + columns) {
                attributedStringBuilderTabs.setLength(0);
                attributedStringBuilderTabs.append(attributedString.columnSubSequence(0, columns - iWcwidth));
                attributedStringBuilderTabs.append(StringUtil.ELLIPSIS);
                attributedString = attributedStringBuilderTabs.toAttributedString();
            }
            this.display.update(Collections.singletonList(attributedString), iColumnLength - this.smallTerminalOffset, z);
            return;
        }
        ArrayList arrayList2 = new ArrayList();
        AttributedString displayedBufferWithPrompts = getDisplayedBufferWithPrompts(arrayList2);
        if (this.size.getColumns() <= 0) {
            listColumnSplitLength = new ArrayList<>();
            listColumnSplitLength.add(displayedBufferWithPrompts);
        } else {
            listColumnSplitLength = displayedBufferWithPrompts.columnSplitLength(this.size.getColumns(), true, this.display.delayLineWrap());
        }
        if (this.rightPrompt.length() == 0 || this.size.getColumns() <= 0) {
            arrayList = new ArrayList<>();
        } else {
            arrayList = this.rightPrompt.columnSplitLength(this.size.getColumns());
        }
        while (true) {
            string = "";
            if (listColumnSplitLength.size() >= arrayList.size()) {
                break;
            } else {
                listColumnSplitLength.add(new AttributedString(""));
            }
        }
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            listColumnSplitLength.set(i4, addRightPrompt(arrayList.get(i4), listColumnSplitLength.get(i4)));
        }
        if (this.size.getColumns() > 0) {
            AttributedStringBuilder attributedStringBuilderTabs2 = new AttributedStringBuilder().tabs(4);
            attributedStringBuilderTabs2.append(this.prompt);
            String strUpToCursor = this.buf.upToCursor();
            Character ch = this.mask;
            if (ch == null) {
                string = strUpToCursor;
            } else if (ch.charValue() != 0) {
                StringBuilder sb = new StringBuilder();
                int length = strUpToCursor.length();
                while (true) {
                    int i5 = length - 1;
                    if (length <= 0) {
                        break;
                    }
                    sb.append(this.mask.charValue());
                    length = i5;
                }
                string = sb.toString();
            }
            attributedStringBuilderTabs2.append(insertSecondaryPrompts(new AttributedString(string), arrayList2, false));
            List<AttributedString> listColumnSplitLength2 = attributedStringBuilderTabs2.columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap());
            if (listColumnSplitLength2.isEmpty()) {
                iCursorPos = -1;
            } else {
                iCursorPos = this.size.cursorPos(listColumnSplitLength2.size() - 1, listColumnSplitLength2.get(listColumnSplitLength2.size() - 1).columnLength());
            }
        } else {
            iCursorPos = -1;
        }
        this.display.update(listColumnSplitLength, iCursorPos, z);
    }

    private void concat(List<AttributedString> list, AttributedStringBuilder attributedStringBuilder) {
        if (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {
                attributedStringBuilder.append(list.get(i));
                attributedStringBuilder.style(attributedStringBuilder.style().inverse());
                attributedStringBuilder.append("\\n");
                attributedStringBuilder.style(attributedStringBuilder.style().inverseOff());
            }
        }
        attributedStringBuilder.append(list.get(list.size() - 1));
    }

    private AttributedString getDisplayedBufferWithPrompts(List<AttributedString> list) {
        AttributedString attributedStringInsertSecondaryPrompts = insertSecondaryPrompts(getHighlightedBuffer(this.buf.toString()), list);
        AttributedStringBuilder attributedStringBuilderTabs = new AttributedStringBuilder().tabs(4);
        attributedStringBuilderTabs.append(this.prompt);
        attributedStringBuilderTabs.append(attributedStringInsertSecondaryPrompts);
        if (this.post != null) {
            attributedStringBuilderTabs.append((CharSequence) "\n");
            attributedStringBuilderTabs.append(this.post.get());
        }
        return attributedStringBuilderTabs.toAttributedString();
    }

    private AttributedString getMaskedBuffer(String str) {
        Character ch = this.mask;
        if (ch != null) {
            if (ch.charValue() == 0) {
                str = "";
            } else {
                StringBuilder sb = new StringBuilder();
                int length = str.length();
                while (true) {
                    int i = length - 1;
                    if (length <= 0) {
                        break;
                    }
                    sb.append(this.mask.charValue());
                    length = i;
                }
                str = sb.toString();
            }
        }
        return new AttributedString(str);
    }

    private AttributedString getHighlightedBuffer(String str) {
        if (this.mask != null) {
            return getMaskedBuffer(str);
        }
        if (this.highlighter != null && !isSet(LineReader.Option.DISABLE_HIGHLIGHTER)) {
            return this.highlighter.highlight(this, str);
        }
        return new AttributedString(str);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:22:0x0064. Please report as an issue. */
    private AttributedString expandPromptPattern(String str, int i, String str2, int i2) {
        int i3;
        boolean z;
        AttributedString attributedString;
        boolean z2;
        StringBuilder sb;
        ArrayList arrayList = new ArrayList();
        StringBuilder sb2 = new StringBuilder();
        String str3 = str + "%{";
        int length = str3.length();
        StringBuilder sb3 = sb2;
        int i4 = 0;
        int iColumnLength = 0;
        int iCharAt = -1;
        StringBuilder sb4 = null;
        int size = -1;
        int length2 = -1;
        boolean z3 = false;
        int i5 = i;
        while (i4 < length) {
            int i6 = i4 + 1;
            char cCharAt = str3.charAt(i4);
            char c = MangleConstant.PLATFORM_FUNCTION_MARKER;
            if (cCharAt != '%' || i6 >= length) {
                sb3.append(cCharAt);
                sb4 = sb4;
                i4 = i6;
            } else {
                boolean z4 = false;
                int i7 = 0;
                while (true) {
                    i3 = i6 + 1;
                    StringBuilder sb5 = sb4;
                    char cCharAt2 = str3.charAt(i6);
                    if (cCharAt2 != c) {
                        if (cCharAt2 != '-') {
                            if (cCharAt2 == 'P') {
                                if (z4 && i7 >= 0) {
                                    i5 = i7;
                                }
                                if (i3 < length) {
                                    iCharAt = str3.charAt(i3);
                                    i3 = i6 + 2;
                                }
                                sb4 = sb5;
                                length2 = sb3.length();
                                size = arrayList.size();
                            } else if (cCharAt2 == '{' || cCharAt2 == '}') {
                                String string = sb3.toString();
                                if (!z3) {
                                    attributedString = AttributedString.fromAnsi(string);
                                    iColumnLength += attributedString.columnLength();
                                } else {
                                    attributedString = new AttributedString(string, AttributedStyle.HIDDEN);
                                }
                                if (size == arrayList.size()) {
                                    sb = i3 < length ? new StringBuilder() : sb3;
                                    z2 = false;
                                } else {
                                    z2 = false;
                                    sb3.setLength(0);
                                    sb = sb3;
                                    sb3 = sb5;
                                }
                                arrayList.add(attributedString);
                                boolean z5 = cCharAt2 != '{' ? z2 : true;
                                sb4 = sb3;
                                sb3 = sb;
                                z3 = z5;
                            } else if (cCharAt2 != 'M') {
                                if (cCharAt2 != 'N') {
                                    switch (cCharAt2) {
                                        case '0':
                                        case '1':
                                        case '2':
                                        case '3':
                                        case '4':
                                        case '5':
                                        case '6':
                                        case '7':
                                        case '8':
                                        case '9':
                                            break;
                                        default:
                                            break;
                                    }
                                } else {
                                    sb3.append(getInt(LineReader.LINE_OFFSET, 0) + i2);
                                }
                            } else if (str2 != null) {
                                sb3.append(str2);
                            }
                        }
                        if (cCharAt2 == '-') {
                            cCharAt2 = str3.charAt(i3);
                            i3 = i6 + 2;
                            z = true;
                        } else {
                            z = false;
                        }
                        int i8 = 0;
                        while (cCharAt2 >= '0' && cCharAt2 <= '9') {
                            i8 = (i8 < 0 ? 0 : i8 * 10) + (cCharAt2 - '0');
                            cCharAt2 = str3.charAt(i3);
                            i3++;
                        }
                        i7 = z ? -i8 : i8;
                        i6 = i3 - 1;
                        sb4 = sb5;
                        z4 = true;
                        c = MangleConstant.PLATFORM_FUNCTION_MARKER;
                    } else {
                        sb3.append(cCharAt2);
                    }
                    sb4 = sb5;
                }
                i4 = i3;
            }
        }
        StringBuilder sb6 = sb4;
        if (i5 > iColumnLength) {
            int iWcwidth = (i5 - iColumnLength) / WCWidth.wcwidth(iCharAt);
            while (true) {
                iWcwidth--;
                if (iWcwidth >= 0) {
                    sb6.insert(length2, (char) iCharAt);
                } else {
                    arrayList.set(size, AttributedString.fromAnsi(sb6.toString()));
                }
            }
        }
        return AttributedString.join(null, arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AttributedString insertSecondaryPrompts(AttributedString attributedString, List<AttributedString> list) {
        return insertSecondaryPrompts(attributedString, list, true);
    }

    private AttributedString insertSecondaryPrompts(AttributedString attributedString, List<AttributedString> list, boolean z) {
        int i;
        int iColumnLength;
        AttributedString attributedStringExpandPromptPattern;
        String missing;
        String missing2;
        Objects.requireNonNull(list);
        List<AttributedString> listColumnSplitLength = attributedString.columnSplitLength(Integer.MAX_VALUE);
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        String string = getString(LineReader.SECONDARY_PROMPT_PATTERN, "%M> ");
        boolean zContains = string.contains("%M");
        AttributedStringBuilder attributedStringBuilder2 = new AttributedStringBuilder();
        ArrayList arrayList = new ArrayList();
        if (z && string.contains("%P")) {
            iColumnLength = this.prompt.columnLength();
            int i2 = 0;
            while (i2 < listColumnSplitLength.size() - 1) {
                attributedStringBuilder2.append(listColumnSplitLength.get(i2)).append((CharSequence) "\n");
                if (zContains) {
                    try {
                        this.parser.parse(attributedStringBuilder2.toString(), attributedStringBuilder2.length(), Parser.ParseContext.SECONDARY_PROMPT);
                    } catch (EOFError e) {
                        missing2 = e.getMissing();
                    } catch (SyntaxError unused) {
                    }
                }
                missing2 = "";
                arrayList.add(missing2);
                i2++;
                iColumnLength = Math.max(iColumnLength, expandPromptPattern(string, 0, missing2, i2).columnLength());
            }
            i = 0;
            attributedStringBuilder2.setLength(0);
        } else {
            i = 0;
            iColumnLength = 0;
        }
        while (i < listColumnSplitLength.size() - 1) {
            attributedStringBuilder.append(listColumnSplitLength.get(i)).append((CharSequence) "\n");
            attributedStringBuilder2.append(listColumnSplitLength.get(i)).append((CharSequence) "\n");
            if (z) {
                if (!zContains) {
                    missing = "";
                } else if (arrayList.isEmpty()) {
                    try {
                        this.parser.parse(attributedStringBuilder2.toString(), attributedStringBuilder2.length(), Parser.ParseContext.SECONDARY_PROMPT);
                    } catch (EOFError e2) {
                        missing = e2.getMissing();
                    } catch (SyntaxError unused2) {
                    }
                    missing = "";
                } else {
                    missing = (String) arrayList.get(i);
                }
                attributedStringExpandPromptPattern = expandPromptPattern(string, iColumnLength, missing, i + 1);
            } else {
                attributedStringExpandPromptPattern = list.get(i);
            }
            list.add(attributedStringExpandPromptPattern);
            attributedStringBuilder.append(attributedStringExpandPromptPattern);
            i++;
        }
        attributedStringBuilder.append(listColumnSplitLength.get(i));
        attributedStringBuilder2.append(listColumnSplitLength.get(i));
        return attributedStringBuilder.toAttributedString();
    }

    private AttributedString addRightPrompt(AttributedString attributedString, AttributedString attributedString2) {
        int iColumnLength = attributedString.columnLength();
        int i = (attributedString2.length() <= 0 || attributedString2.charAt(attributedString2.length() - 1) != '\n') ? 0 : 1;
        int columns = (this.size.getColumns() - iColumnLength) - (attributedString2.columnLength() + i);
        if (columns < 3) {
            return attributedString2;
        }
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder(this.size.getColumns());
        int length = attributedString2.length();
        if (i != 0) {
            length--;
        }
        attributedStringBuilder.append(attributedString2, 0, length);
        for (int i2 = 0; i2 < columns; i2++) {
            attributedStringBuilder.append(io.netty.util.internal.StringUtil.SPACE);
        }
        attributedStringBuilder.append(attributedString);
        if (i != 0) {
            attributedStringBuilder.append('\n');
        }
        return attributedStringBuilder.toAttributedString();
    }

    protected boolean insertTab() {
        return isSet(LineReader.Option.INSERT_TAB) && getLastBinding().equals("\t") && this.buf.toString().matches("(^|[\\s\\S]*\n)[\r\n\t ]*");
    }

    protected boolean expandHistory() {
        String string = this.buf.toString();
        String strExpandHistory = this.expander.expandHistory(this.history, string);
        if (strExpandHistory.equals(string)) {
            return false;
        }
        this.buf.clear();
        this.buf.write(strExpandHistory);
        return true;
    }

    protected boolean expandWord() {
        if (insertTab()) {
            return selfInsert();
        }
        return doComplete(CompletionType.Expand, isSet(LineReader.Option.MENU_COMPLETE), false);
    }

    protected boolean expandOrComplete() {
        if (insertTab()) {
            return selfInsert();
        }
        return doComplete(CompletionType.ExpandComplete, isSet(LineReader.Option.MENU_COMPLETE), false);
    }

    protected boolean expandOrCompletePrefix() {
        if (insertTab()) {
            return selfInsert();
        }
        return doComplete(CompletionType.ExpandComplete, isSet(LineReader.Option.MENU_COMPLETE), true);
    }

    protected boolean completeWord() {
        if (insertTab()) {
            return selfInsert();
        }
        return doComplete(CompletionType.Complete, isSet(LineReader.Option.MENU_COMPLETE), false);
    }

    protected boolean menuComplete() {
        if (insertTab()) {
            return selfInsert();
        }
        return doComplete(CompletionType.Complete, true, false);
    }

    protected boolean menuExpandOrComplete() {
        if (insertTab()) {
            return selfInsert();
        }
        return doComplete(CompletionType.ExpandComplete, true, false);
    }

    protected boolean completePrefix() {
        if (insertTab()) {
            return selfInsert();
        }
        return doComplete(CompletionType.Complete, isSet(LineReader.Option.MENU_COMPLETE), true);
    }

    protected boolean listChoices() {
        return doComplete(CompletionType.List, isSet(LineReader.Option.MENU_COMPLETE), false);
    }

    protected boolean deleteCharOrList() {
        if (this.buf.cursor() != this.buf.length() || this.buf.length() == 0) {
            return deleteChar();
        }
        return doComplete(CompletionType.List, isSet(LineReader.Option.MENU_COMPLETE), false);
    }

    protected boolean doComplete(CompletionType completionType, boolean z, boolean z2) {
        List listAsList;
        final Predicate predicate;
        String commonStart;
        Candidate candidate;
        String strWord;
        String str;
        CompletionType completionType2 = completionType;
        try {
            if (expandHistory()) {
                return true;
            }
            try {
                ParsedLine parsedLine = this.parser.parse(this.buf.toString(), this.buf.cursor(), Parser.ParseContext.COMPLETE);
                ArrayList<Candidate> arrayList = new ArrayList();
                try {
                    Completer completer = this.completer;
                    if (completer != null) {
                        completer.complete(this, parsedLine, arrayList);
                    }
                    if (completionType2 == CompletionType.ExpandComplete || completionType2 == CompletionType.Expand) {
                        String strExpandVar = this.expander.expandVar(parsedLine.word());
                        if (!parsedLine.word().equals(strExpandVar)) {
                            if (z2) {
                                this.buf.backspace(parsedLine.wordCursor());
                            } else {
                                this.buf.move(parsedLine.word().length() - parsedLine.wordCursor());
                                this.buf.backspace(parsedLine.word().length());
                            }
                            this.buf.write(strExpandVar);
                            return true;
                        }
                        if (completionType2 == CompletionType.Expand) {
                            return false;
                        }
                        completionType2 = CompletionType.Complete;
                    }
                    boolean zIsSet = isSet(LineReader.Option.CASE_INSENSITIVE);
                    int i = getInt("errors", 2);
                    TreeMap treeMap = new TreeMap(zIsSet ? String.CASE_INSENSITIVE_ORDER : null);
                    for (Candidate candidate2 : arrayList) {
                        ((List) treeMap.computeIfAbsent(AttributedString.fromAnsi(candidate2.value()).toString(), new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda96
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return LineReaderImpl.lambda$doComplete$11((String) obj);
                            }
                        })).add(candidate2);
                    }
                    if (z2) {
                        final String strSubstring = parsedLine.word().substring(0, parsedLine.wordCursor());
                        listAsList = Arrays.asList(simpleMatcher(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda161
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).startsWith(strSubstring);
                            }
                        }), simpleMatcher(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda172
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).contains(strSubstring);
                            }
                        }), typoMatcher(strSubstring, i));
                        predicate = new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda183
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).equals(strSubstring);
                            }
                        };
                    } else if (isSet(LineReader.Option.COMPLETE_IN_WORD)) {
                        final String strWord2 = parsedLine.word();
                        String strSubstring2 = strWord2.substring(0, parsedLine.wordCursor());
                        String strSubstring3 = strWord2.substring(parsedLine.wordCursor());
                        final Pattern patternCompile = Pattern.compile(Pattern.quote(strSubstring2) + ".*" + Pattern.quote(strSubstring3) + ".*");
                        final Pattern patternCompile2 = Pattern.compile(".*" + Pattern.quote(strSubstring2) + ".*" + Pattern.quote(strSubstring3) + ".*");
                        listAsList = Arrays.asList(simpleMatcher(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda11
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return patternCompile.matcher((String) obj).matches();
                            }
                        }), simpleMatcher(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda22
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return patternCompile2.matcher((String) obj).matches();
                            }
                        }), typoMatcher(strWord2, i));
                        predicate = new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda33
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).equals(strWord2);
                            }
                        };
                    } else {
                        final String strWord3 = parsedLine.word();
                        listAsList = Arrays.asList(simpleMatcher(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda44
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).startsWith(strWord3);
                            }
                        }), simpleMatcher(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda55
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).contains(strWord3);
                            }
                        }), typoMatcher(strWord3, i));
                        predicate = new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda66
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).equals(strWord3);
                            }
                        };
                    }
                    Map mapEmptyMap = Collections.emptyMap();
                    Iterator it = listAsList.iterator();
                    while (it.hasNext()) {
                        mapEmptyMap = (Map) ((Function) it.next()).apply(treeMap);
                        if (!mapEmptyMap.isEmpty()) {
                            break;
                        }
                    }
                    if (mapEmptyMap.isEmpty()) {
                        return false;
                    }
                    if (completionType2 == CompletionType.List) {
                        List<Candidate> list = (List) mapEmptyMap.entrySet().stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda106
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ((List) ((Map.Entry) obj).getValue()).stream();
                            }
                        }).collect(Collectors.toList());
                        doList(list, parsedLine.word(), false);
                        return !list.isEmpty();
                    }
                    if (mapEmptyMap.size() == 1) {
                        candidate = (Candidate) mapEmptyMap.values().stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda117
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ((List) obj).stream();
                            }
                        }).findFirst().orElse(null);
                        commonStart = null;
                    } else if (isSet(LineReader.Option.RECOGNIZE_EXACT)) {
                        commonStart = null;
                        candidate = (Candidate) mapEmptyMap.values().stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda117
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return ((List) obj).stream();
                            }
                        }).filter(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda128
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((Candidate) obj).complete();
                            }
                        }).filter(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda139
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return predicate.test(((Candidate) obj).value());
                            }
                        }).findFirst().orElse(null);
                    } else {
                        commonStart = null;
                        candidate = null;
                    }
                    if (candidate != null && !candidate.value().isEmpty()) {
                        if (z2) {
                            this.buf.backspace(parsedLine.wordCursor());
                        } else {
                            this.buf.move(parsedLine.word().length() - parsedLine.wordCursor());
                            this.buf.backspace(parsedLine.word().length());
                        }
                        this.buf.write(candidate.value());
                        if (candidate.complete() && this.buf.currChar() != 32) {
                            this.buf.write(" ");
                        }
                        if (candidate.suffix() != null) {
                            redisplay();
                            Binding binding = readBinding(getKeys());
                            if (binding != null) {
                                String string = getString(LineReader.REMOVE_SUFFIX_CHARS, " \t\n;&|");
                                String strName = binding instanceof Reference ? ((Reference) binding).name() : commonStart;
                                if ((LineReader.SELF_INSERT.equals(strName) && string.indexOf(getLastBinding().charAt(0)) >= 0) || LineReader.ACCEPT_LINE.equals(strName)) {
                                    this.buf.backspace(candidate.suffix().length());
                                    if (getLastBinding().charAt(0) != ' ') {
                                        this.buf.write(32);
                                    }
                                }
                                pushBackBinding(true);
                            }
                        }
                        return true;
                    }
                    List<Candidate> list2 = (List) mapEmptyMap.entrySet().stream().flatMap(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda150
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return ((List) ((Map.Entry) obj).getValue()).stream();
                        }
                    }).collect(Collectors.toList());
                    if (z) {
                        this.buf.move(parsedLine.word().length() - parsedLine.wordCursor());
                        this.buf.backspace(parsedLine.word().length());
                        doMenu(list2, parsedLine.word());
                        return true;
                    }
                    if (z2) {
                        strWord = parsedLine.word().substring(0, parsedLine.wordCursor());
                    } else {
                        strWord = parsedLine.word();
                        this.buf.move(strWord.length() - parsedLine.wordCursor());
                    }
                    Iterator it2 = mapEmptyMap.keySet().iterator();
                    while (true) {
                        str = commonStart;
                        if (!it2.hasNext()) {
                            break;
                        }
                        commonStart = (String) it2.next();
                        if (str != null) {
                            commonStart = getCommonStart(str, commonStart, zIsSet);
                        }
                    }
                    if (str.startsWith(strWord) && !str.equals(strWord)) {
                        this.buf.backspace(strWord.length());
                        this.buf.write(str);
                        if (((!isSet(LineReader.Option.AUTO_LIST) && isSet(LineReader.Option.AUTO_MENU)) || (isSet(LineReader.Option.AUTO_LIST) && isSet(LineReader.Option.LIST_AMBIGUOUS))) && !nextBindingIsComplete()) {
                            return true;
                        }
                        strWord = str;
                    }
                    if ((!isSet(LineReader.Option.AUTO_LIST) || doList(list2, strWord, true)) && isSet(LineReader.Option.AUTO_MENU)) {
                        this.buf.backspace(strWord.length());
                        doMenu(list2, parsedLine.word());
                    }
                    return true;
                } catch (Exception e) {
                    Log.info("Error while finding completion candidates", e);
                    return false;
                }
            } catch (Exception e2) {
                Log.info("Error while parsing line", e2);
                return false;
            }
        } catch (Exception e3) {
            Log.info("Error while expanding history", e3);
            return false;
        }
    }

    static /* synthetic */ List lambda$doComplete$11(String str) {
        return new ArrayList();
    }

    private void mergeCandidates(List<Candidate> list) {
        HashMap map = new HashMap();
        for (Candidate candidate : list) {
            if (candidate.key() != null) {
                ((List) map.computeIfAbsent(candidate.key(), new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda68
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return LineReaderImpl.lambda$mergeCandidates$24((String) obj);
                    }
                })).add(candidate);
            }
        }
        if (map.isEmpty()) {
            return;
        }
        for (List list2 : map.values()) {
            if (list2.size() >= 1) {
                list.removeAll(list2);
                list2.sort(Comparator.comparing(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda69
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((Candidate) obj).value();
                    }
                }));
                Candidate candidate2 = (Candidate) list2.get(0);
                list.add(new Candidate(candidate2.value(), (String) list2.stream().map(new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda70
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((Candidate) obj).displ();
                    }
                }).collect(Collectors.joining(" ")), candidate2.group(), candidate2.descr(), candidate2.suffix(), null, candidate2.complete()));
            }
        }
    }

    static /* synthetic */ List lambda$mergeCandidates$24(String str) {
        return new ArrayList();
    }

    static /* synthetic */ Map lambda$simpleMatcher$26(final Predicate predicate, Map map) {
        return (Map) map.entrySet().stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda84
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return predicate.test(((Map.Entry) obj).getKey());
            }
        }).collect(Collectors.toMap(new U$$ExternalSyntheticLambda0(), new LineReaderImpl$$ExternalSyntheticLambda101()));
    }

    private Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>> simpleMatcher(final Predicate<String> predicate) {
        return new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda97
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return LineReaderImpl.lambda$simpleMatcher$26(predicate, (Map) obj);
            }
        };
    }

    private Function<Map<String, List<Candidate>>, Map<String, List<Candidate>>> typoMatcher(final String str, final int i) {
        return new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda83
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7380x2cef5d34(str, i, (Map) obj);
            }
        };
    }

    /* JADX INFO: renamed from: lambda$typoMatcher$29$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ Map m7380x2cef5d34(final String str, final int i, Map map) {
        Map map2 = (Map) map.entrySet().stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda100
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.m7379x3be6303f(str, i, (Map.Entry) obj);
            }
        }).collect(Collectors.toMap(new U$$ExternalSyntheticLambda0(), new LineReaderImpl$$ExternalSyntheticLambda101()));
        if (map2.size() > 1) {
            ((List) map2.computeIfAbsent(str, new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda102
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return LineReaderImpl.lambda$null$28((String) obj);
                }
            })).add(new Candidate(str, str, "original", null, null, null, false));
        }
        return map2;
    }

    /* JADX INFO: renamed from: lambda$null$27$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ boolean m7379x3be6303f(String str, int i, Map.Entry entry) {
        return distance(str, (String) entry.getKey()) < i;
    }

    static /* synthetic */ List lambda$null$28(String str) {
        return new ArrayList();
    }

    private int distance(String str, String str2) {
        if (str.length() < str2.length()) {
            return Math.min(Levenshtein.distance(str, str2.substring(0, Math.min(str2.length(), str.length()))), Levenshtein.distance(str, str2));
        }
        return Levenshtein.distance(str, str2);
    }

    protected boolean nextBindingIsComplete() {
        redisplay();
        Binding binding = readBinding(getKeys(), this.keyMaps.get(LineReader.MENU));
        if ((binding instanceof Reference) && LineReader.MENU_COMPLETE.equals(((Reference) binding).name())) {
            return true;
        }
        pushBackBinding();
        return false;
    }

    private class MenuSupport implements Supplier<AttributedString> {
        int columns;
        String completed;
        AttributedString computed;
        int lines;
        final List<Candidate> possible;
        int selection;
        int topLine;
        String word;

        public MenuSupport(List<Candidate> list, String str) {
            ArrayList arrayList = new ArrayList();
            this.possible = arrayList;
            this.selection = -1;
            this.topLine = 0;
            this.word = "";
            this.completed = str;
            LineReaderImpl.this.computePost(list, null, arrayList, str);
            next();
        }

        public Candidate completion() {
            return this.possible.get(this.selection);
        }

        public void next() {
            this.selection = (this.selection + 1) % this.possible.size();
            update();
        }

        public void previous() {
            this.selection = ((this.selection + this.possible.size()) - 1) % this.possible.size();
            update();
        }

        public void down() {
            if (LineReaderImpl.this.isSet(LineReader.Option.LIST_ROWS_FIRST)) {
                int i = this.selection;
                int i2 = this.columns;
                int i3 = i / i2;
                int i4 = i % i2;
                int i5 = i3 + 1;
                if ((i2 * i5) + i4 >= this.possible.size()) {
                    i4++;
                    i5 = 0;
                    if (i4 >= this.columns) {
                        i4 = 0;
                    }
                }
                this.selection = (i5 * this.columns) + i4;
                update();
                return;
            }
            next();
        }

        public void left() {
            int i;
            if (LineReaderImpl.this.isSet(LineReader.Option.LIST_ROWS_FIRST)) {
                previous();
                return;
            }
            int i2 = this.selection;
            int i3 = this.lines;
            int i4 = i2 / i3;
            int i5 = i2 % i3;
            if (i4 - 1 >= 0) {
                i = i4 - 1;
            } else {
                i = this.columns - 1;
                i5--;
            }
            int i6 = (i * i3) + i5;
            this.selection = i6;
            if (i6 < 0) {
                this.selection = this.possible.size() - 1;
            }
            update();
        }

        public void right() {
            if (LineReaderImpl.this.isSet(LineReader.Option.LIST_ROWS_FIRST)) {
                next();
                return;
            }
            int i = this.selection;
            int i2 = this.lines;
            int i3 = i / i2;
            int i4 = i % i2;
            int i5 = i3 + 1;
            if (i5 >= this.columns) {
                i4++;
                i5 = 0;
            }
            int i6 = (i5 * i2) + i4;
            this.selection = i6;
            if (i6 >= this.possible.size()) {
                this.selection = 0;
            }
            update();
        }

        public void up() {
            int i;
            if (LineReaderImpl.this.isSet(LineReader.Option.LIST_ROWS_FIRST)) {
                int i2 = this.selection;
                int i3 = this.columns;
                int i4 = i2 / i3;
                int i5 = i2 % i3;
                if (i4 > 0) {
                    i = i4 - 1;
                } else {
                    i5 = ((i5 + i3) - 1) % i3;
                    int i6 = this.lines;
                    int i7 = i6 - 1;
                    i = (i3 * i7) + i5 >= this.possible.size() ? i6 - 2 : i7;
                }
                this.selection = (i * this.columns) + i5;
                update();
                return;
            }
            previous();
        }

        private void update() {
            LineReaderImpl.this.buf.backspace(this.word.length());
            this.word = completion().value();
            LineReaderImpl.this.buf.write(this.word);
            PostResult postResultComputePost = LineReaderImpl.this.computePost(this.possible, completion(), null, this.completed);
            LineReaderImpl lineReaderImpl = LineReaderImpl.this;
            int size = lineReaderImpl.insertSecondaryPrompts(AttributedStringBuilder.append(lineReaderImpl.prompt, LineReaderImpl.this.buf.toString()), new ArrayList()).columnSplitLength(LineReaderImpl.this.size.getColumns(), false, LineReaderImpl.this.display.delayLineWrap()).size();
            if (postResultComputePost.lines > LineReaderImpl.this.size.getRows() - size) {
                int rows = (LineReaderImpl.this.size.getRows() - size) - 1;
                if (postResultComputePost.selectedLine >= 0) {
                    if (postResultComputePost.selectedLine < this.topLine) {
                        this.topLine = postResultComputePost.selectedLine;
                    } else if (postResultComputePost.selectedLine >= this.topLine + rows) {
                        this.topLine = (postResultComputePost.selectedLine - rows) + 1;
                    }
                }
                AttributedString attributedString = postResultComputePost.post;
                if (attributedString.length() > 0 && attributedString.charAt(attributedString.length() - 1) != '\n') {
                    attributedString = new AttributedStringBuilder(attributedString.length() + 1).append(attributedString).append((CharSequence) "\n").toAttributedString();
                }
                List<AttributedString> listColumnSplitLength = attributedString.columnSplitLength(LineReaderImpl.this.size.getColumns(), true, LineReaderImpl.this.display.delayLineWrap());
                int i = this.topLine;
                ArrayList arrayList = new ArrayList(listColumnSplitLength.subList(i, i + rows));
                arrayList.add(new AttributedStringBuilder().style(AttributedStyle.DEFAULT.foreground(6)).append((CharSequence) "rows ").append((CharSequence) Integer.toString(this.topLine + 1)).append((CharSequence) " to ").append((CharSequence) Integer.toString(this.topLine + rows)).append((CharSequence) " of ").append((CharSequence) Integer.toString(listColumnSplitLength.size())).append((CharSequence) "\n").style(AttributedStyle.DEFAULT).toAttributedString());
                this.computed = AttributedString.join(AttributedString.EMPTY, arrayList);
            } else {
                this.computed = postResultComputePost.post;
            }
            this.lines = postResultComputePost.lines;
            int size2 = this.possible.size();
            int i2 = this.lines;
            this.columns = ((size2 + i2) - 1) / i2;
        }

        @Override // java.util.function.Supplier
        public AttributedString get() {
            return this.computed;
        }
    }

    protected boolean doMenu(List<Candidate> list, String str) {
        ArrayList arrayList = new ArrayList();
        mergeCandidates(list);
        computePost(list, null, arrayList, str);
        MenuSupport menuSupport = new MenuSupport(list, str);
        this.post = menuSupport;
        redisplay();
        KeyMap<Binding> keyMap = this.keyMaps.get(LineReader.MENU);
        while (true) {
            Binding binding = readBinding(getKeys(), keyMap);
            if (binding == null) {
                return false;
            }
            String strName = binding instanceof Reference ? ((Reference) binding).name() : "";
            strName.hashCode();
            switch (strName) {
                case "clear-screen":
                    clearScreen();
                    break;
                case "reverse-menu-complete":
                    menuSupport.previous();
                    break;
                case "forward-char":
                    menuSupport.right();
                    break;
                case "down-line-or-history":
                    menuSupport.down();
                    break;
                case "menu-complete":
                    menuSupport.next();
                    break;
                case "backward-char":
                    menuSupport.left();
                    break;
                case "up-line-or-history":
                    menuSupport.up();
                    break;
                default:
                    Candidate candidateCompletion = menuSupport.completion();
                    if (candidateCompletion.suffix() != null) {
                        String string = getString(LineReader.REMOVE_SUFFIX_CHARS, " \t\n;&|");
                        if ((LineReader.SELF_INSERT.equals(strName) && string.indexOf(getLastBinding().charAt(0)) >= 0) || LineReader.BACKWARD_DELETE_CHAR.equals(strName)) {
                            this.buf.backspace(candidateCompletion.suffix().length());
                        }
                    }
                    if (candidateCompletion.complete() && getLastBinding().charAt(0) != ' ' && (LineReader.SELF_INSERT.equals(strName) || getLastBinding().charAt(0) != ' ')) {
                        this.buf.write(32);
                    }
                    if (!LineReader.ACCEPT_LINE.equals(strName) && (!LineReader.SELF_INSERT.equals(strName) || candidateCompletion.suffix() == null || !candidateCompletion.suffix().startsWith(getLastBinding()))) {
                        pushBackBinding(true);
                    }
                    this.post = null;
                    return true;
            }
            redisplay();
        }
    }

    protected boolean doList(final List<Candidate> list, String str, boolean z) {
        mergeCandidates(list);
        int size = insertSecondaryPrompts(AttributedStringBuilder.append(this.prompt, this.buf.toString()), new ArrayList()).columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap()).size();
        final PostResult postResultComputePost = computePost(list, null, null, str);
        final int i = postResultComputePost.lines;
        int i2 = getInt(LineReader.LIST_MAX, 100);
        if ((i2 > 0 && list.size() >= i2) || i >= this.size.getRows() - size) {
            this.post = new Supplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda71
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7373x72f6ae95(list, i);
                }
            };
            redisplay(true);
            int character = readCharacter();
            if (character != 121 && character != 89 && character != 9) {
                this.post = null;
                return false;
            }
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            final String str2 = str + sb.toString();
            final List<Candidate> list2 = sb.length() > 0 ? (List) list.stream().filter(new Predicate() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda72
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((Candidate) obj).value().startsWith(str2);
                }
            }).collect(Collectors.toList()) : list;
            this.post = new Supplier() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda73
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m7374x94624817(list2, str2, postResultComputePost);
                }
            };
            if (!z) {
                return false;
            }
            redisplay();
            Binding binding = (Binding) this.bindingReader.readBinding(getKeys());
            if (binding instanceof Reference) {
                String strName = ((Reference) binding).name();
                if (LineReader.BACKWARD_DELETE_CHAR.equals(strName) || LineReader.VI_BACKWARD_DELETE_CHAR.equals(strName)) {
                    if (sb.length() == 0) {
                        pushBackBinding();
                        this.post = null;
                        return false;
                    }
                    sb.setLength(sb.length() - 1);
                    this.buf.backspace();
                } else if (LineReader.SELF_INSERT.equals(strName)) {
                    sb.append(getLastBinding());
                    this.buf.write(getLastBinding());
                    if (list2.isEmpty()) {
                        this.post = null;
                        return false;
                    }
                } else {
                    if ("\t".equals(getLastBinding())) {
                        if (list2.size() == 1 || sb.length() > 0) {
                            this.post = null;
                            pushBackBinding();
                        } else if (isSet(LineReader.Option.AUTO_MENU)) {
                            this.buf.backspace(str2.length());
                            doMenu(list2, str2);
                        }
                        return false;
                    }
                    pushBackBinding();
                    this.post = null;
                    return false;
                }
            } else if (binding == null) {
                this.post = null;
                return false;
            }
        }
    }

    /* JADX INFO: renamed from: lambda$doList$30$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ AttributedString m7373x72f6ae95(List list, int i) {
        return new AttributedString(getAppName() + ": do you wish to see to see all " + list.size() + " possibilities (" + i + " lines)?");
    }

    /* JADX INFO: renamed from: lambda$doList$32$org-jetbrains-kotlin-org-jline-reader-impl-LineReaderImpl, reason: not valid java name */
    /* synthetic */ AttributedString m7374x94624817(List list, String str, PostResult postResult) {
        int size = insertSecondaryPrompts(AttributedStringBuilder.append(this.prompt, this.buf.toString()), new ArrayList()).columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap()).size();
        PostResult postResultComputePost = computePost(list, null, null, str);
        if (postResultComputePost.lines < this.size.getRows() - size) {
            return postResultComputePost.post;
        }
        this.post = null;
        int iCursor = this.buf.cursor();
        Buffer buffer = this.buf;
        buffer.cursor(buffer.length());
        redisplay(false);
        this.buf.cursor(iCursor);
        println();
        List<AttributedString> listColumnSplitLength = postResult.post.columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap());
        Display display = new Display(this.terminal, false);
        display.resize(this.size.getRows(), this.size.getColumns());
        display.update(listColumnSplitLength, -1);
        redrawLine();
        return new AttributedString("");
    }

    protected static class PostResult {
        final int lines;
        final AttributedString post;
        final int selectedLine;

        public PostResult(AttributedString attributedString, int i, int i2) {
            this.post = attributedString;
            this.lines = i;
            this.selectedLine = i2;
        }
    }

    protected PostResult computePost(List<Candidate> list, Candidate candidate, List<Candidate> list2, String str) {
        List<Object> arrayList = new ArrayList<>();
        if (isSet(LineReader.Option.GROUP)) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Candidate candidate2 : list) {
                String strGroup = candidate2.group();
                if (strGroup == null) {
                    strGroup = "";
                }
                ((TreeMap) linkedHashMap.computeIfAbsent(strGroup, new Function() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda75
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return LineReaderImpl.lambda$computePost$33((String) obj);
                    }
                })).put(candidate2.value(), candidate2);
            }
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                String str2 = (String) entry.getKey();
                if (str2.isEmpty() && linkedHashMap.size() > 1) {
                    str2 = "others";
                }
                if (!str2.isEmpty() && isSet(LineReader.Option.AUTO_GROUP)) {
                    arrayList.add(str2);
                }
                arrayList.add(new ArrayList(((TreeMap) entry.getValue()).values()));
                if (list2 != null) {
                    list2.addAll(((TreeMap) entry.getValue()).values());
                }
            }
        } else {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            TreeMap treeMap = new TreeMap();
            for (Candidate candidate3 : list) {
                String strGroup2 = candidate3.group();
                if (strGroup2 != null) {
                    linkedHashSet.add(strGroup2);
                }
                treeMap.put(candidate3.value(), candidate3);
            }
            if (isSet(LineReader.Option.AUTO_GROUP)) {
                Iterator it = linkedHashSet.iterator();
                while (it.hasNext()) {
                    arrayList.add((String) it.next());
                }
            }
            arrayList.add(new ArrayList(treeMap.values()));
            if (list2 != null) {
                list2.addAll(treeMap.values());
            }
        }
        return toColumns(arrayList, candidate, str);
    }

    static /* synthetic */ TreeMap lambda$computePost$33(String str) {
        return new TreeMap();
    }

    protected PostResult toColumns(List<Object> list, Candidate candidate, String str) {
        int[] iArr = new int[2];
        int columns = this.size.getColumns();
        int iMax = 0;
        for (Object obj : list) {
            if (obj instanceof String) {
                iMax = Math.max(iMax, this.display.wcwidth((String) obj));
            } else if (obj instanceof List) {
                for (Candidate candidate2 : (List) obj) {
                    int iWcwidth = this.display.wcwidth(candidate2.displ());
                    if (candidate2.descr() != null) {
                        iWcwidth = iWcwidth + 1 + "(".length() + this.display.wcwidth(candidate2.descr()) + Constant.AFTER_QUTO.length();
                    }
                    iMax = Math.max(iMax, iWcwidth);
                }
            }
        }
        AttributedStringBuilder attributedStringBuilder = new AttributedStringBuilder();
        Iterator<Object> it = list.iterator();
        while (it.hasNext()) {
            toColumns(it.next(), columns, iMax, attributedStringBuilder, candidate, str, iArr);
        }
        if (attributedStringBuilder.length() > 0 && attributedStringBuilder.charAt(attributedStringBuilder.length() - 1) == '\n') {
            attributedStringBuilder.setLength(attributedStringBuilder.length() - 1);
        }
        return new PostResult(attributedStringBuilder.toAttributedString(), iArr[0], iArr[1]);
    }

    protected void toColumns(Object obj, int i, int i2, AttributedStringBuilder attributedStringBuilder, Candidate candidate, String str, int[] iArr) {
        char c;
        IntBinaryOperator intBinaryOperator;
        int i3;
        char c2;
        char c3;
        int length;
        char c4;
        if (i2 <= 0) {
            return;
        }
        char c5 = 6;
        int i4 = 0;
        int i5 = 1;
        if (obj instanceof String) {
            attributedStringBuilder.style(AttributedStyle.DEFAULT.foreground(6)).append((CharSequence) obj).style(AttributedStyle.DEFAULT).append("\n");
            iArr[0] = iArr[0] + 1;
        } else if (obj instanceof List) {
            List list = (List) obj;
            int iMin = Math.min(i, i2);
            final int i6 = i / iMin;
            while (true) {
                c = 3;
                if (i6 <= 1 || (i6 * iMin) + ((i6 - 1) * 3) < i) {
                    break;
                } else {
                    i6--;
                }
            }
            final int size = ((list.size() + i6) - 1) / i6;
            if (isSet(LineReader.Option.LIST_ROWS_FIRST)) {
                intBinaryOperator = new IntBinaryOperator() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda81
                    @Override // java.util.function.IntBinaryOperator
                    public final int applyAsInt(int i7, int i8) {
                        return LineReaderImpl.lambda$toColumns$34(i6, i7, i8);
                    }
                };
            } else {
                intBinaryOperator = new IntBinaryOperator() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda82
                    @Override // java.util.function.IntBinaryOperator
                    public final int applyAsInt(int i7, int i8) {
                        return LineReaderImpl.lambda$toColumns$35(size, i7, i8);
                    }
                };
            }
            int i7 = 0;
            while (i7 < size) {
                int i8 = i4;
                while (i8 < i6) {
                    int iApplyAsInt = intBinaryOperator.applyAsInt(i7, i8);
                    if (iApplyAsInt < list.size()) {
                        Candidate candidate2 = (Candidate) list.get(iApplyAsInt);
                        int i9 = (i8 >= i6 + (-1) || intBinaryOperator.applyAsInt(i7, i8 + 1) >= list.size()) ? i4 : i5;
                        AttributedString attributedStringFromAnsi = AttributedString.fromAnsi(candidate2.displ());
                        AttributedString attributedStringFromAnsi2 = AttributedString.fromAnsi(candidate2.descr());
                        int iColumnLength = attributedStringFromAnsi.columnLength();
                        if (attributedStringFromAnsi2 != null) {
                            int length2 = iMin - (((iColumnLength + 1) + "(".length()) + Constant.AFTER_QUTO.length());
                            int iColumnLength2 = attributedStringFromAnsi2.columnLength();
                            if (iColumnLength2 > length2) {
                                c4 = 0;
                                i3 = 1;
                                attributedStringFromAnsi2 = AttributedStringBuilder.append(attributedStringFromAnsi2.columnSubSequence(0, length2 - WCWidth.wcwidth(8230)), StringUtil.ELLIPSIS);
                                iColumnLength2 = attributedStringFromAnsi2.columnLength();
                            } else {
                                c4 = 0;
                                i3 = 1;
                            }
                            CharSequence[] charSequenceArr = new CharSequence[3];
                            charSequenceArr[c4] = "(";
                            charSequenceArr[i3] = attributedStringFromAnsi2;
                            charSequenceArr[2] = Constant.AFTER_QUTO;
                            attributedStringFromAnsi2 = AttributedStringBuilder.append(charSequenceArr);
                            length = "(".length() + Constant.AFTER_QUTO.length() + iColumnLength2;
                        } else {
                            i3 = i5;
                            length = 0;
                        }
                        if (candidate2 == candidate) {
                            iArr[i3] = i7;
                            attributedStringBuilder.style(AttributedStyle.INVERSE);
                            if (attributedStringFromAnsi.toString().startsWith(str)) {
                                attributedStringBuilder.append((CharSequence) attributedStringFromAnsi.toString(), 0, str.length());
                                attributedStringBuilder.append((CharSequence) attributedStringFromAnsi.toString(), str.length(), attributedStringFromAnsi.length());
                            } else {
                                attributedStringBuilder.append((CharSequence) attributedStringFromAnsi.toString());
                            }
                            for (int i10 = 0; i10 < (iMin - iColumnLength) - length; i10++) {
                                attributedStringBuilder.append(io.netty.util.internal.StringUtil.SPACE);
                            }
                            if (attributedStringFromAnsi2 != null) {
                                attributedStringBuilder.append(attributedStringFromAnsi2);
                            }
                            attributedStringBuilder.style(AttributedStyle.DEFAULT);
                            c2 = 6;
                        } else {
                            if (attributedStringFromAnsi.toString().startsWith(str)) {
                                c2 = 6;
                                attributedStringBuilder.style(attributedStringBuilder.style().foreground(6));
                                attributedStringBuilder.append(attributedStringFromAnsi, 0, str.length());
                                attributedStringBuilder.style(AttributedStyle.DEFAULT);
                                attributedStringBuilder.append(attributedStringFromAnsi, str.length(), attributedStringFromAnsi.length());
                            } else {
                                c2 = 6;
                                attributedStringBuilder.append(attributedStringFromAnsi);
                            }
                            if (attributedStringFromAnsi2 != null || i9 != 0) {
                                for (int i11 = 0; i11 < (iMin - iColumnLength) - length; i11++) {
                                    attributedStringBuilder.append(io.netty.util.internal.StringUtil.SPACE);
                                }
                            }
                            if (attributedStringFromAnsi2 != null) {
                                attributedStringBuilder.style(AttributedStyle.DEFAULT.foreground(8));
                                attributedStringBuilder.append(attributedStringFromAnsi2);
                                attributedStringBuilder.style(AttributedStyle.DEFAULT);
                            }
                        }
                        if (i9 != 0) {
                            c3 = 3;
                            for (int i12 = 0; i12 < 3; i12++) {
                                attributedStringBuilder.append(io.netty.util.internal.StringUtil.SPACE);
                            }
                        } else {
                            c3 = 3;
                        }
                    } else {
                        list = list;
                        i3 = i5;
                        i6 = i6;
                        c2 = c5;
                        c3 = c;
                    }
                    i8++;
                    c = c3;
                    c5 = c2;
                    i5 = i3;
                    list = list;
                    i6 = i6;
                    i4 = 0;
                }
                attributedStringBuilder.append('\n');
                i7++;
                c5 = c5;
                i5 = i5;
                list = list;
                i6 = i6;
                i4 = 0;
            }
            int i13 = i4;
            iArr[i13] = iArr[i13] + size;
        }
    }

    private String getCommonStart(String str, String str2, boolean z) {
        int[] array = str.codePoints().toArray();
        int[] array2 = str2.codePoints().toArray();
        int i = 0;
        while (i < Math.min(array.length, array2.length)) {
            int upperCase = array[i];
            int upperCase2 = array2[i];
            if (upperCase != upperCase2 && z && (upperCase = Character.toUpperCase(upperCase)) != (upperCase2 = Character.toUpperCase(upperCase2))) {
                upperCase = Character.toLowerCase(upperCase);
                upperCase2 = Character.toLowerCase(upperCase2);
            }
            if (upperCase != upperCase2) {
                break;
            }
            i++;
        }
        return new String(array, 0, i);
    }

    protected boolean moveHistory(boolean z) {
        String strCurrent;
        if (!this.buf.toString().equals(this.history.current())) {
            this.modifiedHistory.put(Integer.valueOf(this.history.index()), this.buf.toString());
        }
        if (z && !this.history.next()) {
            return false;
        }
        if (!z && !this.history.previous()) {
            return false;
        }
        if (this.modifiedHistory.containsKey(Integer.valueOf(this.history.index()))) {
            strCurrent = this.modifiedHistory.get(Integer.valueOf(this.history.index()));
        } else {
            strCurrent = this.history.current();
        }
        setBuffer(strCurrent);
        return true;
    }

    void print(String str) {
        this.terminal.writer().write(str);
    }

    void println() {
        this.terminal.puts(InfoCmp.Capability.carriage_return, new Object[0]);
        print("\n");
        redrawLine();
    }

    protected boolean killBuffer() {
        this.killRing.add(this.buf.toString());
        this.buf.clear();
        return true;
    }

    protected boolean killWholeLine() {
        int iCursor;
        int iCursor2;
        if (this.buf.length() == 0) {
            return false;
        }
        if (this.count < 0) {
            iCursor2 = this.buf.cursor();
            while (this.buf.atChar(iCursor2) != 0 && this.buf.atChar(iCursor2) != 10) {
                iCursor2++;
            }
            iCursor = iCursor2;
            for (int i = -this.count; i > 0; i--) {
                while (iCursor > 0 && this.buf.atChar(iCursor - 1) != 10) {
                    iCursor--;
                }
                iCursor--;
            }
        } else {
            iCursor = this.buf.cursor();
            while (iCursor > 0 && this.buf.atChar(iCursor - 1) != 10) {
                iCursor--;
            }
            iCursor2 = iCursor;
            while (true) {
                int i2 = this.count;
                this.count = i2 - 1;
                if (i2 <= 0) {
                    break;
                }
                while (iCursor2 < this.buf.length() && this.buf.atChar(iCursor2) != 10) {
                    iCursor2++;
                }
                iCursor2++;
            }
        }
        String strSubstring = this.buf.substring(iCursor, iCursor2);
        this.buf.cursor(iCursor);
        this.buf.delete(iCursor2 - iCursor);
        this.killRing.add(strSubstring);
        return true;
    }

    public boolean killLine() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda20(this));
        }
        if (this.buf.cursor() == this.buf.length()) {
            return false;
        }
        int iCursor = this.buf.cursor();
        int i = iCursor;
        while (true) {
            int i2 = this.count;
            this.count = i2 - 1;
            if (i2 > 0) {
                if (this.buf.atChar(i) == 10) {
                    i++;
                } else {
                    while (this.buf.atChar(i) != 0 && this.buf.atChar(i) != 10) {
                        i++;
                    }
                }
            } else {
                int i3 = i - iCursor;
                String strSubstring = this.buf.substring(iCursor, iCursor + i3);
                this.buf.delete(i3);
                this.killRing.add(strSubstring);
                return true;
            }
        }
    }

    public boolean backwardKillLine() {
        if (this.count < 0) {
            return callNeg(new LineReaderImpl$$ExternalSyntheticLambda98(this));
        }
        if (this.buf.cursor() == 0) {
            return false;
        }
        int iCursor = this.buf.cursor();
        int i = iCursor;
        while (true) {
            int i2 = this.count;
            this.count = i2 - 1;
            if (i2 <= 0 || i == 0) {
                break;
            }
            if (this.buf.atChar(i - 1) == 10) {
                i--;
            } else {
                while (i > 0) {
                    int i3 = i - 1;
                    if (this.buf.atChar(i3) == 0 || this.buf.atChar(i3) == 10) {
                        break;
                    }
                    i--;
                }
            }
        }
        int i4 = iCursor - i;
        String strSubstring = this.buf.substring(i4, iCursor);
        this.buf.cursor(i);
        this.buf.delete(i4);
        this.killRing.add(strSubstring);
        return true;
    }

    public boolean killRegion() {
        return doCopyKillRegion(true);
    }

    public boolean copyRegionAsKill() {
        return doCopyKillRegion(false);
    }

    private boolean doCopyKillRegion(boolean z) {
        if (this.regionMark > this.buf.length()) {
            this.regionMark = this.buf.length();
        }
        if (this.regionActive == LineReader.RegionType.LINE) {
            int i = this.regionMark;
            int iCursor = this.buf.cursor();
            if (i < iCursor) {
                while (i > 0 && this.buf.atChar(i - 1) != 10) {
                    i--;
                }
                while (iCursor < this.buf.length() - 1) {
                    int i2 = iCursor + 1;
                    if (this.buf.atChar(i2) == 10) {
                        break;
                    }
                    iCursor = i2;
                }
                if (isInViCmdMode()) {
                    iCursor++;
                }
                this.killRing.add(this.buf.substring(i, iCursor));
                if (z) {
                    this.buf.backspace(iCursor - i);
                }
            } else {
                while (iCursor > 0 && this.buf.atChar(iCursor - 1) != 10) {
                    iCursor--;
                }
                while (i < this.buf.length() && this.buf.atChar(i) != 10) {
                    i++;
                }
                if (isInViCmdMode()) {
                    i++;
                }
                this.killRing.addBackwards(this.buf.substring(iCursor, i));
                if (z) {
                    this.buf.cursor(iCursor);
                    this.buf.delete(i - iCursor);
                }
            }
        } else if (this.regionMark > this.buf.cursor()) {
            if (isInViCmdMode()) {
                this.regionMark++;
            }
            KillRing killRing = this.killRing;
            Buffer buffer = this.buf;
            killRing.add(buffer.substring(buffer.cursor(), this.regionMark));
            if (z) {
                Buffer buffer2 = this.buf;
                buffer2.delete(this.regionMark - buffer2.cursor());
            }
        } else {
            if (isInViCmdMode()) {
                this.buf.move(1);
            }
            KillRing killRing2 = this.killRing;
            Buffer buffer3 = this.buf;
            killRing2.add(buffer3.substring(this.regionMark, buffer3.cursor()));
            if (z) {
                Buffer buffer4 = this.buf;
                buffer4.backspace(buffer4.cursor() - this.regionMark);
            }
        }
        if (z) {
            this.regionActive = LineReader.RegionType.NONE;
        }
        return true;
    }

    public boolean yank() {
        String strYank = this.killRing.yank();
        if (strYank == null) {
            return false;
        }
        putString(strYank);
        return true;
    }

    public boolean yankPop() {
        String strYank;
        if (!this.killRing.lastYank() || (strYank = this.killRing.yank()) == null) {
            return false;
        }
        this.buf.backspace(strYank.length());
        String strYankPop = this.killRing.yankPop();
        if (strYankPop == null) {
            return false;
        }
        putString(strYankPop);
        return true;
    }

    public boolean mouse() {
        MouseEvent mouseEvent = readMouseEvent();
        if (mouseEvent.getType() == MouseEvent.Type.Released && mouseEvent.getButton() == MouseEvent.Button.Button1) {
            final StringBuilder sb = new StringBuilder();
            Cursor cursorPosition = this.terminal.getCursorPosition(new IntConsumer() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda90
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    sb.append((char) i);
                }
            });
            this.bindingReader.runMacro(sb.toString());
            ArrayList arrayList = new ArrayList();
            getDisplayedBufferWithPrompts(arrayList);
            AttributedStringBuilder attributedStringBuilderTabs = new AttributedStringBuilder().tabs(4);
            attributedStringBuilderTabs.append(this.prompt);
            attributedStringBuilderTabs.append(insertSecondaryPrompts(new AttributedString(this.buf.upToCursor()), arrayList, false));
            int size = attributedStringBuilderTabs.columnSplitLength(this.size.getColumns(), false, this.display.delayLineWrap()).size();
            int i = size - 1;
            int iMax = Math.max(0, Math.min((mouseEvent.getY() + i) - cursorPosition.getY(), arrayList.size()));
            this.buf.moveXY((mouseEvent.getX() - cursorPosition.getX()) - ((iMax == 0 ? this.prompt : arrayList.get(iMax - 1)).columnLength() - (i == 0 ? this.prompt : arrayList.get(size - 2)).columnLength()), mouseEvent.getY() - cursorPosition.getY());
        }
        return true;
    }

    public boolean clear() {
        this.display.update(Collections.emptyList(), 0);
        return true;
    }

    public boolean clearScreen() {
        if (this.terminal.puts(InfoCmp.Capability.clear_screen, new Object[0])) {
            redrawLine();
            return true;
        }
        println();
        return true;
    }

    public boolean beep() {
        BellType bellType = BellType.AUDIBLE;
        String lowerCase = getString(LineReader.BELL_STYLE, "").toLowerCase();
        lowerCase.hashCode();
        switch (lowerCase) {
            case "audible":
                bellType = BellType.AUDIBLE;
                break;
            case "on":
                if (!getBoolean(LineReader.PREFER_VISIBLE_BELL, false)) {
                    bellType = BellType.AUDIBLE;
                    break;
                } else {
                    bellType = BellType.VISIBLE;
                    break;
                }
                break;
            case "off":
            case "none":
                bellType = BellType.NONE;
                break;
            case "visible":
                bellType = BellType.VISIBLE;
                break;
        }
        if (bellType == BellType.VISIBLE) {
            if (this.terminal.puts(InfoCmp.Capability.flash_screen, new Object[0]) || this.terminal.puts(InfoCmp.Capability.bell, new Object[0])) {
                flush();
            }
        } else if (bellType == BellType.AUDIBLE && this.terminal.puts(InfoCmp.Capability.bell, new Object[0])) {
            flush();
        }
        return true;
    }

    protected boolean isDelimiter(int i) {
        return !Character.isLetterOrDigit(i);
    }

    protected boolean isWhitespace(int i) {
        return Character.isWhitespace(i);
    }

    protected boolean isViAlphaNum(int i) {
        return i == 95 || Character.isLetterOrDigit(i);
    }

    protected boolean isAlpha(int i) {
        return Character.isLetter(i);
    }

    protected boolean isWord(int i) {
        return Character.isLetterOrDigit(i) || (i < 128 && getString(LineReader.WORDCHARS, "*?_-.[]~=/&;!#$%^(){}<>").indexOf((char) i) >= 0);
    }

    String getString(String str, String str2) {
        return ReaderUtils.getString(this, str, str2);
    }

    boolean getBoolean(String str, boolean z) {
        return ReaderUtils.getBoolean(this, str, z);
    }

    int getInt(String str, int i) {
        return ReaderUtils.getInt(this, str, i);
    }

    long getLong(String str, long j) {
        return ReaderUtils.getLong(this, str, j);
    }

    @Override // org.jetbrains.kotlin.org.jline.reader.LineReader
    public Map<String, KeyMap<Binding>> defaultKeyMaps() {
        HashMap map = new HashMap();
        map.put(LineReader.EMACS, emacs());
        map.put(LineReader.VICMD, viCmd());
        map.put(LineReader.VIINS, viInsertion());
        map.put(LineReader.MENU, menu());
        map.put(LineReader.VIOPP, viOpp());
        map.put(LineReader.VISUAL, visual());
        map.put(LineReader.SAFE, safe());
        if (getBoolean(LineReader.BIND_TTY_SPECIAL_CHARS, true)) {
            Attributes attributes = this.terminal.getAttributes();
            bindConsoleChars((KeyMap) map.get(LineReader.EMACS), attributes);
            bindConsoleChars((KeyMap) map.get(LineReader.VIINS), attributes);
        }
        for (KeyMap keyMap : map.values()) {
            keyMap.setUnicode(new Reference(LineReader.SELF_INSERT));
            keyMap.setAmbiguousTimeout(getLong(LineReader.AMBIGUOUS_BINDING, 1000L));
        }
        map.put("main", map.get(LineReader.EMACS));
        return map;
    }

    public KeyMap<Binding> emacs() {
        KeyMap<Binding> keyMap = new KeyMap<>();
        bind(keyMap, LineReader.SET_MARK_COMMAND, KeyMap.ctrl('@'));
        bind(keyMap, LineReader.BEGINNING_OF_LINE, KeyMap.ctrl('A'));
        bind(keyMap, LineReader.BACKWARD_CHAR, KeyMap.ctrl(Util.C_BYTE));
        bind(keyMap, LineReader.DELETE_CHAR_OR_LIST, KeyMap.ctrl(Util.C_DOUBLE));
        bind(keyMap, LineReader.END_OF_LINE, KeyMap.ctrl('E'));
        bind(keyMap, LineReader.FORWARD_CHAR, KeyMap.ctrl(Util.C_FLOAT));
        bind(keyMap, LineReader.SEND_BREAK, KeyMap.ctrl('G'));
        bind(keyMap, LineReader.BACKWARD_DELETE_CHAR, KeyMap.ctrl('H'));
        bind(keyMap, LineReader.EXPAND_OR_COMPLETE, KeyMap.ctrl('I'));
        bind(keyMap, LineReader.ACCEPT_LINE, KeyMap.ctrl(Util.C_LONG));
        bind(keyMap, LineReader.KILL_LINE, KeyMap.ctrl('K'));
        bind(keyMap, LineReader.CLEAR_SCREEN, KeyMap.ctrl('L'));
        bind(keyMap, LineReader.ACCEPT_LINE, KeyMap.ctrl('M'));
        bind(keyMap, LineReader.DOWN_LINE_OR_HISTORY, KeyMap.ctrl('N'));
        bind(keyMap, LineReader.UP_LINE_OR_HISTORY, KeyMap.ctrl('P'));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_BACKWARD, KeyMap.ctrl(Matrix.MATRIX_TYPE_RANDOM_REGULAR));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_FORWARD, KeyMap.ctrl(Util.C_SHORT));
        bind(keyMap, LineReader.TRANSPOSE_CHARS, KeyMap.ctrl(Util.C_TYPE_VARIABLE));
        bind(keyMap, LineReader.KILL_WHOLE_LINE, KeyMap.ctrl(Matrix.MATRIX_TYPE_RANDOM_UT));
        bind(keyMap, LineReader.QUOTED_INSERT, KeyMap.ctrl(Util.C_VOID));
        bind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.ctrl('W'));
        bind(keyMap, LineReader.YANK, KeyMap.ctrl('Y'));
        bind(keyMap, LineReader.CHARACTER_SEARCH, KeyMap.ctrl(']'));
        bind(keyMap, LineReader.UNDO, KeyMap.ctrl('_'));
        bind(keyMap, LineReader.SELF_INSERT, KeyMap.range(" -~"));
        bind(keyMap, LineReader.INSERT_CLOSE_PAREN, Constant.AFTER_QUTO);
        bind(keyMap, LineReader.INSERT_CLOSE_SQUARE, "]");
        bind(keyMap, LineReader.INSERT_CLOSE_CURLY, "}");
        bind(keyMap, LineReader.BACKWARD_DELETE_CHAR, KeyMap.del());
        bind(keyMap, LineReader.VI_MATCH_BRACKET, KeyMap.translate("^X^B"));
        bind(keyMap, LineReader.SEND_BREAK, KeyMap.translate("^X^G"));
        bind(keyMap, LineReader.VI_FIND_NEXT_CHAR, KeyMap.translate("^X^F"));
        bind(keyMap, LineReader.VI_JOIN, KeyMap.translate("^X^J"));
        bind(keyMap, LineReader.KILL_BUFFER, KeyMap.translate("^X^K"));
        bind(keyMap, LineReader.INFER_NEXT_HISTORY, KeyMap.translate("^X^N"));
        bind(keyMap, LineReader.OVERWRITE_MODE, KeyMap.translate("^X^O"));
        bind(keyMap, LineReader.REDO, KeyMap.translate("^X^R"));
        bind(keyMap, LineReader.UNDO, KeyMap.translate("^X^U"));
        bind(keyMap, LineReader.VI_CMD_MODE, KeyMap.translate("^X^V"));
        bind(keyMap, LineReader.EXCHANGE_POINT_AND_MARK, KeyMap.translate("^X^X"));
        bind(keyMap, LineReader.DO_LOWERCASE_VERSION, KeyMap.translate("^XA-^XZ"));
        bind(keyMap, LineReader.WHAT_CURSOR_POSITION, KeyMap.translate("^X="));
        bind(keyMap, LineReader.KILL_LINE, KeyMap.translate("^X^?"));
        bind(keyMap, LineReader.SEND_BREAK, KeyMap.alt(KeyMap.ctrl('G')));
        bind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.alt(KeyMap.ctrl('H')));
        bind(keyMap, LineReader.SELF_INSERT_UNMETA, KeyMap.alt(KeyMap.ctrl('M')));
        bind(keyMap, LineReader.COMPLETE_WORD, KeyMap.alt(KeyMap.esc()));
        bind(keyMap, LineReader.CHARACTER_SEARCH_BACKWARD, KeyMap.alt(KeyMap.ctrl(']')));
        bind(keyMap, LineReader.COPY_PREV_WORD, KeyMap.alt(KeyMap.ctrl('_')));
        bind(keyMap, LineReader.SET_MARK_COMMAND, KeyMap.alt(io.netty.util.internal.StringUtil.SPACE));
        bind(keyMap, LineReader.NEG_ARGUMENT, KeyMap.alt('-'));
        bind(keyMap, LineReader.DIGIT_ARGUMENT, KeyMap.range("\\E0-\\E9"));
        bind(keyMap, LineReader.BEGINNING_OF_HISTORY, KeyMap.alt('<'));
        bind(keyMap, LineReader.LIST_CHOICES, KeyMap.alt('='));
        bind(keyMap, LineReader.END_OF_HISTORY, KeyMap.alt('>'));
        bind(keyMap, LineReader.LIST_CHOICES, KeyMap.alt(MangleConstant.Q_MARK));
        bind(keyMap, LineReader.DO_LOWERCASE_VERSION, KeyMap.range("^[A-^[Z"));
        bind(keyMap, LineReader.BACKWARD_WORD, KeyMap.alt('b'));
        bind(keyMap, LineReader.CAPITALIZE_WORD, KeyMap.alt('c'));
        bind(keyMap, LineReader.KILL_WORD, KeyMap.alt(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL));
        bind(keyMap, LineReader.KILL_WORD, KeyMap.translate("^[[3;5~"));
        bind(keyMap, LineReader.FORWARD_WORD, KeyMap.alt('f'));
        bind(keyMap, LineReader.DOWN_CASE_WORD, KeyMap.alt('l'));
        bind(keyMap, LineReader.HISTORY_SEARCH_FORWARD, KeyMap.alt('n'));
        bind(keyMap, LineReader.HISTORY_SEARCH_BACKWARD, KeyMap.alt(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL));
        bind(keyMap, LineReader.TRANSPOSE_WORDS, KeyMap.alt(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL));
        bind(keyMap, LineReader.UP_CASE_WORD, KeyMap.alt('u'));
        bind(keyMap, LineReader.YANK_POP, KeyMap.alt('y'));
        bind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.alt(KeyMap.del()));
        bindArrowKeys(keyMap);
        bind(keyMap, LineReader.FORWARD_WORD, KeyMap.translate("^[[1;5C"));
        bind(keyMap, LineReader.BACKWARD_WORD, KeyMap.translate("^[[1;5D"));
        bind(keyMap, LineReader.FORWARD_WORD, KeyMap.alt(key(InfoCmp.Capability.key_right)));
        bind(keyMap, LineReader.BACKWARD_WORD, KeyMap.alt(key(InfoCmp.Capability.key_left)));
        bind(keyMap, LineReader.FORWARD_WORD, KeyMap.alt(KeyMap.translate("^[[C")));
        bind(keyMap, LineReader.BACKWARD_WORD, KeyMap.alt(KeyMap.translate("^[[D")));
        return keyMap;
    }

    public KeyMap<Binding> viInsertion() {
        KeyMap<Binding> keyMap = new KeyMap<>();
        bind(keyMap, LineReader.SELF_INSERT, KeyMap.range("^@-^_"));
        bind(keyMap, LineReader.LIST_CHOICES, KeyMap.ctrl(Util.C_DOUBLE));
        bind(keyMap, LineReader.SEND_BREAK, KeyMap.ctrl('G'));
        bind(keyMap, LineReader.BACKWARD_DELETE_CHAR, KeyMap.ctrl('H'));
        bind(keyMap, LineReader.EXPAND_OR_COMPLETE, KeyMap.ctrl('I'));
        bind(keyMap, LineReader.ACCEPT_LINE, KeyMap.ctrl(Util.C_LONG));
        bind(keyMap, LineReader.CLEAR_SCREEN, KeyMap.ctrl('L'));
        bind(keyMap, LineReader.ACCEPT_LINE, KeyMap.ctrl('M'));
        bind(keyMap, LineReader.MENU_COMPLETE, KeyMap.ctrl('N'));
        bind(keyMap, LineReader.REVERSE_MENU_COMPLETE, KeyMap.ctrl('P'));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_BACKWARD, KeyMap.ctrl(Matrix.MATRIX_TYPE_RANDOM_REGULAR));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_FORWARD, KeyMap.ctrl(Util.C_SHORT));
        bind(keyMap, LineReader.TRANSPOSE_CHARS, KeyMap.ctrl(Util.C_TYPE_VARIABLE));
        bind(keyMap, LineReader.KILL_WHOLE_LINE, KeyMap.ctrl(Matrix.MATRIX_TYPE_RANDOM_UT));
        bind(keyMap, LineReader.QUOTED_INSERT, KeyMap.ctrl(Util.C_VOID));
        bind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.ctrl('W'));
        bind(keyMap, LineReader.YANK, KeyMap.ctrl('Y'));
        bind(keyMap, LineReader.VI_CMD_MODE, KeyMap.ctrl('['));
        bind(keyMap, LineReader.UNDO, KeyMap.ctrl('_'));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_BACKWARD, KeyMap.ctrl('X') + "r");
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_FORWARD, KeyMap.ctrl('X') + "s");
        bind(keyMap, LineReader.SELF_INSERT, KeyMap.range(" -~"));
        bind(keyMap, LineReader.INSERT_CLOSE_PAREN, Constant.AFTER_QUTO);
        bind(keyMap, LineReader.INSERT_CLOSE_SQUARE, "]");
        bind(keyMap, LineReader.INSERT_CLOSE_CURLY, "}");
        bind(keyMap, LineReader.BACKWARD_DELETE_CHAR, KeyMap.del());
        bindArrowKeys(keyMap);
        return keyMap;
    }

    public KeyMap<Binding> viCmd() {
        KeyMap<Binding> keyMap = new KeyMap<>();
        bind(keyMap, LineReader.LIST_CHOICES, KeyMap.ctrl(Util.C_DOUBLE));
        bind(keyMap, LineReader.EMACS_EDITING_MODE, KeyMap.ctrl('E'));
        bind(keyMap, LineReader.SEND_BREAK, KeyMap.ctrl('G'));
        bind(keyMap, LineReader.VI_BACKWARD_CHAR, KeyMap.ctrl('H'));
        bind(keyMap, LineReader.ACCEPT_LINE, KeyMap.ctrl(Util.C_LONG));
        bind(keyMap, LineReader.KILL_LINE, KeyMap.ctrl('K'));
        bind(keyMap, LineReader.CLEAR_SCREEN, KeyMap.ctrl('L'));
        bind(keyMap, LineReader.ACCEPT_LINE, KeyMap.ctrl('M'));
        bind(keyMap, LineReader.VI_DOWN_LINE_OR_HISTORY, KeyMap.ctrl('N'));
        bind(keyMap, LineReader.VI_UP_LINE_OR_HISTORY, KeyMap.ctrl('P'));
        bind(keyMap, LineReader.QUOTED_INSERT, KeyMap.ctrl(Util.C_UNRESOLVED));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_BACKWARD, KeyMap.ctrl(Matrix.MATRIX_TYPE_RANDOM_REGULAR));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_FORWARD, KeyMap.ctrl(Util.C_SHORT));
        bind(keyMap, LineReader.TRANSPOSE_CHARS, KeyMap.ctrl(Util.C_TYPE_VARIABLE));
        bind(keyMap, LineReader.KILL_WHOLE_LINE, KeyMap.ctrl(Matrix.MATRIX_TYPE_RANDOM_UT));
        bind(keyMap, LineReader.QUOTED_INSERT, KeyMap.ctrl(Util.C_VOID));
        bind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.ctrl('W'));
        bind(keyMap, LineReader.YANK, KeyMap.ctrl('Y'));
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_BACKWARD, KeyMap.ctrl('X') + "r");
        bind(keyMap, LineReader.HISTORY_INCREMENTAL_SEARCH_FORWARD, KeyMap.ctrl('X') + "s");
        bind(keyMap, LineReader.SEND_BREAK, KeyMap.alt(KeyMap.ctrl('G')));
        bind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.alt(KeyMap.ctrl('H')));
        bind(keyMap, LineReader.SELF_INSERT_UNMETA, KeyMap.alt(KeyMap.ctrl('M')));
        bind(keyMap, LineReader.COMPLETE_WORD, KeyMap.alt(KeyMap.esc()));
        bind(keyMap, LineReader.CHARACTER_SEARCH_BACKWARD, KeyMap.alt(KeyMap.ctrl(']')));
        bind(keyMap, LineReader.SET_MARK_COMMAND, KeyMap.alt(io.netty.util.internal.StringUtil.SPACE));
        bind(keyMap, LineReader.DIGIT_ARGUMENT, KeyMap.alt('-'));
        bind(keyMap, LineReader.BEGINNING_OF_HISTORY, KeyMap.alt('<'));
        bind(keyMap, LineReader.LIST_CHOICES, KeyMap.alt('='));
        bind(keyMap, LineReader.END_OF_HISTORY, KeyMap.alt('>'));
        bind(keyMap, LineReader.LIST_CHOICES, KeyMap.alt(MangleConstant.Q_MARK));
        bind(keyMap, LineReader.DO_LOWERCASE_VERSION, KeyMap.range("^[A-^[Z"));
        bind(keyMap, LineReader.BACKWARD_WORD, KeyMap.alt('b'));
        bind(keyMap, LineReader.CAPITALIZE_WORD, KeyMap.alt('c'));
        bind(keyMap, LineReader.KILL_WORD, KeyMap.alt(Advice.OffsetMapping.ForOrigin.Renderer.ForDescriptor.SYMBOL));
        bind(keyMap, LineReader.FORWARD_WORD, KeyMap.alt('f'));
        bind(keyMap, LineReader.DOWN_CASE_WORD, KeyMap.alt('l'));
        bind(keyMap, LineReader.HISTORY_SEARCH_FORWARD, KeyMap.alt('n'));
        bind(keyMap, LineReader.HISTORY_SEARCH_BACKWARD, KeyMap.alt(Advice.OffsetMapping.ForOrigin.Renderer.ForPropertyName.SYMBOL));
        bind(keyMap, LineReader.TRANSPOSE_WORDS, KeyMap.alt(Advice.OffsetMapping.ForOrigin.Renderer.ForTypeName.SYMBOL));
        bind(keyMap, LineReader.UP_CASE_WORD, KeyMap.alt('u'));
        bind(keyMap, LineReader.YANK_POP, KeyMap.alt('y'));
        bind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.alt(KeyMap.del()));
        bind(keyMap, LineReader.FORWARD_CHAR, " ");
        bind(keyMap, LineReader.VI_INSERT_COMMENT, "#");
        bind(keyMap, LineReader.END_OF_LINE, "$");
        bind(keyMap, LineReader.VI_MATCH_BRACKET, "%");
        bind(keyMap, LineReader.VI_DOWN_LINE_OR_HISTORY, Marker.ANY_NON_NULL_MARKER);
        bind(keyMap, LineReader.VI_REV_REPEAT_FIND, ",");
        bind(keyMap, LineReader.VI_UP_LINE_OR_HISTORY, Parameters.DEFAULT_OPTION_PREFIXES);
        bind(keyMap, LineReader.VI_REPEAT_CHANGE, ".");
        bind(keyMap, LineReader.VI_HISTORY_SEARCH_BACKWARD, "/");
        bind(keyMap, LineReader.VI_DIGIT_OR_BEGINNING_OF_LINE, "0");
        bind(keyMap, LineReader.DIGIT_ARGUMENT, KeyMap.range("1-9"));
        bind(keyMap, LineReader.VI_REPEAT_FIND, ";");
        bind(keyMap, LineReader.LIST_CHOICES, ContainerUtils.KEY_VALUE_DELIMITER);
        bind(keyMap, LineReader.VI_HISTORY_SEARCH_FORWARD, TypeDescription.Generic.OfWildcardType.SYMBOL);
        bind(keyMap, LineReader.VI_ADD_EOL, "A");
        bind(keyMap, LineReader.VI_BACKWARD_BLANK_WORD, "B");
        bind(keyMap, LineReader.VI_CHANGE_EOL, "C");
        bind(keyMap, LineReader.VI_KILL_EOL, "D");
        bind(keyMap, LineReader.VI_FORWARD_BLANK_WORD_END, ExifInterface.LONGITUDE_EAST);
        bind(keyMap, LineReader.VI_FIND_PREV_CHAR, "F");
        bind(keyMap, LineReader.VI_FETCH_HISTORY, "G");
        bind(keyMap, LineReader.VI_INSERT_BOL, "I");
        bind(keyMap, LineReader.VI_JOIN, "J");
        bind(keyMap, LineReader.VI_REV_REPEAT_SEARCH, "N");
        bind(keyMap, LineReader.VI_OPEN_LINE_ABOVE, "O");
        bind(keyMap, LineReader.VI_PUT_BEFORE, "P");
        bind(keyMap, LineReader.VI_REPLACE, "R");
        bind(keyMap, LineReader.VI_KILL_LINE, ExifInterface.LATITUDE_SOUTH);
        bind(keyMap, LineReader.VI_FIND_PREV_CHAR_SKIP, ExifInterface.GPS_DIRECTION_TRUE);
        bind(keyMap, LineReader.REDO, "U");
        bind(keyMap, LineReader.VISUAL_LINE_MODE, ExifInterface.GPS_MEASUREMENT_INTERRUPTED);
        bind(keyMap, LineReader.VI_FORWARD_BLANK_WORD, ExifInterface.LONGITUDE_WEST);
        bind(keyMap, LineReader.VI_BACKWARD_DELETE_CHAR, SpriteActivity.EXTRA_X_TRANSFORM);
        bind(keyMap, LineReader.VI_YANK_WHOLE_LINE, SpriteActivity.EXTRA_Y_TRANSFORM);
        bind(keyMap, LineReader.VI_FIRST_NON_BLANK, "^");
        bind(keyMap, LineReader.VI_ADD_NEXT, "a");
        bind(keyMap, LineReader.VI_BACKWARD_WORD, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME);
        bind(keyMap, LineReader.VI_CHANGE, "c");
        bind(keyMap, LineReader.VI_DELETE, "d");
        bind(keyMap, LineReader.VI_FORWARD_WORD_END, "e");
        bind(keyMap, LineReader.VI_FIND_NEXT_CHAR, "f");
        bind(keyMap, LineReader.WHAT_CURSOR_POSITION, "ga");
        bind(keyMap, LineReader.VI_BACKWARD_BLANK_WORD_END, "gE");
        bind(keyMap, LineReader.VI_BACKWARD_WORD_END, "ge");
        bind(keyMap, LineReader.VI_BACKWARD_CHAR, "h");
        bind(keyMap, LineReader.VI_INSERT, ASTExpr.DEFAULT_INDEX_VARIABLE_NAME);
        bind(keyMap, LineReader.DOWN_LINE_OR_HISTORY, "j");
        bind(keyMap, LineReader.UP_LINE_OR_HISTORY, JvmAnnotationNames.KIND_FIELD_NAME);
        bind(keyMap, LineReader.VI_FORWARD_CHAR, "l");
        bind(keyMap, LineReader.VI_REPEAT_SEARCH, "n");
        bind(keyMap, LineReader.VI_OPEN_LINE_BELOW, "o");
        bind(keyMap, LineReader.VI_PUT_AFTER, "p");
        bind(keyMap, LineReader.VI_REPLACE_CHARS, "r");
        bind(keyMap, LineReader.VI_SUBSTITUTE, "s");
        bind(keyMap, LineReader.VI_FIND_NEXT_CHAR_SKIP, "t");
        bind(keyMap, LineReader.UNDO, "u");
        bind(keyMap, LineReader.VISUAL_MODE, RegisterSpec.PREFIX);
        bind(keyMap, LineReader.VI_FORWARD_WORD, "w");
        bind(keyMap, LineReader.VI_DELETE_CHAR, "x");
        bind(keyMap, LineReader.VI_YANK, "y");
        bind(keyMap, LineReader.VI_GOTO_COLUMN, "|");
        bind(keyMap, LineReader.VI_SWAP_CASE, "~");
        bind(keyMap, LineReader.VI_BACKWARD_CHAR, KeyMap.del());
        bindArrowKeys(keyMap);
        return keyMap;
    }

    public KeyMap<Binding> menu() {
        KeyMap<Binding> keyMap = new KeyMap<>();
        bind(keyMap, LineReader.MENU_COMPLETE, "\t");
        bind(keyMap, LineReader.REVERSE_MENU_COMPLETE, key(InfoCmp.Capability.back_tab));
        bind(keyMap, LineReader.ACCEPT_LINE, "\r", "\n");
        bindArrowKeys(keyMap);
        return keyMap;
    }

    public KeyMap<Binding> safe() {
        KeyMap<Binding> keyMap = new KeyMap<>();
        bind(keyMap, LineReader.SELF_INSERT, KeyMap.range("^@-^?"));
        bind(keyMap, LineReader.ACCEPT_LINE, "\r", "\n");
        bind(keyMap, LineReader.SEND_BREAK, KeyMap.ctrl('G'));
        return keyMap;
    }

    public KeyMap<Binding> visual() {
        KeyMap<Binding> keyMap = new KeyMap<>();
        bind(keyMap, LineReader.UP_LINE, key(InfoCmp.Capability.key_up), JvmAnnotationNames.KIND_FIELD_NAME);
        bind(keyMap, LineReader.DOWN_LINE, key(InfoCmp.Capability.key_down), "j");
        bind(keyMap, new Widget() { // from class: org.jetbrains.kotlin.org.jline.reader.impl.LineReaderImpl$$ExternalSyntheticLambda76
            @Override // org.jetbrains.kotlin.org.jline.reader.Widget
            public final boolean apply() {
                return this.f$0.deactivateRegion();
            }
        }, KeyMap.esc());
        bind(keyMap, LineReader.EXCHANGE_POINT_AND_MARK, "o");
        bind(keyMap, LineReader.PUT_REPLACE_SELECTION, "p");
        bind(keyMap, LineReader.VI_DELETE, "x");
        bind(keyMap, LineReader.VI_OPER_SWAP_CASE, "~");
        return keyMap;
    }

    public KeyMap<Binding> viOpp() {
        KeyMap<Binding> keyMap = new KeyMap<>();
        bind(keyMap, LineReader.UP_LINE, key(InfoCmp.Capability.key_up), JvmAnnotationNames.KIND_FIELD_NAME);
        bind(keyMap, LineReader.DOWN_LINE, key(InfoCmp.Capability.key_down), "j");
        bind(keyMap, LineReader.VI_CMD_MODE, KeyMap.esc());
        return keyMap;
    }

    private void bind(KeyMap<Binding> keyMap, String str, Iterable<? extends CharSequence> iterable) {
        keyMap.bind(new Reference(str), iterable);
    }

    private void bind(KeyMap<Binding> keyMap, String str, CharSequence... charSequenceArr) {
        keyMap.bind(new Reference(str), charSequenceArr);
    }

    private void bind(KeyMap<Binding> keyMap, Widget widget, CharSequence... charSequenceArr) {
        keyMap.bind(widget, charSequenceArr);
    }

    private String key(InfoCmp.Capability capability) {
        return KeyMap.key(this.terminal, capability);
    }

    private void bindArrowKeys(KeyMap<Binding> keyMap) {
        bind(keyMap, LineReader.UP_LINE_OR_SEARCH, key(InfoCmp.Capability.key_up));
        bind(keyMap, LineReader.DOWN_LINE_OR_SEARCH, key(InfoCmp.Capability.key_down));
        bind(keyMap, LineReader.BACKWARD_CHAR, key(InfoCmp.Capability.key_left));
        bind(keyMap, LineReader.FORWARD_CHAR, key(InfoCmp.Capability.key_right));
        bind(keyMap, LineReader.BEGINNING_OF_LINE, key(InfoCmp.Capability.key_home));
        bind(keyMap, LineReader.END_OF_LINE, key(InfoCmp.Capability.key_end));
        bind(keyMap, LineReader.DELETE_CHAR, key(InfoCmp.Capability.key_dc));
        bind(keyMap, LineReader.KILL_WHOLE_LINE, key(InfoCmp.Capability.key_dl));
        bind(keyMap, LineReader.OVERWRITE_MODE, key(InfoCmp.Capability.key_ic));
        bind(keyMap, LineReader.MOUSE, key(InfoCmp.Capability.key_mouse));
    }

    private void bindConsoleChars(KeyMap<Binding> keyMap, Attributes attributes) {
        if (attributes != null) {
            rebind(keyMap, LineReader.BACKWARD_DELETE_CHAR, KeyMap.del(), (char) attributes.getControlChar(Attributes.ControlChar.VERASE));
            rebind(keyMap, LineReader.BACKWARD_KILL_WORD, KeyMap.ctrl('W'), (char) attributes.getControlChar(Attributes.ControlChar.VWERASE));
            rebind(keyMap, LineReader.KILL_WHOLE_LINE, KeyMap.ctrl(Matrix.MATRIX_TYPE_RANDOM_UT), (char) attributes.getControlChar(Attributes.ControlChar.VKILL));
            rebind(keyMap, LineReader.QUOTED_INSERT, KeyMap.ctrl(Util.C_VOID), (char) attributes.getControlChar(Attributes.ControlChar.VLNEXT));
        }
    }

    private void rebind(KeyMap<Binding> keyMap, String str, String str2, char c) {
        if (c <= 0 || c >= 128) {
            return;
        }
        Reference reference = new Reference(str);
        bind(keyMap, LineReader.SELF_INSERT, str2);
        keyMap.bind(reference, Character.toString(c));
    }
}
