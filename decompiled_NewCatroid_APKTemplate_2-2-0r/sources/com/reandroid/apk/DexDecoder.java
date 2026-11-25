package com.reandroid.apk;

import com.reandroid.utils.ObjectsUtil;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public interface DexDecoder {
    public static final String DEX_DIRECTORY_NAME = DexFileInputSource.DEX_DIRECTORY_NAME;
    public static final String SMALI_DIRECTORY_NAME = ObjectsUtil.of("smali");

    void decodeDex(DexFileInputSource dexFileInputSource, File file) throws IOException;

    default void decodeDex(ApkModule apkModule, File file) throws IOException {
        Iterator<DexFileInputSource> it = apkModule.listDexFiles().iterator();
        while (it.hasNext()) {
            decodeDex(it.next(), file);
        }
    }
}
