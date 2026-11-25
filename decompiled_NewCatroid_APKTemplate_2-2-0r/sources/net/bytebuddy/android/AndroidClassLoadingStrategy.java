package net.bytebuddy.android;

import com.android.dx.cf.direct.DirectClassFile;
import com.android.dx.cf.direct.StdAttributeFactory;
import com.android.dx.dex.DexOptions;
import com.android.dx.dex.cf.CfOptions;
import com.android.dx.dex.cf.CfTranslator;
import com.android.dx.dex.file.ClassDefItem;
import com.android.dx.dex.file.DexFile;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.logging.Logger;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.utility.RandomString;
import net.bytebuddy.utility.nullability.AlwaysNull;
import net.bytebuddy.utility.nullability.MaybeNull;

/* JADX INFO: loaded from: classes12.dex */
public abstract class AndroidClassLoadingStrategy implements ClassLoadingStrategy<ClassLoader> {
    private static final String DEX_CLASS_FILE = "classes.dex";

    @AlwaysNull
    private static final String EMPTY_LIBRARY_PATH = null;
    private static final FileProcessor FILE_PROCESSOR;
    private static final String JAR_FILE_EXTENSION = ".jar";
    private final DexProcessor dexProcessor;
    protected final File privateDirectory;
    protected final RandomString randomString;

    protected abstract Map<TypeDescription, Class<?>> doLoad(@MaybeNull ClassLoader classLoader, Set<TypeDescription> set, File file) throws IOException;

    static {
        FileProcessor forReadOnlyClassFile;
        try {
            forReadOnlyClassFile = new FileProcessor.ForReadOnlyClassFile(Class.forName("java.nio.file.Files").getMethod("setPosixFilePermissions", Class.forName("java.nio.file.Path"), Set.class), File.class.getMethod("toPath", new Class[0]), Collections.singleton(Class.forName("java.nio.file.attribute.PosixFilePermission").getMethod("valueOf", String.class).invoke(null, "OWNER_READ")));
        } catch (Throwable unused) {
            forReadOnlyClassFile = FileProcessor.Disabled.INSTANCE;
        }
        FILE_PROCESSOR = forReadOnlyClassFile;
    }

    protected AndroidClassLoadingStrategy(File file, DexProcessor dexProcessor) {
        if (!file.isDirectory()) {
            throw new IllegalArgumentException("Not a directory " + file);
        }
        this.privateDirectory = file;
        this.dexProcessor = dexProcessor;
        this.randomString = new RandomString();
    }

