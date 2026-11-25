package org.apache.commons.compress.archivers.sevenz;

import java.util.Arrays;
import org.billthefarmer.mididriver.MidiConstants;

/* JADX INFO: loaded from: classes13.dex */
public enum SevenZMethod {
    COPY(new byte[]{0}),
    LZMA(new byte[]{3, 1, 1}),
    LZMA2(new byte[]{33}),
    DEFLATE(new byte[]{4, 1, 8}),
    DEFLATE64(new byte[]{4, 1, 9}),
    BZIP2(new byte[]{4, 2, 2}),
    AES256SHA256(new byte[]{6, MidiConstants.MIDI_TIME_CODE, 7, 1}),
    BCJ_X86_FILTER(new byte[]{3, 3, 1, 3}),
    BCJ_PPC_FILTER(new byte[]{3, 3, 2, 5}),
    BCJ_IA64_FILTER(new byte[]{3, 3, 4, 1}),
    BCJ_ARM_FILTER(new byte[]{3, 3, 5, 1}),
    BCJ_ARM_THUMB_FILTER(new byte[]{3, 3, 7, 1}),
    BCJ_SPARC_FILTER(new byte[]{3, 3, 8, 5}),
    DELTA_FILTER(new byte[]{3});


    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final byte[] f443id;

    static SevenZMethod byId(byte[] bArr) {
        for (SevenZMethod sevenZMethod : (SevenZMethod[]) SevenZMethod.class.getEnumConstants()) {
            if (Arrays.equals(sevenZMethod.f443id, bArr)) {
                return sevenZMethod;
            }
        }
        return null;
    }

    SevenZMethod(byte[] bArr) {
        this.f443id = bArr;
    }

    byte[] getId() {
        byte[] bArr = this.f443id;
        return Arrays.copyOf(bArr, bArr.length);
    }
}
