package org.jetbrains.kotlin.load.java.structure.impl.classFiles;

import com.reandroid.arsc.chunk.SpecBlock;
import kotlin.Metadata;

/* JADX INFO: compiled from: commonMixins.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u0014\u0010\u0002\u001a\u00020\u0003*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"ASM_API_VERSION_FOR_CLASS_READING", "", "isSet", "", SpecBlock.NAME_flag, "resolution.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CommonMixinsKt {
    public static final int ASM_API_VERSION_FOR_CLASS_READING = 589824;

    public static final boolean isSet(int i, int i2) {
        return (i & i2) != 0;
    }
}
