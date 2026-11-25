package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.base64.Base64;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.util.NetUtil;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLHandshakeException;
import kotlin.jvm.internal.ShortCompanionObject;

/* JADX INFO: loaded from: classes11.dex */
final class SslUtils {
    static final String[] DEFAULT_CIPHER_SUITES;
    static final String[] DEFAULT_TLSV13_CIPHER_SUITES;
    static final String INVALID_CIPHER = "SSL_NULL_WITH_NULL_NULL";
    static final int NOT_ENCRYPTED = -2;
    static final int NOT_ENOUGH_DATA = -1;
    static final String PROTOCOL_SSL_V2 = "SSLv2";
    static final String PROTOCOL_SSL_V2_HELLO = "SSLv2Hello";
    static final String PROTOCOL_SSL_V3 = "SSLv3";
    static final String PROTOCOL_TLS_V1 = "TLSv1";
    static final String PROTOCOL_TLS_V1_1 = "TLSv1.1";
    static final String PROTOCOL_TLS_V1_2 = "TLSv1.2";
    static final String PROTOCOL_TLS_V1_3 = "TLSv1.3";
    static final int SSL_CONTENT_TYPE_ALERT = 21;
    static final int SSL_CONTENT_TYPE_APPLICATION_DATA = 23;
    static final int SSL_CONTENT_TYPE_CHANGE_CIPHER_SPEC = 20;
    static final int SSL_CONTENT_TYPE_EXTENSION_HEARTBEAT = 24;
    static final int SSL_CONTENT_TYPE_HANDSHAKE = 22;
    static final int SSL_RECORD_HEADER_LENGTH = 5;
    static final Set<String> TLSV13_CIPHERS = Collections.unmodifiableSet(new LinkedHashSet(Arrays.asList("TLS_AES_256_GCM_SHA384", "TLS_CHACHA20_POLY1305_SHA256", "TLS_AES_128_GCM_SHA256", "TLS_AES_128_CCM_8_SHA256", "TLS_AES_128_CCM_SHA256")));
    static final String[] TLSV13_CIPHER_SUITES;

    private static short unsignedByte(byte b) {
        return (short) (b & 255);
    }

