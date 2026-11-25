package net.bytebuddy.description.method;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import io.netty.util.internal.StringUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.ModifierReviewable;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationSource;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface ParameterDescription extends AnnotationSource, NamedElement.WithRuntimeName, NamedElement.WithOptionalName, ModifierReviewable.ForParameterDescription, ByteCodeElement.TypeDependant<InDefinedShape, Token> {
    public static final String NAME_PREFIX = "arg";

    public interface InDefinedShape extends ParameterDescription {

        public static abstract class AbstractBase extends AbstractBase implements InDefinedShape {
            @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
            public InDefinedShape asDefined() {
                return this;
            }
        }

        MethodDescription.InDefinedShape getDeclaringMethod();
    }

    public interface InGenericShape extends ParameterDescription {
        @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
        MethodDescription.InGenericShape getDeclaringMethod();
    }

    MethodDescription getDeclaringMethod();

    int getIndex();

    int getOffset();

    TypeDescription.Generic getType();

    boolean hasModifiers();

    public static abstract class AbstractBase extends ModifierReviewable.AbstractBase implements ParameterDescription {
        private transient /* synthetic */ int hashCode;
        private transient /* synthetic */ int offset;

        @Override // net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return 0;
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public /* bridge */ /* synthetic */ ByteCodeElement.Token asToken(ElementMatcher elementMatcher) {
            return asToken((ElementMatcher<? super TypeDescription>) elementMatcher);
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return ParameterDescription.NAME_PREFIX.concat(String.valueOf(getIndex()));
        }

        @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getInternalName() {
            return getName();
        }

        @Override // net.bytebuddy.description.NamedElement
        public String getActualName() {
            return isNamed() ? getName() : "";
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        @CachedReturnPlugin.Enhance(TypedValues.CycleType.S_WAVE_OFFSET)
        public int getOffset() {
            int size;
            int i = 0;
            if (this.offset == 0) {
                TypeList typeListAsErasures = getDeclaringMethod().getParameters().asTypeList().asErasures();
                if (getDeclaringMethod().isStatic()) {
                    size = StackSize.ZERO.getSize();
                } else {
                    size = StackSize.SINGLE.getSize();
                }
                while (i < getIndex()) {
                    size += ((TypeDescription) typeListAsErasures.get(i)).getStackSize().getSize();
                    i++;
                }
                i = size;
            }
            if (i == 0) {
                return this.offset;
            }
            this.offset = i;
            return i;
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public Token asToken(ElementMatcher<? super TypeDescription> elementMatcher) {
            return new Token((TypeDescription.Generic) getType().accept(new TypeDescription.Generic.Visitor.Substitutor.ForDetachment(elementMatcher)), getDeclaredAnnotations(), isNamed() ? getName() : Token.NO_NAME, hasModifiers() ? Integer.valueOf(getModifiers()) : Token.NO_MODIFIERS);
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = this.hashCode != 0 ? 0 : getDeclaringMethod().hashCode() ^ getIndex();
            if (iHashCode == 0) {
                return this.hashCode;
            }
            this.hashCode = iHashCode;
            return iHashCode;
        }

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ParameterDescription)) {
                return false;
            }
            ParameterDescription parameterDescription = (ParameterDescription) obj;
            return getDeclaringMethod().equals(parameterDescription.getDeclaringMethod()) && getIndex() == parameterDescription.getIndex();
        }

        public String toString() {
            String name2;
            StringBuilder sb = new StringBuilder(Modifier.toString(getModifiers()));
            if (getModifiers() != 0) {
                sb.append(StringUtil.SPACE);
            }
            if (isVarArgs()) {
                name2 = getType().asErasure().getName().replaceFirst("\\[]$", "...");
            } else {
                name2 = getType().asErasure().getName();
            }
            sb.append(name2);
            return sb.append(StringUtil.SPACE).append(getName()).toString();
        }
    }

    public static abstract class ForLoadedParameter<T extends AccessibleObject> extends InDefinedShape.AbstractBase {
        private static final boolean ACCESS_CONTROLLER;
        private static final Parameter PARAMETER;
        protected final T executable;
        protected final int index;
        protected final ParameterAnnotationSource parameterAnnotationSource;

        @JavaDispatcher.Proxied("java.lang.reflect.Parameter")
        protected interface Parameter {
            @JavaDispatcher.Proxied("getModifiers")
            int getModifiers(Object obj);

            @JavaDispatcher.Proxied("getName")
            String getName(Object obj);

            @JavaDispatcher.Proxied("isNamePresent")
            boolean isNamePresent(Object obj);
        }

        static {
            boolean z = false;
            try {
                Class.forName("java.security.AccessController", false, null);
                ACCESS_CONTROLLER = Boolean.parseBoolean(System.getProperty("net.bytebuddy.securitymanager", "true"));
            } catch (ClassNotFoundException unused) {
                ACCESS_CONTROLLER = z;
            } catch (SecurityException unused2) {
                z = true;
                ACCESS_CONTROLLER = z;
            }
            PARAMETER = (Parameter) doPrivileged(JavaDispatcher.of(Parameter.class));
        }

        protected ForLoadedParameter(T t, int i, ParameterAnnotationSource parameterAnnotationSource) {
            this.executable = t;
            this.index = i;
            this.parameterAnnotationSource = parameterAnnotationSource;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return PARAMETER.getName(ParameterList.ForLoadedExecutable.EXECUTABLE.getParameters(this.executable)[this.index]);
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public int getIndex() {
            return this.index;
        }

        @Override // net.bytebuddy.description.NamedElement.WithOptionalName
        public boolean isNamed() {
            return PARAMETER.isNamePresent(ParameterList.ForLoadedExecutable.EXECUTABLE.getParameters(this.executable)[this.index]);
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return PARAMETER.getModifiers(ParameterList.ForLoadedExecutable.EXECUTABLE.getParameters(this.executable)[this.index]);
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public boolean hasModifiers() {
            return isNamed() || getModifiers() != 0;
        }

        public interface ParameterAnnotationSource {
            Annotation[][] getParameterAnnotations();

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForLoadedConstructor implements ParameterAnnotationSource {
                private final Constructor<?> constructor;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.constructor.equals(((ForLoadedConstructor) obj).constructor);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.constructor.hashCode();
                }

                public ForLoadedConstructor(Constructor<?> constructor) {
                    this.constructor = constructor;
                }

                @Override // net.bytebuddy.description.method.ParameterDescription.ForLoadedParameter.ParameterAnnotationSource
                public Annotation[][] getParameterAnnotations() {
                    return this.constructor.getParameterAnnotations();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForLoadedMethod implements ParameterAnnotationSource {
                private final Method method;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.method.equals(((ForLoadedMethod) obj).method);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.method.hashCode();
                }

                public ForLoadedMethod(Method method) {
                    this.method = method;
                }

                @Override // net.bytebuddy.description.method.ParameterDescription.ForLoadedParameter.ParameterAnnotationSource
                public Annotation[][] getParameterAnnotations() {
                    return this.method.getParameterAnnotations();
                }
            }
        }

        protected static class OfMethod extends ForLoadedParameter<Method> {
            protected OfMethod(Method method, int i, ParameterAnnotationSource parameterAnnotationSource) {
                super(method, i, parameterAnnotationSource);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
            public MethodDescription.InDefinedShape getDeclaringMethod() {
                return new MethodDescription.ForLoadedMethod((Method) this.executable);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription
            public TypeDescription.Generic getType() {
                if (TypeDescription.AbstractBase.RAW_TYPES) {
                    return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(((Method) this.executable).getParameterTypes()[this.index]);
                }
                return new TypeDescription.Generic.LazyProjection.OfMethodParameter((Method) this.executable, this.index, ((Method) this.executable).getParameterTypes());
            }

            @Override // net.bytebuddy.description.annotation.AnnotationSource
            public AnnotationList getDeclaredAnnotations() {
                return new AnnotationList.ForLoadedAnnotations(this.parameterAnnotationSource.getParameterAnnotations()[this.index]);
            }
        }

        protected static class OfConstructor extends ForLoadedParameter<Constructor<?>> {
            protected OfConstructor(Constructor<?> constructor, int i, ParameterAnnotationSource parameterAnnotationSource) {
                super(constructor, i, parameterAnnotationSource);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
            public MethodDescription.InDefinedShape getDeclaringMethod() {
                return new MethodDescription.ForLoadedConstructor((Constructor) this.executable);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription
            public TypeDescription.Generic getType() {
                if (TypeDescription.AbstractBase.RAW_TYPES) {
                    return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(((Constructor) this.executable).getParameterTypes()[this.index]);
                }
                return new TypeDescription.Generic.LazyProjection.OfConstructorParameter((Constructor) this.executable, this.index, ((Constructor) this.executable).getParameterTypes());
            }

            @Override // net.bytebuddy.description.annotation.AnnotationSource
            public AnnotationList getDeclaredAnnotations() {
                Annotation[][] parameterAnnotations = this.parameterAnnotationSource.getParameterAnnotations();
                MethodDescription.InDefinedShape declaringMethod = getDeclaringMethod();
                if (parameterAnnotations.length != declaringMethod.getParameters().size() && declaringMethod.getDeclaringType().isInnerClass()) {
                    return this.index == 0 ? new AnnotationList.Empty() : new AnnotationList.ForLoadedAnnotations(parameterAnnotations[this.index - 1]);
                }
                return new AnnotationList.ForLoadedAnnotations(parameterAnnotations[this.index]);
            }
        }

        protected static class OfLegacyVmMethod extends InDefinedShape.AbstractBase {
            private final int index;
            private final Method method;
            private final ParameterAnnotationSource parameterAnnotationSource;
            private final Class<?>[] parameterType;

            @Override // net.bytebuddy.description.method.ParameterDescription
            public boolean hasModifiers() {
                return false;
            }

            @Override // net.bytebuddy.description.NamedElement.WithOptionalName
            public boolean isNamed() {
                return false;
            }

            protected OfLegacyVmMethod(Method method, int i, Class<?>[] clsArr, ParameterAnnotationSource parameterAnnotationSource) {
                this.method = method;
                this.index = i;
                this.parameterType = clsArr;
                this.parameterAnnotationSource = parameterAnnotationSource;
            }

            @Override // net.bytebuddy.description.method.ParameterDescription
            public TypeDescription.Generic getType() {
                if (TypeDescription.AbstractBase.RAW_TYPES) {
                    return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(this.parameterType[this.index]);
                }
                return new TypeDescription.Generic.LazyProjection.OfMethodParameter(this.method, this.index, this.parameterType);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
            public MethodDescription.InDefinedShape getDeclaringMethod() {
                return new MethodDescription.ForLoadedMethod(this.method);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription
            public int getIndex() {
                return this.index;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationSource
            public AnnotationList getDeclaredAnnotations() {
                return new AnnotationList.ForLoadedAnnotations(this.parameterAnnotationSource.getParameterAnnotations()[this.index]);
            }
        }

        protected static class OfLegacyVmConstructor extends InDefinedShape.AbstractBase {
            private final Constructor<?> constructor;
            private final int index;
            private final ParameterAnnotationSource parameterAnnotationSource;
            private final Class<?>[] parameterType;

            @Override // net.bytebuddy.description.method.ParameterDescription
            public boolean hasModifiers() {
                return false;
            }

            @Override // net.bytebuddy.description.NamedElement.WithOptionalName
            public boolean isNamed() {
                return false;
            }

            protected OfLegacyVmConstructor(Constructor<?> constructor, int i, Class<?>[] clsArr, ParameterAnnotationSource parameterAnnotationSource) {
                this.constructor = constructor;
                this.index = i;
                this.parameterType = clsArr;
                this.parameterAnnotationSource = parameterAnnotationSource;
            }

            @Override // net.bytebuddy.description.method.ParameterDescription
            public TypeDescription.Generic getType() {
                if (TypeDescription.AbstractBase.RAW_TYPES) {
                    return TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(this.parameterType[this.index]);
                }
                return new TypeDescription.Generic.LazyProjection.OfConstructorParameter(this.constructor, this.index, this.parameterType);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
            public MethodDescription.InDefinedShape getDeclaringMethod() {
                return new MethodDescription.ForLoadedConstructor(this.constructor);
            }

            @Override // net.bytebuddy.description.method.ParameterDescription
            public int getIndex() {
                return this.index;
            }

            @Override // net.bytebuddy.description.annotation.AnnotationSource
            public AnnotationList getDeclaredAnnotations() {
                MethodDescription.InDefinedShape declaringMethod = getDeclaringMethod();
                Annotation[][] parameterAnnotations = this.parameterAnnotationSource.getParameterAnnotations();
                if (parameterAnnotations.length != declaringMethod.getParameters().size() && declaringMethod.getDeclaringType().isInnerClass()) {
                    return this.index == 0 ? new AnnotationList.Empty() : new AnnotationList.ForLoadedAnnotations(parameterAnnotations[this.index - 1]);
                }
                return new AnnotationList.ForLoadedAnnotations(parameterAnnotations[this.index]);
            }
        }
    }

    public static class Latent extends InDefinedShape.AbstractBase {
        private final List<? extends AnnotationDescription> declaredAnnotations;
        private final MethodDescription.InDefinedShape declaringMethod;
        private final int index;

        @MaybeNull
        private final Integer modifiers;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        @MaybeNull
        private final String f352name;
        private final int offset;
        private final TypeDescription.Generic parameterType;

        public Latent(MethodDescription.InDefinedShape inDefinedShape, Token token, int i, int i2) {
            this(inDefinedShape, token.getType(), token.getAnnotations(), token.getName(), token.getModifiers(), i, i2);
        }

        public Latent(MethodDescription.InDefinedShape inDefinedShape, TypeDescription.Generic generic, int i, int i2) {
            this(inDefinedShape, generic, Collections.emptyList(), Token.NO_NAME, Token.NO_MODIFIERS, i, i2);
        }

        public Latent(MethodDescription.InDefinedShape inDefinedShape, TypeDescription.Generic generic, List<? extends AnnotationDescription> list, @MaybeNull String str, @MaybeNull Integer num, int i, int i2) {
            this.declaringMethod = inDefinedShape;
            this.parameterType = generic;
            this.declaredAnnotations = list;
            this.f352name = str;
            this.modifiers = num;
            this.index = i;
            this.offset = i2;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public TypeDescription.Generic getType() {
            return (TypeDescription.Generic) this.parameterType.accept(TypeDescription.Generic.Visitor.Substitutor.ForAttachment.of(this));
        }

        @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
        public MethodDescription.InDefinedShape getDeclaringMethod() {
            return this.declaringMethod;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public int getIndex() {
            return this.index;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.method.ParameterDescription
        public int getOffset() {
            return this.offset;
        }

        @Override // net.bytebuddy.description.NamedElement.WithOptionalName
        public boolean isNamed() {
            return this.f352name != null;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public boolean hasModifiers() {
            return this.modifiers != null;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            String str = this.f352name;
            return str == null ? super.getName() : str;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            Integer num = this.modifiers;
            if (num == null) {
                return super.getModifiers();
            }
            return num.intValue();
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return new AnnotationList.Explicit(this.declaredAnnotations);
        }
    }

    public static class TypeSubstituting extends AbstractBase implements InGenericShape {
        private final MethodDescription.InGenericShape declaringMethod;
        private final ParameterDescription parameterDescription;
        private final TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor;

        public TypeSubstituting(MethodDescription.InGenericShape inGenericShape, ParameterDescription parameterDescription, TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor) {
            this.declaringMethod = inGenericShape;
            this.parameterDescription = parameterDescription;
            this.visitor = visitor;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public TypeDescription.Generic getType() {
            return (TypeDescription.Generic) this.parameterDescription.getType().accept(this.visitor);
        }

        @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
        public MethodDescription.InGenericShape getDeclaringMethod() {
            return this.declaringMethod;
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public int getIndex() {
            return this.parameterDescription.getIndex();
        }

        @Override // net.bytebuddy.description.NamedElement.WithOptionalName
        public boolean isNamed() {
            return this.parameterDescription.isNamed();
        }

        @Override // net.bytebuddy.description.method.ParameterDescription
        public boolean hasModifiers() {
            return this.parameterDescription.hasModifiers();
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.method.ParameterDescription
        public int getOffset() {
            return this.parameterDescription.getOffset();
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithRuntimeName
        public String getName() {
            return this.parameterDescription.getName();
        }

        @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.ModifierReviewable
        public int getModifiers() {
            return this.parameterDescription.getModifiers();
        }

        @Override // net.bytebuddy.description.annotation.AnnotationSource
        public AnnotationList getDeclaredAnnotations() {
            return this.parameterDescription.getDeclaredAnnotations();
        }

        @Override // net.bytebuddy.description.ByteCodeElement.TypeDependant
        public InDefinedShape asDefined() {
            return this.parameterDescription.asDefined();
        }
    }

    public static class Token implements ByteCodeElement.Token<Token> {

        @AlwaysNull
        public static final Integer NO_MODIFIERS = null;

        @AlwaysNull
        public static final String NO_NAME = null;
        private final List<? extends AnnotationDescription> annotations;
        private transient /* synthetic */ int hashCode;

        @MaybeNull
        private final Integer modifiers;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        @MaybeNull
        private final String f353name;
        private final TypeDescription.Generic type;

        @Override // net.bytebuddy.description.ByteCodeElement.Token
        public /* bridge */ /* synthetic */ ByteCodeElement.Token accept(TypeDescription.Generic.Visitor visitor) {
            return accept((TypeDescription.Generic.Visitor<? extends TypeDescription.Generic>) visitor);
        }

        public Token(TypeDescription.Generic generic) {
            this(generic, Collections.emptyList());
        }

        public Token(TypeDescription.Generic generic, List<? extends AnnotationDescription> list) {
            this(generic, list, NO_NAME, NO_MODIFIERS);
        }

        public Token(TypeDescription.Generic generic, @MaybeNull String str, @MaybeNull Integer num) {
            this(generic, Collections.emptyList(), str, num);
        }

        public Token(TypeDescription.Generic generic, List<? extends AnnotationDescription> list, @MaybeNull String str, @MaybeNull Integer num) {
            this.type = generic;
            this.annotations = list;
            this.f353name = str;
            this.modifiers = num;
        }

        public TypeDescription.Generic getType() {
            return this.type;
        }

        public AnnotationList getAnnotations() {
            return new AnnotationList.Explicit(this.annotations);
        }

        @MaybeNull
        public String getName() {
            return this.f353name;
        }

        @MaybeNull
        public Integer getModifiers() {
            return this.modifiers;
        }

        @Override // net.bytebuddy.description.ByteCodeElement.Token
        public Token accept(TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor) {
            return new Token((TypeDescription.Generic) this.type.accept(visitor), this.annotations, this.f353name, this.modifiers);
        }

        @CachedReturnPlugin.Enhance("hashCode")
        public int hashCode() {
            int iHashCode = 0;
            if (this.hashCode == 0) {
                int iHashCode2 = ((this.type.hashCode() * 31) + this.annotations.hashCode()) * 31;
                String str = this.f353name;
                int iHashCode3 = (iHashCode2 + (str != null ? str.hashCode() : 0)) * 31;
                Integer num = this.modifiers;
                iHashCode = (num != null ? num.hashCode() : 0) + iHashCode3;
            }
            if (iHashCode == 0) {
                return this.hashCode;
            }
            this.hashCode = iHashCode;
            return iHashCode;
        }

        public boolean equals(@MaybeNull Object obj) {
            String str;
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            if (this.type.equals(token.type) && this.annotations.equals(token.annotations) && ((str = this.f353name) == null ? token.f353name == null : str.equals(token.f353name))) {
                Integer num = this.modifiers;
                if (num != null) {
                    if (num.equals(token.modifiers)) {
                        return true;
                    }
                } else if (token.modifiers == null) {
                    return true;
                }
            }
            return false;
        }

        public String toString() {
            return "ParameterDescription.Token{type=" + this.type + ", annotations=" + this.annotations + ", name='" + this.f353name + "', modifiers=" + this.modifiers + '}';
        }

        public static class TypeList extends AbstractList<Token> {
            private final List<? extends TypeDefinition> typeDescriptions;

            public TypeList(List<? extends TypeDefinition> list) {
                this.typeDescriptions = list;
            }

            @Override // java.util.AbstractList, java.util.List
            public Token get(int i) {
                return new Token(this.typeDescriptions.get(i).asGenericType());
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return this.typeDescriptions.size();
            }
        }
    }
}
