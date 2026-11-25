package org.jetbrains.kotlin.ir.backend.js;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.backend.js.lower.serialization.ir.JsIrLinker;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: klib.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0002\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001f\u001a\u00020\tHÆ\u0003J\t\u0010 \u001a\u00020\u000bHÆ\u0003J\u0015\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\rHÆ\u0003JW\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\rHÆ\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020'HÖ\u0001J\t\u0010(\u001a\u00020\u000eHÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/IrModuleInfo;", "", "module", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "allDependencies", "", "bultins", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "deserializer", "Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "moduleFragmentToUniqueName", "", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Ljava/util/List;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;Ljava/util/Map;)V", "getAllDependencies", "()Ljava/util/List;", "getBultins", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getDeserializer", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/serialization/ir/JsIrLinker;", "getModule", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragmentToUniqueName", "()Ljava/util/Map;", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "ir.serialization.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IrModuleInfo {
    private final List<IrModuleFragment> allDependencies;
    private final IrBuiltIns bultins;
    private final JsIrLinker deserializer;
    private final IrModuleFragment module;
    private final Map<IrModuleFragment, String> moduleFragmentToUniqueName;
    private final SymbolTable symbolTable;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrModuleInfo copy$default(IrModuleInfo irModuleInfo, IrModuleFragment irModuleFragment, List list, IrBuiltIns irBuiltIns, SymbolTable symbolTable, JsIrLinker jsIrLinker, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            irModuleFragment = irModuleInfo.module;
        }
        if ((i & 2) != 0) {
            list = irModuleInfo.allDependencies;
        }
        List list2 = list;
        if ((i & 4) != 0) {
            irBuiltIns = irModuleInfo.bultins;
        }
        IrBuiltIns irBuiltIns2 = irBuiltIns;
        if ((i & 8) != 0) {
            symbolTable = irModuleInfo.symbolTable;
        }
        SymbolTable symbolTable2 = symbolTable;
        if ((i & 16) != 0) {
            jsIrLinker = irModuleInfo.deserializer;
        }
        JsIrLinker jsIrLinker2 = jsIrLinker;
        if ((i & 32) != 0) {
            map = irModuleInfo.moduleFragmentToUniqueName;
        }
        return irModuleInfo.copy(irModuleFragment, list2, irBuiltIns2, symbolTable2, jsIrLinker2, map);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final IrModuleFragment getModule() {
        return this.module;
    }

    public final List<IrModuleFragment> component2() {
        return this.allDependencies;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final IrBuiltIns getBultins() {
        return this.bultins;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final JsIrLinker getDeserializer() {
        return this.deserializer;
    }

    public final Map<IrModuleFragment, String> component6() {
        return this.moduleFragmentToUniqueName;
    }

    public final IrModuleInfo copy(IrModuleFragment module, List<? extends IrModuleFragment> allDependencies, IrBuiltIns bultins, SymbolTable symbolTable, JsIrLinker deserializer, Map<IrModuleFragment, String> moduleFragmentToUniqueName) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(allDependencies, "allDependencies");
        Intrinsics.checkNotNullParameter(bultins, "bultins");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(deserializer, "deserializer");
        Intrinsics.checkNotNullParameter(moduleFragmentToUniqueName, "moduleFragmentToUniqueName");
        return new IrModuleInfo(module, allDependencies, bultins, symbolTable, deserializer, moduleFragmentToUniqueName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IrModuleInfo)) {
            return false;
        }
        IrModuleInfo irModuleInfo = (IrModuleInfo) other;
        return Intrinsics.areEqual(this.module, irModuleInfo.module) && Intrinsics.areEqual(this.allDependencies, irModuleInfo.allDependencies) && Intrinsics.areEqual(this.bultins, irModuleInfo.bultins) && Intrinsics.areEqual(this.symbolTable, irModuleInfo.symbolTable) && Intrinsics.areEqual(this.deserializer, irModuleInfo.deserializer) && Intrinsics.areEqual(this.moduleFragmentToUniqueName, irModuleInfo.moduleFragmentToUniqueName);
    }

    public int hashCode() {
        return (((((((((this.module.hashCode() * 31) + this.allDependencies.hashCode()) * 31) + this.bultins.hashCode()) * 31) + this.symbolTable.hashCode()) * 31) + this.deserializer.hashCode()) * 31) + this.moduleFragmentToUniqueName.hashCode();
    }

    public String toString() {
        return "IrModuleInfo(module=" + this.module + ", allDependencies=" + this.allDependencies + ", bultins=" + this.bultins + ", symbolTable=" + this.symbolTable + ", deserializer=" + this.deserializer + ", moduleFragmentToUniqueName=" + this.moduleFragmentToUniqueName + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IrModuleInfo(IrModuleFragment module, List<? extends IrModuleFragment> allDependencies, IrBuiltIns bultins, SymbolTable symbolTable, JsIrLinker deserializer, Map<IrModuleFragment, String> moduleFragmentToUniqueName) {
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(allDependencies, "allDependencies");
        Intrinsics.checkNotNullParameter(bultins, "bultins");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(deserializer, "deserializer");
        Intrinsics.checkNotNullParameter(moduleFragmentToUniqueName, "moduleFragmentToUniqueName");
        this.module = module;
        this.allDependencies = allDependencies;
        this.bultins = bultins;
        this.symbolTable = symbolTable;
        this.deserializer = deserializer;
        this.moduleFragmentToUniqueName = moduleFragmentToUniqueName;
    }

    public final IrModuleFragment getModule() {
        return this.module;
    }

    public final List<IrModuleFragment> getAllDependencies() {
        return this.allDependencies;
    }

    public final IrBuiltIns getBultins() {
        return this.bultins;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final JsIrLinker getDeserializer() {
        return this.deserializer;
    }

    public final Map<IrModuleFragment, String> getModuleFragmentToUniqueName() {
        return this.moduleFragmentToUniqueName;
    }
}
