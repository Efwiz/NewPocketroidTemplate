package org.jetbrains.kotlin.fir.java;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArrayLiteralBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirConstExpressionBuilderKt;
import org.jetbrains.kotlin.fir.expressions.builder.FirErrorExpressionBuilder;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveUtilsKt;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirResolvedTypeRefBuilder;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaNullabilityAnnotationSettingsKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner;
import org.jetbrains.kotlin.load.java.structure.JavaWildcardType;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.ConstantValueKind;

/* JADX INFO: compiled from: JavaUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000e\u001a.\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0011*\b\u0012\u0004\u0012\u0002H\u00110\u00122\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00110\u0016H\u0002\u001a\"\u0010\u0017\u001a\u0004\u0018\u00010\u0018*\u0004\u0018\u00010\u00192\u0006\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u001a\u001a\u00020\u001bH\u0000\u001a\"\u0010\u001c\u001a\u00020\u0018*\u0004\u0018\u00010\u00192\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0000\u001a\n\u0010\u001f\u001a\u00020\u001b*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\"\u0018\u0010\u0005\u001a\u00020\u0006*\u00020\t8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n¨\u0006 "}, d2 = {"classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getClassKind", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClass;)Lorg/jetbrains/kotlin/descriptors/ClassKind;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "(Lorg/jetbrains/kotlin/load/java/structure/JavaClass;)Lorg/jetbrains/kotlin/descriptors/Modality;", "Lorg/jetbrains/kotlin/load/java/structure/JavaModifierListOwner;", "(Lorg/jetbrains/kotlin/load/java/structure/JavaModifierListOwner;)Lorg/jetbrains/kotlin/descriptors/Modality;", "extractNullabilityAnnotationOnBoundedWildcard", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "wildcardType", "Lorg/jetbrains/kotlin/load/java/structure/JavaWildcardType;", "createArrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirArrayLiteral;", ExifInterface.GPS_DIRECTION_TRUE, "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "createConstantIfAny", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "", "unsigned", "", "createConstantOrError", "expectedTypeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "hasMetadataAnnotation", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaUtilsKt {
    public static final Modality getModality(JavaModifierListOwner javaModifierListOwner) {
        Intrinsics.checkNotNullParameter(javaModifierListOwner, "<this>");
        if (javaModifierListOwner.mo7260isAbstract()) {
            return Modality.ABSTRACT;
        }
        return javaModifierListOwner.mo7264isFinal() ? Modality.FINAL : Modality.OPEN;
    }

    public static final Modality getModality(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "<this>");
        if (javaClass.mo7261isAnnotationType() || javaClass.mo7263isEnum()) {
            return Modality.FINAL;
        }
        if (javaClass.isSealed()) {
            return Modality.SEALED;
        }
        if (javaClass.mo7260isAbstract()) {
            return Modality.ABSTRACT;
        }
        return javaClass.mo7264isFinal() ? Modality.FINAL : Modality.OPEN;
    }

    public static final ClassKind getClassKind(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "<this>");
        if (javaClass.mo7261isAnnotationType()) {
            return ClassKind.ANNOTATION_CLASS;
        }
        if (javaClass.mo7265isInterface()) {
            return ClassKind.INTERFACE;
        }
        return javaClass.mo7263isEnum() ? ClassKind.ENUM_CLASS : ClassKind.CLASS;
    }

    public static final boolean hasMetadataAnnotation(JavaClass javaClass) {
        Intrinsics.checkNotNullParameter(javaClass, "<this>");
        Collection<JavaAnnotation> annotations = javaClass.mo7252getAnnotations();
        if ((annotations instanceof Collection) && annotations.isEmpty()) {
            return false;
        }
        for (JavaAnnotation javaAnnotation : annotations) {
            FqName METADATA_FQ_NAME = JvmAnnotationNames.METADATA_FQ_NAME;
            Intrinsics.checkNotNullExpressionValue(METADATA_FQ_NAME, "METADATA_FQ_NAME");
            if (javaAnnotation.isResolvedTo(METADATA_FQ_NAME)) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ FirExpression createConstantOrError$default(Object obj, FirSession firSession, FirTypeRef firTypeRef, int i, Object obj2) {
        if ((i & 2) != 0) {
            firTypeRef = null;
        }
        return createConstantOrError(obj, firSession, firTypeRef);
    }

    public static final FirExpression createConstantOrError(Object obj, FirSession session, FirTypeRef firTypeRef) {
        Intrinsics.checkNotNullParameter(session, "session");
        ConeKotlinType coneTypeOrNull = firTypeRef != null ? FirTypeUtilsKt.getConeTypeOrNull(firTypeRef) : null;
        if ((obj instanceof Integer) && coneTypeOrNull != null) {
            if (ConeBuiltinTypeUtilsKt.isByte(coneTypeOrNull)) {
                obj = Byte.valueOf((byte) ((Number) obj).intValue());
            } else if (ConeBuiltinTypeUtilsKt.isShort(coneTypeOrNull)) {
                obj = Short.valueOf((short) ((Number) obj).intValue());
            } else if (ConeBuiltinTypeUtilsKt.isLong(coneTypeOrNull)) {
                obj = Long.valueOf(((Number) obj).intValue());
            }
        }
        FirExpression firExpressionCreateConstantIfAny$default = createConstantIfAny$default(obj, session, false, 2, null);
        if (firExpressionCreateConstantIfAny$default != null) {
            return firExpressionCreateConstantIfAny$default;
        }
        FirErrorExpressionBuilder firErrorExpressionBuilder = new FirErrorExpressionBuilder();
        firErrorExpressionBuilder.setDiagnostic(new ConeSimpleDiagnostic("Unknown value in JavaLiteralAnnotationArgument: " + firErrorExpressionBuilder, DiagnosticKind.Java));
        return firErrorExpressionBuilder.mo6809build();
    }

    public static /* synthetic */ FirExpression createConstantIfAny$default(Object obj, FirSession firSession, boolean z, int i, Object obj2) {
        if ((i & 2) != 0) {
            z = false;
        }
        return createConstantIfAny(obj, firSession, z);
    }

    public static final FirExpression createConstantIfAny(Object obj, FirSession session, boolean z) {
        Intrinsics.checkNotNullParameter(session, "session");
        if (obj instanceof Byte) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, z ? ConstantValueKind.UnsignedByte.INSTANCE : ConstantValueKind.Byte.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof Short) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, z ? ConstantValueKind.UnsignedShort.INSTANCE : ConstantValueKind.Short.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof Integer) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, z ? ConstantValueKind.UnsignedInt.INSTANCE : ConstantValueKind.Int.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof Long) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, z ? ConstantValueKind.UnsignedLong.INSTANCE : ConstantValueKind.Long.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof Character) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Char.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof Float) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Float.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof Double) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Double.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof Boolean) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Boolean.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof String) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.String.INSTANCE, obj, null, true, 8, null);
        }
        if (obj instanceof byte[]) {
            return createArrayLiteral(ArraysKt.toList((byte[]) obj), session, ConstantValueKind.Byte.INSTANCE);
        }
        if (obj instanceof short[]) {
            return createArrayLiteral(ArraysKt.toList((short[]) obj), session, ConstantValueKind.Short.INSTANCE);
        }
        if (obj instanceof int[]) {
            return createArrayLiteral(ArraysKt.toList((int[]) obj), session, ConstantValueKind.Int.INSTANCE);
        }
        if (obj instanceof long[]) {
            return createArrayLiteral(ArraysKt.toList((long[]) obj), session, ConstantValueKind.Long.INSTANCE);
        }
        if (obj instanceof char[]) {
            return createArrayLiteral(ArraysKt.toList((char[]) obj), session, ConstantValueKind.Char.INSTANCE);
        }
        if (obj instanceof float[]) {
            return createArrayLiteral(ArraysKt.toList((float[]) obj), session, ConstantValueKind.Float.INSTANCE);
        }
        if (obj instanceof double[]) {
            return createArrayLiteral(ArraysKt.toList((double[]) obj), session, ConstantValueKind.Double.INSTANCE);
        }
        if (obj instanceof boolean[]) {
            return createArrayLiteral(ArraysKt.toList((boolean[]) obj), session, ConstantValueKind.Boolean.INSTANCE);
        }
        if (obj == null) {
            return FirConstExpressionBuilderKt.buildConstExpression$default(null, ConstantValueKind.Null.INSTANCE, null, null, true, 8, null);
        }
        return null;
    }

    public static final JavaAnnotation extractNullabilityAnnotationOnBoundedWildcard(JavaWildcardType wildcardType) {
        Object obj;
        Intrinsics.checkNotNullParameter(wildcardType, "wildcardType");
        if (wildcardType.getBound() == null) {
            throw new IllegalArgumentException("Nullability annotations on unbounded wildcards aren't supported".toString());
        }
        Iterator<T> it = wildcardType.mo7252getAnnotations().iterator();
        loop0: while (true) {
            obj = null;
            if (!it.hasNext()) {
                break;
            }
            Object next = it.next();
            JavaAnnotation javaAnnotation = (JavaAnnotation) next;
            for (FqName fqName : JavaNullabilityAnnotationSettingsKt.getRXJAVA3_ANNOTATIONS()) {
                ClassId classId = javaAnnotation.getClassId();
                if (Intrinsics.areEqual(classId != null ? classId.asSingleFqName() : null, fqName)) {
                    obj = next;
                    break loop0;
                }
            }
        }
        return (JavaAnnotation) obj;
    }

    private static final <T> FirArrayLiteral createArrayLiteral(List<? extends T> list, FirSession firSession, ConstantValueKind<T> constantValueKind) {
        FirArrayLiteralBuilder firArrayLiteralBuilder = new FirArrayLiteralBuilder();
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        Iterator<? extends T> it = list.iterator();
        while (it.hasNext()) {
            firArgumentListBuilder.getArguments().add(createConstantOrError$default(it.next(), firSession, null, 2, null));
        }
        firArrayLiteralBuilder.setArgumentList(firArgumentListBuilder.build());
        FirResolvedTypeRefBuilder firResolvedTypeRefBuilder = new FirResolvedTypeRefBuilder();
        firResolvedTypeRefBuilder.setType(ArrayUtilsKt.createArrayType$default(BodyResolveUtilsKt.expectedConeType(constantValueKind, firSession), false, false, 3, null));
        firArrayLiteralBuilder.setTypeRef(firResolvedTypeRefBuilder.mo6809build());
        return firArrayLiteralBuilder.mo6809build();
    }
}
