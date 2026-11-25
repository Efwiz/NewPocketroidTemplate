package net.bytebuddy.implementation.bytecode.member;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public enum FieldAccess {
    STATIC(179, 178, StackSize.ZERO),
    INSTANCE(181, 180, StackSize.SINGLE);

    private final int getterOpcode;
    private final int putterOpcode;
    private final int targetSizeChange;

    public interface Defined {
        StackManipulation read();

        StackManipulation write();
    }

    FieldAccess(int i, int i2, StackSize stackSize) {
        this.putterOpcode = i;
        this.getterOpcode = i2;
        this.targetSizeChange = stackSize.getSize();
    }

    public static StackManipulation forEnumeration(EnumerationDescription enumerationDescription) {
        FieldList fieldListFilter = enumerationDescription.getEnumerationType().getDeclaredFields().filter(ElementMatchers.named(enumerationDescription.getValue()));
        if (fieldListFilter.size() != 1 || !((FieldDescription.InDefinedShape) fieldListFilter.getOnly()).isStatic() || !((FieldDescription.InDefinedShape) fieldListFilter.getOnly()).isPublic() || !((FieldDescription.InDefinedShape) fieldListFilter.getOnly()).isEnum()) {
            return StackManipulation.Illegal.INSTANCE;
        }
        FieldAccess fieldAccess = STATIC;
        fieldAccess.getClass();
        return fieldAccess.new AccessDispatcher((FieldDescription.InDefinedShape) fieldListFilter.getOnly()).read();
    }

    public static Defined forField(FieldDescription.InDefinedShape inDefinedShape) {
        if (inDefinedShape.isStatic()) {
            FieldAccess fieldAccess = STATIC;
            fieldAccess.getClass();
            return fieldAccess.new AccessDispatcher(inDefinedShape);
        }
        FieldAccess fieldAccess2 = INSTANCE;
        fieldAccess2.getClass();
        return fieldAccess2.new AccessDispatcher(inDefinedShape);
    }

    public static Defined forField(FieldDescription fieldDescription) {
        FieldDescription.InDefinedShape inDefinedShapeAsDefined = fieldDescription.asDefined();
        if (fieldDescription.getType().asErasure().equals(inDefinedShapeAsDefined.getType().asErasure())) {
            return forField(inDefinedShapeAsDefined);
        }
        return OfGenericField.of(fieldDescription, forField(inDefinedShapeAsDefined));
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class OfGenericField implements Defined {
        private final Defined defined;
        private final TypeDefinition targetType;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            OfGenericField ofGenericField = (OfGenericField) obj;
            return this.targetType.equals(ofGenericField.targetType) && this.defined.equals(ofGenericField.defined);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.targetType.hashCode()) * 31) + this.defined.hashCode();
        }

        protected OfGenericField(TypeDefinition typeDefinition, Defined defined) {
            this.targetType = typeDefinition;
            this.defined = defined;
        }

        protected static Defined of(FieldDescription fieldDescription, Defined defined) {
            return new OfGenericField(fieldDescription.getType(), defined);
        }

        @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.Defined
        public StackManipulation read() {
            return new StackManipulation.Compound(this.defined.read(), TypeCasting.to(this.targetType));
        }

        @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.Defined
        public StackManipulation write() {
            return this.defined.write();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    protected class AccessDispatcher implements Defined {
        private final FieldDescription.InDefinedShape fieldDescription;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AccessDispatcher accessDispatcher = (AccessDispatcher) obj;
            return FieldAccess.this.equals(FieldAccess.this) && this.fieldDescription.equals(accessDispatcher.fieldDescription);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.fieldDescription.hashCode()) * 31) + FieldAccess.this.hashCode();
        }

        protected AccessDispatcher(FieldDescription.InDefinedShape inDefinedShape) {
            this.fieldDescription = inDefinedShape;
        }

        @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.Defined
        public StackManipulation read() {
            return new FieldGetInstruction();
        }

        @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.Defined
        public StackManipulation write() {
            return new FieldPutInstruction();
        }

        private abstract class AbstractFieldInstruction extends StackManipulation.AbstractBase {
            protected abstract int getOpcode();

            protected abstract StackManipulation.Size resolveSize(StackSize stackSize);

            private AbstractFieldInstruction() {
            }

            @Override // net.bytebuddy.implementation.bytecode.StackManipulation
            public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
                methodVisitor.visitFieldInsn(getOpcode(), AccessDispatcher.this.fieldDescription.getDeclaringType().getInternalName(), AccessDispatcher.this.fieldDescription.getInternalName(), AccessDispatcher.this.fieldDescription.getDescriptor());
                return resolveSize(AccessDispatcher.this.fieldDescription.getType().getStackSize());
            }
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class FieldGetInstruction extends AbstractFieldInstruction {
            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && AccessDispatcher.this.equals(AccessDispatcher.this);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + AccessDispatcher.this.hashCode();
            }

            protected FieldGetInstruction() {
                super();
            }

            @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.AccessDispatcher.AbstractFieldInstruction
            protected int getOpcode() {
                return FieldAccess.this.getterOpcode;
            }

            @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.AccessDispatcher.AbstractFieldInstruction
            protected StackManipulation.Size resolveSize(StackSize stackSize) {
                int size = stackSize.getSize() - FieldAccess.this.targetSizeChange;
                return new StackManipulation.Size(size, size);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class FieldPutInstruction extends AbstractFieldInstruction {
            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && AccessDispatcher.this.equals(AccessDispatcher.this);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + AccessDispatcher.this.hashCode();
            }

            protected FieldPutInstruction() {
                super();
            }

            @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.AccessDispatcher.AbstractFieldInstruction
            protected int getOpcode() {
                return FieldAccess.this.putterOpcode;
            }

            @Override // net.bytebuddy.implementation.bytecode.member.FieldAccess.AccessDispatcher.AbstractFieldInstruction
            protected StackManipulation.Size resolveSize(StackSize stackSize) {
                return new StackManipulation.Size((stackSize.getSize() + FieldAccess.this.targetSizeChange) * (-1), 0);
            }
        }
    }
}
