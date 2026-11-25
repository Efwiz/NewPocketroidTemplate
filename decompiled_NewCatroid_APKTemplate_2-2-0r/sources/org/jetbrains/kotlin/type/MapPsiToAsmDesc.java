package org.jetbrains.kotlin.type;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: MapPsiToAsmDesc.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007H\u0002J\u000e\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u000f\u001a\n \u0010*\u0004\u0018\u00010\u00070\u00072\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u000e\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u00020\u0007H\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/type/MapPsiToAsmDesc;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "classDesc", "", "psiClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "error", CrashHianalyticsData.MESSAGE, "methodDesc", "psiMethod", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "primitive", JvmProtoBufUtil.PLATFORM_TYPE_ID, "asmType", "Lorg/jetbrains/org/objectweb/asm/Type;", "typeDesc", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "unknownSignature", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MapPsiToAsmDesc {
    public static final MapPsiToAsmDesc INSTANCE;
    private static final Logger LOG;

    private MapPsiToAsmDesc() {
    }

    public final String typeDesc(PsiType type) {
        String strTypeDesc;
        Intrinsics.checkNotNullParameter(type, "type");
        if (Intrinsics.areEqual(type, PsiType.VOID)) {
            Type VOID_TYPE = Type.VOID_TYPE;
            Intrinsics.checkNotNullExpressionValue(VOID_TYPE, "VOID_TYPE");
            String strPrimitive = primitive(VOID_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive, "primitive(...)");
            return strPrimitive;
        }
        if (Intrinsics.areEqual(type, PsiType.BOOLEAN)) {
            Type BOOLEAN_TYPE = Type.BOOLEAN_TYPE;
            Intrinsics.checkNotNullExpressionValue(BOOLEAN_TYPE, "BOOLEAN_TYPE");
            String strPrimitive2 = primitive(BOOLEAN_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive2, "primitive(...)");
            return strPrimitive2;
        }
        if (Intrinsics.areEqual(type, PsiType.CHAR)) {
            Type CHAR_TYPE = Type.CHAR_TYPE;
            Intrinsics.checkNotNullExpressionValue(CHAR_TYPE, "CHAR_TYPE");
            String strPrimitive3 = primitive(CHAR_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive3, "primitive(...)");
            return strPrimitive3;
        }
        if (Intrinsics.areEqual(type, PsiType.INT)) {
            Type INT_TYPE = Type.INT_TYPE;
            Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
            String strPrimitive4 = primitive(INT_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive4, "primitive(...)");
            return strPrimitive4;
        }
        if (Intrinsics.areEqual(type, PsiType.BYTE)) {
            Type BYTE_TYPE = Type.BYTE_TYPE;
            Intrinsics.checkNotNullExpressionValue(BYTE_TYPE, "BYTE_TYPE");
            String strPrimitive5 = primitive(BYTE_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive5, "primitive(...)");
            return strPrimitive5;
        }
        if (Intrinsics.areEqual(type, PsiType.SHORT)) {
            Type SHORT_TYPE = Type.SHORT_TYPE;
            Intrinsics.checkNotNullExpressionValue(SHORT_TYPE, "SHORT_TYPE");
            String strPrimitive6 = primitive(SHORT_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive6, "primitive(...)");
            return strPrimitive6;
        }
        if (Intrinsics.areEqual(type, PsiType.LONG)) {
            Type LONG_TYPE = Type.LONG_TYPE;
            Intrinsics.checkNotNullExpressionValue(LONG_TYPE, "LONG_TYPE");
            String strPrimitive7 = primitive(LONG_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive7, "primitive(...)");
            return strPrimitive7;
        }
        if (Intrinsics.areEqual(type, PsiType.FLOAT)) {
            Type FLOAT_TYPE = Type.FLOAT_TYPE;
            Intrinsics.checkNotNullExpressionValue(FLOAT_TYPE, "FLOAT_TYPE");
            String strPrimitive8 = primitive(FLOAT_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive8, "primitive(...)");
            return strPrimitive8;
        }
        if (Intrinsics.areEqual(type, PsiType.DOUBLE)) {
            Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
            Intrinsics.checkNotNullExpressionValue(DOUBLE_TYPE, "DOUBLE_TYPE");
            String strPrimitive9 = primitive(DOUBLE_TYPE);
            Intrinsics.checkNotNullExpressionValue(strPrimitive9, "primitive(...)");
            return strPrimitive9;
        }
        if (type instanceof PsiArrayType) {
            StringBuilder sb = new StringBuilder("[");
            PsiType componentType = ((PsiArrayType) type).getComponentType();
            Intrinsics.checkNotNullExpressionValue(componentType, "getComponentType(...)");
            return sb.append(typeDesc(componentType)).toString();
        }
        if (type instanceof PsiClassType) {
            PsiClass psiClassResolve = ((PsiClassType) type).resolve();
            if (!(psiClassResolve instanceof PsiTypeParameter)) {
                if (psiClassResolve != null) {
                    return classDesc(psiClassResolve);
                }
                return unknownSignature();
            }
            PsiClassType[] superTypes = ((PsiTypeParameter) psiClassResolve).getSuperTypes();
            Intrinsics.checkNotNullExpressionValue(superTypes, "getSuperTypes(...)");
            PsiClassType psiClassType = (PsiClassType) ArraysKt.firstOrNull(superTypes);
            return (psiClassType == null || (strTypeDesc = INSTANCE.typeDesc(psiClassType)) == null) ? TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR : strTypeDesc;
        }
        return error("Unexpected type " + type + " of class " + type.getClass());
    }

    private final String classDesc(PsiClass psiClass) {
        StringBuilder sb = new StringBuilder("L");
        List listReversed = CollectionsKt.reversed(SequencesKt.toList(SequencesKt.generateSequence(psiClass, new Function1<PsiClass, PsiClass>() { // from class: org.jetbrains.kotlin.type.MapPsiToAsmDesc$classDesc$1$classes$1
            @Override // kotlin.jvm.functions.Function1
            public final PsiClass invoke(PsiClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.mo6092getContainingClass();
            }
        })));
        String qualifiedName = ((PsiClass) CollectionsKt.first(listReversed)).getQualifiedName();
        Intrinsics.checkNotNull(qualifiedName);
        sb.append(StringsKt.replace$default(qualifiedName, ".", "/", false, 4, (Object) null));
        for (PsiClass psiClass2 : CollectionsKt.drop(listReversed, 1)) {
            sb.append("$");
            String name2 = psiClass2.getMyName();
            Intrinsics.checkNotNull(name2);
            sb.append(name2);
        }
        sb.append(";");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX WARN: Code duplicated, block: B:9:0x004e  */
    public final String methodDesc(PsiMethod psiMethod) {
        String strTypeDesc;
        Intrinsics.checkNotNullParameter(psiMethod, "psiMethod");
        StringBuilder sb = new StringBuilder("(");
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
        for (PsiParameter psiParameter : parameters) {
            MapPsiToAsmDesc mapPsiToAsmDesc = INSTANCE;
            PsiType type = psiParameter.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            sb.append(mapPsiToAsmDesc.typeDesc(type));
        }
        sb.append(Constant.AFTER_QUTO);
        PsiType returnType = psiMethod.getReturnType();
        if (returnType != null) {
            MapPsiToAsmDesc mapPsiToAsmDesc2 = INSTANCE;
            Intrinsics.checkNotNull(returnType);
            strTypeDesc = mapPsiToAsmDesc2.typeDesc(returnType);
            if (strTypeDesc == null) {
                strTypeDesc = ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
            }
        } else {
            strTypeDesc = ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
        }
        sb.append(strTypeDesc);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final String unknownSignature() {
        return "";
    }

    private final String error(String message) {
        LOG.error(message);
        return unknownSignature();
    }

    private final String primitive(Type asmType) {
        return asmType.getDescriptor();
    }

    static {
        MapPsiToAsmDesc mapPsiToAsmDesc = new MapPsiToAsmDesc();
        INSTANCE = mapPsiToAsmDesc;
        Logger logger = Logger.getInstance(mapPsiToAsmDesc.getClass());
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
