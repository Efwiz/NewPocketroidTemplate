package org.jetbrains.kotlin.types;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: ConstantValueKind.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0011\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019B\u000f\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0004H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0011\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind;", ExifInterface.GPS_DIRECTION_TRUE, "", "asString", "", "(Ljava/lang/String;)V", "getAsString", "()Ljava/lang/String;", "toString", "Boolean", "Byte", "Char", "Double", "Error", "Float", "Int", "IntegerLiteral", "Long", "Null", "Short", CommonClassNames.JAVA_LANG_STRING_SHORT, "UnsignedByte", "UnsignedInt", "UnsignedIntegerLiteral", "UnsignedLong", "UnsignedShort", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Boolean;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Byte;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Char;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Double;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Error;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Float;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Int;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$IntegerLiteral;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Long;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Null;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$Short;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$String;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedByte;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedInt;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedIntegerLiteral;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedLong;", "Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedShort;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstantValueKind<T> {
    private final java.lang.String asString;

    public /* synthetic */ ConstantValueKind(java.lang.String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Null;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Null extends ConstantValueKind {
        public static final Null INSTANCE = new Null();

        private Null() {
            super("Null", null);
        }
    }

    private ConstantValueKind(java.lang.String str) {
        this.asString = str;
    }

    public final java.lang.String getAsString() {
        return this.asString;
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Boolean;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Boolean extends ConstantValueKind<java.lang.Boolean> {
        public static final Boolean INSTANCE = new Boolean();

        private Boolean() {
            super("Boolean", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Char;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Char extends ConstantValueKind<Character> {
        public static final Char INSTANCE = new Char();

        private Char() {
            super("Char", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Byte;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Byte extends ConstantValueKind<java.lang.Byte> {
        public static final Byte INSTANCE = new Byte();

        private Byte() {
            super("Byte", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedByte;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnsignedByte extends ConstantValueKind<java.lang.Byte> {
        public static final UnsignedByte INSTANCE = new UnsignedByte();

        private UnsignedByte() {
            super("UByte", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\n\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Short;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Short extends ConstantValueKind<java.lang.Short> {
        public static final Short INSTANCE = new Short();

        private Short() {
            super("Short", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\n\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedShort;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnsignedShort extends ConstantValueKind<java.lang.Short> {
        public static final UnsignedShort INSTANCE = new UnsignedShort();

        private UnsignedShort() {
            super("UShort", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Int;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Int extends ConstantValueKind<Integer> {
        public static final Int INSTANCE = new Int();

        private Int() {
            super("Int", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedInt;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnsignedInt extends ConstantValueKind<Integer> {
        public static final UnsignedInt INSTANCE = new UnsignedInt();

        private UnsignedInt() {
            super("UInt", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Long;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Long extends ConstantValueKind<java.lang.Long> {
        public static final Long INSTANCE = new Long();

        private Long() {
            super("Long", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedLong;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnsignedLong extends ConstantValueKind<java.lang.Long> {
        public static final UnsignedLong INSTANCE = new UnsignedLong();

        private UnsignedLong() {
            super("ULong", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$String;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class String extends ConstantValueKind<java.lang.String> {
        public static final String INSTANCE = new String();

        private String() {
            super(CommonClassNames.JAVA_LANG_STRING_SHORT, null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Float;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Float extends ConstantValueKind<java.lang.Float> {
        public static final Float INSTANCE = new Float();

        private Float() {
            super("Float", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Double;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Double extends ConstantValueKind<java.lang.Double> {
        public static final Double INSTANCE = new Double();

        private Double() {
            super("Double", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$Error;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Error extends ConstantValueKind {
        public static final Error INSTANCE = new Error();

        private Error() {
            super("Error", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$IntegerLiteral;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IntegerLiteral extends ConstantValueKind<java.lang.Long> {
        public static final IntegerLiteral INSTANCE = new IntegerLiteral();

        private IntegerLiteral() {
            super("IntegerLiteral", null);
        }
    }

    /* JADX INFO: compiled from: ConstantValueKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/types/ConstantValueKind$UnsignedIntegerLiteral;", "Lorg/jetbrains/kotlin/types/ConstantValueKind;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnsignedIntegerLiteral extends ConstantValueKind<java.lang.Long> {
        public static final UnsignedIntegerLiteral INSTANCE = new UnsignedIntegerLiteral();

        private UnsignedIntegerLiteral() {
            super("UnsignedIntegerLiteral", null);
        }
    }

    public java.lang.String toString() {
        return this.asString;
    }
}
