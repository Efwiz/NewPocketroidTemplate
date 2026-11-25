package org.jetbrains.kotlin.buildtools.api;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SharedApiClassesClassLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u000bH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/SharedApiClassesClassLoaderImpl;", "Ljava/lang/ClassLoader;", "parent", "fallback", "allowedPackage", "", "(Ljava/lang/ClassLoader;Ljava/lang/ClassLoader;Ljava/lang/String;)V", "loadClass", "Ljava/lang/Class;", "name", "resolve", "", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
final class SharedApiClassesClassLoaderImpl extends ClassLoader {
    private final String allowedPackage;
    private final ClassLoader parent;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SharedApiClassesClassLoaderImpl(ClassLoader parent, ClassLoader fallback, String allowedPackage) {
        super(fallback);
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(fallback, "fallback");
        Intrinsics.checkNotNullParameter(allowedPackage, "allowedPackage");
        this.parent = parent;
        this.allowedPackage = allowedPackage;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> loadClass(String name2, boolean resolve) throws ClassNotFoundException {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (StringsKt.startsWith$default(name2, this.allowedPackage, false, 2, (Object) null)) {
            Class<?> clsLoadClass = this.parent.loadClass(name2);
            Intrinsics.checkNotNullExpressionValue(clsLoadClass, "{\n            parent.loadClass(name)\n        }");
            return clsLoadClass;
        }
        Class<?> clsLoadClass2 = super.loadClass(name2, resolve);
        Intrinsics.checkNotNullExpressionValue(clsLoadClass2, "{\n            super.load…(name, resolve)\n        }");
        return clsLoadClass2;
    }
}
