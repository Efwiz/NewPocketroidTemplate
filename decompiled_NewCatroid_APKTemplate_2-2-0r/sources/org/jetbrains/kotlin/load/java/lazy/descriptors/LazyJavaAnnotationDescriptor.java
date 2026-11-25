package org.jetbrains.kotlin.load.java.lazy.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.components.DescriptorResolverUtils;
import org.jetbrains.kotlin.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import org.jetbrains.kotlin.load.java.lazy.LazyJavaResolverContext;
import org.jetbrains.kotlin.load.java.lazy.types.JavaTypeAttributesKt;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeUsage;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: LazyJavaAnnotationDescriptor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010%\u001a\u00020&2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0018\u0010'\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\b\u0010(\u001a\u0004\u0018\u00010)H\u0002J\u0014\u0010*\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J$\u0010+\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\u0006\u0010,\u001a\u00020\f2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020)0.H\u0002J\"\u0010/\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\b\u00100\u001a\u0004\u0018\u0001012\b\u00102\u001a\u0004\u0018\u00010\fH\u0002J\u0016\u00103\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000e2\u0006\u00104\u001a\u000205H\u0002J\b\u00106\u001a\u000207H\u0016R3\u0010\n\u001a\u001a\u0012\f\u0012\n \r*\u0004\u0018\u00010\f0\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e0\u000b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u0004\u0018\u00010\u00148VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0019\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\u00020\u001dX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001b\u0010 \u001a\u00020!8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b$\u0010\u0012\u001a\u0004\b\"\u0010#¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/load/java/lazy/descriptors/LazyJavaAnnotationDescriptor;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/load/java/descriptors/PossiblyExternalAnnotationDescriptor;", "c", "Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;", "javaAnnotation", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "isFreshlySupportedAnnotation", "", "(Lorg/jetbrains/kotlin/load/java/lazy/LazyJavaResolverContext;Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;Z)V", "allValueArguments", "", "Lorg/jetbrains/kotlin/name/Name;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "getAllValueArguments", "()Ljava/util/Map;", "allValueArguments$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "fqName$delegate", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "isFreshlySupportedTypeUseAnnotation", "()Z", "isIdeExternalAnnotation", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/load/java/sources/JavaSourceElement;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/SimpleType;", "getType", "()Lorg/jetbrains/kotlin/types/SimpleType;", "type$delegate", "createTypeForMissingDependencies", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "resolveAnnotationArgument", "argument", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "resolveFromAnnotation", "resolveFromArray", "argumentName", PathTree.NAME_elements, "", "resolveFromEnumValue", "enumClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "entryName", "resolveFromJavaClassObjectType", "javaType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "toString", "", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyJavaAnnotationDescriptor implements AnnotationDescriptor, PossiblyExternalAnnotationDescriptor {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaAnnotationDescriptor.class), "fqName", "getFqName()Lorg/jetbrains/kotlin/name/FqName;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaAnnotationDescriptor.class), ModuleXmlParser.TYPE, "getType()Lorg/jetbrains/kotlin/types/SimpleType;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyJavaAnnotationDescriptor.class), "allValueArguments", "getAllValueArguments()Ljava/util/Map;"))};

    /* JADX INFO: renamed from: allValueArguments$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue allValueArguments;
    private final LazyJavaResolverContext c;

    /* JADX INFO: renamed from: fqName$delegate, reason: from kotlin metadata */
    private final NullableLazyValue fqName;
    private final boolean isFreshlySupportedTypeUseAnnotation;
    private final boolean isIdeExternalAnnotation;
    private final JavaAnnotation javaAnnotation;
    private final JavaSourceElement source;

    /* JADX INFO: renamed from: type$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue type;

    public LazyJavaAnnotationDescriptor(LazyJavaResolverContext c, JavaAnnotation javaAnnotation, boolean z) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(javaAnnotation, "javaAnnotation");
        this.c = c;
        this.javaAnnotation = javaAnnotation;
        this.fqName = c.getStorageManager().createNullableLazyValue(new Function0<FqName>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor$fqName$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FqName invoke() {
                ClassId classId = this.this$0.javaAnnotation.getClassId();
                if (classId != null) {
                    return classId.asSingleFqName();
                }
                return null;
            }
        });
        this.type = c.getStorageManager().createLazyValue(new Function0<SimpleType>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor$type$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final SimpleType invoke() {
                FqName fqName = this.this$0.getFqName();
                if (fqName == null) {
                    return ErrorUtils.createErrorType(ErrorTypeKind.NOT_FOUND_FQNAME_FOR_JAVA_ANNOTATION, this.this$0.javaAnnotation.toString());
                }
                ClassDescriptor classDescriptorMapJavaToKotlin$default = JavaToKotlinClassMapper.mapJavaToKotlin$default(JavaToKotlinClassMapper.INSTANCE, fqName, this.this$0.c.getModule().getBuiltIns(), null, 4, null);
                if (classDescriptorMapJavaToKotlin$default == null) {
                    JavaClass javaClassResolve = this.this$0.javaAnnotation.resolve();
                    classDescriptorMapJavaToKotlin$default = javaClassResolve != null ? this.this$0.c.getComponents().getModuleClassResolver().resolveClass(javaClassResolve) : null;
                    if (classDescriptorMapJavaToKotlin$default == null) {
                        classDescriptorMapJavaToKotlin$default = this.this$0.createTypeForMissingDependencies(fqName);
                    }
                }
                return classDescriptorMapJavaToKotlin$default.getDefaultType();
            }
        });
        this.source = c.getComponents().getSourceElementFactory().source(javaAnnotation);
        this.allValueArguments = c.getStorageManager().createLazyValue(new Function0<Map<Name, ? extends ConstantValue<?>>>() { // from class: org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor$allValueArguments$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<Name, ? extends ConstantValue<?>> invoke() {
                Collection<JavaAnnotationArgument> arguments = this.this$0.javaAnnotation.getArguments();
                LazyJavaAnnotationDescriptor lazyJavaAnnotationDescriptor = this.this$0;
                ArrayList arrayList = new ArrayList();
                for (JavaAnnotationArgument javaAnnotationArgument : arguments) {
                    Name name2 = javaAnnotationArgument.getName();
                    if (name2 == null) {
                        name2 = JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME;
                    }
                    ConstantValue constantValueResolveAnnotationArgument = lazyJavaAnnotationDescriptor.resolveAnnotationArgument(javaAnnotationArgument);
                    Pair pair = constantValueResolveAnnotationArgument != null ? TuplesKt.to(name2, constantValueResolveAnnotationArgument) : null;
                    if (pair != null) {
                        arrayList.add(pair);
                    }
                }
                return MapsKt.toMap(arrayList);
            }
        });
        this.isIdeExternalAnnotation = javaAnnotation.isIdeExternalAnnotation();
        this.isFreshlySupportedTypeUseAnnotation = javaAnnotation.getIsFreshlySupportedTypeUseAnnotation() || z;
    }

    public /* synthetic */ LazyJavaAnnotationDescriptor(LazyJavaResolverContext lazyJavaResolverContext, JavaAnnotation javaAnnotation, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyJavaResolverContext, javaAnnotation, (i & 4) != 0 ? false : z);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public FqName getFqName() {
        return (FqName) StorageKt.getValue(this.fqName, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public SimpleType getType() {
        return (SimpleType) StorageKt.getValue(this.type, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public JavaSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor
    public Map<Name, ConstantValue<?>> getAllValueArguments() {
        return (Map) StorageKt.getValue(this.allValueArguments, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ConstantValue<?> resolveAnnotationArgument(JavaAnnotationArgument argument) {
        if (argument instanceof JavaLiteralAnnotationArgument) {
            return ConstantValueFactory.createConstantValue$default(ConstantValueFactory.INSTANCE, ((JavaLiteralAnnotationArgument) argument).getValue(), null, 2, null);
        }
        if (argument instanceof JavaEnumValueAnnotationArgument) {
            JavaEnumValueAnnotationArgument javaEnumValueAnnotationArgument = (JavaEnumValueAnnotationArgument) argument;
            return resolveFromEnumValue(javaEnumValueAnnotationArgument.getEnumClassId(), javaEnumValueAnnotationArgument.getEntryName());
        }
        if (!(argument instanceof JavaArrayAnnotationArgument)) {
            if (argument instanceof JavaAnnotationAsAnnotationArgument) {
                return resolveFromAnnotation(((JavaAnnotationAsAnnotationArgument) argument).getAnnotation());
            }
            if (argument instanceof JavaClassObjectAnnotationArgument) {
                return resolveFromJavaClassObjectType(((JavaClassObjectAnnotationArgument) argument).getReferencedType());
            }
            return null;
        }
        JavaArrayAnnotationArgument javaArrayAnnotationArgument = (JavaArrayAnnotationArgument) argument;
        Name name2 = javaArrayAnnotationArgument.getName();
        if (name2 == null) {
            name2 = JvmAnnotationNames.DEFAULT_ANNOTATION_MEMBER_NAME;
        }
        Intrinsics.checkNotNull(name2);
        return resolveFromArray(name2, javaArrayAnnotationArgument.getElements());
    }

    private final ConstantValue<?> resolveFromAnnotation(JavaAnnotation javaAnnotation) {
        return new AnnotationValue(new LazyJavaAnnotationDescriptor(this.c, javaAnnotation, false, 4, null));
    }

    private final ConstantValue<?> resolveFromArray(Name argumentName, List<? extends JavaAnnotationArgument> elements) {
        SimpleType arrayType;
        SimpleType type = getType();
        Intrinsics.checkNotNullExpressionValue(type, "<get-type>(...)");
        if (KotlinTypeKt.isError(type)) {
            return null;
        }
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(this);
        Intrinsics.checkNotNull(annotationClass);
        ValueParameterDescriptor annotationParameterByName = DescriptorResolverUtils.getAnnotationParameterByName(argumentName, annotationClass);
        if (annotationParameterByName == null || (arrayType = annotationParameterByName.getType()) == null) {
            arrayType = this.c.getComponents().getModule().getBuiltIns().getArrayType(Variance.INVARIANT, ErrorUtils.createErrorType(ErrorTypeKind.UNKNOWN_ARRAY_ELEMENT_TYPE_OF_ANNOTATION_ARGUMENT, new String[0]));
        }
        Intrinsics.checkNotNull(arrayType);
        List<? extends JavaAnnotationArgument> list = elements;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            NullValue nullValueResolveAnnotationArgument = resolveAnnotationArgument((JavaAnnotationArgument) it.next());
            if (nullValueResolveAnnotationArgument == null) {
                nullValueResolveAnnotationArgument = new NullValue();
            }
            arrayList.add(nullValueResolveAnnotationArgument);
        }
        return ConstantValueFactory.INSTANCE.createArrayValue(arrayList, arrayType);
    }

    private final ConstantValue<?> resolveFromEnumValue(ClassId enumClassId, Name entryName) {
        if (enumClassId == null || entryName == null) {
            return null;
        }
        return new EnumValue(enumClassId, entryName);
    }

    private final ConstantValue<?> resolveFromJavaClassObjectType(JavaType javaType) {
        return KClassValue.INSTANCE.create(this.c.getTypeResolver().transformJavaType(javaType, JavaTypeAttributesKt.toAttributes$default(TypeUsage.COMMON, false, false, null, 7, null)));
    }

    public String toString() {
        return DescriptorRenderer.renderAnnotation$default(DescriptorRenderer.FQ_NAMES_IN_TYPES, this, null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassDescriptor createTypeForMissingDependencies(FqName fqName) {
        ModuleDescriptor module = this.c.getModule();
        ClassId classId = ClassId.topLevel(fqName);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        return FindClassInModuleKt.findNonGenericClassAcrossDependencies(module, classId, this.c.getComponents().getDeserializedDescriptorResolver().getComponents().getNotFoundClasses());
    }

    @Override // org.jetbrains.kotlin.load.java.descriptors.PossiblyExternalAnnotationDescriptor
    /* JADX INFO: renamed from: isIdeExternalAnnotation, reason: from getter */
    public boolean getIsIdeExternalAnnotation() {
        return this.isIdeExternalAnnotation;
    }

    /* JADX INFO: renamed from: isFreshlySupportedTypeUseAnnotation, reason: from getter */
    public final boolean getIsFreshlySupportedTypeUseAnnotation() {
        return this.isFreshlySupportedTypeUseAnnotation;
    }
}
