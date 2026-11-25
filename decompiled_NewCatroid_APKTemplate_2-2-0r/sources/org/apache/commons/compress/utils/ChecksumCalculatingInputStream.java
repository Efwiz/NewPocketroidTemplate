package org.apache.commons.compress.utils;

import java.io.InputStream;
import java.util.Objects;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;

/* JADX INFO: loaded from: classes13.dex */
@Deprecated
public class ChecksumCalculatingInputStream extends CheckedInputStream {
    @Deprecated
    public ChecksumCalculatingInputStream(Checksum checksum, InputStream inputStream) {
        super((InputStream) Objects.requireNonNull(inputStream, "inputStream"), (Checksum) Objects.requireNonNull(checksum, "checksum"));
    }

    @Deprecated
    public long getValue() {
        return getChecksum().getValue();
    }
}
