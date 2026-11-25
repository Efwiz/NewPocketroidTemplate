package org.jetbrains.kotlin.org.jline.terminal.impl.jna.osx;

import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;

/* JADX INFO: loaded from: classes4.dex */
public interface CLibrary extends Library {
    void ioctl(int i, NativeLong nativeLong, winsize winsizeVar) throws LastErrorException;

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
        public NativeLong c_cflag;
        public NativeLong c_iflag;
        public NativeLong c_lflag;
        public NativeLong c_oflag;

        protected List<String> getFieldOrder() {
            return Arrays.asList("c_iflag", "c_oflag", "c_cflag", "c_lflag", "c_cc", "c_ispeed", "c_ospeed");
        }

        public termios() {
            this.c_cc = new byte[20];
        }

        public termios(Attributes attributes) {
            this.c_cc = new byte[20];
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNBRK), 1L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.BRKINT), 2L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNPAR), 4L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.PARMRK), 8L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.INPCK), 16L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.ISTRIP), 32L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.INLCR), 64L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IGNCR), 128L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.ICRNL), 256L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IXON), 512L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IXOFF), 1024L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IXANY), 2048L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IMAXBEL), 8192L, this.c_iflag);
            setFlag(attributes.getInputFlag(Attributes.InputFlag.IUTF8), 16384L, this.c_iflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OPOST), 1L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLCR), 2L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OXTABS), 4L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOEOT), 8L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OCRNL), 16L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONOCR), 32L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.ONLRET), 64L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFILL), 128L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.NLDLY), 768L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.TABDLY), 3076L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.CRDLY), 12288L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.FFDLY), 16384L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.BSDLY), 32768L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.VTDLY), 65536L, this.c_oflag);
            setFlag(attributes.getOutputFlag(Attributes.OutputFlag.OFDEL), 131072L, this.c_oflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CIGNORE), 1L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS5), 0L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS6), 256L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS7), 512L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CS8), 768L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CSTOPB), 1024L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CREAD), 2048L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARENB), 4096L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.PARODD), 8192L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.HUPCL), 16384L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CLOCAL), 32768L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CCTS_OFLOW), 65536L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CRTS_IFLOW), 131072L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CDTR_IFLOW), 262144L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CDSR_OFLOW), 524288L, this.c_cflag);
            setFlag(attributes.getControlFlag(Attributes.ControlFlag.CCAR_OFLOW), 1048576L, this.c_cflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOKE), 1L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOE), 2L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOK), 4L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHO), 8L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHONL), 16L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOPRT), 32L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ECHOCTL), 64L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ISIG), 128L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ICANON), 256L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.ALTWERASE), 512L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.IEXTEN), 1024L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.EXTPROC), 2048L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.TOSTOP), 4194304L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.FLUSHO), 8388608L, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOKERNINFO), TagBits.HasUnresolvedSuperclass, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.PENDIN), TagBits.HasTypeVariable, this.c_lflag);
            setFlag(attributes.getLocalFlag(Attributes.LocalFlag.NOFLSH), -2147483648L, this.c_lflag);
            this.c_cc[0] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOF);
            this.c_cc[1] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOL);
            this.c_cc[2] = (byte) attributes.getControlChar(Attributes.ControlChar.VEOL2);
            this.c_cc[3] = (byte) attributes.getControlChar(Attributes.ControlChar.VERASE);
            this.c_cc[4] = (byte) attributes.getControlChar(Attributes.ControlChar.VWERASE);
            this.c_cc[5] = (byte) attributes.getControlChar(Attributes.ControlChar.VKILL);
            this.c_cc[6] = (byte) attributes.getControlChar(Attributes.ControlChar.VREPRINT);
            this.c_cc[8] = (byte) attributes.getControlChar(Attributes.ControlChar.VINTR);
            this.c_cc[9] = (byte) attributes.getControlChar(Attributes.ControlChar.VQUIT);
            this.c_cc[10] = (byte) attributes.getControlChar(Attributes.ControlChar.VSUSP);
            this.c_cc[11] = (byte) attributes.getControlChar(Attributes.ControlChar.VDSUSP);
            this.c_cc[12] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTART);
            this.c_cc[13] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTOP);
            this.c_cc[14] = (byte) attributes.getControlChar(Attributes.ControlChar.VLNEXT);
            this.c_cc[15] = (byte) attributes.getControlChar(Attributes.ControlChar.VDISCARD);
            this.c_cc[16] = (byte) attributes.getControlChar(Attributes.ControlChar.VMIN);
            this.c_cc[17] = (byte) attributes.getControlChar(Attributes.ControlChar.VTIME);
            this.c_cc[18] = (byte) attributes.getControlChar(Attributes.ControlChar.VSTATUS);
        }

        private void setFlag(boolean z, long j, NativeLong nativeLong) {
            nativeLong.setValue(z ? nativeLong.longValue() | j : nativeLong.longValue());
        }

        public Attributes toAttributes() {
            Attributes attributes = new Attributes();
            EnumSet<Attributes.InputFlag> inputFlags = attributes.getInputFlags();
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNBRK, 1);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNBRK, 1);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.BRKINT, 2);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNPAR, 4);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.PARMRK, 8);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.INPCK, 16);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.ISTRIP, 32);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.INLCR, 64);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IGNCR, 128);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.ICRNL, 256);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IXON, 512);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IXOFF, 1024);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IXANY, 2048);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IMAXBEL, 8192);
            addFlag(this.c_iflag.longValue(), inputFlags, Attributes.InputFlag.IUTF8, 16384);
            EnumSet<Attributes.OutputFlag> outputFlags = attributes.getOutputFlags();
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OPOST, 1);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONLCR, 2);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OXTABS, 4);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONOEOT, 8);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OCRNL, 16);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONOCR, 32);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.ONLRET, 64);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OFILL, 128);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.NLDLY, 768);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.TABDLY, 3076);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.CRDLY, 12288);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.FFDLY, 16384);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.BSDLY, 32768);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.VTDLY, 65536);
            addFlag(this.c_oflag.longValue(), outputFlags, Attributes.OutputFlag.OFDEL, 131072);
            EnumSet<Attributes.ControlFlag> controlFlags = attributes.getControlFlags();
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CIGNORE, 1);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS5, 0);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS6, 256);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS7, 512);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CS8, 768);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CSTOPB, 1024);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CREAD, 2048);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.PARENB, 4096);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.PARODD, 8192);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.HUPCL, 16384);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CLOCAL, 32768);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CCTS_OFLOW, 65536);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CRTS_IFLOW, 131072);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CDSR_OFLOW, 524288);
            addFlag(this.c_cflag.longValue(), controlFlags, Attributes.ControlFlag.CCAR_OFLOW, 1048576);
            EnumSet<Attributes.LocalFlag> localFlags = attributes.getLocalFlags();
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOKE, 1);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOE, 2);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOK, 4);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHO, 8);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHONL, 16);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOPRT, 32);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ECHOCTL, 64);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ISIG, 128);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ICANON, 256);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.ALTWERASE, 512);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.IEXTEN, 1024);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.EXTPROC, 2048);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.TOSTOP, 4194304);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.FLUSHO, 8388608);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.NOKERNINFO, 33554432);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.PENDIN, 536870912);
            addFlag(this.c_lflag.longValue(), localFlags, Attributes.LocalFlag.NOFLSH, Integer.MIN_VALUE);
            EnumMap<Attributes.ControlChar, Integer> controlChars = attributes.getControlChars();
            controlChars.put(Attributes.ControlChar.VEOF, Integer.valueOf(this.c_cc[0]));
            controlChars.put(Attributes.ControlChar.VEOL, Integer.valueOf(this.c_cc[1]));
            controlChars.put(Attributes.ControlChar.VEOL2, Integer.valueOf(this.c_cc[2]));
            controlChars.put(Attributes.ControlChar.VERASE, Integer.valueOf(this.c_cc[3]));
            controlChars.put(Attributes.ControlChar.VWERASE, Integer.valueOf(this.c_cc[4]));
            controlChars.put(Attributes.ControlChar.VKILL, Integer.valueOf(this.c_cc[5]));
            controlChars.put(Attributes.ControlChar.VREPRINT, Integer.valueOf(this.c_cc[6]));
            controlChars.put(Attributes.ControlChar.VINTR, Integer.valueOf(this.c_cc[8]));
            controlChars.put(Attributes.ControlChar.VQUIT, Integer.valueOf(this.c_cc[9]));
            controlChars.put(Attributes.ControlChar.VSUSP, Integer.valueOf(this.c_cc[10]));
            controlChars.put(Attributes.ControlChar.VDSUSP, Integer.valueOf(this.c_cc[11]));
            controlChars.put(Attributes.ControlChar.VSTART, Integer.valueOf(this.c_cc[12]));
            controlChars.put(Attributes.ControlChar.VSTOP, Integer.valueOf(this.c_cc[13]));
            controlChars.put(Attributes.ControlChar.VLNEXT, Integer.valueOf(this.c_cc[14]));
            controlChars.put(Attributes.ControlChar.VDISCARD, Integer.valueOf(this.c_cc[15]));
            controlChars.put(Attributes.ControlChar.VMIN, Integer.valueOf(this.c_cc[16]));
            controlChars.put(Attributes.ControlChar.VTIME, Integer.valueOf(this.c_cc[17]));
            controlChars.put(Attributes.ControlChar.VSTATUS, Integer.valueOf(this.c_cc[18]));
            return attributes;
        }

        private <T extends Enum<T>> void addFlag(long j, EnumSet<T> enumSet, T t, int i) {
            if ((j & ((long) i)) != 0) {
                enumSet.add(t);
            }
        }
    }
}