    @Override // net.bytebuddy.dynamic.loading.ClassLoadingStrategy
    public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
        DexProcessor.Conversion conversionCreate = this.dexProcessor.create();
        for (Map.Entry<TypeDescription, byte[]> entry : map.entrySet()) {
            conversionCreate.register(entry.getKey().getName(), entry.getValue());
        }
        File file = new File(this.privateDirectory, this.randomString.nextString() + JAR_FILE_EXTENSION);
        try {
            try {
                if (!file.createNewFile()) {
                    throw new IllegalStateException("Cannot create " + file);
                }
                JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(file));
                try {
                    jarOutputStream.putNextEntry(new JarEntry("classes.dex"));
                    conversionCreate.drainTo(jarOutputStream);
                    jarOutputStream.closeEntry();
                    jarOutputStream.close();
                    FILE_PROCESSOR.accept(file);
                    Map<TypeDescription, Class<?>> mapDoLoad = doLoad(classLoader, map.keySet(), file);
                    if (!file.delete()) {
                        Logger.getLogger("net.bytebuddy").warning("Could not delete " + file);
                    }
                    return mapDoLoad;
                } catch (Throwable th) {
                    jarOutputStream.close();
                    throw th;
                }
            } catch (IOException e) {
                throw new IllegalStateException("Cannot write to zip file " + file, e);
            }
        } catch (Throwable th2) {
            if (!file.delete()) {
                Logger.getLogger("net.bytebuddy").warning("Could not delete " + file);
            }
            throw th2;
        }
    }

    protected interface FileProcessor {

        public enum Disabled implements FileProcessor {
            INSTANCE;

            @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.FileProcessor
            public void accept(File file) {
            }
        }

        void accept(File file);

        public static class ForReadOnlyClassFile implements FileProcessor {
            private final Set<?> permissions;
            private final Method setPosixFilePermissions;
            private final Method toPath;

            protected ForReadOnlyClassFile(Method method, Method method2, Set<?> set) {
                this.setPosixFilePermissions = method;
                this.toPath = method2;
                this.permissions = set;
            }

            @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.FileProcessor
            public void accept(File file) {
                try {
                    this.setPosixFilePermissions.invoke(null, this.toPath.invoke(file, new Object[0]), this.permissions);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Cannot access file system permissions", e);
                } catch (InvocationTargetException e2) {
                    if (!(e2.getTargetException() instanceof UnsupportedOperationException)) {
                        throw new IllegalStateException("Cannot invoke file system permissions method", e2.getTargetException());
                    }
                }
            }
        }
    }

    public interface DexProcessor {

        public interface Conversion {
            void drainTo(OutputStream outputStream) throws IOException;

            void register(String str, byte[] bArr);
        }

        Conversion create();

        public static class ForSdkCompiler implements DexProcessor {
            private static final String CLASS_FILE_EXTENSION = ".class";
            private static final int DEX_COMPATIBLE_API_VERSION = 13;
            private static final Dispatcher DISPATCHER;
            private static final boolean NOT_VERBOSE = false;

            @AlwaysNull
            private static final Writer NO_PRINT_OUTPUT;
            private final CfOptions dexCompilerOptions;
            private final DexOptions dexFileOptions;

            static {
                Dispatcher unavailable;
                try {
                    try {
                        try {
                            Class<?> cls = Class.forName("com.android.dx.command.dexer.DxContext");
                            unavailable = new Dispatcher.ForApi26LevelCompatibleVm(CfTranslator.class.getMethod("translate", cls, DirectClassFile.class, byte[].class, CfOptions.class, DexOptions.class, DexFile.class), cls.getConstructor(new Class[0]));
                        } catch (Throwable unused) {
                            unavailable = new Dispatcher.ForLegacyVm(CfTranslator.class.getMethod("translate", DirectClassFile.class, byte[].class, CfOptions.class, DexOptions.class, DexFile.class), DexOptions.class.getField("targetApiLevel"));
                        }
                    } catch (Throwable th) {
                        unavailable = new Dispatcher.Unavailable(th.getMessage());
                    }
                } catch (Throwable unused2) {
                    unavailable = new Dispatcher.ForLegacyVm(CfTranslator.class.getMethod("translate", DirectClassFile.class, byte[].class, CfOptions.class, DexOptions.class, DexFile.class), DexOptions.class.getField("minSdkVersion"));
                }
                DISPATCHER = unavailable;
                NO_PRINT_OUTPUT = null;
            }

            protected static DexProcessor makeDefault() {
                DexOptions dexOptions = new DexOptions();
                DISPATCHER.setTargetApi(dexOptions, 13);
                return new ForSdkCompiler(dexOptions, new CfOptions());
            }

            public ForSdkCompiler(DexOptions dexOptions, CfOptions cfOptions) {
                this.dexFileOptions = dexOptions;
                this.dexCompilerOptions = cfOptions;
            }

            @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor
            public Conversion create() {
                return new Conversion(new DexFile(this.dexFileOptions));
            }

            protected class Conversion implements Conversion {
                private static final boolean NON_STRICT = false;
                private final DexFile dexFile;

                protected Conversion(DexFile dexFile) {
                    this.dexFile = dexFile;
                }

                @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.Conversion
                public void register(String str, byte[] bArr) {
                    DirectClassFile directClassFile = new DirectClassFile(bArr, str.replace('.', '/') + ".class", false);
                    directClassFile.setAttributeFactory(new StdAttributeFactory());
                    this.dexFile.add(ForSdkCompiler.DISPATCHER.translate(directClassFile, bArr, ForSdkCompiler.this.dexCompilerOptions, ForSdkCompiler.this.dexFileOptions, new DexFile(ForSdkCompiler.this.dexFileOptions)));
                }

                @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.Conversion
                public void drainTo(OutputStream outputStream) throws IOException {
                    this.dexFile.writeTo(outputStream, ForSdkCompiler.NO_PRINT_OUTPUT, false);
                }
            }

            protected interface Dispatcher {
                void setTargetApi(DexOptions dexOptions, int i);

                ClassDefItem translate(DirectClassFile directClassFile, byte[] bArr, CfOptions cfOptions, DexOptions dexOptions, DexFile dexFile);

                public static class Unavailable implements Dispatcher {
                    private final String message;

                    protected Unavailable(String str) {
                        this.message = str;
                    }

                    @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.ForSdkCompiler.Dispatcher
                    public ClassDefItem translate(DirectClassFile directClassFile, byte[] bArr, CfOptions cfOptions, DexOptions dexOptions, DexFile dexFile) {
                        throw new IllegalStateException("Could not resolve dispatcher: " + this.message);
                    }

                    @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.ForSdkCompiler.Dispatcher
                    public void setTargetApi(DexOptions dexOptions, int i) {
                        throw new IllegalStateException("Could not resolve dispatcher: " + this.message);
                    }
                }

                public static class ForLegacyVm implements Dispatcher {
                    private final Field targetApi;
                    private final Method translate;

                    protected ForLegacyVm(Method method, Field field) {
                        this.translate = method;
                        this.targetApi = field;
                    }

                    @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.ForSdkCompiler.Dispatcher
                    public ClassDefItem translate(DirectClassFile directClassFile, byte[] bArr, CfOptions cfOptions, DexOptions dexOptions, DexFile dexFile) {
                        try {
                            return (ClassDefItem) this.translate.invoke(null, directClassFile, bArr, cfOptions, dexOptions, dexFile);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException("Cannot access an Android dex file translation method", e);
                        } catch (InvocationTargetException e2) {
                            throw new IllegalStateException("Cannot invoke Android dex file translation method", e2.getTargetException());
                        }
                    }

                    @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.ForSdkCompiler.Dispatcher
                    public void setTargetApi(DexOptions dexOptions, int i) {
                        try {
                            this.targetApi.set(dexOptions, Integer.valueOf(i));
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException("Cannot access an Android dex file translation method", e);
                        }
                    }
                }

                public static class ForApi26LevelCompatibleVm implements Dispatcher {
                    private final Constructor<?> dxContext;
                    private final Method translate;

                    @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.ForSdkCompiler.Dispatcher
                    public void setTargetApi(DexOptions dexOptions, int i) {
                    }

                    protected ForApi26LevelCompatibleVm(Method method, Constructor<?> constructor) {
                        this.translate = method;
                        this.dxContext = constructor;
                    }

                    @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.DexProcessor.ForSdkCompiler.Dispatcher
                    public ClassDefItem translate(DirectClassFile directClassFile, byte[] bArr, CfOptions cfOptions, DexOptions dexOptions, DexFile dexFile) {
                        try {
                            return (ClassDefItem) this.translate.invoke(null, this.dxContext.newInstance(new Object[0]), directClassFile, bArr, cfOptions, dexOptions, dexFile);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException("Cannot access an Android dex file translation method", e);
                        } catch (InstantiationException e2) {
                            throw new IllegalStateException("Cannot instantiate dex context", e2);
                        } catch (InvocationTargetException e3) {
                            throw new IllegalStateException("Cannot invoke Android dex file translation method", e3.getTargetException());
                        }
                    }
                }
            }
        }
    }

    public static class Wrapping extends AndroidClassLoadingStrategy {
        public Wrapping(File file) {
            this(file, DexProcessor.ForSdkCompiler.makeDefault());
        }

        public Wrapping(File file, DexProcessor dexProcessor) {
            super(file, dexProcessor);
        }

        @Override // net.bytebuddy.android.AndroidClassLoadingStrategy
        protected Map<TypeDescription, Class<?>> doLoad(@MaybeNull ClassLoader classLoader, Set<TypeDescription> set, File file) {
            DexClassLoader dexClassLoader = new DexClassLoader(file.getAbsolutePath(), this.privateDirectory.getAbsolutePath(), AndroidClassLoadingStrategy.EMPTY_LIBRARY_PATH, classLoader);
            HashMap map = new HashMap();
            for (TypeDescription typeDescription : set) {
                try {
                    map.put(typeDescription, Class.forName(typeDescription.getName(), false, dexClassLoader));
                } catch (ClassNotFoundException e) {
                    throw new IllegalStateException("Cannot load " + typeDescription, e);
                }
            }
            return map;
        }
    }

    public static class Injecting extends AndroidClassLoadingStrategy {
        private static final Dispatcher DISPATCHER;

        static {
            Dispatcher forAndroidPVm;
            try {
                forAndroidPVm = new Dispatcher.ForAndroidPVm(BaseDexClassLoader.class.getMethod("addDexPath", String.class, Boolean.TYPE));
            } catch (Throwable unused) {
                forAndroidPVm = Dispatcher.ForLegacyVm.INSTANCE;
            }
            DISPATCHER = forAndroidPVm;
        }

        public Injecting(File file) {
            this(file, DexProcessor.ForSdkCompiler.makeDefault());
        }

        public Injecting(File file, DexProcessor dexProcessor) {
            super(file, dexProcessor);
        }

        @Override // net.bytebuddy.android.AndroidClassLoadingStrategy, net.bytebuddy.dynamic.loading.ClassLoadingStrategy
        public Map<TypeDescription, Class<?>> load(@MaybeNull ClassLoader classLoader, Map<TypeDescription, byte[]> map) {
            if (classLoader == null) {
                throw new IllegalArgumentException("Cannot inject classes into the bootstrap class loader on Android");
            }
            return super.load(classLoader, map);
        }

        /* JADX WARN: Code duplicated, block: B:23:0x0052  */
        @Override // net.bytebuddy.android.AndroidClassLoadingStrategy
        protected Map<TypeDescription, Class<?>> doLoad(@MaybeNull ClassLoader classLoader, Set<TypeDescription> set, File file) throws IOException {
            dalvik.system.DexFile dexFileLoadDex = DISPATCHER.loadDex(this.privateDirectory, file, classLoader, this.randomString);
            try {
                HashMap map = new HashMap();
                for (TypeDescription typeDescription : set) {
                    synchronized (classLoader) {
                        Class<?> clsLoadClass = DISPATCHER.loadClass(dexFileLoadDex, classLoader, typeDescription);
                        if (clsLoadClass == null) {
                            throw new IllegalStateException("Could not load " + typeDescription);
                        }
                        map.put(typeDescription, clsLoadClass);
                        if (dexFileLoadDex != null) {
                            dexFileLoadDex.close();
                        }
                        throw th;
                    }
                }
                if (dexFileLoadDex != null) {
                    dexFileLoadDex.close();
                }
                return map;
            } catch (Throwable th) {
                if (dexFileLoadDex != null) {
                    dexFileLoadDex.close();
                }
                throw th;
            }
        }

        protected interface Dispatcher {
            @MaybeNull
            Class<?> loadClass(dalvik.system.DexFile dexFile, @MaybeNull ClassLoader classLoader, TypeDescription typeDescription);

            @MaybeNull
            dalvik.system.DexFile loadDex(File file, File file2, @MaybeNull ClassLoader classLoader, RandomString randomString) throws IOException;

            public enum ForLegacyVm implements Dispatcher {
                INSTANCE;

                private static final String EXTENSION = ".data";
                private static final int NO_FLAGS = 0;

                @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.Injecting.Dispatcher
                @MaybeNull
                public dalvik.system.DexFile loadDex(File file, File file2, @MaybeNull ClassLoader classLoader, RandomString randomString) throws IOException {
                    return dalvik.system.DexFile.loadDex(file2.getAbsolutePath(), new File(file.getAbsolutePath(), randomString.nextString() + EXTENSION).getAbsolutePath(), 0);
                }

                @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.Injecting.Dispatcher
                @MaybeNull
                public Class<?> loadClass(dalvik.system.DexFile dexFile, @MaybeNull ClassLoader classLoader, TypeDescription typeDescription) {
                    return dexFile.loadClass(typeDescription.getName(), classLoader);
                }
            }

            public static class ForAndroidPVm implements Dispatcher {

                @AlwaysNull
                private static final dalvik.system.DexFile NO_RETURN_VALUE = null;
                private final Method addDexPath;

                protected ForAndroidPVm(Method method) {
                    this.addDexPath = method;
                }

                @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.Injecting.Dispatcher
                @MaybeNull
                public dalvik.system.DexFile loadDex(File file, File file2, @MaybeNull ClassLoader classLoader, RandomString randomString) throws IOException {
                    if (!(classLoader instanceof BaseDexClassLoader)) {
                        throw new IllegalArgumentException("On Android P, a class injection can only be applied to BaseDexClassLoader: " + classLoader);
                    }
                    try {
                        this.addDexPath.invoke(classLoader, file2.getAbsolutePath(), true);
                        return NO_RETURN_VALUE;
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Cannot access BaseDexClassLoader#addDexPath(String, boolean)", e);
                    } catch (InvocationTargetException e2) {
                        Throwable targetException = e2.getTargetException();
                        if (targetException instanceof IOException) {
                            throw ((IOException) targetException);
                        }
                        throw new IllegalStateException("Cannot invoke BaseDexClassLoader#addDexPath(String, boolean)", targetException);
                    }
                }

                @Override // net.bytebuddy.android.AndroidClassLoadingStrategy.Injecting.Dispatcher
                public Class<?> loadClass(@MaybeNull dalvik.system.DexFile dexFile, @MaybeNull ClassLoader classLoader, TypeDescription typeDescription) {
                    try {
                        return Class.forName(typeDescription.getName(), false, classLoader);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("Could not locate " + typeDescription, e);
                    }
                }
            }
        }
    }
}
