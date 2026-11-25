package net.bytebuddy.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import net.bytebuddy.agent.utility.nullability.AlwaysNull;
import net.bytebuddy.agent.utility.nullability.MaybeNull;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

/* JADX INFO: loaded from: classes12.dex */
public class ByteBuddyAgent {
    private static final String AGENT_ARGUMENT_SEPARATOR = "=";
    private static final String AGENT_CLASS_PROPERTY = "Agent-Class";
    private static final String ATTACHER_FILE_NAME = "byteBuddyAttacher";
    private static final AttachmentTypeEvaluator ATTACHMENT_TYPE_EVALUATOR = (AttachmentTypeEvaluator) doPrivileged(AttachmentTypeEvaluator.InstallationAction.INSTANCE);

    @AlwaysNull
    private static final ClassLoader BOOTSTRAP_CLASS_LOADER = null;
    private static final int BUFFER_SIZE = 8192;

    @AlwaysNull
    private static final File CANNOT_SELF_RESOLVE = null;
    private static final String CAN_REDEFINE_CLASSES_PROPERTY = "Can-Redefine-Classes";
    private static final String CAN_RETRANSFORM_CLASSES_PROPERTY = "Can-Retransform-Classes";
    private static final String CAN_SET_NATIVE_METHOD_PREFIX = "Can-Set-Native-Method-Prefix";
    private static final String CLASS_FILE_EXTENSION = ".class";
    private static final String CLASS_PATH_ARGUMENT = "-cp";
    private static final int END_OF_FILE = -1;
    private static final String FILE_PROTOCOL = "file";
    private static final String INSTRUMENTATION_METHOD = "getInstrumentation";
    private static final String JAR_FILE_EXTENSION = ".jar";
    private static final String JAVA_HOME = "java.home";
    public static final String LATENT_RESOLVE = "net.bytebuddy.agent.latent";
    private static final String MANIFEST_VERSION_VALUE = "1.0";
    private static final String OS_NAME = "os.name";
    private static final int START_INDEX = 0;
    private static final int SUCCESSFUL_ATTACH = 0;

    @AlwaysNull
    private static final Instrumentation UNAVAILABLE = null;

    @AlwaysNull
    private static final String WITHOUT_ARGUMENT = null;

    private ByteBuddyAgent() {
        throw new UnsupportedOperationException("This class is a utility class and not supposed to be instantiated");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
        try {
            return (T) Class.forName("java.security.AccessController").getMethod("doPrivileged", PrivilegedAction.class).invoke(null, privilegedAction);
        } catch (ClassNotFoundException unused) {
            return privilegedAction.run();
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to access access controller", e);
        } catch (NoSuchMethodException e2) {
            throw new IllegalStateException("Failed to resolve well-known access controller method", e2);
        } catch (InvocationTargetException e3) {
            throw new IllegalStateException("Failed to invoke access controller", e3.getTargetException());
        }
    }

    public static Instrumentation getInstrumentation() {
        Instrumentation instrumentationDoGetInstrumentation = doGetInstrumentation();
        if (instrumentationDoGetInstrumentation != null) {
            return instrumentationDoGetInstrumentation;
        }
        throw new IllegalStateException("The Byte Buddy agent is not initialized or unavailable");
    }

    public static void attach(File file, String str) {
        attach(file, str, WITHOUT_ARGUMENT);
    }

    public static void attach(File file, String str, @MaybeNull String str2) {
        attach(file, str, str2, AttachmentProvider.DEFAULT);
    }

    public static void attach(File file, String str, AttachmentProvider attachmentProvider) {
        attach(file, str, WITHOUT_ARGUMENT, attachmentProvider);
    }

    public static void attach(File file, String str, @MaybeNull String str2, AttachmentProvider attachmentProvider) {
        install(attachmentProvider, str, str2, new AgentProvider.ForExistingAgent(file), false);
    }

    public static void attach(File file, ProcessProvider processProvider) {
        attach(file, processProvider, WITHOUT_ARGUMENT);
    }

    public static void attach(File file, ProcessProvider processProvider, @MaybeNull String str) {
        attach(file, processProvider, str, AttachmentProvider.DEFAULT);
    }

