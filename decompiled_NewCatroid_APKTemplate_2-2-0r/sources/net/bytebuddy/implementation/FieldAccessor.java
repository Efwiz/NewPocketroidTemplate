package net.bytebuddy.implementation;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.scaffold.FieldLocator;
import net.bytebuddy.dynamic.scaffold.InstrumentedType;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.implementation.bytecode.StackManipulation;
import net.bytebuddy.implementation.bytecode.assign.Assigner;
import net.bytebuddy.implementation.bytecode.constant.ClassConstant;
import net.bytebuddy.implementation.bytecode.constant.DefaultValue;
import net.bytebuddy.implementation.bytecode.member.FieldAccess;
import net.bytebuddy.implementation.bytecode.member.MethodReturn;
import net.bytebuddy.implementation.bytecode.member.MethodVariableAccess;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.ConstantValue;
import net.bytebuddy.utility.JavaConstant;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;

/* JADX INFO: loaded from: classes12.dex */
@HashCodeAndEqualsPlugin.Enhance
public abstract class FieldAccessor implements Implementation {
    protected final Assigner assigner;
    protected final FieldLocation fieldLocation;
    protected final Assigner.Typing typing;

    public interface AssignerConfigurable extends PropertyConfigurable {
        PropertyConfigurable withAssigner(Assigner assigner, Assigner.Typing typing);
    }

    public interface OwnerTypeLocatable extends AssignerConfigurable {
        AssignerConfigurable in(Class<?> cls);

        AssignerConfigurable in(TypeDescription typeDescription);

        AssignerConfigurable in(FieldLocator.Factory factory);
    }

    public interface PropertyConfigurable extends Implementation {
        Implementation.Composable setsArgumentAt(int i);

        Implementation.Composable setsDefaultValue();

        Implementation.Composable setsFieldValueOf(String str);

        Implementation.Composable setsFieldValueOf(Field field);

        Implementation.Composable setsFieldValueOf(FieldDescription fieldDescription);

        Implementation.Composable setsFieldValueOf(FieldNameExtractor fieldNameExtractor);

        Implementation.Composable setsReference(Object obj);

        Implementation.Composable setsReference(Object obj, String str);

        Implementation.Composable setsValue(Object obj);

        Implementation.Composable setsValue(TypeDescription typeDescription);

        Implementation.Composable setsValue(StackManipulation stackManipulation, Type type);

        Implementation.Composable setsValue(StackManipulation stackManipulation, TypeDescription.Generic generic);

        Implementation.Composable setsValue(ConstantValue constantValue);

