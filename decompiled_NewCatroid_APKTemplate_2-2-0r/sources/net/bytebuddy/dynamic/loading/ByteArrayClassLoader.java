package net.bytebuddy.dynamic.loading;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import net.bytebuddy.utility.GraalImageCode;
import net.bytebuddy.utility.JavaModule;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public class ByteArrayClassLoader extends InjectionClassLoader {
    private static final boolean ACCESS_CONTROLLER;
    private static final int FROM_BEGINNING = 0;

    @AlwaysNull
    private static final URL NO_URL;
    private static final PackageLookupStrategy PACKAGE_LOOKUP_STRATEGY;
    protected static final SynchronizationStrategy.Initializable SYNCHRONIZATION_STRATEGY;
    public static final String URL_SCHEMA = "bytebuddy";

    @MaybeNull
    protected final Object accessControlContext;
    protected final ClassFilePostProcessor classFilePostProcessor;
    protected final PackageDefinitionStrategy packageDefinitionStrategy;
    protected final PersistenceHandler persistenceHandler;

    @MaybeNull
    protected final ProtectionDomain protectionDomain;
    protected final ConcurrentMap<String, byte[]> typeDefinitions;

    @MaybeNull
    @AccessControllerPlugin.Enhance
    private static Object getContext() {
        if (ACCESS_CONTROLLER) {
            return AccessController.getContext();
        }
        return null;
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
        NO_URL = null;
        PACKAGE_LOOKUP_STRATEGY = (PackageLookupStrategy) doPrivileged(PackageLookupStrategy.CreationAction.INSTANCE);
        SYNCHRONIZATION_STRATEGY = (SynchronizationStrategy.Initializable) doPrivileged(SynchronizationStrategy.CreationAction.INSTANCE);
        doRegisterAsParallelCapable();
    }

    private static void doRegisterAsParallelCapable() {
        try {
            Method declaredMethod = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(null, new Object[0]);
        } catch (Throwable unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @AccessControllerPlugin.Enhance
    public static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
        return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map) {
        this(classLoader, true, map);
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map) {
        this(classLoader, z, map, PersistenceHandler.LATENT);
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map, PersistenceHandler persistenceHandler) {
        this(classLoader, true, map, persistenceHandler);
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map, PersistenceHandler persistenceHandler) {
        this(classLoader, z, map, ClassLoadingStrategy.NO_PROTECTION_DOMAIN, persistenceHandler, PackageDefinitionStrategy.Trivial.INSTANCE);
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy) {
        this(classLoader, true, map, protectionDomain, persistenceHandler, packageDefinitionStrategy);
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy) {
        this(classLoader, z, map, protectionDomain, persistenceHandler, packageDefinitionStrategy, ClassFilePostProcessor.NoOp.INSTANCE);
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy, ClassFilePostProcessor classFilePostProcessor) {
        this(classLoader, true, map, protectionDomain, persistenceHandler, packageDefinitionStrategy, classFilePostProcessor);
    }

    public ByteArrayClassLoader(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy, ClassFilePostProcessor classFilePostProcessor) {
        super(classLoader, z);
        this.typeDefinitions = new ConcurrentHashMap(map);
        this.protectionDomain = protectionDomain;
        this.persistenceHandler = persistenceHandler;
        this.packageDefinitionStrategy = packageDefinitionStrategy;
        this.classFilePostProcessor = classFilePostProcessor;
        this.accessControlContext = getContext();
    }

    @AccessControllerPlugin.Enhance
    private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction, @MaybeNull Object obj) {
        return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction, (AccessControlContext) obj) : privilegedAction.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Object methodHandle() throws Exception {
        return Class.forName("java.lang.invoke.MethodHandles").getMethod("lookup", new Class[0]).invoke(null, new Object[0]);
    }

    public static Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
        return load(classLoader, map, ClassLoadingStrategy.NO_PROTECTION_DOMAIN, PersistenceHandler.LATENT, PackageDefinitionStrategy.Trivial.INSTANCE, false, true);
    }

    public static Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy, boolean z, boolean z2) {
        HashMap map2 = new HashMap();
        for (Map.Entry<TypeDescription, byte[]> entry : map.entrySet()) {
            map2.put(entry.getKey().getName(), entry.getValue());
        }
        ByteArrayClassLoader byteArrayClassLoader = new ByteArrayClassLoader(classLoader, z2, map2, protectionDomain, persistenceHandler, packageDefinitionStrategy, ClassFilePostProcessor.NoOp.INSTANCE);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (TypeDescription typeDescription : map.keySet()) {
            try {
                Class<?> cls = Class.forName(typeDescription.getName(), false, byteArrayClassLoader);
                if (!GraalImageCode.getCurrent().isNativeImageExecution() && z && cls.getClassLoader() != byteArrayClassLoader) {
                    throw new IllegalStateException("Class already loaded: " + cls);
                }
                linkedHashMap.put(typeDescription, cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Cannot load class " + typeDescription, e);
            }
        }
        return linkedHashMap;
    }

    @Override // net.bytebuddy.dynamic.loading.InjectionClassLoader
    protected Map<String, Class<?>> doDefineClasses(Map<String, byte[]> map) throws ClassNotFoundException {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, byte[]> entry : map.entrySet()) {
            map2.put(entry.getKey(), this.typeDefinitions.putIfAbsent(entry.getKey(), entry.getValue()));
        }
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (String str : map.keySet()) {
                synchronized (SYNCHRONIZATION_STRATEGY.initialize().getClassLoadingLock(this, str)) {
                    linkedHashMap.put(str, loadClass(str));
                }
            }
            for (Map.Entry entry2 : map2.entrySet()) {
                if (entry2.getValue() == null) {
                    this.persistenceHandler.release((String) entry2.getKey(), this.typeDefinitions);
                } else {
                    this.typeDefinitions.put((String) entry2.getKey(), (byte[]) entry2.getValue());
                }
            }
            return linkedHashMap;
        } catch (Throwable th) {
            for (Map.Entry entry3 : map2.entrySet()) {
                if (entry3.getValue() == null) {
                    this.persistenceHandler.release((String) entry3.getKey(), this.typeDefinitions);
                } else {
                    this.typeDefinitions.put((String) entry3.getKey(), (byte[]) entry3.getValue());
                }
            }
            throw th;
        }
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String str) throws ClassNotFoundException {
        byte[] bArrLookup = this.persistenceHandler.lookup(str, this.typeDefinitions);
        if (bArrLookup == null) {
            throw new ClassNotFoundException(str);
        }
        return (Class) doPrivileged(new ClassDefinitionAction(str, this.classFilePostProcessor.transform(this, str, this.protectionDomain, bArrLookup)), this.accessControlContext);
    }

    @Override // java.lang.ClassLoader
    @MaybeNull
    protected URL findResource(String str) {
        return this.persistenceHandler.url(str, this.typeDefinitions);
    }

    @Override // java.lang.ClassLoader
    protected Enumeration<URL> findResources(String str) {
        URL url = this.persistenceHandler.url(str, this.typeDefinitions);
        return url == null ? EmptyEnumeration.INSTANCE : new SingletonEnumeration(url);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @MaybeNull
    public Package doGetPackage(String str) {
        return getPackage(str);
    }

    protected interface SynchronizationStrategy {

        public enum ForLegacyVm implements SynchronizationStrategy, Initializable {
            INSTANCE;

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.SynchronizationStrategy
            public Object getClassLoadingLock(ByteArrayClassLoader byteArrayClassLoader, String str) {
                return byteArrayClassLoader;
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.SynchronizationStrategy.Initializable
            public SynchronizationStrategy initialize() {
                return this;
            }
        }

        public interface Initializable {
            SynchronizationStrategy initialize();
        }

        Object getClassLoadingLock(ByteArrayClassLoader byteArrayClassLoader, String str);

        public enum CreationAction implements PrivilegedAction<Initializable> {
            INSTANCE;

            @Override // java.security.PrivilegedAction
            public Initializable run() {
                try {
                    try {
                        Class<?> cls = Class.forName("java.lang.invoke.MethodType");
                        Class<?> cls2 = Class.forName("java.lang.invoke.MethodHandle");
                        return new ForJava8CapableVm(Class.forName("java.lang.invoke.MethodHandles$Lookup").getMethod("findVirtual", Class.class, String.class, cls).invoke(ByteArrayClassLoader.methodHandle(), ClassLoader.class, "getClassLoadingLock", cls.getMethod("methodType", Class.class, Class[].class).invoke(null, Object.class, new Class[]{String.class})), cls2.getMethod("bindTo", Object.class), cls2.getMethod("invokeWithArguments", Object[].class));
                    } catch (Exception unused) {
                        return (ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5).isAtLeast(ClassFileVersion.JAVA_V9) && ByteArrayClassLoader.class.getClassLoader() == null) ? ForLegacyVm.INSTANCE : new ForJava7CapableVm(ClassLoader.class.getDeclaredMethod("getClassLoadingLock", String.class));
                    }
                } catch (Exception unused2) {
                    return ForLegacyVm.INSTANCE;
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForJava7CapableVm implements SynchronizationStrategy, Initializable {
            private final Method method;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.method.equals(((ForJava7CapableVm) obj).method);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.method.hashCode();
            }

            protected ForJava7CapableVm(Method method) {
                this.method = method;
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.SynchronizationStrategy
            public Object getClassLoadingLock(ByteArrayClassLoader byteArrayClassLoader, String str) {
                try {
                    return this.method.invoke(byteArrayClassLoader, str);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                } catch (InvocationTargetException e2) {
                    throw new IllegalStateException(e2.getTargetException());
                }
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.SynchronizationStrategy.Initializable
            public SynchronizationStrategy initialize() {
                try {
                    this.method.setAccessible(true);
                    return this;
                } catch (Exception unused) {
                    return ForLegacyVm.INSTANCE;
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForJava8CapableVm implements SynchronizationStrategy, Initializable {
            private final Method bindTo;
            private final Method invokeWithArguments;
            private final Object methodHandle;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                ForJava8CapableVm forJava8CapableVm = (ForJava8CapableVm) obj;
                return this.methodHandle.equals(forJava8CapableVm.methodHandle) && this.bindTo.equals(forJava8CapableVm.bindTo) && this.invokeWithArguments.equals(forJava8CapableVm.invokeWithArguments);
            }

            public int hashCode() {
                return (((((getClass().hashCode() * 31) + this.methodHandle.hashCode()) * 31) + this.bindTo.hashCode()) * 31) + this.invokeWithArguments.hashCode();
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.SynchronizationStrategy.Initializable
            public SynchronizationStrategy initialize() {
                return this;
            }

            protected ForJava8CapableVm(Object obj, Method method, Method method2) {
                this.methodHandle = obj;
                this.bindTo = method;
                this.invokeWithArguments = method2;
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.SynchronizationStrategy
            public Object getClassLoadingLock(ByteArrayClassLoader byteArrayClassLoader, String str) {
                try {
                    return this.invokeWithArguments.invoke(this.bindTo.invoke(this.methodHandle, byteArrayClassLoader), new Object[]{str});
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                } catch (InvocationTargetException e2) {
                    throw new IllegalStateException(e2.getTargetException());
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance(includeSyntheticFields = true)
    protected class ClassDefinitionAction implements PrivilegedAction<Class<?>> {
        private final byte[] binaryRepresentation;

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final String f359name;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ClassDefinitionAction classDefinitionAction = (ClassDefinitionAction) obj;
            return this.f359name.equals(classDefinitionAction.f359name) && Arrays.equals(this.binaryRepresentation, classDefinitionAction.binaryRepresentation) && ByteArrayClassLoader.this.equals(ByteArrayClassLoader.this);
        }

        public int hashCode() {
            return (((((getClass().hashCode() * 31) + this.f359name.hashCode()) * 31) + Arrays.hashCode(this.binaryRepresentation)) * 31) + ByteArrayClassLoader.this.hashCode();
        }

        protected ClassDefinitionAction(String str, byte[] bArr) {
            this.f359name = str;
            this.binaryRepresentation = bArr;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.security.PrivilegedAction
        public Class<?> run() {
            int iLastIndexOf = this.f359name.lastIndexOf(46);
            if (iLastIndexOf != -1) {
                String strSubstring = this.f359name.substring(0, iLastIndexOf);
                PackageDefinitionStrategy.Definition definitionDefine = ByteArrayClassLoader.this.packageDefinitionStrategy.define(ByteArrayClassLoader.this, strSubstring, this.f359name);
                if (definitionDefine.isDefined()) {
                    Package packageApply = ByteArrayClassLoader.PACKAGE_LOOKUP_STRATEGY.apply(ByteArrayClassLoader.this, strSubstring);
                    if (packageApply == null) {
                        ByteArrayClassLoader.this.definePackage(strSubstring, definitionDefine.getSpecificationTitle(), definitionDefine.getSpecificationVersion(), definitionDefine.getSpecificationVendor(), definitionDefine.getImplementationTitle(), definitionDefine.getImplementationVersion(), definitionDefine.getImplementationVendor(), definitionDefine.getSealBase());
                    } else if (!definitionDefine.isCompatibleTo(packageApply)) {
                        throw new SecurityException("Sealing violation for package " + strSubstring);
                    }
                }
            }
            ByteArrayClassLoader byteArrayClassLoader = ByteArrayClassLoader.this;
            String str = this.f359name;
            byte[] bArr = this.binaryRepresentation;
            return byteArrayClassLoader.defineClass(str, bArr, 0, bArr.length, byteArrayClassLoader.protectionDomain);
        }
    }

    protected interface PackageLookupStrategy {
        @MaybeNull
        Package apply(ByteArrayClassLoader byteArrayClassLoader, String str);

        public enum CreationAction implements PrivilegedAction<PackageLookupStrategy> {
            INSTANCE;

            @Override // java.security.PrivilegedAction
            public PackageLookupStrategy run() {
                if (JavaModule.isSupported()) {
                    try {
                        return new ForJava9CapableVm(ClassLoader.class.getMethod("getDefinedPackage", String.class));
                    } catch (Exception unused) {
                        return ForLegacyVm.INSTANCE;
                    }
                }
                return ForLegacyVm.INSTANCE;
            }
        }

        public enum ForLegacyVm implements PackageLookupStrategy {
            INSTANCE;

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PackageLookupStrategy
            @MaybeNull
            public Package apply(ByteArrayClassLoader byteArrayClassLoader, String str) {
                return byteArrayClassLoader.doGetPackage(str);
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class ForJava9CapableVm implements PackageLookupStrategy {
            private final Method getDefinedPackage;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.getDefinedPackage.equals(((ForJava9CapableVm) obj).getDefinedPackage);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.getDefinedPackage.hashCode();
            }

            protected ForJava9CapableVm(Method method) {
                this.getDefinedPackage = method;
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PackageLookupStrategy
            @MaybeNull
            public Package apply(ByteArrayClassLoader byteArrayClassLoader, String str) {
                try {
                    return (Package) this.getDefinedPackage.invoke(byteArrayClassLoader, str);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                } catch (InvocationTargetException e2) {
                    throw new IllegalStateException(e2.getTargetException());
                }
            }
        }
    }

    public enum PersistenceHandler {
        MANIFEST(true) { // from class: net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler.1
            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler
            protected void release(String str, ConcurrentMap<String, byte[]> concurrentMap) {
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler
            protected byte[] lookup(String str, ConcurrentMap<String, byte[]> concurrentMap) {
                return concurrentMap.get(str);
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler
            protected URL url(String str, ConcurrentMap<String, byte[]> concurrentMap) {
                if (!str.endsWith(".class")) {
                    return ByteArrayClassLoader.NO_URL;
                }
                if (str.startsWith("/")) {
                    str = str.substring(1);
                }
                byte[] bArr = concurrentMap.get(str.replace('/', '.').substring(0, str.length() - ".class".length()));
                return bArr == null ? ByteArrayClassLoader.NO_URL : (URL) ByteArrayClassLoader.doPrivileged(new UrlDefinitionAction(str, bArr));
            }
        },
        LATENT(0 == true ? 1 : 0) { // from class: net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler.2
            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler
            protected byte[] lookup(String str, ConcurrentMap<String, byte[]> concurrentMap) {
                return concurrentMap.remove(str);
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler
            protected URL url(String str, ConcurrentMap<String, byte[]> concurrentMap) {
                return ByteArrayClassLoader.NO_URL;
            }

            @Override // net.bytebuddy.dynamic.loading.ByteArrayClassLoader.PersistenceHandler
            protected void release(String str, ConcurrentMap<String, byte[]> concurrentMap) {
                concurrentMap.remove(str);
            }
        };

        private static final String CLASS_FILE_SUFFIX = ".class";
        private final boolean manifest;

        @MaybeNull
        protected abstract byte[] lookup(String str, ConcurrentMap<String, byte[]> concurrentMap);

        protected abstract void release(String str, ConcurrentMap<String, byte[]> concurrentMap);

        @MaybeNull
        protected abstract URL url(String str, ConcurrentMap<String, byte[]> concurrentMap);

        PersistenceHandler(boolean z) {
            this.manifest = z;
        }

        public boolean isManifest() {
            return this.manifest;
        }

        @HashCodeAndEqualsPlugin.Enhance
        protected static class UrlDefinitionAction implements PrivilegedAction<URL> {
            private static final boolean ACCESS_CONTROLLER;
            private static final Dispatcher DISPATCHER;
            private static final String ENCODING = "UTF-8";
            private static final String NO_FILE = "";
            private static final int NO_PORT = -1;
            private final byte[] binaryRepresentation;
            private final String typeName;

            @JavaDispatcher.Proxied("java.net.URL")
            protected interface Dispatcher {
                @JavaDispatcher.IsConstructor
                @JavaDispatcher.Proxied(TypeProxy.REFLECTION_METHOD)
                URL make(String str, String str2, int i, String str3, URLStreamHandler uRLStreamHandler) throws MalformedURLException;

                @JavaDispatcher.IsStatic
                @JavaDispatcher.Defaults
                @MaybeNull
                @JavaDispatcher.Proxied("of")
                URL of(URI uri, URLStreamHandler uRLStreamHandler) throws MalformedURLException;
            }

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                UrlDefinitionAction urlDefinitionAction = (UrlDefinitionAction) obj;
                return this.typeName.equals(urlDefinitionAction.typeName) && Arrays.equals(this.binaryRepresentation, urlDefinitionAction.binaryRepresentation);
            }

            public int hashCode() {
                return (((getClass().hashCode() * 31) + this.typeName.hashCode()) * 31) + Arrays.hashCode(this.binaryRepresentation);
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
                DISPATCHER = (Dispatcher) doPrivileged(JavaDispatcher.of(Dispatcher.class));
            }

            protected UrlDefinitionAction(String str, byte[] bArr) {
                this.typeName = str;
                this.binaryRepresentation = bArr;
            }

            @AccessControllerPlugin.Enhance
            private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
                return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
            }

            @Override // java.security.PrivilegedAction
            public URL run() {
                try {
                    String strEncode = URLEncoder.encode(this.typeName.replace('.', '/'), "UTF-8");
                    ByteArrayUrlStreamHandler byteArrayUrlStreamHandler = new ByteArrayUrlStreamHandler(this.binaryRepresentation);
                    Dispatcher dispatcher = DISPATCHER;
                    URL urlOf = dispatcher.of(URI.create("bytebuddy://" + strEncode), byteArrayUrlStreamHandler);
                    return urlOf == null ? dispatcher.make(ByteArrayClassLoader.URL_SCHEMA, strEncode, -1, "", byteArrayUrlStreamHandler) : urlOf;
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalStateException("Could not find encoding: UTF-8", e);
                } catch (MalformedURLException e2) {
                    throw new IllegalStateException("Cannot create URL for " + this.typeName, e2);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            protected static class ByteArrayUrlStreamHandler extends URLStreamHandler {
                private final byte[] binaryRepresentation;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && Arrays.equals(this.binaryRepresentation, ((ByteArrayUrlStreamHandler) obj).binaryRepresentation);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + Arrays.hashCode(this.binaryRepresentation);
                }

                protected ByteArrayUrlStreamHandler(byte[] bArr) {
                    this.binaryRepresentation = bArr;
                }

                @Override // java.net.URLStreamHandler
                protected URLConnection openConnection(URL url) {
                    return new ByteArrayUrlConnection(url, new ByteArrayInputStream(this.binaryRepresentation));
                }

                protected static class ByteArrayUrlConnection extends URLConnection {
                    private final InputStream inputStream;

                    protected ByteArrayUrlConnection(URL url, InputStream inputStream) {
                        super(url);
                        this.inputStream = inputStream;
                    }

                    @Override // java.net.URLConnection
                    public void connect() {
                        this.connected = true;
                    }

                    @Override // java.net.URLConnection
                    public InputStream getInputStream() {
                        connect();
                        return this.inputStream;
                    }
                }
            }
        }
    }

    public static class ChildFirst extends ByteArrayClassLoader {
        private static final String CLASS_FILE_SUFFIX = ".class";

        static {
            doRegisterAsParallelCapable();
        }

        private static void doRegisterAsParallelCapable() {
            try {
                Method declaredMethod = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(null, new Object[0]);
            } catch (Throwable unused) {
            }
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map) {
            super(classLoader, map);
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map) {
            super(classLoader, z, map);
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map, PersistenceHandler persistenceHandler) {
            super(classLoader, map, persistenceHandler);
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map, PersistenceHandler persistenceHandler) {
            super(classLoader, z, map, persistenceHandler);
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy) {
            super(classLoader, map, protectionDomain, persistenceHandler, packageDefinitionStrategy);
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy) {
            super(classLoader, z, map, protectionDomain, persistenceHandler, packageDefinitionStrategy);
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy, ClassFilePostProcessor classFilePostProcessor) {
            super(classLoader, map, protectionDomain, persistenceHandler, packageDefinitionStrategy, classFilePostProcessor);
        }

        public ChildFirst(@MaybeNull ClassLoader classLoader, boolean z, Map<String, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy, ClassFilePostProcessor classFilePostProcessor) {
            super(classLoader, z, map, protectionDomain, persistenceHandler, packageDefinitionStrategy, classFilePostProcessor);
        }

        public static Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
            return load(classLoader, map, ClassLoadingStrategy.NO_PROTECTION_DOMAIN, PersistenceHandler.LATENT, PackageDefinitionStrategy.Trivial.INSTANCE, false, true);
        }

        public static Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map, @MaybeNull ProtectionDomain protectionDomain, PersistenceHandler persistenceHandler, PackageDefinitionStrategy packageDefinitionStrategy, boolean z, boolean z2) {
            HashMap map2 = new HashMap();
            for (Map.Entry<TypeDescription, byte[]> entry : map.entrySet()) {
                map2.put(entry.getKey().getName(), entry.getValue());
            }
            ChildFirst childFirst = new ChildFirst(classLoader, z2, map2, protectionDomain, persistenceHandler, packageDefinitionStrategy, ClassFilePostProcessor.NoOp.INSTANCE);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (TypeDescription typeDescription : map.keySet()) {
                try {
                    Class<?> cls = Class.forName(typeDescription.getName(), false, childFirst);
                    if (!GraalImageCode.getCurrent().isNativeImageExecution() && z && cls.getClassLoader() != childFirst) {
                        throw new IllegalStateException("Class already loaded: " + cls);
                    }
                    linkedHashMap.put(typeDescription, cls);
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException("Cannot load class " + typeDescription, e);
                }
            }
            return linkedHashMap;
        }

        @Override // java.lang.ClassLoader
        protected Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
            synchronized (SYNCHRONIZATION_STRATEGY.initialize().getClassLoadingLock(this, str)) {
                Class<?> clsFindLoadedClass = findLoadedClass(str);
                if (clsFindLoadedClass != null) {
                    return clsFindLoadedClass;
                }
                try {
                    Class<?> clsFindClass = findClass(str);
                    if (z) {
                        resolveClass(clsFindClass);
                    }
                    return clsFindClass;
                } catch (ClassNotFoundException unused) {
                    return super.loadClass(str, z);
                }
            }
        }

        @Override // java.lang.ClassLoader
        public URL getResource(String str) {
            URL url = this.persistenceHandler.url(str, this.typeDefinitions);
            return (url != null || isShadowed(str)) ? url : super.getResource(str);
        }

        @Override // java.lang.ClassLoader
        public Enumeration<URL> getResources(String str) throws IOException {
            URL url = this.persistenceHandler.url(str, this.typeDefinitions);
            if (url == null) {
                return super.getResources(str);
            }
            return new PrependingEnumeration(url, super.getResources(str));
        }

        private boolean isShadowed(String str) {
            boolean z = false;
            if (this.persistenceHandler.isManifest() || !str.endsWith(".class")) {
                return false;
            }
            synchronized (this) {
                String strSubstring = str.replace('/', '.').substring(0, str.length() - ".class".length());
                if (this.typeDefinitions.containsKey(strSubstring)) {
                    return true;
                }
                Class clsFindLoadedClass = findLoadedClass(strSubstring);
                if (clsFindLoadedClass != null && clsFindLoadedClass.getClassLoader() == this) {
                    z = true;
                }
                return z;
            }
        }

        protected static class PrependingEnumeration implements Enumeration<URL> {
            private final Enumeration<URL> enumeration;

            @MaybeNull
            private URL nextElement;

            protected PrependingEnumeration(URL url, Enumeration<URL> enumeration) {
                this.nextElement = url;
                this.enumeration = enumeration;
            }

            @Override // java.util.Enumeration
            public boolean hasMoreElements() {
                return this.nextElement != null && this.enumeration.hasMoreElements();
            }

            @Override // java.util.Enumeration
            public URL nextElement() {
                if (this.nextElement != null && this.enumeration.hasMoreElements()) {
                    try {
                        return this.nextElement;
                    } finally {
                        this.nextElement = this.enumeration.nextElement();
                    }
                }
                throw new NoSuchElementException();
            }
        }
    }

    protected enum EmptyEnumeration implements Enumeration<URL> {
        INSTANCE;

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return false;
        }

        @Override // java.util.Enumeration
        public URL nextElement() {
            throw new NoSuchElementException();
        }
    }

    protected static class SingletonEnumeration implements Enumeration<URL> {

        @MaybeNull
        private URL element;

        protected SingletonEnumeration(URL url) {
            this.element = url;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.element != null;
        }

        @Override // java.util.Enumeration
        public URL nextElement() {
            URL url = this.element;
            if (url == null) {
                throw new NoSuchElementException();
            }
            this.element = null;
            return url;
        }
    }
}
