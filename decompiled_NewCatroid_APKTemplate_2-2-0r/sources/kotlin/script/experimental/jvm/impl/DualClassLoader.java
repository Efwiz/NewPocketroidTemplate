package kotlin.script.experimental.jvm.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KJvmCompiledModule.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000 \u00132\u00020\u0001:\u0002\u0013\u0014B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0012\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00102\u0006\u0010\u000b\u001a\u00020\fH\u0014J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0015"}, d2 = {"Lkotlin/script/experimental/jvm/impl/DualClassLoader;", "Ljava/lang/ClassLoader;", "fallbackLoader", "parentLoader", "(Ljava/lang/ClassLoader;Ljava/lang/ClassLoader;)V", "fallbackClassLoader", "Lkotlin/script/experimental/jvm/impl/DualClassLoader$Wrapper;", "getFallbackClassLoader$kotlin_scripting_jvm", "()Lkotlin/script/experimental/jvm/impl/DualClassLoader$Wrapper;", "findClass", "Ljava/lang/Class;", "name", "", "findResource", "Ljava/net/URL;", "findResources", "Ljava/util/Enumeration;", "getResourceAsStream", "Ljava/io/InputStream;", "Companion", "Wrapper", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DualClassLoader extends ClassLoader {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Wrapper fallbackClassLoader;

    /* JADX WARN: Illegal instructions before constructor call */
    public DualClassLoader(ClassLoader fallbackLoader, ClassLoader classLoader) {
        Intrinsics.checkNotNullParameter(fallbackLoader, "fallbackLoader");
        Companion companion = INSTANCE;
        ClassLoader classLoaderSingleClassLoader = companion.singleClassLoader(fallbackLoader, classLoader);
        super(classLoaderSingleClassLoader == null ? classLoader : classLoaderSingleClassLoader);
        this.fallbackClassLoader = (classLoader == null || companion.singleClassLoader(fallbackLoader, classLoader) != null) ? null : new Wrapper(fallbackLoader);
    }

    /* JADX INFO: compiled from: KJvmCompiledModule.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\u0006\u0010\u0006\u001a\u00020\u0007¨\u0006\n"}, d2 = {"Lkotlin/script/experimental/jvm/impl/DualClassLoader$Wrapper;", "Ljava/lang/ClassLoader;", "parent", "(Ljava/lang/ClassLoader;)V", "openFindResource", "Ljava/net/URL;", "name", "", "openFindResources", "Ljava/util/Enumeration;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Wrapper extends ClassLoader {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Wrapper(ClassLoader parent) {
            super(parent);
            Intrinsics.checkNotNullParameter(parent, "parent");
        }

        public final Enumeration<URL> openFindResources(String name2) throws IOException {
            Intrinsics.checkNotNullParameter(name2, "name");
            Enumeration<URL> enumerationFindResources = super.findResources(name2);
            Intrinsics.checkNotNullExpressionValue(enumerationFindResources, "findResources(...)");
            return enumerationFindResources;
        }

        public final URL openFindResource(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return super.findResource(name2);
        }
    }

    /* JADX INFO: compiled from: KJvmCompiledModule.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0004H\u0002¨\u0006\u0007"}, d2 = {"Lkotlin/script/experimental/jvm/impl/DualClassLoader$Companion;", "", "()V", "singleClassLoader", "Ljava/lang/ClassLoader;", "fallbackLoader", "parentLoader", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        private static final boolean singleClassLoader$isAncestorOf(ClassLoader classLoader, ClassLoader classLoader2) {
            while (classLoader2 != null) {
                if (classLoader == classLoader2) {
                    return true;
                }
                classLoader2 = classLoader2.getParent();
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final ClassLoader singleClassLoader(ClassLoader fallbackLoader, ClassLoader parentLoader) {
            if (parentLoader == null || singleClassLoader$isAncestorOf(parentLoader, fallbackLoader)) {
                return fallbackLoader;
            }
            if (singleClassLoader$isAncestorOf(fallbackLoader, parentLoader)) {
                return parentLoader;
            }
            return null;
        }
    }

    /* JADX INFO: renamed from: getFallbackClassLoader$kotlin_scripting_jvm, reason: from getter */
    public final Wrapper getFallbackClassLoader() {
        return this.fallbackClassLoader;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> findClass(String name2) throws ClassNotFoundException {
        Intrinsics.checkNotNullParameter(name2, "name");
        try {
            Class<?> clsFindClass = super.findClass(name2);
            Intrinsics.checkNotNull(clsFindClass);
            return clsFindClass;
        } catch (ClassNotFoundException e) {
            Wrapper wrapper = this.fallbackClassLoader;
            Class<?> clsLoadClass = wrapper != null ? wrapper.loadClass(name2) : null;
            if (clsLoadClass != null) {
                return clsLoadClass;
            }
            throw e;
        }
    }

    @Override // java.lang.ClassLoader
    public InputStream getResourceAsStream(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        InputStream resourceAsStream = super.getResourceAsStream(name2);
        if (resourceAsStream != null) {
            return resourceAsStream;
        }
        Wrapper wrapper = this.fallbackClassLoader;
        if (wrapper != null) {
            return wrapper.getResourceAsStream(name2);
        }
        return null;
    }

    @Override // java.lang.ClassLoader
    protected Enumeration<URL> findResources(String name2) throws IOException {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (this.fallbackClassLoader == null) {
            Enumeration<URL> enumerationFindResources = super.findResources(name2);
            Intrinsics.checkNotNullExpressionValue(enumerationFindResources, "findResources(...)");
            return enumerationFindResources;
        }
        Enumeration<URL> enumerationFindResources2 = super.findResources(name2);
        Intrinsics.checkNotNullExpressionValue(enumerationFindResources2, "findResources(...)");
        ArrayList list = Collections.list(enumerationFindResources2);
        Intrinsics.checkNotNullExpressionValue(list, "list(...)");
        Enumeration<URL> enumeration = Collections.enumeration(CollectionsKt.plus((Collection) list, SequencesKt.asSequence(CollectionsKt.iterator(this.fallbackClassLoader.openFindResources(name2)))));
        Intrinsics.checkNotNullExpressionValue(enumeration, "enumeration(...)");
        return enumeration;
    }

    @Override // java.lang.ClassLoader
    protected URL findResource(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        URL urlFindResource = super.findResource(name2);
        if (urlFindResource != null) {
            return urlFindResource;
        }
        Wrapper wrapper = this.fallbackClassLoader;
        if (wrapper != null) {
            return wrapper.openFindResource(name2);
        }
        return null;
    }
}