    public static void attach(File file, ProcessProvider processProvider, AttachmentProvider attachmentProvider) {
        attach(file, processProvider, WITHOUT_ARGUMENT, attachmentProvider);
    }

    public static void attach(File file, ProcessProvider processProvider, @MaybeNull String str, AttachmentProvider attachmentProvider) {
        install(attachmentProvider, processProvider.resolve(), str, new AgentProvider.ForExistingAgent(file), false);
    }

    public static void attachNative(File file, String str) {
        attachNative(file, str, WITHOUT_ARGUMENT);
    }

    public static void attachNative(File file, String str, @MaybeNull String str2) {
        attachNative(file, str, str2, AttachmentProvider.DEFAULT);
    }

    public static void attachNative(File file, String str, AttachmentProvider attachmentProvider) {
        attachNative(file, str, WITHOUT_ARGUMENT, attachmentProvider);
    }

    public static void attachNative(File file, String str, @MaybeNull String str2, AttachmentProvider attachmentProvider) {
        install(attachmentProvider, str, str2, new AgentProvider.ForExistingAgent(file), true);
    }

    public static void attachNative(File file, ProcessProvider processProvider) {
        attachNative(file, processProvider, WITHOUT_ARGUMENT);
    }

    public static void attachNative(File file, ProcessProvider processProvider, @MaybeNull String str) {
        attachNative(file, processProvider, str, AttachmentProvider.DEFAULT);
    }

    public static void attachNative(File file, ProcessProvider processProvider, AttachmentProvider attachmentProvider) {
        attachNative(file, processProvider, WITHOUT_ARGUMENT, attachmentProvider);
    }

    public static void attachNative(File file, ProcessProvider processProvider, @MaybeNull String str, AttachmentProvider attachmentProvider) {
        install(attachmentProvider, processProvider.resolve(), str, new AgentProvider.ForExistingAgent(file), true);
    }

    public static Instrumentation install() {
        return install(AttachmentProvider.DEFAULT);
    }

    public static Instrumentation install(AttachmentProvider attachmentProvider) {
        return install(attachmentProvider, ProcessProvider.ForCurrentVm.INSTANCE);
    }

    public static Instrumentation install(ProcessProvider processProvider) {
        return install(AttachmentProvider.DEFAULT, processProvider);
    }

    public static synchronized Instrumentation install(AttachmentProvider attachmentProvider, ProcessProvider processProvider) {
        Instrumentation instrumentationDoGetInstrumentation = doGetInstrumentation();
        if (instrumentationDoGetInstrumentation != null) {
            return instrumentationDoGetInstrumentation;
        }
        install(attachmentProvider, processProvider.resolve(), WITHOUT_ARGUMENT, AgentProvider.ForByteBuddyAgent.INSTANCE, false);
        return getInstrumentation();
    }

