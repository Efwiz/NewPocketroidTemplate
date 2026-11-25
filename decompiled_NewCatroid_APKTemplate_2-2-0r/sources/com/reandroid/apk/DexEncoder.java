package com.reandroid.apk;

import com.reandroid.archive.InputSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface DexEncoder {
    List<InputSource> buildDexFiles(ApkModuleEncoder apkModuleEncoder, File file) throws IOException;
}
