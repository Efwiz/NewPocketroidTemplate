package org.jetbrains.kotlin.load.kotlin;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: methodSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016J\u0010\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0002H\u0016R\u0014\u0010\u0004\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactoryImpl;", "Lorg/jetbrains/kotlin/load/kotlin/JvmTypeFactory;", "Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "()V", "javaLangClassType", "getJavaLangClassType", "()Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "boxType", "possiblyPrimitiveType", "createFromString", "representation", "", "createObjectType", "Lorg/jetbrains/kotlin/load/kotlin/JvmType$Object;", "internalName", "createPrimitiveType", "primitiveType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "toString", ModuleXmlParser.TYPE, "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class JvmTypeFactoryImpl implements JvmTypeFactory<JvmType> {
    public static final JvmTypeFactoryImpl INSTANCE = new JvmTypeFactoryImpl();

    /* JADX INFO: compiled from: methodSignatureMapping.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[PrimitiveType.values().length];
            try {
                iArr[PrimitiveType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[PrimitiveType.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[PrimitiveType.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[PrimitiveType.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[PrimitiveType.INT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[PrimitiveType.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[PrimitiveType.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[PrimitiveType.DOUBLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    private JvmTypeFactoryImpl() {
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public JvmType boxType(JvmType possiblyPrimitiveType) {
        Intrinsics.checkNotNullParameter(possiblyPrimitiveType, "possiblyPrimitiveType");
        if (!(possiblyPrimitiveType instanceof JvmType.Primitive)) {
            return possiblyPrimitiveType;
        }
        JvmType.Primitive primitive = (JvmType.Primitive) possiblyPrimitiveType;
        if (primitive.getJvmPrimitiveType() == null) {
            return possiblyPrimitiveType;
        }
        String internalName = JvmClassName.byFqNameWithoutInnerClasses(primitive.getJvmPrimitiveType().getWrapperFqName()).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return createObjectType(internalName);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public JvmType createFromString(String representation) {
        JvmPrimitiveType jvmPrimitiveType;
        Intrinsics.checkNotNullParameter(representation, "representation");
        String str = representation;
        str.length();
        char cCharAt = representation.charAt(0);
        JvmPrimitiveType[] jvmPrimitiveTypeArrValues = JvmPrimitiveType.values();
        int length = jvmPrimitiveTypeArrValues.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                jvmPrimitiveType = null;
                break;
            }
            jvmPrimitiveType = jvmPrimitiveTypeArrValues[i];
            if (jvmPrimitiveType.getDesc().charAt(0) == cCharAt) {
                break;
            }
            i++;
        }
        if (jvmPrimitiveType != null) {
            return new JvmType.Primitive(jvmPrimitiveType);
        }
        if (cCharAt == 'V') {
            return new JvmType.Primitive(null);
        }
        if (cCharAt == '[') {
            String strSubstring = representation.substring(1);
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            return new JvmType.Array(createFromString(strSubstring));
        }
        if (cCharAt == 'L') {
            StringsKt.endsWith$default((CharSequence) str, ';', false, 2, (Object) null);
        }
        String strSubstring2 = representation.substring(1, representation.length() - 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        return new JvmType.Object(strSubstring2);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public JvmType createPrimitiveType(PrimitiveType primitiveType) {
        Intrinsics.checkNotNullParameter(primitiveType, "primitiveType");
        switch (WhenMappings.$EnumSwitchMapping$0[primitiveType.ordinal()]) {
            case 1:
                return JvmType.INSTANCE.getBOOLEAN$descriptors_jvm();
            case 2:
                return JvmType.INSTANCE.getCHAR$descriptors_jvm();
            case 3:
                return JvmType.INSTANCE.getBYTE$descriptors_jvm();
            case 4:
                return JvmType.INSTANCE.getSHORT$descriptors_jvm();
            case 5:
                return JvmType.INSTANCE.getINT$descriptors_jvm();
            case 6:
                return JvmType.INSTANCE.getFLOAT$descriptors_jvm();
            case 7:
                return JvmType.INSTANCE.getLONG$descriptors_jvm();
            case 8:
                return JvmType.INSTANCE.getDOUBLE$descriptors_jvm();
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public JvmType createObjectType(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        return new JvmType.Object(internalName);
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public String toString(JvmType type) {
        String desc;
        Intrinsics.checkNotNullParameter(type, "type");
        if (type instanceof JvmType.Array) {
            return "[" + toString(((JvmType.Array) type).getElementType());
        }
        if (type instanceof JvmType.Primitive) {
            JvmPrimitiveType jvmPrimitiveType = ((JvmType.Primitive) type).getJvmPrimitiveType();
            return (jvmPrimitiveType == null || (desc = jvmPrimitiveType.getDesc()) == null) ? ExifInterface.GPS_MEASUREMENT_INTERRUPTED : desc;
        }
        if (type instanceof JvmType.Object) {
            return "L" + ((JvmType.Object) type).getInternalName() + ';';
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // org.jetbrains.kotlin.load.kotlin.JvmTypeFactory
    public JvmType getJavaLangClassType() {
        return createObjectType(TypeProxy.SilentConstruction.Appender.JAVA_LANG_CLASS_INTERNAL_NAME);
    }
}
