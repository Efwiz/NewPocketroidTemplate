package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import java.io.File;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface DataDirectoryResource extends DataResource {
    static DataDirectoryResource fromFile(Path path, Path path2) {
        return new C3676q(path2.toString().replace(File.separatorChar, '/'), path.resolve(path2).toFile());
    }

    static DataDirectoryResource fromName(String str, Origin origin) {
        return new r(str, origin);
    }

    static DataDirectoryResource fromZip(ZipFile zipFile, ZipEntry zipEntry) {
        return new C3677s(zipFile, zipEntry);
    }
}
