package org.jetbrains.kotlin.org.jline.terminal.impl.jansi;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.org.fusesource.jansi.Ansi;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.Terminal;
import org.jetbrains.kotlin.org.jline.terminal.impl.jansi.freebsd.FreeBsdNativePty;
import org.jetbrains.kotlin.org.jline.terminal.impl.jansi.linux.LinuxNativePty;
import org.jetbrains.kotlin.org.jline.terminal.impl.jansi.osx.OsXNativePty;
import org.jetbrains.kotlin.org.jline.terminal.impl.jansi.win.JansiWinSysTerminal;
import org.jetbrains.kotlin.org.jline.terminal.spi.JansiSupport;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;

/* JADX INFO: loaded from: classes4.dex */
public class JansiSupportImpl implements JansiSupport {
    static final int JANSI_MAJOR_VERSION;
    static final int JANSI_MINOR_VERSION;

    static {
        int i;
        int i2;
        int i3 = 0;
        try {
            String implementationVersion = Ansi.class.getPackage().getImplementationVersion();
            if (implementationVersion != null) {
                Matcher matcher = Pattern.compile("([0-9]+)\\.([0-9]+)([\\.-]\\S+)?").matcher(implementationVersion);
                if (matcher.matches()) {
                    i = Integer.parseInt(matcher.group(1));
                    try {
                        i3 = Integer.parseInt(matcher.group(2));
                    } catch (Throwable unused) {
                    }
                    i2 = i3;
                    i3 = i;
                }
                JANSI_MAJOR_VERSION = i3;
                JANSI_MINOR_VERSION = i2;
            }
            i2 = 0;
        } catch (Throwable unused2) {
            i = 0;
        }
        JANSI_MAJOR_VERSION = i3;
        JANSI_MINOR_VERSION = i2;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.JansiSupport
    public Pty current() throws IOException {
        int i;
        String property = System.getProperty(SystemProperties.OS_NAME);
        if (property.startsWith("Linux")) {
            int i2 = JANSI_MAJOR_VERSION;
            if (i2 > 1 || (i2 == 1 && JANSI_MINOR_VERSION >= 16)) {
                return LinuxNativePty.current();
            }
        } else if (property.startsWith("Mac") || property.startsWith("Darwin")) {
            int i3 = JANSI_MAJOR_VERSION;
            if (i3 > 1 || (i3 == 1 && JANSI_MINOR_VERSION >= 12)) {
                return OsXNativePty.current();
            }
        } else if (!property.startsWith("Solaris") && !property.startsWith("SunOS") && property.startsWith("FreeBSD") && ((i = JANSI_MAJOR_VERSION) > 1 || (i == 1 && JANSI_MINOR_VERSION >= 16))) {
            return FreeBsdNativePty.current();
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.JansiSupport
    public Pty open(Attributes attributes, Size size) throws IOException {
        int i = JANSI_MAJOR_VERSION;
        if (i > 1 || (i == 1 && JANSI_MINOR_VERSION >= 16)) {
            String property = System.getProperty(SystemProperties.OS_NAME);
            if (property.startsWith("Linux")) {
                return LinuxNativePty.open(attributes, size);
            }
            if (property.startsWith("Mac") || property.startsWith("Darwin")) {
                return OsXNativePty.open(attributes, size);
            }
            if (!property.startsWith("Solaris") && !property.startsWith("SunOS") && property.startsWith("FreeBSD")) {
                return FreeBsdNativePty.open(attributes, size);
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.JansiSupport
    public Terminal winSysTerminal(String str, boolean z, Terminal.SignalHandler signalHandler) throws IOException {
        int i = JANSI_MAJOR_VERSION;
        if (i > 1 || (i == 1 && JANSI_MINOR_VERSION >= 12)) {
            JansiWinSysTerminal jansiWinSysTerminal = new JansiWinSysTerminal(str, z, signalHandler);
            if (i == 1 && JANSI_MINOR_VERSION < 16) {
                jansiWinSysTerminal.disableScrolling();
            }
            return jansiWinSysTerminal;
        }
        throw new UnsupportedOperationException();
    }
}
