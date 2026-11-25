package org.jf.dexlib2.writer.builder;

import java.util.List;
import java.util.Set;
import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue;
import org.jf.dexlib2.base.value.BaseArrayEncodedValue;
import org.jf.dexlib2.base.value.BaseBooleanEncodedValue;
import org.jf.dexlib2.base.value.BaseEnumEncodedValue;
import org.jf.dexlib2.base.value.BaseFieldEncodedValue;
import org.jf.dexlib2.base.value.BaseMethodEncodedValue;
import org.jf.dexlib2.base.value.BaseMethodHandleEncodedValue;
import org.jf.dexlib2.base.value.BaseMethodTypeEncodedValue;
import org.jf.dexlib2.base.value.BaseNullEncodedValue;
import org.jf.dexlib2.base.value.BaseStringEncodedValue;
import org.jf.dexlib2.base.value.BaseTypeEncodedValue;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue;
import org.jf.util.ExceptionWithContext;

/* JADX INFO: loaded from: classes6.dex */
public abstract class BuilderEncodedValues {

    public interface BuilderEncodedValue extends EncodedValue {
    }

    public static class BuilderAnnotationEncodedValue extends BaseAnnotationEncodedValue implements BuilderEncodedValue {
        final Set<? extends BuilderAnnotationElement> elements;
        final BuilderTypeReference typeReference;

        BuilderAnnotationEncodedValue(BuilderTypeReference builderTypeReference, Set<? extends BuilderAnnotationElement> set) {
            this.typeReference = builderTypeReference;
            this.elements = set;
        }

        @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue, org.jf.dexlib2.iface.BasicAnnotation
        public String getType() {
            return this.typeReference.getType();
        }

        @Override // org.jf.dexlib2.iface.value.AnnotationEncodedValue, org.jf.dexlib2.iface.BasicAnnotation
        public Set<? extends BuilderAnnotationElement> getElements() {
            return this.elements;
        }
    }

    public static class BuilderArrayEncodedValue extends BaseArrayEncodedValue implements BuilderEncodedValue {
        final List<? extends BuilderEncodedValue> elements;
        int offset = 0;

        BuilderArrayEncodedValue(List<? extends BuilderEncodedValue> list) {
            this.elements = list;
        }

        @Override // org.jf.dexlib2.iface.value.ArrayEncodedValue
        public List<? extends EncodedValue> getValue() {
            return this.elements;
        }
    }

