package org.jetbrains.kotlin.org.jline.terminal.impl.jansi;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.CLibrary;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;
import org.jetbrains.kotlin.org.jline.utils.ExecHelper;
import org.jetbrains.kotlin.org.jline.utils.OSUtils;

/* JADX INFO: loaded from: classes4.dex */
public abstract class JansiNativePty implements Pty {
    private final int master;
    private final FileDescriptor masterFD;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f939name;
    private final int slave;
    private final FileDescriptor slaveFD;

    protected abstract Attributes toAttributes(CLibrary.Termios termios);

    protected abstract CLibrary.Termios toTermios(Attributes attributes);

    public JansiNativePty(int i, FileDescriptor fileDescriptor, int i2, FileDescriptor fileDescriptor2, String str) {
        this.master = i;
        this.slave = i2;
        this.f939name = str;
        this.masterFD = fileDescriptor;
        this.slaveFD = fileDescriptor2;
    }

    protected static String ttyname() throws Throwable {
        String strTtyname;
        if (JansiSupportImpl.JANSI_MAJOR_VERSION > 1 || (JansiSupportImpl.JANSI_MAJOR_VERSION == 1 && JansiSupportImpl.JANSI_MINOR_VERSION >= 16)) {
            strTtyname = CLibrary.ttyname(0);
        } else {
            try {
                strTtyname = ExecHelper.exec(true, OSUtils.TTY_COMMAND);
            } catch (IOException e) {
                throw new IOException("Not a tty", e);
            }
        }
        if (strTtyname != null) {
            strTtyname = strTtyname.trim();
        }
        if (strTtyname == null || strTtyname.isEmpty()) {
            throw new IOException("Not a tty");
        }
        return strTtyname;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.master > 0) {
            getMasterInput().close();
        }
        if (this.slave > 0) {
            getSlaveInput().close();
        }
    }

    public String getName() {
        return this.f939name;
    }

    public FileDescriptor getMasterFD() {
        return this.masterFD;
    }

    public FileDescriptor getSlaveFD() {
        return this.slaveFD;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public InputStream getMasterInput() {
        return new FileInputStream(getMasterFD());
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public OutputStream getMasterOutput() {
        return new FileOutputStream(getMasterFD());
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public InputStream getSlaveInput() {
        return new FileInputStream(getSlaveFD());
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public OutputStream getSlaveOutput() {
        return new FileOutputStream(getSlaveFD());
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public Attributes getAttr() throws IOException {
        CLibrary.Termios termios = new CLibrary.Termios();
        CLibrary.tcgetattr(this.slave, termios);
        return toAttributes(termios);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public void setAttr(Attributes attributes) throws IOException {
        CLibrary.tcsetattr(this.slave, CLibrary.TCSANOW, toTermios(attributes));
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public Size getSize() throws IOException {
        CLibrary.WinSize winSize = new CLibrary.WinSize();
        CLibrary.ioctl(this.slave, CLibrary.TIOCGWINSZ, winSize);
        return new Size(winSize.ws_col, winSize.ws_row);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public void setSize(Size size) throws IOException {
        CLibrary.ioctl(this.slave, CLibrary.TIOCSWINSZ, new CLibrary.WinSize((short) size.getRows(), (short) size.getColumns()));
    }

    public String toString() {
        return "JansiNativePty[" + getName() + "]";
    }

    protected static FileDescriptor newDescriptor(int i) {
        try {
            Constructor declaredConstructor = FileDescriptor.class.getDeclaredConstructor(Integer.TYPE);
            declaredConstructor.setAccessible(true);
            return (FileDescriptor) declaredConstructor.newInstance(Integer.valueOf(i));
        } catch (Throwable th) {
            throw new RuntimeException("Unable to create FileDescriptor", th);
        }
    }
}
