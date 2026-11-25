package org.eclipse.jdt.internal.compiler.apt.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* JADX INFO: loaded from: classes14.dex */
public class Archive {
    public static final Archive UNKNOWN_ARCHIVE = new Archive();
    File file;
    protected Hashtable<String, ArrayList<String>> packagesCache;
    ZipFile zipFile;

    private Archive() {
    }

    public Archive(File file) throws IOException {
        this.file = file;
        this.zipFile = new ZipFile(file);
        initialize();
    }

    private void initialize() {
        this.packagesCache = new Hashtable<>();
        Enumeration<? extends ZipEntry> enumerationEntries = this.zipFile.entries();
        while (enumerationEntries.hasMoreElements()) {
            String name2 = enumerationEntries.nextElement().getName();
            int iLastIndexOf = name2.lastIndexOf(47) + 1;
            String strSubstring = name2.substring(0, iLastIndexOf);
            String strSubstring2 = name2.substring(iLastIndexOf);
            ArrayList<String> arrayList = this.packagesCache.get(strSubstring);
            if (arrayList == null) {
                if (strSubstring2.length() != 0) {
                    ArrayList<String> arrayList2 = new ArrayList<>();
                    arrayList2.add(strSubstring2);
                    this.packagesCache.put(strSubstring, arrayList2);
                }
            } else {
                arrayList.add(strSubstring2);
            }
        }
    }

    public ArchiveFileObject getArchiveFileObject(String str, Charset charset) {
        return new ArchiveFileObject(this.file, str, charset);
    }

    public boolean contains(String str) {
        return this.zipFile.getEntry(str) != null;
    }

    public Set<String> allPackages() {
        if (this.packagesCache == null) {
            initialize();
        }
        return this.packagesCache.keySet();
    }

    public List<String> getTypes(String str) {
        if (this.packagesCache == null) {
            try {
                this.zipFile = new ZipFile(this.file);
                initialize();
            } catch (IOException unused) {
                return Collections.emptyList();
            }
        }
        return this.packagesCache.get(str);
    }

    public void flush() {
        this.packagesCache = null;
    }

    public void close() {
        try {
            ZipFile zipFile = this.zipFile;
            if (zipFile != null) {
                zipFile.close();
            }
            this.packagesCache = null;
        } catch (IOException unused) {
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Archive: ");
        File file = this.file;
        return sb.append(file == null ? "UNKNOWN_ARCHIVE" : file.getAbsolutePath()).toString();
    }
}