    private static void install(AttachmentProvider attachmentProvider, String str, @MaybeNull String str2, AgentProvider agentProvider, boolean z) {
        AttachmentProvider.Accessor accessorAttempt = attachmentProvider.attempt();
        if (!accessorAttempt.isAvailable()) {
            throw new IllegalStateException("No compatible attachment provider is available");
        }
        try {
            if (accessorAttempt.isExternalAttachmentRequired() && ATTACHMENT_TYPE_EVALUATOR.requiresExternalAttachment(str)) {
                installExternal(accessorAttempt.getExternalAttachment(), str, agentProvider.resolve(), z, str2);
            } else {
                Attacher.install(accessorAttempt.getVirtualMachineType(), str, agentProvider.resolve().getAbsolutePath(), z, str2);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            throw new IllegalStateException("Error during attachment using: " + attachmentProvider, e2);
        }
    }

    private static void installExternal(AttachmentProvider.Accessor.ExternalAttachment externalAttachment, String str, File file, boolean z, @MaybeNull String str2) throws Exception {
        String str3 = "";
        File fileTrySelfResolve = trySelfResolve();
        File fileCreateTempFile = null;
        if (fileTrySelfResolve == null) {
            try {
                InputStream resourceAsStream = Attacher.class.getResourceAsStream("/" + Attacher.class.getName().replace('.', '/') + ".class");
                if (resourceAsStream == null) {
                    throw new IllegalStateException("Cannot locate class file for Byte Buddy installation process");
                }
                try {
                    fileCreateTempFile = File.createTempFile(ATTACHER_FILE_NAME, JAR_FILE_EXTENSION);
                    JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(fileCreateTempFile));
                    try {
                        jarOutputStream.putNextEntry(new JarEntry(Attacher.class.getName().replace('.', '/') + ".class"));
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i = resourceAsStream.read(bArr);
                            if (i == -1) {
                                break;
                            } else {
                                jarOutputStream.write(bArr, 0, i);
                            }
                        }
                        jarOutputStream.closeEntry();
                        jarOutputStream.close();
                        resourceAsStream.close();
                    } catch (Throwable th) {
                        jarOutputStream.close();
                        throw th;
                    }
                } catch (Throwable th2) {
                    resourceAsStream.close();
                    throw th2;
                }
            } catch (Throwable th3) {
                if (fileCreateTempFile != null && !fileCreateTempFile.delete()) {
                    fileCreateTempFile.deleteOnExit();
                }
                throw th3;
            }
        }
        StringBuilder sb = new StringBuilder();
        if (fileTrySelfResolve == null) {
            fileTrySelfResolve = fileCreateTempFile;
        }
        StringBuilder sbAppend = sb.append(fileTrySelfResolve.getCanonicalPath());
        Iterator<File> it = externalAttachment.getClassPath().iterator();
        while (it.hasNext()) {
            sbAppend.append(File.pathSeparatorChar).append(it.next().getCanonicalPath());
        }
        String[] strArr = new String[10];
        strArr[0] = System.getProperty("java.home") + File.separatorChar + "bin" + File.separatorChar + (System.getProperty("os.name", "").toLowerCase(Locale.US).contains("windows") ? "java.exe" : SuffixConstants.EXTENSION_java);
        strArr[1] = "-Dnet.bytebuddy.agent.attacher.dump=" + System.getProperty(Attacher.DUMP_PROPERTY, "");
        strArr[2] = CLASS_PATH_ARGUMENT;
        strArr[3] = sbAppend.toString();
        strArr[4] = Attacher.class.getName();
        strArr[5] = externalAttachment.getVirtualMachineType();
        strArr[6] = str;
        strArr[7] = file.getAbsolutePath();
        strArr[8] = Boolean.toString(z);
        if (str2 != null) {
            str3 = "=" + str2;
        }
        strArr[9] = str3;
        if (new ProcessBuilder(strArr).start().waitFor() != 0) {
            throw new IllegalStateException("Could not self-attach to current VM using external process");
        }
        if (fileCreateTempFile == null || fileCreateTempFile.delete()) {
            return;
        }
        fileCreateTempFile.deleteOnExit();
    }

    @MaybeNull
    private static File trySelfResolve() {
        try {
            if (Boolean.getBoolean(LATENT_RESOLVE)) {
                return CANNOT_SELF_RESOLVE;
            }
            ProtectionDomain protectionDomain = Attacher.class.getProtectionDomain();
            if (protectionDomain == null) {
                return CANNOT_SELF_RESOLVE;
            }
            CodeSource codeSource = protectionDomain.getCodeSource();
            if (codeSource == null) {
                return CANNOT_SELF_RESOLVE;
            }
            URL location = codeSource.getLocation();
            if (!location.getProtocol().equals("file")) {
                return CANNOT_SELF_RESOLVE;
            }
            try {
                File file = new File(location.toURI());
                return file.getPath().contains("=") ? CANNOT_SELF_RESOLVE : file;
            } catch (URISyntaxException unused) {
                return new File(location.getPath());
            }
        } catch (Exception unused2) {
            return CANNOT_SELF_RESOLVE;
        }
    }

    @MaybeNull
    private static Instrumentation doGetInstrumentation() {
        try {
            Class<?> cls = Class.forName(Installer.class.getName(), true, ClassLoader.getSystemClassLoader());
            try {
                Class<?> cls2 = Class.forName("java.lang.Module");
                Method method = Class.class.getMethod("getModule", new Class[0]);
                Object objInvoke = method.invoke(ByteBuddyAgent.class, new Object[0]);
                Object objInvoke2 = method.invoke(cls, new Object[0]);
                if (!((Boolean) cls2.getMethod("canRead", cls2).invoke(objInvoke, objInvoke2)).booleanValue()) {
                    cls2.getMethod("addReads", cls2).invoke(objInvoke, objInvoke2);
                }
            } catch (ClassNotFoundException unused) {
            }
            return (Instrumentation) Class.forName(Installer.class.getName(), true, ClassLoader.getSystemClassLoader()).getMethod(INSTRUMENTATION_METHOD, new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused2) {
            return UNAVAILABLE;
        }
    }

    public interface AttachmentProvider {
        public static final AttachmentProvider DEFAULT = new Compound(ForModularizedVm.INSTANCE, ForJ9Vm.INSTANCE, ForStandardToolsJarVm.JVM_ROOT, ForStandardToolsJarVm.JDK_ROOT, ForStandardToolsJarVm.MACINTOSH, ForUserDefinedToolsJar.INSTANCE, ForEmulatedAttachment.INSTANCE);

        Accessor attempt();

        public interface Accessor {
            public static final String VIRTUAL_MACHINE_TYPE_NAME = "com.sun.tools.attach.VirtualMachine";
            public static final String VIRTUAL_MACHINE_TYPE_NAME_J9 = "com.ibm.tools.attach.VirtualMachine";

            ExternalAttachment getExternalAttachment();

            Class<?> getVirtualMachineType();

            boolean isAvailable();

            boolean isExternalAttachmentRequired();

            public enum Unavailable implements Accessor {
                INSTANCE;

                @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                public boolean isAvailable() {
                    return false;
                }

                @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                public boolean isExternalAttachmentRequired() {
                    throw new IllegalStateException("Cannot read the virtual machine type for an unavailable accessor");
                }

                @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                public Class<?> getVirtualMachineType() {
                    throw new IllegalStateException("Cannot read the virtual machine type for an unavailable accessor");
                }

                @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                public ExternalAttachment getExternalAttachment() {
                    throw new IllegalStateException("Cannot read the virtual machine type for an unavailable accessor");
                }
            }

            public static class ExternalAttachment {
                private final List<File> classPath;
                private final String virtualMachineType;

                public ExternalAttachment(String str, List<File> list) {
                    this.virtualMachineType = str;
                    this.classPath = list;
                }

                public String getVirtualMachineType() {
                    return this.virtualMachineType;
                }

                public List<File> getClassPath() {
                    return this.classPath;
                }
            }

            public static abstract class Simple implements Accessor {
                protected final Class<?> virtualMachineType;

                @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                public boolean isAvailable() {
                    return true;
                }

                protected Simple(Class<?> cls) {
                    this.virtualMachineType = cls;
                }

                public static Accessor of(@MaybeNull ClassLoader classLoader, File... fileArr) {
                    try {
                        return new WithExternalAttachment(Class.forName(Accessor.VIRTUAL_MACHINE_TYPE_NAME, false, classLoader), Arrays.asList(fileArr));
                    } catch (ClassNotFoundException unused) {
                        return Unavailable.INSTANCE;
                    }
                }

                public static Accessor ofJ9() {
                    try {
                        return new WithExternalAttachment(ClassLoader.getSystemClassLoader().loadClass(Accessor.VIRTUAL_MACHINE_TYPE_NAME_J9), Collections.emptyList());
                    } catch (ClassNotFoundException unused) {
                        return Unavailable.INSTANCE;
                    }
                }

                @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                public Class<?> getVirtualMachineType() {
                    return this.virtualMachineType;
                }

                protected static class WithExternalAttachment extends Simple {
                    private final List<File> classPath;

                    @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                    public boolean isExternalAttachmentRequired() {
                        return true;
                    }

                    public WithExternalAttachment(Class<?> cls, List<File> list) {
                        super(cls);
                        this.classPath = list;
                    }

                    @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                    public ExternalAttachment getExternalAttachment() {
                        return new ExternalAttachment(this.virtualMachineType.getName(), this.classPath);
                    }
                }

                protected static class WithDirectAttachment extends Simple {
                    @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                    public boolean isExternalAttachmentRequired() {
                        return false;
                    }

                    public WithDirectAttachment(Class<?> cls) {
                        super(cls);
                    }

                    @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider.Accessor
                    public ExternalAttachment getExternalAttachment() {
                        throw new IllegalStateException("Cannot apply external attachment");
                    }
                }
            }
        }

        public enum ForModularizedVm implements AttachmentProvider {
            INSTANCE;

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider
            public Accessor attempt() {
                return Accessor.Simple.of(ClassLoader.getSystemClassLoader(), new File[0]);
            }
        }

        public enum ForJ9Vm implements AttachmentProvider {
            INSTANCE;

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider
            public Accessor attempt() {
                return Accessor.Simple.ofJ9();
            }
        }

        public enum ForStandardToolsJarVm implements AttachmentProvider {
            JVM_ROOT("../lib/tools.jar"),
            JDK_ROOT("lib/tools.jar"),
            MACINTOSH("../Classes/classes.jar");

            private static final String JAVA_HOME_PROPERTY = "java.home";
            private final String toolsJarPath;

            ForStandardToolsJarVm(String str) {
                this.toolsJarPath = str;
            }

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider
            public Accessor attempt() {
                File file = new File(System.getProperty("java.home"), this.toolsJarPath);
                try {
                    return (file.isFile() && file.canRead()) ? Accessor.Simple.of(new URLClassLoader(new URL[]{file.toURI().toURL()}, ByteBuddyAgent.BOOTSTRAP_CLASS_LOADER), file) : Accessor.Unavailable.INSTANCE;
                } catch (MalformedURLException unused) {
                    throw new IllegalStateException("Could not represent " + file + " as URL");
                }
            }
        }

        public enum ForUserDefinedToolsJar implements AttachmentProvider {
            INSTANCE;

            public static final String PROPERTY = "net.bytebuddy.agent.toolsjar";

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider
            public Accessor attempt() {
                String property = System.getProperty(PROPERTY);
                if (property == null) {
                    return Accessor.Unavailable.INSTANCE;
                }
                File file = new File(property);
                try {
                    return Accessor.Simple.of(new URLClassLoader(new URL[]{file.toURI().toURL()}, ByteBuddyAgent.BOOTSTRAP_CLASS_LOADER), file);
                } catch (MalformedURLException unused) {
                    throw new IllegalStateException("Could not represent " + file + " as URL");
                }
            }
        }

        public enum ForEmulatedAttachment implements AttachmentProvider {
            INSTANCE;

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider
            public Accessor attempt() {
                try {
                    return new Accessor.Simple.WithDirectAttachment((Class) ByteBuddyAgent.doPrivileged(VirtualMachine.Resolver.INSTANCE));
                } catch (Throwable unused) {
                    return Accessor.Unavailable.INSTANCE;
                }
            }
        }

        public static class Compound implements AttachmentProvider {
            private final List<AttachmentProvider> attachmentProviders;

            public Compound(AttachmentProvider... attachmentProviderArr) {
                this((List<? extends AttachmentProvider>) Arrays.asList(attachmentProviderArr));
            }

            public Compound(List<? extends AttachmentProvider> list) {
                this.attachmentProviders = new ArrayList();
                for (AttachmentProvider attachmentProvider : list) {
                    if (attachmentProvider instanceof Compound) {
                        this.attachmentProviders.addAll(((Compound) attachmentProvider).attachmentProviders);
                    } else {
                        this.attachmentProviders.add(attachmentProvider);
                    }
                }
            }

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentProvider
            public Accessor attempt() {
                Iterator<AttachmentProvider> it = this.attachmentProviders.iterator();
                while (it.hasNext()) {
                    Accessor accessorAttempt = it.next().attempt();
                    if (accessorAttempt.isAvailable()) {
                        return accessorAttempt;
                    }
                }
                return Accessor.Unavailable.INSTANCE;
            }
        }
    }

    public interface ProcessProvider {
        String resolve();

        public enum ForCurrentVm implements ProcessProvider {
            INSTANCE;

            private final ProcessProvider dispatcher = ForJava9CapableVm.make();

            ForCurrentVm() {
            }

            @Override // net.bytebuddy.agent.ByteBuddyAgent.ProcessProvider
            public String resolve() {
                return this.dispatcher.resolve();
            }

            protected enum ForLegacyVm implements ProcessProvider {
                INSTANCE;

                @Override // net.bytebuddy.agent.ByteBuddyAgent.ProcessProvider
                public String resolve() {
                    try {
                        Method method = Class.forName("java.lang.management.ManagementFactory").getMethod("getRuntimeMXBean", new Class[0]);
                        String str = (String) method.getReturnType().getMethod("getName", new Class[0]).invoke(method.invoke(null, new Object[0]), new Object[0]);
                        int iIndexOf = str.indexOf(64);
                        if (iIndexOf == -1) {
                            throw new IllegalStateException("Cannot extract process id from runtime management bean");
                        }
                        return str.substring(0, iIndexOf);
                    } catch (Exception e) {
                        throw new IllegalStateException("Failed to access VM name via management factory", e);
                    }
                }
            }

            protected static class ForJava9CapableVm implements ProcessProvider {
                private final Method current;
                private final Method pid;

                protected ForJava9CapableVm(Method method, Method method2) {
                    this.current = method;
                    this.pid = method2;
                }

                public static ProcessProvider make() {
                    try {
                        return new ForJava9CapableVm(Class.forName("java.lang.ProcessHandle").getMethod("current", new Class[0]), Class.forName("java.lang.ProcessHandle").getMethod("pid", new Class[0]));
                    } catch (Exception unused) {
                        return ForLegacyVm.INSTANCE;
                    }
                }

                @Override // net.bytebuddy.agent.ByteBuddyAgent.ProcessProvider
                public String resolve() {
                    try {
                        return this.pid.invoke(this.current.invoke(null, new Object[0]), new Object[0]).toString();
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException("Cannot access Java 9 process API", e);
                    } catch (InvocationTargetException e2) {
                        throw new IllegalStateException("Error when accessing Java 9 process API", e2.getTargetException());
                    }
                }
            }
        }
    }

    protected interface AgentProvider {
        File resolve() throws IOException;

        public enum ForByteBuddyAgent implements AgentProvider {
            INSTANCE;

            private static final String AGENT_FILE_NAME = "byteBuddyAgent";

            @MaybeNull
            private static File trySelfResolve() throws IOException {
                CodeSource codeSource;
                File file;
                ProtectionDomain protectionDomain = Installer.class.getProtectionDomain();
                if (!Boolean.getBoolean(ByteBuddyAgent.LATENT_RESOLVE) && protectionDomain != null && (codeSource = protectionDomain.getCodeSource()) != null) {
                    URL location = codeSource.getLocation();
                    if (!location.getProtocol().equals("file")) {
                        return ByteBuddyAgent.CANNOT_SELF_RESOLVE;
                    }
                    try {
                        file = new File(location.toURI());
                    } catch (URISyntaxException unused) {
                        file = new File(location.getPath());
                    }
                    if (!file.isFile() || !file.canRead()) {
                        return ByteBuddyAgent.CANNOT_SELF_RESOLVE;
                    }
                    JarInputStream jarInputStream = new JarInputStream(new FileInputStream(file));
                    try {
                        Manifest manifest = jarInputStream.getManifest();
                        if (manifest == null) {
                            return ByteBuddyAgent.CANNOT_SELF_RESOLVE;
                        }
                        Attributes mainAttributes = manifest.getMainAttributes();
                        if (mainAttributes == null) {
                            return ByteBuddyAgent.CANNOT_SELF_RESOLVE;
                        }
                        return (Installer.class.getName().equals(mainAttributes.getValue(ByteBuddyAgent.AGENT_CLASS_PROPERTY)) && Boolean.parseBoolean(mainAttributes.getValue(ByteBuddyAgent.CAN_REDEFINE_CLASSES_PROPERTY)) && Boolean.parseBoolean(mainAttributes.getValue(ByteBuddyAgent.CAN_RETRANSFORM_CLASSES_PROPERTY)) && Boolean.parseBoolean(mainAttributes.getValue(ByteBuddyAgent.CAN_SET_NATIVE_METHOD_PREFIX))) ? file : ByteBuddyAgent.CANNOT_SELF_RESOLVE;
                    } finally {
                        jarInputStream.close();
                    }
                }
                return ByteBuddyAgent.CANNOT_SELF_RESOLVE;
            }

            private static File createJarFile() throws IOException {
                InputStream resourceAsStream = Installer.class.getResourceAsStream("/" + Installer.class.getName().replace('.', '/') + ".class");
                if (resourceAsStream == null) {
                    throw new IllegalStateException("Cannot locate class file for Byte Buddy installer");
                }
                try {
                    File fileCreateTempFile = File.createTempFile(AGENT_FILE_NAME, ByteBuddyAgent.JAR_FILE_EXTENSION);
                    fileCreateTempFile.deleteOnExit();
                    Manifest manifest = new Manifest();
                    manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
                    manifest.getMainAttributes().put(new Attributes.Name(ByteBuddyAgent.AGENT_CLASS_PROPERTY), Installer.class.getName());
                    manifest.getMainAttributes().put(new Attributes.Name(ByteBuddyAgent.CAN_REDEFINE_CLASSES_PROPERTY), Boolean.TRUE.toString());
                    manifest.getMainAttributes().put(new Attributes.Name(ByteBuddyAgent.CAN_RETRANSFORM_CLASSES_PROPERTY), Boolean.TRUE.toString());
                    manifest.getMainAttributes().put(new Attributes.Name(ByteBuddyAgent.CAN_SET_NATIVE_METHOD_PREFIX), Boolean.TRUE.toString());
                    JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(fileCreateTempFile), manifest);
                    try {
                        jarOutputStream.putNextEntry(new JarEntry(Installer.class.getName().replace('.', '/') + ".class"));
                        byte[] bArr = new byte[8192];
                        while (true) {
                            int i = resourceAsStream.read(bArr);
                            if (i != -1) {
                                jarOutputStream.write(bArr, 0, i);
                            } else {
                                jarOutputStream.closeEntry();
                                jarOutputStream.close();
                                resourceAsStream.close();
                                return fileCreateTempFile;
                            }
                        }
                    } catch (Throwable th) {
                        jarOutputStream.close();
                        throw th;
                    }
                } catch (Throwable th2) {
                    resourceAsStream.close();
                    throw th2;
                }
            }

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AgentProvider
            public File resolve() throws IOException {
                try {
                    File fileTrySelfResolve = trySelfResolve();
                    if (fileTrySelfResolve != null) {
                        return fileTrySelfResolve;
                    }
                } catch (Exception unused) {
                }
                return createJarFile();
            }
        }

        public static class ForExistingAgent implements AgentProvider {
            private final File agent;

            protected ForExistingAgent(File file) {
                this.agent = file;
            }

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AgentProvider
            public File resolve() {
                return this.agent;
            }
        }
    }

    protected interface AttachmentTypeEvaluator {

        public enum Disabled implements AttachmentTypeEvaluator {
            INSTANCE;

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentTypeEvaluator
            public boolean requiresExternalAttachment(String str) {
                return false;
            }
        }

        boolean requiresExternalAttachment(String str);

        public enum InstallationAction implements PrivilegedAction<AttachmentTypeEvaluator> {
            INSTANCE;

            private static final String JDK_ALLOW_SELF_ATTACH = "jdk.attach.allowAttachSelf";

            @Override // java.security.PrivilegedAction
            public AttachmentTypeEvaluator run() {
                try {
                    if (Boolean.getBoolean(JDK_ALLOW_SELF_ATTACH)) {
                        return Disabled.INSTANCE;
                    }
                    return new ForJava9CapableVm(Class.forName("java.lang.ProcessHandle").getMethod("current", new Class[0]), Class.forName("java.lang.ProcessHandle").getMethod("pid", new Class[0]));
                } catch (Exception unused) {
                    return Disabled.INSTANCE;
                }
            }
        }

        public static class ForJava9CapableVm implements AttachmentTypeEvaluator {
            private final Method current;
            private final Method pid;

            protected ForJava9CapableVm(Method method, Method method2) {
                this.current = method;
                this.pid = method2;
            }

            @Override // net.bytebuddy.agent.ByteBuddyAgent.AttachmentTypeEvaluator
            public boolean requiresExternalAttachment(String str) {
                try {
                    return this.pid.invoke(this.current.invoke(null, new Object[0]), new Object[0]).toString().equals(str);
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException("Cannot access Java 9 process API", e);
                } catch (InvocationTargetException e2) {
                    throw new IllegalStateException("Error when accessing Java 9 process API", e2.getTargetException());
                }
            }
        }
    }
}
