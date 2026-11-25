package com.android.tools.r8;

import com.android.tools.r8.origin.Origin;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface DataEntryResource extends DataResource {
    static DataEntryResource fromBytes(byte[] bArr, String str, Origin origin) {
        return new C3863t(bArr, str, origin);
    }

    static DataEntryResource fromFile(Path path, Path path2) {
        return new C3864u(path2.toString().replace(File.separatorChar, '/'), path.resolve(path2).toFile());
    }

    static DataEntryResource fromString(String str, Origin origin, String... strArr) {
        StringBuilder sb = new StringBuilder();
        for (String str2 : strArr) {
            sb.append(str2);
            sb.append(System.lineSeparator());
        }
        return new C3863t(sb.toString().getBytes(), str, origin);
    }

    static DataEntryResource fromZip(ZipFile zipFile, ZipEntry zipEntry) {
        return new C4000w(zipFile, zipEntry);
    }

    InputStream getByteStream() throws ResourceException;

    default DataEntryResource withName(String str) {
        return new C3999v(str, this);
    }
}
