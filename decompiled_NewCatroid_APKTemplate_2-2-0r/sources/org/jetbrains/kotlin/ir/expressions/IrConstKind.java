package org.jetbrains.kotlin.ir.expressions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: IrConstKind.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002:\n\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016B\u000f\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\b\u001a\u00020\u0004H\u0016J\u0017\u0010\t\u001a\u00028\u00002\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b¢\u0006\u0002\u0010\fR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\n\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f ¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", ExifInterface.GPS_DIRECTION_TRUE, "", "asString", "", "(Ljava/lang/String;)V", "getAsString", "()Ljava/lang/String;", "toString", "valueOf", "aConst", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConst;)Ljava/lang/Object;", "Boolean", "Byte", "Char", "Double", "Float", "Int", "Long", "Null", "Short", CommonClassNames.JAVA_LANG_STRING_SHORT, "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Boolean;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Byte;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Char;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Double;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Float;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Int;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Long;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Null;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Short;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$String;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrConstKind<T> {
    private final java.lang.String asString;

    public /* synthetic */ IrConstKind(java.lang.String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private IrConstKind(java.lang.String str) {
        this.asString = str;
    }

    public final java.lang.String getAsString() {
        return this.asString;
    }

    public final T valueOf(IrConst<?> aConst) {
        Intrinsics.checkNotNullParameter(aConst, "aConst");
        return (T) aConst.getValue();
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Null;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Null extends IrConstKind {
        public static final Null INSTANCE = new Null();

        private Null() {
            super("Null", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Boolean;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Boolean extends IrConstKind<java.lang.Boolean> {
        public static final Boolean INSTANCE = new Boolean();

        private Boolean() {
            super("Boolean", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Char;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Char extends IrConstKind<Character> {
        public static final Char INSTANCE = new Char();

        private Char() {
            super("Char", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0005\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Byte;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Byte extends IrConstKind<java.lang.Byte> {
        public static final Byte INSTANCE = new Byte();

        private Byte() {
            super("Byte", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\n\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Short;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Short extends IrConstKind<java.lang.Short> {
        public static final Short INSTANCE = new Short();

        private Short() {
            super("Short", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Int;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Int extends IrConstKind<Integer> {
        public static final Int INSTANCE = new Int();

        private Int() {
            super("Int", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Long;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Long extends IrConstKind<java.lang.Long> {
        public static final Long INSTANCE = new Long();

        private Long() {
            super("Long", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$String;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class String extends IrConstKind<java.lang.String> {
        public static final String INSTANCE = new String();

        private String() {
            super(CommonClassNames.JAVA_LANG_STRING_SHORT, null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Float;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Float extends IrConstKind<java.lang.Float> {
        public static final Float INSTANCE = new Float();

        private Float() {
            super("Float", null);
        }
    }

    /* JADX INFO: compiled from: IrConstKind.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/IrConstKind$Double;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Double extends IrConstKind<java.lang.Double> {
        public static final Double INSTANCE = new Double();

        private Double() {
            super("Double", null);
        }
    }

    public java.lang.String toString() {
        return this.asString;
    }
}
