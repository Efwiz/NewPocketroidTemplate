package net.bytebuddy.dynamic.loading;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.nullability.MaybeNull;
import net.bytebuddy.utility.nullability.UnknownNull;

/* JADX INFO: loaded from: classes12.dex */
public class MultipleParentClassLoader extends InjectionClassLoader {
    private final List<? extends ClassLoader> parents;

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

    public MultipleParentClassLoader(List<? extends ClassLoader> list) {
        this(ClassLoadingStrategy.BOOTSTRAP_LOADER, list);
    }

    public MultipleParentClassLoader(@MaybeNull ClassLoader classLoader, List<? extends ClassLoader> list) {
        this(classLoader, list, true);
    }

    public MultipleParentClassLoader(@MaybeNull ClassLoader classLoader, List<? extends ClassLoader> list, boolean z) {
        super(classLoader, z);
        this.parents = list;
    }

    @Override // java.lang.ClassLoader
    protected Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        Iterator<? extends ClassLoader> it = this.parents.iterator();
        while (it.hasNext()) {
            try {
                Class<?> clsLoadClass = it.next().loadClass(str);
                if (z) {
                    resolveClass(clsLoadClass);
                }
                return clsLoadClass;
            } catch (ClassNotFoundException unused) {
            }
        }
        return super.loadClass(str, z);
    }

    @Override // java.lang.ClassLoader
    public URL getResource(String str) {
        Iterator<? extends ClassLoader> it = this.parents.iterator();
        while (it.hasNext()) {
            URL resource = it.next().getResource(str);
            if (resource != null) {
                return resource;
            }
        }
        return super.getResource(str);
    }

    @Override // java.lang.ClassLoader
    public Enumeration<URL> getResources(String str) throws IOException {
        ArrayList arrayList = new ArrayList(this.parents.size() + 1);
        Iterator<? extends ClassLoader> it = this.parents.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getResources(str));
        }
        arrayList.add(super.getResources(str));
        return new CompoundEnumeration(arrayList);
    }

    @Override // net.bytebuddy.dynamic.loading.InjectionClassLoader
    protected Map<String, Class<?>> doDefineClasses(Map<String, byte[]> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, byte[]> entry : map.entrySet()) {
            map2.put(entry.getKey(), defineClass(entry.getKey(), entry.getValue(), 0, entry.getValue().length));
        }
        return map2;
    }

    protected static class CompoundEnumeration implements Enumeration<URL> {
        private static final int FIRST = 0;

        @UnknownNull
        private Enumeration<URL> current;
        private final List<Enumeration<URL>> enumerations;

        protected CompoundEnumeration(List<Enumeration<URL>> list) {
            this.enumerations = list;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            Enumeration<URL> enumeration = this.current;
            if (enumeration != null && enumeration.hasMoreElements()) {
                return true;
            }
            if (this.enumerations.isEmpty()) {
                return false;
            }
            this.current = this.enumerations.remove(0);
            return hasMoreElements();
        }

        @Override // java.util.Enumeration
        public URL nextElement() {
            if (hasMoreElements()) {
                return this.current.nextElement();
            }
            throw new NoSuchElementException();
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    public static class Builder {
        private final List<? extends ClassLoader> classLoaders;
        private final boolean sealed;

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Builder builder = (Builder) obj;
            return this.sealed == builder.sealed && this.classLoaders.equals(builder.classLoaders);
        }

        public int hashCode() {
            return (((getClass().hashCode() * 31) + (this.sealed ? 1 : 0)) * 31) + this.classLoaders.hashCode();
        }

        public Builder() {
            this(true);
        }

        public Builder(boolean z) {
            this(Collections.emptyList(), z);
        }

        private Builder(List<? extends ClassLoader> list, boolean z) {
            this.classLoaders = list;
            this.sealed = z;
        }

        public Builder append(Class<?>... clsArr) {
            return append((Collection<? extends Class<?>>) Arrays.asList(clsArr));
        }

        public Builder append(Collection<? extends Class<?>> collection) {
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<? extends Class<?>> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getClassLoader());
            }
            return append((List<? extends ClassLoader>) arrayList);
        }

        public Builder append(ClassLoader... classLoaderArr) {
            return append(Arrays.asList(classLoaderArr));
        }

        public Builder append(List<? extends ClassLoader> list) {
            ArrayList arrayList = new ArrayList(this.classLoaders.size() + list.size());
            arrayList.addAll(this.classLoaders);
            HashSet hashSet = new HashSet(this.classLoaders);
            for (ClassLoader classLoader : list) {
                if (classLoader != null && hashSet.add(classLoader)) {
                    arrayList.add(classLoader);
                }
            }
            return new Builder(arrayList, this.sealed);
        }

        public Builder appendMostSpecific(Class<?>... clsArr) {
            return appendMostSpecific((Collection<? extends Class<?>>) Arrays.asList(clsArr));
        }

        public Builder appendMostSpecific(Collection<? extends Class<?>> collection) {
            ArrayList arrayList = new ArrayList(collection.size());
            Iterator<? extends Class<?>> it = collection.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getClassLoader());
            }
            return appendMostSpecific((List<? extends ClassLoader>) arrayList);
        }

        public Builder appendMostSpecific(ClassLoader... classLoaderArr) {
            return appendMostSpecific(Arrays.asList(classLoaderArr));
        }

        public Builder appendMostSpecific(List<? extends ClassLoader> list) {
            ArrayList arrayList = new ArrayList(this.classLoaders.size() + list.size());
            arrayList.addAll(this.classLoaders);
            for (ClassLoader classLoader : list) {
                if (classLoader != null) {
                    ClassLoader parent = classLoader;
                    do {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            if (((ClassLoader) it.next()).equals(parent)) {
                                it.remove();
                            }
                        }
                        parent = parent.getParent();
                    } while (parent != null);
                    Iterator it2 = arrayList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            arrayList.add(classLoader);
                            break;
                        }
                        ClassLoader parent2 = (ClassLoader) it2.next();
                        do {
                            if (parent2.equals(classLoader)) {
                                break;
                            }
                            parent2 = parent2.getParent();
                        } while (parent2 != null);
                    }
                }
            }
            return new Builder(arrayList, this.sealed);
        }

        public Builder filter(ElementMatcher<? super ClassLoader> elementMatcher) {
            ArrayList arrayList = new ArrayList(this.classLoaders.size());
            for (ClassLoader classLoader : this.classLoaders) {
                if (elementMatcher.matches(classLoader)) {
                    arrayList.add(classLoader);
                }
            }
            return new Builder(arrayList, this.sealed);
        }

        public ClassLoader build() {
            if (this.classLoaders.size() == 1) {
                return this.classLoaders.get(0);
            }
            return doBuild(ClassLoadingStrategy.BOOTSTRAP_LOADER);
        }

        public ClassLoader build(ClassLoader classLoader) {
            if (this.classLoaders.isEmpty()) {
                return classLoader;
            }
            return (this.classLoaders.size() == 1 && this.classLoaders.contains(classLoader)) ? classLoader : filter(ElementMatchers.not(ElementMatchers.is(classLoader))).doBuild(classLoader);
        }

        private ClassLoader doBuild(@MaybeNull ClassLoader classLoader) {
            return new MultipleParentClassLoader(classLoader, this.classLoaders, this.sealed);
        }
    }
}
