package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.IrJsUtilsKt;
import org.jetbrains.kotlin.ir.backend.js.utils.NameTablesKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;

/* JADX INFO: compiled from: ModuleFragmentToExternalName.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\tJ\u000e\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u0015\u001a\u00020\u0004J\u001e\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u0005J\u000e\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\tJ\u0018\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0005H\u0002J\u000e\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\tJ\u000e\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\tJ\f\u0010\u001a\u001a\u00020\u0005*\u00020\u0004H\u0002R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t0\bj\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000b\u001a\u00020\u0005*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u0005*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\r¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/ModuleFragmentToExternalName;", "", "jsOutputNamesMapping", "", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "", "(Ljava/util/Map;)V", "externalNameToItsFile", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lkotlin/collections/HashMap;", "outputName", "getOutputName", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;)Ljava/lang/String;", "stableFileName", "getStableFileName", "getExternalNameFor", PsiTreeChangeEvent.PROP_FILE_NAME, "packageFqn", "moduleName", "file", "module", "getExternalNameForExporterFile", "getFileStableName", "getSafeNameExporterFor", "getSafeNameFor", "getJsOutputName", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleFragmentToExternalName {
    private final HashMap<String, IrFile> externalNameToItsFile;
    private final Map<IrModuleFragment, String> jsOutputNamesMapping;

    public ModuleFragmentToExternalName(Map<IrModuleFragment, String> jsOutputNamesMapping) {
        Intrinsics.checkNotNullParameter(jsOutputNamesMapping, "jsOutputNamesMapping");
        this.jsOutputNamesMapping = jsOutputNamesMapping;
        this.externalNameToItsFile = new HashMap<>();
    }

    public final String getExternalNameFor(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String outputName = getOutputName(file);
        String strAsString = file.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String externalNameFor = getExternalNameFor(outputName, strAsString, getJsOutputName(file.getModule()));
        HashMap<String, IrFile> map = this.externalNameToItsFile;
        String lowerCase = externalNameFor.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        IrFile irFilePutIfAbsent = map.putIfAbsent(lowerCase, file);
        if (irFilePutIfAbsent == null || Intrinsics.areEqual(irFilePutIfAbsent, file)) {
            return externalNameFor;
        }
        throw new IllegalStateException(StringsKt.trimMargin$default("\n                      |There are two files in module '" + file.getModule().getName() + "' that have the similar package and file names.\n                      |  - Package \"" + file.getPackageFqName().asString() + "\" and path \"" + IrDeclarationsKt.getPath(file) + "\"\n                      |  - Package \"" + irFilePutIfAbsent.getPackageFqName().asString() + "\" and path \"" + IrDeclarationsKt.getPath(irFilePutIfAbsent) + "\"\n                      |Note, that if the difference is only in letter cases, it also could lead to a clash of the compiled artifacts\n                   ", null, 1, null).toString());
    }

    public final String getExternalNameFor(String fileName, String packageFqn, String moduleName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(packageFqn, "packageFqn");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        return moduleName + '/' + getFileStableName(fileName, packageFqn);
    }

    public final String getExternalNameForExporterFile(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        String outputName = getOutputName(file);
        String strAsString = file.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return getExternalNameForExporterFile(outputName, strAsString, getJsOutputName(file.getModule()));
    }

    public final String getExternalNameForExporterFile(String fileName, String packageFqn, String moduleName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        Intrinsics.checkNotNullParameter(packageFqn, "packageFqn");
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        return getExternalNameFor(fileName, packageFqn, moduleName) + ".export";
    }

    public final String getSafeNameFor(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return IrModuleToJsTransformerKt.getSafeName(file.getModule()) + '/' + getStableFileName(file);
    }

    public final String getSafeNameExporterFor(IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return getSafeNameFor(file) + ".export";
    }

    public final String getExternalNameFor(IrModuleFragment module) {
        Intrinsics.checkNotNullParameter(module, "module");
        return getJsOutputName(module);
    }

    private final String getJsOutputName(IrModuleFragment irModuleFragment) {
        String str = this.jsOutputNamesMapping.get(irModuleFragment);
        return str == null ? NameTablesKt.sanitizeName$default(IrModuleToJsTransformerKt.getSafeName(irModuleFragment), false, 2, null) : str;
    }

    private final String getFileStableName(String fileName, String packageFqn) {
        String strReplace$default = StringsKt.replace$default(packageFqn, '.', '/', false, 4, (Object) null);
        return strReplace$default + (strReplace$default.length() > 0 ? "/" : "") + fileName;
    }

    private final String getOutputName(IrFile irFile) {
        String jsFileName = AnnotationUtilsKt.getJsFileName(irFile);
        return jsFileName == null ? IrJsUtilsKt.getNameWithoutExtension(irFile) : jsFileName;
    }

    private final String getStableFileName(IrFile irFile) {
        String outputName = getOutputName(irFile);
        String strAsString = irFile.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return getFileStableName(outputName, strAsString);
    }
}
