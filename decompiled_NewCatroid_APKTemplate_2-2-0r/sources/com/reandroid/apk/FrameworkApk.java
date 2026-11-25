package com.reandroid.apk;

import com.beust.jcommander.Parameters;
import com.reandroid.archive.ArchiveBytes;
import com.reandroid.archive.ArchiveFile;
import com.reandroid.archive.BlockInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.archive.ZipEntryMap;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.xml.AndroidManifestBlock;
import com.reandroid.arsc.chunk.xml.ResXmlAttribute;
import com.reandroid.arsc.model.FrameworkTable;
import com.reandroid.arsc.value.ValueType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.catrobat.catroid.web.ServerAuthenticationConstants;

/* JADX INFO: loaded from: classes10.dex */
public class FrameworkApk extends ApkModule {
    private boolean mDestroyed;
    private final Object mLock;
    private boolean mOptimizing;
    private String packageName;
    private int versionCode;
    private String versionName;

    public FrameworkApk(String str, ZipEntryMap zipEntryMap) {
        super(str, zipEntryMap);
        this.mLock = new Object();
        super.setLoadDefaultFramework(false);
    }

    public FrameworkApk(ZipEntryMap zipEntryMap) {
        this("framework", zipEntryMap);
    }

    @Override // com.reandroid.apk.ApkModule
    public void destroy() {
        synchronized (this.mLock) {
            this.versionCode = -1;
            this.versionName = ServerAuthenticationConstants.TOKEN_CODE_INVALID;
            this.packageName = "destroyed";
            super.destroy();
            this.mDestroyed = true;
        }
    }

    public boolean isDestroyed() {
        synchronized (this.mLock) {
            if (!this.mDestroyed) {
                return false;
            }
            if (!hasTableBlock()) {
                return true;
            }
            this.versionCode = 0;
            this.versionName = null;
            this.packageName = null;
            this.mDestroyed = false;
            return false;
        }
    }

    @Override // com.reandroid.apk.ApkModule
    public int getVersionCode() {
        if (this.versionCode == 0) {
            initValues();
        }
        return this.versionCode;
    }

    public String getVersionName() {
        if (this.versionName == null) {
            initValues();
        }
        return this.versionName;
    }

    @Override // com.reandroid.apk.ApkModule
    public String getPackageName() {
        if (this.packageName == null) {
            initValues();
        }
        return this.packageName;
    }

    @Override // com.reandroid.apk.ApkModule
    public void setPackageName(String str) {
        super.setPackageName(str);
        this.packageName = null;
    }

    private void initValues() {
        PackageBlock packageBlockPickOne;
        int versionCode;
        if (hasAndroidManifest()) {
            AndroidManifestBlock androidManifest = getAndroidManifest();
            Integer versionCode2 = androidManifest.getVersionCode();
            if (versionCode2 != null) {
                this.versionCode = versionCode2.intValue();
            }
            if (this.versionName == null) {
                this.versionName = androidManifest.getVersionName();
            }
            if (this.packageName == null) {
                this.packageName = androidManifest.getPackageName();
            }
        }
        if (hasTableBlock()) {
            FrameworkTable tableBlock = getTableBlock();
            if (tableBlock.isOptimized() && this.versionCode == 0 && (versionCode = tableBlock.getVersionCode()) != 0) {
                this.versionCode = versionCode;
                if (this.versionName == null) {
                    this.versionName = String.valueOf(versionCode);
                }
            }
            if (this.packageName != null || (packageBlockPickOne = tableBlock.pickOne()) == null) {
                return;
            }
            this.packageName = packageBlockPickOne.getName();
        }
    }

    @Override // com.reandroid.apk.ApkModule
    public void setManifest(AndroidManifestBlock androidManifestBlock) {
        synchronized (this.mLock) {
            super.setManifest(androidManifestBlock);
            this.versionCode = 0;
            this.versionName = null;
            this.packageName = null;
        }
    }

    @Override // com.reandroid.apk.ApkModule
    public void setTableBlock(TableBlock tableBlock) {
        synchronized (this.mLock) {
            super.setTableBlock(tableBlock);
            this.versionCode = 0;
            this.versionName = null;
            this.packageName = null;
        }
    }

    @Override // com.reandroid.apk.ApkModule, com.reandroid.arsc.ApkFile
    public FrameworkTable getTableBlock() {
        return (FrameworkTable) super.getTableBlock();
    }

