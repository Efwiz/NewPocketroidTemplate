package org.jetbrains.kotlin.load.kotlin;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.SpecialJvmAnnotations;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.Flags;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.serialization.deserialization.AnnotatedCallableKind;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoader;
import org.jetbrains.kotlin.serialization.deserialization.ProtoContainer;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\b\b\u0001\u0010\u0003*\u00020\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00042\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00030\u00060\u0005B\u0015\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u001c\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u000eH\u0014J(\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0016\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u0017\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00180\u0016H\u0004J'\u0010\u0019\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016¢\u0006\u0002\u0010 J.\u0010!\u001a\u0004\u0018\u00010\"2\u0006\u0010#\u001a\u00020\u000e2\u0006\u0010$\u001a\u00020%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00020(0'H$J\u001c\u0010)\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00062\u0006\u0010*\u001a\u00020\u000eH\u0002J\u001f\u0010+\u001a\u0004\u0018\u00018\u00012\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u0002H$¢\u0006\u0002\u0010/J\\\u00100\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u00101\u001a\u0002022\u0006\u0010\u001e\u001a\u00020\u001f2+\u00103\u001a'\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006\u0012\u0004\u0012\u00020%\u0012\u0006\u0012\u0004\u0018\u00018\u000104¢\u0006\u0002\b5H\u0002¢\u0006\u0002\u00106J'\u00107\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016¢\u0006\u0002\u0010 J\u0017\u00108\u001a\u0004\u0018\u00018\u00012\u0006\u00109\u001a\u00028\u0001H$¢\u0006\u0002\u0010:R&\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\u000e\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00060\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader;", "A", "", "C", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationAndConstantLoader;", "Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationLoader;", "Lorg/jetbrains/kotlin/load/kotlin/AnnotationsContainerWithConstants;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;)V", "storage", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "getAnnotationsContainer", "binaryClass", "isRepeatableWithImplicitContainer", "", "annotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "arguments", "", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "loadAnnotationDefaultValue", "container", "Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/Object;", "loadAnnotationMethodDefaultValue", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "annotationClass", "methodSignature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "visitResult", "Lkotlin/Function1;", "", "loadAnnotationsAndInitializers", "kotlinClass", "loadConstant", "desc", "", "initializer", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/Object;", "loadConstantFromProperty", "annotatedCallableKind", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;", "loader", "Lkotlin/Function2;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/serialization/deserialization/ProtoContainer;Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property;Lorg/jetbrains/kotlin/serialization/deserialization/AnnotatedCallableKind;Lorg/jetbrains/kotlin/types/KotlinType;Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "loadPropertyConstant", "transformToUnsignedConstant", "constant", "(Ljava/lang/Object;)Ljava/lang/Object;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractBinaryClassAnnotationAndConstantLoader<A, C> extends AbstractBinaryClassAnnotationLoader<A, AnnotationsContainerWithConstants<? extends A, ? extends C>> implements AnnotationAndConstantLoader<A, C> {
    private final MemoizedFunctionToNotNull<KotlinJvmBinaryClass, AnnotationsContainerWithConstants<A, C>> storage;

    protected abstract KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotationMethodDefaultValue(KotlinJvmBinaryClass annotationClass, MemberSignature methodSignature, Function1<? super C, Unit> visitResult);

    protected abstract C loadConstant(String desc, Object initializer);

    protected abstract C transformToUnsignedConstant(C constant);

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractBinaryClassAnnotationAndConstantLoader(StorageManager storageManager, KotlinClassFinder kotlinClassFinder) {
        super(kotlinClassFinder);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.storage = storageManager.createMemoizedFunction(new Function1<KotlinJvmBinaryClass, AnnotationsContainerWithConstants<? extends A, ? extends C>>(this) { // from class: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader$storage$1
            final /* synthetic */ AbstractBinaryClassAnnotationAndConstantLoader<A, C> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            public final AnnotationsContainerWithConstants<A, C> invoke(KotlinJvmBinaryClass kotlinClass) {
                Intrinsics.checkNotNullParameter(kotlinClass, "kotlinClass");
                return this.this$0.loadAnnotationsAndInitializers(kotlinClass);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader
    public AnnotationsContainerWithConstants<A, C> getAnnotationsContainer(KotlinJvmBinaryClass binaryClass) {
        Intrinsics.checkNotNullParameter(binaryClass, "binaryClass");
        return this.storage.invoke(binaryClass);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoader
    public C loadAnnotationDefaultValue(ProtoContainer container, ProtoBuf.Property proto, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        return loadConstantFromProperty(container, proto, AnnotatedCallableKind.PROPERTY_GETTER, expectedType, new Function2<AnnotationsContainerWithConstants<? extends A, ? extends C>, MemberSignature, C>() { // from class: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader.loadAnnotationDefaultValue.1
            @Override // kotlin.jvm.functions.Function2
            public final C invoke(AnnotationsContainerWithConstants<? extends A, ? extends C> loadConstantFromProperty, MemberSignature it) {
                Intrinsics.checkNotNullParameter(loadConstantFromProperty, "$this$loadConstantFromProperty");
                Intrinsics.checkNotNullParameter(it, "it");
                return loadConstantFromProperty.getAnnotationParametersDefaultValues().get(it);
            }
        });
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.AnnotationAndConstantLoader
    public C loadPropertyConstant(ProtoContainer container, ProtoBuf.Property proto, KotlinType expectedType) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        return loadConstantFromProperty(container, proto, AnnotatedCallableKind.PROPERTY, expectedType, new Function2<AnnotationsContainerWithConstants<? extends A, ? extends C>, MemberSignature, C>() { // from class: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader.loadPropertyConstant.1
            @Override // kotlin.jvm.functions.Function2
            public final C invoke(AnnotationsContainerWithConstants<? extends A, ? extends C> loadConstantFromProperty, MemberSignature it) {
                Intrinsics.checkNotNullParameter(loadConstantFromProperty, "$this$loadConstantFromProperty");
                Intrinsics.checkNotNullParameter(it, "it");
                return loadConstantFromProperty.getPropertyConstants().get(it);
            }
        });
    }

    private final C loadConstantFromProperty(ProtoContainer container, ProtoBuf.Property proto, AnnotatedCallableKind annotatedCallableKind, KotlinType expectedType, Function2<? super AnnotationsContainerWithConstants<? extends A, ? extends C>, ? super MemberSignature, ? extends C> loader) {
        C cInvoke;
        KotlinJvmBinaryClass kotlinJvmBinaryClassFindClassWithAnnotationsAndInitializers = findClassWithAnnotationsAndInitializers(container, AbstractBinaryClassAnnotationLoader.INSTANCE.getSpecialCaseContainerClass(container, true, true, Flags.IS_CONST.get(proto.getFlags()), JvmProtoBufUtil.isMovedFromInterfaceCompanion(proto), getKotlinClassFinder(), getJvmMetadataVersion()));
        if (kotlinJvmBinaryClassFindClassWithAnnotationsAndInitializers == null) {
            return null;
        }
        MemberSignature callableSignature = getCallableSignature(proto, container.getNameResolver(), container.getTypeTable(), annotatedCallableKind, kotlinJvmBinaryClassFindClassWithAnnotationsAndInitializers.getClassHeader().getMetadataVersion().isAtLeast(DeserializedDescriptorResolver.INSTANCE.getKOTLIN_1_3_RC_METADATA_VERSION$descriptors_jvm()));
        if (callableSignature == null || (cInvoke = loader.invoke(this.storage.invoke(kotlinJvmBinaryClassFindClassWithAnnotationsAndInitializers), callableSignature)) == null) {
            return null;
        }
        return UnsignedTypes.isUnsignedType(expectedType) ? transformToUnsignedConstant(cInvoke) : cInvoke;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final AnnotationsContainerWithConstants<A, C> loadAnnotationsAndInitializers(final KotlinJvmBinaryClass kotlinClass) {
        final HashMap map = new HashMap();
        final HashMap map2 = new HashMap();
        final HashMap map3 = new HashMap();
        kotlinClass.visitMembers(new KotlinJvmBinaryClass.MemberVisitor(this) { // from class: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader.loadAnnotationsAndInitializers.1
            final /* synthetic */ AbstractBinaryClassAnnotationAndConstantLoader<A, C> this$0;

            {
                this.this$0 = this;
            }

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
                C cLoadConstant;
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                MemberSignature.Companion companion = MemberSignature.INSTANCE;
                String strAsString = name2.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                MemberSignature memberSignatureFromFieldNameAndDesc = companion.fromFieldNameAndDesc(strAsString, desc);
                if (initializer != null && (cLoadConstant = this.this$0.loadConstant(desc, initializer)) != null) {
                    map2.put(memberSignatureFromFieldNameAndDesc, cLoadConstant);
                }
                return new MemberAnnotationVisitor(this, memberSignatureFromFieldNameAndDesc);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1$AnnotationVisitorForMethod */
            /* JADX INFO: compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
            @Metadata(d1 = {"\u00003\n\u0000\n\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0003\u0000\u0001\u0002\b\u008a\u0004\u0018\u00002\u00120\u0001R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\n\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\"\u0010\t\u001a\u0004\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"org/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1.AnnotationVisitorForMethod", "org/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1.MemberAnnotationVisitor", "org/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MethodAnnotationVisitor;", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "(Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1;Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;)V", "visitAnnotationMemberDefaultValue", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "visitParameterAnnotation", "index", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public final class AnnotationVisitorForMethod extends C48091<A, C>.MemberAnnotationVisitor implements KotlinJvmBinaryClass.MethodAnnotationVisitor {
                final /* synthetic */ C48091<A, C> this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnnotationVisitorForMethod(C48091 c48091, MemberSignature signature) {
                    super(c48091, signature);
                    Intrinsics.checkNotNullParameter(signature, "signature");
                    this.this$0 = c48091;
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
                    return this.this$0.this$0.loadAnnotationIfNotSpecial(classId, source, arrayList);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.MethodAnnotationVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotationMemberDefaultValue() {
                    AbstractBinaryClassAnnotationAndConstantLoader<A, C> abstractBinaryClassAnnotationAndConstantLoader = this.this$0.this$0;
                    KotlinJvmBinaryClass kotlinJvmBinaryClass = kotlinClass;
                    MemberSignature signature = getSignature();
                    final HashMap<MemberSignature, C> map = map3;
                    return abstractBinaryClassAnnotationAndConstantLoader.loadAnnotationMethodDefaultValue(kotlinJvmBinaryClass, signature, new Function1<C, Unit>() { // from class: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1$AnnotationVisitorForMethod$visitAnnotationMemberDefaultValue$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                            invoke2(obj);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(C it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            map.put(this.getSignature(), it);
                        }
                    });
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1$MemberAnnotationVisitor */
            /* JADX INFO: compiled from: AbstractBinaryClassAnnotationAndConstantLoader.kt */
            @Metadata(d1 = {"\u00007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\u009a\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0006j\b\u0012\u0004\u0012\u00028\u0000`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0012"}, d2 = {"org/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1.MemberAnnotationVisitor", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "signature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "(Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader$loadAnnotationsAndInitializers$1;Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;)V", "result", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getSignature", "()Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "visitAnnotation", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "visitEnd", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
            public class MemberAnnotationVisitor implements KotlinJvmBinaryClass.AnnotationVisitor {
                private final ArrayList<A> result;
                private final MemberSignature signature;
                final /* synthetic */ C48091<A, C> this$0;

                public MemberAnnotationVisitor(C48091 c48091, MemberSignature signature) {
                    Intrinsics.checkNotNullParameter(signature, "signature");
                    this.this$0 = c48091;
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
                    return this.this$0.this$0.loadAnnotationIfNotSpecial(classId, source, this.result);
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

    protected final boolean isRepeatableWithImplicitContainer(ClassId annotationClassId, Map<Name, ? extends ConstantValue<?>> arguments) {
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        if (!Intrinsics.areEqual(annotationClassId, SpecialJvmAnnotations.INSTANCE.getJAVA_LANG_ANNOTATION_REPEATABLE())) {
            return false;
        }
        ConstantValue<?> constantValue = arguments.get(Name.identifier("value"));
        KClassValue kClassValue = constantValue instanceof KClassValue ? (KClassValue) constantValue : null;
        if (kClassValue == null) {
            return false;
        }
        KClassValue.Value value = kClassValue.getValue();
        KClassValue.Value.NormalClass normalClass = value instanceof KClassValue.Value.NormalClass ? (KClassValue.Value.NormalClass) value : null;
        if (normalClass == null) {
            return false;
        }
        return isImplicitRepeatableContainer(normalClass.getClassId());
    }
}
