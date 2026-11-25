package org.jetbrains.kotlin.resolve.jvm.multiplatform;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.load.java.components.JavaPropertyInitializerEvaluatorImpl;
import org.jetbrains.kotlin.load.java.sources.JavaSourceElement;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationAsAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaClassObjectAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaElement;
import org.jetbrains.kotlin.load.java.structure.JavaEnumValueAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaLiteralAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValueFactory;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: JavaActualAnnotationArgumentExtractor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001e\u0010\u0007\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001a\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\b*\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0002¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/multiplatform/JavaActualAnnotationArgumentExtractor;", "Lorg/jetbrains/kotlin/resolve/checkers/ExpectedActualDeclarationChecker$ActualAnnotationArgumentExtractor;", "()V", "convertTypeToKClassValue", "Lorg/jetbrains/kotlin/resolve/constants/KClassValue;", "javaType", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", "extractDefaultValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "convert", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaActualAnnotationArgumentExtractor implements ExpectedActualDeclarationChecker.ActualAnnotationArgumentExtractor {
    @Override // org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker.ActualAnnotationArgumentExtractor
    public ConstantValue<?> extractDefaultValue(ValueParameterDescriptor parameter, KotlinType expectedType) {
        JavaAnnotationArgument annotationParameterDefaultValue;
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(expectedType, "expectedType");
        SourceElement source = parameter.getSourceElement();
        JavaSourceElement javaSourceElement = source instanceof JavaSourceElement ? (JavaSourceElement) source : null;
        JavaElement javaElement = javaSourceElement != null ? javaSourceElement.getJavaElement() : null;
        JavaMethod javaMethod = javaElement instanceof JavaMethod ? (JavaMethod) javaElement : null;
        if (javaMethod == null || (annotationParameterDefaultValue = javaMethod.getAnnotationParameterDefaultValue()) == null) {
            return null;
        }
        return convert(annotationParameterDefaultValue, expectedType);
    }

    private final ConstantValue<?> convert(JavaAnnotationArgument javaAnnotationArgument, KotlinType kotlinType) {
        Name entryName;
        EnumValue enumValue = null;
        if (javaAnnotationArgument instanceof JavaLiteralAnnotationArgument) {
            Object value = ((JavaLiteralAnnotationArgument) javaAnnotationArgument).getValue();
            if (value != null) {
                return JavaPropertyInitializerEvaluatorImpl.INSTANCE.convertLiteralValue$frontend_java(value, kotlinType);
            }
            return null;
        }
        if (javaAnnotationArgument instanceof JavaEnumValueAnnotationArgument) {
            JavaEnumValueAnnotationArgument javaEnumValueAnnotationArgument = (JavaEnumValueAnnotationArgument) javaAnnotationArgument;
            ClassId enumClassId = javaEnumValueAnnotationArgument.getEnumClassId();
            if (enumClassId != null && (entryName = javaEnumValueAnnotationArgument.getEntryName()) != null) {
                enumValue = new EnumValue(enumClassId, entryName);
            }
            return enumValue;
        }
        if (javaAnnotationArgument instanceof JavaArrayAnnotationArgument) {
            KotlinType arrayElementType = TypeUtilsKt.getBuiltIns(kotlinType).getArrayElementType(kotlinType);
            Intrinsics.checkNotNullExpressionValue(arrayElementType, "getArrayElementType(...)");
            ConstantValueFactory constantValueFactory = ConstantValueFactory.INSTANCE;
            List<JavaAnnotationArgument> elements = ((JavaArrayAnnotationArgument) javaAnnotationArgument).getElements();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = elements.iterator();
            while (it.hasNext()) {
                ConstantValue<?> constantValueConvert = convert((JavaAnnotationArgument) it.next(), arrayElementType);
                if (constantValueConvert != null) {
                    arrayList.add(constantValueConvert);
                }
            }
            return constantValueFactory.createArrayValue(arrayList, kotlinType);
        }
        if (!(javaAnnotationArgument instanceof JavaAnnotationAsAnnotationArgument) && (javaAnnotationArgument instanceof JavaClassObjectAnnotationArgument)) {
            return convertTypeToKClassValue(((JavaClassObjectAnnotationArgument) javaAnnotationArgument).getReferencedType());
        }
        return null;
    }

    private final KClassValue convertTypeToKClassValue(JavaType javaType) {
        int i = 0;
        while (javaType instanceof JavaArrayType) {
            javaType = ((JavaArrayType) javaType).getComponentType();
            i++;
        }
        if (javaType instanceof JavaPrimitiveType) {
            PrimitiveType type = ((JavaPrimitiveType) javaType).getType();
            if (type == null) {
                ClassId classId = ClassId.topLevel(StandardNames.FqNames.unit.toSafe());
                Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                return new KClassValue(classId, 0);
            }
            if (i > 0) {
                ClassId classId2 = ClassId.topLevel(type.getArrayTypeFqName());
                Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
                return new KClassValue(classId2, i - 1);
            }
            ClassId classId3 = ClassId.topLevel(type.getTypeFqName());
            Intrinsics.checkNotNullExpressionValue(classId3, "topLevel(...)");
            return new KClassValue(classId3, i);
        }
        if (!(javaType instanceof JavaClassifierType)) {
            return null;
        }
        FqName fqName = new FqName(((JavaClassifierType) javaType).getClassifierQualifiedName());
        ClassId classIdMapJavaToKotlin = JavaToKotlinClassMap.INSTANCE.mapJavaToKotlin(fqName);
        if (classIdMapJavaToKotlin == null) {
            classIdMapJavaToKotlin = ClassId.topLevel(fqName);
            Intrinsics.checkNotNullExpressionValue(classIdMapJavaToKotlin, "topLevel(...)");
        }
        return new KClassValue(classIdMapJavaToKotlin, i);
    }
}
