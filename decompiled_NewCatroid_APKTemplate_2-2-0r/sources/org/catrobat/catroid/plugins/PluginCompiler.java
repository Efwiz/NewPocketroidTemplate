package org.catrobat.catroid.plugins;

import android.content.Context;
import android.util.Log;
import com.android.dex.DexFormat;
import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.D8;
import com.android.tools.r8.D8Command;
import com.android.tools.r8.OutputMode;
import java.io.File;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.catrobat.catroid.ide.AndroidECJ;
import org.catrobat.catroid.ide.IdeSettings;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/plugins/PluginCompiler.class
  classes14.dex
 */
/* JADX INFO: compiled from: PluginCompiler.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\fH\u0002J&\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u00112\u0006\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/plugins/PluginCompiler;", "", "()V", "TAG", "", "compile", "", "context", "Landroid/content/Context;", "pluginInfo", "Lorg/catrobat/catroid/plugins/PluginInfo;", "prepareApiStubs", "Ljava/io/File;", "androidJar", "runD8Compiler", "", "inputFiles", "", "bootJar", "outputFolder", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PluginCompiler {
    public static final PluginCompiler INSTANCE = new PluginCompiler();
    private static final String TAG = "PluginCompiler";

    private PluginCompiler() {
    }

    public final boolean compile(Context context, PluginInfo pluginInfo) {
        PluginInfo pluginInfo2 = pluginInfo;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(pluginInfo2, "pluginInfo");
        File file = pluginInfo2.pluginDirectory;
        File file2 = new File(file, "src");
        File file3 = new File(file, "plugin.dex");
        if (!file2.exists()) {
            Log.d(TAG, "No 'src' directory found for plugin: " + pluginInfo2.f589name + ". Skipping compilation.");
            return file3.exists();
        }
        Log.d(TAG, "Starting on-device compilation for: " + pluginInfo2.f589name);
        File codeCacheDir = context.getCodeCacheDir();
        File file4 = new File(codeCacheDir, "classes_" + pluginInfo2.packageName);
        FilesKt.deleteRecursively(file4);
        file4.mkdirs();
        File file5 = new File(codeCacheDir, "dex_" + pluginInfo2.packageName);
        FilesKt.deleteRecursively(file5);
        file5.mkdirs();
        try {
            try {
                File androidJar = IdeSettings.INSTANCE.getAndroidJar(context, 34);
                if (!androidJar.exists()) {
                    Log.e(TAG, "android.jar not found. Compilation aborted.");
                    return false;
                }
                File filePrepareApiStubs = prepareApiStubs(context, androidJar);
                if (filePrepareApiStubs == null || !filePrepareApiStubs.exists()) {
                    Log.e(TAG, "Failed to compile API stubs.");
                } else {
                    File file6 = new File(context.getFilesDir(), "app-classes.jar");
                    File file7 = new File(context.getFilesDir(), "gdx.jar");
                    File file8 = new File(context.getFilesDir(), "xstream.jar");
                    File file9 = new File(context.getFilesDir(), "koin-core.jar");
                    File file10 = new File(context.getFilesDir(), "fragment.jar");
                    try {
                        File file11 = new File(context.getFilesDir(), "activity.jar");
                        File file12 = new File(context.getFilesDir(), "core.jar");
                        List<? extends File> listMutableListOf = CollectionsKt.mutableListOf(androidJar, filePrepareApiStubs);
                        if (file6.exists()) {
                            listMutableListOf.add(file6);
                        }
                        if (file7.exists()) {
                            listMutableListOf.add(file7);
                        }
                        if (file8.exists()) {
                            listMutableListOf.add(file8);
                        }
                        if (file9.exists()) {
                            listMutableListOf.add(file9);
                        }
                        if (file10.exists()) {
                            listMutableListOf.add(file10);
                        }
                        if (file11.exists()) {
                            listMutableListOf.add(file11);
                        }
                        if (file12.exists()) {
                            listMutableListOf.add(file12);
                        }
                        File file13 = new File(context.getFilesDir(), "core-lambda-stubs.jar");
                        Log.d(TAG, "Running ECJ compiler...");
                        Map<String, byte[]> mapCompileDirectory = AndroidECJ.INSTANCE.compileDirectory(file2, listMutableListOf, androidJar, file13);
                        if (mapCompileDirectory.isEmpty()) {
                            Log.e(TAG, "ECJ returned no compiled classes.");
                        } else {
                            for (Map.Entry<String, byte[]> entry : mapCompileDirectory.entrySet()) {
                                String key = entry.getKey();
                                byte[] value = entry.getValue();
                                File file14 = new File(file4, key);
                                File parentFile = file14.getParentFile();
                                if (parentFile != null) {
                                    parentFile.mkdirs();
                                }
                                FilesKt.writeBytes(file14, value);
                            }
                            Log.d(TAG, "Running D8 tool...");
                            runD8Compiler(SequencesKt.toList(SequencesKt.filter(FilesKt.walkTopDown(file4), new Function1<File, Boolean>() { // from class: org.catrobat.catroid.plugins.PluginCompiler$compile$classFiles$1
                                @Override // kotlin.jvm.functions.Function1
                                public final Boolean invoke(File it) {
                                    Intrinsics.checkNotNullParameter(it, "it");
                                    return Boolean.valueOf(it.isFile() && Intrinsics.areEqual(FilesKt.getExtension(it), "class"));
                                }
                            })), androidJar, file5);
                            File file15 = new File(file5, DexFormat.DEX_IN_JAR_NAME);
                            if (file15.exists()) {
                                FilesKt.copyTo$default(file15, file3, true, 0, 4, null);
                                Log.d(TAG, "Successfully compiled plugin.dex for " + pluginInfo.f589name);
                                return true;
                            }
                            Log.e(TAG, "classes.dex was not found.");
                        }
                    } catch (Exception e) {
                        e = e;
                        pluginInfo2 = pluginInfo;
                        Log.e(TAG, "On-device compilation failed for " + pluginInfo2.f589name, e);
                    }
                }
                return false;
            } catch (Exception e2) {
                e = e2;
            }
        } finally {
            FilesKt.deleteRecursively(file4);
            FilesKt.deleteRecursively(file5);
        }
    }

    private final File prepareApiStubs(Context context, File androidJar) {
        File codeCacheDir = context.getCodeCacheDir();
        File file = new File(codeCacheDir, "api_stub_src");
        FilesKt.deleteRecursively(file);
        file.mkdirs();
        File file2 = new File(codeCacheDir, "api_stub_classes");
        FilesKt.deleteRecursively(file2);
        file2.mkdirs();
        try {
            File file3 = new File(file, "org/catrobat/catroid/plugins");
            file3.mkdirs();
            FilesKt.writeText$default(new File(file3, "PluginEntry.java"), "package org.catrobat.catroid.plugins;\nimport android.content.Context;\npublic interface PluginEntry {\n    void onLoad(Context context);\n}", null, 2, null);
            Map<String, byte[]> mapCompileDirectory = AndroidECJ.INSTANCE.compileDirectory(file, CollectionsKt.listOf(androidJar), androidJar, new File(context.getFilesDir(), "core-lambda-stubs.jar"));
            if (mapCompileDirectory.isEmpty()) {
                return null;
            }
            for (Map.Entry<String, byte[]> entry : mapCompileDirectory.entrySet()) {
                String key = entry.getKey();
                byte[] value = entry.getValue();
                File file4 = new File(file2, key);
                File parentFile = file4.getParentFile();
                if (parentFile != null) {
                    parentFile.mkdirs();
                }
                FilesKt.writeBytes(file4, value);
            }
            return file2;
        } catch (Exception e) {
            Log.e(TAG, "Failed to prepare API stubs", e);
            return null;
        } finally {
            FilesKt.deleteRecursively(file);
        }
        FilesKt.deleteRecursively(file);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void runD8Compiler(List<? extends File> inputFiles, File bootJar, File outputFolder) throws CompilationFailedException {
        D8Command.Builder builder = (D8Command.Builder) D8Command.builder().setMode(CompilationMode.DEBUG).setMinApiLevel(21).setIntermediate(false).setOutput(outputFolder.toPath(), OutputMode.DexIndexed).addLibraryFiles(bootJar.toPath());
        for (File file : inputFiles) {
            if (file.exists()) {
                builder.addProgramFiles(file.toPath());
            }
        }
        D8.run((D8Command) builder.build());
    }
}
