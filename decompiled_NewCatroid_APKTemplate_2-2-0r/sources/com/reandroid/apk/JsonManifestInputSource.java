package com.reandroid.apk;

import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import java.io.File;

/* JADX INFO: loaded from: classes10.dex */
public class JsonManifestInputSource extends JsonXmlInputSource {
    public JsonManifestInputSource(InputSource inputSource) {
        super(inputSource);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.apk.JsonXmlInputSource
    public AndroidManifestBlock newInstance() {
        return new AndroidManifestBlock();
    }

    public static JsonManifestInputSource fromFile(File file, File file2) {
        return new JsonManifestInputSource(new FileInputSource(file2, ApkUtil.jsonToArchiveResourcePath(file, file2)));
    }
}
