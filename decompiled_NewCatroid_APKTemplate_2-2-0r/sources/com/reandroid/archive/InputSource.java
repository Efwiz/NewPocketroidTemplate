package com.reandroid.archive;

import com.android.dex.DexFormat;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.utils.CRCDigest;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.StringsUtil;
import com.reandroid.utils.io.FileUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;

/* JADX INFO: loaded from: classes10.dex */
public abstract class InputSource {
    private static final String ASSETS = "assets";
    private static final int LAST_ORDER = 10;
    private static final String LIB = "lib";
    private static final String META_INF = "meta-inf";
    private static final int ORDER_android_manifest = 0;
    private static final int ORDER_assets = 6;
    private static final int ORDER_classes = 3;
    private static final int ORDER_lib = 4;
    private static final int ORDER_meta_inf = 2;
    private static final int ORDER_res = 5;
    private static final int ORDER_resources = 1;
    private static final String RES = "res";
    private String alias;
    private long mCrc;
    private long mLength;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f220name;
    private String[] splitAlias;
    public static final Comparator<? super InputSource> ALIAS_COMPARATOR = new Comparator() { // from class: com.reandroid.archive.InputSource$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return InputSource.compareSortOrAlias((InputSource) obj, (InputSource) obj2);
        }
    };
    private static final String ANDROID_MANIFEST = StringsUtil.toLowercase(AndroidManifestBlock.FILE_NAME);
    private static final String RESOURCES = StringsUtil.toLowercase(TableBlock.FILE_NAME);
    private int method = Archive.DEFLATED;
    private int sort = -1;

    public void disposeInputSource() {
    }

    public abstract InputStream openStream() throws IOException;

    public InputSource(String str) {
        this.f220name = str;
        this.alias = ArchiveUtil.sanitizePath(str);
    }

    public byte[] getBytes(int i) throws IOException {
        InputStream inputStreamOpenStream = openStream();
        byte[] bArr = new byte[i];
        inputStreamOpenStream.read(bArr, 0, i);
        close(inputStreamOpenStream);
        return bArr;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int i) {
        this.sort = i;
    }

    public void copyAttributes(InputSource inputSource) {
        if (inputSource == null || inputSource == this) {
            return;
        }
        setSort(inputSource.getSort());
        setMethod(inputSource.getMethod());
        setAlias(inputSource.getAlias());
    }

    public boolean isUncompressed() {
        return getMethod() != Archive.DEFLATED;
    }

    public void setUncompressed(boolean z) {
        if (z) {
            this.method = Archive.STORED;
        } else {
            this.method = Archive.DEFLATED;
        }
    }

    public int getMethod() {
        return this.method;
    }

    public void setMethod(int i) {
        this.method = i;
    }

    public String getAlias() {
        String str = this.alias;
        return str != null ? str : getName();
    }

    public void setAlias(String str) {
        this.alias = str;
        this.splitAlias = null;
    }

    public String getSimpleName() {
        return FileUtil.getFileName(getAlias());
    }

    public String getSimpleNameWoExtension() {
        return FileUtil.getNameWoExtension(getAlias());
    }

    public String getParentPath() {
        return FileUtil.getParent(getAlias());
    }

    public String getExtension() {
        return FileUtil.getExtension(getAlias());
    }

    private String[] getSplitAlias() {
        if (this.splitAlias == null) {
            this.splitAlias = StringsUtil.split(StringsUtil.toLowercase(getAlias()), '/');
        }
        return this.splitAlias;
    }

    public void close(InputStream inputStream) throws IOException {
        inputStream.close();
    }

    public File toFile(File file) {
        return new File(file, getAlias().replace('/', File.separatorChar));
    }

    public void write(File file) throws IOException {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        write(fileOutputStream);
        fileOutputStream.close();
    }

    public long write(OutputStream outputStream) throws IOException {
        return write(outputStream, openStream());
    }

    private long write(OutputStream outputStream, InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024000];
        long j = 0;
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                outputStream.write(bArr, 0, i);
                j += (long) i;
            } else {
                close(inputStream);
                return j;
            }
        }
    }

    public String getName() {
        return this.f220name;
    }

    public long getLength() throws IOException {
        if (this.mLength == 0) {
            calculateCrc();
        }
        return this.mLength;
    }

    public long getCrc() throws IOException {
        if (this.mCrc == 0) {
            calculateCrc();
        }
        return this.mCrc;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InputSource) {
            return getName().equals(((InputSource) obj).getName());
        }
        return false;
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + getName();
    }

    private void calculateCrc() throws IOException {
        InputStream inputStreamOpenStream = openStream();
        CRCDigest cRCDigest = new CRCDigest();
        byte[] bArr = new byte[65536];
        long j = 0;
        while (true) {
            int i = inputStreamOpenStream.read(bArr);
            if (i != -1) {
                cRCDigest.update(bArr, 0, i);
                j += (long) i;
            } else {
                close(inputStreamOpenStream);
                this.mCrc = cRCDigest.getValue();
                this.mLength = j;
                return;
            }
        }
    }

    public static int getDexNumber(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf < 0) {
            iLastIndexOf = str.lastIndexOf(92);
        }
        if (iLastIndexOf >= 0) {
            str = str.substring(iLastIndexOf + 1);
        }
        if (str.equals(DexFormat.DEX_IN_JAR_NAME)) {
            return 0;
        }
        if (str.startsWith("classes") && str.endsWith(".dex")) {
            try {
                return Integer.parseInt(str.substring("classes".length(), str.length() - ".dex".length()));
            } catch (NumberFormatException unused) {
            }
        }
        return -1;
    }

    public static int compareDex(String str, String str2) {
        int dexNumber = getDexNumber(str);
        int dexNumber2 = getDexNumber(str2);
        if (dexNumber == dexNumber2) {
            return 0;
        }
        if (dexNumber < 0) {
            return 1;
        }
        if (dexNumber2 < 0) {
            return -1;
        }
        return Integer.compare(dexNumber, dexNumber2);
    }

    private static int getSortOrder(String[] strArr) {
        int length = strArr.length;
        if (length == 0) {
            return 10;
        }
        String str = strArr[0];
        if (StringsUtil.isEmpty(str)) {
            return 10;
        }
        if (length != 1) {
            if (META_INF.equals(str)) {
                return 2;
            }
            if (LIB.equals(str)) {
                return 4;
            }
            if ("res".equals(str)) {
                return 5;
            }
            return ASSETS.equals(str) ? 6 : 10;
        }
        if (ANDROID_MANIFEST.equals(str)) {
            return 0;
        }
        if (RESOURCES.equals(str)) {
            return 1;
        }
        return (str.startsWith("classes") && str.endsWith(".dex")) ? 3 : 10;
    }

    private static int compareAlias(InputSource inputSource, InputSource inputSource2) {
        String[] splitAlias = inputSource.getSplitAlias();
        String[] splitAlias2 = inputSource2.getSplitAlias();
        int sortOrder = getSortOrder(splitAlias);
        int sortOrder2 = getSortOrder(splitAlias2);
        if (sortOrder != sortOrder2) {
            return CompareUtil.compare(sortOrder, sortOrder2);
        }
        if (sortOrder == 3) {
            return compareDex(splitAlias[0], splitAlias2[0]);
        }
        return StringsUtil.compare(splitAlias, splitAlias2);
    }

    static int compareSortOrAlias(InputSource inputSource, InputSource inputSource2) {
        if (inputSource == inputSource2) {
            return 0;
        }
        if (inputSource == null) {
            return 1;
        }
        if (inputSource2 == null) {
            return -1;
        }
        int sort = inputSource.getSort();
        int sort2 = inputSource.getSort();
        if (sort == -1 && sort2 == -1) {
            return compareAlias(inputSource, inputSource2);
        }
        if (sort == -1) {
            return 1;
        }
        if (sort2 == -1) {
            return -1;
        }
        return Integer.compare(sort, sort2);
    }
}
