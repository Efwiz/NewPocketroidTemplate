package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import com.google.firebase.database.core.ServerValues;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileWithId;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.KotlinBinaryClassCache;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: ClsKotlinBinaryClassCache.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 #2\u00020\u0001:\u0003#$%B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u001a\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0015J3\u0010\u001a\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0019H\u0002¢\u0006\u0002\u0010\u001eJ\u0014\u0010\u001f\u001a\u00020\u000e*\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002R-\u0010\u0003\u001a!\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0006 \u0007*\n\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u00050\u00050\u0004¢\u0006\u0002\b\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n \u0007*\u0004\u0018\u00010\f0\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache;", "", "()V", "KOTLIN_BINARY_DATA_KEY", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "Lorg/jetbrains/kotlin/com/intellij/reference/SoftReference;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$KotlinBinaryData;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "KOTLIN_IS_COMPILED_FILE_ATTRIBUTE", "", "attributeService", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/FileAttributeService;", "createHeaderInfo", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$KotlinBinaryClassHeaderData;", "kotlinBinaryClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "getKotlinBinaryClass", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "fileContent", "", "getKotlinBinaryClassHeaderData", "getKotlinBinaryFromCache", "isKotlinJvmCompiledFile", "", "kotlinJvmBinaryClass", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "isKotlinBinary", "(Lcom/intellij/openapi/vfs/VirtualFile;[BLorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;Ljava/lang/Boolean;)Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "toLightHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "Companion", "KotlinBinaryClassHeaderData", "KotlinBinaryData", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClsKotlinBinaryClassCache {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Key<SoftReference<KotlinBinaryData>> KOTLIN_BINARY_DATA_KEY;
    private final String KOTLIN_IS_COMPILED_FILE_ATTRIBUTE;
    private final FileAttributeService attributeService;

    /* JADX INFO: compiled from: ClsKotlinBinaryClassCache.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u00168F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$KotlinBinaryClassHeaderData;", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "partNamesIfMultifileFacade", "", "", "packageName", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;Ljava/util/List;Ljava/lang/String;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getKind", "()Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getPackageName", "()Ljava/lang/String;", "packageNameWithFallback", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageNameWithFallback", "()Lorg/jetbrains/kotlin/name/FqName;", "getPartNamesIfMultifileFacade", "()Ljava/util/List;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class KotlinBinaryClassHeaderData {
        private final ClassId classId;
        private final KotlinClassHeader.Kind kind;
        private final JvmMetadataVersion metadataVersion;
        private final String packageName;
        private final List<String> partNamesIfMultifileFacade;

        public KotlinBinaryClassHeaderData(ClassId classId, KotlinClassHeader.Kind kind, JvmMetadataVersion metadataVersion, List<String> partNamesIfMultifileFacade, String str) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
            Intrinsics.checkNotNullParameter(partNamesIfMultifileFacade, "partNamesIfMultifileFacade");
            this.classId = classId;
            this.kind = kind;
            this.metadataVersion = metadataVersion;
            this.partNamesIfMultifileFacade = partNamesIfMultifileFacade;
            this.packageName = str;
        }

        public final ClassId getClassId() {
            return this.classId;
        }

        public final KotlinClassHeader.Kind getKind() {
            return this.kind;
        }

        public final JvmMetadataVersion getMetadataVersion() {
            return this.metadataVersion;
        }

        public final List<String> getPartNamesIfMultifileFacade() {
            return this.partNamesIfMultifileFacade;
        }

        public final String getPackageName() {
            return this.packageName;
        }

        public final FqName getPackageNameWithFallback() {
            String str = this.packageName;
            if (str != null) {
                return new FqName(str);
            }
            FqName packageFqName = this.classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
            return packageFqName;
        }
    }

    public ClsKotlinBinaryClassCache() {
        FileAttributeService attributeService = (FileAttributeService) ApplicationManager.getApplication().getService(FileAttributeService.class);
        this.attributeService = attributeService;
        Intrinsics.checkNotNullExpressionValue(attributeService, "attributeService");
        FileAttributeService.register$default(attributeService, "kotlin-is-binary-compiled", 2, false, 4, null);
        this.KOTLIN_IS_COMPILED_FILE_ATTRIBUTE = "kotlin-is-binary-compiled";
        Key<SoftReference<KotlinBinaryData>> keyCreate = Key.create("kotlin-is-binary-compiled");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        this.KOTLIN_BINARY_DATA_KEY = keyCreate;
    }

    /* JADX INFO: compiled from: ClsKotlinBinaryClassCache.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0007HÆ\u0003J)\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00032\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$KotlinBinaryData;", "", "isKotlinBinary", "", ServerValues.NAME_OP_TIMESTAMP, "", "headerData", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$KotlinBinaryClassHeaderData;", "(ZJLorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$KotlinBinaryClassHeaderData;)V", "getHeaderData", "()Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$KotlinBinaryClassHeaderData;", "()Z", "getTimestamp", "()J", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class KotlinBinaryData {
        private final KotlinBinaryClassHeaderData headerData;
        private final boolean isKotlinBinary;
        private final long timestamp;

        public static /* synthetic */ KotlinBinaryData copy$default(KotlinBinaryData kotlinBinaryData, boolean z, long j, KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData, int i, Object obj) {
            if ((i & 1) != 0) {
                z = kotlinBinaryData.isKotlinBinary;
            }
            if ((i & 2) != 0) {
                j = kotlinBinaryData.timestamp;
            }
            if ((i & 4) != 0) {
                kotlinBinaryClassHeaderData = kotlinBinaryData.headerData;
            }
            return kotlinBinaryData.copy(z, j, kotlinBinaryClassHeaderData);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getIsKotlinBinary() {
            return this.isKotlinBinary;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final long getTimestamp() {
            return this.timestamp;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final KotlinBinaryClassHeaderData getHeaderData() {
            return this.headerData;
        }

        public final KotlinBinaryData copy(boolean isKotlinBinary, long timestamp, KotlinBinaryClassHeaderData headerData) {
            return new KotlinBinaryData(isKotlinBinary, timestamp, headerData);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof KotlinBinaryData)) {
                return false;
            }
            KotlinBinaryData kotlinBinaryData = (KotlinBinaryData) other;
            return this.isKotlinBinary == kotlinBinaryData.isKotlinBinary && this.timestamp == kotlinBinaryData.timestamp && Intrinsics.areEqual(this.headerData, kotlinBinaryData.headerData);
        }

        public int hashCode() {
            int iHashCode = ((Boolean.hashCode(this.isKotlinBinary) * 31) + Long.hashCode(this.timestamp)) * 31;
            KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData = this.headerData;
            return iHashCode + (kotlinBinaryClassHeaderData == null ? 0 : kotlinBinaryClassHeaderData.hashCode());
        }

        public String toString() {
            return "KotlinBinaryData(isKotlinBinary=" + this.isKotlinBinary + ", timestamp=" + this.timestamp + ", headerData=" + this.headerData + ')';
        }

        public KotlinBinaryData(boolean z, long j, KotlinBinaryClassHeaderData kotlinBinaryClassHeaderData) {
            this.isKotlinBinary = z;
            this.timestamp = j;
            this.headerData = kotlinBinaryClassHeaderData;
        }

        public final KotlinBinaryClassHeaderData getHeaderData() {
            return this.headerData;
        }

        public final long getTimestamp() {
            return this.timestamp;
        }

        public final boolean isKotlinBinary() {
            return this.isKotlinBinary;
        }
    }

    public static /* synthetic */ boolean isKotlinJvmCompiledFile$default(ClsKotlinBinaryClassCache clsKotlinBinaryClassCache, VirtualFile virtualFile, byte[] bArr, int i, Object obj) {
        if ((i & 2) != 0) {
            bArr = null;
        }
        return clsKotlinBinaryClassCache.isKotlinJvmCompiledFile(virtualFile, bArr);
    }

    public final boolean isKotlinJvmCompiledFile(VirtualFile file, byte[] fileContent) {
        Intrinsics.checkNotNullParameter(file, "file");
        String extension = file.getExtension();
        JavaClassFileType javaClassFileType = JavaClassFileType.INSTANCE;
        Intrinsics.checkNotNull(javaClassFileType);
        if (!Intrinsics.areEqual(extension, javaClassFileType.getDefaultExtension())) {
            return false;
        }
        KotlinBinaryData kotlinBinaryFromCache = getKotlinBinaryFromCache(file);
        if (kotlinBinaryFromCache != null) {
            return kotlinBinaryFromCache.isKotlinBinary();
        }
        return kotlinJvmBinaryClass(file, fileContent, JvmMetadataVersion.INSTANCE, null) != null;
    }

    public static /* synthetic */ KotlinJvmBinaryClass getKotlinBinaryClass$default(ClsKotlinBinaryClassCache clsKotlinBinaryClassCache, VirtualFile virtualFile, byte[] bArr, int i, Object obj) {
        if ((i & 2) != 0) {
            bArr = null;
        }
        return clsKotlinBinaryClassCache.getKotlinBinaryClass(virtualFile, bArr);
    }

    public final KotlinJvmBinaryClass getKotlinBinaryClass(VirtualFile file, byte[] fileContent) {
        JvmMetadataVersion metadataVersion;
        KotlinBinaryClassHeaderData headerData;
        Intrinsics.checkNotNullParameter(file, "file");
        KotlinBinaryData kotlinBinaryFromCache = getKotlinBinaryFromCache(file);
        if (kotlinBinaryFromCache != null && !kotlinBinaryFromCache.isKotlinBinary()) {
            return null;
        }
        if (kotlinBinaryFromCache == null || (headerData = kotlinBinaryFromCache.getHeaderData()) == null || (metadataVersion = headerData.getMetadataVersion()) == null) {
            metadataVersion = JvmMetadataVersion.INSTANCE;
        }
        return kotlinJvmBinaryClass(file, fileContent, metadataVersion, kotlinBinaryFromCache != null ? Boolean.valueOf(kotlinBinaryFromCache.isKotlinBinary()) : null);
    }

    private final KotlinJvmBinaryClass kotlinJvmBinaryClass(VirtualFile virtualFile, byte[] bArr, JvmMetadataVersion jvmMetadataVersion, Boolean bool) throws Exception {
        try {
            KotlinClassFinder.Result kotlinBinaryClassOrClassFileContent = KotlinBinaryClassCache.INSTANCE.getKotlinBinaryClassOrClassFileContent(virtualFile, jvmMetadataVersion, bArr);
            KotlinJvmBinaryClass kotlinJvmBinaryClass = kotlinBinaryClassOrClassFileContent != null ? kotlinBinaryClassOrClassFileContent.toKotlinJvmBinaryClass() : null;
            boolean z = kotlinJvmBinaryClass != null;
            if ((virtualFile instanceof VirtualFileWithId) && !Intrinsics.areEqual(Boolean.valueOf(z), bool)) {
                this.attributeService.writeBooleanAttribute(this.KOTLIN_IS_COMPILED_FILE_ATTRIBUTE, virtualFile, z);
            }
            if (z) {
                Intrinsics.checkNotNull(kotlinJvmBinaryClass);
                virtualFile.putUserData(this.KOTLIN_BINARY_DATA_KEY, new SoftReference(new KotlinBinaryData(z, virtualFile.getTimeStamp(), createHeaderInfo(kotlinJvmBinaryClass))));
            }
            return kotlinJvmBinaryClass;
        } catch (Exception e) {
            if (e instanceof ControlFlowException) {
                throw e;
            }
            return null;
        }
    }

    public static /* synthetic */ KotlinBinaryClassHeaderData getKotlinBinaryClassHeaderData$default(ClsKotlinBinaryClassCache clsKotlinBinaryClassCache, VirtualFile virtualFile, byte[] bArr, int i, Object obj) {
        if ((i & 2) != 0) {
            bArr = null;
        }
        return clsKotlinBinaryClassCache.getKotlinBinaryClassHeaderData(virtualFile, bArr);
    }

    public final KotlinBinaryClassHeaderData getKotlinBinaryClassHeaderData(VirtualFile file, byte[] fileContent) throws Exception {
        Intrinsics.checkNotNullParameter(file, "file");
        KotlinBinaryData kotlinBinaryFromCache = getKotlinBinaryFromCache(file);
        if (kotlinBinaryFromCache != null) {
            if (!kotlinBinaryFromCache.isKotlinBinary()) {
                return null;
            }
            if (kotlinBinaryFromCache.getHeaderData() != null) {
                return kotlinBinaryFromCache.getHeaderData();
            }
        }
        KotlinJvmBinaryClass kotlinJvmBinaryClass = kotlinJvmBinaryClass(file, fileContent, JvmMetadataVersion.INSTANCE, kotlinBinaryFromCache != null ? Boolean.valueOf(kotlinBinaryFromCache.isKotlinBinary()) : null);
        if (kotlinJvmBinaryClass == null) {
            return null;
        }
        return createHeaderInfo(kotlinJvmBinaryClass);
    }

    private final KotlinBinaryClassHeaderData createHeaderInfo(KotlinJvmBinaryClass kotlinBinaryClass) {
        return toLightHeader(kotlinBinaryClass.getClassHeader(), kotlinBinaryClass.getClassId());
    }

    private final KotlinBinaryClassHeaderData toLightHeader(KotlinClassHeader kotlinClassHeader, ClassId classId) {
        return new KotlinBinaryClassHeaderData(classId, kotlinClassHeader.getKind(), kotlinClassHeader.getMetadataVersion(), kotlinClassHeader.getMultifilePartNames(), kotlinClassHeader.getPackageName());
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final KotlinBinaryData getKotlinBinaryFromCache(VirtualFile file) {
        SoftReference softReference = (SoftReference) file.getUserData(this.KOTLIN_BINARY_DATA_KEY);
        KotlinBinaryData kotlinBinaryData = softReference != null ? (KotlinBinaryData) softReference.get() : null;
        if (kotlinBinaryData != null && kotlinBinaryData.getTimestamp() == file.getTimeStamp()) {
            return kotlinBinaryData;
        }
        CachedAttributeData<Boolean> booleanAttribute = file instanceof VirtualFileWithId ? this.attributeService.readBooleanAttribute(this.KOTLIN_IS_COMPILED_FILE_ATTRIBUTE, file) : null;
        if (booleanAttribute == null) {
            return null;
        }
        boolean zBooleanValue = booleanAttribute.getValue().booleanValue();
        KotlinBinaryData kotlinBinaryData2 = new KotlinBinaryData(zBooleanValue, file.getTimeStamp(), null);
        if (zBooleanValue) {
            file.putUserData(this.KOTLIN_BINARY_DATA_KEY, new SoftReference(kotlinBinaryData2));
        }
        return kotlinBinaryData2;
    }

    /* JADX INFO: compiled from: ClsKotlinBinaryClassCache.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache$Companion;", "", "()V", "getInstance", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/ClsKotlinBinaryClassCache;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ClsKotlinBinaryClassCache getInstance() {
            Object service = ApplicationManager.getApplication().getService(ClsKotlinBinaryClassCache.class);
            Intrinsics.checkNotNullExpressionValue(service, "getService(...)");
            return (ClsKotlinBinaryClassCache) service;
        }
    }
}
