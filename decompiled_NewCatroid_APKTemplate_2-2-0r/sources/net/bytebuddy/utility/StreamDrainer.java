package net.bytebuddy.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class StreamDrainer {
    public static final StreamDrainer DEFAULT = new StreamDrainer();
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    private static final int END_OF_STREAM = -1;
    private static final int FROM_BEGINNING = 0;
    private final int bufferSize;

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.bufferSize == ((StreamDrainer) obj).bufferSize;
    }

    public int hashCode() {
        return (getClass().hashCode() * 31) + this.bufferSize;
    }

    public StreamDrainer() {
        this(1024);
    }

    public StreamDrainer(int i) {
        this.bufferSize = i;
    }

    public byte[] drain(InputStream inputStream) throws IOException {
        int i;
        ArrayList<byte[]> arrayList = new ArrayList();
        byte[] bArr = new byte[this.bufferSize];
        int iMax = 0;
        do {
            i = inputStream.read(bArr, iMax, this.bufferSize - iMax);
            iMax += Math.max(i, 0);
            if (iMax == this.bufferSize) {
                arrayList.add(bArr);
                bArr = new byte[this.bufferSize];
                iMax = 0;
            }
        } while (i != -1);
        byte[] bArr2 = new byte[(arrayList.size() * this.bufferSize) + iMax];
        int i2 = 0;
        for (byte[] bArr3 : arrayList) {
            int i3 = this.bufferSize;
            System.arraycopy(bArr3, 0, bArr2, i2 * i3, i3);
            i2++;
        }
        System.arraycopy(bArr, 0, bArr2, i2 * this.bufferSize, iMax);
        return bArr2;
    }
}
