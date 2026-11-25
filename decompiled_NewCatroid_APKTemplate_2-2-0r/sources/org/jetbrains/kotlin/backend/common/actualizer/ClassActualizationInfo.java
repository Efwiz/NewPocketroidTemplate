package org.jetbrains.kotlin.backend.common.actualizer;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: ExpectActualCollector.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B]\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003\u0012\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0003\u0012\u0014\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0003¢\u0006\u0002\u0010\u000eJ\u0015\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003HÆ\u0003J\u0015\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003HÆ\u0003J\u001b\u0010\u0016\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0003HÆ\u0003J\u0017\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0003HÆ\u0003Ji\u0010\u0018\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u00032\u001a\b\u0002\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u00032\u0016\b\u0002\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0003HÆ\u0001J\u0013\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001d\u001a\u00020\u0004J\t\u0010\u001e\u001a\u00020\u001fHÖ\u0001J\t\u0010 \u001a\u00020!HÖ\u0001R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u001f\u0010\f\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R#\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00070\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/ClassActualizationInfo;", "", "actualClasses", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "actualTypeAliases", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "actualTopLevels", "Lorg/jetbrains/kotlin/name/CallableId;", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "actualSymbolsToFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;Ljava/util/Map;)V", "getActualClasses", "()Ljava/util/Map;", "getActualSymbolsToFile", "getActualTopLevels", "getActualTypeAliases", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "getActualWithoutExpansion", "classId", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassActualizationInfo {
    private final Map<ClassId, IrClassSymbol> actualClasses;
    private final Map<IrSymbol, IrFile> actualSymbolsToFile;
    private final Map<CallableId, List<IrSymbol>> actualTopLevels;
    private final Map<ClassId, IrTypeAliasSymbol> actualTypeAliases;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ClassActualizationInfo copy$default(ClassActualizationInfo classActualizationInfo, Map map, Map map2, Map map3, Map map4, int i, Object obj) {
        if ((i & 1) != 0) {
            map = classActualizationInfo.actualClasses;
        }
        if ((i & 2) != 0) {
            map2 = classActualizationInfo.actualTypeAliases;
        }
        if ((i & 4) != 0) {
            map3 = classActualizationInfo.actualTopLevels;
        }
        if ((i & 8) != 0) {
            map4 = classActualizationInfo.actualSymbolsToFile;
        }
        return classActualizationInfo.copy(map, map2, map3, map4);
    }

    public final Map<ClassId, IrClassSymbol> component1() {
        return this.actualClasses;
    }

    public final Map<ClassId, IrTypeAliasSymbol> component2() {
        return this.actualTypeAliases;
    }

    public final Map<CallableId, List<IrSymbol>> component3() {
        return this.actualTopLevels;
    }

    public final Map<IrSymbol, IrFile> component4() {
        return this.actualSymbolsToFile;
    }

    public final ClassActualizationInfo copy(Map<ClassId, ? extends IrClassSymbol> actualClasses, Map<ClassId, ? extends IrTypeAliasSymbol> actualTypeAliases, Map<CallableId, ? extends List<? extends IrSymbol>> actualTopLevels, Map<IrSymbol, ? extends IrFile> actualSymbolsToFile) {
        Intrinsics.checkNotNullParameter(actualClasses, "actualClasses");
        Intrinsics.checkNotNullParameter(actualTypeAliases, "actualTypeAliases");
        Intrinsics.checkNotNullParameter(actualTopLevels, "actualTopLevels");
        Intrinsics.checkNotNullParameter(actualSymbolsToFile, "actualSymbolsToFile");
        return new ClassActualizationInfo(actualClasses, actualTypeAliases, actualTopLevels, actualSymbolsToFile);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassActualizationInfo)) {
            return false;
        }
        ClassActualizationInfo classActualizationInfo = (ClassActualizationInfo) other;
        return Intrinsics.areEqual(this.actualClasses, classActualizationInfo.actualClasses) && Intrinsics.areEqual(this.actualTypeAliases, classActualizationInfo.actualTypeAliases) && Intrinsics.areEqual(this.actualTopLevels, classActualizationInfo.actualTopLevels) && Intrinsics.areEqual(this.actualSymbolsToFile, classActualizationInfo.actualSymbolsToFile);
    }

    public int hashCode() {
        return (((((this.actualClasses.hashCode() * 31) + this.actualTypeAliases.hashCode()) * 31) + this.actualTopLevels.hashCode()) * 31) + this.actualSymbolsToFile.hashCode();
    }

    public String toString() {
        return "ClassActualizationInfo(actualClasses=" + this.actualClasses + ", actualTypeAliases=" + this.actualTypeAliases + ", actualTopLevels=" + this.actualTopLevels + ", actualSymbolsToFile=" + this.actualSymbolsToFile + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ClassActualizationInfo(Map<ClassId, ? extends IrClassSymbol> actualClasses, Map<ClassId, ? extends IrTypeAliasSymbol> actualTypeAliases, Map<CallableId, ? extends List<? extends IrSymbol>> actualTopLevels, Map<IrSymbol, ? extends IrFile> actualSymbolsToFile) {
        Intrinsics.checkNotNullParameter(actualClasses, "actualClasses");
        Intrinsics.checkNotNullParameter(actualTypeAliases, "actualTypeAliases");
        Intrinsics.checkNotNullParameter(actualTopLevels, "actualTopLevels");
        Intrinsics.checkNotNullParameter(actualSymbolsToFile, "actualSymbolsToFile");
        this.actualClasses = actualClasses;
        this.actualTypeAliases = actualTypeAliases;
        this.actualTopLevels = actualTopLevels;
        this.actualSymbolsToFile = actualSymbolsToFile;
    }

    public final Map<ClassId, IrClassSymbol> getActualClasses() {
        return this.actualClasses;
    }

    public final Map<ClassId, IrTypeAliasSymbol> getActualTypeAliases() {
        return this.actualTypeAliases;
    }

    public final Map<CallableId, List<IrSymbol>> getActualTopLevels() {
        return this.actualTopLevels;
    }

    public final Map<IrSymbol, IrFile> getActualSymbolsToFile() {
        return this.actualSymbolsToFile;
    }

    public final IrSymbol getActualWithoutExpansion(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        IrTypeAliasSymbol irTypeAliasSymbol = this.actualTypeAliases.get(classId);
        return irTypeAliasSymbol != null ? irTypeAliasSymbol : this.actualClasses.get(classId);
    }
}
