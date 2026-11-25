package com.esotericsoftware.reflectasm;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes8.dex */
class AccessClassLoader extends ClassLoader {
    private static final WeakHashMap<ClassLoader, WeakReference<AccessClassLoader>> accessClassLoaders = new WeakHashMap<>();
    private static volatile Method defineClassMethod;
    private static volatile AccessClassLoader selfContextAccessClassLoader;
    private static final ClassLoader selfContextParentClassLoader;
    private final HashSet<String> localClassNames;

    static {
        ClassLoader parentClassLoader = getParentClassLoader(AccessClassLoader.class);
        selfContextParentClassLoader = parentClassLoader;
        selfContextAccessClassLoader = new AccessClassLoader(parentClassLoader);
    }

    private AccessClassLoader(ClassLoader classLoader) {
        super(classLoader);
        this.localClassNames = new HashSet<>();
    }

    Class loadAccessClass(String str) {
        if (!this.localClassNames.contains(str)) {
            return null;
        }
        try {
            return loadClass(str, false);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Class defineAccessClass(String str, byte[] bArr) throws ClassFormatError {
        this.localClassNames.add(str);
        return defineClass(str, bArr);
    }

    @Override // java.lang.ClassLoader
    protected Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        if (str.equals(FieldAccess.class.getName())) {
            return FieldAccess.class;
        }
        if (str.equals(MethodAccess.class.getName())) {
            return MethodAccess.class;
        }
        if (str.equals(ConstructorAccess.class.getName())) {
            return ConstructorAccess.class;
        }
        return str.equals(PublicConstructorAccess.class.getName()) ? PublicConstructorAccess.class : super.loadClass(str, z);
    }

    Class<?> defineClass(String str, byte[] bArr) throws ClassFormatError {
        try {
            return (Class) getDefineClassMethod().invoke(getParent(), str, bArr, 0, Integer.valueOf(bArr.length), getClass().getProtectionDomain());
        } catch (Exception unused) {
            return defineClass(str, bArr, 0, bArr.length, getClass().getProtectionDomain());
        }
    }

    static boolean areInSameRuntimeClassLoader(Class cls, Class cls2) {
        if (cls.getPackage() != cls2.getPackage()) {
            return false;
        }
        ClassLoader classLoader = cls.getClassLoader();
        ClassLoader classLoader2 = cls2.getClassLoader();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        if (classLoader == null) {
            return classLoader2 == null || classLoader2 == systemClassLoader;
        }
        if (classLoader2 == null) {
            return classLoader == systemClassLoader;
        }
        return classLoader == classLoader2;
    }

    private static ClassLoader getParentClassLoader(Class cls) {
        ClassLoader classLoader = cls.getClassLoader();
        return classLoader == null ? ClassLoader.getSystemClassLoader() : classLoader;
    }

    private static Method getDefineClassMethod() throws Exception {
        if (defineClassMethod == null) {
            synchronized (accessClassLoaders) {
                if (defineClassMethod == null) {
                    defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                    try {
                        defineClassMethod.setAccessible(true);
                    } catch (Exception unused) {
                    }
                }
            }
        }
        return defineClassMethod;
    }

    static AccessClassLoader get(Class cls) {
        ClassLoader parentClassLoader = getParentClassLoader(cls);
        ClassLoader classLoader = selfContextParentClassLoader;
        if (classLoader.equals(parentClassLoader)) {
            if (selfContextAccessClassLoader == null) {
                synchronized (accessClassLoaders) {
                    if (selfContextAccessClassLoader == null) {
                        selfContextAccessClassLoader = new AccessClassLoader(classLoader);
                    }
                }
            }
            return selfContextAccessClassLoader;
        }
        WeakHashMap<ClassLoader, WeakReference<AccessClassLoader>> weakHashMap = accessClassLoaders;
        synchronized (weakHashMap) {
            WeakReference<AccessClassLoader> weakReference = weakHashMap.get(parentClassLoader);
            if (weakReference != null) {
                AccessClassLoader accessClassLoader = weakReference.get();
                if (accessClassLoader != null) {
                    return accessClassLoader;
                }
                weakHashMap.remove(parentClassLoader);
            }
            AccessClassLoader accessClassLoader2 = new AccessClassLoader(parentClassLoader);
            weakHashMap.put(parentClassLoader, new WeakReference<>(accessClassLoader2));
            return accessClassLoader2;
        }
    }

    public static void remove(ClassLoader classLoader) {
        if (selfContextParentClassLoader.equals(classLoader)) {
            selfContextAccessClassLoader = null;
            return;
        }
        WeakHashMap<ClassLoader, WeakReference<AccessClassLoader>> weakHashMap = accessClassLoaders;
        synchronized (weakHashMap) {
            weakHashMap.remove(classLoader);
        }
    }

    public static int activeAccessClassLoaders() {
        int size = accessClassLoaders.size();
        return selfContextAccessClassLoader != null ? size + 1 : size;
    }
}
