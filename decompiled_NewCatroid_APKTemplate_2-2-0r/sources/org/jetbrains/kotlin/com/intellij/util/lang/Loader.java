package org.jetbrains.kotlin.com.intellij.util.lang;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes3.dex */
public abstract class Loader {
    private Predicate<String> nameFilter;
    final Path path;

    public enum Attribute {
        SPEC_TITLE,
        SPEC_VERSION,
        SPEC_VENDOR,
        CLASS_PATH,
        IMPL_TITLE,
        IMPL_VERSION,
        IMPL_VENDOR
    }

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "name";
        } else if (i != 2) {
            objArr[0] = "path";
        } else {
            objArr[0] = "filter";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/util/lang/Loader";
        if (i == 1) {
            objArr[2] = "containsName";
        } else if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "setNameFilter";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    abstract ClasspathCache.IndexRegistrar buildData() throws IOException;

    abstract Class<?> findClass(String str, String str2, ClassPath.ClassDataConsumer classDataConsumer) throws IOException;

    public abstract Map<Attribute, String> getAttributes() throws IOException;

    abstract Resource getResource(String str);

    Loader(Path path) {
        if (path == null) {
            $$$reportNull$$$0(0);
        }
        this.path = path;
    }

    final boolean containsName(String str) {
        Predicate<String> predicate;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        return str.isEmpty() || (predicate = this.nameFilter) == null || predicate.test(str);
    }

    final void setNameFilter(Predicate<String> predicate) {
        if (predicate == null) {
            $$$reportNull$$$0(2);
        }
        this.nameFilter = predicate;
    }
}
