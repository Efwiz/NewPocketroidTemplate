package kotlin.jvm;

import androidx.exifinterface.media.ExifInterface;
import java.lang.annotation.Annotation;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/jvm/JvmClassMappingKt.class
  classes9.dex
 */
/* JADX INFO: compiled from: JvmClassMapping.kt */
/* JADX INFO: loaded from: classes9.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0011\n\u0002\b\u0002\u001a\u001f\u0010\u001f\u001a\u00020 \"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0014*\u0006\u0012\u0002\b\u00030!¢\u0006\u0002\u0010\"\"'\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u0002H\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\";\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u000e\b\u0000\u0010\b*\b\u0012\u0004\u0012\u0002H\b0\t*\b\u0012\u0004\u0012\u0002H\b0\t8Æ\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\"-\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00018G¢\u0006\f\u0012\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"&\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\u0002H\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0015\";\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018Ç\u0002X\u0087\u0004¢\u0006\f\u0012\u0004\b\u0016\u0010\u0010\u001a\u0004\b\u0017\u0010\u0012\"+\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0012\"-\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u0007\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0012\"+\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0014*\b\u0012\u0004\u0012\u0002H\u00020\u00078G¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e¨\u0006#"}, d2 = {"annotationClass", "Lkotlin/reflect/KClass;", ExifInterface.GPS_DIRECTION_TRUE, "", "getAnnotationClass", "(Ljava/lang/annotation/Annotation;)Lkotlin/reflect/KClass;", "declaringJavaClass", "Ljava/lang/Class;", ExifInterface.LONGITUDE_EAST, "", "getDeclaringJavaClass$annotations", "(Ljava/lang/Enum;)V", "getDeclaringJavaClass", "(Ljava/lang/Enum;)Ljava/lang/Class;", SuffixConstants.EXTENSION_java, "getJavaClass$annotations", "(Lkotlin/reflect/KClass;)V", "getJavaClass", "(Lkotlin/reflect/KClass;)Ljava/lang/Class;", "javaClass", "", "(Ljava/lang/Object;)Ljava/lang/Class;", "getRuntimeClassOfKClassInstance$annotations", "getRuntimeClassOfKClassInstance", "javaObjectType", "getJavaObjectType", "javaPrimitiveType", "getJavaPrimitiveType", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "getKotlinClass", "(Ljava/lang/Class;)Lkotlin/reflect/KClass;", "isArrayOf", "", "", "([Ljava/lang/Object;)Z", PathUtil.KOTLIN_JAVA_STDLIB_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmClassMappingKt {
    public static /* synthetic */ void getDeclaringJavaClass$annotations(Enum r0) {
    }

    public static /* synthetic */ void getJavaClass$annotations(KClass kClass) {
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use 'java' property to get Java class corresponding to this Kotlin class or cast this instance to Any if you really want to get the runtime Java class of this implementation of KClass.", replaceWith = @ReplaceWith(expression = "(this as Any).javaClass", imports = {}))
    public static /* synthetic */ void getRuntimeClassOfKClassInstance$annotations(KClass kClass) {
    }

    public static final <T> Class<T> getJavaClass(KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<T> cls = (Class<T>) ((ClassBasedDeclarationContainer) kClass).getJClass();
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-java>>");
        return cls;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final <T> Class<T> getJavaPrimitiveType(KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<T> cls = (Class<T>) ((ClassBasedDeclarationContainer) kClass).getJClass();
        if (cls.isPrimitive()) {
            Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaPrimitiveType>>");
            return cls;
        }
        String name2 = cls.getName();
        if (name2 != null) {
            switch (name2.hashCode()) {
                case -2056817302:
                    if (name2.equals(CommonClassNames.JAVA_LANG_INTEGER)) {
                        return Integer.TYPE;
                    }
                    break;
                case -527879800:
                    if (name2.equals(CommonClassNames.JAVA_LANG_FLOAT)) {
                        return Float.TYPE;
                    }
                    break;
                case -515992664:
                    if (name2.equals(CommonClassNames.JAVA_LANG_SHORT)) {
                        return Short.TYPE;
                    }
                    break;
                case 155276373:
                    if (name2.equals(CommonClassNames.JAVA_LANG_CHARACTER)) {
                        return Character.TYPE;
                    }
                    break;
                case 344809556:
                    if (name2.equals(CommonClassNames.JAVA_LANG_BOOLEAN)) {
                        return Boolean.TYPE;
                    }
                    break;
                case 398507100:
                    if (name2.equals(CommonClassNames.JAVA_LANG_BYTE)) {
                        return Byte.TYPE;
                    }
                    break;
                case 398795216:
                    if (name2.equals(CommonClassNames.JAVA_LANG_LONG)) {
                        return Long.TYPE;
                    }
                    break;
                case 399092968:
                    if (name2.equals(CommonClassNames.JAVA_LANG_VOID)) {
                        return Void.TYPE;
                    }
                    break;
                case 761287205:
                    if (name2.equals(CommonClassNames.JAVA_LANG_DOUBLE)) {
                        return Double.TYPE;
                    }
                    break;
            }
        }
        return null;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static final <T> Class<T> getJavaObjectType(KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<T> cls = (Class<T>) ((ClassBasedDeclarationContainer) kClass).getJClass();
        if (!cls.isPrimitive()) {
            Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
            return cls;
        }
        String name2 = cls.getName();
        if (name2 != null) {
            switch (name2.hashCode()) {
                case -1325958191:
                    if (name2.equals("double")) {
                        cls = (Class<T>) Double.class;
                    }
                    break;
                case 104431:
                    if (name2.equals("int")) {
                        cls = (Class<T>) Integer.class;
                    }
                    break;
                case 3039496:
                    if (name2.equals("byte")) {
                        cls = (Class<T>) Byte.class;
                    }
                    break;
                case 3052374:
                    if (name2.equals(PsiKeyword.CHAR)) {
                        cls = (Class<T>) Character.class;
                    }
                    break;
                case 3327612:
                    if (name2.equals("long")) {
                        cls = (Class<T>) Long.class;
                    }
                    break;
                case 3625364:
                    if (name2.equals(PsiKeyword.VOID)) {
                        cls = (Class<T>) Void.class;
                    }
                    break;
                case 64711720:
                    if (name2.equals("boolean")) {
                        cls = (Class<T>) Boolean.class;
                    }
                    break;
                case 97526364:
                    if (name2.equals("float")) {
                        cls = (Class<T>) Float.class;
                    }
                    break;
                case 109413500:
                    if (name2.equals("short")) {
                        cls = (Class<T>) Short.class;
                    }
                    break;
            }
        }
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaObjectType>>");
        return cls;
    }

    public static final <T> KClass<T> getKotlinClass(Class<T> cls) {
        Intrinsics.checkNotNullParameter(cls, "<this>");
        return Reflection.getOrCreateKotlinClass(cls);
    }

    public static final <T> Class<T> getJavaClass(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Class<T> cls = (Class<T>) t.getClass();
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>");
        return cls;
    }

    public static final <T> Class<KClass<T>> getRuntimeClassOfKClassInstance(KClass<T> kClass) {
        Intrinsics.checkNotNullParameter(kClass, "<this>");
        Class<KClass<T>> cls = (Class<KClass<T>>) kClass.getClass();
        Intrinsics.checkNotNull(cls, "null cannot be cast to non-null type java.lang.Class<kotlin.reflect.KClass<T of kotlin.jvm.JvmClassMappingKt.<get-javaClass>>>");
        return cls;
    }

    public static final /* synthetic */ boolean isArrayOf(Object[] objArr) {
        Intrinsics.checkNotNullParameter(objArr, "<this>");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return Object.class.isAssignableFrom(objArr.getClass().getComponentType());
    }

    public static final <T extends Annotation> KClass<? extends T> getAnnotationClass(T t) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Class<? extends Annotation> clsAnnotationType = t.annotationType();
        Intrinsics.checkNotNullExpressionValue(clsAnnotationType, "annotationType(...)");
        KClass<? extends T> kotlinClass = getKotlinClass(clsAnnotationType);
        Intrinsics.checkNotNull(kotlinClass, "null cannot be cast to non-null type kotlin.reflect.KClass<out T of kotlin.jvm.JvmClassMappingKt.<get-annotationClass>>");
        return kotlinClass;
    }

    private static final <E extends Enum<E>> Class<E> getDeclaringJavaClass(Enum<E> r1) {
        Intrinsics.checkNotNullParameter(r1, "<this>");
        Class<E> declaringClass = r1.getDeclaringClass();
        Intrinsics.checkNotNullExpressionValue(declaringClass, "getDeclaringClass(...)");
        return declaringClass;
    }
}