        Implementation.Composable setsValue(JavaConstant javaConstant);
    }

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        FieldAccessor fieldAccessor = (FieldAccessor) obj;
        return this.typing.equals(fieldAccessor.typing) && this.fieldLocation.equals(fieldAccessor.fieldLocation) && this.assigner.equals(fieldAccessor.assigner);
    }

    public int hashCode() {
        return (((((getClass().hashCode() * 31) + this.fieldLocation.hashCode()) * 31) + this.assigner.hashCode()) * 31) + this.typing.hashCode();
    }

    protected FieldAccessor(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing) {
        this.fieldLocation = fieldLocation;
        this.assigner = assigner;
        this.typing = typing;
    }

    public static OwnerTypeLocatable ofField(String str) {
        return of(new FieldNameExtractor.ForFixedValue(str));
    }

    public static OwnerTypeLocatable ofBeanProperty() {
        return of(FieldNameExtractor.ForBeanProperty.INSTANCE, FieldNameExtractor.ForBeanProperty.CAPITALIZED);
    }

    public static OwnerTypeLocatable of(FieldNameExtractor fieldNameExtractor) {
        return of((List<? extends FieldNameExtractor>) Collections.singletonList(fieldNameExtractor));
    }

    public static OwnerTypeLocatable of(FieldNameExtractor... fieldNameExtractorArr) {
        return of((List<? extends FieldNameExtractor>) Arrays.asList(fieldNameExtractorArr));
    }

    public static OwnerTypeLocatable of(List<? extends FieldNameExtractor> list) {
        return new ForImplicitProperty(new FieldLocation.Relative(list));
    }

    public static AssignerConfigurable of(Field field) {
        return of(new FieldDescription.ForLoadedField(field));
    }

    public static AssignerConfigurable of(FieldDescription fieldDescription) {
        return new ForImplicitProperty(new FieldLocation.Absolute(fieldDescription));
    }

    protected interface FieldLocation {

        public interface Prepared {
            FieldDescription resolve(MethodDescription methodDescription);
        }

        Prepared prepare(TypeDescription typeDescription);

        FieldLocation with(FieldLocator.Factory factory);

        @HashCodeAndEqualsPlugin.Enhance
        public static class Absolute implements FieldLocation, Prepared {
            private final FieldDescription fieldDescription;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.fieldDescription.equals(((Absolute) obj).fieldDescription);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.fieldDescription.hashCode();
            }

            protected Absolute(FieldDescription fieldDescription) {
                this.fieldDescription = fieldDescription;
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.FieldLocation
            public FieldLocation with(FieldLocator.Factory factory) {
                throw new IllegalStateException("Cannot specify a field locator factory for an absolute field location");
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.FieldLocation
            public Prepared prepare(TypeDescription typeDescription) {
                if (!this.fieldDescription.isStatic() && !typeDescription.isAssignableTo(this.fieldDescription.getDeclaringType().asErasure())) {
                    throw new IllegalStateException(this.fieldDescription + " is not declared by " + typeDescription);
                }
                if (this.fieldDescription.isAccessibleTo(typeDescription)) {
                    return this;
                }
                throw new IllegalStateException("Cannot access " + this.fieldDescription + " from " + typeDescription);
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.FieldLocation.Prepared
            public FieldDescription resolve(MethodDescription methodDescription) {
                return this.fieldDescription;
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class Relative implements FieldLocation {
            private final FieldLocator.Factory fieldLocatorFactory;
            private final List<? extends FieldNameExtractor> fieldNameExtractors;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Relative relative = (Relative) obj;
                return this.fieldNameExtractors.equals(relative.fieldNameExtractors) && this.fieldLocatorFactory.equals(relative.fieldLocatorFactory);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.fieldNameExtractors.hashCode()) * 31) + this.fieldLocatorFactory.hashCode();
            }

            protected Relative(List<? extends FieldNameExtractor> list) {
                this(list, FieldLocator.ForClassHierarchy.Factory.INSTANCE);
            }

            private Relative(List<? extends FieldNameExtractor> list, FieldLocator.Factory factory) {
                this.fieldNameExtractors = list;
                this.fieldLocatorFactory = factory;
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.FieldLocation
            public FieldLocation with(FieldLocator.Factory factory) {
                return new Relative(this.fieldNameExtractors, factory);
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.FieldLocation
            public Prepared prepare(TypeDescription typeDescription) {
                return new Prepared(this.fieldNameExtractors, this.fieldLocatorFactory.make(typeDescription));
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class Prepared implements Prepared {
                private final FieldLocator fieldLocator;
                private final List<? extends FieldNameExtractor> fieldNameExtractors;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Prepared prepared = (Prepared) obj;
                    return this.fieldNameExtractors.equals(prepared.fieldNameExtractors) && this.fieldLocator.equals(prepared.fieldLocator);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.fieldNameExtractors.hashCode()) * 31) + this.fieldLocator.hashCode();
                }

                protected Prepared(List<? extends FieldNameExtractor> list, FieldLocator fieldLocator) {
                    this.fieldNameExtractors = list;
                    this.fieldLocator = fieldLocator;
                }

                @Override // net.bytebuddy.implementation.FieldAccessor.FieldLocation.Prepared
                public FieldDescription resolve(MethodDescription methodDescription) {
                    FieldLocator.Resolution resolutionLocate = FieldLocator.Resolution.Illegal.INSTANCE;
                    Iterator<? extends FieldNameExtractor> it = this.fieldNameExtractors.iterator();
                    while (it.hasNext() && !resolutionLocate.isResolved()) {
                        resolutionLocate = this.fieldLocator.locate(it.next().resolve(methodDescription));
                    }
                    if (!resolutionLocate.isResolved()) {
                        throw new IllegalStateException("Cannot resolve field for " + methodDescription + " using " + this.fieldLocator);
                    }
                    return resolutionLocate.getField();
                }
            }
        }
    }

    public interface FieldNameExtractor {
        String resolve(MethodDescription methodDescription);

        public enum ForBeanProperty implements FieldNameExtractor {
            INSTANCE { // from class: net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor.ForBeanProperty.1
                @Override // net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor.ForBeanProperty
                protected char resolve(char c) {
                    return Character.toLowerCase(c);
                }
            },
            CAPITALIZED { // from class: net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor.ForBeanProperty.2
                @Override // net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor.ForBeanProperty
                protected char resolve(char c) {
                    return Character.toUpperCase(c);
                }
            };

            protected abstract char resolve(char c);

            @Override // net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor
            public String resolve(MethodDescription methodDescription) {
                int i;
                String internalName = methodDescription.getInternalName();
                if (internalName.startsWith("get") || internalName.startsWith(FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
                    i = 3;
                } else {
                    if (!internalName.startsWith(LanguageCodeUtil.IS)) {
                        throw new IllegalArgumentException(methodDescription + " does not follow Java bean naming conventions");
                    }
                    i = 2;
                }
                String strSubstring = internalName.substring(i);
                if (strSubstring.length() == 0) {
                    throw new IllegalArgumentException(methodDescription + " does not specify a bean name");
                }
                return resolve(strSubstring.charAt(0)) + strSubstring.substring(1);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForFixedValue implements FieldNameExtractor {

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f362name;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.f362name.equals(((ForFixedValue) obj).f362name);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.f362name.hashCode();
            }

            protected ForFixedValue(String str) {
                this.f362name = str;
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.FieldNameExtractor
            public String resolve(MethodDescription methodDescription) {
                return this.f362name;
            }
        }
    }

    protected static class ForImplicitProperty extends FieldAccessor implements OwnerTypeLocatable {
        @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
        public InstrumentedType prepare(InstrumentedType instrumentedType) {
            return instrumentedType;
        }

        protected ForImplicitProperty(FieldLocation fieldLocation) {
            this(fieldLocation, Assigner.DEFAULT, Assigner.Typing.STATIC);
        }

        private ForImplicitProperty(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing) {
            super(fieldLocation, assigner, typing);
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return new Appender(this.fieldLocation.prepare(target.getInstrumentedType()));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsArgumentAt(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("A parameter index cannot be negative: " + i);
            }
            return new ForSetter.OfParameterValue(this.fieldLocation, this.assigner, this.typing, ForSetter.TerminationHandler.RETURNING, i);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsDefaultValue() {
            return new ForSetter.OfDefaultValue(this.fieldLocation, this.assigner, this.typing, ForSetter.TerminationHandler.RETURNING);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsValue(@MaybeNull Object obj) {
            if (obj == null) {
                return setsDefaultValue();
            }
            ConstantValue constantValueWrapOrNull = ConstantValue.Simple.wrapOrNull(obj);
            if (constantValueWrapOrNull == null) {
                return setsReference(obj);
            }
            return setsValue(constantValueWrapOrNull.toStackManipulation(), constantValueWrapOrNull.getTypeDescription().asGenericType());
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsValue(TypeDescription typeDescription) {
            return setsValue(ClassConstant.of(typeDescription), Class.class);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsValue(ConstantValue constantValue) {
            return setsValue(constantValue.toStackManipulation(), constantValue.getTypeDescription().asGenericType());
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsValue(JavaConstant javaConstant) {
            return setsValue((ConstantValue) javaConstant);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsValue(StackManipulation stackManipulation, Type type) {
            return setsValue(stackManipulation, TypeDefinition.Sort.describe(type));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsValue(StackManipulation stackManipulation, TypeDescription.Generic generic) {
            return new ForSetter.OfConstantValue(this.fieldLocation, this.assigner, this.typing, ForSetter.TerminationHandler.RETURNING, generic, stackManipulation);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsReference(Object obj) {
            return setsReference(obj, "fixedFieldValue$" + RandomString.hashOf(obj));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsReference(Object obj, String str) {
            return new ForSetter.OfReferenceValue(this.fieldLocation, this.assigner, this.typing, ForSetter.TerminationHandler.RETURNING, obj, str);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsFieldValueOf(Field field) {
            return setsFieldValueOf(new FieldDescription.ForLoadedField(field));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsFieldValueOf(FieldDescription fieldDescription) {
            return new ForSetter.OfFieldValue(this.fieldLocation, this.assigner, this.typing, ForSetter.TerminationHandler.RETURNING, new FieldLocation.Absolute(fieldDescription));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsFieldValueOf(String str) {
            return setsFieldValueOf(new FieldNameExtractor.ForFixedValue(str));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.PropertyConfigurable
        public Implementation.Composable setsFieldValueOf(FieldNameExtractor fieldNameExtractor) {
            return new ForSetter.OfFieldValue(this.fieldLocation, this.assigner, this.typing, ForSetter.TerminationHandler.RETURNING, new FieldLocation.Relative(Collections.singletonList(fieldNameExtractor)));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.AssignerConfigurable
        public PropertyConfigurable withAssigner(Assigner assigner, Assigner.Typing typing) {
            return new ForImplicitProperty(this.fieldLocation, assigner, typing);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.OwnerTypeLocatable
        public AssignerConfigurable in(Class<?> cls) {
            return in(TypeDescription.ForLoadedType.of(cls));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.OwnerTypeLocatable
        public AssignerConfigurable in(TypeDescription typeDescription) {
            return in(new FieldLocator.ForExactType.Factory(typeDescription));
        }

        @Override // net.bytebuddy.implementation.FieldAccessor.OwnerTypeLocatable
        public AssignerConfigurable in(FieldLocator.Factory factory) {
            return new ForImplicitProperty(this.fieldLocation.with(factory), this.assigner, this.typing);
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class Appender implements ByteCodeAppender {
            private final FieldLocation.Prepared fieldLocation;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Appender appender = (Appender) obj;
                return this.fieldLocation.equals(appender.fieldLocation) && ForImplicitProperty.this.equals(ForImplicitProperty.this);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.fieldLocation.hashCode()) * 31) + ForImplicitProperty.this.hashCode();
            }

            protected Appender(FieldLocation.Prepared prepared) {
                this.fieldLocation = prepared;
            }

            @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
            public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                StackManipulation.Compound compound;
                if (!methodDescription.isMethod()) {
                    throw new IllegalArgumentException(methodDescription + " does not describe a field getter or setter");
                }
                FieldDescription fieldDescriptionResolve = this.fieldLocation.resolve(methodDescription);
                if (!fieldDescriptionResolve.isStatic() && methodDescription.isStatic()) {
                    throw new IllegalStateException("Cannot set instance field " + fieldDescriptionResolve + " from " + methodDescription);
                }
                StackManipulation stackManipulationLoadThis = fieldDescriptionResolve.isStatic() ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.loadThis();
                if (!methodDescription.getReturnType().represents(Void.TYPE)) {
                    compound = new StackManipulation.Compound(stackManipulationLoadThis, FieldAccess.forField(fieldDescriptionResolve).read(), ForImplicitProperty.this.assigner.assign(fieldDescriptionResolve.getType(), methodDescription.getReturnType(), ForImplicitProperty.this.typing), MethodReturn.of(methodDescription.getReturnType()));
                } else if (methodDescription.getReturnType().represents(Void.TYPE) && methodDescription.getParameters().size() == 1) {
                    if (fieldDescriptionResolve.isFinal() && methodDescription.isMethod()) {
                        throw new IllegalStateException("Cannot set final field " + fieldDescriptionResolve + " from " + methodDescription);
                    }
                    compound = new StackManipulation.Compound(stackManipulationLoadThis, MethodVariableAccess.load((ParameterDescription) methodDescription.getParameters().get(0)), ForImplicitProperty.this.assigner.assign(((ParameterDescription) methodDescription.getParameters().get(0)).getType(), fieldDescriptionResolve.getType(), ForImplicitProperty.this.typing), FieldAccess.forField(fieldDescriptionResolve).write(), MethodReturn.VOID);
                } else {
                    throw new IllegalArgumentException("Method " + methodDescription + " is no bean accessor");
                }
                if (!compound.isValid()) {
                    throw new IllegalStateException("Cannot set or get value of " + methodDescription + " using " + fieldDescriptionResolve);
                }
                return new ByteCodeAppender.Size(compound.apply(methodVisitor, context).getMaximalSize(), methodDescription.getStackSize());
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static abstract class ForSetter<T> extends FieldAccessor implements Implementation.Composable {
        private final TerminationHandler terminationHandler;

        protected enum TerminationHandler {
            RETURNING { // from class: net.bytebuddy.implementation.FieldAccessor.ForSetter.TerminationHandler.1
                @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter.TerminationHandler
                protected StackManipulation resolve(MethodDescription methodDescription) {
                    if (!methodDescription.getReturnType().represents(Void.TYPE)) {
                        throw new IllegalStateException("Cannot implement setter with return value for " + methodDescription);
                    }
                    return MethodReturn.VOID;
                }
            },
            NON_OPERATIONAL { // from class: net.bytebuddy.implementation.FieldAccessor.ForSetter.TerminationHandler.2
                @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter.TerminationHandler
                protected StackManipulation resolve(MethodDescription methodDescription) {
                    return StackManipulation.Trivial.INSTANCE;
                }
            };

            protected abstract StackManipulation resolve(MethodDescription methodDescription);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.terminationHandler.equals(((ForSetter) obj).terminationHandler);
        }

        @Override // net.bytebuddy.implementation.FieldAccessor
        public int hashCode() {
            return (super.hashCode() * 31) + this.terminationHandler.hashCode();
        }

        @MaybeNull
        protected abstract T initialize(TypeDescription typeDescription);

        protected abstract StackManipulation resolve(@MaybeNull T t, FieldDescription fieldDescription, TypeDescription typeDescription, MethodDescription methodDescription);

        protected ForSetter(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing, TerminationHandler terminationHandler) {
            super(fieldLocation, assigner, typing);
            this.terminationHandler = terminationHandler;
        }

        @Override // net.bytebuddy.implementation.Implementation
        public ByteCodeAppender appender(Implementation.Target target) {
            return new Appender(target.getInstrumentedType(), initialize(target.getInstrumentedType()), this.fieldLocation.prepare(target.getInstrumentedType()));
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class OfParameterValue extends ForSetter<Void> {
            private final int index;

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
            public boolean equals(@MaybeNull Object obj) {
                if (!super.equals(obj)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.index == ((OfParameterValue) obj).index;
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
            public int hashCode() {
                return (super.hashCode() * 31) + this.index;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            @AlwaysNull
            public Void initialize(TypeDescription typeDescription) {
                return null;
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            protected OfParameterValue(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing, TerminationHandler terminationHandler, int i) {
                super(fieldLocation, assigner, typing, terminationHandler);
                this.index = i;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            public StackManipulation resolve(@MaybeNull Void r3, FieldDescription fieldDescription, TypeDescription typeDescription, MethodDescription methodDescription) {
                if (methodDescription.getParameters().size() <= this.index) {
                    throw new IllegalStateException(methodDescription + " does not define a parameter with index " + this.index);
                }
                return new StackManipulation.Compound(MethodVariableAccess.load((ParameterDescription) methodDescription.getParameters().get(this.index)), this.assigner.assign(((ParameterDescription) methodDescription.getParameters().get(this.index)).getType(), fieldDescription.getType(), this.typing));
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation andThen(Implementation implementation) {
                return new Implementation.Compound(new OfParameterValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.index), implementation);
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation.Composable andThen(Implementation.Composable composable) {
                return new Implementation.Compound.Composable(new OfParameterValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.index), composable);
            }
        }

        protected static class OfDefaultValue extends ForSetter<Void> {
            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            @AlwaysNull
            public Void initialize(TypeDescription typeDescription) {
                return null;
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            protected OfDefaultValue(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing, TerminationHandler terminationHandler) {
                super(fieldLocation, assigner, typing, terminationHandler);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            public StackManipulation resolve(@MaybeNull Void r1, FieldDescription fieldDescription, TypeDescription typeDescription, MethodDescription methodDescription) {
                return DefaultValue.of(fieldDescription.getType());
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation andThen(Implementation implementation) {
                return new Implementation.Compound(new OfDefaultValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL), implementation);
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation.Composable andThen(Implementation.Composable composable) {
                return new Implementation.Compound.Composable(new OfDefaultValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL), composable);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class OfConstantValue extends ForSetter<Void> {
            private final StackManipulation stackManipulation;
            private final TypeDescription.Generic typeDescription;

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
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
                OfConstantValue ofConstantValue = (OfConstantValue) obj;
                return this.typeDescription.equals(ofConstantValue.typeDescription) && this.stackManipulation.equals(ofConstantValue.stackManipulation);
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
            public int hashCode() {
                return (((super.hashCode() * 31) + this.typeDescription.hashCode()) * 31) + this.stackManipulation.hashCode();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            @AlwaysNull
            public Void initialize(TypeDescription typeDescription) {
                return null;
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            protected OfConstantValue(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing, TerminationHandler terminationHandler, TypeDescription.Generic generic, StackManipulation stackManipulation) {
                super(fieldLocation, assigner, typing, terminationHandler);
                this.typeDescription = generic;
                this.stackManipulation = stackManipulation;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            public StackManipulation resolve(@MaybeNull Void r3, FieldDescription fieldDescription, TypeDescription typeDescription, MethodDescription methodDescription) {
                return new StackManipulation.Compound(this.stackManipulation, this.assigner.assign(this.typeDescription, fieldDescription.getType(), this.typing));
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation andThen(Implementation implementation) {
                return new Implementation.Compound(new OfConstantValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.typeDescription, this.stackManipulation), implementation);
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation.Composable andThen(Implementation.Composable composable) {
                return new Implementation.Compound.Composable(new OfConstantValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.typeDescription, this.stackManipulation), composable);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class OfReferenceValue extends ForSetter<FieldDescription.InDefinedShape> {
            protected static final String PREFIX = "fixedFieldValue";

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f363name;
            private final Object value;

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
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
                OfReferenceValue ofReferenceValue = (OfReferenceValue) obj;
                return this.f363name.equals(ofReferenceValue.f363name) && this.value.equals(ofReferenceValue.value);
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
            public int hashCode() {
                return (((super.hashCode() * 31) + this.value.hashCode()) * 31) + this.f363name.hashCode();
            }

            protected OfReferenceValue(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing, TerminationHandler terminationHandler, Object obj, String str) {
                super(fieldLocation, assigner, typing, terminationHandler);
                this.value = obj;
                this.f363name = str;
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType.withAuxiliaryField(new FieldDescription.Token(this.f363name, 4105, TypeDescription.ForLoadedType.of(this.value.getClass()).asGenericType()), this.value);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            public FieldDescription.InDefinedShape initialize(TypeDescription typeDescription) {
                return (FieldDescription.InDefinedShape) typeDescription.getDeclaredFields().filter(ElementMatchers.named(this.f363name)).getOnly();
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            public StackManipulation resolve(@MaybeNull FieldDescription.InDefinedShape inDefinedShape, FieldDescription fieldDescription, TypeDescription typeDescription, MethodDescription methodDescription) {
                if (fieldDescription.isFinal() && methodDescription.isMethod()) {
                    throw new IllegalArgumentException("Cannot set final field " + fieldDescription + " from " + methodDescription);
                }
                return new StackManipulation.Compound(FieldAccess.forField(inDefinedShape).read(), this.assigner.assign(TypeDescription.ForLoadedType.of(this.value.getClass()).asGenericType(), fieldDescription.getType(), this.typing));
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation andThen(Implementation implementation) {
                return new Implementation.Compound(new OfReferenceValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.value, this.f363name), implementation);
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation.Composable andThen(Implementation.Composable composable) {
                return new Implementation.Compound.Composable(new OfReferenceValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.value, this.f363name), composable);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class OfFieldValue extends ForSetter<FieldLocation.Prepared> {
            private final FieldLocation target;

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
            public boolean equals(@MaybeNull Object obj) {
                if (!super.equals(obj)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.target.equals(((OfFieldValue) obj).target);
            }

            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter, net.bytebuddy.implementation.FieldAccessor
            public int hashCode() {
                return (super.hashCode() * 31) + this.target.hashCode();
            }

            @Override // net.bytebuddy.dynamic.scaffold.InstrumentedType.Prepareable
            public InstrumentedType prepare(InstrumentedType instrumentedType) {
                return instrumentedType;
            }

            protected OfFieldValue(FieldLocation fieldLocation, Assigner assigner, Assigner.Typing typing, TerminationHandler terminationHandler, FieldLocation fieldLocation2) {
                super(fieldLocation, assigner, typing, terminationHandler);
                this.target = fieldLocation2;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            public FieldLocation.Prepared initialize(TypeDescription typeDescription) {
                return this.target.prepare(typeDescription);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.implementation.FieldAccessor.ForSetter
            public StackManipulation resolve(@MaybeNull FieldLocation.Prepared prepared, FieldDescription fieldDescription, TypeDescription typeDescription, MethodDescription methodDescription) {
                FieldDescription fieldDescriptionResolve = prepared.resolve(methodDescription);
                if (!fieldDescriptionResolve.isStatic() && methodDescription.isStatic()) {
                    throw new IllegalStateException("Cannot set instance field " + fieldDescription + " from " + methodDescription);
                }
                StackManipulation[] stackManipulationArr = new StackManipulation[3];
                stackManipulationArr[0] = fieldDescriptionResolve.isStatic() ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.loadThis();
                stackManipulationArr[1] = FieldAccess.forField(fieldDescriptionResolve).read();
                stackManipulationArr[2] = this.assigner.assign(fieldDescriptionResolve.getType(), fieldDescription.getType(), this.typing);
                return new StackManipulation.Compound(stackManipulationArr);
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation andThen(Implementation implementation) {
                return new Implementation.Compound(new OfFieldValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.target), implementation);
            }

            @Override // net.bytebuddy.implementation.Implementation.Composable
            public Implementation.Composable andThen(Implementation.Composable composable) {
                return new Implementation.Compound.Composable(new OfFieldValue(this.fieldLocation, this.assigner, this.typing, TerminationHandler.NON_OPERATIONAL, this.target), composable);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
        protected class Appender implements ByteCodeAppender {
            private final FieldLocation.Prepared fieldLocation;

            @MaybeNull
            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
            private final T initialized;
            private final TypeDescription instrumentedType;

            /* JADX WARN: Code restructure failed: missing block: B:20:0x002f, code lost:
            
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
                    net.bytebuddy.description.type.TypeDescription r2 = r4.instrumentedType
                    net.bytebuddy.implementation.FieldAccessor$ForSetter$Appender r5 = (net.bytebuddy.implementation.FieldAccessor.ForSetter.Appender) r5
                    net.bytebuddy.description.type.TypeDescription r3 = r5.instrumentedType
                    boolean r2 = r2.equals(r3)
                    if (r2 != 0) goto L20
                    return r1
                L20:
                    T r2 = r4.initialized
                    T r3 = r5.initialized
                    if (r3 == 0) goto L2f
                    if (r2 == 0) goto L31
                    boolean r2 = r2.equals(r3)
                    if (r2 != 0) goto L32
                    return r1
                L2f:
                    if (r2 == 0) goto L32
                L31:
                    return r1
                L32:
                    net.bytebuddy.implementation.FieldAccessor$FieldLocation$Prepared r2 = r4.fieldLocation
                    net.bytebuddy.implementation.FieldAccessor$FieldLocation$Prepared r3 = r5.fieldLocation
                    boolean r2 = r2.equals(r3)
                    if (r2 != 0) goto L3d
                    return r1
                L3d:
                    net.bytebuddy.implementation.FieldAccessor$ForSetter r2 = net.bytebuddy.implementation.FieldAccessor.ForSetter.this
                    net.bytebuddy.implementation.FieldAccessor$ForSetter r5 = net.bytebuddy.implementation.FieldAccessor.ForSetter.this
                    boolean r5 = r2.equals(r5)
                    if (r5 != 0) goto L48
                    return r1
                L48:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.implementation.FieldAccessor.ForSetter.Appender.equals(java.lang.Object):boolean");
            }

            public int hashCode() {
                int iHashCode = ((getClass().hashCode() * 31) + this.instrumentedType.hashCode()) * 31;
                T t = this.initialized;
                if (t != null) {
                    iHashCode += t.hashCode();
                }
                return (((iHashCode * 31) + this.fieldLocation.hashCode()) * 31) + ForSetter.this.hashCode();
            }

            protected Appender(@MaybeNull TypeDescription typeDescription, T t, FieldLocation.Prepared prepared) {
                this.instrumentedType = typeDescription;
                this.initialized = t;
                this.fieldLocation = prepared;
            }

            @Override // net.bytebuddy.implementation.bytecode.ByteCodeAppender
            public ByteCodeAppender.Size apply(MethodVisitor methodVisitor, Implementation.Context context, MethodDescription methodDescription) {
                FieldDescription fieldDescriptionResolve = this.fieldLocation.resolve(methodDescription);
                if (!fieldDescriptionResolve.isStatic() && methodDescription.isStatic()) {
                    throw new IllegalStateException("Cannot set instance field " + fieldDescriptionResolve + " from " + methodDescription);
                }
                if (fieldDescriptionResolve.isFinal() && methodDescription.isMethod()) {
                    throw new IllegalStateException("Cannot set final field " + fieldDescriptionResolve + " from " + methodDescription);
                }
                StackManipulation stackManipulationResolve = ForSetter.this.resolve(this.initialized, fieldDescriptionResolve, this.instrumentedType, methodDescription);
                if (!stackManipulationResolve.isValid()) {
                    throw new IllegalStateException("Set value cannot be assigned to " + fieldDescriptionResolve);
                }
                StackManipulation[] stackManipulationArr = new StackManipulation[4];
                stackManipulationArr[0] = methodDescription.isStatic() ? StackManipulation.Trivial.INSTANCE : MethodVariableAccess.loadThis();
                stackManipulationArr[1] = stackManipulationResolve;
                stackManipulationArr[2] = FieldAccess.forField(fieldDescriptionResolve).write();
                stackManipulationArr[3] = ForSetter.this.terminationHandler.resolve(methodDescription);
                return new ByteCodeAppender.Size(new StackManipulation.Compound(stackManipulationArr).apply(methodVisitor, context).getMaximalSize(), methodDescription.getStackSize());
            }
        }
    }
}
