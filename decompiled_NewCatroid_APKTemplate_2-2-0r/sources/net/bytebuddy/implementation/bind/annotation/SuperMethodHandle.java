package net.bytebuddy.implementation.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaType;

/* JADX INFO: loaded from: classes12.dex */
@Target({ElementType.PARAMETER})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SuperMethodHandle {
    boolean fallbackToDefault() default true;

    boolean nullIfImpossible() default false;

    public enum Binder implements TargetMethodAnnotationDrivenBinder.ParameterBinder<SuperMethodHandle> {
        INSTANCE;

        private static final MethodDescription.InDefinedShape FALLBACK_TO_DEFAULT;
        private static final MethodDescription.InDefinedShape NULL_IF_IMPOSSIBLE;

        static {
            MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(SuperMethodHandle.class).getDeclaredMethods();
            FALLBACK_TO_DEFAULT = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("fallbackToDefault")).getOnly();
            NULL_IF_IMPOSSIBLE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("nullIfImpossible")).getOnly();
        }

        @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
        public Class<SuperMethodHandle> getHandledType() {
            return SuperMethodHandle.class;
        }

        @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
        public MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<SuperMethodHandle> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner, Assigner.Typing typing) {
            Implementation.SpecialMethodInvocation specialMethodInvocationInvokeSuper;
            if (!parameterDescription.getType().asErasure().isAssignableFrom(JavaType.METHOD_HANDLE.getTypeStub())) {
                throw new IllegalStateException("Cannot assign MethodHandle type to " + parameterDescription);
            }
            if (methodDescription.isMethod()) {
                if (((Boolean) loadable.getValue(FALLBACK_TO_DEFAULT).resolve(Boolean.class)).booleanValue()) {
                    specialMethodInvocationInvokeSuper = target.invokeDominant(methodDescription.asSignatureToken());
                } else {
                    specialMethodInvocationInvokeSuper = target.invokeSuper(methodDescription.asSignatureToken());
                }
                Implementation.SpecialMethodInvocation specialMethodInvocationWithCheckedCompatibilityTo = specialMethodInvocationInvokeSuper.withCheckedCompatibilityTo(methodDescription.asTypeToken());
                if (specialMethodInvocationWithCheckedCompatibilityTo.isValid()) {
                    return new MethodDelegationBinder.ParameterBinding.Anonymous(specialMethodInvocationWithCheckedCompatibilityTo.toMethodHandle().toStackManipulation());
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
    }
}
