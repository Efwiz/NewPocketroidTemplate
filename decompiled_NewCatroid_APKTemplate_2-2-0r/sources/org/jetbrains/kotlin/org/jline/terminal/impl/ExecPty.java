package org.jetbrains.kotlin.org.jline.terminal.impl;

import com.beust.jcommander.Parameters;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.SystemProperties;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;
import org.jetbrains.kotlin.org.jline.utils.ExecHelper;
import org.jetbrains.kotlin.org.jline.utils.OSUtils;

/* JADX INFO: loaded from: classes4.dex */
public class ExecPty implements Pty {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f938name;
    private final boolean system;

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
    }

    public static Pty current() throws IOException {
        try {
            return new ExecPty(ExecHelper.exec(true, OSUtils.TTY_COMMAND).trim(), true);
        } catch (IOException e) {
            throw new IOException("Not a tty", e);
        }
    }

    protected ExecPty(String str, boolean z) {
        this.f938name = str;
        this.system = z;
    }

    public String getName() {
        return this.f938name;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public InputStream getMasterInput() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public OutputStream getMasterOutput() {
        throw new UnsupportedOperationException();
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public InputStream getSlaveInput() throws IOException {
        return this.system ? new FileInputStream(FileDescriptor.in) : new FileInputStream(getName());
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public OutputStream getSlaveOutput() throws IOException {
        return this.system ? new FileOutputStream(FileDescriptor.out) : new FileOutputStream(getName());
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public Attributes getAttr() throws IOException {
        return doGetAttr(doGetConfig());
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public void setAttr(Attributes attributes) throws Throwable {
        List<String> flagsToSet = getFlagsToSet(attributes, getAttr());
        if (flagsToSet.isEmpty()) {
            return;
        }
        flagsToSet.add(0, OSUtils.STTY_COMMAND);
        if (!this.system) {
            flagsToSet.add(1, OSUtils.STTY_F_OPTION);
            flagsToSet.add(2, getName());
        }
        try {
            ExecHelper.exec(this.system, (String[]) flagsToSet.toArray(new String[flagsToSet.size()]));
        } catch (IOException e) {
            if (e.toString().contains("unable to perform all requested operations")) {
                List<String> flagsToSet2 = getFlagsToSet(attributes, getAttr());
                if (!flagsToSet2.isEmpty()) {
                    throw new IOException("Could not set the following flags: " + String.join(", ", flagsToSet2), e);
                }
                return;
            }
            throw e;
        }
    }

    protected List<String> getFlagsToSet(Attributes attributes, Attributes attributes2) {
        String str;
        ArrayList arrayList = new ArrayList();
        for (Attributes.InputFlag inputFlag : Attributes.InputFlag.values()) {
            if (attributes.getInputFlag(inputFlag) != attributes2.getInputFlag(inputFlag)) {
                arrayList.add((attributes.getInputFlag(inputFlag) ? inputFlag.name() : Parameters.DEFAULT_OPTION_PREFIXES + inputFlag.name()).toLowerCase());
            }
        }
        for (Attributes.OutputFlag outputFlag : Attributes.OutputFlag.values()) {
            if (attributes.getOutputFlag(outputFlag) != attributes2.getOutputFlag(outputFlag)) {
                arrayList.add((attributes.getOutputFlag(outputFlag) ? outputFlag.name() : Parameters.DEFAULT_OPTION_PREFIXES + outputFlag.name()).toLowerCase());
            }
        }
        for (Attributes.ControlFlag controlFlag : Attributes.ControlFlag.values()) {
            if (attributes.getControlFlag(controlFlag) != attributes2.getControlFlag(controlFlag)) {
                arrayList.add((attributes.getControlFlag(controlFlag) ? controlFlag.name() : Parameters.DEFAULT_OPTION_PREFIXES + controlFlag.name()).toLowerCase());
            }
        }
        for (Attributes.LocalFlag localFlag : Attributes.LocalFlag.values()) {
            if (attributes.getLocalFlag(localFlag) != attributes2.getLocalFlag(localFlag)) {
                arrayList.add((attributes.getLocalFlag(localFlag) ? localFlag.name() : Parameters.DEFAULT_OPTION_PREFIXES + localFlag.name()).toLowerCase());
            }
        }
        String str2 = System.getProperty(SystemProperties.OS_NAME).toLowerCase().startsWith("hp") ? "^-" : "undef";
        for (Attributes.ControlChar controlChar : Attributes.ControlChar.values()) {
            if (attributes.getControlChar(controlChar) != attributes2.getControlChar(controlChar)) {
                int controlChar2 = attributes.getControlChar(controlChar);
                arrayList.add(controlChar.name().toLowerCase().substring(1));
                if (controlChar == Attributes.ControlChar.VMIN || controlChar == Attributes.ControlChar.VTIME) {
                    arrayList.add(Integer.toBinaryString(controlChar2));
                } else if (controlChar2 == 0) {
                    arrayList.add(str2);
                } else {
                    if (controlChar2 < 128) {
                        str = "";
                    } else {
                        controlChar2 -= 128;
                        str = "M-";
                    }
                    if (controlChar2 < 32 || controlChar2 == 127) {
                        controlChar2 ^= 64;
                        str = str + "^";
                    }
                    arrayList.add(str + ((char) controlChar2));
                }
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public Size getSize() throws IOException {
        return doGetSize(doGetConfig());
    }

    protected String doGetConfig() throws IOException {
        return this.system ? ExecHelper.exec(true, OSUtils.STTY_COMMAND, "-a") : ExecHelper.exec(false, OSUtils.STTY_COMMAND, OSUtils.STTY_F_OPTION, getName(), "-a");
    }

    static Attributes doGetAttr(String str) throws IOException {
        Attributes attributes = new Attributes();
        for (Attributes.InputFlag inputFlag : Attributes.InputFlag.values()) {
            Boolean boolDoGetFlag = doGetFlag(str, inputFlag);
            if (boolDoGetFlag != null) {
                attributes.setInputFlag(inputFlag, boolDoGetFlag.booleanValue());
            }
        }
        for (Attributes.OutputFlag outputFlag : Attributes.OutputFlag.values()) {
            Boolean boolDoGetFlag2 = doGetFlag(str, outputFlag);
            if (boolDoGetFlag2 != null) {
                attributes.setOutputFlag(outputFlag, boolDoGetFlag2.booleanValue());
            }
        }
        for (Attributes.ControlFlag controlFlag : Attributes.ControlFlag.values()) {
            Boolean boolDoGetFlag3 = doGetFlag(str, controlFlag);
            if (boolDoGetFlag3 != null) {
                attributes.setControlFlag(controlFlag, boolDoGetFlag3.booleanValue());
            }
        }
        for (Attributes.LocalFlag localFlag : Attributes.LocalFlag.values()) {
            Boolean boolDoGetFlag4 = doGetFlag(str, localFlag);
            if (boolDoGetFlag4 != null) {
                attributes.setLocalFlag(localFlag, boolDoGetFlag4.booleanValue());
            }
        }
        for (Attributes.ControlChar controlChar : Attributes.ControlChar.values()) {
            String strSubstring = controlChar.name().toLowerCase().substring(1);
            if ("reprint".endsWith(strSubstring)) {
                strSubstring = "(?:reprint|rprnt)";
            }
            Matcher matcher = Pattern.compile("[\\s;]" + strSubstring + "\\s*=\\s*(.+?)[\\s;]").matcher(str);
            if (matcher.find()) {
                attributes.setControlChar(controlChar, parseControlChar(matcher.group(1).toUpperCase()));
            }
        }
        return attributes;
    }

    private static Boolean doGetFlag(String str, Enum<?> r3) {
        Matcher matcher = Pattern.compile("(?:^|[\\s;])(\\-?" + r3.name().toLowerCase() + ")(?:[\\s;]|$)").matcher(str);
        if (matcher.find()) {
            return Boolean.valueOf(!matcher.group(1).startsWith(Parameters.DEFAULT_OPTION_PREFIXES));
        }
        return null;
    }

    static int parseControlChar(String str) {
        if ("<UNDEF>".equals(str)) {
            return -1;
        }
        if ("DEL".equalsIgnoreCase(str)) {
            return 127;
        }
        if (str.charAt(0) == '0') {
            return Integer.parseInt(str, 8);
        }
        if (str.charAt(0) >= '1' && str.charAt(0) <= '9') {
            return Integer.parseInt(str, 10);
        }
        if (str.charAt(0) == '^') {
            if (str.charAt(1) == '?') {
                return 127;
            }
            return str.charAt(1) - '@';
        }
        if (str.charAt(0) == 'M' && str.charAt(1) == '-') {
            if (str.charAt(2) == '^') {
                if (str.charAt(3) == '?') {
                    return 255;
                }
                return str.charAt(3) + '@';
            }
            return str.charAt(2) + 128;
        }
        return str.charAt(0);
    }

    static Size doGetSize(String str) throws IOException {
        return new Size(doGetInt("columns", str), doGetInt("rows", str));
    }

    static int doGetInt(String str, String str2) throws IOException {
        String[] strArr = {"\\b([0-9]+)\\s+" + str + "\\b", "\\b" + str + "\\s+([0-9]+)\\b", "\\b" + str + "\\s*=\\s*([0-9]+)\\b"};
        for (int i = 0; i < 3; i++) {
            Matcher matcher = Pattern.compile(strArr[i]).matcher(str2);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
        }
        throw new IOException("Unable to parse " + str);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public void setSize(Size size) throws Throwable {
        if (this.system) {
            ExecHelper.exec(true, OSUtils.STTY_COMMAND, "columns", Integer.toString(size.getColumns()), "rows", Integer.toString(size.getRows()));
        } else {
            ExecHelper.exec(false, OSUtils.STTY_COMMAND, OSUtils.STTY_F_OPTION, getName(), "columns", Integer.toString(size.getColumns()), "rows", Integer.toString(size.getRows()));
        }
    }

    public String toString() {
        return "ExecPty[" + getName() + (this.system ? ", system]" : "]");
    }
}
