package net.bytebuddy.implementation;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.List;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.scaffold.FieldLocator;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.Removal;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.collection.ArrayFactory;
import net.bytebuddy.implementation.bytecode.constant.MethodConstant;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public abstract class InvocationHandlerAdapter implements Implementation.Composable {
    private static final boolean CACHED = true;
    private static final boolean DROPPING = false;
    private static final TypeDescription.Generic INVOCATION_HANDLER_TYPE = TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(InvocationHandler.class);
    private static final boolean PRIVILEGED = true;
    private static final boolean RETURNING = true;
    private static final boolean UNCACHED = false;
    private static final boolean UNPRIVILEGED = false;
    protected final Assigner assigner;
    protected final boolean cached;
    protected final String fieldName;
    protected final boolean privileged;
    protected final boolean returning;

    public interface AssignerConfigurable extends Implementation.Composable {
        Implementation.Composable withAssigner(Assigner assigner);
    }

    public interface WithoutPrivilegeConfiguration extends AssignerConfigurable {
        AssignerConfigurable withPrivilegedLookup();
    }

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InvocationHandlerAdapter invocationHandlerAdapter = (InvocationHandlerAdapter) obj;
        return this.cached == invocationHandlerAdapter.cached && this.privileged == invocationHandlerAdapter.privileged && this.returning == invocationHandlerAdapter.returning && this.fieldName.equals(invocationHandlerAdapter.fieldName) && this.assigner.equals(invocationHandlerAdapter.assigner);
    }

    public int hashCode() {
        return (((((((((getClass().hashCode() * 31) + this.fieldName.hashCode()) * 31) + (this.cached ? 1 : 0)) * 31) + (this.privileged ? 1 : 0)) * 31) + (this.returning ? 1 : 0)) * 31) + this.assigner.hashCode();
    }

    public abstract Implementation withAssigner(Assigner assigner);

    public abstract AssignerConfigurable withPrivilegedLookup();

    public abstract WithoutPrivilegeConfiguration withoutMethodCache();

    protected InvocationHandlerAdapter(String str, boolean z, boolean z2, boolean z3, Assigner assigner) {
        this.fieldName = str;
        this.cached = z;
        this.privileged = z2;
        this.returning = z3;
        this.assigner = assigner;
    }

    public static InvocationHandlerAdapter of(InvocationHandler invocationHandler) {
        return of(invocationHandler, "invocationHandler$" + RandomString.hashOf(invocationHandler));
    }

    public static InvocationHandlerAdapter of(InvocationHandler invocationHandler, String str) {
        return new ForInstance(str, true, false, true, Assigner.DEFAULT, invocationHandler);
    }

    public static InvocationHandlerAdapter toField(String str) {
        return toField(str, FieldLocator.ForClassHierarchy.Factory.INSTANCE);
    }

    public static InvocationHandlerAdapter toField(String str, FieldLocator.Factory factory) {
        return new ForField(str, true, false, true, Assigner.DEFAULT, factory);
    }

    private List<StackManipulation> argumentValuesOf(MethodDescription methodDescription) {
        TypeList.Generic<TypeDescription.Generic> genericAsTypeList = methodDescription.getParameters().asTypeList();
        ArrayList arrayList = new ArrayList(genericAsTypeList.size());
        int size = 1;
        for (TypeDescription.Generic generic : genericAsTypeList) {
            arrayList.add(new StackManipulation.Compound(MethodVariableAccess.of(generic).loadFrom(size), this.assigner.assign(generic, TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Object.class), Assigner.Typing.STATIC)));
            size += generic.getStackSize().getSize();
        }
        return arrayList;
    }

    protected ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription, StackManipulation stackManipulation, FieldDescription fieldDescription) {
        MethodConstant.CanCache canCacheOf;
        if (methodDescription.isStatic() || methodDescription.isConstructor()) {
            throw new IllegalStateException("It is not possible to apply an invocation handler onto the static method or constructor " + methodDescription);
        }
        if (this.privileged) {
            canCacheOf = MethodConstant.ofPrivileged(methodDescription.asDefined());
        } else {
            canCacheOf = MethodConstant.of(methodDescription.asDefined());
        }
        StackManipulation[] stackManipulationArr = new StackManipulation[7];
        stackManipulationArr[0] = stackManipulation;
        stackManipulationArr[1] = FieldAccess.forField(fieldDescription).read();
        stackManipulationArr[2] = MethodVariableAccess.loadThis();
        MethodConstant.CanCache canCacheCached = canCacheOf;
        if (this.cached) {
            canCacheCached = canCacheOf.cached();
        }
        stackManipulationArr[3] = canCacheCached;
        stackManipulationArr[4] = methodDescription.getParameters().isEmpty() ? NullConstant.INSTANCE : ArrayFactory.forType(TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Object.class)).withValues(argumentValuesOf(methodDescription));
        stackManipulationArr[5] = MethodInvocation.invoke((MethodDescription) INVOCATION_HANDLER_TYPE.getDeclaredMethods().filter(ElementMatchers.isAbstract()).getOnly());
        stackManipulationArr[6] = this.returning ? new StackManipulation.Compound(this.assigner.assign(TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Object.class), methodDescription.getReturnType(), Assigner.Typing.DYNAMIC), MethodReturn.of(methodDescription.getReturnType())) : Removal.SINGLE;
        return new ByteCodeAppender.Size(new StackManipulation.Compound(stackManipulationArr).apply(methodVisitor, context).getMaximalSize(), methodDescription.getStackSize());
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ForInstance extends InvocationHandlerAdapter implements WithoutPrivilegeConfiguration {
        private static final String PREFIX = "invocationHandler";
        protected final InvocationHandler invocationHandler;

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.invocationHandler.equals(((ForInstance) obj).invocationHandler);
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter
        public int hashCode() {
            return (super.hashCode() * 31) + this.invocationHandler.hashCode();
        }

        protected ForInstance(String str, boolean z, boolean z2, boolean z3, Assigner assigner, InvocationHandler invocationHandler) {
            super(str, z, z2, z3, assigner);
            this.invocationHandler = invocationHandler;
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter
        public WithoutPrivilegeConfiguration withoutMethodCache() {
            return new ForInstance(this.fieldName, false, this.privileged, this.returning, this.assigner, this.invocationHandler);
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter, net.bytebuddy.implementation.InvocationHandlerAdapter.AssignerConfigurable
        public Implementation.Composable withAssigner(Assigner assigner) {
            return new ForInstance(this.fieldName, this.cached, this.privileged, this.returning, assigner, this.invocationHandler);
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter, net.bytebuddy.implementation.InvocationHandlerAdapter.WithoutPrivilegeConfiguration
        public AssignerConfigurable withPrivilegedLookup() {
            return new ForInstance(this.fieldName, this.cached, true, this.returning, this.assigner, this.invocationHandler);
        }

        @Override // net.bytebuddy.implementation.Implementation.Composable
        public Implementation andThen(Implementation implementation) {
            return new Implementation.Compound(new ForInstance(this.fieldName, this.cached, this.privileged, false, this.assigner, this.invocationHandler), implementation);
        }

        @Override // net.bytebuddy.implementation.Implementation.Composable
        public Implementation.Composable andThen(Implementation.Composable composable) {
            return new Implementation.Compound.Composable(new ForInstance(this.fieldName, this.cached, this.privileged, false, this.assigner, this.invocationHandler), composable);
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            if (!instrumentedType.getDeclaredFields().filter(ElementMatchers.named(this.fieldName).and(ElementMatchers.fieldType(InvocationHandlerAdapter.INVOCATION_HANDLER_TYPE.asErasure()))).isEmpty()) {
                throw new IllegalStateException("Field with name " + this.fieldName + " and type " + InvocationHandlerAdapter.INVOCATION_HANDLER_TYPE.asErasure() + " already declared by " + instrumentedType);
            }
            return instrumentedType.withField(new FieldDescription.Token(this.fieldName, 4169, InvocationHandlerAdapter.INVOCATION_HANDLER_TYPE)).withInitializer(new LoadedTypeInitializer.ForStaticField(this.fieldName, this.invocationHandler));
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return new Appender(target.getInstrumentedType());
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class Appender implements ByteCodeAppender {
            private final TypeDescription instrumentedType;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Appender appender = (Appender) obj;
                return this.instrumentedType.equals(appender.instrumentedType) && ForInstance.this.equals(ForInstance.this);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31) + ForInstance.this.hashCode();
            }

            protected Appender(TypeDescription typeDescription) {
                this.instrumentedType = typeDescription;
            }

            @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
            public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                return ForInstance.this.apply(methodVisitor, context, methodDescription, StackManipulation.Trivial.INSTANCE, (FieldDescription) this.instrumentedType.getDeclaredFields().filter(ElementMatchers.named(ForInstance.this.fieldName).and(ElementMatchers.genericFieldType(InvocationHandlerAdapter.INVOCATION_HANDLER_TYPE))).getOnly());
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ForField extends InvocationHandlerAdapter implements WithoutPrivilegeConfiguration {
        private final FieldLocator.Factory fieldLocatorFactory;

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.fieldLocatorFactory.equals(((ForField) obj).fieldLocatorFactory);
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter
        public int hashCode() {
            return (super.hashCode() * 31) + this.fieldLocatorFactory.hashCode();
        }

        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        protected ForField(String str, boolean z, boolean z2, boolean z3, Assigner assigner, FieldLocator.Factory factory) {
            super(str, z, z2, z3, assigner);
            this.fieldLocatorFactory = factory;
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter
        public WithoutPrivilegeConfiguration withoutMethodCache() {
            return new ForField(this.fieldName, false, this.privileged, this.returning, this.assigner, this.fieldLocatorFactory);
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter, net.bytebuddy.implementation.InvocationHandlerAdapter.AssignerConfigurable
        public Implementation.Composable withAssigner(Assigner assigner) {
            return new ForField(this.fieldName, this.cached, this.privileged, this.returning, assigner, this.fieldLocatorFactory);
        }

        @Override // net.bytebuddy.implementation.InvocationHandlerAdapter, net.bytebuddy.implementation.InvocationHandlerAdapter.WithoutPrivilegeConfiguration
        public AssignerConfigurable withPrivilegedLookup() {
            return new ForField(this.fieldName, this.cached, true, this.returning, this.assigner, this.fieldLocatorFactory);
        }

        @Override // net.bytebuddy.implementation.Implementation.Composable
        public Implementation andThen(Implementation implementation) {
            return new Implementation.Compound(new ForField(this.fieldName, this.cached, this.privileged, false, this.assigner, this.fieldLocatorFactory), implementation);
        }

        @Override // net.bytebuddy.implementation.Implementation.Composable
        public Implementation.Composable andThen(Implementation.Composable composable) {
            return new Implementation.Compound.Composable(new ForField(this.fieldName, this.cached, this.privileged, false, this.assigner, this.fieldLocatorFactory), composable);
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            FieldLocator.Resolution resolutionLocate = this.fieldLocatorFactory.make(target.getInstrumentedType()).locate(this.fieldName);
            if (!resolutionLocate.isResolved()) {
                throw new IllegalStateException("Could not find a field named '" + this.fieldName + "' for " + target.getInstrumentedType());
            }
            if (!resolutionLocate.getField().getType().asErasure().isAssignableTo(InvocationHandler.class)) {
                throw new IllegalStateException("Field " + resolutionLocate.getField() + " does not declare a type that is assignable to invocation handler");
            }
            return new Appender(resolutionLocate.getField());
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class Appender implements ByteCodeAppender {
            private final FieldDescription fieldDescription;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Appender appender = (Appender) obj;
                return this.fieldDescription.equals(appender.fieldDescription) && ForField.this.equals(ForField.this);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.fieldDescription.hashCode()) * 31) + ForField.this.hashCode();
            }

            protected Appender(FieldDescription fieldDescription) {
                this.fieldDescription = fieldDescription;
            }

            @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
            public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                return ForField.this.apply(methodVisitor, context, methodDescription, this.fieldDescription.isStatic() ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.loadThis(), this.fieldDescription);
            }
        }
    }
}
