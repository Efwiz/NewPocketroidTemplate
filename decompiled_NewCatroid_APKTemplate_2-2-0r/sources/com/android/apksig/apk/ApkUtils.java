package com.android.apksig.apk;

import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.android.apksig.internal.apk.v1.V1SchemeVerifier;
import com.android.apksig.internal.util.Pair;
import com.android.apksig.internal.zip.CentralDirectoryRecord;
import com.android.apksig.internal.zip.LocalFileRecord;
import com.android.apksig.internal.zip.ZipUtils;
import com.android.apksig.util.DataSource;
import com.android.apksig.zip.ZipFormatException;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes.dex */
public abstract class ApkUtils {
    public static final String ANDROID_MANIFEST_ZIP_ENTRY_NAME = "AndroidManifest.xml";
    private static final int DEBUGGABLE_ATTR_ID = 16842767;
    private static final String MANIFEST_ELEMENT_TAG = "manifest";
    private static final int MIN_SDK_VERSION_ATTR_ID = 16843276;
    public static final String SOURCE_STAMP_CERTIFICATE_HASH_ZIP_ENTRY_NAME = "stamp-cert-sha256";
    private static final int TARGET_SANDBOX_VERSION_ATTR_ID = 16844108;
    private static final int TARGET_SDK_VERSION_ATTR_ID = 16843376;
    private static final String USES_SDK_ELEMENT_TAG = "uses-sdk";
    private static final int VERSION_CODE_ATTR_ID = 16843291;
    private static final int VERSION_CODE_MAJOR_ATTR_ID = 16844150;

    private ApkUtils() {
    }

    public static ZipSections findZipSections(DataSource apk) throws IOException, ZipFormatException {
        com.android.apksig.zip.ZipSections zipSectionsFindZipSections = ApkUtilsLite.findZipSections(apk);
        return new ZipSections(zipSectionsFindZipSections.getZipCentralDirectoryOffset(), zipSectionsFindZipSections.getZipCentralDirectorySizeBytes(), zipSectionsFindZipSections.getZipCentralDirectoryRecordCount(), zipSectionsFindZipSections.getZipEndOfCentralDirectoryOffset(), zipSectionsFindZipSections.getZipEndOfCentralDirectory());
    }

    public static class ZipSections extends com.android.apksig.zip.ZipSections {
        public ZipSections(long centralDirectoryOffset, long centralDirectorySizeBytes, int centralDirectoryRecordCount, long eocdOffset, ByteBuffer eocd) {
            super(centralDirectoryOffset, centralDirectorySizeBytes, centralDirectoryRecordCount, eocdOffset, eocd);
        }
    }

    public static void setZipEocdCentralDirectoryOffset(ByteBuffer zipEndOfCentralDirectory, long offset) {
        ByteBuffer byteBufferSlice = zipEndOfCentralDirectory.slice();
        byteBufferSlice.order(ByteOrder.LITTLE_ENDIAN);
        ZipUtils.setZipEocdCentralDirectoryOffset(byteBufferSlice, offset);
    }

    public static void updateZipEocdCommentLen(ByteBuffer zipEndOfCentralDirectory) {
        ByteBuffer byteBufferSlice = zipEndOfCentralDirectory.slice();
        byteBufferSlice.order(ByteOrder.LITTLE_ENDIAN);
        ZipUtils.updateZipEocdCommentLen(byteBufferSlice);
    }

    public static ApkSigningBlock findApkSigningBlock(DataSource apk) throws ApkSigningBlockNotFoundException, IOException, ApkFormatException {
        try {
            return findApkSigningBlock(apk, findZipSections(apk));
        } catch (ZipFormatException e) {
            throw new ApkFormatException("Malformed APK: not a ZIP archive", e);
        }
    }

    public static ApkSigningBlock findApkSigningBlock(DataSource apk, ZipSections zipSections) throws ApkSigningBlockNotFoundException, IOException {
        ApkUtilsLite.ApkSigningBlock apkSigningBlockFindApkSigningBlock = ApkUtilsLite.findApkSigningBlock(apk, zipSections);
        return new ApkSigningBlock(apkSigningBlockFindApkSigningBlock.getStartOffset(), apkSigningBlockFindApkSigningBlock.getContents());
    }

