package net.bytebuddy.implementation.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodAccessorFactory;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.MethodConstant;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@Target({ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultMethod {
    boolean cached() default true;

    boolean nullIfImpossible() default false;

    boolean privileged() default false;

    Class<?> targetType() default void.class;

    public enum Binder implements TargetMethodAnnotationDrivenBinder.ParameterBinder<DefaultMethod> {
        INSTANCE;

        private static final MethodDescription.InDefinedShape CACHED;
        private static final MethodDescription.InDefinedShape NULL_IF_IMPOSSIBLE;
        private static final MethodDescription.InDefinedShape PRIVILEGED;
        private static final MethodDescription.InDefinedShape TARGET_TYPE;

        static {
            MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(DefaultMethod.class).getDeclaredMethods();
            CACHED = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("cached")).getOnly();
            PRIVILEGED = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("privileged")).getOnly();
            TARGET_TYPE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("targetType")).getOnly();
            NULL_IF_IMPOSSIBLE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("nullIfImpossible")).getOnly();
        }

        @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
        public Class<DefaultMethod> getHandledType() {
            return DefaultMethod.class;
        }

        @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
        public MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<DefaultMethod> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner, Assigner.Typing typing) {
            if (!parameterDescription.getType().asErasure().isAssignableFrom(Method.class)) {
                throw new IllegalStateException("Cannot assign Method type to " + parameterDescription);
            }
            if (methodDescription.isMethod()) {
                TypeDescription typeDescription = (TypeDescription) loadable.getValue(TARGET_TYPE).resolve(TypeDescription.class);
                Implementation.SpecialMethodInvocation specialMethodInvocationWithCheckedCompatibilityTo = (typeDescription.represents(Void.TYPE) ? MethodLocator.ForImplicitType.INSTANCE : new MethodLocator.ForExplicitType(typeDescription)).resolve(target, methodDescription).withCheckedCompatibilityTo(methodDescription.asTypeToken());
                if (specialMethodInvocationWithCheckedCompatibilityTo.isValid()) {
                    return new MethodDelegationBinder.ParameterBinding.Anonymous(new DelegationMethod(specialMethodInvocationWithCheckedCompatibilityTo, ((Boolean) loadable.getValue(CACHED).resolve(Boolean.class)).booleanValue(), ((Boolean) loadable.getValue(PRIVILEGED).resolve(Boolean.class)).booleanValue()));
                }
                if (((Boolean) loadable.getValue(NULL_IF_IMPOSSIBLE).resolve(Boolean.class)).booleanValue()) {
                    return new MethodDelegationBinder.ParameterBinding.Anonymous(NullConstant.INSTANCE);
                }
                return MethodDelegationBinder.ParameterBinding.Illegal.INSTANCE;
            }
            if (((Boolean) loadable.getValue(NULL_IF_IMPOSSIBLE).resolve(Boolean.class)).booleanValue()) {
                return new MethodDelegationBinder.ParameterBinding.Anonymous(NullConstant.INSTANCE);
            }
            return MethodDelegationBinder.ParameterBinding.Illegal.INSTANCE;
        }

        protected interface MethodLocator {
            Implementation.SpecialMethodInvocation resolve(Implementation.Target target, MethodDescription methodDescription);

            public enum ForImplicitType implements MethodLocator {
                INSTANCE;

                @Override // net.bytebuddy.implementation.bind.annotation.DefaultMethod.Binder.MethodLocator
                public Implementation.SpecialMethodInvocation resolve(Implementation.Target target, MethodDescription methodDescription) {
                    return target.invokeDefault(methodDescription.asSignatureToken());
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForExplicitType implements MethodLocator {
                private final TypeDescription typeDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.typeDescription.equals(((ForExplicitType) obj).typeDescription);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.typeDescription.hashCode();
                }

                protected ForExplicitType(TypeDescription typeDescription) {
                    this.typeDescription = typeDescription;
                }

                @Override // net.bytebuddy.implementation.bind.annotation.DefaultMethod.Binder.MethodLocator
                public Implementation.SpecialMethodInvocation resolve(Implementation.Target target, MethodDescription methodDescription) {
                    if (!this.typeDescription.isInterface()) {
                        throw new IllegalStateException(methodDescription + " method carries default method call parameter on non-interface type");
                    }
                    return target.invokeDefault(methodDescription.asSignatureToken(), TargetType.resolve(this.typeDescription, target.getInstrumentedType()));
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class DelegationMethod implements StackManipulation {
            private final boolean cached;
            private final boolean privileged;
            private final Implementation.SpecialMethodInvocation specialMethodInvocation;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                DelegationMethod delegationMethod = (DelegationMethod) obj;
                return this.cached == delegationMethod.cached && this.privileged == delegationMethod.privileged && this.specialMethodInvocation.equals(delegationMethod.specialMethodInvocation);
            }

            public int hashCode() {
                return (((((getClass().hashCode() * 31) + this.specialMethodInvocation.hashCode()) * 31) + (this.cached ? 1 : 0)) * 31) + (this.privileged ? 1 : 0);
            }

            protected DelegationMethod(Implementation.SpecialMethodInvocation specialMethodInvocation, boolean z, boolean z2) {
                this.specialMethodInvocation = specialMethodInvocation;
                this.cached = z;
                this.privileged = z2;
            }

            @Override // net.bytebuddy.implementation.bytecode.StackManipulation
            public boolean isValid() {
                return this.specialMethodInvocation.isValid();
            }

            @Override // net.bytebuddy.implementation.bytecode.StackManipulation
            public StackManipulation.Size apply(MethodVisitor methodVisitor, Implementation.Context context) {
                StackManipulation stackManipulationOf;
                if (this.privileged) {
                    stackManipulationOf = MethodConstant.ofPrivileged(context.registerAccessorFor(this.specialMethodInvocation, MethodAccessorFactory.AccessType.PUBLIC));
                } else {
                    stackManipulationOf = MethodConstant.of(context.registerAccessorFor(this.specialMethodInvocation, MethodAccessorFactory.AccessType.PUBLIC));
                }
                if (this.cached) {
                    stackManipulationOf = FieldAccess.forField(context.cache(stackManipulationOf, TypeDescription.ForLoadedType.of(Method.class))).read();
                }
                return stackManipulationOf.apply(methodVisitor, context);
            }
        }
    }
}
