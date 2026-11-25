package net.bytebuddy.pool;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nonnull;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.TypeVariableSource;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationValue;
import net.bytebuddy.description.enumeration.EnumerationDescription;
import net.bytebuddy.description.field.FieldDescription;
import net.bytebuddy.description.field.FieldList;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.method.MethodList;
import net.bytebuddy.description.method.ParameterDescription;
import net.bytebuddy.description.method.ParameterList;
import net.bytebuddy.description.type.PackageDescription;
import net.bytebuddy.description.type.RecordComponentDescription;
import net.bytebuddy.description.type.RecordComponentList;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeList;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bytecode.StackSize;
import net.bytebuddy.jar.asm.AnnotationVisitor;
import net.bytebuddy.jar.asm.ClassReader;
import net.bytebuddy.jar.asm.ClassVisitor;
import net.bytebuddy.jar.asm.FieldVisitor;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.RecordComponentVisitor;
import net.bytebuddy.jar.asm.Type;
import net.bytebuddy.jar.asm.TypePath;
import net.bytebuddy.jar.asm.TypeReference;
import net.bytebuddy.jar.asm.signature.SignatureReader;
import net.bytebuddy.jar.asm.signature.SignatureVisitor;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaType;
import net.bytebuddy.utility.OpenedClassReader;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.nullability.UnknownNull;

/* JADX INFO: loaded from: classes12.dex */
public interface TypePool {
    void clear();

    Resolution describe(String str);

    public interface Resolution {
        boolean isResolved();

        TypeDescription resolve();

        @HashCodeAndEqualsPlugin.Enhance
        public static class Simple implements Resolution {
            private final TypeDescription typeDescription;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.typeDescription.equals(((Simple) obj).typeDescription);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.typeDescription.hashCode();
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public boolean isResolved() {
                return true;
            }

