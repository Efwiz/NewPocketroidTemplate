package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.js.backend.ast.JsImport;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModuleKt;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: JsIrProgramFragment.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 $2\u00020\u0001:\u0001$BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\r0\n¢\u0006\u0002\u0010\u000eJ\u000e\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eJ\u0014\u0010\u001f\u001a\u00020\u0015*\u00020\r2\u0006\u0010 \u001a\u00020!H\u0002J\u0014\u0010\"\u001a\u00020\u0015*\u00020\r2\u0006\u0010 \u001a\u00020!H\u0002J\u0014\u0010#\u001a\u00020\u0015*\u00020\r2\u0006\u0010 \u001a\u00020!H\u0002R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\r0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R6\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150\n2\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00150\n@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "importedModules", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "transitiveExportFrom", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleTransitiveExport;", PsiKeyword.EXPORTS, "", "", "imports", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleImport;", "(Lorg/jetbrains/kotlin/serialization/js/ModuleKind;Ljava/util/List;Ljava/util/List;Ljava/util/Map;Ljava/util/Map;)V", "getExports", "()Ljava/util/Map;", "getImportedModules", "()Ljava/util/List;", "getImports", "<set-?>", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "jsImports", "getJsImports", "getModuleKind", "()Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "getTransitiveExportFrom", "initJsImportsForModule", "", "module", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrModule;", "generateCrossModuleImportStatement", "importedAs", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "generateImportVariableDeclaration", "generateJsImportStatement", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CrossModuleReferences {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Map<String, String> exports;
    private final List<JsImportedModule> importedModules;
    private final Map<String, CrossModuleImport> imports;
    private Map<String, ? extends JsStatement> jsImports;
    private final ModuleKind moduleKind;
    private final List<CrossModuleTransitiveExport> transitiveExportFrom;

    /* JADX INFO: compiled from: JsIrProgramFragment.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ModuleKind.values().length];
            try {
                iArr[ModuleKind.ES.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CrossModuleReferences(ModuleKind moduleKind, List<JsImportedModule> importedModules, List<CrossModuleTransitiveExport> transitiveExportFrom, Map<String, String> exports, Map<String, CrossModuleImport> imports) {
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        Intrinsics.checkNotNullParameter(importedModules, "importedModules");
        Intrinsics.checkNotNullParameter(transitiveExportFrom, "transitiveExportFrom");
        Intrinsics.checkNotNullParameter(exports, "exports");
        Intrinsics.checkNotNullParameter(imports, "imports");
        this.moduleKind = moduleKind;
        this.importedModules = importedModules;
        this.transitiveExportFrom = transitiveExportFrom;
        this.exports = exports;
        this.imports = imports;
        this.jsImports = MapsKt.emptyMap();
    }

    public final ModuleKind getModuleKind() {
        return this.moduleKind;
    }

    public final List<JsImportedModule> getImportedModules() {
        return this.importedModules;
    }

    public final List<CrossModuleTransitiveExport> getTransitiveExportFrom() {
        return this.transitiveExportFrom;
    }

    public final Map<String, String> getExports() {
        return this.exports;
    }

    public final Map<String, CrossModuleImport> getImports() {
        return this.imports;
    }

    public final Map<String, JsStatement> getJsImports() {
        return this.jsImports;
    }

    public final void initJsImportsForModule(JsIrModule module) {
        Intrinsics.checkNotNullParameter(module, "module");
        List<JsIrProgramFragment> fragments = module.getFragments();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = fragments.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, ((JsIrProgramFragment) it.next()).getNameBindings().entrySet());
        }
        ArrayList<Map.Entry> arrayList2 = arrayList;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList2, 10)), 16));
        for (Map.Entry entry : arrayList2) {
            Pair pair = TuplesKt.to(entry.getKey(), entry.getValue());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        Set<Map.Entry<String, CrossModuleImport>> setEntrySet = this.imports.entrySet();
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(setEntrySet, 10)), 16));
        Iterator<T> it2 = setEntrySet.iterator();
        while (it2.hasNext()) {
            Map.Entry entry2 = (Map.Entry) it2.next();
            JsName jsName = (JsName) linkedHashMap.get(entry2.getKey());
            if (jsName == null) {
                throw new IllegalStateException(("Internal error: cannot find imported name for signature " + ((String) entry2.getKey())).toString());
            }
            Pair pair2 = TuplesKt.to(entry2.getKey(), generateCrossModuleImportStatement((CrossModuleImport) entry2.getValue(), jsName));
            linkedHashMap2.put(pair2.getFirst(), pair2.getSecond());
        }
        this.jsImports = linkedHashMap2;
    }

    private final JsStatement generateCrossModuleImportStatement(CrossModuleImport crossModuleImport, JsName jsName) {
        if (WhenMappings.$EnumSwitchMapping$0[this.moduleKind.ordinal()] == 1) {
            return generateJsImportStatement(crossModuleImport, jsName);
        }
        return generateImportVariableDeclaration(crossModuleImport, jsName);
    }

    private final JsStatement generateImportVariableDeclaration(CrossModuleImport crossModuleImport, JsName jsName) {
        return new JsVars(new JsVars.JsVar(jsName, new JsNameRef(crossModuleImport.getExportedAs(), ReservedJsNames.INSTANCE.makeCrossModuleNameRef(crossModuleImport.getModuleExporter().getInternalName()))));
    }

    private final JsStatement generateJsImportStatement(CrossModuleImport crossModuleImport, JsName jsName) {
        return new JsImport(JsImportedModuleKt.getRequireName(crossModuleImport.getModuleExporter(), true), new JsImport.Element(new JsName(crossModuleImport.getExportedAs(), false), jsName.makeRef()));
    }

    /* JADX INFO: compiled from: JsIrProgramFragment.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences$Companion;", "", "()V", "Empty", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/CrossModuleReferences;", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CrossModuleReferences Empty(ModuleKind moduleKind) {
            Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
            return new CrossModuleReferences(moduleKind, CollectionsKt.emptyList(), CollectionsKt.emptyList(), MapsKt.emptyMap(), MapsKt.emptyMap());
        }
    }
}
