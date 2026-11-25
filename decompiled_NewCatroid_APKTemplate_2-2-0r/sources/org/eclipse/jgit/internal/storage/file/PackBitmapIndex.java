package org.eclipse.jgit.internal.storage.file;

import com.googlecode.javaewah.EWAHCompressedBitmap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.util.io.SilentFileInputStream;

/* JADX INFO: loaded from: classes13.dex */
public abstract class PackBitmapIndex {
    public static final int FLAG_REUSE = 1;
    byte[] packChecksum;

    public abstract int findPosition(AnyObjectId anyObjectId);

    public abstract EWAHCompressedBitmap getBitmap(AnyObjectId anyObjectId);

    public abstract int getBitmapCount();

    public abstract ObjectId getObject(int i) throws IllegalArgumentException;

    public abstract int getObjectCount();

    public abstract EWAHCompressedBitmap ofObjectType(EWAHCompressedBitmap eWAHCompressedBitmap, int i);

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Throwable] */
    public static PackBitmapIndex open(File file, PackIndex packIndex, PackReverseIndex packReverseIndex) throws IOException {
        ?? r0 = 0;
        r0 = 0;
        r0 = 0;
        try {
            SilentFileInputStream silentFileInputStream = new SilentFileInputStream(file);
            try {
                try {
                    PackBitmapIndex packBitmapIndex = read(silentFileInputStream, packIndex, packReverseIndex);
                    silentFileInputStream.close();
                    return packBitmapIndex;
                } catch (IOException e) {
                    r0 = JGitText.get().unreadablePackIndex;
                    throw new IOException(MessageFormat.format(r0, file.getAbsolutePath()), e);
                }
            } catch (Throwable th) {
                silentFileInputStream.close();
                throw th;
            }
        } catch (Throwable th2) {
            if (r0 == 0) {
                throw th2;
            }
            if (r0 != th2) {
                r0.addSuppressed(th2);
            }
            throw r0;
        }
    }

    public static PackBitmapIndex read(InputStream inputStream, PackIndex packIndex, PackReverseIndex packReverseIndex) throws IOException {
        return new PackBitmapIndexV1(inputStream, packIndex, packReverseIndex);
    }
}
