package org.jetbrains.kotlin.ir.backend.js.ic;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.IrModuleToJsTransformerKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsIrModule;

/* JADX INFO: compiled from: ModuleArtifact.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\fJ\u0012\u0010\u0018\u001a\u00020\u00192\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0003R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/ic/ModuleArtifact;", "", "moduleName", "", "fileArtifacts", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/SrcFileArtifact;", "artifactsDir", "Ljava/io/File;", "forceRebuildJs", "", "externalModuleName", "(Ljava/lang/String;Ljava/util/List;Ljava/io/File;ZLjava/lang/String;)V", "getArtifactsDir", "()Ljava/io/File;", "getFileArtifacts", "()Ljava/util/List;", "getForceRebuildJs", "()Z", "moduleExternalName", "getModuleExternalName", "()Ljava/lang/String;", "moduleSafeName", "getModuleSafeName", "loadJsIrModule", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "reexportedInModuleWithName", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ModuleArtifact {
    private final File artifactsDir;
    private final List<SrcFileArtifact> fileArtifacts;
    private final boolean forceRebuildJs;
    private final String moduleExternalName;
    private final String moduleSafeName;

    public ModuleArtifact(String moduleName, List<SrcFileArtifact> fileArtifacts, File file, boolean z, String str) {
        Intrinsics.checkNotNullParameter(moduleName, "moduleName");
        Intrinsics.checkNotNullParameter(fileArtifacts, "fileArtifacts");
        this.fileArtifacts = fileArtifacts;
        this.artifactsDir = file;
        this.forceRebuildJs = z;
        String safeModuleName = IrModuleToJsTransformerKt.getSafeModuleName(moduleName);
        this.moduleSafeName = safeModuleName;
        this.moduleExternalName = str == null ? safeModuleName : str;
    }

    public /* synthetic */ ModuleArtifact(String str, List list, File file, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, (i & 4) != 0 ? null : file, (i & 8) != 0 ? false : z, (i & 16) != 0 ? null : str2);
    }

    public final List<SrcFileArtifact> getFileArtifacts() {
        return this.fileArtifacts;
    }

    public final File getArtifactsDir() {
        return this.artifactsDir;
    }

    public final boolean getForceRebuildJs() {
        return this.forceRebuildJs;
    }

    public final String getModuleSafeName() {
        return this.moduleSafeName;
    }

    public final String getModuleExternalName() {
        return this.moduleExternalName;
    }

    public static /* synthetic */ JsIrModule loadJsIrModule$default(ModuleArtifact moduleArtifact, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = null;
        }
        return moduleArtifact.loadJsIrModule(str);
    }

    public final JsIrModule loadJsIrModule(String reexportedInModuleWithName) {
        List listSortedWith = CollectionsKt.sortedWith(this.fileArtifacts, new Comparator() { // from class: org.jetbrains.kotlin.ir.backend.js.ic.ModuleArtifact$loadJsIrModule$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((SrcFileArtifact) t).getSrcFilePath(), ((SrcFileArtifact) t2).getSrcFilePath());
            }
        });
        ArrayList arrayList = new ArrayList();
        Iterator it = listSortedWith.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((SrcFileArtifact) it.next()).loadJsIrFragments());
        }
        return new JsIrModule(this.moduleSafeName, this.moduleExternalName, arrayList, reexportedInModuleWithName);
    }
}
