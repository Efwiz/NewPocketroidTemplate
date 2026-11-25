package net.bytebuddy.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import net.bytebuddy.build.AccessControllerPlugin;
import net.bytebuddy.build.CachedReturnPlugin;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import net.bytebuddy.utility.nullability.MaybeNull;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: loaded from: classes12.dex */
public abstract class FileSystem {
    private static final boolean ACCESS_CONTROLLER;
    private static /* synthetic */ FileSystem INSTANCE;

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
    }

    public abstract void copy(File file, File file2) throws IOException;

    public abstract void move(File file, File file2) throws IOException;

    @CachedReturnPlugin.Enhance(JvmAbi.INSTANCE_FIELD)
    public static FileSystem getInstance() {
        FileSystem forLegacyVm;
        if (INSTANCE != null) {
            forLegacyVm = null;
        } else {
            try {
                Class.forName("java.nio.file.Files", false, ClassLoadingStrategy.BOOTSTRAP_LOADER);
                forLegacyVm = new ForNio2CapableVm();
            } catch (ClassNotFoundException unused) {
                forLegacyVm = new ForLegacyVm();
            }
        }
        if (forLegacyVm == null) {
            return INSTANCE;
        }
        INSTANCE = forLegacyVm;
        return forLegacyVm;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @AccessControllerPlugin.Enhance
    public static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
        return ACCESS_CONTROLLER ? (T) AccessController.doPrivileged(privilegedAction) : privilegedAction.run();
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ForLegacyVm extends FileSystem {
        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass();
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        protected ForLegacyVm() {
        }

        @Override // net.bytebuddy.utility.FileSystem
        public void copy(File file, File file2) throws IOException {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i != -1) {
                            fileOutputStream.write(bArr, 0, i);
                        } else {
                            fileOutputStream.close();
                            fileInputStream.close();
                            return;
                        }
                    }
                } catch (Throwable th) {
                    fileOutputStream.close();
                    throw th;
                }
            } catch (Throwable th2) {
                fileInputStream.close();
                throw th2;
            }
        }

        @Override // net.bytebuddy.utility.FileSystem
        public void move(File file, File file2) throws IOException {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                try {
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        } else {
                            fileOutputStream.write(bArr, 0, i);
                        }
                    }
                    fileOutputStream.close();
                    fileInputStream.close();
                    if (file.delete()) {
                        return;
                    }
                    file.deleteOnExit();
                } catch (Throwable th) {
                    fileOutputStream.close();
                    throw th;
                }
            } catch (Throwable th2) {
                fileInputStream.close();
                throw th2;
            }
        }
    }

    @HashCodeAndEqualsPlugin.Enhance
    protected static class ForNio2CapableVm extends FileSystem {
        private static final Dispatcher DISPATCHER = (Dispatcher) FileSystem.doPrivileged(JavaDispatcher.of(Dispatcher.class));
        private static final Files FILES = (Files) FileSystem.doPrivileged(JavaDispatcher.of(Files.class));
        private static final StandardCopyOption STANDARD_COPY_OPTION = (StandardCopyOption) FileSystem.doPrivileged(JavaDispatcher.of(StandardCopyOption.class));

        @JavaDispatcher.Proxied(CommonClassNames.JAVA_IO_FILE)
        protected interface Dispatcher {
            @JavaDispatcher.Proxied("toPath")
            Object toPath(File file) throws IOException;
        }

        @JavaDispatcher.Proxied("java.nio.file.Files")
        protected interface Files {
            @JavaDispatcher.IsStatic
            @JavaDispatcher.Proxied(ConfigConstants.CONFIG_RENAMELIMIT_COPY)
            Object copy(@JavaDispatcher.Proxied("java.nio.file.Path") Object obj, @JavaDispatcher.Proxied("java.nio.file.Path") Object obj2, @JavaDispatcher.Proxied("java.nio.file.CopyOption") Object[] objArr) throws IOException;

            @JavaDispatcher.IsStatic
            @JavaDispatcher.Proxied("move")
            Object move(@JavaDispatcher.Proxied("java.nio.file.Path") Object obj, @JavaDispatcher.Proxied("java.nio.file.Path") Object obj2, @JavaDispatcher.Proxied("java.nio.file.CopyOption") Object[] objArr) throws IOException;
        }

        @JavaDispatcher.Proxied("java.nio.file.StandardCopyOption")
        protected interface StandardCopyOption {
            @JavaDispatcher.Container
            @JavaDispatcher.Proxied("toArray")
            Object[] toArray(int i);

            @JavaDispatcher.IsStatic
            @JavaDispatcher.Proxied("valueOf")
            Object valueOf(String str);
        }

        public boolean equals(@MaybeNull Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass();
        }

        public int hashCode() {
            return getClass().hashCode();
        }

        protected ForNio2CapableVm() {
        }

        @Override // net.bytebuddy.utility.FileSystem
        public void copy(File file, File file2) throws IOException {
            StandardCopyOption standardCopyOption = STANDARD_COPY_OPTION;
            Object[] array = standardCopyOption.toArray(1);
            array[0] = standardCopyOption.valueOf("REPLACE_EXISTING");
            Files files = FILES;
            Dispatcher dispatcher = DISPATCHER;
            files.copy(dispatcher.toPath(file), dispatcher.toPath(file2), array);
        }

        @Override // net.bytebuddy.utility.FileSystem
        public void move(File file, File file2) throws IOException {
            StandardCopyOption standardCopyOption = STANDARD_COPY_OPTION;
            Object[] array = standardCopyOption.toArray(1);
            array[0] = standardCopyOption.valueOf("REPLACE_EXISTING");
            Files files = FILES;
            Dispatcher dispatcher = DISPATCHER;
            files.move(dispatcher.toPath(file), dispatcher.toPath(file2), array);
        }
    }
}