    public static BuilderEncodedValue defaultValueForType(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == 'F') {
            return new BuilderFloatEncodedValue(0.0f);
        }
        if (cCharAt != 'L') {
            if (cCharAt == 'S') {
                return new BuilderShortEncodedValue((short) 0);
            }
            if (cCharAt == 'I') {
                return new BuilderIntEncodedValue(0);
            }
            if (cCharAt == 'J') {
                return new BuilderLongEncodedValue(0L);
            }
            if (cCharAt == 'Z') {
                return BuilderBooleanEncodedValue.FALSE_VALUE;
            }
            if (cCharAt != '[') {
                switch (cCharAt) {
                    case 'B':
                        return new BuilderByteEncodedValue((byte) 0);
                    case 'C':
                        return new BuilderCharEncodedValue((char) 0);
                    case 'D':
                        return new BuilderDoubleEncodedValue(0.0d);
                    default:
                        throw new ExceptionWithContext("Unrecognized type: %s", str);
                }
            }
        }
        return BuilderNullEncodedValue.INSTANCE;
    }

    public static class BuilderBooleanEncodedValue extends BaseBooleanEncodedValue implements BuilderEncodedValue {
        private final boolean value;
        public static final BuilderBooleanEncodedValue TRUE_VALUE = new BuilderBooleanEncodedValue(true);
        public static final BuilderBooleanEncodedValue FALSE_VALUE = new BuilderBooleanEncodedValue(false);

        private BuilderBooleanEncodedValue(boolean z) {
            this.value = z;
        }

        @Override // org.jf.dexlib2.iface.value.BooleanEncodedValue
        public boolean getValue() {
            return this.value;
        }
    }

    public static class BuilderByteEncodedValue extends ImmutableByteEncodedValue implements BuilderEncodedValue {
        public BuilderByteEncodedValue(byte b) {
            super(b);
        }
    }

    public static class BuilderCharEncodedValue extends ImmutableCharEncodedValue implements BuilderEncodedValue {
        public BuilderCharEncodedValue(char c) {
            super(c);
        }
    }

    public static class BuilderDoubleEncodedValue extends ImmutableDoubleEncodedValue implements BuilderEncodedValue {
        public BuilderDoubleEncodedValue(double d) {
            super(d);
        }
    }

    public static class BuilderEnumEncodedValue extends BaseEnumEncodedValue implements BuilderEncodedValue {
        final BuilderFieldReference enumReference;

        BuilderEnumEncodedValue(BuilderFieldReference builderFieldReference) {
            this.enumReference = builderFieldReference;
        }

        @Override // org.jf.dexlib2.iface.value.EnumEncodedValue
        public BuilderFieldReference getValue() {
            return this.enumReference;
        }
    }

    public static class BuilderFieldEncodedValue extends BaseFieldEncodedValue implements BuilderEncodedValue {
        final BuilderFieldReference fieldReference;

        BuilderFieldEncodedValue(BuilderFieldReference builderFieldReference) {
            this.fieldReference = builderFieldReference;
        }

        @Override // org.jf.dexlib2.iface.value.FieldEncodedValue
        public BuilderFieldReference getValue() {
            return this.fieldReference;
        }
    }

    public static class BuilderFloatEncodedValue extends ImmutableFloatEncodedValue implements BuilderEncodedValue {
        public BuilderFloatEncodedValue(float f) {
            super(f);
        }
    }

    public static class BuilderIntEncodedValue extends ImmutableIntEncodedValue implements BuilderEncodedValue {
        public BuilderIntEncodedValue(int i) {
            super(i);
        }
    }

    public static class BuilderLongEncodedValue extends ImmutableLongEncodedValue implements BuilderEncodedValue {
        public BuilderLongEncodedValue(long j) {
            super(j);
        }
    }

    public static class BuilderMethodEncodedValue extends BaseMethodEncodedValue implements BuilderEncodedValue {
        final BuilderMethodReference methodReference;

        BuilderMethodEncodedValue(BuilderMethodReference builderMethodReference) {
            this.methodReference = builderMethodReference;
        }

        @Override // org.jf.dexlib2.iface.value.MethodEncodedValue
        public BuilderMethodReference getValue() {
            return this.methodReference;
        }
    }

    public static class BuilderNullEncodedValue extends BaseNullEncodedValue implements BuilderEncodedValue {
        public static final BuilderNullEncodedValue INSTANCE = new BuilderNullEncodedValue();

        private BuilderNullEncodedValue() {
        }
    }

    public static class BuilderShortEncodedValue extends ImmutableShortEncodedValue implements BuilderEncodedValue {
        public BuilderShortEncodedValue(short s) {
            super(s);
        }
    }

    public static class BuilderStringEncodedValue extends BaseStringEncodedValue implements BuilderEncodedValue {
        final BuilderStringReference stringReference;

        BuilderStringEncodedValue(BuilderStringReference builderStringReference) {
            this.stringReference = builderStringReference;
        }

        @Override // org.jf.dexlib2.iface.value.StringEncodedValue
        public String getValue() {
            return this.stringReference.getString();
        }
    }

    public static class BuilderTypeEncodedValue extends BaseTypeEncodedValue implements BuilderEncodedValue {
        final BuilderTypeReference typeReference;

        BuilderTypeEncodedValue(BuilderTypeReference builderTypeReference) {
            this.typeReference = builderTypeReference;
        }

        @Override // org.jf.dexlib2.iface.value.TypeEncodedValue
        public String getValue() {
            return this.typeReference.getType();
        }
    }

    public static class BuilderMethodTypeEncodedValue extends BaseMethodTypeEncodedValue implements BuilderEncodedValue {
        final BuilderMethodProtoReference methodProtoReference;

        public BuilderMethodTypeEncodedValue(BuilderMethodProtoReference builderMethodProtoReference) {
            this.methodProtoReference = builderMethodProtoReference;
        }

        @Override // org.jf.dexlib2.iface.value.MethodTypeEncodedValue
        public BuilderMethodProtoReference getValue() {
            return this.methodProtoReference;
        }
    }

    public static class BuilderMethodHandleEncodedValue extends BaseMethodHandleEncodedValue implements BuilderEncodedValue {
        final BuilderMethodHandleReference methodHandleReference;

        public BuilderMethodHandleEncodedValue(BuilderMethodHandleReference builderMethodHandleReference) {
            this.methodHandleReference = builderMethodHandleReference;
        }

        @Override // org.jf.dexlib2.iface.value.MethodHandleEncodedValue
        public BuilderMethodHandleReference getValue() {
            return this.methodHandleReference;
        }
    }
}
