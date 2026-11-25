package org.catrobat.catroid.utils;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/PcmToWavConverterKt.class
  classes14.dex
 */
/* JADX INFO: compiled from: PcmToWavConverter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000b\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0003X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"CHUNK_ID", "", "CHUNK_SIZE", "", "FILE_FORMAT", "FIRST_SUBCHUNK_ID", "PCM_AUDIO_FORMAT", "POSITION_OF_BYTE_ONE", "POSITION_OF_BYTE_THREE", "POSITION_OF_BYTE_TWO", "POSITION_OF_BYTE_ZERO", "SECOND_SUBCHUNK_ID", "SUB_CHUNK_SIZE", "TRANSFER_BUFFER_SIZE", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PcmToWavConverterKt {
    private static final String CHUNK_ID = "RIFF";
    private static final int CHUNK_SIZE = 36;
    private static final String FILE_FORMAT = "WAVE";
    private static final String FIRST_SUBCHUNK_ID = "fmt ";
    private static final int PCM_AUDIO_FORMAT = 1;
    private static final int POSITION_OF_BYTE_ONE = 8;
    private static final int POSITION_OF_BYTE_THREE = 24;
    private static final int POSITION_OF_BYTE_TWO = 16;
    private static final int POSITION_OF_BYTE_ZERO = 0;
    private static final String SECOND_SUBCHUNK_ID = "data";
    private static final int SUB_CHUNK_SIZE = 16;
    private static final int TRANSFER_BUFFER_SIZE = 10240;
}
