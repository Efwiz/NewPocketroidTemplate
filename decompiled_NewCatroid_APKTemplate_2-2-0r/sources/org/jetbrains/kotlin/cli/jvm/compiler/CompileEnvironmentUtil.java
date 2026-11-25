package org.jetbrains.kotlin.cli.jvm.compiler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.GregorianCalendar;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import kotlin.io.FilesKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.BuildConfig;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.backend.common.output.OutputFileCollection;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleChunk;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtil;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;
import org.jetbrains.kotlin.utils.ExceptionUtilsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: loaded from: classes3.dex */
public class CompileEnvironmentUtil {
    public static long DOS_EPOCH = new GregorianCalendar(1980, 0, 1, 0, 0, 0).getTimeInMillis();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/cli/jvm/compiler/CompileEnvironmentUtil", "loadModuleChunk"));
    }

    public static ModuleChunk loadModuleChunk(File file, MessageCollector messageCollector) {
        if (!file.exists()) {
            messageCollector.report(CompilerMessageSeverity.ERROR, "Module definition file does not exist: " + file, null);
            ModuleChunk moduleChunk = ModuleChunk.EMPTY;
            if (moduleChunk == null) {
                $$$reportNull$$$0(0);
            }
            return moduleChunk;
        }
        if ("xml".equalsIgnoreCase(FilesKt.getExtension(file))) {
            ModuleChunk moduleScript = ModuleXmlParser.parseModuleScript(file.getPath(), messageCollector);
            if (moduleScript == null) {
                $$$reportNull$$$0(1);
            }
            return moduleScript;
        }
        messageCollector.report(CompilerMessageSeverity.ERROR, "Unknown module definition type: " + file, null);
        ModuleChunk moduleChunk2 = ModuleChunk.EMPTY;
        if (moduleChunk2 == null) {
            $$$reportNull$$$0(2);
        }
        return moduleChunk2;
    }

    private static void doWriteToJar(OutputFileCollection outputFileCollection, OutputStream outputStream, FqName fqName, boolean z, boolean z2, boolean z3) {
        try {
            Manifest manifest = new Manifest();
            Attributes mainAttributes = manifest.getMainAttributes();
            mainAttributes.putValue("Manifest-Version", BuildConfig.VERSION_NAME);
            mainAttributes.putValue("Created-By", "JetBrains Kotlin");
            if (fqName != null) {
                mainAttributes.putValue("Main-Class", fqName.asString());
            }
            JarOutputStream jarOutputStream = new JarOutputStream(outputStream);
            JarEntry jarEntry = new JarEntry("META-INF/MANIFEST.MF");
            if (z3) {
                jarEntry.setTime(DOS_EPOCH);
            }
            jarOutputStream.putNextEntry(jarEntry);
            manifest.write(new BufferedOutputStream(jarOutputStream));
            for (OutputFile outputFile : outputFileCollection.asList()) {
                JarEntry jarEntry2 = new JarEntry(outputFile.getRelativePath());
                if (z3) {
                    jarEntry2.setTime(DOS_EPOCH);
                }
                jarOutputStream.putNextEntry(jarEntry2);
                jarOutputStream.write(outputFile.getContent());
            }
            if (z) {
                writeRuntimeToJar(jarOutputStream, z3);
                if (!z2) {
                    writeReflectToJar(jarOutputStream, z3);
                }
            }
            jarOutputStream.finish();
        } catch (IOException e) {
            throw new CompileEnvironmentException("Failed to generate jar file", e);
        }
    }

    public static void writeToJar(File file, boolean z, boolean z2, boolean z3, FqName fqName, OutputFileCollection outputFileCollection, MessageCollector messageCollector) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                try {
                    if (file.getParentFile() != null) {
                        file.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(file);
                    try {
                        doWriteToJar(outputFileCollection, fileOutputStream, fqName, z, z2, z3);
                        fileOutputStream.close();
                    } catch (FileNotFoundException unused) {
                        messageCollector.report(CompilerMessageSeverity.ERROR, "Invalid jar path: " + file, null);
                    } catch (IOException e) {
                        e = e;
                        throw ExceptionUtilsKt.rethrow(e);
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    ExceptionUtilsKt.closeQuietly(fileOutputStream2);
                    throw th;
                }
            } catch (FileNotFoundException unused2) {
                fileOutputStream = null;
            } catch (IOException e2) {
                e = e2;
            }
            ExceptionUtilsKt.closeQuietly(fileOutputStream);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void writeRuntimeToJar(JarOutputStream jarOutputStream, boolean z) throws IOException {
        File stdlibPath = PathUtil.getKotlinPathsForCompiler().getStdlibPath();
        if (!stdlibPath.exists()) {
            throw new CompileEnvironmentException("Couldn't find kotlin-stdlib at " + stdlibPath);
        }
        copyJarImpl(jarOutputStream, stdlibPath, z);
    }

    private static void writeReflectToJar(JarOutputStream jarOutputStream, boolean z) throws IOException {
        File reflectPath = PathUtil.getKotlinPathsForCompiler().getReflectPath();
        if (!reflectPath.exists()) {
            throw new CompileEnvironmentException("Couldn't find kotlin-reflect at " + reflectPath);
        }
        copyJarImpl(jarOutputStream, reflectPath, z);
    }

    private static void copyJarImpl(JarOutputStream jarOutputStream, File file, boolean z) throws IOException {
        JarInputStream jarInputStream = new JarInputStream(new FileInputStream(file));
        while (true) {
            try {
                JarEntry nextJarEntry = jarInputStream.getNextJarEntry();
                if (nextJarEntry != null) {
                    String name2 = nextJarEntry.getName();
                    if (FileUtilRt.extensionEquals(name2, "class") || FileUtilRt.extensionEquals(name2, BuiltInSerializerProtocol.BUILTINS_FILE_EXTENSION) || name2.startsWith("META-INF/services/")) {
                        if (!StringsKt.substringAfterLast(name2, "/", name2).equals("module-info.class")) {
                            if (z) {
                                nextJarEntry.setTime(DOS_EPOCH);
                            }
                            jarOutputStream.putNextEntry(nextJarEntry);
                            FileUtil.copy(jarInputStream, jarOutputStream);
                        }
                    }
                } else {
                    jarInputStream.close();
                    return;
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        jarInputStream.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        }
    }
}
