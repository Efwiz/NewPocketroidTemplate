package org.jetbrains.kotlin.backend.common.serialization;

import com.android.dx.rop.code.RegisterSpec;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.UStringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: compiled from: CityHash.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a\u0018\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u0001ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\"\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u001a*\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u001a'\u0010\u0014\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010¢\u0006\u0002\u0010\u0015\u001a,\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0010H\u0002\u001a\u001d\u0010\u0017\u001a\u00020\u00182\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u0019\u001a\u001d\u0010\u001a\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u001b\u001a\"\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010 \u001a%\u0010!\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u0015\u001a\"\u0010\"\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b#\u0010 \u001a*\u0010\"\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b%\u0010&\u001a%\u0010'\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u0015\u001a%\u0010(\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0002¢\u0006\u0002\u0010\u0015\u001a\"\u0010)\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00012\u0006\u0010*\u001a\u00020\u0010H\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,\u001a\u001a\u0010-\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010\n\u001a\u0018\u0010/\u001a\u00020\u00102\u0006\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u00020\u0010H\u0002\u001a\u0018\u00102\u001a\u0002032\u0006\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u00020\u0010H\u0002\u001a2\u00104\u001a\u0002052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u00106\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b7\u00108\u001aB\u00104\u001a\u0002052\u0006\u00109\u001a\u00020\u00012\u0006\u0010:\u001a\u00020\u00012\u0006\u0010;\u001a\u00020\u00012\u0006\u0010<\u001a\u00020\u00012\u0006\u00106\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b=\u0010>\u001a\n\u0010\u0014\u001a\u000203*\u00020?\u001a\n\u0010@\u001a\u00020?*\u00020?\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082D¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0003\u001a\u00020\u0001X\u0082D¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0004\u001a\u00020\u0001X\u0082D¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0005\u001a\u00020\u0001X\u0082D¢\u0006\u0004\n\u0002\u0010\u0002\"\u0010\u0010\u0006\u001a\u00020\u0001X\u0082D¢\u0006\u0004\n\u0002\u0010\u0002\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006A"}, d2 = {"k0", "Lkotlin/ULong;", "J", "k1", "k2", "kGoldenRatio", "kMul", "bswap", "value", "bswap-VKZWuLQ", "(J)J", "cityHash128", "Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "s", "", "pos", "", "len", "cityHash128WithSeed", "seed", "cityHash64", "([BII)J", "cityMurmur", "fetch32", "Lkotlin/UInt;", "([BI)I", "fetch64", "([BI)J", "hash128to64", "u", RegisterSpec.PREFIX, "hash128to64-PWzV0Is", "(JJ)J", "hashLen0to16", "hashLen16", "hashLen16-PWzV0Is", "mul", "hashLen16-nJd8lh8", "(JJJ)J", "hashLen17to32", "hashLen33to64", "rotate", "shift", "rotate-4PLdz1A", "(JI)J", "shiftMix", "shiftMix-VKZWuLQ", "toIntLE", JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "toLongLE", "", "weakHashLen32WithSeeds", "Lkotlin/ULongArray;", "a", "weakHashLen32WithSeeds-RQJlUXk", "([BIJJ)[J", "w", "x", "y", CompressorStreamFactory.Z, "weakHashLen32WithSeeds-8jibXXU", "(JJJJJJ)[J", "", "cityHash64String", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CityHashKt {
    private static final long k0 = -4348849565147123417L;
    private static final long k1 = -5435081209227447693L;
    private static final long k2 = -7286425919675154353L;
    private static final long kGoldenRatio = -7046029254386353131L;
    private static final long kMul = -7070675565921424023L;

    private static final long toLongLE(byte[] bArr, int i) {
        return (((long) bArr[i + 7]) << 56) + (((long) (bArr[i + 6] & 255)) << 48) + (((long) (bArr[i + 5] & 255)) << 40) + (((long) (bArr[i + 4] & 255)) << 32) + (((long) (bArr[i + 3] & 255)) << 24) + ((long) ((bArr[i + 2] & 255) << 16)) + ((long) ((bArr[i + 1] & 255) << 8)) + ((long) (bArr[i] & 255));
    }

    private static final int toIntLE(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) + ((bArr[i + 2] & 255) << 16) + ((bArr[i + 1] & 255) << 8) + (bArr[i] & 255);
    }

    private static final long fetch64(byte[] bArr, int i) {
        return ULong.m3072constructorimpl(toLongLE(bArr, i));
    }

    private static final int fetch32(byte[] bArr, int i) {
        return UInt.m2993constructorimpl(toIntLE(bArr, i));
    }

    /* JADX INFO: renamed from: rotate-4PLdz1A, reason: not valid java name */
    private static final long m6118rotate4PLdz1A(long j, int i) {
        if (i == 0) {
            return j;
        }
        return ULong.m3072constructorimpl(ULong.m3072constructorimpl(j << (64 - i)) | ULong.m3072constructorimpl(j >>> i));
    }

    /* JADX INFO: renamed from: shiftMix-VKZWuLQ, reason: not valid java name */
    private static final long m6119shiftMixVKZWuLQ(long j) {
        return ULong.m3072constructorimpl(j ^ ULong.m3072constructorimpl(j >>> 47));
    }

    /* JADX INFO: renamed from: hashLen16-PWzV0Is, reason: not valid java name */
    private static final long m6116hashLen16PWzV0Is(long j, long j2) {
        return m6115hash128to64PWzV0Is(j, j2);
    }

    /* JADX INFO: renamed from: hashLen16-nJd8lh8, reason: not valid java name */
    private static final long m6117hashLen16nJd8lh8(long j, long j2, long j3) {
        long jM3072constructorimpl = ULong.m3072constructorimpl(ULong.m3072constructorimpl(j ^ j2) * j3);
        long jM3072constructorimpl2 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl ^ ULong.m3072constructorimpl(jM3072constructorimpl >>> 47)) ^ j2) * j3);
        return ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl2 ^ ULong.m3072constructorimpl(jM3072constructorimpl2 >>> 47)) * j3);
    }

    /* JADX INFO: renamed from: hash128to64-PWzV0Is, reason: not valid java name */
    private static final long m6115hash128to64PWzV0Is(long j, long j2) {
        long jM3072constructorimpl = ULong.m3072constructorimpl(j ^ j2);
        long j3 = kMul;
        long jM3072constructorimpl2 = ULong.m3072constructorimpl(jM3072constructorimpl * j3);
        long jM3072constructorimpl3 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl2 ^ ULong.m3072constructorimpl(jM3072constructorimpl2 >>> 47)) ^ j2) * j3);
        return ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl3 ^ ULong.m3072constructorimpl(jM3072constructorimpl3 >>> 47)) * j3);
    }

    private static final long hashLen0to16(byte[] bArr, int i, int i2) {
        if (i2 >= 8) {
            long j = k2;
            long jM3072constructorimpl = ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(i2) * ULong.m3072constructorimpl(((long) 2) & 4294967295L)) + j);
            long jM3072constructorimpl2 = ULong.m3072constructorimpl(fetch64(bArr, i) + j);
            long jFetch64 = fetch64(bArr, (i + i2) - 8);
            return m6117hashLen16nJd8lh8(ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6118rotate4PLdz1A(jFetch64, 37) * jM3072constructorimpl) + jM3072constructorimpl2), ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6118rotate4PLdz1A(jM3072constructorimpl2, 25) + jFetch64) * jM3072constructorimpl), jM3072constructorimpl);
        }
        if (i2 >= 4) {
            return m6117hashLen16nJd8lh8(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) fetch32(bArr, i)) & 4294967295L) << 3) + ULong.m3072constructorimpl(((long) UInt.m2993constructorimpl(i2)) & 4294967295L)), ULong.m3072constructorimpl(((long) fetch32(bArr, (i + i2) - 4)) & 4294967295L), ULong.m3072constructorimpl(k2 + ULong.m3072constructorimpl(ULong.m3072constructorimpl(i2) * ULong.m3072constructorimpl(((long) 2) & 4294967295L))));
        }
        if (i2 > 0) {
            byte bM2916constructorimpl = UByte.m2916constructorimpl(bArr[i]);
            byte bM2916constructorimpl2 = UByte.m2916constructorimpl(bArr[(i2 >>> 1) + i]);
            byte bM2916constructorimpl3 = UByte.m2916constructorimpl(bArr[(i + i2) - 1]);
            int iM2993constructorimpl = UInt.m2993constructorimpl(UInt.m2993constructorimpl(bM2916constructorimpl & 255) + UInt.m2993constructorimpl(UInt.m2993constructorimpl(bM2916constructorimpl2 & 255) << 8));
            long jM3072constructorimpl3 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(i2) + ULong.m3072constructorimpl(((long) UInt.m2993constructorimpl(UInt.m2993constructorimpl(bM2916constructorimpl3 & 255) << 2)) & 4294967295L));
            long j2 = k2;
            return ULong.m3072constructorimpl(m6119shiftMixVKZWuLQ(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) iM2993constructorimpl) & 4294967295L) * j2) ^ ULong.m3072constructorimpl(jM3072constructorimpl3 * k0))) * j2);
        }
        return k2;
    }

    private static final long hashLen17to32(byte[] bArr, int i, int i2) {
        long j = k2;
        long jM3072constructorimpl = ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) UInt.m2993constructorimpl(i2 * 2)) & 4294967295L) + j);
        long jM3072constructorimpl2 = ULong.m3072constructorimpl(fetch64(bArr, i) * k1);
        long jFetch64 = fetch64(bArr, i + 8);
        int i3 = i + i2;
        long jM3072constructorimpl3 = ULong.m3072constructorimpl(fetch64(bArr, i3 - 8) * jM3072constructorimpl);
        return m6117hashLen16nJd8lh8(ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(jM3072constructorimpl2 + jFetch64), 43) + m6118rotate4PLdz1A(jM3072constructorimpl3, 30)) + ULong.m3072constructorimpl(fetch64(bArr, i3 - 16) * j)), ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl2 + m6118rotate4PLdz1A(ULong.m3072constructorimpl(jFetch64 + j), 18)) + jM3072constructorimpl3), jM3072constructorimpl);
    }

    /* JADX INFO: renamed from: weakHashLen32WithSeeds-8jibXXU, reason: not valid java name */
    private static final long[] m6120weakHashLen32WithSeeds8jibXXU(long j, long j2, long j3, long j4, long j5, long j6) {
        long jM3072constructorimpl = ULong.m3072constructorimpl(j5 + j);
        long jM6118rotate4PLdz1A = m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j6 + jM3072constructorimpl) + j4), 21);
        long jM3072constructorimpl2 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(j2 + jM3072constructorimpl) + j3);
        return new long[]{ULong.m3072constructorimpl(jM3072constructorimpl2 + j4), ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM6118rotate4PLdz1A + m6118rotate4PLdz1A(jM3072constructorimpl2, 44)) + jM3072constructorimpl)};
    }

    /* JADX INFO: renamed from: weakHashLen32WithSeeds-RQJlUXk, reason: not valid java name */
    private static final long[] m6121weakHashLen32WithSeedsRQJlUXk(byte[] bArr, int i, long j, long j2) {
        return m6120weakHashLen32WithSeeds8jibXXU(fetch64(bArr, i), fetch64(bArr, i + 8), fetch64(bArr, i + 16), fetch64(bArr, i + 24), j, j2);
    }

    /* JADX INFO: renamed from: bswap-VKZWuLQ, reason: not valid java name */
    public static final long m6114bswapVKZWuLQ(long j) {
        return ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j) & 255) << 56) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j >>> 8) & 255) << 48)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j >>> 16) & 255) << 40)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j >>> 24) & 255) << 32)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j >>> 32) & 255) << 24)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j >>> 40) & 255) << 16)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j >>> 48) & 255) << 8)) | ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j >>> 56) & 255)));
    }

    private static final long hashLen33to64(byte[] bArr, int i, int i2) {
        long j = k2;
        long jM3072constructorimpl = ULong.m3072constructorimpl(ULong.m3072constructorimpl(((long) UInt.m2993constructorimpl(i2 * 2)) & 4294967295L) + j);
        long jM3072constructorimpl2 = ULong.m3072constructorimpl(fetch64(bArr, i) * j);
        long jFetch64 = fetch64(bArr, i + 8);
        int i3 = i + i2;
        long jFetch65 = fetch64(bArr, i3 - 24);
        long jFetch66 = fetch64(bArr, i3 - 32);
        long jM3072constructorimpl3 = ULong.m3072constructorimpl(fetch64(bArr, i + 16) * j);
        long j2 = ((long) 9) & 4294967295L;
        long jM3072constructorimpl4 = ULong.m3072constructorimpl(fetch64(bArr, i + 24) * ULong.m3072constructorimpl(j2));
        long jFetch67 = fetch64(bArr, i3 - 8);
        long jM3072constructorimpl5 = ULong.m3072constructorimpl(fetch64(bArr, i3 - 16) * jM3072constructorimpl);
        long j3 = jM3072constructorimpl2 + jFetch67;
        long jM3072constructorimpl6 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(j3), 43) + ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6118rotate4PLdz1A(jFetch64, 30) + jFetch65) * ULong.m3072constructorimpl(j2)));
        long jM3072constructorimpl7 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j3) ^ jFetch66) + jM3072constructorimpl4) + ULong.m3072constructorimpl(1L));
        long jM3072constructorimpl8 = ULong.m3072constructorimpl(m6114bswapVKZWuLQ(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl6 + jM3072constructorimpl7) * jM3072constructorimpl)) + jM3072constructorimpl5);
        long j4 = jM3072constructorimpl3 + jM3072constructorimpl4;
        long jM3072constructorimpl9 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(j4), 42) + jFetch65);
        long jM3072constructorimpl10 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6114bswapVKZWuLQ(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl7 + jM3072constructorimpl8) * jM3072constructorimpl)) + jFetch67) * jM3072constructorimpl);
        long jM3072constructorimpl11 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(j4) + jFetch65);
        return ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6119shiftMixVKZWuLQ(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl11 + ULong.m3072constructorimpl(m6114bswapVKZWuLQ(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl9 + jM3072constructorimpl11) * jM3072constructorimpl) + jM3072constructorimpl10)) + jFetch64)) * jM3072constructorimpl) + jFetch66) + jM3072constructorimpl5)) * jM3072constructorimpl) + jM3072constructorimpl9);
    }

    public static /* synthetic */ long cityHash64$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return cityHash64(bArr, i, i2);
    }

    public static final long cityHash64(byte[] s, int i, int i2) {
        Intrinsics.checkNotNullParameter(s, "s");
        if (i2 <= 32) {
            if (i2 <= 16) {
                return hashLen0to16(s, i, i2);
            }
            return hashLen17to32(s, i, i2);
        }
        if (i2 <= 64) {
            return hashLen33to64(s, i, i2);
        }
        int i3 = i + i2;
        long jFetch64 = fetch64(s, i3 - 40);
        long jM3072constructorimpl = ULong.m3072constructorimpl(fetch64(s, i3 - 16) + fetch64(s, i3 - 56));
        long jM6116hashLen16PWzV0Is = m6116hashLen16PWzV0Is(ULong.m3072constructorimpl(fetch64(s, i3 - 48) + ULong.m3072constructorimpl(((long) UInt.m2993constructorimpl(i2)) & 4294967295L)), fetch64(s, i3 - 24));
        long[] jArrM6121weakHashLen32WithSeedsRQJlUXk = m6121weakHashLen32WithSeedsRQJlUXk(s, i3 - 64, ULong.m3072constructorimpl(i2), jM6116hashLen16PWzV0Is);
        long j = k1;
        long[] jArrM6121weakHashLen32WithSeedsRQJlUXk2 = m6121weakHashLen32WithSeedsRQJlUXk(s, i3 - 32, ULong.m3072constructorimpl(jM3072constructorimpl + j), jFetch64);
        long jM3072constructorimpl2 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(jFetch64 * j) + fetch64(s, i));
        int i4 = i;
        long[] jArr = jArrM6121weakHashLen32WithSeedsRQJlUXk2;
        int i5 = (i2 - 1) & (-64);
        long[] jArr2 = jArrM6121weakHashLen32WithSeedsRQJlUXk;
        while (true) {
            long jM6118rotate4PLdz1A = m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl2 + jM3072constructorimpl) + ULongArray.m3132getsVKNKU(jArr2, 0)) + fetch64(s, i4 + 8)), 37);
            long j2 = k1;
            long jM3072constructorimpl3 = ULong.m3072constructorimpl(jM6118rotate4PLdz1A * j2);
            long jM3072constructorimpl4 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl + ULongArray.m3132getsVKNKU(jArr2, 1)) + fetch64(s, i4 + 48)), 42) * j2);
            long jM3072constructorimpl5 = ULong.m3072constructorimpl(jM3072constructorimpl3 ^ ULongArray.m3132getsVKNKU(jArr, 1));
            long jM3072constructorimpl6 = ULong.m3072constructorimpl(jM3072constructorimpl4 + ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArr2, 0) + fetch64(s, i4 + 40)));
            long jM3072constructorimpl7 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(jM6116hashLen16PWzV0Is + ULongArray.m3132getsVKNKU(jArr, 0)), 33) * j2);
            long[] jArrM6121weakHashLen32WithSeedsRQJlUXk3 = m6121weakHashLen32WithSeedsRQJlUXk(s, i4, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArr2, 1) * j2), ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArr, 0) + jM3072constructorimpl5));
            long[] jArrM6121weakHashLen32WithSeedsRQJlUXk4 = m6121weakHashLen32WithSeedsRQJlUXk(s, i4 + 32, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArr, 1) + jM3072constructorimpl7), ULong.m3072constructorimpl(jM3072constructorimpl6 + fetch64(s, i4 + 16)));
            i4 += 64;
            i5 -= 64;
            if (i5 == 0) {
                return m6116hashLen16PWzV0Is(ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6116hashLen16PWzV0Is(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk3, 0), ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk4, 0)) + ULong.m3072constructorimpl(m6119shiftMixVKZWuLQ(jM3072constructorimpl6) * j2)) + jM3072constructorimpl5), ULong.m3072constructorimpl(m6116hashLen16PWzV0Is(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk3, 1), ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk4, 1)) + jM3072constructorimpl7));
            }
            jM3072constructorimpl2 = jM3072constructorimpl7;
            jM6116hashLen16PWzV0Is = jM3072constructorimpl5;
            jM3072constructorimpl = jM3072constructorimpl6;
            jArr = jArrM6121weakHashLen32WithSeedsRQJlUXk4;
            jArr2 = jArrM6121weakHashLen32WithSeedsRQJlUXk3;
        }
    }

    public static final long cityHash64(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return cityHash64$default(bytes, 0, 0, 6, null);
    }

    public static final String cityHash64String(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        byte[] bytes = str.getBytes(Charsets.UTF_8);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        return UStringsKt.m4237toStringJSWoG40(cityHash64$default(bytes, 0, 0, 6, null), 36);
    }

    private static final Hash128Bits cityMurmur(Hash128Bits hash128Bits, byte[] bArr, int i, int i2) {
        long jM3072constructorimpl;
        long jM3072constructorimpl2;
        long jM6119shiftMixVKZWuLQ;
        long lowBytes = hash128Bits.getLowBytes();
        long highBytes = hash128Bits.getHighBytes();
        if (i2 <= 16) {
            long j = k1;
            jM3072constructorimpl = ULong.m3072constructorimpl(m6119shiftMixVKZWuLQ(ULong.m3072constructorimpl(lowBytes * j)) * j);
            jM3072constructorimpl2 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(j * highBytes) + hashLen0to16(bArr, i, i2));
            jM6119shiftMixVKZWuLQ = m6119shiftMixVKZWuLQ(ULong.m3072constructorimpl((i2 >= 8 ? fetch64(bArr, i) : jM3072constructorimpl2) + jM3072constructorimpl));
        } else {
            int i3 = i + i2;
            long jM6116hashLen16PWzV0Is = m6116hashLen16PWzV0Is(ULong.m3072constructorimpl(fetch64(bArr, i3 - 8) + k1), lowBytes);
            long jM6116hashLen16PWzV0Is2 = m6116hashLen16PWzV0Is(ULong.m3072constructorimpl(ULong.m3072constructorimpl(i2) + highBytes), ULong.m3072constructorimpl(fetch64(bArr, i3 - 16) + jM6116hashLen16PWzV0Is));
            long jM3072constructorimpl3 = jM6116hashLen16PWzV0Is2;
            long jM3072constructorimpl4 = jM6116hashLen16PWzV0Is;
            long jM3072constructorimpl5 = highBytes;
            long jM3072constructorimpl6 = ULong.m3072constructorimpl(lowBytes + jM6116hashLen16PWzV0Is2);
            int i4 = i2;
            int i5 = i;
            do {
                long jFetch64 = fetch64(bArr, i5);
                long j2 = k1;
                jM3072constructorimpl6 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl6 ^ ULong.m3072constructorimpl(m6119shiftMixVKZWuLQ(ULong.m3072constructorimpl(jFetch64 * j2)) * j2)) * j2);
                jM3072constructorimpl5 = ULong.m3072constructorimpl(jM3072constructorimpl5 ^ jM3072constructorimpl6);
                jM3072constructorimpl4 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl4 ^ ULong.m3072constructorimpl(m6119shiftMixVKZWuLQ(ULong.m3072constructorimpl(fetch64(bArr, i5 + 8) * j2)) * j2)) * j2);
                jM3072constructorimpl3 = ULong.m3072constructorimpl(jM3072constructorimpl3 ^ jM3072constructorimpl4);
                i5 += 16;
                i4 -= 16;
            } while (i4 > 16);
            jM3072constructorimpl = jM3072constructorimpl6;
            highBytes = jM3072constructorimpl5;
            jM3072constructorimpl2 = jM3072constructorimpl4;
            jM6119shiftMixVKZWuLQ = jM3072constructorimpl3;
        }
        long jM6116hashLen16PWzV0Is3 = m6116hashLen16PWzV0Is(jM3072constructorimpl, jM3072constructorimpl2);
        long jM6116hashLen16PWzV0Is4 = m6116hashLen16PWzV0Is(jM6119shiftMixVKZWuLQ, highBytes);
        return new Hash128Bits(ULong.m3072constructorimpl(jM6116hashLen16PWzV0Is3 ^ jM6116hashLen16PWzV0Is4), m6116hashLen16PWzV0Is(jM6116hashLen16PWzV0Is4, jM6116hashLen16PWzV0Is3), null);
    }

    public static /* synthetic */ Hash128Bits cityHash128WithSeed$default(Hash128Bits hash128Bits, byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = bArr.length;
        }
        return cityHash128WithSeed(hash128Bits, bArr, i, i2);
    }

    public static final Hash128Bits cityHash128WithSeed(Hash128Bits seed, byte[] s, int i, int i2) {
        int i3;
        long jM3072constructorimpl;
        long jM3072constructorimpl2;
        long jM3072constructorimpl3;
        long[] jArrM6121weakHashLen32WithSeedsRQJlUXk;
        long[] jArrM6121weakHashLen32WithSeedsRQJlUXk2;
        Intrinsics.checkNotNullParameter(seed, "seed");
        Intrinsics.checkNotNullParameter(s, "s");
        if (i2 < 128) {
            return cityMurmur(seed, s, i, i2);
        }
        long[] jArrM3126constructorimpl = ULongArray.m3126constructorimpl(2);
        long[] jArrM3126constructorimpl2 = ULongArray.m3126constructorimpl(2);
        long lowBytes = seed.getLowBytes();
        long highBytes = seed.getHighBytes();
        long jM3072constructorimpl4 = ULong.m3072constructorimpl(i2);
        long j = k1;
        long jM3072constructorimpl5 = ULong.m3072constructorimpl(jM3072constructorimpl4 * j);
        ULongArray.m3137setk8EXiF4(jArrM3126constructorimpl, 0, ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(highBytes ^ j), 49) * j) + fetch64(s, i)));
        int i4 = 1;
        ULongArray.m3137setk8EXiF4(jArrM3126constructorimpl, 1, ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULongArray.m3132getsVKNKU(jArrM3126constructorimpl, 0), 42) * j) + fetch64(s, i + 8)));
        ULongArray.m3137setk8EXiF4(jArrM3126constructorimpl2, 0, ULong.m3072constructorimpl(ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(highBytes + jM3072constructorimpl5), 35) * j) + lowBytes));
        ULongArray.m3137setk8EXiF4(jArrM3126constructorimpl2, 1, ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(fetch64(s, i + 88) + lowBytes), 53) * j));
        int i5 = i;
        int i6 = i2;
        long[] jArr = jArrM3126constructorimpl2;
        long j2 = highBytes;
        while (true) {
            long jM6118rotate4PLdz1A = m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(lowBytes + j2) + ULongArray.m3132getsVKNKU(jArrM3126constructorimpl, 0)) + fetch64(s, i5 + 8)), 37);
            long j3 = k1;
            long jM3072constructorimpl6 = ULong.m3072constructorimpl(jM6118rotate4PLdz1A * j3);
            long jM3072constructorimpl7 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j2 + ULongArray.m3132getsVKNKU(jArrM3126constructorimpl, i4)) + fetch64(s, i5 + 48)), 42) * j3);
            long jM3072constructorimpl8 = ULong.m3072constructorimpl(jM3072constructorimpl6 ^ ULongArray.m3132getsVKNKU(jArr, i4));
            long jM3072constructorimpl9 = ULong.m3072constructorimpl(jM3072constructorimpl7 + ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM3126constructorimpl, 0) + fetch64(s, i5 + 40)));
            long jM3072constructorimpl10 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(jM3072constructorimpl5 + ULongArray.m3132getsVKNKU(jArr, 0)), 33) * j3);
            i3 = i4;
            long[] jArrM6121weakHashLen32WithSeedsRQJlUXk3 = m6121weakHashLen32WithSeedsRQJlUXk(s, i5, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM3126constructorimpl, i4) * j3), ULong.m3072constructorimpl(jM3072constructorimpl8 + ULongArray.m3132getsVKNKU(jArr, 0)));
            long[] jArrM6121weakHashLen32WithSeedsRQJlUXk4 = m6121weakHashLen32WithSeedsRQJlUXk(s, i5 + 32, ULong.m3072constructorimpl(jM3072constructorimpl10 + ULongArray.m3132getsVKNKU(jArr, i3)), ULong.m3072constructorimpl(jM3072constructorimpl9 + fetch64(s, i5 + 16)));
            long jM3072constructorimpl11 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl10 + jM3072constructorimpl9) + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk3, 0)) + fetch64(s, i5 + 72)), 37) * j3);
            long jM3072constructorimpl12 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl9 + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk3, i3)) + fetch64(s, i5 + 112)), 42) * j3);
            jM3072constructorimpl = ULong.m3072constructorimpl(jM3072constructorimpl11 ^ ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk4, i3));
            jM3072constructorimpl2 = ULong.m3072constructorimpl(jM3072constructorimpl12 + ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk3, 0) + fetch64(s, i5 + 104)));
            jM3072constructorimpl3 = ULong.m3072constructorimpl(m6118rotate4PLdz1A(ULong.m3072constructorimpl(jM3072constructorimpl8 + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk4, 0)), 33) * j3);
            jArrM6121weakHashLen32WithSeedsRQJlUXk = m6121weakHashLen32WithSeedsRQJlUXk(s, i5 + 64, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk3, i3) * j3), ULong.m3072constructorimpl(jM3072constructorimpl + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk4, 0)));
            jArrM6121weakHashLen32WithSeedsRQJlUXk2 = m6121weakHashLen32WithSeedsRQJlUXk(s, i5 + 96, ULong.m3072constructorimpl(jM3072constructorimpl3 + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk4, i3)), ULong.m3072constructorimpl(fetch64(s, i5 + 80) + jM3072constructorimpl2));
            i5 += 128;
            i6 -= 128;
            if (i6 < 128) {
                break;
            }
            jArrM3126constructorimpl = jArrM6121weakHashLen32WithSeedsRQJlUXk;
            jArr = jArrM6121weakHashLen32WithSeedsRQJlUXk2;
            j2 = jM3072constructorimpl2;
            i4 = i3;
            lowBytes = jM3072constructorimpl3;
            jM3072constructorimpl5 = jM3072constructorimpl;
        }
        long jM6118rotate4PLdz1A2 = m6118rotate4PLdz1A(ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk, 0) + jM3072constructorimpl), 49);
        long j4 = k0;
        long jM3072constructorimpl13 = ULong.m3072constructorimpl(jM3072constructorimpl3 + ULong.m3072constructorimpl(jM6118rotate4PLdz1A2 * j4));
        long jM3072constructorimpl14 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl2 * j4) + m6118rotate4PLdz1A(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, i3), 37));
        long jM3072constructorimpl15 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM3072constructorimpl * j4) + m6118rotate4PLdz1A(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, 0), 27));
        ULongArray.m3137setk8EXiF4(jArrM6121weakHashLen32WithSeedsRQJlUXk2, 0, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, 0) * 9));
        ULongArray.m3137setk8EXiF4(jArrM6121weakHashLen32WithSeedsRQJlUXk, 0, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk, 0) * j4));
        long j5 = jM3072constructorimpl15;
        long[] jArrM6121weakHashLen32WithSeedsRQJlUXk5 = jArrM6121weakHashLen32WithSeedsRQJlUXk;
        int i7 = 0;
        long j6 = jM3072constructorimpl13;
        while (i7 < i6) {
            i7 += 32;
            long jM6118rotate4PLdz1A3 = m6118rotate4PLdz1A(ULong.m3072constructorimpl(jM3072constructorimpl14 + j6), 42);
            long j7 = k0;
            long jM3072constructorimpl16 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(jM6118rotate4PLdz1A3 * j7) + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, i3));
            int i8 = (i5 + i6) - i7;
            ULongArray.m3137setk8EXiF4(jArrM6121weakHashLen32WithSeedsRQJlUXk2, 0, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, 0) + fetch64(s, i8 + 16)));
            long jM3072constructorimpl17 = ULong.m3072constructorimpl(ULong.m3072constructorimpl(j6 * j7) + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, 0));
            long jM3072constructorimpl18 = ULong.m3072constructorimpl(j5 + ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, i3) + fetch64(s, i8)));
            ULongArray.m3137setk8EXiF4(jArrM6121weakHashLen32WithSeedsRQJlUXk2, i3, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, i3) + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, 0)));
            jArrM6121weakHashLen32WithSeedsRQJlUXk5 = m6121weakHashLen32WithSeedsRQJlUXk(s, i8, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, 0) + jM3072constructorimpl18), ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, i3));
            ULongArray.m3137setk8EXiF4(jArrM6121weakHashLen32WithSeedsRQJlUXk5, 0, ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, 0) * j7));
            jM3072constructorimpl14 = jM3072constructorimpl16;
            j6 = jM3072constructorimpl17;
            j5 = jM3072constructorimpl18;
        }
        long jM6116hashLen16PWzV0Is = m6116hashLen16PWzV0Is(j6, ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, 0));
        long jM6116hashLen16PWzV0Is2 = m6116hashLen16PWzV0Is(ULong.m3072constructorimpl(jM3072constructorimpl14 + j5), ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, 0));
        return new Hash128Bits(ULong.m3072constructorimpl(m6116hashLen16PWzV0Is(ULong.m3072constructorimpl(ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, i3) + jM6116hashLen16PWzV0Is), ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, i3)) + jM6116hashLen16PWzV0Is2), m6116hashLen16PWzV0Is(ULong.m3072constructorimpl(jM6116hashLen16PWzV0Is + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk2, i3)), ULong.m3072constructorimpl(jM6116hashLen16PWzV0Is2 + ULongArray.m3132getsVKNKU(jArrM6121weakHashLen32WithSeedsRQJlUXk5, i3))), null);
    }

    public static /* synthetic */ Hash128Bits cityHash128$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return cityHash128(bArr, i, i2);
    }

    public static final Hash128Bits cityHash128(byte[] s, int i, int i2) {
        Intrinsics.checkNotNullParameter(s, "s");
        if (i2 >= 16) {
            return cityHash128WithSeed(new Hash128Bits(fetch64(s, i), ULong.m3072constructorimpl(fetch64(s, i + 8) + k0), null), s, i + 16, i2 - 16);
        }
        return cityHash128WithSeed(new Hash128Bits(k0, k1, null), s, i, i2);
    }
}
