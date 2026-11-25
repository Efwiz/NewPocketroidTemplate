package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuilderComponents;
import org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuilderContext;
import org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuildingKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmNameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: KotlinClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinClsStubBuilder;", "Lorg/jetbrains/kotlin/com/intellij/psi/compiled/ClsStubBuilder;", "()V", "buildFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/PsiFileStub;", FirebaseAnalytics.Param.CONTENT, "Lorg/jetbrains/kotlin/com/intellij/util/indexing/FileContent;", "createStubBuilderComponents", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/ClsStubBuilderComponents;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "fileContent", "", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "doBuildFileStub", "Lorg/jetbrains/kotlin/psi/KtFile;", "getStubVersion", "", "Companion", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinClsStubBuilder extends ClsStubBuilder {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Logger LOG;
    private static final String VERSIONED_PATH_MARKER;

    /* JADX INFO: compiled from: KotlinClsStubBuilder.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinClassHeader.Kind.values().length];
            try {
                iArr[KotlinClassHeader.Kind.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinClassHeader.Kind.FILE_FACADE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder
    public int getStubVersion() {
        return 118;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder
    public PsiFileStub<?> buildFileStub(FileContent content) {
        Intrinsics.checkNotNullParameter(content, "content");
        VirtualFile file = content.getFile();
        Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
        if (ClsClassFinder.INSTANCE.isKotlinInternalCompiledFile(file, content.getContent()) || INSTANCE.isVersioned(file)) {
            return null;
        }
        byte[] content2 = content.getContent();
        Intrinsics.checkNotNullExpressionValue(content2, "getContent(...)");
        return doBuildFileStub(file, content2);
    }

    private final PsiFileStub<KtFile> doBuildFileStub(VirtualFile file, byte[] fileContent) {
        FqName packageFqName;
        FqName fqNameAsSingleFqName;
        KotlinJvmBinaryClass kotlinBinaryClass = ClsKotlinBinaryClassCache.INSTANCE.getInstance().getKotlinBinaryClass(file, fileContent);
        if (kotlinBinaryClass == null) {
            throw new IllegalStateException(("Can't find binary class for Kotlin file: " + file).toString());
        }
        KotlinClassHeader classHeader = kotlinBinaryClass.getClassHeader();
        ClassId classId = kotlinBinaryClass.getClassId();
        String packageName = classHeader.getPackageName();
        if (packageName != null) {
            packageFqName = new FqName(packageName);
        } else {
            packageFqName = classId.getPackageFqName();
            Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        }
        if (!classHeader.getMetadataVersion().isCompatibleWithCurrentCompilerVersion()) {
            return ClsStubBuildingKt.createIncompatibleAbiVersionFileStub();
        }
        ClsStubBuilderComponents clsStubBuilderComponentsCreateStubBuilderComponents = createStubBuilderComponents(file, packageFqName, fileContent, classHeader.getMetadataVersion());
        if (classHeader.getKind() == KotlinClassHeader.Kind.MULTIFILE_CLASS) {
            List<KotlinJvmBinaryClass> listFindMultifileClassParts = ClsClassFinder.INSTANCE.findMultifileClassParts(file, classId, classHeader.getMultifilePartNames());
            FqName fqNameAsSingleFqName2 = classId.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName2, "asSingleFqName(...)");
            return ClsStubBuildingKt.createMultifileClassStub(classHeader, listFindMultifileClassParts, fqNameAsSingleFqName2, clsStubBuilderComponentsCreateStubBuilderComponents);
        }
        String[] data = classHeader.getData();
        if (data == null) {
            LOG.error("Corrupted kotlin header for file " + file.getName());
            return null;
        }
        String[] strings = classHeader.getStrings();
        if (strings == null) {
            LOG.error("String table not found in file " + file.getName());
            return null;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[classHeader.getKind().ordinal()];
        if (i == 1) {
            if (classId.isLocal()) {
                return null;
            }
            Pair<JvmNameResolver, ProtoBuf.Class> classDataFrom = JvmProtoBufUtil.readClassDataFrom(data, strings);
            JvmNameResolver jvmNameResolverComponent1 = classDataFrom.component1();
            ProtoBuf.Class classComponent2 = classDataFrom.component2();
            if (Flags.VISIBILITY.get(classComponent2.getFlags()) == ProtoBuf.Visibility.LOCAL) {
                return null;
            }
            ProtoBuf.TypeTable typeTable = classComponent2.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
            return ClsStubBuildingKt.createTopLevelClassStub(classId, classComponent2, new KotlinJvmBinarySourceElement(kotlinBinaryClass, null, false, null, 14, null), clsStubBuilderComponentsCreateStubBuilderComponents.createContext(jvmNameResolverComponent1, packageFqName, new TypeTable(typeTable)), classHeader.isScript());
        }
        if (i == 2) {
            Pair<JvmNameResolver, ProtoBuf.Package> packageDataFrom = JvmProtoBufUtil.readPackageDataFrom(data, strings);
            JvmNameResolver jvmNameResolverComponent2 = packageDataFrom.component1();
            ProtoBuf.Package packageComponent2 = packageDataFrom.component2();
            ProtoBuf.TypeTable typeTable2 = packageComponent2.getTypeTable();
            Intrinsics.checkNotNullExpressionValue(typeTable2, "getTypeTable(...)");
            ClsStubBuilderContext clsStubBuilderContextCreateContext = clsStubBuilderComponentsCreateStubBuilderComponents.createContext(jvmNameResolverComponent2, packageFqName, new TypeTable(typeTable2));
            String packageName2 = classHeader.getPackageName();
            if (packageName2 == null || (fqNameAsSingleFqName = new ClassId(new FqName(packageName2), classId.getRelativeClassName(), classId.isLocal()).asSingleFqName()) == null) {
                fqNameAsSingleFqName = classId.asSingleFqName();
            }
            Intrinsics.checkNotNull(fqNameAsSingleFqName);
            return ClsStubBuildingKt.createFileFacadeStub(packageComponent2, fqNameAsSingleFqName, clsStubBuilderContextCreateContext);
        }
        throw new IllegalStateException("Should have processed " + file.getPath() + " with header " + classHeader);
    }

    private final ClsStubBuilderComponents createStubBuilderComponents(VirtualFile file, FqName packageFqName, byte[] fileContent, JvmMetadataVersion jvmMetadataVersion) {
        VirtualFile parent = file.getParent();
        Intrinsics.checkNotNull(parent);
        DirectoryBasedClassFinder directoryBasedClassFinder = new DirectoryBasedClassFinder(parent, packageFqName);
        DirectoryBasedClassFinder directoryBasedClassFinder2 = directoryBasedClassFinder;
        return new ClsStubBuilderComponents(new DirectoryBasedDataFinder(directoryBasedClassFinder, LOG, jvmMetadataVersion), new AnnotationLoaderForClassFileStubBuilder(directoryBasedClassFinder2, file, fileContent, jvmMetadataVersion), file, BuiltInSerializerProtocol.INSTANCE, directoryBasedClassFinder2, jvmMetadataVersion);
    }

    /* JADX INFO: compiled from: KotlinClsStubBuilder.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rR\u0016\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082D¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinClsStubBuilder$Companion;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "getLOG", "()Lcom/intellij/openapi/diagnostic/Logger;", "VERSIONED_PATH_MARKER", "", "isVersioned", "", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Logger getLOG() {
            return KotlinClsStubBuilder.LOG;
        }

        public final boolean isVersioned(VirtualFile virtualFile) {
            Intrinsics.checkNotNullParameter(virtualFile, "virtualFile");
            String path = virtualFile.getPath();
            Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
            return StringsKt.contains$default((CharSequence) path, (CharSequence) KotlinClsStubBuilder.VERSIONED_PATH_MARKER, false, 2, (Object) null);
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) KotlinClsStubBuilder.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
        VERSIONED_PATH_MARKER = "!/META-INF/versions/";
    }
}
