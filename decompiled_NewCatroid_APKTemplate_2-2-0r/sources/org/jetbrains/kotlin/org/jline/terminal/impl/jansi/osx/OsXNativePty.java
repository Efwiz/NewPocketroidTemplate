package org.jetbrains.kotlin.org.jline.terminal.impl.jansi.osx;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.jetbrains.kotlin.org.fusesource.jansi.internal.CLibrary;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.impl.jansi.JansiNativePty;

/* JADX INFO: loaded from: classes4.dex */
public class OsXNativePty extends JansiNativePty {
    private static long setFlag(boolean z, long j, long j2) {
        return z ? j2 | j : j2;
    }

    public static OsXNativePty current() throws IOException {
        try {
            return new OsXNativePty(-1, null, 0, FileDescriptor.in, ttyname());
        } catch (IOException e) {
            throw new IOException("Not a tty", e);
        }
    }

    public static OsXNativePty open(Attributes attributes, Size size) throws IOException {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        byte[] bArr = new byte[64];
        CLibrary.openpty(iArr, iArr2, bArr, attributes != null ? termios(attributes) : null, size != null ? new CLibrary.WinSize((short) size.getRows(), (short) size.getColumns()) : null);
        int i = 0;
        while (bArr[i] != 0) {
            i++;
        }
        String str = new String(bArr, 0, i);
        int i2 = iArr[0];
        FileDescriptor fileDescriptorNewDescriptor = newDescriptor(i2);
        int i3 = iArr2[0];
        return new OsXNativePty(i2, fileDescriptorNewDescriptor, i3, newDescriptor(i3), str);
    }

