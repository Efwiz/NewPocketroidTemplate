package org.jetbrains.kotlin.incremental;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;

/* JADX INFO: compiled from: LocalFileKotlinClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB7\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0096\u0002J\b\u0010\u001c\u001a\u00020\u0005H\u0016J\b\u0010\u001d\u001a\u00020\tH\u0016J\b\u0010\u001e\u001a\u00020\u0015H\u0016R\u001b\u0010\u0006\u001a\u00020\u000f8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/incremental/LocalFileKotlinClass;", "Lorg/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass;", "file", "Ljava/io/File;", "fileContents", "", "className", "Lorg/jetbrains/kotlin/name/ClassId;", "classVersion", "", "classHeader", "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;", "innerClasses", "Lorg/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$InnerClassesInfo;", "(Ljava/io/File;[BLorg/jetbrains/kotlin/name/ClassId;ILorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader;Lorg/jetbrains/kotlin/load/kotlin/FileBasedKotlinClass$InnerClassesInfo;)V", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getClassName", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "className$delegate", "Lkotlin/Lazy;", FirebaseAnalytics.Param.LOCATION, "", "getLocation", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getFileContents", "hashCode", "toString", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalFileKotlinClass extends FileBasedKotlinClass {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: renamed from: className$delegate, reason: from kotlin metadata */
    private final Lazy className;
    private final File file;
    private final byte[] fileContents;

    public /* synthetic */ LocalFileKotlinClass(File file, byte[] bArr, ClassId classId, int i, KotlinClassHeader kotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo innerClassesInfo, DefaultConstructorMarker defaultConstructorMarker) {
        this(file, bArr, classId, i, kotlinClassHeader, innerClassesInfo);
    }

    private LocalFileKotlinClass(File file, byte[] bArr, ClassId classId, int i, KotlinClassHeader kotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo innerClassesInfo) {
        super(classId, i, kotlinClassHeader, innerClassesInfo);
        this.file = file;
        this.fileContents = bArr;
        this.className = LazyKt.lazy(new Function0<JvmClassName>() { // from class: org.jetbrains.kotlin.incremental.LocalFileKotlinClass$className$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final JvmClassName invoke() {
                JvmClassName jvmClassNameByClassId = JvmClassName.byClassId(this.this$0.getClassId());
                Intrinsics.checkNotNullExpressionValue(jvmClassNameByClassId, "byClassId(...)");
                return jvmClassNameByClassId;
            }
        });
    }

    /* JADX INFO: compiled from: LocalFileKotlinClass.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/incremental/LocalFileKotlinClass$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/incremental/LocalFileKotlinClass;", "file", "Ljava/io/File;", "jvmMetadataVersionFromLanguageVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final LocalFileKotlinClass create(final File file, JvmMetadataVersion jvmMetadataVersionFromLanguageVersion) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(jvmMetadataVersionFromLanguageVersion, "jvmMetadataVersionFromLanguageVersion");
            final byte[] bytes = FilesKt.readBytes(file);
            return (LocalFileKotlinClass) FileBasedKotlinClass.create(bytes, jvmMetadataVersionFromLanguageVersion, new Function4<ClassId, Integer, KotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo, LocalFileKotlinClass>() { // from class: org.jetbrains.kotlin.incremental.LocalFileKotlinClass$Companion$create$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(4);
                }

                @Override // kotlin.jvm.functions.Function4
                public final LocalFileKotlinClass invoke(ClassId classId, Integer num, KotlinClassHeader kotlinClassHeader, FileBasedKotlinClass.InnerClassesInfo innerClassesInfo) {
                    File file2 = file;
                    byte[] bArr = bytes;
                    Intrinsics.checkNotNull(classId);
                    Intrinsics.checkNotNull(num);
                    int iIntValue = num.intValue();
                    Intrinsics.checkNotNull(kotlinClassHeader);
                    Intrinsics.checkNotNull(innerClassesInfo);
                    return new LocalFileKotlinClass(file2, bArr, classId, iIntValue, kotlinClassHeader, innerClassesInfo, null);
                }
            });
        }
    }

    public final JvmClassName getClassName() {
        return (JvmClassName) this.className.getValue();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass
    public String getLocation() {
        String absolutePath = this.file.getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
        return absolutePath;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    public byte[] getFileContents() {
        return this.fileContents;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    public int hashCode() {
        return this.file.hashCode();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    public boolean equals(Object other) {
        return (other instanceof LocalFileKotlinClass) && Intrinsics.areEqual(this.file, ((LocalFileKotlinClass) other).file);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.FileBasedKotlinClass
    public String toString() {
        return getClass() + ": " + this.file;
    }
}