    @Override // com.reandroid.apk.ApkModule, com.reandroid.arsc.ApkFile
    public FrameworkTable getLoadedTableBlock() {
        return (FrameworkTable) super.getLoadedTableBlock();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.apk.ApkModule
    public FrameworkTable loadTableBlock() throws IOException {
        ZipEntryMap zipEntryMap = getZipEntryMap();
        InputSource inputSource = zipEntryMap.getInputSource(TableBlock.FILE_NAME);
        if (inputSource == null) {
            throw new IOException("Entry not found: " + TableBlock.FILE_NAME);
        }
        FrameworkTable frameworkTableLoad = FrameworkTable.load(inputSource.openStream());
        frameworkTableLoad.setApkFile(this);
        BlockInputSource blockInputSource = new BlockInputSource(inputSource.getName(), frameworkTableLoad);
        blockInputSource.setMethod(inputSource.getMethod());
        blockInputSource.setSort(inputSource.getSort());
        zipEntryMap.add(blockInputSource);
        return frameworkTableLoad;
    }

    public void optimize() {
        synchronized (this.mLock) {
            if (this.mOptimizing) {
                return;
            }
            if (!hasTableBlock()) {
                this.mOptimizing = false;
                return;
            }
            if (getTableBlock().isOptimized()) {
                this.mOptimizing = false;
                initValues();
            } else {
                new FrameworkOptimizer(this).optimize();
                this.mOptimizing = false;
                initValues();
            }
        }
    }

    public String getName() {
        if (isDestroyed()) {
            return "destroyed";
        }
        String packageName = getPackageName();
        if (packageName == null) {
            return "";
        }
        return packageName + Parameters.DEFAULT_OPTION_PREFIXES + getVersionCode();
    }

    public int hashCode() {
        return Objects.hash(getClass(), getName());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return getName().equals(((FrameworkApk) obj).getName());
    }

    @Override // com.reandroid.apk.ApkModule
    public String toString() {
        return getName();
    }

    public static FrameworkApk loadApkFile(File file) throws IOException {
        return loadApkFile(file, true);
    }

    public static FrameworkApk loadTableBlock(File file) throws IOException {
        return loadApkFile(file, false);
    }

    private static FrameworkApk loadApkFile(File file, boolean z) throws IOException {
        ArchiveFile archiveFile = new ArchiveFile(file);
        InputSource entrySource = archiveFile.getEntrySource(TableBlock.FILE_NAME);
        if (entrySource == null) {
            throw new IOException("Missing " + TableBlock.FILE_NAME + ", on " + file);
        }
        ZipEntryMap zipEntryMap = new ZipEntryMap();
        zipEntryMap.add(entrySource);
        if (z) {
            zipEntryMap.add(archiveFile.getEntrySource(AndroidManifestBlock.FILE_NAME));
        }
        FrameworkApk frameworkApk = new FrameworkApk(zipEntryMap);
        frameworkApk.setCloseable(archiveFile);
        return frameworkApk;
    }

    public static FrameworkApk loadApkFile(File file, String str) throws IOException {
        ArchiveFile archiveFile = new ArchiveFile(file);
        FrameworkApk frameworkApk = new FrameworkApk(str, archiveFile.createZipEntryMap());
        frameworkApk.setCloseable(archiveFile);
        return frameworkApk;
    }

    public static boolean isFramework(ApkModule apkModule) {
        if (apkModule.hasAndroidManifest()) {
            return isFramework(apkModule.getAndroidManifest());
        }
        return false;
    }

    public static boolean isFramework(AndroidManifestBlock androidManifestBlock) {
        ResXmlAttribute resXmlAttributeSearchAttributeByName = androidManifestBlock.getManifestElement().searchAttributeByName(AndroidManifestBlock.NAME_coreApp);
        if (resXmlAttributeSearchAttributeByName == null || resXmlAttributeSearchAttributeByName.getValueType() != ValueType.BOOLEAN) {
            return false;
        }
        return resXmlAttributeSearchAttributeByName.getValueAsBoolean();
    }

    public static FrameworkApk loadApkBuffer(InputStream inputStream) throws IOException {
        return loadApkBuffer("framework", inputStream);
    }

    public static FrameworkApk loadApkBuffer(String str, InputStream inputStream) throws IOException {
        FrameworkApk frameworkApk = new FrameworkApk(str, new ArchiveBytes(inputStream).createZipEntryMap());
        frameworkApk.initValues();
        return frameworkApk;
    }

    public static void optimize(File file, File file2, APKLogger aPKLogger) throws IOException {
        FrameworkApk frameworkApkLoadApkFile = loadApkFile(file);
        frameworkApkLoadApkFile.setAPKLogger(aPKLogger);
        frameworkApkLoadApkFile.optimize();
        frameworkApkLoadApkFile.writeApk(file2);
    }
}