            public Simple(TypeDescription typeDescription) {
                this.typeDescription = typeDescription;
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public TypeDescription resolve() {
                return this.typeDescription;
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class Illegal implements Resolution {

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f402name;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.f402name.equals(((Illegal) obj).f402name);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.f402name.hashCode();
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public boolean isResolved() {
                return false;
            }

            public Illegal(String str) {
                this.f402name = str;
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public TypeDescription resolve() {
                throw new NoSuchTypeException(this.f402name);
            }
        }

        public static class NoSuchTypeException extends IllegalStateException {
            private static final long serialVersionUID = 1;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f403name;

            public NoSuchTypeException(String str) {
                super("Cannot resolve type description for " + str);
                this.f403name = str;
            }

            public String getName() {
                return this.f403name;
            }
        }
    }

    public interface CacheProvider {

        @MaybeNull
        public static final Resolution UNRESOLVED = null;

        void clear();

        @MaybeNull
        Resolution find(String str);

        Resolution register(String str, Resolution resolution);

        public enum NoOp implements CacheProvider {
            INSTANCE;

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            public void clear() {
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            public Resolution register(String str, Resolution resolution) {
                return resolution;
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            @MaybeNull
            public Resolution find(String str) {
                return UNRESOLVED;
            }
        }

        public static class Simple implements CacheProvider {
            private final ConcurrentMap<String, Resolution> storage;

            public Simple() {
                this(new ConcurrentHashMap());
            }

            public Simple(ConcurrentMap<String, Resolution> concurrentMap) {
                this.storage = concurrentMap;
            }

            public static CacheProvider withObjectType() {
                Simple simple = new Simple();
                simple.register(Object.class.getName(), new Resolution.Simple(TypeDescription.ForLoadedType.of(Object.class)));
                return simple;
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            @MaybeNull
            public Resolution find(String str) {
                return this.storage.get(str);
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            public Resolution register(String str, Resolution resolution) {
                Resolution resolutionPutIfAbsent = this.storage.putIfAbsent(str, resolution);
                return resolutionPutIfAbsent == null ? resolution : resolutionPutIfAbsent;
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            public void clear() {
                this.storage.clear();
            }

            public ConcurrentMap<String, Resolution> getStorage() {
                return this.storage;
            }

            public static class UsingSoftReference implements CacheProvider {
                private final AtomicReference<SoftReference<Simple>> delegate = new AtomicReference<>(new SoftReference(new Simple()));

                @Override // net.bytebuddy.pool.TypePool.CacheProvider
                @MaybeNull
                public Resolution find(String str) {
                    Simple simple = this.delegate.get().get();
                    if (simple == null) {
                        return UNRESOLVED;
                    }
                    return simple.find(str);
                }

                @Override // net.bytebuddy.pool.TypePool.CacheProvider
                public Resolution register(String str, Resolution resolution) {
                    SoftReference<Simple> softReference = this.delegate.get();
                    Simple simple = softReference.get();
                    if (simple == null) {
                        simple = new Simple();
                        while (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.delegate, softReference, new SoftReference(simple))) {
                            softReference = this.delegate.get();
                            Simple simple2 = softReference.get();
                            if (simple2 != null) {
                                simple = simple2;
                                break;
                            }
                        }
                    }
                    return simple.register(str, resolution);
                }

                @Override // net.bytebuddy.pool.TypePool.CacheProvider
                public void clear() {
                    Simple simple = this.delegate.get().get();
                    if (simple != null) {
                        simple.clear();
                    }
                }
            }
        }

        public static class Discriminating implements CacheProvider {
            private final CacheProvider matched;
            private final ElementMatcher<String> matcher;
            private final CacheProvider unmatched;

            public Discriminating(ElementMatcher<String> elementMatcher, CacheProvider cacheProvider, CacheProvider cacheProvider2) {
                this.matcher = elementMatcher;
                this.matched = cacheProvider;
                this.unmatched = cacheProvider2;
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            @MaybeNull
            public Resolution find(String str) {
                return (this.matcher.matches(str) ? this.matched : this.unmatched).find(str);
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            public Resolution register(String str, Resolution resolution) {
                return (this.matcher.matches(str) ? this.matched : this.unmatched).register(str, resolution);
            }

            @Override // net.bytebuddy.pool.TypePool.CacheProvider
            public void clear() {
                try {
                    this.unmatched.clear();
                } finally {
                    this.matched.clear();
                }
            }
        }
    }

    public enum Empty implements TypePool {
        INSTANCE;

        @Override // net.bytebuddy.pool.TypePool
        public void clear() {
        }

        @Override // net.bytebuddy.pool.TypePool
        public Resolution describe(String str) {
            return new Resolution.Illegal(str);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static abstract class AbstractBase implements TypePool {
        private static final String ARRAY_SYMBOL = "[";
        protected static final Map<String, String> PRIMITIVE_DESCRIPTORS;
        protected static final Map<String, TypeDescription> PRIMITIVE_TYPES;
        protected final CacheProvider cacheProvider;

        protected interface ComponentTypeReference {

            @MaybeNull
            public static final String NO_ARRAY = null;

            @MaybeNull
            String resolve();
        }

        protected abstract Resolution doDescribe(String str);

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.cacheProvider.equals(((AbstractBase) obj).cacheProvider);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.cacheProvider.hashCode();
        }

        static {
            HashMap map = new HashMap();
            HashMap map2 = new HashMap();
            Class[] clsArr = {Boolean.TYPE, Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
            for (int i = 0; i < 9; i++) {
                Class cls = clsArr[i];
                map.put(cls.getName(), TypeDescription.ForLoadedType.of(cls));
                map2.put(Type.getDescriptor(cls), cls.getName());
            }
            PRIMITIVE_TYPES = Collections.unmodifiableMap(map);
            PRIMITIVE_DESCRIPTORS = Collections.unmodifiableMap(map2);
        }

        protected AbstractBase(CacheProvider cacheProvider) {
            this.cacheProvider = cacheProvider;
        }

        @Override // net.bytebuddy.pool.TypePool
        public Resolution describe(String str) {
            if (str.contains("/")) {
                throw new IllegalArgumentException(str + " contains the illegal character '/'");
            }
            int i = 0;
            while (str.startsWith(ARRAY_SYMBOL)) {
                i++;
                str = str.substring(1);
            }
            if (i > 0) {
                String str2 = PRIMITIVE_DESCRIPTORS.get(str);
                str = str2 == null ? str.substring(1, str.length() - 1) : str2;
            }
            TypeDescription typeDescription = PRIMITIVE_TYPES.get(str);
            Resolution resolutionFind = typeDescription == null ? this.cacheProvider.find(str) : new Resolution.Simple(typeDescription);
            if (resolutionFind == null) {
                resolutionFind = doCache(str, doDescribe(str));
            }
            return ArrayTypeResolution.of(resolutionFind, i);
        }

        protected Resolution doCache(String str, Resolution resolution) {
            return this.cacheProvider.register(str, resolution);
        }

        @Override // net.bytebuddy.pool.TypePool
        public void clear() {
            this.cacheProvider.clear();
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static abstract class Hierarchical extends AbstractBase {
            private final TypePool parent;

            @Override // net.bytebuddy.pool.TypePool.AbstractBase
            public boolean equals(@MaybeNull Object obj) {
                if (!super.equals(obj)) {
                    return false;
                }
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.parent.equals(((Hierarchical) obj).parent);
            }

            @Override // net.bytebuddy.pool.TypePool.AbstractBase
            public int hashCode() {
                return (super.hashCode() * 31) + this.parent.hashCode();
            }

            protected Hierarchical(CacheProvider cacheProvider, TypePool typePool) {
                super(cacheProvider);
                this.parent = typePool;
            }

            @Override // net.bytebuddy.pool.TypePool.AbstractBase, net.bytebuddy.pool.TypePool
            public Resolution describe(String str) {
                Resolution resolutionDescribe = this.parent.describe(str);
                return resolutionDescribe.isResolved() ? resolutionDescribe : super.describe(str);
            }

            @Override // net.bytebuddy.pool.TypePool.AbstractBase, net.bytebuddy.pool.TypePool
            public void clear() {
                try {
                    this.parent.clear();
                } finally {
                    super.clear();
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class ArrayTypeResolution implements Resolution {
            private final int arity;
            private final Resolution resolution;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ArrayTypeResolution arrayTypeResolution = (ArrayTypeResolution) obj;
                return this.arity == arrayTypeResolution.arity && this.resolution.equals(arrayTypeResolution.resolution);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.resolution.hashCode()) * 31) + this.arity;
            }

            protected ArrayTypeResolution(Resolution resolution, int i) {
                this.resolution = resolution;
                this.arity = i;
            }

            protected static Resolution of(Resolution resolution, int i) {
                return i == 0 ? resolution : new ArrayTypeResolution(resolution, i);
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public boolean isResolved() {
                return this.resolution.isResolved();
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public TypeDescription resolve() {
                return TypeDescription.ArrayProjection.of(this.resolution.resolve(), this.arity);
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class Default extends AbstractBase.Hierarchical {

        @AlwaysNull
        private static final MethodVisitor IGNORE_METHOD = null;
        protected final ClassFileLocator classFileLocator;
        protected final ReaderMode readerMode;

        @Override // net.bytebuddy.pool.TypePool.AbstractBase.Hierarchical, net.bytebuddy.pool.TypePool.AbstractBase
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
            Default r5 = (Default) obj;
            return this.readerMode.equals(r5.readerMode) && this.classFileLocator.equals(r5.classFileLocator);
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase.Hierarchical, net.bytebuddy.pool.TypePool.AbstractBase
        public int hashCode() {
            return (((super.hashCode() * 31) + this.classFileLocator.hashCode()) * 31) + this.readerMode.hashCode();
        }

        public Default(CacheProvider cacheProvider, ClassFileLocator classFileLocator, ReaderMode readerMode) {
            this(cacheProvider, classFileLocator, readerMode, Empty.INSTANCE);
        }

        public Default(CacheProvider cacheProvider, ClassFileLocator classFileLocator, ReaderMode readerMode, TypePool typePool) {
            super(cacheProvider, typePool);
            this.classFileLocator = classFileLocator;
            this.readerMode = readerMode;
        }

        public static TypePool ofSystemLoader() {
            return of(ClassFileLocator.ForClassLoader.ofSystemLoader());
        }

        public static TypePool ofPlatformLoader() {
            return of(ClassFileLocator.ForClassLoader.ofPlatformLoader());
        }

        public static TypePool ofBootLoader() {
            return of(ClassFileLocator.ForClassLoader.ofBootLoader());
        }

        public static TypePool of(@MaybeNull ClassLoader classLoader) {
            return of(ClassFileLocator.ForClassLoader.of(classLoader));
        }

        public static TypePool of(ClassFileLocator classFileLocator) {
            return new Default(new CacheProvider.Simple(), classFileLocator, ReaderMode.FAST);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // net.bytebuddy.pool.TypePool.AbstractBase
        public Resolution doDescribe(String str) {
            try {
                ClassFileLocator.Resolution resolutionLocate = this.classFileLocator.locate(str);
                return resolutionLocate.isResolved() ? new Resolution.Simple(parse(resolutionLocate.resolve())) : new Resolution.Illegal(str);
            } catch (IOException e) {
                throw new IllegalStateException("Error while reading class file", e);
            }
        }

        private TypeDescription parse(byte[] bArr) {
            ClassReader classReaderOf = OpenedClassReader.of(bArr);
            TypeExtractor typeExtractor = new TypeExtractor();
            classReaderOf.accept(typeExtractor, this.readerMode.getFlags());
            return typeExtractor.toTypeDescription();
        }

        public enum ReaderMode {
            EXTENDED(4),
            FAST(1);

            private final int flags;

            ReaderMode(int i) {
                this.flags = i;
            }

            protected int getFlags() {
                return this.flags;
            }

            public boolean isExtended() {
                return this == EXTENDED;
            }
        }

        public static class WithLazyResolution extends Default {
            @Override // net.bytebuddy.pool.TypePool.AbstractBase
            protected Resolution doCache(String str, Resolution resolution) {
                return resolution;
            }

            public WithLazyResolution(CacheProvider cacheProvider, ClassFileLocator classFileLocator, ReaderMode readerMode) {
                this(cacheProvider, classFileLocator, readerMode, Empty.INSTANCE);
            }

            public WithLazyResolution(CacheProvider cacheProvider, ClassFileLocator classFileLocator, ReaderMode readerMode, TypePool typePool) {
                super(cacheProvider, classFileLocator, readerMode, typePool);
            }

            public static TypePool ofSystemLoader() {
                return of(ClassFileLocator.ForClassLoader.ofSystemLoader());
            }

            public static TypePool ofPlatformLoader() {
                return of(ClassFileLocator.ForClassLoader.ofPlatformLoader());
            }

            public static TypePool ofBootLoader() {
                return of(ClassFileLocator.ForClassLoader.ofBootLoader());
            }

            public static TypePool of(@MaybeNull ClassLoader classLoader) {
                return of(ClassFileLocator.ForClassLoader.of(classLoader));
            }

            public static TypePool of(ClassFileLocator classFileLocator) {
                return new WithLazyResolution(new CacheProvider.Simple(), classFileLocator, ReaderMode.FAST);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // net.bytebuddy.pool.TypePool.Default, net.bytebuddy.pool.TypePool.AbstractBase
            public Resolution doDescribe(String str) {
                return new LazyResolution(str);
            }

            protected Resolution doResolve(String str) {
                Resolution resolutionFind = this.cacheProvider.find(str);
                return resolutionFind == null ? this.cacheProvider.register(str, super.doDescribe(str)) : resolutionFind;
            }

            @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
            protected class LazyResolution implements Resolution {

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f398name;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    LazyResolution lazyResolution = (LazyResolution) obj;
                    return this.f398name.equals(lazyResolution.f398name) && WithLazyResolution.this.equals(WithLazyResolution.this);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.f398name.hashCode()) * 31) + WithLazyResolution.this.hashCode();
                }

                protected LazyResolution(String str) {
                    this.f398name = str;
                }

                @Override // net.bytebuddy.pool.TypePool.Resolution
                public boolean isResolved() {
                    return WithLazyResolution.this.doResolve(this.f398name).isResolved();
                }

                @Override // net.bytebuddy.pool.TypePool.Resolution
                public TypeDescription resolve() {
                    return WithLazyResolution.this.new LazyTypeDescription(this.f398name);
                }
            }

            protected class LazyTypeDescription extends TypeDescription.AbstractBase.OfSimpleType.WithDelegation {
                private transient /* synthetic */ TypeDescription delegate;

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f399name;

                protected LazyTypeDescription(String str) {
                    this.f399name = str;
                }

                @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
                public String getName() {
                    return this.f399name;
                }

                @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase.OfSimpleType.WithDelegation
                @CachedReturnPlugin.Enhance(MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX)
                protected TypeDescription delegate() {
                    TypeDescription typeDescriptionResolve = this.delegate != null ? null : WithLazyResolution.this.doResolve(this.f399name).resolve();
                    if (typeDescriptionResolve == null) {
                        return this.delegate;
                    }
                    this.delegate = typeDescriptionResolve;
                    return typeDescriptionResolve;
                }
            }
        }

        protected interface AnnotationRegistrant {
            void onComplete();

            void register(String str, AnnotationValue<?, ?> annotationValue);

            public static abstract class AbstractBase implements AnnotationRegistrant {
                private final String descriptor;
                private final Map<String, AnnotationValue<?, ?>> values = new HashMap();

                protected abstract List<LazyTypeDescription.AnnotationToken> getTokens();

                protected AbstractBase(String str) {
                    this.descriptor = str;
                }

                @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                public void register(String str, AnnotationValue<?, ?> annotationValue) {
                    this.values.put(str, annotationValue);
                }

                @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                public void onComplete() {
                    getTokens().add(new LazyTypeDescription.AnnotationToken(this.descriptor, this.values));
                }

                protected static abstract class ForTypeVariable extends AbstractBase {
                    private final String typePath;

                    protected abstract Map<String, List<LazyTypeDescription.AnnotationToken>> getPathMap();

                    protected ForTypeVariable(String str, @MaybeNull TypePath typePath) {
                        String string;
                        super(str);
                        if (typePath == null) {
                            string = "";
                        } else {
                            string = typePath.toString();
                        }
                        this.typePath = string;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase
                    protected List<LazyTypeDescription.AnnotationToken> getTokens() {
                        Map<String, List<LazyTypeDescription.AnnotationToken>> pathMap = getPathMap();
                        List<LazyTypeDescription.AnnotationToken> list = pathMap.get(this.typePath);
                        if (list != null) {
                            return list;
                        }
                        ArrayList arrayList = new ArrayList();
                        pathMap.put(this.typePath, arrayList);
                        return arrayList;
                    }

                    protected static abstract class WithIndex extends ForTypeVariable {
                        private final int index;

                        protected abstract Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> getIndexedPathMap();

                        protected WithIndex(String str, @MaybeNull TypePath typePath, int i) {
                            super(str, typePath);
                            this.index = i;
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase.ForTypeVariable
                        protected Map<String, List<LazyTypeDescription.AnnotationToken>> getPathMap() {
                            Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> indexedPathMap = getIndexedPathMap();
                            Map<String, List<LazyTypeDescription.AnnotationToken>> map = indexedPathMap.get(Integer.valueOf(this.index));
                            if (map != null) {
                                return map;
                            }
                            HashMap map2 = new HashMap();
                            indexedPathMap.put(Integer.valueOf(this.index), map2);
                            return map2;
                        }

                        protected static abstract class DoubleIndexed extends WithIndex {
                            private final int preIndex;

                            protected abstract Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> getDoubleIndexedPathMap();

                            protected DoubleIndexed(String str, @MaybeNull TypePath typePath, int i, int i2) {
                                super(str, typePath, i);
                                this.preIndex = i2;
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase.ForTypeVariable.WithIndex
                            protected Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> getIndexedPathMap() {
                                Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> doubleIndexedPathMap = getDoubleIndexedPathMap();
                                Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> map = doubleIndexedPathMap.get(Integer.valueOf(this.preIndex));
                                if (map != null) {
                                    return map;
                                }
                                HashMap map2 = new HashMap();
                                doubleIndexedPathMap.put(Integer.valueOf(this.preIndex), map2);
                                return map2;
                            }
                        }
                    }
                }
            }

            public static class ForByteCodeElement extends AbstractBase {
                private final List<LazyTypeDescription.AnnotationToken> annotationTokens;

                protected ForByteCodeElement(String str, List<LazyTypeDescription.AnnotationToken> list) {
                    super(str);
                    this.annotationTokens = list;
                }

                @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase
                protected List<LazyTypeDescription.AnnotationToken> getTokens() {
                    return this.annotationTokens;
                }

                public static class WithIndex extends AbstractBase {
                    private final Map<Integer, List<LazyTypeDescription.AnnotationToken>> annotationTokens;
                    private final int index;

                    protected WithIndex(String str, int i, Map<Integer, List<LazyTypeDescription.AnnotationToken>> map) {
                        super(str);
                        this.index = i;
                        this.annotationTokens = map;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase
                    protected List<LazyTypeDescription.AnnotationToken> getTokens() {
                        List<LazyTypeDescription.AnnotationToken> list = this.annotationTokens.get(Integer.valueOf(this.index));
                        if (list != null) {
                            return list;
                        }
                        ArrayList arrayList = new ArrayList();
                        this.annotationTokens.put(Integer.valueOf(this.index), arrayList);
                        return arrayList;
                    }
                }
            }

            public static class ForTypeVariable extends AbstractBase.ForTypeVariable {
                private final Map<String, List<LazyTypeDescription.AnnotationToken>> pathMap;

                protected ForTypeVariable(String str, @MaybeNull TypePath typePath, Map<String, List<LazyTypeDescription.AnnotationToken>> map) {
                    super(str, typePath);
                    this.pathMap = map;
                }

                @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase.ForTypeVariable
                protected Map<String, List<LazyTypeDescription.AnnotationToken>> getPathMap() {
                    return this.pathMap;
                }

                public static class WithIndex extends AbstractBase.ForTypeVariable.WithIndex {
                    private final Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> indexedPathMap;

                    protected WithIndex(String str, @MaybeNull TypePath typePath, int i, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> map) {
                        super(str, typePath, i);
                        this.indexedPathMap = map;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase.ForTypeVariable.WithIndex
                    protected Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> getIndexedPathMap() {
                        return this.indexedPathMap;
                    }

                    public static class DoubleIndexed extends AbstractBase.ForTypeVariable.WithIndex.DoubleIndexed {
                        private final Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> doubleIndexedPathMap;

                        protected DoubleIndexed(String str, @MaybeNull TypePath typePath, int i, int i2, Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> map) {
                            super(str, typePath, i, i2);
                            this.doubleIndexedPathMap = map;
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant.AbstractBase.ForTypeVariable.WithIndex.DoubleIndexed
                        protected Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> getDoubleIndexedPathMap() {
                            return this.doubleIndexedPathMap;
                        }
                    }
                }
            }
        }

        protected interface ComponentTypeLocator {
            AbstractBase.ComponentTypeReference bind(String str);

            public enum Illegal implements ComponentTypeLocator {
                INSTANCE;

                @Override // net.bytebuddy.pool.TypePool.Default.ComponentTypeLocator
                public AbstractBase.ComponentTypeReference bind(String str) {
                    throw new IllegalStateException("Unexpected lookup of component type for " + str);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForAnnotationProperty implements ComponentTypeLocator {
                private final String annotationName;
                private final TypePool typePool;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    ForAnnotationProperty forAnnotationProperty = (ForAnnotationProperty) obj;
                    return this.annotationName.equals(forAnnotationProperty.annotationName) && this.typePool.equals(forAnnotationProperty.typePool);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.typePool.hashCode()) * 31) + this.annotationName.hashCode();
                }

                public ForAnnotationProperty(TypePool typePool, String str) {
                    this.typePool = typePool;
                    this.annotationName = str.substring(1, str.length() - 1).replace('/', '.');
                }

                @Override // net.bytebuddy.pool.TypePool.Default.ComponentTypeLocator
                public AbstractBase.ComponentTypeReference bind(String str) {
                    return new Bound(str);
                }

                @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                protected class Bound implements AbstractBase.ComponentTypeReference {

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    private final String f379name;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        Bound bound = (Bound) obj;
                        return this.f379name.equals(bound.f379name) && ForAnnotationProperty.this.equals(ForAnnotationProperty.this);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.f379name.hashCode()) * 31) + ForAnnotationProperty.this.hashCode();
                    }

                    protected Bound(String str) {
                        this.f379name = str;
                    }

                    @Override // net.bytebuddy.pool.TypePool.AbstractBase.ComponentTypeReference
                    @MaybeNull
                    public String resolve() {
                        TypeDescription componentType = ((MethodDescription.InDefinedShape) ForAnnotationProperty.this.typePool.describe(ForAnnotationProperty.this.annotationName).resolve().getDeclaredMethods().filter(ElementMatchers.named(this.f379name)).getOnly()).getReturnType().asErasure().getComponentType();
                        if (componentType == null) {
                            return NO_ARRAY;
                        }
                        return componentType.getName();
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class ForArrayType implements ComponentTypeLocator, AbstractBase.ComponentTypeReference {
                private final String componentType;

                @Override // net.bytebuddy.pool.TypePool.Default.ComponentTypeLocator
                public AbstractBase.ComponentTypeReference bind(String str) {
                    return this;
                }

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.componentType.equals(((ForArrayType) obj).componentType);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.componentType.hashCode();
                }

                public ForArrayType(String str) {
                    String className = Type.getMethodType(str).getReturnType().getClassName();
                    this.componentType = className.substring(0, className.length() - 2);
                }

                @Override // net.bytebuddy.pool.TypePool.AbstractBase.ComponentTypeReference
                public String resolve() {
                    return this.componentType;
                }
            }
        }

        protected interface GenericTypeRegistrant {
            void register(LazyTypeDescription.GenericTypeToken genericTypeToken);

            public static class RejectingSignatureVisitor extends SignatureVisitor {
                private static final String MESSAGE = "Unexpected token in generic signature";

                public RejectingSignatureVisitor() {
                    super(OpenedClassReader.ASM_API);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitFormalTypeParameter(String str) {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitClassBound() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitInterfaceBound() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitSuperclass() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitInterface() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitParameterType() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitReturnType() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitExceptionType() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitBaseType(char c) {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitTypeVariable(String str) {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitArrayType() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitClassType(String str) {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitInnerClassType(String str) {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitTypeArgument() {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitTypeArgument(char c) {
                    throw new IllegalStateException(MESSAGE);
                }

                @Override // net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitEnd() {
                    throw new IllegalStateException(MESSAGE);
                }
            }
        }

        protected static class ParameterBag {
            private final Map<Integer, String> parameterRegistry = new HashMap();
            private final Type[] parameterType;

            protected ParameterBag(Type[] typeArr) {
                this.parameterType = typeArr;
            }

            protected void register(int i, String str) {
                this.parameterRegistry.put(Integer.valueOf(i), str);
            }

            protected List<LazyTypeDescription.MethodToken.ParameterToken> resolve(boolean z) {
                int size;
                ArrayList arrayList = new ArrayList(this.parameterType.length);
                if (z) {
                    size = StackSize.ZERO.getSize();
                } else {
                    size = StackSize.SINGLE.getSize();
                }
                for (Type type : this.parameterType) {
                    String str = this.parameterRegistry.get(Integer.valueOf(size));
                    arrayList.add(str == null ? new LazyTypeDescription.MethodToken.ParameterToken() : new LazyTypeDescription.MethodToken.ParameterToken(str));
                    size += type.getSize();
                }
                return arrayList;
            }
        }

        protected static class GenericTypeExtractor extends GenericTypeRegistrant.RejectingSignatureVisitor implements GenericTypeRegistrant {
            private final GenericTypeRegistrant genericTypeRegistrant;

            @UnknownNull
            private IncompleteToken incompleteToken;

            protected GenericTypeExtractor(GenericTypeRegistrant genericTypeRegistrant) {
                this.genericTypeRegistrant = genericTypeRegistrant;
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public void visitBaseType(char c) {
                this.genericTypeRegistrant.register(LazyTypeDescription.GenericTypeToken.ForPrimitiveType.of(c));
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public void visitTypeVariable(String str) {
                this.genericTypeRegistrant.register(new LazyTypeDescription.GenericTypeToken.ForTypeVariable(str));
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public SignatureVisitor visitArrayType() {
                return new GenericTypeExtractor(this);
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
            public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                this.genericTypeRegistrant.register(new LazyTypeDescription.GenericTypeToken.ForGenericArray(genericTypeToken));
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public void visitClassType(String str) {
                this.incompleteToken = new IncompleteToken.ForTopLevelType(str);
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public void visitInnerClassType(String str) {
                this.incompleteToken = new IncompleteToken.ForInnerClass(str, this.incompleteToken);
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public void visitTypeArgument() {
                this.incompleteToken.appendPlaceholder();
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public SignatureVisitor visitTypeArgument(char c) {
                if (c == '+') {
                    return this.incompleteToken.appendUpperBound();
                }
                if (c == '-') {
                    return this.incompleteToken.appendLowerBound();
                }
                if (c == '=') {
                    return this.incompleteToken.appendDirectBound();
                }
                throw new IllegalArgumentException("Unknown wildcard: " + c);
            }

            @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
            public void visitEnd() {
                this.genericTypeRegistrant.register(this.incompleteToken.toToken());
            }

            protected interface IncompleteToken {
                SignatureVisitor appendDirectBound();

                SignatureVisitor appendLowerBound();

                void appendPlaceholder();

                SignatureVisitor appendUpperBound();

                String getName();

                boolean isParameterized();

                LazyTypeDescription.GenericTypeToken toToken();

                public static abstract class AbstractBase implements IncompleteToken {
                    protected final List<LazyTypeDescription.GenericTypeToken> parameters = new ArrayList();

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public SignatureVisitor appendDirectBound() {
                        return new GenericTypeExtractor(new ForDirectBound());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public SignatureVisitor appendUpperBound() {
                        return new GenericTypeExtractor(new ForUpperBound());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public SignatureVisitor appendLowerBound() {
                        return new GenericTypeExtractor(new ForLowerBound());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public void appendPlaceholder() {
                        this.parameters.add(LazyTypeDescription.GenericTypeToken.ForUnboundWildcard.INSTANCE);
                    }

                    protected class ForDirectBound implements GenericTypeRegistrant {
                        protected ForDirectBound() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            AbstractBase.this.parameters.add(genericTypeToken);
                        }
                    }

                    protected class ForUpperBound implements GenericTypeRegistrant {
                        protected ForUpperBound() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            AbstractBase.this.parameters.add(new LazyTypeDescription.GenericTypeToken.ForUpperBoundWildcard(genericTypeToken));
                        }
                    }

                    protected class ForLowerBound implements GenericTypeRegistrant {
                        protected ForLowerBound() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            AbstractBase.this.parameters.add(new LazyTypeDescription.GenericTypeToken.ForLowerBoundWildcard(genericTypeToken));
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForTopLevelType extends AbstractBase {
                    private final String internalName;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.internalName.equals(((ForTopLevelType) obj).internalName);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.internalName.hashCode();
                    }

                    public ForTopLevelType(String str) {
                        this.internalName = str;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public LazyTypeDescription.GenericTypeToken toToken() {
                        if (isParameterized()) {
                            return new LazyTypeDescription.GenericTypeToken.ForParameterizedType(getName(), this.parameters);
                        }
                        return new LazyTypeDescription.GenericTypeToken.ForRawType(getName());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public boolean isParameterized() {
                        return !this.parameters.isEmpty();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public String getName() {
                        return this.internalName.replace('/', '.');
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForInnerClass extends AbstractBase {
                    private static final char INNER_CLASS_SEPARATOR = '$';
                    private final String internalName;
                    private final IncompleteToken outerTypeToken;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForInnerClass forInnerClass = (ForInnerClass) obj;
                        return this.internalName.equals(forInnerClass.internalName) && this.outerTypeToken.equals(forInnerClass.outerTypeToken);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.internalName.hashCode()) * 31) + this.outerTypeToken.hashCode();
                    }

                    public ForInnerClass(String str, IncompleteToken incompleteToken) {
                        this.internalName = str;
                        this.outerTypeToken = incompleteToken;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public LazyTypeDescription.GenericTypeToken toToken() {
                        if (isParameterized() || this.outerTypeToken.isParameterized()) {
                            return new LazyTypeDescription.GenericTypeToken.ForParameterizedType.Nested(getName(), this.parameters, this.outerTypeToken.toToken());
                        }
                        return new LazyTypeDescription.GenericTypeToken.ForRawType(getName());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public boolean isParameterized() {
                        return (this.parameters.isEmpty() && this.outerTypeToken.isParameterized()) ? false : true;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.IncompleteToken
                    public String getName() {
                        return this.outerTypeToken.getName() + '$' + this.internalName.replace('/', '.');
                    }
                }
            }

            protected static abstract class ForSignature<T extends LazyTypeDescription.GenericTypeToken.Resolution> extends GenericTypeRegistrant.RejectingSignatureVisitor implements GenericTypeRegistrant {

                @UnknownNull
                protected List<LazyTypeDescription.GenericTypeToken> currentBounds;

                @MaybeNull
                protected String currentTypeParameter;
                protected final List<LazyTypeDescription.GenericTypeToken.OfFormalTypeVariable> typeVariableTokens = new ArrayList();

                public abstract T resolve();

                protected static <S extends LazyTypeDescription.GenericTypeToken.Resolution> S extract(String str, ForSignature<S> forSignature) {
                    new SignatureReader(str).accept(forSignature);
                    return (S) forSignature.resolve();
                }

                @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                public void visitFormalTypeParameter(String str) {
                    collectTypeParameter();
                    this.currentTypeParameter = str;
                    this.currentBounds = new ArrayList();
                }

                @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitClassBound() {
                    return new GenericTypeExtractor(this);
                }

                @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                public SignatureVisitor visitInterfaceBound() {
                    return new GenericTypeExtractor(this);
                }

                @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                    List<LazyTypeDescription.GenericTypeToken> list = this.currentBounds;
                    if (list == null) {
                        throw new IllegalStateException("Did not expect " + genericTypeToken + " before finding formal parameter");
                    }
                    list.add(genericTypeToken);
                }

                protected void collectTypeParameter() {
                    if (this.currentTypeParameter != null) {
                        this.typeVariableTokens.add(new LazyTypeDescription.GenericTypeToken.ForTypeVariable.Formal(this.currentTypeParameter, this.currentBounds));
                    }
                }

                protected static class OfType extends ForSignature<LazyTypeDescription.GenericTypeToken.Resolution.ForType> {
                    private final List<LazyTypeDescription.GenericTypeToken> interfaceTypeTokens = new ArrayList();

                    @UnknownNull
                    private LazyTypeDescription.GenericTypeToken superClassToken;

                    protected OfType() {
                    }

                    public static LazyTypeDescription.GenericTypeToken.Resolution.ForType extract(@MaybeNull String str) {
                        LazyTypeDescription.GenericTypeToken.Resolution.ForType forType;
                        try {
                            if (str == null) {
                                forType = LazyTypeDescription.GenericTypeToken.Resolution.Raw.INSTANCE;
                            } else {
                                forType = (LazyTypeDescription.GenericTypeToken.Resolution.ForType) ForSignature.extract(str, new OfType());
                            }
                            return forType;
                        } catch (RuntimeException unused) {
                            return LazyTypeDescription.GenericTypeToken.Resolution.Malformed.INSTANCE;
                        }
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                    public SignatureVisitor visitSuperclass() {
                        collectTypeParameter();
                        return new GenericTypeExtractor(new SuperClassRegistrant());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                    public SignatureVisitor visitInterface() {
                        return new GenericTypeExtractor(new InterfaceTypeRegistrant());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.ForSignature
                    public LazyTypeDescription.GenericTypeToken.Resolution.ForType resolve() {
                        return new LazyTypeDescription.GenericTypeToken.Resolution.ForType.Tokenized(this.superClassToken, this.interfaceTypeTokens, this.typeVariableTokens);
                    }

                    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                    protected class SuperClassRegistrant implements GenericTypeRegistrant {
                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && OfType.this.equals(OfType.this);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + OfType.this.hashCode();
                        }

                        protected SuperClassRegistrant() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            OfType.this.superClassToken = genericTypeToken;
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                    protected class InterfaceTypeRegistrant implements GenericTypeRegistrant {
                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && OfType.this.equals(OfType.this);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + OfType.this.hashCode();
                        }

                        protected InterfaceTypeRegistrant() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            OfType.this.interfaceTypeTokens.add(genericTypeToken);
                        }
                    }
                }

                protected static class OfField implements GenericTypeRegistrant {

                    @UnknownNull
                    private LazyTypeDescription.GenericTypeToken fieldTypeToken;

                    protected OfField() {
                    }

                    public static LazyTypeDescription.GenericTypeToken.Resolution.ForField extract(@MaybeNull String str) {
                        if (str == null) {
                            return LazyTypeDescription.GenericTypeToken.Resolution.Raw.INSTANCE;
                        }
                        SignatureReader signatureReader = new SignatureReader(str);
                        OfField ofField = new OfField();
                        try {
                            signatureReader.acceptType(new GenericTypeExtractor(ofField));
                            return ofField.resolve();
                        } catch (RuntimeException unused) {
                            return LazyTypeDescription.GenericTypeToken.Resolution.Malformed.INSTANCE;
                        }
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                    public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                        this.fieldTypeToken = genericTypeToken;
                    }

                    protected LazyTypeDescription.GenericTypeToken.Resolution.ForField resolve() {
                        return new LazyTypeDescription.GenericTypeToken.Resolution.ForField.Tokenized(this.fieldTypeToken);
                    }
                }

                protected static class OfMethod extends ForSignature<LazyTypeDescription.GenericTypeToken.Resolution.ForMethod> {

                    @UnknownNull
                    private LazyTypeDescription.GenericTypeToken returnTypeToken;
                    private final List<LazyTypeDescription.GenericTypeToken> parameterTypeTokens = new ArrayList();
                    private final List<LazyTypeDescription.GenericTypeToken> exceptionTypeTokens = new ArrayList();

                    public static LazyTypeDescription.GenericTypeToken.Resolution.ForMethod extract(@MaybeNull String str) {
                        LazyTypeDescription.GenericTypeToken.Resolution.ForMethod forMethod;
                        try {
                            if (str == null) {
                                forMethod = LazyTypeDescription.GenericTypeToken.Resolution.Raw.INSTANCE;
                            } else {
                                forMethod = (LazyTypeDescription.GenericTypeToken.Resolution.ForMethod) ForSignature.extract(str, new OfMethod());
                            }
                            return forMethod;
                        } catch (RuntimeException unused) {
                            return LazyTypeDescription.GenericTypeToken.Resolution.Malformed.INSTANCE;
                        }
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                    public SignatureVisitor visitParameterType() {
                        return new GenericTypeExtractor(new ParameterTypeRegistrant());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                    public SignatureVisitor visitReturnType() {
                        collectTypeParameter();
                        return new GenericTypeExtractor(new ReturnTypeTypeRegistrant());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant.RejectingSignatureVisitor, net.bytebuddy.jar.asm.signature.SignatureVisitor
                    public SignatureVisitor visitExceptionType() {
                        return new GenericTypeExtractor(new ExceptionTypeRegistrant());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeExtractor.ForSignature
                    public LazyTypeDescription.GenericTypeToken.Resolution.ForMethod resolve() {
                        return new LazyTypeDescription.GenericTypeToken.Resolution.ForMethod.Tokenized(this.returnTypeToken, this.parameterTypeTokens, this.exceptionTypeTokens, this.typeVariableTokens);
                    }

                    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                    protected class ParameterTypeRegistrant implements GenericTypeRegistrant {
                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && OfMethod.this.equals(OfMethod.this);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + OfMethod.this.hashCode();
                        }

                        protected ParameterTypeRegistrant() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            OfMethod.this.parameterTypeTokens.add(genericTypeToken);
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                    protected class ReturnTypeTypeRegistrant implements GenericTypeRegistrant {
                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && OfMethod.this.equals(OfMethod.this);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + OfMethod.this.hashCode();
                        }

                        protected ReturnTypeTypeRegistrant() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            OfMethod.this.returnTypeToken = genericTypeToken;
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
                    protected class ExceptionTypeRegistrant implements GenericTypeRegistrant {
                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && OfMethod.this.equals(OfMethod.this);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + OfMethod.this.hashCode();
                        }

                        protected ExceptionTypeRegistrant() {
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                        public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                            OfMethod.this.exceptionTypeTokens.add(genericTypeToken);
                        }
                    }
                }

                protected static class OfRecordComponent implements GenericTypeRegistrant {

                    @UnknownNull
                    private LazyTypeDescription.GenericTypeToken recordComponentType;

                    protected OfRecordComponent() {
                    }

                    public static LazyTypeDescription.GenericTypeToken.Resolution.ForRecordComponent extract(@MaybeNull String str) {
                        if (str == null) {
                            return LazyTypeDescription.GenericTypeToken.Resolution.Raw.INSTANCE;
                        }
                        SignatureReader signatureReader = new SignatureReader(str);
                        OfRecordComponent ofRecordComponent = new OfRecordComponent();
                        try {
                            signatureReader.acceptType(new GenericTypeExtractor(ofRecordComponent));
                            return ofRecordComponent.resolve();
                        } catch (RuntimeException unused) {
                            return LazyTypeDescription.GenericTypeToken.Resolution.Malformed.INSTANCE;
                        }
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.GenericTypeRegistrant
                    public void register(LazyTypeDescription.GenericTypeToken genericTypeToken) {
                        this.recordComponentType = genericTypeToken;
                    }

                    protected LazyTypeDescription.GenericTypeToken.Resolution.ForRecordComponent resolve() {
                        return new LazyTypeDescription.GenericTypeToken.Resolution.ForRecordComponent.Tokenized(this.recordComponentType);
                    }
                }
            }
        }

        protected static class LazyTypeDescription extends TypeDescription.AbstractBase.OfSimpleType {

            @AlwaysNull
            private static final String NO_TYPE = null;
            private final int actualModifiers;
            private final List<AnnotationToken> annotationTokens;
            private final boolean anonymousType;
            private final ClassFileVersion classFileVersion;
            private final List<String> declaredTypes;

            @MaybeNull
            private final String declaringTypeName;
            private final List<FieldToken> fieldTokens;

            @MaybeNull
            private final String genericSignature;
            private final Map<Integer, Map<String, List<AnnotationToken>>> interfaceAnnotationTokens;
            private final List<String> interfaceTypeDescriptors;
            private final List<MethodToken> methodTokens;
            private final int modifiers;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f380name;

            @MaybeNull
            private final String nestHost;
            private final List<String> nestMembers;
            private final List<String> permittedSubclasses;
            private final List<RecordComponentToken> recordComponentTokens;
            private final GenericTypeToken.Resolution.ForType signatureResolution;
            private final Map<String, List<AnnotationToken>> superClassAnnotationTokens;

            @MaybeNull
            private final String superClassDescriptor;
            private final TypeContainment typeContainment;
            private final TypePool typePool;
            private final Map<Integer, Map<String, List<AnnotationToken>>> typeVariableAnnotationTokens;
            private final Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> typeVariableBoundsAnnotationTokens;

            protected LazyTypeDescription(TypePool typePool, int i, int i2, String str, @MaybeNull String str2, @MaybeNull String[] strArr, @MaybeNull String str3, TypeContainment typeContainment, @MaybeNull String str4, List<String> list, boolean z, @MaybeNull String str5, List<String> list2, Map<String, List<AnnotationToken>> map, Map<Integer, Map<String, List<AnnotationToken>>> map2, Map<Integer, Map<String, List<AnnotationToken>>> map3, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map4, List<AnnotationToken> list3, List<FieldToken> list4, List<MethodToken> list5, List<RecordComponentToken> list6, List<String> list7, ClassFileVersion classFileVersion) {
                String descriptor;
                String strReplace;
                String className;
                this.typePool = typePool;
                this.actualModifiers = i & (-33);
                this.modifiers = (-131105) & i2;
                this.f380name = Type.getObjectType(str).getClassName();
                if (str2 == null) {
                    descriptor = NO_TYPE;
                } else {
                    descriptor = Type.getObjectType(str2).getDescriptor();
                }
                this.superClassDescriptor = descriptor;
                this.genericSignature = str3;
                this.signatureResolution = RAW_TYPES ? GenericTypeToken.Resolution.Raw.INSTANCE : GenericTypeExtractor.ForSignature.OfType.extract(str3);
                if (strArr == null) {
                    this.interfaceTypeDescriptors = Collections.emptyList();
                } else {
                    this.interfaceTypeDescriptors = new ArrayList(strArr.length);
                    for (String str6 : strArr) {
                        this.interfaceTypeDescriptors.add(Type.getObjectType(str6).getDescriptor());
                    }
                }
                this.typeContainment = typeContainment;
                if (str4 == null) {
                    strReplace = NO_TYPE;
                } else {
                    strReplace = str4.replace('/', '.');
                }
                this.declaringTypeName = strReplace;
                this.declaredTypes = list;
                this.anonymousType = z;
                if (str5 == null) {
                    className = NO_TYPE;
                } else {
                    className = Type.getObjectType(str5).getClassName();
                }
                this.nestHost = className;
                this.nestMembers = new ArrayList(list2.size());
                Iterator<String> it = list2.iterator();
                while (it.hasNext()) {
                    this.nestMembers.add(Type.getObjectType(it.next()).getClassName());
                }
                this.superClassAnnotationTokens = map;
                this.interfaceAnnotationTokens = map2;
                this.typeVariableAnnotationTokens = map3;
                this.typeVariableBoundsAnnotationTokens = map4;
                this.annotationTokens = list3;
                this.fieldTokens = list4;
                this.methodTokens = list5;
                this.recordComponentTokens = list6;
                this.permittedSubclasses = new ArrayList(list7.size());
                Iterator<String> it2 = list7.iterator();
                while (it2.hasNext()) {
                    this.permittedSubclasses.add(Type.getObjectType(it2.next()).getDescriptor());
                }
                this.classFileVersion = classFileVersion;
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            @MaybeNull
            public TypeDescription.Generic getSuperClass() {
                if (this.superClassDescriptor == null || isInterface()) {
                    return TypeDescription.Generic.UNDEFINED;
                }
                return this.signatureResolution.resolveSuperClass(this.superClassDescriptor, this.typePool, this.superClassAnnotationTokens, this);
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public TypeList.Generic getInterfaces() {
                return this.signatureResolution.resolveInterfaceTypes(this.interfaceTypeDescriptors, this.typePool, this.interfaceAnnotationTokens, this);
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            @MaybeNull
            public MethodDescription.InDefinedShape getEnclosingMethod() {
                return this.typeContainment.getEnclosingMethod(this.typePool);
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            @MaybeNull
            public TypeDescription getEnclosingType() {
                return this.typeContainment.getEnclosingType(this.typePool);
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            public TypeList getDeclaredTypes() {
                return new LazyTypeList(this.typePool, this.declaredTypes);
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            public boolean isAnonymousType() {
                return this.anonymousType;
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            public boolean isLocalType() {
                return !this.anonymousType && this.typeContainment.isLocalType();
            }

            @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
            public FieldList<FieldDescription.InDefinedShape> getDeclaredFields() {
                return new FieldTokenList();
            }

            @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
            public MethodList<MethodDescription.InDefinedShape> getDeclaredMethods() {
                return new MethodTokenList();
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            @MaybeNull
            public PackageDescription getPackage() {
                String name2 = getName();
                int iLastIndexOf = name2.lastIndexOf(46);
                return new LazyPackageDescription(this.typePool, iLastIndexOf == -1 ? "" : name2.substring(0, iLastIndexOf));
            }

            @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
            public String getName() {
                return this.f380name;
            }

            @Override // net.bytebuddy.description.DeclaredByType
            @MaybeNull
            public TypeDescription getDeclaringType() {
                String str = this.declaringTypeName;
                return str == null ? TypeDescription.UNDEFINED : this.typePool.describe(str).resolve();
            }

            @Override // net.bytebuddy.description.ModifierReviewable
            public int getModifiers() {
                return this.modifiers;
            }

            @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
            public int getActualModifiers(boolean z) {
                return z ? this.actualModifiers | 32 : this.actualModifiers;
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            public TypeDescription getNestHost() {
                String str = this.nestHost;
                return str == null ? this : this.typePool.describe(str).resolve();
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            public TypeList getNestMembers() {
                String str = this.nestHost;
                return str == null ? new LazyNestMemberList(this, this.typePool, this.nestMembers) : this.typePool.describe(str).resolve().getNestMembers();
            }

            @Override // net.bytebuddy.description.annotation.AnnotationSource
            public AnnotationList getDeclaredAnnotations() {
                return LazyAnnotationDescription.asList(this.typePool, this.annotationTokens);
            }

            @Override // net.bytebuddy.description.TypeVariableSource
            public TypeList.Generic getTypeVariables() {
                return this.signatureResolution.resolveTypeVariables(this.typePool, this, this.typeVariableAnnotationTokens, this.typeVariableBoundsAnnotationTokens);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
            @MaybeNull
            public String getGenericSignature() {
                return this.genericSignature;
            }

            @Override // net.bytebuddy.description.type.TypeDescription, net.bytebuddy.description.type.TypeDefinition
            public RecordComponentList<RecordComponentDescription.InDefinedShape> getRecordComponents() {
                return new RecordComponentTokenList();
            }

            @Override // net.bytebuddy.description.type.TypeDefinition
            public boolean isRecord() {
                return (this.actualModifiers & 65536) != 0 && JavaType.RECORD.getTypeStub().getDescriptor().equals(this.superClassDescriptor);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
            public boolean isSealed() {
                return !this.permittedSubclasses.isEmpty();
            }

            @Override // net.bytebuddy.description.type.TypeDescription
            public TypeList getPermittedSubtypes() {
                return new LazyTypeList(this.typePool, this.permittedSubclasses);
            }

            @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase, net.bytebuddy.description.type.TypeDescription
            public ClassFileVersion getClassFileVersion() {
                return this.classFileVersion;
            }

            protected class FieldTokenList extends FieldList.AbstractBase<FieldDescription.InDefinedShape> {
                protected FieldTokenList() {
                }

                @Override // java.util.AbstractList, java.util.List
                public FieldDescription.InDefinedShape get(int i) {
                    return ((FieldToken) LazyTypeDescription.this.fieldTokens.get(i)).toFieldDescription(LazyTypeDescription.this);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return LazyTypeDescription.this.fieldTokens.size();
                }
            }

            protected class MethodTokenList extends MethodList.AbstractBase<MethodDescription.InDefinedShape> {
                protected MethodTokenList() {
                }

                @Override // java.util.AbstractList, java.util.List
                public MethodDescription.InDefinedShape get(int i) {
                    return ((MethodToken) LazyTypeDescription.this.methodTokens.get(i)).toMethodDescription(LazyTypeDescription.this);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return LazyTypeDescription.this.methodTokens.size();
                }
            }

            protected class RecordComponentTokenList extends RecordComponentList.AbstractBase<RecordComponentDescription.InDefinedShape> {
                protected RecordComponentTokenList() {
                }

                @Override // java.util.AbstractList, java.util.List
                public RecordComponentDescription.InDefinedShape get(int i) {
                    return ((RecordComponentToken) LazyTypeDescription.this.recordComponentTokens.get(i)).toRecordComponentDescription(LazyTypeDescription.this);
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return LazyTypeDescription.this.recordComponentTokens.size();
                }
            }

            protected interface TypeContainment {
                @MaybeNull
                MethodDescription.InDefinedShape getEnclosingMethod(TypePool typePool);

                @MaybeNull
                TypeDescription getEnclosingType(TypePool typePool);

                boolean isLocalType();

                boolean isSelfContained();

                public enum SelfContained implements TypeContainment {
                    INSTANCE;

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public boolean isLocalType() {
                        return false;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public boolean isSelfContained() {
                        return true;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    @MaybeNull
                    public MethodDescription.InDefinedShape getEnclosingMethod(TypePool typePool) {
                        return MethodDescription.UNDEFINED;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    @MaybeNull
                    public TypeDescription getEnclosingType(TypePool typePool) {
                        return TypeDescription.UNDEFINED;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class WithinType implements TypeContainment {
                    private final boolean localType;

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    private final String f394name;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        WithinType withinType = (WithinType) obj;
                        return this.localType == withinType.localType && this.f394name.equals(withinType.f394name);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.f394name.hashCode()) * 31) + (this.localType ? 1 : 0);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public boolean isSelfContained() {
                        return false;
                    }

                    protected WithinType(String str, boolean z) {
                        this.f394name = str.replace('/', '.');
                        this.localType = z;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    @MaybeNull
                    public MethodDescription.InDefinedShape getEnclosingMethod(TypePool typePool) {
                        return MethodDescription.UNDEFINED;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public TypeDescription getEnclosingType(TypePool typePool) {
                        return typePool.describe(this.f394name).resolve();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public boolean isLocalType() {
                        return this.localType;
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class WithinMethod implements TypeContainment {
                    private final String methodDescriptor;
                    private final String methodName;

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    private final String f393name;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        WithinMethod withinMethod = (WithinMethod) obj;
                        return this.f393name.equals(withinMethod.f393name) && this.methodName.equals(withinMethod.methodName) && this.methodDescriptor.equals(withinMethod.methodDescriptor);
                    }

                    public int hashCode() {
                        return (((((getClass().hashCode() * 31) + this.f393name.hashCode()) * 31) + this.methodName.hashCode()) * 31) + this.methodDescriptor.hashCode();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public boolean isLocalType() {
                        return true;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public boolean isSelfContained() {
                        return false;
                    }

                    protected WithinMethod(String str, String str2, String str3) {
                        this.f393name = str.replace('/', '.');
                        this.methodName = str2;
                        this.methodDescriptor = str3;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public MethodDescription.InDefinedShape getEnclosingMethod(TypePool typePool) {
                        TypeDescription enclosingType = getEnclosingType(typePool);
                        if (enclosingType == null) {
                            throw new IllegalStateException("Could not resolve enclosing type " + this.f393name);
                        }
                        MethodList methodListFilter = enclosingType.getDeclaredMethods().filter(ElementMatchers.hasMethodName(this.methodName).and(ElementMatchers.hasDescriptor(this.methodDescriptor)));
                        if (methodListFilter.isEmpty()) {
                            throw new IllegalStateException(this.methodName + this.methodDescriptor + " not declared by " + enclosingType);
                        }
                        return (MethodDescription.InDefinedShape) methodListFilter.getOnly();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.TypeContainment
                    public TypeDescription getEnclosingType(TypePool typePool) {
                        return typePool.describe(this.f393name).resolve();
                    }
                }
            }

            protected interface GenericTypeToken {
                public static final char COMPONENT_TYPE_PATH = '[';
                public static final String EMPTY_TYPE_PATH = "";
                public static final char INDEXED_TYPE_DELIMITER = ';';
                public static final char INNER_CLASS_PATH = '.';
                public static final char WILDCARD_TYPE_PATH = '*';

                public interface OfFormalTypeVariable {
                    TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, Map<String, List<AnnotationToken>> map, Map<Integer, Map<String, List<AnnotationToken>>> map2);
                }

                String getTypePathPrefix();

                boolean isPrimaryBound(TypePool typePool);

                TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map);

                public enum ForPrimitiveType implements GenericTypeToken {
                    BOOLEAN(Boolean.TYPE),
                    BYTE(Byte.TYPE),
                    SHORT(Short.TYPE),
                    CHAR(Character.TYPE),
                    INTEGER(Integer.TYPE),
                    LONG(Long.TYPE),
                    FLOAT(Float.TYPE),
                    DOUBLE(Double.TYPE),
                    VOID(Void.TYPE);

                    private final TypeDescription typeDescription;

                    ForPrimitiveType(Class cls) {
                        this.typeDescription = TypeDescription.ForLoadedType.of(cls);
                    }

                    public static GenericTypeToken of(char c) {
                        if (c == 'F') {
                            return FLOAT;
                        }
                        if (c == 'S') {
                            return SHORT;
                        }
                        if (c == 'V') {
                            return VOID;
                        }
                        if (c == 'Z') {
                            return BOOLEAN;
                        }
                        if (c == 'I') {
                            return INTEGER;
                        }
                        if (c != 'J') {
                            switch (c) {
                                case 'B':
                                    return BYTE;
                                case 'C':
                                    return CHAR;
                                case 'D':
                                    return DOUBLE;
                                default:
                                    throw new IllegalArgumentException("Not a valid primitive type descriptor: " + c);
                            }
                        }
                        return LONG;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                        return new LazyPrimitiveType(typePool, str, map, this.typeDescription);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        throw new IllegalStateException("A primitive type cannot be a type variable bound: " + this);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        throw new IllegalStateException("A primitive type cannot be the owner of a nested type: " + this);
                    }

                    protected static class LazyPrimitiveType extends TypeDescription.Generic.OfNonGenericType {
                        private final Map<String, List<AnnotationToken>> annotationTokens;
                        private final TypeDescription typeDescription;
                        private final String typePath;
                        private final TypePool typePool;

                        protected LazyPrimitiveType(TypePool typePool, String str, Map<String, List<AnnotationToken>> map, TypeDescription typeDescription) {
                            this.typePool = typePool;
                            this.typePath = str;
                            this.annotationTokens = map;
                            this.typeDescription = typeDescription;
                        }

                        @Override // net.bytebuddy.description.type.TypeDefinition
                        public TypeDescription asErasure() {
                            return this.typeDescription;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        @MaybeNull
                        public TypeDescription.Generic getOwnerType() {
                            return TypeDescription.Generic.UNDEFINED;
                        }

                        @Override // net.bytebuddy.description.type.TypeDefinition
                        @MaybeNull
                        public TypeDescription.Generic getComponentType() {
                            return TypeDescription.Generic.UNDEFINED;
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(this.typePath));
                        }
                    }
                }

                public enum ForUnboundWildcard implements GenericTypeToken {
                    INSTANCE;

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, @MaybeNull Map<String, List<AnnotationToken>> map) {
                        if (map == null) {
                            map = Collections.emptyMap();
                        }
                        return new LazyUnboundWildcard(typePool, str, map);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        throw new IllegalStateException("A wildcard type cannot be a type variable bound: " + this);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        throw new IllegalStateException("An unbound wildcard cannot be the owner of a nested type: " + this);
                    }

                    protected static class LazyUnboundWildcard extends TypeDescription.Generic.OfWildcardType {
                        private final Map<String, List<AnnotationToken>> annotationTokens;
                        private final String typePath;
                        private final TypePool typePool;

                        protected LazyUnboundWildcard(TypePool typePool, String str, Map<String, List<AnnotationToken>> map) {
                            this.typePool = typePool;
                            this.typePath = str;
                            this.annotationTokens = map;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getUpperBounds() {
                            return new TypeList.Generic.Explicit(TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Object.class));
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getLowerBounds() {
                            return new TypeList.Generic.Empty();
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(this.typePath));
                        }
                    }
                }

                public interface Resolution {
                    TypeList.Generic resolveTypeVariables(TypePool typePool, TypeVariableSource typeVariableSource, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2);

                    public enum Raw implements ForType, ForField, ForMethod, ForRecordComponent {
                        INSTANCE;

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForType
                        public TypeDescription.Generic resolveSuperClass(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, TypeDescription typeDescription) {
                            return RawAnnotatedType.of(typePool, map, str);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForType
                        public TypeList.Generic resolveInterfaceTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, TypeDescription typeDescription) {
                            return RawAnnotatedType.LazyRawAnnotatedTypeList.of(typePool, map, list);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution
                        public TypeList.Generic resolveTypeVariables(TypePool typePool, TypeVariableSource typeVariableSource, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2) {
                            return new TypeList.Generic.Empty();
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForField
                        public TypeDescription.Generic resolveFieldType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, FieldDescription.InDefinedShape inDefinedShape) {
                            return RawAnnotatedType.of(typePool, map, str);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                        public TypeDescription.Generic resolveReturnType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, MethodDescription.InDefinedShape inDefinedShape) {
                            return RawAnnotatedType.of(typePool, map, str);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                        public TypeList.Generic resolveParameterTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape) {
                            return RawAnnotatedType.LazyRawAnnotatedTypeList.of(typePool, map, list);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                        public TypeList.Generic resolveExceptionTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape) {
                            return RawAnnotatedType.LazyRawAnnotatedTypeList.of(typePool, map, list);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForRecordComponent
                        public TypeDescription.Generic resolveRecordType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, RecordComponentDescription.InDefinedShape inDefinedShape) {
                            return RawAnnotatedType.of(typePool, map, str);
                        }

                        protected static class RawAnnotatedType extends TypeDescription.Generic.OfNonGenericType {
                            private final Map<String, List<AnnotationToken>> annotationTokens;
                            private final TypeDescription typeDescription;
                            private final String typePath;
                            private final TypePool typePool;

                            protected RawAnnotatedType(TypePool typePool, String str, Map<String, List<AnnotationToken>> map, TypeDescription typeDescription) {
                                this.typePool = typePool;
                                this.typePath = str;
                                this.annotationTokens = map;
                                this.typeDescription = typeDescription;
                            }

                            protected static TypeDescription.Generic of(TypePool typePool, @MaybeNull Map<String, List<AnnotationToken>> map, String str) {
                                if (map == null) {
                                    map = Collections.emptyMap();
                                }
                                return new RawAnnotatedType(typePool, "", map, TokenizedGenericType.toErasure(typePool, str));
                            }

                            @Override // net.bytebuddy.description.type.TypeDefinition
                            public TypeDescription asErasure() {
                                return this.typeDescription;
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            @MaybeNull
                            public TypeDescription.Generic getOwnerType() {
                                TypeDescription declaringType = this.typeDescription.getDeclaringType();
                                return declaringType == null ? TypeDescription.Generic.UNDEFINED : new RawAnnotatedType(this.typePool, this.typePath, this.annotationTokens, declaringType);
                            }

                            @Override // net.bytebuddy.description.type.TypeDefinition
                            @MaybeNull
                            public TypeDescription.Generic getComponentType() {
                                TypeDescription componentType = this.typeDescription.getComponentType();
                                return componentType == null ? TypeDescription.Generic.UNDEFINED : new RawAnnotatedType(this.typePool, this.typePath + '[', this.annotationTokens, componentType);
                            }

                            @Override // net.bytebuddy.description.annotation.AnnotationSource
                            public AnnotationList getDeclaredAnnotations() {
                                StringBuilder sb = new StringBuilder(this.typePath);
                                for (int i = 0; i < this.typeDescription.getInnerClassCount(); i++) {
                                    sb = sb.append('.');
                                }
                                return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(sb.toString()));
                            }

                            protected static class LazyRawAnnotatedTypeList extends TypeList.Generic.AbstractBase {
                                private final Map<Integer, Map<String, List<AnnotationToken>>> annotationTokens;
                                private final List<String> descriptors;
                                private final TypePool typePool;

                                @Override // net.bytebuddy.description.type.TypeList.Generic.AbstractBase, net.bytebuddy.description.type.TypeList.Generic
                                public TypeList.Generic asRawTypes() {
                                    return this;
                                }

                                protected LazyRawAnnotatedTypeList(TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, List<String> list) {
                                    this.typePool = typePool;
                                    this.annotationTokens = map;
                                    this.descriptors = list;
                                }

                                protected static TypeList.Generic of(TypePool typePool, @MaybeNull Map<Integer, Map<String, List<AnnotationToken>>> map, List<String> list) {
                                    if (map == null) {
                                        map = Collections.emptyMap();
                                    }
                                    return new LazyRawAnnotatedTypeList(typePool, map, list);
                                }

                                @Override // java.util.AbstractList, java.util.List
                                public TypeDescription.Generic get(int i) {
                                    return RawAnnotatedType.of(this.typePool, this.annotationTokens.get(Integer.valueOf(i)), this.descriptors.get(i));
                                }

                                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                                public int size() {
                                    return this.descriptors.size();
                                }

                                @Override // net.bytebuddy.description.type.TypeList.Generic.AbstractBase, net.bytebuddy.description.type.TypeList.Generic
                                public TypeList asErasures() {
                                    return new LazyTypeList(this.typePool, this.descriptors);
                                }

                                @Override // net.bytebuddy.description.type.TypeList.Generic.AbstractBase, net.bytebuddy.description.type.TypeList.Generic
                                public int getStackSize() {
                                    Iterator<String> it = this.descriptors.iterator();
                                    int size = 0;
                                    while (it.hasNext()) {
                                        size += Type.getType(it.next()).getSize();
                                    }
                                    return size;
                                }
                            }
                        }
                    }

                    public enum Malformed implements ForType, ForField, ForMethod, ForRecordComponent {
                        INSTANCE;

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForType
                        public TypeDescription.Generic resolveSuperClass(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, TypeDescription typeDescription) {
                            return new TokenizedGenericType.Malformed(typePool, str);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForType
                        public TypeList.Generic resolveInterfaceTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, TypeDescription typeDescription) {
                            return new TokenizedGenericType.Malformed.TokenList(typePool, list);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution
                        public TypeList.Generic resolveTypeVariables(TypePool typePool, TypeVariableSource typeVariableSource, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2) {
                            throw new GenericSignatureFormatError();
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForField
                        public TypeDescription.Generic resolveFieldType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, FieldDescription.InDefinedShape inDefinedShape) {
                            return new TokenizedGenericType.Malformed(typePool, str);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                        public TypeDescription.Generic resolveReturnType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, MethodDescription.InDefinedShape inDefinedShape) {
                            return new TokenizedGenericType.Malformed(typePool, str);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                        public TypeList.Generic resolveParameterTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape) {
                            return new TokenizedGenericType.Malformed.TokenList(typePool, list);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                        public TypeList.Generic resolveExceptionTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape) {
                            return new TokenizedGenericType.Malformed.TokenList(typePool, list);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForRecordComponent
                        public TypeDescription.Generic resolveRecordType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, RecordComponentDescription.InDefinedShape inDefinedShape) {
                            return new TokenizedGenericType.Malformed(typePool, str);
                        }
                    }

                    public interface ForType extends Resolution {
                        TypeList.Generic resolveInterfaceTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, TypeDescription typeDescription);

                        TypeDescription.Generic resolveSuperClass(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, TypeDescription typeDescription);

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class Tokenized implements ForType {
                            private final List<GenericTypeToken> interfaceTypeTokens;
                            private final GenericTypeToken superClassToken;
                            private final List<OfFormalTypeVariable> typeVariableTokens;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                if (obj == null || getClass() != obj.getClass()) {
                                    return false;
                                }
                                Tokenized tokenized = (Tokenized) obj;
                                return this.superClassToken.equals(tokenized.superClassToken) && this.interfaceTypeTokens.equals(tokenized.interfaceTypeTokens) && this.typeVariableTokens.equals(tokenized.typeVariableTokens);
                            }

                            public int hashCode() {
                                return (((((getClass().hashCode() * 31) + this.superClassToken.hashCode()) * 31) + this.interfaceTypeTokens.hashCode()) * 31) + this.typeVariableTokens.hashCode();
                            }

                            protected Tokenized(GenericTypeToken genericTypeToken, List<GenericTypeToken> list, List<OfFormalTypeVariable> list2) {
                                this.superClassToken = genericTypeToken;
                                this.interfaceTypeTokens = list;
                                this.typeVariableTokens = list2;
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForType
                            public TypeDescription.Generic resolveSuperClass(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, TypeDescription typeDescription) {
                                return TokenizedGenericType.of(typePool, this.superClassToken, str, map, typeDescription);
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForType
                            public TypeList.Generic resolveInterfaceTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, TypeDescription typeDescription) {
                                return new TokenizedGenericType.TokenList(typePool, this.interfaceTypeTokens, map, list, typeDescription);
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution
                            public TypeList.Generic resolveTypeVariables(TypePool typePool, TypeVariableSource typeVariableSource, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2) {
                                return new TokenizedGenericType.TypeVariableList(typePool, this.typeVariableTokens, typeVariableSource, map, map2);
                            }
                        }
                    }

                    public interface ForField {
                        TypeDescription.Generic resolveFieldType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, FieldDescription.InDefinedShape inDefinedShape);

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class Tokenized implements ForField {
                            private final GenericTypeToken fieldTypeToken;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                return obj != null && getClass() == obj.getClass() && this.fieldTypeToken.equals(((Tokenized) obj).fieldTypeToken);
                            }

                            public int hashCode() {
                                return (getClass().hashCode() * 31) + this.fieldTypeToken.hashCode();
                            }

                            protected Tokenized(GenericTypeToken genericTypeToken) {
                                this.fieldTypeToken = genericTypeToken;
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForField
                            public TypeDescription.Generic resolveFieldType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, FieldDescription.InDefinedShape inDefinedShape) {
                                return TokenizedGenericType.of(typePool, this.fieldTypeToken, str, map, inDefinedShape.getDeclaringType());
                            }
                        }
                    }

                    public interface ForMethod extends Resolution {
                        TypeList.Generic resolveExceptionTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape);

                        TypeList.Generic resolveParameterTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape);

                        TypeDescription.Generic resolveReturnType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, MethodDescription.InDefinedShape inDefinedShape);

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class Tokenized implements ForMethod {
                            private final List<GenericTypeToken> exceptionTypeTokens;
                            private final List<GenericTypeToken> parameterTypeTokens;
                            private final GenericTypeToken returnTypeToken;
                            private final List<OfFormalTypeVariable> typeVariableTokens;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                if (obj == null || getClass() != obj.getClass()) {
                                    return false;
                                }
                                Tokenized tokenized = (Tokenized) obj;
                                return this.returnTypeToken.equals(tokenized.returnTypeToken) && this.parameterTypeTokens.equals(tokenized.parameterTypeTokens) && this.exceptionTypeTokens.equals(tokenized.exceptionTypeTokens) && this.typeVariableTokens.equals(tokenized.typeVariableTokens);
                            }

                            public int hashCode() {
                                return (((((((getClass().hashCode() * 31) + this.returnTypeToken.hashCode()) * 31) + this.parameterTypeTokens.hashCode()) * 31) + this.exceptionTypeTokens.hashCode()) * 31) + this.typeVariableTokens.hashCode();
                            }

                            protected Tokenized(GenericTypeToken genericTypeToken, List<GenericTypeToken> list, List<GenericTypeToken> list2, List<OfFormalTypeVariable> list3) {
                                this.returnTypeToken = genericTypeToken;
                                this.parameterTypeTokens = list;
                                this.exceptionTypeTokens = list2;
                                this.typeVariableTokens = list3;
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                            public TypeDescription.Generic resolveReturnType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, MethodDescription.InDefinedShape inDefinedShape) {
                                return TokenizedGenericType.of(typePool, this.returnTypeToken, str, map, inDefinedShape);
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                            public TypeList.Generic resolveParameterTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape) {
                                return new TokenizedGenericType.TokenList(typePool, this.parameterTypeTokens, map, list, inDefinedShape);
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForMethod
                            public TypeList.Generic resolveExceptionTypes(List<String> list, TypePool typePool, Map<Integer, Map<String, List<AnnotationToken>>> map, MethodDescription.InDefinedShape inDefinedShape) {
                                return this.exceptionTypeTokens.isEmpty() ? Raw.INSTANCE.resolveExceptionTypes(list, typePool, map, inDefinedShape) : new TokenizedGenericType.TokenList(typePool, this.exceptionTypeTokens, map, list, inDefinedShape);
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution
                            public TypeList.Generic resolveTypeVariables(TypePool typePool, TypeVariableSource typeVariableSource, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2) {
                                return new TokenizedGenericType.TypeVariableList(typePool, this.typeVariableTokens, typeVariableSource, map, map2);
                            }
                        }
                    }

                    public interface ForRecordComponent {
                        TypeDescription.Generic resolveRecordType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, RecordComponentDescription.InDefinedShape inDefinedShape);

                        @HashCodeAndEqualsPlugin.Enhance
                        public static class Tokenized implements ForRecordComponent {
                            private final GenericTypeToken recordComponentTypeToken;

                            public boolean equals(@MaybeNull Object obj) {
                                if (this == obj) {
                                    return true;
                                }
                                return obj != null && getClass() == obj.getClass() && this.recordComponentTypeToken.equals(((Tokenized) obj).recordComponentTypeToken);
                            }

                            public int hashCode() {
                                return (getClass().hashCode() * 31) + this.recordComponentTypeToken.hashCode();
                            }

                            protected Tokenized(GenericTypeToken genericTypeToken) {
                                this.recordComponentTypeToken = genericTypeToken;
                            }

                            @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.ForRecordComponent
                            public TypeDescription.Generic resolveRecordType(String str, TypePool typePool, Map<String, List<AnnotationToken>> map, RecordComponentDescription.InDefinedShape inDefinedShape) {
                                return TokenizedGenericType.of(typePool, this.recordComponentTypeToken, str, map, inDefinedShape.getDeclaringType());
                            }
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForRawType implements GenericTypeToken {

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    private final String f386name;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.f386name.equals(((ForRawType) obj).f386name);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.f386name.hashCode();
                    }

                    protected ForRawType(String str) {
                        this.f386name = str;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                        return new Resolution.Raw.RawAnnotatedType(typePool, str, map, typePool.describe(this.f386name).resolve());
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        return !typePool.describe(this.f386name).resolve().isInterface();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        throw new IllegalStateException("A non-generic type cannot be the owner of a nested type: " + this);
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForTypeVariable implements GenericTypeToken {
                    private final String symbol;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.symbol.equals(((ForTypeVariable) obj).symbol);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.symbol.hashCode();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        return true;
                    }

                    protected ForTypeVariable(String str) {
                        this.symbol = str;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                        TypeDescription.Generic genericFindVariable = typeVariableSource.findVariable(this.symbol);
                        if (genericFindVariable == null) {
                            return new UnresolvedTypeVariable(typeVariableSource, typePool, this.symbol, map.get(str));
                        }
                        return new AnnotatedTypeVariable(typePool, map.get(str), genericFindVariable);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        throw new IllegalStateException("A type variable cannot be the owner of a nested type: " + this);
                    }

                    protected static class AnnotatedTypeVariable extends TypeDescription.Generic.OfTypeVariable {
                        private final List<AnnotationToken> annotationTokens;
                        private final TypePool typePool;
                        private final TypeDescription.Generic typeVariable;

                        protected AnnotatedTypeVariable(TypePool typePool, List<AnnotationToken> list, TypeDescription.Generic generic) {
                            this.typePool = typePool;
                            this.annotationTokens = list;
                            this.typeVariable = generic;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getUpperBounds() {
                            return this.typeVariable.getUpperBounds();
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeVariableSource getTypeVariableSource() {
                            return this.typeVariable.getTypeVariableSource();
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public String getSymbol() {
                            return this.typeVariable.getSymbol();
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens);
                        }
                    }

                    protected static class UnresolvedTypeVariable extends TypeDescription.Generic.OfTypeVariable {
                        private final List<AnnotationToken> annotationTokens;
                        private final String symbol;
                        private final TypePool typePool;
                        private final TypeVariableSource typeVariableSource;

                        protected UnresolvedTypeVariable(TypeVariableSource typeVariableSource, TypePool typePool, String str, List<AnnotationToken> list) {
                            this.typeVariableSource = typeVariableSource;
                            this.typePool = typePool;
                            this.symbol = str;
                            this.annotationTokens = list;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getUpperBounds() {
                            throw new IllegalStateException("Cannot resolve bounds of unresolved type variable " + this + " by " + this.typeVariableSource);
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeVariableSource getTypeVariableSource() {
                            return this.typeVariableSource;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public String getSymbol() {
                            return this.symbol;
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens);
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    protected static class Formal implements OfFormalTypeVariable {
                        private final List<GenericTypeToken> boundTypeTokens;
                        private final String symbol;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (obj == null || getClass() != obj.getClass()) {
                                return false;
                            }
                            Formal formal = (Formal) obj;
                            return this.symbol.equals(formal.symbol) && this.boundTypeTokens.equals(formal.boundTypeTokens);
                        }

                        public int hashCode() {
                            return (((getClass().hashCode() * 31) + this.symbol.hashCode()) * 31) + this.boundTypeTokens.hashCode();
                        }

                        protected Formal(String str, List<GenericTypeToken> list) {
                            this.symbol = str;
                            this.boundTypeTokens = list;
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.OfFormalTypeVariable
                        public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, @MaybeNull Map<String, List<AnnotationToken>> map, @MaybeNull Map<Integer, Map<String, List<AnnotationToken>>> map2) {
                            if (map == null) {
                                map = Collections.emptyMap();
                            }
                            Map<String, List<AnnotationToken>> map3 = map;
                            if (map2 == null) {
                                map2 = Collections.emptyMap();
                            }
                            return new LazyTypeVariable(typePool, typeVariableSource, map3, map2, this.symbol, this.boundTypeTokens);
                        }

                        protected static class LazyTypeVariable extends TypeDescription.Generic.OfTypeVariable {
                            private final Map<String, List<AnnotationToken>> annotationTokens;
                            private final List<GenericTypeToken> boundTypeTokens;
                            private final Map<Integer, Map<String, List<AnnotationToken>>> boundaryAnnotationTokens;
                            private final String symbol;
                            private final TypePool typePool;
                            private final TypeVariableSource typeVariableSource;

                            protected LazyTypeVariable(TypePool typePool, TypeVariableSource typeVariableSource, Map<String, List<AnnotationToken>> map, Map<Integer, Map<String, List<AnnotationToken>>> map2, String str, List<GenericTypeToken> list) {
                                this.typePool = typePool;
                                this.typeVariableSource = typeVariableSource;
                                this.annotationTokens = map;
                                this.boundaryAnnotationTokens = map2;
                                this.symbol = str;
                                this.boundTypeTokens = list;
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            public TypeList.Generic getUpperBounds() {
                                return new LazyBoundTokenList(this.typePool, this.typeVariableSource, this.boundaryAnnotationTokens, this.boundTypeTokens);
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            public TypeVariableSource getTypeVariableSource() {
                                return this.typeVariableSource;
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            public String getSymbol() {
                                return this.symbol;
                            }

                            @Override // net.bytebuddy.description.annotation.AnnotationSource
                            public AnnotationList getDeclaredAnnotations() {
                                return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(""));
                            }

                            protected static class LazyBoundTokenList extends TypeList.Generic.AbstractBase {
                                private final Map<Integer, Map<String, List<AnnotationToken>>> annotationTokens;
                                private final List<GenericTypeToken> boundTypeTokens;
                                private final TypePool typePool;
                                private final TypeVariableSource typeVariableSource;

                                protected LazyBoundTokenList(TypePool typePool, TypeVariableSource typeVariableSource, Map<Integer, Map<String, List<AnnotationToken>>> map, List<GenericTypeToken> list) {
                                    this.typePool = typePool;
                                    this.typeVariableSource = typeVariableSource;
                                    this.annotationTokens = map;
                                    this.boundTypeTokens = list;
                                }

                                @Override // java.util.AbstractList, java.util.List
                                public TypeDescription.Generic get(int i) {
                                    Map<String, List<AnnotationToken>> mapEmptyMap;
                                    if (this.annotationTokens.containsKey(Integer.valueOf(i)) || this.annotationTokens.containsKey(Integer.valueOf(i + 1))) {
                                        mapEmptyMap = this.annotationTokens.get(Integer.valueOf((!this.boundTypeTokens.get(0).isPrimaryBound(this.typePool) ? 1 : 0) + i));
                                    } else {
                                        mapEmptyMap = Collections.emptyMap();
                                    }
                                    GenericTypeToken genericTypeToken = this.boundTypeTokens.get(i);
                                    TypePool typePool = this.typePool;
                                    TypeVariableSource typeVariableSource = this.typeVariableSource;
                                    if (mapEmptyMap == null) {
                                        mapEmptyMap = Collections.emptyMap();
                                    }
                                    return genericTypeToken.toGenericType(typePool, typeVariableSource, "", mapEmptyMap);
                                }

                                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                                public int size() {
                                    return this.boundTypeTokens.size();
                                }
                            }
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForGenericArray implements GenericTypeToken {
                    private final GenericTypeToken componentTypeToken;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.componentTypeToken.equals(((ForGenericArray) obj).componentTypeToken);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.componentTypeToken.hashCode();
                    }

                    protected ForGenericArray(GenericTypeToken genericTypeToken) {
                        this.componentTypeToken = genericTypeToken;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                        return new LazyGenericArray(typePool, typeVariableSource, str, map, this.componentTypeToken);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        throw new IllegalStateException("A generic array type cannot be a type variable bound: " + this);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        throw new IllegalStateException("A generic array type cannot be the owner of a nested type: " + this);
                    }

                    protected static class LazyGenericArray extends TypeDescription.Generic.OfGenericArray {
                        private final Map<String, List<AnnotationToken>> annotationTokens;
                        private final GenericTypeToken componentTypeToken;
                        private final String typePath;
                        private final TypePool typePool;
                        private final TypeVariableSource typeVariableSource;

                        protected LazyGenericArray(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map, GenericTypeToken genericTypeToken) {
                            this.typePool = typePool;
                            this.typeVariableSource = typeVariableSource;
                            this.typePath = str;
                            this.annotationTokens = map;
                            this.componentTypeToken = genericTypeToken;
                        }

                        @Override // net.bytebuddy.description.type.TypeDefinition
                        public TypeDescription.Generic getComponentType() {
                            return this.componentTypeToken.toGenericType(this.typePool, this.typeVariableSource, this.typePath + '[', this.annotationTokens);
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(this.typePath));
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForLowerBoundWildcard implements GenericTypeToken {
                    private final GenericTypeToken boundTypeToken;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.boundTypeToken.equals(((ForLowerBoundWildcard) obj).boundTypeToken);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.boundTypeToken.hashCode();
                    }

                    protected ForLowerBoundWildcard(GenericTypeToken genericTypeToken) {
                        this.boundTypeToken = genericTypeToken;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                        return new LazyLowerBoundWildcard(typePool, typeVariableSource, str, map, this.boundTypeToken);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        throw new IllegalStateException("A wildcard type cannot be a type variable bound: " + this);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        throw new IllegalStateException("A lower bound wildcard cannot be the owner of a nested type: " + this);
                    }

                    protected static class LazyLowerBoundWildcard extends TypeDescription.Generic.OfWildcardType {
                        private final Map<String, List<AnnotationToken>> annotationTokens;
                        private final GenericTypeToken boundTypeToken;
                        private final String typePath;
                        private final TypePool typePool;
                        private final TypeVariableSource typeVariableSource;

                        protected LazyLowerBoundWildcard(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map, GenericTypeToken genericTypeToken) {
                            this.typePool = typePool;
                            this.typeVariableSource = typeVariableSource;
                            this.typePath = str;
                            this.annotationTokens = map;
                            this.boundTypeToken = genericTypeToken;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getUpperBounds() {
                            return new TypeList.Generic.Explicit(TypeDescription.Generic.OfNonGenericType.ForLoadedType.of(Object.class));
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getLowerBounds() {
                            return new LazyTokenList.ForWildcardBound(this.typePool, this.typeVariableSource, this.typePath, this.annotationTokens, this.boundTypeToken);
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(this.typePath));
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForUpperBoundWildcard implements GenericTypeToken {
                    private final GenericTypeToken boundTypeToken;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.boundTypeToken.equals(((ForUpperBoundWildcard) obj).boundTypeToken);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.boundTypeToken.hashCode();
                    }

                    protected ForUpperBoundWildcard(GenericTypeToken genericTypeToken) {
                        this.boundTypeToken = genericTypeToken;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                        return new LazyUpperBoundWildcard(typePool, typeVariableSource, str, map, this.boundTypeToken);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        throw new IllegalStateException("A wildcard type cannot be a type variable bound: " + this);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        throw new IllegalStateException("An upper bound wildcard cannot be the owner of a nested type: " + this);
                    }

                    protected static class LazyUpperBoundWildcard extends TypeDescription.Generic.OfWildcardType {
                        private final Map<String, List<AnnotationToken>> annotationTokens;
                        private final GenericTypeToken boundTypeToken;
                        private final String typePath;
                        private final TypePool typePool;
                        private final TypeVariableSource typeVariableSource;

                        protected LazyUpperBoundWildcard(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map, GenericTypeToken genericTypeToken) {
                            this.typePool = typePool;
                            this.typeVariableSource = typeVariableSource;
                            this.typePath = str;
                            this.annotationTokens = map;
                            this.boundTypeToken = genericTypeToken;
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getUpperBounds() {
                            return new LazyTokenList.ForWildcardBound(this.typePool, this.typeVariableSource, this.typePath, this.annotationTokens, this.boundTypeToken);
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getLowerBounds() {
                            return new TypeList.Generic.Empty();
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(this.typePath));
                        }
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                public static class ForParameterizedType implements GenericTypeToken {

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    private final String f382name;
                    private final List<GenericTypeToken> parameterTypeTokens;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForParameterizedType forParameterizedType = (ForParameterizedType) obj;
                        return this.f382name.equals(forParameterizedType.f382name) && this.parameterTypeTokens.equals(forParameterizedType.parameterTypeTokens);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.f382name.hashCode()) * 31) + this.parameterTypeTokens.hashCode();
                    }

                    protected ForParameterizedType(String str, List<GenericTypeToken> list) {
                        this.f382name = str;
                        this.parameterTypeTokens = list;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                        return new LazyParameterizedType(typePool, typeVariableSource, str, map, this.f382name, this.parameterTypeTokens);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public boolean isPrimaryBound(TypePool typePool) {
                        return !typePool.describe(this.f382name).resolve().isInterface();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                    public String getTypePathPrefix() {
                        return String.valueOf('.');
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class Nested implements GenericTypeToken {

                        /* JADX INFO: renamed from: name, reason: collision with root package name */
                        private final String f384name;
                        private final GenericTypeToken ownerTypeToken;
                        private final List<GenericTypeToken> parameterTypeTokens;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            if (obj == null || getClass() != obj.getClass()) {
                                return false;
                            }
                            Nested nested = (Nested) obj;
                            return this.f384name.equals(nested.f384name) && this.parameterTypeTokens.equals(nested.parameterTypeTokens) && this.ownerTypeToken.equals(nested.ownerTypeToken);
                        }

                        public int hashCode() {
                            return (((((getClass().hashCode() * 31) + this.f384name.hashCode()) * 31) + this.parameterTypeTokens.hashCode()) * 31) + this.ownerTypeToken.hashCode();
                        }

                        protected Nested(String str, List<GenericTypeToken> list, GenericTypeToken genericTypeToken) {
                            this.f384name = str;
                            this.parameterTypeTokens = list;
                            this.ownerTypeToken = genericTypeToken;
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                        public TypeDescription.Generic toGenericType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map) {
                            return new LazyParameterizedType(typePool, typeVariableSource, str, map, this.f384name, this.parameterTypeTokens, this.ownerTypeToken);
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                        public String getTypePathPrefix() {
                            return this.ownerTypeToken.getTypePathPrefix() + '.';
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken
                        public boolean isPrimaryBound(TypePool typePool) {
                            return !typePool.describe(this.f384name).resolve().isInterface();
                        }

                        protected static class LazyParameterizedType extends TypeDescription.Generic.OfParameterizedType {
                            private final Map<String, List<AnnotationToken>> annotationTokens;

                            /* JADX INFO: renamed from: name, reason: collision with root package name */
                            private final String f385name;
                            private final GenericTypeToken ownerTypeToken;
                            private final List<GenericTypeToken> parameterTypeTokens;
                            private final String typePath;
                            private final TypePool typePool;
                            private final TypeVariableSource typeVariableSource;

                            protected LazyParameterizedType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map, String str2, List<GenericTypeToken> list, GenericTypeToken genericTypeToken) {
                                this.typePool = typePool;
                                this.typeVariableSource = typeVariableSource;
                                this.typePath = str;
                                this.annotationTokens = map;
                                this.f385name = str2;
                                this.parameterTypeTokens = list;
                                this.ownerTypeToken = genericTypeToken;
                            }

                            @Override // net.bytebuddy.description.type.TypeDefinition
                            public TypeDescription asErasure() {
                                return this.typePool.describe(this.f385name).resolve();
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            public TypeList.Generic getTypeArguments() {
                                return new LazyTokenList(this.typePool, this.typeVariableSource, this.typePath + this.ownerTypeToken.getTypePathPrefix(), this.annotationTokens, this.parameterTypeTokens);
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            @MaybeNull
                            public TypeDescription.Generic getOwnerType() {
                                return this.ownerTypeToken.toGenericType(this.typePool, this.typeVariableSource, this.typePath, this.annotationTokens);
                            }

                            @Override // net.bytebuddy.description.annotation.AnnotationSource
                            public AnnotationList getDeclaredAnnotations() {
                                return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(this.typePath + this.ownerTypeToken.getTypePathPrefix()));
                            }
                        }
                    }

                    protected static class LazyParameterizedType extends TypeDescription.Generic.OfParameterizedType {
                        private final Map<String, List<AnnotationToken>> annotationTokens;

                        /* JADX INFO: renamed from: name, reason: collision with root package name */
                        private final String f383name;
                        private final List<GenericTypeToken> parameterTypeTokens;
                        private final String typePath;
                        private final TypePool typePool;
                        private final TypeVariableSource typeVariableSource;

                        protected LazyParameterizedType(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map, String str2, List<GenericTypeToken> list) {
                            this.typePool = typePool;
                            this.typeVariableSource = typeVariableSource;
                            this.typePath = str;
                            this.annotationTokens = map;
                            this.f383name = str2;
                            this.parameterTypeTokens = list;
                        }

                        @Override // net.bytebuddy.description.type.TypeDefinition
                        public TypeDescription asErasure() {
                            return this.typePool.describe(this.f383name).resolve();
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        public TypeList.Generic getTypeArguments() {
                            return new LazyTokenList(this.typePool, this.typeVariableSource, this.typePath, this.annotationTokens, this.parameterTypeTokens);
                        }

                        @Override // net.bytebuddy.description.type.TypeDescription.Generic
                        @MaybeNull
                        public TypeDescription.Generic getOwnerType() {
                            TypeDescription enclosingType = this.typePool.describe(this.f383name).resolve().getEnclosingType();
                            if (enclosingType == null) {
                                return TypeDescription.Generic.UNDEFINED;
                            }
                            return enclosingType.asGenericType();
                        }

                        @Override // net.bytebuddy.description.annotation.AnnotationSource
                        public AnnotationList getDeclaredAnnotations() {
                            return LazyAnnotationDescription.asListOfNullable(this.typePool, this.annotationTokens.get(this.typePath));
                        }
                    }
                }

                public static class LazyTokenList extends TypeList.Generic.AbstractBase {
                    private final Map<String, List<AnnotationToken>> annotationTokens;
                    private final List<GenericTypeToken> genericTypeTokens;
                    private final String typePath;
                    private final TypePool typePool;
                    private final TypeVariableSource typeVariableSource;

                    protected LazyTokenList(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map, List<GenericTypeToken> list) {
                        this.typePool = typePool;
                        this.typeVariableSource = typeVariableSource;
                        this.typePath = str;
                        this.annotationTokens = map;
                        this.genericTypeTokens = list;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public TypeDescription.Generic get(int i) {
                        return this.genericTypeTokens.get(i).toGenericType(this.typePool, this.typeVariableSource, this.typePath + i + ';', this.annotationTokens);
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.genericTypeTokens.size();
                    }

                    protected static class ForWildcardBound extends TypeList.Generic.AbstractBase {
                        private final Map<String, List<AnnotationToken>> annotationTokens;
                        private final GenericTypeToken genericTypeToken;
                        private final String typePath;
                        private final TypePool typePool;
                        private final TypeVariableSource typeVariableSource;

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                        public int size() {
                            return 1;
                        }

                        protected ForWildcardBound(TypePool typePool, TypeVariableSource typeVariableSource, String str, Map<String, List<AnnotationToken>> map, GenericTypeToken genericTypeToken) {
                            this.typePool = typePool;
                            this.typeVariableSource = typeVariableSource;
                            this.typePath = str;
                            this.annotationTokens = map;
                            this.genericTypeToken = genericTypeToken;
                        }

                        @Override // java.util.AbstractList, java.util.List
                        public TypeDescription.Generic get(int i) {
                            if (i == 0) {
                                return this.genericTypeToken.toGenericType(this.typePool, this.typeVariableSource, this.typePath + '*', this.annotationTokens);
                            }
                            throw new IndexOutOfBoundsException("index = " + i);
                        }
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class AnnotationToken {
                private final String descriptor;
                private final Map<String, AnnotationValue<?, ?>> values;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    AnnotationToken annotationToken = (AnnotationToken) obj;
                    return this.descriptor.equals(annotationToken.descriptor) && this.values.equals(annotationToken.values);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.descriptor.hashCode()) * 31) + this.values.hashCode();
                }

                protected AnnotationToken(String str, Map<String, AnnotationValue<?, ?>> map) {
                    this.descriptor = str;
                    this.values = map;
                }

                protected String getBinaryName() {
                    String str = this.descriptor;
                    return str.substring(1, str.length() - 1).replace('/', '.');
                }

                /* JADX INFO: Access modifiers changed from: private */
                public Resolution toAnnotationDescription(TypePool typePool) {
                    Resolution resolutionDescribe = typePool.describe(getBinaryName());
                    if (resolutionDescribe.isResolved()) {
                        return new Resolution.Simple(new LazyAnnotationDescription(typePool, resolutionDescribe.resolve(), this.values));
                    }
                    return new Resolution.Illegal(getBinaryName());
                }

                protected interface Resolution {
                    boolean isResolved();

                    AnnotationDescription resolve();

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class Simple implements Resolution {
                        private final AnnotationDescription annotationDescription;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && this.annotationDescription.equals(((Simple) obj).annotationDescription);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + this.annotationDescription.hashCode();
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.AnnotationToken.Resolution
                        public boolean isResolved() {
                            return true;
                        }

                        protected Simple(AnnotationDescription annotationDescription) {
                            this.annotationDescription = annotationDescription;
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.AnnotationToken.Resolution
                        public AnnotationDescription resolve() {
                            return this.annotationDescription;
                        }
                    }

                    @HashCodeAndEqualsPlugin.Enhance
                    public static class Illegal implements Resolution {
                        private final String annotationType;

                        public boolean equals(@MaybeNull Object obj) {
                            if (this == obj) {
                                return true;
                            }
                            return obj != null && getClass() == obj.getClass() && this.annotationType.equals(((Illegal) obj).annotationType);
                        }

                        public int hashCode() {
                            return (getClass().hashCode() * 31) + this.annotationType.hashCode();
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.AnnotationToken.Resolution
                        public boolean isResolved() {
                            return false;
                        }

                        public Illegal(String str) {
                            this.annotationType = str;
                        }

                        @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.AnnotationToken.Resolution
                        public AnnotationDescription resolve() {
                            throw new IllegalStateException("Annotation type is not available: " + this.annotationType);
                        }
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class FieldToken {
                private final List<AnnotationToken> annotationTokens;
                private final String descriptor;

                @UnknownNull
                private final String genericSignature;
                private final int modifiers;

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f381name;
                private final GenericTypeToken.Resolution.ForField signatureResolution;
                private final Map<String, List<AnnotationToken>> typeAnnotationTokens;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    FieldToken fieldToken = (FieldToken) obj;
                    return this.modifiers == fieldToken.modifiers && this.f381name.equals(fieldToken.f381name) && this.descriptor.equals(fieldToken.descriptor) && this.genericSignature.equals(fieldToken.genericSignature) && this.signatureResolution.equals(fieldToken.signatureResolution) && this.typeAnnotationTokens.equals(fieldToken.typeAnnotationTokens) && this.annotationTokens.equals(fieldToken.annotationTokens);
                }

                public int hashCode() {
                    return (((((((((((((getClass().hashCode() * 31) + this.f381name.hashCode()) * 31) + this.modifiers) * 31) + this.descriptor.hashCode()) * 31) + this.genericSignature.hashCode()) * 31) + this.signatureResolution.hashCode()) * 31) + this.typeAnnotationTokens.hashCode()) * 31) + this.annotationTokens.hashCode();
                }

                protected FieldToken(String str, int i, String str2, @MaybeNull String str3, Map<String, List<AnnotationToken>> map, List<AnnotationToken> list) {
                    this.modifiers = i & (-131073);
                    this.f381name = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.signatureResolution = TypeDescription.AbstractBase.RAW_TYPES ? GenericTypeToken.Resolution.Raw.INSTANCE : GenericTypeExtractor.ForSignature.OfField.extract(str3);
                    this.typeAnnotationTokens = map;
                    this.annotationTokens = list;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public LazyFieldDescription toFieldDescription(LazyTypeDescription lazyTypeDescription) {
                    lazyTypeDescription.getClass();
                    return new LazyFieldDescription(this.f381name, this.modifiers, this.descriptor, this.genericSignature, this.signatureResolution, this.typeAnnotationTokens, this.annotationTokens);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class MethodToken {
                private final List<AnnotationToken> annotationTokens;

                @UnknownNull
                private final AnnotationValue<?, ?> defaultValue;
                private final String descriptor;

                @MaybeNull
                private final String[] exceptionName;
                private final Map<Integer, Map<String, List<AnnotationToken>>> exceptionTypeAnnotationTokens;

                @UnknownNull
                private final String genericSignature;
                private final int modifiers;

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f390name;
                private final Map<Integer, List<AnnotationToken>> parameterAnnotationTokens;
                private final List<ParameterToken> parameterTokens;
                private final Map<Integer, Map<String, List<AnnotationToken>>> parameterTypeAnnotationTokens;
                private final Map<String, List<AnnotationToken>> receiverTypeAnnotationTokens;
                private final Map<String, List<AnnotationToken>> returnTypeAnnotationTokens;
                private final GenericTypeToken.Resolution.ForMethod signatureResolution;
                private final Map<Integer, Map<String, List<AnnotationToken>>> typeVariableAnnotationTokens;
                private final Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> typeVariableBoundAnnotationTokens;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    MethodToken methodToken = (MethodToken) obj;
                    return this.modifiers == methodToken.modifiers && this.f390name.equals(methodToken.f390name) && this.descriptor.equals(methodToken.descriptor) && this.genericSignature.equals(methodToken.genericSignature) && this.signatureResolution.equals(methodToken.signatureResolution) && Arrays.equals(this.exceptionName, methodToken.exceptionName) && this.typeVariableAnnotationTokens.equals(methodToken.typeVariableAnnotationTokens) && this.typeVariableBoundAnnotationTokens.equals(methodToken.typeVariableBoundAnnotationTokens) && this.returnTypeAnnotationTokens.equals(methodToken.returnTypeAnnotationTokens) && this.parameterTypeAnnotationTokens.equals(methodToken.parameterTypeAnnotationTokens) && this.exceptionTypeAnnotationTokens.equals(methodToken.exceptionTypeAnnotationTokens) && this.receiverTypeAnnotationTokens.equals(methodToken.receiverTypeAnnotationTokens) && this.annotationTokens.equals(methodToken.annotationTokens) && this.parameterAnnotationTokens.equals(methodToken.parameterAnnotationTokens) && this.parameterTokens.equals(methodToken.parameterTokens) && this.defaultValue.equals(methodToken.defaultValue);
                }

                public int hashCode() {
                    return (((((((((((((((((((((((((((((((getClass().hashCode() * 31) + this.f390name.hashCode()) * 31) + this.modifiers) * 31) + this.descriptor.hashCode()) * 31) + this.genericSignature.hashCode()) * 31) + this.signatureResolution.hashCode()) * 31) + Arrays.hashCode(this.exceptionName)) * 31) + this.typeVariableAnnotationTokens.hashCode()) * 31) + this.typeVariableBoundAnnotationTokens.hashCode()) * 31) + this.returnTypeAnnotationTokens.hashCode()) * 31) + this.parameterTypeAnnotationTokens.hashCode()) * 31) + this.exceptionTypeAnnotationTokens.hashCode()) * 31) + this.receiverTypeAnnotationTokens.hashCode()) * 31) + this.annotationTokens.hashCode()) * 31) + this.parameterAnnotationTokens.hashCode()) * 31) + this.parameterTokens.hashCode()) * 31) + this.defaultValue.hashCode();
                }

                protected MethodToken(String str, int i, String str2, @MaybeNull String str3, @MaybeNull String[] strArr, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2, Map<String, List<AnnotationToken>> map3, Map<Integer, Map<String, List<AnnotationToken>>> map4, Map<Integer, Map<String, List<AnnotationToken>>> map5, Map<String, List<AnnotationToken>> map6, List<AnnotationToken> list, Map<Integer, List<AnnotationToken>> map7, List<ParameterToken> list2, @MaybeNull AnnotationValue<?, ?> annotationValue) {
                    this.modifiers = (-131073) & i;
                    this.f390name = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.signatureResolution = TypeDescription.AbstractBase.RAW_TYPES ? GenericTypeToken.Resolution.Raw.INSTANCE : GenericTypeExtractor.ForSignature.OfMethod.extract(str3);
                    this.exceptionName = strArr;
                    this.typeVariableAnnotationTokens = map;
                    this.typeVariableBoundAnnotationTokens = map2;
                    this.returnTypeAnnotationTokens = map3;
                    this.parameterTypeAnnotationTokens = map4;
                    this.exceptionTypeAnnotationTokens = map5;
                    this.receiverTypeAnnotationTokens = map6;
                    this.annotationTokens = list;
                    this.parameterAnnotationTokens = map7;
                    this.parameterTokens = list2;
                    this.defaultValue = annotationValue;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public MethodDescription.InDefinedShape toMethodDescription(LazyTypeDescription lazyTypeDescription) {
                    lazyTypeDescription.getClass();
                    return new LazyMethodDescription(this.f390name, this.modifiers, this.descriptor, this.genericSignature, this.signatureResolution, this.exceptionName, this.typeVariableAnnotationTokens, this.typeVariableBoundAnnotationTokens, this.returnTypeAnnotationTokens, this.parameterTypeAnnotationTokens, this.exceptionTypeAnnotationTokens, this.receiverTypeAnnotationTokens, this.annotationTokens, this.parameterAnnotationTokens, this.parameterTokens, this.defaultValue);
                }

                @HashCodeAndEqualsPlugin.Enhance
                protected static class ParameterToken {

                    @AlwaysNull
                    protected static final Integer NO_MODIFIERS = null;

                    @AlwaysNull
                    protected static final String NO_NAME = null;

                    @MaybeNull
                    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                    private final Integer modifiers;

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    @MaybeNull
                    @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
                    private final String f391name;

                    /* JADX WARN: Code duplicated, block: B:27:0x0039 A[RETURN] */
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
                            java.lang.Integer r2 = r4.modifiers
                            net.bytebuddy.pool.TypePool$Default$LazyTypeDescription$MethodToken$ParameterToken r5 = (net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.MethodToken.ParameterToken) r5
                            java.lang.Integer r3 = r5.modifiers
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
                            java.lang.String r2 = r4.f391name
                            java.lang.String r5 = r5.f391name
                            if (r5 == 0) goto L36
                            if (r2 == 0) goto L38
                            boolean r5 = r2.equals(r5)
                            if (r5 != 0) goto L39
                            return r1
                        L36:
                            if (r2 == 0) goto L39
                        L38:
                            return r1
                        L39:
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.MethodToken.ParameterToken.equals(java.lang.Object):boolean");
                    }

                    public int hashCode() {
                        int iHashCode = getClass().hashCode() * 31;
                        String str = this.f391name;
                        if (str != null) {
                            iHashCode += str.hashCode();
                        }
                        int i = iHashCode * 31;
                        Integer num = this.modifiers;
                        return num != null ? i + num.hashCode() : i;
                    }

                    protected ParameterToken() {
                        this(NO_NAME);
                    }

                    protected ParameterToken(@MaybeNull String str) {
                        this(str, NO_MODIFIERS);
                    }

                    protected ParameterToken(@MaybeNull String str, @MaybeNull Integer num) {
                        this.f391name = str;
                        this.modifiers = num;
                    }

                    @MaybeNull
                    protected String getName() {
                        return this.f391name;
                    }

                    @MaybeNull
                    protected Integer getModifiers() {
                        return this.modifiers;
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class RecordComponentToken {
                private final List<AnnotationToken> annotationTokens;
                private final String descriptor;

                @UnknownNull
                private final String genericSignature;

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f392name;
                private final GenericTypeToken.Resolution.ForRecordComponent signatureResolution;
                private final Map<String, List<AnnotationToken>> typeAnnotationTokens;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    RecordComponentToken recordComponentToken = (RecordComponentToken) obj;
                    return this.f392name.equals(recordComponentToken.f392name) && this.descriptor.equals(recordComponentToken.descriptor) && this.genericSignature.equals(recordComponentToken.genericSignature) && this.signatureResolution.equals(recordComponentToken.signatureResolution) && this.typeAnnotationTokens.equals(recordComponentToken.typeAnnotationTokens) && this.annotationTokens.equals(recordComponentToken.annotationTokens);
                }

                public int hashCode() {
                    return (((((((((((getClass().hashCode() * 31) + this.f392name.hashCode()) * 31) + this.descriptor.hashCode()) * 31) + this.genericSignature.hashCode()) * 31) + this.signatureResolution.hashCode()) * 31) + this.typeAnnotationTokens.hashCode()) * 31) + this.annotationTokens.hashCode();
                }

                protected RecordComponentToken(String str, String str2, @MaybeNull String str3, Map<String, List<AnnotationToken>> map, List<AnnotationToken> list) {
                    this.f392name = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.signatureResolution = TypeDescription.AbstractBase.RAW_TYPES ? GenericTypeToken.Resolution.Raw.INSTANCE : GenericTypeExtractor.ForSignature.OfRecordComponent.extract(str3);
                    this.typeAnnotationTokens = map;
                    this.annotationTokens = list;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public RecordComponentDescription.InDefinedShape toRecordComponentDescription(LazyTypeDescription lazyTypeDescription) {
                    lazyTypeDescription.getClass();
                    return new LazyRecordComponentDescription(this.f392name, this.descriptor, this.genericSignature, this.signatureResolution, this.typeAnnotationTokens, this.annotationTokens);
                }
            }

            private static class LazyAnnotationDescription extends AnnotationDescription.AbstractBase {
                private final TypeDescription annotationType;
                protected final TypePool typePool;
                protected final Map<String, AnnotationValue<?, ?>> values;

                private LazyAnnotationDescription(TypePool typePool, TypeDescription typeDescription, Map<String, AnnotationValue<?, ?>> map) {
                    this.typePool = typePool;
                    this.annotationType = typeDescription;
                    this.values = map;
                }

                protected static AnnotationList asListOfNullable(TypePool typePool, @MaybeNull List<? extends AnnotationToken> list) {
                    if (list == null) {
                        return new AnnotationList.Empty();
                    }
                    return asList(typePool, list);
                }

                protected static AnnotationList asList(TypePool typePool, List<? extends AnnotationToken> list) {
                    ArrayList arrayList = new ArrayList(list.size());
                    Iterator<? extends AnnotationToken> it = list.iterator();
                    while (it.hasNext()) {
                        AnnotationToken.Resolution annotationDescription = it.next().toAnnotationDescription(typePool);
                        if (annotationDescription.isResolved() && annotationDescription.resolve().getAnnotationType().isAnnotation()) {
                            arrayList.add(annotationDescription.resolve());
                        }
                    }
                    return new UnresolvedAnnotationList(arrayList, list);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationDescription
                public AnnotationValue<?, ?> getValue(MethodDescription.InDefinedShape inDefinedShape) {
                    if (!inDefinedShape.getDeclaringType().asErasure().equals(this.annotationType)) {
                        throw new IllegalArgumentException(inDefinedShape + " is not declared by " + getAnnotationType());
                    }
                    AnnotationValue<?, ?> annotationValue = this.values.get(inDefinedShape.getName());
                    if (annotationValue != null) {
                        return annotationValue.filter(inDefinedShape);
                    }
                    AnnotationValue<?, ?> defaultValue = ((MethodDescription.InDefinedShape) getAnnotationType().getDeclaredMethods().filter(ElementMatchers.is(inDefinedShape)).getOnly()).getDefaultValue();
                    return defaultValue == null ? new AnnotationValue.ForMissingValue(this.annotationType, inDefinedShape.getName()) : defaultValue;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationDescription
                public TypeDescription getAnnotationType() {
                    return this.annotationType;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationDescription
                public <T extends Annotation> Loadable<T> prepare(Class<T> cls) {
                    if (!this.annotationType.represents(cls)) {
                        throw new IllegalArgumentException(cls + " does not represent " + this.annotationType);
                    }
                    return new Loadable<>(this.typePool, cls, this.values);
                }

                private static class Loadable<S extends Annotation> extends LazyAnnotationDescription implements AnnotationDescription.Loadable<S> {
                    private final Class<S> annotationType;

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.LazyAnnotationDescription, net.bytebuddy.description.annotation.AnnotationDescription
                    public /* bridge */ /* synthetic */ AnnotationDescription.Loadable prepare(Class cls) {
                        return super.prepare(cls);
                    }

                    private Loadable(TypePool typePool, Class<S> cls, Map<String, AnnotationValue<?, ?>> map) {
                        super(typePool, TypeDescription.ForLoadedType.of(cls), map);
                        this.annotationType = cls;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationDescription.Loadable
                    public S load() {
                        return (S) AnnotationDescription.AnnotationInvocationHandler.of(this.annotationType.getClassLoader(), this.annotationType, this.values);
                    }
                }

                private static class UnresolvedAnnotationList extends AnnotationList.Explicit {
                    private final List<? extends AnnotationToken> tokens;

                    private UnresolvedAnnotationList(List<? extends AnnotationDescription> list, List<? extends AnnotationToken> list2) {
                        super(list);
                        this.tokens = list2;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationList.AbstractBase, net.bytebuddy.description.annotation.AnnotationList
                    public List<String> asTypeNames() {
                        ArrayList arrayList = new ArrayList(this.tokens.size());
                        Iterator<? extends AnnotationToken> it = this.tokens.iterator();
                        while (it.hasNext()) {
                            arrayList.add(it.next().getBinaryName());
                        }
                        return arrayList;
                    }
                }
            }

            private static abstract class LazyAnnotationValue<U, V> extends AnnotationValue.AbstractBase<U, V> {
                private transient /* synthetic */ int hashCode;

                protected abstract AnnotationValue<U, V> doResolve();

                private LazyAnnotationValue() {
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue
                public AnnotationValue.State getState() {
                    return doResolve().getState();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue
                public AnnotationValue<U, V> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
                    return doResolve().filter(inDefinedShape, typeDefinition);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue
                public U resolve() {
                    return doResolve().resolve();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationValue
                public AnnotationValue.Loaded<V> load(@MaybeNull ClassLoader classLoader) {
                    return doResolve().load(classLoader);
                }

                @CachedReturnPlugin.Enhance("hashCode")
                public int hashCode() {
                    int iHashCode = this.hashCode != 0 ? 0 : doResolve().hashCode();
                    if (iHashCode == 0) {
                        return this.hashCode;
                    }
                    this.hashCode = iHashCode;
                    return iHashCode;
                }

                public boolean equals(@MaybeNull Object obj) {
                    return doResolve().equals(obj);
                }

                public String toString() {
                    return doResolve().toString();
                }

                @HashCodeAndEqualsPlugin.Enhance
                private static class ForMismatchedType<W, X> extends AnnotationValue.AbstractBase<W, X> {
                    private final AnnotationValue.Sort sort;
                    private final String value;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        ForMismatchedType forMismatchedType = (ForMismatchedType) obj;
                        return this.sort.equals(forMismatchedType.sort) && this.value.equals(forMismatchedType.value);
                    }

                    public int hashCode() {
                        return (((getClass().hashCode() * 31) + this.value.hashCode()) * 31) + this.sort.hashCode();
                    }

                    private ForMismatchedType(String str, AnnotationValue.Sort sort) {
                        this.value = str;
                        this.sort = sort;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue.State getState() {
                        return AnnotationValue.State.UNRESOLVED;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue.Sort getSort() {
                        return AnnotationValue.Sort.NONE;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue<W, X> filter(MethodDescription.InDefinedShape inDefinedShape, TypeDefinition typeDefinition) {
                        return new AnnotationValue.ForMismatchedType(inDefinedShape, inDefinedShape.getReturnType().isArray() ? AnnotationValue.RenderingDispatcher.CURRENT.toArrayErrorString(this.sort) : this.value);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public W resolve() {
                        throw new IllegalStateException("Expected filtering of this unresolved property");
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue.Loaded<X> load(@MaybeNull ClassLoader classLoader) {
                        throw new IllegalStateException("Expected filtering of this unresolved property");
                    }
                }

                private static class ForTypeValue extends LazyAnnotationValue<TypeDescription, Class<?>> {
                    private transient /* synthetic */ AnnotationValue resolved;
                    private final String typeName;
                    private final TypePool typePool;

                    private ForTypeValue(TypePool typePool, String str) {
                        super();
                        this.typePool = typePool;
                        this.typeName = str;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue.Sort getSort() {
                        return AnnotationValue.Sort.TYPE;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.LazyAnnotationValue
                    @CachedReturnPlugin.Enhance("resolved")
                    protected AnnotationValue<TypeDescription, Class<?>> doResolve() {
                        AnnotationValue<TypeDescription, Class<?>> forTypeDescription;
                        if (this.resolved != null) {
                            forTypeDescription = null;
                        } else {
                            Resolution resolutionDescribe = this.typePool.describe(this.typeName);
                            forTypeDescription = resolutionDescribe.isResolved() ? new AnnotationValue.ForTypeDescription(resolutionDescribe.resolve()) : new AnnotationValue.ForMissingType<>(this.typeName);
                        }
                        if (forTypeDescription == null) {
                            return this.resolved;
                        }
                        this.resolved = forTypeDescription;
                        return forTypeDescription;
                    }
                }

                private static class ForAnnotationValue extends LazyAnnotationValue<AnnotationDescription, Annotation> {
                    private final AnnotationToken annotationToken;
                    private transient /* synthetic */ AnnotationValue resolved;
                    private final TypePool typePool;

                    private ForAnnotationValue(TypePool typePool, AnnotationToken annotationToken) {
                        super();
                        this.typePool = typePool;
                        this.annotationToken = annotationToken;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue.Sort getSort() {
                        return AnnotationValue.Sort.ANNOTATION;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.LazyAnnotationValue
                    @CachedReturnPlugin.Enhance("resolved")
                    protected AnnotationValue<AnnotationDescription, Annotation> doResolve() {
                        AnnotationValue<AnnotationDescription, Annotation> forAnnotationDescription = null;
                        Object[] objArr = 0;
                        if (this.resolved == null) {
                            AnnotationToken.Resolution annotationDescription = this.annotationToken.toAnnotationDescription(this.typePool);
                            if (!annotationDescription.isResolved()) {
                                forAnnotationDescription = new AnnotationValue.ForMissingType<>(this.annotationToken.getBinaryName());
                            } else if (!annotationDescription.resolve().getAnnotationType().isAnnotation()) {
                                forAnnotationDescription = new ForMismatchedType(annotationDescription.resolve().getAnnotationType().getName(), AnnotationValue.Sort.ANNOTATION);
                            } else {
                                forAnnotationDescription = new AnnotationValue.ForAnnotationDescription<>(annotationDescription.resolve());
                            }
                        }
                        if (forAnnotationDescription == null) {
                            return this.resolved;
                        }
                        this.resolved = forAnnotationDescription;
                        return forAnnotationDescription;
                    }
                }

                private static class ForEnumerationValue extends LazyAnnotationValue<EnumerationDescription, Enum<?>> {
                    private transient /* synthetic */ AnnotationValue resolved;
                    private final String typeName;
                    private final TypePool typePool;
                    private final String value;

                    private ForEnumerationValue(TypePool typePool, String str, String str2) {
                        super();
                        this.typePool = typePool;
                        this.typeName = str;
                        this.value = str2;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue.Sort getSort() {
                        return AnnotationValue.Sort.ENUMERATION;
                    }

                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.LazyAnnotationValue
                    @CachedReturnPlugin.Enhance("resolved")
                    protected AnnotationValue<EnumerationDescription, Enum<?>> doResolve() {
                        AnnotationValue<EnumerationDescription, Enum<?>> forEnumerationDescription = null;
                        Object[] objArr = 0;
                        if (this.resolved == null) {
                            Resolution resolutionDescribe = this.typePool.describe(this.typeName);
                            if (!resolutionDescribe.isResolved()) {
                                forEnumerationDescription = new AnnotationValue.ForMissingType<>(this.typeName);
                            } else if (!resolutionDescribe.resolve().isEnum()) {
                                forEnumerationDescription = new ForMismatchedType(this.typeName + "." + this.value, AnnotationValue.Sort.ENUMERATION);
                            } else if (resolutionDescribe.resolve().getDeclaredFields().filter(ElementMatchers.named(this.value)).isEmpty()) {
                                forEnumerationDescription = new AnnotationValue.ForEnumerationDescription.WithUnknownConstant<>(resolutionDescribe.resolve(), this.value);
                            } else {
                                forEnumerationDescription = new AnnotationValue.ForEnumerationDescription<>(new EnumerationDescription.Latent(resolutionDescribe.resolve(), this.value));
                            }
                        }
                        if (forEnumerationDescription == null) {
                            return this.resolved;
                        }
                        this.resolved = forEnumerationDescription;
                        return forEnumerationDescription;
                    }
                }

                private static class ForArray extends LazyAnnotationValue<Object, Object> {
                    private final AbstractBase.ComponentTypeReference componentTypeReference;
                    private transient /* synthetic */ AnnotationValue resolved;
                    private final TypePool typePool;
                    private final List<AnnotationValue<?, ?>> values;

                    private ForArray(TypePool typePool, AbstractBase.ComponentTypeReference componentTypeReference, List<AnnotationValue<?, ?>> list) {
                        super();
                        this.typePool = typePool;
                        this.componentTypeReference = componentTypeReference;
                        this.values = list;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationValue
                    public AnnotationValue.Sort getSort() {
                        return AnnotationValue.Sort.ARRAY;
                    }

                    /* JADX WARN: Code duplicated, block: B:46:0x015f  */
                    /* JADX WARN: Multi-variable type inference failed */
                    @Override // net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.LazyAnnotationValue
                    @CachedReturnPlugin.Enhance("resolved")
                    protected AnnotationValue<Object, Object> doResolve() {
                        AnnotationValue.Sort sort;
                        ListIterator<AnnotationValue<?, ?>> listIterator;
                        AnnotationValue<Object, Object> forMismatchedType = null;
                        Object[] objArr = 0;
                        if (this.resolved == null) {
                            String strResolve = this.componentTypeReference.resolve();
                            if (strResolve != null) {
                                Resolution resolutionDescribe = this.typePool.describe(strResolve);
                                if (!resolutionDescribe.isResolved()) {
                                    forMismatchedType = new AnnotationValue.ForMissingType<>(strResolve);
                                } else if (resolutionDescribe.resolve().isEnum()) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(EnumerationDescription.class, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().isAnnotation()) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(AnnotationDescription.class, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Class.class)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(TypeDescription.class, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(String.class)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(String.class, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Boolean.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Boolean.TYPE, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Byte.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Byte.TYPE, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Short.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Short.TYPE, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Character.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Character.TYPE, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Integer.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Integer.TYPE, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Long.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Long.TYPE, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Float.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Float.TYPE, resolutionDescribe.resolve(), this.values);
                                } else if (resolutionDescribe.resolve().represents(Double.TYPE)) {
                                    forMismatchedType = new AnnotationValue.ForDescriptionArray<>(Double.TYPE, resolutionDescribe.resolve(), this.values);
                                } else {
                                    sort = AnnotationValue.Sort.NONE;
                                    List<AnnotationValue<?, ?>> list = this.values;
                                    listIterator = list.listIterator(list.size());
                                    while (listIterator.hasPrevious() && !sort.isDefined()) {
                                        sort = listIterator.previous().getSort();
                                    }
                                    forMismatchedType = new ForMismatchedType(AnnotationValue.RenderingDispatcher.CURRENT.toArrayErrorString(sort), sort);
                                }
                            } else {
                                sort = AnnotationValue.Sort.NONE;
                                List<AnnotationValue<?, ?>> list2 = this.values;
                                listIterator = list2.listIterator(list2.size());
                                while (listIterator.hasPrevious()) {
                                    sort = listIterator.previous().getSort();
                                }
                                forMismatchedType = new ForMismatchedType(AnnotationValue.RenderingDispatcher.CURRENT.toArrayErrorString(sort), sort);
                            }
                        }
                        if (forMismatchedType == null) {
                            return this.resolved;
                        }
                        this.resolved = forMismatchedType;
                        return forMismatchedType;
                    }
                }
            }

            private static class LazyPackageDescription extends PackageDescription.AbstractBase {

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f388name;
                private final TypePool typePool;

                private LazyPackageDescription(TypePool typePool, String str) {
                    this.typePool = typePool;
                    this.f388name = str;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    Resolution resolutionDescribe = this.typePool.describe(this.f388name + ".package-info");
                    return resolutionDescribe.isResolved() ? resolutionDescribe.resolve().getDeclaredAnnotations() : new AnnotationList.Empty();
                }

                @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
                public String getName() {
                    return this.f388name;
                }
            }

            protected static class LazyTypeList extends TypeList.AbstractBase {
                private final List<String> descriptors;
                private final TypePool typePool;

                protected LazyTypeList(TypePool typePool, List<String> list) {
                    this.typePool = typePool;
                    this.descriptors = list;
                }

                @Override // java.util.AbstractList, java.util.List
                public TypeDescription get(int i) {
                    return TokenizedGenericType.toErasure(this.typePool, this.descriptors.get(i));
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return this.descriptors.size();
                }

                @Override // net.bytebuddy.description.type.TypeList.AbstractBase, net.bytebuddy.description.type.TypeList
                @MaybeNull
                public String[] toInternalNames() {
                    int size = this.descriptors.size();
                    String[] strArr = new String[size];
                    Iterator<String> it = this.descriptors.iterator();
                    int i = 0;
                    while (it.hasNext()) {
                        strArr[i] = Type.getType(it.next()).getInternalName();
                        i++;
                    }
                    return size == 0 ? NO_INTERFACES : strArr;
                }

                @Override // net.bytebuddy.description.type.TypeList.AbstractBase, net.bytebuddy.description.type.TypeList
                public int getStackSize() {
                    Iterator<String> it = this.descriptors.iterator();
                    int size = 0;
                    while (it.hasNext()) {
                        size += Type.getType(it.next()).getSize();
                    }
                    return size;
                }
            }

            protected static class LazyNestMemberList extends TypeList.AbstractBase {
                private final List<String> nestMembers;
                private final TypeDescription typeDescription;
                private final TypePool typePool;

                protected LazyNestMemberList(TypeDescription typeDescription, TypePool typePool, List<String> list) {
                    this.typeDescription = typeDescription;
                    this.typePool = typePool;
                    this.nestMembers = list;
                }

                @Override // java.util.AbstractList, java.util.List
                public TypeDescription get(int i) {
                    if (i == 0) {
                        return this.typeDescription;
                    }
                    return this.typePool.describe(this.nestMembers.get(i - 1)).resolve();
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public int size() {
                    return this.nestMembers.size() + 1;
                }

                @Override // net.bytebuddy.description.type.TypeList.AbstractBase, net.bytebuddy.description.type.TypeList
                public String[] toInternalNames() {
                    int i = 1;
                    String[] strArr = new String[this.nestMembers.size() + 1];
                    strArr[0] = this.typeDescription.getInternalName();
                    Iterator<String> it = this.nestMembers.iterator();
                    while (it.hasNext()) {
                        strArr[i] = it.next().replace('.', '/');
                        i++;
                    }
                    return strArr;
                }

                @Override // net.bytebuddy.description.type.TypeList.AbstractBase, net.bytebuddy.description.type.TypeList
                public int getStackSize() {
                    return this.nestMembers.size() + 1;
                }
            }

            protected static class TokenizedGenericType extends TypeDescription.Generic.LazyProjection.WithEagerNavigation {
                private final Map<String, List<AnnotationToken>> annotationTokens;
                private transient /* synthetic */ TypeDescription erasure;
                private final GenericTypeToken genericTypeToken;
                private final String rawTypeDescriptor;
                private transient /* synthetic */ TypeDescription.Generic resolved;
                private final TypePool typePool;
                private final TypeVariableSource typeVariableSource;

                protected TokenizedGenericType(TypePool typePool, GenericTypeToken genericTypeToken, String str, Map<String, List<AnnotationToken>> map, TypeVariableSource typeVariableSource) {
                    this.typePool = typePool;
                    this.genericTypeToken = genericTypeToken;
                    this.rawTypeDescriptor = str;
                    this.annotationTokens = map;
                    this.typeVariableSource = typeVariableSource;
                }

                protected static TypeDescription.Generic of(TypePool typePool, GenericTypeToken genericTypeToken, String str, @MaybeNull Map<String, List<AnnotationToken>> map, TypeVariableSource typeVariableSource) {
                    if (map == null) {
                        map = Collections.emptyMap();
                    }
                    return new TokenizedGenericType(typePool, genericTypeToken, str, map, typeVariableSource);
                }

                protected static TypeDescription toErasure(TypePool typePool, String str) {
                    String className;
                    Type type = Type.getType(str);
                    if (type.getSort() == 9) {
                        className = type.getInternalName().replace('/', '.');
                    } else {
                        className = type.getClassName();
                    }
                    return typePool.describe(className).resolve();
                }

                @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                @CachedReturnPlugin.Enhance("resolved")
                protected TypeDescription.Generic resolve() {
                    TypeDescription.Generic genericType = this.resolved != null ? null : this.genericTypeToken.toGenericType(this.typePool, this.typeVariableSource, "", this.annotationTokens);
                    if (genericType == null) {
                        return this.resolved;
                    }
                    this.resolved = genericType;
                    return genericType;
                }

                @Override // net.bytebuddy.description.type.TypeDefinition
                @CachedReturnPlugin.Enhance("erasure")
                public TypeDescription asErasure() {
                    TypeDescription erasure = this.erasure != null ? null : toErasure(this.typePool, this.rawTypeDescriptor);
                    if (erasure == null) {
                        return this.erasure;
                    }
                    this.erasure = erasure;
                    return erasure;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return resolve().getDeclaredAnnotations();
                }

                protected static class TokenList extends TypeList.Generic.AbstractBase {
                    private final Map<Integer, Map<String, List<AnnotationToken>>> annotationTokens;
                    private final List<GenericTypeToken> genericTypeTokens;
                    private final List<String> rawTypeDescriptors;
                    private final TypePool typePool;
                    private final TypeVariableSource typeVariableSource;

                    private TokenList(TypePool typePool, List<GenericTypeToken> list, Map<Integer, Map<String, List<AnnotationToken>>> map, List<String> list2, TypeVariableSource typeVariableSource) {
                        this.typePool = typePool;
                        this.genericTypeTokens = list;
                        this.annotationTokens = map;
                        this.rawTypeDescriptors = list2;
                        this.typeVariableSource = typeVariableSource;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public TypeDescription.Generic get(int i) {
                        if (this.rawTypeDescriptors.size() == this.genericTypeTokens.size()) {
                            return TokenizedGenericType.of(this.typePool, this.genericTypeTokens.get(i), this.rawTypeDescriptors.get(i), this.annotationTokens.get(Integer.valueOf(i)), this.typeVariableSource);
                        }
                        return TokenizedGenericType.toErasure(this.typePool, this.rawTypeDescriptors.get(i)).asGenericType();
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.rawTypeDescriptors.size();
                    }

                    @Override // net.bytebuddy.description.type.TypeList.Generic.AbstractBase, net.bytebuddy.description.type.TypeList.Generic
                    public TypeList asErasures() {
                        return new LazyTypeList(this.typePool, this.rawTypeDescriptors);
                    }
                }

                protected static class TypeVariableList extends TypeList.Generic.AbstractBase {
                    private final Map<Integer, Map<String, List<AnnotationToken>>> annotationTokens;
                    private final Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> boundAnnotationTokens;
                    private final TypePool typePool;
                    private final TypeVariableSource typeVariableSource;
                    private final List<GenericTypeToken.OfFormalTypeVariable> typeVariables;

                    protected TypeVariableList(TypePool typePool, List<GenericTypeToken.OfFormalTypeVariable> list, TypeVariableSource typeVariableSource, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2) {
                        this.typePool = typePool;
                        this.typeVariables = list;
                        this.typeVariableSource = typeVariableSource;
                        this.annotationTokens = map;
                        this.boundAnnotationTokens = map2;
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public TypeDescription.Generic get(int i) {
                        return this.typeVariables.get(i).toGenericType(this.typePool, this.typeVariableSource, this.annotationTokens.get(Integer.valueOf(i)), this.boundAnnotationTokens.get(Integer.valueOf(i)));
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return this.typeVariables.size();
                    }
                }

                protected static class Malformed extends TypeDescription.Generic.LazyProjection.WithEagerNavigation {
                    private final String rawTypeDescriptor;
                    private final TypePool typePool;

                    protected Malformed(TypePool typePool, String str) {
                        this.typePool = typePool;
                        this.rawTypeDescriptor = str;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic.LazyProjection
                    protected TypeDescription.Generic resolve() {
                        throw new GenericSignatureFormatError();
                    }

                    @Override // net.bytebuddy.description.type.TypeDefinition
                    public TypeDescription asErasure() {
                        return TokenizedGenericType.toErasure(this.typePool, this.rawTypeDescriptor);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        throw new GenericSignatureFormatError();
                    }

                    protected static class TokenList extends TypeList.Generic.AbstractBase {
                        private final List<String> rawTypeDescriptors;
                        private final TypePool typePool;

                        protected TokenList(TypePool typePool, List<String> list) {
                            this.typePool = typePool;
                            this.rawTypeDescriptors = list;
                        }

                        @Override // java.util.AbstractList, java.util.List
                        public TypeDescription.Generic get(int i) {
                            return new Malformed(this.typePool, this.rawTypeDescriptors.get(i));
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                        public int size() {
                            return this.rawTypeDescriptors.size();
                        }

                        @Override // net.bytebuddy.description.type.TypeList.Generic.AbstractBase, net.bytebuddy.description.type.TypeList.Generic
                        public TypeList asErasures() {
                            return new LazyTypeList(this.typePool, this.rawTypeDescriptors);
                        }
                    }
                }
            }

            private class LazyFieldDescription extends FieldDescription.InDefinedShape.AbstractBase {
                private final List<AnnotationToken> annotationTokens;
                private final String descriptor;

                @MaybeNull
                private final String genericSignature;
                private final int modifiers;

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f387name;
                private final GenericTypeToken.Resolution.ForField signatureResolution;
                private final Map<String, List<AnnotationToken>> typeAnnotationTokens;

                private LazyFieldDescription(String str, int i, @MaybeNull String str2, String str3, GenericTypeToken.Resolution.ForField forField, Map<String, List<AnnotationToken>> map, List<AnnotationToken> list) {
                    this.modifiers = i;
                    this.f387name = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.signatureResolution = forField;
                    this.typeAnnotationTokens = map;
                    this.annotationTokens = list;
                }

                @Override // net.bytebuddy.description.field.FieldDescription
                public TypeDescription.Generic getType() {
                    return this.signatureResolution.resolveFieldType(this.descriptor, LazyTypeDescription.this.typePool, this.typeAnnotationTokens, this);
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return LazyAnnotationDescription.asListOfNullable(LazyTypeDescription.this.typePool, this.annotationTokens);
                }

                @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
                public String getName() {
                    return this.f387name;
                }

                @Override // net.bytebuddy.description.field.FieldDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
                @Nonnull
                public TypeDescription getDeclaringType() {
                    return LazyTypeDescription.this;
                }

                @Override // net.bytebuddy.description.ModifierReviewable
                public int getModifiers() {
                    return this.modifiers;
                }

                @Override // net.bytebuddy.description.field.FieldDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
                @MaybeNull
                public String getGenericSignature() {
                    return this.genericSignature;
                }
            }

            private class LazyMethodDescription extends MethodDescription.InDefinedShape.AbstractBase {
                private final List<AnnotationToken> annotationTokens;

                @MaybeNull
                private final AnnotationValue<?, ?> defaultValue;
                private final Map<Integer, Map<String, List<AnnotationToken>>> exceptionTypeAnnotationTokens;
                private final List<String> exceptionTypeDescriptors;

                @MaybeNull
                private final String genericSignature;
                private final String internalName;
                private final int modifiers;
                private final Map<Integer, List<AnnotationToken>> parameterAnnotationTokens;
                private final Integer[] parameterModifiers;
                private final String[] parameterNames;
                private final Map<Integer, Map<String, List<AnnotationToken>>> parameterTypeAnnotationTokens;
                private final List<String> parameterTypeDescriptors;
                private final Map<String, List<AnnotationToken>> receiverTypeAnnotationTokens;
                private final Map<String, List<AnnotationToken>> returnTypeAnnotationTokens;
                private final String returnTypeDescriptor;
                private final GenericTypeToken.Resolution.ForMethod signatureResolution;
                private final Map<Integer, Map<String, List<AnnotationToken>>> typeVariableAnnotationTokens;
                private final Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> typeVariableBoundAnnotationTokens;

                private LazyMethodDescription(String str, int i, @MaybeNull String str2, String str3, @MaybeNull GenericTypeToken.Resolution.ForMethod forMethod, String[] strArr, Map<Integer, Map<String, List<AnnotationToken>>> map, Map<Integer, Map<Integer, Map<String, List<AnnotationToken>>>> map2, Map<String, List<AnnotationToken>> map3, Map<Integer, Map<String, List<AnnotationToken>>> map4, Map<Integer, Map<String, List<AnnotationToken>>> map5, Map<String, List<AnnotationToken>> map6, List<AnnotationToken> list, Map<Integer, List<AnnotationToken>> map7, @MaybeNull List<MethodToken.ParameterToken> list2, AnnotationValue<?, ?> annotationValue) {
                    this.modifiers = i;
                    this.internalName = str;
                    Type methodType = Type.getMethodType(str2);
                    Type returnType = methodType.getReturnType();
                    Type[] argumentTypes = methodType.getArgumentTypes();
                    this.returnTypeDescriptor = returnType.getDescriptor();
                    this.parameterTypeDescriptors = new ArrayList(argumentTypes.length);
                    int i2 = 0;
                    for (Type type : argumentTypes) {
                        this.parameterTypeDescriptors.add(type.getDescriptor());
                    }
                    this.genericSignature = str3;
                    this.signatureResolution = forMethod;
                    if (strArr == null) {
                        this.exceptionTypeDescriptors = Collections.emptyList();
                    } else {
                        this.exceptionTypeDescriptors = new ArrayList(strArr.length);
                        for (String str4 : strArr) {
                            this.exceptionTypeDescriptors.add(Type.getObjectType(str4).getDescriptor());
                        }
                    }
                    this.typeVariableAnnotationTokens = map;
                    this.typeVariableBoundAnnotationTokens = map2;
                    this.returnTypeAnnotationTokens = map3;
                    this.parameterTypeAnnotationTokens = map4;
                    this.exceptionTypeAnnotationTokens = map5;
                    this.receiverTypeAnnotationTokens = map6;
                    this.annotationTokens = list;
                    this.parameterAnnotationTokens = map7;
                    this.parameterNames = new String[argumentTypes.length];
                    this.parameterModifiers = new Integer[argumentTypes.length];
                    if (list2.size() == argumentTypes.length) {
                        for (MethodToken.ParameterToken parameterToken : list2) {
                            this.parameterNames[i2] = parameterToken.getName();
                            this.parameterModifiers[i2] = parameterToken.getModifiers();
                            i2++;
                        }
                    }
                    this.defaultValue = annotationValue;
                }

                @Override // net.bytebuddy.description.method.MethodDescription
                public TypeDescription.Generic getReturnType() {
                    return this.signatureResolution.resolveReturnType(this.returnTypeDescriptor, LazyTypeDescription.this.typePool, this.returnTypeAnnotationTokens, this);
                }

                @Override // net.bytebuddy.description.method.MethodDescription
                public TypeList.Generic getExceptionTypes() {
                    return this.signatureResolution.resolveExceptionTypes(this.exceptionTypeDescriptors, LazyTypeDescription.this.typePool, this.exceptionTypeAnnotationTokens, this);
                }

                @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.method.MethodDescription.InDefinedShape
                public ParameterList<ParameterDescription.InDefinedShape> getParameters() {
                    return new LazyParameterList();
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return LazyAnnotationDescription.asList(LazyTypeDescription.this.typePool, this.annotationTokens);
                }

                @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
                public String getInternalName() {
                    return this.internalName;
                }

                @Override // net.bytebuddy.description.method.MethodDescription, net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
                @Nonnull
                public TypeDescription getDeclaringType() {
                    return LazyTypeDescription.this;
                }

                @Override // net.bytebuddy.description.ModifierReviewable
                public int getModifiers() {
                    return this.modifiers;
                }

                @Override // net.bytebuddy.description.TypeVariableSource
                public TypeList.Generic getTypeVariables() {
                    return this.signatureResolution.resolveTypeVariables(LazyTypeDescription.this.typePool, this, this.typeVariableAnnotationTokens, this.typeVariableBoundAnnotationTokens);
                }

                @Override // net.bytebuddy.description.method.MethodDescription
                @MaybeNull
                public AnnotationValue<?, ?> getDefaultValue() {
                    return this.defaultValue;
                }

                @Override // net.bytebuddy.description.method.MethodDescription.InDefinedShape.AbstractBase, net.bytebuddy.description.method.MethodDescription
                @MaybeNull
                public TypeDescription.Generic getReceiverType() {
                    if (isStatic()) {
                        return TypeDescription.Generic.UNDEFINED;
                    }
                    if (!isConstructor()) {
                        return LazyTypeDescription.this.isGenerified() ? new LazyParameterizedReceiverType(this) : new LazyNonGenericReceiverType(this);
                    }
                    TypeDescription declaringType = getDeclaringType();
                    TypeDescription enclosingType = declaringType.getEnclosingType();
                    if (enclosingType == null) {
                        return declaringType.isGenerified() ? new LazyParameterizedReceiverType(declaringType) : new LazyNonGenericReceiverType(declaringType);
                    }
                    return (declaringType.isStatic() || !declaringType.isGenerified()) ? new LazyNonGenericReceiverType(enclosingType) : new LazyParameterizedReceiverType(enclosingType);
                }

                @Override // net.bytebuddy.description.method.MethodDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
                @MaybeNull
                public String getGenericSignature() {
                    return this.genericSignature;
                }

                private class LazyParameterList extends ParameterList.AbstractBase<ParameterDescription.InDefinedShape> {
                    private LazyParameterList() {
                    }

                    @Override // java.util.AbstractList, java.util.List
                    public ParameterDescription.InDefinedShape get(int i) {
                        return LazyMethodDescription.this.new LazyParameterDescription(i);
                    }

                    @Override // net.bytebuddy.description.method.ParameterList.AbstractBase, net.bytebuddy.description.method.ParameterList
                    public boolean hasExplicitMetaData() {
                        for (int i = 0; i < size(); i++) {
                            if (LazyMethodDescription.this.parameterNames[i] == null || LazyMethodDescription.this.parameterModifiers[i] == null) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                    public int size() {
                        return LazyMethodDescription.this.parameterTypeDescriptors.size();
                    }

                    @Override // net.bytebuddy.description.method.ParameterList.AbstractBase, net.bytebuddy.description.method.ParameterList
                    public TypeList.Generic asTypeList() {
                        return LazyMethodDescription.this.signatureResolution.resolveParameterTypes(LazyMethodDescription.this.parameterTypeDescriptors, LazyTypeDescription.this.typePool, LazyMethodDescription.this.parameterTypeAnnotationTokens, LazyMethodDescription.this);
                    }
                }

                private class LazyParameterDescription extends ParameterDescription.InDefinedShape.AbstractBase {
                    private final int index;

                    protected LazyParameterDescription(int i) {
                        this.index = i;
                    }

                    @Override // net.bytebuddy.description.method.ParameterDescription, net.bytebuddy.description.method.ParameterDescription.InDefinedShape
                    public MethodDescription.InDefinedShape getDeclaringMethod() {
                        return LazyMethodDescription.this;
                    }

                    @Override // net.bytebuddy.description.method.ParameterDescription
                    public int getIndex() {
                        return this.index;
                    }

                    @Override // net.bytebuddy.description.NamedElement.WithOptionalName
                    public boolean isNamed() {
                        return LazyMethodDescription.this.parameterNames[this.index] != null;
                    }

                    @Override // net.bytebuddy.description.method.ParameterDescription
                    public boolean hasModifiers() {
                        return LazyMethodDescription.this.parameterModifiers[this.index] != null;
                    }

                    @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithRuntimeName
                    public String getName() {
                        if (isNamed()) {
                            return LazyMethodDescription.this.parameterNames[this.index];
                        }
                        return super.getName();
                    }

                    @Override // net.bytebuddy.description.method.ParameterDescription.AbstractBase, net.bytebuddy.description.ModifierReviewable
                    public int getModifiers() {
                        if (hasModifiers()) {
                            return LazyMethodDescription.this.parameterModifiers[this.index].intValue();
                        }
                        return super.getModifiers();
                    }

                    @Override // net.bytebuddy.description.method.ParameterDescription
                    public TypeDescription.Generic getType() {
                        return (TypeDescription.Generic) LazyMethodDescription.this.signatureResolution.resolveParameterTypes(LazyMethodDescription.this.parameterTypeDescriptors, LazyTypeDescription.this.typePool, LazyMethodDescription.this.parameterTypeAnnotationTokens, LazyMethodDescription.this).get(this.index);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        return LazyAnnotationDescription.asListOfNullable(LazyTypeDescription.this.typePool, (List) LazyMethodDescription.this.parameterAnnotationTokens.get(Integer.valueOf(this.index)));
                    }
                }

                private class LazyParameterizedReceiverType extends TypeDescription.Generic.OfParameterizedType {
                    private final TypeDescription typeDescription;

                    protected LazyParameterizedReceiverType(LazyMethodDescription lazyMethodDescription) {
                        this(LazyTypeDescription.this);
                    }

                    protected LazyParameterizedReceiverType(TypeDescription typeDescription) {
                        this.typeDescription = typeDescription;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic
                    public TypeList.Generic getTypeArguments() {
                        return new TypeArgumentList(this.typeDescription.getTypeVariables());
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic
                    @MaybeNull
                    public TypeDescription.Generic getOwnerType() {
                        TypeDescription declaringType = this.typeDescription.getDeclaringType();
                        if (declaringType == null) {
                            return TypeDescription.Generic.UNDEFINED;
                        }
                        return (this.typeDescription.isStatic() || !declaringType.isGenerified()) ? LazyMethodDescription.this.new LazyNonGenericReceiverType(declaringType) : LazyMethodDescription.this.new LazyParameterizedReceiverType(declaringType);
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        return LazyAnnotationDescription.asListOfNullable(LazyTypeDescription.this.typePool, (List) LazyMethodDescription.this.receiverTypeAnnotationTokens.get(getTypePath()));
                    }

                    /* JADX INFO: Access modifiers changed from: private */
                    public String getTypePath() {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < this.typeDescription.getInnerClassCount(); i++) {
                            sb = sb.append('.');
                        }
                        return sb.toString();
                    }

                    @Override // net.bytebuddy.description.type.TypeDefinition
                    public TypeDescription asErasure() {
                        return this.typeDescription;
                    }

                    protected class TypeArgumentList extends TypeList.Generic.AbstractBase {
                        private final List<? extends TypeDescription.Generic> typeVariables;

                        protected TypeArgumentList(List<? extends TypeDescription.Generic> list) {
                            this.typeVariables = list;
                        }

                        @Override // java.util.AbstractList, java.util.List
                        public TypeDescription.Generic get(int i) {
                            return new AnnotatedTypeVariable(this.typeVariables.get(i), i);
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                        public int size() {
                            return this.typeVariables.size();
                        }

                        protected class AnnotatedTypeVariable extends TypeDescription.Generic.OfTypeVariable {
                            private final int index;
                            private final TypeDescription.Generic typeVariable;

                            protected AnnotatedTypeVariable(TypeDescription.Generic generic, int i) {
                                this.typeVariable = generic;
                                this.index = i;
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            public TypeList.Generic getUpperBounds() {
                                return this.typeVariable.getUpperBounds();
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            public TypeVariableSource getTypeVariableSource() {
                                return this.typeVariable.getTypeVariableSource();
                            }

                            @Override // net.bytebuddy.description.type.TypeDescription.Generic
                            public String getSymbol() {
                                return this.typeVariable.getSymbol();
                            }

                            @Override // net.bytebuddy.description.annotation.AnnotationSource
                            public AnnotationList getDeclaredAnnotations() {
                                return LazyAnnotationDescription.asListOfNullable(LazyTypeDescription.this.typePool, (List) LazyMethodDescription.this.receiverTypeAnnotationTokens.get(LazyParameterizedReceiverType.this.getTypePath() + this.index + ';'));
                            }
                        }
                    }
                }

                protected class LazyNonGenericReceiverType extends TypeDescription.Generic.OfNonGenericType {
                    private final TypeDescription typeDescription;

                    protected LazyNonGenericReceiverType(LazyMethodDescription lazyMethodDescription) {
                        this(LazyTypeDescription.this);
                    }

                    protected LazyNonGenericReceiverType(TypeDescription typeDescription) {
                        this.typeDescription = typeDescription;
                    }

                    @Override // net.bytebuddy.description.type.TypeDescription.Generic
                    @MaybeNull
                    public TypeDescription.Generic getOwnerType() {
                        TypeDescription declaringType = this.typeDescription.getDeclaringType();
                        return declaringType == null ? TypeDescription.Generic.UNDEFINED : LazyMethodDescription.this.new LazyNonGenericReceiverType(declaringType);
                    }

                    @Override // net.bytebuddy.description.type.TypeDefinition
                    @MaybeNull
                    public TypeDescription.Generic getComponentType() {
                        return TypeDescription.Generic.UNDEFINED;
                    }

                    @Override // net.bytebuddy.description.annotation.AnnotationSource
                    public AnnotationList getDeclaredAnnotations() {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < this.typeDescription.getInnerClassCount(); i++) {
                            sb = sb.append('.');
                        }
                        return LazyAnnotationDescription.asListOfNullable(LazyTypeDescription.this.typePool, (List) LazyMethodDescription.this.receiverTypeAnnotationTokens.get(sb.toString()));
                    }

                    @Override // net.bytebuddy.description.type.TypeDefinition
                    public TypeDescription asErasure() {
                        return this.typeDescription;
                    }
                }
            }

            private class LazyRecordComponentDescription extends RecordComponentDescription.InDefinedShape.AbstractBase {
                private final List<AnnotationToken> annotationTokens;
                private final String descriptor;

                @MaybeNull
                private final String genericSignature;

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f389name;
                private final GenericTypeToken.Resolution.ForRecordComponent signatureResolution;
                private final Map<String, List<AnnotationToken>> typeAnnotationTokens;

                private LazyRecordComponentDescription(String str, @MaybeNull String str2, String str3, GenericTypeToken.Resolution.ForRecordComponent forRecordComponent, Map<String, List<AnnotationToken>> map, List<AnnotationToken> list) {
                    this.f389name = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.signatureResolution = forRecordComponent;
                    this.typeAnnotationTokens = map;
                    this.annotationTokens = list;
                }

                @Override // net.bytebuddy.description.type.RecordComponentDescription
                public TypeDescription.Generic getType() {
                    return this.signatureResolution.resolveRecordType(this.descriptor, LazyTypeDescription.this.typePool, this.typeAnnotationTokens, this);
                }

                @Override // net.bytebuddy.description.DeclaredByType.WithMandatoryDeclaration, net.bytebuddy.description.DeclaredByType
                @Nonnull
                public TypeDescription getDeclaringType() {
                    return LazyTypeDescription.this;
                }

                @Override // net.bytebuddy.description.NamedElement
                public String getActualName() {
                    return this.f389name;
                }

                @Override // net.bytebuddy.description.annotation.AnnotationSource
                public AnnotationList getDeclaredAnnotations() {
                    return LazyAnnotationDescription.asList(LazyTypeDescription.this.typePool, this.annotationTokens);
                }

                @Override // net.bytebuddy.description.type.RecordComponentDescription.AbstractBase, net.bytebuddy.description.NamedElement.WithDescriptor
                @MaybeNull
                public String getGenericSignature() {
                    return this.genericSignature;
                }
            }
        }

        protected class TypeExtractor extends ClassVisitor {
            private static final int REAL_MODIFIER_MASK = 65535;
            private static final int SUPER_CLASS_INDEX = -1;
            private int actualModifiers;
            private final List<LazyTypeDescription.AnnotationToken> annotationTokens;
            private boolean anonymousType;

            @MaybeNull
            private ClassFileVersion classFileVersion;
            private final List<String> declaredTypes;

            @MaybeNull
            private String declaringTypeName;
            private final List<LazyTypeDescription.FieldToken> fieldTokens;

            @MaybeNull
            private String genericSignature;

            @MaybeNull
            private String[] interfaceName;

            @MaybeNull
            private String internalName;
            private final List<LazyTypeDescription.MethodToken> methodTokens;
            private int modifiers;

            @MaybeNull
            private String nestHost;
            private final List<String> nestMembers;
            private final List<String> permittedSubclasses;
            private final List<LazyTypeDescription.RecordComponentToken> recordComponentTokens;

            @MaybeNull
            private String superClassName;
            private final Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> superTypeAnnotationTokens;
            private LazyTypeDescription.TypeContainment typeContainment;
            private final Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> typeVariableAnnotationTokens;
            private final Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> typeVariableBoundsAnnotationTokens;

            protected TypeExtractor() {
                super(OpenedClassReader.ASM_API);
                this.superTypeAnnotationTokens = new HashMap();
                this.typeVariableAnnotationTokens = new HashMap();
                this.typeVariableBoundsAnnotationTokens = new HashMap();
                this.annotationTokens = new ArrayList();
                this.fieldTokens = new ArrayList();
                this.methodTokens = new ArrayList();
                this.recordComponentTokens = new ArrayList();
                this.anonymousType = false;
                this.typeContainment = LazyTypeDescription.TypeContainment.SelfContained.INSTANCE;
                this.nestMembers = new ArrayList();
                this.declaredTypes = new ArrayList();
                this.permittedSubclasses = new ArrayList();
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visit(int i, int i2, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String[] strArr) {
                this.modifiers = 65535 & i2;
                this.actualModifiers = i2;
                this.internalName = str;
                this.genericSignature = str2;
                this.superClassName = str3;
                this.interfaceName = strArr;
                this.classFileVersion = ClassFileVersion.ofMinorMajor(i);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitOuterClass(@MaybeNull String str, @MaybeNull String str2, String str3) {
                if (str2 != null && !str2.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                    this.typeContainment = new LazyTypeDescription.TypeContainment.WithinMethod(str, str2, str3);
                } else if (str != null) {
                    this.typeContainment = new LazyTypeDescription.TypeContainment.WithinType(str, true);
                }
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitInnerClass(String str, @MaybeNull String str2, @MaybeNull String str3, int i) {
                if (str.equals(this.internalName)) {
                    if (str2 != null) {
                        this.declaringTypeName = str2;
                        if (this.typeContainment.isSelfContained()) {
                            this.typeContainment = new LazyTypeDescription.TypeContainment.WithinType(str2, false);
                        }
                    }
                    if (str3 == null && !this.typeContainment.isSelfContained()) {
                        this.anonymousType = true;
                    }
                    this.modifiers = 65535 & i;
                    return;
                }
                if (str2 == null || str3 == null || !str2.equals(this.internalName)) {
                    return;
                }
                this.declaredTypes.add("L" + str + ";");
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public AnnotationVisitor visitTypeAnnotation(int i, @MaybeNull TypePath typePath, String str, boolean z) {
                AnnotationRegistrant withIndex;
                TypeReference typeReference = new TypeReference(i);
                int sort = typeReference.getSort();
                if (sort == 0) {
                    withIndex = new AnnotationRegistrant.ForTypeVariable.WithIndex(str, typePath, typeReference.getTypeParameterIndex(), this.typeVariableAnnotationTokens);
                } else if (sort == 16) {
                    withIndex = new AnnotationRegistrant.ForTypeVariable.WithIndex(str, typePath, typeReference.getSuperTypeIndex(), this.superTypeAnnotationTokens);
                } else if (sort == 17) {
                    withIndex = new AnnotationRegistrant.ForTypeVariable.WithIndex.DoubleIndexed(str, typePath, typeReference.getTypeParameterBoundIndex(), typeReference.getTypeParameterIndex(), this.typeVariableBoundsAnnotationTokens);
                } else {
                    throw new IllegalArgumentException("Unexpected type reference: " + typeReference.getSort());
                }
                return new AnnotationExtractor(withIndex, new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public AnnotationVisitor visitAnnotation(String str, boolean z) {
                return new AnnotationExtractor(this, str, this.annotationTokens, new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public FieldVisitor visitField(int i, String str, String str2, @MaybeNull String str3, @MaybeNull Object obj) {
                return new FieldExtractor(i & 65535, str, str2, str3);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            @MaybeNull
            public MethodVisitor visitMethod(int i, String str, String str2, @MaybeNull String str3, @MaybeNull String[] strArr) {
                return str.equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME) ? Default.IGNORE_METHOD : new MethodExtractor(i & 65535, str, str2, str3, strArr);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitNestHost(String str) {
                this.nestHost = str;
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitNestMember(String str) {
                this.nestMembers.add(str);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public RecordComponentVisitor visitRecordComponent(String str, String str2, @MaybeNull String str3) {
                return new RecordComponentExtractor(str, str2, str3);
            }

            @Override // net.bytebuddy.jar.asm.ClassVisitor
            public void visitPermittedSubclass(String str) {
                this.permittedSubclasses.add(str);
            }

            protected TypeDescription toTypeDescription() {
                if (this.internalName == null || this.classFileVersion == null) {
                    throw new IllegalStateException("Internal name or class file version were not set");
                }
                Map<String, List<LazyTypeDescription.AnnotationToken>> mapRemove = this.superTypeAnnotationTokens.remove(-1);
                Default r3 = Default.this;
                int i = this.actualModifiers;
                int i2 = this.modifiers;
                String str = this.internalName;
                String str2 = this.superClassName;
                String[] strArr = this.interfaceName;
                String str3 = this.genericSignature;
                LazyTypeDescription.TypeContainment typeContainment = this.typeContainment;
                String str4 = this.declaringTypeName;
                List<String> list = this.declaredTypes;
                boolean z = this.anonymousType;
                String str5 = this.nestHost;
                List<String> list2 = this.nestMembers;
                if (mapRemove == null) {
                    mapRemove = Collections.emptyMap();
                }
                return new LazyTypeDescription(r3, i, i2, str, str2, strArr, str3, typeContainment, str4, list, z, str5, list2, mapRemove, this.superTypeAnnotationTokens, this.typeVariableAnnotationTokens, this.typeVariableBoundsAnnotationTokens, this.annotationTokens, this.fieldTokens, this.methodTokens, this.recordComponentTokens, this.permittedSubclasses, this.classFileVersion);
            }

            protected class AnnotationExtractor extends AnnotationVisitor {
                private final AnnotationRegistrant annotationRegistrant;
                private final ComponentTypeLocator componentTypeLocator;

                protected AnnotationExtractor(TypeExtractor typeExtractor, String str, List<LazyTypeDescription.AnnotationToken> list, ComponentTypeLocator componentTypeLocator) {
                    this(new AnnotationRegistrant.ForByteCodeElement(str, list), componentTypeLocator);
                }

                protected AnnotationExtractor(TypeExtractor typeExtractor, String str, int i, Map<Integer, List<LazyTypeDescription.AnnotationToken>> map, ComponentTypeLocator componentTypeLocator) {
                    this(new AnnotationRegistrant.ForByteCodeElement.WithIndex(str, i, map), componentTypeLocator);
                }

                protected AnnotationExtractor(AnnotationRegistrant annotationRegistrant, ComponentTypeLocator componentTypeLocator) {
                    super(OpenedClassReader.ASM_API);
                    this.annotationRegistrant = annotationRegistrant;
                    this.componentTypeLocator = componentTypeLocator;
                }

                @Override // net.bytebuddy.jar.asm.AnnotationVisitor
                public void visit(String str, Object obj) {
                    String className;
                    if (obj instanceof Type) {
                        Type type = (Type) obj;
                        AnnotationRegistrant annotationRegistrant = this.annotationRegistrant;
                        Default r2 = Default.this;
                        if (type.getSort() == 9) {
                            className = type.getInternalName().replace('/', '.');
                        } else {
                            className = type.getClassName();
                        }
                        annotationRegistrant.register(str, new LazyTypeDescription.LazyAnnotationValue.ForTypeValue(r2, className));
                        return;
                    }
                    this.annotationRegistrant.register(str, AnnotationValue.ForConstant.of(obj));
                }

                @Override // net.bytebuddy.jar.asm.AnnotationVisitor
                public void visitEnum(String str, String str2, String str3) {
                    this.annotationRegistrant.register(str, new LazyTypeDescription.LazyAnnotationValue.ForEnumerationValue(Default.this, str2.substring(1, str2.length() - 1).replace('/', '.'), str3));
                }

                @Override // net.bytebuddy.jar.asm.AnnotationVisitor
                public AnnotationVisitor visitAnnotation(String str, String str2) {
                    return TypeExtractor.this.new AnnotationExtractor(new AnnotationLookup(str2, str), new ComponentTypeLocator.ForAnnotationProperty(Default.this, str2));
                }

                @Override // net.bytebuddy.jar.asm.AnnotationVisitor
                public AnnotationVisitor visitArray(String str) {
                    return TypeExtractor.this.new AnnotationExtractor(new ArrayLookup(str, this.componentTypeLocator.bind(str)), ComponentTypeLocator.Illegal.INSTANCE);
                }

                @Override // net.bytebuddy.jar.asm.AnnotationVisitor
                public void visitEnd() {
                    this.annotationRegistrant.onComplete();
                }

                protected class ArrayLookup implements AnnotationRegistrant {
                    private final AbstractBase.ComponentTypeReference componentTypeReference;

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    private final String f396name;
                    private final List<AnnotationValue<?, ?>> values;

                    private ArrayLookup(String str, AbstractBase.ComponentTypeReference componentTypeReference) {
                        this.f396name = str;
                        this.componentTypeReference = componentTypeReference;
                        this.values = new ArrayList();
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                    public void register(String str, AnnotationValue<?, ?> annotationValue) {
                        this.values.add(annotationValue);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                    public void onComplete() {
                        AnnotationExtractor.this.annotationRegistrant.register(this.f396name, new LazyTypeDescription.LazyAnnotationValue.ForArray(Default.this, this.componentTypeReference, this.values));
                    }
                }

                protected class AnnotationLookup implements AnnotationRegistrant {
                    private final String descriptor;

                    /* JADX INFO: renamed from: name, reason: collision with root package name */
                    private final String f395name;
                    private final Map<String, AnnotationValue<?, ?>> values = new HashMap();

                    protected AnnotationLookup(String str, String str2) {
                        this.descriptor = str;
                        this.f395name = str2;
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                    public void register(String str, AnnotationValue<?, ?> annotationValue) {
                        this.values.put(str, annotationValue);
                    }

                    @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                    public void onComplete() {
                        AnnotationExtractor.this.annotationRegistrant.register(this.f395name, new LazyTypeDescription.LazyAnnotationValue.ForAnnotationValue(Default.this, new LazyTypeDescription.AnnotationToken(this.descriptor, this.values)));
                    }
                }
            }

            protected class FieldExtractor extends FieldVisitor {
                private final List<LazyTypeDescription.AnnotationToken> annotationTokens;
                private final String descriptor;

                @MaybeNull
                private final String genericSignature;
                private final String internalName;
                private final int modifiers;
                private final Map<String, List<LazyTypeDescription.AnnotationToken>> typeAnnotationTokens;

                protected FieldExtractor(int i, String str, @MaybeNull String str2, String str3) {
                    super(OpenedClassReader.ASM_API);
                    this.modifiers = i;
                    this.internalName = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.typeAnnotationTokens = new HashMap();
                    this.annotationTokens = new ArrayList();
                }

                @Override // net.bytebuddy.jar.asm.FieldVisitor
                @MaybeNull
                public AnnotationVisitor visitTypeAnnotation(int i, @MaybeNull TypePath typePath, String str, boolean z) {
                    TypeReference typeReference = new TypeReference(i);
                    if (typeReference.getSort() == 19) {
                        return TypeExtractor.this.new AnnotationExtractor(new AnnotationRegistrant.ForTypeVariable(str, typePath, this.typeAnnotationTokens), new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
                    }
                    throw new IllegalStateException("Unexpected type reference on field: " + typeReference.getSort());
                }

                @Override // net.bytebuddy.jar.asm.FieldVisitor
                public AnnotationVisitor visitAnnotation(String str, boolean z) {
                    return new AnnotationExtractor(TypeExtractor.this, str, this.annotationTokens, new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
                }

                @Override // net.bytebuddy.jar.asm.FieldVisitor
                public void visitEnd() {
                    TypeExtractor.this.fieldTokens.add(new LazyTypeDescription.FieldToken(this.internalName, this.modifiers, this.descriptor, this.genericSignature, this.typeAnnotationTokens, this.annotationTokens));
                }
            }

            protected class MethodExtractor extends MethodVisitor implements AnnotationRegistrant {
                private final List<LazyTypeDescription.AnnotationToken> annotationTokens;

                @MaybeNull
                private AnnotationValue<?, ?> defaultValue;
                private final String descriptor;

                @MaybeNull
                private final String[] exceptionName;
                private final Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> exceptionTypeAnnotationTokens;

                @MaybeNull
                private Label firstLabel;

                @MaybeNull
                private final String genericSignature;
                private final String internalName;
                private int invisibleParameterShift;
                private final ParameterBag legacyParameterBag;
                private final int modifiers;
                private final Map<Integer, List<LazyTypeDescription.AnnotationToken>> parameterAnnotationTokens;
                private final List<LazyTypeDescription.MethodToken.ParameterToken> parameterTokens;
                private final Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> parameterTypeAnnotationTokens;
                private final Map<String, List<LazyTypeDescription.AnnotationToken>> receiverTypeAnnotationTokens;
                private final Map<String, List<LazyTypeDescription.AnnotationToken>> returnTypeAnnotationTokens;
                private final Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> typeVariableAnnotationTokens;
                private final Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> typeVariableBoundAnnotationTokens;
                private int visibleParameterShift;

                @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                public void onComplete() {
                }

                protected MethodExtractor(int i, String str, @MaybeNull String str2, @MaybeNull String str3, String[] strArr) {
                    super(OpenedClassReader.ASM_API);
                    this.modifiers = i;
                    this.internalName = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.exceptionName = strArr;
                    this.typeVariableAnnotationTokens = new HashMap();
                    this.typeVariableBoundAnnotationTokens = new HashMap();
                    this.returnTypeAnnotationTokens = new HashMap();
                    this.parameterTypeAnnotationTokens = new HashMap();
                    this.exceptionTypeAnnotationTokens = new HashMap();
                    this.receiverTypeAnnotationTokens = new HashMap();
                    this.annotationTokens = new ArrayList();
                    this.parameterAnnotationTokens = new HashMap();
                    this.parameterTokens = new ArrayList();
                    this.legacyParameterBag = new ParameterBag(Type.getMethodType(str2).getArgumentTypes());
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                @MaybeNull
                public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
                    AnnotationRegistrant withIndex;
                    TypeReference typeReference = new TypeReference(i);
                    int sort = typeReference.getSort();
                    if (sort == 1) {
                        withIndex = new AnnotationRegistrant.ForTypeVariable.WithIndex(str, typePath, typeReference.getTypeParameterIndex(), this.typeVariableAnnotationTokens);
                    } else {
                        switch (sort) {
                            case 18:
                                withIndex = new AnnotationRegistrant.ForTypeVariable.WithIndex.DoubleIndexed(str, typePath, typeReference.getTypeParameterBoundIndex(), typeReference.getTypeParameterIndex(), this.typeVariableBoundAnnotationTokens);
                                break;
                            case 19:
                                return null;
                            case 20:
                                withIndex = new AnnotationRegistrant.ForTypeVariable(str, typePath, this.returnTypeAnnotationTokens);
                                break;
                            case 21:
                                withIndex = new AnnotationRegistrant.ForTypeVariable(str, typePath, this.receiverTypeAnnotationTokens);
                                break;
                            case 22:
                                withIndex = new AnnotationRegistrant.ForTypeVariable.WithIndex(str, typePath, typeReference.getFormalParameterIndex(), this.parameterTypeAnnotationTokens);
                                break;
                            case 23:
                                withIndex = new AnnotationRegistrant.ForTypeVariable.WithIndex(str, typePath, typeReference.getExceptionIndex(), this.exceptionTypeAnnotationTokens);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected type reference on method: " + typeReference.getSort());
                        }
                    }
                    return TypeExtractor.this.new AnnotationExtractor(withIndex, new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public AnnotationVisitor visitAnnotation(String str, boolean z) {
                    return new AnnotationExtractor(TypeExtractor.this, str, this.annotationTokens, new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitAnnotableParameterCount(int i, boolean z) {
                    if (z) {
                        this.visibleParameterShift = Type.getMethodType(this.descriptor).getArgumentTypes().length - i;
                    } else {
                        this.invisibleParameterShift = Type.getMethodType(this.descriptor).getArgumentTypes().length - i;
                    }
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z) {
                    return new AnnotationExtractor(TypeExtractor.this, str, i + (z ? this.visibleParameterShift : this.invisibleParameterShift), this.parameterAnnotationTokens, new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitLabel(Label label) {
                    if (Default.this.readerMode.isExtended() && this.firstLabel == null) {
                        this.firstLabel = label;
                    }
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
                    if (Default.this.readerMode.isExtended() && label == this.firstLabel) {
                        this.legacyParameterBag.register(i, str);
                    }
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitParameter(String str, int i) {
                    this.parameterTokens.add(new LazyTypeDescription.MethodToken.ParameterToken(str, Integer.valueOf(i)));
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public AnnotationVisitor visitAnnotationDefault() {
                    return TypeExtractor.this.new AnnotationExtractor(this, new ComponentTypeLocator.ForArrayType(this.descriptor));
                }

                @Override // net.bytebuddy.pool.TypePool.Default.AnnotationRegistrant
                public void register(String str, AnnotationValue<?, ?> annotationValue) {
                    this.defaultValue = annotationValue;
                }

                @Override // net.bytebuddy.jar.asm.MethodVisitor
                public void visitEnd() {
                    List<LazyTypeDescription.MethodToken.ParameterToken> listResolve;
                    List list = TypeExtractor.this.methodTokens;
                    String str = this.internalName;
                    int i = this.modifiers;
                    String str2 = this.descriptor;
                    String str3 = this.genericSignature;
                    String[] strArr = this.exceptionName;
                    Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> map = this.typeVariableAnnotationTokens;
                    Map<Integer, Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>>> map2 = this.typeVariableBoundAnnotationTokens;
                    Map<String, List<LazyTypeDescription.AnnotationToken>> map3 = this.returnTypeAnnotationTokens;
                    Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> map4 = this.parameterTypeAnnotationTokens;
                    Map<Integer, Map<String, List<LazyTypeDescription.AnnotationToken>>> map5 = this.exceptionTypeAnnotationTokens;
                    Map<String, List<LazyTypeDescription.AnnotationToken>> map6 = this.receiverTypeAnnotationTokens;
                    List<LazyTypeDescription.AnnotationToken> list2 = this.annotationTokens;
                    Map<Integer, List<LazyTypeDescription.AnnotationToken>> map7 = this.parameterAnnotationTokens;
                    if (this.parameterTokens.isEmpty()) {
                        listResolve = this.legacyParameterBag.resolve((this.modifiers & 8) != 0);
                    } else {
                        listResolve = this.parameterTokens;
                    }
                    list.add(new LazyTypeDescription.MethodToken(str, i, str2, str3, strArr, map, map2, map3, map4, map5, map6, list2, map7, listResolve, this.defaultValue));
                }
            }

            protected class RecordComponentExtractor extends RecordComponentVisitor {
                private final List<LazyTypeDescription.AnnotationToken> annotationTokens;
                private final String descriptor;

                @MaybeNull
                private final String genericSignature;

                /* JADX INFO: renamed from: name, reason: collision with root package name */
                private final String f397name;
                private final Map<String, List<LazyTypeDescription.AnnotationToken>> typeAnnotationTokens;

                protected RecordComponentExtractor(String str, @MaybeNull String str2, String str3) {
                    super(OpenedClassReader.ASM_API);
                    this.f397name = str;
                    this.descriptor = str2;
                    this.genericSignature = str3;
                    this.typeAnnotationTokens = new HashMap();
                    this.annotationTokens = new ArrayList();
                }

                @Override // net.bytebuddy.jar.asm.RecordComponentVisitor
                public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String str, boolean z) {
                    TypeReference typeReference = new TypeReference(i);
                    if (typeReference.getSort() == 19) {
                        return TypeExtractor.this.new AnnotationExtractor(new AnnotationRegistrant.ForTypeVariable(str, typePath, this.typeAnnotationTokens), new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
                    }
                    throw new IllegalStateException("Unexpected type reference on record component: " + typeReference.getSort());
                }

                @Override // net.bytebuddy.jar.asm.RecordComponentVisitor
                public AnnotationVisitor visitAnnotation(String str, boolean z) {
                    return new AnnotationExtractor(TypeExtractor.this, str, this.annotationTokens, new ComponentTypeLocator.ForAnnotationProperty(Default.this, str));
                }

                @Override // net.bytebuddy.jar.asm.RecordComponentVisitor
                public void visitEnd() {
                    TypeExtractor.this.recordComponentTokens.add(new LazyTypeDescription.RecordComponentToken(this.f397name, this.descriptor, this.genericSignature, this.typeAnnotationTokens, this.annotationTokens));
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class LazyFacade extends AbstractBase {
        private final TypePool typePool;

        @Override // net.bytebuddy.pool.TypePool.AbstractBase
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.typePool.equals(((LazyFacade) obj).typePool);
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase
        public int hashCode() {
            return (super.hashCode() * 31) + this.typePool.hashCode();
        }

        public LazyFacade(TypePool typePool) {
            super(CacheProvider.NoOp.INSTANCE);
            this.typePool = typePool;
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase
        protected Resolution doDescribe(String str) {
            return new LazyResolution(this.typePool, str);
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase, net.bytebuddy.pool.TypePool
        public void clear() {
            this.typePool.clear();
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class LazyResolution implements Resolution {

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f400name;
            private final TypePool typePool;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                LazyResolution lazyResolution = (LazyResolution) obj;
                return this.f400name.equals(lazyResolution.f400name) && this.typePool.equals(lazyResolution.typePool);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.typePool.hashCode()) * 31) + this.f400name.hashCode();
            }

            protected LazyResolution(TypePool typePool, String str) {
                this.typePool = typePool;
                this.f400name = str;
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public boolean isResolved() {
                return this.typePool.describe(this.f400name).isResolved();
            }

            @Override // net.bytebuddy.pool.TypePool.Resolution
            public TypeDescription resolve() {
                return new LazyTypeDescription(this.typePool, this.f400name);
            }
        }

        protected static class LazyTypeDescription extends TypeDescription.AbstractBase.OfSimpleType.WithDelegation {
            private transient /* synthetic */ TypeDescription delegate;

            /* JADX INFO: renamed from: name, reason: collision with root package name */
            private final String f401name;
            private final TypePool typePool;

            protected LazyTypeDescription(TypePool typePool, String str) {
                this.typePool = typePool;
                this.f401name = str;
            }

            @Override // net.bytebuddy.description.NamedElement.WithRuntimeName
            public String getName() {
                return this.f401name;
            }

            @Override // net.bytebuddy.description.type.TypeDescription.AbstractBase.OfSimpleType.WithDelegation
            @CachedReturnPlugin.Enhance(MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX)
            protected TypeDescription delegate() {
                TypeDescription typeDescriptionResolve = this.delegate != null ? null : this.typePool.describe(this.f401name).resolve();
                if (typeDescriptionResolve == null) {
                    return this.delegate;
                }
                this.delegate = typeDescriptionResolve;
                return typeDescriptionResolve;
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class ClassLoading extends AbstractBase.Hierarchical {

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ClassLoader classLoader;

        /* JADX WARN: Code duplicated, block: B:21:0x002e A[RETURN] */
        @Override // net.bytebuddy.pool.TypePool.AbstractBase.Hierarchical, net.bytebuddy.pool.TypePool.AbstractBase
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
            ClassLoader classLoader = this.classLoader;
            ClassLoader classLoader2 = ((ClassLoading) obj).classLoader;
            if (classLoader2 == null) {
                if (classLoader != null) {
                    return false;
                }
                return true;
            }
            if (classLoader == null || !classLoader.equals(classLoader2)) {
                return false;
            }
            return true;
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase.Hierarchical, net.bytebuddy.pool.TypePool.AbstractBase
        public int hashCode() {
            int iHashCode = super.hashCode() * 31;
            ClassLoader classLoader = this.classLoader;
            return classLoader != null ? iHashCode + classLoader.hashCode() : iHashCode;
        }

        public ClassLoading(CacheProvider cacheProvider, TypePool typePool, @MaybeNull ClassLoader classLoader) {
            super(cacheProvider, typePool);
            this.classLoader = classLoader;
        }

        public static TypePool of(@MaybeNull ClassLoader classLoader) {
            return of(classLoader, Empty.INSTANCE);
        }

        public static TypePool of(@MaybeNull ClassLoader classLoader, TypePool typePool) {
            return new ClassLoading(new CacheProvider.Simple(), typePool, classLoader);
        }

        public static TypePool ofSystemLoader() {
            return of(ClassLoader.getSystemClassLoader());
        }

        public static TypePool ofPlatformLoader() {
            return of(ClassLoader.getSystemClassLoader().getParent());
        }

        public static TypePool ofBootLoader() {
            return of(ClassLoadingStrategy.BOOTSTRAP_LOADER);
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase
        protected Resolution doDescribe(String str) {
            try {
                return new Resolution.Simple(TypeDescription.ForLoadedType.of(Class.forName(str, false, this.classLoader)));
            } catch (ClassNotFoundException unused) {
                return new Resolution.Illegal(str);
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class Explicit extends AbstractBase.Hierarchical {
        private final Map<String, TypeDescription> types;

        @Override // net.bytebuddy.pool.TypePool.AbstractBase.Hierarchical, net.bytebuddy.pool.TypePool.AbstractBase
        public boolean equals(@MaybeNull Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.types.equals(((Explicit) obj).types);
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase.Hierarchical, net.bytebuddy.pool.TypePool.AbstractBase
        public int hashCode() {
            return (super.hashCode() * 31) + this.types.hashCode();
        }

        public Explicit(Map<String, TypeDescription> map) {
            this(Empty.INSTANCE, map);
        }

        public Explicit(TypePool typePool, Map<String, TypeDescription> map) {
            super(CacheProvider.NoOp.INSTANCE, typePool);
            this.types = map;
        }

        public static TypePool wrap(TypeDescription typeDescription, List<? extends DynamicType> list, TypePool typePool) {
            HashMap map = new HashMap();
            map.put(typeDescription.getName(), typeDescription);
            Iterator<? extends DynamicType> it = list.iterator();
            while (it.hasNext()) {
                for (TypeDescription typeDescription2 : it.next().getAllTypes().keySet()) {
                    map.put(typeDescription2.getName(), typeDescription2);
                }
            }
            return new Explicit(typePool, map);
        }

        @Override // net.bytebuddy.pool.TypePool.AbstractBase
        protected Resolution doDescribe(String str) {
            TypeDescription typeDescription = this.types.get(str);
            return typeDescription == null ? new Resolution.Illegal(str) : new Resolution.Simple(typeDescription);
        }
    }
}
