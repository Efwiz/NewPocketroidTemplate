package org.jetbrains.kotlin.analysis.decompiler.stub.file;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.SpecialJvmAnnotations;
import org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationMemberDefaultValueVisitor;
import org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationWithArgs;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.KClassValue;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader;
import org.jetbrains.kotlin.load.kotlin.AnnotationsContainerWithConstants;
import org.jetbrains.kotlin.load.kotlin.KotlinClassFinder;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.MemberSignature;
import org.jetbrains.kotlin.load.kotlin.VirtualFileKotlinClass;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.impl.KotlinConstantValueKt;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;

/* JADX INFO: compiled from: KotlinClsStubBuilder.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\b\u0002\u0018\u00002\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00030\u0001B%\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ \u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00032\u0006\u0010\u0014\u001a\u00020\u0012H\u0014J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0016\u001a\u00020\u0012H\u0014J(\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0016\u0010\u001b\u001a\u0012\u0012\u0004\u0012\u00020\u001d\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u001cH\u0004J\u0018\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J&\u0010\u001e\u001a\u00020#2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00020'H\u0014J \u0010(\u001a\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00032\u0006\u0010\u0016\u001a\u00020\u0012H\u0002R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\u0012\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u00030\u0011X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForClassFileStubBuilder;", "Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithArgs;", "Lorg/jetbrains/kotlin/load/kotlin/AnnotationsContainerWithConstants;", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "cachedFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "cachedFileContent", "", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "(Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;Lcom/intellij/openapi/vfs/VirtualFile;[BLorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;)V", "getJvmMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "storage", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "getAnnotationsContainer", "binaryClass", "getCachedFileContent", "kotlinClass", "isRepeatableWithImplicitContainer", "", "annotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "arguments", "", "Lorg/jetbrains/kotlin/name/Name;", "loadAnnotation", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "result", "", "loadAnnotationsAndInitializers", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AnnotationLoaderForClassFileStubBuilder extends AbstractBinaryClassAnnotationLoader<AnnotationWithArgs, AnnotationsContainerWithConstants<? extends AnnotationWithArgs, ? extends ConstantValue<?>>> {
    private final VirtualFile cachedFile;
    private final byte[] cachedFileContent;
    private final JvmMetadataVersion jvmMetadataVersion;
    private final MemoizedFunctionToNotNull<KotlinJvmBinaryClass, AnnotationsContainerWithConstants<AnnotationWithArgs, ConstantValue<?>>> storage;

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader
    public JvmMetadataVersion getJvmMetadataVersion() {
        return this.jvmMetadataVersion;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AnnotationLoaderForClassFileStubBuilder(KotlinClassFinder kotlinClassFinder, VirtualFile cachedFile, byte[] cachedFileContent, JvmMetadataVersion jvmMetadataVersion) {
        super(kotlinClassFinder);
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        Intrinsics.checkNotNullParameter(cachedFile, "cachedFile");
        Intrinsics.checkNotNullParameter(cachedFileContent, "cachedFileContent");
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "jvmMetadataVersion");
        this.cachedFile = cachedFile;
        this.cachedFileContent = cachedFileContent;
        this.jvmMetadataVersion = jvmMetadataVersion;
        this.storage = LockBasedStorageManager.NO_LOCKS.createMemoizedFunction(new Function1<KotlinJvmBinaryClass, AnnotationsContainerWithConstants<? extends AnnotationWithArgs, ? extends ConstantValue<?>>>() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.file.AnnotationLoaderForClassFileStubBuilder$storage$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final AnnotationsContainerWithConstants<AnnotationWithArgs, ConstantValue<?>> invoke(KotlinJvmBinaryClass kotlinClass) {
                Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
                return this.this$0.loadAnnotationsAndInitializers(kotlinClass);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader
    public AnnotationsContainerWithConstants<AnnotationWithArgs, ConstantValue<?>> getAnnotationsContainer(KotlinJvmBinaryClass binaryClass) {
        Intrinsics.checkNotNullParameter(binaryClass, "binaryClass");
        return this.storage.invoke(binaryClass);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader
    protected byte[] getCachedFileContent(KotlinJvmBinaryClass kotlinClass) {
        Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
        VirtualFileKotlinClass virtualFileKotlinClass = kotlinClass instanceof VirtualFileKotlinClass ? (VirtualFileKotlinClass) kotlinClass : null;
        if (Intrinsics.areEqual(virtualFileKotlinClass != null ? virtualFileKotlinClass.getFile() : null, this.cachedFile)) {
            return this.cachedFileContent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader, org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public AnnotationWithArgs loadAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        List<ProtoBuf.Annotation.Argument> argumentList = proto.getArgumentList();
        Intrinsics.checkNotNullExpressionValue(argumentList, "getArgumentList(...)");
        List<ProtoBuf.Annotation.Argument> list = argumentList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (ProtoBuf.Annotation.Argument argument : list) {
            Name name2 = NameResolverUtilKt.getName(nameResolver, argument.getNameId());
            ProtoBuf.Annotation.Argument.Value value = argument.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            Pair pair = TuplesKt.to(name2, KotlinConstantValueKt.createConstantValue(value, nameResolver));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return new AnnotationWithArgs(NameResolverUtilKt.getClassId(nameResolver, proto.getId()), linkedHashMap);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader
    protected KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotation(final ClassId annotationClassId, SourceElement source, final List<AnnotationWithArgs> result) {
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(result, "result");
        return new AnnotationMemberDefaultValueVisitor() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.file.AnnotationLoaderForClassFileStubBuilder.loadAnnotation.1
            @Override // org.jetbrains.kotlin.analysis.decompiler.stub.AnnotationMemberDefaultValueVisitor, org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnd() {
                if (AnnotationLoaderForClassFileStubBuilder.this.isRepeatableWithImplicitContainer(annotationClassId, getArgs())) {
                    return;
                }
                result.add(new AnnotationWithArgs(annotationClassId, getArgs()));
            }
        };
    }

    protected final boolean isRepeatableWithImplicitContainer(ClassId annotationClassId, Map<Name, ? extends ConstantValue<?>> arguments) {
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (!Intrinsics.areEqual(annotationClassId, SpecialJvmAnnotations.INSTANCE.getJAVA_LANG_ANNOTATION_REPEATABLE())) {
            return false;
        }
        ConstantValue<?> constantValue = arguments.get(JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME);
        KClassValue kClassValue = constantValue instanceof KClassValue ? (KClassValue) constantValue : null;
        if (kClassValue == null) {
            return false;
        }
        KClassValue.Value value = kClassValue.getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.constant.KClassValue.Value.NormalClass");
        return isImplicitRepeatableContainer(((KClassValue.Value.NormalClass) value).getClassId());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AnnotationsContainerWithConstants<AnnotationWithArgs, ConstantValue<?>> loadAnnotationsAndInitializers(KotlinJvmBinaryClass kotlinClass) {
        final HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        kotlinClass.visitMembers(new KotlinJvmBinaryClass.MemberVisitor() { // from class: org.jetbrains.kotlin.analysis.decompiler.stub.file.AnnotationLoaderForClassFileStubBuilder.loadAnnotationsAndInitializers.1
            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
            public KotlinJvmBinaryClass.MethodAnnotationVisitor visitMethod(Name name2, String desc) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                MemberSignature.Companion companion = MemberSignature.INSTANCE;
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return new AnnotationVisitorForMethod(this, companion.fromMethodNameAndDesc(strAsString, desc));
            }

            @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MemberVisitor
            public KotlinJvmBinaryClass.AnnotationVisitor visitField(Name name2, String desc, Object initializer) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                MemberSignature.Companion companion = MemberSignature.INSTANCE;
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return new MemberAnnotationVisitor(this, companion.fromFieldNameAndDesc(strAsString, desc));
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.analysis.decompiler.stub.file.AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1$AnnotationVisitorForMethod */
            /* JADX INFO: compiled from: KotlinClsStubBuilder.kt */
            @Metadata(d1 = {"\u00003\n\u0000\n\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0003\u0000\u0001\u0002\b\u008a\u0004\u0018\u00002\u00060\u0001R\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\"\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"org/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1.AnnotationVisitorForMethod", "org/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1.MemberAnnotationVisitor", "org/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MethodAnnotationVisitor;", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "(Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1;Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;)V", "visitAnnotationMemberDefaultValue", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "visitParameterAnnotation", "index", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public final class AnnotationVisitorForMethod extends MemberAnnotationVisitor implements KotlinJvmBinaryClass.MethodAnnotationVisitor {
                final /* synthetic */ C42381 this$0;

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotationMemberDefaultValue() {
                    return null;
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnnotationVisitorForMethod(C42381 c42381, MemberSignature signature) {
                    super(c42381, signature);
                    Intrinsics.checkNotNullParameter(signature, "signature");
                    this.this$0 = c42381;
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitParameterAnnotation(int index, ClassId classId, SourceElement source) {
                    Intrinsics.checkNotNullParameter(classId, "classId");
                    Intrinsics.checkNotNullParameter(source, "source");
                    MemberSignature memberSignatureFromMethodSignatureAndParameterIndex = MemberSignature.INSTANCE.fromMethodSignatureAndParameterIndex(getSignature(), index);
                    ArrayList arrayList = map.get(memberSignatureFromMethodSignatureAndParameterIndex);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        map.put(memberSignatureFromMethodSignatureAndParameterIndex, arrayList);
                    }
                    return AnnotationLoaderForClassFileStubBuilder.this.loadAnnotationIfNotSpecial(classId, source, arrayList);
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.analysis.decompiler.stub.file.AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1$MemberAnnotationVisitor */
            /* JADX INFO: compiled from: KotlinClsStubBuilder.kt */
            @Metadata(d1 = {"\u0000;\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\u009a\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0013"}, d2 = {"org/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1.MemberAnnotationVisitor", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "(Lorg/jetbrains/kotlin/analysis/decompiler/stub/file/AnnotationLoaderForClassFileStubBuilder$loadAnnotationsAndInitializers$1;Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;)V", "result", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/AnnotationWithArgs;", "Lkotlin/collections/ArrayList;", "getSignature", "()Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "visitEnd", "", "decompiler-to-file-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public class MemberAnnotationVisitor implements KotlinJvmBinaryClass.AnnotationVisitor {
                private final ArrayList<AnnotationWithArgs> result;
                private final MemberSignature signature;
                final /* synthetic */ C42381 this$0;

                public MemberAnnotationVisitor(C42381 c42381, MemberSignature signature) {
                    Intrinsics.checkNotNullParameter(signature, "signature");
                    this.this$0 = c42381;
                    this.signature = signature;
                    this.result = new ArrayList<>();
                }

                protected final MemberSignature getSignature() {
                    return this.signature;
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(ClassId classId, SourceElement source) {
                    Intrinsics.checkNotNullParameter(classId, "classId");
                    Intrinsics.checkNotNullParameter(source, "source");
                    return AnnotationLoaderForClassFileStubBuilder.this.loadAnnotationIfNotSpecial(classId, source, this.result);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationVisitor
                public void visitEnd() {
                    if (this.result.isEmpty()) {
                        return;
                    }
                    map.put(this.signature, this.result);
                }
            }
        }, getCachedFileContent(kotlinClass));
        return new AnnotationsContainerWithConstants<>(map, map2, map3);
    }
}
