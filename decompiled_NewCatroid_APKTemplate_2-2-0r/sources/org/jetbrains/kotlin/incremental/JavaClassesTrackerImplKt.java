package org.jetbrains.kotlin.incremental;

import java.io.File;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.java.descriptors.JavaClassDescriptor;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.metadata.java.JavaClassProtoBuf;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.source.PsiSourceElement;
import org.jetbrains.kotlin.serialization.DescriptorSerializer;
import org.jetbrains.kotlin.util.PerformanceCounter;

/* JADX INFO: compiled from: JavaClassesTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u000e\u001a\u00020\u000f*\u00020\u000b2\u0006\u0010\u0010\u001a\u00020\u0011\u001a\n\u0010\u0012\u001a\u00020\u0013*\u00020\u0014\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003\"\u0019\u0010\u0004\u001a\n \u0006*\u0004\u0018\u00010\u00050\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"CONVERTING_JAVA_CLASSES_TO_PROTO", "Lorg/jetbrains/kotlin/util/PerformanceCounter;", "getCONVERTING_JAVA_CLASSES_TO_PROTO", "()Lorg/jetbrains/kotlin/util/PerformanceCounter;", "JAVA_CLASS_PROTOBUF_REGISTRY", "Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getJAVA_CLASS_PROTOBUF_REGISTRY", "()Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "javaSourceFile", "Ljava/io/File;", "Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;", "getJavaSourceFile", "(Lorg/jetbrains/kotlin/load/java/descriptors/JavaClassDescriptor;)Ljava/io/File;", "convertToProto", "Lorg/jetbrains/kotlin/incremental/SerializedJavaClassWithSource;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "toProtoData", "Lorg/jetbrains/kotlin/incremental/ClassProtoData;", "Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;", "kotlin-build-common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaClassesTrackerImplKt {
    private static final PerformanceCounter CONVERTING_JAVA_CLASSES_TO_PROTO = PerformanceCounter.Companion.create$default(PerformanceCounter.INSTANCE, "Converting Java sources to proto", false, 2, null);
    private static final ExtensionRegistryLite JAVA_CLASS_PROTOBUF_REGISTRY;

    static {
        ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
        JavaClassProtoBuf.registerAllExtensions(extensionRegistryLiteNewInstance);
        BuiltInsProtoBuf.registerAllExtensions(extensionRegistryLiteNewInstance);
        JAVA_CLASS_PROTOBUF_REGISTRY = extensionRegistryLiteNewInstance;
    }

    public static final PerformanceCounter getCONVERTING_JAVA_CLASSES_TO_PROTO() {
        return CONVERTING_JAVA_CLASSES_TO_PROTO;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final File getJavaSourceFile(JavaClassDescriptor javaClassDescriptor) {
        PsiElement psi;
        PsiFile containingFile;
        VirtualFile virtualFile;
        String path;
        SourceElement source = javaClassDescriptor.getSource();
        if (!(source instanceof PsiSourceElement)) {
            source = null;
        }
        PsiSourceElement psiSourceElement = (PsiSourceElement) source;
        if (psiSourceElement == null || (psi = psiSourceElement.getPsi()) == null || (containingFile = psi.getContainingFile()) == null) {
            return null;
        }
        if (!(containingFile instanceof PsiJavaFile)) {
            containingFile = null;
        }
        if (containingFile == null || (virtualFile = containingFile.getVirtualFile()) == null || (path = virtualFile.getPath()) == null) {
            return null;
        }
        return new File(path);
    }

    public static final SerializedJavaClassWithSource convertToProto(JavaClassDescriptor javaClassDescriptor, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(javaClassDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        File javaSourceFile = getJavaSourceFile(javaClassDescriptor);
        if (javaSourceFile != null) {
            JavaClassesSerializerExtension javaClassesSerializerExtension = new JavaClassesSerializerExtension();
            try {
                ProtoBuf.Class classBuild = DescriptorSerializer.Companion.create$default(DescriptorSerializer.INSTANCE, javaClassDescriptor, javaClassesSerializerExtension, null, languageVersionSettings, null, 16, null).classProto(javaClassDescriptor).build();
                Pair<ProtoBuf.StringTable, ProtoBuf.QualifiedNameTable> pairBuildProto = javaClassesSerializerExtension.getStringTable().buildProto();
                ProtoBuf.StringTable stringTableComponent1 = pairBuildProto.component1();
                ProtoBuf.QualifiedNameTable qualifiedNameTableComponent2 = pairBuildProto.component2();
                Intrinsics.checkNotNull(classBuild);
                return new SerializedJavaClassWithSource(javaSourceFile, new SerializedJavaClass(classBuild, stringTableComponent1, qualifiedNameTableComponent2));
            } catch (Exception e) {
                throw new IllegalStateException("Error during writing proto for descriptor: " + DescriptorRenderer.DEBUG_TEXT.render(javaClassDescriptor) + "\nSource file: " + javaSourceFile, e);
            }
        }
        throw new AssertionError("convertToProto should only be called for source based classes");
    }

    public static final ClassProtoData toProtoData(SerializedJavaClass serializedJavaClass) {
        Intrinsics.checkNotNullParameter(serializedJavaClass, "<this>");
        return new ClassProtoData(serializedJavaClass.getProto(), new NameResolverImpl(serializedJavaClass.getStringTable(), serializedJavaClass.getQualifiedNameTable()));
    }

    public static final ExtensionRegistryLite getJAVA_CLASS_PROTOBUF_REGISTRY() {
        return JAVA_CLASS_PROTOBUF_REGISTRY;
    }
}
