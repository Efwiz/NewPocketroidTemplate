package net.bytebuddy.implementation.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaConstant;
import net.bytebuddy.utility.JavaType;

/* JADX INFO: loaded from: classes12.dex */
@Target({ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldSetterHandle {
    Class<?> declaringType() default void.class;

    String value() default "";

    public enum Binder implements TargetMethodAnnotationDrivenBinder.ParameterBinder<FieldSetterHandle> {
        INSTANCE(new Delegate());

        private static final MethodDescription.InDefinedShape DECLARING_TYPE;
        private static final MethodDescription.InDefinedShape FIELD_NAME;
        private final TargetMethodAnnotationDrivenBinder.ParameterBinder<FieldSetterHandle> delegate;

        static {
            MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(FieldSetterHandle.class).getDeclaredMethods();
            DECLARING_TYPE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("declaringType")).getOnly();
            FIELD_NAME = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("value")).getOnly();
        }

        Binder(TargetMethodAnnotationDrivenBinder.ParameterBinder parameterBinder) {
            this.delegate = parameterBinder;
        }

        @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
        public Class<FieldSetterHandle> getHandledType() {
            return this.delegate.getHandledType();
        }

        @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
        public MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<FieldSetterHandle> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner, Assigner.Typing typing) {
            return this.delegate.bind(loadable, methodDescription, parameterDescription, target, assigner, typing);
        }

        protected static class Delegate extends TargetMethodAnnotationDrivenBinder.ParameterBinder.ForFieldBinding<FieldSetterHandle> {
            protected Delegate() {
            }

            @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
            public Class<FieldSetterHandle> getHandledType() {
                return FieldSetterHandle.class;
            }

            @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder.ForFieldBinding
            protected String fieldName(AnnotationDescription.Loadable<FieldSetterHandle> loadable) {
                return (String) loadable.getValue(Binder.FIELD_NAME).resolve(String.class);
            }

            @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder.ForFieldBinding
            protected TypeDescription declaringType(AnnotationDescription.Loadable<FieldSetterHandle> loadable) {
                return (TypeDescription) loadable.getValue(Binder.DECLARING_TYPE).resolve(TypeDescription.class);
            }

            @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder.ForFieldBinding
            protected MethodDelegationBinder.ParameterBinding<?> bind(FieldDescription fieldDescription, AnnotationDescription.Loadable<FieldSetterHandle> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner) {
                if (!parameterDescription.getType().asErasure().isAssignableFrom(JavaType.METHOD_HANDLE.getTypeStub())) {
                    throw new IllegalStateException("Cannot assign method handle to " + parameterDescription);
                }
                if (fieldDescription.isStatic()) {
                    return new MethodDelegationBinder.ParameterBinding.Anonymous(JavaConstant.MethodHandle.ofSetter(fieldDescription.asDefined()).toStackManipulation());
                }
                return new MethodDelegationBinder.ParameterBinding.Anonymous(new StackManipulation.Compound(JavaConstant.MethodHandle.ofSetter(fieldDescription.asDefined()).toStackManipulation(), MethodVariableAccess.loadThis(), MethodInvocation.invoke((MethodDescription.InDefinedShape) new MethodDescription.Latent(JavaType.METHOD_HANDLE.getTypeStub(), new MethodDescription.Token("bindTo", 1, JavaType.METHOD_HANDLE.getTypeStub().asGenericType(), new TypeList.Generic.Explicit(TypeDefinition.Sort.describe(Object.class)))))));
            }
        }
    }
}
