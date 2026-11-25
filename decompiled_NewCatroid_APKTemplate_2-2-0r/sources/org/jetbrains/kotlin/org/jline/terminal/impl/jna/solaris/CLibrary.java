package org.jetbrains.kotlin.org.jline.terminal.impl.jna.solaris;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;

/* JADX INFO: loaded from: classes4.dex */
public interface CLibrary extends Library {
    void ioctl(int i, long j, winsize winsizeVar) throws LastErrorException;

    void openpty(int[] iArr, int[] iArr2, byte[] bArr, termios termiosVar, winsize winsizeVar) throws LastErrorException;

    void tcgetattr(int i, termios termiosVar) throws LastErrorException;

    void tcsetattr(int i, int i2, termios termiosVar) throws LastErrorException;

    void ttyname_r(int i, byte[] bArr, int i2) throws LastErrorException;

    public static class winsize extends Structure {
        public short ws_col;
        public short ws_row;

        public winsize() {
        }

        public winsize(Size size) {
            this.ws_row = (short) size.getRows();
            this.ws_col = (short) size.getColumns();
        }

        public Size toSize() {
            return new Size(this.ws_col, this.ws_row);
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList("ws_row", "ws_col", "ws_xpixel", "ws_ypixel");
        }
    }

    public static class termios extends Structure {
        public byte[] c_cc;
        public int c_cflag;
        public int c_iflag;
        public int c_lflag;
        public int c_oflag;

        private int setFlag(boolean z, int i, int i2) {
            return z ? i2 | i : i2;
        }

        protected List<String> getFieldOrder() {
            return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc");
        }

        public termios() {
            this.c_cc = new byte[32];
        }

