package net.bytebuddy.dynamic;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public class Nexus extends WeakReference<ClassLoader> {

    @AlwaysNull
    private static final ReferenceQueue<ClassLoader> NO_QUEUE = null;
    public static final String PROPERTY = "net.bytebuddy.nexus.disabled";
    private static final ConcurrentMap<Nexus, Object> TYPE_INITIALIZERS = new ConcurrentHashMap();
    private final int classLoaderHashCode;
    private final int identification;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final String f358name;

    private Nexus(Class<?> cls, int i) {
        this(nonAnonymous(cls.getName()), cls.getClassLoader(), NO_QUEUE, i);
    }

    private Nexus(String str, @MaybeNull ClassLoader classLoader, @MaybeNull ReferenceQueue<? super ClassLoader> referenceQueue, int i) {
        super(classLoader, classLoader == null ? null : referenceQueue);
        this.f358name = str;
        this.classLoaderHashCode = System.identityHashCode(classLoader);
        this.identification = i;
    }

    private static String nonAnonymous(String str) {
        int iIndexOf = str.indexOf(47);
        return iIndexOf == -1 ? str : str.substring(0, iIndexOf);
    }

    public static void initialize(Class<?> cls, int i) throws Exception {
        Object objRemove = TYPE_INITIALIZERS.remove(new Nexus(cls, i));
        if (objRemove != null) {
            Class.forName("net.bytebuddy.implementation.LoadedTypeInitializer", true, objRemove.getClass().getClassLoader()).getMethod("onLoad", Class.class).invoke(objRemove, cls);
        }
    }

    public static void register(String str, @MaybeNull ClassLoader classLoader, @MaybeNull ReferenceQueue<? super ClassLoader> referenceQueue, int i, Object obj) {
        TYPE_INITIALIZERS.put(new Nexus(str, classLoader, referenceQueue, i), obj);
    }

    public static void clean(Reference<? super ClassLoader> reference) {
        TYPE_INITIALIZERS.remove(reference);
    }

    public int hashCode() {
        return (((this.f358name.hashCode() * 31) + this.classLoaderHashCode) * 31) + this.identification;
    }

    public boolean equals(@MaybeNull Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Nexus nexus = (Nexus) obj;
        return this.classLoaderHashCode == nexus.classLoaderHashCode && this.identification == nexus.identification && this.f358name.equals(nexus.f358name) && get() == nexus.get();
    }

    public String toString() {
        return "Nexus{name='" + this.f358name + "', classLoaderHashCode=" + this.classLoaderHashCode + ", identification=" + this.identification + ", classLoader=" + get() + '}';
    }
}
