package org.catrobat.catroid.virtualmachine;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.NativeLibraryManager;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/virtualmachine/VirtualMachineManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: VirtualMachineManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\b\r\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nH\u0002J\u001e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nJ\u001e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\nJ.\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nJ,\u0010 \u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\n2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\n0\"2\u0006\u0010#\u001a\u00020\nH\u0083 ¢\u0006\u0002\u0010$J\u0019\u0010%\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010&\u001a\u00020\nH\u0083 J\u0011\u0010'\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\nH\u0083 J\u0018\u0010(\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010)\u001a\u00020\nH\u0007J\u0012\u0010*\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0016\u0010+\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010&\u001a\u00020\nJ\u0018\u0010,\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\n2\b\u0010-\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010.\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\nR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n\u0012\b\u0012\u00060\u000bj\u0002`\f0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000e0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/catrobat/catroid/virtualmachine/VirtualMachineManager;", "", "()V", "isWorking", "", "()Z", "setWorking", "(Z)V", "outputBuffers", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "outputVariables", "Lorg/catrobat/catroid/formulaeditor/UserVariable;", "runningVMs", "", "", "copyAssetFolder", "", "context", "Landroid/content/Context;", "srcAssetPath", "dstPath", "createDiskIfNotExists", "baseDir", "diskPath", "diskSize", "createVM", "vmName", "args", "diskName", "nativeCreateAndRunVM", "command", "", "libraryPath", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)I", "nativeSendInputToVM", "input", "nativeStopVM", "onVmOutput", "output", "prepareQemuEnvironment", "sendInputToVM", "setVmOutputVariable", "variable", "stopVM", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class VirtualMachineManager {
    public static final VirtualMachineManager INSTANCE = new VirtualMachineManager();
    private static boolean isWorking = true;
    private static final ConcurrentHashMap<String, UserVariable> outputVariables = new ConcurrentHashMap<>();
    private static final Map<String, Integer> runningVMs = new LinkedHashMap();
    private static final ConcurrentHashMap<String, StringBuilder> outputBuffers = new ConcurrentHashMap<>();

    @JvmStatic
    private static final native int nativeCreateAndRunVM(String vmName, String[] command, String libraryPath);

    @JvmStatic
    private static final native void nativeSendInputToVM(String vmName, String input);

    @JvmStatic
    private static final native int nativeStopVM(String vmName);

    private VirtualMachineManager() {
    }

    public final boolean isWorking() {
        return isWorking;
    }

    public final void setWorking(boolean z) {
        isWorking = z;
    }

    private final void copyAssetFolder(Context context, String srcAssetPath, String dstPath) throws IOException {
        AssetManager assets = context.getAssets();
        String[] list = assets.list(srcAssetPath);
        if (list == null) {
            return;
        }
        if (list.length == 0) {
            InputStream inputStreamOpen = assets.open(srcAssetPath);
            try {
                InputStream inputStream = inputStreamOpen;
                FileOutputStream fileOutputStream = new FileOutputStream(new File(dstPath));
                try {
                    Intrinsics.checkNotNull(inputStream);
                    ByteStreamsKt.copyTo$default(inputStream, fileOutputStream, 0, 2, null);
                    CloseableKt.closeFinally(fileOutputStream, null);
                    CloseableKt.closeFinally(inputStreamOpen, null);
                    return;
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        CloseableKt.closeFinally(fileOutputStream, th);
                        throw th2;
                    }
                }
            } catch (Throwable th3) {
                try {
                    throw th3;
                } catch (Throwable th4) {
                    CloseableKt.closeFinally(inputStreamOpen, th3);
                    throw th4;
                }
            }
        }
        File file = new File(dstPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        for (String str : list) {
            INSTANCE.copyAssetFolder(context, srcAssetPath + "/" + str, dstPath + "/" + str);
        }
    }

    public final void sendInputToVM(String vmName, String input) {
        Intrinsics.checkNotNullParameter(vmName, "vmName");
        Intrinsics.checkNotNullParameter(input, "input");
        if (isWorking && NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.CORE)) {
            nativeSendInputToVM(vmName, input);
        }
    }

    private final String prepareQemuEnvironment(Context context) {
        if (!isWorking) {
            return null;
        }
        File file = new File(context.getFilesDir(), "qemu_x86_64");
        File file2 = new File(file, "qemu-system-x86_64");
        File file3 = new File(file, "qemu-img");
        try {
            if (!file2.exists() || !file3.exists()) {
                Log.i("VMManager", "QEMU environment not found. Extracting from assets...");
                if (file.exists()) {
                    FilesKt.deleteRecursively(file);
                }
                String absolutePath = file.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue(absolutePath, "getAbsolutePath(...)");
                copyAssetFolder(context, "qemu_x86_64", absolutePath);
                Log.i("VMManager", "Extraction complete.");
            }
            if (!file2.canExecute()) {
                file2.setExecutable(true, true);
            }
            if (!file3.canExecute()) {
                file3.setExecutable(true, true);
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            Log.e("VMManager", "Failed to prepare QEMU environment", e);
            isWorking = false;
            return null;
        }
    }

    public final boolean createDiskIfNotExists(String baseDir, String diskPath, String diskSize) {
        Intrinsics.checkNotNullParameter(baseDir, "baseDir");
        Intrinsics.checkNotNullParameter(diskPath, "diskPath");
        Intrinsics.checkNotNullParameter(diskSize, "diskSize");
        if (!isWorking) {
            return false;
        }
        if (!NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.CORE)) {
            Log.e("VMManager", "Cannot create VM, core library is missing.");
            isWorking = false;
            return false;
        }
        File file = new File(diskPath);
        if (file.exists()) {
            Log.i("VMManager", "Disk already exists: " + diskPath);
            return true;
        }
        Log.i("VMManager", "Disk not found. Creating new disk: " + diskPath + " with size " + diskSize);
        String absolutePath = new File(baseDir, "qemu-img").getAbsolutePath();
        String absolutePath2 = new File(baseDir, "lib").getAbsolutePath();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder((List<String>) CollectionsKt.listOf((Object[]) new String[]{"/system/bin/linker64", absolutePath, CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "-f", "qcow2", file.getAbsolutePath(), diskSize}));
            Map<String, String> mapEnvironment = processBuilder.environment();
            Intrinsics.checkNotNullExpressionValue(mapEnvironment, "environment(...)");
            mapEnvironment.put("LD_LIBRARY_PATH", absolutePath2);
            processBuilder.redirectErrorStream(true);
            Process processStart = processBuilder.start();
            InputStream inputStream = processStart.getInputStream();
            Intrinsics.checkNotNullExpressionValue(inputStream, "getInputStream(...)");
            Reader inputStreamReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            BufferedReader bufferedReader2 = bufferedReader instanceof BufferedReader ? bufferedReader : new BufferedReader(bufferedReader, 8192);
            try {
                Iterator<String> it = TextStreamsKt.lineSequence(bufferedReader2).iterator();
                while (it.hasNext()) {
                    Log.i("QEMU-IMG", it.next());
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(bufferedReader2, null);
                int iWaitFor = processStart.waitFor();
                Log.i("VMManager", "qemu-img process finished with exit code: " + iWaitFor);
                return iWaitFor == 0;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    CloseableKt.closeFinally(bufferedReader2, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            Log.e("VMManager", "Failed to create disk", e);
            return false;
        }
    }

    public final void createVM(Context context, String vmName, String args, String diskName, String diskSize) {
        String strReplace$default;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(vmName, "vmName");
        Intrinsics.checkNotNullParameter(args, "args");
        Intrinsics.checkNotNullParameter(diskName, "diskName");
        Intrinsics.checkNotNullParameter(diskSize, "diskSize");
        if (isWorking) {
            if (!NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.CORE)) {
                Log.e("VMManager", "Cannot create VM, core library is missing.");
                isWorking = false;
                return;
            }
            if (runningVMs.containsKey(vmName)) {
                Log.w("VMManager", "VM with name '" + vmName + "' is already running.");
                return;
            }
            String strPrepareQemuEnvironment = prepareQemuEnvironment(context);
            if (strPrepareQemuEnvironment == null) {
                Log.e("VMManager", "Cannot start VM, QEMU environment is not available.");
                return;
            }
            if (!outputVariables.containsKey(vmName) || StringsKt.contains$default((CharSequence) args, (CharSequence) "-append", false, 2, (Object) null)) {
                strReplace$default = args;
            } else {
                strReplace$default = args + " -append \"console=ttyS0\"";
                Log.i("VMManager", "Serial console enabled by default for VM '" + vmName + "'.");
            }
            if (diskName.length() > 0) {
                File filesDir = ProjectManager.getInstance().getCurrentProject().getFilesDir();
                if (!filesDir.exists()) {
                    filesDir.mkdirs();
                }
                String absolutePath = new File(filesDir, diskName).getAbsolutePath();
                Intrinsics.checkNotNull(absolutePath);
                if (!createDiskIfNotExists(strPrepareQemuEnvironment, absolutePath, diskSize)) {
                    Log.e("VMManager", "Failed to create or find disk. Aborting VM start.");
                    return;
                }
                strReplace$default = StringsKt.replace$default(args, "%DISK_PATH%", absolutePath, false, 4, (Object) null);
            }
            String absolutePath2 = new File(strPrepareQemuEnvironment, "qemu-system-x86_64").getAbsolutePath();
            String absolutePath3 = ProjectManager.getInstance().getCurrentProject().getFilesDir().getAbsolutePath();
            List<String> listSplit = new Regex(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)").split(strReplace$default, 0);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit, 10));
            Iterator<T> it = listSplit.iterator();
            while (it.hasNext()) {
                String strRemoveSurrounding = StringsKt.removeSurrounding((String) it.next(), (CharSequence) "\"");
                Intrinsics.checkNotNull(absolutePath3);
                arrayList.add(StringsKt.replace$default(strRemoveSurrounding, "%PROJECT_FILES%", absolutePath3, false, 4, (Object) null));
            }
            List listMutableListOf = CollectionsKt.mutableListOf(absolutePath2);
            listMutableListOf.addAll(arrayList);
            Log.i("VMManager", "Executing command: " + CollectionsKt.joinToString$default(listMutableListOf, " ", null, null, 0, null, null, 62, null));
            int iNativeCreateAndRunVM = nativeCreateAndRunVM(vmName, (String[]) listMutableListOf.toArray(new String[0]), strPrepareQemuEnvironment);
            if (iNativeCreateAndRunVM != -1) {
                runningVMs.put(vmName, Integer.valueOf(iNativeCreateAndRunVM));
            }
        }
    }

    public final void createVM(Context context, String vmName, String args) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(vmName, "vmName");
        Intrinsics.checkNotNullParameter(args, "args");
        createVM(context, vmName, args, "", "");
    }

    public final void stopVM(String vmName) {
        Intrinsics.checkNotNullParameter(vmName, "vmName");
        if (isWorking) {
            if (!NativeLibraryManager.INSTANCE.isLoaded(NativeLibraryManager.Feature.CORE)) {
                Log.e("VMManager", "Cannot create VM, core library is missing.");
                isWorking = false;
                return;
            }
            Map<String, Integer> map = runningVMs;
            if (!map.containsKey(vmName)) {
                Log.w("VMManager", "No running VM found with name '" + vmName + "'.");
            } else {
                nativeStopVM(vmName);
                map.remove(vmName);
            }
        }
    }

    public final void setVmOutputVariable(String vmName, UserVariable variable) {
        Intrinsics.checkNotNullParameter(vmName, "vmName");
        if (variable == null) {
            outputVariables.remove(vmName);
        } else {
            variable.setValue("");
            outputVariables.put(vmName, variable);
        }
    }

    @JvmStatic
    public static final void onVmOutput(String vmName, String output) {
        Intrinsics.checkNotNullParameter(vmName, "vmName");
        Intrinsics.checkNotNullParameter(output, "output");
        UserVariable userVariable = outputVariables.get(vmName);
        if (userVariable == null) {
            return;
        }
        Object value = userVariable.getValue();
        String str = value instanceof String ? (String) value : null;
        if (str == null) {
            str = "";
        }
        String strJoinToString$default = str + output;
        List<String> listLines = StringsKt.lines(strJoinToString$default);
        if (listLines.size() > 200) {
            strJoinToString$default = CollectionsKt.joinToString$default(CollectionsKt.takeLast(listLines, 200), "\n", null, null, 0, null, null, 62, null);
        }
        userVariable.setValue(strJoinToString$default);
    }
}
