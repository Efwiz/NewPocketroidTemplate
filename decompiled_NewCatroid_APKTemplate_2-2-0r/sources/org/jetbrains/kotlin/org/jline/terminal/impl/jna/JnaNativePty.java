package org.jetbrains.kotlin.org.jline.terminal.impl.jna;

import com.sun.jna.Platform;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.impl.jna.freebsd.FreeBsdNativePty;
import org.jetbrains.kotlin.org.jline.terminal.impl.jna.linux.LinuxNativePty;
import org.jetbrains.kotlin.org.jline.terminal.impl.jna.osx.OsXNativePty;
import org.jetbrains.kotlin.org.jline.terminal.impl.jna.solaris.SolarisNativePty;
import org.jetbrains.kotlin.org.jline.terminal.spi.Pty;

/* JADX INFO: loaded from: classes4.dex */
public abstract class JnaNativePty implements Pty {
    private final int master;
    private final FileDescriptor masterFD;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f940name;
    private final int slave;
    private final FileDescriptor slaveFD;

    public static JnaNativePty current() throws IOException {
        if (Platform.isMac()) {
            return OsXNativePty.current();
        }
        if (Platform.isLinux()) {
            return LinuxNativePty.current();
        }
        if (Platform.isSolaris()) {
            return SolarisNativePty.current();
        }
        if (Platform.isFreeBSD()) {
            return FreeBsdNativePty.current();
        }
        throw new UnsupportedOperationException();
    }

    public static JnaNativePty open(Attributes attributes, Size size) throws IOException {
        if (Platform.isMac()) {
            return OsXNativePty.open(attributes, size);
        }
        if (Platform.isLinux()) {
            return LinuxNativePty.open(attributes, size);
        }
        if (Platform.isSolaris()) {
            return SolarisNativePty.open(attributes, size);
        }
        if (Platform.isFreeBSD()) {
            return FreeBsdNativePty.open(attributes, size);
        }
        throw new UnsupportedOperationException();
    }

    protected JnaNativePty(int i, FileDescriptor fileDescriptor, int i2, FileDescriptor fileDescriptor2, String str) {
        this.master = i;
        this.slave = i2;
        this.f940name = str;
        this.masterFD = fileDescriptor;
        this.slaveFD = fileDescriptor2;
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

    public int getSlave() {
        return this.slave;
    }

    public String getName() {
        return this.f940name;
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

    protected static FileDescriptor newDescriptor(int i) {
        try {
            Constructor declaredConstructor = FileDescriptor.class.getDeclaredConstructor(Integer.TYPE);
            declaredConstructor.setAccessible(true);
            return (FileDescriptor) declaredConstructor.newInstance(Integer.valueOf(i));
        } catch (Throwable th) {
            throw new RuntimeException("Unable to create FileDescriptor", th);
        }
    }

    public String toString() {
        return "JnaNativePty[" + getName() + "]";
    }
}
