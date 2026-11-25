package org.apache.commons.compress.archivers.zip;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.function.Predicate;
import org.apache.commons.compress.utils.CharsetNames;
import org.apache.commons.io.Charsets;

/* JADX INFO: loaded from: classes13.dex */
public abstract class ZipEncodingHelper {
    static final ZipEncoding ZIP_ENCODING_UTF_8 = getZipEncoding(CharsetNames.UTF_8);

    public static ZipEncoding getZipEncoding(Charset charset) {
        Charset charset2 = Charsets.toCharset(charset);
        return new NioZipEncoding(charset2, isUTF8(charset2));
    }

    public static ZipEncoding getZipEncoding(String str) {
        Charset charsetDefaultCharset = Charset.defaultCharset();
        try {
            charsetDefaultCharset = Charsets.toCharset(str);
        } catch (UnsupportedCharsetException unused) {
        }
        return new NioZipEncoding(charsetDefaultCharset, isUTF8(charsetDefaultCharset.name()));
    }

    static ByteBuffer growBufferBy(ByteBuffer byteBuffer, int i) {
        byteBuffer.limit(byteBuffer.position());
        byteBuffer.rewind();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(byteBuffer.capacity() + i);
        byteBufferAllocate.put(byteBuffer);
        return byteBufferAllocate;
    }

    static boolean isUTF8(Charset charset) {
        return isUTF8Alias(Charsets.toCharset(charset).name());
    }

    static boolean isUTF8(String str) {
        if (str == null) {
            str = Charset.defaultCharset().name();
        }
        return isUTF8Alias(str);
    }

    private static boolean isUTF8Alias(final String str) {
        return StandardCharsets.UTF_8.name().equalsIgnoreCase(str) || StandardCharsets.UTF_8.aliases().stream().anyMatch(new Predicate() { // from class: org.apache.commons.compress.archivers.zip.ZipEncodingHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((String) obj).equalsIgnoreCase(str);
            }
        });
    }
}
