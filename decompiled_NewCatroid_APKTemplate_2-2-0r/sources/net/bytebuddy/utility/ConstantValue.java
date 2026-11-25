package net.bytebuddy.utility;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.constant.ClassConstant;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface ConstantValue {
    TypeDescription getTypeDescription();

    StackManipulation toStackManipulation();

    @HashCodeAndEqualsPlugin.Enhance
    public static class Simple implements ConstantValue {
        private final StackManipulation stackManipulation;
        private final TypeDescription typeDescription;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Simple simple = (Simple) obj;
            return this.stackManipulation.equals(simple.stackManipulation) && this.typeDescription.equals(simple.typeDescription);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.stackManipulation.hashCode()) * 31) + this.typeDescription.hashCode();
        }

        protected Simple(StackManipulation stackManipulation, TypeDescription typeDescription) {
            this.stackManipulation = stackManipulation;
            this.typeDescription = typeDescription;
        }

        public static ConstantValue wrap(Object obj) {
            ConstantValue constantValueWrapOrNull = wrapOrNull(obj);
            if (constantValueWrapOrNull != null) {
                return constantValueWrapOrNull;
            }
            throw new IllegalArgumentException("Not a constant value: " + obj);
        }

        @MaybeNull
        public static ConstantValue wrapOrNull(Object obj) {
            if (obj instanceof ConstantValue) {
                return (ConstantValue) obj;
            }
            if (obj instanceof TypeDescription) {
                TypeDescription typeDescription = (TypeDescription) obj;
                if (typeDescription.isPrimitive()) {
                    return new Simple(ClassConstant.of(typeDescription), TypeDescription.ForLoadedType.of(Class.class));
                }
                return JavaConstant.Simple.of(typeDescription);
            }
            if (obj instanceof EnumerationDescription) {
                EnumerationDescription enumerationDescription = (EnumerationDescription) obj;
                return new Simple(FieldAccess.forEnumeration(enumerationDescription), enumerationDescription.getEnumerationType());
            }
            if (obj instanceof Boolean) {
                return new Simple(IntegerConstant.forValue(((Boolean) obj).booleanValue()), TypeDescription.ForLoadedType.of(Boolean.TYPE));
            }
            if (obj instanceof Byte) {
                return new Simple(IntegerConstant.forValue(((Byte) obj).byteValue()), TypeDescription.ForLoadedType.of(Byte.TYPE));
            }
            if (obj instanceof Short) {
                return new Simple(IntegerConstant.forValue(((Short) obj).shortValue()), TypeDescription.ForLoadedType.of(Short.TYPE));
            }
            if (obj instanceof Character) {
                return new Simple(IntegerConstant.forValue(((Character) obj).charValue()), TypeDescription.ForLoadedType.of(Character.TYPE));
            }
            if (obj instanceof Class) {
                Class cls = (Class) obj;
                if (cls.isPrimitive()) {
                    return new Simple(ClassConstant.of(TypeDescription.ForLoadedType.of(cls)), TypeDescription.ForLoadedType.of(Class.class));
                }
                return JavaConstant.Simple.of(TypeDescription.ForLoadedType.of(cls));
            }
            if (obj instanceof Enum) {
                Enum r2 = (Enum) obj;
                return new Simple(FieldAccess.forEnumeration(new EnumerationDescription.ForLoadedEnumeration(r2)), TypeDescription.ForLoadedType.of(r2.getDeclaringClass()));
            }
            return JavaConstant.Simple.ofLoadedOrNull(obj);
        }

        @Override // net.bytebuddy.utility.ConstantValue
        public TypeDescription getTypeDescription() {
            return this.typeDescription;
        }

        @Override // net.bytebuddy.utility.ConstantValue
        public StackManipulation toStackManipulation() {
            return this.stackManipulation;
        }
    }
}
