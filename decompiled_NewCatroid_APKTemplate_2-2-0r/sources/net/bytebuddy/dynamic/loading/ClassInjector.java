package net.bytebuddy.dynamic.loading;

import com.sun.jna.FunctionMapper;
import com.sun.jna.JNIEnv;
import com.sun.jna.LastErrorException;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Platform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ReflectPermission;
import java.net.URL;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.asm.MemberRemoval;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.description.modifier.ModifierContributor;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.PackageDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.GraalImageCode;
import net.bytebuddy.utility.JavaModule;
import net.bytebuddy.utility.JavaType;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.nullability.UnknownNull;
import net.bytebuddy.utility.privilege.GetMethodAction;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes12.dex */
public interface ClassInjector {
    public static final boolean ALLOW_EXISTING_TYPES = false;
    public static final Permission SUPPRESS_ACCESS_CHECKS = new ReflectPermission("suppressAccessChecks");

    Map<TypeDescription, Class<?>> inject(Map<? extends TypeDescription, byte[]> map);

    Map<String, Class<?>> injectRaw(Map<? extends String, byte[]> map);

    boolean isAlive();

    public static abstract class AbstractBase implements ClassInjector {
        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public Map<TypeDescription, Class<?>> inject(Map<? extends TypeDescription, byte[]> map) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<? extends TypeDescription, byte[]> entry : map.entrySet()) {
                linkedHashMap.put(entry.getKey().getName(), entry.getValue());
            }
            Map<String, Class<?>> mapInjectRaw = injectRaw(linkedHashMap);
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (TypeDescription typeDescription : map.keySet()) {
                linkedHashMap2.put(typeDescription, mapInjectRaw.get(typeDescription.getName()));
            }
            return linkedHashMap2;
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class UsingReflection extends AbstractBase {
        private static final boolean ACCESS_CONTROLLER;
        private static final Method CHECK_PERMISSION;
        private static final Dispatcher.Initializable DISPATCHER;
        private static final System SYSTEM;
        private final ClassLoader classLoader;
        private final boolean forbidExisting;
        private final PackageDefinitionStrategy packageDefinitionStrategy;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ProtectionDomain protectionDomain;

        @JavaDispatcher.Proxied("java.lang.System")
        protected interface System {
            @JavaDispatcher.IsStatic
            @JavaDispatcher.Defaults
            @MaybeNull
            @JavaDispatcher.Proxied("getSecurityManager")
            Object getSecurityManager();
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x0036, code lost:
        
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
                boolean r2 = r4.forbidExisting
                net.bytebuddy.dynamic.loading.ClassInjector$UsingReflection r5 = (net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection) r5
                boolean r3 = r5.forbidExisting
                if (r2 == r3) goto L1c
                return r1
            L1c:
                java.lang.ClassLoader r2 = r4.classLoader
                java.lang.ClassLoader r3 = r5.classLoader
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L27
                return r1
            L27:
                java.security.ProtectionDomain r2 = r4.protectionDomain
                java.security.ProtectionDomain r3 = r5.protectionDomain
                if (r3 == 0) goto L36
                if (r2 == 0) goto L38
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L39
                return r1
            L36:
                if (r2 == 0) goto L39
            L38:
                return r1
            L39:
                net.bytebuddy.dynamic.loading.PackageDefinitionStrategy r2 = r4.packageDefinitionStrategy
                net.bytebuddy.dynamic.loading.PackageDefinitionStrategy r5 = r5.packageDefinitionStrategy
                boolean r5 = r2.equals(r5)
                if (r5 != 0) goto L44
                return r1
            L44:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            int iHashCode = ((getClass().hashCode() * 31) + this.classLoader.hashCode()) * 31;
            ProtectionDomain protectionDomain = this.protectionDomain;
            if (protectionDomain != null) {
                iHashCode += protectionDomain.hashCode();
            }
            return (((iHashCode * 31) + this.packageDefinitionStrategy.hashCode()) * 31) + (this.forbidExisting ? 1 : 0);
        }

        static {
            try {
                Class.forName("java.security.AccessController", false, null);
                ACCESS_CONTROLLER = Boolean.parseBoolean(java.lang.System.getProperty("net.bytebuddy.securitymanager", "true"));
            } catch (ClassNotFoundException unused) {
                ACCESS_CONTROLLER = false;
            } catch (SecurityException unused2) {
                ACCESS_CONTROLLER = true;
            }
            DISPATCHER = (Dispatcher.Initializable) doPrivileged(Dispatcher.CreationAction.INSTANCE);
            SYSTEM = (System) doPrivileged(JavaDispatcher.of(System.class));
            CHECK_PERMISSION = (Method) doPrivileged(new GetMethodAction("java.lang.SecurityManager", "checkPermission", Permission.class));
        }

        public UsingReflection(ClassLoader classLoader) {
            this(classLoader, ClassLoadingStrategy.NO_PROTECTION_DOMAIN);
        }

        public UsingReflection(ClassLoader classLoader, @MaybeNull ProtectionDomain protectionDomain) {
            this(classLoader, protectionDomain, PackageDefinitionStrategy.Trivial.INSTANCE, false);
        }

        public UsingReflection(ClassLoader classLoader, @MaybeNull ProtectionDomain protectionDomain, PackageDefinitionStrategy packageDefinitionStrategy, boolean z) {
            if (classLoader == null) {
                throw new IllegalArgumentException("Cannot inject classes into the bootstrap class loader");
            }
            this.classLoader = classLoader;
            this.protectionDomain = protectionDomain;
            this.packageDefinitionStrategy = packageDefinitionStrategy;
            this.forbidExisting = z;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public boolean isAlive() {
            return isAvailable();
        }

        /* JADX WARN: Code duplicated, block: B:36:0x00fe  */
        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public Map<String, Class<?>> injectRaw(Map<? extends String, byte[]> map) {
            HashMap map2;
            Iterator<Map.Entry<? extends String, byte[]>> it;
            HashMap map3;
            Dispatcher dispatcherInitialize = DISPATCHER.initialize();
            HashMap map4 = new HashMap();
            Iterator<Map.Entry<? extends String, byte[]>> it2 = map.entrySet().iterator();
            while (it2.hasNext()) {
                Map.Entry<? extends String, byte[]> next = it2.next();
                synchronized (dispatcherInitialize.getClassLoadingLock(this.classLoader, next.getKey())) {
                    Class<?> clsFindClass = dispatcherInitialize.findClass(this.classLoader, next.getKey());
                    if (clsFindClass == null) {
                        int iLastIndexOf = next.getKey().lastIndexOf(46);
                        if (iLastIndexOf != -1) {
                            String strSubstring = next.getKey().substring(0, iLastIndexOf);
                            PackageDefinitionStrategy.Definition definitionDefine = this.packageDefinitionStrategy.define(this.classLoader, strSubstring, next.getKey());
                            if (definitionDefine.isDefined()) {
                                Package definedPackage = dispatcherInitialize.getDefinedPackage(this.classLoader, strSubstring);
                                if (definedPackage == null) {
                                    try {
                                        it = it2;
                                        map2 = map4;
                                        try {
                                            dispatcherInitialize.definePackage(this.classLoader, strSubstring, definitionDefine.getSpecificationTitle(), definitionDefine.getSpecificationVersion(), definitionDefine.getSpecificationVendor(), definitionDefine.getImplementationTitle(), definitionDefine.getImplementationVersion(), definitionDefine.getImplementationVendor(), definitionDefine.getSealBase());
                                        } catch (IllegalStateException e) {
                                            e = e;
                                            Package r2 = dispatcherInitialize.getPackage(this.classLoader, strSubstring);
                                            if (r2 == null) {
                                                throw e;
                                            }
                                            if (!definitionDefine.isCompatibleTo(r2)) {
                                                throw new SecurityException("Sealing violation for package " + strSubstring + " (getPackage fallback)");
                                            }
                                        }
                                    } catch (IllegalStateException e2) {
                                        e = e2;
                                        map2 = map4;
                                        it = it2;
                                    }
                                } else {
                                    map2 = map4;
                                    it = it2;
                                    if (!definitionDefine.isCompatibleTo(definedPackage)) {
                                        throw new SecurityException("Sealing violation for package " + strSubstring);
                                    }
                                }
                            } else {
                                map2 = map4;
                                it = it2;
                            }
                        } else {
                            map2 = map4;
                            it = it2;
                        }
                        clsFindClass = dispatcherInitialize.defineClass(this.classLoader, next.getKey(), next.getValue(), this.protectionDomain);
                    } else {
                        map2 = map4;
                        it = it2;
                        if (this.forbidExisting) {
                            throw new IllegalStateException("Cannot inject already loaded type: " + clsFindClass);
                        }
                    }
                    map3 = map2;
                    map3.put(next.getKey(), clsFindClass);
                }
                it2 = it;
                map4 = map3;
            }
            return map4;
        }

        public static boolean isAvailable() {
            return DISPATCHER.isAvailable();
        }

        public static ClassInjector ofSystemClassLoader() {
            return new UsingReflection(ClassLoader.getSystemClassLoader());
        }

        protected interface Dispatcher {

            @AlwaysNull
            public static final Class<?> UNDEFINED = null;

            Class<?> defineClass(ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain);

            Package definePackage(ClassLoader classLoader, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5, @MaybeNull String str6, @MaybeNull String str7, @MaybeNull URL url);

            @MaybeNull
            Class<?> findClass(ClassLoader classLoader, String str);

            Object getClassLoadingLock(ClassLoader classLoader, String str);

            @MaybeNull
            Package getDefinedPackage(ClassLoader classLoader, String str);

            @MaybeNull
            Package getPackage(ClassLoader classLoader, String str);

            public interface Initializable {
                Dispatcher initialize();

                boolean isAvailable();

                @HashCodeAndEqualsPlugin.Enhance
                public static class Unavailable implements Dispatcher, Initializable {
                    private final String message;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.message.equals(((Unavailable) obj).message);
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Object getClassLoadingLock(ClassLoader classLoader, String str) {
                        return classLoader;
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.message.hashCode();
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                    public Dispatcher initialize() {
                        return this;
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                    public boolean isAvailable() {
                        return false;
                    }

                    protected Unavailable(String str) {
                        this.message = str;
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Class<?> findClass(ClassLoader classLoader, String str) {
                        try {
                            return classLoader.loadClass(str);
                        } catch (ClassNotFoundException unused) {
                            return UNDEFINED;
                        }
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Class<?> defineClass(ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                        throw new UnsupportedOperationException("Cannot define class using reflection: " + this.message);
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Package getDefinedPackage(ClassLoader classLoader, String str) {
                        throw new UnsupportedOperationException("Cannot get defined package using reflection: " + this.message);
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Package getPackage(ClassLoader classLoader, String str) {
                        throw new UnsupportedOperationException("Cannot get package using reflection: " + this.message);
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Package definePackage(ClassLoader classLoader, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5, @MaybeNull String str6, @MaybeNull String str7, @MaybeNull URL url) {
                        throw new UnsupportedOperationException("Cannot define package using injection: " + this.message);
                    }
                }
            }

            public enum CreationAction implements PrivilegedAction<Initializable> {
                INSTANCE;

                @Override // java.security.PrivilegedAction
                public Initializable run() {
                    try {
                        if (JavaModule.isSupported()) {
                            if (UsingUnsafe.isAvailable()) {
                                return UsingUnsafeInjection.make();
                            }
                            return UsingUnsafeOverride.make();
                        }
                        return Direct.make();
                    } catch (InvocationTargetException e) {
                        return new Initializable.Unavailable(e.getTargetException().getMessage());
                    } catch (Exception e2) {
                        return new Initializable.Unavailable(e2.getMessage());
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static abstract class Direct implements Dispatcher, Initializable {
                protected final Method defineClass;
                protected final Method definePackage;
                protected final Method findLoadedClass;

                @UnknownNull
                protected final Method getDefinedPackage;
                protected final Method getPackage;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Direct direct = (Direct) obj;
                    return this.findLoadedClass.equals(direct.findLoadedClass) && this.defineClass.equals(direct.defineClass) && this.getDefinedPackage.equals(direct.getDefinedPackage) && this.getPackage.equals(direct.getPackage) && this.definePackage.equals(direct.definePackage);
                }

                public int hashCode() {
                    return (((((((((getClass().hashCode() * 31) + this.findLoadedClass.hashCode()) * 31) + this.defineClass.hashCode()) * 31) + this.getDefinedPackage.hashCode()) * 31) + this.getPackage.hashCode()) * 31) + this.definePackage.hashCode();
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                public boolean isAvailable() {
                    return true;
                }

                protected Direct(Method method, Method method2, @MaybeNull Method method3, Method method4, Method method5) {
                    this.findLoadedClass = method;
                    this.defineClass = method2;
                    this.getDefinedPackage = method3;
                    this.getPackage = method4;
                    this.definePackage = method5;
                }

                protected static Initializable make() throws Exception {
                    Method method = null;
                    if (JavaModule.isSupported()) {
                        try {
                            method = ClassLoader.class.getMethod("getDefinedPackage", String.class);
                        } catch (NoSuchMethodException unused) {
                        }
                    }
                    Method declaredMethod = ClassLoader.class.getDeclaredMethod("getPackage", String.class);
                    declaredMethod.setAccessible(true);
                    Method declaredMethod2 = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
                    declaredMethod2.setAccessible(true);
                    Method declaredMethod3 = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                    declaredMethod3.setAccessible(true);
                    Method declaredMethod4 = ClassLoader.class.getDeclaredMethod("definePackage", String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class);
                    declaredMethod4.setAccessible(true);
                    try {
                        Method declaredMethod5 = ClassLoader.class.getDeclaredMethod("getClassLoadingLock", String.class);
                        declaredMethod5.setAccessible(true);
                        return new ForJava7CapableVm(declaredMethod2, declaredMethod3, method, declaredMethod, declaredMethod4, declaredMethod5);
                    } catch (NoSuchMethodException unused2) {
                        return new ForLegacyVm(declaredMethod2, declaredMethod3, method, declaredMethod, declaredMethod4);
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                public Dispatcher initialize() {
                    Object securityManager = UsingReflection.SYSTEM.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            UsingReflection.CHECK_PERMISSION.invoke(securityManager, ClassInjector.SUPPRESS_ACCESS_CHECKS);
                        } catch (InvocationTargetException e) {
                            return new Unavailable(e.getTargetException().getMessage());
                        } catch (Exception e2) {
                            return new Unavailable(e2.getMessage());
                        }
                    }
                    return this;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> findClass(ClassLoader classLoader, String str) {
                    try {
                        return (Class) this.findLoadedClass.invoke(classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> defineClass(ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    try {
                        return (Class) this.defineClass.invoke(classLoader, str, bArr, 0, Integer.valueOf(bArr.length), protectionDomain);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                @MaybeNull
                public Package getDefinedPackage(ClassLoader classLoader, String str) {
                    Method method = this.getDefinedPackage;
                    if (method == null) {
                        return getPackage(classLoader, str);
                    }
                    try {
                        return (Package) method.invoke(classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package getPackage(ClassLoader classLoader, String str) {
                    try {
                        return (Package) this.getPackage.invoke(classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package definePackage(ClassLoader classLoader, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5, @MaybeNull String str6, @MaybeNull String str7, @MaybeNull URL url) {
                    try {
                        return (Package) this.definePackage.invoke(classLoader, str, str2, str3, str4, str5, str6, str7, url);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                protected static class ForJava7CapableVm extends Direct {
                    private final Method getClassLoadingLock;

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Direct
                    public boolean equals(@MaybeNull Object obj) {
                        if (!super.equals(obj)) {
                            return false;
                        }
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.getClassLoadingLock.equals(((ForJava7CapableVm) obj).getClassLoadingLock);
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Direct
                    public int hashCode() {
                        return (super.hashCode() * 31) + this.getClassLoadingLock.hashCode();
                    }

                    protected ForJava7CapableVm(Method method, Method method2, @MaybeNull Method method3, Method method4, Method method5, Method method6) {
                        super(method, method2, method3, method4, method5);
                        this.getClassLoadingLock = method6;
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Object getClassLoadingLock(ClassLoader classLoader, String str) {
                        try {
                            return this.getClassLoadingLock.invoke(classLoader, str);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException(e);
                        } catch (InvocationTargetException e2) {
                            throw new IllegalStateException(e2.getTargetException());
                        }
                    }
                }

                protected static class ForLegacyVm extends Direct {
                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Object getClassLoadingLock(ClassLoader classLoader, String str) {
                        return classLoader;
                    }

                    protected ForLegacyVm(Method method, Method method2, @MaybeNull Method method3, Method method4, Method method5) {
                        super(method, method2, method3, method4, method5);
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class UsingUnsafeInjection implements Dispatcher, Initializable {
                private final Object accessor;
                private final Method defineClass;
                private final Method definePackage;
                private final Method findLoadedClass;
                private final Method getClassLoadingLock;

                @UnknownNull
                private final Method getDefinedPackage;
                private final Method getPackage;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    UsingUnsafeInjection usingUnsafeInjection = (UsingUnsafeInjection) obj;
                    return this.accessor.equals(usingUnsafeInjection.accessor) && this.findLoadedClass.equals(usingUnsafeInjection.findLoadedClass) && this.defineClass.equals(usingUnsafeInjection.defineClass) && this.getDefinedPackage.equals(usingUnsafeInjection.getDefinedPackage) && this.getPackage.equals(usingUnsafeInjection.getPackage) && this.definePackage.equals(usingUnsafeInjection.definePackage) && this.getClassLoadingLock.equals(usingUnsafeInjection.getClassLoadingLock);
                }

                public int hashCode() {
                    return (((((((((((((getClass().hashCode() * 31) + this.accessor.hashCode()) * 31) + this.findLoadedClass.hashCode()) * 31) + this.defineClass.hashCode()) * 31) + this.getDefinedPackage.hashCode()) * 31) + this.getPackage.hashCode()) * 31) + this.definePackage.hashCode()) * 31) + this.getClassLoadingLock.hashCode();
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                public boolean isAvailable() {
                    return true;
                }

                protected UsingUnsafeInjection(Object obj, Method method, Method method2, @MaybeNull Method method3, Method method4, Method method5, Method method6) {
                    this.accessor = obj;
                    this.findLoadedClass = method;
                    this.defineClass = method2;
                    this.getDefinedPackage = method3;
                    this.getPackage = method4;
                    this.definePackage = method5;
                    this.getClassLoadingLock = method6;
                }

                protected static Initializable make() throws Exception {
                    Method declaredMethod;
                    int i;
                    DynamicType.Builder builderIntercept;
                    char c;
                    char c2;
                    Method method;
                    if (Boolean.parseBoolean(java.lang.System.getProperty(UsingUnsafe.SAFE_PROPERTY, Boolean.toString(GraalImageCode.getCurrent().isDefined())))) {
                        return new Initializable.Unavailable("Use of Unsafe was disabled by system property");
                    }
                    Class<?> cls = Class.forName("sun.misc.Unsafe");
                    Field declaredField = cls.getDeclaredField("theUnsafe");
                    declaredField.setAccessible(true);
                    Object obj = declaredField.get(null);
                    if (JavaModule.isSupported()) {
                        try {
                            declaredMethod = ClassLoader.class.getDeclaredMethod("getDefinedPackage", String.class);
                        } catch (NoSuchMethodException unused) {
                            declaredMethod = null;
                        }
                    } else {
                        declaredMethod = null;
                    }
                    DynamicType.Builder builderIntercept2 = new ByteBuddy().with(TypeValidation.DISABLED).subclass(Object.class, (ConstructorStrategy) ConstructorStrategy.Default.NO_CONSTRUCTORS).name(ClassLoader.class.getName() + "$ByteBuddyAccessor$V1").defineMethod("findLoadedClass", Class.class, Visibility.PUBLIC).withParameters(ClassLoader.class, String.class).intercept(MethodCall.invoke(ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class)).onArgument(0).withArgument(1)).defineMethod("defineClass", Class.class, Visibility.PUBLIC).withParameters(ClassLoader.class, String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class).intercept(MethodCall.invoke(ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class)).onArgument(0).withArgument(1, 2, 3, 4, 5)).defineMethod("getPackage", Package.class, Visibility.PUBLIC).withParameters(ClassLoader.class, String.class).intercept(MethodCall.invoke(ClassLoader.class.getDeclaredMethod("getPackage", String.class)).onArgument(0).withArgument(1)).defineMethod("definePackage", Package.class, Visibility.PUBLIC).withParameters(ClassLoader.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class).intercept(MethodCall.invoke(ClassLoader.class.getDeclaredMethod("definePackage", String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class)).onArgument(0).withArgument(1, 2, 3, 4, 5, 6, 7, 8));
                    if (declaredMethod != null) {
                        i = 1;
                        builderIntercept2 = builderIntercept2.defineMethod("getDefinedPackage", Package.class, Visibility.PUBLIC).withParameters(ClassLoader.class, String.class).intercept(MethodCall.invoke(declaredMethod).onArgument(0).withArgument(1));
                    } else {
                        i = 1;
                    }
                    try {
                        ModifierContributor.ForMethod[] forMethodArr = new ModifierContributor.ForMethod[i];
                        forMethodArr[0] = Visibility.PUBLIC;
                        builderIntercept = builderIntercept2.defineMethod("getClassLoadingLock", Object.class, forMethodArr).withParameters(ClassLoader.class, String.class).intercept(MethodCall.invoke(ClassLoader.class.getDeclaredMethod("getClassLoadingLock", String.class)).onArgument(0).withArgument(1));
                    } catch (NoSuchMethodException unused2) {
                        builderIntercept = builderIntercept2.defineMethod("getClassLoadingLock", Object.class, Visibility.PUBLIC).withParameters(ClassLoader.class, String.class).intercept(FixedValue.argument(0));
                    }
                    Class loaded = builderIntercept.make().load(ClassLoadingStrategy.BOOTSTRAP_LOADER, new ClassLoadingStrategy.ForUnsafeInjection()).getLoaded();
                    Object objInvoke = cls.getMethod("allocateInstance", Class.class).invoke(obj, loaded);
                    Method method2 = loaded.getMethod("findLoadedClass", ClassLoader.class, String.class);
                    Method method3 = loaded.getMethod("defineClass", ClassLoader.class, String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                    if (declaredMethod != null) {
                        c = 0;
                        c2 = 1;
                        method = loaded.getMethod("getDefinedPackage", ClassLoader.class, String.class);
                    } else {
                        c = 0;
                        c2 = 1;
                        method = null;
                    }
                    Class<?>[] clsArr = new Class[2];
                    clsArr[c] = ClassLoader.class;
                    clsArr[c2] = String.class;
                    Method method4 = loaded.getMethod("getPackage", clsArr);
                    Class<?>[] clsArr2 = new Class[9];
                    clsArr2[c] = ClassLoader.class;
                    clsArr2[c2] = String.class;
                    clsArr2[2] = String.class;
                    clsArr2[3] = String.class;
                    clsArr2[4] = String.class;
                    clsArr2[5] = String.class;
                    clsArr2[6] = String.class;
                    clsArr2[7] = String.class;
                    clsArr2[8] = URL.class;
                    return new UsingUnsafeInjection(objInvoke, method2, method3, method, method4, loaded.getMethod("definePackage", clsArr2), loaded.getMethod("getClassLoadingLock", ClassLoader.class, String.class));
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                public Dispatcher initialize() {
                    Object securityManager = UsingReflection.SYSTEM.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            UsingReflection.CHECK_PERMISSION.invoke(securityManager, ClassInjector.SUPPRESS_ACCESS_CHECKS);
                        } catch (InvocationTargetException e) {
                            return new Unavailable(e.getTargetException().getMessage());
                        } catch (Exception e2) {
                            return new Unavailable(e2.getMessage());
                        }
                    }
                    return this;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Object getClassLoadingLock(ClassLoader classLoader, String str) {
                    try {
                        return this.getClassLoadingLock.invoke(this.accessor, classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> findClass(ClassLoader classLoader, String str) {
                    try {
                        return (Class) this.findLoadedClass.invoke(this.accessor, classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> defineClass(ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    try {
                        return (Class) this.defineClass.invoke(this.accessor, classLoader, str, bArr, 0, Integer.valueOf(bArr.length), protectionDomain);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                @MaybeNull
                public Package getDefinedPackage(ClassLoader classLoader, String str) {
                    Method method = this.getDefinedPackage;
                    if (method == null) {
                        return getPackage(classLoader, str);
                    }
                    try {
                        return (Package) method.invoke(this.accessor, classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package getPackage(ClassLoader classLoader, String str) {
                    try {
                        return (Package) this.getPackage.invoke(this.accessor, classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package definePackage(ClassLoader classLoader, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5, @MaybeNull String str6, @MaybeNull String str7, @MaybeNull URL url) {
                    try {
                        return (Package) this.definePackage.invoke(this.accessor, classLoader, str, str2, str3, str4, str5, str6, str7, url);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }
            }

            public static abstract class UsingUnsafeOverride implements Dispatcher, Initializable {
                protected final Method defineClass;
                protected final Method definePackage;
                protected final Method findLoadedClass;

                @MaybeNull
                protected final Method getDefinedPackage;
                protected final Method getPackage;

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                public boolean isAvailable() {
                    return true;
                }

                protected UsingUnsafeOverride(Method method, Method method2, @MaybeNull Method method3, Method method4, Method method5) {
                    this.findLoadedClass = method;
                    this.defineClass = method2;
                    this.getDefinedPackage = method3;
                    this.getPackage = method4;
                    this.definePackage = method5;
                }

                protected static Initializable make() throws Exception {
                    Field declaredField;
                    if (Boolean.parseBoolean(java.lang.System.getProperty(UsingUnsafe.SAFE_PROPERTY, Boolean.toString(GraalImageCode.getCurrent().isDefined())))) {
                        return new Initializable.Unavailable("Use of Unsafe was disabled by system property");
                    }
                    Class<?> cls = Class.forName("sun.misc.Unsafe");
                    Field declaredField2 = cls.getDeclaredField("theUnsafe");
                    declaredField2.setAccessible(true);
                    Method method = null;
                    Object obj = declaredField2.get(null);
                    try {
                        declaredField = AccessibleObject.class.getDeclaredField("override");
                    } catch (NoSuchFieldException unused) {
                        declaredField = new ByteBuddy().redefine(AccessibleObject.class).name("net.bytebuddy.mirror." + AccessibleObject.class.getSimpleName()).noNestMate().visit(new MemberRemoval().stripInvokables(ElementMatchers.any())).make().load(AccessibleObject.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER.with(AccessibleObject.class.getProtectionDomain())).getLoaded().getDeclaredField("override");
                    }
                    long jLongValue = ((Long) cls.getMethod("objectFieldOffset", Field.class).invoke(obj, declaredField)).longValue();
                    Method method2 = cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
                    if (JavaModule.isSupported()) {
                        try {
                            method = ClassLoader.class.getMethod("getDefinedPackage", String.class);
                        } catch (NoSuchMethodException unused2) {
                        }
                    }
                    Method declaredMethod = ClassLoader.class.getDeclaredMethod("getPackage", String.class);
                    method2.invoke(obj, declaredMethod, Long.valueOf(jLongValue), true);
                    Method declaredMethod2 = ClassLoader.class.getDeclaredMethod("findLoadedClass", String.class);
                    Method declaredMethod3 = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                    Method declaredMethod4 = ClassLoader.class.getDeclaredMethod("definePackage", String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class);
                    method2.invoke(obj, declaredMethod3, Long.valueOf(jLongValue), true);
                    method2.invoke(obj, declaredMethod2, Long.valueOf(jLongValue), true);
                    method2.invoke(obj, declaredMethod4, Long.valueOf(jLongValue), true);
                    try {
                        Method declaredMethod5 = ClassLoader.class.getDeclaredMethod("getClassLoadingLock", String.class);
                        method2.invoke(obj, declaredMethod5, Long.valueOf(jLongValue), true);
                        try {
                            return new ForJava7CapableVm(declaredMethod2, declaredMethod3, method, declaredMethod, declaredMethod4, declaredMethod5);
                        } catch (NoSuchMethodException unused3) {
                            return new ForLegacyVm(declaredMethod2, declaredMethod3, method, declaredMethod, declaredMethod4);
                        }
                    } catch (NoSuchMethodException unused4) {
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher.Initializable
                public Dispatcher initialize() {
                    Object securityManager = UsingReflection.SYSTEM.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            UsingReflection.CHECK_PERMISSION.invoke(securityManager, ClassInjector.SUPPRESS_ACCESS_CHECKS);
                        } catch (InvocationTargetException e) {
                            return new Unavailable(e.getTargetException().getMessage());
                        } catch (Exception e2) {
                            return new Unavailable(e2.getMessage());
                        }
                    }
                    return this;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> findClass(ClassLoader classLoader, String str) {
                    try {
                        return (Class) this.findLoadedClass.invoke(classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> defineClass(ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    try {
                        return (Class) this.defineClass.invoke(classLoader, str, bArr, 0, Integer.valueOf(bArr.length), protectionDomain);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                @MaybeNull
                public Package getDefinedPackage(ClassLoader classLoader, String str) {
                    Method method = this.getDefinedPackage;
                    if (method == null) {
                        return getPackage(classLoader, str);
                    }
                    try {
                        return (Package) method.invoke(classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package getPackage(ClassLoader classLoader, String str) {
                    try {
                        return (Package) this.getPackage.invoke(classLoader, str);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package definePackage(ClassLoader classLoader, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5, @MaybeNull String str6, @MaybeNull String str7, @MaybeNull URL url) {
                    try {
                        return (Package) this.definePackage.invoke(classLoader, str, str2, str3, str4, str5, str6, str7, url);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }

                @HashCodeAndEqualsPlugin.Enhance
                protected static class ForJava7CapableVm extends UsingUnsafeOverride {
                    private final Method getClassLoadingLock;

                    public boolean equals(@MaybeNull Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        return obj != null && getClass() == obj.getClass() && this.getClassLoadingLock.equals(((ForJava7CapableVm) obj).getClassLoadingLock);
                    }

                    public int hashCode() {
                        return (getClass().hashCode() * 31) + this.getClassLoadingLock.hashCode();
                    }

                    protected ForJava7CapableVm(Method method, Method method2, @MaybeNull Method method3, Method method4, Method method5, Method method6) {
                        super(method, method2, method3, method4, method5);
                        this.getClassLoadingLock = method6;
                    }

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Object getClassLoadingLock(ClassLoader classLoader, String str) {
                        try {
                            return this.getClassLoadingLock.invoke(classLoader, str);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException(e);
                        } catch (InvocationTargetException e2) {
                            throw new IllegalStateException(e2.getTargetException());
                        }
                    }
                }

                protected static class ForLegacyVm extends UsingUnsafeOverride {
                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                    public Object getClassLoadingLock(ClassLoader classLoader, String str) {
                        return classLoader;
                    }

                    protected ForLegacyVm(Method method, Method method2, @MaybeNull Method method3, Method method4, Method method5) {
                        super(method, method2, method3, method4, method5);
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Unavailable implements Dispatcher {
                private final String message;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.message.equals(((Unavailable) obj).message);
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Object getClassLoadingLock(ClassLoader classLoader, String str) {
                    return classLoader;
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.message.hashCode();
                }

                protected Unavailable(String str) {
                    this.message = str;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> findClass(ClassLoader classLoader, String str) {
                    try {
                        return classLoader.loadClass(str);
                    } catch (ClassNotFoundException unused) {
                        return UNDEFINED;
                    }
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Class<?> defineClass(ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    throw new UnsupportedOperationException("Cannot define class using reflection: " + this.message);
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package getDefinedPackage(ClassLoader classLoader, String str) {
                    throw new UnsupportedOperationException("Cannot get defined package using reflection: " + this.message);
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package getPackage(ClassLoader classLoader, String str) {
                    throw new UnsupportedOperationException("Cannot get package using reflection: " + this.message);
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingReflection.Dispatcher
                public Package definePackage(ClassLoader classLoader, String str, @MaybeNull String str2, @MaybeNull String str3, @MaybeNull String str4, @MaybeNull String str5, @MaybeNull String str6, @MaybeNull String str7, @MaybeNull URL url) {
                    throw new UnsupportedOperationException("Cannot define package using injection: " + this.message);
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class UsingLookup extends AbstractBase {
        private static final boolean ACCESS_CONTROLLER;
        private static final MethodHandles METHOD_HANDLES;
        private static final MethodHandles.Lookup METHOD_HANDLES_LOOKUP;
        private static final int PACKAGE_LOOKUP = 8;
        private final Object lookup;

        @JavaDispatcher.Proxied("java.lang.invoke.MethodHandles")
        protected interface MethodHandles {

            @JavaDispatcher.Proxied("java.lang.invoke.MethodHandles$Lookup")
            public interface Lookup {
                @JavaDispatcher.Proxied("defineClass")
                Class<?> defineClass(Object obj, byte[] bArr) throws IllegalAccessException;

                @JavaDispatcher.Proxied("lookupClass")
                Class<?> lookupClass(Object obj);

                @JavaDispatcher.Proxied("lookupModes")
                int lookupModes(Object obj);
            }

            @JavaDispatcher.IsStatic
            @JavaDispatcher.Proxied("privateLookupIn")
            Object privateLookupIn(Class<?> cls, @JavaDispatcher.Proxied("java.lang.invoke.MethodHandles$Lookup") Object obj) throws IllegalAccessException;
        }

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.lookup.equals(((UsingLookup) obj).lookup);
        }

        public int hashCode() {
            return (getClass().hashCode() * 31) + this.lookup.hashCode();
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
            METHOD_HANDLES = (MethodHandles) doPrivileged(JavaDispatcher.of(MethodHandles.class));
            METHOD_HANDLES_LOOKUP = (MethodHandles.Lookup) doPrivileged(JavaDispatcher.of(MethodHandles.Lookup.class));
        }

        protected UsingLookup(Object obj) {
            this.lookup = obj;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        public static UsingLookup of(Object obj) {
            if (!isAvailable()) {
                throw new IllegalStateException("The current VM does not support class definition via method handle lookups");
            }
            if (!JavaType.METHOD_HANDLES_LOOKUP.isInstance(obj)) {
                throw new IllegalArgumentException("Not a method handle lookup: " + obj);
            }
            if ((METHOD_HANDLES_LOOKUP.lookupModes(obj) & 8) == 0) {
                throw new IllegalArgumentException("Lookup does not imply package-access: " + obj);
            }
            return new UsingLookup(obj);
        }

        public Class<?> lookupType() {
            return METHOD_HANDLES_LOOKUP.lookupClass(this.lookup);
        }

        public UsingLookup in(Class<?> cls) {
            try {
                return new UsingLookup(METHOD_HANDLES.privateLookupIn(cls, this.lookup));
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Cannot access " + cls.getName() + " from " + this.lookup, e);
            }
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public boolean isAlive() {
            return isAvailable();
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public Map<String, Class<?>> injectRaw(Map<? extends String, byte[]> map) {
            PackageDescription packageDescription = TypeDescription.ForLoadedType.of(lookupType()).getPackage();
            if (packageDescription == null) {
                throw new IllegalArgumentException("Cannot inject array or primitive type");
            }
            HashMap map2 = new HashMap();
            for (Map.Entry<? extends String, byte[]> entry : map.entrySet()) {
                int iLastIndexOf = entry.getKey().lastIndexOf(46);
                if (!packageDescription.getName().equals(iLastIndexOf == -1 ? "" : entry.getKey().substring(0, iLastIndexOf))) {
                    throw new IllegalArgumentException(entry.getKey() + " must be defined in the same package as " + this.lookup);
                }
                try {
                    map2.put(entry.getKey(), METHOD_HANDLES_LOOKUP.defineClass(this.lookup, entry.getValue()));
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return map2;
        }

        public static boolean isAvailable() {
            return JavaType.MODULE.isAvailable();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class UsingUnsafe extends AbstractBase {
        private static final boolean ACCESS_CONTROLLER;
        private static final Object BOOTSTRAP_LOADER_LOCK;
        private static final Method CHECK_PERMISSION;
        private static final Dispatcher.Initializable DISPATCHER;
        public static final String SAFE_PROPERTY = "net.bytebuddy.safe";
        private static final System SYSTEM;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ClassLoader classLoader;
        private final Dispatcher.Initializable dispatcher;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ProtectionDomain protectionDomain;

        @JavaDispatcher.Proxied("java.lang.System")
        protected interface System {
            @JavaDispatcher.IsStatic
            @JavaDispatcher.Defaults
            @MaybeNull
            @JavaDispatcher.Proxied("getSecurityManager")
            Object getSecurityManager();
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0024, code lost:
        
            if (r2 != null) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0036, code lost:
        
            if (r2 != null) goto L26;
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
                java.lang.ClassLoader r2 = r4.classLoader
                net.bytebuddy.dynamic.loading.ClassInjector$UsingUnsafe r5 = (net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe) r5
                java.lang.ClassLoader r3 = r5.classLoader
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
                java.security.ProtectionDomain r2 = r4.protectionDomain
                java.security.ProtectionDomain r3 = r5.protectionDomain
                if (r3 == 0) goto L36
                if (r2 == 0) goto L38
                boolean r2 = r2.equals(r3)
                if (r2 != 0) goto L39
                return r1
            L36:
                if (r2 == 0) goto L39
            L38:
                return r1
            L39:
                net.bytebuddy.dynamic.loading.ClassInjector$UsingUnsafe$Dispatcher$Initializable r2 = r4.dispatcher
                net.bytebuddy.dynamic.loading.ClassInjector$UsingUnsafe$Dispatcher$Initializable r5 = r5.dispatcher
                boolean r5 = r2.equals(r5)
                if (r5 != 0) goto L44
                return r1
            L44:
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            int iHashCode = getClass().hashCode() * 31;
            ClassLoader classLoader = this.classLoader;
            if (classLoader != null) {
                iHashCode += classLoader.hashCode();
            }
            int iHashCode2 = iHashCode * 31;
            ProtectionDomain protectionDomain = this.protectionDomain;
            if (protectionDomain != null) {
                iHashCode2 += protectionDomain.hashCode();
            }
            return (iHashCode2 * 31) + this.dispatcher.hashCode();
        }

        static {
            try {
                Class.forName("java.security.AccessController", false, null);
                ACCESS_CONTROLLER = Boolean.parseBoolean(java.lang.System.getProperty("net.bytebuddy.securitymanager", "true"));
            } catch (ClassNotFoundException unused) {
                ACCESS_CONTROLLER = false;
            } catch (SecurityException unused2) {
                ACCESS_CONTROLLER = true;
            }
            DISPATCHER = (Dispatcher.Initializable) doPrivileged(Dispatcher.CreationAction.INSTANCE);
            SYSTEM = (System) doPrivileged(JavaDispatcher.of(System.class));
            CHECK_PERMISSION = (Method) doPrivileged(new GetMethodAction("java.lang.SecurityManager", "checkPermission", Permission.class));
            BOOTSTRAP_LOADER_LOCK = new Object();
        }

        public UsingUnsafe(@MaybeNull ClassLoader classLoader) {
            this(classLoader, ClassLoadingStrategy.NO_PROTECTION_DOMAIN);
        }

        public UsingUnsafe(@MaybeNull ClassLoader classLoader, @MaybeNull ProtectionDomain protectionDomain) {
            this(classLoader, protectionDomain, DISPATCHER);
        }

        protected UsingUnsafe(@MaybeNull ClassLoader classLoader, @MaybeNull ProtectionDomain protectionDomain, Dispatcher.Initializable initializable) {
            this.classLoader = classLoader;
            this.protectionDomain = protectionDomain;
            this.dispatcher = initializable;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public boolean isAlive() {
            return this.dispatcher.isAvailable();
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public Map<String, Class<?>> injectRaw(Map<? extends String, byte[]> map) {
            Dispatcher dispatcherInitialize = this.dispatcher.initialize();
            HashMap map2 = new HashMap();
            Object obj = this.classLoader;
            if (obj == null) {
                obj = BOOTSTRAP_LOADER_LOCK;
            }
            synchronized (obj) {
                for (Map.Entry<? extends String, byte[]> entry : map.entrySet()) {
                    try {
                        try {
                            try {
                                map2.put(entry.getKey(), Class.forName(entry.getKey(), false, this.classLoader));
                            } catch (RuntimeException e) {
                                try {
                                    map2.put(entry.getKey(), Class.forName(entry.getKey(), false, this.classLoader));
                                } catch (ClassNotFoundException unused) {
                                    throw e;
                                }
                            }
                        } catch (Error e2) {
                            try {
                                map2.put(entry.getKey(), Class.forName(entry.getKey(), false, this.classLoader));
                            } catch (ClassNotFoundException unused2) {
                                throw e2;
                            }
                        }
                    } catch (ClassNotFoundException unused3) {
                        map2.put(entry.getKey(), dispatcherInitialize.defineClass(this.classLoader, entry.getKey(), entry.getValue(), this.protectionDomain));
                    }
                }
            }
            return map2;
        }

        public static boolean isAvailable() {
            return DISPATCHER.isAvailable();
        }

        public static ClassInjector ofSystemLoader() {
            return new UsingUnsafe(ClassLoader.getSystemClassLoader());
        }

        public static ClassInjector ofPlatformLoader() {
            return new UsingUnsafe(ClassLoader.getSystemClassLoader().getParent());
        }

        public static ClassInjector ofBootLoader() {
            return new UsingUnsafe(ClassLoadingStrategy.BOOTSTRAP_LOADER);
        }

        protected interface Dispatcher {

            public interface Initializable {
                Dispatcher initialize();

                boolean isAvailable();
            }

            Class<?> defineClass(@MaybeNull ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain);

            public enum CreationAction implements PrivilegedAction<Initializable> {
                INSTANCE;

                @Override // java.security.PrivilegedAction
                public Initializable run() {
                    Field declaredField;
                    if (Boolean.parseBoolean(java.lang.System.getProperty(UsingUnsafe.SAFE_PROPERTY, Boolean.toString(GraalImageCode.getCurrent().isDefined())))) {
                        return new Unavailable("Use of Unsafe was disabled by system property");
                    }
                    try {
                        Class<?> cls = Class.forName("sun.misc.Unsafe");
                        Field declaredField2 = cls.getDeclaredField("theUnsafe");
                        declaredField2.setAccessible(true);
                        Object obj = declaredField2.get(null);
                        try {
                            Method method = cls.getMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ClassLoader.class, ProtectionDomain.class);
                            method.setAccessible(true);
                            return new Enabled(obj, method);
                        } catch (Exception e) {
                            try {
                                try {
                                    declaredField = AccessibleObject.class.getDeclaredField("override");
                                } catch (Exception unused) {
                                    throw e;
                                }
                            } catch (NoSuchFieldException unused2) {
                                declaredField = new ByteBuddy().redefine(AccessibleObject.class).name("net.bytebuddy.mirror." + AccessibleObject.class.getSimpleName()).noNestMate().visit(new MemberRemoval().stripInvokables(ElementMatchers.any())).make().load(AccessibleObject.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER.with(AccessibleObject.class.getProtectionDomain())).getLoaded().getDeclaredField("override");
                            }
                            long jLongValue = ((Long) cls.getMethod("objectFieldOffset", Field.class).invoke(obj, declaredField)).longValue();
                            Method method2 = cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
                            Class<?> cls2 = Class.forName("jdk.internal.misc.Unsafe");
                            Field declaredField3 = cls2.getDeclaredField("theUnsafe");
                            method2.invoke(obj, declaredField3, Long.valueOf(jLongValue), true);
                            Method method3 = cls2.getMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ClassLoader.class, ProtectionDomain.class);
                            method2.invoke(obj, method3, Long.valueOf(jLongValue), true);
                            return new Enabled(declaredField3.get(null), method3);
                        }
                    } catch (Exception e2) {
                        return new Unavailable(e2.getMessage());
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Enabled implements Dispatcher, Initializable {
                private final Method defineClass;
                private final Object unsafe;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
                    Enabled enabled = (Enabled) obj;
                    return this.unsafe.equals(enabled.unsafe) && this.defineClass.equals(enabled.defineClass);
                }

                public int hashCode() {
                    return (((getClass().hashCode() * 31) + this.unsafe.hashCode()) * 31) + this.defineClass.hashCode();
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.Dispatcher.Initializable
                public boolean isAvailable() {
                    return true;
                }

                protected Enabled(Object obj, Method method) {
                    this.unsafe = obj;
                    this.defineClass = method;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.Dispatcher.Initializable
                public Dispatcher initialize() {
                    Object securityManager = UsingUnsafe.SYSTEM.getSecurityManager();
                    if (securityManager != null) {
                        try {
                            UsingUnsafe.CHECK_PERMISSION.invoke(securityManager, ClassInjector.SUPPRESS_ACCESS_CHECKS);
                        } catch (InvocationTargetException e) {
                            return new Unavailable(e.getTargetException().getMessage());
                        } catch (Exception e2) {
                            return new Unavailable(e2.getMessage());
                        }
                    }
                    return this;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.Dispatcher
                public Class<?> defineClass(@MaybeNull ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    try {
                        return (Class) this.defineClass.invoke(this.unsafe, str, bArr, 0, Integer.valueOf(bArr.length), classLoader, protectionDomain);
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException(e2.getTargetException());
                    }
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Unavailable implements Dispatcher, Initializable {
                private final String message;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.message.equals(((Unavailable) obj).message);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.message.hashCode();
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.Dispatcher.Initializable
                public boolean isAvailable() {
                    return false;
                }

                protected Unavailable(String str) {
                    this.message = str;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.Dispatcher.Initializable
                public Dispatcher initialize() {
                    throw new UnsupportedOperationException("Could not access Unsafe class: " + this.message);
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.Dispatcher
                public Class<?> defineClass(@MaybeNull ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    throw new UnsupportedOperationException("Could not access Unsafe class: " + this.message);
                }
            }
        }

        @HashCodeAndEqualsPlugin.Enhance
        public static class Factory {
            private final Dispatcher.Initializable dispatcher;

            public boolean equals(@MaybeNull Object obj) {
                if (this == obj) {
                    return true;
                }
                return obj != null && getClass() == obj.getClass() && this.dispatcher.equals(((Factory) obj).dispatcher);
            }

            public int hashCode() {
                return (getClass().hashCode() * 31) + this.dispatcher.hashCode();
            }

            public Factory() {
                this(AccessResolver.Default.INSTANCE);
            }

            public Factory(AccessResolver accessResolver) {
                Dispatcher.Initializable unavailable;
                if (UsingUnsafe.DISPATCHER.isAvailable()) {
                    unavailable = UsingUnsafe.DISPATCHER;
                } else {
                    try {
                        Class<?> cls = Class.forName("jdk.internal.misc.Unsafe");
                        Field declaredField = cls.getDeclaredField("theUnsafe");
                        accessResolver.apply(declaredField);
                        Object obj = declaredField.get(null);
                        Method method = cls.getMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ClassLoader.class, ProtectionDomain.class);
                        accessResolver.apply(method);
                        unavailable = new Dispatcher.Enabled(obj, method);
                    } catch (Exception e) {
                        unavailable = new Dispatcher.Unavailable(e.getMessage());
                    }
                }
                this.dispatcher = unavailable;
            }

            protected Factory(Dispatcher.Initializable initializable) {
                this.dispatcher = initializable;
            }

            public static Factory resolve(Instrumentation instrumentation) {
                return resolve(instrumentation, false);
            }

            public static Factory resolve(Instrumentation instrumentation, boolean z) {
                if (UsingUnsafe.isAvailable() || !JavaModule.isSupported()) {
                    return new Factory();
                }
                try {
                    Class<?> cls = Class.forName("jdk.internal.misc.Unsafe");
                    PackageDescription.ForLoadedPackage forLoadedPackage = new PackageDescription.ForLoadedPackage(cls.getPackage());
                    JavaModule javaModuleOfType = JavaModule.ofType(cls);
                    if (javaModuleOfType.isOpened(forLoadedPackage, JavaModule.ofType(UsingUnsafe.class))) {
                        return new Factory();
                    }
                    if (z) {
                        JavaModule javaModuleOfType2 = JavaModule.ofType(AccessResolver.Default.class);
                        UsingInstrumentation.redefineModule(instrumentation, javaModuleOfType, Collections.singleton(javaModuleOfType2), Collections.emptyMap(), Collections.singletonMap(forLoadedPackage.getName(), Collections.singleton(javaModuleOfType2)), Collections.emptySet(), Collections.emptyMap());
                        return new Factory();
                    }
                    Class loaded = new ByteBuddy().subclass(AccessResolver.class).method(ElementMatchers.named("apply")).intercept(MethodCall.invoke(AccessibleObject.class.getMethod("setAccessible", Boolean.TYPE)).onArgument(0).with(true)).make().load(AccessResolver.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER.with(AccessResolver.class.getProtectionDomain())).getLoaded();
                    JavaModule javaModuleOfType3 = JavaModule.ofType(loaded);
                    UsingInstrumentation.redefineModule(instrumentation, javaModuleOfType, Collections.singleton(javaModuleOfType3), Collections.emptyMap(), Collections.singletonMap(forLoadedPackage.getName(), Collections.singleton(javaModuleOfType3)), Collections.emptySet(), Collections.emptyMap());
                    return new Factory((AccessResolver) loaded.getConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (Exception e) {
                    return new Factory(new Dispatcher.Unavailable(e.getMessage()));
                }
            }

            public boolean isAvailable() {
                return this.dispatcher.isAvailable();
            }

            public ClassInjector make(@MaybeNull ClassLoader classLoader) {
                return make(classLoader, ClassLoadingStrategy.NO_PROTECTION_DOMAIN);
            }

            public ClassInjector make(@MaybeNull ClassLoader classLoader, @MaybeNull ProtectionDomain protectionDomain) {
                return new UsingUnsafe(classLoader, protectionDomain, this.dispatcher);
            }

            public interface AccessResolver {
                void apply(AccessibleObject accessibleObject);

                public enum Default implements AccessResolver {
                    INSTANCE;

                    @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingUnsafe.Factory.AccessResolver
                    public void apply(AccessibleObject accessibleObject) {
                        accessibleObject.setAccessible(true);
                    }
                }
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class UsingInstrumentation extends AbstractBase {
        private static final boolean ACCESS_CONTROLLER;
        private static final String CLASS_FILE_EXTENSION = ".class";
        private static final Dispatcher DISPATCHER;
        private static final String JAR = "jar";
        private final File folder;
        private final Instrumentation instrumentation;
        private final RandomString randomString;
        private final Target target;

        @JavaDispatcher.Proxied("java.lang.instrument.Instrumentation")
        protected interface Dispatcher {
            @JavaDispatcher.Proxied("appendToBootstrapClassLoaderSearch")
            void appendToBootstrapClassLoaderSearch(Instrumentation instrumentation, JarFile jarFile);

            @JavaDispatcher.Proxied("appendToSystemClassLoaderSearch")
            void appendToSystemClassLoaderSearch(Instrumentation instrumentation, JarFile jarFile);

            @JavaDispatcher.Proxied("isModifiableModule")
            boolean isModifiableModule(Instrumentation instrumentation, @JavaDispatcher.Proxied("java.lang.Module") Object obj);

            @JavaDispatcher.Proxied("redefineModule")
            void redefineModule(Instrumentation instrumentation, @JavaDispatcher.Proxied("java.lang.Module") Object obj, Set<?> set, Map<String, Set<?>> map, Map<String, Set<?>> map2, Set<Class<?>> set2, Map<Class<?>, List<Class<?>>> map3);
        }

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            UsingInstrumentation usingInstrumentation = (UsingInstrumentation) obj;
            return this.target.equals(usingInstrumentation.target) && this.instrumentation.equals(usingInstrumentation.instrumentation) && this.folder.equals(usingInstrumentation.folder) && this.randomString.equals(usingInstrumentation.randomString);
        }

        public int hashCode() {
            return (((((((getClass().hashCode() * 31) + this.instrumentation.hashCode()) * 31) + this.target.hashCode()) * 31) + this.folder.hashCode()) * 31) + this.randomString.hashCode();
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

        protected UsingInstrumentation(File file, Target target, Instrumentation instrumentation, RandomString randomString) {
            this.folder = file;
            this.target = target;
            this.instrumentation = instrumentation;
            this.randomString = randomString;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        public static void redefineModule(Instrumentation instrumentation, JavaModule javaModule, Set<JavaModule> set, Map<String, Set<JavaModule>> map, Map<String, Set<JavaModule>> map2, Set<Class<?>> set2, Map<Class<?>, List<Class<?>>> map3) {
            if (!DISPATCHER.isModifiableModule(instrumentation, javaModule.unwrap())) {
                throw new IllegalArgumentException("Cannot modify module: " + javaModule);
            }
            HashSet hashSet = new HashSet();
            Iterator<JavaModule> it = set.iterator();
            while (it.hasNext()) {
                hashSet.add(it.next().unwrap());
            }
            HashMap map4 = new HashMap();
            for (Map.Entry<String, Set<JavaModule>> entry : map.entrySet()) {
                HashSet hashSet2 = new HashSet();
                Iterator<JavaModule> it2 = entry.getValue().iterator();
                while (it2.hasNext()) {
                    hashSet2.add(it2.next().unwrap());
                }
                map4.put(entry.getKey(), hashSet2);
            }
            HashMap map5 = new HashMap();
            for (Map.Entry<String, Set<JavaModule>> entry2 : map2.entrySet()) {
                HashSet hashSet3 = new HashSet();
                Iterator<JavaModule> it3 = entry2.getValue().iterator();
                while (it3.hasNext()) {
                    hashSet3.add(it3.next().unwrap());
                }
                map5.put(entry2.getKey(), hashSet3);
            }
            DISPATCHER.redefineModule(instrumentation, javaModule.unwrap(), hashSet, map4, map5, set2, map3);
        }

        public static ClassInjector of(File file, Target target, Instrumentation instrumentation) {
            return new UsingInstrumentation(file, target, instrumentation, new RandomString());
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public boolean isAlive() {
            return isAvailable();
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public Map<String, Class<?>> injectRaw(Map<? extends String, byte[]> map) {
            File file = new File(this.folder, "jar" + this.randomString.nextString() + ".jar");
            try {
                if (!file.createNewFile()) {
                    throw new IllegalStateException("Cannot create file " + file);
                }
                try {
                    JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(file));
                    try {
                        for (Map.Entry<? extends String, byte[]> entry : map.entrySet()) {
                            jarOutputStream.putNextEntry(new JarEntry(entry.getKey().replace('.', '/') + ".class"));
                            jarOutputStream.write(entry.getValue());
                        }
                        jarOutputStream.close();
                        JarFile jarFile = new JarFile(file, false);
                        try {
                            this.target.inject(this.instrumentation, jarFile);
                            jarFile.close();
                            HashMap map2 = new HashMap();
                            for (String str : map.keySet()) {
                                map2.put(str, Class.forName(str, false, this.target.getClassLoader()));
                            }
                            if (!file.delete()) {
                                file.deleteOnExit();
                            }
                            return map2;
                        } catch (Throwable th) {
                            jarFile.close();
                            throw th;
                        }
                    } catch (Throwable th2) {
                        jarOutputStream.close();
                        throw th2;
                    }
                } catch (Throwable th3) {
                    if (!file.delete()) {
                        file.deleteOnExit();
                    }
                    throw th3;
                }
            } catch (IOException e) {
                throw new IllegalStateException("Cannot write jar file to disk", e);
            } catch (ClassNotFoundException e2) {
                throw new IllegalStateException("Cannot load injected class", e2);
            }
        }

        public static boolean isAvailable() {
            return ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5).isAtLeast(ClassFileVersion.JAVA_V6);
        }

        public enum Target {
            BOOTSTRAP(null) { // from class: net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation.Target.1
                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation.Target
                protected void inject(Instrumentation instrumentation, JarFile jarFile) {
                    UsingInstrumentation.DISPATCHER.appendToBootstrapClassLoaderSearch(instrumentation, jarFile);
                }
            },
            SYSTEM(ClassLoader.getSystemClassLoader()) { // from class: net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation.Target.2
                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingInstrumentation.Target
                protected void inject(Instrumentation instrumentation, JarFile jarFile) {
                    UsingInstrumentation.DISPATCHER.appendToSystemClassLoaderSearch(instrumentation, jarFile);
                }
            };


            @MaybeNull
            private final ClassLoader classLoader;

            protected abstract void inject(Instrumentation instrumentation, JarFile jarFile);

            Target(ClassLoader classLoader) {
                this.classLoader = classLoader;
            }

            @MaybeNull
            protected ClassLoader getClassLoader() {
                return this.classLoader;
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class UsingJna extends AbstractBase {
        private static final boolean ACCESS_CONTROLLER;
        private static final Object BOOTSTRAP_LOADER_LOCK;
        private static final Dispatcher DISPATCHER;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ClassLoader classLoader;

        @MaybeNull
        @HashCodeAndEqualsPlugin.ValueHandling(HashCodeAndEqualsPlugin.ValueHandling.Sort.REVERSE_NULLABILITY)
        private final ProtectionDomain protectionDomain;

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
                java.lang.ClassLoader r2 = r4.classLoader
                net.bytebuddy.dynamic.loading.ClassInjector$UsingJna r5 = (net.bytebuddy.dynamic.loading.ClassInjector.UsingJna) r5
                java.lang.ClassLoader r3 = r5.classLoader
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
                java.security.ProtectionDomain r2 = r4.protectionDomain
                java.security.ProtectionDomain r5 = r5.protectionDomain
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
            throw new UnsupportedOperationException("Method not decompiled: net.bytebuddy.dynamic.loading.ClassInjector.UsingJna.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            int iHashCode = getClass().hashCode() * 31;
            ClassLoader classLoader = this.classLoader;
            if (classLoader != null) {
                iHashCode += classLoader.hashCode();
            }
            int i = iHashCode * 31;
            ProtectionDomain protectionDomain = this.protectionDomain;
            return protectionDomain != null ? i + protectionDomain.hashCode() : i;
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
            DISPATCHER = (Dispatcher) doPrivileged(Dispatcher.CreationAction.INSTANCE);
            BOOTSTRAP_LOADER_LOCK = new Object();
        }

        public UsingJna(@MaybeNull ClassLoader classLoader) {
            this(classLoader, ClassLoadingStrategy.NO_PROTECTION_DOMAIN);
        }

        public UsingJna(@MaybeNull ClassLoader classLoader, @MaybeNull ProtectionDomain protectionDomain) {
            this.classLoader = classLoader;
            this.protectionDomain = protectionDomain;
        }

        @AccessControllerPlugin.Enhance
        private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
            return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
        }

        public static boolean isAvailable() {
            return DISPATCHER.isAvailable();
        }

        public static ClassInjector ofSystemLoader() {
            return new UsingJna(ClassLoader.getSystemClassLoader());
        }

        public static ClassInjector ofPlatformLoader() {
            return new UsingJna(ClassLoader.getSystemClassLoader().getParent());
        }

        public static ClassInjector ofBootLoader() {
            return new UsingJna(ClassLoadingStrategy.BOOTSTRAP_LOADER);
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public boolean isAlive() {
            return DISPATCHER.isAvailable();
        }

        @Override // net.bytebuddy.dynamic.loading.ClassInjector
        public Map<String, Class<?>> injectRaw(Map<? extends String, byte[]> map) {
            HashMap map2 = new HashMap();
            Object obj = this.classLoader;
            if (obj == null) {
                obj = BOOTSTRAP_LOADER_LOCK;
            }
            synchronized (obj) {
                for (Map.Entry<? extends String, byte[]> entry : map.entrySet()) {
                    try {
                        map2.put(entry.getKey(), Class.forName(entry.getKey(), false, this.classLoader));
                    } catch (ClassNotFoundException unused) {
                        map2.put(entry.getKey(), DISPATCHER.defineClass(this.classLoader, entry.getKey(), entry.getValue(), this.protectionDomain));
                    }
                }
            }
            return map2;
        }

        protected interface Dispatcher {

            public interface Jvm extends Library {
                Class<?> JVM_DefineClass(JNIEnv jNIEnv, String str, @MaybeNull ClassLoader classLoader, byte[] bArr, int i, @MaybeNull ProtectionDomain protectionDomain) throws LastErrorException;
            }

            Class<?> defineClass(@MaybeNull ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain);

            boolean isAvailable();

            public enum CreationAction implements PrivilegedAction<Dispatcher> {
                INSTANCE;

                @Override // java.security.PrivilegedAction
                public Dispatcher run() {
                    if (System.getProperty(SystemProperties.JAVA_VM_NAME, "").toUpperCase(Locale.US).contains("J9")) {
                        return new Unavailable("J9 does not support JNA-based class definition");
                    }
                    try {
                        HashMap map = new HashMap();
                        map.put("allow-objects", Boolean.TRUE);
                        if (Platform.isWindows() && !Platform.is64Bit()) {
                            map.put("function-mapper", Windows32BitFunctionMapper.INSTANCE);
                        }
                        return new Enabled((Jvm) Native.loadLibrary("jvm", Jvm.class, map));
                    } catch (Throwable th) {
                        return new Unavailable(th.getMessage());
                    }
                }
            }

            public enum Windows32BitFunctionMapper implements FunctionMapper {
                INSTANCE;

                public String getFunctionName(NativeLibrary nativeLibrary, Method method) {
                    if (method.getName().equals("JVM_DefineClass")) {
                        return "_JVM_DefineClass@24";
                    }
                    return method.getName();
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Enabled implements Dispatcher {
                private final Jvm jvm;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.jvm.equals(((Enabled) obj).jvm);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.jvm.hashCode();
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingJna.Dispatcher
                public boolean isAvailable() {
                    return true;
                }

                protected Enabled(Jvm jvm) {
                    this.jvm = jvm;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingJna.Dispatcher
                public Class<?> defineClass(@MaybeNull ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    return this.jvm.JVM_DefineClass(JNIEnv.CURRENT, str.replace('.', '/'), classLoader, bArr, bArr.length, protectionDomain);
                }
            }

            @HashCodeAndEqualsPlugin.Enhance
            public static class Unavailable implements Dispatcher {
                private final String error;

                public boolean equals(@MaybeNull Object obj) {
                    if (this == obj) {
                        return true;
                    }
                    return obj != null && getClass() == obj.getClass() && this.error.equals(((Unavailable) obj).error);
                }

                public int hashCode() {
                    return (getClass().hashCode() * 31) + this.error.hashCode();
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingJna.Dispatcher
                public boolean isAvailable() {
                    return false;
                }

                protected Unavailable(String str) {
                    this.error = str;
                }

                @Override // net.bytebuddy.dynamic.loading.ClassInjector.UsingJna.Dispatcher
                public Class<?> defineClass(@MaybeNull ClassLoader classLoader, String str, byte[] bArr, @MaybeNull ProtectionDomain protectionDomain) {
                    throw new UnsupportedOperationException("JNA is not available and JNA-based injection cannot be used: " + this.error);
                }
            }
        }
    }
}
