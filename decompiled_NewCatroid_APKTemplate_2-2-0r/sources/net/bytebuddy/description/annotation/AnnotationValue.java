package net.bytebuddy.description.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import name.antonsmirnov.firmata.FormatHelper;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface AnnotationValue<T, S> {

    @AlwaysNull
    public static final AnnotationValue<?, ?> UNDEFINED = null;

    AnnotationValue<T, S> filter(MethodDescription.InDefinedShape inDefinedShape);

    AnnotationValue<T, S> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition);

    Sort getSort();

    State getState();

    Loaded<S> load(@MaybeNull ClassLoader classLoader);

    T resolve();

    <W> W resolve(Class<? extends W> cls);

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'JAVA_9_CAPABLE_VM' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:422)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:351)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static abstract class RenderingDispatcher {
        private static final /* synthetic */ RenderingDispatcher[] $VALUES;
        private static final String ARRAY_PREFIX = "Array with component tag: ";
        public static final RenderingDispatcher CURRENT;
        public static final RenderingDispatcher JAVA_14_CAPABLE_VM;
        public static final RenderingDispatcher JAVA_17_CAPABLE_VM;
        public static final RenderingDispatcher JAVA_19_CAPABLE_VM;
        public static final RenderingDispatcher JAVA_9_CAPABLE_VM;
        public static final RenderingDispatcher LEGACY_VM;
        private final char closingBrace;
        private final boolean componentAsInteger;
        private final char openingBrace;

        public abstract String toSourceString(char c);

        public abstract String toSourceString(double d);

        public abstract String toSourceString(float f);

        public abstract String toSourceString(long j);

        public abstract String toSourceString(String str);

        public abstract String toSourceString(TypeDescription typeDescription);

        public static RenderingDispatcher valueOf(String str) {
            return (RenderingDispatcher) Enum.valueOf(RenderingDispatcher.class, str);
        }

        public static RenderingDispatcher[] values() {
            return (RenderingDispatcher[]) $VALUES.clone();
        }

        static {
            RenderingDispatcher renderingDispatcher = new RenderingDispatcher("LEGACY_VM", 0, '[', ']', true) { // from class: net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher.1
                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(String str) {
                    return str;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(char c) {
                    return Character.toString(c);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(long j) {
                    return Long.toString(j);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(float f) {
                    return Float.toString(f);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(double d) {
                    return Double.toString(d);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(TypeDescription typeDescription) {
                    return typeDescription.toString();
                }
            };
            LEGACY_VM = renderingDispatcher;
            char c = '}';
            char c2 = '{';
            RenderingDispatcher renderingDispatcher2 = new RenderingDispatcher("JAVA_9_CAPABLE_VM", 1, c2, c, true) { // from class: net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher.2
                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(char c3) {
                    StringBuilder sb = new StringBuilder(FormatHelper.QUOTE);
                    if (c3 == '\'') {
                        sb.append("\\'");
                    } else {
                        sb.append(c3);
                    }
                    return sb.append('\'').toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(long j) {
                    return Math.abs(j) <= 2147483647L ? String.valueOf(j) : j + "L";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(float f) {
                    if (Math.abs(f) <= Float.MAX_VALUE) {
                        return f + "f";
                    }
                    if (Float.isInfinite(f)) {
                        return f < 0.0f ? "-1.0f/0.0f" : "1.0f/0.0f";
                    }
                    return "0.0f/0.0f";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(double d) {
                    if (Math.abs(d) <= Double.MAX_VALUE) {
                        return Double.toString(d);
                    }
                    if (Double.isInfinite(d)) {
                        return d < 0.0d ? "-1.0/0.0" : "1.0/0.0";
                    }
                    return "0.0/0.0";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(String str) {
                    StringBuilder sb = new StringBuilder("\"");
                    if (str.indexOf(34) != -1) {
                        str = str.replace("\"", "\\\"");
                    }
                    return sb.append(str).append("\"").toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(TypeDescription typeDescription) {
                    return typeDescription.getActualName() + ".class";
                }
            };
            JAVA_9_CAPABLE_VM = renderingDispatcher2;
            char c3 = '}';
            char c4 = '{';
            RenderingDispatcher renderingDispatcher3 = new RenderingDispatcher("JAVA_14_CAPABLE_VM", 2, c4, c3, true) { // from class: net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher.3
                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(byte b) {
                    return "(byte)0x" + Integer.toHexString(b & 255);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(char c5) {
                    StringBuilder sb = new StringBuilder(FormatHelper.QUOTE);
                    if (c5 == '\'') {
                        sb.append("\\'");
                    } else {
                        sb.append(c5);
                    }
                    return sb.append('\'').toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(long j) {
                    return j + "L";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(float f) {
                    if (Math.abs(f) <= Float.MAX_VALUE) {
                        return f + "f";
                    }
                    if (Float.isInfinite(f)) {
                        return f < 0.0f ? "-1.0f/0.0f" : "1.0f/0.0f";
                    }
                    return "0.0f/0.0f";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(double d) {
                    if (Math.abs(d) <= Double.MAX_VALUE) {
                        return Double.toString(d);
                    }
                    if (Double.isInfinite(d)) {
                        return d < 0.0d ? "-1.0/0.0" : "1.0/0.0";
                    }
                    return "0.0/0.0";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(String str) {
                    StringBuilder sb = new StringBuilder("\"");
                    if (str.indexOf(34) != -1) {
                        str = str.replace("\"", "\\\"");
                    }
                    return sb.append(str).append("\"").toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(TypeDescription typeDescription) {
                    return typeDescription.getActualName() + ".class";
                }
            };
            JAVA_14_CAPABLE_VM = renderingDispatcher3;
            RenderingDispatcher renderingDispatcher4 = new RenderingDispatcher("JAVA_17_CAPABLE_VM", 3, c2, c, false) { // from class: net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher.4
                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(byte b) {
                    return "(byte)0x" + Integer.toHexString(b & 255);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(char c5) {
                    StringBuilder sb = new StringBuilder(FormatHelper.QUOTE);
                    if (c5 == '\'') {
                        sb.append("\\'");
                    } else {
                        sb.append(c5);
                    }
                    return sb.append('\'').toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(long j) {
                    return j + "L";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(float f) {
                    if (Math.abs(f) <= Float.MAX_VALUE) {
                        return f + "f";
                    }
                    if (Float.isInfinite(f)) {
                        return f < 0.0f ? "-1.0f/0.0f" : "1.0f/0.0f";
                    }
                    return "0.0f/0.0f";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(double d) {
                    if (Math.abs(d) <= Double.MAX_VALUE) {
                        return Double.toString(d);
                    }
                    if (Double.isInfinite(d)) {
                        return d < 0.0d ? "-1.0/0.0" : "1.0/0.0";
                    }
                    return "0.0/0.0";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(String str) {
                    StringBuilder sb = new StringBuilder("\"");
                    if (str.indexOf(34) != -1) {
                        str = str.replace("\"", "\\\"");
                    }
                    return sb.append(str).append("\"").toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(TypeDescription typeDescription) {
                    return typeDescription.getActualName() + ".class";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toTypeErrorString(Class<?> cls) {
                    return cls.getName();
                }
            };
            JAVA_17_CAPABLE_VM = renderingDispatcher4;
            RenderingDispatcher renderingDispatcher5 = new RenderingDispatcher("JAVA_19_CAPABLE_VM", 4, c4, c3, ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5).isLessThan(ClassFileVersion.JAVA_V17)) { // from class: net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher.5
                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(byte b) {
                    return "(byte)0x" + Integer.toHexString(b & 255);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(char c5) {
                    StringBuilder sb = new StringBuilder(FormatHelper.QUOTE);
                    if (c5 == '\'') {
                        sb.append("\\'");
                    } else {
                        sb.append(c5);
                    }
                    return sb.append('\'').toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(long j) {
                    return j + "L";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(float f) {
                    if (Math.abs(f) <= Float.MAX_VALUE) {
                        return f + "f";
                    }
                    if (Float.isInfinite(f)) {
                        return f < 0.0f ? "-1.0f/0.0f" : "1.0f/0.0f";
                    }
                    return "0.0f/0.0f";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(double d) {
                    if (Math.abs(d) <= Double.MAX_VALUE) {
                        return Double.toString(d);
                    }
                    if (Double.isInfinite(d)) {
                        return d < 0.0d ? "-1.0/0.0" : "1.0/0.0";
                    }
                    return "0.0/0.0";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(String str) {
                    StringBuilder sb = new StringBuilder("\"");
                    if (str.indexOf(34) != -1) {
                        str = str.replace("\"", "\\\"");
                    }
                    return sb.append(str).append("\"").toString();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toSourceString(TypeDescription typeDescription) {
                    return typeDescription.getCanonicalName() + ".class";
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.RenderingDispatcher
                public String toTypeErrorString(Class<?> cls) {
                    return cls.getName();
                }
            };
            JAVA_19_CAPABLE_VM = renderingDispatcher5;
            $VALUES = new RenderingDispatcher[]{renderingDispatcher, renderingDispatcher2, renderingDispatcher3, renderingDispatcher4, renderingDispatcher5};
            ClassFileVersion classFileVersionOfThisVm = ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5);
            if (classFileVersionOfThisVm.isAtLeast(ClassFileVersion.JAVA_V19)) {
                CURRENT = renderingDispatcher5;
                return;
            }
            if (classFileVersionOfThisVm.isAtLeast(ClassFileVersion.JAVA_V17)) {
                CURRENT = renderingDispatcher4;
                return;
            }
            if (classFileVersionOfThisVm.isAtLeast(ClassFileVersion.JAVA_V14)) {
                CURRENT = renderingDispatcher3;
            } else if (classFileVersionOfThisVm.isAtLeast(ClassFileVersion.JAVA_V9)) {
                CURRENT = renderingDispatcher2;
            } else {
                CURRENT = renderingDispatcher;
            }
        }

        private RenderingDispatcher(String str, int i, char c, char c2, boolean z) {
            super(str, i);
            this.openingBrace = c;
            this.closingBrace = c2;
            this.componentAsInteger = z;
        }

        public String toSourceString(boolean z) {
            return Boolean.toString(z);
        }

        public String toSourceString(byte b) {
            return Byte.toString(b);
        }

        public String toSourceString(short s) {
            return Short.toString(s);
        }

        public String toSourceString(int i) {
            return Integer.toString(i);
        }

        public String toSourceString(List<?> list) {
            StringBuilder sbAppend = new StringBuilder().append(this.openingBrace);
            boolean z = true;
            for (Object obj : list) {
                if (z) {
                    z = false;
                } else {
                    sbAppend.append(", ");
                }
                sbAppend.append(obj);
            }
            return sbAppend.append(this.closingBrace).toString();
        }

        public String toArrayErrorString(Sort sort) {
            String string;
            StringBuilder sb = new StringBuilder(ARRAY_PREFIX);
            if (this.componentAsInteger || !sort.isDefined()) {
                string = Integer.toString(sort.getTag());
            } else {
                string = Character.toString((char) sort.getTag());
            }
            return sb.append(string).toString();
        }

        public String toTypeErrorString(Class<?> cls) {
            return cls.toString();
        }
    }

    public interface Loaded<U> {
        State getState();

        boolean represents(Object obj);

        U resolve();

        <V> V resolve(Class<? extends V> cls);

        public static abstract class AbstractBase<W> implements Loaded<W> {
            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public <X> X resolve(Class<? extends X> cls) {
                return cls.cast(resolve());
            }

            public static abstract class ForUnresolvedProperty<Z> extends AbstractBase<Z> {
                @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
                public boolean represents(Object obj) {
                    return false;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
                public State getState() {
                    return State.UNRESOLVED;
                }
            }
        }
    }

    public enum State {
        UNDEFINED,
        UNRESOLVED,
        RESOLVED;

        public boolean isDefined() {
            return this != UNDEFINED;
        }

        public boolean isResolved() {
            return this == RESOLVED;
        }
    }

    public enum Sort {
        BOOLEAN(90),
        BYTE(66),
        SHORT(83),
        CHARACTER(67),
        INTEGER(73),
        LONG(74),
        FLOAT(70),
        DOUBLE(68),
        STRING(115),
        TYPE(99),
        ENUMERATION(101),
        ANNOTATION(64),
        ARRAY(91),
        NONE(0);

        private final int tag;

        Sort(int i) {
            this.tag = i;
        }

        public static Sort of(TypeDefinition typeDefinition) {
            if (typeDefinition.represents(Boolean.TYPE)) {
                return BOOLEAN;
            }
            if (typeDefinition.represents(Byte.TYPE)) {
                return BYTE;
            }
            if (typeDefinition.represents(Short.TYPE)) {
                return SHORT;
            }
            if (typeDefinition.represents(Character.TYPE)) {
                return CHARACTER;
            }
            if (typeDefinition.represents(Integer.TYPE)) {
                return INTEGER;
            }
            if (typeDefinition.represents(Long.TYPE)) {
                return LONG;
            }
            if (typeDefinition.represents(Float.TYPE)) {
                return FLOAT;
            }
            if (typeDefinition.represents(Double.TYPE)) {
                return DOUBLE;
            }
            if (typeDefinition.represents(String.class)) {
                return STRING;
            }
            if (typeDefinition.represents(Class.class)) {
                return TYPE;
            }
            if (typeDefinition.isEnum()) {
                return ENUMERATION;
            }
            if (typeDefinition.isAnnotation()) {
                return ANNOTATION;
            }
            if (typeDefinition.isArray()) {
                return ARRAY;
            }
            return NONE;
        }

        protected int getTag() {
            return this.tag;
        }

        public boolean isDefined() {
            return this != NONE;
        }
    }

    public static abstract class AbstractBase<U, V> implements AnnotationValue<U, V> {
        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public <W> W resolve(Class<? extends W> cls) {
            return cls.cast(resolve());
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<U, V> filter(MethodDescription.InDefinedShape inDefinedShape) {
            return filter(inDefinedShape, inDefinedShape.getReturnType());
        }
    }

    public static class ForConstant<U> extends AbstractBase<U, U> {
        private transient /* synthetic */ int hashCode;
        private final PropertyDelegate propertyDelegate;
        private final U value;

        protected ForConstant(U u, PropertyDelegate propertyDelegate) {
            this.value = u;
            this.propertyDelegate = propertyDelegate;
        }

        public static AnnotationValue<Boolean, Boolean> of(boolean z) {
            return new ForConstant(Boolean.valueOf(z), PropertyDelegate.ForNonArrayType.BOOLEAN);
        }

        public static AnnotationValue<Byte, Byte> of(byte b) {
            return new ForConstant(Byte.valueOf(b), PropertyDelegate.ForNonArrayType.BYTE);
        }

        public static AnnotationValue<Short, Short> of(short s) {
            return new ForConstant(Short.valueOf(s), PropertyDelegate.ForNonArrayType.SHORT);
        }

        public static AnnotationValue<Character, Character> of(char c) {
            return new ForConstant(Character.valueOf(c), PropertyDelegate.ForNonArrayType.CHARACTER);
        }

        public static AnnotationValue<Integer, Integer> of(int i) {
            return new ForConstant(Integer.valueOf(i), PropertyDelegate.ForNonArrayType.INTEGER);
        }

        public static AnnotationValue<Long, Long> of(long j) {
            return new ForConstant(Long.valueOf(j), PropertyDelegate.ForNonArrayType.LONG);
        }

        public static AnnotationValue<Float, Float> of(float f) {
            return new ForConstant(Float.valueOf(f), PropertyDelegate.ForNonArrayType.FLOAT);
        }

        public static AnnotationValue<Double, Double> of(double d) {
            return new ForConstant(Double.valueOf(d), PropertyDelegate.ForNonArrayType.DOUBLE);
        }

        public static AnnotationValue<String, String> of(String str) {
            return new ForConstant(str, PropertyDelegate.ForNonArrayType.STRING);
        }

        public static AnnotationValue<boolean[], boolean[]> of(boolean... zArr) {
            return new ForConstant(zArr, PropertyDelegate.ForArrayType.BOOLEAN);
        }

        public static AnnotationValue<byte[], byte[]> of(byte... bArr) {
            return new ForConstant(bArr, PropertyDelegate.ForArrayType.BYTE);
        }

        public static AnnotationValue<short[], short[]> of(short... sArr) {
            return new ForConstant(sArr, PropertyDelegate.ForArrayType.SHORT);
        }

        public static AnnotationValue<char[], char[]> of(char... cArr) {
            return new ForConstant(cArr, PropertyDelegate.ForArrayType.CHARACTER);
        }

        public static AnnotationValue<int[], int[]> of(int... iArr) {
            return new ForConstant(iArr, PropertyDelegate.ForArrayType.INTEGER);
        }

        public static AnnotationValue<long[], long[]> of(long... jArr) {
            return new ForConstant(jArr, PropertyDelegate.ForArrayType.LONG);
        }

        public static AnnotationValue<float[], float[]> of(float... fArr) {
            return new ForConstant(fArr, PropertyDelegate.ForArrayType.FLOAT);
        }

        public static AnnotationValue<double[], double[]> of(double... dArr) {
            return new ForConstant(dArr, PropertyDelegate.ForArrayType.DOUBLE);
        }

        public static AnnotationValue<String[], String[]> of(String... strArr) {
            return new ForConstant(strArr, PropertyDelegate.ForArrayType.STRING);
        }

        public static AnnotationValue<?, ?> of(Object obj) {
            if (obj instanceof Boolean) {
                return of(((Boolean) obj).booleanValue());
            }
            if (obj instanceof Byte) {
                return of(((Byte) obj).byteValue());
            }
            if (obj instanceof Short) {
                return of(((Short) obj).shortValue());
            }
            if (obj instanceof Character) {
                return of(((Character) obj).charValue());
            }
            if (obj instanceof Integer) {
                return of(((Integer) obj).intValue());
            }
            if (obj instanceof Long) {
                return of(((Long) obj).longValue());
            }
            if (obj instanceof Float) {
                return of(((Float) obj).floatValue());
            }
            if (obj instanceof Double) {
                return of(((Double) obj).doubleValue());
            }
            if (obj instanceof String) {
                return of((String) obj);
            }
            if (obj instanceof boolean[]) {
                return of((boolean[]) obj);
            }
            if (obj instanceof byte[]) {
                return of((byte[]) obj);
            }
            if (obj instanceof short[]) {
                return of((short[]) obj);
            }
            if (obj instanceof char[]) {
                return of((char[]) obj);
            }
            if (obj instanceof int[]) {
                return of((int[]) obj);
            }
            if (obj instanceof long[]) {
                return of((long[]) obj);
            }
            if (obj instanceof float[]) {
                return of((float[]) obj);
            }
            if (obj instanceof double[]) {
                return of((double[]) obj);
            }
            if (obj instanceof String[]) {
                return of((String[]) obj);
            }
            throw new IllegalArgumentException("Not a constant annotation value: " + obj);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.RESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.of(TypeDescription.ForLoadedType.of(this.value.getClass()).asUnboxed());
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<U, U> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            if (typeDefinition.asErasure().asBoxed().represents(this.value.getClass())) {
                return this;
            }
            if (this.value.getClass().isArray()) {
                return new ForMismatchedType(inDefinedShape, RenderingDispatcher.CURRENT.toArrayErrorString(Sort.of(TypeDescription.ForLoadedType.of(this.value.getClass().getComponentType()))));
            }
            if (this.value instanceof Enum) {
                return new ForMismatchedType(inDefinedShape, this.value.getClass().getName() + '.' + ((Enum) this.value).name());
            }
            return new ForMismatchedType(inDefinedShape, RenderingDispatcher.CURRENT.toTypeErrorString(this.value.getClass()) + '[' + this.value + ']');
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public U resolve() {
            return this.value;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<U> load(@MaybeNull ClassLoader classLoader) {
            return new Loaded(this.value, this.propertyDelegate);
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : this.propertyDelegate.hashCode(this.value);
            if (iHashCode == 0) {
                return this.hashCode;
            }
            this.hashCode = iHashCode;
            return iHashCode;
        }

        public boolean equals(@MaybeNull Object obj) {
            return this == obj || ((obj instanceof AnnotationValue) && this.propertyDelegate.equals(this.value, ((AnnotationValue) obj).resolve()));
        }

        public String toString() {
            return this.propertyDelegate.toString(this.value);
        }

        protected interface PropertyDelegate {
            <S> S copy(S s);

            boolean equals(Object obj, Object obj2);

            int hashCode(Object obj);

            String toString(Object obj);

            public enum ForNonArrayType implements PropertyDelegate {
                BOOLEAN { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.1
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Boolean) obj).booleanValue());
                    }
                },
                BYTE { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.2
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Byte) obj).byteValue());
                    }
                },
                SHORT { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.3
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Short) obj).shortValue());
                    }
                },
                CHARACTER { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.4
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Character) obj).charValue());
                    }
                },
                INTEGER { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.5
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Integer) obj).intValue());
                    }
                },
                LONG { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.6
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Long) obj).longValue());
                    }
                },
                FLOAT { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.7
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Float) obj).floatValue());
                    }
                },
                DOUBLE { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.8
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString(((Double) obj).doubleValue());
                    }
                },
                STRING { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForNonArrayType.9
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public String toString(Object obj) {
                        return RenderingDispatcher.CURRENT.toSourceString((String) obj);
                    }
                };

                @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                public <S> S copy(S s) {
                    return s;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                public int hashCode(Object obj) {
                    return obj.hashCode();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                public boolean equals(Object obj, Object obj2) {
                    return obj.equals(obj2);
                }
            }

            public enum ForArrayType implements PropertyDelegate {
                BOOLEAN { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.1
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((boolean[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((boolean[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof boolean[]) && Arrays.equals((boolean[]) obj, (boolean[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.BOOLEAN.toString(Boolean.valueOf(Array.getBoolean(obj, i)));
                    }
                },
                BYTE { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.2
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((byte[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((byte[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof byte[]) && Arrays.equals((byte[]) obj, (byte[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.BYTE.toString(Byte.valueOf(Array.getByte(obj, i)));
                    }
                },
                SHORT { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.3
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((short[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((short[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof short[]) && Arrays.equals((short[]) obj, (short[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.SHORT.toString(Short.valueOf(Array.getShort(obj, i)));
                    }
                },
                CHARACTER { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.4
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((char[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((char[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof char[]) && Arrays.equals((char[]) obj, (char[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.CHARACTER.toString(Character.valueOf(Array.getChar(obj, i)));
                    }
                },
                INTEGER { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.5
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((int[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((int[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof int[]) && Arrays.equals((int[]) obj, (int[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.INTEGER.toString(Integer.valueOf(Array.getInt(obj, i)));
                    }
                },
                LONG { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.6
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((long[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((long[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof long[]) && Arrays.equals((long[]) obj, (long[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.LONG.toString(Long.valueOf(Array.getLong(obj, i)));
                    }
                },
                FLOAT { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.7
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((float[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((float[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof float[]) && Arrays.equals((float[]) obj, (float[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.FLOAT.toString(Float.valueOf(Array.getFloat(obj, i)));
                    }
                },
                DOUBLE { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.8
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((double[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((double[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof double[]) && Arrays.equals((double[]) obj, (double[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.DOUBLE.toString(Double.valueOf(Array.getDouble(obj, i)));
                    }
                },
                STRING { // from class: net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType.9
                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected Object doCopy(Object obj) {
                        return ((String[]) obj).clone();
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public int hashCode(Object obj) {
                        return Arrays.hashCode((String[]) obj);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                    public boolean equals(Object obj, Object obj2) {
                        return (obj2 instanceof String[]) && Arrays.equals((String[]) obj, (String[]) obj2);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate.ForArrayType
                    protected String toString(Object obj, int i) {
                        return ForNonArrayType.STRING.toString(Array.get(obj, i));
                    }
                };

                protected abstract Object doCopy(Object obj);

                protected abstract String toString(Object obj, int i);

                @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                public <S> S copy(S s) {
                    return (S) doCopy(s);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.ForConstant.PropertyDelegate
                public String toString(Object obj) {
                    ArrayList arrayList = new ArrayList(Array.getLength(obj));
                    for (int i = 0; i < Array.getLength(obj); i++) {
                        arrayList.add(toString(obj, i));
                    }
                    return RenderingDispatcher.CURRENT.toSourceString(arrayList);
                }
            }
        }

        protected static class Loaded<V> extends Loaded.AbstractBase<V> {
            private transient /* synthetic */ int hashCode;
            private final PropertyDelegate propertyDelegate;
            private final V value;

            protected Loaded(V v, PropertyDelegate propertyDelegate) {
                this.value = v;
                this.propertyDelegate = propertyDelegate;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public State getState() {
                return State.RESOLVED;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public V resolve() {
                return (V) this.propertyDelegate.copy(this.value);
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public boolean represents(Object obj) {
                return this.propertyDelegate.equals(this.value, obj);
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int iHashCode = this.hashCode != 0 ? 0 : this.propertyDelegate.hashCode(this.value);
                if (iHashCode == 0) {
                    return this.hashCode;
                }
                this.hashCode = iHashCode;
                return iHashCode;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Loaded)) {
                    return false;
                }
                Loaded loaded = (Loaded) obj;
                return loaded.getState().isResolved() && this.propertyDelegate.equals(this.value, loaded.resolve());
            }

            public String toString() {
                return this.propertyDelegate.toString(this.value);
            }
        }
    }

    public static class ForAnnotationDescription<U extends Annotation> extends AbstractBase<AnnotationDescription, U> {
        private final AnnotationDescription annotationDescription;

        public ForAnnotationDescription(AnnotationDescription annotationDescription) {
            this.annotationDescription = annotationDescription;
        }

        public static <V extends Annotation> AnnotationValue<AnnotationDescription, V> of(TypeDescription typeDescription, Map<String, ? extends AnnotationValue<?, ?>> map) {
            return new ForAnnotationDescription(new AnnotationDescription.Latent(typeDescription, map));
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.RESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.ANNOTATION;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<AnnotationDescription, U> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            String string;
            if (typeDefinition.asErasure().equals(this.annotationDescription.getAnnotationType())) {
                return this;
            }
            if (inDefinedShape.getReturnType().isArray()) {
                string = RenderingDispatcher.CURRENT.toArrayErrorString(Sort.ANNOTATION);
            } else {
                string = this.annotationDescription.toString();
            }
            return new ForMismatchedType(inDefinedShape, string);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationDescription resolve() {
            return this.annotationDescription;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<U> load(@MaybeNull ClassLoader classLoader) {
            try {
                AnnotationDescription annotationDescription = this.annotationDescription;
                return new Loaded(annotationDescription.prepare(Class.forName(annotationDescription.getAnnotationType().getName(), false, classLoader)).load());
            } catch (ClassNotFoundException e) {
                return new ForMissingType.Loaded(this.annotationDescription.getAnnotationType().getName(), e);
            }
        }

        public int hashCode() {
            return this.annotationDescription.hashCode();
        }

        public boolean equals(@MaybeNull Object obj) {
            return this == obj || ((obj instanceof AnnotationValue) && this.annotationDescription.equals(((AnnotationValue) obj).resolve()));
        }

        public String toString() {
            return this.annotationDescription.toString();
        }

        public static class Loaded<V extends Annotation> extends Loaded.AbstractBase<V> {
            private final V annotation;

            public Loaded(V v) {
                this.annotation = v;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public State getState() {
                return State.RESOLVED;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public V resolve() {
                return this.annotation;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public boolean represents(Object obj) {
                return this.annotation.equals(obj);
            }

            public int hashCode() {
                return this.annotation.hashCode();
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Loaded)) {
                    return false;
                }
                Loaded loaded = (Loaded) obj;
                return loaded.getState().isResolved() && this.annotation.equals(loaded.resolve());
            }

            public String toString() {
                return this.annotation.toString();
            }
        }
    }

    public static class ForEnumerationDescription<U extends Enum<U>> extends AbstractBase<EnumerationDescription, U> {
        private final EnumerationDescription enumerationDescription;

        public ForEnumerationDescription(EnumerationDescription enumerationDescription) {
            this.enumerationDescription = enumerationDescription;
        }

        public static <V extends Enum<V>> AnnotationValue<EnumerationDescription, V> of(EnumerationDescription enumerationDescription) {
            return new ForEnumerationDescription(enumerationDescription);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public EnumerationDescription resolve() {
            return this.enumerationDescription;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.RESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.ENUMERATION;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<EnumerationDescription, U> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            String arrayErrorString;
            if (typeDefinition.asErasure().equals(this.enumerationDescription.getEnumerationType())) {
                return this;
            }
            if (inDefinedShape.getReturnType().isArray()) {
                arrayErrorString = RenderingDispatcher.CURRENT.toArrayErrorString(Sort.ENUMERATION);
            } else {
                arrayErrorString = this.enumerationDescription.getEnumerationType().getName() + '.' + this.enumerationDescription.getValue();
            }
            return new ForMismatchedType(inDefinedShape, arrayErrorString);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<U> load(@MaybeNull ClassLoader classLoader) {
            try {
                EnumerationDescription enumerationDescription = this.enumerationDescription;
                return new Loaded(enumerationDescription.load(Class.forName(enumerationDescription.getEnumerationType().getName(), false, classLoader)));
            } catch (ClassNotFoundException e) {
                return new ForMissingType.Loaded(this.enumerationDescription.getEnumerationType().getName(), e);
            }
        }

        public int hashCode() {
            return this.enumerationDescription.hashCode();
        }

        public boolean equals(@MaybeNull Object obj) {
            return this == obj || ((obj instanceof AnnotationValue) && this.enumerationDescription.equals(((AnnotationValue) obj).resolve()));
        }

        public String toString() {
            return this.enumerationDescription.toString();
        }

        public static class Loaded<V extends Enum<V>> extends Loaded.AbstractBase<V> {
            private final V enumeration;

            public Loaded(V v) {
                this.enumeration = v;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public State getState() {
                return State.RESOLVED;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public V resolve() {
                return this.enumeration;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public boolean represents(Object obj) {
                return this.enumeration.equals(obj);
            }

            public int hashCode() {
                return this.enumeration.hashCode();
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Loaded)) {
                    return false;
                }
                Loaded loaded = (Loaded) obj;
                return loaded.getState().isResolved() && this.enumeration.equals(loaded.resolve());
            }

            public String toString() {
                return this.enumeration.toString();
            }

            public static class WithIncompatibleRuntimeType extends Loaded.AbstractBase<Enum<?>> {
                private final Class<?> type;

                @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
                public boolean represents(Object obj) {
                    return false;
                }

                public WithIncompatibleRuntimeType(Class<?> cls) {
                    this.type = cls;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
                public State getState() {
                    return State.UNRESOLVED;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
                public Enum<?> resolve() {
                    throw new IncompatibleClassChangeError("Not an enumeration type: " + this.type.getName());
                }
            }
        }

        public static class WithUnknownConstant<U extends Enum<U>> extends AbstractBase<EnumerationDescription, U> {
            private final TypeDescription typeDescription;
            private final String value;

            @Override // net.bytebuddy.description.annotation.AnnotationValue
            public AnnotationValue<EnumerationDescription, U> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
                return this;
            }

            public WithUnknownConstant(TypeDescription typeDescription, String str) {
                this.typeDescription = typeDescription;
                this.value = str;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue
            public State getState() {
                return State.UNRESOLVED;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue
            public Sort getSort() {
                return Sort.NONE;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue
            public EnumerationDescription resolve() {
                throw new IllegalStateException(this.typeDescription + " does not declare enumeration constant " + this.value);
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue
            public Loaded<U> load(@MaybeNull ClassLoader classLoader) {
                try {
                    return new Loaded(Class.forName(this.typeDescription.getName(), false, classLoader), this.value);
                } catch (ClassNotFoundException e) {
                    return new ForMissingType.Loaded(this.typeDescription.getName(), e);
                }
            }

            public String toString() {
                return this.value + " /* Warning: constant not present! */";
            }

            public static class Loaded extends Loaded.AbstractBase.ForUnresolvedProperty<Enum<?>> {
                private final Class<? extends Enum<?>> enumType;
                private final String value;

                public Loaded(Class<? extends Enum<?>> cls, String str) {
                    this.enumType = cls;
                    this.value = str;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
                public Enum<?> resolve() {
                    throw new EnumConstantNotPresentException(this.enumType, this.value);
                }

                public String toString() {
                    return this.value + " /* Warning: constant not present! */";
                }
            }
        }
    }

    public static class ForTypeDescription<U extends Class<U>> extends AbstractBase<TypeDescription, U> {
        private static final boolean NO_INITIALIZATION = false;
        private static final Map<TypeDescription, Class<?>> PRIMITIVE_TYPES = new HashMap();
        private final TypeDescription typeDescription;

        static {
            Class<?>[] clsArr = {Boolean.TYPE, Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
            for (int i = 0; i < 9; i++) {
                Class<?> cls = clsArr[i];
                PRIMITIVE_TYPES.put(TypeDescription.ForLoadedType.of(cls), cls);
            }
        }

        public ForTypeDescription(TypeDescription typeDescription) {
            this.typeDescription = typeDescription;
        }

        public static <V extends Class<V>> AnnotationValue<TypeDescription, V> of(TypeDescription typeDescription) {
            return new ForTypeDescription(typeDescription);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.RESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.TYPE;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<TypeDescription, U> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            String arrayErrorString;
            if (typeDefinition.asErasure().represents(Class.class)) {
                return this;
            }
            if (inDefinedShape.getReturnType().isArray()) {
                arrayErrorString = RenderingDispatcher.CURRENT.toArrayErrorString(Sort.TYPE);
            } else {
                arrayErrorString = Class.class.getName() + '[' + this.typeDescription.getName() + ']';
            }
            return new ForMismatchedType(inDefinedShape, arrayErrorString);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public TypeDescription resolve() {
            return this.typeDescription;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<U> load(@MaybeNull ClassLoader classLoader) {
            Class<?> cls;
            try {
                if (this.typeDescription.isPrimitive()) {
                    cls = PRIMITIVE_TYPES.get(this.typeDescription);
                } else {
                    cls = Class.forName(this.typeDescription.getName(), false, classLoader);
                }
                return new Loaded(cls);
            } catch (ClassNotFoundException e) {
                return new ForMissingType.Loaded(this.typeDescription.getName(), e);
            }
        }

        public int hashCode() {
            return this.typeDescription.hashCode();
        }

        public boolean equals(@MaybeNull Object obj) {
            return this == obj || ((obj instanceof AnnotationValue) && this.typeDescription.equals(((AnnotationValue) obj).resolve()));
        }

        public String toString() {
            return RenderingDispatcher.CURRENT.toSourceString(this.typeDescription);
        }

        protected static class Loaded<U extends Class<U>> extends Loaded.AbstractBase<U> {
            private final U type;

            public Loaded(U u) {
                this.type = u;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public State getState() {
                return State.RESOLVED;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public U resolve() {
                return this.type;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public boolean represents(Object obj) {
                return this.type.equals(obj);
            }

            public int hashCode() {
                return this.type.hashCode();
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Loaded)) {
                    return false;
                }
                Loaded loaded = (Loaded) obj;
                return loaded.getState().isResolved() && this.type.equals(loaded.resolve());
            }

            public String toString() {
                return RenderingDispatcher.CURRENT.toSourceString(TypeDescription.ForLoadedType.of(this.type));
            }
        }
    }

    public static class ForDescriptionArray<U, V> extends AbstractBase<U, V> {
        private final TypeDescription componentType;
        private transient /* synthetic */ int hashCode;
        private final Class<?> unloadedComponentType;
        private final List<? extends AnnotationValue<?, ?>> values;

        public ForDescriptionArray(Class<?> cls, TypeDescription typeDescription, List<? extends AnnotationValue<?, ?>> list) {
            this.unloadedComponentType = cls;
            this.componentType = typeDescription;
            this.values = list;
        }

        public static <W extends Enum<W>> AnnotationValue<EnumerationDescription[], W[]> of(TypeDescription typeDescription, EnumerationDescription[] enumerationDescriptionArr) {
            ArrayList arrayList = new ArrayList(enumerationDescriptionArr.length);
            for (EnumerationDescription enumerationDescription : enumerationDescriptionArr) {
                if (!enumerationDescription.getEnumerationType().equals(typeDescription)) {
                    throw new IllegalArgumentException(enumerationDescription + " is not of " + typeDescription);
                }
                arrayList.add(ForEnumerationDescription.of(enumerationDescription));
            }
            return new ForDescriptionArray(EnumerationDescription.class, typeDescription, arrayList);
        }

        public static <W extends Annotation> AnnotationValue<AnnotationDescription[], W[]> of(TypeDescription typeDescription, AnnotationDescription[] annotationDescriptionArr) {
            ArrayList arrayList = new ArrayList(annotationDescriptionArr.length);
            for (AnnotationDescription annotationDescription : annotationDescriptionArr) {
                if (!annotationDescription.getAnnotationType().equals(typeDescription)) {
                    throw new IllegalArgumentException(annotationDescription + " is not of " + typeDescription);
                }
                arrayList.add(new ForAnnotationDescription(annotationDescription));
            }
            return new ForDescriptionArray(AnnotationDescription.class, typeDescription, arrayList);
        }

        public static AnnotationValue<TypeDescription[], Class<?>[]> of(TypeDescription[] typeDescriptionArr) {
            ArrayList arrayList = new ArrayList(typeDescriptionArr.length);
            for (TypeDescription typeDescription : typeDescriptionArr) {
                arrayList.add(ForTypeDescription.of(typeDescription));
            }
            return new ForDescriptionArray(TypeDescription.class, TypeDescription.ForLoadedType.of(Class.class), arrayList);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.RESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.ARRAY;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<U, V> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            if (typeDefinition.isArray() && typeDefinition.getComponentType().asErasure().equals(this.componentType)) {
                Iterator<? extends AnnotationValue<?, ?>> it = this.values.iterator();
                while (it.hasNext()) {
                    AnnotationValue<U, V> annotationValue = (AnnotationValue<U, V>) it.next().filter(inDefinedShape, typeDefinition.getComponentType());
                    if (annotationValue.getState() != State.RESOLVED) {
                        return annotationValue;
                    }
                }
                return this;
            }
            return new ForMismatchedType(inDefinedShape, RenderingDispatcher.CURRENT.toArrayErrorString(Sort.of(this.componentType)));
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public U resolve() {
            U u = (U) Array.newInstance(this.unloadedComponentType, this.values.size());
            Iterator<? extends AnnotationValue<?, ?>> it = this.values.iterator();
            int i = 0;
            while (it.hasNext()) {
                Array.set(u, i, it.next().resolve());
                i++;
            }
            return u;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<V> load(@MaybeNull ClassLoader classLoader) {
            ArrayList arrayList = new ArrayList(this.values.size());
            Iterator<? extends AnnotationValue<?, ?>> it = this.values.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().load(classLoader));
            }
            try {
                return new Loaded(Class.forName(this.componentType.getName(), false, classLoader), arrayList);
            } catch (ClassNotFoundException e) {
                return new ForMissingType.Loaded(this.componentType.getName(), e);
            }
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int i;
            if (this.hashCode != 0) {
                i = 0;
            } else {
                Iterator<? extends AnnotationValue<?, ?>> it = this.values.iterator();
                int iHashCode = 1;
                while (it.hasNext()) {
                    iHashCode = (iHashCode * 31) + it.next().hashCode();
                }
                i = iHashCode;
            }
            if (i == 0) {
                return this.hashCode;
            }
            this.hashCode = i;
            return i;
        }

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnnotationValue)) {
                return false;
            }
            Object objResolve = ((AnnotationValue) obj).resolve();
            if (!objResolve.getClass().isArray() || this.values.size() != Array.getLength(objResolve)) {
                return false;
            }
            Iterator<? extends AnnotationValue<?, ?>> it = this.values.iterator();
            for (int i = 0; i < this.values.size(); i++) {
                if (!it.next().resolve().equals(Array.get(objResolve, i))) {
                    return false;
                }
            }
            return true;
        }

        public String toString() {
            return RenderingDispatcher.CURRENT.toSourceString(this.values);
        }

        protected static class Loaded<W> extends Loaded.AbstractBase<W> {
            private final Class<W> componentType;
            private transient /* synthetic */ int hashCode;
            private final List<Loaded<?>> values;

            protected Loaded(Class<W> cls, List<Loaded<?>> list) {
                this.componentType = cls;
                this.values = list;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public State getState() {
                Iterator<Loaded<?>> it = this.values.iterator();
                while (it.hasNext()) {
                    if (!it.next().getState().isResolved()) {
                        return State.UNRESOLVED;
                    }
                }
                return State.RESOLVED;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public W resolve() {
                W w = (W) Array.newInstance((Class<?>) this.componentType, this.values.size());
                Iterator<Loaded<?>> it = this.values.iterator();
                int i = 0;
                while (it.hasNext()) {
                    Array.set(w, i, it.next().resolve());
                    i++;
                }
                return w;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public boolean represents(Object obj) {
                if (!(obj instanceof Object[]) || obj.getClass().getComponentType() != this.componentType) {
                    return false;
                }
                Object[] objArr = (Object[]) obj;
                if (this.values.size() != objArr.length) {
                    return false;
                }
                Iterator<Loaded<?>> it = this.values.iterator();
                for (Object obj2 : objArr) {
                    if (!it.next().represents(obj2)) {
                        return false;
                    }
                }
                return true;
            }

            @CachedReturnPlugin.Enhance("hashCode")
            public int hashCode() {
                int i;
                if (this.hashCode != 0) {
                    i = 0;
                } else {
                    Iterator<Loaded<?>> it = this.values.iterator();
                    int iHashCode = 1;
                    while (it.hasNext()) {
                        iHashCode = (iHashCode * 31) + it.next().hashCode();
                    }
                    i = iHashCode;
                }
                if (i == 0) {
                    return this.hashCode;
                }
                this.hashCode = i;
                return i;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Loaded)) {
                    return false;
                }
                Loaded loaded = (Loaded) obj;
                if (!loaded.getState().isResolved()) {
                    return false;
                }
                Object objResolve = loaded.resolve();
                if (!(objResolve instanceof Object[])) {
                    return false;
                }
                Object[] objArr = (Object[]) objResolve;
                if (this.values.size() != objArr.length) {
                    return false;
                }
                Iterator<Loaded<?>> it = this.values.iterator();
                for (Object obj2 : objArr) {
                    Loaded<?> next = it.next();
                    if (!next.getState().isResolved() || !next.resolve().equals(obj2)) {
                        return false;
                    }
                }
                return true;
            }

            public String toString() {
                return RenderingDispatcher.CURRENT.toSourceString(this.values);
            }
        }
    }

    public static class ForMissingType<U, V> extends AbstractBase<U, V> {
        private final String typeName;

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<U, V> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            return this;
        }

        public ForMissingType(String str) {
            this.typeName = str;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.UNRESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.NONE;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public U resolve() {
            throw new IllegalStateException("Type not found: " + this.typeName);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<V> load(@MaybeNull ClassLoader classLoader) {
            return new Loaded(this.typeName, new ClassNotFoundException(this.typeName));
        }

        public String toString() {
            return this.typeName + ".class /* Warning: type not present! */";
        }

        public static class Loaded<U> extends Loaded.AbstractBase.ForUnresolvedProperty<U> {
            private final ClassNotFoundException exception;
            private final String typeName;

            public Loaded(String str, ClassNotFoundException classNotFoundException) {
                this.typeName = str;
                this.exception = classNotFoundException;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public U resolve() {
                throw new TypeNotPresentException(this.typeName, this.exception);
            }

            public String toString() {
                return this.typeName + ".class /* Warning: type not present! */";
            }
        }
    }

    public static class ForMismatchedType<U, V> extends AbstractBase<U, V> {
        private final MethodDescription.InDefinedShape property;
        private final String value;

        public ForMismatchedType(MethodDescription.InDefinedShape inDefinedShape, String str) {
            this.property = inDefinedShape;
            this.value = str;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.UNRESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.NONE;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<U, V> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            return new ForMismatchedType(inDefinedShape, this.value);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public U resolve() {
            throw new IllegalStateException(this.value + " cannot be used as value for " + this.property);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<V> load(@MaybeNull ClassLoader classLoader) {
            try {
                Class<?> cls = Class.forName(this.property.getDeclaringType().getName(), false, classLoader);
                try {
                    return new Loaded(cls.getMethod(this.property.getName(), new Class[0]), this.value);
                } catch (NoSuchMethodException unused) {
                    return new ForIncompatibleType.Loaded(cls);
                }
            } catch (ClassNotFoundException e) {
                return new ForMissingType.Loaded(this.property.getDeclaringType().getName(), e);
            }
        }

        public String toString() {
            return "/* Warning type mismatch! \"" + this.value + "\" */";
        }

        public static class Loaded<W> extends Loaded.AbstractBase.ForUnresolvedProperty<W> {
            private final Method property;
            private final String value;

            public Loaded(Method method, String str) {
                this.property = method;
                this.value = str;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public W resolve() {
                throw new AnnotationTypeMismatchException(this.property, this.value);
            }

            public String toString() {
                return "/* Warning type mismatch! \"" + this.value + "\" */";
            }
        }
    }

    public static class ForMissingValue<U, V> extends AbstractBase<U, V> {
        private final String property;
        private final TypeDescription typeDescription;

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<U, V> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            return this;
        }

        public ForMissingValue(TypeDescription typeDescription, String str) {
            this.typeDescription = typeDescription;
            this.property = str;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.UNDEFINED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.NONE;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<V> load(@MaybeNull ClassLoader classLoader) {
            try {
                Class<?> cls = Class.forName(this.typeDescription.getName(), false, classLoader);
                return cls.isAnnotation() ? new Loaded(cls, this.property) : new ForIncompatibleType.Loaded(cls);
            } catch (ClassNotFoundException e) {
                return new ForMissingType.Loaded(this.typeDescription.getName(), e);
            }
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public U resolve() {
            throw new IllegalStateException(this.typeDescription + " does not define " + this.property);
        }

        public static class Loaded<W> extends Loaded.AbstractBase<W> {
            private final String property;
            private final Class<? extends Annotation> type;

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public boolean represents(Object obj) {
                return false;
            }

            public Loaded(Class<? extends Annotation> cls, String str) {
                this.type = cls;
                this.property = str;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public State getState() {
                return State.UNDEFINED;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public W resolve() {
                throw new IncompleteAnnotationException(this.type, this.property);
            }
        }
    }

    public static class ForIncompatibleType<U, V> extends AbstractBase<U, V> {
        private final TypeDescription typeDescription;

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public AnnotationValue<U, V> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
            return this;
        }

        public ForIncompatibleType(TypeDescription typeDescription) {
            this.typeDescription = typeDescription;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public State getState() {
            return State.UNRESOLVED;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Sort getSort() {
            return Sort.NONE;
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public U resolve() {
            throw new IllegalStateException("Property is defined with an incompatible runtime type: " + this.typeDescription);
        }

        @Override // net.bytebuddy.description.annotation.AnnotationValue
        public Loaded<V> load(@MaybeNull ClassLoader classLoader) {
            try {
                return new Loaded(Class.forName(this.typeDescription.getName(), false, classLoader));
            } catch (ClassNotFoundException e) {
                return new ForMissingType.Loaded(this.typeDescription.getName(), e);
            }
        }

        public String toString() {
            return "/* Warning type incompatibility! \"" + this.typeDescription.getName() + "\" */";
        }

        public static class Loaded<W> extends Loaded.AbstractBase.ForUnresolvedProperty<W> {
            private final Class<?> type;

            public Loaded(Class<?> cls) {
                this.type = cls;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationValue.Loaded
            public W resolve() {
                throw new IncompatibleClassChangeError(this.type.toString());
            }

            public String toString() {
                return "/* Warning type incompatibility! \"" + this.type.getName() + "\" */";
            }
        }
    }
}
