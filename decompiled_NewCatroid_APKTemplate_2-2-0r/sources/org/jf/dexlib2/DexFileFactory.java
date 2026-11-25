package org.jf.dexlib2;

import com.android.dex.DexFormat;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
import org.jf.dexlib2.dexbacked.OatFile;
import org.jf.dexlib2.dexbacked.ZipDexContainer;
import org.jf.dexlib2.iface.DexFile;
import org.jf.dexlib2.iface.MultiDexContainer;
import org.jf.dexlib2.writer.pool.DexPool;
import org.jf.util.ExceptionWithContext;
import org.jf.util.StringUtils;
import org.jf.util.collection.ListUtil;
import org.jf.util.io.ByteStreams;
import org.jf.util.io.Files;

/* JADX INFO: loaded from: classes6.dex */
public final class DexFileFactory {
    public static DexBackedDexFile loadDexFile(String str, Opcodes opcodes) throws IOException {
        return loadDexFile(new File(str), opcodes);
    }

    /* JADX WARN: Code duplicated, block: B:19:0x004b A[Catch: all -> 0x0034, TRY_ENTER, TryCatch #1 {all -> 0x0034, blocks: (B:7:0x002c, B:12:0x0036, B:15:0x003e, B:19:0x004b, B:21:0x0051, B:23:0x005b, B:26:0x0066, B:27:0x0075, B:28:0x0076, B:29:0x007b), top: B:38:0x002c }] */
    /* JADX WARN: Code duplicated, block: B:21:0x0051 A[Catch: all -> 0x0034, TryCatch #1 {all -> 0x0034, blocks: (B:7:0x002c, B:12:0x0036, B:15:0x003e, B:19:0x004b, B:21:0x0051, B:23:0x005b, B:26:0x0066, B:27:0x0075, B:28:0x0076, B:29:0x007b), top: B:38:0x002c }] */
    /* JADX WARN: Code duplicated, block: B:23:0x005b A[Catch: all -> 0x0034, TRY_LEAVE, TryCatch #1 {all -> 0x0034, blocks: (B:7:0x002c, B:12:0x0036, B:15:0x003e, B:19:0x004b, B:21:0x0051, B:23:0x005b, B:26:0x0066, B:27:0x0075, B:28:0x0076, B:29:0x007b), top: B:38:0x002c }] */
    /* JADX WARN: Code duplicated, block: B:26:0x0066 A[Catch: all -> 0x0034, TRY_ENTER, TryCatch #1 {all -> 0x0034, blocks: (B:7:0x002c, B:12:0x0036, B:15:0x003e, B:19:0x004b, B:21:0x0051, B:23:0x005b, B:26:0x0066, B:27:0x0075, B:28:0x0076, B:29:0x007b), top: B:38:0x002c }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0076 A[Catch: all -> 0x0034, TryCatch #1 {all -> 0x0034, blocks: (B:7:0x002c, B:12:0x0036, B:15:0x003e, B:19:0x004b, B:21:0x0051, B:23:0x005b, B:26:0x0066, B:27:0x0075, B:28:0x0076, B:29:0x007b), top: B:38:0x002c }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0080  */
    public static DexBackedDexFile loadDexFile(File file, Opcodes opcodes) throws IOException {
        OatFile oatFileFromInputStream;
        List<DexBackedDexFile> dexFiles;
        if (!file.exists()) {
            throw new DexFileNotFoundException("%s does not exist", file.getName());
        }
        try {
            return (DexBackedDexFile) new DexEntryFinder(file.getPath(), new ZipDexContainer(file, opcodes)).findEntry(DexFormat.DEX_IN_JAR_NAME, true).getDexFile();
        } catch (ZipDexContainer.NotAZipFileException unused) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                try {
                    try {
                        try {
                            DexBackedDexFile dexBackedDexFileFromInputStream = DexBackedDexFile.fromInputStream(opcodes, bufferedInputStream);
                            bufferedInputStream.close();
                            return dexBackedDexFileFromInputStream;
                        } catch (Throwable th) {
                            bufferedInputStream.close();
                            throw th;
                        }
                    } catch (DexBackedOdexFile.NotAnOdexFile unused2) {
                        oatFileFromInputStream = OatFile.fromInputStream(bufferedInputStream, new FilenameVdexProvider(file));
                        if (oatFileFromInputStream != null) {
                            bufferedInputStream.close();
                            throw new UnsupportedFileTypeException("%s is not an apk, dex, odex or oat file.", file.getPath());
                        }
                        if (oatFileFromInputStream.isSupportedVersion() != 0) {
                            throw new UnsupportedOatVersionException(oatFileFromInputStream);
                        }
                        dexFiles = oatFileFromInputStream.getDexFiles();
                        if (dexFiles.size() != 0) {
                            throw new DexFileNotFoundException("Oat file %s contains no dex files", file.getName());
                        }
                        DexBackedDexFile dexBackedDexFile = dexFiles.get(0);
                        bufferedInputStream.close();
                        return dexBackedDexFile;
                    }
                } catch (OatFile.NotAnOatFileException unused3) {
                    oatFileFromInputStream = null;
                    if (oatFileFromInputStream != null) {
                        bufferedInputStream.close();
                        throw new UnsupportedFileTypeException("%s is not an apk, dex, odex or oat file.", file.getPath());
                    }
                    if (oatFileFromInputStream.isSupportedVersion() != 0) {
                        throw new UnsupportedOatVersionException(oatFileFromInputStream);
                    }
                    dexFiles = oatFileFromInputStream.getDexFiles();
                    if (dexFiles.size() != 0) {
                        throw new DexFileNotFoundException("Oat file %s contains no dex files", file.getName());
                    }
                    DexBackedDexFile dexBackedDexFile2 = dexFiles.get(0);
                    bufferedInputStream.close();
                    return dexBackedDexFile2;
                }
            } catch (DexBackedDexFile.NotADexFile unused4) {
                DexBackedOdexFile dexBackedOdexFileFromInputStream = DexBackedOdexFile.fromInputStream(opcodes, (InputStream) bufferedInputStream);
                bufferedInputStream.close();
                return dexBackedOdexFileFromInputStream;
            }
        }
    }

    public static MultiDexContainer.DexEntry<? extends DexBackedDexFile> loadDexEntry(File file, String str, boolean z, Opcodes opcodes) throws IOException {
        OatFile oatFileFromInputStream;
        if (!file.exists()) {
            throw new DexFileNotFoundException("Container file %s does not exist", file.getName());
        }
        try {
            return new DexEntryFinder(file.getPath(), new ZipDexContainer(file, opcodes)).findEntry(str, z);
        } catch (ZipDexContainer.NotAZipFileException unused) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            try {
                try {
                    oatFileFromInputStream = OatFile.fromInputStream(bufferedInputStream, new FilenameVdexProvider(file));
                } catch (OatFile.NotAnOatFileException unused2) {
                    oatFileFromInputStream = null;
                }
                if (oatFileFromInputStream != null) {
                    if (oatFileFromInputStream.isSupportedVersion() == 0) {
                        throw new UnsupportedOatVersionException(oatFileFromInputStream);
                    }
                    if (oatFileFromInputStream.getDexFiles().size() == 0) {
                        throw new DexFileNotFoundException("Oat file %s contains no dex files", file.getName());
                    }
                    MultiDexContainer.DexEntry<? extends DexBackedDexFile> dexEntryFindEntry = new DexEntryFinder(file.getPath(), oatFileFromInputStream).findEntry(str, z);
                    bufferedInputStream.close();
                    return dexEntryFindEntry;
                }
                bufferedInputStream.close();
                throw new UnsupportedFileTypeException("%s is not an apk or oat file.", file.getPath());
            } catch (Throwable th) {
                bufferedInputStream.close();
                throw th;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:20:0x004d A[Catch: all -> 0x002d, TRY_ENTER, TRY_LEAVE, TryCatch #2 {all -> 0x002d, blocks: (B:8:0x001c, B:13:0x002f, B:16:0x0040, B:20:0x004d, B:24:0x0057, B:25:0x005c), top: B:34:0x001c }] */
    /* JADX WARN: Code duplicated, block: B:22:0x0053  */
    /* JADX WARN: Code duplicated, block: B:24:0x0057 A[Catch: all -> 0x002d, TRY_ENTER, TryCatch #2 {all -> 0x002d, blocks: (B:8:0x001c, B:13:0x002f, B:16:0x0040, B:20:0x004d, B:24:0x0057, B:25:0x005c), top: B:34:0x001c }] */
    /* JADX WARN: Code duplicated, block: B:28:0x0061  */
    public static MultiDexContainer<? extends DexBackedDexFile> loadDexContainer(File file, Opcodes opcodes) throws IOException {
        OatFile oatFileFromInputStream;
        if (!file.exists()) {
            throw new DexFileNotFoundException("%s does not exist", file.getName());
        }
        ZipDexContainer zipDexContainer = new ZipDexContainer(file, opcodes);
        if (zipDexContainer.isZipFile()) {
            return zipDexContainer;
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        try {
            try {
                try {
                    try {
                        SingletonMultiDexContainer singletonMultiDexContainer = new SingletonMultiDexContainer(file.getPath(), DexBackedDexFile.fromInputStream(opcodes, bufferedInputStream));
                        bufferedInputStream.close();
                        return singletonMultiDexContainer;
                    } catch (DexBackedDexFile.NotADexFile unused) {
                        SingletonMultiDexContainer singletonMultiDexContainer2 = new SingletonMultiDexContainer(file.getPath(), DexBackedOdexFile.fromInputStream(opcodes, (InputStream) bufferedInputStream));
                        bufferedInputStream.close();
                        return singletonMultiDexContainer2;
                    }
                } catch (OatFile.NotAnOatFileException unused2) {
                    oatFileFromInputStream = null;
                    if (oatFileFromInputStream != null) {
                        bufferedInputStream.close();
                        throw new UnsupportedFileTypeException("%s is not an apk, dex, odex or oat file.", file.getPath());
                    }
                    if (oatFileFromInputStream.isSupportedVersion() != 0) {
                        throw new UnsupportedOatVersionException(oatFileFromInputStream);
                    }
                    bufferedInputStream.close();
                    return oatFileFromInputStream;
                }
            } catch (DexBackedOdexFile.NotAnOdexFile unused3) {
                oatFileFromInputStream = OatFile.fromInputStream(bufferedInputStream, new FilenameVdexProvider(file));
                if (oatFileFromInputStream != null) {
                    bufferedInputStream.close();
                    throw new UnsupportedFileTypeException("%s is not an apk, dex, odex or oat file.", file.getPath());
                }
                if (oatFileFromInputStream.isSupportedVersion() != 0) {
                    throw new UnsupportedOatVersionException(oatFileFromInputStream);
                }
                bufferedInputStream.close();
                return oatFileFromInputStream;
            }
        } catch (Throwable th) {
            bufferedInputStream.close();
            throw th;
        }
    }

    public static void writeDexFile(String str, DexFile dexFile) throws IOException {
        DexPool.writeTo(str, dexFile);
    }

    private DexFileFactory() {
    }

    public static class DexFileNotFoundException extends ExceptionWithContext {
        public DexFileNotFoundException(String str, Object... objArr) {
            super(str, objArr);
        }

        public DexFileNotFoundException(Throwable th, String str, Object... objArr) {
            super(th, str, objArr);
        }
    }

    public static class UnsupportedOatVersionException extends ExceptionWithContext {
        public final OatFile oatFile;

        public UnsupportedOatVersionException(OatFile oatFile) {
            super("Unsupported oat version: %d", Integer.valueOf(oatFile.getOatVersion()));
            this.oatFile = oatFile;
        }
    }

    public static class MultipleMatchingDexEntriesException extends ExceptionWithContext {
        public MultipleMatchingDexEntriesException(String str, Object... objArr) {
            super(String.format(str, objArr), new Object[0]);
        }
    }

    public static class UnsupportedFileTypeException extends ExceptionWithContext {
        public UnsupportedFileTypeException(String str, Object... objArr) {
            super(String.format(str, objArr), new Object[0]);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean fullEntryMatch(String str, String str2) {
        if (str.equals(str2)) {
            return true;
        }
        if (str.charAt(0) == '/') {
            str = str.substring(1);
        }
        if (str2.charAt(0) == '/') {
            str2 = str2.substring(1);
        }
        return str.equals(str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean partialEntryMatch(String str, String str2) {
        if (str.equals(str2)) {
            return true;
        }
        if (!str.endsWith(str2)) {
            return false;
        }
        char cCharAt = str.charAt((str.length() - str2.length()) - 1);
        char cCharAt2 = str2.charAt(0);
        return cCharAt2 == ':' || cCharAt2 == '/' || cCharAt2 == '!' || cCharAt == ':' || cCharAt == '/' || cCharAt == '!';
    }

    protected static class DexEntryFinder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final MultiDexContainer<? extends DexBackedDexFile> dexContainer;
        private final String filename;

        public DexEntryFinder(String str, MultiDexContainer<? extends DexBackedDexFile> multiDexContainer) {
            this.filename = str;
            this.dexContainer = multiDexContainer;
        }

        public MultiDexContainer.DexEntry<? extends DexBackedDexFile> findEntry(String str, boolean z) throws IOException {
            if (z) {
                try {
                    MultiDexContainer.DexEntry entry = this.dexContainer.getEntry(str);
                    if (entry != null) {
                        return entry;
                    }
                    throw new DexFileNotFoundException("Could not find entry %s in %s.", str, this.filename);
                } catch (DexBackedDexFile.NotADexFile unused) {
                    throw new UnsupportedFileTypeException("Entry %s in %s is not a dex file", str, this.filename);
                }
            }
            ArrayList arrayListNewArrayList = ListUtil.newArrayList();
            ArrayList arrayListNewArrayList2 = ListUtil.newArrayList();
            ArrayList arrayListNewArrayList3 = ListUtil.newArrayList();
            ArrayList arrayListNewArrayList4 = ListUtil.newArrayList();
            for (String str2 : this.dexContainer.getDexEntryNames()) {
                if (!DexFileFactory.fullEntryMatch(str2, str)) {
                    if (DexFileFactory.partialEntryMatch(str2, str)) {
                        arrayListNewArrayList3.add(str2);
                        arrayListNewArrayList4.add(this.dexContainer.getEntry(str2));
                    }
                } else {
                    arrayListNewArrayList.add(str2);
                    arrayListNewArrayList2.add(this.dexContainer.getEntry(str2));
                }
            }
            if (arrayListNewArrayList2.size() == 1) {
                try {
                    return (MultiDexContainer.DexEntry) arrayListNewArrayList2.get(0);
                } catch (DexBackedDexFile.NotADexFile unused2) {
                    throw new UnsupportedFileTypeException("Entry %s in %s is not a dex file", arrayListNewArrayList.get(0), this.filename);
                }
            }
            if (arrayListNewArrayList2.size() > 1) {
                throw new MultipleMatchingDexEntriesException(String.format("Multiple entries in %s match %s: %s", this.filename, str, StringUtils.join(", ", arrayListNewArrayList)), new Object[0]);
            }
            if (arrayListNewArrayList4.size() == 0) {
                throw new DexFileNotFoundException("Could not find a dex entry in %s matching %s", this.filename, str);
            }
            if (arrayListNewArrayList4.size() > 1) {
                throw new MultipleMatchingDexEntriesException(String.format("Multiple dex entries in %s match %s: %s", this.filename, str, StringUtils.join(", ", arrayListNewArrayList3)), new Object[0]);
            }
            return (MultiDexContainer.DexEntry) arrayListNewArrayList4.get(0);
        }
    }

    private static class SingletonMultiDexContainer implements MultiDexContainer<DexBackedDexFile> {
        private final DexBackedDexFile dexFile;
        private final String entryName;

        public SingletonMultiDexContainer(String str, DexBackedDexFile dexBackedDexFile) {
            this.entryName = str;
            this.dexFile = dexBackedDexFile;
        }

        @Override // org.jf.dexlib2.iface.MultiDexContainer
        public List<String> getDexEntryNames() {
            return ListUtil.of(this.entryName);
        }

        @Override // org.jf.dexlib2.iface.MultiDexContainer
        public MultiDexContainer.DexEntry<DexBackedDexFile> getEntry(final String str) {
            if (str.equals(this.entryName)) {
                return new MultiDexContainer.DexEntry<DexBackedDexFile>() { // from class: org.jf.dexlib2.DexFileFactory.SingletonMultiDexContainer.1
                    @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
                    public String getEntryName() {
                        return str;
                    }

                    @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
                    public DexBackedDexFile getDexFile() {
                        return SingletonMultiDexContainer.this.dexFile;
                    }

                    @Override // org.jf.dexlib2.iface.MultiDexContainer.DexEntry
                    public MultiDexContainer<? extends DexBackedDexFile> getContainer() {
                        return SingletonMultiDexContainer.this;
                    }
                };
            }
            return null;
        }
    }

    public static class FilenameVdexProvider implements OatFile.VdexProvider {
        private byte[] buf = null;
        private boolean loadedVdex = false;
        private final File vdexFile;

        public FilenameVdexProvider(File file) {
            this.vdexFile = new File(file.getAbsoluteFile().getParentFile(), Files.getNameWithoutExtension(file) + ".vdex");
        }

        @Override // org.jf.dexlib2.dexbacked.OatFile.VdexProvider
        public byte[] getVdex() {
            File parentFile;
            if (!this.loadedVdex) {
                File file = this.vdexFile;
                if (!file.exists() && (parentFile = file.getParentFile().getParentFile()) != null) {
                    file = new File(parentFile, this.vdexFile.getName());
                }
                if (file.exists()) {
                    try {
                        this.buf = ByteStreams.toByteArray(new FileInputStream(file));
                    } catch (FileNotFoundException unused) {
                        this.buf = null;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.loadedVdex = true;
            }
            return this.buf;
        }
    }
}
