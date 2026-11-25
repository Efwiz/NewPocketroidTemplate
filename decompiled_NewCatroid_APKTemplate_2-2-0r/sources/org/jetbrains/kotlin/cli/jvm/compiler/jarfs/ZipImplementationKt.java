package org.jetbrains.kotlin.cli.jvm.compiler.jarfs;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* JADX INFO: compiled from: ZipImplementation.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u0014\u0010\n\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0001H\u0002\u001a\u0010\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u000e*\u00020\u0007\u001a\u0018\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u0010*\u00020\u0007H\u0002\u001a\u0018\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00010\u0010*\u00020\u0007H\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"END_OF_CENTRAL_DIR_SIZE", "", "END_OF_CENTRAL_DIR_ZIP64_SIZE", "LOCAL_FILE_HEADER_EXTRA_OFFSET", "LOCAL_FILE_HEADER_SIZE", "contentsToByteArray", "", "Ljava/nio/MappedByteBuffer;", "zipEntryDescription", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/ZipEntryDescription;", "getUnsignedShort", "Ljava/nio/ByteBuffer;", TypedValues.CycleType.S_WAVE_OFFSET, "parseCentralDirectory", "", "parseCentralDirectoryRecordsNumberAndOffset", "Lkotlin/Pair;", "parseZip64CentralDirectoryRecordsNumberAndOffset", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ZipImplementationKt {

    /* JADX INFO: compiled from: ZipImplementation.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ZipEntryDescription.CompressionKind.values().length];
            try {
                iArr[ZipEntryDescription.CompressionKind.DEFLATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ZipEntryDescription.CompressionKind.PLAIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final byte[] contentsToByteArray(MappedByteBuffer mappedByteBuffer, ZipEntryDescription zipEntryDescription) throws DataFormatException {
        Intrinsics.checkNotNullParameter(mappedByteBuffer, "<this>");
        Intrinsics.checkNotNullParameter(zipEntryDescription, "zipEntryDescription");
        mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        mappedByteBuffer.position(zipEntryDescription.getOffsetInFile() + 30 + zipEntryDescription.getFileNameSize() + getUnsignedShort(mappedByteBuffer, zipEntryDescription.getOffsetInFile() + 28));
        byte[] bArr = new byte[zipEntryDescription.getCompressedSize() + 1];
        mappedByteBuffer.get(bArr, 0, zipEntryDescription.getCompressedSize());
        int i = WhenMappings.$EnumSwitchMapping$0[zipEntryDescription.getCompressionKind().ordinal()];
        if (i != 1) {
            if (i != 2) {
                throw new NoWhenBranchMatchedException();
            }
            byte[] bArrCopyOf = Arrays.copyOf(bArr, zipEntryDescription.getCompressedSize());
            Intrinsics.checkNotNullExpressionValue(bArrCopyOf, "copyOf(...)");
            return bArrCopyOf;
        }
        Inflater inflater = new Inflater(true);
        inflater.setInput(bArr, 0, zipEntryDescription.getCompressedSize());
        byte[] bArr2 = new byte[zipEntryDescription.getUncompressedSize()];
        inflater.inflate(bArr2);
        return bArr2;
    }

    public static final List<ZipEntryDescription> parseCentralDirectory(MappedByteBuffer mappedByteBuffer) {
        CharSequence byteArrayCharSequence;
        ZipEntryDescription.CompressionKind compressionKind;
        Intrinsics.checkNotNullParameter(mappedByteBuffer, "<this>");
        mappedByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        Pair<Integer, Integer> centralDirectoryRecordsNumberAndOffset = parseCentralDirectoryRecordsNumberAndOffset(mappedByteBuffer);
        int iIntValue = centralDirectoryRecordsNumberAndOffset.component1().intValue();
        int iIntValue2 = centralDirectoryRecordsNumberAndOffset.component2().intValue();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < iIntValue; i++) {
            int i2 = mappedByteBuffer.getInt(iIntValue2);
            if (i2 != 33639248) {
                throw new IllegalArgumentException((i + ": " + i2).toString());
            }
            short s = mappedByteBuffer.getShort(iIntValue2 + 6);
            short s2 = mappedByteBuffer.getShort(iIntValue2 + 10);
            int i3 = mappedByteBuffer.getInt(iIntValue2 + 20);
            int i4 = mappedByteBuffer.getInt(iIntValue2 + 24);
            MappedByteBuffer mappedByteBuffer2 = mappedByteBuffer;
            int unsignedShort = getUnsignedShort(mappedByteBuffer2, iIntValue2 + 28);
            int unsignedShort2 = getUnsignedShort(mappedByteBuffer2, iIntValue2 + 30);
            int unsignedShort3 = getUnsignedShort(mappedByteBuffer2, iIntValue2 + 32);
            int i5 = mappedByteBuffer.getInt(iIntValue2 + 42);
            byte[] bArr = new byte[unsignedShort];
            mappedByteBuffer.position(iIntValue2 + 46);
            mappedByteBuffer.get(bArr);
            int i6 = 0;
            while (true) {
                if (i6 >= unsignedShort) {
                    byteArrayCharSequence = new ByteArrayCharSequence(bArr, 0, 0, 6, null);
                    break;
                }
                if (bArr[i6] < 0) {
                    byteArrayCharSequence = new String(bArr, Charsets.UTF_8);
                    break;
                }
                i6++;
            }
            iIntValue2 += unsignedShort + 46 + unsignedShort2 + unsignedShort3;
            if (s < 0 || s >= 21) {
                throw new IllegalArgumentException(("Unexpected versionNeededToExtract (" + ((int) s) + ") at " + ((Object) byteArrayCharSequence)).toString());
            }
            if (s2 == 0) {
                compressionKind = ZipEntryDescription.CompressionKind.PLAIN;
            } else {
                if (s2 != 8) {
                    throw new IllegalStateException(("Unexpected compression method (" + ((int) s2) + ") at " + ((Object) byteArrayCharSequence)).toString());
                }
                compressionKind = ZipEntryDescription.CompressionKind.DEFLATE;
            }
            arrayList.add(new ZipEntryDescription(byteArrayCharSequence, i3, i4, i5, compressionKind, unsignedShort));
        }
        return arrayList;
    }

    private static final Pair<Integer, Integer> parseCentralDirectoryRecordsNumberAndOffset(MappedByteBuffer mappedByteBuffer) {
        int iCapacity = mappedByteBuffer.capacity() - 22;
        while (iCapacity >= 0 && mappedByteBuffer.getInt(iCapacity) != 101010256) {
            iCapacity--;
        }
        int unsignedShort = getUnsignedShort(mappedByteBuffer, iCapacity + 10);
        int i = mappedByteBuffer.getInt(iCapacity + 16);
        if (unsignedShort == 65535 || i == -1) {
            return parseZip64CentralDirectoryRecordsNumberAndOffset(mappedByteBuffer);
        }
        return new Pair<>(Integer.valueOf(unsignedShort), Integer.valueOf(i));
    }

    private static final Pair<Integer, Integer> parseZip64CentralDirectoryRecordsNumberAndOffset(MappedByteBuffer mappedByteBuffer) {
        int iCapacity = mappedByteBuffer.capacity() - 56;
        while (iCapacity >= 0 && mappedByteBuffer.getInt(iCapacity) != 101075792) {
            iCapacity--;
        }
        long j = mappedByteBuffer.getLong(iCapacity + 32);
        long j2 = mappedByteBuffer.getLong(iCapacity + 48);
        if (j > 2147483647L) {
            throw new IllegalArgumentException(("Jar " + j + " entries number equal or more than 2147483647 is not supported by FastJarFS").toString());
        }
        if (j2 > 2147483647L) {
            throw new IllegalArgumentException(("Jar " + j2 + " offset equal or more than 2147483647 is not supported by FastJarFS").toString());
        }
        return new Pair<>(Integer.valueOf((int) j), Integer.valueOf((int) j2));
    }

    private static final int getUnsignedShort(ByteBuffer byteBuffer, int i) {
        return Short.toUnsignedInt(byteBuffer.getShort(i));
    }
}
