package net.bytebuddy.implementation;

import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.ClassConstant;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.ConstantValue;
import net.bytebuddy.utility.JavaConstant;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public abstract class FixedValue implements Implementation {
    protected final Assigner assigner;
    protected final Assigner.Typing typing;

    public interface AssignerConfigurable extends Implementation {
        Implementation withAssigner(Assigner assigner, Assigner.Typing typing);
    }

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FixedValue fixedValue = (FixedValue) obj;
        return this.typing.equals(fixedValue.typing) && this.assigner.equals(fixedValue.assigner);
    }

    public int hashCode() {
        return (((getClass().hashCode() * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode();
    }

    protected FixedValue(Assigner assigner, Assigner.Typing typing) {
        this.assigner = assigner;
        this.typing = typing;
    }

    public static AssignerConfigurable value(Object obj) {
        ConstantValue constantValueWrapOrNull = ConstantValue.Simple.wrapOrNull(obj);
        if (constantValueWrapOrNull == null) {
            return reference(obj);
        }
        return new ForConstantValue(constantValueWrapOrNull.toStackManipulation(), constantValueWrapOrNull.getTypeDescription());
    }

    public static AssignerConfigurable reference(Object obj) {
        return reference(obj, "value$" + RandomString.hashOf(obj));
    }

    public static AssignerConfigurable reference(Object obj, String str) {
        return new ForValue(obj, str);
    }

    public static AssignerConfigurable value(TypeDescription typeDescription) {
        return new ForConstantValue(ClassConstant.of(typeDescription), TypeDescription.ForLoadedType.of(Class.class));
    }

    public static AssignerConfigurable value(ConstantValue constantValue) {
        return new ForConstantValue(constantValue.toStackManipulation(), constantValue.getTypeDescription());
    }

    public static AssignerConfigurable value(JavaConstant javaConstant) {
        return value((ConstantValue) javaConstant);
    }

    public static AssignerConfigurable argument(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Argument index cannot be negative: " + i);
        }
        return new ForArgument(i);
    }

    public static AssignerConfigurable self() {
        return new ForThisValue();
    }

    public static Implementation nullValue() {
        return ForNullValue.INSTANCE;
    }

    public static AssignerConfigurable originType() {
        return new ForOriginType();
    }

    protected ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription, TypeDescription.Generic generic, StackManipulation stackManipulation) {
        StackManipulation stackManipulationAssign = this.assigner.assign(generic, methodDescription.getReturnType(), this.typing);
        if (!stackManipulationAssign.isValid()) {
            throw new IllegalArgumentException("Cannot return value of type " + generic + " for " + methodDescription);
        }
        return new ByteCodeAppender.Size(new StackManipulation.Compound(stackManipulation, stackManipulationAssign, MethodReturn.of(methodDescription.getReturnType())).apply(methodVisitor, context).getMaximalSize(), methodDescription.getStackSize());
    }

    protected enum ForNullValue implements Implementation, ByteCodeAppender {
        INSTANCE;

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return this;
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            if (methodDescription.getReturnType().isPrimitive()) {
                throw new IllegalStateException("Cannot return null from " + methodDescription);
            }
            return new ByteCodeAppender.Simple(NullConstant.INSTANCE, MethodReturn.REFERENCE).apply(methodVisitor, context, methodDescription);
        }
    }

    protected static class ForOriginType extends FixedValue implements AssignerConfigurable {
        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        protected ForOriginType() {
            this(Assigner.DEFAULT, Assigner.Typing.STATIC);
        }

        private ForOriginType(Assigner assigner, Assigner.Typing typing) {
            super(assigner, typing);
        }

        @Override // net.bytebuddy.implementation.FixedValue.AssignerConfigurable
        public Implementation withAssigner(Assigner assigner, Assigner.Typing typing) {
            return new ForOriginType(assigner, typing);
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return new Appender(target.getOriginType().asErasure());
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class Appender implements ByteCodeAppender {
            private final TypeDescription originType;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Appender appender = (Appender) obj;
                return this.originType.equals(appender.originType) && ForOriginType.this.equals(ForOriginType.this);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.originType.hashCode()) * 31) + ForOriginType.this.hashCode();
            }

            protected Appender(TypeDescription typeDescription) {
                this.originType = typeDescription;
            }

            @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
            public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                return ForOriginType.this.apply(methodVisitor, context, methodDescription, TypeDescription.ForLoadedType.of(Class.class).asGenericType(), ClassConstant.of(this.originType));
            }
        }
    }

    protected static class ForThisValue extends FixedValue implements AssignerConfigurable {
        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        protected ForThisValue() {
            super(Assigner.DEFAULT, Assigner.Typing.STATIC);
        }

        private ForThisValue(Assigner assigner, Assigner.Typing typing) {
            super(assigner, typing);
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return new Appender(target.getInstrumentedType());
        }

        @Override // net.bytebuddy.implementation.FixedValue.AssignerConfigurable
        public Implementation withAssigner(Assigner assigner, Assigner.Typing typing) {
            return new ForThisValue(assigner, typing);
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class Appender implements ByteCodeAppender {
            private final TypeDescription instrumentedType;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.instrumentedType.equals(((Appender) obj).instrumentedType);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.instrumentedType.hashCode();
            }

            protected Appender(TypeDescription typeDescription) {
                this.instrumentedType = typeDescription;
            }

            @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
            public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                if (methodDescription.isStatic() || !this.instrumentedType.isAssignableTo(methodDescription.getReturnType().asErasure())) {
                    throw new IllegalStateException("Cannot return 'this' from " + methodDescription);
                }
                return new ByteCodeAppender.Simple(MethodVariableAccess.loadThis(), MethodReturn.REFERENCE).apply(methodVisitor, context, methodDescription);
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ForArgument extends FixedValue implements AssignerConfigurable, ByteCodeAppender {
        private final int index;

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return this;
        }

        @Override // net.bytebuddy.implementation.FixedValue
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.index == ((ForArgument) obj).index;
        }

        @Override // net.bytebuddy.implementation.FixedValue
        public int hashCode() {
            return (super.hashCode() * 31) + this.index;
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        protected ForArgument(int i) {
            this(Assigner.DEFAULT, Assigner.Typing.STATIC, i);
        }

        private ForArgument(Assigner assigner, Assigner.Typing typing, int i) {
            super(assigner, typing);
            this.index = i;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            if (methodDescription.getParameters().size() <= this.index) {
                throw new IllegalStateException(methodDescription + " does not define a parameter with index " + this.index);
            }
            ParameterDescription parameterDescription = (ParameterDescription) methodDescription.getParameters().get(this.index);
            StackManipulation.Compound compound = new StackManipulation.Compound(MethodVariableAccess.load(parameterDescription), this.assigner.assign(parameterDescription.getType(), methodDescription.getReturnType(), this.typing), MethodReturn.of(methodDescription.getReturnType()));
            if (!compound.isValid()) {
                throw new IllegalStateException("Cannot assign " + methodDescription.getReturnType() + " to " + parameterDescription);
            }
            return new ByteCodeAppender.Size(compound.apply(methodVisitor, context).getMaximalSize(), methodDescription.getStackSize());
        }

        @Override // net.bytebuddy.implementation.FixedValue.AssignerConfigurable
        public Implementation withAssigner(Assigner assigner, Assigner.Typing typing) {
            return new ForArgument(assigner, typing, this.index);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ForConstantValue extends FixedValue implements AssignerConfigurable, ByteCodeAppender {
        private final TypeDescription loadedType;
        private final StackManipulation valueLoadInstruction;

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return this;
        }

        @Override // net.bytebuddy.implementation.FixedValue
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ForConstantValue forConstantValue = (ForConstantValue) obj;
            return this.valueLoadInstruction.equals(forConstantValue.valueLoadInstruction) && this.loadedType.equals(forConstantValue.loadedType);
        }

        @Override // net.bytebuddy.implementation.FixedValue
        public int hashCode() {
            return (((super.hashCode() * 31) + this.valueLoadInstruction.hashCode()) * 31) + this.loadedType.hashCode();
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        protected ForConstantValue(StackManipulation stackManipulation, Class<?> cls) {
            this(stackManipulation, TypeDescription.ForLoadedType.of(cls));
        }

        protected ForConstantValue(StackManipulation stackManipulation, TypeDescription typeDescription) {
            this(Assigner.DEFAULT, Assigner.Typing.STATIC, stackManipulation, typeDescription);
        }

        private ForConstantValue(Assigner assigner, Assigner.Typing typing, StackManipulation stackManipulation, TypeDescription typeDescription) {
            super(assigner, typing);
            this.valueLoadInstruction = stackManipulation;
            this.loadedType = typeDescription;
        }

        @Override // net.bytebuddy.implementation.FixedValue.AssignerConfigurable
        public Implementation withAssigner(Assigner assigner, Assigner.Typing typing) {
            return new ForConstantValue(assigner, typing, this.valueLoadInstruction, this.loadedType);
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            return apply(methodVisitor, context, methodDescription, this.loadedType.asGenericType(), this.valueLoadInstruction);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ForValue extends FixedValue implements AssignerConfigurable {
        private static final String PREFIX = "value";

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f364name;
        private final Object value;

        @Override // net.bytebuddy.implementation.FixedValue
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ForValue forValue = (ForValue) obj;
            return this.f364name.equals(forValue.f364name) && this.value.equals(forValue.value);
        }

        @Override // net.bytebuddy.implementation.FixedValue
        public int hashCode() {
            return (((super.hashCode() * 31) + this.f364name.hashCode()) * 31) + this.value.hashCode();
        }

        protected ForValue(Object obj, String str) {
            this(Assigner.DEFAULT, Assigner.Typing.STATIC, obj, str);
        }

        private ForValue(Assigner assigner, Assigner.Typing typing, Object obj, String str) {
            super(assigner, typing);
            this.f364name = str;
            this.value = obj;
        }

        @Override // net.bytebuddy.implementation.FixedValue.AssignerConfigurable
        public Implementation withAssigner(Assigner assigner, Assigner.Typing typing) {
            return new ForValue(assigner, typing, this.value, this.f364name);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType.withAuxiliaryField(new FieldDescription.Token(this.f364name, 4169, TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(this.value.getClass())), this.value);
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return new StaticFieldByteCodeAppender(target.getInstrumentedType());
        }

        @HashCodeAndEqualsPlugin.Enhance
        private class StaticFieldByteCodeAppender implements ByteCodeAppender {
            private final StackManipulation fieldGetAccess;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.fieldGetAccess.equals(((StaticFieldByteCodeAppender) obj).fieldGetAccess);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.fieldGetAccess.hashCode();
            }

            private StaticFieldByteCodeAppender(TypeDescription typeDescription) {
                this.fieldGetAccess = FieldAccess.forField((FieldDescription.InDefinedShape) typeDescription.getDeclaredFields().filter(ElementMatchers.named(ForValue.this.f364name)).getOnly()).read();
            }

            @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
            public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                ForValue forValue = ForValue.this;
                return forValue.apply(methodVisitor, context, methodDescription, TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(forValue.value.getClass()), this.fieldGetAccess);
            }
        }
    }
}
