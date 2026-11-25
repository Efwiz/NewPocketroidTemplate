package org.jetbrains.kotlin.load.kotlin;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.FileNotFoundException;
import java.io.IOException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ControlFlowException;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.util.PerformanceCounter;

/* JADX INFO: compiled from: VirtualFileKotlinClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB/\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0014J\b\u0010\u001b\u001a\u00020\u0007H\u0016J\b\u0010\u001c\u001a\u00020\u000eH\u0016R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/VirtualFileKotlinClass;", "Lorg/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "className", "Lorg/jetbrains/kotlin/name/ClassId;", "classVersion", "", "classHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "innerClasses", "Lorg/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$InnerClassesInfo;", "(Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/name/ClassId;ILorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;Lorg/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$InnerClassesInfo;)V", "containingLibrary", "", "getContainingLibrary", "()Ljava/lang/String;", "getFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", FirebaseAnalytics.Param.LOCATION, "getLocation", Namer.EQUALS_METHOD_NAME, "", "other", "", "getFileContents", "", "hashCode", "toString", "Factory", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VirtualFileKotlinClass extends FileBasedKotlinClass {

    /* JADX INFO: renamed from: Factory, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Logger LOG;
    private static final PerformanceCounter perfCounter;
    private final VirtualFile file;

    public /* synthetic */ VirtualFileKotlinClass(VirtualFile virtualFile, ClassId classId, int i, KotlinClassHeader kotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo innerClassesInfo, DefaultConstructorMarker defaultConstructorMarker) {
        this(virtualFile, classId, i, kotlinClassHeader, innerClassesInfo);
    }

    public final VirtualFile getFile() {
        return this.file;
    }

    private VirtualFileKotlinClass(VirtualFile virtualFile, ClassId classId, int i, KotlinClassHeader kotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo innerClassesInfo) {
        super(classId, i, kotlinClassHeader, innerClassesInfo);
        this.file = virtualFile;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public String getLocation() {
        String path = this.file.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        return path;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public String getContainingLibrary() {
        String path = this.file.getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        return (String) CollectionsKt.firstOrNull(StringsKt.split$default((CharSequence) path, new String[]{"!/"}, false, 0, 6, (Object) null));
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    protected byte[] getFileContents() throws Throwable {
        try {
            byte[] bArrContentsToByteArray = this.file.contentsToByteArray();
            Intrinsics.checkNotNullExpressionValue(bArrContentsToByteArray, "contentsToByteArray(...)");
            return bArrContentsToByteArray;
        } catch (IOException e) {
            throw INSTANCE.logFileReadingErrorMessage(e, this.file);
        }
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    public boolean equals(Object other) {
        return (other instanceof VirtualFileKotlinClass) && Intrinsics.areEqual(((VirtualFileKotlinClass) other).file, this.file);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    public int hashCode() {
        return this.file.hashCode();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    public String toString() {
        return getClass().getSimpleName() + ": " + this.file;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.load.kotlin.VirtualFileKotlinClass$Factory, reason: from kotlin metadata */
    /* JADX INFO: compiled from: VirtualFileKotlinClass.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J)\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0000¢\u0006\u0002\b\u0010J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/VirtualFileKotlinClass$Factory;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "perfCounter", "Lorg/jetbrains/kotlin/util/PerformanceCounter;", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder$Result;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "fileContent", "", "create$frontend_common_jvm", "logFileReadingErrorMessage", "", "e", "renderFileReadingErrorMessage", "", "frontend.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinClassFinder.Result create$frontend_common_jvm(final VirtualFile file, final JvmMetadataVersion jvmMetadataVersion, final byte[] fileContent) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
            return (KotlinClassFinder.Result) VirtualFileKotlinClass.perfCounter.time(new Function0<KotlinClassFinder.Result>() { // from class: org.jetbrains.kotlin.load.kotlin.VirtualFileKotlinClass$Factory$create$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final KotlinClassFinder.Result invoke() throws Throwable {
                    if (!Intrinsics.areEqual(file.getExtension(), JavaClassFileType.INSTANCE.getDefaultExtension())) {
                        Intrinsics.areEqual(file.getFileType(), JavaClassFileType.INSTANCE);
                    }
                    VirtualFile virtualFile = file;
                    try {
                        byte[] bArrContentsToByteArray = fileContent;
                        if (bArrContentsToByteArray == null) {
                            bArrContentsToByteArray = virtualFile.contentsToByteArray(false);
                            Intrinsics.checkNotNullExpressionValue(bArrContentsToByteArray, "contentsToByteArray(...)");
                        }
                        if (bArrContentsToByteArray.length == 0) {
                            return null;
                        }
                        JvmMetadataVersion jvmMetadataVersion2 = jvmMetadataVersion;
                        final VirtualFile virtualFile2 = file;
                        VirtualFileKotlinClass virtualFileKotlinClass = (VirtualFileKotlinClass) FileBasedKotlinClass.create(bArrContentsToByteArray, jvmMetadataVersion2, new Function4<ClassId, Integer, KotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo, VirtualFileKotlinClass>() { // from class: org.jetbrains.kotlin.load.kotlin.VirtualFileKotlinClass$Factory$create$1$kotlinJvmBinaryClass$1
                            {
                                super(4);
                            }

                            @Override // kotlin.jvm.functions.Function4
                            public final VirtualFileKotlinClass invoke(ClassId classId, Integer num, KotlinClassHeader kotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo innerClassesInfo) {
                                VirtualFile virtualFile3 = virtualFile2;
                                Intrinsics.checkNotNull(classId);
                                Intrinsics.checkNotNull(num);
                                int iIntValue = num.intValue();
                                Intrinsics.checkNotNull(kotlinClassHeader);
                                Intrinsics.checkNotNull(innerClassesInfo);
                                return new VirtualFileKotlinClass(virtualFile3, classId, iIntValue, kotlinClassHeader, innerClassesInfo, null);
                            }
                        });
                        if (virtualFileKotlinClass != null) {
                            return new KotlinClassFinder.Result.KotlinClass(virtualFileKotlinClass, bArrContentsToByteArray);
                        }
                        return new KotlinClassFinder.Result.ClassFileContent(bArrContentsToByteArray);
                    } catch (FileNotFoundException unused) {
                        return null;
                    } catch (Throwable th) {
                        if (th instanceof ControlFlowException) {
                            throw th;
                        }
                        throw VirtualFileKotlinClass.INSTANCE.logFileReadingErrorMessage(th, file);
                    }
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Throwable logFileReadingErrorMessage(Throwable e, VirtualFile file) {
            String strRenderFileReadingErrorMessage = renderFileReadingErrorMessage(file);
            VirtualFileKotlinClass.LOG.warn(strRenderFileReadingErrorMessage, e);
            return new IllegalStateException(strRenderFileReadingErrorMessage, e);
        }

        private final String renderFileReadingErrorMessage(VirtualFile file) {
            return "Could not read file: " + file.getPath() + "; size in bytes: " + file.getLength() + "; file type: " + file.getFileType().getName();
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) VirtualFileKotlinClass.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
        perfCounter = PerformanceCounter.Companion.create$default(PerformanceCounter.INSTANCE, "Binary class from Kotlin file", false, 2, null);
    }
}
