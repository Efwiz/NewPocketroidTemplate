package org.jetbrains.kotlin.org.jline.terminal.impl.jna.osx;

import com.sun.jna.LastErrorException;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Platform;
import java.io.FileDescriptor;
import java.io.IOException;
import org.jetbrains.kotlin.org.jline.terminal.Attributes;
import org.jetbrains.kotlin.org.jline.terminal.Size;
import org.jetbrains.kotlin.org.jline.terminal.impl.jna.JnaNativePty;

/* JADX INFO: loaded from: classes4.dex */
public class OsXNativePty extends JnaNativePty {
    private static final CLibrary C_LIBRARY = (CLibrary) Native.loadLibrary(Platform.C_LIBRARY_NAME, CLibrary.class);

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    public static OsXNativePty current() throws LastErrorException, IOException {
        byte[] bArr = new byte[64];
        C_LIBRARY.ttyname_r(0, bArr, 64);
        int i = 0;
        while (bArr[i] != 0) {
            i++;
        }
        return new OsXNativePty(-1, null, 0, FileDescriptor.in, new String(bArr, 0, i));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    public static OsXNativePty open(Attributes attributes, Size size) throws LastErrorException, IOException {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        byte[] bArr = new byte[64];
        C_LIBRARY.openpty(iArr, iArr2, bArr, attributes != null ? new CLibrary.termios(attributes) : null, size != null ? new CLibrary.winsize(size) : null);
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

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public Attributes getAttr() throws LastErrorException, IOException {
        CLibrary.termios termiosVar = new CLibrary.termios();
        C_LIBRARY.tcgetattr(getSlave(), termiosVar);
        return termiosVar.toAttributes();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public void setAttr(Attributes attributes) throws LastErrorException, IOException {
        C_LIBRARY.tcsetattr(getSlave(), 0, new CLibrary.termios(attributes));
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public Size getSize() throws LastErrorException, IOException {
        CLibrary.winsize winsizeVar = new CLibrary.winsize();
        C_LIBRARY.ioctl(getSlave(), new NativeLong(1074295912L), winsizeVar);
        return winsizeVar.toSize();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.sun.jna.LastErrorException */
    @Override // org.jetbrains.kotlin.org.jline.terminal.spi.Pty
    public void setSize(Size size) throws LastErrorException, IOException {
        C_LIBRARY.ioctl(getSlave(), new NativeLong(2148037735L), new CLibrary.winsize(size));
    }
}
