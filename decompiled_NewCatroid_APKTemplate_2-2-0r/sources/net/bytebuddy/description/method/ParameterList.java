package net.bytebuddy.description.method;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.description.ByteCodeElement;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.FilterableList;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;

/* JADX INFO: loaded from: classes12.dex */
public interface ParameterList<T extends ParameterDescription> extends FilterableList<T, ParameterList<T>> {
    ParameterList<ParameterDescription.InDefinedShape> asDefined();

    ByteCodeElement.Token.TokenList<ParameterDescription.Token> asTokenList(ElementMatcher<? super TypeDescription> elementMatcher);

    TypeList.Generic asTypeList();

    boolean hasExplicitMetaData();

    public static abstract class AbstractBase<S extends ParameterDescription> extends FilterableList.AbstractBase<S, ParameterList<S>> implements ParameterList<S> {
        @Override // net.bytebuddy.description.method.ParameterList
        public boolean hasExplicitMetaData() {
            for (S s : this) {
                if (!s.isNamed() || !s.hasModifiers()) {
                    return false;
                }
            }
            return true;
        }

        @Override // net.bytebuddy.description.method.ParameterList
        public ByteCodeElement.Token.TokenList<ParameterDescription.Token> asTokenList(ElementMatcher<? super TypeDescription> elementMatcher) {
            ArrayList arrayList = new ArrayList(size());
            Iterator it = iterator();
            while (it.hasNext()) {
                arrayList.add(((ParameterDescription) it.next()).asToken(elementMatcher));
            }
            return new ByteCodeElement.Token.TokenList<>(arrayList);
        }

        @Override // net.bytebuddy.description.method.ParameterList
        public TypeList.Generic asTypeList() {
            ArrayList arrayList = new ArrayList(size());
            Iterator it = iterator();
            while (it.hasNext()) {
                arrayList.add(((ParameterDescription) it.next()).getType());
            }
            return new TypeList.Generic.Explicit(arrayList);
        }