    public static class ApkSigningBlock extends ApkUtilsLite.ApkSigningBlock {
        public ApkSigningBlock(long startOffsetInApk, DataSource contents) {
            super(startOffsetInApk, contents);
        }
    }

    public static ByteBuffer getAndroidManifest(DataSource apk) throws IOException, ApkFormatException {
        CentralDirectoryRecord next;
        try {
            ZipSections zipSectionsFindZipSections = findZipSections(apk);
            Iterator<CentralDirectoryRecord> it = V1SchemeVerifier.parseZipCentralDirectory(apk, zipSectionsFindZipSections).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!ANDROID_MANIFEST_ZIP_ENTRY_NAME.equals(next.getName()));
            if (next == null) {
                throw new ApkFormatException("Missing AndroidManifest.xml");
            }
            DataSource dataSourceSlice = apk.slice(0L, zipSectionsFindZipSections.getZipCentralDirectoryOffset());
            try {
                return ByteBuffer.wrap(LocalFileRecord.getUncompressedData(dataSourceSlice, next, dataSourceSlice.size()));
            } catch (ZipFormatException e) {
                throw new ApkFormatException("Failed to read AndroidManifest.xml", e);
            }
        } catch (ZipFormatException e2) {
            throw new ApkFormatException("Not a valid ZIP archive", e2);
        }
    }

    public static int getMinSdkVersionFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws MinSdkVersionException {
        int minSdkVersionForCodename;
        try {
            AndroidBinXmlParser androidBinXmlParser = new AndroidBinXmlParser(androidManifestContents);
            int iMax = 1;
            for (int eventType = androidBinXmlParser.getEventType(); eventType != 2; eventType = androidBinXmlParser.next()) {
                if (eventType == 3 && androidBinXmlParser.getDepth() == 2 && USES_SDK_ELEMENT_TAG.equals(androidBinXmlParser.getName()) && androidBinXmlParser.getNamespace().isEmpty()) {
                    int i = 0;
                    while (true) {
                        if (i >= androidBinXmlParser.getAttributeCount()) {
                            minSdkVersionForCodename = 1;
                            break;
                        }
                        if (androidBinXmlParser.getAttributeNameResourceId(i) == 16843276) {
                            int attributeValueType = androidBinXmlParser.getAttributeValueType(i);
                            if (attributeValueType == 1) {
                                minSdkVersionForCodename = getMinSdkVersionForCodename(androidBinXmlParser.getAttributeStringValue(i));
                                break;
                            }
                            if (attributeValueType == 2) {
                                minSdkVersionForCodename = androidBinXmlParser.getAttributeIntValue(i);
                                break;
                            }
                            throw new MinSdkVersionException("Unable to determine APK's minimum supported Android: unsupported value type in AndroidManifest.xml's minSdkVersion. Only integer values supported.");
                        }
                        i++;
                    }
                    iMax = Math.max(iMax, minSdkVersionForCodename);
                }
            }
            return iMax;
        } catch (AndroidBinXmlParser.XmlParserException e) {
            throw new MinSdkVersionException("Unable to determine APK's minimum supported Android platform version: malformed binary resource: AndroidManifest.xml", e);
        }
    }

    private static class CodenamesLazyInitializer {
        private static final Pair<Character, Integer>[] SORTED_CODENAMES_FIRST_CHAR_TO_API_LEVEL = {Pair.of(Character.valueOf(Util.C_CHAR), 2), Pair.of(Character.valueOf(Util.C_DOUBLE), 3), Pair.of('E', 4), Pair.of(Character.valueOf(Util.C_FLOAT), 7), Pair.of('G', 8), Pair.of('H', 10), Pair.of('I', 13), Pair.of(Character.valueOf(Util.C_LONG), 15), Pair.of('K', 18), Pair.of('L', 20), Pair.of('M', 22), Pair.of('N', 23), Pair.of('O', 25)};
        private static final Comparator<Pair<Character, Integer>> CODENAME_FIRST_CHAR_COMPARATOR = new ByFirstComparator();

        private CodenamesLazyInitializer() {
        }

        private static class ByFirstComparator implements Comparator<Pair<Character, Integer>> {
            private ByFirstComparator() {
            }

            @Override // java.util.Comparator
            public int compare(Pair<Character, Integer> o1, Pair<Character, Integer> o2) {
                return o1.getFirst().charValue() - o2.getFirst().charValue();
            }
        }
    }

    static int getMinSdkVersionForCodename(String codename) throws CodenameMinSdkVersionException {
        char cCharAt = codename.isEmpty() ? StringUtil.SPACE : codename.charAt(0);
        if (cCharAt >= 'A' && cCharAt <= 'Z') {
            Pair[] pairArr = CodenamesLazyInitializer.SORTED_CODENAMES_FIRST_CHAR_TO_API_LEVEL;
            int iBinarySearch = Arrays.binarySearch(pairArr, Pair.of(Character.valueOf(cCharAt), null), CodenamesLazyInitializer.CODENAME_FIRST_CHAR_COMPARATOR);
            if (iBinarySearch >= 0) {
                return ((Integer) pairArr[iBinarySearch].getSecond()).intValue();
            }
            if ((-1) - iBinarySearch == 0) {
                return 1;
            }
            Pair pair = pairArr[(-2) - iBinarySearch];
            return ((Integer) pair.getSecond()).intValue() + (cCharAt - ((Character) pair.getFirst()).charValue());
        }
        throw new CodenameMinSdkVersionException("Unable to determine APK's minimum supported Android platform version : Unsupported codename in AndroidManifest.xml's minSdkVersion: \"" + codename + "\"", codename);
    }

    public static boolean getDebuggableFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws ApkFormatException {
        try {
            AndroidBinXmlParser androidBinXmlParser = new AndroidBinXmlParser(androidManifestContents);
            for (int eventType = androidBinXmlParser.getEventType(); eventType != 2; eventType = androidBinXmlParser.next()) {
                if (eventType == 3 && androidBinXmlParser.getDepth() == 2 && "application".equals(androidBinXmlParser.getName()) && androidBinXmlParser.getNamespace().isEmpty()) {
                    for (int i = 0; i < androidBinXmlParser.getAttributeCount(); i++) {
                        if (androidBinXmlParser.getAttributeNameResourceId(i) == 16842767) {
                            int attributeValueType = androidBinXmlParser.getAttributeValueType(i);
                            if (attributeValueType != 1 && attributeValueType != 2) {
                                if (attributeValueType == 3) {
                                    throw new ApkFormatException("Unable to determine whether APK is debuggable: AndroidManifest.xml's android:debuggable attribute references a resource. References are not supported for security reasons. Only constant boolean, string and int values are supported.");
                                }
                                if (attributeValueType != 4) {
                                    throw new ApkFormatException("Unable to determine whether APK is debuggable: AndroidManifest.xml's android:debuggable attribute uses unsupported value type. Only boolean, string and int values are supported.");
                                }
                            }
                            String attributeStringValue = androidBinXmlParser.getAttributeStringValue(i);
                            return "true".equals(attributeStringValue) || "TRUE".equals(attributeStringValue) || SchemaSymbols.ATTVAL_TRUE_1.equals(attributeStringValue);
                        }
                    }
                    return false;
                }
            }
            return false;
        } catch (AndroidBinXmlParser.XmlParserException e) {
            throw new ApkFormatException("Unable to determine whether APK is debuggable: malformed binary resource: AndroidManifest.xml", e);
        }
    }

    public static String getPackageNameFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws ApkFormatException {
        try {
            AndroidBinXmlParser androidBinXmlParser = new AndroidBinXmlParser(androidManifestContents);
            for (int eventType = androidBinXmlParser.getEventType(); eventType != 2; eventType = androidBinXmlParser.next()) {
                if (eventType == 3 && androidBinXmlParser.getDepth() == 1 && "manifest".equals(androidBinXmlParser.getName()) && androidBinXmlParser.getNamespace().isEmpty()) {
                    for (int i = 0; i < androidBinXmlParser.getAttributeCount(); i++) {
                        if ("package".equals(androidBinXmlParser.getAttributeName(i)) && androidBinXmlParser.getNamespace().isEmpty()) {
                            return androidBinXmlParser.getAttributeStringValue(i);
                        }
                    }
                    return null;
                }
            }
            return null;
        } catch (AndroidBinXmlParser.XmlParserException e) {
            throw new ApkFormatException("Unable to determine APK package name: malformed binary resource: AndroidManifest.xml", e);
        }
    }

    public static int getTargetSandboxVersionFromBinaryAndroidManifest(ByteBuffer androidManifestContents) {
        try {
            return getAttributeValueFromBinaryAndroidManifest(androidManifestContents, "manifest", 16844108);
        } catch (ApkFormatException unused) {
            return 1;
        }
    }

    public static int getTargetSdkVersionFromBinaryAndroidManifest(ByteBuffer androidManifestContents) {
        try {
            return getAttributeValueFromBinaryAndroidManifest(androidManifestContents, USES_SDK_ELEMENT_TAG, 16843376);
        } catch (ApkFormatException unused) {
            androidManifestContents.rewind();
            try {
                return getMinSdkVersionFromBinaryAndroidManifest(androidManifestContents);
            } catch (ApkFormatException unused2) {
                return 1;
            }
        }
    }

    public static int getVersionCodeFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws ApkFormatException {
        return getAttributeValueFromBinaryAndroidManifest(androidManifestContents, "manifest", 16843291);
    }

    public static long getLongVersionCodeFromBinaryAndroidManifest(ByteBuffer androidManifestContents) throws ApkFormatException {
        long attributeValueFromBinaryAndroidManifest;
        int versionCodeFromBinaryAndroidManifest = getVersionCodeFromBinaryAndroidManifest(androidManifestContents);
        try {
            androidManifestContents.rewind();
            attributeValueFromBinaryAndroidManifest = getAttributeValueFromBinaryAndroidManifest(androidManifestContents, "manifest", 16844150);
        } catch (ApkFormatException unused) {
            attributeValueFromBinaryAndroidManifest = 0;
        }
        return (attributeValueFromBinaryAndroidManifest << 32) | ((long) versionCodeFromBinaryAndroidManifest);
    }

    private static int getAttributeValueFromBinaryAndroidManifest(ByteBuffer androidManifestContents, String elementName, int attributeId) throws ApkFormatException {
        if (elementName == null) {
            throw new NullPointerException("elementName cannot be null");
        }
        try {
            AndroidBinXmlParser androidBinXmlParser = new AndroidBinXmlParser(androidManifestContents);
            for (int eventType = androidBinXmlParser.getEventType(); eventType != 2; eventType = androidBinXmlParser.next()) {
                if (eventType == 3 && elementName.equals(androidBinXmlParser.getName())) {
                    for (int i = 0; i < androidBinXmlParser.getAttributeCount(); i++) {
                        if (androidBinXmlParser.getAttributeNameResourceId(i) == attributeId) {
                            int attributeValueType = androidBinXmlParser.getAttributeValueType(i);
                            if (attributeValueType != 1 && attributeValueType != 2) {
                                throw new ApkFormatException("Unsupported value type, " + attributeValueType + ", for attribute " + String.format("0x%08X", Integer.valueOf(attributeId)) + " under element " + elementName);
                            }
                            return androidBinXmlParser.getAttributeIntValue(i);
                        }
                    }
                }
            }
            throw new ApkFormatException("Failed to determine APK's " + elementName + " attribute " + String.format("0x%08X", Integer.valueOf(attributeId)) + " value");
        } catch (AndroidBinXmlParser.XmlParserException e) {
            throw new ApkFormatException("Unable to determine value for attribute " + String.format("0x%08X", Integer.valueOf(attributeId)) + " under element " + elementName + "; malformed binary resource: AndroidManifest.xml", e);
        }
    }

    public static byte[] computeSha256DigestBytes(byte[] data) {
        return ApkUtilsLite.computeSha256DigestBytes(data);
    }
}
