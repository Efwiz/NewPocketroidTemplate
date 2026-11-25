package org.jf.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes6.dex */
public class ClassFileNameHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MAX_FILENAME_LENGTH = 255;
    private static final int NUMERIC_SUFFIX_RESERVE = 6;
    private static Pattern reservedFileNameRegex = Pattern.compile("^(CON|PRN|AUX|NUL|COM[1-9]|LPT[1-9])(\\..*)?$", 2);
    private final int CASE_INSENSITIVE;
    private final int CASE_SENSITIVE;
    private final int NO_VALUE;
    private String fileExtension;
    private int forcedCaseSensitivity;
    private boolean modifyWindowsReservedFilenames;
    private DirectoryEntry top;

    /* JADX INFO: Access modifiers changed from: private */
    public int getMaxFilenameLength() {
        return 249;
    }

    private static int utf8Length(int i) {
        if (i < 128) {
            return 1;
        }
        if (i < 2048) {
            return 2;
        }
        return i < 65536 ? 3 : 4;
    }

    public ClassFileNameHandler(File file, String str) {
        this.NO_VALUE = -1;
        this.CASE_INSENSITIVE = 0;
        this.CASE_SENSITIVE = 1;
        this.forcedCaseSensitivity = -1;
        this.top = new DirectoryEntry(file);
        this.fileExtension = str;
        this.modifyWindowsReservedFilenames = isWindows();
    }

    public ClassFileNameHandler(File file, String str, boolean z, boolean z2) {
        this.NO_VALUE = -1;
        this.CASE_INSENSITIVE = 0;
        this.CASE_SENSITIVE = 1;
        this.forcedCaseSensitivity = -1;
        this.top = new DirectoryEntry(file);
        this.fileExtension = str;
        this.forcedCaseSensitivity = z ? 1 : 0;
        this.modifyWindowsReservedFilenames = z2;
    }

    public File getUniqueFilenameForClass(String str) throws IOException {
        if (str.charAt(0) != 'L' || str.charAt(str.length() - 1) != ';') {
            throw new RuntimeException("Not a valid dalvik class name");
        }
        int i = 1;
        for (int i2 = 1; i2 < str.length() - 1; i2++) {
            if (str.charAt(i2) == '/') {
                i++;
            }
        }
        String[] strArr = new String[i];
        int i3 = 0;
        int i4 = 1;
        int i5 = 1;
        while (i4 < str.length() - 1) {
            if (str.charAt(i4) == '/') {
                if (i4 - i5 == 0) {
                    throw new RuntimeException("Not a valid dalvik class name");
                }
                strArr[i3] = str.substring(i5, i4);
                i4++;
                i5 = i4;
                i3++;
            }
            i4++;
        }
        if (i5 >= str.length() - 1) {
            throw new RuntimeException("Not a valid dalvik class name");
        }
        strArr[i3] = str.substring(i5, str.length() - 1);
        return addUniqueChild(this.top, strArr, 0);
    }

    private File addUniqueChild(DirectoryEntry directoryEntry, String[] strArr, int i) throws IOException {
        if (i == strArr.length - 1) {
            FileEntry fileEntry = new FileEntry(directoryEntry, strArr[i] + this.fileExtension);
            directoryEntry.addChild(fileEntry);
            return new File(directoryEntry.file, fileEntry.getPhysicalName());
        }
        return addUniqueChild((DirectoryEntry) directoryEntry.addChild(new DirectoryEntry(directoryEntry, strArr[i])), strArr, i + 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int utf8Length(String str) {
        int iCharCount = 0;
        int iUtf8Length = 0;
        while (iCharCount < str.length()) {
            int iCodePointAt = str.codePointAt(iCharCount);
            iUtf8Length += utf8Length(iCodePointAt);
            iCharCount += Character.charCount(iCodePointAt);
        }
        return iUtf8Length;
    }

    static String shortenPathComponent(String str, int i) {
        int i2 = i + 1;
        try {
            IntBuffer intBufferAsIntBuffer = ByteBuffer.wrap(str.getBytes("UTF-32BE")).asIntBuffer();
            int iLimit = intBufferAsIntBuffer.limit();
            int[] iArr = new int[iLimit];
            intBufferAsIntBuffer.get(iArr);
            int i3 = iLimit / 2;
            int i4 = i3 + 1;
            int iUtf8Length = utf8Length(iArr[i3]);
            if (iLimit % 2 == 0 && iUtf8Length < i2) {
                iUtf8Length += utf8Length(iArr[i4]);
                i4 = i3 + 2;
            }
            while (iUtf8Length < i2 && (i3 > 0 || i4 < iLimit)) {
                if (i3 > 0) {
                    i3--;
                    iUtf8Length += utf8Length(iArr[i3]);
                }
                if (iUtf8Length < i2 && i4 < iLimit) {
                    iUtf8Length += utf8Length(iArr[i4]);
                    i4++;
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i5 = 0; i5 < i3; i5++) {
                sb.appendCodePoint(iArr[i5]);
            }
            sb.append('#');
            while (i4 < iLimit) {
                sb.appendCodePoint(iArr[i4]);
                i4++;
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isWindows() {
        return System.getProperty(SystemProperties.OS_NAME).startsWith("Windows");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isReservedFileName(String str) {
        return reservedFileNameRegex.matcher(str).matches();
    }

    private abstract class FileSystemEntry {
        public final String logicalName;
        public final DirectoryEntry parent;
        protected String physicalName;

        protected abstract void createIfNeeded() throws IOException;

        public abstract String getPhysicalNameWithSuffix(int i);

        private FileSystemEntry(DirectoryEntry directoryEntry, String str) {
            this.physicalName = null;
            this.parent = directoryEntry;
            this.logicalName = str;
        }

        public String getNormalizedName(boolean z) {
            DirectoryEntry directoryEntry;
            String strAddSuffixBeforeExtension = this.logicalName;
            if (!z && (directoryEntry = this.parent) != null && !directoryEntry.isCaseSensitive()) {
                strAddSuffixBeforeExtension = strAddSuffixBeforeExtension.toLowerCase();
            }
            if (ClassFileNameHandler.this.modifyWindowsReservedFilenames && ClassFileNameHandler.isReservedFileName(strAddSuffixBeforeExtension)) {
                strAddSuffixBeforeExtension = ClassFileNameHandler.addSuffixBeforeExtension(strAddSuffixBeforeExtension, "#");
            }
            int iUtf8Length = ClassFileNameHandler.utf8Length(strAddSuffixBeforeExtension);
            return iUtf8Length > ClassFileNameHandler.this.getMaxFilenameLength() ? ClassFileNameHandler.shortenPathComponent(strAddSuffixBeforeExtension, iUtf8Length - ClassFileNameHandler.this.getMaxFilenameLength()) : strAddSuffixBeforeExtension;
        }

        public String getPhysicalName() {
            return this.physicalName;
        }

        public void setSuffix(int i) throws IOException {
            if (i < 0 || i > 99999) {
                throw new IllegalArgumentException("suffix must be in [0, 100000)");
            }
            if (this.physicalName != null) {
                throw new IllegalStateException("The suffix can only be set once");
            }
            this.physicalName = new File(this.parent.file, getPhysicalNameWithSuffix(i)).getCanonicalFile().getName();
            createIfNeeded();
        }
    }

    private class DirectoryEntry extends FileSystemEntry {
        private int caseSensitivity;
        private final Map<String, Set<FileSystemEntry>> children;
        private File file;
        private final Map<String, Integer> lastSuffixMap;
        private final Map<String, FileSystemEntry> physicalToEntry;

        /* JADX WARN: Multi-variable type inference failed */
        public DirectoryEntry(File file) {
            super(null, file.getName());
            this.file = null;
            this.caseSensitivity = ClassFileNameHandler.this.forcedCaseSensitivity;
            this.children = new HashMap();
            this.physicalToEntry = new HashMap();
            this.lastSuffixMap = new HashMap();
            this.file = file;
            this.physicalName = file.getName();
        }

        public DirectoryEntry(DirectoryEntry directoryEntry, String str) {
            super(directoryEntry, str);
            this.file = null;
            this.caseSensitivity = ClassFileNameHandler.this.forcedCaseSensitivity;
            this.children = new HashMap();
            this.physicalToEntry = new HashMap();
            this.lastSuffixMap = new HashMap();
        }

        public synchronized FileSystemEntry addChild(FileSystemEntry fileSystemEntry) throws IOException {
            String normalizedName = fileSystemEntry.getNormalizedName(false);
            Set<FileSystemEntry> hashSet = this.children.get(normalizedName);
            if (hashSet == null) {
                hashSet = new HashSet<>();
                this.children.put(normalizedName, hashSet);
            }
            if (fileSystemEntry instanceof DirectoryEntry) {
                for (FileSystemEntry fileSystemEntry2 : hashSet) {
                    if (fileSystemEntry2.logicalName.equals(fileSystemEntry.logicalName)) {
                        return fileSystemEntry2;
                    }
                }
            }
            Integer num = this.lastSuffixMap.get(normalizedName);
            if (num == null) {
                num = -1;
            }
            int iIntValue = num.intValue();
            do {
                iIntValue++;
            } while (this.physicalToEntry.containsKey(new File(this.file, fileSystemEntry.getPhysicalNameWithSuffix(iIntValue)).getCanonicalFile().getName()));
            fileSystemEntry.setSuffix(iIntValue);
            this.lastSuffixMap.put(normalizedName, Integer.valueOf(iIntValue));
            this.physicalToEntry.put(fileSystemEntry.getPhysicalName(), fileSystemEntry);
            hashSet.add(fileSystemEntry);
            return fileSystemEntry;
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        public String getPhysicalNameWithSuffix(int i) {
            if (i > 0) {
                return getNormalizedName(true) + "." + i;
            }
            return getNormalizedName(true);
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        protected void createIfNeeded() throws IOException {
            String physicalName = getPhysicalName();
            if (this.parent == null || physicalName == null) {
                return;
            }
            File canonicalFile = new File(this.parent.file, physicalName).getCanonicalFile();
            this.file = canonicalFile;
            canonicalFile.mkdirs();
        }

        protected boolean isCaseSensitive() {
            File file;
            if (getPhysicalName() == null || (file = this.file) == null) {
                throw new IllegalStateException("Must call setSuffix() first");
            }
            int i = this.caseSensitivity;
            if (i != -1) {
                return i == 1;
            }
            if (file.exists() && file.isFile() && !file.delete()) {
                throw new ExceptionWithContext("Can't delete %s to make it into a directory", file.getAbsolutePath());
            }
            if (!file.exists() && !file.mkdirs()) {
                throw new ExceptionWithContext("Couldn't create directory %s", file.getAbsolutePath());
            }
            try {
                boolean zTestCaseSensitivity = PathUtil.testCaseSensitivity(file);
                this.caseSensitivity = zTestCaseSensitivity ? 1 : 0;
                return zTestCaseSensitivity;
            } catch (IOException unused) {
                return false;
            }
        }
    }

    private class FileEntry extends FileSystemEntry {
        private FileEntry(DirectoryEntry directoryEntry, String str) {
            super(directoryEntry, str);
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        public String getPhysicalNameWithSuffix(int i) {
            if (i > 0) {
                return ClassFileNameHandler.addSuffixBeforeExtension(getNormalizedName(true), "." + Integer.toString(i));
            }
            return getNormalizedName(true);
        }

        @Override // org.jf.util.ClassFileNameHandler.FileSystemEntry
        protected void createIfNeeded() throws IOException {
            String physicalName = getPhysicalName();
            if (this.parent == null || physicalName == null) {
                return;
            }
            new File(this.parent.file, physicalName).getCanonicalFile().createNewFile();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String addSuffixBeforeExtension(String str, String str2) {
        int iLastIndexOf = str.lastIndexOf(46);
        StringBuilder sb = new StringBuilder(str.length() + str2.length() + 1);
        if (iLastIndexOf < 0) {
            sb.append(str);
            sb.append(str2);
        } else {
            sb.append(str.subSequence(0, iLastIndexOf));
            sb.append(str2);
            sb.append(str.subSequence(iLastIndexOf, str.length()));
        }
        return sb.toString();
    }
}
