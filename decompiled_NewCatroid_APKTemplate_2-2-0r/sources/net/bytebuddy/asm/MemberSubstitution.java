package net.bytebuddy.asm;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.dynamic.scaffold.FieldLocator;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.Duplication;
import net.bytebuddy.implementation.bytecode.Removal;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.implementation.bytecode.TypeCreation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.collection.ArrayFactory;
import net.bytebuddy.implementation.bytecode.constant.ClassConstant;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import net.bytebuddy.implementation.bytecode.constant.FieldConstant;
import net.bytebuddy.implementation.bytecode.constant.MethodConstant;
import net.bytebuddy.implementation.bytecode.constant.NullConstant;
import net.bytebuddy.implementation.bytecode.constant.SerializedConstant;
import net.bytebuddy.implementation.bytecode.constant.TextConstant;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodInvocation;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import net.bytebuddy.utility.CompoundList;
import net.bytebuddy.utility.ConstantValue;
import net.bytebuddy.utility.JavaConstant;
import net.bytebuddy.utility.JavaType;
import net.bytebuddy.utility.OpenedClassReader;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.visitor.LocalVariableAwareMethodVisitor;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public class MemberSubstitution implements AsmVisitorWrapper.ForDeclaredMethods.MethodVisitorWrapper {
    protected static final int THIS_REFERENCE = 0;
    private final boolean failIfNoMatch;
    private final MethodGraph.Compiler methodGraphCompiler;
    private final Replacement.Factory replacementFactory;
    private final boolean strict;
    private final TypePoolResolver typePoolResolver;

    @Target({ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface AllArguments {
        boolean includeSelf() default false;

        boolean nullIfEmpty() default false;

        Source source() default Source.SUBSTITUTED_ELEMENT;

        Assigner.Typing typing() default Assigner.Typing.STATIC;
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Argument {
        boolean optional() default false;

        Source source() default Source.SUBSTITUTED_ELEMENT;

        Assigner.Typing typing() default Assigner.Typing.STATIC;

        int value();
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Current {
        Assigner.Typing typing() default Assigner.Typing.STATIC;
    }

    @Target({ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FieldGetterHandle {
        Class<?> declaringType() default void.class;

        String value() default "";
    }

    @Target({ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FieldSetterHandle {
        Class<?> declaringType() default void.class;

        String value() default "";
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FieldValue {
        Class<?> declaringType() default void.class;

        Assigner.Typing typing() default Assigner.Typing.STATIC;

        String value() default "";
    }

    @Target({ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Origin {
        Source source() default Source.SUBSTITUTED_ELEMENT;
    }

    @Target({ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SelfCallHandle {
        boolean bound() default true;

        Source source() default Source.SUBSTITUTED_ELEMENT;
    }

    @Target({ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface StubValue {
        Source source() default Source.SUBSTITUTED_ELEMENT;
    }

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface This {
        boolean optional() default false;

        Source source() default Source.SUBSTITUTED_ELEMENT;

        Assigner.Typing typing() default Assigner.Typing.STATIC;
    }

    @Target({ElementType.PARAMETER})
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Unused {
    }

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MemberSubstitution memberSubstitution = (MemberSubstitution) obj;
        return this.strict == memberSubstitution.strict && this.failIfNoMatch == memberSubstitution.failIfNoMatch && this.methodGraphCompiler.equals(memberSubstitution.methodGraphCompiler) && this.typePoolResolver.equals(memberSubstitution.typePoolResolver) && this.replacementFactory.equals(memberSubstitution.replacementFactory);
    }

    public int hashCode() {
        return (((((((((getClass().hashCode() * 31) + this.methodGraphCompiler.hashCode()) * 31) + this.typePoolResolver.hashCode()) * 31) + (this.strict ? 1 : 0)) * 31) + (this.failIfNoMatch ? 1 : 0)) * 31) + this.replacementFactory.hashCode();
    }

    protected MemberSubstitution(boolean z) {
        this(MethodGraph.Compiler.DEFAULT, TypePoolResolver.OfImplicitPool.INSTANCE, z, false, Replacement.NoOp.INSTANCE);
    }

    protected MemberSubstitution(MethodGraph.Compiler compiler, TypePoolResolver typePoolResolver, boolean z, boolean z2, Replacement.Factory factory) {
        this.methodGraphCompiler = compiler;
        this.typePoolResolver = typePoolResolver;
        this.failIfNoMatch = z2;
        this.strict = z;
        this.replacementFactory = factory;
    }

    public static MemberSubstitution strict() {
        return new MemberSubstitution(true);
    }

    public static MemberSubstitution relaxed() {
        return new MemberSubstitution(false);
    }

    public WithoutSpecification element(ElementMatcher<? super ByteCodeElement.Member> elementMatcher) {
        return new WithoutSpecification.ForMatchedByteCodeElement(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, elementMatcher);
    }

    public WithoutSpecification.ForMatchedField field(ElementMatcher<? super FieldDescription> elementMatcher) {
        return new WithoutSpecification.ForMatchedField(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, elementMatcher);
    }

    public WithoutSpecification.ForMatchedMethod method(ElementMatcher<? super MethodDescription> elementMatcher) {
        return new WithoutSpecification.ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, elementMatcher);
    }

    public WithoutSpecification constructor(ElementMatcher<? super MethodDescription> elementMatcher) {
        return invokable(ElementMatchers.isConstructor().and(elementMatcher));
    }

    public WithoutSpecification invokable(ElementMatcher<? super MethodDescription> elementMatcher) {
        return new WithoutSpecification.ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, elementMatcher);
    }

    public MemberSubstitution with(MethodGraph.Compiler compiler) {
        return new MemberSubstitution(compiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory);
    }

    public MemberSubstitution with(TypePoolResolver typePoolResolver) {
        return new MemberSubstitution(this.methodGraphCompiler, typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory);
    }

    public MemberSubstitution failIfNoMatch(boolean z) {
        return new MemberSubstitution(this.methodGraphCompiler, this.typePoolResolver, this.strict, z, this.replacementFactory);
    }

    public AsmVisitorWrapper.ForDeclaredMethods on(ElementMatcher<? super MethodDescription> elementMatcher) {
        return new AsmVisitorWrapper.ForDeclaredMethods().invokable(elementMatcher, this);
    }

    @Override // net.bytebuddy.asm.AsmVisitorWrapper.ForDeclaredMethods.MethodVisitorWrapper
    public MethodVisitor wrap(TypeDescription typeDescription, MethodDescription methodDescription, MethodVisitor methodVisitor, Implementation.Context context, TypePool typePool, int i, int i2) {
        TypePool typePoolResolve = this.typePoolResolver.resolve(typeDescription, methodDescription, typePool);
        return new SubstitutingMethodVisitor(methodVisitor, typeDescription, methodDescription, this.methodGraphCompiler, this.strict, this.failIfNoMatch, this.replacementFactory.make(typeDescription, methodDescription, typePoolResolve), context, typePoolResolve, context.getClassFileVersion().isAtLeast(ClassFileVersion.JAVA_V11));
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static abstract class WithoutSpecification {
        protected final boolean failIfNoMatch;
        protected final MethodGraph.Compiler methodGraphCompiler;
        protected final Replacement.Factory replacementFactory;
        protected final boolean strict;
        protected final TypePoolResolver typePoolResolver;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            WithoutSpecification withoutSpecification = (WithoutSpecification) obj;
            return this.strict == withoutSpecification.strict && this.failIfNoMatch == withoutSpecification.failIfNoMatch && this.methodGraphCompiler.equals(withoutSpecification.methodGraphCompiler) && this.typePoolResolver.equals(withoutSpecification.typePoolResolver) && this.replacementFactory.equals(withoutSpecification.replacementFactory);
        }

        public int hashCode() {
            return (((((((((getClass().hashCode() * 31) + this.methodGraphCompiler.hashCode()) * 31) + this.typePoolResolver.hashCode()) * 31) + (this.strict ? 1 : 0)) * 31) + (this.failIfNoMatch ? 1 : 0)) * 31) + this.replacementFactory.hashCode();
        }

        public abstract MemberSubstitution replaceWith(Substitution.Factory factory);

        protected WithoutSpecification(MethodGraph.Compiler compiler, TypePoolResolver typePoolResolver, boolean z, boolean z2, Replacement.Factory factory) {
            this.methodGraphCompiler = compiler;
            this.typePoolResolver = typePoolResolver;
            this.strict = z;
            this.failIfNoMatch = z2;
            this.replacementFactory = factory;
        }

        public MemberSubstitution stub() {
            return replaceWith(Substitution.Stubbing.INSTANCE);
        }

        public MemberSubstitution replaceWithConstant(Object obj) {
            ConstantValue constantValueWrap = ConstantValue.Simple.wrap(obj);
            return replaceWith(new Substitution.ForValue(constantValueWrap.toStackManipulation(), constantValueWrap.getTypeDescription().asGenericType()));
        }

        public MemberSubstitution replaceWith(Field field) {
            return replaceWith(new FieldDescription.ForLoadedField(field));
        }

        public MemberSubstitution replaceWith(FieldDescription fieldDescription) {
            return replaceWith(new Substitution.ForFieldAccess.OfGivenField(fieldDescription));
        }

        public MemberSubstitution replaceWithField(ElementMatcher<? super FieldDescription> elementMatcher) {
            return replaceWith(new Substitution.ForFieldAccess.OfMatchedField(elementMatcher));
        }

        public MemberSubstitution replaceWith(Method method) {
            return replaceWith(new MethodDescription.ForLoadedMethod(method));
        }

        public MemberSubstitution replaceWith(MethodDescription methodDescription) {
            if (!methodDescription.isMethod()) {
                throw new IllegalArgumentException("Cannot use " + methodDescription + " as a replacement");
            }
            return replaceWith(new Substitution.ForMethodInvocation.OfGivenMethod(methodDescription));
        }

        public MemberSubstitution replaceWithMethod(ElementMatcher<? super MethodDescription> elementMatcher) {
            return replaceWithMethod(elementMatcher, this.methodGraphCompiler);
        }

        public MemberSubstitution replaceWithMethod(ElementMatcher<? super MethodDescription> elementMatcher, MethodGraph.Compiler compiler) {
            return replaceWith(new Substitution.ForMethodInvocation.OfMatchedMethod(elementMatcher, compiler));
        }

        public MemberSubstitution replaceWithInstrumentedMethod() {
            return replaceWith(Substitution.ForMethodInvocation.OfInstrumentedMethod.INSTANCE);
        }

        public MemberSubstitution replaceWithChain(Substitution.Chain.Step.Factory... factoryArr) {
            return replaceWithChain(Arrays.asList(factoryArr));
        }

        public MemberSubstitution replaceWithChain(List<? extends Substitution.Chain.Step.Factory> list) {
            return replaceWith(Substitution.Chain.withDefaultAssigner().executing(list));
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class ForMatchedByteCodeElement extends WithoutSpecification {
            private final ElementMatcher<? super ByteCodeElement.Member> matcher;

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
            public boolean equals(@MaybeNull Object obj) {
                if (!super.equals(obj)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.matcher.equals(((ForMatchedByteCodeElement) obj).matcher);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
            public int hashCode() {
                return (super.hashCode() * 31) + this.matcher.hashCode();
            }

            protected ForMatchedByteCodeElement(MethodGraph.Compiler compiler, TypePoolResolver typePoolResolver, boolean z, boolean z2, Replacement.Factory factory, ElementMatcher<? super ByteCodeElement.Member> elementMatcher) {
                super(compiler, typePoolResolver, z, z2, factory);
                this.matcher = elementMatcher;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
            public MemberSubstitution replaceWith(Substitution.Factory factory) {
                return new MemberSubstitution(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, new Replacement.Factory.Compound(this.replacementFactory, Replacement.ForElementMatchers.Factory.of(this.matcher, factory)));
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForMatchedField extends WithoutSpecification {
            private final boolean matchRead;
            private final boolean matchWrite;
            private final ElementMatcher<? super FieldDescription> matcher;

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
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
                ForMatchedField forMatchedField = (ForMatchedField) obj;
                return this.matchRead == forMatchedField.matchRead && this.matchWrite == forMatchedField.matchWrite && this.matcher.equals(forMatchedField.matcher);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
            public int hashCode() {
                return (((((super.hashCode() * 31) + this.matcher.hashCode()) * 31) + (this.matchRead ? 1 : 0)) * 31) + (this.matchWrite ? 1 : 0);
            }

            protected ForMatchedField(MethodGraph.Compiler compiler, TypePoolResolver typePoolResolver, boolean z, boolean z2, Replacement.Factory factory, ElementMatcher<? super FieldDescription> elementMatcher) {
                this(compiler, typePoolResolver, z, z2, factory, elementMatcher, true, true);
            }

            protected ForMatchedField(MethodGraph.Compiler compiler, TypePoolResolver typePoolResolver, boolean z, boolean z2, Replacement.Factory factory, ElementMatcher<? super FieldDescription> elementMatcher, boolean z3, boolean z4) {
                super(compiler, typePoolResolver, z, z2, factory);
                this.matcher = elementMatcher;
                this.matchRead = z3;
                this.matchWrite = z4;
            }

            public WithoutSpecification onRead() {
                return new ForMatchedField(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, this.matcher, true, false);
            }

            public WithoutSpecification onWrite() {
                return new ForMatchedField(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, this.matcher, false, true);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
            public MemberSubstitution replaceWith(Substitution.Factory factory) {
                return new MemberSubstitution(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, new Replacement.Factory.Compound(this.replacementFactory, Replacement.ForElementMatchers.Factory.ofField(this.matcher, this.matchRead, this.matchWrite, factory)));
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForMatchedMethod extends WithoutSpecification {
            private final boolean includeSuperCalls;
            private final boolean includeVirtualCalls;
            private final ElementMatcher<? super MethodDescription> matcher;

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
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
                ForMatchedMethod forMatchedMethod = (ForMatchedMethod) obj;
                return this.includeVirtualCalls == forMatchedMethod.includeVirtualCalls && this.includeSuperCalls == forMatchedMethod.includeSuperCalls && this.matcher.equals(forMatchedMethod.matcher);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
            public int hashCode() {
                return (((((super.hashCode() * 31) + this.matcher.hashCode()) * 31) + (this.includeVirtualCalls ? 1 : 0)) * 31) + (this.includeSuperCalls ? 1 : 0);
            }

            protected ForMatchedMethod(MethodGraph.Compiler compiler, TypePoolResolver typePoolResolver, boolean z, boolean z2, Replacement.Factory factory, ElementMatcher<? super MethodDescription> elementMatcher) {
                this(compiler, typePoolResolver, z, z2, factory, elementMatcher, true, true);
            }

            protected ForMatchedMethod(MethodGraph.Compiler compiler, TypePoolResolver typePoolResolver, boolean z, boolean z2, Replacement.Factory factory, ElementMatcher<? super MethodDescription> elementMatcher, boolean z3, boolean z4) {
                super(compiler, typePoolResolver, z, z2, factory);
                this.matcher = elementMatcher;
                this.includeVirtualCalls = z3;
                this.includeSuperCalls = z4;
            }

            public WithoutSpecification onVirtualCall() {
                return new ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, ElementMatchers.isVirtual().and(this.matcher), true, false);
            }

            public WithoutSpecification onSuperCall() {
                return new ForMatchedMethod(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, this.replacementFactory, ElementMatchers.isVirtual().and(this.matcher), false, true);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.WithoutSpecification
            public MemberSubstitution replaceWith(Substitution.Factory factory) {
                return new MemberSubstitution(this.methodGraphCompiler, this.typePoolResolver, this.strict, this.failIfNoMatch, new Replacement.Factory.Compound(this.replacementFactory, Replacement.ForElementMatchers.Factory.ofMethod(this.matcher, this.includeVirtualCalls, this.includeSuperCalls, factory)));
            }
        }
    }

    public interface TypePoolResolver {

        public enum OfImplicitPool implements TypePoolResolver {
            INSTANCE;

            @Override // net.bytebuddy.asm.MemberSubstitution.TypePoolResolver
            public TypePool resolve(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                return typePool;
            }
        }

        TypePool resolve(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool);

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForExplicitPool implements TypePoolResolver {
            private final TypePool typePool;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.typePool.equals(((ForExplicitPool) obj).typePool);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.typePool.hashCode();
            }

            public ForExplicitPool(TypePool typePool) {
                this.typePool = typePool;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.TypePoolResolver
            public TypePool resolve(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                return this.typePool;
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForClassFileLocator implements TypePoolResolver {
            private final ClassFileLocator classFileLocator;
            private final TypePool.Default.ReaderMode readerMode;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForClassFileLocator forClassFileLocator = (ForClassFileLocator) obj;
                return this.readerMode.equals(forClassFileLocator.readerMode) && this.classFileLocator.equals(forClassFileLocator.classFileLocator);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.classFileLocator.hashCode()) * 31) + this.readerMode.hashCode();
            }

            public ForClassFileLocator(ClassFileLocator classFileLocator) {
                this(classFileLocator, TypePool.Default.ReaderMode.FAST);
            }

            public ForClassFileLocator(ClassFileLocator classFileLocator, TypePool.Default.ReaderMode readerMode) {
                this.classFileLocator = classFileLocator;
                this.readerMode = readerMode;
            }

            public static TypePoolResolver of(@MaybeNull ClassLoader classLoader) {
                return new ForClassFileLocator(ClassFileLocator.ForClassLoader.of(classLoader));
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.TypePoolResolver
            public TypePool resolve(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                return new TypePool.Default(new TypePool.CacheProvider.Simple(), this.classFileLocator, this.readerMode, typePool);
            }
        }
    }

    public interface Substitution {

        public interface Factory {
            Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool);
        }

        StackManipulation resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i);

        public enum Stubbing implements Substitution, Factory {
            INSTANCE;

            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
            public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                return this;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution
            public StackManipulation resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i) {
                ArrayList arrayList = new ArrayList(generic.size());
                for (int size = generic.size() - 1; size >= 0; size--) {
                    arrayList.add(Removal.of((TypeDefinition) generic.get(size)));
                }
                return new StackManipulation.Compound((List<? extends StackManipulation>) CompoundList.of(arrayList, DefaultValue.of(generic2.asErasure())));
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForValue implements Substitution, Factory {
            private final StackManipulation stackManipulation;
            private final TypeDescription.Generic typeDescription;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForValue forValue = (ForValue) obj;
                return this.stackManipulation.equals(forValue.stackManipulation) && this.typeDescription.equals(forValue.typeDescription);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.stackManipulation.hashCode()) * 31) + this.typeDescription.hashCode();
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
            public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                return this;
            }

            public ForValue(StackManipulation stackManipulation, TypeDescription.Generic generic) {
                this.stackManipulation = stackManipulation;
                this.typeDescription = generic;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution
            public StackManipulation resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i) {
                ArrayList arrayList = new ArrayList(generic.size());
                for (int size = generic.size() - 1; size >= 0; size--) {
                    arrayList.add(Removal.of((TypeDefinition) generic.get(size)));
                }
                if (!this.typeDescription.asErasure().isAssignableTo(generic2.asErasure())) {
                    throw new IllegalStateException("Cannot assign " + this.typeDescription + " to " + generic2);
                }
                return new StackManipulation.Compound((List<? extends StackManipulation>) CompoundList.of(arrayList, this.stackManipulation));
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForFieldAccess implements Substitution {
            private final FieldResolver fieldResolver;
            private final TypeDescription instrumentedType;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForFieldAccess forFieldAccess = (ForFieldAccess) obj;
                return this.instrumentedType.equals(forFieldAccess.instrumentedType) && this.fieldResolver.equals(forFieldAccess.fieldResolver);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31) + this.fieldResolver.hashCode();
            }

            public ForFieldAccess(TypeDescription typeDescription, FieldResolver fieldResolver) {
                this.instrumentedType = typeDescription;
                this.fieldResolver = fieldResolver;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution
            public StackManipulation resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i) {
                FieldDescription fieldDescriptionResolve = this.fieldResolver.resolve(typeDescription, member, generic, generic2);
                if (!fieldDescriptionResolve.isAccessibleTo(this.instrumentedType)) {
                    throw new IllegalStateException(this.instrumentedType + " cannot access " + fieldDescriptionResolve);
                }
                if (generic2.represents(Void.TYPE)) {
                    if (generic.size() != (fieldDescriptionResolve.isStatic() ? 1 : 2)) {
                        throw new IllegalStateException("Cannot set " + fieldDescriptionResolve + " with " + generic);
                    }
                    if (!fieldDescriptionResolve.isStatic() && !((TypeDescription.Generic) generic.get(0)).asErasure().isAssignableTo(fieldDescriptionResolve.getDeclaringType().asErasure())) {
                        throw new IllegalStateException("Cannot set " + fieldDescriptionResolve + " on " + generic.get(0));
                    }
                    if (!((TypeDescription.Generic) generic.get(!fieldDescriptionResolve.isStatic() ? 1 : 0)).asErasure().isAssignableTo(fieldDescriptionResolve.getType().asErasure())) {
                        throw new IllegalStateException("Cannot set " + fieldDescriptionResolve + " to " + generic.get(!fieldDescriptionResolve.isStatic() ? 1 : 0));
                    }
                    return FieldAccess.forField(fieldDescriptionResolve).write();
                }
                if (generic.size() != (1 ^ (fieldDescriptionResolve.isStatic() ? 1 : 0))) {
                    throw new IllegalStateException("Cannot set " + fieldDescriptionResolve + " with " + generic);
                }
                if (!fieldDescriptionResolve.isStatic() && !((TypeDescription.Generic) generic.get(0)).asErasure().isAssignableTo(fieldDescriptionResolve.getDeclaringType().asErasure())) {
                    throw new IllegalStateException("Cannot get " + fieldDescriptionResolve + " on " + generic.get(0));
                }
                if (!fieldDescriptionResolve.getType().asErasure().isAssignableTo(generic2.asErasure())) {
                    throw new IllegalStateException("Cannot get " + fieldDescriptionResolve + " as " + generic2);
                }
                return FieldAccess.forField(fieldDescriptionResolve).read();
            }

            public interface FieldResolver {
                FieldDescription resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2);

                @HashCodeAndEqualsPlugin.Enhance
                public static class Simple implements FieldResolver {
                    private final FieldDescription fieldDescription;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.fieldDescription.equals(((Simple) obj).fieldDescription);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.fieldDescription.hashCode();
                    }

                    public Simple(FieldDescription fieldDescription) {
                        this.fieldDescription = fieldDescription;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.ForFieldAccess.FieldResolver
                    public FieldDescription resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2) {
                        return this.fieldDescription;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForElementMatcher implements FieldResolver {
                    private final TypeDescription instrumentedType;
                    private final ElementMatcher<? super FieldDescription> matcher;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForElementMatcher forElementMatcher = (ForElementMatcher) obj;
                        return this.instrumentedType.equals(forElementMatcher.instrumentedType) && this.matcher.equals(forElementMatcher.matcher);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31) + this.matcher.hashCode();
                    }

                    protected ForElementMatcher(TypeDescription typeDescription, ElementMatcher<? super FieldDescription> elementMatcher) {
                        this.instrumentedType = typeDescription;
                        this.matcher = elementMatcher;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.ForFieldAccess.FieldResolver
                    public FieldDescription resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2) {
                        if (generic.isEmpty()) {
                            throw new IllegalStateException("Cannot substitute parameterless instruction with " + member);
                        }
                        if (((TypeDescription.Generic) generic.get(0)).isPrimitive() || ((TypeDescription.Generic) generic.get(0)).isArray()) {
                            throw new IllegalStateException("Cannot access field on primitive or array type for " + member);
                        }
                        TypeDefinition superClass = (TypeDefinition) ((TypeDescription.Generic) generic.get(0)).accept(new TypeDescription.Generic.Visitor.Substitutor.ForReplacement(this.instrumentedType));
                        do {
                            FieldList fieldListFilter = superClass.getDeclaredFields().filter(ElementMatchers.not(ElementMatchers.isStatic()).and(ElementMatchers.isVisibleTo(this.instrumentedType)).and(this.matcher));
                            if (fieldListFilter.size() == 1) {
                                return (FieldDescription) fieldListFilter.getOnly();
                            }
                            if (fieldListFilter.size() > 1) {
                                throw new IllegalStateException("Ambiguous field location of " + fieldListFilter);
                            }
                            superClass = superClass.getSuperClass();
                        } while (superClass != null);
                        throw new IllegalStateException("Cannot locate field matching " + this.matcher + " on " + typeDescription);
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class OfGivenField implements Factory {
                private final FieldDescription fieldDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.fieldDescription.equals(((OfGivenField) obj).fieldDescription);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.fieldDescription.hashCode();
                }

                public OfGivenField(FieldDescription fieldDescription) {
                    this.fieldDescription = fieldDescription;
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
                public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    return new ForFieldAccess(typeDescription, new FieldResolver.Simple(this.fieldDescription));
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class OfMatchedField implements Factory {
                private final ElementMatcher<? super FieldDescription> matcher;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.matcher.equals(((OfMatchedField) obj).matcher);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.matcher.hashCode();
                }

                public OfMatchedField(ElementMatcher<? super FieldDescription> elementMatcher) {
                    this.matcher = elementMatcher;
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
                public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    return new ForFieldAccess(typeDescription, new FieldResolver.ForElementMatcher(typeDescription, this.matcher));
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForMethodInvocation implements Substitution {
            private final TypeDescription instrumentedType;
            private final MethodResolver methodResolver;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForMethodInvocation forMethodInvocation = (ForMethodInvocation) obj;
                return this.instrumentedType.equals(forMethodInvocation.instrumentedType) && this.methodResolver.equals(forMethodInvocation.methodResolver);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31) + this.methodResolver.hashCode();
            }

            public ForMethodInvocation(TypeDescription typeDescription, MethodResolver methodResolver) {
                this.instrumentedType = typeDescription;
                this.methodResolver = methodResolver;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution
            public StackManipulation resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i) {
                TypeList.Generic explicit;
                MethodDescription methodDescriptionResolve = this.methodResolver.resolve(typeDescription, member, generic, generic2);
                if (!methodDescriptionResolve.isAccessibleTo(this.instrumentedType)) {
                    throw new IllegalStateException(this.instrumentedType + " cannot access " + methodDescriptionResolve);
                }
                if (methodDescriptionResolve.isStatic()) {
                    explicit = methodDescriptionResolve.getParameters().asTypeList();
                } else {
                    explicit = new TypeList.Generic.Explicit((List<? extends TypeDefinition>) CompoundList.of(methodDescriptionResolve.getDeclaringType(), methodDescriptionResolve.getParameters().asTypeList()));
                }
                if (!methodDescriptionResolve.getReturnType().asErasure().isAssignableTo(generic2.asErasure())) {
                    throw new IllegalStateException("Cannot assign return value of " + methodDescriptionResolve + " to " + generic2);
                }
                if (explicit.size() != generic.size()) {
                    throw new IllegalStateException("Cannot invoke " + methodDescriptionResolve + " on " + generic.size() + " parameters");
                }
                for (int i2 = 0; i2 < explicit.size(); i2++) {
                    if (!((TypeDescription.Generic) generic.get(i2)).asErasure().isAssignableTo(((TypeDescription.Generic) explicit.get(i2)).asErasure())) {
                        throw new IllegalStateException("Cannot invoke " + methodDescriptionResolve + " on parameter " + i2 + " of type " + generic.get(i2));
                    }
                }
                boolean zIsVirtual = methodDescriptionResolve.isVirtual();
                MethodInvocation.WithImplicitInvocationTargetType withImplicitInvocationTargetTypeInvoke = MethodInvocation.invoke(methodDescriptionResolve);
                return zIsVirtual ? withImplicitInvocationTargetTypeInvoke.virtual(((TypeDescription.Generic) explicit.get(0)).asErasure()) : withImplicitInvocationTargetTypeInvoke;
            }

            public interface MethodResolver {
                MethodDescription resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2);

                @HashCodeAndEqualsPlugin.Enhance
                public static class Simple implements MethodResolver {
                    private final MethodDescription methodDescription;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.methodDescription.equals(((Simple) obj).methodDescription);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.methodDescription.hashCode();
                    }

                    public Simple(MethodDescription methodDescription) {
                        this.methodDescription = methodDescription;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.ForMethodInvocation.MethodResolver
                    public MethodDescription resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2) {
                        return this.methodDescription;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class Matching implements MethodResolver {
                    private final TypeDescription instrumentedType;
                    private final ElementMatcher<? super MethodDescription> matcher;
                    private final MethodGraph.Compiler methodGraphCompiler;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        Matching matching = (Matching) obj;
                        return this.instrumentedType.equals(matching.instrumentedType) && this.methodGraphCompiler.equals(matching.methodGraphCompiler) && this.matcher.equals(matching.matcher);
                    }

                    public int hashCode() {
                        return (((((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31) + this.methodGraphCompiler.hashCode()) * 31) + this.matcher.hashCode();
                    }

                    public Matching(TypeDescription typeDescription, MethodGraph.Compiler compiler, ElementMatcher<? super MethodDescription> elementMatcher) {
                        this.instrumentedType = typeDescription;
                        this.methodGraphCompiler = compiler;
                        this.matcher = elementMatcher;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.ForMethodInvocation.MethodResolver
                    public MethodDescription resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2) {
                        if (generic.isEmpty()) {
                            throw new IllegalStateException("Cannot substitute parameterless instruction with " + member);
                        }
                        if (((TypeDescription.Generic) generic.get(0)).isPrimitive() || ((TypeDescription.Generic) generic.get(0)).isArray()) {
                            throw new IllegalStateException("Cannot invoke method on primitive or array type for " + member);
                        }
                        TypeDefinition typeDefinition = (TypeDefinition) ((TypeDescription.Generic) generic.get(0)).accept(new TypeDescription.Generic.Visitor.Substitutor.ForReplacement(this.instrumentedType));
                        List listOf = CompoundList.of(this.methodGraphCompiler.compile(typeDefinition, this.instrumentedType).listNodes().asMethodList().filter(this.matcher), typeDefinition.getDeclaredMethods().filter(ElementMatchers.isPrivate().and(ElementMatchers.isVisibleTo(this.instrumentedType)).and(this.matcher)));
                        if (listOf.size() == 1) {
                            return (MethodDescription) listOf.get(0);
                        }
                        throw new IllegalStateException("Not exactly one method that matches " + this.matcher + ": " + listOf);
                    }
                }
            }

            enum OfInstrumentedMethod implements Factory {
                INSTANCE;

                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
                public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    return new ForMethodInvocation(typeDescription, new MethodResolver.Simple(methodDescription));
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class OfGivenMethod implements Factory {
                private final MethodDescription methodDescription;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.methodDescription.equals(((OfGivenMethod) obj).methodDescription);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.methodDescription.hashCode();
                }

                public OfGivenMethod(MethodDescription methodDescription) {
                    this.methodDescription = methodDescription;
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
                public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    return new ForMethodInvocation(typeDescription, new MethodResolver.Simple(this.methodDescription));
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class OfMatchedMethod implements Factory {
                private final ElementMatcher<? super MethodDescription> matcher;
                private final MethodGraph.Compiler methodGraphCompiler;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    OfMatchedMethod ofMatchedMethod = (OfMatchedMethod) obj;
                    return this.matcher.equals(ofMatchedMethod.matcher) && this.methodGraphCompiler.equals(ofMatchedMethod.methodGraphCompiler);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.matcher.hashCode()) * 31) + this.methodGraphCompiler.hashCode();
                }

                public OfMatchedMethod(ElementMatcher<? super MethodDescription> elementMatcher, MethodGraph.Compiler compiler) {
                    this.matcher = elementMatcher;
                    this.methodGraphCompiler = compiler;
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
                public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    return new ForMethodInvocation(typeDescription, new MethodResolver.Matching(typeDescription, this.methodGraphCompiler, this.matcher));
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class Chain implements Substitution {
            private final Assigner assigner;
            private final List<Step> steps;
            private final Assigner.Typing typing;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Chain chain = (Chain) obj;
                return this.typing.equals(chain.typing) && this.assigner.equals(chain.assigner) && this.steps.equals(chain.steps);
            }

            public int hashCode() {
                return (((((getClass().hashCode() * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode()) * 31) + this.steps.hashCode();
            }

            protected Chain(Assigner assigner, Assigner.Typing typing, List<Step> list) {
                this.assigner = assigner;
                this.typing = typing;
                this.steps = list;
            }

            public static Factory withDefaultAssigner() {
                return with(Assigner.DEFAULT, Assigner.Typing.STATIC);
            }

            public static Factory with(Assigner assigner, Assigner.Typing typing) {
                return new Factory(assigner, typing, Collections.emptyList());
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution
            public StackManipulation resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i) {
                ArrayList arrayList = new ArrayList(generic.size() + 1 + (this.steps.size() * 2) + (generic2.represents(Void.TYPE) ? 0 : 2));
                HashMap map = new HashMap();
                int size = i;
                for (int size2 = generic.size() - 1; size2 >= 0; size2--) {
                    arrayList.add(MethodVariableAccess.of((TypeDefinition) generic.get(size2)).storeAt(size));
                    map.put(Integer.valueOf(size2), Integer.valueOf(size));
                    size += ((TypeDescription.Generic) generic.get(size2)).getStackSize().getSize();
                }
                arrayList.add(DefaultValue.of(generic2));
                Iterator<Step> it = this.steps.iterator();
                TypeDescription.Generic resultType = generic2;
                while (it.hasNext()) {
                    Step.Resolution resolutionResolve = it.next().resolve(typeDescription, member, generic, generic2, methodHandle, stackManipulation, resultType, map, size);
                    arrayList.add(resolutionResolve.getStackManipulation());
                    resultType = resolutionResolve.getResultType();
                }
                StackManipulation stackManipulationAssign = this.assigner.assign(resultType, generic2, this.typing);
                if (!stackManipulationAssign.isValid()) {
                    throw new IllegalStateException("Failed to assign " + resultType + " to " + generic2);
                }
                arrayList.add(stackManipulationAssign);
                return new StackManipulation.Compound(arrayList);
            }

            public interface Step {

                public interface Factory {
                    Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription);
                }

                public interface Resolution {
                    TypeDescription.Generic getResultType();

                    StackManipulation getStackManipulation();
                }

                Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i);

                public enum OfOriginalExpression implements Step, Factory {
                    INSTANCE;

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                    public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                        return this;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        ArrayList arrayList;
                        TypeDescription.Generic returnType;
                        boolean z = member instanceof MethodDescription;
                        if (z && ((MethodDescription) member).isConstructor()) {
                            arrayList = new ArrayList(generic.size() + 4);
                            arrayList.add(Removal.of(generic3));
                            arrayList.add(TypeCreation.of(member.getDeclaringType().asErasure()));
                            arrayList.add(Duplication.SINGLE);
                        } else {
                            arrayList = new ArrayList(generic.size() + 4);
                            arrayList.add(Removal.of(generic3));
                        }
                        for (int i2 = 0; i2 < generic.size(); i2++) {
                            arrayList.add(MethodVariableAccess.of((TypeDefinition) generic.get(i2)).loadFrom(map.get(Integer.valueOf(i2)).intValue()));
                        }
                        if (z) {
                            arrayList.add(stackManipulation);
                            StackManipulation.Compound compound = new StackManipulation.Compound(arrayList);
                            MethodDescription methodDescription = (MethodDescription) member;
                            if (methodDescription.isConstructor()) {
                                returnType = member.getDeclaringType().asGenericType();
                            } else {
                                returnType = methodDescription.getReturnType();
                            }
                            return new Simple(compound, returnType);
                        }
                        if (member instanceof FieldDescription) {
                            if (member.isStatic()) {
                                if (generic.isEmpty()) {
                                    arrayList.add(stackManipulation);
                                    return new Simple(new StackManipulation.Compound(arrayList), ((FieldDescription) member).getType());
                                }
                                arrayList.add(stackManipulation);
                                return new Simple(new StackManipulation.Compound(arrayList), TypeDefinition.Sort.describe(Void.TYPE));
                            }
                            if (generic.size() == 1) {
                                FieldDescription fieldDescription = (FieldDescription) member;
                                arrayList.add(FieldAccess.forField(fieldDescription).read());
                                return new Simple(new StackManipulation.Compound(arrayList), fieldDescription.getType());
                            }
                            arrayList.add(FieldAccess.forField((FieldDescription) member).write());
                            return new Simple(new StackManipulation.Compound(arrayList), TypeDefinition.Sort.describe(Void.TYPE));
                        }
                        throw new IllegalArgumentException("Unexpected target type: " + member);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class Simple implements Step, Resolution, Factory {
                    private final TypeDescription.Generic resultType;
                    private final StackManipulation stackManipulation;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        Simple simple = (Simple) obj;
                        return this.stackManipulation.equals(simple.stackManipulation) && this.resultType.equals(simple.resultType);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.stackManipulation.hashCode()) * 31) + this.resultType.hashCode();
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                    public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                        return this;
                    }

                    public Simple(StackManipulation stackManipulation, Type type) {
                        this(stackManipulation, TypeDefinition.Sort.describe(type));
                    }

                    public Simple(StackManipulation stackManipulation, TypeDescription.Generic generic) {
                        this.stackManipulation = stackManipulation;
                        this.resultType = generic;
                    }

                    public static Factory of(Object obj) {
                        ConstantValue constantValueWrap = ConstantValue.Simple.wrap(obj);
                        return new Simple(constantValueWrap.toStackManipulation(), constantValueWrap.getTypeDescription().asGenericType());
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        return typeDescription.represents(Void.TYPE) ? this : new Simple(new StackManipulation.Compound(Removal.of(generic3), this.stackManipulation), this.resultType);
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Resolution
                    public StackManipulation getStackManipulation() {
                        return this.stackManipulation;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Resolution
                    public TypeDescription.Generic getResultType() {
                        return this.resultType;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForAssignment implements Step {
                    private final Assigner assigner;

                    @MaybeNull
                    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                    private final TypeDescription.Generic result;

                    /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
                    
                        if (r2 != null) goto L18;
                     */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct add '--show-bad-code' argument
                    */
                    public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
                        /*
                            r4 = this;
                            r0 = 1
                            if (r4 != r5) goto L4
                            return r0
                        L4:
                            r1 = 0
                            if (r5 != 0) goto L8
                            return r1
                        L8:
                            java.lang.Class r2 = r4.getClass()
                            java.lang.Class r3 = r5.getClass()
                            if (r2 == r3) goto L13
                            return r1
                        L13:
                            net.bytebuddy.description.type.TypeDescription$Generic r2 = r4.result
                            net.bytebuddy.asm.MemberSubstitution$Substitution$Chain$Step$ForAssignment r5 = (net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForAssignment) r5
                            net.bytebuddy.description.type.TypeDescription$Generic r3 = r5.result
                            if (r3 == 0) goto L24
                            if (r2 == 0) goto L26
                            boolean r2 = r2.equals(r3)
                            if (r2 != 0) goto L27
                            return r1
                        L24:
                            if (r2 == 0) goto L27
                        L26:
                            return r1
                        L27:
                            net.bytebuddy.implementation.bytecode.assign.Assigner r2 = r4.assigner
                            net.bytebuddy.implementation.bytecode.assign.Assigner r5 = r5.assigner
                            boolean r5 = r2.equals(r5)
                            if (r5 != 0) goto L32
                            return r1
                        L32:
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForAssignment.equals(java.lang.Object):boolean");
                    }

                    public int hashCode() {
                        int iHashCode = getClass().hashCode() * 31;
                        TypeDescription.Generic generic = this.result;
                        if (generic != null) {
                            iHashCode += generic.hashCode();
                        }
                        return (iHashCode * 31) + this.assigner.hashCode();
                    }

                    protected ForAssignment(@MaybeNull TypeDescription.Generic generic, Assigner assigner) {
                        this.result = generic;
                        this.assigner = assigner;
                    }

                    public static Factory castTo(Type type) {
                        return new Factory(TypeDefinition.Sort.describe(type));
                    }

                    public static Factory castTo(TypeDescription.Generic generic) {
                        return new Factory(generic);
                    }

                    public static Factory castToSubstitutionResult() {
                        return new Factory(null);
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        Assigner assigner = this.assigner;
                        TypeDescription.Generic generic4 = this.result;
                        if (generic4 == null) {
                            generic4 = generic2;
                        }
                        StackManipulation stackManipulationAssign = assigner.assign(generic3, generic4, Assigner.Typing.DYNAMIC);
                        if (!stackManipulationAssign.isValid()) {
                            StringBuilder sbAppend = new StringBuilder("Failed to assign ").append(generic3).append(" to ");
                            TypeDescription.Generic generic5 = this.result;
                            if (generic5 != null) {
                                generic2 = generic5;
                            }
                            throw new IllegalStateException(sbAppend.append(generic2).toString());
                        }
                        TypeDescription.Generic generic6 = this.result;
                        if (generic6 != null) {
                            generic2 = generic6;
                        }
                        return new Simple(stackManipulationAssign, generic2);
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    protected static class Factory implements Factory {

                        @MaybeNull
                        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                        private final TypeDescription.Generic result;

                        /* JADX WARN: Code duplicated, block: B:19:0x0027 A[RETURN] */
                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (obj == null || getClass() != obj.getClass()) {
                                return false;
                            }
                            TypeDescription.Generic generic = this.result;
                            TypeDescription.Generic generic2 = ((Factory) obj).result;
                            if (generic2 == null) {
                                if (generic != null) {
                                    return false;
                                }
                                return true;
                            }
                            if (generic == null || !generic.equals(generic2)) {
                                return false;
                            }
                            return true;
                        }

                        public int hashCode() {
                            int iHashCode = getClass().hashCode() * 31;
                            TypeDescription.Generic generic = this.result;
                            return generic != null ? iHashCode + generic.hashCode() : iHashCode;
                        }

                        protected Factory(@MaybeNull TypeDescription.Generic generic) {
                            this.result = generic;
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                        public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                            return new ForAssignment(this.result, assigner);
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForArgumentSubstitution implements Step {
                    private final Assigner assigner;
                    private final int index;
                    private final StackManipulation substitution;
                    private final TypeDescription.Generic typeDescription;
                    private final Assigner.Typing typing;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForArgumentSubstitution forArgumentSubstitution = (ForArgumentSubstitution) obj;
                        return this.index == forArgumentSubstitution.index && this.typing.equals(forArgumentSubstitution.typing) && this.substitution.equals(forArgumentSubstitution.substitution) && this.typeDescription.equals(forArgumentSubstitution.typeDescription) && this.assigner.equals(forArgumentSubstitution.assigner);
                    }

                    public int hashCode() {
                        return (((((((((getClass().hashCode() * 31) + this.substitution.hashCode()) * 31) + this.typeDescription.hashCode()) * 31) + this.index) * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode();
                    }

                    protected ForArgumentSubstitution(StackManipulation stackManipulation, TypeDescription.Generic generic, int i, Assigner assigner, Assigner.Typing typing) {
                        this.substitution = stackManipulation;
                        this.typeDescription = generic;
                        this.index = i;
                        this.assigner = assigner;
                        this.typing = typing;
                    }

                    public static Factory of(Object obj, int i) {
                        if (i < 0) {
                            throw new IllegalArgumentException("Index cannot be negative: " + i);
                        }
                        ConstantValue constantValueWrap = ConstantValue.Simple.wrap(obj);
                        return new Factory(constantValueWrap.toStackManipulation(), constantValueWrap.getTypeDescription().asGenericType(), i);
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        if (this.index >= generic.size()) {
                            throw new IllegalStateException(member + " has not " + this.index + " arguments");
                        }
                        StackManipulation stackManipulationAssign = this.assigner.assign(this.typeDescription, (TypeDescription.Generic) generic.get(this.index), this.typing);
                        if (!stackManipulationAssign.isValid()) {
                            throw new IllegalStateException("Cannot assign " + this.typeDescription + " to " + generic.get(this.index));
                        }
                        return new Simple(new StackManipulation.Compound(this.substitution, stackManipulationAssign, MethodVariableAccess.of((TypeDefinition) generic.get(this.index)).storeAt(map.get(Integer.valueOf(this.index)).intValue())), generic3);
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class Factory implements Factory {
                        private final int index;
                        private final StackManipulation stackManipulation;
                        private final TypeDescription.Generic typeDescription;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (obj == null || getClass() != obj.getClass()) {
                                return false;
                            }
                            Factory factory = (Factory) obj;
                            return this.index == factory.index && this.stackManipulation.equals(factory.stackManipulation) && this.typeDescription.equals(factory.typeDescription);
                        }

                        public int hashCode() {
                            return (((((getClass().hashCode() * 31) + this.stackManipulation.hashCode()) * 31) + this.typeDescription.hashCode()) * 31) + this.index;
                        }

                        public Factory(StackManipulation stackManipulation, Type type, int i) {
                            this(stackManipulation, TypeDefinition.Sort.describe(type), i);
                        }

                        public Factory(StackManipulation stackManipulation, TypeDescription.Generic generic, int i) {
                            this.stackManipulation = stackManipulation;
                            this.typeDescription = generic;
                            this.index = i;
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                        public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                            return new ForArgumentSubstitution(this.stackManipulation, this.typeDescription, this.index, assigner, typing);
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForArgumentLoading implements Step, Factory {
                    private final int index;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.index == ((ForArgumentLoading) obj).index;
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.index;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                    public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                        return this;
                    }

                    protected ForArgumentLoading(int i) {
                        this.index = i;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        if (this.index >= generic.size()) {
                            throw new IllegalStateException(member + " has not " + this.index + " arguments");
                        }
                        return new Simple(new StackManipulation.Compound(Removal.of(generic3), MethodVariableAccess.of((TypeDefinition) generic.get(this.index)).loadFrom(map.get(Integer.valueOf(this.index)).intValue())), (TypeDescription.Generic) generic.get(this.index));
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static abstract class ForField implements Step {
                    protected final Assigner assigner;
                    protected final FieldDescription fieldDescription;
                    protected final Assigner.Typing typing;

                    protected abstract Resolution doResolve(ByteCodeElement.Member member, TypeList.Generic generic, Map<Integer, Integer> map, StackManipulation stackManipulation);

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForField forField = (ForField) obj;
                        return this.typing.equals(forField.typing) && this.fieldDescription.equals(forField.fieldDescription) && this.assigner.equals(forField.assigner);
                    }

                    public int hashCode() {
                        return (((((getClass().hashCode() * 31) + this.fieldDescription.hashCode()) * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode();
                    }

                    protected ForField(FieldDescription fieldDescription, Assigner assigner, Assigner.Typing typing) {
                        this.fieldDescription = fieldDescription;
                        this.assigner = assigner;
                        this.typing = typing;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        ArrayList arrayList = new ArrayList(2);
                        if (this.fieldDescription.isStatic()) {
                            arrayList.add(Removal.of(generic3));
                        } else {
                            StackManipulation stackManipulationAssign = this.assigner.assign(generic3, this.fieldDescription.getDeclaringType().asGenericType(), this.typing);
                            if (!stackManipulationAssign.isValid()) {
                                throw new IllegalStateException("Cannot assign " + generic3 + " to " + this.fieldDescription.getDeclaringType());
                            }
                            arrayList.add(stackManipulationAssign);
                        }
                        return doResolve(member, generic, map, new StackManipulation.Compound(arrayList));
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class Read extends ForField {
                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForField
                        public boolean equals(@MaybeNull Object obj) {
                            if (!super.equals(obj)) {
                                return false;
                            }
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass();
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForField
                        public int hashCode() {
                            return super.hashCode();
                        }

                        protected Read(FieldDescription fieldDescription, Assigner assigner, Assigner.Typing typing) {
                            super(fieldDescription, assigner, typing);
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForField
                        protected Resolution doResolve(ByteCodeElement.Member member, TypeList.Generic generic, Map<Integer, Integer> map, StackManipulation stackManipulation) {
                            return new Simple(new StackManipulation.Compound(stackManipulation, FieldAccess.forField(this.fieldDescription).read()), this.fieldDescription.getType());
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class Factory implements Factory {
                            private final FieldDescription fieldDescription;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                return obj != null && getClass() == obj.getClass() && this.fieldDescription.equals(((Factory) obj).fieldDescription);
                            }

                            public int hashCode() {
                                return (getClass().hashCode() * 31) + this.fieldDescription.hashCode();
                            }

                            public Factory(Field field) {
                                this(new FieldDescription.ForLoadedField(field));
                            }

                            public Factory(FieldDescription fieldDescription) {
                                this.fieldDescription = fieldDescription;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                            public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                return new Read(this.fieldDescription, assigner, typing);
                            }
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class Write extends ForField {
                        private final int index;

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForField
                        public boolean equals(@MaybeNull Object obj) {
                            if (!super.equals(obj)) {
                                return false;
                            }
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && this.index == ((Write) obj).index;
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForField
                        public int hashCode() {
                            return (super.hashCode() * 31) + this.index;
                        }

                        protected Write(FieldDescription fieldDescription, Assigner assigner, Assigner.Typing typing, int i) {
                            super(fieldDescription, assigner, typing);
                            this.index = i;
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForField
                        protected Resolution doResolve(ByteCodeElement.Member member, TypeList.Generic generic, Map<Integer, Integer> map, StackManipulation stackManipulation) {
                            int i = ((member.getModifiers() & 8) != 0 || ((member instanceof MethodDescription) && ((MethodDescription) member).isConstructor())) ? this.index : this.index + 1;
                            if (i >= generic.size()) {
                                throw new IllegalStateException(member + " does not define an argument with index " + i);
                            }
                            StackManipulation stackManipulationAssign = this.assigner.assign((TypeDescription.Generic) generic.get(i), this.fieldDescription.getType(), this.typing);
                            if (!stackManipulationAssign.isValid()) {
                                throw new IllegalStateException("Cannot write " + generic.get(i) + " to " + this.fieldDescription);
                            }
                            return new Simple(new StackManipulation.Compound(stackManipulation, MethodVariableAccess.of((TypeDefinition) generic.get(i)).loadFrom(map.get(Integer.valueOf(i)).intValue()), stackManipulationAssign, FieldAccess.forField(this.fieldDescription).write()), TypeDefinition.Sort.describe(Void.TYPE));
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class Factory implements Factory {
                            private final FieldDescription fieldDescription;
                            private final int index;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                if (obj == null || getClass() != obj.getClass()) {
                                    return false;
                                }
                                Factory factory = (Factory) obj;
                                return this.index == factory.index && this.fieldDescription.equals(factory.fieldDescription);
                            }

                            public int hashCode() {
                                return (((getClass().hashCode() * 31) + this.fieldDescription.hashCode()) * 31) + this.index;
                            }

                            public Factory(Field field, int i) {
                                this(new FieldDescription.ForLoadedField(field), i);
                            }

                            public Factory(FieldDescription fieldDescription, int i) {
                                this.fieldDescription = fieldDescription;
                                this.index = i;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                            public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                return new Write(this.fieldDescription, assigner, typing, this.index);
                            }
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForInvocation implements Step {
                    private final Assigner assigner;
                    private final MethodDescription methodDescription;
                    private final Map<Integer, Integer> substitutions;
                    private final Assigner.Typing typing;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForInvocation forInvocation = (ForInvocation) obj;
                        return this.typing.equals(forInvocation.typing) && this.methodDescription.equals(forInvocation.methodDescription) && this.substitutions.equals(forInvocation.substitutions) && this.assigner.equals(forInvocation.assigner);
                    }

                    public int hashCode() {
                        return (((((((getClass().hashCode() * 31) + this.methodDescription.hashCode()) * 31) + this.substitutions.hashCode()) * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode();
                    }

                    protected ForInvocation(MethodDescription methodDescription, Map<Integer, Integer> map, Assigner assigner, Assigner.Typing typing) {
                        this.methodDescription = methodDescription;
                        this.substitutions = map;
                        this.assigner = assigner;
                        this.typing = typing;
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        ArrayList arrayList = new ArrayList((generic.size() * 2) + 3);
                        if (this.methodDescription.isStatic()) {
                            arrayList.add(Removal.of(generic3));
                        } else if (this.methodDescription.isConstructor()) {
                            arrayList.add(Removal.of(generic3));
                            arrayList.add(TypeCreation.of(this.methodDescription.getDeclaringType().asErasure()));
                        } else {
                            StackManipulation stackManipulationAssign = this.assigner.assign(generic3, this.methodDescription.getDeclaringType().asGenericType(), this.typing);
                            if (!stackManipulationAssign.isValid()) {
                                throw new IllegalStateException("Cannot assign " + generic3 + " to " + this.methodDescription.getDeclaringType());
                            }
                            arrayList.add(stackManipulationAssign);
                        }
                        int i2 = ((member.getModifiers() & 8) != 0 || ((member instanceof MethodDescription) && ((MethodDescription) member).isConstructor())) ? 0 : 1;
                        for (int i3 = 0; i3 < this.methodDescription.getParameters().size(); i3++) {
                            int iIntValue = this.substitutions.containsKey(Integer.valueOf(i3 + i2)) ? this.substitutions.get(Integer.valueOf(i3 + i2)).intValue() : i3 + i2;
                            if (iIntValue >= generic.size()) {
                                throw new IllegalStateException(member + " does not support an index " + iIntValue);
                            }
                            arrayList.add(MethodVariableAccess.of((TypeDefinition) generic.get(iIntValue)).loadFrom(map.get(Integer.valueOf(iIntValue)).intValue()));
                            StackManipulation stackManipulationAssign2 = this.assigner.assign((TypeDescription.Generic) generic.get(iIntValue), ((ParameterDescription) this.methodDescription.getParameters().get(i3)).getType(), this.typing);
                            if (!stackManipulationAssign2.isValid()) {
                                throw new IllegalStateException("Cannot assign parameter with " + i3 + " of type " + generic.get(iIntValue) + " to " + this.methodDescription);
                            }
                            arrayList.add(stackManipulationAssign2);
                        }
                        arrayList.add(MethodInvocation.invoke(this.methodDescription));
                        return new Simple(new StackManipulation.Compound(arrayList), this.methodDescription.getReturnType());
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class Factory implements Factory {
                        private final MethodDescription methodDescription;
                        private final Map<Integer, Integer> substitutions;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (obj == null || getClass() != obj.getClass()) {
                                return false;
                            }
                            Factory factory = (Factory) obj;
                            return this.methodDescription.equals(factory.methodDescription) && this.substitutions.equals(factory.substitutions);
                        }

                        public int hashCode() {
                            return (((getClass().hashCode() * 31) + this.methodDescription.hashCode()) * 31) + this.substitutions.hashCode();
                        }

                        public Factory(Method method) {
                            this(new MethodDescription.ForLoadedMethod(method));
                        }

                        public Factory(Constructor<?> constructor) {
                            this(new MethodDescription.ForLoadedConstructor(constructor));
                        }

                        public Factory(MethodDescription methodDescription) {
                            this(methodDescription, Collections.emptyMap());
                        }

                        public Factory(MethodDescription methodDescription, Map<Integer, Integer> map) {
                            this.methodDescription = methodDescription;
                            this.substitutions = map;
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                        public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                            return new ForInvocation(this.methodDescription, this.substitutions, assigner, typing);
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForDelegation implements Step {
                    private final Dispatcher.Resolved dispatcher;
                    private final List<OffsetMapping.Resolved> offsetMappings;
                    private final TypeDescription.Generic returned;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForDelegation forDelegation = (ForDelegation) obj;
                        return this.returned.equals(forDelegation.returned) && this.dispatcher.equals(forDelegation.dispatcher) && this.offsetMappings.equals(forDelegation.offsetMappings);
                    }

                    public int hashCode() {
                        return (((((getClass().hashCode() * 31) + this.returned.hashCode()) * 31) + this.dispatcher.hashCode()) * 31) + this.offsetMappings.hashCode();
                    }

                    protected ForDelegation(TypeDescription.Generic generic, Dispatcher.Resolved resolved, List<OffsetMapping.Resolved> list) {
                        this.returned = generic;
                        this.dispatcher = resolved;
                        this.offsetMappings = list;
                    }

                    public static Factory to(Method method) {
                        return to(new MethodDescription.ForLoadedMethod(method));
                    }

                    public static Factory to(Constructor<?> constructor) {
                        return to(new MethodDescription.ForLoadedConstructor(constructor));
                    }

                    public static Factory to(MethodDescription.InDefinedShape inDefinedShape) {
                        if (inDefinedShape.isTypeInitializer()) {
                            throw new IllegalArgumentException("Cannot delegate to a type initializer: " + inDefinedShape);
                        }
                        return to(inDefinedShape, Dispatcher.ForRegularInvocation.Factory.INSTANCE, Collections.emptyList());
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public static Factory to(MethodDescription.InDefinedShape inDefinedShape, Dispatcher.Factory factory, List<? extends OffsetMapping.Factory<?>> list) {
                        if (inDefinedShape.isTypeInitializer()) {
                            throw new IllegalArgumentException("Cannot delegate to type initializer: " + inDefinedShape);
                        }
                        return new Factory(inDefinedShape, factory.make(inDefinedShape), CompoundList.of(Arrays.asList(OffsetMapping.ForArgument.Factory.INSTANCE, OffsetMapping.ForThisReference.Factory.INSTANCE, OffsetMapping.ForAllArguments.Factory.INSTANCE, OffsetMapping.ForSelfCallHandle.Factory.INSTANCE, OffsetMapping.ForField.Unresolved.Factory.INSTANCE, OffsetMapping.ForFieldHandle.Unresolved.GetterFactory.INSTANCE, OffsetMapping.ForFieldHandle.Unresolved.SetterFactory.INSTANCE, OffsetMapping.ForOrigin.Factory.INSTANCE, OffsetMapping.ForStubValue.Factory.INSTANCE, new OffsetMapping.ForStackManipulation.OfDefaultValue(Unused.class), OffsetMapping.ForCurrent.Factory.INSTANCE), (List) list));
                    }

                    public static WithCustomMapping withCustomMapping() {
                        return new WithCustomMapping(Dispatcher.ForRegularInvocation.Factory.INSTANCE, Collections.emptyMap());
                    }

                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step
                    public Resolution resolve(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, TypeDescription.Generic generic3, Map<Integer, Integer> map, int i) {
                        ArrayList arrayList = new ArrayList(this.offsetMappings.size() + 3);
                        arrayList.add(generic3.represents(Void.TYPE) ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.of(generic3).storeAt(i));
                        arrayList.add(this.dispatcher.initialize());
                        Iterator<OffsetMapping.Resolved> it = this.offsetMappings.iterator();
                        while (it.hasNext()) {
                            arrayList.add(it.next().apply(typeDescription, member, generic, generic2, generic3, methodHandle, map, i));
                        }
                        arrayList.add(this.dispatcher.apply(typeDescription, member, methodHandle));
                        return new Simple(new StackManipulation.Compound(arrayList), this.returned);
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    protected static class Factory implements Factory {
                        private final MethodDescription.InDefinedShape delegate;
                        private final Dispatcher dispatcher;
                        private final List<OffsetMapping> offsetMappings;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (obj == null || getClass() != obj.getClass()) {
                                return false;
                            }
                            Factory factory = (Factory) obj;
                            return this.delegate.equals(factory.delegate) && this.dispatcher.equals(factory.dispatcher) && this.offsetMappings.equals(factory.offsetMappings);
                        }

                        public int hashCode() {
                            return (((((getClass().hashCode() * 31) + this.delegate.hashCode()) * 31) + this.dispatcher.hashCode()) * 31) + this.offsetMappings.hashCode();
                        }

                        protected Factory(MethodDescription.InDefinedShape inDefinedShape, Dispatcher dispatcher, List<? extends OffsetMapping.Factory<?>> list) {
                            HashMap map = new HashMap();
                            for (OffsetMapping.Factory<?> factory : list) {
                                map.put(TypeDescription.ForLoadedType.of(factory.getAnnotationType()), factory);
                            }
                            this.offsetMappings = new ArrayList(list.size());
                            if (inDefinedShape.isMethod() && !inDefinedShape.isStatic()) {
                                OffsetMapping offsetMapping = null;
                                for (AnnotationDescription annotationDescription : inDefinedShape.getDeclaredAnnotations()) {
                                    OffsetMapping.Factory factory2 = (OffsetMapping.Factory) map.get(annotationDescription.getAnnotationType());
                                    if (factory2 != null) {
                                        OffsetMapping offsetMappingMake = factory2.make(inDefinedShape, annotationDescription.prepare(factory2.getAnnotationType()));
                                        if (offsetMapping != null) {
                                            throw new IllegalStateException(inDefinedShape + " is bound to both " + offsetMappingMake + " and " + offsetMapping);
                                        }
                                        offsetMapping = offsetMappingMake;
                                    }
                                }
                                this.offsetMappings.add(offsetMapping == null ? new OffsetMapping.ForThisReference(inDefinedShape.getDeclaringType().asGenericType(), null, Source.SUBSTITUTED_ELEMENT, false) : offsetMapping);
                            }
                            for (int i = 0; i < inDefinedShape.getParameters().size(); i++) {
                                ParameterDescription.InDefinedShape inDefinedShape2 = (ParameterDescription.InDefinedShape) inDefinedShape.getParameters().get(i);
                                OffsetMapping forArgument = null;
                                for (AnnotationDescription annotationDescription2 : inDefinedShape2.getDeclaredAnnotations()) {
                                    OffsetMapping.Factory factory3 = (OffsetMapping.Factory) map.get(annotationDescription2.getAnnotationType());
                                    if (factory3 != null) {
                                        OffsetMapping offsetMappingMake2 = factory3.make(inDefinedShape2, annotationDescription2.prepare(factory3.getAnnotationType()));
                                        if (forArgument != null) {
                                            throw new IllegalStateException(inDefinedShape2 + " is bound to both " + offsetMappingMake2 + " and " + forArgument);
                                        }
                                        forArgument = offsetMappingMake2;
                                    }
                                }
                                List<OffsetMapping> list2 = this.offsetMappings;
                                if (forArgument == null) {
                                    forArgument = new OffsetMapping.ForArgument(inDefinedShape2.getType(), i, null, Source.SUBSTITUTED_ELEMENT, false);
                                }
                                list2.add(forArgument);
                            }
                            this.delegate = inDefinedShape;
                            this.dispatcher = dispatcher;
                        }

                        @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.Factory
                        public Step make(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                            ArrayList arrayList = new ArrayList(this.offsetMappings.size());
                            Iterator<OffsetMapping> it = this.offsetMappings.iterator();
                            while (it.hasNext()) {
                                arrayList.add(it.next().resolve(assigner, typing, typeDescription, methodDescription));
                            }
                            return new ForDelegation(this.delegate.getReturnType(), this.dispatcher.resolve(typeDescription, methodDescription), arrayList);
                        }
                    }

                    public interface OffsetMapping {
                        Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription);

                        public interface Resolved {
                            StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i);

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class ForStackManipulation implements Resolved {
                                private final StackManipulation stackManipulation;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.stackManipulation.equals(((ForStackManipulation) obj).stackManipulation);
                                }

                                public int hashCode() {
                                    return (getClass().hashCode() * 31) + this.stackManipulation.hashCode();
                                }

                                public ForStackManipulation(StackManipulation stackManipulation) {
                                    this.stackManipulation = stackManipulation;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    return this.stackManipulation;
                                }
                            }
                        }

                        public interface Factory<T extends Annotation> {
                            Class<T> getAnnotationType();

                            OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<T> loadable);

                            OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<T> loadable);

                            public static abstract class AbstractBase<S extends Annotation> implements Factory<S> {
                                protected abstract OffsetMapping make(TypeDescription.Generic generic, AnnotationDescription.Loadable<S> loadable);

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<S> loadable) {
                                    return make(inDefinedShape.getDeclaringType().asGenericType(), loadable);
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<S> loadable) {
                                    return make(inDefinedShape.getType(), loadable);
                                }
                            }

                            public static abstract class WithParameterSupportOnly<S extends Annotation> implements Factory<S> {
                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<S> loadable) {
                                    throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class Simple<S extends Annotation> extends AbstractBase<S> {
                                private final Class<S> annotationType;
                                private final OffsetMapping offsetMapping;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Simple simple = (Simple) obj;
                                    return this.annotationType.equals(simple.annotationType) && this.offsetMapping.equals(simple.offsetMapping);
                                }

                                public int hashCode() {
                                    return (((getClass().hashCode() * 31) + this.annotationType.hashCode()) * 31) + this.offsetMapping.hashCode();
                                }

                                public Simple(Class<S> cls, OffsetMapping offsetMapping) {
                                    this.annotationType = cls;
                                    this.offsetMapping = offsetMapping;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<S> getAnnotationType() {
                                    return this.annotationType;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory.AbstractBase
                                protected OffsetMapping make(TypeDescription.Generic generic, AnnotationDescription.Loadable<S> loadable) {
                                    return this.offsetMapping;
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForStackManipulation implements OffsetMapping {
                            private final StackManipulation stackManipulation;
                            private final TypeDescription.Generic targetType;
                            private final TypeDescription.Generic typeDescription;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                if (obj == null || getClass() != obj.getClass()) {
                                    return false;
                                }
                                ForStackManipulation forStackManipulation = (ForStackManipulation) obj;
                                return this.stackManipulation.equals(forStackManipulation.stackManipulation) && this.typeDescription.equals(forStackManipulation.typeDescription) && this.targetType.equals(forStackManipulation.targetType);
                            }

                            public int hashCode() {
                                return (((((getClass().hashCode() * 31) + this.stackManipulation.hashCode()) * 31) + this.typeDescription.hashCode()) * 31) + this.targetType.hashCode();
                            }

                            public ForStackManipulation(StackManipulation stackManipulation, TypeDescription.Generic generic, TypeDescription.Generic generic2) {
                                this.targetType = generic2;
                                this.stackManipulation = stackManipulation;
                                this.typeDescription = generic;
                            }

                            public static <S extends Annotation> Factory<S> of(Class<S> cls, @MaybeNull Object obj) {
                                if (obj == null) {
                                    return new OfDefaultValue(cls);
                                }
                                return new Factory(cls, ConstantValue.Simple.wrap(obj));
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                return new Resolved(assigner, typing, this.stackManipulation, this.typeDescription, this.targetType);
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final Assigner assigner;
                                private final StackManipulation stackManipulation;
                                private final TypeDescription.Generic targetType;
                                private final TypeDescription.Generic typeDescription;
                                private final Assigner.Typing typing;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.typing.equals(resolved.typing) && this.assigner.equals(resolved.assigner) && this.stackManipulation.equals(resolved.stackManipulation) && this.typeDescription.equals(resolved.typeDescription) && this.targetType.equals(resolved.targetType);
                                }

                                public int hashCode() {
                                    return (((((((((getClass().hashCode() * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode()) * 31) + this.stackManipulation.hashCode()) * 31) + this.typeDescription.hashCode()) * 31) + this.targetType.hashCode();
                                }

                                protected Resolved(Assigner assigner, Assigner.Typing typing, StackManipulation stackManipulation, TypeDescription.Generic generic, TypeDescription.Generic generic2) {
                                    this.assigner = assigner;
                                    this.typing = typing;
                                    this.stackManipulation = stackManipulation;
                                    this.typeDescription = generic;
                                    this.targetType = generic2;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    StackManipulation stackManipulationAssign = this.assigner.assign(this.typeDescription, this.targetType, this.typing);
                                    if (!stackManipulationAssign.isValid()) {
                                        throw new IllegalStateException("Cannot assign " + this.typeDescription + " to " + this.targetType);
                                    }
                                    return new StackManipulation.Compound(this.stackManipulation, stackManipulationAssign);
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class OfDefaultValue<T extends Annotation> implements Factory<T> {
                                private final Class<T> annotationType;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.annotationType.equals(((OfDefaultValue) obj).annotationType);
                                }

                                public int hashCode() {
                                    return (getClass().hashCode() * 31) + this.annotationType.hashCode();
                                }

                                public OfDefaultValue(Class<T> cls) {
                                    this.annotationType = cls;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<T> getAnnotationType() {
                                    return this.annotationType;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<T> loadable) {
                                    throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<T> loadable) {
                                    return new ForStackManipulation(DefaultValue.of(inDefinedShape.getType()), inDefinedShape.getType(), inDefinedShape.getType());
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class OfAnnotationProperty<T extends Annotation> extends Factory.WithParameterSupportOnly<T> {
                                private final Class<T> annotationType;
                                private final MethodDescription.InDefinedShape property;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    OfAnnotationProperty ofAnnotationProperty = (OfAnnotationProperty) obj;
                                    return this.annotationType.equals(ofAnnotationProperty.annotationType) && this.property.equals(ofAnnotationProperty.property);
                                }

                                public int hashCode() {
                                    return (((getClass().hashCode() * 31) + this.annotationType.hashCode()) * 31) + this.property.hashCode();
                                }

                                protected OfAnnotationProperty(Class<T> cls, MethodDescription.InDefinedShape inDefinedShape) {
                                    this.annotationType = cls;
                                    this.property = inDefinedShape;
                                }

                                public static <S extends Annotation> Factory<S> of(Class<S> cls, String str) {
                                    if (!cls.isAnnotation()) {
                                        throw new IllegalArgumentException("Not an annotation type: " + cls);
                                    }
                                    try {
                                        return new OfAnnotationProperty(cls, new MethodDescription.ForLoadedMethod(cls.getMethod(str, new Class[0])));
                                    } catch (NoSuchMethodException e) {
                                        throw new IllegalArgumentException("Cannot find a property " + str + " on " + cls, e);
                                    }
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<T> getAnnotationType() {
                                    return this.annotationType;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<T> loadable) {
                                    ConstantValue constantValueWrapOrNull = ConstantValue.Simple.wrapOrNull(loadable.getValue(this.property).resolve());
                                    if (constantValueWrapOrNull == null) {
                                        throw new IllegalStateException("Not a constant value property: " + this.property);
                                    }
                                    return new ForStackManipulation(constantValueWrapOrNull.toStackManipulation(), constantValueWrapOrNull.getTypeDescription().asGenericType(), inDefinedShape.getType());
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class OfSerializedConstant<T extends Annotation> extends Factory.AbstractBase<T> {
                                private final Class<T> annotationType;
                                private final StackManipulation deserialization;
                                private final TypeDescription.Generic typeDescription;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    OfSerializedConstant ofSerializedConstant = (OfSerializedConstant) obj;
                                    return this.annotationType.equals(ofSerializedConstant.annotationType) && this.deserialization.equals(ofSerializedConstant.deserialization) && this.typeDescription.equals(ofSerializedConstant.typeDescription);
                                }

                                public int hashCode() {
                                    return (((((getClass().hashCode() * 31) + this.annotationType.hashCode()) * 31) + this.deserialization.hashCode()) * 31) + this.typeDescription.hashCode();
                                }

                                protected OfSerializedConstant(Class<T> cls, StackManipulation stackManipulation, TypeDescription.Generic generic) {
                                    this.annotationType = cls;
                                    this.deserialization = stackManipulation;
                                    this.typeDescription = generic;
                                }

                                public static <S extends Annotation, U extends Serializable> Factory<S> of(Class<S> cls, U u, Class<? super U> cls2) {
                                    if (!cls2.isInstance(u)) {
                                        throw new IllegalArgumentException(u + " is no instance of " + cls2);
                                    }
                                    return new OfSerializedConstant(cls, SerializedConstant.of(u), TypeDescription.ForLoadedType.of(cls2).asGenericType());
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<T> getAnnotationType() {
                                    return this.annotationType;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory.AbstractBase
                                protected OffsetMapping make(TypeDescription.Generic generic, AnnotationDescription.Loadable<T> loadable) {
                                    return new ForStackManipulation(this.deserialization, this.typeDescription, generic);
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class OfDynamicInvocation<T extends Annotation> extends Factory.AbstractBase<T> {
                                private final Class<T> annotationType;
                                private final List<? extends JavaConstant> arguments;
                                private final MethodDescription.InDefinedShape bootstrapMethod;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    OfDynamicInvocation ofDynamicInvocation = (OfDynamicInvocation) obj;
                                    return this.annotationType.equals(ofDynamicInvocation.annotationType) && this.bootstrapMethod.equals(ofDynamicInvocation.bootstrapMethod) && this.arguments.equals(ofDynamicInvocation.arguments);
                                }

                                public int hashCode() {
                                    return (((((getClass().hashCode() * 31) + this.annotationType.hashCode()) * 31) + this.bootstrapMethod.hashCode()) * 31) + this.arguments.hashCode();
                                }

                                public OfDynamicInvocation(Class<T> cls, MethodDescription.InDefinedShape inDefinedShape, List<? extends JavaConstant> list) {
                                    this.annotationType = cls;
                                    this.bootstrapMethod = inDefinedShape;
                                    this.arguments = list;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<T> getAnnotationType() {
                                    return this.annotationType;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory.AbstractBase
                                protected OffsetMapping make(TypeDescription.Generic generic, AnnotationDescription.Loadable<T> loadable) {
                                    if (!generic.isInterface()) {
                                        throw new IllegalArgumentException(generic + " is not an interface");
                                    }
                                    if (!generic.getInterfaces().isEmpty()) {
                                        throw new IllegalArgumentException(generic + " must not extend other interfaces");
                                    }
                                    if (!generic.isPublic()) {
                                        throw new IllegalArgumentException(generic + " is mot public");
                                    }
                                    MethodList methodListFilter = generic.getDeclaredMethods().filter(ElementMatchers.isAbstract());
                                    if (methodListFilter.size() != 1) {
                                        throw new IllegalArgumentException(generic + " must declare exactly one abstract method");
                                    }
                                    return new ForStackManipulation(MethodInvocation.invoke(this.bootstrapMethod).dynamic(((MethodDescription) methodListFilter.getOnly()).getInternalName(), generic.asErasure(), Collections.emptyList(), this.arguments), generic, generic);
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class Factory<T extends Annotation> extends Factory.AbstractBase<T> {
                                private final Class<T> annotationType;
                                private final StackManipulation stackManipulation;
                                private final TypeDescription.Generic typeDescription;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Factory factory = (Factory) obj;
                                    return this.annotationType.equals(factory.annotationType) && this.stackManipulation.equals(factory.stackManipulation) && this.typeDescription.equals(factory.typeDescription);
                                }

                                public int hashCode() {
                                    return (((((getClass().hashCode() * 31) + this.annotationType.hashCode()) * 31) + this.stackManipulation.hashCode()) * 31) + this.typeDescription.hashCode();
                                }

                                public Factory(Class<T> cls, ConstantValue constantValue) {
                                    this(cls, constantValue.toStackManipulation(), constantValue.getTypeDescription().asGenericType());
                                }

                                public Factory(Class<T> cls, StackManipulation stackManipulation, TypeDescription.Generic generic) {
                                    this.annotationType = cls;
                                    this.stackManipulation = stackManipulation;
                                    this.typeDescription = generic;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<T> getAnnotationType() {
                                    return this.annotationType;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory.AbstractBase
                                protected OffsetMapping make(TypeDescription.Generic generic, AnnotationDescription.Loadable<T> loadable) {
                                    return new ForStackManipulation(this.stackManipulation, this.typeDescription, generic);
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForArgument implements OffsetMapping {
                            private final int index;
                            private final boolean optional;
                            private final Source source;
                            private final TypeDescription.Generic targetType;

                            @MaybeNull
                            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                            private final Assigner.Typing typing;

                            /* JADX WARN: Code restructure failed: missing block: B:23:0x0032, code lost:
                            
                                if (r2 != null) goto L24;
                             */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
                                /*
                                    r4 = this;
                                    r0 = 1
                                    if (r4 != r5) goto L4
                                    return r0
                                L4:
                                    r1 = 0
                                    if (r5 != 0) goto L8
                                    return r1
                                L8:
                                    java.lang.Class r2 = r4.getClass()
                                    java.lang.Class r3 = r5.getClass()
                                    if (r2 == r3) goto L13
                                    return r1
                                L13:
                                    int r2 = r4.index
                                    net.bytebuddy.asm.MemberSubstitution$Substitution$Chain$Step$ForDelegation$OffsetMapping$ForArgument r5 = (net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForArgument) r5
                                    int r3 = r5.index
                                    if (r2 == r3) goto L1c
                                    return r1
                                L1c:
                                    boolean r2 = r4.optional
                                    boolean r3 = r5.optional
                                    if (r2 == r3) goto L23
                                    return r1
                                L23:
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r2 = r4.typing
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r3 = r5.typing
                                    if (r3 == 0) goto L32
                                    if (r2 == 0) goto L34
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L35
                                    return r1
                                L32:
                                    if (r2 == 0) goto L35
                                L34:
                                    return r1
                                L35:
                                    net.bytebuddy.asm.MemberSubstitution$Source r2 = r4.source
                                    net.bytebuddy.asm.MemberSubstitution$Source r3 = r5.source
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L40
                                    return r1
                                L40:
                                    net.bytebuddy.description.type.TypeDescription$Generic r2 = r4.targetType
                                    net.bytebuddy.description.type.TypeDescription$Generic r5 = r5.targetType
                                    boolean r5 = r2.equals(r5)
                                    if (r5 != 0) goto L4b
                                    return r1
                                L4b:
                                    return r0
                                */
                                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForArgument.equals(java.lang.Object):boolean");
                            }

                            public int hashCode() {
                                int iHashCode = ((((getClass().hashCode() * 31) + this.targetType.hashCode()) * 31) + this.index) * 31;
                                Assigner.Typing typing = this.typing;
                                if (typing != null) {
                                    iHashCode += typing.hashCode();
                                }
                                return (((iHashCode * 31) + this.source.hashCode()) * 31) + (this.optional ? 1 : 0);
                            }

                            public ForArgument(TypeDescription.Generic generic, int i, @MaybeNull Assigner.Typing typing, Source source, boolean z) {
                                this.targetType = generic;
                                this.index = i;
                                this.typing = typing;
                                this.source = source;
                                this.optional = z;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                TypeDescription.Generic generic = this.targetType;
                                int i = this.index;
                                Assigner.Typing typing2 = this.typing;
                                return new Resolved(generic, i, typing2 == null ? typing : typing2, this.source, this.optional, assigner, methodDescription);
                            }

                            protected enum Factory implements Factory<Argument> {
                                INSTANCE;

                                private static final MethodDescription.InDefinedShape ARGUMENT_OPTIONAL;
                                private static final MethodDescription.InDefinedShape ARGUMENT_SOURCE;
                                private static final MethodDescription.InDefinedShape ARGUMENT_TYPING;
                                private static final MethodDescription.InDefinedShape ARGUMENT_VALUE;

                                static {
                                    MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(Argument.class).getDeclaredMethods();
                                    ARGUMENT_VALUE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("value")).getOnly();
                                    ARGUMENT_TYPING = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("typing")).getOnly();
                                    ARGUMENT_SOURCE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named(FirebaseAnalytics.Param.SOURCE)).getOnly();
                                    ARGUMENT_OPTIONAL = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named(SchemaSymbols.ATTVAL_OPTIONAL)).getOnly();
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<Argument> getAnnotationType() {
                                    return Argument.class;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<Argument> loadable) {
                                    return new ForArgument(inDefinedShape.getDeclaringType().asGenericType(), ((Integer) loadable.getValue(ARGUMENT_VALUE).resolve(Integer.class)).intValue(), (Assigner.Typing) ((EnumerationDescription) loadable.getValue(ARGUMENT_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class), (Source) ((EnumerationDescription) loadable.getValue(ARGUMENT_SOURCE).resolve(EnumerationDescription.class)).load(Source.class), ((Boolean) loadable.getValue(ARGUMENT_OPTIONAL).resolve(Boolean.class)).booleanValue());
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<Argument> loadable) {
                                    int iIntValue = ((Integer) loadable.getValue(ARGUMENT_VALUE).resolve(Integer.class)).intValue();
                                    if (iIntValue < 0) {
                                        throw new IllegalStateException("Cannot assign negative parameter index " + iIntValue + " for " + inDefinedShape);
                                    }
                                    return new ForArgument(inDefinedShape.getType(), iIntValue, (Assigner.Typing) ((EnumerationDescription) loadable.getValue(ARGUMENT_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class), (Source) ((EnumerationDescription) loadable.getValue(ARGUMENT_SOURCE).resolve(EnumerationDescription.class)).load(Source.class), ((Boolean) loadable.getValue(ARGUMENT_OPTIONAL).resolve(Boolean.class)).booleanValue());
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final Assigner assigner;
                                private final int index;
                                private final MethodDescription instrumentedMethod;
                                private final boolean optional;
                                private final Source source;
                                private final TypeDescription.Generic targetType;
                                private final Assigner.Typing typing;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.index == resolved.index && this.optional == resolved.optional && this.typing.equals(resolved.typing) && this.source.equals(resolved.source) && this.targetType.equals(resolved.targetType) && this.assigner.equals(resolved.assigner) && this.instrumentedMethod.equals(resolved.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((((((((((((getClass().hashCode() * 31) + this.targetType.hashCode()) * 31) + this.index) * 31) + this.typing.hashCode()) * 31) + this.source.hashCode()) * 31) + (this.optional ? 1 : 0)) * 31) + this.assigner.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Resolved(TypeDescription.Generic generic, int i, Assigner.Typing typing, Source source, boolean z, Assigner assigner, MethodDescription methodDescription) {
                                    this.targetType = generic;
                                    this.index = i;
                                    this.typing = typing;
                                    this.source = source;
                                    this.optional = z;
                                    this.assigner = assigner;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    Source.Value valueArgument = this.source.argument(this.index, generic, map, member, this.instrumentedMethod);
                                    if (valueArgument != null) {
                                        StackManipulation stackManipulationAssign = this.assigner.assign(valueArgument.getTypeDescription(), this.targetType, this.typing);
                                        if (!stackManipulationAssign.isValid()) {
                                            throw new IllegalStateException("Cannot assign " + valueArgument.getTypeDescription() + " to " + this.targetType);
                                        }
                                        return new StackManipulation.Compound(MethodVariableAccess.of(valueArgument.getTypeDescription()).loadFrom(valueArgument.getOffset()), stackManipulationAssign);
                                    }
                                    if (this.optional) {
                                        return DefaultValue.of(this.targetType);
                                    }
                                    throw new IllegalStateException("No argument with index " + this.index + " available for " + member);
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForThisReference implements OffsetMapping {
                            private final boolean optional;
                            private final Source source;
                            private final TypeDescription.Generic targetType;

                            @MaybeNull
                            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                            private final Assigner.Typing typing;

                            /* JADX WARN: Code restructure failed: missing block: B:20:0x002b, code lost:
                            
                                if (r2 != null) goto L21;
                             */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
                                /*
                                    r4 = this;
                                    r0 = 1
                                    if (r4 != r5) goto L4
                                    return r0
                                L4:
                                    r1 = 0
                                    if (r5 != 0) goto L8
                                    return r1
                                L8:
                                    java.lang.Class r2 = r4.getClass()
                                    java.lang.Class r3 = r5.getClass()
                                    if (r2 == r3) goto L13
                                    return r1
                                L13:
                                    boolean r2 = r4.optional
                                    net.bytebuddy.asm.MemberSubstitution$Substitution$Chain$Step$ForDelegation$OffsetMapping$ForThisReference r5 = (net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForThisReference) r5
                                    boolean r3 = r5.optional
                                    if (r2 == r3) goto L1c
                                    return r1
                                L1c:
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r2 = r4.typing
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r3 = r5.typing
                                    if (r3 == 0) goto L2b
                                    if (r2 == 0) goto L2d
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L2e
                                    return r1
                                L2b:
                                    if (r2 == 0) goto L2e
                                L2d:
                                    return r1
                                L2e:
                                    net.bytebuddy.asm.MemberSubstitution$Source r2 = r4.source
                                    net.bytebuddy.asm.MemberSubstitution$Source r3 = r5.source
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L39
                                    return r1
                                L39:
                                    net.bytebuddy.description.type.TypeDescription$Generic r2 = r4.targetType
                                    net.bytebuddy.description.type.TypeDescription$Generic r5 = r5.targetType
                                    boolean r5 = r2.equals(r5)
                                    if (r5 != 0) goto L44
                                    return r1
                                L44:
                                    return r0
                                */
                                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForThisReference.equals(java.lang.Object):boolean");
                            }

                            public int hashCode() {
                                int iHashCode = ((getClass().hashCode() * 31) + this.targetType.hashCode()) * 31;
                                Assigner.Typing typing = this.typing;
                                if (typing != null) {
                                    iHashCode += typing.hashCode();
                                }
                                return (((iHashCode * 31) + this.source.hashCode()) * 31) + (this.optional ? 1 : 0);
                            }

                            public ForThisReference(TypeDescription.Generic generic, @MaybeNull Assigner.Typing typing, Source source, boolean z) {
                                this.targetType = generic;
                                this.typing = typing;
                                this.source = source;
                                this.optional = z;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                TypeDescription.Generic generic = this.targetType;
                                Assigner.Typing typing2 = this.typing;
                                return new Resolved(generic, typing2 == null ? typing : typing2, this.source, this.optional, assigner, methodDescription);
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final Assigner assigner;
                                private final MethodDescription instrumentedMethod;
                                private final boolean optional;
                                private final Source source;
                                private final TypeDescription.Generic targetType;
                                private final Assigner.Typing typing;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.optional == resolved.optional && this.typing.equals(resolved.typing) && this.source.equals(resolved.source) && this.targetType.equals(resolved.targetType) && this.assigner.equals(resolved.assigner) && this.instrumentedMethod.equals(resolved.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((((((((((getClass().hashCode() * 31) + this.targetType.hashCode()) * 31) + this.typing.hashCode()) * 31) + this.source.hashCode()) * 31) + (this.optional ? 1 : 0)) * 31) + this.assigner.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Resolved(TypeDescription.Generic generic, Assigner.Typing typing, Source source, boolean z, Assigner assigner, MethodDescription methodDescription) {
                                    this.targetType = generic;
                                    this.typing = typing;
                                    this.source = source;
                                    this.optional = z;
                                    this.assigner = assigner;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    Source.Value valueSelf = this.source.self(generic, map, member, this.instrumentedMethod);
                                    if (valueSelf != null) {
                                        StackManipulation stackManipulationAssign = this.assigner.assign(valueSelf.getTypeDescription(), this.targetType, this.typing);
                                        if (!stackManipulationAssign.isValid()) {
                                            throw new IllegalStateException("Cannot assign " + valueSelf.getTypeDescription() + " to " + this.targetType);
                                        }
                                        return new StackManipulation.Compound(MethodVariableAccess.of(valueSelf.getTypeDescription()).loadFrom(valueSelf.getOffset()), stackManipulationAssign);
                                    }
                                    if (this.optional) {
                                        return DefaultValue.of(this.targetType);
                                    }
                                    throw new IllegalStateException("No this reference available for " + member);
                                }
                            }

                            protected enum Factory implements Factory<This> {
                                INSTANCE;

                                private static final MethodDescription.InDefinedShape THIS_OPTIONAL;
                                private static final MethodDescription.InDefinedShape THIS_SOURCE;
                                private static final MethodDescription.InDefinedShape THIS_TYPING;

                                static {
                                    MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(This.class).getDeclaredMethods();
                                    THIS_TYPING = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("typing")).getOnly();
                                    THIS_SOURCE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named(FirebaseAnalytics.Param.SOURCE)).getOnly();
                                    THIS_OPTIONAL = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named(SchemaSymbols.ATTVAL_OPTIONAL)).getOnly();
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<This> getAnnotationType() {
                                    return This.class;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<This> loadable) {
                                    return new ForThisReference(inDefinedShape.getDeclaringType().asGenericType(), (Assigner.Typing) ((EnumerationDescription) loadable.getValue(THIS_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class), (Source) ((EnumerationDescription) loadable.getValue(THIS_SOURCE).resolve(EnumerationDescription.class)).load(Source.class), ((Boolean) loadable.getValue(THIS_OPTIONAL).resolve(Boolean.class)).booleanValue());
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<This> loadable) {
                                    return new ForThisReference(inDefinedShape.getType(), (Assigner.Typing) ((EnumerationDescription) loadable.getValue(THIS_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class), (Source) ((EnumerationDescription) loadable.getValue(THIS_SOURCE).resolve(EnumerationDescription.class)).load(Source.class), ((Boolean) loadable.getValue(THIS_OPTIONAL).resolve(Boolean.class)).booleanValue());
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForAllArguments implements OffsetMapping {
                            private final boolean includeSelf;
                            private final boolean nullIfEmpty;
                            private final Source source;
                            private final TypeDescription.Generic targetComponentType;

                            @MaybeNull
                            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                            private final Assigner.Typing typing;

                            /* JADX WARN: Code restructure failed: missing block: B:23:0x0032, code lost:
                            
                                if (r2 != null) goto L24;
                             */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
                                /*
                                    r4 = this;
                                    r0 = 1
                                    if (r4 != r5) goto L4
                                    return r0
                                L4:
                                    r1 = 0
                                    if (r5 != 0) goto L8
                                    return r1
                                L8:
                                    java.lang.Class r2 = r4.getClass()
                                    java.lang.Class r3 = r5.getClass()
                                    if (r2 == r3) goto L13
                                    return r1
                                L13:
                                    boolean r2 = r4.includeSelf
                                    net.bytebuddy.asm.MemberSubstitution$Substitution$Chain$Step$ForDelegation$OffsetMapping$ForAllArguments r5 = (net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForAllArguments) r5
                                    boolean r3 = r5.includeSelf
                                    if (r2 == r3) goto L1c
                                    return r1
                                L1c:
                                    boolean r2 = r4.nullIfEmpty
                                    boolean r3 = r5.nullIfEmpty
                                    if (r2 == r3) goto L23
                                    return r1
                                L23:
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r2 = r4.typing
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r3 = r5.typing
                                    if (r3 == 0) goto L32
                                    if (r2 == 0) goto L34
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L35
                                    return r1
                                L32:
                                    if (r2 == 0) goto L35
                                L34:
                                    return r1
                                L35:
                                    net.bytebuddy.asm.MemberSubstitution$Source r2 = r4.source
                                    net.bytebuddy.asm.MemberSubstitution$Source r3 = r5.source
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L40
                                    return r1
                                L40:
                                    net.bytebuddy.description.type.TypeDescription$Generic r2 = r4.targetComponentType
                                    net.bytebuddy.description.type.TypeDescription$Generic r5 = r5.targetComponentType
                                    boolean r5 = r2.equals(r5)
                                    if (r5 != 0) goto L4b
                                    return r1
                                L4b:
                                    return r0
                                */
                                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForAllArguments.equals(java.lang.Object):boolean");
                            }

                            public int hashCode() {
                                int iHashCode = ((getClass().hashCode() * 31) + this.targetComponentType.hashCode()) * 31;
                                Assigner.Typing typing = this.typing;
                                if (typing != null) {
                                    iHashCode += typing.hashCode();
                                }
                                return (((((iHashCode * 31) + this.source.hashCode()) * 31) + (this.includeSelf ? 1 : 0)) * 31) + (this.nullIfEmpty ? 1 : 0);
                            }

                            public ForAllArguments(TypeDescription.Generic generic, @MaybeNull Assigner.Typing typing, Source source, boolean z, boolean z2) {
                                this.targetComponentType = generic;
                                this.typing = typing;
                                this.source = source;
                                this.includeSelf = z;
                                this.nullIfEmpty = z2;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                TypeDescription.Generic generic = this.targetComponentType;
                                Assigner.Typing typing2 = this.typing;
                                return new Resolved(generic, typing2 == null ? typing : typing2, this.source, this.includeSelf, this.nullIfEmpty, assigner, methodDescription);
                            }

                            protected enum Factory implements Factory<AllArguments> {
                                INSTANCE;

                                private static final MethodDescription.InDefinedShape ALL_ARGUMENTS_INCLUDE_SELF;
                                private static final MethodDescription.InDefinedShape ALL_ARGUMENTS_NULL_IF_EMPTY;
                                private static final MethodDescription.InDefinedShape ALL_ARGUMENTS_SOURCE;
                                private static final MethodDescription.InDefinedShape ALL_ARGUMENTS_TYPING;

                                static {
                                    MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(AllArguments.class).getDeclaredMethods();
                                    ALL_ARGUMENTS_TYPING = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("typing")).getOnly();
                                    ALL_ARGUMENTS_SOURCE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named(FirebaseAnalytics.Param.SOURCE)).getOnly();
                                    ALL_ARGUMENTS_INCLUDE_SELF = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("includeSelf")).getOnly();
                                    ALL_ARGUMENTS_NULL_IF_EMPTY = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("nullIfEmpty")).getOnly();
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<AllArguments> getAnnotationType() {
                                    return AllArguments.class;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<AllArguments> loadable) {
                                    throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<AllArguments> loadable) {
                                    if (!inDefinedShape.getType().isArray()) {
                                        throw new IllegalStateException("Expected array as parameter type for " + inDefinedShape);
                                    }
                                    return new ForAllArguments(inDefinedShape.getType().getComponentType(), (Assigner.Typing) ((EnumerationDescription) loadable.getValue(ALL_ARGUMENTS_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class), (Source) ((EnumerationDescription) loadable.getValue(ALL_ARGUMENTS_SOURCE).resolve(EnumerationDescription.class)).load(Source.class), ((Boolean) loadable.getValue(ALL_ARGUMENTS_INCLUDE_SELF).resolve(Boolean.class)).booleanValue(), ((Boolean) loadable.getValue(ALL_ARGUMENTS_NULL_IF_EMPTY).resolve(Boolean.class)).booleanValue());
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final Assigner assigner;
                                private final boolean includeSelf;
                                private final MethodDescription instrumentedMethod;
                                private final boolean nullIfEmpty;
                                private final Source source;
                                private final TypeDescription.Generic targetComponentType;
                                private final Assigner.Typing typing;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.includeSelf == resolved.includeSelf && this.nullIfEmpty == resolved.nullIfEmpty && this.typing.equals(resolved.typing) && this.source.equals(resolved.source) && this.targetComponentType.equals(resolved.targetComponentType) && this.assigner.equals(resolved.assigner) && this.instrumentedMethod.equals(resolved.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((((((((((((getClass().hashCode() * 31) + this.targetComponentType.hashCode()) * 31) + this.typing.hashCode()) * 31) + this.source.hashCode()) * 31) + (this.includeSelf ? 1 : 0)) * 31) + (this.nullIfEmpty ? 1 : 0)) * 31) + this.assigner.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Resolved(TypeDescription.Generic generic, Assigner.Typing typing, Source source, boolean z, boolean z2, Assigner assigner, MethodDescription methodDescription) {
                                    this.targetComponentType = generic;
                                    this.typing = typing;
                                    this.source = source;
                                    this.includeSelf = z;
                                    this.nullIfEmpty = z2;
                                    this.assigner = assigner;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    List<Source.Value> listArguments = this.source.arguments(this.includeSelf, generic, map, member, this.instrumentedMethod);
                                    if (this.nullIfEmpty && listArguments.isEmpty()) {
                                        return NullConstant.INSTANCE;
                                    }
                                    ArrayList arrayList = new ArrayList();
                                    for (Source.Value value : listArguments) {
                                        StackManipulation stackManipulationAssign = this.assigner.assign(value.getTypeDescription(), this.targetComponentType, this.typing);
                                        if (!stackManipulationAssign.isValid()) {
                                            throw new IllegalStateException("Cannot assign " + value.getTypeDescription() + " to " + this.targetComponentType);
                                        }
                                        arrayList.add(new StackManipulation.Compound(MethodVariableAccess.of(value.getTypeDescription()).loadFrom(value.getOffset()), stackManipulationAssign));
                                    }
                                    return ArrayFactory.forType(this.targetComponentType).withValues(arrayList);
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForSelfCallHandle implements OffsetMapping {
                            private final boolean bound;
                            private final Source source;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                if (obj == null || getClass() != obj.getClass()) {
                                    return false;
                                }
                                ForSelfCallHandle forSelfCallHandle = (ForSelfCallHandle) obj;
                                return this.bound == forSelfCallHandle.bound && this.source.equals(forSelfCallHandle.source);
                            }

                            public int hashCode() {
                                return (((getClass().hashCode() * 31) + this.source.hashCode()) * 31) + (this.bound ? 1 : 0);
                            }

                            public ForSelfCallHandle(Source source, boolean z) {
                                this.source = source;
                                this.bound = z;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                return this.bound ? new Bound(this.source, methodDescription) : new Unbound(this.source, methodDescription);
                            }

                            protected enum Factory implements Factory<SelfCallHandle> {
                                INSTANCE;

                                private static final MethodDescription.InDefinedShape ALL_ARGUMENTS_BOUND;
                                private static final MethodDescription.InDefinedShape ALL_ARGUMENTS_SOURCE;

                                static {
                                    MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(SelfCallHandle.class).getDeclaredMethods();
                                    ALL_ARGUMENTS_SOURCE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named(FirebaseAnalytics.Param.SOURCE)).getOnly();
                                    ALL_ARGUMENTS_BOUND = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("bound")).getOnly();
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<SelfCallHandle> getAnnotationType() {
                                    return SelfCallHandle.class;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<SelfCallHandle> loadable) {
                                    throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<SelfCallHandle> loadable) {
                                    if (!inDefinedShape.getType().asErasure().isAssignableFrom(JavaType.METHOD_HANDLE.getTypeStub())) {
                                        throw new IllegalStateException("Cannot assign method handle to " + inDefinedShape);
                                    }
                                    return new ForSelfCallHandle((Source) ((EnumerationDescription) loadable.getValue(ALL_ARGUMENTS_SOURCE).resolve(EnumerationDescription.class)).load(Source.class), ((Boolean) loadable.getValue(ALL_ARGUMENTS_BOUND).resolve(Boolean.class)).booleanValue());
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Bound implements Resolved {
                                private final MethodDescription instrumentedMethod;
                                private final Source source;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Bound bound = (Bound) obj;
                                    return this.source.equals(bound.source) && this.instrumentedMethod.equals(bound.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((getClass().hashCode() * 31) + this.source.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Bound(Source source, MethodDescription methodDescription) {
                                    this.source = source;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    Source.Value valueSelf = this.source.self(generic, map, member, this.instrumentedMethod);
                                    List<Source.Value> listArguments = this.source.arguments(false, generic, map, member, this.instrumentedMethod);
                                    ArrayList arrayList = new ArrayList(listArguments.size() + (valueSelf == null ? 0 : 2) + 1 + (!listArguments.isEmpty() ? 1 : 0));
                                    arrayList.add(this.source.handle(methodHandle, this.instrumentedMethod).toStackManipulation());
                                    if (valueSelf != null) {
                                        arrayList.add(MethodVariableAccess.of(valueSelf.getTypeDescription()).loadFrom(valueSelf.getOffset()));
                                        arrayList.add(MethodInvocation.invoke((MethodDescription.InDefinedShape) new MethodDescription.Latent(JavaType.METHOD_HANDLE.getTypeStub(), new MethodDescription.Token("bindTo", 1, JavaType.METHOD_HANDLE.getTypeStub().asGenericType(), new TypeList.Generic.Explicit(TypeDefinition.Sort.describe(Object.class))))));
                                    }
                                    if (!listArguments.isEmpty()) {
                                        for (Source.Value value : listArguments) {
                                            arrayList.add(MethodVariableAccess.of(value.getTypeDescription()).loadFrom(value.getOffset()));
                                        }
                                        arrayList.add(MethodInvocation.invoke((MethodDescription.InDefinedShape) new MethodDescription.Latent(JavaType.METHOD_HANDLES.getTypeStub(), new MethodDescription.Token("insertArguments", 9, JavaType.METHOD_HANDLE.getTypeStub().asGenericType(), new TypeList.Generic.Explicit(JavaType.METHOD_HANDLE.getTypeStub(), TypeDefinition.Sort.describe(Integer.TYPE), TypeDefinition.Sort.describe(Object[].class))))));
                                    }
                                    return new StackManipulation.Compound(arrayList);
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Unbound implements Resolved {
                                private final MethodDescription instrumentedMethod;
                                private final Source source;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Unbound unbound = (Unbound) obj;
                                    return this.source.equals(unbound.source) && this.instrumentedMethod.equals(unbound.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((getClass().hashCode() * 31) + this.source.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Unbound(Source source, MethodDescription methodDescription) {
                                    this.source = source;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    return this.source.handle(methodHandle, this.instrumentedMethod).toStackManipulation();
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static abstract class ForField implements OffsetMapping {
                            private static final MethodDescription.InDefinedShape FIELD_VALUE_DECLARING_TYPE;
                            private static final MethodDescription.InDefinedShape FIELD_VALUE_TYPING;
                            private static final MethodDescription.InDefinedShape FIELD_VALUE_VALUE;
                            private final TypeDescription.Generic target;

                            @MaybeNull
                            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                            private final Assigner.Typing typing;

                            /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
                            
                                if (r2 != null) goto L18;
                             */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
                                /*
                                    r4 = this;
                                    r0 = 1
                                    if (r4 != r5) goto L4
                                    return r0
                                L4:
                                    r1 = 0
                                    if (r5 != 0) goto L8
                                    return r1
                                L8:
                                    java.lang.Class r2 = r4.getClass()
                                    java.lang.Class r3 = r5.getClass()
                                    if (r2 == r3) goto L13
                                    return r1
                                L13:
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r2 = r4.typing
                                    net.bytebuddy.asm.MemberSubstitution$Substitution$Chain$Step$ForDelegation$OffsetMapping$ForField r5 = (net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField) r5
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r3 = r5.typing
                                    if (r3 == 0) goto L24
                                    if (r2 == 0) goto L26
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L27
                                    return r1
                                L24:
                                    if (r2 == 0) goto L27
                                L26:
                                    return r1
                                L27:
                                    net.bytebuddy.description.type.TypeDescription$Generic r2 = r4.target
                                    net.bytebuddy.description.type.TypeDescription$Generic r5 = r5.target
                                    boolean r5 = r2.equals(r5)
                                    if (r5 != 0) goto L32
                                    return r1
                                L32:
                                    return r0
                                */
                                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField.equals(java.lang.Object):boolean");
                            }

                            public int hashCode() {
                                int iHashCode = ((getClass().hashCode() * 31) + this.target.hashCode()) * 31;
                                Assigner.Typing typing = this.typing;
                                return typing != null ? iHashCode + typing.hashCode() : iHashCode;
                            }

                            protected abstract FieldDescription resolve(TypeDescription typeDescription, MethodDescription methodDescription);

                            static {
                                MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(FieldValue.class).getDeclaredMethods();
                                FIELD_VALUE_VALUE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("value")).getOnly();
                                FIELD_VALUE_DECLARING_TYPE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("declaringType")).getOnly();
                                FIELD_VALUE_TYPING = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("typing")).getOnly();
                            }

                            protected ForField(TypeDescription.Generic generic, @MaybeNull Assigner.Typing typing) {
                                this.target = generic;
                                this.typing = typing;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                FieldDescription fieldDescriptionResolve = resolve(typeDescription, methodDescription);
                                if (!fieldDescriptionResolve.isStatic() && methodDescription.isStatic()) {
                                    throw new IllegalStateException("Cannot access non-static field " + fieldDescriptionResolve + " from static method " + methodDescription);
                                }
                                TypeDescription.Generic type = fieldDescriptionResolve.getType();
                                TypeDescription.Generic generic = this.target;
                                Assigner.Typing typing2 = this.typing;
                                if (typing2 != null) {
                                    typing = typing2;
                                }
                                StackManipulation stackManipulationAssign = assigner.assign(type, generic, typing);
                                if (!stackManipulationAssign.isValid()) {
                                    throw new IllegalStateException("Cannot assign " + fieldDescriptionResolve + " to " + this.target);
                                }
                                StackManipulation[] stackManipulationArr = new StackManipulation[3];
                                stackManipulationArr[0] = fieldDescriptionResolve.isStatic() ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.loadThis();
                                stackManipulationArr[1] = FieldAccess.forField(fieldDescriptionResolve).read();
                                stackManipulationArr[2] = stackManipulationAssign;
                                return new Resolved.ForStackManipulation(new StackManipulation.Compound(stackManipulationArr));
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static abstract class Unresolved extends ForField {
                                protected static final String BEAN_PROPERTY = "";

                                /* JADX INFO: renamed from: name, reason: collision with root package name */
                                private final String f340name;

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                public boolean equals(@MaybeNull Object obj) {
                                    if (!super.equals(obj)) {
                                        return false;
                                    }
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.f340name.equals(((Unresolved) obj).f340name);
                                }

                                protected abstract FieldLocator fieldLocator(TypeDescription typeDescription);

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                public int hashCode() {
                                    return (super.hashCode() * 31) + this.f340name.hashCode();
                                }

                                protected Unresolved(TypeDescription.Generic generic, Assigner.Typing typing, String str) {
                                    super(generic, typing);
                                    this.f340name = str;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                protected FieldDescription resolve(TypeDescription typeDescription, MethodDescription methodDescription) {
                                    FieldLocator.Resolution resolutionLocate;
                                    FieldLocator fieldLocator = fieldLocator(typeDescription);
                                    if (this.f340name.equals("")) {
                                        resolutionLocate = FieldLocator.Resolution.Simple.ofBeanAccessor(fieldLocator, methodDescription);
                                    } else {
                                        resolutionLocate = fieldLocator.locate(this.f340name);
                                    }
                                    if (!resolutionLocate.isResolved()) {
                                        throw new IllegalStateException("Cannot locate field named " + this.f340name + " for " + typeDescription);
                                    }
                                    return resolutionLocate.getField();
                                }

                                public static class WithImplicitType extends Unresolved {
                                    protected WithImplicitType(TypeDescription.Generic generic, AnnotationDescription.Loadable<FieldValue> loadable) {
                                        this(generic, (Assigner.Typing) ((EnumerationDescription) loadable.getValue(ForField.FIELD_VALUE_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class), (String) loadable.getValue(ForField.FIELD_VALUE_VALUE).resolve(String.class));
                                    }

                                    public WithImplicitType(TypeDescription.Generic generic, Assigner.Typing typing, String str) {
                                        super(generic, typing, str);
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField.Unresolved
                                    protected FieldLocator fieldLocator(TypeDescription typeDescription) {
                                        return new FieldLocator.ForClassHierarchy(typeDescription);
                                    }
                                }

                                @HashCodeAndEqualsPlugin.Enhance
                                public static class WithExplicitType extends Unresolved {
                                    private final TypeDescription declaringType;

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField.Unresolved, net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                    public boolean equals(@MaybeNull Object obj) {
                                        if (!super.equals(obj)) {
                                            return false;
                                        }
                                        if (this == obj) {
                                            return true;
                                        }
                                        return obj != null && getClass() == obj.getClass() && this.declaringType.equals(((WithExplicitType) obj).declaringType);
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField.Unresolved, net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                    public int hashCode() {
                                        return (super.hashCode() * 31) + this.declaringType.hashCode();
                                    }

                                    protected WithExplicitType(TypeDescription.Generic generic, AnnotationDescription.Loadable<FieldValue> loadable, TypeDescription typeDescription) {
                                        this(generic, (Assigner.Typing) ((EnumerationDescription) loadable.getValue(ForField.FIELD_VALUE_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class), (String) loadable.getValue(ForField.FIELD_VALUE_VALUE).resolve(String.class), typeDescription);
                                    }

                                    public WithExplicitType(TypeDescription.Generic generic, Assigner.Typing typing, String str, TypeDescription typeDescription) {
                                        super(generic, typing, str);
                                        this.declaringType = typeDescription;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField.Unresolved
                                    protected FieldLocator fieldLocator(TypeDescription typeDescription) {
                                        if (!this.declaringType.represents(TargetType.class) && !typeDescription.isAssignableTo(this.declaringType)) {
                                            throw new IllegalStateException(this.declaringType + " is no super type of " + typeDescription);
                                        }
                                        return new FieldLocator.ForExactType(TargetType.resolve(this.declaringType, typeDescription));
                                    }
                                }

                                protected enum Factory implements Factory<FieldValue> {
                                    INSTANCE;

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public Class<FieldValue> getAnnotationType() {
                                        return FieldValue.class;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<FieldValue> loadable) {
                                        TypeDescription typeDescription = (TypeDescription) loadable.getValue(ForField.FIELD_VALUE_DECLARING_TYPE).resolve(TypeDescription.class);
                                        if (typeDescription.represents(Void.TYPE)) {
                                            return new WithImplicitType(inDefinedShape.getDeclaringType().asGenericType(), loadable);
                                        }
                                        return new WithExplicitType(inDefinedShape.getDeclaringType().asGenericType(), loadable, typeDescription);
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<FieldValue> loadable) {
                                        TypeDescription typeDescription = (TypeDescription) loadable.getValue(ForField.FIELD_VALUE_DECLARING_TYPE).resolve(TypeDescription.class);
                                        if (typeDescription.represents(Void.TYPE)) {
                                            return new WithImplicitType(inDefinedShape.getType(), loadable);
                                        }
                                        return new WithExplicitType(inDefinedShape.getType(), loadable, typeDescription);
                                    }
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class Resolved extends ForField {
                                private final FieldDescription fieldDescription;

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                public boolean equals(@MaybeNull Object obj) {
                                    if (!super.equals(obj)) {
                                        return false;
                                    }
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.fieldDescription.equals(((Resolved) obj).fieldDescription);
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                public int hashCode() {
                                    return (super.hashCode() * 31) + this.fieldDescription.hashCode();
                                }

                                public Resolved(TypeDescription.Generic generic, Assigner.Typing typing, FieldDescription fieldDescription) {
                                    super(generic, typing);
                                    this.fieldDescription = fieldDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForField
                                protected FieldDescription resolve(TypeDescription typeDescription, MethodDescription methodDescription) {
                                    if (!this.fieldDescription.isStatic() && !this.fieldDescription.getDeclaringType().asErasure().isAssignableFrom(typeDescription)) {
                                        throw new IllegalStateException(this.fieldDescription + " is no member of " + typeDescription);
                                    }
                                    if (!this.fieldDescription.isVisibleTo(typeDescription)) {
                                        throw new IllegalStateException("Cannot access " + this.fieldDescription + " from " + typeDescription);
                                    }
                                    return this.fieldDescription;
                                }

                                @HashCodeAndEqualsPlugin.Enhance
                                public static class Factory<T extends Annotation> extends Factory.AbstractBase<T> {
                                    private final Class<T> annotationType;
                                    private final FieldDescription fieldDescription;
                                    private final Assigner.Typing typing;

                                    public boolean equals(@MaybeNull Object obj) {
                                        if (this == obj) {
                                            return true;
                                        }
                                        if (obj == null || getClass() != obj.getClass()) {
                                            return false;
                                        }
                                        Factory factory = (Factory) obj;
                                        return this.typing.equals(factory.typing) && this.annotationType.equals(factory.annotationType) && this.fieldDescription.equals(factory.fieldDescription);
                                    }

                                    public int hashCode() {
                                        return (((((getClass().hashCode() * 31) + this.annotationType.hashCode()) * 31) + this.fieldDescription.hashCode()) * 31) + this.typing.hashCode();
                                    }

                                    public Factory(Class<T> cls, FieldDescription fieldDescription) {
                                        this(cls, fieldDescription, Assigner.Typing.STATIC);
                                    }

                                    public Factory(Class<T> cls, FieldDescription fieldDescription, Assigner.Typing typing) {
                                        this.annotationType = cls;
                                        this.fieldDescription = fieldDescription;
                                        this.typing = typing;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public Class<T> getAnnotationType() {
                                        return this.annotationType;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory.AbstractBase
                                    protected OffsetMapping make(TypeDescription.Generic generic, AnnotationDescription.Loadable<T> loadable) {
                                        return new Resolved(generic, this.typing, this.fieldDescription);
                                    }
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static abstract class ForFieldHandle implements OffsetMapping {
                            private final Access access;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                return obj != null && getClass() == obj.getClass() && this.access.equals(((ForFieldHandle) obj).access);
                            }

                            public int hashCode() {
                                return (getClass().hashCode() * 31) + this.access.hashCode();
                            }

                            protected abstract FieldDescription resolve(TypeDescription typeDescription, MethodDescription methodDescription);

                            protected ForFieldHandle(Access access) {
                                this.access = access;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                FieldDescription fieldDescriptionResolve = resolve(typeDescription, methodDescription);
                                if (!fieldDescriptionResolve.isStatic() && methodDescription.isStatic()) {
                                    throw new IllegalStateException("Cannot access non-static field " + fieldDescriptionResolve + " from static method " + methodDescription);
                                }
                                if (fieldDescriptionResolve.isStatic()) {
                                    return new Resolved.ForStackManipulation(this.access.resolve(fieldDescriptionResolve.asDefined()).toStackManipulation());
                                }
                                return new Resolved.ForStackManipulation(new StackManipulation.Compound(this.access.resolve(fieldDescriptionResolve.asDefined()).toStackManipulation(), MethodVariableAccess.REFERENCE.loadFrom(0), MethodInvocation.invoke((MethodDescription.InDefinedShape) new MethodDescription.Latent(JavaType.METHOD_HANDLE.getTypeStub(), new MethodDescription.Token("bindTo", 1, JavaType.METHOD_HANDLE.getTypeStub().asGenericType(), new TypeList.Generic.Explicit(TypeDefinition.Sort.describe(Object.class)))))));
                            }

                            public enum Access {
                                GETTER { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Access.1
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Access
                                    protected JavaConstant.MethodHandle resolve(FieldDescription.InDefinedShape inDefinedShape) {
                                        return JavaConstant.MethodHandle.ofGetter(inDefinedShape);
                                    }
                                },
                                SETTER { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Access.2
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Access
                                    protected JavaConstant.MethodHandle resolve(FieldDescription.InDefinedShape inDefinedShape) {
                                        return JavaConstant.MethodHandle.ofSetter(inDefinedShape);
                                    }
                                };

                                protected abstract JavaConstant.MethodHandle resolve(FieldDescription.InDefinedShape inDefinedShape);

                                /* synthetic */ Access(AnonymousClass1 anonymousClass1) {
                                    this();
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static abstract class Unresolved extends ForFieldHandle {
                                protected static final String BEAN_PROPERTY = "";

                                /* JADX INFO: renamed from: name, reason: collision with root package name */
                                private final String f341name;

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                public boolean equals(@MaybeNull Object obj) {
                                    if (!super.equals(obj)) {
                                        return false;
                                    }
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.f341name.equals(((Unresolved) obj).f341name);
                                }

                                protected abstract FieldLocator fieldLocator(TypeDescription typeDescription);

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                public int hashCode() {
                                    return (super.hashCode() * 31) + this.f341name.hashCode();
                                }

                                public Unresolved(Access access, String str) {
                                    super(access);
                                    this.f341name = str;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                protected FieldDescription resolve(TypeDescription typeDescription, MethodDescription methodDescription) {
                                    FieldLocator.Resolution resolutionLocate;
                                    FieldLocator fieldLocator = fieldLocator(typeDescription);
                                    if (this.f341name.equals("")) {
                                        resolutionLocate = FieldLocator.Resolution.Simple.ofBeanAccessor(fieldLocator, methodDescription);
                                    } else {
                                        resolutionLocate = fieldLocator.locate(this.f341name);
                                    }
                                    if (!resolutionLocate.isResolved()) {
                                        throw new IllegalStateException("Cannot locate field named " + this.f341name + " for " + typeDescription);
                                    }
                                    return resolutionLocate.getField();
                                }

                                public static class WithImplicitType extends Unresolved {
                                    public WithImplicitType(Access access, String str) {
                                        super(access, str);
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Unresolved
                                    protected FieldLocator fieldLocator(TypeDescription typeDescription) {
                                        return new FieldLocator.ForClassHierarchy(typeDescription);
                                    }
                                }

                                @HashCodeAndEqualsPlugin.Enhance
                                public static class WithExplicitType extends Unresolved {
                                    private final TypeDescription declaringType;

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Unresolved, net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                    public boolean equals(@MaybeNull Object obj) {
                                        if (!super.equals(obj)) {
                                            return false;
                                        }
                                        if (this == obj) {
                                            return true;
                                        }
                                        return obj != null && getClass() == obj.getClass() && this.declaringType.equals(((WithExplicitType) obj).declaringType);
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Unresolved, net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                    public int hashCode() {
                                        return (super.hashCode() * 31) + this.declaringType.hashCode();
                                    }

                                    public WithExplicitType(Access access, String str, TypeDescription typeDescription) {
                                        super(access, str);
                                        this.declaringType = typeDescription;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle.Unresolved
                                    protected FieldLocator fieldLocator(TypeDescription typeDescription) {
                                        if (!this.declaringType.represents(TargetType.class) && !typeDescription.isAssignableTo(this.declaringType)) {
                                            throw new IllegalStateException(this.declaringType + " is no super type of " + typeDescription);
                                        }
                                        return new FieldLocator.ForExactType(TargetType.resolve(this.declaringType, typeDescription));
                                    }
                                }

                                protected enum GetterFactory implements Factory<FieldGetterHandle> {
                                    INSTANCE;

                                    private static final MethodDescription.InDefinedShape FIELD_GETTER_HANDLE_DECLARING_TYPE;
                                    private static final MethodDescription.InDefinedShape FIELD_GETTER_HANDLE_VALUE;

                                    static {
                                        MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(FieldGetterHandle.class).getDeclaredMethods();
                                        FIELD_GETTER_HANDLE_VALUE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("value")).getOnly();
                                        FIELD_GETTER_HANDLE_DECLARING_TYPE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("declaringType")).getOnly();
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public Class<FieldGetterHandle> getAnnotationType() {
                                        return FieldGetterHandle.class;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<FieldGetterHandle> loadable) {
                                        throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<FieldGetterHandle> loadable) {
                                        if (!inDefinedShape.getType().asErasure().isAssignableFrom(JavaType.METHOD_HANDLE.getTypeStub())) {
                                            throw new IllegalStateException("Cannot assign method handle to " + inDefinedShape);
                                        }
                                        TypeDescription typeDescription = (TypeDescription) loadable.getValue(FIELD_GETTER_HANDLE_DECLARING_TYPE).resolve(TypeDescription.class);
                                        if (typeDescription.represents(Void.TYPE)) {
                                            return new WithImplicitType(Access.GETTER, (String) loadable.getValue(FIELD_GETTER_HANDLE_VALUE).resolve(String.class));
                                        }
                                        return new WithExplicitType(Access.GETTER, (String) loadable.getValue(FIELD_GETTER_HANDLE_VALUE).resolve(String.class), typeDescription);
                                    }
                                }

                                protected enum SetterFactory implements Factory<FieldSetterHandle> {
                                    INSTANCE;

                                    private static final MethodDescription.InDefinedShape FIELD_SETTER_HANDLE_DECLARING_TYPE;
                                    private static final MethodDescription.InDefinedShape FIELD_SETTER_HANDLE_VALUE;

                                    static {
                                        MethodList<MethodDescription.InDefinedShape> declaredMethods = TypeDescription.ForLoadedType.of(FieldSetterHandle.class).getDeclaredMethods();
                                        FIELD_SETTER_HANDLE_VALUE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("value")).getOnly();
                                        FIELD_SETTER_HANDLE_DECLARING_TYPE = (MethodDescription.InDefinedShape) declaredMethods.filter(ElementMatchers.named("declaringType")).getOnly();
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public Class<FieldSetterHandle> getAnnotationType() {
                                        return FieldSetterHandle.class;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<FieldSetterHandle> loadable) {
                                        throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<FieldSetterHandle> loadable) {
                                        if (!inDefinedShape.getType().asErasure().isAssignableFrom(JavaType.METHOD_HANDLE.getTypeStub())) {
                                            throw new IllegalStateException("Cannot assign method handle to " + inDefinedShape);
                                        }
                                        TypeDescription typeDescription = (TypeDescription) loadable.getValue(FIELD_SETTER_HANDLE_DECLARING_TYPE).resolve(TypeDescription.class);
                                        if (typeDescription.represents(Void.TYPE)) {
                                            return new WithImplicitType(Access.SETTER, (String) loadable.getValue(FIELD_SETTER_HANDLE_VALUE).resolve(String.class));
                                        }
                                        return new WithExplicitType(Access.SETTER, (String) loadable.getValue(FIELD_SETTER_HANDLE_VALUE).resolve(String.class), typeDescription);
                                    }
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            public static class Resolved extends ForFieldHandle {
                                private final FieldDescription fieldDescription;

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                public boolean equals(@MaybeNull Object obj) {
                                    if (!super.equals(obj)) {
                                        return false;
                                    }
                                    if (this == obj) {
                                        return true;
                                    }
                                    return obj != null && getClass() == obj.getClass() && this.fieldDescription.equals(((Resolved) obj).fieldDescription);
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                public int hashCode() {
                                    return (super.hashCode() * 31) + this.fieldDescription.hashCode();
                                }

                                public Resolved(Access access, FieldDescription fieldDescription) {
                                    super(access);
                                    this.fieldDescription = fieldDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForFieldHandle
                                protected FieldDescription resolve(TypeDescription typeDescription, MethodDescription methodDescription) {
                                    if (!this.fieldDescription.isStatic() && !this.fieldDescription.getDeclaringType().asErasure().isAssignableFrom(typeDescription)) {
                                        throw new IllegalStateException(this.fieldDescription + " is no member of " + typeDescription);
                                    }
                                    if (!this.fieldDescription.isVisibleTo(typeDescription)) {
                                        throw new IllegalStateException("Cannot access " + this.fieldDescription + " from " + typeDescription);
                                    }
                                    return this.fieldDescription;
                                }

                                @HashCodeAndEqualsPlugin.Enhance
                                public static class Factory<T extends Annotation> implements Factory<T> {
                                    private final Access access;
                                    private final Class<T> annotationType;
                                    private final FieldDescription fieldDescription;

                                    public boolean equals(@MaybeNull Object obj) {
                                        if (this == obj) {
                                            return true;
                                        }
                                        if (obj == null || getClass() != obj.getClass()) {
                                            return false;
                                        }
                                        Factory factory = (Factory) obj;
                                        return this.access.equals(factory.access) && this.annotationType.equals(factory.annotationType) && this.fieldDescription.equals(factory.fieldDescription);
                                    }

                                    public int hashCode() {
                                        return (((((getClass().hashCode() * 31) + this.annotationType.hashCode()) * 31) + this.fieldDescription.hashCode()) * 31) + this.access.hashCode();
                                    }

                                    public Factory(Class<T> cls, FieldDescription fieldDescription, Access access) {
                                        this.annotationType = cls;
                                        this.fieldDescription = fieldDescription;
                                        this.access = access;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public Class<T> getAnnotationType() {
                                        return this.annotationType;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<T> loadable) {
                                        throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                    public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<T> loadable) {
                                        if (!inDefinedShape.getType().asErasure().isAssignableFrom(JavaType.METHOD_HANDLE.getTypeStub())) {
                                            throw new IllegalStateException("Cannot assign method handle to " + inDefinedShape);
                                        }
                                        return new Resolved(this.access, this.fieldDescription);
                                    }
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForOrigin implements OffsetMapping {
                            private final Sort sort;
                            private final Source source;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                if (obj == null || getClass() != obj.getClass()) {
                                    return false;
                                }
                                ForOrigin forOrigin = (ForOrigin) obj;
                                return this.sort.equals(forOrigin.sort) && this.source.equals(forOrigin.source);
                            }

                            public int hashCode() {
                                return (((getClass().hashCode() * 31) + this.sort.hashCode()) * 31) + this.source.hashCode();
                            }

                            protected ForOrigin(Sort sort, Source source) {
                                this.sort = sort;
                                this.source = source;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                return new Resolved(this.sort, this.source, methodDescription);
                            }

                            protected enum Sort {
                                METHOD { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.1
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return (member instanceof MethodDescription) && ((MethodDescription) member).isMethod();
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return MethodConstant.of(((MethodDescription) member).asDefined());
                                    }
                                },
                                CONSTRUCTOR { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.2
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return (member instanceof MethodDescription) && ((MethodDescription) member).isConstructor();
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return MethodConstant.of(((MethodDescription) member).asDefined());
                                    }
                                },
                                FIELD { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.3
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return member instanceof FieldDescription;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return new FieldConstant(((FieldDescription) member).asDefined());
                                    }
                                },
                                EXECUTABLE { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.4
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return member instanceof MethodDescription;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return MethodConstant.of(((MethodDescription) member).asDefined());
                                    }
                                },
                                TYPE { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.5
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return true;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return ClassConstant.of(member.getDeclaringType().asErasure());
                                    }
                                },
                                LOOKUP { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.6
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return true;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return MethodInvocation.lookup();
                                    }
                                },
                                METHOD_HANDLE { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.7
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return true;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        JavaConstant.MethodHandle methodHandleOfGetter;
                                        if (member instanceof MethodDescription) {
                                            methodHandleOfGetter = JavaConstant.MethodHandle.of(((MethodDescription) member).asDefined());
                                        } else if (member instanceof FieldDescription) {
                                            if (typeDescription.represents(Void.TYPE)) {
                                                methodHandleOfGetter = JavaConstant.MethodHandle.ofSetter(((FieldDescription) member).asDefined());
                                            } else {
                                                methodHandleOfGetter = JavaConstant.MethodHandle.ofGetter(((FieldDescription) member).asDefined());
                                            }
                                        } else {
                                            throw new IllegalStateException("Unexpected byte code element: " + member);
                                        }
                                        return methodHandleOfGetter.toStackManipulation();
                                    }
                                },
                                METHOD_TYPE { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.8
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return true;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return JavaConstant.MethodType.of(typeDescription, list).toStackManipulation();
                                    }
                                },
                                STRING { // from class: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort.9
                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected boolean isRepresentable(ByteCodeElement.Member member) {
                                        return true;
                                    }

                                    @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort
                                    protected StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription) {
                                        return new TextConstant(member.toString());
                                    }
                                };

                                protected abstract boolean isRepresentable(ByteCodeElement.Member member);

                                protected abstract StackManipulation resolve(ByteCodeElement.Member member, List<TypeDescription> list, TypeDescription typeDescription);

                                /* synthetic */ Sort(AnonymousClass1 anonymousClass1) {
                                    this();
                                }
                            }

                            protected enum Factory implements Factory<Origin> {
                                INSTANCE;

                                private static final MethodDescription.InDefinedShape ORIGIN_TYPE = (MethodDescription.InDefinedShape) TypeDescription.ForLoadedType.of(Origin.class).getDeclaredMethods().filter(ElementMatchers.named(FirebaseAnalytics.Param.SOURCE)).getOnly();

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<Origin> getAnnotationType() {
                                    return Origin.class;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<Origin> loadable) {
                                    throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<Origin> loadable) {
                                    Sort sort;
                                    if (inDefinedShape.getType().asErasure().represents(Class.class)) {
                                        sort = Sort.TYPE;
                                    } else if (inDefinedShape.getType().asErasure().represents(Method.class)) {
                                        sort = Sort.METHOD;
                                    } else if (inDefinedShape.getType().asErasure().represents(Constructor.class)) {
                                        sort = Sort.CONSTRUCTOR;
                                    } else if (inDefinedShape.getType().asErasure().represents(Field.class)) {
                                        sort = Sort.FIELD;
                                    } else if (JavaType.EXECUTABLE.getTypeStub().equals(inDefinedShape.getType().asErasure())) {
                                        sort = Sort.EXECUTABLE;
                                    } else if (JavaType.METHOD_HANDLE.getTypeStub().equals(inDefinedShape.getType().asErasure())) {
                                        sort = Sort.METHOD_HANDLE;
                                    } else if (JavaType.METHOD_TYPE.getTypeStub().equals(inDefinedShape.getType().asErasure())) {
                                        sort = Sort.METHOD_TYPE;
                                    } else if (JavaType.METHOD_HANDLES_LOOKUP.getTypeStub().equals(inDefinedShape.getType().asErasure())) {
                                        sort = Sort.LOOKUP;
                                    } else if (inDefinedShape.getType().asErasure().isAssignableFrom(String.class)) {
                                        sort = Sort.STRING;
                                    } else {
                                        throw new IllegalStateException("Non-supported type " + inDefinedShape.getType() + " for @Origin annotation");
                                    }
                                    return new ForOrigin(sort, (Source) ((EnumerationDescription) loadable.getValue(ORIGIN_TYPE).resolve(EnumerationDescription.class)).load(Source.class));
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final MethodDescription instrumentedMethod;
                                private final Sort sort;
                                private final Source source;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.sort.equals(resolved.sort) && this.source.equals(resolved.source) && this.instrumentedMethod.equals(resolved.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((((getClass().hashCode() * 31) + this.sort.hashCode()) * 31) + this.source.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Resolved(Sort sort, Source source, MethodDescription methodDescription) {
                                    this.sort = sort;
                                    this.source = source;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    if (!this.source.isRepresentable(this.sort, member, this.instrumentedMethod)) {
                                        throw new IllegalStateException("Cannot represent " + this.sort + " for " + this.source + " in " + this.instrumentedMethod);
                                    }
                                    return this.source.resolve(this.sort, member, generic, generic2, this.instrumentedMethod);
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForStubValue implements OffsetMapping {
                            private final Source source;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                return obj != null && getClass() == obj.getClass() && this.source.equals(((ForStubValue) obj).source);
                            }

                            public int hashCode() {
                                return (getClass().hashCode() * 31) + this.source.hashCode();
                            }

                            protected ForStubValue(Source source) {
                                this.source = source;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                return new Resolved(this.source, methodDescription);
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final MethodDescription instrumentedMethod;
                                private final Source source;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.source.equals(resolved.source) && this.instrumentedMethod.equals(resolved.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((getClass().hashCode() * 31) + this.source.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Resolved(Source source, MethodDescription methodDescription) {
                                    this.source = source;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    return DefaultValue.of(this.source.handle(methodHandle, this.instrumentedMethod).getReturnType());
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected enum Factory implements Factory<StubValue> {
                                INSTANCE;

                                private static final MethodDescription.InDefinedShape STUB_VALUE_SOURCE = (MethodDescription.InDefinedShape) TypeDescription.ForLoadedType.of(StubValue.class).getDeclaredMethods().filter(ElementMatchers.named(FirebaseAnalytics.Param.SOURCE)).getOnly();

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<StubValue> getAnnotationType() {
                                    return StubValue.class;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<StubValue> loadable) {
                                    throw new UnsupportedOperationException("This factory does not support binding a method receiver");
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<StubValue> loadable) {
                                    if (!inDefinedShape.getType().represents(Object.class)) {
                                        throw new IllegalStateException("Expected " + inDefinedShape + " to declare an Object type");
                                    }
                                    return new ForStubValue((Source) ((EnumerationDescription) loadable.getValue(STUB_VALUE_SOURCE).resolve(EnumerationDescription.class)).load(Source.class));
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForCurrent implements OffsetMapping {
                            private final TypeDescription.Generic targetType;

                            @MaybeNull
                            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                            private final Assigner.Typing typing;

                            /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
                            
                                if (r2 != null) goto L18;
                             */
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct add '--show-bad-code' argument
                            */
                            public boolean equals(@net.bytebuddy.utility.nullability.MaybeNull java.lang.Object r5) {
                                /*
                                    r4 = this;
                                    r0 = 1
                                    if (r4 != r5) goto L4
                                    return r0
                                L4:
                                    r1 = 0
                                    if (r5 != 0) goto L8
                                    return r1
                                L8:
                                    java.lang.Class r2 = r4.getClass()
                                    java.lang.Class r3 = r5.getClass()
                                    if (r2 == r3) goto L13
                                    return r1
                                L13:
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r2 = r4.typing
                                    net.bytebuddy.asm.MemberSubstitution$Substitution$Chain$Step$ForDelegation$OffsetMapping$ForCurrent r5 = (net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForCurrent) r5
                                    net.bytebuddy.implementation.bytecode.assign.Assigner$Typing r3 = r5.typing
                                    if (r3 == 0) goto L24
                                    if (r2 == 0) goto L26
                                    boolean r2 = r2.equals(r3)
                                    if (r2 != 0) goto L27
                                    return r1
                                L24:
                                    if (r2 == 0) goto L27
                                L26:
                                    return r1
                                L27:
                                    net.bytebuddy.description.type.TypeDescription$Generic r2 = r4.targetType
                                    net.bytebuddy.description.type.TypeDescription$Generic r5 = r5.targetType
                                    boolean r5 = r2.equals(r5)
                                    if (r5 != 0) goto L32
                                    return r1
                                L32:
                                    return r0
                                */
                                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.ForCurrent.equals(java.lang.Object):boolean");
                            }

                            public int hashCode() {
                                int iHashCode = ((getClass().hashCode() * 31) + this.targetType.hashCode()) * 31;
                                Assigner.Typing typing = this.typing;
                                return typing != null ? iHashCode + typing.hashCode() : iHashCode;
                            }

                            public ForCurrent(TypeDescription.Generic generic, @MaybeNull Assigner.Typing typing) {
                                this.targetType = generic;
                                this.typing = typing;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping
                            public Resolved resolve(Assigner assigner, Assigner.Typing typing, TypeDescription typeDescription, MethodDescription methodDescription) {
                                TypeDescription.Generic generic = this.targetType;
                                Assigner.Typing typing2 = this.typing;
                                if (typing2 != null) {
                                    typing = typing2;
                                }
                                return new Resolved(generic, assigner, typing);
                            }

                            protected enum Factory implements Factory<Current> {
                                INSTANCE;

                                private static final MethodDescription.InDefinedShape CURRENT_TYPING = (MethodDescription.InDefinedShape) TypeDescription.ForLoadedType.of(Current.class).getDeclaredMethods().filter(ElementMatchers.named("typing")).getOnly();

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public Class<Current> getAnnotationType() {
                                    return Current.class;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(MethodDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<Current> loadable) {
                                    return new ForCurrent(inDefinedShape.getDeclaringType().asGenericType(), (Assigner.Typing) ((EnumerationDescription) loadable.getValue(CURRENT_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class));
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Factory
                                public OffsetMapping make(ParameterDescription.InDefinedShape inDefinedShape, AnnotationDescription.Loadable<Current> loadable) {
                                    return new ForCurrent(inDefinedShape.getType(), (Assigner.Typing) ((EnumerationDescription) loadable.getValue(CURRENT_TYPING).resolve(EnumerationDescription.class)).load(Assigner.Typing.class));
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final Assigner assigner;
                                private final TypeDescription.Generic targetType;
                                private final Assigner.Typing typing;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.typing.equals(resolved.typing) && this.targetType.equals(resolved.targetType) && this.assigner.equals(resolved.assigner);
                                }

                                public int hashCode() {
                                    return (((((getClass().hashCode() * 31) + this.targetType.hashCode()) * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode();
                                }

                                public Resolved(TypeDescription.Generic generic, Assigner assigner, Assigner.Typing typing) {
                                    this.targetType = generic;
                                    this.assigner = assigner;
                                    this.typing = typing;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.OffsetMapping.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, TypeDescription.Generic generic3, JavaConstant.MethodHandle methodHandle, Map<Integer, Integer> map, int i) {
                                    StackManipulation stackManipulationAssign = this.assigner.assign(generic3, this.targetType, this.typing);
                                    if (!stackManipulationAssign.isValid()) {
                                        throw new IllegalStateException("Cannot assign " + generic3 + " to " + this.targetType);
                                    }
                                    return new StackManipulation.Compound(MethodVariableAccess.of(generic3).loadFrom(i), stackManipulationAssign);
                                }
                            }
                        }
                    }

                    protected interface Dispatcher {

                        public interface Factory {
                            Dispatcher make(MethodDescription.InDefinedShape inDefinedShape);
                        }

                        public interface Resolved {
                            StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, JavaConstant.MethodHandle methodHandle);

                            StackManipulation initialize();
                        }

                        Resolved resolve(TypeDescription typeDescription, MethodDescription methodDescription);

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForRegularInvocation implements Dispatcher, Resolved {
                            private final MethodDescription delegate;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                return obj != null && getClass() == obj.getClass() && this.delegate.equals(((ForRegularInvocation) obj).delegate);
                            }

                            public int hashCode() {
                                return (getClass().hashCode() * 31) + this.delegate.hashCode();
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher
                            public Resolved resolve(TypeDescription typeDescription, MethodDescription methodDescription) {
                                return this;
                            }

                            protected ForRegularInvocation(MethodDescription methodDescription) {
                                this.delegate = methodDescription;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher.Resolved
                            public StackManipulation initialize() {
                                return this.delegate.isConstructor() ? new StackManipulation.Compound(TypeCreation.of(this.delegate.getDeclaringType().asErasure()), Duplication.SINGLE) : StackManipulation.Trivial.INSTANCE;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher.Resolved
                            public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, JavaConstant.MethodHandle methodHandle) {
                                return MethodInvocation.invoke(this.delegate);
                            }

                            protected enum Factory implements Factory {
                                INSTANCE;

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher.Factory
                                public Dispatcher make(MethodDescription.InDefinedShape inDefinedShape) {
                                    return new ForRegularInvocation(inDefinedShape);
                                }
                            }
                        }

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForDynamicInvocation implements Dispatcher {
                            private final MethodDescription.InDefinedShape bootstrapMethod;
                            private final MethodDescription.InDefinedShape delegate;
                            private final BootstrapArgumentResolver resolver;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                if (obj == null || getClass() != obj.getClass()) {
                                    return false;
                                }
                                ForDynamicInvocation forDynamicInvocation = (ForDynamicInvocation) obj;
                                return this.bootstrapMethod.equals(forDynamicInvocation.bootstrapMethod) && this.delegate.equals(forDynamicInvocation.delegate) && this.resolver.equals(forDynamicInvocation.resolver);
                            }

                            public int hashCode() {
                                return (((((getClass().hashCode() * 31) + this.bootstrapMethod.hashCode()) * 31) + this.delegate.hashCode()) * 31) + this.resolver.hashCode();
                            }

                            protected ForDynamicInvocation(MethodDescription.InDefinedShape inDefinedShape, MethodDescription.InDefinedShape inDefinedShape2, BootstrapArgumentResolver bootstrapArgumentResolver) {
                                this.bootstrapMethod = inDefinedShape;
                                this.delegate = inDefinedShape2;
                                this.resolver = bootstrapArgumentResolver;
                            }

                            protected static Factory of(MethodDescription.InDefinedShape inDefinedShape, BootstrapArgumentResolver.Factory factory) {
                                if (!inDefinedShape.isInvokeBootstrap()) {
                                    throw new IllegalStateException("Not a bootstrap method: " + inDefinedShape);
                                }
                                return new Factory(inDefinedShape, factory);
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher
                            public Resolved resolve(TypeDescription typeDescription, MethodDescription methodDescription) {
                                return new Resolved(this.bootstrapMethod, this.delegate, this.resolver.resolve(typeDescription, methodDescription));
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final MethodDescription.InDefinedShape bootstrapMethod;
                                private final MethodDescription.InDefinedShape delegate;
                                private final BootstrapArgumentResolver.Resolved resolver;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.bootstrapMethod.equals(resolved.bootstrapMethod) && this.delegate.equals(resolved.delegate) && this.resolver.equals(resolved.resolver);
                                }

                                public int hashCode() {
                                    return (((((getClass().hashCode() * 31) + this.bootstrapMethod.hashCode()) * 31) + this.delegate.hashCode()) * 31) + this.resolver.hashCode();
                                }

                                protected Resolved(MethodDescription.InDefinedShape inDefinedShape, MethodDescription.InDefinedShape inDefinedShape2, BootstrapArgumentResolver.Resolved resolved) {
                                    this.bootstrapMethod = inDefinedShape;
                                    this.delegate = inDefinedShape2;
                                    this.resolver = resolved;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher.Resolved
                                public StackManipulation initialize() {
                                    return StackManipulation.Trivial.INSTANCE;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher.Resolved
                                public StackManipulation apply(TypeDescription typeDescription, ByteCodeElement.Member member, JavaConstant.MethodHandle methodHandle) {
                                    List<JavaConstant> listMake = this.resolver.make(typeDescription, member, methodHandle);
                                    if (!this.bootstrapMethod.isInvokeBootstrap(TypeList.Explicit.of((List<? extends JavaConstant>) listMake))) {
                                        throw new IllegalArgumentException(this.bootstrapMethod + " is not accepting advice bootstrap arguments: " + listMake);
                                    }
                                    return MethodInvocation.invoke(this.bootstrapMethod).dynamic(this.delegate.getInternalName(), this.delegate.getReturnType().asErasure(), this.delegate.getParameters().asTypeList().asErasures(), listMake);
                                }
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Factory implements Factory {
                                private final MethodDescription.InDefinedShape bootstrapMethod;
                                private final BootstrapArgumentResolver.Factory resolverFactory;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Factory factory = (Factory) obj;
                                    return this.bootstrapMethod.equals(factory.bootstrapMethod) && this.resolverFactory.equals(factory.resolverFactory);
                                }

                                public int hashCode() {
                                    return (((getClass().hashCode() * 31) + this.bootstrapMethod.hashCode()) * 31) + this.resolverFactory.hashCode();
                                }

                                protected Factory(MethodDescription.InDefinedShape inDefinedShape, BootstrapArgumentResolver.Factory factory) {
                                    this.bootstrapMethod = inDefinedShape;
                                    this.resolverFactory = factory;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.Dispatcher.Factory
                                public Dispatcher make(MethodDescription.InDefinedShape inDefinedShape) {
                                    return new ForDynamicInvocation(this.bootstrapMethod, inDefinedShape, this.resolverFactory.make(inDefinedShape));
                                }
                            }
                        }
                    }

                    public interface BootstrapArgumentResolver {

                        public interface Factory {
                            BootstrapArgumentResolver make(MethodDescription.InDefinedShape inDefinedShape);
                        }

                        public interface Resolved {
                            List<JavaConstant> make(TypeDescription typeDescription, ByteCodeElement.Member member, JavaConstant.MethodHandle methodHandle);
                        }

                        Resolved resolve(TypeDescription typeDescription, MethodDescription methodDescription);

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class ForDefaultValues implements BootstrapArgumentResolver {
                            private final MethodDescription.InDefinedShape delegate;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                return obj != null && getClass() == obj.getClass() && this.delegate.equals(((ForDefaultValues) obj).delegate);
                            }

                            public int hashCode() {
                                return (getClass().hashCode() * 31) + this.delegate.hashCode();
                            }

                            protected ForDefaultValues(MethodDescription.InDefinedShape inDefinedShape) {
                                this.delegate = inDefinedShape;
                            }

                            @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.BootstrapArgumentResolver
                            public Resolved resolve(TypeDescription typeDescription, MethodDescription methodDescription) {
                                return new Resolved(this.delegate, typeDescription, methodDescription);
                            }

                            @HashCodeAndEqualsPlugin.Enhance
                            protected static class Resolved implements Resolved {
                                private final MethodDescription.InDefinedShape delegate;
                                private final MethodDescription instrumentedMethod;
                                private final TypeDescription instrumentedType;

                                public boolean equals(@MaybeNull Object obj) {
                                    if (this == obj) {
                                        return true;
                                    }
                                    if (obj == null || getClass() != obj.getClass()) {
                                        return false;
                                    }
                                    Resolved resolved = (Resolved) obj;
                                    return this.delegate.equals(resolved.delegate) && this.instrumentedType.equals(resolved.instrumentedType) && this.instrumentedMethod.equals(resolved.instrumentedMethod);
                                }

                                public int hashCode() {
                                    return (((((getClass().hashCode() * 31) + this.delegate.hashCode()) * 31) + this.instrumentedType.hashCode()) * 31) + this.instrumentedMethod.hashCode();
                                }

                                protected Resolved(MethodDescription.InDefinedShape inDefinedShape, TypeDescription typeDescription, MethodDescription methodDescription) {
                                    this.delegate = inDefinedShape;
                                    this.instrumentedType = typeDescription;
                                    this.instrumentedMethod = methodDescription;
                                }

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.BootstrapArgumentResolver.Resolved
                                public List<JavaConstant> make(TypeDescription typeDescription, ByteCodeElement.Member member, JavaConstant.MethodHandle methodHandle) {
                                    if (this.instrumentedMethod.isTypeInitializer()) {
                                        return Arrays.asList(JavaConstant.Simple.ofLoaded(this.delegate.getDeclaringType().getName()), JavaConstant.Simple.of(typeDescription), JavaConstant.Simple.ofLoaded(member.getInternalName()), methodHandle, JavaConstant.Simple.of(this.instrumentedType), JavaConstant.Simple.ofLoaded(this.instrumentedMethod.getInternalName()));
                                    }
                                    return Arrays.asList(JavaConstant.Simple.ofLoaded(this.delegate.getDeclaringType().getName()), JavaConstant.Simple.of(typeDescription), JavaConstant.Simple.ofLoaded(member.getInternalName()), methodHandle, JavaConstant.Simple.of(this.instrumentedType), JavaConstant.Simple.ofLoaded(this.instrumentedMethod.getInternalName()), JavaConstant.MethodHandle.of(this.instrumentedMethod.asDefined()));
                                }
                            }

                            public enum Factory implements Factory {
                                INSTANCE;

                                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.Step.ForDelegation.BootstrapArgumentResolver.Factory
                                public BootstrapArgumentResolver make(MethodDescription.InDefinedShape inDefinedShape) {
                                    return new ForDefaultValues(inDefinedShape);
                                }
                            }
                        }
                    }

                    public static class WithCustomMapping {
                        private final Dispatcher.Factory dispatcherFactory;
                        private final Map<Class<? extends Annotation>, OffsetMapping.Factory<?>> offsetMappings;

                        protected WithCustomMapping(Dispatcher.Factory factory, Map<Class<? extends Annotation>, OffsetMapping.Factory<?>> map) {
                            this.dispatcherFactory = factory;
                            this.offsetMappings = map;
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, @MaybeNull Object obj) {
                            return bind(OffsetMapping.ForStackManipulation.of(cls, obj));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, Field field) {
                            return bind((Class) cls, (FieldDescription) new FieldDescription.ForLoadedField(field));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, FieldDescription fieldDescription) {
                            return bind(new OffsetMapping.ForField.Resolved.Factory(cls, fieldDescription));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, Class<?> cls2) {
                            return bind((Class) cls, TypeDescription.ForLoadedType.of(cls2));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, TypeDescription typeDescription) {
                            return bind(new OffsetMapping.ForStackManipulation.Factory(cls, ConstantValue.Simple.wrap(typeDescription)));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, Enum<?> r3) {
                            return bind((Class) cls, (EnumerationDescription) new EnumerationDescription.ForLoadedEnumeration(r3));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, EnumerationDescription enumerationDescription) {
                            return bind(new OffsetMapping.ForStackManipulation.Factory(cls, ConstantValue.Simple.wrap(enumerationDescription)));
                        }

                        public <T extends Annotation> WithCustomMapping bindSerialized(Class<T> cls, Serializable serializable) {
                            return bindSerialized(cls, serializable, serializable.getClass());
                        }

                        public <T extends Annotation, S extends Serializable> WithCustomMapping bindSerialized(Class<T> cls, S s, Class<? super S> cls2) {
                            return bind(OffsetMapping.ForStackManipulation.OfSerializedConstant.of(cls, s, cls2));
                        }

                        public <T extends Annotation> WithCustomMapping bindProperty(Class<T> cls, String str) {
                            return bind(OffsetMapping.ForStackManipulation.OfAnnotationProperty.of(cls, str));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, ConstantValue constantValue) {
                            return bind(new OffsetMapping.ForStackManipulation.Factory(cls, constantValue.toStackManipulation(), constantValue.getTypeDescription().asGenericType()));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, StackManipulation stackManipulation, Type type) {
                            return bind(cls, stackManipulation, TypeDefinition.Sort.describe(type));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, StackManipulation stackManipulation, TypeDescription.Generic generic) {
                            return bind(new OffsetMapping.ForStackManipulation.Factory(cls, stackManipulation, generic));
                        }

                        public <T extends Annotation> WithCustomMapping bindLambda(Class<T> cls, Constructor<?> constructor, Class<?> cls2) {
                            return bindLambda(cls, new MethodDescription.ForLoadedConstructor(constructor), TypeDescription.ForLoadedType.of(cls2));
                        }

                        public <T extends Annotation> WithCustomMapping bindLambda(Class<T> cls, Constructor<?> constructor, Class<?> cls2, MethodGraph.Compiler compiler) {
                            return bindLambda(cls, new MethodDescription.ForLoadedConstructor(constructor), TypeDescription.ForLoadedType.of(cls2), compiler);
                        }

                        public <T extends Annotation> WithCustomMapping bindLambda(Class<T> cls, Method method, Class<?> cls2) {
                            return bindLambda(cls, new MethodDescription.ForLoadedMethod(method), TypeDescription.ForLoadedType.of(cls2));
                        }

                        public <T extends Annotation> WithCustomMapping bindLambda(Class<T> cls, Method method, Class<?> cls2, MethodGraph.Compiler compiler) {
                            return bindLambda(cls, new MethodDescription.ForLoadedMethod(method), TypeDescription.ForLoadedType.of(cls2), compiler);
                        }

                        public <T extends Annotation> WithCustomMapping bindLambda(Class<T> cls, MethodDescription.InDefinedShape inDefinedShape, TypeDescription typeDescription) {
                            return bindLambda(cls, inDefinedShape, typeDescription, MethodGraph.Compiler.DEFAULT);
                        }

                        public <T extends Annotation> WithCustomMapping bindLambda(Class<T> cls, MethodDescription.InDefinedShape inDefinedShape, TypeDescription typeDescription, MethodGraph.Compiler compiler) {
                            if (!typeDescription.isInterface()) {
                                throw new IllegalArgumentException(typeDescription + " is not an interface type");
                            }
                            MethodList methodListFilter = compiler.compile((TypeDefinition) typeDescription).listNodes().asMethodList().filter(ElementMatchers.isAbstract());
                            if (methodListFilter.size() != 1) {
                                throw new IllegalArgumentException(typeDescription + " does not define exactly one abstract method: " + methodListFilter);
                            }
                            return bindDynamic(cls, new MethodDescription.Latent(new TypeDescription.Latent("java.lang.invoke.LambdaMetafactory", 1, TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Object.class), new TypeDescription.Generic[0]), "metafactory", 9, Collections.emptyList(), JavaType.CALL_SITE.getTypeStub().asGenericType(), Arrays.asList(new ParameterDescription.Token(JavaType.METHOD_HANDLES_LOOKUP.getTypeStub().asGenericType()), new ParameterDescription.Token(TypeDescription.ForLoadedType.of(String.class).asGenericType()), new ParameterDescription.Token(JavaType.METHOD_TYPE.getTypeStub().asGenericType()), new ParameterDescription.Token(JavaType.METHOD_TYPE.getTypeStub().asGenericType()), new ParameterDescription.Token(JavaType.METHOD_HANDLE.getTypeStub().asGenericType()), new ParameterDescription.Token(JavaType.METHOD_TYPE.getTypeStub().asGenericType())), Collections.emptyList(), Collections.emptyList(), AnnotationValue.UNDEFINED, TypeDescription.Generic.UNDEFINED), JavaConstant.MethodType.ofSignature(methodListFilter.asDefined().getOnly()), JavaConstant.MethodHandle.of(inDefinedShape), JavaConstant.MethodType.ofSignature(methodListFilter.asDefined().getOnly()));
                        }

                        public <T extends Annotation> WithCustomMapping bindDynamic(Class<T> cls, Method method, Object... objArr) {
                            return bindDynamic(cls, method, Arrays.asList(objArr));
                        }

                        public <T extends Annotation> WithCustomMapping bindDynamic(Class<T> cls, Method method, List<?> list) {
                            return bindDynamic(cls, new MethodDescription.ForLoadedMethod(method), list);
                        }

                        public <T extends Annotation> WithCustomMapping bindDynamic(Class<T> cls, Constructor<?> constructor, Object... objArr) {
                            return bindDynamic(cls, constructor, Arrays.asList(objArr));
                        }

                        public <T extends Annotation> WithCustomMapping bindDynamic(Class<T> cls, Constructor<?> constructor, List<?> list) {
                            return bindDynamic(cls, new MethodDescription.ForLoadedConstructor(constructor), list);
                        }

                        public <T extends Annotation> WithCustomMapping bindDynamic(Class<T> cls, MethodDescription.InDefinedShape inDefinedShape, Object... objArr) {
                            return bindDynamic(cls, inDefinedShape, Arrays.asList(objArr));
                        }

                        public <T extends Annotation> WithCustomMapping bindDynamic(Class<T> cls, MethodDescription.InDefinedShape inDefinedShape, List<?> list) {
                            List<JavaConstant> listWrap = JavaConstant.Simple.wrap(list);
                            if (!inDefinedShape.isInvokeBootstrap(TypeList.Explicit.of((List<? extends JavaConstant>) listWrap))) {
                                throw new IllegalArgumentException("Not a valid bootstrap method " + inDefinedShape + " for " + listWrap);
                            }
                            return bind(new OffsetMapping.ForStackManipulation.OfDynamicInvocation(cls, inDefinedShape, listWrap));
                        }

                        public <T extends Annotation> WithCustomMapping bind(Class<T> cls, OffsetMapping offsetMapping) {
                            return bind(new OffsetMapping.Factory.Simple(cls, offsetMapping));
                        }

                        public WithCustomMapping bind(OffsetMapping.Factory<?> factory) {
                            LinkedHashMap linkedHashMap = new LinkedHashMap(this.offsetMappings);
                            if (!factory.getAnnotationType().isAnnotation()) {
                                throw new IllegalArgumentException("Not an annotation type: " + factory.getAnnotationType());
                            }
                            if (linkedHashMap.put(factory.getAnnotationType(), factory) != null) {
                                throw new IllegalArgumentException("Annotation type already mapped: " + factory.getAnnotationType());
                            }
                            return new WithCustomMapping(this.dispatcherFactory, linkedHashMap);
                        }

                        public WithCustomMapping bootstrap(Constructor<?> constructor) {
                            return bootstrap(new MethodDescription.ForLoadedConstructor(constructor));
                        }

                        public WithCustomMapping bootstrap(Constructor<?> constructor, BootstrapArgumentResolver.Factory factory) {
                            return bootstrap(new MethodDescription.ForLoadedConstructor(constructor), factory);
                        }

                        public WithCustomMapping bootstrap(Method method) {
                            return bootstrap(new MethodDescription.ForLoadedMethod(method));
                        }

                        public WithCustomMapping bootstrap(Method method, BootstrapArgumentResolver.Factory factory) {
                            return bootstrap(new MethodDescription.ForLoadedMethod(method), factory);
                        }

                        public WithCustomMapping bootstrap(MethodDescription.InDefinedShape inDefinedShape) {
                            return bootstrap(inDefinedShape, BootstrapArgumentResolver.ForDefaultValues.Factory.INSTANCE);
                        }

                        public WithCustomMapping bootstrap(MethodDescription.InDefinedShape inDefinedShape, BootstrapArgumentResolver.Factory factory) {
                            return new WithCustomMapping(Dispatcher.ForDynamicInvocation.of(inDefinedShape, factory), this.offsetMappings);
                        }

                        public Factory to(Method method) {
                            return to(new MethodDescription.ForLoadedMethod(method));
                        }

                        public Factory to(Constructor<?> constructor) {
                            return to(new MethodDescription.ForLoadedConstructor(constructor));
                        }

                        public Factory to(MethodDescription.InDefinedShape inDefinedShape) {
                            return ForDelegation.to(inDefinedShape, this.dispatcherFactory, new ArrayList(this.offsetMappings.values()));
                        }
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Factory implements Factory {
                private final Assigner assigner;
                private final List<Step.Factory> steps;
                private final Assigner.Typing typing;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Factory factory = (Factory) obj;
                    return this.typing.equals(factory.typing) && this.assigner.equals(factory.assigner) && this.steps.equals(factory.steps);
                }

                public int hashCode() {
                    return (((((getClass().hashCode() * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode()) * 31) + this.steps.hashCode();
                }

                protected Factory(Assigner assigner, Assigner.Typing typing, List<Step.Factory> list) {
                    this.assigner = assigner;
                    this.typing = typing;
                    this.steps = list;
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Substitution.Factory
                public Substitution make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    if (this.steps.isEmpty()) {
                        return Stubbing.INSTANCE;
                    }
                    ArrayList arrayList = new ArrayList(this.steps.size());
                    Iterator<Step.Factory> it = this.steps.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next().make(this.assigner, this.typing, typeDescription, methodDescription));
                    }
                    return new Chain(this.assigner, this.typing, arrayList);
                }

                public Factory executing(Step.Factory... factoryArr) {
                    return executing(Arrays.asList(factoryArr));
                }

                public Factory executing(List<? extends Step.Factory> list) {
                    return new Factory(this.assigner, this.typing, CompoundList.of((List) this.steps, (List) list));
                }
            }
        }
    }

    protected interface Replacement {
        Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, FieldDescription fieldDescription, boolean z);

        Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, MethodDescription methodDescription2, InvocationType invocationType);

        public interface Binding {
            boolean isBound();

            StackManipulation make(TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i);

            public enum Unresolved implements Binding {
                INSTANCE;

                @Override // net.bytebuddy.asm.MemberSubstitution.Replacement.Binding
                public boolean isBound() {
                    return false;
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Replacement.Binding
                public StackManipulation make(TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i) {
                    throw new IllegalStateException("Cannot resolve unresolved binding");
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Resolved implements Binding {
                private final ByteCodeElement.Member original;
                private final TypeDescription receiver;
                private final Substitution substitution;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Resolved resolved = (Resolved) obj;
                    return this.receiver.equals(resolved.receiver) && this.original.equals(resolved.original) && this.substitution.equals(resolved.substitution);
                }

                public int hashCode() {
                    return (((((getClass().hashCode() * 31) + this.receiver.hashCode()) * 31) + this.original.hashCode()) * 31) + this.substitution.hashCode();
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Replacement.Binding
                public boolean isBound() {
                    return true;
                }

                protected Resolved(TypeDescription typeDescription, ByteCodeElement.Member member, Substitution substitution) {
                    this.receiver = typeDescription;
                    this.original = member;
                    this.substitution = substitution;
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Replacement.Binding
                public StackManipulation make(TypeList.Generic generic, TypeDescription.Generic generic2, JavaConstant.MethodHandle methodHandle, StackManipulation stackManipulation, int i) {
                    return this.substitution.resolve(this.receiver, this.original, generic, generic2, methodHandle, stackManipulation, i);
                }
            }
        }

        public interface Factory {
            Replacement make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool);

            @HashCodeAndEqualsPlugin.Enhance
            public static class Compound implements Factory {
                private final List<Factory> factories;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.factories.equals(((Compound) obj).factories);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.factories.hashCode();
                }

                protected Compound(Factory... factoryArr) {
                    this((List<? extends Factory>) Arrays.asList(factoryArr));
                }

                protected Compound(List<? extends Factory> list) {
                    this.factories = new ArrayList();
                    for (Factory factory : list) {
                        if (factory instanceof Compound) {
                            this.factories.addAll(((Compound) factory).factories);
                        } else if (!(factory instanceof NoOp)) {
                            this.factories.add(factory);
                        }
                    }
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Replacement.Factory
                public Replacement make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    ArrayList arrayList = new ArrayList();
                    Iterator<Factory> it = this.factories.iterator();
                    while (it.hasNext()) {
                        arrayList.add(it.next().make(typeDescription, methodDescription, typePool));
                    }
                    return new ForFirstBinding(arrayList);
                }
            }
        }

        public enum InvocationType {
            VIRTUAL,
            SUPER,
            OTHER;

            protected static InvocationType of(int i, MethodDescription methodDescription) {
                if (i != 182) {
                    if (i == 183) {
                        return methodDescription.isVirtual() ? SUPER : OTHER;
                    }
                    if (i != 185) {
                        return OTHER;
                    }
                }
                return VIRTUAL;
            }

            protected boolean matches(boolean z, boolean z2) {
                int i = AnonymousClass1.$SwitchMap$net$bytebuddy$asm$MemberSubstitution$Replacement$InvocationType[ordinal()];
                if (i == 1) {
                    return z;
                }
                if (i != 2) {
                    return true;
                }
                return z2;
            }
        }

        public enum NoOp implements Replacement, Factory {
            INSTANCE;

            @Override // net.bytebuddy.asm.MemberSubstitution.Replacement.Factory
            public Replacement make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                return this;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Replacement
            public Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, FieldDescription fieldDescription, boolean z) {
                return Binding.Unresolved.INSTANCE;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Replacement
            public Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, MethodDescription methodDescription2, InvocationType invocationType) {
                return Binding.Unresolved.INSTANCE;
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForElementMatchers implements Replacement {
            private final ElementMatcher<? super FieldDescription> fieldMatcher;
            private final boolean includeSuperCalls;
            private final boolean includeVirtualCalls;
            private final boolean matchFieldRead;
            private final boolean matchFieldWrite;
            private final ElementMatcher<? super MethodDescription> methodMatcher;
            private final Substitution substitution;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForElementMatchers forElementMatchers = (ForElementMatchers) obj;
                return this.matchFieldRead == forElementMatchers.matchFieldRead && this.matchFieldWrite == forElementMatchers.matchFieldWrite && this.includeVirtualCalls == forElementMatchers.includeVirtualCalls && this.includeSuperCalls == forElementMatchers.includeSuperCalls && this.fieldMatcher.equals(forElementMatchers.fieldMatcher) && this.methodMatcher.equals(forElementMatchers.methodMatcher) && this.substitution.equals(forElementMatchers.substitution);
            }

            public int hashCode() {
                return (((((((((((((getClass().hashCode() * 31) + this.fieldMatcher.hashCode()) * 31) + this.methodMatcher.hashCode()) * 31) + (this.matchFieldRead ? 1 : 0)) * 31) + (this.matchFieldWrite ? 1 : 0)) * 31) + (this.includeVirtualCalls ? 1 : 0)) * 31) + (this.includeSuperCalls ? 1 : 0)) * 31) + this.substitution.hashCode();
            }

            protected ForElementMatchers(ElementMatcher<? super FieldDescription> elementMatcher, ElementMatcher<? super MethodDescription> elementMatcher2, boolean z, boolean z2, boolean z3, boolean z4, Substitution substitution) {
                this.fieldMatcher = elementMatcher;
                this.methodMatcher = elementMatcher2;
                this.matchFieldRead = z;
                this.matchFieldWrite = z2;
                this.includeVirtualCalls = z3;
                this.includeSuperCalls = z4;
                this.substitution = substitution;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Replacement
            public Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, FieldDescription fieldDescription, boolean z) {
                if (!z ? this.matchFieldRead : this.matchFieldWrite) {
                    if (this.fieldMatcher.matches(fieldDescription)) {
                        return new Binding.Resolved(typeDescription2, fieldDescription, this.substitution);
                    }
                }
                return Binding.Unresolved.INSTANCE;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Replacement
            public Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, MethodDescription methodDescription2, InvocationType invocationType) {
                return (invocationType.matches(this.includeVirtualCalls, this.includeSuperCalls) && this.methodMatcher.matches(methodDescription2)) ? new Binding.Resolved(typeDescription2, methodDescription2, this.substitution) : Binding.Unresolved.INSTANCE;
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class Factory implements Factory {
                private final ElementMatcher<? super FieldDescription> fieldMatcher;
                private final boolean includeSuperCalls;
                private final boolean includeVirtualCalls;
                private final boolean matchFieldRead;
                private final boolean matchFieldWrite;
                private final ElementMatcher<? super MethodDescription> methodMatcher;
                private final Substitution.Factory substitutionFactory;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Factory factory = (Factory) obj;
                    return this.matchFieldRead == factory.matchFieldRead && this.matchFieldWrite == factory.matchFieldWrite && this.includeVirtualCalls == factory.includeVirtualCalls && this.includeSuperCalls == factory.includeSuperCalls && this.fieldMatcher.equals(factory.fieldMatcher) && this.methodMatcher.equals(factory.methodMatcher) && this.substitutionFactory.equals(factory.substitutionFactory);
                }

                public int hashCode() {
                    return (((((((((((((getClass().hashCode() * 31) + this.fieldMatcher.hashCode()) * 31) + this.methodMatcher.hashCode()) * 31) + (this.matchFieldRead ? 1 : 0)) * 31) + (this.matchFieldWrite ? 1 : 0)) * 31) + (this.includeVirtualCalls ? 1 : 0)) * 31) + (this.includeSuperCalls ? 1 : 0)) * 31) + this.substitutionFactory.hashCode();
                }

                protected Factory(ElementMatcher<? super FieldDescription> elementMatcher, ElementMatcher<? super MethodDescription> elementMatcher2, boolean z, boolean z2, boolean z3, boolean z4, Substitution.Factory factory) {
                    this.fieldMatcher = elementMatcher;
                    this.methodMatcher = elementMatcher2;
                    this.matchFieldRead = z;
                    this.matchFieldWrite = z2;
                    this.includeVirtualCalls = z3;
                    this.includeSuperCalls = z4;
                    this.substitutionFactory = factory;
                }

                protected static Factory of(ElementMatcher<? super ByteCodeElement.Member> elementMatcher, Substitution.Factory factory) {
                    return new Factory(elementMatcher, elementMatcher, true, true, true, true, factory);
                }

                protected static Factory ofField(ElementMatcher<? super FieldDescription> elementMatcher, boolean z, boolean z2, Substitution.Factory factory) {
                    return new Factory(elementMatcher, ElementMatchers.none(), z, z2, false, false, factory);
                }

                protected static Factory ofMethod(ElementMatcher<? super MethodDescription> elementMatcher, boolean z, boolean z2, Substitution.Factory factory) {
                    return new Factory(ElementMatchers.none(), elementMatcher, false, false, z, z2, factory);
                }

                @Override // net.bytebuddy.asm.MemberSubstitution.Replacement.Factory
                public Replacement make(TypeDescription typeDescription, MethodDescription methodDescription, TypePool typePool) {
                    return new ForElementMatchers(this.fieldMatcher, this.methodMatcher, this.matchFieldRead, this.matchFieldWrite, this.includeVirtualCalls, this.includeSuperCalls, this.substitutionFactory.make(typeDescription, methodDescription, typePool));
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForFirstBinding implements Replacement {
            private final List<? extends Replacement> replacements;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.replacements.equals(((ForFirstBinding) obj).replacements);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.replacements.hashCode();
            }

            protected ForFirstBinding(List<? extends Replacement> list) {
                this.replacements = list;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Replacement
            public Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, FieldDescription fieldDescription, boolean z) {
                Iterator<? extends Replacement> it = this.replacements.iterator();
                while (it.hasNext()) {
                    Binding bindingBind = it.next().bind(typeDescription, methodDescription, typeDescription2, fieldDescription, z);
                    if (bindingBind.isBound()) {
                        return bindingBind;
                    }
                }
                return Binding.Unresolved.INSTANCE;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Replacement
            public Binding bind(TypeDescription typeDescription, MethodDescription methodDescription, TypeDescription typeDescription2, MethodDescription methodDescription2, InvocationType invocationType) {
                Iterator<? extends Replacement> it = this.replacements.iterator();
                while (it.hasNext()) {
                    Binding bindingBind = it.next().bind(typeDescription, methodDescription, typeDescription2, methodDescription2, invocationType);
                    if (bindingBind.isBound()) {
                        return bindingBind;
                    }
                }
                return Binding.Unresolved.INSTANCE;
            }
        }
    }

    /* JADX INFO: renamed from: net.bytebuddy.asm.MemberSubstitution$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$bytebuddy$asm$MemberSubstitution$Replacement$InvocationType;

        static {
            int[] iArr = new int[Replacement.InvocationType.values().length];
            $SwitchMap$net$bytebuddy$asm$MemberSubstitution$Replacement$InvocationType = iArr;
            try {
                iArr[Replacement.InvocationType.VIRTUAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$bytebuddy$asm$MemberSubstitution$Replacement$InvocationType[Replacement.InvocationType.SUPER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    protected static class SubstitutingMethodVisitor extends LocalVariableAwareMethodVisitor {
        private final boolean failIfNoMatch;
        private final Implementation.Context implementationContext;
        private final MethodDescription instrumentedMethod;
        private final TypeDescription instrumentedType;
        private int localVariableExtension;
        private boolean matched;
        private final MethodGraph.Compiler methodGraphCompiler;
        private final Replacement replacement;
        private int stackSizeBuffer;
        private final boolean strict;
        private final TypePool typePool;
        private final boolean virtualPrivateCalls;

        protected SubstitutingMethodVisitor(MethodVisitor methodVisitor, TypeDescription typeDescription, MethodDescription methodDescription, MethodGraph.Compiler compiler, boolean z, boolean z2, Replacement replacement, Implementation.Context context, TypePool typePool, boolean z3) {
            super(methodVisitor, methodDescription);
            this.instrumentedType = typeDescription;
            this.instrumentedMethod = methodDescription;
            this.methodGraphCompiler = compiler;
            this.strict = z;
            this.failIfNoMatch = z2;
            this.replacement = replacement;
            this.implementationContext = context;
            this.typePool = typePool;
            this.virtualPrivateCalls = z3;
            this.stackSizeBuffer = 0;
            this.localVariableExtension = 0;
        }

        /* JADX WARN: Code duplicated, block: B:35:0x0125  */
        /* JADX WARN: Code duplicated, block: B:36:0x0136  */
        /* JADX WARN: Code duplicated, block: B:39:0x014a  */
        /* JADX WARN: Code duplicated, block: B:40:0x0159  */
        @Override // net.bytebuddy.jar.asm.MethodVisitor
        public void visitFieldInsn(int i, String str, String str2, String str3) {
            ElementMatcher.Junction junctionFailSafe;
            FieldList fieldListFilter;
            TypeList.Generic empty;
            TypeDescription.Generic type;
            TypeList.Generic.Explicit explicit;
            TypeDescription.Generic genericOf;
            TypeList.Generic generic;
            TypeDescription.Generic generic2;
            JavaConstant.MethodHandle methodHandleOfSetter;
            StackManipulation stackManipulationWrite;
            TypePool.Resolution resolutionDescribe = this.typePool.describe(str.replace('/', '.'));
            if (resolutionDescribe.isResolved()) {
                Iterator it = resolutionDescribe.resolve().iterator();
                do {
                    FieldList<?> declaredFields = ((TypeDefinition) it.next()).getDeclaredFields();
                    if (this.strict) {
                        junctionFailSafe = ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3));
                    } else {
                        junctionFailSafe = ElementMatchers.failSafe(ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3)));
                    }
                    fieldListFilter = declaredFields.filter(junctionFailSafe);
                    if (!it.hasNext()) {
                        break;
                    }
                } while (fieldListFilter.isEmpty());
                if (!fieldListFilter.isEmpty()) {
                    boolean z = false;
                    Replacement.Binding bindingBind = this.replacement.bind(this.instrumentedType, this.instrumentedMethod, resolutionDescribe.resolve(), (FieldDescription) fieldListFilter.getOnly(), i == 181 || i == 179);
                    if (bindingBind.isBound()) {
                        switch (i) {
                            case 178:
                                empty = new TypeList.Generic.Empty();
                                type = ((FieldDescription) fieldListFilter.getOnly()).getType();
                                generic = empty;
                                generic2 = type;
                                z = true;
                                int i2 = this.stackSizeBuffer;
                                if (z) {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofGetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                } else {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofSetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                }
                                JavaConstant.MethodHandle methodHandle = methodHandleOfSetter;
                                if (z) {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).read();
                                } else {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).write();
                                }
                                this.stackSizeBuffer = Math.max(i2, bindingBind.make(generic, generic2, methodHandle, stackManipulationWrite, getFreeOffset()).apply(new LocalVariableTracingMethodVisitor(this, this.mv, null), this.implementationContext).getMaximalSize());
                                this.matched = true;
                                return;
                            case 179:
                                explicit = new TypeList.Generic.Explicit(((FieldDescription) fieldListFilter.getOnly()).getType());
                                genericOf = TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Void.TYPE);
                                generic = explicit;
                                generic2 = genericOf;
                                int i3 = this.stackSizeBuffer;
                                if (z) {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofGetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                } else {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofSetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                }
                                JavaConstant.MethodHandle methodHandle2 = methodHandleOfSetter;
                                if (z) {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).read();
                                } else {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).write();
                                }
                                this.stackSizeBuffer = Math.max(i3, bindingBind.make(generic, generic2, methodHandle2, stackManipulationWrite, getFreeOffset()).apply(new LocalVariableTracingMethodVisitor(this, this.mv, null), this.implementationContext).getMaximalSize());
                                this.matched = true;
                                return;
                            case 180:
                                empty = new TypeList.Generic.Explicit(((FieldDescription) fieldListFilter.getOnly()).getDeclaringType());
                                type = ((FieldDescription) fieldListFilter.getOnly()).getType();
                                generic = empty;
                                generic2 = type;
                                z = true;
                                int i4 = this.stackSizeBuffer;
                                if (z) {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofGetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                } else {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofSetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                }
                                JavaConstant.MethodHandle methodHandle3 = methodHandleOfSetter;
                                if (z) {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).read();
                                } else {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).write();
                                }
                                this.stackSizeBuffer = Math.max(i4, bindingBind.make(generic, generic2, methodHandle3, stackManipulationWrite, getFreeOffset()).apply(new LocalVariableTracingMethodVisitor(this, this.mv, null), this.implementationContext).getMaximalSize());
                                this.matched = true;
                                return;
                            case 181:
                                explicit = new TypeList.Generic.Explicit(((FieldDescription) fieldListFilter.getOnly()).getDeclaringType(), ((FieldDescription) fieldListFilter.getOnly()).getType());
                                genericOf = TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Void.TYPE);
                                generic = explicit;
                                generic2 = genericOf;
                                int i5 = this.stackSizeBuffer;
                                if (z) {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofGetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                } else {
                                    methodHandleOfSetter = JavaConstant.MethodHandle.ofSetter(((FieldDescription) fieldListFilter.getOnly()).asDefined());
                                }
                                JavaConstant.MethodHandle methodHandle4 = methodHandleOfSetter;
                                if (z) {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).read();
                                } else {
                                    stackManipulationWrite = FieldAccess.forField((FieldDescription) fieldListFilter.getOnly()).write();
                                }
                                this.stackSizeBuffer = Math.max(i5, bindingBind.make(generic, generic2, methodHandle4, stackManipulationWrite, getFreeOffset()).apply(new LocalVariableTracingMethodVisitor(this, this.mv, null), this.implementationContext).getMaximalSize());
                                this.matched = true;
                                return;
                            default:
                                throw new IllegalStateException("Unexpected opcode: " + i);
                        }
                    }
                } else if (this.strict) {
                    throw new IllegalStateException("Could not resolve " + str.replace('/', '.') + "." + str2 + str3 + " using " + this.typePool);
                }
            } else if (this.strict) {
                throw new IllegalStateException("Could not resolve " + str.replace('/', '.') + " using " + this.typePool);
            }
            super.visitFieldInsn(i, str, str2, str3);
        }

        @Override // net.bytebuddy.jar.asm.MethodVisitor
        public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
            ElementMatcher.Junction junctionFailSafe;
            MethodList methodListFilter;
            ElementMatcher.Junction junctionFailSafe2;
            ElementMatcher.Junction junctionFailSafe3;
            ElementMatcher.Junction junctionFailSafe4;
            ElementMatcher.Junction junctionFailSafe5;
            MethodList methodList;
            TypeDescription.Generic returnType;
            JavaConstant.MethodHandle methodHandleOf;
            StackManipulation stackManipulationInvoke;
            ElementMatcher.Junction junctionFailSafe6;
            TypePool.Resolution resolutionDescribe = this.typePool.describe(str.replace('/', '.'));
            if (resolutionDescribe.isResolved()) {
                if (i == 183 && str2.equals("<init>")) {
                    MethodList<MethodDescription.InDefinedShape> declaredMethods = resolutionDescribe.resolve().getDeclaredMethods();
                    if (this.strict) {
                        junctionFailSafe6 = ElementMatchers.isConstructor().and(ElementMatchers.hasDescriptor(str3));
                    } else {
                        junctionFailSafe6 = ElementMatchers.failSafe(ElementMatchers.isConstructor().and(ElementMatchers.hasDescriptor(str3)));
                    }
                    methodListFilter = declaredMethods.filter(junctionFailSafe6);
                } else if (i == 184) {
                    Iterator it = resolutionDescribe.resolve().iterator();
                    do {
                        MethodList<?> declaredMethods2 = ((TypeDefinition) it.next()).getDeclaredMethods();
                        if (this.strict) {
                            junctionFailSafe5 = ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3));
                        } else {
                            junctionFailSafe5 = ElementMatchers.failSafe(ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3)));
                        }
                        methodList = (MethodList) declaredMethods2.filter(junctionFailSafe5);
                        if (!it.hasNext()) {
                            break;
                        }
                    } while (methodList.isEmpty());
                    methodListFilter = methodList;
                } else if (i == 183) {
                    MethodList<MethodDescription.InDefinedShape> declaredMethods3 = resolutionDescribe.resolve().getDeclaredMethods();
                    if (this.strict) {
                        junctionFailSafe4 = ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3));
                    } else {
                        junctionFailSafe4 = ElementMatchers.failSafe(ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3)));
                    }
                    methodListFilter = declaredMethods3.filter(junctionFailSafe4);
                } else if (this.virtualPrivateCalls) {
                    MethodList<MethodDescription.InDefinedShape> declaredMethods4 = resolutionDescribe.resolve().getDeclaredMethods();
                    if (this.strict) {
                        junctionFailSafe2 = ElementMatchers.isPrivate().and(ElementMatchers.not(ElementMatchers.isStatic())).and(ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3)));
                    } else {
                        junctionFailSafe2 = ElementMatchers.failSafe(ElementMatchers.isPrivate().and(ElementMatchers.not(ElementMatchers.isStatic())).and(ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3))));
                    }
                    methodListFilter = declaredMethods4.filter(junctionFailSafe2);
                    if (methodListFilter.isEmpty()) {
                        MethodList<?> methodListAsMethodList = this.methodGraphCompiler.compile((TypeDefinition) resolutionDescribe.resolve(), this.instrumentedType).listNodes().asMethodList();
                        if (this.strict) {
                            junctionFailSafe3 = ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3));
                        } else {
                            junctionFailSafe3 = ElementMatchers.failSafe(ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3)));
                        }
                        methodListFilter = (MethodList) methodListAsMethodList.filter(junctionFailSafe3);
                    }
                } else {
                    MethodList<?> methodListAsMethodList2 = this.methodGraphCompiler.compile((TypeDefinition) resolutionDescribe.resolve(), this.instrumentedType).listNodes().asMethodList();
                    if (this.strict) {
                        junctionFailSafe = ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3));
                    } else {
                        junctionFailSafe = ElementMatchers.failSafe(ElementMatchers.named(str2).and(ElementMatchers.hasDescriptor(str3)));
                    }
                    methodListFilter = methodListAsMethodList2.filter(junctionFailSafe);
                }
                if (!methodListFilter.isEmpty()) {
                    Replacement.Binding bindingBind = this.replacement.bind(this.instrumentedType, this.instrumentedMethod, resolutionDescribe.resolve(), (MethodDescription) methodListFilter.getOnly(), Replacement.InvocationType.of(i, (MethodDescription) methodListFilter.getOnly()));
                    if (bindingBind.isBound()) {
                        TypeList.Generic genericAsTypeList = (((MethodDescription) methodListFilter.getOnly()).isStatic() || ((MethodDescription) methodListFilter.getOnly()).isConstructor()) ? ((MethodDescription) methodListFilter.getOnly()).getParameters().asTypeList() : new TypeList.Generic.Explicit((List<? extends TypeDefinition>) CompoundList.of(resolutionDescribe.resolve(), ((MethodDescription) methodListFilter.getOnly()).getParameters().asTypeList()));
                        if (((MethodDescription) methodListFilter.getOnly()).isConstructor()) {
                            returnType = ((MethodDescription) methodListFilter.getOnly()).getDeclaringType().asGenericType();
                        } else {
                            returnType = ((MethodDescription) methodListFilter.getOnly()).getReturnType();
                        }
                        TypeDescription.Generic generic = returnType;
                        if (i == 183 && ((MethodDescription) methodListFilter.getOnly()).isMethod() && !((MethodDescription) methodListFilter.getOnly()).isPrivate()) {
                            methodHandleOf = JavaConstant.MethodHandle.ofSpecial(((MethodDescription) methodListFilter.getOnly()).asDefined(), resolutionDescribe.resolve());
                        } else {
                            methodHandleOf = JavaConstant.MethodHandle.of(((MethodDescription) methodListFilter.getOnly()).asDefined());
                        }
                        JavaConstant.MethodHandle methodHandle = methodHandleOf;
                        if (i == 183 && ((MethodDescription) methodListFilter.getOnly()).isMethod() && !((MethodDescription) methodListFilter.getOnly()).isPrivate()) {
                            stackManipulationInvoke = MethodInvocation.invoke((MethodDescription) methodListFilter.getOnly()).special(resolutionDescribe.resolve());
                        } else {
                            stackManipulationInvoke = MethodInvocation.invoke((MethodDescription) methodListFilter.getOnly());
                        }
                        StackManipulation.Size sizeApply = bindingBind.make(genericAsTypeList, generic, methodHandle, stackManipulationInvoke, getFreeOffset()).apply(new LocalVariableTracingMethodVisitor(this, this.mv, null), this.implementationContext);
                        if (((MethodDescription) methodListFilter.getOnly()).isConstructor()) {
                            int iMax = Math.max(this.stackSizeBuffer, sizeApply.getMaximalSize() + 2);
                            this.stackSizeBuffer = iMax;
                            this.stackSizeBuffer = Math.max(iMax, new StackManipulation.Compound(Duplication.SINGLE.flipOver(TypeDescription.ForLoadedType.of(Object.class)), Removal.SINGLE, Removal.SINGLE, Duplication.SINGLE.flipOver(TypeDescription.ForLoadedType.of(Object.class)), Removal.SINGLE, Removal.SINGLE).apply(this.mv, this.implementationContext).getMaximalSize() + StackSize.SINGLE.getSize());
                        } else {
                            this.stackSizeBuffer = Math.max(this.stackSizeBuffer, sizeApply.getMaximalSize());
                        }
                        this.matched = true;
                        return;
                    }
                } else if (this.strict) {
                    throw new IllegalStateException("Could not resolve " + str.replace('/', '.') + "." + str2 + str3 + " using " + this.typePool);
                }
            } else if (this.strict) {
                throw new IllegalStateException("Could not resolve " + str.replace('/', '.') + " using " + this.typePool);
            }
            super.visitMethodInsn(i, str, str2, str3, z);
        }

        @Override // net.bytebuddy.jar.asm.MethodVisitor
        public void visitMaxs(int i, int i2) {
            if (this.failIfNoMatch && !this.matched) {
                throw new IllegalStateException("No substitution found within " + this.instrumentedMethod + " of " + this.instrumentedType);
            }
            super.visitMaxs(i + this.stackSizeBuffer, Math.max(this.localVariableExtension, i2));
        }

        private class LocalVariableTracingMethodVisitor extends MethodVisitor {
            /* synthetic */ LocalVariableTracingMethodVisitor(SubstitutingMethodVisitor substitutingMethodVisitor, MethodVisitor methodVisitor, AnonymousClass1 anonymousClass1) {
                this(methodVisitor);
            }

            private LocalVariableTracingMethodVisitor(MethodVisitor methodVisitor) {
                super(OpenedClassReader.ASM_API, methodVisitor);
            }

            @Override // net.bytebuddy.jar.asm.MethodVisitor
            public void visitVarInsn(int i, int i2) {
                switch (i) {
                    case 54:
                    case 56:
                    case 58:
                        SubstitutingMethodVisitor substitutingMethodVisitor = SubstitutingMethodVisitor.this;
                        substitutingMethodVisitor.localVariableExtension = Math.max(substitutingMethodVisitor.localVariableExtension, i2 + 1);
                        break;
                    case 55:
                    case 57:
                        SubstitutingMethodVisitor substitutingMethodVisitor2 = SubstitutingMethodVisitor.this;
                        substitutingMethodVisitor2.localVariableExtension = Math.max(substitutingMethodVisitor2.localVariableExtension, i2 + 2);
                        break;
                }
                super.visitVarInsn(i, i2);
            }
        }
    }

    public enum Source {
        SUBSTITUTED_ELEMENT { // from class: net.bytebuddy.asm.MemberSubstitution.Source.1
            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected ByteCodeElement.Member element(ByteCodeElement.Member member, MethodDescription methodDescription) {
                return member;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected JavaConstant.MethodHandle handle(JavaConstant.MethodHandle methodHandle, MethodDescription methodDescription) {
                return methodHandle;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            @MaybeNull
            protected Value self(TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription) {
                if (member.isStatic()) {
                    return null;
                }
                return new Value((TypeDescription.Generic) generic.get(0), map.get(0).intValue());
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            @MaybeNull
            protected Value argument(int i, TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription) {
                if (i < generic.size() - (!member.isStatic() ? 1 : 0)) {
                    return new Value((TypeDescription.Generic) generic.get((!member.isStatic() ? 1 : 0) + i), map.get(Integer.valueOf(i + (!member.isStatic() ? 1 : 0))).intValue());
                }
                return null;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected List<Value> arguments(boolean z, TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription) {
                int i = 0;
                ArrayList arrayList = new ArrayList(generic.size() - ((z || member.isStatic()) ? 0 : 1));
                if (!member.isStatic() && !z) {
                    i = 1;
                }
                while (i < generic.size()) {
                    arrayList.add(new Value((TypeDescription.Generic) generic.get(i), map.get(Integer.valueOf(i)).intValue()));
                    i++;
                }
                return arrayList;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected boolean isRepresentable(Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort sort, ByteCodeElement.Member member, MethodDescription methodDescription) {
                return sort.isRepresentable(member);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected StackManipulation resolve(Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort sort, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, MethodDescription methodDescription) {
                return sort.resolve(member, generic.asErasures(), generic2.asErasure());
            }
        },
        ENCLOSING_METHOD { // from class: net.bytebuddy.asm.MemberSubstitution.Source.2
            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected ByteCodeElement.Member element(ByteCodeElement.Member member, MethodDescription methodDescription) {
                return methodDescription;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            @MaybeNull
            protected Value self(TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription) {
                if (methodDescription.isStatic()) {
                    return null;
                }
                return new Value(methodDescription.getDeclaringType().asGenericType(), 0);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            @MaybeNull
            protected Value argument(int i, TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription) {
                if (i >= methodDescription.getParameters().size()) {
                    return null;
                }
                ParameterDescription parameterDescription = (ParameterDescription) methodDescription.getParameters().get(i);
                return new Value(parameterDescription.getType(), parameterDescription.getOffset());
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected List<Value> arguments(boolean z, TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription) {
                ArrayList arrayList;
                if (z && !methodDescription.isStatic()) {
                    arrayList = new ArrayList(methodDescription.getParameters().size() + 1);
                    arrayList.add(new Value(methodDescription.getDeclaringType().asGenericType(), 0));
                } else {
                    arrayList = new ArrayList(methodDescription.getParameters().size());
                }
                Iterator it = methodDescription.getParameters().iterator();
                while (it.hasNext()) {
                    ParameterDescription parameterDescription = (ParameterDescription) it.next();
                    arrayList.add(new Value(parameterDescription.getType(), parameterDescription.getOffset()));
                }
                return arrayList;
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected JavaConstant.MethodHandle handle(JavaConstant.MethodHandle methodHandle, MethodDescription methodDescription) {
                return JavaConstant.MethodHandle.of(methodDescription.asDefined());
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected boolean isRepresentable(Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort sort, ByteCodeElement.Member member, MethodDescription methodDescription) {
                return sort.isRepresentable(methodDescription);
            }

            @Override // net.bytebuddy.asm.MemberSubstitution.Source
            protected StackManipulation resolve(Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort sort, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, MethodDescription methodDescription) {
                List<TypeDescription> listAsErasures;
                TypeDescription typeDescriptionAsErasure;
                if (methodDescription.isStatic() || methodDescription.isConstructor()) {
                    listAsErasures = methodDescription.getParameters().asTypeList().asErasures();
                } else {
                    listAsErasures = CompoundList.of(methodDescription.getDeclaringType().asErasure(), methodDescription.getParameters().asTypeList().asErasures());
                }
                if (methodDescription.isConstructor()) {
                    typeDescriptionAsErasure = methodDescription.getDeclaringType().asErasure();
                } else {
                    typeDescriptionAsErasure = methodDescription.getReturnType().asErasure();
                }
                return sort.resolve(methodDescription, listAsErasures, typeDescriptionAsErasure);
            }
        };

        @MaybeNull
        protected abstract Value argument(int i, TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription);

        protected abstract List<Value> arguments(boolean z, TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription);

        protected abstract ByteCodeElement.Member element(ByteCodeElement.Member member, MethodDescription methodDescription);

        protected abstract JavaConstant.MethodHandle handle(JavaConstant.MethodHandle methodHandle, MethodDescription methodDescription);

        protected abstract boolean isRepresentable(Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort sort, ByteCodeElement.Member member, MethodDescription methodDescription);

        protected abstract StackManipulation resolve(Substitution.Chain.Step.ForDelegation.OffsetMapping.ForOrigin.Sort sort, ByteCodeElement.Member member, TypeList.Generic generic, TypeDescription.Generic generic2, MethodDescription methodDescription);

        @MaybeNull
        protected abstract Value self(TypeList.Generic generic, Map<Integer, Integer> map, ByteCodeElement.Member member, MethodDescription methodDescription);

        /* synthetic */ Source(AnonymousClass1 anonymousClass1) {
            this();
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class Value {
            private final int offset;
            private final TypeDescription.Generic typeDescription;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Value value = (Value) obj;
                return this.offset == value.offset && this.typeDescription.equals(value.typeDescription);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.typeDescription.hashCode()) * 31) + this.offset;
            }

            protected Value(TypeDescription.Generic generic, int i) {
                this.typeDescription = generic;
                this.offset = i;
            }

            protected TypeDescription.Generic getTypeDescription() {
                return this.typeDescription;
            }

            protected int getOffset() {
                return this.offset;
            }
        }
    }
}
