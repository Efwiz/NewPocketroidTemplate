package net.bytebuddy.implementation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.scaffold.FieldLocator;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import net.bytebuddy.implementation.bind.annotation.TargetMethodAnnotationDrivenBinder;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.Duplication;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.TypeCreation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.CompoundList;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class MethodDelegation implements Implementation.Composable {
    private final MethodDelegationBinder.AmbiguityResolver ambiguityResolver;
    private final Assigner assigner;
    private final MethodDelegationBinder.BindingResolver bindingResolver;
    private final ImplementationDelegate implementationDelegate;
    private final List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> parameterBinders;
    private final MethodDelegationBinder.TerminationHandler terminationHandler;

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MethodDelegation methodDelegation = (MethodDelegation) obj;
        return this.implementationDelegate.equals(methodDelegation.implementationDelegate) && this.parameterBinders.equals(methodDelegation.parameterBinders) && this.ambiguityResolver.equals(methodDelegation.ambiguityResolver) && this.terminationHandler.equals(methodDelegation.terminationHandler) && this.bindingResolver.equals(methodDelegation.bindingResolver) && this.assigner.equals(methodDelegation.assigner);
    }

    public int hashCode() {
        return (((((((((((getClass().hashCode() * 31) + this.implementationDelegate.hashCode()) * 31) + this.parameterBinders.hashCode()) * 31) + this.ambiguityResolver.hashCode()) * 31) + this.terminationHandler.hashCode()) * 31) + this.bindingResolver.hashCode()) * 31) + this.assigner.hashCode();
    }

    protected MethodDelegation(ImplementationDelegate implementationDelegate, List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list, MethodDelegationBinder.AmbiguityResolver ambiguityResolver, MethodDelegationBinder.BindingResolver bindingResolver) {
        this(implementationDelegate, list, ambiguityResolver, MethodDelegationBinder.TerminationHandler.Default.RETURNING, bindingResolver, Assigner.DEFAULT);
    }

    private MethodDelegation(ImplementationDelegate implementationDelegate, List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list, MethodDelegationBinder.AmbiguityResolver ambiguityResolver, MethodDelegationBinder.TerminationHandler terminationHandler, MethodDelegationBinder.BindingResolver bindingResolver, Assigner assigner) {
        this.implementationDelegate = implementationDelegate;
        this.parameterBinders = list;
        this.terminationHandler = terminationHandler;
        this.ambiguityResolver = ambiguityResolver;
        this.bindingResolver = bindingResolver;
        this.assigner = assigner;
    }

    public static MethodDelegation to(Class<?> cls) {
        return withDefaultConfiguration().to(cls);
    }

    public static MethodDelegation to(TypeDescription typeDescription) {
        return withDefaultConfiguration().to(typeDescription);
    }

    public static MethodDelegation to(Object obj) {
        return withDefaultConfiguration().to(obj);
    }

    public static MethodDelegation to(Object obj, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().to(obj, compiler);
    }

    public static MethodDelegation to(Object obj, String str) {
        return withDefaultConfiguration().to(obj, str);
    }

    public static MethodDelegation to(Object obj, String str, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().to(obj, str, compiler);
    }

    public static MethodDelegation to(Object obj, Type type) {
        return withDefaultConfiguration().to(obj, type);
    }

    public static MethodDelegation to(Object obj, Type type, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().to(obj, type, compiler);
    }

    public static MethodDelegation to(Object obj, Type type, String str) {
        return withDefaultConfiguration().to(obj, type, str);
    }

    public static MethodDelegation to(Object obj, Type type, String str, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().to(obj, type, str, compiler);
    }

    public static MethodDelegation to(Object obj, TypeDefinition typeDefinition) {
        return withDefaultConfiguration().to(obj, typeDefinition);
    }

    public static MethodDelegation to(Object obj, TypeDefinition typeDefinition, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().to(obj, typeDefinition, compiler);
    }

    public static MethodDelegation to(Object obj, TypeDefinition typeDefinition, String str) {
        return withDefaultConfiguration().to(obj, typeDefinition, str);
    }

    public static MethodDelegation to(Object obj, TypeDefinition typeDefinition, String str, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().to(obj, typeDefinition, str, compiler);
    }

    public static MethodDelegation toConstructor(Class<?> cls) {
        return withDefaultConfiguration().toConstructor(cls);
    }

    public static MethodDelegation toConstructor(TypeDescription typeDescription) {
        return withDefaultConfiguration().toConstructor(typeDescription);
    }

    public static MethodDelegation toField(String str) {
        return withDefaultConfiguration().toField(str);
    }

    public static MethodDelegation toField(String str, FieldLocator.Factory factory) {
        return withDefaultConfiguration().toField(str, factory);
    }

    public static MethodDelegation toField(String str, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().toField(str, compiler);
    }

    public static MethodDelegation toField(String str, FieldLocator.Factory factory, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().toField(str, factory, compiler);
    }

    public static MethodDelegation toMethodReturnOf(String str) {
        return withDefaultConfiguration().toMethodReturnOf(str);
    }

    public static MethodDelegation toMethodReturnOf(String str, MethodGraph.Compiler compiler) {
        return withDefaultConfiguration().toMethodReturnOf(str, compiler);
    }

    public static WithCustomProperties withDefaultConfiguration() {
        return new WithCustomProperties(MethodDelegationBinder.AmbiguityResolver.DEFAULT, TargetMethodAnnotationDrivenBinder.ParameterBinder.DEFAULTS);
    }

    public static WithCustomProperties withEmptyConfiguration() {
        return new WithCustomProperties(MethodDelegationBinder.AmbiguityResolver.NoOp.INSTANCE, Collections.emptyList());
    }

    public Implementation.Composable withAssigner(Assigner assigner) {
        return new MethodDelegation(this.implementationDelegate, this.parameterBinders, this.ambiguityResolver, this.terminationHandler, this.bindingResolver, assigner);
    }

    @Override // net.bytebuddy.implementation.Implementation.Composable
    public Implementation andThen(Implementation implementation) {
        return new Implementation.Compound(new MethodDelegation(this.implementationDelegate, this.parameterBinders, this.ambiguityResolver, MethodDelegationBinder.TerminationHandler.Default.DROPPING, this.bindingResolver, this.assigner), implementation);
    }

    @Override // net.bytebuddy.implementation.Implementation.Composable
    public Implementation.Composable andThen(Implementation.Composable composable) {
        return new Implementation.Compound.Composable(new MethodDelegation(this.implementationDelegate, this.parameterBinders, this.ambiguityResolver, MethodDelegationBinder.TerminationHandler.Default.DROPPING, this.bindingResolver, this.assigner), composable);
    }

    @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
    public InstrumentedType prepare(InstrumentedType instrumentedType) {
        return this.implementationDelegate.prepare(instrumentedType);
    }

    @Override // net.bytebuddy.implementation.Implementation
    public ByteCodeAppender appender(Implementation.Target target) {
        ImplementationDelegate.Compiled compiledCompile = this.implementationDelegate.compile(target.getInstrumentedType());
        return new Appender(target, new MethodDelegationBinder.Processor(compiledCompile.getRecords(), this.ambiguityResolver, this.bindingResolver), this.terminationHandler, this.assigner, compiledCompile);
    }

    protected interface ImplementationDelegate extends InstrumentedType.Prepareable {
        public static final String FIELD_NAME_PREFIX = "delegate";

        Compiled compile(TypeDescription typeDescription);

        public interface Compiled {
            List<MethodDelegationBinder.Record> getRecords();

            MethodDelegationBinder.MethodInvoker invoke();

            StackManipulation prepare(MethodDescription methodDescription);

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForStaticCall implements Compiled {
                private final List<MethodDelegationBinder.Record> records;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.records.equals(((ForStaticCall) obj).records);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.records.hashCode();
                }

                protected ForStaticCall(List<MethodDelegationBinder.Record> list) {
                    this.records = list;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public StackManipulation prepare(MethodDescription methodDescription) {
                    return StackManipulation.Trivial.INSTANCE;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public MethodDelegationBinder.MethodInvoker invoke() {
                    return MethodDelegationBinder.MethodInvoker.Simple.INSTANCE;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public List<MethodDelegationBinder.Record> getRecords() {
                    return this.records;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForField implements Compiled {
                private final FieldDescription fieldDescription;
                private final List<MethodDelegationBinder.Record> records;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    ForField forField = (ForField) obj;
                    return this.fieldDescription.equals(forField.fieldDescription) && this.records.equals(forField.records);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.fieldDescription.hashCode()) * 31) + this.records.hashCode();
                }

                protected ForField(FieldDescription fieldDescription, List<MethodDelegationBinder.Record> list) {
                    this.fieldDescription = fieldDescription;
                    this.records = list;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public StackManipulation prepare(MethodDescription methodDescription) {
                    if (methodDescription.isStatic() && !this.fieldDescription.isStatic()) {
                        throw new IllegalStateException("Cannot read " + this.fieldDescription + " from " + methodDescription);
                    }
                    StackManipulation[] stackManipulationArr = new StackManipulation[2];
                    stackManipulationArr[0] = this.fieldDescription.isStatic() ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.loadThis();
                    stackManipulationArr[1] = FieldAccess.forField(this.fieldDescription).read();
                    return new StackManipulation.Compound(stackManipulationArr);
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public MethodDelegationBinder.MethodInvoker invoke() {
                    return new MethodDelegationBinder.MethodInvoker.Virtual(this.fieldDescription.getType().asErasure());
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public List<MethodDelegationBinder.Record> getRecords() {
                    return this.records;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForMethodReturn implements Compiled {
                private final MethodDescription methodDescription;
                private final List<MethodDelegationBinder.Record> records;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    ForMethodReturn forMethodReturn = (ForMethodReturn) obj;
                    return this.methodDescription.equals(forMethodReturn.methodDescription) && this.records.equals(forMethodReturn.records);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.methodDescription.hashCode()) * 31) + this.records.hashCode();
                }

                protected ForMethodReturn(MethodDescription methodDescription, List<MethodDelegationBinder.Record> list) {
                    this.methodDescription = methodDescription;
                    this.records = list;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public StackManipulation prepare(MethodDescription methodDescription) {
                    if (methodDescription.isStatic() && !this.methodDescription.isStatic()) {
                        throw new IllegalStateException("Cannot invoke " + this.methodDescription + " from " + methodDescription);
                    }
                    StackManipulation[] stackManipulationArr = new StackManipulation[2];
                    stackManipulationArr[0] = this.methodDescription.isStatic() ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.loadThis();
                    stackManipulationArr[1] = MethodInvocation.invoke(this.methodDescription);
                    return new StackManipulation.Compound(stackManipulationArr);
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public MethodDelegationBinder.MethodInvoker invoke() {
                    return new MethodDelegationBinder.MethodInvoker.Virtual(this.methodDescription.getReturnType().asErasure());
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public List<MethodDelegationBinder.Record> getRecords() {
                    return this.records;
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForConstruction implements Compiled {
                private final List<MethodDelegationBinder.Record> records;
                private final TypeDescription typeDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    ForConstruction forConstruction = (ForConstruction) obj;
                    return this.typeDescription.equals(forConstruction.typeDescription) && this.records.equals(forConstruction.records);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.typeDescription.hashCode()) * 31) + this.records.hashCode();
                }

                protected ForConstruction(TypeDescription typeDescription, List<MethodDelegationBinder.Record> list) {
                    this.typeDescription = typeDescription;
                    this.records = list;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public StackManipulation prepare(MethodDescription methodDescription) {
                    return new StackManipulation.Compound(TypeCreation.of(this.typeDescription), Duplication.SINGLE);
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public MethodDelegationBinder.MethodInvoker invoke() {
                    return MethodDelegationBinder.MethodInvoker.Simple.INSTANCE;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.Compiled
                public List<MethodDelegationBinder.Record> getRecords() {
                    return this.records;
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForStaticMethod implements ImplementationDelegate {
            private final List<MethodDelegationBinder.Record> records;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.records.equals(((ForStaticMethod) obj).records);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.records.hashCode();
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            protected ForStaticMethod(List<MethodDelegationBinder.Record> list) {
                this.records = list;
            }

            protected static ImplementationDelegate of(MethodList<?> methodList, MethodDelegationBinder methodDelegationBinder) {
                ArrayList arrayList = new ArrayList(methodList.size());
                Iterator it = methodList.iterator();
                while (it.hasNext()) {
                    arrayList.add(methodDelegationBinder.compile((MethodDescription) it.next()));
                }
                return new ForStaticMethod(arrayList);
            }

            @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate
            public Compiled compile(TypeDescription typeDescription) {
                return new Compiled.ForStaticCall(this.records);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static abstract class ForField implements ImplementationDelegate {
            protected final String fieldName;
            protected final ElementMatcher<? super MethodDescription> matcher;
            protected final MethodGraph.Compiler methodGraphCompiler;
            protected final List<? extends TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> parameterBinders;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForField forField = (ForField) obj;
                return this.fieldName.equals(forField.fieldName) && this.methodGraphCompiler.equals(forField.methodGraphCompiler) && this.parameterBinders.equals(forField.parameterBinders) && this.matcher.equals(forField.matcher);
            }

            public int hashCode() {
                return (((((((getClass().hashCode() * 31) + this.fieldName.hashCode()) * 31) + this.methodGraphCompiler.hashCode()) * 31) + this.parameterBinders.hashCode()) * 31) + this.matcher.hashCode();
            }

            protected abstract FieldDescription resolve(TypeDescription typeDescription);

            protected ForField(String str, MethodGraph.Compiler compiler, List<? extends TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list, ElementMatcher<? super MethodDescription> elementMatcher) {
                this.fieldName = str;
                this.methodGraphCompiler = compiler;
                this.parameterBinders = list;
                this.matcher = elementMatcher;
            }

            @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate
            public Compiled compile(TypeDescription typeDescription) {
                FieldDescription fieldDescriptionResolve = resolve(typeDescription);
                if (!fieldDescriptionResolve.getType().asErasure().isVisibleTo(typeDescription)) {
                    throw new IllegalStateException(fieldDescriptionResolve + " is not visible to " + typeDescription);
                }
                MethodList methodListFilter = this.methodGraphCompiler.compile(fieldDescriptionResolve.getType(), typeDescription).listNodes().asMethodList().filter(this.matcher);
                ArrayList arrayList = new ArrayList(methodListFilter.size());
                MethodDelegationBinder methodDelegationBinderOf = TargetMethodAnnotationDrivenBinder.of(this.parameterBinders);
                Iterator it = methodListFilter.iterator();
                while (it.hasNext()) {
                    arrayList.add(methodDelegationBinderOf.compile((MethodDescription) it.next()));
                }
                return new Compiled.ForField(fieldDescriptionResolve, arrayList);
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class WithInstance extends ForField {
                private final TypeDescription.Generic fieldType;
                private final Object target;

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.ForField
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
                    WithInstance withInstance = (WithInstance) obj;
                    return this.target.equals(withInstance.target) && this.fieldType.equals(withInstance.fieldType);
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.ForField
                public int hashCode() {
                    return (((super.hashCode() * 31) + this.target.hashCode()) * 31) + this.fieldType.hashCode();
                }

                protected WithInstance(String str, MethodGraph.Compiler compiler, List<? extends TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list, ElementMatcher<? super MethodDescription> elementMatcher, Object obj, TypeDescription.Generic generic) {
                    super(str, compiler, list, elementMatcher);
                    this.target = obj;
                    this.fieldType = generic;
                }

                @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
                public InstrumentedType prepare(InstrumentedType instrumentedType) {
                    return instrumentedType.withAuxiliaryField(new FieldDescription.Token(this.fieldName, 4169, this.fieldType), this.target);
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.ForField
                protected FieldDescription resolve(TypeDescription typeDescription) {
                    if (!this.fieldType.asErasure().isVisibleTo(typeDescription)) {
                        throw new IllegalStateException(this.fieldType + " is not visible to " + typeDescription);
                    }
                    return (FieldDescription) typeDescription.getDeclaredFields().filter(ElementMatchers.named(this.fieldName).and(ElementMatchers.fieldType(this.fieldType.asErasure()))).getOnly();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class WithLookup extends ForField {
                private final FieldLocator.Factory fieldLocatorFactory;

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.ForField
                public boolean equals(@MaybeNull Object obj) {
                    if (!super.equals(obj)) {
                        return false;
                    }
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.fieldLocatorFactory.equals(((WithLookup) obj).fieldLocatorFactory);
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.ForField
                public int hashCode() {
                    return (super.hashCode() * 31) + this.fieldLocatorFactory.hashCode();
                }

                @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
                public InstrumentedType prepare(InstrumentedType instrumentedType) {
                    return instrumentedType;
                }

                protected WithLookup(String str, MethodGraph.Compiler compiler, List<? extends TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list, ElementMatcher<? super MethodDescription> elementMatcher, FieldLocator.Factory factory) {
                    super(str, compiler, list, elementMatcher);
                    this.fieldLocatorFactory = factory;
                }

                @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate.ForField
                protected FieldDescription resolve(TypeDescription typeDescription) {
                    FieldLocator.Resolution resolutionLocate = this.fieldLocatorFactory.make(typeDescription).locate(this.fieldName);
                    if (!resolutionLocate.isResolved()) {
                        throw new IllegalStateException("Could not locate " + this.fieldName + " on " + typeDescription);
                    }
                    return resolutionLocate.getField();
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForMethodReturn implements ImplementationDelegate {
            private final ElementMatcher<? super MethodDescription> matcher;
            private final MethodGraph.Compiler methodGraphCompiler;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f374name;
            private final List<? extends TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> parameterBinders;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForMethodReturn forMethodReturn = (ForMethodReturn) obj;
                return this.f374name.equals(forMethodReturn.f374name) && this.methodGraphCompiler.equals(forMethodReturn.methodGraphCompiler) && this.parameterBinders.equals(forMethodReturn.parameterBinders) && this.matcher.equals(forMethodReturn.matcher);
            }

            public int hashCode() {
                return (((((((getClass().hashCode() * 31) + this.f374name.hashCode()) * 31) + this.methodGraphCompiler.hashCode()) * 31) + this.parameterBinders.hashCode()) * 31) + this.matcher.hashCode();
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            protected ForMethodReturn(String str, MethodGraph.Compiler compiler, List<? extends TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list, ElementMatcher<? super MethodDescription> elementMatcher) {
                this.f374name = str;
                this.methodGraphCompiler = compiler;
                this.parameterBinders = list;
                this.matcher = elementMatcher;
            }

            @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate
            public Compiled compile(TypeDescription typeDescription) {
                MethodList methodListFilter = new MethodList.Explicit(CompoundList.of(typeDescription.getDeclaredMethods().filter(ElementMatchers.isStatic().or(ElementMatchers.isPrivate())), (List) this.methodGraphCompiler.compile((TypeDefinition) typeDescription).listNodes().asMethodList())).filter(ElementMatchers.named(this.f374name).and(ElementMatchers.takesArguments(0)).and(ElementMatchers.not(ElementMatchers.returns(ElementMatchers.isPrimitive().or(ElementMatchers.isArray())))));
                if (methodListFilter.size() != 1) {
                    throw new IllegalStateException(typeDescription + " does not define method without arguments with name " + this.f374name + ": " + methodListFilter);
                }
                if (!((MethodDescription) methodListFilter.getOnly()).getReturnType().asErasure().isVisibleTo(typeDescription)) {
                    throw new IllegalStateException(methodListFilter.getOnly() + " is not visible to " + typeDescription);
                }
                MethodList methodListFilter2 = this.methodGraphCompiler.compile(((MethodDescription) methodListFilter.getOnly()).getReturnType(), typeDescription).listNodes().asMethodList().filter(this.matcher);
                ArrayList arrayList = new ArrayList(methodListFilter2.size());
                MethodDelegationBinder methodDelegationBinderOf = TargetMethodAnnotationDrivenBinder.of(this.parameterBinders);
                Iterator it = methodListFilter2.iterator();
                while (it.hasNext()) {
                    arrayList.add(methodDelegationBinderOf.compile((MethodDescription) it.next()));
                }
                return new Compiled.ForMethodReturn((MethodDescription) methodListFilter.get(0), arrayList);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForConstruction implements ImplementationDelegate {
            private final List<MethodDelegationBinder.Record> records;
            private final TypeDescription typeDescription;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForConstruction forConstruction = (ForConstruction) obj;
                return this.typeDescription.equals(forConstruction.typeDescription) && this.records.equals(forConstruction.records);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.typeDescription.hashCode()) * 31) + this.records.hashCode();
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            protected ForConstruction(TypeDescription typeDescription, List<MethodDelegationBinder.Record> list) {
                this.typeDescription = typeDescription;
                this.records = list;
            }

            protected static ImplementationDelegate of(TypeDescription typeDescription, MethodList<?> methodList, MethodDelegationBinder methodDelegationBinder) {
                ArrayList arrayList = new ArrayList(methodList.size());
                Iterator it = methodList.iterator();
                while (it.hasNext()) {
                    arrayList.add(methodDelegationBinder.compile((MethodDescription) it.next()));
                }
                return new ForConstruction(typeDescription, arrayList);
            }

            @Override // net.bytebuddy.implementation.MethodDelegation.ImplementationDelegate
            public Compiled compile(TypeDescription typeDescription) {
                return new Compiled.ForConstruction(this.typeDescription, this.records);
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class Appender implements ByteCodeAppender {
        private final Assigner assigner;
        private final ImplementationDelegate.Compiled compiled;
        private final Implementation.Target implementationTarget;
        private final MethodDelegationBinder.Record processor;
        private final MethodDelegationBinder.TerminationHandler terminationHandler;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Appender appender = (Appender) obj;
            return this.implementationTarget.equals(appender.implementationTarget) && this.processor.equals(appender.processor) && this.terminationHandler.equals(appender.terminationHandler) && this.assigner.equals(appender.assigner) && this.compiled.equals(appender.compiled);
        }

        public int hashCode() {
            return (((((((((getClass().hashCode() * 31) + this.implementationTarget.hashCode()) * 31) + this.processor.hashCode()) * 31) + this.terminationHandler.hashCode()) * 31) + this.assigner.hashCode()) * 31) + this.compiled.hashCode();
        }

        protected Appender(Implementation.Target target, MethodDelegationBinder.Record record, MethodDelegationBinder.TerminationHandler terminationHandler, Assigner assigner, ImplementationDelegate.Compiled compiled) {
            this.implementationTarget = target;
            this.processor = record;
            this.terminationHandler = terminationHandler;
            this.assigner = assigner;
            this.compiled = compiled;
        }

        @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
        public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
            return new ByteCodeAppender.Size(new StackManipulation.Compound(this.compiled.prepare(methodDescription), this.processor.bind(this.implementationTarget, methodDescription, this.terminationHandler, this.compiled.invoke(), this.assigner)).apply(methodVisitor, context).getMaximalSize(), methodDescription.getStackSize());
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class WithCustomProperties {
        private final MethodDelegationBinder.AmbiguityResolver ambiguityResolver;
        private final MethodDelegationBinder.BindingResolver bindingResolver;
        private final ElementMatcher<? super MethodDescription> matcher;
        private final List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> parameterBinders;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            WithCustomProperties withCustomProperties = (WithCustomProperties) obj;
            return this.ambiguityResolver.equals(withCustomProperties.ambiguityResolver) && this.parameterBinders.equals(withCustomProperties.parameterBinders) && this.bindingResolver.equals(withCustomProperties.bindingResolver) && this.matcher.equals(withCustomProperties.matcher);
        }

        public int hashCode() {
            return (((((((getClass().hashCode() * 31) + this.ambiguityResolver.hashCode()) * 31) + this.parameterBinders.hashCode()) * 31) + this.bindingResolver.hashCode()) * 31) + this.matcher.hashCode();
        }

        protected WithCustomProperties(MethodDelegationBinder.AmbiguityResolver ambiguityResolver, List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list) {
            this(ambiguityResolver, list, MethodDelegationBinder.BindingResolver.Default.INSTANCE, ElementMatchers.any());
        }

        private WithCustomProperties(MethodDelegationBinder.AmbiguityResolver ambiguityResolver, List<TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list, MethodDelegationBinder.BindingResolver bindingResolver, ElementMatcher<? super MethodDescription> elementMatcher) {
            this.ambiguityResolver = ambiguityResolver;
            this.parameterBinders = list;
            this.bindingResolver = bindingResolver;
            this.matcher = elementMatcher;
        }

        public WithCustomProperties withResolvers(MethodDelegationBinder.AmbiguityResolver... ambiguityResolverArr) {
            return withResolvers(Arrays.asList(ambiguityResolverArr));
        }

        public WithCustomProperties withResolvers(List<? extends MethodDelegationBinder.AmbiguityResolver> list) {
            return new WithCustomProperties(new MethodDelegationBinder.AmbiguityResolver.Compound((List<? extends MethodDelegationBinder.AmbiguityResolver>) CompoundList.of(this.ambiguityResolver, list)), this.parameterBinders, this.bindingResolver, this.matcher);
        }

        public WithCustomProperties withBinders(TargetMethodAnnotationDrivenBinder.ParameterBinder<?>... parameterBinderArr) {
            return withBinders(Arrays.asList(parameterBinderArr));
        }

        public WithCustomProperties withBinders(List<? extends TargetMethodAnnotationDrivenBinder.ParameterBinder<?>> list) {
            return new WithCustomProperties(this.ambiguityResolver, CompoundList.of((List) this.parameterBinders, (List) list), this.bindingResolver, this.matcher);
        }

        public WithCustomProperties withBindingResolver(MethodDelegationBinder.BindingResolver bindingResolver) {
            return new WithCustomProperties(this.ambiguityResolver, this.parameterBinders, bindingResolver, this.matcher);
        }

        public WithCustomProperties filter(ElementMatcher<? super MethodDescription> elementMatcher) {
            return new WithCustomProperties(this.ambiguityResolver, this.parameterBinders, this.bindingResolver, new ElementMatcher.Junction.Conjunction(this.matcher, elementMatcher));
        }

        public MethodDelegation to(Class<?> cls) {
            return to(TypeDescription.ForLoadedType.of(cls));
        }

        public MethodDelegation to(TypeDescription typeDescription) {
            if (typeDescription.isArray()) {
                throw new IllegalArgumentException("Cannot delegate to array " + typeDescription);
            }
            if (typeDescription.isPrimitive()) {
                throw new IllegalArgumentException("Cannot delegate to primitive " + typeDescription);
            }
            return new MethodDelegation(ImplementationDelegate.ForStaticMethod.of(typeDescription.getDeclaredMethods().filter(ElementMatchers.isStatic().and(this.matcher)), TargetMethodAnnotationDrivenBinder.of(this.parameterBinders)), this.parameterBinders, this.ambiguityResolver, this.bindingResolver);
        }

        public MethodDelegation to(Object obj) {
            return to(obj, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation to(Object obj, MethodGraph.Compiler compiler) {
            return to(obj, obj.getClass(), compiler);
        }

        public MethodDelegation to(Object obj, String str) {
            return to(obj, str, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation to(Object obj, String str, MethodGraph.Compiler compiler) {
            return to(obj, obj.getClass(), str, compiler);
        }

        public MethodDelegation to(Object obj, Type type) {
            return to(obj, type, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation to(Object obj, Type type, MethodGraph.Compiler compiler) {
            return to(obj, type, "delegate$" + RandomString.hashOf(obj), compiler);
        }

        public MethodDelegation to(Object obj, Type type, String str) {
            return to(obj, type, str, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation to(Object obj, Type type, String str, MethodGraph.Compiler compiler) {
            return to(obj, TypeDefinition.Sort.describe(type), str, compiler);
        }

        public MethodDelegation to(Object obj, TypeDefinition typeDefinition) {
            return to(obj, typeDefinition, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation to(Object obj, TypeDefinition typeDefinition, MethodGraph.Compiler compiler) {
            return to(obj, typeDefinition, "delegate$" + RandomString.hashOf(obj), compiler);
        }

        public MethodDelegation to(Object obj, TypeDefinition typeDefinition, String str) {
            return to(obj, typeDefinition, str, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation to(Object obj, TypeDefinition typeDefinition, String str, MethodGraph.Compiler compiler) {
            if (!typeDefinition.asErasure().isInstance(obj)) {
                throw new IllegalArgumentException(obj + " is not an instance of " + typeDefinition);
            }
            return new MethodDelegation(new ImplementationDelegate.ForField.WithInstance(str, compiler, this.parameterBinders, this.matcher, obj, typeDefinition.asGenericType()), this.parameterBinders, this.ambiguityResolver, this.bindingResolver);
        }

        public MethodDelegation toConstructor(Class<?> cls) {
            return toConstructor(TypeDescription.ForLoadedType.of(cls));
        }

        public MethodDelegation toConstructor(TypeDescription typeDescription) {
            return new MethodDelegation(ImplementationDelegate.ForConstruction.of(typeDescription, typeDescription.getDeclaredMethods().filter(ElementMatchers.isConstructor().and(this.matcher)), TargetMethodAnnotationDrivenBinder.of(this.parameterBinders)), this.parameterBinders, this.ambiguityResolver, this.bindingResolver);
        }

        public MethodDelegation toField(String str) {
            return toField(str, FieldLocator.ForClassHierarchy.Factory.INSTANCE);
        }

        public MethodDelegation toField(String str, FieldLocator.Factory factory) {
            return toField(str, factory, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation toField(String str, MethodGraph.Compiler compiler) {
            return toField(str, FieldLocator.ForClassHierarchy.Factory.INSTANCE, compiler);
        }

        public MethodDelegation toField(String str, FieldLocator.Factory factory, MethodGraph.Compiler compiler) {
            return new MethodDelegation(new ImplementationDelegate.ForField.WithLookup(str, compiler, this.parameterBinders, this.matcher, factory), this.parameterBinders, this.ambiguityResolver, this.bindingResolver);
        }

        public MethodDelegation toMethodReturnOf(String str) {
            return toMethodReturnOf(str, MethodGraph.Compiler.DEFAULT);
        }

        public MethodDelegation toMethodReturnOf(String str, MethodGraph.Compiler compiler) {
            return new MethodDelegation(new ImplementationDelegate.ForMethodReturn(str, compiler, this.parameterBinders, this.matcher), this.parameterBinders, this.ambiguityResolver, this.bindingResolver);
        }
    }
}
