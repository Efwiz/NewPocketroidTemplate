package net.bytebuddy.dynamic.loading;

import java.io.File;
import java.lang.ClassLoader;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Map;
import java.util.concurrent.Callable;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public interface ClassLoadingStrategy<T extends ClassLoader> {

    @AlwaysNull
    public static final ClassLoader BOOTSTRAP_LOADER = null;

    @AlwaysNull
    public static final ProtectionDomain NO_PROTECTION_DOMAIN = null;

    public interface Configurable<S extends ClassLoader> extends ClassLoadingStrategy<S> {
        Configurable<S> allowExistingTypes();

        Configurable<S> opened();

        Configurable<S> with(ProtectionDomain protectionDomain);

        Configurable<S> with(PackageDefinitionStrategy packageDefinitionStrategy);
    }

    Map<TypeDescription, Class<?>> load(@MaybeNull T t, Map<TypeDescription, byte[]> map);

    public enum Default implements Configurable<ClassLoader> {
        WRAPPER(new WrappingDispatcher(ByteArrayClassLoader.PersistenceHandler.LATENT, false)),
        WRAPPER_PERSISTENT(new WrappingDispatcher(ByteArrayClassLoader.PersistenceHandler.MANIFEST, false)),
        CHILD_FIRST(new WrappingDispatcher(ByteArrayClassLoader.PersistenceHandler.LATENT, true)),
        CHILD_FIRST_PERSISTENT(new WrappingDispatcher(ByteArrayClassLoader.PersistenceHandler.MANIFEST, true)),
        INJECTION(new InjectionDispatcher());

        private static final boolean DEFAULT_FORBID_EXISTING = true;
        private final Configurable<ClassLoader> dispatcher;

        Default(Configurable configurable) {
            this.dispatcher = configurable;
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
        public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
            return this.dispatcher.load(classLoader, map);
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
        public Configurable<ClassLoader> with(ProtectionDomain protectionDomain) {
            return this.dispatcher.with(protectionDomain);
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
        public Configurable<ClassLoader> with(PackageDefinitionStrategy packageDefinitionStrategy) {
            return this.dispatcher.with(packageDefinitionStrategy);
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
        public Configurable<ClassLoader> allowExistingTypes() {
            return this.dispatcher.allowExistingTypes();
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
        public Configurable<ClassLoader> opened() {
            return this.dispatcher.opened();
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class InjectionDispatcher implements Configurable<ClassLoader> {
            private final boolean forbidExisting;
            private final PackageDefinitionStrategy packageDefinitionStrategy;

            @MaybeNull
            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
            private final ProtectionDomain protectionDomain;

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
                    boolean r2 = r4.forbidExisting
                    net.bytebuddy.dynamic.loading.ClassLoadingStrategy$Default$InjectionDispatcher r5 = (net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.InjectionDispatcher) r5
                    boolean r3 = r5.forbidExisting
                    if (r2 == r3) goto L1c
                    return r1
                L1c:
                    java.security.ProtectionDomain r2 = r4.protectionDomain
                    java.security.ProtectionDomain r3 = r5.protectionDomain
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
                    net.bytebuddy.dynamic.loading.PackageDefinitionStrategy r2 = r4.packageDefinitionStrategy
                    net.bytebuddy.dynamic.loading.PackageDefinitionStrategy r5 = r5.packageDefinitionStrategy
                    boolean r5 = r2.equals(r5)
                    if (r5 != 0) goto L39
                    return r1
                L39:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.InjectionDispatcher.equals(java.lang.Object):boolean");
            }

            public int hashCode() {
                int iHashCode = getClass().hashCode() * 31;
                ProtectionDomain protectionDomain = this.protectionDomain;
                if (protectionDomain != null) {
                    iHashCode += protectionDomain.hashCode();
                }
                return (((iHashCode * 31) + this.packageDefinitionStrategy.hashCode()) * 31) + (this.forbidExisting ? 1 : 0);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> opened() {
                return this;
            }

            protected InjectionDispatcher() {
                this(NO_PROTECTION_DOMAIN, PackageDefinitionStrategy.NoOp.INSTANCE, true);
            }

            private InjectionDispatcher(@MaybeNull ProtectionDomain protectionDomain, PackageDefinitionStrategy packageDefinitionStrategy, boolean z) {
                this.protectionDomain = protectionDomain;
                this.packageDefinitionStrategy = packageDefinitionStrategy;
                this.forbidExisting = z;
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
            public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
                if (classLoader == null) {
                    throw new IllegalArgumentException("Cannot inject classes into the bootstrap class loader");
                }
                return new ClassInjector.UsingReflection(classLoader, this.protectionDomain, this.packageDefinitionStrategy, this.forbidExisting).inject(map);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> with(ProtectionDomain protectionDomain) {
                return new InjectionDispatcher(protectionDomain, this.packageDefinitionStrategy, this.forbidExisting);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> with(PackageDefinitionStrategy packageDefinitionStrategy) {
                return new InjectionDispatcher(this.protectionDomain, packageDefinitionStrategy, this.forbidExisting);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> allowExistingTypes() {
                return new InjectionDispatcher(this.protectionDomain, this.packageDefinitionStrategy, false);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class WrappingDispatcher implements Configurable<ClassLoader> {
            private static final boolean CHILD_FIRST = true;
            private static final boolean PARENT_FIRST = false;
            private final boolean childFirst;
            private final boolean forbidExisting;
            private final PackageDefinitionStrategy packageDefinitionStrategy;
            private final ByteArrayClassLoader.PersistenceHandler persistenceHandler;

            @MaybeNull
            @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
            private final ProtectionDomain protectionDomain;
            private final boolean sealed;

            /* JADX WARN: Code restructure failed: missing block: B:29:0x0044, code lost:
            
                if (r2 != null) goto L30;
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
                    boolean r2 = r4.childFirst
                    net.bytebuddy.dynamic.loading.ClassLoadingStrategy$Default$WrappingDispatcher r5 = (net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.WrappingDispatcher) r5
                    boolean r3 = r5.childFirst
                    if (r2 == r3) goto L1c
                    return r1
                L1c:
                    boolean r2 = r4.forbidExisting
                    boolean r3 = r5.forbidExisting
                    if (r2 == r3) goto L23
                    return r1
                L23:
                    boolean r2 = r4.sealed
                    boolean r3 = r5.sealed
                    if (r2 == r3) goto L2a
                    return r1
                L2a:
                    net.bytebuddy.dynamic.loading.ByteArrayClassLoader$PersistenceHandler r2 = r4.persistenceHandler
                    net.bytebuddy.dynamic.loading.ByteArrayClassLoader$PersistenceHandler r3 = r5.persistenceHandler
                    boolean r2 = r2.equals(r3)
                    if (r2 != 0) goto L35
                    return r1
                L35:
                    java.security.ProtectionDomain r2 = r4.protectionDomain
                    java.security.ProtectionDomain r3 = r5.protectionDomain
                    if (r3 == 0) goto L44
                    if (r2 == 0) goto L46
                    boolean r2 = r2.equals(r3)
                    if (r2 != 0) goto L47
                    return r1
                L44:
                    if (r2 == 0) goto L47
                L46:
                    return r1
                L47:
                    net.bytebuddy.dynamic.loading.PackageDefinitionStrategy r2 = r4.packageDefinitionStrategy
                    net.bytebuddy.dynamic.loading.PackageDefinitionStrategy r5 = r5.packageDefinitionStrategy
                    boolean r5 = r2.equals(r5)
                    if (r5 != 0) goto L52
                    return r1
                L52:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Default.WrappingDispatcher.equals(java.lang.Object):boolean");
            }

            public int hashCode() {
                int iHashCode = getClass().hashCode() * 31;
                ProtectionDomain protectionDomain = this.protectionDomain;
                if (protectionDomain != null) {
                    iHashCode += protectionDomain.hashCode();
                }
                return (((((((((iHashCode * 31) + this.persistenceHandler.hashCode()) * 31) + this.packageDefinitionStrategy.hashCode()) * 31) + (this.childFirst ? 1 : 0)) * 31) + (this.forbidExisting ? 1 : 0)) * 31) + (this.sealed ? 1 : 0);
            }

            protected WrappingDispatcher(ByteArrayClassLoader.PersistenceHandler persistenceHandler, boolean z) {
                this(NO_PROTECTION_DOMAIN, PackageDefinitionStrategy.Trivial.INSTANCE, persistenceHandler, z, true, true);
            }

            private WrappingDispatcher(@MaybeNull ProtectionDomain protectionDomain, PackageDefinitionStrategy packageDefinitionStrategy, ByteArrayClassLoader.PersistenceHandler persistenceHandler, boolean z, boolean z2, boolean z3) {
                this.protectionDomain = protectionDomain;
                this.packageDefinitionStrategy = packageDefinitionStrategy;
                this.persistenceHandler = persistenceHandler;
                this.childFirst = z;
                this.forbidExisting = z2;
                this.sealed = z3;
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
            public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
                if (this.childFirst) {
                    return ByteArrayClassLoader.ChildFirst.load(classLoader, map, this.protectionDomain, this.persistenceHandler, this.packageDefinitionStrategy, this.forbidExisting, this.sealed);
                }
                return ByteArrayClassLoader.load(classLoader, map, this.protectionDomain, this.persistenceHandler, this.packageDefinitionStrategy, this.forbidExisting, this.sealed);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> with(ProtectionDomain protectionDomain) {
                return new WrappingDispatcher(protectionDomain, this.packageDefinitionStrategy, this.persistenceHandler, this.childFirst, this.forbidExisting, this.sealed);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> with(PackageDefinitionStrategy packageDefinitionStrategy) {
                return new WrappingDispatcher(this.protectionDomain, packageDefinitionStrategy, this.persistenceHandler, this.childFirst, this.forbidExisting, this.sealed);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> allowExistingTypes() {
                return new WrappingDispatcher(this.protectionDomain, this.packageDefinitionStrategy, this.persistenceHandler, this.childFirst, false, this.sealed);
            }

            @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy.Configurable
            public Configurable<ClassLoader> opened() {
                return new WrappingDispatcher(this.protectionDomain, this.packageDefinitionStrategy, this.persistenceHandler, this.childFirst, this.forbidExisting, false);
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class UsingLookup implements ClassLoadingStrategy<ClassLoader> {
        private final ClassInjector classInjector;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.classInjector.equals(((UsingLookup) obj).classInjector);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.classInjector.hashCode();
        }

        protected UsingLookup(ClassInjector classInjector) {
            this.classInjector = classInjector;
        }

        public static ClassLoadingStrategy<ClassLoader> of(Object obj) {
            return new UsingLookup(ClassInjector.UsingLookup.of(obj));
        }

        public static ClassLoadingStrategy<ClassLoader> withFallback(Callable<?> callable) {
            return withFallback(callable, false);
        }

        public static ClassLoadingStrategy<ClassLoader> withFallback(Callable<?> callable, boolean z) {
            if (ClassInjector.UsingLookup.isAvailable()) {
                try {
                    return of(callable.call());
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            if (ClassInjector.UsingUnsafe.isAvailable()) {
                return new ForUnsafeInjection();
            }
            if (z) {
                return Default.WRAPPER;
            }
            throw new IllegalStateException("Neither lookup or unsafe class injection is available");
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
        public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
            return this.classInjector.inject(map);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class ForBootstrapInjection implements ClassLoadingStrategy<ClassLoader> {
        private final File folder;
        private final Instrumentation instrumentation;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ForBootstrapInjection forBootstrapInjection = (ForBootstrapInjection) obj;
            return this.instrumentation.equals(forBootstrapInjection.instrumentation) && this.folder.equals(forBootstrapInjection.folder);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + this.instrumentation.hashCode()) * 31) + this.folder.hashCode();
        }

        public ForBootstrapInjection(Instrumentation instrumentation, File file) {
            this.instrumentation = instrumentation;
            this.folder = file;
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
        public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
            return (classLoader == null ? ClassInjector.UsingInstrumentation.of(this.folder, ClassInjector.UsingInstrumentation.Target.BOOTSTRAP, this.instrumentation) : new ClassInjector.UsingReflection(classLoader)).inject(map);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class ForUnsafeInjection implements ClassLoadingStrategy<ClassLoader> {

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ProtectionDomain protectionDomain;

        /* JADX WARN: Code duplicated, block: B:19:0x0027 A[RETURN] */
        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProtectionDomain protectionDomain = this.protectionDomain;
            ProtectionDomain protectionDomain2 = ((ForUnsafeInjection) obj).protectionDomain;
            if (protectionDomain2 == null) {
                if (protectionDomain != null) {
                    return false;
                }
                return true;
            }
            if (protectionDomain == null || !protectionDomain.equals(protectionDomain2)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int iHashCode = getClass().hashCode() * 31;
            ProtectionDomain protectionDomain = this.protectionDomain;
            return protectionDomain != null ? iHashCode + protectionDomain.hashCode() : iHashCode;
        }

        public ForUnsafeInjection() {
            this(NO_PROTECTION_DOMAIN);
        }

        public ForUnsafeInjection(@MaybeNull ProtectionDomain protectionDomain) {
            this.protectionDomain = protectionDomain;
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
        public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
            return new ClassInjector.UsingUnsafe(classLoader, this.protectionDomain).inject(map);
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class ForJnaInjection implements ClassLoadingStrategy<ClassLoader> {

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ProtectionDomain protectionDomain;

        /* JADX WARN: Code duplicated, block: B:19:0x0027 A[RETURN] */
        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProtectionDomain protectionDomain = this.protectionDomain;
            ProtectionDomain protectionDomain2 = ((ForJnaInjection) obj).protectionDomain;
            if (protectionDomain2 == null) {
                if (protectionDomain != null) {
                    return false;
                }
                return true;
            }
            if (protectionDomain == null || !protectionDomain.equals(protectionDomain2)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int iHashCode = getClass().hashCode() * 31;
            ProtectionDomain protectionDomain = this.protectionDomain;
            return protectionDomain != null ? iHashCode + protectionDomain.hashCode() : iHashCode;
        }

        public ForJnaInjection() {
            this(NO_PROTECTION_DOMAIN);
        }

        public ForJnaInjection(@MaybeNull ProtectionDomain protectionDomain) {
            this.protectionDomain = protectionDomain;
        }

        @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
        public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
            return new ClassInjector.UsingUnsafe(classLoader, this.protectionDomain).inject(map);
        }
    }
}
