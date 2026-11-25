package org.jetbrains.kotlin.codegen;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FieldDescriptor;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotated;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.descriptors.impl.AnonymousFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.constants.AnnotationValue;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.BooleanValue;
import org.jetbrains.kotlin.resolve.constants.ByteValue;
import org.jetbrains.kotlin.resolve.constants.CharValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.DoubleValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.ErrorValue;
import org.jetbrains.kotlin.resolve.constants.FloatValue;
import org.jetbrains.kotlin.resolve.constants.IntValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.LongValue;
import org.jetbrains.kotlin.resolve.constants.NullValue;
import org.jetbrains.kotlin.resolve.constants.ShortValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.constants.UByteValue;
import org.jetbrains.kotlin.resolve.constants.UIntValue;
import org.jetbrains.kotlin.resolve.constants.ULongValue;
import org.jetbrains.kotlin.resolve.constants.UShortValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.FlexibleTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: loaded from: classes3.dex */
public abstract class AnnotationCodegen {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final List<JvmFlagAnnotation> FIELD_FLAGS = Arrays.asList(new JvmFlagAnnotation(JvmNames.VOLATILE_ANNOTATION_FQ_NAME.asString(), 64), new JvmFlagAnnotation(JvmNames.TRANSIENT_ANNOTATION_FQ_NAME.asString(), 128));
    public static final List<JvmFlagAnnotation> METHOD_FLAGS = Arrays.asList(new JvmFlagAnnotation(JvmNames.STRICTFP_ANNOTATION_FQ_NAME.asString(), 2048), new JvmFlagAnnotation(JvmNames.SYNCHRONIZED_ANNOTATION_FQ_NAME.asString(), 32));
    private static final AnnotationVisitor NO_ANNOTATION_VISITOR = new AnnotationVisitor(589824) { // from class: org.jetbrains.kotlin.codegen.AnnotationCodegen.1
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "desc", "org/jetbrains/kotlin/codegen/AnnotationCodegen$1", "visitAnnotation"));
        }

        @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
        public AnnotationVisitor visitAnnotation(String str, String str2) {
            if (str2 == null) {
                $$$reportNull$$$0(0);
            }
            return AnnotationCodegen.safe(super.visitAnnotation(str, str2));
        }

        @Override // org.jetbrains.org.objectweb.asm.AnnotationVisitor
        public AnnotationVisitor visitArray(String str) {
            return AnnotationCodegen.safe(super.visitArray(str));
        }
    };
    private static final Map<KotlinRetention, RetentionPolicy> annotationRetentionMap;
    private static final Map<KotlinTarget, ElementType> annotationTargetMap;
    private final InnerClassConsumer innerClassConsumer;
    private final ModuleDescriptor module;
    private final boolean skipNullabilityAnnotations;
    private final GenerationState state;
    private final KotlinTypeMapper typeMapper;

    /* JADX WARN: Code duplicated, block: B:11:0x0014  */
    /* JADX WARN: Code duplicated, block: B:6:0x000a  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 50) {
            switch (i) {
                case 26:
                case 27:
                case 28:
                    str = "@NotNull method %s.%s must not return null";
                    break;
                default:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
            }
        } else {
            str = "@NotNull method %s.%s must not return null";
        }
        if (i != 50) {
            switch (i) {
                case 26:
                case 27:
                case 28:
                    i2 = 2;
                    break;
                default:
                    i2 = 3;
                    break;
            }
        } else {
            i2 = 2;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
            case 31:
            case 34:
            case 37:
            case 40:
            case 43:
            case 46:
            case 49:
                objArr[0] = "state";
                break;
            case 2:
            case 30:
            case 33:
            case 36:
            case 39:
            case 42:
            case 45:
            case 48:
            default:
                objArr[0] = "innerClassConsumer";
                break;
            case 4:
                objArr[0] = "additionalVisibleAnnotations";
                break;
            case 5:
            case 51:
                objArr[0] = "annotated";
                break;
            case 6:
            case 8:
            case 9:
            case 11:
            case 13:
            case 15:
            case 17:
                objArr[0] = "annotationDescriptorsAlreadyPresent";
                break;
            case 7:
            case 10:
            case 25:
                objArr[0] = "descriptor";
                break;
            case 12:
            case 14:
            case 16:
                objArr[0] = "classDescriptor";
                break;
            case 18:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
            case 19:
            case 23:
                objArr[0] = "value";
                break;
            case 20:
                objArr[0] = "expectedType";
                break;
            case 21:
                objArr[0] = "annotationDescriptor";
                break;
            case 22:
                objArr[0] = "parameterName";
                break;
            case 24:
                objArr[0] = "annotationVisitor";
                break;
            case 26:
            case 27:
            case 28:
            case 50:
                objArr[0] = "org/jetbrains/kotlin/codegen/AnnotationCodegen";
                break;
            case 29:
                objArr[0] = "cv";
                break;
            case 32:
            case 35:
            case 44:
            case 47:
                objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                break;
            case 38:
            case 41:
                objArr[0] = "fv";
                break;
        }
        if (i != 50) {
            switch (i) {
                case 26:
                case 27:
                case 28:
                    objArr[1] = "getRetentionPolicy";
                    break;
                default:
                    objArr[1] = "org/jetbrains/kotlin/codegen/AnnotationCodegen";
                    break;
            }
        } else {
            objArr[1] = "safe";
        }
        switch (i) {
            case 4:
                objArr[2] = "genAnnotations";
                break;
            case 5:
            case 6:
                objArr[2] = "generateAdditionalAnnotations";
                break;
            case 7:
            case 8:
                objArr[2] = "generateAdditionalCallableAnnotations";
                break;
            case 9:
            case 10:
                objArr[2] = "generateAdditionalClassAnnotations";
                break;
            case 11:
                objArr[2] = "generateNullabilityAnnotation";
                break;
            case 12:
            case 13:
                objArr[2] = "generateTargetAnnotation";
                break;
            case 14:
            case 15:
                objArr[2] = "generateRetentionAnnotation";
                break;
            case 16:
            case 17:
                objArr[2] = "generateDocumentedAnnotation";
                break;
            case 18:
                objArr[2] = "isBareTypeParameterWithNullableUpperBound";
                break;
            case 19:
            case 20:
                objArr[2] = "generateAnnotationDefaultValue";
                break;
            case 21:
                objArr[2] = "genAnnotation";
                break;
            case 22:
                objArr[2] = "getAnnotationArgumentJvmName";
                break;
            case 23:
            case 24:
                objArr[2] = "genCompileTimeValue";
                break;
            case 25:
                objArr[2] = "getRetentionPolicy";
                break;
            case 26:
            case 27:
            case 28:
            case 50:
                break;
            case 29:
            case 30:
            case 31:
                objArr[2] = "forClass";
                break;
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
                objArr[2] = "forMethod";
                break;
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
                objArr[2] = "forField";
                break;
            case 44:
            case 45:
            case 46:
                objArr[2] = "forParameter";
                break;
            case 47:
            case 48:
            case 49:
                objArr[2] = "forAnnotationDefaultValue";
                break;
            case 51:
                objArr[2] = "generateTypeAnnotations";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 50) {
            switch (i) {
                case 26:
                case 27:
                case 28:
                    break;
                default:
                    throw new IllegalArgumentException(str2);
            }
        }
        throw new IllegalStateException(str2);
    }

    abstract AnnotationVisitor visitAnnotation(String str, boolean z);

    public static final class JvmFlagAnnotation {
        private final FqName fqName;
        private final int jvmFlag;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "fqName", "org/jetbrains/kotlin/codegen/AnnotationCodegen$JvmFlagAnnotation", "<init>"));
        }

        public JvmFlagAnnotation(String str, int i) {
            if (str == null) {
                $$$reportNull$$$0(0);
            }
            this.fqName = new FqName(str);
            this.jvmFlag = i;
        }

        public int getJvmFlag(Annotated annotated) {
            if (annotated == null || !annotated.getAnnotations().hasAnnotation(this.fqName)) {
                return 0;
            }
            return this.jvmFlag;
        }
    }

    static {
        EnumMap enumMap = new EnumMap(KotlinTarget.class);
        annotationTargetMap = enumMap;
        enumMap.put(KotlinTarget.CLASS, ElementType.TYPE);
        enumMap.put(KotlinTarget.ANNOTATION_CLASS, ElementType.ANNOTATION_TYPE);
        enumMap.put(KotlinTarget.CONSTRUCTOR, ElementType.CONSTRUCTOR);
        enumMap.put(KotlinTarget.LOCAL_VARIABLE, ElementType.LOCAL_VARIABLE);
        enumMap.put(KotlinTarget.FUNCTION, ElementType.METHOD);
        enumMap.put(KotlinTarget.PROPERTY_GETTER, ElementType.METHOD);
        enumMap.put(KotlinTarget.PROPERTY_SETTER, ElementType.METHOD);
        enumMap.put(KotlinTarget.FIELD, ElementType.FIELD);
        enumMap.put(KotlinTarget.VALUE_PARAMETER, ElementType.PARAMETER);
        enumMap.put(KotlinTarget.TYPE_PARAMETER, ElementType.TYPE_PARAMETER);
        enumMap.put(KotlinTarget.TYPE, ElementType.TYPE_USE);
        EnumMap enumMap2 = new EnumMap(KotlinRetention.class);
        annotationRetentionMap = enumMap2;
        enumMap2.put(KotlinRetention.SOURCE, RetentionPolicy.SOURCE);
        enumMap2.put(KotlinRetention.BINARY, RetentionPolicy.CLASS);
        enumMap2.put(KotlinRetention.RUNTIME, RetentionPolicy.RUNTIME);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private AnnotationCodegen(InnerClassConsumer innerClassConsumer, GenerationState generationState) {
        this(innerClassConsumer, generationState, false);
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(0);
        }
        if (generationState == null) {
            $$$reportNull$$$0(1);
        }
    }

    private AnnotationCodegen(InnerClassConsumer innerClassConsumer, GenerationState generationState, boolean z) {
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(2);
        }
        if (generationState == null) {
            $$$reportNull$$$0(3);
        }
        this.innerClassConsumer = innerClassConsumer;
        this.typeMapper = generationState.getTypeMapper();
        this.module = generationState.getModule();
        this.state = generationState;
        this.skipNullabilityAnnotations = z;
    }

    public void genAnnotations(Annotated annotated, Type type, KotlinType kotlinType) {
        genAnnotations(annotated, type, kotlinType, null, Collections.emptyList());
    }

    public void genAnnotations(Annotated annotated, Type type, KotlinType kotlinType, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility, List<String> list) {
        if (list == null) {
            $$$reportNull$$$0(4);
        }
        if (annotated == null) {
            return;
        }
        HashSet hashSet = new HashSet();
        for (AnnotationDescriptor annotationDescriptor : annotated.getAnnotations()) {
            Set<KotlinTarget> setApplicableTargetSet = AnnotationChecker.applicableTargetSet(annotationDescriptor);
            if (!(annotated instanceof AnonymousFunctionDescriptor) || setApplicableTargetSet.contains(KotlinTarget.FUNCTION) || setApplicableTargetSet.contains(KotlinTarget.PROPERTY_GETTER) || setApplicableTargetSet.contains(KotlinTarget.PROPERTY_SETTER)) {
                if (!(annotated instanceof ClassDescriptor) || setApplicableTargetSet.contains(KotlinTarget.CLASS) || setApplicableTargetSet.contains(KotlinTarget.ANNOTATION_CLASS) || ((ClassDescriptor) annotated).getVisibility() != DescriptorVisibilities.LOCAL) {
                    String strGenAnnotation = genAnnotation(annotationDescriptor, null, false);
                    if (strGenAnnotation != null) {
                        hashSet.add(strGenAnnotation);
                    }
                }
            }
        }
        for (String str : list) {
            generateAnnotationIfNotPresent(hashSet, str, true);
            hashSet.add(str);
        }
        generateAdditionalAnnotations(annotated, type, hashSet, declarationDescriptorWithVisibility);
        generateTypeAnnotations(annotated, kotlinType);
    }

    private void generateAdditionalAnnotations(Annotated annotated, Type type, Set<String> set, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        if (annotated == null) {
            $$$reportNull$$$0(5);
        }
        if (set == null) {
            $$$reportNull$$$0(6);
        }
        if (annotated instanceof CallableDescriptor) {
            generateAdditionalCallableAnnotations((CallableDescriptor) annotated, type, set, declarationDescriptorWithVisibility);
        } else if (annotated instanceof FieldDescriptor) {
            generateAdditionalCallableAnnotations(((FieldDescriptor) annotated).getCorrespondingProperty(), type, set, declarationDescriptorWithVisibility);
        } else if (annotated instanceof ClassDescriptor) {
            generateAdditionalClassAnnotations(set, (ClassDescriptor) annotated);
        }
    }

    private void generateAdditionalCallableAnnotations(CallableDescriptor callableDescriptor, Type type, Set<String> set, DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility) {
        DeclarationDescriptor containingDeclaration;
        if (callableDescriptor == null) {
            $$$reportNull$$$0(7);
        }
        if (set == null) {
            $$$reportNull$$$0(8);
        }
        if (isInvisibleFromTheOutside(callableDescriptor)) {
            return;
        }
        if (callableDescriptor instanceof ParameterDescriptor) {
            if (declarationDescriptorWithVisibility == null) {
                containingDeclaration = callableDescriptor.getContainingModule();
            }
            if (isInvisibleFromTheOutside(containingDeclaration)) {
                return;
            }
        }
        containingDeclaration = declarationDescriptorWithVisibility;
        if (((callableDescriptor instanceof PropertyGetterDescriptor) && DescriptorUtils.isAnnotationClass(callableDescriptor.getContainingModule())) || type == null || AsmUtil.isPrimitive(type) || this.skipNullabilityAnnotations) {
            return;
        }
        generateNullabilityAnnotation(callableDescriptor.getReturnType(), set);
    }

    private void generateAdditionalClassAnnotations(Set<String> set, ClassDescriptor classDescriptor) {
        if (set == null) {
            $$$reportNull$$$0(9);
        }
        if (classDescriptor == null) {
            $$$reportNull$$$0(10);
        }
        if (classDescriptor.getClassKind() == ClassKind.ANNOTATION_CLASS) {
            generateDocumentedAnnotation(classDescriptor, set);
            generateRetentionAnnotation(classDescriptor, set);
            generateTargetAnnotation(classDescriptor, set);
        }
    }

    private static boolean isInvisibleFromTheOutside(DeclarationDescriptor declarationDescriptor) {
        if (isAccessor(declarationDescriptor)) {
            return true;
        }
        return (declarationDescriptor instanceof MemberDescriptor) && DescriptorAsmUtil.getVisibilityAccessFlag((MemberDescriptor) declarationDescriptor) == 2;
    }

    private static boolean isAccessor(Annotated annotated) {
        return (annotated instanceof CallableMemberDescriptor) && KotlinTypeMapper.isAccessor((CallableMemberDescriptor) annotated);
    }

    private void generateNullabilityAnnotation(KotlinType kotlinType, Set<String> set) {
        if (set == null) {
            $$$reportNull$$$0(11);
        }
        if (kotlinType == null || isBareTypeParameterWithNullableUpperBound(kotlinType)) {
            return;
        }
        if (FlexibleTypesKt.isFlexible(kotlinType)) {
            FlexibleType flexibleTypeAsFlexibleType = FlexibleTypesKt.asFlexibleType(kotlinType);
            if (!TypeUtils.isNullableType(flexibleTypeAsFlexibleType.getLowerBound()) && TypeUtils.isNullableType(flexibleTypeAsFlexibleType.getUpperBound())) {
                if (kotlinType.getAnnotations().mo6752findAnnotation(JvmAnnotationNames.JETBRAINS_NOT_NULL_ANNOTATION) != null) {
                    generateAnnotationIfNotPresent(set, Type.getType((Class<?>) NotNull.class).getDescriptor(), false);
                    return;
                }
                return;
            }
        }
        generateAnnotationIfNotPresent(set, Type.getType((Class<?>) (TypeUtils.isNullableType(kotlinType) ? Nullable.class : NotNull.class)).getDescriptor(), false);
    }

    private void generateTargetAnnotation(ClassDescriptor classDescriptor, Set<String> set) {
        Set setNoneOf;
        if (classDescriptor == null) {
            $$$reportNull$$$0(12);
        }
        if (set == null) {
            $$$reportNull$$$0(13);
        }
        String descriptor = Type.getType((Class<?>) Target.class).getDescriptor();
        if (set.add(descriptor)) {
            Set<KotlinTarget> setApplicableTargetSetFromTargetAnnotationOrNull = AnnotationChecker.INSTANCE.applicableTargetSetFromTargetAnnotationOrNull(classDescriptor);
            if (setApplicableTargetSetFromTargetAnnotationOrNull == null) {
                setNoneOf = getJavaTargetList(classDescriptor);
                if (setNoneOf == null) {
                    return;
                }
            } else {
                setNoneOf = EnumSet.noneOf(ElementType.class);
                Iterator<KotlinTarget> it = setApplicableTargetSetFromTargetAnnotationOrNull.iterator();
                while (it.hasNext()) {
                    ElementType elementType = annotationTargetMap.get(it.next());
                    if (elementType != null) {
                        setNoneOf.add(elementType);
                    }
                }
            }
            AnnotationVisitor annotationVisitorVisitAnnotation = visitAnnotation(descriptor, true);
            AnnotationVisitor annotationVisitorVisitArray = annotationVisitorVisitAnnotation.visitArray("value");
            Iterator it2 = setNoneOf.iterator();
            while (it2.hasNext()) {
                annotationVisitorVisitArray.visitEnum(null, Type.getType((Class<?>) ElementType.class).getDescriptor(), ((ElementType) it2.next()).name());
            }
            annotationVisitorVisitArray.visitEnd();
            annotationVisitorVisitAnnotation.visitEnd();
        }
    }

    private void generateRetentionAnnotation(ClassDescriptor classDescriptor, Set<String> set) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(14);
        }
        if (set == null) {
            $$$reportNull$$$0(15);
        }
        RetentionPolicy retentionPolicy = getRetentionPolicy(classDescriptor);
        String descriptor = Type.getType((Class<?>) Retention.class).getDescriptor();
        if (set.add(descriptor)) {
            AnnotationVisitor annotationVisitorVisitAnnotation = visitAnnotation(descriptor, true);
            annotationVisitorVisitAnnotation.visitEnum("value", Type.getType((Class<?>) RetentionPolicy.class).getDescriptor(), retentionPolicy.name());
            annotationVisitorVisitAnnotation.visitEnd();
        }
    }

    private void generateDocumentedAnnotation(ClassDescriptor classDescriptor, Set<String> set) {
        if (classDescriptor == null) {
            $$$reportNull$$$0(16);
        }
        if (set == null) {
            $$$reportNull$$$0(17);
        }
        if (DescriptorUtilsKt.isDocumentedAnnotation(classDescriptor)) {
            String descriptor = Type.getType((Class<?>) Documented.class).getDescriptor();
            if (set.add(descriptor)) {
                visitAnnotation(descriptor, true).visitEnd();
            }
        }
    }

    private void generateAnnotationIfNotPresent(Set<String> set, String str, boolean z) {
        if (set.contains(str)) {
            return;
        }
        visitAnnotation(str, z).visitEnd();
    }

    private static boolean isBareTypeParameterWithNullableUpperBound(KotlinType kotlinType) {
        if (kotlinType == null) {
            $$$reportNull$$$0(18);
        }
        return !kotlinType.getIsMarkedNullable() && (kotlinType.getConstructor().getThis$0() instanceof TypeParameterDescriptor) && TypeUtils.hasNullableSuperType(kotlinType);
    }

    public void generateAnnotationDefaultValue(ConstantValue<?> constantValue, KotlinType kotlinType) {
        if (constantValue == null) {
            $$$reportNull$$$0(19);
        }
        if (kotlinType == null) {
            $$$reportNull$$$0(20);
        }
        AnnotationVisitor annotationVisitorVisitAnnotation = visitAnnotation(null, false);
        genCompileTimeValue(null, constantValue, annotationVisitorVisitAnnotation);
        annotationVisitorVisitAnnotation.visitEnd();
    }

    private String genAnnotation(AnnotationDescriptor annotationDescriptor, TypePath typePath, boolean z) {
        AnnotationVisitor annotationVisitorVisitAnnotation;
        if (annotationDescriptor == null) {
            $$$reportNull$$$0(21);
        }
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        RetentionPolicy retentionPolicy = getRetentionPolicy(annotationClass);
        if ((retentionPolicy == RetentionPolicy.SOURCE && !this.typeMapper.getClassBuilderMode().generateSourceRetentionAnnotations) || OptionalAnnotationUtil.isOptionalAnnotationClass(annotationClass)) {
            return null;
        }
        this.innerClassConsumer.addInnerClassInfoFromAnnotation(annotationClass);
        String descriptor = this.typeMapper.mapType(annotationDescriptor.getType()).getDescriptor();
        if (z) {
            annotationVisitorVisitAnnotation = visitTypeAnnotation(descriptor, typePath, retentionPolicy == RetentionPolicy.RUNTIME);
        } else {
            annotationVisitorVisitAnnotation = visitAnnotation(descriptor, retentionPolicy == RetentionPolicy.RUNTIME);
        }
        genAnnotationArguments(annotationDescriptor, annotationVisitorVisitAnnotation);
        annotationVisitorVisitAnnotation.visitEnd();
        return descriptor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void genAnnotationArguments(AnnotationDescriptor annotationDescriptor, AnnotationVisitor annotationVisitor) {
        ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
        for (Map.Entry<Name, ConstantValue<?>> entry : annotationDescriptor.getAllValueArguments().entrySet()) {
            genCompileTimeValue(getAnnotationArgumentJvmName(annotationClass, entry.getKey()), entry.getValue(), annotationVisitor);
        }
    }

    private String getAnnotationArgumentJvmName(ClassDescriptor classDescriptor, Name name2) {
        if (name2 == null) {
            $$$reportNull$$$0(22);
        }
        if (classDescriptor == null) {
            return name2.asString();
        }
        Collection<? extends PropertyDescriptor> contributedVariables = classDescriptor.getUnsubstitutedMemberScope().getContributedVariables(name2, NoLookupLocation.FROM_BACKEND);
        return contributedVariables.size() != 1 ? name2.asString() : this.typeMapper.mapAnnotationParameterName(contributedVariables.iterator().next());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void genCompileTimeValue(final String str, ConstantValue<?> constantValue, final AnnotationVisitor annotationVisitor) {
        if (constantValue == null) {
            $$$reportNull$$$0(23);
        }
        if (annotationVisitor == null) {
            $$$reportNull$$$0(24);
        }
        constantValue.accept(new AnnotationArgumentVisitor<Void, Void>() { // from class: org.jetbrains.kotlin.codegen.AnnotationCodegen.2
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/codegen/AnnotationCodegen$2", "visitLongValue"));
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitLongValue(LongValue longValue, Void r2) {
                if (longValue == null) {
                    $$$reportNull$$$0(0);
                }
                return visitSimpleValue(longValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitIntValue(IntValue intValue, Void r2) {
                return visitSimpleValue(intValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitShortValue(ShortValue shortValue, Void r2) {
                return visitSimpleValue(shortValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitByteValue(ByteValue byteValue, Void r2) {
                return visitSimpleValue(byteValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitDoubleValue(DoubleValue doubleValue, Void r2) {
                return visitSimpleValue(doubleValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitFloatValue(FloatValue floatValue, Void r2) {
                return visitSimpleValue(floatValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitBooleanValue(BooleanValue booleanValue, Void r2) {
                return visitSimpleValue(booleanValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitCharValue(CharValue charValue, Void r2) {
                return visitSimpleValue(charValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitStringValue(StringValue stringValue, Void r2) {
                return visitSimpleValue(stringValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitEnumValue(EnumValue enumValue, Void r4) {
                ClassId enumClassId = enumValue.getEnumClassId();
                String descriptor = AsmUtil.asmTypeByClassId(enumClassId).getDescriptor();
                String strAsString = enumValue.getEnumEntryName().asString();
                ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(AnnotationCodegen.this.state.getModule(), enumClassId);
                if (classDescriptorFindClassAcrossModuleDependencies != null) {
                    AnnotationCodegen.this.innerClassConsumer.addInnerClassInfoFromAnnotation(classDescriptorFindClassAcrossModuleDependencies);
                }
                annotationVisitor.visitEnum(str, descriptor, strAsString);
                return null;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitArrayValue(ArrayValue arrayValue, Void r5) {
                AnnotationVisitor annotationVisitorVisitArray = annotationVisitor.visitArray(str);
                Iterator<? extends ConstantValue<?>> it = arrayValue.getValue().iterator();
                while (it.hasNext()) {
                    AnnotationCodegen.this.genCompileTimeValue(null, it.next(), annotationVisitorVisitArray);
                }
                annotationVisitorVisitArray.visitEnd();
                return null;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitAnnotationValue(AnnotationValue annotationValue, Void r5) {
                KotlinType type = annotationValue.getValue().getType();
                AnnotationCodegen.this.innerClassConsumer.addInnerClassInfoFromAnnotation(DescriptorUtils.getClassDescriptorForType(type));
                AnnotationVisitor annotationVisitorVisitAnnotation = annotationVisitor.visitAnnotation(str, AnnotationCodegen.this.typeMapper.mapType(type).getDescriptor());
                AnnotationCodegen.this.genAnnotationArguments(annotationValue.getValue(), annotationVisitorVisitAnnotation);
                annotationVisitorVisitAnnotation.visitEnd();
                return null;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitKClassValue(KClassValue kClassValue, Void r4) {
                KotlinType argumentType = kClassValue.getArgumentType(AnnotationCodegen.this.module);
                AnnotationCodegen.this.innerClassConsumer.addInnerClassInfoFromAnnotation(DescriptorUtils.getClassDescriptorForType(argumentType));
                if (InlineClassesUtilsKt.isInlineClassType(argumentType)) {
                    argumentType = TypeUtils.makeNullable(argumentType);
                }
                annotationVisitor.visit(str, AnnotationCodegen.this.typeMapper.mapType(argumentType));
                return null;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitUByteValue(UByteValue uByteValue, Void r2) {
                return visitSimpleValue(uByteValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitUShortValue(UShortValue uShortValue, Void r2) {
                return visitSimpleValue(uShortValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitUIntValue(UIntValue uIntValue, Void r2) {
                return visitSimpleValue(uIntValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitULongValue(ULongValue uLongValue, Void r2) {
                return visitSimpleValue(uLongValue);
            }

            private Void visitSimpleValue(ConstantValue<?> constantValue2) {
                annotationVisitor.visit(str, constantValue2.getValue());
                return null;
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitErrorValue(ErrorValue errorValue, Void r2) {
                return visitUnsupportedValue(errorValue);
            }

            @Override // org.jetbrains.kotlin.descriptors.annotations.AnnotationArgumentVisitor
            public Void visitNullValue(NullValue nullValue, Void r2) {
                return visitUnsupportedValue(nullValue);
            }

            private Void visitUnsupportedValue(ConstantValue<?> constantValue2) {
                if (AnnotationCodegen.this.typeMapper.getClassBuilderMode().generateBodies) {
                    throw new IllegalStateException("Don't know how to compile annotation value " + constantValue2);
                }
                return null;
            }
        }, null);
    }

    private static Set<ElementType> getJavaTargetList(ClassDescriptor classDescriptor) {
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = classDescriptor.getAnnotations().mo6752findAnnotation(new FqName(Target.class.getName()));
        if (annotationDescriptorMo6752findAnnotation == null) {
            return null;
        }
        Collection<ConstantValue<?>> collectionValues = annotationDescriptorMo6752findAnnotation.getAllValueArguments().values();
        if (collectionValues.isEmpty()) {
            return null;
        }
        ConstantValue<?> next = collectionValues.iterator().next();
        if (!(next instanceof ArrayValue)) {
            return null;
        }
        List<? extends ConstantValue<?>> value = ((ArrayValue) next).getValue();
        EnumSet enumSetNoneOf = EnumSet.noneOf(ElementType.class);
        for (ConstantValue<?> constantValue : value) {
            if (constantValue instanceof EnumValue) {
                EnumValue enumValue = (EnumValue) constantValue;
                if (ElementType.class.getName().equals(enumValue.getEnumClassId().asSingleFqName().asString())) {
                    enumSetNoneOf.add(ElementType.valueOf(enumValue.getEnumEntryName().asString()));
                }
            }
        }
        return enumSetNoneOf;
    }

    private static RetentionPolicy getRetentionPolicy(Annotated annotated) {
        if (annotated == null) {
            $$$reportNull$$$0(25);
        }
        KotlinRetention annotationRetention = DescriptorUtilsKt.getAnnotationRetention(annotated);
        if (annotationRetention != null) {
            RetentionPolicy retentionPolicy = annotationRetentionMap.get(annotationRetention);
            if (retentionPolicy == null) {
                $$$reportNull$$$0(26);
            }
            return retentionPolicy;
        }
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = annotated.getAnnotations().mo6752findAnnotation(new FqName(Retention.class.getName()));
        if (annotationDescriptorMo6752findAnnotation != null) {
            ConstantValue constantValue = (ConstantValue) CollectionsKt.firstOrNull(annotationDescriptorMo6752findAnnotation.getAllValueArguments().values());
            if (constantValue instanceof EnumValue) {
                EnumValue enumValue = (EnumValue) constantValue;
                if (RetentionPolicy.class.getName().equals(enumValue.getEnumClassId().asSingleFqName().asString())) {
                    RetentionPolicy retentionPolicyValueOf = RetentionPolicy.valueOf(enumValue.getEnumEntryName().asString());
                    if (retentionPolicyValueOf == null) {
                        $$$reportNull$$$0(27);
                    }
                    return retentionPolicyValueOf;
                }
            }
        }
        RetentionPolicy retentionPolicy2 = RetentionPolicy.RUNTIME;
        if (retentionPolicy2 == null) {
            $$$reportNull$$$0(28);
        }
        return retentionPolicy2;
    }

    AnnotationVisitor visitTypeAnnotation(String str, TypePath typePath, boolean z) {
        throw new RuntimeException("Not implemented");
    }

    public static AnnotationCodegen forClass(final ClassVisitor classVisitor, InnerClassConsumer innerClassConsumer, GenerationState generationState) {
        if (classVisitor == null) {
            $$$reportNull$$$0(29);
        }
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(30);
        }
        if (generationState == null) {
            $$$reportNull$$$0(31);
        }
        return new AnnotationCodegen(innerClassConsumer, generationState) { // from class: org.jetbrains.kotlin.codegen.AnnotationCodegen.3
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/codegen/AnnotationCodegen$3", "visitAnnotation"));
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitAnnotation(String str, boolean z) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(classVisitor.visitAnnotation(str, z));
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(0);
                }
                return annotationVisitorSafe;
            }
        };
    }

    public static AnnotationCodegen forMethod(MethodVisitor methodVisitor, InnerClassConsumer innerClassConsumer, GenerationState generationState) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(32);
        }
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(33);
        }
        if (generationState == null) {
            $$$reportNull$$$0(34);
        }
        return forMethod(methodVisitor, innerClassConsumer, generationState, false);
    }

    public static AnnotationCodegen forMethod(final MethodVisitor methodVisitor, InnerClassConsumer innerClassConsumer, GenerationState generationState, boolean z) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(35);
        }
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(36);
        }
        if (generationState == null) {
            $$$reportNull$$$0(37);
        }
        return new AnnotationCodegen(innerClassConsumer, generationState, z) { // from class: org.jetbrains.kotlin.codegen.AnnotationCodegen.4
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/codegen/AnnotationCodegen$4";
                if (i != 1) {
                    objArr[1] = "visitAnnotation";
                } else {
                    objArr[1] = "visitTypeAnnotation";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitAnnotation(String str, boolean z2) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(methodVisitor.visitAnnotation(str, z2));
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(0);
                }
                return annotationVisitorSafe;
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitTypeAnnotation(String str, TypePath typePath, boolean z2) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(methodVisitor.visitTypeAnnotation(TypeReference.newTypeReference(20).getValue(), typePath, str, z2));
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(1);
                }
                return annotationVisitorSafe;
            }
        };
    }

    public static AnnotationCodegen forField(FieldVisitor fieldVisitor, InnerClassConsumer innerClassConsumer, GenerationState generationState) {
        if (fieldVisitor == null) {
            $$$reportNull$$$0(38);
        }
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(39);
        }
        if (generationState == null) {
            $$$reportNull$$$0(40);
        }
        return forField(fieldVisitor, innerClassConsumer, generationState, false);
    }

    public static AnnotationCodegen forField(final FieldVisitor fieldVisitor, InnerClassConsumer innerClassConsumer, GenerationState generationState, boolean z) {
        if (fieldVisitor == null) {
            $$$reportNull$$$0(41);
        }
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(42);
        }
        if (generationState == null) {
            $$$reportNull$$$0(43);
        }
        return new AnnotationCodegen(innerClassConsumer, generationState, z) { // from class: org.jetbrains.kotlin.codegen.AnnotationCodegen.5
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/codegen/AnnotationCodegen$5";
                if (i != 1) {
                    objArr[1] = "visitAnnotation";
                } else {
                    objArr[1] = "visitTypeAnnotation";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitAnnotation(String str, boolean z2) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(fieldVisitor.visitAnnotation(str, z2));
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(0);
                }
                return annotationVisitorSafe;
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitTypeAnnotation(String str, TypePath typePath, boolean z2) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(fieldVisitor.visitTypeAnnotation(TypeReference.newTypeReference(19).getValue(), typePath, str, z2));
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(1);
                }
                return annotationVisitorSafe;
            }
        };
    }

    public static AnnotationCodegen forParameter(final int i, final MethodVisitor methodVisitor, InnerClassConsumer innerClassConsumer, GenerationState generationState, boolean z) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(44);
        }
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(45);
        }
        if (generationState == null) {
            $$$reportNull$$$0(46);
        }
        return new AnnotationCodegen(innerClassConsumer, generationState, z) { // from class: org.jetbrains.kotlin.codegen.AnnotationCodegen.6
            private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                Object[] objArr = new Object[2];
                objArr[0] = "org/jetbrains/kotlin/codegen/AnnotationCodegen$6";
                if (i2 != 1) {
                    objArr[1] = "visitAnnotation";
                } else {
                    objArr[1] = "visitTypeAnnotation";
                }
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", objArr));
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitAnnotation(String str, boolean z2) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(methodVisitor.visitParameterAnnotation(i, str, z2));
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(0);
                }
                return annotationVisitorSafe;
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitTypeAnnotation(String str, TypePath typePath, boolean z2) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(methodVisitor.visitTypeAnnotation(TypeReference.newFormalParameterReference(i).getValue(), typePath, str, z2));
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(1);
                }
                return annotationVisitorSafe;
            }
        };
    }

    public static AnnotationCodegen forAnnotationDefaultValue(final MethodVisitor methodVisitor, InnerClassConsumer innerClassConsumer, GenerationState generationState) {
        if (methodVisitor == null) {
            $$$reportNull$$$0(47);
        }
        if (innerClassConsumer == null) {
            $$$reportNull$$$0(48);
        }
        if (generationState == null) {
            $$$reportNull$$$0(49);
        }
        return new AnnotationCodegen(innerClassConsumer, generationState) { // from class: org.jetbrains.kotlin.codegen.AnnotationCodegen.7
            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/codegen/AnnotationCodegen$7", "visitAnnotation"));
            }

            @Override // org.jetbrains.kotlin.codegen.AnnotationCodegen
            AnnotationVisitor visitAnnotation(String str, boolean z) {
                AnnotationVisitor annotationVisitorSafe = AnnotationCodegen.safe(methodVisitor.visitAnnotationDefault());
                if (annotationVisitorSafe == null) {
                    $$$reportNull$$$0(0);
                }
                return annotationVisitorSafe;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AnnotationVisitor safe(AnnotationVisitor annotationVisitor) {
        if (annotationVisitor == null) {
            annotationVisitor = NO_ANNOTATION_VISITOR;
        }
        if (annotationVisitor == null) {
            $$$reportNull$$$0(50);
        }
        return annotationVisitor;
    }

    private void generateTypeAnnotations(Annotated annotated, KotlinType kotlinType) {
        if (annotated == null) {
            $$$reportNull$$$0(51);
        }
        if (isAccessor(annotated) || kotlinType == null || !this.state.getConfiguration().getBoolean(JVMConfigurationKeys.EMIT_JVM_TYPE_ANNOTATIONS)) {
            return;
        }
        Iterator<T> it = new PsiTypeAnnotationCollector().collectTypeAnnotations(kotlinType).iterator();
        while (it.hasNext()) {
            TypePathInfo typePathInfo = (TypePathInfo) it.next();
            Iterator it2 = typePathInfo.getAnnotations().iterator();
            while (it2.hasNext()) {
                genAnnotation((AnnotationDescriptor) it2.next(), typePathInfo.getPath(), true);
            }
        }
    }
}
