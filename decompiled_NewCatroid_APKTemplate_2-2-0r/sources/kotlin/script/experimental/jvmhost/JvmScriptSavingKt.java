package kotlin.script.experimental.jvmhost;

import androidx.core.app.NotificationCompat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.CompiledScript;
import kotlin.script.experimental.api.ErrorHandlingKt;
import kotlin.script.experimental.api.ResultWithDiagnostics;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.jvm.JvmDependency;
import kotlin.script.experimental.jvm.impl.KJvmCompiledModule;
import kotlin.script.experimental.jvm.impl.KJvmCompiledModuleInMemory;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScript;
import kotlin.script.experimental.jvm.impl.KJvmCompiledScriptKt;
import kotlin.script.experimental.jvm.util.JvmClasspathUtilKt;
import kotlin.text.StringsKt;
import org.catrobat.catroid.BuildConfig;
import org.jetbrains.kotlin.utils.KotlinPaths;

/* JADX INFO: compiled from: jvmScriptSaving.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b\u001a\u0012\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006¨\u0006\r"}, d2 = {"failure", "Lkotlin/script/experimental/api/ResultWithDiagnostics$Failure;", NotificationCompat.CATEGORY_MESSAGE, "", "loadScriptFromJar", "Lkotlin/script/experimental/api/CompiledScript;", "Ljava/io/File;", "checkMissingDependencies", "", "saveToJar", "", "Lkotlin/script/experimental/jvm/impl/KJvmCompiledScript;", "outputJar", "kotlin-scripting-jvm-host-unshaded"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmScriptSavingKt {
    public static final void saveToJar(KJvmCompiledScript kJvmCompiledScript, File outputJar) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(kJvmCompiledScript, "<this>");
        Intrinsics.checkNotNullParameter(outputJar, "outputJar");
        KJvmCompiledModule compiledModule = kJvmCompiledScript.getCompiledModule();
        KJvmCompiledModuleInMemory kJvmCompiledModuleInMemory = compiledModule instanceof KJvmCompiledModuleInMemory ? (KJvmCompiledModuleInMemory) compiledModule : null;
        if (kJvmCompiledModuleInMemory == null) {
            throw new IllegalArgumentException("Unsupported module type " + kJvmCompiledScript.getCompiledModule());
        }
        List list = (List) kJvmCompiledScript.getCompilationConfiguration().get(ScriptCompilationKt.getDependencies(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (obj instanceof JvmDependency) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList2, ((JvmDependency) it.next()).getClasspath());
            }
            arrayListEmptyList = arrayList2;
        } else {
            arrayListEmptyList = null;
        }
        if (arrayListEmptyList == null) {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        String[] strArr = {KotlinPaths.Jar.ScriptingLib.getBaseName(), KotlinPaths.Jar.ScriptingJvmLib.getBaseName()};
        ClassLoader classLoader = kJvmCompiledScript.getClass().getClassLoader();
        Intrinsics.checkNotNullExpressionValue(classLoader, "getClassLoader(...)");
        List listScriptCompilationClasspathFromContextOrNull$default = JvmClasspathUtilKt.scriptCompilationClasspathFromContextOrNull$default(strArr, classLoader, false, false, 8, null);
        if (listScriptCompilationClasspathFromContextOrNull$default == null) {
            listScriptCompilationClasspathFromContextOrNull$default = CollectionsKt.emptyList();
        }
        List listDistinct = CollectionsKt.distinct(CollectionsKt.plus((Collection) arrayListEmptyList, (Iterable) listScriptCompilationClasspathFromContextOrNull$default));
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : listDistinct) {
            if (((File) obj2).exists()) {
                arrayList3.add(obj2);
            }
        }
        ArrayList arrayList4 = arrayList3;
        FileOutputStream fileOutputStream = new FileOutputStream(outputJar);
        try {
            FileOutputStream fileOutputStream2 = fileOutputStream;
            Manifest manifest = new Manifest();
            Attributes mainAttributes = manifest.getMainAttributes();
            mainAttributes.putValue("Manifest-Version", BuildConfig.VERSION_NAME);
            mainAttributes.putValue("Created-By", "JetBrains Kotlin");
            if (!arrayList4.isEmpty()) {
                mainAttributes.putValue("Class-Path", CollectionsKt.joinToString$default(arrayList4, " ", null, null, 0, null, new Function1<File, CharSequence>() { // from class: kotlin.script.experimental.jvmhost.JvmScriptSavingKt$saveToJar$1$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(File it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        String externalForm = it2.toURI().toURL().toExternalForm();
                        Intrinsics.checkNotNullExpressionValue(externalForm, "toExternalForm(...)");
                        return externalForm;
                    }
                }, 30, null));
            }
            mainAttributes.putValue("Main-Class", kJvmCompiledScript.getScriptClassFQName());
            JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream2, manifest);
            try {
                JarOutputStream jarOutputStream2 = jarOutputStream;
                jarOutputStream2.putNextEntry(new JarEntry(KJvmCompiledScriptKt.scriptMetadataPath(kJvmCompiledScript.getScriptClassFQName())));
                jarOutputStream2.write(KJvmCompiledScriptKt.toBytes(KJvmCompiledScriptKt.copyWithoutModule(kJvmCompiledScript)));
                jarOutputStream2.closeEntry();
                for (Map.Entry<String, byte[]> entry : kJvmCompiledModuleInMemory.getCompilerOutputFiles().entrySet()) {
                    String key = entry.getKey();
                    byte[] value = entry.getValue();
                    jarOutputStream2.putNextEntry(new JarEntry(key));
                    jarOutputStream2.write(value);
                    jarOutputStream2.closeEntry();
                }
                jarOutputStream2.finish();
                jarOutputStream2.flush();
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(jarOutputStream, null);
                fileOutputStream2.flush();
                Unit unit2 = Unit.INSTANCE;
                CloseableKt.closeFinally(fileOutputStream, null);
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(jarOutputStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(fileOutputStream, th3);
                throw th4;
            }
        }
    }

    public static /* synthetic */ CompiledScript loadScriptFromJar$default(File file, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return loadScriptFromJar(file, z);
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0051 A[Catch: all -> 0x00cc, TryCatch #2 {all -> 0x00cc, blocks: (B:4:0x0018, B:6:0x003b, B:9:0x0055, B:8:0x0051), top: B:50:0x0018, outer: #0 }] */
    public static final CompiledScript loadScriptFromJar(File file, boolean z) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(file, "<this>");
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            JarInputStream jarInputStream = new JarInputStream(fileInputStream);
            try {
                JarInputStream jarInputStream2 = jarInputStream;
                String value = jarInputStream2.getManifest().getMainAttributes().getValue("Main-Class");
                String value2 = jarInputStream2.getManifest().getMainAttributes().getValue("Class-Path");
                if (value2 != null) {
                    Intrinsics.checkNotNull(value2);
                    listEmptyList = StringsKt.split$default((CharSequence) value2, new String[]{" "}, false, 0, 6, (Object) null);
                    if (listEmptyList == null) {
                        listEmptyList = CollectionsKt.emptyList();
                    }
                } else {
                    listEmptyList = CollectionsKt.emptyList();
                }
                Pair pair = TuplesKt.to(value, listEmptyList);
                CloseableKt.closeFinally(jarInputStream, null);
                CloseableKt.closeFinally(fileInputStream, null);
                String str = (String) pair.component1();
                List<String> list = (List) pair.component2();
                if (str == null) {
                    return null;
                }
                List listMutableListOf = CollectionsKt.mutableListOf(file);
                for (String str2 : list) {
                    File file2 = new File(new URI(str2));
                    if (!file2.exists()) {
                        file2 = null;
                    }
                    if (file2 == null) {
                        file2 = new File(str2);
                        if (!file2.exists()) {
                            file2 = null;
                        }
                    }
                    if (file2 != null) {
                        listMutableListOf.add(file2);
                    }
                }
                List list2 = listMutableListOf;
                if (!z || list.size() + 1 == list2.size()) {
                    return new KJvmCompiledScriptLazilyLoadedFromClasspath(str, list2);
                }
                return null;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(jarInputStream, th);
                    throw th2;
                }
            }
        } catch (Throwable th3) {
            try {
                throw th3;
            } catch (Throwable th4) {
                CloseableKt.closeFinally(fileInputStream, th3);
                throw th4;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ResultWithDiagnostics.Failure failure(String str) {
        return new ResultWithDiagnostics.Failure(ErrorHandlingKt.asErrorDiagnostics$default(str, 0, null, null, 7, null));
    }
}
