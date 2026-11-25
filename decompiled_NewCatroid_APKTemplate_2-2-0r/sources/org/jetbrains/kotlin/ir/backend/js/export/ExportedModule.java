package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0003J-\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedModule;", "", "name", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "declarations", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;)V", "getDeclarations", "()Ljava/util/List;", "getModuleKind", "()Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "getName", "()Ljava/lang/String;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExportedModule {
    private final List<ExportedDeclaration> declarations;
    private final ModuleKind moduleKind;
    private final String name;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExportedModule copy$default(ExportedModule exportedModule, String str, ModuleKind moduleKind, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = exportedModule.name;
        }
        if ((i & 2) != 0) {
            moduleKind = exportedModule.moduleKind;
        }
        if ((i & 4) != 0) {
            list = exportedModule.declarations;
        }
        return exportedModule.copy(str, moduleKind, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ModuleKind getModuleKind() {
        return this.moduleKind;
    }

    public final List<ExportedDeclaration> component3() {
        return this.declarations;
    }

    public final ExportedModule copy(String name2, ModuleKind moduleKind, List<? extends ExportedDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        return new ExportedModule(name2, moduleKind, declarations);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportedModule)) {
            return false;
        }
        ExportedModule exportedModule = (ExportedModule) other;
        return Intrinsics.areEqual(this.name, exportedModule.name) && this.moduleKind == exportedModule.moduleKind && Intrinsics.areEqual(this.declarations, exportedModule.declarations);
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + this.moduleKind.hashCode()) * 31) + this.declarations.hashCode();
    }

    public String toString() {
        return "ExportedModule(name=" + this.name + ", moduleKind=" + this.moduleKind + ", declarations=" + this.declarations + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ExportedModule(String name2, ModuleKind moduleKind, List<? extends ExportedDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        this.name = name2;
        this.moduleKind = moduleKind;
        this.declarations = declarations;
    }

    public final String getName() {
        return this.name;
    }

    public final ModuleKind getModuleKind() {
        return this.moduleKind;
    }

    public final List<ExportedDeclaration> getDeclarations() {
        return this.declarations;
    }
}