        @Override // net.bytebuddy.description.method.ParameterList
        public ParameterList<ParameterDescription.InDefinedShape> asDefined() {
            ArrayList arrayList = new ArrayList(size());
            Iterator it = iterator();
            while (it.hasNext()) {
                arrayList.add(((ParameterDescription) it.next()).asDefined());
            }
            return new Explicit(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.bytebuddy.matcher.FilterableList.AbstractBase
        public ParameterList<S> wrap(List<S> list) {
            return new Explicit(list);
        }
    }

    public static abstract class ForLoadedExecutable<T> extends AbstractBase<ParameterDescription.InDefinedShape> {
        private static final boolean ACCESS_CONTROLLER;
        protected static final Executable EXECUTABLE;
        protected final T executable;
        protected final ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource;

        @JavaDispatcher.Proxied("java.lang.reflect.Executable")
        protected interface Executable {
            @JavaDispatcher.Proxied("getParameterCount")
            int getParameterCount(Object obj);

            @JavaDispatcher.Proxied("getParameters")
            Object[] getParameters(Object obj);

            @JavaDispatcher.Instance
            @JavaDispatcher.Proxied("isInstance")
            boolean isInstance(Object obj);
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
            EXECUTABLE = (Executable) doPrivileged(JavaDispatcher.of(Executable.class));
        }

        protected ForLoadedExecutable(T t, ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource) {
            this.executable = t;
            this.parameterAnnotationSource = parameterAnnotationSource;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        public static ParameterList<ParameterDescription.InDefinedShape> of(Constructor<?> constructor) {
            return of(constructor, (ParameterDescription.ForLoadedParameter.ParameterAnnotationSource) new ParameterDescription.ForLoadedParameter.ParameterAnnotationSource.ForLoadedConstructor(constructor));
        }

        public static ParameterList<ParameterDescription.InDefinedShape> of(Constructor<?> constructor, ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource) {
            return EXECUTABLE.isInstance(constructor) ? new OfConstructor(constructor, parameterAnnotationSource) : new OfLegacyVmConstructor(constructor, parameterAnnotationSource);
        }

        public static ParameterList<ParameterDescription.InDefinedShape> of(Method method) {
            return of(method, (ParameterDescription.ForLoadedParameter.ParameterAnnotationSource) new ParameterDescription.ForLoadedParameter.ParameterAnnotationSource.ForLoadedMethod(method));
        }

        public static ParameterList<ParameterDescription.InDefinedShape> of(Method method, ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource) {
            return EXECUTABLE.isInstance(method) ? new OfMethod(method, parameterAnnotationSource) : new OfLegacyVmMethod(method, parameterAnnotationSource);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return EXECUTABLE.getParameterCount(this.executable);
        }

        protected static class OfConstructor extends ForLoadedExecutable<Constructor<?>> {
            protected OfConstructor(Constructor<?> constructor, ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource) {
                super(constructor, parameterAnnotationSource);
            }

            @Override // java.util.AbstractList, java.util.List
            public ParameterDescription.InDefinedShape get(int i) {
                return new ParameterDescription.ForLoadedParameter.OfConstructor((Constructor) this.executable, i, this.parameterAnnotationSource);
            }
        }

        protected static class OfMethod extends ForLoadedExecutable<Method> {
            protected OfMethod(Method method, ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource) {
                super(method, parameterAnnotationSource);
            }

            @Override // java.util.AbstractList, java.util.List
            public ParameterDescription.InDefinedShape get(int i) {
                return new ParameterDescription.ForLoadedParameter.OfMethod((Method) this.executable, i, this.parameterAnnotationSource);
            }
        }

        protected static class OfLegacyVmConstructor extends AbstractBase<ParameterDescription.InDefinedShape> {
            private final Constructor<?> constructor;
            private final ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource;
            private final Class<?>[] parameterType;

            protected OfLegacyVmConstructor(Constructor<?> constructor, ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource) {
                this.constructor = constructor;
                this.parameterType = constructor.getParameterTypes();
                this.parameterAnnotationSource = parameterAnnotationSource;
            }

            @Override // java.util.AbstractList, java.util.List
            public ParameterDescription.InDefinedShape get(int i) {
                return new ParameterDescription.ForLoadedParameter.OfLegacyVmConstructor(this.constructor, i, this.parameterType, this.parameterAnnotationSource);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return this.parameterType.length;
            }
        }

        protected static class OfLegacyVmMethod extends AbstractBase<ParameterDescription.InDefinedShape> {
            private final Method method;
            private final ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource;
            private final Class<?>[] parameterType;

            protected OfLegacyVmMethod(Method method, ParameterDescription.ForLoadedParameter.ParameterAnnotationSource parameterAnnotationSource) {
                this.method = method;
                this.parameterType = method.getParameterTypes();
                this.parameterAnnotationSource = parameterAnnotationSource;
            }

            @Override // java.util.AbstractList, java.util.List
            public ParameterDescription.InDefinedShape get(int i) {
                return new ParameterDescription.ForLoadedParameter.OfLegacyVmMethod(this.method, i, this.parameterType, this.parameterAnnotationSource);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return this.parameterType.length;
            }
        }
    }

    public static class Explicit<S extends ParameterDescription> extends AbstractBase<S> {
        private final List<? extends S> parameterDescriptions;

        public Explicit(S... sArr) {
            this(Arrays.asList(sArr));
        }

        public Explicit(List<? extends S> list) {
            this.parameterDescriptions = list;
        }

        @Override // java.util.AbstractList, java.util.List
        public S get(int i) {
            return this.parameterDescriptions.get(i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.parameterDescriptions.size();
        }

        public static class ForTypes extends AbstractBase<ParameterDescription.InDefinedShape> {
            private final MethodDescription.InDefinedShape methodDescription;
            private final List<? extends TypeDefinition> typeDefinitions;

            public ForTypes(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition... typeDefinitionArr) {
                this(inDefinedShape, (List<? extends TypeDefinition>) Arrays.asList(typeDefinitionArr));
            }

            public ForTypes(MethodDescription.InDefinedShape inDefinedShape, List<? extends TypeDefinition> list) {
                this.methodDescription = inDefinedShape;
                this.typeDefinitions = list;
            }

            @Override // java.util.AbstractList, java.util.List
            public ParameterDescription.InDefinedShape get(int i) {
                int size = !this.methodDescription.isStatic() ? 1 : 0;
                for (int i2 = 0; i2 < i; i2++) {
                    size += this.typeDefinitions.get(i2).getStackSize().getSize();
                }
                return new ParameterDescription.Latent(this.methodDescription, this.typeDefinitions.get(i).asGenericType(), i, size);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return this.typeDefinitions.size();
            }
        }
    }

    public static class ForTokens extends AbstractBase<ParameterDescription.InDefinedShape> {
        private final MethodDescription.InDefinedShape declaringMethod;
        private final List<? extends ParameterDescription.Token> tokens;

        public ForTokens(MethodDescription.InDefinedShape inDefinedShape, List<? extends ParameterDescription.Token> list) {
            this.declaringMethod = inDefinedShape;
            this.tokens = list;
        }

        @Override // java.util.AbstractList, java.util.List
        public ParameterDescription.InDefinedShape get(int i) {
            int size = !this.declaringMethod.isStatic() ? 1 : 0;
            Iterator<? extends ParameterDescription.Token> it = this.tokens.subList(0, i).iterator();
            while (it.hasNext()) {
                size += it.next().getType().getStackSize().getSize();
            }
            return new ParameterDescription.Latent(this.declaringMethod, this.tokens.get(i), i, size);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.tokens.size();
        }
    }

    public static class TypeSubstituting extends AbstractBase<ParameterDescription.InGenericShape> {
        private final MethodDescription.InGenericShape declaringMethod;
        private final List<? extends ParameterDescription> parameterDescriptions;
        private final TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor;

        public TypeSubstituting(MethodDescription.InGenericShape inGenericShape, List<? extends ParameterDescription> list, TypeDescription.Generic.Visitor<? extends TypeDescription.Generic> visitor) {
            this.declaringMethod = inGenericShape;
            this.parameterDescriptions = list;
            this.visitor = visitor;
        }

        @Override // java.util.AbstractList, java.util.List
        public ParameterDescription.InGenericShape get(int i) {
            return new ParameterDescription.TypeSubstituting(this.declaringMethod, this.parameterDescriptions.get(i), this.visitor);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.parameterDescriptions.size();
        }
    }

    public static class Empty<S extends ParameterDescription> extends FilterableList.Empty<S, ParameterList<S>> implements ParameterList<S> {
        @Override // net.bytebuddy.description.method.ParameterList
        public ParameterList<ParameterDescription.InDefinedShape> asDefined() {
            return this;
        }

        @Override // net.bytebuddy.description.method.ParameterList
        public boolean hasExplicitMetaData() {
            return true;
        }

        @Override // net.bytebuddy.description.method.ParameterList
        public TypeList.Generic asTypeList() {
            return new TypeList.Generic.Empty();
        }

        @Override // net.bytebuddy.description.method.ParameterList
        public ByteCodeElement.Token.TokenList<ParameterDescription.Token> asTokenList(ElementMatcher<? super TypeDescription> elementMatcher) {
            return new ByteCodeElement.Token.TokenList<>(new ParameterDescription.Token[0]);
        }
    }
}
