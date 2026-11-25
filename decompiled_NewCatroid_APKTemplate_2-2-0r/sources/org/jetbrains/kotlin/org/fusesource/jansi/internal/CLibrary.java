package org.jetbrains.kotlin.org.fusesource.jansi.internal;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.ArgFlag;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.ClassFlag;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.FieldFlag;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniArg;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniClass;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniField;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.JniMethod;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.Library;
import org.jetbrains.kotlin.org.fusesource.hawtjni.runtime.MethodFlag;

/* JADX INFO: loaded from: classes4.dex */
@JniClass
public class CLibrary {

    @JniField(accessor = SchemaSymbols.ATTVAL_TRUE_1, conditional = "defined(HAVE_ISATTY)", flags = {FieldFlag.CONSTANT})
    public static boolean HAVE_ISATTY;
    private static final Library LIBRARY;

    @JniField(conditional = "defined(STDERR_FILENO)", flags = {FieldFlag.CONSTANT})
    public static int STDERR_FILENO;

    @JniField(conditional = "defined(STDIN_FILENO)", flags = {FieldFlag.CONSTANT})
    public static int STDIN_FILENO;

    @JniField(conditional = "defined(STDOUT_FILENO)", flags = {FieldFlag.CONSTANT})
    public static int STDOUT_FILENO;

    @JniField(conditional = "defined(TCSADRAIN)", flags = {FieldFlag.CONSTANT})
    public static int TCSADRAIN;

    @JniField(conditional = "defined(TCSAFLUSH)", flags = {FieldFlag.CONSTANT})
    public static int TCSAFLUSH;

    @JniField(conditional = "defined(TCSANOW)", flags = {FieldFlag.CONSTANT})
    public static int TCSANOW;

    @JniField(conditional = "defined(TIOCGETA)", flags = {FieldFlag.CONSTANT})
    public static long TIOCGETA;

    @JniField(conditional = "defined(TIOCGETD)", flags = {FieldFlag.CONSTANT})
    public static long TIOCGETD;

    @JniField(conditional = "defined(TIOCGWINSZ)", flags = {FieldFlag.CONSTANT})
    public static long TIOCGWINSZ;

    @JniField(conditional = "defined(TIOCSETA)", flags = {FieldFlag.CONSTANT})
    public static long TIOCSETA;

    @JniField(conditional = "defined(TIOCSETD)", flags = {FieldFlag.CONSTANT})
    public static long TIOCSETD;

    @JniField(conditional = "defined(TIOCSWINSZ)", flags = {FieldFlag.CONSTANT})
    public static long TIOCSWINSZ;

    @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
    private static native void init();

    @JniMethod(conditional = "defined(HAVE_IOCTL)")
    public static native int ioctl(@JniArg int i, @JniArg long j, @JniArg(flags = {ArgFlag.POINTER_ARG}) WinSize winSize);

    @JniMethod(conditional = "defined(HAVE_IOCTL)")
    public static native int ioctl(@JniArg int i, @JniArg long j, @JniArg int[] iArr);

    @JniMethod(conditional = "defined(HAVE_ISATTY)")
    public static native int isatty(@JniArg int i);

    @JniMethod(conditional = "defined(HAVE_OPENPTY)")
    public static native int openpty(@JniArg(cast = "int *", flags = {ArgFlag.NO_IN}) int[] iArr, @JniArg(cast = "int *", flags = {ArgFlag.NO_IN}) int[] iArr2, @JniArg(cast = "char *", flags = {ArgFlag.NO_IN}) byte[] bArr, @JniArg(cast = "struct termios *", flags = {ArgFlag.NO_OUT}) Termios termios, @JniArg(cast = "struct winsize *", flags = {ArgFlag.NO_OUT}) WinSize winSize);

    @JniMethod(conditional = "defined(HAVE_TCGETATTR)")
    public static native int tcgetattr(@JniArg int i, @JniArg(cast = "struct termios *", flags = {ArgFlag.NO_IN}) Termios termios);

    @JniMethod(conditional = "defined(HAVE_TCSETATTR)")
    public static native int tcsetattr(@JniArg int i, @JniArg int i2, @JniArg(cast = "struct termios *", flags = {ArgFlag.NO_OUT}) Termios termios);

    @JniMethod(conditional = "FALSE")
    public static native String ttyname(@JniArg int i);

    static {
        Library library = new Library("jansi", (Class<?>) CLibrary.class);
        LIBRARY = library;
        library.load();
        init();
    }

    @JniClass(conditional = "defined(HAVE_IOCTL)", flags = {ClassFlag.STRUCT}, name = "winsize")
    public static class WinSize {

        @JniField(accessor = "sizeof(struct winsize)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = "ws_col")
        public short ws_col;

        @JniField(accessor = "ws_row")
        public short ws_row;

        @JniField(accessor = "ws_xpixel")
        public short ws_xpixel;

        @JniField(accessor = "ws_ypixel")
        public short ws_ypixel;

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static native void init();

        static {
            CLibrary.LIBRARY.load();
            init();
        }

        public WinSize() {
        }

        public WinSize(short s, short s2) {
            this.ws_row = s;
            this.ws_col = s2;
        }
    }

    @JniClass(conditional = "defined(HAVE_IOCTL)", flags = {ClassFlag.STRUCT}, name = "termios")
    public static class Termios {

        @JniField(accessor = "sizeof(struct termios)", flags = {FieldFlag.CONSTANT})
        public static int SIZEOF;

        @JniField(accessor = "c_cc")
        public byte[] c_cc = new byte[32];

        @JniField(accessor = "c_cflag")
        public long c_cflag;

        @JniField(accessor = "c_iflag")
        public long c_iflag;

        @JniField(accessor = "c_ispeed")
        public long c_ispeed;

        @JniField(accessor = "c_lflag")
        public long c_lflag;

        @JniField(accessor = "c_oflag")
        public long c_oflag;

        @JniField(accessor = "c_ospeed")
        public long c_ospeed;

        @JniMethod(flags = {MethodFlag.CONSTANT_INITIALIZER})
        private static native void init();

        static {
            CLibrary.LIBRARY.load();
            init();
        }
    }
}