    static {
        String[] strArr = {"TLS_AES_128_GCM_SHA256", "TLS_AES_256_GCM_SHA384"};
        TLSV13_CIPHER_SUITES = strArr;
        if (PlatformDependent.javaVersion() >= 11) {
            DEFAULT_TLSV13_CIPHER_SUITES = strArr;
        } else {
            DEFAULT_TLSV13_CIPHER_SUITES = EmptyArrays.EMPTY_STRINGS;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA");
        arrayList.add("TLS_RSA_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_RSA_WITH_AES_128_CBC_SHA");
        arrayList.add("TLS_RSA_WITH_AES_256_CBC_SHA");
        Collections.addAll(arrayList, DEFAULT_TLSV13_CIPHER_SUITES);
        DEFAULT_CIPHER_SUITES = (String[]) arrayList.toArray(new String[0]);
    }

    static void addIfSupported(Set<String> set, List<String> list, String... strArr) {
        for (String str : strArr) {
            if (set.contains(str)) {
                list.add(str);
            }
        }
    }

    static void useFallbackCiphersIfDefaultIsEmpty(List<String> list, Iterable<String> iterable) {
        if (list.isEmpty()) {
            for (String str : iterable) {
                if (!str.startsWith("SSL_") && !str.contains("_RC4_")) {
                    list.add(str);
                }
            }
        }
    }

    static void useFallbackCiphersIfDefaultIsEmpty(List<String> list, String... strArr) {
        useFallbackCiphersIfDefaultIsEmpty(list, Arrays.asList(strArr));
    }

    static SSLHandshakeException toSSLHandshakeException(Throwable th) {
        if (th instanceof SSLHandshakeException) {
            return (SSLHandshakeException) th;
        }
        return (SSLHandshakeException) new SSLHandshakeException(th.getMessage()).initCause(th);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0028  */
    /* JADX WARN: Code duplicated, block: B:19:0x0031  */
    /* JADX WARN: Code duplicated, block: B:20:0x0033  */
    /* JADX WARN: Code duplicated, block: B:28:0x0044  */
    /* JADX WARN: Code duplicated, block: B:29:0x004c  */
    /* JADX WARN: Code duplicated, block: B:32:0x0056 A[RETURN] */
    static int getEncryptedPacketLength(ByteBuf byteBuf, int i) {
        boolean z;
        int iUnsignedShortBE;
        int i2;
        short unsignedByte;
        int iShortBE;
        boolean z2 = false;
        switch (byteBuf.getUnsignedByte(i)) {
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            if (byteBuf.getUnsignedByte(i + 1) == 3) {
                iUnsignedShortBE = unsignedShortBE(byteBuf, i + 3) + 5;
                if (iUnsignedShortBE > 5) {
                }
            } else {
                iUnsignedShortBE = 0;
            }
            if (!z2) {
                if ((byteBuf.getUnsignedByte(i) & 128) != 0) {
                    i2 = 2;
                } else {
                    i2 = 3;
                }
                unsignedByte = byteBuf.getUnsignedByte(i + i2 + 1);
                if (unsignedByte == 2 && unsignedByte != 3) {
                    return -2;
                }
                if (i2 == 2) {
                    iShortBE = (shortBE(byteBuf, i) & ShortCompanionObject.MAX_VALUE) + 2;
                } else {
                    iShortBE = (shortBE(byteBuf, i) & 16383) + 3;
                }
                iUnsignedShortBE = iShortBE;
                if (iUnsignedShortBE <= i2) {
                    return -1;
                }
            }
            return iUnsignedShortBE;
        }
        iUnsignedShortBE = 0;
        z2 = z;
        if (!z2) {
            if ((byteBuf.getUnsignedByte(i) & 128) != 0) {
                i2 = 2;
            } else {
                i2 = 3;
            }
            unsignedByte = byteBuf.getUnsignedByte(i + i2 + 1);
            if (unsignedByte == 2) {
            }
            if (i2 == 2) {
                iShortBE = (shortBE(byteBuf, i) & ShortCompanionObject.MAX_VALUE) + 2;
            } else {
                iShortBE = (shortBE(byteBuf, i) & 16383) + 3;
            }
            iUnsignedShortBE = iShortBE;
            if (iUnsignedShortBE <= i2) {
                return -1;
            }
        }
        return iUnsignedShortBE;
    }

    private static int unsignedShortBE(ByteBuf byteBuf, int i) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.getUnsignedShort(i) : byteBuf.getUnsignedShortLE(i);
    }

    private static short shortBE(ByteBuf byteBuf, int i) {
        return byteBuf.order() == ByteOrder.BIG_ENDIAN ? byteBuf.getShort(i) : byteBuf.getShortLE(i);
    }

    private static int unsignedShortBE(ByteBuffer byteBuffer, int i) {
        return shortBE(byteBuffer, i) & 65535;
    }

    private static short shortBE(ByteBuffer byteBuffer, int i) {
        return byteBuffer.order() == ByteOrder.BIG_ENDIAN ? byteBuffer.getShort(i) : ByteBufUtil.swapShort(byteBuffer.getShort(i));
    }

    static int getEncryptedPacketLength(ByteBuffer[] byteBufferArr, int i) {
        ByteBuffer byteBuffer = byteBufferArr[i];
        if (byteBuffer.remaining() >= 5) {
            return getEncryptedPacketLength(byteBuffer);
        }
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(5);
        while (true) {
            int i2 = i + 1;
            ByteBuffer byteBufferDuplicate = byteBufferArr[i].duplicate();
            if (byteBufferDuplicate.remaining() > byteBufferAllocate.remaining()) {
                byteBufferDuplicate.limit(byteBufferDuplicate.position() + byteBufferAllocate.remaining());
            }
            byteBufferAllocate.put(byteBufferDuplicate);
            if (!byteBufferAllocate.hasRemaining()) {
                byteBufferAllocate.flip();
                return getEncryptedPacketLength(byteBufferAllocate);
            }
            i = i2;
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0034  */
    /* JADX WARN: Code duplicated, block: B:19:0x0041  */
    /* JADX WARN: Code duplicated, block: B:20:0x0043  */
    /* JADX WARN: Code duplicated, block: B:28:0x0058  */
    /* JADX WARN: Code duplicated, block: B:29:0x0060  */
    /* JADX WARN: Code duplicated, block: B:32:0x006a A[RETURN] */
    private static int getEncryptedPacketLength(ByteBuffer byteBuffer) {
        boolean z;
        int iUnsignedShortBE;
        int i;
        short sUnsignedByte;
        int iShortBE;
        int iPosition = byteBuffer.position();
        boolean z2 = false;
        switch (unsignedByte(byteBuffer.get(iPosition))) {
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                z = true;
                break;
            default:
                z = false;
                break;
        }
        if (z) {
            if (unsignedByte(byteBuffer.get(iPosition + 1)) == 3) {
                iUnsignedShortBE = unsignedShortBE(byteBuffer, iPosition + 3) + 5;
                if (iUnsignedShortBE > 5) {
                }
            } else {
                iUnsignedShortBE = 0;
            }
            if (!z2) {
                if ((unsignedByte(byteBuffer.get(iPosition)) & 128) != 0) {
                    i = 2;
                } else {
                    i = 3;
                }
                sUnsignedByte = unsignedByte(byteBuffer.get(iPosition + i + 1));
                if (sUnsignedByte == 2 && sUnsignedByte != 3) {
                    return -2;
                }
                if (i == 2) {
                    iShortBE = (shortBE(byteBuffer, iPosition) & ShortCompanionObject.MAX_VALUE) + 2;
                } else {
                    iShortBE = (shortBE(byteBuffer, iPosition) & 16383) + 3;
                }
                iUnsignedShortBE = iShortBE;
                if (iUnsignedShortBE <= i) {
                    return -1;
                }
            }
            return iUnsignedShortBE;
        }
        iUnsignedShortBE = 0;
        z2 = z;
        if (!z2) {
            if ((unsignedByte(byteBuffer.get(iPosition)) & 128) != 0) {
                i = 2;
            } else {
                i = 3;
            }
            sUnsignedByte = unsignedByte(byteBuffer.get(iPosition + i + 1));
            if (sUnsignedByte == 2) {
            }
            if (i == 2) {
                iShortBE = (shortBE(byteBuffer, iPosition) & ShortCompanionObject.MAX_VALUE) + 2;
            } else {
                iShortBE = (shortBE(byteBuffer, iPosition) & 16383) + 3;
            }
            iUnsignedShortBE = iShortBE;
            if (iUnsignedShortBE <= i) {
                return -1;
            }
        }
        return iUnsignedShortBE;
    }

    static void handleHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th, boolean z) {
        channelHandlerContext.flush();
        if (z) {
            channelHandlerContext.fireUserEventTriggered((Object) new SslHandshakeCompletionEvent(th));
        }
        channelHandlerContext.close();
    }

    static void zeroout(ByteBuf byteBuf) {
        if (byteBuf.isReadOnly()) {
            return;
        }
        byteBuf.setZero(0, byteBuf.capacity());
    }

    static void zerooutAndRelease(ByteBuf byteBuf) {
        zeroout(byteBuf);
        byteBuf.release();
    }

    static ByteBuf toBase64(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf) {
        ByteBuf byteBufEncode = Base64.encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), true, Base64Dialect.STANDARD, byteBufAllocator);
        byteBuf.readerIndex(byteBuf.writerIndex());
        return byteBufEncode;
    }

    static boolean isValidHostNameForSNI(String str) {
        return (str == null || str.indexOf(46) <= 0 || str.endsWith(".") || NetUtil.isValidIpV4Address(str) || NetUtil.isValidIpV6Address(str)) ? false : true;
    }

    static boolean isTLSv13Cipher(String str) {
        return TLSV13_CIPHERS.contains(str);
    }

    private SslUtils() {
    }
}
