package net.bytebuddy.utility;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.AnnotatedElement;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Set;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.description.annotation.AnnotationList;
import net.bytebuddy.description.annotation.AnnotationSource;
import net.bytebuddy.description.type.PackageDescription;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes12.dex */
public class JavaModule implements NamedElement.WithOptionalName, AnnotationSource {
    private static final boolean ACCESS_CONTROLLER;
    protected static final Module MODULE;
    protected static final Resolver RESOLVER;

    @AlwaysNull
    public static final JavaModule UNSUPPORTED;
    private final AnnotatedElement module;

    @JavaDispatcher.Proxied("java.lang.Module")
    protected interface Module {
        @JavaDispatcher.Proxied("canRead")
        boolean canRead(Object obj, @JavaDispatcher.Proxied("java.lang.Module") Object obj2);

        @MaybeNull
        @JavaDispatcher.Proxied("getClassLoader")
        ClassLoader getClassLoader(Object obj);

        @JavaDispatcher.Proxied("getName")
        String getName(Object obj);

        @JavaDispatcher.Proxied("getPackages")
        Set<String> getPackages(Object obj);

        @MaybeNull
        @JavaDispatcher.Proxied("getResourceAsStream")
        InputStream getResourceAsStream(Object obj, String str) throws IOException;

        @JavaDispatcher.Proxied("isExported")
        boolean isExported(Object obj, String str, @JavaDispatcher.Proxied("java.lang.Module") Object obj2);

        @JavaDispatcher.Instance
        @JavaDispatcher.Proxied("isInstance")
        boolean isInstance(Object obj);

        @JavaDispatcher.Proxied("isNamed")
        boolean isNamed(Object obj);

        @JavaDispatcher.Proxied("isOpen")
        boolean isOpen(Object obj, String str, @JavaDispatcher.Proxied("java.lang.Module") Object obj2);
    }

    @JavaDispatcher.Proxied(CommonClassNames.JAVA_LANG_CLASS)
    protected interface Resolver {
        @JavaDispatcher.Defaults
        @MaybeNull
        @JavaDispatcher.Proxied("getModule")
        Object getModule(Class<?> cls);
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
        UNSUPPORTED = null;
        RESOLVER = (Resolver) doPrivileged(JavaDispatcher.of(Resolver.class));
        MODULE = (Module) doPrivileged(JavaDispatcher.of(Module.class));
    }

    protected JavaModule(AnnotatedElement annotatedElement) {
        this.module = annotatedElement;
    }

    @AccessControllerPlugin.Enhance
    private static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
        return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
    }

    @MaybeNull
    public static JavaModule ofType(Class<?> cls) {
        Object module = RESOLVER.getModule(cls);
        return module == null ? UNSUPPORTED : new JavaModule((AnnotatedElement) module);
    }

    public static JavaModule of(Object obj) {
        if (!MODULE.isInstance(obj)) {
            throw new IllegalArgumentException("Not a Java module: " + obj);
        }
        return new JavaModule((AnnotatedElement) obj);
    }

    public static boolean isSupported() {
        return ClassFileVersion.ofThisVm(ClassFileVersion.JAVA_V5).isAtLeast(ClassFileVersion.JAVA_V9);
    }

    @Override // net.bytebuddy.description.NamedElement.WithOptionalName
    public boolean isNamed() {
        return MODULE.isNamed(this.module);
    }

    @Override // net.bytebuddy.description.NamedElement
    public String getActualName() {
        return MODULE.getName(this.module);
    }

    public Set<String> getPackages() {
        return MODULE.getPackages(this.module);
    }

    @MaybeNull
    public InputStream getResourceAsStream(String str) throws IOException {
        return MODULE.getResourceAsStream(this.module, str);
    }

    @MaybeNull
    public ClassLoader getClassLoader() {
        return MODULE.getClassLoader(this.module);
    }

    public Object unwrap() {
        return this.module;
    }

    public boolean canRead(JavaModule javaModule) {
        return MODULE.canRead(this.module, javaModule.unwrap());
    }

    public boolean isExported(@MaybeNull PackageDescription packageDescription, JavaModule javaModule) {
        return packageDescription == null || packageDescription.isDefault() || MODULE.isExported(this.module, packageDescription.getName(), javaModule.unwrap());
    }

    public boolean isOpened(@MaybeNull PackageDescription packageDescription, JavaModule javaModule) {
        return packageDescription == null || packageDescription.isDefault() || MODULE.isOpen(this.module, packageDescription.getName(), javaModule.unwrap());
    }

    @Override // net.bytebuddy.description.annotation.AnnotationSource
    public AnnotationList getDeclaredAnnotations() {
        return new AnnotationList.ForLoadedAnnotations(this.module.getDeclaredAnnotations());
    }

    public int hashCode() {
        return this.module.hashCode();
    }

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof JavaModule) {
            return this.module.equals(((JavaModule) obj).module);
        }
        return false;
    }

    public String toString() {
        return this.module.toString();
    }
}
