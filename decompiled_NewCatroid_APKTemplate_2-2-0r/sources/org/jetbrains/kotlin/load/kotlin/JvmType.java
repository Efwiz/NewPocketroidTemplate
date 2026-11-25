package org.jetbrains.kotlin.load.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.resolve.jvm.JvmPrimitiveType;

/* JADX INFO: compiled from: methodSignatureMapping.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u00062\u00020\u0001:\u0004\u0005\u0006\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016\u0082\u0001\u0003\t\n\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "", "()V", "toString", "", "Array", "Companion", CommonClassNames.JAVA_LANG_OBJECT_SHORT, "Primitive", "Lorg/jetbrains/kotlin/load/kotlin/JvmType$Array;", "Lorg/jetbrains/kotlin/load/kotlin/JvmType$Object;", "Lorg/jetbrains/kotlin/load/kotlin/JvmType$Primitive;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JvmType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Primitive BOOLEAN = new Primitive(JvmPrimitiveType.BOOLEAN);
    private static final Primitive CHAR = new Primitive(JvmPrimitiveType.CHAR);
    private static final Primitive BYTE = new Primitive(JvmPrimitiveType.BYTE);
    private static final Primitive SHORT = new Primitive(JvmPrimitiveType.SHORT);
    private static final Primitive INT = new Primitive(JvmPrimitiveType.INT);
    private static final Primitive FLOAT = new Primitive(JvmPrimitiveType.FLOAT);
    private static final Primitive LONG = new Primitive(JvmPrimitiveType.LONG);
    private static final Primitive DOUBLE = new Primitive(JvmPrimitiveType.DOUBLE);

    public /* synthetic */ JvmType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private JvmType() {
    }

    /* JADX INFO: compiled from: methodSignatureMapping.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmType$Primitive;", "Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "jvmPrimitiveType", "Lorg/jetbrains/kotlin/resolve/jvm/JvmPrimitiveType;", "(Lorg/jetbrains/kotlin/resolve/jvm/JvmPrimitiveType;)V", "getJvmPrimitiveType", "()Lorg/jetbrains/kotlin/resolve/jvm/JvmPrimitiveType;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Primitive extends JvmType {
        private final JvmPrimitiveType jvmPrimitiveType;

        public Primitive(JvmPrimitiveType jvmPrimitiveType) {
            super(null);
            this.jvmPrimitiveType = jvmPrimitiveType;
        }

        public final JvmPrimitiveType getJvmPrimitiveType() {
            return this.jvmPrimitiveType;
        }
    }

    /* JADX INFO: compiled from: methodSignatureMapping.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmType$Object;", "Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "internalName", "", "(Ljava/lang/String;)V", "getInternalName", "()Ljava/lang/String;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Object extends JvmType {
        private final String internalName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Object(String internalName) {
            super(null);
            Intrinsics.checkNotNullParameter(internalName, "internalName");
            this.internalName = internalName;
        }

        public final String getInternalName() {
            return this.internalName;
        }
    }

    /* JADX INFO: compiled from: methodSignatureMapping.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmType$Array;", "Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "elementType", "(Lorg/jetbrains/kotlin/load/kotlin/JvmType;)V", "getElementType", "()Lorg/jetbrains/kotlin/load/kotlin/JvmType;", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Array extends JvmType {
        private final JvmType elementType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Array(JvmType elementType) {
            super(null);
            Intrinsics.checkNotNullParameter(elementType, "elementType");
            this.elementType = elementType;
        }

        public final JvmType getElementType() {
            return this.elementType;
        }
    }

    public String toString() {
        return JvmTypeFactoryImpl.INSTANCE.toString(this);
    }

    /* JADX INFO: compiled from: methodSignatureMapping.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0014\u0010\u000f\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0014\u0010\u0011\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0014\u0010\u0013\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/load/kotlin/JvmType$Companion;", "", "()V", "BOOLEAN", "Lorg/jetbrains/kotlin/load/kotlin/JvmType$Primitive;", "getBOOLEAN$descriptors_jvm", "()Lorg/jetbrains/kotlin/load/kotlin/JvmType$Primitive;", "BYTE", "getBYTE$descriptors_jvm", "CHAR", "getCHAR$descriptors_jvm", "DOUBLE", "getDOUBLE$descriptors_jvm", "FLOAT", "getFLOAT$descriptors_jvm", "INT", "getINT$descriptors_jvm", "LONG", "getLONG$descriptors_jvm", "SHORT", "getSHORT$descriptors_jvm", "descriptors.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Primitive getBOOLEAN$descriptors_jvm() {
            return JvmType.BOOLEAN;
        }

        public final Primitive getCHAR$descriptors_jvm() {
            return JvmType.CHAR;
        }

        public final Primitive getBYTE$descriptors_jvm() {
            return JvmType.BYTE;
        }

        public final Primitive getSHORT$descriptors_jvm() {
            return JvmType.SHORT;
        }

        public final Primitive getINT$descriptors_jvm() {
            return JvmType.INT;
        }

        public final Primitive getFLOAT$descriptors_jvm() {
            return JvmType.FLOAT;
        }

        public final Primitive getLONG$descriptors_jvm() {
            return JvmType.LONG;
        }

        public final Primitive getDOUBLE$descriptors_jvm() {
            return JvmType.DOUBLE;
        }
    }
}