        public termios(Attributes attributes) {
            this.c_cc = new byte[32];
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNBRK), 1, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.BRKINT), 2, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNPAR), 4, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.PARMRK), 16, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.INPCK), 32, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.ISTRIP), 64, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.INLCR), 256, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNCR), 512, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.ICRNL), 1024, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXON), 8192, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXOFF), 65536, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IXANY), 16384, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IMAXBEL), 131072, this.c_iflag);
            this.c_iflag = setFlag(attributes.getInputFlag(Attributes.InputFlag.IUTF8), 262144, this.c_iflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OPOST), 1, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLCR), 4, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OCRNL), 16, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOCR), 32, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLRET), 64, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFILL), 256, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.NLDLY), 1024, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.TABDLY), 81920, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.FFDLY), 1048576, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.BSDLY), 131072, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.VTDLY), 262144, this.c_oflag);
            this.c_oflag = setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFDEL), 512, this.c_oflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS5), 0, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS6), 32, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS7), 64, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS8), 96, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CSTOPB), 256, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CREAD), 512, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARENB), 1024, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARODD), 4096, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.HUPCL), 8192, this.c_cflag);
            this.c_cflag = setFlag(attributes.getControlFlag(Attributes.ControlFlag.CLOCAL), 16384, this.c_cflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOKE), 16384, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOE), 32, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOK), 64, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHO), 16, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHONL), 256, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 8192, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 4096, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ISIG), 1, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ICANON), 2, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1048576, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2097152, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.TOSTOP), 1024, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.FLUSHO), 65536, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.PENDIN), 262144, this.c_lflag);
            this.c_lflag = setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOFLSH), 512, this.c_lflag);
            this.c_cc[4] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOF);
            this.c_cc[11] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOL);
            this.c_cc[16] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOL2);
            this.c_cc[2] = (byte) attributes.getControlChar(Attributes.ControlChar.VERASE);
            this.c_cc[14] = (byte) attributes.getControlChar(Attributes.ControlChar.VWERASE);
            this.c_cc[3] = (byte) attributes.getControlChar(Attributes.ControlChar.VKILL);
            this.c_cc[12] = (byte) attributes.getControlChar(Attributes.ControlChar.VREPRINT);
            this.c_cc[0] = (byte) attributes.getControlChar(Attributes.ControlChar.VINTR);
            this.c_cc[1] = (byte) attributes.getControlChar(Attributes.ControlChar.VQUIT);
            this.c_cc[10] = (byte) attributes.getControlChar(Attributes.ControlChar.VSUSP);
            this.c_cc[8] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTART);
            this.c_cc[9] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTOP);
            this.c_cc[15] = (byte) attributes.getControlChar(Attributes.ControlChar.VLNEXT);
            this.c_cc[13] = (byte) attributes.getControlChar(Attributes.ControlChar.VDISCARD);
            this.c_cc[6] = (byte) attributes.getControlChar(Attributes.ControlChar.VMIN);
            this.c_cc[5] = (byte) attributes.getControlChar(Attributes.ControlChar.VTIME);
        }

        public Attributes toAttributes() {
            Attributes attributes = new Attributes();
            EnumSet<Attributes.InputFlag> inputFlags = attributes.getInputFlags();
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNBRK, 1);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.BRKINT, 2);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNPAR, 4);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.PARMRK, 16);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.INPCK, 32);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.ISTRIP, 64);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.INLCR, 256);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IGNCR, 512);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.ICRNL, 1024);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IXON, 8192);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IXOFF, 65536);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IXANY, 16384);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IMAXBEL, 131072);
            addFlag(this.c_iflag, inputFlags, Attributes.InputFlag.IUTF8, 262144);
            EnumSet<Attributes.OutputFlag> outputFlags = attributes.getOutputFlags();
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OPOST, 1);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.ONLCR, 4);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OCRNL, 16);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.ONOCR, 32);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.ONLRET, 64);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OFILL, 256);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.NLDLY, 1024);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.TABDLY, 81920);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.CRDLY, 12288);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.FFDLY, 1048576);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.BSDLY, 131072);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.VTDLY, 262144);
            addFlag(this.c_oflag, outputFlags, Attributes.OutputFlag.OFDEL, 512);
            EnumSet<Attributes.ControlFlag> controlFlags = attributes.getControlFlags();
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS5, 0);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS6, 32);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS7, 64);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CS8, 96);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CSTOPB, 256);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CREAD, 512);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.PARENB, 1024);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.PARODD, 4096);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.HUPCL, 8192);
            addFlag(this.c_cflag, controlFlags, Attributes.ControlFlag.CLOCAL, 16384);
            EnumSet<Attributes.LocalFlag> localFlags = attributes.getLocalFlags();
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOKE, 16384);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOE, 32);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOK, 64);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHO, 16);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHONL, 256);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOPRT, 8192);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ECHOCTL, 4096);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ISIG, 1);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.ICANON, 2);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.IEXTEN, 1048576);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.EXTPROC, 2097152);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.TOSTOP, 1024);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.FLUSHO, 65536);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.PENDIN, 262144);
            addFlag(this.c_lflag, localFlags, Attributes.LocalFlag.NOFLSH, 512);
            EnumMap<Attributes.ControlChar, Integer> controlChars = attributes.getControlChars();
            controlChars.put(Attributes.ControlChar.VEOF, Integer.valueOf(this.c_cc[4]));
            controlChars.put(Attributes.ControlChar.VEOL, Integer.valueOf(this.c_cc[11]));
            controlChars.put(Attributes.ControlChar.VEOL2, Integer.valueOf(this.c_cc[16]));
            controlChars.put(Attributes.ControlChar.VERASE, Integer.valueOf(this.c_cc[2]));
            controlChars.put(Attributes.ControlChar.VWERASE, Integer.valueOf(this.c_cc[14]));
            controlChars.put(Attributes.ControlChar.VKILL, Integer.valueOf(this.c_cc[3]));
            controlChars.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(this.c_cc[12]));
            controlChars.put(Attributes.ControlChar.VINTR, Integer.valueOf(this.c_cc[0]));
            controlChars.put(Attributes.ControlChar.VQUIT, Integer.valueOf(this.c_cc[1]));
            controlChars.put(Attributes.ControlChar.VSUSP, Integer.valueOf(this.c_cc[10]));
            controlChars.put(Attributes.ControlChar.VSTART, Integer.valueOf(this.c_cc[8]));
            controlChars.put(Attributes.ControlChar.VSTOP, Integer.valueOf(this.c_cc[9]));
            controlChars.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(this.c_cc[15]));
            controlChars.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(this.c_cc[13]));
            controlChars.put(Attributes.ControlChar.VMIN, Integer.valueOf(this.c_cc[6]));
            controlChars.put(Attributes.ControlChar.VTIME, Integer.valueOf(this.c_cc[5]));
            return attributes;
        }

        private <T extends Enum<T>> void addFlag(int i, EnumSet<T> enumSet, T t, int i2) {
            if ((i & i2) != 0) {
                enumSet.add(t);
            }
        }
    }
}
