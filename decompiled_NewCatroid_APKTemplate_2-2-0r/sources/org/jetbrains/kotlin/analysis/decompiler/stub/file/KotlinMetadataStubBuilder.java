package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.stub.CallableClsStubBuilderKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.ClassClsStubBuilderKt;
import org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuilderComponents;
import org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuilderContext;
import org.jetbrains.kotlin.analysis.decompiler.stub.ClsStubBuildingKt;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.util.indexing.FileContent;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.metadata.deserialization.TypeTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinFileStubImpl;
import org.jetbrains.kotlin.serialization.SerializerExtensionProtocol;
import org.jetbrains.kotlin.serialization.deserialization.ClassDeserializer;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.serialization.deserialization.ProtoBasedClassDataFinder;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;

/* JADX INFO: compiled from: KotlinMetadataStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001:\u0001\u0014B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u001a\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\n¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0003H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\t\u001a\u0016\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0012\u0006\u0012\u0004\u0018\u00010\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder;", "Lorg/jetbrains/kotlin/com/intellij/psi/compiled/ClsStubBuilder;", "version", "", "fileType", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileType;", "serializerProtocol", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "readFile", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;", "(ILcom/intellij/openapi/fileTypes/FileType;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function2;)V", "buildFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/PsiFileStub;", FirebaseAnalytics.Param.CONTENT, "Lorg/jetbrains/kotlin/com/intellij/util/indexing/FileContent;", "getStubVersion", "FileWithMetadata", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KotlinMetadataStubBuilder extends ClsStubBuilder {
    private final FileType fileType;
    private final Function2<VirtualFile, byte[], FileWithMetadata> readFile;
    private final Function0<SerializerExtensionProtocol> serializerProtocol;
    private final int version;

    /* JADX WARN: Multi-variable type inference failed */
    public KotlinMetadataStubBuilder(int i, FileType fileType, Function0<? extends SerializerExtensionProtocol> serializerProtocol, Function2<? super VirtualFile, ? super byte[], ? extends FileWithMetadata> readFile) {
        Intrinsics.checkNotNullParameter(fileType, "fileType");
        Intrinsics.checkNotNullParameter(serializerProtocol, "serializerProtocol");
        Intrinsics.checkNotNullParameter(readFile, "readFile");
        this.version = i;
        this.fileType = fileType;
        this.serializerProtocol = serializerProtocol;
        this.readFile = readFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder
    public int getStubVersion() {
        return this.version + 26;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.compiled.ClsStubBuilder
    public PsiFileStub<?> buildFileStub(FileContent content) {
        Intrinsics.checkNotNullParameter(content, "content");
        VirtualFile file = content.getFile();
        Intrinsics.checkNotNullExpressionValue(file, "getFile(...)");
        if (!Intrinsics.areEqual(file.getExtension(), this.fileType.getDefaultExtension())) {
            Intrinsics.areEqual(file.getFileType(), this.fileType);
        }
        Function2<VirtualFile, byte[], FileWithMetadata> function2 = this.readFile;
        byte[] content2 = content.getContent();
        Intrinsics.checkNotNullExpressionValue(content2, "getContent(...)");
        FileWithMetadata fileWithMetadataInvoke = function2.invoke(file, content2);
        if (fileWithMetadataInvoke == null) {
            return null;
        }
        if (fileWithMetadataInvoke instanceof FileWithMetadata.Incompatible) {
            return ClsStubBuildingKt.createIncompatibleAbiVersionFileStub();
        }
        if (!(fileWithMetadataInvoke instanceof FileWithMetadata.Compatible)) {
            throw new NoWhenBranchMatchedException();
        }
        FileWithMetadata.Compatible compatible = (FileWithMetadata.Compatible) fileWithMetadataInvoke;
        ProtoBuf.Package r12 = compatible.getProto().getPackage();
        FqName packageFqName = compatible.getPackageFqName();
        NameResolverImpl nameResolver = compatible.getNameResolver();
        SerializerExtensionProtocol serializerExtensionProtocolInvoke = this.serializerProtocol.invoke();
        NameResolverImpl nameResolverImpl = nameResolver;
        ClsStubBuilderComponents clsStubBuilderComponents = new ClsStubBuilderComponents(new ProtoBasedClassDataFinder(compatible.getProto(), nameResolverImpl, compatible.getVersion(), null, 8, null), new AnnotationLoaderForStubBuilderImpl(serializerExtensionProtocolInvoke), file, serializerExtensionProtocolInvoke, null, null, 48, null);
        ProtoBuf.TypeTable typeTable = r12.getTypeTable();
        Intrinsics.checkNotNullExpressionValue(typeTable, "getTypeTable(...)");
        ClsStubBuilderContext clsStubBuilderContextCreateContext = clsStubBuilderComponents.createContext(nameResolverImpl, packageFqName, new TypeTable(typeTable));
        KotlinFileStubImpl kotlinFileStubImplCreateFileStub = ClsStubBuildingKt.createFileStub(packageFqName, false);
        KotlinFileStubImpl kotlinFileStubImpl = kotlinFileStubImplCreateFileStub;
        ProtoContainer.Package r5 = new ProtoContainer.Package(packageFqName, clsStubBuilderContextCreateContext.getNameResolver(), clsStubBuilderContextCreateContext.getTypeTable(), null);
        Intrinsics.checkNotNull(r12);
        CallableClsStubBuilderKt.createPackageDeclarationsStubs(kotlinFileStubImpl, clsStubBuilderContextCreateContext, r5, r12);
        for (ProtoBuf.Class r18 : compatible.getClassesToDecompile()) {
            ClassClsStubBuilderKt.createClassStub(kotlinFileStubImpl, r18, nameResolverImpl, NameResolverUtilKt.getClassId(nameResolverImpl, r18.getFqName()), null, clsStubBuilderContextCreateContext);
        }
        return kotlinFileStubImplCreateFileStub;
    }

    /* JADX INFO: compiled from: KotlinMetadataStubBuilder.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;", "", "()V", "Compatible", "Incompatible", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata$Compatible;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata$Incompatible;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class FileWithMetadata {
        public /* synthetic */ FileWithMetadata(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: KotlinMetadataStubBuilder.kt */
        @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata$Incompatible;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;", "version", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "(Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;)V", "getVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Incompatible extends FileWithMetadata {
            private final BinaryVersion version;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Incompatible(BinaryVersion version) {
                super(null);
                Intrinsics.checkNotNullParameter(version, "version");
                this.version = version;
            }

            public final BinaryVersion getVersion() {
                return this.version;
            }
        }

        private FileWithMetadata() {
        }

        /* JADX INFO: compiled from: KotlinMetadataStubBuilder.kt */
        @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u0013¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata$Compatible;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/KotlinMetadataStubBuilder$FileWithMetadata;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "version", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "serializerProtocol", "Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/serialization/SerializerExtensionProtocol;)V", "classesToDecompile", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "getClassesToDecompile", "()Ljava/util/List;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolverImpl;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$PackageFragment;", "getVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static class Compatible extends FileWithMetadata {
            private final List<ProtoBuf.Class> classesToDecompile;
            private final NameResolverImpl nameResolver;
            private final FqName packageFqName;
            private final ProtoBuf.PackageFragment proto;
            private final BinaryVersion version;

            public final ProtoBuf.PackageFragment getProto() {
                return this.proto;
            }

            public final BinaryVersion getVersion() {
                return this.version;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Compatible(ProtoBuf.PackageFragment proto, BinaryVersion version, SerializerExtensionProtocol serializerProtocol) {
                super(null);
                Intrinsics.checkNotNullParameter(proto, "proto");
                Intrinsics.checkNotNullParameter(version, "version");
                Intrinsics.checkNotNullParameter(serializerProtocol, "serializerProtocol");
                this.proto = proto;
                this.version = version;
                ProtoBuf.StringTable strings = proto.getStrings();
                Intrinsics.checkNotNullExpressionValue(strings, "getStrings(...)");
                ProtoBuf.QualifiedNameTable qualifiedNames = proto.getQualifiedNames();
                Intrinsics.checkNotNullExpressionValue(qualifiedNames, "getQualifiedNames(...)");
                NameResolverImpl nameResolverImpl = new NameResolverImpl(strings, qualifiedNames);
                this.nameResolver = nameResolverImpl;
                Object extension = proto.getPackage().getExtension(serializerProtocol.getPackageFqName());
                Intrinsics.checkNotNullExpressionValue(extension, "getExtension(...)");
                this.packageFqName = new FqName(nameResolverImpl.getPackageFqName(((Number) extension).intValue()));
                List<ProtoBuf.Class> class_List = proto.getClass_List();
                Intrinsics.checkNotNullExpressionValue(class_List, "getClass_List(...)");
                ArrayList arrayList = new ArrayList();
                for (Object obj : class_List) {
                    ClassId classId = NameResolverUtilKt.getClassId(this.nameResolver, ((ProtoBuf.Class) obj).getFqName());
                    if (!classId.isNestedClass() && !ClassDeserializer.INSTANCE.getBLACK_LIST().contains(classId)) {
                        arrayList.add(obj);
                    }
                }
                this.classesToDecompile = arrayList;
            }

            public final NameResolverImpl getNameResolver() {
                return this.nameResolver;
            }

            public final FqName getPackageFqName() {
                return this.packageFqName;
            }

            public List<ProtoBuf.Class> getClassesToDecompile() {
                return this.classesToDecompile;
            }
        }
    }
}
