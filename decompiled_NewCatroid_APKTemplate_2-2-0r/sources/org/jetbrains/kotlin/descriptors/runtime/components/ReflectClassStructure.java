package org.jetbrains.kotlin.descriptors.runtime.components;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.descriptors.runtime.structure.ReflectClassUtilKt;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.constants.ClassLiteralValue;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: ReflectKotlinClass.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\bJ\u001c\u0010\t\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\f\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u001c\u0010\r\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J \u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0001H\u0002J$\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u00102\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0002J\u001a\u0010\u0018\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\u0019\u001a\u00020\u001a*\u0006\u0012\u0002\b\u00030\u0006H\u0002¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/runtime/components/ReflectClassStructure;", "", "()V", "loadClassAnnotations", "", "klass", "Ljava/lang/Class;", "visitor", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationVisitor;", "loadConstructorAnnotations", "memberVisitor", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$MemberVisitor;", "loadFieldAnnotations", "loadMethodAnnotations", "processAnnotation", "annotation", "", "processAnnotationArgumentValue", "Lorg/jetbrains/kotlin/load/kotlin/KotlinJvmBinaryClass$AnnotationArgumentVisitor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "value", "processAnnotationArguments", "annotationType", "visitMembers", "classLiteralValue", "Lorg/jetbrains/kotlin/resolve/constants/ClassLiteralValue;", "descriptors.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ReflectClassStructure {
    public static final ReflectClassStructure INSTANCE = new ReflectClassStructure();

    private ReflectClassStructure() {
    }

    public final void loadClassAnnotations(Class<?> klass, KotlinJvmBinaryClass.AnnotationVisitor visitor) throws InvocationTargetException {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        Annotation[] declaredAnnotations = klass.getDeclaredAnnotations();
        Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "getDeclaredAnnotations(...)");
        for (Annotation annotation : declaredAnnotations) {
            Intrinsics.checkNotNull(annotation);
            processAnnotation(visitor, annotation);
        }
        visitor.visitEnd();
    }

    public final void visitMembers(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(memberVisitor, "memberVisitor");
        loadMethodAnnotations(klass, memberVisitor);
        loadConstructorAnnotations(klass, memberVisitor);
        loadFieldAnnotations(klass, memberVisitor);
    }

    private final void loadMethodAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Method[] declaredMethods = klass.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
        for (Method method : declaredMethods) {
            Name nameIdentifier = Name.identifier(method.getName());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNull(method);
            KotlinJvmBinaryClass.MethodAnnotationVisitor methodAnnotationVisitorVisitMethod = memberVisitor.visitMethod(nameIdentifier, signatureSerializer.methodDesc(method));
            if (methodAnnotationVisitorVisitMethod != null) {
                Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "getDeclaredAnnotations(...)");
                for (Annotation annotation : declaredAnnotations) {
                    Intrinsics.checkNotNull(annotation);
                    processAnnotation(methodAnnotationVisitorVisitMethod, annotation);
                }
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                Intrinsics.checkNotNullExpressionValue(parameterAnnotations, "getParameterAnnotations(...)");
                Annotation[][] annotationArr = parameterAnnotations;
                int length = annotationArr.length;
                for (int i = 0; i < length; i++) {
                    Annotation[] annotationArr2 = annotationArr[i];
                    Intrinsics.checkNotNull(annotationArr2);
                    for (Annotation annotation2 : annotationArr2) {
                        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                        ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                        Intrinsics.checkNotNull(annotation2);
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitParameterAnnotation = methodAnnotationVisitorVisitMethod.visitParameterAnnotation(i, classId, new ReflectAnnotationSource(annotation2));
                        if (annotationArgumentVisitorVisitParameterAnnotation != null) {
                            INSTANCE.processAnnotationArguments(annotationArgumentVisitorVisitParameterAnnotation, annotation2, javaClass);
                        }
                    }
                }
                methodAnnotationVisitorVisitMethod.visitEnd();
            }
        }
    }

    private final void loadConstructorAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Constructor<?>[] constructorArr;
        int i;
        Constructor<?>[] declaredConstructors = klass.getDeclaredConstructors();
        Intrinsics.checkNotNullExpressionValue(declaredConstructors, "getDeclaredConstructors(...)");
        int length = declaredConstructors.length;
        int i2 = 0;
        while (i2 < length) {
            Constructor<?> constructor = declaredConstructors[i2];
            Name name2 = SpecialNames.INIT;
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNull(constructor);
            KotlinJvmBinaryClass.MethodAnnotationVisitor methodAnnotationVisitorVisitMethod = memberVisitor.visitMethod(name2, signatureSerializer.constructorDesc(constructor));
            if (methodAnnotationVisitorVisitMethod == null) {
                constructorArr = declaredConstructors;
                i = length;
            } else {
                Annotation[] declaredAnnotations = constructor.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "getDeclaredAnnotations(...)");
                for (Annotation annotation : declaredAnnotations) {
                    Intrinsics.checkNotNull(annotation);
                    processAnnotation(methodAnnotationVisitorVisitMethod, annotation);
                }
                Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
                Intrinsics.checkNotNull(parameterAnnotations);
                Annotation[][] annotationArr = parameterAnnotations;
                if (!(annotationArr.length == 0)) {
                    int length2 = constructor.getParameterTypes().length - annotationArr.length;
                    int length3 = annotationArr.length;
                    for (int i3 = 0; i3 < length3; i3++) {
                        Annotation[] annotationArr2 = parameterAnnotations[i3];
                        Intrinsics.checkNotNull(annotationArr2);
                        int length4 = annotationArr2.length;
                        int i4 = 0;
                        while (i4 < length4) {
                            Annotation annotation2 = annotationArr2[i4];
                            Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation2));
                            Constructor<?>[] constructorArr2 = declaredConstructors;
                            ClassId classId = ReflectClassUtilKt.getClassId(javaClass);
                            int i5 = length;
                            Intrinsics.checkNotNull(annotation2);
                            KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitParameterAnnotation = methodAnnotationVisitorVisitMethod.visitParameterAnnotation(i3 + length2, classId, new ReflectAnnotationSource(annotation2));
                            if (annotationArgumentVisitorVisitParameterAnnotation != null) {
                                INSTANCE.processAnnotationArguments(annotationArgumentVisitorVisitParameterAnnotation, annotation2, javaClass);
                            }
                            i4++;
                            declaredConstructors = constructorArr2;
                            length = i5;
                        }
                    }
                }
                constructorArr = declaredConstructors;
                i = length;
                methodAnnotationVisitorVisitMethod.visitEnd();
            }
            i2++;
            declaredConstructors = constructorArr;
            length = i;
        }
    }

    private final void loadFieldAnnotations(Class<?> klass, KotlinJvmBinaryClass.MemberVisitor memberVisitor) throws InvocationTargetException {
        Field[] declaredFields = klass.getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
        for (Field field : declaredFields) {
            Name nameIdentifier = Name.identifier(field.getName());
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            SignatureSerializer signatureSerializer = SignatureSerializer.INSTANCE;
            Intrinsics.checkNotNull(field);
            KotlinJvmBinaryClass.AnnotationVisitor annotationVisitorVisitField = memberVisitor.visitField(nameIdentifier, signatureSerializer.fieldDesc(field), null);
            if (annotationVisitorVisitField != null) {
                Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                Intrinsics.checkNotNullExpressionValue(declaredAnnotations, "getDeclaredAnnotations(...)");
                for (Annotation annotation : declaredAnnotations) {
                    Intrinsics.checkNotNull(annotation);
                    processAnnotation(annotationVisitorVisitField, annotation);
                }
                annotationVisitorVisitField.visitEnd();
            }
        }
    }

    private final void processAnnotation(KotlinJvmBinaryClass.AnnotationVisitor visitor, Annotation annotation) throws InvocationTargetException {
        Class<?> javaClass = JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass(annotation));
        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = visitor.visitAnnotation(ReflectClassUtilKt.getClassId(javaClass), new ReflectAnnotationSource(annotation));
        if (annotationArgumentVisitorVisitAnnotation != null) {
            INSTANCE.processAnnotationArguments(annotationArgumentVisitorVisitAnnotation, annotation, javaClass);
        }
    }

    private final void processAnnotationArguments(KotlinJvmBinaryClass.AnnotationArgumentVisitor visitor, Annotation annotation, Class<?> annotationType) throws InvocationTargetException {
        Method[] declaredMethods = annotationType.getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(declaredMethods, "getDeclaredMethods(...)");
        for (Method method : declaredMethods) {
            try {
                Object objInvoke = method.invoke(annotation, new Object[0]);
                Intrinsics.checkNotNull(objInvoke);
                Name nameIdentifier = Name.identifier(method.getName());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                processAnnotationArgumentValue(visitor, nameIdentifier, objInvoke);
            } catch (IllegalAccessException unused) {
            }
        }
        visitor.visitEnd();
    }

    private final ClassLiteralValue classLiteralValue(Class<?> cls) {
        int i = 0;
        while (cls.isArray()) {
            i++;
            cls = cls.getComponentType();
            Intrinsics.checkNotNullExpressionValue(cls, "getComponentType(...)");
        }
        if (cls.isPrimitive()) {
            if (Intrinsics.areEqual(cls, Void.TYPE)) {
                ClassId classId = ClassId.topLevel(StandardNames.FqNames.unit.toSafe());
                Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                return new ClassLiteralValue(classId, i);
            }
            PrimitiveType primitiveType = JvmPrimitiveType.get(cls.getName()).getPrimitiveType();
            Intrinsics.checkNotNullExpressionValue(primitiveType, "getPrimitiveType(...)");
            if (i > 0) {
                ClassId classId2 = ClassId.topLevel(primitiveType.getArrayTypeFqName());
                Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
                return new ClassLiteralValue(classId2, i - 1);
            }
            ClassId classId3 = ClassId.topLevel(primitiveType.getTypeFqName());
            Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(...)");
            return new ClassLiteralValue(classId3, i);
        }
        ClassId classId4 = ReflectClassUtilKt.getClassId(cls);
        JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
        FqName fqNameAsSingleFqName = classId4.asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        ClassId classIdMapJavaToKotlin = javaToKotlinClassMap.mapJavaToKotlin(fqNameAsSingleFqName);
        if (classIdMapJavaToKotlin != null) {
            classId4 = classIdMapJavaToKotlin;
        }
        return new ClassLiteralValue(classId4, i);
    }

    private final void processAnnotationArgumentValue(KotlinJvmBinaryClass.AnnotationArgumentVisitor visitor, Name name2, Object value) throws InvocationTargetException {
        Class<?> enclosingClass = value.getClass();
        if (!Intrinsics.areEqual(enclosingClass, Class.class)) {
            if (ReflectKotlinClassKt.TYPES_ELIGIBLE_FOR_SIMPLE_VISIT.contains(enclosingClass)) {
                visitor.visit(name2, value);
                return;
            }
            if (ReflectClassUtilKt.isEnumClassOrSpecializedEnumEntryClass(enclosingClass)) {
                if (!enclosingClass.isEnum()) {
                    enclosingClass = enclosingClass.getEnclosingClass();
                }
                Intrinsics.checkNotNull(enclosingClass);
                ClassId classId = ReflectClassUtilKt.getClassId(enclosingClass);
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Enum<*>");
                Name nameIdentifier = Name.identifier(((Enum) value).name());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                visitor.visitEnum(name2, classId, nameIdentifier);
                return;
            }
            if (Annotation.class.isAssignableFrom(enclosingClass)) {
                Class<?>[] interfaces = enclosingClass.getInterfaces();
                Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
                Class<?> cls = (Class) ArraysKt.single(interfaces);
                Intrinsics.checkNotNull(cls);
                KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation = visitor.visitAnnotation(name2, ReflectClassUtilKt.getClassId(cls));
                if (annotationArgumentVisitorVisitAnnotation == null) {
                    return;
                }
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Annotation");
                processAnnotationArguments(annotationArgumentVisitorVisitAnnotation, (Annotation) value, cls);
                return;
            }
            if (enclosingClass.isArray()) {
                KotlinJvmBinaryClass.AnnotationArrayArgumentVisitor annotationArrayArgumentVisitorVisitArray = visitor.visitArray(name2);
                if (annotationArrayArgumentVisitorVisitArray == null) {
                    return;
                }
                Class<?> componentType = enclosingClass.getComponentType();
                int i = 0;
                if (componentType.isEnum()) {
                    Intrinsics.checkNotNull(componentType);
                    ClassId classId2 = ReflectClassUtilKt.getClassId(componentType);
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Array<*>");
                    Object[] objArr = (Object[]) value;
                    int length = objArr.length;
                    while (i < length) {
                        Object obj = objArr[i];
                        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Enum<*>");
                        Name nameIdentifier2 = Name.identifier(((Enum) obj).name());
                        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                        annotationArrayArgumentVisitorVisitArray.visitEnum(classId2, nameIdentifier2);
                        i++;
                    }
                } else if (Intrinsics.areEqual(componentType, Class.class)) {
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Array<*>");
                    Object[] objArr2 = (Object[]) value;
                    int length2 = objArr2.length;
                    while (i < length2) {
                        Object obj2 = objArr2[i];
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type java.lang.Class<*>");
                        annotationArrayArgumentVisitorVisitArray.visitClassLiteral(classLiteralValue((Class) obj2));
                        i++;
                    }
                } else if (Annotation.class.isAssignableFrom(componentType)) {
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Array<*>");
                    Object[] objArr3 = (Object[]) value;
                    int length3 = objArr3.length;
                    while (i < length3) {
                        Object obj3 = objArr3[i];
                        Intrinsics.checkNotNull(componentType);
                        KotlinJvmBinaryClass.AnnotationArgumentVisitor annotationArgumentVisitorVisitAnnotation2 = annotationArrayArgumentVisitorVisitArray.visitAnnotation(ReflectClassUtilKt.getClassId(componentType));
                        if (annotationArgumentVisitorVisitAnnotation2 != null) {
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Annotation");
                            processAnnotationArguments(annotationArgumentVisitorVisitAnnotation2, (Annotation) obj3, componentType);
                        }
                        i++;
                    }
                } else {
                    Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Array<*>");
                    Object[] objArr4 = (Object[]) value;
                    int length4 = objArr4.length;
                    while (i < length4) {
                        annotationArrayArgumentVisitorVisitArray.visit(objArr4[i]);
                        i++;
                    }
                }
                annotationArrayArgumentVisitorVisitArray.visitEnd();
                return;
            }
            throw new UnsupportedOperationException("Unsupported annotation argument value (" + enclosingClass + "): " + value);
        }
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type java.lang.Class<*>");
        visitor.visitClassLiteral(name2, classLiteralValue((Class) value));
    }
}
