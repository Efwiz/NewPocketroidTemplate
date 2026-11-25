package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.NotFoundClasses;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptorImpl;
import org.jetbrains.kotlin.load.java.components.DescriptorResolverUtils;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ByteValue;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.ShortValue;
import org.jetbrains.kotlin.resolve.constants.UByteValue;
import org.jetbrains.kotlin.resolve.constants.UIntValue;
import org.jetbrains.kotlin.resolve.constants.ULongValue;
import org.jetbrains.kotlin.resolve.constants.UShortValue;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.serialization.deserialization.AnnotationDeserializer;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0001:\u00017B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ \u0010\u0015\u001a\u0006\u0012\u0002\b\u00030\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002J\u0018\u0010\u001a\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J(\u0010\u001a\u001a\u0004\u0018\u00010\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020#2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00020%H\u0014J2\u0010&\u001a\u0004\u0018\u00010\u001f2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0016\u0010+\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0003\u0012\u0004\u0012\u00020-0,H\u0014J\u001e\u0010.\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u00020\u0019H\u0014J\u0010\u00102\u001a\u0002032\u0006\u00104\u001a\u00020!H\u0002J\u001a\u00105\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\n\u00106\u001a\u0006\u0012\u0002\b\u00030\u0003H\u0014R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl;", "Lorg/jetbrains/kotlin/load/kotlin/AbstractBinaryClassAnnotationAndConstantLoader;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "notFoundClasses", "Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "kotlinClassFinder", "Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/descriptors/NotFoundClasses;Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/load/kotlin/KotlinClassFinder;)V", "annotationDeserializer", "Lorg/jetbrains/kotlin/serialization/deserialization/AnnotationDeserializer;", "jvmMetadataVersion", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "getJvmMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;", "setJvmMetadataVersion", "(Lorg/jetbrains/kotlin/metadata/jvm/deserialization/JvmMetadataVersion;)V", "createConstant", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "", "loadAnnotation", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Annotation;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "annotationClassId", "Lorg/jetbrains/kotlin/name/ClassId;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "result", "", "loadAnnotationMethodDefaultValue", "annotationClass", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass;", "methodSignature", "Lorg/jetbrains/kotlin/load/kotlin/MemberSignature;", "visitResult", "Lkotlin/Function1;", "", "loadConstant", "desc", "", "initializer", "resolveClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "classId", "transformToUnsignedConstant", "constant", "AbstractAnnotationArgumentVisitor", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryClassAnnotationAndConstantLoaderImpl extends AbstractBinaryClassAnnotationAndConstantLoader<AnnotationDescriptor, ConstantValue<?>> {
    private final AnnotationDeserializer annotationDeserializer;
    private JvmMetadataVersion jvmMetadataVersion;
    private final ModuleDescriptor module;
    private final NotFoundClasses notFoundClasses;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BinaryClassAnnotationAndConstantLoaderImpl(ModuleDescriptor module, NotFoundClasses notFoundClasses, StorageManager storageManager, KotlinClassFinder kotlinClassFinder) {
        super(storageManager, kotlinClassFinder);
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(notFoundClasses, "notFoundClasses");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(kotlinClassFinder, "kotlinClassFinder");
        this.module = module;
        this.notFoundClasses = notFoundClasses;
        this.annotationDeserializer = new AnnotationDeserializer(module, notFoundClasses);
        this.jvmMetadataVersion = JvmMetadataVersion.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader
    public JvmMetadataVersion getJvmMetadataVersion() {
        return this.jvmMetadataVersion;
    }

    public void setJvmMetadataVersion(JvmMetadataVersion jvmMetadataVersion) {
        Intrinsics.checkNotNullParameter(jvmMetadataVersion, "<set-?>");
        this.jvmMetadataVersion = jvmMetadataVersion;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader, org.jetbrains.kotlin.serialization.deserialization.AnnotationLoader
    public AnnotationDescriptor loadAnnotation(ProtoBuf.Annotation proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        return this.annotationDeserializer.deserializeAnnotation(proto, nameResolver);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader
    public ConstantValue<?> loadConstant(String desc, Object initializer) {
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        if (StringsKt.contains$default((CharSequence) "ZBCS", (CharSequence) desc, false, 2, (Object) null)) {
            int iIntValue = ((Integer) initializer).intValue();
            int iHashCode = desc.hashCode();
            if (iHashCode == 66) {
                if (desc.equals("B")) {
                    initializer = Byte.valueOf((byte) iIntValue);
                }
                throw new AssertionError(desc);
            }
            if (iHashCode == 67) {
                if (desc.equals("C")) {
                    initializer = Character.valueOf((char) iIntValue);
                }
                throw new AssertionError(desc);
            }
            if (iHashCode == 83) {
                if (desc.equals(ExifInterface.LATITUDE_SOUTH)) {
                    initializer = Short.valueOf((short) iIntValue);
                }
                throw new AssertionError(desc);
            }
            if (iHashCode == 90 && desc.equals("Z")) {
                initializer = Boolean.valueOf(iIntValue != 0);
            }
            throw new AssertionError(desc);
        }
        return ConstantValueFactory.INSTANCE.createConstantValue(initializer, this.module);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader
    public ConstantValue<?> transformToUnsignedConstant(ConstantValue<?> constant) {
        Intrinsics.checkNotNullParameter(constant, "constant");
        if (constant instanceof ByteValue) {
            return new UByteValue(((ByteValue) constant).getValue().byteValue());
        }
        if (constant instanceof ShortValue) {
            return new UShortValue(((ShortValue) constant).getValue().shortValue());
        }
        if (constant instanceof IntValue) {
            return new UIntValue(((IntValue) constant).getValue().intValue());
        }
        return constant instanceof LongValue ? new ULongValue(((LongValue) constant).getValue().longValue()) : constant;
    }

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationLoader
    protected KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotation(final ClassId annotationClassId, final SourceElement source, final List<AnnotationDescriptor> result) {
        Intrinsics.checkNotNullParameter(annotationClassId, "annotationClassId");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(result, "result");
        final ClassDescriptor classDescriptorResolveClass = resolveClass(annotationClassId);
        return new AbstractAnnotationArgumentVisitor() { // from class: org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl.loadAnnotation.1
            private final HashMap<Name, ConstantValue<?>> arguments;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
                this.arguments = new HashMap<>();
            }

            @Override // org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor
            public void visitConstantValue(Name name2, ConstantValue<?> value) {
                Intrinsics.checkNotNullParameter(value, "value");
                if (name2 != null) {
                    this.arguments.put(name2, value);
                }
            }

            @Override // org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor
            public void visitArrayValue(Name name2, ArrayList<ConstantValue<?>> elements) {
                Intrinsics.checkNotNullParameter(elements, "elements");
                if (name2 == null) {
                    return;
                }
                ValueParameterDescriptor annotationParameterByName = DescriptorResolverUtils.getAnnotationParameterByName(name2, classDescriptorResolveClass);
                if (annotationParameterByName != null) {
                    HashMap<Name, ConstantValue<?>> map = this.arguments;
                    ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
                    List<? extends ConstantValue<?>> listCompact = CollectionsKt.compact(elements);
                    KotlinType type = annotationParameterByName.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    map.put(name2, constantValueFactory.createArrayValue(listCompact, type));
                    return;
                }
                if (BinaryClassAnnotationAndConstantLoaderImpl.this.isImplicitRepeatableContainer(annotationClassId) && Intrinsics.areEqual(name2.asString(), "value")) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : elements) {
                        if (obj instanceof AnnotationValue) {
                            arrayList.add(obj);
                        }
                    }
                    List<AnnotationDescriptor> list = result;
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        list.add(((AnnotationValue) it.next()).getValue());
                    }
                }
            }

            @Override // org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor, org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
            public void visitEnd() {
                if (BinaryClassAnnotationAndConstantLoaderImpl.this.isRepeatableWithImplicitContainer(annotationClassId, this.arguments) || BinaryClassAnnotationAndConstantLoaderImpl.this.isImplicitRepeatableContainer(annotationClassId)) {
                    return;
                }
                result.add(new AnnotationDescriptorImpl(classDescriptorResolveClass.getDefaultType(), this.arguments, source));
            }
        };
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl$loadAnnotationMethodDefaultValue$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
    @Metadata(d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00060\u0001R\u00020\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J2\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\u001e\u0010\r\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u000ej\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004`\u000fH\u0016J\u001e\u0010\u0010\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0016J\b\u0010\u0012\u001a\u00020\nH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"org/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl$loadAnnotationMethodDefaultValue$1", "Lorg/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor;", "Lorg/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl;", "defaultValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "guessArrayType", "Lorg/jetbrains/kotlin/types/KotlinType;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "visitArrayValue", "", "name", "Lorg/jetbrains/kotlin/name/Name;", PathTree.NAME_elements, "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "visitConstantValue", "value", "visitEnd", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class C48111 extends AbstractAnnotationArgumentVisitor {
        final /* synthetic */ KotlinJvmBinaryClass $annotationClass;
        final /* synthetic */ MemberSignature $methodSignature;
        final /* synthetic */ Function1<ConstantValue<?>, Unit> $visitResult;
        private ConstantValue<?> defaultValue;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C48111(Function1<? super ConstantValue<?>, Unit> function1, MemberSignature memberSignature, KotlinJvmBinaryClass kotlinJvmBinaryClass) {
            super();
            this.$visitResult = function1;
            this.$methodSignature = memberSignature;
            this.$annotationClass = kotlinJvmBinaryClass;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor
        public void visitConstantValue(Name name2, ConstantValue<?> value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this.defaultValue = value;
        }

        @Override // org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor
        public void visitArrayValue(Name name2, ArrayList<ConstantValue<?>> elements) {
            Intrinsics.checkNotNullParameter(elements, "elements");
            this.defaultValue = new ArrayValue(CollectionsKt.compact(elements), new Function1<ModuleDescriptor, KotlinType>() { // from class: org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl$loadAnnotationMethodDefaultValue$1$visitArrayValue$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final KotlinType invoke(ModuleDescriptor moduleDescriptor) {
                    Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
                    return this.this$0.guessArrayType(moduleDescriptor);
                }
            });
        }

        @Override // org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor, org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public void visitEnd() {
            ConstantValue<?> constantValue = this.defaultValue;
            if (constantValue != null) {
                this.$visitResult.invoke(constantValue);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final KotlinType guessArrayType(ModuleDescriptor moduleDescriptor) {
            String jvmName;
            String strRemovePrefix = StringsKt.removePrefix(StringsKt.substringAfterLast$default(this.$methodSignature.getSignature(), ')', (String) null, 2, (Object) null), (CharSequence) "[");
            JvmPrimitiveType byDesc = JvmPrimitiveType.getByDesc(strRemovePrefix);
            if (byDesc != null) {
                SimpleType primitiveArrayKotlinType = moduleDescriptor.getBuiltIns().getPrimitiveArrayKotlinType(byDesc.getPrimitiveType());
                Intrinsics.checkNotNullExpressionValue(primitiveArrayKotlinType, "getPrimitiveArrayKotlinType(...)");
                return primitiveArrayKotlinType;
            }
            if (Intrinsics.areEqual(strRemovePrefix, "Ljava/lang/String;")) {
                SimpleType arrayType = moduleDescriptor.getBuiltIns().getArrayType(Variance.INVARIANT, moduleDescriptor.getBuiltIns().getStringType());
                Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
                return arrayType;
            }
            MemberScope unsubstitutedMemberScope = FindClassInModuleKt.findNonGenericClassAcrossDependencies(moduleDescriptor, this.$annotationClass.getClassId(), BinaryClassAnnotationAndConstantLoaderImpl.this.notFoundClasses).getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, null, null, 3, null);
            ArrayList arrayList = new ArrayList();
            for (Object obj : contributedDescriptors$default) {
                if (obj instanceof PropertyDescriptor) {
                    arrayList.add(obj);
                }
            }
            MemberSignature memberSignature = this.$methodSignature;
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : arrayList) {
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) obj2;
                PropertyGetterDescriptor getter = propertyDescriptor.getGetter();
                if (getter != null) {
                    jvmName = DescriptorUtils.getJvmName(getter);
                    if (jvmName == null) {
                        jvmName = propertyDescriptor.getShortName().asString();
                    }
                } else {
                    jvmName = null;
                }
                if (Intrinsics.areEqual(jvmName, StringsKt.substringBefore$default(memberSignature.getSignature(), '(', (String) null, 2, (Object) null))) {
                    arrayList2.add(obj2);
                }
            }
            PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) kotlin.collections.CollectionsKt.singleOrNull((List) arrayList2);
            if (propertyDescriptor2 == null) {
                throw new IllegalStateException(("Signature " + this.$methodSignature.getSignature() + " does not belong to class " + this.$annotationClass.getClassId() + " or multiple duplicates found").toString());
            }
            KotlinType type = propertyDescriptor2.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return type;
        }
    }

    @Override // org.jetbrains.kotlin.load.kotlin.AbstractBinaryClassAnnotationAndConstantLoader
    protected KotlinJvmBinaryClass.AnnotationArgumentVisitor loadAnnotationMethodDefaultValue(KotlinJvmBinaryClass annotationClass, MemberSignature methodSignature, Function1<? super ConstantValue<?>, Unit> visitResult) {
        Intrinsics.checkNotNullParameter(annotationClass, "annotationClass");
        Intrinsics.checkNotNullParameter(methodSignature, "methodSignature");
        Intrinsics.checkNotNullParameter(visitResult, "visitResult");
        return new C48111(visitResult, methodSignature, annotationClass);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: BinaryClassAnnotationAndConstantLoaderImpl.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b¢\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0016J\u001c\u0010\t\u001a\u0004\u0018\u00010\u00012\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0014\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016J2\u0010\u000e\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u001e\u0010\u000f\u001a\u001a\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\u0010j\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0011`\u0012H&J\u001a\u0010\u0013\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\u0014H\u0016J\u001e\u0010\u0015\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0011H&J\b\u0010\u0016\u001a\u00020\u0004H&J\"\u0010\u0017\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0006H\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor;", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "(Lorg/jetbrains/kotlin/load/kotlin/BinaryClassAnnotationAndConstantLoaderImpl;)V", "visit", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "", "visitAnnotation", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "visitArray", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArrayArgumentVisitor;", "visitArrayValue", PathTree.NAME_elements, "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "Lkotlin/collections/ArrayList;", "visitClassLiteral", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "visitConstantValue", "visitEnd", "visitEnum", "enumClassId", "enumEntryName", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    abstract class AbstractAnnotationArgumentVisitor implements KotlinJvmBinaryClass.AnnotationArgumentVisitor {
        public abstract void visitArrayValue(Name name2, ArrayList<ConstantValue<?>> elements);

        public abstract void visitConstantValue(Name name2, ConstantValue<?> value);

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public abstract void visitEnd();

        public AbstractAnnotationArgumentVisitor() {
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public void visit(Name name2, Object value) {
            visitConstantValue(name2, BinaryClassAnnotationAndConstantLoaderImpl.this.createConstant(name2, value));
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public void visitClassLiteral(Name name2, ClassLiteralValue value) {
            Intrinsics.checkNotNullParameter(value, "value");
            visitConstantValue(name2, new KClassValue(value));
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public void visitEnum(Name name2, ClassId enumClassId, Name enumEntryName) {
            Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
            Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
            visitConstantValue(name2, new EnumValue(enumClassId, enumEntryName));
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name2) {
            return new BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor$visitArray$1(BinaryClassAnnotationAndConstantLoaderImpl.this, name2, this);
        }

        @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
        public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(final Name name2, ClassId classId) {
            Intrinsics.checkNotNullParameter(classId, "classId");
            final ArrayList arrayList = new ArrayList();
            BinaryClassAnnotationAndConstantLoaderImpl binaryClassAnnotationAndConstantLoaderImpl = BinaryClassAnnotationAndConstantLoaderImpl.this;
            SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
            Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
            final KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorLoadAnnotation = binaryClassAnnotationAndConstantLoaderImpl.loadAnnotation(classId, NO_SOURCE, arrayList);
            Intrinsics.checkNotNull(annotationArgumentVisitorLoadAnnotation);
            return new KotlinJvmBinaryClass.AnnotationArgumentVisitor(this, name2, arrayList) { // from class: org.jetbrains.kotlin.load.kotlin.BinaryClassAnnotationAndConstantLoaderImpl$AbstractAnnotationArgumentVisitor$visitAnnotation$1
                private final /* synthetic */ KotlinJvmBinaryClass.AnnotationArgumentVisitor $$delegate_0;
                final /* synthetic */ ArrayList<AnnotationDescriptor> $list;
                final /* synthetic */ Name $name;
                final /* synthetic */ BinaryClassAnnotationAndConstantLoaderImpl.AbstractAnnotationArgumentVisitor this$0;

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visit(Name name3, Object value) {
                    this.$$delegate_0.visit(name3, value);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public KotlinJvmBinaryClass.AnnotationArgumentVisitor visitAnnotation(Name name3, ClassId classId2) {
                    Intrinsics.checkNotNullParameter(classId2, "classId");
                    return this.$$delegate_0.visitAnnotation(name3, classId2);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor visitArray(Name name3) {
                    return this.$$delegate_0.visitArray(name3);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitClassLiteral(Name name3, ClassLiteralValue value) {
                    Intrinsics.checkNotNullParameter(value, "value");
                    this.$$delegate_0.visitClassLiteral(name3, value);
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitEnum(Name name3, ClassId enumClassId, Name enumEntryName) {
                    Intrinsics.checkNotNullParameter(enumClassId, "enumClassId");
                    Intrinsics.checkNotNullParameter(enumEntryName, "enumEntryName");
                    this.$$delegate_0.visitEnum(name3, enumClassId, enumEntryName);
                }

                {
                    this.this$0 = this;
                    this.$name = name2;
                    this.$list = arrayList;
                    this.$$delegate_0 = this.$visitor;
                }

                @Override // org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass.AnnotationArgumentVisitor
                public void visitEnd() {
                    this.$visitor.visitEnd();
                    this.this$0.visitConstantValue(this.$name, new AnnotationValue((AnnotationDescriptor) kotlin.collections.CollectionsKt.single((List) this.$list)));
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConstantValue<?> createConstant(Name name2, Object value) {
        ConstantValue<?> constantValueCreateConstantValue = ConstantValueFactory.INSTANCE.createConstantValue(value, this.module);
        return constantValueCreateConstantValue == null ? ErrorValue.INSTANCE.create("Unsupported annotation argument: " + name2) : constantValueCreateConstantValue;
    }

    private final ClassDescriptor resolveClass(ClassId classId) {
        return FindClassInModuleKt.findNonGenericClassAcrossDependencies(this.module, classId, this.notFoundClasses);
    }
}
