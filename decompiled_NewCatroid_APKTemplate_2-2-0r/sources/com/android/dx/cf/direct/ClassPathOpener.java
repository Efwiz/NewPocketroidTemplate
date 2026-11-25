package com.android.dx.cf.direct;

import com.android.dex.util.FileUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: loaded from: classes.dex */
public class ClassPathOpener {
    public static final FileNameFilter acceptAll = new FileNameFilter() { // from class: com.android.dx.cf.direct.ClassPathOpener.1
        @Override // com.android.dx.cf.direct.ClassPathOpener.FileNameFilter
        public boolean accept(String str) {
            return true;
        }
    };
    private final Consumer consumer;
    private FileNameFilter filter;
    private final String pathname;
    private final boolean sort;

    public interface Consumer {
        void onException(Exception exc);

        void onProcessArchiveStart(File file);

        boolean processFileBytes(String str, long j, byte[] bArr);
    }

    public interface FileNameFilter {
        boolean accept(String str);
    }

    public ClassPathOpener(String str, boolean z, Consumer consumer) {
        this(str, z, acceptAll, consumer);
    }

    public ClassPathOpener(String str, boolean z, FileNameFilter fileNameFilter, Consumer consumer) {
        this.pathname = str;
        this.sort = z;
        this.consumer = consumer;
        this.filter = fileNameFilter;
    }

    public boolean process() {
        return processOne(new File(this.pathname), true);
    }

    private boolean processOne(File file, boolean z) {
        try {
            if (file.isDirectory()) {
                return processDirectory(file, z);
            }
            String path = file.getPath();
            if (!path.endsWith(Constants.ZIP_EXTENSION) && !path.endsWith(".jar") && !path.endsWith(".apk")) {
                if (!this.filter.accept(path)) {
                    return false;
                }
                return this.consumer.processFileBytes(path, file.lastModified(), FileUtils.readFile(file));
            }
            return processArchive(file);
        } catch (Exception e) {
            this.consumer.onException(e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int compareClassNames(String str, String str2) {
        return str.replace('$', ExternalAnnotationProvider.NULLABLE).replace("package-info", "").compareTo(str2.replace('$', ExternalAnnotationProvider.NULLABLE).replace("package-info", ""));
    }

    private boolean processDirectory(File file, boolean z) {
        if (z) {
            file = new File(file, ".");
        }
        File[] fileArrListFiles = file.listFiles();
        if (this.sort) {
            Arrays.sort(fileArrListFiles, new Comparator<File>() { // from class: com.android.dx.cf.direct.ClassPathOpener.2
                @Override // java.util.Comparator
                public int compare(File file2, File file3) {
                    return ClassPathOpener.compareClassNames(file2.getName(), file3.getName());
                }
            });
        }
        boolean zProcessOne = false;
        for (File file2 : fileArrListFiles) {
            zProcessOne |= processOne(file2, false);
        }
        return zProcessOne;
    }

    private boolean processArchive(File file) throws IOException {
        byte[] byteArray;
        ZipFile zipFile = new ZipFile(file);
        ArrayList<ZipEntry> list = Collections.list(zipFile.entries());
        if (this.sort) {
            Collections.sort(list, new Comparator<ZipEntry>() { // from class: com.android.dx.cf.direct.ClassPathOpener.3
                @Override // java.util.Comparator
                public int compare(ZipEntry zipEntry, ZipEntry zipEntry2) {
                    return ClassPathOpener.compareClassNames(zipEntry.getName(), zipEntry2.getName());
                }
            });
        }
        this.consumer.onProcessArchiveStart(file);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(40000);
        byte[] bArr = new byte[20000];
        boolean zProcessFileBytes = false;
        for (ZipEntry zipEntry : list) {
            boolean zIsDirectory = zipEntry.isDirectory();
            String name2 = zipEntry.getName();
            if (this.filter.accept(name2)) {
                if (!zIsDirectory) {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    byteArrayOutputStream.reset();
                    while (true) {
                        int i = inputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        byteArrayOutputStream.write(bArr, 0, i);
                    }
                    inputStream.close();
                    byteArray = byteArrayOutputStream.toByteArray();
                } else {
                    byteArray = new byte[0];
                }
                zProcessFileBytes |= this.consumer.processFileBytes(name2, zipEntry.getTime(), byteArray);
            }
        }
        zipFile.close();
        return zProcessFileBytes;
    }
}
