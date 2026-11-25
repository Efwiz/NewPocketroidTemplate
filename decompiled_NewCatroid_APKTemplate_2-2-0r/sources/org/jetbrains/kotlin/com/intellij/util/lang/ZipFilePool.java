package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ZipFilePool {
    public static ZipFilePool POOL;

    public interface EntryResolver {
        InputStream loadZipEntry(String str) throws IOException;
    }

    public abstract EntryResolver load(Path path) throws IOException;
}
