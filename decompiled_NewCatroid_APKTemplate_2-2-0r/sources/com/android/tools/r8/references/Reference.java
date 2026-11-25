package com.android.tools.r8.references;

import androidx.exifinterface.media.ExifInterface;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.utils.C3970u0;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import net.bytebuddy.description.method.MethodDescription;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Reference {
    static final /* synthetic */ boolean a = true;
    public static PrimitiveReference BOOL = PrimitiveReference.a;
    public static PrimitiveReference BYTE = PrimitiveReference.b;
    public static PrimitiveReference CHAR = PrimitiveReference.c;
    public static PrimitiveReference SHORT = PrimitiveReference.d;
    public static PrimitiveReference INT = PrimitiveReference.e;
    public static PrimitiveReference FLOAT = PrimitiveReference.f;
    public static PrimitiveReference LONG = PrimitiveReference.g;
    public static PrimitiveReference DOUBLE = PrimitiveReference.h;

    public static ArrayReference array(TypeReference typeReference, int i) {
        return ArrayReference.a(typeReference, i);
    }

    public static ArrayReference arrayFromDescriptor(String str) {
        return ArrayReference.a(str);
    }

    public static MethodReference classConstructor(ClassReference classReference) {
        return method(classReference, MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME, Collections.emptyList(), null);
    }

    public static ClassReference classFromBinaryName(String str) {
        return classFromDescriptor(C3970u0.l(str));
    }

    public static ClassReference classFromClass(Class<?> cls) {
        return classFromTypeName(cls.getTypeName());
    }

    public static ClassReference classFromDescriptor(String str) {
        return ClassReference.a(str);
    }

    public static ClassReference classFromTypeName(String str) {
        return classFromDescriptor(C3970u0.H(str));
    }

    public static FieldReference field(ClassReference classReference, String str, TypeReference typeReference) {
        return new FieldReference(classReference, str, typeReference);
    }

    public static FieldReference fieldFromField(Field field) {
        Class<?> declaringClass = field.getDeclaringClass();
        return field(classFromClass(declaringClass), field.getName(), typeFromDescriptor(C3970u0.H(field.getType().getTypeName())));
    }

    public static MethodReference method(ClassReference classReference, String str, List<TypeReference> list, TypeReference typeReference) {
        return new MethodReference(classReference, str, AbstractC1639am.a(list), typeReference);
    }

    public static MethodReference methodFromDescriptor(String str, String str2, String str3) {
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (String str4 : C3970u0.e(str3)) {
            c1507XlG.a(typeFromDescriptor(str4));
        }
        String strU = C3970u0.u(str3);
        return method(classFromDescriptor(str), str2, c1507XlG.a(), strU.equals(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) ? null : typeFromDescriptor(strU));
    }

    public static MethodReference methodFromMethod(Executable executable) {
        if (executable instanceof Constructor) {
            return methodFromMethod((Constructor<?>) executable);
        }
        if (a || (executable instanceof Method)) {
            return methodFromMethod((Method) executable);
        }
        throw new AssertionError();
    }

    public static PackageReference packageFromPackage(Package r1) {
        return new PackageReference(r1.getName());
    }

    public static PackageReference packageFromString(String str) {
        return new PackageReference(str);
    }

    public static PrimitiveReference primitiveFromDescriptor(String str) {
        return PrimitiveReference.a(str);
    }

    public static TypeReference returnTypeFromDescriptor(String str) {
        if (str.equals(ExifInterface.GPS_MEASUREMENT_INTERRUPTED)) {
            return null;
        }
        return typeFromDescriptor(str);
    }

    public static TypeReference returnTypeFromTypeName(String str) {
        if (str.equals(PsiKeyword.VOID)) {
            return null;
        }
        return typeFromTypeName(str);
    }

    public static TypeReference typeFromDescriptor(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt != 'L') {
            return cCharAt != '[' ? primitiveFromDescriptor(str) : arrayFromDescriptor(str);
        }
        return classFromDescriptor(str);
    }

    public static TypeReference typeFromTypeName(String str) {
        return typeFromDescriptor(C3970u0.H(str));
    }

    public static MethodReference methodFromMethod(Method method) {
        String name2 = method.getName();
        Class<?> declaringClass = method.getDeclaringClass();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Class<?> returnType = method.getReturnType();
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (Class<?> cls : parameterTypes) {
            c1507XlG.a(typeFromDescriptor(C3970u0.H(cls.getTypeName())));
        }
        return method(classFromClass(declaringClass), name2, c1507XlG.a(), returnType == Void.TYPE ? null : typeFromDescriptor(C3970u0.H(returnType.getTypeName())));
    }

    public static MethodReference methodFromDescriptor(ClassReference classReference, String str, String str2) {
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (String str3 : C3970u0.e(str2)) {
            c1507XlG.a(typeFromDescriptor(str3));
        }
        String strU = C3970u0.u(str2);
        return method(classReference, str, c1507XlG.a(), strU.equals(ExifInterface.GPS_MEASUREMENT_INTERRUPTED) ? null : typeFromDescriptor(strU));
    }

    public static MethodReference methodFromMethod(Constructor<?> constructor) {
        Class<?> declaringClass = constructor.getDeclaringClass();
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (Class<?> cls : parameterTypes) {
            c1507XlG.a(typeFromDescriptor(C3970u0.H(cls.getTypeName())));
        }
        return method(classFromClass(declaringClass), "<init>", c1507XlG.a(), null);
    }
}