    public OsXNativePty(int i, FileDescriptor fileDescriptor, int i2, FileDescriptor fileDescriptor2, String str) {
        super(i, fileDescriptor, i2, fileDescriptor2, str);
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jansi.JansiNativePty
    protected CLibrary.Termios toTermios(Attributes attributes) {
        return termios(attributes);
    }

    static CLibrary.Termios termios(Attributes attributes) {
        CLibrary.Termios termios = new CLibrary.Termios();
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.BRKINT), 2L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.PARMRK), 8L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.INPCK), 16L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.ISTRIP), 32L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.INLCR), 64L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNCR), 128L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.ICRNL), 256L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXON), 512L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXOFF), 1024L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXANY), 2048L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192L, termios.c_iflag);
        termios.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IUTF8), 16384L, termios.c_iflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLCR), 2L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OXTABS), 4L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOEOT), 8L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOCR), 32L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFILL), 128L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.NLDLY), 768L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.TABDLY), 3076L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.FFDLY), 16384L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.BSDLY), 32768L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.VTDLY), 65536L, termios.c_oflag);
        termios.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFDEL), 131072L, termios.c_oflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CIGNORE), 1L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS5), 0L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS6), 256L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS7), 512L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS8), 768L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CSTOPB), 1024L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CREAD), 2048L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARENB), 4096L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARODD), 8192L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.HUPCL), 16384L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CLOCAL), 32768L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CCTS_OFLOW), 65536L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CRTS_IFLOW), 131072L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CDTR_IFLOW), 262144L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CDSR_OFLOW), 524288L, termios.c_cflag);
        termios.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CCAR_OFLOW), 1048576L, termios.c_cflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOKE), 1L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOE), 2L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOK), 4L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHO), 8L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHONL), 16L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 32L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 64L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ISIG), 128L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ICANON), 256L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ALTWERASE), 512L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1024L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2048L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.TOSTOP), 4194304L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.FLUSHO), 8388608L, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOKERNINFO), TagBits.HasUnresolvedSuperclass, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.PENDIN), TagBits.HasTypeVariable, termios.c_lflag);
        termios.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOFLSH), -2147483648L, termios.c_lflag);
        termios.c_cc[0] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOF);
        termios.c_cc[1] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOL);
        termios.c_cc[2] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOL2);
        termios.c_cc[3] = (byte) attributes.getControlChar(Attributes.ControlChar.VERASE);
        termios.c_cc[4] = (byte) attributes.getControlChar(Attributes.ControlChar.VWERASE);
        termios.c_cc[5] = (byte) attributes.getControlChar(Attributes.ControlChar.VKILL);
        termios.c_cc[6] = (byte) attributes.getControlChar(Attributes.ControlChar.VREPRINT);
        termios.c_cc[8] = (byte) attributes.getControlChar(Attributes.ControlChar.VINTR);
        termios.c_cc[9] = (byte) attributes.getControlChar(Attributes.ControlChar.VQUIT);
        termios.c_cc[10] = (byte) attributes.getControlChar(Attributes.ControlChar.VSUSP);
        termios.c_cc[11] = (byte) attributes.getControlChar(Attributes.ControlChar.VDSUSP);
        termios.c_cc[12] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTART);
        termios.c_cc[13] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTOP);
        termios.c_cc[14] = (byte) attributes.getControlChar(Attributes.ControlChar.VLNEXT);
        termios.c_cc[15] = (byte) attributes.getControlChar(Attributes.ControlChar.VDISCARD);
        termios.c_cc[16] = (byte) attributes.getControlChar(Attributes.ControlChar.VMIN);
        termios.c_cc[17] = (byte) attributes.getControlChar(Attributes.ControlChar.VTIME);
        termios.c_cc[18] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTATUS);
        return termios;
    }

    @Override // org.jetbrains.kotlin.org.jline.terminal.impl.jansi.JansiNativePty
    protected Attributes toAttributes(CLibrary.Termios termios) {
        Attributes attributes = new Attributes();
        EnumSet<Attributes.InputFlag> inputFlags = attributes.getInputFlags();
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.BRKINT, 2);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNPAR, 4);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.PARMRK, 8);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.INPCK, 16);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.ISTRIP, 32);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.INLCR, 64);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IGNCR, 128);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.ICRNL, 256);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IXON, 512);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IXOFF, 1024);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IXANY, 2048);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IMAXBEL, 8192);
        addFlag(termios.c_iflag, inputFlags, Attributes.InputFlag.IUTF8, 16384);
        EnumSet<Attributes.OutputFlag> outputFlags = attributes.getOutputFlags();
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OPOST, 1);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.ONLCR, 2);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OXTABS, 4);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.ONOEOT, 8);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OCRNL, 16);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.ONOCR, 32);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.ONLRET, 64);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OFILL, 128);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.NLDLY, 768);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.TABDLY, 3076);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.CRDLY, 12288);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.FFDLY, 16384);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.BSDLY, 32768);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.VTDLY, 65536);
        addFlag(termios.c_oflag, outputFlags, Attributes.OutputFlag.OFDEL, 131072);
        EnumSet<Attributes.ControlFlag> controlFlags = attributes.getControlFlags();
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CIGNORE, 1);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS5, 0);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS6, 256);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS7, 512);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CS8, 768);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CSTOPB, 1024);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CREAD, 2048);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.PARENB, 4096);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.PARODD, 8192);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.HUPCL, 16384);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CLOCAL, 32768);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CCTS_OFLOW, 65536);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CRTS_IFLOW, 131072);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CDSR_OFLOW, 524288);
        addFlag(termios.c_cflag, controlFlags, Attributes.ControlFlag.CCAR_OFLOW, 1048576);
        EnumSet<Attributes.LocalFlag> localFlags = attributes.getLocalFlags();
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOKE, 1);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOE, 2);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOK, 4);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHO, 8);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHONL, 16);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOPRT, 32);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ECHOCTL, 64);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ISIG, 128);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ICANON, 256);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.ALTWERASE, 512);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.IEXTEN, 1024);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.EXTPROC, 2048);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.TOSTOP, 4194304);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.FLUSHO, 8388608);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.NOKERNINFO, 33554432);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.PENDIN, 536870912);
        addFlag(termios.c_lflag, localFlags, Attributes.LocalFlag.NOFLSH, Integer.MIN_VALUE);
        EnumMap<Attributes.ControlChar, Integer> controlChars = attributes.getControlChars();
        controlChars.put(Attributes.ControlChar.VEOF, Integer.valueOf(termios.c_cc[0]));
        controlChars.put(Attributes.ControlChar.VEOL, Integer.valueOf(termios.c_cc[1]));
        controlChars.put(Attributes.ControlChar.VEOL2, Integer.valueOf(termios.c_cc[2]));
        controlChars.put(Attributes.ControlChar.VERASE, Integer.valueOf(termios.c_cc[3]));
        controlChars.put(Attributes.ControlChar.VWERASE, Integer.valueOf(termios.c_cc[4]));
        controlChars.put(Attributes.ControlChar.VKILL, Integer.valueOf(termios.c_cc[5]));
        controlChars.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(termios.c_cc[6]));
        controlChars.put(Attributes.ControlChar.VINTR, Integer.valueOf(termios.c_cc[8]));
        controlChars.put(Attributes.ControlChar.VQUIT, Integer.valueOf(termios.c_cc[9]));
        controlChars.put(Attributes.ControlChar.VSUSP, Integer.valueOf(termios.c_cc[10]));
        controlChars.put(Attributes.ControlChar.VDSUSP, Integer.valueOf(termios.c_cc[11]));
        controlChars.put(Attributes.ControlChar.VSTART, Integer.valueOf(termios.c_cc[12]));
        controlChars.put(Attributes.ControlChar.VSTOP, Integer.valueOf(termios.c_cc[13]));
        controlChars.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(termios.c_cc[14]));
        controlChars.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(termios.c_cc[15]));
        controlChars.put(Attributes.ControlChar.VMIN, Integer.valueOf(termios.c_cc[16]));
        controlChars.put(Attributes.ControlChar.VTIME, Integer.valueOf(termios.c_cc[17]));
        controlChars.put(Attributes.ControlChar.VSTATUS, Integer.valueOf(termios.c_cc[18]));
        return attributes;
    }

    private static <T extends Enum<T>> void addFlag(long j, EnumSet<T> enumSet, T t, int i) {
        if ((j & ((long) i)) != 0) {
            enumSet.add(t);
        }
    }
}
