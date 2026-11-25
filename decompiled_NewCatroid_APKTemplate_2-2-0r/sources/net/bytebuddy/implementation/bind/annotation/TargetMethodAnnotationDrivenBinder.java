package net.bytebuddy.implementation.bind.annotation;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.scaffold.FieldLocator;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import net.bytebuddy.utility.ConstantValue;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class TargetMethodAnnotationDrivenBinder implements MethodDelegationBinder {
    private final DelegationProcessor delegationProcessor;

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.delegationProcessor.equals(((TargetMethodAnnotationDrivenBinder) obj).delegationProcessor);
    }

    public int hashCode() {
        return (getClass().hashCode() * 31) + this.delegationProcessor.hashCode();
    }

    protected TargetMethodAnnotationDrivenBinder(DelegationProcessor delegationProcessor) {
        this.delegationProcessor = delegationProcessor;
    }

    public static MethodDelegationBinder of(List<? extends ParameterBinder<?>> list) {
        return new TargetMethodAnnotationDrivenBinder(DelegationProcessor.of(list));
    }

    @Override // net.bytebuddy.implementation.bind.MethodDelegationBinder
    public MethodDelegationBinder.Record compile(MethodDescription methodDescription) {
        if (IgnoreForBinding.Verifier.check(methodDescription)) {
            return MethodDelegationBinder.Record.Illegal.INSTANCE;
        }
        ArrayList arrayList = new ArrayList(methodDescription.getParameters().size());
        Iterator it = methodDescription.getParameters().iterator();
        while (it.hasNext()) {
            arrayList.add(this.delegationProcessor.prepare((ParameterDescription) it.next()));
        }
        return new Record(methodDescription, arrayList, RuntimeType.Verifier.check(methodDescription));
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class Record implements MethodDelegationBinder.Record {
        private final MethodDescription candidate;
        private final List<DelegationProcessor.Handler> handlers;
        private final Assigner.Typing typing;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Record record = (Record) obj;
            return this.typing.equals(record.typing) && this.candidate.equals(record.candidate) && this.handlers.equals(record.handlers);
        }

        public int hashCode() {
            return (((((getClass().hashCode() * 31) + this.candidate.hashCode()) * 31) + this.handlers.hashCode()) * 31) + this.typing.hashCode();
        }

        protected Record(MethodDescription methodDescription, List<DelegationProcessor.Handler> list, Assigner.Typing typing) {
            this.candidate = methodDescription;
            this.handlers = list;
            this.typing = typing;
        }

        @Override // net.bytebuddy.implementation.bind.MethodDelegationBinder.Record
        public MethodDelegationBinder.MethodBinding bind(Implementation.Target target, MethodDescription methodDescription, MethodDelegationBinder.TerminationHandler terminationHandler, MethodDelegationBinder.MethodInvoker methodInvoker, Assigner assigner) {
            if (!this.candidate.isAccessibleTo(target.getInstrumentedType())) {
                return MethodDelegationBinder.MethodBinding.Illegal.INSTANCE;
            }
            StackManipulation stackManipulationResolve = terminationHandler.resolve(assigner, this.typing, methodDescription, this.candidate);
            if (!stackManipulationResolve.isValid()) {
                return MethodDelegationBinder.MethodBinding.Illegal.INSTANCE;
            }
            MethodDelegationBinder.MethodBinding.Builder builder = new MethodDelegationBinder.MethodBinding.Builder(methodInvoker, this.candidate);
            Iterator<DelegationProcessor.Handler> it = this.handlers.iterator();
            while (it.hasNext()) {
                MethodDelegationBinder.ParameterBinding<?> parameterBindingBind = it.next().bind(methodDescription, target, assigner);
                if (!parameterBindingBind.isValid() || !builder.append(parameterBindingBind)) {
                    return MethodDelegationBinder.MethodBinding.Illegal.INSTANCE;
                }
            }
            return builder.build(stackManipulationResolve);
        }

        public String toString() {
            return this.candidate.toString();
        }
    }

    public interface ParameterBinder<T extends Annotation> {
        public static final List<ParameterBinder<?>> DEFAULTS = Collections.unmodifiableList(Arrays.asList(Argument.Binder.INSTANCE, AllArguments.Binder.INSTANCE, Origin.Binder.INSTANCE, This.Binder.INSTANCE, Super.Binder.INSTANCE, Default.Binder.INSTANCE, SuperCall.Binder.INSTANCE, SuperCallHandle.Binder.INSTANCE, DefaultCall.Binder.INSTANCE, DefaultCallHandle.Binder.INSTANCE, SuperMethod.Binder.INSTANCE, SuperMethodHandle.Binder.INSTANCE, DefaultMethod.Binder.INSTANCE, DefaultMethodHandle.Binder.INSTANCE, FieldValue.Binder.INSTANCE, FieldGetterHandle.Binder.INSTANCE, FieldSetterHandle.Binder.INSTANCE, StubValue.Binder.INSTANCE, Empty.Binder.INSTANCE));

        MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<T> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner, Assigner.Typing typing);

        Class<T> getHandledType();

        public static abstract class ForFixedValue<S extends Annotation> implements ParameterBinder<S> {
            @MaybeNull
            protected abstract Object bind(AnnotationDescription.Loadable<S> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription);

            @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
            public MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<S> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner, Assigner.Typing typing) {
                Object objBind = bind(loadable, methodDescription, parameterDescription);
                if (objBind == null) {
                    return new MethodDelegationBinder.ParameterBinding.Anonymous(DefaultValue.of(parameterDescription.getType()));
                }
                ConstantValue constantValueWrap = ConstantValue.Simple.wrap(objBind);
                return new MethodDelegationBinder.ParameterBinding.Anonymous(new StackManipulation.Compound(constantValueWrap.toStackManipulation(), assigner.assign(constantValueWrap.getTypeDescription().asGenericType(), parameterDescription.getType(), typing)));
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class OfConstant<U extends Annotation> extends ForFixedValue<U> {
                private final Class<U> type;

                @MaybeNull
                @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                private final Object value;

                /* JADX WARN: Code duplicated, block: B:22:0x0032 A[RETURN] */
                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    OfConstant ofConstant = (OfConstant) obj;
                    if (!this.type.equals(ofConstant.type)) {
                        return false;
                    }
                    Object obj2 = this.value;
                    Object obj3 = ofConstant.value;
                    if (obj3 == null) {
                        if (obj2 != null) {
                            return false;
                        }
                        return true;
                    }
                    if (obj2 == null || !obj2.equals(obj3)) {
                        return false;
                    }
                    return true;
                }

                public int hashCode() {
                    int iHashCode = ((getClass().hashCode() * 31) + this.type.hashCode()) * 31;
                    Object obj = this.value;
                    return obj != null ? iHashCode + obj.hashCode() : iHashCode;
                }

                protected OfConstant(Class<U> cls, @MaybeNull Object obj) {
                    this.type = cls;
                    this.value = obj;
                }

                public static <V extends Annotation> ParameterBinder<V> of(Class<V> cls, @MaybeNull Object obj) {
                    return new OfConstant(cls, obj);
                }

                @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
                public Class<U> getHandledType() {
                    return this.type;
                }

                @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder.ForFixedValue
                @MaybeNull
                protected Object bind(AnnotationDescription.Loadable<U> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription) {
                    return this.value;
                }
            }
        }

        public static abstract class ForFieldBinding<S extends Annotation> implements ParameterBinder<S> {
            protected static final String BEAN_PROPERTY = "";

            protected abstract MethodDelegationBinder.ParameterBinding<?> bind(FieldDescription fieldDescription, AnnotationDescription.Loadable<S> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner);

            protected abstract TypeDescription declaringType(AnnotationDescription.Loadable<S> loadable);

            protected abstract String fieldName(AnnotationDescription.Loadable<S> loadable);

            @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.ParameterBinder
            public MethodDelegationBinder.ParameterBinding<?> bind(AnnotationDescription.Loadable<S> loadable, MethodDescription methodDescription, ParameterDescription parameterDescription, Implementation.Target target, Assigner assigner, Assigner.Typing typing) {
                FieldLocator forExactType;
                FieldLocator.Resolution resolutionLocate;
                if (!declaringType(loadable).represents(Void.TYPE)) {
                    if (declaringType(loadable).isPrimitive() || declaringType(loadable).isArray()) {
                        throw new IllegalStateException("A primitive type or array type cannot declare a field: " + methodDescription);
                    }
                    if (!target.getInstrumentedType().isAssignableTo(declaringType(loadable))) {
                        return MethodDelegationBinder.ParameterBinding.Illegal.INSTANCE;
                    }
                }
                if (declaringType(loadable).represents(Void.TYPE)) {
                    forExactType = new FieldLocator.ForClassHierarchy(target.getInstrumentedType());
                } else {
                    forExactType = new FieldLocator.ForExactType(declaringType(loadable), target.getInstrumentedType());
                }
                if (fieldName(loadable).equals("")) {
                    resolutionLocate = FieldLocator.Resolution.Simple.ofBeanAccessor(forExactType, methodDescription);
                } else {
                    resolutionLocate = forExactType.locate(fieldName(loadable));
                }
                return (!resolutionLocate.isResolved() || (methodDescription.isStatic() && !resolutionLocate.getField().isStatic())) ? MethodDelegationBinder.ParameterBinding.Illegal.INSTANCE : bind(resolutionLocate.getField(), loadable, methodDescription, parameterDescription, target, assigner);
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class DelegationProcessor {
        private final Map<? extends TypeDescription, ? extends ParameterBinder<?>> parameterBinders;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.parameterBinders.equals(((DelegationProcessor) obj).parameterBinders);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.parameterBinders.hashCode();
        }

        protected DelegationProcessor(Map<? extends TypeDescription, ? extends ParameterBinder<?>> map) {
            this.parameterBinders = map;
        }

        protected static DelegationProcessor of(List<? extends ParameterBinder<?>> list) {
            HashMap map = new HashMap();
            for (ParameterBinder<?> parameterBinder : list) {
                if (map.put(TypeDescription.ForLoadedType.of(parameterBinder.getHandledType()), parameterBinder) != null) {
                    throw new IllegalArgumentException("Attempt to bind two handlers to " + parameterBinder.getHandledType());
                }
            }
            return new DelegationProcessor(map);
        }

        protected Handler prepare(ParameterDescription parameterDescription) {
            Assigner.Typing typingCheck = RuntimeType.Verifier.check(parameterDescription);
            Handler unbound = new Handler.Unbound(parameterDescription, typingCheck);
            for (AnnotationDescription annotationDescription : parameterDescription.getDeclaredAnnotations()) {
                ParameterBinder<?> parameterBinder = this.parameterBinders.get(annotationDescription.getAnnotationType());
                if (parameterBinder != null && unbound.isBound()) {
                    throw new IllegalStateException("Ambiguous binding for parameter annotated with two handled annotation types");
                }
                if (parameterBinder != null) {
                    unbound = Handler.Bound.of(parameterDescription, parameterBinder, annotationDescription, typingCheck);
                }
            }
            return unbound;
        }

        protected interface Handler {
            MethodDelegationBinder.ParameterBinding<?> bind(MethodDescription methodDescription, Implementation.Target target, Assigner assigner);

            boolean isBound();

            @HashCodeAndEqualsPlugin.Enhance
            public static class Unbound implements Handler {
                private final ParameterDescription target;
                private final Assigner.Typing typing;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Unbound unbound = (Unbound) obj;
                    return this.typing.equals(unbound.typing) && this.target.equals(unbound.target);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.target.hashCode()) * 31) + this.typing.hashCode();
                }

                @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.DelegationProcessor.Handler
                public boolean isBound() {
                    return false;
                }

                protected Unbound(ParameterDescription parameterDescription, Assigner.Typing typing) {
                    this.target = parameterDescription;
                    this.typing = typing;
                }

                @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.DelegationProcessor.Handler
                public MethodDelegationBinder.ParameterBinding<?> bind(MethodDescription methodDescription, Implementation.Target target, Assigner assigner) {
                    return Argument.Binder.INSTANCE.bind(AnnotationDescription.ForLoadedAnnotation.of(new DefaultArgument(this.target.getIndex())), methodDescription, this.target, target, assigner, this.typing);
                }

                protected static class DefaultArgument implements Argument {
                    private static final String BINDING_MECHANIC = "bindingMechanic";
                    private static final String VALUE = "value";
                    private final int parameterIndex;

                    protected DefaultArgument(int i) {
                        this.parameterIndex = i;
                    }

                    @Override // net.bytebuddy.implementation.bind.annotation.Argument
                    public int value() {
                        return this.parameterIndex;
                    }

                    @Override // net.bytebuddy.implementation.bind.annotation.Argument
                    public Argument.BindingMechanic bindingMechanic() {
                        return Argument.BindingMechanic.UNIQUE;
                    }

                    @Override // java.lang.annotation.Annotation
                    public Class<Argument> annotationType() {
                        return Argument.class;
                    }

                    @Override // java.lang.annotation.Annotation
                    public int hashCode() {
                        return ((BINDING_MECHANIC.hashCode() * 127) ^ Argument.BindingMechanic.UNIQUE.hashCode()) + (("value".hashCode() * 127) ^ this.parameterIndex);
                    }

                    @Override // java.lang.annotation.Annotation
                    public boolean equals(@MaybeNull Object obj) {
                        return this == obj || ((obj instanceof Argument) && this.parameterIndex == ((Argument) obj).value());
                    }

                    @Override // java.lang.annotation.Annotation
                    public String toString() {
                        return PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + Argument.class.getName() + "(bindingMechanic=" + Argument.BindingMechanic.UNIQUE + ", value=" + this.parameterIndex + Constant.AFTER_QUTO;
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Bound<T extends Annotation> implements Handler {
                private final AnnotationDescription.Loadable<T> annotation;
                private final ParameterBinder<T> parameterBinder;
                private final ParameterDescription target;
                private final Assigner.Typing typing;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Bound bound = (Bound) obj;
                    return this.typing.equals(bound.typing) && this.target.equals(bound.target) && this.parameterBinder.equals(bound.parameterBinder) && this.annotation.equals(bound.annotation);
                }

                public int hashCode() {
                    return (((((((getClass().hashCode() * 31) + this.target.hashCode()) * 31) + this.parameterBinder.hashCode()) * 31) + this.annotation.hashCode()) * 31) + this.typing.hashCode();
                }

                @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.DelegationProcessor.Handler
                public boolean isBound() {
                    return true;
                }

                protected Bound(ParameterDescription parameterDescription, ParameterBinder<T> parameterBinder, AnnotationDescription.Loadable<T> loadable, Assigner.Typing typing) {
                    this.target = parameterDescription;
                    this.parameterBinder = parameterBinder;
                    this.annotation = loadable;
                    this.typing = typing;
                }

                protected static Handler of(ParameterDescription parameterDescription, ParameterBinder<?> parameterBinder, AnnotationDescription annotationDescription, Assigner.Typing typing) {
                    return new Bound(parameterDescription, parameterBinder, annotationDescription.prepare(parameterBinder.getHandledType()), typing);
                }

                @Override // net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder.DelegationProcessor.Handler
                public MethodDelegationBinder.ParameterBinding<?> bind(MethodDescription methodDescription, Implementation.Target target, Assigner assigner) {
                    return this.parameterBinder.bind(this.annotation, methodDescription, this.target, target, assigner, this.typing);
                }
            }
        }
    }
}
